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
public class MEDMigration {
    
    public static void migrateMEDData(final MainWindowSceneController control){
        Thread medThread = new Thread() {
            @Override
            public void run() {
                medThread(control);
            }
        };
        medThread.start();        
    }
    
    private static void medThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("MED Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating MED Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateMEDCases");
    }
}
