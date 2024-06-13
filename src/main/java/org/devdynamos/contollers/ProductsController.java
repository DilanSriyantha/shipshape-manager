package org.devdynamos.contollers;

import org.devdynamos.interfaces.SendOrderPlacementCallback;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.Console;

public class ProductsController {
    private final InventoryController inventoryController = new InventoryController();

    public void placeOrder(SparePart sparePart, String expectedDate, int requiredQuantity, SendOrderPlacementCallback callback){
        Thread insertionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final int id = inventoryController.insertSparePart(sparePart.toObjectArray());
                sparePart.setPartId(id);
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    insertionThread.join(); // wait for the insertion thread to complete its work, cuz its result id should be the partId of the spare part object goes to order placing method.
                    inventoryController.sendOrderPlacementToTheSupplier(
                            sparePart,
                            expectedDate,
                            requiredQuantity,
                            callback
                    );

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        insertionThread.start();
        waitingThread.start();
    }
}
