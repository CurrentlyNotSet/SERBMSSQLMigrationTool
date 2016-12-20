/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.HearingsCaseModel;
import com.model.oldSMDSCaseTrackingModel;
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
public class sqlHearingsCase {
    
    public static List<oldSMDSCaseTrackingModel> getCases() {
        List<oldSMDSCaseTrackingModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM SMDSCaseTracking ORDER BY CaseNumber";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldSMDSCaseTrackingModel item = new oldSMDSCaseTrackingModel();
                item.setSmdsCaseTrackingid(rs.getInt("SmdsCaseTrackingid"));
                item.setActive(rs.getInt("Active"));
                item.setCaseNumber(rs.getString("CaseNumber") != null ? rs.getString("CaseNumber") : "");
                item.setOpenYN(rs.getString("OpenYN") != null ? rs.getString("OpenYN") : "");
                item.setCaseType(rs.getString("CaseType") != null ? rs.getString("CaseType") : "");
                item.setBoardActionPCDate(rs.getString("BoardActionPCDate") != null ? rs.getString("BoardActionPCDate") : "");
                item.setBrdActionPreDDate(rs.getString("BrdActionPreDDate") != null ? rs.getString("BrdActionPreDDate") : "");
                item.setCompanionCaseNumber1(rs.getString("CompanionCaseNumber1") != null ? rs.getString("CompanionCaseNumber1") : "");
                item.setCompanionCaseNumber2(rs.getString("CompanionCaseNumber2") != null ? rs.getString("CompanionCaseNumber2") : "");
                item.setCompanionCaseNumber3(rs.getString("CompanionCaseNumber3") != null ? rs.getString("CompanionCaseNumber3") : "");
                item.setCompanionCaseNumber4(rs.getString("CompanionCaseNumber4") != null ? rs.getString("CompanionCaseNumber4") : "");
                item.setCompanionCaseNumber5(rs.getString("CompanionCaseNumber5") != null ? rs.getString("CompanionCaseNumber5") : "");
                item.setDirectiveIssuedDate(rs.getString("DirectiveIssuedDate") != null ? rs.getString("DirectiveIssuedDate") : "");
                item.setExpeditedYN(rs.getString("ExpeditedYN") != null ? rs.getString("ExpeditedYN") : "");
                item.setMediator(rs.getString("Mediator") != null ? rs.getString("Mediator") : "");
                item.setMediationDate1(rs.getString("MediationDate1") != null ? rs.getString("MediationDate1") : "");
                item.setMediationDate2(rs.getString("MediationDate2") != null ? rs.getString("MediationDate2") : "");
                item.setMediationDate3(rs.getString("MediationDate3") != null ? rs.getString("MediationDate3") : "");
                item.setMediationAssignedDate(rs.getString("MediationAssignedDate") != null ? rs.getString("MediationAssignedDate") : "");
                item.setMediationFailedDate(rs.getString("MediationFailedDate") != null ? rs.getString("MediationFailedDate") : "");
                item.setMediationDeadlineDate(rs.getString("MediationDeadlineDate") != null ? rs.getString("MediationDeadlineDate") : "");
                item.setOtherAction(rs.getString("OtherAction") != null ? rs.getString("OtherAction") : "");
                item.setComplaintDueDate(rs.getString("ComplaintDueDate") != null ? rs.getString("ComplaintDueDate") : "");
                item.setDraftComplaintToHearingDate(rs.getString("DraftComplaintToHearingDate") != null ? rs.getString("DraftComplaintToHearingDate") : "");
                item.setALJid(rs.getInt("ALJid"));
                item.setALJInitials(rs.getString("ALJInitials") != null ? rs.getString("ALJInitials") : "");
                item.setOpinion(rs.getString("Opinion") != null ? rs.getString("Opinion") : "");
                item.setComplaintIssuedDate(rs.getString("ComplaintIssuedDate") != null ? rs.getString("ComplaintIssuedDate") : "");
                item.setPreHearingDate(rs.getString("PreHearingDate") != null ? rs.getString("PreHearingDate") : "");
                item.setHearingDate(rs.getString("HearingDate") != null ? rs.getString("HearingDate") : "");
                item.setProposedRecDueDate(rs.getString("ProposedRecDueDate") != null ? rs.getString("ProposedRecDueDate") : "");
                item.setProposedRecIssuedDate(rs.getString("ProposedRecIssuedDate") != null ? rs.getString("ProposedRecIssuedDate") : "");
                item.setBoardActionDate(rs.getString("BoardActionDate") != null ? rs.getString("BoardActionDate") : "");
                item.setIssuanceOfOptionOrDirectiveDate(rs.getString("IssuanceOfOptionOrDirectiveDate") != null ? rs.getString("IssuanceOfOptionOrDirectiveDate") : "");
                item.setComments(rs.getString("Comments") != null ? rs.getString("Comments") : "");
                item.setExceptionsFilingDate(rs.getString("ExceptionsFilingDate") != null ? rs.getString("ExceptionsFilingDate") : "");
                item.setResponceFilingDate(rs.getString("ResponceFilingDate") != null ? rs.getString("ResponceFilingDate") : "");
                item.setFinalResult(rs.getString("FinalResult") != null ? rs.getString("FinalResult") : "");
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
        
    public static void importOldHearingsCase(HearingsCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO HearingCase ("
                    + "caseYear, "          //01
                    + "caseType, "          //02
                    + "caseMonth, "         //03
                    + "caseNumber, "        //04
                    + "openClose, "         //05
                    + "expedited, "         //06
                    + "boardActionPCDate, " //07
                    + "boardActionPreDDate, "        //08
                    + "directiveIssueDate, "         //09
                    + "complaintDueDate, "           //10
                    + "draftComplaintToHearingDate, "//11
                    + "preHearingDate, "        //12
                    + "proposedRecDueDate, "    //13
                    + "exceptionFilingDate, "   //14
                    + "boardActionDate, "       //15
                    + "otherAction, "           //16
                    + "aljID, "                 //17
                    + "complaintIssuedDate, "   //18
                    + "hearingDate, "           //19
                    + "proposedRecIssuedDate, " //20
                    + "responseFilingDate, "    //21
                    + "issuanceOfOptionOrDirectiveDate, " //22
                    + "finalResult, "           //23
                    + "opinion, "               //24
                    + "companionCases, "        //25
                    + "caseStatusNotes "        //26
                    + ") VALUES (";
                    for(int i=0; i<25; i++){
                        sql += "?, ";   //01-25
                    }
                     sql += "?)";   //26
            ps = conn.prepareStatement(sql);
            ps.setString ( 1, item.getCaseYear());
            ps.setString ( 2, item.getCaseType());
            ps.setString ( 3, item.getCaseMonth());
            ps.setString ( 4, item.getCaseNumber());
            ps.setString ( 5, item.getOpenClose());
            ps.setBoolean( 6, item.isExpedited());
            ps.setDate   ( 7, item.getBoardActionPCDate());
            ps.setDate   ( 8, item.getBoardActionPreDDate());
            ps.setDate   ( 9, item.getDirectiveIssueDate());
            ps.setDate   (10, item.getComplaintDueDate());
            ps.setDate   (11, item.getDraftComplaintToHearingDate());
            ps.setDate   (12, item.getPreHearingDate());
            ps.setDate   (13, item.getProposedRecDueDate());
            ps.setDate   (14, item.getExceptionFilingDate());
            ps.setDate   (15, item.getBoardActionDate());
            ps.setString (16, item.getOtherAction());
            if (item.getAljID() != 0){
                ps.setInt  (17, item.getAljID());
            } else {
                ps.setNull (17, java.sql.Types.INTEGER);
            }
            ps.setDate   (18, item.getComplaintIssuedDate());
            ps.setDate   (19, item.getHearingDate());
            ps.setDate   (20, item.getProposedRecIssuedDate());
            ps.setDate   (21, item.getResponseFilingDate());
            ps.setDate   (22, item.getIssuanceOfOptionOrDirectiveDate());
            ps.setString (23, item.getFinalResult());
            ps.setDate   (24, item.getOpinion());
            ps.setString (25, item.getCompanionCases());
            ps.setString (26, item.getCaseStatusNotes());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
