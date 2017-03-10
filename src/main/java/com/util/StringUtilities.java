/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.caseNumberModel;
import com.model.casePartyModel;
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
import java.util.regex.Pattern;

/**
 *
 * @author Andrew
 */
public class StringUtilities {

    public static caseNumberModel parseFullCaseNumber(String fullCaseNumber) {
        caseNumberModel item = new caseNumberModel();

        String[] parsedCaseNumber = fullCaseNumber.split("-");
        if (parsedCaseNumber.length == 4){
            item.setCaseYear(parsedCaseNumber[0]);
            item.setCaseType(parsedCaseNumber[1]);
            item.setCaseMonth(parsedCaseNumber[2]);
            item.setCaseNumber(parsedCaseNumber[3]);
            return item;
        } else {
            return null;
        }
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
                if (!"".equals(blobString.trim()) || !"null".equals(blobString.trim())) {
                    return blobString.trim();
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                return null;
            }
        }
        return null;
    }

    public static Timestamp convertStringTimeStamp(String oldDate) {
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
                    SlackNotification.sendNotification(ex);
                    return null;
                }
            }
        }
        return null;
    }

    public static java.sql.Time convertStringSQLTime(String oldTime) {
        if (oldTime != null && !"".equals(oldTime)) {
            String[] splitOldTime = oldTime.split(":");
            int hour = Integer.valueOf(splitOldTime[0].replaceAll("\\D+", ""));
            int minute = Integer.valueOf(splitOldTime[1].replaceAll("\\D+", ""));
            String AMPM = "";

            if (hour <= 12 && minute <= 60) {
                if (7 <= hour && hour <= 12) {
                    AMPM = "AM";
                } else {
                    AMPM = "PM";
                }
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                    Date parsedDate = (Date)formatter.parse(hour + ":" + minute + ":00 " + AMPM);
                    return new java.sql.Time(parsedDate.getTime());
                } catch (ParseException ex) {
                    SlackNotification.sendNotification(ex);
                    return null;
                }
            }
        }
        return null;
    }

    public static java.sql.Date convertStringSQLDate(String oldDate) {
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
                    return new java.sql.Date(parsedDate.getTime());
                } catch (ParseException ex) {
                    SlackNotification.sendNotification(ex);
                    return null;
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

            String[] time = oldTime.replaceAll("\\.", "").split(";|:| ");

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.valueOf(date.getYear()));
            cal.set(Calendar.MONTH, Integer.valueOf(date.getMonth()));
            cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.getDay()));

            if (!"".equals(oldTime.trim()) && time.length > 1) {

                int hour = Integer.valueOf(time[0].replaceAll("\\D+", ""));
                if (7 <= hour && hour <= 12) {
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, hour + 12);
                }
                cal.set(Calendar.MINUTE, Integer.valueOf(time[1].replaceAll("\\D+", "")));
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

    public static Timestamp convertStringDateAndTimeORG(String oldDate, String oldTime) {
        Pattern p = Pattern.compile("[a-zA-Z]");

        oldDate = oldDate.trim();
        if (oldDate != null && !"".equals(oldDate) && p.matcher(oldDate).find() == false) {
            String[] date = oldDate.split("-");

            String[] time = oldTime.replaceAll("\\.", "").split(";|:| ");

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.valueOf(date[0]));
            cal.set(Calendar.MONTH, Integer.valueOf(date[1]));
            cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date[2]));

            if (!"".equals(oldTime.trim()) && time.length > 1) {

                int hour = Integer.valueOf(time[0].replaceAll("\\D+", ""));
                if (7 <= hour && hour <= 12) {
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, hour + 12);
                }
                cal.set(Calendar.MINUTE, Integer.valueOf(time[1].replaceAll("\\D+", "")));
            } else {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
            }

            String[] timesecond = time[2].split(".");
            cal.set(Calendar.SECOND, Integer.valueOf(timesecond[0]));
            cal.set(Calendar.MILLISECOND, Integer.valueOf(timesecond[1]));

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
                    date.setMonth(parsedOldDate[2]);
                    date.setDay(parsedOldDate[1]);
                } else if (Integer.valueOf(parsedOldDate[2]) > 12) {
                    date.setMonth(parsedOldDate[1]);
                    date.setDay(parsedOldDate[2]);
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
            if (Integer.parseInt(date.getYear()) > 1752 && Integer.parseInt(date.getYear()) < 9999){
                return date;
            }

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
                if (Integer.parseInt(date.getYear()) > 1752 && Integer.parseInt(date.getYear()) < 9999) {
                    return date;
                }
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

    public static int convertUserInitialToID(String userEntry) {
        if (!"".equals(userEntry.trim())) {
            for (userModel usr : Global.getUserList()) {
                if (usr.getInitials().length() > 0) {
                    if (userEntry.toLowerCase().trim().startsWith(usr.getInitials().toLowerCase().trim().substring(0, 1))
                            && userEntry.toLowerCase().trim().endsWith(usr.getInitials().toLowerCase().trim().substring(usr.getInitials().length() - 1))) {
                        return usr.getId();
                    }
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

    public static String monthNumber(String month){
        if (month != null){
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
        }
        return null;
    }

    public static String monthName(String month){
        if (month != null){
            if (       month.startsWith("01")){
                return "January";
            } else if (month.startsWith("02")){
                return "February";
            } else if (month.startsWith("03")){
                return "March";
            } else if (month.startsWith("04")){
                return "April";
            } else if (month.startsWith("05")){
                return "May";
            } else if (month.startsWith("06")){
                return "June";
            } else if (month.startsWith("07")){
                return "July";
            } else if (month.startsWith("08")){
                return "August";
            } else if (month.startsWith("09")){
                return "September";
            } else if (month.startsWith("10")){
                return "October";
            } else if (month.startsWith("11")){
                return "November";
            } else if (month.startsWith("12")){
                return "December";
            }
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

    public static Time convertToSQLTime(String time){
        String numbers = TimeNumbers(time);
        String ampm = AMPM(time);
        String hour = "";


        if (numbers != null && ampm != null) {
            String[] numbersSplit = numbers.split(":");

            int hourBlock = Integer.valueOf(numbersSplit[0]);
            if (hourBlock < 12){
                hour = String.valueOf(ampm.equalsIgnoreCase("AM") ? hourBlock : hourBlock + 12);
            } else {
                hour = String.valueOf(hourBlock);
            }

            return java.sql.Time.valueOf(hour + ":" + numbersSplit[1] + ":00");
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
        time = time.replaceAll("[^a-zA-Z]", "").trim();

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

    public static String joinNameTogether(casePartyModel party){
        String name = "";


        if(party.getPrefix() != null){
            name += party.getPrefix().trim() + " ";
        }
        if (party.getFirstName() != null){
            name += party.getFirstName().trim() + " ";
        }
        if(party.getMiddleInitial() != null){
            name += party.getMiddleInitial().trim() + ". ";
        }
        if(party.getLastName()!= null){
            name += party.getLastName().trim() + " ";
        }
        if(party.getSuffix()!= null){
            name += party.getSuffix().trim() + " ";
        }
        if(party.getNameTitle()!= null){
            name += party.getNameTitle().trim() + " ";
        }
        if(party.getJobTitle()!= null){
            name += party.getJobTitle().trim() + " ";
        }
        if(party.getCompanyName()!= null){
            name += party.getCompanyName().trim();
        }
        return name.trim();
    }

    public static String buildFullName(String first, String middle, String last) {
        String fullName = "";
        if (!first.equals("")) {
            fullName += first.trim();
        }
        if (!middle.equals("")) {
            fullName += " " + (middle.trim().length() == 1 ? middle.trim() + "." : middle.trim());
        }
        if (!last.equals("")) {
            fullName += " " + last.trim();
        }
        return fullName.trim();
    }

}
