package org.devdynamos.tableModels;

import org.devdynamos.models.HistoryOrder;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderHistoryTableModel extends AbstractTableModel {
    private final List<HistoryOrder> ordersList;
    private final String[] columnNames = new String[] { "ID", "Order", "Customer", "dis.Rate", "vat.Rate", "service.Rate", "Total", "GrandTotal", "Paid", "Balance", "Date" };

    public OrderHistoryTableModel(List<HistoryOrder> ordersList){
        this.ordersList = ordersList;
    }

    @Override
    public int getRowCount() {
        return ordersList.size();
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HistoryOrder order = ordersList.get(rowIndex);
        return switch (columnIndex){
            case 0 -> order.getCustomerOrderId();
            case 1 -> order.getCustomerOrderCaption();
            case 2 -> order.getCustomerName();
            case 3 -> order.getDiscountRate();
            case 4 -> order.getVatRate();
            case 5 -> order.getServiceChargeRate();
            case 6 -> order.getTotal();
            case 7 -> order.getGrandTotal();
            case 8 -> order.getPaidAmount();
            case 9 -> order.getBalanceAmount();
            case 10 -> order.getDatePlaced();
            default -> throw new IllegalArgumentException("invalid column index");
        };
    }

    public HistoryOrder getOrderAt(int rowIndex){
        return ordersList.get(rowIndex);
    }
}
