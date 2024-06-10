package org.devdynamos.contollers;

import org.devdynamos.models.Employee;
import org.devdynamos.utils.DBManager;

import javax.management.ReflectionException;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EmployeesController {
    public EmployeesController() {
        if(DBManager.getConnection() != null) return;

        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public List<Employee> getEmployeesList(){
        try{
            List<Employee> employees = DBManager.getAll(Employee.class, "employees");
            return  employees;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public Object[][] getEmployees2DArray(Component parentComponent) {
        try{
            List<Employee> employees = DBManager.getAll(Employee.class, "employees");
            return this.to2DArray(employees);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new Object[0][0];
        }
    }

    private <T> Object[][] to2DArray(List<T> list) throws IllegalAccessException, ReflectionException, Exception {
        if(list.isEmpty()) return new Object[0][0];

        Class<?> model = list.getFirst().getClass();
        Field[] fields = model.getDeclaredFields();

        Object[][] array2d = new Object[list.size()][fields.length];
        for (int i = 0; i < list.size(); i++) {
            T row = list.get(i);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                array2d[i][j] = fields[j].get(row);
            }
        }

        return array2d;
    }
}
