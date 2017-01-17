/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.SMDSDocumentsModel;
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

/**
 *
 * @author User
 */
public class sqlDocument {

    public static List<SMDSDocumentsModel> getNewDocuments() {
        List<SMDSDocumentsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM SMDSDocuments";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SMDSDocumentsModel item = new SMDSDocumentsModel();
                item.setId(rs.getInt("id"));
                item.setActive(rs.getBoolean("Active"));
                item.setType(rs.getString("Type") == null ? null : rs.getString("Type").trim());
                item.setSection(rs.getString("Section") == null ? null : rs.getString("Section").trim());
                item.setDescription(rs.getString("Description") == null ? null : rs.getString("Description").trim());
                item.setFileName(rs.getString("FileName") == null ? null : rs.getString("FileName").trim());
                item.setDueDate(rs.getInt("dueDate"));
                item.setGroup(rs.getString("group") == null ? null : rs.getString("group").trim());
                item.setHistoryFileName(rs.getString("historyFileName") == null ? null : rs.getString("historyFileName").trim());
                item.setHistoryDescription(rs.getString("historyDescription") == null ? null : rs.getString("historyDescription").trim());
                item.setCHDCHG(rs.getString("CHDCHG") == null ? null : rs.getString("CHDCHG").trim());
                item.setQuestionsFileName(rs.getString("questionsFileName") == null ? null : rs.getString("questionsFileName").trim());
                item.setEmailSubject(rs.getString("emailSubject") == null ? null : rs.getString("emailSubject").trim());
                item.setParameters(rs.getString("parameters") == null ? null : rs.getString("parameters").trim());
                item.setEmailBody(rs.getString("emailBody") == null ? null : rs.getString("emailBody").trim());
                item.setSortOrder(rs.getDouble("sortOrder"));
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

    public static void batchAddSMDSDocument(List<SMDSDocumentsModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SMDSDocuments ("
                    + "section, " //01
                    + "type, " //02
                    + "description, " //03
                    + "fileName, " //04
                    + "active, " //05
                    + "dueDate, " //06
                    + "[group], " //07
                    + "historyFileName, " //08
                    + "historyDescription, "//09
                    + "CHDCHG, " //10
                    + "questionsFileName, " //11
                    + "emailSubject, " //12
                    + "parameters, " //13
                    + "emailBody, " //14
                    + "sortOrder " //15
                    + ") VALUES (";
            for (int i = 0; i < 14; i++) {
                sql += "?, ";   //01-14
            }
            sql += "?)";   //15
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (SMDSDocumentsModel item : list){
                ps.setString(1, item.getSection());
                ps.setString(2, item.getType());
                ps.setString(3, item.getDescription());
                ps.setString(4, item.getFileName());
                ps.setBoolean(5, item.isActive());
                if (item.getDueDate() > 0) {
                    ps.setInt(6, item.getDueDate());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }
                ps.setString(7, item.getGroup());
                ps.setString(8, item.getHistoryFileName());
                ps.setString(9, item.getHistoryDescription());
                ps.setString(10, item.getCHDCHG());
                ps.setString(11, item.getQuestionsFileName());
                ps.setString(12, item.getEmailSubject());
                ps.setString(13, item.getParameters());
                ps.setString(14, item.getEmailBody());
                if (item.getSortOrder() > 0) {
                    ps.setDouble(15, item.getSortOrder());
                } else {
                    ps.setNull(15, java.sql.Types.DOUBLE);
                }
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
