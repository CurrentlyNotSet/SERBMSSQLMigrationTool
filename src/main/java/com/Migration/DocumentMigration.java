/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CMDSDocumentModel;
import com.model.CMDSReport;
import com.model.SMDSDocumentsModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlCMDSDocuments;
import com.sql.sqlCMDSReport;
import com.sql.sqlDocument;
import com.sql.sqlMigrationStatus;
import com.util.ExcelIterator;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.SlackNotification;
import com.util.StringUtilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Andrew
 */
public class DocumentMigration {

    public static void migrateDocuments(MainWindowSceneController control) {
        Thread docThread = new Thread() {
            @Override
            public void run() {
                docThread(control);
            }
        };
        docThread.start();
    }

    public static void docThread(MainWindowSceneController control) {
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Documents Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        ArrayList SMDSDocXLS = ExcelIterator.read("SMDSDocuments.xlsx");
        ArrayList CMDSReportXLS = ExcelIterator.read("CMDSReports.xlsx");
        List<CMDSDocumentModel> oldCMDSDocumentList = sqlCMDSDocuments.getOldCMDSDocuments();
        if (Global.isDebug()){
            System.out.println("Gathered Documents");
        }
        
        List<SMDSDocumentsModel> cleanedSMDSDocsList = new ArrayList<>();
        List<CMDSReport> cleanedCMDSReport = new ArrayList<>();
        
        totalRecordCount = SMDSDocXLS.size() + CMDSReportXLS.size() + oldCMDSDocumentList.size();
        
        for (Iterator iterator = SMDSDocXLS.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            if (list.size() == 15) {
                cleanedSMDSDocsList.add(sanitizeSMDSDocumentsFromExcel(list));
            }
        }
        for (Iterator iterator = CMDSReportXLS.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            if (list.size() == 4) {
                cleanedCMDSReport.add(sanitizeCMDSReportsFromExcel(list));
            }
        }
        
        sqlDocument.batchAddSMDSDocument(cleanedSMDSDocsList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, cleanedSMDSDocsList.size() + currentRecord, totalRecordCount, "SMDSDocuments Added");
        
        sqlCMDSReport.batchAddCMDSReport(cleanedCMDSReport, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, cleanedCMDSReport.size() + currentRecord, totalRecordCount, "CMDSReport Added");
        
        sqlCMDSDocuments.batchAddCMDSDocuments(oldCMDSDocumentList, control, currentRecord, totalRecordCount);
        SceneUpdater.listItemFinished(control, oldCMDSDocumentList.size() + currentRecord, totalRecordCount, "CMDSDocuments Added");
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Documents: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateDocuments");
        }
        SlackNotification.sendNotification(finishedText);
    }

    private static SMDSDocumentsModel sanitizeSMDSDocumentsFromExcel(List list) {
        SMDSDocumentsModel item = new SMDSDocumentsModel();
        
        item.setSection(list.get(0).toString().trim().equals("NULL") ? null : list.get(0).toString().trim());
        item.setType(list.get(1).toString().trim().equals("NULL") ? null : list.get(1).toString().trim());
        item.setDescription(list.get(2).toString().trim().equals("NULL") ? null : list.get(2).toString().trim());
        item.setFileName(list.get(3).toString().trim().equals("NULL") ? null : list.get(3).toString().trim());
        item.setActive(true);  //column 4
        item.setDueDate(list.get(5).toString().trim().equals("NULL") ? -1 : Integer.valueOf(list.get(5).toString().trim()));
        item.setGroup(list.get(6).toString().trim().equals("NULL") ? null : list.get(6).toString().trim());
        item.setHistoryFileName(list.get(7).toString().trim().equals("NULL") ? null : list.get(7).toString().trim());
        item.setHistoryDescription(list.get(8).toString().trim().equals("NULL") ? null : list.get(8).toString().trim());
        item.setCHDCHG(list.get(9).toString().trim().equals("NULL") ? null : list.get(9).toString().trim());
        item.setQuestionsFileName(list.get(10).toString().trim().equals("NULL") ? null : list.get(10).toString().trim());
        item.setEmailSubject(list.get(11).toString().trim().equals("NULL") ? null : list.get(11).toString().trim());
        item.setParameters(list.get(12).toString().trim().equals("NULL") ? null : list.get(12).toString().trim());
        item.setEmailBody(list.get(13).toString().trim().equals("NULL") ? null : list.get(13).toString().trim());
        item.setSortOrder(list.get(14).toString().trim().equals("NULL") ? -1 : Double.valueOf(list.get(14).toString().trim()));
        
        if (item.getSection() != null) {
            if (item.getSection().trim().equalsIgnoreCase("ULP") && item.getType().trim().equalsIgnoreCase("letter")) {
                if (item.getFileName().contains("IL") || item.getFileName().contains("ILP")) {
                    item.setDueDate(21);
                } else if (item.getFileName().contains("FR") || item.getFileName().contains("LTR")
                        || item.getFileName().contains("RECON") || item.getFileName().contains("Suf")) {
                    item.setDueDate(7);
                }
            }
        }
        if(item.getType().equals("Direct")) {
            item.setType("Directive");
        }
        
        switch (item.getSection()) {
            case "MED":
                item.setEmailBody(Global.getEmailBodyMED());
                break;
            case "REP":
                item.setEmailBody(Global.getEmailBodyREP());
                break;
            case "ULP":
                item.setEmailBody(Global.getEmailBodyULP());
                break;
            default:
                item.setEmailBody(null);
                break;
        }
        
        return item;
    }
    
    private static CMDSReport sanitizeCMDSReportsFromExcel(List list) {
        CMDSReport item = new CMDSReport();
        item.setSection(list.get(0).toString().trim().equals("NULL") ? null : list.get(0).toString().trim());
        item.setDescription(list.get(1).toString().trim().equals("NULL") ? null : list.get(1).toString().trim());
        item.setFileName(list.get(2).toString().trim().equals("NULL") ? null : list.get(2).toString().trim());
        item.setParameters(list.get(3).toString().trim().equals("NULL") ? null : list.get(3).toString().trim());
        item.setActive(true); 
        item.setSortOrder(-1);
        
        return item;
    }
}
