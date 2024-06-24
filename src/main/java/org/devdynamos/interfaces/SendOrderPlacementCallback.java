package org.devdynamos.interfaces;

public interface SendOrderPlacementCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
