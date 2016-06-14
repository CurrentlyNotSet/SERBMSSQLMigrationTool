/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.caseTypeModel;
import com.model.systemEmailModel;
import com.model.deptInStateModel;
import com.model.historyTypeModel;
import com.model.oldCountyModel;
import com.model.partyTypeModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivityType;
import com.sql.sqlCaseType;
import com.sql.sqlDeptInState;
import com.sql.sqlMigrationStatus;
import com.sql.sqlPartyType;
import com.sql.sqlPreFix;
import com.sql.sqlSystemData;
import com.sql.sqlSystemEmail;
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
    
    private static void sysThread(MainWindowSceneController control){
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
        
        totalRecordCount = oldCountiesList.size() + deptInStateList.size() + systemEmailList.size() 
                 + Global.getNamePrefixList().size() + partyTypesList.size() + activityTypeList.size();
        
        for (oldCountyModel item : oldCountiesList){
            item.setActive(("OH".equals(item.getStateCode().trim())) ? 1 : 0);
            sqlSystemData.addCounty(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCountyCode().trim());
        }
        
        for (partyTypeModel item : partyTypesList){
            sqlPartyType.addPartyType(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getSection() + ": " + item.getName());
        }
        
        for (deptInStateModel item : deptInStateList){
            sqlDeptInState.addDeptInState(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getStateCode().trim());
        }
        
        for (systemEmailModel item : systemEmailList){
            sqlSystemEmail.addSystemEmail(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getEmailAddress());
        }
        
        for (String item : Global.getNamePrefixList()){
            sqlPreFix.addNamePrefix(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item);
        }
        
        for (historyTypeModel item : activityTypeList){
            sqlActivityType.addActivtyType(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getFileAttrib());
        }
        
        for (caseTypeModel item : caseTypeList){
            sqlCaseType.addCaseType(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseType());
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating System Defaults: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateSystemDefaults");
        }
    }

}
