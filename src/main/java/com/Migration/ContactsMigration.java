/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.casePartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlContactList;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class ContactsMigration {
    
    public static void migrateContacts(final MainWindowSceneController control){
        Thread contactsThread = new Thread() {
            @Override
            public void run() {
                contactsThread(control);
            }
        };
        contactsThread.start();
    }
    
    public static void contactsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Contact Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<casePartyModel> masterContactList = new ArrayList<>();
        masterContactList.addAll(sqlContactList.getSERBMasterList());
        masterContactList.addAll(sqlContactList.getPBRMasterList());
        masterContactList.addAll(sqlContactList.getRepresentativeList());
        totalRecordCount = masterContactList.size();
        
        sqlContactList.batchAddPartyInformation(masterContactList, control, currentRecord, totalRecordCount);
        SceneUpdater.listItemFinished(control, masterContactList.size(), totalRecordCount, "Contacts Finished");
                
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Contacts: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateContacts");
        }
    }
    
}
