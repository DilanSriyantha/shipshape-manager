package org.devdynamos.models;

import java.util.HashMap;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactNumber;
    private String email;

    public Supplier() {}

    public Supplier(int supplierId, String supplierName, String contactNumber, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.email = email;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object[] toObjectArray() {
        Object[] values = { this.supplierName, this.contactNumber, this.email };

        return values;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> supplierHashMap = new HashMap<>(){{
            put("supplierId", supplierId);
            put("supplierName", supplierName);
            put("contactNumber", contactNumber);
            put("email", email);
        }};

        return supplierHashMap;
    }
}
