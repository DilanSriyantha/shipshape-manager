package org.devdynamos.models;

import com.google.api.client.util.DateTime;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrder {
    private int customerOrderId;
    private String customerOrderCaption;
    private int customerId;
    private double discountRate;
    private double vatRate;
    private double serviceChargeRate;
    private double total;
    private double grandTotal;
    private double paidAmount;
    private double balanceAmount;
    private DateTime datePlaced;
    private List<CustomerOrderProduct> productsList;
    private List<CustomerOrderService> servicesList;

    public CustomerOrder() {
        this.productsList = new ArrayList<>();
        this.servicesList = new ArrayList<>();
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
        this.total = (double) Math.round(total * 100) / 100;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = (double) Math.round(grandTotal * 100) / 100;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = (double) Math.round(paidAmount * 100) / 100;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = (double) Math.round(balanceAmount * 100) / 100;
    }

    public DateTime getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(DateTime datePlaced){
        this.datePlaced = datePlaced;
    }

    public List<CustomerOrderProduct> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<CustomerOrderProduct> productsList) {
        this.productsList = productsList;
    }

    public void addProduct(CustomerOrderProduct product){
        productsList.add(product);
    }

    public void removeProduct(CustomerOrderProduct product) {
        productsList.removeIf((_product) -> _product.getCustomerOrderProductId() == product.getCustomerOrderProductId());
    }

    public List<CustomerOrderService> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<CustomerOrderService> servicesList) {
        this.servicesList = servicesList;
    }

    public void addService(CustomerOrderService service){
        servicesList.add(service);
    }

    public void removeService(CustomerOrderService service){
        servicesList.removeIf((_service) -> _service.getCustomerOrderServiceId() == service.getCustomerOrderServiceId());
    }

    public Object[] toObjectArray() {
        return new Object[] { customerOrderId, customerOrderCaption, customerId, discountRate, vatRate, serviceChargeRate, total, grandTotal, paidAmount, balanceAmount };
    }

    public Object[][] getOrderedServices2dArray() {
        Object[][] orderedServices = new Object[servicesList.size()][3];

        for (int i = 0; i < servicesList.size(); i++) {
            for (int j = 0; j < 3; j++) {
                switch (j){
                    case 0 -> orderedServices[i][j] = servicesList.get(i).getCustomerOrderId();
                    case 1 -> orderedServices[i][j] = servicesList.get(i).getServiceId();
                    case 2 -> orderedServices[i][j] = servicesList.get(i).getQuantity();
                    default -> throw new IllegalArgumentException("invalid column index");
                }
            }
        }

        return orderedServices;
    }

    public Object[][] getOrderedProducts2dArray() {
        Object[][] orderedProducts = new Object[productsList.size()][3];

        for (int i = 0; i < productsList.size(); i++) {
            for (int j = 0; j < 3; j++) {
                switch (j){
                    case 0 -> orderedProducts[i][j] = productsList.get(i).getCustomerOrderId();
                    case 1 -> orderedProducts[i][j] = productsList.get(i).getSparePartId();
                    case 2 -> orderedProducts[i][j] = productsList.get(i).getQuantity();
                    default -> throw new IllegalArgumentException("invalid column index");
                }
            }
        }

        return orderedProducts;
    }
}
