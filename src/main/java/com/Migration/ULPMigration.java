/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ULPCaseModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.oldBlobFileModel;
import com.model.oldULPDataModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlBlobFile;
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
        List<oldULPDataModel> oldULPDataList = sqlULPData.getCases();
        totalRecordCount = oldULPDataList.size();
        System.out.println("Record Count: " + totalRecordCount);
        System.out.println("End Time " + new Date());
        
        for (oldULPDataModel item : oldULPDataList){
            migrateCase(item);
            currentRecord++;
            System.out.println("Current Record Number:  " + currentRecord);
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        control.setProgressBarDisable();
        sqlMigrationStatus.updateTimeCompleted("MigrateULPCases");
    }
        
    private static void migrateCase(oldULPDataModel item){
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        ULPMigration.migrateChargingParty(item, caseNumber);
        ULPMigration.migrateChargingPartyRep(item, caseNumber);
        ULPMigration.migrateChargedParty(item, caseNumber);
        ULPMigration.migrateChargedPartyRep(item, caseNumber);
        ULPMigration.migrateCaseData(item, caseNumber);
    }
    
    private static void migrateChargingParty(oldULPDataModel item, caseNumberModel caseNumber) {
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
    
    private static void migrateChargingPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {        
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
    
    private static void migrateChargedParty(oldULPDataModel item, caseNumberModel caseNumber) {
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
    
    private static void migrateChargedPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {
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
    
    private static void migrateCaseData(oldULPDataModel item, caseNumberModel caseNumber) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(caseNumber);
        
        ULPCaseModel ulpcase = new ULPCaseModel();
        
        ulpcase.setCaseYear(caseNumber.getCaseYear());
        ulpcase.setCaseType(caseNumber.getCaseType());
        ulpcase.setCaseMonth(caseNumber.getCaseMonth());
        ulpcase.setCaseNumber(caseNumber.getCaseNumber());
        ulpcase.setEmployerIDNumber((item.getEmployerNum()== null) ? "" : item.getEmployerNum().trim());
        ulpcase.setDeptInState((item.getDeptInState() == null) ? "" : item.getDeptInState().trim());
        ulpcase.setBarginingUnitNo((item.getBarginingUnitNumber() == null) ? "" : item.getBarginingUnitNumber().trim());
        ulpcase.setEONumber((item.getEmployeeOrgNumber() == null) ? "" : item.getEmployeeOrgNumber().trim());
        ulpcase.setAllegation((item.getAllegation() == null) ? "" : item.getAllegation().trim());
        ulpcase.setCurrentStatus((item.getStatus() == null) ? "" : item.getStatus().trim());
        ulpcase.setPriority(("Yes".equals(item.getPriority()) || "1".equals(item.getPriority())));
//        ulpcase.setAssignedDate();
//        ulpcase.setTurnInDate();
//        ulpcase.setReportDueDate();
//        ulpcase.setDismissalDate();
//        ulpcase.setDeferredDate();
//        ulpcase.setFileDate();
//        ulpcase.setProbableCause();
//        ulpcase.setAppealDateReceived();
//        ulpcase.setAppealDateSent();
        ulpcase.setCourtName((item.getCourt() == null) ? "" : item.getCourt().trim());
        ulpcase.setCourtCaseNumber((item.getCourtCaseNumber() == null) ? "" : item.getCourtCaseNumber().trim());
        ulpcase.setSERBCaseNumber((item.getSERBCourtCaseNumber() == null) ? "" : item.getSERBCourtCaseNumber().trim());
        ulpcase.setFinalDispositionStatus((item.getFinalDispostion() == null) ? "" : item.getFinalDispostion().trim());
//        ulpcase.setInvestigatorID();
//        ulpcase.setMediatorAssignedID();
//        ulpcase.setAljID();
        ulpcase.setNote("");
        ulpcase.setInvestigationReveals("");
        ulpcase.setStatement("");
        ulpcase.setRecommendation("");

        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "Notes":
                    ulpcase.setNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Invest":
                    ulpcase.setInvestigationReveals(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Statement":
                    ulpcase.setStatement(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Recommendation":
                    ulpcase.setRecommendation(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                default:
                    break;
            }
        }

        sqlULPData.importOldULPCase(ulpcase);



    }

}
