package org.devdynamos.listModels;

import org.devdynamos.models.Skill;
import org.devdynamos.utils.DBManager;

import javax.swing.*;

public class SkillsListModel extends DefaultListModel<Skill> {
    public void insertBatch(){
        String[] columns = { "empId", "skillDescription" };
        Object[][] values = new Object[this.size()][2];

        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0 -> values[i][j] = this.get(i).getEmpId();
                    case 1 -> values[i][j] = this.get(i).getSkillDescription();
                }
            }
        }

        try{
            DBManager.insertBatch("skills", columns, values);

            JOptionPane.showMessageDialog(null, "Skills insertion completed.", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEmpIdBatch(int empId){
        for(int i = 0; i < this.size(); i++){
            this.get(i).setEmpId(empId);
        }
    }
}
