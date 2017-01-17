/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.factFinderModel;
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
public class sqlFactFinder {
    
    public static List<factFinderModel> getOldFactFinders() {
        List<factFinderModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM FactFinders";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                factFinderModel item = new factFinderModel();
                item.setId(rs.getInt("FactFindersID"));
                item.setActive(rs.getInt("Active") == 1 ? 1 : 0);
                item.setFirstName(null);
                item.setMiddleName(null);
                item.setLastName(!"".equals(rs.getString("FactFinderName").trim()) ? rs.getString("FactFinderName").trim() : null);
                item.setStatus(!"".equals(rs.getString("Status").trim()) ? rs.getString("Status").trim() : null);
                item.setAddress1(!"".equals(rs.getString("FactFinderAddress1").trim()) ? rs.getString("FactFinderAddress1").trim() : null);
                item.setAddress2(!"".equals(rs.getString("FactFinderAddress2").trim()) ? rs.getString("FactFinderAddress2").trim() : null);
                item.setAddress3(!"".equals(rs.getString("FactFinderAddress3").trim()) ? rs.getString("FactFinderAddress3").trim() : null);
                item.setCity(!"".equals(rs.getString("FactFinderCityStateZip").trim()) ? rs.getString("FactFinderCityStateZip").trim() : null);
                item.setState(null);
                item.setZip(null);
                item.setEmail(!"".equals(rs.getString("Email").trim()) ? rs.getString("Email").trim() : null);
                item.setPhoneNumber(rs.getString("PhoneNumber") != null ? StringUtilities.convertPhoneNumberToString(rs.getString("PhoneNumber")).trim() : null);
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
    
    public static void batchAddFactFinder(List<factFinderModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO FactFinder ("
                    + "active, "    //01
                    + "status, "    //02
                    + "firstName, " //03
                    + "middleName, "//04
                    + "lastName, "  //05
                    + "address1, "  //06
                    + "address2, "  //07
                    + "address3, "  //08
                    + "city, "      //09
                    + "state, "     //10
                    + "zip, "       //11
                    + "email, "     //12
                    + "phone "      //13
                    + ") VALUES (";
                    for(int i=0; i<12; i++){
                        sql += "?, ";   //01-12
                    }
                     sql += "?)";   //13
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (factFinderModel item : list) {
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
                if (item.getCity() != null) {
                    String[] cityStateZipSplit = item.getCity().split(",", 2);
                    item.setCity("".equals(cityStateZipSplit[0]) ? null : cityStateZipSplit[0].trim().replaceAll(",", ""));

                    String[] stateZipSplit = cityStateZipSplit[1].trim().split(" ");
                    item.setState("".equals(stateZipSplit[0]) ? null : stateZipSplit[0].trim().replaceAll("[^A-Za-z]", ""));
                    item.setZip("".equals(stateZipSplit[1]) ? null : stateZipSplit[1].trim().replaceAll(",", ""));
                }

                if ("Ohio".equals(item.getState())) {
                    item.setState("OH");
                } else if ("Kentucky".equals(item.getState())) {
                    item.setState("KY");
                }                
                
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getStatus(), 1));
                ps.setString( 3, StringUtils.left(item.getFirstName(), 100));
                ps.setString( 4, StringUtils.left(item.getMiddleName(), 100));
                ps.setString( 5, StringUtils.left(item.getLastName(), 200));
                ps.setString( 6, StringUtils.left(item.getAddress1(), 200));
                ps.setString( 7, StringUtils.left(item.getAddress2(), 200));
                ps.setString( 8, StringUtils.left(item.getAddress3(), 200));
                ps.setString( 9, StringUtils.left(item.getCity(), 100));
                ps.setString(10, StringUtils.left(item.getState(), 2));
                ps.setString(11, StringUtils.left(item.getZip(), 15));
                ps.setString(12, StringUtils.left(item.getEmail(), 200));
                ps.setString(13, StringUtils.left(item.getPhoneNumber(), 255));
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
    
}
