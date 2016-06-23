/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.administrationInformationModel;
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
public class sqlAdministrationInformation {
    
    public static List<administrationInformationModel> getOldInfo() {
        List<administrationInformationModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT SystemSerbid AS id, Active, 'SERB' AS Department, "
                    + "GovernorName, LtGovernorName, SerbAddress AS address, "
                    + "SerbCityStateZip AS cityStateZip, SerbURL AS url, "
                    + "SerbPhone AS phone, SerbFax AS fax, SerbFooter AS footer "
                    + "FROM systemSerb UNION ALL SELECT SystemPBRID AS id, "
                    + "Active, 'SPBR' AS Department, GovernorName, LtGovernorName, "
                    + "PBRAddress AS address, PBRCityStateZip AS cityStateZip, "
                    + "PBRURL AS url, PBRPhone AS phone, PBRFax AS fax, PBRFooter AS footer "
                    + "FROM systemPBR";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                administrationInformationModel item = new administrationInformationModel();
                item.setId(rs.getInt("id"));
                item.setActive(rs.getInt("Active"));
                item.setDepartment(rs.getString("Department").trim());
                item.setGovernorName(rs.getString("GovernorName").trim());
                item.setLtGovernorName(rs.getString("LtGovernorName").trim());
                item.setAddress1(rs.getString("address").trim());
                item.setAddress2(null);
                item.setCity(rs.getString("cityStateZip").trim());
                item.setState(null);
                item.setZip(null);
                item.setUrl(rs.getString("url").trim());
                item.setPhone(rs.getString("phone").trim());
                item.setFax(rs.getString("fax").trim());
                item.setFooter(rs.getString("footer").trim());
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
    
    public static void addInfo(administrationInformationModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO AdministrationInformation ("
                    + "active, "        //01
                    + "department, "    //02
                    + "GovernorName, "  //03
                    + "LtGovernorName, "//04
                    + "Address1, "      //05
                    + "Address2, "      //06
                    + "City, "          //07
                    + "State, "         //08
                    + "Zip, "           //09
                    + "Url, "           //10
                    + "Phone, "         //11
                    + "Fax, "           //12
                    + "Footer "         //13
                    + ") VALUES (";
                    for(int i=0; i<12; i++){
                        sql += "?, ";//01-12
                    }
                     sql += "?)";    //013
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getDepartment());
            ps.setString( 3, item.getGovernorName());
            ps.setString( 4, item.getLtGovernorName());
            ps.setString( 5, item.getAddress1());
            ps.setString( 6, item.getAddress2());
            ps.setString( 7, item.getCity());
            ps.setString( 8, item.getState());
            ps.setString( 9, item.getZip());
            ps.setString(10, item.getUrl());
            ps.setString(11, item.getPhone());
            ps.setString(12, item.getFax());
            ps.setString(13, item.getFooter());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
