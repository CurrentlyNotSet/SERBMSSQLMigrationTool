/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.employerTypeModel;
import com.model.employersModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
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
 * @author Andrew
 */
public class sqlEmployers {
        
    public static void batchAddEmployerType(List<String> list) {
        int count = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO EmployerType (Type) VALUES (?)";
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (String item : list) {
                ps.setString(1, StringUtils.left(item, 50));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                SlackNotification.sendNotification(ex1);
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
        
    public static void batchAddEmployer(List<employersModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
                    + "AssistantFirstName, "    //24
                    + "AssistantMiddleInitial, "//25
                    + "AssistantLastName, "     //26
                    + "AssistantEmail, "        //27
                    + "County,"                 //28
                    + "population, "            //29
                    + "employerIRN "            //30
                    + ") VALUES (";
                    for(int i=0; i<29; i++){
                        sql += "?, ";   //01-29
                    }
                     sql += "?)"; //30
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (employersModel item : list) {
                ps.setInt   ( 1, item.getActive());             
                ps.setInt   ( 2, item.getEmployerType());       
                ps.setString( 3, StringUtils.left(item.getEmployerName(), 255));       
                ps.setString( 4, StringUtils.left(item.getPrefix(), 25));             
                ps.setString( 5, StringUtils.left(item.getFirstName(), 100));          
                ps.setString( 6, StringUtils.left(item.getMiddleInitial(), 1));      
                ps.setString( 7, StringUtils.left(item.getLastName(), 100));           
                ps.setString( 8, StringUtils.left(item.getSuffix(), 50));             
                ps.setString( 9, StringUtils.left(item.getTitle(), 255));              
                ps.setString(10, StringUtils.left(item.getAddress1(), 255));           
                ps.setString(11, StringUtils.left(item.getAddress2(), 255));           
                ps.setString(12, StringUtils.left(item.getAddress3(), 255));           
                ps.setString(13, StringUtils.left(item.getCity(), 255));               
                ps.setString(14, StringUtils.left(item.getState(), 2));              
                ps.setString(15, StringUtils.left(item.getZipCode(), 15));            
                ps.setString(16, StringUtils.left(item.getPhone1(), 25));             
                ps.setString(17, StringUtils.left(item.getPhone2(), 25));             
                ps.setString(18, StringUtils.left(item.getFax(), 25));                
                ps.setString(19, StringUtils.left(item.getEmailAddress(), 200));       
                ps.setString(20, StringUtils.left(item.getEmployerIDNumber(), 4));   
                ps.setString(21, StringUtils.left(item.getEmployerTypeCode(), 2));   
                ps.setString(22, StringUtils.left(item.getJurisdiction(), 2));       
                ps.setString(23, StringUtils.left(item.getRegion(), 2));         
                ps.setString(24, StringUtils.left(item.getAssistantFirstName(), 100));
                ps.setString(25, StringUtils.left(item.getAssistantMiddleInitial(), 1));
                ps.setString(26, StringUtils.left(item.getAssistantLastName(), 100));
                ps.setString(27, StringUtils.left(item.getAssistantEmail(), 255));
                ps.setString(28, StringUtils.left(item.getCounty(), 100));
                ps.setString(29, StringUtils.left(item.getPopulation(), 50));
                ps.setString(30, StringUtils.left(item.getEmployerIRN(), 10));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                SlackNotification.sendNotification(ex1);
            }
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
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
    public static List<employersModel> getOldEmployers(List<employerTypeModel> typeList) {
        List<employersModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT party.*, employers.population, employers.employerIRN "
                    + "FROM party LEFT JOIN employers ON  party.DisplayName = employers.EmployerName ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int typeID = 0;
                for (employerTypeModel type : typeList){
                    if (type.getType().equals(rs.getString("PartyType"))){
                        typeID = type.getId();
                        break;
                    }
                }  
                
                employersModel item = new employersModel();
                item.setId(rs.getInt("Partyid"));
                item.setActive(rs.getInt("Active"));
                item.setEmployerType(typeID);
                item.setEmployerName(rs.getString("BusinessName").trim().equals("") ? null : rs.getString("BusinessName").trim());
                item.setTitle(rs.getString("Title").trim().equals("") ? null : rs.getString("Title").trim());
                item.setPrefix(rs.getString("Prefix").trim().equals("") ? null : rs.getString("Prefix").trim());
                item.setFirstName(rs.getString("FirstName").trim().equals("") ? null : rs.getString("FirstName").trim());
                item.setMiddleInitial(rs.getString("MiddleInitial").trim().equals("") ? null : rs.getString("MiddleInitial").trim());
                item.setLastName(rs.getString("LastName").trim().equals("") ? null : rs.getString("LastName").trim());
                item.setSuffix(rs.getString("Suffix").trim().equals("") ? null : rs.getString("Suffix").trim());
                item.setAddress1(rs.getString("Address1").trim().equals("") ? null : rs.getString("Address1").trim());
                item.setAddress2(rs.getString("Address2").trim().equals("") ? null : rs.getString("Address2").trim());
                item.setCity(rs.getString("City").trim().equals("") ? null : rs.getString("City").trim());
                item.setCounty(rs.getString("County").trim().equals("") ? null : rs.getString("County").trim());
                item.setState(rs.getString("State").trim().equals("") ? null : rs.getString("State").trim());
                item.setZipCode(rs.getString("ZipPlusFive").trim().equals("") ? null : rs.getString("ZipPlusFive").trim());
                item.setPhone1(!"".equals(StringUtilities.convertPhoneNumberToString(rs.getString("WorkPhone").trim())) ? StringUtilities.convertPhoneNumberToString(rs.getString("WorkPhone").trim()) : null);
                item.setPhone2(!"".equals(StringUtilities.convertPhoneNumberToString(rs.getString("CellPhone").trim())) ? StringUtilities.convertPhoneNumberToString(rs.getString("CellPhone").trim()) : null);
                item.setFax(rs.getString("Fax").trim().equals("") ? null : rs.getString("Fax").trim());
                item.setEmailAddress(rs.getString("EMail").trim().equals("") ? null : rs.getString("EMail").trim());
                item.setAssistantFirstName(rs.getString("AssistantFirstName").trim().equals("") ? null : rs.getString("AssistantFirstName").trim());
                item.setAssistantMiddleInitial(rs.getString("AssistantMiddleInitial").trim().equals("") ? null : rs.getString("AssistantMiddleInitial").trim());
                item.setAssistantLastName(rs.getString("AssistantLastName").trim().equals("") ? null : rs.getString("AssistantLastName").trim());
                item.setAssistantEmail(rs.getString("AssistantEMail").trim().equals("") ? null : rs.getString("AssistantEMail").trim());
                item.setEmployerIDNumber(rs.getString("PartyField1").trim().equals("") ? null : rs.getString("PartyField1").trim());
                item.setEmployerTypeCode(rs.getString("PartyField2").trim().equals("") ? null : rs.getString("PartyField2").trim());
                item.setJurisdiction(rs.getString("PartyField3").trim().equals("") ? null : rs.getString("PartyField3").trim());
                item.setRegion(rs.getString("PartyField4").trim().equals("") ? null : rs.getString("PartyField4").trim());
                item.setPopulation(rs.getString("population") == null ? "" : rs.getString("population"));
                item.setEmployerIRN(rs.getString("EmployerIRN") == null ? "" : rs.getString("EmployerIRN"));
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
    public static String getEmployerName(String employerID) {
        String name = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT EmployerName FROM Employers WHERE EmployerIDNumber = ?";
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, employerID);
            rs = ps.executeQuery();
            if (rs.first()) {
                name = rs.getString("EmployerName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(rs);
        }
        return name;
    }
    
    public static List<employersModel> getEmployersForReference() {
        List<employersModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT Employers.EmployerID, Employers.EmployerName, Party.PartyField1 "
                    + "FROM Employers "
                    + "LEFT OUTER JOIN Party "
                    + "ON Employers.EmployerName = Party.DisplayName "
                    + "WHERE Party.PartyType = 'Employer' "
                    + "ORDER BY Employers.EmployerID";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                employersModel item = new employersModel();
                item.setId(rs.getInt("EmployerID"));
                item.setEmployerName(rs.getString("EmployerName"));
                item.setEmployerIDNumber(rs.getString("PartyField1"));
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
}
