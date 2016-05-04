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
        Thread repThread = new Thread() {
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
            if (Global.isDebug()) {
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getCaseNumber().trim() + ")");
            }
            control.updateProgressBar(Double.valueOf(currentRecord), totalRecordCount);
        }
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating REP Cases: "
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false) {
            sqlMigrationStatus.updateTimeCompleted("MigrateREPCases");
        }
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
        migrateCaseData(item, caseNumber);
    }

    private static void migratePetitioner(oldREPDataModel item, caseNumberModel caseNumber) {

        casePartyModel party = new casePartyModel();
        party.setCaseYear(caseNumber.getCaseYear());
        party.setCaseType(caseNumber.getCaseType());
        party.setCaseMonth(caseNumber.getCaseMonth());
        party.setCaseNumber(caseNumber.getCaseNumber());
        party.setCaseRelation("Petitioner");
        party.setLastName(!"".equals(item.getPName().trim()) ? item.getPName().trim() : null);
        party.setAddress1(!"".equals(item.getPAddress1().trim()) ? item.getPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getPAddress2().trim()) ? item.getPAddress2().trim() : null);
        party.setCity(!"".equals(item.getPCity().trim()) ? item.getPCity().trim() : null);
        party.setState(!"".equals(item.getPState().trim()) ? item.getPState().trim() : null);
        party.setZip(!"".equals(item.getPZip().trim()) ? item.getPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getPPhone().trim()) ? item.getPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getPEmail().trim()) ? item.getPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getPAsstName() == null) ? "" : item.getPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getPAsstEmail().trim()) ? item.getPAsstEmail().trim() : null);
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
        party.setCaseRelation("Petitioner REP");
        party.setLastName(!"".equals(item.getPREPName().trim()) ? item.getPREPName().trim() : null);
        party.setAddress1(!"".equals(item.getPREPAddress1().trim()) ? item.getPREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getPREPAddress2().trim()) ? item.getPREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getPREPCity().trim()) ? item.getPREPCity().trim() : null);
        party.setState(!"".equals(item.getPREPState().trim()) ? item.getPREPState().trim() : null);
        party.setZip(!"".equals(item.getPREPZip().trim()) ? item.getPREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getPREPPhone().trim()) ? item.getPREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getPREPEmail().trim()) ? item.getPREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getPREPAsstName() == null) ? "" : item.getPREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Petitioner REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getPREPAsstEmail().trim()) ? item.getPREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Employer");
        party.setLastName(!"".equals(item.getEName().trim()) ? item.getEName().trim() : null);
        party.setAddress1(!"".equals(item.getEAddress1().trim()) ? item.getEAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEAddress2().trim()) ? item.getEAddress2().trim() : null);
        party.setCity(!"".equals(item.getECity().trim()) ? item.getECity().trim() : null);
        party.setState(!"".equals(item.getEState().trim()) ? item.getEState().trim() : null);
        party.setZip(!"".equals(item.getEZip().trim()) ? item.getEZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEPhone().trim()) ? item.getEPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getEEmail().trim()) ? item.getEEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEAsstName() == null) ? "" : item.getEAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEAsstEmail().trim()) ? item.getEAsstEmail().trim() : null);
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
        party.setCaseRelation("Employer REP");
        party.setLastName(!"".equals(item.getEREPName().trim()) ? item.getEREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEREPAddress1().trim()) ? item.getEREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEREPAddress2().trim()) ? item.getEREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEREPCity().trim()) ? item.getEREPCity().trim() : null);
        party.setState(!"".equals(item.getEREPState().trim()) ? item.getEREPState().trim() : null);
        party.setZip(!"".equals(item.getEREPZip().trim()) ? item.getEREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEREPPhone().trim()) ? item.getEREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getEREPEmail().trim()) ? item.getEREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEREPAsstName() == null) ? "" : item.getEREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employer REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEREPAsstEmail().trim()) ? item.getEREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Employee Organization");
        party.setLastName(!"".equals(item.getEOName().trim()) ? item.getEOName().trim() : null);
        party.setAddress1(!"".equals(item.getEOAddress1().trim()) ? item.getEOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEOAddress2().trim()) ? item.getEOAddress2().trim() : null);
        party.setCity(!"".equals(item.getEOCity().trim()) ? item.getEOCity().trim() : null);
        party.setState(!"".equals(item.getEOState().trim()) ? item.getEOState().trim() : null);
        party.setZip(!"".equals(item.getEOZip().trim()) ? item.getEOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEOPhone().trim()) ? item.getEOPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getEOEmail().trim()) ? item.getEOEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEOAsstName() == null) ? "" : item.getEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEOAsstEmail().trim()) ? item.getEOAsstEmail().trim() : null);
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
        party.setCaseRelation("Employee Organization REP");
        party.setLastName(!"".equals(item.getEOREPName().trim()) ? item.getEOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getEOREPAddress1().trim()) ? item.getEOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getEOREPAddress2().trim()) ? item.getEOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getEOREPCity().trim()) ? item.getEOREPCity().trim() : null);
        party.setState(!"".equals(item.getEOREPState().trim()) ? item.getEOREPState().trim() : null);
        party.setZip(!"".equals(item.getEOREPZip().trim()) ? item.getEOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getEOREPPhone().trim()) ? item.getEOREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getEOREPEmail().trim()) ? item.getEOREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getEOREPAsstName() == null) ? "" : item.getEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getEOREPAsstEmail().trim()) ? item.getEOREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization");
        party.setLastName(!"".equals(item.getREOName().trim()) ? item.getREOName().trim() : null);
        party.setAddress1(!"".equals(item.getREOAddress1().trim()) ? item.getREOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREOAddress2().trim()) ? item.getREOAddress2().trim() : null);
        party.setCity(!"".equals(item.getREOCity().trim()) ? item.getREOCity().trim() : null);
        party.setState(!"".equals(item.getREOState().trim()) ? item.getREOState().trim() : null);
        party.setZip(!"".equals(item.getREOZip().trim()) ? item.getREOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREOPhone().trim()) ? item.getREOPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREOEmail().trim()) ? item.getREOEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREOAsstName() == null) ? "" : item.getREOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREOAsstEmail().trim()) ? item.getREOAsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization REP");
        party.setLastName(!"".equals(item.getREOREPName().trim()) ? item.getREOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getREOREPAddress1().trim()) ? item.getREOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREOREPAddress2().trim()) ? item.getREOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREOREPCity().trim()) ? item.getREOREPCity().trim() : null);
        party.setState(!"".equals(item.getREOREPState().trim()) ? item.getREOREPState().trim() : null);
        party.setZip(!"".equals(item.getREOREPZip().trim()) ? item.getREOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREOREPPhone().trim()) ? item.getREOREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREOREPEmail().trim()) ? item.getREOREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREOREPAsstName() == null) ? "" : item.getREOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREOREPAsstEmail().trim()) ? item.getREOREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization");
        party.setLastName(!"".equals(item.getREO2Name().trim()) ? item.getREO2Name().trim() : null);
        party.setAddress1(!"".equals(item.getREO2Address1().trim()) ? item.getREO2Address1().trim() : null);
        party.setAddress2(!"".equals(item.getREO2Address2().trim()) ? item.getREO2Address2().trim() : null);
        party.setCity(!"".equals(item.getREO2City().trim()) ? item.getREO2City().trim() : null);
        party.setState(!"".equals(item.getREO2State().trim()) ? item.getREO2State().trim() : null);
        party.setZip(!"".equals(item.getREO2Zip().trim()) ? item.getREO2Zip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO2Phone().trim()) ? item.getREO2Phone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREO2Email().trim()) ? item.getREO2Email().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO2AsstName() == null) ? "" : item.getREO2AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO2AsstEmail().trim()) ? item.getREO2AsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization REP");
        party.setLastName(!"".equals(item.getREO2REPName().trim()) ? item.getREO2REPName().trim() : null);
        party.setAddress1(!"".equals(item.getREO2REPAddress1().trim()) ? item.getREO2REPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREO2REPAddress2().trim()) ? item.getREO2REPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREO2REPCity().trim()) ? item.getREO2REPCity().trim() : null);
        party.setState(!"".equals(item.getREO2REPState().trim()) ? item.getREO2REPState().trim() : null);
        party.setZip(!"".equals(item.getREO2REPZip().trim()) ? item.getREO2REPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO2REPPhone().trim()) ? item.getREO2REPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREO2REPEmail().trim()) ? item.getREO2REPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO2REPAsstName() == null) ? "" : item.getREO2REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO2REPAsstEmail().trim()) ? item.getREO2REPAsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization");
        party.setLastName(!"".equals(item.getREO3Name().trim()) ? item.getREO3Name().trim() : null);
        party.setAddress1(!"".equals(item.getREO3Address1().trim()) ? item.getREO3Address1().trim() : null);
        party.setAddress2(!"".equals(item.getREO3Address2().trim()) ? item.getREO3Address2().trim() : null);
        party.setCity(!"".equals(item.getREO3City().trim()) ? item.getREO3City().trim() : null);
        party.setState(!"".equals(item.getREO3State().trim()) ? item.getREO3State().trim() : null);
        party.setZip(!"".equals(item.getREO3Zip().trim()) ? item.getREO3Zip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO3Phone().trim()) ? item.getREO3Phone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREO3Email().trim()) ? item.getREO3Email().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO3AsstName() == null) ? "" : item.getREO3AsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO3AsstEmail().trim()) ? item.getREO3AsstEmail().trim() : null);
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
        party.setCaseRelation("Rival Employee Organization REP");
        party.setLastName(!"".equals(item.getREO3REPName().trim()) ? item.getREO3REPName().trim() : null);
        party.setAddress1(!"".equals(item.getREO3REPAddress1().trim()) ? item.getREO3REPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getREO3REPAddress2().trim()) ? item.getREO3REPAddress2().trim() : null);
        party.setCity(!"".equals(item.getREO3REPCity().trim()) ? item.getREO3REPCity().trim() : null);
        party.setState(!"".equals(item.getREO3REPState().trim()) ? item.getREO3REPState().trim() : null);
        party.setZip(!"".equals(item.getREO3REPZip().trim()) ? item.getREO3REPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getREO3REPPhone().trim()) ? item.getREO3REPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getREO3REPEmail().trim()) ? item.getREO3REPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getREO3REPAsstName() == null) ? "" : item.getREO3REPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Rival Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getREO3REPAsstEmail().trim()) ? item.getREO3REPAsstEmail().trim() : null);
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
        party.setCaseRelation("Incumbent Employee Organization");
        party.setLastName(!"".equals(item.getIEOName().trim()) ? item.getIEOName().trim() : null);
        party.setAddress1(!"".equals(item.getIEOAddress1().trim()) ? item.getIEOAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIEOAddress2().trim()) ? item.getIEOAddress2().trim() : null);
        party.setCity(!"".equals(item.getIEOCity().trim()) ? item.getIEOCity().trim() : null);
        party.setState(!"".equals(item.getIEOState().trim()) ? item.getIEOState().trim() : null);
        party.setZip(!"".equals(item.getIEOZip().trim()) ? item.getIEOZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIEOPhone().trim()) ? item.getIEOPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getIEOEmail().trim()) ? item.getIEOEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIEOAsstName() == null) ? "" : item.getIEOAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIEOAsstEmail().trim()) ? item.getIEOAsstEmail().trim() : null);
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
        party.setCaseRelation("Incumbent Employee Organization REP");
        party.setLastName(!"".equals(item.getIEOREPName().trim()) ? item.getIEOREPName().trim() : null);
        party.setAddress1(!"".equals(item.getIEOREPAddress1().trim()) ? item.getIEOREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIEOREPAddress2().trim()) ? item.getIEOREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getIEOREPCity().trim()) ? item.getIEOREPCity().trim() : null);
        party.setState(!"".equals(item.getIEOREPState().trim()) ? item.getIEOREPState().trim() : null);
        party.setZip(!"".equals(item.getIEOREPZip().trim()) ? item.getIEOREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIEOREPPhone().trim()) ? item.getIEOREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getIEOREPEmail().trim()) ? item.getIEOREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIEOREPAsstName() == null) ? "" : item.getIEOREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Incumbent Employee Organization REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIEOREPAsstEmail().trim()) ? item.getIEOREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Intervener");
        party.setLastName(!"".equals(item.getIName().trim()) ? item.getIName().trim() : null);
        party.setAddress1(!"".equals(item.getIAddress1().trim()) ? item.getIAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIAddress2().trim()) ? item.getIAddress2().trim() : null);
        party.setCity(!"".equals(item.getICity().trim()) ? item.getICity().trim() : null);
        party.setState(!"".equals(item.getIState().trim()) ? item.getIState().trim() : null);
        party.setZip(!"".equals(item.getIZip().trim()) ? item.getIZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIPhone().trim()) ? item.getIPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getIEmail().trim()) ? item.getIEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIAsstName() == null) ? "" : item.getIAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIAsstEmail().trim()) ? item.getIAsstEmail().trim() : null);
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
        party.setCaseRelation("Intervener REP");
        party.setLastName(!"".equals(item.getIREPName().trim()) ? item.getIREPName().trim() : null);
        party.setAddress1(!"".equals(item.getIREPAddress1().trim()) ? item.getIREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getIREPAddress2().trim()) ? item.getIREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getIREPCity().trim()) ? item.getIREPCity().trim() : null);
        party.setState(!"".equals(item.getIREPState().trim()) ? item.getIREPState().trim() : null);
        party.setZip(!"".equals(item.getIREPZip().trim()) ? item.getIREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getIREPPhone().trim()) ? item.getIREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getIREPEmail().trim()) ? item.getIREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getIREPAsstName() == null) ? "" : item.getIREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Intervener REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getIREPAsstEmail().trim()) ? item.getIREPAsstEmail().trim() : null);
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
        party.setCaseRelation("Conversion School");
        party.setLastName(!"".equals(item.getCSName().trim()) ? item.getCSName().trim() : null);
        party.setAddress1(!"".equals(item.getCSAddress1().trim()) ? item.getCSAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCSAddress2().trim()) ? item.getCSAddress2().trim() : null);
        party.setCity(!"".equals(item.getCSCity().trim()) ? item.getCSCity().trim() : null);
        party.setState(!"".equals(item.getCSState().trim()) ? item.getCSState().trim() : null);
        party.setZip(!"".equals(item.getCSZip().trim()) ? item.getCSZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCSPhone().trim()) ? item.getCSPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getCSEmail().trim()) ? item.getCSEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getCSAsstName() == null) ? "" : item.getCSAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getCSAsstEmail().trim()) ? item.getCSAsstEmail().trim() : null);
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
        party.setCaseRelation("Conversion School REP");
        party.setLastName(!"".equals(item.getCSREPName().trim()) ? item.getCSREPName().trim() : null);
        party.setAddress1(!"".equals(item.getCSREPAddress1().trim()) ? item.getCSREPAddress1().trim() : null);
        party.setAddress2(!"".equals(item.getCSREPAddress2().trim()) ? item.getCSREPAddress2().trim() : null);
        party.setCity(!"".equals(item.getCSREPCity().trim()) ? item.getCSREPCity().trim() : null);
        party.setState(!"".equals(item.getCSREPState().trim()) ? item.getCSREPState().trim() : null);
        party.setZip(!"".equals(item.getCSREPZip().trim()) ? item.getCSREPZip().trim() : null);
        party.setPhoneOne(!"".equals(item.getCSREPPhone().trim()) ? item.getCSREPPhone().trim() : null);
        party.setEmailAddress(!"".equals(item.getCSREPEmail().trim()) ? item.getCSREPEmail().trim() : null);

        if (!"".equals(party.getLastName()) || !"".equals(party.getAddress1()) || !"".equals(party.getEmailAddress()) || !"".equals(party.getPhoneOne())) {
            sqlCaseParty.savePartyInformation(party);

            String asstName = ((item.getCSREPAsstName() == null) ? "" : item.getCSREPAsstName().trim());
            if (!"".equals(asstName)) {
                party.setCaseRelation("Conversion School REP Assistant");
                party.setLastName(asstName);
                party.setPhoneOne(null);
                party.setEmailAddress(!"".equals(item.getCSREPAsstEmail().trim()) ? item.getCSREPAsstEmail().trim() : null);
                sqlCaseParty.savePartyInformation(party);
            }
        }
    }

    private static void migrateCaseData(oldREPDataModel item, caseNumberModel caseNumber) {

    }

}
