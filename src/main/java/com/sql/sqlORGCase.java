/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.ORGCaseModel;
import com.model.oldEmployeeOrgModel;
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
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
        
    public static void importOldEmployeeOrgCase(ORGCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO ORGCase ("
                    + "active, "
                    + "orgName, "
                    + "orgNumber, "
                    + "fiscalYearEnding, "
                    + "filingDueDate, "
                    + "annualReport, "
                    + "financialReport, "
                    + "registrationReport, "
                    + "constructionAndByLaws, "
                    + "filedByParent, "
                    + "note"
                    + ") VALUES (";
                    for(int i=0; i<10; i++){
                        sql += "?, ";   //01-10
                    }
                     sql += "?)";   //11
            ps = conn.prepareStatement(sql);
            ps.setInt    ( 1, item.getActive());
            ps.setString ( 2, item.getOrgName());
            ps.setString ( 3, item.getOrgNumber());
            ps.setString ( 4, item.getFiscalYearEnding());
            ps.setString ( 5, item.getFilingDueDate());
            ps.setDate   ( 6, item.getAnnualReport());
            ps.setDate   ( 7, item.getFinancialReport());
            ps.setDate   ( 8, item.getRegistrationReport());
            ps.setDate   ( 9, item.getConstructionAndByLaws());
            ps.setBoolean(10, item.isFiledByParent());
            ps.setString (11, item.getNote());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
