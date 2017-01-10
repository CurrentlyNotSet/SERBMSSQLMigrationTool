/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSReport;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlCMDSReport {
    
    public static void addSMDSDocument(CMDSReport item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSReport ("
                    + "section, "    //01
                    + "description, "//02
                    + "fileName, "   //03
                    + "parameters, " //04
                    + "active, "     //05
                    + "sortOrder "   //06
                    + ") VALUES (";
            for (int i = 0; i < 05; i++) {
                sql += "?, ";   //01-05
            }
            sql += "?)";   //06
            ps = conn.prepareStatement(sql);
            ps.setString (1, item.getSection());
            ps.setString (2, item.getDescription());
            ps.setString (3, item.getFileName());
            ps.setString (4, item.getParameters());
            ps.setBoolean(5, item.isActive());
            ps.setNull   (6, java.sql.Types.DOUBLE);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
