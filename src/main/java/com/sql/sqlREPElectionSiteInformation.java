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
                    + "active, "        //01
                    + "caseYear, "      //02
                    + "caseType, "      //03
                    + "caseMonth, "     //04
                    + "caseNumber, "    //05
                    + "siteDate, "      //06
                    + "siteStartTime, " //07
                    + "siteEndTime, "   //08
                    + "sitePlace, "     //09
                    + "siteAddress1, "  //10
                    + "siteAddress2, "  //11
                    + "siteLocation "   //12
                    + ") VALUES (";
                    for(int i=0; i<11; i++){
                        sql += "?, ";   //01-11
                    }
                     sql += "?)";   //12
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getCaseYear());
            ps.setString( 3, item.getCaseType());
            ps.setString( 4, item.getCaseMonth());
            ps.setString( 5, item.getCaseNumber());
            ps.setDate  ( 6, item.getSiteDate());
            ps.setTime  ( 7, item.getSiteStartTime());
            ps.setTime  ( 8, item.getSiteEndTime());
            ps.setString( 9, item.getSitePlace());
            ps.setString(10, item.getSiteAddress1());
            ps.setString(11, item.getSiteAddress2());
            ps.setString(12, item.getSiteLocation());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
