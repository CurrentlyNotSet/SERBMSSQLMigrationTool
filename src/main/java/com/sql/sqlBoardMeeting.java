/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.boardMeetingModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Andrew
 */
public class sqlBoardMeeting {
    
    public static void batchAddBoardMeeting(List<boardMeetingModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO BoardMeeting("
                    + "caseYear, "        //01
                    + "caseType, "        //02
                    + "caseMonth, "       //03
                    + "caseNumber, "      //04
                    + "boardMeetingDate, "//05
                    + "agendaItemNumber, "//06
                    + "recommendation, "  //07
                    + "memoDate "         //08
                    + ") VALUES (";
                    for(int i=0; i<7; i++){
                        sql += "?, ";   //01-07
                    }
                     sql += "?)"; //08
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (boardMeetingModel item : list) {
            ps.setString   (1, StringUtils.left(item.getCaseYear(), 4));
            ps.setString   (2, StringUtils.left(item.getCaseType(), 3));
            ps.setString   (3, StringUtils.left(item.getCaseMonth(), 2));
            ps.setString   (4, StringUtils.left(item.getCaseNumber(), 4));
            ps.setTimestamp(5, item.getBoardMeetingDate());
            ps.setString   (6, StringUtils.left(item.getAgendaItemNumber(), 4));
            ps.setString   (7, item.getRecommendation());
            ps.setTimestamp(8, item.getMemoDate());
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
