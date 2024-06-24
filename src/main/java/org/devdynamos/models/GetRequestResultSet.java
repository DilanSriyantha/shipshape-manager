package org.devdynamos.models;

import java.util.List;

public class GetRequestResultSet<T> {
    private List<T> resultList;

    public GetRequestResultSet () {}

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
