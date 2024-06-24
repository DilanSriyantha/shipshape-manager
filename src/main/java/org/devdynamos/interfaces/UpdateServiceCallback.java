package org.devdynamos.interfaces;

public interface UpdateServiceCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
