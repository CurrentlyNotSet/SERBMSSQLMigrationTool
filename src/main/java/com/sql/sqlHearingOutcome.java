/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.HearingOutcomeModel;
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
public class sqlHearingOutcome {
    
    public static List<HearingOutcomeModel> getOutcomeList() {
        List<HearingOutcomeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM HearingOutcome";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HearingOutcomeModel item = new HearingOutcomeModel();
                item.setId(rs.getInt("HearingOutcomeID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setDescription(rs.getString("Description"));
                item.setType(rs.getString("Type"));               
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
    
    public static void batchAddOutcome(List<HearingOutcomeModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO HearingOutcome ("
                    + "active, "    //01
                    + "type, "    //02
                    + "description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (HearingOutcomeModel item : list) {
                ps.setBoolean(1, item.isActive());
                ps.setString (2, StringUtils.left(item.getType(), 10));
                ps.setString (3, item.getDescription());
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
            };
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
