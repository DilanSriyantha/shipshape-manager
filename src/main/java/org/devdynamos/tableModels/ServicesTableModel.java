package org.devdynamos.tableModels;

import org.devdynamos.models.Service;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ServicesTableModel extends AbstractTableModel {
    private final List<Service> servicesList;
    private final String[] columnNames = new String[]{ "ID", "Name", "Unit Price", "Date Created" };

    public ServicesTableModel(List<Service> servicesList){
        this.servicesList = servicesList;
    }

    @Override
    public int getRowCount() {
        return servicesList.size();
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
        Service service = servicesList.get(rowIndex);
        switch (columnIndex){
            case 0: return service.getServiceId();
            case 1: return service.getServiceName();
            case 2: return service.getUnitPrice();
            case 3: return service.getDateCreated();
            default: throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public Service getServiceAt(int rowIndex){
        return servicesList.get(rowIndex);
    }
}
