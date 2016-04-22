/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Blob;

/**
 *
 * @author Andrew
 */
public class oldBlobFileModel {

    private int BlobFileid;
    private int active;
    private String type;
    private String Section;
    private int Caseid;
    private String CaseNumber;
    private String SelectorA;
    private String SelectorB;
    private int Sequence;
    private Blob BlobData;

    public int getBlobFileid() {
        return BlobFileid;
    }

    public void setBlobFileid(int BlobFileid) {
        this.BlobFileid = BlobFileid;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String Section) {
        this.Section = Section;
    }

    public int getCaseid() {
        return Caseid;
    }

    public void setCaseid(int Caseid) {
        this.Caseid = Caseid;
    }

    public String getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(String CaseNumber) {
        this.CaseNumber = CaseNumber;
    }

    public String getSelectorA() {
        return SelectorA;
    }

    public void setSelectorA(String SelectorA) {
        this.SelectorA = SelectorA;
    }

    public String getSelectorB() {
        return SelectorB;
    }

    public void setSelectorB(String SelectorB) {
        this.SelectorB = SelectorB;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int Sequence) {
        this.Sequence = Sequence;
    }

    public Blob getBlobData() {
        return BlobData;
    }

    public void setBlobData(Blob BlobData) {
        this.BlobData = BlobData;
    }

    
}
