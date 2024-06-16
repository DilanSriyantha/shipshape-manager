package org.devdynamos.models;

public class CustomerOrderService {
    private int customerOrderServiceId;
    private int customerOrderId;
    private int serviceId;
    private int quantity;

    public CustomerOrderService() {}

    public CustomerOrderService(int customerOrderServiceId, int customerOrderId, int serviceId, int quantity) {
        this.customerOrderServiceId = customerOrderServiceId;
        this.customerOrderId = customerOrderId;
        this.serviceId = serviceId;
        this.quantity = quantity;
    }

    public int getCustomerOrderServiceId() {
        return customerOrderServiceId;
    }

    public void setCustomerOrderServiceId(int customerOrderServiceId) {
        this.customerOrderServiceId = customerOrderServiceId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object[] toObjectArray() {
        return new Object[] { customerOrderId, serviceId, quantity };
    }
}
