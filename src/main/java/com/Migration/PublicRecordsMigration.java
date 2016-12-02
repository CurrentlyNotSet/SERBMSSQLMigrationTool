/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Migration;

import com.model.activityModel;
import com.model.caseNumberModel;
import com.model.oldPublicRecordsModel;
import com.sceneControllers.MainWindowSceneController;
import com.sql.sqlActivity;
import com.sql.sqlMigrationStatus;
import com.sql.sqlPublicRecords;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Andrew
 */
public class PublicRecordsMigration {
    
    public static void migratePublicRecordsData(final MainWindowSceneController control){
        Thread hearingThread = new Thread() {
            @Override
            public void run() {
                hearingsThread(control);
            }
        };
        hearingThread.start();        
    }
    
    private static void hearingsThread(MainWindowSceneController control){
        long lStartTime = System.currentTimeMillis();
        control.setProgressBarIndeterminate("Public Records Migration");
        int totalRecordCount = 0;
        int currentRecord = 0;
        
        List<oldPublicRecordsModel> oldPublicRecords = sqlPublicRecords.getOldPublicRecords();
        
        totalRecordCount = oldPublicRecords.size();
                   
        for (oldPublicRecordsModel item : oldPublicRecords) {
            migrateHistory(item);
            currentRecord = SceneUpdater.listItemFinished(control, currentRecord, totalRecordCount, item.getCaseNumber() + ": " + item.getDocumentName());
        }
                
        long lEndTime = System.currentTimeMillis();
        String finishedText = "Finished Migrating Public Records: " 
                + totalRecordCount + " records in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
        control.setProgressBarDisable(finishedText);
        if (Global.isDebug() == false){
            sqlMigrationStatus.updateTimeCompleted("MigratePublicRecords");
        }
    }
     
    private static void migrateHistory(oldPublicRecordsModel old) {
        caseNumberModel caseNumber = null;
        if (old.getCaseNumber().trim().length() == 16) {
            caseNumber = StringUtilities.parseFullCaseNumber(old.getCaseNumber().trim());
        } else if (old.getCaseNumber().trim().length() == 4){
            caseNumber = new caseNumberModel();
            caseNumber.setCaseYear(null);
            caseNumber.setCaseType("ORG");
            caseNumber.setCaseMonth(null);
            caseNumber.setCaseNumber(old.getCaseNumber());
        }
        
        if(caseNumber != null){
            activityModel item = new activityModel();
            
            if (old.getDateTime().length() > 10){
                String[] dateTime = old.getDateTime().split(" ", 2);
                
                item.setDate(StringUtilities.convertStringDateAndTime(dateTime[0].replaceAll(" ", "").trim(), dateTime[1].replaceAll(" ", "").trim()));
            } else if (old.getDateTime().length() < 10 && old.getDateTime().length() > 1){
                item.setDate(StringUtilities.convertStringTimeStamp(old.getDateTime() + " 00:00:00.000"));
            }
            
            String Comment = "";
            if (!old.getBody().trim().equals("")){
                Comment += "Body: " + old.getBody();
            }
            if (!old.getNotes().trim().equals("")){
                Comment += System.lineSeparator() + System.lineSeparator() + "Notes: " + old.getNotes();
            }
            
            item.setCaseYear(caseNumber.getCaseYear());
            item.setCaseType(caseNumber.getCaseType());
            item.setCaseMonth(caseNumber.getCaseMonth());
            item.setCaseNumber(caseNumber.getCaseNumber());
            item.setUserID(0);
            item.setAction(!"".equals(old.getDocumentName().trim()) ? old.getDocumentName().trim() : null);
            item.setFileName(!"".equals(old.getFileName().trim()) ? FilenameUtils.getName(old.getFileName().trim()) : null);
            item.setFrom(null);
            item.setTo(!"".equals(old.getEmailAddress().trim()) ? old.getEmailAddress().trim() : null);
            item.setType(null);
            item.setComment(Comment.trim().equals("") ? null : Comment.trim());
            item.setRedacted(0);
            item.setAwaitingTimeStamp(0);

            sqlActivity.addActivity(item);
        }
    }

}
