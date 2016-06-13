/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.casePartyModel;

/**
 *
 * @author User
 */
public class ContactNameSeperator {
    
    public static casePartyModel seperateName(casePartyModel item) {
        if (item.getLastName() != null) {
            String[] jobTitleNameSplit = item.getLastName().split(",");
            if (jobTitleMatch(jobTitleNameSplit[jobTitleNameSplit.length - 1].trim())) {
                item.setJobTitle(jobTitleNameSplit[jobTitleNameSplit.length - 1].trim());
                item.setLastName(item.getLastName().replace(item.getJobTitle(), "").trim());
                if (item.getLastName().trim().endsWith(",")) {
                    item.setLastName(item.getLastName().substring(0, item.getLastName().length() - 1));
                }
            }

            if (item.getLastName().startsWith("The Honorable")) {
                item.setPrefix("The Honorable");
                item.setLastName(item.getLastName().replaceFirst("The Honorable", "").trim());
            }

            String[] nameSplit = item.getLastName().replaceAll(", ", " ").split(" ");
            if (nameSplit.length == 2) {
                twoPeiceName(item, nameSplit);
            } else if (nameSplit.length == 3) {
                threePieceName(item, nameSplit);
            } else if (nameSplit.length == 4) {
                fourPieceName(item, nameSplit);
            } else if (nameSplit.length == 5) {
                fivePieceName(item, nameSplit);
            }
            return sanitizeContact(item);
        }
        return item;
    }
    
    private static casePartyModel twoPeiceName(casePartyModel item, String[] nameSplit) {
        if (nameSplit[1].trim().endsWith(".") == false) {
//2p:
            item.setFirstName(nameSplit[0]);
            item.setLastName(nameSplit[1]);
            return item;
        }
        return item;
    }

    private static casePartyModel threePieceName(casePartyModel item, String[] nameSplit) {
        if ((nameSplit[1].trim().length() == 2 && nameSplit[1].trim().endsWith("."))
                || nameSplit[1].trim().length() == 1) {
//3p: MI
            item.setFirstName(nameSplit[0]);
            item.setMiddleInitial(nameSplit[1]);
            item.setLastName(nameSplit[2]);
            return item;
        } else if (prefixMatch(nameSplit[0].trim())) {
//3p: Prefix
            item.setPrefix(nameSplit[0]);
            item.setFirstName(nameSplit[1]);
            item.setLastName(nameSplit[2]);
            return item;
        } else if (nameSplit[2].trim().endsWith(".")) {
//3p: Suffix
            item.setFirstName(nameSplit[0]);
            item.setLastName(nameSplit[1]);
            if (suffixMatch(nameSplit[2])) {
                item.setSuffix(nameSplit[2]);
            } else {
                item.setNameTitle(nameSplit[2]);
            }
            return item;
        }
        return item;
    }

    private static casePartyModel fourPieceName(casePartyModel item, String[] nameSplit) {
        if(prefixMatch(nameSplit[0].trim()) && nameSplit[1].trim().endsWith(".") == false  
                    && nameSplit[3].trim().endsWith(".")){
//4p: Prefix && Suffix                
                item.setPrefix(nameSplit[0]);
                item.setFirstName(nameSplit[1]);
                item.setLastName(nameSplit[2]);
                if (suffixMatch(nameSplit[3])){
                    item.setSuffix(nameSplit[3]);
                } else {
                    item.setNameTitle(nameSplit[3]);
                }
                return item;
            } else if(prefixMatch(nameSplit[0].trim()) && nameSplit[1].trim().endsWith(".") == false 
                    && (nameSplit[2].trim().length() == 2 
                    && nameSplit[2].trim().endsWith(".")) || nameSplit[2].trim().length() == 1){
//4p: Prefix && MI                
                item.setPrefix(nameSplit[0]);
                item.setFirstName(nameSplit[1]);
                item.setMiddleInitial(nameSplit[2]);
                item.setLastName(nameSplit[3]);
                return item;
            } else if ((nameSplit[1].trim().length() == 2 && nameSplit[1].trim().endsWith(".")) 
                    || nameSplit[1].trim().length() == 1 && nameSplit[2].trim().endsWith(",")){
//4p: MI & Suffix 
                item.setFirstName(nameSplit[0]);
                item.setMiddleInitial(nameSplit[1]);
                item.setLastName(nameSplit[2]);
                if (suffixMatch(nameSplit[3])){
                    item.setSuffix(nameSplit[3]);
                } else {
                    item.setNameTitle(nameSplit[3]);
                }
                return item;
            }        
        return item;
    }
    
    private static casePartyModel fivePieceName(casePartyModel item, String[] nameSplit) {
        if ((nameSplit[1].trim().length() == 2 && nameSplit[1].trim().endsWith("."))
                && suffixMatch(nameSplit[3]) && nameTitleMatch(nameSplit[4])) {
//F,MI,L,Suf,NameTitle
            item.setFirstName(nameSplit[0]);
            item.setMiddleInitial(nameSplit[1]);
            item.setLastName(nameSplit[2]);
            item.setSuffix(nameSplit[3]);
            item.setNameTitle(nameSplit[4]);
            return item;
        } else if (prefixMatch(nameSplit[0].trim()) && 
                (nameSplit[2].trim().length() == 2 && nameSplit[2].trim().endsWith("."))
                && suffixMatch(nameSplit[4])) {
//Pre,F,MI,Last,Suffix
            item.setPrefix(nameSplit[0]);
            item.setFirstName(nameSplit[1]);
            item.setMiddleInitial(nameSplit[2]);
            item.setLastName(nameSplit[3]);
            item.setSuffix(nameSplit[4]);            
        }
        return item;
    }
    
    private static boolean prefixMatch(String prefix) {
        if (prefix != null){
            for (String s : Global.namePrefixList) {
                if (s.trim().toUpperCase().equals(prefix.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean suffixMatch(String suffix) {
        if (suffix != null){
            for (String s : Global.suffixList) {
                if (s.trim().toUpperCase().equals(suffix.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean nameTitleMatch(String nameTitle) {
        if (nameTitle != null){
            for (String s : Global.nameTitleList) {
                if (s.trim().toUpperCase().equals(nameTitle.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean jobTitleMatch(String jobTitle) {
        if (jobTitle != null){
            for (String s : Global.jobTitleList) {
                if (s.trim().toUpperCase().equals(jobTitle.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static casePartyModel sanitizeContact(casePartyModel item) {
         if (item.getFirstName() == null){
            item.setCompanyName(item.getLastName());
            item.setLastName(null);
        } 

        if (item.getPrefix() != null) {
            item.setPrefix("".equals(item.getPrefix().trim()) 
                    ? null : item.getPrefix().trim());
            if(prefixMatch(item.getPrefix()) && item.getPrefix().endsWith(".") == false) {
                item.setPrefix(item.getPrefix() + ".");
            }
        }
        if (item.getFirstName() != null) {
            item.setFirstName("".equals(item.getFirstName().trim()) 
                    ? null : item.getFirstName().trim());
        }
        if (item.getMiddleInitial() != null) {
            item.setMiddleInitial("".equals(item.getMiddleInitial().replaceAll("[^A-Za-z]", "").trim()) 
                    ? null : item.getMiddleInitial().replaceAll("[^A-Za-z]", "").trim());
        }
        if (item.getLastName() != null) {
            item.setLastName("".equals(item.getLastName().trim()) 
                    ? null : item.getLastName().trim());
        }
        if (item.getSuffix() != null) {
            item.setSuffix("".equals(item.getSuffix().trim()) 
                    ? null : item.getSuffix().replaceAll("[^A-Za-z.]", "").trim());
        }
        if (item.getNameTitle() != null) {
            item.setNameTitle("".equals(item.getNameTitle().trim()) 
                    ? null : item.getNameTitle().replaceAll("[^A-Za-z./]", "").trim());
        }
        if (item.getJobTitle() != null) {
            item.setJobTitle("".equals(item.getJobTitle().trim()) 
                    ? null : item.getJobTitle().trim());
        }
        if (item.getCompanyName()!= null) {
            item.setCompanyName("".equals(item.getCompanyName().trim()) 
                    ? null : item.getCompanyName().trim());
        }
        if (item.getAddress1() != null) {
            item.setAddress1("".equals(item.getAddress1().trim()) 
                    ? null : item.getAddress1().trim());
        }
        if (item.getAddress2() != null) {
            item.setAddress2("".equals(item.getAddress2().trim()) 
                    ? null : item.getAddress2().trim());
        }
        if (item.getCity() != null) {
            item.setCity("".equals(item.getCity().trim()) 
                    ? null : item.getCity().trim());
        }
        if (item.getState() != null) {
            item.setState("".equals(item.getState().trim()) 
                    ? null : item.getState().trim());
        }
        if (item.getZip() != null) {
            item.setZip("".equals(item.getZip().trim()) 
                    ? null : item.getZip().trim());
        }
        if (item.getPhoneOne() != null) {
            item.setPhoneOne("".equals(item.getPhoneOne().trim()) 
                    ? null : item.getPhoneOne().trim());
        }
        if (item.getPhoneTwo() != null) {
            item.setPhoneTwo("".equals(item.getPhoneTwo().trim()) 
                    ? null : item.getPhoneTwo().trim());
        }
        if (item.getEmailAddress() != null) {
            item.setEmailAddress("".equals(item.getEmailAddress().trim()) 
                    ? null : item.getEmailAddress().trim());
        }
        return item;
     }
    
}
