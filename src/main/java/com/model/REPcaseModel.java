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
    private String comments;
    private Date REPClosedCaseDueDate;
    private Date actualREPClosedDate;
    
    //added columns
    private int REPClosedUser;
    private Date actualClerksClosedDate;
    private int clerksClosedUser;
    private String note;
    private Date alphaListDate;
    private String fileBy;
    private String bargainingUnitIncluded;
    private String bargainingUnitExcluded;
    private String optInIncluded;
    private int professionalNonProfessional;
    private String professionalIncluded;
    private String professionalExcluded;
    private String nonProfessionalIncluded;
    private String nonProfessionalExcluded;
    private String toReflect;
    private String typeFiledBy;
    private String typeFiledVia;
    private String positionStatementFiledBy;
    private String EEONameChangeFrom;
    private String EEONameChangeTo;
    private String ERNameChangeFrom;
    private String ERNameChangeTo;
    private String boardActionType;
    private Date boardActionDate;
    private int hearingPersonID;
    private String boardStatusNote;
    private String boardStatusBlurb;
    
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

    public Date getActualClerksClosedDate() {
        return actualClerksClosedDate;
    }

    public void setActualClerksClosedDate(Date actualClerksClosedDate) {
        this.actualClerksClosedDate = actualClerksClosedDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getREPClosedUser() {
        return REPClosedUser;
    }

    public void setREPClosedUser(int REPClosedUser) {
        this.REPClosedUser = REPClosedUser;
    }

    public int getClerksClosedUser() {
        return clerksClosedUser;
    }

    public void setClerksClosedUser(int clerksClosedUser) {
        this.clerksClosedUser = clerksClosedUser;
    }

    public Date getAlphaListDate() {
        return alphaListDate;
    }

    public void setAlphaListDate(Date alphaListDate) {
        this.alphaListDate = alphaListDate;
    }

    public String getFileBy() {
        return fileBy;
    }

    public void setFileBy(String fileBy) {
        this.fileBy = fileBy;
    }

    public String getBargainingUnitIncluded() {
        return bargainingUnitIncluded;
    }

    public void setBargainingUnitIncluded(String bargainingUnitIncluded) {
        this.bargainingUnitIncluded = bargainingUnitIncluded;
    }

    public String getBargainingUnitExcluded() {
        return bargainingUnitExcluded;
    }

    public void setBargainingUnitExcluded(String bargainingUnitExcluded) {
        this.bargainingUnitExcluded = bargainingUnitExcluded;
    }

    public String getOptInIncluded() {
        return optInIncluded;
    }

    public void setOptInIncluded(String optInIncluded) {
        this.optInIncluded = optInIncluded;
    }

    public int getProfessionalNonProfessional() {
        return professionalNonProfessional;
    }

    public void setProfessionalNonProfessional(int professionalNonProfessional) {
        this.professionalNonProfessional = professionalNonProfessional;
    }

    public String getProfessionalIncluded() {
        return professionalIncluded;
    }

    public void setProfessionalIncluded(String professionalIncluded) {
        this.professionalIncluded = professionalIncluded;
    }

    public String getProfessionalExcluded() {
        return professionalExcluded;
    }

    public void setProfessionalExcluded(String professionalExcluded) {
        this.professionalExcluded = professionalExcluded;
    }

    public String getToReflect() {
        return toReflect;
    }

    public void setToReflect(String toReflect) {
        this.toReflect = toReflect;
    }

    public String getTypeFiledBy() {
        return typeFiledBy;
    }

    public void setTypeFiledBy(String typeFiledBy) {
        this.typeFiledBy = typeFiledBy;
    }

    public String getTypeFiledVia() {
        return typeFiledVia;
    }

    public void setTypeFiledVia(String typeFiledVia) {
        this.typeFiledVia = typeFiledVia;
    }

    public String getPositionStatementFiledBy() {
        return positionStatementFiledBy;
    }

    public void setPositionStatementFiledBy(String positionStatementFiledBy) {
        this.positionStatementFiledBy = positionStatementFiledBy;
    }

    public String getEEONameChangeFrom() {
        return EEONameChangeFrom;
    }

    public void setEEONameChangeFrom(String EEONameChangeFrom) {
        this.EEONameChangeFrom = EEONameChangeFrom;
    }

    public String getEEONameChangeTo() {
        return EEONameChangeTo;
    }

    public void setEEONameChangeTo(String EEONameChangeTo) {
        this.EEONameChangeTo = EEONameChangeTo;
    }

    public String getERNameChangeFrom() {
        return ERNameChangeFrom;
    }

    public void setERNameChangeFrom(String ERNameChangeFrom) {
        this.ERNameChangeFrom = ERNameChangeFrom;
    }

    public String getERNameChangeTo() {
        return ERNameChangeTo;
    }

    public void setERNameChangeTo(String ERNameChangeTo) {
        this.ERNameChangeTo = ERNameChangeTo;
    }

    public String getBoardActionType() {
        return boardActionType;
    }

    public void setBoardActionType(String boardActionType) {
        this.boardActionType = boardActionType;
    }

    public Date getBoardActionDate() {
        return boardActionDate;
    }

    public void setBoardActionDate(Date boardActionDate) {
        this.boardActionDate = boardActionDate;
    }

    public int getHearingPersonID() {
        return hearingPersonID;
    }

    public void setHearingPersonID(int hearingPersonID) {
        this.hearingPersonID = hearingPersonID;
    }

    public String getBoardStatusNote() {
        return boardStatusNote;
    }

    public void setBoardStatusNote(String boardStatusNote) {
        this.boardStatusNote = boardStatusNote;
    }

    public String getBoardStatusBlurb() {
        return boardStatusBlurb;
    }

    public void setBoardStatusBlurb(String boardStatusBlurb) {
        this.boardStatusBlurb = boardStatusBlurb;
    }

    public String getNonProfessionalIncluded() {
        return nonProfessionalIncluded;
    }

    public void setNonProfessionalIncluded(String nonProfessionalIncluded) {
        this.nonProfessionalIncluded = nonProfessionalIncluded;
    }

    public String getNonProfessionalExcluded() {
        return nonProfessionalExcluded;
    }

    public void setNonProfessionalExcluded(String nonProfessionalExcluded) {
        this.nonProfessionalExcluded = nonProfessionalExcluded;
    }

}
