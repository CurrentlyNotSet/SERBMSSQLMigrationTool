/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Timestamp;

/**
 *
 * @author Andrew
 */
public class migrationStatusModel {
    
    private int id;
    private Timestamp migrateULPCases;
    private Timestamp migrateMEDCases;
    private Timestamp migrateREPCases;
    private Timestamp migrateCSCCases;
    private Timestamp migrateCMDSCases;
    private Timestamp migrateContacts;
    private Timestamp MigrateORGCase;
    private Timestamp MigrateUsers;
    private Timestamp MigrateDocuments;
    private Timestamp MigrateHearingsCases;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getMigrateULPCases() {
        return migrateULPCases;
    }

    public void setMigrateULPCases(Timestamp migrateULPCases) {
        this.migrateULPCases = migrateULPCases;
    }

    public Timestamp getMigrateMEDCases() {
        return migrateMEDCases;
    }

    public void setMigrateMEDCases(Timestamp migrateMEDCases) {
        this.migrateMEDCases = migrateMEDCases;
    }

    public Timestamp getMigrateREPCases() {
        return migrateREPCases;
    }

    public void setMigrateREPCases(Timestamp migrateREPCases) {
        this.migrateREPCases = migrateREPCases;
    }

    public Timestamp getMigrateCSCCases() {
        return migrateCSCCases;
    }

    public void setMigrateCSCCases(Timestamp migrateCSCCases) {
        this.migrateCSCCases = migrateCSCCases;
    }

    public Timestamp getMigrateCMDSCases() {
        return migrateCMDSCases;
    }

    public void setMigrateCMDSCases(Timestamp migrateCMDSCases) {
        this.migrateCMDSCases = migrateCMDSCases;
    }

    public Timestamp getMigrateContacts() {
        return migrateContacts;
    }

    public void setMigrateContacts(Timestamp migrateContacts) {
        this.migrateContacts = migrateContacts;
    }

    public Timestamp getMigrateORGCase() {
        return MigrateORGCase;
    }

    public void setMigrateORGCase(Timestamp MigrateORGCase) {
        this.MigrateORGCase = MigrateORGCase;
    }

    public Timestamp getMigrateUsers() {
        return MigrateUsers;
    }

    public void setMigrateUsers(Timestamp MigrateUsers) {
        this.MigrateUsers = MigrateUsers;
    }

    public Timestamp getMigrateDocuments() {
        return MigrateDocuments;
    }

    public void setMigrateDocuments(Timestamp MigrateDocuments) {
        this.MigrateDocuments = MigrateDocuments;
    }

    public Timestamp getMigrateHearingsCases() {
        return MigrateHearingsCases;
    }

    public void setMigrateHearingsCases(Timestamp MigrateHearingsCases) {
        this.MigrateHearingsCases = MigrateHearingsCases;
    }
    
}
