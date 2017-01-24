/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.NextCaseNumberModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
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
public class sqlNextCaseNumber {
    
    public static List<NextCaseNumberModel> getOldCaseNextNumber() {
        List<NextCaseNumberModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Nextnumber ORDER BY NextnumberYear ASC, NextnumberType ASC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                NextCaseNumberModel item = new NextCaseNumberModel();
                item.setId(rs.getInt("Nextnumberid"));
                item.setActive(rs.getInt("active") == 1);
                item.setCaseType(rs.getString("NextnumberType"));
                item.setCaseYear(rs.getString("NextnumberYear"));
                item.setNextCaseNumber(String.valueOf(rs.getInt("Nextnumber")));
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
        
    public static void batchAddNextCaseNumber(List<NextCaseNumberModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CaseNumber ("
                    + "year, "     //01
                    + "caseType, " //02
                    + "caseNumber "//03
                    + ") VALUES ("
                    + "?, " //01
                    + "?, " //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (NextCaseNumberModel item : list) {
                ps.setString(1, StringUtils.left(item.getCaseYear(), 4));
                ps.setString(2, StringUtils.left(item.getCaseType(), 4));
                ps.setString(3, StringUtils.left(item.getNextCaseNumber(), 4));
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
