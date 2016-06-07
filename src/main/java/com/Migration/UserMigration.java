/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlMigrationStatus;
import com.sql.sqlRole;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class UserMigration {
    
    public static void migrateUserData(MainWindowSceneController control){
        Thread userThread = new Thread() {
            @Override
            public void run() {
                userThread(control);
            }
        };
        userThread.start();        
    }
    
    private static void userThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Users Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        List<userModel> oldSecUserList = sqlUsers.getSecUsers();
        List<userModel> oldUserList = sqlUsers.getUsers();
        List<String> roleList = Arrays.asList("Admin", "Docketing", "REP", "ULP", "ORG", "MED", "Employer Search");
        
        totalRecordCount = oldUserList.size() + oldSecUserList.size() + roleList.size();
        
        for (String item : roleList){
            sqlRole.addUserRole(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item);
        }
        
        for (userModel item : oldSecUserList){
            migrateUser(item);
            currentRecord++;
            if (Global.isDebug()){
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getUserName().trim() + ")");
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getUserName());
        }

        for (userModel item : oldUserList) {
            boolean missingEntry = true;
            for (userModel secitem : oldSecUserList){
                if (secitem.getUserName().trim() == null ? item.getUserName().trim() == null : secitem.getUserName().trim().equalsIgnoreCase(item.getUserName().trim())){
                    missingEntry = false;
                    break;
                }
            }
            if (missingEntry) {
                migrateUser(item);
            }
            currentRecord++;
            if (Global.isDebug()) {
                System.out.println("Current Record Number Finished:  " + currentRecord + "  (" + item.getUserName().trim() + ")");
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getUserName());
        }

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Users: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateUsers");
        }
        
    }
        
    private static void migrateUser(userModel item){
        if ("".equals(item.getFirstName().trim()) && "".equals(item.getMiddleInitial().trim())){
            item = seperateName(item);
        }
        sqlUsers.saveUserInformation(seperateName(item));
    }
        
    private static userModel seperateName(userModel item) {
        String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
        
        if (nameSplit.length == 2 && nameSplit[1].trim().endsWith(".") == false){
            item.setFirstName(nameSplit[0].trim());
            item.setMiddleInitial("");
            item.setLastName(nameSplit[1].trim());
            return item;
        } else if (nameSplit.length == 3) {
            if ((nameSplit[1].trim().length() == 2 && nameSplit[1].trim().endsWith(".")) 
                    || nameSplit[1].trim().length() == 1){
                item.setFirstName(nameSplit[0].trim());
                item.setMiddleInitial(nameSplit[1].replaceAll("\\.", "").trim());
                item.setLastName(nameSplit[2].trim());
                return item;
            }
        }
        return item;
    }
    
}
