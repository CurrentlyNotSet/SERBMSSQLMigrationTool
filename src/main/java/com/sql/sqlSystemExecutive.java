/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.systemExecutiveModel;
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
 * @author Andrew
 */
public class sqlSystemExecutive {
    
    public static List<systemExecutiveModel> getOldExecutives() {
        List<systemExecutiveModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT SystemSerbExecutiveid AS id, Active, 'SERB' AS Department, "
                    + "Position, Name, Phone, EMail FROM SystemSerbExecutive "
                    + "UNION ALL SELECT SystemSerbExecutiveid AS id, Active, "
                    + "'SPBR' AS Department, Position, Name, Phone, EMail "
                    + "FROM systemspbrexecutive";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                systemExecutiveModel item = new systemExecutiveModel();
                item.setId(rs.getInt("id"));
                item.setActive(rs.getInt("Active"));
                item.setDepartment(rs.getString("Department").trim());
                item.setPosition(rs.getString("Position").trim());
                item.setName(rs.getString("Name").trim());
                item.setPhone(rs.getString("Phone").trim());
                item.setEmail(rs.getString("EMail").trim());
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
    
    public static void addExecutive(systemExecutiveModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SystemExecutive ("
                    + "active, "     //01
                    + "department, " //02
                    + "position, "   //03
                    + "Name, "       //04
                    + "phoneNumber, "//05
                    + "email "       //06
                    + ") VALUES (";
                    for(int i=0; i<5; i++){
                        sql += "?, ";//01-05
                    }
                     sql += "?)";    //06
            ps = conn.prepareStatement(sql);
            ps.setInt   (1, item.getActive());
            ps.setString(2, item.getDepartment());
            ps.setString(3, item.getPosition());
            ps.setString(4, item.getName());
            ps.setString(5, item.getPhone());
            ps.setString(6, item.getEmail());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
}
