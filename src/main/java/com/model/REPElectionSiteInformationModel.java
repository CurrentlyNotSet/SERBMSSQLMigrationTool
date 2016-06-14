/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author User
 */
public class REPElectionSiteInformationModel {
    
    private int id;
    private int active;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private Date siteDate;
    private Time siteTime;
    private String sitePlace;
    private String siteAddress1;
    private String siteAddress2;
    private String siteLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
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

    public Date getSiteDate() {
        return siteDate;
    }

    public void setSiteDate(Date siteDate) {
        this.siteDate = siteDate;
    }

    public Time getSiteTime() {
        return siteTime;
    }

    public void setSiteTime(Time siteTime) {
        this.siteTime = siteTime;
    }

    public String getSitePlace() {
        return sitePlace;
    }

    public void setSitePlace(String sitePlace) {
        this.sitePlace = sitePlace;
    }

    public String getSiteAddress1() {
        return siteAddress1;
    }

    public void setSiteAddress1(String siteAddress1) {
        this.siteAddress1 = siteAddress1;
    }

    public String getSiteAddress2() {
        return siteAddress2;
    }

    public void setSiteAddress2(String siteAddress2) {
        this.siteAddress2 = siteAddress2;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }
    
    
}
