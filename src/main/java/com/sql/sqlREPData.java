/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldREPDataModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

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
            String sql = "SELECT * FROM REPData";
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
                item.setEligibilityDate(rs.getString("EligibilityDate"));
                item.setBallotOne(rs.getString("BallotOne"));
                item.setBallotTwo(rs.getString("BallotTwo"));
                item.setBallotThree(rs.getString("BallotThree"));
                item.setBallotFour(rs.getString("BallotFour"));
                item.setPollingStartDate(rs.getString("PollingStartDate"));
                item.setPollingEndDate(rs.getString("PollingEndDate"));
                item.setBallotsCountDay(rs.getString("BallotsCountDay"));
                item.setBallotsCountDate(rs.getString("BallotsCountDate"));
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
                item.setMailKitDate(rs.getString("MailKitDate"));
                item.setEligibilityListDate(rs.getString("EligibilityListDate"));
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
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }

}
