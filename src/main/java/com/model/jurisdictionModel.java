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
public class jurisdictionModel {
    
    private int id;
    private boolean active;
    private String jurisCode;
    private String jurisName;
    private String employerType;

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
 
    public String getJurisCode() {
        return jurisCode;
    }

    public void setJurisCode(String jurisCode) {
        this.jurisCode = jurisCode;
    }

    public String getJurisName() {
        return jurisName;
    }

    public void setJurisName(String jurisName) {
        this.jurisName = jurisName;
    }

    public String getEmployerType() {
        return employerType;
    }

    public void setEmployerType(String employerType) {
        this.employerType = employerType;
    }
    
}
