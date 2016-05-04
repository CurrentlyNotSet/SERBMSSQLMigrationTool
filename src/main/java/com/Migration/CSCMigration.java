/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.StringUtilities;

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
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CSC Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCSCCases");
        }
    }
}
