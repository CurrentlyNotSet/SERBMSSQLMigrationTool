/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.MEDCaseModel;
import com.model.MEDCaseSearchModel;
import com.model.activityModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.employerCaseSearchModel;
import com.model.factFinderModel;
import com.model.mediatorsModel;
import com.model.oldBlobFileModel;
import com.model.oldMEDCaseModel;
import com.model.oldMEDHistoryModel;
import com.model.relatedCaseModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployerCaseSearchData;
import com.sql.sqlEmployers;
import com.sql.sqlFactFinder;
import com.sql.sqlMEDCaseSearch;
import com.sql.sqlMEDData;
import com.sql.sqlMediator;
import com.sql.sqlMigrationStatus;
import com.sql.sqlRelatedCase;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class MEDMigration {
    
    private static List<mediatorsModel> newMediatorsList;
    
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
        List<factFinderModel> oldFactFindersList = sqlFactFinder.getOldFactFinders();
        List<mediatorsModel> oldMediatorsList = sqlMediator.getOldMediator();
        
        totalRecordCount = oldMEDCaseList.size() + oldFactFindersList.size() + oldMediatorsList.size();
        
        for (factFinderModel item : oldFactFindersList) {
            migrateFactFinder(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getFirstName().trim() + " " + item.getLastName().trim());
        }
        
        for (mediatorsModel item : oldMediatorsList) {
            migrateMediator(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getFirstName().trim() + " " + item.getLastName().trim());
        }
        
        newMediatorsList = sqlMediator.getNewMediator();
        
        for (oldMEDCaseModel item : oldMEDCaseList) {
            migrateCase(item);            
            String caseNumber = item.getCaseNumber().trim().equals("") ? item.getStrikeCaseNumber() : item.getCaseNumber().trim();
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, caseNumber);
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating MED Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateMEDCases");
        }
    }
    
    private static void migrateFactFinder(factFinderModel item) { 
        String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
        
        switch (nameSplit.length) {
            case 2:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(null);
                item.setLastName(nameSplit[1].trim());
                break;
            case 3:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
                break;
            case 4:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim() + ", " + nameSplit[3].trim());
                break;
            default:
                break;
        }
        if (item.getCity() != null){
            String[] cityStateZipSplit = item.getCity().split(",", 2);
            item.setCity("".equals(cityStateZipSplit[0]) ? null : cityStateZipSplit[0].trim().replaceAll(",", ""));
            
            String[] stateZipSplit = cityStateZipSplit[1].trim().split(" ");
            item.setState("".equals(stateZipSplit[0]) ? null : stateZipSplit[0].trim().replaceAll("[^A-Za-z]", ""));
            item.setZip("".equals(stateZipSplit[1]) ? null : stateZipSplit[1].trim().replaceAll(",", ""));
        }
        
        if ("Ohio".equals(item.getState())){
            item.setState("OH");
        } else if ("Kentucky".equals(item.getState())){
            item.setState("KY");
        }    
        sqlFactFinder.addFactFinder(item);
    }
    
    private static void migrateMediator(mediatorsModel item) { 
        String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
        
        switch (nameSplit.length) {
            case 2:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(null);
                item.setLastName(nameSplit[1].trim());
                break;
            case 3:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
                break;
            case 4:
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim() + ", " + nameSplit[3].trim());
                break;  
            default:
                break;
        }
        sqlMediator.addMediator(item);
    }
    
    private static void migrateCase(oldMEDCaseModel item) {
        caseNumberModel caseNumber = null;
        if (item.getCaseNumber().trim().length() == 16){
            caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        } else if (item.getStrikeCaseNumber().trim().length() == 16){
            caseNumber = StringUtilities.parseFullCaseNumber(item.getStrikeCaseNumber().trim());
        }
        if (caseNumber != null){
            migrateParties(item, caseNumber);
            migrateCaseData(item, caseNumber);
            migrateRelatedCases(item, caseNumber);
            migrateCaseHistory(caseNumber);
            migrateCaseSearch(item, caseNumber);
            migrateEmployerCaseSearch(item, caseNumber);
        }
    }
    
    private static void migrateParties(oldMEDCaseModel item, caseNumberModel caseNumber){
        migrateEmployer(item, caseNumber);
        migrateEmployerREP(item, caseNumber);
        migrateEmployeeORG(item, caseNumber);
        migrateEmployeeORGREP(item, caseNumber);
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
        party.setCaseRelation("Employer REP");
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
        party.setCaseRelation("Employee Organization");
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
        party.setCaseRelation("Employee Organization REP");
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
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(StringUtilities.generateFullCaseNumber(caseNumber));
        MEDCaseModel med = new MEDCaseModel();
        String note = "";
        
        med.setActive(item.getActive());
        med.setCaseYear(caseNumber.getCaseYear());
        med.setCaseType(caseNumber.getCaseType());
        med.setCaseMonth(caseNumber.getCaseMonth());
        med.setCaseNumber(caseNumber.getCaseNumber());
        med.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getCaseFileDate())) : null); 
        med.setConcilList1OrderDate(!"".equals(item.getConciliationOrderDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConciliationOrderDate())) : null);
        med.setConcilList1SelectionDueDate(!"".equals(item.getConciliationSelectionDue().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConciliationSelectionDue())) : null);
        med.setConcilList1Name1(!"".equals(item.getConciliator1().trim()) ? item.getConciliator1().trim() : null);
        med.setConcilList1Name2(!"".equals(item.getConciliator2().trim()) ? item.getConciliator2().trim() : null);
        med.setConcilList1Name3(!"".equals(item.getConciliator3().trim()) ? item.getConciliator3().trim() : null);
        med.setConcilList1Name4(!"".equals(item.getConciliator4().trim()) ? item.getConciliator4().trim() : null);
        med.setConcilList1Name5(!"".equals(item.getConciliator5().trim()) ? item.getConciliator5().trim() : null);
        med.setConcilAppointmentDate(!"".equals(item.getConciliatorApptDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConciliatorApptDate())) : null);
        med.setConcilType(!"".equals(item.getConciliationType().trim()) ? item.getConciliationType().trim() : null);
        med.setConcilSelection(!"".equals(item.getConciliatorSelection().trim()) ? item.getConciliatorSelection().trim() : null);
        med.setConcilReplacement(!"".equals(item.getConciliatorReplacement().trim()) ? item.getConciliatorReplacement().trim() : null);
        med.setConcilOriginalConciliator(!"".equals(item.getTempHolder2().trim()) ? item.getTempHolder2().trim() : null); 
        med.setConcilOriginalConcilDate(!"".equals(item.getOrgConcilDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getOrgConcilDate())) : null);
        med.setConcilList2OrderDate(!"".equals(item.getConciliationOrderDate2().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConciliationOrderDate2())) : null);
        med.setConcilList2SelectionDueDate(!"".equals(item.getConciliationSelectionDate2().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConciliationSelectionDate2())) : null);
        med.setConcilList2Name1(!"".equals(item.getConciliator1Set2().trim()) ? item.getConciliator1Set2().trim() : null);
        med.setConcilList2Name2(!"".equals(item.getConciliator2Set2().trim()) ? item.getConciliator2Set2().trim() : null);
        med.setConcilList2Name3(!"".equals(item.getConciliator3Set2().trim()) ? item.getConciliator3Set2().trim() : null);
        med.setConcilList2Name4(!"".equals(item.getConciliator4Set2().trim()) ? item.getConciliator4Set2().trim() : null);
        med.setConcilList2Name5(!"".equals(item.getConciliator5Set2().trim()) ? item.getConciliator5Set2().trim() : null);
        med.setFFList1OrderDate(!"".equals(item.getFFPanelSelectionDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFFPanelSelectionDate().trim())) : null);
        med.setFFList1SelectionDueDate(!"".equals(item.getFFPanelSelectionDue().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFFPanelSelectionDue().trim())) : null);
        med.setFFList1Name1(!"".equals(item.getFactFinder1().trim()) ? item.getFactFinder1().trim() : null);
        med.setFFList1Name2(!"".equals(item.getFactFinder2().trim()) ? item.getFactFinder2().trim() : null);
        med.setFFList1Name3(!"".equals(item.getFactFinder3().trim()) ? item.getFactFinder3().trim() : null);
        med.setFFList1Name4(!"".equals(item.getFactFinder4().trim()) ? item.getFactFinder4().trim() : null);
        med.setFFList1Name5(!"".equals(item.getFactFinder5().trim()) ? item.getFactFinder5().trim() : null);
        med.setFFAppointmentDate(!"".equals(item.getFactFinderApptDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFactFinderApptDate().trim())) : null);
        med.setFFType(!"".equals(item.getFactFinderType().trim()) ? item.getFactFinderType().trim() : null);
        med.setFFSelection(!"".equals(item.getFactFinderSelected().trim()) ? item.getFactFinderSelected().trim() : null);
        med.setFFReplacement(!"".equals(item.getFactFinderRepalcement().trim()) ? item.getFactFinderRepalcement().trim() : null);
        med.setFFOriginalFactFinder("".equals(item.getFFOrg().trim()) ? item.getFFOrg().trim() : null);
        med.setFFOriginalFactFinderDate(!"".equals(item.getOrgFFDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getOrgFFDate().trim())) : null);
        med.setAsAgreedToByParties("1".equals(item.getAgreedByTheParties().trim()) ? 1 : 0);
        med.setFFList2OrderDate(!"".equals(item.getFFPanelSelectionDate2().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFFPanelSelectionDate2().trim())) : null);
        med.setFFList2SelectionDueDate(!"".equals(item.getFFPanelSelectionDue2().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFFPanelSelectionDue2().trim())) : null);
        med.setFFList2Name1(!"".equals(item.getFactFinder1Set2().trim()) ? item.getFactFinder1Set2().trim() : null);
        med.setFFList2Name2(!"".equals(item.getFactFinder2Set2().trim()) ? item.getFactFinder2Set2().trim() : null);
        med.setFFList2Name3(!"".equals(item.getFactFinder3Set2().trim()) ? item.getFactFinder3Set2().trim() : null);
        med.setFFList2Name4(!"".equals(item.getFactFinder4Set2().trim()) ? item.getFactFinder4Set2().trim() : null);
        med.setFFList2Name5(!"".equals(item.getFactFinder5Set2().trim()) ? item.getFactFinder5Set2().trim() : null);
        med.setFFEmployerType(!"".equals(item.getEmployerType().trim()) ? item.getEmployerType().trim() : null);
        med.setFFEmployeeType(!"".equals(item.getEmployeeType().trim()) ? item.getEmployeeType().trim() : null);
        med.setFFReportIssueDate(!"".equals(item.getReportIssuedDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getReportIssuedDate().trim())) : null);
        med.setFFMediatedSettlement("1".equals(item.getMediatedSettlement().trim()) ? 1 : 0);
        med.setFFAcceptedBy(!"".equals(item.getResultsAppectedBy().trim()) ? item.getResultsAppectedBy().trim() : null);
        med.setFFDeemedAcceptedBy(!"".equals(item.getResultsDeemedAcceptedBy().trim()) ? item.getResultsDeemedAcceptedBy().trim() : null);
        med.setFFRejectedBy(!"".equals(item.getResultsRejectedBy().trim()) ? item.getResultsRejectedBy().trim() : null);
        med.setFFOverallResult(!"".equals(item.getResultsOverallResult().trim()) ? item.getResultsOverallResult().trim() : null);
        med.setEmployerIDNumber(!"".equals(item.getEmployerIDNumber().trim()) ? item.getEmployerIDNumber().trim() : null);
        med.setBargainingUnitNumber(!"".equals(item.getBUNumber().trim()) ? item.getBUNumber().trim() : null);
        med.setApproxNumberOfEmployees(!"".equals(item.getApproxNumberOfEmployees().trim()) ? item.getApproxNumberOfEmployees().trim() : null);
        med.setDuplicateCaseNumber(!"".equals(item.getDuplicateCase().trim()) ? item.getDuplicateCase().trim() : null);
        med.setRelatedCaseNumber(!"".equals(item.getRelated().trim()) ? item.getRelated().trim() : null);
        med.setNegotiationType(!"".equals(item.getNegotiationType().trim()) ? item.getNegotiationType().trim() : null);
        med.setExpirationDate(!"".equals(item.getExpirationDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getExpirationDate().trim())) : null);
        med.setNTNFiledBy(!"".equals(item.getNTNFiledBy().trim()) ? item.getNTNFiledBy().trim() : null);
        med.setNegotiationPeriod(!"".equals(item.getNegotiationPeriod().trim()) ? item.getNegotiationPeriod().trim() : null);
        med.setMultiunitBargainingRequested(item.getMultiUnitBargainingRequested().equals("1"));
        med.setMediatorAppointedDate(!"".equals(item.getMediatorApptDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getMediatorApptDate().trim())) : null);
        med.setMediatorReplacement(item.getMediatorInvolved().equals("1"));
        med.setSettlementDate(!"".equals(item.getCBAReceivedDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getCBAReceivedDate().trim())) : null);
        med.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
        med.setSendToBoardToClose(item.getTempHolder4().equals("Send to Brd to Close"));
        med.setBoardFinalDate(!"".equals(item.getTempHolder3().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getTempHolder3().trim())) : null);
        med.setRetentionTicklerDate(!"".equals(item.getTicklerDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getTicklerDate().trim())) : null);
        med.setLateFiling(item.getLateFiling().equals("1"));
        med.setImpasse(item.getImpasse().equals("1"));
        med.setSettled(item.getSettledCheckBox().equals("1"));
        med.setTA(item.getTACheckBox().equals("1"));
        med.setMAD(item.getMADCheckBox().equals("1"));
        med.setWithdrawl(item.getWithdraw().equals("1"));
        med.setMotion(item.getMotionCheckBox().equals("1"));
        med.setDismissed(item.getDismiss().equals("1"));        
        med.setStrikeFileDate(!"".equals(item.getStrikeFilingDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getStrikeFilingDate().trim())) : null);
        med.setStrikeCaseNumber(!"".equals(item.getStrikeCaseNumber().trim()) ? item.getStrikeCaseNumber().trim() : null);
        med.setMedCaseNumber(!"".equals(item.getCaseNumber().trim()) ? item.getCaseNumber().trim() : null);
        med.setUnitDescription(!"".equals(item.getBUDescription().trim()) ? item.getBUDescription().trim() : null);
        med.setUnitSize(!"".equals(item.getBUSize().trim()) ? item.getBUSize().trim() : null);
        med.setUnauthorizedStrike(item.getUnauthorizedStrike().equals("Y"));
        med.setNoticeOfIntentToStrikeOnly(item.getNoticeOfIntentToStrike().equals("1"));
        med.setIntendedDateStrike(!"".equals(item.getIntendedStrikeDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getIntendedStrikeDate().trim())) : null);
        med.setNoticeOfIntentToPicketOnly(item.getNoticeofIntentToPicket().equals("1"));
        med.setIntendedDatePicket(!"".equals(item.getIntendedPicketDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getIntendedPicketDate().trim())) : null);
        med.setInformational(item.getInformational().equals("1"));
        med.setNoticeOfIntentToStrikeAndPicket(item.getNoticeOfIntentToStrikeAndPicket().equals("1"));
        switch (item.getStrikeOccured()) {
            case "1":
                med.setStrikeOccured("Yes");
                break;
            case "0":
                med.setStrikeOccured("No");
                break;
            default:
                med.setStrikeOccured(null);
                break;
        }        
        med.setStrikeStatus(!"".equals(item.getStrikeStatus().trim()) ? item.getStrikeStatus().trim() : null);
        med.setStrikeBegan(!"".equals(item.getStrikeBegan().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getStrikeBegan().trim())) : null);
        med.setStrikeEnded(!"".equals(item.getStrikeEnded().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getStrikeEnded().trim())) : null);
        med.setTotalNumberOfDays(!"".equals(item.getTotalNumOfDays().trim()) ? item.getTotalNumOfDays().trim() : null);
                
        if (!"".equals(item.getPicketDate2().trim())) {
            if (!note.trim().equals("")) {
                System.lineSeparator();
            }
            note += "Picket Date 2:" + item.getPicketDate2();
        }
        if (!"".equals(item.getPicketDate3().trim())) {
            if (!note.trim().equals("")) {
                System.lineSeparator();
            }
            note += "Picket Date 3:" + item.getPicketDate3();
        }
        if (!"".equals(item.getPicketDate4().trim())) {
            if (!note.trim().equals("")) {
                System.lineSeparator();
            }
            note += "Picket Date 4:" + item.getPicketDate4();
        }
        if (!"".equals(item.getPicketDate5().trim())) {
            if (!note.trim().equals("")) {
                System.lineSeparator();
            }
            note += "Picket Date 5:" + item.getPicketDate5();
        }
        
        for (mediatorsModel person : newMediatorsList) {
            if (item.getTempHolder5().toLowerCase().startsWith(person.getFirstName().toLowerCase()) || 
                    item.getTempHolder5().toLowerCase().endsWith(person.getLastName().toLowerCase()) ){
                med.setStrikeMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }
        
        for (mediatorsModel person : newMediatorsList) {
            if (item.getStateMediatorAppt().toLowerCase().startsWith(person.getFirstName().toLowerCase()) || 
                    item.getStateMediatorAppt().toLowerCase().endsWith(person.getLastName().toLowerCase()) ){
                med.setStateMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }
        
        for (mediatorsModel person : newMediatorsList) { 
            if (item.getFMCSMediatorAppt().toLowerCase().startsWith(person.getFirstName().toLowerCase()) || 
                    item.getFMCSMediatorAppt().toLowerCase().endsWith(person.getLastName().toLowerCase()) ){
                med.setFMCSMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }
        
        med.setNote(note);
        
        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "Notes":                   
                    String note2 = StringUtilities.convertBlobFileToString(blob.getBlobData());
                    
                    if (note2 != null && !note.trim().equals("")){
                        note += System.lineSeparator() + System.lineSeparator() + note2;
                    } else {
                        note += note2;
                    }
                    
                    if (note.trim().equals("")){
                        note = null;
                    }
                    
                    med.setNote(note);
                    break;
                case "StkNotes":
                    med.setStrikeNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "FFNotes":
                    med.setFFNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;  
                default:
                    break;
            }
        }
                
        sqlMEDData.importOldMEDCase(med);
    }
    
    private static void migrateRelatedCases(oldMEDCaseModel item, caseNumberModel caseNumber){
        relatedCaseModel relatedCase = new relatedCaseModel();

        relatedCase.setCaseYear(caseNumber.getCaseYear());
        relatedCase.setCaseType(caseNumber.getCaseType());
        relatedCase.setCaseMonth(caseNumber.getCaseMonth());
        relatedCase.setCaseNumber(caseNumber.getCaseNumber());
        
        if (!item.getCaseNumber2().trim().equals("")){
            relatedCase.setRelatedCaseNumber(item.getCaseNumber2().trim());
            sqlRelatedCase.addRelatedCase(relatedCase);
        }
        if (!item.getCaseNumber3().trim().equals("")){
            relatedCase.setRelatedCaseNumber(item.getCaseNumber3().trim());
            sqlRelatedCase.addRelatedCase(relatedCase);
        }
        if (!item.getCaseNumber4().trim().equals("")){
            relatedCase.setRelatedCaseNumber(item.getCaseNumber4().trim());
            sqlRelatedCase.addRelatedCase(relatedCase);
        }
        if (!item.getCaseNumber5().trim().equals("")){
            relatedCase.setRelatedCaseNumber(item.getCaseNumber5().trim());
            sqlRelatedCase.addRelatedCase(relatedCase);
        }
        if (!item.getCaseNumber6().trim().equals("")){
            relatedCase.setRelatedCaseNumber(item.getCaseNumber6().trim());
            sqlRelatedCase.addRelatedCase(relatedCase);
        }
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
    
    private static void migrateCaseSearch(oldMEDCaseModel item, caseNumberModel caseNumber) {
        MEDCaseSearchModel search = new MEDCaseSearchModel();
        
        search.setCaseYear(caseNumber.getCaseYear());
        search.setCaseType(caseNumber.getCaseType());
        search.setCaseMonth(caseNumber.getCaseMonth());
        search.setCaseNumber(caseNumber.getCaseNumber());
        search.setEmployerName(!"".equals(item.getEmployerName().trim()) ? item.getEmployerName().trim() : null);
        search.setUnionName(!"".equals(item.getEmployeeOrgName()) ? item.getEmployeeOrgName().trim() : null);
        search.setCounty(!"".equals(item.getEmployerCounty().trim()) ? item.getEmployerCounty().trim() : null);
        search.setEmployerID(!"".equals(item.getEmployerIDNumber().trim()) ? item.getEmployerIDNumber().trim() : null);
        search.setBunNumber(!"".equals(item.getBUNumber().trim()) ? item.getBUNumber().trim() : null);
        
        sqlMEDCaseSearch.addMEDCaseSearchCase(search);
    }
    
    private static void migrateEmployerCaseSearch(oldMEDCaseModel item, caseNumberModel caseNumber) {
        if (!"".equals(item.getEmployerIDNumber().trim())) {
            employerCaseSearchModel search = new employerCaseSearchModel();

            search.setCaseYear(caseNumber.getCaseYear());
            search.setCaseType(caseNumber.getCaseType());
            search.setCaseMonth(caseNumber.getCaseMonth());
            search.setCaseNumber(caseNumber.getCaseNumber());
            search.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
            search.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getCaseFileDate())) : null); 
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerIDNumber().trim()));

            sqlEmployerCaseSearchData.addEmployer(search);
        }
    }
}
