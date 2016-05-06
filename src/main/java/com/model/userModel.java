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
public class userModel {
    
    private int id;
    private String userName;
    private int active;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String initials;
    private String email;
    private String workPhone;
    private int passwordSalt;
    private String password;
    private Timestamp lastLoginDateTime;
    private String lastLoginPCName;
    private boolean activeLogin;
    private boolean passwordReset;
    private String applicationVersion;
    private String defaultSection;
    private boolean ULPCaseWorker;
    private boolean mediator;
    private boolean REPDocketing;
    private boolean ULPDocketing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public int getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(int passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(Timestamp lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public String getLastLoginPCName() {
        return lastLoginPCName;
    }

    public void setLastLoginPCName(String lastLoginPCName) {
        this.lastLoginPCName = lastLoginPCName;
    }

    public boolean isActiveLogin() {
        return activeLogin;
    }

    public void setActiveLogin(boolean activeLogin) {
        this.activeLogin = activeLogin;
    }

    public boolean isPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getDefaultSection() {
        return defaultSection;
    }

    public void setDefaultSection(String defaultSection) {
        this.defaultSection = defaultSection;
    }

    public boolean isULPCaseWorker() {
        return ULPCaseWorker;
    }

    public void setULPCaseWorker(boolean ULPCaseWorker) {
        this.ULPCaseWorker = ULPCaseWorker;
    }

    public boolean isMediator() {
        return mediator;
    }

    public void setMediator(boolean mediator) {
        this.mediator = mediator;
    }

    public boolean isREPDocketing() {
        return REPDocketing;
    }

    public void setREPDocketing(boolean REPDocketing) {
        this.REPDocketing = REPDocketing;
    }

    public boolean isULPDocketing() {
        return ULPDocketing;
    }

    public void setULPDocketing(boolean ULPDocketing) {
        this.ULPDocketing = ULPDocketing;
    }
    
    
    
}
