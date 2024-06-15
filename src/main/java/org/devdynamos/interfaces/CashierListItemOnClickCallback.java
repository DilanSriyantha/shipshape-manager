package org.devdynamos.interfaces;

import org.devdynamos.models.SparePart;

@FunctionalInterface
public interface CashierListItemOnClickCallback<T> {
    void execute(T object);
}
