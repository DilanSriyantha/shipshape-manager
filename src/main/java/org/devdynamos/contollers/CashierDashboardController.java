package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetProductsAndServicesCallback;
import org.devdynamos.interfaces.GetServicesListCallback;
import org.devdynamos.models.OrderItem;
import org.devdynamos.models.Service;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.Console;

import java.util.ArrayList;
import java.util.List;

public class CashierDashboardController {
    private final List<OrderItem> orderItemRecords;
    private final InventoryController inventoryController;
    private final ServiceController serviceController;

    public CashierDashboardController(){
        this.inventoryController = new InventoryController();
        this.serviceController = new ServiceController();
        this.orderItemRecords = new ArrayList<>();
    }

    public List<SparePart> getSpareParts() {
        return inventoryController.getSparePartsList();
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

    public void addRecord(OrderItem orderItem){
        this.orderItemRecords.add(orderItem);
    }

    public List<OrderItem> getOrderItemRecords(){
        return this.orderItemRecords;
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem orderItem : this.orderItemRecords){
            total += orderItem.getTotal();
        }

        return total;
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
