package org.devdynamos.tableModels;

import org.devdynamos.models.ReportRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ReportTableModel extends AbstractTableModel {
    private final List<ReportRecord> reportRecordList;
    private final String[] columnNames = new String[] { "#", "Caption", "Quantity", "Total", "Date" };

    public ReportTableModel(List<ReportRecord> reportRecordList){
        this.reportRecordList = reportRecordList;
    }

    @Override
    public int getRowCount() {
        return reportRecordList.size();
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
        return switch (columnIndex){
            case 0 -> reportRecordList.get(rowIndex).getId();
            case 1 -> reportRecordList.get(rowIndex).getCaption();
            case 2 -> reportRecordList.get(rowIndex).getQuantity();
            case 3 -> reportRecordList.get(rowIndex).getTotal();
            case 4 -> reportRecordList.get(rowIndex).getDatePlaced();
            default -> throw new IllegalArgumentException("invalid column index");
        };
    }

    public ReportRecord getRecordAt(int rowIndex){
        return reportRecordList.get(rowIndex);
    }
}
