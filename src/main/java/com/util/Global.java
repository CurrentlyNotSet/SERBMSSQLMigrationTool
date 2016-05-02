/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.userModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class Global {
    
    private static final boolean debug = true;
    
    private static final SimpleDateFormat mmddyyyyhhmmssa = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    
    private static List<userModel> userList;

    public static List<userModel> getUserList() {
        return userList;
    }

    public static void setUserList(List<userModel> userList) {
        Global.userList = userList;
    }
    
    public static SimpleDateFormat getMmddyyyyhhmmssa() {
        return mmddyyyyhhmmssa;
    }

    public static boolean isDebug() {
        return debug;
    }
    
}
