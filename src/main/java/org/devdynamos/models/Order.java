package org.devdynamos.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Order {
    private int orderId;
    private String orderCaption;
    private int supplierId;
    private String supplierName;
    private int sparePartId;
    private String partName;
    private int quantity;
    private int currentQuantity;
    private double receivedPrice;
    private double sellingPrice;
    private String expectedDeliveryDate;
    private Date placedDate;
    private boolean delivered;

    public Order() {}

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getCurrentQuantity(){
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity){
        this.currentQuantity = currentQuantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getReceivedPrice(){
        return receivedPrice;
    }

    public void setReceivedPrice(double receivedPrice){
        this.receivedPrice = receivedPrice;
    }

    public double getSellingPrice(){
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered){
        this.delivered = delivered;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderCaption='" + orderCaption + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", sparePartId=" + sparePartId +
                ", partName='" + partName + '\'' +
                ", currentQuantity=" + currentQuantity +
                ", quantity=" + quantity +
                ", receivedPrice=" + receivedPrice +
                ", sellingPrice=" + sellingPrice +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", placedDate=" + placedDate +
                ", delivered=" + delivered +
                '}';
    }
}
