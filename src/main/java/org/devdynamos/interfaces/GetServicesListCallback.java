package org.devdynamos.interfaces;

import org.devdynamos.models.Service;

import java.util.List;

public interface GetServicesListCallback {
    void onSuccess(List<Service> serviceList);
    void onFailed(Exception ex);
}
