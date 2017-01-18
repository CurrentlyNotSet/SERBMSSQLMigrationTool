/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.casePartyModel;
import com.util.DBCInfo;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlCMDSCaseParty {
    
    public static List<casePartyModel> getPartyList() {
        List<casePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT [case].[month], [case].type, caseparticipants.* "
                    + "FROM caseparticipants LEFT JOIN [case] ON "
                    + "[case].[year] = caseparticipants.[year] AND "
                    + "[case].[CaseSeqNumber] = caseparticipants.[CaseSeqNumber]";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("Active") == 1){
                    casePartyModel item = new casePartyModel();
                    item.setId(rs.getInt("CaseParticipantsID"));
                    item.setCaseYear(rs.getString("Year"));
                    item.setCaseMonth(rs.getString("Month"));
                    item.setCaseType(rs.getString("Type"));
                    item.setCaseNumber(rs.getString("CaseSeqNumber"));
                    item.setCaseRelation(rs.getString("ParticipantType"));
                    item.setLastName(rs.getString("LastName"));
                    item.setFirstName(rs.getString("FirstName"));
                    item.setMiddleInitial(rs.getString("MiddleInitial"));
                    item.setJobTitle(rs.getString("Title"));
                    item.setAddress1(rs.getString("Address1"));
                    item.setAddress2(rs.getString("Address2"));
                    item.setCity(rs.getString("city"));
                    item.setState(rs.getString("state"));
                    item.setZip(rs.getString("zip"));
                    item.setPhoneOne(rs.getString("OfficePhone").equals("null") ? "" : rs.getString("OfficePhone"));
                    item.setPhoneTwo(rs.getString("CellularPhone").equals("null") ? "" : rs.getString("CellularPhone"));
                    item.setFax(rs.getString("Fax").trim());
                    item.setEmailAddress(rs.getString("Email"));
                    item.setNameTitle(rs.getString("etalextraname"));

                    if (item.getPhoneOne() == null && !rs.getString("HomePhone").equals("")){
                        item.setPhoneOne(!rs.getString("HomePhone").trim().equals("") ? StringUtilities.convertPhoneNumberToString(rs.getString("HomePhone").trim()) : null);
                    } else if (item.getPhoneTwo() == null && !rs.getString("Pager").equals("")){
                        item.setPhoneTwo(!rs.getString("Pager").trim().equals("") ? StringUtilities.convertPhoneNumberToString(rs.getString("Pager").trim()) : null);
                    }
                    
                    item.setPhoneOne(!item.getPhoneOne().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getPhoneOne().trim()) : null);
                    item.setPhoneTwo(!item.getPhoneTwo().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getPhoneTwo().trim()) : null);
                    item.setFax(!item.getFax().trim().equals("") ? StringUtilities.convertPhoneNumberToString(item.getFax().trim()) : null);
                                        
                    list.add(item);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
    public static List<casePartyModel> getPartyByCase(String year, String sequenceNumber) {
        List<casePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT caseparticipants.[year], caseparticipants.caseseqnumber, "
                    + "caseparticipants.participantType, caseparticipants.firstName, "
                    + "caseparticipants.middleinitial, caseparticipants.lastname "
                    + "FROM caseparticipants "
                    + "WHERE CaseParticipants.ParticipantType NOT LIKE '%Rep%' AND "
                    + "caseparticipants.active = 1 "
                    + "AND caseparticipants.[year] = ? "
                    + "AND caseparticipants.caseseqnumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, year);
            ps.setString(2, sequenceNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                casePartyModel item = new casePartyModel();
                item.setCaseYear(rs.getString("Year"));
                item.setCaseNumber(rs.getString("CaseSeqNumber"));
                item.setCaseRelation(rs.getString("ParticipantType"));
                item.setLastName(rs.getString("LastName"));
                item.setMiddleInitial(rs.getString("MiddleInitial"));        
                item.setFirstName(rs.getString("FirstName"));                        
                list.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
}
