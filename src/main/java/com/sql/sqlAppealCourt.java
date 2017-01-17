/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.appealCourtModel;
import com.util.DBCInfo;
import com.util.Global;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlAppealCourt {
    
    public static List<appealCourtModel> getOldCMDSHistoryDescription() {
        List<appealCourtModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM AppealCourts";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                appealCourtModel item = new appealCourtModel();
                item.setId(rs.getInt("AppealCourtsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setCourtName(rs.getString("CourtName").trim().equals("") ? null : rs.getString("CourtName").trim());
                item.setType(rs.getString("Type").trim().equals("") ? null : rs.getString("Type").trim());               
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
        
    public static void batchAddAppealCourt(List<appealCourtModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO AppealCourt ("
                    + "active, "    //01
                    + "type, "//02
                    + "courtName "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (appealCourtModel item : list) {
                ps.setBoolean(1, item.isActive());
                ps.setString (2, item.getType());
                ps.setString (3, item.getCourtName());
                ps.addBatch();
                    if (++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
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
