/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ULPCaseModel;
import com.model.oldULPDataModel;
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
public class sqlULPData {
    
    public static List<oldULPDataModel> getCases() {
        List<oldULPDataModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM ULPData";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldULPDataModel item = new oldULPDataModel();
                item.setActive(rs.getInt("Active"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setCPName(rs.getString("CPName"));
                item.setCPAddress1(rs.getString("CPAddress1"));
                item.setCPAddress2(rs.getString("CPAddress2"));
                item.setCPCity(rs.getString("CPCity"));
                item.setCPState(rs.getString("CPState"));
                item.setCPZip(rs.getString("CPZip"));
                item.setCPPhone1(rs.getString("CPPhone1"));
                item.setCPPhone2(rs.getString("CPPhone2"));
                item.setCPEmail(rs.getString("CPEmail"));
                item.setCPREPName(rs.getString("CPREPName"));
                item.setCPREPAddress1(rs.getString("CPREPAddress1"));
                item.setCPREPAddress2(rs.getString("CPREPAddress2"));
                item.setCPREPCity(rs.getString("CPREPCity"));
                item.setCPREPState(rs.getString("CPREPState"));
                item.setCPREPZip(rs.getString("CPREPZip"));
                item.setCPREPPhone1(rs.getString("CPREPPhone1"));
                item.setCPREPPhone2(rs.getString("CPREPPhone2"));
                item.setCPREPEmail(rs.getString("CPREPEmail"));
                item.setCHDName(rs.getString("CHDName"));
                item.setCHDAddress1(rs.getString("CHDAddress1"));
                item.setCHDAddress2(rs.getString("CHDAddress2"));
                item.setCHDCity(rs.getString("CHDCity"));
                item.setCHDState(rs.getString("CHDState"));
                item.setCHDZip(rs.getString("CHDZip"));
                item.setCHDPhone1(rs.getString("CHDPhone1"));
                item.setCHDPhone2(rs.getString("CHDPhone2"));
                item.setCHDEmail(rs.getString("CHDEmail"));
                item.setCHDREPName(rs.getString("CHDREPName"));
                item.setCHDREPAddress1(rs.getString("CHDREPAddress1"));
                item.setCHDREPAddress2(rs.getString("CHDREPAddress2"));
                item.setCHDREPCity(rs.getString("CHDREPCity"));
                item.setCHDREPState(rs.getString("CHDREPState"));
                item.setCHDREPZip(rs.getString("CHDREPZip"));
                item.setCHDREPPhone1(rs.getString("CHDREPPhone1"));
                item.setCHDREPPhone2(rs.getString("CHDREPPhone2"));
                item.setCHDREPEmail(rs.getString("CHDREPEmail"));
                item.setAllegation(rs.getString("Allegation"));
                item.setStatus(rs.getString("Status"));
                item.setPriority(rs.getString("Priority"));
                item.setAssignedDate(rs.getString("AssignedDate"));
                item.setTurnInDate(rs.getString("TurnInDate"));
                item.setReportDate(rs.getString("ReportDate"));
                item.setAgendaItem(rs.getString("AgendaItem"));
                item.setBoardMeetingDate(rs.getString("BoardMeetingDate"));
                item.setDismissalBoardMeetingDate(rs.getString("DismissalBoardMeetingDate"));
                item.setDeferredBoardMeetingDate(rs.getString("DeferredBoardMeetingDate"));
                item.setLRSName(rs.getString("LRSName") == null ? "" : rs.getString("LRSName"));
                item.setMediatorAssigned(rs.getString("MediatorAssigned") == null ? "" : rs.getString("MediatorAssigned"));
                item.setRelatedCases(rs.getString("RelatedCases"));
                item.setEmployerNum(rs.getString("EmployerNum"));
                item.setProbable(rs.getString("Probable"));
                item.setBarginingUnitNumber(rs.getString("BarginingUnitNumber"));
                item.setFinalDispostion(rs.getString("FinalDispostion"));
                item.setEmployeeOrgNumber(rs.getString("EmployeeOrgNumber"));
                item.setFileDate(rs.getString("FileDate"));
                item.setALJ(rs.getString("ALJ") == null ? "" : rs.getString("ALJ"));
                item.setBoardMeetingDate1(rs.getString("BoardMeetingDate1"));
                item.setBoardMeetingDate2(rs.getString("BoardMeetingDate2"));
                item.setAgendaItem1(rs.getString("AgendaItem1"));
                item.setAgendaItem2(rs.getString("AgendaItem2"));
                item.setRecommendation(rs.getString("Recommendation"));
                item.setRecommendation1(rs.getString("Recommendation1"));
                item.setRecommendation2(rs.getString("Recommendation2"));
                item.setAppealDateReceived(rs.getString("AppealDateReceived"));
                item.setAppealDateSent(rs.getString("AppealDateSent"));
                item.setCourt(rs.getString("Court"));
                item.setCourtCaseNumber(rs.getString("CourtCaseNumber"));
                item.setSERBCourtCaseNumber(rs.getString("SERBCourtCaseNumber"));
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
    
    public static void importOldULPCase(ULPCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO ULPcase ("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "employerIDNumber, "  //05
                    + "deptInState, "       //06
                    + "barginingUnitNo, "   //07
                    + "EONumber, "          //08
                    + "allegation, "        //09
                    + "currentStatus, "     //10
                    + "priority, "          //11
                    + "assignedDate, "      //12
                    + "turnInDate, "        //13
                    + "reportDueDate, "     //14
                    + "dismissalDate, "     //15
                    + "deferredDate, "      //16
                    + "fileDate, "          //17
                    + "probableCause, "     //18
                    + "appealDateReceived, "//19
                    + "appealDateSent, "    //20
                    + "courtName, "         //21
                    + "courtCaseNumber, "   //22
                    + "SERBCaseNumber, "    //23
                    + "finalDispositionStatus, "//24
                    + "investigatorID, "    //25
                    + "mediatorAssignedID, "//26
                    + "aljID, "             //27
                    + "statement, "         //28
                    + "recommendation, "    //29
                    + "investigationReveals, "//30
                    + "note "//31
                    + ") VALUES ("
                    + "?, " //01 
                    + "?, " //02 
                    + "?, " //03 
                    + "?, " //04 
                    + "?, " //05
                    + "?, " //06 
                    + "?, " //07 
                    + "?, " //08 
                    + "?, " //09 
                    + "?, " //10 
                    + "?, " //11 
                    + "?, " //12 
                    + "?, " //13 
                    + "?, " //14 
                    + "?, " //15 
                    + "?, " //16 
                    + "?, " //17 
                    + "?, " //18 
                    + "?, " //19 
                    + "?, " //20 
                    + "?, " //21 
                    + "?, " //22 
                    + "?, " //23 
                    + "?, " //24 
                    + "?, " //25 
                    + "?, " //26 
                    + "?, " //27 
                    + "?, " //28 
                    + "?, " //29 
                    + "?, " //30 
                    + "?)"; //31 
            ps = conn.prepareStatement(sql);
            ps.setString   ( 1, item.getCaseYear());
            ps.setString   ( 2, item.getCaseType());
            ps.setString   ( 3, item.getCaseMonth());
            ps.setString   ( 4, item.getCaseNumber());
            ps.setString   ( 5, item.getEmployerIDNumber());
            ps.setString   ( 6, item.getDeptInState());
            ps.setString   ( 7, item.getBarginingUnitNo());
            ps.setString   ( 8, item.getEONumber());
            ps.setString   ( 9, item.getAllegation());
            ps.setString   (10, item.getCurrentStatus());
            ps.setBoolean  (11, item.isPriority());
            ps.setTimestamp(12, item.getAssignedDate());
            ps.setTimestamp(13, item.getTurnInDate());
            ps.setTimestamp(14, item.getReportDueDate());
            ps.setTimestamp(15, item.getDismissalDate());
            ps.setTimestamp(16, item.getDeferredDate());
            ps.setTimestamp(17, item.getFileDate());
            ps.setBoolean  (18, item.isProbableCause());
            ps.setTimestamp(19, item.getAppealDateReceived());
            ps.setTimestamp(20, item.getAppealDateSent());
            ps.setString   (21, item.getCourtName());
            ps.setString   (22, item.getCourtCaseNumber());
            ps.setString   (23, item.getSERBCaseNumber());
            ps.setString   (24, item.getFinalDispositionStatus());
            if (item.getInvestigatorID() != 0){
                ps.setInt  (25, item.getInvestigatorID());
            } else {
                ps.setNull (25, java.sql.Types.INTEGER);
            }
            if (item.getMediatorAssignedID() != 0){
                ps.setInt  (26, item.getMediatorAssignedID());
            } else {
                ps.setNull (26, java.sql.Types.INTEGER);
            }
            if (item.getAljID() != 0){
                ps.setInt  (27, item.getAljID());
            } else {
                ps.setNull (27, java.sql.Types.INTEGER);
            }
            ps.setString   (28, item.getStatement());
            ps.setString   (29, item.getRecommendation());
            ps.setString   (30, item.getInvestigationReveals());
            ps.setString   (31, item.getNote());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
