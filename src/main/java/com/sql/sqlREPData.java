/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseModel;
import com.model.oldREPDataModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Andrew
 */
public class sqlREPData {

    public static List<oldREPDataModel> getCases() {
        List<oldREPDataModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPData WHERE caseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldREPDataModel item = new oldREPDataModel();
                item.setActive(rs.getByte("Active"));
                item.setCaseActive(rs.getString("CaseActive"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setCaseType(rs.getString("CaseType"));
                item.setStatus1(rs.getString("Status1"));
                item.setStatus2(rs.getString("Status2"));
                item.setCurrentOwner(rs.getString("CurrentOwner"));
                item.setCounty(rs.getString("County"));
                item.setEmployerIDNum(rs.getString("EmployerIDNum"));
                item.setBargainingUnitNum(rs.getString("BargainingUnitNum"));
                item.setBoardCertified(rs.getString("BoardCertified"));
                item.setDeemedCertified(rs.getString("DeemedCertified"));
                item.setFileDate(rs.getString("FileDate"));
                item.setFinalBoardDate(rs.getString("FinalBoardDate"));
                item.setRegLetterSentDate(rs.getString("RegLetterSentDate"));
                item.setDateOfAppeal(rs.getString("DateOfAppeal"));
                item.setCourtClosedDate(rs.getString("CourtClosedDate"));
                item.setReturnSOIDueDate(rs.getString("ReturnSOIDueDate"));
                item.setActualSOIReturnDate(rs.getString("ActualSOIReturnDate"));
                item.setSOIReturnInitials1(rs.getString("SOIReturnInitials1"));
                item.setREPClosedCaseDueDate(rs.getString("REPClosedCaseDueDate"));
                item.setActualREPClosedDate(rs.getString("ActualREPClosedDate"));
                item.setSOIReturnInitials2(rs.getString("SOIReturnInitials2"));
                item.setBoardActionType(rs.getString("BoardActionType"));
                item.setBoardActionDate(rs.getString("BoardActionDate"));
                item.setBoardPersonAssigned(rs.getString("BoardPersonAssigned"));
                item.setBoardMeetingDate1(rs.getString("BoardMeetingDate1"));
                item.setAgendaItem1(rs.getString("AgendaItem1"));
                item.setRecommendation1(rs.getString("Recommendation1"));
                item.setBoardMeetingDate2(rs.getString("BoardMeetingDate2"));
                item.setAgendaItem2(rs.getString("AgendaItem2"));
                item.setRecommendation2(rs.getString("Recommendation2"));
                item.setBoardMeetingDate3(rs.getString("BoardMeetingDate3"));
                item.setAgendaItem3(rs.getString("AgendaItem3"));
                item.setRecommendation3(rs.getString("Recommendation3"));
                item.setBoardMeetingDate4(rs.getString("BoardMeetingDate4"));
                item.setAgendaItem4(rs.getString("AgendaItem4"));
                item.setRecommendation4(rs.getString("Recommendation4"));
                item.setBoardMeetingDate5(rs.getString("BoardMeetingDate5"));
                item.setAgendaItem5(rs.getString("AgendaItem5"));
                item.setRecommendation5(rs.getString("Recommendation5"));
                item.setInternalMediationDate(rs.getString("InternalMediationDate"));
                item.setInternalMediationTime(rs.getString("InternalMediationTime"));
                item.setInternalMediator(rs.getString("InternalMediator"));
                item.setInternaResult(rs.getString("InternaResult"));
                item.setBoardDirMedDate(rs.getString("BoardDirMedDate"));
                item.setBoardDirMedMeetingDate(rs.getString("BoardDirMedMeetingDate"));
                item.setBoardDirMedMeetingTime(rs.getString("BoardDirMedMeetingTime"));
                item.setBoardDirectedMediatior(rs.getString("BoardDirectedMediatior"));
                item.setBoardDirectedMedResult(rs.getString("BoardDirectedMedResult"));
                item.setPostDirMedDate(rs.getString("PostDirMedDate"));
                item.setPostDirMedMeetingDate(rs.getString("PostDirMedMeetingDate"));
                item.setPostDirMedMeetingTime(rs.getString("PostDirMedMeetingTime"));
                item.setPostDirMediatior(rs.getString("PostDirMediatior"));
                item.setPostDirMedResult(rs.getString("PostDirMedResult"));
                item.setCaseFiledBy(rs.getString("CaseFiledBy"));
                item.setToReflect(rs.getString("ToReflect"));
                item.setTypeFiledBy(rs.getString("TypeFiledBy"));
                item.setTypeFiledHow(rs.getString("TypeFiledHow"));
                item.setPSFiledBy(rs.getString("PSFiledBy"));
                item.setEEONameFrom(rs.getString("EEONameFrom"));
                item.setEEONameTo(rs.getString("EEONameTo"));
                item.setERNameFrom(rs.getString("ERNameFrom"));
                item.setERNameTo(rs.getString("ERNameTo"));
                item.setMultiCaseElection(rs.getString("MultiCaseElection"));
                item.setElectionCaseNumber1(rs.getString("ElectionCaseNumber1"));
                item.setElectionCaseNumber2(rs.getString("ElectionCaseNumber2"));
                item.setElectionCaseNumber3(rs.getString("ElectionCaseNumber3"));
                item.setElectionCaseNumber4(rs.getString("ElectionCaseNumber4"));
                item.setElectionCaseNumber5(rs.getString("ElectionCaseNumber5"));
                item.setElectionCaseNumber6(rs.getString("ElectionCaseNumber6"));
                item.setElectionType1(rs.getString("ElectionType1"));
                item.setElectionType2(rs.getString("ElectionType2"));
                item.setElectionType3(rs.getString("ElectionType3"));
                item.setEligibilityDate(rs.getString("EligibilityDate") == null ? "" : rs.getString("EligibilityDate").trim());
                item.setBallotOne(rs.getString("BallotOne"));
                item.setBallotTwo(rs.getString("BallotTwo"));
                item.setBallotThree(rs.getString("BallotThree"));
                item.setBallotFour(rs.getString("BallotFour"));
                item.setPollingStartDate(rs.getString("PollingStartDate") == null ? "" : rs.getString("PollingStartDate").trim());
                item.setPollingEndDate(rs.getString("PollingEndDate") == null ? "" : rs.getString("PollingEndDate").trim());
                item.setBallotsCountDay(rs.getString("BallotsCountDay"));
                item.setBallotsCountDate(rs.getString("BallotsCountDate") == null ? "" : rs.getString("BallotsCountDate").trim());
                item.setBallotsCountTime(rs.getString("BallotsCountTime"));
                item.setPreElectionConfDate(rs.getString("PreElectionConfDate"));
                item.setSelfReleasing(rs.getString("SelfReleasing"));
                item.setSite1Date(rs.getString("Site1Date"));
                item.setSite2Date(rs.getString("Site2Date"));
                item.setSite3Date(rs.getString("Site3Date"));
                item.setSite1Time(rs.getString("Site1Time"));
                item.setSite2Time(rs.getString("Site2Time"));
                item.setSite3Time(rs.getString("Site3Time"));
                item.setSite1Place(rs.getString("Site1Place"));
                item.setSite2Place(rs.getString("Site2Place"));
                item.setSite3Place(rs.getString("Site3Place"));
                item.setSite1Address1(rs.getString("Site1Address1"));
                item.setSite1Address2(rs.getString("Site1Address2"));
                item.setSite2Address1(rs.getString("Site2Address1"));
                item.setSite2Address2(rs.getString("Site2Address2"));
                item.setSite3Address1(rs.getString("Site3Address1"));
                item.setSite3Address2(rs.getString("Site3Address2"));
                item.setSite1Location(rs.getString("Site1Location"));
                item.setSite2Location(rs.getString("Site2Location"));
                item.setSite3Location(rs.getString("Site3Location"));
                item.setResultsApproxNumEligible(rs.getString("ResultsApproxNumEligible"));
                item.setResultsVoidBallots(rs.getString("ResultsVoidBallots"));
                item.setResultsVotesForEEO(rs.getString("ResultsVotesForEEO"));
                item.setResultsVotesForRival(rs.getString("ResultsVotesForRival"));
                item.setResultsVaildCounted(rs.getString("ResultsVaildCounted"));
                item.setResultsChallenged(rs.getString("ResultsChallenged"));
                item.setResultsTotalVotesCast(rs.getString("ResultsTotalVotesCast"));
                item.setResultsVotesForNoREP(rs.getString("ResultsVotesForNoREP"));
                item.setResultsWhoPrevailed(rs.getString("ResultsWhoPrevailed"));
                item.setPApproxNumEligible(rs.getString("PApproxNumEligible"));
                item.setPYES(rs.getString("PYES"));
                item.setPNO(rs.getString("PNO"));
                item.setPChallenged(rs.getString("PChallenged"));
                item.setPTotalVotes(rs.getString("PTotalVotes"));
                item.setPOutcome(rs.getString("POutcome"));
                item.setPWhoPrevailed(rs.getString("PWhoPrevailed"));
                item.setPVoidBallots(rs.getString("PVoidBallots"));
                item.setPValidVotes(rs.getString("PValidVotes"));
                item.setPVotesforNoREP(rs.getString("PVotesforNoREP"));
                item.setPVotesforEEO(rs.getString("PVotesforEEO"));
                item.setPVotesforIncumbentEEO(rs.getString("PVotesforIncumbentEEO"));
                item.setPVotesCastforRivalEEO1(rs.getString("PVotesCastforRivalEEO1"));
                item.setPVotesCastforRivalEEO2(rs.getString("PVotesCastforRivalEEO2"));
                item.setPVotesCastforRivalEEO3(rs.getString("PVotesCastforRivalEEO3"));
                item.setNPApproxNumEligible(rs.getString("NPApproxNumEligible"));
                item.setNPYES(rs.getString("NPYES"));
                item.setNPNO(rs.getString("NPNO"));
                item.setNPChallenged(rs.getString("NPChallenged"));
                item.setNPTotalVotes(rs.getString("NPTotalVotes"));
                item.setNPOutcome(rs.getString("NPOutcome"));
                item.setNPWhoPrevailed(rs.getString("NPWhoPrevailed"));
                item.setNPVoidBallots(rs.getString("NPVoidBallots"));
                item.setNPValidVotes(rs.getString("NPValidVotes"));
                item.setNPVotesforNoREP(rs.getString("NPVotesforNoREP"));
                item.setNPVotesforEEO(rs.getString("NPVotesforEEO"));
                item.setNPVotesforIncumbentEEO(rs.getString("NPVotesforIncumbentEEO"));
                item.setNPVotesCastforRivalEEO1(rs.getString("NPVotesCastforRivalEEO1"));
                item.setNPVotesCastforRivalEEO2(rs.getString("NPVotesCastforRivalEEO2"));
                item.setNPVotesCastforRivalEEO3(rs.getString("NPVotesCastforRivalEEO3"));
                item.setCApproxNumEligible(rs.getString("CApproxNumEligible"));
                item.setCYes(rs.getString("CYes"));
                item.setCNo(rs.getString("CNo"));
                item.setCChallenged(rs.getString("CChallenged"));
                item.setCTotalVotes(rs.getString("CTotalVotes"));
                item.setCOutcome(rs.getString("COutcome"));
                item.setCWhoPrevailed(rs.getString("CWhoPrevailed"));
                item.setCVoidBallots(rs.getString("CVoidBallots"));
                item.setCVaildVotes(rs.getString("CVaildVotes"));
                item.setCVotesForNoREP(rs.getString("CVotesForNoREP"));
                item.setCVotesforEEO(rs.getString("CVotesforEEO"));
                item.setCVotesForIncumbentEEO(rs.getString("CVotesForIncumbentEEO"));
                item.setCVotesCastforRivalEEO1(rs.getString("CVotesCastforRivalEEO1"));
                item.setCVotesCastforRivalEEO2(rs.getString("CVotesCastforRivalEEO2"));
                item.setCVotesCastforRivalEEO3(rs.getString("CVotesCastforRivalEEO3"));
                item.setNotesDescript(rs.getString("NotesDescript"));
                item.setNotesStatus(rs.getString("NotesStatus"));
                item.setPName(rs.getString("PName"));
                item.setPAddress1(rs.getString("PAddress1"));
                item.setPAddress2(rs.getString("PAddress2"));
                item.setPCity(rs.getString("PCity"));
                item.setPState(rs.getString("PState"));
                item.setPZip(rs.getString("PZip"));
                item.setPPhone(rs.getString("PPhone"));
                item.setPEmail(rs.getString("PEmail"));
                item.setPAsstName(rs.getString("PAsstName"));
                item.setPAsstEmail(rs.getString("PAsstEmail"));
                item.setPREPName(rs.getString("PREPName"));
                item.setPREPAddress1(rs.getString("PREPAddress1"));
                item.setPREPAddress2(rs.getString("PREPAddress2"));
                item.setPREPCity(rs.getString("PREPCity"));
                item.setPREPState(rs.getString("PREPState"));
                item.setPREPZip(rs.getString("PREPZip"));
                item.setPREPPhone(rs.getString("PREPPhone"));
                item.setPREPEmail(rs.getString("PREPEmail"));
                item.setPREPAsstName(rs.getString("PREPAsstName"));
                item.setPREPAsstEmail(rs.getString("PREPAsstEmail"));
                item.setEName(rs.getString("EName"));
                item.setEAddress1(rs.getString("EAddress1"));
                item.setEAddress2(rs.getString("EAddress2"));
                item.setECity(rs.getString("ECity"));
                item.setEState(rs.getString("EState"));
                item.setEZip(rs.getString("EZip"));
                item.setEPhone(rs.getString("EPhone"));
                item.setEEmail(rs.getString("EEmail"));
                item.setEAsstName(rs.getString("EAsstName"));
                item.setEAsstEmail(rs.getString("EAsstEmail"));
                item.setEREPName(rs.getString("EREPName"));
                item.setEREPAddress1(rs.getString("EREPAddress1"));
                item.setEREPAddress2(rs.getString("EREPAddress2"));
                item.setEREPCity(rs.getString("EREPCity"));
                item.setEREPState(rs.getString("EREPState"));
                item.setEREPZip(rs.getString("EREPZip"));
                item.setEREPPhone(rs.getString("EREPPhone"));
                item.setEREPEmail(rs.getString("EREPEmail"));
                item.setEREPAsstName(rs.getString("EREPAsstName"));
                item.setEREPAsstEmail(rs.getString("EREPAsstEmail"));
                item.setEOName(rs.getString("EOName"));
                item.setEOAddress1(rs.getString("EOAddress1"));
                item.setEOAddress2(rs.getString("EOAddress2"));
                item.setEOCity(rs.getString("EOCity"));
                item.setEOState(rs.getString("EOState"));
                item.setEOZip(rs.getString("EOZip"));
                item.setEOPhone(rs.getString("EOPhone"));
                item.setEOEmail(rs.getString("EOEmail"));
                item.setEOAsstName(rs.getString("EOAsstName"));
                item.setEOAsstEmail(rs.getString("EOAsstEmail"));
                item.setEOREPName(rs.getString("EOREPName"));
                item.setEOREPAddress1(rs.getString("EOREPAddress1"));
                item.setEOREPAddress2(rs.getString("EOREPAddress2"));
                item.setEOREPCity(rs.getString("EOREPCity"));
                item.setEOREPState(rs.getString("EOREPState"));
                item.setEOREPZip(rs.getString("EOREPZip"));
                item.setEOREPPhone(rs.getString("EOREPPhone"));
                item.setEOREPEmail(rs.getString("EOREPEmail"));
                item.setEOREPAsstName(rs.getString("EOREPAsstName"));
                item.setEOREPAsstEmail(rs.getString("EOREPAsstEmail"));
                item.setREOName(rs.getString("REOName"));
                item.setREO2Name(rs.getString("REO2Name"));
                item.setREO3Name(rs.getString("REO3Name"));
                item.setREOAddress1(rs.getString("REOAddress1"));
                item.setREO2Address1(rs.getString("REO2Address1"));
                item.setREO3Address1(rs.getString("REO3Address1"));
                item.setREOAddress2(rs.getString("REOAddress2"));
                item.setREO2Address2(rs.getString("REO2Address2"));
                item.setREO3Address2(rs.getString("REO3Address2"));
                item.setREOCity(rs.getString("REOCity"));
                item.setREO2City(rs.getString("REO2City"));
                item.setREO3City(rs.getString("REO3City"));
                item.setREOState(rs.getString("REOState"));
                item.setREO2State(rs.getString("REO2State"));
                item.setREO3State(rs.getString("REO3State"));
                item.setREOZip(rs.getString("REOZip"));
                item.setREO2Zip(rs.getString("REO2Zip"));
                item.setREO3Zip(rs.getString("REO3Zip"));
                item.setREOPhone(rs.getString("REOPhone"));
                item.setREO2Phone(rs.getString("REO2Phone"));
                item.setREO3Phone(rs.getString("REO3Phone"));
                item.setREOEmail(rs.getString("REOEmail"));
                item.setREO2Email(rs.getString("REO2Email"));
                item.setREO3Email(rs.getString("REO3Email"));
                item.setREOAsstName(rs.getString("REOAsstName"));
                item.setREO2AsstName(rs.getString("REO2AsstName"));
                item.setREO3AsstName(rs.getString("REO3AsstName"));
                item.setREOAsstEmail(rs.getString("REOAsstEmail"));
                item.setREO2AsstEmail(rs.getString("REO2AsstEmail"));
                item.setREO3AsstEmail(rs.getString("REO3AsstEmail"));
                item.setREOREPName(rs.getString("REOREPName"));
                item.setREO2REPName(rs.getString("REO2REPName"));
                item.setREO3REPName(rs.getString("REO3REPName"));
                item.setREOREPAddress1(rs.getString("REOREPAddress1"));
                item.setREO2REPAddress1(rs.getString("REO2REPAddress1"));
                item.setREO3REPAddress1(rs.getString("REO3REPAddress1"));
                item.setREOREPAddress2(rs.getString("REOREPAddress2"));
                item.setREO2REPAddress2(rs.getString("REO2REPAddress2"));
                item.setREO3REPAddress2(rs.getString("REO3REPAddress2"));
                item.setREOREPCity(rs.getString("REOREPCity"));
                item.setREO2REPCity(rs.getString("REO2REPCity"));
                item.setREO3REPCity(rs.getString("REO3REPCity"));
                item.setREOREPState(rs.getString("REOREPState"));
                item.setREO2REPState(rs.getString("REO2REPState"));
                item.setREO3REPState(rs.getString("REO3REPState"));
                item.setREOREPZip(rs.getString("REOREPZip"));
                item.setREO2REPZip(rs.getString("REO2REPZip"));
                item.setREO3REPZip(rs.getString("REO3REPZip"));
                item.setREOREPPhone(rs.getString("REOREPPhone"));
                item.setREO2REPPhone(rs.getString("REO2REPPhone"));
                item.setREO3REPPhone(rs.getString("REO3REPPhone"));
                item.setREOREPEmail(rs.getString("REOREPEmail"));
                item.setREO2REPEmail(rs.getString("REO2REPEmail"));
                item.setREO3REPEmail(rs.getString("REO3REPEmail"));
                item.setREOREPAsstName(rs.getString("REOREPAsstName"));
                item.setREO2REPAsstName(rs.getString("REO2REPAsstName"));
                item.setREO3REPAsstName(rs.getString("REO3REPAsstName"));
                item.setREOREPAsstEmail(rs.getString("REOREPAsstEmail"));
                item.setREO2REPAsstEmail(rs.getString("REO2REPAsstEmail"));
                item.setREO3REPAsstEmail(rs.getString("REO3REPAsstEmail"));
                item.setIEOName(rs.getString("IEOName"));
                item.setIEOAddress1(rs.getString("IEOAddress1"));
                item.setIEOAddress2(rs.getString("IEOAddress2"));
                item.setIEOCity(rs.getString("IEOCity"));
                item.setIEOState(rs.getString("IEOState"));
                item.setIEOZip(rs.getString("IEOZip"));
                item.setIEOPhone(rs.getString("IEOPhone"));
                item.setIEOEmail(rs.getString("IEOEmail"));
                item.setIEOAsstName(rs.getString("IEOAsstName"));
                item.setIEOAsstEmail(rs.getString("IEOAsstEmail"));
                item.setIEOREPName(rs.getString("IEOREPName"));
                item.setIEOREPAddress1(rs.getString("IEOREPAddress1"));
                item.setIEOREPAddress2(rs.getString("IEOREPAddress2"));
                item.setIEOREPCity(rs.getString("IEOREPCity"));
                item.setIEOREPState(rs.getString("IEOREPState"));
                item.setIEOREPZip(rs.getString("IEOREPZip"));
                item.setIEOREPPhone(rs.getString("IEOREPPhone"));
                item.setIEOREPEmail(rs.getString("IEOREPEmail"));
                item.setIEOREPAsstName(rs.getString("IEOREPAsstName"));
                item.setIEOREPAsstEmail(rs.getString("IEOREPAsstEmail"));
                item.setIName(rs.getString("IName"));
                item.setIAddress1(rs.getString("IAddress1"));
                item.setIAddress2(rs.getString("IAddress2"));
                item.setICity(rs.getString("ICity"));
                item.setIState(rs.getString("IState"));
                item.setIZip(rs.getString("IZip"));
                item.setIPhone(rs.getString("IPhone"));
                item.setIEmail(rs.getString("IEmail"));
                item.setIAsstName(rs.getString("IAsstName"));
                item.setIAsstEmail(rs.getString("IAsstEmail"));
                item.setIREPName(rs.getString("IREPName"));
                item.setIREPAddress1(rs.getString("IREPAddress1"));
                item.setIREPAddress2(rs.getString("IREPAddress2"));
                item.setIREPCity(rs.getString("IREPCity"));
                item.setIREPState(rs.getString("IREPState"));
                item.setIREPZip(rs.getString("IREPZip"));
                item.setIREPPhone(rs.getString("IREPPhone"));
                item.setIREPEmail(rs.getString("IREPEmail"));
                item.setIREPAsstName(rs.getString("IREPAsstName"));
                item.setIREPAsstEmail(rs.getString("IREPAsstEmail"));
                item.setCSName(rs.getString("CSName"));
                item.setCSAddress1(rs.getString("CSAddress1"));
                item.setCSAddress2(rs.getString("CSAddress2"));
                item.setCSCity(rs.getString("CSCity"));
                item.setCSState(rs.getString("CSState"));
                item.setCSZip(rs.getString("CSZip"));
                item.setCSPhone(rs.getString("CSPhone"));
                item.setCSEmail(rs.getString("CSEmail"));
                item.setCSAsstName(rs.getString("CSAsstName"));
                item.setCSAsstEmail(rs.getString("CSAsstEmail"));
                item.setCSREPName(rs.getString("CSREPName"));
                item.setCSREPAddress1(rs.getString("CSREPAddress1"));
                item.setCSREPAddress2(rs.getString("CSREPAddress2"));
                item.setCSREPCity(rs.getString("CSREPCity"));
                item.setCSREPState(rs.getString("CSREPState"));
                item.setCSREPZip(rs.getString("CSREPZip"));
                item.setCSREPPhone(rs.getString("CSREPPhone"));
                item.setCSREPEmail(rs.getString("CSREPEmail"));
                item.setCSREPAsstName(rs.getString("CSREPAsstName"));
                item.setCSREPAsstEmail(rs.getString("CSREPAsstEmail"));
                item.setPNonP(rs.getString("PNonP"));
                item.setNotesDescription(rs.getString("NotesDescription"));
                item.setNotesPanelStatus(rs.getString("NotesPanelStatus"));
                item.setActualClerksClosed(rs.getString("ActualClerksClosed"));
                item.setClearksClosedInitials(rs.getString("ClearksClosedInitials"));
                item.setBoardRec(rs.getString("BoardRec"));
                item.setResultsVotesForIncumbent(rs.getString("ResultsVotesForIncumbent"));
                item.setResultsVotesForRival2(rs.getString("ResultsVotesForRival2"));
                item.setResultsVotesForRival3(rs.getString("ResultsVotesForRival3"));
                item.setREOEmployeeOrgNum(rs.getString("REOEmployeeOrgNum"));
                item.setREO2EmployeeOrgNum(rs.getString("REO2EmployeeOrgNum"));
                item.setREO3EmployeeOrgNum(rs.getString("REO3EmployeeOrgNum"));
                item.setIncumbentEmployeeOrgNum(rs.getString("IncumbentEmployeeOrgNum"));
                item.setMailKitDate(rs.getString("MailKitDate") == null ? "" : rs.getString("MailKitDate").trim());
                item.setEligibilityListDate(rs.getString("EligibilityListDate") == null ? "" : rs.getString("EligibilityListDate").trim());
                item.setAmendedFilingDate(rs.getString("AmendedFilingDate"));
                item.setCertRevoked(rs.getString("CertRevoked"));
                item.setEmployerIDNum2(rs.getString("EmployerIDNum2"));
                item.setEmployeeOrgIDNum(rs.getString("EmployeeOrgIDNum"));
                item.setIntervenorNumber(rs.getString("IntervenorNumber"));
                item.setMemoDate1(rs.getString("MemoDate1"));
                item.setMemoDate2(rs.getString("MemoDate2"));
                item.setMemoDate3(rs.getString("MemoDate3"));
                item.setMemoDate4(rs.getString("MemoDate4"));
                item.setMemoDate5(rs.getString("MemoDate5"));
                item.setDeptInState(rs.getString("DeptInState"));
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }

    public static void batchAddREPCase(List<REPCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO REPcase ("
                    + "active, "            //01
                    + "caseYear, "          //02
                    + "caseType, "          //03
                    + "caseMonth, "         //04
                    + "caseNumber, "        //05
                    + "type, "              //06
                    + "status1, "           //07
                    + "status2, "           //08
                    + "currentOwnerID, "    //09
                    + "county, "            //10
                    + "employerIDNumber, "  //11
                    + "deptInState, "       //12
                    + "bargainingUnitNumber, "  //13
                    + "boardCertified, "        //14
                    + "deemedCertified, "       //15
                    + "certificationRevoked, "  //16
                    + "fileDate, "              //17
                    + "amendedFilingDate, "     //18
                    + "finalBoardDate, "        //19
                    + "registrationLetterSent, "//20
                    + "dateOfAppeal, "          //21
                    + "courtClosedDate, "       //22
                    + "returnSOIDueDate, "      //23
                    + "actualSOIReturnDate, "   //24
                    + "SOIReturnIntials, "      //25
                    + "REPClosedCaseDueDate, "  //26
                    + "actualREPClosedDate, "   //27
                    + "REPClosedUser, "         //28
                    + "actualClerksClosedDate, "//29
                    + "clerksClosedUser, "      //30
                    + "note, "                  //31
                    + "alphaListDate, "         //32
                    + "fileBy, "                //33
                    + "bargainingUnitIncluded, "//34
                    + "bargainingUnitExcluded, "//35
                    + "optInIncluded, "         //36
                    + "professionalNonProfessional, "//37
                    + "professionalIncluded, "       //38
                    + "professionalExcluded, "       //39
                    + "nonProfessionalIncluded, "    //40
                    + "nonProfessionalExcluded, "    //41
                    + "toReflect, "                  //42
                    + "typeFiledBy, "                //43
                    + "typeFiledVia, "               //44
                    + "positionStatementFiledBy, "   //45
                    + "EEONameChangeFrom, "  //46
                    + "EEONameChangeTo, "    //47
                    + "ERNameChangeFrom, "   //48
                    + "ERNameChangeTo, "     //49
                    + "boardActionType, "    //50
                    + "boardActionDate, "    //51
                    + "hearingPersonID, "    //52
                    + "boardStatusNote, "    //53
                    + "boardStatusBlurb, "   //54
                    + "multicaseElection, "  //55
                    + "electionType1, "      //56
                    + "electionType2, "      //57
                    + "electionType3, "      //58
                    + "eligibilityDate, "    //59
                    + "ballotOne, "          //60
                    + "ballotTwo, "          //61
                    + "ballotThree, "        //62
                    + "ballotFour, "         //63
                    + "mailKitDate, "        //64
                    + "pollingStartDate, "   //65
                    + "pollingEndDate, "     //66
                    + "ballotsCountDay, "    //67
                    + "ballotsCountDate, "   //68
                    + "ballotsCountTime, "   //69
                    + "eligibilityListDate, "//70
                    + "preElectionConfDate, "//71
                    + "selfReleasing, "      //72
                    + "resultApproxNumberEligibleVoters, "  //73
                    + "resultVoidBallots, "                 //74
                    + "resultVotesCastForEEO, "             //75
                    + "resultVotesCastForIncumbentEEO, "    //76
                    + "resultVotesCastForRivalEEO1, "       //77
                    + "resultVotesCastForRivalEEO2, "       //78
                    + "resultVotesCastForRivalEEO3, "       //79
                    + "resultVotesCastForNoRepresentative, "//80
                    + "resultValidVotesCounted, "           //81
                    + "resultChallengedBallots, "           //82
                    + "resultTotalBallotsCast, "            //83
                    + "professionalApproxNumberEligible, "  //84
                    + "professionalYES, "                   //85
                    + "professionalNO, "                    //86
                    + "professionalChallenged, "            //87
                    + "professionalTotalVotes, "            //88
                    + "professionalOutcome, "               //89
                    + "professionalVoidBallots, "           //90
                    + "professionalValidVotes, "            //91
                    + "professionalVotesCastForNoRepresentative, "  //92
                    + "professionalVotesCastForEEO, "           //93
                    + "professionalVotesCastForIncumbentEEO, "  //94
                    + "professionalVotesCastForRivalEEO1, "     //95
                    + "professionalVotesCastForRivalEEO2, "     //96
                    + "professionalVotesCastForRivalEEO3, "     //97
                    + "nonprofessionalApproxNumberEligible, "   //98
                    + "nonprofessionalYES, "                    //99
                    + "nonprofessionalNO, "                     //100
                    + "nonprofessionalChallenged, "             //101
                    + "nonprofessionalTotalVotes, "             //102
                    + "nonprofessionalOutcome, "                //103
                    + "nonprofessionalVoidBallots, "            //104
                    + "nonprofessionalValidVotes, "             //105
                    + "nonprofessionalVotesCastForNoRepresentative, "//106
                    + "nonprofessionalVotesCastForEEO, "             //107
                    + "nonprofessionalVotesCastForIncumbentEEO, "    //108
                    + "nonprofessionalVotesCastForRivalEEO1, "  //109
                    + "nonprofessionalVotesCastForRivalEEO2, "  //110
                    + "nonprofessionalVotesCastForRivalEEO3, "  //111
                    + "combinedApproxNumberEligible, "          //112
                    + "combinedYES, "                           //113
                    + "combinedNO, "                            //114
                    + "combinedChallenged, "                    //115
                    + "combinedTotalVotes, "                    //116
                    + "combinedOutcome, "                       //117
                    + "combinedVoidBallots, "                   //118
                    + "combinedValidVotes, "                    //119
                    + "combinedVotesCastForNoRepresentative, "  //120
                    + "combinedVotesCastForEEO, "               //121
                    + "combinedVotesCastForIncumbentEEO, "      //122
                    + "combinedVotesCastForRivalEEO1, "         //123
                    + "combinedVotesCastForRivalEEO2, "         //124
                    + "combinedVotesCastForRivalEEO3, "         //125
                    + "resultWhoPrevailed, "                    //126
                    + "professionalWhoPrevailed, "              //127
                    + "nonprofessionalWhoPrevailed, "           //128
                    + "combinedWhoPrevailed "                   //129
                    + ") VALUES (";
                    for(int i=0; i<128; i++){
                        sql += "?, ";   //01-128
                    }
                     sql += "?)";   //129
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (REPCaseModel item : list){
                ps.setInt      ( 1, item.getActive());
                ps.setString   ( 2, StringUtils.left(item.getCaseYear(), 4));
                ps.setString   ( 3, StringUtils.left(item.getCaseType(), 3));
                ps.setString   ( 4, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString   ( 5, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString   ( 6, StringUtils.left(item.getType(), 20));
                ps.setString   ( 7, StringUtils.left(item.getStatus1(), 20));
                ps.setString   ( 8, StringUtils.left(item.getStatus2(), 50));
                if (item.getCurrentOwnerID() != 0){
                    ps.setInt  ( 9, item.getCurrentOwnerID());
                } else {
                    ps.setNull ( 9, java.sql.Types.INTEGER);
                }
                ps.setString   (10, StringUtils.left(item.getCounty(), 20));
                ps.setString   (11, StringUtils.left(item.getEmployerIDNumber(), 10));
                ps.setString   (12, StringUtils.left(item.getDeptInState(), 3));
                ps.setString   (13, StringUtils.left(item.getBargainingUnitNumber(), 10));
                ps.setInt      (14, item.getBoardCertified());
                ps.setInt      (15, item.getDeemedCertified());
                ps.setInt      (16, item.getCertificationRevoked());
                ps.setDate     (17, item.getFileDate());
                ps.setDate     (18, item.getAmendedFilingDate());
                ps.setDate     (19, item.getFinalBoardDate());
                ps.setDate     (20, item.getRegistrationLetterSent());
                ps.setDate     (21, item.getDateOfAppeal());
                ps.setDate     (22, item.getCourtClosedDate());
                ps.setDate     (23, item.getReturnSOIDueDate());
                ps.setDate     (24, item.getActualSOIReturnDate());
                if (item.getSOIReturnIntials() != 0){
                    ps.setInt  (25, item.getSOIReturnIntials());
                } else {
                    ps.setNull (25, java.sql.Types.INTEGER);
                }
                ps.setDate     (26, item.getREPClosedCaseDueDate());
                ps.setDate     (27, item.getActualREPClosedDate());
                if (item.getREPClosedUser() != 0){
                    ps.setInt  (28, item.getREPClosedUser());
                } else {
                    ps.setNull (28, java.sql.Types.INTEGER);
                }
                ps.setDate     (29, item.getActualClerksClosedDate());
                if (item.getClerksClosedUser() != 0){
                    ps.setInt  (30, item.getClerksClosedUser());
                } else {
                    ps.setNull (30, java.sql.Types.INTEGER);
                }
                ps.setString   (31, item.getNote());
                ps.setDate     (32, item.getAlphaListDate());
                ps.setString   (33, StringUtils.left(item.getFileBy(), 50));
                ps.setString   (34, item.getBargainingUnitIncluded());
                ps.setString   (35, item.getBargainingUnitExcluded());
                ps.setString   (36, item.getOptInIncluded());
                ps.setInt      (37, item.getProfessionalNonProfessional());
                ps.setString   (38, item.getProfessionalIncluded());
                ps.setString   (39, item.getProfessionalExcluded());
                ps.setString   (40, item.getNonProfessionalIncluded());
                ps.setString   (41, item.getNonProfessionalExcluded());
                ps.setString   (42, item.getToReflect());
                ps.setString   (43, StringUtils.left(item.getTypeFiledBy(), 500));
                ps.setString   (44, StringUtils.left(item.getTypeFiledVia(), 50));
                ps.setString   (45, StringUtils.left(item.getPositionStatementFiledBy(), 255));
                ps.setString   (46, StringUtils.left(item.getEEONameChangeFrom(), 255));
                ps.setString   (47, StringUtils.left(item.getEEONameChangeTo(), 255));
                ps.setString   (48, StringUtils.left(item.getERNameChangeFrom(), 255));
                ps.setString   (49, StringUtils.left(item.getERNameChangeTo(), 255));
                ps.setString   (50, StringUtils.left(item.getBoardActionType(), 25));
                ps.setDate     (51, item.getBoardActionDate());
                if (item.getHearingPersonID() != 0){
                    ps.setInt  (52, item.getHearingPersonID());
                } else {
                    ps.setNull (52, java.sql.Types.INTEGER);
                }
                ps.setString   (53, item.getBoardStatusNote());
                ps.setString   (54, item.getBoardStatusBlurb());
                if (item.getMulticaseElection() != 0){
                    ps.setInt  (55, item.getMulticaseElection());
                } else {
                    ps.setNull (55, java.sql.Types.INTEGER);
                }
                ps.setString   (56, StringUtils.left(item.getElectionType1(), 200));
                ps.setString   (57, StringUtils.left(item.getElectionType2(), 200));
                ps.setString   (58, StringUtils.left(item.getElectionType3(), 200));
                ps.setDate     (59, item.getEligibilityDate());
                ps.setString   (60, item.getBallotOne());
                ps.setString   (61, item.getBallotTwo());
                ps.setString   (62, item.getBallotThree());
                ps.setString   (63, item.getBallotFour());
                ps.setDate     (64, item.getMailKitDate());
                ps.setDate     (65, item.getPollingStartDate());
                ps.setDate     (66, item.getPollingEndDate());
                ps.setString   (67, StringUtils.left(item.getBallotsCountDay(), 25));
                ps.setDate     (68, item.getBallotsCountDate());
                ps.setTime     (69, item.getBallotsCountTime());
                ps.setDate     (70, item.getEligibilityListDate());
                ps.setDate     (71, item.getPreElectionConfDate());
                ps.setString   (72, item.getSelfReleasing());
                if (item.getResultApproxNumberEligibleVoters() != -1){
                    ps.setInt  (73, item.getResultApproxNumberEligibleVoters());
                } else {
                    ps.setNull (73, java.sql.Types.INTEGER);
                }
                if (item.getResultVoidBallots() != -1){
                    ps.setInt  (74, item.getResultVoidBallots());
                } else {
                    ps.setNull (74, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForEEO() != -1){
                    ps.setInt  (75, item.getResultVotesCastForEEO());
                } else {
                    ps.setNull (75, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForIncumbentEEO() != -1){
                    ps.setInt  (76, item.getResultVotesCastForIncumbentEEO());
                } else {
                    ps.setNull (76, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForRivalEEO1() != -1){
                    ps.setInt  (77, item.getResultVotesCastForRivalEEO1());
                } else {
                    ps.setNull (77, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForRivalEEO2() != -1){
                    ps.setInt  (78, item.getResultVotesCastForRivalEEO2());
                } else {
                    ps.setNull (78, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForRivalEEO3() != -1){
                    ps.setInt  (79, item.getResultVotesCastForRivalEEO3());
                } else {
                    ps.setNull (79, java.sql.Types.INTEGER);
                }
                if (item.getResultVotesCastForNoRepresentative() != -1){
                    ps.setInt  (80, item.getResultVotesCastForNoRepresentative());
                } else {
                    ps.setNull (80, java.sql.Types.INTEGER);
                }
                if (item.getResultValidVotesCounted() != -1){
                    ps.setInt  (81, item.getResultValidVotesCounted());
                } else {
                    ps.setNull (81, java.sql.Types.INTEGER);
                }
                if (item.getResultChallengedBallots() != -1){
                    ps.setInt  (82, item.getResultChallengedBallots());
                } else {
                    ps.setNull (82, java.sql.Types.INTEGER);
                }
                if (item.getResultTotalBallotsCast() != -1){
                    ps.setInt  (83, item.getResultTotalBallotsCast());
                } else {
                    ps.setNull (83, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalApproxNumberEligible() != -1){
                    ps.setInt  (84, item.getProfessionalApproxNumberEligible());
                } else {
                    ps.setNull (84, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalYES() != -1){
                    ps.setInt  (85, item.getProfessionalYES());
                } else {
                    ps.setNull (85, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalNO() != -1){
                    ps.setInt  (86, item.getProfessionalNO());
                } else {
                    ps.setNull (86, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalChallenged() != -1){
                    ps.setInt  (87, item.getProfessionalChallenged());
                } else {
                    ps.setNull (87, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalTotalVotes() != -1){
                    ps.setInt  (88, item.getProfessionalTotalVotes());
                } else {
                    ps.setNull (88, java.sql.Types.INTEGER);
                }
                ps.setString(89, StringUtils.left(item.getProfessionalOutcome(), 200));
                if (item.getProfessionalVoidBallots() != -1){
                    ps.setInt  (90, item.getProfessionalVoidBallots());
                } else {
                    ps.setNull (90, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalValidVotes() != -1){
                    ps.setInt  (91, item.getProfessionalValidVotes());
                } else {
                    ps.setNull (91, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCastForNoRepresentative() != -1){
                    ps.setInt  (92, item.getProfessionalVotesCastForNoRepresentative());
                } else {
                    ps.setNull (92, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCastForEEO() != -1){
                    ps.setInt  (93, item.getProfessionalVotesCastForEEO());
                } else {
                    ps.setNull (93, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCstForIncumbentEEO() != -1){
                    ps.setInt  (94, item.getProfessionalVotesCstForIncumbentEEO());
                } else {
                    ps.setNull (94, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCastForRivalEEO1() != -1){
                    ps.setInt  (95, item.getProfessionalVotesCastForRivalEEO1());
                } else {
                    ps.setNull (95, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCastForRivalEEO2() != -1){
                    ps.setInt  (96, item.getProfessionalVotesCastForRivalEEO2());
                } else {
                    ps.setNull (96, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalVotesCastForRivalEEO3() != -1){
                    ps.setInt  (97, item.getProfessionalVotesCastForRivalEEO3());
                } else {
                    ps.setNull (97, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalApproxNumberEligible() != -1){
                    ps.setInt  (98, item.getNonprofessionalApproxNumberEligible());
                } else {
                    ps.setNull (98, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalYES() != -1){
                    ps.setInt  (99, item.getNonprofessionalYES());
                } else {
                    ps.setNull (99, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalNO() != -1){
                    ps.setInt  (100, item.getNonprofessionalNO());
                } else {
                    ps.setNull (100, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalChallenged() != -1){
                    ps.setInt  (101, item.getNonprofessionalChallenged());
                } else {
                    ps.setNull (101, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalTotalVotes() != -1){
                    ps.setInt  (102, item.getNonprofessionalTotalVotes());
                } else {
                    ps.setNull (102, java.sql.Types.INTEGER);
                }
                ps.setString(103, StringUtils.left(item.getNonprofessionalOutcome(), 200));
                if (item.getNonprofessionalVoidBallots() != -1){
                    ps.setInt  (104, item.getNonprofessionalVoidBallots());
                } else {
                    ps.setNull (104, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalValidVotes() != -1){
                    ps.setInt  (105, item.getNonprofessionalValidVotes());
                } else {
                    ps.setNull (105, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForNoRepresentative() != -1){
                    ps.setInt  (106, item.getNonprofessionalVotesCastForNoRepresentative());
                } else {
                    ps.setNull (106, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForEEO() != -1){
                    ps.setInt  (107, item.getNonprofessionalVotesCastForEEO());
                } else {
                    ps.setNull (107, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForIncumbentEEO() != -1){
                    ps.setInt  (108, item.getNonprofessionalVotesCastForIncumbentEEO());
                } else {
                    ps.setNull (108, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForRivalEEO1() != -1){
                    ps.setInt  (109, item.getNonprofessionalVotesCastForRivalEEO1());
                } else {
                    ps.setNull (109, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForRivalEEO2() != -1){
                    ps.setInt  (110, item.getNonprofessionalVotesCastForRivalEEO2());
                } else {
                    ps.setNull (110, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalVotesCastForRivalEEO3() != -1){
                    ps.setInt  (111, item.getNonprofessionalVotesCastForRivalEEO3());
                } else {
                    ps.setNull (111, java.sql.Types.INTEGER);
                }
                if (item.getCombinedApproxNumberEligible() != -1){
                    ps.setInt  (112, item.getCombinedApproxNumberEligible());
                } else {
                    ps.setNull (112, java.sql.Types.INTEGER);
                }
                if (item.getCombinedYES() != -1){
                    ps.setInt  (113, item.getCombinedYES());
                } else {
                    ps.setNull (113, java.sql.Types.INTEGER);
                }
                if (item.getCombinedNO() != -1){
                    ps.setInt  (114, item.getCombinedNO());
                } else {
                    ps.setNull (114, java.sql.Types.INTEGER);
                }
                if (item.getCombinedChallenged() != -1){
                    ps.setInt  (115, item.getCombinedChallenged());
                } else {
                    ps.setNull (115, java.sql.Types.INTEGER);
                }
                if (item.getCombinedTotalVotes() != -1){
                    ps.setInt  (116, item.getCombinedTotalVotes());
                } else {
                    ps.setNull (116, java.sql.Types.INTEGER);
                }
                ps.setString(117, StringUtils.left(item.getCombinedOutcome(), 200));
                if (item.getCombinedVoidBallots() != -1){
                    ps.setInt  (118, item.getCombinedVoidBallots());
                } else {
                    ps.setNull (118, java.sql.Types.INTEGER);
                }
                if (item.getCombinedValidVotes() != -1){
                    ps.setInt  (119, item.getCombinedValidVotes());
                } else {
                    ps.setNull (119, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForNoRepresentative() != -1){
                    ps.setInt  (120, item.getCombinedVotesCastForNoRepresentative());
                } else {
                    ps.setNull (120, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForEEO() != -1){
                    ps.setInt  (121, item.getCombinedVotesCastForEEO());
                } else {
                    ps.setNull (121, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForIncumbentEEO() != -1){
                    ps.setInt  (122, item.getCombinedVotesCastForIncumbentEEO());
                } else {
                    ps.setNull (122, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForRivalEEO1() != -1){
                    ps.setInt  (123, item.getCombinedVotesCastForRivalEEO1());
                } else {
                    ps.setNull (123, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForRivalEEO2() != -1){
                    ps.setInt  (124, item.getCombinedVotesCastForRivalEEO2());
                } else {
                    ps.setNull (124, java.sql.Types.INTEGER);
                }
                if (item.getCombinedVotesCastForRivalEEO3() != -1){
                    ps.setInt  (125, item.getCombinedVotesCastForRivalEEO3());
                } else {
                    ps.setNull (125, java.sql.Types.INTEGER);
                }
                if (item.getResultWhoPrevailed() != -1){
                    ps.setInt  (126, item.getResultWhoPrevailed());
                } else {
                    ps.setNull (126, java.sql.Types.INTEGER);
                }
                if (item.getProfessionalWhoPrevailed() != -1){
                    ps.setInt  (127, item.getProfessionalWhoPrevailed());
                } else {
                    ps.setNull (127, java.sql.Types.INTEGER);
                }
                if (item.getNonprofessionalWhoPrevailed() != -1){
                    ps.setInt  (128, item.getNonprofessionalWhoPrevailed());
                } else {
                    ps.setNull (128, java.sql.Types.INTEGER);
                }
                if (item.getCombinedWhoPrevailed() != -1){
                    ps.setInt  (129, item.getCombinedWhoPrevailed());
                } else {
                    ps.setNull (129, java.sql.Types.INTEGER);
                }
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                SlackNotification.sendNotification(ex1);
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

}
