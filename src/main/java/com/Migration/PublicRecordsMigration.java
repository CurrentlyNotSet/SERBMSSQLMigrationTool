/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.oldPublicRecordsModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlMigrationStatus;
import com.sql.sqlPublicRecords;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class PublicRecordsMigration {
    
    public static void migratePublicRecordsData(final MainWindowSceneController control){
        Thread pRecordsThread = new Thread() {
            @Override
            public void run() {
                publicRecordsThread(control);
            }
        };
        pRecordsThread.start();
    }
    
    public static void publicRecordsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Public Records Migration");
        
        List<oldPublicRecordsModel> oldPublicRecords = sqlPublicRecords.getOldPublicRecords();
                   
        sqlActivity.batchAddPublicRecordActivity(oldPublicRecords, control);
        SceneUpdater.listItemFinished(control, oldPublicRecords.size(), oldPublicRecords.size(), "Public Records Finished");
   
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Public Records: " 
                + oldPublicRecords.size() + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigratePublicRecords");
        }
    }
}
