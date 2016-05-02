/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.util.StringUtilities;

/**
 *
 * @author Andrew
 */
public class DocumentMigration {
    
    public static void migrateDocuments(MainWindowSceneController control){
        Thread docThread = new Thread() {
            @Override
            public void run() {
                docThread(control);
            }
        };
        docThread.start();        
    }
    
    private static void docThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Documents Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
//        List<userModel> oldUserList = sqlUsers.getUsers();
//        totalRecordCount = oldUserList.size();
//        
//        for (userModel item : oldUserList){
//            migrateDocument(item);
//            currentRecord++;
//            if (Global.isDebug()){
//                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getUserName().trim() + ")");
//            }
//            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
//        }
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Documents: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateDocuments");
    }
        
    private static void migrateDocument(userModel item){
        
    }
    
}
