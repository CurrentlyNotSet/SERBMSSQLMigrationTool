/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.systemEmailModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
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
                item.setIncomingFolder(rs.getString("Folder").trim().equals("") ? null : rs.getString("Folder").trim());
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
        
    public static void batchAddSystemEmail(List<systemEmailModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
                    + ") VALUES (";
                    for(int i=0; i<12; i++){
                        sql += "?, ";   //01-12
                    }
                     sql += "?)";   //13
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (systemEmailModel item : list) {
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getSection(), 8));
                ps.setString( 3, StringUtils.left(item.getEmailAddress(), 150));
                ps.setString( 4, StringUtils.left(item.getUsername(), 150));
                ps.setString( 5, StringUtils.left(item.getPassword(), 32));
                ps.setString( 6, StringUtils.left(item.getIncomingURL(), 150));
                ps.setString( 7, StringUtils.left(item.getIncomingProtocol(), 25));
                ps.setInt   ( 8, item.getIncomingPort());
                ps.setString( 9, StringUtils.left(item.getIncomingFolder(), 64));
                ps.setString(10, StringUtils.left(item.getOutgoingURL(), 150));
                ps.setString(11, StringUtils.left(item.getOutgoingProtocol(), 25));
                ps.setInt   (12, item.getOutgoingPort());
                ps.setString(13, StringUtils.left(item.getOutgoingFolder(), 64));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
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
