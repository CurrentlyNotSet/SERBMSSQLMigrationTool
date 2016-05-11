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
public class oldREPHistoryModel {
    private int HistoryID;
    private int Active;
    private String UserInitals;
    private Timestamp Date;
    private String Action;
    private String CaseNumber;
    private String FileName;
    private String ParseDate;
    private String EmailTo;
    private String EmailFrom;
    private String MailLogDate;
    private String Note;
    private String Approved;
    private String Requested;
    private String Redacted;
    private String RedactedHistoryID;

    public int getHistoryID() {
        return HistoryID;
    }

    public void setHistoryID(int HistoryID) {
        this.HistoryID = HistoryID;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }

    public String getUserInitals() {
        return UserInitals;
    }

    public void setUserInitals(String UserInitals) {
        this.UserInitals = UserInitals;
    }

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp Date) {
        this.Date = Date;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public String getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(String CaseNumber) {
        this.CaseNumber = CaseNumber;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getParseDate() {
        return ParseDate;
    }

    public void setParseDate(String ParseDate) {
        this.ParseDate = ParseDate;
    }

    public String getEmailTo() {
        return EmailTo;
    }

    public void setEmailTo(String EmailTo) {
        this.EmailTo = EmailTo;
    }

    public String getEmailFrom() {
        return EmailFrom;
    }

    public void setEmailFrom(String EmailFrom) {
        this.EmailFrom = EmailFrom;
    }

    public String getMailLogDate() {
        return MailLogDate;
    }

    public void setMailLogDate(String MailLogDate) {
        this.MailLogDate = MailLogDate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String Approved) {
        this.Approved = Approved;
    }

    public String getRequested() {
        return Requested;
    }

    public void setRequested(String Requested) {
        this.Requested = Requested;
    }

    public String getRedacted() {
        return Redacted;
    }

    public void setRedacted(String Redacted) {
        this.Redacted = Redacted;
    }

    public String getRedactedHistoryID() {
        return RedactedHistoryID;
    }

    public void setRedactedHistoryID(String RedactedHistoryID) {
        this.RedactedHistoryID = RedactedHistoryID;
    }
        
    
}
