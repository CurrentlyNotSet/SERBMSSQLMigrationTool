/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.employerTypeModel;
import com.model.employersModel;
import com.model.oldPartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class EmployersMigration {
    
    public static void migrateEmployers(final MainWindowSceneController control){
        Thread employersThread = new Thread() {
            @Override
            public void run() {
                employersThread(control);
            }
        };
        employersThread.start();        
    }
    
    private static void employersThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Employers Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<employerTypeModel> types = sqlEmployers.getEmployerType();
        List<oldPartyModel> masterList = sqlEmployers.getOldEmployers();
        totalRecordCount = masterList.size();
        
        for (oldPartyModel item : masterList){
            migrateContact(item, types);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getLastName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Employers: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateEmployers");
    }
    
    private static void migrateContact(oldPartyModel old, List<employerTypeModel> typeList) {
        int typeID = 0;
        for (employerTypeModel type : typeList){
            if (type.getType().equals(old.getPartyType())){
                typeID = type.getId();
                break;
            }
        }
        
        employersModel item = new employersModel();
        item.setActive(old.getActive());
        item.setEmployerType(typeID);
        item.setEmployerName((old.getBusinessName() == null) ? "" : old.getBusinessName().trim());
        item.setPrefix((old.getPrefix()== null) ? "" : old.getPrefix().trim());
        item.setFirstName((old.getFirstName()== null) ? "" : old.getFirstName().trim());
        item.setMiddleInitial((old.getMiddleInitial()== null) ? "" : old.getMiddleInitial().trim());
        item.setLastName((old.getLastName()== null) ? "" : old.getLastName().trim());
        item.setSuffix((old.getSuffix()== null) ? "" : old.getSuffix().trim());
        item.setTitle((old.getTitle()== null) ? "" : old.getTitle().trim());
        item.setAddress1((old.getAddress1()== null) ? "" : old.getAddress1().trim());
        item.setAddress2((old.getAddress2()== null) ? "" : old.getAddress2().trim());
        item.setAddress3("");
        item.setCity((old.getCity()== null) ? "" : old.getCity().trim());
        item.setState((old.getState()== null) ? "" : old.getState().trim());
        item.setZipCode((old.getZipPlusFive()== null) ? "" : old.getZipPlusFive().trim());
        item.setPhone1((old.getWorkPhone()== null) ? "" : old.getWorkPhone().trim());
        item.setPhone2((old.getCellPhone()== null) ? "" : old.getCellPhone().trim());
        item.setFax((old.getFax()== null) ? "" : old.getFax().trim());
        item.setEmailAddress((old.getEMail() == null) ? "" : old.getEMail().trim());
        item.setEmployerIDNumber((old.getPartyField1()== null) ? "" : old.getPartyField1().trim());
        item.setEmployerTypeCode((old.getPartyField2()== null) ? "" : old.getPartyField2().trim());
        item.setJurisdiction((old.getPartyField3()== null) ? "" : old.getPartyField3().trim());
        item.setRegion((old.getPartyField4()== null) ? "" : old.getPartyField4().trim());
        item.setAssistantFirstName((old.getAssistantFirstName()== null) ? "" : old.getAssistantFirstName().trim());
        item.setAssistantMiddleInitial((old.getAssistantMiddleInitial()== null) ? "" : old.getAssistantMiddleInitial().trim());
        item.setAssistantLastName((old.getAssistantLastName()== null) ? "" : old.getAssistantLastName().trim());
        item.setAssistantEmail((old.getAssistantEMail()== null) ? "" : old.getAssistantEMail().trim());
        item.setCounty((old.getCounty()== null) ? "" : old.getCounty().trim());

        sqlEmployers.addEmployer(item);
    }

}
