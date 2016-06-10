/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author User
 */
public class oldDocumentModel {
    
    private int documentID;
    private int active;
    private String type;
    private String section;
    private String documentDescription;
    private String documentFileName;
    private String bodyFileName;
    private String subjectFileName;
    private String attachmentListFileName;

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
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
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getBodyFileName() {
        return bodyFileName;
    }

    public void setBodyFileName(String bodyFileName) {
        this.bodyFileName = bodyFileName;
    }

    public String getSubjectFileName() {
        return subjectFileName;
    }

    public void setSubjectFileName(String subjectFileName) {
        this.subjectFileName = subjectFileName;
    }

    public String getAttachmentListFileName() {
        return attachmentListFileName;
    }

    public void setAttachmentListFileName(String attachmentListFileName) {
        this.attachmentListFileName = attachmentListFileName;
    }
    
    
    
}
