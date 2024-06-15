package org.devdynamos.models;

import com.google.api.client.util.DateTime;

import java.util.List;

public class CustomerOrder {
    private int customerOrderId;
    private int customerOrderCaption;
    private int customerId;
    private float discountRate;
    private float vatRate;
    private float serviceChargeRate;
    private DateTime datePlaced;
    private List<SparePart> productsList;
    private List<Service> servicesList;

    CustomerOrder() {}

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getCustomerOrderCaption() {
        return customerOrderCaption;
    }

    public void setCustomerOrderCaption(int customerOrderCaption) {
        this.customerOrderCaption = customerOrderCaption;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }

    public float getVatRate() {
        return vatRate;
    }

    public void setVatRate(float vatRate) {
        this.vatRate = vatRate;
    }

    public float getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(float serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public DateTime getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(DateTime datePlaced){
        this.datePlaced = datePlaced;
    }

    public List<SparePart> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<SparePart> productsList) {
        this.productsList = productsList;
    }

    public void addProduct(SparePart product){
        productsList.add(product);
    }

    public List<Service> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<Service> servicesList) {
        this.servicesList = servicesList;
    }

    public void addService(Service service){
        servicesList.add(service);
    }
}
