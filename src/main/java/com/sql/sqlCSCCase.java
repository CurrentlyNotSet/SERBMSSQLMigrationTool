/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CSCCaseModel;
import com.model.oldCivilServiceCommissionModel;
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
 * @author User
 */
public class sqlCSCCase {
    
    public static List<oldCivilServiceCommissionModel> getCases() {
        List<oldCivilServiceCommissionModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM CivilServiceCommission ORDER BY cscnumber";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCivilServiceCommissionModel item = new oldCivilServiceCommissionModel();
                
                item.setCSCID(rs.getInt("cscid"));
                item.setACTIVE(rs.getInt("active"));
                item.setCSCName(rs.getString("cscname"));
                item.setCSCType(rs.getString("csctype"));
                item.setCSCNumber(rs.getInt("cscnumber"));
                item.setCSCEmployerID(rs.getString("cscemployerID"));
                item.setCSCAddress1(rs.getString("cscaddress1"));
                item.setCSCAddress2(rs.getString("cscaddress2"));
                item.setCSCCity(rs.getString("csccity"));
                item.setCSCCounty(rs.getString("csccounty"));
                item.setCSCState(rs.getString("cscstate"));
                item.setCSCZipPlusFour(rs.getString("csczipplusfour"));
                item.setCSCPhone1(rs.getString("cscphone1"));
                item.setCSCPhone2(rs.getString("cscphone2"));
                item.setCSCFax(rs.getString("cscfax"));
                item.setCSCEmail(rs.getString("cscemail"));
                item.setRepType(rs.getString("reptype"));
                item.setRepFirstName(rs.getString("repfirstname"));
                item.setRepMiddleInitial(rs.getString("repmiddleinitial"));
                item.setRepLastName(rs.getString("replastName"));
                item.setRepAddress1(rs.getString("repaddress1"));
                item.setRepAddress2(rs.getString("repaddress2"));
                item.setRepCity(rs.getString("repcity"));
                item.setRepState(rs.getString("repstate"));
                item.setRepZipPlusFour(rs.getString("repzipplusfour"));
                item.setRepPhone1(rs.getString("repphone1"));
                item.setRepPhone2(rs.getString("repphone2"));
                item.setRepFax(rs.getString("repfax"));
                item.setRepEmail(rs.getString("repemail"));
                item.setChairman1(rs.getString("chairman1"));
                item.setChairman1Title(rs.getString("chairman1title"));
                item.setChairman2(rs.getString("chairman2"));
                item.setChairman2Title(rs.getString("chairman2title"));
                item.setChairman3(rs.getString("chairman3"));
                item.setChairman3Title(rs.getString("chairman3title"));
                item.setChairman4(rs.getString("chairman4"));
                item.setChairman4Title(rs.getString("chairman4title"));
                item.setStatutory(rs.getString("statutory"));
                item.setCharter(rs.getString("charter"));
                item.setFiscalYearEnding(rs.getString("Fiscalyearending"));
                item.setLastNotification(rs.getString("lastnotification"));
                item.setActivitesLastFiled(rs.getString("activiteslastfiled"));
                item.setPreviousFileDate(rs.getString("previousfiledate"));
                item.setDescription1(rs.getString("description1"));
                item.setDescription2(rs.getString("description2"));
                item.setParent1(rs.getString("Parent1") == null ? "" : rs.getString("Parent1"));
                item.setParent2(rs.getString("Parent2") == null ? "" : rs.getString("Parent2"));
                item.setDueDate(rs.getString("DueDate"));
                item.setFiled(rs.getString("Filed"));
                item.setValid(rs.getString("Valid"));
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
        
    public static void BatchAddCSCCase(List<CSCCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO CSCCase ("
                    + "active, "        //01
                    + "name, "          //02
                    + "type, "          //03
                    + "cscNumber, "     //04
                    + "address1, "      //05
                    + "address2, "      //06
                    + "city, "          //07
                    + "state, "         //08
                    + "zipCode, "       //09
                    + "phone1, "        //10
                    + "phone2, "        //11
                    + "fax, "           //12
                    + "email, "         //13
                    + "statutory, "     //14
                    + "charter, "       //15
                    + "fiscalYearEnding, "  //16
                    + "lastNotification, "  //17
                    + "activityLastFiled, " //18
                    + "previousFileDate, "  //19
                    + "dueDate, "       //20
                    + "filed, "         //21
                    + "valid, "         //22
                    + "note, "          //23
                    + "alsoknownas, "   //24
                    + "county "         //25
                    + ") VALUES (";
                    for(int i=0; i<24; i++){
                        sql += "?, ";   //01-24
                    }
                     sql += "?)";   //25
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (CSCCaseModel item : list){
                ps.setBoolean( 1, item.isActive());
                ps.setString ( 2, StringUtils.left(item.getName(), 500));
                ps.setString ( 3, StringUtils.left(item.getType(), 50));
                ps.setString ( 4, StringUtils.left(item.getCscNumber(), 10));
                ps.setString ( 5, StringUtils.left(item.getAddress1(), 255));
                ps.setString ( 6, StringUtils.left(item.getAddress2(), 255));
                ps.setString ( 7, StringUtils.left(item.getCity(), 255));
                ps.setString ( 8, StringUtils.left(item.getState(), 4));
                ps.setString ( 9, StringUtils.left(item.getZipCode(), 10));
                ps.setString (10, StringUtils.left(item.getPhone1().equals("") ? null : item.getPhone1(), 20));
                ps.setString (11, StringUtils.left(item.getPhone2().equals("") ? null : item.getPhone2(), 20));
                ps.setString (12, StringUtils.left(item.getFax().equals("") ? null : item.getFax(), 20));
                ps.setString (13, StringUtils.left(item.getEmail(), 255));
                ps.setBoolean(14, item.isStatutory());
                ps.setBoolean(15, item.isCharter());
                ps.setString (16, StringUtils.left(item.getFiscalYearEnding(), 20));
                ps.setString (17, StringUtils.left(item.getLastNotification(), 255));
                ps.setDate   (18, item.getActivityLastFiled());
                ps.setDate   (19, item.getPreviousFileDate());
                ps.setString (20, StringUtils.left(item.getDueDate(), 20));
                ps.setDate   (21, item.getFiled());
                ps.setBoolean(22, item.isValid());
                ps.setString (23, item.getNote());
                ps.setString (24, StringUtils.left(item.getAlsoknownas(), 255));
                ps.setString (25, StringUtils.left(item.getCounty(), 100));
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
