/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.barginingUnitModel;
import com.model.oldBarginingUnitNewModel;
import com.model.oldBlobFileModel;
import com.sceneControllers.MainWindowSceneController;
import com.util.DBCInfo;
import com.util.Global;
import com.util.SceneUpdater;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class sqlBarginingUnit {
    
    public static List<oldBarginingUnitNewModel> getOldBarginingUnits() {
        List<oldBarginingUnitNewModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM BarginingUnitNew";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldBarginingUnitNewModel item = new oldBarginingUnitNewModel();
                item.setBarginingUnitid(rs.getInt("BarginingUnitid"));
                item.setActive(rs.getInt("Active"));
                item.setEmployerNumber(rs.getString("EmployerNumber"));
                item.setUnitNumber(rs.getString("UnitNumber"));
                item.setCert(rs.getString("Cert"));
                item.setBUEmployerName(rs.getString("BUEmployerName"));
                item.setJur(rs.getString("Jur"));
                item.setCounty(rs.getString("County"));
                item.setLUnion(rs.getString("LUnion"));
                item.setLocal(rs.getString("Local"));
                item.setStrike(rs.getString("Strike"));
                item.setLGroup(rs.getString("LGroup"));
                item.setCertDate(rs.getString("CertDate"));
                item.setEnabled(rs.getString("Enabled"));
                item.setCaseRef(rs.getString("CaseRef"));
                item.setUnitDescription(rs.getString("UnitDescription"));                
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
        
    public static void addBarginingUnit(barginingUnitModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO BarginingUnit ("
                    + "Active, "            //01
                    + "EmployerNumber, "    //02
                    + "UnitNumber, "        //03
                    + "Cert, "              //04
                    + "BUEmployerName, "    //05
                    + "Jurisdiction, "      //06
                    + "County, "            //07
                    + "LUnion, "            //08
                    + "Local, "             //09
                    + "Strike, "            //10
                    + "LGroup, "            //11
                    + "CertDate, "          //12
                    + "Enabled, "           //13
                    + "CaseRefYear, "       //14
                    + "CaseRefSection, "    //15
                    + "CaseRefMonth, "      //16
                    + "CaseRefSequence, "   //17
                    + "UnitDescription, "   //18
                    + "Notes"               //19
                    + ") VALUES ("; 
                    for(int i=0; i<18; i++){
                        sql += "?, ";   //01-18
                    }
                     sql += "?)"; //19
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getActive());
            ps.setString( 2, item.getEmployerNumber());
            ps.setString( 3, item.getUnitNumber());
            ps.setString( 4, item.getCert());
            ps.setString( 5, item.getBUEmployerName());
            ps.setString( 6, item.getJurisdiction());
            ps.setString( 7, item.getCounty());
            ps.setString( 8, item.getLUnion());
            ps.setString( 9, item.getLocal());
            ps.setInt   (10, item.getStrike());
            ps.setString(11, item.getLGroup());
            ps.setDate  (12, item.getCertDate());
            ps.setInt   (13, item.getEnabled());
            ps.setString(14, item.getCaseRefYear());
            ps.setString(15, item.getCaseRefSection());
            ps.setString(16, item.getCaseRefMonth());
            ps.setString(17, item.getCaseRefSequence());
            ps.setString(18, item.getUnitDescription());
            ps.setString(19, item.getNotes());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void batchAddBarginingUnit(List<oldBarginingUnitNewModel> list, MainWindowSceneController control, int totalRecordCount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "Insert INTO BarginingUnit ("
                    + "Active, "            //01
                    + "EmployerNumber, "    //02
                    + "UnitNumber, "        //03
                    + "Cert, "              //04
                    + "BUEmployerName, "    //05
                    + "Jurisdiction, "      //06
                    + "County, "            //07
                    + "LUnion, "            //08
                    + "Local, "             //09
                    + "Strike, "            //10
                    + "LGroup, "            //11
                    + "CertDate, "          //12
                    + "Enabled, "           //13
                    + "CaseRefYear, "       //14
                    + "CaseRefSection, "    //15
                    + "CaseRefMonth, "      //16
                    + "CaseRefSequence, "   //17
                    + "UnitDescription, "   //18
                    + "Notes"               //19
                    + ") VALUES ("; 
                    for(int i=0; i<18; i++){
                        sql += "?, ";   //01-18
                    }
                     sql += "?)"; //19
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for (oldBarginingUnitNewModel old : list){
                String unitDescription = null;
                String note = null; 
                Date certDate = null;
                String CaseRefYear = null;
                String CaseRefSection = null;
                String CaseRefMonth = null;
                String CaseRefSequence = null;
                
                Timestamp certDateTime = StringUtilities.convertStringTimeStamp(old.getCertDate());
                if (certDateTime != null){
                    certDate = new Date(certDateTime.getTime());
                }

                List<oldBlobFileModel>oldBlobFileList = sqlBlobFile.getOldBlobDataBUDectioption((old.getEmployerNumber() + "-" + old.getUnitNumber()).split("-"));
                for (oldBlobFileModel blob : oldBlobFileList) {
                    if (null != blob.getSelectorA().trim()) switch (blob.getSelectorA().trim()) {
                        case "UnitDesc":
                            unitDescription = StringUtilities.convertBlobFileToString(blob.getBlobData());
                            break;
                        case "Notes":
                            note = StringUtilities.convertBlobFileToString(blob.getBlobData());
                            break;
                        default:
                            break;
                    }
                }

                //Check CaseNumber
                String[] caseNumber = old.getCaseRef().split("-");
                if (old.getCaseRef().trim().length() == 16 &&
                        (caseNumber[0].length() == 4 || caseNumber[0].length() == 2) &&
                        caseNumber[1].length() < 5 && caseNumber[2].length() == 2){
                    if (caseNumber[0].length() == 4){
                        CaseRefYear = caseNumber[0];
                    } else if (caseNumber[0].length() == 2) {
                        if (Integer.parseInt(caseNumber[0]) > 20){
                            CaseRefYear = "19" + caseNumber[0];
                        } else {
                            CaseRefYear = "20" + caseNumber[0];
                        }
                    }            
                    CaseRefSection = caseNumber[1];
                    CaseRefMonth = caseNumber[2];
                    if (caseNumber[3].length() > 4){
                        CaseRefSequence = caseNumber[3].substring(0, 3);
                    } else {
                        CaseRefSequence = caseNumber[3];
                    }
                }
                
                ps.setInt   ( 1, old.getActive());
                ps.setString( 2, !"".equals(old.getEmployerNumber().trim()) ? old.getEmployerNumber().trim() : null);
                ps.setString( 3, !"".equals(old.getUnitNumber().trim()) ? old.getUnitNumber().trim() : null);
                ps.setString( 4, !"".equals(old.getCert().trim()) ? old.getCert().trim() : null);
                ps.setString( 5, !"".equals(old.getBUEmployerName().trim()) ? old.getBUEmployerName().trim() : null);
                ps.setString( 6, !"".equals(old.getJur().trim()) ? old.getJur().trim() : null);
                ps.setString( 7, !"".equals(old.getCounty().trim()) ? old.getCounty().trim() : null);
                ps.setString( 8, !"".equals(old.getLUnion().trim()) ? old.getLUnion().trim() : null);
                ps.setString( 9, !"".equals(old.getLocal().trim()) ? old.getLocal().trim() : null);
                ps.setInt   (10, "Y".equals(old.getStrike().trim()) ? 1 : 0);
                ps.setString(11, !"".equals(old.getLGroup().trim()) ? old.getLGroup().trim() : null);
                ps.setDate  (12, certDate);
                ps.setInt   (13, "Y".equals(old.getEnabled().trim()) ? 1 : 0);
                ps.setString(14, CaseRefYear);
                ps.setString(15, CaseRefSection);
                ps.setString(16, CaseRefMonth);
                ps.setString(17, CaseRefSequence);
                ps.setString(18, unitDescription);
                ps.setString(19, note);
                ps.addBatch();
                    if(++count % Global.getBATCH_SIZE() == 0) {
                        ps.executeBatch();
                        SceneUpdater.listItemFinished(control, count, totalRecordCount, count + " imported");
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
