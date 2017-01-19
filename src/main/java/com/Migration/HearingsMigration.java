/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.HearingsCaseModel;
import com.model.HearingsCaseSearchModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.HearingOutcomeModel;
import com.model.activityModel;
import com.model.oldHearingsMediationModel;
import com.model.oldSMDSCaseTrackingModel;
import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlCaseParty;
import com.sql.sqlHearingOutcome;
import com.sql.sqlHearingsCase;
import com.sql.sqlHearingsCaseSearch;
import com.sql.sqlHearingsMediation;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class HearingsMigration {
    
    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static final List<HearingsCaseModel> HearingsCaseList = new ArrayList<>();
    private static final List<HearingsCaseSearchModel> HearingsCaseSearchList = new ArrayList<>();
    
    public static void migrateHearingsData(final MainWindowSceneController control){
        Thread hearingThread = new Thread() {
            @Override
            public void run() {
                hearingsThread(control);
            }
        };
        hearingThread.start();        
    }
    
    public static void hearingsThread(MainWindowSceneController controlPassed){
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("Hearings Case Migration");
        totalRecordCount = 0;
        currentRecord = 0;
        List<oldSMDSCaseTrackingModel> oldHearingCaseList = sqlHearingsCase.getCases();
        if (Global.isDebug()){
            System.out.println("Gathered Hearing Cases");
        }
        List<activityModel> oldHearingsHistoryList = sqlActivity.getHearingsHistory();
        if (Global.isDebug()){
            System.out.println("Gathered Hearing History");
        }
        List<oldHearingsMediationModel> oldHearingsMediationList = sqlHearingsMediation.getHearingsMediations();
        if (Global.isDebug()){
            System.out.println("Gathered Hearing Mediations");
        }
        List<HearingOutcomeModel> oldHearingOutcomeList = sqlHearingOutcome.getOutcomeList();
        if (Global.isDebug()){
            System.out.println("Gathered Hearing Outcomes");
        }
        
        //Clean ULP Case Data
        control.setProgressBarIndeterminateCleaning("Hearing Case");
        totalRecordCount = oldHearingCaseList.size();
        oldHearingCaseList.stream().forEach(item ->
                executor.submit(() ->
                        migrateCase(item)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        
        currentRecord = 0;
        totalRecordCount = oldHearingCaseList.size() + oldHearingsHistoryList.size()
                + oldHearingsMediationList.size() + oldHearingOutcomeList.size()
                + HearingsCaseSearchList.size() + HearingsCaseList.size();
        
        sqlHearingsCase.batchAddHearingsCase(HearingsCaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, HearingsCaseList.size() + currentRecord, totalRecordCount, "Hearing Cases Finished");
        
        sqlHearingsCaseSearch.importHearingCaseSearch(HearingsCaseSearchList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, HearingsCaseSearchList.size() + currentRecord, totalRecordCount, "Hearing Case Search Finished");
        
        sqlHearingOutcome.batchAddOutcome(oldHearingOutcomeList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldHearingOutcomeList.size() + currentRecord, totalRecordCount, "Hearing Outcomes Finished");
        
        sqlActivity.batchAddActivity(oldHearingsHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldHearingsHistoryList.size() + currentRecord, totalRecordCount, "Hearing Activities Finished");
        
        sqlHearingsMediation.batchAddOldHearingsMediation(oldHearingsMediationList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldHearingsMediationList.size() + currentRecord, totalRecordCount, "Hearing Mediations Finished");
        
        HearingsCaseSearchList.clear();
        HearingsCaseList.clear();
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Hearings Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateHearingsCases");
        }
    }
        
    private static void migrateCase(oldSMDSCaseTrackingModel item) {
        caseNumberModel caseNumber = null;
        if (item.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
            migrateCaseData(item, caseNumber);
            migrateSearchData(item, caseNumber);
        }
        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
    }

    private static void migrateCaseData(oldSMDSCaseTrackingModel old, caseNumberModel caseNumber) {
        String companionCase = "";
        int aljID = 0;

        if (!old.getCompanionCaseNumber1().equals("")) {
            companionCase = old.getCompanionCaseNumber1();
        }

        if (!old.getCompanionCaseNumber2().equals("")) {
            if (!companionCase.trim().equals("")) {
                companionCase += ", ";
            }
            companionCase += old.getCompanionCaseNumber2();
        }

        if (!old.getCompanionCaseNumber3().equals("")) {
            if (!companionCase.trim().equals("")) {
                companionCase += ", ";
            }
            companionCase += old.getCompanionCaseNumber3();
        }

        if (!old.getCompanionCaseNumber4().equals("")) {
            if (!companionCase.trim().equals("")) {
                companionCase += ", ";
            }
            companionCase += old.getCompanionCaseNumber4();
        }

        if (!old.getCompanionCaseNumber5().equals("")) {
            if (!companionCase.trim().equals("")) {
                companionCase += ", ";
            }
            companionCase += old.getCompanionCaseNumber5();
        }

        if (!old.getALJInitials().trim().equals("")) {
            for (userModel user : Global.getUserList()) {
                if (user.getLastName().equalsIgnoreCase(old.getALJInitials()) || user.getInitials().equalsIgnoreCase(old.getALJInitials())) {
                    aljID = user.getId();
                    break;
                }
            }
        }

        HearingsCaseModel item = new HearingsCaseModel();
        item.setCaseYear(caseNumber.getCaseYear());
        item.setCaseType(caseNumber.getCaseType());
        item.setCaseMonth(caseNumber.getCaseMonth());
        item.setCaseNumber(caseNumber.getCaseNumber());
        item.setOpenClose(old.getOpenYN().equals("Y") ? "Open" : "Closed");
        item.setExpedited(old.getExpeditedYN().equals("Y"));
        item.setBoardActionPCDate(old.getBoardActionPCDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBoardActionPCDate())));
        item.setBoardActionPreDDate(old.getBrdActionPreDDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBrdActionPreDDate())));
        item.setDirectiveIssueDate(old.getDirectiveIssuedDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getDirectiveIssuedDate())));
        item.setComplaintDueDate(old.getComplaintDueDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getComplaintDueDate())));
        item.setDraftComplaintToHearingDate(old.getDraftComplaintToHearingDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getComplaintDueDate())));
        item.setPreHearingDate(old.getPreHearingDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getPreHearingDate())));
        item.setProposedRecDueDate(old.getProposedRecDueDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getProposedRecDueDate())));
        item.setExceptionFilingDate(old.getExceptionsFilingDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getExceptionsFilingDate())));
        item.setBoardActionDate(old.getBoardActionDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBoardActionDate())));
        item.setOtherAction(old.getOtherAction().equals("") ? null : old.getOtherAction().trim());
        item.setAljID(aljID);
        item.setComplaintIssuedDate(old.getComplaintIssuedDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getComplaintIssuedDate())));
        item.setHearingDate(old.getHearingDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getComplaintIssuedDate())));
        item.setProposedRecIssuedDate(old.getProposedRecIssuedDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getComplaintIssuedDate())));
        item.setResponseFilingDate(old.getResponceFilingDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getResponceFilingDate())));
        item.setIssuanceOfOptionOrDirectiveDate(old.getIssuanceOfOptionOrDirectiveDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getIssuanceOfOptionOrDirectiveDate())));
        item.setFinalResult(old.getFinalResult().equals("") ? null : old.getFinalResult().trim());
        item.setOpinion(old.getOpinion().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getOpinion())));
        item.setCompanionCases(companionCase.trim().equals("") ? null : companionCase.trim());
        item.setCaseStatusNotes(null);

        HearingsCaseList.add(item);
    }
    
    private static void migrateSearchData(oldSMDSCaseTrackingModel old, caseNumberModel caseNumber) {
        HearingsCaseSearchModel item = new HearingsCaseSearchModel();
        List<casePartyModel> partyList = sqlCaseParty.getCasePartyFromParties(
                caseNumber.getCaseYear(), caseNumber.getCaseType(), caseNumber.getCaseMonth(), caseNumber.getCaseNumber());
        String partyNames = "";
        String ALJName = "";
        
        if (!old.getALJInitials().trim().equals("")) {
            for (userModel user : Global.getUserList()) {
                if (user.getLastName().equalsIgnoreCase(old.getALJInitials()) || user.getInitials().equalsIgnoreCase(old.getALJInitials())) {
                    ALJName = StringUtilities.buildFullName(
                            user.getFirstName() == null ? "" : user.getFirstName(), 
                            user.getMiddleInitial() == null ? "" : user.getMiddleInitial(), 
                            user.getLastName() == null ? "" : user.getLastName()
                    );
                    break;
                }
            }
        }

        for (casePartyModel party : partyList) {
            String partyName = StringUtilities.buildFullName(
                    party.getFirstName() == null ? "" : party.getFirstName(), 
                    party.getMiddleInitial() == null ? "" : party.getMiddleInitial(), 
                    party.getLastName() == null ? "" : party.getLastName());
            String companyName = party.getCompanyName();
            
            if (!partyNames.trim().equals("") && (!partyName.trim().equals("") || !companyName.trim().equals(""))){
                partyNames += ", ";
            }
            
            partyNames +=  partyName.trim().equals("") ? companyName.trim() : partyName.trim();
        }
        
        item.setCaseYear(caseNumber.getCaseYear());
        item.setCaseType(caseNumber.getCaseType());
        item.setCaseMonth(caseNumber.getCaseMonth());
        item.setCaseNumber(caseNumber.getCaseNumber());
        item.setHearingStatus(old.getOpenYN().equals("Y") ? "Open" : "Closed");
        item.setHearingParties(partyNames.trim().equals("") ? null : partyNames.trim());
        item.setHearingPCDate(old.getBoardActionPCDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBoardActionPCDate())));
        item.setHearingALJ(ALJName.trim().equals("") ? null : ALJName.trim());
        item.setHearingBoardActionDate(old.getBoardActionDate().equals("") ? null : (StringUtilities.convertStringSQLDate(old.getBoardActionDate())));

        HearingsCaseSearchList.add(item);
    }

}
