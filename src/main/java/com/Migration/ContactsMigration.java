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
        
        List<casePartyModel> masterSERBList = sqlContactList.getSERBMasterList();
        List<casePartyModel> masterPBRList = sqlContactList.getPBRMasterList();
        List<casePartyModel> representativeList = sqlContactList.getRepresentativeList();
        totalRecordCount = masterSERBList.size() + masterPBRList.size() + representativeList.size();
        
        sqlContactList.batchAddPartyInformation(masterSERBList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, masterSERBList.size(), totalRecordCount, "SERB Contacts Finished");
        
        sqlContactList.batchAddPartyInformation(masterPBRList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + masterPBRList.size(), totalRecordCount, "SPBR Contacts Finished");

        sqlContactList.batchAddPartyInformation(representativeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + representativeList.size(), totalRecordCount, "Representatives Finished");
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Contacts: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateContacts");
        }
    }
    
}
