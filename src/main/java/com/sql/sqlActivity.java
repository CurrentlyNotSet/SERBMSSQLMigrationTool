/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.activityModel;
import com.model.caseNumberModel;
import com.model.oldCMDSHistoryModel;
import com.model.oldCSCHistoryModel;
import com.model.oldMEDHistoryModel;
import com.model.oldORGHistoryModel;
import com.model.oldREPHistoryModel;
import com.model.oldSMDSHistoryModel;
import com.model.oldULPHistoryModel;
import com.util.DBCInfo;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

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
            ps.setString   ( 1, StringUtils.left(item.getCaseYear(), 4));
            ps.setString   ( 2, StringUtils.left(item.getCaseType(), 3));
            ps.setString   ( 3, StringUtils.left(item.getCaseMonth(), 2));
            ps.setString   ( 4, StringUtils.left(item.getCaseNumber(), 8));
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
    
    public static void batchAddORGActivity(List<oldORGHistoryModel> ORGCaseHistory) {
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
            
            for (oldORGHistoryModel old : ORGCaseHistory){
                int userID = StringUtilities.convertUserToID(old.getUserInitials());

                ps.setString   ( 1, null);
                ps.setString   ( 2, "ORG");
                ps.setString   ( 3, null);
                ps.setString   ( 4, StringUtils.left(old.getOrgNum(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, new Timestamp(old.getDateTimeMillis()));
                ps.setString   ( 7, !"".equals(old.getDescription().trim()) ? old.getDescription().trim() : null);
                ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                ps.setString   ( 9, !"".equals(old.getSrc().trim()) ? old.getSrc().trim() : null);
                ps.setString   (10, !"".equals(old.getDest().trim()) ? old.getDest().trim() : null);
                ps.setString   (11, !old.getFileAttrib().trim().equals("") ? old.getFileAttrib().trim() : null);
                ps.setString   (12, old.getNote() != null ? old.getNote().trim() : null);
                ps.setInt      (13, "Y".equals(old.getRedacted().trim()) ? 1 : 0);
                ps.setInt      (14, 0);
                ps.addBatch();
            }
            
            ps.executeBatch();
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
    
    public static void batchAddCSCActivity(List<oldCSCHistoryModel> CSCCaseHistory) {
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
            
            for (oldCSCHistoryModel old : CSCCaseHistory){
                int userID = StringUtilities.convertUserToID(old.getInitials());
                
                String comment = !old.getNote().trim().equals("null") ? old.getNote().trim() : "";
                if (!comment.trim().equals("")) {
                    comment += "/n/n";
                }
                comment += !old.getNote().trim().equals("null") ? old.getNote().trim() : "";

                ps.setString   ( 1, null);
                ps.setString   ( 2, "CSC");
                ps.setString   ( 3, null);
                ps.setString   ( 4, StringUtils.left(old.getCSCNumber(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, new Timestamp(old.getDateTimeMillis()));
                ps.setString   ( 7, !"".equals(old.getDescription().trim()) ? old.getDescription().trim() : null);
                ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                ps.setString   ( 9, null);
                ps.setString   (10, null);
                ps.setString   (11, !old.getFileAttrib().trim().equals("") ? old.getFileAttrib().trim() : null);
                ps.setString   (12, comment.trim().equals("") ? null : comment);
                ps.setInt      (13, 0);
                ps.setInt      (14, 0);
                ps.addBatch();
            }
            
            ps.executeBatch();
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
    
    public static void batchAddULPActivity(List<oldULPHistoryModel> ULPCaseHistory , caseNumberModel caseNumber) {
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
            
            for (oldULPHistoryModel old : ULPCaseHistory){
                int userID = StringUtilities.convertUserToID(old.getUserInitials());

                ps.setString   ( 1, StringUtils.left(caseNumber.getCaseYear(), 4));
                ps.setString   ( 2, StringUtils.left(caseNumber.getCaseType(), 3));
                ps.setString   ( 3, StringUtils.left(caseNumber.getCaseMonth(), 2));
                ps.setString   ( 4, StringUtils.left(caseNumber.getCaseNumber(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, old.getDate());
                ps.setString   ( 7, !"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
                ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                ps.setString   ( 9, !"".equals(old.getEmailFrom().trim()) ? old.getEmailFrom().trim() : null);
                ps.setString   (10, !"".equals(old.getEmailTo().trim()) ? old.getEmailTo().trim() : null);
                ps.setString   (11, null);
                ps.setString   (12, null);
                ps.setInt      (13, 0);
                ps.setInt      (14, 0);
                ps.addBatch();
            }
            
            ps.executeBatch();
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
    
    public static void batchAddMEDActivity(List<oldMEDHistoryModel> oldMEDCaseList , caseNumberModel caseNumber) {
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
            
            for (oldMEDHistoryModel old : oldMEDCaseList){
                int userID = StringUtilities.convertUserToID(old.getUserInitals());

                ps.setString   ( 1, StringUtils.left(caseNumber.getCaseYear(), 4));
                ps.setString   ( 2, StringUtils.left(caseNumber.getCaseType(), 3));
                ps.setString   ( 3, StringUtils.left(caseNumber.getCaseMonth(), 2));
                ps.setString   ( 4, StringUtils.left(caseNumber.getCaseNumber(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, old.getDate());
                ps.setString   ( 7, !"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
                ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                ps.setString   ( 9, !"".equals(old.getEmailFrom().trim()) ? old.getEmailFrom().trim() : null);
                ps.setString   (10, !"".equals(old.getEmailTo().trim()) ? old.getEmailTo().trim() : null);
                ps.setString   (11, null);
                ps.setString   (12, null);
                ps.setInt      (13, 0);
                ps.setInt      (14, 0);
                ps.addBatch();
            }
            
            ps.executeBatch();
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
    
    public static void batchAddREPActivity(List<oldREPHistoryModel> REPCaseHistory , caseNumberModel caseNumber) {
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
            
            for (oldREPHistoryModel old : REPCaseHistory){
                int userID = StringUtilities.convertUserToID(old.getUserInitals());

                ps.setString   ( 1, StringUtils.left(caseNumber.getCaseYear(), 4));
                ps.setString   ( 2, StringUtils.left(caseNumber.getCaseType(), 3));
                ps.setString   ( 3, StringUtils.left(caseNumber.getCaseMonth(), 2));
                ps.setString   ( 4, StringUtils.left(caseNumber.getCaseNumber(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, old.getDate());
                ps.setString   ( 7, !"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
                ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                ps.setString   ( 9, !"".equals(old.getEmailFrom().trim()) ? old.getEmailFrom().trim() : null);
                ps.setString   (10, !"".equals(old.getEmailTo().trim()) ? old.getEmailTo().trim() : null);
                ps.setString   (11, null);
                ps.setString   (12, null);
                ps.setInt      (13, "Y".equals(old.getRedacted().trim()) ? 1 : 0);
                ps.setInt      (14, 0);
                ps.addBatch();
            }
            
            ps.executeBatch();
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
    
    public static List<oldCSCHistoryModel> getCSCHistory() {
        List<oldCSCHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM CSCHistory";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCSCHistoryModel item = new oldCSCHistoryModel();
                
                item.setCSCHistoryID(rs.getInt("CSCHistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setInitials(rs.getString("Initials"));
                item.setCSCID(rs.getInt("CSCID"));
                item.setDateTimeMillis(rs.getLong("DateTimeMillis"));
                item.setDirection(rs.getString("Direction"));
                item.setDate(rs.getString("Date"));
                item.setTime(rs.getString("Time"));
                item.setCSCNumber(rs.getString("CSCNumber"));
                item.setType(rs.getString("Type"));
                item.setSection(rs.getString("Section"));
                item.setFileAttrib(rs.getString("FileAttrib"));
                item.setOtherComment(rs.getString("OtherComment"));
                item.setFileName(rs.getString("FileName"));
                item.setDescription(rs.getString("Description"));
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
    
    public static List<oldCMDSHistoryModel> getCMDSHistory() {
        List<oldCMDSHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [case].[month], [case].type, CaseHistory.*, Users.Username AS username "
                    + "FROM CaseHistory LEFT JOIN Users ON Users.Initials = CaseHistory.Userinitials "
                    + "LEFT JOIN [case] ON ([case].[year] = CaseHistory.[year] "
                    + "AND [case].[CaseSeqNumber] = CaseHistory.[CaseSeqNumber])";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCMDSHistoryModel item = new oldCMDSHistoryModel();

                item.setCaseHistoryID(rs.getInt("CaseHistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setCaseYear(rs.getString("Year") == null ? "" : rs.getString("Year"));
                item.setCaseType(rs.getString("Type") == null ? "" : rs.getString("Type"));
                item.setCaseMonth(rs.getString("Month") == null ? "" : rs.getString("Month"));
                item.setCaseSeqNumber(rs.getString("CaseSeqNumber"));
                item.setHistorySeqNumber(rs.getString("HistorySeqNumber"));
                item.setEntryType(rs.getString("EntryType"));
                item.setEntryDescription(rs.getString("EntryDescription"));
                item.setEntryDate(rs.getString("EntryDate").length() < 10 ? "" : rs.getString("EntryDate").substring(0, 10));
                item.setMailType(rs.getString("MailType"));
                
                if (rs.getString("username") != null){
                    item.setUserinitials(rs.getString("username").equals("") ? rs.getString("Userinitials") : rs.getString("username"));
                } else {
                    item.setUserinitials(rs.getString("Userinitials"));
                }
                item.setDocumentLink(rs.getString("DocumentLink"));
                item.setOrderType(rs.getString("OrderType"));
                item.setWhichDateField(rs.getString("WhichDateField"));
                item.setWhatDateB4(rs.getString("WhatDateB4"));
                item.setWhatDateAfter(rs.getString("WhatDateAfter"));
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
    
    public static List<oldSMDSHistoryModel> getSMDSHistory() {
        List<oldSMDSHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM smdshistory WHERE caseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldSMDSHistoryModel item = new oldSMDSHistoryModel();
                item.setSMDSHistoryID(rs.getInt("SMDSHistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setUserName(rs.getString("UserName"));
                item.setDate(rs.getString("Date"));
                item.setTime(rs.getString("Time"));
                item.setAction(rs.getString("Action"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setFileName(rs.getString("FileName"));
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
