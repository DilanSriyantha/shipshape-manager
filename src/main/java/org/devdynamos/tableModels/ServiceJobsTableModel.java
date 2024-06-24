package org.devdynamos.tableModels;

import org.devdynamos.models.ServiceJob;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ServiceJobsTableModel extends AbstractTableModel {
    private final List<ServiceJob> serviceJobList;
    private final String[] columnNames = new String[] { "ID", "Order", "Service", "Finished", "Date Created" };

    public ServiceJobsTableModel(List<ServiceJob> serviceJobList){
        this.serviceJobList = serviceJobList;
    }

    @Override
    public int getRowCount() {
        return serviceJobList.size();
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
        ServiceJob serviceJob = serviceJobList.get(rowIndex);
        return switch (columnIndex){
            case 0 -> serviceJob.getServiceJobId();
            case 1 -> serviceJob.getCustomerOrderCaption();
            case 2 -> serviceJob.getServiceName();
            case 3 -> serviceJob.isFinished();
            case 4 -> serviceJob.getDateCreated();
            default -> throw new IllegalArgumentException("Invalid column index");
        };
    }

    public ServiceJob getJobAt(int rowIndex){
        return serviceJobList.get(rowIndex);
    }
}
