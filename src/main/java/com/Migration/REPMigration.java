/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.boardAcionTypeModel;
import com.model.REPCaseStatusModel;
import com.model.REPCaseTypeModel;
import com.model.REPElectionMultiCaseModel;
import com.model.REPElectionSiteInformationModel;
import com.model.REPMediationModel;
import com.model.REPRecommendationModel;
import com.model.REPCaseModel;
import com.model.activityModel;
import com.model.boardMeetingModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.employerCaseSearchModel;
import com.model.oldBlobFileModel;
import com.model.oldREPDataModel;
import com.model.oldREPHistoryModel;
import com.model.relatedCaseModel;
import com.model.repCaseSearchModel;
import com.model.startTimeEndTimeModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlBoardActionType;
import com.sql.sqlBoardMeeting;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployerCaseSearchData;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.sql.sqlREPCaseSearch;
import com.sql.sqlREPCaseStatus;
import com.sql.sqlREPCaseType;
import com.sql.sqlREPData;
import com.sql.sqlREPElectionMultiCase;
import com.sql.sqlREPElectionSiteInformation;
import com.sql.sqlREPMediation;
import com.sql.sqlREPRecommendation;
import com.sql.sqlRelatedCase;
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
public class REPMigration {

    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    
    public static void migrateREPData(final MainWindowSceneController control) {
        Thread repThread = new Thread() {
            @Override
            public void run() {
                repThread(control);
            }
        };
        repThread.start();
    }

    private static void repThread(MainWindowSceneController controlPassed) {
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        control.setProgressBarIndeterminate("REP Case Migration");
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        totalRecordCount = 0;
        currentRecord = 0;
        
        List<oldREPDataModel> oldREPDataList = sqlREPData.getCases();
        List<boardAcionTypeModel> REPBoardActionList = sqlBoardActionType.getOldBoardActionType();
        List<REPCaseTypeModel> REPCaseTypeList = sqlREPCaseType.getOldREPCaseType();
        List<REPRecommendationModel> REPrecList = sqlREPRecommendation.getOldREPRecommendation();
        List<REPCaseStatusModel> REPCaseStatusList = sqlREPCaseStatus.getOldREPCaseStatus();
        
        totalRecordCount = oldREPDataList.size() + REPBoardActionList.size() 
                + REPCaseTypeList.size() + REPCaseStatusList.size() + REPrecList.size();

        sqlREPRecommendation.batchAddREPRecommendation(REPrecList);
        currentRecord = SceneUpdater.listItemFinished(control, REPrecList.size(), totalRecordCount, "REP Recommendations Finished");
        
        sqlREPCaseType.batchAddREPCaseType(REPCaseTypeList);
        currentRecord = SceneUpdater.listItemFinished(control, REPCaseTypeList.size(), totalRecordCount, "REP Case Types Finished");
        
        sqlREPCaseStatus.batchAddREPCaseStatus(REPCaseStatusList);
        currentRecord = SceneUpdater.listItemFinished(control, REPCaseStatusList.size(), totalRecordCount, "REP Case Status Finished");
        
        sqlBoardActionType.batchAddREPBoardActionType(REPBoardActionList);
        currentRecord = SceneUpdater.listItemFinished(control, REPBoardActionList.size(), totalRecordCount, "REP Board Actions Finished");
        
        //Insert REP Case Data
        oldREPDataList.stream().forEach(item -> 
                executor.submit(() -> 
                        migrateCase(item)));
        
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        System.out.println("\nFinished all threads");
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating REP Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateREPCases");
        }
    }

    private static void migrateCase(oldREPDataModel item) {
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        
        migratePartyInformation(item, caseNumber);
        migrateCaseData(item, caseNumber);
        migrateBoardMeetings(item, caseNumber);
        migrateMultiCaseElections(item, caseNumber);
        migrateElectionSiteInfo(item, caseNumber);
        migrateMediations(item, caseNumber);
        migrateCaseSearch(item, caseNumber);
        migrateCaseHistory(caseNumber);
        migrateEmployerCaseSearch(item, caseNumber);
        
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseNumber().trim());
    }

    private static void migratePartyInformation(oldREPDataModel item, caseNumberModel caseNumber){
        List<casePartyModel> list = new ArrayList<>();
        
        list = migratePetitioner(item, caseNumber, list);
        list = migratePetitionerRep(item, caseNumber, list);
        list = migrateEmployer(item, caseNumber, list);
        list = migrateEmployerRep(item, caseNumber, list);
        list = migrateEmployeeOrg(item, caseNumber, list);
        list = migrateEmployeeOrgRep(item, caseNumber, list);
        list = migrateRivalEmployeeOrg(item, caseNumber, list);
        list = migrateRivalEmployeeOrgRep(item, caseNumber, list);
        list = migrateRivalEmployeeOrg2(item, caseNumber, list);
        list = migrateRivalEmployeeOrg2Rep(item, caseNumber, list);
        list = migrateRivalEmployeeOrg3(item, caseNumber, list);
        list = migrateRivalEmployeeOrg3Rep(item, caseNumber, list);
        list = migrateIncumbentEmployeeOrg(item, caseNumber, list);
        list = migrateIncumbentEmployeeOrgRep(item, caseNumber, list);
        list = migrateIntervener(item, caseNumber, list);
        list = migrateIntervenerRep(item, caseNumber, list);
        list = migrateConversionSchool(item, caseNumber, list);
        list = migrateConversionSchoolRep(item, caseNumber, list);
        
        sqlCaseParty.batchAddPartyInformation(list);
    }
    
    private static List<casePartyModel> migratePetitioner(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Petitioner");
        party.setLastName(!"".equals(item.getPName().trim()) ? item.getPName().trim() : null);
        party.setAddress1(!"".equals(item.getPAddress1().trim()) ? item.getPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getPAddress2().trim()) ? item.getPAddress2().trim() : null);
        party.setCity(!"".equals(item.getPCity().trim()) ? item.getPCity().trim() : null);
        party.setState(!"".equals(item.getPState().trim()) ? item.getPState().trim() : null);
        party.setZip(!"".equals(item.getPZip().trim()) ? item.getPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getPEmail().trim()) ? item.getPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getPAsstName() == null) ? "" : item.getPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getPAsstEmail().trim()) ? item.getPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migratePetitionerRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Petitioner REP");
        party.setLastName(!"".equals(item.getPREPName().trim()) ? item.getPREPName().trim() : null);
        party.setAddress1(!"".equals(item.getPREPAddress1().trim()) ? item.getPREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getPREPAddress2().trim()) ? item.getPREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getPREPCity().trim()) ? item.getPREPCity().trim() : null);
        party.setState(!"".equals(item.getPREPState().trim()) ? item.getPREPState().trim() : null);
        party.setZip(!"".equals(item.getPREPZip().trim()) ? item.getPREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getPREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getPREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getPREPEmail().trim()) ? item.getPREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getPREPAsstName() == null) ? "" : item.getPREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getPREPAsstEmail().trim()) ? item.getPREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateEmployer(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employer");
        party.setLastName(!"".equals(item.getEName().trim()) ? item.getEName().trim() : null);
        party.setAddress1(!"".equals(item.getEAddress1().trim()) ? item.getEAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEAddress2().trim()) ? item.getEAddress2().trim() : null);
        party.setCity(!"".equals(item.getECity().trim()) ? item.getECity().trim() : null);
        party.setState(!"".equals(item.getEState().trim()) ? item.getEState().trim() : null);
        party.setZip(!"".equals(item.getEZip().trim()) ? item.getEZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEEmail().trim()) ? item.getEEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getEAsstName() == null) ? "" : item.getEAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEAsstEmail().trim()) ? item.getEAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateEmployerRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employer REP");
        party.setLastName(!"".equals(item.getEREPName().trim()) ? item.getEREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEREPAddress1().trim()) ? item.getEREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEREPAddress2().trim()) ? item.getEREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEREPCity().trim()) ? item.getEREPCity().trim() : null);
        party.setState(!"".equals(item.getEREPState().trim()) ? item.getEREPState().trim() : null);
        party.setZip(!"".equals(item.getEREPZip().trim()) ? item.getEREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEREPEmail().trim()) ? item.getEREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getEREPAsstName() == null) ? "" : item.getEREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEREPAsstEmail().trim()) ? item.getEREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employee Organization");
        party.setLastName(!"".equals(item.getEOName().trim()) ? item.getEOName().trim() : null);
        party.setAddress1(!"".equals(item.getEOAddress1().trim()) ? item.getEOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEOAddress2().trim()) ? item.getEOAddress2().trim() : null);
        party.setCity(!"".equals(item.getEOCity().trim()) ? item.getEOCity().trim() : null);
        party.setState(!"".equals(item.getEOState().trim()) ? item.getEOState().trim() : null);
        party.setZip(!"".equals(item.getEOZip().trim()) ? item.getEOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEOPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEOPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEOEmail().trim()) ? item.getEOEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getEOAsstName() == null) ? "" : item.getEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEOAsstEmail().trim()) ? item.getEOAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Employee Organization REP");
        party.setLastName(!"".equals(item.getEOREPName().trim()) ? item.getEOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEOREPAddress1().trim()) ? item.getEOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEOREPAddress2().trim()) ? item.getEOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEOREPCity().trim()) ? item.getEOREPCity().trim() : null);
        party.setState(!"".equals(item.getEOREPState().trim()) ? item.getEOREPState().trim() : null);
        party.setZip(!"".equals(item.getEOREPZip().trim()) ? item.getEOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEOREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getEOREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getEOREPEmail().trim()) ? item.getEOREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getEOREPAsstName() == null) ? "" : item.getEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEOREPAsstEmail().trim()) ? item.getEOREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization");
        party.setLastName(!"".equals(item.getREOName().trim()) ? item.getREOName().trim() : null);
        party.setAddress1(!"".equals(item.getREOAddress1().trim()) ? item.getREOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREOAddress2().trim()) ? item.getREOAddress2().trim() : null);
        party.setCity(!"".equals(item.getREOCity().trim()) ? item.getREOCity().trim() : null);
        party.setState(!"".equals(item.getREOState().trim()) ? item.getREOState().trim() : null);
        party.setZip(!"".equals(item.getREOZip().trim()) ? item.getREOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREOPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREOPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREOEmail().trim()) ? item.getREOEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREOAsstName() == null) ? "" : item.getREOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREOAsstEmail().trim()) ? item.getREOAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization REP");
        party.setLastName(!"".equals(item.getREOREPName().trim()) ? item.getREOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getREOREPAddress1().trim()) ? item.getREOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREOREPAddress2().trim()) ? item.getREOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREOREPCity().trim()) ? item.getREOREPCity().trim() : null);
        party.setState(!"".equals(item.getREOREPState().trim()) ? item.getREOREPState().trim() : null);
        party.setZip(!"".equals(item.getREOREPZip().trim()) ? item.getREOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREOREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREOREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREOREPEmail().trim()) ? item.getREOREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREOREPAsstName() == null) ? "" : item.getREOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREOREPAsstEmail().trim()) ? item.getREOREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrg2(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization 2");
        party.setLastName(!"".equals(item.getREO2Name().trim()) ? item.getREO2Name().trim() : null);
        party.setAddress1(!"".equals(item.getREO2Address1().trim()) ? item.getREO2Address1().trim() : null);
        party.setAddress2(!"".equals(item.getREO2Address2().trim()) ? item.getREO2Address2().trim() : null);
        party.setCity(!"".equals(item.getREO2City().trim()) ? item.getREO2City().trim() : null);
        party.setState(!"".equals(item.getREO2State().trim()) ? item.getREO2State().trim() : null);
        party.setZip(!"".equals(item.getREO2Zip().trim()) ? item.getREO2Zip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO2Phone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREO2Phone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREO2Email().trim()) ? item.getREO2Email().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREO2AsstName() == null) ? "" : item.getREO2AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization 2 Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO2AsstEmail().trim()) ? item.getREO2AsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrg2Rep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization 2 REP");
        party.setLastName(!"".equals(item.getREO2REPName().trim()) ? item.getREO2REPName().trim() : null);
        party.setAddress1(!"".equals(item.getREO2REPAddress1().trim()) ? item.getREO2REPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREO2REPAddress2().trim()) ? item.getREO2REPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREO2REPCity().trim()) ? item.getREO2REPCity().trim() : null);
        party.setState(!"".equals(item.getREO2REPState().trim()) ? item.getREO2REPState().trim() : null);
        party.setZip(!"".equals(item.getREO2REPZip().trim()) ? item.getREO2REPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO2REPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREO2REPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREO2REPEmail().trim()) ? item.getREO2REPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREO2REPAsstName() == null) ? "" : item.getREO2REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization 2 REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO2REPAsstEmail().trim()) ? item.getREO2REPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrg3(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization 3");
        party.setLastName(!"".equals(item.getREO3Name().trim()) ? item.getREO3Name().trim() : null);
        party.setAddress1(!"".equals(item.getREO3Address1().trim()) ? item.getREO3Address1().trim() : null);
        party.setAddress2(!"".equals(item.getREO3Address2().trim()) ? item.getREO3Address2().trim() : null);
        party.setCity(!"".equals(item.getREO3City().trim()) ? item.getREO3City().trim() : null);
        party.setState(!"".equals(item.getREO3State().trim()) ? item.getREO3State().trim() : null);
        party.setZip(!"".equals(item.getREO3Zip().trim()) ? item.getREO3Zip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO3Phone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREO3Phone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREO3Email().trim()) ? item.getREO3Email().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREO3AsstName() == null) ? "" : item.getREO3AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization 3 Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO3AsstEmail().trim()) ? item.getREO3AsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateRivalEmployeeOrg3Rep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Rival Employee Organization 3 REP");
        party.setLastName(!"".equals(item.getREO3REPName().trim()) ? item.getREO3REPName().trim() : null);
        party.setAddress1(!"".equals(item.getREO3REPAddress1().trim()) ? item.getREO3REPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREO3REPAddress2().trim()) ? item.getREO3REPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREO3REPCity().trim()) ? item.getREO3REPCity().trim() : null);
        party.setState(!"".equals(item.getREO3REPState().trim()) ? item.getREO3REPState().trim() : null);
        party.setZip(!"".equals(item.getREO3REPZip().trim()) ? item.getREO3REPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO3REPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getREO3REPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getREO3REPEmail().trim()) ? item.getREO3REPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getREO3REPAsstName() == null) ? "" : item.getREO3REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization 3 REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO3REPAsstEmail().trim()) ? item.getREO3REPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateIncumbentEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Incumbent Employee Organization");
        party.setLastName(!"".equals(item.getIEOName().trim()) ? item.getIEOName().trim() : null);
        party.setAddress1(!"".equals(item.getIEOAddress1().trim()) ? item.getIEOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIEOAddress2().trim()) ? item.getIEOAddress2().trim() : null);
        party.setCity(!"".equals(item.getIEOCity().trim()) ? item.getIEOCity().trim() : null);
        party.setState(!"".equals(item.getIEOState().trim()) ? item.getIEOState().trim() : null);
        party.setZip(!"".equals(item.getIEOZip().trim()) ? item.getIEOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIEOPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getIEOPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getIEOEmail().trim()) ? item.getIEOEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getIEOAsstName() == null) ? "" : item.getIEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIEOAsstEmail().trim()) ? item.getIEOAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateIncumbentEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Incumbent Employee Organization REP");
        party.setLastName(!"".equals(item.getIEOREPName().trim()) ? item.getIEOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getIEOREPAddress1().trim()) ? item.getIEOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIEOREPAddress2().trim()) ? item.getIEOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getIEOREPCity().trim()) ? item.getIEOREPCity().trim() : null);
        party.setState(!"".equals(item.getIEOREPState().trim()) ? item.getIEOREPState().trim() : null);
        party.setZip(!"".equals(item.getIEOREPZip().trim()) ? item.getIEOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIEOREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getIEOREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getIEOREPEmail().trim()) ? item.getIEOREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getIEOREPAsstName() == null) ? "" : item.getIEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIEOREPAsstEmail().trim()) ? item.getIEOREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateIntervener(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Intervener");
        party.setLastName(!"".equals(item.getIName().trim()) ? item.getIName().trim() : null);
        party.setAddress1(!"".equals(item.getIAddress1().trim()) ? item.getIAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIAddress2().trim()) ? item.getIAddress2().trim() : null);
        party.setCity(!"".equals(item.getICity().trim()) ? item.getICity().trim() : null);
        party.setState(!"".equals(item.getIState().trim()) ? item.getIState().trim() : null);
        party.setZip(!"".equals(item.getIZip().trim()) ? item.getIZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getIPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getIEmail().trim()) ? item.getIEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getIAsstName() == null) ? "" : item.getIAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIAsstEmail().trim()) ? item.getIAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateIntervenerRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Intervener REP");
        party.setLastName(!"".equals(item.getIREPName().trim()) ? item.getIREPName().trim() : null);
        party.setAddress1(!"".equals(item.getIREPAddress1().trim()) ? item.getIREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIREPAddress2().trim()) ? item.getIREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getIREPCity().trim()) ? item.getIREPCity().trim() : null);
        party.setState(!"".equals(item.getIREPState().trim()) ? item.getIREPState().trim() : null);
        party.setZip(!"".equals(item.getIREPZip().trim()) ? item.getIREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getIREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getIREPEmail().trim()) ? item.getIREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getIREPAsstName() == null) ? "" : item.getIREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIREPAsstEmail().trim()) ? item.getIREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateConversionSchool(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Conversion School");
        party.setLastName(!"".equals(item.getCSName().trim()) ? item.getCSName().trim() : null);
        party.setAddress1(!"".equals(item.getCSAddress1().trim()) ? item.getCSAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCSAddress2().trim()) ? item.getCSAddress2().trim() : null);
        party.setCity(!"".equals(item.getCSCity().trim()) ? item.getCSCity().trim() : null);
        party.setState(!"".equals(item.getCSState().trim()) ? item.getCSState().trim() : null);
        party.setZip(!"".equals(item.getCSZip().trim()) ? item.getCSZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCSPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCSPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCSEmail().trim()) ? item.getCSEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);
            
            String asstName = ((item.getCSAsstName() == null) ? "" : item.getCSAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getCSAsstEmail().trim()) ? item.getCSAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static List<casePartyModel> migrateConversionSchoolRep(oldREPDataModel item, caseNumberModel caseNumber, List<casePartyModel> list) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Conversion School REP");
        party.setLastName(!"".equals(item.getCSREPName().trim()) ? item.getCSREPName().trim() : null);
        party.setAddress1(!"".equals(item.getCSREPAddress1().trim()) ? item.getCSREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCSREPAddress2().trim()) ? item.getCSREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getCSREPCity().trim()) ? item.getCSREPCity().trim() : null);
        party.setState(!"".equals(item.getCSREPState().trim()) ? item.getCSREPState().trim() : null);
        party.setZip(!"".equals(item.getCSREPZip().trim()) ? item.getCSREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCSREPPhone().trim()) ? StringUtilities.convertPhoneNumberToString(item.getCSREPPhone().trim()) : null);
        party.setEmailAddress(!"".equals(item.getCSREPEmail().trim()) ? item.getCSREPEmail().trim() : null);
        party.setFax(null);

        if (party.getLastName() != null || party.getAddress1() != null || party.getEmailAddress() != null || party.getPhoneOne() != null) {
            list.add(party);

            String asstName = ((item.getCSREPAsstName() == null) ? "" : item.getCSREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getCSREPAsstEmail().trim()) ? item.getCSREPAsstEmail().trim() : null);
                list.add(party);
            }
        }
        return list;
    }

    private static void migrateCaseData(oldREPDataModel item, caseNumberModel caseNumber) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(StringUtilities.generateFullCaseNumber(caseNumber));
        List<casePartyModel> casePartyList = sqlCaseParty.getCasePartyFromParties(
                caseNumber.getCaseYear(), caseNumber.getCaseType(), caseNumber.getCaseMonth(), caseNumber.getCaseNumber());
        
        REPCaseModel rep = new REPCaseModel();
        
        rep.setActive(item.getActive());
        rep.setCaseYear(caseNumber.getCaseYear());
        rep.setCaseType(caseNumber.getCaseType());
        rep.setCaseMonth(caseNumber.getCaseMonth());
        rep.setCaseNumber(caseNumber.getCaseNumber());
        rep.setType(!"".equals(item.getCaseType().trim()) ? item.getCaseType().trim() : null);
        rep.setStatus1(!"".equals(item.getStatus1().trim()) ? item.getStatus1().trim() : null);
        rep.setStatus2(!"".equals(item.getStatus2().trim()) ? item.getStatus2().trim() : null);
        rep.setCurrentOwnerID(StringUtilities.convertUserToID(item.getCurrentOwner()));
        rep.setCounty(!"".equals(item.getCounty().trim()) ? item.getCounty().trim() : null);
        rep.setEmployerIDNumber(!"".equals(item.getEmployerIDNum().trim()) ? item.getEmployerIDNum().trim() : null);
        rep.setDeptInState(!"".equals(item.getDeptInState().trim()) ? item.getDeptInState().trim() : null);
        rep.setBargainingUnitNumber(!"".equals(item.getBargainingUnitNum().trim()) ? item.getBargainingUnitNum().trim() : null);
        rep.setBoardCertified("1".equals(item.getBoardCertified().trim()) ? 1 : 0);
        rep.setDeemedCertified("1".equals(item.getDeemedCertified().trim()) ? 1 : 0);
        rep.setCertificationRevoked("1".equals(item.getCertRevoked().trim()) ? 1 : 0);
        rep.setFileDate(!"".equals(item.getFileDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getFileDate())) : null);    
        rep.setAmendedFilingDate(!"".equals(item.getAmendedFilingDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getAmendedFilingDate())) : null);
        rep.setFinalBoardDate(!"".equals(item.getFinalBoardDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getFinalBoardDate())) : null);      
        rep.setRegistrationLetterSent(!"".equals(item.getRegLetterSentDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getRegLetterSentDate())) : null);      
        rep.setDateOfAppeal(!"".equals(item.getDateOfAppeal().trim()) ? (StringUtilities.convertStringSQLDate(item.getDateOfAppeal())) : null);        
        rep.setCourtClosedDate(!"".equals(item.getCourtClosedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getCourtClosedDate())) : null);     
        rep.setReturnSOIDueDate(!"".equals(item.getReturnSOIDueDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getReturnSOIDueDate())) : null);    
        rep.setActualSOIReturnDate(!"".equals(item.getActualSOIReturnDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getActualSOIReturnDate())) : null);     
        rep.setSOIReturnIntials(StringUtilities.convertUserToID(item.getSOIReturnInitials1()));
        rep.setREPClosedCaseDueDate(!"".equals(item.getREPClosedCaseDueDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getREPClosedCaseDueDate())) : null);
        rep.setActualREPClosedDate(!"".equals(item.getActualREPClosedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getActualREPClosedDate())) : null);
        rep.setREPClosedUser(StringUtilities.convertUserToID(item.getSOIReturnInitials2()));
        rep.setActualClerksClosedDate(!"".equals(item.getActualClerksClosed().trim()) ? (StringUtilities.convertStringSQLDate(item.getActualClerksClosed())) : null);
        rep.setClerksClosedUser(StringUtilities.convertUserToID(item.getClearksClosedInitials()));
        rep.setNote(null);
        rep.setAlphaListDate(null);
        rep.setFileBy(!"".equals(item.getCaseFiledBy().trim()) ? item.getCaseFiledBy().trim() : null);
        rep.setProfessionalNonProfessional("1".equals(item.getPNonP().trim()) ? 1 : 0);
        rep.setToReflect(!"".equals(item.getToReflect().trim()) ? item.getToReflect().trim() : null);
        rep.setTypeFiledBy(!"".equals(item.getTypeFiledBy().trim()) ? item.getTypeFiledBy().trim() : null);
        rep.setTypeFiledVia(!"".equals(item.getTypeFiledHow().trim()) ? item.getTypeFiledHow().trim() : null);
        rep.setPositionStatementFiledBy(!"".equals(item.getPSFiledBy().trim()) ? item.getPSFiledBy().trim() : null);
        rep.setEEONameChangeFrom(!"".equals(item.getEEONameFrom().trim()) ? item.getEEONameFrom().trim() : null);
        rep.setEEONameChangeTo(!"".equals(item.getEEONameTo().trim()) ? item.getEEONameTo().trim() : null);
        rep.setERNameChangeFrom(!"".equals(item.getERNameFrom().trim()) ? item.getERNameFrom().trim() : null);
        rep.setERNameChangeTo(!"".equals(item.getERNameTo().trim()) ? item.getERNameTo().trim() : null);
        rep.setBoardActionType(!"".equals(item.getBoardActionType().trim()) ? item.getBoardActionType().trim() : null);
        rep.setBoardActionDate(!"".equals(item.getBoardActionDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getBoardActionDate())) : null);
        rep.setHearingPersonID(StringUtilities.convertUserToID(item.getBoardPersonAssigned()));
        rep.setMulticaseElection("1".equals(item.getMultiCaseElection().trim()) ? 1 : 0);
        rep.setElectionType1(!"".equals(item.getElectionType1().trim()) ? item.getElectionType1().trim() : null);
        rep.setElectionType2(!"".equals(item.getElectionType2().trim()) ? item.getElectionType2().trim() : null);
        rep.setElectionType3(!"".equals(item.getElectionType3().trim()) ? item.getElectionType3().trim() : null);
        rep.setEligibilityDate(StringUtilities.convertStringTimeStamp(item.getEligibilityDate()) != null ? (StringUtilities.convertStringSQLDate(item.getEligibilityDate())) : null);
        rep.setBallotOne(!"".equals(item.getBallotOne().trim()) ? item.getBallotOne().trim() : null);
        rep.setBallotTwo(!"".equals(item.getBallotTwo().trim()) ? item.getBallotTwo().trim() : null);
        rep.setBallotThree(!"".equals(item.getBallotThree().trim()) ? item.getBallotThree().trim() : null);
        rep.setBallotFour(!"".equals(item.getBallotFour().trim()) ? item.getBallotFour().trim() : null);
        rep.setMailKitDate(StringUtilities.convertStringTimeStamp(item.getMailKitDate()) != null  ? (StringUtilities.convertStringSQLDate(item.getMailKitDate())) : null);
        rep.setPollingStartDate(StringUtilities.convertStringTimeStamp(item.getPollingStartDate()) != null  ? (StringUtilities.convertStringSQLDate(item.getPollingStartDate())) : null);
        rep.setPollingEndDate(StringUtilities.convertStringTimeStamp(item.getPollingEndDate()) != null  ? (StringUtilities.convertStringSQLDate(item.getPollingEndDate())) : null);
        rep.setBallotsCountDay(!"".equals(item.getBallotsCountDay().trim()) ? item.getBallotsCountDay().trim() : null);
        rep.setBallotsCountDate(StringUtilities.convertStringTimeStamp(item.getBallotsCountDate()) != null  ? (StringUtilities.convertStringSQLDate(item.getBallotsCountDate())) : null);
        rep.setBallotsCountTime(!"".equals(item.getBallotsCountTime().trim()) ? StringUtilities.convertStringTimeStamp(item.getBallotsCountTime()) : null);
        rep.setEligibilityListDate(StringUtilities.convertStringTimeStamp(item.getEligibilityListDate()) != null  ? (StringUtilities.convertStringSQLDate(item.getEligibilityListDate())) : null);
        rep.setPreElectionConfDate(StringUtilities.convertStringTimeStamp(item.getPreElectionConfDate()) != null ? (StringUtilities.convertStringSQLDate(item.getPreElectionConfDate())) : null);
        rep.setSelfReleasing(!"".equals(item.getSelfReleasing().trim()) ? item.getSelfReleasing().trim() : null);
        rep.setResultApproxNumberEligibleVoters(!"".equals(item.getResultsApproxNumEligible().trim()) ? StringUtilities.parseStringtoInt(item.getResultsApproxNumEligible().trim()) : -1);
        rep.setResultVoidBallots(!"".equals(item.getResultsVoidBallots().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVoidBallots().trim()) : -1);
        rep.setResultVotesCastForEEO(!"".equals(item.getResultsVoidBallots().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVoidBallots().trim()) : -1);
        rep.setResultVotesCastForIncumbentEEO(!"".equals(item.getResultsVotesForIncumbent().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVotesForIncumbent().trim()) : -1);
        rep.setResultVotesCastForRivalEEO1(!"".equals(item.getResultsVotesForRival().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVotesForRival().trim()) : -1);
        rep.setResultVotesCastForRivalEEO2(!"".equals(item.getResultsVotesForRival2().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVotesForRival2().trim()) : -1);
        rep.setResultVotesCastForRivalEEO3(!"".equals(item.getResultsVotesForRival3().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVotesForRival3().trim()) : -1);
        rep.setResultVotesCastForNoRepresentative(!"".equals(item.getResultsVotesForNoREP().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVotesForNoREP().trim()) : -1);
        rep.setResultValidVotesCounted(!"".equals(item.getResultsVaildCounted().trim()) ? StringUtilities.parseStringtoInt(item.getResultsVaildCounted().trim()) : -1);
        rep.setResultChallengedBallots(!"".equals(item.getResultsChallenged().trim()) ? StringUtilities.parseStringtoInt(item.getResultsChallenged().trim()) : -1);
        rep.setResultTotalBallotsCast(!"".equals(item.getResultsTotalVotesCast().trim()) ? StringUtilities.parseStringtoInt(item.getResultsTotalVotesCast().trim()) : -1);
        rep.setProfessionalApproxNumberEligible(!"".equals(item.getPApproxNumEligible().trim()) ? StringUtilities.parseStringtoInt(item.getPApproxNumEligible().trim()) : -1);
        rep.setProfessionalYES(!"".equals(item.getPYES().trim()) ? StringUtilities.parseStringtoInt(item.getPYES().trim()) : -1);
        rep.setProfessionalNO(!"".equals(item.getPNO().trim()) ? StringUtilities.parseStringtoInt(item.getPNO().trim()) : -1);
        rep.setProfessionalChallenged(!"".equals(item.getPChallenged().trim()) ? StringUtilities.parseStringtoInt(item.getPChallenged().trim()) : -1);
        rep.setProfessionalTotalVotes(!"".equals(item.getPTotalVotes().trim()) ? StringUtilities.parseStringtoInt(item.getPTotalVotes().trim()) : -1);
        rep.setProfessionalOutcome(!"".equals(item.getPOutcome().trim()) ? item.getPOutcome().trim() : null);
        rep.setProfessionalVoidBallots(!"".equals(item.getPVoidBallots().trim()) ? StringUtilities.parseStringtoInt(item.getPVoidBallots().trim()) : -1);
        rep.setProfessionalValidVotes(!"".equals(item.getPValidVotes().trim()) ? StringUtilities.parseStringtoInt(item.getPValidVotes().trim()) : -1);
        rep.setProfessionalVotesCastForNoRepresentative(!"".equals(item.getPVotesforNoREP().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesforNoREP().trim()) : -1);
        rep.setProfessionalVotesCastForEEO(!"".equals(item.getPVotesforEEO().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesforEEO().trim()) : -1);
        rep.setProfessionalVotesCstForIncumbentEEO(!"".equals(item.getPVotesforIncumbentEEO().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesforIncumbentEEO().trim()) : -1);
        rep.setProfessionalVotesCastForRivalEEO1(!"".equals(item.getPVotesCastforRivalEEO1().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesCastforRivalEEO1().trim()) : -1);
        rep.setProfessionalVotesCastForRivalEEO2(!"".equals(item.getPVotesCastforRivalEEO2().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesCastforRivalEEO2().trim()) : -1);
        rep.setProfessionalVotesCastForRivalEEO3(!"".equals(item.getPVotesCastforRivalEEO3().trim()) ? StringUtilities.parseStringtoInt(item.getPVotesCastforRivalEEO3().trim()) : -1);
        rep.setNonprofessionalApproxNumberEligible(!"".equals(item.getNPApproxNumEligible().trim()) ? StringUtilities.parseStringtoInt(item.getNPApproxNumEligible().trim()) : -1);
        rep.setNonprofessionalYES(!"".equals(item.getNPYES().trim()) ? StringUtilities.parseStringtoInt(item.getNPYES().trim()) : -1);
        rep.setNonprofessionalNO(!"".equals(item.getNPNO().trim()) ? StringUtilities.parseStringtoInt(item.getNPNO().trim()) : -1);
        rep.setNonprofessionalChallenged(!"".equals(item.getNPChallenged().trim()) ? StringUtilities.parseStringtoInt(item.getNPChallenged().trim()) : -1);
        rep.setNonprofessionalTotalVotes(!"".equals(item.getNPTotalVotes().trim()) ? StringUtilities.parseStringtoInt(item.getNPTotalVotes().trim()) : -1);
        rep.setNonprofessionalOutcome(!"".equals(item.getNPOutcome().trim()) ? item.getNPOutcome().trim() : null);
        rep.setNonprofessionalVoidBallots(!"".equals(item.getNPVoidBallots().trim()) ? StringUtilities.parseStringtoInt(item.getNPVoidBallots().trim()) : -1);
        rep.setNonprofessionalValidVotes(!"".equals(item.getNPValidVotes().trim()) ? StringUtilities.parseStringtoInt(item.getNPValidVotes().trim()) : -1);
        rep.setNonprofessionalVotesCastForNoRepresentative(!"".equals(item.getNPVotesforNoREP().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesforNoREP().trim()) : -1);
        rep.setNonprofessionalVotesCastForEEO(!"".equals(item.getNPVotesforEEO().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesforEEO().trim()) : -1);
        rep.setNonprofessionalVotesCastForIncumbentEEO(!"".equals(item.getNPVotesforIncumbentEEO().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesforIncumbentEEO().trim()) : -1);
        rep.setNonprofessionalVotesCastForRivalEEO1(!"".equals(item.getNPVotesCastforRivalEEO1().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesCastforRivalEEO1().trim()) : -1);
        rep.setNonprofessionalVotesCastForRivalEEO2(!"".equals(item.getNPVotesCastforRivalEEO2().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesCastforRivalEEO2().trim()) : -1);
        rep.setNonprofessionalVotesCastForRivalEEO3(!"".equals(item.getNPVotesCastforRivalEEO3().trim()) ? StringUtilities.parseStringtoInt(item.getNPVotesCastforRivalEEO3().trim()) : -1);
        rep.setCombinedApproxNumberEligible(!"".equals(item.getCApproxNumEligible().trim()) ? StringUtilities.parseStringtoInt(item.getCApproxNumEligible().trim()) : -1);
        rep.setCombinedYES(!"".equals(item.getCYes().trim()) ? StringUtilities.parseStringtoInt(item.getCYes().trim()) : -1);
        rep.setCombinedNO(!"".equals(item.getCNo().trim()) ? StringUtilities.parseStringtoInt(item.getCNo().trim()) : -1);
        rep.setCombinedChallenged(!"".equals(item.getCChallenged().trim()) ? StringUtilities.parseStringtoInt(item.getCChallenged().trim()) : -1);
        rep.setCombinedTotalVotes(!"".equals(item.getCTotalVotes().trim()) ? StringUtilities.parseStringtoInt(item.getCTotalVotes().trim()) : -1);
        rep.setCombinedOutcome(!"".equals(item.getCOutcome().trim()) ? item.getCOutcome().trim() : null);
        rep.setCombinedVoidBallots(!"".equals(item.getCVoidBallots().trim()) ? StringUtilities.parseStringtoInt(item.getCVoidBallots().trim()) : -1);
        rep.setCombinedValidVotes(!"".equals(item.getCVaildVotes().trim()) ? StringUtilities.parseStringtoInt(item.getCVaildVotes().trim()) : -1);
        rep.setCombinedVotesCastForNoRepresentative(!"".equals(item.getCVotesForNoREP().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesForNoREP().trim()) : -1);
        rep.setCombinedVotesCastForEEO(!"".equals(item.getCVotesforEEO().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesforEEO().trim()) : -1);
        rep.setCombinedVotesCastForIncumbentEEO(!"".equals(item.getCVotesForIncumbentEEO().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesForIncumbentEEO().trim()) : -1);
        rep.setCombinedVotesCastForRivalEEO1(!"".equals(item.getCVotesCastforRivalEEO1().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesCastforRivalEEO1().trim()) : -1);
        rep.setCombinedVotesCastForRivalEEO2(!"".equals(item.getCVotesCastforRivalEEO2().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesCastforRivalEEO2().trim()) : -1);
        rep.setCombinedVotesCastForRivalEEO3(!"".equals(item.getCVotesCastforRivalEEO3().trim()) ? StringUtilities.parseStringtoInt(item.getCVotesCastforRivalEEO3().trim()) : -1);
        rep.setResultWhoPrevailed(-1);
        rep.setProfessionalWhoPrevailed(-1);
        rep.setNonprofessionalWhoPrevailed(-1);
        rep.setCombinedWhoPrevailed(-1);
                
        for (casePartyModel party : casePartyList){
            if (item.getResultsWhoPrevailed().trim().equalsIgnoreCase(StringUtilities.joinNameTogether(party))){
                rep.setResultWhoPrevailed(party.getId());
            }
            if (item.getPWhoPrevailed().trim().equalsIgnoreCase(StringUtilities.joinNameTogether(party))){
                rep.setProfessionalWhoPrevailed(party.getId());
            }
            if (item.getNPWhoPrevailed().trim().equalsIgnoreCase(StringUtilities.joinNameTogether(party))){
                rep.setNonprofessionalWhoPrevailed(party.getId());
            }
            if (item.getCWhoPrevailed().trim().equalsIgnoreCase(StringUtilities.joinNameTogether(party))){
                rep.setCombinedWhoPrevailed(party.getId());
            }
        }
        
        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "Notes":
                    String note = StringUtilities.convertBlobFileToString(blob.getBlobData());
                    if (rep.getSOIReturnIntials() == 0 && !"".equals(item.getSOIReturnInitials1().trim()) && note != null) {
                        rep.setNote(item.getSOIReturnInitials1() + System.lineSeparator() + System.lineSeparator() + note);
                    } else {
                        rep.setNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    }
                    break;
                case "BUIN":
                    rep.setBargainingUnitIncluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "BUEX":
                    rep.setBargainingUnitExcluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "OIN":
                    rep.setOptInIncluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "PIN":
                    rep.setProfessionalIncluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "PEX":
                    rep.setProfessionalExcluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "NPIN":
                    rep.setNonProfessionalIncluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "NPEX":
                    rep.setNonProfessionalExcluded(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "BoardNotes":
                    rep.setBoardStatusNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;    
                case "Blurb":
                    rep.setBoardStatusBlurb(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Related":
                    migrateRelatedCases(caseNumber, StringUtilities.convertBlobFileToString(blob.getBlobData()));
                default:
                    break;
            }
        }
        sqlREPData.importOldREPCase(rep);
    }

    private static void migrateRelatedCases(caseNumberModel caseNumber, String relatedCaseNumbers){
        if (relatedCaseNumbers != null) {
            List<relatedCaseModel> list = new ArrayList<>();
            relatedCaseModel relatedCase = new relatedCaseModel();

            relatedCase.setCaseYear(caseNumber.getCaseYear());
            relatedCase.setCaseType(caseNumber.getCaseType());
            relatedCase.setCaseMonth(caseNumber.getCaseMonth());
            relatedCase.setCaseNumber(caseNumber.getCaseNumber());

            String[] caseNumberArray = relatedCaseNumbers.split("[" + System.lineSeparator() + "\\,]");

            for (String casenumber : caseNumberArray) {
                if (!"".equals(casenumber.trim())) {
                    relatedCase.setRelatedCaseNumber(casenumber.trim());
                    list.add(relatedCase);
                }
            }
            
            if (list != null ) {
                sqlRelatedCase.batchAddRelatedCase(list);
            }
        }
    }
    
    private static void migrateMediations(oldREPDataModel item, caseNumberModel caseNumber) {
        List<REPMediationModel> list = new ArrayList<>();
        REPMediationModel mediation = new REPMediationModel();
        
        mediation.setCaseYear(caseNumber.getCaseYear());
        mediation.setCaseType(caseNumber.getCaseType());
        mediation.setCaseMonth(caseNumber.getCaseMonth());
        mediation.setCaseNumber(caseNumber.getCaseNumber());
        
        //Internal Mediation
        if(!"".equals(item.getInternalMediationDate()) || !"".equals(item.getInternalMediationTime()) ||
            !"".equals(item.getInternalMediator()) || !"".equals(item.getInternaResult())) {
            mediation.setMediationDate(null);
            mediation.setMediationEntryDate(null);
            if (!"".equals(item.getInternalMediationDate().trim())){
                mediation.setMediationDate(StringUtilities.convertStringDateAndTime(item.getInternalMediationDate(), item.getInternalMediationTime()));
            }
            mediation.setMediationType("Internal Mediation");
            mediation.setMediatorID(String.valueOf(StringUtilities.convertUserToID(item.getInternalMediator())));
            mediation.setMediationOutcome(!"".equals(item.getInternaResult().trim()) ? item.getInternaResult().trim() : null);
            
            list.add(mediation);
        }

        //30 Day Mediation
        if (!"".equals(item.getBoardDirMedDate()) || !"".equals(item.getBoardDirMedMeetingDate()) || !"".equals(item.getBoardDirMedMeetingTime())
                || !"".equals(item.getBoardDirectedMedResult()) || !"".equals(item.getBoardDirectedMediatior())) {                        
            mediation.setMediationDate(null);
            mediation.setMediationEntryDate(!"".equals(item.getBoardDirMedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getBoardDirMedDate())) : null);
            if (!"".equals(item.getBoardDirMedMeetingDate().trim())){
                mediation.setMediationDate(StringUtilities.convertStringDateAndTime(item.getBoardDirMedMeetingDate(), item.getBoardDirMedMeetingTime()));
            }
            mediation.setMediationType("30 Day Mediation");
            mediation.setMediatorID(String.valueOf(StringUtilities.convertUserToID(item.getBoardDirectedMediatior())));
            mediation.setMediationOutcome(!"".equals(item.getBoardDirectedMedResult().trim()) ? item.getBoardDirectedMedResult().trim() : null);
            
            list.add(mediation);
        }

        //Post-Directive Mediation
        if (!"".equals(item.getPostDirMedDate()) || !"".equals(item.getPostDirMedMeetingDate()) || !"".equals(item.getPostDirMedMeetingTime())
                || !"".equals(item.getPostDirMedResult()) || !"".equals(item.getPostDirMediatior())) {
            
            mediation.setMediationDate(null);
            if (!"".equals(item.getPostDirMedMeetingDate().trim())){
                mediation.setMediationDate(StringUtilities.convertStringDateAndTime(item.getPostDirMedMeetingDate(), item.getPostDirMedMeetingTime()));
            }
            mediation.setMediationEntryDate(!"".equals(item.getPostDirMedDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getPostDirMedDate())) : null);
            mediation.setMediationType("Post-Directive Mediation");
            mediation.setMediatorID(String.valueOf(StringUtilities.convertUserToID(item.getPostDirMediatior())));
            mediation.setMediationOutcome(!"".equals(item.getPostDirMedResult().trim()) ? item.getPostDirMedResult().trim() : null);
            
            list.add(mediation);
        }
        
        if (list != null) {
            sqlREPMediation.batchAddREPMediation(list);
        }
    }

    private static void migrateBoardMeetings(oldREPDataModel item, caseNumberModel caseNumber) {
        List<boardMeetingModel> list = new ArrayList<>();
        boardMeetingModel meeting = new boardMeetingModel();
        
        meeting.setCaseYear(caseNumber.getCaseYear());
        meeting.setCaseType(caseNumber.getCaseType());
        meeting.setCaseMonth(caseNumber.getCaseMonth());
        meeting.setCaseNumber(caseNumber.getCaseNumber());
        
        if (!"".equals(item.getBoardMeetingDate1().trim()) || !"".equals(item.getAgendaItem1().trim()) || !"".equals(item.getRecommendation1().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem1().trim()) ? item.getAgendaItem1().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate1()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate1()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation1().trim()) ? item.getRecommendation1().trim() : null);
            meeting.setMemoDate(!"".equals(item.getMemoDate1()) ? StringUtilities.convertStringTimeStamp(item.getMemoDate1()) : null);
            list.add(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate2().trim()) || !"".equals(item.getAgendaItem2().trim()) || !"".equals(item.getRecommendation2().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem2().trim()) ? item.getAgendaItem2().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate2()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate2()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation2().trim()) ? item.getRecommendation2().trim() : null);
            meeting.setMemoDate(!"".equals(item.getMemoDate2()) ? StringUtilities.convertStringTimeStamp(item.getMemoDate2()) : null);
            list.add(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate3().trim()) || !"".equals(item.getAgendaItem3().trim()) || !"".equals(item.getRecommendation3().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem3().trim()) ? item.getAgendaItem3().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate3()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate3()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation3().trim()) ? item.getRecommendation3().trim() : null);
            meeting.setMemoDate(!"".equals(item.getMemoDate3()) ? StringUtilities.convertStringTimeStamp(item.getMemoDate3()) : null);
            list.add(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate4().trim()) || !"".equals(item.getAgendaItem4().trim()) || !"".equals(item.getRecommendation4().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem4().trim()) ? item.getAgendaItem4().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate4()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate4()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation4().trim()) ? item.getRecommendation4().trim() : null);
            meeting.setMemoDate(!"".equals(item.getMemoDate4()) ? StringUtilities.convertStringTimeStamp(item.getMemoDate4()) : null);
            list.add(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate5().trim()) || !"".equals(item.getAgendaItem5().trim()) || !"".equals(item.getRecommendation5().trim())) {
            meeting.setAgendaItemNumber(!"".equals(item.getAgendaItem5().trim()) ? item.getAgendaItem5().trim() : null);
            meeting.setBoardMeetingDate(!"".equals(item.getBoardMeetingDate5()) ? StringUtilities.convertStringTimeStamp(item.getBoardMeetingDate5()) : null);
            meeting.setRecommendation(!"".equals(item.getRecommendation5().trim()) ? item.getRecommendation5().trim() : null);
            meeting.setMemoDate(!"".equals(item.getMemoDate5()) ? StringUtilities.convertStringTimeStamp(item.getMemoDate5()) : null);
            list.add(meeting);
        }
        
        if (list != null) {
            sqlBoardMeeting.batchAddBoardMeeting(list);
        }       
    }
    
    private static void migrateMultiCaseElections(oldREPDataModel item, caseNumberModel caseNumber) {
        List<REPElectionMultiCaseModel> list = new ArrayList<>();
        REPElectionMultiCaseModel multi = new REPElectionMultiCaseModel();
        
        multi.setActive(item.getActive());
        multi.setCaseYear(caseNumber.getCaseYear());
        multi.setCaseType(caseNumber.getCaseType());
        multi.setCaseMonth(caseNumber.getCaseMonth());
        multi.setCaseNumber(caseNumber.getCaseNumber());
        
        if (!"".equals(item.getElectionCaseNumber1().trim())){
            multi.setMultiCase(item.getElectionCaseNumber1().trim());
            list.add(multi);
        }
        if (!"".equals(item.getElectionCaseNumber2().trim())){
            multi.setMultiCase(item.getElectionCaseNumber2().trim());
            list.add(multi);
        }
        if (!"".equals(item.getElectionCaseNumber3().trim())){
            multi.setMultiCase(item.getElectionCaseNumber3().trim());
            list.add(multi);
        }
        if (!"".equals(item.getElectionCaseNumber4().trim())){
            multi.setMultiCase(item.getElectionCaseNumber4().trim());
            list.add(multi);
        }
        if (!"".equals(item.getElectionCaseNumber5().trim())){
            multi.setMultiCase(item.getElectionCaseNumber5().trim());
            list.add(multi);
        }
        if (!"".equals(item.getElectionCaseNumber6().trim())){
            multi.setMultiCase(item.getElectionCaseNumber6().trim());
            list.add(multi);;
        }
        if (list != null) {
            sqlREPElectionMultiCase.batchAddElectionSite(list);
        }
    }
    
    private static void migrateElectionSiteInfo(oldREPDataModel item, caseNumberModel caseNumber) {
        List<REPElectionSiteInformationModel> list = new ArrayList<>();
        REPElectionSiteInformationModel site = new REPElectionSiteInformationModel();
        
        site.setActive(item.getActive());
        site.setCaseYear(caseNumber.getCaseYear());
        site.setCaseType(caseNumber.getCaseType());
        site.setCaseMonth(caseNumber.getCaseMonth());
        site.setCaseNumber(caseNumber.getCaseNumber());
                
        if (!"".equals(item.getSite1Location().trim()) || !"".equals(item.getSite1Place().trim()) || !"".equals(item.getSite1Date().trim())){
            startTimeEndTimeModel time = StringUtilities.splitTime(item.getSite1Time());
            site.setSiteDate(null);
            site.setSiteStartTime(time.getStartTime());
            site.setSiteEndTime(time.getEndTime());
            site.setSitePlace(!"".equals(item.getSite1Place().trim()) ? item.getSite1Place().trim() : null); 
            site.setSiteAddress1(!"".equals(item.getSite1Address1().trim()) ? item.getSite1Address1().trim() : null);
            site.setSiteAddress2(!"".equals(item.getSite1Address2().trim()) ? item.getSite1Address2().trim() : null);
            site.setSiteLocation(!"".equals(item.getSite1Location().trim()) ? item.getSite1Location().trim() : null);
            list.add(site);
        }
        if (!"".equals(item.getSite2Location().trim()) || !"".equals(item.getSite2Place().trim()) || !"".equals(item.getSite2Date().trim())){
            startTimeEndTimeModel time = StringUtilities.splitTime(item.getSite2Time());
            
            site.setSiteDate(null);
            site.setSiteStartTime(time.getStartTime());
            site.setSiteEndTime(time.getEndTime());
            site.setSitePlace(!"".equals(item.getSite2Place().trim()) ? item.getSite2Place().trim() : null); 
            site.setSiteAddress1(!"".equals(item.getSite2Address1().trim()) ? item.getSite2Address1().trim() : null);
            site.setSiteAddress2(!"".equals(item.getSite2Address2().trim()) ? item.getSite2Address2().trim() : null);
            site.setSiteLocation(!"".equals(item.getSite2Location().trim()) ? item.getSite2Location().trim() : null);
            list.add(site);
        }
        if (!"".equals(item.getSite2Location().trim()) || !"".equals(item.getSite2Place().trim()) || !"".equals(item.getSite2Date().trim())){
            startTimeEndTimeModel time = StringUtilities.splitTime(item.getSite3Time());
            site.setSiteDate(null);
            site.setSiteStartTime(time.getStartTime());
            site.setSiteEndTime(time.getEndTime());
            site.setSitePlace(!"".equals(item.getSite2Place().trim()) ? item.getSite2Place().trim() : null); 
            site.setSiteAddress1(!"".equals(item.getSite2Address1().trim()) ? item.getSite2Address1().trim() : null);
            site.setSiteAddress2(!"".equals(item.getSite2Address2().trim()) ? item.getSite2Address2().trim() : null);
            site.setSiteLocation(!"".equals(item.getSite2Location().trim()) ? item.getSite2Location().trim() : null);
            list.add(site);
        }
        if (list != null) {
            sqlREPElectionSiteInformation.batchAddElectionSite(list);
        }
    }
       
    private static void migrateCaseHistory(caseNumberModel caseNumber) {
        List<oldREPHistoryModel> oldREPDataList = sqlActivity.getREPHistoryByCase(StringUtilities.generateFullCaseNumber(caseNumber));
        sqlActivity.batchAddREPActivity(oldREPDataList, caseNumber);
    }
        
    private static void migrateCaseSearch(oldREPDataModel item, caseNumberModel caseNumber) {
        String[] bunnum = item.getBargainingUnitNum().trim().split("-");
        
        List<oldBlobFileModel>oldBlobFileList = sqlBlobFile.getOldBlobDataBUDectioption(bunnum);
        
        repCaseSearchModel search = new repCaseSearchModel();
        search.setCaseYear(caseNumber.getCaseYear());
        search.setCaseType(caseNumber.getCaseType());
        search.setCaseMonth(caseNumber.getCaseMonth());
        search.setCaseNumber(caseNumber.getCaseNumber());
        search.setEmployerName(!"".equals(item.getEName().trim()) ? item.getEName().trim() : null);
        search.setBunNumber(!"".equals(item.getBargainingUnitNum().trim()) ? item.getBargainingUnitNum().trim() : null);
        search.setCounty(!"".equals(item.getCounty().trim()) ? item.getCounty().trim() : null);
        search.setEmployeeOrg(!"".equals(item.getEOName().trim()) ? item.getEOName().trim() : null);
        search.setIncumbent(!"".equals(item.getIEOName().trim()) ? item.getIEOName().trim() : null);
        search.setDescription(null);
        
        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "UnitDesc":
                    search.setDescription(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                default:
                    break;
            }
        }
        
        if (item.getBoardCertified().equals("1")) {
            search.setBoardDeemed("Board");
        } else if (item.getDeemedCertified().equals("1")) {
            search.setBoardDeemed("Deemed");
        } else if (item.getCertRevoked().equals("1")) {
            search.setBoardDeemed("Cert Revoked");
        } else {
            search.setBoardDeemed(null);
        }
        sqlREPCaseSearch.addREPCaseSearchCase(search);
    }
    
    private static void migrateEmployerCaseSearch(oldREPDataModel item, caseNumberModel caseNumber) {
        if (!"".equals(item.getEmployerIDNum().trim())) {
            employerCaseSearchModel search = new employerCaseSearchModel();

            search.setCaseYear(caseNumber.getCaseYear());
            search.setCaseType(caseNumber.getCaseType());
            search.setCaseMonth(caseNumber.getCaseMonth());
            search.setCaseNumber(caseNumber.getCaseNumber());
            search.setCaseStatus(!"".equals(item.getStatus1().trim()) ? item.getStatus1().trim() : null);
            search.setFileDate(!"".equals(item.getFileDate().trim()) ? (StringUtilities.convertStringSQLDate(item.getFileDate())) : null); 
            search.setEmployer(sqlEmployers.getEmployerName(item.getEmployerIDNum().trim()));
            search.setEmployerID(item.getEmployerIDNum().trim());

            sqlEmployerCaseSearchData.addEmployer(search);
        }
    }
}
