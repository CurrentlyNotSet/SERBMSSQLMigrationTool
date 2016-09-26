/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSHearingModel;
import com.model.oldCMDShearingModel;
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
public class sqlCMDSHearing {
    
    public static List<oldCMDShearingModel> getHearings() {
        List<oldCMDShearingModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [case].[month], [case].type, casehearings.* "
                    + "FROM casehearings LEFT JOIN [case] ON [case].[year] = casehearings.[year] "
                    + "AND [case].[CaseSeqNumber] = casehearings.[CaseSeqNumber]";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCMDShearingModel item = new oldCMDShearingModel();

                item.setCaseHearingsID(rs.getInt("CaseHearingsID"));
                item.setActive(rs.getInt("Active"));
                item.setYear(rs.getString("Year") == null ? "" : rs.getString("Year"));
                item.setType(rs.getString("Type") == null ? "" : rs.getString("Type"));
                item.setMonth(rs.getString("Month") == null ? "" : rs.getString("Month"));
                item.setCaseSeqNumber(rs.getString("CaseSeqNumber"));
                item.setHistorySeqNumber(rs.getString("HistorySeqNumber"));
                item.setEntryDate(rs.getString("EntryDate").length() < 10 ? "" : rs.getString("EntryDate"));
                item.setHearingtype(rs.getString("Hearingtype").equals("null") ? "" : rs.getString("Hearingtype"));
                item.setHearingDate(rs.getString("HearingDate").length() < 10 ? "" : rs.getString("HearingDate"));
                item.setHearingTime(rs.getString("HearingTime"));
                item.setRoom(rs.getString("Room").equals("null") ? "" : rs.getString("Room"));
                item.setLinktoCalendar(rs.getString("LinktoCalendar"));
                item.setSortDate(rs.getString("SortDate"));
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
    
    public static void addHearings(CMDSHearingModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSHearing("
                    + "active, "            //01
                    + "caseYear, "          //02
                    + "caseType, "          //03
                    + "caseMonth, "         //04
                    + "caseNumber, "        //05
                    + "entryDate, "         //06
                    + "hearingDateTime, "   //07
                    + "hearingType, "       //08
                    + "room "               //09
                    + ") VALUES (";
                    for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                     sql += "?)";   //09
            ps = conn.prepareStatement(sql);
            ps.setBoolean  (1, item.isActive());
            ps.setString   (2, item.getCaseYear());
            ps.setString   (3, item.getCaseType());
            ps.setString   (4, item.getCaseMonth());
            ps.setString   (5, item.getCaseNumber());
            ps.setDate     (6, item.getEntryDate());
            ps.setTimestamp(7, item.getHearingDateTime());
            ps.setString   (8, item.getHearingType());
            ps.setString   (9, item.getRoom());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
