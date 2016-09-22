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
public class CMDSCaseModel {
    private int id;
    private boolean active;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String note;
    private Date openDate;
    private String groupNumber;
    private String aljID;
    private Date closeDate;
    private String inventoryStatusLine;
    private Date inventoryStatusDate;
    private String caseStatus;
    private String result;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getAljID() {
        return aljID;
    }

    public void setAljID(String aljID) {
        this.aljID = aljID;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getInventoryStatusLine() {
        return inventoryStatusLine;
    }

    public void setInventoryStatusLine(String inventoryStatusLine) {
        this.inventoryStatusLine = inventoryStatusLine;
    }

    public Date getInventoryStatusDate() {
        return inventoryStatusDate;
    }

    public void setInventoryStatusDate(Date inventoryStatusDate) {
        this.inventoryStatusDate = inventoryStatusDate;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
}
