package com.zuora.test.exam.model;

import java.util.List;

import com.zuora.test.exam.Mandatory;
import com.zuora.test.exam.NumRange;

public class Deal extends BaseModel {

    @Mandatory
    private Long id;

    @Mandatory
    private String name;

    private String description;

    @Mandatory
    private Double expected_value;

    @Mandatory
    private Long pipeline_id;

    @Mandatory
    private String milestone;

    @NumRange(min = 0, max = 100)
    private Integer probability;

    @Mandatory
    private Long close_date;

    private Long created_time;

    private String owner_id;

    private String prefs;

    private List<Contact> contacts;

    private List<String> contact_ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getExpected_value() {
        return expected_value;
    }

    public void setExpected_value(Double expected_value) {
        this.expected_value = expected_value;
    }

    public Long getPipeline_id() {
        return pipeline_id;
    }

    public void setPipeline_id(Long pipeline_id) {
        this.pipeline_id = pipeline_id;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Long getClose_date() {
        return close_date;
    }

    public void setClose_date(Long close_date) {
        this.close_date = close_date;
    }

    public Long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Long created_time) {
        this.created_time = created_time;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getPrefs() {
        return prefs;
    }

    public void setPrefs(String prefs) {
        this.prefs = prefs;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContact_ids() {
        return contact_ids;
    }

    public void setContact_ids(List<String> contact_ids) {
        this.contact_ids = contact_ids;
    }

}
