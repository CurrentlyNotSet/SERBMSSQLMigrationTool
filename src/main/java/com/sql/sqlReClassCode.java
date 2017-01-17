/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ReClassCodeModel;
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
public class sqlReClassCode {
    
    public static List<ReClassCodeModel> getoldReclassCodesList() {
        List<ReClassCodeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM ReClassCodes where code != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ReClassCodeModel item = new ReClassCodeModel();
                item.setId(rs.getInt("ReclassCodesID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setCode(rs.getString("Code"));
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
    
    public static void addReClassCode(List<ReClassCodeModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO ReClassCode ("
                    + "Active, "
                    + "code "  
                    + ") VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (ReClassCodeModel item : list) {
            ps.setBoolean(1, item.isActive());             
            ps.setString (2, item.getCode());
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
