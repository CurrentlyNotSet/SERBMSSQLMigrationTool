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
public class NextCaseNumberModel {
    
    private int id;
    private boolean active;
    private String caseType;
    private String caseYear;
    private String nextCaseNumber;

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

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(String caseYear) {
        this.caseYear = caseYear;
    }

    public String getNextCaseNumber() {
        return nextCaseNumber;
    }

    public void setNextCaseNumber(String nextCaseNumber) {
        this.nextCaseNumber = nextCaseNumber;
    }
    
    
    
}
