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
                item.setActive(rs.getInt("Active"));
                item.setType(rs.getString("Type"));
                item.setSection(rs.getString("Section"));
                item.setDocumentDescription(rs.getString("DocumentDescription"));
                item.setDocumentFileName(rs.getString("DocumentFileName"));
                item.setBodyFileName(rs.getString("BodyFileName"));
                item.setSubjectFileName(rs.getString("SubjectFileName"));
                item.setAttachmentListFileName(rs.getString("AttachmentListFileName"));
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
                    + "active "      //05
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?)"; //05
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getSection());
            ps.setString(2, item.getType());
            ps.setString(3, item.getDocumentDescription());
            ps.setString(4, item.getDocumentFileName());
            ps.setInt   (5, item.getActive());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
