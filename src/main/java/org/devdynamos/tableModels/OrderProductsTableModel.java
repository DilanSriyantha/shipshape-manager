package org.devdynamos.tableModels;

import org.devdynamos.models.OrderProduct;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderProductsTableModel extends AbstractTableModel {
    private final List<OrderProduct> products;
    private final String[] columnNames = new String[] { "ID", "Product Name", "Quantity" };

    public OrderProductsTableModel(List<OrderProduct> products){
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
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
        OrderProduct product = products.get(rowIndex);
        return switch (columnIndex){
            case 0 -> product.getPartId();
            case 1 -> product.getPartName();
            case 2 -> product.getQuantity();
            default -> throw new IllegalArgumentException("Invalid column index");
        };
    }
}
