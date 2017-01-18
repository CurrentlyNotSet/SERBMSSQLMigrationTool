/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.NextCaseNumberModel;
import com.model.administrationInformationModel;
import com.model.caseTypeModel;
import com.model.systemEmailModel;
import com.model.deptInStateModel;
import com.model.hearingRoomModel;
import com.model.hearingTypeModel;
import com.model.historyTypeModel;
import com.model.oldCountyModel;
import com.model.partyTypeModel;
import com.model.systemExecutiveModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivityType;
import com.sql.sqlAdministrationInformation;
import com.sql.sqlCaseType;
import com.sql.sqlDeptInState;
import com.sql.sqlHearingsInfo;
import com.sql.sqlMigrationStatus;
import com.sql.sqlNextCaseNumber;
import com.sql.sqlPartyType;
import com.sql.sqlPreFix;
import com.sql.sqlSystemData;
import com.sql.sqlSystemEmail;
import com.sql.sqlSystemExecutive;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class SystemDefaultsMigration { 
    /**
     * 
     * @param control 
     */
    public static void migrateSystemData(MainWindowSceneController control){
        Thread sysThread = new Thread() {
            @Override
            public void run() {
                sysThread(control);
            }
        };
        sysThread.start();
    }
    
    public static void sysThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("System Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        List<NextCaseNumberModel> oldNextNumber = sqlNextCaseNumber.getOldCaseNextNumber();
        List<oldCountyModel> oldCountiesList = sqlSystemData.getCounties();
        List<deptInStateModel> deptInStateList = sqlDeptInState.getOldDeptInState();
        List<systemEmailModel> systemEmailList = sqlSystemEmail.getOldSystemEmail();
        List<partyTypeModel> partyTypesList = sqlPartyType.getPartyTypeList();
        List<historyTypeModel> activityTypeList = sqlActivityType.getOldActivityType();
        List<caseTypeModel> caseTypeList = sqlCaseType.getOldCaseType();
        List<systemExecutiveModel> execList = sqlSystemExecutive.getOldExecutives();
        List<administrationInformationModel> systemInfoList = sqlAdministrationInformation.getOldInfo();
        List<hearingRoomModel> hearingRoomList = sqlHearingsInfo.getOldHearingRoom();
        List<hearingTypeModel> hearingTypeList = sqlHearingsInfo.getOldHearingType();
        
        totalRecordCount = oldCountiesList.size() + deptInStateList.size() + systemEmailList.size() 
                + Global.getNamePrefixList().size() + partyTypesList.size() + activityTypeList.size()
                + caseTypeList.size() + execList.size() + systemInfoList.size() + hearingRoomList.size()
                + hearingTypeList.size() + oldNextNumber.size();
        
        //import
        sqlNextCaseNumber.batchAddNextCaseNumber(oldNextNumber, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldNextNumber.size() + currentRecord, totalRecordCount, "Next Numbers Finished");
        
        sqlSystemData.batchAddCounty(oldCountiesList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldCountiesList.size() + currentRecord, totalRecordCount, "Counties Finished");
        
        sqlPartyType.batchAddPartyType(partyTypesList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, partyTypesList.size() + currentRecord, totalRecordCount, "Party Type Finished");
        
        sqlDeptInState.batchAddDeptInState(deptInStateList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, deptInStateList.size() + currentRecord, totalRecordCount, "Dept In State Finished");
        
        sqlSystemEmail.batchAddSystemEmail(systemEmailList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, systemEmailList.size() + currentRecord, totalRecordCount, "System Email Finished");
        
        sqlPreFix.batchAddNamePrefix(Global.getNamePrefixList(), control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, Global.getNamePrefixList().size() + currentRecord, totalRecordCount, "PreFixes Finished");
        
        sqlActivityType.batchAddActivtyType(activityTypeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, activityTypeList.size() + currentRecord, totalRecordCount, "Activity Types Finished");
        
        sqlCaseType.batchAddCaseType(caseTypeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, caseTypeList.size() + currentRecord, totalRecordCount, "Case Types Finished");
        
        sqlHearingsInfo.batchAddHearingRoom(hearingRoomList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, hearingRoomList.size() + currentRecord, totalRecordCount, "Hearing Rooms Finished");
        
        sqlHearingsInfo.batchAddHearingType(hearingTypeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, hearingTypeList.size() + currentRecord, totalRecordCount, "Hearing Types Finished");
        
        sqlSystemExecutive.batchAddExecutive(execList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, execList.size() + currentRecord, totalRecordCount, "Execs Finished");
        
        sqlAdministrationInformation.batchAddAdminInfo(systemInfoList, control, currentRecord, totalRecordCount);
        SceneUpdater.listItemFinished(control, systemInfoList.size() + currentRecord, totalRecordCount, "Admin Finished");
               
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating System Defaults: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateSystemDefaults");
        }
    }
    
}
