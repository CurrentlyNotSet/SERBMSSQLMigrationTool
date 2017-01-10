/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.CMDSDocumentModel;
import com.model.CMDSReport;
import com.model.oldDocumentModel;
import com.model.smdsDocumentsModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlCMDSDocuments;
import com.sql.sqlCMDSReport;
import com.sql.sqlDocument;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.io.FileInputStream;
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

    private static void docThread(MainWindowSceneController control) {
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Documents Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        ArrayList SMDSDocXLS = read("SMDSDocuments.xlsx");
        ArrayList CMDSReportXLS = read("CMDSReports.xlsx");
        
//        List<oldDocumentModel> oldDocumentList = sqlDocument.getOldDocuments();
        List<CMDSDocumentModel> oldCMDSDocumentList = sqlCMDSDocuments.getOldCMDSDocuments();
        
        totalRecordCount = SMDSDocXLS.size() + CMDSReportXLS.size() + oldCMDSDocumentList.size();

        for (Iterator iterator = SMDSDocXLS.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            if (list.size() == 15) {
                sanitizeSMDSDocumentsFromExcel(list);
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount,
                    (list.size() <= 2 ? "" : list.get(0).toString().trim()) + ": " + (list.size() <= 1 ? "" : list.get(2).toString().trim()));
        }

        for (Iterator iterator = CMDSReportXLS.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            if (list.size() == 5) {
                sanitizeCMDSReportsFromExcel(list);
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount,
                    (list.size() <= 2 ? "" : list.get(0).toString().trim()) + ": " + (list.size() <= 1 ? "" : list.get(2).toString().trim()));
        }
        
//        //get file list from newDB
//        List<smdsDocumentsModel> newDocumentList = sqlDocument.getNewDocuments();
//
//        //import missing old documents
//        for (oldDocumentModel item : oldDocumentList) {
//            boolean exists = false;
//            for (smdsDocumentsModel sqldocs : newDocumentList) {
//                if (sqldocs.getFileName() == null ? item.getDocumentFileName() == null
//                        : sqldocs.getFileName().equals(item.getDocumentFileName())) {
//                    exists = true;
//                    break;
//                }
//            }
//            if (!exists) {
//                migrateDocument(item);
//            }
//            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getSection() + ": " + item.getDocumentFileName());
//        }

        for (CMDSDocumentModel item : oldCMDSDocumentList) {
            sqlCMDSDocuments.addCMDSDocuments(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, "CMDS: " + item.getLetterName());
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Documents: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateDocuments");
        }
    }

    private static void migrateDocument(oldDocumentModel item) {
        smdsDocumentsModel doc = new smdsDocumentsModel();

        doc.setActive(item.getActive() == 1);
        doc.setSection(item.getSection());
        doc.setType(item.getType());
        doc.setDescription(item.getDocumentDescription());
        doc.setFileName(item.getDocumentFileName());
        doc.setGroup(null);
        doc.setHistoryFileName(null);
        doc.setHistoryDescription(null);
        doc.setCHDCHG(null);
        doc.setQuestionsFileName(null);
        doc.setEmailSubject(item.getDocumentDescription());
        doc.setParameters(null);
        doc.setSortOrder(0);
        
        switch (item.getSection()) {
            case "MED":
                doc.setEmailBody(Global.getEmailBodyMED());
                break;
            case "REP":
                doc.setEmailBody(Global.getEmailBodyREP());
                break;
            case "ULP":
                doc.setEmailBody(Global.getEmailBodyULP());
                break;
            default:
                doc.setEmailBody(null);
                break;
        }        
        
        if (item.getSection().trim().equalsIgnoreCase("ULP") && item.getType().trim().equalsIgnoreCase("letter")) {
            if (item.getDocumentFileName().contains("IL") || item.getDocumentFileName().contains("ILP")) {
                doc.setDueDate(21);
            } else if (item.getDocumentFileName().contains("FR") || item.getDocumentFileName().contains("LTR")
                    || item.getDocumentFileName().contains("RECON") || item.getDocumentFileName().contains("Suf")) {
                doc.setDueDate(7);
            }
        }
        if(item.getType().equals("Direct")) {
            item.setType("Directive");
        }

        sqlDocument.addSMDSDocument(doc);
    }

    public static ArrayList read(String fileName) {
        ArrayList cellVectorHolder = new ArrayList();
        try {
            FileInputStream myInput = new FileInputStream(fileName);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                List list = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    list.add(myCell);
                }
                cellVectorHolder.add(list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cellVectorHolder;
    }

    private static void sanitizeSMDSDocumentsFromExcel(List list) {
        smdsDocumentsModel item = new smdsDocumentsModel();
        
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
        
        sqlDocument.addSMDSDocument(item);
    }
    
    private static void sanitizeCMDSReportsFromExcel(List list) {
        CMDSReport item = new CMDSReport();
        item.setSection(list.get(0).toString().trim().equals("NULL") ? null : list.get(0).toString().trim());
        item.setDescription(list.get(1).toString().trim().equals("NULL") ? null : list.get(1).toString().trim());
        item.setFileName(list.get(2).toString().trim().equals("NULL") ? null : list.get(2).toString().trim());
        item.setParameters(list.get(3).toString().trim().equals("NULL") ? null : list.get(3).toString().trim());
        item.setActive(true);  //column 4
        item.setSortOrder(-1);
        
        sqlCMDSReport.addCMDSReport(item);
    }
}
