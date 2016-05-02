/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.ULPCaseModel;
import com.model.activityModel;
import com.model.boardMeetingModel;
import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.oldBlobFileModel;
import com.model.oldULPDataModel;
import com.model.oldULPHistoryModel;
import com.model.relatedCaseModel;
import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlBlobFile;
import com.sql.sqlBoardMeeting;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlULPData;
import com.util.Global;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class ULPMigration {
    
    public static void migrateULPData(MainWindowSceneController control){
        Thread ulpThread = new Thread() {
            @Override
            public void run() {
                ulpThread(control);
            }
        };
        ulpThread.start();        
    }
    
    private static void ulpThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("ULP Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        List<oldULPDataModel> oldULPDataList = sqlULPData.getCases();
        totalRecordCount = oldULPDataList.size();
        
        for (oldULPDataModel item : oldULPDataList){
            migrateCase(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getCaseNumber().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating ULP Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateULPCases");
    }
        
    private static void migrateCase(oldULPDataModel item){
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        ULPMigration.migrateChargingParty(item, caseNumber);
        ULPMigration.migrateChargingPartyRep(item, caseNumber);
        ULPMigration.migrateChargedParty(item, caseNumber);
        ULPMigration.migrateChargedPartyRep(item, caseNumber);
        ULPMigration.migrateCaseData(item, caseNumber);
        ULPMigration.migrateBoardMeetings(item, caseNumber);
        migrateRelatedCases(item, caseNumber);
        migrateCaseHistory(caseNumber);
    }
    
    private static void migrateChargingParty(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charging Party");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCPName() == null) ? "" : item.getCPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCPAddress1() == null) ? "" : item.getCPAddress1().trim()));
        party.setAddress2(((item.getCPAddress2() == null) ? "" : item.getCPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCPCity() == null) ? "" : item.getCPCity().trim()));
        party.setState(((item.getCPState() == null) ? "" : item.getCPState().trim()));
        party.setZip(((item.getCPZip() == null) ? "" : item.getCPZip().trim()));
        party.setPhoneOne(((item.getCPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPPhone1().trim())));
        party.setPhoneTwo(((item.getCPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPPhone2().trim())));
        party.setEmailAddress(((item.getCPEmail() == null) ? "" : item.getCPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargingPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {        
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charging Party REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCPREPName() == null) ? "" : item.getCPREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCPREPAddress1() == null) ? "" : item.getCPREPAddress1().trim()));
        party.setAddress2(((item.getCPREPAddress2() == null) ? "" : item.getCPREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCPREPCity() == null) ? "" : item.getCPREPCity().trim()));
        party.setState(((item.getCPREPState() == null) ? "" : item.getCPREPState().trim()));
        party.setZip(((item.getCPREPZip() == null) ? "" : item.getCPREPZip().trim()));
        party.setPhoneOne(((item.getCPREPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPREPPhone1().trim())));
        party.setPhoneTwo(((item.getCPREPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCPREPPhone2().trim())));
        party.setEmailAddress(((item.getCPREPEmail() == null) ? "" : item.getCPREPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargedParty(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charged Party");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCHDName() == null) ? "" : item.getCHDName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCHDAddress1() == null) ? "" : item.getCHDAddress1().trim()));
        party.setAddress2(((item.getCHDAddress2() == null) ? "" : item.getCHDAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCHDCity() == null) ? "" : item.getCHDCity().trim()));
        party.setState(((item.getCHDState() == null) ? "" : item.getCHDState().trim()));
        party.setZip(((item.getCHDZip() == null) ? "" : item.getCHDZip().trim()));
        party.setPhoneOne(((item.getCHDPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDPhone1().trim())));
        party.setPhoneTwo(((item.getCHDPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDPhone2().trim())));
        party.setEmailAddress(((item.getCHDEmail() == null) ? "" : item.getCHDEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateChargedPartyRep(oldULPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new  casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Charged Party REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCHDREPName() == null) ? "" : item.getCHDREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCHDREPAddress1() == null) ? "" : item.getCHDREPAddress1().trim()));
        party.setAddress2(((item.getCHDREPAddress2() == null) ? "" : item.getCHDREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCHDREPCity() == null) ? "" : item.getCHDREPCity().trim()));
        party.setState(((item.getCHDREPState() == null) ? "" : item.getCHDREPState().trim()));
        party.setZip(((item.getCHDREPZip() == null) ? "" : item.getCHDREPZip().trim()));
        party.setPhoneOne(((item.getCHDREPPhone1() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone1().trim())));
        party.setPhoneTwo(((item.getCHDREPPhone2() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCHDREPPhone2().trim())));
        party.setEmailAddress(((item.getCHDREPEmail() == null) ? "" : item.getCHDREPEmail().trim()));
        
        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);
        }
    }
    
    private static void migrateCaseData(oldULPDataModel item, caseNumberModel caseNumber) {
        List<oldBlobFileModel> oldBlobFileList = sqlBlobFile.getOldBlobData(caseNumber);
        
        ULPCaseModel ulpcase = new ULPCaseModel();
        
        ulpcase.setCaseYear(caseNumber.getCaseYear());
        ulpcase.setCaseType(caseNumber.getCaseType());
        ulpcase.setCaseMonth(caseNumber.getCaseMonth());
        ulpcase.setCaseNumber(caseNumber.getCaseNumber());
        ulpcase.setEmployerIDNumber((item.getEmployerNum()== null) ? "" : item.getEmployerNum().trim());
        ulpcase.setDeptInState((item.getDeptInState() == null) ? "" : item.getDeptInState().trim());
        ulpcase.setBarginingUnitNo((item.getBarginingUnitNumber() == null) ? "" : item.getBarginingUnitNumber().trim());
        ulpcase.setEONumber((item.getEmployeeOrgNumber() == null) ? "" : item.getEmployeeOrgNumber().trim());
        ulpcase.setAllegation((item.getAllegation() == null) ? "" : item.getAllegation().trim());
        ulpcase.setCurrentStatus((item.getStatus() == null) ? "" : item.getStatus().trim());
        ulpcase.setPriority("Y".equals(item.getPriority().trim()) || "Yes".equals(item.getPriority().trim()) || "1".equals(item.getPriority().trim()));
        ulpcase.setAssignedDate(StringUtilities.convertStringDate(item.getAssignedDate()));
        ulpcase.setTurnInDate(StringUtilities.convertStringDate(item.getTurnInDate()));
        ulpcase.setReportDueDate(StringUtilities.convertStringDate(item.getReportDate()));
        ulpcase.setDismissalDate(StringUtilities.convertStringDate(item.getDismissalBoardMeetingDate()));
        ulpcase.setDeferredDate(StringUtilities.convertStringDate(item.getDeferredBoardMeetingDate()));
        ulpcase.setFileDate(StringUtilities.convertStringDate(item.getFileDate()));
        ulpcase.setProbableCause("Y".equals(item.getProbable().trim()) || "Yes".equals(item.getProbable().trim()) || "1".equals(item.getProbable().trim()));
        ulpcase.setAppealDateReceived(StringUtilities.convertStringDate(item.getAppealDateReceived()));
        ulpcase.setAppealDateSent(StringUtilities.convertStringDate(item.getAppealDateSent()));
        ulpcase.setCourtName((item.getCourt() == null) ? "" : item.getCourt().trim());
        ulpcase.setCourtCaseNumber((item.getCourtCaseNumber() == null) ? "" : item.getCourtCaseNumber().trim());
        ulpcase.setSERBCaseNumber((item.getSERBCourtCaseNumber() == null) ? "" : item.getSERBCourtCaseNumber().trim());
        ulpcase.setFinalDispositionStatus((item.getFinalDispostion() == null) ? "" : item.getFinalDispostion().trim());
//        ulpcase.setInvestigatorID();
//        ulpcase.setMediatorAssignedID();
//        ulpcase.setAljID();
        ulpcase.setNote("");
        ulpcase.setInvestigationReveals("");
        ulpcase.setStatement("");
        ulpcase.setRecommendation("");

        for (oldBlobFileModel blob : oldBlobFileList) {
            if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                case "Notes":
                    ulpcase.setNote(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Invest":
                    ulpcase.setInvestigationReveals(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Statement":
                    ulpcase.setStatement(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                case "Recommendation":
                    ulpcase.setRecommendation(StringUtilities.convertBlobFileToString(blob.getBlobData()));
                    break;
                default:
                    break;
            }
        }
        sqlULPData.importOldULPCase(ulpcase);
    }
    
    private static void migrateBoardMeetings(oldULPDataModel item, caseNumberModel caseNumber) {
        boardMeetingModel meeting = new boardMeetingModel();
        
        meeting.setCaseYear(caseNumber.getCaseYear());
        meeting.setCaseType(caseNumber.getCaseType());
        meeting.setCaseMonth(caseNumber.getCaseMonth());
        meeting.setCaseNumber(caseNumber.getCaseNumber());
        
        if (!"".equals(item.getBoardMeetingDate().trim()) || !"".equals(item.getAgendaItem().trim()) || !"".equals(item.getRecommendation().trim())) {
            meeting.setAgendaItemNumber(item.getAgendaItem().trim());
            meeting.setBoardMeetingDate(StringUtilities.convertStringDate(item.getBoardMeetingDate()));
            meeting.setRecommendation(item.getRecommendation().trim());
            sqlBoardMeeting.addULPBoardMeeting(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate1().trim()) || !"".equals(item.getAgendaItem1().trim()) || !"".equals(item.getRecommendation1().trim())) {
            meeting.setAgendaItemNumber(item.getAgendaItem1().trim());
            meeting.setBoardMeetingDate(StringUtilities.convertStringDate(item.getBoardMeetingDate1()));
            meeting.setRecommendation(item.getRecommendation1().trim());
            sqlBoardMeeting.addULPBoardMeeting(meeting);
        }
        
        if (!"".equals(item.getBoardMeetingDate2().trim()) || !"".equals(item.getAgendaItem2().trim()) || !"".equals(item.getRecommendation2().trim())) {
            meeting.setAgendaItemNumber(item.getAgendaItem2().trim());
            meeting.setBoardMeetingDate(StringUtilities.convertStringDate(item.getBoardMeetingDate2()));
            meeting.setRecommendation(item.getRecommendation2().trim());
            sqlBoardMeeting.addULPBoardMeeting(meeting);
        }
    }

    private static void migrateRelatedCases(oldULPDataModel item, caseNumberModel caseNumber) {
        relatedCaseModel relatedCase = new relatedCaseModel();
        
        relatedCase.setCaseYear(caseNumber.getCaseYear());
        relatedCase.setCaseType(caseNumber.getCaseType());
        relatedCase.setCaseMonth(caseNumber.getCaseMonth());
        relatedCase.setCaseNumber(caseNumber.getCaseNumber());
        relatedCase.setRelatedCaseNumber(item.getRelatedCases());
        
//        sqlRelatedCase.addRelatedCase(relatedCase);
    }
    
    private static void migrateCaseHistory(caseNumberModel caseNumber) {
        List<oldULPHistoryModel> oldULPDataList = sqlActivity.getULPHistoryByCase(StringUtilities.generateFullCaseNumber(caseNumber));
        
        for (oldULPHistoryModel old : oldULPDataList){
            int userID = 0;
            for (userModel usr : Global.getUserList()){
                if (old.getUserInitials().toLowerCase().trim().equals(usr.getUserName().toLowerCase().trim())){
                    userID = usr.getId();
                }
            }
                                                
            activityModel item = new activityModel();
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setUserID(userID);
            item.setDate(old.getDate());
            item.setAction(old.getAction());
            item.setFileName(old.getFileName());
            item.setFrom(old.getEmailFrom());
            item.setTo(old.getEmailTo());
            item.setType("");
            item.setRedacted(0);
            item.setAwaitingTimeStamp(0);
            
            sqlActivity.addActivity(item);
        }
    }
    
}
