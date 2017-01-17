/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.hearingRoomModel;
import com.model.hearingTypeModel;
import com.util.DBCInfo;
import com.util.Global;
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
public class sqlHearingsInfo {
    
    public static List<hearingRoomModel> getOldHearingRoom() {
        List<hearingRoomModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM HearingRooms";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                hearingRoomModel item = new hearingRoomModel();
                item.setId(rs.getInt("HearingRoomsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setRoomAbbreviation(!"".equals(rs.getString("RoomAbbreviation").trim()) ? rs.getString("RoomAbbreviation").trim() : null);
                item.setRoomName(!"".equals(rs.getString("RoomName").trim()) ? rs.getString("RoomName").trim() : null);
                item.setRoomEmail(!"".equals(rs.getString("RoomEmail").trim()) ? rs.getString("RoomEmail").trim() : null);
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
     
    public static List<hearingTypeModel> getOldHearingType() {
        List<hearingTypeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM HearingTypes";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                hearingTypeModel item = new hearingTypeModel();
                item.setId(rs.getInt("HearingTypesID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setSection("CMDS");
                item.setHearingType(!"".equals(rs.getString("HearingType").trim()) ? rs.getString("HearingType").trim() : null);
                item.setHearingDescription(!"".equals(rs.getString("HearingDescription").trim()) ? rs.getString("HearingDescription").trim() : null);
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
        
    public static void batchAddHearingRoom(List<hearingRoomModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO HearingRoom ("
                    + "active, "          //01
                    + "roomAbbreviation, "//02
                    + "roomName, "        //03
                    + "roomEmail "        //04
                    + ") VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (hearingRoomModel item : list) {
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getRoomAbbreviation());
            ps.setString (3, item.getRoomName());
            ps.setString (4, item.getRoomEmail());
            ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
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
    
    public static void batchAddHearingType(List<hearingTypeModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO HearingType ("
                    + "active, "            //01
                    + "section, "           //02
                    + "hearingType, "       //03
                    + "hearingDescription " //04
                    + ") VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (hearingTypeModel item : list) {
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getSection());
            ps.setString (3, item.getHearingType());
            ps.setString (4, item.getHearingDescription());
            ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
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
