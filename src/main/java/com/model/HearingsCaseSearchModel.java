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
public class HearingsCaseSearchModel {
    
    private int id;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String hearingStatus;
    private String hearingParties;
    private Date hearingPCDate;
    private String hearingALJ;
    private Date hearingBoardActionDate;

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

    public String getHearingStatus() {
        return hearingStatus;
    }

    public void setHearingStatus(String hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    public String getHearingParties() {
        return hearingParties;
    }

    public void setHearingParties(String hearingParties) {
        this.hearingParties = hearingParties;
    }

    public Date getHearingPCDate() {
        return hearingPCDate;
    }

    public void setHearingPCDate(Date hearingPCDate) {
        this.hearingPCDate = hearingPCDate;
    }

    public String getHearingALJ() {
        return hearingALJ;
    }

    public void setHearingALJ(String hearingALJ) {
        this.hearingALJ = hearingALJ;
    }

    public Date getHearingBoardActionDate() {
        return hearingBoardActionDate;
    }

    public void setHearingBoardActionDate(Date hearingBoardActionDate) {
        this.hearingBoardActionDate = hearingBoardActionDate;
    }
    
}
