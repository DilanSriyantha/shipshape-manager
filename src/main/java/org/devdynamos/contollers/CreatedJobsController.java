package org.devdynamos.contollers;

import org.devdynamos.interfaces.GetJobsListCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.*;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;
import org.devdynamos.utils.MailGenerator;
import org.devdynamos.utils.NotificationSender;

import java.util.HashMap;
import java.util.List;

public class CreatedJobsController {
    public CreatedJobsController() {}

    public void getJobsList(GetJobsListCallback callback) {
        GetRequestResultSet<ServiceJob> resultSet = new GetRequestResultSet<>();

        Thread getJobs = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<ServiceJob> jobs = DBManager.executeQuery(
                            ServiceJob.class,
                            "select serviceJobId, co.customerId, sj.customerOrderId as orderId, co.customerOrderCaption as orderCaption, s.serviceId, s.serviceName, sj.finished, sj.dateCreated from servicejobs as sj join services as s on sj.serviceId = s.serviceId join customerorders as co on sj.customerOrderId = co.customerOrderId;"
                    );
                    resultSet.setResultList(jobs);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread loadRequiredSkills = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    getJobs.join();

                    for(ServiceJob job : resultSet.getResultList()){
                        List<RequiredSkill> requiredSkills = DBManager.get(RequiredSkill.class, "requiredskills", "serviceId=" + job.getServiceId());
                        job.setRequiredSkills(requiredSkills);
                    }
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread loadAllocatedEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    getJobs.join();
                    for(ServiceJob job : resultSet.getResultList()){
                        List<Employee> employees = DBManager.executeQuery(Employee.class, "select e.empId, e.empName, e.email, e.contactNumber, e.jobRole, e.availability, e.workArea, e.allocationStatus, e.registeredDate from employees as e join (select * from allocatedemployees where serviceJobId=" + job.getServiceJobId() + ") as ae on e.empId = ae.employeeId join servicejobs as sj on ae.serviceJobId = sj.serviceJobId;");
                        job.setAllocatedEmployees(employees);
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
                    loadRequiredSkills.join();
                    loadAllocatedEmployees.join();
                    callback.onSuccess(resultSet.getResultList());
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        getJobs.start();
        loadRequiredSkills.start();
        loadAllocatedEmployees.start();
        waitingThread.start();
    }

    public void finishJob(ServiceJob job, InsertRequestCallback callback){
        Thread updateServiceJob = new Thread(new Runnable() {
            @Override
            public void run() {
                job.setFinished(true);
                DBManager.update("servicejobs", job.toHashMap(), "serviceJobId=" + job.getServiceJobId());
            }
        });

        Thread updateAllocatedEmployeesAndDeleteAllocationRecords = new Thread(new Runnable() {
            @Override
            public void run() {
                for(Employee employee : job.getAllocatedEmployees()){
                    employee.setAllocationStatus(false);
                    DBManager.update("employees", employee.toHashMap(), "empId=" + employee.getEmpId());
                    DBManager.delete("allocatedEmployees", "serviceJobId=" + job.getServiceJobId() + " and employeeId=" + employee.getEmpId());

                    Console.log(employee.toString());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        callback.onFailed(ex);
                    }
                }
            }
        });

        Thread sendJobCompletionNotification = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Customer> customers = DBManager.executeQuery(Customer.class, "select c.customerId, c.customerName, c.contactNumber, c.email, c.registeredDate from (select * from servicejobs where serviceJobId = " + job.getServiceJobId() + ") as sj\n" +
                            "join customerorders as co on sj.customerOrderId = co.customerOrderId\n" +
                            "join customers as c on co.customerId = c.customerId;");
                    if(!customers.isEmpty())
                        NotificationSender.sendEmail(customers.getFirst().getEmail(), "Ordered service is completed.", MailGenerator.generateJobCompletionNotification(customers.getFirst(), job), NotificationSender.HTML);
                }catch(Exception ex){
                    Console.log(ex.getMessage());
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    updateServiceJob.join();
                    updateAllocatedEmployeesAndDeleteAllocationRecords.join();
                    sendJobCompletionNotification.join();

                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        updateServiceJob.start();
        updateAllocatedEmployeesAndDeleteAllocationRecords.start();
        sendJobCompletionNotification.start();
        waitingThread.start();
    }
}
