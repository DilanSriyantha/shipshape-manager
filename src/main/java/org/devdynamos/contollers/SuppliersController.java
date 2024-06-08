package org.devdynamos.contollers;

import org.devdynamos.models.SparePart;
import org.devdynamos.models.Supplier;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuppliersController {
    public SuppliersController() {
        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public List<Supplier> getSuppliersList() {
        try{
            List<Supplier> suppliers = DBManager.getAll(Supplier.class, "suppliers");
            return suppliers;
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void insertSupplier(Supplier supplier){
        String[] columns = { "supplierName", "contactNumber", "email" };

        final int res = DBManager.insert("suppliers", columns, supplier.toObjectArray());
        if(res > 0)
            JOptionPane.showMessageDialog(null, "Supplier is inserted successfully.");
    }

    public void updateSupplier(int id, HashMap<String, Object> newValues){
        final int res = DBManager.update("suppliers", newValues, "supplierId=" + id);
        if(res > 0)
            JOptionPane.showMessageDialog(null, newValues.get("supplierName") + " updated successfully.");
    }

    public void deleteSupplier(int id){
        DBManager.delete("suppliers", "supplierId=" + id);
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
