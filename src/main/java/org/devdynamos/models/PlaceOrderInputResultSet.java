package org.devdynamos.models;

import java.util.Date;

public class PlaceOrderInputResultSet {
    private int qty;
    private String expectedDeliveryDate;

    public PlaceOrderInputResultSet(int qty, String expectedDeliveryDate) {
        this.qty = qty;
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}
