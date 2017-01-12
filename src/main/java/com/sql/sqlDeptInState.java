/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.deptInStateModel;
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
public class sqlDeptInState {
    
    public static List<deptInStateModel> getOldDeptInState() {
        List<deptInStateModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM DeptInState";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                deptInStateModel item = new deptInStateModel();
                item.setId(rs.getInt("DeptInStateid"));
                item.setActive(rs.getInt("Active"));
                item.setStateCode(rs.getString("DeptInStateCode"));
                item.setDescription(rs.getString("DeptInStateDescription"));               
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
        
    public static void addDeptInState(deptInStateModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO DeptInState ("
                    + "Active, "    //01
                    + "code, "      //02
                    + "description" //03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getStateCode());
            ps.setString( 3, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
