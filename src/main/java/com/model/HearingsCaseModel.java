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
public class HearingsCaseModel {
    private int id;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String openClose;
    private boolean expedited;
    private Date boardActionPCDate;
    private Date boardActionPreDDate;
    private Date directiveIssueDate;
    private Date complaintDueDate;
    private Date draftComplaintToHearingDate;
    private Date preHearingDate;
    private Date proposedRecDueDate;
    private Date exceptionFilingDate;
    private Date boardActionDate;
    private String otherAction;
    private int aljID;
    private Date complaintIssuedDate;
    private Date hearingDate;
    private Date proposedRecIssuedDate;
    private Date responseFilingDate;
    private Date issuanceOfOptionOrDirectiveDate;
    private String finalResult;
    private Date opinion;
    private String companionCases;
    private String caseStatusNotes;

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

    public String getOpenClose() {
        return openClose;
    }

    public void setOpenClose(String openClose) {
        this.openClose = openClose;
    }

    public boolean isExpedited() {
        return expedited;
    }

    public void setExpedited(boolean expedited) {
        this.expedited = expedited;
    }

    public Date getBoardActionPCDate() {
        return boardActionPCDate;
    }

    public void setBoardActionPCDate(Date boardActionPCDate) {
        this.boardActionPCDate = boardActionPCDate;
    }

    public Date getBoardActionPreDDate() {
        return boardActionPreDDate;
    }

    public void setBoardActionPreDDate(Date boardActionPreDDate) {
        this.boardActionPreDDate = boardActionPreDDate;
    }

    public Date getDirectiveIssueDate() {
        return directiveIssueDate;
    }

    public void setDirectiveIssueDate(Date directiveIssueDate) {
        this.directiveIssueDate = directiveIssueDate;
    }

    public Date getComplaintDueDate() {
        return complaintDueDate;
    }

    public void setComplaintDueDate(Date complaintDueDate) {
        this.complaintDueDate = complaintDueDate;
    }

    public Date getDraftComplaintToHearingDate() {
        return draftComplaintToHearingDate;
    }

    public void setDraftComplaintToHearingDate(Date draftComplaintToHearingDate) {
        this.draftComplaintToHearingDate = draftComplaintToHearingDate;
    }

    public Date getPreHearingDate() {
        return preHearingDate;
    }

    public void setPreHearingDate(Date preHearingDate) {
        this.preHearingDate = preHearingDate;
    }

    public Date getProposedRecDueDate() {
        return proposedRecDueDate;
    }

    public void setProposedRecDueDate(Date proposedRecDueDate) {
        this.proposedRecDueDate = proposedRecDueDate;
    }

    public Date getExceptionFilingDate() {
        return exceptionFilingDate;
    }

    public void setExceptionFilingDate(Date exceptionFilingDate) {
        this.exceptionFilingDate = exceptionFilingDate;
    }

    public Date getBoardActionDate() {
        return boardActionDate;
    }

    public void setBoardActionDate(Date boardActionDate) {
        this.boardActionDate = boardActionDate;
    }

    public String getOtherAction() {
        return otherAction;
    }

    public void setOtherAction(String otherAction) {
        this.otherAction = otherAction;
    }

    public int getAljID() {
        return aljID;
    }

    public void setAljID(int aljID) {
        this.aljID = aljID;
    }

    public Date getComplaintIssuedDate() {
        return complaintIssuedDate;
    }

    public void setComplaintIssuedDate(Date complaintIssuedDate) {
        this.complaintIssuedDate = complaintIssuedDate;
    }

    public Date getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(Date hearingDate) {
        this.hearingDate = hearingDate;
    }

    public Date getProposedRecIssuedDate() {
        return proposedRecIssuedDate;
    }

    public void setProposedRecIssuedDate(Date proposedRecIssuedDate) {
        this.proposedRecIssuedDate = proposedRecIssuedDate;
    }

    public Date getResponseFilingDate() {
        return responseFilingDate;
    }

    public void setResponseFilingDate(Date responseFilingDate) {
        this.responseFilingDate = responseFilingDate;
    }

    public Date getIssuanceOfOptionOrDirectiveDate() {
        return issuanceOfOptionOrDirectiveDate;
    }

    public void setIssuanceOfOptionOrDirectiveDate(Date issuanceOfOptionOrDirectiveDate) {
        this.issuanceOfOptionOrDirectiveDate = issuanceOfOptionOrDirectiveDate;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public Date getOpinion() {
        return opinion;
    }

    public void setOpinion(Date opinion) {
        this.opinion = opinion;
    }

    public String getCompanionCases() {
        return companionCases;
    }

    public void setCompanionCases(String companionCases) {
        this.companionCases = companionCases;
    }

    public String getCaseStatusNotes() {
        return caseStatusNotes;
    }

    public void setCaseStatusNotes(String caseStatusNotes) {
        this.caseStatusNotes = caseStatusNotes;
    }
    
}
