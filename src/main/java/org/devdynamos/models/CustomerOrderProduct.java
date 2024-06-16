package org.devdynamos.models;

public class CustomerOrderProduct {
    private int customerOrderProductId;
    private int customerOrderId;
    private int sparePartId;
    private int quantity;

    public CustomerOrderProduct() {}

    public CustomerOrderProduct(int customerOrderProductId, int customerOrderId, int sparePartId, int quantity) {
        this.customerOrderProductId = customerOrderProductId;
        this.customerOrderId = customerOrderId;
        this.sparePartId = sparePartId;
        this.quantity = quantity;
    }

    public int getCustomerOrderProductId() {
        return customerOrderProductId;
    }

    public void setCustomerOrderProductId(int customerOrderProductId) {
        this.customerOrderProductId = customerOrderProductId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object[] toObjectArray() {
        return new Object[] { customerOrderId, sparePartId, quantity };
    }
}
