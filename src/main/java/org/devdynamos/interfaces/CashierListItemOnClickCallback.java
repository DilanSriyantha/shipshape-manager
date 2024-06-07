package org.devdynamos.interfaces;

import org.devdynamos.models.SparePart;

@FunctionalInterface
public interface CashierListItemOnClickCallback {
    void execute(SparePart sparePart);
}
