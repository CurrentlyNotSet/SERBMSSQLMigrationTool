/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.caseNumberModel;
import com.model.oldHearingsMediationModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
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
public class sqlHearingsMediation {
    
    public static List<oldHearingsMediationModel> getHearingsMediations() {
        List<oldHearingsMediationModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM HearingMediationDate ORDER BY CaseNumber";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldHearingsMediationModel item = new oldHearingsMediationModel();
                item.setHearingMediationDateID(rs.getInt("HearingMediationDateID"));
                item.setActive(rs.getInt("Active"));
                item.setCaseNumber(rs.getString("CaseNumber") != null ? rs.getString("CaseNumber") : "");
                item.setPCPreD(rs.getString("PCPreD") != null ? rs.getString("PCPreD") : "");
                item.setMediatorInitials(rs.getString("MediatorInitials") != null ? rs.getString("MediatorInitials") : "");
                item.setDateAssigned(rs.getString("DateAssigned") != null ? rs.getString("DateAssigned") : "");
                item.setMedDate(rs.getString("MedDate") != null ? rs.getString("MedDate") : "");
                item.setOutcome(rs.getString("Outcome") != null ? rs.getString("Outcome") : "");
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
                
    public static void batchAddOldHearingsMediation(List<oldHearingsMediationModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO HearingsMediation ("
                    + "active, "        //01
                    + "caseYear, "      //02
                    + "caseType, "      //03
                    + "caseMonth, "     //04
                    + "caseNumber, "    //05
                    + "PCPreD, "        //06
                    + "mediator, "      //07
                    + "DateAssigned, "  //08
                    + "MediationDate, " //09
                    + "Outcome "        //10
                    + ") VALUES (";
                    for(int i=0; i<9; i++){
                        sql += "?, ";   //01-00
                    }
                     sql += "?)";   //10
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (oldHearingsMediationModel item : list) {
                caseNumberModel caseNumber = null;
                if (item.getCaseNumber().trim().length() == 16) {
                    caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
                }

                if(caseNumber != null){
                    int mediatorID = StringUtilities.convertUserInitialToID(item.getMediatorInitials());
                                        
                    ps.setBoolean( 1, item.getActive() == 1);
                    ps.setString ( 2, caseNumber.getCaseYear());
                    ps.setString ( 3, caseNumber.getCaseType());
                    ps.setString ( 4, caseNumber.getCaseMonth());
                    ps.setString ( 5, caseNumber.getCaseNumber());
                    ps.setString ( 6, item.getPCPreD().trim().equals("") ? null : item.getPCPreD().trim());
                    if (mediatorID != 0){
                        ps.setInt  ( 7, mediatorID);
                    } else {
                        ps.setNull ( 7, java.sql.Types.INTEGER);
                    }
                    ps.setDate   ( 8, StringUtilities.convertStringSQLDate(item.getDateAssigned()));
                    ps.setDate   ( 9, StringUtilities.convertStringSQLDate(item.getMedDate()));
                    ps.setString (10, item.getOutcome().equals("") ? null : item.getOutcome().trim());
                    ps.addBatch();
                    if (++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
                        currentCount = SceneUpdater.listItemFinished(control, currentCount + Global.getBATCH_SIZE() - 1, totalCount, count + " imported");
                    }
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
