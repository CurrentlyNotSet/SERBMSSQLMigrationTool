/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.userModel;
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
public class sqlUsers {
        
    public static List<userModel> getUsers() {
        List<userModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Users";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                userModel item = new userModel();
                item.setActive(rs.getInt("Active"));
                item.setLastName(rs.getString("Name").trim());
                item.setInitials(rs.getString("Initials").trim());
                item.setUserName(rs.getString("Username").toLowerCase());
                item.setEmail(rs.getString("Email").trim());
                
                //blank data
                item.setFirstName(null);
                item.setMiddleInitial(null);
                item.setWorkPhone(null);
                item.setPasswordSalt(0);
                item.setPassword(null);
                item.setLastLoginDateTime(null);
                item.setLastLoginPCName(null);
                item.setActiveLogin(false);
                item.setPasswordReset(true);
                item.setApplicationVersion(null);
                item.setDefaultSection(null);
                item.setULPCaseWorker(true);
                item.setMediator(true);
                item.setREPDocketing(true);
                item.setULPDocketing(true);
                item.setJobTitle(null);
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
    
    public static List<userModel> getSecUsers() {
        List<userModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Secuser";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                userModel item = new userModel();
                item.setActive(rs.getInt("Active"));
                item.setFirstName(rs.getString("firstname"));
                item.setMiddleInitial(rs.getString("middlename"));
                item.setLastName(rs.getString("lastName"));
                item.setInitials(rs.getString("UserInitials"));
                item.setUserName(rs.getString("Username").toLowerCase());
                item.setEmail(rs.getString("userEmail"));
                
                //blank data
                item.setWorkPhone(null);
                item.setPasswordSalt(0);
                item.setPassword(null);
                item.setLastLoginDateTime(null);
                item.setLastLoginPCName(null);
                item.setActiveLogin(false);
                item.setPasswordReset(true);
                item.setApplicationVersion(null);
                item.setDefaultSection(null);
                item.setULPCaseWorker(true);
                item.setMediator(true);
                item.setREPDocketing(true);
                item.setULPDocketing(true);
                item.setJobTitle(null);
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
    
    public static void getNewDBUsers() {
        List<userModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT "
                    + "id, Username, firstname, middleinitial, lastName, initials "
                    + "FROM Users";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                userModel item = new userModel();
                item.setId(rs.getInt("id"));
                item.setUserName(rs.getString("Username") == null ? "" : rs.getString("Username"));
                item.setFirstName(rs.getString("firstname") == null ? "" : rs.getString("firstname"));
                item.setMiddleInitial(rs.getString("middleinitial") == null ? "" : rs.getString("middleinitial"));
                item.setLastName(rs.getString("lastName") == null ? "" : rs.getString("lastName"));
                item.setInitials(rs.getString("initials") == null ? "" : rs.getString("initials"));
                list.add(item);
            }
            Global.setUserList(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
    }
    
    public static void saveUserInformation(userModel item){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO Users ("
                    + "active, "       //01
                    + "firstName, "    //02
                    + "middleInitial, "//03
                    + "lastName, "     //04
                    + "username, "     //05
                    + "emailAddress, " //06
                    + "workPhone, "    //07
                    + "passwordSalt, "      //08
                    + "password, "          //09
                    + "lastLoginDateTime, " //10
                    + "lastLoginPCName, "   //11
                    + "activeLogin, "       //12
                    + "passwordReset, "     //13
                    + "applicationVersion, "//14
                    + "defaultSection, "    //15
                    + "ULPCaseWorker, "     //16
                    + "ULPDocketing, "      //17
                    + "REPDocketing, "      //18
                    + "initials, "          //19
                    + "jobTitle "           //20
                    + ") VALUES (";
                    for(int i=0; i<19; i++){
                        sql += "?, ";   //01-19
                    }
                     sql += "?)";   //20
            ps = conn.prepareStatement(sql);
            ps.setInt      ( 1, item.getActive());
            ps.setString   ( 2, item.getFirstName());
            ps.setString   ( 3, item.getMiddleInitial());
            ps.setString   ( 4, item.getLastName());
            ps.setString   ( 5, item.getUserName());
            ps.setString   ( 6, item.getEmail());
            ps.setString   ( 7, item.getWorkPhone());
            ps.setInt      ( 8, item.getPasswordSalt());
            ps.setString   ( 9, item.getPassword());
            ps.setTimestamp(10, item.getLastLoginDateTime());
            ps.setString   (11, item.getLastLoginPCName());
            ps.setBoolean  (12, item.isActiveLogin());
            ps.setBoolean  (13, item.isPasswordReset());
            ps.setString   (14, item.getApplicationVersion());
            ps.setString   (15, item.getDefaultSection());
            ps.setBoolean  (16, item.isULPCaseWorker());
            ps.setBoolean  (17, item.isULPDocketing());
            ps.setBoolean  (18, item.isREPDocketing());
            ps.setString   (19, item.getInitials());
            ps.setString   (20, item.getJobTitle());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
}
