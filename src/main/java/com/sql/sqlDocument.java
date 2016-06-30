/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldDocumentModel;
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
    
    public static void addSMDSDocument(oldDocumentModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO SMDSDocuments ("
                    + "section, "    //01
                    + "type, "       //02
                    + "description, "//03
                    + "fileName, "   //04
                    + "active, "     //05
                    + "dueDate "     //06
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?)"; //06
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getSection());
            ps.setString(2, item.getType());
            ps.setString(3, item.getDocumentDescription());
            ps.setString(4, item.getDocumentFileName());
            ps.setInt   (5, item.getActive());
            if (item.getDueDate() != 0){
                ps.setInt  (6, item.getDueDate());
            } else {
                ps.setNull (6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
