/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ORGCaseModel;
import com.model.ORGParentChildLinkModel;
import com.model.activityModel;
import com.model.casePartyModel;
import com.model.employersModel;
import com.model.oldBlobFileModel;
import com.model.oldEmployeeOrgModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlCaseParty;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.sql.sqlORGCase;
import com.sql.sqlORGParentChildLink;
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
public class ORGMigration {

    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static List<casePartyModel> casePartyList = new ArrayList<>();
    private static List<ORGCaseModel> orgCaseList = new ArrayList<>();

    public static void migrateORGData(final MainWindowSceneController control) {
        Thread orgThread = new Thread() {
            @Override
            public void run() {
                orgThread(control);
            }
        };
        orgThread.start();
    }

    public static void orgThread(MainWindowSceneController controlPassed) {
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("ORG Case Migration");

        totalRecordCount = 0;
        currentRecord = 0;

        List<oldEmployeeOrgModel> oldORGCaseList = sqlORGCase.getCases();
        if (Global.isDebug()) {
            System.out.println("Gathered ORG Cases");
        }
        List<activityModel> oldORGHistoryList = sqlActivity.getORGHistory();
        if (Global.isDebug()) {
            System.out.println("Gathered ORG History");
        }
        List<ORGParentChildLinkModel> ORGParentChildLinkList = sqlORGParentChildLink.getOldLink();
        if (Global.isDebug()) {
            System.out.println("Gathered Parent/Child Links");
        }
        List<employersModel> employerReference = sqlEmployers.getEmployersForReference();
        if (Global.isDebug()) {
            System.out.println("Gathered Employers (Reference)");
        }

        //Clean ORG Case Data
        control.setProgressBarIndeterminateCleaning("ORG Case");
        totalRecordCount = oldORGCaseList.size();
        oldORGCaseList.stream().forEach(item
                -> executor.submit(()
                        -> migrateCase(item, employerReference)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }

        oldORGCaseList = null;

        sqlORGCase.batchAddEmployeeOrgCase(orgCaseList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, casePartyList.size(), totalRecordCount, "ORG Cases Finished");

        sqlCaseParty.batchAddPartyInformation(casePartyList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, casePartyList.size(), totalRecordCount, "ORG Parties Finished");

        sqlActivity.batchAddActivity(oldORGHistoryList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, oldORGHistoryList.size(), totalRecordCount, "History Finished");

        sqlORGParentChildLink.batchAddOrgParentChildLinks(ORGParentChildLinkList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, ORGParentChildLinkList.size(), totalRecordCount, "Parent/Child Link Finished");

        casePartyList = null;
        orgCaseList = null;
        oldORGHistoryList = null;
        ORGParentChildLinkList = null;

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating ORG Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateORGCases");
        }
        SlackNotification.sendBasicNotification(finishedText);
        System.gc();
    }

    private static void migrateCase(oldEmployeeOrgModel item, List<employersModel> employerReference) {
        migrateRepresentative(item);
        migrateOfficers(item);
        migrateCaseData(item, employerReference);
        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getOrgName().trim());
    }

    private static void migrateRepresentative(oldEmployeeOrgModel item) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(null);
        party.setCaseType("ORG");
        party.setCaseMonth(null);
        party.setCaseNumber(item.getOrgNumber());
        party.setCaseRelation("Representative");
        party.setFirstName(item.getRepFirstName().trim().equals("") ? null : item.getRepFirstName().trim());
        party.setMiddleInitial(item.getRepMiddleInitial().trim().equals("") ? null : item.getRepMiddleInitial().trim());
        party.setLastName(item.getRepLastName().trim().equals("") ? null : item.getRepLastName().trim());
        party.setAddress1(item.getRepAddress1().trim().equals("") ? null : item.getRepAddress1().trim());
        party.setAddress2(item.getRepAddress2().trim().equals("") ? null : item.getRepAddress2().trim());
        party.setCity(item.getRepCity().trim().equals("") ? null : item.getRepCity().trim());
        party.setState(item.getRepState().trim().equals("") ? null : item.getRepState().trim());
        party.setZip(item.getRepZipPlusFive().trim().equals("") ? null : item.getRepZipPlusFive().trim());
        party.setPhoneOne((!item.getRepPhone1().trim().equals("null") || !item.getRepPhone1().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone1().trim()) : null);
        party.setPhoneTwo((!item.getRepPhone2().trim().equals("null") || !item.getRepPhone2().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepPhone2().trim()) : null);
        party.setFax((!item.getRepFax().trim().equals("null") || !item.getRepFax().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getRepFax().trim()) : null);
        if (party.getPhoneTwo().equals("")) {
            party.setPhoneTwo(null);
        }
        if (party.getFax().equals("")) {
            party.setFax(null);
        }
        party.setEmailAddress(item.getRepEMail().trim().equals("") ? null : item.getRepEMail().trim());

        if (!item.getRepLastName().trim().equals("")) {
            casePartyList.add(party);
        }
    }

    private static void migrateOfficers(oldEmployeeOrgModel item) {
        if (!item.getOfficer1().trim().equals("")) {
            casePartyModel party = new casePartyModel();
            party.setCaseYear(null);
            party.setCaseType("ORG");
            party.setCaseMonth(null);
            party.setCaseNumber(item.getOrgNumber());
            party.setCaseRelation("Officer");
            party.setAddress1(null);
            party.setAddress2(null);
            party.setCity(null);
            party.setState(null);
            party.setZip(null);
            party.setPhoneOne(null);
            party.setPhoneTwo(null);
            party.setEmailAddress(null);
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getOfficer1().trim().equals("") ? null : item.getOfficer1().trim());
            party.setJobTitle(item.getOfficer1Title().trim().equals("") ? null : item.getOfficer1Title().trim());

            casePartyList.add(party);
        }

        if (!item.getOfficer2().trim().equals("")) {
            casePartyModel party = new casePartyModel();
            party.setCaseYear(null);
            party.setCaseType("ORG");
            party.setCaseMonth(null);
            party.setCaseNumber(item.getOrgNumber());
            party.setCaseRelation("Officer");
            party.setAddress1(null);
            party.setAddress2(null);
            party.setCity(null);
            party.setState(null);
            party.setZip(null);
            party.setPhoneOne(null);
            party.setPhoneTwo(null);
            party.setEmailAddress(null);
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getOfficer2().trim().equals("") ? null : item.getOfficer2().trim());
            party.setJobTitle(item.getOfficer2Title().trim().equals("") ? null : item.getOfficer2Title().trim());

            casePartyList.add(party);
        }

        if (!item.getOfficer3().trim().equals("")) {
            casePartyModel party = new casePartyModel();
            party.setCaseYear(null);
            party.setCaseType("ORG");
            party.setCaseMonth(null);
            party.setCaseNumber(item.getOrgNumber());
            party.setCaseRelation("Officer");
            party.setAddress1(null);
            party.setAddress2(null);
            party.setCity(null);
            party.setState(null);
            party.setZip(null);
            party.setPhoneOne(null);
            party.setPhoneTwo(null);
            party.setEmailAddress(null);
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getOfficer3().trim().equals("") ? null : item.getOfficer3().trim());
            party.setJobTitle(item.getOfficer3Title().trim().equals("") ? null : item.getOfficer3Title().trim());

            casePartyList.add(party);
        }

        if (!item.getOfficer4().trim().equals("")) {
            casePartyModel party = new casePartyModel();
            party.setCaseYear(null);
            party.setCaseType("ORG");
            party.setCaseMonth(null);
            party.setCaseNumber(item.getOrgNumber());
            party.setCaseRelation("Officer");
            party.setAddress1(null);
            party.setAddress2(null);
            party.setCity(null);
            party.setState(null);
            party.setZip(null);
            party.setPhoneOne(null);
            party.setPhoneTwo(null);
            party.setEmailAddress(null);
            party.setLastName(null);
            party.setJobTitle(null);
            party.setLastName(item.getOfficer4().trim().equals("") ? null : item.getOfficer4().trim());
            party.setJobTitle(item.getOfficer4Title().trim().equals("") ? null : item.getOfficer4Title().trim());

            casePartyList.add(party);
        }
    }

    private static void migrateCaseData(oldEmployeeOrgModel item, List<employersModel> employerReference) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(item.getOrgNumber());
        ORGCaseModel org = new ORGCaseModel();

        org.setActive(item.getActive());
        org.setOrgName((!item.getOrgName().trim().equals("null") || !item.getOrgName().trim().equals("")) ? item.getOrgName().trim() : null);
        org.setOrgNumber((!item.getOrgNumber().trim().equals("null") || !item.getOrgNumber().trim().equals("")) ? item.getOrgNumber().trim() : null);
        org.setFiscalYearEnding(StringUtilities.monthName(item.getFiscalYearEnding()));
        String filingDueDate = StringUtilities.monthName(StringUtilities.monthNumber(item.getDueDate().replaceAll("[^A-Za-z]", "")));
        org.setFilingDueDate(filingDueDate != null ? filingDueDate + " 15th" : null);
        org.setAnnualReport(!item.getAnnualReportLastFiled().trim().equals("null") ? StringUtilities.convertStringSQLDate(item.getAnnualReportLastFiled()) : null);
        org.setFinancialReport(!item.getFinancialStatementLastFiled().trim().equals("null")
                ? StringUtilities.convertStringSQLDate(item.getFinancialStatementLastFiled()) : null);
        org.setRegistrationReport(!item.getRegistrationReportLastFiled().trim().equals("null")
                ? StringUtilities.convertStringSQLDate(item.getRegistrationReportLastFiled()) : null);
        org.setConstructionAndByLaws(!item.getConstitutionAndBylawsFiled().trim().equals("null")
                ? StringUtilities.convertStringSQLDate(item.getConstitutionAndBylawsFiled()) : null);
        org.setFiledByParent(item.getFiledByParent().equals("Y"));
        String note = !item.getDescription2().trim().equals("null") ? item.getDescription2().trim() : "";
        org.setAlsoKnownAs((!item.getDescription1().trim().equals("null") || !item.getDescription1().trim().equals(""))
                ? item.getDescription1().trim() : null);
        org.setOrgType((!item.getOrgType().trim().equals("null") || !item.getOrgType().trim().equals("")) ? item.getOrgType().trim() : null);
        org.setOrgPhone1((!item.getOrgPhone1().trim().equals("null") || !item.getOrgPhone1().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getOrgPhone1().trim()) : null);
        org.setOrgPhone2((!item.getOrgPhone2().trim().equals("null") || !item.getOrgPhone2().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getOrgPhone2().trim()) : null);
        org.setOrgFax((!item.getOrgFax().trim().equals("null") || !item.getOrgFax().trim().equals(""))
                ? StringUtilities.convertPhoneNumberToString(item.getOrgFax().trim()) : null);
        org.setOrgAddress1((!item.getOrgAddress1().trim().equals("null") || !item.getOrgAddress1().trim().equals(""))
                ? item.getOrgAddress1().trim() : null);
        org.setOrgAddress2((!item.getOrgAddress2().trim().equals("null") || !item.getOrgAddress2().trim().equals(""))
                ? item.getOrgAddress2().trim() : null);
        org.setOrgCity((!item.getOrgCity().trim().equals("null") || !item.getOrgCity().trim().equals(""))
                ? item.getOrgCity().trim() : null);
        org.setOrgState((!item.getOrgState().trim().equals("null") || !item.getOrgState().trim().equals(""))
                ? item.getOrgState().trim() : null);
        org.setOrgZip((!item.getOrgZipPlusFive().trim().equals("null") || !item.getOrgZipPlusFive().trim().equals(""))
                ? item.getOrgZipPlusFive().trim() : null);
        org.setOrgCounty((!item.getOrgCounty().trim().equals("null") || !item.getOrgCounty().trim().equals(""))
                ? item.getOrgCounty().trim() : null);
        org.setOrgEmail((!item.getOrgEMail().trim().equals("null") || !item.getOrgEMail().trim().equals(""))
                ? item.getOrgEMail().trim() : null);
        org.setLastNotification((!item.getLastNotification().trim().equals("null") || !item.getLastNotification().trim().equals(""))
                ? item.getLastNotification().trim() : null);
        org.setDeemedCertified(item.getDeemedCertified().equals("Y"));
        org.setBoardCertified(item.getBoardCertified().equals("Y"));
        org.setValid(item.getValid().equals("Y"));
        org.setParent1((!item.getParent1().trim().equals("null") || !item.getParent1().trim().equals(""))
                ? item.getParent1().trim() : null);
        org.setParent2((!item.getParent2().trim().equals("null") || !item.getParent2().trim().equals(""))
                ? item.getParent2().trim() : null);
        org.setOutsideCase((!item.getCase1().trim().equals("null") || !item.getCase1().trim().equals(""))
                ? item.getCase1().trim() : null);
        org.setDateFiled(!item.getFiled().trim().equals("null")
                ? (StringUtilities.convertStringSQLDate(item.getFiled())) : null);
        org.setCertifiedDate(!item.getCertifiedDate().trim().equals("null")
                ? (StringUtilities.convertStringSQLDate(item.getCertifiedDate())) : null);
        org.setRegistrationLetterSent(!item.getRegistrationSentDate().trim().equals("null")
                ? (StringUtilities.convertStringSQLDate(item.getRegistrationSentDate())) : null);
        org.setExtensionDate(!item.getExtensionDate().trim().equals("null")
                ? (StringUtilities.convertStringSQLDate(item.getExtensionDate())) : null);

        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) {
                switch (blob.getSelectorA().trim()) {
                    case "ORGNotesFile":
                        String note2 = StringUtilities.convertBlobFileToString(blob.getBlobData());

                        note2 = note2 == null ? "" : note2;
                        note2 = note2.trim().toLowerCase().equals("null") ? "" : note2.trim();

                        if (!note.trim().toLowerCase().equals(note2.trim().toLowerCase())) {
                            if (!note.trim().equals("") && !note2.trim().equals("")) {
                                note += System.lineSeparator() + System.lineSeparator() + note2;
                            } else if (note.trim().equals("") && !note2.trim().equals("")) {
                                note += note2;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        org.setNote(note.trim().equals("") ? null : note);

        org.setEmployerID("");
        if (item.getOrgEmployerid() != null) {
            String id = item.getOrgEmployerid().replaceAll("[^0-9]", "").trim();

            switch (id.length()) {
                case 1:
                    id = "000" + id;
                    break;
                case 2:
                    id = "00" + id;
                    break;
                case 3:
                    id = "0" + id;
                    break;
                default:
                    break;
            }

            org.setEmployerID(id);
        }

        if (!org.getEmployerID().equals("")) {
            for (employersModel emp : employerReference) {
                if (org.getEmployerID().equals(emp.getId())) {
                    org.setEmployerID(emp.getEmployerIDNumber());
                    break;
                }
            }
        }
        orgCaseList.add(org);
    }

}
