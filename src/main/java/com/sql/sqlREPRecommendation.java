/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPRecommendationModel;
import com.util.DBCInfo;
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
public class sqlREPRecommendation {
    
    public static List<REPRecommendationModel> getOldREPRecommendation() {
        List<REPRecommendationModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPRec";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                REPRecommendationModel item = new REPRecommendationModel();
                item.setREPRecID(rs.getInt("REPRecID"));
                item.setActive(rs.getInt("Active"));
                item.setType(!"".equals(rs.getString("Type").trim()) ? rs.getString("Type").trim() : null);
                item.setRecommendation(!"".equals(rs.getString("Recommendation").trim()) ? rs.getString("Recommendation").trim() : null);
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
        
    public static void addREPRecommendation(REPRecommendationModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPRecommendation ("
                    + "Active, "        //01
                    + "Type, "          //02
                    + "Recommendation " //03
                    + ") VALUES ("
                    + "?, " //01
                    + "?, " //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, StringUtils.left(item.getType(), 20));
            ps.setString( 3, item.getRecommendation());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddREPRecommendation(List<REPRecommendationModel> list) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPRecommendation ("
                    + "Active, "        //01
                    + "Type, "          //02
                    + "Recommendation " //03
                    + ") VALUES ("
                    + "?, " //01
                    + "?, " //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (REPRecommendationModel item : list) {
                ps.setInt(1, item.getActive());
                ps.setString(2, StringUtils.left(item.getType(), 20));
                ps.setString(3, item.getRecommendation());
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
