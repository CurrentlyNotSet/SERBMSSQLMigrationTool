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
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
        if (text != null) {
            try {
                byte[] bdata = text.getBytes(1, (int) text.length());
                String blobString = new String(bdata).trim();
                if (!"".equals(blobString.trim())){
                    return blobString.trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Timestamp convertStringDate(String oldDate) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        
        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate) && p.matcher(oldDate).find() == false) {
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
            if (month.length() == 1){
                month = "0" + month;
            }
            if (day.length() == 1){
                day = "0" + day;
            }
            if (year.length() == 2){
                if (Integer.parseInt(year) > 20){
                    year = "19" + year;
                } else{
                    year = "20" + year;
                }
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

    public static Timestamp convertStringDateAndTime(String oldDate, String oldTime) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        
        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate) && p.matcher(oldDate).find() == false) {
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
            
            String[] time = oldTime.replaceAll("\\.", "").split(":| ");
                            
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.valueOf(year));
                cal.set(Calendar.MONTH, Integer.valueOf(month));
                cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
                
                if(!"".equals(oldTime.trim())){
                    int hour = Integer.valueOf(time[0]);
                    cal.set(Calendar.HOUR_OF_DAY, time[2].equalsIgnoreCase("AM") ? hour : hour + 12);
                    cal.set(Calendar.MINUTE, Integer.valueOf(time[1]));
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                }
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
        
                cal.getTimeInMillis();
                return new Timestamp(cal.getTimeInMillis());
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
