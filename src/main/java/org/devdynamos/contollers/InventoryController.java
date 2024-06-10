package org.devdynamos.contollers;

import com.google.api.services.gmail.model.Message;
import org.checkerframework.checker.units.qual.C;
import org.devdynamos.interfaces.SendOrderPlacementCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.models.Supplier;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;
import org.devdynamos.utils.NotificationSender;

import javax.swing.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InventoryController {
    public InventoryController() {
        if(DBManager.getConnection() != null) return;

        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public void sendOrderPlacementToTheSupplier(SparePart sparePart, String preferredDeliveryDate, int quantity, SendOrderPlacementCallback callback){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NotificationSender.sendEmail(
                            sparePart.getSupplierEmail(),
                            "Order Placement Request for " + sparePart.getName(),
                            "Dear " + sparePart.getSupplierName() + ",\n\n" +
                                    "We trust this message finds you well.\n\n" +
                                    "We are writing to formally notify you that our inventory of " + sparePart.getName() + " is dedicated to maintaining our high standards of service to our customers, it is imperative that we replenish our stock promptly.\n\n" +
                                    "Please find specifics of our order below.\n\n" +
                                    "\t• Product Name : " + sparePart.getName() + "\n" +
                                    "\t• Quantity Required : " + quantity + "\n" +
                                    "\t• Preferred Delivery Date : " + preferredDeliveryDate +"\n\n" +
                                    "We kindly request you to confirm the availability of the aforementioned items and provide an estimated timeline for delivery. Should there be any potential issues or delays, please inform us at your earliest convenience to allow us to make the necessary arrangements.\n\n" +
                                    "Kindly send the invoice and any pertinent documentation to info.shipshapemanager@gmail.com to facilitate prompt processing. We highly value our ongoing partnership with " + sparePart.getSupplierName() + " and appreciate your timely attention to this matter.\n\n" +
                                    "Thank you for your cooperation and prompt response.\n\n" +
                                    "Yours sincerely,\n\n" +
                                    "ShipShape\n" +
                                    "+94 70 13 63 615\n" +
                                    "info.shipshapemanager@gmail.com"
                    );
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String orderCaption = sparePart.getSupplierName() + "-" + sparePart.getName() + "-" + new Date().getTime();
                final int res = new OrdersController().insertOrderRecord(new Order(orderCaption, sparePart.getSupplierId(), sparePart.getPartId(), quantity, preferredDeliveryDate));
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    t1.join();
                    t2.join();

                    Console.log("Both threads are died");
                    callback.execute(0);
                }catch (Exception ex){
                    callback.execute(-1);
                    ex.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        waitingThread.start();
    }

    public void insertSparePart(Object[] values){
        final int res = DBManager.insert("spareparts", new String[] { "supplierId", "partName", "receivedPrice", "sellingPrice", "quantity" }, values);
        if(res > 0){
            JOptionPane.showMessageDialog(null, "Spare part inserted successfully.");
        }
    }

    public List<SparePart> getSparePartsList() {
        try{
            List<SparePart> spareParts = DBManager.executeQuery(SparePart.class, "select * from spareparts as p join (select supplierId, supplierName, supplierEmail from suppliers) as s on p.supplierId = s.supplierId");
            return spareParts;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public List<SparePart> getSparePartsList(String condition){
        try{
            List<SparePart> spareParts = DBManager.executeQuery(SparePart.class, "select * from spareparts as p join (select supplierId, supplierName from suppliers where " + condition + ") as s on p.supplierId = s.supplierId");
            return spareParts;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void updateSparePart(int id, HashMap<String, Object> newValues){
        final int res = DBManager.update("spareparts", newValues, "partId=" + id);

        if(res > 0)
            JOptionPane.showMessageDialog(null, newValues.get("partName") + " updated successfully.");
    }

    public void deleteSparePart(int id){
        DBManager.delete("spareparts", "partId=" + id);
    }

    private <T> Object[][] to2DArray(List<T> list) throws Exception {
        if(list.isEmpty()) return new Object[0][0];

        Class<?> model = list.getFirst().getClass();
        Field[] fields = model.getDeclaredFields();

        Object[][] array2d = new Object[list.size()][fields.length];
        for (int i = 0; i < list.size(); i++) {
            T row = list.get(i);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                array2d[i][j] = fields[j].get(row);
            }
        }

        return array2d;
    }
}
