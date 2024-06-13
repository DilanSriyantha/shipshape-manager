package org.devdynamos.contollers;

import org.devdynamos.interfaces.MergeCompletedCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrdersController {
    public OrdersController() {
        if(DBManager.getConnection() != null) return;

        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");

        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public List<Order> getOrderRecordsList(){
        try{
            List<Order> ordersList = DBManager.executeQuery(
                    Order.class,
                    "select orderId, o.orderCaption, o.supplierId, sup.supplierName, o.sparePartId, sp.partName, o.quantity, sp.currentQuantity, sp.receivedPrice, sp.sellingPrice, o.expectedDeliveryDate, o.placedDate, o.delivered from orders as o\n" +
                            "join (select supplierId, supplierName from suppliers) as sup on o.supplierId = sup.supplierId\n" +
                            "join (select partId, partName, receivedPrice, sellingPrice, currentQuantity from spareparts) as sp on o.sparePartId = sp.partId;"
            );
            return ordersList;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new ArrayList<>();
    }

    public int insertOrderRecord(Order order){
        String[] columns = { "orderCaption", "supplierId", "sparePartId", "quantity", "expectedDeliveryDate" };
        Object[] values = order.toObjectArray();

        final int res = DBManager.insert("orders", columns, values);

        return res;
    }

    public int updateOrderRecord(int id, HashMap<String, Object> newValues){
        final int res = DBManager.update("orders", newValues, "orderId=" + id);
        return res;
    }

    public void executeMergeOperation(List<SparePart> productsToReplace, List<SparePart> productsToInsert, List<Order> ordersToUpdate, MergeCompletedCallback callback){
        InventoryController inventoryController = new InventoryController();

        Thread replaceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(SparePart productToReplace : productsToReplace){
                    inventoryController.insertOrReplaceSparePart(productToReplace.toObjectArray_toReplace());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Console.log("replace thread completed task");
            }
        });

        Thread insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(SparePart productToInsert : productsToInsert){
                    inventoryController.insertSparePart(productToInsert.toObjectArray());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Console.log("insert thread completed task");
            }
        });

        Thread orderUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(Order orderToUpdate : ordersToUpdate){
                    updateOrderRecord(
                            orderToUpdate.getOrderId(),
                            new HashMap<>() {{
                                put("delivered", true);
                            }}
                    );
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    replaceThread.join();
                    insertThread.join();
                    orderUpdateThread.join();

                    callback.onSuccess();
                } catch (InterruptedException e) {
                    callback.onFailed(e);
                }
            }
        });

        replaceThread.start();
        insertThread.start();
        orderUpdateThread.start();
        waitingThread.start();
    }
}
