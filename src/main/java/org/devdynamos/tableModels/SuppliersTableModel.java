package org.devdynamos.tableModels;

import org.devdynamos.models.Supplier;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SuppliersTableModel extends AbstractTableModel {
    private final List<Supplier> suppliers;
    private final String[] columnNames = { "ID", "Name", "Contact Number", "Email" };

    public SuppliersTableModel(List<Supplier> spareParts){
        this.suppliers = spareParts;
    }

    @Override
    public int getRowCount() {
        return suppliers.size();
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
        Supplier supplier = suppliers.get(rowIndex);
        switch (columnIndex){
            case 0: return supplier.getSupplierId();
            case 1: return supplier.getSupplierName();
            case 2: return supplier.getContactNumber();
            case 3: return supplier.getEmail();
            default: throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public Supplier getSupplierAt(int rowIndex){
        return this.suppliers.get(rowIndex);
    }
}
