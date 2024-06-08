package org.devdynamos.contollers;

import org.devdynamos.models.SparePart;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryController {
    public InventoryController() {
        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public void insertSparePart(Object[] values){
        final int res = DBManager.insert("spareparts", new String[] { "supplierId", "partName", "receivedPrice", "sellingPrice", "quantity" }, values);
        if(res > 0){
            JOptionPane.showMessageDialog(null, "Spare part inserted successfully.");
        }
    }

    public List<SparePart> getSparePartsList() {
        try{
            List<SparePart> spareParts = DBManager.executeQuery(SparePart.class, "select * from spareparts as p join (select supplierId, supplierName from suppliers) as s on p.supplierId = s.supplierId");
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
