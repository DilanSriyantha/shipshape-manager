package org.devdynamos.interfaces;

import org.devdynamos.models.Employee;

public interface InsertEmployeeCallback {
    void executeInsertion(Employee employee);
}
