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
public class ContactsMigration {
    
    public static void migrateContacts(final MainWindowSceneController control){
        Thread contactsThread;
        
        contactsThread = new Thread() {
            @Override
            public void run() {
                contactsThread(control);
            }
        };
        contactsThread.start();        
    }
    
    private static void contactsThread(MainWindowSceneController control){
        control.setProgressBarIndeterminate("Contact Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateContacts");
    }
}
