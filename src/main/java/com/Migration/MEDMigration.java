/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.MEDCaseModel;
import com.model.activityModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.employerCaseSearchModel;
import com.model.oldBlobFileModel;
import com.model.oldMEDCaseModel;
import com.model.oldMEDHistoryModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployerCaseSearchData;
import com.sql.sqlEmployers;
import com.sql.sqlMEDData;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class MEDMigration {
    
    public static void migrateMEDData(final MainWindowSceneController control){
        Thread medThread = new Thread() {
            @Override
            public void run() {
                medThread(control);
            }
        };
        medThread.start();        
    }
    
    private static void medThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("MED Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
                
        List<oldMEDCaseModel> oldMEDCaseList = sqlMEDData.getCases();
        
        totalRecordCount = oldMEDCaseList.size();
        
        for (oldMEDCaseModel item : oldMEDCaseList) {
            migrateCase(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating MED Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateMEDCases");
        }
    }
    
    private static void migrateCase(oldMEDCaseModel item) {
        if (item.getCaseNumber().trim().length() == 16){
            caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());

            migrateEmployer(item, caseNumber);
            migrateEmployerREP(item, caseNumber);
            migrateEmployeeORG(item, caseNumber);
            migrateEmployeeORGREP(item, caseNumber);
            migrateCaseData(item, caseNumber);
            migrateCaseHistory(caseNumber);
            migrateEmployerCaseSearch(item, caseNumber);
        }
    }
    
    private static void migrateEmployer(oldMEDCaseModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employer");
        party.setLastName(!"".equals(item.getEmployerName().trim()) ? item.getEmployerName().trim() : null);
        party.setAddress1(!"".equals(item.getEmployerAddress1().trim()) ? item.getEmployerAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEmployerAddress2().trim()) ? item.getEmployerAddress2().trim() : null);
        party.setCity(!"".equals(item.getEmlpoyerCity().trim()) ? item.getEmlpoyerCity().trim() : null);
        party.setState(!"".equals(item.getEmployerState().trim()) ? item.getEmployerState().trim() : null);
        party.setZip(!"".equals(item.getEmployerZip().trim()) ? item.getEmployerZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEmployerPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEmployerPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEmployerEmail().trim()) ? item.getEmployerEmail().trim() : null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateEmployerREP(oldMEDCaseModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employer Rep");
        party.setPrefix(!"".equals(item.getEmployerREPSal().trim()) ? item.getEmployerREPSal().trim() : null);
        party.setLastName(!"".equals(item.getEmployerREPName().trim()) ? item.getEmployerREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEmployerREPAddress1().trim()) ? item.getEmployerREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEmployerREPAddress2().trim()) ? item.getEmployerREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEmployerREPCity().trim()) ? item.getEmployerREPCity().trim() : null);
        party.setState(!"".equals(item.getEmployerREPState().trim()) ? item.getEmployerREPState().trim() : null);
        party.setZip(!"".equals(item.getEmployerREPZip().trim()) ? item.getEmployerREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEmployerREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEmployerREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEmployerREPEmail().trim()) ? item.getEmployerREPEmail().trim() : null);


        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateEmployeeORG(oldMEDCaseModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employee Org");
        party.setLastName(!"".equals(item.getEmployeeOrgName().trim()) ? item.getEmployeeOrgName().trim() : null);
        party.setAddress1(!"".equals(item.getEmployeeOrgAddress1().trim()) ? item.getEmployeeOrgAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEmployeeOrgAddress2().trim()) ? item.getEmployeeOrgAddress2().trim() : null);
        party.setCity(!"".equals(item.getEmployeeOrgCity().trim()) ? item.getEmployeeOrgCity().trim() : null);
        party.setState(!"".equals(item.getEmployeeOrgState().trim()) ? item.getEmployeeOrgState().trim() : null);
        party.setZip(!"".equals(item.getEmployeeOrgZip().trim()) ? item.getEmployeeOrgZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEmployeeOrgPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEmployeeOrgPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEmployeeOrgEmail().trim()) ? item.getEmployeeOrgEmail().trim() : null);



        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateEmployeeORGREP(oldMEDCaseModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employee Org Rep");
        party.setPrefix(!"".equals(item.getEmployeeOrgREPSal().trim()) ? item.getEmployeeOrgREPSal().trim() : null);
        party.setLastName(!"".equals(item.getEmployeeOrgREPName().trim()) ? item.getEmployeeOrgREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEmployeeOrgREPAddress1().trim()) ? item.getEmployeeOrgREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEmployeeOrgREPAddress2().trim()) ? item.getEmployeeOrgREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEmployeeOrgREPCity().trim()) ? item.getEmployeeOrgREPCity().trim() : null);
        party.setState(!"".equals(item.getEmployeeOrgREPState().trim()) ? item.getEmployeeOrgREPState().trim() : null);
        party.setZip(!"".equals(item.getEmployeeOrgREPZip().trim()) ? item.getEmployeeOrgREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEmployeeOrgREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEmployeeOrgREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEmployeeOrgREPEmail().trim()) ? item.getEmployeeOrgREPEmail().trim() : null);


        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCaseData(oldMEDCaseModel item, caseNumberModel caseNumber) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(caseNumber);
        MEDCaseModel med = new MEDCaseModel();
        
        med.setActive(item.getActive());
        med.setCaseYear(caseNumber.getCaseYear());
        med.setCaseType(caseNumber.getCaseType());
        med.setCaseMonth(caseNumber.getCaseMonth());
        med.setCaseNumber(caseNumber.getCaseNumber());
        
        sqlMEDData.importOldMEDCase(med);
    }
    
    
    private static void migrateCaseHistory(caseNumberModel caseNumber) {
        List<oldMEDHistoryModel> oldMEDCaseList = sqlActivity.getMEDHistoryByCase(StringUtilities.generateFullCaseNumber(caseNumber));
        
        for (oldMEDHistoryModel old : oldMEDCaseList){                                                
            activityModel item = new activityModel();
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setUserID(StringUtilities.convertUserToID(old.getUserInitals()));
            item.setDate(old.getDate());
            item.setAction(!"".equals(old.getAction().trim()) ? old.getAction().trim() : null);
            item.setFileName(!"".equals(old.getFileName().trim()) ? old.getFileName().trim() : null);
            item.setFrom(!"".equals(old.getEmailFrom().trim()) ? old.getEmailFrom().trim() : null);
            item.setTo(!"".equals(old.getEmailTo().trim()) ? old.getEmailTo().trim() : null);
            item.setType(null);
            item.setComment(null);
            item.setRedacted("Y".equals(old.getRedacted().trim()) ? 1 : 0);
            item.setAwaitingTimeStamp(0);
            
            sqlActivity.addActivity(item);
        }
    }
    
    private static void migrateEmployerCaseSearch(oldMEDCaseModel item, caseNumberModel caseNumber) {
        if (!"".equals(item.getEmployerIDNumber().trim())) {

            employerCaseSearchModel search = new employerCaseSearchModel();

            search.setCaseYear(caseNumber.getCaseYear());
            search.setCaseType(caseNumber.getCaseType());
            search.setCaseMonth(caseNumber.getCaseMonth());
            search.setCaseNumber(caseNumber.getCaseNumber());
            search.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
            search.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getCaseFileDate()).getTime()) : null); 
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerIDNumber().trim()));

            sqlEmployerCaseSearchData.addEmployer(search);
        }
    }
}
