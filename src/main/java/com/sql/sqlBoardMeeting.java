/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.boardMeetingModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlBoardMeeting {
    
    public static void addULPBoardMeeting(boardMeetingModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO BoardMeeting("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "boardMeetingDate, "  //05
                    + "agendaItemNumber, " //06
                    + "recommendation "     //07
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?,"  //06
                    + "?)"; //07
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getCaseYear());
            ps.setString(2, item.getCaseType());
            ps.setString(3, item.getCaseMonth());
            ps.setString(4, item.getCaseNumber());
            ps.setTimestamp(5, item.getBoardMeetingDate());
            ps.setString(6, item.getAgendaItemNumber());
            ps.setString(7, item.getRecommendation());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
