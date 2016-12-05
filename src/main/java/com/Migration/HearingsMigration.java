/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.HearingsCaseModel;
import com.model.HearingsMediationModel;
import com.model.activityModel;
import com.model.caseNumberModel;
import com.model.oldHearingsMediationModel;
import com.model.oldSMDSCaseTrackingModel;
import com.model.oldSMDSHistoryModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlHearingsCase;
import com.sql.sqlHearingsMediation;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class HearingsMigration {
    
    public static void migrateHearingsData(final MainWindowSceneController control){
        Thread hearingThread = new Thread() {
            @Override
            public void run() {
                hearingsThread(control);
            }
        };
        hearingThread.start();        
    }
    
    private static void hearingsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Hearings Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<oldSMDSCaseTrackingModel> oldHearingCaseList = sqlHearingsCase.getCases();
        List<oldSMDSHistoryModel> oldHearingsHistoryList = sqlActivity.getSMDSHistory();
        List<oldHearingsMediationModel> oldHearingsMediationList = sqlHearingsMediation.getHearingsMediations();
        
        totalRecordCount = oldHearingCaseList.size() + oldHearingsHistoryList.size() + oldHearingsMediationList.size();
                
        for (oldSMDSCaseTrackingModel item : oldHearingCaseList) {
            migrateCase(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseNumber());
        }
        
        for (oldSMDSHistoryModel item : oldHearingsHistoryList) {
            migrateHistory(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getAction());
        }
        
        for (oldHearingsMediationModel item : oldHearingsMediationList) {
            migrateMediations(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
        }
                
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Hearings Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateHearingsCases");
        }
    }
        
    private static void migrateCase(oldSMDSCaseTrackingModel item) {
        migrateCaseData(item);
    }
    
    private static void migrateCaseData(oldSMDSCaseTrackingModel old) {
        caseNumberModel caseNumber = null;
        if (old.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(old.getCaseNumber().trim());
            
            HearingsCaseModel item = new HearingsCaseModel();
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setOpenClose(old.getOpenYN().equals("Y") ? "Open" : "Closed");
            item.setExpedited(old.getExpeditedYN().equals("Y"));
            item.setBoardActionPCDate(old.getBoardActionPCDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBoardActionPCDate())));

            sqlHearingsCase.importOldHearingsCase(item);
        }
        
    }

    private static void migrateHistory(oldSMDSHistoryModel old) {
        caseNumberModel caseNumber = null;
        if (old.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(old.getCaseNumber().trim());
        }
        
        if(caseNumber != null){
            activityModel item = new activityModel();
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setUserID(StringUtilities.convertUserToID(old.getUserName()));
            item.setDate(StringUtilities.convertStringDateAndTime(old.getDate(), old.getTime()));
            item.setAction(!"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
            item.setFileName(!"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
            item.setFrom(null);
            item.setTo(null);
            item.setType(null);
            item.setComment(null);
            item.setRedacted(0);
            item.setAwaitingTimeStamp(0);

            sqlActivity.addActivity(item);
        }
    }
    
    private static void migrateMediations(oldHearingsMediationModel old) {  
        caseNumberModel caseNumber = null;
        if (old.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(old.getCaseNumber().trim());
        }
        
        if(caseNumber != null){
            HearingsMediationModel item = new HearingsMediationModel();
            
            item.setActive(old.getActive() == 1);
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setPCPreD(old.getPCPreD().trim().equals("") ? null : old.getPCPreD().trim());
            item.setMediator(StringUtilities.convertUserInitialToID(old.getMediatorInitials()));
            item.setDateAssigned(StringUtilities.convertStringSQLDate(old.getDateAssigned()));
            item.setMediationDate(StringUtilities.convertStringSQLDate(old.getMedDate()));
            item.setOutcome(old.getOutcome().equals("") ? null : old.getOutcome().trim());
            
            sqlHearingsMediation.importOldHearingsMediation(item);
        }
    }

}
