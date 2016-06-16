/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.systemEmailModel;
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
 * @author User
 */
public class sqlSystemEmail {
    
    public static List<systemEmailModel> getOldSystemEmail() {
        List<systemEmailModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM SystemEmail WHERE IO = 'IN'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                systemEmailModel item = new systemEmailModel();
                item.setId(rs.getInt("SystemEMailid"));
                item.setActive(rs.getInt("Active"));
                item.setSection(rs.getString("Section").trim());
                item.setEmailAddress(rs.getString("EMailAddress").trim());
                item.setUsername(rs.getString("User").trim());
                item.setPassword(rs.getString("Pass").trim());
                item.setIncomingURL(rs.getString("URL").trim());
                item.setIncomingProtocol(rs.getString("Protocol").trim());
                item.setIncomingPort(143);
                item.setIncomingFolder(rs.getString("Folder").trim());
                item.setOutgoingURL("soccemsmtp.em.ohio.gov");
                item.setOutgoingProtocol("smtp");
                item.setOutgoingPort(25);
                item.setOutgoingFolder(null);
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
        
    public static void addSystemEmail(systemEmailModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SystemEmail ("
                    + "Active, "            //01
                    + "section, "           //02
                    + "emailAddress, "      //03
                    + "username, "          //04
                    + "password, "          //05
                    + "incomingURL, "       //06
                    + "incomingProtocol, "  //07
                    + "incomingPort, "      //08
                    + "incomingFolder, "    //09
                    + "outgoingURL, "       //10
                    + "outgoingProtocol, "  //11
                    + "outgoingPort, "      //12
                    + "outgoingFolder"      //13
                    + ") VALUES ("
                    + "?";              //01
                    for(int i=1; i<13; i++){
                        sql += ", ?";   //02-13
                    }
                     sql += ")";
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getSection());
            ps.setString( 3, item.getEmailAddress());
            ps.setString( 4, item.getUsername());
            ps.setString( 5, item.getPassword());
            ps.setString( 6, item.getIncomingURL());
            ps.setString( 7, item.getIncomingProtocol());
            ps.setInt   ( 8, item.getIncomingPort());
            ps.setString( 9, item.getIncomingFolder());
            ps.setString(10, item.getOutgoingURL());
            ps.setString(11, item.getOutgoingProtocol());
            ps.setInt   (12, item.getOutgoingPort());
            ps.setString(13, item.getOutgoingFolder());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
