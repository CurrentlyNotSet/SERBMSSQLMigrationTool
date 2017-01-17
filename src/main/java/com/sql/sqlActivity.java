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
import com.model.oldPublicRecordsModel;
import com.model.oldREPHistoryModel;
import com.model.oldSMDSHistoryModel;
import com.model.oldULPHistoryModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Andrew
 */
public class sqlActivity {
        
    public static void batchAddActivity(List<activityModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
            for (activityModel item : list){
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
    
    public static void batchAddPublicRecordActivity(List<oldPublicRecordsModel> list, MainWindowSceneController control) {
        int totalRecordCount = list.size();
        int count = 0;
        
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
            conn.setAutoCommit(false);
            
            for (oldPublicRecordsModel item : list) {
                caseNumberModel caseNumber = null;
                if (item.getCaseNumber().trim().length() == 16) {
                    caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
                } else if (item.getCaseNumber().trim().length() == 4){
                    caseNumber = new caseNumberModel();
                    caseNumber.setCaseYear(null);
                    caseNumber.setCaseType("ORG");
                    caseNumber.setCaseMonth(null);
                    caseNumber.setCaseNumber(item.getCaseNumber());
                }

                if(caseNumber != null){
                    Timestamp date = null;
                    if (item.getDateTime().length() > 10){
                        String[] dateTime = item.getDateTime().split(" ", 2);

                        date = StringUtilities.convertStringDateAndTime(dateTime[0].replaceAll(" ", "").trim(), dateTime[1].replaceAll(" ", "").trim());
                    } else if (item.getDateTime().length() < 10 && item.getDateTime().length() > 1){
                        date = StringUtilities.convertStringTimeStamp(item.getDateTime() + " 00:00:00.000");
                    }

                    String Comment = "";
                    if (!item.getBody().trim().equals("")){
                        Comment += "Body: " + item.getBody();
                    }
                    if (!item.getNotes().trim().equals("")){
                        Comment += System.lineSeparator() + System.lineSeparator() + "Notes: " + item.getNotes();
                    }
                    
                    ps.setString   ( 1, caseNumber.getCaseYear());
                    ps.setString   ( 2, caseNumber.getCaseType());
                    ps.setString   ( 3, caseNumber.getCaseMonth());
                    ps.setString   ( 4, StringUtils.left(caseNumber.getCaseNumber(), 8));
                    ps.setNull     ( 5, java.sql.Types.INTEGER);
                    ps.setTimestamp( 6, date);
                    ps.setString   ( 7, !"".equals(item.getDocumentName().trim()) ? item.getDocumentName().trim() : null);
                    ps.setString   ( 8, !"".equals(item.getFileName().trim()) ? FilenameUtils.getName(item.getFileName().trim()) : null);
                    ps.setString   ( 9, null);
                    ps.setString   (10, !"".equals(item.getEmailAddress().trim()) ? item.getEmailAddress().trim() : null);
                    ps.setString   (11, null);
                    ps.setString   (12, Comment.trim().equals("") ? null : Comment.trim());
                    ps.setInt      (13, 0);
                    ps.setInt      (14, 0);
                    ps.addBatch();
                    if(++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
                        SceneUpdater.listItemFinished(control, count - 1, totalRecordCount, count + " imported");
                    }
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
    
    public static void batchAddORGActivity(List<oldORGHistoryModel> ORGCaseHistory) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
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
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
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
    
    public static void batchAddHearingsActivity(List<oldSMDSHistoryModel> list) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
            for (oldSMDSHistoryModel old : list){
                caseNumberModel caseNumber = null;
                if (old.getCaseNumber().trim().length() == 16) {
                    caseNumber = StringUtilities.parseFullCaseNumber(old.getCaseNumber().trim());
                }

                if(caseNumber != null){
                    int userID = StringUtilities.convertUserToID(old.getUserName());
                    
                    ps.setString   ( 1, caseNumber.getCaseYear());
                    ps.setString   ( 2, caseNumber.getCaseType());
                    ps.setString   ( 3, caseNumber.getCaseMonth());
                    ps.setString   ( 4, caseNumber.getCaseNumber());
                    if (userID != 0){
                        ps.setInt  ( 5, userID);
                    } else {
                        ps.setNull ( 5, java.sql.Types.INTEGER);
                    }
                    ps.setTimestamp( 6, (StringUtilities.convertStringDateAndTime(old.getDate(), old.getTime())));
                    ps.setString   ( 7, !"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
                    ps.setString   ( 8, !"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
                    ps.setString   ( 9, null);
                    ps.setString   (10, null);
                    ps.setString   (11, null);
                    ps.setString   (12, null);
                    ps.setInt      (13, 0);
                    ps.setInt      (14, 0);
                    ps.addBatch();
                    if (++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
                    }
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
    
    public static void batchAddCSCActivity(List<oldCSCHistoryModel> CSCCaseHistory) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
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
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
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
    
    public static void batchAddCMDSActivity(List<oldCMDSHistoryModel> CMDSCaseHistory, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
            for (oldCMDSHistoryModel old : CMDSCaseHistory){
                String direction = "";
                if (old.getMailType().equals("I")) {
                    direction = "IN - ";
                } else if (old.getMailType().equals("O")) {
                    direction = "OUT - ";
                }
                int userID = StringUtilities.convertUserToID(old.getUserinitials());

                ps.setString   ( 1, StringUtils.left(old.getCaseYear(), 4));
                ps.setString   ( 2, StringUtils.left(old.getCaseType(), 3));
                ps.setString   ( 3, StringUtils.left(old.getCaseMonth(), 2));
                ps.setString   ( 4, StringUtils.left(old.getCaseSeqNumber(), 8));
                if (userID != 0){
                    ps.setInt  ( 5, userID);
                } else {
                    ps.setNull ( 5, java.sql.Types.INTEGER);
                }
                ps.setTimestamp( 6, old.getEntryDate().equals("") ? null : StringUtilities.convertStringTimeStamp(old.getEntryDate()));
                ps.setString   ( 7, !old.getEntryDescription().trim().equals("") ? direction + old.getEntryDescription().trim() : null);
                ps.setString   ( 8, !old.getDocumentLink().trim().equals("") ? old.getDocumentLink().trim() : null);
                ps.setString(9, null);
                ps.setString(10, null);
                ps.setString(11, !old.getEntryType().trim().equals("") ? old.getEntryType().trim() : null);
                ps.setString(12, null);
                ps.setInt   (13, 0);
                ps.setInt   (14, 0);
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
        
    public static List<activityModel> getULPHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, CAST(date AS datetime) AS Date2 FROM ULPHistory WHERE LEN(CaseNumber) = 16";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                activityModel item = new activityModel();
                caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(rs.getString("CaseNumber").trim());
                if (caseNumber != null){
                    item.setCaseYear(caseNumber.getCaseYear());
                    item.setCaseType(caseNumber.getCaseType());
                    item.setCaseMonth(caseNumber.getCaseMonth());
                    item.setCaseNumber(caseNumber.getCaseNumber());
                    item.setUserID(StringUtilities.convertUserToID(rs.getString("UserInitals")));
                    item.setDate(rs.getTimestamp("Date2"));
                    item.setAction(rs.getString("Action").trim().equals("") ? null : rs.getString("Action").trim());
                    item.setFileName(rs.getString("FileName").trim().equals("") ? null : rs.getString("FileName").trim());
                    item.setFrom(rs.getString("EmailFrom").trim().equals("") ? null : rs.getString("EmailFrom").trim());
                    item.setTo(rs.getString("EmailTo").trim().equals("") ? null : rs.getString("EmailTo").trim());
                    item.setType(null);
                    item.setComment(null);
                    item.setRedacted(rs.getString("Redacted").equals("Y") ? 1 : 0);
                    item.setAwaitingTimeStamp(0);
                    list.add(item);
                }
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
    
    public static List<activityModel> getMEDHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, CAST(date AS datetime) AS Date2 FROM medhistory WHERE LEN(CaseNumber) = 16";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                activityModel item = new activityModel();
                caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(rs.getString("CaseNumber").trim());
                if (caseNumber != null){
                    item.setCaseYear(caseNumber.getCaseYear());
                    item.setCaseType(caseNumber.getCaseType());
                    item.setCaseMonth(caseNumber.getCaseMonth());
                    item.setCaseNumber(caseNumber.getCaseNumber());
                    item.setUserID(StringUtilities.convertUserToID(rs.getString("UserInitals")));
                    item.setDate(rs.getTimestamp("Date2"));
                    item.setAction(rs.getString("Action").trim().equals("") ? null : rs.getString("Action").trim());
                    item.setFileName(rs.getString("FileName").trim().equals("") ? null : rs.getString("FileName").trim());
                    item.setFrom(rs.getString("EmailFrom").trim().equals("") ? null : rs.getString("EmailFrom").trim());
                    item.setTo(rs.getString("EmailTo").trim().equals("") ? null : rs.getString("EmailTo").trim());
                    item.setType(null);
                    item.setComment(null);
                    item.setRedacted(rs.getString("Redacted").equals("Y") ? 1 : 0);
                    item.setAwaitingTimeStamp(0);
                    list.add(item);
                }
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
    
    public static List<activityModel> getREPHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT *, CAST(date AS datetime) AS Date2 FROM REPHistory WHERE LEN(CaseNumber) = 16";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                activityModel item = new activityModel();
                caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(rs.getString("CaseNumber").trim());
                if (caseNumber != null){
                    item.setCaseYear(caseNumber.getCaseYear());
                    item.setCaseType(caseNumber.getCaseType());
                    item.setCaseMonth(caseNumber.getCaseMonth());
                    item.setCaseNumber(caseNumber.getCaseNumber());
                    item.setUserID(StringUtilities.convertUserToID(rs.getString("UserInitals")));
                    item.setDate(rs.getTimestamp("Date2"));
                    item.setAction(rs.getString("Action").trim().equals("") ? null : rs.getString("Action").trim());
                    item.setFileName(rs.getString("FileName").trim().equals("") ? null : rs.getString("FileName").trim());
                    item.setFrom(rs.getString("EmailFrom").trim().equals("") ? null : rs.getString("EmailFrom").trim());
                    item.setTo(rs.getString("EmailTo").trim().equals("") ? null : rs.getString("EmailTo").trim());
                    item.setType(null);
                    item.setComment(null);
                    item.setRedacted(rs.getString("Redacted").equals("Y") ? 1 : 0);
                    item.setAwaitingTimeStamp(0);
                    list.add(item);
                }
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
    
    public static List<oldSMDSHistoryModel> getSMDSHearingHistory() {
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
