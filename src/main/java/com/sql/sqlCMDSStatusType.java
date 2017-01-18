/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSStatusTypeModel;
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
 * @author User
 */
public class sqlCMDSStatusType {
    
    public static List<CMDSStatusTypeModel> getOldCMDSStatusType() {
        List<CMDSStatusTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM StatusTypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSStatusTypeModel item = new CMDSStatusTypeModel();
                item.setID(rs.getInt("StatusTypesID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setStatusCode(rs.getString("Status"));
                item.setDescription(rs.getString("Description"));               
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
        
    public static void batchAddCMDSStatusType(List<CMDSStatusTypeModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSStatusType ("
                    + "active, "    //01
                    + "StatusCode, "//02
                    + "Description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (CMDSStatusTypeModel item : list) {
                ps.setBoolean(1, item.isActive());
                ps.setString (2, StringUtils.left(item.getStatusCode(), 1));
                ps.setString (3, StringUtils.left(item.getDescription(), 50));
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
