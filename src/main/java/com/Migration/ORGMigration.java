/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ORGCaseModel;
import com.model.oldEmployeeOrgModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.sql.sqlOrgCase;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class ORGMigration {
    
    public static void migrateORGData(final MainWindowSceneController control){
        Thread orgThread = new Thread() {
            @Override
            public void run() {
                orgThread(control);
            }
        };
        orgThread.start();        
    }
    
    private static void orgThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("ORG Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<oldEmployeeOrgModel> oldORGCaseList = sqlOrgCase.getCases();
        
        totalRecordCount = oldORGCaseList.size();
        
        for (oldEmployeeOrgModel item : oldORGCaseList) {
            migrateCase(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getOrgNumber() + ": " + item.getOrgName());
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating ORG Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
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
        org.setAnnualReport(!item.getAnnualReportLastFiled().trim().equals("") ? new Date(StringUtilities.convertStringDate(item.getAnnualReportLastFiled()).getTime()) : null);
        org.setFinancialReport(!item.getFinancialStatementLastFiled().trim().equals("") ? new Date(StringUtilities.convertStringDate(item.getFinancialStatementLastFiled()).getTime()) : null);
        org.setRegistrationReport(!item.getRegistrationReportLastFiled().trim().equals("") ? new Date(StringUtilities.convertStringDate(item.getRegistrationReportLastFiled()).getTime()) : null);
        org.setConstructionAndByLaws(!item.getConstitutionAndBylawsFiled().trim().equals("") ? new Date(StringUtilities.convertStringDate(item.getConstitutionAndBylawsFiled()).getTime()) : null);
        org.setFiledByParent(item.getFiledByParent().equals("Y"));
        
        sqlOrgCase.importOldEmployeeOrgCase(org);
    }
}
