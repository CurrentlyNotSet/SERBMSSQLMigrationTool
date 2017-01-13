/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyTypeModel;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class sqlPartyType {

    public static List<partyTypeModel> getPartyTypeList() {
        List<partyTypeModel> list = new ArrayList();

        //ULP Section
        list.add(new partyTypeModel("ULP", "Charging Party"));
        list.add(new partyTypeModel("ULP", "Charging Party REP"));
        list.add(new partyTypeModel("ULP", "Charged Party"));
        list.add(new partyTypeModel("ULP", "Charged Party REP"));

        //REP Section
        list.add(new partyTypeModel("REP", "Conversion School"));
        list.add(new partyTypeModel("REP", "Conversion School REP"));
        list.add(new partyTypeModel("REP", "Employee Organization"));
        list.add(new partyTypeModel("REP", "Employee Organization REP"));
        list.add(new partyTypeModel("REP", "Employer"));
        list.add(new partyTypeModel("REP", "Employer REP"));
        list.add(new partyTypeModel("REP", "Incumbent Employee Organization"));
        list.add(new partyTypeModel("REP", "Incumbent Employee Organization REP"));
        list.add(new partyTypeModel("REP", "Intervener"));
        list.add(new partyTypeModel("REP", "Intervener REP"));
        list.add(new partyTypeModel("REP", "Petitioner"));
        list.add(new partyTypeModel("REP", "Petitioner REP"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization REP"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization 2"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization 2 REP"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization 3"));
        list.add(new partyTypeModel("REP", "Rival Employee Organization 3 REP"));

        //MED Section
        list.add(new partyTypeModel("MED", "Employer"));
        list.add(new partyTypeModel("MED", "Employer REP"));
        list.add(new partyTypeModel("MED", "Employee Organization"));
        list.add(new partyTypeModel("MED", "Employee Organization REP"));

        //Return party Type List
        return list;
    }

    public static void addPartyType(partyTypeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO PartyType ("
                    + "Active, " //
                    + "section, " //01
                    + "type" //02
                    + ") VALUES ("
                    + "1," //
                    + "?," //01
                    + "?)"; //02
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getSection());
            ps.setString(2, item.getSection());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static void batchAddPartyType(List<partyTypeModel> list) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO PartyType ("
                    + "Active, " //
                    + "section, " //01
                    + "type" //02
                    + ") VALUES ("
                    + "1," //
                    + "?," //01
                    + "?)"; //02
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            for (partyTypeModel item : list) {
                ps.setString(1, item.getSection());
                ps.setString(2, item.getName());
                ps.addBatch();
                if (++count % Global.getBATCH_SIZE() == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
