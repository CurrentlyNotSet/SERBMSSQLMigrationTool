/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Andrew
 */
public class REPCaseModel {

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
    private int SOIReturnIntials;
    private Date REPClosedCaseDueDate;
    private Date actualREPClosedDate;
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
    private int multicaseElection;
    private String electionType1;
    private String electionType2;
    private String electionType3;
    private Date eligibilityDate;
    private String ballotOne;
    private String ballotTwo;
    private String ballotThree;
    private String ballotFour;
    private Date mailKitDate;
    private Date pollingStartDate;
    private Date pollingEndDate;
    private String ballotsCountDay;
    private Date ballotsCountDate;
    private Timestamp ballotsCountTime;
    private Date eligibilityListDate;
    private Date preElectionConfDate;
    private String selfReleasing;
    private int resultApproxNumberEligibleVoters;
    private int resultVoidBallots;
    private int resultVotesCastForEEO;
    private int resultVotesCastForIncumbentEEO;
    private int resultVotesCastForRivalEEO1;
    private int resultVotesCastForRivalEEO2;
    private int resultVotesCastForRivalEEO3;
    private int resultVotesCastForNoRepresentative;
    private int resultValidVotesCounted;
    private int resultChallengedBallots;
    private int resultTotalBallotsCast;
    private int resultWhoPrevailed;
    private int professionalApproxNumberEligible;
    private int professionalYES;
    private int professionalNO;
    private int professionalChallenged;
    private int professionalTotalVotes;
    private String professionalOutcome;
    private int professionalWhoPrevailed;
    private int professionalVoidBallots;
    private int professionalValidVotes;
    private int professionalVotesCastForNoRepresentative;
    private int professionalVotesCastForEEO;
    private int professionalVotesCstForIncumbentEEO;
    private int professionalVotesCastForRivalEEO1;
    private int professionalVotesCastForRivalEEO2;
    private int professionalVotesCastForRivalEEO3;
    private int nonprofessionalApproxNumberEligible;
    private int nonprofessionalYES;
    private int nonprofessionalNO;
    private int nonprofessionalChallenged;
    private int nonprofessionalTotalVotes;
    private String nonprofessionalOutcome;
    private int nonprofessionalWhoPrevailed;
    private int nonprofessionalVoidBallots;
    private int nonprofessionalValidVotes;
    private int nonprofessionalVotesCastForNoRepresentative;
    private int nonprofessionalVotesCastForEEO;
    private int nonprofessionalVotesCastForIncumbentEEO;
    private int nonprofessionalVotesCastForRivalEEO1;
    private int nonprofessionalVotesCastForRivalEEO2;
    private int nonprofessionalVotesCastForRivalEEO3;
    private int combinedApproxNumberEligible;
    private int combinedYES;
    private int combinedNO;
    private int combinedChallenged;
    private int combinedTotalVotes;
    private String combinedOutcome;
    private int combinedWhoPrevailed;
    private int combinedVoidBallots;
    private int combinedValidVotes;
    private int combinedVotesCastForNoRepresentative;
    private int combinedVotesCastForEEO;
    private int combinedVotesCastForIncumbentEEO;
    private int combinedVotesCastForRivalEEO1;
    private int combinedVotesCastForRivalEEO2;
    private int combinedVotesCastForRivalEEO3;
    
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

    public int getSOIReturnIntials() {
        return SOIReturnIntials;
    }

    public void setSOIReturnIntials(int SOIReturnIntials) {
        this.SOIReturnIntials = SOIReturnIntials;
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

    public int getMulticaseElection() {
        return multicaseElection;
    }

    public void setMulticaseElection(int multicaseElection) {
        this.multicaseElection = multicaseElection;
    }

    public String getElectionType1() {
        return electionType1;
    }

    public void setElectionType1(String electionType1) {
        this.electionType1 = electionType1;
    }

    public String getElectionType2() {
        return electionType2;
    }

    public void setElectionType2(String electionType2) {
        this.electionType2 = electionType2;
    }

    public String getElectionType3() {
        return electionType3;
    }

    public void setElectionType3(String electionType3) {
        this.electionType3 = electionType3;
    }

    public Date getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public String getBallotOne() {
        return ballotOne;
    }

    public void setBallotOne(String ballotOne) {
        this.ballotOne = ballotOne;
    }

    public String getBallotTwo() {
        return ballotTwo;
    }

    public void setBallotTwo(String ballotTwo) {
        this.ballotTwo = ballotTwo;
    }

    public String getBallotThree() {
        return ballotThree;
    }

    public void setBallotThree(String ballotThree) {
        this.ballotThree = ballotThree;
    }

    public String getBallotFour() {
        return ballotFour;
    }

    public void setBallotFour(String ballotFour) {
        this.ballotFour = ballotFour;
    }

    public Date getMailKitDate() {
        return mailKitDate;
    }

    public void setMailKitDate(Date mailKitDate) {
        this.mailKitDate = mailKitDate;
    }

    public Date getPollingStartDate() {
        return pollingStartDate;
    }

    public void setPollingStartDate(Date pollingStartDate) {
        this.pollingStartDate = pollingStartDate;
    }

    public Date getPollingEndDate() {
        return pollingEndDate;
    }

    public void setPollingEndDate(Date pollingEndDate) {
        this.pollingEndDate = pollingEndDate;
    }

    public String getBallotsCountDay() {
        return ballotsCountDay;
    }

    public void setBallotsCountDay(String ballotsCountDay) {
        this.ballotsCountDay = ballotsCountDay;
    }

    public Date getBallotsCountDate() {
        return ballotsCountDate;
    }

    public void setBallotsCountDate(Date ballotsCountDate) {
        this.ballotsCountDate = ballotsCountDate;
    }

    public Timestamp getBallotsCountTime() {
        return ballotsCountTime;
    }

    public void setBallotsCountTime(Timestamp ballotsCountTime) {
        this.ballotsCountTime = ballotsCountTime;
    }

    public Date getEligibilityListDate() {
        return eligibilityListDate;
    }

    public void setEligibilityListDate(Date eligibilityListDate) {
        this.eligibilityListDate = eligibilityListDate;
    }

    public Date getPreElectionConfDate() {
        return preElectionConfDate;
    }

    public void setPreElectionConfDate(Date preElectionConfDate) {
        this.preElectionConfDate = preElectionConfDate;
    }

    public String getSelfReleasing() {
        return selfReleasing;
    }

    public void setSelfReleasing(String selfReleasing) {
        this.selfReleasing = selfReleasing;
    }

    public int getResultApproxNumberEligibleVoters() {
        return resultApproxNumberEligibleVoters;
    }

    public void setResultApproxNumberEligibleVoters(int resultApproxNumberEligibleVoters) {
        this.resultApproxNumberEligibleVoters = resultApproxNumberEligibleVoters;
    }

    public int getResultVoidBallots() {
        return resultVoidBallots;
    }

    public void setResultVoidBallots(int resultVoidBallots) {
        this.resultVoidBallots = resultVoidBallots;
    }

    public int getResultVotesCastForEEO() {
        return resultVotesCastForEEO;
    }

    public void setResultVotesCastForEEO(int resultVotesCastForEEO) {
        this.resultVotesCastForEEO = resultVotesCastForEEO;
    }

    public int getResultVotesCastForIncumbentEEO() {
        return resultVotesCastForIncumbentEEO;
    }

    public void setResultVotesCastForIncumbentEEO(int resultVotesCastForIncumbentEEO) {
        this.resultVotesCastForIncumbentEEO = resultVotesCastForIncumbentEEO;
    }

    public int getResultVotesCastForRivalEEO1() {
        return resultVotesCastForRivalEEO1;
    }

    public void setResultVotesCastForRivalEEO1(int reslutVotesCastForRivalEEO1) {
        this.resultVotesCastForRivalEEO1 = reslutVotesCastForRivalEEO1;
    }

    public int getResultVotesCastForRivalEEO2() {
        return resultVotesCastForRivalEEO2;
    }

    public void setResultVotesCastForRivalEEO2(int resultVotesCastForRivalEEO2) {
        this.resultVotesCastForRivalEEO2 = resultVotesCastForRivalEEO2;
    }

    public int getResultVotesCastForRivalEEO3() {
        return resultVotesCastForRivalEEO3;
    }

    public void setResultVotesCastForRivalEEO3(int resultVotesCastForRivalEEO3) {
        this.resultVotesCastForRivalEEO3 = resultVotesCastForRivalEEO3;
    }

    public int getResultVotesCastForNoRepresentative() {
        return resultVotesCastForNoRepresentative;
    }

    public void setResultVotesCastForNoRepresentative(int resultVotesCastForNoRepresentative) {
        this.resultVotesCastForNoRepresentative = resultVotesCastForNoRepresentative;
    }

    public int getResultValidVotesCounted() {
        return resultValidVotesCounted;
    }

    public void setResultValidVotesCounted(int resultValidVotesCounted) {
        this.resultValidVotesCounted = resultValidVotesCounted;
    }

    public int getResultChallengedBallots() {
        return resultChallengedBallots;
    }

    public void setResultChallengedBallots(int resultChallengedBallots) {
        this.resultChallengedBallots = resultChallengedBallots;
    }

    public int getResultTotalBallotsCast() {
        return resultTotalBallotsCast;
    }

    public void setResultTotalBallotsCast(int resultTotalBallotsCast) {
        this.resultTotalBallotsCast = resultTotalBallotsCast;
    }

    public int getResultWhoPrevailed() {
        return resultWhoPrevailed;
    }

    public void setResultWhoPrevailed(int resultWhoPrevailed) {
        this.resultWhoPrevailed = resultWhoPrevailed;
    }

    public int getProfessionalApproxNumberEligible() {
        return professionalApproxNumberEligible;
    }

    public void setProfessionalApproxNumberEligible(int professionalApproxNumberEligible) {
        this.professionalApproxNumberEligible = professionalApproxNumberEligible;
    }

    public int getProfessionalYES() {
        return professionalYES;
    }

    public void setProfessionalYES(int professionalYES) {
        this.professionalYES = professionalYES;
    }

    public int getProfessionalNO() {
        return professionalNO;
    }

    public void setProfessionalNO(int professionalNO) {
        this.professionalNO = professionalNO;
    }

    public int getProfessionalChallenged() {
        return professionalChallenged;
    }

    public void setProfessionalChallenged(int professionalChallenged) {
        this.professionalChallenged = professionalChallenged;
    }

    public int getProfessionalTotalVotes() {
        return professionalTotalVotes;
    }

    public void setProfessionalTotalVotes(int professionalTotalVotes) {
        this.professionalTotalVotes = professionalTotalVotes;
    }

    public String getProfessionalOutcome() {
        return professionalOutcome;
    }

    public void setProfessionalOutcome(String professionalOutcome) {
        this.professionalOutcome = professionalOutcome;
    }

    public int getProfessionalWhoPrevailed() {
        return professionalWhoPrevailed;
    }

    public void setProfessionalWhoPrevailed(int professionalWhoPrevailed) {
        this.professionalWhoPrevailed = professionalWhoPrevailed;
    }

    public int getProfessionalVoidBallots() {
        return professionalVoidBallots;
    }

    public void setProfessionalVoidBallots(int professionalVoidBallots) {
        this.professionalVoidBallots = professionalVoidBallots;
    }

    public int getProfessionalValidVotes() {
        return professionalValidVotes;
    }

    public void setProfessionalValidVotes(int professionalValidVotes) {
        this.professionalValidVotes = professionalValidVotes;
    }

    public int getProfessionalVotesCastForNoRepresentative() {
        return professionalVotesCastForNoRepresentative;
    }

    public void setProfessionalVotesCastForNoRepresentative(int professionalVotesCastForNoRepresentative) {
        this.professionalVotesCastForNoRepresentative = professionalVotesCastForNoRepresentative;
    }

    public int getProfessionalVotesCastForEEO() {
        return professionalVotesCastForEEO;
    }

    public void setProfessionalVotesCastForEEO(int professionalVotesCastForEEO) {
        this.professionalVotesCastForEEO = professionalVotesCastForEEO;
    }

    public int getProfessionalVotesCstForIncumbentEEO() {
        return professionalVotesCstForIncumbentEEO;
    }

    public void setProfessionalVotesCstForIncumbentEEO(int professionalVotesCstForIncumbentEEO) {
        this.professionalVotesCstForIncumbentEEO = professionalVotesCstForIncumbentEEO;
    }

    public int getProfessionalVotesCastForRivalEEO1() {
        return professionalVotesCastForRivalEEO1;
    }

    public void setProfessionalVotesCastForRivalEEO1(int professionalVotesCastForRivalEEO1) {
        this.professionalVotesCastForRivalEEO1 = professionalVotesCastForRivalEEO1;
    }

    public int getProfessionalVotesCastForRivalEEO2() {
        return professionalVotesCastForRivalEEO2;
    }

    public void setProfessionalVotesCastForRivalEEO2(int professionalVotesCastForRivalEEO2) {
        this.professionalVotesCastForRivalEEO2 = professionalVotesCastForRivalEEO2;
    }
    
    public int getProfessionalVotesCastForRivalEEO3() {
        return professionalVotesCastForRivalEEO3;
    }

    public void setProfessionalVotesCastForRivalEEO3(int professionalVotesCastForRivalEEO3) {
        this.professionalVotesCastForRivalEEO3 = professionalVotesCastForRivalEEO3;
    }

    public int getNonprofessionalApproxNumberEligible() {
        return nonprofessionalApproxNumberEligible;
    }

    public void setNonprofessionalApproxNumberEligible(int nonprofessionalApproxNumberEligible) {
        this.nonprofessionalApproxNumberEligible = nonprofessionalApproxNumberEligible;
    }

    public int getNonprofessionalYES() {
        return nonprofessionalYES;
    }

    public void setNonprofessionalYES(int nonprofessionalYES) {
        this.nonprofessionalYES = nonprofessionalYES;
    }

    public int getNonprofessionalNO() {
        return nonprofessionalNO;
    }

    public void setNonprofessionalNO(int nonprofessionalNO) {
        this.nonprofessionalNO = nonprofessionalNO;
    }

    public int getNonprofessionalChallenged() {
        return nonprofessionalChallenged;
    }

    public void setNonprofessionalChallenged(int nonprofessionalChallenged) {
        this.nonprofessionalChallenged = nonprofessionalChallenged;
    }

    public int getNonprofessionalTotalVotes() {
        return nonprofessionalTotalVotes;
    }

    public void setNonprofessionalTotalVotes(int nonprofessionalTotalVotes) {
        this.nonprofessionalTotalVotes = nonprofessionalTotalVotes;
    }

    public String getNonprofessionalOutcome() {
        return nonprofessionalOutcome;
    }

    public void setNonprofessionalOutcome(String nonprofessionalOutcome) {
        this.nonprofessionalOutcome = nonprofessionalOutcome;
    }

    public int getNonprofessionalWhoPrevailed() {
        return nonprofessionalWhoPrevailed;
    }

    public void setNonprofessionalWhoPrevailed(int nonprofessionalWhoPrevailed) {
        this.nonprofessionalWhoPrevailed = nonprofessionalWhoPrevailed;
    }

    public int getNonprofessionalVoidBallots() {
        return nonprofessionalVoidBallots;
    }

    public void setNonprofessionalVoidBallots(int nonprofessionalVoidBallots) {
        this.nonprofessionalVoidBallots = nonprofessionalVoidBallots;
    }

    public int getNonprofessionalValidVotes() {
        return nonprofessionalValidVotes;
    }

    public void setNonprofessionalValidVotes(int nonprofessionalValidVotes) {
        this.nonprofessionalValidVotes = nonprofessionalValidVotes;
    }

    public int getNonprofessionalVotesCastForNoRepresentative() {
        return nonprofessionalVotesCastForNoRepresentative;
    }

    public void setNonprofessionalVotesCastForNoRepresentative(int nonprofessionalVotesCastForNoRepresentative) {
        this.nonprofessionalVotesCastForNoRepresentative = nonprofessionalVotesCastForNoRepresentative;
    }

    public int getNonprofessionalVotesCastForEEO() {
        return nonprofessionalVotesCastForEEO;
    }

    public void setNonprofessionalVotesCastForEEO(int nonprofessionalVotesCastForEEO) {
        this.nonprofessionalVotesCastForEEO = nonprofessionalVotesCastForEEO;
    }

    public int getNonprofessionalVotesCastForIncumbentEEO() {
        return nonprofessionalVotesCastForIncumbentEEO;
    }

    public void setNonprofessionalVotesCastForIncumbentEEO(int nonprofessionalVotesCastForIncumbentEEO) {
        this.nonprofessionalVotesCastForIncumbentEEO = nonprofessionalVotesCastForIncumbentEEO;
    }

    public int getNonprofessionalVotesCastForRivalEEO1() {
        return nonprofessionalVotesCastForRivalEEO1;
    }

    public void setNonprofessionalVotesCastForRivalEEO1(int nonprofessionalVotesCastForRivalEEO1) {
        this.nonprofessionalVotesCastForRivalEEO1 = nonprofessionalVotesCastForRivalEEO1;
    }

    public int getNonprofessionalVotesCastForRivalEEO2() {
        return nonprofessionalVotesCastForRivalEEO2;
    }

    public void setNonprofessionalVotesCastForRivalEEO2(int nonprofessionalVotesCastForRivalEEO2) {
        this.nonprofessionalVotesCastForRivalEEO2 = nonprofessionalVotesCastForRivalEEO2;
    }

    public int getNonprofessionalVotesCastForRivalEEO3() {
        return nonprofessionalVotesCastForRivalEEO3;
    }

    public void setNonprofessionalVotesCastForRivalEEO3(int nonprofessionalVotesCastForRivalEEO3) {
        this.nonprofessionalVotesCastForRivalEEO3 = nonprofessionalVotesCastForRivalEEO3;
    }

    public int getCombinedApproxNumberEligible() {
        return combinedApproxNumberEligible;
    }

    public void setCombinedApproxNumberEligible(int combinedApproxNumberEligible) {
        this.combinedApproxNumberEligible = combinedApproxNumberEligible;
    }

    public int getCombinedYES() {
        return combinedYES;
    }

    public void setCombinedYES(int combinedYES) {
        this.combinedYES = combinedYES;
    }

    public int getCombinedNO() {
        return combinedNO;
    }

    public void setCombinedNO(int combinedNO) {
        this.combinedNO = combinedNO;
    }

    public int getCombinedChallenged() {
        return combinedChallenged;
    }

    public void setCombinedChallenged(int combinedChallenged) {
        this.combinedChallenged = combinedChallenged;
    }

    public int getCombinedTotalVotes() {
        return combinedTotalVotes;
    }

    public void setCombinedTotalVotes(int combinedTotalVotes) {
        this.combinedTotalVotes = combinedTotalVotes;
    }

    public String getCombinedOutcome() {
        return combinedOutcome;
    }

    public void setCombinedOutcome(String combinedOutcome) {
        this.combinedOutcome = combinedOutcome;
    }

    public int getCombinedWhoPrevailed() {
        return combinedWhoPrevailed;
    }

    public void setCombinedWhoPrevailed(int combinedWhoPrevailed) {
        this.combinedWhoPrevailed = combinedWhoPrevailed;
    }

    public int getCombinedVoidBallots() {
        return combinedVoidBallots;
    }

    public void setCombinedVoidBallots(int combinedVoidBallots) {
        this.combinedVoidBallots = combinedVoidBallots;
    }

    public int getCombinedValidVotes() {
        return combinedValidVotes;
    }

    public void setCombinedValidVotes(int combinedValidVotes) {
        this.combinedValidVotes = combinedValidVotes;
    }

    public int getCombinedVotesCastForNoRepresentative() {
        return combinedVotesCastForNoRepresentative;
    }

    public void setCombinedVotesCastForNoRepresentative(int combinedVotesCastForNoRepresentative) {
        this.combinedVotesCastForNoRepresentative = combinedVotesCastForNoRepresentative;
    }

    public int getCombinedVotesCastForEEO() {
        return combinedVotesCastForEEO;
    }

    public void setCombinedVotesCastForEEO(int combinedVotesCastForEEO) {
        this.combinedVotesCastForEEO = combinedVotesCastForEEO;
    }

    public int getCombinedVotesCastForIncumbentEEO() {
        return combinedVotesCastForIncumbentEEO;
    }

    public void setCombinedVotesCastForIncumbentEEO(int combinedVotesCastForIncumbentEEO) {
        this.combinedVotesCastForIncumbentEEO = combinedVotesCastForIncumbentEEO;
    }

    public int getCombinedVotesCastForRivalEEO1() {
        return combinedVotesCastForRivalEEO1;
    }

    public void setCombinedVotesCastForRivalEEO1(int combinedVotesCastForRivalEEO1) {
        this.combinedVotesCastForRivalEEO1 = combinedVotesCastForRivalEEO1;
    }

    public int getCombinedVotesCastForRivalEEO2() {
        return combinedVotesCastForRivalEEO2;
    }

    public void setCombinedVotesCastForRivalEEO2(int combinedVotesCastForRivalEEO2) {
        this.combinedVotesCastForRivalEEO2 = combinedVotesCastForRivalEEO2;
    }

    public int getCombinedVotesCastForRivalEEO3() {
        return combinedVotesCastForRivalEEO3;
    }

    public void setCombinedVotesCastForRivalEEO3(int combinedVotesCastForRivalEEO3) {
        this.combinedVotesCastForRivalEEO3 = combinedVotesCastForRivalEEO3;
    }

}
