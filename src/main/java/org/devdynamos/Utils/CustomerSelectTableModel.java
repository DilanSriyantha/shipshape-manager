package org.devdynamos.utils;

import org.devdynamos.models.Customer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomerSelectTableModel extends AbstractTableModel {
    private final List<Customer> customersList;
    private final String[] columnNames = new String[] { "ID", "Name" };

    public CustomerSelectTableModel(List<Customer> customersList){
        this.customersList = customersList;
    }
    
    @Override
    public int getRowCount() {
        return customersList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customersList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> customer.getCustomerId();
            case 1 -> customer.getCustomerName();
            default -> throw new IllegalArgumentException("Invalid column index");
        };
    }

    public Customer getCustomerAt(int rowIndex){
        return customersList.get(rowIndex);
    }
}
