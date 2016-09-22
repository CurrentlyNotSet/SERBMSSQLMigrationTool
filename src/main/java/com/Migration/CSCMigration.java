/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CSCCaseModel;
import com.model.activityModel;
import com.model.casePartyModel;
import com.model.oldCSCHistoryModel;
import com.model.oldCivilServiceCommissionModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlCSCCase;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlCSCHistory;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class CSCMigration {
    
    public static void migrateCSCData(final MainWindowSceneController control){
        Thread cscThread = new Thread() {
            @Override
            public void run() {
                cscThread(control);
            }
        };
        cscThread.start();        
    }
    
    private static void cscThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("CSC Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<oldCivilServiceCommissionModel> oldORGCaseList = sqlCSCCase.getCases();
        List<oldCSCHistoryModel> oldCSCHistoryList = sqlCSCHistory.getActivities();
        
        totalRecordCount = oldORGCaseList.size() + oldCSCHistoryList.size();
                
        for (oldCivilServiceCommissionModel item : oldORGCaseList) {
            migrateCase(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCSCNumber()+ ": " + item.getCSCName());
        }
        
        for (oldCSCHistoryModel item : oldCSCHistoryList) {
            migrateCaseHistory(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCSCNumber() + ": " + item.getDateTimeMillis());
        }
                
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CSC Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCSCCases");
        }
    }
        
    private static void migrateCase(oldCivilServiceCommissionModel item) {
        migrateRepresentative(item);
        migrateOfficers(item);
        migrateCaseData(item);
    }

    private static void migrateRepresentative(oldCivilServiceCommissionModel item) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(null);
        party.setCaseType("CSC");
        party.setCaseMonth(null);
        party.setCaseNumber(String.valueOf(item.getCSCNumber()));
        party.setCaseRelation("Representative");
        party.setFirstName(item.getRepFirstName().trim().equals("") ? null : item.getRepFirstName().trim());
        party.setMiddleInitial(item.getRepMiddleInitial().trim().equals("") ? null : item.getRepMiddleInitial().trim());
        party.setLastName(item.getRepLastName().trim().equals("") ? null : item.getRepLastName().trim());
        party.setJobTitle(item.getRepType().trim().equals("") ? null : item.getRepType().trim());
        party.setAddress1(item.getRepAddress1().trim().equals("") ? null : item.getRepAddress1().trim());
        party.setAddress2(item.getRepAddress2().trim().equals("") ? null : item.getRepAddress2().trim());
        party.setCity(item.getRepCity().trim().equals("") ? null : item.getRepCity().trim());
        party.setState(item.getRepState().trim().equals("") ? null : item.getRepState().trim());
        party.setZip(item.getRepZipPlusFour().trim().equals("") ? null : item.getRepZipPlusFour().trim());
        party.setPhoneOne((!item.getRepPhone1().trim().equals("null") || !item.getRepPhone1().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone1().trim()) : null);
        party.setPhoneTwo((!item.getRepPhone2().trim().equals("null") || !item.getRepPhone2().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone2().trim()) : null);
        party.setFax((!item.getRepFax().trim().equals("null") || !item.getRepFax().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getRepFax().trim()) : null);
        if (party.getPhoneTwo().equals("")){
            party.setPhoneTwo(null);
        }     
        if (party.getFax().equals("")){
            party.setFax(null);
        }        
        party.setEmailAddress(item.getRepEmail().trim().equals("") ? null : item.getRepEmail().trim());
                
        if (!item.getRepLastName().trim().equals("")){             
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateOfficers(oldCivilServiceCommissionModel item) {
        
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(null);
        party.setCaseType("CSC");
        party.setCaseMonth(null);
        party.setCaseNumber(String.valueOf(item.getCSCNumber()));
        party.setCaseRelation("Chairman");
        party.setAddress1(null);
        party.setAddress2(null);
        party.setCity(null);
        party.setState(null);
        party.setZip(null);
        party.setPhoneOne(null);
        party.setPhoneTwo(null);
        party.setEmailAddress(null);
                
        if (!item.getChairman1().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman1().trim().equals("") ? null : item.getChairman1().trim());
            party.setJobTitle(item.getChairman1Title().trim().equals("") ? null : item.getChairman1Title().trim());
                        
            sqlCaseParty.savePartyInformation(party);
        }
        
        if (!item.getChairman2().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman2().trim().equals("") ? null : item.getChairman2().trim());
            party.setJobTitle(item.getChairman2Title().trim().equals("") ? null : item.getChairman2Title().trim());
                        
            sqlCaseParty.savePartyInformation(party);
        }
        
        if (!item.getChairman3().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman3().trim().equals("") ? null : item.getChairman3().trim());
            party.setJobTitle(item.getChairman3Title().trim().equals("") ? null : item.getChairman3Title().trim());
                        
            sqlCaseParty.savePartyInformation(party);
        }
        
        if (!item.getChairman4().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman4().trim().equals("") ? null : item.getChairman4().trim());
            party.setJobTitle(item.getChairman4Title().trim().equals("") ? null : item.getChairman4Title().trim());
                        
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCaseData(oldCivilServiceCommissionModel item) {
        CSCCaseModel org = new CSCCaseModel();

        org.setActive(item.getACTIVE() == 1);
        org.setName(!item.getCSCName().trim().equals("") ? item.getCSCName().trim() : null);
        org.setType(!item.getCSCType().trim().equals("") ? item.getCSCType().trim() : null);
        org.setCscNumber(String.valueOf(item.getCSCNumber()));
        org.setAddress1(!item.getCSCAddress1().trim().equals("") ? item.getCSCAddress1().trim() : null);
        org.setAddress2(!item.getCSCAddress2().trim().equals("") ? item.getCSCAddress2().trim() : null);
        org.setCity(!item.getCSCCity().trim().equals("") ? item.getCSCCity().trim() : null);
        org.setState(!item.getCSCState().trim().equals("") ? item.getCSCState().trim() : null);
        org.setZipCode(!item.getCSCZipPlusFour().trim().equals("") ? item.getCSCZipPlusFour().trim() : null);
        org.setCounty(!item.getCSCCounty().trim().equals("") ? item.getCSCCounty().trim() : null);
        org.setPhone1((!item.getCSCPhone1().trim().equals("null") || !item.getCSCPhone1().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getCSCPhone1().trim()) : null);
        org.setPhone2((!item.getCSCPhone2().trim().equals("null") || !item.getCSCPhone2().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getCSCPhone2().trim()) : null);
        org.setFax((!item.getCSCFax().trim().equals("null") || !item.getCSCFax().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getCSCFax().trim()) : null);
        org.setEmail(!item.getCSCEmail().trim().equals("") ? item.getCSCEmail().trim() : null);
        org.setStatutory(!item.getStatutory().trim().equals("Y"));
        org.setCharter(item.getCharter().trim().equals("Home Rule"));
        org.setFiscalYearEnding(StringUtilities.monthName(item.getFiscalYearEnding()));
        org.setLastNotification(!item.getLastNotification().trim().equals("") ? item.getLastNotification().trim() : null);
        org.setActivityLastFiled(!item.getActivitesLastFiled().trim().equals("null") 
                ? StringUtilities.convertStringSQLDate(item.getActivitesLastFiled()) : null);
        org.setPreviousFileDate(!item.getPreviousFileDate().trim().equals("null") 
                ? StringUtilities.convertStringSQLDate(item.getPreviousFileDate()) : null);
        org.setDueDate(StringUtilities.monthName(item.getDueDate()));
        org.setFiled(!item.getFiled().trim().equals("null") ? StringUtilities.convertStringSQLDate(item.getFiled()) : null);
        org.setValid(!item.getValid().trim().equals("Y"));
        org.setNote(!item.getDescription2().trim().equals("") ? item.getDescription2().trim() : null);
        org.setAlsoknownas(!item.getDescription1().trim().equals("") ? item.getDescription1().trim() : null);
        
        sqlCSCCase.importOldCSCCase(org);
    }
    
    private static void migrateCaseHistory(oldCSCHistoryModel old) {
        String comment = !old.getNote().trim().equals("null") ? old.getNote().trim() : "";        
        if (!comment.trim().equals("")){
            comment += "/n/n";
        }
        comment += !old.getNote().trim().equals("null") ? old.getNote().trim() : "";
        
        activityModel item = new activityModel();
        item.setCaseYear(null);
        item.setCaseType("CSC");
        item.setCaseMonth(null);
        item.setCaseNumber(old.getCSCNumber());
        item.setUserID(StringUtilities.convertUserToID(old.getInitials()));
        item.setDate(new Timestamp(old.getDateTimeMillis()));
        item.setAction(!old.getDescription().trim().equals("") ? old.getDescription().trim() : null);
        item.setFileName(!old.getFileName().trim().equals("") ? old.getFileName().trim() : null);
        item.setFrom(null);
        item.setTo(null);
        item.setType(!old.getFileAttrib().trim().equals("null") ? old.getFileAttrib().trim() : null);
        item.setRedacted(0);
        item.setAwaitingTimeStamp(0);
        item.setComment(comment.trim().equals("") ? null : comment);
                
        sqlActivity.addActivity(item);
    }
        
}
