/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.caseNumberModel;
import com.model.dateModel;
import com.model.startTimeEndTimeModel;
import com.model.userModel;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Time;
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
                if (!"".equals(blobString.trim())) {
                    return blobString.trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return null;
    }

    public static Timestamp convertStringDate(String oldDate) {
        Pattern p = Pattern.compile("[a-zA-Z]");

        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate)) {
            dateModel date = null;

            if (p.matcher(oldDate).find() == false) {
                date = dateParseNumbers(oldDate);
            } else {
                date = dateParseFullSpelling(oldDate);
            }

            if (date != null) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
                    return new Timestamp(parsedDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    return Timestamp.valueOf("1900-01-01 00:00:00.0");
                }
            }
        }
        return null;
    }

    public static Timestamp convertStringDateAndTime(String oldDate, String oldTime) {
        Pattern p = Pattern.compile("[a-zA-Z]");

        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate) && p.matcher(oldDate).find() == false) {
            dateModel date = dateParseNumbers(oldDate);

            String[] time = oldTime.replaceAll("\\.", "").split(":| ");

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.valueOf(date.getYear()));
            cal.set(Calendar.MONTH, Integer.valueOf(date.getMonth()));
            cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.getDay()));

            if (!"".equals(oldTime.trim())) {
                int hour = Integer.valueOf(time[0]);
                if (hour < 12) {
                    cal.set(Calendar.HOUR_OF_DAY, time[2].equalsIgnoreCase("AM") ? hour : hour + 12);
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                }
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

    private static dateModel dateParseNumbers(String oldDate) {
        dateModel date = new dateModel();

        oldDate = oldDate.replaceAll("\\\\", "-").replaceAll("/", "-");
        String[] parsedOldDate = oldDate.trim().split("-");
        if (parsedOldDate.length == 3) {
            //  0         1           2
            //year  -   month   -   day
            //year  -   day     -   month
            //month -   day     -   year
            //day   -   month   -   year
            if (parsedOldDate[0].length() == 4) {
                date.setYear(parsedOldDate[0]);
                date.setMonth(parsedOldDate[1]);
                date.setDay(parsedOldDate[2]);
                if (Integer.valueOf(parsedOldDate[1]) > 12) {
                    date.setMonth(parsedOldDate[1]);
                    date.setDay(parsedOldDate[2]);
                } else if (Integer.valueOf(parsedOldDate[2]) > 12) {
                    date.setMonth(parsedOldDate[2]);
                    date.setDay(parsedOldDate[1]);
                }
            } else if (parsedOldDate[2].length() == 4) {
                date.setMonth(parsedOldDate[0]);
                date.setDay(parsedOldDate[1]);
                date.setYear(parsedOldDate[2]);
                if (Integer.valueOf(parsedOldDate[0]) > 12) {
                    date.setMonth(parsedOldDate[1]);
                    date.setDay(parsedOldDate[0]);
                } else if (Integer.valueOf(parsedOldDate[1]) > 12) {
                    date.setMonth(parsedOldDate[0]);
                    date.setDay(parsedOldDate[1]);
                }
            } else {
                date.setMonth(parsedOldDate[0]);
                date.setDay(parsedOldDate[1]);
                date.setYear(parsedOldDate[2]);
            }

            if (date.getMonth().length() == 1) {
                date.setMonth("0" + date.getMonth());
            }
            if (date.getDay().length() == 1) {
                date.setDay("0" + date.getDay());
            }
            if (date.getYear().length() == 2) {
                if (Integer.parseInt(date.getYear()) > 20) {
                    date.setYear("19" + date.getYear());
                } else {
                    date.setYear("20" + date.getYear());
                }
            }
            return date;
        }
        return null;
    }

    private static dateModel dateParseFullSpelling(String oldDate) {
        dateModel date = new dateModel();
        if (monthMatch(oldDate)) {
            String[] dateSplit = oldDate.split(" ");

            if (dateSplit.length == 3) {

                date.setMonth(monthNumber(dateSplit[0]));
                date.setDay(dateSplit[1].replaceAll("[^0-9]", ""));
                date.setYear(dateSplit[2].replaceAll("[^0-9]", ""));

                if (date.getDay().length() == 1) {
                    date.setDay("0" + date.getDay());
                }
                if (date.getYear().length() == 2) {
                    if (Integer.parseInt(date.getYear()) > 20) {
                        date.setYear("19" + date.getYear());
                    } else {
                        date.setYear("20" + date.getYear());
                    }
                }
                return date;
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
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0) {
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

    private static boolean monthMatch(String month) {
        if (month != null){
            for (String s : Global.getMonthList()) {
                if (month.toUpperCase().startsWith(s.toUpperCase())){
                    return true;
                }
            }
        }
        return false;
    }
    
    private static String monthNumber(String month){
        if (       month.startsWith("Jan")){
            return "01";
        } else if (month.startsWith("Feb")){
            return "02";
        } else if (month.startsWith("Mar")){
            return "03";
        } else if (month.startsWith("Apr")){
            return "04";
        } else if (month.startsWith("May")){
            return "05";
        } else if (month.startsWith("Jun")){
            return "06";
        } else if (month.startsWith("Jul")){
            return "07";
        } else if (month.startsWith("Aug")){
            return "08";
        } else if (month.startsWith("Sep")){
            return "09";
        } else if (month.startsWith("Oct")){
            return "10";
        } else if (month.startsWith("Nov")){
            return "11";
        } else if (month.startsWith("Dec")){
            return "12";
        }
        return null;
    }
    
    public static startTimeEndTimeModel splitTime(String time){
        startTimeEndTimeModel sTeT = new startTimeEndTimeModel();
        
        String[] timeSplit = time.split("-");
        if (timeSplit.length == 2){
            sTeT.setStartTime(convertToSQLTime(timeSplit[0]));
            sTeT.setEndTime(convertToSQLTime(timeSplit[1]));
        }
        return sTeT;
    }
    
    private static Time convertToSQLTime(String time){
        Calendar cal = Calendar.getInstance();
        String numbers = TimeNumbers(time);
        String ampm = AMPM(time);
        
        if (numbers != null && ampm != null) {
            String[] nunmbersSplit = numbers.split(":");
            
            int hour = Integer.valueOf(nunmbersSplit[0]);
            if (hour < 12){
                cal.set(Calendar.HOUR_OF_DAY, ampm.equalsIgnoreCase("AM") ? hour : hour + 12);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, hour);
            }
            cal.set(Calendar.MINUTE, Integer.valueOf(nunmbersSplit[1]));
            
            return java.sql.Time.valueOf(Global.getHhmmss().format(cal.getTime()));
        }
        return null;
    }
    
    private static String TimeNumbers(String time) {
        time = time.replaceAll("[^0-9]", "").trim();
        if (time.length() == 3){
            time = "0" + time;
        }
        if (time.length() == 4){
            return new StringBuilder(time).insert(time.length()-2, ":").toString();
        }
        return null;
    }
    
    private static String AMPM(String time) {
        time = time.replaceAll("a-zA-Z", "").trim();
        
        if(time.toUpperCase().startsWith("A")){
            return "AM";
        } else if (time.toUpperCase().startsWith("P") || time.toUpperCase().startsWith("N")){
            return "PM";
        }
        return null;
    }
    
    public static int parseStringtoInt(String number) {
        String numberCheck = number.replaceAll("[0-9]", "").trim();
        if ("".equals(numberCheck.trim())) {
            return Integer.parseInt(number);
        }
        return -1;
    }

}
