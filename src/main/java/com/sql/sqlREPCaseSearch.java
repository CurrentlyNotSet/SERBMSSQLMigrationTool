/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.REPCaseSearchModel;
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
 * @author Andrew
 */
public class sqlREPCaseSearch {
    
    public static void addREPCaseSearchCase(List<REPCaseSearchModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try { 
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO REPCaseSearch("
                    + "caseYear, "      //01
                    + "caseType, "      //02
                    + "caseMonth, "     //03
                    + "caseNumber, "    //04
                    + "employerName, "  //05
                    + "bunNumber, "     //06
                    + "description, "   //07
                    + "county, "        //08
                    + "boardDeemed, "   //09
                    + "employeeOrg, "   //10
                    + "incumbent "      //11
                    + ") VALUES (";
                    for(int i=0; i<10; i++){
                        sql += "?, ";   //01-10
                    }
                     sql += "?)";   //11
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (REPCaseSearchModel item : list){
                ps.setString( 1, StringUtils.left(item.getCaseYear(), 4));
                ps.setString( 2, StringUtils.left(item.getCaseType(), 3));
                ps.setString( 3, StringUtils.left(item.getCaseMonth(), 2));
                ps.setString( 4, StringUtils.left(item.getCaseNumber(), 4));
                ps.setString( 5, item.getEmployerName());
                ps.setString( 6, StringUtils.left(item.getBunNumber(), 8));
                ps.setString( 7, item.getDescription());
                ps.setString( 8, StringUtils.left(item.getCounty(), 100));
                ps.setString( 9, StringUtils.left(item.getBoardDeemed(), 20));
                ps.setString(10, item.getEmployeeOrg());
                ps.setString(11, item.getIncumbent());
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
