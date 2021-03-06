/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSHearingModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class sqlCMDSHearing {
        
    public static List<CMDSHearingModel> getHearingsList() {
        List<CMDSHearingModel> list = new ArrayList();
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
                CMDSHearingModel item = new CMDSHearingModel();

                item.setId(rs.getInt("CaseHearingsID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setCaseYear(rs.getString("Year"));
                item.setCaseType(rs.getString("Type"));
                item.setCaseMonth(rs.getString("Month"));
                item.setCaseNumber(rs.getString("CaseSeqNumber"));
                item.setEntryDate(rs.getString("EntryDate").length() < 10 ? null : StringUtilities.convertStringSQLDate(rs.getString("EntryDate").substring(0,9)));
                item.setHearingType(rs.getString("Hearingtype").equals("null") ? "" : rs.getString("Hearingtype"));
                item.setRoom(rs.getString("Room").equals("null") ? "" : rs.getString("Room"));
                
                if (rs.getString("HearingDate").length() > 10 && !rs.getString("HearingTime").equals("")){
                    item.setHearingDateTime(item.getEntryDate() == null 
                        ? null : StringUtilities.convertStringDateAndTime(
                                rs.getString("HearingDate").substring(0,9).trim(), 
                                rs.getString("HearingTime").trim())
                    );
                }
                
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
        
    public static void batchAddHearings(List<CMDSHearingModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
            conn.setAutoCommit(false);

            for (CMDSHearingModel item : list) {
                ps.setBoolean  (1, item.isActive());
                ps.setString   (2, StringUtils.left(item.getCaseYear(), 4));
                ps.setString   (3, StringUtils.left(item.getCaseType(), 4));
                ps.setString   (4, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString   (5, StringUtils.left(item.getCaseNumber(), 4));
                ps.setDate     (6, item.getEntryDate());
                ps.setTimestamp(7, item.getHearingDateTime());
                ps.setString   (8, StringUtils.left(item.getHearingType(), 255));
                ps.setString   (9, StringUtils.left(item.getRoom(), 255));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                SlackNotification.sendNotification(ex1);
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
