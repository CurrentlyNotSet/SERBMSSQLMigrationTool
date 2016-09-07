/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.casePartyModel;
import com.util.ContactNameSeperator;
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
public class sqlCaseParty {
    
    public static void savePartyInformation(casePartyModel item){
        item = ContactNameSeperator.seperateName(item);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO CaseParty ("
                    + "caseYear, "      //01
                    + "caseType, "      //02
                    + "caseMonth, "     //03
                    + "caseNumber, "    //04
                    + "partyID, "       //05
                    + "caseRelation, "  //06
                    + "prefix, "        //07
                    + "firstName, "     //08
                    + "middleInitial, " //09
                    + "lastName, "      //10
                    + "suffix, "        //11
                    + "nameTitle, "     //12
                    + "jobTitle, "      //13
                    + "companyName, "   //14
                    + "address1, "      //15
                    + "address2, "      //16
                    + "address3, "      //17
                    + "city, "          //18
                    + "stateCode, "     //19
                    + "zipCode, "       //20
                    + "phone1, "        //21
                    + "phone2, "        //22
                    + "email, "         //23
                    + "fax "            //24
                    + ") VALUES (";
                    for(int i=0; i<23; i++){
                        sql += "?, ";   //01-23
                    }
                     sql += "?)"; //24
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getCaseYear());
            ps.setString( 2, item.getCaseType());
            ps.setString( 3, item.getCaseMonth());
            ps.setString( 4, item.getCaseNumber());
            if (item.getPartyID() != 0){
                ps.setInt  ( 5, item.getPartyID());
            } else {
                ps.setNull ( 5, java.sql.Types.INTEGER);
            }
            ps.setString( 6, item.getCaseRelation());
            ps.setString( 7, item.getPrefix());
            ps.setString( 8, item.getFirstName());
            ps.setString( 9, item.getMiddleInitial());
            ps.setString(10, item.getLastName());
            ps.setString(11, item.getSuffix());
            ps.setString(12, item.getNameTitle());
            ps.setString(13, item.getJobTitle());
            ps.setString(14, item.getCompanyName());
            ps.setString(15, item.getAddress1());
            ps.setString(16, item.getAddress2());
            ps.setString(17, item.getAddress3());
            ps.setString(18, item.getCity());
            ps.setString(19, item.getState());
            ps.setString(20, item.getZip());
            ps.setString(21, item.getPhoneOne());
            ps.setString(22, item.getPhoneTwo());
            ps.setString(23, item.getEmailAddress());
            ps.setString(24, item.getFax());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static List<casePartyModel> getCasePartyFromParties(String caseYear, String caseType, String caseMonth, String caseNumber) {
        List<casePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM CaseParty WHERE "
                    + "caseYear = ? AND caseType = ? AND caseMonth = ? AND caseNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, caseYear);
            ps.setString(2, caseType);
            ps.setString(3, caseMonth);
            ps.setString(4, caseNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                casePartyModel item = new casePartyModel();          
                item.setId(rs.getInt("id"));
                item.setCaseYear(rs.getString("caseYear"));
                item.setCaseType(rs.getString("caseType"));
                item.setCaseMonth(rs.getString("caseMonth"));
                item.setCaseNumber(rs.getString("caseNumber"));
                item.setPartyID(rs.getInt("partyID"));
                item.setCaseRelation(rs.getString("caseRelation"));
                item.setPrefix(rs.getString("prefix"));
                item.setFirstName(rs.getString("firstName"));
                item.setMiddleInitial(rs.getString("middleInitial"));
                item.setLastName(rs.getString("lastName"));
                item.setSuffix(rs.getString("suffix"));
                item.setNameTitle(rs.getString("nameTitle"));
                item.setJobTitle(rs.getString("jobTitle"));
                item.setCompanyName(rs.getString("companyName"));
                item.setAddress1(rs.getString("address1"));
                item.setAddress2(rs.getString("address2"));
                item.setAddress3(rs.getString("address3"));
                item.setCity(rs.getString("city"));
                item.setState(rs.getString("stateCode"));
                item.setZip(rs.getString("zipCode"));
                item.setPhoneOne(rs.getString("phone1"));
                item.setEmailAddress(rs.getString("email"));
                item.setPhoneTwo(rs.getString("phone2"));   
                item.setFax(rs.getString("fax"));   
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
