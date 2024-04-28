package org.devdynamos.controller;

import org.devdynamos.model.Employee;
import org.devdynamos.model.Job;

import java.util.ArrayList;

public class AllocationController {
    public Job.Allocation createAllocation(Job job, Employee emp, String task) {
        Job.Allocation allocation = job.new Allocation(emp, task);
        return allocation;
    }
}
