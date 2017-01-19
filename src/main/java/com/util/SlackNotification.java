/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import java.io.IOException;

/**
 *
 * @author parkerjohnston
 */
public class SlackNotification {
    
    public static void sendNotification(String message) {
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
}
