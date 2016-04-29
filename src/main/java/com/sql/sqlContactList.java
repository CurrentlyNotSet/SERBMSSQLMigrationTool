/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyModel;
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
 * @author Andrew
 */
public class sqlContactList {
    
    public static List<partyModel> getMasterList() {
        List<partyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                partyModel item = new partyModel();                
                item.setPrefix("");
                item.setFirstName("");
                item.setMiddleInitial("");
                item.setLastName(((rs.getString("Name") == null) ? "" : rs.getString("Name").trim()));
                item.setSuffix("");
                item.setNameTitle("");
                item.setJobTitle("");
                item.setCompanyName("");
                item.setAddress1(((rs.getString("Address1") == null) ? "" : rs.getString("Address1").trim()));
                item.setAddress2(((rs.getString("Address2") == null) ? "" : rs.getString("Address2").trim()));
                item.setAddress3("");
                item.setCity(((rs.getString("City") == null) ? "" : rs.getString("City").trim()));
                item.setState(((rs.getString("State") == null) ? "" : rs.getString("State").trim()));
                item.setZip(((rs.getString("Zip") == null) ? "" : rs.getString("Zip").trim()));
                item.setPhoneOne(((rs.getString("Phone1") == null) ? "" : StringUtilities.convertPhoneNumberToString(rs.getString("Phone1"))));  
                item.setPhoneTwo(((rs.getString("Phone2") == null) ? "" : StringUtilities.convertPhoneNumberToString(rs.getString("Phone2"))));
                item.setEmailAddress(((rs.getString("Email") == null) ? "" : rs.getString("Email").trim()));
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
        
    public static void savePartyInformation(partyModel item){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO Party ("
                    + "prefix, "        //01
                    + "firstName, "     //02
                    + "middleInitial, " //03
                    + "lastName, "      //04
                    + "suffix, "        //05
                    + "nameTitle, "     //06
                    + "jobTitle, "      //07
                    + "companyName, "   //08
                    + "address1, "      //09
                    + "address2, "      //10
                    + "address3, "      //11
                    + "city, "          //12
                    + "stateCode, "     //13
                    + "zipCode, "       //14
                    + "phone1, "        //15
                    + "phone2, "        //16
                    + "emailAddress  "  //17
                    + ") VALUES ("
                    + "?, " //01 prefix
                    + "?, " //02 firstName
                    + "?, " //03 middleInital
                    + "?, " //04 lastName
                    + "?, " //05 suffix
                    + "?, " //06 nameTitle
                    + "?, " //07 jobTitle
                    + "?, " //08 companyName
                    + "?, " //09 address1
                    + "?, " //10 address2
                    + "?, " //11 address3
                    + "?, " //12 city
                    + "?, " //13 stateCode
                    + "?, " //14 zipCode
                    + "?, " //15 phone1
                    + "?, " //16 phone2
                    + "?) ";//17 emailAddress
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getPrefix());
            ps.setString( 2, item.getFirstName());
            ps.setString( 3, item.getMiddleInitial());
            ps.setString( 4, item.getLastName());
            ps.setString( 5, item.getSuffix());
            ps.setString( 6, item.getNameTitle());
            ps.setString( 7, item.getJobTitle());
            ps.setString( 8, item.getCompanyName());
            ps.setString( 9, item.getAddress1());
            ps.setString(10, item.getAddress2());
            ps.setString(11, item.getAddress3());
            ps.setString(12, item.getCity());
            ps.setString(13, item.getState());
            ps.setString(14, item.getZip());
            ps.setString(15, item.getPhoneOne());
            ps.setString(16, item.getPhoneTwo());
            ps.setString(17, item.getEmailAddress());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
