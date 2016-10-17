/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.casePartyModel;
import com.model.oldCMDSCasePartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlContactList;
import com.sql.sqlMigrationStatus;
import com.util.ContactNameSeperator;
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
    
    private static void contactsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Contact Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<casePartyModel> masterSERBList = sqlContactList.getSERBMasterList();
        List<oldCMDSCasePartyModel> masterPBRList = sqlContactList.getPBRMasterList();
        totalRecordCount = masterSERBList.size();
        
        for (casePartyModel item : masterSERBList){
            sqlContactList.savePartyInformation(ContactNameSeperator.seperateName(item));
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
                    (item.getLastName() != null) ? item.getLastName() : item.getCompanyName());
        }
        
        for (oldCMDSCasePartyModel item : masterPBRList){
            migratePBRParty(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
                    (item.getFirstName() + " " + item.getLastName()));
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Contacts: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateContacts");
        }
    }
            
    private static void migratePBRParty(oldCMDSCasePartyModel item) {
        casePartyModel party = new  casePartyModel();
        party.setFirstName(item.getFirstName().trim().equals("") ? null : item.getFirstName().trim());
        party.setMiddleInitial(item.getMiddleInitial().trim().equals("") ? null : item.getMiddleInitial().trim());
        party.setLastName(item.getLastName().trim().equals("") ? null : item.getLastName().trim());
        party.setJobTitle(item.getTitle().trim().equals("") ? null : item.getTitle().trim());
        party.setNameTitle(item.getEtalextraname().trim().equals("") ? null : item.getEtalextraname().trim());
        party.setAddress1(item.getAddress1().trim().equals("") ? null : item.getAddress1().trim());
        party.setAddress2(item.getAddress2().trim().equals("") ? null : item.getAddress2().trim());
        party.setCity(item.getCity().trim().equals("") ? null : item.getCity().trim());
        party.setState(item.getState().trim().equals("") ? null : item.getState().trim());
        party.setZip(item.getZip().trim().equals("") ? null : item.getZip().trim());
        party.setFax((!item.getFax().trim().equals("null") || !item.getFax().trim().equals("")) 
                ? StringUtilities.convertPhoneNumberToString(item.getFax().trim()) : null);  
        party.setEmailAddress(item.getEmail().trim().equals("") ? null : item.getEmail().trim());
        party.setPhoneOne(!item.getOfficePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getOfficePhone().trim()) : null);
        party.setPhoneTwo(!item.getCellularPhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getCellularPhone().trim()) : null);
        
        if (party.getPhoneOne() == null && !item.getHomePhone().equals("")){
            party.setPhoneOne(!item.getHomePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getHomePhone().trim()) : null);
        } else if (party.getPhoneTwo() == null && !item.getHomePhone().equals("")){
            party.setPhoneTwo(!item.getHomePhone().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getHomePhone().trim()) : null);
        }
                
        if (item.getActive() == 1){
            sqlContactList.savePartyInformation(party);
        }
    }
    
}
