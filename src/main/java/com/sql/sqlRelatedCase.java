/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.relatedCaseModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlRelatedCase {
    
    public static void addRelatedCase(relatedCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO relatedCase("
                    + "caseYear, "   //01
                    + "caseType, "   //02
                    + "caseMonth, "  //03
                    + "caseNumber, " //04
                    + "relatedCase " //05
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?)"; //05
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getCaseYear());
            ps.setString(2, item.getCaseType());
            ps.setString(3, item.getCaseMonth());
            ps.setString(4, item.getCaseNumber());
            ps.setString(5, item.getRelatedCaseNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
