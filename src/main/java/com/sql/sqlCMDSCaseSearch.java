/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSCaseSearchModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlCMDSCaseSearch {
    
    public static void addRow(CMDSCaseSearchModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSCaseSearch ("
                    + "caseYear, "  //01
                    + "caseType, "  //02
                    + "caseMonth, " //03
                    + "caseNumber, "//04
                    + "appellant, " //05
                    + "appellee, "  //06
                    + "alj, "       //07
                    + "dateOpened " //08
                    + ") VALUES (";
                    for(int i=0; i<7; i++){
                        sql += "?, ";   //01-07
                    }
                     sql += "?)"; //08
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getCaseYear());
            ps.setString(2, item.getCaseType());
            ps.setString(3, item.getCaseMonth());
            ps.setString(4, item.getCaseNumber());
            ps.setString(5, item.getAppellant());
            ps.setString(6, item.getAppellee());
            ps.setString(7, item.getAlj());
            ps.setDate  (8, item.getDateOpened());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
}
