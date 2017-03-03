/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ORGCaseModel;
import com.model.oldEmployeeOrgModel;
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
public class sqlORGCase {

    public static List<oldEmployeeOrgModel> getCases() {
        List<oldEmployeeOrgModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM EmployeeOrg ORDER BY EmployeeOrgid";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldEmployeeOrgModel item = new oldEmployeeOrgModel();
                item.setEmployeeOrgid(rs.getInt("EmployeeOrgid"));
                item.setActive(rs.getByte("Active"));
                item.setOrgName(rs.getString("OrgName") != null ? rs.getString("OrgName") : "");
                item.setOrgType(rs.getString("OrgType") != null ? rs.getString("OrgType") : "");
                item.setOrgNumber(rs.getString("OrgNumber") != null ? rs.getString("OrgNumber") : "");
                item.setOrgEmployerid(rs.getString("OrgEmployerid") != null ? rs.getString("OrgEmployerid") : "");
                item.setOrgAddress1(rs.getString("OrgAddress1") != null ? rs.getString("OrgAddress1") : "");
                item.setOrgAddress2(rs.getString("OrgAddress2") != null ? rs.getString("OrgAddress2") : "");
                item.setOrgCity(rs.getString("OrgCity") != null ? rs.getString("OrgCity") : "");
                item.setOrgCounty(rs.getString("OrgCounty") != null ? rs.getString("OrgCounty") : "");
                item.setOrgState(rs.getString("OrgState") != null ? rs.getString("OrgState") : "");
                item.setOrgZipPlusFive(rs.getString("OrgZipPlusFive") != null ? rs.getString("OrgZipPlusFive") : "");
                item.setOrgPhone1(rs.getString("OrgPhone1") != null ? rs.getString("OrgPhone1") : "");
                item.setOrgPhone2(rs.getString("OrgPhone2") != null ? rs.getString("OrgPhone2") : "");
                item.setOrgFax(rs.getString("OrgFax") != null ? rs.getString("OrgFax") : "");
                item.setOrgEMail(rs.getString("OrgEMail") != null ? rs.getString("OrgEMail") : "");
                item.setRepFirstName(rs.getString("RepFirstName") != null ? rs.getString("RepFirstName") : "");
                item.setRepMiddleInitial(rs.getString("RepMiddleInitial") != null ? rs.getString("RepMiddleInitial") : "");
                item.setRepLastName(rs.getString("RepLastName") != null ? rs.getString("RepLastName") : "");
                item.setRepAddress1(rs.getString("RepAddress1") != null ? rs.getString("RepAddress1") : "");
                item.setRepAddress2(rs.getString("RepAddress2") != null ? rs.getString("RepAddress2") : "");
                item.setRepCity(rs.getString("RepCity") != null ? rs.getString("RepCity") : "");
                item.setRepState(rs.getString("RepState") != null ? rs.getString("RepState") : "");
                item.setRepZipPlusFive(rs.getString("RepZipPlusFive") != null ? rs.getString("RepZipPlusFive") : "");
                item.setRepPhone1(rs.getString("RepPhone1") != null ? rs.getString("RepPhone1") : "");
                item.setRepPhone2(rs.getString("RepPhone2") != null ? rs.getString("RepPhone2") : "");
                item.setRepFax(rs.getString("RepFax") != null ? rs.getString("RepFax") : "");
                item.setRepEMail(rs.getString("RepEMail") != null ? rs.getString("RepEMail") : "");
                item.setOfficer1(rs.getString("Officer1") != null ? rs.getString("Officer1") : "");
                item.setOfficer1Title(rs.getString("Officer1Title") != null ? rs.getString("Officer1Title") : "");
                item.setOfficer2(rs.getString("Officer2") != null ? rs.getString("Officer2") : "");
                item.setOfficer2Title(rs.getString("Officer2Title") != null ? rs.getString("Officer2Title") : "");
                item.setOfficer3(rs.getString("Officer3") != null ? rs.getString("Officer3") : "");
                item.setOfficer3Title(rs.getString("Officer3Title") != null ? rs.getString("Officer3Title") : "");
                item.setOfficer4(rs.getString("Officer4") != null ? rs.getString("Officer4") : "");
                item.setOfficer4Title(rs.getString("Officer4Title") != null ? rs.getString("Officer4Title") : "");
                item.setBoardCertified(rs.getString("BoardCertified") != null ? rs.getString("BoardCertified") : "");
                item.setDeemedCertified(rs.getString("DeemedCertified") != null ? rs.getString("DeemedCertified") : "");
                item.setFiscalYearEnding(rs.getString("FiscalYearEnding") != null ? rs.getString("FiscalYearEnding") : "");
                item.setLastNotification(rs.getString("LastNotification") != null ? rs.getString("LastNotification") : "");
                item.setAnnualReportLastFiled(rs.getString("AnnualReportLastFiled") != null ? rs.getString("AnnualReportLastFiled") : "");
                item.setFinancialStatementLastFiled(rs.getString("FinancialStatementLastFiled") != null ? rs.getString("FinancialStatementLastFiled") : "");
                item.setRegistrationReportLastFiled(rs.getString("RegistrationReportLastFiled") != null ? rs.getString("RegistrationReportLastFiled") : "");
                item.setConstitutionAndBylawsFiled(rs.getString("ConstitutionAndBylawsFiled") != null ? rs.getString("ConstitutionAndBylawsFiled") : "");
                item.setPreviousFilings(rs.getString("PreviousFilings") != null ? rs.getString("PreviousFilings") : "");
                item.setInitiationFee(rs.getString("InitiationFee") != null ? rs.getString("InitiationFee") : "");
                item.setMonthlyFee(rs.getString("MonthlyFee") != null ? rs.getString("MonthlyFee") : "");
                item.setCase1(rs.getString("Case1") != null ? rs.getString("Case1") : "");
                item.setDescription1(rs.getString("Description1") != null ? rs.getString("Description1") : "");
                item.setDescription2(rs.getString("Description2") != null ? rs.getString("Description2") : "");
                item.setParent1(rs.getString("Parent1") != null ? rs.getString("Parent1") : "");
                item.setParent2(rs.getString("Parent2") != null ? rs.getString("Parent2") : "");
                item.setDueDate(rs.getString("DueDate") != null ? rs.getString("DueDate") : "");
                item.setFiled(rs.getString("Filed") != null ? rs.getString("Filed") : "");
                item.setValid(rs.getString("Valid") != null ? rs.getString("Valid") : "");
                item.setFiledByParent(rs.getString("FiledByParent") != null ? rs.getString("FiledByParent") : "");
                item.setCertifiedDate(rs.getString("CertifiedDate") != null ? rs.getString("CertifiedDate") : "");
                item.setRegistrationSentDate(rs.getString("RegistrationSentDate") != null ? rs.getString("RegistrationSentDate") : "");
                item.setExtensionDate(rs.getString("ExtensionDate") != null ? rs.getString("ExtensionDate") : "");
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

    public static void batchAddEmployeeOrgCase(List<ORGCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO ORGCase ("
                    + "active, "        //01
                    + "orgName, "       //02
                    + "orgNumber, "     //03
                    + "fiscalYearEnding, "//04
                    + "filingDueDate, " //05
                    + "annualReport, "  //06
                    + "financialReport, "//07
                    + "registrationReport, "//08
                    + "constructionAndByLaws, "//09
                    + "filedByParent, " //10
                    + "note, "          //11
                    + "alsoKnownAs, "   //12
                    + "orgType, "       //13
                    + "orgPhone1, "     //14
                    + "orgPhone2, "     //15
                    + "orgFax, "        //16
                    + "employerID, "    //17
                    + "orgAddress1, "   //18
                    + "orgAddress2, "   //19
                    + "orgCity, "       //20
                    + "orgState, "      //21
                    + "orgZip, "        //22
                    + "orgCounty, "     //23
                    + "orgEmail, "      //24
                    + "lastNotification, "//25
                    + "deemedCertified, " //26
                    + "boardCertified, "  //27
                    + "valid, "         //28
                    + "parent1, "       //29
                    + "parent2, "       //30
                    + "outsideCase, "   //31
                    + "dateFiled, "     //32
                    + "registrationLetterSent, "//33
                    + "extensionDate "  //34
                    + ") VALUES (";
                    for(int i=0; i<33; i++){
                        sql += "?, ";   //01-33
                    }
                     sql += "?)";   //34
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (ORGCaseModel item : list) {
                ps.setInt    ( 1, item.getActive());
                ps.setString ( 2, StringUtils.left(item.getOrgName(), 500));
                ps.setString ( 3, StringUtils.left(item.getOrgNumber(), 10));
                ps.setString ( 4, StringUtils.left(item.getFiscalYearEnding(), 20));
                ps.setString ( 5, StringUtils.left(item.getFilingDueDate(), 20));
                ps.setDate   ( 6, item.getAnnualReport());
                ps.setDate   ( 7, item.getFinancialReport());
                ps.setDate   ( 8, item.getRegistrationReport());
                ps.setDate   ( 9, item.getConstructionAndByLaws());
                ps.setBoolean(10, item.isFiledByParent());
                ps.setString (11, item.getNote());
                ps.setString (12, StringUtils.left(item.getAlsoKnownAs().trim().equals("") ? null : item.getAlsoKnownAs(), 500));
                ps.setString (13, StringUtils.left(item.getOrgType().trim().equals("") ? null : item.getOrgType(), 50));
                ps.setString (14, StringUtils.left(item.getOrgPhone1().trim().equals("") ? null : item.getOrgPhone1(), 15));
                ps.setString (15, StringUtils.left(item.getOrgPhone2().trim().equals("") ? null : item.getOrgPhone2(), 15));
                ps.setString (16, StringUtils.left(item.getOrgFax().trim().equals("") ? null : item.getOrgFax(), 15));
                ps.setString (17, StringUtils.left(item.getEmployerID().trim().equals("") ? null : item.getEmployerID(), 10));
                ps.setString (18, StringUtils.left(item.getOrgAddress1().trim().equals("") ? null : item.getOrgAddress1(), 255));
                ps.setString (19, StringUtils.left(item.getOrgAddress2().trim().equals("") ? null : item.getOrgAddress2(), 255));
                ps.setString (20, StringUtils.left(item.getOrgCity().trim().equals("") ? null : item.getOrgCity(), 150));
                ps.setString (21, StringUtils.left(item.getOrgState().trim().equals("") ? null : item.getOrgState(), 50));
                ps.setString (22, StringUtils.left(item.getOrgZip().trim().equals("") ? null : item.getOrgZip(), 10));
                ps.setString (23, StringUtils.left(item.getOrgCounty().trim().equals("") ? null : item.getOrgCounty(), 100));
                ps.setString (24, StringUtils.left(item.getOrgEmail().trim().equals("") ? null : item.getOrgEmail(), 255));
                ps.setString (25, StringUtils.left(item.getLastNotification().trim().equals("") ? null : item.getLastNotification(), 50));
                ps.setBoolean(26, item.isDeemedCertified());
                ps.setBoolean(27, item.isBoardCertified());
                ps.setBoolean(28, item.isValid());
                ps.setString (29, StringUtils.left(item.getParent1().trim().equals("") ? null : item.getParent1(), 255));
                ps.setString (30, StringUtils.left(item.getParent2().trim().equals("") ? null : item.getParent2(), 255));
                ps.setString (31, StringUtils.left(item.getOutsideCase().trim().equals("") ? null : item.getOutsideCase(), 100));
                ps.setDate   (32, item.getDateFiled());
                ps.setDate   (33, item.getRegistrationLetterSent());
                ps.setDate   (34, item.getExtensionDate());
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
