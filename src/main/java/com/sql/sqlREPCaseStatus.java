/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseStatusModel;
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
public class sqlREPCaseStatus {
    
    public static List<REPCaseStatusModel> getOldREPCaseStatus() {
        List<REPCaseStatusModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPStatusOptions";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                REPCaseStatusModel item = new REPCaseStatusModel();
                item.setId(rs.getInt("REPStatusID"));
                item.setActive(rs.getInt("Active"));
                item.setStatusType(!"".equals(rs.getString("StatusType").trim()) ? rs.getString("StatusType").trim() : null);
                item.setStatus(!"".equals(rs.getString("Status").trim()) ? rs.getString("Status").trim() : null);
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
        
    public static void batchAddREPCaseStatus(List<REPCaseStatusModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseStatus ("
                    + "Active, "    //01
                    + "StatusType, "//02
                    + "Status "     //03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPCaseStatusModel item : list) {
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getStatusType(), 1));
                ps.setString( 3, StringUtils.left(item.getStatus(), 100));
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
