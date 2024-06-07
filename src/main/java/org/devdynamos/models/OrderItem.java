package org.devdynamos.models;

public class OrderItem {
    private int recordId;
    private int orderId;
    private String name;
    private double unitPrice;
    private int quantity;
    private double total;

    public OrderItem(int recordId, int orderId, String name, double unitPrice, int quantity, double total) {
        this.recordId = recordId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
