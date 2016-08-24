/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.activityModel;
import com.model.oldMEDHistoryModel;
import com.model.oldORGHistoryModel;
import com.model.oldREPHistoryModel;
import com.model.oldULPHistoryModel;
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
public class sqlActivity {    
    
    public static void addActivity(activityModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Activity ("
                    + "caseYear, "        //01
                    + "caseType, "        //02
                    + "caseMonth, "       //03
                    + "caseNumber, "      //04
                    + "userID, "          //05
                    + "date, "            //06
                    + "action, "          //07
                    + "fileName, "        //08
                    + "[from], "          //09
                    + "[to], "            //10
                    + "type, "            //11
                    + "comment, "         //12
                    + "redacted, "        //13
                    + "awaitingTimeStamp "//14
                    + ") VALUES (";
                    for(int i=0; i<13; i++){
                        sql += "?, ";   //01-13
                    }
                     sql += "?)"; //14
            ps = conn.prepareStatement(sql);
            ps.setString   ( 1, item.getCaseYear());
            ps.setString   ( 2, item.getCaseType());
            ps.setString   ( 3, item.getCaseMonth());
            ps.setString   ( 4, item.getCaseNumber());
            if (item.getUserID() != 0){
                ps.setInt  ( 5, item.getUserID());
            } else {
                ps.setNull ( 5, java.sql.Types.INTEGER);
            }
            ps.setTimestamp( 6, item.getDate());
            ps.setString   ( 7, item.getAction());
            ps.setString   ( 8, item.getFileName());
            ps.setString   ( 9, item.getFrom());
            ps.setString   (10, item.getTo());
            ps.setString   (11, item.getType());
            ps.setString   (12, item.getComment());
            ps.setInt      (13, item.getRedacted());
            ps.setInt      (14, item.getAwaitingTimeStamp());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
        
    public static List<oldULPHistoryModel> getULPHistoryByCase(String caseNumber) {
        List<oldULPHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, cast(date as datetime) as Date2 FROM ULPHistory WHERE caseNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, caseNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldULPHistoryModel item = new oldULPHistoryModel();
                item.setHistoryID(rs.getInt("HistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setUserInitials(rs.getString("UserInitals"));
                item.setDate(rs.getTimestamp("Date2"));
                item.setAction(rs.getString("Action"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setFileName(rs.getString("FileName"));
                item.setParseDate(rs.getString("ParseDate"));
                item.setEmailTo(rs.getString("EmailTo"));
                item.setEmailFrom(rs.getString("EmailFrom"));
                item.setMailLogDate(rs.getString("MailLogDate"));
                item.setApproved(rs.getString("Approved"));
                item.setRequested(rs.getString("Requested"));
                item.setRedacted(rs.getString("Redacted"));
                item.setRedactedHistoryID(rs.getString("RedactedHistoryID"));
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
    
    public static List<oldREPHistoryModel> getREPHistoryByCase(String caseNumber) {
        List<oldREPHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, cast(date as datetime) as Date2 FROM REPHistory WHERE caseNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, caseNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldREPHistoryModel item = new oldREPHistoryModel();
                item.setHistoryID(rs.getInt("HistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setUserInitals(rs.getString("UserInitals"));
                item.setDate(rs.getTimestamp("Date2"));
                item.setAction(rs.getString("Action"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setFileName(rs.getString("FileName"));
                item.setParseDate(rs.getString("ParseDate"));
                item.setEmailTo(rs.getString("EmailTo"));
                item.setEmailFrom(rs.getString("EmailFrom"));
                item.setMailLogDate(rs.getString("MailLogDate"));
                item.setApproved(rs.getString("Approved"));
                item.setRequested(rs.getString("Requested"));
                item.setRedacted(rs.getString("Redacted"));
                item.setRedactedHistoryID(rs.getString("RedactedHistoryID"));
                item.setNote(rs.getString("Note"));
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
    
    public static List<oldMEDHistoryModel> getMEDHistoryByCase(String caseNumber) {
        List<oldMEDHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, cast(date as datetime) as Date2 FROM medhistory WHERE caseNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, caseNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldMEDHistoryModel item = new oldMEDHistoryModel();
                item.setHistoryID(rs.getInt("HistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setUserInitals(rs.getString("UserInitals"));
                item.setDate(rs.getTimestamp("Date2"));
                item.setAction(rs.getString("Action"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setFileName(rs.getString("FileName"));
                item.setParseDate(rs.getString("ParseDate"));
                item.setEmailTo(rs.getString("EmailTo"));
                item.setEmailFrom(rs.getString("EmailFrom"));
                item.setMailLogDate(rs.getString("MailLogDate"));
                item.setApproved(rs.getString("Approved"));
                item.setRequested(rs.getString("Requested"));
                item.setRedacted(rs.getString("Redacted"));
                item.setRedactedHistoryID(rs.getString("RedactedHistoryID"));
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
    
    public static List<oldORGHistoryModel> getORGHistory() {
        List<oldORGHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM orghistory where note != 'null'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldORGHistoryModel item = new oldORGHistoryModel();
                item.setOrgHistoryid(rs.getInt("OrgHistoryid"));
                item.setActive(rs.getInt("Active"));
                item.setUserInitials(rs.getString("UserInitials").trim().equals("null") ? "" : rs.getString("UserInitials"));
                item.setSrc(rs.getString("Src").trim().equals("null") ? "" : rs.getString("Src"));
                item.setDest(rs.getString("Dest").trim().equals("null") ? "" : rs.getString("Dest"));
                item.setEmployeeOrgid(rs.getInt("EmployeeOrgid"));
                item.setDateTimeMillis(rs.getLong("DateTimeMillis"));
                item.setDirection(rs.getString("Direction").trim().equals("null") ? "" : rs.getString("Direction"));
                item.setDate(rs.getString("Date").trim().equals("null") ? "" : rs.getString("Date"));
                item.setTime(rs.getString("Time").trim().equals("null") ? "" : rs.getString("Time"));
                item.setOrgNum(rs.getString("OrgNum").trim().equals("null") ? "" : rs.getString("OrgNum"));
                item.setType(rs.getString("Type").trim().equals("null") ? "" : rs.getString("Type"));        
                item.setSection(rs.getString("Section").trim().equals("null") ? "" : rs.getString("Section"));
                item.setFileAttrib(rs.getString("FileAttrib").trim().equals("null") ? "" : rs.getString("FileAttrib"));
                item.setOtherComment(rs.getString("OtherComment").trim().equals("null") ? "" : rs.getString("OtherComment"));
                item.setFileName(rs.getString("FileName").trim().equals("null") ? "" : rs.getString("FileName"));
                item.setDescription(rs.getString("Description").trim().equals("null") ? "" : rs.getString("Description"));
                item.setMailLogDate(rs.getString("MailLogDate").trim().equals("null") ? "" : rs.getString("MailLogDate"));         
                item.setNote(rs.getString("Note").trim().equals("null") ? "" : rs.getString("Note"));
                item.setApproved(rs.getString("Approved").trim().equals("null") ? "" : rs.getString("Approved"));
                item.setRequested(rs.getString("Requested").trim().equals("null") ? "" : rs.getString("Requested"));
                item.setRedacted(rs.getString("Redacted").trim().equals("null") ? "" : rs.getString("Redacted"));
                item.setRedactedHistoryID(rs.getString("RedactedHistoryID"));
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
