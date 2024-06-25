package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetProductsAndServicesCallback;
import org.devdynamos.interfaces.GetServicesListCallback;
import org.devdynamos.interfaces.PlaceOrderCallback;
import org.devdynamos.models.*;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;
import org.devdynamos.utils.MailGenerator;
import org.devdynamos.utils.NotificationSender;

import java.util.ArrayList;
import java.util.List;

public class CashierDashboardController {
    private final List<OrderItem> orderItemRecords;
    private final InventoryController inventoryController;
    private final ServiceController serviceController;
    private final CustomerOrder customerOrder;
    private Customer customer;

    public CashierDashboardController(){
        this.inventoryController = new InventoryController();
        this.serviceController = new ServiceController();
        this.orderItemRecords = new ArrayList<>();
        this.customerOrder = new CustomerOrder();

        customerOrder.setCustomerOrderId(getMaxOrderId() + 1);
        customerOrder.setCustomerOrderCaption("Order " + customerOrder.getCustomerOrderId());
    }

    private int getMaxOrderId() {
        try{
            List<MaxOrderIdResultSet> maxOrderIdResultSets = DBManager.executeQuery(MaxOrderIdResultSet.class, "select max(customerOrderId) as maxOrderId from customerorders");
            if(maxOrderIdResultSets.isEmpty()) return 0;
            if(maxOrderIdResultSets.getFirst().maxOrderId == null) return 0;
            return maxOrderIdResultSets.getFirst().maxOrderId;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }

    public void getProductsAndServices(GetProductsAndServicesCallback callback) {
        final GetProductsAndServicesResultSet resultSet = new GetProductsAndServicesResultSet();

        serviceController.getServicesList(new GetServicesListCallback() {
            @Override
            public void onSuccess(List<Service> serviceList) {
                resultSet.setServiceList(serviceList);

                Console.log("service loading thread completed");

                Thread loadSparePartsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<SparePart> sparePartsList = inventoryController.getSparePartsList();
                        resultSet.setSparePartList(sparePartsList);
                    }
                });

                Thread waitingThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loadSparePartsThread.join();

                            callback.onSuccess(resultSet);
                        }catch (Exception ex){
                            callback.onFailed(ex);
                        }
                    }
                });

                loadSparePartsThread.start();
                waitingThread.start();
            }

            @Override
            public void onFailed(Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void addRecord(SparePart sparePart, int quantity){
        final int recId = (int)(Math.random() * 1000);

        OrderItem orderItem = new OrderItem(
                recId,
                customerOrder.getCustomerOrderId(),
                sparePart.getName(),
                sparePart.getSellingPrice(),
                quantity,
                sparePart.getSellingPrice() * quantity
        );
        orderItemRecords.add(orderItem);

        CustomerOrderProduct cop = new CustomerOrderProduct(
                recId,
                customerOrder.getCustomerOrderId(),
                sparePart.getPartId(),
                quantity
        );
        customerOrder.addProduct(cop);

        updateTotal();
    }

    public void addRecord(Service service, int quantity){
        final int recId = (int)(Math.random() * 1000);

        OrderItem orderItem = new OrderItem(
                recId,
                customerOrder.getCustomerOrderId(),
                service.getServiceName(),
                service.getUnitPrice(),
                quantity,
                service.getUnitPrice() * quantity
        );
        orderItemRecords.add(orderItem);

        CustomerOrderService cos = new CustomerOrderService(
                recId,
                customerOrder.getCustomerOrderId(),
                service.getServiceId(),
                quantity
        );
        customerOrder.addService(cos);

        updateTotal();
    }

    public int getRemainingQuantity(SparePart sparePart){
        int qtyInOrder = 0;
        for(CustomerOrderProduct product : customerOrder.getProductsList()){
            if(product.getSparePartId() == sparePart.getPartId())
                qtyInOrder += product.getQuantity();
        }

        return sparePart.getCurrentQuantity() - qtyInOrder;
    }

    public void removeRecord(OrderItem orderItem) {
        orderItemRecords.removeIf((_orderItem) -> _orderItem.getRecordId() == orderItem.getRecordId());
        customerOrder.getProductsList().removeIf((product) -> product.getCustomerOrderProductId() == orderItem.getRecordId());
        customerOrder.getServicesList().removeIf((service) -> service.getCustomerOrderServiceId() == orderItem.getRecordId());

        updateTotal();
    }

    public void addCustomer(Customer customer){
        customerOrder.setCustomerId(customer.getCustomerId());
        this.customer = customer;
        updateTotal();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addDiscountRate(double discountRate){
        customerOrder.setDiscountRate(discountRate);
        updateTotal();
    }

    public void addVatRate(double vatRate){
        customerOrder.setVatRate(vatRate);
        updateTotal();
    }

    public void addServiceChargeRate(double serviceChargeRate){
        customerOrder.setServiceChargeRate(serviceChargeRate);
        updateTotal();
    }

    public void updateTotal() {
        double total = getTotal();
        total -= (total * (customerOrder.getDiscountRate() / 100));
        total += (total * (customerOrder.getVatRate() / 100));
        total += (total * (customerOrder.getServiceChargeRate() / 100));

        customerOrder.setGrandTotal(total);
        customerOrder.setTotal(getTotal());
    }

    public double getSubTotal() {
        return customerOrder.getGrandTotal();
    }

    public List<OrderItem> getOrderItemRecords(){
        return this.orderItemRecords;
    }

    private double getTotal() {
        double total = 0;
        for (OrderItem orderItem : this.orderItemRecords){
            total += orderItem.getTotal();
        }

        return total;
    }

    public CustomerOrder getCurrentOrder() {
        return customerOrder;
    }

    public void placeOrder(CustomerOrder customerOrder, PlaceOrderCallback callback){
        Thread insertCustomerOrder = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] columns = { "customerOrderId", "customerOrderCaption", "customerId", "discountRate", "vatRate", "serviceChargeRate", "total", "grandTotal", "paidAmount", "balanceAmount" };
                DBManager.insert("customerorders", columns, customerOrder.toObjectArray());
            }
        });

        Thread insertOrderedServices = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] columns = { "customerOrderId", "serviceId", "quantity" };
                DBManager.insertBatch("customerorderservices", columns, customerOrder.getOrderedServices2dArray());
            }
        });

        Thread insertOrderedProducts = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] columns = { "customerOrderId", "sparePartId", "quantity" };
                DBManager.insertBatch("customerorderproducts", columns, customerOrder.getOrderedProducts2dArray());
            }
        });

        Thread deductStocks = new Thread(new Runnable() {
            @Override
            public void run() {
                for(CustomerOrderProduct product : customerOrder.getProductsList()){
                    try{
                        String updateQuery = "update spareparts set currentQuantity = currentQuantity - " + product.getQuantity() + " where partId = " + product.getSparePartId() + ";";
                        DBManager.executeCustomQuery(updateQuery);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        Thread createServiceJobs = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] columns = { "customerOrderId", "serviceId" };
                for(CustomerOrderService service : customerOrder.getServicesList()){
                    Object[] values = new Object[]{ service.getCustomerOrderId(), service.getServiceId() };
                    DBManager.insert("servicejobs", columns, values);
                }
            }
        });

        Thread sendEmailToClient = new Thread(new Runnable() {
            @Override
            public void run() {
                if(customer.getEmail().isEmpty()) return;

                String body = MailGenerator.generateInvoice(customerOrder, customer, orderItemRecords);
                try{
                    NotificationSender.sendEmail(customer.getEmail(), customerOrder.getCustomerOrderCaption(), body, NotificationSender.HTML);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    insertCustomerOrder.join();
                    insertOrderedServices.join();
                    insertOrderedProducts.join();
                    deductStocks.join();
                    createServiceJobs.join();
                    sendEmailToClient.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    ex.printStackTrace();
                    callback.onFailed(ex);
                }
            }
        });

        insertCustomerOrder.start();
        insertOrderedServices.start();
        insertOrderedProducts.start();
        deductStocks.start();
        createServiceJobs.start();
        sendEmailToClient.start();
        waitingThread.start();
    }

    public void reset() {
        customerOrder.setCustomerOrderId(getMaxOrderId() + 1);
        customerOrder.setCustomerOrderCaption("Order " + customerOrder.getCustomerOrderId());
        updateTotal();
        customer = null;
        orderItemRecords.clear();
    }

    public class GetProductsAndServicesResultSet {
        private List<SparePart> sparePartList = new ArrayList<>();
        private List<Service> serviceList = new ArrayList<>();

        public GetProductsAndServicesResultSet() {}

        public List<SparePart> getSparePartList() {
            return sparePartList;
        }

        public void setSparePartList(List<SparePart> sparePartList) {
            this.sparePartList = sparePartList;
        }

        public List<Service> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<Service> serviceList) {
            this.serviceList = serviceList;
        }
    }
}
