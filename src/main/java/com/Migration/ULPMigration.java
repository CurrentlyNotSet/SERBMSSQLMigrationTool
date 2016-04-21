/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.oldULPDataTableModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlULPData;
import com.util.StringUtilities;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class ULPMigration {
    
    public static void migrateULPData(MainWindowSceneController control){
        Thread ulpThread;
        
        ulpThread = new Thread() {
            @Override
            public void run() {
                ulpThread(control);
            }
        };
        ulpThread.start();        
    }
    
    private static void ulpThread(MainWindowSceneController control){
        control.setProgressBarIndeterminate("ULP Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        System.out.println("Start Time " + new Date());
        List<oldULPDataTableModel> oldULPDataList = sqlULPData.getCases();
        totalRecordCount = oldULPDataList.size();
        System.out.println("Record Count: " + totalRecordCount);
        System.out.println("End Time " + new Date());
        
        for (oldULPDataTableModel item : oldULPDataList){
            migrateCase(item);
            currentRecord++;
            System.out.println("Current Record Number:  " + currentRecord);
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateULPCases");
    }
        
    private static void migrateCase(oldULPDataTableModel item){
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        ULPMigration.migrateChargingParty(item, caseNumber);
        ULPMigration.migrateChargingPartyRep(item, caseNumber);
        ULPMigration.migrateChargedParty(item, caseNumber);
        ULPMigration.migrateChargedPartyRep(item, caseNumber);
        ULPMigration.migrateCaseData(item, caseNumber);
    }
    
    private static void migrateChargingParty(oldULPDataTableModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charging Party");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCPName() == null) ? "" : item.getCPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCPAddress1() == null) ? "" : item.getCPAddress1().trim()));
        party.setAddress2(((item.getCPAddress2() == null) ? "" : item.getCPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCPCity() == null) ? "" : item.getCPCity().trim()));
        party.setState(((item.getCPState() == null) ? "" : item.getCPState().trim()));
        party.setZip(((item.getCPZip() == null) ? "" : item.getCPZip().trim()));
        party.setPhoneOne(((item.getCPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPPhone1().trim())));
        party.setPhoneTwo(((item.getCPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPPhone2().trim())));
        party.setEmailAddress(((item.getCPEmail() == null) ? "" : item.getCPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargingPartyRep(oldULPDataTableModel item, caseNumberModel caseNumber) {        
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charging Party REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCPREPName() == null) ? "" : item.getCPREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCPREPAddress1() == null) ? "" : item.getCPREPAddress1().trim()));
        party.setAddress2(((item.getCPREPAddress2() == null) ? "" : item.getCPREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCPREPCity() == null) ? "" : item.getCPREPCity().trim()));
        party.setState(((item.getCPREPState() == null) ? "" : item.getCPREPState().trim()));
        party.setZip(((item.getCPREPZip() == null) ? "" : item.getCPREPZip().trim()));
        party.setPhoneOne(((item.getCPREPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPREPPhone1().trim())));
        party.setPhoneTwo(((item.getCPREPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPREPPhone2().trim())));
        party.setEmailAddress(((item.getCPREPEmail() == null) ? "" : item.getCPREPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargedParty(oldULPDataTableModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charged Party");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCHDName() == null) ? "" : item.getCHDName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCHDAddress1() == null) ? "" : item.getCHDAddress1().trim()));
        party.setAddress2(((item.getCHDAddress2() == null) ? "" : item.getCHDAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCHDCity() == null) ? "" : item.getCHDCity().trim()));
        party.setState(((item.getCHDState() == null) ? "" : item.getCHDState().trim()));
        party.setZip(((item.getCHDZip() == null) ? "" : item.getCHDZip().trim()));
        party.setPhoneOne(((item.getCHDPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDPhone1().trim())));
        party.setPhoneTwo(((item.getCHDPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDPhone2().trim())));
        party.setEmailAddress(((item.getCHDEmail() == null) ? "" : item.getCHDEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargedPartyRep(oldULPDataTableModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charged Party REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCHDREPName() == null) ? "" : item.getCHDREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCHDREPAddress1() == null) ? "" : item.getCHDREPAddress1().trim()));
        party.setAddress2(((item.getCHDREPAddress2() == null) ? "" : item.getCHDREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCHDREPCity() == null) ? "" : item.getCHDREPCity().trim()));
        party.setState(((item.getCHDREPState() == null) ? "" : item.getCHDREPState().trim()));
        party.setZip(((item.getCHDREPZip() == null) ? "" : item.getCHDREPZip().trim()));
        party.setPhoneOne(((item.getCHDREPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone1().trim())));
        party.setPhoneTwo(((item.getCHDREPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone2().trim())));
        party.setEmailAddress(((item.getCHDREPEmail() == null) ? "" : item.getCHDREPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCaseData(oldULPDataTableModel item, caseNumberModel caseNumber) {
        //TODO
    }
}
