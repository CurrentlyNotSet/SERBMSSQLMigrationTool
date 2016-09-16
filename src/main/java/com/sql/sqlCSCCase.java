/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CSCCaseModel;
import com.model.oldCivilServiceCommissionModel;
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
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
        
    public static void importOldCSCCase(CSCCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO CSCCase ("
                    + "active, "        //01
                    + "name, "          //02
                    + "type, "          //03
                    + "cscNumber, "     //04
                    + "cscEmployerID, " //05
                    + "address1, "      //06
                    + "address2, "      //07
                    + "city, "          //08
                    + "state, "         //09
                    + "zipCode, "       //10
                    + "phone1, "        //11
                    + "phone2, "        //12
                    + "fax, "           //13
                    + "email, "         //14
                    + "statutory, "     //15
                    + "charter, "       //16
                    + "fiscalYearEnding, "  //17
                    + "lastNotification, "  //18
                    + "activityLastFiled, " //19
                    + "previousFileDate, "  //20
                    + "parent1, "       //21
                    + "parent2, "       //22
                    + "dueDate, "       //23
                    + "filed, "         //24
                    + "valid, "         //25
                    + "note, "          //26
                    + "alsoknownas, "   //27
                    + "county "         //28
                    + ") VALUES (";
                    for(int i=0; i<27; i++){
                        sql += "?, ";   //01-27
                    }
                     sql += "?)";   //28
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, item.getName());
            ps.setString ( 3, item.getType());
            ps.setString ( 4, item.getCscNumber());
            ps.setString ( 5, item.getCscEmployerID());
            ps.setString ( 6, item.getAddress1());
            ps.setString ( 7, item.getAddress2());
            ps.setString ( 8, item.getCity());
            ps.setString ( 9, item.getState());
            ps.setString (10, item.getZipCode());
            ps.setString (11, item.getPhone1());
            ps.setString (12, item.getPhone2());
            ps.setString (13, item.getFax());
            ps.setString (14, item.getEmail());
            ps.setBoolean(15, item.isStatutory());
            ps.setString (16, item.getCharter());
            ps.setString (17, item.getFiscalYearEnding());
            ps.setString (18, item.getLastNotification());
            ps.setDate   (19, item.getActivityLastFiled());
            ps.setDate   (20, item.getPreviousFileDate());
            ps.setString (21, item.getParent1());
            ps.setString (22, item.getParent2());
            ps.setString (23, item.getDueDate());
            ps.setDate   (24, item.getFiled());
            ps.setBoolean(25, item.isValid());
            ps.setString (26, item.getNote());
            ps.setString (27, item.getAlsoknownas());
            ps.setString (28, item.getCounty());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
