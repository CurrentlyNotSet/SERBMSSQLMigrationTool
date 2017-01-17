/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSResultModel;
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
 * @author User
 */
public class sqlCMDSResult {
    
    public static List<CMDSResultModel> getOldCMDSResults() {
        List<CMDSResultModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Results";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSResultModel item = new CMDSResultModel();
                item.setResultsID(rs.getInt("ResultsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setResult(rs.getString("Result"));
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
        
    public static void addCMDSResult(List<CMDSResultModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSResult("
                    + "active, "    //01
                    + "result, "    //02
                    + "Description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (CMDSResultModel item : list) {
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getResult());
            ps.setString (3, item.getDescription());
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
