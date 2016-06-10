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
public class historyTypeModel {
    
    private int id;
    private int active;
    private String type;
    private String Section;
    private String fileAttrib;
    private String HistoryDescription;

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

    public String getFileAttrib() {
        return fileAttrib;
    }

    public void setFileAttrib(String fileAttrib) {
        this.fileAttrib = fileAttrib;
    }

    public String getHistoryDescription() {
        return HistoryDescription;
    }

    public void setHistoryDescription(String HistoryDescription) {
        this.HistoryDescription = HistoryDescription;
    }
    
    
}
