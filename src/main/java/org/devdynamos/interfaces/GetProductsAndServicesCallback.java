package org.devdynamos.interfaces;

import org.devdynamos.contollers.CashierDashboardController;
import org.devdynamos.models.Service;
import org.devdynamos.models.SparePart;

import java.util.List;

public interface GetProductsAndServicesCallback {
    void onSuccess(CashierDashboardController.GetProductsAndServicesResultSet resultSet);
    void onFailed(Exception ex);
}
