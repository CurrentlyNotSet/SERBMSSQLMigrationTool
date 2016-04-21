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
public class MEDMigration {
    
    public static void migrateMEDData(final MainWindowSceneController control){
        Thread medThread;
        
        medThread = new Thread() {
            @Override
            public void run() {
                medThread(control);
            }
        };
        medThread.start();        
    }
    
    private static void medThread(MainWindowSceneController control){
        control.setProgressBarIndeterminate("MED Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateMEDCases");
    }
}
