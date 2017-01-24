/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldCountyModel;
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
 * @author Andrew
 */
public class sqlSystemData {
    
    public static List<oldCountyModel> getCounties() {
        List<oldCountyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM County";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCountyModel item = new oldCountyModel();
                item.setCountyid(rs.getInt("Countyid"));
                item.setActive(rs.getInt("Active"));
                item.setStateCode(rs.getString("StateCode"));
                item.setRegionCode(rs.getString("RegionCode"));
                item.setCountyCode(rs.getString("CountyCode"));
                item.setName(rs.getString("Name"));
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
    
    public static void batchAddCounty(List<oldCountyModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO County ("
                    + "active, "    //01
                    + "stateCode, " //02
                    + "regionCode, "//03
                    + "countyCode, "//04
                    + "countyName " //05
                    + ") VALUES ("
                    + "?," //01
                    + "?," //02
                    + "?," //03
                    + "?," //04
                    + "?)"; //05
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (oldCountyModel item : list) {
                ps.setInt(1, "OH".equals(item.getStateCode().trim()) ? 1 : 0);
                ps.setString(2, StringUtils.left(item.getStateCode(), 4));
                ps.setString(3, StringUtils.left(item.getRegionCode(), 4));
                ps.setString(4, StringUtils.left(item.getCountyCode(), 4));
                ps.setString(5, StringUtils.left(item.getName(), 200));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                    ps.executeBatch();
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
