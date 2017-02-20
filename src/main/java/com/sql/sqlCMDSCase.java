/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSCaseModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
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
public class sqlCMDSCase {

    public static List<CMDSCaseModel> getCaseList() {
        List<CMDSCaseModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [Case].*, CaseNotes.CaseNote, CaseNotes.InventoryStatusNote, ALJ.Username AS ALJUser, "
                    + "med.Username AS MedUser, CaseNotes.OutsideCourtNote FROM [Case] "
                    + "LEFT JOIN ALJ ON ALJ.Initials = [case].ALJ "
                    + "LEFT JOIN ALJ as med ON med.Initials = [case].Mediator "
                    + "LEFT JOIN CaseNotes ON [case].[year] = CaseNotes.[year] AND "
                    + "[case].[CaseSeqNumber] = CaseNotes.[CaseSeqNumber] ORDER BY [Case].Year ASC, [Case].CaseSeqNumber ASC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSCaseModel cmds = new CMDSCaseModel();
                cmds.setId(rs.getInt("CaseID"));
                cmds.setActive(rs.getInt("Active") == 1);
                cmds.setCaseYear(rs.getString("Year"));
                cmds.setCaseType(rs.getString("Type"));
                cmds.setCaseMonth(rs.getString("Month"));
                cmds.setCaseNumber(rs.getString("CaseSeqNumber"));
                cmds.setNote("");
                cmds.setOpenDate(rs.getString("OpenDate").length() < 10
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("OpenDate").substring(0,9)));
                cmds.setGroupNumber(rs.getString("GroupNumber").equals("00000000") ? null : rs.getString("GroupNumber").trim());
                cmds.setCloseDate(rs.getString("CloseDate").length() < 10
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("CloseDate").substring(0,9)));
                cmds.setInventoryStatusLine(rs.getString("InventoryStatusLine").equals("") ? null : rs.getString("InventoryStatusLine").trim());
                cmds.setInventoryStatusDate(rs.getString("InventoryStatusDate").length() < 10
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("InventoryStatusDate").substring(0,9)));
                cmds.setCaseStatus(rs.getString("Status").equals("") ? null : rs.getString("Status").trim());
                cmds.setResult(rs.getString("Result").equals("") ? null : rs.getString("Result").trim());
                cmds.setPbrBox(rs.getString("PBRBoxNumber").equals("") ? null : rs.getString("PBRBoxNumber").trim());
                cmds.setGroupType(rs.getString("GroupType").trim().equals("") ? null : rs.getString("GroupType").trim());
                cmds.setReclassCode(rs.getString("ReclassCode").trim().equals("") ? null : rs.getString("ReclassCode").trim());
                cmds.setMailedRR(rs.getString("MailedRR").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedRR").substring(0,9)));
                cmds.setMailedBO(rs.getString("MailedBO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedBO").substring(0,9)));
                cmds.setMailedPO1(rs.getString("MailedPO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedPO").substring(0,9)));
                cmds.setMailedPO2(rs.getString("MailedPO2").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedPO2").substring(0,9)));
                cmds.setMailedPO3(rs.getString("MailedPO3").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedPO3").substring(0,9)));
                cmds.setMailedPO4(rs.getString("MailedPO4").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("MailedPO4").substring(0,9)));
                cmds.setRemailedRR(rs.getString("RemailedRR").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedRR").substring(0,9)));
                cmds.setRemailedBO(rs.getString("RemailedBO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedbO").substring(0,9)));
                cmds.setRemailedPO1(rs.getString("RemailedPO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedPO").substring(0,9)));
                cmds.setRemailedPO2(rs.getString("RemailedPO2").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedPO2").substring(0,9)));
                cmds.setRemailedPO3(rs.getString("RemailedPO3").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedPO3").substring(0,9)));
                cmds.setRemailedPO4(rs.getString("RemailedPO4").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("RemailedPO4").substring(0,9)));
                cmds.setReturnReceiptRR(rs.getString("GreenCardSignedRR").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedRR").substring(0,9)));
                cmds.setReturnReceiptBO(rs.getString("GreenCardSignedBO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedBO").substring(0,9)));
                cmds.setReturnReceiptPO1(rs.getString("GreenCardSignedPO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedPO").substring(0,9)));
                cmds.setReturnReceiptPO2(rs.getString("GreenCardSignedPO2").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedPO2").substring(0,9)));
                cmds.setReturnReceiptPO3(rs.getString("GreenCardSignedPO3").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedPO3").substring(0,9)));
                cmds.setReturnReceiptPO4(rs.getString("GreenCardSignedPO4").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("GreenCardSignedPO4").substring(0,9)));
                cmds.setPullDateRR(rs.getString("PullDateRR").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDateRR").substring(0,9)));
                cmds.setPullDateBO(rs.getString("PullDateBO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDateBO").substring(0,9)));
                cmds.setPullDatePO1(rs.getString("PullDatePO").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDatePO").substring(0,9)));
                cmds.setPullDatePO2(rs.getString("PullDatePO2").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDatePO2").substring(0,9)));
                cmds.setPullDatePO3(rs.getString("PullDatePO3").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDatePO3").substring(0,9)));
                cmds.setPullDatePO4(rs.getString("PullDatePO4").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PullDatePO4").substring(0,9)));
                cmds.setHearingCompletedDate(rs.getString("HearingCompletedDate").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("HearingCompletedDate").substring(0,9)));
                cmds.setPostHearingDriefsDue(rs.getString("PostHearingBriefsDueDate").trim().equals("")
                        ? null : StringUtilities.convertStringSQLDate(rs.getString("PostHearingBriefsDueDate").substring(0,9)));

                if(rs.getString("CaseNote") != null){
                    cmds.setNote("Case Note: " + rs.getString("CaseNote").trim());
                }
                if (rs.getString("InventoryStatusNote") != null){
                    if (cmds.getNote().equals("")){
                        cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Inventory Status Note: " + rs.getString("InventoryStatusNote").trim());
                    }
                    cmds.setNote(cmds.getNote() + "Inventory Status Note: " + rs.getString("InventoryStatusNote").trim());
                }
                if (rs.getString("OutsideCourtNote") != null){
                    if (cmds.getNote().equals("")){
                        cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Outside Court Note: " + rs.getString("OutsideCourtNote").trim());
                    }
                    cmds.setNote(cmds.getNote() + "Outside Court Note: " + rs.getString("OutsideCourtNote").trim());
                }

                String ALJID = "";
                String MediatorID = "";

                if (rs.getString("ALJUser") != null){
                    ALJID = rs.getString("ALJUser").equals("") ? rs.getString("ALJ") : rs.getString("ALJUser");
                } else {
                    ALJID = rs.getString("ALJ");
                }
                if (rs.getString("MedUser") != null){
                    ALJID = rs.getString("MedUser").equals("") ? rs.getString("Mediator") : rs.getString("MedUser");
                } else {
                    MediatorID = rs.getString("Mediator");
                }
                cmds.setMediatorID(String.valueOf(StringUtilities.convertUserToID(MediatorID)));
                cmds.setAljID(String.valueOf(StringUtilities.convertUserToID(ALJID)));

                list.add(cmds);
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

    public static void batchAddCase(List<CMDSCaseModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
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
            conn.setAutoCommit(false);

            for (CMDSCaseModel item : list) {
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, StringUtils.left(item.getCaseYear(), 4));
            ps.setString ( 3, StringUtils.left(item.getCaseType(), 3));
            ps.setString ( 4, StringUtils.left(item.getCaseMonth(), 2));
            ps.setString ( 5, StringUtils.left(item.getCaseNumber(), 4));
            ps.setString ( 6, item.getNote().trim().equals("") ? null : item.getNote());
            ps.setDate   ( 7, item.getOpenDate());
            ps.setString ( 8, StringUtils.left(item.getGroupNumber(), 15));
            ps.setString ( 9, StringUtils.left(item.getAljID().equals("0") ? null : item.getAljID(), 5));
            ps.setDate   (10, item.getCloseDate());
            ps.setString (11, item.getInventoryStatusLine());
            ps.setDate   (12, item.getInventoryStatusDate());
            ps.setString (13, StringUtils.left(item.getCaseStatus(), 50));
            ps.setString (14, StringUtils.left(item.getResult(), 50));
            ps.setString (15, StringUtils.left(item.getMediatorID().equals("0") ? null : item.getMediatorID(), 10));
            ps.setString (16, StringUtils.left(item.getPbrBox(), 10));
            ps.setString (17, StringUtils.left(item.getGroupType(), 10));
            ps.setString (18, StringUtils.left(item.getReclassCode(), 50));
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

    public static String getCaseByYearAndSequence(String year, String sequence) {
        String caseNumber = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [year], type, [month], caseseqnumber FROM [Case] WHERE [year] = ? AND caseseqnumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, year);
            ps.setString(2, sequence);
            rs = ps.executeQuery();
            while (rs.next()) {
                caseNumber = rs.getString("Year") + "-" + rs.getString("type") + "-" + rs.getString("month") + "-" + rs.getString("caseseqnumber");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return caseNumber;
    }

}
