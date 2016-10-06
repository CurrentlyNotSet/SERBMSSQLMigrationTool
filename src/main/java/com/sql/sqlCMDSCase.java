/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSCaseModel;
import com.model.oldCMDSCaseModel;
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
public class sqlCMDSCase {
    
    public static List<oldCMDSCaseModel> getCase() {
        List<oldCMDSCaseModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [Case].*, CaseNotes.CaseNote, CaseNotes.InventoryStatusNote, "
                    + "CaseNotes.OutsideCourtNote FROM [Case] LEFT JOIN "
                    + "CaseNotes ON [case].[year] = CaseNotes.[year] AND "
                    + "[case].[CaseSeqNumber] = CaseNotes.[CaseSeqNumber]";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCMDSCaseModel item = new oldCMDSCaseModel();

                item.setCaseID(rs.getInt("CaseID"));
                item.setActive(rs.getInt("Active"));
                item.setYear(rs.getString("Year"));
                item.setCaseSeqNumber(rs.getString("CaseSeqNumber"));
                item.setMonth(rs.getString("Month"));
                item.setType(rs.getString("Type"));
                item.setGroupNumber(rs.getString("GroupNumber").equals("00000000") ? "" : rs.getString("GroupNumber"));
                item.setGroupType(rs.getString("GroupType"));
                item.setALJ(rs.getString("ALJ"));
                item.setMediator(rs.getString("Mediator"));
                item.setOpenDate(rs.getString("OpenDate").length() < 10 ? "" : rs.getString("OpenDate").substring(0, 10));
                item.setCloseDate(rs.getString("CloseDate").length() < 10 ? "" : rs.getString("CloseDate").substring(0, 10));
                item.setGreenCardSignedRR(rs.getString("GreenCardSignedRR"));
                item.setGreenCardSignedPO(rs.getString("GreenCardSignedPO"));
                item.setGreenCardSignedBO(rs.getString("GreenCardSignedBO"));
                item.setPullDateRR(rs.getString("PullDateRR"));
                item.setPullDatePO(rs.getString("PullDatePO"));
                item.setPullDateBO(rs.getString("PullDateBO"));
                item.setMailedRR(rs.getString("MailedRR"));
                item.setMailedPO(rs.getString("MailedPO"));
                item.setMailedBO(rs.getString("MailedBO"));
                item.setRemailedRR(rs.getString("RemailedRR"));
                item.setRemailedPO(rs.getString("RemailedPO"));
                item.setRemailedBO(rs.getString("RemailedBO"));
                item.setReclassCode(rs.getString("ReclassCode"));
                item.setStatus(rs.getString("Status"));
                item.setScheduleType(rs.getString("ScheduleType"));
                item.setNumberofTapes(rs.getString("NumberofTapes"));
                item.setInventoryStatusLine(rs.getString("InventoryStatusLine"));
                item.setInventoryStatusDate(rs.getString("InventoryStatusDate").length() < 10 ? "" : rs.getString("InventoryStatusDate").substring(0, 10));
                item.setResult(rs.getString("Result"));
                item.setJobClassification(rs.getString("JobClassification"));
                item.setPBRBoxNumber(rs.getString("PBRBoxNumber"));
                item.setHearingCompletedDate(rs.getString("HearingCompletedDate"));
                item.setPostHearingBriefsDueDate(rs.getString("PostHearingBriefsDueDate"));
                item.setMailedPO2(rs.getString("MailedPO2"));
                item.setMailedPO3(rs.getString("MailedPO3"));
                item.setMailedPO4(rs.getString("MailedPO4"));
                item.setRemailedPO2(rs.getString("RemailedPO2"));
                item.setRemailedPO3(rs.getString("RemailedPO3"));
                item.setRemailedPO4(rs.getString("RemailedPO4"));
                item.setGreenCardSignedPO2(rs.getString("GreenCardSignedPO2"));
                item.setGreenCardSignedPO3(rs.getString("GreenCardSignedPO3"));
                item.setGreenCardSignedPO4(rs.getString("GreenCardSignedPO4"));
                item.setPullDatePO2(rs.getString("PullDatePO2"));
                item.setPullDatePO3(rs.getString("PullDatePO3"));
                item.setPullDatePO4(rs.getString("PullDatePO4"));
                item.setGroupFlag(rs.getString("GroupFlag"));
                item.setCaseNote(rs.getString("CaseNote") == null ? "" : rs.getString("CaseNote"));
                item.setInventoryStatusNote(rs.getString("InventoryStatusNote") == null ? "" : rs.getString("InventoryStatusNote"));
                item.setOutsideCourtNote(rs.getString("OutsideCourtNote") == null ? "" : rs.getString("OutsideCourtNote"));
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
    
    public static void addCase(CMDSCaseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSCase("
                    + "active, "        //01
                    + "caseYear, "      //02
                    + "caseType, "      //03
                    + "caseMonth, "     //04
                    + "caseNumber, "    //05
                    + "note, "          //06
                    + "openDate, "      //07
                    + "groupNumber, "   //08
                    + "aljID, "         //09
                    + "closeDate, "     //10
                    + "inventoryStatusLine, "//11
                    + "inventoryStatusDate, "//12
                    + "caseStatus, "    //13
                    + "result, "        //14
                    + "mediatorID, "    //15
                    + "pbrBox, "        //16
                    + "groupType, "     //17
                    + "reclassCode, "   //18
                    + "mailedRR, "      //19
                    + "mailedBO, "      //20
                    + "mailedPO1, "     //21
                    + "mailedPO2, "     //22
                    + "mailedPO3, "     //23
                    + "mailedPO4, "     //24
                    + "remailedRR, "    //25
                    + "remailedBO, "    //26
                    + "remailedPO1, "   //27
                    + "remailedPO2, "   //28
                    + "remailedPO3, "   //29
                    + "remailedPO4, "   //30
                    + "returnReceiptRR, "   //31
                    + "returnReceiptBO, "   //32
                    + "returnReceiptPO1, "  //33
                    + "returnReceiptPO2, "  //34
                    + "returnReceiptPO3, "  //35
                    + "returnReceiptPO4, "  //36
                    + "pullDateRR, "    //37
                    + "pullDateBO, "    //38
                    + "pullDatePO1, "   //39
                    + "pullDatePO2, "   //40
                    + "pullDatePO3, "   //41
                    + "pullDatePO4, "   //42
                    + "hearingCompletedDate, "  //43
                    + "postHearingDriefsDue "   //44
                    + ") VALUES (";
                    for(int i=0; i<43; i++){
                        sql += "?, ";   //01-43
                    }
                     sql += "?)";   //44
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, item.getCaseYear());
            ps.setString ( 3, item.getCaseType());
            ps.setString ( 4, item.getCaseMonth());
            ps.setString ( 5, item.getCaseNumber());
            ps.setString ( 6, item.getNote().trim().equals("") ? null : item.getNote());
            ps.setDate   ( 7, item.getOpenDate());
            ps.setString ( 8, item.getGroupNumber());
            ps.setString ( 9, item.getAljID().equals("0") ? null : item.getAljID());
            ps.setDate   (10, item.getCloseDate());
            ps.setString (11, item.getInventoryStatusLine());
            ps.setDate   (12, item.getInventoryStatusDate());
            ps.setString (13, item.getCaseStatus());
            ps.setString (14, item.getResult());
            ps.setString (15, item.getMediatorID());
            ps.setString (16, item.getPbrBox());
            ps.setString (17, item.getGroupType());
            ps.setString (18, item.getReclassCode());
            ps.setDate   (19, item.getMailedRR());
            ps.setDate   (20, item.getMailedBO());
            ps.setDate   (21, item.getMailedPO1());
            ps.setDate   (22, item.getMailedPO2());
            ps.setDate   (23, item.getMailedPO3());
            ps.setDate   (24, item.getMailedPO4());
            ps.setDate   (25, item.getRemailedRR());
            ps.setDate   (26, item.getRemailedBO());
            ps.setDate   (27, item.getRemailedPO1());
            ps.setDate   (28, item.getRemailedPO2());
            ps.setDate   (29, item.getRemailedPO3());
            ps.setDate   (30, item.getRemailedPO4());
            ps.setDate   (31, item.getReturnReceiptRR());
            ps.setDate   (32, item.getReturnReceiptBO());
            ps.setDate   (33, item.getReturnReceiptPO1());
            ps.setDate   (34, item.getReturnReceiptPO2());
            ps.setDate   (35, item.getReturnReceiptPO3());
            ps.setDate   (36, item.getReturnReceiptPO4());
            ps.setDate   (37, item.getPullDateRR());
            ps.setDate   (38, item.getPullDateBO());
            ps.setDate   (39, item.getPullDatePO1());
            ps.setDate   (40, item.getPullDatePO2());
            ps.setDate   (41, item.getPullDatePO3());
            ps.setDate   (42, item.getPullDatePO4());
            ps.setDate   (43, item.getHearingCompletedDate());
            ps.setDate   (44, item.getPostHearingDriefsDue());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
