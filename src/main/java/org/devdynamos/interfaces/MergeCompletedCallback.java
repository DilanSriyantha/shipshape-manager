package org.devdynamos.interfaces;

public interface MergeCompletedCallback {
    void onSuccess();
    void onFailed(Exception ex);
}
