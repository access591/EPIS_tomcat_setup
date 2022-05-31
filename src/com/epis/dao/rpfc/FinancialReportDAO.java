/**
 * File       : FinancialReportDAO.java
 * Date       : 08/07/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.dao.rpfc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeCardReportInfo;
import com.epis.bean.rpfc.EmployeePensionCardInfo;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.FinancialYearBean;
import com.epis.bean.rpfc.Form7MultipleYearBean;
import com.epis.bean.rpfc.Form8Bean;
import com.epis.bean.rpfc.Form8DataBean;
import com.epis.bean.rpfc.Form8RemittanceBean;
import com.epis.bean.rpfc.NomineeBean;
import com.epis.bean.rpfc.PTWBean;
import com.epis.bean.rpfc.PensionContBean;
import com.epis.bean.rpfc.TempPensionTransBean;
import com.epis.bean.rpfc.TrustPCBean;
import com.epis.dao.CommonDAO;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class FinancialReportDAO implements Constants {
	Log log = new Log(FinancialReportDAO.class);
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil = new CommonUtil();
	CommonDAO commonDAO = new CommonDAO();
	
	public ArrayList pensionContributionReport(String fromDate, String toDate,
			String region, String airportcode, String empserialNO,
			String cpfAccno, String transferFlag, String mappingFlag) {
		ArrayList penContHeaderList = new ArrayList();
		penContHeaderList = this.pensionContrHeaderInfo(region, airportcode,
				empserialNO, cpfAccno, transferFlag, mappingFlag);
		String cpfacno = "", empRegion = "", empSerialNumber = "", tempPensionInfo = "", empCpfaccno = "";
		String[] cpfaccnos = new String[10];
		String[] dupCpfaccnos = new String[10];
		String[] regions = new String[10];
		String[] empPensionList = null;
		String[] pensionInfo = null;
		CommonDAO commonDAO = new CommonDAO();
		String pensionList = "", dateOfRetriment = "";
		ArrayList penConReportList = new ArrayList();
		log.info("Header Size" + penContHeaderList.size());
		log.info("" + penContHeaderList);
		String countFlag = "";
		int yearCount = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			for (int i = 0; i < penContHeaderList.size(); i++) {
				PensionContBean penContHeaderBean = new PensionContBean();
				PensionContBean penContBean = new PensionContBean();

				penContHeaderBean = (PensionContBean) penContHeaderList.get(i);
				penContBean.setCpfacno(commonUtil
						.duplicateWords(penContHeaderBean.getCpfacno()));

				penContBean.setEmployeeNM(penContHeaderBean.getEmployeeNM());
				penContBean.setEmpDOB(penContHeaderBean.getEmpDOB());
				penContBean.setEmpSerialNo(penContHeaderBean.getEmpSerialNo());

				penContBean.setEmpDOJ(penContHeaderBean.getEmpDOJ());
				penContBean.setGender(penContHeaderBean.getGender());
				penContBean.setFhName(penContHeaderBean.getFhName());
				penContBean.setEmployeeNO(penContHeaderBean.getEmployeeNO());
				penContBean.setDesignation(penContHeaderBean.getDesignation());
				penContBean.setPfSettled(penContHeaderBean.getPfSettled());
				penContBean.setFinalSettlementDate(penContHeaderBean.getFinalSettlementDate());
				log.info(penContHeaderBean.getWhetherOption() + "option");
				if (!penContHeaderBean.getWhetherOption().equals("")) {
					penContBean.setWhetherOption(penContHeaderBean
							.getWhetherOption());
				}
				penContBean.setDateOfEntitle(penContHeaderBean
						.getDateOfEntitle());
				penContBean.setMaritalStatus(penContHeaderBean
						.getMaritalStatus());
				penContBean.setDepartment(penContHeaderBean.getDepartment());
				
				penContBean.setPensionNo(commonDAO.getPFID(penContBean
						.getEmployeeNM(), penContBean.getEmpDOB(), commonUtil
						.leadingZeros(5, penContHeaderBean.getEmpSerialNo())));
				
				log.info("penContBean " + penContBean.getPensionNo());
				empSerialNumber = penContHeaderBean.getEmpSerialNo();
				if (empSerialNumber.length() >= 5) {
					empSerialNumber = empSerialNumber.substring(empSerialNumber
							.length() - 5, empSerialNumber.length());
					empSerialNumber = commonUtil.trailingZeros(empSerialNumber
							.toCharArray());
				}
				cpfacno = penContHeaderBean.getCpfacno();
				empRegion = penContHeaderBean.getEmpRegion();

				cpfaccnos = cpfacno.split("=");
				regions = empRegion.split("=");
				double totalAAICont = 0.0, calCPF = 0.0, calPens = 0.0;
				ArrayList employeFinanceList = new ArrayList();
				String preparedString = preparedCPFString(cpfaccnos, regions);
				if (cpfaccnos.length >= 1) {
					for (int k = 0; k < cpfaccnos.length; k++) {
						region = regions[k];
						empCpfaccno = cpfaccnos[k];
					}
				}
				log.info("region is " + region);
				penContBean.setEmpRegion(region);
				penContBean.setEmpCpfaccno(empCpfaccno);
				try {
					if (!penContBean.getEmpDOB().trim().equals("---")
							&& !penContBean.getEmpDOB().trim().equals("")) {
						dateOfRetriment = this.getRetriedDate(penContBean
								.getEmpDOB());
					}

				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList rateList = new ArrayList();

				pensionList = this.getEmployeePensionInfo(preparedString,
						penContHeaderBean.getDateOfEntitle(), toDate,
						penContHeaderBean.getWhetherOption(), dateOfRetriment,
						penContBean.getEmpDOB(),empserialNO);
				
				String rateFromYear = "", rateToYear = "", checkMnthDate = "", rateOfInterest = "";
				boolean rateFlag = false;
				
				st = con.createStatement();
				if (!pensionList.equals("")) {
					empPensionList = pensionList.split("=");
					if (empPensionList != null) {
						for (int r = 0; r < empPensionList.length; r++) {
							TempPensionTransBean bean = new TempPensionTransBean();
							tempPensionInfo = empPensionList[r];
							pensionInfo = tempPensionInfo.split(",");
							bean.setMonthyear(pensionInfo[0]);
							try {

								checkMnthDate = commonUtil.converDBToAppFormat(
										pensionInfo[0], "dd-MMM-yyyy", "MMM")
										.toLowerCase();
								if (r == 0 && !checkMnthDate.equals("apr")) {
									checkMnthDate = "apr";

								}

								if (checkMnthDate.equals("apr")) {
									rateOfInterest = this
											.getEmployeeRateOfInterest(
													con,
													commonUtil
															.converDBToAppFormat(
																	pensionInfo[0],
																	"dd-MMM-yyyy",
																	"yyyy"));
									if (rateOfInterest.equals("")) {
										rateOfInterest = "12";
									}
								}
							} catch (InvalidDataException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							bean.setInterestRate(rateOfInterest);
							//log.info("Monthyear"+checkMnthDate+"Interest Rate"
							// +rateOfInterest);
							checkMnthDate = "";
							bean.setEmoluments(pensionInfo[1]);
							bean.setCpf(pensionInfo[2]);
							bean.setEmpVPF(pensionInfo[3]);
							bean.setEmpAdvRec(pensionInfo[4]);
							bean.setEmpInrstRec(pensionInfo[5]);
							bean.setStation(pensionInfo[6]);
						//	log.info("contribution "+pensionInfo[7]);
							bean.setPensionContr(pensionInfo[7]);
							// calCPF=Double.parseDouble(bean.getCpf());
							// calPens=Double.parseDouble(pensionInfo[7]);
							calCPF = Math.round(Double.parseDouble(bean
									.getCpf()));
							DateFormat df = new SimpleDateFormat("dd-MMM-yy");
							
							bean.setDeputationFlag(pensionInfo[19].trim());
							Date transdate = df.parse(pensionInfo[0]);
							if (transdate.before(new Date("31-Mar-08"))&& (bean.getDeputationFlag().equals("N")||bean.getDeputationFlag().equals(""))) {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[7]));
								totalAAICont = calCPF - calPens;
							} else {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[12]));
								bean.setPensionContr(pensionInfo[12]);
								totalAAICont = calCPF - calPens;
							}
						
							
							bean.setAaiPFCont(Double.toString(totalAAICont));
						//	log.info("calCPF " +calCPF +"calPens "+calPens );
							bean.setGenMonthYear(pensionInfo[8]);

							bean.setTransCpfaccno(pensionInfo[9]);
							bean.setRegion(pensionInfo[10]);
						//	log.info("pensionInfo[12] " + pensionInfo[12]);
							bean.setRecordCount(pensionInfo[11]);
							bean.setDbPensionCtr(pensionInfo[12]);
							
							bean.setDataFreezFlag(pensionInfo[13]);
							bean.setForm7Narration(pensionInfo[14]);
							bean.setPcHeldAmt(pensionInfo[15]);
							bean.setNoofMonths(pensionInfo[16]);
							bean.setPccalApplied(pensionInfo[17].trim());
						//	log.info("PcApplied " +bean.getPccalApplied());
							if(bean.getPccalApplied().equals("N")){
								bean.setCpf("0.00");
								bean.setAaiPFCont("0.00");	
								bean.setPensionContr("0.00");
								bean.setDbPensionCtr("0.00");
							}
							bean.setArrearFlag(pensionInfo[18].trim());
							bean.setDeputationFlag(pensionInfo[19].trim());
							
							
							String monthYear1 = commonUtil.converDBToAppFormat(
									pensionInfo[0], "dd-MMM-yyyy", "MMM-yyyy");
							String advances = "select amount from employee_pension_advances where pensionno='"
									+ empSerialNumber
									+ "' and to_char(advtransdate,'Mon-yyyy') like'%"
									+ monthYear1 + "'";
							String loans = "select SUB_AMT,CONT_AMT  from employee_pension_loans where pensionno='"
									+ empSerialNumber
									+ "' and to_char(loandate,'Mon-yyyy') like'%"
									+ monthYear1 + "'";
							String advAmount = "0.00";
							
							
							
							ResultSet rs1 = st.executeQuery(advances);
							while (rs1.next()) {
								if (rs1.getString("amount") != null) {
									advAmount = rs1.getString("amount");

								} else {
									advAmount = "0.00";
								}

							}
							bean.setAdvAmount(advAmount);
							String employeeLoan = "0.00", aaiLoan = "0.00";
							ResultSet rs2 = st.executeQuery(loans);
							while (rs2.next()) {
								if (rs2.getString("SUB_AMT") != null) {
									employeeLoan = rs2.getString("SUB_AMT");

								} else {
									employeeLoan = "0.00";
								}
								if (rs2.getString("CONT_AMT") != null) {
									aaiLoan = rs2.getString("CONT_AMT");

								} else {
									aaiLoan = "0.00";
								}
							}
							bean.setEmployeeLoan(employeeLoan);
							bean.setAaiLoan(aaiLoan);
						//	log.info("loan " + bean.getEmployeeLoan());
							if (bean.getRecordCount().equals("Duplicate")) {
								countFlag = "true";
							}
							bean.setRemarks("---");

							employeFinanceList.add(bean);
						}

					}
				}
				if (!pensionList.equals("")) {
					penContBean.setBlockList(this.getMonthList(con,
							pensionInfo[20]));
				}
				employeFinanceList = this
						.chkDuplicateMntsForCpfs(employeFinanceList);
				penContBean.setEmpPensionList(employeFinanceList);
				penContBean.setCountFlag(countFlag);

				penConReportList.add(penContBean);

			}
		} catch (SQLException se) {
			log.printStackTrace(se);
		} catch (Exception ex) {
			log.printStackTrace(ex);
		} finally {
			commonDB.closeConnection(con, st, null);
		}

		return penConReportList;
	}

	private ArrayList chkDuplicateMntsForCpfs(ArrayList list) {
		ArrayList finalList = new ArrayList();
		finalList = list;
		TempPensionTransBean bean = new TempPensionTransBean();
		log.info("List Size" + list.size() + "Final List Size"
				+ finalList.size());
		for (int i = 0; i < list.size(); i++) {
			bean = (TempPensionTransBean) list.get(i);
			if (i > 168) {
				System.out.println("Greter than Monthyear"
						+ bean.getMonthyear() + "i=" + i);
				if (bean.getMonthyear().trim().equals("-NA-")) {
					bean = null;
					finalList.set(i, bean);
				}
			} else {
				if (bean.getMonthyear().trim().equals("-NA-")) {
					bean.setMonthyear(bean.getGenMonthYear().toUpperCase());
					finalList.set(i, bean);
				}
			}

		}

		return finalList;
	}

	private ArrayList pensionContrHeaderInfo(String region, String airportCD,
			String empserialNO, String cpfAccno, String transferFlag,
			String mappingFlag) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		log.info("region in reportDao********  " + region);
		if (mappingFlag.equals("true")) {
			sqlQuery = this.buildPenContMappingQuery(region, airportCD,
					empserialNO, cpfAccno);
		} else {
			sqlQuery = this.buildPenContRptQuery(region, airportCD,
					empserialNO, cpfAccno, transferFlag);
		}

		log.info("pensionContrHeaderInfo===Query" + sqlQuery);
		String tempSrlNumber = "", srlNumber = "", doj = "", dob = "", cpfacnos = "", regions = "", fhName = "", employeeName = "", designation = "";
		String tempRegion = "", tempCPF = "", department = "";
		String finalSettlementdate="";
		ArrayList finalList = new ArrayList();
		int totalRS = 0, tempTotalRs = 0, totalSrlNo = 0, totalRecCpf = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			ArrayList list = new ArrayList();
			PensionContBean bean = new PensionContBean();
			totalRS = this.getEmpPensionCount(empserialNO);
			String wetherOption = "",pfSettled="";
			while (rs.next()) {
				tempTotalRs++;
				// code modified as on Feb 19th
				if (rs.getString("employeename") != null) {
					employeeName = rs.getString("employeename");
				}
				if (rs.getString("wetheroption") != null) {
					wetherOption = rs.getString("wetheroption");
				}
				if (rs.getString("dateofbirth") != null) {
					dob = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				}

				if (rs.getString("dateofjoining") != null) {
					doj = commonUtil.converDBToAppFormat(rs
							.getDate("dateofjoining"));
				}
				if (rs.getString("FHNAME") != null) {
					fhName = rs.getString("FHNAME");
				}
				if (rs.getString("DEPARTMENT") != null) {
					department = rs.getString("DEPARTMENT");
				}
				if (rs.getString("DESEGNATION") != null) {
					designation = rs.getString("DESEGNATION");
				}
				if (rs.getString("PFSETTLED") != null) {
					pfSettled = rs.getString("PFSETTLED");
				}
				
				if (rs.getString("EMPSERIALNUMBER") != null) {
					if (tempSrlNumber.equals("")) {
						tempSrlNumber = rs.getString("EMPSERIALNUMBER");
					} else if (!tempSrlNumber.equals(rs
							.getString("EMPSERIALNUMBER"))) {
						tempRegion = "";
						tempCPF = "";
						cpfacnos = "";
						regions = "";
						if (totalSrlNo > 0) {
							finalList.add(bean);
							bean = null;
							bean = new PensionContBean();
							totalSrlNo = 0;
						}
						tempSrlNumber = rs.getString("EMPSERIALNUMBER");
					}

					if (tempSrlNumber.equals(rs.getString("EMPSERIALNUMBER"))) {
						totalSrlNo++;
						if (tempRegion.equals("") && tempCPF.equals("")) {
							tempRegion = rs.getString("REGION").trim();
							tempCPF = rs.getString("CPFACNO").trim();
							cpfacnos = cpfacnos + "=" + rs.getString("CPFACNO");
							regions = regions + "=" + rs.getString("REGION");
						} else if (!(tempRegion.equals("") && tempCPF
								.equals(""))
								&& tempRegion.trim().equals(
										rs.getString("REGION").trim())
								&& tempCPF.trim().equals(
										rs.getString("CPFACNO").trim())) {
							cpfacnos = cpfacnos;
							regions = regions;
						} else if (!(tempRegion.equals("") && tempCPF
								.equals(""))
								&& ((!tempRegion.equals(rs.getString("REGION")
										.trim()) && !tempCPF.equals(rs
										.getString("CPFACNO").trim()))
										|| (!tempRegion.equals(rs.getString(
												"REGION").trim()) && tempCPF
												.equals(rs.getString("CPFACNO")
														.trim())) || (tempRegion
										.equals(rs.getString("REGION").trim()) && !tempCPF
										.equals(rs.getString("CPFACNO").trim())))) {
							tempRegion = rs.getString("REGION").trim();
							tempCPF = rs.getString("CPFACNO").trim();
							cpfacnos = cpfacnos + "=" + rs.getString("CPFACNO");
							regions = regions + "=" + rs.getString("REGION");
						}

						bean = this.loadEmployeeInfo(rs, cpfacnos, regions);
						bean.setEmployeeNM(employeeName);
						bean.setWhetherOption(wetherOption);
						bean.setEmpDOB(dob);
						bean.setEmpDOJ(doj);
						bean.setFhName(fhName);
						bean.setDepartment(department);
						bean.setDesignation(designation);
						bean.setPfSettled(pfSettled);
					
					}

					if (tempTotalRs == totalRS) {
						finalList.add(bean);
					}
				} else {
					if (totalRecCpf == 0) {
						totalRecCpf++;

						bean = this.loadEmployeeInfo(rs, rs
								.getString("CPFACNO"), region);
						bean.setWhetherOption(wetherOption);
						bean.setEmpDOB(dob);
						bean.setEmpDOJ(doj);
						bean.setFhName(fhName);
						bean.setDepartment(department);
					  	finalList.add(bean);
						
					}

				}

			}
			log.info("tempSrlNumber" + tempSrlNumber + "bean.cpfacnos"
					+ bean.getCpfacno() + "regions" + regions
					+ finalList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return finalList;
	}

	private PensionContBean loadEmployeeInfo(ResultSet rs, String cpfacnos,
			String regions) throws SQLException {
		PensionContBean contr = new PensionContBean();

		if (rs.getString("MARITALSTATUS") != null) {
			contr.setMaritalStatus(rs.getString("MARITALSTATUS").trim());
		} else {
			contr.setMaritalStatus("---");

		}
		if (rs.getString("EMPSERIALNUMBER") != null) {
			contr.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
		}
		if (rs.getString("EMPLOYEENO") != null) {
			contr.setEmployeeNO(rs.getString("EMPLOYEENO"));
		} else {
			contr.setEmployeeNO("---");
		}
		if (rs.getString("SEX") != null) {
			contr.setGender(rs.getString("SEX"));
		} else {
			contr.setGender("---");
		}
		if (rs.getString("DESEGNATION") != null) {
			contr.setDesignation(rs.getString("DESEGNATION"));
		} else {
			contr.setDesignation("---");
		}
		if (rs.getString("FHNAME") != null) {
			contr.setFhName(rs.getString("FHNAME"));
		} else {
			contr.setFhName("---");
		}
		if (rs.getString("CPFACNO") != null) {
			contr.setCpfacno(cpfacnos);
		}

		if (rs.getString("EMPLOYEENAME") != null) {
			contr.setEmployeeNM(rs.getString("EMPLOYEENAME"));
		}

		if (rs.getString("REGION") != null) {
			contr.setEmpRegion(regions);
		}

		if (rs.getString("DATEOFJOINING") != null) {
			contr.setEmpDOJ(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFJOINING")));
		} else {
			contr.setEmpDOJ("---");
		}
		if (rs.getString("DATEOFBIRTH") != null) {
			contr.setEmpDOB(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFBIRTH")));
		} else {
			contr.setEmpDOB("---");
		}
		if (rs.getString("department") != null) {
			contr.setDepartment(rs.getString("department"));
		} else {
			contr.setDepartment("---");
		}
		if (rs.getString("DATEOFANNUATION") != null) {
		String	finalSettlementdate = rs.getString("DATEOFANNUATION");
		contr.setFinalSettlementDate(finalSettlementdate);
		}
		log.info("department " + contr.getDepartment());
		CommonDAO commonDAO = new CommonDAO();
		String pensionNumber = commonDAO.getPFID(contr.getEmployeeNM(), contr
				.getEmpDOB(), contr.getEmpSerialNo());
		contr.setPensionNo(pensionNumber);
		if (rs.getString("WETHEROPTION") != null) {
			contr.setWhetherOption(rs.getString("WETHEROPTION"));
		} else {
			contr.setWhetherOption("---");
		}

		long noOfYears = 0;
		noOfYears = rs.getLong("ENTITLEDIFF");

		if (noOfYears > 0) {
			contr.setDateOfEntitle(contr.getEmpDOJ());
		} else {
			contr.setDateOfEntitle("01-Apr-1995");
		}
		return contr;

	}

	public String buildPenContRptQuery(String region, String airportCD,
			String empserialNO, String cpfAccno, String transferFlag) {
		log.info("FinancialReportDAO :buildPenContRptQuery() entering method");

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String dynamicQuery = "";
		sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO";
		if (!cpfAccno.equals("")) {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO AND  EPI.region='"
					+ region
					+ "' and EPI.cpfacno='"
					+ cpfAccno
					+ "' AND ETI.TRANSFERFLAG='" + transferFlag + "' ";
		} else if (empserialNO.equals("") && region.equals("NO-SELECT")) {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO AND ETI.TRANSFERFLAG='"
					+ transferFlag + "' ";
		} else if (empserialNO.equals("") && !region.equals("")) {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO AND  EPI.region='"
					+ region + "' AND ETI.TRANSFERFLAG='" + transferFlag + "' ";
		} else {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO AND EPI.PENSIONNO='"
					+ empserialNO + "'";
			// AND ETI.TRANSFERFLAG='"+transferFlag+"' ";
		}

		/*
		 * if (!cpfAccno.equals("")) {
		 * whereClause.append(" LOWER(cpfaccno) like'" + cpfAccno.trim()+ "'");
		 * whereClause.append(" AND "); } if (!empserialNO.equals("")) {
		 * whereClause.append(" LOWER(epi.pensionno) like'" +
		 * empserialNO.trim()+ "'"); whereClause.append(" AND "); }
		 */

		if (!airportCD.equals("NO-SELECT")) {
			whereClause.append(" LOWER(EPI.AIRPORTCODE) like'%"
					+ airportCD.trim().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!region.equals("NO-SELECT")) {

			whereClause.append(" LOWER(EPI.region)='"
					+ region.trim().toLowerCase() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("NO-SELECT")) && (airportCD.equals("NO-SELECT"))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "ORDER BY EPI.PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("FinancialReportDAO :buildQuery() leaving method");
		return dynamicQuery;
	}
	public String buildPenContMappingQuery(String region, String airportCD,
			String empserialNO, String cpfAccno) {
		log
				.info("FinancialReportDAO :buildPenContMappingQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String Query = "",Query2="";
		String dynamicQuery = "";
		String finalsettlementdate="",personalfinalsettlementdate="",settlementDate="";
		Connection conn = null;
		Statement st = null;
		ResultSet rs=null;
		try
		{
			conn = commonDB.getConnection();
			st = conn.createStatement();
			Query="select SETTLEMENTDATE from employee_pension_finsettlement where pensionno='"+empserialNO+"'";
			rs = st.executeQuery(Query);
			log.info("FinancialReportDAO::buildPenContMappingQuery() ---For SETTLEMENTDATE Query"+Query);
			if (rs.next()) {
				if (rs.getString("SETTLEMENTDATE") != null) {
					finalsettlementdate = commonUtil.converDBToAppFormat(rs.getDate("SETTLEMENTDATE"));
				}
			}
			
			
			// Migration ..........
			
			Query2="select FINALSETTLMENTDT from employee_personal_info where pensionno='"+empserialNO+"'";
			rs = st.executeQuery(Query2);
			log.info("FinancialReportDAO::buildPenContMappingQuery() ---For FINALSETTLMENTDT Query"+Query2);
			if (rs.next()) {
				if (rs.getString("FINALSETTLMENTDT") != null) {
					personalfinalsettlementdate = commonUtil.converDBToAppFormat(rs.getDate("FINALSETTLMENTDT"));
				}
			} 
		 
			if(finalsettlementdate.equals("")){
				settlementDate=personalfinalsettlementdate;
			}else{
				settlementDate=finalsettlementdate;
			}
			// Migration ..........
			
		 
			if (!cpfAccno.equals("") && empserialNO.equals("")) {
				sqlQuery = "SELECT DISTINCT NVL(CPFACNO,'NO-VAL') AS CPFACNO,DEPARTMENT,REGION,'"+settlementDate+"' AS DATEOFANNUATION,MARITALSTATUS,PENSIONNUMBER,EMPSERIALNUMBER,DATEOFJOINING,EMPLOYEENO,DATEOFBIRTH,EMPLOYEENAME,SEX,FHNAME,DESEGNATION,WETHEROPTION,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_INFO WHERE region='"
						+ region + "' and cpfacno='" + cpfAccno + "'  ";
			} else if (empserialNO.equals("") && region.equals("NO-SELECT")) {
				sqlQuery = "SELECT DISTINCT NVL(CPFACNO,'NO-VAL') AS CPFACNO,DEPARTMENT,'"+settlementDate+"' AS DATEOFANNUATION,PENSIONNUMBER,REGION,MARITALSTATUS,EMPSERIALNUMBER,DATEOFJOINING,EMPLOYEENO,DATEOFBIRTH,EMPLOYEENAME,SEX,FHNAME,DESEGNATION,WETHEROPTION,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IS NOT NULL ";
			} else if (empserialNO.equals("") && !region.equals("")) {
				sqlQuery = "SELECT DISTINCT NVL(CPFACNO,'NO-VAL') AS CPFACNO,DEPARTMENT,'"+settlementDate+"' AS DATEOFANNUATION,PENSIONNUMBER,REGION,MARITALSTATUS,EMPSERIALNUMBER,DATEOFJOINING,EMPLOYEENO,DATEOFBIRTH,EMPLOYEENAME,SEX,FHNAME,DESEGNATION,WETHEROPTION,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IS NOT NULL  and region='"
						+ region + "'";
			} else {
				sqlQuery = "SELECT DISTINCT NVL(CPFACNO,'NO-VAL') AS CPFACNO,DEPARTMENT,REGION,'"+settlementDate+"'AS DATEOFANNUATION,PENSIONNUMBER,MARITALSTATUS,EMPSERIALNUMBER,DATEOFJOINING,EMPLOYEENO,DATEOFBIRTH,EMPLOYEENAME,SEX,FHNAME,DESEGNATION,WETHEROPTION,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IS NOT NULL  and EMPSERIALNUMBER='"
						+ empserialNO + "'  ";
			}
	 
		/*
		 * if (cpfAccno.equals("")) { region = "NO-SELECT"; }
		 */
		if (!empserialNO.equals("")) {
			region = "NO-SELECT";
		}

		if (!airportCD.equals("NO-SELECT") && !airportCD.equals("")) {
			whereClause.append(" AIRPORTCODE like'%" + airportCD.trim() + "%'");
			whereClause.append(" AND ");
		}
		if (!region.equals("NO-SELECT")) {
			whereClause.append(" LOWER(region)='" + region.trim().toLowerCase()
					+ "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("NO-SELECT")) && (region.equals("NO-SELECT"))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "ORDER BY EMPSERIALNUMBER";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("FinancialReportDAO :buildQuery() leaving method");
		}catch(Exception e){
			log.printStackTrace(e);
			}
		finally{
			commonDB.closeConnection(conn,st,rs);
			
		}
		return dynamicQuery;
	}

	private String getEmployeePensionInfo(String cpfString, String fromDate,
			String toDate, String whetherOption, String dateOfRetriment,
			String dateOfBirth,String Pensionno) {
		
		log.info("formdate :" +fromDate +"todate :"+toDate);
		String tempCpfString = cpfString.replaceAll("CPFACCNO", "cpfacno");
		String dojQuery = "(select nvl(to_char (dateofjoining,'dd-Mon-yyyy'),'1-Apr-1995') from employee_info where ("
				+ tempCpfString + ") and rownum=1)";
		String condition="";
		if(Pensionno!="" && !Pensionno.equals("")){
    	condition=" or pensionno="+Pensionno;
       }
		
		String sqlQuery = " select mo.* from (select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)) AS EMPMNTHYEAR,emp.MONTHYEAR AS MONTHYEAR,emp.EMOLUMENTS AS EMOLUMENTS,emp.EMPPFSTATUARY AS EMPPFSTATUARY,emp.EMPVPF AS EMPVPF,emp.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST AS EMPADVRECINTEREST,emp.AIRPORTCODE AS AIRPORTCODE,emp.cpfaccno AS CPFACCNO,emp.region as region ,'Duplicate' DUPFlag,emp.PENSIONCONTRI as PENSIONCONTRI,emp.DATAFREEZEFLAG as DATAFREEZEFLAG,emp.form7narration as form7narration,emp.pcHeldAmt as pcHeldAmt,nvl(emp.emolumentmonths,'1') as emolumentmonths, PCCALCAPPLIED,ARREARFLAG, nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG from "
				+ "(select distinct to_char(to_date('"
				+ fromDate
				+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' and    rownum "
				+ "<= to_date('"
				+ toDate
				+ "','dd-mon-yyyy')-to_date('"
				+ fromDate
				+ "','dd-mon-yyyy')+1) empdt ,(SELECT cpfaccno,to_char(MONTHYEAR,'MONYYYY') empmonyear,TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR,ROUND(EMOLUMENTS,2) AS EMOLUMENTS,ROUND(EMPPFSTATUARY,2) AS EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,AIRPORTCODE,REGION,EMPFLAG,PENSIONCONTRI,DATAFREEZEFLAG,form7narration,pcHeldAmt,nvl(emolumentmonths,'1') as emolumentmonths,PCCALCAPPLIED,ARREARFLAG, nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and ("
				+ cpfString
				+ " "+condition+") AND MONTHYEAR>= TO_DATE('"
				+ fromDate
				+ "','DD-MON-YYYY') and empflag='Y' ORDER BY TO_DATE(MONTHYEAR, 'dd-Mon-yy')) emp  where empdt.monyear = emp.empmonyear(+)   and empdt.monyear in (select to_char(MONTHYEAR,'MONYYYY')monyear FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and  ("
				+ cpfString
				+ "  "+condition+") and  MONTHYEAR >= TO_DATE('"
				+ fromDate
				+ "', 'DD-MON-YYYY')  group by  to_char(MONTHYEAR,'MONYYYY')  having count(*)>1)"
				+ " union	 (select TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||  SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR, emp.MONTHYEAR AS MONTHYEAR,"
				+ " emp.EMOLUMENTS AS EMOLUMENTS,emp.EMPPFSTATUARY AS EMPPFSTATUARY,emp.EMPVPF AS EMPVPF,emp.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,"
				+ "emp.EMPADVRECINTEREST AS EMPADVRECINTEREST,emp.AIRPORTCODE AS AIRPORTCODE,emp.cpfaccno AS CPFACCNO,emp.region as region,'Single' DUPFlag,emp.PENSIONCONTRI as PENSIONCONTRI,emp.DATAFREEZEFLAG as DATAFREEZEFLAG,emp.form7narration as form7narration,emp.pcHeldAmt as pcHeldAmt,nvl(emp.emolumentmonths,'1') as emolumentmonths,PCCALCAPPLIED,ARREARFLAG, nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG from (select distinct to_char(to_date("
				+ dojQuery
				+ ",'dd-mon-yyyy') + rownum -1,'MONYYYY') MONYEAR from employee_pension_validate where empflag='Y' AND rownum <= to_date('"
				+ toDate
				+ "','dd-mon-yyyy')-to_date("
				+ dojQuery
				+ ",'dd-mon-yyyy')+1 ) empdt,"
				+ "(SELECT cpfaccno,to_char(MONTHYEAR, 'MONYYYY') empmonyear,TO_CHAR(MONTHYEAR, 'DD-MON-YYYY') AS MONTHYEAR,ROUND(EMOLUMENTS, 2) AS EMOLUMENTS,"
				+ " ROUND(EMPPFSTATUARY, 2) AS EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,AIRPORTCODE,REGION,EMPFLAG,PENSIONCONTRI,DATAFREEZEFLAG,form7narration,pcHeldAmt,nvl(emolumentmonths,'1') as emolumentmonths,PCCALCAPPLIED,ARREARFLAG, nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG "
				+ " FROM EMPLOYEE_PENSION_VALIDATE   WHERE empflag = 'Y' and ("
				+ cpfString
				+ " "+condition+" ) AND MONTHYEAR >= TO_DATE('"
				+ fromDate
				+ "', 'DD-MON-YYYY') and "
				+ " empflag = 'Y'  ORDER BY TO_DATE(MONTHYEAR, 'dd-Mon-yy')) emp where empdt.monyear = emp.empmonyear(+)   and empdt.monyear not in (select to_char(MONTHYEAR,'MONYYYY')monyear FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and  ("
				+ cpfString
				+ " "+condition+") AND MONTHYEAR >= TO_DATE('"
				+ fromDate
				+ "','DD-MON-YYYY')  group by  to_char(MONTHYEAR,'MONYYYY')  having count(*)>1)))mo where to_date(to_char(mo.Empmnthyear,'dd-Mon-yyyy')) >= to_date('01-Apr-1995')";

		//String advances = "select amount from employee_pension_advances where pensionno=1";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer();
		String monthsBuffer = "", formatter = "", tempMntBuffer = "";

		long transMntYear = 0, empRetriedDt = 0;
		double pensionCOntr = 0;
		double pensionCOntr1 = 0;
		String recordCount = "";
		int getDaysBymonth = 0, cnt = 0, checkMnts = 0, chkPrvmnth = 0, chkCrntMnt = 0;
		double PENSIONCONTRI = 0;
		boolean contrFlag = false, chkDOBFlag = false, formatterFlag = false;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			log.info(sqlQuery);
			rs = st.executeQuery(sqlQuery);
			log.info("Query" + sqlQuery);
			 //log.info("Query" +sqlQuery1);
			
			
			String monthYear = "", days = "";
			int i = 0, count = 0;
			String marMnt = "", prvsMnth = "", crntMnth = "", frntYear = "";
			while (rs.next()) {

				log.info("=========> while block ");
				if (rs.getString("MONTHYEAR") != null) {
					monthYear = rs.getString("MONTHYEAR");
					buffer.append(rs.getString("MONTHYEAR"));
				} else {
					i++;
					monthYear = commonUtil.converDBToAppFormat(rs
							.getDate("EMPMNTHYEAR"), "MM/dd/yyyy");
					buffer.append(monthYear);

				}
				buffer.append(",");
				count++;
				if (rs.getString("EMOLUMENTS") != null) {
					buffer.append(rs.getString("EMOLUMENTS"));
				} else {
					buffer.append("0");
				}
				buffer.append(",");
				if (rs.getString("EMPPFSTATUARY") != null) {
					buffer.append(rs.getString("EMPPFSTATUARY"));
				} else {
					buffer.append("0");
				}

				buffer.append(",");
				if (rs.getString("EMPVPF") != null) {
					buffer.append(rs.getString("EMPVPF"));
				} else {
					buffer.append("0");
				}

				buffer.append(",");
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					buffer.append(rs.getString("EMPADVRECPRINCIPAL"));
				} else {
					buffer.append("0");
				}

				buffer.append(",");
				if (rs.getString("EMPADVRECINTEREST") != null) {
					buffer.append(rs.getString("EMPADVRECINTEREST"));
				} else {
					buffer.append("0");
				}

				buffer.append(",");

				if (rs.getString("AIRPORTCODE") != null) {
					buffer.append(rs.getString("AIRPORTCODE"));
				} else {
					buffer.append("-NA-");
				}
				buffer.append(",");
				String region = "";
				if (rs.getString("region") != null) {
					region = rs.getString("region");
				} else {
					region = "-NA-";
				}

				log.info("======================1============");
				if (!monthYear.equals("-NA-") && !dateOfRetriment.equals("")) {
					transMntYear = Date.parse(monthYear);
					empRetriedDt = Date.parse(dateOfRetriment);
					/*
					 * log.info("monthYear" + monthYear + "dateOfRetriment" +
					 * dateOfRetriment);
					 */
					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == empRetriedDt) {
						chkDOBFlag = true;
					}
				}

				log.info("======================2============");
				if (rs.getString("EMOLUMENTS") != null) {
					// log.info("whetherOption==="+whetherOption+"Month
					// Year===="+rs.getString("MONTHYEAR"));
					if (contrFlag != true) {
						pensionCOntr = this
								.pensionCalculation(rs.getString("MONTHYEAR"),
										rs.getString("EMOLUMENTS"),
										whetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionCOntr = Math.round(pensionCOntr
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionCOntr = 0;
					}

					buffer.append(Double.toString(pensionCOntr));
				} else {
					buffer.append("0");
				}

				buffer.append(",");
				log.info("======================3============");
				if (rs.getDate("EMPMNTHYEAR") != null) {
					buffer.append(commonUtil.converDBToAppFormat(rs
							.getDate("EMPMNTHYEAR")));
				} else {
					buffer.append("-NA-");
				}
				buffer.append(",");

				if (rs.getString("CPFACCNO") != null) {
					buffer.append(rs.getString("CPFACCNO"));
				} else {
					buffer.append("-NA-");
				}
				buffer.append(",");

				if (rs.getString("region") != null) {
					buffer.append(rs.getString("region"));
				} else {
					buffer.append("-NA-");
				}

				buffer.append(",");
				log.info("======================4============");
				//log.info(rs.getString("Dupflag"));
				if (rs.getString("Dupflag") != null) {
					recordCount = rs.getString("Dupflag");
					buffer.append(rs.getString("Dupflag"));
				}
				buffer.append(",");
				DateFormat df = new SimpleDateFormat("dd-MMM-yy");

				Date transdate = df.parse(monthYear);
				float cpfarrear = 0, pfstaturoty = 0;
				log.info("======================5============");
				if (transdate.after(new Date("31-Mar-08")) ||(rs.getString("Deputationflag").equals("Y"))) {
					if (rs.getString("PENSIONCONTRI") != null) {
						PENSIONCONTRI = Double.parseDouble(rs
								.getString("PENSIONCONTRI"));
						buffer.append(rs.getString("PENSIONCONTRI"));
					} else {
						buffer.append("0.00");
					}
				} else if (rs.getString("EMOLUMENTS") != null) {
						if (contrFlag != true) {
						pensionCOntr1 = this
								.pensionCalculation(rs.getString("MONTHYEAR"),
										rs.getString("EMOLUMENTS"),
										whetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionCOntr1 = Math.round(pensionCOntr1
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionCOntr1 = 0;
					}

					buffer.append(Double.toString(pensionCOntr1));
					log.info("======================6============");
				} else {
					buffer.append("0");
				}
				buffer.append(",");
				if (rs.getString("Datafreezeflag") != null) {
					buffer.append(rs.getString("Datafreezeflag"));
				} else {
					buffer.append("-NA-");
				}
				log.info("======================7============");
				buffer.append(",");
				if (rs.getString("FORM7NARRATION") != null) {
					buffer.append(rs.getString("FORM7NARRATION"));
				} else {
					buffer.append(" ");
				}
				log.info("======================8============");
				buffer.append(",");
				if (rs.getString("pcHeldAmt") != null) {
					buffer.append(rs.getString("pcHeldAmt"));
				} else {
					buffer.append(" ");
				}
				buffer.append(",");
				log.info("======================9============");
				if (rs.getString("emolumentmonths") != null) {
					buffer.append(rs.getString("emolumentmonths"));
				} else {
					buffer.append(" ");
				}
				buffer.append(",");
				if (rs.getString("PCCALCAPPLIED") != null) {
					buffer.append(rs.getString("PCCALCAPPLIED"));
				} else {
					buffer.append(" ");
				}
				buffer.append(",");
				if (rs.getString("ARREARFLAG") != null) {
					buffer.append(rs.getString("ARREARFLAG"));
				} else {
					buffer.append(" ");
				}
				buffer.append(",");
				if (rs.getString("Deputationflag") != null) {
					buffer.append(rs.getString("Deputationflag"));
				} else {
					buffer.append("N");
				}
				log.info("======================10============");
				buffer.append(",");
			 
				monthYear = commonUtil.converDBToAppFormat(monthYear,
						"dd-MMM-yyyy", "MM-yy");

				crntMnth = monthYear.substring(0, 2);
				log.info("======================11============");
				if (monthYear.substring(0, 2).equals("02")) {
					marMnt = "_03";
				} else {
					marMnt = "";
				}
				log.info("======================12============");
				if (monthsBuffer.equals("")) {
					cnt++;

					if (!monthYear.equals("04-95")) {
						String[] checkOddEven = monthYear.split("-");
						int mntVal = Integer.parseInt(checkOddEven[0]);
						if (mntVal % 2 != 0) {
							monthsBuffer = "00-00" + "#" + monthYear + "_03";
							cnt = 0;
							formatterFlag = true;
						} else {
							monthsBuffer = monthYear + marMnt;
							formatterFlag = false;
						}

					} else {

						monthsBuffer = monthYear + marMnt;
					}

					 log.info("Month Buffer is blank"+monthsBuffer);
				} else {
					cnt++;
					if (cnt == 2) {
						formatter = "#";
						cnt = 0;
					} else {
						formatter = "@";
					}

					if (!prvsMnth.equals("") && !crntMnth.equals("")) {

						chkPrvmnth = Integer.parseInt(prvsMnth);
						chkCrntMnt = Integer.parseInt(crntMnth);
						checkMnts = chkPrvmnth - chkCrntMnt;
						if (checkMnts > 1 && checkMnts < 9) {
							frntYear = prvsMnth;
						}
						prvsMnth = "";

					}
					// log.info("chkPrvmnth"+chkPrvmnth+"chkCrntMnt"+chkCrntMnt+
					// "Monthyear"+monthYear+"buffer ==== checkMnts"+checkMnts);
					if (frntYear.equals("")) {
						monthsBuffer = monthsBuffer + formatter + monthYear
								+ marMnt.trim();
					} else if (!frntYear.equals("")) {

						// log.info("buffer ==== frntYear"+monthsBuffer);
						// log.info("monthYear======"+monthYear+"cnt"+cnt+
						// "frntYear"+frntYear);

						monthsBuffer = monthsBuffer + "*" + frntYear
								+ formatter + monthYear;

					}
					if (prvsMnth.equals("")) {
						prvsMnth = crntMnth;
					}

				}
				frntYear = "";
				buffer.append(monthsBuffer.toString());
				buffer.append("=");

			}
			if (count == i) {
				buffer = new StringBuffer();
			} else {

			}

			
			
			log.info("======================***********===========================");
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		return buffer.toString();
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

	/*
	 * private double pensionCalculation(String monthYear,String
	 * emoluments,String penionOption){ double penContrVal = 0.0; DecimalFormat
	 * df = new DecimalFormat("#########0.00"); String sqlQuery=""; long
	 * beginDate
	 * =0,endDate=0,empPenDt=0,endNovMnthDt=0,secBeginDate=0,secEndDate=
	 * 0,secPenBeginDate=0; beginDate=Date.parse("01-Apr-1995");
	 * endDate=Date.parse("01-Nov-1995");
	 * endNovMnthDt=Date.parse("15-Nov-1995");
	 * secBeginDate=Date.parse("01-Jan-1996");
	 * secPenBeginDate=Date.parse("01-Jun-2000");
	 * secEndDate=Date.parse("01-Feb-2008"); empPenDt=Date.parse(monthYear);
	 * 
	 * if(empPenDt>=beginDate && empPenDt<=endDate){
	 * //log.info("beginDate"+beginDate+"endDate"+endDate+"empPenDt"+empPenDt);
	 * penContrVal= new Double(df.format((Double.parseDouble(emoluments) 1.16) /
	 * 100f)).doubleValue()2; }else if(empPenDt>=secBeginDate &&
	 * empPenDt<=secEndDate){ if(!penionOption.equals("---")){
	 * if(penionOption.trim().equals("A")){ penContrVal= new
	 * Double(df.format((Double.parseDouble(emoluments) 8.33) /
	 * 100f)).doubleValue(); // log.info("penionOption==="+penionOption+"Pension
	 * Total Value"+penContrVal); }else if(penionOption.trim().equals("B")){
	 * penContrVal= new Double(df.format((Double.parseDouble(emoluments) 8.33) /
	 * 100f)).doubleValue(); if(empPenDt>=secPenBeginDate){
	 * if(penContrVal>6500){ penContrVal=6500; } }else{ if(penContrVal>5000){
	 * penContrVal=5000; } } // log.info("penionOption==="+penionOption+"Pension
	 * Total Value"+penContrVal); } } }else
	 * if(monthYear.toLowerCase().equals("01-dec-1995")){ double
	 * salary=0,perDaySal=0,firstHalfMnthAmt=0,secHalfMnthAmt=0; salary=new
	 * Double(df.format(Double.parseDouble(emoluments))).doubleValue();
	 * perDaySal=salary/31; firstHalfMnthAmt=(((perDaySal15)1.13)/100)2;
	 * secHalfMnthAmt=((perDaySal16)8.33)/100; penContrVal=new
	 * Double(df.format(firstHalfMnthAmt+secHalfMnthAmt)).doubleValue();
	 * //log.info
	 * ("salary"+salary+"penContrVal"+penContrVal+"perDaySal"+perDaySal
	 * +"firstHalfMnthAmt"+firstHalfMnthAmt+"secHalfMnthAmt"+secHalfMnthAmt); }
	 * return penContrVal; }
	 */

	public double pensionCalculation(String monthYear, String emoluments,
			String penionOption, String region,String emolumentMonths) {
		double penContrVal = 0.0;
		log.info("emolumentMonths "+emolumentMonths);
		if(!emolumentMonths.trim().equals("0.5")&& !emolumentMonths.trim().equals("1")&& !emolumentMonths.trim().equals("2") && !emolumentMonths.trim().equals("90")&& !emolumentMonths.trim().equals("120")&& !emolumentMonths.trim().equals("150")&& !emolumentMonths.trim().equals("180")){
			int getDaysBymonth = this.getNoOfDaysForsalRecovery(monthYear);
			emolumentMonths=String.valueOf(Double.parseDouble(emolumentMonths.trim())/getDaysBymonth);
			DecimalFormat twoDForm = new DecimalFormat("#.####");
			log.info("emolumentMonths "+emolumentMonths +"getDaysBymonth "+getDaysBymonth);
			emolumentMonths=twoDForm.format(Double.parseDouble(emolumentMonths));
			}else if(emolumentMonths.trim().equals("90")|| emolumentMonths.trim().equals("120")|| emolumentMonths.trim().equals("150")|| emolumentMonths.trim().equals("180")){
				if(emolumentMonths.trim().equals("90")){
					emolumentMonths="3";
				}else if(emolumentMonths.trim().equals("120")){
					emolumentMonths="4";
				}else if(emolumentMonths.trim().equals("150")){
					emolumentMonths="5";
				}else if(emolumentMonths.trim().equals("180")){
					emolumentMonths="6";
				}
				else if(emolumentMonths.trim().equals("210")){
					emolumentMonths="7";
				}
				else if(emolumentMonths.trim().equals("240")){
					emolumentMonths="8";
				}
				else if(emolumentMonths.trim().equals("270")){
					emolumentMonths="9";
				}
				else if(emolumentMonths.trim().equals("300")){
					emolumentMonths="10";
				}else{
					emolumentMonths="1";
				}
			}
		log.info("emolumentMonths "+emolumentMonths);
		DecimalFormat df = new DecimalFormat("#########0.00");
		String sqlQuery = "", chkDecMnth = "";
		boolean dtFlag = false;
		long beginDate = 0, endDate = 0, empPenDt = 0, endNovMnthDt = 0, secBeginDate = 0, secEndDate = 0, secPenBeginDate = 0;
		if (monthYear.substring(0, 2).equals("01")) {
			dtFlag = true;
		}
		if (dtFlag == true) {
			beginDate = Date.parse("01-Apr-1995");
			endDate = Date.parse("01-Nov-1995");
			secBeginDate = Date.parse("01-Jan-1996");
			chkDecMnth = "01-dec-1995";
		} else {
			beginDate = Date.parse("30-Apr-1995");
			endDate = Date.parse("30-Nov-1995");
			secBeginDate = Date.parse("20-Jan-1996");
			chkDecMnth = "31-dec-1995";
		}
		String transMonthYear = "";
		try {
			transMonthYear = commonUtil.converDBToAppFormat(monthYear,
					"dd-MMM-yyyy", "-MMM-yyyy");
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		if (transMonthYear.equals("-Apr-1995")) {
			log.info("transMonthYear in if " + transMonthYear + "region"
					+ region);
			penContrVal = 0.0;
			return penContrVal;
		}
		secPenBeginDate = Date.parse("01-Jul-2001");
		// secEndDate = Date.parse("31-Mar-2008");
		secEndDate = Date.parse("31-Mar-2011");
		empPenDt = Date.parse(monthYear);

		if (empPenDt >= beginDate && empPenDt <= endDate) {
			// log.info("beginDate"+beginDate+"endDate"+endDate+"empPenDt"+
			// empPenDt);
			if (Double.parseDouble(emoluments) >= 5000) {
				emoluments = "5000";
			}
			penContrVal = new Double(df
					.format((Double.parseDouble(emoluments) * 1.16* Double.parseDouble(emolumentMonths)) / 100f))
					.doubleValue() * 2;

		} else if (empPenDt >= secBeginDate && empPenDt <= secEndDate) {
			if (!penionOption.equals("---")) {
				if (penionOption.trim().equals("A")) {
					penContrVal = new Double(df.format((Double
							.parseDouble(emoluments) * 8.33) / 100f))
							.doubleValue();
					
				} else if (penionOption.trim().equals("B")
						|| penionOption.toLowerCase().trim().equals(
								"NO".toLowerCase())) {
                   
					if (empPenDt >= secPenBeginDate) {
						if (Double.parseDouble(emoluments) >= 6500) {
							penContrVal = Math.round((6500 * 8.33* Double.parseDouble(emolumentMonths)) / 100f);
						} else if (Double.parseDouble(emoluments) < 6500) {
							penContrVal = new Double(df.format((Double
									.parseDouble(emoluments) * 8.33* Double.parseDouble(emolumentMonths)) / 100f))
									.doubleValue();
						}
					} else {
						if (Double.parseDouble(emoluments) < 5000) {
							penContrVal = new Double(df.format((Double
									.parseDouble(emoluments) * 8.33) / 100f))
									.doubleValue();
						} else if (Double.parseDouble(emoluments) >= 5000) {
							penContrVal = (5000 * 8.33*Double.parseDouble(emolumentMonths)) / 100f;

						}
					}

					// log.info("penionOption==="+penionOption+"Pension Total
					// Value"+penContrVal);
				}
			}
		} else if (monthYear.toLowerCase().equals(chkDecMnth)) {
			double salary = 0, perDaySal = 0, firstHalfMnthAmt = 0, secHalfMnthAmt = 0;

			if (penionOption.trim().equals("B")
					|| penionOption.toLowerCase().trim().equals(
							"NO".toLowerCase())) {
				if (Double.parseDouble(emoluments) >= 5000) {
					emoluments = "5000";
				}
			}
			/*
			 * Issue fixed by suresh kumar on 18-Aug-2009 Issue_no:86
			 */
			salary = new Double(df.format(Double.parseDouble(emoluments)))
					.doubleValue();
			perDaySal = salary / 30;
			secHalfMnthAmt = Math.round(salary * 8.33 / 2 / 100);
			log.info("salary================" + salary + "==========" + salary
					* 8.33 / 2 / 100 + "secHalfMnthAmt" + secHalfMnthAmt);

			if (salary >= 5000) {
				salary = 5000;

			}
			firstHalfMnthAmt = (Math.round((salary * 1.16) / 2 / 100)) * 2;

			log.info("firstHalfMnthAmt" + firstHalfMnthAmt + "secHalfMnthAmt"
					+ secHalfMnthAmt);
			penContrVal = new Double(df.format(firstHalfMnthAmt
					+ secHalfMnthAmt)).doubleValue();

		}
		log.info(monthYear + "====" + emoluments + "penContrVal" + penContrVal
				+ "penionOption" + penionOption);
		return penContrVal;
	}
	public double pensionFormsCalculation(String monthYear, String emoluments,
			String penionOption, String region, boolean formFlag,
			boolean secondFormFlag,String emolumentMonths) {
		double penContrVal = 0.0;
		DecimalFormat df = new DecimalFormat("#########0.00");
		String  chkDecMnth = "";
		boolean dtFlag = false;
		long beginDate = 0, endDate = 0, empPenDt = 0, secBeginDate = 0, secEndDate = 0;
	
		if(!emolumentMonths.trim().equals("0.5")&& !emolumentMonths.trim().equals("1")&& !emolumentMonths.trim().equals("2") && !emolumentMonths.trim().equals("90")&& !emolumentMonths.trim().equals("120")&& !emolumentMonths.trim().equals("150")&& !emolumentMonths.trim().equals("180")){
			int getDaysBymonth = this.getNoOfDaysForsalRecovery(monthYear);
			emolumentMonths=String.valueOf(Double.parseDouble(emolumentMonths.trim())/getDaysBymonth);
			DecimalFormat twoDForm = new DecimalFormat("#.#####");
			log.info("emolumentMonths "+emolumentMonths +"getDaysBymonth "+getDaysBymonth);
			emolumentMonths=twoDForm.format(Double.parseDouble(emolumentMonths));
		}else if(emolumentMonths.trim().equals("90")|| emolumentMonths.trim().equals("120")|| emolumentMonths.trim().equals("150")|| emolumentMonths.trim().equals("180")){
			if(emolumentMonths.trim().equals("90")){
				emolumentMonths="3";
			}else if(emolumentMonths.trim().equals("120")){
				emolumentMonths="4";
			}else if(emolumentMonths.trim().equals("150")){
				emolumentMonths="5";
			}else if(emolumentMonths.trim().equals("180")){
				emolumentMonths="6";
			}else if(emolumentMonths.trim().equals("210")){
				emolumentMonths="7";
			}
			else if(emolumentMonths.trim().equals("240")){
				emolumentMonths="8";
			}
			else if(emolumentMonths.trim().equals("270")){
				emolumentMonths="9";
			}
			else if(emolumentMonths.trim().equals("300")){
				emolumentMonths="10";
			}else{
				emolumentMonths="1";
			}
		}
		if (monthYear.substring(0, 2).equals("01")) {
			dtFlag = true;
		}
		if (dtFlag == true) {
			beginDate = Date.parse("01-Apr-1995");
			endDate = Date.parse("01-Nov-1995");
			secBeginDate = Date.parse("01-Jan-1996");
			chkDecMnth = "01-dec-1995";
		} else {
			beginDate = Date.parse("30-Apr-1995");
			endDate = Date.parse("30-Nov-1995");
			secBeginDate = Date.parse("20-Jan-1996");
			chkDecMnth = "31-dec-1995";
		}
		String transMonthYear = "";
		try {
			transMonthYear = commonUtil.converDBToAppFormat(monthYear,
					"dd-MMM-yyyy", "-MMM-yyyy");
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		if (transMonthYear.equals("-Apr-1995")) {
			log.info("transMonthYear in if " + transMonthYear + "region"
					+ region);
			penContrVal = 0.0;
			return penContrVal;
		}
		// modifed from 2009 to 2011 on apr 15 th 2010
		secEndDate = Date.parse("31-Mar-2011");

		empPenDt = Date.parse(monthYear);

		if (empPenDt >= beginDate && empPenDt <= endDate) {
			// log.info("beginDate"+beginDate+"endDate"+endDate+"empPenDt"+
			// empPenDt);
			if (Double.parseDouble(emoluments) >= 5000) {
				emoluments = "5000";
			}
			penContrVal = new Double(df
					.format((Double.parseDouble(emoluments) * 1.16* Double.parseDouble(emolumentMonths)) / 100f))
					.doubleValue() * 2;

		} else if (empPenDt >= secBeginDate && empPenDt <= secEndDate) {
			if (!penionOption.equals("---")) {
				if (penionOption.trim().equals("A")) {
					penContrVal = new Double(df.format((Double
							.parseDouble(emoluments) * 8.33) / 100f))
							.doubleValue();
					// log.info("penionOption==="+penionOption+"Pension Total
					// Value"+penContrVal);
				} else if (penionOption.trim().equals("B")
						|| penionOption.toLowerCase().trim().equals(
								"NO".toLowerCase())) {

					if (Double.parseDouble(emoluments) >= 6500) {
						penContrVal = Math.round((6500 * 8.33* Double.parseDouble(emolumentMonths)) / 100f);
					} else if (Double.parseDouble(emoluments) < 6500) {
						penContrVal = Math.round(new Double(df.format((Double
								.parseDouble(emoluments) * 8.33* Double.parseDouble(emolumentMonths)) / 100f))
								.doubleValue());
					}
				}
			}

		} else if (monthYear.toLowerCase().equals(chkDecMnth)) {
			double salary = 0, perDaySal = 0, firstHalfMnthAmt = 0, secHalfMnthAmt = 0;

			if (penionOption.trim().equals("B")
					|| penionOption.toLowerCase().trim().equals(
							"NO".toLowerCase())) {
				if (Double.parseDouble(emoluments) >= 5000) {
					emoluments = "5000";
				}
			}
			/*
			 * Issue fixed by suresh kumar on 18-Aug-2009 Issue_no:86
			 */
			salary = new Double(df.format(Double.parseDouble(emoluments)))
					.doubleValue();
			perDaySal = salary / 30;
			secHalfMnthAmt = Math.round(salary * 8.33 / 2 / 100);
			if (salary >= 5000) {
				salary = 5000;

			}
			firstHalfMnthAmt = (Math.round((salary * 1.16) / 2 / 100)) * 2;

			if (formFlag == true) {
				penContrVal = new Double(df.format(firstHalfMnthAmt))
						.doubleValue();
			} else if (secondFormFlag == true) {

				penContrVal = new Double(df.format(secHalfMnthAmt))
						.doubleValue();
			} else {
				penContrVal = new Double(df.format(firstHalfMnthAmt
						+ secHalfMnthAmt)).doubleValue();
			}

		}
		// log.info(monthYear+"===="+emoluments+"penContrVal"+penContrVal+
		// "penionOption"+penionOption);
		return penContrVal;
	}

	public ArrayList financeYearList() {
		ArrayList financeYearList = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		FinancialYearBean yearBean = new FinancialYearBean();
		sqlQuery = "SELECT FIANCIALYEAR,FINANCIALMONTH FROM PENSION_FINANCIALYEAR ORDER BY FIANCIALYEAR";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				yearBean = new FinancialYearBean();
				yearBean.setMonth(rs.getString("FINANCIALMONTH"));
				yearBean.setFromDate(rs.getString("FIANCIALYEAR"));
				financeYearList.add(yearBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return financeYearList;
	}

	public int getEmpPensionCount(String empserialNO) {
		int count = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		FinancialYearBean yearBean = new FinancialYearBean();
		if (empserialNO.equals("")) {
			sqlQuery = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_INFO WHERE empserialnumber is not null  ORDER BY empserialnumber";

		} else {
			sqlQuery = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_INFO WHERE empserialnumber is not null  and empserialnumber='"
					+ empserialNO + "' ORDER BY empserialnumber";
		}
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				count = rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return count;
	}

	private String preparedCPFString(String[] cpfacno, String[] regions) {
		String cpfstring = "";
		for (int i = 0; i < cpfacno.length; i++) {
			if (cpfstring.equals("")) {
				cpfstring = " (CPFACCNO='" + cpfacno[i] + "' AND REGION='"
						+ regions[i] + "') ";
			} else {
				cpfstring = cpfstring + " OR " + " (CPFACCNO='" + cpfacno[i]
						+ "' AND REGION='" + regions[i] + "') ";
			}

		}
		return cpfstring;
	}

	public void deleteTransactionData(String cpfAccno, String monthyear,
			String region, String airportcode,String Computername,String Username,String pfid) {
		Connection con = null;
		Statement st = null;
		String sqlQuery = "",condition="";
		String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			DateFormat df = new SimpleDateFormat("dd-MMM-yy");
			Date transdate = df.parse(monthyear);
			if (!airportcode.equals("") && !airportcode.equals("-NA-")) {
				if (transdate.before(new Date("31-Mar-08"))) {
					condition=" cpfaccno='"+cpfAccno+"'";
				}else{
					condition=" Pensionno='"+pfid+"'";	
				}
				sqlQuery = "update employee_pension_validate set empflag='N',username='"+Username+"',ipaddress='"+Computername+"',UPDATEDDATE='"+updatedDate+"' where "+condition+" and to_char(monthyear,'dd-mon-yyyy')='"
						+ monthyear.toLowerCase()
						+ "' and region='"
						+ region
						+ "' and airportcode='" + airportcode + "'  ";
			} else {
				
				if (transdate.before(new Date("31-Mar-08"))) {
					condition=" cpfaccno='"+cpfAccno+"'";
				}else{
					condition=" Pensionno='"+pfid+"'";	
				}
				sqlQuery = "update employee_pension_validate set empflag='N',username='"+Username+"',ipaddress='"+Computername+"',UPDATEDDATE='"+updatedDate+"' where "+condition+" and to_char(monthyear,'dd-mon-yyyy')='"
						+ monthyear.toLowerCase()
						+ "' and region='"
						+ region
						+ "' and (airportcode is null or airportcode='-NA-') ";

			}
			log.info(" " + sqlQuery);
			st.executeQuery(sqlQuery);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {

		}
	}

	public void editTransactionData(String cpfAccno, String monthyear,
			String emoluments, String empvpf, String principle,
			String interest, String contri, String advance, String loan,
			String aailoan, String pfid, String region, String airportcode,
			String username, String computername,String from7narration,String pcheldamt,String noofmonths,String arrearflag,String duputationflag,String pensionoption) {
		log.info("pfid"+pfid +" noofmonths "+noofmonths +"arrearflag"+arrearflag);
		double pensionCOntr=0.0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", transMonthYear = "", emoluments_log = "", emoluments_log_history = "",arrearQuery="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			DateFormat df = new SimpleDateFormat("dd-MMM-yy");
			Date transdate = df.parse(monthyear);
			transMonthYear = commonUtil.converDBToAppFormat(monthyear
					.trim(), "dd-MMM-yy", "-MMM-yy");
			String emppfstatuary = "0.00", oldemoluments = "0.00", oldemppfstatuary = "0.00", pf = "0.00";
			if (cpfAccno.indexOf(",") != -1) {
				cpfAccno = cpfAccno.substring(0, cpfAccno.indexOf(","));
			}
			log.info("cpfAccno " + cpfAccno);
			if (transdate.after(new Date("31-Mar-98")) && duputationflag!="Y") {
				emppfstatuary = String
						.valueOf(Float.parseFloat(emoluments) * 12 / 100);
			} else {
				emppfstatuary = String
						.valueOf(Float.parseFloat(emoluments) * 10 / 100);
			}
			if (emppfstatuary != "" && emppfstatuary != "0.00") {
				pf = String.valueOf(Float.parseFloat(emppfstatuary)
						- Float.parseFloat(contri));
			}
			ImportDao IDAO = new ImportDao();
			FinacialDataBean bean = new FinacialDataBean();
			String checkArrearTable=IDAO.checkArrears(con, monthyear, cpfAccno, "", region,pfid);
			bean = IDAO.getEmolumentsBean(con, monthyear, cpfAccno, "", region,pfid);
			log.info("emoluments " + bean.getEmoluments());
			if(duputationflag.equals("Y")){
				emppfstatuary=bean.getEmpPfStatuary();
			}
			
			
			oldemppfstatuary=bean.getEmpPfStatuary();
			if (bean.getEmoluments() != "" && bean.getEmoluments() != "0.00") {
				transMonthYear = commonUtil.converDBToAppFormat(monthyear
						.trim(), "dd-MMM-yy", "-MMM-yy");
				String wherecondition="";
									
				if(pfid=="" || transdate.before(new Date("31-Mar-2008"))){
					wherecondition+="cpfaccno='" + cpfAccno+"' and region='" + region
					+ "'";
					
				}else{
					wherecondition+="pensionno='"+pfid+"'";
				}
				sqlQuery = "update employee_pension_validate set emoluments='"
						+ emoluments + "',emppfstatuary='" + emppfstatuary
						+ "',empvpf='" + empvpf + "',EMPADVRECPRINCIPAL='"
						+ principle + "',EMPADVRECINTEREST='" + interest
						+ "',PENSIONCONTRI='" + contri + "',pf='"+ pf+ "', empflag='Y',edittrans='Y',arrearflag='"+arrearflag.trim()+"',FORM7NARRATION='"+from7narration+"',pcheldamt='"+pcheldamt.trim()+"',emolumentmonths='"+noofmonths+"' where "+wherecondition+" and  to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear + "'  AND empflag='Y' ";
				log.info("checkArrearTable "+checkArrearTable);
				if(checkArrearTable.trim().equals("") && arrearflag.equals("Y")){
				 arrearQuery="insert into employee_pension_arrear(PENSIONNO,ARREARAMT,ARREARDATE,REGION,CPFACCNO,airportcode) values('"+pfid+"','"+emoluments+"','"+monthyear+"','"+region+"','"+cpfAccno+"','"+airportcode+"')";
				st.executeUpdate(arrearQuery);
				}
				
			} else {
				 if(airportcode.trim().equals("-NA-")){
					 airportcode=""; 
				 }

				pensionCOntr = this.pensionCalculation(monthyear,emoluments,pensionoption, region,"1");
				pf=String.valueOf(Double.parseDouble(emppfstatuary)-pensionCOntr);
				sqlQuery = "insert into employee_pension_validate (emoluments,emppfstatuary,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf,monthyear,cpfaccno,region,pensionno,FORM7NARRATION,pcheldamt,EMPFLAG,arrearflag,edittrans,remarks,AIRPORTCODE) values('"
						+ emoluments
						+ "','"
						+ emppfstatuary
						+ "','"
						+ empvpf
						+ "','"
						+ principle
						+ "','"
						+ interest
						+ "','"
						+ pensionCOntr
						+ "','"
						+ pf
						+ "','"
						+ monthyear
						+ "','"
						+ cpfAccno
						+ "','" + region + "','" + pfid + "','"+from7narration+"','"+pcheldamt.trim()+"','Y','"+arrearflag.trim()+"','N','New Record','"+airportcode+"')";
				if(checkArrearTable.trim().equals("")&& arrearflag.equals("Y")){
					 arrearQuery="insert into employee_pension_arrear(PENSIONNO,ARREARAMT,ARREARDATE,REGION,CPFACCNO,airportcode) values('"+pfid+"','"+emoluments+"','"+monthyear+"','"+region+"','"+cpfAccno+"','"+airportcode+"')";
					st.executeUpdate(arrearQuery);
				}
			
			}
			if (advance != "" && advance != "0.00"&&!advance.equals("0")) {
				String checkAdvtbl = "select count(*) as count from employee_pension_advances where pensionno='"
						+ pfid
						+ "' and  to_char(ADVTRANSDATE,'dd-Mon-yy') like '%"
						+ transMonthYear + "'";
				rs = st.executeQuery(checkAdvtbl);
				String updateAdvtbl = "";
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count != 0) {
						updateAdvtbl = "update employee_pension_advances set AMOUNT='"
								+ advance
								+ "' where pensionno='"
								+ pfid
								+ "' and  to_char(ADVTRANSDATE,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";
						st.executeUpdate(updateAdvtbl);
					} else {
						updateAdvtbl = "insert into employee_pension_advances(AMOUNT,pensionno,ADVTRANSDATE) values('"
								+ advance
								+ "','"
								+ pfid
								+ "','"
								+ monthyear
								+ "')";
						st.executeUpdate(updateAdvtbl);
					}
				}

			}
			log.info("loan amout is "+loan);
			if (loan != "" && loan != "0.00" &&!loan.equals("0")) {
				String checkLoantbl = "select count(*) as count from employee_pension_loans where pensionno='"
						+ pfid
						+ "' and  to_char(loandate,'dd-Mon-yy') like '%"
						+ transMonthYear + "'";
				rs = st.executeQuery(checkLoantbl);
				String updateloantbl = "";
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count != 0) {
						updateloantbl = "update employee_pension_loans set SUB_AMT='"
								+ loan
								+ "',CONT_AMT='"
								+ aailoan
								+ "',LOANTYPE='NRF' where pensionno='"
								+ pfid
								+ "' and  to_char(LOANDATE,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";
						st.executeUpdate(updateloantbl);
					} else  {
						updateloantbl = "insert into employee_pension_loans(SUB_AMT,CONT_AMT,pensionno,LOANDATE,LOANTYPE) values('"
								+ loan
								+ "','"
								+ aailoan
								+ "','"
								+ pfid
								+ "','"
								+ monthyear + "','NRF')";
						st.executeUpdate(updateloantbl);
					}

				}
			}
			String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");
			String selectEmolumentsLog = "select count(*) as count from emoluments_log where cpfacno='"
					+ cpfAccno
					+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear + "' and region='" + region + "' ";
			rs = st.executeQuery(selectEmolumentsLog);
			while (rs.next()) {
				int count = rs.getInt(1);
				if (count == 0) {
					emoluments_log = "insert into emoluments_log(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,oldprinciple,newprinciple,oldinterest,newinterest,oldempvpf,newempvpf,OLDPENSIONCONTRI,NEWENSIONCONTRI,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
							+ bean.getEmoluments()
							+ "','"
							+ emoluments
							+ "','"
							+ oldemppfstatuary
							+ "','"
							+ emppfstatuary
							+ "','"
							+ bean.getPrincipal()
							+ "','"
							+ principle
							+ "','"
							+ bean.getInterest()
							+ "','"
							+ interest
							+ "','"
							+ bean.getEmpVpf()
							+ "','"
							+ empvpf
							+ "','"
							+ bean.getPenContri()
							+ "','"
							+ contri
							+ "','"
							+ monthyear
							+ "','"
							+ updatedDate
							+ "','"
							+ pfid
							+ "','"
							+ cpfAccno
							+ "','"
							+ region
							+ "','"
							+ username + "','" + computername + "')";
				} else {
					emoluments_log = "update emoluments_log set oldemoluments='"
							+ bean.getEmoluments()
							+ "',newemoluments='"
							+ emoluments
							+ "',oldemppfstatuary='"
							+ oldemppfstatuary
							+ "',newemppfstatuary='"
							+ emppfstatuary
							+ "',oldprinciple='"
							+ bean.getPrincipal()
							+ "',newprinciple='"
							+ principle
							+ "',oldinterest='"
							+ bean.getInterest()
							+ "',newinterest='"
							+ interest
							+ "',oldempvpf='"
							+ bean.getEmpVpf()
							+ "',newempvpf='"
							+ empvpf
							+ "',OLDPENSIONCONTRI='"
							+ bean.getPenContri()
							+ "',NEWENSIONCONTRI='"
							+ contri
							+ "',monthyear='"
							+ monthyear
							+ "',UPDATEDDATE='"
							+ updatedDate
							+ "',pensionno='"
							+ pfid
							+ "',region='"
							+ region
							+ "',username='"
							+ username
							+ "',computername='"
							+ computername
							+ "' where cpfacno='"
							+ cpfAccno
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "' and region='" + region + "'";

				}
				emoluments_log_history = "insert into emoluments_log_history(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
						+ bean.getEmoluments()
						+ "','"
						+ emoluments
						+ "','"
						+ oldemppfstatuary
						+ "','"
						+ emppfstatuary
						+ "','"
						+ monthyear
						+ "','"
						+ updatedDate
						+ "','"
						+ pfid
						+ "','"
						+ cpfAccno
						+ "','"
						+ region
						+ "','"
						+ username
						+ "','" + computername + "')";
				
			}
			String queryforProcess="insert into EMPLOYEE_GENRTED_PEND_ADJ_0B(PENSIONNO,cpfaccno,EMPLOYEENAME,AIRPORTCODE,region,REQGENDATE,STATUS,dateofbirth,wetheroption,dateofjoining,remarks,ADJSTATUS,PFCRDSTATUS) select pensionno,cpfacno,employeename,'"+airportcode+"','"+region+"',TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS'),'N' ,dateofbirth,wetheroption,dateofjoining,'"+username+"','N','N' from employee_personal_info where pensionno='"+pfid+"'";
			log.info("emoluments_log .." + emoluments_log);
			log.info("emoluments_log_history " + emoluments_log_history);
			log.info(" update transaction " + sqlQuery);
			log.info(" arrearQuery " + arrearQuery);
			st.executeUpdate(emoluments_log);
			st.executeUpdate(emoluments_log_history);
			st.executeUpdate(sqlQuery);
			st.executeUpdate(queryforProcess);
			
			String weatherOption="",retirementDate="",dateofbirth="",days="";
			if (!pfid.equals("")) {
				// if(!tempInfo[1].equals("XXX")){
				String checkPFID = "select wetheroption,pensionno, to_char(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth from employee_personal_info where to_char(pensionno)='"
						+ pfid + "'";
				log.info(checkPFID);
				rs = st.executeQuery(checkPFID);
    
				if(rs.next()){
				if (rs.getString("wetheroption") != null) {
					weatherOption=rs.getString("wetheroption").toString();
				} else {
					weatherOption="";
				}
				if(rs.getString("REIREMENTDATE")!=null){
					retirementDate=rs.getString("REIREMENTDATE");
				}else{
					retirementDate="";
				}
				if(rs.getString("dateofbirth")!=null){
					dateofbirth=rs.getString("dateofbirth");
				}else{
					dateofbirth="";
				}
				}
						
			}
			
			String pfidWithRegion = this.getEmployeeMappingPFInfo(con,pfid, "", "");

			String[] pfIDLists = pfidWithRegion.split("=");
			String pfIDString=this.preparedCPFString1(pfIDLists);
			//this.updateEmpPC200809("1-Apr-1995", "1-May-2011", pfIDString,weatherOption,region,true,retirementDate,dateofbirth,pfid);
			this.updatePCReport("1-Apr-1995", "1-May-2008",region,airportcode,pfid,cpfAccno,"","1 - 1","true");
			ImportDao iDao=new ImportDao();
			
			iDao.pentionContributionProcess2008to11(region,pfid);
			if (bean.getEmoluments() != "" && bean.getEmoluments() != "0.00"){
		//	this.updateTransTblPC(cpfAccno,con,region, monthyear, Double.parseDouble(contri), Double.parseDouble(pf), pfid);
			}
			FinancialReportService fs=new FinancialReportService();
			String years[]={"2010","2011"};
			FileWriter fw = null;
	        ResourceBundle bundle = ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
	      String  uploadFilePath = bundle.getString("upload.folder.path");
	        File filePath = new File(uploadFilePath);
	        if (!filePath.exists()) {
	     	   	filePath.mkdirs();
	        }
	       String  fileName="//PCOB-"+region+".txt";
	          	 fw = new FileWriter(new File(filePath + fileName));
	       		for(int i=0;i<years.length;i++){
				st.executeUpdate(queryforProcess);
				//log.info("in loop "+years[i] );
				this.updatePFCardReport("NO-SELECT", "NO-SELECT",years[i], "true","xyz","", pfid, fw,"false",false,"");
			//int j=fs.updateOBCardReport("NO-SELECT",region,years[i], "true", "","", pfid, "true");
			
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);

		}
	}

	public ArrayList employeePFCardReport(String region, String selectedYear,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno) {
		log.info("FinancialReportDAO::employeePFCardReport");
		String fromYear = "", toYear = "", dateOfRetriment = "";
		if (!selectedYear.equals("Select One")) {
			fromYear = "01-Apr-" + selectedYear;
			int toSelectYear = 0;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Mar-2009";
		}

		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeCardReportInfo cardInfo = null;
		ArrayList list = new ArrayList();
		ArrayList ptwList = new ArrayList();
		ArrayList finalSttmentList = new ArrayList();
		String appEmpNameQry = "";
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				appEmpNameQry = " LOWER(EMPLOYEENAME) LIKE'"
						+ empName.toLowerCase().trim() + "%' AND PENSIONNO='"
						+ pensionno + "' ";
			} else if (!empName.equals("") && pensionno.equals("")) {
				appEmpNameQry = " LOWER(EMPLOYEENAME) LIKE'"
						+ empName.toLowerCase().trim() + "%' ";
			} else {
				appEmpNameQry = "";
			}
		} else {
			appEmpNameQry = "";
		}
		// this.updateOBPensionCard(fromYear,toYear,"CHQIAD","");
		empDataList = this.getEmployeePFInfo(region, appEmpNameQry,
				sortedColumn);
		ArrayList cardList = new ArrayList();
		for (int i = 0; i < empDataList.size(); i++) {
			cardInfo = new EmployeeCardReportInfo();
			personalInfo = (EmployeePersonalInfo) empDataList.get(i);
			try {
				dateOfRetriment = this.getRetriedDate(personalInfo
						.getDateOfBirth());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
			list = this.getEmployeePensionCard(fromYear, toYear, personalInfo
					.getPfIDString(), personalInfo.getWetherOption(),
					personalInfo.getRegion(), true, dateOfRetriment,
					personalInfo.getDateOfBirth(), personalInfo
							.getOldPensionNo());

			ptwList = this.getPTWDetails(fromYear, toYear, personalInfo
					.getCpfAccno(), "IAD");

			finalSttmentList = this.getFinalSettlement(fromYear, toYear,
					personalInfo.getPfIDString(), personalInfo
							.getOldPensionNo());
			cardInfo.setFinalSettmentList(finalSttmentList);
			cardInfo.setPersonalInfo(personalInfo);
			cardInfo.setPensionCardList(list);
			cardInfo.setPtwList(ptwList);
			cardList.add(cardInfo);
		}

		return cardList;
	}

	public ArrayList employeePFCardEdit(String region, String selectedYear,
			String pensionno) {
		log.info("FinancialReportDAO::employeePFCardReport");
		String fromYear = "", toYear = "", dateOfRetriment = "";
		if (!selectedYear.equals("Select One")) {
			fromYear = "01-Apr-" + selectedYear;
			int toSelectYear = 0;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Apr-2010";
		}

		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeCardReportInfo cardInfo = null;
		ArrayList list = new ArrayList();
		empDataList = this.getEmployeePFInfo(region, pensionno);
		ArrayList cardList = new ArrayList();
		for (int i = 0; i < empDataList.size(); i++) {
			cardInfo = new EmployeeCardReportInfo();
			personalInfo = (EmployeePersonalInfo) empDataList.get(i);
			try {
				dateOfRetriment = this.getRetriedDate(personalInfo
						.getDateOfBirth());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
			list = this.getEmployeeTempPensionCard(fromYear, toYear,
					personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(), true,
					dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo());
			cardInfo.setPersonalInfo(personalInfo);
			cardInfo.setPensionCardList(list);
			cardList.add(cardInfo);
		}

		return cardList;
	}

	public ArrayList getEmployeePFInfo(String region, String appendQuery,
			String sortedColumn) {
		CommonDAO commonDAO = new CommonDAO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if (!appendQuery.equals("")) {
				sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB FROM EMPLOYEE_PERSONAL_INFO WHERE "
						+ appendQuery + " ORDER BY " + sortedColumn;
			} else {
				sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB FROM EMPLOYEE_PERSONAL_INFO WHERE REGION='"
						+ region
						+ "' "
						+ appendQuery
						+ " ORDER BY "
						+ sortedColumn;

			}

			log.info("FinanceReportDAO::getEmployeePFInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
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
				log.info(data.getWhetherOptionDescr());
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());

				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;
	}

	public ArrayList getEmployeePFInfo(String region, String pfid) {
		CommonDAO commonDAO = new CommonDAO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if (!pfid.equals("")) {
				sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB FROM EMPLOYEE_PERSONAL_INFO WHERE  pensionno='"
						+ pfid + "'";
			}

			log.info("FinanceReportDAO::getEmployeePFInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
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
				log.info(data.getWhetherOptionDescr());
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());

				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;
	}

	private String getEmployeeMappingPFInfo(Connection con,
			String personalPFID, String personalCPFACCNO, String personalRegion) {

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfID = "", region = "", regionPFIDS = "";
		try {

			st = con.createStatement();
			sqlQuery = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"
					+ personalPFID + "'";
			log.info("FinanceReportDAO::getEmployeeMappingPFInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("CPFACNO") != null) {
					pfID = rs.getString("CPFACNO");

				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");

				}
				if (regionPFIDS.equals("")) {
					regionPFIDS = pfID + "," + region + "=";
				} else {
					regionPFIDS = regionPFIDS + pfID + "," + region + "=";
				}

			}
			if(regionPFIDS.equals("")){
				regionPFIDS="---";
			}
			/*regionPFIDS = regionPFIDS + personalCPFACCNO + "," + personalRegion
					+ "=";*/
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return regionPFIDS;
	}

	private String preparedCPFString(String[] cpfaccnoList) {
		String cpfstring = "", cpfacno = "", regions = "";
		String getCpfalist = "";
		for (int i = 0; i < cpfaccnoList.length; i++) {
			getCpfalist = cpfaccnoList[i];
			String[] loadCPFRegionlist = getCpfalist.split(",");
			cpfacno = loadCPFRegionlist[0];
			regions = loadCPFRegionlist[1];
			if (cpfstring.equals("")) {
				cpfstring = " (CPFACCNO='" + cpfacno + "' AND REGION='"
						+ regions + "') ";
			} else {
				cpfstring = cpfstring + " OR " + " (CPFACCNO='" + cpfacno
						+ "' AND REGION='" + regions + "') ";
			}
		}
		return cpfstring;
	}
	private String preparedCPFString1(String[] cpfaccnoList) {
		String cpfstring = "", cpfacno = "", regions = "";
		String getCpfalist = "";
		for (int i = 0; i < cpfaccnoList.length-1; i++) {
			getCpfalist = cpfaccnoList[i];
			String[] loadCPFRegionlist = getCpfalist.split(",");
			cpfacno = loadCPFRegionlist[0];
			regions = loadCPFRegionlist[1];
			if (cpfstring.equals("")) {
				cpfstring = " (CPFACCNO='" + cpfacno + "' AND REGION='"
						+ regions + "') ";
			} else {
				cpfstring = cpfstring + " OR " + " (CPFACCNO='" + cpfacno
						+ "' AND REGION='" + regions + "') ";
			}
		}
		return cpfstring;
	}

	private int getEmployeeLoans(Connection con, String monthyear,
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

		//log.info("FinanceReportDAO::getEmployeeLoans" + sqlQuery);
		try {
			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("SUAMNT") != null) {
					nrfSubAmount = nrfSubAmount + rs.getInt("SUAMNT");
				}
				/*log.info("In While rfSubamount" + rfSubamount + "nrfSubAmount"
						+ nrfSubAmount);*/
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

	public ArrayList getPTWInfo(String fromYear, String toYear, String pfIDS) {
		String sqlQuery = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PTWBean ptwBean = null;
		ArrayList ptwList = new ArrayList();
		sqlQuery = "SELECT ADVTRANSDATE,ADVPURPOSE,ROUND(AMOUNT)AS AMOUNT FROM EMPLOYEE_PENSION_ADVANCES WHERE ("
				+ pfIDS
				+ ") AND to_char(ADVTRANSDATE,'dd-Mon-yyyy')>='"
				+ fromYear
				+ "' AND to_char(ADVTRANSDATE,'dd-Mon-yyyy')<='"
				+ toYear + "' ";

		log.info("FinanceReportDAO::getPTWInfo" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				ptwBean = new PTWBean();
				log.info("Purpose===" + rs.getString("ADVPURPOSE"));
				if (rs.getDate("ADVTRANSDATE") != null) {
					ptwBean.setPtwDate(commonUtil.getDatetoString(rs
							.getDate("ADVTRANSDATE"), "dd-MMM-yyyy"));
				} else {
					ptwBean.setPtwDate("---");
				}
				if (rs.getString("ADVPURPOSE") != null) {
					ptwBean.setPtwPurpose(rs.getString("ADVPURPOSE"));
				} else {
					ptwBean.setPtwPurpose("---");
				}
				if (rs.getString("AMOUNT") != null) {
					ptwBean.setPtwAmount(rs.getString("AMOUNT"));
				} else {
					ptwBean.setPtwAmount("---");
				}
				ptwList.add(ptwBean);
			}

			log.info("FinanceReportDAO::getPTWInfo" + ptwList.size());
		} catch (SQLException e) {
			e.printStackTrace();
			log.printStackTrace(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return ptwList;
	}

	public ArrayList getPTWDetails(String fromYear, String toYear,
			String pfIDS, String flag) {
		log.info("Purpose===getPTWDetails");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";

		PTWBean ptwBean = null;
		ArrayList ptwList = new ArrayList();
		/*
		 * if (flag.equals("NAD")) { sqlQuery =
		 * "SELECT CPFACCNO,ADVTRANSDATE,ADVPURPOSE,ROUND(AMOUNT)AS AMOUNT FROM EMPLOYEE_PENSION_ADVANCES WHERE (TO_DATE(to_char(ADVTRANSDATE,'dd-Mon-yyyy'))>='"
		 * + fromYear +
		 * "' AND TO_DATE(to_char(ADVTRANSDATE,'dd-Mon-yyyy'))<=LAST_DAY('" +
		 * toYear + "')) AND CPFACCNO='" + pfIDS + "' ORDER BY CPFACCNO"; } else
		 * if (flag.equals("IAD")) {
		 */
		sqlQuery = "SELECT CPFACCNO,LOANDATE AS ADVTRANSDATE,LOANPURPOSE AS ADVPURPOSE,ROUND(SUB_AMT+CONT_AMT)AS AMOUNT FROM EMPLOYEE_PENSION_LOANS WHERE (TO_DATE(to_char(LOANDATE,'dd-Mon-yyyy'))>='"
				+ fromYear
				+ "' AND TO_DATE(to_char(LOANDATE,'dd-Mon-yyyy'))<=LAST_DAY('"
				+ toYear + "')) AND CPFACCNO='" + pfIDS + "' ORDER BY CPFACCNO";
		/* } */

		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				ptwBean = new PTWBean();
				log
						.info("Purpose===getPTWDetails"
								+ rs.getString("ADVPURPOSE"));
				if (rs.getDate("ADVTRANSDATE") != null) {
					ptwBean.setPtwDate(commonUtil.getDatetoString(rs
							.getDate("ADVTRANSDATE"), "dd-MMM-yyyy"));
				} else {
					ptwBean.setPtwDate("---");
				}
				if (rs.getString("ADVPURPOSE") != null) {
					ptwBean.setPtwPurpose(rs.getString("ADVPURPOSE"));
				} else {
					ptwBean.setPtwPurpose("---");
				}
				if (rs.getString("AMOUNT") != null) {
					ptwBean.setPtwAmount(rs.getString("AMOUNT"));
				} else {
					ptwBean.setPtwAmount("---");
				}

				ptwBean.setRemarks("---");

				ptwList.add(ptwBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return ptwList;
	}

	private long getEmployeeAdvances(Connection con, String monthyear,
			String pfIDS, String flag, String pensionNo) {
		String sqlQuery = "";
		Statement st = null;
		ResultSet rs = null;
		String status = "";
		long advance = 0, amount = 0, partAmount = 0;
		if (!pfIDS.equals("")) {
			if (flag.equals("ADV.PAID")) {
				sqlQuery = "SELECT SUM(NVL(PARTAMT,'0')) AS PARTAMT,SUM(NVL(AMOUNT,'0')) AS ADVANCE,'WITHOUTPARTAAI' as status FROM EMPLOYEE_PENSION_ADVANCES WHERE ("
						+ pfIDS
						+ ") AND to_char(ADVTRANSDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "'";
			} else {
				sqlQuery = "SELECT AAIPARTAMT AS ADVANCE FROM EMPLOYEE_PENSION_ADVANCES WHERE ("
						+ pfIDS
						+ ") AND to_char(ADVTRANSDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "'";
			}
		} else {
			if (flag.equals("ADV.PAID")) {
				sqlQuery = "SELECT SUM(NVL(PARTAMT,'0')) AS PARTAMT,SUM(NVL(AMOUNT,'0')) AS ADVANCE,'WITHOUTPARTAAI' as status FROM EMPLOYEE_PENSION_ADVANCES WHERE PENSIONNO="
						+ pensionNo
						+ " AND to_char(ADVTRANSDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "'";
			} else {
				sqlQuery = "SELECT AAIPARTAMT AS ADVANCE FROM EMPLOYEE_PENSION_ADVANCES WHERE PENSIONNO="
						+ pensionNo
						+ " AND to_char(ADVTRANSDATE,'dd-Mon-yy') like'%-"
						+ monthyear + "'";
			}
		}

		//log.info("FinanceReportDAO::getEmployeeAdvances" + sqlQuery);
		try {
			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("status") != null) {
					status = rs.getString("status");
				}
				if (status.equals("WITHOUTPARTAAI")) {
					partAmount = rs.getLong("PARTAMT");
					amount = rs.getLong("ADVANCE");
					advance = partAmount + amount;

				} else {
					advance = rs.getInt("ADVANCE");
				}

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return advance;
	}

	private ArrayList getForm8EmployeeInfo(String sortedColumn, String region) {
		log.info("FinanceReportDAO::getForm8EmployeeInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", appendRegionTag = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}
		if (region.equals("NO-SELECT")) {
			appendRegionTag = "";
		} else {
			appendRegionTag = "WHERE REGION='" + region + "' ";
		}
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB FROM EMPLOYEE_PERSONAL_INFO "
					+ appendRegionTag + " ORDER BY " + sortedColumn;
			log.info("FinanceReportDAO::getEmployeePFInfo Query" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();
				CommonDAO commonDAO = new CommonDAO();
				data = commonDAO.loadPersonalInfo(rs);
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());
				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				if (data.getWetherOption().trim().equals("---")) {
					data.setRemarks("Wether Option is not available");
				}
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;

	}

	private String getEmployeePensionInfo(String fromDate, String toDate,
			String pfIDS, String wetherOption, String region,
			String dateOfRetriment, String dateOfBirth, String pension) {
		// log.info("FinanceReportDAO::getEmployeePensionInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String emoluments = "", pfStatury = "", vpf = "", cpf = "", monthYear = "", days = "";
		;
		String checkDate = "", chkMnthYear = "Apr-1995";
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		double pensionVal = 0.0, retriredEmoluments = 0.0, totalEmoluments = 0.0, totalPFStatury = 0.0, totalVPF = 0.0, totalCPF = 0.0, totalContribution = 0.0;
		String sqlQuery = "SELECT CPFACCNO,MONTHYEAR,ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,ROUND(REFADV) AS REFADV, AIRPORTCODE,EMOLUMENTMONTHS FROM EMPLOYEE_PENSION_VALIDATE WHERE EMPFLAG='Y' AND (to_date(to_char(monthyear,'dd-Mon-yyyy'))>='"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=LAST_DAY('"
				+ toDate
				+ "'))"
				+ " AND ("
				+ pfIDS
				+ ") ORDER BY TO_DATE(monthyear)";
		log.info("FinanceReportDAO::getEmployeePensionInfo" + sqlQuery);
		StringBuffer buffer = new StringBuffer();
		String calEmoluments = "", frmCpfaccno = "",emolumentsMonths="";
		double cellingRate = 0.0;
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				flag = false;
				contrFlag = false;
				chkDOBFlag = false;
				if (rs.getString("MONTHYEAR") != null) {
					monthYear = commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy");

				}
				if (rs.getString("CPFACCNO") != null) {
					frmCpfaccno = rs.getString("CPFACCNO");

				}
				if (rs.getString("EMOLUMENTMONTHS") != null) {
					emolumentsMonths=rs.getString("EMOLUMENTMONTHS".trim());
				} else {
					emolumentsMonths="1";
				}
				if (rs.getString("EMOLUMENTS") != null) {
					emoluments = rs.getString("EMOLUMENTS");
				} else {
					emoluments = "0.00";
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(monthYear,
							"dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				calEmoluments = this.calWages(emoluments, monthYear,
						wetherOption, false, false,"1");

				if (rs.getString("EMPPFSTATUARY") != null) {
					pfStatury = rs.getString("EMPPFSTATUARY");
				} else {
					pfStatury = "0.00";
				}
				if (rs.getString("EMPVPF") != null) {
					vpf = rs.getString("EMPVPF");
				} else {
					vpf = "0";
				}
				if (rs.getString("CPF") != null) {
					cpf = rs.getString("CPF");
				} else {
					cpf = "0";
				}

				if (flag == false) {
					if (!monthYear.equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(monthYear);
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == 0 || empRetriedDt == 0) {
							contrFlag = false;
						} else if (transMntYear == empRetriedDt
								&& transMntYear != 0 && empRetriedDt != 0) {
							chkDOBFlag = true;
						}
					}
					// log.info("transMntYear"+transMntYear+"empRetriedDt"+
					// empRetriedDt);
					// log.info("contrFlag"+contrFlag+"chkDOBFlag"+chkDOBFlag);
					if (contrFlag != true) {
						pensionVal = this.pensionFormsCalculation(monthYear,
								calEmoluments, wetherOption.trim(), region,
								false, false,emolumentsMonths);

						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionVal = pensionVal
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth;
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}

					} else {
						pensionVal = 0;
						calEmoluments = "0";
					}

				} else {
					pensionVal = 0;
				}

				emoluments = calEmoluments;
				totalEmoluments = totalEmoluments
						+ Double.parseDouble(emoluments);
				totalPFStatury = totalPFStatury + Double.parseDouble(pfStatury);
				totalVPF = totalVPF + Double.parseDouble(vpf);
				totalCPF = totalCPF + Double.parseDouble(cpf);
				totalContribution = totalContribution + pensionVal;
				log.info("monthYear===" + monthYear + "==frmCpfaccno==="
						+ commonUtil.leadingZeros(20, frmCpfaccno)
						+ "emoluments===="
						+ commonUtil.leadingZeros(20, emoluments)
						+ "pensionVal====" + pensionVal);
			}

			if (totalEmoluments != 0 && totalPFStatury != 0
					&& totalContribution != 0) {
				buffer.append(Math.round(totalEmoluments));
				buffer.append(",");
				buffer.append(Math.round(totalPFStatury));
				buffer.append(",");
				buffer.append(Math.round(totalVPF));
				buffer.append(",");
				buffer.append(Math.round(totalCPF));
				buffer.append(",");
				buffer.append(totalContribution);
			} else {
				buffer.append("NO-RECORDS");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return buffer.toString();
	}

	public double getRateOfInterest(String transMnthYear) {
		long frstBeginDate = 0, frstEndDate = 0, sndBeginDate = 0, sndEndDate = 0;
		long thrdBeginDate = 0, thrdEndDate = 0, frthBeginDate = 0, frthEndDate = 0, trnsMnthYear = 0;
		String day = "", chkTransMnthYear = "";
		double cellingRate = 0.0;
		day = transMnthYear.substring(0, 2);
		if (Integer.parseInt(day) >= 20 && Integer.parseInt(day) <= 31) {
			chkTransMnthYear = transMnthYear;
			transMnthYear = "";
			try {
				transMnthYear = "01-"
						+ commonUtil.converDBToAppFormat(chkTransMnthYear,
								"dd-MMM-yyyy", "MMM-yyyy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		frstBeginDate = Date.parse("01-Apr-1995");
		frstEndDate = Date.parse("01-Mar-2000");

		sndBeginDate = Date.parse("01-Apr-2000");
		sndEndDate = Date.parse("01-Mar-2001");

		thrdBeginDate = Date.parse("01-Apr-2001");
		thrdEndDate = Date.parse("01-Mar-2005");

		frthBeginDate = Date.parse("01-Mar-2005");
		frthEndDate = Date.parse("01-Apr-2009");
		trnsMnthYear = Date.parse(transMnthYear);
		if (trnsMnthYear >= frstBeginDate && trnsMnthYear <= frstEndDate) {
			cellingRate = 0.12;
		} else if (trnsMnthYear >= sndBeginDate && trnsMnthYear <= sndEndDate) {
			cellingRate = 0.11;
		} else if (trnsMnthYear >= thrdBeginDate && trnsMnthYear <= thrdEndDate) {
			cellingRate = 0.095;
		} else if (trnsMnthYear >= frthBeginDate && trnsMnthYear <= frthEndDate) {
			cellingRate = 0.085;
		}

		return cellingRate;
	}

	/*public String calWages(String emoluments, String monthYear,
			String wethereOption, boolean formFlag, boolean secondFormFlag,String emolumentMonths) {
		String calEmoluments = "0", sndCalEmoluments = "", day = "", chkTransMnthYear = "", chkDecMnth = "01-dec-1995";
		long transMthYear = 0, transFrstDate = 0, transSndDate = 0, transEndDate = 0, transSendDate = 0;
		boolean signFlag = false;
		DecimalFormat df = new DecimalFormat("#########0");
		day = monthYear.substring(0, 2);
		if (Integer.parseInt(day) >= 20 && Integer.parseInt(day) <= 31) {
			chkTransMnthYear = monthYear;
			monthYear = "";
			try {
				monthYear = "01-"
						+ commonUtil.converDBToAppFormat(chkTransMnthYear,
								"dd-MMM-yyyy", "MMM-yyyy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!emoluments.equals("")) {
			if (emoluments.indexOf("-") != -1) {
				signFlag = true;
			} else {
				signFlag = false;
			}
		}
		// log.info(monthYear);
		transMthYear = Date.parse(monthYear);
		transFrstDate = Date.parse("01-Apr-1995");
		transEndDate = Date.parse("30-Nov-1995");
		transSendDate = Date.parse("01-Dec-1995");
		double cellingRate = 0.0;
		//log.info("calWages=======emoluments"+emoluments+"monthYear"+monthYear+
		// "wethereOption==="+wethereOption);
       
		// new code added (i.e emolumentMonths as per the issue raised by rahul as on 03-Aug-2010
		if(!emolumentMonths.trim().equals("0.5")&& !emolumentMonths.trim().equals("1")&& !emolumentMonths.trim().equals("2")){
			int getDaysBymonth = this.getNoOfDaysForsalRecovery(monthYear);
			emolumentMonths=String.valueOf(Double.parseDouble(emolumentMonths.trim())/getDaysBymonth);
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			log.info("emolumentMonths "+emolumentMonths +"getDaysBymonth "+getDaysBymonth);
			emolumentMonths=twoDForm.format(Double.parseDouble(emolumentMonths));
			}
		
		if (transMthYear >= transFrstDate && transMthYear <= transEndDate) {

			if (Double.parseDouble(emoluments) > 5000) {
				calEmoluments = "5000";
				calEmoluments=String.valueOf(Integer.parseInt(calEmoluments)*Integer.parseInt(emolumentMonths.trim()));
			} else {
				calEmoluments = emoluments;
			}
		} else if (monthYear.toLowerCase().equals(chkDecMnth.toLowerCase())) {
			double salary = 0, perDaySal = 0, firstHalfMnthAmt = 0, secHalfMnthAmt = 0;
			String firstCalEmoluments = "";
			if (wethereOption.trim().equals("B")
					|| wethereOption.toLowerCase().trim().equals(
							"NO".toLowerCase())) {
				if (Double.parseDouble(emoluments) >= 5000) {
					sndCalEmoluments = "5000";
				} else {
					sndCalEmoluments = emoluments;
				}
			} else {
				sndCalEmoluments = emoluments;
			}

			if (Double.parseDouble(emoluments) >= 5000) {
				firstCalEmoluments = "5000";
			} else {
				firstCalEmoluments = emoluments;
			}

			if (formFlag == true) {
				calEmoluments = df.format(Double
						.parseDouble(firstCalEmoluments));
			} else if (secondFormFlag == true) {
				calEmoluments = df.format(Double.parseDouble(sndCalEmoluments));
			} else {
				log.info("First Emoluments" + firstCalEmoluments
						+ "Second Emoluments=====" + sndCalEmoluments);
				calEmoluments = df.format(Double
						.parseDouble(firstCalEmoluments)
						+ Double.parseDouble(sndCalEmoluments));
			}
		} else if (transMthYear >= transSendDate) {
			if (wethereOption.toUpperCase().equals("B")
					|| wethereOption.toUpperCase().equals("NO")) {
				if (Double.parseDouble(emoluments) >= 6500 && signFlag == false) {
					calEmoluments = "6500";
					calEmoluments=String.valueOf(Integer.parseInt(calEmoluments)*Integer.parseInt(emolumentMonths.trim()));
				} else if (Double.parseDouble(emoluments) <= -6500
						&& signFlag == true) {
					calEmoluments = "-6500";
				} else if (Double.parseDouble(emoluments) < 6500
						&& Double.parseDouble(emoluments) >= 0
						&& signFlag == false) {
					calEmoluments = emoluments;
				} else if (Double.parseDouble(emoluments) < -6500
						&& Double.parseDouble(emoluments) >= 0
						&& signFlag == true) {
					calEmoluments = emoluments;
				}

			} else if (wethereOption.toUpperCase().equals("A")) {
				calEmoluments = emoluments;
			}
		}

		//log.info("calWages=======emoluments"+emoluments+"monthYear"+monthYear+
		// "cellingRate"+cellingRate);
		return calEmoluments;
	}*/
	
	public String calWages(String emoluments, String monthYear,
			String wethereOption, boolean formFlag, boolean secondFormFlag,String emolumentMonths) {
		String calEmoluments = "0", sndCalEmoluments = "", day = "", chkTransMnthYear = "", chkDecMnth = "01-dec-1995";
		long transMthYear = 0, transFrstDate = 0, transSndDate = 0,secEndDate=0, transEndDate = 0, transSendDate = 0,secPenBeginDate=0;
		boolean signFlag = false;
		DecimalFormat df = new DecimalFormat("#########0");
		day = monthYear.substring(0, 2);
		if (Integer.parseInt(day) >= 20 && Integer.parseInt(day) <= 31) {
			chkTransMnthYear = monthYear;
			monthYear = "";
			try {
				monthYear = "01-"
						+ commonUtil.converDBToAppFormat(chkTransMnthYear,
								"dd-MMM-yyyy", "MMM-yyyy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!emoluments.equals("")) {
			if (emoluments.indexOf("-") != -1) {
				signFlag = true;
			} else {
				signFlag = false;
			}
		}
		// log.info(monthYear);
		transMthYear = Date.parse(monthYear);
		transFrstDate = Date.parse("01-Apr-1995");
		transEndDate = Date.parse("30-Nov-1995");
		transSendDate = Date.parse("01-Dec-1995");
		secPenBeginDate = Date.parse("01-Jul-2001");
		secEndDate = Date.parse("31-Mar-2015");
		double cellingRate = 0.0;
		//log.info("calWages=======emoluments"+emoluments+"monthYear"+monthYear+
		// "wethereOption==="+wethereOption);
       
		// new code added (i.e emolumentMonths as per the issue raised by rahul as on 03-Aug-2010
		if(!emolumentMonths.trim().equals("0.5")&& !emolumentMonths.trim().equals("1")&& !emolumentMonths.trim().equals("2")){
			int getDaysBymonth = this.getNoOfDaysForsalRecovery(monthYear);
			emolumentMonths=String.valueOf(Double.parseDouble(emolumentMonths.trim())/getDaysBymonth);
			DecimalFormat twoDForm = new DecimalFormat("#.#####");
			log.info("emolumentMonths "+emolumentMonths +"getDaysBymonth "+getDaysBymonth);
			emolumentMonths=twoDForm.format(Double.parseDouble(emolumentMonths));
			}
		
		if (transMthYear >= transFrstDate && transMthYear <= transEndDate) {

			if (Double.parseDouble(emoluments) > 5000) {
				calEmoluments = "5000";
				calEmoluments=String.valueOf(Integer.parseInt(calEmoluments)* Double.parseDouble(emolumentMonths));
			} else {
				calEmoluments = emoluments;
			}
		} else if (monthYear.toLowerCase().equals(chkDecMnth.toLowerCase())) {
			double salary = 0, perDaySal = 0, firstHalfMnthAmt = 0, secHalfMnthAmt = 0;
			String firstCalEmoluments = "";
			if (wethereOption.trim().equals("B")
					|| wethereOption.toLowerCase().trim().equals(
							"NO".toLowerCase())) {
				if (Double.parseDouble(emoluments) >= 5000) {
					sndCalEmoluments = "5000";
				} else {
					sndCalEmoluments = emoluments;
				}
			} else {
				sndCalEmoluments = emoluments;
			}

			if (Double.parseDouble(emoluments) >= 5000) {
				firstCalEmoluments = "5000";
			} else {
				firstCalEmoluments = emoluments;
			}

			if (formFlag == true) {
				calEmoluments = df.format(Double
						.parseDouble(firstCalEmoluments));
			} else if (secondFormFlag == true) {
				calEmoluments = df.format(Double.parseDouble(sndCalEmoluments));
			} else {
				log.info("First Emoluments" + firstCalEmoluments
						+ "Second Emoluments=====" + sndCalEmoluments);
				calEmoluments = df.format(Double
						.parseDouble(firstCalEmoluments)
						+ Double.parseDouble(sndCalEmoluments));
			}
		} else if (transMthYear >= transSendDate && transMthYear <= secEndDate) {
			
			if (wethereOption.toUpperCase().equals("B")
					|| wethereOption.toUpperCase().equals("NO")) {
				if (transMthYear >= secPenBeginDate) {
					if (Double.parseDouble(emoluments) >= 6500 && signFlag == false) {
						calEmoluments = "6500";
						calEmoluments=String.valueOf(Integer.parseInt(calEmoluments)*Double.parseDouble(emolumentMonths.trim()));
					} else if (Double.parseDouble(emoluments) <= -6500
							&& signFlag == true) {
						calEmoluments = "-6500";
					} else if (Double.parseDouble(emoluments) < 6500
							&& Double.parseDouble(emoluments) >= 0
							&& signFlag == false) {
						calEmoluments = emoluments;
					} else if (Double.parseDouble(emoluments) < -6500
							&& Double.parseDouble(emoluments) >= 0
							&& signFlag == true) {
						calEmoluments = emoluments;
					}
				} else {
					if (Double.parseDouble(emoluments) < 5000) {
						calEmoluments=emoluments;
					} else if (Double.parseDouble(emoluments) >= 5000) {
						calEmoluments =  "5000";;
						calEmoluments =String.valueOf(Integer.parseInt(calEmoluments)* Double.parseDouble(emolumentMonths));

					}
		

				}	
			} else if (wethereOption.toUpperCase().equals("A")) {
				calEmoluments = emoluments;
			}
		}

		//log.info("calWages=======emoluments"+emoluments+"monthYear"+monthYear+
		// "cellingRate"+cellingRate);
		return calEmoluments;
	}

	public int getNoOfDays(String dateOfBirth) {
		String sqlQuery = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int days = 0;
	sqlQuery = "SELECT TO_CHAR(LAST_DAY(TO_DATE('" + dateOfBirth
				+ "') + INTERVAL '58' YEAR),'dd') as days FROM DUAL";

		 log.info("FinanceReportDAO::getNoOfDays" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				days = rs.getInt("days");
			}
			if(days==29){
				days=29;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return days;
	}
	public int getNoOfDaysForsalRecovery(String salMonth) {
		String sqlQuery = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int days = 0;

		sqlQuery = "SELECT to_char(add_months(LAST_DAY(TO_DATE('"+salMonth+"')),-1),'dd') as days FROM DUAL";

		 log.info("FinanceReportDAO::getNoOfDays" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				days = rs.getInt("days");
			}
			if(days==29){
				days=29;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return days;
	}

	public String getRetriedDate(String dateOfBirth)
			throws InvalidDataException {
		dateOfBirth = commonUtil.converDBToAppFormat(dateOfBirth,
				"dd-MMM-yyyy", "dd-MM-yyyy");
		String[] dobElements = dateOfBirth.split("-");
		int day = 0, month = 0, year = 0;
		String retriedDt = "", formatedRtDt = "";
		day = Integer.parseInt(dobElements[0]);
		month = Integer.parseInt(dobElements[1]);
		year = Integer.parseInt(dobElements[2]);
		if (day >= 1) {
			month = month + 1;
			day = 1;
		}
		year = year + 58;
		formatedRtDt = day + "-" + month + "-" + year;
		retriedDt = commonUtil.converDBToAppFormat(formatedRtDt, "dd-MM-yyyy",
				"dd-MMM-yyyy");
		return retriedDt;
	}

	public int getPFPensionCount(String empserialNumber, String sortedColumn) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", personalCPFACCNO = "", personalRegion = "", pfidWithRegion = "", pfidString = "";
		int totalCount = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT CPFACNO,REGION FROM EMPLOYEE_PERSONAL_INFO WHERE PENSIONNO='"
					+ empserialNumber + "'  ORDER BY " + sortedColumn;
			log.info("FinanceReportDAO::getPFPensionCount" + sqlQuery);
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("CPFACNO") != null) {
					personalCPFACCNO = rs.getString("CPFACNO");
				} else {
					personalCPFACCNO = "---";
				}
				if (rs.getString("REGION") != null) {
					personalRegion = rs.getString("REGION");
				} else {
					personalRegion = "---";
				}
			}
			if (!(personalRegion.equals("---") && personalCPFACCNO
					.equals("---"))) {
				pfidWithRegion = this.getEmployeeMappingPFInfo(con,
						empserialNumber, personalCPFACCNO, personalRegion);
				String[] pfIDLists = pfidWithRegion.split("=");
				pfidString = this.preparedCPFString(pfIDLists);
				totalCount = this.getEmployeePensionTotal(con, pfidString);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return totalCount;
	}

	private int getEmployeePensionTotal(Connection con, String pfIDS) {
		String sqlQuery = "";
		Statement st = null;
		ResultSet rs = null;

		int totalCount = 0;
		sqlQuery = "SELECT count(*) AS TOTAL FROM EMPLOYEE_PENSION_VALIDATE WHERE ("
				+ pfIDS + ")";
		log.info("FinanceReportDAO::getEmployeePensionTotal" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				totalCount = rs.getInt("TOTAL");
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return totalCount;
	}

	private String getEmployeeRateOfInterest(Connection con, String getTranDate) {
		String sqlQuery = "", interestRate = "", toDate = "", fromDate = "";

		int transAddYears = 0;
		// log.info("getEmployeeRateOfInterest" + getTranDate);
		if (Integer.parseInt(getTranDate) >= 1995
				&& Integer.parseInt(getTranDate) < 2000) {

			fromDate = "Apr-1995";
			toDate = "Mar-2000";
		} else if (Integer.parseInt(getTranDate) >= 2000
				&& Integer.parseInt(getTranDate) < 2001) {

			fromDate = "Apr-2000";
			toDate = "Mar-2001";
		} else if (Integer.parseInt(getTranDate) >= 2001
				&& Integer.parseInt(getTranDate) < 2005) {
			fromDate = "Apr-2001";
			toDate = "Mar-2005";
		} else if (Integer.parseInt(getTranDate) >= 2005
				&& Integer.parseInt(getTranDate) < 2009) {
			fromDate = "Apr-2005";
			toDate = "Mar-2009";
		} else if (Integer.parseInt(getTranDate) >= 2009
				&& Integer.parseInt(getTranDate) < 2010) {
			fromDate = "Apr-2009";
			toDate = "Mar-2010";
		} else {
			fromDate = "Apr-1995";
			toDate = "Mar-2009";
		}

		Statement st = null;
		ResultSet rs = null;

		ArrayList list = new ArrayList();

		sqlQuery = "SELECT CEILINGRATE,TO_CHAR(WITHEFFCTDATE,'Mon-yyyy') AS FROMYEAR,TO_CHAR(WITHEFFCTTODATE,'Mon-yyyy') AS TOYEAR FROM EMPLOYEE_CEILING_MASTER WHERE TO_CHAR(WITHEFFCTDATE,'Mon-yyyy')>='"
				+ fromDate
				+ "' AND TO_CHAR(WITHEFFCTTODATE,'Mon-yyyy')<='"
				+ toDate + "'";
		// log.info("FinanceReportDAO::getEmployeeRateOfInterest"+sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("CEILINGRATE") != null) {
					interestRate = rs.getString("CEILINGRATE");
				}

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return interestRate;
	}

	public ArrayList pensionContributionReportAll(String fromDate,
			String toDate, String region, String airportcode,
			String empserialNO, String cpfAccno, String transferFlag,
			String pfIDStrip,String bulkPrint) {
		ArrayList penContHeaderList = new ArrayList();

		if (pfIDStrip.equals("NO-SELECT")) {
			penContHeaderList = this
			.pensionContrPFIDHeaderInfoWthNav(region, airportcode,
					empserialNO, cpfAccno, transferFlag, pfIDStrip);
		}else if(bulkPrint.equals("true")){
			penContHeaderList =this.pensionContrBulkPrintPFIDS(region, airportcode,
					empserialNO, cpfAccno, transferFlag, pfIDStrip);
		} else {
			penContHeaderList = this
			.pensionContrPFIDHeaderInfo(region, airportcode,
					empserialNO, cpfAccno, transferFlag, pfIDStrip);
		}

		String cpfacno = "", empRegion = "", empSerialNumber = "", tempPensionInfo = "";
		String[] cpfaccnos = new String[10];
		String[] dupCpfaccnos = new String[10];
		String[] regions = new String[10];
		String[] empPensionList = null;
		String[] pensionInfo = null;
		CommonDAO commonDAO = new CommonDAO();
		String pensionList = "", tempCPFAcno = "", tempRegion = "", dateOfRetriment = "";
		ArrayList penConReportList = new ArrayList();
		log.info("Header Size" + penContHeaderList.size());
		String dupCpf = "", dupRegion = "", countFlag = "";
		int yearCount = 0;
		Connection con = null;
		try {
			con = commonDB.getConnection();
			for (int i = 0; i < penContHeaderList.size(); i++) {
				PensionContBean penContHeaderBean = new PensionContBean();
				PensionContBean penContBean = new PensionContBean();

				penContHeaderBean = (PensionContBean) penContHeaderList.get(i);
				penContBean.setCpfacno(commonUtil
						.duplicateWords(penContHeaderBean.getCpfacno()));

				penContBean.setEmployeeNM(penContHeaderBean.getEmployeeNM());
				penContBean.setEmpDOB(penContHeaderBean.getEmpDOB());
				penContBean.setEmpSerialNo(penContHeaderBean.getEmpSerialNo());

				penContBean.setEmpDOJ(penContHeaderBean.getEmpDOJ());
				penContBean.setGender(penContHeaderBean.getGender());
				penContBean.setFhName(penContHeaderBean.getFhName());
				penContBean.setEmployeeNO(penContHeaderBean.getEmployeeNO());
				penContBean.setDesignation(penContHeaderBean.getDesignation());
				penContBean.setWhetherOption(penContHeaderBean
						.getWhetherOption());
				penContBean.setDateOfEntitle(penContHeaderBean
						.getDateOfEntitle());
				penContBean.setMaritalStatus(penContHeaderBean
						.getMaritalStatus());
				penContBean.setDepartment(penContHeaderBean.getDepartment());

				penContBean.setPensionNo(commonDAO.getPFID(penContBean
						.getEmployeeNM(), penContBean.getEmpDOB(), commonUtil
						.leadingZeros(5, penContHeaderBean.getEmpSerialNo())));
				log.info("penContBean " + penContBean.getPensionNo());
				empSerialNumber = penContHeaderBean.getEmpSerialNo();

				double totalAAICont = 0.0, calCPF = 0.0, calPens = 0.0;
				ArrayList employeFinanceList = new ArrayList();
				String preparedString = penContHeaderBean.getPrepareString();

				try {
					dateOfRetriment = this.getRetriedDate(penContBean
							.getEmpDOB());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList rateList = new ArrayList();
				String findFromDate = "";
				findFromDate = this.compareTwoDates(penContHeaderBean
						.getDateOfEntitle(), fromDate);
				log.info("Find From Date" + findFromDate);
				pensionList = this.getEmployeePensionInfo(preparedString,
						findFromDate, toDate, penContHeaderBean
								.getWhetherOption(), dateOfRetriment,
						penContBean.getEmpDOB(),penContHeaderBean.getEmpSerialNo());
				String rateFromYear = "", rateToYear = "", checkMnthDate = "", rateOfInterest = "";
				boolean rateFlag = false;
				if (!pensionList.equals("")) {
					empPensionList = pensionList.split("=");
					if (empPensionList != null) {
						for (int r = 0; r < empPensionList.length; r++) {
							TempPensionTransBean bean = new TempPensionTransBean();
							tempPensionInfo = empPensionList[r];
							pensionInfo = tempPensionInfo.split(",");
							bean.setMonthyear(pensionInfo[0]);
							try {
								checkMnthDate = commonUtil.converDBToAppFormat(
										pensionInfo[0], "dd-MMM-yyyy", "MMM")
										.toLowerCase();
								if (r == 0 && !checkMnthDate.equals("apr")) {
									checkMnthDate = "apr";

								}
								if (checkMnthDate.equals("apr")) {
									rateOfInterest = this
											.getEmployeeRateOfInterest(
													con,
													commonUtil
															.converDBToAppFormat(
																	pensionInfo[0],
																	"dd-MMM-yyyy",
																	"yyyy"));
									if (rateOfInterest.equals("")) {
										rateOfInterest = "12";
									}
								}
							} catch (InvalidDataException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							bean.setInterestRate(rateOfInterest);
							//log.info("Monthyear"+checkMnthDate+"Interest Rate"
							// +rateOfInterest);
							checkMnthDate = "";
							bean.setEmoluments(pensionInfo[1]);
							bean.setCpf(pensionInfo[2]);
							bean.setEmpVPF(pensionInfo[3]);
							bean.setEmpAdvRec(pensionInfo[4]);
							bean.setEmpInrstRec(pensionInfo[5]);
							bean.setStation(pensionInfo[6]);
							bean.setPensionContr(pensionInfo[7]);
							// calCPF=Double.parseDouble(bean.getCpf());
							// calPens=Double.parseDouble(pensionInfo[7]);
							calCPF = Math.round(Double.parseDouble(bean
									.getCpf()));
							calPens = Math.round(Double
									.parseDouble(pensionInfo[7]));
							DateFormat df = new SimpleDateFormat("dd-MMM-yy");
							Date transdate = df.parse(pensionInfo[0]);
							bean.setDeputationFlag(pensionInfo[19].trim());
							if (transdate.before(new Date("31-Mar-08"))&& (bean.getDeputationFlag().equals("N")||bean.getDeputationFlag().equals(""))) {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[7]));
								totalAAICont = calCPF - calPens;
							} else {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[12]));
								bean.setPensionContr(pensionInfo[12]);
								totalAAICont = calCPF - calPens;
							}
							bean.setAaiPFCont(Double.toString(totalAAICont));
							bean.setGenMonthYear(pensionInfo[8]);
							bean.setTransCpfaccno(pensionInfo[9]);
							bean.setRegion(pensionInfo[10]);
							bean.setRecordCount(pensionInfo[11]);
							bean.setDbPensionCtr(pensionInfo[12]);
							bean.setForm7Narration(pensionInfo[14]);
							bean.setPcHeldAmt(pensionInfo[15]);
							bean.setPccalApplied(pensionInfo[17].trim());
							log.info("PcApplied " +bean.getPccalApplied());
							if(bean.getPccalApplied().equals("N")){
								bean.setCpf("0.00");
								bean.setAaiPFCont("0.00");	
								bean.setPensionContr("0.00");
								bean.setDbPensionCtr("0.00");
							}
							if (bean.getRecordCount().equals("Duplicate")) {
								countFlag = "true";
							}
							bean.setRemarks("---");

							employeFinanceList.add(bean);
						}

					}
				}
				if (!pensionList.equals("")) {
					penContBean.setBlockList(this.getMonthList(con,
							pensionInfo[20]));
				
				employeFinanceList = this
						.chkDuplicateMntsForCpfs(employeFinanceList);
				penContBean.setEmpPensionList(employeFinanceList);
				penContBean.setCountFlag(countFlag);
				
				penConReportList.add(penContBean);
				}
			}
		
		} catch (Exception ex) {
			log.printStackTrace(ex);
		} finally {
			commonDB.closeConnection(con, null, null);
		}

		return penConReportList;
	}

	private ArrayList pensionContrPFIDHeaderInfoWthNav(String region,
			String airportCD, String empserialNO, String cpfAccno,
			String transferFlag, String pfidString) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sqlQuery = "", prCPFString = "";
		// log.info("region in reportDao********  "+region);
		if (!transferFlag.equals("")) {
			sqlQuery = this.buildPenContRptQuery(region, airportCD,
					empserialNO, cpfAccno, transferFlag);
		} else {
			sqlQuery = this.buildPenContRptWthOutTransferQuery(region,
					airportCD, empserialNO, cpfAccno);
		}

		PersonalDAO personalDAO = new PersonalDAO();
		PensionContBean data = null;
		CommonDAO commonDao = new CommonDAO();
		log.info("pensionContrHeaderInfo===Query" + sqlQuery);
		ArrayList empinfo = new ArrayList();
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			while (rs.next()) {
				data = new PensionContBean();
				data = loadEmployeeInfo(rs);
				prCPFString = commonDao.getCPFACCNOByPension(data
						.getEmpSerialNo(), data.getCpfacno(), data
						.getEmpRegion());
				String[] cpfaccnoList = prCPFString.split("=");
				String cpfString = personalDAO.preparedCPFString(cpfaccnoList);
				log.info(data.getEmpSerialNo() + "-" + cpfString);
				data.setPrepareString(cpfString);
				empinfo.add(data);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		return empinfo;
	}

	private ArrayList pensionContrPFIDHeaderInfo(String region,
			String airportCD, String empserialNO, String cpfAccno,
			String transferFlag, String pfidString) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		log.info("empserialNO "+empserialNO);
		String sqlQuery = "", prCPFString = "";
		// log.info("pensionContrPFIDHeaderInfo::region"+region);
		int startIndex = 0, endIndex = 0, countGridLength = 0;
		int totalSize = 0;
		ResourceBundle bundle = ResourceBundle
				.getBundle("com.epis.resource.ApplicationResources");
		if (bundle.getString("common.pension.pagesize") != null) {
			totalSize = Integer.parseInt(bundle
					.getString("common.pension.pagesize"));
		} else {
			totalSize = 100;
		}
		if (!pfidString.equals("")) {
			if (!pfidString.equals("1 - 1")) {
				String[] pfidList = pfidString.split(" - ");

				startIndex = Integer.parseInt(pfidList[0]);
				if (!pfidList[1].trim().equals("END")) {
					endIndex = Integer.parseInt(pfidList[1]);
				} else {
					endIndex = totalSize;
				}
			} else {
				pfidString = "";
			}

		}

		sqlQuery = this.buildPCReportQuery(region, airportCD, empserialNO,
				cpfAccno, transferFlag, startIndex, endIndex, pfidString);
		PersonalDAO personalDAO = new PersonalDAO();
		PensionContBean data = null;
		CommonDAO commonDao = new CommonDAO();
		log.info("pensionContrHeaderInfo===Query" + sqlQuery);
		ArrayList empinfo = new ArrayList();
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			// log.info("startIndex"+startIndex+"endIndex"+endIndex);
			while (rs.next()) {
				data = new PensionContBean();
				/*
				 * log.info("loadPersonalInfo=======WHILE=======" +
				 * rs.getString("cpfacno") + "countGridLength" +
				 * countGridLength);
				 */
				data = loadEmployeeInfo(rs);
				prCPFString = commonDao.getCPFACCNOByPension(data
						.getEmpSerialNo(), data.getCpfacno(), data
						.getEmpRegion());
				String cpfString="";
				if(!("---".equals(prCPFString))){
				String[] cpfaccnoList = prCPFString.split("=");
				
				log.info("======================================================"+prCPFString);
			
					 cpfString = personalDAO.preparedCPFString(cpfaccnoList);
						log.info(data.getEmpSerialNo() + "" + cpfString);
						data.setPrepareString(cpfString);
						empinfo.add(data);
				}
				
			

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		return empinfo;
	}

	public String buildPCReportQuery(String region, String airportCD,
			String empserialNO, String cpfAccno, String transferFlag,
			int startPensionNo, int endPensionno, String pfdString) {
		log.info("FinancialReportDAO :buildPCReportQuery() entering method");
      log.info("endPensionno"+empserialNO);
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String dynamicQuery = "";
		log.info(" transferFlag "+transferFlag);
		if (!transferFlag.equals("")) {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DEPARTMENT AS DEPARTMENT,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO ";
		} else {
			sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DEPARTMENT AS DEPARTMENT,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL  ";
		}
      log.info(sqlQuery);
		if (!airportCD.equals("NO-SELECT")) {
			whereClause.append(" LOWER(EPI.AIRPORTCODE) ='"
					+ airportCD.trim().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!empserialNO.equals("")) {
			whereClause.append(" EPI.PENSIONNO ='" + empserialNO + "'");
			whereClause.append(" AND ");
		}
		if (!pfdString.equals("") && startPensionNo != 0) {
			whereClause.append(" EPI.PENSIONNO BETWEEN'" + startPensionNo
					+ "' AND '" + endPensionno + "'");
			whereClause.append(" AND ");
		}

		if (!cpfAccno.equals("")) {
			whereClause.append(" EPI.cpfAccno ='" + cpfAccno + "'");
			whereClause.append(" AND ");
		}
		if (!region.equals("NO-SELECT") && !region.equals("AllRegions")) {
			whereClause.append(" LOWER(EPI.region)='"
					+ region.trim().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!transferFlag.equals("")) {
			whereClause.append(" ETI.TRANSFERFLAG='" + transferFlag + "'");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if (region.equals("NO-SELECT") && (airportCD.equals("NO-SELECT"))
				&& (pfdString.equals("")) && (empserialNO.equals(""))
				&& (transferFlag.equals("")) && region.equals("AllRegions")) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "ORDER BY EPI.PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("FinancialReportDAO :buildPCReportQuery() leaving method");
		return dynamicQuery;
	}

	private PensionContBean loadEmployeeInfo(ResultSet rs) throws SQLException {
		PensionContBean contr = new PensionContBean();

		/*
		 * if(rs.getString("PENSIONNUMBER")!=null){
		 * contr.setPensionNo(rs.getString("PENSIONNUMBER")); }
		 */
		if (rs.getString("MARITALSTATUS") != null) {
			contr.setMaritalStatus(rs.getString("MARITALSTATUS").trim());
		} else {
			contr.setMaritalStatus("---");

		}
		if (rs.getString("DEPARTMENT") != null) {
			contr.setDepartment(rs.getString("DEPARTMENT").trim());
		} else {
			contr.setDepartment("---");

		}
		if (rs.getString("EMPSERIALNUMBER") != null) {
			contr.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
		}
		if (rs.getString("EMPLOYEENO") != null) {
			contr.setEmployeeNO(rs.getString("EMPLOYEENO"));
		} else {
			contr.setEmployeeNO("---");
		}
		if (rs.getString("SEX") != null) {
			contr.setGender(rs.getString("SEX"));
		} else {
			contr.setGender("---");
		}
		if (rs.getString("DESEGNATION") != null) {
			contr.setDesignation(rs.getString("DESEGNATION"));
		} else {
			contr.setDesignation("---");
		}
		if (rs.getString("FHNAME") != null) {
			contr.setFhName(rs.getString("FHNAME"));
		} else {
			contr.setFhName("---");
		}

		if (rs.getString("REGION") != null) {
			contr.setEmpRegion(rs.getString("REGION"));
		}
		if (rs.getString("EMPLOYEENAME") != null) {
			contr.setEmployeeNM(rs.getString("EMPLOYEENAME"));
		}
		if (rs.getString("CPFACNO") != null) {
			contr.setCpfacno(rs.getString("CPFACNO"));
		}else {
			contr.setCpfacno("---");
		}

		if (rs.getString("DATEOFJOINING") != null) {
			contr.setEmpDOJ(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFJOINING")));
		} else {
			contr.setEmpDOJ("---");
		}
		if (rs.getString("DATEOFBIRTH") != null) {
			contr.setEmpDOB(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFBIRTH")));
		} else {
			contr.setEmpDOB("---");
		}
		CommonDAO commonDAO = new CommonDAO();
		String pensionNumber = commonDAO.getPFID(contr.getEmployeeNM(), contr
				.getEmpDOB(), contr.getEmpSerialNo());
		contr.setPensionNo(pensionNumber);
		if (rs.getString("WETHEROPTION") != null) {
			contr.setWhetherOption(rs.getString("WETHEROPTION"));
		} else {
			contr.setWhetherOption("---");
		}
		long noOfYears = 0;
		noOfYears = rs.getLong("ENTITLEDIFF");

		if (noOfYears > 0) {
			contr.setDateOfEntitle(contr.getEmpDOJ());
		} else {
			contr.setDateOfEntitle("01-Apr-1995");
		}
		return contr;

	}

	public ArrayList rnfcForm8Report(String selectedYear, String month,
			String sortedColumn, String region, boolean formFlag,
			String airportCode, String pensionNo) {
		String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
		int toSelectYear = 0;
		ArrayList empList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		ArrayList form8List = new ArrayList();
		ArrayList getPensionList = new ArrayList();
		if (!month.equals("NO-SELECT")) {
			try {
				frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
			fromYear = "01-Apr-" + selectedYear;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else if (!selectedYear.equals("Select One")
				&& !month.equals("NO-SELECT")) {
			fromYear = "01-" + frmMonth + "-" + selectedYear;
			toYear = fromYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Mar-2008";
		}
		log.info(fromYear + "====" + toYear);
		empList = this.getForm8EmployeeInfo(sortedColumn, region, formFlag,
				fromYear, airportCode, pensionNo);
		String pensionInfo = "", regionInfo = "";

		for (int i = 0; i < empList.size(); i++) {
			personalInfo = (EmployeePersonalInfo) empList.get(i);

			if (!personalInfo.getDateOfBirth().equals("---")) {
				try {
					dateOfRetriment = this.getRetriedDate(personalInfo
							.getDateOfBirth());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (formFlag == true) {
				if (!personalInfo.getPfIDString().equals("")) {
					pensionInfo = this.getEmployeePensionInfo(fromYear, toYear,
							personalInfo.getPfIDString(), personalInfo
									.getWetherOption().trim(), personalInfo
									.getRegion(), dateOfRetriment, personalInfo
									.getDateOfBirth(), pensionNo);

					if (personalInfo.getRegion().equals("CHQIAD")) {
						regionInfo = "IAD-" + airportCode;
						personalInfo.setRegion(regionInfo);
						regionInfo = "";
					}
					if (!pensionInfo.equals("NO-RECORDS")) {
						personalInfo.setPensionInfo(pensionInfo);
					} else {
						personalInfo = null;
					}

				}

			} else {

				getPensionList = this.getEmployeePensionCard(fromYear, toYear,
						personalInfo.getPfIDString(), personalInfo
								.getWetherOption(), personalInfo.getRegion(),
						false, dateOfRetriment, personalInfo.getDateOfBirth(),
						personalInfo.getOldPensionNo());
				personalInfo.setPensionList(getPensionList);
			}
			dateOfRetriment = "";
			form8List.add(personalInfo);
		}
		return form8List;
	}

	private ArrayList getEmployeePensionCard(String fromDate, String toDate,
			String pfIDS, String wetherOption, String region, boolean formFlag,
			String dateOfRetriment, String dateOfBirth, String pensionNo) {

		DecimalFormat df = new DecimalFormat("#########0.00");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0;

		boolean obFlag = false;
		/*
		 * sqlQuery =
		 * "SELECT MONTHYEAR,ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,ROUND(REFADV) AS REFADV, AIRPORTCODE FROM EMPLOYEE_PENSION_VALIDATE WHERE (to_date(to_char(monthyear,'dd-Mon-yyyy'))>='"
		 * + fromDate + "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='" +
		 * toDate + "')" + " AND (" + pfIDS + ") ORDER BY TO_DATE(monthyear)";
		 */

		sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,emp.* from (select distinct to_char(to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
				+ "from employee_pension_validate where rownum <=to_date('"
				+ toDate
				+ "', 'dd-mon-yyyy') -to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
				+ toDate
				+ "'))"
				+ " AND ("
				+ pfIDS
				+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));
					} else {
						cardInfo.setMonthyear("---");
					}
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy",
								"MMM-yy"));
					}

				}
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}

				/*
				 * if (region.equals("CHQNAD")) {
				 * 
				 * if (rs.getString("CPFADVANCE") != null) {
				 * cardInfo.setPrincipal(rs.getString("CPFADVANCE")); } else {
				 * cardInfo.setPrincipal("0"); } } else
				 */if (region.equals("North Region")) {
					if (rs.getString("REFADV") != null) {
						cardInfo.setPrincipal(rs.getString("REFADV"));
					} else {
						cardInfo.setPrincipal("0");
					}
				} else {
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					/*
					 * if (region.equals("CHQIAD")) {
					 * 
					 * loanPFWPaid = this.getEmployeeLoans(con, cardInfo
					 * .getShnMnthYear(), pfIDS, "ADV.PAID"); advancePFWPaid =
					 * this.getEmployeeAdvances(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID");
					 * log.info("Region"+region+"loanPFWPaid"
					 * +loanPFWPaid+"advancePFWPaid"+advancePFWPaid);
					 * totalAdvancePFWPaid=loanPFWPaid+advancePFWPaid; } else if
					 * (region.equals("CHQNAD")) { loanPFWPaid =
					 * this.getEmployeeLoans(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID"); advancePFWPaid =
					 * this.getEmployeeAdvances(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID");
					 * totalAdvancePFWPaid=loanPFWPaid+advancePFWPaid; } else if
					 * (region.equals("North Region")) {
					 * if(rs.getString("DEDADV")!=null){ advancePFWPaid =
					 * Double.parseDouble(rs.getString("DEDADV"));
					 * totalAdvancePFWPaid=advancePFWPaid; }else{
					 * advancePFWPaid=0; totalAdvancePFWPaid=advancePFWPaid; }
					 * 
					 * }
					 */

					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("Region" + region + "loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				}

				/*
				 * log.info("cardInfo.getShnMnthYear()" +
				 * cardInfo.getShnMnthYear() + "advancePFWPaid" +
				 * advancePFWPaid);
				 */
				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				log.info("monthYear" + cardInfo.getMonthyear()
						+ "dateOfRetriment" + dateOfRetriment);
				if (flag == false) {
					if (!cardInfo.getMonthyear().equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(cardInfo.getMonthyear());
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == empRetriedDt) {
							chkDOBFlag = true;
						}
					}
					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					if (contrFlag != true) {
						pensionAsPerOption = this.pensionCalculation(cardInfo
								.getMonthyear(), cardInfo.getEmoluments(),
								wetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				} else {
					cardInfo.setPensionContribution("0");
				}
				log.info("flag" + flag + checkDate + "Pension"
						+ cardInfo.getPensionContribution());
				if (formFlag == true) {
					/*
					 * if (region.equals("CHQIAD")) { advPFDrawn =
					 * this.getEmployeeLoans(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.DRAWN"); } else if (region.equals("CHQNAD"))
					 * { advPFDrawn = this.getEmployeeLoans(con, cardInfo
					 * .getShnMnthYear(), pfIDS, "ADV.DRAWN"); } else if
					 * (region.equals("North Region")) { advPFDrawn = 0; }
					 */
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return pensionList;
	}

	private ArrayList getForm8EmployeeInfo(String sortedColumn, String region,
			boolean formFlag, String fromYear, String airportCode,
			String pensionNo) {
		log.info("FinanceReportDAO::getForm8EmployeeInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", appendRegionTag = "", nextAppendRegionTag = "", appendAirportTag = "", appendPenTag = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}

		if (pensionNo.equals("")) {
			appendPenTag = "";
		} else {
			appendPenTag = " AND PENSIONNO='" + pensionNo + "' ";
		}
		if (airportCode.equals("NO-SELECT")) {
			appendAirportTag = "";
		} else {
			appendAirportTag = "AND AIRPORTCODE='" + airportCode + "' ";
		}
		if (region.equals("NO-SELECT")) {
			appendRegionTag = "WHERE  NVL(DATEOFJOINING,'01-Mar-2009') <='01-Mar-2009' ";
			nextAppendRegionTag = " ";
		} else {
			appendRegionTag = "WHERE  NVL(DATEOFJOINING,'01-Mar-2009') <='01-Mar-2009' AND REGION='"
					+ region + "' " + appendAirportTag + appendPenTag;
			nextAppendRegionTag = " AND REGION='" + region + "'";
		}
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			/*
			 * sqlQuery =
			 * "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('"
			 * + fromYear+
			 * "',dateofbirth)/12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO " +
			 * appendRegionTag + " ORDER BY " + sortedColumn;
			 */
			sqlQuery = "SELECT PENSIONNUMBER, EMPSERIALNUMBER, CPFACNO, AIRPORTSERIALNUMBER, EMPLOYEENO, EMPLOYEENAME, DESEGNATION, EMP_LEVEL, "
					+ "DATEOFBIRTH, DATEOFJOINING, DATEOFSEPERATION_REASON, DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, REMARKS, AIRPORTCODE, "
					+ "SEX, FHNAME, MARITALSTATUS, PERMANENTADDRESS, TEMPORATYADDRESS, WETHEROPTION, SETDATEOFANNUATION, EMPFLAG, DIVISION, DEPARTMENT, "
					+ "EMAILID, EMPNOMINEESHARABLE, REGION, (LAST_DAY(dateofbirth) + INTERVAL '60' year) as DOR, username, LASTACTIVE, OTHERREASON, decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)), -1, '01-Apr-1995', 1, to_char(dateofjoining, 'dd-Mon-yyyy'), to_char(dateofjoining, 'dd-Mon-yyyy')) as DATEOFENTITLE, to_char(trunc((dateofbirth + INTERVAL '60' year), 'MM') - 1, 'dd-Mon-yyyy') as LASTDOB, ROUND(months_between('01-Apr-1995', dateofbirth) / 12) "
					+ "EMPAGE FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM EMPLOYEE_PERSONAL_INFO "
					+ appendRegionTag
					+ ") "
					+ nextAppendRegionTag
					+ " ORDER BY " + sortedColumn;
			log.info("FinanceReportDAO::getEmployeePFInfo Query" + sqlQuery);
			rs = st.executeQuery(sqlQuery);
			String remarks = "";
			while (rs.next()) {
				data = new EmployeePersonalInfo();
				CommonDAO commonDAO = new CommonDAO();
				data = commonDAO.loadEmployeePersonalInfo(rs);
				/*
				 * pfidWithRegion = this.getEmployeeForm8MappingPFInfo(data
				 * .getOldPensionNo(), data.getCpfAccno(), data.getRegion());
				 */
				pfidWithRegion = data.getCpfAccno() + "," + data.getRegion()
						+ "=";
				if (!pfidWithRegion.equals("")) {
					String[] pfIDLists = pfidWithRegion.split("=");
					data.setPfIDString(this.preparedCPFString(pfIDLists));
				}

				if (data.getWetherOption().trim().equals("---")) {
					data.setRemarks("Wether Option is not available");
				}

				if (rs.getString("DATEOFENTITLE") != null) {
					data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
				} else {
					data.setDateOfEntitle("---");
				}
				if (formFlag == false) {

					if (rs.getInt("EMPAGE") == 58) {
						remarks = "Attained 58 years";
					}
					if (data.getSeperationReason().trim().equals("Resignation")
							|| data.getSeperationReason().trim().equals(
									"Termination")) {
						remarks = remarks + data.getSeperationReason() + " On"
								+ data.getSeperationDate();
					}
					data.setRemarks(remarks);
				}

				empinfo.add(data);
				remarks = "";
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;

	}

	private ArrayList getMonthList(Connection con, String monthsInfo) {
		String[] twoMnths = monthsInfo.split("@");
		String firstDt = "", secondDt = "";
		String findMnth = "", foundDate = "";
		int diff = 0;
		ArrayList list = new ArrayList();
		ArrayList mnthBlckList = new ArrayList();
		int firstMnt = 0, secMnts = 0, prvMnth = 0, chkPrvsDt = 0;
		String tempMar = "", tempYear = "", tempFoundDate = "", tempDate = "", firstDtYear = "", secndDtYear = "", chkPrvDtYear = "";
		boolean tempMarFlag = false, breakNotMarFlag = false, hyPenFlag = false, needCheckFlag = false;
		String prvMnt = "", chkPrvMnt = "";
		for (int i = 0; i < twoMnths.length; i++) {
			findMnth = twoMnths[i].toString().trim();
			String[] splits = findMnth.split("#");

			if (splits.length == 2) {
				if (!splits[0].equals("")) {
					firstDt = splits[0];
					if (firstDt.lastIndexOf("*") != -1) {
						tempFoundDate = firstDt.substring(0, firstDt
								.lastIndexOf("*"));
					}
				} else {
					firstDt = "";
				}
				if (!splits[1].equals("")) {
					secondDt = splits[1];
					if (secondDt.lastIndexOf("*") != -1) {
						tempFoundDate = secondDt.substring(0, secondDt
								.lastIndexOf("*"));
					}
				} else {
					secondDt = "";
				}
			} else {
				if (!splits[0].equals("")) {
					firstDt = splits[0];
					secondDt = "";
				} else {
					firstDt = "";
				}
			}
			// log.info("firstDt"+firstDt+"secondDt"+secondDt+"tempFoundDate"+
			// tempFoundDate);

			if (!chkPrvMnt.equals("")) {
				if (chkPrvMnt.lastIndexOf("*") != -1) {
					chkPrvMnt = chkPrvMnt.substring(0, chkPrvMnt
							.lastIndexOf("*"));
					chkPrvsDt = Integer.parseInt(chkPrvMnt.substring(0, 2));
				} else if (chkPrvMnt.lastIndexOf("_") != -1) {
					chkPrvMnt = chkPrvMnt.substring(0, chkPrvMnt
							.lastIndexOf("_"));
					chkPrvsDt = Integer.parseInt(chkPrvMnt.substring(0, 2));
				} else {
					chkPrvsDt = Integer.parseInt(chkPrvMnt.substring(0, 2));
				}
				// log.info("firstDt"+firstDt);
				firstMnt = Integer.parseInt(firstDt.substring(0, 2));
				if (firstMnt < chkPrvsDt) {
					diff = chkPrvsDt - firstMnt;
				} else {
					diff = firstMnt - chkPrvsDt;
				}
				//log.info("firstMnt==================================="+firstMnt
				// );
				if (firstMnt == 4) {
					needCheckFlag = true;
					foundDate = chkPrvMnt;
				}
			}
			//log.info("chkPrvsDt"+chkPrvsDt+"firstMnt"+firstMnt+"needCheckFlag"
			// +needCheckFlag+"diff"+diff+"foundDate==="+foundDate);
			if (needCheckFlag != true) {
				if (!firstDt.equals("")) {
					if (firstDt.lastIndexOf("_") != -1) {
						String[] seprDt = firstDt.split("_");
						tempMar = seprDt[1];
						if (!secondDt.equals("")) {
							if (!tempMar.equals("")
									&& secondDt.substring(0, 2).equals(tempMar)) {
								tempFoundDate = secondDt;
							}

						}

					} else if (!secondDt.equals("")) {
						if (tempMar.equals("")) {
							if (secondDt.lastIndexOf("_") != -1) {
								String[] seprDt1 = secondDt.split("_");
								tempMar = seprDt1[1];
								prvMnt = seprDt1[0];
								breakNotMarFlag = false;
								hyPenFlag = true;
							}

						}
					}
				}

				// log.info("**************firstDt"+firstDt+"secondDt"+secondDt+
				// "hyPenFlag"+hyPenFlag+"breakNotMarFlag"+breakNotMarFlag);

				if (!tempMar.equals("") && tempFoundDate.equals("")
						&& hyPenFlag != true) {
					firstMnt = Integer.parseInt(firstDt.substring(0, 2));
					secMnts = Integer.parseInt(firstDt.substring(0, 2));
					tempYear = firstDt.substring(3, 5);
					//log.info("firstMnt" + firstMnt + "secMnts" + secMnts);
					if (firstMnt != 03) {
						foundDate = "02-" + tempYear;
						tempMarFlag = true;
					} else if (secMnts != 03) {
						foundDate = "02-" + tempYear;
						tempMarFlag = true;
					}
					tempDate = prvMnt;

				} else if (!tempMar.equals("") && breakNotMarFlag == true
						&& !prvMnt.equals("")) {
					// log.info("tempMar-----------"+tempMar+"prvMnt"+prvMnt);
					firstMnt = Integer.parseInt(firstDt.substring(0, 2));
					if (!secondDt.equals("")) {
						secMnts = Integer.parseInt(secondDt.substring(0, 2));
					}

					prvMnth = Integer.parseInt(prvMnt.substring(0, 2));

					if (prvMnth > firstMnt) {
						diff = prvMnth - firstMnt;
					} else {
						diff = firstMnt - prvMnth;
					}

					// log.info("tempMar-----------"+tempMar+"firstMnt"+firstDt+
					// "prvMnt"+prvMnth+"diff=="+diff);
					if (diff >= 2) {
						if (firstMnt == 4) {
							foundDate = prvMnt;
							tempMarFlag = true;
						}

					} else if (diff == 1 && firstMnt == 3) {
						foundDate = firstDt;
						tempMarFlag = true;
					}
					prvMnt = "";
					tempDate = "";
					breakNotMarFlag = false;
					hyPenFlag = false;
				} else if (!tempFoundDate.equals("") && breakNotMarFlag != true) {
					if (firstDt.lastIndexOf("*") != -1) {
						foundDate = tempFoundDate;
						tempMarFlag = true;
					} else if (secondDt.lastIndexOf("*") != -1) {
						chkPrvMnt = tempFoundDate;
						tempMarFlag = true;
					}

				}
				// log.info("tempMar********"+tempMar+"tempFoundDate"+
				// tempFoundDate+"foundDate"+foundDate+"prvMnt"+prvMnt);
				if (!firstDt.equals("") && !secondDt.equals("")) {
					firstMnt = Integer.parseInt(firstDt.substring(0, 2));
					secMnts = Integer.parseInt(secondDt.substring(0, 2));
					if (firstMnt < secMnts) {

						diff = secMnts - firstMnt;
					} else {
						diff = firstMnt - secMnts;
					}

				} else if (!firstDt.equals("") && secondDt.equals("")) {
					log.info("===firstMnt===   "+firstDt);
					firstMnt = Integer.parseInt(firstDt.substring(0, 2));
					secMnts = 0;
					diff = firstMnt - secMnts;
				}

				if (hyPenFlag == true) {
					breakNotMarFlag = true;
				}

				//log.info("firstMnt"+firstMnt+","+firstDt+"secMnts"+secondDt+","
				// +secMnts+"diff"+diff);

				if (tempMarFlag != true && hyPenFlag != true) {
					// log.info("tempMarFlag!=true");
					if (!chkPrvMnt.equals("")) {
						firstDtYear = firstDt.substring(firstDt
								.lastIndexOf("-") + 1, firstDt.length());
						secndDtYear = secondDt.substring(secondDt
								.lastIndexOf("-") + 1, secondDt.length());
						chkPrvDtYear = chkPrvMnt.substring(chkPrvMnt
								.lastIndexOf("-") + 1, chkPrvMnt.length());

					}
					// log.info("firstDtYear"+firstDtYear+"secndDtYear"+
					// secndDtYear+"chkPrvDtYear"+chkPrvDtYear);
					if (firstMnt == 3) {
						log.info("===firstMnt===");
						if (!chkPrvDtYear.equals(firstDtYear) && firstMnt > 3) {
							foundDate = chkPrvMnt;
						} else {
							foundDate = firstDt;
						}

					} else if (secMnts == 3) {
						if (!firstDtYear.equals(secndDtYear)) {
							if (!chkPrvDtYear.equals(secndDtYear)) {
								foundDate = firstDt;
							} else {
								foundDate = secondDt;
							}
						} else {
							foundDate = secondDt;
						}

						// log.info(secndDtYear+"===secMnts==="+chkPrvDtYear);

					} else if (secMnts == 0 && (firstMnt >= 1 && firstMnt <= 3)) {
						//log.info("secMnts==0 && (firstMnt>=1 && firstMnt<=3)")
						// ;
						foundDate = firstDt;

					} else if ((diff > 3 && diff <= 8) && (secMnts == 4)) {
						// log.info("(diff> 3  && diff<=8)&& (secMnts==4)");
						foundDate = firstDt;
					} else {
						// log.info("===Both Failed ===");
						if (diff != 1 && diff != 0 && secMnts > 3
								&& firstMnt < 3) {
							// log.info(
							// "diff!=1 && diff!=0 && secMnts>3 && firstMnt<3");
							foundDate = firstDt;
						} else if (diff != 1 && diff != 0 && firstMnt < 3) {
							// log.info("diff!=1 && diff!=0 && firstMnt<3");
							foundDate = secondDt;
						} else if (diff == 1 && firstMnt == 3) {
							// log.info("diff==1 && firstMnt==3");
							foundDate = firstDt;
						} else if (diff == 1 && secMnts == 3) {
							// log.info("diff==1 && secMnts==3");
							foundDate = secondDt;
						}
					}
				}
				// log.info("diff"+diff+"foundDate"+foundDate);
			} else {
				needCheckFlag = false;
				chkPrvMnt = "";
			}

			if (breakNotMarFlag != true) {
				tempMarFlag = false;
				tempMar = "";
				tempYear = "";
				tempFoundDate = "";
				prvMnt = "";

			}

			chkPrvMnt = secondDt;
			// log.info("Last secondDt"+secondDt);
			// log.info("i=="+i+twoMnths.length);
			if (i == (twoMnths.length - 1)) {
				if (!secondDt.equals("")) {
					foundDate = secondDt;
				} else {
					foundDate = firstDt;
				}
			}
			if (!foundDate.equals("")) {
				if (foundDate.lastIndexOf("*") != -1) {
					foundDate = foundDate.substring(0, foundDate
							.lastIndexOf("*"));
				}
				if (foundDate.lastIndexOf("_") != -1) {
					foundDate = foundDate.substring(0, foundDate
							.lastIndexOf("_"));
				}
				if (!list.contains(foundDate)) {
					list.add(foundDate);
				}

			}
			foundDate = "";

		}

		mnthBlckList = this.getRatesForBlockMnth(con, list);
		return mnthBlckList;
	}

	private String compareTwoDates(String dateOfEntitle, String fromDate) {
		long lDoE = 0, lFrDt = 0;
		String fndDate = "";
		lDoE = Date.parse(dateOfEntitle);
		lFrDt = Date.parse(fromDate);
		log.info("Date Of Entile"+dateOfEntitle+"fromDate"+fromDate);
		log.info("Date Of Entile"+lDoE+"fromDate"+lFrDt);
		if (lDoE >= lFrDt) {
			fndDate = dateOfEntitle;

		} else {
			fndDate = fromDate;
		}
		return fndDate;
	}

	private ArrayList getRatesForBlockMnth(Connection con, ArrayList blockList) {
		ArrayList list = new ArrayList();
		int counter = 0;
		String interestRtMnt = "", getMnth = "", finalRtMnth = "", years = "", decremtnYear = "", finalRtrvdMthYear = "";
		try {

			for (int i = 0; i < blockList.size(); i++) {
				counter++;
				getMnth = (String) blockList.get(i);
				String getyear[] = getMnth.split("-");
				years = getyear[1];
				//log.info("=====================================" + years);
				if (years.equals("95")) {
					decremtnYear = years;

				} else {
					/*
					 * if(counter==blockList.size()){
					 * decremtnYear=Integer.toString(Integer.parseInt(years));
					 * }else{
					 */
					if (years.equals("00")) {
						decremtnYear = "99";
					} else {
						decremtnYear = Integer
								.toString(Integer.parseInt(years) - 1);
					}

					/* } */

					if (decremtnYear.length() < 2) {
						decremtnYear = "0" + decremtnYear;
					}

				}
				finalRtrvdMthYear = getyear[0] + "-" + decremtnYear;
				// log.info("getRatesForBlockMnth================="+
				// finalRtrvdMthYear);
				try {
					interestRtMnt = this.getEmployeeRateOfInterest(con,
							commonUtil.converDBToAppFormat(finalRtrvdMthYear,
									"MM-yy", "yyyy"));
					finalRtMnth = interestRtMnt + "," + getMnth;
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info(finalRtMnth);
				list.add(finalRtMnth);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return list;
	}

	public Form8RemittanceBean getForm8RemittanceInfo(String fromDate,
			String toDate, String region, String pensionNo, boolean FormFlag,
			boolean secondFormFlag,String formType) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Form8RemittanceBean remittanceBean = new Form8RemittanceBean();
		String emoluments = "", pfStatury = "", vpf = "", cpf = "", monthYear = "", month = "";
		int subscribersCnt = 0;
		double pensionVal = 0.0, totalPFStatury = 0.0, totalVPF = 0.0, totalCPF = 0.0, totalContribution = 0.0;
		int totalAprCnt = 0, totalMayCnt = 0, totalJunCnt = 0, totalJulCnt = 0, totalAugCnt = 0, totalSepCnt = 0, totalOctCnt = 0, totalNovCnt = 0;
		int totalDecCnt = 0, totalJanCnt = 0, totalFebCnt = 0, totalMarCnt = 0;
		double totalAprPFStatury = 0.0, totalAprVPF = 0.0, totalAprCPF = 0.0, totalAprContribution = 0.0;
		double totalMayPFStatury = 0.0, totalMayVPF = 0.0, totalMayCPF = 0.0, totalMayContribution = 0.0;
		double totalJunPFStatury = 0.0, totalJunVPF = 0.0, totalJunCPF = 0.0, totalJunContribution = 0.0;
		double totalJulPFStatury = 0.0, totalJulVPF = 0.0, totalJulCPF = 0.0, totalJulContribution = 0.0;
		double totalAugPFStatury = 0.0, totalAugVPF = 0.0, totalAugCPF = 0.0, totalAugContribution = 0.0;
		double totalSepPFStatury = 0.0, totalSepVPF = 0.0, totalSepCPF = 0.0, totalSepContribution = 0.0;
		double totalOctPFStatury = 0.0, totalOctVPF = 0.0, totalOctCPF = 0.0, totalOctContribution = 0.0;
		double totalNovPFStatury = 0.0, totalNovVPF = 0.0, totalNovCPF = 0.0, totalNovContribution = 0.0;
		double totalDecPFStatury = 0.0, totalDecVPF = 0.0, totalDecCPF = 0.0, totalDecContribution = 0.0;
		double totalJanPFStatury = 0.0, totalJanVPF = 0.0, totalJanCPF = 0.0, totalJanContribution = 0.0;
		double totalFebPFStatury = 0.0, totalFebVPF = 0.0, totalFebCPF = 0.0, totalFebContribution = 0.0;
		double totalMarPFStatury = 0.0, totalMarVPF = 0.0, totalMarCPF = 0.0, totalMarContribution = 0.0, retriredEmoluments = 0.0;

		double totalAprEmoluments = 0, totalEmoluments = 0;
		double totalMayEmoluments = 0;
		double totalJunEmoluments = 0;
		double totalJulEmoluments = 0;
		double totalAugEmoluments = 0;
		double totalSepEmoluments = 0;
		double totalOctEmoluments = 0;
		double totalNovEmoluments = 0;
		double totalDecEmoluments = 0;
		double totalJanEmoluments = 0;
		double totalFebEmoluments = 0;
		double totalMarEmoluments = 0;
		String airportCode = "", aprDispMnth = "", mayDispMnth = "", junDispMnth = "", julDispMnth = "", augDispMnth = "", sepDispMnth = "", octDispMnth = "", novDispMnth = "", decDispMnth = "", janDispMnth = "", febDispMnth = "", marDispMnth = "";
		String wetherOption = "", pensionNoQry = "", iadAirportString = "", days = "", regionString = "", nxtRegionString = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		if (!pensionNo.equals("")) {
			pensionNoQry = " AND PENSIONNO='" + pensionNo + "'";
		}
		if (!region.equals("NO-SELECT")) {
			if (region.indexOf("-") != -1) {
				String regionsList[] = region.split("-");
				if (regionsList[0].equals("IAD")) {

					region = "";

					region = "CHQIAD";
					airportCode = regionsList[1];
					iadAirportString = " AND AIRPORTCODE='" + airportCode
							+ "' ";
				}
			}
			regionString = "AND REGION = '" + region + "' " + iadAirportString;
			nxtRegionString = "AND REGION = '" + region + "' ";
		} else {
			regionString = "";
			nxtRegionString = "";
		}
		String findFromYear="",findToYear="",sqlQuery="";
		boolean yearBreakMonthFlag=false;
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			yearBreakMonthFlag = true;
			if(!formType.equals("FORM-8-PS-Arrear")){
				 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
						+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG FROM "
						+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
			}else{
				 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
						+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG FROM "
						+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
				
			}
			
			

		}else{
			yearBreakMonthFlag = false;
			 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.WETHEROPTION,EMPFID.EMPSERIALNUMBER as EMPSERIALNUMBER,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
				+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.PENSIONCONTRI,EPV.PF,EPV.EDITTRANS,EPV.ARREARFLAG,EPV.DEPUTATIONFLAG FROM "
				+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
				+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 OR emoluments!=0.0) and monthyear between '"
				+ fromDate
				+ "' and LAST_DAY('"
				+ toDate
				+ "') ) EPV  ,(SELECT REGION , CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
				+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND(NVL(DATEOFJOINING,'01-Mar-2009')) <='01-Mar-2009' "
				+ regionString
				+ pensionNoQry
				+ ") ) EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER ";

		}
		
		log.info("FinanceReportDAO::getForm8RemittanceInfo" + sqlQuery);
		DecimalFormat df = new DecimalFormat("#########0");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		String totalContr = "", disYear = "", calEmoluments = "", dateOfBirth = "", dateOfRetriment = "",editTransFlag="",arrearFlag ="";
		String checkDate = "", chkMnthYear = "Apr-1995", pensionNos = "", frmCpfaccno = "",emolumentMonths="";
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		try {
			con = commonDB.getConnection();

			st = con.createStatement();
			st.setFetchSize(100);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				flag = false;
				contrFlag = false;
				chkDOBFlag = false;
				if (rs.getString("MONTH") != null) {
					month = rs.getString("MONTH");
				}

				if (rs.getString("MNTYEAR") != null) {
					disYear = rs.getString("MNTYEAR");
				} else {
					disYear = "---";
				}
				if (rs.getString("EMOLUMENTMONTHS") != null) {
					emolumentMonths=rs.getString("EMOLUMENTMONTHS".trim());
				} else {
					emolumentMonths="1";
				}
				if (rs.getString("CPFACCNO") != null) {
					frmCpfaccno = rs.getString("CPFACCNO");
				}

				if (rs.getString("WETHEROPTION") != null) {
					wetherOption = rs.getString("WETHEROPTION");
				}
				if (rs.getString("EDITTRANS") != null) {
					editTransFlag = rs.getString("EDITTRANS");
				} 
				if (rs.getString("ARREARFLAG") != null) {
					arrearFlag = rs.getString("ARREARFLAG");
				}
				if (rs.getString("MONTHYEAR") != null) {
					monthYear = commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy");

				}
				if (rs.getString("EMOLUMENTS") != null) {
					emoluments = rs.getString("EMOLUMENTS");
				} else {
					emoluments = "0.00";
				}
				if (rs.getString("dateofbirth") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}
				String deputationflag="N";
				if (rs.getString("DEPUTATIONFLAG") != null) {
					deputationflag=rs.getString("DEPUTATIONFLAG");
				} 
				if (!dateOfBirth.equals("---")) {
					try {
						dateOfRetriment = this.getRetriedDate(dateOfBirth);
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					dateOfRetriment = "";
				}

				try {
					checkDate = commonUtil.converDBToAppFormat(monthYear,
							"dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				String decMnt = "";
				if (checkDate.equals("Dec-1995")) {
					decMnt = this.getRnfcForm8ReportDec95(emoluments,
							dateOfBirth, monthYear, wetherOption.trim(),
							dateOfRetriment, flag,emolumentMonths);
					String[] decCal = decMnt.split(",");
					if (decCal.length == 2) {
						calEmoluments = decCal[0];
						pensionVal = Double.parseDouble(decCal[1]);
					}

				} else {
					calEmoluments = this.calWages(emoluments, monthYear,
							wetherOption.trim(), false, false,emolumentMonths);
				}
				//calEmoluments=this.calWages(emoluments,monthYear,wetherOption.
				// trim(),FormFlag,secondFormFlag);
				if (rs.getString("EMPPFSTATUARY") != null) {
					pfStatury = rs.getString("EMPPFSTATUARY");
				} else {
					pfStatury = "0.00";
				}
				if (rs.getString("EMPVPF") != null) {
					vpf = rs.getString("EMPVPF");
				} else {
					vpf = "0";
				}
				if (rs.getString("CPF") != null) {
					cpf = rs.getString("CPF");
				} else {
					cpf = "0";
				}
				if (!monthYear.equals("-NA-")
						&& !dateOfRetriment.equals("")) {
					transMntYear = Date.parse(monthYear);
					empRetriedDt = Date.parse(dateOfRetriment);

					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == 0
							|| empRetriedDt == 0) {
						contrFlag = false;
					} else if (transMntYear == empRetriedDt
							&& transMntYear != 0
							&& empRetriedDt != 0) {
						chkDOBFlag = true;
					}
				}

				if(yearBreakMonthFlag==false){
					if (!checkDate.equals("Dec-1995")) {
						if (flag == false) {
							
							if (contrFlag != true) {
								// log.info("frmEmployeeName"+frmEmployeeName);
								pensionVal = this.pensionFormsCalculation(
										monthYear, calEmoluments, wetherOption
												.trim(), region, false, false,emolumentMonths);

								if (chkDOBFlag == true) {
									String[] dobList = dateOfBirth.split("-");
									days = dobList[0];
									getDaysBymonth = this
											.getNoOfDays(dateOfBirth);
									pensionVal = pensionVal
											* (Double.parseDouble(days) - 1)
											/ getDaysBymonth;
									retriredEmoluments = new Double(
											df1
													.format(Double
															.parseDouble(calEmoluments)
															* (Double
																	.parseDouble(days) - 1)
															/ getDaysBymonth))
											.doubleValue();
									calEmoluments = Double
											.toString(retriredEmoluments);
								}

							} else {
								pensionVal = 0;
								calEmoluments = "0";
								}

						} else {
							pensionVal = 0;
						}
					}
					if(deputationflag.equals("Y")){
						pensionVal = rs.getDouble("PENSIONCONTRI");
						
					}
				}else{
					if(rs.getString("PENSIONCONTRI")!=null){
						pensionVal=Double.parseDouble(rs.getString("PENSIONCONTRI"));
					}else{
						pensionVal = 0.0;
					}
					//log.info("available data is editTransFlag"+editTransFlag+"checkDate"+checkDate);
					//log.info("available data is contrFlag"+contrFlag+"checkDate"+chkDOBFlag);
					if (contrFlag != true) {
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
						
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}
						}else {
						//	log.info("available data is"+editTransFlag+"checkDate"+checkDate);
							if(arrearFlag.equals("N")){
								if(!(editTransFlag.equals("Y") && checkDate.toUpperCase().equals("SEP-2009"))){
									pensionVal = 0.0;
									calEmoluments = "0";
								}
							
							}
								
						}
				
				}
		

				emoluments = calEmoluments.trim();

				// log.info("monthYear=="+monthYear+"==CPFACCNO==="+commonUtil.
				// leadingZeros
				// (20,frmCpfaccno)+"emoluments===="+commonUtil.leadingZeros
				// (20,emoluments)+"pensionVal===="+pensionVal);
				calEmoluments = "";
				if (pensionVal != 0) {
					if (month.toUpperCase().equals("APR")) {
						aprDispMnth = disYear;
						totalAprEmoluments = totalAprEmoluments
								+ Double.parseDouble(emoluments);
						totalAprPFStatury = totalAprPFStatury
								+ Double.parseDouble(pfStatury);
						totalAprVPF = totalAprVPF + Double.parseDouble(vpf);
						totalAprCPF = totalAprCPF + Double.parseDouble(cpf);
						totalAprContribution = totalAprContribution
								+ Math.round(pensionVal);
						totalAprCnt++;
					}
					if (month.toUpperCase().equals("MAY")) {
						mayDispMnth = disYear;
						totalMayEmoluments = totalMayEmoluments
								+ Double.parseDouble(emoluments);
						totalMayPFStatury = totalMayPFStatury
								+ Double.parseDouble(pfStatury);
						totalMayVPF = totalMayVPF + Double.parseDouble(vpf);
						totalMayCPF = totalMayCPF + Double.parseDouble(cpf);
						totalMayContribution = totalMayContribution
								+ Math.round(pensionVal);
						totalMayCnt++;
					}
					if (month.toUpperCase().equals("JUN")) {
						junDispMnth = disYear;
						totalJunEmoluments = totalJunEmoluments
								+ Double.parseDouble(emoluments);
						totalJunPFStatury = totalJunPFStatury
								+ Double.parseDouble(pfStatury);
						totalJunVPF = totalJunVPF + Double.parseDouble(vpf);
						totalJunCPF = totalJunCPF + Double.parseDouble(cpf);
						totalJunContribution = totalJunContribution
								+ Math.round(pensionVal);
						totalJunCnt++;
					}
					if (month.toUpperCase().equals("JUL")) {
						julDispMnth = disYear;
						totalJulEmoluments = totalJulEmoluments
								+ Double.parseDouble(emoluments);
						totalJulPFStatury = totalJulPFStatury
								+ Double.parseDouble(pfStatury);
						totalJulVPF = totalJulVPF + Double.parseDouble(vpf);
						totalJulCPF = totalJulCPF + Double.parseDouble(cpf);
						totalJulContribution = totalJulContribution
								+ Math.round(pensionVal);
						totalJulCnt++;
					}
					if (month.toUpperCase().equals("AUG")) {
						augDispMnth = disYear;
						totalAugEmoluments = totalAugEmoluments
								+ Double.parseDouble(emoluments);
						totalAugPFStatury = totalAugPFStatury
								+ Double.parseDouble(pfStatury);
						totalAugVPF = totalAugVPF + Double.parseDouble(vpf);
						totalAugCPF = totalAugCPF + Double.parseDouble(cpf);
						totalAugContribution = totalAugContribution
								+ Math.round(pensionVal);
						totalAugCnt++;
					}
					if (month.toUpperCase().equals("SEP")) {
						sepDispMnth = disYear;
						totalSepEmoluments = totalSepEmoluments
								+ Double.parseDouble(emoluments);
						totalSepPFStatury = totalSepPFStatury
								+ Double.parseDouble(pfStatury);
						totalSepVPF = totalSepVPF + Double.parseDouble(vpf);
						totalSepCPF = totalSepCPF + Double.parseDouble(cpf);
						totalSepContribution = totalSepContribution
								+ Math.round(pensionVal);
						totalSepCnt++;
					}
					if (month.toUpperCase().equals("OCT")) {
						octDispMnth = disYear;
						totalOctEmoluments = totalOctEmoluments
								+ Double.parseDouble(emoluments);
						totalOctPFStatury = totalOctPFStatury
								+ Double.parseDouble(pfStatury);
						totalOctVPF = totalOctVPF + Double.parseDouble(vpf);
						totalOctCPF = totalOctCPF + Double.parseDouble(cpf);
						totalOctContribution = totalOctContribution
								+ Math.round(pensionVal);
						totalOctCnt++;
					}
					if (month.toUpperCase().equals("NOV")) {
						novDispMnth = disYear;
						totalNovEmoluments = totalNovEmoluments
								+ Double.parseDouble(emoluments);
						totalNovPFStatury = totalNovPFStatury
								+ Double.parseDouble(pfStatury);
						totalNovVPF = totalNovVPF + Double.parseDouble(vpf);
						totalNovCPF = totalNovCPF + Double.parseDouble(cpf);
						totalNovContribution = totalNovContribution
								+ Math.round(pensionVal);
						totalNovCnt++;
					}
					if (month.toUpperCase().equals("DEC")) {
						decDispMnth = disYear;
						totalDecEmoluments = totalDecEmoluments
								+ Double.parseDouble(emoluments);
						totalDecPFStatury = totalDecPFStatury
								+ Double.parseDouble(pfStatury);
						totalDecVPF = totalDecVPF + Double.parseDouble(vpf);
						totalDecCPF = totalDecCPF + Double.parseDouble(cpf);
						totalDecContribution = totalDecContribution
								+ Math.round(pensionVal);
						totalDecCnt++;
					}
					if (month.toUpperCase().equals("JAN")) {
						janDispMnth = disYear;
						totalJanEmoluments = totalJanEmoluments
								+ Double.parseDouble(emoluments);
						totalJanPFStatury = totalJanPFStatury
								+ Double.parseDouble(pfStatury);
						totalJanVPF = totalJanVPF + Double.parseDouble(vpf);
						totalJanCPF = totalJanCPF + Double.parseDouble(cpf);
						totalJanContribution = totalJanContribution
								+ Math.round(pensionVal);;
						totalJanCnt++;
					}
					if (month.toUpperCase().equals("FEB")) {
						febDispMnth = disYear;
						totalFebEmoluments = totalFebEmoluments
								+ Double.parseDouble(emoluments);
						totalFebPFStatury = totalFebPFStatury
								+ Double.parseDouble(pfStatury);
						totalFebVPF = totalFebVPF + Double.parseDouble(vpf);
						totalFebCPF = totalFebCPF + Double.parseDouble(cpf);
						totalFebContribution = totalFebContribution
								+ Math.round(pensionVal);
						totalFebCnt++;
					}
					if (month.toUpperCase().equals("MAR")) {
						marDispMnth = disYear;
						totalMarEmoluments = totalMarEmoluments
								+ Double.parseDouble(emoluments);
						totalMarPFStatury = totalMarPFStatury
								+ Double.parseDouble(pfStatury);
						totalMarVPF = totalMarVPF + Double.parseDouble(vpf);
						totalMarCPF = totalMarCPF + Double.parseDouble(cpf);
						totalMarContribution = totalMarContribution
								+ Math.round(pensionVal);
						totalMarCnt++;
					}
					pensionVal = 0.0;
					emoluments = "";
					pfStatury = "";
					dateOfRetriment = "";

				}
			}

			totalContribution = totalAprContribution + totalMayContribution
					+ totalJunContribution + totalJulContribution
					+ totalAugContribution + totalSepContribution;
			totalContribution = totalContribution + totalOctContribution
					+ totalNovContribution + totalDecContribution;
			totalContribution = totalContribution + totalJanContribution
					+ totalFebContribution + totalMarContribution;

			totalEmoluments = totalAprEmoluments + totalMayEmoluments
					+ totalJunEmoluments + totalJulEmoluments
					+ totalAugEmoluments + totalSepEmoluments;
			totalEmoluments = totalEmoluments + totalOctEmoluments
					+ totalNovEmoluments + totalDecEmoluments;
			totalEmoluments = totalEmoluments + totalJanEmoluments
					+ totalFebEmoluments + totalMarEmoluments;

			remittanceBean.setTotalAprContribution(new Double(df.format(Math
					.round(totalAprContribution))).doubleValue());
			remittanceBean.setTotalAprEmoluments(new Long(df.format(Math
					.round(totalAprEmoluments))).longValue());
			remittanceBean.setAprMnth(aprDispMnth);
			remittanceBean.setAprCnt(totalAprCnt);
			remittanceBean.setTotalMayContribution(new Double(df.format(Math
					.round(totalMayContribution))).doubleValue());
			remittanceBean.setTotalMayEmoluments(new Long(df.format(Math
					.round(totalMayEmoluments))).longValue());
			remittanceBean.setMayCnt(totalMayCnt);
			remittanceBean.setMayMnth(mayDispMnth);
			remittanceBean.setTotalJunContribution(new Double(df.format(Math
					.round(totalJunContribution))).doubleValue());
			remittanceBean.setTotalJunEmoluments(new Long(df.format(Math
					.round(totalJunEmoluments))).longValue());
			remittanceBean.setJunCnt(totalJunCnt);
			remittanceBean.setJunMnth(junDispMnth);
			remittanceBean.setTotalJulContribution(new Double(df.format(Math
					.round(totalJulContribution))).doubleValue());
			remittanceBean.setTotalJulEmoluments(new Long(df.format(Math
					.round(totalJulEmoluments))).longValue());
			remittanceBean.setJulCnt(totalJulCnt);
			remittanceBean.setJulMnth(julDispMnth);
			remittanceBean.setTotalAugContribution(new Double(df.format(Math
					.round(totalAugContribution))).doubleValue());
			remittanceBean.setTotalAugEmoluments(new Long(df.format(Math
					.round(totalAugEmoluments))).longValue());
			remittanceBean.setAugCnt(totalAugCnt);
			remittanceBean.setAugMnth(augDispMnth);
			remittanceBean.setTotalSepContribution(new Double(df.format(Math
					.round(totalSepContribution))).doubleValue());
			remittanceBean.setTotalSepEmoluments(new Long(df.format(Math
					.round(totalSepEmoluments))).longValue());
			remittanceBean.setSepCnt(totalSepCnt);
			remittanceBean.setSepMnth(sepDispMnth);
			remittanceBean.setTotalOctContribution(new Double(df.format(Math
					.round(totalOctContribution))).doubleValue());
			remittanceBean.setTotalOctEmoluments(new Long(df.format(Math
					.round(totalOctEmoluments))).longValue());
			remittanceBean.setOctCnt(totalOctCnt);
			remittanceBean.setOctMnth(octDispMnth);
			remittanceBean.setTotalNovContribution(new Double(df.format(Math
					.round(totalNovContribution))).doubleValue());
			remittanceBean.setTotalNovEmoluments(new Long(df.format(Math
					.round(totalNovEmoluments))).longValue());
			remittanceBean.setNovCnt(totalNovCnt);
			remittanceBean.setNovMnth(novDispMnth);
			remittanceBean.setTotalDecContribution(new Double(df.format(Math
					.round(totalDecContribution))).doubleValue());
			remittanceBean.setTotalDecEmoluments(new Long(df.format(Math
					.round(totalDecEmoluments))).longValue());
			remittanceBean.setDecCnt(totalDecCnt);
			remittanceBean.setDecMnth(decDispMnth);
			remittanceBean.setTotalJanContribution(new Double(df.format(Math
					.round(totalJanContribution))).doubleValue());
			remittanceBean.setTotalJanEmoluments(new Long(df.format(Math
					.round(totalJanEmoluments))).longValue());
			remittanceBean.setJanCnt(totalJanCnt);
			remittanceBean.setJanMnth(janDispMnth);
			remittanceBean.setTotalFebContribution(new Double(df.format(Math
					.round(totalFebContribution))).doubleValue());
			remittanceBean.setTotalFebEmoluments(new Long(df.format(Math
					.round(totalFebEmoluments))).longValue());
			remittanceBean.setFebCnt(totalFebCnt);
			remittanceBean.setFebMnth(febDispMnth);
			remittanceBean.setTotalMarContribution(new Double(df.format(Math
					.round(totalMarContribution))).doubleValue());
			remittanceBean.setTotalMarEmoluments(new Long(df.format(Math
					.round(totalMarEmoluments))).longValue());
			remittanceBean.setMarCnt(totalMarCnt);
			remittanceBean.setMarMnth(marDispMnth);

			remittanceBean.setTotalContr(df.format(Math
					.round(totalContribution)));
			remittanceBean
					.setTotalEmnts(df.format(Math.round(totalEmoluments)));
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return remittanceBean;
	}



	private String getEmployeeForm8MappingPFInfo(String personalPFID,
			String personalCPFACCNO, String personalRegion) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfID = "", region = "", regionPFIDS = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"
					+ personalPFID + "' and region='" + personalRegion + "'";
			// log.info("FinanceReportDAO::getEmployeeMappingPFInfo" +
			// sqlQuery);
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("CPFACNO") != null) {
					pfID = rs.getString("CPFACNO");

				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");

				}
				if (regionPFIDS.equals("")) {
					regionPFIDS = pfID + "," + region + "=";
				} else {
					regionPFIDS = regionPFIDS + pfID + "," + region + "=";
				}

			}
			if (!regionPFIDS.equals("")) {
				regionPFIDS = regionPFIDS + personalCPFACCNO + ","
						+ personalRegion + "=";
			}
			log
					.info("=============getEmployeeForm8MappingPFInfo========================"
							+ regionPFIDS);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return regionPFIDS;
	}


	public ArrayList calOBPFCard(double interest, ArrayList obList,
			double totalAAINet, double totalAaiIntNet, double totalEmpNet,
			double totalEmpIntNet, double totalPensionContr,
			double penInterest, double advancePFWPaid, double principle,
			int noOfMonths) {
		ArrayList finalOBList = new ArrayList();
		String sqlInsQuery = "", obFromTrFlag = "", obYear = "", obMnthYear = "";
		int year = 0, inserted = 0;
		Long empNetOB = null, aaiNetOB = null, pensionOB = null, principalOB = null;
		Long empNetAdjOB = null, aaiNetAdjOB = null, pensionAdjOB = null, principalAdjOB = null, empSubAdjOB = null,adjPensionContri=null;

		long finalPrincipalOB = 0, finalEmpNetOB = 0, finalAaiNetOB = 0, finalPensionOB = 0, finalOBFromTrFlag = 0, finalOBYear = 0, obEmpNetInterest = 0, obAaiNetInterest = 0, obPenInterest = 0;
		long adjOBEmpNetInterest = 0, adjOBAaiNetInterest = 0, adjOBPenInterest = 0, adjOBEmpSubInterest = 0;
		long empNetInterest = 0, aaiNetInterest = 0, penContInterest = 0;
		
		empNetOB = (Long) obList.get(0);
		aaiNetOB = (Long) obList.get(1);
		pensionOB = (Long) obList.get(2);
		principalOB = (Long) obList.get(10);
		obFromTrFlag = (String) obList.get(3);
		obYear = (String) obList.get(4);
		if (obList.size() > 4) {
			empNetAdjOB = (Long) obList.get(5);
			aaiNetAdjOB = (Long) obList.get(6);

			pensionAdjOB = (Long) obList.get(7);
			empSubAdjOB = (Long) obList.get(8);
			adjPensionContri = (Long) obList.get(9);
			principalAdjOB = new Long(0);
		}

		
		if (!obYear.equals("")) {
			year = Integer.parseInt(obYear) + 1;
		} else {
			year = 2009;
			obYear = "2009";
		}
		if (!obYear.equals("")) {

			obMnthYear = "01-Apr-" + year;
			if (noOfMonths < 12) {
				obEmpNetInterest = Math
						.round(((empNetOB.longValue() * interest) / 100 / 12)
								* noOfMonths);
				obAaiNetInterest = Math
						.round(((aaiNetOB.longValue() * interest) / 100 / 12)
								* noOfMonths);
				obPenInterest = Math
						.round(((pensionOB.longValue() * interest) / 100 / 12)
								* noOfMonths);

				adjOBEmpSubInterest = Math
						.round(((empSubAdjOB.longValue() * interest) / 100 / 12)
								* noOfMonths);
				adjOBEmpNetInterest = Math
						.round(((empNetAdjOB.longValue() * interest) / 100 / 12)
								* noOfMonths);
				adjOBAaiNetInterest = Math
						.round(((aaiNetAdjOB.longValue() * interest) / 100 / 12)
								* noOfMonths);

				adjOBPenInterest = Math
						.round(((pensionAdjOB.longValue() * interest) / 100 / 12)
								* noOfMonths);
			} else {
				obEmpNetInterest = Math
						.round((empNetOB.longValue() * interest) / 100);
				obAaiNetInterest = Math
						.round((aaiNetOB.longValue() * interest) / 100);
				obPenInterest = Math
						.round((pensionOB.longValue() * interest) / 100);

				adjOBEmpSubInterest = Math
						.round((empSubAdjOB.longValue() * interest) / 100);
				adjOBEmpNetInterest = Math
						.round((empNetAdjOB.longValue() * interest) / 100);
				adjOBAaiNetInterest = Math
						.round((aaiNetAdjOB.longValue() * interest) / 100);

				adjOBPenInterest = Math
						.round((pensionAdjOB.longValue() * interest) / 100);
			}

	
			finalEmpNetOB = new Double(empNetOB.longValue()
					+ new Double(totalEmpNet).longValue()+penInterest
					+ empSubAdjOB.longValue() + obEmpNetInterest
					+ totalEmpIntNet + adjOBEmpSubInterest).longValue();
			finalAaiNetOB = new Double(aaiNetOB.longValue()
					+ new Double(totalAAINet).longValue()
					+ aaiNetAdjOB.longValue() + obAaiNetInterest
					+ totalAaiIntNet + adjOBAaiNetInterest).longValue();
			// finalPensionOB=new Double(pensionOB.longValue()+new
			// Double(totalPensionContr
			// ).longValue()+obPenInterest+penInterest).longValue();
/*			finalPensionOB = new Double(pensionOB.longValue()+adjPensionContri.longValue()
					+ new Double(totalPensionContr).longValue() + obPenInterest)
					.longValue();*/
			/*We are not showing teh pensionob.So that is the reason we are removed the pensionob for the calculation of finalpensionob*/
			finalPensionOB = new Double(adjPensionContri.longValue()
					+ new Double(totalPensionContr).longValue() )
					.longValue();
			finalPrincipalOB = new Double(principalOB.longValue()
					+ new Double(advancePFWPaid).longValue()
					+ principalAdjOB.longValue() - principle).longValue();
	
			log.info("empNetOB.longValue()====" + empNetOB.longValue() + "::"+totalEmpNet+"empSubAdjOB====="
					+ empSubAdjOB+"obEmpNetInterest"+obEmpNetInterest+"totalEmpIntNet::"+totalEmpIntNet);
			
			log.info("totalEmpNet" + totalEmpNet + "totalEmpIntNet"
					+ totalEmpIntNet + "empNetOB=======" + empNetOB +"obEmpNetInterest" + obEmpNetInterest);
			
			log.info("empNetAdjOB====" + empNetAdjOB + "adjOBEmpSubInterest====="
					+ adjOBEmpNetInterest);
			
			log.info("totalAAINet" + totalAAINet + "totalAaiIntNet"
					+ totalAaiIntNet + "aaiNetOB==========="
					+ aaiNetOB +"obAaiNetInterest" + obAaiNetInterest);
			
			log.info("aaiNetAdjOB====="
					+ aaiNetAdjOB + "adjOBAaiNetInterest====" + adjOBAaiNetInterest);
			
			log.info("empSubAdjOB" + empSubAdjOB + "adjOBEmpSubInterest"
					+ adjOBEmpSubInterest + "obEmpNetInterest" + obEmpNetInterest
					+ "totalEmpIntNet" + totalEmpIntNet);
	
			
			log.info("finalEmpNetOB" + finalEmpNetOB + "finalAaiNetOB"
					+ finalAaiNetOB + "finalPensionOB" + finalPensionOB
					+ "finalPrincipalOB" + finalPrincipalOB);

		}
	
		empNetInterest = adjOBEmpSubInterest + obEmpNetInterest
				+ new Double(totalEmpIntNet).longValue();
		aaiNetInterest = adjOBAaiNetInterest + obAaiNetInterest
				+ new Double(totalAaiIntNet).longValue();
		penContInterest = obAaiNetInterest
				+ new Double(penInterest).longValue();
		log.info("Final Interest===empNetInterest====================" + empNetInterest
				+ "aaiNetInterest=================" + aaiNetInterest);
		finalOBList.add(new Long(finalEmpNetOB));
		finalOBList.add(new Long(finalAaiNetOB));
		finalOBList.add(new Long(finalPensionOB));
		finalOBList.add(obMnthYear);
		finalOBList.add(new Long(empNetInterest));
		finalOBList.add(new Long(aaiNetInterest));
		finalOBList.add(new Long(penContInterest));
		finalOBList.add(new Long(finalPrincipalOB));
		log.info("==============================calobcard end============================================" );
		return finalOBList;
	}




	private ArrayList getOBForPFCardReport(Connection con, String monthYear,
			String pensionNo) {
		ArrayList list = new ArrayList();
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", obYear = "", obFlag = "", tempMonthYear = "";
		long empNetOB = 0, aaiNetOB = 0, pensionOB = 0, outstandOB = 0;
		tempMonthYear = "%" + monthYear.substring(2, monthYear.length());
		sqlQuery = "SELECT EMPNETOB,AAINETOB,PENSIONOB,OBFLAG,TO_CHAR(OBYEAR,'yyyy') AS YEAR,OUTSTANDADV FROM EMPLOYEE_PENSION_OB WHERE PENSIONNO="
				+ pensionNo.trim()
				+ " and to_char(OBYEAR,'dd-Mon-yyyy') like '"
				+ tempMonthYear
				+ "' and empflag='Y'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			log.info("getOBForPFCardReport===================sqlQuery==="
					+ sqlQuery);
			if (rs.next()) {
				empNetOB = rs.getLong("EMPNETOB");
				aaiNetOB = rs.getLong("AAINETOB");
				pensionOB = rs.getLong("PENSIONOB");

				obFlag = rs.getString("OBFLAG");
				outstandOB = rs.getLong("OUTSTANDADV");
				if (rs.getString("YEAR") != null) {
					obYear = rs.getString("YEAR");
				} else {
					obYear = "---";
				}
			}
			list.add(new Long(empNetOB));
			list.add(new Long(aaiNetOB));
			list.add(new Long(pensionOB));
			list.add(obFlag);
			list.add(obYear);
			list = this
					.getAdjOBForPFCardReport(con, monthYear, pensionNo, list);
			list.add(new Long(outstandOB));
			log.info("Size of Opening Balances" + list.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return list;
	}

	public int updatePFCardReport(String range, String region,
			String selectedYear, String empNameFlag, String empName,
			String sortedColumn, String pensionno, FileWriter fw,
			String chkPendingFlag,boolean procesingFlag,String stationName) {
		
		
		log.info("FinancialReportDAO::empPFCardReportPrint");
		String fromYear = "", toYear = "", dateOfRetriment = "";
		Connection con = null;
		if (!selectedYear.equals("Select One")) {
			fromYear = "01-Apr-" + selectedYear;
			int toSelectYear = 0;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Mar-2011";
		}
		String fromSelectedYear="-02-"+(Integer.parseInt(selectedYear) + 1);
		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeCardReportInfo cardInfo = null;
		ArrayList list = new ArrayList();
		ArrayList ptwList = new ArrayList();
		ArrayList finalSttmentList = new ArrayList();
		String appEmpNameQry = "", finalSettlementDate = "";
		ArrayList cardList = new ArrayList();
		boolean finalStFlag = false;
		int transInserted = 0, totalInserted = 0,arrerMonths=0;
		int formFrmYear = 0, formToYear = 0, finalSttlementDtYear = 0, formMonthYear = 0;
		try {
			con = commonDB.getConnection();
			if (chkPendingFlag.equals("true")) {
				empDataList = this.pendingPFIDS(con, range, region,
						empNameFlag, empName, sortedColumn, pensionno,fromYear,toYear);
			} else {
				empDataList = this.loadPFIDS(con, range, region, empNameFlag,
						empName, sortedColumn, pensionno,fromSelectedYear,stationName,fromYear,toYear);
			}

			try {
				formFrmYear = Integer.parseInt(commonUtil.converDBToAppFormat(
						fromYear, "dd-MMM-yyyy", "yyyy"));
				formToYear = Integer.parseInt(commonUtil.converDBToAppFormat(
						toYear, "dd-MMM-yyyy", "yyyy"));

			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidDataException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < empDataList.size(); i++) {
				cardInfo = new EmployeeCardReportInfo();
				personalInfo = (EmployeePersonalInfo) empDataList.get(i);
				try {
					dateOfRetriment = this.getRetriedDate(personalInfo
							.getDateOfBirth());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					log.printStackTrace(e);
				}
				if (!personalInfo.getFinalSettlementDate().equals("---")) {

					finalSttlementDtYear = Integer.parseInt(commonUtil
							.converDBToAppFormat(personalInfo
									.getFinalSettlementDate(), "dd-MMM-yyyy",
									"yyyy"));
					formMonthYear = Integer.parseInt(commonUtil
							.converDBToAppFormat(personalInfo
									.getFinalSettlementDate(), "dd-MMM-yyyy",
									"MM"));
					if ( finalSttlementDtYear<= formFrmYear) {
						finalStFlag = true;
						log.info("con1");
					} else if (formToYear == finalSttlementDtYear
							&& formMonthYear < 4) {
						finalStFlag = true;
						log.info("con2");
					} else {
						finalStFlag = false;
						log.info("con2 else");
					}
					log.info("formFrmYear" + formFrmYear + "formToYear"
							+ formToYear + "finalStFlag" + finalStFlag
							+ "formMonthYear" + formMonthYear);
					if (finalStFlag == true) {

						finalSettlementDate = commonUtil.converDBToAppFormat(
								personalInfo.getFinalSettlementDate(),
								"dd-MMM-yyyy", "MMM-yyyy");
					} else {
						personalInfo.setFinalSettlementDate("---");
						finalSettlementDate = "---";
					}
				} else {
					finalSettlementDate = "---";
				}

				if (!personalInfo.getFinalSettlementDate().equals("---")) {
					arrerMonths=this.getArrearInfo(con,fromYear, toYear,personalInfo.getOldPensionNo());
					log.info("Arrear+FinalSettlement"+commonUtil.compareTwoDates(
							finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
					if(commonUtil.compareTwoDates(
							finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy"))==true){
						personalInfo.setChkArrearFlag("N");
						log.info("personalInfo.getResttlmentDate() "+personalInfo.getResttlmentDate());
						if(!personalInfo.getResttlmentDate().equals("---")){
							cardInfo.setInterNoOfMonths(12);
							cardInfo.setNoOfMonths(this
									.numOfMnthFinalSettlement(commonUtil
											.converDBToAppFormat(personalInfo
													.getResttlmentDate(),
													"dd-MMM-yyyy", "MMM")));
						}else if(arrerMonths!=0){
							cardInfo.setInterNoOfMonths(12);
							cardInfo.setNoOfMonths(12);
						}else{
							cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
							cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
							
						}
							
					}else{
						personalInfo.setChkArrearFlag("Y");
						cardInfo.setNoOfMonths(this
								.numOfMnthFinalSettlement(commonUtil
										.converDBToAppFormat(personalInfo
												.getFinalSettlementDate(),
												"dd-MMM-yyyy", "MMM")));
						cardInfo.setArrearNoOfMonths(arrerMonths);
						cardInfo.setInterNoOfMonths(12);
					}
					
				} else {
					personalInfo.setChkArrearFlag("N");
					cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
					cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
				}
				log.info("Update PF Card====Final Settlement Date"+personalInfo.getFinalSettlementDate()+"fromYear"+fromYear+"NO.Of Months"+cardInfo.getNoOfMonths());
			         
				if(procesingFlag==true){
					this.updatePCReport("1-Apr-1995", "1-May-2008","NO-SELECT","NO-SELECT",personalInfo.getPensionNo(),"","","NO-SELECT","false");
				}
				//Added new  this below code on 26-Jul
				finalSttmentList = this.getFinalSettlement(con, fromYear,
						toYear, personalInfo.getPfIDString(), personalInfo
								.getOldPensionNo());
				
				transInserted = this.updatePFCardClosingBal(con, fromYear,
						toYear, personalInfo.getPfIDString(), personalInfo
								.getWetherOption(), personalInfo.getRegion(),
						true, dateOfRetriment, personalInfo.getDateOfBirth(),
						personalInfo.getOldPensionNo(), cardInfo
								.getNoOfMonths(), finalSettlementDate,"false",finalSttmentList,cardInfo
								.getInterNoOfMonths(),personalInfo.getChkArrearFlag(),personalInfo.getAdjDate(),personalInfo.getAdjAmount(),personalInfo.getAdjInt());



				totalInserted = totalInserted + transInserted;

				// Flag is not used in the last paramter of getPTWDetails method
				/*
				 * ptwList = this.getPTWDetails(con,fromYear, toYear,
				 * personalInfo .getCpfAccno(), "IAD");
				 */
				/*
				 * finalSttmentList=this.getFinalSettlement(con,fromYear,
				 * toYear, personalInfo .getPfIDString(),
				 * personalInfo.getOldPensionNo());
				 */
				cardInfo.setFinalSettmentList(finalSttmentList);
				cardInfo.setPersonalInfo(personalInfo);
				cardInfo.setPensionCardList(list);
				cardInfo.setPtwList(ptwList);
				cardList.add(cardInfo);
				try {
					this.processStsPFCards(con,personalInfo.getPensionNo());
					fw.write(commonUtil.leadingZeros(5, personalInfo
							.getPensionNo())
							+ "================"
							+ transInserted
							+ "========"
							+ personalInfo.getEmployeeName() + "\n");
					fw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.printStackTrace(e);
				}
			}

		} catch (Exception se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(con, null, null);
		}

		return totalInserted;
	}

	public int updateOBPensionCard(String fromDate, String toDate,
			String region, String fromSelPensionNo) {
		DecimalFormat df = new DecimalFormat("#########0.00");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		ArrayList closingList = new ArrayList();
		ArrayList finalSettlementList=new  ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", pfIDS = "", cpfaccno = "", transRegion = "", tempCpfaccno = "", calYear = "";
		String monthYear = "", days = "", sqlQuery = "", wetherOption = "", dateOfBirth = "", dateOfRetriment = "", month = "", pensionNo = "";
		int getDaysBymonth = 0, totalValues = 0, insertedValues = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, rateOfInterest = 0.0;
		double totalEmoluments = 0.0, pfStaturary = 0.0, empVpf = 0.0, principle = 0.0, interest = 0.0, empTotal = 0.0, totalEmpNet = 0.0, totalEmpIntNet = 0.0, totalAaiIntNet = 0.0;
		double totalAAIPF = 0.0, totalPFDrwn = 0.0, totalAAINet = 0.0, totalAdvancePFWPaid = 0.0, empNetInterest = 0.0, aaiNetInterest = 0.0, totalPensionContr = 0.0, penInterest = 0.0;
		double pensionAsPerOption = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0, loanPFWPaid = 0;
		boolean obFlag = false;
		sqlQuery = "SELECT TRANS.REGION,TRANS.MONTHYEAR AS MONTHYEAR,TO_CHAR(TRANS.MONTHYEAR,'Mon') AS MONTH,TRANS.CPFACCNO AS CPFACCNO,TRANS.EMOLUMENTS AS EMOLUMENTS,TRANS.EMPPFSTATUARY AS EMPPFSTATUARY,TRANS.EMPVPF AS EMPVPF,"
				+ "TRANS.CPF AS CPF,TRANS.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,TRANS.EMPADVRECINTEREST AS EMPADVRECINTEREST,TRANS.AAICONPF AS AAICONPF,"
				+ "TRANS.CPFADVANCE AS CPFADVANCE,TRANS.DEDADV AS DEDADV,TRANS.REFADV AS REFADV,EMPINFO.WETHEROPTION AS WETHEROPTION,EMPINFO.DATEOFBIRTH AS DATEOFBIRTH,"
				+ "EMPINFO.DATEOFJOINING AS DATEOFJOINING,EMPINFO.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPINFO.EMPSERIALNUMBER AS EMPSERIALNUMBER FROM (SELECT MONTHYEAR,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "EMPPFSTATUARY,EMPVPF,CPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,AAICONPF,CPFADVANCE,DEDADV,REFADV,AIRPORTCODE,REGION,CPFACCNO "
				+ "FROM EMPLOYEE_PENSION_VALIDATE  WHERE (TO_DATE(TO_CHAR(MONTHYEAR, 'DD-MON-YYYY')) >= '"
				+ fromDate
				+ "' AND "
				+ "TO_DATE(TO_CHAR(MONTHYEAR, 'DD-MON-YYYY')) <= '"
				+ toDate
				+ "') AND EMPFLAG = 'Y') TRANS,(SELECT REGION, CPFACNO, WETHEROPTION,DATEOFBIRTH,"
				+ "DATEOFJOINING,EMPSERIALNUMBER FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM EMPLOYEE_PERSONAL_INFO "
				+ "WHERE REGION = '"
				+ region
				+ "' AND PENSIONNO="
				+ fromSelPensionNo
				+ ")) EMPINFO WHERE EMPINFO.REGION = TRANS.REGION AND EMPINFO.CPFACNO = TRANS.CPFACCNO ORDER BY TRANS.CPFACCNO, MONTHYEAR";

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		long obEmpNet = 0, obAaiNetOB = 0, obPensionOB = 0;
		String empNetOB = "", aaiNetOB = "", pensionOB = "", obFromTrFlag = "", tempPensioNo = "";
		ArrayList OBList = new ArrayList();
		ArrayList finalOBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {

				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("EMPSERIALNUMBER") != null) {
					pensionNo = rs.getString("EMPSERIALNUMBER");

				}
				if (rs.getString("CPFACCNO") != null) {
					cpfaccno = rs.getString("CPFACCNO");
				}
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
				} else {
					cardInfo.setMonthyear("---");
					cardInfo.setShnMnthYear("---");
				}
				if (rs.getString("MONTH") != null) {
					month = rs.getString("MONTH");
					if (month.toLowerCase().equals("apr")) {
						obFlag = false;
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
				}

				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					calYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "yyyy");
					obFlag = true;
				}

				if (rs.getString("WETHEROPTION") != null) {
					wetherOption = rs.getString("WETHEROPTION");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("---");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = commonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy");
				}

				if (rs.getString("REGION") != null) {
					transRegion = rs.getString("REGION");
				}

				pfIDS = "(CPFACCNO='" + cpfaccno + "' AND REGION='"
						+ transRegion + "')";
				try {
					dateOfRetriment = this.getRetriedDate(dateOfBirth);
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}

				if (transRegion.equals("North Region")) {
					if (rs.getString("REFADV") != null) {
						cardInfo.setPrincipal(rs.getString("REFADV"));
					} else {
						cardInfo.setPrincipal("0");
					}
				} else {
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();

				if (region.equals("CHQIAD")) {

					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("Region" + region + "loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				} else if (region.equals("CHQNAD")) {
					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				} else if (region.equals("North Region")) {
					if (rs.getString("DEDADV") != null) {
						advancePFWPaid = Double.parseDouble(rs
								.getString("DEDADV"));
						totalAdvancePFWPaid = advancePFWPaid;
					} else {
						advancePFWPaid = 0;
						totalAdvancePFWPaid = advancePFWPaid;
					}

				}

				empNet = total - totalAdvancePFWPaid;
				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;

				log.info("EMP PFS==========================="
						+ cardInfo.getEmppfstatury() + "EMP VPF"
						+ cardInfo.getEmpvpf().trim());
				log.info("EMP Principal==========================="
						+ cardInfo.getPrincipal().trim() + "Interest"
						+ cardInfo.getInterest().trim());
				log.info("total===========================" + total
						+ "advancePFWPaid" + advancePFWPaid);
				log.info("empCumlative==========================="
						+ empCumlative);
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				if (flag == false) {
					if (!cardInfo.getMonthyear().equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(cardInfo.getMonthyear());
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == empRetriedDt) {
							chkDOBFlag = true;
						}
					}
					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					if (contrFlag != true) {
						pensionAsPerOption = this.pensionCalculation(cardInfo
								.getMonthyear(), cardInfo.getEmoluments(),
								wetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				} else {
					cardInfo.setPensionContribution("0");
				}
				log.info("flag" + flag + checkDate + "Pension"
						+ cardInfo.getPensionContribution());

				if (region.equals("CHQIAD")) {
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				} else if (region.equals("CHQNAD")) {
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				} else if (region.equals("North Region")) {
					advPFDrawn = 0;
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				log.info("aaiPF" + aaiPF + "aaiNet" + aaiNet
						+ "pensionAsPerOption" + pensionAsPerOption
						+ "advPFDrawn" + advPFDrawn);
				log.info("aaiNetCumlative" + aaiNetCumlative);

				totalEmoluments = new Double(df.format(totalEmoluments
						+ Math.round(Double.parseDouble(cardInfo
								.getEmoluments())))).doubleValue();
				pfStaturary = new Double(df.format(pfStaturary
						+ Math.round(Double.parseDouble(cardInfo
								.getEmppfstatury())))).doubleValue();
				empVpf = new Double(df.format(empVpf
						+ Math.round(Double.parseDouble(cardInfo.getEmpvpf()))))
						.doubleValue();
				principle = new Double(df.format(principle
						+ Math.round(Double
								.parseDouble(cardInfo.getPrincipal()))))
						.doubleValue();
				interest = new Double(df.format(interest
						+ Math
								.round(Double.parseDouble(cardInfo
										.getInterest())))).doubleValue();
				empTotal = new Double(df.format(empTotal
						+ Math
								.round(Double.parseDouble(cardInfo
										.getEmptotal())))).doubleValue();
				totalAdvancePFWPaid = new Double(df.format(totalAdvancePFWPaid
						+ Math.round(Double.parseDouble(cardInfo
								.getAdvancePFWPaid())))).doubleValue();
				totalEmpNet = new Double(df.format(totalEmpNet
						+ Math.round(Double.parseDouble(cardInfo
								.getEmpNetCummulative())))).doubleValue();

				totalAAIPF = new Double(df.format(totalAAIPF
						+ Math.round(Double.parseDouble(cardInfo.getAaiPF()))))
						.doubleValue();
				totalPFDrwn = new Double(df
						.format(totalPFDrwn
								+ Math.round(Double.parseDouble(cardInfo
										.getPfDrawn())))).doubleValue();
				totalAAINet = new Double(df.format(totalAAINet
						+ Math.round(Double.parseDouble(cardInfo
								.getAaiCummulative())))).doubleValue();
				totalPensionContr = new Double(df.format(totalPensionContr
						+ Math.round(Double.parseDouble(cardInfo
								.getPensionContribution())))).doubleValue();
				log.info("EmpNet Cummalitve" + cardInfo.getEmpNetCummulative());

			}
			log.info(empNetOB + "," + aaiNetOB + "," + pensionOB + ","
					+ obFromTrFlag.trim());
			log
					.info("===============================Data========================================================================================");
			log.info(pfStaturary + "," + empVpf + "," + principle + ","
					+ interest + "," + empTotal + "," + totalAdvancePFWPaid
					+ "," + totalEmpNet + "," + totalAAINet + ","
					+ totalPensionContr);
			log
					.info("===============================Interest========================================================================================");

			if (OBList.size() != 0) {
				CommonDAO commonDAO = new CommonDAO();
				rateOfInterest = commonDAO.getRateOfInterest(calYear);
				empNetInterest = new Double(df
						.format(((totalEmpNet * rateOfInterest) / 100)))
						.doubleValue();
				aaiNetInterest = new Double(df
						.format(((totalAAINet * rateOfInterest) / 100)))
						.doubleValue();
				penInterest = new Double(df
						.format(((totalPensionContr * rateOfInterest) / 100)))
						.doubleValue();
				log
						.info("empNetInterest===" + empNetInterest
								+ "aaiNetInterest" + aaiNetInterest + ","
								+ penInterest);
				finalOBList = this.calOBForPFCardReport(rateOfInterest, OBList,
						Math.round(totalAAINet), Math.round(aaiNetInterest),
						Math.round(totalEmpNet), Math.round(empNetInterest),
						Math.round(totalPensionContr), Math.round(penInterest));
				for (int j = 0; j < finalOBList.size(); j++) {
					log.info("Value From final OBList" + finalOBList.get(j));
				}
				insertedValues = this.insertOBInfo(fromSelPensionNo, con,
						finalOBList, closingList,finalSettlementList,rateOfInterest,12,0.0,0.0,"0.00","---",0.0,0.0);
			} else {
				insertedValues = 0;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return insertedValues;
	}

	public Form8RemittanceBean getForm6RemittanceInfo(String fromDate,
			String toDate, String region, String pensionNo, boolean FormFlag,
			boolean secondFormFlag) {
		log.info("FinanceReportDAO::getEmployeePensionInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Form8RemittanceBean remittanceBean = new Form8RemittanceBean();
		String emoluments = "", pfStatury = "", vpf = "", cpf = "", monthYear = "", month = "", toDateString = "";
		int subscribersCnt = 0;
		double pensionVal = 0.0, totalPFStatury = 0.0, totalVPF = 0.0, totalCPF = 0.0, totalContribution = 0.0;
		int totalAprCnt = 0, totalMayCnt = 0, totalJunCnt = 0, totalJulCnt = 0, totalAugCnt = 0, totalSepCnt = 0, totalOctCnt = 0, totalNovCnt = 0;
		int totalDecCnt = 0, totalJanCnt = 0, totalFebCnt = 0, totalMarCnt = 0;
		double totalAprPFStatury = 0.0, totalAprVPF = 0.0, totalAprCPF = 0.0, totalAprContribution = 0.0;
		double totalMayPFStatury = 0.0, totalMayVPF = 0.0, totalMayCPF = 0.0, totalMayContribution = 0.0;
		double totalJunPFStatury = 0.0, totalJunVPF = 0.0, totalJunCPF = 0.0, totalJunContribution = 0.0;
		double totalJulPFStatury = 0.0, totalJulVPF = 0.0, totalJulCPF = 0.0, totalJulContribution = 0.0;
		double totalAugPFStatury = 0.0, totalAugVPF = 0.0, totalAugCPF = 0.0, totalAugContribution = 0.0;
		double totalSepPFStatury = 0.0, totalSepVPF = 0.0, totalSepCPF = 0.0, totalSepContribution = 0.0;
		double totalOctPFStatury = 0.0, totalOctVPF = 0.0, totalOctCPF = 0.0, totalOctContribution = 0.0;
		double totalNovPFStatury = 0.0, totalNovVPF = 0.0, totalNovCPF = 0.0, totalNovContribution = 0.0;
		double totalDecPFStatury = 0.0, totalDecVPF = 0.0, totalDecCPF = 0.0, totalDecContribution = 0.0;
		double totalJanPFStatury = 0.0, totalJanVPF = 0.0, totalJanCPF = 0.0, totalJanContribution = 0.0;
		double totalFebPFStatury = 0.0, totalFebVPF = 0.0, totalFebCPF = 0.0, totalFebContribution = 0.0;
		double totalMarPFStatury = 0.0, totalMarVPF = 0.0, totalMarCPF = 0.0, totalMarContribution = 0.0, retriredEmoluments = 0.0;

		double totalAprEmoluments = 0, totalEmoluments = 0;
		double totalMayEmoluments = 0;
		double totalJunEmoluments = 0;
		double totalJulEmoluments = 0;
		double totalAugEmoluments = 0;
		double totalSepEmoluments = 0;
		double totalOctEmoluments = 0;
		double totalNovEmoluments = 0;
		double totalNovOptionAEmoluments = 0;
		double totalNovOptionBEmoluments = 0, totalAprOptionAEmoluments = 0.0, totalAprOptionBEmoluments = 0.0, totalMayOptionAEmoluments = 0.0, totalMayOptionBEmoluments = 0.0, totalJunOptionAEmoluments = 0.0, totalJunOptionBEmoluments = 0.0, totalJulOptionAEmoluments = 0.0, totalJulOptionBEmoluments = 0.0;
		double totalAugOptionAEmoluments = 0.0, totalAugOptionBEmoluments = 0.0, totalSepOptionAEmoluments = 0.0, totalSepOptionBEmoluments = 0.0, totalOctOptionAEmoluments = 0.0, totalOctOptionBEmoluments = 0.0, totalDecOptionAEmoluments = 0.0, totalDecOptionBEmoluments = 0.0;
		double totalJanOptionAEmoluments = 0.0, totalJanOptionBEmoluments = 0.0, totalFebOptionAEmoluments = 0.0, totalFebOptionBEmoluments = 0.0, totalMarOptionAEmoluments = 0.0, totalMarOptionBEmoluments = 0.0;
		double totalDecEmoluments = 0;
		double totalJanEmoluments = 0;
		double totalFebEmoluments = 0;
		double totalMarEmoluments = 0;
		String airportCode = "", aprDispMnth = "", mayDispMnth = "", junDispMnth = "", julDispMnth = "", augDispMnth = "", sepDispMnth = "", octDispMnth = "", novDispMnth = "", decDispMnth = "", janDispMnth = "", febDispMnth = "", marDispMnth = "";
		String wetherOption = "", pensionNoQry = "", iadAirportString = "", days = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;

		if (!pensionNo.equals("")) {
			pensionNoQry = " AND PENSIONNO='" + pensionNo + "'";
		}
		if (region.indexOf("-") != -1) {
			String regionsList[] = region.split("-");
			if (regionsList[0].equals("IAD")) {
				region = "";
				region = "CHQIAD";
				airportCode = regionsList[1];
				iadAirportString = " AND AIRPORTCODE='" + airportCode + "'";
			}
		}
		String[] todtArray = toDate.split("-");
		if (todtArray[0].equals("16")) {
			toDateString = " AND '" + toDate + "'";
		} else {
			toDateString = " AND LAST_DAY('" + toDate + "')";
		}
		String findFromYear="",findToYear="",sqlQuery="";
		boolean yearBreakMonthFlag=false;
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			yearBreakMonthFlag = true;
			 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
					+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS FROM "
					+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
					+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between  add_months('"
					+ fromDate
					+ "',1) and LAST_DAY( add_months('"
					+ toDate
					+ "',1)) ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
					+ " EMPLOYEE_PERSONAL_INFO WHERE   FORMSDISABLE='Y' AND ((round(months_between(add_months('"+ fromDate+ "',1),add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE(add_months('"
					+ fromDate
					+ "',1)))) "
					
					+ pensionNoQry
					
					+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";

		}else{
			sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPSERIALNUMBER as EMPSERIALNUMBER,EMPFID.WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
				+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS FROM "
				+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
				+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,EMOLUMENTMONTHS FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 and emoluments!=0.0) and monthyear between add_months('"
				+ fromDate
				+ "',1) and LAST_DAY('"
				+ toDate
				+ "') ) EPV  ,(SELECT REGION , CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
				+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND( NVL(DATEOFJOINING,'01-Mar-2009')) <='01-Mar-2009' AND REGION = '"
				+ region
				+ "' "
				+ pensionNoQry
				+ iadAirportString
				+ " )) EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";

		}
		
		log.info("FinanceReportDAO::getForm6RemittanceInfo" + sqlQuery);
		DecimalFormat df = new DecimalFormat("#########0");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		String totalContr = "", disYear = "", transRegion = "", calEmoluments = "", dateOfBirth = "", dateOfRetriment = "";
		String checkDate = "",emolumentMonths="", editTransFlag="",arrearFlag="",chkMnthYear = "Apr-1995", pensionNos = "", frmCpfaccno = "", tempSerialNumber = "", serialNumber = "";
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false, mergeFlag = false;
		try {
			con = commonDB.getConnection();

			st = con.createStatement();
			st.setFetchSize(100);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				flag = false;
				contrFlag = false;
				chkDOBFlag = false;
				try {
					month = commonUtil.converDBToAppFormat(fromDate,
							"dd-MMM-yyyy", "MMM");
				} catch (InvalidDataException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if (rs.getString("MNTYEAR") != null) {
					disYear = rs.getString("MNTYEAR");
					try {
						disYear = commonUtil.converDBToAppFormat(fromDate,
								"dd-MMM-yyyy", "MMM-yyyy");
					} catch (InvalidDataException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} else {
					disYear = "---";
				}
				if (rs.getString("EMPSERIALNUMBER") != null) {
					serialNumber = rs.getString("EMPSERIALNUMBER");
					if (!tempSerialNumber.equals("")) {
						if (serialNumber.trim().equals(tempSerialNumber.trim())) {
							mergeFlag = true;
						} else {
							mergeFlag = false;
						}
						tempSerialNumber = "";
					} else {
						tempSerialNumber = "";
						mergeFlag = false;
					}

				}
				// log.info("serialNumber"+serialNumber+"==="+mergeFlag);
				if (rs.getString("CPFACCNO") != null) {
					frmCpfaccno = rs.getString("CPFACCNO");
				}

				if (rs.getString("WETHEROPTION") != null) {
					wetherOption = rs.getString("WETHEROPTION");
				}
				if (rs.getString("REGION") != null) {
					transRegion = rs.getString("REGION");
				}

				if (rs.getString("MONTHYEAR") != null) {
					monthYear = commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy");

				}
				if (rs.getString("EDITTRANS") != null) {
					editTransFlag = rs.getString("EDITTRANS");
				} 
				if (rs.getString("ARREARFLAG") != null) {
					arrearFlag = rs.getString("ARREARFLAG");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					emoluments = rs.getString("EMOLUMENTS");
				} else {
					emoluments = "0.00";
				}
				if (rs.getString("EMOLUMENTMONTHS") != null) {
					emolumentMonths=rs.getString("EMOLUMENTMONTHS".trim());
				} else {
					emolumentMonths="1";
				}
				if (rs.getString("dateofbirth") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}

				if (!dateOfBirth.equals("---")) {
					try {
						dateOfRetriment = this.getRetriedDate(dateOfBirth);
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					dateOfRetriment = "";
				}

				try {
					checkDate = commonUtil.converDBToAppFormat(monthYear,
							"dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				calEmoluments = this.calWages(emoluments, monthYear,
						wetherOption.trim(), false, false,emolumentMonths);
		
				// calEmoluments=emoluments;
				if (rs.getString("EMPPFSTATUARY") != null) {
					pfStatury = rs.getString("EMPPFSTATUARY");
				} else {
					pfStatury = "0.00";
				}
				if (rs.getString("EMPVPF") != null) {
					vpf = rs.getString("EMPVPF");
				} else {
					vpf = "0";
				}
				if (rs.getString("CPF") != null) {
					cpf = rs.getString("CPF");
				} else {
					cpf = "0";
				}
				if (rs.getString("EMOLUMENTMONTHS") != null) {
					emolumentMonths=rs.getString("EMOLUMENTMONTHS".trim());
				} else {
					emolumentMonths="1";
				}
				if (!monthYear.equals("-NA-")
						&& !dateOfRetriment.equals("")) {
					transMntYear = Date.parse(monthYear);
					empRetriedDt = Date.parse(dateOfRetriment);

					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == 0
							|| empRetriedDt == 0) {
						contrFlag = false;
					} else if (transMntYear == empRetriedDt
							&& transMntYear != 0
							&& empRetriedDt != 0) {
						chkDOBFlag = true;
					}
				}

				if(yearBreakMonthFlag==false){
					if (!checkDate.equals("Dec-1995")) {
						if (flag == false) {
							
							if (contrFlag != true) {
								// log.info("frmEmployeeName"+frmEmployeeName);
								pensionVal = this.pensionFormsCalculation(
										monthYear, calEmoluments, wetherOption
												.trim(), region, false, false,emolumentMonths);

								if (chkDOBFlag == true) {
									String[] dobList = dateOfBirth.split("-");
									days = dobList[0];
									getDaysBymonth = this
											.getNoOfDays(dateOfBirth);
									pensionVal = pensionVal
											* (Double.parseDouble(days) - 1)
											/ getDaysBymonth;
									retriredEmoluments = new Double(
											df1
													.format(Double
															.parseDouble(calEmoluments)
															* (Double
																	.parseDouble(days) - 1)
															/ getDaysBymonth))
											.doubleValue();
									calEmoluments = Double
											.toString(retriredEmoluments);
								}

							} else {
								pensionVal = 0.0;
								calEmoluments = "0";
								}

						} else {
							pensionVal = 0;
						}
					}
				}else{
					if(rs.getString("PENSIONCONTRI")!=null){
						pensionVal=Double.parseDouble(rs.getString("PENSIONCONTRI"));
					}else{
						pensionVal = 0.0;
					}
					//log.info("available data is editTransFlag"+editTransFlag+"checkDate"+checkDate);
					//log.info("available data is contrFlag"+contrFlag+"checkDate"+chkDOBFlag);
					if (contrFlag != true) {
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
						
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}
						}else {
						//	log.info("available data is"+editTransFlag+"checkDate"+checkDate);
							if(arrearFlag.equals("N")){
								if(!(editTransFlag.equals("Y") && checkDate.toUpperCase().equals("SEP-2009"))){
									pensionVal = 0.0;
									calEmoluments = "0";
								}
							
							}
								
						}
				
				}
		

		
				

				emoluments = Long.toString(Math.round(Double.parseDouble(calEmoluments.trim())));

			/*log.info("monthYear==" + monthYear + "==Empserialnumber==="
						+ commonUtil.leadingZeros(20, serialNumber)
						+ "emoluments===="
						+ commonUtil.leadingZeros(20, emoluments)
						+ "pensionVal====" + pensionVal);*/
				calEmoluments = "";
				if (pensionVal != 0.0 || pensionVal != 0) {
					if (month.toUpperCase().equals("APR")) {
						aprDispMnth = disYear;
						totalAprEmoluments = totalAprEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalAprOptionAEmoluments = totalAprOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalAprOptionBEmoluments = totalAprOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalAprPFStatury = totalAprPFStatury
								+ Double.parseDouble(pfStatury);
						totalAprVPF = totalAprVPF + Double.parseDouble(vpf);
						totalAprCPF = totalAprCPF + Double.parseDouble(cpf);
						totalAprContribution = totalAprContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalAprCnt++;
						}

					}
					if (month.toUpperCase().equals("MAY")) {
						mayDispMnth = disYear;
						totalMayEmoluments = totalMayEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalMayOptionAEmoluments = totalMayOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalMayOptionBEmoluments = totalMayOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalMayPFStatury = totalMayPFStatury
								+ Double.parseDouble(pfStatury);
						totalMayVPF = totalMayVPF + Double.parseDouble(vpf);
						totalMayCPF = totalMayCPF + Double.parseDouble(cpf);
						totalMayContribution = totalMayContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalMayCnt++;
						}

					}

					if (month.toUpperCase().equals("JUN")) {
						junDispMnth = disYear;
						totalJunEmoluments = totalJunEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalJunOptionAEmoluments = totalJunOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalJunOptionBEmoluments = totalJunOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalJunPFStatury = totalJunPFStatury
								+ Double.parseDouble(pfStatury);
						totalJunVPF = totalJunVPF + Double.parseDouble(vpf);
						totalJunCPF = totalJunCPF + Double.parseDouble(cpf);
						totalJunContribution = totalJunContribution
								+ Math.round(pensionVal);

						if (mergeFlag == false) {
							totalJunCnt++;
						}

					}
					if (month.toUpperCase().equals("JUL")) {
						julDispMnth = disYear;
						if (transRegion.trim().equals("CHQIAD")) {
							totalJulOptionAEmoluments = totalJulOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalJulOptionBEmoluments = totalJulOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalJulEmoluments = totalJulEmoluments
								+ Double.parseDouble(emoluments);
						totalJulPFStatury = totalJulPFStatury
								+ Double.parseDouble(pfStatury);
						totalJulVPF = totalJulVPF + Double.parseDouble(vpf);
						totalJulCPF = totalJulCPF + Double.parseDouble(cpf);
						totalJulContribution = totalJulContribution
								+ Math.round(pensionVal);

						if (mergeFlag == false) {
							totalJulCnt++;
						}

					}

					if (month.toUpperCase().equals("AUG")) {
						augDispMnth = disYear;
						totalAugEmoluments = totalAugEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalAugOptionAEmoluments = totalAugOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalAugOptionBEmoluments = totalAugOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalAugPFStatury = totalAugPFStatury
								+ Double.parseDouble(pfStatury);
						totalAugVPF = totalAugVPF + Double.parseDouble(vpf);
						totalAugCPF = totalAugCPF + Double.parseDouble(cpf);
						totalAugContribution = totalAugContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalAugCnt++;
						}

					}
					if (month.toUpperCase().equals("SEP")) {
						sepDispMnth = disYear;
						if (transRegion.trim().equals("CHQIAD")) {
							totalSepOptionAEmoluments = totalSepOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalSepOptionBEmoluments = totalSepOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalSepEmoluments = totalSepEmoluments
								+ Double.parseDouble(emoluments);
						totalSepPFStatury = totalSepPFStatury
								+ Double.parseDouble(pfStatury);
						totalSepVPF = totalSepVPF + Double.parseDouble(vpf);
						totalSepCPF = totalSepCPF + Double.parseDouble(cpf);
						totalSepContribution = totalSepContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalSepCnt++;
						}

					}
					if (month.toUpperCase().equals("OCT")) {
						octDispMnth = disYear;
						totalOctEmoluments = totalOctEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalOctOptionAEmoluments = totalOctOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalOctOptionBEmoluments = totalOctOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalOctPFStatury = totalOctPFStatury
								+ Double.parseDouble(pfStatury);
						totalOctVPF = totalOctVPF + Double.parseDouble(vpf);
						totalOctCPF = totalOctCPF + Double.parseDouble(cpf);
						totalOctContribution = totalOctContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalOctCnt++;
						}

					}

					if (month.toUpperCase().equals("NOV")) {
						novDispMnth = disYear;
						if (secondFormFlag) {
							if (transRegion.trim().equals("CHQIAD")) {
								totalNovOptionAEmoluments = totalNovOptionAEmoluments
										+ Double.parseDouble(emoluments);
							} else {
								totalNovOptionBEmoluments = totalNovOptionBEmoluments
										+ Double.parseDouble(emoluments);
							}

						} else if (secondFormFlag == false) {
							if (transRegion.trim().equals("CHQIAD")) {
								totalNovOptionAEmoluments = totalNovOptionAEmoluments
										+ Double.parseDouble(emoluments);
							} else {
								totalNovOptionBEmoluments = totalNovOptionBEmoluments
										+ Double.parseDouble(emoluments);
							}
						}
						totalNovEmoluments = totalNovEmoluments
								+ Double.parseDouble(emoluments);

						totalNovPFStatury = totalNovPFStatury
								+ Double.parseDouble(pfStatury);
						totalNovVPF = totalNovVPF + Double.parseDouble(vpf);
						totalNovCPF = totalNovCPF + Double.parseDouble(cpf);

						totalNovContribution = totalNovContribution
								+ Math.round(pensionVal);
						// log.info(pensionVal+" "+totalNovContribution);
						if (mergeFlag == false) {
							totalNovCnt++;
						}

					}
					if (month.toUpperCase().equals("DEC")) {
						decDispMnth = disYear;
						totalDecEmoluments = totalDecEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalDecOptionAEmoluments = totalDecOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalDecOptionBEmoluments = totalDecOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalDecPFStatury = totalDecPFStatury
								+ Double.parseDouble(pfStatury);
						totalDecVPF = totalDecVPF + Double.parseDouble(vpf);
						totalDecCPF = totalDecCPF + Double.parseDouble(cpf);
						totalDecContribution = totalDecContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalDecCnt++;
						}

					}
					if (month.toUpperCase().equals("JAN")) {
						janDispMnth = disYear;
						totalJanEmoluments = totalJanEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalJanOptionAEmoluments = totalJanOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalJanOptionBEmoluments = totalJanOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalJanPFStatury = totalJanPFStatury
								+ Double.parseDouble(pfStatury);
						totalJanVPF = totalJanVPF + Double.parseDouble(vpf);
						totalJanCPF = totalJanCPF + Double.parseDouble(cpf);
						totalJanContribution = totalJanContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalJanCnt++;
						}

					}
					if (month.toUpperCase().equals("FEB")) {
						febDispMnth = disYear;
						totalFebEmoluments = totalFebEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalFebOptionAEmoluments = totalFebOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalFebOptionBEmoluments = totalFebOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalFebPFStatury = totalFebPFStatury
								+ Double.parseDouble(pfStatury);
						totalFebVPF = totalFebVPF + Double.parseDouble(vpf);
						totalFebCPF = totalFebCPF + Double.parseDouble(cpf);
						totalFebContribution = totalFebContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalFebCnt++;
						}

					}
					if (month.toUpperCase().equals("MAR")) {
						marDispMnth = disYear;
						totalMarEmoluments = totalMarEmoluments
								+ Double.parseDouble(emoluments);
						if (transRegion.trim().equals("CHQIAD")) {
							totalMarOptionAEmoluments = totalMarOptionAEmoluments
									+ Double.parseDouble(emoluments);
						} else {
							totalMarOptionBEmoluments = totalMarOptionBEmoluments
									+ Double.parseDouble(emoluments);
						}
						totalMarPFStatury = totalMarPFStatury
								+ Double.parseDouble(pfStatury);
						totalMarVPF = totalMarVPF + Double.parseDouble(vpf);
						totalMarCPF = totalMarCPF + Double.parseDouble(cpf);
						totalMarContribution = totalMarContribution
								+ Math.round(pensionVal);
						if (mergeFlag == false) {
							totalMarCnt++;
						}

					}
				}
				pensionVal = 0.0;
				emoluments = "";
				pfStatury = "";
				dateOfRetriment = "";
				tempSerialNumber = serialNumber;
			}

			totalContribution = totalAprContribution + totalMayContribution
					+ totalJunContribution + totalJulContribution
					+ totalAugContribution + totalSepContribution;
			totalContribution = totalContribution + totalOctContribution
					+ totalNovContribution + totalDecContribution;
			totalContribution = totalContribution + totalJanContribution
					+ totalFebContribution + totalMarContribution;

			totalEmoluments = totalAprEmoluments + totalMayEmoluments
					+ totalJunEmoluments + totalJulEmoluments
					+ totalAugEmoluments + totalSepEmoluments;
			totalEmoluments = totalEmoluments + totalOctEmoluments
					+ totalNovEmoluments + totalDecEmoluments;
			totalEmoluments = totalEmoluments + totalJanEmoluments
					+ totalFebEmoluments + totalMarEmoluments;

			
			remittanceBean.setTotalAprContribution(new Double(df.format(Math
					.round(totalAprContribution))).doubleValue());
			remittanceBean.setTotalAprEmoluments(new Long(df.format(Math
					.round(totalAprEmoluments))).longValue());
			remittanceBean.setAprMnth(aprDispMnth);
			remittanceBean.setAprCnt(totalAprCnt);
			remittanceBean.setTotalMayContribution(new Double(df.format(Math
					.round(totalMayContribution))).doubleValue());
			remittanceBean.setTotalMayEmoluments(new Long(df.format(Math
					.round(totalMayEmoluments))).longValue());
			remittanceBean.setMayCnt(totalMayCnt);
			remittanceBean.setMayMnth(mayDispMnth);
			remittanceBean.setTotalJunContribution(new Double(df.format(Math
					.round(totalJunContribution))).doubleValue());
			remittanceBean.setTotalJunEmoluments(new Long(df.format(Math
					.round(totalJunEmoluments))).longValue());
			remittanceBean.setJunCnt(totalJunCnt);
			remittanceBean.setJunMnth(junDispMnth);
			remittanceBean.setTotalJulContribution(new Double(df.format(Math
					.round(totalJulContribution))).doubleValue());
			remittanceBean.setTotalJulEmoluments(new Long(df.format(Math
					.round(totalJulEmoluments))).longValue());
			remittanceBean.setJulCnt(totalJulCnt);
			remittanceBean.setJulMnth(julDispMnth);
			remittanceBean.setTotalAugContribution(new Double(df.format(Math
					.round(totalAugContribution))).doubleValue());
			remittanceBean.setTotalAugEmoluments(new Long(df.format(Math
					.round(totalAugEmoluments))).longValue());
			remittanceBean.setAugCnt(totalAugCnt);
			remittanceBean.setAugMnth(augDispMnth);
			remittanceBean.setTotalSepContribution(new Double(df.format(Math
					.round(totalSepContribution))).doubleValue());
			remittanceBean.setTotalSepEmoluments(new Long(df.format(Math
					.round(totalSepEmoluments))).longValue());
			remittanceBean.setSepCnt(totalSepCnt);
			remittanceBean.setSepMnth(sepDispMnth);
			remittanceBean.setTotalOctContribution(new Double(df.format(Math
					.round(totalOctContribution))).doubleValue());
			remittanceBean.setTotalOctEmoluments(new Long(df.format(Math
					.round(totalOctEmoluments))).longValue());
			remittanceBean.setOctCnt(totalOctCnt);
			remittanceBean.setOctMnth(octDispMnth);
			remittanceBean.setTotalNovContribution(new Double(df.format(Math
					.round(totalNovContribution))).doubleValue());
			if (novDispMnth.equals("Nov-1995")) {
				totalNovEmoluments = totalNovEmoluments / 2;
				totalNovOptionAEmoluments = totalNovOptionAEmoluments / 2;
				totalNovOptionBEmoluments = totalNovOptionBEmoluments / 2;
				// log.info("total emoluments is " + totalNovEmoluments);
			}

			// for Option A and B Emoluemnts
			remittanceBean.setTotalNovOptionAEmoluments(new Long(df.format(Math
					.round(totalNovOptionAEmoluments))).longValue());
			remittanceBean.setTotalNovOptionBEmoluments(new Long(df.format(Math
					.round(totalNovOptionBEmoluments))).longValue());
			remittanceBean.setTotalDecOptionAEmoluments(new Long(df.format(Math
					.round(totalDecOptionAEmoluments))).longValue());
			remittanceBean.setTotalDecOptionBEmoluments(new Long(df.format(Math
					.round(totalDecOptionBEmoluments))).longValue());
			remittanceBean.setTotalJanOptionAEmoluments(new Long(df.format(Math
					.round(totalJanOptionAEmoluments))).longValue());
			remittanceBean.setTotalJanOptionBEmoluments(new Long(df.format(Math
					.round(totalJanOptionBEmoluments))).longValue());
			remittanceBean.setTotalFebOptionAEmoluments(new Long(df.format(Math
					.round(totalFebOptionAEmoluments))).longValue());
			remittanceBean.setTotalFebOptionBEmoluments(new Long(df.format(Math
					.round(totalFebOptionBEmoluments))).longValue());
			remittanceBean.setTotalMarOptionAEmoluments(new Long(df.format(Math
					.round(totalMarOptionAEmoluments))).longValue());
			remittanceBean.setTotalMarOptionBEmoluments(new Long(df.format(Math
					.round(totalMarOptionBEmoluments))).longValue());
			remittanceBean.setTotalAprOptionAEmoluments(new Long(df.format(Math
					.round(totalAprOptionAEmoluments))).longValue());
			remittanceBean.setTotalAprOptionBEmoluments(new Long(df.format(Math
					.round(totalAprOptionBEmoluments))).longValue());
			remittanceBean.setTotalMayOptionAEmoluments(new Long(df.format(Math
					.round(totalMayOptionAEmoluments))).longValue());
			remittanceBean.setTotalMayOptionBEmoluments(new Long(df.format(Math
					.round(totalMayOptionBEmoluments))).longValue());
			remittanceBean.setTotalJunOptionAEmoluments(new Long(df.format(Math
					.round(totalJunOptionAEmoluments))).longValue());
			remittanceBean.setTotalJunOptionBEmoluments(new Long(df.format(Math
					.round(totalJunOptionBEmoluments))).longValue());
			remittanceBean.setTotalJulOptionAEmoluments(new Long(df.format(Math
					.round(totalJulOptionAEmoluments))).longValue());
			remittanceBean.setTotalJulOptionBEmoluments(new Long(df.format(Math
					.round(totalJulOptionBEmoluments))).longValue());
			remittanceBean.setTotalAugOptionAEmoluments(new Long(df.format(Math
					.round(totalAugOptionAEmoluments))).longValue());
			remittanceBean.setTotalAugOptionBEmoluments(new Long(df.format(Math
					.round(totalAugOptionBEmoluments))).longValue());
			remittanceBean.setTotalSepOptionAEmoluments(new Long(df.format(Math
					.round(totalSepOptionAEmoluments))).longValue());
			remittanceBean.setTotalSepOptionBEmoluments(new Long(df.format(Math
					.round(totalSepOptionBEmoluments))).longValue());
			remittanceBean.setTotalOctOptionAEmoluments(new Long(df.format(Math
					.round(totalOctOptionAEmoluments))).longValue());
			remittanceBean.setTotalOctOptionBEmoluments(new Long(df.format(Math
					.round(totalOctOptionBEmoluments))).longValue());

			// log.info("total Emoluments in Nov-95 "+totalNovEmoluments);
			// log.info("OptionA "+remittanceBean.getTotalNovOptionAEmoluments()
			// +"option B"+remittanceBean.getTotalNovOptionBEmoluments());

			remittanceBean.setTotalNovEmoluments(new Long(df.format(Math
					.round(totalNovEmoluments))).longValue());
			remittanceBean.setNovCnt(totalNovCnt);
			remittanceBean.setNovMnth(novDispMnth);
			remittanceBean.setTotalDecContribution(new Double(df.format(Math
					.round(totalDecContribution))).doubleValue());
			remittanceBean.setTotalDecEmoluments(new Long(df.format(Math
					.round(totalDecEmoluments))).longValue());
			remittanceBean.setDecCnt(totalDecCnt);
			remittanceBean.setDecMnth(decDispMnth);
			remittanceBean.setTotalJanContribution(new Double(df.format(Math
					.round(totalJanContribution))).doubleValue());
			remittanceBean.setTotalJanEmoluments(new Long(df.format(Math
					.round(totalJanEmoluments))).longValue());
			remittanceBean.setJanCnt(totalJanCnt);
			remittanceBean.setJanMnth(janDispMnth);
			remittanceBean.setTotalFebContribution(new Double(df.format(Math
					.round(totalFebContribution))).doubleValue());
			remittanceBean.setTotalFebEmoluments(new Long(df.format(Math
					.round(totalFebEmoluments))).longValue());
			remittanceBean.setFebCnt(totalFebCnt);
			remittanceBean.setFebMnth(febDispMnth);
			remittanceBean.setTotalMarContribution(new Double(df.format(Math
					.round(totalMarContribution))).doubleValue());
			remittanceBean.setTotalMarEmoluments(new Long(df.format(Math
					.round(totalMarEmoluments))).longValue());
			remittanceBean.setMarCnt(totalMarCnt);
			remittanceBean.setMarMnth(marDispMnth);

			remittanceBean.setTotalContr(df.format(Math
					.round(totalContribution)));
			remittanceBean
					.setTotalEmnts(df.format(Math.round(totalEmoluments)));
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return remittanceBean;
	}

	public ArrayList getRnfcForm8PSInfo(String fromDate, String toDate,
			String region, String pensionNo, boolean FormFlag,
			boolean secondFormFlag,String formType) {
		// log.info("FinanceReportDAO::getEmployeePensionInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String emoluments = "", pfStatury = "", dateOfBirth = "", vpf = "", cpf = "", monthYear = "", days = "", wetherOption = "", dateOfRetriment = "";
		String checkDate = "", chkMnthYear = "Apr-1995", frmRegion = "", dispRegion = "",emolumentMonths="";
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String pensionNoQry = "", airportCode = "", iadAirportString = "", regionString = "", nxtRegionString = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		double pensionVal = 0.0, retriredEmoluments = 0.0, totalEmoluments = 0.0, totalPFStatury = 0.0, totalVPF = 0.0, totalCPF = 0.0, totalContribution = 0.0;
		double totalEmoluments1 = 0.0, totalPFStatury1 = 0.0, totalVPF1 = 0.0, totalCPF1 = 0.0, totalContribution1 = 0.0;

		if (!pensionNo.equals("")) {
			pensionNoQry = " AND PENSIONNO='" + pensionNo + "'";
		}
		dispRegion = region;
		if (!region.equals("NO-SELECT")) {
			if (region.indexOf("-") != -1) {
				String regionsList[] = region.split("-");
				if (regionsList[0].equals("IAD")) {

					region = "";

					region = "CHQIAD";
					airportCode = regionsList[1];
					iadAirportString = " AND AIRPORTCODE='" + airportCode
							+ "' ";
				}
			}
			regionString = "AND REGION = '" + region + "' " + iadAirportString;
			nxtRegionString = "AND REGION = '" + region + "' ";
		} else {
			regionString = "";
			nxtRegionString = "";
		}
		int countForYear = 0, cntInnnerLoop = 0;
		EmployeePersonalInfo personalInfo = null;
		log.info("====================================");
		log.info("region==============================" + region);
		String findFromYear="",findToYear="",sqlQuery="",sqlcntQuery="",editTransFlag="",arrearFlag="";
		boolean yearBreakMonthFlag=false;
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			yearBreakMonthFlag = true;
			if(!formType.equals("FORM-8-PS-Arrear")){
				 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
						+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG FROM "
						+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
				 sqlcntQuery = "SELECT COUNT(*) AS COUNT FROM (SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND EPV.EMPFLAG='Y'  ";
			}else{
				 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
						+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG FROM "
						+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EDITTRANS,EMOLUMENTMONTHS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' AND EDITTRANS='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)>58 )) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND EPV.EDITTRANS='Y' AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
				 
				 sqlcntQuery = "SELECT COUNT(*) AS COUNT FROM (SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
						+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EDITTRANS,EMOLUMENTMONTHS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' AND EDITTRANS='Y' and (emoluments!=0 or emoluments!=0.0) and monthyear between '"
						+ fromDate
						+ "' and LAST_DAY('"
						+ toDate
						+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME FROM"
						+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)>58 )) "
						
						+ pensionNoQry
						
						+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND EPV.EMPFLAG='Y' AND EPV.EDITTRANS='Y' ";
			}
		
		log.info("FinanceReportDAO::getRnfcForm8PSInfo" + sqlQuery);

		
		}else{
			yearBreakMonthFlag = false;
			 sqlQuery = "SELECT EPV.MONTHYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
				+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS,EPV.EDITTRANS AS EDITTRANS,EPV.ARREARFLAG AS ARREARFLAG,EPV.DEPUTATIONFLAG FROM "
				+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
				+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
				+ fromDate
				+ "' and LAST_DAY('"
				+ toDate
				+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER,EMPLOYEENAME FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
				+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND(NVL(DATEOFJOINING,'01-Mar-2009')) <='01-Mar-2009'"
				+ regionString
				+ pensionNoQry
				+ ")"
				+ ") EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
		log.info("FinanceReportDAO::getRnfcForm8PSInfo" + sqlQuery);

		 sqlcntQuery = "SELECT COUNT(*) AS COUNT FROM (SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
				+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,EMOLUMENTMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and monthyear between '"
				+ fromDate
				+ "' and LAST_DAY('"
				+ toDate
				+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER,EMPLOYEENAME FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
				+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND(NVL(DATEOFJOINING,'01-Mar-2009')) <='01-Mar-2009'"
				+ regionString
				+ pensionNoQry
				+ ")"
				+ ") EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y'  ";
		}
		
		StringBuffer buffer = new StringBuffer();
		String calEmoluments = "", frmCpfaccno = "", frmEmployeeName = "", frmPensionNo = "", tempSerialNumber = "";
		double cellingRate = 0.0;

		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		ArrayList list = new ArrayList();
		try {
			con = commonDB.getConnection();
			countForYear = this.totalCountFin(con, sqlcntQuery);

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				flag = false;
				contrFlag = false;
				chkDOBFlag = false;

				if (tempSerialNumber.equals("")) {
					tempSerialNumber = rs.getString("EMPSERIALNUMBER");
				} else if (!tempSerialNumber.equals(rs.getString(
						"EMPSERIALNUMBER").trim())) {
					// log.info(cntInnnerLoop+"==="+countForYear);
					// log.info("Pension No=="+frmPensionNo);
					// log.info("==frmCpfaccno==="+commonUtil.leadingZeros(20,
					// frmCpfaccno
					// )+"emoluments===="+df1.format(totalEmoluments)+
					// "pensionVal===="+df1.format(totalContribution));

					personalInfo = new EmployeePersonalInfo();
					personalInfo.setEmployeeName(frmEmployeeName);
					personalInfo.setPensionNo(frmPensionNo);
					buffer.append(totalEmoluments);
					buffer.append(",");
					buffer.append(Math.round(totalPFStatury));
					buffer.append(",");
					buffer.append(Math.round(totalVPF));
					buffer.append(",");
					buffer.append(Math.round(totalCPF));
					buffer.append(",");
					buffer.append(totalContribution);
					personalInfo.setRemarks("---");
					personalInfo.setPensionInfo(buffer.toString());
					dispRegion = "";
					// log.info(frmEmployeeName+
					// "=======================tempSerialNumber.equals(rs.getString(EMPSERIALNUMBER)===================================="
					// +buffer.toString());
					list.add(personalInfo);

					buffer = new StringBuffer();
					totalEmoluments = 0.0;

					totalContribution = 0.0;
					emoluments = "";
					calEmoluments = "";
					pensionVal = 0;
					tempSerialNumber = "";
					tempSerialNumber = rs.getString("EMPSERIALNUMBER");
				}

				cntInnnerLoop++;
				if (tempSerialNumber.equals(rs.getString("EMPSERIALNUMBER")
						.trim())) {
					if (rs.getString("MONTHYEAR") != null) {
						monthYear = commonUtil.getDatetoString(rs
								.getDate("MONTHYEAR"), "dd-MMM-yyyy");

					}
					if (rs.getString("CPFACCNO") != null) {
						frmCpfaccno = rs.getString("CPFACCNO");
					}
				
					if (rs.getString("REGION") != null) {
						frmRegion = rs.getString("REGION");
					}
					if (rs.getString("EMPSERIALNUMBER") != null) {
						frmPensionNo = rs.getString("EMPSERIALNUMBER");
					}
					if (rs.getString("EMOLUMENTMONTHS") != null) {
						emolumentMonths=rs.getString("EMOLUMENTMONTHS".trim());
					} else {
						emolumentMonths="1";
					}
					if(yearBreakMonthFlag==false){
						frmEmployeeName=this.getEmployeePersonalInfo(con,frmPensionNo);
					}else{
						if (rs.getString("EMPLOYEENAME") != null) {
							frmEmployeeName = rs.getString("EMPLOYEENAME");
						}
					}
					String deputationflag="N";
					if (rs.getString("DEPUTATIONFLAG") != null) {
						deputationflag=rs.getString("DEPUTATIONFLAG");
					} 

					if (rs.getString("WETHEROPTION") != null) {
						wetherOption = rs.getString("WETHEROPTION");
					}
					if (rs.getString("EMOLUMENTS") != null) {
						emoluments = rs.getString("EMOLUMENTS");
					} else {
						emoluments = "0.00";
					}
					if (rs.getString("EDITTRANS") != null) {
						editTransFlag = rs.getString("EDITTRANS");
					} 
					if (rs.getString("ARREARFLAG") != null) {
						arrearFlag = rs.getString("ARREARFLAG");
					}
					if (rs.getString("dateofbirth") != null) {
						dateOfBirth = commonUtil.converDBToAppFormat(rs
								.getDate("DATEOFBIRTH"));
					} else {
						dateOfBirth = "---";
					}
					if (!dateOfBirth.equals("---")) {
						try {
							dateOfRetriment = this.getRetriedDate(dateOfBirth);
						} catch (InvalidDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						dateOfRetriment = "";
					}

					try {
						checkDate = commonUtil.converDBToAppFormat(monthYear,
								"dd-MMM-yyyy", "MMM-yyyy");
						flag = false;
					} catch (InvalidDataException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
					if (checkDate.toLowerCase().equals(
							chkMnthYear.toLowerCase())) {
						flag = true;
					}
					String decMnt = "";
					if (checkDate.equals("Dec-1995")) {
						decMnt = this.getRnfcForm8ReportDec95(emoluments,
								dateOfBirth, monthYear, wetherOption.trim(),
								dateOfRetriment, flag,emolumentMonths);
						String[] decCal = decMnt.split(",");
						if (decCal.length == 2) {
							calEmoluments = decCal[0];
							pensionVal = Double.parseDouble(decCal[1]);
						}

					} else {
						calEmoluments = this.calWages(emoluments, monthYear,
								wetherOption.trim(), false, false,emolumentMonths);
					}

				
					if (rs.getString("EMPPFSTATUARY") != null) {
						pfStatury = rs.getString("EMPPFSTATUARY");
					} else {
						pfStatury = "0.00";
					}
					if (rs.getString("EMPVPF") != null) {
						vpf = rs.getString("EMPVPF");
					} else {
						vpf = "0";
					}
					if (rs.getString("CPF") != null) {
						cpf = rs.getString("CPF");
					} else {
						cpf = "0";
					}
					
					if (!monthYear.equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(monthYear);
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == 0
								|| empRetriedDt == 0) {
							contrFlag = false;
						} else if (transMntYear == empRetriedDt
								&& transMntYear != 0
								&& empRetriedDt != 0) {
							chkDOBFlag = true;
						}
					}

					if(yearBreakMonthFlag==false){
						if (!checkDate.equals("Dec-1995")) {
							if (flag == false) {
								
								if (contrFlag != true) {
									// log.info("frmEmployeeName"+frmEmployeeName);
									pensionVal = this.pensionFormsCalculation(
											monthYear, calEmoluments, wetherOption
													.trim(), region, false, false,emolumentMonths);

									if (chkDOBFlag == true) {
										String[] dobList = dateOfBirth.split("-");
										days = dobList[0];
										getDaysBymonth = this
												.getNoOfDays(dateOfBirth);
										pensionVal = pensionVal
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth;
										retriredEmoluments = new Double(
												df1
														.format(Double
																.parseDouble(calEmoluments)
																* (Double
																		.parseDouble(days) - 1)
																/ getDaysBymonth))
												.doubleValue();
										calEmoluments = Double
												.toString(retriredEmoluments);
									}

								} else {
									pensionVal = 0;
									calEmoluments = "0";
									}

							} else {
								pensionVal = 0;
							}
						}
						if(deputationflag.equals("Y")){
							pensionVal = rs.getDouble("PENSIONCONTRI");
							
						}
					}else{
						if(rs.getString("PENSIONCONTRI")!=null){
							pensionVal=Double.parseDouble(rs.getString("PENSIONCONTRI"));
						}else{
							pensionVal = 0;
						}
						//log.info("available data is editTransFlag"+editTransFlag+"checkDate"+checkDate);
						//log.info("available data is contrFlag"+contrFlag+"checkDate"+chkDOBFlag);
						if (contrFlag != true) {
							if (chkDOBFlag == true) {
								String[] dobList = dateOfBirth.split("-");
								days = dobList[0];
							
								getDaysBymonth = this.getNoOfDays(dateOfBirth);
								
								retriredEmoluments = new Double(df1.format(Double
										.parseDouble(calEmoluments)
										* (Double.parseDouble(days) - 1)
										/ getDaysBymonth)).doubleValue();
								calEmoluments = Double.toString(retriredEmoluments);
							}
							}else {
								//log.info("available data is"+editTransFlag+"checkDate"+checkDate);
								if(arrearFlag.equals("N")){
									if(!(editTransFlag.equals("Y") && checkDate.toUpperCase().equals("SEP-2009"))){
										pensionVal = 0;
										calEmoluments = "0";
									}
								
								}
									
							}
					
					}
				

				/*	log.info("monthYear==" + monthYear + "==Empserialnumber==="
							+ commonUtil.leadingZeros(20, frmPensionNo)
							+ "emoluments===="
							+ commonUtil.leadingZeros(20, emoluments)
							+ "calEmoluments===="
							+ calEmoluments
							+ "pensionVal====" + pensionVal
							+ "pensionVal====" + Math.round(pensionVal));
*/
					
					if(!formType.trim().equals("FORM-8-PS-Arrear")){
						emoluments = Long.toString(Math.round(Double.parseDouble(calEmoluments)));
					}else{
						emoluments =Double.toString(Math.round(pensionVal*100/8.33));
					}
					if (pensionVal != 0) {
						totalEmoluments = totalEmoluments
								+ Double.parseDouble(emoluments);
						totalPFStatury = totalPFStatury
								+ Double.parseDouble(pfStatury);
						totalVPF = totalVPF + Double.parseDouble(vpf);
						totalCPF = totalCPF + Double.parseDouble(cpf);
						totalContribution = totalContribution + Math.round(pensionVal);

					}
				}
				if (cntInnnerLoop == countForYear) {

					personalInfo = new EmployeePersonalInfo();
					personalInfo.setEmployeeName(frmEmployeeName);
					personalInfo.setPensionNo(frmPensionNo);
					buffer.append(totalEmoluments);
					buffer.append(",");
					buffer.append(Math.round(totalPFStatury));
					buffer.append(",");
					buffer.append(Math.round(totalVPF));
					buffer.append(",");
					buffer.append(Math.round(totalCPF));
					buffer.append(",");
					buffer.append(totalContribution);
					/* log.info(frmEmployeeName+"======="+cntInnnerLoop+
					 "countForYear==="+countForYear+
					 "===============cntInnnerLoop==countForYear================================="+buffer.toString());*/
					personalInfo.setPensionInfo(buffer.toString());
					if (totalEmoluments == 0 && totalPFStatury == 0
							&& totalContribution == 0) {
						personalInfo = null;
					}

					list.add(personalInfo);

					emoluments = "";
					calEmoluments = "";
					pensionVal = 0;
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return list;
	}




	private int totalCountFin(Connection con, String sqlQuery) {
		int totalCnt = 0;
		Statement st = null;
		ResultSet rs = null;
		log.info("FinanceReportDAO::totalCountFin" + sqlQuery);
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				totalCnt = rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		log.info("totalCountFin" + totalCnt);
		return totalCnt;

	}

	private String getRnfcForm8ReportDec95(String emoluments,
			String dateOfBirth, String monthYear, String wetherOption,
			String dateOfRetriment, boolean flag,String emolumentMonths) {
		String decMnthYear = "", calEmoluments = "", days = "";
		boolean firtmntFlag = false, sndMnthFlag = false, contrFlag = false, chkDOBFlag = false;
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		StringBuffer buffer = new StringBuffer();
		long transMntYear = 0, empRetriedDt = 0;
		double pensionVal = 0, retriredEmoluments = 0.0, totalContribution = 0.0, totalEmoluments = 0.0;
		int trnsTotalCnt = 2, getDaysBymonth = 0;
		
		try {
			decMnthYear = commonUtil.converDBToAppFormat(monthYear,
					"dd-MMM-yyyy", "MMM-yyyy");

		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < trnsTotalCnt; i++) {
			if (i == 0) {
				firtmntFlag = true;
			} else if (i == 1) {
				sndMnthFlag = true;
			}
			calEmoluments = this.calWages(emoluments, monthYear, wetherOption
					.trim(), firtmntFlag, sndMnthFlag,"1");
			if (flag == false) {
				if (!monthYear.equals("-NA-") && !dateOfRetriment.equals("")) {
					transMntYear = Date.parse(monthYear);
					empRetriedDt = Date.parse(dateOfRetriment);

					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == 0 || empRetriedDt == 0) {
						contrFlag = false;
					} else if (transMntYear == empRetriedDt
							&& transMntYear != 0 && empRetriedDt != 0) {
						chkDOBFlag = true;
					}
				}

				if (contrFlag != true) {
					pensionVal = this.pensionFormsCalculation(monthYear,
							calEmoluments, wetherOption.trim(), "",
							firtmntFlag, sndMnthFlag,emolumentMonths);

					if (chkDOBFlag == true) {
						String[] dobList = dateOfBirth.split("-");
						days = dobList[0];
						getDaysBymonth = this.getNoOfDays(dateOfBirth);
						pensionVal = pensionVal
								* (Double.parseDouble(days) - 1)
								/ getDaysBymonth;
						retriredEmoluments = new Double(df1.format(Double
								.parseDouble(calEmoluments)
								* (Double.parseDouble(days) - 1)
								/ getDaysBymonth)).doubleValue();
						calEmoluments = Double.toString(retriredEmoluments);
					}

				} else {
					pensionVal = 0;
					calEmoluments = "0";
				}

			} else {
				pensionVal = 0;
			}
			firtmntFlag = false;
			sndMnthFlag = false;
			// log.info("Total Emoluments"+calEmoluments+"Total Contribution"+
			// pensionVal);
			totalEmoluments = totalEmoluments
					+ (Double.parseDouble(calEmoluments) / 2);
			totalContribution = totalContribution + pensionVal;
		}
		buffer.append(totalEmoluments);
		buffer.append(",");
		buffer.append(totalContribution);
		return buffer.toString();
	}

	public int updatePCReport(String fromDate, String toDate, String region,
			String airportcode, String empserialNO, String cpfAccno,
			String transferFlag, String pfIDStrip, String chkMappingFlag) {

		ArrayList penContHeaderList = new ArrayList();
		String mappingflag="true";
		if (chkMappingFlag.equals("true")) {
			penContHeaderList = pendingPFIDsForAdjOB(empserialNO);
		} else {
			
			if (pfIDStrip.equals("NO-SELECT")) {
				penContHeaderList = this.pensionContrPFIDHeaderInfoWthNav(
						region, airportcode, empserialNO, cpfAccno,
						transferFlag, pfIDStrip);
			} else {
				penContHeaderList = this.pensionContrPFIDHeaderInfo(region,
						airportcode, empserialNO, cpfAccno, transferFlag,
						pfIDStrip);
			}
			
		}
		String adjOBYear = "", adjOBRemarks = "";
		try {
			String getToYear = commonUtil.converDBToAppFormat(toDate,
					"dd-MMM-yyyy", "yyyy");
			log.info("getToYear "+getToYear);
			if (getToYear.equals("2008")) {
				adjOBYear = Constants.ADJ_CREATED_DATE_2008;
				adjOBRemarks = "Generated From 1995-08 Grand Total PC Report";
			} else if (getToYear.equals("2009")) {
				adjOBYear = ADJ_CREATED_DATE_2009;
				adjOBRemarks = "Generated From 2008-09 Grand Total PC Report";
			} else if (getToYear.equals("2010")) {
				adjOBYear = ADJ_CREATED_DATE_2010;
				adjOBRemarks = "Generated From 2009-10 Grand Total PC Report";
			}
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}

		String cpfacno = "", empRegion = "", empSerialNumber = "", tempPensionInfo = "";
		String[] cpfaccnos = new String[10];
		String[] dupCpfaccnos = new String[10];
		String[] regions = new String[10];
		String[] empPensionList = null;
		String[] pensionInfo = null;
		CommonDAO commonDAO = new CommonDAO();
		String pensionList = "", tempCPFAcno = "", tempRegion = "", dateOfRetriment = "", getMnthYear = "";
		double totalEmoluments = 0.0, pfStaturary = 0.0, totalPension = 0.0, empVpf = 0.0, principle = 0.0, interest = 0.0, pfContribution = 0.0;
		double grandEmoluments = 0.0, grandCPF = 0.0, grandPension = 0.0, grandPFContribution = 0.0;
		double cpfInterest = 0.0, pensionInterest = 0.0, pfContributionInterest = 0.0;
		double grandCPFInterest = 0.0, grandPensionInterest = 0.0, grandPFContributionInterest = 0.0;
		double cumPFStatury = 0.0, cumPension = 0.0, cumPfContribution = 0.0;
		double cpfOpeningBalance = 0.0, penOpeningBalance = 0.0, pfOpeningBalance = 0.0, rateOfInterest = 0.0;

		DecimalFormat df = new DecimalFormat("#########0");
		ArrayList penConReportList = new ArrayList();
		log.info("=================Update PC Report Starts===========");
		log.info("Header Size" + penContHeaderList.size());
		String dupCpf = "", dupRegion = "", countFlag = "";
		int yearCount = 0;
		int totalRecordIns = 0, inserted = 0;
		Connection con = null;
		try {
			con = commonDB.getConnection();
			for (int i = 0; i < penContHeaderList.size(); i++) {
				PensionContBean penContHeaderBean = new PensionContBean();
				PensionContBean penContBean = new PensionContBean();

				penContHeaderBean = (PensionContBean) penContHeaderList.get(i);
				penContBean.setCpfacno(commonUtil
						.duplicateWords(penContHeaderBean.getCpfacno()));

				penContBean.setEmployeeNM(penContHeaderBean.getEmployeeNM());
				penContBean.setEmpDOB(penContHeaderBean.getEmpDOB());
				penContBean.setEmpSerialNo(penContHeaderBean.getEmpSerialNo());

				penContBean.setEmpDOJ(penContHeaderBean.getEmpDOJ());
				penContBean.setGender(penContHeaderBean.getGender());
				penContBean.setFhName(penContHeaderBean.getFhName());
				penContBean.setEmployeeNO(penContHeaderBean.getEmployeeNO());
				penContBean.setDesignation(penContHeaderBean.getDesignation());
				penContBean.setWhetherOption(penContHeaderBean
						.getWhetherOption());
				penContBean.setDateOfEntitle(penContHeaderBean
						.getDateOfEntitle());
				penContBean.setMaritalStatus(penContHeaderBean
						.getMaritalStatus());

				penContBean.setPensionNo(commonDAO.getPFID(penContBean
						.getEmployeeNM(), penContBean.getEmpDOB(), commonUtil
						.leadingZeros(5, penContHeaderBean.getEmpSerialNo())));
			
				empSerialNumber = penContHeaderBean.getEmpSerialNo();

				double totalAAICont = 0.0, calCPF = 0.0, calPens = 0.0;
				ArrayList employeFinanceList = new ArrayList();
				String preparedString = penContHeaderBean.getPrepareString();
				try {
					dateOfRetriment = this.getRetriedDate(penContBean
							.getEmpDOB());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList rateList = new ArrayList();
				String findFromDate = "", monthInfo = "", findMnt = "";
				findFromDate = this.compareTwoDates(penContHeaderBean
						.getDateOfEntitle(), fromDate);
				log.info("Find From Date" + findFromDate);
				pensionList = this.getEmployeePensionInfo(preparedString,
						findFromDate, toDate, penContHeaderBean
								.getWhetherOption(), dateOfRetriment,
						penContBean.getEmpDOB(),empserialNO);
				String rateFromYear = "", rateToYear = "", checkMnthDate = "", dispFromYear = "", monthInterestInfo = "", dispFromMonth = "";
				ArrayList blockList = new ArrayList();
				boolean dispYearFlag = false, yearBreak = false;
				boolean rateFlag = false;
				if (!pensionList.equals("")) {
					grandEmoluments = 0.0;
					grandCPF = 0.0;
					grandPension = 0.0;
					grandCPFInterest = 0.0;
					grandPensionInterest = 0.0;
					grandPFContribution = 0.0;
					grandPFContributionInterest = 0.0;
					cumPFStatury = 0.0;
					cumPension = 0.0;
					cumPfContribution = 0.0;
					totalEmoluments = 0;
					pfStaturary = 0;
					totalPension = 0;
					pfContribution = 0;
					cpfInterest = 0;
					pensionInterest = 0;
					pfContributionInterest = 0;
					cpfOpeningBalance = 0.0;
					penOpeningBalance = 0.0;
					pfOpeningBalance = 0.0;
					empPensionList = pensionList.split("=");
					String penTempMnthInfo = empPensionList[empPensionList.length - 1];
					String[] penMnthInfo = penTempMnthInfo.split(",");
					//log.info(penMnthInfo[13]);
					blockList = this.getMonthList(con, penMnthInfo[20]);

					if (empPensionList != null) {
						for (int r = 0; r < empPensionList.length; r++) {
							TempPensionTransBean bean = new TempPensionTransBean();
							tempPensionInfo = empPensionList[r];
							pensionInfo = tempPensionInfo.split(",");
							bean.setMonthyear(pensionInfo[0]);
							dispFromMonth = commonUtil.converDBToAppFormat(bean
									.getMonthyear(), "dd-MMM-yyyy", "MMM");
							if (dispYearFlag == false
									&& dispFromMonth.equals("Mar")) {
								if (dispFromYear.equals("")) {
									dispFromYear = commonUtil
											.converDBToAppFormat(bean
													.getMonthyear(),
													"dd-MMM-yyyy", "yy");
								}

								getMnthYear = commonUtil.converDBToAppFormat(
										bean.getMonthyear(), "dd-MMM-yyyy",
										"MM-yy");

								monthInterestInfo = this.getBlockYear(
										getMnthYear, blockList);
								// log.info("Month Info"+monthInterestInfo);
								String[] monthInterestList = monthInterestInfo
										.split(",");

								monthInfo = monthInterestList[1];

								rateOfInterest = new Double(
										monthInterestList[0]).doubleValue();

								dispYearFlag = true;
							}
							bean.setEmoluments(pensionInfo[1]);
							bean.setCpf(pensionInfo[2]);
							bean.setEmpVPF(pensionInfo[3]);
							bean.setEmpAdvRec(pensionInfo[4]);
							bean.setEmpInrstRec(pensionInfo[5]);
							bean.setStation(pensionInfo[6]);
							bean.setPensionContr(pensionInfo[7]);
							calCPF = Math.round(Double.parseDouble(bean
									.getCpf()));
							bean.setDeputationFlag(pensionInfo[19].trim());
							DateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yy");
							
							Date transdate1 = dateFormat1.parse(pensionInfo[0]);
							if (transdate1.before(new Date("31-Mar-08"))&& (bean.getDeputationFlag().equals("N")||bean.getDeputationFlag().equals(""))) {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[7]));
								
								totalAAICont = calCPF - calPens;
							} else {
								calPens = Math.round(Double
										.parseDouble(pensionInfo[12]));
								bean.setPensionContr(pensionInfo[12]);
								totalAAICont = calCPF - calPens;
							}
							bean.setAaiPFCont(Double.toString(totalAAICont));
							bean.setGenMonthYear(pensionInfo[8]);
							bean.setTransCpfaccno(pensionInfo[9]);
							bean.setRegion(pensionInfo[10]);
							bean.setRecordCount(pensionInfo[11]);
							bean.setDbPensionCtr(pensionInfo[12]);
							bean.setForm7Narration(pensionInfo[14]);
							bean.setPcHeldAmt(pensionInfo[15]);
							bean.setPccalApplied(pensionInfo[17].trim());
				
							if (bean.getRecordCount().equals("Duplicate")) {
								countFlag = "true";
							}
							bean.setRemarks("---");
							findMnt = commonUtil.converDBToAppFormat(bean
									.getMonthyear(), "dd-MMM-yyyy", "MM-yy");

							totalEmoluments = new Double(df
									.format(totalEmoluments
											+ Math.round(Double
													.parseDouble(bean
															.getEmoluments()))))
									.doubleValue();
							pfStaturary = new Double(df.format(pfStaturary
									+ Math.round(Double.parseDouble(bean
											.getCpf())))).doubleValue();
							cumPFStatury = cumPFStatury + pfStaturary;
							empVpf = new Double(df.format(empVpf
									+ Math.round(Double.parseDouble(bean
											.getEmpVPF())))).doubleValue();
							principle = new Double(df.format(principle
									+ Math.round(Double.parseDouble(bean
											.getEmpAdvRec())))).doubleValue();
							interest = new Double(df.format(interest
									+ Math.round(Double.parseDouble(bean
											.getEmpInrstRec())))).doubleValue();
							totalPension = new Double(df.format(totalPension
									+ Math.round(Double.parseDouble(bean
											.getPensionContr()))))
									.doubleValue();
							cumPension = cumPension + totalPension;
							pfContribution = new Double(df
									.format(pfContribution
											+ Math.round(Double
													.parseDouble(bean
															.getAaiPFCont()))))
									.doubleValue();
							cumPfContribution = cumPfContribution
									+ pfContribution;

							if (findMnt.equals(monthInfo)) {
								yearBreak = true;
							}
							if (yearBreak == true) {
								cpfInterest = Math.round((cumPFStatury
										* rateOfInterest / 100) / 12)
										+ Math.round(cpfOpeningBalance
												* rateOfInterest / 100);
								pensionInterest = Math.round((cumPension
										* rateOfInterest / 100) / 12)
										+ Math.round(penOpeningBalance
												* rateOfInterest / 100);
								pfContributionInterest = Math
										.round((cumPfContribution
												* rateOfInterest / 100) / 12)
										+ Math.round(pfOpeningBalance
												* rateOfInterest / 100);
								yearBreak = false;
								// log.info(bean.getMonthyear()+"cpfInterest"+
								// cpfInterest+"cumPFStatury"+cumPFStatury);
								cpfOpeningBalance = Math.round(pfStaturary
										+ cpfInterest
										+ Math.round(cpfOpeningBalance));
								penOpeningBalance = Math.round(totalPension
										+ pensionInterest
										+ Math.round(penOpeningBalance));
								pfOpeningBalance = Math.round(pfContribution
										+ pfContributionInterest
										+ Math.round(pfOpeningBalance));
								grandEmoluments = grandEmoluments
										+ totalEmoluments;
								grandCPF = grandCPF + pfStaturary;
								grandPension = grandPension + totalPension;
								grandPFContribution = grandPFContribution
										+ pfContribution;

								grandCPFInterest = grandCPFInterest
										+ cpfInterest;
								grandPensionInterest = grandPensionInterest
										+ pensionInterest;
								grandPFContributionInterest = grandPFContributionInterest
										+ pfContributionInterest;
								cumPFStatury = 0.0;
								cumPension = 0.0;
								cumPfContribution = 0.0;
								totalEmoluments = 0;
								pfStaturary = 0;
								totalPension = 0;
								pfContribution = 0;
								cpfInterest = 0;
								pensionInterest = 0;
								pfContributionInterest = 0;

								dispYearFlag = false;

							}

						}

					}
					log.info("grandEmoluments" + grandEmoluments + "grandCPF"
							+ grandCPF + "grandCPFInterest========"
							+ df.format(grandCPFInterest) + "adjOBYear"
							+ adjOBYear + "adjOBRemarks" + adjOBRemarks);
					log.info("grandEmoluments" + grandEmoluments + "grandPension"
							+ grandPension + "grandPensionInterest===="
							+ df.format(grandPensionInterest));
					log.info("grandEmoluments" + grandEmoluments
							+ "grandPFContribution" + grandPFContribution
							+ "grandPFContributionInterest================="
							+ df.format(grandPFContributionInterest));
					String trustWiseFlag=this.chkTrustWise(penContBean.getEmpSerialNo(), con,
							adjOBYear);
					log.info("==================trustWiseFlag"+trustWiseFlag);
					if (!trustWiseFlag.equals("X")) {
						inserted = this.insertTrustWiseBal(con, grandPension,
								grandPensionInterest, penContBean
										.getEmpSerialNo(), region, adjOBYear,
								adjOBRemarks);
						totalRecordIns = totalRecordIns + inserted;
					} else if (chkMappingFlag.equals("true") || trustWiseFlag.equals("X")) {
						inserted = this.updateTrustWiseBal(con, grandPension,
								grandPensionInterest, penContBean
										.getEmpSerialNo(), region, adjOBYear,
								adjOBRemarks);
						totalRecordIns = totalRecordIns + inserted;
					}
					log.info("==================trustWiseFlag"+this.chkOBAdjInfo(penContBean.getEmpSerialNo(), con,
							Constants.ADJ_CREATED_DATE));
					if (!trustWiseFlag.equals("X")) {
						inserted = this.insertTrustWiseBal(con, grandPension,
								grandPensionInterest, penContBean
								.getEmpSerialNo(), region, adjOBYear,
								adjOBRemarks);
						totalRecordIns = totalRecordIns + inserted;
					} else if (chkMappingFlag.equals("true") || trustWiseFlag.equals("X")) {
						inserted = this.updateTrustWiseBal(con, grandPension,
								grandPensionInterest, penContBean
								.getEmpSerialNo(), region, adjOBYear,
								adjOBRemarks);
						totalRecordIns = totalRecordIns + inserted;
					}
					this.pctotalAddorUpdate(con, grandCPF,
							grandCPFInterest, grandPension,
							grandPensionInterest, grandPFContribution,
							grandPFContributionInterest, penContBean
							.getEmpSerialNo(), region);
					if(chkOBAdjFreezeInfo(penContBean.getEmpSerialNo(), con,Constants.ADJ_CREATED_DATE).equals("X")){
						/*Regarding FYI 2008-2009 Data Freeze,So we can add the data difference between freeze data,latest values
						 * of from FYI 1995-2008.This difference values added into FYI 2009-2010.
						 */
						String chkAdjFlag=this.chkOBAdjInfo(penContBean.getEmpSerialNo(), con,
								Constants.ADJ_CREATED_DATE_2009);
						log.info("==================chkAdjFlag"+chkAdjFlag);
						log.info("grandCPF"+ grandCPF+"grandCPFInterest"+
									grandCPFInterest+"grandPension"+ grandPension+"grandPensionInterest"+
									grandPensionInterest+"grandPFContribution"+ grandPFContribution+"grandPFContributionInterest"+
									grandPFContributionInterest);
						if (!chkAdjFlag.equals("X")) {
							inserted = this.insertAdjBalFYI2009(con, grandCPF,
									grandCPFInterest, grandPension,
									grandPensionInterest, grandPFContribution,
									grandPFContributionInterest, penContBean
									.getEmpSerialNo(), region);
							totalRecordIns = totalRecordIns + inserted;
						} else if (chkAdjFlag.equals("X")) {
							inserted = this.updateAdjBalFYI2009(con, grandCPF,
									grandCPFInterest, grandPension,
									grandPensionInterest, grandPFContribution,
									grandPFContributionInterest, penContBean
									.getEmpSerialNo(), region);
							totalRecordIns = totalRecordIns + inserted;
						}
					
					}else{/*
						if (!this.chkOBAdjInfo(penContBean.getEmpSerialNo(), con,
								Constants.ADJ_CREATED_DATE).equals("X")) {
							inserted = this.insertAdjBal(con, grandCPF,
									grandCPFInterest, grandPension,
									grandPensionInterest, grandPFContribution,
									grandPFContributionInterest, penContBean
											.getEmpSerialNo(), region);
							totalRecordIns = totalRecordIns + inserted;
						} else if (chkMappingFlag.equals("true")) {
							inserted = this.updateAdjBal(con, grandCPF,
									grandCPFInterest, grandPension,
									grandPensionInterest, grandPFContribution,
									grandPFContributionInterest, penContBean
											.getEmpSerialNo(), region);
							totalRecordIns = totalRecordIns + inserted;
						}
						
					*/}
					this.updateEmolumentTbl(con, penContBean.getEmpSerialNo());
					
					}
				}
		
	
		} catch (Exception ex) {
			log.printStackTrace(ex);
		} finally {
			commonDB.closeConnection(con, null, null);
		}
		log.info("totalRecordIns============================================"
				+ totalRecordIns);
		return totalRecordIns;
	}


	private ArrayList getAdjOBForPFCardReport(Connection con, String monthYear,
			String pensionNo, ArrayList list) {

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", obYear = "", obFlag = "", tempMonthYear = "",adjFlag="N";
		long cpfTotal = 0, pensionTotal = 0, pfTotal = 0, empsubTotal = 0;
		long cpfInterest = 0, pensionInterest = 0, pfInterest = 0, empsubInterest = 0,priorAdjAmount=0;
		long adjcpfTotal = 0, adjPensionTotal = 0, adjPfTotal = 0, adjPensionTotalInt=0,adjEmpSubTotal = 0,adjPensionContri=0;
		tempMonthYear = "%" + monthYear.substring(2, monthYear.length());
		sqlQuery = "SELECT CPFTOTAL,CPFINTEREST,PENSIONTOTAL,PENSIONINTEREST,PFTOTAL,PFINTEREST,NVL(EMPSUB,'0') AS EMPSUB,NVL(EMPSUBINTEREST,'0') AS EMPSUBINTEREST,OUTSTANDADV,PRIORADJFLAG,NVL(PENSIONCONTRIADJ,'0.00') AS PENSIONCONTRIADJ  FROM EMPLOYEE_ADJ_OB WHERE PENSIONNO="
				+ pensionNo.trim()
				+ " and to_char(ADJOBYEAR,'dd-Mon-yyyy') like '"
				+ tempMonthYear + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			log.info("getAdjOBForPFCardReport===================sqlQuery==="
					+ sqlQuery);
			if (rs.next()) {
				cpfTotal = rs.getLong("CPFTOTAL");
				pensionTotal = rs.getLong("PENSIONTOTAL");
				pfTotal = rs.getLong("PFTOTAL");
				cpfInterest = rs.getLong("CPFINTEREST");
				pensionInterest = rs.getLong("PENSIONINTEREST");
				pfInterest = rs.getLong("PFINTEREST");
				empsubTotal = rs.getLong("EMPSUB");
				empsubInterest = rs.getLong("EMPSUBINTEREST");
				adjPensionContri = rs.getLong("PENSIONCONTRIADJ");
				if(rs.getString("PRIORADJFLAG")!=null){
					adjFlag=rs.getString("PRIORADJFLAG");
				}
			
			}
			adjcpfTotal = cpfTotal + cpfInterest;
			
			adjPensionTotal = pensionTotal + pensionInterest;
			/*if(Integer.parseInt(commonUtil.converDBToAppFormat(monthYear,"dd-MMM-yyyy","yyyy"))==2009 && adjFlag.equals("N")){
				adjPensionTotalInt=new Double(adjPensionTotal*8.5/100).longValue();
			}
			adjPensionTotal=adjPensionTotal+adjPensionTotalInt;*/
			adjPfTotal = pfTotal + pfInterest;
			adjEmpSubTotal = empsubTotal + empsubInterest;
			log.info("PFCard==========getAdjOBForPFCardReport===================pensionTotal==="
					+ pensionTotal+"pensionInterest"+pensionInterest);
			list.add(new Long(adjcpfTotal));
			list.add(new Long(-adjPensionTotal));
			list.add(new Long(adjPfTotal));
			list.add(new Long(adjEmpSubTotal));
			list.add(new Long(adjPensionContri));
			

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return list;
	}
	private String chkOBInfo(String empSerialNo, Connection con,
			ArrayList OBList) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "", obMnthYear = "", chkOBFlag = "";

		Long finalEmpNetOB = null, finalAaiNetOB = null, finalPensionOB = null;
		finalEmpNetOB = (Long) OBList.get(0);
		finalAaiNetOB = (Long) OBList.get(1);
		finalPensionOB = (Long) OBList.get(2);
		obMnthYear = (String) OBList.get(3);
		try {
			sqlInsQuery = "SELECT 'X' AS FLAG FROM EMPLOYEE_PENSION_OB WHERE OBYEAR='"
					+ obMnthYear + "' AND PENSIONNO='" + empSerialNo + "'";
			st = con.createStatement();
			log.info("chkOBInfo::select Query" + sqlInsQuery);
			rs = st.executeQuery(sqlInsQuery);
			if (rs.next()) {
				if (rs.getString("FLAG") != null) {
					chkOBFlag = rs.getString("FLAG");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return chkOBFlag;
	}

	public String getBlockYear(String year, ArrayList blockList) {
		String bYear = "", breakYear = "";
		for (int by = 0; by < blockList.size(); by++) {
			bYear = (String) blockList.get(by);

			String[] bDate = bYear.split(",");

			if (year.equals(bDate[1])) {
				breakYear = bDate[1];
				breakYear = bYear;
				break;
			} else {
				breakYear = "03-96";
			}
		}
		return breakYear;
	}

	private int insertAdjBal(Connection con, double cpf, double cpfinterest,
			double pension, double pensionintrest, double pf,
			double pfinterest, String pensionno, String region) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", remarks = "", adjOBYear = "";
		DecimalFormat df = new DecimalFormat("#########0");
		adjOBYear = Constants.ADJ_CREATED_DATE;
		try {
			remarks = "Generated From 1995-08 Grand Total PC Report";
			sqlInsQuery = "INSERT INTO employee_adj_ob(CPFTOTAL,CPFINTEREST,PENSIONTOTAL,PENSIONINTEREST,PFTOTAL,PFINTEREST,REMARKS,ADJOBYEAR,REGION,PENSIONNO) VALUES("
					+ df.format(cpf)
					+ ","
					+ df.format(cpfinterest)
					+ ","
					+ df.format(pension)
					+ ","
					+ df.format(pensionintrest)
					+ ","
					+ df.format(pf)
					+ ","
					+ df.format(pfinterest)
					+ ",'"
					+ remarks
					+ "','"
					+ adjOBYear
					+ "','"
					+ region
					+ "',"
					+ pensionno + ")";
			st = con.createStatement();
			log.info("insertAdjBal::Inserted Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
	
		}
		return count;
	}

	private String chkOBAdjInfo(String empSerialNo, Connection con,
			String obMnthYear) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "";
		String chkFlag = "";
		try {
			sqlInsQuery = "SELECT 'X' AS FLAG FROM EMPLOYEE_ADJ_OB WHERE ADJOBYEAR='"
					+ obMnthYear + "' AND PENSIONNO='" + empSerialNo + "'";
			st = con.createStatement();
			log.info("chkOBAdjInfo::Query" + sqlInsQuery);
			rs = st.executeQuery(sqlInsQuery);
			if (rs.next()) {
				if (rs.getString("FLAG") != null) {
					chkFlag = rs.getString("FLAG");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return chkFlag;
	}

	public String getWagesForm3Report(Connection con, String monthYear,
			String pensionNo, String flag) {
		String sqlQuery = "", emoluments = "", pfStatury = "", transInfo = "";
		monthYear = this.getLastMonthYear(monthYear);
		Statement st = null;
		ResultSet rs = null;
		if (flag.equals("NEW")) {
			sqlQuery = "SELECT ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY FROM EMPLOYEE_PENSION_VALIDATE WHERE PENSIONNO='"
					+ pensionNo
					+ "' and empflag='Y' and monthyear between '"
					+ monthYear + "' and last_day('" + monthYear + "')";
		} else if (flag.equals("OLD")) {
			sqlQuery = "SELECT EPV.MONTHYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY FROM "
					+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,REGION,CPFACCNO,EMPFLAG "
					+ " FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and monthyear between '"
					+ monthYear
					+ "' and last_day('"
					+ monthYear
					+ "') ) EPV  ,(SELECT REGION , CPFACNO FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER='"
					+ pensionNo
					+ "' ) EMPFID "
					+ " WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y'";
		}
		log.info("getWagesForm3Report::Query" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("EMOLUMENTS") != null) {
					emoluments = rs.getString("EMOLUMENTS");
				} else {
					emoluments = "0";
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					pfStatury = rs.getString("EMPPFSTATUARY");
				} else {
					pfStatury = "0";
				}
				transInfo = emoluments + "," + pfStatury;
			} else {
				transInfo = "0,0";
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return transInfo;
	}

	public ArrayList financeForm3ByPFID(String monthyear, String region,
			String airportcode, String formTypeFlag,String sortingOrder)
			throws InvalidDataException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePersonalInfo personalInfo = null;
		ArrayList form3PSList = new ArrayList();
		String seperationReason = "", transInfo = "", sqlQuery = "", flag = "", dojMnth = "", dojYear = "", selectedMonth = "", transMonthYear = "", selectedYear = "";
		selectedMonth = commonUtil.converDBToAppFormat(monthyear,
				"dd-MMM-yyyy", "MMM");
		selectedYear = commonUtil.converDBToAppFormat(monthyear, "dd-MMM-yyyy",
				"yyyy");
		long employeeOFYears = 0;
		/*
		 * StringsqlQuery=
		 * "SELECT to_char(DATEOFJOINING,'Mon') as dojmonth,to_char(DATEOFJOINING,'yyyy') as dojyear,REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
		 * +
		 * "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
		 * +
		 * "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO "
		 * +"WHERE NVL(DATEOFJOINING,'01-Mar-2009')<=last_day('"+monthyear+
		 * "') ORDER BY PENSIONNO";
		 */
		sqlQuery = buildQueryForm3PFID(region, airportcode, monthyear,
				formTypeFlag,sortingOrder);
		log.info("financeForm3ByPFID::Query" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				personalInfo = new EmployeePersonalInfo();
				personalInfo = commonDAO.loadPersonalInfo(rs);

				if (!personalInfo.getDateOfJoining().equals("")
						&& !personalInfo.getDateOfBirth().equals("---")) {

					long noOfYears = 0;
					noOfYears = rs.getLong("ENTITLEDIFF");
					if (noOfYears > 0) {
						employeeOFYears = commonUtil.getDateDifference(
								personalInfo.getDateOfBirth(), personalInfo
										.getDateOfJoining());

						personalInfo.setAgeAtEntry(Long
								.toString(employeeOFYears));
						personalInfo.setDateOfEntitle(personalInfo
								.getDateOfJoining());
					} else {
						employeeOFYears = commonUtil.getDateDifference(
								personalInfo.getDateOfBirth(), "01-Apr-1995");
						personalInfo.setAgeAtEntry(Long
								.toString(employeeOFYears));

						personalInfo.setDateOfEntitle("01-Apr-1995");

					}
				} else {
					personalInfo.setDateOfEntitle("---");
					personalInfo.setAgeAtEntry("---");
				}
				if (rs.getString("dojmonth") != null) {
					dojMnth = rs.getString("dojmonth");
				} else {
					dojMnth = "---";
				}
				if (rs.getString("dojyear") != null) {
					dojYear = rs.getString("dojyear");
				} else {
					dojYear = "---";
				}
				if (rs.getString("DATEOFSEPERATION_REASON") != null) {
					seperationReason = rs.getString("DATEOFSEPERATION_REASON");
					if (!seperationReason.equals("Other")) {
						if (seperationReason.equals("Death")) {
							personalInfo.setRemarks("Expired & PF Settled");
						} else if (seperationReason.equals("Retirement")) {
							personalInfo.setRemarks("Retired & PF Settled");
						} else {
							personalInfo.setRemarks(seperationReason);
						}

					}
					//personalInfo.setDateOfEntitle("---");
				} else {
					seperationReason = "---";
					personalInfo.setRemarks("---");
				}

				log.info("selectedMonth=========" + selectedMonth
						+ "dojMnth=======" + dojMnth);
				if (!seperationReason.equals("---")) {
					if (!seperationReason.equals("Other")) {
						flag = "OLD"; // retrived old year
						transMonthYear = "01-Oct-2007";
					} else {
					//	transMonthYear = monthyear;
						transMonthYear ="01-Feb-2009";
					}

				} else if (!dojMnth.equals("---") && !dojYear.equals("---")) {
					if (dojMnth.toUpperCase().equals(
							selectedMonth.toUpperCase())
							&& dojYear.equals(selectedYear)) {
						flag = "NO";
					} else {
//						transMonthYear = monthyear;
						transMonthYear ="01-Feb-2009";
						flag = "NEW";
					}
				}
				log.info("transMonthYear============================="
						+ transMonthYear + "flag=======" + flag);
				if (!flag.equals("NO") && !flag.equals("")) {

					transInfo = this.getWagesForm3Report(con, transMonthYear,
							personalInfo.getPensionNo(), flag);
				}
				String[] transData = transInfo.split(",");
				if (transData.length > 0) {
					personalInfo.setBasicWages(transData[0]);
				} else {
					personalInfo.setBasicWages("0");
				}
				if (flag.equals("NO")) {
					personalInfo.setBasicWages("0");
				}
				form3PSList.add(personalInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form3PSList;
	}


	private String getLastMonthYear(String monthyear) {
		String tempMonthYear = "", finalMnthYear = "";
		int month = 0;
		log.info("monthyear============" + monthyear);
		try {
			tempMonthYear = commonUtil.converDBToAppFormat(monthyear,
					"dd-MMM-yyyy", "dd-MM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String findMonth[] = tempMonthYear.split("-");
		month = Integer.parseInt(findMonth[1]) - 1;
		if (month == 0) {
			month = 12;
		}
		tempMonthYear = "";
		tempMonthYear = findMonth[0] + "-" + month + "-" + findMonth[2];
		try {
			finalMnthYear = commonUtil.converDBToAppFormat(tempMonthYear,
					"dd-MM-yyyy", "dd-MMM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalMnthYear;
	}

	public String buildQueryForm3PFID(String region, String airportcode,
			String monthyear, String formTypeFlag,String sortingOrder) {
		log.info("FinancialReportDAO::buildQueryForm3PFID-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		if (region.equals("NO-SELECT")) {
			region = "";
		}
		if (airportcode.equals("NO-SELECT")) {
			airportcode = "";
		}
		sqlQuery = "SELECT to_char(DATEOFJOINING,'Mon') as dojmonth,to_char(DATEOFJOINING,'yyyy') as dojyear,REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
				+ "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
				+ "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,FINALSETTLMENTDT,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO "
				+ "WHERE NVL(DATEOFJOINING,'"
				+ monthyear
				+ "')<=last_day('"
				+ monthyear + "') ";

		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		if (!airportcode.equals("")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = " ORDER BY "+sortingOrder;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("FinancialReportDAO::buildQueryForm3PFID Leaving Method");
		return dynamicQuery;
	}

	public ArrayList rnfcForm7Report(String selectedYear, String month,
			String sortedColumn, String region, boolean formFlag,
			String airportCode, String pensionNo, String range, String empflag,
			String empName,String formType,String formRevisedFlag) {
		String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
		int toSelectYear = 0;
		ArrayList empList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		ArrayList form8List = new ArrayList();
		ArrayList getPensionList = new ArrayList();
		boolean arrearsFlag=false;
		if (!month.equals("NO-SELECT")) {
			try {
				frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
			fromYear = "01-Apr-" + selectedYear;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else if (!selectedYear.equals("Select One")
				&& !month.equals("NO-SELECT")) {
			fromYear = "01-" + frmMonth + "-" + selectedYear;
			toYear = fromYear;
		} else {
			fromYear = Constants.FORMS_FROM_DATE;
			toYear = Constants.FORMS_TO_DATE;
		}

		empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
				fromYear, toYear, airportCode, pensionNo, empflag, empName,"N",formType,formRevisedFlag);
	
		if(empList.size()==0 && Integer.parseInt(selectedYear)>=2008){
			
					empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
							fromYear, toYear, airportCode, pensionNo, empflag, empName,"Y",formType,formRevisedFlag);
					arrearsFlag=true;
		}
		log.info("rnfcForm7Report:::fromYear"+fromYear+"toYear"+toYear+"arrearsFlag"+arrearsFlag);
		
		String pensionInfo = "", regionInfo = "";

		for (int i = 0; i < empList.size(); i++) {
			personalInfo = (EmployeePersonalInfo) empList.get(i);
			if(personalInfo.getChkarrearAdj().equals("Y")){
				arrearsFlag=true;
			}else{
				arrearsFlag=false;
			}
			if (!personalInfo.getDateOfBirth().equals("---")) {
				try {
					dateOfRetriment = this.getRetriedDate(personalInfo
							.getDateOfBirth());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!personalInfo.getFinalSettlementDate().equals("---")){
				int finalSettlmentYear=0;
				try {
					finalSettlmentYear=Integer.parseInt(commonUtil.converDBToAppFormat(personalInfo.getFinalSettlementDate(),"dd-MMM-yyyy","yyyy"));
				} catch (NumberFormatException e) {
					log.printStackTrace(e);
				} catch (InvalidDataException e) {
					log.printStackTrace(e);
				}
				if((Integer.parseInt(selectedYear)>=finalSettlmentYear && finalSettlmentYear<=toSelectYear) && arrearsFlag==false){
					arrearsFlag=true;
				}
			}
			String tempEntitle="",returnFromDate="",fromDOJ="";
			if(formRevisedFlag.equals("N")){
				tempEntitle="01"+personalInfo.getDateOfEntitle().substring(2,personalInfo.getDateOfEntitle().length());
				returnFromDate=this.compareTwoDates(tempEntitle,fromYear);
				if(returnFromDate.equals(tempEntitle)){
					fromDOJ=personalInfo.getDateOfEntitle();
				}else{
					fromDOJ=fromYear;
				}
			}else{
				tempEntitle=personalInfo.getFromarrearreviseddate();
				returnFromDate=this.compareTwoDates(tempEntitle,fromYear);
				if(returnFromDate.equals(tempEntitle)){
					fromDOJ=personalInfo.getFromarrearreviseddate();
				}else{
					fromDOJ=fromYear;
				}
			}
			
			log.info("Pensionno"+personalInfo.getOldPensionNo()+"From Date"+fromYear+"toYear"+toYear+"fromDOJ"+fromDOJ+"returnFromDate"+returnFromDate);
			getPensionList = this.getForm7PensionInfo(fromDOJ, toYear,
					personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(),
					false, dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo(),arrearsFlag,formRevisedFlag);
			personalInfo.setPensionList(getPensionList);

			dateOfRetriment = "";
			form8List.add(personalInfo);
		}
		return form8List;
	}

	public ArrayList rnfcForm7IndexReport(String selectedYear, String month,
			String sortedColumn, String region, boolean formFlag,
			String airportCode, String pensionNo, String range, String empflag,
			String empName) {
		String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
		int toSelectYear = 0;
		ArrayList empList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		ArrayList form8List = new ArrayList();
		ArrayList getPensionList = new ArrayList();
		if (!month.equals("NO-SELECT")) {
			try {
				frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
			fromYear = "01-Apr-" + selectedYear;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else if (!selectedYear.equals("Select One")
				&& !month.equals("NO-SELECT")) {
			fromYear = "01-" + frmMonth + "-" + selectedYear;
			toYear = fromYear;
		} else {
			fromYear = Constants.FORMS_FROM_DATE;
			toYear = Constants.FORMS_TO_DATE;
		}

		empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
				fromYear, toYear, airportCode, pensionNo, empflag, empName,"N","","N");
		

		
		return empList;
	}

	private ArrayList getForm7EmployeeInfo(String range, String sortedColumn,
			String region, String fromYear, String toYear, String airportCode,
			String pensionNo, String empNameFlag, String empName,String arrears,String formType,String formRevisedFlag) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String remarks = "", chkDOE="",findFromYear="",payrevisionarrear="",arrearInfo="",findToYear="",sqlQuery = "",seperationFlag="", pensionAppednQry = "", pfidWithRegion = "", appendRegionTag = "", nextAppendRegionTag = "", appendAirportTag = "", appendPenTag = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();
		int startIndex = 0, endIndex = 0;
		if (region.equals("NO-SELECT")) {
			region = "";
		}
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toYear, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		log.info("formRevisedFlag================="+formRevisedFlag);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if(formRevisedFlag.equals("N")){
				if(arrears.equals("N")){
					sqlQuery = this.buildQuerygetEmployeePFInfoPrinting(range, region,
							empNameFlag, empName, sortedColumn, pensionNo,toYear,fromYear);
				}else{
					sqlQuery = this.buildQueryEmpPFForm7ArrearsInfoPrinting(range, region,
							empNameFlag, empName, sortedColumn, pensionNo,toYear,fromYear);
				}
			}else{
				sqlQuery = this.buildQueryEmployeeArrearRevisedInfo(range, region,
						empNameFlag, empName, sortedColumn, pensionNo,toYear,fromYear);
			}

			log.info("FinanceReportDAO::getForm7EmployeeInfo"+sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();
				CommonDAO commonDAO = new CommonDAO();
				data = commonDAO.loadPersonalInfo(rs);
				if(Integer.parseInt(findFromYear)>=2008){
					pfidWithRegion="";
				}else{
					pfidWithRegion = this.getEmployeeForm7MappingPFInfo(data
							.getOldPensionNo(), data.getCpfAccno(), data
							.getRegion());
				}
				
	
				if (pfidWithRegion.equals("")) {
					pfidWithRegion = data.getCpfAccno() + ","
							+ data.getRegion() + "=";
				}

				if (!pfidWithRegion.equals("")) {
					String[] pfIDLists = pfidWithRegion.split("=");
					data.setPfIDString(this.preparedCPFString(pfIDLists));
				}
				if (rs.getString("CHKWTHRARRNOT") != null) {
					data.setChkarrearAdj((rs.getString("CHKWTHRARRNOT")));
					arrears=data.getChkarrearAdj();
				}
				if (data.getWetherOption().trim().equals("---")) {
					data.setRemarks("Wether Option is not available");
				}

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");

				if (noOfYears > 0) {
					data.setDateOfEntitle(data.getDateOfJoining());
				} else {
					data.setDateOfEntitle("01-Apr-1995");
				}
				if (rs.getString("ARREARFORMDATE") != null) {
					data.setFromarrearreviseddate(commonUtil.converDBToAppFormat(rs.getDate("ARREARFORMDATE"),"dd-MMM-yyyy"));
				}else{
					data.setFromarrearreviseddate("---");
				}
				if (rs.getString("ARREARTODATE") != null) {
					data.setToarrearreviseddate(commonUtil.converDBToAppFormat(rs.getDate("ARREARTODATE"),"dd-MMM-yyyy"));
				}else{
					data.setToarrearreviseddate("---");
				}
			/*	if (rs.getString("DATEOFENTITLE") != null) {
					data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
					chkDOE=commonUtil.converDBToAppFormat(data.getDateOfEntitle(), "dd-MMM-yyyy", "dd-MM-yyyy");
					
					String[] chckDOFArray=chkDOE.split("-");
					log.info("chkDOE"+chkDOE+""+chckDOFArray.length);
					if(Integer.parseInt(chckDOFArray[0])==30 || Integer.parseInt(chckDOFArray[0])==31){
						
					}
					if(Integer.parseInt(chckDOFArray[0])>1 && Integer.parseInt(chckDOFArray[1])==03){
						data.setDateOfEntitle("01-Apr-"+chckDOFArray[2]);
					}
				} else {
					data.setDateOfEntitle("---");
				}*/

				
				if (rs.getString("SEPER_FLAG")!=null) {
					seperationFlag = rs.getString("SEPER_FLAG");
				}
				if (rs.getInt("EMPAGE1") >= 58 && rs.getInt("EMPAGE1")<=59) {
					remarks = "Date of Leaving service:"+rs.getString("LASTATTAINED")+"<br/>Reason for Leaving Service:Attained 58 years";
				}
				if (rs.getString("ARREARDATE") != null) {
					arrearInfo=rs.getString("ARREARDATE");
				}
				
				if(seperationFlag.equals("N")){
					data.setSeperationReason("---");
					data.setSeperationDate("---");
				}
				
				if(rs.getString("REMARKS")!=null && !arrears.equals("N")&& formType.equals("Summary")){
					if(this.compareFinalSettlementDates(fromYear,toYear,arrearInfo)==true){
						remarks=remarks+rs.getString("REMARKS");
					}
				}
				if (data.getSeperationReason().trim().equals("Resignation")
						|| data.getSeperationReason().trim().equals(
								"Termination")) {
					remarks = remarks + data.getSeperationReason() + " On"
							+ data.getSeperationDate();
				} else if (data.getSeperationReason().trim().equals("Death")) {
					remarks = remarks + "Death Case & PF settled";
				} else if (data.getSeperationReason().trim().equals(
						"Retirement")) {
					remarks = remarks + "Retirement Case & PF settled";
				}
				data.setRemarks(remarks);
				log.info("getForm7EmployeeInfo============Employee Name"
						+ data.getEmployeeName() + "Seperation Reason"
						+ data.getSeperationReason() + "Arrear Condition"
						+ "remarks" + remarks);
				if(!payrevisionarrear.equals("DONTADD")){
					empinfo.add(data);
				}
				
				remarks = "";
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;

	}

	public String buildQueryEmpPFForm7ArrearsInfoPrinting(String range,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno,String toYear,String fromYear) {
		log
				.info("FinanceReportDAO::buildQueryEmpPFForm7ArrearsInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT EINFO.REFPENSIONNUMBER,EINFO.CPFACNO,EINFO.AIRPORTSERIALNUMBER,EINFO.EMPLOYEENO, INITCAP(EINFO.EMPLOYEENAME) AS EMPLOYEENAME,EINFO.DESEGNATION,EINFO.EMP_LEVEL,EINFO.DATEOFBIRTH,EINFO.DATEOFJOINING," +
		"EINFO.DATEOFSEPERATION_REASON,EINFO.DATEOFSEPERATION_DATE,EINFO.WHETHER_FORM1_NOM_RECEIVED,AINFO.Form7narration AS REMARKS,EINFO.AIRPORTCODE,EINFO.GENDER,EINFO.FHNAME,EINFO.MARITALSTATUS,EINFO.PERMANENTADDRESS,EINFO.TEMPORATYADDRESS," +
		"EINFO.WETHEROPTION,EINFO.SETDATEOFANNUATION,EMPFLAG,EINFO.DIVISION,EINFO.DEPARTMENT,EINFO.EMAILID,EINFO.EMPNOMINEESHARABLE,EINFO.REGION,EINFO.PENSIONNO,(LAST_DAY(EINFO.dateofbirth) + INTERVAL '60' year ) as DOR,EINFO.username," +
		"EINFO.LASTACTIVE,EINFO.RefMonthYear,EINFO.IPAddress,EINFO.OTHERREASON,decode(sign(round(months_between(EINFO.dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(EINFO.dateofjoining,'dd-Mon-yyyy'),to_char(EINFO.dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE," +
		"to_char(trunc((EINFO.dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', EINFO.dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT,(CASE  WHEN (EINFO.dateofbirth<last_day('"+toYear+"')) THEN  round(months_between(last_day('"+toYear+"'),EINFO.dateofbirth)/12) ELSE  round(months_between(EINFO.dateofbirth, last_day('"+toYear+"')) / 12) END)  as	EMPAGE1, " +
		"(CASE WHEN (EINFO.DATEOFSEPERATION_date < = '"+toYear+"' AND EINFO.DATEOFSEPERATION_date >='"+fromYear+"') THEN 'Y' ELSE 'N' END) as SEPER_FLAG,to_char((EINFO.dateofbirth + INTERVAL '58' year ),'dd-Mon-yyyy')  as LASTATTAINED,to_char(to_date(AINFO.ARREARDATE),'dd-Mon-yyyy') as ARREARDATE,round(months_between(NVL(EINFO.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF, '' as ARREARTODATE,'' as ARREARFORMDATE,ARREARFLAG AS CHKWTHRARRNOT  FROM EMPLOYEE_PERSONAL_INFO EINFO,EMPLOYEE_INFO_RPFC_FORMS AINFO WHERE EINFO.FORMSDISABLE='Y' AND EINFO.PENSIONNO=AINFO.PENSIONNO AND to_date(AINFO.ARREARDATE)>='"+fromYear+"' and to_date(AINFO.ARREARDATE)<='"+toYear+"' ";





		if (!range.equals("NO-SELECT")) {

			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);

			whereClause.append("  EINFO.PENSIONNO >=" + startIndex
					+ " AND EINFO.PENSIONNO <=" + endIndex);
			whereClause.append(" AND ");

		}

		if (!region.equals("") && !region.equals("AllRegions")) {
			whereClause.append(" EINFO.REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!toYear.equals("")) {
			whereClause.append(" EINFO.DATEOFJOINING <=LAST_DAY('" + toYear + "')");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("EINFO.PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if (region.equals("")
				&& (range.equals("NO-SELECT") && toYear.equals("") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log
				.info("FinanceReportDAO::buildQueryEmpPFForm7ArrearsInfoPrinting Leaving Method");
		return dynamicQuery;
	}



	private String getEmployeeForm7MappingPFInfo(String personalPFID,
			String personalCPFACCNO, String personalRegion) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfID = "", region = "", regionPFIDS = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"
					+ personalPFID + "'";
			// log.info("FinanceReportDAO::getEmployeeMappingPFInfo" +
			// sqlQuery);
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("CPFACNO") != null) {
					pfID = rs.getString("CPFACNO");

				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");

				}
				if (regionPFIDS.equals("")) {
					regionPFIDS = pfID + "," + region + "=";
				} else {
					regionPFIDS = regionPFIDS + pfID + "," + region + "=";
				}

			}
		/*	regionPFIDS = regionPFIDS + personalCPFACCNO + "," + personalRegion
					+ "=";*/
			log
					.info("=============getEmployeeForm7MappingPFInfo========================"
							+ regionPFIDS);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return regionPFIDS;
	}

	private ArrayList getForm7PensionInfo(String fromDate, String toDate,
			String pfIDS, String wetherOption, String region, boolean formFlag,
			String dateOfRetriment, String dateOfBirth, String pensionNo,boolean arrearsFlag,String formRevisedFlag) {
		log.info("FinanceReportDAO::getForm7PensionInfo" );
		DecimalFormat df = new DecimalFormat("#########0.00");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", formatMnth = "", chkDecMnthYear = "", findFromYear = "", findToYear = "";
		String monthYear = "", days = "", getMonth = "", condition="",sqlQuery = "",deptuationflag="N", calEmoluments = "",arrearFromDate="01-Sep-2009",arrearToDate="",arrearDatesInfo="";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;
		boolean yearBreakMonthFlag=false;
		boolean obFlag = false,fpfFund=false;
		double pensionVal=0.0;
		/*
		 * sqlQuery =
		 * "SELECT MONTHYEAR,ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,ROUND(REFADV) AS REFADV, AIRPORTCODE FROM EMPLOYEE_PENSION_VALIDATE WHERE (to_date(to_char(monthyear,'dd-Mon-yyyy'))>='"
		 * + fromDate + "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='" +
		 * toDate + "')" + " AND (" + pfIDS + ") ORDER BY TO_DATE(monthyear)";
		 */
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if(pensionNo!="" && !pensionNo.equals("")){
	    	condition=" or pensionno="+pensionNo;
	       }
		boolean arrearData=false;
		
		ArrayList OBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			if(Integer.parseInt(findFromYear)>=2008){
				yearBreakMonthFlag = true;
				if(arrearsFlag==false){
					
					sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
						+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
						+ "from employee_pension_validate where rownum <=to_date('"
						+ toDate
						+ "', 'dd-mon-yyyy') -to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
						+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,NVL(ARREARFLAG,'N') AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS  FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
						+ fromDate
						+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
						+ toDate
						+ "'))"
						+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
				}else{
					arrearDatesInfo=this.getArrearDate(con,fromDate,toDate,pensionNo);
					String[] arrearDts=arrearDatesInfo.split(",");
					arrearFromDate=arrearDts[0];
					arrearToDate=arrearDts[1];
					
					sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
						+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
						+ "from employee_pension_validate where rownum <=to_date('"
						+ toDate
						+ "', 'dd-mon-yyyy') -to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
						+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,NVL(ARREARFLAG,'N') AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS  FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
						+ arrearFromDate
						+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='"
						+ arrearToDate
						+ "')"
						+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
				}
				
			}else{
				yearBreakMonthFlag = false;
				sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
					+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,NVL(ARREARFLAG,'N') AS ARREARSMONTHS,DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS  FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND (("
					+ pfIDS
					+ ")"+condition+")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
			}
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			log.info("getForm7PensionInfo:======"+sqlQuery);
			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				formatMnth = rs.getString("FORMATMONTHYEAR");
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(formatMnth
							+ "/"
							+ commonUtil.converDBToAppFormat(cardInfo
									.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));

					} else {
						cardInfo.setMonthyear("---");
					}

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM"));

					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(formatMnth
								+ "/"
								+ commonUtil.converDBToAppFormat(cardInfo
										.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					}

				}
			
		
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}
				if (rs.getString("EMOLUMENTMONTHS") != null) {
					cardInfo.setEmolumentMonths(rs.getString("EMOLUMENTMONTHS".trim()));
				} else {
					cardInfo.setEmolumentMonths("1");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}
				if (rs.getString("ARREARAMOUNT") != null) {
					cardInfo.setDuepensionamount(rs.getString("ARREARAMOUNT"));
				} else {
					cardInfo.setDuepensionamount("0");
				}
				if (rs.getString("DUEEMOLUMENTS") != null) {
					cardInfo.setDueemoluments(rs.getString("DUEEMOLUMENTS"));
				} else {
					cardInfo.setDueemoluments("0");
				}
				if(rs.getString("ARREARSMONTHS")!=null){
					if(rs.getString("ARREARSMONTHS").trim().equals("Y")){
						arrearData=true;
					}
				}
					
				
				log.info("monthYear===========sss==========="+ cardInfo.getShnMnthYear()+"ARREARSMONTHS"+rs.getString("ARREARSMONTHS")+"arrearData"+arrearData + "Emoluments"+ cardInfo.getEmoluments());
				calEmoluments = this.calWages(cardInfo.getEmoluments(),
						cardInfo.getMonthyear(), wetherOption.trim(), false,
						false,cardInfo.getEmolumentMonths());
				
				
				
				if (chkDecMnthYear.trim().equals("Dec-95")) {
					if (!calEmoluments.equals("")) {
						calEmoluments = Double.toString(Double
								.parseDouble(calEmoluments) / 2);
					}

				}
				//log.info("monthYear======================"+ cardInfo.getShnMnthYear() + "calEmoluments"+ calEmoluments);
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}
				if (rs.getString("PENSIONCONTRI") != null) {
					pensionVal=rs.getDouble("PENSIONCONTRI");
				} else {
					pensionVal=0.0;
				}
				if (rs.getString("DEPUTATIONFLAG") != null) {
					deptuationflag=rs.getString("DEPUTATIONFLAG");
				} 
				
				if (region.equals("North Region")) {
					if (rs.getString("REFADV") != null) {
						cardInfo.setPrincipal(rs.getString("REFADV"));
					} else {
						cardInfo.setPrincipal("0");
					}
				} else {
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					if (region.equals("CHQIAD")) {

						loanPFWPaid = this
								.getEmployeeLoans(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						advancePFWPaid = this
								.getEmployeeAdvances(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						log.info("Region" + region + "loanPFWPaid"
								+ loanPFWPaid + "advancePFWPaid"
								+ advancePFWPaid);
						totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					} else if (region.equals("CHQNAD")) {
						loanPFWPaid = this
								.getEmployeeLoans(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						advancePFWPaid = this
								.getEmployeeAdvances(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					} else if (region.equals("North Region")) {
						if (rs.getString("DEDADV") != null) {
							advancePFWPaid = Double.parseDouble(rs
									.getString("DEDADV"));
							totalAdvancePFWPaid = advancePFWPaid;
						} else {
							advancePFWPaid = 0;
							totalAdvancePFWPaid = advancePFWPaid;
						}

					}
				}

				/*
				 * log.info("cardInfo.getShnMnthYear()" +
				 * cardInfo.getShnMnthYear() + "advancePFWPaid" +
				 * advancePFWPaid);
				 */
				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				log.info("monthYear" + cardInfo.getMonthyear()
						+ "dateOfRetriment" + dateOfRetriment);
				transMntYear = Date.parse(cardInfo.getMonthyear());
				empRetriedDt = Date.parse(dateOfRetriment);

				if (transMntYear > empRetriedDt) {
					contrFlag = true;
				} else if (transMntYear == empRetriedDt) {
					chkDOBFlag = true;
				}

				log.info("transMntYear" + transMntYear + "empRetriedDt"
						+ empRetriedDt);
				log.info("contrFlag" + contrFlag + "chkDOBFlag"
						+ chkDOBFlag);
				fpfFund=commonUtil.compareTwoDates(commonUtil.converDBToAppFormat(cardInfo
						.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy"),"Jan-1996");
				if (yearBreakMonthFlag == false) {
					
					if (flag == false) {
						if (!cardInfo.getMonthyear().equals("-NA-")
								&& !dateOfRetriment.equals("")) {

							if (contrFlag != true) {
								if(deptuationflag.equals("Y")){
									pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
								}else{
								pensionAsPerOption = this.pensionCalculation(
										cardInfo.getMonthyear(), cardInfo.getEmoluments(),
										wetherOption, region, rs
												.getString("emolumentmonths"));
								if (chkDOBFlag == true) {
									String[] dobList = dateOfBirth.split("-");
									days = dobList[0];
									getDaysBymonth = this
											.getNoOfDays(dateOfBirth);
									pensionAsPerOption = pensionAsPerOption
											* (Double.parseDouble(days) - 1)
											/ getDaysBymonth;
									retriredEmoluments = new Double(
											df1
													.format(Double
															.parseDouble(calEmoluments)
															* (Double
																	.parseDouble(days) - 1)
															/ getDaysBymonth))
											.doubleValue();
									calEmoluments = Double
											.toString(retriredEmoluments);
								}
								}
							}else {
								pensionAsPerOption = 0;
								calEmoluments = "0";
							}
							cardInfo.setPensionContribution(Double
									.toString(pensionAsPerOption));
						} else {
							cardInfo.setPensionContribution("0");
						}
					}else{
						cardInfo.setPensionContribution("0");
					}
				} else {
					pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
					if (contrFlag != true) {
					if (chkDOBFlag == true) {
						String[] dobList = dateOfBirth.split("-");
						days = dobList[0];
					
						getDaysBymonth = this.getNoOfDays(dateOfBirth);
						
						retriredEmoluments = new Double(df1.format(Double
								.parseDouble(calEmoluments)
								* (Double.parseDouble(days) - 1)
								/ getDaysBymonth)).doubleValue();
						calEmoluments = Double.toString(retriredEmoluments);
					}
					}else {
						if(arrearData==false){
							pensionAsPerOption = 0;
							calEmoluments = "0";
						}
						
                     
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				}
				if(fpfFund==true){
					if (!chkDecMnthYear.trim().equals("Dec-95")) {
						cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
					}else{
						pensionAsPerOption = this.pensionFormsCalculation(
								cardInfo.getMonthyear(), cardInfo.getEmoluments(),
								wetherOption, region,true,false, rs
										.getString("emolumentmonths"));
						cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
					}
				}else{
					cardInfo.setFpfFund("0");
				}
				log.info("Month Year================"+cardInfo.getMonthyear() + "flag" + flag + checkDate
						+ "Pension" + cardInfo.getPensionContribution()
						+ "calEmoluments" + calEmoluments);
				if(arrearData==false){
					cardInfo.setEmoluments(calEmoluments);
				}else{
					cardInfo.setEmoluments(df.format(Math.round(pensionAsPerOption*100/8.33)));
				}
				
				if (formFlag == true) {
					if (region.equals("CHQIAD")) {
						advPFDrawn = this.getEmployeeLoans(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN",
								pensionNo);
					} else if (region.equals("CHQNAD")) {
						advPFDrawn = this.getEmployeeLoans(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN",
								pensionNo);
					} else if (region.equals("North Region")) {
						advPFDrawn = 0;
					}
				}
				if (yearBreakMonthFlag == false) {
					aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				} else {
					aaiPF = rs.getDouble("PF");
				}
			
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if(rs.getString("FORM7NARRATION")!=null){
					cardInfo.setForm7Narration(rs.getString("FORM7NARRATION"));
				}else{
					cardInfo.setForm7Narration("---");
				}
				if(rs.getString("EMOLUMENTMONTHS")!=null){
					cardInfo.setEmolumentMonths(rs.getString("EMOLUMENTMONTHS"));
				}else{
					cardInfo.setEmolumentMonths("1");
				}
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				arrearData=false;
				
				//new code snippet added as on Sep-02
				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setOriginalEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setOriginalEmoluments("0");
				}
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return pensionList;
}


	public ArrayList rnfcForm7ZerosReport(String selectedYear, String month,
			String sortedColumn, String region, boolean formFlag,
			String airportCode, String pensionNo, String range, String empflag,
			String empName) {
		String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
		int toSelectYear = 0;
		ArrayList empList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		ArrayList form8List = new ArrayList();
		ArrayList getPensionList = new ArrayList();
		if (!month.equals("NO-SELECT")) {
			try {
				frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
			fromYear = "01-Apr-" + selectedYear;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else if (!selectedYear.equals("Select One")
				&& !month.equals("NO-SELECT")) {
			fromYear = "01-" + frmMonth + "-" + selectedYear;
			toYear = fromYear;
		} else {
			fromYear = Constants.FORMS_FROM_DATE;
			toYear = Constants.FORMS_TO_DATE;
		}

		empList = this.getForm7ZeroEmployeeInfo(range, sortedColumn, region,
				fromYear, toYear, airportCode, pensionNo, empflag, empName);
		String pensionInfo = "", regionInfo = "";

		for (int i = 0; i < empList.size(); i++) {
			personalInfo = (EmployeePersonalInfo) empList.get(i);

			if (!personalInfo.getDateOfBirth().equals("---")) {
				try {
					dateOfRetriment = this.getRetriedDate(personalInfo
							.getDateOfBirth());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			getPensionList = this.getForm7PensionZeroInfo(fromYear, toYear,
					personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(),
					false, dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo());
			personalInfo.setPensionList(getPensionList);

			dateOfRetriment = "";
			form8List.add(personalInfo);
		}
		return form8List;
	}

	private ArrayList getForm7PensionZeroInfo(String fromDate, String toDate,
			String pfIDS, String wetherOption, String region, boolean formFlag,
			String dateOfRetriment, String dateOfBirth, String pensionNo) {

		DecimalFormat df = new DecimalFormat("#########0.00");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", formatMnth = "", chkDecMnthYear = "";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "", calEmoluments = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;

		boolean obFlag = false;

		sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
				+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,emp.* from (select distinct to_char(to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
				+ "from employee_pension_validate where rownum <=to_date('"
				+ toDate
				+ "', 'dd-mon-yyyy') -to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
				+ toDate
				+ "'))"
				+ " AND ("
				+ pfIDS
				+ ")) emp where empdt.monyear =  emp.empmonyear  (+) and emp.empmonyear is null  ORDER BY TO_DATE(EMPMNTHYEAR)";

		log.info("FinanceReportDAO::getForm7PensionZeroInfo" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				formatMnth = rs.getString("FORMATMONTHYEAR");
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(formatMnth
							+ "/"
							+ commonUtil.converDBToAppFormat(cardInfo
									.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));

					} else {
						cardInfo.setMonthyear("---");
					}

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM"));

					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(formatMnth
								+ "/"
								+ commonUtil.converDBToAppFormat(cardInfo
										.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					}

				}
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}

				calEmoluments = this.calWages(cardInfo.getEmoluments(),
						cardInfo.getMonthyear(), wetherOption.trim(), false,
						false,"1");
				if (chkDecMnthYear.trim().equals("Dec-95")) {
					if (!calEmoluments.equals("")) {
						calEmoluments = Double.toString(Double
								.parseDouble(calEmoluments) / 2);
					}

				}
				log.info("monthYear======================"
						+ cardInfo.getShnMnthYear() + "calEmoluments"
						+ calEmoluments);
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}

				/*
				 * if (region.equals("CHQNAD")) {
				 * 
				 * if (rs.getString("CPFADVANCE") != null) {
				 * cardInfo.setPrincipal(rs.getString("CPFADVANCE")); } else {
				 * cardInfo.setPrincipal("0"); } } else
				 */if (region.equals("North Region")) {
					if (rs.getString("REFADV") != null) {
						cardInfo.setPrincipal(rs.getString("REFADV"));
					} else {
						cardInfo.setPrincipal("0");
					}
				} else {
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					if (region.equals("CHQIAD")) {

						loanPFWPaid = this
								.getEmployeeLoans(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						advancePFWPaid = this
								.getEmployeeAdvances(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						log.info("Region" + region + "loanPFWPaid"
								+ loanPFWPaid + "advancePFWPaid"
								+ advancePFWPaid);
						totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					} else if (region.equals("CHQNAD")) {
						loanPFWPaid = this
								.getEmployeeLoans(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						advancePFWPaid = this
								.getEmployeeAdvances(con, cardInfo
										.getShnMnthYear(), pfIDS, "ADV.PAID",
										pensionNo);
						totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					} else if (region.equals("North Region")) {
						if (rs.getString("DEDADV") != null) {
							advancePFWPaid = Double.parseDouble(rs
									.getString("DEDADV"));
							totalAdvancePFWPaid = advancePFWPaid;
						} else {
							advancePFWPaid = 0;
							totalAdvancePFWPaid = advancePFWPaid;
						}

					}
				}

				/*
				 * log.info("cardInfo.getShnMnthYear()" +
				 * cardInfo.getShnMnthYear() + "advancePFWPaid" +
				 * advancePFWPaid);
				 */
				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				log.info("monthYear" + cardInfo.getMonthyear()
						+ "dateOfRetriment" + dateOfRetriment);
				if (flag == false) {
					if (!cardInfo.getMonthyear().equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(cardInfo.getMonthyear());
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == empRetriedDt) {
							chkDOBFlag = true;
						}
					}
					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					if (contrFlag != true) {
						pensionAsPerOption = this.pensionCalculation(cardInfo
								.getMonthyear(), calEmoluments, wetherOption,
								region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionAsPerOption = pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth;
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}

					} else {
						pensionAsPerOption = 0;
						calEmoluments = "0";
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				} else {
					cardInfo.setPensionContribution("0");
				}
				log.info(cardInfo.getMonthyear() + "flag" + flag + checkDate
						+ "Pension" + cardInfo.getPensionContribution()
						+ "calEmoluments" + calEmoluments);

				cardInfo.setEmoluments(calEmoluments);
				if (formFlag == true) {
					if (region.equals("CHQIAD")) {
						advPFDrawn = this.getEmployeeLoans(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN",
								pensionNo);
					} else if (region.equals("CHQNAD")) {
						advPFDrawn = this.getEmployeeLoans(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN",
								pensionNo);
					} else if (region.equals("North Region")) {
						advPFDrawn = 0;
					}
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return pensionList;
	}

	private ArrayList getForm7ZeroEmployeeInfo(String range,
			String sortedColumn, String region, String fromYear, String toYear,
			String airportCode, String pensionNo, String empNameFlag,
			String empName) {
		log.info("FinanceReportDAO::getForm7ZeroEmployeeInfo");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String remarks = "", sqlQuery = "", pensionAppednQry = "", pfidWithRegion = "", appendRegionTag = "", nextAppendRegionTag = "", appendAirportTag = "", appendPenTag = "";
		EmployeePersonalInfo data = null;
		ArrayList empinfo = new ArrayList();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = this.buildQuerygetEmployeePFInfoPrinting(range, region,
					empNameFlag, empName, sortedColumn, pensionNo,airportCode);
			if(sqlQuery.toLowerCase().indexOf("where")!=-1){
				sqlQuery=sqlQuery+" AND FORMSDISABLE='Y'";
			}else{
				sqlQuery=sqlQuery+" WHERE FORMSDISABLE='Y'";
			}
			
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();
				CommonDAO commonDAO = new CommonDAO();
				data = commonDAO.loadPersonalInfo(rs);
				pfidWithRegion = this.getEmployeeForm7MappingPFInfo(data
						.getOldPensionNo(), data.getCpfAccno(), data
						.getRegion());
				log
						.info("==========================================================="
								+ pfidWithRegion);
				if (pfidWithRegion.equals("")) {
					pfidWithRegion = data.getCpfAccno() + ","
							+ data.getRegion() + "=";
				}

				if (!pfidWithRegion.equals("")) {
					String[] pfIDLists = pfidWithRegion.split("=");
					data.setPfIDString(this.preparedCPFString(pfIDLists));
				}

				if (data.getWetherOption().trim().equals("---")) {
					data.setRemarks("Wether Option is not available");
				}

				if (rs.getString("DATEOFENTITLE") != null) {
					data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
				} else {
					data.setDateOfEntitle("---");
				}

				if (rs.getInt("EMPAGE") == 58) {
					remarks = "Attained 58 years";
				}
				if (data.getSeperationReason().trim().equals("Resignation")
						|| data.getSeperationReason().trim().equals(
								"Termination")) {
					remarks = remarks + data.getSeperationReason() + " On"
							+ data.getSeperationDate();
				} else if (data.getSeperationReason().trim().equals("Death")) {
					remarks = remarks + "Death Case & PF settled";
				} else if (data.getSeperationReason().trim().equals(
						"Retirement")) {
					remarks = remarks + "Retirement Case & PF settled";
				}
				data.setRemarks(remarks);
				// log.info("Employee Name"+data.getEmployeeName()+
				// "Seperation Reason"
				// +data.getSeperationReason()+"Age"+rs.getInt
				// ("EMPAGE")+"remarks"+remarks);

				empinfo.add(data);
				remarks = "";
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;

	}

	public ArrayList financePersonalByPFID(String monthyear, String region,
			String airportcode, String sortingOrder,
			String frmSeperationReason, boolean reasonFlag)
			throws InvalidDataException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePersonalInfo personalInfo = null;
		ArrayList form3PSList = new ArrayList();
		String seperationReason = "", transInfo = "", sqlQuery = "", flag = "", dojMnth = "", dojYear = "", selectedMonth = "", transMonthYear = "", selectedYear = "";
		selectedMonth = commonUtil.converDBToAppFormat(monthyear,
				"dd-MMM-yyyy", "MMM");
		selectedYear = commonUtil.converDBToAppFormat(monthyear, "dd-MMM-yyyy",
				"yyyy");
		long employeeOFYears = 0;
		/*
		 * StringsqlQuery=
		 * "SELECT to_char(DATEOFJOINING,'Mon') as dojmonth,to_char(DATEOFJOINING,'yyyy') as dojyear,REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
		 * +
		 * "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
		 * +
		 * "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO "
		 * +"WHERE NVL(DATEOFJOINING,'01-Mar-2009')<=last_day('"+monthyear+
		 * "') ORDER BY PENSIONNO";
		 */
		if (frmSeperationReason.equals("Retirement") && reasonFlag == true) {
			sqlQuery = this.buildQueryPersonalFormPFIDByRetire(
					frmSeperationReason, region, airportcode, monthyear,
					sortingOrder);
		} else {
			sqlQuery = this.buildQueryPersonalFormPFID(frmSeperationReason,
					region, airportcode, monthyear, sortingOrder);
		}

		log.info("financePersonalByPFID::Query" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				personalInfo = new EmployeePersonalInfo();
				personalInfo = commonDAO.loadPersonalInfo(rs);

				if (!personalInfo.getDateOfJoining().equals("")
						&& !personalInfo.getDateOfBirth().equals("---")) {

					long noOfYears = 0;
					noOfYears = rs.getLong("ENTITLEDIFF");
					if (noOfYears > 0) {
						employeeOFYears = commonUtil.getDateDifference(
								personalInfo.getDateOfBirth(), personalInfo
										.getDateOfJoining());

						personalInfo.setAgeAtEntry(Long
								.toString(employeeOFYears));
						personalInfo.setDateOfEntitle(personalInfo
								.getDateOfJoining());
					} else {
						employeeOFYears = commonUtil.getDateDifference(
								personalInfo.getDateOfBirth(), "01-Apr-1995");
						personalInfo.setAgeAtEntry(Long
								.toString(employeeOFYears));

						personalInfo.setDateOfEntitle("01-Apr-1995");

					}
				} else {
					personalInfo.setDateOfEntitle("---");
					personalInfo.setAgeAtEntry("---");
				}
				if (rs.getString("dojmonth") != null) {
					dojMnth = rs.getString("dojmonth");
				} else {
					dojMnth = "---";
				}
				if (rs.getString("dojyear") != null) {
					dojYear = rs.getString("dojyear");
				} else {
					dojYear = "---";
				}
				if (rs.getString("DATEOFSEPERATION_REASON") != null) {
					seperationReason = rs.getString("DATEOFSEPERATION_REASON");
					if (!seperationReason.equals("Other")) {
						if (seperationReason.equals("Death")) {
							personalInfo.setRemarks("Expired & PF Settled");
						} else if (seperationReason.equals("Retirement")) {
							personalInfo.setRemarks("Retired & PF Settled");
						} else {
							personalInfo.setRemarks(seperationReason);
						}

					}

				} else {
					seperationReason = "---";
					personalInfo.setRemarks("---");
				}

				/*
				 * log.info("selectedMonth========="+selectedMonth+"dojMnth======="
				 * +dojMnth); if(!seperationReason.equals("---")){
				 * if(!seperationReason.equals("Other")){ flag="OLD"; //retrived
				 * old year transMonthYear="01-Oct-2007"; }else{
				 * transMonthYear=monthyear; }
				 * 
				 * }else if(!dojMnth.equals("---") && !dojYear.equals("---")){
				 * if(dojMnth.toUpperCase().equals(selectedMonth.toUpperCase())
				 * && dojYear.equals(selectedYear)){ flag="NO"; }else{
				 * transMonthYear=monthyear; flag="NEW"; } }
				 * log.info("transMonthYear============================="
				 * +transMonthYear+"flag======="+flag); if(!flag.equals("NO") &&
				 * !flag.equals("")){
				 * 
				 * transInfo=this.getWagesForm3Report(con,transMonthYear,
				 * personalInfo.getPensionNo(),flag); } String[]
				 * transData=transInfo.split(","); if(transData.length>0){
				 * personalInfo.setBasicWages(transData[0]); }else{
				 * personalInfo.setBasicWages("0"); } if(flag.equals("NO")){
				 * personalInfo.setBasicWages("0"); }
				 */
				form3PSList.add(personalInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form3PSList;
	}

	public String buildQueryPersonalFormPFID(String seperationreason,
			String region, String airportcode, String monthyear,
			String sortingOrder) {
		log
				.info("FinancialReportDAO::buildQueryPersonalFormPFID-- Entering Method");
		String dataString = "";
		boolean chkflag = false;
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		sqlQuery = "SELECT to_char(DATEOFJOINING,'Mon') as dojmonth,to_char(DATEOFJOINING,'yyyy') as dojyear,REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
				+ "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
				+ "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO ";
		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!monthyear.equals("")) {
			dataString = "NVL(DATEOFSEPERATION_DATE,'" + monthyear
					+ "')<=last_day('" + monthyear + "')";
			whereClause.append(dataString);
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}
		log
				.info("seperationreason========================="
						+ seperationreason);
		if (!seperationreason.equals("")) {
			if (!seperationreason.equals("NO-SELECT")) {
				whereClause.append(" DATEOFSEPERATION_REASON ='"
						+ seperationreason + "'");
				whereClause.append(" AND ");
			} else {
				whereClause.append(" DATEOFSEPERATION_REASON IS NULL");
				whereClause.append(" AND ");
			}

		}

		query.append(sqlQuery);
		if ((region.equals("")) && (monthyear.equals(""))
				&& (airportcode.equals("")) && (seperationreason.equals(""))) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = " ORDER BY " + sortingOrder + " ASC";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("FinancialReportDAO::buildQueryPersonalFormPFID Leaving Method");
		return dynamicQuery;
	}

	public String buildQueryPersonalFormPFIDByRetire(String seperationreason,
			String region, String airportcode, String monthyear,
			String sortingOrder) {
		log
				.info("FinancialReportDAO::buildQueryPersonalFormPFIDByRetire-- Entering Method");
		String dataString = "";
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		if (monthyear.equals("")) {
			monthyear = "01-Dec-2009";
		}
		sqlQuery = "SELECT to_char(DATEOFJOINING,'Mon') as dojmonth,to_char(DATEOFJOINING,'yyyy') as dojyear,REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
				+ "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
				+ "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO WHERE round(round(months_between('"
				+ monthyear
				+ "',dateofbirth),3)/12,2)>=58 AND DATEOFSEPERATION_REASON IS NULL ";

		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		if (!airportcode.equals("")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = " ORDER BY " + sortingOrder + " ASC";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("FinancialReportDAO::buildQueryPersonalFormPFIDByRetire Leaving Method");
		return dynamicQuery;
	}
	 //	By Radha On 29-Sep-2011  for stop back ground process for pf card
// PFCARD 
	public ArrayList empPFCardReportPrint(String range, String region,
			String selectedYear, String empNameFlag, String empName,
			String sortedColumn, String pensionno, String lastmonthFlag,
			String lastmonthYear,String airportcode) {
		log.info("FinancialReportDAO::empPFCardReportPrint");
		String fromYear = "", toYear = "", dateOfRetriment = "";
		Connection con = null;
		if (!selectedYear.equals("Select One")) {
			fromYear = "01-Apr-" + selectedYear;
			int toSelectYear = 0;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Mar-2011";
		}
		int formFrmYear = 0, formToYear = 0, finalSttlementDtYear = 0, formMonthYear = 0;
		try {
			formFrmYear = Integer.parseInt(commonUtil.converDBToAppFormat(
					fromYear, "dd-MMM-yyyy", "yyyy"));
			formToYear = Integer.parseInt(commonUtil.converDBToAppFormat(
					toYear, "dd-MMM-yyyy", "yyyy"));

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeCardReportInfo cardInfo = null;
		ArrayList list = new ArrayList();
		ArrayList ptwList = new ArrayList();
		ArrayList finalSttmentList = new ArrayList();
		String appEmpNameQry = "", finalSettlementDate = "";
		ArrayList cardList = new ArrayList();
		int arrerMonths=0;
		boolean finalStFlag = false;
		try {
			con = commonDB.getConnection();
			empDataList = this.getEmployeePFInfoPrinting(con, range, region,
					empNameFlag, empName, sortedColumn, pensionno,
					lastmonthFlag, lastmonthYear,airportcode,fromYear,toYear);

			for (int i = 0; i < empDataList.size(); i++) {
				cardInfo = new EmployeeCardReportInfo();
				personalInfo = (EmployeePersonalInfo) empDataList.get(i);
				try {
					dateOfRetriment = this.getRetriedDate(personalInfo
							.getDateOfBirth());
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					log.printStackTrace(e);
				}
				log.info("FincialReportDAO:::empPFCardReportPrint:Final Settlement Date"+personalInfo.getFinalSettlementDate()+"Resttlement Date"+personalInfo.getResttlmentDate());
				if (!personalInfo.getFinalSettlementDate().equals("---")) {
					finalSttlementDtYear = Integer.parseInt(commonUtil
							.converDBToAppFormat(personalInfo
									.getFinalSettlementDate(), "dd-MMM-yyyy",
									"yyyy"));
					formMonthYear = Integer.parseInt(commonUtil
							.converDBToAppFormat(personalInfo
									.getFinalSettlementDate(), "dd-MMM-yyyy",
									"MM"));
					
					if ( finalSttlementDtYear<= formFrmYear) {
						finalStFlag = true;
						log.info("finalSttlementDtYear<= formFrmYear");
					} else if (formToYear == finalSttlementDtYear
							&& formMonthYear < 4) {
						finalStFlag = true;
						log.info("formToYear == finalSttlementDtYear&& formMonthYear < 4");
					} else {
						finalStFlag = false;
						log.info("con2 else");
					}
					
					if (finalStFlag == true) {

						finalSettlementDate = commonUtil.converDBToAppFormat(
								personalInfo.getFinalSettlementDate(),
								"dd-MMM-yyyy", "MMM-yyyy");
					} else {
						personalInfo.setFinalSettlementDate("---");
						finalSettlementDate = "---";
					}

				} else {
					finalSettlementDate = "---";
				}
				log.info("Before ProcessingAdjOB formFrmYear" + formFrmYear + "formToYear"
						+ formToYear + "finalSttlementDtYear"+finalSttlementDtYear+"finalStFlag" + finalStFlag
						+ "formMonthYear" + formMonthYear+"Final Settlement Date" + finalSettlementDate);
				
				//ProcessforAdjOb(personalInfo.getPensionNo(),true);
				list = this.getEmployeePensionCard(con, fromYear, toYear,
						personalInfo.getPfIDString(), personalInfo
								.getWetherOption(), personalInfo.getRegion(),
						true, dateOfRetriment, personalInfo.getDateOfBirth(),
						personalInfo.getOldPensionNo(), finalSettlementDate);
				
				// Flag is not used in the last paramter of getPTWDetails method

				ptwList = this.getPTWDetails(con, fromYear, toYear,
						personalInfo.getCpfAccno(), personalInfo
								.getOldPensionNo());

				finalSttmentList = this.getFinalSettlement(con, fromYear,
						toYear, personalInfo.getPfIDString(), personalInfo
								.getOldPensionNo());
				log.info("PF Card====================Final Settlement Date"+personalInfo.getFinalSettlementDate());
						
				if (!personalInfo.getFinalSettlementDate().equals("---")) {
					arrerMonths=this.getArrearInfo(con,fromYear, toYear,personalInfo.getOldPensionNo());
					log.info("Arrear+FinalSettlement"+commonUtil.compareTwoDates(
							finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
					if(commonUtil.compareTwoDates(
							finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy"))==true){
						//Final settlement date is lower than fromYear
						personalInfo.setChkArrearFlag("N");
						log.info("ChkArrearFlag"+personalInfo.getChkArrearFlag());
						if(!personalInfo.getResttlmentDate().equals("---")){
							cardInfo.setInterNoOfMonths(12);
							cardInfo.setNoOfMonths(this
									.numOfMnthFinalSettlement(commonUtil
											.converDBToAppFormat(personalInfo
													.getResttlmentDate(),
													"dd-MMM-yyyy", "MMM")));
						}else if(arrerMonths!=0){
							cardInfo.setInterNoOfMonths(12);
							cardInfo.setNoOfMonths(12);
							
						}else{
							cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
							cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
							
						}
							
					}else{
						
						personalInfo.setChkArrearFlag("Y");
						log.info("Else Condtion compareTwoDates ChkArrearFlag"+personalInfo.getChkArrearFlag());
						
						cardInfo.setNoOfMonths(this
								.numOfMnthFinalSettlement(commonUtil
										.converDBToAppFormat(personalInfo
												.getFinalSettlementDate(),
												"dd-MMM-yyyy", "MMM")));
						
						cardInfo.setArrearNoOfMonths(arrerMonths);
						cardInfo.setInterNoOfMonths(12);
					}
					
				} else {
					personalInfo.setChkArrearFlag("N");
					log.info("ChkArrearFlag"+personalInfo.getChkArrearFlag());
					cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
					cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
				}
				log.info("PF Card====Final Settlement Date"+personalInfo.getFinalSettlementDate()+"Resettlement Date"+personalInfo.getResttlmentDate()+"fromYear"+fromYear+"NO.Of Months"+cardInfo.getNoOfMonths()+"arrerMonths======"+arrerMonths);
				cardInfo.setFinalSettmentList(finalSttmentList);
				cardInfo.setArrearInfo(this.getArrearData(con,fromYear, toYear,personalInfo.getOldPensionNo()));
				if(finalSttmentList.size()!=0){
					cardInfo.setOrderInfo(this.getSanctionOrderInfo(con,fromYear, toYear,personalInfo.getOldPensionNo()));
				}else{
					cardInfo.setOrderInfo("");
				}
				
				cardInfo.setPersonalInfo(personalInfo);
				cardInfo.setPensionCardList(list);
				
				
				cardInfo.setPtwList(ptwList);
				cardList.add(cardInfo);
			}

		} catch (Exception se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(con, null, null);
		}

		return cardList;
	}



	public String findSettlementDt(String dateOfSettlement)
			throws InvalidDataException {
		dateOfSettlement = commonUtil.converDBToAppFormat(dateOfSettlement,
				"dd-MMM-yyyy", "dd-MM-yyyy");
		String[] dobElements = dateOfSettlement.split("-");
		int day = 0, month = 0, year = 0;
		String settlementDt = "", formatedRtDt = "";
		day = Integer.parseInt(dobElements[0]);
		month = Integer.parseInt(dobElements[1]);
		year = Integer.parseInt(dobElements[2]);
		if (day < 30) {
			month = month - 1;
			day = 1;
		}
		formatedRtDt = day + "-" + month + "-" + year;
		settlementDt = commonUtil.converDBToAppFormat(formatedRtDt,
				"dd-MM-yyyy", "dd-MMM-yyyy");
		return settlementDt;
	}

	public ArrayList getEmployeePFInfoPrinting(String range, String region,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno, String lastmonthFlag, String lastmonthYear) {
		CommonDAO commonDAO = new CommonDAO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "";
		EmployeePersonalInfo data = null;

		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if (region.equals("NO-SELECT")) {
				region = "";
			}
			if (lastmonthFlag.equals("true")) {
				sqlQuery = this.buildQueryEmpPFTransInfoPrinting(range, region,
						empNameFlag, empName, sortedColumn, pensionno,
						lastmonthYear,"");
			} else {
				sqlQuery = this.buildQuerygetEmployeePFInfoPrinting(range,
						region, empNameFlag, empName, sortedColumn, pensionno);
			}

			log.info("FinanceReportDAO::getEmployeePFInfoPrinting" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
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
				log.info(data.getWhetherOptionDescr());
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());

				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;
	}

	public String buildQuerygetEmployeePFInfoPrinting(String range,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno) {
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		log.info("pensionno  " +pensionno);
		sqlQuery = "SELECT EPI.REFPENSIONNUMBER,EPI.RESETTLEMENTDATE,EPI.CPFACNO,EPI.AIRPORTSERIALNUMBER,EPI.EMPLOYEENO, INITCAP(EPI.EMPLOYEENAME) AS EMPLOYEENAME,EPI.DESEGNATION,EPI.EMP_LEVEL,EPI.DATEOFBIRTH,EPI.DATEOFJOINING,EPI.DATEOFSEPERATION_REASON,EPI.DATEOFSEPERATION_DATE,EPI.WHETHER_FORM1_NOM_RECEIVED,REMARKS," +
				"EPI.AIRPORTCODE,EPI.GENDER,EPI.FHNAME,EPI.MARITALSTATUS,EPI.PERMANENTADDRESS,EPI.TEMPORATYADDRESS,EPI.WETHEROPTION,EPI.SETDATEOFANNUATION,EPI.EMPFLAG,EPI.DIVISION,EPI.DEPARTMENT,EPI.EMAILID,EPI.EMPNOMINEESHARABLE,EPI.REGION,EPI.PENSIONNO,(LAST_DAY(EPI.dateofbirth) + INTERVAL '60' year ) as DOR,EPI.username,EPI.LASTACTIVE,EPI.RefMonthYear," +
				"EPI.IPAddress,EPI.OTHERREASON,decode(sign(round(months_between(EPI.dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(EPI.dateofjoining,'dd-Mon-yyyy'),to_char(EPI.dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((EPI.dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB," +
				"ROUND(months_between('01-Apr-1995', EPI.dateofbirth) / 12) EMPAGE,EPI.FINALSETTLMENTDT,PADJ.ADJDATE,PADJ.ADJAMOUNT,PADJ.ADJINT FROM EMPLOYEE_PERSONAL_INFO EPI,EMPLOYEE_PENSION_ADJUSTMENTS PADJ WHERE  EPI.PENSIONNO = PADJ.PENSIONNO(+) ";
	log.info(sqlQuery);
		if (!range.equals("NO-SELECT")) {

			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);

			whereClause.append("  EPI.PENSIONNO >=" + startIndex
					+ " AND EPI.PENSIONNO <=" + endIndex);
			whereClause.append(" AND ");

		}

		if (!region.equals("") && !region.equals("AllRegions")) {
			whereClause.append(" EPI.REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("EPI.PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if (region.equals("")
				&& (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting Leaving Method");
		return dynamicQuery;
	}


	public String updatePCTrans(String range, String region,
			String selectedYear, FileWriter fw, String chkPendingFlag) {
		log.info("FinancialReportDAO::updatePCTrans");
		String fromYear = "", toYear = "", dateOfRetriment = "";
		if (!selectedYear.equals("Select One")) {
			fromYear = "01-Apr-" + selectedYear;
			int toSelectYear = 0;
			toSelectYear = Integer.parseInt(selectedYear) + 1;
			toYear = "01-Mar-" + toSelectYear;
		} else {
			fromYear = "01-Apr-1995";
			toYear = "01-Mar-2009";
		}

		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeCardReportInfo cardInfo = null;
		int transInserted = 0;
		StringBuffer buffer = new StringBuffer();
		ArrayList ptwList = new ArrayList();
		String appEmpNameQry = "";

		if (chkPendingFlag.equals("true")) {
			empDataList = this.updatePendingEmpPCInfo();
		} else {
			empDataList = this.updateEmpPCInfo(range, region);
		}

		ArrayList cardList = new ArrayList();
		for (int i = 0; i < empDataList.size(); i++) {
			cardInfo = new EmployeeCardReportInfo();
			personalInfo = (EmployeePersonalInfo) empDataList.get(i);
			try {
				dateOfRetriment = this.getRetriedDate(personalInfo
						.getDateOfBirth());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
			transInserted = this.updateEmpPC200809(fromYear, toYear,
					personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(), true,
					dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo());
			try {
				fw
						.write(commonUtil.leadingZeros(5, personalInfo
								.getPensionNo())
								+ "================"
								+ transInserted
								+ "========"
								+ personalInfo.getEmployeeName()
								+ "=========================================================="
								+ personalInfo.getPfIDString() + "\n");
				fw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return buffer.toString();
	}

	private ArrayList updateEmpPCInfo(String range, String region) {
		CommonDAO commonDAO = new CommonDAO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "";
		EmployeePersonalInfo data = null;

		ArrayList empinfo = new ArrayList();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if (region.equals("NO-SELECT")) {
				region = "";
			}
			sqlQuery = this.buildQueryUpdateEmpPCInfo(range, region);
			log.info("FinanceReportDAO::buildQueryUpdateEmpPCInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
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
				log.info(data.getWhetherOptionDescr());
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());

				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;
	}

	public String buildQueryUpdateEmpPCInfo(String range, String region) {
		log
				.info("FinanceReportDAO::buildQueryUpdateEmpPCInfo-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO";
		if (!range.equals("NO-SELECT")) {
			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);

			whereClause.append("  PENSIONNO >=" + startIndex
					+ " AND PENSIONNO <=" + endIndex);
			whereClause.append(" AND ");

		}

		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("")) && (range.equals("NO-SELECT"))) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log.info("FinanceReportDAO::buildQueryUpdateEmpPCInfo Leaving Method");
		return dynamicQuery;
	}

	private int updateEmpPC200809(String fromDate, String toDate, String pfIDS,
			String wetherOption, String region, boolean formFlag,
			String dateOfRetriment, String dateOfBirth, String pensionNo) {

		DecimalFormat df = new DecimalFormat("#########0.00");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "", transRegion = "", transCpfaccno = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("updateEmpPC200809===checkDate==" + checkDate + "flag==="
				+ flag);
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0;
		int inserted = 0, totalInserted = 0;
		boolean obFlag = false;

		sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,emp.* from (select distinct to_char(to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
				+ "from employee_pension_validate where rownum <=to_date('"
				+ toDate
				+ "', 'dd-mon-yyyy') -to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,REGION,CPFACCNO FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
				+ toDate
				+ "'))"
				+ " AND ("
				+ pfIDS
				+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";

		log.info("FinanceReportDAO::updateEmpPC200809" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));
					} else {
						cardInfo.setMonthyear("---");
					}
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy",
								"MMM-yy"));
					}

				}

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}
				if (region.equals("North Region")) {
					if (rs.getString("REFADV") != null) {
						cardInfo.setPrincipal(rs.getString("REFADV"));
					} else {
						cardInfo.setPrincipal("0");
					}
				} else {
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("Region" + region + "loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				}

				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}
				if (flag == false) {
					if (!cardInfo.getMonthyear().equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(cardInfo.getMonthyear());
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == empRetriedDt) {
							chkDOBFlag = true;
						}
					}
					log.info("updateEmpPC200809::monthYear"
							+ cardInfo.getMonthyear() + "dateOfRetriment"
							+ dateOfRetriment + "transMntYear" + transMntYear
							+ "empRetriedDt" + empRetriedDt + "contrFlag"
							+ contrFlag + "chkDOBFlag" + chkDOBFlag);

					if (contrFlag != true) {
						pensionAsPerOption = this.pensionCalculation(cardInfo
								.getMonthyear(), cardInfo.getEmoluments(),
								wetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				} else {
					cardInfo.setPensionContribution("0");
				}
				log.info("flag" + flag + checkDate + "Pension"
						+ cardInfo.getPensionContribution());
				if (formFlag == true) {

					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				if (rs.getString("REGION") != null) {
					transRegion = rs.getString("REGION");
				}
				if (rs.getString("CPFACCNO") != null) {
					transCpfaccno = rs.getString("CPFACCNO");
				}

				inserted = this.updateTransTblPC(transCpfaccno, con,
						transRegion, cardInfo.getMonthyear(),
						pensionAsPerOption, aaiPF);
				totalInserted = totalInserted + inserted;

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return totalInserted;
	}

	private int updateTransTblPC(String cpfaccno, Connection con,
			String region, String monthyear, double pensionval, double pf) {
		Statement st = null;
		String sqlInsQuery = "";
		int inserted = 0;
		log.info("updateTransTblPC::cpfaccno" + cpfaccno + "region" + region
				+ "monthyear" + monthyear);
		try {

			sqlInsQuery = "UPDATE employee_pension_validate SET pensioncontri=round("
					+ pensionval
					+ "),PF=round('"
					+ pf
					+ "') WHERE CPFACCNO='"
					+ cpfaccno
					+ "' AND REGION='"
					+ region
					+ "' AND MONTHYEAR='" + monthyear + "' AND EMPFLAG='Y'";
			st = con.createStatement();
			log.info("insertOBInfo::Inserted Query" + sqlInsQuery);
			inserted = st.executeUpdate(sqlInsQuery);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return inserted;
	}

	public ArrayList getFinalSettlement(String fromYear, String toYear,
			String pfIDS, String pensionno) {
		log.info("Purpose===getFinalSettlement");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String finalEmpSub = "", finalAAICont = "", finalPensionCon = "", finalNetAmount = "", finalPurpose = "", finalPensionNo = "", finalCpfaccno = "", finalAirportcode = "", finalRegion = "";
		String finalStlmntDt = "", finalEmployeeNo = "", finalEmpName = "";

		ArrayList finalSettlementList = new ArrayList();

		sqlQuery = "SELECT PENSIONNO,CPFACCNO,EMPLOYEENO,PURPOSE,EMPLOYEENAME,FINEMP,FINAAI,PENCON,NETAMOUNT,SETTLEMENTDATE,AIRPORTCODE,REGION FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE "
				+ "(" + pfIDS + ") ORDER BY PENSIONNO";

		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("PENSIONNO") != null) {
					finalPensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("CPFACCNO") != null) {
					finalCpfaccno = rs.getString("CPFACCNO");
				} else {
					finalCpfaccno = "---";
				}
				if (rs.getString("EMPLOYEENO") != null) {
					finalEmployeeNo = rs.getString("EMPLOYEENO");
				} else {
					finalEmployeeNo = "---";
				}
				if (rs.getString("PURPOSE") != null) {
					finalPurpose = rs.getString("PURPOSE");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					finalEmpName = rs.getString("EMPLOYEENAME");
				} else {
					finalEmpName = "---";
				}
				if (rs.getString("FINEMP") != null) {
					finalEmpSub = rs.getString("FINEMP");
				} else {
					finalEmpSub = "0";
				}
				if (rs.getString("FINAAI") != null) {
					finalAAICont = rs.getString("FINAAI");
				} else {
					finalAAICont = "0";
				}
				if (rs.getString("PENCON") != null) {
					finalPensionCon = rs.getString("PENCON");
				} else {
					finalPensionCon = "0";
				}
				if (rs.getString("NETAMOUNT") != null) {
					finalNetAmount = rs.getString("NETAMOUNT");
				} else {
					finalNetAmount = "0";
				}
				if (rs.getString("AIRPORTCODE") != null) {
					finalAirportcode = rs.getString("AIRPORTCODE");
				}
				if (rs.getString("REGION") != null) {
					finalRegion = rs.getString("REGION");
				}
				if (rs.getString("SETTLEMENTDATE") != null) {
					finalStlmntDt = commonUtil.getDatetoString(rs
							.getDate("SETTLEMENTDATE"), "dd-MMM-yyyy");
				}
				finalSettlementList.add(finalPensionNo);
				finalSettlementList.add(finalCpfaccno);
				finalSettlementList.add(finalEmployeeNo);
				finalSettlementList.add(finalPurpose);
				finalSettlementList.add(finalEmpName);
				finalSettlementList.add(finalEmpSub);
				finalSettlementList.add(finalAAICont);
				finalSettlementList.add(finalPensionCon);
				finalSettlementList.add(finalNetAmount);
				finalSettlementList.add(finalAirportcode);
				finalSettlementList.add(finalRegion);
				finalSettlementList.add(finalStlmntDt);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return finalSettlementList;
	}

	public ArrayList calOBPFCard(double interest, ArrayList obList,
			double totalAAINet, double totalAaiIntNet, double totalEmpNet,
			double totalEmpIntNet, double totalPensionContr,
			double penInterest, double advancePFWPaid, double principle) {
		ArrayList finalOBList = new ArrayList();
		String sqlInsQuery = "", obFromTrFlag = "", obYear = "", obMnthYear = "";
		int year = 0, inserted = 0;
		Long empNetOB = null, aaiNetOB = null, pensionOB = null, principalOB = null;
		Long empNetAdjOB = null, aaiNetAdjOB = null, pensionAdjOB = null, principalAdjOB = null, empSubAdjOB = null;

		long finalPrincipalOB = 0, finalEmpNetOB = 0, finalAaiNetOB = 0, finalPensionOB = 0, finalOBFromTrFlag = 0, finalOBYear = 0, obEmpNetInterest = 0, obAaiNetInterest = 0, obPenInterest = 0;
		long adjOBEmpNetInterest = 0, adjOBAaiNetInterest = 0, adjOBPenInterest = 0, adjOBEmpSubInterest = 0;
		long empNetInterest = 0, aaiNetInterest = 0, penContInterest = 0;
		log.info("interest" + interest);
		empNetOB = (Long) obList.get(0);
		aaiNetOB = (Long) obList.get(1);
		pensionOB = (Long) obList.get(2);
		principalOB = (Long) obList.get(9);
		obFromTrFlag = (String) obList.get(3);
		obYear = (String) obList.get(4);
		if (obList.size() > 4) {
			empNetAdjOB = (Long) obList.get(5);
			aaiNetAdjOB = (Long) obList.get(6);
			pensionAdjOB = (Long) obList.get(7);
			empSubAdjOB = (Long) obList.get(8);
			principalAdjOB = new Long(0);
		}

		log.info("empNetOB=======" + empNetOB + "aaiNetOB==========="
				+ aaiNetOB + "pensionOB==========" + pensionOB + "obYear"
				+ obYear);
		log.info("empNetAdjOB====" + empNetAdjOB + "aaiNetAdjOB====="
				+ aaiNetAdjOB + "pensionAdjOB====" + pensionAdjOB);
		log.info("advancePFWPaid===============" + advancePFWPaid + "principle"
				+ principle);
		if (!obYear.equals("")) {
			year = Integer.parseInt(obYear) + 1;
		} else {
			year = 2009;
			obYear = "2009";
		}
		if (!obYear.equals("")) {
			obMnthYear = "01-Apr-" + year;
			obEmpNetInterest = Math
					.round((empNetOB.longValue() * interest) / 100);
			obAaiNetInterest = Math
					.round((aaiNetOB.longValue() * interest) / 100);
			obPenInterest = Math
					.round((pensionOB.longValue() * interest) / 100);

			adjOBEmpSubInterest = Math
					.round((empSubAdjOB.longValue() * interest) / 100);
			adjOBEmpNetInterest = Math
					.round((empNetAdjOB.longValue() * interest) / 100);
			adjOBAaiNetInterest = Math
					.round((aaiNetAdjOB.longValue() * interest) / 100);
			adjOBPenInterest = Math
					.round((pensionAdjOB.longValue() * interest) / 100);

			log.info("totalEmpNet" + totalEmpNet + "totalEmpIntNet"
					+ totalEmpIntNet + "obEmpNetInterest" + obEmpNetInterest);
			log.info("totalAAINet" + totalAAINet + "totalAaiIntNet"
					+ totalAaiIntNet + "obAaiNetInterest" + obAaiNetInterest);
			finalEmpNetOB = new Double(empNetOB.longValue()
					+ new Double(totalEmpNet).longValue()
					+ empSubAdjOB.longValue() + obEmpNetInterest
					+ totalEmpIntNet + adjOBEmpSubInterest).longValue();
			finalAaiNetOB = new Double(aaiNetOB.longValue()
					+ new Double(totalAAINet).longValue()
					+ aaiNetAdjOB.longValue() + obAaiNetInterest
					+ totalAaiIntNet + adjOBAaiNetInterest).longValue();
			// finalPensionOB=new Double(pensionOB.longValue()+new
			// Double(totalPensionContr
			// ).longValue()+obPenInterest+penInterest).longValue();
			finalPensionOB = new Double(pensionOB.longValue()
					+ new Double(totalPensionContr).longValue() + obPenInterest)
					.longValue();
			finalPrincipalOB = new Double(principalOB.longValue()
					+ new Double(advancePFWPaid).longValue()
					+ principalAdjOB.longValue() - principle).longValue();
			log.info("pensionOB" + pensionOB + "totalPensionContr"
					+ totalPensionContr + "obPenInterest" + obPenInterest
					+ "penInterest" + penInterest + "adjOBPenInterest"
					+ adjOBPenInterest);
			log.info("finalEmpNetOB" + finalEmpNetOB + "finalAaiNetOB"
					+ finalAaiNetOB + "finalPensionOB" + finalPensionOB
					+ "finalPrincipalOB" + finalPrincipalOB);

		}
		log.info("empSubAdjOB" + empSubAdjOB + "adjOBEmpSubInterest"
				+ adjOBEmpSubInterest + "obEmpNetInterest" + obEmpNetInterest
				+ "totalEmpIntNet" + totalEmpIntNet);
		empNetInterest = adjOBEmpSubInterest + obEmpNetInterest
				+ new Double(totalEmpIntNet).longValue();
		aaiNetInterest = adjOBAaiNetInterest + obAaiNetInterest
				+ new Double(totalAaiIntNet).longValue();
		penContInterest = obAaiNetInterest
				+ new Double(penInterest).longValue();
		log.info("empNetInterest====================" + empNetInterest
				+ "aaiNetInterest=================" + aaiNetInterest);
		finalOBList.add(new Long(finalEmpNetOB));
		finalOBList.add(new Long(finalAaiNetOB));
		finalOBList.add(new Long(finalPensionOB));
		finalOBList.add(obMnthYear);
		finalOBList.add(new Long(empNetInterest));
		finalOBList.add(new Long(aaiNetInterest));
		finalOBList.add(new Long(penContInterest));
		finalOBList.add(new Long(finalPrincipalOB));

		return finalOBList;
	}

	public String buildQueryEmpPFTransInfoPrinting(String range, String region,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno, String lastmonthYear,String airportcode) {
		log
		.info("FinanceReportDAO::buildQueryEmpPFTransInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		
		sqlQuery = "SELECT DISTINCT EPI.REFPENSIONNUMBER,EPI.CPFACNO,EPI.AIRPORTSERIALNUMBER,EPI.EMPLOYEENO, INITCAP(EPI.EMPLOYEENAME) AS EMPLOYEENAME,EPI.DESEGNATION,EPI.EMP_LEVEL,EPI.DATEOFBIRTH,EPI.DATEOFJOINING," +
				"EPI.DATEOFSEPERATION_REASON,EPI.DATEOFSEPERATION_DATE,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.WHETHER_FORM1_NOM_RECEIVED,EPI.REMARKS,EPI.AIRPORTCODE,EPI.GENDER,EPI.GENDER AS SEX,EPI.FHNAME,EPI.MARITALSTATUS,EPI.PERMANENTADDRESS," +
				"EPI.TEMPORATYADDRESS,EPI.WETHEROPTION,EPI.SETDATEOFANNUATION,EPI.EMPFLAG,EPI.DIVISION,EPI.DEPARTMENT,EPI.EMAILID,EPI.EMPNOMINEESHARABLE,EPI.REGION,EPI.PENSIONNO,(LAST_DAY(EPI.dateofbirth) + INTERVAL '60' year ) as DOR,EPI.username,EPI.LASTACTIVE,EPI.RefMonthYear,EPI.IPAddress," +
				"EPI.OTHERREASON,decode(sign(round(months_between(EPI.dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(EPI.dateofjoining,'dd-Mon-yyyy'),to_char(EPI.dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((EPI.dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB," +
				"ROUND(months_between('01-Apr-1995', EPI.dateofbirth) / 12) EMPAGE,EPI.FINALSETTLMENTDT,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,PFSETTLED,EPI.RESETTLEMENTDATE,PADJ.ADJDATE,PADJ.ADJAMOUNT,PADJ.ADJINT FROM EMPLOYEE_PERSONAL_INFO EPI,EMPLOYEE_PENSION_ADJUSTMENTS PADJ,EPIS_BULK_PRINT_PFIDS EPV WHERE EPI.PENSIONNO = PADJ.PENSIONNO(+) AND EPV.PENSIONNO=EPI.PENSIONNO";
		if (!range.equals("NO-SELECT")) {
			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);
			
			whereClause.append("  (EPV.PFID BETWEEN " + startIndex
					+ " AND " + endIndex + ")");
			whereClause.append(" AND ");
			
		}
		
		if (!region.equals("") && (empNameFlag.equals("false"))) {
			whereClause.append(" EPV.REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("") && (empNameFlag.equals("false"))) {
			whereClause.append(" EPV.AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}
		/*	if (!lastmonthYear.equals("")) {
		 whereClause.append(" TO_CHAR(MONTHYEAR,'dd-MM-yyyy') like'%"
		 + lastmonthYear + "'");
		 whereClause.append(" AND ");
		 }*/
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("EPV.PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}
		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))
				&& (range.equals("NO-SELECT") && (empNameFlag.equals("false")) && (lastmonthYear
						.equals("")))) {
			
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		String orderBy = " ORDER BY EPI.PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
		.info("FinanceReportDAO::buildQueryEmpPFTransInfoPrinting Leaving Method");
		return dynamicQuery;
	}


	public ArrayList getTrustPCReport(String range, String division,
			String region, String airprotcode, String empName,
			String empNameFlag, String frmSelctedYear, String sortingOrder,
			String frmPensionno) {
		DecimalFormat df = new DecimalFormat("#########0.00");
		String selPerQuery = "", selQuery = "", fromDate = "", toDate = "", selTransQuery = "", pfsettled = "", pcInformation = "", subPcInformation = "";
		String pensionTotal2008 = "0", pensionTotal2009 = "0", pensionTotal2010 = "0", interest2008 = "0", interest2009 = "0", interest2010 = "0";
		long calInterest2009 = 0, calNad2008PCTotal = 0, calIad2008PCTotal = 0, calNad2009PCTotal = 0, calIad2009PCTotal = 0;
		boolean pcFlag = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		TrustPCBean trustPCBean = null;
		ArrayList form3DataList = new ArrayList();
		String regionDesc = "", stationDesc = "", seperationReason = "", otherRemarks = "";
		if (!region.equals("NO-SELECT")) {
			regionDesc = region;
		} else {
			regionDesc = "";
		}
		if (!airprotcode.equals("NO-SELECT")) {
			stationDesc = airprotcode;
		} else {
			stationDesc = "";
		}
		if (sortingOrder.equals("")) {
			sortingOrder = "PENSIONNO";
		}
		StringBuffer whereClause = new StringBuffer();
		if (!regionDesc.equals("")) {
			whereClause.append(" REGION ='" + regionDesc + "'");
			whereClause.append(" AND ");
		} else {
			whereClause.append("");
		}
		/*
		 * selPerQuery=this.buildQueryEmpPFInfo(range,division,empNameFlag,empName
		 * ,sortingOrder,frmPensionno,frmSelctedYear);
		 * selTransQuery=this.buildQueryEmpPFAdjTransInfo
		 * (regionDesc,stationDesc,sortingOrder,fromDate,toDate);selQuery=
		 * "SELECT PINFO.PENSIONNO, PINFO.CPFACNO, PINFO.DATEOFJOINING,PINFO.DATEOFBIRTH,PINFO.EMPLOYEENAME, PINFO.EMPLOYEENO,PINFO.DIVISION, PINFO.WETHEROPTION, PINFO.DESEGNATION, "
		 * +
		 * "PADJOB.PENSIONTOTAL, PADJOB.PENSIONINTEREST, PINFO.AIRPORTCODE,PINFO.REGION,PINFO.DATEOFSEPERATION_REASON,PADJOB.PCTOTAL,PINFO.MAPINGFLAG,PINFO.PFSETTLED FROM ("
		 * +selPerQuery+") PINFO,("+selTransQuery +
		 * ") PADJOB WHERE PADJOB.PENSIONNO (+) = PINFO.PENSIONNO  ORDER BY PINFO."
		 * +sortingOrder;
		 */
		selPerQuery = this.buildQueryEmpPFInfo(range, division, regionDesc,
				empNameFlag, empName, sortingOrder, frmPensionno,
				frmSelctedYear);
		selQuery = "SELECT PERSONAL.PENSIONNO, NVL(join(cursor (SELECT NVL(PENSIONTOTAL,'0.00') || '-' || NVL(INTEREST,'0.00') || '-' || to_char(TRUSTOBYEAR,'Monyyyy')"
				+ "FROM EMPLOYEE_TRUST_PC_REPORT WHERE "
				+ whereClause.toString()
				+ "PENSIONNO = PERSONAL.PENSIONNO ORDER BY TRUSTOBYEAR)),'NODATA') AS PCINFORMATION,"
				+ "PERSONAL.CPFACNO, PERSONAL.EMPLOYEENO, INITCAP(PERSONAL.EMPLOYEENAME) AS EMPLOYEENAME, PERSONAL.DESEGNATION,"
				+ "PERSONAL.EMP_LEVEL, PERSONAL.DATEOFBIRTH, PERSONAL.DATEOFJOINING, PERSONAL.DATEOFSEPERATION_REASON,"
				+ "PERSONAL.DATEOFSEPERATION_DATE, PERSONAL.WHETHER_FORM1_NOM_RECEIVED, PERSONAL.REMARKS, PERSONAL.AIRPORTCODE,"
				+ "PERSONAL.GENDER, PERSONAL.FHNAME, PERSONAL.MARITALSTATUS, PERSONAL.PERMANENTADDRESS, PERSONAL.TEMPORATYADDRESS,"
				+ "PERSONAL.WETHEROPTION, PERSONAL.EMPFLAG, PERSONAL.DIVISION, PERSONAL.DEPARTMENT, PERSONAL.EMAILID,"
				+ "PERSONAL.EMPNOMINEESHARABLE, PERSONAL.REGION, PERSONAL.PENSIONNO, PERSONAL.OTHERREASON, PERSONAL.MAPINGFLAG,"
				+ "PERSONAL.PFSETTLED FROM (" + selPerQuery + ") PERSONAL";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			log.info("FinancialReportDAO::getTrustPCReport===selQuery"
					+ selQuery);
			rs = st.executeQuery(selQuery);

			while (rs.next()) {

				trustPCBean = new TrustPCBean();
				if (rs.getString("PENSIONNO") != null) {
					trustPCBean.setPensionno(rs.getString("PENSIONNO"));

				}

				if (rs.getString("CPFACNO") != null) {
					trustPCBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					trustPCBean.setCpfaccno("---");
				}

				if (rs.getString("EMPLOYEENO") != null) {
					trustPCBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					trustPCBean.setEmployeeNo("---");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					trustPCBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					trustPCBean.setEmployeeName("---");
				}
				if (rs.getString("DESEGNATION") != null) {
					trustPCBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					trustPCBean.setDesignation("---");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					trustPCBean.setDateOfBirth(commonUtil
							.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));
				} else {
					trustPCBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					trustPCBean.setDateofjoining(commonUtil
							.converDBToAppFormat(rs.getDate("DATEOFJOINING")));
				} else {
					trustPCBean.setDateofjoining("---");
				}

				if (!trustPCBean.getDateOfBirth().equals("---")) {
					String personalPFID = commonDAO.getPFID(trustPCBean
							.getEmployeeName(), trustPCBean.getDateOfBirth(),
							commonUtil.leadingZeros(5, trustPCBean
									.getPensionno()));
					trustPCBean.setPfID(personalPFID);
				} else {
					trustPCBean.setPfID(trustPCBean.getPensionno());
				}

				if (rs.getString("WETHEROPTION") != null) {
					trustPCBean.setWetherOption(rs.getString("WETHEROPTION"));
				} else {
					trustPCBean.setWetherOption("---");
				}
				if (rs.getString("DIVISION") != null) {
					trustPCBean.setDivision(rs.getString("DIVISION"));
				} else {
					trustPCBean.setDivision("---");
				}
				if (rs.getString("PFSETTLED") != null) {
					pfsettled = rs.getString("PFSETTLED");
				}
				if (rs.getString("PCINFORMATION") != null) {
					pcInformation = rs.getString("PCINFORMATION");
				}
				if (!pcInformation.equals("NODATA")) {
					pcFlag = true;
				}
				if (pcFlag == true) {
					String[] pcInformtionList = pcInformation.split(",");
					/*
					 * log.info("PensionNo" + trustPCBean.getPensionno() +
					 * "Length" + pcInformtionList.length);
					 */

					for (int i = 0; i < pcInformtionList.length; i++) {
						subPcInformation = pcInformtionList[i];
						// log.info("subPcInformation========================="
						// +
						// subPcInformation);
						String[] subPcInformationList1 = subPcInformation
								.split("-");

						log.info("Pensionno" + trustPCBean.getPensionno()
								+ "PCTOTAL" + subPcInformationList1[0]
								+ "Interest" + subPcInformationList1[1]
								+ "date" + subPcInformationList1[2]);

						if (subPcInformationList1[2].equals("Apr2008")) {
							pensionTotal2008 = subPcInformationList1[0];
							interest2008 = subPcInformationList1[1];
							/*
							 * log.info("pensionTotal2008" + pensionTotal2008 +
							 * "interest2008" + interest2008);
							 */
						} else if (subPcInformationList1[2].equals("Apr2009")) {
							pensionTotal2009 = subPcInformationList1[0];
							interest2009 = subPcInformationList1[1];
							/*
							 * log.info("pensionTotal2009" + pensionTotal2009 +
							 * "interest2009" + interest2009);
							 */
						} else if (subPcInformationList1[2].equals("Apr2010")) {
							pensionTotal2010 = subPcInformationList1[0];
							interest2010 = subPcInformationList1[1];
						}
					}

					if (pfsettled.equals("N")) {
						if (trustPCBean.getDivision().equals("IAD")) {

							trustPCBean
									.setIad2008Pensiontotal(pensionTotal2008);
							trustPCBean.setNad2008Pensiontotal("0");

							trustPCBean.setIad2008PensionInterest(interest2008);
							trustPCBean.setNad2008PensionInterest("0");

							calIad2008PCTotal = Long
									.parseLong(pensionTotal2008)
									+ Long.parseLong(interest2008);
							calNad2008PCTotal = 0;

							trustPCBean
									.setIad2009Pensiontotal(pensionTotal2009);
							trustPCBean.setNad2009Pensiontotal("0");

							if (!interest2008.equals("0")
									|| !interest2008.equals("")) {
								calInterest2009 = Math
										.round((((calIad2008PCTotal * 8.5) / 100)));
								calInterest2009 = calInterest2009
										+ Long.parseLong(interest2009);
							} else {
								calInterest2009 = Long.parseLong(interest2009);
							}
							trustPCBean.setIad2009PensionInterest(Long
									.toString(calInterest2009));
							trustPCBean.setNad2009PensionInterest("0");

							trustPCBean
									.setIad2010Pensiontotal(pensionTotal2010);
							trustPCBean.setNad2010Pensiontotal("0");

							trustPCBean.setIad2010PensionInterest(interest2010);
							trustPCBean.setNad2010PensionInterest("0");

							calIad2009PCTotal = Long
									.parseLong(pensionTotal2009)
									+ calInterest2009;
							calNad2009PCTotal = 0;

							trustPCBean.setIad2008PCTotal(Long
									.toString(calIad2008PCTotal));
							trustPCBean.setNad2008PCTotal("0");

							trustPCBean.setIad2009PCTotal(Long
									.toString(calIad2009PCTotal));
							trustPCBean.setNad2009PCTotal("0");

							/*
							 * if(rs.getString("PENSIONTOTAL")!=null){
							 * trustPCBean.setIadPensiontotal
							 * (rs.getString("PENSIONTOTAL"));
							 * trustPCBean.setNadPensiontotal("0.00"); }else{
							 * trustPCBean.setIadPensiontotal("0.00");
							 * trustPCBean.setNadPensiontotal("0.00"); }
							 * 
							 * if(rs.getString("PENSIONINTEREST")!=null){
							 * trustPCBean
							 * .setIadPensionInterest(rs.getString("PENSIONINTEREST"
							 * )); trustPCBean.setNadPensionInterest("0.00");
							 * }else{ trustPCBean.setIadPensionInterest("0.00");
							 * trustPCBean.setNadPensionInterest("0.00"); }
							 * 
							 * if(rs.getString("PCTOTAL")!=null){
							 * trustPCBean.setIadPCTotal
							 * (rs.getString("PCTOTAL"));
							 * trustPCBean.setNadPCTotal("0.00"); }else{
							 * trustPCBean.setIadPCTotal("0.00");
							 * trustPCBean.setNadPCTotal("0.00"); }
							 */

						} else if (trustPCBean.getDivision().equals("NAD")) {

							// log.info("PCTOTAL" + pensionTotal2008 +
							// "Interest" +
							// pensionTotal2008 + "date" + pensionTotal2008);
							trustPCBean
									.setNad2008Pensiontotal(pensionTotal2008);
							trustPCBean.setIad2008Pensiontotal("0");

							trustPCBean.setNad2008PensionInterest(interest2008);
							trustPCBean.setIad2008PensionInterest("0");
							/*
							 * log.info("PCTOTAL" + pensionTotal2009 +
							 * "Interest" + pensionTotal2009 + "date" +
							 * pensionTotal2009);
							 */

							calIad2008PCTotal = 0;
							calNad2008PCTotal = Long
									.parseLong(pensionTotal2008)
									+ Long.parseLong(interest2008);

							if (calNad2008PCTotal != 0) {
								calInterest2009 = Math
										.round((((calNad2008PCTotal * 8.5) / 100)));
								calInterest2009 = calInterest2009
										+ Long.parseLong(interest2009);
							} else {
								calInterest2009 = Long.parseLong(interest2009);
							}
							trustPCBean
									.setNad2009Pensiontotal(pensionTotal2009);
							trustPCBean.setIad2009Pensiontotal("0");
							trustPCBean.setNad2009PensionInterest(Long
									.toString(calInterest2009));
							trustPCBean.setIad2009PensionInterest("0");

							trustPCBean
									.setNad2010Pensiontotal(pensionTotal2010);
							trustPCBean.setIad2010Pensiontotal("0");
							trustPCBean.setNad2010PensionInterest(interest2010);
							trustPCBean.setIad2010PensionInterest("0");

							calIad2009PCTotal = 0;
							calNad2009PCTotal = Long
									.parseLong(pensionTotal2009)
									+ calInterest2009;

							trustPCBean.setIad2008PCTotal("0");
							trustPCBean.setNad2008PCTotal(Long
									.toString(calNad2008PCTotal));

							trustPCBean.setIad2009PCTotal("0");
							trustPCBean.setNad2009PCTotal(Long
									.toString(calNad2009PCTotal));

							/*
							 * if(rs.getString("PENSIONTOTAL")!=null){
							 * trustPCBean.setNadPensiontotal
							 * (rs.getString("PENSIONTOTAL"));
							 * trustPCBean.setIadPensiontotal("0.00"); }else{
							 * trustPCBean.setNadPensiontotal("0.00");
							 * trustPCBean.setIadPensiontotal("0.00"); }
							 * 
							 * if(rs.getString("PENSIONINTEREST")!=null){
							 * trustPCBean
							 * .setNadPensionInterest(rs.getString("PENSIONINTEREST"
							 * )); trustPCBean.setIadPensionInterest("0.00");
							 * }else{ trustPCBean.setNadPensionInterest("0.00");
							 * trustPCBean.setIadPensionInterest("0.00"); }
							 * if(rs.getString("PCTOTAL")!=null){
							 * trustPCBean.setNadPCTotal
							 * (rs.getString("PCTOTAL"));
							 * trustPCBean.setIadPCTotal("0.00"); }else{
							 * trustPCBean.setNadPCTotal("0.00");
							 * trustPCBean.setIadPCTotal("0.00"); }
							 */
						} else {

							trustPCBean
									.setNad2008Pensiontotal(pensionTotal2008);
							trustPCBean.setIad2008Pensiontotal("0");
							trustPCBean.setNad2008PensionInterest(interest2008);
							trustPCBean.setIad2008PensionInterest("0");

							trustPCBean
									.setNad2009Pensiontotal(pensionTotal2009);
							trustPCBean.setIad2009Pensiontotal("0");
							trustPCBean.setNad2009PensionInterest(interest2009);
							trustPCBean.setIad2009PensionInterest("0");

							trustPCBean
									.setNad2010Pensiontotal(pensionTotal2010);
							trustPCBean.setIad2010Pensiontotal("0");
							trustPCBean.setNad2010PensionInterest(interest2010);
							trustPCBean.setIad2010PensionInterest("0");

							/*
							 * if(rs.getString("PENSIONTOTAL")!=null){
							 * trustPCBean.setNadPensiontotal
							 * (rs.getString("PENSIONTOTAL"));
							 * trustPCBean.setIadPensiontotal("0.00"); }else{
							 * trustPCBean.setNadPensiontotal("0.00");
							 * trustPCBean.setIadPensiontotal("0.00"); }
							 * 
							 * if(rs.getString("PENSIONINTEREST")!=null){
							 * trustPCBean
							 * .setNadPensionInterest(rs.getString("PENSIONINTEREST"
							 * )); trustPCBean.setIadPensionInterest("0.00");
							 * }else{ trustPCBean.setNadPensionInterest("0.00");
							 * trustPCBean.setIadPensionInterest("0.00"); }
							 * if(rs.getString("PCTOTAL")!=null){
							 * trustPCBean.setNadPCTotal
							 * (rs.getString("PCTOTAL"));
							 * trustPCBean.setIadPCTotal("0.00"); }else{
							 * trustPCBean.setNadPCTotal("0.00");
							 * trustPCBean.setIadPCTotal("0.00"); }
							 */
							otherRemarks = "DIVISION IS NOT AVAILBLE";
						}
					} else {
						trustPCBean.setNadPensiontotal("0");
						trustPCBean.setIadPensiontotal("0");
						trustPCBean.setNadPensionInterest("0");
						trustPCBean.setIadPensionInterest("0");
						trustPCBean.setNadPCTotal("0");
						trustPCBean.setIadPCTotal("0");

						trustPCBean.setNad2008Pensiontotal("0");
						trustPCBean.setIad2008Pensiontotal("0");
						trustPCBean.setNad2008PensionInterest("0");
						trustPCBean.setIad2008PensionInterest("0");
						trustPCBean.setNad2008PCTotal("0");
						trustPCBean.setIad2008PCTotal("0");

						trustPCBean.setNad2009Pensiontotal("0");
						trustPCBean.setIad2009Pensiontotal("0");
						trustPCBean.setNad2009PensionInterest("0");
						trustPCBean.setIad2009PensionInterest("0");
						trustPCBean.setNad2009PCTotal("0");
						trustPCBean.setIad2009PCTotal("0");

						trustPCBean.setNad2010Pensiontotal("0");
						trustPCBean.setIad2010Pensiontotal("0");
						trustPCBean.setNad2010PensionInterest("0");
						trustPCBean.setIad2010PensionInterest("0");
						trustPCBean.setNad2010PCTotal("0");
						trustPCBean.setIad2010PCTotal("0");
					}
				} else {
					trustPCBean.setNadPensiontotal("0");
					trustPCBean.setIadPensiontotal("0");
					trustPCBean.setNadPensionInterest("0");
					trustPCBean.setIadPensionInterest("0");
					trustPCBean.setNadPCTotal("0");
					trustPCBean.setIadPCTotal("0");

					trustPCBean.setNad2008Pensiontotal("0");
					trustPCBean.setIad2008Pensiontotal("0");
					trustPCBean.setNad2008PensionInterest("0");
					trustPCBean.setIad2008PensionInterest("0");
					trustPCBean.setNad2008PCTotal("0");
					trustPCBean.setIad2008PCTotal("0");

					trustPCBean.setNad2009Pensiontotal("0");
					trustPCBean.setIad2009Pensiontotal("0");
					trustPCBean.setNad2009PensionInterest("0");
					trustPCBean.setIad2009PensionInterest("0");
					trustPCBean.setNad2009PCTotal("0");
					trustPCBean.setIad2009PCTotal("0");

					trustPCBean.setNad2010Pensiontotal("0");
					trustPCBean.setIad2010Pensiontotal("0");
					trustPCBean.setNad2010PensionInterest("0");
					trustPCBean.setIad2010PensionInterest("0");
					trustPCBean.setNad2010PCTotal("0");
					trustPCBean.setIad2010PCTotal("0");
				}

				//log.info("NAD Pension Total"+trustPCBean.getNad2008Pensiontotal
				// ()+"Interest"+trustPCBean.getNad2008PensionInterest());
				if (rs.getString("DATEOFSEPERATION_REASON") != null) {
					seperationReason = rs.getString("DATEOFSEPERATION_REASON");
					if (!seperationReason.equals("Other")) {
						if (seperationReason.equals("Death")
								&& pfsettled.equals("Y")) {
							otherRemarks = otherRemarks
									+ "Expired & PF Settled";
						} else if (seperationReason.equals("Retirement")
								&& pfsettled.equals("Y")) {
							otherRemarks = otherRemarks
									+ "Retired & PF Settled";
						} else if (seperationReason.equals("Death")
								&& pfsettled.equals("N")) {
							otherRemarks = otherRemarks + "Expired";
						} else if (seperationReason.equals("Retirement")
								&& pfsettled.equals("N")) {
							otherRemarks = otherRemarks + "Retired";
						} else {
							otherRemarks = otherRemarks + seperationReason;
						}

					}

				} else {
					if (pfsettled.equals("Y")) {
						otherRemarks = otherRemarks + "PF Settled";
					}
				}
				if (!otherRemarks.equals("")) {
					trustPCBean.setRemarks(otherRemarks);
				} else {
					trustPCBean.setRemarks("---");
				}
				if (rs.getString("MAPINGFLAG") != null) {
					trustPCBean.setMappingFlag(rs.getString("MAPINGFLAG"));

				} else {
					trustPCBean.setMappingFlag("N");
				}
				if (rs.getString("REGION") != null) {
					trustPCBean.setRegion(rs.getString("REGION"));
				} else {
					trustPCBean.setRegion("---");
				}
				if (rs.getString("AIRPORTCODE") != null) {
					trustPCBean.setStation(rs.getString("AIRPORTCODE"));
				} else {
					trustPCBean.setStation("---");
				}
				otherRemarks = "";
				pensionTotal2008 = "0";
				interest2008 = "0";
				pensionTotal2009 = "0";
				interest2009 = "0";
				pensionTotal2010 = "0";
				interest2010 = "0";
				pcFlag = false;
				calNad2008PCTotal = 0;
				calIad2008PCTotal = 0;
				calNad2009PCTotal = 0;
				calIad2009PCTotal = 0;
				form3DataList.add(trustPCBean);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form3DataList;
	}

	public String buildQueryEmpPFInfo(String range, String division,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno, String frmSelectedDate) {
		log.info("FinancialReportDAO::buildQueryEmpPFInfo-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,"
				+ "TEMPORATYADDRESS,WETHEROPTION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,OTHERREASON,MAPINGFLAG,PFSETTLED FROM EMPLOYEE_PERSONAL_INFO";

		if (!range.equals("NO-SELECT")) {
			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);

			whereClause.append("  PENSIONNO >=" + startIndex
					+ " AND PENSIONNO <=" + endIndex);
			whereClause.append(" AND ");

		}
		if (!frmSelectedDate.equals("")) {
			whereClause.append("DATEOFJOINING<='" + frmSelectedDate + "' ");
			whereClause.append(" AND ");
		}
		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!division.equals("Both")) {
			whereClause.append("DIVISION='" + division + "' ");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if ((range.equals("NO-SELECT") && region.equals("")
				&& frmSelectedDate.equals("") && division.equals("Both") && (empNameFlag
				.equals("false")))) {

		} else {
			query.append(" WHERE ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}

		String orderBy = " ORDER BY " + sortedColumn;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("FinancialReportDAO::buildQueryEmpPFInfo Leaving Method");
		return dynamicQuery;
	}

	public String buildQueryEmpPFAdjTransInfo(String region,
			String airportcode, String sortedColumn, String fromDate,
			String toDate) {
		log
				.info("FinancialReportDAO::buildQueryEmpPFAdjTransInfo-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";

		sqlQuery = "SELECT PENSIONNO, CPFTOTAL, CPFINTEREST, PENSIONTOTAL AS PENSIONTOTAL, PENSIONINTEREST, PFTOTAL, PFINTEREST, REGION,(PENSIONTOTAL+PENSIONINTEREST) AS PCTOTAL FROM EMPLOYEE_ADJ_OB WHERE to_char(ADJOBYEAR, 'dd-Mon-yyyy') like '%-Apr-2008'";

		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals(""))) {

		} else {
			query.append(" AND ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}

		dynamicQuery = query.toString();
		log
				.info("FinancialReportDAO::buildQueryEmpPFAdjTransInfo Leaving Method");
		return dynamicQuery;
	}

	private int updateAdjBal(Connection con, double cpf, double cpfinterest,
			double pension, double pensionintrest, double pf,
			double pfinterest, String pensionno, String region) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", remarks = "", adjOBYear = "", sqlAdjQuery = "";
		DecimalFormat df = new DecimalFormat("#########0");
		adjOBYear = Constants.ADJ_CREATED_DATE;
		try {
			remarks = "Generated From 1995-08 Grand Total PC Report";
			sqlInsQuery = "UPDATE employee_adj_ob SET CPFTOTAL='"
					+ df.format(cpf) + "',CPFINTEREST='"
					+ df.format(cpfinterest) + "',PENSIONTOTAL='"
					+ df.format(pension) + "',PENSIONINTEREST='"
					+ df.format(pensionintrest) + "',PFTOTAL='" + df.format(pf)
					+ "',PFINTEREST='" + df.format(pfinterest) + "' WHERE PRIORADJFLAG='N' AND PENSIONNO='" + pensionno + "' AND ADJOBYEAR='" + adjOBYear + "'";
			st = con.createStatement();
			
			count = st.executeUpdate(sqlInsQuery);
			log.info("updateAdjBal::Inserted Query" + sqlInsQuery+"count====="+count);
			if (count != 0) {
				sqlAdjQuery = "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
						+ pensionno;
				count = st.executeUpdate(sqlAdjQuery);
				log
						.info("updateAdjBal=========================="
								+ sqlAdjQuery);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}

	private ArrayList pendingPFIDsForAdjOB(String pensionno) {
		Connection con = null;
		Statement pst = null;
		ResultSet rs = null;
		String sqlQuery = "", prCPFString = "",queryAppend="";
		if(!pensionno.equals("")){
			queryAppend=" AND PENSIONNO="+pensionno+" AND ROWNUM=1 AND to_char(REQGENDATE,'dd-Mon-yyyy')=to_char(CURRENT_DATE,'dd-Mon-yyyy')";
		}else{
			queryAppend="";
		}
		
		sqlQuery = "SELECT PENSIONNO,cpfaccno,EMPLOYEENAME,AIRPORTCODE,REGION,DATEOFBIRTH,WETHEROPTION,DATEOFJOINING,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_GENRTED_PEND_ADJ_0B WHERE STATUS='N'"+queryAppend;
		PersonalDAO personalDAO = new PersonalDAO();
		PensionContBean data = null;
		CommonDAO commonDao = new CommonDAO();
		log.info("pensionContrHeaderInfo===Query" + sqlQuery);
		ArrayList empinfo = new ArrayList();
		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			rs = pst.executeQuery(sqlQuery);
			while (rs.next()) {
				data = new PensionContBean();
				if (rs.getString("PENSIONNO") != null) {
					data.setEmpSerialNo(rs.getString("PENSIONNO"));
					data.setPensionNo(rs.getString("PENSIONNO"));
				} else {
					data.setEmpSerialNo("");
					data.setPensionNo("");
				}
				if (rs.getString("cpfaccno") != null) {
					data.setCpfacno(rs.getString("cpfaccno"));
				} else {
					data.setCpfacno("---");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					data.setEmployeeNM(rs.getString("EMPLOYEENAME"));
				} else {
					data.setEmployeeNM("---");
				}
				if (rs.getString("REGION") != null) {
					data.setEmpRegion(rs.getString("REGION"));
				} else {
					data.setEmpRegion("");

				}
				if (rs.getString("DATEOFJOINING") != null) {
					data.setEmpDOJ(commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFJOINING")));
				} else {
					data.setEmpDOJ("");

				}
				if (rs.getString("DATEOFBIRTH") != null) {
					data.setEmpDOB(commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH")));
				} else {
					data.setEmpDOB("");

				}
				if (rs.getString("WETHEROPTION") != null) {
					data.setWhetherOption(rs.getString("WETHEROPTION"));
				} else {
					data.setWhetherOption("---");

				}

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");

				if (noOfYears > 0) {
					data.setDateOfEntitle(data.getEmpDOJ());
				} else {
					data.setDateOfEntitle("01-Apr-1995");
				}
				prCPFString = commonDao.getCPFACCNOByPension(data
						.getEmpSerialNo(), data.getCpfacno(), data
						.getEmpRegion());
				String[] cpfaccnoList = prCPFString.split("=");
				String cpfString = personalDAO.preparedCPFString(cpfaccnoList);
				log.info(cpfString);
				data.setPrepareString(cpfString);
				empinfo.add(data);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		return empinfo;
	}

	public int preProcessAdjOB(String pfid) {
		Connection con = null;
		Statement pst = null;
		ResultSet rs = null;
		String status = "";
		int updatedRecords = 0;
		String sqlInsertQuery = "", prCPFString = "", sqlUpdateQuery = "";
		sqlInsertQuery = "insert into EMPLOYEE_GENRTED_PEND_ADJ_0B(PENSIONNO,cpfaccno,EMPLOYEENAME,AIRPORTCODE,region,REQGENDATE,STATUS,dateofbirth,wetheroption,dateofjoining) select pensionno,cpfacno,employeename,airportcode,region,TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS'),'N' ,dateofbirth,wetheroption,dateofjoining from employee_personal_info where pensionno="
				+ pfid;
		sqlUpdateQuery = "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='N' , REQGENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
				+ pfid;
		PersonalDAO personalDAO = new PersonalDAO();
		PensionContBean data = null;
		CommonDAO commonDao = new CommonDAO();

		ArrayList empinfo = new ArrayList();
		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			if (!pfid.equals("")) {
				status = this.checkPreProcessAdjOB(con, pfid);
				if (status.equals("insert")) {
					updatedRecords = pst.executeUpdate(sqlInsertQuery);
				} else if (status.equals("update")) {
					updatedRecords = pst.executeUpdate(sqlUpdateQuery);
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		return updatedRecords;
	}

	private String checkPreProcessAdjOB(Connection con, String pfid) {
		Statement pst = null;
		ResultSet rs = null;
		String pensionNo = "", requestGenDate = "", status = "", returnType = "";
		long noOfDays = 0;
		String sqlQuery = "SELECT PENSIONNO,REQGENDATE,STATUS FROM EMPLOYEE_GENRTED_PEND_ADJ_0B WHERE PENSIONNO="
				+ pfid;
		// log.info("FinancialReportDAO::checkPreProcessAdjOB" + sqlQuery);
		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			rs = pst.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("REQGENDATE") != null) {
					requestGenDate = commonUtil.converDBToAppFormat(rs
							.getDate("REQGENDATE"));
				}
				if (rs.getString("STATUS") != null) {
					status = rs.getString("STATUS");
				}
				noOfDays = commonUtil.getDateDifference(commonUtil
						.getCurrentDate("dd-MMM-yyyy"), requestGenDate);
				if (noOfDays > 1) {
					returnType = "update";
				} else {
					returnType = "none";
				}
			} else {
				returnType = "insert";
			}

			// log.info("FinancialReportDAO::checkPreProcessAdjOB noOfDays"+
			// noOfDays + "returnType=====" + returnType);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, pst, rs);
		}
		return returnType;
	}

	public ArrayList getEmployeePFInfoPrinting(Connection con, String range,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno, String lastmonthFlag,
			String lastmonthYear,String airportcode,String fromYear,String toYear) {
		CommonDAO commonDAO = new CommonDAO();

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", findFromYear="",pensionAppednQry = "";
		EmployeePersonalInfo data = null;
		int restltmentYear=0;
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			log.printStackTrace(e2);
		}
		try {

			st = con.createStatement();
			if (region.equals("NO-SELECT")) {
				region = "";
			}
			if (lastmonthFlag.equals("true")) {
				sqlQuery = this.buildQueryEmpPFTransInfoPrinting(range, region,
						empNameFlag, empName, sortedColumn, pensionno,
						lastmonthYear,airportcode);
			} else {
				sqlQuery = this.buildQuerygetEmployeePFInfoPrinting(range,
						region, empNameFlag, empName, sortedColumn, pensionno);
			}

			log.info("FinanceReportDAO::getEmployeePFInfoPrinting" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();

				data = commonDAO.loadPersonalInfo(rs);
				
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
				if (rs.getString("ADJDATE") != null) {
					data.setAdjDate(commonUtil.getDatetoString(rs
							.getDate("ADJDATE"), "dd-MMM-yyyy"));
					log.info("GEEEEEEEEEEEE"+this.compareFinalSettlementDates(fromYear,toYear,data.getAdjDate()));
					if(this.compareFinalSettlementDates(fromYear,toYear,data.getAdjDate())==true){
						if (rs.getString("ADJAMOUNT") != null) {
							data.setAdjAmount(rs.getString("ADJAMOUNT"));
						} else {
							data.setAdjAmount("0");
						}
						if (rs.getString("ADJINT") != null) {
							data.setAdjInt(rs.getString("ADJINT"));
						} else {
							data.setAdjInt("0");
						}
						data.setAdjRemarks("Int. on Rs."+data.getAdjAmount()+" received in "+commonUtil.getDatetoString(rs
								.getDate("ADJDATE"), "MMM-yyyy"));
					}else{
						data.setAdjDate("---");
						data.setAdjAmount("0");
						data.setAdjInt("0");
					}
				} else {
					data.setAdjDate("---");
					data.setAdjAmount("0");
					data.setAdjInt("0");
				}
				
				
				if (rs.getString("FINALSETTLMENTDT") != null) {
					data.setFinalSettlementDate(commonUtil.getDatetoString(rs
							.getDate("FINALSETTLMENTDT"), "dd-MMM-yyyy"));
				} else {
					data.setFinalSettlementDate("---");
				}
				log.info("Resettlement Date"+rs.getString("RESETTLEMENTDATE")+"fromYear"+fromYear);
				if (rs.getString("RESETTLEMENTDATE") != null) {
					restltmentYear=Integer.parseInt(commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "yyyy"));
					log.info("Resettlement Date Issues"+commonUtil.compareTwoDates(
							commonUtil.getDatetoString(rs
									.getDate("RESETTLEMENTDATE"), "MMM-yyyy"), commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
					if(compareFinalSettlementDates(fromYear,toYear,commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"))==true){
						data.setResttlmentDate(commonUtil.getDatetoString(rs
								.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"));
					}else{
						data.setResttlmentDate("---");
					}
					
				} else {
					data.setResttlmentDate("---");
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
				if(Integer.parseInt(findFromYear)>2008){
					data.setPfIDString("");
				}else{
					pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
							.getPensionNo(), data.getCpfAccno(), data.getRegion());
					if(!pfidWithRegion.equals("---")){
						String[] pfIDLists = pfidWithRegion.split("=");
						data.setPfIDString(this.preparedCPFString(pfIDLists));
						log.info("CPACCNO Strip==========="+data.getPfIDString());
					}
					log.info(data.getPfIDString());
				}
				
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return empinfo;
	}

	private ArrayList getEmployeePensionCard(Connection con, String fromDate,
			String toDate, String pfIDS, String wetherOption, String region,
			boolean formFlag, String dateOfRetriment, String dateOfBirth,
			String pensionNo, String finalSettlmentMonth) {

		DecimalFormat df = new DecimalFormat("#########0.00");

		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false, contrFlag = false, obFlag = false, rareCaseflag = false, chkDOBFlag = false, finalSettlementFlag = false, yearBreakMonthFlag = false, resettlementFlag = false;
		boolean arrearsFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", emolumentsMonths = "1", arrearFlag = "N";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "", findFromYear = "", findToYear = "", finalSettlmentYear = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, arrearEmpCumlative = 0.0, arrearAaiNetCumlative = 0, aaiPF = 0.0, aaiNetCumlative = 0.0, grandEmpCumlative = 0.0, grandAaiCumlative = 0.0, grandArrearEmpCumlative = 0.0, grandArrearAaiCumlative = 0.0;
		double pensionAsPerOption = 0.0, totalAdvances = 0;
	    boolean loanAdvFlag = false;
	    double additionalContri=0.0,netepf=0.0;
	    ArrayList addConList=new ArrayList();
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
			if (!finalSettlmentMonth.equals("---")) {
				finalSettlmentYear = commonUtil.converDBToAppFormat(
						finalSettlmentMonth, "MMM-yyyy", "yyyy");
			}

		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
		if (Integer.parseInt(findFromYear) >= 2008) {
			yearBreakMonthFlag = true;
			pfIDS = "";
			if (Integer.parseInt(findFromYear) >= 2013 && Integer.parseInt(findToYear) > 2013 ) {
				loanAdvFlag=true;
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,decode((sign(sysdate-add_months(to_date(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
					+ "SUBSTR(empdt.MONYEAR, 4, 4))),1))),-1,0,1,1) as signs,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT add_months(MONTHYEAR,-1) as MONTHYEAR,to_char(add_months(MONTHYEAR,-1), 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,PENSIONCONTRI,PF,emolumentmonths,MERGEFLAG,REMARKS,PFCARDNARRATION,SUPPLIFLAG,PFCARDNRFLAG,additionalcontri,form4flag FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= add_months('"
					+ fromDate
					+ "',1) and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=add_months(last_day('"
					+ toDate
					+ "'),1))"
					+ " AND PENSIONNO="
					+ pensionNo
					+ ") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
			}else{
				sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,decode((sign(sysdate-to_date(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
					+ "SUBSTR(empdt.MONYEAR, 4, 4))))),-1,0,1,1) as signs,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT  MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,PENSIONCONTRI,PF,emolumentmonths,MERGEFLAG,REMARKS,PFCARDNARRATION,SUPPLIFLAG,PFCARDNRFLAG,form4flag FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >='"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND PENSIONNO="
					+ pensionNo
					+ ") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
			}
		} else {
			yearBreakMonthFlag = false;
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,decode((sign(sysdate-to_date(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
					+ "SUBSTR(empdt.MONYEAR, 4, 4))))),-1,0,1,1) as signs,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,PENSIONCONTRI,PF,emolumentmonths,MERGEFLAG,REMARKS,PFCARDNARRATION,SUPPLIFLAG,PFCARDNRFLAG,form4flag FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND ("
					+ pfIDS
					+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
		}

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));
					} else {
						cardInfo.setMonthyear("---");
					}
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy",
								"MMM-yy"));
					}

				}
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}
				if (rs.getString("form4flag") != null) {
					cardInfo.setForm4flag(rs.getString("form4flag"));
				} else {
					cardInfo.setForm4flag("N");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}
				if (rs.getString("arrearflag") != null) {
					arrearFlag = rs.getString("arrearflag");
				} else {
					arrearFlag = "N";
				}
				cardInfo.setTransArrearFlag(arrearFlag);
				if (arrearFlag.equals("Y") && arrearsFlag == false) {
					arrearsFlag = true;
				}

				log.info("=========MonthYear" + cardInfo.getMonthyear()
						+ "Emoluments" + cardInfo.getEmoluments()
						+ "finalSettlmentMonth" + finalSettlmentMonth
						+ "arrearFlag" + arrearFlag + "arrearsFlag"
						+ arrearsFlag);
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}
				if (rs.getString("SUPPLIFLAG") != null) {
					cardInfo.setSupflag(rs.getString("SUPPLIFLAG"));
				} else {
					cardInfo.setSupflag("N");
				}
				//To Disply PFCARDNARRATION irrespective of arrears,Suppli or Merge Data
				if (rs.getString("PFCARDNRFLAG") != null) {
					cardInfo.setPfcardNarrationFlag(rs.getString("PFCARDNRFLAG"));
				} else {
					cardInfo.setPfcardNarrationFlag("N");
				}
				
				/*
				 * if (region.equals("CHQNAD")) {
				 * 
				 * if (rs.getString("CPFADVANCE") != null) {
				 * cardInfo.setPrincipal(rs.getString("CPFADVANCE")); } else {
				 * cardInfo.setPrincipal("0"); } } else if (region.equals("North
				 * Region")) { if (rs.getString("REFADV") != null) {
				 * cardInfo.setPrincipal(rs.getString("REFADV")); } else {
				 * cardInfo.setPrincipal("0"); } }
				 */{
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				
				
				if(Integer.parseInt(findFromYear)>=2015) {								
					
					//additionalcalculation.additionalContributionCalculation(con, cardInfo.getEmoluments(),pensionNo,cardInfo.getMonthyear(),
					//		dateOfRetriment,dateOfBirth);
					
					if(rs.getString("additionalcontri")!=null) {
						//additionalContri=commonDAO.additionalContriCalculation(cardInfo.getMonthyear(),cardInfo.getEmoluments());
						cardInfo.setAdditionalContri(rs.getString("additionalcontri"));
					}
					else {
						cardInfo.setAdditionalContri("0.0");
					}
					if( Date.parse(cardInfo.getMonthyear())<Date.parse("01-Sep-2014") &&
							Date.parse(cardInfo.getMonthyear())>=Date.parse("01-Apr-2014")) {
						cardInfo.setAdditionalContri("0.0");						
					}
					additionalContri=Double.parseDouble(cardInfo.getAdditionalContri());
					if(cardInfo.getMonthyear().equals("01-Apr-2015")) {
						addConList=commonDAO.pfCardAdditionalContriCalculation(pensionNo);
						additionalContri=Double.parseDouble(addConList.get(8).toString());
					}
					
					netepf=Double.parseDouble(cardInfo.getEmppfstatury())-additionalContri;					
					cardInfo.setNetEPF(Double.toString(netepf));
					total = new Double(df.format(Double.parseDouble(cardInfo
							.getNetEPF().trim())
							+ Double.parseDouble(cardInfo.getEmpvpf().trim())
							+ Double.parseDouble(cardInfo.getPrincipal().trim())
							+ Double.parseDouble(cardInfo.getInterest().trim())))
							.doubleValue();
				}
				if (formFlag == true) {
					log.info("loanAdvFlag:true:::"+cardInfo.getShnMnthYear());
					
					if(cardInfo.getShnMnthYear().equals("Apr-13") && loanAdvFlag == false) {
						
					}else{
					loanPFWPaid = commonDAO.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);

					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					}
					
					/*if(loanAdvFlag == true){
						log.info("loanAdvFlag:true");
						loanPFWPaid = commonDAO.getEmployeeLoans1(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
						advancePFWPaid = this.getEmployeeAdvances1(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);

						totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					}else{
						log.info("loanAdvFlag:false");
					loanPFWPaid = commonDAO.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);

					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
					}*/
				}

				cardInfo.setAdvancesAmount(Double.toString(Math
						.round(advancePFWPaid)));

				if (!finalSettlmentMonth.equals("---")) {
					if (commonDAO.compareFinalSettlementDates(fromDate, "31-Mar-"
							+ findToYear, "02-" + finalSettlmentMonth) == true) {
						
						/*new code for 2013-14 year int calculation===create contants remember*/
						if(fromDate.equals("01-Apr-2013") || fromDate.equals("01-Apr-2014")|| fromDate.equals("01-Apr-2015")|| fromDate.equals("01-Apr-2016")|| fromDate.equals("01-Apr-2017")|| fromDate.equals("01-Apr-2018")|| fromDate.equals("01-Apr-2019")|| fromDate.equals("01-Apr-2020")){
							log.info("Prasad for chking of 13369");
						Calendar cal = Calendar.getInstance();
						Date comparedDt = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
						try {
							comparedDt = dateFormat.parse(finalSettlmentMonth);
						cal.setTime(comparedDt);
						cal.add(Calendar.MONTH, -1);
						 comparedDt = cal.getTime();
						}catch(Exception e){
							e.printStackTrace();
						}
						finalSettlementFlag = commonUtil.compareTwoDates(
								dateFormat.format(cal.getTime()), checkDate);
					}else{
							finalSettlementFlag = commonUtil.compareTwoDates(
									finalSettlmentMonth, checkDate);	
						}
  //new code for 2013-14 year int calculation
						//finalSettlementFlag = commonUtil.compareTwoDates(
								//finalSettlmentMonth, checkDate);
						
					} else {
						/*
						 * if(cardInfo.getTransArrearFlag().equals("Y")&&
						 * commonUtil.getDateDifference("01-Apr-2010",fromDate)>=0){
						 * finalSettlementFlag = true; }else{
						 * finalSettlementFlag = false; }
						 */

						if (commonUtil.compareTwoDates(commonUtil
								.converDBToAppFormat("01-Mar-2011",
										"dd-MMM-yyyy", "MMM-yyyy"), commonUtil
								.converDBToAppFormat(fromDate, "dd-MMM-yyyy",
										"MMM-yyyy")) == true) {
							if (finalSettlmentMonth.equals("Mar-"
									+ findFromYear)) {
								if (commonUtil.getDifferenceTwoDatesInDays(
										"31-" + finalSettlmentMonth, cardInfo
												.getMonthyear()) > -30) {
									rareCaseflag = true;
								}
							}
						}
						if (rareCaseflag == true) {
							finalSettlementFlag = true;
						} else {
							finalSettlementFlag = false;
						}

					}

				} else {

					finalSettlementFlag = false;
				}
				log.info("Two dates informaiton"+commonUtil.compareTwoDates(commonUtil.converDBToAppFormat(
						"01-Mar-2010", "dd-MMM-yyyy", "MMM-yyyy"), commonUtil
						.converDBToAppFormat(fromDate, "dd-MMM-yyyy",
								"MMM-yyyy")));
				if (commonUtil.compareTwoDates(commonUtil.converDBToAppFormat(
						"01-Mar-2010", "dd-MMM-yyyy", "MMM-yyyy"), commonUtil
						.converDBToAppFormat(fromDate, "dd-MMM-yyyy",
								"MMM-yyyy")) == true
						&& !finalSettlmentMonth.equals("---")) {

					/*if (!(resttlementdate.equals("---"))) {
						if (commonDAO.compareFinalSettlementDates(fromDate,
								"31-Mar-" + findToYear, "02-"
										+ commonUtil.converDBToAppFormat(
												resttlementdate, "dd-MMM-yyyy",
												"MMM-yyyy")) == true) {
							resettlementFlag = commonUtil.compareTwoDates(
									commonUtil.converDBToAppFormat(
											resttlementdate, "dd-MMM-yyyy",
											"MMM-yyyy"), checkDate);
						}
					}*/
					if (resettlementFlag == true) {
						finalSettlementFlag = true;
						arrearsFlag = false;

					}

				}
			/*	if (isFrozenAvail==true && arrearFlag.equals("Y")){
					//arrearsFlag=true;
					cardInfo.setTransArrearFlag("Y");
				}
				log.info("finalSettlementFlag======================"
						+ finalSettlementFlag + "checkDate" + checkDate
						+ "resettlementFlag" + resettlementFlag
						+ "finalSettlmentMonth" + finalSettlmentMonth
						+ "rareCaseflag" + rareCaseflag + "finalSettlementFlag"
						+ finalSettlementFlag+"isFrozenAvail"+isFrozenAvail);*/
				empNet = total - totalAdvancePFWPaid;
				rareCaseflag = false;
				resettlementFlag = false;
				if (arrearsFlag == true) {
					arrearEmpCumlative = arrearEmpCumlative + empNet;

					cardInfo.setArrearEmpNetCummulative(Double
							.toString(arrearEmpCumlative));

					grandArrearEmpCumlative = grandArrearEmpCumlative
							+ arrearEmpCumlative;

					cardInfo.setGrandArrearEmpNetCummulative(Double
							.toString(Math.round(grandArrearEmpCumlative)));
				}
				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				if (finalSettlementFlag == true) {
					empNet = 0;
				}
				empCumlative = empCumlative + empNet;
				//restrict cumilative int for mar month for the year 2013-14 onwords ...
				if(cardInfo.getMonthyear().equals("01-Mar-2014") || cardInfo.getMonthyear().equals("01-Mar-2015") || cardInfo.getMonthyear().equals("01-Mar-2016")|| cardInfo.getMonthyear().equals("01-Mar-2017")|| cardInfo.getMonthyear().equals("01-Mar-2018")|| cardInfo.getMonthyear().equals("01-Mar-2019")|| cardInfo.getMonthyear().equals("01-Mar-2020")|| cardInfo.getMonthyear().equals("01-Mar-2021")){
					finalSettlementFlag=true;
				}
				if (finalSettlementFlag == false) {
					if (Integer.parseInt(findFromYear) >= 2011) {
						cardInfo.setYearFlag(true);
						if (rs.getInt("signs") == 1) {
								grandEmpCumlative = grandEmpCumlative + empNet;	
							
							cardInfo.setGrandCummulative(Double.toString(Math
									.round(grandEmpCumlative)));
						} else {
							cardInfo.setGrandCummulative(Double.toString(Math
									.round(0)));
						}

					} else {
						grandEmpCumlative = grandEmpCumlative + empCumlative;
						cardInfo.setGrandCummulative(Double.toString(Math
								.round(grandEmpCumlative)));
					}

				}
				log
				.info("grandEmpCumlative====================================="+cardInfo.getMonthyear()+"::"
						+ grandEmpCumlative);
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}
				if (rs.getString("emolumentmonths") != null) {
					emolumentsMonths = rs.getString("emolumentmonths");
				}

				if (yearBreakMonthFlag == false) {
					if (flag == false) {
						if (!cardInfo.getMonthyear().equals("-NA-")
								&& !dateOfRetriment.equals("")) {
							transMntYear = Date.parse(cardInfo.getMonthyear());
							empRetriedDt = Date.parse(dateOfRetriment);

							if (transMntYear > empRetriedDt) {
								contrFlag = true;
							} else if (transMntYear == empRetriedDt) {
								chkDOBFlag = true;
							}
						}
						/*
						 * log.info("transMntYear" + transMntYear +
						 * "empRetriedDt" + empRetriedDt+"contrFlag" + contrFlag +
						 * "chkDOBFlag" + chkDOBFlag);
						 */

						if (contrFlag != true) {
							/*pensionAsPerOption = commonDAO.pensionCalculation(
									cardInfo.getMonthyear(), cardInfo
											.getEmoluments(), wetherOption,
									region, emolumentsMonths);
							if (chkDOBFlag == true) {
								String[] dobList = dateOfBirth.split("-");
								days = dobList[0];
								getDaysBymonth = commonDAO.getNoOfDays(dateOfBirth);
								pensionAsPerOption = Math
										.round(pensionAsPerOption
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth);

							}*/

						} else {
							pensionAsPerOption = 0;
						}
						cardInfo.setPensionContribution(Double
								.toString(pensionAsPerOption));
					} else {
						cardInfo.setPensionContribution("0");
					}
				} else {
					pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				}
				/*
				 * log.info("flag" + flag + checkDate + "Pension" +
				 * cardInfo.getPensionContribution());
				 */
				if (formFlag == true) {
					if(cardInfo.getShnMnthYear().equals("Apr-13") && loanAdvFlag == false) {
						
					}else{
						advPFDrawn = commonDAO.getEmployeeLoans(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);	
					}
					/*if(loanAdvFlag == true){
						advPFDrawn = commonDAO.getEmployeeLoans1(con, cardInfo
								.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
					}else{
					advPFDrawn = commonDAO.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
					}*/
				}
				if (yearBreakMonthFlag == false) {
					aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				} else {
					aaiPF = rs.getDouble("PF");
				}

				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				/*
				 * log.info("aaiPF=======================================" +
				 * aaiPF + "advPFDrawn" + advPFDrawn + "aaiNet" + aaiNet);
				 */
				cardInfo.setAaiNet(Double.toString(aaiNet));
				if (arrearsFlag == true) {
					arrearAaiNetCumlative = arrearAaiNetCumlative + aaiNet;
					cardInfo.setArrearAaiCummulative(Double
							.toString(arrearAaiNetCumlative));
					grandArrearAaiCumlative = grandArrearAaiCumlative
							+ arrearAaiNetCumlative;
					cardInfo.setGrandArrearAAICummulative(Double
							.toString(grandArrearAaiCumlative));
				}
				if (finalSettlementFlag == true) {
					aaiNet = 0;
				}

				aaiNetCumlative = aaiNetCumlative + aaiNet;

				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (finalSettlementFlag == false) {
					if (Integer.parseInt(findFromYear) >= 2011) {
						cardInfo.setYearFlag(true);
						if (rs.getInt("signs") == 1) {
							
							grandAaiCumlative = grandAaiCumlative + aaiNet;
							cardInfo.setGrandAAICummulative(Double
									.toString(Math.round(grandAaiCumlative)));
						} else {
							cardInfo.setGrandAAICummulative(Double
									.toString(Math.round(0)));
						}
					} else {
						grandAaiCumlative = grandAaiCumlative + aaiNetCumlative;
						cardInfo.setGrandAAICummulative(Double.toString(Math
								.round(grandAaiCumlative)));
					}

				}
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}

				if (rs.getString("MERGEFLAG") != null) {
					cardInfo.setMergerflag(rs.getString("MERGEFLAG"));
				} else {
					cardInfo.setMergerflag("N");
				}
				if (cardInfo.getMergerflag().equals("Y")) {
					if (rs.getString("REMARKS") != null) {
						cardInfo.setMergerremarks(rs.getString("REMARKS"));
					} else {
						cardInfo.setMergerremarks("---");
					}
				} else {
					cardInfo.setMergerremarks("---");
				}
				/*if (rs.getString("PFCARDNARRATION") != null) {
					cardInfo.setPfcardNarration(rs.getString("PFCARDNARRATION"));
				} else {
					cardInfo.setPfcardNarration("---");
				}*/
				finalSettlementFlag = false;
				// arrearsFlag=false;
				arrearFlag="N";						
				
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return pensionList;
	}


	public ArrayList getPTWDetails(Connection con, String fromYear,
			String toYear, String pfIDS, String pensionNo) {
		log.info("Purpose===getPTWDetails");

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", finalFromDate = "";

		PTWBean ptwBean = null;
		ArrayList ptwList = new ArrayList();
		/*
		 * if (flag.equals("NAD")) { sqlQuery =
		 * "SELECT CPFACCNO,ADVTRANSDATE,ADVPURPOSE,ROUND(AMOUNT)AS AMOUNT FROM EMPLOYEE_PENSION_ADVANCES WHERE (TO_DATE(to_char(ADVTRANSDATE,'dd-Mon-yyyy'))>='"
		 * + fromYear +
		 * "' AND TO_DATE(to_char(ADVTRANSDATE,'dd-Mon-yyyy'))<=LAST_DAY('" +
		 * toYear + "')) AND CPFACCNO='" + pfIDS + "' ORDER BY CPFACCNO"; } else
		 * if (flag.equals("IAD")) {
		 */
		try {
			finalFromDate = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Integer.parseInt(finalFromDate) >= 2008) {
			sqlQuery = "SELECT CPFACCNO,LOANDATE AS ADVTRANSDATE,LOANPURPOSE AS ADVPURPOSE,ROUND(SUB_AMT+CONT_AMT)AS AMOUNT FROM EMPLOYEE_PENSION_LOANS WHERE  TO_DATE(to_char(LOANDATE,'dd-Mon-yyyy'))<=LAST_DAY('"
					+ toYear
					+ "') AND PENSIONNO='"
					+ pensionNo
					+ "' ORDER BY LOANDATE,pensionNo";
		} else {
			sqlQuery = "SELECT CPFACCNO,LOANDATE AS ADVTRANSDATE,LOANPURPOSE AS ADVPURPOSE,ROUND(SUB_AMT+CONT_AMT)AS AMOUNT FROM EMPLOYEE_PENSION_LOANS WHERE TO_DATE(to_char(LOANDATE,'dd-Mon-yyyy'))<=LAST_DAY('"
					+ toYear
					+ "') AND CPFACCNO='"
					+ pfIDS
					+ "' ORDER BY LOANDATE,CPFACCNO";
		}

		/* } */

		log.info("getPTWDetails=================================="+sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				ptwBean = new PTWBean();
				log
						.info("Purpose===getPTWDetails"
								+ rs.getString("ADVPURPOSE"));
				if (rs.getDate("ADVTRANSDATE") != null) {
					ptwBean.setPtwDate(commonUtil.getDatetoString(rs
							.getDate("ADVTRANSDATE"), "dd-MMM-yyyy"));
				} else {
					ptwBean.setPtwDate("---");
				}
				if (rs.getString("ADVPURPOSE") != null) {
					ptwBean.setPtwPurpose(rs.getString("ADVPURPOSE"));
				} else {
					ptwBean.setPtwPurpose("---");
				}
				if (rs.getString("AMOUNT") != null) {
					ptwBean.setPtwAmount(rs.getString("AMOUNT"));
				} else {
					ptwBean.setPtwAmount("---");
				}

				ptwBean.setRemarks("---");

				ptwList.add(ptwBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return ptwList;
	}

	public ArrayList getFinalSettlement(Connection con, String fromYear,
			String toYear, String pfIDS, String pensionno) {
		log.info("Purpose===getFinalSettlement");

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String finalEmpSub = "", findFromYear = "", findToYear = "", finalAAICont = "", finalPensionCon = "", finalNetAmount = "", finalPurpose = "", finalPensionNo = "", finalCpfaccno = "", finalAirportcode = "", finalRegion = "";
		String finalStlmntDt = "", finalEmployeeNo = "", finalEmpName = "",finalSetlmntMsg="",finalSetlmntNxtMonth="",finalSetlmntEndMonth="";

		ArrayList finalSettlementList = new ArrayList();
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toYear, "dd-MMM-yyyy",
					"yyyy");
			finalSetlmntEndMonth = commonUtil.converDBToAppFormat(toYear, "dd-MMM-yyyy",
			"MMM-yy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			/*sqlQuery = "SELECT PENSIONNO,CPFACCNO,EMPLOYEENO,PURPOSE,EMPLOYEENAME,FINEMP,(FINAAI-NVL(PENCON,'0.00')) AS FINAAI,PENCON,NETAMOUNT,SETTLEMENTDATE,AIRPORTCODE,REGION FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE PENSIONNO="
					+ pensionno + " AND SETTLEMENTDATE BETWEEN '"+fromYear+"' AND '"+toYear+"' AND SETTLEMENTDATE IS NOT NULL ORDER BY PENSIONNO";*/
			sqlQuery="SELECT WITHOUTSUM.PENSIONNO AS PENSIONNO, WITHOUTSUM.CPFACCNO AS CPFACCNO, WITHOUTSUM.EMPLOYEENO AS EMPLOYEENO, WITHOUTSUM.PURPOSE AS PURPOSE, WITHOUTSUM.EMPLOYEENAME AS EMPLOYEENAME, SUMDATA.FINEMP AS FINEMP, SUMDATA.FINAAI AS FINAAI, SUMDATA.PENCON AS PENCON, SUMDATA.NETAMOUNT AS NETAMOUNT , " +
					"SUMDATA.SETTLEMENTDATE AS SETTLEMENTDATE,to_char(add_months(SUMDATA.SETTLEMENTDATE,1),'Mon-yy') as NEXTMNTHINT, WITHOUTSUM.AIRPORTCODE AS AIRPORTCODE, WITHOUTSUM.REGION AS REGION FROM(SELECT PENSIONNO, CPFACCNO, EMPLOYEENO, PURPOSE, EMPLOYEENAME, AIRPORTCODE, REGION FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE PENSIONNO = "+ pensionno +" AND SETTLEMENTDATE " +
					"BETWEEN '"+fromYear+"' AND LAST_DAY('"+toYear+"') AND SETTLEMENTDATE IS NOT NULL AND ROWNUM=1 ORDER BY PENSIONNO) WITHOUTSUM, (SELECT PENSIONNO,MIN(SETTLEMENTDATE) AS SETTLEMENTDATE, SUM(FINEMP) AS FINEMP, SUM((FINAAI - NVL(PENCON, '0.00'))) AS FINAAI, SUM(PENCON) AS PENCON, " +
					"SUM(NETAMOUNT) AS NETAMOUNT FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE PENSIONNO = "+ pensionno +" AND SETTLEMENTDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') AND SETTLEMENTDATE IS NOT NULL GROUP BY PENSIONNO) SUMDATA WHERE WITHOUTSUM.PENSIONNO=SUMDATA.PENSIONNO";
		} else {
			/*sqlQuery = "SELECT PENSIONNO,CPFACCNO,EMPLOYEENO,PURPOSE,EMPLOYEENAME,FINEMP,(FINAAI-NVL(PENCON,'0.00'))AS FINAAI,PENCON,NETAMOUNT,SETTLEMENTDATE,AIRPORTCODE,REGION FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE "
					+ "(" + pfIDS + ") AND SETTLEMENTDATE BETWEEN '"+fromYear+"' AND '"+toYear+"' AND SETTLEMENTDATE IS NOT NULL ORDER BY PENSIONNO";*/
			sqlQuery="SELECT WITHOUTSUM.PENSIONNO AS PENSIONNO, WITHOUTSUM.CPFACCNO AS CPFACCNO, WITHOUTSUM.EMPLOYEENO AS EMPLO, WITHOUTSUM.PURPOSE AS PURPOSE, WITHOUTSUM.EMPLOYEENAME AS EMPLOYEENAME, SUMDATA.FINEMP AS FINEMP, SUMDATA.FINAAI AS FINAAI, SUMDATA.PENCON AS PENCON, SUMDATA.NETAMOUNT AS NETAMOUNT , " +
			"SUMDATA.SETTLEMENTDATE AS SETTLEMENTDATE,to_char(add_months(SUMDATA.SETTLEMENTDATE,1),'Mon-yy') as NEXTMNTHINT, WITHOUTSUM.AIRPORTCODE AS AIRPORTCODE, WITHOUTSUM.REGION AS REGION FROM(SELECT PENSIONNO, CPFACCNO, EMPLOYEENO, PURPOSE, EMPLOYEENAME, AIRPORTCODE, REGION FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE "+ "(" + pfIDS + ")  AND SETTLEMENTDATE " +
			"BETWEEN '"+fromYear+"' AND LAST_DAY('"+toYear+"') AND SETTLEMENTDATE IS NOT NULL AND ROWNUM=1 ORDER BY PENSIONNO) WITHOUTSUM, (SELECT PENSIONNO,MIN(SETTLEMENTDATE) AS SETTLEMENTDATE, SUM(FINEMP) AS FINEMP, SUM((FINAAI - NVL(PENCON, '0.00'))) AS FINAAI, SUM(PENCON) AS PENCON, " +
			"SUM(NETAMOUNT) AS NETAMOUNT FROM EMPLOYEE_PENSION_FINSETTLEMENT WHERE "+ "(" + pfIDS + ")  AND SETTLEMENTDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') AND SETTLEMENTDATE IS NOT NULL GROUP BY PENSIONNO) SUMDATA WHERE WITHOUTSUM.PENSIONNO=SUMDATA.PENSIONNO";
		}

		log.info("getFinalSettlement"+sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (rs.getString("PENSIONNO") != null) {
					finalPensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("CPFACCNO") != null) {
					finalCpfaccno = rs.getString("CPFACCNO");
				} else {
					finalCpfaccno = "---";
				}
				if (rs.getString("EMPLOYEENO") != null) {
					finalEmployeeNo = rs.getString("EMPLOYEENO");
				} else {
					finalEmployeeNo = "---";
				}
				if (rs.getString("PURPOSE") != null) {
					finalPurpose = rs.getString("PURPOSE");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					finalEmpName = rs.getString("EMPLOYEENAME");
				} else {
					finalEmpName = "---";
				}
				if (rs.getString("FINEMP") != null) {
					finalEmpSub = rs.getString("FINEMP");
				} else {
					finalEmpSub = "0";
				}
				if (rs.getString("FINAAI") != null) {
					finalAAICont = rs.getString("FINAAI");
				} else {
					finalAAICont = "0";
				}
				if (rs.getString("PENCON") != null) {
					finalPensionCon = rs.getString("PENCON");
				} else {
					finalPensionCon = "0";
				}
				if (rs.getString("NETAMOUNT") != null) {
					finalNetAmount = rs.getString("NETAMOUNT");
				} else {
					finalNetAmount = "0";
				}
				if (rs.getString("AIRPORTCODE") != null) {
					finalAirportcode = rs.getString("AIRPORTCODE");
				}
				if (rs.getString("REGION") != null) {
					finalRegion = rs.getString("REGION");
				}
				if (rs.getString("SETTLEMENTDATE") != null) {
					finalStlmntDt = commonUtil.getDatetoString(rs
							.getDate("SETTLEMENTDATE"), "dd-MMM-yyyy");
				}
				if (rs.getString("NEXTMNTHINT") != null) {
					finalSetlmntNxtMonth = rs.getString("NEXTMNTHINT");
				}
				finalSetlmntMsg="From "+finalSetlmntNxtMonth+" To "+finalSetlmntEndMonth;
				
				finalSettlementList.add(finalPensionNo);
				finalSettlementList.add(finalCpfaccno);
				finalSettlementList.add(finalEmployeeNo);
				finalSettlementList.add(finalPurpose);
				finalSettlementList.add(finalEmpName);
				finalSettlementList.add(finalEmpSub);
				finalSettlementList.add(finalAAICont);
				finalSettlementList.add(finalPensionCon);
				finalSettlementList.add(finalNetAmount);
				finalSettlementList.add(finalAirportcode);
				finalSettlementList.add(finalRegion);
				finalSettlementList.add(finalStlmntDt);
				finalSettlementList.add(finalSetlmntMsg);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return finalSettlementList;
	}


	private ArrayList updatePendingEmpPCInfo() {
		CommonDAO commonDAO = new CommonDAO();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "";
		EmployeePersonalInfo data = null;

		ArrayList empinfo = new ArrayList();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "select info.REFPENSIONNUMBER,info.CPFACNO,info.AIRPORTSERIALNUMBER,info.EMPLOYEENO,INITCAP(info.EMPLOYEENAME) AS EMPLOYEENAME,info.DESEGNATION,info.EMP_LEVEL,info.DATEOFBIRTH,info.DATEOFJOINING,info.DATEOFSEPERATION_REASON,info.DATEOFSEPERATION_DATE,info.WHETHER_FORM1_NOM_RECEIVED,info.REMARKS,info.AIRPORTCODE,info.GENDER,info.FHNAME,info.MARITALSTATUS,"
					+ "info.PERMANENTADDRESS,info.TEMPORATYADDRESS,info.WETHEROPTION,info.SETDATEOFANNUATION,info.EMPFLAG,info.DIVISION,info.DEPARTMENT,info.EMAILID,info.EMPNOMINEESHARABLE,info.REGION,info.PENSIONNO,(LAST_DAY(info.dateofbirth) + INTERVAL '60' year ) as DOR,info.username,info.LASTACTIVE,info.RefMonthYear,info.IPAddress,info.OTHERREASON,"
					+ "decode(sign(round(months_between(info.dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(info.dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((info.dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', info.dateofbirth) / 12) EMPAGE "
					+ "from(select REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,"
					+ "DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,"
					+ "to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO) info, (select distinct pensionno from employee_pension_validate where monthyear between '01-Apr-2008' and '31-Mar-2009' and pf is null and PENSIONCONTRI is null order by pensionno) trans "
					+ "where trans.pensionno=info.pensionno(+) order by trans.pensionno";
			log.info("FinanceReportDAO::updatePendingEmpPCInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
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
				log.info(data.getWhetherOptionDescr());
				pfidWithRegion = this.getEmployeeMappingPFInfo(con, data
						.getPensionNo(), data.getCpfAccno(), data.getRegion());

				String[] pfIDLists = pfidWithRegion.split("=");
				data.setPfIDString(this.preparedCPFString(pfIDLists));
				log.info(data.getPfIDString());
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return empinfo;
	}

	private String chkTrustWise(String empSerialNo, Connection con,
			String obMnthYear) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "";
		String chkFlag = "";
		try {
			sqlInsQuery = "SELECT 'X' AS FLAG FROM employee_trust_pc_report WHERE TRUSTOBYEAR='"
					+ obMnthYear + "' AND PENSIONNO='" + empSerialNo + "'";
			st = con.createStatement();
			log.info("chkOBAdjInfo::Query" + sqlInsQuery);
			rs = st.executeQuery(sqlInsQuery);
			if (rs.next()) {
				if (rs.getString("FLAG") != null) {
					chkFlag = rs.getString("FLAG");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return chkFlag;
	}

	private int updateTrustWiseBal(Connection con, double pension,
			double pensionintrest, String pensionno, String region,
			String adjOBYear, String remarks) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", sqlAdjQuery = "";
		DecimalFormat df = new DecimalFormat("#########0");

		try {

			sqlInsQuery = "UPDATE EMPLOYEE_TRUST_PC_REPORT SET PENSIONTOTAL='"
					+ df.format(pension) + "',INTEREST='"
					+ df.format(pensionintrest) + "',REMARKS='" + remarks
					+"',REGION='" + region
					+ "' WHERE PENSIONNO='" + pensionno + "' AND TRUSTOBYEAR='"+adjOBYear+"'";
			st = con.createStatement();
			log.info("updateAdjBal::Inserted Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);

			/*
			 * if(count!=0){sqlAdjQuery=
			 * "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
			 * +pensionno; count = st.executeUpdate(sqlAdjQuery);
			 * log.info("updateAdjBal=========================="+sqlAdjQuery); }
			 */

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}

	private int updateTrustWise(Connection con, double pension,
			double pensionintrest, String pensionno, String region,
			String adjOBYear, String remarks) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", sqlAdjQuery = "";
		DecimalFormat df = new DecimalFormat("#########0");

		try {

			sqlInsQuery = "UPDATE EMPLOYEE_TRUST_PC_REPORT SET PENSIONTOTAL='"
					+ df.format(pension) + "',INTEREST='"
					+ df.format(pensionintrest) + "',REMARKS='" + remarks
					+ "',REGION='" + region + "' WHERE PENSIONNO='" + pensionno
					+ "' AND TRUSTOBYEAR='" + adjOBYear + "'";
			st = con.createStatement();
			log.info("updateAdjBal::Inserted Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);

			/*
			 * if(count!=0){sqlAdjQuery=
			 * "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
			 * +pensionno; count = st.executeUpdate(sqlAdjQuery);
			 * log.info("updateAdjBal=========================="+sqlAdjQuery); }
			 */

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}
	 //	By Radha On 29-Sep-2011  Changing rate of interest  9.5 for 2010-2011 and 2011-2012
	private int updatePFCardClosingBal(Connection con, String fromDate,
			String toDate, String pfIDS, String wetherOption, String region,
			boolean formFlag, String dateOfRetriment, String dateOfBirth,
			String pensionNo, int totalNoOfMonths, String finalSettlementDate,String checkPendingFlag,ArrayList finalSttmentList,int interesNoMonths,String chkArrearFlag,String adjDate,String adjAmount,String adjInt) {

		DecimalFormat df = new DecimalFormat("#########0.00");

		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		ArrayList closingOBList = new ArrayList();
		ArrayList closingYearList = new ArrayList();
		boolean flag = false,arrearsFlag=false;
	
		boolean contrFlag = false, chkDOBFlag = false, finalSettlementFlag = false, yearBreakMonthFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", sqlGenAdjQuery = "";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "", findFromYear = "", finalSettlmentYear="",findToYear = "", calYear = "", chkMappingFlag = "", transRegion = "", transCpfaccno = "";
		int getDaysBymonth = 0, generatedCnt = 0;
		long transMntYear = 0, empRetriedDt = 0;
		double rateOfInterest = 0,totalGrandArrearEmpNet=0.0,totalGrandArrearAaiNet=0.0;;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double totalArrearEmpNet=0.0,totalArrearAAINet=0.0;
		double advancePFWPaidTotal=0,totalAdvancePFWPaid = 0,grandArrearEmpCumlative=0,arrearEmpCumlative=0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0, aaiContrCumlative = 0.0, grandEmpCumlative = 0.0, grandAaiCumlative = 0.0;
		double pensionAsPerOption = 0.0, pensionInterest = 0.0,arrearAaiNetCumlative=0.0,grandArrearAaiCumlative=0.0;
		double totalEmoluments = 0.0, dispTotalEmpNet = 0.0, totalAdvances=0,dispTotalAAINet = 0.0, pfStaturary = 0.0, empVpf = 0.0, principle = 0.0, interest = 0.0, empTotal = 0.0, totalEmpNet = 0.0, totalEmpIntNet = 0.0, totalAaiIntNet = 0.0;
		double totalAAIPF = 0.0, arrearEmpNetInterest=0.0,arrearAaiNetInterest=0.0,totalPFDrwn = 0.0, totalAAINet = 0.0, empNetInterest = 0.0, aaiNetInterest = 0.0, totalPensionContr = 0.0, totalPensionCum = 0.0, penInterest = 0.0;
		boolean obFlag = false;
		int insereted = 0, totalRecordIns = 0, adjInterest = 0;
		String empNetOB = "", aaiNetOB = "", pensionOB = "", obFromTrFlag = "", tempPensioNo = "",arrearFlag="N";
		String adjOBYear = "", adjOBRemarks = "",emolumentsMonths="";
		try {
			String getToYear = commonUtil.converDBToAppFormat(toDate,
					"dd-MMM-yyyy", "yyyy");
			if (getToYear.equals("2008")) {
				adjOBYear = Constants.ADJ_CREATED_DATE_2008;
				adjOBRemarks = "Generated From 1995-08 Grand Total PC Report";
			} if (getToYear.equals("2009")) {
				adjOBYear = ADJ_CREATED_DATE_2009;
				adjOBRemarks = "Generated From 2008-09 Grand Total PC Report";
			} else if (getToYear.equals("2010")) {
				adjOBYear = ADJ_CREATED_DATE_2010;
				adjOBRemarks = "Generated From 2009-10 Grand Total PC Report";
			}else if (getToYear.equals("2011")) {
				adjOBYear = ADJ_CREATED_DATE_2011;
				adjOBRemarks = "Generated From 2010-11 Grand Total PC Report";
			}
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}

		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
			if(!finalSettlementDate.equals("---")){
				finalSettlmentYear= commonUtil.converDBToAppFormat(finalSettlementDate, "MMM-yyyy",
				"yyyy");
			}
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			pfIDS="";
			yearBreakMonthFlag = true;
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,REGION,CPFACCNO,PENSIONCONTRI,PF,emolumentmonths FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND PENSIONNO="
					+ pensionNo
					+ ") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
		} else {
			yearBreakMonthFlag = false;
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,REGION,CPFACCNO,PENSIONCONTRI,PF,emolumentmonths FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND ("
					+ pfIDS
					+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
		}

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));
					} else {
						cardInfo.setMonthyear("---");
					}
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy",
								"MMM-yy"));
					}

				}
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					calYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "yyyy");
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}

				if(rs.getString("arrearflag")!=null){
						arrearFlag=rs.getString("arrearflag");
					}else{
						arrearFlag="N";
					}
					
					if(arrearFlag.equals("Y") && arrearsFlag==false){
						arrearsFlag=true;
					}

				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}

				/*
				 * if (region.equals("CHQNAD")) {
				 * 
				 * if (rs.getString("CPFADVANCE") != null) {
				 * cardInfo.setPrincipal(rs.getString("CPFADVANCE")); } else {
				 * cardInfo.setPrincipal("0"); } } else if
				 * (region.equals("North Region")) { if (rs.getString("REFADV")
				 * != null) { cardInfo.setPrincipal(rs.getString("REFADV")); }
				 * else { cardInfo.setPrincipal("0"); } }
				 */{
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						cardInfo.setPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						cardInfo.setPrincipal("0");
					}
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					
					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("Region" + region + "loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				}
				cardInfo.setAdvancesAmount(Double.toString(Math
						.round(advancePFWPaid)));
				/*
				 * log.info("cardInfo.getShnMnthYear()" +
				 * cardInfo.getShnMnthYear() + "advancePFWPaid" +
				 * advancePFWPaid);
				 */
				if (!finalSettlementDate.equals("---")) {
					if(this.compareFinalSettlementDates(fromDate,"31-Mar-"+findToYear,"02-"+finalSettlementDate)==true){
						log.info("Update PFCard findFromYear===========Condition==========="
								+ fromDate + "finalSettlmentMonth" + finalSettlementDate
								+ "findToYear" + toDate);
						finalSettlementFlag = commonUtil.compareTwoDates(
								finalSettlementDate, checkDate);
					}else{
						log.info("Update PFCardfindFromYear========Else=============="
								+ fromDate + "finalSettlmentMonth" + finalSettlementDate
								+ "findToYear" + toDate);
						finalSettlementFlag = false;
					}
					
				} else {
					log.info("Update PFCard findFromYear====SecondEles =Else================="
							+ fromDate + "finalSettlmentMonth" + finalSettlementDate
							+ "findToYear" + toDate);
					finalSettlementFlag = false;
				}

				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				
				if(arrearsFlag==true){
					arrearEmpCumlative=arrearEmpCumlative+empNet;
					
					cardInfo.setArrearEmpNetCummulative(Double.toString(arrearEmpCumlative));
					
					grandArrearEmpCumlative =grandArrearEmpCumlative+arrearEmpCumlative;
					
					cardInfo.setGrandArrearEmpNetCummulative(Double.toString(Math
							.round(grandArrearEmpCumlative)));
				}else{
					cardInfo.setArrearEmpNetCummulative("");
					cardInfo.setGrandArrearEmpNetCummulative("");
				}
				if (finalSettlementFlag == true) {
					empNet = 0;
				}
				empCumlative = empCumlative + empNet;

				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (finalSettlementFlag == false) {
					grandEmpCumlative = grandEmpCumlative + empCumlative;
					cardInfo.setGrandCummulative(Double.toString(Math
							.round(grandEmpCumlative)));
				}

				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}
				if (rs.getString("emolumentmonths") != null) {
					emolumentsMonths=rs.getString("emolumentmonths");
				}
				if (yearBreakMonthFlag == false) {
					if (flag == false) {
						if (!cardInfo.getMonthyear().equals("-NA-")
								&& !dateOfRetriment.equals("")) {
							transMntYear = Date.parse(cardInfo.getMonthyear());
							empRetriedDt = Date.parse(dateOfRetriment);

							if (transMntYear > empRetriedDt) {
								contrFlag = true;
							} else if (transMntYear == empRetriedDt) {
								chkDOBFlag = true;
							}
						}

						if (contrFlag != true) {
							pensionAsPerOption = this.pensionCalculation(
									cardInfo.getMonthyear(), cardInfo
											.getEmoluments(), wetherOption,
									region,emolumentsMonths);
							if (chkDOBFlag == true) {
								String[] dobList = dateOfBirth.split("-");
								days = dobList[0];
								getDaysBymonth = this.getNoOfDays(dateOfBirth);
								pensionAsPerOption = Math
										.round(pensionAsPerOption
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth);

							}

						} else {
							pensionAsPerOption = 0;
						}
						cardInfo.setPensionContribution(Double
								.toString(pensionAsPerOption));
					} else {
						cardInfo.setPensionContribution("0");
					}
				} else {
					pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				}

				if (formFlag == true) {
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				}
				if (yearBreakMonthFlag == false) {
					aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				} else {
					aaiPF = rs.getDouble("PF");
				}
	
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				if(arrearsFlag==true){
					arrearAaiNetCumlative=arrearAaiNetCumlative+aaiNet;
					cardInfo.setArrearAaiCummulative(Double.toString(arrearAaiNetCumlative));
					grandArrearAaiCumlative =grandArrearAaiCumlative+arrearAaiNetCumlative;
					cardInfo.setGrandArrearAAICummulative(Double.toString(grandArrearAaiCumlative));
				}else{
					cardInfo.setArrearAaiCummulative("");
					cardInfo.setGrandArrearAAICummulative("");
				}
				log.info("MonthYear"+cardInfo.getMonthyear()+"grandArrearAaiCumlative"+grandArrearAaiCumlative+"EmpnETcUMMLATIVE"+grandArrearEmpCumlative);
				if (finalSettlementFlag == true) {
					aaiNet = 0;
				}
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				

				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (finalSettlementFlag == false) {
					grandAaiCumlative = grandAaiCumlative + aaiNetCumlative;
					cardInfo.setGrandAAICummulative(Double.toString(Math
							.round(grandAaiCumlative)));
				}
				aaiContrCumlative = aaiContrCumlative + pensionAsPerOption;
				cardInfo.setAaiContrCumlative(Double
						.toString(aaiContrCumlative));

				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				if (rs.getString("REGION") != null) {
					transRegion = rs.getString("REGION");
				}
				if (rs.getString("CPFACCNO") != null) {
					transCpfaccno = rs.getString("CPFACCNO");
				}

				 /*this.updateTransTblPC(transCpfaccno,con,transRegion,cardInfo.
				 getMonthyear(),pensionAsPerOption,aaiPF,pensionNo);*/
				/* pensionList.add(cardInfo); */
				if(arrearFlag.equals("N")||(arrearFlag.equals("Y")&&(chkArrearFlag.equals("N")))){
					totalEmoluments = new Double(df.format(totalEmoluments
							+ Math.round(Double.parseDouble(cardInfo
									.getEmoluments())))).doubleValue();
					pfStaturary = new Double(df.format(pfStaturary
							+ Math.round(Double.parseDouble(cardInfo
									.getEmppfstatury())))).doubleValue();
					empVpf = new Double(df.format(empVpf
							+ Math.round(Double.parseDouble(cardInfo.getEmpvpf()))))
							.doubleValue();
					principle = new Double(df.format(principle
							+ Math.round(Double
									.parseDouble(cardInfo.getPrincipal()))))
							.doubleValue();
					interest = new Double(df.format(interest
							+ Math
									.round(Double.parseDouble(cardInfo
											.getInterest())))).doubleValue();
					empTotal = new Double(df.format(empTotal
							+ Math
									.round(Double.parseDouble(cardInfo
											.getEmptotal())))).doubleValue();
					advancePFWPaidTotal = new Double(df.format(advancePFWPaidTotal
							+ Math.round(Double.parseDouble(cardInfo
									.getAdvancePFWPaid())))).doubleValue();
					if (!cardInfo.getGrandCummulative().equals("")) {
						totalEmpNet = new Double(df.format(Double
								.parseDouble(cardInfo.getGrandCummulative())))
								.doubleValue();
					}

					dispTotalEmpNet = new Double(df.format(dispTotalEmpNet
							+ Math.round(Double.parseDouble(cardInfo.getEmpNet()))))
							.doubleValue();

					totalAAIPF = new Double(df.format(totalAAIPF
							+ Math.round(Double.parseDouble(cardInfo.getAaiPF()))))
							.doubleValue();
					totalPFDrwn = new Double(df
							.format(totalPFDrwn
									+ Math.round(Double.parseDouble(cardInfo
											.getPfDrawn())))).doubleValue();
					if (!cardInfo.getGrandCummulative().equals("")) {
						totalAAINet = new Double(df.format(Double
								.parseDouble(cardInfo.getGrandAAICummulative())))
								.doubleValue();
					}
					totalAdvances=new Double(df.format(totalAdvances+Math.round( Double.parseDouble(cardInfo.getAdvancesAmount())))).doubleValue();
					dispTotalAAINet = new Double(df.format(dispTotalAAINet
							+ Math.round(Double.parseDouble(cardInfo.getAaiNet()))))
							.doubleValue();

					totalPensionContr = new Double(df.format(totalPensionContr
							+ Math.round(Double.parseDouble(cardInfo
									.getPensionContribution())))).doubleValue();
					totalPensionCum = new Double(df.format(totalPensionCum
							+ Math.round(Double.parseDouble(cardInfo
									.getAaiContrCumlative())))).doubleValue();
				}else{
					totalArrearEmpNet=totalArrearEmpNet+Math.round( Double.parseDouble(cardInfo.getEmpNet()));
	  				totalArrearAAINet=totalArrearAAINet+Math.round(Double.parseDouble(cardInfo.getAaiNet()));
       			    arrearFlag="Y";
	       			
	  		   }
				
			}
			 if(!cardInfo.getGrandArrearEmpNetCummulative().equals("")){
			    	
			    	totalGrandArrearEmpNet= new Double(df.format(Double.parseDouble(cardInfo.getGrandArrearEmpNetCummulative()))).doubleValue();
		    }
		  	if(!cardInfo.getGrandArrearAAICummulative().equals("")){
			    	
			    	totalGrandArrearAaiNet= new Double(df.format(Double.parseDouble(cardInfo.getGrandArrearAAICummulative()))).doubleValue();
		    } 	
			log.info(empNetOB + "," + aaiNetOB + "," + pensionOB + ","
					+ obFromTrFlag.trim());
			log
					.info("===============================Data========================================================================================");
			log.info(pfStaturary + "," + empVpf + "," + principle + ","
					+ interest + "," + empTotal + "," + advancePFWPaidTotal
					+ "," + totalEmpNet + "," + totalAAINet + ","
					+ totalPensionContr + "," + totalPensionCum);
			log
					.info("===============================Interest========================================================================================");
			if (OBList.size() != 0) {
				if (Integer.parseInt(calYear) >= 1995
						&& Integer.parseInt(calYear) < 2000) {
					rateOfInterest = 12;
				} else if (Integer.parseInt(calYear) >= 2000
						&& Integer.parseInt(calYear) < 2001) {
					rateOfInterest = 11;
				} else if (Integer.parseInt(calYear) >= 2001
						&& Integer.parseInt(calYear) < 2005) {
					rateOfInterest = 9.50;
				} else if (Integer.parseInt(calYear) >= 2005
						&& Integer.parseInt(calYear) < 2010) {
					rateOfInterest = 8.50;
				}else if (Integer.parseInt(calYear) >= 2010
						&& Integer.parseInt(calYear) < 2012) {
					rateOfInterest = 9.50;
				} 

				empNetInterest = new Double(df
						.format(((totalEmpNet * rateOfInterest) / 100 / interesNoMonths)))
						.doubleValue();
				aaiNetInterest = new Double(df
						.format(((totalAAINet * rateOfInterest) / 100 / interesNoMonths)))
						.doubleValue();
				// For Pension Contribution attribute,we are not cummlative
				pensionInterest = new Double(
						df
								.format(((totalPensionCum * rateOfInterest) / 100 / 12)))
						.doubleValue();
				 if(totalGrandArrearEmpNet!=0.0 && totalGrandArrearAaiNet!=0.0){
					    arrearEmpNetInterest=totalArrearEmpNet+new Double(df.format(((totalGrandArrearEmpNet*rateOfInterest)/100/12))).doubleValue();
					    arrearAaiNetInterest=totalArrearAAINet+new Double(df.format(((totalGrandArrearAaiNet*rateOfInterest)/100/12))).doubleValue();
				 }else{
					    	arrearEmpNetInterest=0.0;
					    	arrearAaiNetInterest=0.0;
				}
				 log.info("=================arrearEmpNetInterest" + arrearEmpNetInterest
							+ "totalArrearEmpNet" + totalArrearEmpNet + "arrearAaiNetInterest"
							+ arrearAaiNetInterest+"totalArrearAAINet"+totalArrearAAINet);
				totalEmpIntNet = new Double(Math.round(empNetInterest
						+ totalEmpNet)).doubleValue();
				totalAaiIntNet = new Double(Math.round(totalAAINet
						+ aaiNetInterest)).doubleValue();
				log.info("=================aaiNetInterest" + aaiNetInterest
						+ "empNetInterest" + empNetInterest + "pensionInterest"
						+ pensionInterest);
				log.info("totalNoOfMonths" + totalNoOfMonths + "empNetInterest"
						+ empNetInterest + "aaiNetInterest" + aaiNetInterest);
				closingYearList.add(new Long(Math.round(dispTotalEmpNet)));
				closingYearList.add(new Long(Math.round(dispTotalAAINet)));
				closingYearList.add(new Long(Math.round(totalPensionContr)));
				closingYearList.add(new Long(Math.round(pensionInterest)));
				log.info("PF Card==================ssss======================"+pensionNo+"totalNoOfMonths"+totalNoOfMonths);
				//If any changes on ob interest for final settlment cases
				closingOBList = this.calOBPFCard(rateOfInterest, OBList, Math
						.round(dispTotalAAINet), Math.round(aaiNetInterest),
						Math.round(dispTotalEmpNet),
						Math.round(empNetInterest), Math
								.round(totalPensionContr), Math
								.round(pensionInterest), totalAdvances,
						principle, totalNoOfMonths);
				
				insereted = this.insertOBInfo(pensionNo, con, closingOBList,
						closingYearList,finalSttmentList,rateOfInterest,totalNoOfMonths,arrearEmpNetInterest,arrearAaiNetInterest,adjInt,adjDate,totalArrearEmpNet,totalArrearAAINet);


				log.info("PF Card========================================"+pensionNo+"adjOBYear"+adjOBYear);
				String trustWiseFlag=this.chkTrustWise(pensionNo, con, adjOBYear);
				if (!trustWiseFlag.equals("X")) {
					adjInterest = this.insertTrustWiseBal(con,
							totalPensionContr, pensionInterest, pensionNo,
							region, adjOBYear, adjOBRemarks);
					totalRecordIns = totalRecordIns + adjInterest;
				} else if (chkMappingFlag.equals("true") || trustWiseFlag.equals("X")) {
					adjInterest = this.updateTrustWiseBal(con,
							totalPensionContr, pensionInterest, pensionNo,
							region, adjOBYear, adjOBRemarks);
					totalRecordIns = totalRecordIns + adjInterest;
				} else {
					adjInterest = 1;
				}
				log.info("insereted" + insereted + "adjInterest" + adjInterest);
				if (adjInterest != 0 && insereted != 0 && checkPendingFlag.equals("true")) {
					sqlGenAdjQuery = "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
							+ pensionNo;
					generatedCnt = st.executeUpdate(sqlGenAdjQuery);
					log.info("updateAdjBal=========================="
							+ sqlGenAdjQuery + generatedCnt);
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return insereted;
	}
	public int getArrearInfo(Connection con,String fromYear,String toYear,String pensionNo){
		int noOfMonths=0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sqlQuery = "", finalFromDate = "";
		log.info("::getArrearInfo="+fromYear+"toYear"+toYear+"pensionNo"+pensionNo);
		sqlQuery = "SELECT round(months_between(LAST_DAY(?),ARREARDATE)) AS MONTHS FROM EMPLOYEE_PENSION_ARREAR WHERE  (ARREARDATE BETWEEN ? AND ?) AND  PENSIONNO=?";
		log.info("FinanceReportDAO:::getArrearInfo=================================="+sqlQuery);
		try {

			pst = con.prepareStatement(sqlQuery);
			pst.setDate(1,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(toYear,"dd-MMM-yyyy","yyyy-MM-dd")));
			pst.setDate(2,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy","yyyy-MM-dd")));
			pst.setDate(3,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(toYear,"dd-MMM-yyyy","yyyy-MM-dd")));
			pst.setString(4,pensionNo);
			rs = pst.executeQuery();
			if (rs.next()) {
				noOfMonths=rs.getInt("MONTHS");
			}else{
				noOfMonths=0;
			}
			log.info("FinanceReportDAO:::getArrearInfo=================================noOfMonths"+noOfMonths);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (pst != null) {
				try {
					pst.close();
					pst = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			commonDB.closeConnection(null, null, rs);
		}
		return noOfMonths;
	}
	public ArrayList loadPFIDS(Connection con, String range, String region,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno,String selectedYear,String airportcode,String fromYear,String toYear) {
		CommonDAO commonDAO = new CommonDAO();

		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "",findFromYear="";
		EmployeePersonalInfo data = null;
		int restltmentYear=0;
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			log.printStackTrace(e2);
		}
		try {

			st = con.createStatement();
			if (region.equals("NO-SELECT")) {
				region = "";
			}

			sqlQuery = this.buildQuerygetEmployeePFInfoPrinting(range, region,
					empNameFlag, empName, sortedColumn, pensionno);

			log.info("FinanceReportDAO::getEmployeePFInfoPrinting" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();

				data = commonDAO.loadPersonalInfo(rs);

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

				if (rs.getString("DATEOFENTITLE") != null) {
					data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
				} else {
					data.setDateOfEntitle("");
				}
				if (rs.getString("FINALSETTLMENTDT") != null) {
					data.setFinalSettlementDate(commonUtil.getDatetoString(rs
							.getDate("FINALSETTLMENTDT"), "dd-MMM-yyyy"));
				} else {
					data.setFinalSettlementDate("---");
				}
				if (data.getWetherOption().trim().equals("A")) {
					data.setWhetherOptionDescr("Full Pay");
				} else if (data.getWetherOption().trim().equals("B")
						|| data.getWetherOption().trim().equals("NO")) {
					data.setWhetherOptionDescr("Celing");
				}
				if (rs.getString("ADJDATE") != null) {
					data.setAdjDate(commonUtil.getDatetoString(rs
							.getDate("ADJDATE"), "dd-MMM-yyyy"));
					log.info("GEEEEEEEEEEEE"+this.compareFinalSettlementDates(fromYear,toYear,data.getAdjDate()));
					if(this.compareFinalSettlementDates(fromYear,toYear,data.getAdjDate())==true){
						if (rs.getString("ADJAMOUNT") != null) {
							data.setAdjAmount(rs.getString("ADJAMOUNT"));
						} else {
							data.setAdjAmount("0");
						}
						if (rs.getString("ADJINT") != null) {
							data.setAdjInt(rs.getString("ADJINT"));
						} else {
							data.setAdjInt("0");
						}
						data.setAdjRemarks("Int. on Rs."+data.getAdjAmount()+" received in "+commonUtil.getDatetoString(rs
								.getDate("ADJDATE"), "MMM-yyyy"));
					}else{
						data.setAdjDate("---");
						data.setAdjAmount("0");
						data.setAdjInt("0");
					}
				} else {
					
					data.setAdjDate("---");
					data.setAdjAmount("0");
					data.setAdjInt("0");
				}
				if (rs.getString("RESETTLEMENTDATE") != null) {
					restltmentYear=Integer.parseInt(commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "yyyy"));
					log.info("Resettlement Date Issues"+commonUtil.compareTwoDates(
							commonUtil.getDatetoString(rs
									.getDate("RESETTLEMENTDATE"), "MMM-yyyy"), commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
					if(compareFinalSettlementDates(fromYear,toYear,commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"))==true){
						data.setResttlmentDate(commonUtil.getDatetoString(rs
								.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"));
					}else{
						data.setResttlmentDate("---");
					}
					
				} else {
					data.setResttlmentDate("---");
				}
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return empinfo;
	}

	public ArrayList pendingPFIDS(Connection con, String range, String region,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno,String fromYear,String toYear) {
		String pfid="",findFromYear="";
		int restltmentYear=0;
		CommonDAO commonDAO = new CommonDAO();
		//newly added below line pfid
	     if(pensionno!=""&&!pensionno.equals("")){
	    	  pfid="and pensionno='"+pensionno+"'";
	     }else{
	    	 pfid="";
	     }
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "";
		EmployeePersonalInfo data = null;
		try {
			findFromYear = commonUtil.converDBToAppFormat(fromYear,
					"dd-MMM-yyyy", "yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			log.printStackTrace(e2);
		}
		ArrayList empinfo = new ArrayList();
		if (sortedColumn.toLowerCase().equals("cpfaccno")) {
			sortedColumn = "cpfacno";
		}

		try {

			st = con.createStatement();
			if (region.equals("NO-SELECT")) {
				region = "";
			}

			sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,"
					+ "AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,"
					+ "IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,"
					+ "ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT,RESETTLEMENTDATE FROM EMPLOYEE_PERSONAL_INFO WHERE PFSETTLED='N' AND PENSIONNO IN (SELECT PENSIONNO FROM EMPLOYEE_GENRTED_PEND_ADJ_0B WHERE STATUS='N' "+pfid+") ORDER BY PENSIONNO";

			log.info("FinanceReportDAO::pendingPFIDS" + sqlQuery);
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				data = new EmployeePersonalInfo();

				data = commonDAO.loadPersonalInfo(rs);

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

				if (rs.getString("DATEOFENTITLE") != null) {
					data.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
				} else {
					data.setDateOfEntitle("");
				}
				if (rs.getString("FINALSETTLMENTDT") != null) {
					data.setFinalSettlementDate(commonUtil.getDatetoString(rs
							.getDate("FINALSETTLMENTDT"), "dd-MMM-yyyy"));
				} else {
					data.setFinalSettlementDate("---");
				}
				if (data.getWetherOption().trim().equals("A")) {
					data.setWhetherOptionDescr("Full Pay");
				} else if (data.getWetherOption().trim().equals("B")
						|| data.getWetherOption().trim().equals("NO")) {
					data.setWhetherOptionDescr("Celing");
				}
				if (rs.getString("RESETTLEMENTDATE") != null) {
					restltmentYear=Integer.parseInt(commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "yyyy"));
					log.info("Resettlement Date Issues"+commonUtil.compareTwoDates(
							commonUtil.getDatetoString(rs
									.getDate("RESETTLEMENTDATE"), "MMM-yyyy"), commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
					if(compareFinalSettlementDates(fromYear,toYear,commonUtil.getDatetoString(rs
							.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"))==true){
						data.setResttlmentDate(commonUtil.getDatetoString(rs
								.getDate("RESETTLEMENTDATE"), "dd-MMM-yyyy"));
					}else{
						data.setResttlmentDate("---");
					}
					
				} else {
					data.setResttlmentDate("---");
				}
				empinfo.add(data);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return empinfo;
	}

	private int numOfMnthFinalSettlement(String finalSettlementDate) {
		int noOfMonths = 0;
		if (finalSettlementDate.toUpperCase().equals("APR")) {
			noOfMonths = 1;
		} else if (finalSettlementDate.toUpperCase().equals("MAY")) {
			noOfMonths = 2;
		} else if (finalSettlementDate.toUpperCase().equals("JUN")) {
			noOfMonths = 3;
		} else if (finalSettlementDate.toUpperCase().equals("JUL")) {
			noOfMonths = 4;
		} else if (finalSettlementDate.toUpperCase().equals("AUG")) {
			noOfMonths = 5;
		} else if (finalSettlementDate.toUpperCase().equals("SEP")) {
			noOfMonths = 6;
		} else if (finalSettlementDate.toUpperCase().equals("OCT")) {
			noOfMonths = 7;
		} else if (finalSettlementDate.toUpperCase().equals("NOV")) {
			noOfMonths = 8;
		} else if (finalSettlementDate.toUpperCase().equals("DEC")) {
			noOfMonths = 9;
		} else if (finalSettlementDate.toUpperCase().equals("JAN")) {
			noOfMonths = 10;
		} else if (finalSettlementDate.toUpperCase().equals("FEB")) {
			noOfMonths = 11;
		} else if (finalSettlementDate.toUpperCase().equals("MAR")) {
			noOfMonths = 12;
		}

		return noOfMonths;
	}

	public ArrayList calOBForPFCardReport(double interest, ArrayList obList,
			double totalAAINet, double totalAaiIntNet, double totalEmpNet,
			double totalEmpIntNet, double totalPensionContr, double penInterest) {
		ArrayList finalOBList = new ArrayList();
		String sqlInsQuery = "", obFromTrFlag = "", obYear = "", obMnthYear = "";
		int year = 0, inserted = 0;
		Long empNetOB = null, aaiNetOB = null, pensionOB = null;
		Long empNetAdjOB = null, aaiNetAdjOB = null, pensionAdjOB = null;
		long finalEmpNetOB = 0, finalAaiNetOB = 0, finalPensionOB = 0, finalOBFromTrFlag = 0, finalOBYear = 0, obEmpNetInterest = 0, obAaiNetInterest = 0, obPenInterest = 0;
		long adjOBEmpNetInterest = 0, adjOBAaiNetInterest = 0, adjOBPenInterest = 0;
		long empNetInterest = 0, aaiNetInterest = 0, penContInterest = 0;
		log.info("interest" + interest);
		empNetOB = (Long) obList.get(0);
		aaiNetOB = (Long) obList.get(1);
		pensionOB = (Long) obList.get(2);
		obFromTrFlag = (String) obList.get(3);
		obYear = (String) obList.get(4);
		if (obList.size() > 4) {
			empNetAdjOB = (Long) obList.get(5);
			aaiNetAdjOB = (Long) obList.get(6);
			pensionAdjOB = (Long) obList.get(7);
		}

		log.info("empNetOB=======" + empNetOB + "aaiNetOB==========="
				+ aaiNetOB + "pensionOB==========" + pensionOB + "obYear"
				+ obYear);
		log.info("empNetAdjOB====" + empNetAdjOB + "aaiNetAdjOB====="
				+ aaiNetAdjOB + "pensionAdjOB====" + pensionAdjOB);

		if (!obYear.equals("")) {
			year = Integer.parseInt(obYear) + 1;
		} else {
			year = 2009;
			obYear = "2009";
		}
		if (!obYear.equals("")) {
			obMnthYear = "01-Apr-" + year;
			obEmpNetInterest = Math
					.round((empNetOB.longValue() * interest) / 100);
			obAaiNetInterest = Math
					.round((aaiNetOB.longValue() * interest) / 100);
			obPenInterest = Math
					.round((pensionOB.longValue() * interest) / 100);

			adjOBEmpNetInterest = Math
					.round((empNetAdjOB.longValue() * interest) / 100);
			adjOBAaiNetInterest = Math
					.round((aaiNetAdjOB.longValue() * interest) / 100);
			adjOBPenInterest = Math
					.round((pensionAdjOB.longValue() * interest) / 100);

			log.info("totalEmpNet" + totalEmpNet + "totalEmpIntNet"
					+ totalEmpIntNet + "obEmpNetInterest" + obEmpNetInterest);
			log.info("totalAAINet" + totalAAINet + "totalAaiIntNet"
					+ totalAaiIntNet + "obAaiNetInterest" + obAaiNetInterest);
			finalEmpNetOB = new Double(empNetOB.longValue()
					+ new Double(totalEmpNet).longValue() + obEmpNetInterest
					+ totalEmpIntNet).longValue();
			finalAaiNetOB = new Double(aaiNetOB.longValue()
					+ new Double(totalAAINet).longValue()
					+ aaiNetAdjOB.longValue() + obAaiNetInterest
					+ totalAaiIntNet + adjOBAaiNetInterest).longValue();
			// finalPensionOB=new Double(pensionOB.longValue()+new
			// Double(totalPensionContr
			// ).longValue()+obPenInterest+penInterest).longValue();
			finalPensionOB = new Double(pensionOB.longValue()
					+ new Double(totalPensionContr).longValue() + obPenInterest)
					.longValue();
			log.info("pensionOB" + pensionOB + "totalPensionContr"
					+ totalPensionContr + "obPenInterest" + obPenInterest
					+ "penInterest" + penInterest + "adjOBPenInterest"
					+ adjOBPenInterest);
			log.info("finalEmpNetOB" + finalEmpNetOB + "finalAaiNetOB"
					+ finalAaiNetOB + "finalPensionOB" + finalPensionOB);

		}
		empNetInterest = obEmpNetInterest
				+ new Double(totalEmpIntNet).longValue();
		aaiNetInterest = adjOBAaiNetInterest + obAaiNetInterest
				+ new Double(totalAaiIntNet).longValue();
		penContInterest = obAaiNetInterest
				+ new Double(penInterest).longValue();
		log.info("empNetInterest====================" + empNetInterest
				+ "aaiNetInterest=================" + aaiNetInterest);
		finalOBList.add(new Long(finalEmpNetOB));
		finalOBList.add(new Long(finalAaiNetOB));
		finalOBList.add(new Long(finalPensionOB));
		finalOBList.add(obMnthYear);
		finalOBList.add(new Long(empNetInterest));
		finalOBList.add(new Long(aaiNetInterest));
		finalOBList.add(new Long(penContInterest));

		return finalOBList;
	}
	private String chkControlAcInterestInfo(String empSerialNo, Connection con,
			String interestMonthYear) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "", obMnthYear = "", chkOBFlag = "";

		try {
			sqlInsQuery = "SELECT 'X' AS FLAG FROM EPIS_FIN_YEAR_TOTAL_INTEREST WHERE YEAR='"
					+ interestMonthYear
					+ "' AND PENSIONNO='"
					+ empSerialNo
					+ "'";
			st = con.createStatement();
			log.info("chkControlAcInterestInfo::Query" + sqlInsQuery);
			rs = st.executeQuery(sqlInsQuery);
			if (rs.next()) {
				if (rs.getString("FLAG") != null) {
					chkOBFlag = rs.getString("FLAG");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return chkOBFlag;
	}

	private int insertTrustWiseBal(Connection con, double pension,
			double pensionintrest, String pensionno, String region,
			String adjOBYear, String remarks) {
		int count = 0;
		Statement st = null;
		DecimalFormat df = new DecimalFormat("#########0");
		String sqlInsQuery = "";

		try {
			// remarks="Generated From 1995-08 Grand Total PC Report";
			sqlInsQuery = "INSERT INTO EMPLOYEE_TRUST_PC_REPORT(PENSIONTOTAL,INTEREST,REMARKS,TRUSTOBYEAR,REGION,PENSIONNO,GENERATEDATE) VALUES("
					+ df.format(pension)
					+ ","
					+ df.format(pensionintrest)
					+ ",'"
					+ remarks
					+ "','"
					+ adjOBYear
					+ "','"
					+ region
					+ "'," + pensionno + ",SYSDATE)";
			st = con.createStatement();
			log.info("insertAdjBal::Inserted Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}

	private int updateEmolumentTbl(Connection con, String pensionno) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", sqlAdjQuery = "";
		DecimalFormat df = new DecimalFormat("#########0");

		try {

			sqlInsQuery = "UPDATE EMOLUMENTS_LOG SET PROCESSDATE=CURRENT_DATE WHERE PENSIONNO='"
					+ pensionno + "'";
			st = con.createStatement();
			log.info("updateEmolumentTbl::Updated Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);

			/*
			 * if(count!=0){sqlAdjQuery=
			 * "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
			 * +pensionno; count = st.executeUpdate(sqlAdjQuery);
			 * log.info("updateAdjBal=========================="+sqlAdjQuery); }
			 */

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}

	private ArrayList getEmployeeTempPensionCard(String fromDate,
			String toDate, String pfIDS, String wetherOption, String region,
			boolean formFlag, String dateOfRetriment, String dateOfBirth,
			String pensionNo) {

		DecimalFormat df = new DecimalFormat("#########0.00");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		log.info("checkDate==" + checkDate + "flag===" + flag);
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0;

		boolean obFlag = false;
		/*
		 * sqlQuery =
		 * "SELECT MONTHYEAR,ROUND(EMOLUMENTS) AS EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,ROUND(REFADV) AS REFADV, AIRPORTCODE FROM EMPLOYEE_PENSION_VALIDATE WHERE (to_date(to_char(monthyear,'dd-Mon-yyyy'))>='"
		 * + fromDate + "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='" +
		 * toDate + "')" + " AND (" + pfIDS + ") ORDER BY TO_DATE(monthyear)";
		 */

		sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,emp.* from (select distinct to_char(to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
				+ "from employee_pension_validate where rownum <=to_date('"
				+ toDate
				+ "', 'dd-mon-yyyy') -to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(PF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
				+ toDate
				+ "'))"
				+ " AND ("
				+ pfIDS
				+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));
					} else {
						cardInfo.setMonthyear("---");
					}
					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM-yy"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy",
								"MMM-yy"));
					}

				}
				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					cardInfo.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));
				} else {
					cardInfo.setPrincipal("0");
				}

				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					/*
					 * if (region.equals("CHQIAD")) {
					 * 
					 * loanPFWPaid = this.getEmployeeLoans(con, cardInfo
					 * .getShnMnthYear(), pfIDS, "ADV.PAID"); advancePFWPaid =
					 * this.getEmployeeAdvances(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID");
					 * log.info("Region"+region+"loanPFWPaid"
					 * +loanPFWPaid+"advancePFWPaid"+advancePFWPaid);
					 * totalAdvancePFWPaid=loanPFWPaid+advancePFWPaid; } else if
					 * (region.equals("CHQNAD")) { loanPFWPaid =
					 * this.getEmployeeLoans(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID"); advancePFWPaid =
					 * this.getEmployeeAdvances(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.PAID");
					 * totalAdvancePFWPaid=loanPFWPaid+advancePFWPaid; } else if
					 * (region.equals("North Region")) {
					 * if(rs.getString("DEDADV")!=null){ advancePFWPaid =
					 * Double.parseDouble(rs.getString("DEDADV"));
					 * totalAdvancePFWPaid=advancePFWPaid; }else{
					 * advancePFWPaid=0; totalAdvancePFWPaid=advancePFWPaid; }
					 * 
					 * }
					 */

					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("Region" + region + "loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				}

				/*
				 * log.info("cardInfo.getShnMnthYear()" +
				 * cardInfo.getShnMnthYear() + "advancePFWPaid" +
				 * advancePFWPaid);
				 */
				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				log.info("monthYear" + cardInfo.getMonthyear()
						+ "dateOfRetriment" + dateOfRetriment);
				if (flag == false) {
					if (!cardInfo.getMonthyear().equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(cardInfo.getMonthyear());
						empRetriedDt = Date.parse(dateOfRetriment);

						if (transMntYear > empRetriedDt) {
							contrFlag = true;
						} else if (transMntYear == empRetriedDt) {
							chkDOBFlag = true;
						}
					}
					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					if (contrFlag != true) {
						pensionAsPerOption = this.pensionCalculation(cardInfo
								.getMonthyear(), cardInfo.getEmoluments(),
								wetherOption, region,rs.getString("emolumentmonths"));
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				} else {
					cardInfo.setPensionContribution("0");
				}
				log.info("flag" + flag + checkDate + "Pension"
						+ cardInfo.getPensionContribution());
				if (formFlag == true) {
					/*
					 * if (region.equals("CHQIAD")) { advPFDrawn =
					 * this.getEmployeeLoans(con, cardInfo .getShnMnthYear(),
					 * pfIDS, "ADV.DRAWN"); } else if (region.equals("CHQNAD"))
					 * { advPFDrawn = this.getEmployeeLoans(con, cardInfo
					 * .getShnMnthYear(), pfIDS, "ADV.DRAWN"); } else if
					 * (region.equals("North Region")) { advPFDrawn = 0; }
					 */
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return pensionList;
	}

	public ArrayList updateMonthlyCpfRecoverieData(String pfid,
			String monthYear, String emoluments, String cpf, String vpf,
			String principle, String interest, String wetherOption) {
		log.info("FinancialReportDAO::updateMonthlyCpfRecoverieData");
		Connection con = null;
		Statement st = null;
		double pensioncontri = 0.00, pf = 0.00;
		String foundSalaryDate = "", updateMonthlyCpfRecoveries = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			pensioncontri = this.pensionCalculation("01-" + monthYear,
					emoluments, wetherOption, "","1");
			log.info("pensioncontri... " + pensioncontri);

			pf = Math.round(Double.parseDouble(cpf.toString()) - pensioncontri);
			updateMonthlyCpfRecoveries = "update employee_pension_validate_temp set pensionno="
					+ pfid
					+ ",emoluments='"
					+ emoluments
					+ "',EMPPFSTATUARY='"
					+ cpf
					+ "',EMPVPF='"
					+ vpf
					+ "',EMPADVRECPRINCIPAL='"
					+ principle
					+ "',EMPADVRECINTEREST='"
					+ interest
					+ "',wetheroption='"
					+ wetherOption
					+ "',MONTHYEAR='01-'+'"
					+ monthYear
					+ "',PF='"
					+ pf
					+ "',PENSIONCONTRI='"
					+ pensioncontri
					+ "' where pensionno='"
					+ pfid
					+ "' and monthyear like '%" + monthYear + "')";
			ImportDao IDAO = new ImportDao();
			foundSalaryDate = IDAO.checkFinanceDuplicate1(con, "01-"
					+ monthYear, "", "", "", pfid, "", "temp");
			if (foundSalaryDate != "") {
				st.executeUpdate(updateMonthlyCpfRecoveries);
			} else {
				updateMonthlyCpfRecoveries = "insert into employee_pension_validate_temp(pensionno,emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,wetheroption,MONTHYEAR,PF,PENSIONCONTRI) values("
						+ pfid
						+ ","
						+ emoluments
						+ ","
						+ cpf
						+ ",'"
						+ vpf
						+ "','"
						+ principle
						+ "','"
						+ interest
						+ "','"
						+ wetherOption
						+ "','01-'+'"
						+ monthYear
						+ "','"
						+ pf
						+ "','" + pensioncontri + "')";
				st.executeUpdate(updateMonthlyCpfRecoveries);
			}
		} catch (Exception e) {
			if (st != null) {
				try {
					st.close();
					st = null;
					con.close();
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
		}
		return null;
	}

	public String buildPenContRptWthOutTransferQuery(String region,
			String airportCD, String empserialNO, String cpfAccno) {
		log
				.info("FinancialReportDAO :buildPenContRptWthOutTransferQuery() entering method");

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String dynamicQuery = "";
		sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS PENSIONNUMBER,EPI.REGION AS REGION,EPI.DEPARTMENT AS DEPARTMENT,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI WHERE EPI.PENSIONNO IS NOT NULL ";

		if (!airportCD.equals("NO-SELECT")) {
			whereClause.append(" LOWER(EPI.AIRPORTCODE) like'%"
					+ airportCD.trim().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!region.equals("NO-SELECT")) {
			whereClause.append(" LOWER(EPI.region)='"
					+ region.trim().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!empserialNO.equals("")) {

			whereClause.append(" EPI.PENSIONNO=" + empserialNO.trim() + "");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if ((region.equals("NO-SELECT")) && (airportCD.equals("NO-SELECT"))
				&& (empserialNO.equals(""))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "ORDER BY EPI.PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("FinancialReportDAO :buildPenContRptWthOutTransferQuery() leaving method");
		return dynamicQuery;
	}

	public double calclatedPF(String monthYear, String dateOfRetriment,
			String dateOfBirth, String calEmoluments, String wetherOption,
			String region, String days1,String emolumentMonths) {
		long transMntYear = 0, empRetriedDt = 0;
		boolean flag = true, contrFlag = false, chkDOBFlag = false;
		double pensionVal = 0.00;
		String days = "", getMonth = "";
		int getDaysBymonth = 0;
		DecimalFormat df = new DecimalFormat("#########0.00");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;
		if (flag == true) {
			if (!monthYear.equals("-NA-") && !dateOfRetriment.equals("")) {
				transMntYear = Date.parse(monthYear);
				empRetriedDt = Date.parse(dateOfRetriment);

				log.info("days " + days1);

				if (transMntYear> empRetriedDt) {
					contrFlag = true;
				} else if (transMntYear == 0 || empRetriedDt == 0) {
					contrFlag = false;
				}
				if (transMntYear != 0 && empRetriedDt != 0
						&& Integer.parseInt(days1) > 0 &&Integer.parseInt(days1) <= 31 ) {
					chkDOBFlag = true;
				}
			}
			// log.info("transMntYear"+transMntYear+"empRetriedDt"+
			// empRetriedDt);
			// log.info("contrFlag"+contrFlag+"chkDOBFlag"+chkDOBFlag);
			if (contrFlag != true) {
				pensionVal = this.pensionFormsCalculation(monthYear,
						calEmoluments, wetherOption.trim(), region, false,
						false,emolumentMonths);

			}
			if (chkDOBFlag == true) {
				String[] dobList = dateOfBirth.split("-");
				days = dobList[0];
				getDaysBymonth = this.getNoOfDays(dateOfBirth);
				pensionVal = pensionVal * (Double.parseDouble(days) - 1)
						/ getDaysBymonth;
				retriredEmoluments = new Double(df1.format(Double
						.parseDouble(calEmoluments)
						* (Double.parseDouble(days)-1 ) / 30))
						.doubleValue();
				calEmoluments = Double.toString(retriredEmoluments);
				pensionVal = this.pensionFormsCalculation(monthYear,
						calEmoluments, wetherOption.trim(), region, false,
						false,emolumentMonths);
			}

		} else {
			pensionVal = 0;
		}
		return pensionVal;
	}
	public double calclatedPF1(String monthYear, String dateOfRetriment,
			String dateOfBirth, String calEmoluments, String wetherOption,
			String region, String days1,String emolumentMonths,String emppfstatuary) {
		long transMntYear = 0, empRetriedDt = 0;
		boolean flag = true, contrFlag = false, chkDOBFlag = false;
		double pensionVal = 0.00;
		String days = "", getMonth = "";
		int getDaysBymonth = 0;
		DecimalFormat df = new DecimalFormat("#########0.00");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;
		if (flag == true) {
			if (!monthYear.equals("-NA-") && !dateOfRetriment.equals("")) {
				transMntYear = Date.parse(monthYear);
				empRetriedDt = Date.parse(dateOfRetriment);

				log.info("days " + days1 +"empRetriedDt "+empRetriedDt +"transMntYear "+transMntYear);

				if (transMntYear>=empRetriedDt) {
					contrFlag = true;
				} else if (transMntYear == 0 || empRetriedDt == 0) {
					contrFlag = false;
				}
				log.info("contrFlag "+contrFlag);
				if (transMntYear != 0 && empRetriedDt != 0
						&& (Integer.parseInt(days1) >= 0 &&Integer.parseInt(days1) <=1)||(Integer.parseInt(days1) < 0 &&Integer.parseInt(days1) >-29)) {
					chkDOBFlag = true;
				}
			}
			// log.info("transMntYear"+transMntYear+"empRetriedDt"+
			// empRetriedDt);
			// log.info("contrFlag"+contrFlag+"chkDOBFlag"+chkDOBFlag);
			if (contrFlag != true) {
				pensionVal = this.pensionFormsCalculation(monthYear,
						calEmoluments, wetherOption.trim(), region, false,
						false,emolumentMonths);

			}
			if (chkDOBFlag == true) {
				String[] dobList = dateOfBirth.split("-");
				days = dobList[0];
				getDaysBymonth = this.getNoOfDays(dateOfBirth);
				pensionVal = pensionVal * (Double.parseDouble(days) - 1)
						/ getDaysBymonth;
				retriredEmoluments = new Double(df1.format(Double
						.parseDouble(calEmoluments)
						* (Double.parseDouble(days)-1 ) / getDaysBymonth))
						.doubleValue();
				log.info("retriredEmoluments" +retriredEmoluments);
				calEmoluments = Double.toString(retriredEmoluments);
				pensionVal = this.pensionFormsCalculation(monthYear,
						calEmoluments, wetherOption.trim(), region, false,
						false,emolumentMonths );
			}

		} else {
			pensionVal = 0;
		}
		return pensionVal;
	}
	private int updateTransTblPC(String cpfaccno, Connection con,
			String region, String monthyear, double pensionval, double pf,String pensionno) {
		Statement st = null;
		String sqlInsQuery = "";
		int inserted = 0;
		log.info("updateTransTblPC::cpfaccno" + cpfaccno + "region" + region
				+ "monthyear" + monthyear);
		try {

			sqlInsQuery = "UPDATE employee_pension_validate SET pensioncontri=round("
					+ pensionval
					+ "),PF=round('"
					+ pf
					+ "') WHERE CPFACCNO='"
					+ cpfaccno
					+ "' AND REGION='"
					+ region
					+ "' AND MONTHYEAR='" + monthyear + "' AND EMPFLAG='Y' AND PENSIONNO="+pensionno;
			st = con.createStatement();
			log.info("insertOBInfo::Inserted Query" + sqlInsQuery);
			inserted = st.executeUpdate(sqlInsQuery);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return inserted;
	}
	public String buildQuerygetEmployeePFInfoPrinting(String range,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno,String toYear) {
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT FROM EMPLOYEE_PERSONAL_INFO ";
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
		if (!toYear.equals("")) {
			whereClause.append(" DATEOFJOINING <='" + toYear + "'");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if (region.equals("")
				&& (range.equals("NO-SELECT") && toYear.equals("") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting Leaving Method");
		return dynamicQuery;
	}
	
	public void	pentionContributionProcess(String region,String userName,String ipAddress,String pensionno){

		log.info("ImportDao :pentionContributionProcess() entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "",dateofbirth="";
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String foundSalaryDate = "",retirementDate="";
		boolean addflag = false;
		boolean updateflag = false;
		try {
			
			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
				double calculatedPension=0.00, pf=0.00;
				bean = new EmpMasterBean();
				bean.setRegion(region);
				
				if (!pensionno.equals("")) {
					bean.setPfid(commonUtil.getSearchPFID1(pensionno.trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

					String transMonthYear="";
						 
				if (!bean.getPfid().trim().equals("")) {
				   String calPensionDate="select to_char(add_months(dateofbirth, 697),'dd-Mon-yyyy')AS  calPensiondays from employee_personal_info where pensionno='"+ bean.getPfid() +" '";
					     rs=st1.executeQuery(calPensionDate);
					     if(rs.next()){
					        if(rs.getString("calPensiondays")!=null){
					    	 monthYear=	rs.getString("calPensiondays"); 
					     }
					     }
				   String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date(to_char(add_months(TO_DATE('"+monthYear+"'), -1),'dd-Mon-yyyy'),'DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days,to_char(add_months(dateofbirth, 697),'dd-Mon-yyyy')AS calPensionupto from employee_personal_info where pensionno='"+ bean.getPfid() +"'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee "+bean.getEmpName()+". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if(rs.getString("REIREMENTDATE")!=null){
						retirementDate=rs.getString("REIREMENTDATE");
					}else{
						retirementDate="";
					}
					if(rs.getString("dateofbirth")!=null){
						dateofbirth=rs.getString("dateofbirth");
					}else{
						dateofbirth="";
					}
					String days="0";
					if(rs.getString("days")!=null){
						days=rs.getString("days");
					}else{
						days="0";
					}
					String calPensionupto="";
					if(rs.getString("calPensionupto")!=null){
						calPensionupto=rs.getString("calPensionupto");
					}else{
						calPensionupto="0";
					}
				 transMonthYear = commonUtil.converDBToAppFormat(calPensionupto.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				  String emolumentsQuery="select emoluments from employee_pension_validate where pensionno='"+bean.getPfid()+"' and to_char(monthyear,'dd-Mon-yyyy') like '%"+ transMonthYear + "'";
					log.info(emolumentsQuery);
					rs2=st1.executeQuery(emolumentsQuery);
					 if (rs2.next()) {
						 if(rs2.getString("emoluments")!=null){
						bean.setEmoluments(rs2.getString("emoluments")); 
						 }
					 }else{
						 bean.setEmoluments("0.00"); 
					 }
					 Date transdate = null;
						DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						transdate = df.parse(monthYear);
							float cpfarrear = 0;
							if (transdate.after(new Date("31-Mar-1998"))) {
								bean.setEmployeePF(String.valueOf(Float.parseFloat(bean
										.getEmoluments()) * 12 / 100));
							} else {
								bean.setEmployeePF(String.valueOf(Float.parseFloat(bean
										.getEmoluments()) * 10 / 100));
							}
									
					 FinancialReportDAO fdao=new FinancialReportDAO();
					 calculatedPension=fdao.calclatedPF(monthYear,retirementDate, dateofbirth ,bean.getEmoluments(), bean.getWetherOption() , "",days,"1");
					 calculatedPension=Math.round(calculatedPension);
					 pf=Double.parseDouble(bean.getEmployeePF().toString())- calculatedPension;
					 pf=Math.round(pf);
		             
	 		}
				//this code snippet is removed from if condition  foundSalaryDate.equals("") && 
			  if (!bean.getPfid().equals("") ) {
					String insertPensionCon = "",updatePensionCon="";
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertPensionCon = "update employee_pension_validate set pf='"+pf+"',PENSIONCONTRI='"+calculatedPension+"'  where pensionno='"+bean.getPfid()+"' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' ";
					
					
					updatePensionCon="update employee_pension_validate set PF=EMPPFSTATUARY,PENSIONCONTRI='0.00' where pensionno='"+bean.getPfid()+"' and monthyear>=add_months(to_date('"+monthYear+"','dd-Mon-yyyy'),1) ";
					log.info(insertPensionCon);
					log.info(updatePensionCon);
					st.executeUpdate(insertPensionCon);
					st.executeUpdate(updatePensionCon);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		
		} finally {
			try {
				pst.close();
				rs.close();
				rs2.close();
				st1.close();
				st.close();
				conn.close();
			} catch (Exception e) {

			}
		}

			
	}
	public String buildQuerygetEmployeePFInfoPrinting(String range,
			String region, String empNameFlag, String empName,
			String sortedColumn, String pensionno,String toYear,String fromYear) {
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING," +
		"DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS," +
		"WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username," +
		"LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE," +
		"to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT,(CASE  WHEN (dateofbirth<last_day('"+toYear+"')) THEN  round(months_between(last_day('"+toYear+"'),dateofbirth)/12) ELSE  round(months_between(dateofbirth, last_day('"+toYear+"')) / 12) END)  as	EMPAGE1, " +
		"(CASE WHEN (DATEOFSEPERATION_date < = '"+toYear+"' AND DATEOFSEPERATION_date >='"+fromYear+"') THEN 'Y' ELSE 'N' END) as SEPER_FLAG,to_char((dateofbirth + INTERVAL '58' year ),'dd-Mon-yyyy')  as LASTATTAINED,'01-Jan-1900' as ARREARDATE,round(months_between(NVL(DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,'' as ARREARTODATE,'' as ARREARFORMDATE,'N' AS CHKWTHRARRNOT FROM EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromYear+"',last_day(dateofbirth))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND ADD_MONTHS(DATEOFSEPERATION_DATE,1)>=TO_DATE('"+fromYear+"'))) ";


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
		if (!toYear.equals("")) {
			whereClause.append(" DATEOFJOINING <=LAST_DAY('" + toYear + "')");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if (region.equals("")
				&& (range.equals("NO-SELECT") && toYear.equals("") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log
				.info("FinanceReportDAO::buildQuerygetEmployeePFInfoPrinting Leaving Method");
		return dynamicQuery;
	}

	
	public void ProcessforAdjOb(String pfid,boolean pfcardflag){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String weatherOption="",retirementDate="",dateofbirth="",region="",cpfacno="",airportcode="";
		try{
			con = commonDB.getConnection();
			st = con.createStatement();
		  if (!pfid.equals("")) {
			// if(!tempInfo[1].equals("XXX")){
			String checkPFID = "select wetheroption,pensionno,region,cpfacno,airportcode,to_char(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth from employee_personal_info where to_char(pensionno)='"
					+ pfid + "'";
			
			log.info(checkPFID);
			rs = st.executeQuery(checkPFID);

			if(rs.next()){
			if (rs.getString("wetheroption") != null) {
				weatherOption=rs.getString("wetheroption").toString();
			} else {
				weatherOption="";
			}
			if(rs.getString("REIREMENTDATE")!=null){
				retirementDate=rs.getString("REIREMENTDATE");
			}else{
				retirementDate="";
			}
			if(rs.getString("dateofbirth")!=null){
				dateofbirth=rs.getString("dateofbirth");
			}else{
				dateofbirth="";
			}
			if(rs.getString("region")!=null){
				region=rs.getString("region");
			}else{
				region="";
			}
			if(rs.getString("cpfacno")!=null){
				cpfacno=rs.getString("cpfacno");
			}else{
				cpfacno="";
			}
			if(rs.getString("airportcode")!=null){
				airportcode=rs.getString("airportcode");
			}else{
				airportcode="";
			}
			}
					
		}
		String queryforProcess="insert into EMPLOYEE_GENRTED_PEND_ADJ_0B(PENSIONNO,cpfaccno,EMPLOYEENAME,AIRPORTCODE,region,REQGENDATE,STATUS,dateofbirth,wetheroption,dateofjoining,remarks,ADJSTATUS,PFCRDSTATUS) select pensionno,cpfacno,employeename,airportcode,region,TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS'),'N' ,dateofbirth,wetheroption,dateofjoining,'chennaiiad-processing-19-Apr-2010','N','N' from employee_personal_info where pensionno='"+pfid+"'";
		String pfidWithRegion = this.getEmployeeMappingPFInfo(con,pfid, "", "");
		st.executeUpdate(queryforProcess);
		String[] pfIDLists = pfidWithRegion.split("=");
		String pfIDString=this.preparedCPFString1(pfIDLists);
		//this.updateEmpPC200809("1-Apr-1995", "1-May-2011", pfIDString,weatherOption,region,true,retirementDate,dateofbirth,pfid);
		
		this.updatePCReport("1-Apr-1995", "1-May-2008",region,airportcode,pfid,cpfacno,"","1 - 1","true");
		
		ImportDao iDao=new ImportDao();
		
		
		iDao.pentionContributionProcess2008to11(region,pfid);
		
		
		
		//this.updatePFCardClosingBal(con, "1-Apr-2008", "1-May-2011", pfIDString,weatherOption, region,true,retirementDate, dateofbirth,	pfid,0,"---","false");
		//this.pentionContributionProcess(region,"","",pfid);
		FinancialReportService fs=new FinancialReportService();
		String years[]={"2010","2011"};
		FileWriter fw = null;
        ResourceBundle bundle = ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
      String  uploadFilePath = bundle.getString("upload.folder.path");
        File filePath = new File(uploadFilePath);
        if (!filePath.exists()) {
     	   	filePath.mkdirs();
        }
        String  fileName="//PCOB-"+region+".txt";
     	 fw = new FileWriter(new File(filePath + fileName));
		for(int i=0;i<years.length;i++){
			st.executeUpdate(queryforProcess);
			log.info("in loop "+years[i] );
			this.updatePFCardReport("NO-SELECT", "NO-SELECT",years[i], "true","xyz","", pfid, fw,"false",false,"");
	//	int j=fs.updateOBCardReport("NO-SELECT",region,years[i], "true", "","", pfid, "true");
		
		}
	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(con, st, rs);

	}
}
	public ArrayList getForm10DPensionInfo(Connection con,String fromDate, String toDate,
			String pfIDS, String wetherOption,String dateOfRetriment, String dateOfBirth, String pensionNo,boolean formFlag) {

		DecimalFormat df = new DecimalFormat("#########0.00");
		DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
		
		Statement st = null;
		ResultSet rs = null;
		EmployeePensionCardInfo cardInfo = null;
		ArrayList pensionList = new ArrayList();
		boolean flag = false;
		boolean contrFlag = false, chkDOBFlag = false;
		String checkDate = "", chkMnthYear = "Apr-1995", formatMnth = "", chkDecMnthYear = "", findFromYear = "", findToYear = "";
		String monthYear = "", days = "", getMonth = "", sqlQuery = "", calEmoluments = "",emolumentsMonths="1";
		int getDaysBymonth = 0;
		long transMntYear = 0, empRetriedDt = 0;
		
		double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
		double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;

		boolean obFlag = false,yearBreakMonthFlag=false;

		try {
			findFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MMM-yyyy", "yyyy");
			findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
					"yyyy");
		} catch (InvalidDataException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (Integer.parseInt(findFromYear) >= 2008) {
			yearBreakMonthFlag = true;
			pfIDS="";
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
				+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
				+ "from employee_pension_validate where rownum <=to_date('"
				+ toDate
				+ "', 'dd-mon-yyyy') -to_date('"
				+ fromDate
				+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
				+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
				+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,PENSIONCONTRI,PF,nvl(emolumentmonths,'1') as emolumentmonths FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
				+ fromDate
				+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
				+ toDate
				+ "'))"
				+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
		
		} else {
			yearBreakMonthFlag = false;
			sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
					+ "from employee_pension_validate where rownum <=to_date('"
					+ toDate
					+ "', 'dd-mon-yyyy') -to_date('"
					+ fromDate
					+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,ROUND(EMOLUMENTS) AS EMOLUMENTS,"
					+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,arrearflag as arrearflag,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,PENSIONCONTRI,PF,emolumentmonths FROM EMPLOYEE_PENSION_VALIDATE  WHERE empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
					+ fromDate
					+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
					+ toDate
					+ "'))"
					+ " AND ("
					+ pfIDS
					+ ")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
		}
			
		

		log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
		ArrayList OBList = new ArrayList();
		try {
		
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {
				cardInfo = new EmployeePensionCardInfo();
				double total = 0.0;
				formatMnth = rs.getString("FORMATMONTHYEAR");
				if (rs.getString("MONTHYEAR") != null) {
					cardInfo.setMonthyear(commonUtil.getDatetoString(rs
							.getDate("MONTHYEAR"), "dd-MMM-yyyy"));

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(formatMnth
							+ "/"
							+ commonUtil.converDBToAppFormat(cardInfo
									.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
				} else {
					if (rs.getString("EMPMNTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));

					} else {
						cardInfo.setMonthyear("---");
					}

					getMonth = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM");
					chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
					cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
							cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM"));

					if (getMonth.toUpperCase().equals("APR")) {
						obFlag = false;
						getMonth = "";
						empCumlative = 0.0;
						aaiNetCumlative = 0.0;
						advancePFWPaid = 0.0;
						advPFDrawn = 0.0;
						totalAdvancePFWPaid = 0.0;
					}
					if (getMonth.toUpperCase().equals("MAR")) {
						cardInfo.setCbFlag("Y");
					} else {
						cardInfo.setCbFlag("N");
					}
					if (!cardInfo.getMonthyear().equals("---")) {
						cardInfo.setShnMnthYear(formatMnth
								+ "/"
								+ commonUtil.converDBToAppFormat(cardInfo
										.getMonthyear(), "dd-MMM-yyyy", "MMM"));
					}

				}
/*				if (obFlag == false) {
					OBList = this.getOBForPFCardReport(con, cardInfo
							.getMonthyear(), pensionNo);
					cardInfo.setObList(OBList);
					cardInfo.setObFlag("Y");
					obFlag = true;
					getMonth = "";
				} else {
					cardInfo.setObFlag("N");
				}*/

				if (rs.getString("EMOLUMENTS") != null) {
					cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					cardInfo.setEmoluments("0");
				}

				calEmoluments = this.calWages(cardInfo.getEmoluments(),
						cardInfo.getMonthyear(), wetherOption.trim(), false,
						false,"1");
				if (chkDecMnthYear.trim().equals("Dec-95")) {
					if (!calEmoluments.equals("")) {
						calEmoluments = Double.toString(Double
								.parseDouble(calEmoluments) / 2);
					}

				}
				log.info("monthYear======================"+ cardInfo.getShnMnthYear() + "calEmoluments"+ calEmoluments);
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0");
				}
				if (rs.getString("CPF") != null) {
					cardInfo.setEmpCPF(rs.getString("CPF"));
				} else {
					cardInfo.setEmpCPF("0");
				}

				/*
				 * if (region.equals("CHQNAD")) {
				 * 
				 * if (rs.getString("CPFADVANCE") != null) {
				 * cardInfo.setPrincipal(rs.getString("CPFADVANCE")); } else {
				 * cardInfo.setPrincipal("0"); } } else
				 */
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
							cardInfo.setPrincipal(rs
									.getString("EMPADVRECPRINCIPAL"));
				} else {
							cardInfo.setPrincipal("0");
				}
					
		
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0");
				}
				try {
					checkDate = commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
					flag = false;
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
				if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
					flag = true;
				}
				total = new Double(df.format(Double.parseDouble(cardInfo
						.getEmppfstatury().trim())
						+ Double.parseDouble(cardInfo.getEmpvpf().trim())
						+ Double.parseDouble(cardInfo.getPrincipal().trim())
						+ Double.parseDouble(cardInfo.getInterest().trim())))
						.doubleValue();
				if (formFlag == true) {
					loanPFWPaid = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					advancePFWPaid = this.getEmployeeAdvances(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.PAID", pensionNo);
					log.info("loanPFWPaid" + loanPFWPaid
							+ "advancePFWPaid" + advancePFWPaid);
					totalAdvancePFWPaid = loanPFWPaid + advancePFWPaid;
				}

				if (rs.getString("emolumentmonths") != null) {
					emolumentsMonths=rs.getString("emolumentmonths");
				}
				empNet = total - totalAdvancePFWPaid;

				cardInfo.setEmptotal(Double.toString(Math.round(total)));
				cardInfo.setAdvancePFWPaid(Double.toString(Math
						.round(totalAdvancePFWPaid)));
				cardInfo.setEmpNet((Double.toString(Math.round(empNet))));
				empCumlative = empCumlative + empNet;
				cardInfo.setEmpNetCummulative(Double.toString(empCumlative));
				if (rs.getString("AAICONPF") != null) {
					cardInfo.setAaiPF(rs.getString("AAICONPF"));
				} else {
					cardInfo.setAaiPF("0");
				}

				log.info("monthYear" + cardInfo.getMonthyear()
						+ "dateOfRetriment" + dateOfRetriment);
				if (yearBreakMonthFlag == false) {
					if (flag == false) {
						if (!cardInfo.getMonthyear().equals("-NA-")
								&& !dateOfRetriment.equals("")) {
							transMntYear = Date.parse(cardInfo.getMonthyear());
							empRetriedDt = Date.parse(dateOfRetriment);

							if (transMntYear > empRetriedDt) {
								contrFlag = true;
							} else if (transMntYear == empRetriedDt) {
								chkDOBFlag = true;
							}
						}
						log.info("transMntYear" + transMntYear + "empRetriedDt"
								+ empRetriedDt);
						log.info("contrFlag" + contrFlag + "chkDOBFlag"
								+ chkDOBFlag);
						if (contrFlag != true) {
							pensionAsPerOption = this.pensionCalculation(
									cardInfo.getMonthyear(), cardInfo
											.getEmoluments(), wetherOption,
									"",emolumentsMonths);
							if (chkDOBFlag == true) {
								String[] dobList = dateOfBirth.split("-");
								days = dobList[0];
								getDaysBymonth = this.getNoOfDays(dateOfBirth);
								pensionAsPerOption = Math
										.round(pensionAsPerOption
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth);

							}

						} else {
							pensionAsPerOption = 0;
						}
						cardInfo.setPensionContribution(Double
								.toString(pensionAsPerOption));
					} else {
						cardInfo.setPensionContribution("0");
					}
				} else {
					pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
					cardInfo.setPensionContribution(Double
							.toString(pensionAsPerOption));
				}
		
				log.info(cardInfo.getMonthyear() + "flag" + flag + checkDate
						+ "Pension" + cardInfo.getPensionContribution()
						+ "calEmoluments" + calEmoluments);

				cardInfo.setEmoluments(calEmoluments);
				if (formFlag == true) {
					advPFDrawn = this.getEmployeeLoans(con, cardInfo
							.getShnMnthYear(), pfIDS, "ADV.DRAWN", pensionNo);
				}

				aaiPF = (Double.parseDouble(cardInfo.getEmppfstatury()) - pensionAsPerOption);
				cardInfo.setAaiPF(Double.toString(aaiPF));
				cardInfo.setPfDrawn(Double.toString(advPFDrawn));
				aaiNet = Double.parseDouble(cardInfo.getAaiPF()) - advPFDrawn;
				aaiNetCumlative = aaiNetCumlative + aaiNet;
				cardInfo.setAaiNet(Double.toString(aaiNet));
				cardInfo.setAaiCummulative(Double.toString(aaiNetCumlative));
				if (rs.getString("AIRPORTCODE") != null) {
					cardInfo.setStation(rs.getString("AIRPORTCODE"));
				} else {
					cardInfo.setStation("---");
				}
				cardInfo.setEmoluments(calEmoluments);
				pensionList.add(cardInfo);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return pensionList;
	}
	public ArrayList getCpfData(String pfid,String year,String month,String region){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;	
		String fromYear="",toYear="";
		String  airportcode="NO-SELECT",pfIDStrip="1 - 1";
      if(!year.equals("")){
			String[] dateArr=year.split(",");
			fromYear=dateArr[0];
			toYear=dateArr[1];
		}	
      
      //********

      ArrayList penContHeaderList = new ArrayList();
     
      penContHeaderList = this
      			.pensionContrPFIDHeaderInfo(region, airportcode,
      					pfid, "", "", pfIDStrip);
      String pensionList = "";
      String preparedString="",dateOfRetriment="",sqlQuery="";
      for (int i = 0; i < penContHeaderList.size(); i++) {
			PensionContBean penContHeaderBean = new PensionContBean();
			PensionContBean penContBean = new PensionContBean();
        
			penContHeaderBean = (PensionContBean) penContHeaderList.get(i);
			  preparedString = penContHeaderBean.getPrepareString();
			  try{
			  if (!penContBean.getEmpDOB().trim().equals("---")
						&& !penContBean.getEmpDOB().trim().equals("")) {
					dateOfRetriment = this.getRetriedDate(penContBean.getEmpDOB());
				}
			  }catch(Exception e){
				  log.info(e.getMessage());
			  }
		/*	 pensionList = this.getEmployeePensionInfo(preparedString,
		    		  fromYear, toYear, penContHeaderBean
		    	  								.getWhetherOption(), dateOfRetriment,
		    	  						penContBean.getEmpDOB());*/
			  DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			  Date	transdate=null;
		    	try {
						transdate = df.parse(fromYear);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(transdate.before(new Date("31-Mar-2008"))){
			  String tempCpfString = preparedString.replaceAll("CPFACCNO", "cpfacno");
				
			  String dojQuery = "(select nvl(to_char (dateofjoining,'dd-Mon-yyyy'),'1-Apr-1995') from employee_info where ("
						+ tempCpfString + ") and rownum=1)";
   
				 sqlQuery = " select mo.* from (select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)) AS EMPMNTHYEAR,emp.MONTHYEAR AS MONTHYEAR,emp.EMOLUMENTS AS EMOLUMENTS,emp.EMPPFSTATUARY AS EMPPFSTATUARY,emp.EMPVPF AS EMPVPF,emp.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST AS EMPADVRECINTEREST,emp.AIRPORTCODE AS AIRPORTCODE,emp.cpfaccno AS CPFACCNO,emp.region as region ,'Duplicate' DUPFlag,emp.PENSIONCONTRI as PENSIONCONTRI,emp.DATAFREEZEFLAG as DATAFREEZEFLAG,emp.form7narration as form7narration,emp.pensionno as pensionno from "
						+ "(select distinct to_char(to_date('"
						+ fromYear
						+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' and    rownum "
						+ "<= to_date('"
						+ toYear
						+ "','dd-mon-yyyy')-to_date('"
						+ fromYear
						+ "','dd-mon-yyyy')+1) empdt ,(SELECT cpfaccno,to_char(MONTHYEAR,'MONYYYY') empmonyear,TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR,ROUND(EMOLUMENTS,2) AS EMOLUMENTS,ROUND(EMPPFSTATUARY,2) AS EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,AIRPORTCODE,REGION,EMPFLAG,PENSIONCONTRI,DATAFREEZEFLAG,form7narration,pensionno FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and ("
						+ preparedString
						+ ") AND MONTHYEAR>= TO_DATE('"
						+ fromYear
						+ "','DD-MON-YYYY') and empflag='Y' ORDER BY TO_DATE(MONTHYEAR, 'dd-Mon-yy')) emp  where empdt.monyear = emp.empmonyear(+)   and empdt.monyear in (select to_char(MONTHYEAR,'MONYYYY')monyear FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and  ("
						+ preparedString
						+ ") and  MONTHYEAR >= TO_DATE('"
						+ fromYear
						+ "', 'DD-MON-YYYY')  group by  to_char(MONTHYEAR,'MONYYYY')  having count(*)>1)"
						+ " union	 (select TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||  SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR, emp.MONTHYEAR AS MONTHYEAR,"
						+ " emp.EMOLUMENTS AS EMOLUMENTS,emp.EMPPFSTATUARY AS EMPPFSTATUARY,emp.EMPVPF AS EMPVPF,emp.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,"
						+ "emp.EMPADVRECINTEREST AS EMPADVRECINTEREST,emp.AIRPORTCODE AS AIRPORTCODE,emp.cpfaccno AS CPFACCNO,emp.region as region,'Single' DUPFlag,emp.PENSIONCONTRI as PENSIONCONTRI,emp.DATAFREEZEFLAG as DATAFREEZEFLAG,emp.form7narration as form7narration,emp.pensionno as pensionno from (select distinct to_char(to_date("
						+ dojQuery
						+ ",'dd-mon-yyyy') + rownum -1,'MONYYYY') MONYEAR from employee_pension_validate where empflag='Y' AND rownum <= to_date('"
						+ toYear
						+ "','dd-mon-yyyy')-to_date("
						+ dojQuery
						+ ",'dd-mon-yyyy')+1 ) empdt,"
						+ "(SELECT cpfaccno,to_char(MONTHYEAR, 'MONYYYY') empmonyear,TO_CHAR(MONTHYEAR, 'DD-MON-YYYY') AS MONTHYEAR,ROUND(EMOLUMENTS, 2) AS EMOLUMENTS,"
						+ " ROUND(EMPPFSTATUARY, 2) AS EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,AIRPORTCODE,REGION,EMPFLAG,PENSIONCONTRI,DATAFREEZEFLAG,form7narration,pensionno "
						+ " FROM EMPLOYEE_PENSION_VALIDATE   WHERE empflag = 'Y' and ("
						+ preparedString
						+ " ) AND MONTHYEAR >= TO_DATE('"
						+ fromYear
						+ "', 'DD-MON-YYYY') and "
						+ " empflag = 'Y'  ORDER BY TO_DATE(MONTHYEAR, 'dd-Mon-yy')) emp where empdt.monyear = emp.empmonyear(+)   and empdt.monyear not in (select to_char(MONTHYEAR,'MONYYYY')monyear FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and  ("
						+ preparedString
						+ ") AND MONTHYEAR >= TO_DATE('"
						+ fromYear
						+ "','DD-MON-YYYY')  group by  to_char(MONTHYEAR,'MONYYYY')  having count(*)>1)))mo where to_date(to_char(mo.Empmnthyear,'dd-Mon-yyyy')) >= to_date('"+toYear+"')";

      }else{
    	  sqlQuery="select to_char(monthyear,'dd-Mon-yyyy')AS MONTHYEAR,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,PF,PENSIONNO,CPFACCNO,REGION,AIRPORTCODE from employee_pension_validate where pensionno='"+pfid+"' AND MONTHYEAR >= TO_DATE('"
						+ fromYear
						+ "','DD-MON-YYYY') AND MONTHYEAR <= TO_DATE('"
						+ toYear
						+ "','DD-MON-YYYY')";
      }
		
      }
      System.out.println("header size "+penContHeaderList.size());
      if(penContHeaderList.size()==0){
			 sqlQuery="select to_char(monthyear,'dd-Mon-yyyy')AS MONTHYEAR,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,PF,PENSIONNO,CPFACCNO,REGION,AIRPORTCODE from employee_pension_validate where pensionno='"+pfid+"' AND MONTHYEAR >= TO_DATE('"
				+ fromYear
				+ "','DD-MON-YYYY') AND MONTHYEAR <= TO_DATE('"
				+ toYear
				+ "','DD-MON-YYYY')";	
		}
		
  	  
      log.info(sqlQuery);
      
      //**************
      
	ArrayList a=new ArrayList();
	//String sqlQuery="select emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,to_char(monthyear,'dd-Mon-yyyy') as monthyear,pensionno,region from employee_pension_validate where pensionno='"+pfid+"' and monthyear BETWEEN '"+fromYear+"' AND last_day('"+toYear+"')";
	//log.info("sqlQuery "+sqlQuery);
	try{
	con = commonDB.getConnection();
	st = con.createStatement();
	rs=st.executeQuery(sqlQuery);
	while(rs.next()){
		EmployeePensionCardInfo cardInfo=new EmployeePensionCardInfo();
		if(rs.getString("emoluments")!=null){
			cardInfo.setEmoluments(rs.getString("emoluments"));
		}else{
			cardInfo.setEmoluments("0.00");
		}
		if(rs.getString("EMPPFSTATUARY")!=null){
			cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
		}else{
			cardInfo.setEmppfstatury("0.00");
		}
		if(rs.getString("EMPVPF")!=null){
			cardInfo.setEmpvpf(rs.getString("EMPVPF"));
		}else{
			cardInfo.setEmpvpf("0.00");
		}
		if(rs.getString("EMPADVRECPRINCIPAL")!=null){
			cardInfo.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));
		}else{
			cardInfo.setPrincipal("0.00");
		}
		if(rs.getString("EMPADVRECINTEREST")!=null){
			cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
		}else{
			cardInfo.setInterest("0.00");
		}
		if(rs.getString("PENSIONCONTRI")!=null){
			cardInfo.setPensionContribution(rs.getString("PENSIONCONTRI"));
		}else{
			cardInfo.setPensionContribution("0.00");
		}
	//	log.info("monthyear "+ rs.getString("Monthyear"));
		if(rs.getString("Monthyear")!=null){
			cardInfo.setMonthyear(rs.getString("Monthyear"));
		}else{
			Date monthyear=rs.getDate("Empmnthyear");
			//monthyear=monthyear.substring(0, monthyear.lastIndexOf('-')+2);
			log.info("monthyear"+monthyear);
			String monthyear1=commonUtil.getDatetoString(monthyear, "dd-MMM-yyyy");
			cardInfo.setMonthyear(monthyear1);
		}
		/*if(rs.getString("pensionno")!=null){
			cardInfo.setPensionNo(rs.getString("pensionno"));
		}else{
			cardInfo.setPensionNo("");
		}*/
		cardInfo.setPensionNo(pfid);
		if(rs.getString("region")!=null){
			cardInfo.setRegion(rs.getString("region"));
		}else{
			cardInfo.setRegion(region);
		}
		
		//for advances and loans
		String advances = "select amount from employee_pension_advances where pensionno='"
				+ pfid
				+ "' and advtransdate BETWEEN '"+fromYear+"' AND last_day('"+toYear+"')";
		String loans = "select SUB_AMT,CONT_AMT  from employee_pension_loans where pensionno='"
				+ pfid
				+ "' and loandate BETWEEN '"+fromYear+"' AND last_day('"+toYear+"')";
		String advAmount = "0.00";
		st = con.createStatement();
		ResultSet rs1 = st.executeQuery(advances);
		while (rs1.next()) {
			if (rs1.getString("amount") != null) {
				advAmount = rs1.getString("amount");

			} else {
				advAmount = "0.00";
			}

		}
		cardInfo.setAdvancesAmount(advAmount);
		String employeeLoan = "0.00", aaiLoan = "0.00";
		ResultSet rs2 = st.executeQuery(loans);
		while (rs2.next()) {
			if (rs2.getString("SUB_AMT") != null) {
				employeeLoan = rs2.getString("SUB_AMT");

			} else {
				employeeLoan = "0.00";
			}
			if (rs2.getString("CONT_AMT") != null) {
				aaiLoan = rs2.getString("CONT_AMT");

			} else {
				aaiLoan = "0.00";
			}
		}
		cardInfo.setAdvancePFWPaid(employeeLoan);
		cardInfo.setAaiNet(aaiLoan);
		log.info("loan " + cardInfo.getAdvancePFWPaid());
		a.add(cardInfo);
	}
	if(a.size()==0){
		EmployeePensionCardInfo cardInfo = new EmployeePensionCardInfo();
		cardInfo.setEmoluments("0.00");
		cardInfo.setEmppfstatury("0.00");
		cardInfo.setEmpvpf("0.00");
		cardInfo.setPrincipal("0.00");
		cardInfo.setInterest("0.00");
		cardInfo.setPensionContribution("0.00");
		cardInfo.setMonthyear(fromYear);
		cardInfo.setPensionNo(pfid);
		cardInfo.setAdvancePFWPaid("0.00");
		cardInfo.setAaiNet("0.00");	
		cardInfo.setRegion(region);
		cardInfo.setAdvancesAmount("0.00");
		a.add(cardInfo);
	}
	}catch(Exception e){
		log.info(e.getMessage());
		
	}
	return a;	
	}
	public ArrayList saveCpfAdjustMents(String emoluments, String cpf,
			String vpf, String principle, String interest, String pensionno,
			String month, String year,String pccontrib,String transmonthyear,String computername,String username,String region,String advAmount,String loan_sub_amt,String loan_cont_amt,String cpfacno) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String fromYear = "", toYear = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (!year.equals("")) {
				String[] dateArr = year.split(",");
				fromYear = dateArr[0];
				toYear = dateArr[1];
			}
			log.info("from year " + fromYear + "toYear " + toYear);
			ImportDao IDAO = new ImportDao();
			FinacialDataBean bean = new FinacialDataBean();
			bean = IDAO.getEmolumentsBean1(con, fromYear, toYear, "", "",pensionno);
			log.info("emoluments " + bean.getEmoluments());
			String oldemppfstatuary="";
			String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");
			oldemppfstatuary=bean.getEmpPfStatuary();
			double emolumentdiff=0.00,emppfstatuarydiff=0.00;
			if(bean.getEmoluments()==""){
				bean.setEmoluments("0.00");
			}
			if(oldemppfstatuary==""){
				oldemppfstatuary="0.00";
			}
			double newemoluments=Double.parseDouble(emoluments)+Double.parseDouble(bean.getEmoluments());
			double newemppfstatuary=Double.parseDouble(oldemppfstatuary)+Double.parseDouble(cpf);
			emolumentdiff=Double.parseDouble(emoluments);
			emppfstatuarydiff=Double.parseDouble(cpf);
		String	emoluments_log_history = "insert into emoluments_log_history(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername,emolumentdiff,emppfstatuarydiff)values('"
				+ bean.getEmoluments()
				+ "','"
				+ newemoluments
				+ "','"
				+ oldemppfstatuary
				+ "','"
				+ newemppfstatuary
				+ "','"
				+ transmonthyear
				+ "','"
				+ updatedDate
				+ "','"
				+ pensionno
				+ "','"
				+ ""
				+ "','"
				+ region
				+ "','"
				+ username
				+ "','" + computername + "','"+emolumentdiff+"','"+emppfstatuarydiff+"')";
		log.info(emoluments_log_history);
		String checkRecord="select count(*) as count from employee_pension_validate where pensionno='"+ pensionno+"' and monthyear BETWEEN '"
					+ fromYear
					+ "' AND last_day('" + toYear + "') and empflag='Y'";
		  rs = st.executeQuery(checkRecord);
		  String query="";
		  while (rs.next()) {
			  int count = rs.getInt(1);
				if (count != 0) {
			 query = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
					+ emoluments
					+ "')) ,EMPPFSTATUARY=to_char(to_NUMBER(nvl(EMPPFSTATUARY,0)+'"
					+ cpf
					+ "')),EMPVPF=to_char(to_NUMBER(nvl(EMPVPF,0)+'"
					+ vpf
					+ "')),EMPADVRECPRINCIPAL=to_char(to_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
					+ principle
					+ "')),EMPADVRECINTEREST=to_char(to_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
					+ interest
					+ "')),PENSIONCONTRI=to_char(to_NUMBER(nvl(PENSIONCONTRI,0)+'"
					+ pccontrib
					+ "')) where pensionno='"
					+ pensionno
					+ "' and empflag='Y' and monthyear BETWEEN '"
					+ fromYear
					+ "' AND last_day('" + toYear + "')";
		  }	else{
			query="insert into employee_pension_validate(pensionno,emoluments,emppfstatuary,empvpf,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,monthyear,cpfaccno,region) values('"+pensionno+"','"+emoluments+"','"+cpf+"','"+vpf+"','"+principle+"','"+interest+"','"+pccontrib+"','"+fromYear+"','"+cpfacno+"','"+region+"')";  
		  }
		  }
		if (advAmount != "" && advAmount != "0.00"&&!advAmount.equals("0")) {
				String checkAdvtbl = "select count(*) as count from employee_pension_advances where pensionno='"
						+ pensionno
						+ "' and  ADVTRANSDATE BETWEEN '"+ fromYear	+ "' AND last_day('" + toYear + "')";
				rs = st.executeQuery(checkAdvtbl);
				String updateAdvtbl = "";
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count != 0) {
						updateAdvtbl = "update employee_pension_advances set AMOUNT=to_char(to_NUMBER(nvl(AMOUNT,0)+'"+ advAmount+ "')) where pensionno='"+ pensionno+"' and  ADVTRANSDATE BETWEEN '"+ fromYear	+ "' AND last_day('" + toYear + "')";
						st.executeUpdate(updateAdvtbl);
					} else {
						updateAdvtbl = "insert into employee_pension_advances(AMOUNT,pensionno,ADVTRANSDATE) values('"
								+ advAmount
								+ "','"
								+ pensionno
								+ "','"
								+ transmonthyear
								+ "')";
						st.executeUpdate(updateAdvtbl);
					}
				}

			}
			if (loan_sub_amt != "" && loan_sub_amt != "0.00" &&!loan_sub_amt.equals("0")) {
				String checkLoantbl = "select count(*) as count from employee_pension_loans where pensionno='"
						+ pensionno
						+ "' and  loandate BETWEEN '"+ fromYear	+ "' AND last_day('" + toYear + "')";
				rs = st.executeQuery(checkLoantbl);
				String updateloantbl = "";
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count != 0) {
						updateloantbl = "update employee_pension_loans set SUB_AMT=to_char(to_NUMBER(nvl(SUB_AMT,0)+'"+ loan_sub_amt+ "')),CONT_AMT=to_char(to_NUMBER(nvl(CONT_AMT,0)+'"+ loan_cont_amt+ "')),LOANTYPE='NRF' where pensionno='"
								+ pensionno
								+ "' and LOANDATE BETWEEN '"+ fromYear	+ "' AND last_day('" + toYear + "')";
						st.executeUpdate(updateloantbl);
					} else {
						updateloantbl = "insert into employee_pension_loans(SUB_AMT,CONT_AMT,pensionno,LOANDATE,LOANTYPE) values('"
								+ loan_sub_amt
								+ "','"
								+ loan_cont_amt
								+ "','"
								+ pensionno
								+ "','"
								+ transmonthyear + "','NRF')";
						st.executeUpdate(updateloantbl);
					}
				}}

			log.info(query);
			st.executeUpdate(query);
			st.executeUpdate(emoluments_log_history);
		

		} catch (Exception e) {
           log.info(e.getMessage());
		}
		ArrayList a = new ArrayList();
		String sqlQuery = "select emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,to_char(monthyear,'dd-Mon-yyyy') as monthyear,pensionno,region from employee_pension_validate where pensionno='"
				+ pensionno
				+ "' and empflag='Y' and monthyear BETWEEN '"
				+ fromYear
				+ "' AND last_day('" + toYear + "')";
		log.info("sqlQuery " + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				EmployeePensionCardInfo cardInfo = new EmployeePensionCardInfo();
				if (rs.getString("emoluments") != null) {
					cardInfo.setEmoluments(rs.getString("emoluments"));
				} else {
					cardInfo.setEmoluments("0.00");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
				} else {
					cardInfo.setEmppfstatury("0.00");
				}
				if (rs.getString("EMPVPF") != null) {
					cardInfo.setEmpvpf(rs.getString("EMPVPF"));
				} else {
					cardInfo.setEmpvpf("0.00");
				}
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					cardInfo.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));
				} else {
					cardInfo.setPrincipal("0.00");
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					cardInfo.setInterest(rs.getString("EMPADVRECINTEREST"));
				} else {
					cardInfo.setInterest("0.00");
				}
				if(rs.getString("PENSIONCONTRI")!=null){
					cardInfo.setPensionContribution(rs.getString("PENSIONCONTRI"));
				}else{
					cardInfo.setPensionContribution("0.00");
				}
				if (rs.getString("monthyear") != null) {
					cardInfo.setMonthyear(rs.getString("monthyear"));
				} else {
					cardInfo.setMonthyear("");
				}
				if (rs.getString("pensionno") != null) {
					cardInfo.setPensionNo(rs.getString("pensionno"));
				} else {
					cardInfo.setPensionNo("");
				}
				if (rs.getString("region") != null) {
					cardInfo.setRegion(rs.getString("region"));
				} else {
					cardInfo.setRegion("");
				}
				String advances = "select amount from employee_pension_advances where pensionno='"
					+ pensionno
					+ "' and advtransdate BETWEEN '"+fromYear+"' AND last_day('"+toYear+"')";
			String loans = "select SUB_AMT,CONT_AMT  from employee_pension_loans where pensionno='"
					+ pensionno
					+ "' and loandate BETWEEN '"+fromYear+"' AND last_day('"+toYear+"')";
			advAmount="0.00";
			st = con.createStatement();
			ResultSet rs1 = st.executeQuery(advances);
			while (rs1.next()) {
				if (rs1.getString("amount") != null) {
					advAmount = rs1.getString("amount");

				} else {
					advAmount = "0.00";
				}

			}
			cardInfo.setAdvancesAmount(advAmount);
			String employeeLoan = "0.00", aaiLoan = "0.00";
			ResultSet rs2 = st.executeQuery(loans);
			while (rs2.next()) {
				if (rs2.getString("SUB_AMT") != null) {
					employeeLoan = rs2.getString("SUB_AMT");

				} else {
					employeeLoan = "0.00";
				}
				if (rs2.getString("CONT_AMT") != null) {
					aaiLoan = rs2.getString("CONT_AMT");

				} else {
					aaiLoan = "0.00";
				}
			}
			cardInfo.setAdvancePFWPaid(employeeLoan);
			cardInfo.setAaiNet(aaiLoan);
			log.info("loan " + cardInfo.getAdvancePFWPaid());
				a.add(cardInfo);
			}
		} catch (Exception e) {
			log.info(e.getMessage());

		}finally{
			//commonDB.closeConnection(con, pst, rs);
		}
		
		return a;

	}

	public void finalUpdates(){
		Connection con=null;
		CallableStatement cst=null;
		try{
			con=commonDB.getConnection();
			cst=con.prepareCall("{call finalUpdates}");
			int count=cst.executeUpdate();
			log.info("finalUpdates================"+count);
		}catch(Exception e){
			log.printStackTrace(e);
		}finally{
			if (cst != null) {
				try {
					cst.close();
					cst = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			commonDB.closeConnection(con,null,null);
		}
		
	}
	private ArrayList pensionContrBulkPrintPFIDS(String region,
			String airportCD, String empserialNO, String cpfAccno,
			String transferFlag, String pfidString) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sqlQuery = "", prCPFString = "",stationString="";
		log.info("pensionContrBulkPrintPFIDS===region" + region+"airportCD"+airportCD);
		if(!airportCD.equals("NO-SELECT")){
			stationString=airportCD;
		}else{
			stationString="";
		}
		sqlQuery=this.buildQueryEmpPFTransInfoPrinting(pfidString,region,"false","","PENSIONNO",empserialNO,"false",stationString);
		PersonalDAO personalDAO = new PersonalDAO();
		PensionContBean data = null;
		CommonDAO commonDao = new CommonDAO();
		log.info("pensionContrBulkPrintPFIDS===Query" + sqlQuery);
		ArrayList empinfo = new ArrayList();
		try {
			con = commonDB.getConnection();
			pst = con
			.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			while (rs.next()) {
				data = new PensionContBean();
				data = loadEmployeeInfo(rs);
				prCPFString = commonDao.getCPFACCNOByPension(data
						.getEmpSerialNo(), data.getCpfacno(), data
						.getEmpRegion());
				
				String cpfString="";
				if(!prCPFString.equals("---")){
					String[] cpfaccnoList = prCPFString.split("=");
					
					 cpfString = personalDAO.preparedCPFString(cpfaccnoList);
				}else{
					cpfString="";
				}
				log.info(data.getEmpSerialNo() + "-" + cpfString);
				data.setPrepareString(cpfString);
				if(!cpfString.equals("")){
					empinfo.add(data);
				}
				
			}
			
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		return empinfo;
	}
	private int getNoOfMonthsForPFID(String formFromSelectDate) {
		log
				.info("=====================getNoOfMonthsForEachPFID===============================");
		String currentDt = "", findCurrentYear = "", findToMonth = "",formToDate="", findCurretntMonth = "", findFormYear = "";
		currentDt = commonUtil.getCurrentDate("dd-MMM-yyyy");
		int noOfMonths = 0, findFormToYear = 0,difference=0;
		try {
			findFormYear = commonUtil.converDBToAppFormat(formFromSelectDate,
					"dd-MMM-yyyy", "yyyy");
		
			findCurrentYear = commonUtil.converDBToAppFormat(currentDt,
					"dd-MMM-yyyy", "yyyy");
			findCurretntMonth = commonUtil.converDBToAppFormat(currentDt,
					"dd-MMM-yyyy", "MM");
			findCurretntMonth = commonUtil.converDBToAppFormat(currentDt,
					"dd-MMM-yyyy", "MM");
			findFormToYear=Integer.parseInt(findFormYear)+1;
			log.info("======findFormYear========" + findFormYear);
			log.info("======findCurrentYear========" + findCurrentYear);
			log.info("======findCurretntMonth========" + findCurretntMonth);
			log.info("======findCurretntMonth========" + findFormToYear);
			
			/*if ((Integer.parseInt(findCurrentYear) <= Integer
					.parseInt(findFormYear) && Integer.parseInt(findCurretntMonth)>=04 && Integer.parseInt(findCurretntMonth)<=12)||(Integer.parseInt(findCurrentYear) >= findFormToYear && Integer.parseInt(findCurretntMonth)<=2)) {
				noOfMonths = this.numOfMnthFinalSettlement(commonUtil
						.converDBToAppFormat(commonUtil
								.getCurrentDate("dd-MMM-yyyy"), "dd-MMM-yyyy",
								"MMM"));
			} else {
				noOfMonths = 12;
			}*/
			if(Integer.parseInt(findCurretntMonth)>=04 && Integer.parseInt(findCurretntMonth)<=12){
				difference=(Integer.parseInt(findCurrentYear)-1)-Integer.parseInt(findFormYear);
				
			}else if(Integer.parseInt(findCurretntMonth)>=01 && Integer.parseInt(findCurretntMonth)<=03){
				difference=(Integer.parseInt(findCurrentYear))-findFormToYear;
			}
			if(difference==0){
				noOfMonths = this.numOfMnthFinalSettlement(commonUtil
						.converDBToAppFormat(commonUtil
								.getCurrentDate("dd-MMM-yyyy"), "dd-MMM-yyyy",
								"MMM"));
			}else{
				noOfMonths = 12;
			}
			
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info("===getNoOfMonthsForPFID===noOfMonths========" + noOfMonths+"difference"+difference);
		return noOfMonths;
	}
	private String chkOBAdjFreezeInfo(String empSerialNo, Connection con,
			String obMnthYear) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "";
		String chkFlag = "";
		try {
			sqlInsQuery = "SELECT 'X' AS FLAG FROM EMPLOYEE_ADJ_OB_FREEZE WHERE DATAFREEZEFLAG='Y' AND ADJOBYEAR='"
				+ obMnthYear + "' AND PENSIONNO='" + empSerialNo + "'";
			st = con.createStatement();
			log.info("chkOBAdjFreezeInfo::Query" + sqlInsQuery);
			rs = st.executeQuery(sqlInsQuery);
			if (rs.next()) {
				if (rs.getString("FLAG") != null) {
					chkFlag = rs.getString("FLAG").trim();
				}
			}else{
				chkFlag="NOTFOUND";
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return chkFlag;
	}
	private int insertAdjBalFYI2009(Connection con, double cpf, double cpfinterest,
			double pension, double pensionintrest, double pf,
			double pfinterest, String pensionno, String region) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", remarks = "", adjOBYear = "",adjFreezeOBYear="";
		DecimalFormat df = new DecimalFormat("#########0");
		adjOBYear = Constants.ADJ_CREATED_DATE_2009;
		adjFreezeOBYear = Constants.ADJ_CREATED_DATE;
		try {
			remarks = "Generated From 1995-08 Grand Total PC Report";
			sqlInsQuery = "INSERT INTO employee_adj_ob(CPFTOTAL,CPFINTEREST,PENSIONTOTAL,PENSIONINTEREST,PFTOTAL,PFINTEREST,REMARKS,ADJOBYEAR,REGION,PENSIONNO)"+
			" SELECT (case when(CPFTOTAL-("+df.format(cpf)+"))< CPFTOTAL then ("+df.format(cpf)+"-CPFTOTAL) else (CPFTOTAL-("+df.format(cpf)+")) end)" +
			",(case when(CPFINTEREST-("+df.format(cpfinterest)+"))< CPFINTEREST then ("+df.format(cpfinterest)+"-CPFINTEREST) else (CPFINTEREST-("+df.format(cpfinterest)+")) end)" +
			",(case when(PENSIONTOTAL-("+df.format(pension)+"))< PENSIONTOTAL then ("+df.format(pension)+"-PENSIONTOTAL) else (PENSIONTOTAL-("+df.format(pension)+")) end)" +
			",(case when(PENSIONINTEREST-("+df.format(pensionintrest)+"))< PENSIONINTEREST then ("+df.format(pensionintrest)+"-PENSIONINTEREST) else (PENSIONINTEREST-("+df.format(pensionintrest)+")) end)" +
			",(case when(PFTOTAL-("+df.format(pf)+"))< PFTOTAL then ("+df.format(pf)+"-PFTOTAL) else (PFTOTAL-("+df.format(pf)+")) end)" +
			",(case when(PFINTEREST-("+ df.format(pfinterest)+"))< PFINTEREST then ("+df.format(pfinterest)+"-PFINTEREST) else (PFINTEREST-("+df.format(pfinterest)+")) end)" +

						",'"+remarks+"','"+adjOBYear+"','"+region+"',"+pensionno+" FROM EMPLOYEE_ADJ_OB_FREEZE WHERE PENSIONNO="+pensionno+" and adjobyear='"+adjFreezeOBYear+"'"; 
			st = con.createStatement();
			log.info("insertAdjBalFYI2009::Inserted Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);
			int adjobyear=Integer.parseInt(commonUtil.converDBToAppFormat(adjOBYear,"dd-MMM-yyyy","yyyy"));
			if(adjobyear>=2009){
				this.getUpdateAdjOBForPFCard(con,adjOBYear,pensionno);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}

	private int updateAdjBalFYI2009(Connection con, double cpf, double cpfinterest,
			double pension, double pensionintrest, double pf,
			double pfinterest, String pensionno, String region) {
		int count = 0;
		Statement st = null;
		String sqlInsQuery = "", remarks = "", adjOBYear = "", sqlAdjQuery = "";
		DecimalFormat df = new DecimalFormat("#########0");
		adjOBYear = Constants.ADJ_CREATED_DATE_2009;
		try {
			remarks = "Generated From 1995-08 Grand Total PC Report";
			st=con.createStatement();
			sqlInsQuery ="UPDATE employee_adj_ob ADJOB SET (CPFTOTAL,CPFINTEREST,PENSIONTOTAL,PENSIONINTEREST,PFTOTAL,PFINTEREST)=" +
			"( SELECT (case when(CPFTOTAL-("+df.format(cpf)+"))< CPFTOTAL then ("+df.format(cpf)+"-CPFTOTAL) else (CPFTOTAL-("+df.format(cpf)+")) end)" +
				",(case when(CPFINTEREST-("+df.format(cpfinterest)+"))< CPFINTEREST then ("+df.format(cpfinterest)+"-CPFINTEREST) else (CPFINTEREST-("+df.format(cpfinterest)+")) end)" +
				",(case when(PENSIONTOTAL-("+df.format(pension)+"))< PENSIONTOTAL then ("+df.format(pension)+"-PENSIONTOTAL) else (PENSIONTOTAL-("+df.format(pension)+")) end)" +
				",(case when(PENSIONINTEREST-("+df.format(pensionintrest)+"))< PENSIONINTEREST then ("+df.format(pensionintrest)+"-PENSIONINTEREST) else (PENSIONINTEREST-("+df.format(pensionintrest)+")) end)" +
				",(case when(PFTOTAL-("+df.format(pf)+"))< PFTOTAL then ("+df.format(pf)+"-PFTOTAL) else (PFTOTAL-("+df.format(pf)+")) end)" +
				",(case when(PFINTEREST-("+ df.format(pfinterest)+"))< PFINTEREST then ("+df.format(pfinterest)+"-PFINTEREST) else (PFINTEREST-("+df.format(pfinterest)+")) end)" +
				" FROM EMPLOYEE_ADJ_OB_FREEZE ADJOBFRZ WHERE ADJOB.PENSIONNO=ADJOBFRZ.PENSIONNO AND ADJOBFRZ.PRIORADJFLAG='N' AND  ADJOBFRZ.ADJOBYEAR='" + Constants.ADJ_CREATED_DATE + "' " +
					"AND ADJOBFRZ.PENSIONNO="+pensionno+") WHERE ADJOB.PENSIONNO="+pensionno+" AND ADJOB.ADJOBYEAR='" + adjOBYear + "' AND ADJOB.PRIORADJFLAG='N'";
			log.info("updateAdjBalFYI2009::update Query" + sqlInsQuery);
			count = st.executeUpdate(sqlInsQuery);
			int adjobyear=Integer.parseInt(commonUtil.converDBToAppFormat(adjOBYear,"dd-MMM-yyyy","yyyy"));
			if(adjobyear>=2009){
				this.getUpdateAdjOBForPFCard(con,adjOBYear,pensionno);
			}
			
			if (count != 0) {
				sqlAdjQuery = "UPDATE EMPLOYEE_GENRTED_PEND_ADJ_0B SET STATUS='Y',GENDATE=TO_CHAR(CURRENT_DATE, 'DD-MON-YYYY HH:MI:SS') WHERE PENSIONNO="
					+ pensionno;
				count = st.executeUpdate(sqlAdjQuery);
				log
				.info("updateAdjBal=========================="
						+ sqlAdjQuery);
			}
			
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return count;
	}
	public ArrayList getSummaryPCReport(String fromDate, String toDate,
			String region, String airportcode, String sortedColumn,
			String empNameFlag, String empName, String pensionno) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "";
		ArrayList summaryList = new ArrayList();
		EmployeePersonalInfo personalInfo=null;
		String sortingString="";
		if(sortedColumn.toLowerCase().equals("airportcode")){
			sortingString="trans."+sortedColumn+",pers.pensionno";
		}else{
			sortingString="pers."+sortedColumn;
		}
		String bulkPFIDQuery = this.buildQueryGetSummaryReport(region,
				airportcode, empNameFlag, empName, pensionno);
		sqlQuery = "select pers.pensionno AS PENSIONNO, initcap(pers.employeename) AS EMPLOYEENAME, pers.wetheroption AS WETHEROPTION, pers.dateofbirth AS DATEOFBIRTH, pers.dateofjoining AS DATEOFJOINING, "
				+ "trans.region AS region,initcap( (CASE WHEN (trans.DESEGNATION is not null) THEN trans.DESEGNATION ELSE pers.DESEGNATION END)) as DESEGNATION, trans.AIRPORTCODE AS AIRPORTCODE, NVL(trans.totalemoluments,'0') AS EMOLUMENTS, "
				+ "NVL(trans.PENSIONCONTRI,'0') AS PCVAL, NVL(trans.PF,'0') AS PF,NVL(trans.EMPPFSTATUARY,'0') as EMPPFSTATUARY from (select pensionno, employeename, DESEGNATION, wetheroption, dateofbirth, dateofjoining from employee_personal_info) pers, (select blktrans.pensionno, "
				+ "blktrans.region, blktrans.DESEGNATION, btrans.EMPPFSTATUARY, blktrans.AIRPORTCODE, btrans.totalemoluments as totalemoluments, btrans.PENSIONCONTRI, btrans.PF from (select pensionno, sum(round(emoluments, 0)) as totalemoluments, "
				+ "sum(round(PENSIONCONTRI, 0)) as PENSIONCONTRI,sum(round(EMPPFSTATUARY, 0)) as EMPPFSTATUARY,sum(round(PF, 0)) as PF from employee_pension_validate where monthyear between '"
				+ fromDate
				+ "' and LAST_DAY('"
				+ toDate
				+ "') and empflag = 'Y' group by pensionno) btrans, "
				+ "("
				+ bulkPFIDQuery
				+ ") blktrans where blktrans.pensionno = btrans.pensionno) trans where pers.pensionno = trans.pensionno order by "
				+ sortingString;
		StringBuffer buffer = new StringBuffer();
		log.info("getSummaryPCReport::SQL Query"+sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				personalInfo = new EmployeePersonalInfo();
				if(rs.getString("PENSIONNO")!=null){
					
					personalInfo.setPensionNo(rs.getString("PENSIONNO"));
				}else{
					personalInfo.setPensionNo("");
				}
		        if (rs.getString("employeename") != null) {
		        	personalInfo.setEmployeeName(rs.getString("employeename"));
		        } else {
		        	personalInfo.setEmployeeName("---");
		        }
		        if (rs.getString("WETHEROPTION") != null) {
		        	personalInfo.setWetherOption(rs.getString("WETHEROPTION"));
		        } else {
		        	personalInfo.setWetherOption("---");
		        }
				 if (rs.getString("dateofbirth") != null) {
					 personalInfo.setDateOfBirth(commonUtil.converDBToAppFormat(rs
			                    .getDate("dateofbirth")));
			     } else {
			    	 personalInfo.setDateOfBirth("---");
			     }
			     if (rs.getString("DATEOFJOINING") != null) {
			    	 personalInfo.setDateOfJoining(commonUtil.converDBToAppFormat(rs
			                    .getDate("DATEOFJOINING")));
			     } else {
			    	 personalInfo.setDateOfJoining("---");
			     }
				 if(!personalInfo.getDateOfBirth().equals("---")){
			            String personalPFID = commonDAO.getPFID(personalInfo.getEmployeeName(), personalInfo
			                    .getDateOfBirth(), commonUtil.leadingZeros(5,personalInfo.getPensionNo()));
			            personalInfo.setPfID(personalPFID);
			        }else{
			        	personalInfo.setPfID(personalInfo.getPensionNo());
			    }
				if(rs.getString("DESEGNATION")!=null){
					personalInfo.setDesignation(rs.getString("DESEGNATION"));
				}else{
					 personalInfo.setDesignation("---");
				}
				if(rs.getString("AIRPORTCODE")!=null){
					personalInfo.setAirportCode(rs.getString("AIRPORTCODE"));
				}else{
					personalInfo.setAirportCode("---");
				}
				if(rs.getString("REGION")!=null){
					personalInfo.setRegion(rs.getString("REGION"));
				}else{
					personalInfo.setRegion("---");
				}
				buffer.append(rs.getString("EMOLUMENTS"));
				buffer.append(",");
				buffer.append(rs.getString("PCVAL"));
				buffer.append(",");
				buffer.append(rs.getString("PF"));
				buffer.append(",");
				buffer.append(rs.getString("EMPPFSTATUARY"));
				personalInfo.setPensionInfo(buffer.toString());
				summaryList.add(personalInfo);
				buffer=new StringBuffer();
			}
		} catch (Exception e) {
			log.info(e.getMessage());

		}finally{
			commonDB.closeConnection(con,st,rs);
		}
		return summaryList;
	}
	public String buildQueryGetSummaryReport(String region,String airportcode, String empNameFlag, String empName,String pensionno) {
		log
				.info("FinanceReportDAO::buildQueryGetSummaryReport-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "select pensionno, region, DESEGNATION, AIRPORTCODE from epis_bulk_print_pfids";
		

		if (!region.equals("") ) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("")) {
			whereClause.append(" LOWER(AIRPORTCODE) ='" + airportcode.toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if (region.equals("")
				&& (airportcode.equals("") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
		dynamicQuery = query.toString();
		log
				.info("FinanceReportDAO::buildQueryGetSummaryReport Leaving Method");
		return dynamicQuery;
	}
	private String getEmployeePersonalInfo(Connection con,String pensionNo) {
		String sqlQuery = "";
		Statement st = null;
		ResultSet rs = null;
		String employeeName = "";
		long advance = 0, amount = 0, partAmount = 0;
		sqlQuery="SELECT EMPLOYEENAME FROM EMPLOYEE_PERSONAL_INFO WHERE PENSIONNO="+pensionNo;
		//log.info("FinanceReportDAO::getEmployeeAdvances" + sqlQuery);
		try {
			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if(rs.getString("EMPLOYEENAME")!=null){
					employeeName=rs.getString("EMPLOYEENAME");
				}
				
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return employeeName;
	}
	public ArrayList getAllYearsForm7PrintOut(String selectedYear, String month,
			String sortedColumn, String region, boolean formFlag,
			String airportCode, String pensionNo, String range, String empflag,
			String empName,String formType,String formRevisedFlag) {
		ArrayList totalYearForm8List=new ArrayList();
		ArrayList form8List=new ArrayList();
		int currentYear=Integer.parseInt(commonUtil.getCurrentDate("yyyy"));
		int fromOldYear=1995;
		int totalSpan=currentYear-fromOldYear;
		String message="";
		int messageFromYear=0,messageToYear=0;
		Form7MultipleYearBean multipleYearBean=null;
		if(selectedYear.equals("NO-SELECT")){
			for(int i=0;i<=totalSpan;i++){
				 multipleYearBean=new Form7MultipleYearBean();
				form8List=this.rnfcForm7Report(Integer.toString(fromOldYear+i), month,
						sortedColumn, region, formFlag,
						 airportCode, pensionNo, range, empflag,
						 empName,formType,formRevisedFlag);
				if(form8List.size()!=0){
					messageFromYear=fromOldYear+i;
					messageToYear=messageFromYear+1;
					message="01-Apr-" + messageFromYear + " To Mar-" + messageToYear;
					multipleYearBean.setEachYearList(form8List);
					multipleYearBean.setMessage(message);
					totalYearForm8List.add(multipleYearBean);
				}
			
			}
		}
		return totalYearForm8List;
	}


	private int insertOBInfo(String empSerialNo, Connection con,
			ArrayList OBList, ArrayList closingYearList,ArrayList dataFinalSettlementList,double rateOfInterest,int noOfmonthsForInterest,double arrearEmpNetInterest,double arrearAaiNetInterest,String adjIntAmount,String adjDate,double totalArrearEmpnetAmnt,double totalArrearAAINetAmnt) {
		Statement st = null;
		ResultSet rs = null;
		String sqlInsQuery = "", obMnthYear = "", finMnthYear = "", sqlInsQuery1 = "";
		int inserted = 0,finalInterestCal=0;
	  	DecimalFormat df1 = new DecimalFormat("#########0");
	  	double remaininInt=0.00,remainAaiInt=0.00;
	  	double remainingEmpNetInt=0.00,remainingAaiInt=0.00;
		Long finalStlmentEmpNet=null,finalStlmentNetAmount=null,finalStlmentAAICon=null,finalStlmentPensDet=null;
		Long finalEmpNetOB = null, finalAaiNetOB = null, finalPrincipalOB = null, finalPensionOB = null, finalEmpNetOBIntrst = null, finalAaiNetOBIntrst = null, finalPensionOBIntrst = null;
		Long empNetYearTotal = null, aaiNetYearTotal = null, pensionYearTotal = null, principalYearTotal = null;
		Long empNetYearInterest = null, aaiNetYearInterest = null, pensionYearInterest = null, principalYearInterest = null;
		Long totalEmpNetYearIntereset=null,totalAAINetYearInterest = null;
		Long empNetClosingInt=null,aaiNetClosingInt=null;
		if(adjIntAmount.equals("")){
			adjIntAmount="0";
		}
		Long adjInt=new Long(adjIntAmount);
		finalEmpNetOB = (Long) OBList.get(0);
		finalAaiNetOB = (Long) OBList.get(1);
		finalPensionOB = (Long) OBList.get(2);
		obMnthYear = (String) OBList.get(3);
		Long netcloseEmpNet=null,netcloseAaiNetAmount=null;
		finalInterestCal=12-noOfmonthsForInterest;
		if(dataFinalSettlementList.size()!=0){
			finalStlmentEmpNet=new Long(Long.parseLong((String)dataFinalSettlementList.get(5)));
			finalStlmentAAICon=new Long(Long.parseLong((String)dataFinalSettlementList.get(6)));
			finalStlmentPensDet=new Long(Long.parseLong((String)dataFinalSettlementList.get(7)));
			finalStlmentNetAmount=new Long(Long.parseLong((String)dataFinalSettlementList.get(8)));
			log.info("dataFinalSettlementList"+(String)dataFinalSettlementList.get(8)+"finalStlmentEmpNet=="+finalStlmentEmpNet+"finalStlmentNetAmount"+finalStlmentNetAmount);
			netcloseEmpNet=new Long((finalEmpNetOB.longValue())+(-finalStlmentEmpNet.longValue()));
			netcloseAaiNetAmount=new Long((finalAaiNetOB.longValue())+(-finalStlmentAAICon.longValue()));
			empNetClosingInt=new Long(Math.round((netcloseEmpNet.longValue()*rateOfInterest/100/12)*finalInterestCal));
			aaiNetClosingInt=new Long(Math.round((netcloseAaiNetAmount.longValue()*rateOfInterest/100/12)*finalInterestCal));
			remaininInt=arrearEmpNetInterest+Math.round((netcloseEmpNet.longValue()*rateOfInterest/100/12)*finalInterestCal);
			remainAaiInt=arrearAaiNetInterest+Math.round((netcloseAaiNetAmount.longValue()*rateOfInterest/100/12)*finalInterestCal);
			netcloseEmpNet=new Long(netcloseEmpNet.longValue()+new Long(df1.format(remaininInt)).longValue());
			netcloseAaiNetAmount=new Long(netcloseAaiNetAmount.longValue()+new Long(df1.format(remainAaiInt)).longValue());
		}else{
			netcloseEmpNet=finalEmpNetOB;
			netcloseAaiNetAmount=finalAaiNetOB;
		}
		
		if(!adjDate.equals("---")){
			netcloseEmpNet=new Long(netcloseEmpNet.longValue()+adjInt.longValue());
			netcloseAaiNetAmount=new Long(netcloseAaiNetAmount.longValue()+adjInt.longValue());
		}
		
		try {
			finMnthYear = "31-Mar-"
				+ commonUtil.converDBToAppFormat(obMnthYear, "dd-MMM-yyyy",
				"yyyy");
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
		empNetYearInterest = (Long) OBList.get(4);
		aaiNetYearInterest = (Long) OBList.get(5);
		pensionYearInterest = (Long) OBList.get(6);
		finalPrincipalOB = (Long) OBList.get(7);
		empNetYearTotal = (Long) closingYearList.get(0);
		aaiNetYearTotal = (Long) closingYearList.get(1);
		pensionYearTotal = (Long) closingYearList.get(2);
		pensionYearInterest = (Long) closingYearList.get(3);

		log.info("empSerialNo" + empSerialNo + "obMnthYear" + obMnthYear);
		log.info("finalEmpNetOB" + netcloseEmpNet + "finalAaiNetOB"
				+ netcloseAaiNetAmount +"adjInt"+adjInt+"remainAaiInt"+remainAaiInt+ "finalPensionOB" + finalPensionOB
				+ "finalPrincipalOB" + finalPrincipalOB);
		log.info("empNetYearTotal" + empNetYearTotal + "empNetYearInterest"
				+ empNetYearInterest + "aaiNetYearTotal" + aaiNetYearTotal
				+ "aaiNetYearInterest" + aaiNetYearInterest
				+ "pensionYearTotal" + pensionYearTotal + "pensionYearInterest"
				+ pensionYearInterest);

		try {
	
			if (!this.chkOBInfo(empSerialNo, con, OBList).equals("X")) {
				sqlInsQuery = "INSERT INTO EMPLOYEE_PENSION_OB(EMPNETOB,AAINETOB,PENSIONOB,OUTSTANDADV,OBFLAG,OBYEAR,PENSIONNO) VALUES("
					+ netcloseEmpNet
					+ ","
					+ netcloseAaiNetAmount
					+ ","
					+ finalPensionOB
					+ ","
					+ finalPrincipalOB
					+ ",'Y','"
					+ obMnthYear + "'," + empSerialNo + ")";
			} else {
				sqlInsQuery = "UPDATE EMPLOYEE_PENSION_OB SET EMPNETOB="
					+ netcloseEmpNet + ",AAINETOB=" + netcloseAaiNetAmount
					+ ",PENSIONOB=" + finalPensionOB + ",OUTSTANDADV="
					+ finalPrincipalOB + " WHERE PENSIONNO=" + empSerialNo
					+ " AND OBYEAR='" + obMnthYear + "'";
			}
			netcloseEmpNet=finalEmpNetOB;
			netcloseAaiNetAmount=finalAaiNetOB;
			st = con.createStatement();
			log.info("insertOBInfo::Inserted Query" + sqlInsQuery);
			inserted = st.executeUpdate(sqlInsQuery);
			if(empNetClosingInt==null){
				empNetClosingInt=new Long(0);
			}
			if(aaiNetClosingInt==null){
				aaiNetClosingInt=new Long(0);
			}
			log.info("insertOBInfo::empNetYearInterest" + empNetYearInterest+"remaininInt"+remaininInt+"arrearEmpNetInterest"+arrearEmpNetInterest);
			log.info("insertOBInfo::totalArrearEmpnetAmnt" + totalArrearEmpnetAmnt+"empNetClosingInt"+empNetClosingInt);
			if(totalArrearEmpnetAmnt!=0.00){
				//remainingEmpNetInt=0.00,remainingAaiInt=0.00;
				
				remainingEmpNetInt=new Long(df1.format(remaininInt)).longValue()-(new Long(df1.format(totalArrearEmpnetAmnt)).longValue()+empNetClosingInt.longValue());
				remainingAaiInt=new Long(df1.format(remainAaiInt)).longValue()-(new Long(df1.format(totalArrearAAINetAmnt)).longValue()+aaiNetClosingInt.longValue());
			}else{
				remainingEmpNetInt=new Long(df1.format(remaininInt)).longValue()-new Long(df1.format(empNetClosingInt)).longValue();
				remainingAaiInt=new Long(df1.format(remainAaiInt)).longValue()-new Long(df1.format(aaiNetClosingInt)).longValue();
			}
			log.info("insertOBInfo::remainingEmpNetInt" + remainingEmpNetInt+"remainingAaiInt"+remainingAaiInt);
			totalEmpNetYearIntereset=new Long(empNetYearInterest.longValue()+new Long(df1.format(remainingEmpNetInt)).longValue());
			totalAAINetYearInterest=new Long(aaiNetYearInterest.longValue()+new Long(df1.format(remainingAaiInt)).longValue());
			if(!adjDate.equals("---")){
				totalEmpNetYearIntereset=new Long(empNetYearInterest.longValue()+adjInt.longValue());
				totalAAINetYearInterest=new Long(aaiNetYearInterest.longValue()+adjInt.longValue());
			}
			log.info("insertOBInfo::totalEmpNetYearIntereset" + totalEmpNetYearIntereset+"totalAAINetYearInterest"+totalAAINetYearInterest+"adjInt"+adjInt);
			if (!chkControlAcInterestInfo(empSerialNo, con, finMnthYear).equals("X")) {
				sqlInsQuery1 = "INSERT INTO EPIS_FIN_YEAR_TOTAL_INTEREST(EMPSUBTOTAL,EMPSUBINTEREST,AAICONTRTOTAL,AAICONTRINTEREST,PENSIONTOTAL,PENSIONINTEREST,FINEMP,FINAAI,NETCLOSINGEMPNET,NETCLOSINGAAINET,FLAG,YEAR,PENSIONNO) VALUES("
					+ empNetYearTotal
					+ ","
					+ totalEmpNetYearIntereset
					+ ","
					+ aaiNetYearTotal
					+ ","
					+ totalAAINetYearInterest
					+ ","
					+ pensionYearTotal
					+ ","
					+ pensionYearInterest
					+ ","
					+ finalStlmentEmpNet
					+ ","
					+ finalStlmentAAICon
					+ ","
					+ empNetClosingInt
					+ ","
					+ aaiNetClosingInt
					+ ",'Y','" + finMnthYear + "'," + empSerialNo + ")";
			} else {
				sqlInsQuery1 = "UPDATE EPIS_FIN_YEAR_TOTAL_INTEREST set EMPSUBTOTAL="
					+ empNetYearTotal
					+ ",EMPSUBINTEREST="
					+ totalEmpNetYearIntereset
					+ ",AAICONTRTOTAL="
					+ aaiNetYearTotal
					+ ",AAICONTRINTEREST="
					+ totalAAINetYearInterest
					+ ",PENSIONTOTAL="
					+ pensionYearTotal
					+ ",PENSIONINTEREST="
					+ pensionYearInterest
					+ ",FINEMP="
					+ finalStlmentEmpNet
					+ ",NETCLOSINGEMPNET="
					+ empNetClosingInt
					+ ",FINAAI="
					+ finalStlmentAAICon
					+ ",NETCLOSINGAAINET="
					+ aaiNetClosingInt
					
					
					+ " WHERE PENSIONNO="
					+ empSerialNo + " AND YEAR='" + finMnthYear + "'";
			}

			if (inserted != 0) {
				inserted = st.executeUpdate(sqlInsQuery1);
			}
			log.info("insertOBInfo::Inserted Query" + sqlInsQuery1);

		} catch (SQLException e) {

			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(st);
		}
		return inserted;
	}
	public boolean compareFinalSettlementDates(String fromDate,String todate,String finalsettlementDate){
		 Date fromYear=new Date();
			Date toYear=new Date();
			Date fnlDate=new Date();
			boolean finalDateFlag=false;
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
			System.out.println("fromDate"+fromDate+"todate"+todate+"finalsettlementDate"+finalsettlementDate);
			try {
				fromYear=dateFormat.parse(fromDate);
				toYear=dateFormat.parse(todate);
				fnlDate=dateFormat.parse(finalsettlementDate);
				if (fnlDate.after(fromYear) &&fnlDate.before(toYear)){
					finalDateFlag=true;
					log.info(fromDate+"In between"+finalsettlementDate+" years"+todate);
				}else{
					finalDateFlag=false;
					log.info(fromDate+"In out "+finalsettlementDate+" years"+todate);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
			return finalDateFlag;
	}
	 public void editFinalDate(String finalsettlementDate,String pfid)
		{
			log.info("pfid"+pfid+"finalsettlementDate"+finalsettlementDate);
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String transMonthYear = "",sqlQuery = "",sqlQuery1 = "";
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				DateFormat df = new SimpleDateFormat("dd-MMM-yy");
				//Date transdate = df.parse(finalsettlementDate);
				transMonthYear = commonUtil.converDBToAppFormat(finalsettlementDate
						.trim(), "dd/MMM/yyyy", "dd-MMM-yyyy");
				log.info("transMonthYear"+transMonthYear);
				PensionContBean bean = new PensionContBean();
				sqlQuery="update employee_info set SETDATEOFANNUATION='"+transMonthYear+"' where empserialnumber='"+pfid+ "'";
				sqlQuery1="update employee_personal_info set FINALSETTLMENTDT='"+transMonthYear+"' where pensionno='"+pfid+ "'";
				log.info("sqlQuery"+sqlQuery);
				log.info("sqlQuery"+sqlQuery1);
				st.executeUpdate(sqlQuery);
				st.executeUpdate(sqlQuery1);
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);

			}
			
			
		}
	 public void pfCardProcessing(String range, String region,
				String selectedYear, String empNameFlag, String empName,
				String sortedColumn, String pensionno, FileWriter fw,
				String stationName) {
			
			
			log.info("FinancialReportDAO::pfCardProcessing");
			String fromYear = "", toYear = "", dateOfRetriment = "";
			Connection con = null;
			ArrayList empDataList = new ArrayList();
			EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
			try {
				con = commonDB.getConnection();
				empDataList = this.getEmployeePersonalInfo(con, range, region,
						empNameFlag, empName, pensionno,stationName);
				for (int i = 0; i < empDataList.size(); i++) {
					personalInfo = (EmployeePersonalInfo) empDataList.get(i);
					ProcessforAdjOb(personalInfo.getPensionNo(),true);
					try {
						fw
								.write(commonUtil.leadingZeros(5, personalInfo
										.getPensionNo())
										+ "================"
										+ personalInfo.getAirportCode()
										+ "========"
										+ personalInfo.getRegion()
										+ "===========================IS PROCESSED"
										+ "\n");
						fw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception se) {
				log.printStackTrace(se);
			} finally {
				commonDB.closeConnection(con, null, null);
			}

			//return totalInserted;
		}
	 public String buildQueryEmpPFCardProcessing(String range, String region,
				String empNameFlag, String empName,String pensionno, String airportcode) {
			log
			.info("FinanceReportDAO::buildQueryEmpPFCardProcessing-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "", sqlQuery = "";
			int startIndex = 0, endIndex = 0;
			
			sqlQuery = "SELECT PENSIONNO,AIRPORTCODE,REGION FROM EPIS_PERSONAL_PROCESSING_INFO EPV";
			if (!range.equals("NO-SELECT")) {
				String[] findRnge = range.split(" - ");
				startIndex = Integer.parseInt(findRnge[0]);
				endIndex = Integer.parseInt(findRnge[1]);
				
				whereClause.append("  (EPV.PFID BETWEEN " + startIndex
						+ " AND " + endIndex + ")");
				whereClause.append(" AND ");
				
			}
			
			if (!region.equals("") && (empNameFlag.equals("false"))) {
				whereClause.append(" EPV.REGION ='" + region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("") && (empNameFlag.equals("false"))) {
				whereClause.append(" EPV.AIRPORTCODE ='" + airportcode + "'");
				whereClause.append(" AND ");
			}
			
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("EPV.PENSIONNO='" + pensionno + "' ");
					whereClause.append(" AND ");
				}
			}
			query.append(sqlQuery);
			if ((region.equals("")) && (airportcode.equals(""))
					&& (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
				
			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
			String orderBy = " ORDER BY EPV.PFID";
			query.append(orderBy);
			dynamicQuery = query.toString();
			log
			.info("FinanceReportDAO::buildQueryEmpPFCardProcessing Leaving Method");
			return dynamicQuery;
		}
	 public ArrayList getEmployeePersonalInfo(Connection con,String range, String region,
				String empNameFlag, String empName,
				String pensionno, String airportcode) {
			CommonDAO commonDAO = new CommonDAO();
		
			Statement st = null;
			ResultSet rs = null;
			String sqlQuery = "", pfidWithRegion = "", pensionAppednQry = "";
			EmployeePersonalInfo data = null;

			ArrayList empinfo = new ArrayList();
	

			try {
				
				st = con.createStatement();
				if (region.equals("NO-SELECT")) {
					region = "";
				}
				
					sqlQuery = this.buildQueryEmpPFCardProcessing(range, region,
							empNameFlag, empName, pensionno,airportcode);
				
					
				log.info("FinanceReportDAO::getEmployeePersonalInfo" + sqlQuery);
				rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					data = new EmployeePersonalInfo();

				     if (rs.getString("PENSIONNO") != null) {
				    	 data.setPensionNo(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
				    
				        } else {
				        	data.setPensionNo("---");
				        }
					empinfo.add(data);
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return empinfo;
		}
	 public String getArrearDate(Connection con,String fromYear,String toYear,String pensionno){
			String arrearFromDate="",arrearToDate="",year="";
			Statement st = null;
			ResultSet rs = null;
			String sqlQuery = "";
			StringBuffer buffer=new StringBuffer();
			int i=0;
			try {
				year=commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy","yyyy");
			} catch (InvalidDataException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				st = con.createStatement();
				if(Integer.parseInt(year)==2009){
					sqlQuery="SELECT TO_CHAR(MONTHYEAR,'dd-Mon-yyyy') as ARREARFROMDATE,TO_CHAR(LAST_DAY(MONTHYEAR),'dd-Mon-yyyy') as ARREARTODATE FROM EMPLOYEE_PENSION_VALIDATE WHERE (NVL(PENSIONCONTRI,0.00)!=0.00 ) AND (ARREARFLAG='Y' OR EDITTRANS='Y') AND EMPFLAG='Y' AND MONTHYEAR BETWEEN '"+fromYear+"' AND '"+toYear+"' AND PENSIONNO="+pensionno;
				}else{
					sqlQuery="SELECT TO_CHAR(MONTHYEAR,'dd-Mon-yyyy') as ARREARFROMDATE,TO_CHAR(LAST_DAY(MONTHYEAR),'dd-Mon-yyyy') as ARREARTODATE FROM EMPLOYEE_PENSION_VALIDATE WHERE (NVL(PENSIONCONTRI,0.00)!=0.00 ) AND (ARREARFLAG='Y') AND EMPFLAG='Y' AND MONTHYEAR BETWEEN '"+fromYear+"' AND '"+toYear+"' AND PENSIONNO="+pensionno;
				}
				
				log.info("FinanceReportDAO::getEmployeePersonalInfo" + sqlQuery);
				rs = st.executeQuery(sqlQuery);

				/*while (rs.next() && i<2) {
					 i++;
				     if (rs.getString("ARREARFROMDATE") != null) {
				    	 arrearFromDate=rs.getString("ARREARFROMDATE");
				    	 arrearToDate=rs.getString("ARREARTODATE");
				        } else {
				        	arrearFromDate=fromYear;
							arrearToDate=toYear;
				        }
					
				}
				if(i!=1){
					arrearFromDate=fromYear;
					arrearToDate=toYear;
				}*/
				if (rs.next()) {
				     if (rs.getString("ARREARFROMDATE") != null) {
				    	 arrearFromDate=rs.getString("ARREARFROMDATE");
				    	 arrearToDate=rs.getString("ARREARTODATE");
				        } else {
				        	arrearFromDate=fromYear;
							arrearToDate=toYear;
				        }
					
				}else{
					arrearFromDate=fromYear;
					arrearToDate=toYear;
				}
				buffer.append(arrearFromDate);
				buffer.append(",");
				buffer.append(arrearToDate);
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return buffer.toString();
			
		}

	 public ArrayList getRfcForm8IndivReport(String selectedYear, String month,
				String sortedColumn, String region, boolean formFlag,
				String airportCode, String pensionNo, String range, String empflag,
				String empName,String formType,String formTypeRevisedFlag) {
			String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
			int toSelectYear = 0;
			ArrayList empList = new ArrayList();
			EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
			ArrayList form8List = new ArrayList();
			ArrayList bothList = new ArrayList();
			ArrayList getPensionList = new ArrayList();
			Form8Bean indivBean=new Form8Bean();
			boolean arrearsFlag=false;
			if (!month.equals("NO-SELECT")) {
				try {
					frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
				fromYear = "01-Apr-" + selectedYear;
				toSelectYear = Integer.parseInt(selectedYear) + 1;
				toYear = "01-Mar-" + toSelectYear;
			} else if (!selectedYear.equals("Select One")
					&& !month.equals("NO-SELECT")) {
				fromYear = "01-" + frmMonth + "-" + selectedYear;
				toYear = fromYear;
			} else {
				fromYear = Constants.FORMS_FROM_DATE;
				toYear = Constants.FORMS_TO_DATE;
			}
			Form8RemittanceBean form8Remittance=new Form8RemittanceBean();
			empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
					fromYear, toYear, airportCode, pensionNo, empflag, empName,"N",formType,formTypeRevisedFlag);
		
			if(empList.size()==0 && Integer.parseInt(selectedYear)>=2008){
						empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
								fromYear, toYear, airportCode, pensionNo, empflag, empName,"Y",formType,formTypeRevisedFlag);
						arrearsFlag=true;
			}
			log.info("rnfcForm7Report:::fromYear"+fromYear+"toYear"+toYear+"arrearsFlag"+arrearsFlag+"Size is===="+empList.size());
			Connection con=null;
			String pensionInfo = "", regionInfo = "";
			try{
				con=commonDB.getConnection();
				for (int i = 0; i < empList.size(); i++) {
					personalInfo = (EmployeePersonalInfo) empList.get(i);
					 if(personalInfo.getChkarrearAdj().equals("N")){
						 arrearsFlag=false;
					 }
					if (!personalInfo.getDateOfBirth().equals("---")) {
						try {
							dateOfRetriment = this.getRetriedDate(personalInfo
									.getDateOfBirth());
						} catch (InvalidDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(!personalInfo.getFinalSettlementDate().equals("---")){
						int finalSettlmentYear=0;
						try {
							finalSettlmentYear=Integer.parseInt(commonUtil.converDBToAppFormat(personalInfo.getFinalSettlementDate(),"dd-MMM-yyyy","yyyy"));
						} catch (NumberFormatException e) {
							log.printStackTrace(e);
						} catch (InvalidDataException e) {
							log.printStackTrace(e);
						}
						if((Integer.parseInt(selectedYear)>=finalSettlmentYear && finalSettlmentYear<=toSelectYear) && arrearsFlag==false){
							arrearsFlag=true;
						}
					}
					String tempEntitle="",returnFromDate="",fromDOJ="";
					tempEntitle="01"+personalInfo.getDateOfEntitle().substring(2,personalInfo.getDateOfEntitle().length());
					returnFromDate=this.compareTwoDates(tempEntitle,fromYear);
					if(returnFromDate.equals(tempEntitle)){
						fromDOJ=personalInfo.getDateOfEntitle();
					}else{
						fromDOJ=fromYear;
					}
					log.info("Pensionno"+personalInfo.getOldPensionNo()+"From Date"+fromYear+"toYear"+toYear+"fromDOJ"+fromDOJ+"returnFromDate"+returnFromDate);
					getPensionList = this.getForm8PensionInfo(con,fromDOJ, toYear,
							personalInfo.getPfIDString(), personalInfo
									.getWetherOption(), personalInfo.getRegion(),
							false, dateOfRetriment, personalInfo.getDateOfBirth(),
							personalInfo.getOldPensionNo(),arrearsFlag,formTypeRevisedFlag);
					form8Remittance = this.getForm8RemInfo(con,fromDOJ, toYear,
							personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(),
					false, dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo(),arrearsFlag,formTypeRevisedFlag);
					personalInfo.setPensionList(getPensionList);

					dateOfRetriment = "";
					form8List.add(personalInfo);
					indivBean.setForm8List(form8List);
					indivBean.setRemittanceBean(form8Remittance);
				}
			}catch(Exception e){
				log.printStackTrace(e);
			}finally{
				commonDB.closeConnection(con,null,null);
			}
			bothList.add(indivBean);
			
			return bothList;
		}
	 private ArrayList getForm8PensionInfo(Connection con,String fromDate, String toDate,
				String pfIDS, String wetherOption, String region, boolean formFlag,
				String dateOfRetriment, String dateOfBirth, String pensionNo,boolean arrearsFlag,String formTypeRevisedFlag) {

			DecimalFormat df = new DecimalFormat("#########0.00");
			DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
			
			Statement st = null;
			ResultSet rs = null;
			EmployeePensionCardInfo cardInfo = null;
			ArrayList pensionList = new ArrayList();
			boolean flag = false;
			boolean contrFlag = false, chkDOBFlag = false;
			String checkDate = "", chkMnthYear = "Apr-1995", formatMnth = "", chkDecMnthYear = "", findFromYear = "", findToYear = "";
			String monthYear = "", days = "", getMonth = "", sqlQuery = "", calEmoluments = "",arrearFromDate="01-Sep-2009",arrearToDate="",arrearDatesInfo="";
			int getDaysBymonth = 0;
			long transMntYear = 0, empRetriedDt = 0;
			log.info("checkDate==" + checkDate + "flag===" + flag);
			double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
			double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;
			boolean yearBreakMonthFlag=false;
			boolean obFlag = false,fpfFund=false;
			double pensionVal=0.0;

			try {
				findFromYear = commonUtil.converDBToAppFormat(fromDate,
						"dd-MMM-yyyy", "yyyy");
				findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
						"yyyy");
			} catch (InvalidDataException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			log.info("FinanceReportDAO::getForm8PensionInfo" + pensionNo);
			String condition="";
			if(pensionNo!="" && !pensionNo.equals("")){
	    	condition=" or pensionno="+pensionNo;
	       }
			boolean arrearData=false;
			
			ArrayList OBList = new ArrayList();
			try {
			
				if(Integer.parseInt(findFromYear)>=2008){
					yearBreakMonthFlag = true;
					if(arrearsFlag==false){
						
						sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
							+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
							+ "from employee_pension_validate where rownum <=to_date('"
							+ toDate
							+ "', 'dd-mon-yyyy') -to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
							+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
							+ fromDate
							+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
							+ toDate
							+ "'))"
							+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
					}else{
						arrearDatesInfo=this.getArrearDate(con,fromDate,toDate,pensionNo);
						String[] arrearDts=arrearDatesInfo.split(",");
						arrearFromDate=arrearDts[0];
						arrearToDate=arrearDts[1];
						
						sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
							+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
							+ "from employee_pension_validate where rownum <=to_date('"
							+ toDate
							+ "', 'dd-mon-yyyy') -to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
							+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
							+ arrearFromDate
							+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='"
							+ arrearToDate
							+ "')"
							+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
					}
					
				}else{
					yearBreakMonthFlag = false;
					sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
						+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
						+ "from employee_pension_validate where rownum <=to_date('"
						+ toDate
						+ "', 'dd-mon-yyyy') -to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
						+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
						+ fromDate
						+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
						+ toDate
						+ "'))"
						+ " AND (("
						+ pfIDS
						+ ")"+condition+")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
				}
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				log.info("getForm7PensionInfo:======"+sqlQuery);
				while (rs.next()) {
					cardInfo = new EmployeePensionCardInfo();
					double total = 0.0;
					formatMnth = rs.getString("FORMATMONTHYEAR");
					if (rs.getString("MONTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("MONTHYEAR"), "dd-MMM-yyyy"));

						getMonth = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM");
						chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
						cardInfo.setShnMnthYear(formatMnth
								+ "/"
								+ commonUtil.converDBToAppFormat(cardInfo
										.getMonthyear(), "dd-MMM-yyyy", "MMM"));
						if (getMonth.toUpperCase().equals("APR")) {
							obFlag = false;
							getMonth = "";
							empCumlative = 0.0;
							aaiNetCumlative = 0.0;
							advancePFWPaid = 0.0;
							advPFDrawn = 0.0;
							totalAdvancePFWPaid = 0.0;
						}
						if (getMonth.toUpperCase().equals("MAR")) {
							cardInfo.setCbFlag("Y");
						} else {
							cardInfo.setCbFlag("N");
						}
					} else {
						if (rs.getString("EMPMNTHYEAR") != null) {
							cardInfo.setMonthyear(commonUtil.getDatetoString(rs
									.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));

						} else {
							cardInfo.setMonthyear("---");
						}

						getMonth = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM");
						chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM"));

						if (getMonth.toUpperCase().equals("APR")) {
							obFlag = false;
							getMonth = "";
							empCumlative = 0.0;
							aaiNetCumlative = 0.0;
							advancePFWPaid = 0.0;
							advPFDrawn = 0.0;
							totalAdvancePFWPaid = 0.0;
						}
						if (getMonth.toUpperCase().equals("MAR")) {
							cardInfo.setCbFlag("Y");
						} else {
							cardInfo.setCbFlag("N");
						}
						if (!cardInfo.getMonthyear().equals("---")) {
							cardInfo.setShnMnthYear(formatMnth
									+ "/"
									+ commonUtil.converDBToAppFormat(cardInfo
											.getMonthyear(), "dd-MMM-yyyy", "MMM"));
						}

					}
					if (obFlag == false) {
						OBList = this.getOBForPFCardReport(con, cardInfo
								.getMonthyear(), pensionNo);
						cardInfo.setObList(OBList);
						cardInfo.setObFlag("Y");
						obFlag = true;
						getMonth = "";
					} else {
						cardInfo.setObFlag("N");
					}
					if (rs.getString("ARREARAMOUNT") != null) {
						cardInfo.setDuepensionamount(rs.getString("ARREARAMOUNT"));
					} else {
						cardInfo.setDuepensionamount("0");
					}
					if (rs.getString("DUEEMOLUMENTS") != null) {
						cardInfo.setDueemoluments(rs.getString("DUEEMOLUMENTS"));
					} else {
						cardInfo.setDueemoluments("0");
					}
			
					if (rs.getString("EMOLUMENTMONTHS") != null) {
						cardInfo.setEmolumentMonths(rs.getString("EMOLUMENTMONTHS".trim()));
					} else {
						cardInfo.setEmolumentMonths("1");
					}
					if (rs.getString("EMOLUMENTS") != null) {
						cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
					} else {
						cardInfo.setEmoluments("0");
					}
				
					if(rs.getString("ARREARSMONTHS")!=null ){
						if(rs.getString("ARREARSMONTHS").equals("Y")){
							arrearData=true;
						}
					
						
					}
					String deputationflag="N";
					if (rs.getString("DEPUTATIONFLAG") != null) {
						deputationflag=rs.getString("DEPUTATIONFLAG");
					} 
						
					
					log.info("monthYear===========sss==========="+ cardInfo.getShnMnthYear()+"arrearData"+arrearData + "Emoluments"+ cardInfo.getEmoluments());
					calEmoluments = this.calWages(cardInfo.getEmoluments(),
							cardInfo.getMonthyear(), wetherOption.trim(), false,
							false,cardInfo.getEmolumentMonths());
					
					
					
					if (chkDecMnthYear.trim().equals("Dec-95")) {
						if (!calEmoluments.equals("")) {
							calEmoluments = Double.toString(Double
									.parseDouble(calEmoluments) / 2);
						}

					}
					log.info("monthYear======================"+ cardInfo.getShnMnthYear() + "calEmoluments"+ calEmoluments);
					if (rs.getString("EMPPFSTATUARY") != null) {
						cardInfo.setEmppfstatury(rs.getString("EMPPFSTATUARY"));
					} else {
						cardInfo.setEmppfstatury("0");
					}
					if (rs.getString("EMPVPF") != null) {
						cardInfo.setEmpvpf(rs.getString("EMPVPF"));
					} else {
						cardInfo.setEmpvpf("0");
					}
					if (rs.getString("CPF") != null) {
						cardInfo.setEmpCPF(rs.getString("CPF"));
					} else {
						cardInfo.setEmpCPF("0");
					}
				
				
					try {
						checkDate = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
						flag = false;
					} catch (InvalidDataException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
					if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
						flag = true;
					}
					
					
					transMntYear = Date.parse(cardInfo.getMonthyear());
					empRetriedDt = Date.parse(dateOfRetriment);

					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == empRetriedDt) {
						chkDOBFlag = true;
					}

					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					fpfFund=commonUtil.compareTwoDates(commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy"),"Jan-1996");
					
					if (yearBreakMonthFlag == false) {
						
						if (flag == false) {
							if (!cardInfo.getMonthyear().equals("-NA-")
									&& !dateOfRetriment.equals("")) {

								if (contrFlag != true) {
									pensionAsPerOption = this.pensionCalculation(
											cardInfo.getMonthyear(), cardInfo.getEmoluments(),
											wetherOption, region, rs
													.getString("emolumentmonths"));
									if (chkDOBFlag == true) {
										String[] dobList = dateOfBirth.split("-");
										days = dobList[0];
										getDaysBymonth = this
												.getNoOfDays(dateOfBirth);
										pensionAsPerOption = pensionAsPerOption
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth;
										retriredEmoluments = new Double(
												df1
														.format(Double
																.parseDouble(calEmoluments)
																* (Double
																		.parseDouble(days) - 1)
																/ getDaysBymonth))
												.doubleValue();
										calEmoluments = Double
												.toString(retriredEmoluments);
									}

								}else {
									pensionAsPerOption = 0;
									calEmoluments = "0";
								}
								cardInfo.setPensionContribution(Double
										.toString(pensionAsPerOption));
							} else {
								cardInfo.setPensionContribution("0");
							}
						}else{
							cardInfo.setPensionContribution("0");
						}
						if(deputationflag.equals("Y")){
							pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
							cardInfo.setPensionContribution(Double
									.toString(pensionAsPerOption));
						}
					} else {
						pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
						if (contrFlag != true) {
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
						
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}
						}else {
							if(arrearData==false){
								pensionAsPerOption = 0;
								calEmoluments = "0";
							}
							
	                     
						}
						cardInfo.setPensionContribution(Double
								.toString(pensionAsPerOption));
					}
					if(fpfFund==true){
						if (!chkDecMnthYear.trim().equals("Dec-95")) {
							cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
						}else{
							pensionAsPerOption = this.pensionFormsCalculation(
									cardInfo.getMonthyear(), cardInfo.getEmoluments(),
									wetherOption, region,true,false, rs
											.getString("emolumentmonths"));
							cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
						}
					}else{
						cardInfo.setFpfFund("0");
					}
					if (chkDecMnthYear.trim().equals("Apr-95")) {
						
						calEmoluments = "0";

					}
					if(arrearData==false){
						cardInfo.setEmoluments(calEmoluments);
					}else{
						cardInfo.setEmoluments(df.format(Math.round(pensionAsPerOption*100/8.33)));
					}
				

					log.info("Month Year=======Form-8========="+cardInfo.getMonthyear() + "flag" + flag + checkDate
							+ "Pension" + cardInfo.getPensionContribution()
							+ "calEmoluments" + cardInfo.getEmoluments());
				
				
					
					if(rs.getString("FORM7NARRATION")!=null){
						cardInfo.setForm7Narration(rs.getString("FORM7NARRATION"));
					}else{
						cardInfo.setForm7Narration("---");
					}
					if(rs.getString("EMOLUMENTMONTHS")!=null){
						cardInfo.setEmolumentMonths(rs.getString("EMOLUMENTMONTHS"));
					}else{
						cardInfo.setEmolumentMonths("1");
					}
					if (rs.getString("AIRPORTCODE") != null) {
						cardInfo.setStation(rs.getString("AIRPORTCODE"));
					} else {
						cardInfo.setStation("---");
					}
					arrearData=false;
					
					//new code snippet added as on Sep-02
					if (rs.getString("EMOLUMENTS") != null) {
						cardInfo.setOriginalEmoluments(rs.getString("EMOLUMENTS"));
					} else {
						cardInfo.setOriginalEmoluments("0");
					}
					pensionList.add(cardInfo);
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return pensionList;
	}
	 private Form8RemittanceBean getForm8RemInfo(Connection con,String fromDate, String toDate,
				String pfIDS, String wetherOption, String region, boolean formFlag,
				String dateOfRetriment, String dateOfBirth, String pensionNo,boolean arrearsFlag,String formTypeRevisedFlag) {
			Form8RemittanceBean remittanceBean = new Form8RemittanceBean();
			DecimalFormat df = new DecimalFormat("#########0.00");
			DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
			
			Statement st = null;
			ResultSet rs = null;
			EmployeePensionCardInfo cardInfo = null;
			ArrayList pensionList = new ArrayList();
			boolean flag = false;
			boolean contrFlag = false, chkDOBFlag = false;
			String checkDate = "", chkMnthYear = "Apr-1995", formatMnth = "", chkDecMnthYear = "", findFromYear = "", findToYear = "";
			String monthYear = "", days = "",month="", getMonth = "",disYear="", sqlQuery = "", calEmoluments = "",arrearFromDate="01-Sep-2009",arrearToDate="",arrearDatesInfo="";
			int getDaysBymonth = 0;
			long transMntYear = 0, empRetriedDt = 0;
			String aprDispMnth = "", mayDispMnth = "", junDispMnth = "", julDispMnth = "", augDispMnth = "", sepDispMnth = "", octDispMnth = "", novDispMnth = "", decDispMnth = "", janDispMnth = "", febDispMnth = "", marDispMnth = "";
			double totalAdvancePFWPaid = 0, loanPFWPaid = 0, advancePFWPaid = 0, empNet = 0, aaiNet = 0, advPFDrawn = 0, empCumlative = 0.0, aaiPF = 0.0, aaiNetCumlative = 0.0;
			double pensionAsPerOption = 0.0, retriredEmoluments = 0.0;
			boolean yearBreakMonthFlag=false;
			boolean obFlag = false,fpfFund=false;
			double pensionVal=0.0,totalContribution=0.00;
			double totalAprContribution = 0.0,totalMayContribution = 0.0,totalJunContribution = 0.0,totalJulContribution = 0.0;
			double totalAugContribution = 0.0,totalSepContribution = 0.0,totalOctContribution = 0.0,totalNovContribution = 0.0,totalDecContribution = 0.0,totalJanContribution = 0.0,totalFebContribution = 0.0,totalMarContribution = 0.0;
			double totalAprDueContribution = 0.0,totalMayDueContribution = 0.0,totalJunDueContribution = 0.0,totalJulDueContribution = 0.0;
			double totalAugDueContribution = 0.0,totalSepDueContribution = 0.0,totalOctDueContribution = 0.0,totalNovDueContribution = 0.0,totalDecDueContribution = 0.0,totalJanDueContribution = 0.0,totalFebDueContribution = 0.0,totalMarDueContribution = 0.0;
			try {
				findFromYear = commonUtil.converDBToAppFormat(fromDate,
						"dd-MMM-yyyy", "yyyy");
				findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
						"yyyy");
			} catch (InvalidDataException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String condition="";
			if(pensionNo!="" && !pensionNo.equals("")){
	    	condition=" or pensionno="+pensionNo;
	       }
			boolean arrearData=false;
			log.info("FinanceReportDAO::getEmployeePensionCard" + sqlQuery);
			ArrayList OBList = new ArrayList();
			try {
				
				if(Integer.parseInt(findFromYear)>=2008){
					yearBreakMonthFlag = true;
					if(arrearsFlag==false){
						
						sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
							+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
							+ "from employee_pension_validate where rownum <=to_date('"
							+ toDate
							+ "', 'dd-mon-yyyy') -to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTHS,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
							+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
							+ fromDate
							+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
							+ toDate
							+ "'))"
							+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
					}else{
						arrearDatesInfo=this.getArrearDate(con,fromDate,toDate,pensionNo);
						String[] arrearDts=arrearDatesInfo.split(",");
						arrearFromDate=arrearDts[0];
						arrearToDate=arrearDts[1];
						
						sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
							+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
							+ "from employee_pension_validate where rownum <=to_date('"
							+ toDate
							+ "', 'dd-mon-yyyy') -to_date('"
							+ fromDate
							+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTHS,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
							+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
							+ arrearFromDate
							+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<='"
							+ arrearToDate
							+ "')"
							+ " AND PENSIONNO="+pensionNo+") emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
					}
					
				}else{
					yearBreakMonthFlag = false;
					sqlQuery = "SELECT TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||SUBSTR(empdt.MONYEAR, 4, 4)) AS EMPMNTHYEAR,to_char(add_months(TO_DATE('01-' || SUBSTR(empdt.MONYEAR, 0, 3) || '-' ||"
						+ "SUBSTR(empdt.MONYEAR, 4, 4)),-1),'Mon') as FORMATMONTHYEAR,nvl(emp.EMOLUMENTMONTHS, 1) AS EMOLUMENTMONTHS,emp.* from (select distinct to_char(to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + rownum - 1,'MONYYYY') monyear "
						+ "from employee_pension_validate where rownum <=to_date('"
						+ toDate
						+ "', 'dd-mon-yyyy') -to_date('"
						+ fromDate
						+ "', 'dd-mon-yyyy') + 1) empdt,(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTHS,to_char(MONTHYEAR, 'MONYYYY') empmonyear,EMOLUMENTS AS EMOLUMENTS,"
						+ "round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
						+ "ROUND(REFADV) AS REFADV,AIRPORTCODE,EMPFLAG,FORM7NARRATION,NVL(EMOLUMENTMONTHS,'1') AS EMOLUMENTMONTHS,PENSIONCONTRI,PF,ARREARFLAG AS ARREARSMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,NVL(ARREARAMOUNT,'0.00') AS ARREARAMOUNT,NVL(DUEEMOLUMENTS,'0.00') AS DUEEMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE  WHERE ARREARSBREAKUP='"+formTypeRevisedFlag+"' and empflag='Y' and (to_date(to_char(monthyear, 'dd-Mon-yyyy')) >= '"
						+ fromDate
						+ "' and to_date(to_char(monthyear,'dd-Mon-yyyy'))<=last_day('"
						+ toDate
						+ "'))"
						+ " AND (("
						+ pfIDS
						+ ")"+condition+")) emp where empdt.monyear =  emp.empmonyear  (+) ORDER BY TO_DATE(EMPMNTHYEAR)";
				}
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				log.info("getForm7PensionInfo:======"+sqlQuery);
				while (rs.next()) {
					cardInfo = new EmployeePensionCardInfo();
					double total = 0.0;
					formatMnth = rs.getString("FORMATMONTHYEAR");
					if (rs.getString("MONTHS") != null) {
						month = rs.getString("MONTHS");
					}
					if (rs.getString("MNTYEAR") != null) {
						disYear = rs.getString("MNTYEAR");
					} else {
						disYear = "---";
					}
					if (rs.getString("MONTHYEAR") != null) {
						cardInfo.setMonthyear(commonUtil.getDatetoString(rs
								.getDate("MONTHYEAR"), "dd-MMM-yyyy"));

						getMonth = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM");
						chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
						cardInfo.setShnMnthYear(formatMnth
								+ "/"
								+ commonUtil.converDBToAppFormat(cardInfo
										.getMonthyear(), "dd-MMM-yyyy", "MMM"));
						if (getMonth.toUpperCase().equals("APR")) {
							obFlag = false;
							getMonth = "";
							empCumlative = 0.0;
							aaiNetCumlative = 0.0;
							advancePFWPaid = 0.0;
							advPFDrawn = 0.0;
							totalAdvancePFWPaid = 0.0;
						}
						if (getMonth.toUpperCase().equals("MAR")) {
							cardInfo.setCbFlag("Y");
						} else {
							cardInfo.setCbFlag("N");
						}
					} else {
						if (rs.getString("EMPMNTHYEAR") != null) {
							cardInfo.setMonthyear(commonUtil.getDatetoString(rs
									.getDate("EMPMNTHYEAR"), "dd-MMM-yyyy"));

						} else {
							cardInfo.setMonthyear("---");
						}

						getMonth = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM");
						chkDecMnthYear = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yy");
						cardInfo.setShnMnthYear(commonUtil.converDBToAppFormat(
								cardInfo.getMonthyear(), "dd-MMM-yyyy", "MMM"));

						if (getMonth.toUpperCase().equals("APR")) {
							obFlag = false;
							getMonth = "";
							empCumlative = 0.0;
							aaiNetCumlative = 0.0;
							advancePFWPaid = 0.0;
							advPFDrawn = 0.0;
							totalAdvancePFWPaid = 0.0;
						}
						if (getMonth.toUpperCase().equals("MAR")) {
							cardInfo.setCbFlag("Y");
						} else {
							cardInfo.setCbFlag("N");
						}
						if (!cardInfo.getMonthyear().equals("---")) {
							cardInfo.setShnMnthYear(formatMnth
									+ "/"
									+ commonUtil.converDBToAppFormat(cardInfo
											.getMonthyear(), "dd-MMM-yyyy", "MMM"));
						}

					}
				
					if (rs.getString("ARREARAMOUNT") != null) {
						cardInfo.setDuepensionamount(rs.getString("ARREARAMOUNT"));
					} else {
						cardInfo.setDuepensionamount("0");
					}
					if (rs.getString("DUEEMOLUMENTS") != null) {
						cardInfo.setDueemoluments(rs.getString("DUEEMOLUMENTS"));
					} else {
						cardInfo.setDueemoluments("0");
					}
			
					if (rs.getString("EMOLUMENTMONTHS") != null) {
						cardInfo.setEmolumentMonths(rs.getString("EMOLUMENTMONTHS".trim()));
					} else {
						cardInfo.setEmolumentMonths("1");
					}
					if (rs.getString("EMOLUMENTS") != null) {
						cardInfo.setEmoluments(rs.getString("EMOLUMENTS"));
					} else {
						cardInfo.setEmoluments("0");
					}
					if(rs.getString("ARREARSMONTHS")!=null ){
						if(rs.getString("ARREARSMONTHS").equals("Y")){
							arrearData=true;
						}
					
						
					}
					String deputationflag="N";
					if (rs.getString("DEPUTATIONFLAG") != null) {
						deputationflag=rs.getString("DEPUTATIONFLAG");
					} 
						
					
					log.info("monthYear===========sss==========="+ cardInfo.getShnMnthYear()+"arrearData"+arrearData + "Emoluments"+ cardInfo.getEmoluments());
					calEmoluments = this.calWages(cardInfo.getEmoluments(),
							cardInfo.getMonthyear(), wetherOption.trim(), false,
							false,cardInfo.getEmolumentMonths());
					
					
					
					if (chkDecMnthYear.trim().equals("Dec-95")) {
						if (!calEmoluments.equals("")) {
							calEmoluments = Double.toString(Double
									.parseDouble(calEmoluments) / 2);
						}

					}
					log.info("monthYear======================"+ cardInfo.getShnMnthYear() + "calEmoluments"+ calEmoluments);
					

				
					try {
						checkDate = commonUtil.converDBToAppFormat(cardInfo
								.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy");
						flag = false;
					} catch (InvalidDataException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
					if (checkDate.toLowerCase().equals(chkMnthYear.toLowerCase())) {
						flag = true;
					}

					
					transMntYear = Date.parse(cardInfo.getMonthyear());
					empRetriedDt = Date.parse(dateOfRetriment);

					if (transMntYear > empRetriedDt) {
						contrFlag = true;
					} else if (transMntYear == empRetriedDt) {
						chkDOBFlag = true;
					}

					log.info("transMntYear" + transMntYear + "empRetriedDt"
							+ empRetriedDt);
					log.info("contrFlag" + contrFlag + "chkDOBFlag"
							+ chkDOBFlag);
					fpfFund=commonUtil.compareTwoDates(commonUtil.converDBToAppFormat(cardInfo
							.getMonthyear(), "dd-MMM-yyyy", "MMM-yyyy"),"Jan-1996");
					if (yearBreakMonthFlag == false) {
						
						if (flag == false) {
							if (!cardInfo.getMonthyear().equals("-NA-")
									&& !dateOfRetriment.equals("")) {

								if (contrFlag != true) {
									pensionAsPerOption = this.pensionCalculation(
											cardInfo.getMonthyear(), cardInfo.getEmoluments(),
											wetherOption, region, rs
													.getString("emolumentmonths"));
									if (chkDOBFlag == true) {
										String[] dobList = dateOfBirth.split("-");
										days = dobList[0];
										getDaysBymonth = this
												.getNoOfDays(dateOfBirth);
										pensionAsPerOption = pensionAsPerOption
												* (Double.parseDouble(days) - 1)
												/ getDaysBymonth;
										retriredEmoluments = new Double(
												df1
														.format(Double
																.parseDouble(calEmoluments)
																* (Double
																		.parseDouble(days) - 1)
																/ getDaysBymonth))
												.doubleValue();
										calEmoluments = Double
												.toString(retriredEmoluments);
									}

								}else {
									pensionAsPerOption = 0;
									calEmoluments = "0";
								}
								cardInfo.setPensionContribution(Double
										.toString(pensionAsPerOption));
							} else {
								cardInfo.setPensionContribution("0");
							}
						}else{
							cardInfo.setPensionContribution("0");
						}
						if(deputationflag.equals("Y")){
							pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
							cardInfo.setPensionContribution(Double
									.toString(pensionAsPerOption));
						}
					} else {
						pensionAsPerOption = rs.getDouble("PENSIONCONTRI");
						if (contrFlag != true) {
						if (chkDOBFlag == true) {
							String[] dobList = dateOfBirth.split("-");
							days = dobList[0];
						
							getDaysBymonth = this.getNoOfDays(dateOfBirth);
							
							retriredEmoluments = new Double(df1.format(Double
									.parseDouble(calEmoluments)
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth)).doubleValue();
							calEmoluments = Double.toString(retriredEmoluments);
						}
						}else {
							if(arrearData==false){
								pensionAsPerOption = 0;
								calEmoluments = "0";
							}
							
	                     
						}
						cardInfo.setPensionContribution(Double
								.toString(pensionAsPerOption));
					}
					if(fpfFund==true){
						if (!chkDecMnthYear.trim().equals("Dec-95")) {
							cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
						}else{
							pensionAsPerOption = this.pensionFormsCalculation(
									cardInfo.getMonthyear(), cardInfo.getEmoluments(),
									wetherOption, region,true,false, rs
											.getString("emolumentmonths"));
							cardInfo.setFpfFund(df.format(Math.round(pensionAsPerOption/2)));
						}
					}else{
						cardInfo.setFpfFund("0");
					}
					log.info("Month Year================"+cardInfo.getMonthyear() + "flag" + flag + checkDate
							+ "Pension" + cardInfo.getPensionContribution()
							+ "calEmoluments" + calEmoluments);
					if(arrearData==false){
						cardInfo.setEmoluments(calEmoluments);
					}else{
						cardInfo.setEmoluments(df.format(Math.round(pensionAsPerOption*100/8.33)));
					}
					
					contrFlag = false;
					chkDOBFlag = false;
				
					
					if(rs.getString("FORM7NARRATION")!=null){
						cardInfo.setForm7Narration(rs.getString("FORM7NARRATION"));
					}else{
						cardInfo.setForm7Narration("---");
					}
					
					if (rs.getString("AIRPORTCODE") != null) {
						cardInfo.setStation(rs.getString("AIRPORTCODE"));
					} else {
						cardInfo.setStation("---");
					}
					arrearData=false;
					
					//new code snippet added as on Sep-02
					if (rs.getString("EMOLUMENTS") != null) {
						cardInfo.setOriginalEmoluments(rs.getString("EMOLUMENTS"));
					} else {
						cardInfo.setOriginalEmoluments("0");
					}
					

						if (month.toUpperCase().equals("APR")) {
							aprDispMnth = disYear;
							totalAprDueContribution = totalAprDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalAprContribution = totalAprContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
						
						}
						if (month.toUpperCase().equals("MAY")) {
							mayDispMnth = disYear;
							totalMayDueContribution = totalMayDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalMayContribution = totalMayContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
							
						}
						if (month.toUpperCase().equals("JUN")) {
							junDispMnth = disYear;
							totalJunDueContribution = totalJunDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalJunContribution = totalJunContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
							
						}
						if (month.toUpperCase().equals("JUL")) {
							julDispMnth = disYear;
							totalJulDueContribution = totalJulDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalJulContribution = totalJulContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
							
						}
						if (month.toUpperCase().equals("AUG")) {
							augDispMnth = disYear;
							totalAugDueContribution = totalAugDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalAugContribution = totalAugContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
							
						}
						if (month.toUpperCase().equals("SEP")) {
							sepDispMnth = disYear;
							totalSepDueContribution = totalSepDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalSepContribution = totalSepContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
						
						}
						if (month.toUpperCase().equals("OCT")) {
							octDispMnth = disYear;
							totalOctDueContribution = totalOctDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalOctContribution = totalOctContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
						
						}
						if (month.toUpperCase().equals("NOV")) {
							novDispMnth = disYear;
							totalNovDueContribution = totalNovDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalNovContribution = totalNovContribution
									+ Double.parseDouble(cardInfo.getPensionContribution());
							
						}
						if (month.toUpperCase().equals("DEC")) {
							decDispMnth = disYear;
							totalDecDueContribution = totalDecDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalDecContribution = totalDecContribution
									+ Math.round(Double.parseDouble(cardInfo.getPensionContribution()));
							
						}
						if (month.toUpperCase().equals("JAN")) {
							janDispMnth = disYear;
							totalJanDueContribution = totalJanDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalJanContribution = totalJanContribution
									+ Math.round(Double.parseDouble(cardInfo.getPensionContribution()));;
							
						}
						if (month.toUpperCase().equals("FEB")) {
							febDispMnth = disYear;
							totalFebDueContribution = totalFebDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalFebContribution = totalFebContribution
									+ Math.round(Double.parseDouble(cardInfo.getPensionContribution()));
							
						}
						if (month.toUpperCase().equals("MAR")) {
							marDispMnth = disYear;
							totalMarDueContribution = totalMarDueContribution
							+ Double.parseDouble(cardInfo.getDuepensionamount());
							totalMarContribution = totalMarContribution
									+ Math.round(Double.parseDouble(cardInfo.getPensionContribution()));
							
						}
						
				}
				totalContribution = totalAprContribution + totalMayContribution
				+ totalJunContribution + totalJulContribution
				+ totalAugContribution + totalSepContribution;
				totalContribution = totalContribution + totalOctContribution
				+ totalNovContribution + totalDecContribution;
				totalContribution = totalContribution + totalJanContribution
				+ totalFebContribution + totalMarContribution;
				
				remittanceBean.setTotalAprDueContribution(new Double(df.format(Math
						.round(totalAprDueContribution))).doubleValue());
				remittanceBean.setTotalMayDueContribution(new Double(df.format(Math
						.round(totalMayDueContribution))).doubleValue());
				remittanceBean.setTotalJunDueContribution(new Double(df.format(Math
						.round(totalJunDueContribution))).doubleValue());
				remittanceBean.setTotalJulDueContribution(new Double(df.format(Math
						.round(totalJulDueContribution))).doubleValue());
				remittanceBean.setTotalAugDueContribution(new Double(df.format(Math
						.round(totalAugDueContribution))).doubleValue());
				remittanceBean.setTotalSepDueContribution(new Double(df.format(Math
						.round(totalSepDueContribution))).doubleValue());
				remittanceBean.setTotalOctDueContribution(new Double(df.format(Math
						.round(totalOctDueContribution))).doubleValue());
				remittanceBean.setTotalNovDueContribution(new Double(df.format(Math
						.round(totalNovDueContribution))).doubleValue());
				remittanceBean.setTotalDecDueContribution(new Double(df.format(Math
						.round(totalDecDueContribution))).doubleValue());
				remittanceBean.setTotalJanDueContribution(new Double(df.format(Math
						.round(totalJanDueContribution))).doubleValue());
				remittanceBean.setTotalFebDueContribution(new Double(df.format(Math
						.round(totalFebDueContribution))).doubleValue());
				remittanceBean.setTotalMarDueContribution(new Double(df.format(Math
						.round(totalMarDueContribution))).doubleValue());
				
				remittanceBean.setTotalAprContribution(new Double(df.format(Math
						.round(totalAprContribution))).doubleValue());
				remittanceBean.setAprMnth(aprDispMnth);
			
				remittanceBean.setTotalMayContribution(new Double(df.format(Math
						.round(totalMayContribution))).doubleValue());
				remittanceBean.setMayMnth(mayDispMnth);
				remittanceBean.setTotalJunContribution(new Double(df.format(Math
						.round(totalJunContribution))).doubleValue());
				remittanceBean.setJunMnth(junDispMnth);
				remittanceBean.setTotalJulContribution(new Double(df.format(Math
						.round(totalJulContribution))).doubleValue());
				remittanceBean.setJulMnth(julDispMnth);
				remittanceBean.setTotalAugContribution(new Double(df.format(Math
						.round(totalAugContribution))).doubleValue());
				remittanceBean.setAugMnth(augDispMnth);
				remittanceBean.setTotalSepContribution(new Double(df.format(Math
						.round(totalSepContribution))).doubleValue());
				remittanceBean.setSepMnth(sepDispMnth);
				remittanceBean.setTotalOctContribution(new Double(df.format(Math
						.round(totalOctContribution))).doubleValue());
				remittanceBean.setOctMnth(octDispMnth);
				remittanceBean.setTotalNovContribution(new Double(df.format(Math
						.round(totalNovContribution))).doubleValue());
				remittanceBean.setNovMnth(novDispMnth);
				remittanceBean.setTotalDecContribution(new Double(df.format(Math
						.round(totalDecContribution))).doubleValue());
				remittanceBean.setDecMnth(decDispMnth);
				remittanceBean.setTotalJanContribution(new Double(df.format(Math
						.round(totalJanContribution))).doubleValue());
				remittanceBean.setJanMnth(janDispMnth);
				remittanceBean.setTotalFebContribution(new Double(df.format(Math
						.round(totalFebContribution))).doubleValue());
				remittanceBean.setFebMnth(febDispMnth);
				remittanceBean.setTotalMarContribution(new Double(df.format(Math
						.round(totalMarContribution))).doubleValue());
				remittanceBean.setMarMnth(marDispMnth);
				remittanceBean.setTotalContr(df.format(Math
						.round(totalContribution)));
				log.info("Remittance Bean"+remittanceBean.getTotalContr());
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return remittanceBean;
	}		
		public String getArrearData(Connection con,String fromYear,String toYear,String pensionNo){
			int noOfMonths=0;
			PreparedStatement pst = null;
			ResultSet rs = null;
			String sqlQuery = "", finalFromDate = "",arrearDate="",arrearInfo="";
			double arrearAmount=0.00,arrearContr=0.00;
			
			log.info("::getArrearInfo="+fromYear+"toYear"+toYear+"pensionNo"+pensionNo);
			sqlQuery = "SELECT NVL(ARREARAMT,'0.00') AS ARREARAMT,NVL(AAICONTRAMT,'0.00') AS AAICONTRAMT,TO_CHAR(ARREARDATE,'dd-Mon-yyyy') as ARREARDATE,round(months_between(LAST_DAY(?),ARREARDATE)) AS MONTHS FROM EMPLOYEE_PENSION_ARREAR WHERE  (ARREARDATE BETWEEN ? AND ?) AND  PENSIONNO=?";
			log.info("FinanceReportDAO:::getArrearInfo=================================="+sqlQuery);
			try {

				pst = con.prepareStatement(sqlQuery);
				pst.setDate(1,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(toYear,"dd-MMM-yyyy","yyyy-MM-dd")));
				pst.setDate(2,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy","yyyy-MM-dd")));
				pst.setDate(3,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(toYear,"dd-MMM-yyyy","yyyy-MM-dd")));
				pst.setString(4,pensionNo);
				rs = pst.executeQuery();
				if (rs.next()) {
					noOfMonths=rs.getInt("MONTHS");
					arrearAmount=rs.getDouble("ARREARAMT");
					arrearContr=rs.getDouble("AAICONTRAMT");
					arrearDate=rs.getString("ARREARDATE");
				}else{
					noOfMonths=0;
					arrearAmount=0.00;
					arrearContr=0.00;
					arrearDate="NA";
				}
				arrearInfo=arrearDate+","+noOfMonths+","+arrearAmount+","+arrearContr;
				log.info("FinanceReportDAO:::getArrearInfo=================================arrearInfo"+arrearInfo);
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				if (pst != null) {
					try {
						pst.close();
						pst = null;
					} catch (SQLException se) {
						log.printStackTrace(se);
					}
				}
				commonDB.closeConnection(null, null, rs);
			}
			return arrearInfo;
		}
		public String getSanctionOrderInfo(Connection con,String fromYear,String toYear,String pensionNo){
			PreparedStatement pst = null;
			ResultSet rs = null;
			String sqlQuery = "", finalFromDate = "",arrearDate="",sanctionOrderInfo="";
			String pensionno="",sancationNo="",sanctionDate="",seperationreason="",verfiedby="",arrearType="";
			
			log.info("::getArrearInfo="+fromYear+"toYear"+toYear+"pensionNo"+pensionNo);
			sqlQuery = "SELECT PENSIONNO,NSSANCTIONNO,SEPERATIONRESAON,TO_CHAR(NSSANCTIONEDDT,'dd-Mon-yyyy') AS NSSANCTIONEDDT,VERIFIEDBY,NSTYPE FROM EMPLOYEE_ADVANCE_NOTEPARAM WHERE  ((VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED') or (VERIFIEDBY='FINANCE,SRMGRREC,DGMREC,APPROVED')) AND DELETEFLAG='N' AND (NSSANCTIONEDDT BETWEEN ? AND ?) AND  PENSIONNO=?";
			log.info("FinanceReportDAO:::getArrearInfo=================================="+sqlQuery);
			try {

				pst = con.prepareStatement(sqlQuery);
				pst.setDate(1,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy","yyyy-MM-dd")));
				pst.setDate(2,java.sql.Date.valueOf(commonUtil.converDBToAppFormat(toYear,"dd-MMM-yyyy","yyyy-MM-dd")));
				pst.setString(3,pensionNo);
				rs = pst.executeQuery();
				if (rs.next()) {
					pensionno=rs.getString("PENSIONNO");
					sancationNo=rs.getString("NSSANCTIONNO");
					sanctionDate=rs.getString("NSSANCTIONEDDT");
					seperationreason=rs.getString("SEPERATIONRESAON");
					verfiedby=rs.getString("VERIFIEDBY");
					arrearType=rs.getString("NSTYPE");
				}else{
					pensionno="";
					sancationNo="";
					sanctionDate="";
					seperationreason="";
					verfiedby="";
					arrearType="";
				}
				sanctionOrderInfo=sancationNo+","+sanctionDate;
				log.info("FinanceReportDAO:::getArrearInfo=================================arrearInfo"+sanctionOrderInfo);
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				if (pst != null) {
					try {
						pst.close();
						pst = null;
					} catch (SQLException se) {
						log.printStackTrace(se);
					}
				}
				commonDB.closeConnection(null, null, rs);
			}
			return sanctionOrderInfo;
		}	
		public ArrayList getNomineeList(Connection con,String pensionno){
			Statement st = null;
			ResultSet rs = null;
			ArrayList nomineeList=new ArrayList();
			String selectQry="";
			NomineeBean nomineeBean=null;
			selectQry="SELECT SRNO,NOMINEENAME,NOMINEEDOB,GAURDIANADDRESS,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,TOTALSHARE FROM EMPLOYEE_NOMINEE_DTLS WHERE EMPFLAG='Y' AND PENSIONNO="+pensionno;
			log.info("getNomineeList::selectQry"+selectQry);
			try{
				con=commonDB.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(selectQry);
				while(rs.next()){
					nomineeBean=new NomineeBean();
					   if (rs.getString("NOMINEENAME") != null) {
						   nomineeBean.setNomineeName(rs.getString("NOMINEENAME"));
		 	            } else {
		 	            	nomineeBean.setNomineeName("");
		 	            }
		 	            if (rs.getString("NOMINEEADDRESS") != null) {
		 	            	nomineeBean.setNomineeAddress(rs.getString("NOMINEEADDRESS"));
		 	            } else {
		 	            	nomineeBean.setNomineeAddress("");
		 	            }
		 	            if (rs.getString("NOMINEEDOB") != null) {

		 	            	nomineeBean.setNomineeDob(rs.getString("NOMINEEDOB"));
		 	            } else {
		 	            	nomineeBean.setNomineeDob("");
		 	            }
		 	            if (rs.getString("NOMINEERELATION") != null) {
		 	            	nomineeBean.setNomineeRelation(rs.getString("NOMINEERELATION"));
		 	            } else {
		 	            	nomineeBean.setNomineeRelation("");
		 	            }
		 	            if (rs.getString("NAMEOFGUARDIAN") != null) {
		 	            	nomineeBean.setNameOfGuardian(rs.getString("NAMEOFGUARDIAN"));

		 	            } else {
		 	            	nomineeBean.setNameOfGuardian("");
		 	            }
		 	            if (rs.getString("GAURDIANADDRESS") != null) {
		 	            	nomineeBean.setGaurdianAddress(rs.getString("GAURDIANADDRESS"));
		 	               
		 	            } else {
		 	            	nomineeBean.setGaurdianAddress("");
		 	            }
		 	            if (rs.getString("TOTALSHARE") != null) {
		 	            	nomineeBean.setTotalShare(rs.getString("TOTALSHARE"));
		 	            } else {
		 	            	nomineeBean.setTotalShare("");
		 	            }
		 	           
		 	            if (rs.getString("SRNO") != null) {
		 	            	nomineeBean.setSrno(rs.getString("SRNO"));
		 	            } else {
		 	            	nomineeBean.setSrno("");
		 	            }
		 	           nomineeList.add(nomineeBean);
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			
			return nomineeList;
		
	}
}
		public ArrayList getStatementOfWagePension(String range, String region,
				String selectedYear,String month,String sortedColumn, String pensionno,
				String airportcode) {
			log.info("FinancialReportDAO::empPFCardReportPrint");
			String fromYear = "", toYear = "", dateOfRetriment = "";
			Connection con = null;
			if (!selectedYear.equals("NO-SELECT") && month.equals("NO-SELECT")) {
				fromYear = "01-Apr-" + selectedYear;
				int toSelectYear = 0;
				toSelectYear = Integer.parseInt(selectedYear) + 1;
				toYear = "01-Mar-" + toSelectYear;
			}else if(!selectedYear.equals("NO-SELECT") && !month.equals("NO-SELECT")){
				fromYear = "01-"+month+"-" + selectedYear;
				toYear=fromYear;
			} else {
				fromYear = "";
				toYear = "";
			}
			log.info("getStatementOfWagePension::fromYear"+fromYear+"toYear"+toYear);
			int formFrmYear = 0, formToYear = 0, finalSttlementDtYear = 0, formMonthYear = 0;
			if(!selectedYear.equals("NO-SELECT")){
				try {
					formFrmYear = Integer.parseInt(commonUtil.converDBToAppFormat(
							fromYear, "dd-MMM-yyyy", "yyyy"));
					formToYear = Integer.parseInt(commonUtil.converDBToAppFormat(
							toYear, "dd-MMM-yyyy", "yyyy"));

				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ArrayList empDataList = new ArrayList();
			EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
			EmployeeCardReportInfo cardInfo = null;
			ArrayList list = new ArrayList();
		
			String appEmpNameQry = "", finalSettlementDate = "",fromDate="",toDate="";
			ArrayList cardList = new ArrayList();
			int arrerMonths=0;
			boolean finalStFlag = false;
			try {
				con = commonDB.getConnection();
				empDataList = this.getPersonalInfo(con, range, region,
						airportcode,fromYear,toYear,sortedColumn, pensionno);

				for (int i = 0; i < empDataList.size(); i++) {
					cardInfo = new EmployeeCardReportInfo();
					personalInfo = (EmployeePersonalInfo) empDataList.get(i);
					try {
						dateOfRetriment = this.getRetriedDate(personalInfo
								.getDateOfBirth());
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						log.printStackTrace(e);
					}
					fromDate=personalInfo.getSeperationFromDate();
					fromDate="01"+fromDate.substring(2,fromDate.length());
					toDate=personalInfo.getSeperationDate();
					log.info("FincialReportDAO:::getStatementOfWagePension:Final Settlement Date"+personalInfo.getFinalSettlementDate()+"Resttlement Date"+personalInfo.getResttlmentDate());
					list = this.getForm10DPensionInfo(con,fromDate,toDate,personalInfo.getPfIDString(),personalInfo.getWetherOption().trim(),dateOfRetriment,personalInfo.getDateOfBirth(),personalInfo.getOldPensionNo(),true);
					log.info("getStatementOfWagePension====Final Settlement Date"+personalInfo.getFinalSettlementDate()+"fromYear"+fromYear+"NO.Of Months"+cardInfo.getNoOfMonths()+"arrerMonths======"+arrerMonths);
					cardInfo.setPersonalInfo(personalInfo);
					cardInfo.setPensionCardList(list);
					cardList.add(cardInfo);
				}

			} catch (Exception se) {
				log.printStackTrace(se);
			} finally {
				commonDB.closeConnection(con, null, null);
			}

			return cardList;
		}
		public ArrayList getPersonalInfo(Connection con,String range,String region,String airportcode,String fromYear,String toYear,String sortingOrder,String pensionNo){
			Statement st = null;
			ResultSet rs = null;
			String sqlSelQuery="",pfidWithRegion = "", findFromYear="",pensionAppednQry = ""; 
			ArrayList list=new ArrayList();
			EmployeePersonalInfo personalInfo=null;
			if(!fromYear.equals("")){
				try{
					findFromYear = commonUtil.converDBToAppFormat(fromYear,
							"dd-MMM-yyyy", "yyyy");
					
					} catch (InvalidDataException e2) {
					// yy
					log.printStackTrace(e2);
					}
			}else{
				findFromYear="2008";
			}
			
			try {
				
				st = con.createStatement();
				sqlSelQuery=this.buildQueryPersonalFormPFID(range,region,airportcode,fromYear,toYear,sortingOrder,pensionNo);
				log.info("PersonalDAO:getPersonalInfo"+sqlSelQuery);
				st = con.createStatement();
				rs=st.executeQuery(sqlSelQuery);
				while(rs.next()){
					personalInfo=new EmployeePersonalInfo();
					personalInfo=commonDAO.loadPersonalInfo(rs);
					if(Integer.parseInt(findFromYear)>2008){
						personalInfo.setPfIDString("");
					}else{
						pfidWithRegion = this.getEmployeeMappingPFInfo(con, personalInfo
								.getPensionNo(), personalInfo.getCpfAccno(), personalInfo.getRegion());
						if(!pfidWithRegion.equals("---")){
							String[] pfIDLists = pfidWithRegion.split("=");
							personalInfo.setPfIDString(this.preparedCPFString(pfIDLists));
							
						}
						
					}
					if (rs.getString("FINALSETTLMENTDT") != null) {
						personalInfo.setFinalSettlementDate(commonUtil.converDBToAppFormat(rs
								.getDate("FINALSETTLMENTDT")));
					} else {
						personalInfo.setFinalSettlementDate("---");
					}
					if (rs.getString("DATEOFENTITLE") != null) {
						personalInfo.setDateOfEntitle(rs.getString("DATEOFENTITLE"));
					} else {
						personalInfo.setDateOfEntitle("01-Apr-1995");
					}
					if (rs.getString("PERMANENTADDRESS") != null) {
						personalInfo.setPerAddress(rs.getString("PERMANENTADDRESS"));
					} else {
						personalInfo.setPerAddress("");
					}
					if (rs.getString("TEMPORATYADDRESS") != null) {
						personalInfo.setTempAddress(rs.getString("TEMPORATYADDRESS"));
					} else {
						personalInfo.setTempAddress("");
					}
					if (rs.getString("PHONENUMBER") != null) {
						personalInfo.setPhoneNumber(rs.getString("PHONENUMBER"));
					} else {
						personalInfo.setPhoneNumber("");
					}
					if (rs.getString("NATIONALITY") != null) {
						personalInfo.setNationality(rs.getString("NATIONALITY"));
					} else {
						personalInfo.setNationality("");
					}
					if (rs.getString("HEIGHT") != null) {
						personalInfo.setHeightWithInches(StringUtility.replace(rs.getString("HEIGHT").toCharArray(),".".toCharArray(),"'").toString());
					} else {
						personalInfo.setHeightWithInches("");
					}
					if(rs.getString("SEPERATION_FROMDATE") != null){
						personalInfo.setSeperationFromDate(rs.getString("SEPERATION_FROMDATE"));
					}else{
						personalInfo.setSeperationFromDate("---");
					}
					list.add(personalInfo);
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return list;
		}
		public String buildQueryPersonalFormPFID(String range,String region, String airportcode, String fromYear,String toYear,
				String sortingOrder,String pensionno) {
			log
					.info("FinancialReportDAO::buildQueryPersonalFormPFID-- Entering Method");
			String dataString = "";
			boolean chkflag = false;
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "", orderBy = "", sqlQuery = "";
			sqlQuery = "SELECT CPFACNO, REFPENSIONNUMBER   , AIRPORTSERIALNUMBER, EMPLOYEENO   , EMPLOYEENAME , DESEGNATION  , EMP_LEVEL    , " +
			"DATEOFBIRTH   , DATEOFJOINING , DATEOFSEPERATION_REASON    , DATEOFSEPERATION_DATE      , WHETHER_FORM1_NOM_RECEIVED , AIRPORTCODE, GENDER , " +
			"FHNAME , MARITALSTATUS   , PERMANENTADDRESS, TEMPORATYADDRESS, WETHEROPTION    , SETDATEOFANNUATION, EMPFLAG    , OTHERREASON, DIVISION   , DEPARTMENT , EMAILID    , " +
			"EMPNOMINEESHARABLE, REMARKS    , PENSIONNO  , REGION     , USERNAME   , LASTACTIVE , REFMONTHYEAR , IPADDRESS , FHFLAG    , MAPINGFLAG, PFSETTLED , FINALSETTLMENTDT  , " +
			"OPTIONFORMRECEIVED,NATIONALITY,HEIGHT, MAPPEDUSERNM,to_char(add_months(DATEOFSEPERATION_DATE,-12),'dd-Mon-yyyy') AS SEPERATION_FROMDATE,PHONENUMBER,to_char(dateofjoining,'dd-Mon-yyyy') as DATEOFENTITLE FROM EMPLOYEE_PERSONAL_INFO";
			if (!region.equals("NO-SELECT")) {
				whereClause.append(" REGION ='" + region + "'");
				whereClause.append(" AND ");
			}
			if (!fromYear.equals("")) {
				dataString = "DATEOFSEPERATION_DATE BETWEEN '"+fromYear+"' AND last_day('" + toYear + "')";
				whereClause.append(dataString);
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
				whereClause.append(" AND ");
			}
			if (!pensionno.equals("")) {
				whereClause.append(" PENSIONNO =" + pensionno);
				whereClause.append(" AND ");
			}
			query.append(sqlQuery);
			if ((region.equals("NO-SELECT")) && (fromYear.equals(""))
					&& (airportcode.equals("")) && (pensionno.equals(""))) {

			} else {
				query.append(" WHERE ");
				query.append(this.sTokenFormat(whereClause));
			}
			
			dynamicQuery = query.toString();
			log
					.info("FinancialReportDAO::buildQueryPersonalFormPFID Leaving Method");
			return dynamicQuery;
		}
		
		public ArrayList TransferInOutPrinting(String range, String region,
				String frmSelectedDts, String empNameFlag, String empName,
				String sortedColumn, String pensionno, String lastmonthFlag,
				String lastmonthYear,String airportcode){
			String fromYear="",toYear="";
			CommonDAO commonDAO = new CommonDAO();
			Connection con=null;
			Statement st = null;
			ResultSet rs = null;
			String sqlQuery = "", pfidWithRegion = "", findFromYear="",pensionAppednQry = "";
			EmployeePersonalInfo data = null;
			String condition="";
			int restltmentYear=0;
			ArrayList empinfo = new ArrayList();
			if(!frmSelectedDts.equals("")){
				
				String[] dateArr=frmSelectedDts.split(",");
				fromYear=dateArr[0];
				toYear=dateArr[1];
			}	
			if (sortedColumn.toLowerCase().equals("cpfaccno")) {
				sortedColumn = "cpfacno";
			}
			
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				if (region.equals("NO-SELECT")) {
					region = "";
				}
				if(!airportcode.equals("")&& airportcode!=""){
					 condition="and AIRPORTCODE='"+airportcode+"'";
					}else{
					condition="";
					}
					sqlQuery = "select '"+fromYear+"' as monthyear,info.pensionno, info.employeename, info.dateofbirth, info.dateofjoining,info.wetheroption," +
							" (CASE     WHEN trans.cnt2 =0    THEN 'Transfer Out' ELSE 'Transfer In'     END) as transferstatus,trans.region" +
							" from (select pensionno, region, count(src1) CNT1, count(src2) CNT2  from (select a.pensionno, a.region, 1 src1," +
							" to_number(null) src2  from employee_pension_validate a where monthyear between add_months('"+fromYear+"',-1) and  last_day(add_months('"+fromYear+"',-1))" +
							" and empflag = 'Y' and  region = '"+region+"' "+condition+"   union all select b.pensionno, b.region, to_number(null) src1, 2 src2 from" +
							" EMPLOYEE_PENSION_VALIDATE b WHERE monthyear between '"+fromYear+"' and  last_day('"+fromYear+"') and empflag = 'Y' " +
							"and region = '"+region+"' "+condition+")     group by pensionno, region    having count(src1) <> count(src2)) trans,(select pensionno," +
							"employeeno,cpfacno,employeename, dateofbirth,dateofjoining, wetheroption from employee_personal_info) info where info.pensionno = trans.pensionno order by info.pensionno";
				

				log.info("FinanceReportDAO::getTransferInOutPrinting" + sqlQuery);
				rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					data = new EmployeePersonalInfo();
					
					if (rs.getString("pensionno") != null) {
						data.setPfID(rs.getString("pensionno"));
					} else {
						data.setPfID("");
					}
					
					if (rs.getString("dateofbirth") != null) {
						data.setDateOfBirth(commonUtil.getDatetoString(rs
								.getDate("dateofbirth"), "dd-MMM-yyyy"));
					} else {
						data.setDateOfBirth("---");
					}
					if (rs.getString("dateofjoining") != null) {
						data.setDateOfJoining(commonUtil.getDatetoString(rs
								.getDate("dateofjoining"), "dd-MMM-yyyy"));
					} else {
						data.setDateOfJoining("---");
					}
					if (rs.getString("employeename") != null) {
						data.setEmployeeName(rs.getString("employeename"));
					} else {
						data.setEmployeeName("---");
					}
					if (rs.getString("wetheroption") != null) {
						data.setWetherOption(rs.getString("wetheroption"));
					} else {
						data.setWetherOption("---");
					}
					if (rs.getString("transferstatus") != null) {
						data.setRemarks(rs.getString("transferstatus"));
					} else {
						data.setRemarks("---");
					}
					if (rs.getString("region") != null) {
						data.setRegion(rs.getString("region"));
					} else {
						data.setRegion("---");
					}
				    data.setMonthyear(rs.getString("monthyear"));
					empinfo.add(data);
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return empinfo;
		}

		public ArrayList getRnfcForm68PS(String fromDate, String toDate,
				String region, String pensionNo,String formType,String formFromDate,String sortingcolumn) {

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String emoluments = "", pfStatury = "", dateOfBirth = "", vpf = "", cpf = "", monthYear = "", days = "", wetherOption = "", dateOfRetriment = "";
			String checkDate = "", chkMnthYear = "Apr-1995", frmRegion = "", dispRegion = "",emolumentMonths="";
			boolean flag = false;
			boolean contrFlag = false, chkDOBFlag = false;
			String pensionNoQry = "", airportCode = "", iadAirportString = "", regionString = "", nxtRegionString = "";
			int getDaysBymonth = 0;
			long transMntYear = 0, empRetriedDt = 0;
			double pensionVal = 0.0, retriredEmoluments = 0.0, totalEmoluments = 0.0, totalPFStatury = 0.0, totalVPF = 0.0, totalCPF = 0.0, totalContribution = 0.0;
			double totalEmoluments1 = 0.0, totalPFStatury1 = 0.0, totalVPF1 = 0.0, totalCPF1 = 0.0, totalContribution1 = 0.0;
			String aprDispMnth = "", mayDispMnth = "", junDispMnth = "", julDispMnth = "", augDispMnth = "", sepDispMnth = "", octDispMnth = "", novDispMnth = "", decDispMnth = "", janDispMnth = "", febDispMnth = "", marDispMnth = "";
			double totalAprEmoluments = 0, totalAprContribution = 0.0;
			double totalMayEmoluments = 0, totalMayContribution = 0.0;
			double totalJunEmoluments = 0, totalJunContribution = 0.0;
			double totalJulEmoluments = 0, totalJulContribution = 0.0;
			double totalAugEmoluments = 0, totalAugContribution = 0.0;
			double totalSepEmoluments = 0, totalSepContribution = 0.0;
			double totalOctEmoluments = 0, totalOctContribution = 0.0;
			double totalNovEmoluments = 0, totalNovContribution = 0.0;
			double totalDecEmoluments = 0, totalDecContribution = 0.0;
			double totalJanEmoluments = 0, totalJanContribution = 0.0;
			double totalFebEmoluments = 0, totalFebContribution = 0.0;
			double totalMarEmoluments = 0, totalMarContribution = 0.0,totalRemEmoluments=0.00,totalRemContribution=0.00;
			int totalAprCnt = 0, totalMayCnt = 0, totalJunCnt = 0, totalJulCnt = 0, totalAugCnt = 0, totalSepCnt = 0, totalOctCnt = 0, totalNovCnt = 0;
			int totalDecCnt = 0, totalJanCnt = 0, totalFebCnt = 0, totalMarCnt = 0;
			int countForYear = 0, cntInnnerLoop = 0;
			EmployeePersonalInfo personalInfo = null;
			Form8RemittanceBean remittanceBean=new Form8RemittanceBean();
			String findFromYear="",findToYear="",sqlQuery="",sqlcntQuery="",editTransFlag="",arrearFlag="",month="",disYear= "";
			boolean yearBreakMonthFlag=false;
			StringBuffer buffer = new StringBuffer();
			String calEmoluments = "", frmCpfaccno = "",chkdateOfJoining="", frmEmployeeName = "", frmPensionNo = "", tempSerialNumber = "";
			double cellingRate = 0.0;
			DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
			DecimalFormat df = new DecimalFormat("#########0");
			ArrayList list = new ArrayList();
			ArrayList datalist = new ArrayList();
			if (!pensionNo.equals("")) {
				pensionNoQry = " AND PENSIONNO='" + pensionNo + "'";
			}
			if(!toDate.equals("")){
				String[]chkdoj=toDate.split("-");
				chkdateOfJoining="01-Mar-"+chkdoj[2];
			}
			dispRegion = region;
			if (!region.equals("NO-SELECT")) {
				if (region.indexOf("-") != -1) {
					String regionsList[] = region.split("-");
					if (regionsList[0].equals("IAD")) {

						region = "";

						region = "CHQIAD";
						airportCode = regionsList[1];
						iadAirportString = " AND AIRPORTCODE='" + airportCode
								+ "' ";
					}
				}
				regionString = "AND REGION = '" + region + "' " + iadAirportString;
				nxtRegionString = "AND REGION = '" + region + "' ";
			} else {
				regionString = "";
				nxtRegionString = "";
			}
			try {
				findFromYear = commonUtil.converDBToAppFormat(fromDate,
						"dd-MMM-yyyy", "yyyy");
				findToYear = commonUtil.converDBToAppFormat(toDate, "dd-MMM-yyyy",
						"yyyy");
			} catch (InvalidDataException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			log.info("====================================");
			log.info("region==============================" + region);
			if (Integer.parseInt(findFromYear) >= 2008) {
				yearBreakMonthFlag = true;
				
					 sqlQuery = "SELECT EPV.MONTHYEAR,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
							+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.PENSIONCONTRI,EPV.PF,EPV.EMOLUMENTMONTHS AS EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG FROM "
							+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
							+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
							+ fromDate
							+ "' and LAST_DAY('"
							+ toDate
							+ "') AND PENSIONNO IS NOT NULL) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME  FROM"
							+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
							
							+ pensionNoQry
							
							+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND  EPV.EMPFLAG='Y' ORDER BY EMPFID.EMPSERIALNUMBER";
					 sqlcntQuery = "SELECT COUNT(*) AS COUNT FROM (SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
							+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
							+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,PENSIONNO,PENSIONCONTRI,PF,EMOLUMENTMONTHS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and monthyear between '"
							+ fromDate
							+ "' and LAST_DAY('"
							+ toDate
							+ "') AND PENSIONNO IS NOT NULL ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,PENSIONNO AS EMPSERIALNUMBER,EMPLOYEENAME FROM"
							+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ((round(months_between('"+fromDate+"',add_months(dateofbirth,1))/12,2)<=58 AND (DATEOFSEPERATION_REASON NOT IN ('Death','Resignation') OR DATEOFSEPERATION_REASON IS NULL)) OR (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE>=TO_DATE('"+fromDate+"'))) "
							
							+ pensionNoQry
							
							+ ") EMPFID WHERE EMPFID.EMPSERIALNUMBER=EPV.PENSIONNO  AND EPV.EMPFLAG='Y'  ";
			
			log.info("FinanceReportDAO::getRnfcForm8PSInfo" + sqlQuery);
			}else{
				if(sortingcolumn.equals("cpfaccno")){
					sortingcolumn="CpfAcno";
				}
				yearBreakMonthFlag = false;
				 sqlQuery = "SELECT EPV.MONTHYEAR,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EMPFID.EMPLOYEENAME AS EMPLOYEENAME,EMPFID.EMPSERIALNUMBER AS EMPSERIALNUMBER,EMPFID.WETHEROPTION AS WETHEROPTION,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EPV.EMPFLAG,TO_CHAR(MONTHYEAR, 'Mon')AS MONTH,TO_CHAR(MONTHYEAR, 'Mon-yyyy')AS MNTYEAR,EPV.EMOLUMENTS AS EMOLUMENTS,EPV.EMPPFSTATUARY AS EMPPFSTATUARY,EPV.EMPVPF AS EMPVPF,EPV.CPF AS CPF,"
					+ "EPV.AIRPORTCODE AS AIRPORTCODE,EPV.REGION AS REGION,EPV.CPFACCNO AS CPFACCNO,EPV.EMOLUMENTMONTHS,EPV.ARREARFLAG AS ARREARFLAG,EPV.EDITTRANS AS EDITTRANS,EPV.DEPUTATIONFLAG,EPV.PENSIONCONTRI,EPV.PF FROM "
					+ "(SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
					+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,EMOLUMENTMONTHS,EDITTRANS,ARREARFLAG,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,PENSIONCONTRI,PF FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and  monthyear between '"
					+ fromDate
					+ "' and LAST_DAY('"
					+ toDate
					+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER,EMPLOYEENAME FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
					+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND(NVL(DATEOFJOINING,'"+chkdateOfJoining+"')) <='"+chkdateOfJoining+"'"
					+ regionString
					+ pensionNoQry
					+ ")"
					+ ") EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y' ORDER BY EMPFID."+sortingcolumn;
			log.info("FinanceReportDAO::getRnfcForm8PSInfo" + sqlQuery);

			 sqlcntQuery = "SELECT COUNT(*) AS COUNT FROM (SELECT MONTHYEAR,TO_CHAR(MONTHYEAR,'Mon') AS MONTH,EMOLUMENTS,round(EMPPFSTATUARY) AS EMPPFSTATUARY,round(EMPVPF) AS EMPVPF,CPF,"
					+ "round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL,round(EMPADVRECINTEREST) AS EMPADVRECINTEREST,round(AAICONPF) AS AAICONPF,ROUND(CPFADVANCE) AS CPFADVANCE,ROUND(DEDADV) AS DEDADV,"
					+ "ROUND(REFADV) AS REFADV, AIRPORTCODE,REGION,EMPFLAG,CPFACCNO,EMOLUMENTMONTHS,nvl(DEPUTATIONFLAG,'N') AS DEPUTATIONFLAG,PENSIONCONTRI,PF FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and (emoluments!=0 or emoluments!=0.0) and monthyear between '"
					+ fromDate
					+ "' and LAST_DAY('"
					+ toDate
					+ "') ) EPV  ,(SELECT REGION,CPFACNO,WETHEROPTION,DATEOFBIRTH,EMPSERIALNUMBER,EMPLOYEENAME FROM EMPLOYEE_INFO  WHERE EMPSERIALNUMBER IN (SELECT PENSIONNO FROM"
					+ " EMPLOYEE_PERSONAL_INFO WHERE FORMSDISABLE='Y' AND ROUND(NVL(DATEOFJOINING,'"+chkdateOfJoining+"')) <='"+chkdateOfJoining+"'"
					+ regionString
					+ pensionNoQry
					+ ")"
					+ ") EMPFID WHERE EMPFID.REGION=EPV.REGION AND EMPFID.CPFACNO =EPV.CPFACCNO AND  EPV.EMPFLAG='Y'  ";
			}
			
	
			try {
				con = commonDB.getConnection();
				countForYear = this.totalCountFin(con, sqlcntQuery);
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					flag = false;
					contrFlag = false;
					chkDOBFlag = false;

					if (tempSerialNumber.equals("")) {
						tempSerialNumber = rs.getString("EMPSERIALNUMBER");
					} else if (!tempSerialNumber.equals(rs.getString(
							"EMPSERIALNUMBER").trim())) {
					
						personalInfo = new EmployeePersonalInfo();
						personalInfo.setEmployeeName(frmEmployeeName);
						personalInfo.setPensionNo(frmPensionNo);
						buffer.append(totalEmoluments);
						buffer.append(",");
						buffer.append(Math.round(totalPFStatury));
						buffer.append(",");
						buffer.append(Math.round(totalVPF));
						buffer.append(",");
						buffer.append(Math.round(totalCPF));
						buffer.append(",");
						buffer.append(totalContribution);
						personalInfo.setRemarks("---");
						personalInfo.setPensionInfo(buffer.toString());
						dispRegion = "";

						//list.add(personalInfo);
						if (totalEmoluments != 0 
								&& totalContribution != 0) {
							//personalInfo = null;
							list.add(personalInfo);
						}
						buffer = new StringBuffer();
						totalEmoluments = 0.0;

						totalContribution = 0.0;
						emoluments = "";
						calEmoluments = "";
						pensionVal = 0;
						tempSerialNumber = "";
						tempSerialNumber = rs.getString("EMPSERIALNUMBER");
					}

					cntInnnerLoop++;
					if (tempSerialNumber.equals(rs.getString("EMPSERIALNUMBER")
							.trim())) {
						if (rs.getString("MONTHYEAR") != null) {
							monthYear = commonUtil.getDatetoString(rs
									.getDate("MONTHYEAR"), "dd-MMM-yyyy");

						}
						if(!formType.equals("Form-6")){
							if (rs.getString("MONTH") != null) {
								month = rs.getString("MONTH");
							}

							if (rs.getString("MNTYEAR") != null) {
								disYear = rs.getString("MNTYEAR");
							} else {
								disYear = "---";
							}
						}else{
							try {
								month = commonUtil.converDBToAppFormat(formFromDate,
										"dd-MMM-yyyy", "MMM");
							} catch (InvalidDataException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							try {
								disYear = commonUtil.converDBToAppFormat(formFromDate,
										"dd-MMM-yyyy", "MMM-yyyy");
							} catch (InvalidDataException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
					
						if (rs.getString("CPFACCNO") != null) {
							frmCpfaccno = rs.getString("CPFACCNO");
						}
						String deputationflag="N";
						if (rs.getString("DEPUTATIONFLAG") != null) {
							deputationflag=rs.getString("DEPUTATIONFLAG");
						} 

						if (rs.getString("REGION") != null) {
							frmRegion = rs.getString("REGION");
						}
						if (rs.getString("EMPSERIALNUMBER") != null) {
							frmPensionNo = rs.getString("EMPSERIALNUMBER");
						}
						if (rs.getString("EMOLUMENTMONTHS") != null) {
							emolumentMonths=rs.getString("EMOLUMENTMONTHS".trim());
						} else {
							emolumentMonths="1";
						}
						if(yearBreakMonthFlag==false){
							frmEmployeeName=this.getEmployeePersonalInfo(con,frmPensionNo);
						}else{
							if (rs.getString("EMPLOYEENAME") != null) {
								frmEmployeeName = rs.getString("EMPLOYEENAME");
							}
						}
						
						if (rs.getString("WETHEROPTION") != null) {
							wetherOption = rs.getString("WETHEROPTION");
						}
						if (rs.getString("EMOLUMENTS") != null) {
							emoluments = rs.getString("EMOLUMENTS");
						} else {
							emoluments = "0.00";
						}
						if (rs.getString("EDITTRANS") != null) {
							editTransFlag = rs.getString("EDITTRANS");
						} 
						if (rs.getString("ARREARFLAG") != null) {
							arrearFlag = rs.getString("ARREARFLAG");
						}
						if (rs.getString("dateofbirth") != null) {
							dateOfBirth = commonUtil.converDBToAppFormat(rs
									.getDate("DATEOFBIRTH"));
						} else {
							dateOfBirth = "---";
						}
						if (!dateOfBirth.equals("---")) {
							try {
								dateOfRetriment = this.getRetriedDate(dateOfBirth);
							} catch (InvalidDataException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							dateOfRetriment = "";
						}

						try {
							checkDate = commonUtil.converDBToAppFormat(monthYear,
									"dd-MMM-yyyy", "MMM-yyyy");
							flag = false;
						} catch (InvalidDataException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// log.info(checkDate + "chkMnthYear===" + chkMnthYear);
						if (checkDate.toLowerCase().equals(
								chkMnthYear.toLowerCase())) {
							flag = true;
						}
						String decMnt = "";
						if (checkDate.equals("Dec-1995")) {
							decMnt = this.getRnfcForm8ReportDec95(emoluments,
									dateOfBirth, monthYear, wetherOption.trim(),
									dateOfRetriment, flag,emolumentMonths);
							String[] decCal = decMnt.split(",");
							if (decCal.length == 2) {
								calEmoluments = decCal[0];
								pensionVal = Double.parseDouble(decCal[1]);
							}

						} else {
							calEmoluments = this.calWages(emoluments, monthYear,
									wetherOption.trim(), false, false,emolumentMonths);
						}

					
						if (rs.getString("EMPPFSTATUARY") != null) {
							pfStatury = rs.getString("EMPPFSTATUARY");
						} else {
							pfStatury = "0.00";
						}
						if (rs.getString("EMPVPF") != null) {
							vpf = rs.getString("EMPVPF");
						} else {
							vpf = "0";
						}
						if (rs.getString("CPF") != null) {
							cpf = rs.getString("CPF");
						} else {
							cpf = "0";
						}
						
						if (!monthYear.equals("-NA-")
								&& !dateOfRetriment.equals("")) {
							transMntYear = Date.parse(monthYear);
							empRetriedDt = Date.parse(dateOfRetriment);

							if (transMntYear > empRetriedDt) {
								contrFlag = true;
							} else if (transMntYear == 0
									|| empRetriedDt == 0) {
								contrFlag = false;
							} else if (transMntYear == empRetriedDt
									&& transMntYear != 0
									&& empRetriedDt != 0) {
								chkDOBFlag = true;
							}
						}

						if(yearBreakMonthFlag==false){
							if (!checkDate.equals("Dec-1995")) {
							
								if (flag == false) {
									
									if (contrFlag != true) {
										// log.info("frmEmployeeName"+frmEmployeeName);
										pensionVal = this.pensionFormsCalculation(
												monthYear, calEmoluments, wetherOption
														.trim(), region, false, false,emolumentMonths);

										if (chkDOBFlag == true) {
											String[] dobList = dateOfBirth.split("-");
											days = dobList[0];
											getDaysBymonth = this
													.getNoOfDays(dateOfBirth);
											pensionVal = pensionVal
													* (Double.parseDouble(days) - 1)
													/ getDaysBymonth;
											retriredEmoluments = new Double(
													df1
															.format(Double
																	.parseDouble(calEmoluments)
																	* (Double
																			.parseDouble(days) - 1)
																	/ getDaysBymonth))
													.doubleValue();
											calEmoluments = Double
													.toString(retriredEmoluments);
										}

									} else {
										pensionVal = 0;
										calEmoluments = "0";
										}

								} else {
									pensionVal = 0;
								}
							}
							if(deputationflag.equals("Y")){
								pensionVal = rs.getDouble("PENSIONCONTRI");
								
							}
						}else{
							if(rs.getString("PENSIONCONTRI")!=null){
								pensionVal=Double.parseDouble(rs.getString("PENSIONCONTRI"));
							}else{
								pensionVal = 0;
							}

							if (contrFlag != true) {
								if (chkDOBFlag == true) {
									String[] dobList = dateOfBirth.split("-");
									days = dobList[0];
								
									getDaysBymonth = this.getNoOfDays(dateOfBirth);
									
									retriredEmoluments = new Double(df1.format(Double
											.parseDouble(calEmoluments)
											* (Double.parseDouble(days) - 1)
											/ getDaysBymonth)).doubleValue();
									calEmoluments = Double.toString(retriredEmoluments);
								}
								}else {
									//log.info("available data is"+editTransFlag+"checkDate"+checkDate);
									if(arrearFlag.equals("N")){
										if(!(editTransFlag.equals("Y") && checkDate.toUpperCase().equals("SEP-2009"))){
											pensionVal = 0;
											calEmoluments = "0";
										}
									
									}
										
								}
						
						}
					

					/*	log.info("monthYear==" + monthYear + "==Empserialnumber==="
								+ commonUtil.leadingZeros(20, frmPensionNo)
								+ "emoluments===="
								+ commonUtil.leadingZeros(20, emoluments)
								+ "calEmoluments===="
								+ calEmoluments
								+ "pensionVal====" + pensionVal
								+ "pensionVal====" + Math.round(pensionVal));
	*/
						
						if(!arrearFlag.equals("Y")){
							emoluments = Long.toString(Math.round(Double.parseDouble(calEmoluments)));
						}else{
							emoluments =Double.toString(Math.round(pensionVal*100/8.33));
						}
						if (pensionVal != 0 && pensionVal!=0.0) {
							if (month.toUpperCase().equals("APR")) {
								aprDispMnth = disYear;
								totalAprEmoluments = totalAprEmoluments
										+ Double.parseDouble(emoluments);
								totalAprContribution = totalAprContribution
										+ Math.round(pensionVal);
								totalAprCnt++;
							}
							if (month.toUpperCase().equals("MAY")) {
								mayDispMnth = disYear;
								totalMayEmoluments = totalMayEmoluments
										+ Double.parseDouble(emoluments);
								totalMayContribution = totalMayContribution
										+ Math.round(pensionVal);
								totalMayCnt++;
							}
							if (month.toUpperCase().equals("JUN")) {
								junDispMnth = disYear;
								totalJunEmoluments = totalJunEmoluments
										+ Double.parseDouble(emoluments);
								
								totalJunContribution = totalJunContribution
										+ Math.round(pensionVal);
								totalJunCnt++;
							}
							if (month.toUpperCase().equals("JUL")) {
								julDispMnth = disYear;
								totalJulEmoluments = totalJulEmoluments
										+ Double.parseDouble(emoluments);
								
								totalJulContribution = totalJulContribution
										+ Math.round(pensionVal);
								totalJulCnt++;
							}
							if (month.toUpperCase().equals("AUG")) {
								augDispMnth = disYear;
								totalAugEmoluments = totalAugEmoluments
										+ Double.parseDouble(emoluments);
								
								totalAugContribution = totalAugContribution
										+ Math.round(pensionVal);
								totalAugCnt++;
							}
							if (month.toUpperCase().equals("SEP")) {
								sepDispMnth = disYear;
								totalSepEmoluments = totalSepEmoluments
										+ Double.parseDouble(emoluments);
						
								totalSepContribution = totalSepContribution
										+ Math.round(pensionVal);
								totalSepCnt++;
							}
							if (month.toUpperCase().equals("OCT")) {
								octDispMnth = disYear;
								totalOctEmoluments = totalOctEmoluments
										+ Double.parseDouble(emoluments);
					
								totalOctContribution = totalOctContribution
										+ Math.round(pensionVal);
								totalOctCnt++;
							}
							if (month.toUpperCase().equals("NOV")) {
								novDispMnth = disYear;
								totalNovEmoluments = totalNovEmoluments
										+ Double.parseDouble(emoluments);
								
								totalNovContribution = totalNovContribution
										+ Math.round(pensionVal);
								totalNovCnt++;
							}
							if (month.toUpperCase().equals("DEC")) {
								decDispMnth = disYear;
								totalDecEmoluments = totalDecEmoluments
										+ Double.parseDouble(emoluments);
								
								totalDecContribution = totalDecContribution
										+ Math.round(pensionVal);
								totalDecCnt++;
							}
							if (month.toUpperCase().equals("JAN")) {
								janDispMnth = disYear;
								totalJanEmoluments = totalJanEmoluments
										+ Double.parseDouble(emoluments);
								
								totalJanContribution = totalJanContribution
										+ Math.round(pensionVal);;
								totalJanCnt++;
							}
							if (month.toUpperCase().equals("FEB")) {
								febDispMnth = disYear;
								totalFebEmoluments = totalFebEmoluments
										+ Double.parseDouble(emoluments);
								totalFebContribution = totalFebContribution
										+ Math.round(pensionVal);
								totalFebCnt++;
							}
							if (month.toUpperCase().equals("MAR")) {
								marDispMnth = disYear;
								totalMarEmoluments = totalMarEmoluments
										+ Double.parseDouble(emoluments);
								
								totalMarContribution = totalMarContribution
										+ Math.round(pensionVal);
								totalMarCnt++;
							}
							totalEmoluments = totalEmoluments
									+ Double.parseDouble(emoluments);
							totalPFStatury = totalPFStatury
									+ Double.parseDouble(pfStatury);
							totalVPF = totalVPF + Double.parseDouble(vpf);
							totalCPF = totalCPF + Double.parseDouble(cpf);
							totalContribution = totalContribution + Math.round(pensionVal);

						}
					}
					if (cntInnnerLoop == countForYear) {

						personalInfo = new EmployeePersonalInfo();
						personalInfo.setEmployeeName(frmEmployeeName);
						personalInfo.setPensionNo(frmPensionNo);
						buffer.append(totalEmoluments);
						buffer.append(",");
						buffer.append(Math.round(totalPFStatury));
						buffer.append(",");
						buffer.append(Math.round(totalVPF));
						buffer.append(",");
						buffer.append(Math.round(totalCPF));
						buffer.append(",");
						buffer.append(totalContribution);
						/* log.info(frmEmployeeName+"======="+cntInnnerLoop+
						 "countForYear==="+countForYear+
						 "===============cntInnnerLoop==countForYear================================="+buffer.toString());*/
						personalInfo.setPensionInfo(buffer.toString());
						if (totalEmoluments != 0 
								&& totalContribution != 0) {
							//personalInfo = null;
							list.add(personalInfo);
						}

						

						emoluments = "";
						calEmoluments = "";
						pensionVal = 0;
					}
					
				}
				log.info("totalAprEmoluments"+df1.format(totalAprEmoluments)+"totalAprContribution"+df1.format(totalAprContribution));
				log.info("totalMayEmoluments"+df1.format(totalMayEmoluments)+"totalMayContribution"+df1.format(totalMayContribution));
				log.info("totalJunEmoluments"+df1.format(totalJunEmoluments)+"totalJunContribution"+df1.format(totalJunContribution));
				log.info("totalJulEmoluments"+df1.format(totalJulEmoluments)+"totalJulContribution"+df1.format(totalJulContribution));
				log.info("totalAugEmoluments"+df1.format(totalAugEmoluments)+"totalAugContribution"+df1.format(totalAugContribution));
				log.info("totalSepEmoluments"+df1.format(totalSepEmoluments)+"totalSepContribution"+df1.format(totalSepContribution));
				log.info("totalOctEmoluments"+df1.format(totalOctEmoluments)+"totalOctContribution"+df1.format(totalOctContribution));
				log.info("totalNovEmoluments"+df1.format(totalNovEmoluments)+"totalNovContribution"+df1.format(totalNovContribution));
				log.info("totalDecEmoluments"+df1.format(totalDecEmoluments)+"totalDecContribution"+df1.format(totalDecContribution));
				log.info("totalMayEmoluments"+df1.format(totalJanEmoluments)+"totalJanContribution"+df1.format(totalJanContribution));
				log.info("totalJanEmoluments"+df1.format(totalFebEmoluments)+"totalFebContribution"+df1.format(totalFebContribution));
				log.info("totalMarEmoluments"+df1.format(totalMarEmoluments)+"totalMarContribution"+df1.format(totalMarContribution));
				
				
				totalRemContribution = totalAprContribution + totalMayContribution
				+ totalJunContribution + totalJulContribution
				+ totalAugContribution + totalSepContribution;
				totalRemContribution = totalRemContribution + totalOctContribution
				+ totalNovContribution + totalDecContribution;
				totalRemContribution = totalRemContribution + totalJanContribution
				+ totalFebContribution + totalMarContribution;
				
				totalRemEmoluments = totalAprEmoluments + totalMayEmoluments
						+ totalJunEmoluments + totalJulEmoluments
						+ totalAugEmoluments + totalSepEmoluments;
				totalRemEmoluments = totalRemEmoluments + totalOctEmoluments
						+ totalNovEmoluments + totalDecEmoluments;
				totalRemEmoluments = totalRemEmoluments + totalJanEmoluments
						+ totalFebEmoluments + totalMarEmoluments;

				remittanceBean.setTotalAprContribution(totalAprContribution);
				remittanceBean.setTotalAprEmoluments(new Long(df.format(Math
						.round(totalAprEmoluments))).longValue());
				remittanceBean.setAprMnth(aprDispMnth);
				remittanceBean.setAprCnt(totalAprCnt);
				remittanceBean.setTotalMayContribution(totalMayContribution);
				remittanceBean.setTotalMayEmoluments(new Long(df.format(Math
						.round(totalMayEmoluments))).longValue());
				remittanceBean.setMayCnt(totalMayCnt);
				remittanceBean.setMayMnth(mayDispMnth);
				remittanceBean.setTotalJunContribution(totalJunContribution);
				remittanceBean.setTotalJunEmoluments(new Long(df.format(Math
						.round(totalJunEmoluments))).longValue());
				remittanceBean.setJunCnt(totalJunCnt);
				remittanceBean.setJunMnth(junDispMnth);
				remittanceBean.setTotalJulContribution(totalJulContribution);
				remittanceBean.setTotalJulEmoluments(new Long(df.format(Math
						.round(totalJulEmoluments))).longValue());
				remittanceBean.setJulCnt(totalJulCnt);
				remittanceBean.setJulMnth(julDispMnth);
				remittanceBean.setTotalAugContribution(totalAugContribution);
				remittanceBean.setTotalAugEmoluments(new Long(df.format(Math
						.round(totalAugEmoluments))).longValue());
				remittanceBean.setAugCnt(totalAugCnt);
				remittanceBean.setAugMnth(augDispMnth);
				remittanceBean.setTotalSepContribution(totalSepContribution);
				remittanceBean.setTotalSepEmoluments(new Long(df.format(Math
						.round(totalSepEmoluments))).longValue());
				remittanceBean.setSepCnt(totalSepCnt);
				remittanceBean.setSepMnth(sepDispMnth);
				remittanceBean.setTotalOctContribution(totalOctContribution);
				remittanceBean.setTotalOctEmoluments(new Long(df.format(Math
						.round(totalOctEmoluments))).longValue());
				remittanceBean.setOctCnt(totalOctCnt);
				remittanceBean.setOctMnth(octDispMnth);
				remittanceBean.setTotalNovContribution(totalNovContribution);
				remittanceBean.setTotalNovEmoluments(new Long(df.format(Math
						.round(totalNovEmoluments))).longValue());
				remittanceBean.setNovCnt(totalNovCnt);
				remittanceBean.setNovMnth(novDispMnth);
				remittanceBean.setTotalDecContribution(totalDecContribution);
				remittanceBean.setTotalDecEmoluments(new Long(df.format(Math
						.round(totalDecEmoluments))).longValue());
				remittanceBean.setDecCnt(totalDecCnt);
				remittanceBean.setDecMnth(decDispMnth);
				remittanceBean.setTotalJanContribution(totalJanContribution);
				remittanceBean.setTotalJanEmoluments(new Long(df.format(Math
						.round(totalJanEmoluments))).longValue());
				remittanceBean.setJanCnt(totalJanCnt);
				remittanceBean.setJanMnth(janDispMnth);
				remittanceBean.setTotalFebContribution(totalFebContribution);
				remittanceBean.setTotalFebEmoluments(new Long(df.format(Math
						.round(totalFebEmoluments))).longValue());
				remittanceBean.setFebCnt(totalFebCnt);
				remittanceBean.setFebMnth(febDispMnth);
				remittanceBean.setTotalMarContribution(totalMarContribution);
				remittanceBean.setTotalMarEmoluments(new Long(df.format(Math
						.round(totalMarEmoluments))).longValue());
				remittanceBean.setMarCnt(totalMarCnt);
				remittanceBean.setMarMnth(marDispMnth);
				remittanceBean.setTotalContr(df.format(Math
						.round(totalRemContribution)));
				remittanceBean
						.setTotalEmnts(df.format(Math.round(totalRemEmoluments)));
				datalist.add(list);
				datalist.add(remittanceBean);
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return datalist;
		}
		public String getForm6CompDates(String fromDates,String toDates){
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String fromDate="",toDate="";
			StringBuffer buffer=new StringBuffer();
			String sqlQuery="SELECT to_char(add_months('"+fromDates+"',1),'dd-Mon-yyyy') as fromdate,to_char(last_day(add_months('"+toDates+"',1)),'dd-Mon-yyyy') as todate from dual";
			try{
				con=commonDB.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(sqlQuery);
				if(rs.next()){
					fromDate=rs.getString("fromdate");
					toDate=rs.getString("todate");
				}
				buffer.append(fromDate);
				buffer.append(",");
				buffer.append(toDate);
			}catch(SQLException se){
				log.printStackTrace(se);
			}catch(Exception e){
				log.printStackTrace(e);
			
			}finally{
				commonDB.closeConnection(con,st,rs);
			}
			return buffer.toString();
		}
		public void processingPFCards(){
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String fromDate="",toDate="";
			StringBuffer buffer=new StringBuffer();
			String sqlQuery="SELECT PENSIONNO FROM processing_pfcards WHERE PFCARDSTATUS='N'";
			try{
				con=commonDB.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(sqlQuery);
				while(rs.next()){
					ProcessforAdjOb(rs.getString("PENSIONNO"),true);
				}
				finalUpdates();	
			}catch(SQLException se){
				log.printStackTrace(se);
			}catch(Exception e){
				log.printStackTrace(e);
			
			}finally{
				commonDB.closeConnection(con,st,rs);
			}
		}
		public void processStsPFCards(Connection con,String pfid){
			Statement st = null;
			ResultSet rs = null;
			int cnt=0;
			StringBuffer buffer=new StringBuffer();
			String sqlQuery="UPDATE processing_pfcards SET PROCESSEDDATE=SYSDATE,PFCARDSTATUS='Y' WHERE PENSIONNO="+pfid;
			try{
				con=commonDB.getConnection();
				st=con.createStatement();
				cnt=st.executeUpdate(sqlQuery);
			}catch(SQLException se){
				log.printStackTrace(se);
			}catch(Exception e){
				log.printStackTrace(e);
			
			}finally{
				commonDB.closeConnection(null,st,rs);
			}
		}
		private long getUpdateAdjOBForPFCard(Connection con, String monthYear,
				String pensionNo) {

			Statement st = null;
			ResultSet rs = null;
			double adjStartYear=2008,noOfYears=0;
			String sqlQuery = "", obYear = "", obFlag = "", tempMonthYear = "",adjFlag="N",sqlUpdateQry="";
			long cpfTotal = 0, pensionTotal = 0, pfTotal = 0, empsubTotal = 0;
			long cpfInterest = 0, pensionInterest = 0, pfInterest = 0, empsubInterest = 0,priorAdjAmount=0;
			long adjcpfTotal = 0, adjPensionTotal = 0, adjPfTotal = 0, adjPensionTotalInt=0,adjEmpSubTotal = 0,adjpensionwopriinterest=0;
			tempMonthYear = "%" + monthYear.substring(2, monthYear.length());
			sqlQuery = "SELECT CPFTOTAL,CPFINTEREST,NVL(PENSIONTOTAL,'0') AS PENSIONTOTAL,NVL(PENSIONINTEREST,'0') AS PENSIONINTEREST,PFTOTAL,PFINTEREST,NVL(EMPSUB,'0') AS EMPSUB,NVL(EMPSUBINTEREST,'0') AS EMPSUBINTEREST,OUTSTANDADV,PRIORADJFLAG,NVL(PRIORADJAMT,'0') AS  PRIORADJAMT FROM EMPLOYEE_ADJ_OB WHERE PENSIONNO="
					+ pensionNo.trim()
					+ " and to_char(ADJOBYEAR,'dd-Mon-yyyy') like '"
					+ tempMonthYear + "'";
			
			
			try {
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				log.info("getAdjOBForPFCardReport===================sqlQuery==="
						+ sqlQuery);
				if (rs.next()) {
					cpfTotal = rs.getLong("CPFTOTAL");
					pensionTotal = rs.getLong("PENSIONTOTAL");
					pfTotal = rs.getLong("PFTOTAL");
					cpfInterest = rs.getLong("CPFINTEREST");
					pensionInterest = rs.getLong("PENSIONINTEREST");
					pfInterest = rs.getLong("PFINTEREST");
					empsubTotal = rs.getLong("EMPSUB");
					empsubInterest = rs.getLong("EMPSUBINTEREST");
					if(rs.getString("PRIORADJFLAG")!=null){
						adjFlag=rs.getString("PRIORADJFLAG");
					}
					priorAdjAmount= rs.getLong("PRIORADJAMT");
				}
				
				adjPensionTotal = pensionTotal + pensionInterest;
				adjpensionwopriinterest=adjPensionTotal;
				noOfYears=Double.parseDouble(commonUtil.converDBToAppFormat(monthYear,"dd-MMM-yyyy","yyyy"))-adjStartYear;
				adjPensionTotalInt=Math.round(adjPensionTotal*Math.pow((1+8.5/100),noOfYears));
				/*if(Integer.parseInt(commonUtil.converDBToAppFormat(monthYear,"dd-MMM-yyyy","yyyy"))==2009 && adjFlag.equals("N")){
					adjPensionTotalInt=new Double(adjPensionTotal*8.5/100).longValue();
				}else if(Integer.parseInt(commonUtil.converDBToAppFormat(monthYear,"dd-MMM-yyyy","yyyy"))==2010 && adjFlag.equals("N")){
					adjPensionTotalInt=new Double(adjPensionTotal*8.5/100).longValue()*2;
				}*/
				adjPensionTotal=adjPensionTotalInt+priorAdjAmount;
				
				commonDB.closeStatement(st);
				log.info("PFCard==========getUpdateAdjOBForPFCard===================pensionTotal==="
						+ pensionTotal+"pensionInterest"+pensionInterest);
				log.info("PFCard==========getUpdateAdjOBForPFCard===================adjPensionTotalInt==="
						+ adjPensionTotalInt+"pensionInterest"+priorAdjAmount+"adjPensionTotal=="+adjpensionwopriinterest);
				
				sqlUpdateQry="UPDATE EMPLOYEE_ADJ_OB SET  PENSIONINTEREST=0,PENSIONWOPRIINT="+adjpensionwopriinterest+",PENSIONTOTAL="+adjPensionTotal+" WHERE PENSIONNO="+pensionNo+" AND to_char(ADJOBYEAR,'dd-Mon-yyyy') like'"+tempMonthYear+"' AND PRIORADJFLAG='N'";
				log.info("PFCard==========getUpdateAdjOBForPFCard===================sqlUpdateQry==="
						+ sqlUpdateQry);
				st = con.createStatement();
				int i=st.executeUpdate(sqlUpdateQry);

			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return adjPensionTotal;
		}
		
		private int insertPCTotals(Connection con, double cpf, double cpfinterest,
				double pension, double pensionintrest, double pf,
				double pfinterest, String pensionno, String region) {
			int count = 0;
			Statement st = null;
			String sqlInsQuery = "", remarks = "", adjOBYear = "";
			DecimalFormat df = new DecimalFormat("#########0");
			adjOBYear = Constants.ADJ_CREATED_DATE_2009;
			try {
				remarks = "Generated From 1995-08 Grand Total PC Report";
				sqlInsQuery = "INSERT INTO epis_pc_totals(CPFTOTAL,CPFINTEREST,PENSIONTOTAL,PENSIONINTEREST,PFTOTAL,PFINTEREST,REMARKS,CREATIONDATE,REGION,PENSIONNO) VALUES("
					+ df.format(cpf)
					+ ","
					+ df.format(cpfinterest)
					+ ","
					+ df.format(pension)
					+ ","
					+ df.format(pensionintrest)
					+ ","
					+ df.format(pf)
					+ ","
					+ df.format(pfinterest)
					+ ",'"
					+ remarks
					+ "','"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ region
					+ "',"
					+ pensionno + ")"; 
				st = con.createStatement();
				log.info("insertPCTotals::Inserted Query" + sqlInsQuery);
				count = st.executeUpdate(sqlInsQuery);
				
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeStatement(st);
			}
			return count;
		}
		private int updatePCTotals(Connection con, double cpf, double cpfinterest,
				double pension, double pensionintrest, double pf,
				double pfinterest, String pensionno, String region) {
			int count = 0;
			Statement st = null;
			String sqlInsQuery = "", remarks = "", adjOBYear = "", sqlAdjQuery = "";
			DecimalFormat df = new DecimalFormat("#########0");
			adjOBYear = Constants.ADJ_CREATED_DATE;
			try {
				remarks = "Generated From 1995-08 Grand Total PC Report";
				sqlInsQuery = "UPDATE epis_pc_totals SET CPFTOTAL='"
						+ df.format(cpf) + "',CPFINTEREST='"
						+ df.format(cpfinterest) + "',PENSIONTOTAL='"
						+ df.format(pension) + "',PENSIONINTEREST='"
						+ df.format(pensionintrest) + "',PFTOTAL='" + df.format(pf)
						+ "',PFINTEREST='" + df.format(pfinterest) + "',LASTACTIVE=sysdate WHERE PENSIONNO='" + pensionno + "'";
				st = con.createStatement();
				
				count = st.executeUpdate(sqlInsQuery);
				log.info("updatePCTotals::Updated Query" + sqlInsQuery+"count====="+count);
				

			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeStatement(st);
			}
			return count;
		}
		
		private void pctotalAddorUpdate(Connection con, double cpf, double cpfinterest,
				double pension, double pensionintrest, double pf,
				double pfinterest, String pensionno, String region){
			    int counts=0;
				if(!chkPCTotalInfo(pensionno,con).equals("X")){
					
					counts=this.insertPCTotals(con, cpf, cpfinterest,pension, pensionintrest, pf,pfinterest, pensionno, region);
				}else{
					counts=this.updatePCTotals(con, cpf, cpfinterest,pension, pensionintrest, pf,pfinterest, pensionno, region);
				}
			log.info("FinancialReportDAO::pctotalAddorUpdate"+counts);
		}
		//New Method

		private String chkPCTotalInfo(String empSerialNo, Connection con) {
			Statement st = null;
			ResultSet rs = null;
			String sqlInsQuery = "";
			String chkFlag = "";
			try {
				sqlInsQuery = "SELECT 'X' AS FLAG FROM epis_pc_totals WHERE  PENSIONNO='" + empSerialNo + "'";
				st = con.createStatement();
				log.info("chkPCTotalInfo::Query" + sqlInsQuery);
				rs = st.executeQuery(sqlInsQuery);
				if (rs.next()) {
					if (rs.getString("FLAG") != null) {
						chkFlag = rs.getString("FLAG");
					}
				}else{
					chkFlag = "N";
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeStatement(st);
			}
			return chkFlag;
		}
		public ArrayList getRfcForm8RangeReport(String selectedYear, String month,
				String sortedColumn, String region, boolean formFlag,
				String airportCode, String pensionNo, String range, String empflag,
				String empName,String formType,String formTypeRevisedFlag) {
			String fromYear = "", toYear = "", dateOfRetriment = "", frmMonth = "";
			int toSelectYear = 0;
			ArrayList empList = new ArrayList();
			EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
			Form8DataBean form8DataBean=new Form8DataBean();
			ArrayList form8List = new ArrayList();
			ArrayList bothList = new ArrayList();
			ArrayList getPensionList = new ArrayList();
			Form8Bean indivBean=new Form8Bean();
			boolean arrearsFlag=false;
			if (!month.equals("NO-SELECT")) {
				try {
					frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!selectedYear.equals("Select One") && month.equals("NO-SELECT")) {
				fromYear = "01-Apr-" + selectedYear;
				toSelectYear = Integer.parseInt(selectedYear) + 1;
				toYear = "01-Mar-" + toSelectYear;
			} else if (!selectedYear.equals("Select One")
					&& !month.equals("NO-SELECT")) {
				fromYear = "01-" + frmMonth + "-" + selectedYear;
				toYear = fromYear;
			} else {
				fromYear = Constants.FORMS_FROM_DATE;
				toYear = Constants.FORMS_TO_DATE;
			}
			Form8RemittanceBean form8Remittance=new Form8RemittanceBean();
			empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
					fromYear, toYear, airportCode, pensionNo, empflag, empName,"N",formType,formTypeRevisedFlag);
		
			if(empList.size()==0 && Integer.parseInt(selectedYear)>=2008){
						empList = this.getForm7EmployeeInfo(range, sortedColumn, region,
								fromYear, toYear, airportCode, pensionNo, empflag, empName,"Y",formType,formTypeRevisedFlag);
						arrearsFlag=true;
			}
			log.info("rnfcForm8Report:::fromYear"+fromYear+"toYear"+toYear+"arrearsFlag"+arrearsFlag+"Size is===="+empList.size()+"formTypeRevisedFlag======="+formTypeRevisedFlag);
			Connection con=null;
			String pensionInfo = "", regionInfo = "";
			try{
				con=commonDB.getConnection();
				for (int i = 0; i < empList.size(); i++) {
					personalInfo = (EmployeePersonalInfo) empList.get(i);
					 form8DataBean=new Form8DataBean();
					 if(personalInfo.getChkarrearAdj().equals("N")){
						 arrearsFlag=false;
					 }
					if (!personalInfo.getDateOfBirth().equals("---")) {
						try {
							dateOfRetriment = this.getRetriedDate(personalInfo
									.getDateOfBirth());
						} catch (InvalidDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(!personalInfo.getFinalSettlementDate().equals("---")){
						int finalSettlmentYear=0;
						try {
							finalSettlmentYear=Integer.parseInt(commonUtil.converDBToAppFormat(personalInfo.getFinalSettlementDate(),"dd-MMM-yyyy","yyyy"));
						} catch (NumberFormatException e) {
							log.printStackTrace(e);
						} catch (InvalidDataException e) {
							log.printStackTrace(e);
						}
						if((Integer.parseInt(selectedYear)>=finalSettlmentYear && finalSettlmentYear<=toSelectYear) && arrearsFlag==false){
							arrearsFlag=true;
						}
					}
					String tempEntitle="",returnFromDate="",fromDOJ="";
					if(formTypeRevisedFlag.equals("N")){
						tempEntitle="01"+personalInfo.getDateOfEntitle().substring(2,personalInfo.getDateOfEntitle().length());
						returnFromDate=this.compareTwoDates(tempEntitle,fromYear);
						if(returnFromDate.equals(tempEntitle)){
							fromDOJ=personalInfo.getDateOfEntitle();
						}else{
							fromDOJ=fromYear;
						}
					}else{
						tempEntitle=personalInfo.getFromarrearreviseddate();
						returnFromDate=this.compareTwoDates(tempEntitle,fromYear);
						if(returnFromDate.equals(tempEntitle)){
							fromDOJ=personalInfo.getFromarrearreviseddate();
						}else{
							fromDOJ=fromYear;
						}
					}
					
					log.info("Pensionno"+personalInfo.getOldPensionNo()+"From Date"+fromYear+"toYear"+toYear+"fromDOJ"+fromDOJ+"returnFromDate"+returnFromDate);
					getPensionList = this.getForm8PensionInfo(con,fromDOJ, toYear,
							personalInfo.getPfIDString(), personalInfo
									.getWetherOption(), personalInfo.getRegion(),
							false, dateOfRetriment, personalInfo.getDateOfBirth(),
							personalInfo.getOldPensionNo(),arrearsFlag,formTypeRevisedFlag);
					form8Remittance = this.getForm8RemInfo(con,fromDOJ, toYear,
							personalInfo.getPfIDString(), personalInfo
							.getWetherOption(), personalInfo.getRegion(),
					false, dateOfRetriment, personalInfo.getDateOfBirth(),
					personalInfo.getOldPensionNo(),arrearsFlag,formTypeRevisedFlag);
					personalInfo.setPensionList(getPensionList);
					log.info("Pensionno"+personalInfo.getPensionNo());
					dateOfRetriment = "";
					form8List.add(personalInfo);
					form8DataBean.setPensionNo(personalInfo.getPensionNo());
					form8DataBean.setEmployeeName(personalInfo.getEmployeeName());
					form8DataBean.setRemarks(personalInfo.getRemarks());
					form8DataBean.setForm8List(getPensionList);
					form8DataBean.setRemittanceBean(form8Remittance);
					bothList.add(form8DataBean);
				}
			}catch(Exception e){
				log.printStackTrace(e);
			}finally{
				commonDB.closeConnection(con,null,null);
			}
			
			
			return bothList;
		}


		public String buildQueryEmployeeArrearRevisedInfo(String range,
				String region, String empNameFlag, String empName,
				String sortedColumn, String pensionno, String toYear,
				String fromYear) {
			log
					.info("FinanceReportDAO::buildQueryEmployeeArrearRevisedInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "", sqlQuery = "";
			int startIndex = 0, endIndex = 0;
			sqlQuery = "SELECT EINFO.REFPENSIONNUMBER,EINFO.CPFACNO,EINFO.AIRPORTSERIALNUMBER,EINFO.EMPLOYEENO, INITCAP(EINFO.EMPLOYEENAME) AS EMPLOYEENAME,EINFO.DESEGNATION,EINFO.EMP_LEVEL,EINFO.DATEOFBIRTH,EINFO.DATEOFJOINING,"
				+ "EINFO.DATEOFSEPERATION_REASON,EINFO.DATEOFSEPERATION_DATE,EINFO.WHETHER_FORM1_NOM_RECEIVED,'' AS REMARKS,EINFO.AIRPORTCODE,EINFO.GENDER,EINFO.FHNAME,EINFO.MARITALSTATUS,EINFO.PERMANENTADDRESS,EINFO.TEMPORATYADDRESS,"
				+ "EINFO.WETHEROPTION,EINFO.SETDATEOFANNUATION,EMPFLAG,EINFO.DIVISION,EINFO.DEPARTMENT,EINFO.EMAILID,EINFO.EMPNOMINEESHARABLE,EINFO.REGION,EINFO.PENSIONNO,(LAST_DAY(EINFO.dateofbirth) + INTERVAL '60' year ) as DOR,EINFO.username,"
				+ "EINFO.LASTACTIVE,EINFO.RefMonthYear,EINFO.IPAddress,EINFO.OTHERREASON,AINFO.ARREARTODATE,AINFO.ARREARFORMDATE,decode(sign(round(months_between(EINFO.dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(EINFO.dateofjoining,'dd-Mon-yyyy'),to_char(EINFO.dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,"
				+ "to_char(trunc((EINFO.dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', EINFO.dateofbirth) / 12) EMPAGE,FINALSETTLMENTDT,(CASE  WHEN (EINFO.dateofbirth<last_day('"
				+ toYear
				+ "')) THEN  round(months_between(last_day('"
				+ toYear
				+ "'),EINFO.dateofbirth)/12) ELSE  round(months_between(EINFO.dateofbirth, last_day('"
				+ toYear
				+ "')) / 12) END)  as	EMPAGE1, "
				+ "(CASE WHEN (EINFO.DATEOFSEPERATION_date < = '"
				+ toYear
				+ "' AND EINFO.DATEOFSEPERATION_date >='"
				+ fromYear
				+ "') THEN 'Y' ELSE 'N' END) as SEPER_FLAG,to_char((EINFO.dateofbirth + INTERVAL '58' year ),'dd-Mon-yyyy')  as LASTATTAINED,'' as ARREARDATE,round(months_between(NVL(EINFO.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,'N' AS CHKWTHRARRNOT  FROM EMPLOYEE_PERSONAL_INFO EINFO,EMPLOYEE_ARREAR_BREAKUP AINFO WHERE EINFO.FORMSDISABLE='Y' AND EINFO.PENSIONNO=AINFO.PENSIONNO AND  ((AINFO.Arrearformdate between '"+fromYear+"' and LAST_DAY('"+toYear+"')) or "+
				"(AINFO.Arreartodate between '"+fromYear+"' and LAST_DAY('"+toYear+"')))";

			if (!range.equals("NO-SELECT")) {

				String[] findRnge = range.split(" - ");
				startIndex = Integer.parseInt(findRnge[0]);
				endIndex = Integer.parseInt(findRnge[1]);

				whereClause.append("  EINFO.PENSIONNO >=" + startIndex
						+ " AND EINFO.PENSIONNO <=" + endIndex);
				whereClause.append(" AND ");

			}

			if (!region.equals("") && !region.equals("AllRegions")) {
				whereClause.append(" EINFO.REGION ='" + region + "'");
				whereClause.append(" AND ");
			}
			if (!toYear.equals("")) {
				whereClause.append(" EINFO.DATEOFJOINING <=LAST_DAY('" + toYear
						+ "')");
				whereClause.append(" AND ");
			}
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("EINFO.PENSIONNO='" + pensionno + "' ");
					whereClause.append(" AND ");
				}
			}

			query.append(sqlQuery);
			if (region.equals("")
					&& (range.equals("NO-SELECT") && toYear.equals("") && (empNameFlag
							.equals("false")))) {

			} else {
				query.append(" AND ");
				query.append(this.sTokenFormat(whereClause));
			}
			dynamicQuery = query.toString();
			log
					.info("FinanceReportDAO::buildQueryEmployeeArrearRevisedInfo Leaving Method");
			return dynamicQuery;
		}
			 //	By Radha On 29-Sep-2011  Changing rate of interest  9.5 for 2010-2011 and 2011-2012
			//By Radha On 15-Jul-2011  For Not Connecting with RPFC Changes
			 public String getPensionInfo(String pensionNo,String employeeName,String fromDate,String toDate){
					ArrayList pensionList=new ArrayList();
					DecimalFormat df = new DecimalFormat("#########0.00");
					String wetherOption="",dateOfBirth="",dateOfRetirment="",calYear="", finalSettlementDate = "";;
					EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
					EmployeePensionCardInfo cardInfo=null;
					ArrayList empDataList=new ArrayList();
					ArrayList pfcardList=new ArrayList();
					   Long empNetOB = null, aaiNetOB = null, pensionOB = null, principalOB = null;
						Long empNetAdjOB = null, aaiNetAdjOB = null, pensionAdjOB = null, principalAdjOB = null, empSubAdjOB = null;
						String obFromTrFlag = "", obYear = "";
						long finalPrincipalOB = 0, finalEmpNetOB = 0, finalAaiNetOB = 0, finalPensionOB = 0, finalOBFromTrFlag = 0, finalOBYear = 0, obEmpNetInterest = 0, obAaiNetInterest = 0, obPenInterest = 0;
						long adjOBEmpNetInterest = 0, adjOBAaiNetInterest = 0, adjOBPenInterest = 0, adjOBEmpSubInterest = 0;
						double totalAdditionalContri=0.0;
					double totalEmoluments = 0.0, dispTotalEmpNet = 0.0, totalAdvances=0,dispTotalAAINet = 0.0, pfStaturary = 0.0, empVpf = 0.0, principle = 0.0, interest = 0.0, empTotal = 0.0, totalEmpNet = 0.0, totalEmpIntNet = 0.0, totalAaiIntNet = 0.0;
					double totalAAIPF = 0.0, totalPFDrwn = 0.0,advancePFWPaidTotal=0, totalAAINet = 0.0, empNetInterest = 0.0, aaiNetInterest = 0.0, totalPensionContr = 0.0, totalPensionCum = 0.0, penInterest = 0.0;
					Connection con = null;
					ArrayList closingOBList=new ArrayList();
					ArrayList OBList=new ArrayList();
					StringBuffer buffer=new StringBuffer();
					EmployeeCardReportInfo cardReport=new EmployeeCardReportInfo();
					int noOfmonthsForInterest=0;
					//log.info("getPensionInfo====================fromDate"+fromDate+"toDate"+toDate);
					try{
						 con = commonDB.getConnection();
						 pfcardList=this.advancesEmpPFCardReportPrint("NO-SELECT","",commonUtil.converDBToAppFormat(fromDate,"dd-MMM-yyyy","yyyy"),"true",employeeName,"PENSIONNO",pensionNo,"","","");
						 for(int cardList=0;cardList<pfcardList.size();cardList++){
								cardReport=(EmployeeCardReportInfo)pfcardList.get(cardList);
								personalInfo=cardReport.getPersonalInfo();
								System.out.println("PF ID String"+personalInfo.getPfIDString());
								pensionList=cardReport.getPensionCardList();
								noOfmonthsForInterest=cardReport.getNoOfMonths();
						 for(int pensionlst=0;pensionlst<pensionList.size();pensionlst++){
							 cardInfo=new EmployeePensionCardInfo();
							 cardInfo=(EmployeePensionCardInfo)pensionList.get(pensionlst);
							 if(cardInfo.getObFlag().equals("Y")){
									OBList=(ArrayList)cardInfo.getObList();
									calYear = commonUtil.converDBToAppFormat(cardInfo
											.getMonthyear(), "dd-MMM-yyyy", "yyyy");
								 
							 }
							 	totalEmoluments= new Double(df.format(totalEmoluments+Math.round(Double.parseDouble(cardInfo.getEmoluments())))).doubleValue();
								pfStaturary= new Double(df.format(pfStaturary+Math.round(Double.parseDouble(cardInfo.getEmppfstatury())))).doubleValue();
								if(cardInfo.getMonthyear().equals("01-Apr-2015")) {
									//totalAdditionalContri=new Double(df.format(totalAdditionalContri+Math.round(totalAddContri))).doubleValue();
									} else {
									totalAdditionalContri=new Double(df.format(totalAdditionalContri+Math.round(Double.parseDouble(cardInfo.getAdditionalContri())))).doubleValue();
									}
								
								empVpf = new Double(df.format(empVpf+Math.round(Double.parseDouble(cardInfo.getEmpvpf())))).doubleValue();
								principle =new Double(df.format(principle+Math.round(Double.parseDouble(cardInfo.getPrincipal())))).doubleValue();
								interest =new Double(df.format(interest+Math.round(Double.parseDouble(cardInfo.getInterest())))).doubleValue();
								empTotal=new Double(df.format(empTotal+Math.round(Double.parseDouble(cardInfo.getEmptotal())))).doubleValue();
								advancePFWPaidTotal= new Double(df.format(advancePFWPaidTotal+Math.round( Double.parseDouble(cardInfo.getAdvancePFWPaid())))).doubleValue();
							    if(!cardInfo.getGrandCummulative().equals("")){
						    		if(cardInfo.isYearFlag()==true){
							    		
							    		totalEmpNet=totalEmpNet+new Double(df.format(Double.parseDouble(cardInfo.getGrandCummulative()))).doubleValue();
							    
							    		}else{
							    		totalEmpNet= new Double(df.format(Double.parseDouble(cardInfo.getGrandCummulative()))).doubleValue();
							    
							    		}
							    	    }

							   
							   	dispTotalEmpNet= new Double(df.format(dispTotalEmpNet+Math.round( Double.parseDouble(cardInfo.getEmpNet())))).doubleValue();
							   	totalAdvances=new Double(df.format(totalAdvances+Math.round( Double.parseDouble(cardInfo.getAdvancesAmount())))).doubleValue();
							   	System.out.println("Total Advances"+totalAdvances);
							    totalAAIPF=new Double(df.format(totalAAIPF+Math.round(Double.parseDouble(cardInfo.getAaiPF())))).doubleValue();
							    totalPFDrwn= new Double(df.format(totalPFDrwn+Math.round( Double.parseDouble(cardInfo.getPfDrawn())))).doubleValue();
							    if(!cardInfo.getGrandAAICummulative().equals("")){
							    	    totalAAINet= new Double(df.format(Double.parseDouble(cardInfo.getGrandAAICummulative()))).doubleValue();
							    }
							     
							   // totalAAINet= new Double(df.format(totalAAINet+Math.round( Double.parseDouble(pensionCardInfo.getAaiCummulative())))).doubleValue();
							    dispTotalAAINet= new Double(df.format(dispTotalAAINet+Math.round( Double.parseDouble(cardInfo.getAaiNet())))).doubleValue();
							    
							    totalPensionContr=new Double(df.format(totalPensionContr+Math.round( Double.parseDouble(cardInfo.getPensionContribution())))).doubleValue();
							 
						 }
						 double rateOfInterest = 0, pensionInterest = 0.0;
						 
						 if(Integer.parseInt(calYear)>=1995 && Integer.parseInt(calYear)<2000){
				 				rateOfInterest=12;
							}else if(Integer.parseInt(calYear)>=2000 && Integer.parseInt(calYear)<2001){
								rateOfInterest=11;
							}else if(Integer.parseInt(calYear)>=2001 && Integer.parseInt(calYear)<2005){
								rateOfInterest=9.50;
							}else if(Integer.parseInt(calYear)>=2005 && Integer.parseInt(calYear)<2010){
								rateOfInterest=8.50;
							}else if (Integer.parseInt(calYear)>= 2010	&& Integer.parseInt(calYear) < 2012) {
								rateOfInterest = 9.50;
							} else if (Integer.parseInt(calYear)>= 2020	&& Integer.parseInt(calYear) < 2025) {
								rateOfInterest = 8.25;
							} 

						 	empNetInterest=new Double(df.format(((totalEmpNet*rateOfInterest)/100/12))).doubleValue();
						    aaiNetInterest=new Double(df.format(((totalAAINet*rateOfInterest)/100/12))).doubleValue();
						    //For Pension Contribution attribute,we are not cummlative
						    pensionInterest=new Double(df.format(((totalPensionContr*rateOfInterest)/100))).doubleValue();
						    totalEmpIntNet=new Double(Math.round(empNetInterest+totalEmpNet)).doubleValue();
						    totalAaiIntNet=new Double(Math.round(totalAAINet+aaiNetInterest)).doubleValue();
						    System.out.println("********************************************************************************");
						    System.out.println("dispTotalAAINet"+dispTotalAAINet+"aaiNetInterest"+aaiNetInterest+"totalEmpNet"+totalEmpNet);
						    System.out.println("dispTotalEmpNet"+dispTotalEmpNet+"empNetInterest"+empNetInterest+"totalAAINet"+totalAAINet);
						   
						    System.out.println("*****************************************noOfmonthsForInterest***************************************"+noOfmonthsForInterest);
						 
							boolean obCheckflag=false;
						    if(noOfmonthsForInterest<12){
						    	obCheckflag=false;
						    	empNetOB = (Long) OBList.get(0);
								aaiNetOB = (Long) OBList.get(1);
								pensionOB = (Long) OBList.get(2);
								principalOB = (Long) OBList.get(9);
								obFromTrFlag = (String) OBList.get(3);
								obYear = (String) OBList.get(4);
								if (OBList.size() > 4) {
									empNetAdjOB = (Long) OBList.get(5);
									aaiNetAdjOB = (Long) OBList.get(6);
									pensionAdjOB = (Long) OBList.get(7);
									empSubAdjOB = (Long) OBList.get(8);
									principalAdjOB = new Long(0);
								}															
								finalEmpNetOB = new Double(empNetOB.longValue()
										+ new Double(Math.round(dispTotalEmpNet)).longValue()
										+ empSubAdjOB.longValue()).longValue();
								
								finalAaiNetOB = new Double(aaiNetOB.longValue()
										+ new Double(Math
												.round(dispTotalAAINet)).longValue()
										+ aaiNetAdjOB.longValue()).longValue();
						    }else{
						    	obCheckflag=true;
							 	closingOBList=this.calOBPFCard(rateOfInterest, OBList, Math
										.round(dispTotalAAINet), Math.round(0),
										Math.round(dispTotalEmpNet),
										Math.round(0), Math
												.round(totalPensionContr), empNetInterest, totalAdvances,
										principle,noOfmonthsForInterest);
						    }

						 	
						
						   
							 
							   buffer.append(totalEmoluments);
							   buffer.append(",");
							   buffer.append(empTotal);
							   buffer.append(",");
					 	  if(obCheckflag==true){
					 	 	   log.info("totalEmpNet===Opening Balances obEmpNet"+totalEmpNet+"totalAAINet  :::::"+closingOBList.get(4).toString()+"====="+totalAAINet);
						 	   
							 	  buffer.append((Long)(closingOBList.get(0)));
							 	  buffer.append(",");
							 	  buffer.append(Math.round(empNetInterest));
							 	  buffer.append(",");
								  buffer.append((Long)closingOBList.get(1));
								  buffer.append(",");
								  buffer.append(aaiNetInterest);
								  buffer.append(",");	
					 	  }else{
					 	 	   log.info("totalEmpNet===Opening Balances obCheckflag"+obCheckflag+" finalEmpNetOB"+finalEmpNetOB+"finalAaiNetOB====="+finalAaiNetOB);
						 	   
							 	  buffer.append(finalEmpNetOB);
							 	  buffer.append(",");
							 	  buffer.append(Math.round(0));
							 	  buffer.append(",");
								  buffer.append(finalAaiNetOB);
								  buffer.append(",");
								  buffer.append(Math.round(0));
								  buffer.append(",");	
					 	  }
					
					  }
					
					
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						commonDB.closeConnection(con,null,null);
					}
					return buffer.toString();
				}
			 //	New Method for advances By Radha On 15-Jul-2011 For Not Connecting with RPFC Changes
			 public ArrayList advancesEmpPFCardReportPrint(String range, String region,
						String selectedYear, String empNameFlag, String empName,
						String sortedColumn, String pensionno, String lastmonthFlag,
						String lastmonthYear,String airportcode) {
					log.info("FinancialReportDAO::empPFCardReportPrint");
					String fromYear = "", toYear = "", dateOfRetriment = "";
					Connection con = null;
					if (!selectedYear.equals("Select One")) {
						fromYear = "01-Apr-" + selectedYear;
						int toSelectYear = 0;
						toSelectYear = Integer.parseInt(selectedYear) + 1;
						toYear = "01-Mar-" + toSelectYear;
					} else {
						fromYear = "01-Apr-1995";
						toYear = "01-Mar-2011";
					}
					int formFrmYear = 0, formToYear = 0, finalSttlementDtYear = 0, formMonthYear = 0;
					try {
						formFrmYear = Integer.parseInt(commonUtil.converDBToAppFormat(
								fromYear, "dd-MMM-yyyy", "yyyy"));
						formToYear = Integer.parseInt(commonUtil.converDBToAppFormat(
								toYear, "dd-MMM-yyyy", "yyyy"));

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidDataException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList empDataList = new ArrayList();
					EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
					EmployeeCardReportInfo cardInfo = null;
					ArrayList list = new ArrayList();
					ArrayList ptwList = new ArrayList();
					ArrayList finalSttmentList = new ArrayList();
					String appEmpNameQry = "", finalSettlementDate = "";
					ArrayList cardList = new ArrayList();
					int arrerMonths=0;
					boolean finalStFlag = false;
					try {
						con = commonDB.getConnection();
						empDataList = this.getEmployeePFInfoPrinting(con, range, region,
								empNameFlag, empName, sortedColumn, pensionno,
								lastmonthFlag, lastmonthYear,airportcode,fromYear,toYear);

						for (int i = 0; i < empDataList.size(); i++) {
							cardInfo = new EmployeeCardReportInfo();
							personalInfo = (EmployeePersonalInfo) empDataList.get(i);
							try {
								dateOfRetriment = this.getRetriedDate(personalInfo
										.getDateOfBirth());
							} catch (InvalidDataException e) {
								// TODO Auto-generated catch block
								log.printStackTrace(e);
							}
							log.info("FincialReportDAO:::empPFCardReportPrint:Final Settlement Date"+personalInfo.getFinalSettlementDate()+"Resttlement Date"+personalInfo.getResttlmentDate());
							if (!personalInfo.getFinalSettlementDate().equals("---")) {
								finalSttlementDtYear = Integer.parseInt(commonUtil
										.converDBToAppFormat(personalInfo
												.getFinalSettlementDate(), "dd-MMM-yyyy",
												"yyyy"));
								formMonthYear = Integer.parseInt(commonUtil
										.converDBToAppFormat(personalInfo
												.getFinalSettlementDate(), "dd-MMM-yyyy",
												"MM"));
								
								if ( finalSttlementDtYear<= formFrmYear) {
									finalStFlag = true;
									log.info("finalSttlementDtYear<= formFrmYear");
								} else if (formToYear == finalSttlementDtYear
										&& formMonthYear < 4) {
									finalStFlag = true;
									log.info("formToYear == finalSttlementDtYear&& formMonthYear < 4");
								} else {
									finalStFlag = false;
									log.info("con2 else");
								}
								
								if (finalStFlag == true) {

									finalSettlementDate = commonUtil.converDBToAppFormat(
											personalInfo.getFinalSettlementDate(),
											"dd-MMM-yyyy", "MMM-yyyy");
								} else {
									personalInfo.setFinalSettlementDate("---");
									finalSettlementDate = "---";
								}

							} else {
								finalSettlementDate = "---";
							}
							log.info("Before ProcessingAdjOB formFrmYear" + formFrmYear + "formToYear"
									+ formToYear + "finalSttlementDtYear"+finalSttlementDtYear+"finalStFlag" + finalStFlag
									+ "formMonthYear" + formMonthYear+"Final Settlement Date" + finalSettlementDate);
							
							 
							list = this.getEmployeePensionCard(con, fromYear, toYear,
									personalInfo.getPfIDString(), personalInfo
											.getWetherOption(), personalInfo.getRegion(),
									true, dateOfRetriment, personalInfo.getDateOfBirth(),
									personalInfo.getOldPensionNo(), finalSettlementDate);
							
							// Flag is not used in the last paramter of getPTWDetails method

							ptwList = this.getPTWDetails(con, fromYear, toYear,
									personalInfo.getCpfAccno(), personalInfo
											.getOldPensionNo());

							finalSttmentList = this.getFinalSettlement(con, fromYear,
									toYear, personalInfo.getPfIDString(), personalInfo
											.getOldPensionNo());
							log.info("PF Card====================Final Settlement Date"+personalInfo.getFinalSettlementDate());
									
							if (!personalInfo.getFinalSettlementDate().equals("---")) {
								arrerMonths=this.getArrearInfo(con,fromYear, toYear,personalInfo.getOldPensionNo());
								log.info("Arrear+FinalSettlement"+commonUtil.compareTwoDates(
										finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy")));
								if(commonUtil.compareTwoDates(
										finalSettlementDate, commonUtil.converDBToAppFormat(fromYear,"dd-MMM-yyyy", "MMM-yyyy"))==true){
									//Final settlement date is lower than fromYear
									personalInfo.setChkArrearFlag("N");
									log.info("ChkArrearFlag"+personalInfo.getChkArrearFlag());
									if(!personalInfo.getResttlmentDate().equals("---")){
										cardInfo.setInterNoOfMonths(12);
										cardInfo.setNoOfMonths(this
												.numOfMnthFinalSettlement(commonUtil
														.converDBToAppFormat(personalInfo
																.getResttlmentDate(),
																"dd-MMM-yyyy", "MMM")));
									}else if(arrerMonths!=0){
										cardInfo.setInterNoOfMonths(12);
										cardInfo.setNoOfMonths(12);
										
									}else{
										cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
										cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
										
									}
										
								}else{
									
									personalInfo.setChkArrearFlag("Y");
									log.info("Else Condtion compareTwoDates ChkArrearFlag"+personalInfo.getChkArrearFlag());
									
									cardInfo.setNoOfMonths(this
											.numOfMnthFinalSettlement(commonUtil
													.converDBToAppFormat(personalInfo
															.getFinalSettlementDate(),
															"dd-MMM-yyyy", "MMM")));
									
									cardInfo.setArrearNoOfMonths(arrerMonths);
									cardInfo.setInterNoOfMonths(12);
								}
								
							} else {
								personalInfo.setChkArrearFlag("N");
								log.info("ChkArrearFlag"+personalInfo.getChkArrearFlag());
								cardInfo.setNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
								cardInfo.setInterNoOfMonths(this.getNoOfMonthsForPFID(fromYear));
							}
							log.info("PF Card====Final Settlement Date"+personalInfo.getFinalSettlementDate()+"Resettlement Date"+personalInfo.getResttlmentDate()+"fromYear"+fromYear+"NO.Of Months"+cardInfo.getNoOfMonths()+"arrerMonths======"+arrerMonths);
							cardInfo.setFinalSettmentList(finalSttmentList);
							cardInfo.setArrearInfo(this.getArrearData(con,fromYear, toYear,personalInfo.getOldPensionNo()));
							if(finalSttmentList.size()!=0){
								cardInfo.setOrderInfo(this.getSanctionOrderInfo(con,fromYear, toYear,personalInfo.getOldPensionNo()));
							}else{
								cardInfo.setOrderInfo("");
							}
							
							cardInfo.setPersonalInfo(personalInfo);
							cardInfo.setPensionCardList(list);
							
							
							cardInfo.setPtwList(ptwList);
							cardList.add(cardInfo);
						}

					} catch (Exception se) {
						log.printStackTrace(se);
					} finally {
						commonDB.closeConnection(con, null, null);
					}

					return cardList;
				}
}


