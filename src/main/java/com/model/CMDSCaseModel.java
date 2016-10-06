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
    private String mediatorID;
    private String pbrBox;
    private String groupType;
    private String reclassCode;
    private Date mailedRR;
    private Date mailedBO;
    private Date mailedPO1;
    private Date mailedPO2;
    private Date mailedPO3;
    private Date mailedPO4;
    private Date remailedRR;
    private Date remailedBO;
    private Date remailedPO1;
    private Date remailedPO2;
    private Date remailedPO3;
    private Date remailedPO4;
    private Date returnReceiptRR;
    private Date returnReceiptBO;
    private Date returnReceiptPO1;
    private Date returnReceiptPO2;
    private Date returnReceiptPO3;
    private Date returnReceiptPO4;
    private Date pullDateRR;
    private Date pullDateBO;
    private Date pullDatePO1;
    private Date pullDatePO2;
    private Date pullDatePO3;
    private Date pullDatePO4;
    private Date hearingCompletedDate;
    private Date postHearingDriefsDue;

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

    public String getMediatorID() {
        return mediatorID;
    }

    public void setMediatorID(String mediatorID) {
        this.mediatorID = mediatorID;
    }

    public String getPbrBox() {
        return pbrBox;
    }

    public void setPbrBox(String pbrBox) {
        this.pbrBox = pbrBox;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getReclassCode() {
        return reclassCode;
    }

    public void setReclassCode(String reclassCode) {
        this.reclassCode = reclassCode;
    }

    public Date getMailedRR() {
        return mailedRR;
    }

    public void setMailedRR(Date mailedRR) {
        this.mailedRR = mailedRR;
    }

    public Date getMailedBO() {
        return mailedBO;
    }

    public void setMailedBO(Date mailedBO) {
        this.mailedBO = mailedBO;
    }

    public Date getMailedPO1() {
        return mailedPO1;
    }

    public void setMailedPO1(Date mailedPO1) {
        this.mailedPO1 = mailedPO1;
    }

    public Date getMailedPO2() {
        return mailedPO2;
    }

    public void setMailedPO2(Date mailedPO2) {
        this.mailedPO2 = mailedPO2;
    }

    public Date getMailedPO3() {
        return mailedPO3;
    }

    public void setMailedPO3(Date mailedPO3) {
        this.mailedPO3 = mailedPO3;
    }

    public Date getMailedPO4() {
        return mailedPO4;
    }

    public void setMailedPO4(Date mailedPO4) {
        this.mailedPO4 = mailedPO4;
    }

    public Date getRemailedRR() {
        return remailedRR;
    }

    public void setRemailedRR(Date remailedRR) {
        this.remailedRR = remailedRR;
    }

    public Date getRemailedBO() {
        return remailedBO;
    }

    public void setRemailedBO(Date remailedBO) {
        this.remailedBO = remailedBO;
    }

    public Date getRemailedPO1() {
        return remailedPO1;
    }

    public void setRemailedPO1(Date remailedPO1) {
        this.remailedPO1 = remailedPO1;
    }

    public Date getRemailedPO2() {
        return remailedPO2;
    }

    public void setRemailedPO2(Date remailedPO2) {
        this.remailedPO2 = remailedPO2;
    }

    public Date getRemailedPO3() {
        return remailedPO3;
    }

    public void setRemailedPO3(Date remailedPO3) {
        this.remailedPO3 = remailedPO3;
    }

    public Date getRemailedPO4() {
        return remailedPO4;
    }

    public void setRemailedPO4(Date remailedPO4) {
        this.remailedPO4 = remailedPO4;
    }

    public Date getReturnReceiptRR() {
        return returnReceiptRR;
    }

    public void setReturnReceiptRR(Date returnReceiptRR) {
        this.returnReceiptRR = returnReceiptRR;
    }

    public Date getReturnReceiptBO() {
        return returnReceiptBO;
    }

    public void setReturnReceiptBO(Date returnReceiptBO) {
        this.returnReceiptBO = returnReceiptBO;
    }

    public Date getReturnReceiptPO1() {
        return returnReceiptPO1;
    }

    public void setReturnReceiptPO1(Date returnReceiptPO1) {
        this.returnReceiptPO1 = returnReceiptPO1;
    }

    public Date getReturnReceiptPO2() {
        return returnReceiptPO2;
    }

    public void setReturnReceiptPO2(Date returnReceiptPO2) {
        this.returnReceiptPO2 = returnReceiptPO2;
    }

    public Date getReturnReceiptPO3() {
        return returnReceiptPO3;
    }

    public void setReturnReceiptPO3(Date returnReceiptPO3) {
        this.returnReceiptPO3 = returnReceiptPO3;
    }

    public Date getReturnReceiptPO4() {
        return returnReceiptPO4;
    }

    public void setReturnReceiptPO4(Date returnReceiptPO4) {
        this.returnReceiptPO4 = returnReceiptPO4;
    }

    public Date getPullDateRR() {
        return pullDateRR;
    }

    public void setPullDateRR(Date pullDateRR) {
        this.pullDateRR = pullDateRR;
    }

    public Date getPullDateBO() {
        return pullDateBO;
    }

    public void setPullDateBO(Date pullDateBO) {
        this.pullDateBO = pullDateBO;
    }

    public Date getPullDatePO1() {
        return pullDatePO1;
    }

    public void setPullDatePO1(Date pullDatePO1) {
        this.pullDatePO1 = pullDatePO1;
    }

    public Date getPullDatePO2() {
        return pullDatePO2;
    }

    public void setPullDatePO2(Date pullDatePO2) {
        this.pullDatePO2 = pullDatePO2;
    }

    public Date getPullDatePO3() {
        return pullDatePO3;
    }

    public void setPullDatePO3(Date pullDatePO3) {
        this.pullDatePO3 = pullDatePO3;
    }

    public Date getPullDatePO4() {
        return pullDatePO4;
    }

    public void setPullDatePO4(Date pullDatePO4) {
        this.pullDatePO4 = pullDatePO4;
    }

    public Date getHearingCompletedDate() {
        return hearingCompletedDate;
    }

    public void setHearingCompletedDate(Date hearingCompletedDate) {
        this.hearingCompletedDate = hearingCompletedDate;
    }

    public Date getPostHearingDriefsDue() {
        return postHearingDriefsDue;
    }

    public void setPostHearingDriefsDue(Date postHearingDriefsDue) {
        this.postHearingDriefsDue = postHearingDriefsDue;
    }
    
}
