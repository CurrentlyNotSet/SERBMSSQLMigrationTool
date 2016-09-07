/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ORGParentChildLinkModel;
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
public class sqlORGParentChildLink {
    
    public static List<ORGParentChildLinkModel> getOldLink() {
        List<ORGParentChildLinkModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT employeeorglinkid AS id, P.orgnumber AS parent, c.orgnumber AS child "
                    + "FROM employeeorglink "
                    + "LEFT JOIN employeeorg AS P ON p.employeeorgid = employeeorglink.parentid "
                    + "LEFT JOIN employeeorg as C ON C.employeeorgid = employeeorglink.childid";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ORGParentChildLinkModel item = new ORGParentChildLinkModel();
                item.setId(rs.getInt("id"));
                item.setActive(true);
                item.setParentOrgNumber(rs.getString("parent"));
                item.setChildOrgNumber(rs.getString("child"));
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
    
    public static void importOrgParentChildLinks(ORGParentChildLinkModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO ORGParentChildLink ("
                    + "active, "
                    + "parentOrgNumber, "
                    + "childOrgNumber "
                    + ") VALUES ("
                    + "?, "
                    + "?, "
                    + "?)";
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, item.getParentOrgNumber());
            ps.setString ( 3, item.getChildOrgNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    
}