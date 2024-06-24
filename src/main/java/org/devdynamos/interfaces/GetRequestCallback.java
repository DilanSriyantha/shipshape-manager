package org.devdynamos.interfaces;

import org.devdynamos.models.GetRequestResultSet;

public interface GetRequestCallback<T> {
    void onSuccess(GetRequestResultSet<T> resultSet);
    void onFailed(Exception ex);
}
