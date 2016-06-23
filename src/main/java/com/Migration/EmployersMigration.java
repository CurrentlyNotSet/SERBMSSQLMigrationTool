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
import com.model.oldPartyModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlBarginingUnit;
import com.sql.sqlBlobFile;
import com.sql.sqlEmployers;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import com.util.StringUtilities;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class EmployersMigration {
    
    public static void migrateEmployers(final MainWindowSceneController control){
        Thread employersThread = new Thread() {
            @Override
            public void run() {
                employersThread(control);
            }
        };
        employersThread.start();        
    }
    
    private static void employersThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Employers Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<employerTypeModel> types = sqlEmployers.getEmployerType();
        List<oldPartyModel> employersList = sqlEmployers.getOldEmployers();
        List<oldBarginingUnitNewModel> unionList = sqlBarginingUnit.getOldBarginingUnits();
        
        totalRecordCount = employersList.size() + unionList.size();
        
        for (oldPartyModel item : employersList){
            migrateEmployers(item, types);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getLastName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }

        for (oldBarginingUnitNewModel item : unionList){
            migrateBarginingUnitUnions(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getBUEmployerName().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Employers: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateEmployers");
        }
    }
    
    private static void migrateEmployers(oldPartyModel old, List<employerTypeModel> typeList) {
        int typeID = 0;
        for (employerTypeModel type : typeList){
            if (type.getType().equals(old.getPartyType())){
                typeID = type.getId();
                break;
            }
        }        
        employersModel item = new employersModel();
        item.setActive(old.getActive());
        item.setEmployerType(typeID);
        item.setEmployerName(!"".equals(old.getBusinessName().trim()) ? old.getBusinessName().trim() : null);
        item.setPrefix(!"".equals(old.getPrefix().trim()) ? old.getPrefix().trim() : null);
        item.setFirstName(!"".equals(old.getFirstName().trim()) ? old.getFirstName().trim() : null);
        item.setMiddleInitial(!"".equals(old.getMiddleInitial().trim()) ? old.getMiddleInitial().trim() : null);
        item.setLastName(!"".equals(old.getLastName().trim()) ? old.getLastName().trim() : null);
        item.setSuffix(!"".equals(old.getSuffix().trim()) ? old.getSuffix().trim() : null);   
        item.setTitle(!"".equals(old.getTitle().trim()) ? old.getTitle().trim() : null);       
        item.setAddress1(!"".equals(old.getAddress1().trim()) ? old.getAddress1().trim() : null);
        item.setAddress2(!"".equals(old.getAddress2().trim()) ? old.getAddress2().trim() : null);
        item.setAddress3(null);
        item.setCity(!"".equals(old.getCity().trim()) ? old.getCity().trim() : null);
        item.setState(!"".equals(old.getState().trim()) ? old.getState().trim() : null);
        item.setZipCode(!"".equals(old.getZipPlusFive().trim()) ? old.getZipPlusFive().trim() : null);
        item.setPhone1(!"".equals(StringUtilities.convertPhoneNumberToString(old.getWorkPhone().trim())) ? StringUtilities.convertPhoneNumberToString(old.getWorkPhone().trim()) : null);
        item.setPhone2(!"".equals(StringUtilities.convertPhoneNumberToString(old.getCellPhone().trim())) ? StringUtilities.convertPhoneNumberToString(old.getCellPhone().trim()) : null);
        item.setFax(!"".equals(StringUtilities.convertPhoneNumberToString(old.getFax().trim())) ? StringUtilities.convertPhoneNumberToString(old.getFax().trim()) : null);
        item.setEmailAddress(!"".equals(old.getEMail().trim()) ? old.getEMail().trim() : null);
        item.setEmployerIDNumber(!"".equals(old.getPartyField1().trim()) ? old.getPartyField1().trim() : null);
        item.setEmployerTypeCode(!"".equals(old.getPartyField2().trim()) ? old.getPartyField2().trim() : null);
        item.setJurisdiction(!"".equals(old.getPartyField3().trim()) ? old.getPartyField3().trim() : null);
        item.setRegion(!"".equals(old.getPartyField4().trim()) ? old.getPartyField4().trim() : null);
        item.setAssistantFirstName(!"".equals(old.getAssistantFirstName().trim()) ? old.getAssistantFirstName().trim() : null);
        item.setAssistantMiddleInitial(!"".equals(old.getAssistantMiddleInitial().trim()) ? old.getAssistantMiddleInitial().trim() : null);
        item.setAssistantLastName(!"".equals(old.getAssistantLastName().trim()) ? old.getAssistantLastName().trim() : null);
        item.setAssistantEmail(!"".equals(old.getAssistantEMail().trim()) ? old.getAssistantEMail().trim() : null);
        item.setCounty(!"".equals(old.getCounty().trim()) ? old.getCounty().trim() : null);

        sqlEmployers.addEmployer(item);
    }

    private static void migrateBarginingUnitUnions(oldBarginingUnitNewModel old) {
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
        
        Timestamp certDateTime = StringUtilities.convertStringDate(old.getCertDate());
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
        sqlBarginingUnit.addBarginingUnit(item);
    }
}
