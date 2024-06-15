package org.devdynamos.contollers;

import org.devdynamos.models.Customer;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersController {
    public CustomersController() {
        if(DBManager.getConnection() != null) return;

        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public List<Customer> getCustomersList() {
        try{
            List<Customer> customerList = DBManager.getAll(Customer.class, "customers");
            return customerList;
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new ArrayList<>();
    }

    public void insertCustomer(Customer customer){
        String[] columns = { "customerName", "contactNumber", "email" };

        final int res = DBManager.insert("customers", columns, customer.toObjectArray());
        if(res > -1)
            JOptionPane.showMessageDialog(null, "Customer inserted successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateCustomer(Customer customer){
        final int res = DBManager.update("customers", customer.toHashMap(), "customerId=" + customer.getCustomerId());
        if(res > -1)
            JOptionPane.showMessageDialog(null, "Customer updated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteCustomer(int id){
        DBManager.delete("customers", "customerId=" + id);
    }
}
