package org.devdynamos.models;

import org.devdynamos.utils.ArrayUtils;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Employee {
    private int empId;
    private String empName;
    private String email;
    private String contactNumber;
    private String jobRole;
    private boolean availability;
    private String workArea;
    private boolean allocationStatus;
    private Date registeredDate;
    private List<Skill> skills;

    public Employee() {}

    public Employee(int empId, String empName, String email, String contactNumber, String jobRole, boolean availability, String workArea, boolean allocationStatus) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.jobRole = jobRole;
        this.availability = availability;
        this.workArea = workArea;
        this.allocationStatus = allocationStatus;
        this.skills = DBManager.get(Skill.class, "skills", "empId=" + this.empId);
    }

    public int insert(){
        String[] columns = { "empId", "empName", "email", "contactNumber", "jobRole", "workArea" };
        Object[] values = { this.empId, this.empName, this.email, this.contactNumber, this.jobRole, this.workArea };
        final int id = DBManager.insert("employees", columns, values);
        if(id > -1){
            this.empId = id;
        }

        return id;
    }

    public void update() {
        HashMap<String, Object> newValues = new HashMap<>(){{
            put("empId", empId);
            put("empName", empName);
            put("email", email);
            put("contactNumber", contactNumber);
            put("jobRole", jobRole);
            put("workArea", workArea);
        }};
        DBManager.update("employees", newValues, "empId=" + this.empId);

        JOptionPane.showMessageDialog(null, empName + " is updated!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void delete(){
        DBManager.delete("employees", "empId=" + this.empId);
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public boolean isAllocated() {
        return allocationStatus;
    }

    public void setAllocationStatus(boolean allocationStatus) {
        try{
            System.out.println(allocationStatus);
            DBManager.update("employees", new HashMap<String, Object>(){{
                put("allocationStatus", allocationStatus);
            }}, "empId=" + this.empId);

            this.allocationStatus = allocationStatus;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate){
        this.registeredDate = registeredDate;
    }

    public List<Skill> getSkills() {
        if(skills == null){
            skills = DBManager.get(Skill.class, "skills", "empId=" + this.empId);
        }

        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill){
        final int id = DBManager.insert("skills", new String[]{ "empId", "skillDescription" }, new Object[]{ this.empId, skill.getSkillDescription() });
        skill.setSkillId(id);

        System.out.println(id);

        this.skills.add(skill);
    }

    public void removeSkill(Skill skill){
        this.skills.remove(skill);
    }

    public void removeSkillAt(int index){
        this.skills.remove(index);
    }

    public void removeSkill(int skillId){
        this.skills.removeIf((skill) -> {
            return skill.getSkillId() == skillId;
        });

        DBManager.delete("skills", "skillId="+skillId);
    }

    public int getMaxSkillId(){
        Skill sk = ArrayUtils.find(this.skills, (lastElement, element) -> {
            if(element.getSkillId() > lastElement.getSkillId()){
                return element;
            }
            return null;
        });

        return sk.getSkillId();
    }
}
