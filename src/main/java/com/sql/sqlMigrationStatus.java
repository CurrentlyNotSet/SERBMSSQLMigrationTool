/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.migrationStatusModel;
import com.util.DBCInfo;
import com.util.SlackNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlMigrationStatus {
    
    public static migrationStatusModel getMigrationStatus() {
        migrationStatusModel item = new migrationStatusModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM migrationStatus WHERE "
                    + "id = (SELECT TOP 1 id FROM migrationStatus)";
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            if(rs.first()){
                item.setId(rs.getInt("id"));
                item.setMigrateULPCases(rs.getTimestamp("MigrateULPCases"));
                item.setMigrateMEDCases(rs.getTimestamp("MigrateMEDCases"));
                item.setMigrateREPCases(rs.getTimestamp("MigrateREPCases"));
                item.setMigrateORGCase(rs.getTimestamp("MigrateORGCases"));
                item.setMigrateCSCCases(rs.getTimestamp("MigrateCSCCases"));
                item.setMigrateCMDSCases(rs.getTimestamp("MigrateCMDSCases"));
                item.setMigrateContacts(rs.getTimestamp("MigrateContacts"));
                item.setMigrateUsers(rs.getTimestamp("MigrateUsers"));
                item.setMigrateDocuments(rs.getTimestamp("MigrateDocuments"));
                item.setMigrateHearingsCases(rs.getTimestamp("MigrateHearingsCases"));
                item.setMigratePublicRecords(rs.getTimestamp("MigratePublicRecords"));
                item.setMigrateEmployers(rs.getTimestamp("MigrateEmployers"));
                item.setMigrateSystemDefaults(rs.getTimestamp("MigrateSystemDefaults"));
            } else {
                item = null;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return item;
    }
    
    public static void updateTimeCompleted(String column){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "UPDATE migrationStatus SET " + column + " = GETDATE() "
                    + "WHERE id = (SELECT TOP 1 id FROM migrationStatus)";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void verifyBlankRow() {
        long count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT COUNT(*) AS row FROM migrationStatus";
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
            String sql = "INSERT INTO migrationStatus DEFAULT VALUES";
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
