/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.systemExecutiveModel;
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
                item.setLastName(rs.getString("Name").trim());
                item.setPhone(rs.getString("Phone").trim());
                item.setEmail(rs.getString("EMail").trim());
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
    
    public static void batchAddExecutive(List<systemExecutiveModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SystemExecutive ("
                    + "active, "     //01
                    + "department, " //02
                    + "position, "   //03
                    + "firstName, "  //04
                    + "middleName, " //05
                    + "lastName, "   //06
                    + "phoneNumber, "//07
                    + "email "       //08
                    + ") VALUES (";
                    for(int i=0; i<7; i++){
                        sql += "?, ";//01-07
                    }
                     sql += "?)";    //08
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (systemExecutiveModel item : list) {
                String[] nameSplit = item.getLastName().split(" ");

                switch (nameSplit.length) {
                    case 2:
                        item.setFirstName(nameSplit[0]);
                        item.setLastName(nameSplit[1]);
                        break;
                    case 3:
                        item.setFirstName(nameSplit[0]);
                        item.setMiddleName(nameSplit[1]);
                        item.setLastName(nameSplit[2]);
                        break;
                    default:
                        item.setFirstName(null);
                        item.setMiddleName(null);
                        item.setLastName(null);
                        break;
                }

                ps.setInt   (1, item.getActive());
                ps.setString(2, StringUtils.left(item.getDepartment(), 4));
                ps.setString(3, StringUtils.left("".equals(item.getPosition()) ? null : item.getPosition(), 200));
                ps.setString(4, StringUtils.left(item.getFirstName(), 100));
                ps.setString(5, StringUtils.left(item.getMiddleName(), 100));
                ps.setString(6, StringUtils.left(item.getLastName(), 100));
                ps.setString(7, StringUtils.left("".equals(item.getPhone().replaceAll("[^0-9]", "")) ? null : item.getPhone().replaceAll("[^0-9]", ""), 20));
                ps.setString(8, StringUtils.left("".equals(item.getEmail()) ? null : item.getEmail(), 200));
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
