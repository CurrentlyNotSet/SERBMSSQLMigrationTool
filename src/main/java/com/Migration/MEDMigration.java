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
import java.sql.Date;
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
    
    private static void migrateFactFinder(factFinderModel item) { 
        String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
        
        if (nameSplit.length == 2){
            item.setFirstName(nameSplit[0].trim());
            item.setMiddleName(null);
            item.setLastName(nameSplit[1].trim());
        } else if (nameSplit.length == 3) {
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
        } else if (nameSplit.length == 4) {
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim() + ", " + nameSplit[3].trim());
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
        
        if (nameSplit.length == 2){
            item.setFirstName(nameSplit[0].trim());
            item.setMiddleName(null);
            item.setLastName(nameSplit[1].trim());
        } else if (nameSplit.length == 3) {
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
        } else if (nameSplit.length == 4) {
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleName(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim() + ", " + nameSplit[3].trim());
        }  
        sqlMediator.addMediator(item);
    }
    
    private static void migrateCase(oldMEDCaseModel item) {
        if (item.getCaseNumber().trim().length() == 16){
            caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());

            migrateEmployer(item, caseNumber);
            migrateEmployerREP(item, caseNumber);
            migrateEmployeeORG(item, caseNumber);
            migrateEmployeeORGREP(item, caseNumber);
            migrateCaseData(item, caseNumber);
            migrateRelatedCases(item, caseNumber);
            migrateCaseHistory(caseNumber);
            migrateCaseSearch(item, caseNumber);
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
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(caseNumber);
        MEDCaseModel med = new MEDCaseModel();
        
        med.setActive(item.getActive());
        med.setCaseYear(caseNumber.getCaseYear());
        med.setCaseType(caseNumber.getCaseType());
        med.setCaseMonth(caseNumber.getCaseMonth());
        med.setCaseNumber(caseNumber.getCaseNumber());
        med.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getCaseFileDate()).getTime()) : null); 
        med.setConcilList1OrderDate(!"".equals(item.getConciliationOrderDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getConciliationOrderDate()).getTime()) : null);
        med.setConcilList1SelectionDueDate(!"".equals(item.getConciliationSelectionDue().trim()) ? new Date(StringUtilities.convertStringDate(item.getConciliationSelectionDue()).getTime()) : null);
        med.setConcilList1Name1(!"".equals(item.getConciliator1().trim()) ? item.getConciliator1().trim() : null);
        med.setConcilList1Name2(!"".equals(item.getConciliator2().trim()) ? item.getConciliator2().trim() : null);
        med.setConcilList1Name3(!"".equals(item.getConciliator3().trim()) ? item.getConciliator3().trim() : null);
        med.setConcilList1Name4(!"".equals(item.getConciliator4().trim()) ? item.getConciliator4().trim() : null);
        med.setConcilList1Name5(!"".equals(item.getConciliator5().trim()) ? item.getConciliator5().trim() : null);
        med.setConcilAppointmentDate(!"".equals(item.getConciliatorApptDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getConciliatorApptDate()).getTime()) : null);
        med.setConcilType(!"".equals(item.getConciliationType().trim()) ? item.getConciliationType().trim() : null);
        med.setConcilSelection(!"".equals(item.getConciliatorSelection().trim()) ? item.getConciliatorSelection().trim() : null);
        med.setConcilReplacement(!"".equals(item.getConciliatorReplacement().trim()) ? item.getConciliatorReplacement().trim() : null);
        med.setConcilOriginalConciliator(!"".equals(item.getTempHolder2().trim()) ? item.getTempHolder2().trim() : null); 
        med.setConcilOriginalConcilDate(!"".equals(item.getOrgConcilDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getOrgConcilDate()).getTime()) : null);
        med.setConcilList2OrderDate(!"".equals(item.getConciliationOrderDate2().trim()) ? new Date(StringUtilities.convertStringDate(item.getConciliationOrderDate2()).getTime()) : null);
        med.setConcilList2SelectionDueDate(!"".equals(item.getConciliationSelectionDate2().trim()) ? new Date(StringUtilities.convertStringDate(item.getConciliationSelectionDate2()).getTime()) : null);
        med.setConcilList2Name1(!"".equals(item.getConciliator1Set2().trim()) ? item.getConciliator1Set2().trim() : null);
        med.setConcilList2Name2(!"".equals(item.getConciliator2Set2().trim()) ? item.getConciliator2Set2().trim() : null);
        med.setConcilList2Name3(!"".equals(item.getConciliator3Set2().trim()) ? item.getConciliator3Set2().trim() : null);
        med.setConcilList2Name4(!"".equals(item.getConciliator4Set2().trim()) ? item.getConciliator4Set2().trim() : null);
        med.setConcilList2Name5(!"".equals(item.getConciliator5Set2().trim()) ? item.getConciliator5Set2().trim() : null);
        med.setFFList1OrderDate(!"".equals(item.getFFPanelSelectionDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getFFPanelSelectionDate().trim()).getTime()) : null);
        med.setFFList1SelectionDueDate(!"".equals(item.getFFPanelSelectionDue().trim()) ? new Date(StringUtilities.convertStringDate(item.getFFPanelSelectionDue().trim()).getTime()) : null);
        med.setFFList1Name1(!"".equals(item.getFactFinder1().trim()) ? item.getFactFinder1().trim() : null);
        med.setFFList1Name2(!"".equals(item.getFactFinder2().trim()) ? item.getFactFinder2().trim() : null);
        med.setFFList1Name3(!"".equals(item.getFactFinder3().trim()) ? item.getFactFinder3().trim() : null);
        med.setFFList1Name4(!"".equals(item.getFactFinder4().trim()) ? item.getFactFinder4().trim() : null);
        med.setFFList1Name5(!"".equals(item.getFactFinder5().trim()) ? item.getFactFinder5().trim() : null);
        med.setFFAppointmentDate(!"".equals(item.getFactFinderApptDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getFactFinderApptDate().trim()).getTime()) : null);
        med.setFFType(!"".equals(item.getFactFinderType().trim()) ? item.getFactFinderType().trim() : null);
        med.setFFSelection(!"".equals(item.getFactFinderSelected().trim()) ? item.getFactFinderSelected().trim() : null);
        med.setFFReplacement(!"".equals(item.getFactFinderRepalcement().trim()) ? item.getFactFinderRepalcement().trim() : null);
        med.setFFOriginalFactFinder("".equals(item.getFFOrg().trim()) ? item.getFFOrg().trim() : null);
        med.setFFOriginalFactFinderDate(!"".equals(item.getOrgFFDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getOrgFFDate().trim()).getTime()) : null);
        med.setAsAgreedToByParties("1".equals(item.getAgreedByTheParties().trim()) ? 1 : 0);
        med.setFFList2OrderDate(!"".equals(item.getFFPanelSelectionDate2().trim()) ? new Date(StringUtilities.convertStringDate(item.getFFPanelSelectionDate2().trim()).getTime()) : null);
        med.setFFList2SelectionDueDate(!"".equals(item.getFFPanelSelectionDue2().trim()) ? new Date(StringUtilities.convertStringDate(item.getFFPanelSelectionDue2().trim()).getTime()) : null);
        med.setFFList2Name1(!"".equals(item.getFactFinder1Set2().trim()) ? item.getFactFinder1Set2().trim() : null);
        med.setFFList2Name2(!"".equals(item.getFactFinder2Set2().trim()) ? item.getFactFinder2Set2().trim() : null);
        med.setFFList2Name3(!"".equals(item.getFactFinder3Set2().trim()) ? item.getFactFinder3Set2().trim() : null);
        med.setFFList2Name4(!"".equals(item.getFactFinder4Set2().trim()) ? item.getFactFinder4Set2().trim() : null);
        med.setFFList2Name5(!"".equals(item.getFactFinder5Set2().trim()) ? item.getFactFinder5Set2().trim() : null);
        med.setFFEmployerType(!"".equals(item.getEmployerType().trim()) ? item.getEmployerType().trim() : null);
        med.setFFEmployeeType(!"".equals(item.getEmployeeType().trim()) ? item.getEmployeeType().trim() : null);
        med.setFFReportIssueDate(!"".equals(item.getReportIssuedDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getReportIssuedDate().trim()).getTime()) : null);
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
        med.setExpirationDate(!"".equals(item.getExpirationDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getExpirationDate().trim()).getTime()) : null);
        med.setNTNFiledBy(!"".equals(item.getNTNFiledBy().trim()) ? item.getNTNFiledBy().trim() : null);
        med.setNegotiationPeriod(!"".equals(item.getNegotiationPeriod().trim()) ? item.getNegotiationPeriod().trim() : null);
        med.setMultiunitBargainingRequested(item.getMultiUnitBargainingRequested().equals("1"));
        med.setMediatorAppointedDate(!"".equals(item.getMediatorApptDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getMediatorApptDate().trim()).getTime()) : null);
        med.setMediatorReplacement(item.getMediatorInvolved().equals("1"));
        med.setSettlementDate(!"".equals(item.getCBAReceivedDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getCBAReceivedDate().trim()).getTime()) : null);
        med.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
        med.setSendToBoardToClose(item.getTempHolder4().equals("Send to Brd to Close"));
        med.setBoardFinalDate(!"".equals(item.getTempHolder3().trim()) ? new Date(StringUtilities.convertStringDate(item.getTempHolder3().trim()).getTime()) : null);
        med.setRetentionTicklerDate(!"".equals(item.getTicklerDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getTicklerDate().trim()).getTime()) : null);
        med.setLateFiling(item.getLateFiling().equals("1"));
        med.setImpasse(item.getImpasse().equals("1"));
        med.setSettled(item.getSettledCheckBox().equals("1"));
        med.setTA(item.getTACheckBox().equals("1"));
        med.setMAD(item.getMADCheckBox().equals("1"));
        med.setWithdrawl(item.getWithdraw().equals("1"));
        med.setMotion(item.getMotionCheckBox().equals("1"));
        med.setDismissed(item.getDismiss().equals("1"));

        
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
        
        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "Notes":
                    med.setNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
//                case "StkNotes":
//                    med.setStkNotes(StringUtilities.convertBlobFileToString(blob.getBlobData()));
//                    break;
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
            search.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? new Date(StringUtilities.convertStringDate(item.getCaseFileDate()).getTime()) : null); 
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerIDNumber().trim()));

            sqlEmployerCaseSearchData.addEmployer(search);
        }
    }
}
