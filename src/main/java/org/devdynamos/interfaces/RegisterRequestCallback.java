package org.devdynamos.interfaces;

import org.devdynamos.models.LoginRequestResultSet;

public interface RegisterRequestCallback<T> {
    void onSuccess(LoginRequestResultSet<T> authObject);
    void onFailed(Exception ex);
}
