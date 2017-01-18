/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPElectionMultiCaseModel;
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
public class sqlREPElectionMultiCase {
    
    public static void batchAddMultiCaseElection(List<REPElectionMultiCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPElectionMultiCase ("
                    + "active, "      //01
                    + "caseYear, "    //02
                    + "caseType, "    //03
                    + "caseMonth, "   //04
                    + "caseNumber, "  //05
                    + "multicase "    //06
                    + ") VALUES ("
                    + "?,"  //01
                    + "?,"  //02
                    + "?,"  //03
                    + "?,"  //04
                    + "?,"  //05
                    + "?)"; //06
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPElectionMultiCaseModel item : list) {
                ps.setInt   (1, item.getActive());
                ps.setString(2, StringUtils.left(item.getCaseYear(), 4));
                ps.setString(3, StringUtils.left(item.getCaseType(), 3));
                ps.setString(4, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString(5, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString(6, StringUtils.left(item.getMultiCase(), 20));
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
