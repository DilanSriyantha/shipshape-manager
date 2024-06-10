package org.devdynamos.utils;

import org.devdynamos.models.Order;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PendingOrdersTableModel extends AbstractTableModel {
    private final List<Order> pendingOrders;
    private final String[] columnNames = { "ID", "Caption", "Supplier", "Product", "Quantity", "Expected Delivery Date", "Date Placed" };

    public PendingOrdersTableModel(List<Order> pendingOrders){
        this.pendingOrders = pendingOrders;
    }

    @Override
    public int getRowCount() {
        return pendingOrders.size();
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
        Order order = pendingOrders.get(rowIndex);
        switch (columnIndex){
            case 0: return order.getOrderId();
            case 1: return order.getOrderCaption();
            case 2: return order.getSupplierName();
            case 3: return order.getPartName();
            case 4: return order.getQuantity();
            case 5: return order.getExpectedDeliveryDate();
            case 6: return order.getPlacedDate();
            default: throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public Order getOrderAt(int rowIndex){
        return pendingOrders.get(rowIndex);
    }
}
