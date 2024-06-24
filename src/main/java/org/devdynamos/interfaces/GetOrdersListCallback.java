package org.devdynamos.interfaces;

import org.devdynamos.contollers.OrderHistoryController;
import org.devdynamos.models.HistoryOrder;

import java.util.List;

public interface GetOrdersListCallback {
    void onSuccess(List<HistoryOrder> orderList);
    void onFailed(Exception ex);
}
