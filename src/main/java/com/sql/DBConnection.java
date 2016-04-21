/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.util.AlertDialog;
import com.util.DBCInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Andrew
 */
public class DBConnection {

    public static Connection connectToDB(String DBName) {
        Connection conn = null;
        int nbAttempts = 0;
        while (true) {
            try {
                Class.forName(DBCInfo.getDBdriver());
                conn = DriverManager.getConnection(DBCInfo.getDBurl() + DBName, DBCInfo.getDBusername(), DBCInfo.getDBpassword());
                break;
            } catch (ClassNotFoundException | SQLException e) {
                nbAttempts++;
                System.out.println();
                if (nbAttempts == 2) {
                    AlertDialog.StaticAlert(3, "Warning", "Database Connection Error",
                            "Unable to connect to server. Please verify network connection "
                            + "and press OK to try again.");
                }
                try {
                    Thread.sleep(3000);
                } catch (Exception exi) {
                    System.err.println(exi.getMessage());
                }
                if (nbAttempts == 3) {
                    AlertDialog.StaticAlert(4, "Error", "Database Connection Error",
                            "Unable to connect to server. Information could not be saved. "
                            + "The system will now exit.");
                    System.exit(0);
                }
            }
        }
        return conn;
    }

}
