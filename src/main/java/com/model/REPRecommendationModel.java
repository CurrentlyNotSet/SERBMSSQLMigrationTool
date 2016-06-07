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
public class REPRecommendationModel {
    
    private int REPRecID;
    private int active;
    private String type;
    private String recommendation;

    public int getREPRecID() {
        return REPRecID;
    }

    public void setREPRecID(int REPRecID) {
        this.REPRecID = REPRecID;
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

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
    
    
}
