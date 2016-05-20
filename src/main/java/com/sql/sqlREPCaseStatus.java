/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseStatusModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlREPCaseStatus {
    
    public static List<REPCaseStatusModel> getOldREPCaseStatus() {
        List<REPCaseStatusModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPStatusOptions";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                REPCaseStatusModel item = new REPCaseStatusModel();
                item.setId(rs.getInt("REPStatusID"));
                item.setActive(rs.getInt("Active"));
                item.setStatusType(!"".equals(rs.getString("StatusType").trim()) ? rs.getString("StatusType").trim() : null);
                item.setStatus(!"".equals(rs.getString("Status").trim()) ? rs.getString("Status").trim() : null);
                list.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
        
    public static void addREPCaseStatus(REPCaseStatusModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseStatus ("
                    + "Active, "    //01
                    + "StatusType, "//02
                    + "Status "     //03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getStatusType());
            ps.setString( 3, item.getStatus());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
