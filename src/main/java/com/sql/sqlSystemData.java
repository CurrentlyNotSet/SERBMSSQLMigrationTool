/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldCountyModel;
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
public class sqlSystemData {
    public static List<oldCountyModel> getCounties() {
        List<oldCountyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM County";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCountyModel item = new oldCountyModel();
                item.setCountyid(rs.getInt("Countyid"));
                item.setActive(rs.getInt("Active"));
                item.setStateCode(rs.getString("StateCode"));
                item.setRegionCode(rs.getString("RegionCode"));
                item.setCountyCode(rs.getString("CountyCode"));
                item.setName(rs.getString("Name"));
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
    
    public static void addCounty(oldCountyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO County ("
                    + "active, "    //01
                    + "stateCode, " //02
                    + "regionCode, "//03
                    + "countyCode, "//04
                    + "countyName " //05
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?)"; //05
            ps = conn.prepareStatement(sql);
            ps.setInt   (1, item.getActive());
            ps.setString(2, item.getStateCode());
            ps.setString(3, item.getRegionCode());
            ps.setString(4, item.getCountyCode());
            ps.setString(5, item.getName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
