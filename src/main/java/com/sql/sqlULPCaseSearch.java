/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ULPCaseSearchModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlULPCaseSearch {
    
    public static void addULPCaseSearchCase(ULPCaseSearchModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO ULPCaseSearch("
                    + "caseYear, "      //01
                    + "caseType, "      //02
                    + "caseMonth, "     //03
                    + "caseNumber, "    //04
                    + "ChargingParty, " //05
                    + "ChargedParty, "  //06
                    + "EmployerNumber, "//07
                    + "UnionNumber "    //08
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?,"  //06
                    + "?,"  //07
                    + "?)"; //08
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getCaseYear());
            ps.setString(2, item.getCaseType());
            ps.setString(3, item.getCaseMonth());
            ps.setString(4, item.getCaseNumber());
            ps.setString(5, item.getChargingParty());
            ps.setString(6, item.getChargedParty());
            ps.setString(7, item.getEmployerNumber());
            ps.setString(8, item.getUnionNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
