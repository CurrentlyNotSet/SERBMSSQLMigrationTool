/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.caseNumberModel;
import com.model.userModel;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class StringUtilities {

    public static caseNumberModel parseFullCaseNumber(String fullCaseNumber) {
        caseNumberModel item = new caseNumberModel();

        String[] parsedCaseNumber = fullCaseNumber.split("-");
        item.setCaseYear(parsedCaseNumber[0]);
        item.setCaseType(parsedCaseNumber[1]);
        item.setCaseMonth(parsedCaseNumber[2]);
        item.setCaseNumber(parsedCaseNumber[3]);
        return item;
    }

    public static String generateFullCaseNumber(caseNumberModel caseNumber) {
        return caseNumber.getCaseYear() + "-" + caseNumber.getCaseType()
                + "-" + caseNumber.getCaseMonth() + "-" + caseNumber.getCaseNumber();
    }

    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }

    public static String convertBlobFileToString(Blob text) {
        try {
            byte[] bdata = text.getBytes(1, (int) text.length());
            return new String(bdata).trim();
        } catch (SQLException ex) {
            Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static Timestamp convertStringDate(String oldDate) {
        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate)) {
            String day = "";
            String month = "";
            String year = "";

            oldDate = oldDate.replaceAll("\\\\", "-").replaceAll("/", "-");
            String[] parsedOldDate = oldDate.trim().split("-");

            //  0         1           2
            //year  -   month   -   day
            //year  -   day     -   month
            //month -   day     -   year
            //day   -   month   -   year
            if (parsedOldDate[0].length() == 4) {
                year = parsedOldDate[0];
                month = parsedOldDate[1];
                day = parsedOldDate[2];
                if (Integer.valueOf(parsedOldDate[1]) > 12) {
                    month = parsedOldDate[1];
                    day = parsedOldDate[2];
                } else if (Integer.valueOf(parsedOldDate[2]) > 12) {
                    month = parsedOldDate[2];
                    day = parsedOldDate[1];
                }
            } else if (parsedOldDate[2].length() == 4) {
                month = parsedOldDate[0];
                day = parsedOldDate[1];
                year = parsedOldDate[2];
                if (Integer.valueOf(parsedOldDate[0]) > 12) {
                    month = parsedOldDate[1];
                    day = parsedOldDate[0];
                } else if (Integer.valueOf(parsedOldDate[1]) > 12) {
                    month = parsedOldDate[0];
                    day = parsedOldDate[1];
                }
            } else {
                month = parsedOldDate[0];
                day = parsedOldDate[1];
                year = parsedOldDate[2];
            }
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(year + "-" + month + "-" + day);
                return new Timestamp(parsedDate.getTime());

            } catch (ParseException ex) {
                Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
                return Timestamp.valueOf("1900-01-01 00:00:00.0");
            }
        }
        return null;
    }

    public static String convertLongToTime(long millis) {
        String duration = String.format("%02dhr %02dmin %02dsec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0){
            String[] split = duration.split("hr");
            duration = split[1].trim();
        }        
        return duration.trim();
    }

    public static int convertUserToID(String userEntry) {
        if (!"".equals(userEntry.trim())) {
            for (userModel usr : Global.getUserList()) {
                if (userEntry.toLowerCase().trim().equals(usr.getUserName().toLowerCase().trim())
                        || userEntry.toLowerCase().trim().equals(usr.getInitials().toLowerCase().trim())
                        || (userEntry.toLowerCase().trim().startsWith(usr.getFirstName().toLowerCase().trim())
                        && userEntry.toLowerCase().trim().endsWith(usr.getLastName().toLowerCase().trim()))) {
                    return usr.getId();
                }
            }
        }
        return 0;
    }

}
