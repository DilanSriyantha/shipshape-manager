package org.devdynamos.models;

public class OrderService {
    private int serviceId;
    private String serviceName;
    private int quantity;

    public OrderService() {}

    public OrderService(int serviceId, String serviceName, int quantity) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
