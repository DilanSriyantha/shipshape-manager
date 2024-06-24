package org.devdynamos.interfaces;

public interface InsertRequestCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
