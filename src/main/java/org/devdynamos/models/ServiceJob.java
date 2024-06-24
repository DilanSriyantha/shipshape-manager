package org.devdynamos.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ServiceJob {
    private int serviceJobId;
    private int customerOrderId;
    private String customerOrderCaption;
    private int serviceId;
    private String serviceName;
    private boolean finished;
    private Date dateCreated;
    private List<RequiredSkill> requiredSkills;
    private List<Employee> allocatedEmployees;

    public ServiceJob() {}

    public int getServiceJobId() {
        return serviceJobId;
    }

    public void setServiceJobId(int serviceJobId) {
        this.serviceJobId = serviceJobId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public String getCustomerOrderCaption() {
        return customerOrderCaption;
    }

    public void setCustomerOrderCaption(String customerOrderCaption) {
        this.customerOrderCaption = customerOrderCaption;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<RequiredSkill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<RequiredSkill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<Employee> getAllocatedEmployees() {
        return allocatedEmployees;
    }

    public void setAllocatedEmployees(List<Employee> allocatedEmployees) {
        this.allocatedEmployees = allocatedEmployees;
    }

    @Override
    public String toString() {
        return "ServiceJob{" +
                "serviceJobId=" + serviceJobId +
                ", customerOrderId=" + customerOrderId +
                ", customerOrderCaption='" + customerOrderCaption + '\'' +
                ", serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", finished=" + finished +
                ", dateCreated=" + dateCreated +
                ", requiredSkills=" + requiredSkills +
                ", allocatedEmployees=" + allocatedEmployees +
                '}';
    }

    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("customerOrderId", customerOrderId);
            put("serviceId", serviceId);
            put("finished", finished);
        }};
    }
}
