/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author Andrew
 */
public class barginingUnitModel {
    private int id;
    private int Active;
    private String EmployerNumber;
    private String UnitNumber;
    private String Cert;
    private String BUEmployerName;
    private String Jurisdiction;
    private String County;
    private String LUnion;
    private String Local;
    private int Strike;
    private String LGroup;
    private Date CertDate;
    private int Enabled;
    private String CaseRefYear;
    private String CaseRefSection;
    private String CaseRefMonth;
    private String CaseRefSequence;
    private String UnitDescription;
    private String Notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }

    public String getEmployerNumber() {
        return EmployerNumber;
    }

    public void setEmployerNumber(String EmployerNumber) {
        this.EmployerNumber = EmployerNumber;
    }

    public String getUnitNumber() {
        return UnitNumber;
    }

    public void setUnitNumber(String UnitNumber) {
        this.UnitNumber = UnitNumber;
    }

    public String getCert() {
        return Cert;
    }

    public void setCert(String Cert) {
        this.Cert = Cert;
    }

    public String getBUEmployerName() {
        return BUEmployerName;
    }

    public void setBUEmployerName(String BUEmployerName) {
        this.BUEmployerName = BUEmployerName;
    }

    public String getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(String Jurisdiction) {
        this.Jurisdiction = Jurisdiction;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String County) {
        this.County = County;
    }

    public String getLUnion() {
        return LUnion;
    }

    public void setLUnion(String LUnion) {
        this.LUnion = LUnion;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String Local) {
        this.Local = Local;
    }

    public int getStrike() {
        return Strike;
    }

    public void setStrike(int Strike) {
        this.Strike = Strike;
    }

    public String getLGroup() {
        return LGroup;
    }

    public void setLGroup(String LGroup) {
        this.LGroup = LGroup;
    }

    public Date getCertDate() {
        return CertDate;
    }

    public void setCertDate(Date CertDate) {
        this.CertDate = CertDate;
    }

    public int getEnabled() {
        return Enabled;
    }

    public void setEnabled(int Enabled) {
        this.Enabled = Enabled;
    }

    public String getCaseRefYear() {
        return CaseRefYear;
    }

    public void setCaseRefYear(String CaseRefYear) {
        this.CaseRefYear = CaseRefYear;
    }

    public String getCaseRefSection() {
        return CaseRefSection;
    }

    public void setCaseRefSection(String CaseRefSection) {
        this.CaseRefSection = CaseRefSection;
    }

    public String getCaseRefMonth() {
        return CaseRefMonth;
    }

    public void setCaseRefMonth(String CaseRefMonth) {
        this.CaseRefMonth = CaseRefMonth;
    }

    public String getCaseRefSequence() {
        return CaseRefSequence;
    }

    public void setCaseRefSequence(String CaseRefSequence) {
        this.CaseRefSequence = CaseRefSequence;
    }

    public String getUnitDescription() {
        return UnitDescription;
    }

    public void setUnitDescription(String UnitDescription) {
        this.UnitDescription = UnitDescription;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }
    
}
