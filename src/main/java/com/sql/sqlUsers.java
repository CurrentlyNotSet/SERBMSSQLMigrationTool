/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.userModel;
import com.util.DBCInfo;
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
                item.setLastName(rs.getString("Name"));
                item.setUserName(rs.getString("Username"));
                item.setEmail(rs.getString("Email"));
                
                //blank data
                item.setWorkPhone("");
                item.setPasswordSalt(0);
                item.setPassword("");
                item.setLastLoginDateTime(null);
                item.setLastLoginPCName("");
                item.setActiveLogin(false);
                item.setPasswordReset(true);
                item.setApplicationVersion("");
                item.setDefaultSection("");
                item.setULPCaseWorker(true);
                item.setMediator(true);
                item.setREPDocketing(true);
                item.setULPDocketing(true);
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
                    + "mediator, "          //17
                    + "ULPDocketing, "      //18
                    + "REPDocketing "       //19
                    + ") VALUES ("
                    + "?, " //01 active
                    + "?, " //02 firstName
                    + "?, " //03 middleInital
                    + "?, " //04 lastName
                    + "?, " //05 username
                    + "?, " //06 emailAddress
                    + "?, " //07 workPhone
                    + "?, " //08 passwordSalt
                    + "?, " //09 password
                    + "?, " //10 lastLoginDateTime
                    + "?, " //11 lastLoginPCName
                    + "?, " //12 activeLogin
                    + "?, " //13 passwordReset
                    + "?, " //14 applicationVersion
                    + "?, " //15 defaultSection
                    + "?, " //16 ULPCaseWorker
                    + "?, " //17 mediator
                    + "?, " //18 ULPDocketing
                    + "?) ";//19 REPDocketing
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
            ps.setBoolean  (17, item.isMediator());
            ps.setBoolean  (18, item.isULPDocketing());
            ps.setBoolean  (19, item.isREPDocketing());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
}
