/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;

/**
 *
 * @author Andrew
 */
public class CSCMigration {
    
    public static void migrateCSCData(final MainWindowSceneController control){
        Thread cscThread;
        
        cscThread = new Thread() {
            @Override
            public void run() {
                cscThread(control);
            }
        };
        cscThread.start();        
    }
    
    private static void cscThread(MainWindowSceneController control){
        control.setProgressBarIndeterminate("CSC Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateCSCCases");
    }
}
