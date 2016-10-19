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
public class CMDSResultModel {
    private int ResultsID;
    private boolean Active;
    private String Result;
    private String Description;

    public int getResultsID() {
        return ResultsID;
    }

    public void setResultsID(int ResultsID) {
        this.ResultsID = ResultsID;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
}
