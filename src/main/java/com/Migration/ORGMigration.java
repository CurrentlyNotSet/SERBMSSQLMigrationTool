/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ORGCaseModel;
import com.model.activityModel;
import com.model.oldEmployeeOrgModel;
import com.model.oldORGHistoryModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlMigrationStatus;
import com.sql.sqlOrgCase;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class ORGMigration {

    public static void migrateORGData(final MainWindowSceneController control) {
        Thread orgThread = new Thread() {
            @Override
            public void run() {
                orgThread(control);
            }
        };
        orgThread.start();
    }

    private static void orgThread(MainWindowSceneController control) {
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("ORG Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;

        List<oldEmployeeOrgModel> oldORGCaseList = sqlOrgCase.getCases();
        List<oldORGHistoryModel> oldORGHistoryList = sqlActivity.getORGHistory();

        totalRecordCount = oldORGCaseList.size() + oldORGHistoryList.size();

//        for (oldEmployeeOrgModel item : oldORGCaseList) {
//            migrateCase(item);
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getOrgNumber() + ": " + item.getOrgName());
//        }
        
        for (oldORGHistoryModel item : oldORGHistoryList) {
            migrateCaseHistory(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getOrgNum() + ": " + item.getDateTimeMillis());
        }

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating ORG Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateORGCases");
        }
    }

    private static void migrateCase(oldEmployeeOrgModel item) {
        migrateCaseData(item);
    }

    private static void migrateCaseData(oldEmployeeOrgModel item) {
        ORGCaseModel org = new ORGCaseModel();

        org.setActive(item.getActive());
        org.setOrgName(!item.getOrgName().trim().equals("") ? item.getOrgName().trim() : null);
        org.setOrgNumber(!item.getOrgNumber().trim().equals("") ? item.getOrgNumber().trim() : null);
        org.setFiscalYearEnding(StringUtilities.monthName(item.getFiscalYearEnding()));
        String filingDueDate = StringUtilities.monthName(StringUtilities.monthNumber(item.getFiscalYearEnding().replaceAll("[^A-Za-z]", "")));
        org.setFilingDueDate(filingDueDate != null ? filingDueDate + " 15th" : null);
        org.setAnnualReport(!item.getAnnualReportLastFiled().trim().equals("") ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getAnnualReportLastFiled())) : null);
        org.setFinancialReport(!item.getFinancialStatementLastFiled().trim().equals("") ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getFinancialStatementLastFiled())) : null);
        org.setRegistrationReport(!item.getRegistrationReportLastFiled().trim().equals("") ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getRegistrationReportLastFiled())) : null);
        org.setConstructionAndByLaws(!item.getConstitutionAndBylawsFiled().trim().equals("") ? StringUtilities.convertTimeStampToDate(StringUtilities.convertStringDate(item.getConstitutionAndBylawsFiled())) : null);
        org.setFiledByParent(item.getFiledByParent().equals("Y"));

        sqlOrgCase.importOldEmployeeOrgCase(org);
    }

    private static void migrateCaseHistory(oldORGHistoryModel old) {
        activityModel item = new activityModel();
        item.setCaseYear(null);
        item.setCaseType("ORG");
        item.setCaseMonth(null);
        item.setCaseNumber(old.getOrgNum());
        item.setUserID(StringUtilities.convertUserToID(old.getUserInitials()));
        item.setDate(new Timestamp(old.getDateTimeMillis()));
        item.setAction(!old.getDescription().trim().equals("") ? old.getDescription().trim() : null);
        item.setFileName(!old.getFileName().trim().equals("") ? old.getFileName().trim() : null);
        item.setFrom(!old.getSrc().trim().equals("") ? old.getSrc().trim() : null);
        item.setTo(!old.getDest().trim().equals("") ? old.getDest().trim() : null);
        item.setType(!old.getFileAttrib().trim().equals("") ? old.getFileAttrib().trim() : null);
        item.setComment(old.getNote() != null ? old.getNote().trim() : null);
        item.setRedacted("Y".equals(old.getRedacted().trim()) ? 1 : 0);
        item.setAwaitingTimeStamp(0);

        sqlActivity.addActivity(item);
    }

}
