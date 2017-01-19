/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.barginingUnitModel;
import com.model.employerTypeModel;
import com.model.employersModel;
import com.model.oldBarginingUnitNewModel;
import com.model.oldBlobFileModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlBarginingUnit;
import com.sql.sqlBlobFile;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class EmployersMigration {
    
    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static final List<barginingUnitModel> BUList = new ArrayList<>();

    public static void migrateEmployers(final MainWindowSceneController control) {
        Thread employersThread = new Thread() {
            @Override
            public void run() {
                employersThread(control);
            }
        };
        employersThread.start();
    }

    public static void employersThread(MainWindowSceneController controlPassed) {
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("Employers Migration");
        totalRecordCount = 0;
        currentRecord = 0;
        //Add in EmployerTypes
        List<String> employerTypeList = Arrays.asList(
                "Attorney", "Employer", "FCMS Mediator", "Individual",
                "Rep", "State Mediator", "Union"
        );
        sqlEmployers.batchAddEmployerType(employerTypeList);
        //Get Lists
        List<employerTypeModel> types = sqlEmployers.getEmployerType();
        if (Global.isDebug()){
            System.out.println("Gathered Employer Types");
        }
        List<employersModel> employersList = sqlEmployers.getOldEmployers(types);
        if (Global.isDebug()){
            System.out.println("Gathered Employers");
        }
        List<oldBarginingUnitNewModel> unionList = sqlBarginingUnit.getOldBarginingUnits();
        if (Global.isDebug()){
            System.out.println("Gathered Bargining Units");
        }
        
        control.setProgressBarIndeterminateCleaning("Bargining Units");
        totalRecordCount = unionList.size();
        unionList.stream().forEach(item ->
                executor.submit(() ->
                        batchMigrateBarginingUnitUnions(item)));
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        
        currentRecord = 0;
        totalRecordCount = employersList.size() + unionList.size();
        
        sqlEmployers.batchAddEmployer(employersList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + employerTypeList.size(), totalRecordCount, "Employers Finished");
        
        sqlBarginingUnit.batchAddBarginingUnit(BUList, control, currentRecord, totalRecordCount);
        currentRecord = SceneUpdater.listItemFinished(control, currentRecord + BUList.size(), totalRecordCount, "Bargining Units Finished");
        
        BUList.clear();
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Employers: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateEmployers");
        }
    }
        
    private static void batchMigrateBarginingUnitUnions(oldBarginingUnitNewModel old) {
        barginingUnitModel item = new barginingUnitModel();
        
        item.setCaseRefYear(null);
        item.setCaseRefSection(null);
        item.setCaseRefMonth(null);
        item.setCaseRefSequence(null);
        item.setUnitDescription(null);
        item.setNotes(null);
        item.setActive(old.getActive());
        item.setEmployerNumber(!"".equals(old.getEmployerNumber().trim()) ? old.getEmployerNumber().trim() : null);
        item.setUnitNumber(!"".equals(old.getUnitNumber().trim()) ? old.getUnitNumber().trim() : null);
        item.setCert(!"".equals(old.getCert().trim()) ? old.getCert().trim() : null);
        item.setBUEmployerName(!"".equals(old.getBUEmployerName().trim()) ? old.getBUEmployerName().trim() : null);
        item.setJurisdiction(!"".equals(old.getJur().trim()) ? old.getJur().trim() : null);
        item.setCounty(!"".equals(old.getCounty().trim()) ? old.getCounty().trim() : null);
        item.setLUnion(!"".equals(old.getLUnion().trim()) ? old.getLUnion().trim() : null);
        item.setLocal(!"".equals(old.getLocal().trim()) ? old.getLocal().trim() : null);
        item.setStrike(("Y".equals(old.getStrike().trim())) ? 1 : 0);
        item.setLGroup(!"".equals(old.getLGroup().trim()) ? old.getLGroup().trim() : null);        
        
        Timestamp certDateTime = StringUtilities.convertStringTimeStamp(old.getCertDate());
        Date certDate = null;
        if (certDateTime != null){
            certDate = new Date(certDateTime.getTime());
        }
        item.setCertDate(certDate);
        item.setEnabled(("Y".equals(old.getEnabled().trim())) ? 1 : 0);
        
        String[] bunnum = (item.getEmployerNumber()+"-"+item.getUnitNumber()).split("-");
        List<oldBlobFileModel>oldBlobFileList = sqlBlobFile.getOldBlobDataBUDectioption(bunnum);
        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "UnitDesc":
                    item.setUnitDescription(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Notes":
                    item.setNotes(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                default:
                    break;
            }
        }
        
        //Check CaseNumber
        String[] caseNumber = old.getCaseRef().split("-");
        if (old.getCaseRef().trim().length() == 16 &&
                (caseNumber[0].length() == 4 || caseNumber[0].length() == 2) &&
                caseNumber[1].length() < 5 && caseNumber[2].length() == 2){
            if (caseNumber[0].length() == 4){
                item.setCaseRefYear(caseNumber[0]);
            } else if (caseNumber[0].length() == 2) {
                if (Integer.parseInt(caseNumber[0]) > 20){
                    item.setCaseRefYear( "19" + caseNumber[0]);
                } else {
                    item.setCaseRefYear( "20" + caseNumber[0]);
                }
            }            
            item.setCaseRefSection(caseNumber[1]);
            item.setCaseRefMonth(caseNumber[2]);
            if (caseNumber[3].length() > 4){
                item.setCaseRefSequence(caseNumber[3].substring(0, 3));
            } else {
                item.setCaseRefSequence(caseNumber[3]);
            }
        }
        BUList.add(item);
        currentRecord = SceneUpdater.listItemCleaned(control, currentRecord, totalRecordCount, item.getBUEmployerName().trim());
    }
}
