/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.casePartyModel;
import com.model.oldCMDSCasePartyModel;
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
 * @author Andrew
 */
public class sqlContactList {

    public static List<casePartyModel> getSERBMasterList() {
        List<casePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "(Select distinct \n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "ulpdata.CPname as LastName, \n"
                    + "ulpdata.CPAddress1 as Address1,\n"
                    + "ulpdata.CPAddress2 as Address2, \n"
                    + "ulpdata.CPCity as City, \n"
                    + "ulpdata.CPState as 'State',\n"
                    + "ulpdata.CPZip as Zip,\n"
                    + "ulpdata.CPPhone1 as Phone1,\n"
                    + "ulpdata.CPPhone2 as Phone2, \n"
                    + "ulpdata.CPEmail as Email\n"
                    + "from ulpdata\n"
                    + "where ulpdata.Casenumber LIKE ('2010%') or \n"
                    + "ulpdata.Casenumber LIKE ('2011%') or\n"
                    + "ulpdata.Casenumber LIKE ('2012%') or\n"
                    + "ulpdata.Casenumber LIKE ('2013%') or\n"
                    + "ulpdata.Casenumber LIKE ('2014%') or\n"
                    + "ulpdata.Casenumber LIKE ('2015%') or\n"
                    + "ulpdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct\n"
                    + "NULL as OrgNumber,\n"
                    + "NULL as Sal,\n"
                    + "ulpdata.CPREPname as LastName, \n"
                    + "ulpdata.CPREPAddress1 as Address1,\n"
                    + "ulpdata.CPREPAddress2 as Address2, \n"
                    + "ulpdata.CPREPCity as City, \n"
                    + "ulpdata.CPREPState as 'State',\n"
                    + "ulpdata.CPREPZip as Zip,\n"
                    + "ulpdata.CPREPPhone1 as Phone1,\n"
                    + "ulpdata.CPREPPhone2 as Phone2, \n"
                    + "ulpdata.CPREPEmail as Email\n"
                    + "from ulpdata\n"
                    + "where ulpdata.Casenumber LIKE ('2010%') or \n"
                    + "ulpdata.Casenumber LIKE ('2011%') or\n"
                    + "ulpdata.Casenumber LIKE ('2012%') or\n"
                    + "ulpdata.Casenumber LIKE ('2013%') or\n"
                    + "ulpdata.Casenumber LIKE ('2014%') or\n"
                    + "ulpdata.Casenumber LIKE ('2015%') or\n"
                    + "ulpdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct \n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "ulpdata.CHDname as LastName, \n"
                    + "ulpdata.CHDAddress1 as Address1,\n"
                    + "ulpdata.CHDAddress2 as Address2, \n"
                    + "ulpdata.CHDCity as City, \n"
                    + "ulpdata.CHDState as 'State',\n"
                    + "ulpdata.CHDZip as Zip,\n"
                    + "ulpdata.CHDPhone1 as Phone1,\n"
                    + "ulpdata.CHDPhone2 as Phone2, \n"
                    + "ulpdata.CHDEmail as Email\n"
                    + "from ulpdata\n"
                    + "where ulpdata.Casenumber LIKE ('2010%') or \n"
                    + "ulpdata.Casenumber LIKE ('2011%') or\n"
                    + "ulpdata.Casenumber LIKE ('2012%') or\n"
                    + "ulpdata.Casenumber LIKE ('2013%') or\n"
                    + "ulpdata.Casenumber LIKE ('2014%') or\n"
                    + "ulpdata.Casenumber LIKE ('2015%') or\n"
                    + "ulpdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct \n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "ulpdata.CHDREPname as LastName, \n"
                    + "ulpdata.CHDREPAddress1 as Address1,\n"
                    + "ulpdata.CHDREPAddress2 as Address2, \n"
                    + "ulpdata.CHDREPCity as City, \n"
                    + "ulpdata.CHDREPState as 'State',\n"
                    + "ulpdata.CHDREPZip as Zip,\n"
                    + "ulpdata.CHDREPPhone1 as Phone1,\n"
                    + "ulpdata.CHDREPPhone2 as Phone2, \n"
                    + "ulpdata.CHDREPEmail as Email\n"
                    + "from ulpdata\n"
                    + "where ulpdata.Casenumber LIKE ('2010%') or \n"
                    + "ulpdata.Casenumber LIKE ('2011%') or\n"
                    + "ulpdata.Casenumber LIKE ('2012%') or\n"
                    + "ulpdata.Casenumber LIKE ('2013%') or\n"
                    + "ulpdata.Casenumber LIKE ('2014%') or\n"
                    + "ulpdata.Casenumber LIKE ('2015%') or\n"
                    + "ulpdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct \n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "medcase.EmployerName as LastName, \n"
                    + "medcase.EmployerAddress1 as Address1,\n"
                    + "medcase.EmployerAddress2 as Address2, \n"
                    + "medcase.EmlpoyerCity as City, \n"
                    + "medcase.EmployerState as 'State',\n"
                    + "medcase.EmployerZip as Zip,\n"
                    + "medcase.EmployerPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "medcase.EmployerEmail as Email\n"
                    + "from medcase\n"
                    + "where medcase.Casenumber LIKE ('2010%') or \n"
                    + "medcase.Casenumber LIKE ('2011%') or\n"
                    + "medcase.Casenumber LIKE ('2012%') or\n"
                    + "medcase.Casenumber LIKE ('2013%') or\n"
                    + "medcase.Casenumber LIKE ('2014%') or\n"
                    + "medcase.Casenumber LIKE ('2015%') or\n"
                    + "medcase.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct \n"
                    + "medcase.EmployerREPSal as Sal,\n"
                    + "NULL as OrgNumber, \n"
                    + "medcase.EmployerREPName as LastName, \n"
                    + "medcase.EmployerREPAddress1 as Address1,\n"
                    + "medcase.EmployerREPAddress2 as Address2, \n"
                    + "medcase.EmployerREPCity as City, \n"
                    + "medcase.EmployerREPState as 'State',\n"
                    + "medcase.EmployerREPZip as Zip,\n"
                    + "medcase.EmployerREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "medcase.EmployerREPEmail as Email\n"
                    + "from medcase\n"
                    + "where medcase.Casenumber LIKE ('2010%') or \n"
                    + "medcase.Casenumber LIKE ('2011%') or\n"
                    + "medcase.Casenumber LIKE ('2012%') or\n"
                    + "medcase.Casenumber LIKE ('2013%') or\n"
                    + "medcase.Casenumber LIKE ('2014%') or\n"
                    + "medcase.Casenumber LIKE ('2015%') or\n"
                    + "medcase.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct \n"
                    + "NULL as Sal,\n"
                    + "medcase.EmployeeOrgNumberb as OrgNumber,\n"
                    + "medcase.EmployeeOrgName as LastName, \n"
                    + "medcase.EmployeeOrgAddress1 as Address1,\n"
                    + "medcase.EmployeeOrgAddress2 as Address2, \n"
                    + "medcase.EmployeeOrgCity as City, \n"
                    + "medcase.EmployeeOrgState as 'State',\n"
                    + "medcase.EmployeeOrgZip as Zip,\n"
                    + "medcase.EmployeeOrgPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "medcase.EmployeeOrgEmail as Email\n"
                    + "from medcase\n"
                    + "where medcase.Casenumber LIKE ('2010%') or \n"
                    + "medcase.Casenumber LIKE ('2011%') or\n"
                    + "medcase.Casenumber LIKE ('2012%') or\n"
                    + "medcase.Casenumber LIKE ('2013%') or\n"
                    + "medcase.Casenumber LIKE ('2014%') or\n"
                    + "medcase.Casenumber LIKE ('2015%') or\n"
                    + "medcase.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select distinct\n"
                    + "medcase.EmployeeOrgREPSal as Sal,\n"
                    + "NULL as OrgNumber, \n"
                    + "medcase.EmployeeOrgREPName as LastName, \n"
                    + "medcase.EmployeeOrgREPAddress1 as Address1,\n"
                    + "medcase.EmployeeOrgREPAddress2 as Address2, \n"
                    + "medcase.EmployeeOrgREPCity as City, \n"
                    + "medcase.EmployeeOrgREPState as 'State',\n"
                    + "medcase.EmployeeOrgREPZip as Zip,\n"
                    + "medcase.EmployeeOrgREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "medcase.EmployeeOrgREPEmail as Email\n"
                    + "from medcase\n"
                    + "where medcase.Casenumber LIKE ('2010%') or \n"
                    + "medcase.Casenumber LIKE ('2011%') or\n"
                    + "medcase.Casenumber LIKE ('2012%') or\n"
                    + "medcase.Casenumber LIKE ('2013%') or\n"
                    + "medcase.Casenumber LIKE ('2014%') or\n"
                    + "medcase.Casenumber LIKE ('2015%') or\n"
                    + "medcase.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.PName as LastName, \n"
                    + "repdata.PAddress1 as Address1,\n"
                    + "repdata.PAddress2 as Address2, \n"
                    + "repdata.PCity as City, \n"
                    + "repdata.PState as 'State',\n"
                    + "repdata.PZip as Zip,\n"
                    + "repdata.PPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.PEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.PREPName as LastName, \n"
                    + "repdata.PREPAddress1 as Address1,\n"
                    + "repdata.PREPAddress2 as Address2, \n"
                    + "repdata.PREPCity as City, \n"
                    + "repdata.PREPState as 'State',\n"
                    + "repdata.PREPZip as Zip,\n"
                    + "repdata.PREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.PREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.EName as LastName, \n"
                    + "repdata.EAddress1 as Address1,\n"
                    + "repdata.EAddress2 as Address2, \n"
                    + "repdata.ECity as City, \n"
                    + "repdata.EState as 'State',\n"
                    + "repdata.EZip as Zip,\n"
                    + "repdata.EPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.EEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.EREPName as LastName, \n"
                    + "repdata.EREPAddress1 as Address1,\n"
                    + "repdata.EREPAddress2 as Address2, \n"
                    + "repdata.EREPCity as City, \n"
                    + "repdata.EREPState as 'State',\n"
                    + "repdata.EREPZip as Zip,\n"
                    + "repdata.EREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.EREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.EOName as LastName, \n"
                    + "repdata.EOAddress1 as Address1,\n"
                    + "repdata.EOAddress2 as Address2, \n"
                    + "repdata.EOCity as City, \n"
                    + "repdata.EOState as 'State',\n"
                    + "repdata.EOZip as Zip,\n"
                    + "repdata.EOPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.EOEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.EOREPName as LastName, \n"
                    + "repdata.EOREPAddress1 as Address1,\n"
                    + "repdata.EOREPAddress2 as Address2, \n"
                    + "repdata.EOREPCity as City, \n"
                    + "repdata.EOREPState as 'State',\n"
                    + "repdata.EOREPZip as Zip,\n"
                    + "repdata.EOREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.EOREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REOName as LastName, \n"
                    + "repdata.REOAddress1 as Address1,\n"
                    + "repdata.REOAddress2 as Address2, \n"
                    + "repdata.REOCity as City, \n"
                    + "repdata.REOState as 'State',\n"
                    + "repdata.REOZip as Zip,\n"
                    + "repdata.REOPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REOEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REOREPName as LastName, \n"
                    + "repdata.REOREPAddress1 as Address1,\n"
                    + "repdata.REOREPAddress2 as Address2, \n"
                    + "repdata.REOREPCity as City, \n"
                    + "repdata.REOREPState as 'State',\n"
                    + "repdata.REOREPZip as Zip,\n"
                    + "repdata.REOREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REOREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REO2Name as LastName, \n"
                    + "repdata.REO2Address1 as Address1,\n"
                    + "repdata.REO2Address2 as Address2, \n"
                    + "repdata.REO2City as City, \n"
                    + "repdata.REO2State as 'State',\n"
                    + "repdata.REO2Zip as Zip,\n"
                    + "repdata.REO2Phone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REO2Email as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REO2REPName as LastName, \n"
                    + "repdata.REO2REPAddress1 as Address1,\n"
                    + "repdata.REO2REPAddress2 as Address2, \n"
                    + "repdata.REO2REPCity as City, \n"
                    + "repdata.REO2REPState as 'State',\n"
                    + "repdata.REO2REPZip as Zip,\n"
                    + "repdata.REO2REPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REO2REPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REO3Name as LastName, \n"
                    + "repdata.REO3Address1 as Address1,\n"
                    + "repdata.REO3Address2 as Address2, \n"
                    + "repdata.REO3City as City, \n"
                    + "repdata.REO3State as 'State',\n"
                    + "repdata.REO3Zip as Zip,\n"
                    + "repdata.REO3Phone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REO3Email as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.REO3REPName as LastName, \n"
                    + "repdata.REO3REPAddress1 as Address1,\n"
                    + "repdata.REO3REPAddress2 as Address2, \n"
                    + "repdata.REO3REPCity as City, \n"
                    + "repdata.REO3REPState as 'State',\n"
                    + "repdata.REO3REPZip as Zip,\n"
                    + "repdata.REO3REPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.REO3REPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.IEOName as LastName, \n"
                    + "repdata.IEOAddress1 as Address1,\n"
                    + "repdata.IEOAddress2 as Address2, \n"
                    + "repdata.IEOCity as City, \n"
                    + "repdata.IEOState as 'State',\n"
                    + "repdata.IEOZip as Zip,\n"
                    + "repdata.IEOPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.IEOEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.IEOREPName as LastName, \n"
                    + "repdata.IEOREPAddress1 as Address1,\n"
                    + "repdata.IEOREPAddress2 as Address2, \n"
                    + "repdata.IEOREPCity as City, \n"
                    + "repdata.IEOREPState as 'State',\n"
                    + "repdata.IEOREPZip as Zip,\n"
                    + "repdata.IEOREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.IEOREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.IName as LastName, \n"
                    + "repdata.IAddress1 as Address1,\n"
                    + "repdata.IAddress2 as Address2, \n"
                    + "repdata.ICity as City, \n"
                    + "repdata.IState as 'State',\n"
                    + "repdata.IZip as Zip,\n"
                    + "repdata.IPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.IEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.IREPName as LastName, \n"
                    + "repdata.IREPAddress1 as Address1,\n"
                    + "repdata.IREPAddress2 as Address2, \n"
                    + "repdata.IREPCity as City, \n"
                    + "repdata.IREPState as 'State',\n"
                    + "repdata.IREPZip as Zip,\n"
                    + "repdata.IREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.IREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.CSName as LastName, \n"
                    + "repdata.CSAddress1 as Address1,\n"
                    + "repdata.CSAddress2 as Address2, \n"
                    + "repdata.CSCity as City, \n"
                    + "repdata.CSState as 'State',\n"
                    + "repdata.CSZip as Zip,\n"
                    + "repdata.CSPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.CSEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "(Select DISTINCT\n"
                    + "NULL as Sal,\n"
                    + "NULL as OrgNumber,\n"
                    + "repdata.CSREPName as LastName, \n"
                    + "repdata.CSREPAddress1 as Address1,\n"
                    + "repdata.CSREPAddress2 as Address2, \n"
                    + "repdata.CSREPCity as City, \n"
                    + "repdata.CSREPState as 'State',\n"
                    + "repdata.CSREPZip as Zip,\n"
                    + "repdata.CSREPPhone as Phone1,\n"
                    + "NULL as Phone2,\n"
                    + "repdata.CSREPEmail as Email\n"
                    + "from repdata\n"
                    + "where repdata.Casenumber LIKE ('2010%') or \n"
                    + "repdata.Casenumber LIKE ('2011%') or\n"
                    + "repdata.Casenumber LIKE ('2012%') or\n"
                    + "repdata.Casenumber LIKE ('2013%') or\n"
                    + "repdata.Casenumber LIKE ('2014%') or\n"
                    + "repdata.Casenumber LIKE ('2015%') or\n"
                    + "repdata.Casenumber LIKE ('2016%')\n"
                    + ")";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                casePartyModel item = new casePartyModel();
                item.setPrefix((rs.getString("Sal") == null) ? null : rs.getString("Sal").trim().replaceAll("[^A-Za-z.]", ""));
                item.setFirstName(null);
                item.setMiddleInitial(null);
                item.setLastName((rs.getString("LastName") == null) ? null : rs.getString("LastName").trim());
                item.setSuffix(null);
                item.setNameTitle(null);
                item.setJobTitle(null);
                item.setCompanyName(null);
                item.setAddress1((rs.getString("Address1") == null) ? null : rs.getString("Address1").trim());
                item.setAddress2((rs.getString("Address2") == null) ? null : rs.getString("Address2").trim());
                item.setAddress3(null);
                item.setCity((rs.getString("City") == null) ? null : rs.getString("City").trim());
                item.setState((rs.getString("State") == null) ? null : rs.getString("State").trim());
                item.setZip((rs.getString("Zip") == null) ? null : rs.getString("Zip").trim());
                item.setPhoneOne((rs.getString("Phone1") == null) ? null : StringUtilities.convertPhoneNumberToString(rs.getString("Phone1")));
                item.setPhoneTwo((rs.getString("Phone2") == null) ? null : StringUtilities.convertPhoneNumberToString(rs.getString("Phone2")));
                item.setEmailAddress((rs.getString("Email") == null) ? null : rs.getString("Email").trim());
                item.setFax(null);
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
    
    public static List<oldCMDSCasePartyModel> getPBRMasterList() {
        List<oldCMDSCasePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT DISTINCT LastName, FirstName, MiddleInitial, "
                    + "Title, Address1, Address2, City, State, Zip, OfficePhone, "
                    + "HomePhone, CellularPhone, Pager, Fax, Email, etalextraname "
                    + "FROM caseparticipants WHERE "
                    + "year LIKE ('2010') OR year LIKE ('2011') OR "
                    + "year LIKE ('2012') OR year LIKE ('2013') OR "
                    + "year LIKE ('2014') OR year LIKE ('2015') OR "
                    + "year LIKE ('2016')";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                oldCMDSCasePartyModel item = new oldCMDSCasePartyModel();
                item.setLastName(rs.getString("LastName"));
                item.setFirstName(rs.getString("FirstName"));
                item.setMiddleInitial(rs.getString("MiddleInitial"));
                item.setEtalextraname(rs.getString("etalextraname"));
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
    
    public static List<casePartyModel> getRepresentativeList() {
        List<casePartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameOLD());
            String sql = "SELECT * FROM representatives";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                casePartyModel item = new casePartyModel();
                item.setPrefix(null);
                item.setFirstName((rs.getString("FirstName") == null) ? null : rs.getString("FirstName").trim());
                item.setMiddleInitial((rs.getString("MiddleInitial") == null) ? null : rs.getString("MiddleInitial").trim());
                item.setLastName((rs.getString("LastName") == null) ? null : rs.getString("LastName").trim());
                item.setSuffix(null);
                item.setNameTitle(null);
                item.setJobTitle((rs.getString("Title") == null) ? null : rs.getString("Title").trim());
                item.setCompanyName((rs.getString("Description") == null) ? null : rs.getString("Description").trim());
                item.setAddress1((rs.getString("Address1") == null) ? null : rs.getString("Address1").trim());
                item.setAddress2((rs.getString("Address2") == null) ? null : rs.getString("Address2").trim());
                item.setAddress3(null);
                item.setCity((rs.getString("City") == null) ? null : rs.getString("City").trim());
                item.setState((rs.getString("State") == null) ? null : rs.getString("State").trim());
                item.setZip((rs.getString("Zip") == null) ? null : rs.getString("Zip").trim());
                item.setPhoneOne(null);
                item.setPhoneTwo(null);
                if (rs.getString("Phone") != null){
                    item.setPhoneOne(rs.getString("Phone").equals("null") ? null : StringUtilities.convertPhoneNumberToString(rs.getString("Phone")));
                }
                if (rs.getString("Fax") != null){
                    item.setPhoneTwo(rs.getString("Fax").equals("null") ? null : StringUtilities.convertPhoneNumberToString(rs.getString("Fax")));
                }
                if (rs.getString("CellPhone") != null){
                    if (!rs.getString("CellPhone").equals("null") && item.getPhoneTwo() == null){
                        item.setPhoneTwo(rs.getString("CellPhone").equals("null") ? null : StringUtilities.convertPhoneNumberToString(rs.getString("CellPhone")));
                    }
                }
                item.setEmailAddress((rs.getString("Email") == null) ? null : rs.getString("Email").trim());
                item.setFax(null);              
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

    public static void savePartyInformation(casePartyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB(DBCInfo.getDBnameNEW());
            String sql = "INSERT INTO Party ("
                    + "prefix, "        //01
                    + "firstName, "     //02
                    + "middleInitial, " //03
                    + "lastName, "      //04
                    + "suffix, "        //05
                    + "nameTitle, "     //06
                    + "jobTitle, "      //07
                    + "companyName, "   //08
                    + "address1, "      //09
                    + "address2, "      //10
                    + "address3, "      //11
                    + "city, "          //12
                    + "stateCode, "     //13
                    + "zipCode, "       //14
                    + "phone1, "        //15
                    + "phone2, "        //16
                    + "emailAddress, "  //17
                    + "fax  "           //18
                    + ") VALUES (";
                    for(int i=0; i<17; i++){
                        sql += "?, ";   //01-17
                    }
                     sql += "?)"; //18
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getPrefix());
            ps.setString( 2, item.getFirstName());
            ps.setString( 3, item.getMiddleInitial());
            ps.setString( 4, item.getLastName());
            ps.setString( 5, item.getSuffix());
            ps.setString( 6, item.getNameTitle());
            ps.setString( 7, item.getJobTitle());
            ps.setString( 8, item.getCompanyName());
            ps.setString( 9, item.getAddress1());
            ps.setString(10, item.getAddress2());
            ps.setString(11, item.getAddress3());
            ps.setString(12, item.getCity());
            ps.setString(13, item.getState());
            ps.setString(14, item.getZip());
            ps.setString(15, item.getPhoneOne());
            ps.setString(16, item.getPhoneTwo());
            ps.setString(17, item.getEmailAddress());
            ps.setString(18, item.getFax());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
}
