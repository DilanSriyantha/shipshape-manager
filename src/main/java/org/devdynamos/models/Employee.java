package org.devdynamos.models;

import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.HashMap;

public class Employee {
    private int empId;
    private String empName;
    private String email;
    private String contactNumber;
    private String jobRole;
    private boolean availability;
    private String workArea;
    private boolean allocationStatus;

    public Employee() {}

    public Employee(int empId, String empName, String email, String contactNumber, String jobRole, boolean availability, String workArea, boolean allocationStatus) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.jobRole = jobRole;
        this.availability = availability;
        this.workArea = workArea;
        this.allocationStatus = allocationStatus;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public boolean isAllocated() {
        return allocationStatus;
    }

    public void setAllocationStatus(boolean allocationStatus) {
        try{
            System.out.println(allocationStatus);
            DBManager.update("employees", new HashMap<String, Object>(){{
                put("allocationStatus", allocationStatus);
            }}, "empId=" + this.empId);

            this.allocationStatus = allocationStatus;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
