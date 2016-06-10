/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.userModel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    public static List<String> namePrefixList = Arrays.asList(
            "Ms.", "Miss.", "Mrs.", "Messr.", "Messrs.", "Mses.", "Mr.", "Rev.",
            "Fr.", "Dr.", "Atty.", "Prof.", "Hon.", "The Honorable", "Pres.", 
            "Gov.", "Coach.", "Ofc.", "Supt.", "Captain", "Capt.", "Rep.", 
            "Sen.", "Treas.", "Sec.", "Adm."
    );

    public static List<String> suffixList = Arrays.asList(
            "II", "III", "IV", "V", "Jr.", "Sr.", "Attorney"
    );

    public static List<String> nameTitleList = Arrays.asList(
            "Esq", "Esq.", "Esquire"
    );

    public static List<String> jobTitleList = Arrays.asList(
            "3rd District VP", "3rd District Vice President", "4th District Vice President",
            "Account Manager", "Acting Director", "Administrator", "Administrative Deputy",
            "Administrative Organizer", "Administrative Services Coordinator", "Administratve Organizer",
            "Asst. Prosecuting Attorney", "Assistant Attorney General", "Assistant Attorney Generals",
            "Assistant City Administrator", "Assistant City Attorney", "Assistant City Solicitor",
            "Assistant Directing Business Representative", "Assistant Director Human Resources",
            "Assistant Director Human Resourses", "Assistant General Counsel", "Assistant Prosecutor",
            "Assistant Prosecuting Attorney", "Assistant Director", "Assistant Director Human Resources",
            "Assistant Director of Human Resources", "Assistant Director of Law", "Assistant Law Director",
            "Assistant Law Diretor", "Assistant Solicitor", "Assistant Superintendent",
            "Assistant Vice President", "Assistan Law Director", "Assitant City Attorney",
            "Assitant City Solicitor", "Assitant Law Director", "Associate Counsel", "Associate Counsel II",
            "Associate General Counsel", "Associate Legal Counsel", "Attorney", "Attorney at Law",
            "Attorneys", "BA. Agent", "Board Attorney", "Board Counsel", "Board Legal Counsel",
            "Busienss Manager", "Buisness Manager", "Business Agent", "Business Manager",
            "Business Representative", "CEO", "Chairman UAW Local 3056", "Chapter President",
            "Chief – Labor & Employment Section", "Chief Counsel", "Chief Counsel – Labor & Employment",
            "Chief Counsel – Labor & Employment Section", "Chief Counsel – Labor and Employment",
            "Chief Assistant Directgor of Law", "Chief Assistant Director", "Chief Assistant Director of Law",
            "Chief Assistant Law Director", "Chief Assistant Prosecuting Attorney",
            "Chief Human Resources Officer", "Chief Legal Counsel", "Chief Negotiator", "City Attorney",
            "City Administrator", "City Manager", "Clerk of Courts", "Cleveland Regional Vice President",
            "Commissioner", "Consultant", "Counsel", "Counsel for Toledo Public Schools", "County Administrator",
            "County Engineer", "CWA Staff Representative", "Deputy Chief Counsel", "Deputy Chief Legal Counsel",
            "Deputy Chief Director of Labor Relations",
            "Deputy Executive Director/GeneralCounsel/Director of Human Resources", "Deputy Mayor", "Director",
            "Director Employee/Labor Relations", "Director of Administration",
            "Director of Emergency Medical Service- Meigs County EMS", "Director of Finance/Treasurer",
            "Director of Human Resources", "Director of HR & Purchasing", "Director of Labor Relations",
            "Director of Law", "Director of Legal Services", "Director of Mgmt Services", "Director of Operations",
            "Director of Public Safety", "Director of Public Safety and Service", "Director of Research",
            "Director of Support Services", "District Organizing Committee", "District Organizing Coordinator",
            "District Representative", "District State Organizer", "Division Director", "Employee Relations Administrator",
            "Engineer", "et al", "et al.", "Executive Transformation Leader of Operations", "Executive Director",
            "Field Representative", "Field Services Coordinator", "Fire Chief", "Fiscal Officer",
            "Fiscal Officer/Acting Director", "General Counsel", "General Councel", "General Council", "General Counsel TPS",
            "GMP Executive Officer", "Grand Lodge Representative", "Grievance Chair", "Health Commissioner",
            "hHman Resources", "Highway Superintendent", "HR Admin", "HR Director", "HR Manager", "HR Officer",
            "Human Resource Manager", "Human Resources Administration", "Human Resources & Purchasing Director",
            "Human Resources and Administration", "Human Resources Director", "Human Resources Manager",
            "Human Resources Officer", "IBEW International Representative", "Interim Administrator",
            "Interim Executive Director", "Interim President", "Interim Superintendent", "International Representative",
            "Labor and Employment Chief", "Labor and Employment Section Chief", "Labor Counsel", "Labor Council",
            "Labor Cousel", "Labor Counsel/Policy Analyst", "Labor Director", "Labor Relations Administrator",
            "Labor Relations & Policy Dev.", "Labor Relations Consultant", "Labor Relations Deputy", "Labor Relations Manager",
            "Labor Relations Representative", "Labor Section Chief", "Labor/Employee Relations Compliance Advisor", "Law Clerk",
            "Law Director", "Legal Counsel", "Manager", "Manager of Adm. Practice", "Manager of Administrative Practice",
            "Manager of Administrative Practices", "Manager of Human Resources", "Managing Associate General Counsel",
            "Mayor", "National Representative", "OAPSE Director of Legal Services", "OAPSE Field Representative",
            "OAPSE Staff Attorney", "OAPSE Sattf Attorney", "OEA Labor Relations Consultant", "OEA Organizer",
            "OFT Staff Representative", "Office of Laboer Relarions", "Office Manager", "Office Manager – Payroll/Human Resources",
            "OPBA Attorney", "Organizer", "Organizing Counsel", "Organizing Director", "Paralegal/Operations Director",
            "Personal Supervisor", "Personnel Director", "Personnel Supervisor", "Police Chief", "Policy Analyst",
            "Policy Administrator", "President", "President of Cincinnati School Board", "President/Business Agent",
            "Prosecuting Attorney", "Public Division Director", "Public Division Manager", "Public Service & Safety Director",
            "Region 3 National Representative", "Regional Director", "Regional III Director", "Representative", "Safety Director",
            "Safety Service Director", "Secretary-Treasurer/Business Representative", "Section Chief",
            "Senior Assistant Attorney General", "Senior Attorney", "Senior Consultant", "Senior Director Labor Relations",
            "Senior Director of Labor Relations", "Senior Employee Relations Director", "Senior Human Resources Manager",
            "Senior Labor Counsel", "Senior Legal Counsel", "Senior Staff Attorney", "Service Director", "Service Director & Superintendent",
            "Service Representative", "Special Counsel", "Sheriff", "Site Rep", "Sr. Assistant City Attorney",
            "Sr. Assistant City Solicitor", "Sr. Director", "Staff Attorney", "Staff Representative", "State Coordinator",
            "Sub District Director", "Superintendent", "Technical Director", "Township Administrator", "Trustee", "Transformational Leader",
            "UE General Counsel", "Union President", "Union Representative", "USW Staff Representative", "Vice President",
            "Vice President of HR", "Vice President/Business Agent", "Vice Provost for Academic Personnel", "VP for Legal Affairs",
            "VP of Human Resources", "VP of Human Resources & Labor Relations", "WEA President"
    );

}
