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
import com.model.activityModel;
import com.model.casePartyModel;
import com.model.oldCMDSCaseModel;
import com.model.oldCMDSCasePartyModel;
import com.model.oldCMDSHistoryModel;
import com.model.oldCMDShearingModel;
import com.model.DirectorsModel;
import com.model.ReClassCodeModel;
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
    
    private static void cmdsThread(MainWindowSceneController controlPassed){
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("CMDS Case Migration");
        totalRecordCount = 0;
        currentRecord = 0;
        
        sqlUsers.getNewDBUsers();
        
        List<casePartyModel> oldCMDScasePartyList = sqlCMDSCaseParty.getPartyList();
        List<CMDSCaseModel> oldCMDScaseList = sqlCMDSCase.getCaseList();
        List<CMDSHearingModel> oldCMDSHearingList = sqlCMDSHearing.getHearingsList();
        List<oldCMDSHistoryModel> oldCMDSHistoryList = sqlActivity.getCMDSHistory();
        List<CMDSResultModel> cmdsResultList = sqlCMDSResult.getOldCMDSResults();
        List<CMDSStatusTypeModel> cmdsStatusTypeList = sqlCMDSStatusType.getOldCMDSStatusType();
        List<DirectorsModel> directorList = sqlDirector.getoldDirectorList();
        List<ReClassCodeModel> reclassCodeList = sqlReClassCode.getoldReclassCodesList();
        
        List<CMDSHistoryCategoryModel> historyCategoryList = sqlCMDSHistoryCategory.getOldCMDSHistoryCategory();
        List<CMDSHistoryDescriptionModel> historyDescriptionList = sqlCMDSHistoryDescription.getOldCMDSHistoryDescription();
        List<appealCourtModel> appealCourtList = sqlAppealCourt.getOldCMDSHistoryDescription();
                
        totalRecordCount = oldCMDScasePartyList.size() + oldCMDScaseList.size() 
                + oldCMDSHearingList.size() + oldCMDSHistoryList.size() + cmdsResultList.size()
                + cmdsStatusTypeList.size() + directorList.size() + reclassCodeList.size()
                + historyCategoryList.size() + historyDescriptionList.size() + appealCourtList.size();
        
        control.setProgressBarIndeterminateCleaning("CMDS Case");
        //Insert CMDS Case Data
        oldCMDScaseList.stream().forEach(item -> 
                executor.submit(() -> 
                        migrateSearch(item)));
        
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        
        sqlCMDSCaseSearch.batchAddCaseSearch(CMDSCaseSearchList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + CMDSCaseSearchList.size(), totalRecordCount, "CMDS Case Search Finished");
        
        sqlAppealCourt.batchAddAppealCourt(appealCourtList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + appealCourtList.size(), totalRecordCount, "Appeal Courts Finished");
        
        sqlCMDSHistoryCategory.batchAddCMDSHistoryCategory(historyCategoryList);
        sqlCMDSHistoryCategory.batchAddHearingsHistoryCategory(historyCategoryList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + historyCategoryList.size(), totalRecordCount, "History Category Finished");
        
        sqlCMDSHistoryDescription.batchAddCMDSHistoryDescription(historyDescriptionList);
        sqlCMDSHistoryDescription.batchAddHearingsHistoryDescription(historyDescriptionList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + historyDescriptionList.size(), totalRecordCount, "History Description Finished");
        
        sqlReClassCode.batchAddReClassCode(reclassCodeList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + reclassCodeList.size(), totalRecordCount, "ReClass Codes Finished");
        
        sqlDirector.batchAddDirector(directorList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + directorList.size(), totalRecordCount, "Directors Finished");
        
        sqlCMDSStatusType.batchAddCMDSStatusType(cmdsStatusTypeList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + cmdsStatusTypeList.size(), totalRecordCount, "CMDS Status Types Finished");
        
        sqlCMDSResult.batchAddCMDSResult(cmdsResultList);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + cmdsResultList.size(), totalRecordCount, "CMDS Results Finished");
                
        sqlCaseParty.batchAddPartyInformation(oldCMDScasePartyList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord - 1, totalRecordCount, "CMDS Case Parties Finished");
        
        sqlCMDSHearing.batchAddHearings(oldCMDSHearingList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord - 1, totalRecordCount, "CMDS Hearings Finished");
        
        sqlActivity.batchAddCMDSActivity(oldCMDSHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord - 1, totalRecordCount, "CMDS Activity Finished");
        
        sqlCMDSCase.batchAddCase(oldCMDScaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord - 1, totalRecordCount, "CMDS Case Finished");
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CMDS Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCMDSCases");
        }
    }
    
    private static void migrateSearch(CMDSCaseModel item) {
        int aljID = StringUtilities.convertUserToID(item.getAljID().trim() == null ? "" : item.getAljID());
        String appellant = "";
        String appellee = "";
        String ALJName = "";
        List<oldCMDSCasePartyModel> partyList = sqlCMDSCaseParty.getPartyByCase(item.getCaseYear(), item.getCaseNumber());
        
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
        
        search.setCaseYear(item.getCaseYear() == null ? "" : item.getCaseYear());
        search.setCaseType(item.getCaseType() == null ? "" : item.getCaseType());
        search.setCaseMonth(item.getCaseMonth() == null ? "" : item.getCaseMonth());
        search.setCaseNumber(item.getCaseNumber() == null ? "" : item.getCaseNumber());
        search.setAppellant(appellant.trim().equals("") ? null : appellant);
        search.setAppellee(appellee.trim().equals("") ? null : appellee);
        search.setAlj(ALJName.trim().equals("") ? null : ALJName);
        search.setDateOpened(item.getOpenDate() == null ? null : item.getOpenDate());
        
        CMDSCaseSearchList.add(search);
    }
        
}
