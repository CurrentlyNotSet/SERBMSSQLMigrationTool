/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseTypeModel;
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
public class sqlREPCaseType {
    
    public static List<REPCaseTypeModel> getOldREPCaseType() {
        List<REPCaseTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM REPCaseTypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                REPCaseTypeModel item = new REPCaseTypeModel();
                item.setId(rs.getInt("TypeID"));
                item.setActive(rs.getInt("Active"));
                item.setTypeAbbrevation(!"".equals(rs.getString("TypeAbbrev").trim()) ? rs.getString("TypeAbbrev").trim() : null);
                item.setTypeName(!"".equals(rs.getString("TypeName").trim()) ? rs.getString("TypeName").trim() : null);
                item.setDescription(!"".equals(rs.getString("Description").trim()) ? rs.getString("Description").trim() : null);
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
        
    public static void addREPCaseType(REPCaseTypeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseType ("
                    + "Active, "         //01
                    + "typeAbbrevation, "//02
                    + "typeName, "       //03
                    + "description, "    //04
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?)"; //04
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getTypeAbbrevation());
            ps.setString( 3, item.getTypeName());
            ps.setString( 3, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
