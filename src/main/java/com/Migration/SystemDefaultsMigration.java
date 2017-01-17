/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

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
                + hearingTypeList.size();
        
        //import
        sqlSystemData.batchAddCounty(oldCountiesList);
        currentRecord = SceneUpdater.listItemFinished(control, oldCountiesList.size() + currentRecord, totalRecordCount, "Counties Finished");
        
        sqlPartyType.batchAddPartyType(partyTypesList);
        currentRecord = SceneUpdater.listItemFinished(control, partyTypesList.size() + currentRecord, totalRecordCount, "Party Type Finished");
        
        sqlDeptInState.batchAddDeptInState(deptInStateList);
        currentRecord = SceneUpdater.listItemFinished(control, deptInStateList.size() + currentRecord, totalRecordCount, "Dept In State Finished");
        
        sqlSystemEmail.batchAddSystemEmail(systemEmailList);
        currentRecord = SceneUpdater.listItemFinished(control, systemEmailList.size() + currentRecord, totalRecordCount, "System Email Finished");
        
        sqlPreFix.addNamePrefix(Global.getNamePrefixList());
        currentRecord = SceneUpdater.listItemFinished(control, Global.getNamePrefixList().size() + currentRecord, totalRecordCount, "PreFixes Finished");
        
        sqlActivityType.batchAddActivtyType(activityTypeList);
        currentRecord = SceneUpdater.listItemFinished(control, activityTypeList.size() + currentRecord, totalRecordCount, "Activity Types Finished");
        
        sqlCaseType.batchAddCaseType(caseTypeList);
        currentRecord = SceneUpdater.listItemFinished(control, caseTypeList.size() + currentRecord, totalRecordCount, "Case Types Finished");
        
        sqlHearingsInfo.batchAddHearingRoom(hearingRoomList);
        currentRecord = SceneUpdater.listItemFinished(control, hearingRoomList.size() + currentRecord, totalRecordCount, "Hearing Rooms Finished");
        
        sqlHearingsInfo.batchAddHearingType(hearingTypeList);
        currentRecord = SceneUpdater.listItemFinished(control, hearingTypeList.size() + currentRecord, totalRecordCount, "Hearing Types Finished");
        
        sqlSystemExecutive.batchAddExecutive(execList);
        currentRecord = SceneUpdater.listItemFinished(control, execList.size() + currentRecord, totalRecordCount, "Execs Finished");
        
        sqlAdministrationInformation.batchAddAdminInfo(systemInfoList);
        currentRecord = SceneUpdater.listItemFinished(control, systemInfoList.size() + currentRecord, totalRecordCount, "Admin Finished");
               
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating System Defaults: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateSystemDefaults");
        }
    }

    private static void migrateExec(systemExecutiveModel item) {
        String[] nameSplit = item.getLastName().split(" ");
        
        switch (nameSplit.length) {
            case 2:
                item.setFirstName(nameSplit[0]);
                item.setLastName(nameSplit[1]);
                break;
            case 3:
                item.setFirstName(nameSplit[0]);
                item.setMiddleName(nameSplit[1]);
                item.setLastName(nameSplit[2]);
                break;
            default:
                item.setFirstName(null);
                item.setMiddleName(null);
                item.setLastName(null);
                break;
        }
        
        
        item.setPosition("".equals(item.getPosition()) ? null : item.getPosition());
        item.setPhone("".equals(item.getPhone().replaceAll("[^0-9]", "")) ? null : item.getPhone().replaceAll("[^0-9]", ""));
        item.setEmail("".equals(item.getEmail()) ? null : item.getEmail());
                
//        sqlSystemExecutive.batchAddExecutive(item);
    }
    
    private static void migrateAdministrationInformation(administrationInformationModel item) {
        String[] cityStateZipSplit = item.getCity().replaceAll("  ", " ").split(" ");
                
        item.setGovernorName("".equals(item.getGovernorName()) ? null : item.getGovernorName());
        item.setLtGovernorName("".equals(item.getLtGovernorName()) ? null : item.getLtGovernorName());
        item.setAddress1("".equals(item.getAddress1()) ? null : item.getAddress1());
        item.setCity("".equals(cityStateZipSplit[0]) ? null : cityStateZipSplit[0].trim());
        item.setState("".equals(cityStateZipSplit[1]) ? null : cityStateZipSplit[1].trim());
        item.setZip("".equals(cityStateZipSplit[2]) ? null : cityStateZipSplit[2].trim());
        item.setUrl("".equals(item.getUrl()) ? null : item.getUrl());
        item.setPhone("".equals(item.getPhone().replaceAll("[^0-9]", "")) ? null : item.getPhone().replaceAll("[^0-9]", ""));
        item.setFax("".equals(item.getFax().replaceAll("[^0-9]", "")) ? null : item.getFax().replaceAll("[^0-9]", ""));
        item.setFooter("".equals(item.getFooter()) ? null : item.getFooter());
        
//        sqlAdministrationInformation.batchAddAdminInfo(item);
    }
    
}
