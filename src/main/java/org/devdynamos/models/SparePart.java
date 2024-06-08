package org.devdynamos.models;

import java.util.HashMap;

public class SparePart {
    private int partId;
    private int supplierId;
    private String supplierName;
    private String partName;
    private double receivedPrice;
    private double sellingPrice;
    private int quantity;
    private boolean onShip;
    private boolean topSeller;

    public SparePart() {}

    public SparePart(int partId, int supplierId, String supplierName, String partName, double receivedPrice, double sellingPrice, int quantity, boolean onShip, boolean topSeller) {
        this.partId = partId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.partName = partName;
        this.receivedPrice = receivedPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.onShip = onShip;
        this.topSeller = topSeller;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
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

    public String getName() {
        return partName;
    }

    public void setName(String name) {
        this.partName = name;
    }

    public double getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(double receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isOnShip() {
        return onShip;
    }

    public void setOnShip(boolean onShip) {
        this.onShip = onShip;
    }

    public boolean isTopSeller() {
        return topSeller;
    }

    public void setTopSeller(boolean topSeller) {
        this.topSeller = topSeller;
    }

    @Override
    public String toString() {
        return "SparePart{" +
                "partId=" + partId +
                ", supplierId=" + supplierId +
                ", name='" + partName + '\'' +
                ", receivedPrice=" + receivedPrice +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", onShip=" + onShip +
                ", topSeller=" + topSeller +
                '}';
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> sparePartHashMap = new HashMap<>(){{
            put("partId", partId);
            put("supplierId", supplierId);
            put("partName", partName);
            put("receivedPrice", receivedPrice);
            put("sellingPrice", sellingPrice);
            put("quantity", quantity);
            put("onShip", onShip);
            put("topSeller", topSeller);
        }};

        return sparePartHashMap;
    }

    public Object[] toObjectArray() {
        return new Object[] {
                this.supplierId,
                this.partName,
                this.receivedPrice,
                this.sellingPrice,
                this.quantity
        };
    }
}
