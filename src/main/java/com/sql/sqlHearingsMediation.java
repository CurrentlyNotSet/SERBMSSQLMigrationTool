/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.HearingsMediationModel;
import com.model.oldHearingsMediationModel;
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
        
    public static void importOldHearingsMediation(HearingsMediationModel item) {
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
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, item.getCaseYear());
            ps.setString ( 3, item.getCaseType());
            ps.setString ( 4, item.getCaseMonth());
            ps.setString ( 5, item.getCaseNumber());
            ps.setString ( 6, item.getPCPreD());
            ps.setInt    ( 7, item.getMediator());
            ps.setDate   ( 8, item.getDateAssigned());
            ps.setDate   ( 9, item.getMediationDate());
            ps.setString (10, item.getOutcome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
