/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.oldALJModel;
import com.model.userModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlALJ;
import com.sql.sqlMigrationStatus;
import com.sql.sqlRole;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrew
 */
public class UserMigration {
    
    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    
    public static void migrateUserData(MainWindowSceneController control){
        Thread userThread = new Thread() {
            @Override
            public void run() {
                userThread(control);
            }
        };
        userThread.start();        
    }
    
    public static void userThread(MainWindowSceneController controlPassed){
        long lStartTime = System.currentTimeMillis();
        control = controlPassed;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        control.setProgressBarIndeterminate("Users Migration");
        totalRecordCount = 0;
        currentRecord = 0;
        List<userModel> oldSecUserList = sqlUsers.getSecUsers();
        List<userModel> oldUserList = sqlUsers.getUsers();
        List<oldALJModel> oldALJList = sqlALJ.getOldALJList();
        
        List<String> roleList = Arrays.asList(
                "Admin", "Docketing", "REP", "ULP", "ORG", "MED", "Employer Search", "Civil Service Commission", "CMDS"
        );
        
        totalRecordCount = oldUserList.size() + oldSecUserList.size() + roleList.size() + oldALJList.size();
                
        sqlRole.addUserRole(roleList);
        currentRecord = SceneUpdater.listItemFinished(control, roleList.size() + currentRecord, totalRecordCount, "Roles Finished");
        
        sqlUsers.batchAddUserInformation(oldSecUserList);
        currentRecord = SceneUpdater.listItemFinished(control, oldSecUserList.size() + currentRecord, totalRecordCount, "Sec Users Finished");

        Global.setUserList(sqlUsers.getUsers());

        //Insert ULP Case Data
        oldUserList.stream().forEach(item -> 
                executor.submit(() -> 
                        migrateOldUsers(item)));
        
        Global.setUserList(sqlUsers.getUsers());
        oldALJList.stream().forEach(item -> 
                executor.submit(() -> 
                        migrateALJ(item)));
        
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }

        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Users: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigrateUsers");
        }   
    }
        
    private static void migrateOldUsers(userModel item ){
        boolean missingEntry = true;
            for (userModel newUsers : Global.getUserList()){
                if (newUsers.getUserName().trim() == null ? item.getUserName().trim() == null : newUsers.getUserName().trim().equalsIgnoreCase(item.getUserName().trim())){
                    missingEntry = false;
                    break;
                }
            }
            if (missingEntry) {
                sqlUsers.saveUserInformation(seperateName(item));
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getUserName());
    }

    private static void migrateALJ(oldALJModel item){
        boolean missingEntry = true;
            for (userModel newUsers : Global.getUserList()){
                if (newUsers.getUserName().trim() == null ? item.getUsername().trim() == null : newUsers.getUserName().trim().equalsIgnoreCase(item.getUsername().trim())){
                    missingEntry = false;
                    break;
                }
            }
            if (missingEntry) {
                userModel user = new userModel();
        
                user.setActive(item.getActive());
                user.setInitials(item.getInitials().equals("") ? null : item.getInitials().trim());
                user.setLastName(item.getName().trim());
                user.setUserName(item.getUsername());
                user.setEmail(item.getEmail().trim().equals("") ? null : item.getEmail().trim());

                user.setWorkPhone((!item.getOfficePhone().trim().equals("null") || !item.getOfficePhone().trim().equals("")) 
                        ? StringUtilities.convertPhoneNumberToString(item.getOfficePhone().trim()) : null);

                sqlUsers.saveUserInformation(seperateName(user));
            }
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getUsername());
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
