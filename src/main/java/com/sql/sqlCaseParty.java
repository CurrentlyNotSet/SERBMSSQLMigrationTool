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
import java.sql.SQLException;
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
                    + "email  "         //23
                    + ") VALUES (";
                    for(int i=0; i<22; i++){
                        sql += "?, ";   //01-22
                    }
                     sql += "?)"; //23
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
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
    
    
}
