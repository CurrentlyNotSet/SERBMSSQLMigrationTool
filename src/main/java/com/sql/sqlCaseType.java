/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.caseTypeModel;
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
public class sqlCaseType {
        
    public static List<caseTypeModel> getOldCaseType() {
        List<caseTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT caseTypeid AS id, active AS active, "
                    + "section AS section, casetype AS type, NULL AS description "
                    + "FROM casetype UNION ALL SELECT caseTypesid AS id, "
                    + "active AS active, 'CMDS' AS section, type AS type, "
                    + "description AS description FROM casetypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                caseTypeModel item = new caseTypeModel();
                item.setId(rs.getInt("id"));
                item.setSection(rs.getString("section"));
                item.setCaseType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
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
        
    public static void batchAddCaseType(List<caseTypeModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CaseType ("
                    + "section, "   //01
                    + "caseType, "  //02
                    + "description "//03
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?)"; //03
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (caseTypeModel item : list) {
                ps.setString(1, StringUtils.left(item.getSection(), 10));
                ps.setString(2, StringUtils.left(item.getCaseType(), 3));
                ps.setString(3, StringUtils.left(item.getDescription(), 100));
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
