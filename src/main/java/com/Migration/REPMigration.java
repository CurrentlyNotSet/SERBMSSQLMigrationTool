/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.caseNumberModel;
import com.model.casePartyModel;
import com.model.oldREPDataModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlCaseParty;
import com.sql.sqlMigrationStatus;
import com.sql.sqlREPData;
import com.util.Global;
import com.util.StringUtilities;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class REPMigration {

    public static void migrateREPData(final MainWindowSceneController control) {
        Thread repThread;

        repThread = new Thread() {
            @Override
            public void run() {
                repThread(control);
            }
        };
        repThread.start();
    }

    private static void repThread(MainWindowSceneController control) {
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("REP Case Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        List<oldREPDataModel> oldREPDataList = sqlREPData.getCases();
        totalRecordCount = oldREPDataList.size();

        for (oldREPDataModel item : oldREPDataList) {
            migrateCase(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getCaseNumber().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating REP Cases: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        sqlMigrationStatus.updateTimeCompleted("MigrateREPCases");
    }

    private static void migrateCase(oldREPDataModel item) {
        caseNumberModel caseNumber = StringUtilities.parseFullCaseNumber(item.getCaseNumber().trim());
        migratePetitioner(item, caseNumber);
        migratePetitionerRep(item, caseNumber);
        migrateEmployer(item, caseNumber);
        migrateEmployerRep(item, caseNumber);
        migrateEmployeeOrg(item, caseNumber);
        migrateEmployeeOrgRep(item, caseNumber);
        migrateRivalEmployeeOrg(item, caseNumber);
        migrateRivalEmployeeOrgRep(item, caseNumber);
        migrateRivalEmployeeOrg2(item, caseNumber);
        migrateRivalEmployeeOrg2Rep(item, caseNumber);
        migrateRivalEmployeeOrg3(item, caseNumber);
        migrateRivalEmployeeOrg3Rep(item, caseNumber);
        migrateIncumbentEmployeeOrg(item, caseNumber);
        migrateIncumbentEmployeeOrgRep(item, caseNumber);
        migrateIntervener(item, caseNumber);
        migrateIntervenerRep(item, caseNumber);
        migrateConversionSchool(item, caseNumber);
        migrateConversionSchoolRep(item, caseNumber);
    }

    private static void migratePetitioner(oldREPDataModel item, caseNumberModel caseNumber) {

        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Petitioner");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getPName() == null) ? "" : item.getPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getPAddress1() == null) ? "" : item.getPAddress1().trim()));
        party.setAddress2(((item.getPAddress2() == null) ? "" : item.getPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getPCity() == null) ? "" : item.getPCity().trim()));
        party.setState(((item.getPState() == null) ? "" : item.getPState().trim()));
        party.setZip(((item.getPZip() == null) ? "" : item.getPZip().trim()));
        party.setPhoneOne(((item.getPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getPEmail() == null) ? "" : item.getPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getPAsstName() == null) ? "" : item.getPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getPAsstEmail() == null) ? "" : item.getPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migratePetitionerRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Petitioner REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getPREPName() == null) ? "" : item.getPREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getPREPAddress1() == null) ? "" : item.getPREPAddress1().trim()));
        party.setAddress2(((item.getPREPAddress2() == null) ? "" : item.getPREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getPREPCity() == null) ? "" : item.getPREPCity().trim()));
        party.setState(((item.getPREPState() == null) ? "" : item.getPREPState().trim()));
        party.setZip(((item.getPREPZip() == null) ? "" : item.getPREPZip().trim()));
        party.setPhoneOne(((item.getPREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getPREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getPREPEmail() == null) ? "" : item.getPREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getPREPAsstName() == null) ? "" : item.getPREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getPREPAsstEmail() == null) ? "" : item.getPREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateEmployer(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Employer");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getEName() == null) ? "" : item.getEName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getEAddress1() == null) ? "" : item.getEAddress1().trim()));
        party.setAddress2(((item.getEAddress2() == null) ? "" : item.getEAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getECity() == null) ? "" : item.getECity().trim()));
        party.setState(((item.getEState() == null) ? "" : item.getEState().trim()));
        party.setZip(((item.getEZip() == null) ? "" : item.getEZip().trim()));
        party.setPhoneOne(((item.getEPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getEPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getEEmail() == null) ? "" : item.getEEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEAsstName() == null) ? "" : item.getEAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getEAsstEmail() == null) ? "" : item.getEAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateEmployerRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Employer REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getEREPName() == null) ? "" : item.getEREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getEREPAddress1() == null) ? "" : item.getEREPAddress1().trim()));
        party.setAddress2(((item.getEREPAddress2() == null) ? "" : item.getEREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getEREPCity() == null) ? "" : item.getEREPCity().trim()));
        party.setState(((item.getEREPState() == null) ? "" : item.getEREPState().trim()));
        party.setZip(((item.getEREPZip() == null) ? "" : item.getEREPZip().trim()));
        party.setPhoneOne(((item.getEREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getEREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getEREPEmail() == null) ? "" : item.getEREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEREPAsstName() == null) ? "" : item.getEREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getEREPAsstEmail() == null) ? "" : item.getEREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Employee Organization");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getEOName() == null) ? "" : item.getEOName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getEOAddress1() == null) ? "" : item.getEOAddress1().trim()));
        party.setAddress2(((item.getEOAddress2() == null) ? "" : item.getEOAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getEOCity() == null) ? "" : item.getEOCity().trim()));
        party.setState(((item.getEOState() == null) ? "" : item.getEOState().trim()));
        party.setZip(((item.getEOZip() == null) ? "" : item.getEOZip().trim()));
        party.setPhoneOne(((item.getEOPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getEOPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getEOEmail() == null) ? "" : item.getEOEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEOAsstName() == null) ? "" : item.getEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getEOAsstEmail() == null) ? "" : item.getEOAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Employee Organization REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getEOREPName() == null) ? "" : item.getEOREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getEOREPAddress1() == null) ? "" : item.getEOREPAddress1().trim()));
        party.setAddress2(((item.getEOREPAddress2() == null) ? "" : item.getEOREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getEOREPCity() == null) ? "" : item.getEOREPCity().trim()));
        party.setState(((item.getEOREPState() == null) ? "" : item.getEOREPState().trim()));
        party.setZip(((item.getEOREPZip() == null) ? "" : item.getEOREPZip().trim()));
        party.setPhoneOne(((item.getEOREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getEOREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getEOREPEmail() == null) ? "" : item.getEOREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEOREPAsstName() == null) ? "" : item.getEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getEOREPAsstEmail() == null) ? "" : item.getEOREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREOName() == null) ? "" : item.getREOName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREOAddress1() == null) ? "" : item.getREOAddress1().trim()));
        party.setAddress2(((item.getREOAddress2() == null) ? "" : item.getREOAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREOCity() == null) ? "" : item.getREOCity().trim()));
        party.setState(((item.getREOState() == null) ? "" : item.getREOState().trim()));
        party.setZip(((item.getREOZip() == null) ? "" : item.getREOZip().trim()));
        party.setPhoneOne(((item.getREOPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREOPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREOEmail() == null) ? "" : item.getREOEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREOAsstName() == null) ? "" : item.getREOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREOAsstEmail() == null) ? "" : item.getREOAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREOREPName() == null) ? "" : item.getREOREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREOREPAddress1() == null) ? "" : item.getREOREPAddress1().trim()));
        party.setAddress2(((item.getREOREPAddress2() == null) ? "" : item.getREOREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREOREPCity() == null) ? "" : item.getREOREPCity().trim()));
        party.setState(((item.getREOREPState() == null) ? "" : item.getREOREPState().trim()));
        party.setZip(((item.getREOREPZip() == null) ? "" : item.getREOREPZip().trim()));
        party.setPhoneOne(((item.getREOREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREOREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREOREPEmail() == null) ? "" : item.getREOREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREOREPAsstName() == null) ? "" : item.getREOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREOREPAsstEmail() == null) ? "" : item.getREOREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrg2(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREO2Name() == null) ? "" : item.getREO2Name().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREO2Address1() == null) ? "" : item.getREO2Address1().trim()));
        party.setAddress2(((item.getREO2Address2() == null) ? "" : item.getREO2Address2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREO2City() == null) ? "" : item.getREO2City().trim()));
        party.setState(((item.getREO2State() == null) ? "" : item.getREO2State().trim()));
        party.setZip(((item.getREO2Zip() == null) ? "" : item.getREO2Zip().trim()));
        party.setPhoneOne(((item.getREO2Phone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREO2Phone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREO2Email() == null) ? "" : item.getREO2Email().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO2AsstName() == null) ? "" : item.getREO2AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREO2AsstEmail() == null) ? "" : item.getREO2AsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrg2Rep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREO2REPName() == null) ? "" : item.getREO2REPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREO2REPAddress1() == null) ? "" : item.getREO2REPAddress1().trim()));
        party.setAddress2(((item.getREO2REPAddress2() == null) ? "" : item.getREO2REPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREO2REPCity() == null) ? "" : item.getREO2REPCity().trim()));
        party.setState(((item.getREO2REPState() == null) ? "" : item.getREO2REPState().trim()));
        party.setZip(((item.getREO2REPZip() == null) ? "" : item.getREO2REPZip().trim()));
        party.setPhoneOne(((item.getREO2REPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREO2REPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREO2REPEmail() == null) ? "" : item.getREO2REPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO2REPAsstName() == null) ? "" : item.getREO2REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREO2REPAsstEmail() == null) ? "" : item.getREO2REPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrg3(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREO3Name() == null) ? "" : item.getREO3Name().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREO3Address1() == null) ? "" : item.getREO3Address1().trim()));
        party.setAddress2(((item.getREO3Address2() == null) ? "" : item.getREO3Address2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREO3City() == null) ? "" : item.getREO3City().trim()));
        party.setState(((item.getREO3State() == null) ? "" : item.getREO3State().trim()));
        party.setZip(((item.getREO3Zip() == null) ? "" : item.getREO3Zip().trim()));
        party.setPhoneOne(((item.getREO3Phone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREO3Phone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREO3Email() == null) ? "" : item.getREO3Email().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO3AsstName() == null) ? "" : item.getREO3AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREO3AsstEmail() == null) ? "" : item.getREO3AsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateRivalEmployeeOrg3Rep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Rival Employee Organization REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getREO3REPName() == null) ? "" : item.getREO3REPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getREO3REPAddress1() == null) ? "" : item.getREO3REPAddress1().trim()));
        party.setAddress2(((item.getREO3REPAddress2() == null) ? "" : item.getREO3REPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getREO3REPCity() == null) ? "" : item.getREO3REPCity().trim()));
        party.setState(((item.getREO3REPState() == null) ? "" : item.getREO3REPState().trim()));
        party.setZip(((item.getREO3REPZip() == null) ? "" : item.getREO3REPZip().trim()));
        party.setPhoneOne(((item.getREO3REPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getREO3REPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getREO3REPEmail() == null) ? "" : item.getREO3REPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO3REPAsstName() == null) ? "" : item.getREO3REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getREO3REPAsstEmail() == null) ? "" : item.getREO3REPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateIncumbentEmployeeOrg(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Incumbent Employee Organization");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getIEOName() == null) ? "" : item.getIEOName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getIEOAddress1() == null) ? "" : item.getIEOAddress1().trim()));
        party.setAddress2(((item.getIEOAddress2() == null) ? "" : item.getIEOAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getIEOCity() == null) ? "" : item.getIEOCity().trim()));
        party.setState(((item.getIEOState() == null) ? "" : item.getIEOState().trim()));
        party.setZip(((item.getIEOZip() == null) ? "" : item.getIEOZip().trim()));
        party.setPhoneOne(((item.getIEOPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getIEOPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getIEOEmail() == null) ? "" : item.getIEOEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIEOAsstName() == null) ? "" : item.getIEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getIEOAsstEmail() == null) ? "" : item.getIEOAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateIncumbentEmployeeOrgRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Incumbent Employee Organization REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getIEOREPName() == null) ? "" : item.getIEOREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getIEOREPAddress1() == null) ? "" : item.getIEOREPAddress1().trim()));
        party.setAddress2(((item.getIEOREPAddress2() == null) ? "" : item.getIEOREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getIEOREPCity() == null) ? "" : item.getIEOREPCity().trim()));
        party.setState(((item.getIEOREPState() == null) ? "" : item.getIEOREPState().trim()));
        party.setZip(((item.getIEOREPZip() == null) ? "" : item.getIEOREPZip().trim()));
        party.setPhoneOne(((item.getIEOREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getIEOREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getIEOREPEmail() == null) ? "" : item.getIEOREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIEOREPAsstName() == null) ? "" : item.getIEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getIEOREPAsstEmail() == null) ? "" : item.getIEOREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateIntervener(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Intervener");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getIName() == null) ? "" : item.getIName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getIAddress1() == null) ? "" : item.getIAddress1().trim()));
        party.setAddress2(((item.getIAddress2() == null) ? "" : item.getIAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getICity() == null) ? "" : item.getICity().trim()));
        party.setState(((item.getIState() == null) ? "" : item.getIState().trim()));
        party.setZip(((item.getIZip() == null) ? "" : item.getIZip().trim()));
        party.setPhoneOne(((item.getIPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getIPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getIEmail() == null) ? "" : item.getIEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIAsstName() == null) ? "" : item.getIAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getIAsstEmail() == null) ? "" : item.getIAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateIntervenerRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Intervener REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getIREPName() == null) ? "" : item.getIREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getIREPAddress1() == null) ? "" : item.getIREPAddress1().trim()));
        party.setAddress2(((item.getIREPAddress2() == null) ? "" : item.getIREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getIREPCity() == null) ? "" : item.getIREPCity().trim()));
        party.setState(((item.getIREPState() == null) ? "" : item.getIREPState().trim()));
        party.setZip(((item.getIREPZip() == null) ? "" : item.getIREPZip().trim()));
        party.setPhoneOne(((item.getIREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getIREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getIREPEmail() == null) ? "" : item.getIREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIREPAsstName() == null) ? "" : item.getIREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener REP Assistant");
                party.setLastName(asstName);
                party.setEmailAddress(((item.getIREPAsstEmail() == null) ? "" : item.getIREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateConversionSchool(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Conversion School");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCSName() == null) ? "" : item.getCSName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCSAddress1() == null) ? "" : item.getCSAddress1().trim()));
        party.setAddress2(((item.getCSAddress2() == null) ? "" : item.getCSAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCSCity() == null) ? "" : item.getCSCity().trim()));
        party.setState(((item.getCSState() == null) ? "" : item.getCSState().trim()));
        party.setZip(((item.getCSZip() == null) ? "" : item.getCSZip().trim()));
        party.setPhoneOne(((item.getCSPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCSPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getCSEmail() == null) ? "" : item.getCSEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getCSAsstName() == null) ? "" : item.getCSAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getCSAsstEmail() == null) ? "" : item.getCSAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateConversionSchoolRep(oldREPDataModel item, caseNumberModel caseNumber) {
        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setPartyID(0);
        party.setCaseRelation("Conversion School REP");
        party.setPrefix("");
        party.setFirstName("");
        party.setMiddleInitial("");
        party.setLastName(((item.getCSREPName() == null) ? "" : item.getCSREPName().trim()));
        party.setSuffix("");
        party.setNameTitle("");
        party.setJobTitle("");
        party.setCompanyName("");
        party.setAddress1(((item.getCSREPAddress1() == null) ? "" : item.getCSREPAddress1().trim()));
        party.setAddress2(((item.getCSREPAddress2() == null) ? "" : item.getCSREPAddress2().trim()));
        party.setAddress3("");
        party.setCity(((item.getCSREPCity() == null) ? "" : item.getCSREPCity().trim()));
        party.setState(((item.getCSREPState() == null) ? "" : item.getCSREPState().trim()));
        party.setZip(((item.getCSREPZip() == null) ? "" : item.getCSREPZip().trim()));
        party.setPhoneOne(((item.getCSREPPhone() == null) ? "" : StringUtilities.convertPhoneNumberToString(item.getCSREPPhone().trim())));
        party.setPhoneTwo("");
        party.setEmailAddress(((item.getCSREPEmail() == null) ? "" : item.getCSREPEmail().trim()));

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getCSREPAsstName() == null) ? "" : item.getCSREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne("");
                party.setEmailAddress(((item.getCSREPAsstEmail() == null) ? "" : item.getCSREPAsstEmail().trim()));
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

}
