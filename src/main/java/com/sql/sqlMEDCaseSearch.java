/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.MEDCaseSearchModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlMEDCaseSearch {
    
    public static void addMEDCaseSearchCase(MEDCaseSearchModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO MEDCaseSearch("
                    + "caseYear, "    //01
                    + "caseType, "    //02
                    + "caseMonth, "   //03
                    + "caseNumber, "  //04
                    + "employerName, "//05
                    + "unionName, "   //06
                    + "county, "      //07
                    + "employerID, "  //08
                    + "bunNumber "    //09
                    + ") VALUES (";
                    for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                     sql += "?)";  //09
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getCaseYear());
            ps.setString( 2, item.getCaseType());
            ps.setString( 3, item.getCaseMonth());
            ps.setString( 4, item.getCaseNumber());
            ps.setString( 5, item.getEmployerName());
            ps.setString( 6, item.getUnionName());
            ps.setString( 7, item.getCounty());
            ps.setString( 8, item.getEmployerID());
            ps.setString( 9, item.getBunNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
