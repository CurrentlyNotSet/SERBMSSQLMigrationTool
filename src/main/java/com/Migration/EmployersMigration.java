/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.BarginingUnitModel;
import com.model.employerTypeModel;
import com.model.employersModel;
import com.model.oldBarginingUnitNewModel;
import com.model.oldPartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlBarginingUnit;
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
        List<oldPartyModel> employersList = sqlEmployers.getOldEmployers();
        List<oldBarginingUnitNewModel> unionList = sqlBarginingUnit.getOldBarginingUnits();
        
        totalRecordCount = employersList.size() + unionList.size();
        
        for (oldPartyModel item : employersList){
            migrateEmployers(item, types);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getLastName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        
        for (oldBarginingUnitNewModel item : unionList){
            migrateBarginingUnitUnions(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getBUEmployerName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Employers: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateEmployers");
    }
    
    private static void migrateEmployers(oldPartyModel old, List<employerTypeModel> typeList) {
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
        item.setPhone1((old.getWorkPhone()== null) ? "" : StringUtilities.convertPhoneNumberToString(old.getWorkPhone().trim()));
        item.setPhone2((old.getCellPhone()== null) ? "" : StringUtilities.convertPhoneNumberToString(old.getCellPhone().trim()));
        item.setFax((old.getFax()== null) ? "" : StringUtilities.convertPhoneNumberToString(old.getFax().trim()));
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

    private static void migrateBarginingUnitUnions(oldBarginingUnitNewModel old) {
        BarginingUnitModel item = new BarginingUnitModel();
        
        item.setActive(old.getActive());
        item.setEmployerNumber((old.getEmployerNumber()== null) ? "" : old.getEmployerNumber().trim());
        item.setUnitNumber((old.getUnitNumber()== null) ? "" : old.getUnitNumber().trim());
        item.setCert((old.getCert()== null) ? "" : old.getCert().trim());
        item.setBUEmployerName((old.getBUEmployerName()== null) ? "" : old.getBUEmployerName().trim());
        item.setJurisdiction((old.getJur()== null) ? "" : old.getJur().trim());
        item.setCounty((old.getCounty()== null) ? "" : old.getCounty().trim());
        item.setLUnion((old.getLUnion()== null) ? "" : old.getLUnion().trim());
        item.setLocal((old.getLocal()== null) ? "" : old.getLocal().trim());
        item.setStrike(("Y".equals(old.getStrike().trim())) ? 1 : 0);
        item.setLGroup((old.getLGroup()== null) ? "" : old.getLGroup().trim());
//        item.setCertDate((old.getCertDate()== null) ? "" : old.getCertDate().trim());
        item.setEnabled(("Y".equals(old.getEnabled().trim())) ? 1 : 0);
        item.setUnitDescription((old.getUnitDescription()== null) ? "" : old.getUnitDescription().trim());
        
        String[] caseNumber = old.getCaseRef().split("-");
        if (old.getCaseRef().trim().length() == 16 &&
                (caseNumber[0].length() == 4 || caseNumber[0].length() == 2) &&
                caseNumber[1].length() < 5 && caseNumber[2].length() == 2){
            if (caseNumber[0].length() == 4){
                item.setCaseRefYear(caseNumber[0]);
            } else if (caseNumber[0].length() == 2) {
                if (Integer.parseInt(caseNumber[0]) > 20){
                    item.setCaseRefYear( "19" + caseNumber[0]);
                } else {
                    item.setCaseRefYear( "20" + caseNumber[0]);
                }
            }            
            item.setCaseRefSection(caseNumber[1]);
            item.setCaseRefMonth(caseNumber[2]);
            if (caseNumber[3].length() > 4){
                item.setCaseRefSequence(caseNumber[3].substring(0, 3));
            } else {
                item.setCaseRefSequence(caseNumber[3]);
            }
        } else {
            item.setCaseRefYear("");
            item.setCaseRefSection("");
            item.setCaseRefMonth("");
            item.setCaseRefSequence("");
        }
        sqlBarginingUnit.addBarginingUnit(item);
    }
}
