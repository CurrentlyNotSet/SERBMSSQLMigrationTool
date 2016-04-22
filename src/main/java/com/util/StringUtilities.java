/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.caseNumberModel;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
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
    
    public static String convertBlobFileToString(Blob text){
        try {
            byte[] bdata = text.getBytes(1, (int) text.length());
            String s = new String(bdata);
            return s.trim();
        } catch (SQLException ex) {
            Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
