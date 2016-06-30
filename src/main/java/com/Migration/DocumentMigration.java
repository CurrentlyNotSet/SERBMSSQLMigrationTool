/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.oldDocumentModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlDocument;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

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
        List<oldDocumentModel> oldDocumentList = sqlDocument.getOldDocuments();
        totalRecordCount = oldDocumentList.size();
        
        for (oldDocumentModel item : oldDocumentList){
            migrateDocument(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getSection() + ": " + item.getDocumentFileName());
        }

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Documents: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateDocuments");
        }
    }
    
    private static void migrateDocument(oldDocumentModel item){
        if (item.getSection().trim().equalsIgnoreCase("ULP") && item.getType().trim().equalsIgnoreCase("letter")){
            if (item.getDocumentFileName().contains("IL") || item.getDocumentFileName().contains("ILP")){
                item.setDueDate(21);
            } else if (item.getDocumentFileName().contains("FR") || item.getDocumentFileName().contains("LTR") 
                    || item.getDocumentFileName().contains("RECON") || item.getDocumentFileName().contains("Suf")){
                item.setDueDate(7);
            }
        }        
        sqlDocument.addSMDSDocument(item);
    }
    
}
