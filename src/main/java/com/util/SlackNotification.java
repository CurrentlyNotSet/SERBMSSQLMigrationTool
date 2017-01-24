/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author parkerjohnston
 */
public class SlackNotification {
    
    public static void sendBasicNotification(String message) {
        try {            
            new Slack(SlackInfo.getSLACK_HOOK())
                .icon(SlackInfo.getSLACK_ICON()) // Ref - http://www.emoji-cheat-sheet.com/
                .sendToChannel(SlackInfo.getSLACK_CHANNEL())
                .displayName(SlackInfo.getSLACK_USER())
                .push(new SlackMessage(message)); 
        } catch (IOException ex) {
            //leave blank
        }
    }
    
    public static void sendNotification(Exception ex) {
        System.out.println(convertStackTrace(ex));
        try {
            String message = "MIGRATION TOOL" + "\n" ;
            message += "Class Name: " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n";
            message += "Method Name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n";
            message += "Exception Type: " + ex.getClass().getSimpleName() + "\n";
//            message += "Stack Trace: " + convertStackTrace(ex);
            
            new Slack(SlackInfo.getSLACK_HOOK())
                .icon(SlackInfo.getSLACK_ICON()) // Ref - http://www.emoji-cheat-sheet.com/
                .sendToChannel(SlackInfo.getSLACK_CHANNEL())
                .displayName(SlackInfo.getSLACK_USER())
                .push(new SlackMessage(message)); 
        } catch (IOException ex1) {
            //leave blank
        }
    }
    
    private static String convertStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
