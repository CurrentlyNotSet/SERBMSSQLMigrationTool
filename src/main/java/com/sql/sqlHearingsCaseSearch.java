/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.HearingsCaseSearchModel;
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
public class sqlHearingsCaseSearch {
    
    public static void importHearingCaseSearch(List<HearingsCaseSearchModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO HearingCaseSearch ("
                    + "caseYear, "              //01
                    + "caseType, "              //02
                    + "caseMonth, "             //03
                    + "caseNumber, "            //04
                    + "hearingStatus, "         //05
                    + "hearingParties, "        //06
                    + "hearingPCDate, "         //07
                    + "hearingALJ, "            //08
                    + "hearingBoardActionDate " //09
                    + ") VALUES (";
                    for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                     sql += "?)";   //09
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (HearingsCaseSearchModel item : list) {
                ps.setString (1, StringUtils.left(item.getCaseYear(), 4));
                ps.setString (2, StringUtils.left(item.getCaseType(), 3));
                ps.setString (3, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString (4, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString (5, StringUtils.left(item.getHearingStatus(), 10));
                ps.setString (6, item.getHearingParties());
                ps.setDate   (7, item.getHearingPCDate());
                ps.setString (8, item.getHearingALJ());
                ps.setDate   (9, item.getHearingBoardActionDate());
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
