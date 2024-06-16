package org.devdynamos.interfaces;

public interface PlaceOrderCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
