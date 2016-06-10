/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.historyTypeModel;
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
 * @author User
 */
public class sqlActivityType {
    
    public static List<historyTypeModel> getOldActivityType() {
        List<historyTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM HistoryType";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                historyTypeModel item = new historyTypeModel();
                item.setId(rs.getInt("HistoryTypeid"));
                item.setActive(rs.getInt("Active"));
                item.setType(rs.getString("Type"));
                item.setSection(rs.getString("Section"));
                item.setFileAttrib(rs.getString("FileAttrib"));
                item.setHistoryDescription(rs.getString("HistoryDescription"));
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
    
    
    public static void addActivtyType(historyTypeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO ActivityType ("
                    + "active, "          //01
                    + "section, "         //02
                    + "descriptionAbbrv, "//03
                    + "descriptionFull "  //04
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?)"; //04
            ps = conn.prepareStatement(sql);
            ps.setInt   (1, item.getActive());
            ps.setString(2, item.getSection());
            ps.setString(3, item.getFileAttrib());
            ps.setString(4, item.getHistoryDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
