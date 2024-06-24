package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.Employee;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.models.Skill;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;

import javax.management.ReflectionException;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeesController {
    public EmployeesController() {}

    public void getEmployeesList(GetRequestCallback<Employee> callback){
        GetRequestResultSet<Employee> resultSet = new GetRequestResultSet<>();

        Thread loadEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Employee> employees = DBManager.getAll(Employee.class, "employees");
                    resultSet.setResultList(employees);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread loadSkills = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadEmployees.join();

                    for(Employee employee : resultSet.getResultList()){
                        List<Skill> skills = DBManager.get(Skill.class, "skills", "empId=" + employee.getEmpId());
                        employee.setSkills(skills);
                    }
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadSkills.join();
                    callback.onSuccess(resultSet);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        loadEmployees.start();
        loadSkills.start();
        waitingThread.start();
    }

    public void insertEmployee(Employee employee, InsertRequestCallback callback){
        Thread insertEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                Console.log(employee.getEmpId());

                String[] columns = { "empName", "email", "contactNumber", "jobRole", "workArea" };
                int id = DBManager.insert("employees", columns, employee.toObjectArray());
                employee.setEmpId(id);
            }
        });

        Thread insertSkills = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    insertEmployee.join();

                    Console.log(employee.getEmpId());

                    String[] columns = { "empId", "skillDescription" };
                    DBManager.insertBatch("skills", columns, employee.getSkills2dArray());
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    insertSkills.join();
                    callback.onSuccess();
                }catch (Exception ex) {
                    callback.onFailed(ex);
                }
            }
        });

        insertEmployee.start();
        insertSkills.start();
        waitingThread.start();
    }

    public void updateEmployee(Employee employee, InsertRequestCallback callback){
        Thread updateEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HashMap<String, Object> newValues = new HashMap<>(){{
                        put("empName", employee.getEmpName());
                        put("email", employee.getEmail());
                        put("contactNumber", employee.getContactNumber());
                        put("jobRole", employee.getJobRole());
                        put("workArea", employee.getWorkArea());
                    }};
                    DBManager.update("employees", newValues, "empId=" + employee.getEmpId());
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread updateSkills = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    updateEmployee.join();

                    DBManager.delete("skills", "empId=" + employee.getEmpId());

                    Thread.sleep(1000);

                    String[] columns = { "empId", "skillDescription" };
                    DBManager.insertBatch("skills", columns, employee.getSkills2dArray());
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    updateSkills.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        updateEmployee.start();
        updateSkills.start();
        waitingThread.start();
    }

    public void deleteEmployee(int id, InsertRequestCallback callback) {
        Thread deleteEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.delete("employees", "empId=" + id);
            }
        });

        Thread deleteSkills = new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.delete("skills", "empId=" + id);
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    deleteEmployee.join();
                    deleteSkills.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        deleteEmployee.start();
        deleteSkills.start();
        waitingThread.start();
    }
}
