/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPElectionMultiCaseModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlREPElectionMultiCase {
    
    public static void addElectionSite(REPElectionMultiCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPElectionMultiCase ("
                    + "active, "      //01
                    + "caseYear, "    //02
                    + "caseType, "    //03
                    + "caseMonth, "   //04
                    + "caseNumber, "  //05
                    + "multicase "    //06
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?)"; //06
            ps = conn.prepareStatement(sql);
            ps.setInt   (1, item.getActive());
            ps.setString(2, item.getCaseYear());
            ps.setString(3, item.getCaseType());
            ps.setString(4, item.getCaseMonth());
            ps.setString(5, item.getCaseNumber());
            ps.setString(6, item.getMultiCase());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddElectionSite(List<REPElectionMultiCaseModel> list) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPElectionMultiCase ("
                    + "active, "      //01
                    + "caseYear, "    //02
                    + "caseType, "    //03
                    + "caseMonth, "   //04
                    + "caseNumber, "  //05
                    + "multicase "    //06
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?)"; //06
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPElectionMultiCaseModel item : list) {
                ps.setInt   (1, item.getActive());
                ps.setString(2, item.getCaseYear());
                ps.setString(3, item.getCaseType());
                ps.setString(4, item.getCaseMonth());
                ps.setString(5, item.getCaseNumber());
                ps.setString(6, item.getMultiCase());
                ps.addBatch();
            }            
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
