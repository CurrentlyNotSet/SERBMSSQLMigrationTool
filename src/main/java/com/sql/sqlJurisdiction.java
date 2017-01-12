/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.jurisdictionModel;
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
 * @author Andrew
 */
public class sqlJurisdiction {
    
    public static List<jurisdictionModel> getOldJurisdiction() {
        List<jurisdictionModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM medjurisdiction";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                jurisdictionModel item = new jurisdictionModel();
                item.setId(rs.getInt("MEDJurisdictionID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setJurisCode(!"".equals(rs.getString("Jurisdiction").trim()) ? rs.getString("Jurisdiction").trim() : null);
                item.setEmployerType(!"".equals(rs.getString("EmployerType").trim()) ? rs.getString("EmployerType").trim() : null);
                item.setJurisName(!"".equals(rs.getString("Description").trim()) ? rs.getString("Description").trim() : null);
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
        
    public static void addJurisdiction(jurisdictionModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Jurisdiction ("
                    + "active, "      //01
                    + "jurisCode, "   //02
                    + "employerType, "//03
                    + "jurisName "    //04
                    + ") VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getJurisCode());
            ps.setString (3, item.getEmployerType());
            ps.setString (4, item.getJurisName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddJurisdiction(List<jurisdictionModel> list) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Jurisdiction ("
                    + "active, "      //01
                    + "jurisCode, "   //02
                    + "employerType, "//03
                    + "jurisName "    //04
                    + ") VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (jurisdictionModel item : list) {
                ps.setBoolean(1, item.isActive());
                ps.setString (2, StringUtils.left(item.getJurisCode(), 4));
                ps.setString (3, StringUtils.left(item.getEmployerType(), 4));
                ps.setString (4, StringUtils.left(item.getJurisName(), 150));
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
