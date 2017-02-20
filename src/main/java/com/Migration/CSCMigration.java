/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CSCCaseModel;
import com.model.activityModel;
import com.model.casePartyModel;
import com.model.oldCivilServiceCommissionModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlCSCCase;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class CSCMigration {

    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static List<casePartyModel> casePartyList = new ArrayList<>();
    private static List<CSCCaseModel> cscCaseList = new ArrayList<>();

    public static void migrateCSCData(final MainWindowSceneController control){
        Thread cscThread = new Thread() {
            @Override
            public void run() {
                cscThread(control);
            }
        };
        cscThread.start();
    }

    public static void cscThread(MainWindowSceneController controlPassed){
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("CSC Case Migration");
        totalRecordCount = 0;
        currentRecord = 0;

        List<oldCivilServiceCommissionModel> oldCSCCaseList = sqlCSCCase.getCases();
        if (Global.isDebug()){
            System.out.println("Gathered CSC Cases");
        }
        List<activityModel> oldCSCHistoryList = sqlActivity.getCSCHistory();
        if (Global.isDebug()){
            System.out.println("Gathered CSC History");
        }

        //Insert CSC Case Data
        control.setProgressBarIndeterminateCleaning("CSC Case");
        totalRecordCount = oldCSCCaseList.size();
        oldCSCCaseList.stream().forEach(item ->
                executor.submit(() ->
                        migrateCase(item)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }

        oldCSCCaseList = null;
        currentRecord = 0;
        totalRecordCount = cscCaseList.size() + oldCSCHistoryList.size() + casePartyList.size();

        sqlCaseParty.batchAddPartyInformation(casePartyList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, casePartyList.size(), totalRecordCount, "CSC Parties Finished");

        sqlActivity.batchAddActivity(oldCSCHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldCSCHistoryList.size(), totalRecordCount, "History Finished");

        sqlCSCCase.BatchAddCSCCase(cscCaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldCSCHistoryList.size(), totalRecordCount, "CSC Case Finished");

        casePartyList = null;
        cscCaseList = null;
        oldCSCHistoryList = null;

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating CSC Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateCSCCases");
        }
        SlackNotification.sendBasicNotification(finishedText);
        System.gc();
    }

    private static void migrateCase(oldCivilServiceCommissionModel item) {
        migrateRepresentative(item);
        migrateOfficers(item);
        migrateCaseData(item);
        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getCSCNumber()+ ": " + item.getCSCName());
    }

    private static void migrateRepresentative(oldCivilServiceCommissionModel item) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(null);
        party.setCaseType("CSC");
        party.setCaseMonth(null);
        party.setCaseNumber(String.valueOf(item.getCSCNumber()));
        party.setCaseRelation("Representative");
        party.setFirstName(item.getRepFirstName().trim().equals("") ? null : item.getRepFirstName().trim());
        party.setMiddleInitial(item.getRepMiddleInitial().trim().equals("") ? null : item.getRepMiddleInitial().trim());
        party.setLastName(item.getRepLastName().trim().equals("") ? null : item.getRepLastName().trim());
        party.setJobTitle(item.getRepType().trim().equals("") ? null : item.getRepType().trim());
        party.setAddress1(item.getRepAddress1().trim().equals("") ? null : item.getRepAddress1().trim());
        party.setAddress2(item.getRepAddress2().trim().equals("") ? null : item.getRepAddress2().trim());
        party.setCity(item.getRepCity().trim().equals("") ? null : item.getRepCity().trim());
        party.setState(item.getRepState().trim().equals("") ? null : item.getRepState().trim());
        party.setZip(item.getRepZipPlusFour().trim().equals("") ? null : item.getRepZipPlusFour().trim());
        party.setPhoneOne((!item.getRepPhone1().trim().equals("null") || !item.getRepPhone1().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone1().trim()) : null);
        party.setPhoneTwo((!item.getRepPhone2().trim().equals("null") || !item.getRepPhone2().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone2().trim()) : null);
        party.setFax((!item.getRepFax().trim().equals("null") || !item.getRepFax().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepFax().trim()) : null);
        if (party.getPhoneTwo().equals("")){
            party.setPhoneTwo(null);
        }
        if (party.getFax().equals("")){
            party.setFax(null);
        }
        party.setEmailAddress(item.getRepEmail().trim().equals("") ? null : item.getRepEmail().trim());

        if (!item.getRepLastName().trim().equals("")){
            casePartyList.add(party);
        }
    }

    private static void migrateOfficers(oldCivilServiceCommissionModel item) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(null);
        party.setCaseType("CSC");
        party.setCaseMonth(null);
        party.setCaseNumber(String.valueOf(item.getCSCNumber()));
        party.setCaseRelation("Chairman");
        party.setAddress1(null);
        party.setAddress2(null);
        party.setCity(null);
        party.setState(null);
        party.setZip(null);
        party.setPhoneOne(null);
        party.setPhoneTwo(null);
        party.setEmailAddress(null);

        if (!item.getChairman1().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman1().trim().equals("") ? null : item.getChairman1().trim());
            party.setJobTitle(item.getChairman1Title().trim().equals("") ? null : item.getChairman1Title().trim());

            casePartyList.add(party);
        }

        if (!item.getChairman2().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman2().trim().equals("") ? null : item.getChairman2().trim());
            party.setJobTitle(item.getChairman2Title().trim().equals("") ? null : item.getChairman2Title().trim());

            casePartyList.add(party);
        }

        if (!item.getChairman3().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman3().trim().equals("") ? null : item.getChairman3().trim());
            party.setJobTitle(item.getChairman3Title().trim().equals("") ? null : item.getChairman3Title().trim());

            casePartyList.add(party);
        }

        if (!item.getChairman4().trim().equals("")){
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getChairman4().trim().equals("") ? null : item.getChairman4().trim());
            party.setJobTitle(item.getChairman4Title().trim().equals("") ? null : item.getChairman4Title().trim());

            casePartyList.add(party);
        }
    }

    private static void migrateCaseData(oldCivilServiceCommissionModel item) {
        CSCCaseModel org = new CSCCaseModel();

        org.setActive(item.getACTIVE() == 1);
        org.setName(!item.getCSCName().trim().equals("") ? item.getCSCName().trim() : null);
        org.setType(!item.getCSCType().trim().equals("") ? item.getCSCType().trim() : null);
        org.setCscNumber(String.valueOf(item.getCSCNumber()));
        org.setAddress1(!item.getCSCAddress1().trim().equals("") ? item.getCSCAddress1().trim() : null);
        org.setAddress2(!item.getCSCAddress2().trim().equals("") ? item.getCSCAddress2().trim() : null);
        org.setCity(!item.getCSCCity().trim().equals("") ? item.getCSCCity().trim() : null);
        org.setState(!item.getCSCState().trim().equals("") ? item.getCSCState().trim() : null);
        org.setZipCode(!item.getCSCZipPlusFour().trim().equals("") ? item.getCSCZipPlusFour().trim() : null);
        org.setCounty(!item.getCSCCounty().trim().equals("") ? item.getCSCCounty().trim() : null);
        org.setPhone1((!item.getCSCPhone1().trim().equals("null") || !item.getCSCPhone1().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getCSCPhone1().trim()) : null);
        org.setPhone2((!item.getCSCPhone2().trim().equals("null") || !item.getCSCPhone2().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getCSCPhone2().trim()) : null);
        org.setFax((!item.getCSCFax().trim().equals("null") || !item.getCSCFax().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getCSCFax().trim()) : null);
        org.setEmail(!item.getCSCEmail().trim().equals("") ? item.getCSCEmail().trim() : null);
        org.setStatutory(!item.getStatutory().trim().equals("Y"));
        org.setCharter(item.getCharter().trim().equals("Home Rule"));
        org.setFiscalYearEnding(StringUtilities.monthName(item.getFiscalYearEnding()));
        org.setLastNotification(!item.getLastNotification().trim().equals("") ? item.getLastNotification().trim() : null);
        org.setActivityLastFiled(!item.getActivitesLastFiled().trim().equals("null")
                ? StringUtilities.convertStringSQLDate(item.getActivitesLastFiled()) : null);
        org.setPreviousFileDate(!item.getPreviousFileDate().trim().equals("null")
                ? StringUtilities.convertStringSQLDate(item.getPreviousFileDate()) : null);
        org.setDueDate(StringUtilities.monthName(item.getDueDate()));
        org.setFiled(!item.getFiled().trim().equals("null") ? StringUtilities.convertStringSQLDate(item.getFiled()) : null);
        org.setValid(item.getValid().trim().equals("Y"));
        org.setNote(!item.getDescription2().trim().equals("") ? item.getDescription2().trim() : null);
        org.setAlsoknownas(!item.getDescription1().trim().equals("") ? item.getDescription1().trim() : null);

        cscCaseList.add(org);
    }

}
