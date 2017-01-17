/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSReport;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlCMDSReport {
    
    public static void addCMDSReport(CMDSReport item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSReport ("
                    + "section, "    //01
                    + "description, "//02
                    + "fileName, "   //03
                    + "parameters, " //04
                    + "active, "     //05
                    + "sortOrder "   //06
                    + ") VALUES (";
            for (int i = 0; i < 05; i++) {
                sql += "?, ";   //01-05
            }
            sql += "?)";   //06
            ps = conn.prepareStatement(sql);
            ps.setString (1, item.getSection());
            ps.setString (2, item.getDescription());
            ps.setString (3, item.getFileName());
            ps.setString (4, item.getParameters());
            ps.setBoolean(5, item.isActive());
            ps.setNull   (6, java.sql.Types.DOUBLE);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddCMDSReport(List<CMDSReport> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSReport ("
                    + "section, "    //01
                    + "description, "//02
                    + "fileName, "   //03
                    + "parameters, " //04
                    + "active, "     //05
                    + "sortOrder "   //06
                    + ") VALUES (";
            for (int i = 0; i < 05; i++) {
                sql += "?, ";   //01-05
            }
            sql += "?)";   //06
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (CMDSReport item : list){
                ps.setString (1, item.getSection());
                ps.setString (2, item.getDescription());
                ps.setString (3, item.getFileName());
                ps.setString (4, item.getParameters());
                ps.setBoolean(5, item.isActive());
                ps.setNull   (6, java.sql.Types.DOUBLE);
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
