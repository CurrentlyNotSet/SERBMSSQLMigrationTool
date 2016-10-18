/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author User
 */
public class hearingTypeModel {
    
    private int id;
    private boolean active;
    private String section;
    private String hearingType;
    private String hearingDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getHearingType() {
        return hearingType;
    }

    public void setHearingType(String hearingType) {
        this.hearingType = hearingType;
    }

    public String getHearingDescription() {
        return hearingDescription;
    }

    public void setHearingDescription(String hearingDescription) {
        this.hearingDescription = hearingDescription;
    }
    
    
}
