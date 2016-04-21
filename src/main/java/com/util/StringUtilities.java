/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.caseNumberModel;

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
    
    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }
}
