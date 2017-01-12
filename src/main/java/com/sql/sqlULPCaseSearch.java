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
import org.apache.commons.lang3.StringUtils;

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
                    + ") VALUES (";
                    for(int i=0; i<7; i++){
                        sql += "?, ";   //01-07
                    }
                     sql += "?)";   //08
            ps = conn.prepareStatement(sql);
            ps.setString(1, StringUtils.left(item.getCaseYear(), 4));
            ps.setString(2, StringUtils.left(item.getCaseType(), 5));
            ps.setString(3, StringUtils.left(item.getCaseMonth(), 2));
            ps.setString(4, StringUtils.left(item.getCaseNumber(), 4));
            ps.setString(5, item.getChargingParty());
            ps.setString(6, item.getChargedParty());
            ps.setString(7, StringUtils.left(item.getEmployerNumber(), 8));
            ps.setString(8, StringUtils.left(item.getUnionNumber(), 8));
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
