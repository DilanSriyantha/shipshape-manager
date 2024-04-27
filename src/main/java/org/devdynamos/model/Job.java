package org.devdynamos.model;

public class Job {
    private String mJobID;
    private String mJobTitle;
    private String mJobDesc;
    private String mShipName;
    private String mLocation;
    private String mDate;

    public Job(String mJobID, String mJobTitle, String mJobDesc, String mShipName, String mLocation, String mDate) {
        this.mJobID = mJobID;
        this.mJobTitle = mJobTitle;
        this.mJobDesc = mJobDesc;
        this.mShipName = mShipName;
        this.mLocation = mLocation;
        this.mDate = mDate;
    }

    public String getmJobID() {
        return mJobID;
    }

    public void setmJobID(String mJobID) {
        this.mJobID = mJobID;
    }

    public String getmJobTitle() {
        return mJobTitle;
    }

    public void setmJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getmJobDesc() {
        return mJobDesc;
    }

    public void setmJobDesc(String mJobDesc) {
        this.mJobDesc = mJobDesc;
    }

    public String getmShipName() {
        return mShipName;
    }

    public void setmShipName(String mShipName) {
        this.mShipName = mShipName;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public class Allocation {
        private Employee employee;
        private String mTask;

        public Allocation(Employee employee, String mTask) {
            this.employee = employee;
            this.mTask = mTask;
        }

        public Employee getEmployee() {
            return employee;
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        public String getmTask() {
            return mTask;
        }

        public void setmTask(String mTask) {
            this.mTask = mTask;
        }
    }
}
