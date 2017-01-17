/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPMediationModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class sqlREPMediation {
    
    public static void addREPMediation(REPMediationModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPMediation("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "mediationEntryDate, "//05
                    + "mediationDate, "     //06
                    + "mediationType, "     //07
                    + "mediatorID, "        //08
                    + "mediationOutcome "   //09
                    + ") VALUES (";
                    for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                     sql += "?)";   //09
            ps = conn.prepareStatement(sql);
            ps.setString   (1, item.getCaseYear());
            ps.setString   (2, item.getCaseType());
            ps.setString   (3, item.getCaseMonth());
            ps.setString   (4, item.getCaseNumber());
            ps.setDate     (5, item.getMediationEntryDate());
            ps.setTimestamp(6, item.getMediationDate());
            ps.setString   (7, item.getMediationType());
            ps.setString   (8, item.getMediatorID());
            ps.setString   (9, item.getMediationOutcome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddREPMediation(List<REPMediationModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPMediation("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "mediationEntryDate, "//05
                    + "mediationDate, "     //06
                    + "mediationType, "     //07
                    + "mediatorID, "        //08
                    + "mediationOutcome "   //09
                    + ") VALUES (";
                    for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                     sql += "?)";   //09
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPMediationModel item : list) {
                ps.setString   (1, StringUtils.left(item.getCaseYear(), 4));
                ps.setString   (2, StringUtils.left(item.getCaseType(), 3));
                ps.setString   (3, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString   (4, StringUtils.left(item.getCaseNumber(), 4));
                ps.setDate     (5, item.getMediationEntryDate());
                ps.setTimestamp(6, item.getMediationDate());
                ps.setString   (7, StringUtils.left(item.getMediationType(), 100));
                ps.setString   (8, StringUtils.left(item.getMediatorID(), 5));
                ps.setString   (9, StringUtils.left(item.getMediationOutcome(), 100));
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                    currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
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
