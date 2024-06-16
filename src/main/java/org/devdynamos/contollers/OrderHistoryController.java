package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetOrdersListCallback;
import org.devdynamos.models.HistoryOrder;
import org.devdynamos.models.OrderProduct;
import org.devdynamos.models.OrderService;
import org.devdynamos.utils.DBManager;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryController {
    public OrderHistoryController() {}

    public void getOrdersList(GetOrdersListCallback callback) {
        GetOrdersListResultSet resultSet = new GetOrdersListResultSet();

        Thread loadOrders = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<HistoryOrder> ordersList = DBManager.executeQuery(
                            HistoryOrder.class,
                            "select customerOrderId, customerOrderCaption, c.customerName, discountRate, vatRate,\n" +
                                    "serviceChargeRate, total, grandTotal, paidAmount, balanceAmount, datePlaced from customerorders as co\n" +
                                    "join customers as c on co.customerId = c.customerId;"
                    );

                    for(HistoryOrder order : ordersList){
                        List<OrderProduct> products = DBManager.executeQuery(
                                OrderProduct.class,
                                "select sp.partId, sp.partName, cop.quantity from spareparts as sp\n" +
                                        "join (select * from customerorderproducts where customerOrderId = " + order.getCustomerOrderId() + ") as cop on sp.partId = cop.sparePartId;"
                        );
                        order.setProductList(products);

                        List<OrderService> services = DBManager.executeQuery(
                                OrderService.class,
                                "select se.serviceId, se.serviceName, cos.quantity from services as se\n" +
                                        "join (select * from customerorderservices where customerOrderId = " + order.getCustomerOrderId() + ") as cos on se.serviceId = cos.serviceId;"
                        );
                        order.setServicesList(services);
                    }

                    resultSet.setOrders(ordersList);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadOrders.join();

                    Thread.sleep(1000);

                    callback.onSuccess(resultSet.getOrders());
                } catch (Exception e) {
                    e.printStackTrace();

                    callback.onFailed(e);
                }
            }
        });

        loadOrders.start();
        waitingThread.start();
    }

    public class GetOrdersListResultSet {
        public List<HistoryOrder> orders;

        public GetOrdersListResultSet() {}

        public GetOrdersListResultSet(List<HistoryOrder> orders){
            this.orders = orders;
        }

        public List<HistoryOrder> getOrders() {
            return orders;
        }

        public void setOrders(List<HistoryOrder> orders) {
            this.orders = orders;
        }
    }
}
