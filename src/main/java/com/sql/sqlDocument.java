/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldDocumentModel;
import com.model.smdsDocumentsModel;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author User
 */
public class sqlDocument {

    public static List<oldDocumentModel> getOldDocuments() {
        List<oldDocumentModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM Document";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldDocumentModel item = new oldDocumentModel();
                item.setDocumentID(rs.getInt("Documentid"));
                item.setActive(rs.getInt("Active") == 1 ? 1 : 0);
                item.setType(!"".equals(rs.getString("Type").trim()) ? WordUtils.capitalize(rs.getString("Type").toLowerCase().trim()) : null);
                item.setSection(!"".equals(rs.getString("Section").trim()) ? rs.getString("Section").trim() : null);
                item.setDocumentDescription(!"".equals(rs.getString("DocumentDescription").trim()) ? rs.getString("DocumentDescription").trim() : null);
                item.setDocumentFileName(!"".equals(rs.getString("DocumentFileName").trim()) ? rs.getString("DocumentFileName").trim() : null);
                item.setBodyFileName(!"".equals(rs.getString("BodyFileName").trim()) ? rs.getString("BodyFileName").trim() : null);
                item.setSubjectFileName(!"".equals(rs.getString("SubjectFileName").trim()) ? rs.getString("SubjectFileName").trim() : null);
                item.setAttachmentListFileName(!"".equals(rs.getString("AttachmentListFileName").trim()) ? rs.getString("AttachmentListFileName").trim() : null);
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

    public static List<smdsDocumentsModel> getNewDocuments() {
        List<smdsDocumentsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "SELECT * FROM SMDSDocuments";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                smdsDocumentsModel item = new smdsDocumentsModel();
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

    public static void addSMDSDocument(smdsDocumentsModel item) {
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
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static void batchAddSMDSDocument(List<smdsDocumentsModel> list) {
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
            
            for (smdsDocumentsModel item : list){
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
