package org.devdynamos.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private List<Employee> employees;
    private final String[] columnNames = { "ID", "Name", "Job Role", "Availability", "Work Area", "Allocation Status" };

    public EmployeeTableModel(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return this.employees.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = this.employees.get(rowIndex);
        switch (columnIndex){
            case 0: return employee.getEmpId();
            case 1: return employee.getEmpName();
            case 2: return employee.getJobRole();
            case 3: return employee.isAvailability();
            case 4: return employee.getWorkArea();
            case 5: return employee.isAllocated();
            default: throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        super.isCellEditable(rowIndex, columnIndex);
        return false;
    }

    public Employee getEmployeeAt(int rowIndex){
        return this.employees.get(rowIndex);
    }

    public void setAllocated(int rowIndex, boolean status){
        Employee updatedEmployee = this.employees.get(rowIndex);
        updatedEmployee.setAllocationStatus(status);

        employees.set(rowIndex, updatedEmployee);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
