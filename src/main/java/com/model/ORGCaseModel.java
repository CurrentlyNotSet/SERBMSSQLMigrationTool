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
    private String note;
    private String alsoKnownAs;
    private String orgType;
    private String orgPhone1;
    private String orgPhone2;
    private String orgFax;
    private String employerID;
    private String orgAddress1;
    private String orgAddress2;
    private String orgCity;
    private String orgState;
    private String orgZip;
    private String orgCounty;
    private String orgEmail;
    private String lastNotification;
    private boolean deemedCertified;
    private boolean boardCertified;
    private boolean valid;
    private String parent1;
    private String parent2;
    private String outsideCase;
    private Date dateFiled;
    private Date certifiedDate;
    private Date registrationLetterSent;
    private Date extensionDate;
    
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(String alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgPhone1() {
        return orgPhone1;
    }

    public void setOrgPhone1(String orgPhone1) {
        this.orgPhone1 = orgPhone1;
    }

    public String getOrgPhone2() {
        return orgPhone2;
    }

    public void setOrgPhone2(String orgPhone2) {
        this.orgPhone2 = orgPhone2;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getOrgAddress1() {
        return orgAddress1;
    }

    public void setOrgAddress1(String orgAddress1) {
        this.orgAddress1 = orgAddress1;
    }

    public String getOrgAddress2() {
        return orgAddress2;
    }

    public void setOrgAddress2(String orgAddress2) {
        this.orgAddress2 = orgAddress2;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

    public String getOrgZip() {
        return orgZip;
    }

    public void setOrgZip(String orgZip) {
        this.orgZip = orgZip;
    }

    public String getOrgCounty() {
        return orgCounty;
    }

    public void setOrgCounty(String orgCounty) {
        this.orgCounty = orgCounty;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(String lastNotification) {
        this.lastNotification = lastNotification;
    }

    public boolean isDeemedCertified() {
        return deemedCertified;
    }

    public void setDeemedCertified(boolean deemedCertified) {
        this.deemedCertified = deemedCertified;
    }

    public boolean isBoardCertified() {
        return boardCertified;
    }

    public void setBoardCertified(boolean boardCertified) {
        this.boardCertified = boardCertified;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getParent1() {
        return parent1;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getOutsideCase() {
        return outsideCase;
    }

    public void setOutsideCase(String outsideCase) {
        this.outsideCase = outsideCase;
    }

    public Date getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(Date dateFiled) {
        this.dateFiled = dateFiled;
    }

    public Date getCertifiedDate() {
        return certifiedDate;
    }

    public void setCertifiedDate(Date certifiedDate) {
        this.certifiedDate = certifiedDate;
    }

    public Date getRegistrationLetterSent() {
        return registrationLetterSent;
    }

    public void setRegistrationLetterSent(Date registrationLetterSent) {
        this.registrationLetterSent = registrationLetterSent;
    }

    public Date getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(Date extensionDate) {
        this.extensionDate = extensionDate;
    }

    
}
