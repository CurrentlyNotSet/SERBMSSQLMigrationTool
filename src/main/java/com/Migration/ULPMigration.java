/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ULPCaseModel;
import com.model.ULPCaseSearchModel;
import com.model.ULPRecommendationsModel;
import com.model.activityModel;
import com.model.boardMeetingModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.employerCaseSearchModel;
import com.model.oldBlobFileModel;
import com.model.oldULPDataModel;
import com.model.relatedCaseModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlBoardMeeting;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployerCaseSearchData;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.sql.sqlRelatedCase;
import com.sql.sqlULPCaseSearch;
import com.sql.sqlULPData;
import com.sql.sqlULPRecommendations;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class ULPMigration {

    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static final List<casePartyModel> casePartyList = new ArrayList<>();
    private static final List<ULPCaseModel> ULPCaseList = new ArrayList<>();
    private static final List<boardMeetingModel> boardMeetingList = new ArrayList<>();
    private static final List<relatedCaseModel> relatedCaseList = new ArrayList<>();
    private static final List<ULPCaseSearchModel> caseSearchList = new ArrayList<>();
    private static final List<employerCaseSearchModel> EmployerSearchList = new ArrayList<>();

    public static void migrateULPData(MainWindowSceneController control) {
        Thread ulpThread = new Thread() {
            @Override
            public void run() {
                ulpThread(control);
            }
        };
        ulpThread.start();
    }

    public static void ulpThread(MainWindowSceneController controlPassed) {
        try {
            control = controlPassed;
            long lStartTime = System.currentTimeMillis();
            control.setProgressBarIndeterminate("ULP Case Migration");
            
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            
            List<ULPRecommendationsModel> oldULPRecsList = sqlULPRecommendations.getOLDULPRecommendations();
            if (Global.isDebug()){
                System.out.println("Gathered ULP Recommendations");
            }
            List<activityModel> ULPCaseHistoryList = sqlActivity.getULPHistory();
            if (Global.isDebug()){
                System.out.println("Gathered ULP Case History");
            }
            List<oldULPDataModel> oldULPDataList = sqlULPData.getCases();
            if (Global.isDebug()){
                System.out.println("Gathered ULP Case Data");
            }
            
            //Clean ULP Case Data
            control.setProgressBarIndeterminateCleaning("ULP Case");
            totalRecordCount = oldULPDataList.size();
            oldULPDataList.stream().forEach(item
                    -> executor.submit(()
                            -> migrateCase(item)));
            
            executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {
            }
            
            currentRecord = 0;
            totalRecordCount = oldULPRecsList.size() + casePartyList.size()
                    + ULPCaseList.size() + boardMeetingList.size()
                    + relatedCaseList.size() + caseSearchList.size()
                    + EmployerSearchList.size() + ULPCaseHistoryList.size();
            
            //Insert ULP Recommendations
            sqlULPRecommendations.batchAddULPRecommendation(oldULPRecsList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + oldULPRecsList.size() - 1, totalRecordCount, "ULP Recommendations Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlActivity.batchAddActivity(ULPCaseHistoryList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + ULPCaseHistoryList.size() - 1, totalRecordCount, "ULP Activities Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlCaseParty.batchAddPartyInformation(casePartyList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + casePartyList.size() - 1, totalRecordCount, "ULP Case Parties Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlULPData.batchImportULPCase(ULPCaseList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + ULPCaseList.size() - 1, totalRecordCount, "ULP Case Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlBoardMeeting.batchAddBoardMeeting(boardMeetingList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + boardMeetingList.size() - 1, totalRecordCount, "ULP Board Meetings Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlRelatedCase.batchAddRelatedCase(relatedCaseList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + relatedCaseList.size() - 1, totalRecordCount, "ULP Related Cases Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlULPCaseSearch.batchAddULPCaseSearchCase(caseSearchList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + caseSearchList.size() - 1, totalRecordCount, "ULP Case Search Finished");
            Thread.sleep(Global.getSLEEP());
            
            sqlEmployerCaseSearchData.batchAddEmployerSearch(EmployerSearchList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord + EmployerSearchList.size() - 1, totalRecordCount, "ULP Employer Search Finished");
            Thread.sleep(Global.getSLEEP());
            
            casePartyList.clear();
            ULPCaseList.clear();
            boardMeetingList.clear();
            relatedCaseList.clear();
            caseSearchList.clear();
            EmployerSearchList.clear();
            
            long lEndTime = System.currentTimeMillis();
            String finishedText = "Finished Migrating ULP Cases: "
                    + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
            control.setProgressBarDisable(finishedText);
            if (Global.isDebug() == false) {
                sqlMigrationStatus.updateTimeCompleted("MigrateULPCases");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ULPMigration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void migrateCase(oldULPDataModel item) {
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        casePartyMigration(item, caseNumber);
        migrateCaseData(item, caseNumber);
        migrateBoardMeetings(item, caseNumber);
        migrateRelatedCases(item, caseNumber);
        migrateCaseSearch(item, caseNumber);
        migrateEmployerCaseSearch(item, caseNumber);

        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
    }

    private static void casePartyMigration(oldULPDataModel item, caseNumberModel caseNumber) {
        migrateChargingParty(item, caseNumber);
        migrateChargingPartyRep(item, caseNumber);
        migrateChargedParty(item, caseNumber);
        migrateChargedPartyRep(item, caseNumber);
    }

    private static void migrateChargingParty(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Charging Party");
        party.setLastName(!"".equals(item.getCPName().trim()) ? item.getCPName().trim() : null);
        party.setAddress1(!"".equals(item.getCPAddress1().trim()) ? item.getCPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCPAddress2().trim()) ? item.getCPAddress2().trim() : null);
        party.setCity(!"".equals(item.getCPCity().trim()) ? item.getCPCity().trim() : null);
        party.setState(!"".equals(item.getCPState().trim()) ? item.getCPState().trim() : null);
        party.setZip(!"".equals(item.getCPZip().trim()) ? item.getCPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCPPhone1().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCPPhone1().trim()) : null);
        party.setPhoneTwo(!"".equals(item.getCPPhone2().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCPPhone2().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCPEmail().trim()) ? item.getCPEmail().trim() : null);
        party.setFax(null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            casePartyList.add(party);
        }
    }

    private static void migrateChargingPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Charging Party REP");
        party.setLastName(!"".equals(item.getCPREPName().trim()) ? item.getCPREPName().trim() : null);
        party.setAddress1(!"".equals(item.getCPREPAddress1().trim()) ? item.getCPREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCPREPAddress2().trim()) ? item.getCPREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getCPREPCity().trim()) ? item.getCPREPCity().trim() : null);
        party.setState(!"".equals(item.getCPREPState().trim()) ? item.getCPREPState().trim() : null);
        party.setZip(!"".equals(item.getCPREPZip().trim()) ? item.getCPREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCPREPPhone1().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCPREPPhone1().trim()) : null);
        party.setPhoneTwo(!"".equals(item.getCPREPPhone2().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCPREPPhone2().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCPREPEmail().trim()) ? item.getCPREPEmail().trim() : null);
        party.setFax(null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            casePartyList.add(party);
        }
    }

    private static void migrateChargedParty(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Charged Party");
        party.setLastName(!"".equals(item.getCHDName().trim()) ? item.getCHDName().trim() : null);
        party.setAddress1(!"".equals(item.getCHDAddress1().trim()) ? item.getCHDAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCHDAddress2().trim()) ? item.getCHDAddress2().trim() : null);
        party.setCity(!"".equals(item.getCHDCity().trim()) ? item.getCHDCity().trim() : null);
        party.setState(!"".equals(item.getCHDState().trim()) ? item.getCHDState().trim() : null);
        party.setZip(!"".equals(item.getCHDZip().trim()) ? item.getCHDZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCHDPhone1().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCHDPhone1().trim()) : null);
        party.setPhoneTwo(!"".equals(item.getCHDPhone2().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCHDPhone2().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCHDEmail().trim()) ? item.getCHDEmail().trim() : null);
        party.setFax(null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            casePartyList.add(party);
        }
    }

    private static void migrateChargedPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Charged Party REP");
        party.setLastName(!"".equals(item.getCHDREPName().trim()) ? item.getCHDREPName().trim() : null);
        party.setAddress1(!"".equals(item.getCHDREPAddress1().trim()) ? item.getCHDREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCHDREPAddress2().trim()) ? item.getCHDREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getCHDREPCity().trim()) ? item.getCHDREPCity().trim() : null);
        party.setState(!"".equals(item.getCHDREPState().trim()) ? item.getCHDREPState().trim() : null);
        party.setZip(!"".equals(item.getCHDREPZip().trim()) ? item.getCHDREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCHDREPPhone1().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone1().trim()) : null);
        party.setPhoneTwo(!"".equals(item.getCHDREPPhone2().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone2().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCHDREPEmail().trim()) ? item.getCHDREPEmail().trim() : null);
        party.setFax(null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            casePartyList.add(party);
        }
    }

    private static void migrateCaseData(oldULPDataModel item, caseNumberModel caseNumber) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(StringUtilities.generateFullCaseNumber(caseNumber));

        ULPCaseModel ulpcase = new ULPCaseModel();

        ulpcase.setCaseYear(caseNumber.getCaseYear());
        ulpcase.setCaseType(caseNumber.getCaseType());
        ulpcase.setCaseMonth(caseNumber.getCaseMonth());
        ulpcase.setCaseNumber(caseNumber.getCaseNumber());
        ulpcase.setEmployerIDNumber(!"".equals(item.getEmployerNum().trim()) ? item.getEmployerNum().trim() : null);
        ulpcase.setDeptInState(!"".equals(item.getDeptInState().trim()) ? item.getDeptInState().trim() : null);
        ulpcase.setBarginingUnitNo(!"".equals(item.getBarginingUnitNumber().trim()) ? item.getBarginingUnitNumber().trim() : null);
        ulpcase.setEONumber(!"".equals(item.getEmployeeOrgNumber().trim()) ? item.getEmployeeOrgNumber().trim() : null);
        ulpcase.setAllegation(!"".equals(item.getAllegation().trim()) ? item.getAllegation().trim() : null);
        ulpcase.setCurrentStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
        ulpcase.setPriority("Y".equals(item.getPriority().trim()) || "Yes".equals(item.getPriority().trim()) || "1".equals(item.getPriority().trim()));
        ulpcase.setAssignedDate(StringUtilities.convertStringTimeStamp(item.getAssignedDate()));
        ulpcase.setTurnInDate(StringUtilities.convertStringTimeStamp(item.getTurnInDate()));
        ulpcase.setReportDueDate(StringUtilities.convertStringTimeStamp(item.getReportDate()));
        ulpcase.setDismissalDate(StringUtilities.convertStringTimeStamp(item.getDismissalBoardMeetingDate()));
        ulpcase.setDeferredDate(StringUtilities.convertStringTimeStamp(item.getDeferredBoardMeetingDate()));
        ulpcase.setFileDate(StringUtilities.convertStringTimeStamp(item.getFileDate()));
        ulpcase.setProbableCause("Y".equals(item.getProbable().trim()) || "Yes".equals(item.getProbable().trim()) || "1".equals(item.getProbable().trim()));
        ulpcase.setAppealDateReceived(StringUtilities.convertStringTimeStamp(item.getAppealDateReceived()));
        ulpcase.setAppealDateSent(StringUtilities.convertStringTimeStamp(item.getAppealDateSent()));
        ulpcase.setCourtName(!"".equals(item.getCourt().trim()) ? item.getCourt().trim() : null);
        ulpcase.setCourtCaseNumber(!"".equals(item.getCourtCaseNumber().trim()) ? item.getCourtCaseNumber().trim() : null);
        ulpcase.setSERBCaseNumber(!"".equals(item.getSERBCourtCaseNumber().trim()) ? item.getSERBCourtCaseNumber().trim() : null);
        ulpcase.setFinalDispositionStatus(!"".equals(item.getFinalDispostion().trim()) ? item.getFinalDispostion().trim() : null);
        ulpcase.setInvestigatorID(StringUtilities.convertUserToID(item.getLRSName().trim()));
        ulpcase.setMediatorAssignedID(StringUtilities.convertUserToID(item.getMediatorAssigned().trim()));
        ulpcase.setAljID(StringUtilities.convertUserToID(item.getALJ().trim()));
        ulpcase.setNote(null);
        ulpcase.setInvestigationReveals(null);
        ulpcase.setStatement(null);
        ulpcase.setRecommendation(null);

        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) {
                switch (blob.getSelectorA().trim()) {
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
        }
        ULPCaseList.add(ulpcase);
    }

    private static void migrateBoardMeetings(oldULPDataModel item, caseNumberModel caseNumber) {
        boardMeetingModel meeting = new boardMeetingModel();

        meeting.setCaseYear(caseNumber.getCaseYear());
        meeting.setCaseType(caseNumber.getCaseType());
        meeting.setCaseMonth(caseNumber.getCaseMonth());
        meeting.setCaseNumber(caseNumber.getCaseNumber());
        meeting.setMemoDate(null);

        if (!"".equals(item.getBoardMeetingDate().trim()) || !"".equals(item.getAgendaItem().trim()) || !"".equals(item.getRecommendation().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem().trim()) ? item.getAgendaItem().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation().trim()) ? item.getRecommendation().trim() : null);
            boardMeetingList.add(meeting);
        }

        if (!"".equals(item.getBoardMeetingDate1().trim()) || !"".equals(item.getAgendaItem1().trim()) || !"".equals(item.getRecommendation1().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem1().trim()) ? item.getAgendaItem1().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate1()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate1()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation1().trim()) ? item.getRecommendation1().trim() : null);
            boardMeetingList.add(meeting);
        }

        if (!"".equals(item.getBoardMeetingDate2().trim()) || !"".equals(item.getAgendaItem2().trim()) || !"".equals(item.getRecommendation2().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem2().trim()) ? item.getAgendaItem2().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate2()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate2()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation2().trim()) ? item.getRecommendation2().trim() : null);
            boardMeetingList.add(meeting);
        }
    }

    private static void migrateRelatedCases(oldULPDataModel item, caseNumberModel caseNumber) {
        if (item.getRelatedCases() != null) {
            relatedCaseModel relatedCase = new relatedCaseModel();

            relatedCase.setCaseYear(caseNumber.getCaseYear());
            relatedCase.setCaseType(caseNumber.getCaseType());
            relatedCase.setCaseMonth(caseNumber.getCaseMonth());
            relatedCase.setCaseNumber(caseNumber.getCaseNumber());

            String[] caseNumberArray = item.getRelatedCases().split("[" + System.lineSeparator() + "\\,]");

            for (String casenumber : caseNumberArray) {
                if (!"".equals(casenumber.trim())) {
                    relatedCase.setRelatedCaseNumber(casenumber.trim());
                    relatedCaseList.add(relatedCase);
                }
            }
        }
    }

    private static void migrateCaseSearch(oldULPDataModel item, caseNumberModel caseNumber) {
        ULPCaseSearchModel search = new ULPCaseSearchModel();

        search.setCaseYear(caseNumber.getCaseYear());
        search.setCaseType(caseNumber.getCaseType());
        search.setCaseMonth(caseNumber.getCaseMonth());
        search.setCaseNumber(caseNumber.getCaseNumber());
        search.setChargingParty(!"".equals(item.getCPName().trim()) ? item.getCPName().trim() : null);
        search.setChargedParty(!"".equals(item.getCHDName().trim()) ? item.getCHDName().trim() : null);
        search.setEmployerNumber(!"".equals(item.getEmployerNum().trim()) ? item.getEmployerNum().trim() : null);
        search.setUnionNumber(!"".equals(item.getBarginingUnitNumber().trim()) ? item.getBarginingUnitNumber().trim() : null);

        caseSearchList.add(search);
    }

    private static void migrateEmployerCaseSearch(oldULPDataModel item, caseNumberModel caseNumber) {
        if (!"".equals(item.getEmployerNum().trim())) {

            employerCaseSearchModel search = new employerCaseSearchModel();

            search.setCaseYear(caseNumber.getCaseYear());
            search.setCaseType(caseNumber.getCaseType());
            search.setCaseMonth(caseNumber.getCaseMonth());
            search.setCaseNumber(caseNumber.getCaseNumber());
            search.setCaseStatus(!"".equals(item.getStatus().trim()) ? item.getStatus().trim() : null);
            search.setFileDate(!"".equals(item.getFileDate().trim()) ? StringUtilities.convertStringSQLDate(item.getFileDate()) : null);
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerNum().trim()));
            search.setEmployerID(item.getEmployerNum().trim());

            EmployerSearchList.add(search);
        }
    }
}
