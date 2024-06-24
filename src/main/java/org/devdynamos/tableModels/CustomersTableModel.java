package org.devdynamos.tableModels;

import org.devdynamos.models.Customer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomersTableModel extends AbstractTableModel {
    private final List<Customer> customersList;
    private final String[] columnNames = { "ID", "Name", "Contact Number", "Email", "Registered Date" };

    public CustomersTableModel(List<Customer> customersList){
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
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customersList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> customer.getCustomerId();
            case 1 -> customer.getCustomerName();
            case 2 -> customer.getContactNumber();
            case 3 -> customer.getEmail();
            case 4 -> customer.getRegisteredDate();
            default -> throw new IllegalArgumentException("Invalid column index");
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public Customer getCustomerAt(int rowIndex) {
        return customersList.get(rowIndex);
    }
}
