package org.devdynamos.contollers;

import org.devdynamos.models.Order;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.ArrayList;
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
            List<Order> ordersList = DBManager.executeQuery(Order.class, "select orderId, orderCaption, o.supplierId, supplierName, o.sparePartId, partName, quantity, expectedDeliveryDate, placedDate from orders as o join (select supplierId, supplierName from suppliers) as sup on o.supplierId = sup.supplierId join (select partId, partName from spareparts) as sp on o.sparePartId = sp.partId;");
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
