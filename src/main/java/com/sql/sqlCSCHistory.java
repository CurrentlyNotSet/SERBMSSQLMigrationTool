/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldCSCHistoryModel;
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
public class sqlCSCHistory {
    
    public static List<oldCSCHistoryModel> getActivities() {
        List<oldCSCHistoryModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM CSCHistory";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCSCHistoryModel item = new oldCSCHistoryModel();
                
                item.setCSCHistoryID(rs.getInt("CSCHistoryID"));
                item.setActive(rs.getInt("Active"));
                item.setInitials(rs.getString("Initials"));
                item.setCSCID(rs.getInt("CSCID"));
                item.setDateTimeMillis(rs.getLong("DateTimeMillis"));
                item.setDirection(rs.getString("Direction"));
                item.setDate(rs.getString("Date"));
                item.setTime(rs.getString("Time"));
                item.setCSCNumber(rs.getString("CSCNumber"));
                item.setType(rs.getString("Type"));
                item.setSection(rs.getString("Section"));
                item.setFileAttrib(rs.getString("FileAttrib"));
                item.setOtherComment(rs.getString("OtherComment"));
                item.setFileName(rs.getString("FileName"));
                item.setDescription(rs.getString("Description"));
                item.setNote(rs.getString("Note"));
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
