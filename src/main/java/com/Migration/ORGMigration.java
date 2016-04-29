/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.util.StringUtilities;

/**
 *
 * @author Andrew
 */
public class ORGMigration {
    
    public static void migrateORGData(final MainWindowSceneController control){
        Thread orgThread = new Thread() {
            @Override
            public void run() {
                orgThread(control);
            }
        };
        orgThread.start();        
    }
    
    private static void orgThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("ORG Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating ORG Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateORGCases");
    }
}
