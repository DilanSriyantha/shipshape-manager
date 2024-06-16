package org.devdynamos.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class HistoryOrder {
    private int customerOrderId;
    private String customerOrderCaption;
    private String customerName;
    private double discountRate;
    private double vatRate;
    private double serviceChargeRate;
    private double total;
    private double grandTotal;
    private double paidAmount;
    private double balanceAmount;
    private LocalDateTime datePlaced;
    private List<OrderProduct> productList;
    private List<OrderService> servicesList;

    public HistoryOrder() {}

    public HistoryOrder(int customerOrderId, String customerOrderCaption, String customerName, double discountRate, double vatRate, double serviceChargeRate, double total, double grandTotal, double paidAmount, double balanceAmount, LocalDateTime datePlaced) {
        this.customerOrderId = customerOrderId;
        this.customerOrderCaption = customerOrderCaption;
        this.customerName = customerName;
        this.discountRate = discountRate;
        this.vatRate = vatRate;
        this.serviceChargeRate = serviceChargeRate;
        this.total = total;
        this.grandTotal = grandTotal;
        this.paidAmount = paidAmount;
        this.balanceAmount = balanceAmount;
        this.datePlaced = datePlaced;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public String getCustomerOrderCaption() {
        return customerOrderCaption;
    }

    public void setCustomerOrderCaption(String customerOrderCaption) {
        this.customerOrderCaption = customerOrderCaption;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(double serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public LocalDateTime getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(LocalDateTime datePlaced) {
        this.datePlaced = datePlaced;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public void addProduct(OrderProduct product){
        productList.add(product);
    }

    public List<OrderService> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<OrderService> servicesList) {
        this.servicesList = servicesList;
    }

    public void addService(OrderService service){
        servicesList.add(service);
    }
}
