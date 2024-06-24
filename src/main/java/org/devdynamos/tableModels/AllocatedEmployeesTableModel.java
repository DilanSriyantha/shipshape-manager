package org.devdynamos.tableModels;

import org.devdynamos.models.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AllocatedEmployeesTableModel extends AbstractTableModel {
    private final String[] columnNames = new String[] { "Employee ID", "Employee Name", "Email", "Telephone" };
    private final List<Employee> employees;

    public AllocatedEmployeesTableModel(List<Employee> employees){
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
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
            case 0 -> employees.get(rowIndex).getEmpId();
            case 1 -> employees.get(rowIndex).getEmpName();
            case 2 -> employees.get(rowIndex).getEmail();
            case 3 -> employees.get(rowIndex).getContactNumber();
            default -> throw new IllegalArgumentException("invalid column index");
        };
    }

    public Employee getEmployeeAt(int rowIndex){
        return employees.get(rowIndex);
    }
}
