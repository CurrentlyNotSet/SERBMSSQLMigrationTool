/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author Andrew
 */
public class REPcaseModel {

    private int id;
    private int active;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String type;
    private String status1;
    private String status2;
    private int currentOwnerID;
    private String county;
    private String employerIDNumber;
    private String deptInState;
    private String bargainingUnitNumber;
    private int boardCertified;
    private int deemedCertified;
    private int certificationRevoked;
    private Date fileDate;
    private Date amendedFilingDate;
    private Date finalBoardDate;
    private Date registrationLetterSent;
    private Date dateOfAppeal;
    private Date courtClosedDate;
    private Date returnSOIDueDate;
    private Date actualSOIReturnDate;
    private String SOIReturnInitials;
    private Date REPClosedCaseDueDate;
    private Date actualREPClosedDate;
    private String REPClosedInitials;
    private Date actualClerksClosedDate;
    private String clerksClosedDateInitials;
    private String note;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public int getCurrentOwnerID() {
        return currentOwnerID;
    }

    public void setCurrentOwnerID(int currentOwnerID) {
        this.currentOwnerID = currentOwnerID;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getEmployerIDNumber() {
        return employerIDNumber;
    }

    public void setEmployerIDNumber(String employerIDNumber) {
        this.employerIDNumber = employerIDNumber;
    }

    public String getDeptInState() {
        return deptInState;
    }

    public void setDeptInState(String deptInState) {
        this.deptInState = deptInState;
    }

    public String getBargainingUnitNumber() {
        return bargainingUnitNumber;
    }

    public void setBargainingUnitNumber(String bargainingUnitNumber) {
        this.bargainingUnitNumber = bargainingUnitNumber;
    }

    public int getBoardCertified() {
        return boardCertified;
    }

    public void setBoardCertified(int boardCertified) {
        this.boardCertified = boardCertified;
    }

    public int getDeemedCertified() {
        return deemedCertified;
    }

    public void setDeemedCertified(int deemedCertified) {
        this.deemedCertified = deemedCertified;
    }

    public int getCertificationRevoked() {
        return certificationRevoked;
    }

    public void setCertificationRevoked(int certificationRevoked) {
        this.certificationRevoked = certificationRevoked;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public Date getAmendedFilingDate() {
        return amendedFilingDate;
    }

    public void setAmendedFilingDate(Date amendedFilingDate) {
        this.amendedFilingDate = amendedFilingDate;
    }

    public Date getFinalBoardDate() {
        return finalBoardDate;
    }

    public void setFinalBoardDate(Date finalBoardDate) {
        this.finalBoardDate = finalBoardDate;
    }

    public Date getRegistrationLetterSent() {
        return registrationLetterSent;
    }

    public void setRegistrationLetterSent(Date registrationLetterSent) {
        this.registrationLetterSent = registrationLetterSent;
    }

    public Date getDateOfAppeal() {
        return dateOfAppeal;
    }

    public void setDateOfAppeal(Date dateOfAppeal) {
        this.dateOfAppeal = dateOfAppeal;
    }

    public Date getCourtClosedDate() {
        return courtClosedDate;
    }

    public void setCourtClosedDate(Date courtClosedDate) {
        this.courtClosedDate = courtClosedDate;
    }

    public Date getReturnSOIDueDate() {
        return returnSOIDueDate;
    }

    public void setReturnSOIDueDate(Date returnSOIDueDate) {
        this.returnSOIDueDate = returnSOIDueDate;
    }

    public Date getActualSOIReturnDate() {
        return actualSOIReturnDate;
    }

    public void setActualSOIReturnDate(Date actualSOIReturnDate) {
        this.actualSOIReturnDate = actualSOIReturnDate;
    }

    public String getSOIReturnInitials() {
        return SOIReturnInitials;
    }

    public void setSOIReturnInitials(String SOIReturnInitials) {
        this.SOIReturnInitials = SOIReturnInitials;
    }

    public Date getREPClosedCaseDueDate() {
        return REPClosedCaseDueDate;
    }

    public void setREPClosedCaseDueDate(Date REPClosedCaseDueDate) {
        this.REPClosedCaseDueDate = REPClosedCaseDueDate;
    }

    public Date getActualREPClosedDate() {
        return actualREPClosedDate;
    }

    public void setActualREPClosedDate(Date actualREPClosedDate) {
        this.actualREPClosedDate = actualREPClosedDate;
    }

    public String getREPClosedInitials() {
        return REPClosedInitials;
    }

    public void setREPClosedInitials(String REPClosedInitials) {
        this.REPClosedInitials = REPClosedInitials;
    }

    public Date getActualClerksClosedDate() {
        return actualClerksClosedDate;
    }

    public void setActualClerksClosedDate(Date actualClerksClosedDate) {
        this.actualClerksClosedDate = actualClerksClosedDate;
    }

    public String getClerksClosedDateInitials() {
        return clerksClosedDateInitials;
    }

    public void setClerksClosedDateInitials(String clerksClosedDateInitials) {
        this.clerksClosedDateInitials = clerksClosedDateInitials;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
