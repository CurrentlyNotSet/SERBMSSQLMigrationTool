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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class UserMigration {

    private static int totalRecordCount = 0;
    private static int currentRecord = 0;
    private static MainWindowSceneController control;
    private static List<userModel> userList = new ArrayList<>();

    public static void migrateUserData(MainWindowSceneController control) {
        Thread userThread = new Thread() {
            @Override
            public void run() {
                userThread(control);
            }
        };
        userThread.start();
    }

    public static void userThread(MainWindowSceneController controlPassed) {
        try {
            long lStartTime = System.currentTimeMillis();
            control = controlPassed;
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            control.setProgressBarIndeterminate("Users Migration");
            totalRecordCount = 0;
            currentRecord = 0;
            userList = sqlUsers.getSecUsers();
            List<userModel> oldUserList = sqlUsers.getUsers();
            if (Global.isDebug()){
                System.out.println("Gathered Users");
            }
            List<oldALJModel> oldALJList = sqlALJ.getOldALJList();
            if (Global.isDebug()){
                System.out.println("Gathered ALJs");
            }
            
            List<String> roleList = Arrays.asList(
                    "Admin", "Docketing", "REP", "ULP", "ORG", "MED", "Employer Search", "Civil Service Commission", "CMDS", "Hearings"
            );
            
            totalRecordCount = oldUserList.size() + userList.size() + roleList.size() + oldALJList.size();
            
            sqlRole.batchAddUserRole(roleList);
            currentRecord = SceneUpdater.listItemFinished(control, roleList.size() + currentRecord, totalRecordCount, "Roles Finished");
            Thread.sleep(Global.getSLEEP());
            
            //Clean User Data
            oldUserList.stream().forEach(item -> executor.submit(() -> cleanOLDUser(item)));
            oldALJList.stream().forEach(item -> executor.submit(() -> cleanALJ(item)));
            
            executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {
            }
            
            currentRecord = 0;
            sqlUsers.batchAddUserInformation(userList, control, currentRecord, totalRecordCount);
            currentRecord = SceneUpdater.listItemFinished(control, userList.size() + currentRecord, totalRecordCount, "Users Finished");
            Thread.sleep(Global.getSLEEP());
            
            userList.clear();
            
            sqlUsers.getNewDBUsers();
            long lEndTime = System.currentTimeMillis();
            String finishedText = "Finished Migrating Users: "
                    + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
            control.setProgressBarDisable(finishedText);
            if (Global.isDebug() == false) {
                sqlMigrationStatus.updateTimeCompleted("MigrateUsers");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(UserMigration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void cleanOLDUser(userModel item) {
        boolean missingEntry = true;
        for (userModel newUsers : userList) {
            if (newUsers.getUserName().trim() == null ? item.getUserName().trim() == null
                    : newUsers.getUserName().trim().equalsIgnoreCase(item.getUserName().trim())) {
                missingEntry = false;
                break;
            }
        }
        if (missingEntry) {
            userList.add(item);
        }
    }

    private static void cleanALJ(oldALJModel item) {
        boolean missingEntry = true;
        for (userModel newUsers : userList) {
            if (newUsers.getUserName().trim() == null ? item.getUsername().trim() == null
                    : newUsers.getUserName().trim().equalsIgnoreCase(item.getUsername().trim())) {
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

            userList.add(user);
        }
    }

}
