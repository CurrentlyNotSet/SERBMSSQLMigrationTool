/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSCaseSearchModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
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
public class sqlCMDSCaseSearch {

    public static void batchAddCaseSearch(List<CMDSCaseSearchModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSCaseSearch ("
                    + "caseYear, "    //01
                    + "caseType, "    //02
                    + "caseMonth, "   //03
                    + "caseNumber, "  //04
                    + "appellant, "   //05
                    + "appellee, "    //06
                    + "alj, "         //07
                    + "dateOpened, "  //08
                    + "appelleeREP, " //09
                    + "appellantREP " //10
                    + ") VALUES (";
            for (int i = 0; i < 9; i++) {
                sql += "?, ";   //01-09
            }
            sql += "?)"; //10
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for (CMDSCaseSearchModel item : list) {
                ps.setString(1, StringUtils.left(item.getCaseYear(), 4));
                ps.setString(2, StringUtils.left(item.getCaseType(), 4));
                ps.setString(3, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString(4, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString(5, item.getAppellant());
                ps.setString(6, item.getAppellee());
                ps.setString(7, item.getAlj());
                ps.setDate  (8, item.getDateOpened());
                ps.setString(9, item.getAppelleeRep());
                ps.setString(10, item.getAppellantRep());
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
