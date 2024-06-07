package org.devdynamos.utils;

import org.devdynamos.models.SparePart;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class InventoryTableModel extends AbstractTableModel {
    private final List<SparePart> spareParts;
    private final String[] columnNames = { "ID", "Name", "Supplier", "Rec.Price", "Sel.Price", "Quantity", "onShip" };

    public InventoryTableModel(List<SparePart> spareParts){
        this.spareParts = spareParts;
    }

    @Override
    public int getRowCount() {
        return spareParts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SparePart sparePart = spareParts.get(rowIndex);
        switch (columnIndex){
            case 0: return sparePart.getPartId();
            case 1: return sparePart.getName();
            case 2: return sparePart.getSupplierName();
            case 3: return sparePart.getReceivedPrice();
            case 4: return sparePart.getSellingPrice();
            case 5: return sparePart.getQuantity();
            case 6: return sparePart.isOnShip();
            default: throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public SparePart getSparePartAt(int rowIndex) {
        return this.spareParts.get(rowIndex);
    }
}
