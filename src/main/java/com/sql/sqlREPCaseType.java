/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseTypeModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
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
public class sqlREPCaseType {
    
    public static List<REPCaseTypeModel> getOldREPCaseType() {
        List<REPCaseTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPCaseTypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                REPCaseTypeModel item = new REPCaseTypeModel();
                item.setId(rs.getInt("TypeID"));
                item.setActive(rs.getInt("Active"));
                item.setTypeAbbrevation(!"".equals(rs.getString("TypeAbbrev").trim()) ? rs.getString("TypeAbbrev").trim() : null);
                item.setTypeName(!"".equals(rs.getString("TypeName").trim()) ? rs.getString("TypeName").trim() : null);
                item.setDescription(!"".equals(rs.getString("Description").trim()) ? rs.getString("Description").trim() : null);
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
        
    public static void addREPCaseType(REPCaseTypeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseType ("
                    + "active, "         //01
                    + "typeAbbrevation, "//02
                    + "typeName, "       //03
                    + "description "     //04
                    + ") VALUES ("
                    + "?, " //01
                    + "?, " //02
                    + "?, " //03
                    + "?)"; //04
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, StringUtils.left(item.getTypeAbbrevation(), 15));
            ps.setString( 3, StringUtils.left(item.getTypeName(), 200));
            ps.setString( 4, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddREPCaseType(List<REPCaseTypeModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseType ("
                    + "active, "         //01
                    + "typeAbbrevation, "//02
                    + "typeName, "       //03
                    + "description "     //04
                    + ") VALUES ("
                    + "?, " //01
                    + "?, " //02
                    + "?, " //03
                    + "?)"; //04
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPCaseTypeModel item : list) {
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getTypeAbbrevation(), 15));
                ps.setString( 3, StringUtils.left(item.getTypeName(), 200));
                ps.setString( 4, item.getDescription());
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
