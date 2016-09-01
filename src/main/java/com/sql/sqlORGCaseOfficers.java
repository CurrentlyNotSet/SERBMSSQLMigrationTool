/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ORGCaseOfficersModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlORGCaseOfficers {
    
    public static void addOfficer(ORGCaseOfficersModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO ORGCaseOfficer ("
                    + "active, "    //01
                    + "orgNumber, " //02
                    + "position, "  //03
                    + "firstName, " //04
                    + "middleName, "//05
                    + "lastName "   //06
                    + ") VALUES (";
                    for(int i=0; i<5; i++){
                        sql += "?, ";   //01-05
                    }
                     sql += "?)";   //06
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setString (2, item.getOrgNumber());
            ps.setString (3, item.getPosition());
            ps.setString (4, item.getFirstName());
            ps.setString (5, item.getMiddleName());
            ps.setString (6, item.getLastName());            
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
