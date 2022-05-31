/**
  * File       : DatabaseBean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.epis.bean.rpfc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;

public class DatabaseBean implements Serializable {
	/**
	 * 
	 */

	Log log = new Log(DatabaseBean.class);

	private static final long serialVersionUID = 1L;

	String empName = "", airPortCD = "", employeeCode = "";

	String cpfACNumber = "", desegination = "", salary = "", pf = "",
			pensionFund = "";

	String fromDt = "", toDt = "";
	DBUtility commonDB = new DBUtility();


	public String getCpfACNumber() {
		return cpfACNumber;
	}

	public void setCpfACNumber(String cpfACNumber) {
		this.cpfACNumber = cpfACNumber;
	}

	public String getDesegination() {
		return desegination;
	}

	public void setDesegination(String desegination) {
		this.desegination = desegination;
	}

	public String getPensionFund() {
		return pensionFund;
	}

	public void setPensionFund(String pensionFund) {
		this.pensionFund = pensionFund;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAirPortCD() {
		return airPortCD;
	}

	public void setAirPortCD(String airPortCD) {
		this.airPortCD = airPortCD;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	

	public ArrayList retriveByAll() {
		log.info("DatabaseBean:retriveByAll-- Entering Method");
		ArrayList searchData = new ArrayList();
		DatabaseBean data = null;
		Connection con = null;
		String sqlQuery = this.buildQuery();
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				data = new DatabaseBean();
				if (rs.getString("CODE") != null) {
					data.setAirPortCD(rs.getString("CODE"));
				} else {
					data.setAirPortCD("");
				}
				if (rs.getString("NAME") != null) {
					data.setEmpName(rs.getString("NAME"));
				} else {
					data.setEmpName("");
				}
				System.out.println("Name" + rs.getString("NAME"));

				if (rs.getString("DESEG") != null) {
					data.setDesegination(rs.getString("DESEG"));
				} else {
					data.setDesegination("");
				}
				if (rs.getString("SALPF") != null) {
					data.setSalary(Integer.toString(rs.getInt("SALPF")));
				} else {
					data.setSalary("");
				}
				if (rs.getString("PENSIONFUND") != null) {
					data.setPensionFund(Integer.toString(rs
							.getInt("PENSIONFUND")));
				} else {
					data.setPensionFund("");
				}
				if (rs.getString("PF") != null) {
					data.setPf(Integer.toString(rs.getInt("PF")));
				} else {
					data.setPf("");
				}
				if (rs.getString("FROMDATE") != null) {
					data.setFromDt(CommonUtil.converDBToAppFormat(rs
							.getDate("FROMDATE")));
				} else {
					data.setFromDt("");
				}
				if (rs.getString("TODATE") != null) {
					data.setToDt(CommonUtil.converDBToAppFormat(rs
							.getDate("TODATE")));
				} else {
					data.setToDt("");
				}

				searchData.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("DatabaseBean:retriveByAll-- Leaving Method");
		return searchData;

	}

	public int totalData(String selectquery) {
		log.info("DatabaseBean:totalData-- Entering Method");
		Connection con = null;
		String selectSQL = selectquery;
		// String selectSQL = "select count(*) as count from pension_data";
		int totalRecords = 0;
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			if (rs.next()) {
				totalRecords = Integer.parseInt(rs.getString("count"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("DatabaseBean:totalData-- Leaving Method");
		return totalRecords;

	}

	public String buildQuery() {
		log.info("DatabaseBean:buildQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";

		String sqlQuery = "select ei.AIRPORTCODE AS CODE,ei.EMPLOYEENAME AS NAME,ei.DESEGNATION AS DESEG, ep.SALPF AS SALPF,ep.PF AS PF,ep.PENSIONFUND AS PENSIONFUND ,ep.FROMDATE AS FROMDATE, ep.TODATE AS TODATE from employee_pension ep,employee_info ei where ep.CPFACCNO=ei.CPFACNO ";
		System.out.println("Employee" + empName + "To Date" + toDt
				+ "airport code" + airPortCD + "From Date" + fromDt);

		if (!empName.equals("")) {
			whereClause.append(" LOWER(ei.EMPLOYEENAME) like'%"
					+ empName.toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!airPortCD.equals("")) {
			whereClause.append(" ei.AIRPORTCODE like'%" + airPortCD + "%'");
			whereClause.append(" AND ");
		}
		if (!fromDt.equals("")) {
			whereClause.append(" ep.FROMDATE ='" + fromDt + "'");
			whereClause.append(" AND ");
		}

		if (!toDt.equals("")) {
			whereClause.append(" ep.TODATE='" + toDt + "'");
			whereClause.append(" AND ");

		}

		query.append(sqlQuery);
		if ((empName.equals(""))
				&& (airPortCD.equals("") && (fromDt.equals("")) && (toDt
						.equals("")))) {
			log.info("inside if");
			;
		} else {
			log.info("inside else");

			query.append(" AND ");

			query.append(this.sTokenFormat(whereClause));

		}

		dynamicQuery = query.toString();

		log.info("DatabaseBean:buildQuery-- Leaving Method");
		return dynamicQuery;
	}

	private String sTokenFormat(StringBuffer stringBuffer) {

		StringBuffer whereStr = new StringBuffer();
		StringTokenizer st = new StringTokenizer(stringBuffer.toString());
		int count = 0;
		int stCount = st.countTokens();
		// && && count<=st.countTokens()-1st.countTokens()-1
		while (st.hasMoreElements()) {
			count++;
			if (count == stCount)
				break;
			whereStr.append(st.nextElement());
			whereStr.append(" ");
		}
		return whereStr.toString();
	}

	public String getFromDt() {
		return fromDt;
	}

	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	public String getToDt() {
		return toDt;
	}

	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

}
