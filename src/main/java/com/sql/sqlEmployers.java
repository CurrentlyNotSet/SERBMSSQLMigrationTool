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
                ps.setString(1, item);
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
                ps.setString(29, item.getPopulation());
                ps.setString(30, item.getEmployerIRN());
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
                item.setEmployerName(rs.getString("BusinessName"));
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
                item.setZipCode(rs.getString("ZipPlusFive"));
                item.setPhone1(!"".equals(StringUtilities.convertPhoneNumberToString(rs.getString("WorkPhone").trim())) ? StringUtilities.convertPhoneNumberToString(rs.getString("WorkPhone").trim()) : null);
                item.setPhone2(!"".equals(StringUtilities.convertPhoneNumberToString(rs.getString("CellPhone").trim())) ? StringUtilities.convertPhoneNumberToString(rs.getString("CellPhone").trim()) : null);
                item.setFax(rs.getString("Fax"));
                item.setEmailAddress(rs.getString("EMail"));
                item.setAssistantFirstName(rs.getString("AssistantFirstName"));
                item.setAssistantMiddleInitial(rs.getString("AssistantMiddleInitial"));
                item.setAssistantLastName(rs.getString("AssistantLastName"));
                item.setAssistantEmail(rs.getString("AssistantEMail"));
                item.setEmployerIDNumber(rs.getString("PartyField1"));
                item.setEmployerTypeCode(rs.getString("PartyField2"));
                item.setJurisdiction(rs.getString("PartyField3"));
                item.setRegion(rs.getString("PartyField4"));
                item.setPopulation(rs.getString("population") == null ? "" : rs.getString("population"));
                item.setEmployerIRN(rs.getString("EmployerIRN") == null ? "" : rs.getString("EmployerIRN"));
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
}
