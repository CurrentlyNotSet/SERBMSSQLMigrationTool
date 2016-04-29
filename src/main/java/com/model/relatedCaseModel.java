/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Andrew
 */
public class relatedCaseModel {
    
    private int id;
    private String CaseYear;
    private String CaseType;
    private String CaseMonth;
    private String CaseNumber;
    private String relatedCaseNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseYear() {
        return CaseYear;
    }

    public void setCaseYear(String CaseYear) {
        this.CaseYear = CaseYear;
    }

    public String getCaseType() {
        return CaseType;
    }

    public void setCaseType(String CaseType) {
        this.CaseType = CaseType;
    }

    public String getCaseMonth() {
        return CaseMonth;
    }

    public void setCaseMonth(String CaseMonth) {
        this.CaseMonth = CaseMonth;
    }

    public String getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(String CaseNumber) {
        this.CaseNumber = CaseNumber;
    }

    public String getRelatedCaseNumber() {
        return relatedCaseNumber;
    }

    public void setRelatedCaseNumber(String relatedCaseNumber) {
        this.relatedCaseNumber = relatedCaseNumber;
    }
    
    
}
