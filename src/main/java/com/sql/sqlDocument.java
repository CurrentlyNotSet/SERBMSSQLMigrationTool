/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.SMDSDocumentsModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class sqlDocument {

    public static void batchAddSMDSDocument(List<SMDSDocumentsModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SMDSDocuments ("
                    + "section, " //01
                    + "type, " //02
                    + "description, " //03
                    + "fileName, " //04
                    + "active, " //05
                    + "dueDate, " //06
                    + "[group], " //07
                    + "historyFileName, " //08
                    + "historyDescription, "//09
                    + "CHDCHG, " //10
                    + "questionsFileName, " //11
                    + "emailSubject, " //12
                    + "parameters, " //13
                    + "emailBody, " //14
                    + "sortOrder " //15
                    + ") VALUES (";
            for (int i = 0; i < 14; i++) {
                sql += "?, ";   //01-14
            }
            sql += "?)";   //15
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (SMDSDocumentsModel item : list){
                ps.setString(1, StringUtils.left(item.getSection(), 10));
                ps.setString(2, StringUtils.left(item.getType(), 25));
                ps.setString(3, item.getDescription());
                ps.setString(4, item.getFileName());
                ps.setBoolean(5, item.isActive());
                if (item.getDueDate() > 0) {
                    ps.setInt(6, item.getDueDate());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }
                ps.setString(7, StringUtils.left(item.getGroup(), 100));
                ps.setString(8, StringUtils.left(item.getHistoryFileName(), 255));
                ps.setString(9, item.getHistoryDescription());
                ps.setString(10, StringUtils.left(item.getCHDCHG(), 3));
                ps.setString(11, StringUtils.left(item.getQuestionsFileName(), 255));
                ps.setString(12, item.getEmailSubject());
                ps.setString(13, item.getParameters());
                ps.setString(14, item.getEmailBody());
                if (item.getSortOrder() > 0) {
                    ps.setDouble(15, item.getSortOrder());
                } else {
                    ps.setNull(15, java.sql.Types.DOUBLE);
                }
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
    
}
