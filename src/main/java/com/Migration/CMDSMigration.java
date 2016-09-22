/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CMDSCaseModel;
import com.model.casePartyModel;
import com.model.oldCMDSCaseModel;
import com.model.oldCMDSCasePartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlCMDSCase;
import com.sql.sqlCMDSCaseParty;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class CMDSMigration {
    
    public static void migrateCMDSData(final MainWindowSceneController control){
        Thread cmdsThread = new Thread() {
            @Override
            public void run() {
                cmdsThread(control);
            }
        };
        cmdsThread.start();        
    }
    
    private static void cmdsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("CMDS Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        sqlUsers.getNewDBUsers();
        
        List<oldCMDSCasePartyModel> oldCMDScasePartyList = sqlCMDSCaseParty.getParty();
        List<oldCMDSCaseModel> oldCMDScaseList = sqlCMDSCase.getCase();
        
        totalRecordCount = oldCMDScasePartyList.size() + oldCMDScaseList.size();
        
        for (oldCMDSCasePartyModel item : oldCMDScasePartyList) {
            migrateCaseParty(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
                    item.getYear() + "-" + item.getCaseType() + "-" + item.getCaseMonth() + "-" + item.getCaseSeqNumber()
                            + ": " + item.getFirstName() + " " + item.getLastName());
        }
        
        for (oldCMDSCaseModel item : oldCMDScaseList) {
            migrateCase(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, 
                    item.getYear() + "-" + item.getType() + "-" + item.getMonth() + "-" + item.getCaseSeqNumber());
        }
                
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CMDS Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCMDSCases");
        }
    }
    
    private static void migrateCaseParty(oldCMDSCasePartyModel item) {
        casePartyModel party = new  casePartyModel();
        
        party.setCaseYear(item.getYear());
        party.setCaseType(item.getCaseType());
        party.setCaseMonth(item.getCaseMonth());
        party.setCaseNumber(item.getCaseSeqNumber());
        party.setCaseRelation(item.getParticipantType());
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
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCase(oldCMDSCaseModel item){
        CMDSCaseModel cmds = new CMDSCaseModel();
        
        cmds.setActive(item.getActive() == 1);
        cmds.setCaseYear(item.getYear());
        cmds.setCaseType(item.getType());
        cmds.setCaseMonth(item.getMonth());
        cmds.setCaseNumber(item.getCaseSeqNumber());
        cmds.setNote("");
        cmds.setOpenDate(item.getOpenDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getOpenDate().substring(0,9)));
        cmds.setGroupNumber(item.getGroupNumber().equals("") ? null : item.getGroupNumber().trim());
        cmds.setAljID(String.valueOf(StringUtilities.convertUserToID(item.getALJ().trim())));
        cmds.setCloseDate(item.getCloseDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getCloseDate().substring(0,9)));
        cmds.setInventoryStatusLine(item.getInventoryStatusLine().equals("") ? null : item.getInventoryStatusLine().trim());
        cmds.setInventoryStatusDate(item.getInventoryStatusDate().trim().equals("") 
                ? null : StringUtilities.convertStringSQLDate(item.getInventoryStatusDate().substring(0,9)));
        cmds.setCaseStatus(item.getStatus().equals("") ? null : item.getStatus().trim());
        cmds.setResult(item.getResult().equals("") ? null : item.getResult().trim());
        
        if(!item.getCaseNote().equals("")){
            cmds.setNote("Case Note: " + item.getCaseNote().trim());
        }
        if (!item.getInventoryStatusNote().equals("")){
            if (cmds.getNote().equals("")){
                cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Inventory Status Note: " + item.getInventoryStatusNote().trim());
            }
            cmds.setNote(cmds.getNote() + "Inventory Status Note: " + item.getInventoryStatusNote().trim());
        }
        if (!item.getOutsideCourtNote().equals("")){
            if (cmds.getNote().equals("")){
                cmds.setNote(cmds.getNote() + System.lineSeparator() + System.lineSeparator() + "Outside Court Note: " + item.getOutsideCourtNote().trim());
            }
            cmds.setNote(cmds.getNote() + "Outside Court Note: " + item.getOutsideCourtNote().trim());
        }
        
        sqlCMDSCase.addCase(cmds);
    }
    
}
