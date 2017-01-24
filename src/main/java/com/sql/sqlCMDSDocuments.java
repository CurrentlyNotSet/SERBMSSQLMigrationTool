/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.CMDSDocumentModel;
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
public class sqlCMDSDocuments {
    
    public static List<CMDSDocumentModel> getOldCMDSDocuments() {
        List<CMDSDocumentModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM FormLetters";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CMDSDocumentModel item = new CMDSDocumentModel();
                item.setID(rs.getInt("FormLettersID"));
                item.setActive(rs.getInt("Active") == 1);
                item.setMainCategory(rs.getString("MainCategory").trim().equals("") ? null : rs.getString("MainCategory").trim());
                item.setSubCategory(rs.getString("SubCategory").trim().equals("") ? null : rs.getString("SubCategory").trim());
                item.setLetterName(rs.getString("FormLetterName").trim().equals("") ? null : rs.getString("FormLetterName").trim());
                item.setLocation(rs.getString("FormLetterLocation").trim().equals("") ? null : rs.getString("FormLetterLocation").trim());
                item.setMultiplePrint(rs.getString("MultiplePrint").equalsIgnoreCase("yes"));
                item.setResponseDue(rs.getString("ResponseDue").equalsIgnoreCase("Y"));
                item.setActionAppealed(rs.getString("ActionAppealed").equalsIgnoreCase("Y"));
                item.setClassificationTitle(rs.getString("ClassificationTitle").equalsIgnoreCase("Y"));
                item.setClassificationNumber(rs.getString("ClassificationNum").equalsIgnoreCase("Y"));
                item.setBarginingUnit(rs.getString("BarginingUnit").equalsIgnoreCase("Y"));
                item.setAppelantAppointed(rs.getString("AppelantAppointed").equalsIgnoreCase("Y"));
                item.setProbitionaryPeriod(rs.getString("Probitionaryperiod").equalsIgnoreCase("Y"));
                item.setHearingDate(rs.getString("HearingDate").equalsIgnoreCase("Y"));
                item.setHearingTime(rs.getString("HearingTime").equalsIgnoreCase("Y"));
                item.setHearingServed(rs.getString("HearingServed").equalsIgnoreCase("Y"));
                item.setMemorandumContra(rs.getString("MemorandumContra").equalsIgnoreCase("Y"));
                item.setGender(rs.getString("Gender").equalsIgnoreCase("Y"));
                item.setAddressBlock(rs.getString("AddressBlock").equalsIgnoreCase("Y"));
                item.setFirstLetterSent(rs.getString("FirstLetterSent").equalsIgnoreCase("Y"));
                item.setCodeSection(rs.getString("CodeSection").equalsIgnoreCase("Y"));
                item.setCountyName(rs.getString("CountyName").equalsIgnoreCase("Y"));
                item.setStayDate(rs.getString("StayDate").equalsIgnoreCase("Y"));
                item.setCasePendingResolution(rs.getString("CasePendingResolution").equalsIgnoreCase("Y"));
                item.setLastUpdate(rs.getString("LastUpdate").equalsIgnoreCase("Y"));
                item.setDateGranted(rs.getString("DateGranted").equalsIgnoreCase("Y"));
                item.setMatterContinued(rs.getString("MatterContinued").equalsIgnoreCase("Y"));
                item.setSettlementDue(rs.getString("SettlementDue").equalsIgnoreCase("Y"));
                item.setFilingParty(rs.getString("FilingParty").equalsIgnoreCase("Y"));
                item.setRespondingParty(rs.getString("RespondingParty").equalsIgnoreCase("Y"));
                item.setRequestingParty(rs.getString("RequestingParty").equalsIgnoreCase("Y"));
                item.setDeposition(rs.getString("Deposition").equalsIgnoreCase("Y"));
                item.setRepHimOrHer(rs.getString("RepHimOrHer").equalsIgnoreCase("Y"));
                item.setTypeOfAction(rs.getString("TypeOfAction").equalsIgnoreCase("Y"));
                item.setCodeSectionFillIn(rs.getString("CodeSectionFillIn").equalsIgnoreCase("Y"));
                item.setDocumentName(rs.getString("DocumentName").equalsIgnoreCase("Y"));
                item.setDateFiled(rs.getString("DateFiled").equalsIgnoreCase("Y"));
                item.setInfoRedacted(rs.getString("InfoRedacted").equalsIgnoreCase("Y"));
                item.setRedactorName(rs.getString("RedactorName").equalsIgnoreCase("Y"));
                item.setRedactorTitle(rs.getString("RedactorTitle").equalsIgnoreCase("Y"));
                item.setDatePOSent(rs.getString("DatePOSent").equalsIgnoreCase("Y"));
                item.setAppealType(rs.getString("AppealType").equalsIgnoreCase("Y"));
                item.setAppealType2(rs.getString("AppealType2").equalsIgnoreCase("Y"));
                item.setAppealTypeUF(rs.getString("AppealTypeUF").equalsIgnoreCase("Y"));
                item.setAppealTypeLS(rs.getString("AppealTypeLS").equalsIgnoreCase("Y"));
                item.setRequestingPartyC(rs.getString("RequestingPartyC").equalsIgnoreCase("Y"));
                item.setDateRequested(rs.getString("DateRequested").equalsIgnoreCase("Y"));
                item.setPurposeOfExtension(rs.getString("PurposeOfExtension").equalsIgnoreCase("Y"));
                item.setEmailSubject(null);
                item.setEmailBody(null);
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
    
    public static void addCMDSDocuments(CMDSDocumentModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSDocuments("
                    + "Active, "                //01
                    + "MainCategory, "          //02
                    + "SubCategory, "           //03
                    + "LetterName, "            //04
                    + "Location, "              //05
                    + "MultiplePrint, "         //06
                    + "ResponseDue, "           //07
                    + "ActionAppealed, "        //08
                    + "ClassificationTitle, "   //09
                    + "ClassificationNumber, "  //10
                    + "BarginingUnit, "         //11
                    + "AppelantAppointed, "     //12
                    + "ProbitionaryPeriod, "    //13
                    + "HearingDate, "           //14
                    + "HearingTime, "           //15
                    + "HearingServed, "         //16
                    + "MemorandumContra, "      //17
                    + "Gender, "                //18
                    + "AddressBlock, "          //19
                    + "FirstLetterSent, "       //20
                    + "CodeSection, "           //21
                    + "CountyName, "            //22
                    + "StayDate, "              //23
                    + "CasePendingResolution, " //24
                    + "LastUpdate, "            //25
                    + "DateGranted, "           //26
                    + "MatterContinued, "       //27
                    + "SettlementDue, "         //28
                    + "FilingParty, "           //29
                    + "RespondingParty, "       //30
                    + "RequestingParty, "       //31
                    + "Deposition, "            //32
                    + "RepHimOrHer, "           //33
                    + "TypeOfAction, "          //34
                    + "CodeSectionFillIn, "     //35
                    + "DocumentName, "          //36
                    + "DateFiled, "             //37
                    + "InfoRedacted, "          //38
                    + "RedactorName, "          //39
                    + "RedactorTitle, "         //40
                    + "DatePOSent, "            //41
                    + "AppealType, "            //42
                    + "AppealType2, "           //43
                    + "AppealTypeUF, "          //44
                    + "AppealTypeLS, "          //45
                    + "RequestingPartyC, "      //46
                    + "DateRequested, "         //47
                    + "PurposeOfExtension, "    //48
                    + "EmailSubject, "          //49
                    + "EmailBody, "             //50
                    + "sortOrder "              //51
                    + ") VALUES (";
                    for(int i=0; i<50; i++){
                        sql += "?, ";   //01-50
                    }
                     sql += "?)";   //51
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, StringUtils.left(item.getMainCategory(), 100));
            ps.setString ( 3, StringUtils.left(item.getSubCategory(), 100));
            ps.setString ( 4, StringUtils.left(item.getLetterName(), 100));
            ps.setString ( 5, StringUtils.left(item.getLocation(), 255));
            ps.setBoolean( 6, item.isMultiplePrint());
            ps.setBoolean( 7, item.isResponseDue());
            ps.setBoolean( 8, item.isActionAppealed());
            ps.setBoolean( 9, item.isClassificationTitle());
            ps.setBoolean(10, item.isClassificationNumber());
            ps.setBoolean(11, item.isBarginingUnit());
            ps.setBoolean(12, item.isAppelantAppointed());
            ps.setBoolean(13, item.isProbitionaryPeriod());
            ps.setBoolean(14, item.isHearingDate());
            ps.setBoolean(15, item.isHearingTime());
            ps.setBoolean(16, item.isHearingServed());
            ps.setBoolean(17, item.isMemorandumContra());
            ps.setBoolean(18, item.isGender());
            ps.setBoolean(19, item.isAddressBlock());
            ps.setBoolean(20, item.isFirstLetterSent());
            ps.setBoolean(21, item.isCodeSection());
            ps.setBoolean(22, item.isCountyName());
            ps.setBoolean(23, item.isStayDate());
            ps.setBoolean(24, item.isCasePendingResolution());
            ps.setBoolean(25, item.isLastUpdate());
            ps.setBoolean(26, item.isDateGranted());
            ps.setBoolean(27, item.isMatterContinued());
            ps.setBoolean(28, item.isSettlementDue());
            ps.setBoolean(29, item.isFilingParty());
            ps.setBoolean(30, item.isRespondingParty());
            ps.setBoolean(31, item.isRequestingParty());
            ps.setBoolean(32, item.isDeposition());
            ps.setBoolean(33, item.isRepHimOrHer());
            ps.setBoolean(34, item.isTypeOfAction());
            ps.setBoolean(35, item.isCodeSectionFillIn());
            ps.setBoolean(36, item.isDocumentName());
            ps.setBoolean(37, item.isDateFiled());
            ps.setBoolean(38, item.isInfoRedacted());
            ps.setBoolean(39, item.isRedactorName());
            ps.setBoolean(40, item.isRedactorTitle());
            ps.setBoolean(41, item.isDatePOSent());
            ps.setBoolean(42, item.isAppealType());
            ps.setBoolean(43, item.isAppealType2());
            ps.setBoolean(44, item.isAppealTypeUF());
            ps.setBoolean(45, item.isAppealTypeLS());
            ps.setBoolean(46, item.isRequestingPartyC());
            ps.setBoolean(47, item.isDateRequested());
            ps.setBoolean(48, item.isPurposeOfExtension());
            ps.setString (49, item.getEmailSubject());
            ps.setString (50, item.getEmailBody());
            ps.setNull   (51, java.sql.Types.DOUBLE);
            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddCMDSDocuments(List<CMDSDocumentModel> list, MainWindowSceneController control, int currentCount, int totalCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO CMDSDocuments("
                    + "Active, "                //01
                    + "MainCategory, "          //02
                    + "SubCategory, "           //03
                    + "LetterName, "            //04
                    + "Location, "              //05
                    + "MultiplePrint, "         //06
                    + "ResponseDue, "           //07
                    + "ActionAppealed, "        //08
                    + "ClassificationTitle, "   //09
                    + "ClassificationNumber, "  //10
                    + "BarginingUnit, "         //11
                    + "AppelantAppointed, "     //12
                    + "ProbitionaryPeriod, "    //13
                    + "HearingDate, "           //14
                    + "HearingTime, "           //15
                    + "HearingServed, "         //16
                    + "MemorandumContra, "      //17
                    + "Gender, "                //18
                    + "AddressBlock, "          //19
                    + "FirstLetterSent, "       //20
                    + "CodeSection, "           //21
                    + "CountyName, "            //22
                    + "StayDate, "              //23
                    + "CasePendingResolution, " //24
                    + "LastUpdate, "            //25
                    + "DateGranted, "           //26
                    + "MatterContinued, "       //27
                    + "SettlementDue, "         //28
                    + "FilingParty, "           //29
                    + "RespondingParty, "       //30
                    + "RequestingParty, "       //31
                    + "Deposition, "            //32
                    + "RepHimOrHer, "           //33
                    + "TypeOfAction, "          //34
                    + "CodeSectionFillIn, "     //35
                    + "DocumentName, "          //36
                    + "DateFiled, "             //37
                    + "InfoRedacted, "          //38
                    + "RedactorName, "          //39
                    + "RedactorTitle, "         //40
                    + "DatePOSent, "            //41
                    + "AppealType, "            //42
                    + "AppealType2, "           //43
                    + "AppealTypeUF, "          //44
                    + "AppealTypeLS, "          //45
                    + "RequestingPartyC, "      //46
                    + "DateRequested, "         //47
                    + "PurposeOfExtension, "    //48
                    + "EmailSubject, "          //49
                    + "EmailBody, "             //50
                    + "sortOrder "              //51
                    + ") VALUES (";
                    for(int i=0; i<50; i++){
                        sql += "?, ";   //01-50
                    }
                     sql += "?)";   //51
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (CMDSDocumentModel item : list){
                ps.setBoolean( 1, item.isActive());
                ps.setString ( 2, item.getMainCategory());
                ps.setString ( 3, item.getSubCategory());
                ps.setString ( 4, item.getLetterName());
                ps.setString ( 5, item.getLocation());
                ps.setBoolean( 6, item.isMultiplePrint());
                ps.setBoolean( 7, item.isResponseDue());
                ps.setBoolean( 8, item.isActionAppealed());
                ps.setBoolean( 9, item.isClassificationTitle());
                ps.setBoolean(10, item.isClassificationNumber());
                ps.setBoolean(11, item.isBarginingUnit());
                ps.setBoolean(12, item.isAppelantAppointed());
                ps.setBoolean(13, item.isProbitionaryPeriod());
                ps.setBoolean(14, item.isHearingDate());
                ps.setBoolean(15, item.isHearingTime());
                ps.setBoolean(16, item.isHearingServed());
                ps.setBoolean(17, item.isMemorandumContra());
                ps.setBoolean(18, item.isGender());
                ps.setBoolean(19, item.isAddressBlock());
                ps.setBoolean(20, item.isFirstLetterSent());
                ps.setBoolean(21, item.isCodeSection());
                ps.setBoolean(22, item.isCountyName());
                ps.setBoolean(23, item.isStayDate());
                ps.setBoolean(24, item.isCasePendingResolution());
                ps.setBoolean(25, item.isLastUpdate());
                ps.setBoolean(26, item.isDateGranted());
                ps.setBoolean(27, item.isMatterContinued());
                ps.setBoolean(28, item.isSettlementDue());
                ps.setBoolean(29, item.isFilingParty());
                ps.setBoolean(30, item.isRespondingParty());
                ps.setBoolean(31, item.isRequestingParty());
                ps.setBoolean(32, item.isDeposition());
                ps.setBoolean(33, item.isRepHimOrHer());
                ps.setBoolean(34, item.isTypeOfAction());
                ps.setBoolean(35, item.isCodeSectionFillIn());
                ps.setBoolean(36, item.isDocumentName());
                ps.setBoolean(37, item.isDateFiled());
                ps.setBoolean(38, item.isInfoRedacted());
                ps.setBoolean(39, item.isRedactorName());
                ps.setBoolean(40, item.isRedactorTitle());
                ps.setBoolean(41, item.isDatePOSent());
                ps.setBoolean(42, item.isAppealType());
                ps.setBoolean(43, item.isAppealType2());
                ps.setBoolean(44, item.isAppealTypeUF());
                ps.setBoolean(45, item.isAppealTypeLS());
                ps.setBoolean(46, item.isRequestingPartyC());
                ps.setBoolean(47, item.isDateRequested());
                ps.setBoolean(48, item.isPurposeOfExtension());
                ps.setString (49, item.getEmailSubject());
                ps.setString (50, item.getEmailBody());
                ps.setNull   (51, java.sql.Types.DOUBLE);
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
