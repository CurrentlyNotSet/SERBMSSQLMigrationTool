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
public class CMDSMigration {
    
    public static void migrateCMDSData(final MainWindowSceneController control){
        Thread cmdsThread;
        
        cmdsThread = new Thread() {
            @Override
            public void run() {
                cmdsThread(control);
            }
        };
        cmdsThread.start();        
    }
    
    private static void cmdsThread(MainWindowSceneController control){
        control.setProgressBarIndeterminate("CMDS Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateCMDSCases");
    }
}
