package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.*;
import org.devdynamos.utils.DBManager;
import org.devdynamos.utils.MailGenerator;
import org.devdynamos.utils.NotificationSender;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllocateEmployeesController {
    private final EmployeesController employeesController = new EmployeesController();

    public AllocateEmployeesController() {

    }

    public void getEmployeesList(GetRequestCallback<Employee> callback){
        GetRequestResultSet<Employee> resultSet = new GetRequestResultSet<>();

        Thread loadEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Employee> employees = DBManager.executeQuery(Employee.class, "select * from employees where allocationStatus=0");
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

    public void getAllocatedEmployeesList(ServiceJob job, GetRequestCallback<Employee> callback){
        GetRequestResultSet<Employee> resultSet = new GetRequestResultSet<>();

        Thread loadEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Employee> employees = DBManager.executeQuery(Employee.class, "select e.empId, e.empName, e.email, e.contactNumber, e.jobRole, e.availability, e.workArea, e.allocationStatus, e.registeredDate from employees as e join (select * from allocatedemployees where serviceJobId=" + job.getServiceJobId() + ") as ae on e.empId = ae.employeeId join servicejobs as sj on ae.serviceJobId = sj.serviceJobId;");
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

    public void getSuggestedEmployees(ServiceJob job, GetRequestCallback<Employee> callback) {
        GetRequestResultSet<Employee> resultSet = new GetRequestResultSet<>();

        Thread loadEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Employee> employees = DBManager.executeQuery(Employee.class, "select * from employees where allocationStatus=0");
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

        Thread makeSuggestedList = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadSkills.join();

                    List<Employee> suggestionList = new ArrayList<>();
                    for(Employee employee : resultSet.getResultList()){
                        int matches = 0;
                        for(Skill skill : employee.getSkills()){
                            for(RequiredSkill requiredSkill : job.getRequiredSkills()){
                                if(requiredSkill.getRequiredSkillDescription().equalsIgnoreCase(skill.getSkillDescription())){
                                    matches++;
                                }
                            }
                        }
                        if(matches > 0) suggestionList.add(employee);
                    }
                    resultSet.setResultList(suggestionList);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    makeSuggestedList.join();
                    callback.onSuccess(resultSet);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        loadEmployees.start();
        loadSkills.start();
        makeSuggestedList.start();
        waitingThread.start();
    }

    public void allocateEmployee(ServiceJob job, Employee employee, InsertRequestCallback callback) {
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

        Thread insertAllocationRecord = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String[] columns = new String[]{ "employeeId", "serviceJobId" };

                    DBManager.insert("allocatedemployees", columns, new Object[]{ employee.getEmpId(), job.getServiceJobId() });
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread sendEmailToEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    NotificationSender.sendEmail(employee.getEmail(), "You are allocated to the job " + job.getServiceName() + " (" + job.getServiceJobId() + ")", MailGenerator.generateAllocationNotification(employee, job), NotificationSender.HTML);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    updateEmployee.join();
                    insertAllocationRecord.join();
                    sendEmailToEmployee.join();

                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        updateEmployee.start();
        insertAllocationRecord.start();
        sendEmailToEmployee.start();
        waitingThread.start();
    }

    public void deallocateEmployee(ServiceJob job, Employee employee, InsertRequestCallback callback){
        Thread deleteAllocationRecord = new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.delete("allocatedemployees", "serviceJobId=" + job.getServiceJobId() + " and employeeId=" + employee.getEmpId());
            }
        });

        Thread updateEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                employee.setAllocationStatus(false);
                DBManager.update("employees", employee.toHashMap(), "empId=" + employee.getEmpId());
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    deleteAllocationRecord.join();
                    updateEmployee.join();

                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        deleteAllocationRecord.start();
        updateEmployee.start();
        waitingThread.start();
    }
}
