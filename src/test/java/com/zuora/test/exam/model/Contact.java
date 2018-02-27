package com.zuora.test.exam.model;

import java.util.List;

import com.google.gson.JsonObject;

public class Contact extends BaseModel {

    private Long id;

    private String type;

    private List<String> tags;

    private Integer lead_score;

    private Long contact_company_id;

    private Short star_value;

    private List<JsonObject> properties;

    private List<JsonObject> campaignStatus;

    private List<JsonObject> unsubscribeStatus;

    private List<JsonObject> emailBounceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getLead_score() {
        return lead_score;
    }

    public void setLead_score(Integer lead_score) {
        this.lead_score = lead_score;
    }

    public Long getContact_company_id() {
        return contact_company_id;
    }

    public void setContact_company_id(Long contact_company_id) {
        this.contact_company_id = contact_company_id;
    }

    public Short getStar_value() {
        return star_value;
    }

    public void setStar_value(Short star_value) {
        this.star_value = star_value;
    }

    public List<JsonObject> getProperties() {
        return properties;
    }

    public void setProperties(List<JsonObject> properties) {
        this.properties = properties;
    }

    public List<JsonObject> getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(List<JsonObject> campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public List<JsonObject> getUnsubscribeStatus() {
        return unsubscribeStatus;
    }

    public void setUnsubscribeStatus(List<JsonObject> unsubscribeStatus) {
        this.unsubscribeStatus = unsubscribeStatus;
    }

    public List<JsonObject> getEmailBounceStatus() {
        return emailBounceStatus;
    }

    public void setEmailBounceStatus(List<JsonObject> emailBounceStatus) {
        this.emailBounceStatus = emailBounceStatus;
    }

}
