/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.employerTypeModel;
import com.model.employersModel;
import com.model.oldPartyModel;
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
public class sqlEmployers {
    
    public static void addEmployer(employersModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO Employers ("
                    + "Active, "            //01
                    + "EmployerType, "      //02
                    + "EmployerName, "      //03
                    + "Prefix, "            //04
                    + "FirstName, "         //05
                    + "MiddleInitial, "     //06
                    + "LastName, "          //07
                    + "Suffix, "            //08
                    + "Title, "             //09
                    + "Address1, "          //10
                    + "Address2, "          //11
                    + "Address3, "          //12
                    + "City, "              //13
                    + "State, "             //14
                    + "ZipCode, "           //15
                    + "Phone1, "            //16
                    + "Phone2, "            //17
                    + "Fax, "               //18
                    + "EmailAddress, "      //19
                    + "EmployerIDNumber, "  //20
                    + "EmployerTypeCode, "  //21
                    + "Jurisdiction, "      //22
                    + "Region, "            //23
                    + "AssistantFirstName, "//24
                    + "AssistantMiddleInitial, "//25
                    + "AssistantLastName, " //26
                    + "AssistantEmail, "    //27
                    + "County"              //28
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?,"  //06
                    + "?,"  //07
                    + "?,"  //08
                    + "?,"  //09
                    + "?,"  //10
                    + "?,"  //11
                    + "?,"  //12
                    + "?,"  //13
                    + "?,"  //14
                    + "?,"  //15
                    + "?,"  //16
                    + "?,"  //17
                    + "?,"  //18
                    + "?,"  //19
                    + "?,"  //20
                    + "?,"  //21
                    + "?,"  //22
                    + "?,"  //23
                    + "?,"  //24
                    + "?,"  //25
                    + "?,"  //26
                    + "?,"  //27
                    + "?)"; //28
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());             
            ps.setInt   ( 2, item.getEmployerType());       
            ps.setString( 3, item.getEmployerName());       
            ps.setString( 4, item.getPrefix());             
            ps.setString( 5, item.getFirstName());          
            ps.setString( 6, item.getMiddleInitial());      
            ps.setString( 7, item.getLastName());           
            ps.setString( 8, item.getSuffix());             
            ps.setString( 9, item.getTitle());              
            ps.setString(10, item.getAddress1());           
            ps.setString(11, item.getAddress2());           
            ps.setString(12, item.getAddress3());           
            ps.setString(13, item.getCity());               
            ps.setString(14, item.getState());              
            ps.setString(15, item.getZipCode());            
            ps.setString(16, item.getPhone1());             
            ps.setString(17, item.getPhone2());             
            ps.setString(18, item.getFax());                
            ps.setString(19, item.getEmailAddress());       
            ps.setString(20, item.getEmployerIDNumber());   
            ps.setString(21, item.getEmployerTypeCode());   
            ps.setString(22, item.getJurisdiction());       
            ps.setString(23, item.getRegion());         
            ps.setString(24, item.getAssistantFirstName());
            ps.setString(25, item.getAssistantMiddleInitial());
            ps.setString(26, item.getAssistantLastName());
            ps.setString(27, item.getAssistantEmail());
            ps.setString(28, item.getCounty());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static List<employerTypeModel> getEmployerType() {
        List<employerTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM EmployerType";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                employerTypeModel item = new employerTypeModel();
                item.setId(rs.getInt("id"));
                item.setType(rs.getString("type"));
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
    
    public static List<oldPartyModel> getOldEmployers() {
        List<oldPartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM party";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldPartyModel item = new oldPartyModel();
                item.setPartyid(rs.getInt("Partyid"));
                item.setActive(rs.getInt("Active"));
                item.setPartyType(rs.getString("PartyType"));
                item.setDisplayName(rs.getString("DisplayName"));
                item.setBusinessName(rs.getString("BusinessName"));
                item.setTitle(rs.getString("Title"));
                item.setPrefix(rs.getString("Prefix"));
                item.setFirstName(rs.getString("FirstName"));
                item.setMiddleInitial(rs.getString("MiddleInitial"));
                item.setLastName(rs.getString("LastName"));
                item.setSuffix(rs.getString("Suffix"));
                item.setAddress1(rs.getString("Address1"));
                item.setAddress2(rs.getString("Address2"));
                item.setCity(rs.getString("City"));
                item.setCounty(rs.getString("County"));
                item.setState(rs.getString("State"));
                item.setZipPlusFive(rs.getString("ZipPlusFive"));
                item.setWorkPhone(rs.getString("WorkPhone"));
                item.setCellPhone(rs.getString("CellPhone"));
                item.setFax(rs.getString("Fax"));
                item.setEMail(rs.getString("EMail"));
                item.setAssistantFirstName(rs.getString("AssistantFirstName"));
                item.setAssistantMiddleInitial(rs.getString("AssistantMiddleInitial"));
                item.setAssistantLastName(rs.getString("AssistantLastName"));
                item.setAssistantEMail(rs.getString("AssistantEMail"));
                item.setPartyField1(rs.getString("PartyField1"));
                item.setPartyField2(rs.getString("PartyField2"));
                item.setPartyField3(rs.getString("PartyField3"));
                item.setPartyField4(rs.getString("PartyField4"));
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
