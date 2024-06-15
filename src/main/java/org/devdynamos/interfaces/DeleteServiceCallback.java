package org.devdynamos.interfaces;

public interface DeleteServiceCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
