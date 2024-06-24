package org.devdynamos.models;

import java.util.Date;
import java.util.HashMap;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactNumber;
    private String supplierEmail;
    private Date date;

    public Supplier() {}

    public Supplier(int supplierId, String supplierName, String contactNumber, String supplierEmail) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.supplierEmail = supplierEmail;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return supplierEmail;
    }

    public void setEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Object[] toObjectArray() {
        Object[] values = { this.supplierName, this.contactNumber, this.supplierEmail };

        return values;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> supplierHashMap = new HashMap<>(){{
            put("supplierId", supplierId);
            put("supplierName", supplierName);
            put("contactNumber", contactNumber);
            put("supplierEmail", supplierEmail);
        }};

        return supplierHashMap;
    }
}
