/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.oldCMDSCasePartyModel;
import com.util.DBCInfo;
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
    
    public static List<oldCMDSCasePartyModel> getParty() {
        List<oldCMDSCasePartyModel> list = new ArrayList();
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
                oldCMDSCasePartyModel item = new oldCMDSCasePartyModel();
                item.setCaseParticipantsID(rs.getInt("CaseParticipantsID"));
                item.setActive(rs.getInt("Active"));
                item.setYear(rs.getString("Year"));
                item.setCaseMonth(rs.getString("Month"));
                item.setCaseType(rs.getString("Type"));
                item.setCaseSeqNumber(rs.getString("CaseSeqNumber"));
                item.setParticipantType(rs.getString("ParticipantType"));
                item.setLastName(rs.getString("LastName"));
                item.setFirstName(rs.getString("FirstName"));
                item.setMiddleInitial(rs.getString("MiddleInitial"));
                item.setTitle(rs.getString("Title"));
                item.setAddress1(rs.getString("Address1"));
                item.setAddress2(rs.getString("Address2"));
                item.setCity(rs.getString("city"));
                item.setState(rs.getString("state"));
                item.setZip(rs.getString("zip"));
                item.setOfficePhone(rs.getString("OfficePhone").equals("null") ? "" : rs.getString("OfficePhone"));
                item.setHomePhone(rs.getString("HomePhone").equals("null") ? "" : rs.getString("HomePhone"));
                item.setCellularPhone(rs.getString("CellularPhone").equals("null") ? "" : rs.getString("CellularPhone"));
                item.setPager(rs.getString("Pager"));
                item.setFax(rs.getString("Fax").trim());
                item.setEmail(rs.getString("Email"));
                item.setInactive(rs.getString("Inactive"));
                item.setEtalextraname(rs.getString("etalextraname"));
                item.setAgencyType(rs.getString("AgencyType"));
                item.setEnvelopeFlag(rs.getString("EnvelopeFlag"));
                
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