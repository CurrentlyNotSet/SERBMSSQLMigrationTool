/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.MEDCaseModel;
import com.model.oldMEDCaseModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
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
            
    public static void batchAddMEDCase(List<MEDCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
                    + "fileDate, "  //07
                    + "concilList1OrderDate, "       //08
                    + "concilList1SelectionDueDate, "//09
                    + "concilList1Name1, "           //10
                    + "concilList1Name2, "           //11
                    + "concilList1Name3, "           //12
                    + "concilList1Name4, "           //13
                    + "concilList1Name5, "           //14
                    + "concilAppointmentDate, "      //15
                    + "concilType, "                 //16
                    + "concilSelection, "            //17
                    + "concilReplacement, "          //18
                    + "concilOriginalConciliator, "  //19
                    + "concilOriginalConcilDate, "   //20
                    + "concilList2OrderDate, "       //21
                    + "concilList2SelectionDueDate, "//22
                    + "concilList2Name1, "//23
                    + "concilList2Name2, "//24
                    + "concilList2Name3, "//25
                    + "concilList2Name4, "//26
                    + "concilList2Name5, "//27
                    + "FFList1OrderDate, "//28
                    + "FFList1SelectionDueDate, "//29
                    + "FFList1Name1, "//30
                    + "FFList1Name2, "//31
                    + "FFList1Name3, "//32
                    + "FFList1Name4, "//33
                    + "FFList1Name5, "//34
                    + "FFAppointmentDate, "//35
                    + "FFType, "           //36
                    + "FFSelection, "      //37
                    + "FFReplacement, "    //38
                    + "FFOriginalFactFinder, "    //39
                    + "FFOriginalFactFinderDate, "//40
                    + "asAgreedToByParties, "     //41
                    + "FFList2OrderDate, "        //42
                    + "FFList2SelectionDueDate, " //43
                    + "FFList2Name1, "//44
                    + "FFList2Name2, "//45
                    + "FFList2Name3, "//46
                    + "FFList2Name4, "//47
                    + "FFList2Name5, "//48
                    + "FFEmployerType, "      //49
                    + "FFEmployeeType, "      //50
                    + "FFReportIssueDate, "   //51
                    + "FFMediatedSettlement, "//52
                    + "FFAcceptedBy, "        //53
                    + "FFDeemedAcceptedBy, "  //54
                    + "FFRejectedBy, "        //55
                    + "FFOverallResult, "     //56
                    + "FFNote, "              //57
                    + "EmployerIDNumber, "            //58
                    + "bargainingUnitNumber, "        //59
                    + "ApproxNumberOfEmployees, "     //60
                    + "DuplicateCaseNumber, "         //61
                    + "RelatedCaseNumber, "           //62
                    + "NegotiationType, "             //63
                    + "ExpirationDate, "              //64
                    + "NTNFiledBy, "                  //65
                    + "NegotiationPeriod, "           //66
                    + "MultiunitBargainingRequested, "//67
                    + "MediatorAppointedDate, "       //68
                    + "MediatorReplacement, "         //69
                    + "StateMediatorAppointedID, "    //70
                    + "FMCSMediatorAppointedID, "     //71
                    + "SettlementDate, "              //72
                    + "CaseStatus, "                  //73
                    + "SendToBoardToClose, "          //74
                    + "BoardFinalDate, "              //75
                    + "RetentionTicklerDate, "        //76
                    + "LateFiling, "//77
                    + "Impasse, "   //78
                    + "Settled, "   //79
                    + "TA, "        //80
                    + "MAD, "       //81
                    + "Withdrawl, " //82
                    + "Motion, "    //83
                    + "Dismissed, " //84
                    + "strikeFileDate, "    //85
                    + "unitDescription, "   //86
                    + "unitSize, "          //87
                    + "unauthorizedStrike, "//88
                    + "noticeOfIntentToStrikeOnly, "//89
                    + "intendedDateStrike, "        //90
                    + "noticeOfIntentToPicketOnly, "//91
                    + "intendedDatePicket, "        //92
                    + "informational, "             //93
                    + "noticeOfIntentToStrikeAndPicket, "//94
                    + "strikeOccured, "     //95
                    + "strikeStatus, "      //96
                    + "strikeBegan, "       //97
                    + "strikeEnded, "       //98
                    + "totalNumberOfDays, " //99
                    + "strikeMediatorAppointedID, " //100
                    + "strikeNote "                 //101
                    + ") VALUES (";
                    for(int i=0; i<100; i++){
                        sql += "?, ";   //01-100
                    }
                     sql += "?)";   //101
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (MEDCaseModel item : list) {
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getCaseYear(), 4));
                ps.setString( 3, StringUtils.left(item.getCaseType(), 3));
                ps.setString( 4, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString( 5, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString( 6, item.getNote());
                ps.setDate  ( 7, item.getFileDate());
                ps.setDate  ( 8, item.getConcilList1OrderDate());
                ps.setDate  ( 9, item.getConcilList1SelectionDueDate());
                ps.setString(10, StringUtils.left(item.getConcilList1Name1(), 200));
                ps.setString(11, StringUtils.left(item.getConcilList1Name2(), 200));
                ps.setString(12, StringUtils.left(item.getConcilList1Name3(), 200));
                ps.setString(13, StringUtils.left(item.getConcilList1Name4(), 200));
                ps.setString(14, StringUtils.left(item.getConcilList1Name5(), 200));
                ps.setDate  (15, item.getConcilAppointmentDate());
                ps.setString(16, StringUtils.left(item.getConcilType(), 100));
                ps.setString(17, StringUtils.left(item.getConcilSelection(), 100));
                ps.setString(18, StringUtils.left(item.getConcilReplacement(), 255));
                ps.setString(19, StringUtils.left(item.getConcilOriginalConciliator(), 255));
                ps.setDate  (20, item.getConcilOriginalConcilDate());
                ps.setDate  (21, item.getConcilList2OrderDate());
                ps.setDate  (22, item.getConcilList2SelectionDueDate());
                ps.setString(23, StringUtils.left(item.getConcilList2Name1(), 200));
                ps.setString(24, StringUtils.left(item.getConcilList2Name2(), 200));
                ps.setString(25, StringUtils.left(item.getConcilList2Name3(), 200));
                ps.setString(26, StringUtils.left(item.getConcilList2Name4(), 200));
                ps.setString(27, StringUtils.left(item.getConcilList2Name5(), 200));
                ps.setDate  (28, item.getFFList1OrderDate());
                ps.setDate  (29, item.getFFList1SelectionDueDate());
                ps.setString(30, StringUtils.left(item.getFFList1Name1(), 200));
                ps.setString(31, StringUtils.left(item.getFFList1Name2(), 200));
                ps.setString(32, StringUtils.left(item.getFFList1Name3(), 200));
                ps.setString(33, StringUtils.left(item.getFFList1Name4(), 200));
                ps.setString(34, StringUtils.left(item.getFFList1Name5(), 200));
                ps.setDate  (35, item.getFFAppointmentDate());
                ps.setString(36, StringUtils.left(item.getFFType(), 100));
                ps.setString(37, StringUtils.left(item.getFFSelection(), 100));
                ps.setString(38, StringUtils.left(item.getFFReplacement(), 255));
                ps.setString(39, StringUtils.left(item.getFFOriginalFactFinder(), 255));
                ps.setDate  (40, item.getFFOriginalFactFinderDate());
                ps.setInt   (41, item.getAsAgreedToByParties());
                ps.setDate  (42, item.getFFList2OrderDate());
                ps.setDate  (43, item.getFFList2SelectionDueDate());
                ps.setString(44, StringUtils.left(item.getFFList2Name1(), 200));
                ps.setString(45, StringUtils.left(item.getFFList2Name2(), 200));
                ps.setString(46, StringUtils.left(item.getFFList2Name3(), 200));
                ps.setString(47, StringUtils.left(item.getFFList2Name4(), 200));
                ps.setString(48, StringUtils.left(item.getFFList2Name5(), 200));
                ps.setString(49, StringUtils.left(item.getFFEmployerType(), 200));
                ps.setString(50, StringUtils.left(item.getFFEmployeeType(), 200));
                ps.setDate  (51, item.getFFReportIssueDate());
                ps.setInt   (52, item.getFFMediatedSettlement());
                ps.setString(53, StringUtils.left(item.getFFAcceptedBy(), 200));
                ps.setString(54, StringUtils.left(item.getFFDeemedAcceptedBy(), 200));
                ps.setString(55, StringUtils.left(item.getFFRejectedBy(), 200));
                ps.setString(56, StringUtils.left(item.getFFOverallResult(), 200));
                ps.setString(57, item.getFFNote());
                ps.setString(58, StringUtils.left(item.getEmployerIDNumber(), 10));
                ps.setString(59, StringUtils.left(item.getBargainingUnitNumber(), 10));
                ps.setString(60, StringUtils.left(item.getApproxNumberOfEmployees(), 4));
                ps.setString(61, StringUtils.left(item.getDuplicateCaseNumber(), 20));
                ps.setString(62, StringUtils.left(item.getRelatedCaseNumber(), 20));
                ps.setString(63, StringUtils.left(item.getNegotiationType(), 75));
                ps.setDate   (64, item.getExpirationDate());
                ps.setString (65, StringUtils.left(item.getNTNFiledBy(), 50));
                ps.setString (66, StringUtils.left(item.getNegotiationPeriod(), 3));
                ps.setBoolean(67, item.isMultiunitBargainingRequested());
                ps.setDate   (68, item.getMediatorAppointedDate());
                ps.setBoolean(69, item.isMediatorReplacement());
                ps.setString (70, StringUtils.left(item.getStateMediatorAppointedID(), 10));
                ps.setString (71, StringUtils.left(item.getFMCSMediatorAppointedID(), 10));
                ps.setDate   (72, item.getSettlementDate());
                ps.setString (73, StringUtils.left(item.getCaseStatus(), 50));
                ps.setBoolean(74, item.isSendToBoardToClose());
                ps.setDate   (75, item.getBoardFinalDate());
                ps.setDate   (76, item.getRetentionTicklerDate());
                ps.setBoolean(77, item.isLateFiling());
                ps.setBoolean(78, item.isImpasse());
                ps.setBoolean(79, item.isSettled());
                ps.setBoolean(80, item.isTA());
                ps.setBoolean(81, item.isMAD());
                ps.setBoolean(82, item.isWithdrawl());
                ps.setBoolean(83, item.isMotion());
                ps.setBoolean(84, item.isDismissed());
                ps.setDate   (85, item.getStrikeFileDate());
                ps.setString (86, StringUtils.left(item.getUnitDescription(), 100));
                ps.setString (87, StringUtils.left(item.getUnitSize(), 10));
                ps.setBoolean(88, item.isUnauthorizedStrike());
                ps.setBoolean(89, item.isNoticeOfIntentToStrikeOnly());
                ps.setDate   (90, item.getIntendedDateStrike());
                ps.setBoolean(91, item.isNoticeOfIntentToPicketOnly());
                ps.setDate   (92, item.getIntendedDatePicket());
                ps.setBoolean(93, item.isInformational());
                ps.setBoolean(94, item.isNoticeOfIntentToStrikeAndPicket());
                ps.setString (95, StringUtils.left(item.getStrikeOccured(), 20));
                ps.setString (96, StringUtils.left(item.getStrikeStatus(), 100));
                ps.setDate   (97, item.getStrikeBegan());
                ps.setDate   (98, item.getStrikeEnded());
                ps.setString (99, StringUtils.left(item.getTotalNumberOfDays(), 10));
                ps.setString (100, StringUtils.left(item.getStrikeMediatorAppointedID(), 10));
                ps.setString (101, item.getStrikeNote());
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
                ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
