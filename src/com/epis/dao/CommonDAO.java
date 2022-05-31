package com.epis.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.rpfc.DesignationBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.SignatureMappingBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;

import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.dao.CommonDAO;

public class CommonDAO implements Constants {
	Log log = new Log(CommonDAO.class);

	DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();
	
	public String lookforMonth(String pensionNo,String currentDate,boolean dummy) throws EPISException
	{ 
		Connection con = null;
		Statement st=null;
		ResultSet rs = null;
		String monthYear="",query="";
		try {
			con = commonDB.getConnection();			 
			  
				query = "SELECT TO_CHAR(MAX(MONTHYEAR),'Mon-yyyy') AS MONTHYEAR FROM EMPLOYEE_PENSION_VALIDATE  WHERE PENSIONNO='"
					+ pensionNo + "' ";
				st = con.createStatement();			 
				rs = st.executeQuery(query);
				if (rs.next()) {
					monthYear = rs.getString("MONTHYEAR");
				}else{
					throw new EPISException("No Records Found");
				}
			 
		}catch (SQLException e) {
			throw new EPISException(e);
		}catch (EPISException e) {
			throw e;
		}finally{
			commonDB.closeConnection(con, st, rs);
		}	
		
		return monthYear;
	}

	

	public int checkEmployeeInfo(Connection con, String empCode,
			String cpfaccno, String employeeName, String region) {
		log.info("FinanceDAO :checkEmployeeInfo() entering method ");
		log.info("FinanceDAO :checkEmployeeInfo() cpfaccno " + cpfaccno
				+ "empCode " + empCode);
		int foundEmpFlag = 0;
		String query = "";
		Statement st = null;
		ResultSet rs = null;
		// for Eastern Region Data.
		/*
		 * if (!cpfaccno.equals("") && !employeeName.equals("")) { query =
		 * "select count(EMPLOYEENAME) as EMPLOYEENAME from employee_info where
		 * CPFACNO='" + cpfaccno + "' and employeeName='" + employeeName + "'"; }
		 * else if (!cpfaccno.equals("") && employeeName.equals("")) { query =
		 * "select count(cpfacno) as cpfacno from employee_info where CPFACNO='" +
		 * cpfaccno + "'"; } else
		 */
		/*
		 * if (!cpfaccno.equals("") && !empCode.equals("")) { query = "select
		 * count(*) as count from employee_info where cpfacno='" + cpfaccno + "'
		 * and employeeno='" + empCode + "' and region='" + region + "' "; }
		 * else
		 */if (!cpfaccno.equals("")) {
			query = "select count(*) as count from employee_info where   cpfacno='"
					+ cpfaccno.trim() + "' and region='" + region.trim() + "'";
			// " AND airportcode='IGI IAD'";
		} else {
			query = "select count(*) as count from employee_info where employeeno='"
					+ empCode + "' and region='" + region + "'";
			// " AND airportcode='IGI IAD' ";
		}

		// For Southern Region
		/*
		 * if (!cpfaccno.equals("") && (!empCode.equals(""))) { query = "select
		 * EMPLOYEENAME as EMPLOYEENAME from employee_info where CPFACNO='" +
		 * cpfaccno + "' and EMPLOYEENO='" + empCode + "' and employeeName='" +
		 * employeeName + "'"; } else
		 * 
		 * if (!cpfaccno.equals("") ) { query = "select EMPLOYEENAME as
		 * EMPLOYEENAME from employee_info where CPFACNO='" + cpfaccno + "' and
		 * employeeName='" + employeeName + "'"; } else { query = "select
		 * EMPLOYEENAME as EMPLOYEENAME from employee_info where EMPLOYEENO='" +
		 * empCode + "' and employeeName='" + employeeName + "'"; }
		 */

		log.info("query is " + query);
		try {

			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				if (rs.getString("count") != null) {
					foundEmpFlag = rs.getInt("count");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Resultset ");
				}
			}

			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Statement.");
				}
			}
		}
		log.info("FinanceDAO :checkEmployeeInfo() leaving method"
				+ foundEmpFlag);
		return foundEmpFlag;
	}

	public String checkEmployeeInfo1(Connection con, String empCode,
			String cpfaccno, String employeeName, String region) {
		log.info("FinanceDAO :checkEmployeeInfo() entering method ");
		log.info("FinanceDAO :checkEmployeeInfo() cpfaccno " + cpfaccno
				+ "empCode " + empCode);
		String foundEmpFlag = "";
		String query = "";
		Statement st = null;
		ResultSet rs = null;
		if (!cpfaccno.equals("")) {
			query = "select cpfacno  from employee_info where   cpfacno='"
					+ cpfaccno.trim() + "' and region='" + region.trim()
					+ "' and cpfacno is not null";

		} else {
			query = "select cpfacno  from employee_info where employeeno='"
					+ empCode + "' and region='" + region
					+ "' and cpfacno is not null";

		}
		log.info("query is " + query);
		try {

			st = con.createStatement();
			log.info("st " + st);
			rs = st.executeQuery(query);

			if (rs.next()) {
				// log.info("cpfaccno "+rs.getString(1));
				foundEmpFlag = rs.getString(1);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Resultset ");
				}
			}

			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Statement.");
				}
			}
		}
		log.info("FinanceDAO :checkEmployeeInfo() leaving method"
				+ foundEmpFlag);
		return foundEmpFlag;
	}

	public String checkFinanceDuplicate(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region) {
		log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		System.out.println("fromDate" + fromDate + "employeeNo" + employeeNo);

		CommonUtil commonUtil = new CommonUtil();
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yy", "-MMM-yy");
			String query = "select employeeNo as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and employeeno='"
					+ employeeNo
					+ "' and region='" + region + "'";

			log.info("query is " + query);

			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				if (rs.getString("COLUMNNM") != null) {
					foundEmpFlag = rs.getString("COLUMNNM");
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			// e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Resultset ");
				}
			}

			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Statement.");
				}
			}

			// this.closeConnection(con, st, rs);
		}
		log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	public int checkRecordCount(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region) {
		log.info("PensionDAO :checkRecordCount() entering method ");
		int foundEmpFlag = 0;
		Statement st = null;

		ResultSet rs = null;
		System.out.println("fromDate" + fromDate + "employeeNo" + employeeNo);

		CommonUtil commonUtil = new CommonUtil();
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yy", "-MMM-yy");
			String query = "select *  from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "'";

			log.info("query is " + query);

			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				EmpMasterBean bean = new EmpMasterBean();
				bean.setBasic(rs.getString("basic"));
				bean.setSpecialBasic(rs.getString("SPECIALBASIC"));
				bean.setEmoluments(rs.getString("EMOLUMENTS"));
				bean.setDailyAllowance(rs.getString("DAILYALLOWANCE"));
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic().trim())
						+ Float.parseFloat(bean.getDailyAllowance())
						+ Float.parseFloat(bean.getSpecialBasic()));
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			// e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Resultset ");
				}
			}

			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Statement.");
				}
			}

			// this.closeConnection(con, st, rs);
		}
		log.info("PensionDAO :checkRecordCount() leaving method");
		return foundEmpFlag;
	}

	public String getPensionNumber(String empName, String dateofBirth,
			String cpf, boolean flag) {
		log.info("PensionDao:getPensionNumber() entering method");
		log.info("PensionDao:getPensionNumber() dateofBirth" + dateofBirth
				+ "empName" + empName);
		// TODO Auto-generated method stub
		String finalName = "", fname = "";
		SimpleDateFormat fromDate = null;
		int endIndxName = 0;
		String quats[] = { "Mrs.", "DR." };
		String tempName = "", convertedDt = "";
		// String uniquenumber = validateCPFAccno(cpf.toCharArray());
		try {
			if (dateofBirth.indexOf("-") != -1) {
				fromDate = new SimpleDateFormat("dd-MMM-yyyy");
			} else {
				fromDate = new SimpleDateFormat("dd/MMM/yyyy");
			}
			SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
			convertedDt = toDate.format(fromDate.parse(dateofBirth));
			System.out.println(" convertedDt " + convertedDt);
			int startIndxName = 0;
			endIndxName = 1;
			for (int i = 0; i < quats.length; i++) {
				if (empName.indexOf(quats[i]) != -1) {
					tempName = empName.replaceAll(quats[i], "").trim();
					// tempName=empName.substring(index+1,empName.length());
					empName = tempName;
				}
			}

			finalName = empName.substring(startIndxName, endIndxName);
			finalName = empName.substring(startIndxName, endIndxName);
			if (empName.indexOf(" ") != -1) {
				endIndxName = empName.lastIndexOf(" ");
				finalName = finalName + empName.substring(endIndxName).trim();
			} else if (empName.indexOf(".") != -1) {
				endIndxName = empName.lastIndexOf(".");
				finalName = finalName
						+ empName.substring(endIndxName + 1, empName.length())
								.trim();
			} else {
				finalName = empName.substring(0, 2);
			}
			log.info("final name is" + finalName);
			char[] cArray = finalName.toCharArray();
			fname = String.valueOf(cArray[0]);
			fname = fname + String.valueOf(cArray[1]);
			log.info(empName + " Short Name of " + fname);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			log.info("Exception is " + e);
		}
		log.info("PensionDao:getPensionNumber() leaving method");
		if (flag == true)
			return convertedDt + fname + cpf;
		else
			return convertedDt + fname;
	}

	public String getPensionNumber(String empName, String dateofBirth,
			String cpf, String region) {
		log.info("PensionDao:getPensionNumber() entering method");
		log.info("PensionDao:getPensionNumber() dateofBirth" + dateofBirth
				+ "empName" + empName);
		// TODO Auto-generated method stub
		String finalName = "", fname = "";
		SimpleDateFormat fromDate = null;
		int endIndxName = 0;
		String quats[] = { "Mrs.", "DR." };
		String tempName = "", convertedDt = "";
		String uniquenumber = validateAlphaCPFAccno(cpf.toCharArray());

		try {
			if (dateofBirth.indexOf("-") != -1) {
				fromDate = new SimpleDateFormat("dd-MMM-yyyy");
			} else {
				fromDate = new SimpleDateFormat("dd/MMM/yyyy");
			}
			SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
			convertedDt = toDate.format(fromDate.parse(dateofBirth));
			System.out.println(" convertedDt " + convertedDt);

			int startIndxName = 0, index = 0;
			endIndxName = 1;
			for (int i = 0; i < quats.length; i++) {
				if (empName.indexOf(quats[i]) != -1) {
					tempName = empName.replaceAll(quats[i], "").trim();
					// tempName=empName.substring(index+1,empName.length());
					empName = tempName;
				}
			}
			finalName = empName.substring(startIndxName, endIndxName);
			finalName = empName.substring(startIndxName, endIndxName);
			if (empName.indexOf(" ") != -1) {
				endIndxName = empName.lastIndexOf(" ");
				finalName = finalName + empName.substring(endIndxName).trim();
			} else if (empName.indexOf(".") != -1) {
				endIndxName = empName.lastIndexOf(".");
				finalName = finalName
						+ empName.substring(endIndxName + 1, empName.length())
								.trim();
			} else {
				finalName = empName.substring(0, 2);
			}
			log.info("final name is" + finalName);
			char[] cArray = finalName.toCharArray();
			fname = String.valueOf(cArray[0]);
			fname = fname + String.valueOf(cArray[1]);
			log.info(empName + " Short Name of " + fname);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			log.info("Exception is " + e);

		}
		log.info("PensionDao:getPensionNumber() leaving method");
		return convertedDt + fname + uniquenumber;

	}

	public String getPensionNumberError(String empName, String dateofBirth,
			String cpf, String tempPensionNumber) {
		log.info("PensionDao:getPensionNumber() entering method");
		log.info("PensionDao:getPensionNumber() dateofBirth" + dateofBirth
				+ "empName" + empName);
		// TODO Auto-generated method stub
		String finalName = "", fname = "", finalString = "", tempPensionSubString = "";
		String finalPensionNumber = "";
		SimpleDateFormat fromDate = null;
		int endIndxName = 0, tempPensionNoSize = 0;
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt.", "SHRI",
				"MISS." };
		String tempName = "", PensionNumber = "", convertedDt = "";
		String uniquenumber = validateCPFAccno(cpf.toCharArray());
		try {
			if (dateofBirth.indexOf("-") != -1) {
				fromDate = new SimpleDateFormat("dd-MMM-yyyy");
			} else {
				fromDate = new SimpleDateFormat("dd/MMM/yyyy");
			}
			SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
			convertedDt = toDate.format(fromDate.parse(dateofBirth));
			System.out.println(" convertedDt " + convertedDt);

			int startIndxName = 0, index = 0;
			endIndxName = 1;
			for (int i = 0; i < quats.length; i++) {
				if (empName.indexOf(quats[i]) != -1) {
					tempName = empName.replaceAll(quats[i], "").trim();
					// tempName=empName.substring(index+1,empName.length());
					empName = tempName;
				}
			}
			String delimiters = " ";
			System.out
					.println("Oringial String indexof" + empName.indexOf(" "));
			if (empName.indexOf(" ") != -1) {
				finalString = StringUtility.replace(
						empName.trim().toCharArray(), delimiters.toCharArray(),
						".").toString();
			} else {
				finalString = empName;
			}
			System.out.println("Oringial String" + empName
					+ " Modified String " + finalString);
			StringTokenizer st = new StringTokenizer(finalString, ".");
			int count = 0, i = 0;

			int stCount = st.countTokens();
			String[] finalStringArray = new String[stCount];
			// && && count<=st.countTokens()-1st.countTokens()-1
			while (st.hasMoreElements()) {
				finalStringArray[i] = st.nextToken();
				i++;
			}
			System.out.println("Length==tempPensionNumber==="
					+ finalStringArray.length + "Temp Pension Number is "
					+ tempPensionNumber);
			tempPensionNoSize = tempPensionNumber.length();
			System.out.println("tempPensionNoSize=====" + tempPensionNoSize);
			for (int j = 0; j < finalStringArray.length; j++) {
				if (!tempPensionNumber.equals("") && tempPensionNoSize != 0) {
					if (tempPensionNumber.substring(0, 1).equals(
							finalStringArray[j].substring(0, 1))) {
						System.out.println("finalStringArray[j].length()====="
								+ finalStringArray[j].length());
						if (finalStringArray[j].length() <= 1) {
							tempPensionSubString = tempPensionNumber.substring(
									1, 2);
						} else {
							tempPensionSubString = tempPensionNumber.substring(
									0, 1);

						}
					}
				}

			}

			System.out.println("tempPensionSubString====="
					+ tempPensionSubString);
			int startIndex = 0;
			for (int j = 0; j < finalStringArray.length; j++) {
				System.out.println("Length=====" + finalStringArray[j].length()
						+ "Value is" + finalStringArray[j]);
				if (finalStringArray[j].length() > 1) {
					if (!tempPensionNumber.equals("") && tempPensionNoSize != 0) {
						System.out
								.println("Check Pension number===tempPensionSubString=="
										+ tempPensionSubString
										+ "Value is"
										+ finalStringArray[j].substring(0, 1)
										+ "Temp Pension Size========="
										+ tempPensionNoSize);
						if (tempPensionSubString.equals(finalStringArray[j]
								.substring(0, 1))) {
							if ((tempPensionNoSize - 1) == 1) {
								fname = fname
										+ finalStringArray[j].substring(0,
												tempPensionNoSize + 1);
							} else {
								if (tempPensionNoSize + 1 != finalStringArray[j]
										.length() - 1) {
									fname = fname
											+ finalStringArray[j].substring(0,
													tempPensionNoSize + 1);
								} else {
									fname = fname
											+ finalStringArray[j].substring(0,
													tempPensionNoSize);
								}
							}
							System.out
									.println("Check Pension number===tempPensionSubString==fname===="
											+ fname);
						} else {
							fname = fname + finalStringArray[j].substring(0, 1);
						}

					} else {
						fname = fname + finalStringArray[j].substring(0, 2);
					}

				} else if (finalStringArray[j].length() <= 1) {
					fname = fname + finalStringArray[j];
				}
			}
			finalPensionNumber = convertedDt + fname + uniquenumber;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		log.info("PensionDao:getPensionNumber() leaving method");
		return finalPensionNumber;

	}

	public String checkPensionNumber(String pensionnumber, String cpfaccno,
			String employeeno, String employeeName, String region,
			String dateOfBirth) {
		log.info("PensionDAO :checkPensionNumber() Enter method==============");
		String pensionNumber = "", tempPensionNumber = "";
		boolean tempPensionflag = false;
		String[] employeeList = new String[3];
		employeeList = getPensionNumberFromDB(pensionnumber);
		String dbCPFACCno = "", dbEmployeeNo = "", dbEmployeeName = "", dbRegion = "", chkFinalPensionNumber = "";
		if (employeeList[0] != null) {
			dbCPFACCno = employeeList[0].toString();
		}
		if (employeeList[1] != null) {
			dbEmployeeName = employeeList[1].toString();
		}
		if (employeeList[2] != null) {
			dbEmployeeNo = employeeList[2].toString();
		}
		if (employeeList[3] != null) {
			dbRegion = employeeList[3].toString();
		}
		System.out.println("Size is (checkPensionNumber) "
				+ employeeList.length);
		System.out.println("CPFACCNO From DB " + employeeList[0] + "From Form"
				+ cpfaccno);
		System.out.println("Employee Name From DB" + employeeList[1]
				+ "From Form" + employeeName);
		System.out.println("Employee No From DB" + employeeList[2]
				+ "From Form" + employeeno);
		System.out.println("Region From DB" + employeeList[3] + "From Form"
				+ region);
		String delimeters = " ";
		dbEmployeeName = StringUtility.replace(dbEmployeeName.toCharArray(),
				delimeters.toCharArray(), ".").toString();
		employeeName = StringUtility.replace(employeeName.toCharArray(),
				delimeters.toCharArray(), ".").toString();
		if (dbCPFACCno.equals(cpfaccno) && dbEmployeeName.equals(employeeName)
				&& dbEmployeeNo.equals(employeeno) && dbRegion.equals(region)) {
			log.info("Pnesion number both are equal");
			pensionNumber = pensionnumber;
		} else if (dbCPFACCno != null && dbEmployeeName != null
				&& dbEmployeeNo != null && !dbRegion.equals("")
				&& dbRegion.equals(region)) {
			log
					.info("Pnesion number db values are avaliable the record is created new both are equal");
			pensionNumber = pensionnumber;
		} else {
			if (!pensionnumber.equals("") && !dbRegion.equals("")) {
				tempPensionNumber = pensionnumber.substring(6, pensionnumber
						.length());
				String tempCpfacno = employeeList[0].toString();
				tempPensionNumber = StringUtility.replace(
						tempPensionNumber.toCharArray(),
						tempCpfacno.toCharArray(), "").toString();
				tempPensionflag = true;
			}
			System.out.println("tempPensionNumber=============="
					+ tempPensionNumber);
			pensionNumber = this.getPensionNumberError(employeeName,
					dateOfBirth, cpfaccno, tempPensionNumber);
			if (checkPensionNumberFromDB(pensionNumber) == true) {

				pensionNumber = checkPensionNumber(pensionNumber, cpfaccno,
						employeeno, employeeName, region.trim(), dateOfBirth);
			}
		}
		System.out.println("checkPensionNumber==============" + pensionNumber);
		log.info("PensionDAO :checkPensionNumber() Exit method==============");
		return pensionNumber;
	}

	private String[] getPensionNumberFromDB(String pensionnumber) {
		int count = 0;
		String[] employeeList = new String[4];
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		System.out.println("pensionnumber==============" + pensionnumber);
		String query = "select CPFACNO,EMPLOYEENAME,EMPLOYEENO,REGION from employee_info where  pensionnumber='"
				+ pensionnumber + "' and pensionnumber is not null";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getString("CPFACNO") != null) {
					employeeList[0] = rs.getString("CPFACNO");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					employeeList[1] = rs.getString("EMPLOYEENAME");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					employeeList[2] = rs.getString("EMPLOYEENO");
				}
				if (rs.getString("REGION") != null) {
					employeeList[3] = rs.getString("REGION");
				} else {
					employeeList[3] = "";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return employeeList;
	}

	private boolean checkPensionNumberFromDB(String pensionnumber) {
		int count = 0;
		String[] employeeList = new String[4];
		boolean flag = false;
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		System.out.println("pensionnumber==============" + pensionnumber);
		String query = "select pensionnumber from employee_info where  pensionnumber='"
				+ pensionnumber + "' and pensionnumber is not null";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("pensionnumber") != null) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return flag;
	}

	private String validateCPFAccno(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		StringBuffer digitBuff = new StringBuffer();
		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				buff.append(c);
			} else if (c >= '0' && c <= '9') {
				digitBuff.append(c);
			}
		}
		String validDt = digitBuff.toString();
		log.info("cpfacno " + validDt);
		return validDt;
	}

	private String validateAlphaCPFAccno(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		StringBuffer digitBuff = new StringBuffer();
		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				buff.append(c);
			} else if (c >= '0' && c <= '9') {
				buff.append(c);
			} else if (c >= '-') {
				digitBuff.append(c);
			}

		}
		String validDt = buff.toString();
		log.info("cpfacno " + validDt);
		return validDt;
	}

	public ArrayList getDepartmentList() {
		log.info("CommonDAO :getDepartmentList() Entering Method ");
		Connection con = null;
		Statement st = null;
		ArrayList departmentList = new ArrayList();
		ResultSet rs = null;
		String departmentName = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String sql = "select * from employee_department";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("departmentname") != null) {
					departmentName = rs.getString("departmentname");
				}
				departmentList.add(departmentName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		log.info("CommonDAO :getDepartmentList() Leaving Method ");
		return departmentList;
	}

	public ArrayList getDesignationList() {
		log.info("CommonDAO :getDesignationList() Entering Method ");
		Connection con = null;
		Statement st = null;
		ArrayList designationList = new ArrayList();
		ResultSet rs = null;
		DesignationBean bean = null;
		String sql = "select DESIGNATION,EMPLEVEL from EMPLOYEE_DESEGNATION";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				bean = new DesignationBean();
				if (rs.getString("EMPLEVEL") != null) {
					bean.setEmplevel(rs.getString("EMPLEVEL"));
				} else {
					bean.setEmplevel("");
				}
				if (rs.getString("DESIGNATION") != null) {
					bean.setDesingation(rs.getString("DESIGNATION"));
				} else {
					bean.setDesingation("");
				}
				designationList.add(bean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		log.info("CommonDAO :getDesignationList() Leaving Method ");
		return designationList;
	}

	public EmployeePersonalInfo loadEmployeePersonalInfo(ResultSet rs)
			throws SQLException {
		EmployeePersonalInfo personal = new EmployeePersonalInfo();
		log.info("loadEmployeePersonalInfo==============");
		CommonUtil commonUtil = new CommonUtil();
		if (rs.getString("cpfacno") != null) {
			personal.setCpfAccno(rs.getString("cpfacno"));
		} else {
			personal.setCpfAccno("---");
		}

		if (rs.getString("airportcode") != null) {
			personal.setAirportCode(rs.getString("airportcode"));
		} else {
			personal.setAirportCode("---");
		}
		if (rs.getString("desegnation") != null) {
			personal.setDesignation(rs.getString("desegnation"));
		} else {
			personal.setDesignation("---");
		}
		if (rs.getString("EMPSERIALNUMBER") != null) {
			personal.setOldPensionNo(rs.getString("EMPSERIALNUMBER"));

		} else {
			personal.setOldPensionNo("---");
		}
		if (rs.getString("EMPSERIALNUMBER") != null) {
			personal.setPensionNo(commonUtil.leadingZeros(5, rs
					.getString("EMPSERIALNUMBER")));

		} else {
			personal.setPensionNo("---");
		}

		if (rs.getString("employeename") != null) {
			personal.setEmployeeName(rs.getString("employeename"));
		} else {
			personal.setEmployeeName("---");
		}
		if (rs.getString("EMPLOYEENO") != null) {
			personal.setEmployeeNumber(rs.getString("EMPLOYEENO"));
		} else {
			personal.setEmployeeNumber("---");
		}

		if (rs.getString("PENSIONNUMBER") != null) {
			personal.setRefPensionNumber(rs.getString("PENSIONNUMBER"));
		} else {
			personal.setRefPensionNumber("---");
		}
		if (rs.getString("dateofbirth") != null) {
			personal.setDateOfBirth(commonUtil.converDBToAppFormat(rs
					.getDate("dateofbirth")));
		} else {
			personal.setDateOfBirth("---");
		}
		if (rs.getString("DATEOFJOINING") != null) {
			personal.setDateOfJoining(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFJOINING")));
		} else {
			personal.setDateOfJoining("---");
		}
		if (rs.getString("WETHEROPTION") != null) {
			personal.setWetherOption(rs.getString("WETHEROPTION"));
		} else {
			personal.setWetherOption("---");
		}
		if (rs.getString("region") != null) {
			personal.setRegion(rs.getString("region"));
		} else {
			personal.setRegion("---");
		}
		if (rs.getString("MARITALSTATUS") != null) {
			personal.setMaritalStatus(rs.getString("MARITALSTATUS"));
		} else {
			personal.setMaritalStatus("---");
		}

		personal.setRemarks("---");

		if (rs.getString("lastactive") != null) {
			personal.setLastActive(commonUtil.getDatetoString(rs
					.getDate("lastactive"), "dd-MMM-yyyy"));
		} else {
			personal.setLastActive("---");
		}

		if (rs.getString("SEX") != null) {
			personal.setGender(rs.getString("SEX"));

		} else {
			personal.setGender("---");
		}
		if (rs.getString("FHNAME") != null) {
			personal.setFhName(rs.getString("FHNAME"));
		} else {
			personal.setFhName("---");
		}
		if (rs.getString("OTHERREASON") != null) {
			personal.setOtherReason(rs.getString("OTHERREASON"));
		} else {
			personal.setOtherReason("---");
		}
		if (rs.getString("DATEOFSEPERATION_REASON") != null) {
			personal.setSeperationReason(rs
					.getString("DATEOFSEPERATION_REASON"));
		} else {
			personal.setSeperationReason("---");
		}
		if (rs.getString("DATEOFSEPERATION_DATE") != null) {
			personal.setSeperationDate(rs.getString("DATEOFSEPERATION_DATE"));
		} else {
			personal.setSeperationDate("---");
		}
		if (rs.getString("division") != null) {
			personal.setDivision(rs.getString("division"));
		} else {
			personal.setDivision("---");
		}
		if (!personal.getDateOfBirth().equals("---")) {
			String personalPFID = this.getPFID(personal.getEmployeeName(),
					personal.getDateOfBirth(), personal.getPensionNo());
			personal.setPfID(personalPFID);
		} else {
			personal.setPfID(personal.getPensionNo());
		}

		return personal;

	}

	public EmployeePersonalInfo loadPersonalInfo(ResultSet rs)
			throws SQLException {
        EmployeePersonalInfo personal = new EmployeePersonalInfo();
        log.info("loadPersonalInfo==============");
        CommonUtil  commonUtil=new CommonUtil();
        if (rs.getString("cpfacno") != null) {
            personal.setCpfAccno(rs.getString("cpfacno"));
        } else {
            personal.setCpfAccno("---");
        }
      
        if (rs.getString("airportcode") != null) {
            personal.setAirportCode(rs.getString("airportcode"));
        } else {
            personal.setAirportCode("---");
        }
        if (rs.getString("desegnation") != null) {
            personal.setDesignation(rs.getString("desegnation"));
        } else {
            personal.setDesignation("---");
        }
        if (rs.getString("PENSIONNO") != null) {
            personal.setOldPensionNo(rs.getString("PENSIONNO"));
       
        } else {
            personal.setOldPensionNo("---");
        }
        if (rs.getString("PENSIONNO") != null) {
            personal.setPensionNo(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
        log.info("pfno "+personal.getPensionNo());
        } else {
            personal.setPensionNo("---");
        }
        
        if (rs.getString("employeename") != null) {
            personal.setEmployeeName(rs.getString("employeename"));
        } else {
            personal.setEmployeeName("---");
        }
        if (rs.getString("EMPLOYEENO") != null) {
            personal.setEmployeeNumber(rs.getString("EMPLOYEENO"));
        } else {
            personal.setEmployeeNumber("---");
        }
        
        if (rs.getString("REFPENSIONNUMBER") != null) {
            personal.setRefPensionNumber(rs.getString("REFPENSIONNUMBER"));
        } else {
            personal.setRefPensionNumber("---");
        }
        if (rs.getString("dateofbirth") != null) {
            personal.setDateOfBirth(commonUtil.converDBToAppFormat(rs
                    .getDate("dateofbirth")));
        } else {
            personal.setDateOfBirth("---");
        }
        if (rs.getString("DATEOFJOINING") != null) {
            personal.setDateOfJoining(commonUtil.converDBToAppFormat(rs
                    .getDate("DATEOFJOINING")));
        } else {
            personal.setDateOfJoining("---");
        }
        
        if (rs.getString("WETHEROPTION") != null) {
            personal.setWetherOption(rs.getString("WETHEROPTION"));
        } else {
            personal.setWetherOption("---");
        }
        if (rs.getString("region") != null) {
            personal.setRegion(rs.getString("region"));
        } else {
            personal.setRegion("---");
        }
        if (rs.getString("MARITALSTATUS") != null) {
            personal.setMaritalStatus(rs.getString("MARITALSTATUS"));
        } else {
            personal.setMaritalStatus("---");
        }
    
            personal.setRemarks("---");
        
        if (rs.getString("lastactive") != null) {
            personal.setLastActive(commonUtil.getDatetoString(rs
                    .getDate("lastactive"), "dd-MMM-yyyy"));
        } else {
            personal.setLastActive("---");
        }
        
        if (rs.getString("GENDER") != null) {
            personal.setGender(rs.getString("GENDER"));
            log.info("gender "+rs.getString("GENDER").toString());
        } else {
            personal.setGender("---");
        }
        if (rs.getString("FHNAME") != null) {
            personal.setFhName(rs.getString("FHNAME"));
        } else {
            personal.setFhName("---");
        }
        if (rs.getString("OTHERREASON") != null) {
            personal.setOtherReason(rs.getString("OTHERREASON"));
        } else {
            personal.setOtherReason("---");
        }
        if (rs.getString("DATEOFSEPERATION_REASON") != null) {
            personal.setSeperationReason(rs
                    .getString("DATEOFSEPERATION_REASON"));
        } else {
            personal.setSeperationReason("---");
        }
        if (rs.getString("DATEOFSEPERATION_DATE") != null) {
            personal.setSeperationDate(commonUtil.converDBToAppFormat(rs
                    .getDate("DATEOFSEPERATION_DATE")));
        } else {
            personal.setSeperationDate("---");
        }
        if (rs.getString("division") != null) {
            personal.setDivision(rs.getString("division"));
        } else {
            personal.setDivision("---");
        }
        if(!personal.getDateOfBirth().equals("---")){
            String personalPFID = this.getPFID(personal.getEmployeeName(), personal
                    .getDateOfBirth(), personal.getPensionNo());
            personal.setPfID(personalPFID);
        }else{
            personal.setPfID(personal.getPensionNo());
        }
        //below lines added as on 21-sep-2010
        if (rs.getString("FINALSETTLMENTDT") != null) {
            personal.setFinalSettlementDate(commonUtil.converDBToAppFormat(rs
                    .getDate("FINALSETTLMENTDT")));
        } else {
            personal.setFinalSettlementDate("---");
        }
         
        return personal;
        
        }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getPFID(String empName, String dateofBirth, String sequenceNo) {

		// TODO Auto-generated method stub
		String finalName = "", fname = "";
		SimpleDateFormat fromDate = null;
		int endIndxName = 0;
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String tempName = "", convertedDt = "";

		try {
			if (!(dateofBirth.equals("") || dateofBirth.equals("---"))) {
				if (dateofBirth.indexOf("-") != -1) {
					fromDate = new SimpleDateFormat("dd-MMM-yyyy");
				} else {
					fromDate = new SimpleDateFormat("dd/MMM/yyyy");
				}
				SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
				convertedDt = toDate.format(fromDate.parse(dateofBirth));
			}

			int startIndxName = 0, index = 0;
			endIndxName = 1;
			if (!empName.equals("")) {
				if (empName.lastIndexOf(".") == empName.length() - 1) {
					empName = empName.substring(0, empName.length() - 1);
				}
			}

			for (int i = 0; i < quats.length; i++) {
				if (empName.indexOf(quats[i]) != -1) {
					tempName = empName.replaceAll(quats[i], "").trim();
					// tempName=empName.substring(index+1,empName.length());

					empName = tempName;
				}
			}
			finalName = empName.substring(startIndxName, endIndxName);

			if (empName.indexOf(" ") != -1) {
				endIndxName = empName.lastIndexOf(" ");
				finalName = finalName + empName.substring(endIndxName).trim();
			} else if (empName.indexOf(".") != -1) {
				endIndxName = empName.indexOf(".");
				finalName = finalName
						+ empName.substring(endIndxName + 1, empName.length())
								.trim();
			} else {
				finalName = empName.substring(0, 2);
			}

			char[] cArray = finalName.toCharArray();
			fname = String.valueOf(cArray[0]);
			fname = fname + String.valueOf(cArray[1]);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);

		}

		return convertedDt + fname + sequenceNo;

	}
	public int getEmployeeLoans(Connection con, String monthyear,
			String pfIDS, String flag, String pensionNo) {
		String sqlQuery = "";
		Statement st = null;
		ResultSet rs = null;
		String loanType = "";
		int rfSubamount = 0, nrfSubAmount = 0;
		int loanAmount = 0;
		if (!pfIDS.equals("")) {
			if (flag.equals("ADV.PAID")) {
				sqlQuery = "SELECT SUB_AMT AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE ("
						+ pfIDS
						+ ") AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE IN('RF','NRF')";
			} else if (flag.equals("ADV.DRAWN")) {
				sqlQuery = "SELECT CONT_AMT AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE ("
						+ pfIDS
						+ ") AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE='NRF'";
			} else {
				sqlQuery = "SELECT (CONT_AMT+SUB_AMT) AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE ("
						+ pfIDS
						+ ") AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE='NRF'";
			}
		} else {
			if (flag.equals("ADV.PAID")) {
				sqlQuery = "SELECT SUB_AMT AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE PENSIONNO="
						+ pensionNo
						+ " AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE IN('RF','NRF')";
			} else if (flag.equals("ADV.DRAWN")) {
				sqlQuery = "SELECT CONT_AMT AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE PENSIONNO="
						+ pensionNo
						+ " AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE='NRF'";
			} else {
				sqlQuery = "SELECT (CONT_AMT+SUB_AMT) AS SUAMNT,LOANTYPE AS LOANTYPE FROM EMPLOYEE_PENSION_LOANS WHERE WHERE PENSIONNO="
						+ pensionNo
						+ " AND to_char(LOANDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "' AND LOANTYPE='NRF'";
			}
		}

		// log.info("CommonDAO::getEmployeeLoans" + sqlQuery);
		try {
			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("SUAMNT") != null) {
					nrfSubAmount = nrfSubAmount + rs.getInt("SUAMNT");
				}
				/*
				 * log.info("In While rfSubamount" + rfSubamount +
				 * "nrfSubAmount" + nrfSubAmount);
				 */
			}
			/*
			 * log.info("rfSubamount" + rfSubamount + "nrfSubAmount" +
			 * nrfSubAmount); if (flag.equals("ADV.PAID")) { loanAmount =
			 * rfSubamount + nrfSubAmount; } else { loanAmount = nrfSubAmount; }
			 */

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return nrfSubAmount;
	}
	public boolean compareFinalSettlementDates(String fromDate, String todate,
			String finalsettlementDate) {
		Date fromYear = new Date();
		Date toYear = new Date();
		Date fnlDate = new Date();
		boolean finalDateFlag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("fromDate" + fromDate + "todate" + todate
				+ "finalsettlementDate" + finalsettlementDate);
		try {
			fromYear = dateFormat.parse(fromDate);
			toYear = dateFormat.parse(todate);
			fnlDate = dateFormat.parse(finalsettlementDate);
			if (fnlDate.after(fromYear) && fnlDate.before(toYear)) {
				finalDateFlag = true;
				log.info(fromDate + "In between" + finalsettlementDate
						+ " years" + todate);
			} else {
				finalDateFlag = false;
				log.info(fromDate + "In out " + finalsettlementDate + " years"
						+ todate);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return finalDateFlag;
	}
	public ArrayList pfCardAdditionalContriCalculation(String pensionNo){
		
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String qury="",additionalContri="";
		double total=0.0;
		ArrayList al=new ArrayList();
		try {
			con = commonDB.getConnection();
			stmt = con.createStatement();
			qury = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' || SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR, decode((sign(sysdate - add_months(to_date(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' || SUBSTR(empdt.MONYEAR, 4, 4))), 1))),-1, 0, 1, 1) as signs, emp.* " +
					" from (select distinct to_char(to_date('01-Sep-2014', 'dd-mon-yyyy') + rownum - 1, 'MONYYYY') monyear from employee_pension_validate where rownum <= to_date('01-Apr-2015', 'dd-mon-yyyy') - to_date('01-Sep-2014', 'dd-mon-yyyy') + 1) empdt, (SELECT add_months(MONTHYEAR, -1) as MONTHYEAR," +
					" to_char(add_months(MONTHYEAR, -1), 'MONYYYY') empmonyear, additionalcontri FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag = 'Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= add_months('01-Sep-2014', 1) and to_date(to_char(monthyear, 'dd-Mon-yyyy')) <= add_months(last_day('01-Apr-2015'), 1))" +
					" AND PENSIONNO = "+pensionNo+" ) emp where empdt.monyear = emp.empmonyear(+) ORDER BY TO_DATE(EMPMNTHYEAR)";
			log.info("pfCardAdditionalContriCalculation====qury====" + qury);
			rs = stmt.executeQuery(qury);
			while(rs.next()) {
				if(rs.getString("additionalcontri")!=null) {
					additionalContri=rs.getString("additionalcontri");
				}else {
					additionalContri="0.0";
				}
				al.add(additionalContri);
				total=total+Double.parseDouble(additionalContri);					
			}								
			al.add(Double.toString(total));
			log.info("total : "+total+" arraylist : "+al);
		} catch (Exception e) {
			log.info("===========<<<<<<<<<<< Exception >>>>>>>>>>==========" + e.getMessage());
		} finally {
			commonDB.closeConnection(null, stmt, rs);
		}
		
		return al;
	}
	
	public String getCPFACCNOByPension(String pensionNo, String cpfacno,
			String region) {
		Connection con = null;
		StringBuffer buffer = new StringBuffer();
		String cpfaccno = "";
		String selectFamilySQL = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"
				+ pensionNo.trim() + "' ";
		log.info("Query =====" + selectFamilySQL);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectFamilySQL);
			while (rs.next()) {
				buffer.append(rs.getString("CPFACNO"));
				buffer.append(",");
				buffer.append(rs.getString("REGION"));
				buffer.append("=");
			}
			buffer.append(cpfacno);
			buffer.append(",");
			buffer.append(region);
			buffer.append("=");
			cpfaccno = buffer.toString();
			if (cpfaccno.equals("")) {
				cpfaccno = "---";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cpfaccno;
	}

	public ArrayList getAirportsByPersonalTbl(String region) {
		Connection con = null;
		Statement st = null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String unitName = "", unitCode = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String query = "SELECT distinct AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO where region='"
					+ region + "' and airportcode is not null";
			log.info("getAirportsByFinanceTbl==query===========" + query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				EmpMasterBean bean = new EmpMasterBean();
				if (rs.getString("AIRPORTCODE") != null) {
					unitName = (String) rs.getString("airportcode").toString()
							.trim();
					bean.setStation(unitName);
				} else {
					bean.setStation("");
				}

				airportList.add(bean);

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return airportList;

	}

	

	public void updateTransPensionData() {
		String fromYear = "", toYear = "", dateOfRetriment = "";

		ArrayList empList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		String updateQuery = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Statement upST = null;
		int updateSt = 0, finalUpdate = 0;
		String sqlQuery = "SELECT CPFACNO,REGION,EMPLOYEENAME,EMPSERIALNUMBER FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM EMPLOYEE_PERSONAL_INFO) ORDER BY EMPSERIALNUMBER";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			upST = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			String per_region = "", cpfaccno = "", employeename = "", perPensionNo = "";
			BufferedWriter fw = new BufferedWriter(new FileWriter(
					"c://GenertedPensionNoTrnsData.txt"));
			String totalInfo = "";
			while (rs.next()) {
				per_region = rs.getString("REGION");
				cpfaccno = rs.getString("CPFACNO");
				perPensionNo = rs.getString("EMPSERIALNUMBER");
				totalInfo = per_region + "," + cpfaccno + "," + perPensionNo;
				updateQuery = "UPDATE employee_pension_validate SET PENSIONNO='"
						+ perPensionNo.trim()
						+ "' WHERE CPFACCNO='"
						+ cpfaccno.trim()
						+ "' AND REGION='"
						+ per_region.trim() + "'";
				fw.write(totalInfo);
				fw.newLine();
				fw.write(updateQuery + ";");
				fw.newLine();
				fw.write("============================");
				fw.flush();
				updateSt = upST.executeUpdate(updateQuery);
				finalUpdate = finalUpdate + updateSt;

			}
			log.info("Total Updations" + finalUpdate);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		// return form8List;
	}

	public String sTokenFormat(StringBuffer stringBuffer) {

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

	public String buildQueryForPersonalTransInfo(String employeeName,
			String region, String pensionNo, String cpfaccno, String monthYear) {
		log.info("CommonDAO:buildQueryForPersonalTransInfo-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderyBy = "", sqlQuery = "";

		sqlQuery = "select PENSIONNO,EMPLOYEENO,CPFACNO,AIRPORTCODE,EMPLOYEENAME,FHNAME,REGION,DATEOFBIRTH,DATEOFJOINING,DESEGNATION from EMPLOYEE_PERSONAL_INFO ";

		if (!pensionNo.equals("")) {
			whereClause.append(" PENSIONNO ='" + pensionNo.toLowerCase() + "'");
			whereClause.append(" AND ");
		}

		if (!employeeName.equals("")) {
			whereClause.append(" LOWER(employeename) like'%"
					+ employeeName.toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!region.equals("")) {
			whereClause.append(" REGION='" + region + "'");
			whereClause.append(" AND ");

		}
		if (!cpfaccno.equals("")) {
			whereClause.append(" CPFACNO='" + cpfaccno + "'");
			whereClause.append(" AND ");

		}
		query.append(sqlQuery);
		if (region.equals("") && employeeName.equals("")
				&& pensionNo.equals("") && cpfaccno.equals("")) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}

		dynamicQuery = query.toString();

		log.info("CommonDAO:buildQueryForPersonalTransInfo Leaving Method");
		return dynamicQuery;
	}

	 public ArrayList loadPersonalTransInfo(String employeeName, String region,
				String pensionNo, String cpfaccno, String monthYear) {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AdvanceBasicBean advanceBasicBean = null;
			ArrayList loadPersList = new ArrayList();
			String sqlQuery = "";
			sqlQuery = this.buildQueryForPersonalTrans(employeeName, region,
					pensionNo, cpfaccno, monthYear);
			System.out.println(sqlQuery);
			log.info("CommonDAO::loadPersonalTransInfo" + sqlQuery);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					advanceBasicBean = new AdvanceBasicBean();
					/*
					 * if(rs.getString("MONTHYEAR")!=null){
					 * advanceBasicBean.setTransMnthYear(CommonUtil.getDatetoString(rs.getDate("MONTHYEAR"),"dd-MMM-yyyy"));
					 * }else{ advanceBasicBean.setTransMnthYear("---"); }
					 */
					if (rs.getString("newempcode") != null) {
						advanceBasicBean.setEmployeeNo(rs.getString("newempcode"));
					} else {
						advanceBasicBean.setEmployeeNo("---");
					}
					/*
					 * if(rs.getString("WETHEROPTION")!=null){
					 * advanceBasicBean.setWetherOption(rs.getString("WETHEROPTION")) ;
					 * }else{ advanceBasicBean.setWetherOption("---"); }
					 */
					if (rs.getString("DATEOFBIRTH") != null) {
						advanceBasicBean.setDateOfBirth(CommonUtil.getDatetoString(
								rs.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					} else {
						advanceBasicBean.setDateOfBirth("---");
					}
					if (rs.getString("PERMANENTADDRESS") != null) {
						advanceBasicBean.setPermenentaddress(rs
								.getString("PERMANENTADDRESS"));
					} else {
						advanceBasicBean.setPermenentaddress("");
					}

					if (rs.getString("TEMPORATYADDRESS") != null) {
						advanceBasicBean.setPresentaddress(rs
								.getString("TEMPORATYADDRESS"));
					} else {
						advanceBasicBean.setPresentaddress("");
					}

					if (rs.getString("EMAILID") != null) {
						advanceBasicBean.setMailID(rs.getString("EMAILID"));
					} else {
						advanceBasicBean.setMailID("");
					}
					if (rs.getString("DESEGNATION") != null) {
						advanceBasicBean
								.setDesignation(rs.getString("DESEGNATION"));
					} else {
						advanceBasicBean.setDesignation("");
					}
					if (rs.getString("EMPLOYEENAME") != null) {
						advanceBasicBean.setEmployeeName(rs
								.getString("EMPLOYEENAME"));
					} else {
						advanceBasicBean.setEmployeeName("");
					}
					if (rs.getString("DEPARTMENT") != null) {
						advanceBasicBean.setDepartment(rs.getString("DEPARTMENT"));
					} else {
						advanceBasicBean.setDepartment("");
					}
					if (rs.getString("DATEOFJOINING") != null) {
						advanceBasicBean.setDateOfJoining(CommonUtil
								.getDatetoString(rs.getDate("DATEOFJOINING"),
										"dd-MMM-yyyy"));
					} else {
						advanceBasicBean.setDateOfJoining("");
					}

					if (rs.getString("FHNAME") != null) {
						advanceBasicBean.setFhName(rs.getString("FHNAME"));
					} else {
						advanceBasicBean.setFhName("");
					}
					if (rs.getString("PENSIONNO") != null) {
						advanceBasicBean.setPensionNo(rs.getString("PENSIONNO"));
					}
					if (rs.getString("REGION") != null) {
						advanceBasicBean.setRegion(rs.getString("REGION"));
					} else {
						advanceBasicBean.setRegion("");
					}
					if (rs.getString("CPFACNO") != null) {
						advanceBasicBean.setCpfaccno(rs.getString("CPFACNO"));
					} else {
						advanceBasicBean.setCpfaccno("");
					}

					if (rs.getString("AIRPORTCODE") != null) {
						advanceBasicBean.setStation(rs.getString("AIRPORTCODE")
								.toUpperCase());
					} else {
						advanceBasicBean.setStation("");
					}
					
					if (rs.getString("DATEOFSEPERATION_REASON") != null) {
						advanceBasicBean.setSeperationreason(rs.getString("DATEOFSEPERATION_REASON"));
					} else {
						advanceBasicBean.setSeperationreason("");
					}
					
					if (rs.getString("DATEOFSEPERATION_DATE") != null) {
						advanceBasicBean.setSeperationdate(CommonUtil.getDatetoString(
								rs.getDate("DATEOFSEPERATION_DATE"), "dd-MMM-yyyy"));
					} else {
						advanceBasicBean.setSeperationdate("");
					}
					
					long noOfYears = 0;
					noOfYears = rs.getLong("ENTITLEDIFF");

					if (noOfYears > 0) {
						advanceBasicBean.setDateOfMembership(advanceBasicBean
								.getDateOfJoining());
					} else {
						advanceBasicBean.setDateOfMembership("01-Apr-1995");
					}
					if (!advanceBasicBean.getDateOfBirth().equals("---")) {
						String personalPFID = this.getPFID(advanceBasicBean
								.getEmployeeName(), advanceBasicBean
								.getDateOfBirth(), commonUtil.leadingZeros(5,
								advanceBasicBean.getPensionNo()));
						advanceBasicBean.setPfid(personalPFID);
					} else {
						advanceBasicBean.setPfid(commonUtil.leadingZeros(5,
								advanceBasicBean.getPensionNo()));
					}

					String transData = this.getPersonalTransInfo(advanceBasicBean,
							monthYear, pensionNo, con);
					String[] details = transData.split(",");
					String emoluments = "", empPFStatuary = "";
					for (int i = 0; i < details.length; i++) {
						if (i == 0)
							emoluments = details[0];
						if (i == 1)
							empPFStatuary = details[1];
					}

					if (!emoluments.equals("")) {
						advanceBasicBean.setEmoluments(emoluments);
					} else {
						advanceBasicBean.setEmoluments("0.0");
					}

					if (!empPFStatuary.equals("")) {
						advanceBasicBean.setPfStatury(empPFStatuary);
					} else {
						advanceBasicBean.setPfStatury("0.0");
					}
					if (rs.getDate("FINALSETTLMENTDT") != null) {
						advanceBasicBean.setFinalSettlementDate(CommonUtil.converDBToAppFormat(rs
								.getDate("FINALSETTLMENTDT")));
					} else {
						advanceBasicBean.setFinalSettlementDate("---");
					}
					if (rs.getDate("RESETTLEMENTDATE") != null) {
						advanceBasicBean.setReSettlementDate(CommonUtil.converDBToAppFormat(rs
								.getDate("RESETTLEMENTDATE")));
					} else {
						advanceBasicBean.setReSettlementDate("---");
					}
					if (rs.getString("ADVANCEMONTHS") != null) {
						advanceBasicBean.setAdvInsMonths(rs.getString("ADVANCEMONTHS"));
					} else {
						advanceBasicBean.setAdvInsMonths("0");
					}
					loadPersList.add(advanceBasicBean);
				}

			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(rs, st, con);
			}
			return loadPersList;
		}

	public String getPersonalTransInfo(AdvanceBasicBean basicBean,
			String monthYear, String pensionNo, Connection con) {
		StringBuffer buffer = new StringBuffer();
		Statement st = null;
		ResultSet rs = null;
		String emoluments = "", empPFStatuary = "";
		try {
			st = con.createStatement();

			String transQuery = "select round(EMOLUMENTS) as EMOLUMENTS,round(EMPPFSTATUARY) as EMPPFSTATUARY FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and PENSIONNO='"
					+ pensionNo
					+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
					+ monthYear + "'";

			log.info("transQuery------" + transQuery);
			rs = st.executeQuery(transQuery);
			if (rs.next()) {

				if (rs.getString("EMOLUMENTS") != null) {
					emoluments = rs.getString("EMOLUMENTS");
				} else {
					emoluments = "";
				}

				buffer.append(emoluments);
				buffer.append(",");

				if (rs.getString("EMPPFSTATUARY") != null) {
					empPFStatuary = rs.getString("EMPPFSTATUARY");
				} else {
					empPFStatuary = "";
				}

				buffer.append(empPFStatuary);

			}
		} catch (Exception e) {

		}
		return buffer.toString();

	}

//	13-Aug-2010 radha FS Arrears load details seperationdate,seperatereason
	public String buildQueryForPersonalTrans(String employeeName,
				String region, String pensionNo, String cpfaccno, String monthYear) {
			log.info("CommonDAO:buildQueryForPersonalTrans-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "", orderyBy = "", sqlQuery = "";

			/*
			 * sqlQuery = "SELECT EPV.MONTHYEAR AS MONTHYEAR,EMPFID.ENTITLEDIFF AS
			 * ENTITLEDIFF ,EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.WETHEROPTION AS
			 * WETHEROPTION,EMPFID.DATEOFBIRTH as
			 * DATEOFBIRTH,EPV.EMPFLAG,EPV.EMOLUMENTS AS
			 * EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EMPFID.EMPLOYEENAME As
			 * EMPLOYEENAME,EMPFID.DEPARTMENT AS ,EMPFID.DESEGNATION AS
			 * DESEGNATION," + "EMPFID.EMAILID AS EMAILID,EMPFID.DATEOFJOINING AS
			 * DATEOFJOINING,EMPFID.FHNAME AS FHNAME,EMPFID.EMPSERIALNUMBER AS
			 * PENSIONNO,EPV.REGION AS REGION,EPV.CPFACCNO AS
			 * CPFACCNO,EPV.AIRPORTCODE AS AIRPORTCODE FROM (SELECT MONTHYEAR," +
			 * "TO_CHAR(MONTHYEAR,'Mon') AS MONTH,ROUND(EMOLUMENTS) AS
			 * EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY," +
			 * "AIRPORTCODE,REGION,EMPFLAG,CPFACCNO FROM EMPLOYEE_PENSION_VALIDATE
			 * WHERE empflag='Y' and to_char(monthyear,'dd-Mon-yyyy') like'%" +
			 * monthYear + "') EPV ,(SELECT
			 * REGION,CPFACNO,EMPLOYEENAME,AIRPORTCODE,DEPARTMENT,DESEGNATION,EMPLOYEENO,WETHEROPTION,DATEOFBIRTH,DATEOFJOINING," +
			 * "FHNAME,EMAILID,EMPSERIALNUMBER,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3)
			 * ENTITLEDIFF FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IN (SELECT
			 * PENSIONNO FROM EMPLOYEE_PERSONAL_INFO ";
			 */
			sqlQuery = "select PENSIONNO,EMPLOYEENO,newempcode,CPFACNO,AIRPORTCODE,EMPLOYEENAME,FHNAME,DATEOFBIRTH,DATEOFJOINING,DESEGNATION,DEPARTMENT,REGION,PERMANENTADDRESS,TEMPORATYADDRESS,EMAILID,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,DATEOFSEPERATION_DATE,DATEOFSEPERATION_REASON,FINALSETTLMENTDT,RESETTLEMENTDATE,(case when dateofseperation_date is not null or dateofseperation_reason is not null then 0 else case when to_char(DATEOFBIRTH, 'dd') = '01' then (720 - round(months_between(sysdate, DATEOFBIRTH)))  else case when (720 - floor(months_between(sysdate, DATEOFBIRTH))) < 36 then (720 - floor(months_between(sysdate, DATEOFBIRTH))) else 36 end end end) as ADVANCEMONTHS from EMPLOYEE_PERSONAL_INFO ";

			if (!pensionNo.equals("")) {
				whereClause.append(" PENSIONNO ='" + pensionNo.toLowerCase() + "'");
				whereClause.append(" AND ");
			}

			if (!employeeName.equals("")) {
				whereClause.append(" LOWER(employeename) like'%"
						+ employeeName.toLowerCase() + "%'");
				whereClause.append(" AND ");
			}

			if (!region.equals("")) {
				whereClause.append(" REGION='" + region + "'");
				whereClause.append(" AND ");

			}
			if (!cpfaccno.equals("")) {
				whereClause.append(" CPFACNO='" + cpfaccno + "'");
				whereClause.append(" AND ");

			}
			query.append(sqlQuery);
			if (region.equals("") && employeeName.equals("")
					&& pensionNo.equals("") && cpfaccno.equals("")) {

			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
			dynamicQuery = query.toString();

			// dynamicQuery=dynamicQuery+" )) EMPFID WHERE EMPFID.REGION=EPV.REGION
			// AND EMPFID.CPFACNO =EPV.CPFACCNO AND EPV.EMPFLAG='Y' ORDER BY
			// EMPSERIALNUMBER";
			log
					.info("CommonDAO:CommonDAO:buildQueryForPersonalTrans Leaving Method");
			return dynamicQuery;
		}

	public ArrayList loadPersonalTransactionInfo(String employeeName,
			String region, String pensionNo, String cpfaccno, String monthYear) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicBean advanceBasicBean = null;
		ArrayList loadPersList = new ArrayList();
		String sqlQuery = "";
		sqlQuery = this.buildQueryForPersonalTransInfo(employeeName, region,
				pensionNo, cpfaccno, monthYear);
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadPersonalTransInfo" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();

				if (rs.getString("EMPLOYEENO") != null) {
					advanceBasicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					advanceBasicBean.setEmployeeNo("---");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					advanceBasicBean.setDateOfBirth(CommonUtil.getDatetoString(
							rs.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					advanceBasicBean
							.setDesignation(rs.getString("DESEGNATION"));
				} else {
					advanceBasicBean.setDesignation("");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					advanceBasicBean.setEmployeeName(rs
							.getString("EMPLOYEENAME"));
				} else {
					advanceBasicBean.setEmployeeName("");
				}
				if (rs.getString("REGION") != null) {
					advanceBasicBean.setRegion(rs.getString("REGION"));
				} else {
					advanceBasicBean.setRegion("");
				}
				if (rs.getString("DATEOFJOINING") != null) {
					advanceBasicBean.setDateOfJoining(CommonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setDateOfJoining("");
				}

				if (rs.getString("FHNAME") != null) {
					advanceBasicBean.setFhName(rs.getString("FHNAME"));
				} else {
					advanceBasicBean.setFhName("");
				}
				if (rs.getString("PENSIONNO") != null) {
					advanceBasicBean.setPensionNo(rs.getString("PENSIONNO"));
				}

				if (rs.getString("CPFACNO") != null) {
					advanceBasicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					advanceBasicBean.setCpfaccno("");
				}

				if (!advanceBasicBean.getDateOfBirth().equals("---")) {
					String personalPFID = this.getPFID(advanceBasicBean
							.getEmployeeName(), advanceBasicBean
							.getDateOfBirth(), commonUtil.leadingZeros(5,
							advanceBasicBean.getPensionNo()));
					advanceBasicBean.setPfid(personalPFID);
				} else {
					advanceBasicBean.setPfid(commonUtil.leadingZeros(5,
							advanceBasicBean.getPensionNo()));
				}

				loadPersList.add(advanceBasicBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return loadPersList;
	}

	public ArrayList loadNomineeInfo(String employeeName, String region,
			String pensionNo, String monthYear) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicBean advanceBasicBean = null;
		ArrayList loadNomineeList = new ArrayList();
		String sqlQuery = "";
		sqlQuery = "select SRNO,NOMINEENAME,NOMINEEDOB,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE from employee_nominee_dtls where EMPFLAG='Y' and  PENSIONNO='"
				+ pensionNo + "' order by  SRNO";
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadPersonalTransInfo" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();

				if (rs.getString("SRNO") != null) {
					advanceBasicBean.setSerialNo(rs.getString("SRNO"));
				} else {
					advanceBasicBean.setSerialNo("");
				}

				if (rs.getString("NOMINEENAME") != null) {
					advanceBasicBean
							.setNomineename(rs.getString("NOMINEENAME"));
				} else {
					advanceBasicBean.setNomineename("");
				}

				if (rs.getString("NOMINEEDOB") != null) {
					advanceBasicBean.setNomineeDOB(CommonUtil.getDatetoString(
							rs.getDate("NOMINEEDOB"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setNomineeDOB("");
				}

				if (rs.getString("NOMINEEADDRESS") != null) {
					advanceBasicBean.setNomineeaddress(rs
							.getString("NOMINEEADDRESS"));
				} else {
					advanceBasicBean.setNomineeaddress("");
				}

				if (rs.getString("NOMINEERELATION") != null) {
					advanceBasicBean.setNomineerelation(rs
							.getString("NOMINEERELATION"));
				} else {
					advanceBasicBean.setNomineerelation("");
				}

				if (rs.getString("NAMEOFGUARDIAN") != null) {
					advanceBasicBean.setGardianname(rs
							.getString("NAMEOFGUARDIAN"));
				} else {
					advanceBasicBean.setGardianname("");
				}

				if (rs.getString("GAURDIANADDRESS") != null) {
					advanceBasicBean.setGardianaddress(rs
							.getString("GAURDIANADDRESS"));
				} else {
					advanceBasicBean.setGardianaddress("");
				}

				if (rs.getString("TOTALSHARE") != null) {
					advanceBasicBean.setTotalshare(rs.getString("TOTALSHARE"));
				} else {
					advanceBasicBean.setTotalshare("");
				}
				loadNomineeList.add(advanceBasicBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return loadNomineeList;
	}
//	BY Radha On 09-May-2012 for Getting Station & Region of the Employee
//	BY Radha On 07-May-2012 for Getting Designation of the Employee
	public AdvanceBasicBean loadNoteSheetInfo(String pensionNo,
			String sanctionNo) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicBean advanceBasicBean = null;
		ArrayList loadNomineeList = new ArrayList();
		String sqlQuery = "", remarks = "",transDt="",station="",region="";
		sqlQuery = "select info.employeename as EMPLOYEENAME ,info.REGION AS REGION_PERSNL, info.AIRPORTCODE AS AIRPORTCODE_PERSNL, NSSANCTIONNO,AAISANCTIONNO,EN.REGION AS REGION,EN.AIRPORTCODE AS AIRPORTCODE ,SOREMARKS,SEPERATIONRESAON,SEPERATIONDT,TRUST,EMPSHARESUBSCRIPITION,EMPSHARECONTRIBUTION,LESSCONTRIBUTION,NETCONTRIBUTION,"
				+" ADHOCAMOUNT,NSSANCTIONEDDT,AMTADMITTEDDT,PAYMENTDT,en.REMARKS AS REMARKS ,SEPERATIONFAVOUR,SEPERATIONREMARKS,VERIFIEDBY,PAYMENTINFO,LOD,PLACEOFPOSTING,REASONFORRESIGNATION,ARREARTYPE,ARREARDATE,FROMFINYEAR,TOFINYEAR,FROMREVISEDINTERESTRATE,"
				+" TOREVISEDINTERESTRATE,POSTINGFLAG,POSTINGREGION,POSTINGSTATION,EN.DESIGNATION AS DESIGNATION,EN.TRANSDT AS TRANSDT  from employee_advance_noteparam EN,employee_personal_info info  where EN.pensionno=info.pensionno and EN.PENSIONNO='"
				+ pensionNo + "' and  NSSANCTIONNO='" + sanctionNo + "'";
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadNoteSheetInfo" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();
				
				if (rs.getString("EMPLOYEENAME") != null) {
					advanceBasicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					advanceBasicBean.setEmployeeName("");
				}				
				if (rs.getString("ADHOCAMOUNT") != null) {
					advanceBasicBean.setAdhocamt(rs.getString("ADHOCAMOUNT"));
				} else {
					advanceBasicBean.setAdhocamt("");
				}
				if (rs.getString("NSSANCTIONNO") != null) {
					advanceBasicBean.setNssanctionno(rs
							.getString("NSSANCTIONNO"));
				} else {
					advanceBasicBean.setNssanctionno("");
				}
				if (rs.getString("TRUST") != null) {
					advanceBasicBean.setTrust(rs.getString("TRUST"));
				} else {
					advanceBasicBean.setTrust("");
				}
				if (rs.getString("AAISANCTIONNO") != null) {
					advanceBasicBean.setSanctionno(rs
							.getString("AAISANCTIONNO"));
				} else {
					advanceBasicBean.setSanctionno("");
				}
				if (rs.getString("SOREMARKS") != null) {
					 
					advanceBasicBean.setSoremarks(rs.getString("SOREMARKS"));
				} else {
					advanceBasicBean.setSoremarks("");
				}
				if (rs.getString("SEPERATIONFAVOUR") != null) {
					advanceBasicBean.setSeperationfavour(rs
							.getString("SEPERATIONFAVOUR"));
				} else {
					advanceBasicBean.setSeperationfavour("");
				}

				if (rs.getString("SEPERATIONREMARKS") != null) {
					advanceBasicBean.setSeperationremarks(rs
							.getString("SEPERATIONREMARKS"));
				} else {
					advanceBasicBean.setSeperationremarks("");
				}

				if (rs.getString("SEPERATIONRESAON") != null) {
					advanceBasicBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));
				} else {
					advanceBasicBean.setSeperationreason("");
				}

				if (rs.getString("SEPERATIONDT") != null) {
					advanceBasicBean.setSeperationdate(CommonUtil
							.getDatetoString(rs.getDate("SEPERATIONDT"),
									"dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setSeperationdate("");
				}

				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					advanceBasicBean.setEmplshare(rs
							.getString("EMPSHARESUBSCRIPITION"));
				} else {
					advanceBasicBean.setEmplshare("");
				}

				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					advanceBasicBean.setEmplrshare(rs
							.getString("EMPSHARECONTRIBUTION"));
				} else {
					advanceBasicBean.setEmplrshare("");
				}

				if (rs.getString("LESSCONTRIBUTION") != null) {
					advanceBasicBean.setPensioncontribution(rs
							.getString("LESSCONTRIBUTION"));
				} else {
					advanceBasicBean.setPensioncontribution("");
				}

				if (rs.getString("NETCONTRIBUTION") != null) {
					advanceBasicBean.setNetcontribution(rs
							.getString("NETCONTRIBUTION"));
				} else {
					advanceBasicBean.setNetcontribution("");
				}

				if (rs.getString("NSSANCTIONEDDT") != null) {
					advanceBasicBean.setSanctiondt(CommonUtil.getDatetoString(
							rs.getDate("NSSANCTIONEDDT"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setSanctiondt("");
				}

				if (rs.getString("AMTADMITTEDDT") != null) {
					advanceBasicBean.setAmtadmtdate(CommonUtil.getDatetoString(
							rs.getDate("AMTADMITTEDDT"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setAmtadmtdate("");
				}

				if (rs.getString("PAYMENTDT") != null) {
					advanceBasicBean.setPaymentdt(CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setPaymentdt("");
				}

				if (rs.getString("REMARKS") != null) {
					// remarks=rs.getString("REMARKS");
					// advanceBasicBean.setRemarks(this.loadRemarks(remarks,","));
					advanceBasicBean.setRemarks(rs.getString("REMARKS"));
				} else {
					advanceBasicBean.setRemarks("");
				}

				if (rs.getString("VERIFIEDBY") != null) {
					advanceBasicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {
					advanceBasicBean.setVerifiedby("");
				}
				
				
				if (rs.getString("PAYMENTINFO") != null) {
					advanceBasicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					advanceBasicBean.setPaymentinfo("");
				}
				
				
				if (rs.getString("LOD") != null) {
					advanceBasicBean.setLodInfo(rs.getString("LOD"));
				} else {
					advanceBasicBean.setLodInfo("");
				}
				
				if (rs.getString("PLACEOFPOSTING") != null) {
					advanceBasicBean.setPostingdetails(rs.getString("PLACEOFPOSTING"));
				} else {
					advanceBasicBean.setPostingdetails("");
				}
				
				if (rs.getString("REASONFORRESIGNATION") != null) {
					advanceBasicBean.setResignationreason(rs.getString("REASONFORRESIGNATION"));
				} else {
					advanceBasicBean.setResignationreason("");
				}
				
				if (rs.getString("ARREARTYPE") != null) {
					advanceBasicBean.setArreartype(rs.getString("ARREARTYPE"));
				} else {
					advanceBasicBean.setArreartype("");
				}
				
				if (rs.getString("ARREARDATE") != null) {
					advanceBasicBean.setArreardate(CommonUtil.getDatetoString(rs
							.getDate("ARREARDATE"), "dd-MMM-yyyy"));
				} else {
					advanceBasicBean.setArreardate("");
				}
				
				
				if (rs.getString("FROMFINYEAR") != null) {
					advanceBasicBean.setFromfinyear(CommonUtil.getDatetoString(rs.getDate("FROMFINYEAR"),"dd-MMM-yyyy").substring(7,11));
				} else {
					advanceBasicBean.setFromfinyear("");
				}
				
				if (rs.getString("TOFINYEAR") != null) {
					advanceBasicBean.setTofinyear(CommonUtil.getDatetoString(rs.getDate("TOFINYEAR"),"dd-MMM-yyyy").substring(7,11));
				} else {
					advanceBasicBean.setTofinyear("");
				}
				
				if (rs.getString("FROMREVISEDINTERESTRATE") != null) {
					advanceBasicBean.setInterestratefrom(rs.getString("FROMREVISEDINTERESTRATE"));
				} else {
					advanceBasicBean.setInterestratefrom("");
				}
				
				if (rs.getString("TOREVISEDINTERESTRATE") != null) {
					advanceBasicBean.setInterestrateto(rs.getString("TOREVISEDINTERESTRATE"));
				} else {
					advanceBasicBean.setInterestrateto("");
				}
				log.info("Posting Flag"+rs.getString("POSTINGFLAG"));
				if (rs.getString("POSTINGFLAG") != null) {
					advanceBasicBean.setPostingFlag(rs.getString("POSTINGFLAG"));
				} else {
					advanceBasicBean.setPostingFlag("N");
				}
				
				if (rs.getString("POSTINGREGION") != null) {
					advanceBasicBean.setPostingRegion(rs.getString("POSTINGREGION"));
				} else {
					advanceBasicBean.setPostingRegion("");
				}
				
				if (rs.getString("POSTINGSTATION") != null) {
					advanceBasicBean.setPostingStation(rs.getString("POSTINGSTATION"));
				} else {
					advanceBasicBean.setPostingStation("---");
				}
				if (rs.getString("DESIGNATION") != null) {
					advanceBasicBean.setDesignation(rs.getString("DESIGNATION"));
				} else {
					advanceBasicBean.setDesignation("");
				}
				// Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = CommonUtil.converDBToAppFormat(rs
							.getDate("TRANSDT"));
					Date transdate = df.parse(transDt); 
					if(transdate.after(new Date("08-May-2012"))){
						station = rs.getString("AIRPORTCODE") ;
						region = rs.getString("REGION");
					}else{                  
						station = rs.getString("AIRPORTCODE_PERSNL") ;
						region =  rs.getString("REGION_PERSNL") ;
					}
				}else{
					station = rs.getString("AIRPORTCODE_PERSNL") ;
					region =  rs.getString("REGION_PERSNL") ;					
				}
				if (region != null) {
					advanceBasicBean.setRegion(region);
				} else {
					advanceBasicBean.setRegion("");
				}
				if (station != null) {
					advanceBasicBean.setStation(station);
				} else {
					advanceBasicBean.setStation("");
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return advanceBasicBean;
	}


	public String loadRemarks(String remarks, String tokens) {
		String[] remark = remarks.split(tokens);
		StringBuffer buffer = new StringBuffer();

		int j = 0;
		for (int i = 0; i < remark.length; i++) {
			j++;
			if (!remark[i].equals("")) {
				buffer.append("\n" + remark[i]);
				buffer.append("<br/>");
			}
		}
		return buffer.toString();
	}

	public double getRateOfInterest(String year) {
		double rateOfInterest = 0.0;
		if (Integer.parseInt(year) >= 1995 && Integer.parseInt(year) < 2000) {
			rateOfInterest = 12;
		} else if (Integer.parseInt(year) >= 2000
				&& Integer.parseInt(year) < 2001) {
			rateOfInterest = 11;
		} else if (Integer.parseInt(year) >= 2001
				&& Integer.parseInt(year) < 2005) {
			rateOfInterest = 9.50;
		} else if (Integer.parseInt(year) >= 2005
				&& Integer.parseInt(year) <= 2009) {
			rateOfInterest = 8.50;
		}
		return rateOfInterest;
	}

	public String getHBAPurposeOptionDesc(String purposeOptionType) {
		String purposeDesc = "";
		if (purposeOptionType.equals("PURCHASESITE")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_PURCHASESITE;
		} else if (purposeOptionType.equals("PURCHASEHOUSE")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_PURCHASEHOUSE;
		} else if (purposeOptionType.equals("CONSTRUCTIONHOUSE")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_CONSTRUCTIONHOUSE;
		} else if (purposeOptionType.equals("ACQUIREFLAT")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_ACQUIREFLAT;
		} else if (purposeOptionType.equals("RENOVATIONHOUSE")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_RENOVATIONHOUSE;
		} else if (purposeOptionType.equals("REPAYMENTHBA")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_REPAYMENTHBA;
		} else if (purposeOptionType.equals("HBAOTHERS")) {
			purposeDesc = Constants.APPLICATION_HBA_PURPOSE_HBAOTHERS;
		}
		return purposeDesc;
	}
// By Radha On 25-Jan-2012 for Loading staions based on Profile(SAU/RAU) basis 
	public ArrayList loadStations(AdvanceBasicBean advanceBean) {
		ArrayList stationList = new ArrayList();
		AdvanceBasicBean advanceBasicBean = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String profileName="",profileCond="",accounttype="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			profileName = advanceBean.getProfileName();
			if(profileName.equals("R")){
				accounttype="RAU";
				profileCond=" and accounttype='"+accounttype+"'";
			}else{
				profileCond="";
			}
			String query = "select distinct(UNITNAME) from employee_unit_master where region='"
					+ advanceBean.getRegion() + "' "+profileCond;
			
			log.info("=========query============" + query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();
				advanceBasicBean.setStation(rs.getString("UNITNAME"));
				log.info("-----Station in CommonDAO------"
						+ advanceBasicBean.getStation());
				stationList.add(advanceBasicBean);
			}

		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>" + e.getMessage());
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return stationList;
	}

	public int getPFIDsNaviagtionListSize(int totalSize) {
		log.info("CommonDAO :getPFIDsNaviagtionListSize() Entering Method ");
		Connection con = null;
		Statement st = null;
		int pfidTotal = 0, eachPage = 0;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_PERSONAL_INFO ";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				pfidTotal = rs.getInt("COUNT");
			}

			eachPage = Math.round(pfidTotal / totalSize + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		log.info("CommonDAO :getPFIDsNaviagtionListSize() Leaving Method ");
		return eachPage;
	}

	public AdvanceBasicBean checkSanctionDate(String pensionNo) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		ArrayList loadNomineeList = new ArrayList();
		String sqlQuery = "", remarks = "";
		int count = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "select PENSIONNO,NSSANCTIONEDDT,VERIFIEDBY from employee_advance_noteparam where DELETEFLAG='N' and PENSIONNO='"
					+ pensionNo + "'";
			log.info("CommonDAO::checkSanctionDate" + sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("PENSIONNO") != null) {
					advanceBasicBean.setPensionNo(rs.getString("PENSIONNO"));
				} else {
					advanceBasicBean.setPensionNo("");
				}

				if (rs.getString("NSSANCTIONEDDT") != null) {
					advanceBasicBean.setSanctiondt(rs
							.getString("NSSANCTIONEDDT"));
				} else {
					advanceBasicBean.setSanctiondt("");
				}

				if (rs.getString("VERIFIEDBY") != null) {
					advanceBasicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {
					advanceBasicBean.setVerifiedby("");
				}
			}

		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>" + e.getMessage());
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return advanceBasicBean;
	}
	 public ArrayList navgtionPFIDsList(String region,String airportcode,String range,String monthYear,String mnthYearFlag) {
	        log.info("CommonDAO :navgtionPFIDsList() Entering Method ");
	        ArrayList rangeList=new ArrayList();
	        Connection con = null;
	        Statement st=null;
	        int pfidTotal=0,eachPage=0;
	        ResultSet rs = null;
	        String airportCodeString="",regionString="",personalQry="",sqlQuery="",fromID="",toID="",pfidString="";
	        if(!airportcode.equals("NO-SELECT")){
	        	airportCodeString=airportcode;
	        }else{
	        	airportCodeString="";
	        }
	        if(!region.equals("NO-SELECT")){
	        	regionString=region;
	        }else{
	        	regionString="";
	        }
	        if(mnthYearFlag.equals("true")){
	        	personalQry=this.buildQueryTransNavgationPFList(regionString,airportCodeString,monthYear);	
	        }else{
	        	personalQry=this.buildQueryNavgationPFList(regionString,airportCodeString);	
	        	
	        }
	        
	        sqlQuery="SELECT SFROM, STO FROM (SELECT ((MYSEQ - 1) * "+range+" + 1) SFROM, (MYSEQ * "+range+") STO FROM (SELECT LEVEL MYSEQ FROM DUAL  CONNECT BY LEVEL <= 1000 "+
	        ")) SEQ, ("+personalQry+") PNO WHERE PNO.PENSIONNO BETWEEN SFROM AND STO GROUP BY SFROM, STO ORDER BY SFROM, STO";
	        log.info(sqlQuery);
	        try {
	                con = commonDB.getConnection();
	                st = con.createStatement();
	                rs = st.executeQuery(sqlQuery);
	                while (rs.next()) {
	                	if(rs.getString("SFROM")!=null){
	                		fromID=rs.getString("SFROM");
	                	}
	                	if(rs.getString("STO")!=null){
	                		toID=rs.getString("STO");
	                	}
	                	pfidString=fromID+" - "+toID;
	                	rangeList.add(pfidString);
	                }	
	               
	               
	                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            commonDB.closeConnection(con, st, rs);
	        }
	        log.info("CommonDAO :navgtionPFIDsList() Leaving Method ");
	        return rangeList;
	    }
	 public String buildQueryTransNavgationPFList(String region,String airportCode,String monthYear) {
	    	log.info("CommonDAO::buildQueryTransNavgationPFList-- Entering Method");
	    	StringBuffer whereClause = new StringBuffer();
	    	StringBuffer query = new StringBuffer();
	    	String dynamicQuery = "",sqlQuery = "";
	    	
	    	sqlQuery="SELECT DISTINCT PENSIONNO FROM EMPLOYEE_PENSION_VALIDATE";

	    	if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
	  
	    	if (!airportCode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportCode + "'");
				whereClause.append(" AND ");
			}
	    	if (!monthYear.equals("")) {
				whereClause.append(" TO_CHAR(MONTHYEAR,'dd-MM-yyyy') like'%"
						+ monthYear + "'");
				whereClause.append(" AND ");
			}
			query.append(sqlQuery);
			if ((region.equals("")) && (airportCode.equals(""))) {

			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
	    	dynamicQuery = query.toString();
	    	log.info("CommonDAO::buildQueryTransNavgationPFList Leaving Method");
	    	return dynamicQuery;
	    }
	 public String getLatestTrnsDate(String region) {
			Connection con = null;
			Statement st=null;
			ResultSet rs = null;
			String monthYear="";
			
			try {
				con = commonDB.getConnection();
				 st = con.createStatement();
				String query = "SELECT ('-' || TO_CHAR(MAX(MONTHYEAR),'MM') || '-' || TO_CHAR(MAX(MONTHYEAR),'YYYY')) AS INFORMAT FROM EMPLOYEE_PENSION_VALIDATE WHERE REGION='"+region+"'";
				log.info("getLatestTrnsDate==query==========="+query);
				rs = st.executeQuery(query);
				if (rs.next()) {
					if (rs.getString("INFORMAT") != null) {
						monthYear = rs.getString("INFORMAT");
					}
					
				}

			} catch (SQLException e) {
				log.printStackTrace(e);
			}catch (Exception e) {
				log.printStackTrace(e);
			}finally{
				
			}
			return monthYear;

		}
	 public String buildQueryNavgationPFList(String region,String airportCode) {
	    	log.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting-- Entering Method");
	    	StringBuffer whereClause = new StringBuffer();
	    	StringBuffer query = new StringBuffer();
	    	String dynamicQuery = "",sqlQuery = "";
	    	
	    	sqlQuery="SELECT PENSIONNO FROM EMPLOYEE_PERSONAL_INFO";

	    	if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
	  
	    	if (!airportCode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportCode + "'");
				whereClause.append(" AND ");
			}
			query.append(sqlQuery);
			if ((region.equals("")) && (airportCode.equals(""))) {

			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
	    	dynamicQuery = query.toString();
	    	log.info("FinanceReportDAO::buildQueryNavgationPFList Leaving Method");
	    	return dynamicQuery;
	    }
	 public EmployeePersonalInfo getEmployeePersonalInfo(String range,String region,String airportcode, String employeeName,String pensionno) {
			CommonDAO commonDAO = new CommonDAO();
			Connection con=null;
			Statement st = null;
			ResultSet rs = null;
			String sqlQuery = "";
			EmployeePersonalInfo data = null;
		
			try {
				con=commonDB.getConnection();
				st = con.createStatement();
				log.info("CommonDAO::getEmployeePersonalInfo"+pensionno);
				sqlQuery = this.buildQueryEmployeeInfoPrinting(range,
							region,airportcode, employeeName,  pensionno);
			
				
				log.info("CommonDAO::getEmployeePFInfoPrinting" + sqlQuery);
				rs = st.executeQuery(sqlQuery);
				
				if (rs.next()) {
					data = new EmployeePersonalInfo();
					
					data = commonDAO.loadPersonalInfo(rs);
					log.info("date flag"
							+ this.checkDOB(data.getDateOfBirth().trim()));
					if (this.checkDOB(data.getDateOfBirth().trim()) == true) {
						if (rs.getString("LASTDOB") != null) {
							data.setDateOfAnnuation(rs.getString("LASTDOB"));
						} else {
							data.setDateOfAnnuation("");
						}
					} else {
						if (rs.getString("DOR") != null) {
							data.setDateOfAnnuation(commonUtil.getDatetoString(rs
									.getDate("DOR"), "dd-MMM-yyyy"));
						} else {
							data.setDateOfAnnuation("");
						}
					}
					if (rs.getString("FINALSETTLMENTDT") != null) {
						data.setFinalSettlementDate(commonUtil.getDatetoString(rs
								.getDate("FINALSETTLMENTDT"), "dd-MMM-yyyy"));
					} else {
						data.setFinalSettlementDate("---");
					}
					
					if (rs.getString("DATEOFENTITLE") != null) {
						data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
					} else {
						data.setDateOfEntitle("");
					}
					
					if (data.getWetherOption().trim().equals("A")) {
						data.setWhetherOptionDescr("Full Pay");
					} else if (data.getWetherOption().trim().equals("B")
							|| data.getWetherOption().trim().equals("NO")) {
						data.setWhetherOptionDescr("Celing");
					}
		
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return data;
		}
	 public String lookforMonth(String pensionNo,String currentDate)
		{
			Connection con = null;
			Statement st=null;
			ResultSet rs = null;
			String monthYear="",query="";
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				query="SELECT * FROM EMPLOYEE_PENSION_VALIDATE  WHERE PENSIONNO='"+pensionNo+"'AND TO_CHAR(MONTHYEAR,'dd-MM-yyyy') like'%"+currentDate+ "'";
				System.out.println("Query-----------------"+query); 
				rs=st.executeQuery(query);
				if (rs.next()) {
					monthYear = currentDate;
				} else {
					commonDB.closeConnection(null,st,rs);
					query = "SELECT TO_CHAR(MAX(MONTHYEAR),'dd-mm-yyyy') FROM EMPLOYEE_PENSION_VALIDATE  WHERE PENSIONNO='"
						+ pensionNo + "' ";
					st = con.createStatement();
					rs = st.executeQuery(query);
					if (rs.next()) {
						monthYear = rs.getString(1);
					}
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			}catch (Exception e) {
				log.printStackTrace(e);
			}finally{
				commonDB.closeConnection(con, st, rs);
			}	
			return monthYear;
		}

	public String loadNomineeDetails(String employeeName, String region,
				String pensionNo, String monthYear) {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AdvanceBasicBean advanceBasicBean = null;
			ArrayList loadNomineeList = new ArrayList();
			StringBuffer buffer = new StringBuffer();
			String sqlQuery = "";
			sqlQuery = "select SRNO,NOMINEENAME,NOMINEEDOB,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,EQUALSHARE from employee_nominee_dtls where EMPFLAG='Y' and  PENSIONNO='"
					+ pensionNo + "' order by  SRNO";
			System.out.println(sqlQuery);
			log.info("CommonDAO::loadNomineeDetails" + sqlQuery);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					advanceBasicBean = new AdvanceBasicBean();

					if (rs.getString("SRNO") != null) {
						advanceBasicBean.setSerialNo(rs.getString("SRNO"));
					} else {
						advanceBasicBean.setSerialNo("");
					}

					if (rs.getString("NOMINEENAME") != null) {
						advanceBasicBean
								.setNomineename(rs.getString("NOMINEENAME"));
					} else {
						advanceBasicBean.setNomineename("");
					}

					if (rs.getString("NOMINEEDOB") != null) {
						advanceBasicBean.setNomineeDOB(CommonUtil.getDatetoString(
								rs.getDate("NOMINEEDOB"), "dd-MMM-yyyy"));
					} else {
						advanceBasicBean.setNomineeDOB("");
					}

					if (rs.getString("NOMINEEADDRESS") != null) {
						advanceBasicBean.setNomineeaddress(rs
								.getString("NOMINEEADDRESS"));
					} else {
						advanceBasicBean.setNomineeaddress("");
					}

					if (rs.getString("NOMINEERELATION") != null) {
						advanceBasicBean.setNomineerelation(rs
								.getString("NOMINEERELATION"));
					} else {
						advanceBasicBean.setNomineerelation("");
					}

					if (rs.getString("NAMEOFGUARDIAN") != null) {
						advanceBasicBean.setGardianname(rs
								.getString("NAMEOFGUARDIAN"));
					} else {
						advanceBasicBean.setGardianname("");
					}

					if (rs.getString("GAURDIANADDRESS") != null) {
						advanceBasicBean.setGardianaddress(rs
								.getString("GAURDIANADDRESS"));
					} else {
						advanceBasicBean.setGardianaddress("");
					}

					if (rs.getString("TOTALSHARE") != null) {
						advanceBasicBean.setTotalshare(rs.getString("TOTALSHARE"));
					} else {
						advanceBasicBean.setTotalshare("");
					}
					
					
					if (rs.getString("EQUALSHARE") != null) {
						advanceBasicBean.setEqualshare(rs.getString("EQUALSHARE"));
					} else {
						advanceBasicBean.setEqualshare("");
					}
					
					//loadNomineeList.add(advanceBasicBean);
					
					buffer.append(advanceBasicBean.getSerialNo());
					buffer.append("#");
					buffer.append(advanceBasicBean.getNomineename());
					buffer.append("#");
					buffer.append(advanceBasicBean.getNomineeDOB());
					buffer.append("#");
					buffer.append(advanceBasicBean.getNomineeaddress());
					buffer.append("#");
					buffer.append(advanceBasicBean.getNomineerelation());
					buffer.append("#");
					buffer.append(advanceBasicBean.getGardianname());
					buffer.append("#");
					buffer.append(advanceBasicBean.getGardianaddress());
					buffer.append("#");
					buffer.append(advanceBasicBean.getTotalshare());
					buffer.append("#");
					buffer.append(advanceBasicBean.getEqualshare());
					buffer.append(":");
					// reportList.add(buffer.toString());
					log.info("buffer.toString()" + buffer.toString());
				}

			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(rs, st, con);
			}
			return buffer.toString();
		}
	    public String buildQueryEmployeeInfoPrinting(String range,
				String region, 
				String airportcode,String empName, String pensionno) {
			log
			.info("FinanceReportDAO::buildQueryEmployeeInfoPrinting-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "", sqlQuery = "";
			int startIndex = 0, endIndex = 0;
			log.info("pensionno  " +pensionno);
			sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT FROM EMPLOYEE_PERSONAL_INFO ";
			log.info(sqlQuery);
			if (!range.equals("NO-SELECT")) {
				
				String[] findRnge = range.split(" - ");
				startIndex = Integer.parseInt(findRnge[0]);
				endIndex = Integer.parseInt(findRnge[1]);
				
				whereClause.append("  PENSIONNO >=" + startIndex
						+ " AND PENSIONNO <=" + endIndex);
				whereClause.append(" AND ");
				
			}
			
			if (!region.equals("") && !region.equals("AllRegions")) {
				whereClause.append(" REGION ='" + region + "'");
				whereClause.append(" AND ");
			}
			
			if (!airportcode.equals("") ) {
				whereClause.append("AIRPORTCODE='" + airportcode + "' ");
				whereClause.append(" AND ");
			}
			if (!pensionno.equals("") ) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
			if (!empName.equals("") ) {
					whereClause.append("EMPLOYEENAME='" + pensionno + "' ");
					whereClause.append(" AND ");
			}
			
			
			query.append(sqlQuery);
			if (region.equals("")&& (airportcode.equals(""))&& (pensionno.equals(""))
					&& (range.equals("NO-SELECT") && (empName.equals("")) )) {
				
			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
			dynamicQuery = query.toString();
			log
			.info("FinanceReportDAO::buildQueryEmployeeInfoPrinting Leaving Method");
			return dynamicQuery;
		}
		private boolean checkDOB(String dateOfBirth) {
			boolean dtFlag = false;
			String getDay = "";
			log.info("checkDOB===dateOfBirth=" + dateOfBirth);
			getDay = dateOfBirth.substring(0, 2);
			log.info("checkDOB====" + getDay);
			String[] dates = { "01" };
			for (int i = 0; i < dates.length; i++) {
				if (getDay.equals(dates[i])) {
					dtFlag = true;
				}
			}
			return dtFlag;
		}
		
		public ArrayList getAirportsByBulkPFIDSTbl(String region) {
			Connection con = null;
			Statement st=null;
			ArrayList airportList = new ArrayList();
			EmpMasterBean bean=null;
			ResultSet rs = null;
			String unitName = "", unitCode = "";
			
			try {
				con = commonDB.getConnection();
				 st = con.createStatement();
				String query = "SELECT distinct AIRPORTCODE  FROM EPIS_BULK_PRINT_PFIDS where region='"
					+ region+"' and airportcode is not null";
				log.info("getAirportsByFinanceTbl==query==========="+query);
				rs = st.executeQuery(query);
				bean = new EmpMasterBean();
				
				while (rs.next()) {
					bean = new EmpMasterBean();
					if (rs.getString("AIRPORTCODE") != null) {
						unitName = (String) rs.getString("airportcode").toString().trim();
						bean.setStation(unitName);
					}else {
						bean.setStation("");
					}
					
					airportList.add(bean);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.info("error" + e.getMessage());

			}
			return airportList;

		}
		//add  the below methods
		public ArrayList bulkNavgtionPFIDsList(String region,String airportcode,String range,String monthYear,String mnthYearFlag,String finYear) {
	        log.info("CommonDAO :navgtionPFIDsList() Entering Method ");
	        ArrayList rangeList=new ArrayList();
	        Connection con = null;
	        Statement st=null;
	        int pfidTotal=0,eachPage=0;
	        ResultSet rs = null;
	        String airportCodeString="",regionString="",personalQry="",sqlQuery="",fromID="",toID="",pfidString="";
	        if(!airportcode.equals("NO-SELECT")){
	        	airportCodeString=airportcode;
	        }else{
	        	airportCodeString="";
	        }
	        if(!region.equals("NO-SELECT")){
	        	regionString=region;
	        }else{
	        	
	        	
	        	regionString="";
	        }
	  
	        	personalQry=this.buildQueryBulkNavgationPFList(regionString,airportCodeString,finYear);	
	        	
	       
	        
	        sqlQuery="SELECT SFROM, STO FROM (SELECT ((MYSEQ - 1) * "+range+" + 1) SFROM, (MYSEQ * "+range+") STO FROM (SELECT LEVEL MYSEQ FROM DUAL  CONNECT BY LEVEL <= 1000 "+
	        ")) SEQ, ("+personalQry+") PNO WHERE PNO.PENSIONNO BETWEEN SFROM AND STO GROUP BY SFROM, STO ORDER BY SFROM, STO";
	        log.info(sqlQuery);
	        try {
	                con = commonDB.getConnection();
	                st = con.createStatement();
	                rs = st.executeQuery(sqlQuery);
	                while (rs.next()) {
	                	if(rs.getString("SFROM")!=null){
	                		fromID=rs.getString("SFROM");
	                	}
	                	if(rs.getString("STO")!=null){
	                		toID=rs.getString("STO");
	                	}
	                	pfidString=fromID+" - "+toID;
	                	rangeList.add(pfidString);
	                }	
	               
	               
	                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            commonDB.closeConnection(con, st, rs);
	        }
	        log.info("CommonDAO :navgtionPFIDsList() Leaving Method ");
	        return rangeList;
	    }
		public String buildQueryBulkNavgationPFList(String region,String airportCode,String finYear) {
	    	log.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting-- Entering Method");
	    	StringBuffer whereClause = new StringBuffer();
	    	StringBuffer query = new StringBuffer();
	    	String dynamicQuery = "",sqlQuery = "";
	    	
	    	sqlQuery="SELECT PFID AS PENSIONNO FROM EPIS_BULK_PRINT_PFIDS WHERE FINAYEAR='"+finYear+"'";

	    	if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
	  
	    	if (!airportCode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportCode + "'");
				whereClause.append(" AND ");
			}
			query.append(sqlQuery);
			if ((region.equals("")) && (airportCode.equals(""))) {

			} else {
				query.append(" AND ");
				query.append(this.sTokenFormat(whereClause));
			}
	    	dynamicQuery = query.toString();
	    	log.info("FinanceReportDAO::buildQueryNavgationPFList Leaving Method");
	    	return dynamicQuery;
	    }

	 public int getPFIDsNaviagtionList(String region,String airportcode,String transferFlag,int totalSize) {
	        log.info("CommonDAO :getPFIDsNaviagtionList() Entering Method ");
	        Connection con = null;
	        Statement st=null;
	        int pfidTotal=0,eachPage=0;
	        ResultSet rs = null;
	        String airportCodeString="";
	        if(!airportcode.equals("NO-SELECT")){
	        	airportCodeString="AND EPI.AIRPORTCODE='"+airportcode+"'";
	        }else{
	        	airportCodeString="";
	        }
	        
	        String sql = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO AND  EPI.REGION='"+region+ "' " +
	        		" AND ETI.TRANSFERFLAG='"+transferFlag+"' "+airportCodeString;
	        log.info(sql);
	        try {
	                con = commonDB.getConnection();
	                st = con.createStatement();
	                rs = st.executeQuery(sql);
	                if (rs.next()) {
	                	pfidTotal=rs.getInt("COUNT");
	                }	
	               
	                eachPage=Math.round(pfidTotal/totalSize+1);
	                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            commonDB.closeConnection(con, st, rs);
	        }
	        log.info("CommonDAO :getPFIDsNaviagtionList() Leaving Method ");
	        return eachPage;
	    }
	 public ArrayList getPFIDsNaviagtionList(String region,String airportcode,String transferFlag,String range) {
	        log.info("CommonDAO :getPFIDsNaviagtionList() Entering Method ");
	        Connection con = null;
	        Statement st=null;
	        int pfidTotal=0,eachPage=0;
	        ResultSet rs = null;
	        ArrayList rangeList=new ArrayList(); 
	        String airportCodeString="",regionString="",personalQry="",sqlQuery="",fromID="",toID="",pfidString="";
	        if(!airportcode.equals("NO-SELECT")){
	        	airportCodeString=airportcode;
	        }else{
	        	airportCodeString="";
	        }
	        if(!region.equals("NO-SELECT")){
	        	regionString=region;
	        }else{
	        	regionString="";
	        }
	        personalQry=this.buildQueryNavgationForTrnsferPFList(regionString,airportCodeString,transferFlag);
	        sqlQuery="SELECT SFROM, STO FROM (SELECT ((MYSEQ - 1) * "+range+" + 1) SFROM, (MYSEQ * "+range+") STO FROM (SELECT LEVEL MYSEQ FROM DUAL  CONNECT BY LEVEL <= 1000 "+
	        ")) SEQ, ("+personalQry+") PNO WHERE PNO.PENSIONNO BETWEEN SFROM AND STO GROUP BY SFROM, STO ORDER BY SFROM, STO";
	        log.info(sqlQuery);
	        try {
	                con = commonDB.getConnection();
	                st = con.createStatement();
	                rs = st.executeQuery(sqlQuery);
	                while (rs.next()) {
	                	if(rs.getString("SFROM")!=null){
	                		fromID=rs.getString("SFROM");
	                	}
	                	if(rs.getString("STO")!=null){
	                		toID=rs.getString("STO");
	                	}
	                	pfidString=fromID+" - "+toID;
	                	rangeList.add(pfidString);
	                }	
	               
	              
	                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            commonDB.closeConnection(con, st, rs);
	        }
	        log.info("CommonDAO :getPFIDsNaviagtionList() Leaving Method ");
	        return rangeList;
	    }
	 public String buildQueryNavgationForTrnsferPFList(String region,String airportCode,String transferFlag) {
	    	log.info("FinanceReportDAO::buildQueryNavgationForTrnsferPFList-- Entering Method");
	    	StringBuffer whereClause = new StringBuffer();
	    	StringBuffer query = new StringBuffer();
	    	String dynamicQuery = "",sqlQuery = "";
	    	if(!transferFlag.equals("")){
	    		sqlQuery = "SELECT EPI.PENSIONNO FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO ";
	    	}else{
	    		sqlQuery = "SELECT EPI.PENSIONNO FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL  ";
	    	}
	    	
	    

	    	if (!region.equals("")) {
				whereClause.append(" EPI.REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
	  
	    	if (!airportCode.equals("")) {
				whereClause.append(" EPI.AIRPORTCODE ='"
						+ airportCode + "'");
				whereClause.append(" AND ");
			}
	    	if (!transferFlag.equals("")) {
				whereClause.append("  ETI.TRANSFERFLAG ='"
						+ transferFlag + "'");
				whereClause.append(" AND ");
			}
			query.append(sqlQuery);
			if ((region.equals("")) && (airportCode.equals("")) && (transferFlag.equals(""))) {

			} else {
				query.append(" AND ");
				query.append(this.sTokenFormat(whereClause));
			}
	    	dynamicQuery = query.toString();
	    	log.info("FinanceReportDAO::buildQueryNavgationForTrnsferPFList Leaving Method");
	    	return dynamicQuery;
	    }
	 public ArrayList loadModules() {
			Connection con = null;
			Statement st=null;
			ArrayList moduleList = new ArrayList();
			SignatureMappingBean mappingBean = null;
			ResultSet rs = null;
			String moduleName = "", moduleCode = "";
			
			try {
				con = commonDB.getConnection();
				 st = con.createStatement();
				String query = "select replace(name,'&','and') Name ,code from epis_modules";
					 
				log.info("loadModules==query==========="+query);
				rs = st.executeQuery(query);
				mappingBean = new SignatureMappingBean();
				
				while (rs.next()) {
					mappingBean = new SignatureMappingBean();
					if (rs.getString("NAME") != null) {
						moduleName =(String) rs.getString("NAME").toString()
						.trim();
						mappingBean.setModuleName(moduleName);
					}else {
						mappingBean.setModuleName("");
					}
					
					if (rs.getString("code") != null) {
						moduleCode =(String) rs.getString("code").toString()
						.trim();
						mappingBean.setModuleCode(moduleCode);
					}else {
						mappingBean.setModuleCode("");
					}
					
					moduleList.add(mappingBean);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.info("error" + e.getMessage());

			}finally {
				commonDB.closeConnection(con, st, rs);
			}
			return moduleList;

		}
	 
	 public ArrayList loadScreenNames(String moduleCode) {
			Connection con = null;
			Statement st=null;
			ArrayList screenList = new ArrayList();
			SignatureMappingBean mappingBean = null;
			ResultSet rs = null;
			String screenName = "",screenCode="";
			
			try {
				con = commonDB.getConnection();
				 st = con.createStatement();
				String query = "SELECT SCREENCODE, SCREENNAME FROM EPIS_ACCESSCODES_MT mt where mt.MODULECODE='"+moduleCode+"'";
					 
				log.info("loadScreenNames==query==========="+query);
				rs = st.executeQuery(query);
				mappingBean = new SignatureMappingBean();
				
				while (rs.next()) {
					mappingBean = new SignatureMappingBean();
					if (rs.getString("SCREENCODE") != null) {
						screenName =(String) rs.getString("SCREENCODE").toString()
						.trim();
						mappingBean.setScreenName(screenName);
					}else {
						mappingBean.setScreenName("");
					}
					
					if (rs.getString("SCREENNAME") != null) {
						screenCode =(String) rs.getString("SCREENNAME").toString()
						.trim();
						mappingBean.setScreenCode(screenCode);
					}else {
						mappingBean.setScreenCode("");
					}
					
					screenList.add(mappingBean);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.info("error" + e.getMessage());

			}finally {
				commonDB.closeConnection(con, st, rs);
			}
			return screenList;

		}
	 public String getLatestPerDtls(Connection con,String pensionno) {
	        log.info("CommonDAO :getLatestPerDtls() Entering Method ");
	        Statement st=null;
	        int pfidTotal=0,eachPage=0;
	        ResultSet rs1 = null;
	        String airportCode="",region="",perinfo="";
	        
	        
	        String sql = "SELECT AIRPORTCODE,REGION FROM EMPLOYEE_PENSION_VALIDATE WHERE MONTHYEAR IN(SELECT MAX(MONTHYEAR) FROM EMPLOYEE_PENSION_VALIDATE WHERE PENSIONNO="+pensionno+") AND PENSIONNO="+pensionno ;
	        log.info("sql"+sql);
	        try {
	              
	                st = con.createStatement();
	                rs1 = st.executeQuery(sql);
	                if (rs1.next()) {
	                	if(rs1.getString("AIRPORTCODE")!=null){
	                		airportCode=rs1.getString("AIRPORTCODE");
	                	}else{
	                		airportCode="NA";
	                	}
	                	 
	                   	if(rs1.getString("REGION")!=null){
	                   		region=rs1.getString("REGION");
	                	}else{
	                		region="NA";
	                	}
	                	
	                }	
	                perinfo=airportCode+","+region;
	              
	                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            commonDB.closeConnection(con, st, rs1);
	        }
	        log.info("CommonDAO :getLatestPerDtls() Leaving Method ");
	        return perinfo;
	    }
	 
	 public String checkArrears(Connection con, String fromDate,
				String cpfaccno, String employeeno, String region,String Pensionno) {

			String foundEmpFlag = "",arrearamt="";
			Statement st = null;
			ResultSet rs = null;
			try {
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				Date transdate = df.parse(fromDate);
				String transMonthYear = commonUtil.converDBToAppFormat(fromDate
						.trim(), "dd-MMM-yyyy", "-MMM-yy");
				String query="";
				if(Pensionno=="" || transdate.before(new Date("31-Mar-2008"))){
				 query = "select ARREARAMT from employee_pension_arrear where to_char(ARREARDATE,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and cpfaccno='"
						+ cpfaccno
						+ "' and region='" + region + "' ";
				}else{
					query = "select ARREARAMT from employee_pension_arrear where to_char(ARREARDATE,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and pensionno='"
						+ Pensionno
						+ "'  ";	
				}

				st = con.createStatement();
				rs = st.executeQuery(query);

				if (rs.next()) {
					if (rs.getString("ARREARAMT") != null) {
						arrearamt= rs.getString("ARREARAMT");
					}
					
				}
			} catch (SQLException e) {
				// e.printStackTrace();
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
				// e.printStackTrace();
			} finally {

				 commonDB.closeConnection(null, st, rs);

				
			}
			// log.info("PensionDAO :checkPensionDuplicate() leaving method");
			return arrearamt;
		}
	 public com.epis.bean.rpfc.FinacialDataBean getEmolumentsBean(Connection con, String fromDate,
				String cpfaccno, String employeeno, String region,String Pensionno) {

			String foundEmpFlag = "";
			Statement st = null;
			ResultSet rs = null;
			FinacialDataBean bean = new FinacialDataBean();
			try {
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

				Date transdate = df.parse(fromDate);
							
			
				String transMonthYear = commonUtil.converDBToAppFormat(fromDate
						.trim(), "dd-MMM-yyyy", "-MMM-yy");
				String query="";
				if(Pensionno=="" || transdate.before(new Date("31-Mar-2008"))){
				 query = "select emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and cpfaccno='"
						+ cpfaccno
						+ "' and region='" + region + "' and empflag='Y' ";
				}else{
					query = "select emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and pensionno='"
						+ Pensionno
						+ "'  and empflag='Y' ";	
				}

				st = con.createStatement();
				rs = st.executeQuery(query);

				if (rs.next()) {
					if (rs.getString("emoluments") != null) {
						bean.setEmoluments(rs.getString("emoluments"));
					}
					if (rs.getString("EMPPFSTATUARY") != null) {
						bean.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
					}
					if (rs.getString("EMPVPF") != null) {
						bean.setEmpVpf(rs.getString("EMPVPF"));
					}
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));
					}
					if (rs.getString("EMPADVRECINTEREST") != null) {
						bean.setInterest(rs.getString("EMPADVRECINTEREST"));
					}
					if (rs.getString("PENSIONCONTRI") != null) {
						bean.setPenContri(rs.getString("PENSIONCONTRI"));
					}
				}
			} catch (SQLException e) {
				// e.printStackTrace();
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
				// e.printStackTrace();
			} finally {

			

				 commonDB.closeConnection(null, st, rs);
			}
			// log.info("PensionDAO :checkPensionDuplicate() leaving method");
			return bean;
		}
	 public ArrayList loadDeathCaseNomineeInfo(String pensionNo, String transId) {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AdvanceBasicBean advanceBasicBean = null;
			ArrayList loadNomineeList = new ArrayList();
			String sqlQuery = "";
			int count=0;
			 
			 count=this.checkBankDetails(pensionNo,transId);
			
			 if(count==0){
				 sqlQuery="select SRNO,NOMINEENAME from employee_nominee_dtls where EMPFLAG='Y' and pensionno="+pensionNo+" order by SRNO ";
			 }else{
				  
				 sqlQuery =" select SRNO , NOMINEENAME from employee_nominee_dtls where srno in( (select SRNO   from employee_nominee_dtls    where EMPFLAG = 'Y' and pensionno="+pensionNo+")    minus    (select   NOMINEEID from employee_bank_info where pensionno="+pensionNo+" and CPFPFWTRANSID="+transId+")   ) and pensionno="+pensionNo+"order by SRNO  ";
			  
			 }
			System.out.println(sqlQuery);
			log.info("CommonDAO::loadDeathCaseNomineeInfo" + sqlQuery);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					advanceBasicBean = new AdvanceBasicBean();

					if (rs.getString("SRNO") != null) {
						advanceBasicBean.setSerialNo(rs.getString("SRNO"));
					} else {
						advanceBasicBean.setSerialNo("");
					}
						
					if (rs.getString("NOMINEENAME") != null) {
						advanceBasicBean
								.setNomineename(rs.getString("NOMINEENAME"));
					} else {
						advanceBasicBean.setNomineename("");
					}
					 
					 
					loadNomineeList.add(advanceBasicBean);
				}

			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(rs, st, con);
			}
			return loadNomineeList;
		}
	 public int checkBankDetails(String pensionNo,String transId) {
			
			log.info("checkBankDetails() entering method ");
			String query = "";
			Statement st = null;
			Connection con = null;
			ResultSet rs = null;
			int i = 0;
			query = "select count(*) from EMPLOYEE_BANK_INFO where  CPFPFWTRANSID="
				+ transId + " and PENSIONNO='" + pensionNo + "'";
			
			log.info("query is " + query);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(query);
				if (rs.next()) {
					i = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				commonDB.closeConnection(rs, st, con);
			}
			log.info("checkBankDetails() leaving method");
			return i;
		}	

}
