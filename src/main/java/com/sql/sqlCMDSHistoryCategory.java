/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSHistoryCategoryModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
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
public class sqlCMDSHistoryCategory {
    
    public static List<CMDSHistoryCategoryModel> getOldCMDSHistoryCategory() {
        List<CMDSHistoryCategoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Categories";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSHistoryCategoryModel item = new CMDSHistoryCategoryModel();
                item.setId(rs.getInt("CategoriesID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setEntryType(rs.getString("EntryType").trim().equals("") ? null : rs.getString("EntryType").trim());
                item.setDescription(rs.getString("Description").trim().equals("") ? null : rs.getString("Description").trim());               
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
        
    public static void batchAddCMDSHistoryCategory(List<CMDSHistoryCategoryModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSHistoryCategory ("
                    + "active, "    //01
                    + "entryType, "//02
                    + "Description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (CMDSHistoryCategoryModel item : list) {
            ps.setBoolean(1, item.isActive());
            ps.setString (2, StringUtils.left(item.getEntryType(), 1));
            ps.setString (3, StringUtils.left(item.getDescription(), 100));
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
    
    public static void batchAddHearingsHistoryCategory(List<CMDSHistoryCategoryModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO HearingHistoryCategory ("
                    + "active, "    //01
                    + "entryType, "//02
                    + "Description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (CMDSHistoryCategoryModel item : list) {
            ps.setBoolean(1, item.isActive());
            ps.setString (2, StringUtils.left(item.getEntryType(), 1));
            ps.setString (3, StringUtils.left(item.getDescription(), 100));
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
