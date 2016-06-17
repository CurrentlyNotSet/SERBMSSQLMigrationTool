/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPElectionSiteInformationModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlREPElectionSiteInformation {
    
    public static void addElectionSite(REPElectionSiteInformationModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPElectionSiteInformation ("
                    + "active, "      //01
                    + "caseYear, "    //02
                    + "caseType, "    //03
                    + "caseMonth, "   //04
                    + "caseNumber, "  //05
                    + "siteDate, "    //06
                    + "siteTime, "    //07
                    + "sitePlace, "   //08
                    + "siteAddress1, "//09
                    + "siteAddress2, "//10
                    + "siteLocation " //11
                    + ") VALUES (";
                    for(int i=0; i<10; i++){
                        sql += "?, ";   //01-10
                    }
                     sql += "?)";   //11
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getCaseYear());
            ps.setString( 3, item.getCaseType());
            ps.setString( 4, item.getCaseMonth());
            ps.setString( 5, item.getCaseNumber());
            ps.setDate  ( 6, item.getSiteDate());
            ps.setTime  ( 7, item.getSiteTime());
            ps.setString( 8, item.getSitePlace());
            ps.setString( 9, item.getSiteAddress1());
            ps.setString(10, item.getSiteAddress2());
            ps.setString(11, item.getSiteLocation());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
