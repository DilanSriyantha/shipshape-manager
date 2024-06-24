package org.devdynamos.contollers;

import org.devdynamos.interfaces.MergeCompletedCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.ArrayUtils;
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
                    "select orderId, o.orderCaption, o.supplierId, sup.supplierName, o.sparePartId, sp.partName, o.quantity, sp.currentQuantity, sp.receivedPrice, sp.sellingPrice, o.expectedDeliveryDate, o.placedDate, o.delivered from orders as o join (select supplierId, supplierName from suppliers) as sup on o.supplierId = sup.supplierId join (select partId, partName, receivedPrice, sellingPrice, currentQuantity from spareparts) as sp on o.sparePartId = sp.partId order by o.orderId;"
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

    public void executeMergeOperation(List<SparePart> productsToMerge, List<Order> ordersToUpdate, MergeCompletedCallback callback){
        Thread mergeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(SparePart productToMerge : productsToMerge){
                    InventoryController inventoryController = new InventoryController();
                    int id = inventoryController.insertOrReplaceSparePart(productToMerge.toObjectArray());
                    inventoryController.deleteSparePart(productToMerge.getPartId());

                    for(Order orderToUpdate : ordersToUpdate){
                        if(orderToUpdate.getSparePartId() != productToMerge.getPartId()) continue;
                        updateOrderRecord(
                                orderToUpdate.getOrderId(),
                                new HashMap<>(){{
                                    put("sparePartId", id);
                                    put("delivered", true);
                                }}
                        );
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Console.log("replace thread completed task");
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mergeThread.join();

                    callback.onSuccess();
                } catch (InterruptedException e) {
                    callback.onFailed(e);
                }
            }
        });

        mergeThread.start();
        waitingThread.start();
    }
}
