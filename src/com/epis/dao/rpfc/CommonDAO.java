package com.epis.dao.rpfc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.epis.bean.rpfc.DesignationBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.DBUtility;
public class CommonDAO {
	Log log = new Log(CommonDAO.class);
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil=new CommonUtil();
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
		/*if (!cpfaccno.equals("") && !empCode.equals("")) {
			query = "select count(*) as count from employee_info where  cpfacno='"
					+ cpfaccno
					+ "' and employeeno='"
					+ empCode
					+ "' and region='" + region + "' ";
		} else*/ if (!cpfaccno.equals("")) {
			query = "select count(*) as count from employee_info where   cpfacno='"
					+ cpfaccno.trim() + "' and region='" + region.trim() + "'";
                         //   " AND airportcode='IGI IAD'";
		} else {
			query = "select count(*) as count from employee_info where employeeno='"
					+ empCode + "' and region='" + region + "'" ;
                          //  " AND airportcode='IGI IAD' ";
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
		String foundEmpFlag ="";
		String query = "";
		Statement st = null;
		ResultSet rs = null;
		 if (!cpfaccno.equals("")) {
			query = "select cpfacno  from employee_info where   cpfacno='"
					+ cpfaccno.trim() + "' and region='" + region.trim() + "' and cpfacno is not null";
                       
		} else {
			query = "select cpfacno  from employee_info where employeeno='"
					+ empCode + "' and region='" + region + "' and cpfacno is not null" ;
                         
		}
	log.info("query is " + query);
		try {

			st = con.createStatement();
			log.info("st "+st);
			rs = st.executeQuery(query);

			if (rs.next()) {
			//	log.info("cpfaccno "+rs.getString(1));
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
			 String query = "select employeeNo as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"+transMonthYear+"' and employeeno='"+ employeeNo+ "' and region='"+ region + "'";
		
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
			 String query = "select *  from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"+transMonthYear+"' and cpfaccno='"+ cpfaccno+ "' and region='"+ region + "'";
		
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
			String cpf,boolean flag) {
		log.info("PensionDao:getPensionNumber() entering method");
		
		// TODO Auto-generated method stub
		String finalName = "", fname = "";
		SimpleDateFormat fromDate = null;
		int endIndxName = 0;
		String quats[] = { "Mrs.", "DR." };
		String tempName = "", convertedDt = "";
	//	String uniquenumber = validateCPFAccno(cpf.toCharArray());
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
		if(flag==true)
		return convertedDt + fname + cpf;
		else return convertedDt + fname;
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
			pensionNumber = this.getPensionNumberError(employeeName, dateOfBirth,
					cpfaccno, tempPensionNumber);
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
        Statement st =null;
        ArrayList departmentList = new ArrayList();
        ResultSet rs = null;
        String departmentName = "";
        try {
            con = commonDB.getConnection();
            st = con.createStatement();
            String sql = "select * from employee_department";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                if(rs.getString("departmentname")!=null){
                    departmentName=rs.getString("departmentname");
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
        Statement st=null;
        ArrayList designationList = new ArrayList();
        ResultSet rs = null;
        DesignationBean bean=null;
        String sql = "select DESIGNATION,EMPLEVEL from EMPLOYEE_DESEGNATION";
        try {
                con = commonDB.getConnection();
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    bean = new DesignationBean();
                    if (rs.getString("EMPLEVEL")!=null) {
                        bean.setEmplevel(rs.getString("EMPLEVEL"));
                    } else {
                        bean.setEmplevel("");
                    }
                    if (rs.getString("DESIGNATION")!=null) {
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
    public EmployeePersonalInfo loadEmployeePersonalInfo(ResultSet rs)  throws SQLException {
        EmployeePersonalInfo personal = new EmployeePersonalInfo();
        log.info("loadEmployeePersonalInfo==============");
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
        if (rs.getString("EMPSERIALNUMBER") != null) {
            personal.setOldPensionNo(rs.getString("EMPSERIALNUMBER"));
       
        } else {
            personal.setOldPensionNo("---");
        }
        if (rs.getString("EMPSERIALNUMBER") != null) {
            personal.setPensionNo(commonUtil.leadingZeros(5,rs.getString("EMPSERIALNUMBER")));
      
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
        if(!personal.getDateOfBirth().equals("---")){
            String personalPFID = this.getPFID(personal.getEmployeeName(), personal
                    .getDateOfBirth(), personal.getPensionNo());
            personal.setPfID(personalPFID);
        }else{
            personal.setPfID(personal.getPensionNo());
        }
        
        return personal;
        
        }
    public EmployeePersonalInfo loadPersonalInfo(ResultSet rs)  throws SQLException {
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
        
        return personal;
        
        }
    
   /* public String getPFID(String empName, String dateofBirth, String sequenceNo) {

        // TODO Auto-generated method stub
        String finalName = "", fname = "";
        SimpleDateFormat fromDate = null;
        int endIndxName = 0;
        String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
        String tempName = "", convertedDt = "";
       
        try {
            if(!(dateofBirth.equals("")||dateofBirth.equals("---"))){
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
            if(!empName.equals("")){
                 if(empName.lastIndexOf(".")==empName.length()-1){
                    empName=empName.substring(0,empName.length()-1);
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
                endIndxName = empName.trim().indexOf(" ");
                finalName = finalName + empName.substring(endIndxName).trim();
                log.info("First if finalName"+finalName+"empName"+empName);
            } else if (empName.indexOf(".") != -1) {
                endIndxName = empName.lastIndexOf(".");
                finalName = finalName
                        + empName.substring(endIndxName + 1, empName.length())
                                .trim();
                log.info("Else if finalName"+finalName+"empName"+empName);
            } else {
                finalName = empName.substring(0, 2);
            }
            log.info("finalName"+finalName+"empName===="+empName);
            char[] cArray = finalName.toCharArray();
            log.info("Array0"+cArray[0]+"Array1"+cArray[1]);
            fname = String.valueOf(cArray[0]);
            if(cArray[1]!=' '){
            	 fname = fname + String.valueOf(cArray[1]);
            }
           

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.printStackTrace(e);
        } catch (Exception e) {
             log.printStackTrace(e);

        }
     
        return convertedDt + fname + sequenceNo;

    }*/
    public String getPFID(String empName, String dateofBirth, String sequenceNo) {
      /*  log.info("PersonalDao:getPFID() entering method");
         log.info("PersonalDao:getPFID() dateofBirth" + dateofBirth + "empName"
                 + empName+"sequenceNo"+sequenceNo);*/
         // TODO Auto-generated method stub
    	   String finalName = "", fname = "";
           SimpleDateFormat fromDate = null;
           int endIndxName = 0;
           String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
           String tempName = "", convertedDt = "";
          
           try {
               if (dateofBirth.indexOf("-") != -1) {
                   fromDate = new SimpleDateFormat("dd-MMM-yyyy");
               } else {
                   fromDate = new SimpleDateFormat("dd/MMM/yyyy");
               }
               SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
               convertedDt = toDate.format(fromDate.parse(dateofBirth));
               int startIndxName = 0, index = 0;
               endIndxName = 1;
               for (int i = 0; i < quats.length; i++) {
                   if (empName.indexOf(quats[i]) != -1) {
                       tempName = empName.replaceAll(quats[i], "").trim();
                       empName = tempName;
                   }
               }
               if(empName.lastIndexOf("(")!=-1){
              	 empName=empName.substring(0,empName.lastIndexOf("(")).trim();
               }else if(empName.lastIndexOf(" -")!=-1){
              	 empName=empName.substring(0,empName.lastIndexOf(" -")).trim();
               }else if(empName.lastIndexOf(" I")!=-1){
              	 empName=empName.substring(0,empName.lastIndexOf(" I")).trim();
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
             e.printStackTrace();
         } catch (Exception e) {
             log.info("Exception is " +sequenceNo+"empName"+empName+ e);

         }
        // log.info("PersonalDao:getPFID() leaving method");
         return convertedDt + fname + sequenceNo;

     }
    public String getCPFACCNOByPension(String pensionNo,String cpfacno,String region){
        Connection con = null;
        StringBuffer buffer=new StringBuffer();
        String cpfaccno="";
        String selectFamilySQL = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"+pensionNo.trim()+"' ";
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
            cpfaccno=buffer.toString();
            if(cpfaccno.equals("")){
                cpfaccno="---";
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
		Statement st=null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String unitName = "", unitCode = "";
		
		try {
			con = commonDB.getConnection();
			 st = con.createStatement();
			String query = "SELECT distinct AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO where region='"
				+ region+"' and airportcode is not null";
			log.info("getAirportsByFinanceTbl==query==========="+query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				EmpMasterBean bean = new EmpMasterBean();
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
            commonDB.closeConnection(rs, st, con);
        }
        log.info("CommonDAO :getPFIDsNaviagtionList() Leaving Method ");
        return eachPage;
    }
    public int getPFIDsNaviagtionListSize(int totalSize) {
        log.info("CommonDAO :getPFIDsNaviagtionListSize() Entering Method ");
        Connection con = null;
        Statement st=null;
        int pfidTotal=0,eachPage=0;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_PERSONAL_INFO ";
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
            commonDB.closeConnection(rs, st, con);
        }
        log.info("CommonDAO :getPFIDsNaviagtionListSize() Leaving Method ");
        return eachPage;
    }
    public void updateTransPensionData() {
        String fromYear = "", toYear = "",dateOfRetriment="";
       
        ArrayList empList = new ArrayList();
        EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
        String updateQuery="";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        Statement upST = null;
        int updateSt=0,finalUpdate=0;
        String sqlQuery = "SELECT CPFACNO,REGION,EMPLOYEENAME,EMPSERIALNUMBER FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM EMPLOYEE_PERSONAL_INFO) ORDER BY EMPSERIALNUMBER";
        try {
            con = commonDB.getConnection();
            st = con.createStatement();
            upST=con.createStatement();
            rs = st.executeQuery(sqlQuery);
            String per_region="",cpfaccno="",employeename="",perPensionNo="";
            BufferedWriter fw = new BufferedWriter(new FileWriter(
            "c://GenertedPensionNoTrnsData.txt"));
            String totalInfo="";
            while (rs.next()) {
           	 per_region=rs.getString("REGION");
           	 cpfaccno=rs.getString("CPFACNO");
           	 perPensionNo=rs.getString("EMPSERIALNUMBER");
           	 totalInfo=per_region+","+cpfaccno+","+perPensionNo; 
           	 updateQuery="UPDATE employee_pension_validate SET PENSIONNO='"+perPensionNo.trim()+"' WHERE CPFACCNO='"+cpfaccno.trim()+"' AND REGION='"+per_region.trim()+"'";
           	 fw.write(totalInfo);
           	 fw.newLine();
           	 fw.write(updateQuery + ";");
                fw.newLine();
                fw.write("============================");
                fw.flush();
           	 updateSt=upST.executeUpdate(updateQuery);
           	 finalUpdate=finalUpdate+updateSt;
           	 
            }
            log.info("Total Updations"+finalUpdate);
        }catch (SQLException e) {
            log.printStackTrace(e);
        } catch (Exception e) {
            log.printStackTrace(e);
        } finally {
            commonDB.closeConnection(rs, st, con);
        }
      //  return form8List;
    }
    public String getLastDayOfMonth(String date) {
		Connection con = null;
		Statement st=null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String lastDayOfMnth="";
		
		try {
			con = commonDB.getConnection();
			 st = con.createStatement();
			String query = "SELECT TO_CHAR(LAST_DAY(add_months('"+date+"',1)),'dd-Mon-yyyy') AS LASTDAYMNTH FROM DUAL";
				
			log.info("getAirportsByFinanceTbl==query==========="+query);
			rs = st.executeQuery(query);
			if (rs.next()) {
				lastDayOfMnth=rs.getString("LASTDAYMNTH");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return lastDayOfMnth;

	}
    public double getRateOfInterest(String year){
    	double rateOfInterest=0.0;
    	log.info("getRateOfInterest"+year);
    	if(Integer.parseInt(year)>=1995 && Integer.parseInt(year)<=2000){
			rateOfInterest=12;
		}else if(Integer.parseInt(year)>2000 && Integer.parseInt(year)<=2001){
			rateOfInterest=11;
		}else if(Integer.parseInt(year)>2001 && Integer.parseInt(year)<=2005){
			rateOfInterest=9.50;
		}else if(Integer.parseInt(year)>2005 && Integer.parseInt(year)<=2009){
			rateOfInterest=8.50;
		}else if(Integer.parseInt(year)>2009 && Integer.parseInt(year)<=2010){
			rateOfInterest=8.00;
		}
    	return rateOfInterest;
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
            commonDB.closeConnection(rs, st, con);
        }
        log.info("CommonDAO :getPFIDsNaviagtionList() Leaving Method ");
        return rangeList;
    }
    public String buildQuerygetPFIDsNaviagtionList(String region,String airportcode) {
    	log.info("CommonDAO::buildQuerygetPFIDsNaviagtionList-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "",sqlQuery = "";
  
    	sqlQuery="SELECT COUNT(*) AS COUNT FROM EMPLOYEE_PERSONAL_INFO ";
    	if (!region.equals("")) {
			whereClause.append(" REGION ='"
					+ region + "'");
			whereClause.append(" AND ");
		}
  
    	if (!airportcode.equals("")) {
			whereClause.append(" AIRPORTCODE ='"
					+ airportcode + "'");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals("")) ) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
    	dynamicQuery = query.toString();
    	log.info("CommonDAO::buildQuerygetPFIDsNaviagtionList Leaving Method");
    	return dynamicQuery;
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
            commonDB.closeConnection(rs, st, con);
        }
        log.info("CommonDAO :navgtionPFIDsList() Leaving Method ");
        return rangeList;
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
    public String buildQueryNavgationForTrnsferPFList(String region,String airportCode,String transferFlag) {
    	log.info("FinanceReportDAO::buildQueryNavgationForTrnsferPFList-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "",sqlQuery = "";
    	
    	sqlQuery = "SELECT EPI.PENSIONNO FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO ";
    

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
    public String buildQueryTransNavgationPFList(String region,String airportCode,String monthYear) {
    	log.info("FinanceReportDAO::buildQueryTransNavgationPFList-- Entering Method");
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
    	log.info("FinanceReportDAO::buildQueryTransNavgationPFList Leaving Method");
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
   
}
