package org.devdynamos.interfaces;

import org.devdynamos.models.LoginRequestResultSet;

public interface LoginRequestCallback<T> {
    void onSuccess(LoginRequestResultSet<T> resultSet);
    void onFailed(Exception ex);
}
