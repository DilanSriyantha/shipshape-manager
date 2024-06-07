package org.devdynamos.contollers;

import org.devdynamos.models.OrderItem;
import org.devdynamos.models.SparePart;

import java.util.ArrayList;
import java.util.List;

public class CashierDashboardController {
    private final List<OrderItem> orderItemRecords;
    private final InventoryController inventoryController;

    public CashierDashboardController(){
        this.inventoryController = new InventoryController();
        this.orderItemRecords = new ArrayList<>();
    }

    public List<SparePart> getSpareParts() {
        return inventoryController.getSparePartsList();
    }

    public void addRecord(OrderItem orderItem){
        this.orderItemRecords.add(orderItem);
    }

    public List<OrderItem> getOrderItemRecords(){
        return this.orderItemRecords;
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem orderItem : this.orderItemRecords){
            total += orderItem.getTotal();
        }

        return total;
    }
}
