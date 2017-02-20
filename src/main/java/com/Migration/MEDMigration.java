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
import com.model.jurisdictionModel;
import com.model.mediatorsModel;
import com.model.oldBlobFileModel;
import com.model.oldMEDCaseModel;
import com.model.relatedCaseModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployerCaseSearchData;
import com.sql.sqlEmployers;
import com.sql.sqlFactFinder;
import com.sql.sqlJurisdiction;
import com.sql.sqlMEDCaseSearch;
import com.sql.sqlMEDData;
import com.sql.sqlMediator;
import com.sql.sqlMigrationStatus;
import com.sql.sqlRelatedCase;
import com.util.ExcelIterator;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class MEDMigration {

    private static List<mediatorsModel> newMediatorsList;
    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static List<casePartyModel> casePartyList = new ArrayList<>();
    private static List<MEDCaseModel> caseList = new ArrayList<>();
    private static List<employerCaseSearchModel> EmployerSearchList = new ArrayList<>();
    private static List<MEDCaseSearchModel> caseSearchList = new ArrayList<>();
    private static List<relatedCaseModel> relatedCaseList = new ArrayList<>();

    public static void migrateMEDData(final MainWindowSceneController control) {
        Thread medThread = new Thread() {
            @Override
            public void run() {
                medThread(control);
            }
        };
        medThread.start();
    }

    public static void medThread(MainWindowSceneController controlPassed) {
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("MED Case Migration");
        totalRecordCount = 0;
        currentRecord = 0;

        List<oldMEDCaseModel> oldMEDCaseList = sqlMEDData.getCases();
        if (Global.isDebug()){
            System.out.println("Gathered MED Cases");
        }
        List<factFinderModel> oldFactFindersList = sqlFactFinder.getOldFactFinders();
        if (Global.isDebug()){
            System.out.println("Gathered Fact Finders");
        }
        List<mediatorsModel> oldMediatorsList = sqlMediator.getOldMediator();
        if (Global.isDebug()){
            System.out.println("Gathered Mediators");
        }
        List<jurisdictionModel> oldjurisdictionList = sqlJurisdiction.getOldJurisdiction();
        if (Global.isDebug()){
            System.out.println("Gathered Jurisdiction");
        }
        List<activityModel> MEDCaseHistoryList = sqlActivity.getMEDHistory();
        if (Global.isDebug()){
            System.out.println("Gathered MED History");
        }
        List<factFinderModel> cleanedBiosList = new ArrayList<>();
        ArrayList FactFinderBioXLS = ExcelIterator.read("FactFinderBios.xlsx");

        for (Iterator iterator = FactFinderBioXLS.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            if (list.size() == 4) {
                cleanedBiosList.add(sanitizeCMDSReportsFromExcel(list));
            }
        }
        if (Global.isDebug()){
            System.out.println("Gathered Fact Finder Bios List");
        }

        totalRecordCount = oldMediatorsList.size();

        sqlMediator.batchAddMediator(oldMediatorsList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldMediatorsList.size(), totalRecordCount, "Mediators Finished");
        oldMediatorsList = null;
        newMediatorsList = sqlMediator.getNewMediator();
        
        //Clean MED Case Data
        currentRecord = 0;
        totalRecordCount = oldMEDCaseList.size();
        control.setProgressBarIndeterminateCleaning("MED Case");
        oldMEDCaseList.stream().forEach(item ->
                executor.submit(() ->
                        migrateCase(item)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }

        oldMEDCaseList = null;

        currentRecord = 0;
        totalRecordCount = oldFactFindersList.size() + oldMediatorsList.size()
                + oldjurisdictionList.size() + MEDCaseHistoryList.size()
                + relatedCaseList.size() + caseSearchList.size() + EmployerSearchList.size()
                + caseList.size() + casePartyList.size();

        sqlActivity.batchAddActivity(MEDCaseHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + MEDCaseHistoryList.size() - 1, totalRecordCount, "MED Activities Finished");

        sqlFactFinder.batchAddFactFinder(oldFactFindersList, cleanedBiosList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldFactFindersList.size(), totalRecordCount, "Fact Finders Finished");

        sqlJurisdiction.batchAddJurisdiction(oldjurisdictionList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldjurisdictionList.size(), totalRecordCount, "Jurisdictions Finished");

        sqlCaseParty.batchAddPartyInformation(casePartyList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + casePartyList.size() - 1, totalRecordCount, "MED Case Parties Finished");

        sqlMEDData.batchAddMEDCase(caseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + caseList.size() - 1, totalRecordCount, "MED Case Finished");

        sqlRelatedCase.batchAddRelatedCase(relatedCaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + relatedCaseList.size() - 1, totalRecordCount, "MED Related Cases Finished");

        sqlEmployerCaseSearchData.batchAddEmployerSearch(EmployerSearchList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + EmployerSearchList.size() - 1, totalRecordCount, "MED Employer Search Finished");

        sqlMEDCaseSearch.batchAddMEDCaseSearchCase(caseSearchList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + EmployerSearchList.size() - 1, totalRecordCount, "MED Case Search Finished");

        relatedCaseList = null;
        caseSearchList = null;
        EmployerSearchList = null ;
        caseList = null;
        casePartyList = null;
        oldFactFindersList = null;
        oldMediatorsList = null;
        oldjurisdictionList = null;
        MEDCaseHistoryList = null;

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating MED Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateMEDCases");
        }
        SlackNotification.sendBasicNotification(finishedText);
        System.gc();
    }

    private static void migrateCase(oldMEDCaseModel item) {
        caseNumberModel caseNumber = null;
        if (item.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        } else if (item.getStrikeCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(item.getStrikeCaseNumber().trim());
        }
        if (caseNumber != null) {
            migrateParties(item, caseNumber);
            migrateCaseData(item, caseNumber);
            migrateRelatedCases(item, caseNumber);
            migrateCaseSearch(item, caseNumber);
            migrateEmployerCaseSearch(item, caseNumber);
            currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount,
                    StringUtilities.generateFullCaseNumber(caseNumber));
        }

    }

    private static void migrateParties(oldMEDCaseModel item, caseNumberModel caseNumber) {
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
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            casePartyList.add(party);
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
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            casePartyList.add(party);
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
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            casePartyList.add(party);
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
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            casePartyList.add(party);
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
        med.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getCaseFileDate())) : null);
        med.setConcilList1OrderDate(!"".equals(item.getConciliationOrderDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getConciliationOrderDate())) : null);
        med.setConcilList1SelectionDueDate(!"".equals(item.getConciliationSelectionDue().trim()) ? (StringUtilities.convertStringSQLDate(item.getConciliationSelectionDue())) : null);
        med.setConcilList1Name1(!"".equals(item.getConciliator1().trim()) ? item.getConciliator1().trim() : null);
        med.setConcilList1Name2(!"".equals(item.getConciliator2().trim()) ? item.getConciliator2().trim() : null);
        med.setConcilList1Name3(!"".equals(item.getConciliator3().trim()) ? item.getConciliator3().trim() : null);
        med.setConcilList1Name4(!"".equals(item.getConciliator4().trim()) ? item.getConciliator4().trim() : null);
        med.setConcilList1Name5(!"".equals(item.getConciliator5().trim()) ? item.getConciliator5().trim() : null);
        med.setConcilAppointmentDate(!"".equals(item.getConciliatorApptDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getConciliatorApptDate())) : null);
        med.setConcilType(!"".equals(item.getConciliationType().trim()) ? item.getConciliationType().trim() : null);
        med.setConcilSelection(!"".equals(item.getConciliatorSelection().trim()) ? item.getConciliatorSelection().trim() : null);
        med.setConcilReplacement(!"".equals(item.getConciliatorReplacement().trim()) ? item.getConciliatorReplacement().trim() : null);
        med.setConcilOriginalConciliator(!"".equals(item.getTempHolder2().trim()) ? item.getTempHolder2().trim() : null);
        med.setConcilOriginalConcilDate(!"".equals(item.getOrgConcilDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getOrgConcilDate())) : null);
        med.setConcilList2OrderDate(!"".equals(item.getConciliationOrderDate2().trim()) ? (StringUtilities.convertStringSQLDate(item.getConciliationOrderDate2())) : null);
        med.setConcilList2SelectionDueDate(!"".equals(item.getConciliationSelectionDate2().trim()) ? (StringUtilities.convertStringSQLDate(item.getConciliationSelectionDate2())) : null);
        med.setConcilList2Name1(!"".equals(item.getConciliator1Set2().trim()) ? item.getConciliator1Set2().trim() : null);
        med.setConcilList2Name2(!"".equals(item.getConciliator2Set2().trim()) ? item.getConciliator2Set2().trim() : null);
        med.setConcilList2Name3(!"".equals(item.getConciliator3Set2().trim()) ? item.getConciliator3Set2().trim() : null);
        med.setConcilList2Name4(!"".equals(item.getConciliator4Set2().trim()) ? item.getConciliator4Set2().trim() : null);
        med.setConcilList2Name5(!"".equals(item.getConciliator5Set2().trim()) ? item.getConciliator5Set2().trim() : null);
        med.setFFList1OrderDate(!"".equals(item.getFFPanelSelectionDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getFFPanelSelectionDate().trim())) : null);
        med.setFFList1SelectionDueDate(!"".equals(item.getFFPanelSelectionDue().trim()) ? (StringUtilities.convertStringSQLDate(item.getFFPanelSelectionDue().trim())) : null);
        med.setFFList1Name1(!"".equals(item.getFactFinder1().trim()) ? item.getFactFinder1().trim() : null);
        med.setFFList1Name2(!"".equals(item.getFactFinder2().trim()) ? item.getFactFinder2().trim() : null);
        med.setFFList1Name3(!"".equals(item.getFactFinder3().trim()) ? item.getFactFinder3().trim() : null);
        med.setFFList1Name4(!"".equals(item.getFactFinder4().trim()) ? item.getFactFinder4().trim() : null);
        med.setFFList1Name5(!"".equals(item.getFactFinder5().trim()) ? item.getFactFinder5().trim() : null);
        med.setFFAppointmentDate(!"".equals(item.getFactFinderApptDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getFactFinderApptDate().trim())) : null);
        med.setFFType(!"".equals(item.getFactFinderType().trim()) ? item.getFactFinderType().trim() : null);
        med.setFFSelection(!"".equals(item.getFactFinderSelected().trim()) ? item.getFactFinderSelected().trim() : null);
        med.setFFReplacement(!"".equals(item.getFactFinderRepalcement().trim()) ? item.getFactFinderRepalcement().trim() : null);
        med.setFFOriginalFactFinder("".equals(item.getFFOrg().trim()) ? item.getFFOrg().trim() : null);
        med.setFFOriginalFactFinderDate(!"".equals(item.getOrgFFDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getOrgFFDate().trim())) : null);
        med.setAsAgreedToByParties("1".equals(item.getAgreedByTheParties().trim()) ? 1 : 0);
        med.setFFList2OrderDate(!"".equals(item.getFFPanelSelectionDate2().trim()) ? (StringUtilities.convertStringSQLDate(item.getFFPanelSelectionDate2().trim())) : null);
        med.setFFList2SelectionDueDate(!"".equals(item.getFFPanelSelectionDue2().trim()) ? (StringUtilities.convertStringSQLDate(item.getFFPanelSelectionDue2().trim())) : null);
        med.setFFList2Name1(!"".equals(item.getFactFinder1Set2().trim()) ? item.getFactFinder1Set2().trim() : null);
        med.setFFList2Name2(!"".equals(item.getFactFinder2Set2().trim()) ? item.getFactFinder2Set2().trim() : null);
        med.setFFList2Name3(!"".equals(item.getFactFinder3Set2().trim()) ? item.getFactFinder3Set2().trim() : null);
        med.setFFList2Name4(!"".equals(item.getFactFinder4Set2().trim()) ? item.getFactFinder4Set2().trim() : null);
        med.setFFList2Name5(!"".equals(item.getFactFinder5Set2().trim()) ? item.getFactFinder5Set2().trim() : null);
        med.setFFEmployerType(!"".equals(item.getEmployerType().trim()) ? item.getEmployerType().trim() : null);
        med.setFFEmployeeType(!"".equals(item.getEmployeeType().trim()) ? item.getEmployeeType().trim() : null);
        med.setFFReportIssueDate(!"".equals(item.getReportIssuedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getReportIssuedDate().trim())) : null);
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
        med.setExpirationDate(!"".equals(item.getExpirationDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getExpirationDate().trim())) : null);
        med.setNTNFiledBy(!"".equals(item.getNTNFiledBy().trim()) ? item.getNTNFiledBy().trim() : null);
        med.setNegotiationPeriod(!"".equals(item.getNegotiationPeriod().trim()) ? item.getNegotiationPeriod().trim() : null);
        med.setMultiunitBargainingRequested(item.getMultiUnitBargainingRequested().equals("1"));
        med.setMediatorAppointedDate(!"".equals(item.getMediatorApptDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getMediatorApptDate().trim())) : null);
        med.setMediatorReplacement(item.getMediatorInvolved().equals("1"));
        med.setSettlementDate(!"".equals(item.getCBAReceivedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getCBAReceivedDate().trim())) : null);
        med.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
        med.setSendToBoardToClose(item.getTempHolder4().equals("Send to Brd to Close"));
        med.setBoardFinalDate(!"".equals(item.getTempHolder3().trim()) ? (StringUtilities.convertStringSQLDate(item.getTempHolder3().trim())) : null);
        med.setRetentionTicklerDate(!"".equals(item.getTicklerDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getTicklerDate().trim())) : null);
        med.setLateFiling(item.getLateFiling().equals("1"));
        med.setImpasse(item.getImpasse().equals("1"));
        med.setSettled(item.getSettledCheckBox().equals("1"));
        med.setTA(item.getTACheckBox().equals("1"));
        med.setMAD(item.getMADCheckBox().equals("1"));
        med.setWithdrawl(item.getWithdraw().equals("1"));
        med.setMotion(item.getMotionCheckBox().equals("1"));
        med.setDismissed(item.getDismiss().equals("1"));
        med.setStrikeFileDate(!"".equals(item.getStrikeFilingDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getStrikeFilingDate().trim())) : null);
        if (caseNumber.getCaseType().equals("MED") && !item.getStrikeCaseNumber().trim().equals("")) {
            med.setRelatedCaseNumber(!"".equals(item.getStrikeCaseNumber().trim()) ? item.getStrikeCaseNumber().trim() : null);
        }
        med.setUnitDescription(!"".equals(item.getBUDescription().trim()) ? item.getBUDescription().trim() : null);
        med.setUnitSize(!"".equals(item.getBUSize().trim()) ? item.getBUSize().trim() : null);
        med.setUnauthorizedStrike(item.getUnauthorizedStrike().equals("Y"));
        med.setNoticeOfIntentToStrikeOnly(item.getNoticeOfIntentToStrike().equals("1"));
        med.setIntendedDateStrike(!"".equals(item.getIntendedStrikeDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getIntendedStrikeDate().trim())) : null);
        med.setNoticeOfIntentToPicketOnly(item.getNoticeofIntentToPicket().equals("1"));
        med.setIntendedDatePicket(!"".equals(item.getIntendedPicketDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getIntendedPicketDate().trim())) : null);
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
        med.setStrikeBegan(!"".equals(item.getStrikeBegan().trim()) ? (StringUtilities.convertStringSQLDate(item.getStrikeBegan().trim())) : null);
        med.setStrikeEnded(!"".equals(item.getStrikeEnded().trim()) ? (StringUtilities.convertStringSQLDate(item.getStrikeEnded().trim())) : null);
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
            if (item.getTempHolder5().toLowerCase().startsWith(person.getFirstName().toLowerCase())
                    || item.getTempHolder5().toLowerCase().endsWith(person.getLastName().toLowerCase())) {
                med.setStrikeMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }

        for (mediatorsModel person : newMediatorsList) {
            if (item.getStateMediatorAppt().toLowerCase().startsWith(person.getFirstName().toLowerCase())
                    || item.getStateMediatorAppt().toLowerCase().endsWith(person.getLastName().toLowerCase())) {
                med.setStateMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }

        for (mediatorsModel person : newMediatorsList) {
            if (item.getFMCSMediatorAppt().toLowerCase().startsWith(person.getFirstName().toLowerCase())
                    || item.getFMCSMediatorAppt().toLowerCase().endsWith(person.getLastName().toLowerCase())) {
                med.setFMCSMediatorAppointedID(String.valueOf(person.getID()));
                break;
            }
        }

        med.setNote(note);

        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) {
                switch (blob.getSelectorA().trim()) {
                    case "Notes":
                        String note2 = StringUtilities.convertBlobFileToString(blob.getBlobData());

                        if (note2 != null) {
                            if (note != null) {
                                if (!note.trim().equals("")) {
                                    note += System.lineSeparator() + System.lineSeparator() + note2;
                                } else {
                                    note += note2;
                                }
                            } else {
                                note = note2;
                            }
                        }

                        if (note != null) {
                            if (note.trim().equals("")) {
                                note = null;
                            }
                        }
                        med.setNote(note);
                        break;
                    case "StkNotes":
                        String stkNote = StringUtilities.convertBlobFileToString(blob.getBlobData());
                        if (stkNote != null) {
                            if (stkNote.trim().equals("")) {
                                stkNote = null;
                            }
                        }

                        med.setStrikeNote(stkNote);
                        break;
                    case "FFNotes":
                        String ffNote = StringUtilities.convertBlobFileToString(blob.getBlobData());
                        if (ffNote != null) {
                            if (ffNote.trim().equals("")) {
                                ffNote = null;
                            }
                        }

                        med.setFFNote(ffNote);
                        break;
                    default:
                        break;
                }
            }
        }

        if (med.getNote() != null) {
            if (med.getNote().trim().equals("")) {
                med.setNote(null);
            }
        }

        caseList.add(med);
    }

    private static void migrateRelatedCases(oldMEDCaseModel item, caseNumberModel caseNumber) {
        relatedCaseModel relatedCase = new relatedCaseModel();

        relatedCase.setCaseYear(caseNumber.getCaseYear());
        relatedCase.setCaseType(caseNumber.getCaseType());
        relatedCase.setCaseMonth(caseNumber.getCaseMonth());
        relatedCase.setCaseNumber(caseNumber.getCaseNumber());

        if (!item.getCaseNumber2().trim().equals("")) {
            relatedCase.setRelatedCaseNumber(item.getCaseNumber2().trim());
            relatedCaseList.add(relatedCase);
        }
        if (!item.getCaseNumber3().trim().equals("")) {
            relatedCase.setRelatedCaseNumber(item.getCaseNumber3().trim());
            relatedCaseList.add(relatedCase);
        }
        if (!item.getCaseNumber4().trim().equals("")) {
            relatedCase.setRelatedCaseNumber(item.getCaseNumber4().trim());
            relatedCaseList.add(relatedCase);
        }
        if (!item.getCaseNumber5().trim().equals("")) {
            relatedCase.setRelatedCaseNumber(item.getCaseNumber5().trim());
            relatedCaseList.add(relatedCase);
        }
        if (!item.getCaseNumber6().trim().equals("")) {
            relatedCase.setRelatedCaseNumber(item.getCaseNumber6().trim());
            relatedCaseList.add(relatedCase);
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

        caseSearchList.add(search);
    }

    private static void migrateEmployerCaseSearch(oldMEDCaseModel item, caseNumberModel caseNumber) {
        if (!"".equals(item.getEmployerIDNumber().trim())) {
            employerCaseSearchModel search = new employerCaseSearchModel();

            search.setCaseYear(caseNumber.getCaseYear());
            search.setCaseType(caseNumber.getCaseType());
            search.setCaseMonth(caseNumber.getCaseMonth());
            search.setCaseNumber(caseNumber.getCaseNumber());
            search.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
            search.setFileDate(!"".equals(item.getCaseFileDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getCaseFileDate())) : null);
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerIDNumber().trim()));
            search.setEmployerID(item.getEmployerIDNumber().trim());

            EmployerSearchList.add(search);
        }
    }

    private static factFinderModel sanitizeCMDSReportsFromExcel(List list) {
        factFinderModel item = new factFinderModel();
        item.setFirstName(list.get(0).toString().trim().equals("NULL") ? "" : list.get(0).toString().trim());
        item.setMiddleName(list.get(1).toString().trim().equals("NULL") ? "" : list.get(1).toString().trim());
        item.setLastName(list.get(2).toString().trim().equals("NULL") ? "" : list.get(2).toString().trim());
        item.setBioFileName(list.get(3).toString().trim().equals("NULL") ? "" : list.get(3).toString().trim());

        return item;
    }

}
