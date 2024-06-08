package org.devdynamos.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomBooleanCellRenderer extends DefaultTableCellRenderer {
    private final String positiveStateLabel;

    public CustomBooleanCellRenderer(String positiveStateLabel) {
        this.positiveStateLabel = positiveStateLabel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(value instanceof Boolean){
            boolean val = (Boolean) value;
            setText(val ? this.positiveStateLabel : "Not " + this.positiveStateLabel);
        }

        return this;
    }
}
