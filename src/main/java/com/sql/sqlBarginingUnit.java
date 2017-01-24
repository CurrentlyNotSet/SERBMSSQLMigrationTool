/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.barginingUnitModel;
import com.model.oldBarginingUnitNewModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
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
 * @author Andrew
 */
public class sqlBarginingUnit {
    
    public static List<oldBarginingUnitNewModel> getOldBarginingUnits() {
        List<oldBarginingUnitNewModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM BarginingUnitNew";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldBarginingUnitNewModel item = new oldBarginingUnitNewModel();
                item.setBarginingUnitid(rs.getInt("BarginingUnitid"));
                item.setActive(rs.getInt("Active"));
                item.setEmployerNumber(rs.getString("EmployerNumber"));
                item.setUnitNumber(rs.getString("UnitNumber"));
                item.setCert(rs.getString("Cert"));
                item.setBUEmployerName(rs.getString("BUEmployerName"));
                item.setJur(rs.getString("Jur"));
                item.setCounty(rs.getString("County"));
                item.setLUnion(rs.getString("LUnion"));
                item.setLocal(rs.getString("Local"));
                item.setStrike(rs.getString("Strike"));
                item.setLGroup(rs.getString("LGroup"));
                item.setCertDate(rs.getString("CertDate"));
                item.setEnabled(rs.getString("Enabled"));
                item.setCaseRef(rs.getString("CaseRef"));
                item.setUnitDescription(rs.getString("UnitDescription"));                
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
    
    public static void batchAddBarginingUnit(List<barginingUnitModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO BarginingUnit ("
                    + "Active, "            //01
                    + "EmployerNumber, "    //02
                    + "UnitNumber, "        //03
                    + "Cert, "              //04
                    + "BUEmployerName, "    //05
                    + "Jurisdiction, "      //06
                    + "County, "            //07
                    + "LUnion, "            //08
                    + "Local, "             //09
                    + "Strike, "            //10
                    + "LGroup, "            //11
                    + "CertDate, "          //12
                    + "Enabled, "           //13
                    + "CaseRefYear, "       //14
                    + "CaseRefSection, "    //15
                    + "CaseRefMonth, "      //16
                    + "CaseRefSequence, "   //17
                    + "UnitDescription, "   //18
                    + "Notes"               //19
                    + ") VALUES ("; 
                    for(int i=0; i<18; i++){
                        sql += "?, ";   //01-18
                    }
                     sql += "?)"; //19
           ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (barginingUnitModel item : list) {
                ps.setInt   ( 1, item.getActive());
                ps.setString( 2, StringUtils.left(item.getEmployerNumber(), 4));
                ps.setString( 3, StringUtils.left(item.getUnitNumber(), 2));
                ps.setString( 4, StringUtils.left(item.getCert(), 1));
                ps.setString( 5, StringUtils.left(item.getBUEmployerName(), 150));
                ps.setString( 6, StringUtils.left(item.getJurisdiction(), 10));
                ps.setString( 7, StringUtils.left(item.getCounty(), 50));
                ps.setString( 8, StringUtils.left(item.getLUnion(), 100));
                ps.setString( 9, StringUtils.left(item.getLocal(), 25));
                ps.setInt   (10, item.getStrike());
                ps.setString(11, StringUtils.left(item.getLGroup(), 2));
                ps.setDate  (12, item.getCertDate());
                ps.setInt   (13, item.getEnabled());
                ps.setString(14, StringUtils.left(item.getCaseRefYear(), 4));
                ps.setString(15, StringUtils.left(item.getCaseRefSection(), 4));
                ps.setString(16, StringUtils.left(item.getCaseRefMonth(), 2));
                ps.setString(17, StringUtils.left(item.getCaseRefSequence(), 4));
                ps.setString(18, item.getUnitDescription());
                ps.setString(19, item.getNotes());
                ps.addBatch();
                    if(++count % Global.getBATCH_SIZE() == 0) {
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
