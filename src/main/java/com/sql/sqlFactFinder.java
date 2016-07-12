/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.factFinderModel;
import com.util.DBCInfo;
import com.util.StringUtilities;
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
    
    
    public static void addFactFinder(factFinderModel item) {
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
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getStatus());
            ps.setString( 3, item.getFirstName());
            ps.setString( 4, item.getMiddleName());
            ps.setString( 5, item.getLastName());
            ps.setString( 6, item.getAddress1());
            ps.setString( 7, item.getAddress2());
            ps.setString( 8, item.getAddress3());
            ps.setString( 9, item.getCity());
            ps.setString(10, item.getState());
            ps.setString(11, item.getZip());
            ps.setString(12, item.getEmail());
            ps.setString(13, item.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
