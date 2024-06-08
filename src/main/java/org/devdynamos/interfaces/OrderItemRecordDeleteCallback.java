package org.devdynamos.interfaces;

import org.devdynamos.models.OrderItem;

@FunctionalInterface
public interface OrderItemRecordDeleteCallback {
    void execute(OrderItem orderItem);
}
