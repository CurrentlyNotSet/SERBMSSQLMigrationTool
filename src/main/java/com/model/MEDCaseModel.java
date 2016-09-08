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
public class MEDCaseModel {
    
    private int id;
    private int active;
    private String caseYear;
    private String caseType;
    private String caseMonth;
    private String caseNumber;
    private String note;
    private Date fileDate;
    private Date concilList1OrderDate;
    private Date concilList1SelectionDueDate;
    private String concilList1Name1;
    private String concilList1Name2;
    private String concilList1Name3;
    private String concilList1Name4;
    private String concilList1Name5;
    private Date concilAppointmentDate;
    private String concilType;
    private String concilSelection;
    private String concilReplacement;
    private String concilOriginalConciliator;
    private Date concilOriginalConcilDate;
    private Date concilList2OrderDate;
    private Date concilList2SelectionDueDate;
    private String concilList2Name1;
    private String concilList2Name2;
    private String concilList2Name3;
    private String concilList2Name4;
    private String concilList2Name5;
    private Date FFList1OrderDate;
    private Date FFList1SelectionDueDate;
    private String FFList1Name1;
    private String FFList1Name2;
    private String FFList1Name3;
    private String FFList1Name4;
    private String FFList1Name5;
    private Date FFAppointmentDate;
    private String FFType;
    private String FFSelection;
    private String FFReplacement;
    private String FFOriginalFactFinder;
    private Date FFOriginalFactFinderDate;
    private int asAgreedToByParties;
    private Date FFList2OrderDate;
    private Date FFList2SelectionDueDate;
    private String FFList2Name1;
    private String FFList2Name2;
    private String FFList2Name3;
    private String FFList2Name4;
    private String FFList2Name5;
    private String FFEmployerType;
    private String FFEmployeeType;
    private Date FFReportIssueDate;
    private int FFMediatedSettlement;
    private String FFAcceptedBy;
    private String FFDeemedAcceptedBy;
    private String FFRejectedBy;
    private String FFOverallResult;
    private String FFNote;
    private String employerIDNumber;
    private String bargainingUnitNumber;
    private String approxNumberOfEmployees;
    private String duplicateCaseNumber;
    private String relatedCaseNumber;
    private String negotiationType;
    private Date expirationDate;
    private String NTNFiledBy;
    private String negotiationPeriod;
    private boolean multiunitBargainingRequested;
    private Date mediatorAppointedDate;
    private boolean mediatorReplacement;
    private String stateMediatorAppointedID;
    private String FMCSMediatorAppointedID;
    private Date settlementDate;
    private String caseStatus;
    private boolean sendToBoardToClose;
    private Date boardFinalDate;
    private Date retentionTicklerDate;
    private boolean lateFiling;
    private boolean impasse;
    private boolean settled;
    private boolean TA;
    private boolean MAD;
    private boolean withdrawl;
    private boolean motion;
    private boolean dismissed;
    private Date strikeFileDate;
    private String unitDescription;
    private String unitSize;
    private boolean unauthorizedStrike;
    private boolean noticeOfIntentToStrikeOnly;
    private Date intendedDateStrike;
    private boolean noticeOfIntentToPicketOnly;
    private Date intendedDatePicket;
    private boolean informational;
    private boolean noticeOfIntentToStrikeAndPicket;
    private String strikeOccured;
    private String strikeStatus;
    private Date strikeBegan;
    private Date strikeEnded;
    private String totalNumberOfDays;
    private String strikeMediatorAppointedID;
    private String strikeNote;
 
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public Date getConcilList1OrderDate() {
        return concilList1OrderDate;
    }

    public void setConcilList1OrderDate(Date concilList1OrderDate) {
        this.concilList1OrderDate = concilList1OrderDate;
    }

    public Date getConcilList1SelectionDueDate() {
        return concilList1SelectionDueDate;
    }

    public void setConcilList1SelectionDueDate(Date concilList1SelectionDueDate) {
        this.concilList1SelectionDueDate = concilList1SelectionDueDate;
    }

    public String getConcilList1Name1() {
        return concilList1Name1;
    }

    public void setConcilList1Name1(String concilList1Name1) {
        this.concilList1Name1 = concilList1Name1;
    }

    public String getConcilList1Name2() {
        return concilList1Name2;
    }

    public void setConcilList1Name2(String concilList1Name2) {
        this.concilList1Name2 = concilList1Name2;
    }

    public String getConcilList1Name3() {
        return concilList1Name3;
    }

    public void setConcilList1Name3(String concilList1Name3) {
        this.concilList1Name3 = concilList1Name3;
    }

    public String getConcilList1Name4() {
        return concilList1Name4;
    }

    public void setConcilList1Name4(String concilList1Name4) {
        this.concilList1Name4 = concilList1Name4;
    }

    public String getConcilList1Name5() {
        return concilList1Name5;
    }

    public void setConcilList1Name5(String concilList1Name5) {
        this.concilList1Name5 = concilList1Name5;
    }

    public Date getConcilAppointmentDate() {
        return concilAppointmentDate;
    }

    public void setConcilAppointmentDate(Date concilAppointmentDate) {
        this.concilAppointmentDate = concilAppointmentDate;
    }

    public String getConcilType() {
        return concilType;
    }

    public void setConcilType(String concilType) {
        this.concilType = concilType;
    }

    public String getConcilSelection() {
        return concilSelection;
    }

    public void setConcilSelection(String concilSelection) {
        this.concilSelection = concilSelection;
    }

    public String getConcilReplacement() {
        return concilReplacement;
    }

    public void setConcilReplacement(String concilReplacement) {
        this.concilReplacement = concilReplacement;
    }

    public String getConcilOriginalConciliator() {
        return concilOriginalConciliator;
    }

    public void setConcilOriginalConciliator(String concilOriginalConciliator) {
        this.concilOriginalConciliator = concilOriginalConciliator;
    }

    public Date getConcilOriginalConcilDate() {
        return concilOriginalConcilDate;
    }

    public void setConcilOriginalConcilDate(Date concilOriginalConcilDate) {
        this.concilOriginalConcilDate = concilOriginalConcilDate;
    }

    public Date getConcilList2OrderDate() {
        return concilList2OrderDate;
    }

    public void setConcilList2OrderDate(Date concilList2OrderDate) {
        this.concilList2OrderDate = concilList2OrderDate;
    }

    public Date getConcilList2SelectionDueDate() {
        return concilList2SelectionDueDate;
    }

    public void setConcilList2SelectionDueDate(Date concilList2SelectionDueDate) {
        this.concilList2SelectionDueDate = concilList2SelectionDueDate;
    }

    public String getConcilList2Name1() {
        return concilList2Name1;
    }

    public void setConcilList2Name1(String concilList2Name1) {
        this.concilList2Name1 = concilList2Name1;
    }

    public String getConcilList2Name2() {
        return concilList2Name2;
    }

    public void setConcilList2Name2(String concilList2Name2) {
        this.concilList2Name2 = concilList2Name2;
    }

    public String getConcilList2Name3() {
        return concilList2Name3;
    }

    public void setConcilList2Name3(String concilList2Name3) {
        this.concilList2Name3 = concilList2Name3;
    }

    public String getConcilList2Name4() {
        return concilList2Name4;
    }

    public void setConcilList2Name4(String concilList2Name4) {
        this.concilList2Name4 = concilList2Name4;
    }

    public String getConcilList2Name5() {
        return concilList2Name5;
    }

    public void setConcilList2Name5(String concilList2Name5) {
        this.concilList2Name5 = concilList2Name5;
    }

    public Date getFFList1OrderDate() {
        return FFList1OrderDate;
    }

    public void setFFList1OrderDate(Date FFList1OrderDate) {
        this.FFList1OrderDate = FFList1OrderDate;
    }

    public Date getFFList1SelectionDueDate() {
        return FFList1SelectionDueDate;
    }

    public void setFFList1SelectionDueDate(Date FFList1SelectionDueDate) {
        this.FFList1SelectionDueDate = FFList1SelectionDueDate;
    }

    public String getFFList1Name1() {
        return FFList1Name1;
    }

    public void setFFList1Name1(String FFList1Name1) {
        this.FFList1Name1 = FFList1Name1;
    }

    public String getFFList1Name2() {
        return FFList1Name2;
    }

    public void setFFList1Name2(String FFList1Name2) {
        this.FFList1Name2 = FFList1Name2;
    }

    public String getFFList1Name3() {
        return FFList1Name3;
    }

    public void setFFList1Name3(String FFList1Name3) {
        this.FFList1Name3 = FFList1Name3;
    }

    public String getFFList1Name4() {
        return FFList1Name4;
    }

    public void setFFList1Name4(String FFList1Name4) {
        this.FFList1Name4 = FFList1Name4;
    }

    public String getFFList1Name5() {
        return FFList1Name5;
    }

    public void setFFList1Name5(String FFList1Name5) {
        this.FFList1Name5 = FFList1Name5;
    }

    public Date getFFAppointmentDate() {
        return FFAppointmentDate;
    }

    public void setFFAppointmentDate(Date FFAppointmentDate) {
        this.FFAppointmentDate = FFAppointmentDate;
    }

    public String getFFType() {
        return FFType;
    }

    public void setFFType(String FFType) {
        this.FFType = FFType;
    }

    public String getFFSelection() {
        return FFSelection;
    }

    public void setFFSelection(String FFSelection) {
        this.FFSelection = FFSelection;
    }

    public String getFFReplacement() {
        return FFReplacement;
    }

    public void setFFReplacement(String FFReplacement) {
        this.FFReplacement = FFReplacement;
    }

    public String getFFOriginalFactFinder() {
        return FFOriginalFactFinder;
    }

    public void setFFOriginalFactFinder(String FFOriginalFactFinder) {
        this.FFOriginalFactFinder = FFOriginalFactFinder;
    }

    public Date getFFOriginalFactFinderDate() {
        return FFOriginalFactFinderDate;
    }

    public void setFFOriginalFactFinderDate(Date FFOriginalFactFinderDate) {
        this.FFOriginalFactFinderDate = FFOriginalFactFinderDate;
    }

    public int getAsAgreedToByParties() {
        return asAgreedToByParties;
    }

    public void setAsAgreedToByParties(int asAgreedToByParties) {
        this.asAgreedToByParties = asAgreedToByParties;
    }

    public Date getFFList2OrderDate() {
        return FFList2OrderDate;
    }

    public void setFFList2OrderDate(Date FFList2OrderDate) {
        this.FFList2OrderDate = FFList2OrderDate;
    }

    public Date getFFList2SelectionDueDate() {
        return FFList2SelectionDueDate;
    }

    public void setFFList2SelectionDueDate(Date FFList2SelectionDueDate) {
        this.FFList2SelectionDueDate = FFList2SelectionDueDate;
    }

    public String getFFList2Name1() {
        return FFList2Name1;
    }

    public void setFFList2Name1(String FFList2Name1) {
        this.FFList2Name1 = FFList2Name1;
    }

    public String getFFList2Name2() {
        return FFList2Name2;
    }

    public void setFFList2Name2(String FFList2Name2) {
        this.FFList2Name2 = FFList2Name2;
    }

    public String getFFList2Name3() {
        return FFList2Name3;
    }

    public void setFFList2Name3(String FFList2Name3) {
        this.FFList2Name3 = FFList2Name3;
    }

    public String getFFList2Name4() {
        return FFList2Name4;
    }

    public void setFFList2Name4(String FFList2Name4) {
        this.FFList2Name4 = FFList2Name4;
    }

    public String getFFList2Name5() {
        return FFList2Name5;
    }

    public void setFFList2Name5(String FFList2Name5) {
        this.FFList2Name5 = FFList2Name5;
    }

    public String getFFEmployerType() {
        return FFEmployerType;
    }

    public void setFFEmployerType(String FFEmployerType) {
        this.FFEmployerType = FFEmployerType;
    }

    public String getFFEmployeeType() {
        return FFEmployeeType;
    }

    public void setFFEmployeeType(String FFEmployeeType) {
        this.FFEmployeeType = FFEmployeeType;
    }

    public Date getFFReportIssueDate() {
        return FFReportIssueDate;
    }

    public void setFFReportIssueDate(Date FFReportIssueDate) {
        this.FFReportIssueDate = FFReportIssueDate;
    }

    public int getFFMediatedSettlement() {
        return FFMediatedSettlement;
    }

    public void setFFMediatedSettlement(int FFMediatedSettlement) {
        this.FFMediatedSettlement = FFMediatedSettlement;
    }

    public String getFFAcceptedBy() {
        return FFAcceptedBy;
    }

    public void setFFAcceptedBy(String FFAcceptedBy) {
        this.FFAcceptedBy = FFAcceptedBy;
    }

    public String getFFDeemedAcceptedBy() {
        return FFDeemedAcceptedBy;
    }

    public void setFFDeemedAcceptedBy(String FFDeemedAcceptedBy) {
        this.FFDeemedAcceptedBy = FFDeemedAcceptedBy;
    }

    public String getFFRejectedBy() {
        return FFRejectedBy;
    }

    public void setFFRejectedBy(String FFRejectedBy) {
        this.FFRejectedBy = FFRejectedBy;
    }

    public String getFFOverallResult() {
        return FFOverallResult;
    }

    public void setFFOverallResult(String FFOverallResult) {
        this.FFOverallResult = FFOverallResult;
    }

    public String getFFNote() {
        return FFNote;
    }

    public void setFFNote(String FFNote) {
        this.FFNote = FFNote;
    }

    public String getEmployerIDNumber() {
        return employerIDNumber;
    }

    public void setEmployerIDNumber(String employerIDNumber) {
        this.employerIDNumber = employerIDNumber;
    }

    public String getBargainingUnitNumber() {
        return bargainingUnitNumber;
    }

    public void setBargainingUnitNumber(String bargainingUnitNumber) {
        this.bargainingUnitNumber = bargainingUnitNumber;
    }

    public String getApproxNumberOfEmployees() {
        return approxNumberOfEmployees;
    }

    public void setApproxNumberOfEmployees(String approxNumberOfEmployees) {
        this.approxNumberOfEmployees = approxNumberOfEmployees;
    }

    public String getDuplicateCaseNumber() {
        return duplicateCaseNumber;
    }

    public void setDuplicateCaseNumber(String duplicateCaseNumber) {
        this.duplicateCaseNumber = duplicateCaseNumber;
    }

    public String getRelatedCaseNumber() {
        return relatedCaseNumber;
    }

    public void setRelatedCaseNumber(String relatedCaseNumber) {
        this.relatedCaseNumber = relatedCaseNumber;
    }

    public String getNegotiationType() {
        return negotiationType;
    }

    public void setNegotiationType(String negotiationType) {
        this.negotiationType = negotiationType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNTNFiledBy() {
        return NTNFiledBy;
    }

    public void setNTNFiledBy(String NTNFiledBy) {
        this.NTNFiledBy = NTNFiledBy;
    }

    public String getNegotiationPeriod() {
        return negotiationPeriod;
    }

    public void setNegotiationPeriod(String negotiationPeriod) {
        this.negotiationPeriod = negotiationPeriod;
    }

    public boolean isMultiunitBargainingRequested() {
        return multiunitBargainingRequested;
    }

    public void setMultiunitBargainingRequested(boolean multiunitBargainingRequested) {
        this.multiunitBargainingRequested = multiunitBargainingRequested;
    }

    public Date getMediatorAppointedDate() {
        return mediatorAppointedDate;
    }

    public void setMediatorAppointedDate(Date mediatorAppointedDate) {
        this.mediatorAppointedDate = mediatorAppointedDate;
    }

    public boolean isMediatorReplacement() {
        return mediatorReplacement;
    }

    public void setMediatorReplacement(boolean mediatorReplacement) {
        this.mediatorReplacement = mediatorReplacement;
    }

    public String getStateMediatorAppointedID() {
        return stateMediatorAppointedID;
    }

    public void setStateMediatorAppointedID(String stateMediatorAppointedID) {
        this.stateMediatorAppointedID = stateMediatorAppointedID;
    }

    public String getFMCSMediatorAppointedID() {
        return FMCSMediatorAppointedID;
    }

    public void setFMCSMediatorAppointedID(String FMCSMediatorAppointedID) {
        this.FMCSMediatorAppointedID = FMCSMediatorAppointedID;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public boolean isSendToBoardToClose() {
        return sendToBoardToClose;
    }

    public void setSendToBoardToClose(boolean sendToBoardToClose) {
        this.sendToBoardToClose = sendToBoardToClose;
    }

    public Date getBoardFinalDate() {
        return boardFinalDate;
    }

    public void setBoardFinalDate(Date boardFinalDate) {
        this.boardFinalDate = boardFinalDate;
    }

    public Date getRetentionTicklerDate() {
        return retentionTicklerDate;
    }

    public void setRetentionTicklerDate(Date retentionTicklerDate) {
        this.retentionTicklerDate = retentionTicklerDate;
    }

    public boolean isLateFiling() {
        return lateFiling;
    }

    public void setLateFiling(boolean lateFiling) {
        this.lateFiling = lateFiling;
    }

    public boolean isImpasse() {
        return impasse;
    }

    public void setImpasse(boolean impasse) {
        this.impasse = impasse;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public boolean isTA() {
        return TA;
    }

    public void setTA(boolean TA) {
        this.TA = TA;
    }

    public boolean isMAD() {
        return MAD;
    }

    public void setMAD(boolean MAD) {
        this.MAD = MAD;
    }

    public boolean isWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(boolean withdrawl) {
        this.withdrawl = withdrawl;
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public Date getStrikeFileDate() {
        return strikeFileDate;
    }

    public void setStrikeFileDate(Date strikeFileDate) {
        this.strikeFileDate = strikeFileDate;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public boolean isUnauthorizedStrike() {
        return unauthorizedStrike;
    }

    public void setUnauthorizedStrike(boolean unauthorizedStrike) {
        this.unauthorizedStrike = unauthorizedStrike;
    }

    public boolean isNoticeOfIntentToStrikeOnly() {
        return noticeOfIntentToStrikeOnly;
    }

    public void setNoticeOfIntentToStrikeOnly(boolean noticeOfIntentToStrikeOnly) {
        this.noticeOfIntentToStrikeOnly = noticeOfIntentToStrikeOnly;
    }

    public Date getIntendedDateStrike() {
        return intendedDateStrike;
    }

    public void setIntendedDateStrike(Date intendedDateStrike) {
        this.intendedDateStrike = intendedDateStrike;
    }

    public boolean isNoticeOfIntentToPicketOnly() {
        return noticeOfIntentToPicketOnly;
    }

    public void setNoticeOfIntentToPicketOnly(boolean noticeOfIntentToPicketOnly) {
        this.noticeOfIntentToPicketOnly = noticeOfIntentToPicketOnly;
    }

    public Date getIntendedDatePicket() {
        return intendedDatePicket;
    }

    public void setIntendedDatePicket(Date intendedDatePicket) {
        this.intendedDatePicket = intendedDatePicket;
    }

    public boolean isInformational() {
        return informational;
    }

    public void setInformational(boolean informational) {
        this.informational = informational;
    }

    public boolean isNoticeOfIntentToStrikeAndPicket() {
        return noticeOfIntentToStrikeAndPicket;
    }

    public void setNoticeOfIntentToStrikeAndPicket(boolean noticeOfIntentToStrikeAndPicket) {
        this.noticeOfIntentToStrikeAndPicket = noticeOfIntentToStrikeAndPicket;
    }

    public String getStrikeOccured() {
        return strikeOccured;
    }

    public void setStrikeOccured(String strikeOccured) {
        this.strikeOccured = strikeOccured;
    }

    public String getStrikeStatus() {
        return strikeStatus;
    }

    public void setStrikeStatus(String strikeStatus) {
        this.strikeStatus = strikeStatus;
    }

    public Date getStrikeBegan() {
        return strikeBegan;
    }

    public void setStrikeBegan(Date strikeBegan) {
        this.strikeBegan = strikeBegan;
    }

    public Date getStrikeEnded() {
        return strikeEnded;
    }

    public void setStrikeEnded(Date strikeEnded) {
        this.strikeEnded = strikeEnded;
    }

    public String getTotalNumberOfDays() {
        return totalNumberOfDays;
    }

    public void setTotalNumberOfDays(String totalNumberOfDays) {
        this.totalNumberOfDays = totalNumberOfDays;
    }

    public String getStrikeMediatorAppointedID() {
        return strikeMediatorAppointedID;
    }

    public void setStrikeMediatorAppointedID(String strikeMediatorAppointedID) {
        this.strikeMediatorAppointedID = strikeMediatorAppointedID;
    }

    public String getStrikeNote() {
        return strikeNote;
    }

    public void setStrikeNote(String strikeNote) {
        this.strikeNote = strikeNote;
    }
    
    
}
