package org.devdynamos.models;

import org.devdynamos.utils.ArrayUtils;

import java.util.*;

public class Service {
    private int serviceId;
    private String serviceName;
    private double unitPrice;
    private Date dateCreated;
    private List<RequiredSkill> requiredSkills = new ArrayList<>();

    public Service() {}

    public Service(String serviceName, double unitPrice) {
        this.serviceName = serviceName;
        this.unitPrice = unitPrice;
    }

    public Service(int serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Service(int serviceId){
        this.serviceId = serviceId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public List<RequiredSkill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<RequiredSkill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public void addSkill(RequiredSkill requiredSkill){
        requiredSkills.add(requiredSkill);
    }

    public void removeSkill(int index){
        requiredSkills.remove(index);
    }

    public int getMaxSkillId() {
        RequiredSkill requiredSkill = ArrayUtils.find(requiredSkills, (lastElement, element) -> {
            if(element.getRequiredSkillId() > lastElement.getRequiredSkillId())
                return element;
            return null;
        });

        return requiredSkill.getRequiredSkillId();
    }

    public Object[] toObjectArray() {
        return new Object[] { serviceId, serviceName, unitPrice };
    }

    public Object[][] getSkillsAs2dObjectsArray() {
        if(requiredSkills.isEmpty()) return new Object[0][];

        Object[][] _requiredSkills = new Object[requiredSkills.size()][2];
        for (int i = 0; i < requiredSkills.size(); i++) {
            for (int j = 0; j < 2; j++) {
                switch (j){
                    case 0 -> _requiredSkills[i][j] = requiredSkills.get(i).getServiceId();
                    case 1 -> _requiredSkills[i][j] = requiredSkills.get(i).getRequiredSkillDescription();
                }
            }
        }

        return _requiredSkills;
    }

    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("serviceName", serviceName);
            put("unitPrice", unitPrice);
        }};
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", unitPrice=" + unitPrice +
                ", dateCreated=" + dateCreated +
                ", requiredSkills=" + requiredSkills.size() +
                '}';
    }
}
