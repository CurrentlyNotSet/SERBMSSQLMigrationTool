/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldBlobFileModel;
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
 * @author Andrew
 */
public class sqlBlobFile {
    
    public static List<oldBlobFileModel> getOldBlobData(String caseNumber) {
        List<oldBlobFileModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM blobfile WHERE casenumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString( 1, caseNumber);
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

    public static List<oldBlobFileModel> getOldBlobDataBUDectioption(String[] bunnum) {
        List<oldBlobFileModel> list = new ArrayList();
        if (bunnum.length == 2) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
                String sql = "SELECT blobfile.selectorA, blobfile.blobData FROM blobfile JOIN barginingunitnew "
                        + "ON blobfile.caseid = barginingunitNew.barginingunitid "
                        + "WHERE barginingunitNew.employernumber = ? "
                        + "AND barginingunitNew.unitnumber = ? "
                        + "AND blobfile.type = 'BU'";
                ps = conn.prepareStatement(sql);
                ps.setString(1, bunnum[0]);
                ps.setString(2, bunnum[1]);
                rs = ps.executeQuery();
                while (rs.next()) {
                oldBlobFileModel item = new oldBlobFileModel();
                item.setSelectorA(rs.getString("SelectorA"));
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
        }
        return list;
    }

}
