package org.devdynamos.models;

import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.utils.DBManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SparePart {
    private int partId;
    private int supplierId;
    private String supplierName;
    private String supplierEmail;
    private String partName;
    private double receivedPrice;
    private double sellingPrice;
    private int currentQuantity;
    private int initialQuantity;
    private boolean onShip;
    private boolean topSeller;
    private Date date;

    public SparePart() {}

    public SparePart(int partId, int supplierId, String supplierName, String partName, double receivedPrice, double sellingPrice, int currentQuantity, int initialQuantity, boolean onShip, boolean topSeller) {
        this.partId = partId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.partName = partName;
        this.receivedPrice = receivedPrice;
        this.sellingPrice = sellingPrice;
        this.currentQuantity = currentQuantity;
        this.initialQuantity = initialQuantity;
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

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
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

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
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

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public String toString() {
        return "SparePart{" +
                "partId=" + partId +
                ", supplierId=" + supplierId +
                ", name='" + partName + '\'' +
                ", receivedPrice=" + receivedPrice +
                ", sellingPrice=" + sellingPrice +
                ", currentQuantity=" + currentQuantity +
                ", initialQuantity=" + initialQuantity +
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
            put("currentQuantity", currentQuantity);
            put("initialQuantity", initialQuantity);
            put("onShip", onShip);
            put("topSeller", topSeller);
        }};

        return sparePartHashMap;
    }

    public Object[] toObjectArray() {
        return new Object[] {
                supplierId,
                partName,
                receivedPrice,
                sellingPrice,
                currentQuantity,
                initialQuantity
        };
    }

    public Object[] toObjectArray_toReplace() {
        return new Object[] {
                partId,
                supplierId,
                partName,
                receivedPrice,
                sellingPrice,
                currentQuantity,
                initialQuantity
        };
    }

    public void predictOutOfStockDate(GetRequestCallback<LocalDate> callback) {
        GetRequestResultSet<LocalDate> resultSet = new GetRequestResultSet<>();
        AtomicReference<Throwable> predictionThreadException = new AtomicReference<>(null);
        Thread predict = new Thread(new Runnable() {
            @Override
            public void run() {
                double avgSalesRate = 0.0d;
                List<ReportRecord> salesRecords = new ArrayList<>();
                try {
                    salesRecords = DBManager.executeQuery(ReportRecord.class, "select * from (select (row_number() over (order by cop.customerOrderId)) as id, sp.partName as caption, cop.quantity, (cop.quantity * sp.sellingPrice) as total, co.datePlaced from customerorderproducts as cop join (select * from spareparts where partId=" + partId + ") as sp on cop.sparePartId = sp.partId join customerorders as co on cop.customerOrderId = co.customerOrderId) as recs;");

                    if (salesRecords.isEmpty())
                        avgSalesRate = 0.0d;
                    else {
                        int totalSold = salesRecords.stream().mapToInt(ReportRecord::getQuantity).sum();
                        LocalDateTime firstDate = salesRecords.getFirst().getDatePlaced();
                        LocalDateTime lastDate = salesRecords.getLast().getDatePlaced();
                        long daysBetween = ChronoUnit.DAYS.between(firstDate, lastDate);
                        if (daysBetween == 0)
                            avgSalesRate = totalSold;
                        else
                            avgSalesRate = (double) totalSold / daysBetween;
                    }

                    long daysToStockOut = (long) (currentQuantity / avgSalesRate);
                    LocalDate localDate = LocalDate.now().plusDays(daysToStockOut);

                    List<LocalDate> results = new ArrayList<>();
                    results.add(localDate);

                    resultSet.setResultList(results);
                }catch (Exception ex){
                    predictionThreadException.set(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    predict.join();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if(predictionThreadException.get() == null)
                    callback.onSuccess(resultSet);
                else
                    callback.onFailed((Exception) predictionThreadException.get());
            }
        });

        predict.start();
        waitingThread.start();
    }
}
