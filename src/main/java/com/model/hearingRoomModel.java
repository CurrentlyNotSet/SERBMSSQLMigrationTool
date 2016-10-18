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
public class hearingRoomModel {
    private int id;
    private boolean active;
    private String roomAbbreviation;
    private String roomName;
    private String roomEmail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoomAbbreviation() {
        return roomAbbreviation;
    }

    public void setRoomAbbreviation(String roomAbbreviation) {
        this.roomAbbreviation = roomAbbreviation;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomEmail() {
        return roomEmail;
    }

    public void setRoomEmail(String roomEmail) {
        this.roomEmail = roomEmail;
    }
    
    
}
