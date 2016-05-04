/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.partyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlContactList;
import com.sql.sqlMigrationStatus;
import com.util.Global;
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
    
    private static void contactsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Contact Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<partyModel> masterList = sqlContactList.getMasterList();
        totalRecordCount = masterList.size();
        
        for (partyModel item : masterList){
            migrateContact(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getLastName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Contacts: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateContacts");
        }
    }
    
    private static void migrateContact(partyModel item) {
        sqlContactList.savePartyInformation(seperateName(item));
    }
    
    private static partyModel seperateName(partyModel item) {
        String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
        
        if (nameSplit.length == 2 && nameSplit[1].trim().endsWith(".") == false){
//2p:
            item.setFirstName(nameSplit[0].trim());
            item.setLastName(nameSplit[1].trim());
            return item;
        } else if (nameSplit.length == 3) {
            if ((nameSplit[1].trim().length() == 2 && nameSplit[1].trim().endsWith(".")) 
                    || nameSplit[1].trim().length() == 1){
//3p: MI
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleInitial(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
                return item;
            } else if (nameSplit[0].trim().endsWith(".")){
//3p: Prefix
                item.setPrefix(nameSplit[0].trim());
                item.setFirstName(nameSplit[1].trim());
                item.setLastName(nameSplit[2].trim());
                return item;
            } else if (nameSplit[2].trim().endsWith(".")){
//3p: Suffix
                item.setFirstName(nameSplit[0].trim());
                item.setLastName(nameSplit[1].trim());
                if (nameSplit[2].trim().startsWith("esq")){
                    item.setNameTitle(nameSplit[2].trim());
                } else {
                    item.setSuffix(nameSplit[2].trim());
                }
                return item;
            }
        } else if (nameSplit.length == 4) {
            if(nameSplit[0].trim().endsWith(".") && nameSplit[1].trim().endsWith(".") == false  
                    && nameSplit[3].trim().endsWith(".")){
//4p: Prefix && Suffix                
                item.setPrefix(nameSplit[0].trim());
                item.setFirstName(nameSplit[1].trim());
                item.setLastName(nameSplit[2].trim());
                if (nameSplit[3].trim().startsWith("esq")){
                    item.setNameTitle(nameSplit[3].trim());
                } else {
                    item.setSuffix(nameSplit[3].trim());
                }
                return item;
            } else if(nameSplit[0].trim().endsWith(".") && nameSplit[1].trim().endsWith(".") == false 
                    && (nameSplit[2].trim().length() == 2 
                    && nameSplit[2].trim().endsWith(".")) || nameSplit[2].trim().length() == 1){
//4p: Prefix && MI                
                item.setPrefix(nameSplit[0].trim());
                item.setFirstName(nameSplit[1].trim());
                item.setMiddleInitial(nameSplit[2].trim());
                item.setLastName(nameSplit[3].trim());
                return item;
            }   
        }
        return item;
    }
    
}
