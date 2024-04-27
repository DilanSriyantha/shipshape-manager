package org.devdynamos.model;

public class Employee {
    private String mEmpID;
    private String mEmpName;
    private String mEmpAddress;
    private String mEmpPosition;

    public Employee(String mEmpID, String mEmpName, String mEmpAddress, String mEmpPosition) {
        this.mEmpID = mEmpID;
        this.mEmpName = mEmpName;
        this.mEmpAddress = mEmpAddress;
        this.mEmpPosition = mEmpPosition;
    }

    public String getmEmpID() {
        return mEmpID;
    }

    public void setmEmpID(String mEmpID) {
        this.mEmpID = mEmpID;
    }

    public String getmEmpName() {
        return mEmpName;
    }

    public void setmEmpName(String mEmpName) {
        this.mEmpName = mEmpName;
    }

    public String getmEmpAddress() {
        return mEmpAddress;
    }

    public void setmEmpAddress(String mEmpAddress) {
        this.mEmpAddress = mEmpAddress;
    }

    public String getmEmpPosition() {
        return mEmpPosition;
    }

    public void setmEmpPosition(String mEmpPosition) {
        this.mEmpPosition = mEmpPosition;
    }
}
