/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class REPMediationModel {
    private int id;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private Date mediationEntryDate;
    private Timestamp mediationDate;
    private String mediationType;
    private String mediatorID;
    private String mediationOutcome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(String caseYear) {
        this.caseYear = caseYear;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseMonth() {
        return caseMonth;
    }

    public void setCaseMonth(String caseMonth) {
        this.caseMonth = caseMonth;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public Date getMediationEntryDate() {
        return mediationEntryDate;
    }

    public void setMediationEntryDate(Date mediationEntryDate) {
        this.mediationEntryDate = mediationEntryDate;
    }

    public Timestamp getMediationDate() {
        return mediationDate;
    }

    public void setMediationDate(Timestamp mediationDate) {
        this.mediationDate = mediationDate;
    }

    public String getMediationType() {
        return mediationType;
    }

    public void setMediationType(String mediationType) {
        this.mediationType = mediationType;
    }

    public String getMediatorID() {
        return mediatorID;
    }

    public void setMediatorID(String mediatorID) {
        this.mediatorID = mediatorID;
    }

    public String getMediationOutcome() {
        return mediationOutcome;
    }

    public void setMediationOutcome(String mediationOutcome) {
        this.mediationOutcome = mediationOutcome;
    }
    
    
}
