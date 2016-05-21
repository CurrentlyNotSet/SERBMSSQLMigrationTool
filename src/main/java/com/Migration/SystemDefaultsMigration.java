/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.BoardAcionTypeModel;
import com.model.SystemEmailModel;
import com.model.deptInStateModel;
import com.model.oldCountyModel;
import com.model.partyTypeModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlBoardActionType;
import com.sql.sqlDeptInState;
import com.sql.sqlMigrationStatus;
import com.sql.sqlPartyType;
import com.sql.sqlPreFix;
import com.sql.sqlSystemData;
import com.sql.sqlSystemEmail;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.Arrays;
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
        List<SystemEmailModel> systemEmailList = sqlSystemEmail.getOldSystemEmail();
        List<partyTypeModel> partyTypesList = sqlPartyType.getPartyTypeList();
        
        List<String> namePrefixList = Arrays.asList(
            "Ms.", "Miss.", "Mrs.", "Mr.", "Rev.", "Fr.", "Dr.", "Atty.", 
            "Prof.", "Hon.", "Pres.", "Gov.", "Coach.", "Ofc.", "Supt.", 
            "Rep.", "Sen.", "Treas.", "Sec.", "Adm." );
        
        totalRecordCount = oldCountiesList.size() + deptInStateList.size() + systemEmailList.size() 
                 + namePrefixList.size() + partyTypesList.size();
        
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
        
        for (SystemEmailModel item : systemEmailList){
            sqlSystemEmail.addSystemEmail(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getEmailAddress());
        }
        
        for (String item : namePrefixList){
            sqlPreFix.addNamePrefix(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item);
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
