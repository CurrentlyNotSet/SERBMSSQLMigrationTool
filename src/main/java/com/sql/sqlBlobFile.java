/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.caseNumberModel;
import com.model.oldBlobFileModel;
import com.util.DBCInfo;
import com.util.StringUtilities;
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
public class sqlBlobFile {
    
    public static List<oldBlobFileModel> getOldBlobData(caseNumberModel caseNumber) {
        List<oldBlobFileModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "select * from blobfile where casenumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, StringUtilities.generateFullCaseNumber(caseNumber));
            rs = ps.executeQuery();
            while (rs.next()) {
                oldBlobFileModel item = new oldBlobFileModel();
                item.setBlobFileid(rs.getInt("blobFileID"));
                item.setActive(rs.getInt("Active"));
                item.setType(rs.getString("type"));
                item.setSection(rs.getString("section"));
                item.setCaseid(rs.getInt("caseID"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setSelectorA(rs.getString("SelectorA"));
                item.setSelectorB(rs.getString("SelectorB"));
                item.setSequence(0);
                item.setBlobData(rs.getBlob("BlobData"));                
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
