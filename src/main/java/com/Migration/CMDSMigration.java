/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CMDSCaseModel;
import com.model.CMDSCaseSearchModel;
import com.model.CMDSHearingModel;
import com.model.CMDSHistoryCategoryModel;
import com.model.CMDSHistoryDescriptionModel;
import com.model.CMDSResultModel;
import com.model.CMDSStatusTypeModel;
import com.model.casePartyModel;
import com.model.DirectorsModel;
import com.model.ReClassCodeModel;
import com.model.activityModel;
import com.model.appealCourtModel;
import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlAppealCourt;
import com.sql.sqlCMDSCase;
import com.sql.sqlCMDSCaseParty;
import com.sql.sqlCMDSCaseSearch;
import com.sql.sqlCMDSHearing;
import com.sql.sqlCMDSHistoryCategory;
import com.sql.sqlCMDSHistoryDescription;
import com.sql.sqlCMDSResult;
import com.sql.sqlCMDSStatusType;
import com.sql.sqlCaseParty;
import com.sql.sqlDirector;
import com.sql.sqlMigrationStatus;
import com.sql.sqlReClassCode;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class CMDSMigration {
    
    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static final List<CMDSCaseSearchModel> CMDSCaseSearchList = new ArrayList<>();
    
    public static void migrateCMDSData(final MainWindowSceneController control){
        Thread cmdsThread = new Thread() {
            @Override
            public void run() {
                cmdsThread(control);
            }
        };
        cmdsThread.start();        
    }
    
    public static void cmdsThread(MainWindowSceneController controlPassed){
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("CMDS Case Migration");
        totalRecordCount = 0;
        currentRecord = 0;
        sqlUsers.getNewDBUsers();
        List<casePartyModel> oldCMDScasePartyList = sqlCMDSCaseParty.getPartyList();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS Case Party");
        }
        List<CMDSCaseModel> oldCMDScaseList = sqlCMDSCase.getCaseList();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS Cases");
        }
        List<CMDSHearingModel> oldCMDSHearingList = sqlCMDSHearing.getHearingsList();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS Hearings");
        }
        List<activityModel> oldCMDSHistoryList = sqlActivity.getCMDSHistory();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS History");
        }
        List<CMDSResultModel> cmdsResultList = sqlCMDSResult.getOldCMDSResults();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS Results");
        }
        List<CMDSStatusTypeModel> cmdsStatusTypeList = sqlCMDSStatusType.getOldCMDSStatusType();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS Status Types");
        }
        List<DirectorsModel> directorList = sqlDirector.getoldDirectorList();
        if (Global.isDebug()){
            System.out.println("Gathered Directors");
        }
        List<ReClassCodeModel> reclassCodeList = sqlReClassCode.getoldReclassCodesList();
        if (Global.isDebug()){
            System.out.println("Gathered ReClass Codes");
        }
        List<CMDSHistoryCategoryModel> historyCategoryList = sqlCMDSHistoryCategory.getOldCMDSHistoryCategory();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS History Categories");
        }
        List<CMDSHistoryDescriptionModel> historyDescriptionList = sqlCMDSHistoryDescription.getOldCMDSHistoryDescription();
        if (Global.isDebug()){
            System.out.println("Gathered CMDS History Description");
        }
        List<appealCourtModel> appealCourtList = sqlAppealCourt.getAppealCourts();
        if (Global.isDebug()){
            System.out.println("Gathered Appeal Courts");
        }
        
        control.setProgressBarIndeterminateCleaning("CMDS Case");
        totalRecordCount = oldCMDScaseList.size();
        //Insert CMDS Case Data
        oldCMDScaseList.stream().forEach(item ->
                executor.submit(() ->
                        migrateSearch(item)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        
        currentRecord = 0;
        totalRecordCount = oldCMDScasePartyList.size() + oldCMDScaseList.size() + CMDSCaseSearchList.size()
                + oldCMDSHearingList.size() + oldCMDSHistoryList.size() + cmdsResultList.size()
                + cmdsStatusTypeList.size() + directorList.size() + reclassCodeList.size()
                + historyCategoryList.size() + historyDescriptionList.size() + appealCourtList.size();
                
        sqlCMDSCaseSearch.batchAddCaseSearch(CMDSCaseSearchList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + CMDSCaseSearchList.size(), totalRecordCount, "CMDS Case Search Finished");
        
        sqlAppealCourt.batchAddAppealCourt(appealCourtList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + appealCourtList.size(), totalRecordCount, "Appeal Courts Finished");
        
        sqlCMDSHistoryCategory.batchAddCMDSHistoryCategory(historyCategoryList, control, currentRecord, totalRecordCount);
        sqlCMDSHistoryCategory.batchAddHearingsHistoryCategory(historyCategoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + historyCategoryList.size(), totalRecordCount, "History Category Finished");
        
        sqlCMDSHistoryDescription.batchAddCMDSHistoryDescription(historyDescriptionList, control, currentRecord, totalRecordCount);
        sqlCMDSHistoryDescription.batchAddHearingsHistoryDescription(historyDescriptionList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + historyDescriptionList.size(), totalRecordCount, "History Description Finished");
        
        sqlReClassCode.batchAddReClassCode(reclassCodeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + reclassCodeList.size(), totalRecordCount, "ReClass Codes Finished");
        
        sqlDirector.batchAddDirector(directorList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + directorList.size(), totalRecordCount, "Directors Finished");
        
        sqlCMDSStatusType.batchAddCMDSStatusType(cmdsStatusTypeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + cmdsStatusTypeList.size(), totalRecordCount, "CMDS Status Types Finished");
        
        sqlCMDSResult.batchAddCMDSResult(cmdsResultList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + cmdsResultList.size(), totalRecordCount, "CMDS Results Finished");
        
        sqlCaseParty.batchAddPartyInformation(oldCMDScasePartyList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldCMDScasePartyList.size(), totalRecordCount, "CMDS Case Parties Finished");
        
        sqlCMDSHearing.batchAddHearings(oldCMDSHearingList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldCMDSHearingList.size(), totalRecordCount, "CMDS Hearings Finished");
        
        sqlActivity.batchAddActivity(oldCMDSHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldCMDSHistoryList.size(), totalRecordCount, "CMDS Activity Finished");
        
        sqlCMDSCase.batchAddCase(oldCMDScaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldCMDScaseList.size(), totalRecordCount, "CMDS Case Finished");
        
        CMDSCaseSearchList.clear();
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CMDS Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCMDSCases");
        }
        SlackNotification.sendNotification(finishedText);
    }
    
    private static void migrateSearch(CMDSCaseModel item) {
        int aljID = StringUtilities.convertUserToID(item.getAljID());
        String appellant = "";
        String appellee = "";
        String ALJName = "";
        List<casePartyModel> partyList = sqlCMDSCaseParty.getPartyByCase(item.getCaseYear(), item.getCaseNumber());
        
        for (casePartyModel person : partyList){
            if (person.getCaseRelation().contains("Appellee")){
                if (!appellee.trim().equals("")){
                    appellee += ", ";
                }
                appellee += StringUtilities.buildFullName(person.getFirstName(), person.getMiddleInitial(), person.getLastName());
                
            } else if (person.getCaseRelation().contains("Appellant")) {
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
        
        search.setCaseYear(item.getCaseYear() == null ? "" : item.getCaseYear());
        search.setCaseType(item.getCaseType() == null ? "" : item.getCaseType());
        search.setCaseMonth(item.getCaseMonth() == null ? "" : item.getCaseMonth());
        search.setCaseNumber(item.getCaseNumber() == null ? "" : item.getCaseNumber());
        search.setAppellant(appellant.trim().equals("") ? null : appellant);
        search.setAppellee(appellee.trim().equals("") ? null : appellee);
        search.setAlj(ALJName.trim().equals("") ? null : ALJName);
        search.setDateOpened(item.getOpenDate() == null ? null : item.getOpenDate());
        
        CMDSCaseSearchList.add(search);
        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
    }
        
}
