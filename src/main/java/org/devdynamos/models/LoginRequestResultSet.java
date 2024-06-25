package org.devdynamos.models;

public class LoginRequestResultSet<T> {
    private T authObject;

    public LoginRequestResultSet() {}

    public T getAuthObject() {
        return authObject;
    }

    public void setAuthObject(T authObject) {
        this.authObject = authObject;
    }
}
