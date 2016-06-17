/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.MEDCaseModel;
import com.model.oldMEDCaseModel;
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
 * @author User
 */
public class sqlMEDData {
    
    public static List<oldMEDCaseModel> getCases() {
        List<oldMEDCaseModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM medcase WHERE caseNumber != '' OR StrikeCaseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldMEDCaseModel item = new oldMEDCaseModel();
                item.setMEDCaseID(rs.getInt("MEDCaseID"));
                item.setActive(rs.getInt("Active"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setCaseNumber2(rs.getString("CaseNumber2"));
                item.setCaseNumber3(rs.getString("CaseNumber3"));
                item.setCaseNumber4(rs.getString("CaseNumber4"));
                item.setCaseNumber5(rs.getString("CaseNumber5"));
                item.setCaseNumber6(rs.getString("CaseNumber6"));
                item.setReopener(rs.getString("Reopener"));
                item.setCaseFileDate(rs.getString("CaseFileDate"));
                item.setEmployerIDNumber(rs.getString("EmployerIDNumber"));
                item.setBUNumber(rs.getString("BUNumber"));
                item.setBoardCertified(rs.getString("BoardCertified"));
                item.setDeemedCertified(rs.getString("DeemedCertified"));
                item.setApproxNumberOfEmployees(rs.getString("ApproxNumberOfEmployees"));
                item.setEmployerName(rs.getString("EmployerName"));
                item.setEmployerAddress1(rs.getString("EmployerAddress1"));
                item.setEmployerAddress2(rs.getString("EmployerAddress2"));
                item.setEmlpoyerCity(rs.getString("EmlpoyerCity"));
                item.setEmployerState(rs.getString("EmployerState"));
                item.setEmployerZip(rs.getString("EmployerZip"));
                item.setEmployerPhone(rs.getString("EmployerPhone"));
                item.setEmployerEmail(rs.getString("EmployerEmail"));
                item.setEmployerREPSal(rs.getString("EmployerREPSal"));
                item.setEmployerREPName(rs.getString("EmployerREPName"));
                item.setEmployerREPAddress1(rs.getString("EmployerREPAddress1"));
                item.setEmployerREPAddress2(rs.getString("EmployerREPAddress2"));
                item.setEmployerREPCity(rs.getString("EmployerREPCity"));
                item.setEmployerREPState(rs.getString("EmployerREPState"));
                item.setEmployerREPZip(rs.getString("EmployerREPZip"));
                item.setEmployerREPPhone(rs.getString("EmployerREPPhone"));
                item.setEmployerREPEmail(rs.getString("EmployerREPEmail"));
                item.setEmployeeOrgNumberb(rs.getString("EmployeeOrgNumberb"));
                item.setEmployeeOrgName(rs.getString("EmployeeOrgName"));
                item.setEmployeeOrgAddress1(rs.getString("EmployeeOrgAddress1"));
                item.setEmployeeOrgAddress2(rs.getString("EmployeeOrgAddress2"));
                item.setEmployeeOrgCity(rs.getString("EmployeeOrgCity"));
                item.setEmployeeOrgState(rs.getString("EmployeeOrgState"));
                item.setEmployeeOrgZip(rs.getString("EmployeeOrgZip"));
                item.setEmployeeOrgPhone(rs.getString("EmployeeOrgPhone"));
                item.setEmployeeOrgEmail(rs.getString("EmployeeOrgEmail"));
                item.setEmployeeOrgREPSal(rs.getString("EmployeeOrgREPSal"));
                item.setEmployeeOrgREPName(rs.getString("EmployeeOrgREPName"));
                item.setEmployeeOrgREPAddress1(rs.getString("EmployeeOrgREPAddress1"));
                item.setEmployeeOrgREPAddress2(rs.getString("EmployeeOrgREPAddress2"));
                item.setEmployeeOrgREPCity(rs.getString("EmployeeOrgREPCity"));
                item.setEmployeeOrgREPState(rs.getString("EmployeeOrgREPState"));
                item.setEmployeeOrgREPZip(rs.getString("EmployeeOrgREPZip"));
                item.setEmployeeOrgREPPhone(rs.getString("EmployeeOrgREPPhone"));
                item.setEmployeeOrgREPEmail(rs.getString("EmployeeOrgREPEmail"));
                item.setNegotiationType(rs.getString("NegotiationType"));
                item.setControllingDate(rs.getString("ControllingDate"));
                item.setNTNFiledBy(rs.getString("NTNFiledBy"));
                item.setNegotiationPeriod(rs.getString("NegotiationPeriod"));
                item.setMultiUnitBargainingRequested(rs.getString("MultiUnitBargainingRequested"));
                item.setCBAReceivedDate(rs.getString("CBAReceivedDate"));
                item.setStatus(rs.getString("Status"));
                item.setMediatorApptDate(rs.getString("MediatorApptDate"));
                item.setMediatorInvolved(rs.getString("MediatorInvolved"));
                item.setStateMediatorAppt(rs.getString("StateMediatorAppt"));
                item.setStateMediatorPhone(rs.getString("StateMediatorPhone"));
                item.setFMCSMediatorAppt(rs.getString("FMCSMediatorAppt"));
                item.setFMCSMediatorPhone(rs.getString("FMCSMediatorPhone"));
                item.setNumOfUnits(rs.getString("NumOfUnits"));
                item.setJurisdiction(rs.getString("Jurisdiction"));
                item.setEmployerCounty(rs.getString("EmployerCounty"));
                item.setRegion(rs.getString("Region"));
                item.setFactFinderSelected(rs.getString("FactFinderSelected"));
                item.setBUSize(rs.getString("BUSize"));
                item.setNumberofIssues(rs.getString("NumberofIssues"));
                item.setCost(rs.getString("Cost"));
                item.setMileage(rs.getString("Mileage"));
                item.setEmployerType(rs.getString("EmployerType"));
                item.setEmployeeType(rs.getString("EmployeeType"));
                item.setFFInvolvement(rs.getString("FFInvolvement"));
                item.setMediatedSettlement(rs.getString("MediatedSettlement"));
                item.setMediatedSettlementDate(rs.getString("MediatedSettlementDate"));
                item.setResultsAppectedBy(rs.getString("ResultsAppectedBy"));
                item.setResultsDeemedAcceptedBy(rs.getString("ResultsDeemedAcceptedBy"));
                item.setResultsRejectedBy(rs.getString("ResultsRejectedBy"));
                item.setResultsOverallResult(rs.getString("ResultsOverallResult"));
                item.setFFPanelSelectionDate(rs.getString("FFPanelSelectionDate"));
                item.setFFPanelSelectionDue(rs.getString("FFPanelSelectionDue"));
                item.setFactFinder1(rs.getString("FactFinder1"));
                item.setFactFinder2(rs.getString("FactFinder2"));
                item.setFactFinder3(rs.getString("FactFinder3"));
                item.setFactFinder4(rs.getString("FactFinder4"));
                item.setFactFinder5(rs.getString("FactFinder5"));
                item.setFactFinderApptDate(rs.getString("FactFinderApptDate"));
                item.setFactFinderType(rs.getString("FactFinderType"));
                item.setFactFinderReportDate(rs.getString("FactFinderReportDate"));
                item.setAgreedByTheParties(rs.getString("AgreedByTheParties"));
                item.setBUType(rs.getString("BUType"));
                item.setSettledCheckBox(rs.getString("SettledCheckBox"));
                item.setTACheckBox(rs.getString("TACheckBox"));
                item.setMADCheckBox(rs.getString("MADCheckBox"));
                item.setMotionCheckBox(rs.getString("MotionCheckBox"));
                item.setConciliationOrderDate(rs.getString("ConciliationOrderDate"));
                item.setConciliationSelectionDue(rs.getString("ConciliationSelectionDue"));
                item.setConciliator1(rs.getString("Conciliator1"));
                item.setConciliator2(rs.getString("Conciliator2"));
                item.setConciliator3(rs.getString("Conciliator3"));
                item.setConciliator4(rs.getString("Conciliator4"));
                item.setConciliator5(rs.getString("Conciliator5"));
                item.setConciliatorSelection(rs.getString("ConciliatorSelection"));
                item.setConciliatorApptDate(rs.getString("ConciliatorApptDate"));
                item.setConciliationType(rs.getString("ConciliationType"));
                item.setStatutory(rs.getString("Statutory"));
                item.setStrikeFilingDate(rs.getString("StrikeFilingDate"));
                item.setStrikeCaseNumber(rs.getString("StrikeCaseNumber"));
                item.setNoticeOfIntentToStrike(rs.getString("NoticeOfIntentToStrike"));
                item.setIntendedStrikeDate(rs.getString("IntendedStrikeDate"));
                item.setNoticeofIntentToPicket(rs.getString("NoticeofIntentToPicket"));
                item.setIntendedPicketDate(rs.getString("IntendedPicketDate"));
                item.setInformational(rs.getString("Informational"));
                item.setNoticeOfIntentToStrikeAndPicket(rs.getString("NoticeOfIntentToStrikeAndPicket"));
                item.setStrikeOccured(rs.getString("StrikeOccured"));
                item.setStrikeStatus(rs.getString("StrikeStatus"));
                item.setStrikeBegan(rs.getString("StrikeBegan"));
                item.setStrikeEnded(rs.getString("StrikeEnded"));
                item.setTotalNumOfDays(rs.getString("TotalNumOfDays"));
                item.setBUDescription(rs.getString("BUDescription"));
                item.setFFPanelSelectionDate2(rs.getString("FFPanelSelectionDate2"));
                item.setFFPanelSelectionDue2(rs.getString("FFPanelSelectionDue2"));
                item.setFactFinder1Set2(rs.getString("FactFinder1Set2"));
                item.setFactFinder2Set2(rs.getString("FactFinder2Set2"));
                item.setFactFinder3Set2(rs.getString("FactFinder3Set2"));
                item.setFactFinder4Set2(rs.getString("FactFinder4Set2"));
                item.setFactFinder5Set2(rs.getString("FactFinder5Set2"));
                item.setConciliationOrderDate2(rs.getString("ConciliationOrderDate2"));
                item.setConciliationSelectionDate2(rs.getString("ConciliationSelectionDate2"));
                item.setConciliator1Set2(rs.getString("Conciliator1Set2"));
                item.setConciliator2Set2(rs.getString("Conciliator2Set2"));
                item.setConciliator3Set2(rs.getString("Conciliator3Set2"));
                item.setConciliator4Set2(rs.getString("Conciliator4Set2"));
                item.setConciliator5Set2(rs.getString("Conciliator5Set2"));
                item.setImpasse(rs.getString("Impasse"));
                item.setEmployeeOrgCounty(rs.getString("EmployeeOrgCounty"));
                item.setEmployeeOrgRegion(rs.getString("EmployeeOrgRegion"));
                item.setNoCaseNumber(rs.getString("NoCaseNumber"));
                item.setEmployeeNumber(rs.getString("EmployeeNumber"));
                item.setFactFinderRepalcement(rs.getString("FactFinderRepalcement"));
                item.setConciliatorReplacement(rs.getString("ConciliatorReplacement"));
                item.setPicketDate2(rs.getString("PicketDate2"));
                item.setPicketDate3(rs.getString("PicketDate3"));
                item.setPicketDate4(rs.getString("PicketDate4"));
                item.setPicketDate5(rs.getString("PicketDate5"));
                item.setReportIssuedDate(rs.getString("ReportIssuedDate"));
                item.setIssuesForER(rs.getString("IssuesForER"));
                item.setIssuesForEO(rs.getString("IssuesForEO"));
                item.setConcilDateRecieved(rs.getString("ConcilDateRecieved"));
                item.setFFDateRecieved(rs.getString("FFDateRecieved"));
                item.setNoReport(rs.getString("NoReport"));
                item.setAltSelection(rs.getString("AltSelection"));
                item.setAltReplacement(rs.getString("AltReplacement"));
                item.setDisSelection(rs.getString("DisSelection"));
                item.setDisReplacement(rs.getString("DisReplacement"));
                item.setFFOrg(rs.getString("FFOrg"));
                item.setTempHolder2(rs.getString("TempHolder2"));
                item.setTempHolder3(rs.getString("TempHolder3"));
                item.setTempHolder4(rs.getString("TempHolder4"));
                item.setTempHolder5(rs.getString("TempHolder5"));
                item.setReportIssueDate(rs.getString("ReportIssueDate"));
                item.setEmployerIssues(rs.getString("EmployerIssues"));
                item.setUnionIssues(rs.getString("UnionIssues"));
                item.setConciliationAwarded(rs.getString("ConciliationAwarded"));
                item.setFFConcilName(rs.getString("FFConcilName"));
                item.setNumIssues(rs.getString("NumIssues"));
                item.setLateFiling(rs.getString("LateFiling"));
                item.setExpirationDate(rs.getString("ExpirationDate"));
                item.setNTNFilingDate(rs.getString("NTNFilingDate"));
                item.setWithdraw(rs.getString("Withdraw"));
                item.setDismiss(rs.getString("Dismiss"));
                item.setRelated(rs.getString("Related"));
                item.setTicklerDate(rs.getString("TicklerDate"));
                item.setDeptInState(rs.getString("DeptInState"));
                item.setOrgFFDate(rs.getString("OrgFFDate"));
                item.setOrgConcilDate(rs.getString("OrgConcilDate"));
                item.setDuplicateCase(rs.getString("DuplicateCase"));
                item.setUnauthorizedStrike(rs.getString("unauthorizedStrike"));
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
    
    
    public static void importOldMEDCase(MEDCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO MEDCase ("
                    + "active, "    //01
                    + "caseYear, "  //02
                    + "caseType, "  //03
                    + "caseMonth, " //04
                    + "caseNumber, "//05
                    + "note, "      //06
                    + "fileDate "   //07
                    + ") VALUES (";
                    for(int i=0; i<06; i++){
                        sql += "?, ";   //01-06
                    }
                     sql += "?)";   //07
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getCaseYear());
            ps.setString( 3, item.getCaseType());
            ps.setString( 4, item.getCaseMonth());
            ps.setString( 5, item.getCaseNumber());
            ps.setString( 6, item.getNote());
            ps.setDate  ( 7, item.getFileDate());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
