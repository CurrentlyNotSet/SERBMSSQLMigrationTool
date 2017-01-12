/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ULPRecommendationsModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class sqlULPRecommendations {
    
    public static List<ULPRecommendationsModel> getOLDULPRecommendations() {
        List<ULPRecommendationsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM ULPRecs";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ULPRecommendationsModel item = new ULPRecommendationsModel();
                item.setId(rs.getInt("UlpRecid"));
                item.setActive(rs.getInt("Active"));
                item.setCode(!"".equals(rs.getString("RecCode").trim()) ? rs.getString("RecCode").trim() : null);
                item.setDescription(!"".equals(rs.getString("RecDescription").trim()) ? rs.getString("RecDescription").trim() : null);
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
        
    public static void addULPRecommendation(ULPRecommendationsModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO ULPRecommendation ("
                    + "active, "    //01
                    + "code, "      //02
                    + "description "//03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            ps.setInt   (1, item.getActive());
            ps.setString(2, StringUtils.left(item.getCode(), 50));
            ps.setString(3, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddULPRecommendation(List<ULPRecommendationsModel> oldULPRecsList) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            
            String sql = "Insert INTO ULPRecommendation ("
                    + "active, "    //01
                    + "code, "      //02
                    + "description "//03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            
            conn.setAutoCommit(false);
            
            for (ULPRecommendationsModel item : oldULPRecsList) {
                ps.setInt   (1, item.getActive());
                ps.setString(2, StringUtils.left(item.getCode(), 50));
                ps.setString(3, item.getDescription());
                ps.addBatch();
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
