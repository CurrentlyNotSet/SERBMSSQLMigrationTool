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
public class ORGCaseModel {

    private int id;
    private int active;
    private String orgName;
    private String orgNumber;
    private String fiscalYearEnding;
    private String filingDueDate;
    private Date annualReport;
    private Date financialReport;
    private Date registrationReport;
    private Date constructionAndByLaws;
    private boolean filedByParent;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getFiscalYearEnding() {
        return fiscalYearEnding;
    }

    public void setFiscalYearEnding(String fiscalYearEnding) {
        this.fiscalYearEnding = fiscalYearEnding;
    }

    public String getFilingDueDate() {
        return filingDueDate;
    }

    public void setFilingDueDate(String filingDueDate) {
        this.filingDueDate = filingDueDate;
    }

    public Date getAnnualReport() {
        return annualReport;
    }

    public void setAnnualReport(Date annualReport) {
        this.annualReport = annualReport;
    }

    public Date getFinancialReport() {
        return financialReport;
    }

    public void setFinancialReport(Date financialReport) {
        this.financialReport = financialReport;
    }

    public Date getRegistrationReport() {
        return registrationReport;
    }

    public void setRegistrationReport(Date registrationReport) {
        this.registrationReport = registrationReport;
    }

    public Date getConstructionAndByLaws() {
        return constructionAndByLaws;
    }

    public void setConstructionAndByLaws(Date constructionAndByLaws) {
        this.constructionAndByLaws = constructionAndByLaws;
    }

    public boolean isFiledByParent() {
        return filedByParent;
    }

    public void setFiledByParent(boolean filedByParent) {
        this.filedByParent = filedByParent;
    }

}
