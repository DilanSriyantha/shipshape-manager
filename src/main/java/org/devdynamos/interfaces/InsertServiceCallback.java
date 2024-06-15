package org.devdynamos.interfaces;

public interface InsertServiceCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
