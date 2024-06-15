package org.devdynamos.utils;

import org.devdynamos.models.RequiredSkill;

import javax.swing.*;

public class RequiredSkillsListModel extends DefaultListModel<RequiredSkill> {
    public int insertBatch() {
        String[] columns = { "serviceId", "requiredSkillDescription" };
        Object[][] values = new Object[this.size()][2];

        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < 2; j++) {
                switch (j){
                    case 0 -> values[i][j] = this.get(i).getServiceId();
                    case 1 -> values[i][j] = this.get(i).getRequiredSkillDescription();
                }
            }
        }

        try{
            DBManager.insertBatch("requiredSkills", columns, values);
            return 0;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;
    }

    public void setServiceIdBatch(int serviceId){
        for (int i = 0; i < this.size(); i++) {
            this.get(i).setServiceId(serviceId);
        }
    }
}
