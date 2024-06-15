package org.devdynamos.models;

public class RequiredSkill {
    private int requiredSkillId;
    private int serviceId;
    private String requiredSkillDescription;

    public RequiredSkill() {}

    public RequiredSkill(int serviceId, String requiredSkillDescription) {
        this.serviceId = serviceId;
        this.requiredSkillDescription = requiredSkillDescription;
    }

    public RequiredSkill(int requiredSkillId, int serviceId, String requiredSkillDescription){
        this.requiredSkillId = requiredSkillId;
        this.serviceId = serviceId;
        this.requiredSkillDescription = requiredSkillDescription;
    }

    public int getRequiredSkillId() {
        return requiredSkillId;
    }

    public void setRequiredSkillId(int requiredSkillId) {
        this.requiredSkillId = requiredSkillId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getRequiredSkillDescription() {
        return requiredSkillDescription;
    }

    public void setRequiredSkillDescription(String requiredSkillDescription) {
        this.requiredSkillDescription = requiredSkillDescription;
    }

    @Override
    public String toString() {
        return requiredSkillDescription;
    }
}
