/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.repCaseSearchModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlREPCaseSearch {
    
    public static void addREPCaseSearchCase(repCaseSearchModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseSearch("
                    + "caseYear, "      //01
                    + "caseType, "      //02
                    + "caseMonth, "     //03
                    + "caseNumber, "    //04
                    + "employerName, "  //05
                    + "bunNumber, "     //06
                    + "description, "   //07
                    + "county, "        //08
                    + "boardDeemed, "   //09
                    + "employeeOrg, "   //10
                    + "incumbent "      //11
                    + ") VALUES ("
                    + "?";              //01
                    for(int i=1; i<11; i++){
                        sql += ", ?";   //02-11
                    }
                     sql += ")";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getCaseYear());
            ps.setString( 2, item.getCaseType());
            ps.setString( 3, item.getCaseMonth());
            ps.setString( 4, item.getCaseNumber());
            ps.setString( 5, item.getEmployerName());
            ps.setString( 6, item.getBunNumber());
            ps.setString( 7, item.getDescription());
            ps.setString( 8, item.getCounty());
            ps.setString( 9, item.getBoardDeemed());
            ps.setString(10, item.getEmployeeOrg());
            ps.setString(11, item.getIncumbent());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
