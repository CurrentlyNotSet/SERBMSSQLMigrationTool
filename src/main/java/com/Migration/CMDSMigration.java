/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CMDSCaseModel;
import com.model.CMDSCaseSearchModel;
import com.model.CMDSHearingModel;
import com.model.CMDSResultModel;
import com.model.CMDSStatusType;
import com.model.activityModel;
import com.model.casePartyModel;
import com.model.oldCMDSCaseModel;
import com.model.oldCMDSCasePartyModel;
import com.model.oldCMDSHistoryModel;
import com.model.oldCMDShearingModel;
import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlCMDSCase;
import com.sql.sqlCMDSCaseParty;
import com.sql.sqlCMDSCaseSearch;
import com.sql.sqlCMDSHearing;
import com.sql.sqlCMDSResult;
import com.sql.sqlCMDSStatusType;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class CMDSMigration {
    
    public static void migrateCMDSData(final MainWindowSceneController control){
        Thread cmdsThread = new Thread() {
            @Override
            public void run() {
                cmdsThread(control);
            }
        };
        cmdsThread.start();        
    }
    
    private static void cmdsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("CMDS Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        sqlUsers.getNewDBUsers();
        
        List<oldCMDSCasePartyModel> oldCMDScasePartyList = sqlCMDSCaseParty.getParty();
        List<oldCMDSCaseModel> oldCMDScaseList = sqlCMDSCase.getCase();
        List<oldCMDShearingModel> oldCMDSHearingList = sqlCMDSHearing.getHearings();
        List<oldCMDSHistoryModel> oldCMDSHistoryList = sqlActivity.getCMDSHistory();
        List<CMDSResultModel> cmdsResultList = sqlCMDSResult.getOldCMDSResults();
        List<CMDSStatusType> cmdsStatusTypeList = sqlCMDSStatusType.getOldCMDSStatusType();
        
        totalRecordCount = oldCMDScasePartyList.size() + oldCMDScaseList.size() 
                + oldCMDSHearingList.size() + oldCMDSHistoryList.size() + cmdsResultList.size();
        
        for (CMDSStatusType item : cmdsStatusTypeList) {
            sqlCMDSStatusType.addCMDSStatusType(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getDescription());
        }
        
//        for (CMDSResultModel item : cmdsResultList) {
//            sqlCMDSResult.addCMDSResult(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getResult());
//        }
//        
//        for (oldCMDSCasePartyModel item : oldCMDScasePartyList) {
//            migrateCaseParty(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
//                    item.getYear() + "-" + item.getCaseType() + "-" + item.getCaseMonth() + "-" + item.getCaseSeqNumber()
//                            + ": " + item.getFirstName() + " " + item.getLastName());
//        }
//        
//        for (oldCMDSCaseModel item : oldCMDScaseList) {
//            migrateCase(item);
//            migrateSearch(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
//                    item.getYear() + "-" + item.getType() + "-" + item.getMonth() + "-" + item.getCaseSeqNumber());
//        }
//
//        for (oldCMDShearingModel item : oldCMDSHearingList) {
//            migrateHearings(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
//                    item.getYear() + "-" + item.getType() + "-" + item.getMonth() + "-" + item.getCaseSeqNumber());
//        }
//        
//        for (oldCMDSHistoryModel item : oldCMDSHistoryList) {
//            migrateHistory(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
//                    item.getCaseYear() + "-" + item.getCaseType() + "-" + item.getCaseMonth() + "-" + item.getCaseSeqNumber());
//        }

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CMDS Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCMDSCases");
        }
    }
    
    private static void migrateCaseParty(oldCMDSCasePartyModel item) {
        casePartyModel party = new  casePartyModel();
        
        party.setCaseYear(item.getYear());
        party.setCaseType(item.getCaseType());
        party.setCaseMonth(item.getCaseMonth());
        party.setCaseNumber(item.getCaseSeqNumber());
        party.setCaseRelation(item.getParticipantType());
        party.setFirstName(item.getFirstName().trim().equals("") ? null : item.getFirstName().trim());
        party.setMiddleInitial(item.getMiddleInitial().trim().equals("") ? null : item.getMiddleInitial().trim());
        party.setLastName(item.getLastName().trim().equals("") ? null : item.getLastName().trim());
        party.setJobTitle(item.getTitle().trim().equals("") ? null : item.getTitle().trim());
        party.setNameTitle(item.getEtalextraname().trim().equals("") ? null : item.getEtalextraname().trim());
        party.setAddress1(item.getAddress1().trim().equals("") ? null : item.getAddress1().trim());
        party.setAddress2(item.getAddress2().trim().equals("") ? null : item.getAddress2().trim());
        party.setCity(item.getCity().trim().equals("") ? null : item.getCity().trim());
        party.setState(item.getState().trim().equals("") ? null : item.getState().trim());
        party.setZip(item.getZip().trim().equals("") ? null : item.getZip().trim());
        party.setFax((!item.getFax().trim().equals("null") || !item.getFax().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getFax().trim()) : null);  
        party.setEmailAddress(item.getEmail().trim().equals("") ? null : item.getEmail().trim());
        party.setPhoneOne(!item.getOfficePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getOfficePhone().trim()) : null);
        party.setPhoneTwo(!item.getCellularPhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getCellularPhone().trim()) : null);
        
        if (party.getPhoneOne() == null && !item.getHomePhone().equals("")){
            party.setPhoneOne(!item.getHomePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getHomePhone().trim()) : null);
        } else if (party.getPhoneTwo() == null && !item.getHomePhone().equals("")){
            party.setPhoneTwo(!item.getHomePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getHomePhone().trim()) : null);
        }
                
        if (item.getActive() == 1){
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCase(oldCMDSCaseModel item) {
        CMDSCaseModel cmds = new CMDSCaseModel();
        
        cmds.setActive(item.getActive() == 1);
        cmds.setCaseYear(item.getYear());
        cmds.setCaseType(item.getType());
        cmds.setCaseMonth(item.getMonth());
        cmds.setCaseNumber(item.getCaseSeqNumber());
        cmds.setNote("");
        cmds.setOpenDate(item.getOpenDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getOpenDate().substring(0,9)));
        cmds.setGroupNumber(item.getGroupNumber().equals("") ? null : item.getGroupNumber().trim());
        cmds.setAljID(String.valueOf(StringUtilities.convertUserToID(item.getALJ().trim() == null ? "" : item.getALJ())));
        cmds.setCloseDate(item.getCloseDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getCloseDate().substring(0,9)));
        cmds.setInventoryStatusLine(item.getInventoryStatusLine().equals("") ? null : item.getInventoryStatusLine().trim());
        cmds.setInventoryStatusDate(item.getInventoryStatusDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getInventoryStatusDate().substring(0,9)));
        cmds.setCaseStatus(item.getStatus().equals("") ? null : item.getStatus().trim());
        cmds.setResult(item.getResult().equals("") ? null : item.getResult().trim());
        cmds.setMediatorID(String.valueOf(StringUtilities.convertUserToID(item.getMediator() == null ? "" : item.getMediator())));
        cmds.setPbrBox(item.getPBRBoxNumber().equals("") ? null : item.getPBRBoxNumber().trim());
        cmds.setGroupType(item.getGroupType().trim().equals("") ? null : item.getGroupType().trim());
        cmds.setReclassCode(item.getReclassCode().trim().equals("") ? null : item.getReclassCode().trim());
        cmds.setMailedRR(item.getMailedRR().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedRR().substring(0,9)));
        cmds.setMailedBO(item.getMailedBO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedBO().substring(0,9)));
        cmds.setMailedPO1(item.getMailedPO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedPO().substring(0,9)));
        cmds.setMailedPO2(item.getMailedPO2().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedPO2().substring(0,9)));
        cmds.setMailedPO3(item.getMailedPO3().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedPO3().substring(0,9)));
        cmds.setMailedPO4(item.getMailedPO4().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getMailedPO4().substring(0,9)));
        cmds.setRemailedRR(item.getRemailedRR().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedRR().substring(0,9)));
        cmds.setRemailedBO(item.getRemailedBO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedBO().substring(0,9)));
        cmds.setRemailedPO1(item.getRemailedPO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedPO().substring(0,9)));
        cmds.setRemailedPO2(item.getRemailedPO2().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedPO2().substring(0,9)));
        cmds.setRemailedPO3(item.getRemailedPO3().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedPO3().substring(0,9)));
        cmds.setRemailedPO4(item.getRemailedPO4().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getRemailedPO4().substring(0,9)));
        cmds.setReturnReceiptRR(item.getGreenCardSignedRR().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedRR().substring(0,9)));
        cmds.setReturnReceiptBO(item.getGreenCardSignedBO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedBO().substring(0,9)));
        cmds.setReturnReceiptPO1(item.getGreenCardSignedPO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedPO().substring(0,9)));
        cmds.setReturnReceiptPO2(item.getGreenCardSignedPO2().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedPO2().substring(0,9)));
        cmds.setReturnReceiptPO3(item.getGreenCardSignedPO3().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedPO3().substring(0,9)));
        cmds.setReturnReceiptPO4(item.getGreenCardSignedPO4().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getGreenCardSignedPO4().substring(0,9)));
        cmds.setPullDateRR(item.getPullDateRR().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDateRR().substring(0,9)));
        cmds.setPullDateBO(item.getPullDateBO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDateBO().substring(0,9)));
        cmds.setPullDatePO1(item.getPullDatePO().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDatePO().substring(0,9)));
        cmds.setPullDatePO2(item.getPullDatePO2().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDatePO2().substring(0,9)));
        cmds.setPullDatePO3(item.getPullDatePO3().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDatePO3().substring(0,9)));
        cmds.setPullDatePO4(item.getPullDatePO4().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPullDatePO4().substring(0,9)));
        cmds.setHearingCompletedDate(item.getHearingCompletedDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getHearingCompletedDate().substring(0,9)));
        cmds.setPostHearingDriefsDue(item.getPostHearingBriefsDueDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getPostHearingBriefsDueDate().substring(0,9)));
        
        if(!item.getCaseNote().equals("")){
            cmds.setNote("Case Note: " + item.getCaseNote().trim());
        }
        if (!item.getInventoryStatusNote().equals("")){
            if (cmds.getNote().equals("")){
                cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Inventory Status Note: " + item.getInventoryStatusNote().trim());
            }
            cmds.setNote(cmds.getNote() + "Inventory Status Note: " + item.getInventoryStatusNote().trim());
        }
        if (!item.getOutsideCourtNote().equals("")){
            if (cmds.getNote().equals("")){
                cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Outside Court Note: " + item.getOutsideCourtNote().trim());
            }
            cmds.setNote(cmds.getNote() + "Outside Court Note: " + item.getOutsideCourtNote().trim());
        }
        
        sqlCMDSCase.addCase(cmds);
    }
    
    private static void migrateSearch(oldCMDSCaseModel item) {
        int aljID = StringUtilities.convertUserToID(item.getALJ().trim() == null ? "" : item.getALJ());
        String appellant = "";
        String appellee = "";
        String ALJName = "";
        List<oldCMDSCasePartyModel> partyList = sqlCMDSCaseParty.getPartyByCase(item.getYear(), item.getCaseSeqNumber());
        
        for (oldCMDSCasePartyModel person : partyList){
            if (person.getParticipantType().contains("Appellee")){
                if (!appellee.trim().equals("")){
                    appellee += ", ";
                }
                appellee += StringUtilities.buildFullName(person.getFirstName(), person.getMiddleInitial(), person.getLastName());
                
            } else if (person.getParticipantType().contains("Appellant")) {
                if (!appellant.trim().equals("")){
                   appellant += ", ";
                }
                appellant += StringUtilities.buildFullName(person.getFirstName(), person.getMiddleInitial(), person.getLastName());
            }
        }
        
        for (userModel user : Global.getUserList()){
            if (user.getId() == aljID){
                ALJName = StringUtilities.buildFullName(user.getFirstName(), user.getMiddleInitial(), user.getLastName());
            }
        }        
        
        CMDSCaseSearchModel search = new CMDSCaseSearchModel();
        
        search.setCaseYear(item.getYear());
        search.setCaseType(item.getType());
        search.setCaseMonth(item.getMonth());
        search.setCaseNumber(item.getCaseSeqNumber());
        search.setAppellant(appellant.trim().equals("") ? null : appellant);
        search.setAppellee(appellee.trim().equals("") ? null : appellee);
        search.setAlj(ALJName.trim().equals("") ? null : ALJName);
        search.setDateOpened(item.getOpenDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getOpenDate().substring(0,9)));
        
        sqlCMDSCaseSearch.addRow(search);
    }
    
    private static void migrateHearings(oldCMDShearingModel item) {
        CMDSHearingModel hearing = new CMDSHearingModel();
                
        hearing.setActive(item.getActive() == 1);
        hearing.setCaseYear(item.getYear().equals("") ? null : item.getYear().trim());
        hearing.setCaseType(item.getType().equals("") ? null : item.getType().trim());
        hearing.setCaseMonth(item.getMonth().equals("") ? null : item.getMonth().trim());
        hearing.setCaseNumber(item.getCaseSeqNumber().equals("") ? null : item.getCaseSeqNumber().trim());
        hearing.setEntryDate(item.getEntryDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getEntryDate().substring(0,9)));
        hearing.setHearingType(item.getHearingtype().equals("") ? null : item.getHearingtype().trim());
        hearing.setRoom(item.getRoom().trim().equals("") ? null : item.getRoom().trim());

        if (!item.getHearingDate().equals("") && !item.getHearingTime().equals("")){
            hearing.setHearingDateTime(item.getEntryDate().trim().equals("") 
                ? null : StringUtilities.convertStringDateAndTime(
                        item.getHearingDate().substring(0,9).trim(), 
                        item.getHearingTime().trim())
            );
        }

        sqlCMDSHearing.addHearings(hearing);
    }
    
    private static void migrateHistory(oldCMDSHistoryModel old) {
        String direction = "";
        if (old.getMailType().equals("I")) {
            direction = "IN - ";
        } else if (old.getMailType().equals("O")) {
            direction = "OUT - ";
        }
        
        activityModel item = new activityModel();
        item.setCaseYear(old.getCaseYear());
        item.setCaseType(old.getCaseType());
        item.setCaseMonth(old.getCaseMonth());
        item.setCaseNumber(old.getCaseSeqNumber());
        item.setUserID(StringUtilities.convertUserToID(old.getUserinitials()));
        item.setDate(old.getEntryDate().equals("") ? null : StringUtilities.convertStringTimeStamp(old.getEntryDate()));
        item.setAction(!old.getEntryDescription().trim().equals("") ? direction + old.getEntryDescription().trim() : null);
        item.setFileName(!old.getDocumentLink().trim().equals("") ? old.getDocumentLink().trim() : null);
        item.setFrom(null);
        item.setTo(null);
        item.setType(!old.getEntryType().trim().equals("") ? old.getEntryType().trim() : null);
        item.setRedacted(0);
        item.setAwaitingTimeStamp(0);
        item.setComment(null);
                
        sqlActivity.addActivity(item);
    }
    
}
