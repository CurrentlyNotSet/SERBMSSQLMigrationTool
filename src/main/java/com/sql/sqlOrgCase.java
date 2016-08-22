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
public class sqlOrgCase {
    
    public static List<oldEmployeeOrgModel> getCases() {
        List<oldEmployeeOrgModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM EmployeeOrg";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldEmployeeOrgModel item = new oldEmployeeOrgModel();
                item.setEmployeeOrgid(rs.getInt("EmployeeOrgid"));
                item.setActive(rs.getByte("Active"));
                item.setOrgName(rs.getString("OrgName"));
                item.setOrgType(rs.getString("OrgType"));
                item.setOrgNumber(rs.getString("OrgNumber"));
                item.setOrgEmployerid(rs.getString("OrgEmployerid"));
                item.setOrgAddress1(rs.getString("OrgAddress1"));
                item.setOrgAddress2(rs.getString("OrgAddress2"));
                item.setOrgCity(rs.getString("OrgCity"));
                item.setOrgCounty(rs.getString("OrgCounty"));
                item.setOrgState(rs.getString("OrgState"));
                item.setOrgZipPlusFive(rs.getString("OrgZipPlusFive"));
                item.setOrgPhone1(rs.getString("OrgPhone1"));
                item.setOrgPhone2(rs.getString("OrgPhone2"));
                item.setOrgFax(rs.getString("OrgFax"));
                item.setOrgEMail(rs.getString("OrgEMail"));
                item.setRepFirstName(rs.getString("RepFirstName"));
                item.setRepMiddleInitial(rs.getString("RepMiddleInitial"));
                item.setRepLastName(rs.getString("RepLastName"));
                item.setRepAddress1(rs.getString("RepAddress1"));
                item.setRepAddress2(rs.getString("RepAddress2"));
                item.setRepCity(rs.getString("RepCity"));
                item.setRepState(rs.getString("RepState"));
                item.setRepZipPlusFive(rs.getString("RepZipPlusFive"));
                item.setRepPhone1(rs.getString("RepPhone1"));
                item.setRepPhone2(rs.getString("RepPhone2"));
                item.setRepFax(rs.getString("RepFax"));
                item.setRepEMail(rs.getString("RepEMail"));
                item.setOfficer1(rs.getString("Officer1"));
                item.setOfficer1Title(rs.getString("Officer1Title"));
                item.setOfficer2(rs.getString("Officer2"));
                item.setOfficer2Title(rs.getString("Officer2Title"));
                item.setOfficer3(rs.getString("Officer3"));
                item.setOfficer3Title(rs.getString("Officer3Title"));
                item.setOfficer4(rs.getString("Officer4"));
                item.setOfficer4Title(rs.getString("Officer4Title"));
                item.setBoardCertified(rs.getString("BoardCertified"));
                item.setDeemedCertified(rs.getString("DeemedCertified"));
                item.setFiscalYearEnding(rs.getString("FiscalYearEnding"));
                item.setLastNotification(rs.getString("LastNotification"));
                item.setAnnualReportLastFiled(rs.getString("AnnualReportLastFiled"));
                item.setFinancialStatementLastFiled(rs.getString("FinancialStatementLastFiled"));
                item.setRegistrationReportLastFiled(rs.getString("RegistrationReportLastFiled"));
                item.setConstitutionAndBylawsFiled(rs.getString("ConstitutionAndBylawsFiled"));
                item.setPreviousFilings(rs.getString("PreviousFilings"));
                item.setInitiationFee(rs.getString("InitiationFee"));
                item.setMonthlyFee(rs.getString("MonthlyFee"));
                item.setCase1(rs.getString("Case1"));
                item.setDescription1(rs.getString("Description1"));
                item.setDescription2(rs.getString("Description2"));
                item.setParent1(rs.getString("Parent1"));
                item.setParent2(rs.getString("Parent2"));
                item.setDueDate(rs.getString("DueDate"));
                item.setFiled(rs.getString("Filed"));
                item.setValid(rs.getString("Valid"));
                item.setFiledByParent(rs.getString("FiledByParent"));
                item.setCertifiedDate(rs.getString("CertifiedDate"));
                item.setRegistrationSentDate(rs.getString("RegistrationSentDate"));
                item.setExtensionDate(rs.getString("ExtensionDate"));
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
                    + "filedByParent"
                    + ") VALUES (";
                    for(int i=0; i<9; i++){
                        sql += "?, ";   //01-09
                    }
                     sql += "?)";   //10
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
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
