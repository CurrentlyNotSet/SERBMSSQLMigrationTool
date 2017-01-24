/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.util.DBCInfo;
import com.util.SlackNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlServerEmailControl {
    
    public static void verifyBlankRow() {
        long count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT COUNT(*) AS row FROM serveremailcontrol";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getLong("row");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        if (count == 0){
            insertBlankRow();
        }
    }
    
    private static void insertBlankRow() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO serveremailcontrol DEFAULT VALUES";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
    }
        
}
