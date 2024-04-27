package org.devdynamos.controller;

import org.devdynamos.model.Employee;

import java.util.ArrayList;

public class EmployeeController {
    private ArrayList<Employee> employeeList = new ArrayList<Employee>();

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Employee addEmployee(String empID, String empName, String address, String position) {
        Employee employee = new Employee(empID, empName, address, position);
        employeeList.add(employee);
        return employee;
    }
}
