/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.DirectorsModel;
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

/**
 *
 * @author User
 */
public class sqlDirector {
    
    public static List<DirectorsModel> getoldDirectorList() {
        List<DirectorsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Directors";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DirectorsModel item = new DirectorsModel();
                item.setID(rs.getInt("DirectorsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setAgency(rs.getString("Agency"));
                item.setTitle(rs.getString("Title"));
                item.setName(rs.getString("Name"));
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
    
    public static void batchAddDirector(List<DirectorsModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Director ("
                    + "Active, "  //01
                    + "Agency, "  //02
                    + "Title, "   //03
                    + "Name "     //04
                    + ") VALUES (";
                    for(int i=0; i<3; i++){
                        sql += "?, ";   //01-03
                    }
                     sql += "?)"; //04
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (DirectorsModel item : list) {
            ps.setBoolean(1, item.isActive());             
            ps.setString (2, item.getAgency());       
            ps.setString (3, item.getTitle());       
            ps.setString (4, item.getName());             
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
