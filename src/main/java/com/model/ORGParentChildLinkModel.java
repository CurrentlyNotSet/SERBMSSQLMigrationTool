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
public class ORGParentChildLinkModel {
    private int id;
    private boolean active;
    private String parentOrgNumber;
    private String childOrgNumber;

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

    public String getParentOrgNumber() {
        return parentOrgNumber;
    }

    public void setParentOrgNumber(String parentOrgNumber) {
        this.parentOrgNumber = parentOrgNumber;
    }

    public String getChildOrgNumber() {
        return childOrgNumber;
    }

    public void setChildOrgNumber(String childOrgNumber) {
        this.childOrgNumber = childOrgNumber;
    }
    
    
}
