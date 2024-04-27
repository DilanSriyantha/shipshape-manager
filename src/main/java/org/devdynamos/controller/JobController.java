package org.devdynamos.controller;

import org.devdynamos.model.Employee;
import org.devdynamos.model.Job;

import java.util.ArrayList;

public class JobController {
    private ArrayList<Job> jobList = new ArrayList<Job>();

    public ArrayList<Job> getJobList() {
        return jobList;
    }

    public void setJobList(ArrayList<Job> jobList) {
        this.jobList = jobList;
    }

    public Job addJob(String jobID, String jobTItle, String desc, String shipName, String location, String date) {
        Job job = new Job(jobID, jobTItle, desc, shipName, location, date);
        jobList.add(job);
        return job;
    }
}
