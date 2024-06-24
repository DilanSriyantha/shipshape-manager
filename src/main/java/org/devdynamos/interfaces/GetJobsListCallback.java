package org.devdynamos.interfaces;

import org.devdynamos.models.ServiceJob;

import java.util.List;

public interface GetJobsListCallback {
    void onSuccess(List<ServiceJob> jobs);
    void onFailed(Exception ex);
}
