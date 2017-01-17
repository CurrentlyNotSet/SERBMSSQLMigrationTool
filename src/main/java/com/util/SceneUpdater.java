/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.sceneControllers.MainWindowSceneController;

/**
 *
 * @author User
 */
public class SceneUpdater {
    
    public static int listItemFinished(MainWindowSceneController control, int currentRecord, int totalRecordCount, String printoutText) {
        currentRecord++;
        if (Global.isDebug()) {
            System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + printoutText + ")");
        }
        control.updateProgressBarProcessing(Double.valueOf(currentRecord), totalRecordCount);
        return currentRecord;
    }
    
    public static int listItemCleaned(MainWindowSceneController control, int currentRecord, int totalRecordCount, String printoutText) {
        currentRecord++;
        if (Global.isDebug()) {
            System.out.println("Current Record Number Cleaned:  " + currentRecord + "  (" + printoutText + ")");
        }
        control.updateProgressBarCleaning(Double.valueOf(currentRecord), totalRecordCount);
        return currentRecord;
    }
    
}
