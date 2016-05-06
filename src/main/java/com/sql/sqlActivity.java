/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.activityModel;
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
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?,"  //06
                    + "?,"  //07
                    + "?,"  //08
                    + "?,"  //09
                    + "?,"  //10
                    + "?,"  //11
                    + "?,"  //12
                    + "?,"  //13
                    + "?)"; //14
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
}
