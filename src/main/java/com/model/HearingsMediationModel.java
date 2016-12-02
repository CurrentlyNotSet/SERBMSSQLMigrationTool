/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author User
 */
public class HearingsMediationModel {

    private int id;
    private boolean active;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String PCPreD;
    private int mediator;
    private Date DateAssigned;
    private Date MediationDate;
    private String Outcome;

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

    public String getPCPreD() {
        return PCPreD;
    }

    public void setPCPreD(String PCPreD) {
        this.PCPreD = PCPreD;
    }

    public int getMediator() {
        return mediator;
    }

    public void setMediator(int mediator) {
        this.mediator = mediator;
    }

    public Date getDateAssigned() {
        return DateAssigned;
    }

    public void setDateAssigned(Date DateAssigned) {
        this.DateAssigned = DateAssigned;
    }

    public Date getMediationDate() {
        return MediationDate;
    }

    public void setMediationDate(Date MediationDate) {
        this.MediationDate = MediationDate;
    }

    public String getOutcome() {
        return Outcome;
    }

    public void setOutcome(String Outcome) {
        this.Outcome = Outcome;
    }
        
}
