package org.devdynamos.tableModels;
import org.devdynamos.models.OrderService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderServicesTableModel extends AbstractTableModel {
    private final List<OrderService> services;
    private final String[] columnNames = new String[] { "ID", "Service Name", "Quantity" };

    public OrderServicesTableModel(List<OrderService> services){
        this.services = services;
    }

    @Override
    public int getRowCount() {
        return services.size();
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
        OrderService service = services.get(rowIndex);
        return switch (columnIndex){
            case 0 -> service.getServiceId();
            case 1 -> service.getServiceName();
            case 2 -> service.getQuantity();
            default -> throw new IllegalArgumentException("Invalid column index");
        };
    }
}
