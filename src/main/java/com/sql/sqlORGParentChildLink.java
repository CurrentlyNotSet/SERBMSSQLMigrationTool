/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ORGParentChildLinkModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

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
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
        
    public static void batchAddOrgParentChildLinks(List<ORGParentChildLinkModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
            conn.setAutoCommit(false);
            
            for (ORGParentChildLinkModel item : list) {
                ps.setBoolean( 1, item.isActive());
                ps.setString ( 2, StringUtils.left(item.getParentOrgNumber(), 10));
                ps.setString ( 3, StringUtils.left(item.getChildOrgNumber(), 10));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                SlackNotification.sendNotification(ex1);
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
        
}
