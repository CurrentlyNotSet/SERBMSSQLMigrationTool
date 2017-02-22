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
public class CMDSCaseSearchModel {
    private int id;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String appellant;
    private String appellantRep;
    private String appellee;
    private String appelleeRep;
    private String alj;
    private Date dateOpened;

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

    public String getAppellant() {
        return appellant;
    }

    public void setAppellant(String appellant) {
        this.appellant = appellant;
    }

    public String getAppellee() {
        return appellee;
    }

    public void setAppellee(String appellee) {
        this.appellee = appellee;
    }

    public String getAlj() {
        return alj;
    }

    public void setAlj(String alj) {
        this.alj = alj;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getAppellantRep() {
        return appellantRep;
    }

    public void setAppellantRep(String appellantRep) {
        this.appellantRep = appellantRep;
    }

    public String getAppelleeRep() {
        return appelleeRep;
    }

    public void setAppelleeRep(String appelleeRep) {
        this.appelleeRep = appelleeRep;
    }
    
}
