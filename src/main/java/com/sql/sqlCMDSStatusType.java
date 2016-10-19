/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSStatusType;
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
public class sqlCMDSStatusType {
    
    public static List<CMDSStatusType> getOldCMDSStatusType() {
        List<CMDSStatusType> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM StatusTypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSStatusType item = new CMDSStatusType();
                item.setID(rs.getInt("StatusTypesID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setStatusCode(rs.getString("Status"));
                item.setDescription(rs.getString("Description"));               
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
        
    public static void addCMDSStatusType(CMDSStatusType item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSStatusType ("
                    + "active, "    //01
                    + "StatusCode, "//02
                    + "Description "//03
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)"; //03
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getStatusCode());
            ps.setString (3, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
