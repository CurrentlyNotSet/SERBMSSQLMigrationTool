/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.activityModel;
import com.model.caseNumberModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
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
                    + "caseYear, "         //01
                    + "caseType, "         //02
                    + "caseMonth, "        //03
                    + "caseNumber, "       //04
                    + "userID, "           //05
                    + "date, "             //06
                    + "action, "           //07
                    + "fileName, "         //08
                    + "[from], "           //09
                    + "[to], "             //10
                    + "type, "             //11
                    + "comment, "          //12
                    + "redacted, "         //13
                    + "awaitingTimeStamp, "//14
                    + "active, "           //15
                    + "mailLog "           //16
                    + ") VALUES (";
                    for(int i=0; i<15; i++){
                        sql += "?, ";   //01-15
                    }
                     sql += "?)"; //16
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
                ps.setBoolean  (15, item.isActive());
                ps.setDate     (16, item.isMailLog());
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
                    item.setActive(rs.getInt("Active") == 1);
                    item.setMailLog(rs.getString("MailLogDate") == null ? null
                            : (rs.getString("MailLogDate").equals("") ? null
                                    : StringUtilities.convertStringSQLDate(rs.getString("MailLogDate"))));
                    list.add(item);
                }
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
                    item.setActive(rs.getInt("Active") == 1);
                    item.setMailLog(rs.getString("MailLogDate") == null ? null
                            : (rs.getString("MailLogDate").equals("") ? null
                                    : StringUtilities.convertStringSQLDate(rs.getString("MailLogDate"))));
                    list.add(item);
                }
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
                    item.setActive(rs.getInt("Active") == 1);
                    item.setMailLog(rs.getString("MailLogDate") == null ? null
                            : (rs.getString("MailLogDate").equals("") ? null
                                    : StringUtilities.convertStringSQLDate(rs.getString("MailLogDate"))));
                    list.add(item);
                }
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

    public static List<activityModel> getHearingsHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM smdshistory WHERE caseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                caseNumberModel caseNumber = null;
                if (rs.getString("CaseNumber").trim().length() == 16) {
                    caseNumber = StringUtilities.parseFullCaseNumber(rs.getString("CaseNumber").trim());
                }

                if(caseNumber != null){
                    activityModel item = new activityModel();
                    item.setCaseYear(caseNumber.getCaseYear());
                    item.setCaseType(caseNumber.getCaseType());
                    item.setCaseMonth(caseNumber.getCaseMonth());
                    item.setCaseNumber(caseNumber.getCaseNumber());
                    item.setUserID(StringUtilities.convertUserToID(rs.getString("UserName")));
                    item.setDate(StringUtilities.convertStringDateAndTime(rs.getString("Date"), rs.getString("Time")));
                    item.setAction(!"".equals(rs.getString("Action").trim()) ? rs.getString("Action").trim() : null);
                    item.setFileName(!"".equals(rs.getString("FileName").trim()) ? rs.getString("FileName").trim() : null);
                    item.setFrom(null);
                    item.setTo(null);
                    item.setType(null);
                    item.setComment(null);
                    item.setRedacted(0);
                    item.setAwaitingTimeStamp(0);
                    item.setActive(rs.getInt("Active") == 1);
                    item.setMailLog(null);
                    list.add(item);
                }
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

    public static List<activityModel> getPublicRecords() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT PublicRecordsDocument.*, "
                    + "cast(PublicRecordsBody.body as varchar(max)) as body, "
                    + "PublicRecordsEmail.EmailAddress, "
                    + "cast(PublicRecordsNotes.Notes as varchar(max)) as notes "
                    + "FROM PublicRecordsDocument "
                    + "LEFT JOIN PublicRecordsBody ON PublicRecordsBody.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsBody.Date = PublicRecordsDocument.Date "
                    + "LEFT JOIN PublicRecordsEmail ON PublicRecordsEmail.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsEmail.Date = PublicRecordsDocument.Date "
                    + "LEFT JOIN PublicRecordsNotes ON PublicRecordsNotes.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsNotes.Date = PublicRecordsDocument.Date "
                    + "WHERE PublicRecordsBody.CaseNumber != '' AND PublicRecordsDocument.CaseNumber != '' "
                    + "AND PublicRecordsEmail.CaseNumber != '' AND PublicRecordsNotes.CaseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                caseNumberModel caseNumber = null;
                if (rs.getString("CaseNumber").trim().length() == 16) {
                    caseNumber = StringUtilities.parseFullCaseNumber(rs.getString("CaseNumber").trim());
                } else if (rs.getString("CaseNumber").trim().length() == 4) {
                    caseNumber = new caseNumberModel();
                    caseNumber.setCaseYear(null);
                    caseNumber.setCaseType("ORG");
                    caseNumber.setCaseMonth(null);
                    caseNumber.setCaseNumber(rs.getString("CaseNumber"));
                }

                if (caseNumber != null) {
                    activityModel item = new activityModel();
                    Timestamp date = null;
                    if (rs.getString("Date").length() > 10) {
                        String[] dateTime = rs.getString("Date").split(" ", 2);

                        date = StringUtilities.convertStringDateAndTime(dateTime[0].replaceAll(" ", "").trim(), dateTime[1].replaceAll(" ", "").trim());
                    } else if (rs.getString("Date").length() < 10 && rs.getString("Date").length() > 1) {
                        date = StringUtilities.convertStringTimeStamp(rs.getString("Date") + " 00:00:00.000");
                    }

                    String Comment = "";
                    if (!rs.getString("body").trim().equals("")) {
                        Comment += "Body: " + rs.getString("body");
                    }
                    if (!rs.getString("Notes").trim().equals("")) {
                        Comment += System.lineSeparator() + System.lineSeparator() + "Notes: " + rs.getString("Notes");
                    }

                    item.setCaseYear(caseNumber.getCaseYear());
                    item.setCaseType(caseNumber.getCaseType());
                    item.setCaseMonth(caseNumber.getCaseMonth());
                    item.setCaseNumber(caseNumber.getCaseNumber());
                    item.setDate(date);
                    item.setAction(!"".equals(rs.getString("DocumentName").trim()) ? rs.getString("DocumentName").trim() : null);
                    item.setFileName(!"".equals(rs.getString("DocumentFileName").trim()) ? FilenameUtils.getName(rs.getString("DocumentFileName").trim()) : null);
                    item.setTo(rs.getString("EmailAddress"));
                    item.setComment(Comment.trim().equals("") ? null : Comment.trim());
                    item.setActive(rs.getInt("Active") == 1);
                    item.setMailLog(null);
                    list.add(item);
                }
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

    public static List<activityModel> getORGHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM orghistory";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String comment = "";
                comment = rs.getString("Note").trim().equals("null") ? "" : rs.getString("Note");
                if (!rs.getString("otherComment").trim().equals("")){
                    comment += System.lineSeparator() + System.lineSeparator() + rs.getString("otherComment");
                }

                activityModel item = new activityModel();
                item.setCaseYear(null);
                item.setCaseType("ORG");
                item.setCaseMonth(null);
                item.setCaseNumber(rs.getString("OrgNum").trim().equals("null") ? "" : rs.getString("OrgNum"));
                item.setUserID(StringUtilities.convertUserToID(rs.getString("UserInitials")));
                item.setDate(StringUtilities.convertStringDateAndTimeORG(rs.getString("Date"), rs.getString("Time").replaceAll("-", ":")));
                item.setAction(rs.getString("Description").trim().equals("null") ? "" : rs.getString("Description"));
                item.setFileName(rs.getString("FileName").trim().equals("null") ? "" : rs.getString("FileName"));
                item.setFrom(rs.getString("Src").trim().equals("null") ? "" : rs.getString("Src"));
                item.setTo(rs.getString("Dest").trim().equals("null") ? "" : rs.getString("Dest"));
                item.setType(rs.getString("FileAttrib").trim().equals("null") ? "" : rs.getString("FileAttrib"));
                item.setComment(comment.trim());
                item.setRedacted(rs.getString("Redacted").equals("Y") ? 1 : 0);
                item.setAwaitingTimeStamp(0);
                item.setActive(rs.getInt("Active") == 1);
                item.setMailLog(rs.getString("MailLogDate") == null ? null
                            : (rs.getString("MailLogDate").equals("") ? null
                                    : StringUtilities.convertStringSQLDate(rs.getString("MailLogDate"))));
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

    public static List<activityModel> getCSCHistory() {
        List<activityModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM CSCHistory";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                activityModel item = new activityModel();

                String comment = !rs.getString("Note").trim().equals("null") ? rs.getString("Note").trim() : "";
                if (!comment.trim().equals("")) {
                    comment += "/n/n";
                }
                comment += !rs.getString("OtherComment").trim().equals("null") ? rs.getString("OtherComment").trim() : "";

                item.setCaseYear(null);
                item.setCaseType("CSC");
                item.setCaseMonth(null);
                item.setCaseNumber(rs.getString("CSCNumber"));
                item.setUserID(StringUtilities.convertUserToID(rs.getString("Initials")));
                item.setDate(new Timestamp(rs.getLong("DateTimeMillis")));
                item.setAction(!"".equals(rs.getString("Description").trim()) ? rs.getString("Description").trim() : null);
                item.setFileName(!"".equals(rs.getString("FileName").trim()) ? rs.getString("FileName").trim() : null);
                item.setFrom(null);
                item.setTo(null);
                item.setType(!rs.getString("FileAttrib").trim().equals("") ? rs.getString("FileAttrib").trim() : null);
                item.setComment(comment.trim().equals("") ? null : comment);
                item.setRedacted(0);
                item.setAwaitingTimeStamp(0);
                item.setActive(rs.getInt("Active") == 1);
                if (rs.getString("Direction").trim().equals("I")){
                    item.setMailLog(rs.getString("Date") == null ? null
                            : (rs.getString("Date").equals("") ? null
                                    : StringUtilities.convertStringSQLDate(rs.getString("Date"))));
                } else {
                    item.setMailLog(null);
                }


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

    public static List<activityModel> getCMDSHistory() {
        List<activityModel> list = new ArrayList();
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
                activityModel item = new activityModel();
                String direction = "";
                if (rs.getString("MailType").equals("I")) {
                    direction = "IN - ";
                } else if (rs.getString("MailType").equals("O")) {
                    direction = "OUT - ";
                }
                String user = "";
                if (rs.getString("username") != null){
                    user = rs.getString("username").equals("") ? rs.getString("Userinitials") : rs.getString("username");
                } else {
                    user = rs.getString("Userinitials");
                }

                item.setId(rs.getInt("CaseHistoryID"));
                item.setCaseYear(rs.getString("Year") == null ? "" : rs.getString("Year"));
                item.setCaseType(rs.getString("Type") == null ? "" : rs.getString("Type"));
                item.setCaseMonth(rs.getString("Month") == null ? "" : rs.getString("Month"));
                item.setCaseNumber(rs.getString("CaseSeqNumber"));
                item.setAction(!rs.getString("EntryDescription").trim().equals("") ? direction + rs.getString("EntryDescription").trim() : null);
                item.setUserID(StringUtilities.convertUserToID(user));
                item.setType(rs.getString("EntryType"));
                item.setDate(rs.getString("EntryDate").length() < 10 ? null : StringUtilities.convertStringTimeStamp(rs.getString("EntryDate").substring(0, 10)));
                item.setFileName(!"".equals(rs.getString("DocumentLink").trim()) ? FilenameUtils.getName(rs.getString("DocumentLink").trim()) : null);
                item.setActive(rs.getInt("active") == 1);
                if (rs.getString("MailType").equals("I")){
                    item.setMailLog(rs.getString("EntryDate").length() < 10 ? null
                            : StringUtilities.convertStringSQLDate(rs.getString("EntryDate").substring(0, 10)));
                } else {
                    item.setMailLog(null);
                }
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

}
