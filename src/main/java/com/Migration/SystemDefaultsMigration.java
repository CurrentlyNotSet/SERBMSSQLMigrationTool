/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.oldCountyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.sql.sqlSystemData;
import com.util.Global;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class SystemDefaultsMigration {
    
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
        List<oldCountyModel> oldULPDataList = sqlSystemData.getCounties();
        totalRecordCount = oldULPDataList.size();
        
        for (oldCountyModel item : oldULPDataList){
            migrateCounty(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getCountyCode().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating System Defaults: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateSystemDefaults");
        }
    }
    
    
    private static void migrateCounty(oldCountyModel item) {
        item.setActive(("OH".equals(item.getStateCode().trim())) ? 1 : 0);
        sqlSystemData.addCounty(item);
    }
    
}
