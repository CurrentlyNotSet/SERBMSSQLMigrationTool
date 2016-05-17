/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPMediationModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlREPMediation {
    
    public static void addREPMediation(REPMediationModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPMediation("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "mediationEntryDate, "//05
                    + "mediationDate, "     //06
                    + "mediationType, "     //07
                    + "mediatorID, "        //08
                    + "mediationOutcome "   //09
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?,"  //06
                    + "?,"  //07
                    + "?,"  //08
                    + "?)"; //09
            ps = conn.prepareStatement(sql);
            ps.setString   (1, item.getCaseYear());
            ps.setString   (2, item.getCaseType());
            ps.setString   (3, item.getCaseMonth());
            ps.setString   (4, item.getCaseNumber());
            ps.setDate     (5, item.getMediationEntryDate());
            ps.setTimestamp(6, item.getMediationDate());
            ps.setString   (7, item.getMediationType());
            ps.setString   (8, item.getMediatorID());
            ps.setString   (9, item.getMediationOutcome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
