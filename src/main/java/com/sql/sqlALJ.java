/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldALJModel;
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
public class sqlALJ {

    public static List<oldALJModel> getOldALJList() {
        List<oldALJModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM ALJ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldALJModel item = new oldALJModel();
                item.setALJID(rs.getInt("ALJID"));
                item.setActive(rs.getInt("Active"));
                item.setInitials(rs.getString("Initials") == null ? "" : rs.getString("Initials"));
                item.setName(rs.getString("Name") == null ? "" : rs.getString("Name"));
                item.setUsername(rs.getString("Username") == null ? "" : rs.getString("Username"));
                item.setAddress1(rs.getString("Address1") == null ? "" : rs.getString("Address1"));
                item.setAddress2(rs.getString("Address2") == null ? "" : rs.getString("Address2"));
                item.setCity(rs.getString("City") == null ? "" : rs.getString("City"));
                item.setState(rs.getString("State") == null ? "" : rs.getString("State"));
                item.setZip(rs.getString("Zip") == null ? "" : rs.getString("Zip"));
                item.setOfficePhone(rs.getString("OfficePhone") == null ? "" : rs.getString("OfficePhone"));
                item.setHomePhone(rs.getString("HomePhone") == null ? "" : rs.getString("HomePhone"));
                item.setPager(rs.getString("Pager") == null ? "" : rs.getString("Pager"));
                item.setCellular(rs.getString("Cellular") == null ? "" : rs.getString("Cellular"));
                item.setEmail(rs.getString("Email") == null ? "" : rs.getString("Email"));
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
    
}
