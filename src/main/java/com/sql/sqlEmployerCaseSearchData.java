/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.employerCaseSearchModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class sqlEmployerCaseSearchData {
    
    public static void addEmployer(employerCaseSearchModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO EmployerCaseSearchData ("
                    + "caseYear, "   //01
                    + "caseType, "   //02
                    + "caseMonth, "  //03
                    + "caseNumber, " //04
                    + "caseStatus, " //05
                    + "fileDate, "   //06
                    + "employer, "   //07
                    + "employerID "  //07
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
            ps.setString(1, StringUtils.left(item.getCaseYear(), 4));             
            ps.setString(2, StringUtils.left(item.getCaseType(), 5));       
            ps.setString(3, StringUtils.left(item.getCaseMonth(), 2));       
            ps.setString(4, StringUtils.left(item.getCaseNumber(), 4));             
            ps.setString(5, StringUtils.left(item.getCaseStatus(), 25));          
            ps.setDate  (6, item.getFileDate());      
            ps.setString(7, item.getEmployer());
            ps.setString(8, item.getEmployerID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
