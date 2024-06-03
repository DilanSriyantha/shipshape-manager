package org.devdynamos.models;

import org.devdynamos.utils.DBManager;

import javax.swing.*;

public class Skill {
    private int skillId;
    private int empId;
    private String skillDescription;

    public Skill() {}

    public Skill(int skillId, int empId, String skillDescription) {
        this.skillId = skillId;
        this.empId = empId;
        this.skillDescription = skillDescription;
    }

    public void insert(DefaultListModel<Skill> defaultListModel){
        String[] columns = { "empId", "skillDescription" };
        Object[][] values = new Object[defaultListModel.size()][2];

        for (int i = 0; i < defaultListModel.size(); i++) {
            for (int j = 0; j < 2; j++) {
                if(j == 0) values[i][j] = defaultListModel.get(i).empId;
                else values[i][j] = defaultListModel.get(i).skillDescription;
            }
        }

        try{
            DBManager.insertBatch("skills", columns, values);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insert(Object[][] values){
        try{
            String[] columns = { "empId", "skillDescription" };
            DBManager.insertBatch("skills", columns, values);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    @Override
    public String toString() {
        return skillDescription;
    }
}
