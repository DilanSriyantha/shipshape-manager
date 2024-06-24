package org.devdynamos.models;

import java.util.Date;
import java.util.HashMap;

public class Customer {
    private int customerId;
    private String customerName;
    private String contactNumber;
    private String email;
    private Date registeredDate;

    public Customer() {}

    public Customer(int customerId, String customerName, String contactNumber, String email, Date registeredDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.registeredDate = registeredDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Object[] toObjectArray() {
        return new Object[] { customerName, contactNumber, email };
    }

    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("customerName", customerName);
            put("contactNumber", contactNumber);
            put("email", email);
        }};
    }
}
