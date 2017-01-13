/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.administrationInformationModel;
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
    
    public static void addInfo(List<administrationInformationModel> list) {
        int count = 0;
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
            conn.setAutoCommit(false);

            for (administrationInformationModel item : list) {
                String[] cityStateZipSplit = item.getCity().replaceAll("  ", " ").split(" ");
                
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, item.getDepartment());
                ps.setString( 3, "".equals(item.getGovernorName()) ? null : item.getGovernorName());
                ps.setString( 4, "".equals(item.getLtGovernorName()) ? null : item.getLtGovernorName());
                ps.setString( 5, "".equals(item.getAddress1()) ? null : item.getAddress1());
                ps.setString( 6, item.getAddress2());
                ps.setString( 7, "".equals(cityStateZipSplit[0]) ? null : cityStateZipSplit[0].trim());
                ps.setString( 8, "".equals(cityStateZipSplit[1]) ? null : cityStateZipSplit[1].trim());
                ps.setString( 9, "".equals(cityStateZipSplit[2]) ? null : cityStateZipSplit[2].trim());
                ps.setString(10, "".equals(item.getUrl()) ? null : item.getUrl());
                ps.setString(11, "".equals(item.getPhone().replaceAll("[^0-9]", "")) ? null : item.getPhone().replaceAll("[^0-9]", ""));
                ps.setString(12, "".equals(item.getFax().replaceAll("[^0-9]", "")) ? null : item.getFax().replaceAll("[^0-9]", ""));
                ps.setString(13, "".equals(item.getFooter()) ? null : item.getFooter());
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
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
