package org.devdynamos.contollers;

import org.devdynamos.models.Order;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersController {
    public OrdersController() {
        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");

        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public List<Order> getOrderRecordsList(){
        try{
            List<Order> ordersList = DBManager.getAll(Order.class, "orders");
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
}
