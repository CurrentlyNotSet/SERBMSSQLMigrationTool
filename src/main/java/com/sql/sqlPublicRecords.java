/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldPublicRecordsModel;
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
public class sqlPublicRecords {
    
    public static List<oldPublicRecordsModel> getOldPublicRecords() {
        List<oldPublicRecordsModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT PublicRecordsDocument.*, "
                    + "cast(PublicRecordsBody.body as varchar(max)) as body, "
                    + "PublicRecordsEmail.EmailAddress, "
                    + "cast(PublicRecordsNotes.Notes as varchar(max)) as notes "
                    + "FROM PublicRecordsDocument "
                    + "LEFT JOIN PublicRecordsBody ON PublicRecordsBody.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsBody.Date = PublicRecordsDocument.Date "
                    + "LEFT JOIN PublicRecordsEmail ON PublicRecordsEmail.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsEmail.Date = PublicRecordsDocument.Date "
                    + "LEFT JOIN PublicRecordsNotes ON PublicRecordsNotes.CaseNumber = PublicRecordsDocument.CaseNumber "
                    + "AND PublicRecordsNotes.Date = PublicRecordsDocument.Date "
                    + "WHERE PublicRecordsBody.CaseNumber != '' AND PublicRecordsDocument.CaseNumber != '' "
                    + "AND PublicRecordsEmail.CaseNumber != '' AND PublicRecordsNotes.CaseNumber != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldPublicRecordsModel item = new oldPublicRecordsModel();
                item.setId(rs.getInt("PublicRecordsDocumentID"));
                item.setActive(rs.getInt("Active"));
                item.setDateTime(rs.getString("Date"));
                item.setCaseNumber(rs.getString("CaseNumber"));
                item.setDocumentName(rs.getString("DocumentName"));
                item.setFileName(rs.getString("DocumentFileName"));
                item.setBody(rs.getString("body"));
                item.setEmailAddress(rs.getString("EmailAddress"));
                item.setNotes(rs.getString("Notes"));
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
