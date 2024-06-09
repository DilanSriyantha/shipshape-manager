package org.devdynamos.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    private int orderId;
    private String orderCaption;
    private int supplierId;
    private int sparePartId;
    private int quantity;
    private String expectedDeliveryDate;
    private Date placedDate;

    public Order(String orderCaption, int supplierId, int sparePartId, int quantity, String expectedDeliveryDate) {
        this.orderCaption = orderCaption;
        this.supplierId = supplierId;
        this.sparePartId = sparePartId;
        this.quantity = quantity;
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCaption() {
        return orderCaption;
    }

    public void setOrderCaption(String orderCaption) {
        this.orderCaption = orderCaption;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public Object[] toObjectArray() {
        return new Object[] {
                orderCaption,
                supplierId,
                sparePartId,
                quantity,
                expectedDeliveryDate
        };
    }
}
