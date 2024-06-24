package org.devdynamos.models;

import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.util.Date;

public class ReportRecord {
    private long id;
    private String caption;
    private int quantity;
    private double total;
    private LocalDateTime datePlaced;

    public ReportRecord() {}

    public ReportRecord(long id, String caption, int quantity, double total, LocalDateTime datePlaced) {
        this.id = id;
        this.caption = caption;
        this.quantity = quantity;
        this.total = total;
        this.datePlaced = datePlaced;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getQuantity() {
        return quantity;
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

    public LocalDateTime getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(LocalDateTime datePlaced) {
        this.datePlaced = datePlaced;
    }

    @Override
    public String toString() {
        return "ReportRecord{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", datePlaced=" + datePlaced +
                '}';
    }
}
