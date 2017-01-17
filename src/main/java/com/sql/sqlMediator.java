/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.mediatorsModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
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
public class sqlMediator {
    
    public static List<mediatorsModel> getOldMediator() {
        List<mediatorsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM medmediators";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mediatorsModel item = new mediatorsModel();
                item.setID(rs.getInt("MedMediatorsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setFirstName(null);
                item.setMiddleName(null);
                item.setLastName(!"".equals(rs.getString("Name").trim()) ? rs.getString("Name").trim() : null);
                item.setType(!"".equals(rs.getString("Type").trim()) ? rs.getString("Type").trim() : null);
                item.setEmail((rs.getString("Email") == null || "".equals(rs.getString("Email").trim())) ? null: rs.getString("Email").trim());
                item.setPhone(rs.getString("Phone") != null ? StringUtilities.convertPhoneNumberToString(rs.getString("Phone")).trim() : null);
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
    
    public static void batchAddMediator(List<mediatorsModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Mediator ("
                    + "active, "    //01
                    + "type, "      //02
                    + "firstName, " //03
                    + "middleName, "//04
                    + "lastName, "  //05
                    + "phone, "     //06
                    + "email "      //07
                    + ") VALUES (";
                    for(int i=0; i<6; i++){
                        sql += "?, ";   //01-06
                    }
                     sql += "?)";   //07
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (mediatorsModel item : list) {       
                String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");

                switch (nameSplit.length) {
                    case 2:
                        item.setFirstName(nameSplit[0].trim());
                        item.setMiddleName(null);
                        item.setLastName(nameSplit[1].trim());
                        break;
                    case 3:
                        item.setFirstName(nameSplit[0].trim());
                        item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                        item.setLastName(nameSplit[2].trim());
                        break;
                    case 4:
                        item.setFirstName(nameSplit[0].trim());
                        item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                        item.setLastName(nameSplit[2].trim() + ", " + nameSplit[3].trim());
                        break;
                    default:
                        break;
                }
                
                ps.setBoolean(1, item.isActive());
                ps.setString (2, StringUtils.left(item.getType(), 5));
                ps.setString (3, StringUtils.left(item.getFirstName(), 100));
                ps.setString (4, StringUtils.left(item.getMiddleName(), 100));
                ps.setString (5, StringUtils.left(item.getLastName(), 100));
                ps.setString (6, StringUtils.left(item.getPhone(), 20));
                ps.setString (7, StringUtils.left(item.getEmail(), 200));
                ps.addBatch();
                    if (++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
                        currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
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
    
    public static List<mediatorsModel> getNewMediator() {
        List<mediatorsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM Mediator";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mediatorsModel item = new mediatorsModel();                
                item.setID(rs.getInt("id"));
                item.setActive(rs.getInt("Active") == 1);
                item.setType(rs.getString("Type"));
                item.setFirstName(rs.getString("FirstName") != null ? rs.getString("FirstName").trim() : null);
                item.setMiddleName(rs.getString("MiddleName") != null ? rs.getString("MiddleName").trim() : null);
                item.setLastName(rs.getString("LastName") != null ? rs.getString("LastName").trim() : null);
                item.setPhone(rs.getString("Phone") != null ? rs.getString("Phone") : null);
                item.setEmail(rs.getString("Email") != null ? rs.getString("Email") : null);
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
}
