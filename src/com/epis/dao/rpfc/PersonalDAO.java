package com.epis.dao.rpfc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.BLOB;

import com.epis.bean.rpfc.DatabaseBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeAdlPensionInfo;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.NomineeBean;
import com.epis.bean.rpfc.NomineeStatusBean;
import com.epis.bean.rpfc.RPFCForm9Bean;
import com.epis.bean.rpfc.RegionBean;
import com.epis.bean.rpfc.SignatureMappingBean;
import com.epis.bean.rpfc.form3Bean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.DateValidation;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class PersonalDAO {
	Log log = new Log(PersonalDAO.class);

	DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();

	FinancialDAO financeDAO = new FinancialDAO();

	PensionDAO pensionDAO = new PensionDAO();

	CommonDAO commonDAO = new CommonDAO();

	/* Starting Nominee Screens done by saibaba */
	public int updatePension(EmpMasterBean bean) throws InvalidDataException {
		log.info("the pension updation starts....");
		Connection con = null;
		Statement st = null;
		String sql3 = "";
		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
		String nomineeOldName = "", empserialNo = "", rowId = "", newCpfAcno = "", pensionNo = "", editStatus = "";
		String tempData = "";
		String tempInfo[] = null;
		int count = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String strSql = "";
			nomineeRows = bean.getNomineeRow();
			newCpfAcno = bean.getNewCpfAcNo();
			log.info("the new cpfacno" + newCpfAcno);
			String region = bean.getRegion();
			ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
			DateValidation dateValidation = new DateValidation();
			int serialno = 0;
			for (int j = 0; j < nomineeList.size(); j++) {
				tempData = nomineeList.get(j).toString();
				tempInfo = tempData.split("@");
				nomineeName = tempInfo[0];
				System.out.println("tempInfo(updatePensionMaster)" + tempInfo);
				if (!tempInfo[1].equals("XXX")) {
					nomineeAddress = tempInfo[1];
				} else {
					nomineeAddress = "";
				}
				if (!tempInfo[2].equals("XXX")) {
					nomineeDob = tempInfo[2].toString().trim();
				} else {
					nomineeDob = "";
				}

				if (!tempInfo[3].equals("XXX")) {
					nomineeRelation = tempInfo[3];
				} else {
					nomineeRelation = "";
				}
				if (!tempInfo[4].equals("XXX")) {
					nameofGuardian = tempInfo[4];
				} else {
					nameofGuardian = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					gaurdianAddress = tempInfo[5];
				} else {
					gaurdianAddress = "";
				}

				if (!tempInfo[6].equals("XXX")) {
					nomineeOldName = tempInfo[6];
				} else {
					nomineeOldName = "";
				}
				if (!tempInfo[7].equals("XXX")) {
					empserialNo = tempInfo[7];
				} else
					empserialNo = "";
				if (!tempInfo[8].equals("XXX")) {
					pensionNo = tempInfo[8];
				} else
					pensionNo = "";
				if (!tempInfo[9].equals("XXX")) {
					editStatus = tempInfo[9];
				} else
					editStatus = "";
				if (!tempInfo[10].equals("XXX")) {
					totalShare = tempInfo[10];
				} else
					totalShare = "";
				log.info("nomineeDob" + nomineeDob);
				serialno++;
				log.info("the empserial no" + empserialNo + "ssfoe");
				log.info("the pensionno is" + pensionNo + "pension");
				if (empserialNo.equals("") && pensionNo.equals("")) {
					strSql = "insert into EMPLOYEE_NOMINEE_DTLS_REQ(cpfaccno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,region,SRNO,PENSIONNO,REQUESTEDDATE)values('"
							+ newCpfAcno
							+ "','"
							+ nomineeName
							+ "','"
							+ nomineeAddress
							+ "','"
							+ nomineeDob
							+ "','"
							+ nomineeRelation
							+ "','"
							+ nameofGuardian
							+ "','"
							+ gaurdianAddress
							+ "','"
							+ totalShare
							+ "','"
							+ region
							+ "','"
							+ serialno
							+ "','"
							+ bean.getEmpSerialNo() + "',sysdate)";
					log.info("the query is..." + strSql);
					count = st.executeUpdate(strSql);
				} else {
					int pensioncount = 0;
					int pensionrequestCount = 0;
					String nomineeRequest = "";
					String nomineeUpdation = "";
					pensioncount = CheckPensionNo(empserialNo, pensionNo,
							"Master");
					if (pensioncount != 0 && editStatus.equals("Y")) {
						nomineeRequest = "insertion";
					}
					pensionrequestCount = CheckPensionNo(empserialNo,
							pensionNo, "Child");
					if (pensionrequestCount != 0 && editStatus.equals("Y")) {
						nomineeUpdation = "updation";
					}
					if (nomineeRequest.equals("insertion")
							&& nomineeUpdation.equals("")) {
						strSql = "insert into EMPLOYEE_NOMINEE_DTLS_REQ(cpfaccno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,region,SRNO,PENSIONNO,REQUESTEDDATE)values('"
								+ newCpfAcno
								+ "','"
								+ nomineeName
								+ "','"
								+ nomineeAddress
								+ "','"
								+ nomineeDob
								+ "','"
								+ nomineeRelation
								+ "','"
								+ nameofGuardian
								+ "','"
								+ gaurdianAddress
								+ "','"
								+ totalShare
								+ "','"
								+ region
								+ "','"
								+ serialno
								+ "','"
								+ bean.getEmpSerialNo() + "',sysdate)";
						log.info("the query is..." + strSql);
						count = st.executeUpdate(strSql);
					}
					if ((nomineeRequest.equals("insertion") && nomineeUpdation
							.equals("updation"))
							|| (nomineeUpdation.equals("updation") && nomineeRequest
									.equals(""))) {
						strSql = "update EMPLOYEE_NOMINEE_DTLS_REQ set cpfaccno='"
								+ newCpfAcno
								+ "',nomineeName='"
								+ nomineeName
								+ "',nomineeAddress='"
								+ nomineeAddress
								+ "',nomineeDob='"
								+ nomineeDob
								+ "',nomineeRelation='"
								+ nomineeRelation
								+ "',nameofGuardian='"
								+ nameofGuardian
								+ "',GAURDIANADDRESS='"
								+ gaurdianAddress
								+ "',totalshare='"
								+ totalShare
								+ "',region='"
								+ region
								+ "',REQUESTEDDATE=sysdate,DELETEFLAG='Y' where SRNO='"
								+ serialno
								+ "' and   PENSIONNO='"
								+ bean.getEmpSerialNo() + "'";
						log.info("the updation query is" + strSql);
						count = st.executeUpdate(strSql);

					}

				}
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}

		return count;
	}

	public int CheckPensionNo(String serialNo, String pensionNO, String flag) {
		String query = "";
		int count = 0;
		try {
			if (flag.equals("Master")) {
				query = "SELECT COUNT(1) FROM EMPLOYEE_NOMINEE_DTLS WHERE PENSIONNO ='"
						+ pensionNO.trim()
						+ "'AND SRNO='"
						+ serialNo.trim()
						+ "'";
			} else {
				query = "SELECT COUNT(1) FROM EMPLOYEE_NOMINEE_DTLS_REQ WHERE PENSIONNO ='"
						+ pensionNO.trim()
						+ "'AND SRNO='"
						+ serialNo.trim()
						+ "' ";
			}
			count = DBUtility.getRecordCount(query);
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		return count;
	}

	public int nomineeRequestUpdate(EmpMasterBean bean) {
		Connection con = null;
		Statement st = null;
		String nomineeQuery = "";
		String tempData = "";
		String tempInfo[] = null;
		String nomineeRows = "", empSrno = "", PensionNo = "", nomineeRequest = "";
		int count = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			nomineeRows = bean.getNomineeRow();

			ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
			for (int j = 0; j < nomineeList.size(); j++) {
				tempData = nomineeList.get(j).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[0].equals("XXX")) {
					nomineeRequest = tempInfo[0];
				} else {
					nomineeRequest = "";
				}

				if (!tempInfo[1].equals("XXX")) {
					empSrno = tempInfo[1];
				} else {
					empSrno = "";
				}
				if (!tempInfo[2].equals("XXX")) {
					PensionNo = tempInfo[2];
				} else {
					PensionNo = "";
				}

				log.info("PersonalDAO:nomineeRequestUpdate::the accept is"
						+ nomineeRequest + "empSrno" + empSrno + "PensionNo"
						+ PensionNo);

				nomineeQuery = "UPDATE EMPLOYEE_NOMINEE_DTLS_REQ SET DELETEFLAG='"
						+ nomineeRequest
						+ "' WHERE PENSIONNO='"
						+ PensionNo
						+ "'AND SRNO='" + empSrno + "'";
				count = st.executeUpdate(nomineeQuery);

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return count;
	}

	public ArrayList nomineeRequestResult(String psfNo, String reqDate) {
		Connection con = null;
		EmpMasterBean bean = null;
		ResultSet rs = null;
		Statement st = null;
		String query = "";
		ArrayList nominee = new ArrayList();
		log.info("the psfno and reqdate is" + psfNo + reqDate);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			query = "SELECT DISTINCT INFO.PENSIONNO PENSIONNO,Cpfacno,TO_CHAR(DATEOFBIRTH,'dd/MON/YYYY')DATEOFBIRTH,DESEGNATION,AIRPORTCODE,INFO.REGION REGION ,EMPLOYEENO,EMPLOYEENAME FROM EMPLOYEE_PERSONAL_INFO INFO, EMPLOYEE_NOMINEE_DTLS_REQ REQ WHERE INFO.PENSIONNO=REQ.PENSIONNO AND REQ.DELETEFLAG='Y'";
			if (!psfNo.equals(""))
				query += " and info.pensionno='" + psfNo + "'";
			if (!reqDate.equals(""))
				query += " and to_date(to_char(REQ.REQUESTEDDATE,'dd/Mon/yyyy'))='"
						+ reqDate + "'";
			rs = st.executeQuery(query);
			while (rs.next()) {
				bean = new EmpMasterBean();
				bean.setPensionNumber(rs.getString("PENSIONNO"));
				bean.setCpfAcNo(rs.getString("Cpfacno"));
				bean.setDateofBirth(rs.getString("DATEOFBIRTH"));
				bean.setDesegnation(rs.getString("DESEGNATION"));
				bean.setEmpNumber(rs.getString("EMPLOYEENO"));
				bean.setEmpName(rs.getString("EMPLOYEENAME"));
				bean.setRegion("REGION");
				bean.setStation(rs.getString("AIRPORTCODE"));
				nominee.add(bean);

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			commonDB.closeConnection(null, st, con);
		}

		return nominee;

	}

	public ArrayList getNomineeStatusList(String pfid) {
		Connection con = null;
		NomineeStatusBean bean = null;
		ResultSet rs = null;
		Statement st = null;
		String query = "";
		ArrayList arl = new ArrayList();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			query = "select PENSIONNO,SRNO,nvl(CPFACCNO,'')CPFACCNO,NOMINEENAME,to_char(NOMINEEDOB,'dd/Mon/YYYY')NOMINEEDOB,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,TOTALSHARE,GAURDIANADDRESS,nvl(REGION,'')REGION,decode(deleteflag,'Y','Pending','A','Accept','R','Reject') deleteflag,to_char(REQUESTEDDATE,'dd/Mon/YYYY')REQUESTEDDATE FROM EMPLOYEE_NOMINEE_DTLS_REQ WHERE PENSIONNO='"
					+ pfid + "'";
			rs = st.executeQuery(query);
			while (rs.next()) {
				bean = new NomineeStatusBean();
				bean.setPensionNo(rs.getString("PENSIONNO"));
				bean.setEmpSrno(rs.getString("SRNO"));
				bean.setNomineeDOB(rs.getString("NOMINEEDOB"));
				bean.setNomineeName(rs.getString("NOMINEENAME"));
				bean.setNomineeAddress(rs.getString("NOMINEEADDRESS"));
				bean.setNomineeRelation(rs.getString("NOMINEERELATION"));
				bean.setNameoftheGuardian(rs.getString("NAMEOFGUARDIAN"));
				bean.setGuardianAddress(rs.getString("GAURDIANADDRESS"));
				bean.setRegion(rs.getString("REGION"));
				bean.setRequestDate(rs.getString("REQUESTEDDATE"));
				bean.setStatus(rs.getString("deleteflag"));

				arl.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			commonDB.closeConnection(null, st, con);
		}

		return arl;
	}

	public EmpMasterBean empPensionEdit(String pfid, String flag)
			throws Exception {
		EmpMasterBean editBean = new EmpMasterBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String query = "", query1 = "", query2 = "";

		query = "SELECT * FROM EMPLOYEE_PERSONAL_INFO WHERE PENSIONNO='" + pfid
				+ "'";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getString("employeename") != null)
					editBean.setEmpName(rs.getString("employeename").trim());
				else
					editBean.setEmpName("");
				if (rs.getString("CPFACNO") != null)
					editBean.setCpfAcNo(rs.getString("CPFACNO").trim());
				else
					editBean.setCpfAcNo("");
				if (rs.getString("EMPLOYEENO") != null)
					editBean.setEmpNumber(rs.getString("EMPLOYEENO").trim());
				else
					editBean.setEmpNumber("");

				if (rs.getString("desegnation") != null)
					editBean.setDesegnation(rs.getString("desegnation").trim());
				else
					editBean.setDesegnation("");

				if (rs.getString("EMP_LEVEL") != null)
					editBean.setEmpLevel(rs.getString("EMP_LEVEL"));
				else
					editBean.setEmpLevel("");

				if (rs.getString("DATEOFBIRTH") != null) {
					editBean.setDateofBirth(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFBIRTH").toString(), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
					// editBean.setDateofBirth(CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH").toString()));
				} else
					editBean.setDateofBirth("");
				if (rs.getString("DATEOFJOINING") != null)
					// editBean.setDateofJoining(CommonUtil.getStringtoDate(rs.getString("DATEOFJOINING").toString()));
					editBean.setDateofJoining(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFJOINING").toString(),
							"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofJoining("");

				if (rs.getString("DATEOFSEPERATION_REASON") != null)
					editBean.setSeperationReason(rs
							.getString("DATEOFSEPERATION_REASON"));
				else
					editBean.setSeperationReason("");

				if (rs.getString("DATEOFSEPERATION_DATE") != null)
					// editBean.setDateofSeperationDate(CommonUtil.getStringtoDate(rs.getString("DATEOFSEPERATION_DATE").toString()));
					editBean.setDateofSeperationDate(commonUtil
							.converDBToAppFormat(rs.getString(
									"DATEOFSEPERATION_DATE").toString(),
									"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofSeperationDate("");

				if (rs.getString("WHETHER_FORM1_NOM_RECEIVED") != null)
					editBean.setForm2Nomination(rs
							.getString("WHETHER_FORM1_NOM_RECEIVED"));
				else
					editBean.setForm2Nomination("");

				if (rs.getString("airportcode") != null)
					editBean.setStation(rs.getString("airportcode"));
				else
					editBean.setStation("");
				if (rs.getString("REFPENSIONNUMBER") != null)
					editBean.setPensionNumber(rs.getString("REFPENSIONNUMBER"));
				else
					editBean.setPensionNumber("");
				if (rs.getString("emailId") != null)
					editBean.setEmailId(rs.getString("emailId"));
				else
					editBean.setEmailId("");
				if (rs.getString("AIRPORTSERIALNUMBER") != null)
					editBean.setAirportSerialNumber(rs
							.getString("AIRPORTSERIALNUMBER"));
				else
					editBean.setAirportSerialNumber("");
				if (rs.getString("employeeno") != null)
					editBean.setEmpNumber(rs.getString("employeeno"));
				else
					editBean.setEmpNumber("");
				if (rs.getString("remarks") != null)
					editBean.setRemarks(rs.getString("remarks"));
				else
					editBean.setRemarks("");
				if (rs.getString("GENDER") != null)
					editBean.setSex(rs.getString("GENDER"));
				else
					editBean.setSex("");

				if (rs.getString("setDateOfAnnuation") != null)
					// editBean.setDateOfAnnuation(CommonUtil.getStringtoDate(rs.getString("setDateOfAnnuation")));
					editBean.setDateOfAnnuation(commonUtil.converDBToAppFormat(
							rs.getString("setDateOfAnnuation"), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
				else
					editBean.setDateOfAnnuation("");
				if (rs.getString("fhname") != null)
					editBean.setFhName(rs.getString("fhname"));
				else
					editBean.setFhName("");
				if (rs.getString("fhflag") != null)
					editBean.setFhFlag(rs.getString("fhflag"));
				else
					editBean.setFhFlag("");
				if (rs.getString("maritalstatus") != null)
					editBean.setMaritalStatus(rs.getString("maritalstatus"));
				else
					editBean.setMaritalStatus("");
				log.info("PERMANENTADDRESS" + rs.getString("PERMANENTADDRESS"));
				if (rs.getString("PERMANENTADDRESS") != null)
					editBean.setPermanentAddress(rs
							.getString("PERMANENTADDRESS"));
				else
					editBean.setPermanentAddress("");

				if (rs.getString("TEMPORATYADDRESS") != null)
					editBean.setTemporatyAddress(rs
							.getString("TEMPORATYADDRESS"));
				else
					editBean.setTemporatyAddress("");
				if (rs.getString("wetherOption") != null) {
					editBean.setWetherOption(rs.getString("wetherOption"));
					log.info("wetheroption is" + editBean.getWetherOption());
				} else
					editBean.setWetherOption("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region is" + editBean.getRegion());
				} else
					editBean.setRegion("");

				if (rs.getString("otherreason") != null) {
					editBean.setOtherReason(rs.getString("otherreason"));
					log.info("otherreason is" + editBean.getOtherReason());
				} else
					editBean.setOtherReason("");

				if (rs.getString("department") != null) {
					editBean.setDepartment(rs.getString("department"));
					log.info("department is" + editBean.getDepartment());
				} else
					editBean.setDepartment("");
				if (rs.getString("division") != null) {
					editBean.setDivision(rs.getString("division"));
					log.info("division is" + editBean.getDivision());
				} else
					editBean.setDivision("");

				if (rs.getString("empnomineesharable") != null) {
					editBean.setEmpNomineeSharable(rs
							.getString("empnomineesharable"));
					log.info("empnomineesharable is"
							+ editBean.getEmpNomineeSharable());
				} else
					editBean.setEmpNomineeSharable("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region  is" + editBean.getRegion());
				} else
					editBean.setRegion("");
				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					log.info("setEmpSerialNo  is" + editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");

				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					editBean.setPfid(commonDAO.getPFID(editBean.getEmpName(),
							editBean.getDateofBirth(), commonUtil.leadingZeros(
									5, editBean.getEmpSerialNo())));
					log.info("setEmpSerialNo  is" + editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");

			}
			log.info("the cpfacno" + editBean.getCpfAcNo());
			StringBuffer sbf1 = new StringBuffer();
			String nomineeName = "", nomineeAddress = "", nomineeDob = "";
			String nomineeRelation = "", nameofGuardian = "", guardianAddress = "", totalShare = "";
			String rowId = "";
			String srno = "";
			if (flag.equals("true")) {
				query1 = "select SRNO,NOMINEENAME,NOMINEEADDRESS,NOMINEEDOB,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,rowid from employee_nominee_dtls where PENSIONNO='"
						+ pfid.trim() + "'";
				rs2 = st.executeQuery(query1);

				while (rs2.next()) {

					if (rs2.getString("NOMINEENAME") != null) {
						nomineeName = rs2.getString("NOMINEENAME");
					} else {
						nomineeName = "xxx";
					}
					// nomineeName =rs.getString("NOMINEENAME");
					if (rs2.getString("NOMINEEADDRESS") != null) {
						nomineeAddress = rs2.getString("NOMINEEADDRESS");
					} else {
						nomineeAddress = "xxx";
					}
					if (rs2.getString("NOMINEEDOB") != null) {
						// nomineeDob =
						// CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
						nomineeDob = commonUtil.converDBToAppFormat(rs2
								.getString("NOMINEEDOB"), "yyyy-MM-dd",
								"dd-MMM-yyyy");
					} else {
						nomineeDob = "xxx";
					}
					if (rs2.getString("NOMINEERELATION") != null) {
						nomineeRelation = rs2.getString("NOMINEERELATION");
					} else {
						nomineeRelation = "xxx";
					}
					if (rs2.getString("NAMEOFGUARDIAN") != null) {
						nameofGuardian = rs2.getString("NAMEOFGUARDIAN");

					} else {
						nameofGuardian = "xxx";
					}
					if (rs2.getString("GAURDIANADDRESS") != null) {
						guardianAddress = rs2.getString("GAURDIANADDRESS");
						log.info("guardianAddress  " + guardianAddress);
					} else {
						guardianAddress = "xxx";
					}
					if (rs2.getString("TOTALSHARE") != null) {
						totalShare = rs2.getString("TOTALSHARE");
					} else {
						totalShare = "xxx";
					}
					if (rs2.getString("SRNO") != null) {
						srno = rs2.getString("SRNO");
					} else {
						srno = "xxx";
					}

					if (rs2.getString("rowId") != null) {
						rowId = rs2.getString("rowId");
					} else {
						rowId = "xxx";
					}
					// nomineeAddress=rs.getString("NOMINEEADDRESS");
					// nomineeDob=CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
					// nomineeRelation =rs.getString("NOMINEERELATION");
					// nameofGuardian = rs.getString("NAMEOFGUARDIAN");
					// totalShare = rs.getString("TOTALSHARE");
					sbf1.append(nomineeName + "@");
					sbf1.append(nomineeAddress + "@");
					sbf1.append(nomineeDob + "@");
					sbf1.append(nomineeRelation + "@");
					sbf1.append(nameofGuardian + "@");
					sbf1.append(guardianAddress + "@");
					sbf1.append(totalShare + "@");
					sbf1.append(srno + "@");
					sbf1.append(rowId + "***");

					log.info(sbf1.toString());
					// sbf1.append(nomineeRelation + "@");

				}
				query2 = "select SRNO,NOMINEENAME,NOMINEEADDRESS,NOMINEEDOB,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,rowid from employee_nominee_dtls_req where PENSIONNO='"
						+ pfid.trim() + "'";
				rs1 = st.executeQuery(query2);
				while (rs1.next()) {

					if (rs1.getString("NOMINEENAME") != null) {
						nomineeName = rs1.getString("NOMINEENAME");
					} else {
						nomineeName = "xxx";
					}
					// nomineeName =rs.getString("NOMINEENAME");
					if (rs1.getString("NOMINEEADDRESS") != null) {
						nomineeAddress = rs1.getString("NOMINEEADDRESS");
					} else {
						nomineeAddress = "xxx";
					}
					if (rs1.getString("NOMINEEDOB") != null) {
						// nomineeDob =
						// CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
						nomineeDob = commonUtil.converDBToAppFormat(rs1
								.getString("NOMINEEDOB"), "yyyy-MM-dd",
								"dd-MMM-yyyy");
					} else {
						nomineeDob = "xxx";
					}
					if (rs1.getString("NOMINEERELATION") != null) {
						nomineeRelation = rs1.getString("NOMINEERELATION");
					} else {
						nomineeRelation = "xxx";
					}
					if (rs1.getString("NAMEOFGUARDIAN") != null) {
						nameofGuardian = rs1.getString("NAMEOFGUARDIAN");

					} else {
						nameofGuardian = "xxx";
					}
					if (rs1.getString("GAURDIANADDRESS") != null) {
						guardianAddress = rs1.getString("GAURDIANADDRESS");
						log.info("guardianAddress  " + guardianAddress);
					} else {
						guardianAddress = "xxx";
					}
					if (rs1.getString("TOTALSHARE") != null) {
						totalShare = rs1.getString("TOTALSHARE");
					} else {
						totalShare = "xxx";
					}
					if (rs1.getString("SRNO") != null) {
						srno = rs1.getString("SRNO");
					} else {
						srno = "xxx";
					}

					if (rs1.getString("rowId") != null) {
						rowId = rs1.getString("rowId");
					} else {
						rowId = "xxx";
					}
					// nomineeAddress=rs.getString("NOMINEEADDRESS");
					// nomineeDob=CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
					// nomineeRelation =rs.getString("NOMINEERELATION");
					// nameofGuardian = rs.getString("NAMEOFGUARDIAN");
					// totalShare = rs.getString("TOTALSHARE");
					sbf1.append(nomineeName + "@");
					sbf1.append(nomineeAddress + "@");
					sbf1.append(nomineeDob + "@");
					sbf1.append(nomineeRelation + "@");
					sbf1.append(nameofGuardian + "@");
					sbf1.append(guardianAddress + "@");
					sbf1.append(totalShare + "@");
					sbf1.append(srno + "@");
					sbf1.append(rowId + "***");

					log.info("the requestupdate screen" + sbf1.toString());
					// sbf1.append(nomineeRelation + "@");

				}

				log.info("nominee records " + sbf1.toString());
				editBean.setNomineeRow(sbf1.toString());
			} else {
				query2 = "select SRNO,NOMINEENAME,NOMINEEADDRESS,NOMINEEDOB,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,rowid from employee_nominee_dtls_req where PENSIONNO='"
						+ pfid.trim() + "' and DELETEFLAG='Y'";
				rs1 = st.executeQuery(query2);
				while (rs1.next()) {

					if (rs1.getString("NOMINEENAME") != null) {
						nomineeName = rs1.getString("NOMINEENAME");
					} else {
						nomineeName = "xxx";
					}
					// nomineeName =rs.getString("NOMINEENAME");
					if (rs1.getString("NOMINEEADDRESS") != null) {
						nomineeAddress = rs1.getString("NOMINEEADDRESS");
					} else {
						nomineeAddress = "xxx";
					}
					if (rs1.getString("NOMINEEDOB") != null) {
						// nomineeDob =
						// CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
						nomineeDob = commonUtil.converDBToAppFormat(rs1
								.getString("NOMINEEDOB"), "yyyy-MM-dd",
								"dd-MMM-yyyy");
					} else {
						nomineeDob = "xxx";
					}
					if (rs1.getString("NOMINEERELATION") != null) {
						nomineeRelation = rs1.getString("NOMINEERELATION");
					} else {
						nomineeRelation = "xxx";
					}
					if (rs1.getString("NAMEOFGUARDIAN") != null) {
						nameofGuardian = rs1.getString("NAMEOFGUARDIAN");

					} else {
						nameofGuardian = "xxx";
					}
					if (rs1.getString("GAURDIANADDRESS") != null) {
						guardianAddress = rs1.getString("GAURDIANADDRESS");
						log.info("guardianAddress  " + guardianAddress);
					} else {
						guardianAddress = "xxx";
					}
					if (rs1.getString("TOTALSHARE") != null) {
						totalShare = rs1.getString("TOTALSHARE");
					} else {
						totalShare = "xxx";
					}
					if (rs1.getString("SRNO") != null) {
						srno = rs1.getString("SRNO");
					} else {
						srno = "xxx";
					}

					if (rs1.getString("rowId") != null) {
						rowId = rs1.getString("rowId");
					} else {
						rowId = "xxx";
					}
					// nomineeAddress=rs.getString("NOMINEEADDRESS");
					// nomineeDob=CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
					// nomineeRelation =rs.getString("NOMINEERELATION");
					// nameofGuardian = rs.getString("NAMEOFGUARDIAN");
					// totalShare = rs.getString("TOTALSHARE");
					sbf1.append(nomineeName + "@");
					sbf1.append(nomineeAddress + "@");
					sbf1.append(nomineeDob + "@");
					sbf1.append(nomineeRelation + "@");
					sbf1.append(nameofGuardian + "@");
					sbf1.append(guardianAddress + "@");
					sbf1.append(totalShare + "@");
					sbf1.append(srno + "@");
					sbf1.append(rowId + "***");

					log.info(sbf1.toString());
					// sbf1.append(nomineeRelation + "@");

				}

				log.info("nominee records " + sbf1.toString());
				editBean.setNomineeRow(sbf1.toString());

			}

		} catch (Exception e) {
			log.printStackTrace(e);
			System.out.println("Exception is" + e.getMessage());
		}

		finally {
			rs.close();
			st.close();
			con.close();
		}
		return editBean;
	}

	/* End of Nominee Screen done by saibaba */
	public String autoProcessingPersonalInfo(String selectedDate,
			String retiredDate, HashMap regionMap, String selectedairportCode,
			String userName, String ipName) throws IOException {
		ArrayList airportList = new ArrayList();
		String lastactive = "", region = "", airportCode = "", message = "";
		ArrayList form3List = new ArrayList();
		Set set = regionMap.entrySet();
		Iterator iter = set.iterator();
		ArrayList regionList = new ArrayList();
		RegionBean regionInfo = new RegionBean();
		int totalFailed = 0, totalInserted = 0, totalRecords = 0, form3Cnt = 0;
		Connection con = null;
		try {
			con = commonDB.getConnection();
			BufferedWriter fw = new BufferedWriter(new FileWriter(
					"c://missingImportData.txt"));
			BufferedWriter fw1 = new BufferedWriter(new FileWriter(
					"c://chqiadData.txt"));
			lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
			regionList = commonUtil.loadRegions();

			boolean chkMnthYearFlag = false;

			for (int rg = 0; rg < regionList.size(); rg++) {
				regionInfo = (RegionBean) regionList.get(rg);

				if (selectedairportCode.equals("NO-SELECT")) {
					if (regionInfo.getAaiCategory().equals("METRO AIRPORT")) {
						region = regionInfo.getRegion();
						airportList = null;
						airportList = new ArrayList();
						airportList.add(regionInfo.getAirportcode());
					} else {
						if (regionInfo.getAaiCategory().equals(
								"NON-METRO AIRPORT")) {
							region = regionInfo.getRegion();
							airportList = financeDAO.getPensionAirportList(
									region, "");
						}

					}

				} else {
					airportList.add(selectedairportCode);
				}

				if (chkMnthYearFlag = true) {
					for (int k = 0; k < airportList.size(); k++) {
						airportCode = (String) airportList.get(k);
						form3List = financeDAO.financeForm3Report(airportCode,
								selectedDate, region, retiredDate,
								"employeename", "", "false");
						form3Cnt = form3Cnt + form3List.size();
						for (int j = 0; j < form3List.size(); j++) {
							String ms = "";
							form3Bean form3 = new form3Bean();
							form3 = (form3Bean) form3List.get(j);

							try {
								totalRecords = this.processinPersonalInfo(con,
										form3, lastactive, region, fw,
										userName, ipName, selectedDate);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								log.printStackTrace(e);
							}
							if (totalRecords == 0) {
								totalFailed = totalFailed + 1;
							} else {
								totalInserted = totalInserted + 1;
							}
						}

					}
					form3List = null;
					form3List = new ArrayList();
					if (region.equals("CHAIAD")
							&& airportCode.equals("DPO IAD")) {
						chkMnthYearFlag = true;
					}

				}
				message = "Total Form 3 Records=====" + form3Cnt + "<br/>";
				message = "Total Inserted=====" + totalInserted + "<br/>";
				message = message + "Total Failed=====" + totalFailed + "<br/>";
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return message;
	}

	private int processinPersonalInfo(Connection con, form3Bean form3Bean,
			String lastactive, String region, BufferedWriter fw,
			String userName, String IPAddress, String selectedDate)
			throws SQLException {
		log.info("processinPersonalInfo========");
		String sqlSelQuery = "", totalRecords = "", pensionNumber = "", insertQry = "", updateQry = "", updateNomineeQry = "", updateFamilDetails = "";

		Statement st = null;
		Statement perSt = null;
		Statement nomineeSt = null;
		Statement familySt = null;
		int i = 0, totalInserted = 0, totalFail = 0, j = 0, k = 0;
		selectedDate = selectedDate.replaceAll("%", "01");
		try {
			pensionNumber = this.getSequenceNo(con);
			if (!pensionNumber.equals("")) {
				sqlSelQuery = "select PENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
						+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,SEX,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,"
						+ Long.parseLong(pensionNumber)
						+ ",'"
						+ userName
						+ "','"
						+ lastactive
						+ "','"
						+ selectedDate
						+ "','"
						+ IPAddress
						+ "' "
						+ " FROM employee_info WHERE  ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ form3Bean.getCpfaccno().trim()
						+ "' AND REGION='"
						+ region.trim() + "')";
				log.info(sqlSelQuery);
				st = con.createStatement();
				perSt = con.createStatement();
				nomineeSt = con.createStatement();
				familySt = con.createStatement();
				insertQry = "insert into employee_personal_info(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
						+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,IPAddress)  "
						+ sqlSelQuery;

				log.info(insertQry);
				i = st.executeUpdate(insertQry);
				updateQry = "UPDATE EMPLOYEE_INFO SET EMPSERIALNUMBER='"
						+ pensionNumber
						+ "' WHERE ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "')";
				i = perSt.executeUpdate(updateQry);
				updateNomineeQry = "UPDATE EMPLOYEE_NOMINEE_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "'";
				j = nomineeSt.executeUpdate(updateNomineeQry);
				updateFamilDetails = "UPDATE EMPLOYEE_FAMILY_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "'";
				k = familySt.executeUpdate(updateFamilDetails);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);

			if (!(insertQry.equals("") || updateQry.equals(""))) {
				writeFailedQueryErrorLog(insertQry, fw);
				writeFailedQueryErrorLog(updateQry, fw);
				writeFailedQueryErrorLog(updateNomineeQry, fw);
				writeFailedQueryErrorLog(updateFamilDetails, fw);
			}

		} catch (Exception e) {
			log.printStackTrace(e);

			if (!(insertQry.equals("") || updateQry.equals(""))) {
				writeFailedQueryErrorLog(insertQry, fw);
				writeFailedQueryErrorLog(updateQry, fw);
				writeFailedQueryErrorLog(updateNomineeQry, fw);
				writeFailedQueryErrorLog(updateFamilDetails, fw);
			}

		} finally {
			if (perSt != null) {
				try {
					perSt.close();
					perSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (nomineeSt != null) {
				try {
					nomineeSt.close();
					nomineeSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (familySt != null) {
				try {
					familySt.close();
					familySt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}

		}

		return i;
	}

	public int addPersonalInfo(String pensionNumber, String cpfaccno,
			String lastactive, String region, String userName,
			String IPAddress, String selectedDate) throws SQLException {
		log.info("processinPersonalInfo========");
		String sqlSelQuery = "", insertQry = "", updateQry = "", updateNomineeQry = "", updateFamilDetails = "";
		Connection con = null;
		Statement st = null;
		Statement perSt = null;
		Statement nomineeSt = null;
		Statement familySt = null;
		int i = 0, totalInserted = 0, totalFail = 0, j = 0, k = 0;
		selectedDate = selectedDate.replaceAll("%", "01");
		try {
			con = commonDB.getConnection();
			if (!pensionNumber.equals("")) {
				sqlSelQuery = "select PENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
						+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,SEX,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,"
						+ Long.parseLong(pensionNumber)
						+ ",'"
						+ userName
						+ "','"
						+ lastactive
						+ "','"
						+ selectedDate
						+ "','"
						+ IPAddress
						+ "' "
						+ " FROM employee_info WHERE  ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ cpfaccno.trim()
						+ "' AND REGION='"
						+ region.trim()
						+ "')";
				log.info(sqlSelQuery);
				st = con.createStatement();
				perSt = con.createStatement();
				nomineeSt = con.createStatement();
				familySt = con.createStatement();
				insertQry = "insert into employee_personal_info(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
						+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,IPAddress)  "
						+ sqlSelQuery;

				log.info(insertQry);
				i = st.executeUpdate(insertQry);
				updateQry = "UPDATE EMPLOYEE_INFO SET EMPSERIALNUMBER='"
						+ pensionNumber
						+ "' WHERE ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ cpfaccno.trim() + "' AND REGION='" + region.trim()
						+ "')";
				i = perSt.executeUpdate(updateQry);
				updateNomineeQry = "UPDATE EMPLOYEE_NOMINEE_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ cpfaccno.trim() + "' AND REGION='" + region.trim()
						+ "'";
				j = nomineeSt.executeUpdate(updateNomineeQry);
				updateFamilDetails = "UPDATE EMPLOYEE_FAMILY_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ cpfaccno.trim() + "' AND REGION='" + region.trim()
						+ "'";
				k = familySt.executeUpdate(updateFamilDetails);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (perSt != null) {
				try {
					perSt.close();
					perSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (nomineeSt != null) {
				try {
					nomineeSt.close();
					nomineeSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (familySt != null) {
				try {
					familySt.close();
					familySt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}

		}

		return i;
	}

	private void writeFailedQueryErrorLog(String query, BufferedWriter fw) {
		try {
			fw.write(query + ";");
			fw.newLine();
			fw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
	}

	public String getSequenceNo(Connection con) throws Exception {
		String selQuery = "SELECT PERPENSIONNO.nextval AS PENSIONNO FROM DUAL";

		Statement st = null;
		ResultSet rs = null;
		String pensionNo = "";
		try {

			st = con.createStatement();
			rs = st.executeQuery(selQuery);
			if (rs.next()) {
				pensionNo = rs.getString("PENSIONNO");
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
		return pensionNo;
	}

	public boolean checkRefMnthYear(String selectedDate, String region) {
		log.info("PersonalDAO :checkRefMnthYear() entering method");
		String qryRegion = "";
		if (!region.equals("")) {
			qryRegion = " AND REGION='" + region + "'";
		} else {
			qryRegion = "";
		}
		selectedDate = selectedDate.replaceAll("%", "01");
		String selQuery = "SELECT COUNT(*) AS COUNT FROM EMPLOYEE_PERSONAL_INFO WHERE TO_CHAR(REFMONTHYEAR,'dd-Mon-yyyy')='"
				+ selectedDate + "'" + qryRegion;
		log.info("PersonalDAO : Select Query" + selQuery);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		int count = 0;
		boolean isAvailable = false;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selQuery);
			if (rs.next()) {
				count = rs.getInt("COUNT");
				if (count == 0) {
					isAvailable = true;
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return isAvailable;
	}

	public ArrayList searchPersonal(EmployeePersonalInfo empPersonalBean,
			int start, int gridLength, String sortingColumn, String flag,
			String pageFlag) throws Exception {

		log.info("PersonalDAO :searchPersonal() entering method");
		ArrayList empinfo = new ArrayList();

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sqlQuery = "";
		DatabaseBean searchData = new DatabaseBean();

		if (empPersonalBean.getPensionNo() != null) {
			empPersonalBean
					.setPensionNo(commonUtil.trailingZeros(empPersonalBean
							.getPensionNo().toCharArray()));
		}

		if (!pageFlag.equals("form10D")) {
			sqlQuery = this.personalBuildQuery(empPersonalBean, flag, "",
					sortingColumn);
		} else {
			sqlQuery = this.form10DPersonalBuildQuery(empPersonalBean, flag,
					"", sortingColumn);
		}

		log.info("sql query " + sqlQuery);
		empinfo = new ArrayList();
		int countGridLength = 0;
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		String employeeName = "";
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			EmployeePersonalInfo data = null;
			while (rs.next()) {

				log.info("gridLength" + gridLength + "countGridLength"
						+ countGridLength);

				data = new EmployeePersonalInfo();
				log.info("loadPersonalInfo=======IF======="
						+ rs.getString("cpfacno") + "countGridLength"
						+ countGridLength);
				data = loadPersonalInfo(rs);
				empinfo.add(data);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, rs);
		}
		log.info("PersonalDAO :searchPersonal leaving method");
		return empinfo;
	}

	private EmployeePersonalInfo loadPersonalInfo(ResultSet rs)
			throws SQLException {
		EmployeePersonalInfo personal = new EmployeePersonalInfo();
		log.info("loadPersonalInfo==============");

		if (rs.getString("cpfacno") != null) {
			personal.setCpfAccno(rs.getString("cpfacno"));
		} else {
			personal.setCpfAccno("---");
		}
		log
				.info("loadPersonalInfo=============="
						+ rs.getString("airportcode"));
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
			log.info("setOldPensionNo " + personal.getOldPensionNo());
		} else {
			personal.setOldPensionNo("---");
		}
		if (rs.getString("PENSIONNO") != null) {
			personal.setPensionNo(commonUtil.leadingZeros(5, rs
					.getString("PENSIONNO")));
			log.info("pfno " + personal.getPensionNo());
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

		if ((personal.getCpfAccno().equals("")
				|| (personal.getEmployeeNumber().equals(""))
				|| (personal.getDateOfBirth().equals(""))
				|| (personal.getWetherOption().equals("")) || (personal
				.getEmployeeName().equals("")))) {
			personal.setRemarks("incomplete Data");
			// log.info("inside if");
		} else {
			personal.setRemarks("---");
			// log.info("inside else");
		}

		if (rs.getString("lastactive") != null) {
			personal.setLastActive(commonUtil.getDatetoString(rs
					.getDate("lastactive"), "dd-MMM-yyyy"));
		} else {
			personal.setLastActive("---");
		}

		if (rs.getString("GENDER") != null) {
			personal.setGender(rs.getString("GENDER"));
			log.info("gender " + rs.getString("GENDER").toString());
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
			String personalPFID = commonDAO.getPFID(personal.getEmployeeName(),
					personal.getDateOfBirth(), personal.getPensionNo());
			personal.setPfID(personalPFID);
		} else {
			personal.setPfID(personal.getPensionNo());
		}

		return personal;

	}

	public String personalBuildQuery(EmployeePersonalInfo bean, String flag,
			String empNamechek, String sortingColumn) {

		log.info("PersonalDao:personalBuildQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
		log.info("empname check " + bean.getChkEmpNameFlag()
				+ "empNamechek====" + empNamechek);
		if (!flag.equals("count")) {
			sqlQuery = "SELECT * from employee_personal_info where empflag='Y' "
					+ empNamechek;
			/*
			 * sqlQuery = "SELECT
			 * CPFACNO,REFPENSIONNUMBER,EMPLOYEENO,EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,AIRPORTCODE,GENDER,FHNAME" +
			 * ",MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REMARKS,PENSIONNO,REGION,LASTACTIVE
			 * from employee_personal_info where empflag='Y'";
			 */
		} else {
			sqlQuery = "SELECT COUNT(*) as COUNT from employee_personal_info where empflag='Y' "
					+ empNamechek;
		}

		if (!bean.getCpfAccno().equals("")) {
			whereClause.append(" LOWER(cpfacno) ='"
					+ bean.getCpfAccno().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getPensionNo().equals("")) {
			whereClause.append(" PENSIONNO =" + bean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfBirth().equals("")) {

			if (bean.getDateOfBirth().indexOf("-") != -1
					&& bean.getDateOfBirth().length() == 11) {
				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd-MON-yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else if (bean.getDateOfBirth().length() == 10
					&& bean.getDateOfBirth().indexOf("/") != -1) {

				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd/MM/yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else if (bean.getDateOfBirth().length() == 10
					&& bean.getDateOfBirth().indexOf("-") != -1) {

				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd-MM-yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else {
				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd/MON/yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			}
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfJoining().equals("")) {
			if (bean.getDateOfJoining().indexOf("-") != -1
					&& bean.getDateOfJoining().length() == 11) {
				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MON-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else if (bean.getDateOfJoining().length() == 10
					&& bean.getDateOfJoining().indexOf("/") != -1) {

				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd/MM/yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else if (bean.getDateOfJoining().length() == 10
					&& bean.getDateOfJoining().indexOf("-") != -1) {

				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MM-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else {
				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MON-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");

			}
			whereClause.append(" AND ");
		}
		if (!bean.getAirportCode().equals("")) {
			whereClause.append(" LOWER(airportcode)='"
					+ bean.getAirportCode().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(employeename) like'%"
					+ bean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!bean.getDesignation().equals("")) {
			whereClause.append(" LOWER(desegnation)='"
					+ bean.getDesignation().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeNumber().equals("")) {
			whereClause
					.append(" EMPLOYEENO='" + bean.getEmployeeNumber() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getRegion().equals("")) {
			whereClause.append(" region='" + bean.getRegion() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (bean.getAirportCode().equals("")
				&& bean.getPensionNo().equals("")
				&& (bean.getDateOfBirth().equals("") || bean.getDateOfBirth()
						.equals(""))
				&& bean.getCpfAccno().equals("")
				&& (bean.getEmployeeName().equals(""))
				&& (bean.getDesignation().equals("") && (bean.getCpfAccno()
						.equals("")
						&& (bean.getEmployeeNumber().equals("")) && (bean
						.getRegion().equals("") && (bean.getDateOfJoining()
						.equals("")))))) {
			log.info("inside if");
		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" AND ");
			}
		}

		query.append(this.sTokenFormat(whereClause));
		String orderBy = " ORDER BY " + sortingColumn;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("PersonalDAO:buildQuery Leaving Method");
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

	public int totalCountPersonal(EmployeePersonalInfo empMasterBean,
			String flag, String sortingColumn, String pageFlag) {
		log.info("PersonalDAO :totalCountPersonal() entering method");
		Connection con = null;
		int totalRecords = 0;

		log.info("Sorting Column" + sortingColumn);
		String selectSQL = "";
		if (!pageFlag.equals("form10D")) {
			selectSQL = this.personalBuildQuery(empMasterBean, flag, "",
					sortingColumn);
		} else {
			selectSQL = this.form10DPersonalBuildQuery(empMasterBean, flag, "",
					sortingColumn);
		}
		log.info("Query =====" + selectSQL);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			if (rs.next()) {
				totalRecords = rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("PersonalDAO :totalCountPersonal() leaving method");
		return totalRecords;
	}

	public String form10DPersonalBuildQuery(EmployeePersonalInfo bean,
			String flag, String empNamechek, String sortingColumn) {

		log.info("PersonalDao:personalBuildQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
		log.info("empname check " + bean.getChkEmpNameFlag()
				+ "empNamechek====" + empNamechek);
		if (!flag.equals("count")) {
			sqlQuery = "SELECT * from employee_personal_info where empflag='Y' AND FINALSETTLMENTDT IS NOT NULL"
					+ empNamechek;
			/*
			 * sqlQuery = "SELECT
			 * CPFACNO,REFPENSIONNUMBER,EMPLOYEENO,EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,AIRPORTCODE,GENDER,FHNAME" +
			 * ",MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REMARKS,PENSIONNO,REGION,LASTACTIVE
			 * from employee_personal_info where empflag='Y'";
			 */
		} else {
			sqlQuery = "SELECT COUNT(*) as COUNT from employee_personal_info where empflag='Y' AND FINALSETTLMENTDT IS NOT NULL"
					+ empNamechek;
		}

		if (!bean.getCpfAccno().equals("")) {
			whereClause.append(" LOWER(cpfacno) ='"
					+ bean.getCpfAccno().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getPensionNo().equals("")) {
			whereClause.append(" PENSIONNO =" + bean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfBirth().equals("")) {

			if (bean.getDateOfBirth().indexOf("-") != -1
					&& bean.getDateOfBirth().length() == 11) {
				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd-MON-yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else if (bean.getDateOfBirth().length() == 10
					&& bean.getDateOfBirth().indexOf("/") != -1) {

				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd/MM/yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else if (bean.getDateOfBirth().length() == 10
					&& bean.getDateOfBirth().indexOf("-") != -1) {

				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd-MM-yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			} else {
				whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd/MON/yyyy') like '"
						+ bean.getDateOfBirth().toUpperCase() + "'");
			}
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfJoining().equals("")) {
			if (bean.getDateOfJoining().indexOf("-") != -1
					&& bean.getDateOfJoining().length() == 11) {
				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MON-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else if (bean.getDateOfJoining().length() == 10
					&& bean.getDateOfJoining().indexOf("/") != -1) {

				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd/MM/yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else if (bean.getDateOfJoining().length() == 10
					&& bean.getDateOfJoining().indexOf("-") != -1) {

				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MM-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");
			} else {
				whereClause
						.append(" TO_CHAR(DATEOFJOINING,'dd-MON-yyyy') like '"
								+ bean.getDateOfJoining().toUpperCase() + "'");

			}
			whereClause.append(" AND ");
		}
		if (!bean.getAirportCode().equals("")) {
			whereClause.append(" LOWER(airportcode)='"
					+ bean.getAirportCode().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(employeename) like'%"
					+ bean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!bean.getDesignation().equals("")) {
			whereClause.append(" LOWER(desegnation)='"
					+ bean.getDesignation().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeNumber().equals("")) {
			whereClause
					.append(" EMPLOYEENO='" + bean.getEmployeeNumber() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getRegion().equals("")) {
			whereClause.append(" region='" + bean.getRegion() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (bean.getAirportCode().equals("")
				&& bean.getPensionNo().equals("")
				&& (bean.getDateOfBirth().equals("") || bean.getDateOfBirth()
						.equals(""))
				&& bean.getCpfAccno().equals("")
				&& (bean.getEmployeeName().equals(""))
				&& (bean.getDesignation().equals("") && (bean.getCpfAccno()
						.equals("")
						&& (bean.getEmployeeNumber().equals("")) && (bean
						.getRegion().equals("") && (bean.getDateOfJoining()
						.equals("")))))) {
			log.info("inside if");
		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" AND ");
			}
		}

		query.append(this.sTokenFormat(whereClause));
		String orderBy = " ORDER BY " + sortingColumn;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("PersonalDAO:buildQuery Leaving Method");
		return dynamicQuery;
	}

	public EmployeePersonalInfo getPersonalInfo(Connection con, String pensionNo) {
		Statement st = null;
		ResultSet rs = null;
		String sqlSelQuery = "";
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		try {

			st = con.createStatement();
			sqlSelQuery = "SELECT CPFACNO, REFPENSIONNUMBER   , AIRPORTSERIALNUMBER, EMPLOYEENO   , EMPLOYEENAME , DESEGNATION  , EMP_LEVEL    , "
					+ "DATEOFBIRTH   , DATEOFJOINING , DATEOFSEPERATION_REASON    , DATEOFSEPERATION_DATE      , WHETHER_FORM1_NOM_RECEIVED , AIRPORTCODE, GENDER , "
					+ "FHNAME , MARITALSTATUS   , PERMANENTADDRESS, TEMPORATYADDRESS, WETHEROPTION    , SETDATEOFANNUATION, EMPFLAG    , OTHERREASON, DIVISION   , DEPARTMENT , EMAILID    , "
					+ "EMPNOMINEESHARABLE, REMARKS    , PENSIONNO  , REGION     , USERNAME   , LASTACTIVE , REFMONTHYEAR , IPADDRESS , FHFLAG    , MAPINGFLAG, PFSETTLED , FINALSETTLMENTDT  , "
					+ "OPTIONFORMRECEIVED,NATIONALITY,HEIGHT, MAPPEDUSERNM,to_char(add_months(DATEOFSEPERATION_DATE,-12),'dd-Mon-yyyy') AS SEPERATION_FROMDATE FROM EMPLOYEE_PERSONAL_INFO WHERE PENSIONNO="
					+ pensionNo;
			log.info("PersonalDAO:getPersonalInfo" + sqlSelQuery);
			st = con.createStatement();
			rs = st.executeQuery(sqlSelQuery);
			if (rs.next()) {
				personalInfo = commonDAO.loadPersonalInfo(rs);
				if (rs.getString("FINALSETTLMENTDT") != null) {
					personalInfo
							.setFinalSettlementDate(commonUtil
									.converDBToAppFormat(rs
											.getDate("FINALSETTLMENTDT")));
				} else {
					personalInfo.setFinalSettlementDate("---");
				}
				if (rs.getString("PERMANENTADDRESS") != null) {
					personalInfo
							.setPerAddress(rs.getString("PERMANENTADDRESS"));
				} else {
					personalInfo.setPerAddress("");
				}
				if (rs.getString("TEMPORATYADDRESS") != null) {
					personalInfo.setTempAddress(rs
							.getString("TEMPORATYADDRESS"));
				} else {
					personalInfo.setTempAddress("");
				}
				if (rs.getString("NATIONALITY") != null) {
					personalInfo.setNationality(rs.getString("NATIONALITY"));
				} else {
					personalInfo.setNationality("");
				}
				if (rs.getString("HEIGHT") != null) {
					personalInfo.setHeightWithInches(StringUtility.replace(
							rs.getString("HEIGHT").toCharArray(),
							".".toCharArray(), "'").toString());
				} else {
					personalInfo.setHeightWithInches("");
				}
				if (rs.getString("SEPERATION_FROMDATE") != null) {
					personalInfo.setSeperationFromDate(rs
							.getString("SEPERATION_FROMDATE"));
				} else {
					personalInfo.setSeperationFromDate("---");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return personalInfo;
	}

	public ArrayList personalReport(EmployeePersonalInfo empMasterBean,
			String flag, String empNameCheak, String sortingColumn) {
		log.info("PersonalDAO :personalReport() entering method");
		Connection con = null;
		String empNameCheck = "";
		EmployeePersonalInfo personal = null;
		ArrayList reportPersList = new ArrayList();

		int totalRecords = 0;
		if (empMasterBean.getPensionNo() != null) {
			empMasterBean.setPensionNo(commonUtil.trailingZeros(empMasterBean
					.getPensionNo().toCharArray()));
		}
		if (empMasterBean.getChkEmpNameFlag().equals("true")) {
			empNameCheck = " and info.employeename is null";
		}
		log.info("empNameCheck" + empNameCheck);
		String selectSQL = this.personalReportBuildQuery(empMasterBean, flag,
				empNameCheck, "PENSIONNO ASC");
		log.info("Query =====" + selectSQL);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			while (rs.next()) {
				personal = new EmployeePersonalInfo();
				personal = loadPersonalInfo(rs);
				personal.setTransferFlag(rs.getString("TRANSFERFLAG"));
				reportPersList.add(personal);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("PersonalDAO :personalReport() leaving method");
		return reportPersList;
	}

	public EmpMasterBean empPersonalEdit(String cpfacno, String empName,
			boolean flag, String empCode, String region, String pfid)
			throws Exception {
		log.info("PensionDAO:empPersonalEdit entering method ");
		EmpMasterBean editBean = new EmpMasterBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String query = "", query1 = "";
		if (flag == true) {
			if (!cpfacno.equals("") && !empName.equals("")) {
				query = " select * from EMPLOYEE_PERSONAL_INFO where  empflag='Y' AND LOWER(PENSIONNO) ='"
						+ pfid.toLowerCase().trim()
						+ "' AND region='"
						+ region.trim()
						+ "' AND LOWER(employeename)='"
						+ empName.toLowerCase() + "' order by PENSIONNO";

				log.info("query1" + query1);
			} else if (!cpfacno.equals("")) {
				query = " select * from EMPLOYEE_PERSONAL_INFO where ROWID IN(select MIN(ROWID) from EMPLOYEE_PERSONAL_INFO"
						+ " where empflag='Y' AND LOWER(PENSIONNO) ='"
						+ pfid.toLowerCase()
						+ "' AND region='"
						+ region.trim()
						+ "'  group by cpfacno) order by PENSIONNO";
			} else {
				query = " select * from EMPLOYEE_PERSONAL_INFO where ROWID IN(select MIN(ROWID) from employee_info"
						+ " where empflag='Y' AND LOWER(EMPLOYEENO) ='"
						+ empCode.toLowerCase()
						+ "' AND region='"
						+ region.trim() + "')";
			}
		} else {
			query = "select * from employee_info_invaliddata where cpfacno='"
					+ cpfacno + "' AND LOWER(employeename)='"
					+ empName.toLowerCase() + "'";
		}
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			editBean.setCpfAcNo(cpfacno.trim());
			while (rs.next()) {

				if (rs.getString("employeename") != null)
					editBean.setEmpName(rs.getString("employeename").trim());
				else
					editBean.setEmpName("");
				if (rs.getString("EMPLOYEENO") != null)
					editBean.setEmpNumber(rs.getString("EMPLOYEENO").trim());
				else
					editBean.setEmpNumber("");

				if (rs.getString("desegnation") != null)
					editBean.setDesegnation(rs.getString("desegnation").trim());
				else
					editBean.setDesegnation("");

				if (rs.getString("EMP_LEVEL") != null)
					editBean.setEmpLevel(rs.getString("EMP_LEVEL"));
				else
					editBean.setEmpLevel("");

				if (rs.getString("DATEOFBIRTH") != null) {
					editBean.setDateofBirth(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFBIRTH").toString(), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
					// editBean.setDateofBirth(CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH").toString()));
				} else
					editBean.setDateofBirth("");
				if (rs.getString("DATEOFJOINING") != null)
					// editBean.setDateofJoining(CommonUtil.getStringtoDate(rs.getString("DATEOFJOINING").toString()));
					editBean.setDateofJoining(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFJOINING").toString(),
							"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofJoining("");

				if (rs.getString("DATEOFSEPERATION_REASON") != null)
					editBean.setSeperationReason(rs
							.getString("DATEOFSEPERATION_REASON"));
				else
					editBean.setSeperationReason("");

				if (rs.getString("DATEOFSEPERATION_DATE") != null)
					// editBean.setDateofSeperationDate(CommonUtil.getStringtoDate(rs.getString("DATEOFSEPERATION_DATE").toString()));
					editBean.setDateofSeperationDate(commonUtil
							.converDBToAppFormat(rs.getString(
									"DATEOFSEPERATION_DATE").toString(),
									"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofSeperationDate("");

				if (rs.getString("WHETHER_FORM1_NOM_RECEIVED") != null)
					editBean.setForm2Nomination(rs
							.getString("WHETHER_FORM1_NOM_RECEIVED"));
				else
					editBean.setForm2Nomination("");

				if (rs.getString("airportcode") != null)
					editBean.setStation(rs.getString("airportcode"));
				else
					editBean.setStation("");
				if (rs.getString("REFPENSIONNUMBER") != null)
					editBean.setPensionNumber(rs.getString("REFPENSIONNUMBER"));
				else
					editBean.setPensionNumber("");
				if (rs.getString("emailId") != null)
					editBean.setEmailId(rs.getString("emailId"));
				else
					editBean.setEmailId("");
				if (rs.getString("AIRPORTSERIALNUMBER") != null)
					editBean.setAirportSerialNumber(rs
							.getString("AIRPORTSERIALNUMBER"));
				else
					editBean.setAirportSerialNumber("");
				if (rs.getString("employeeno") != null)
					editBean.setEmpNumber(rs.getString("employeeno"));
				else
					editBean.setEmpNumber("");
				if (rs.getString("remarks") != null)
					editBean.setRemarks(rs.getString("remarks"));
				else
					editBean.setRemarks("");
				if (rs.getString("GENDER") != null)
					editBean.setSex(rs.getString("GENDER"));
				else
					editBean.setSex("");

				if (rs.getString("setDateOfAnnuation") != null)
					// editBean.setDateOfAnnuation(CommonUtil.getStringtoDate(rs.getString("setDateOfAnnuation")));
					editBean.setDateOfAnnuation(commonUtil.converDBToAppFormat(
							rs.getString("setDateOfAnnuation"), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
				else
					editBean.setDateOfAnnuation("");
				if (rs.getString("fhname") != null)
					editBean.setFhName(rs.getString("fhname"));
				else
					editBean.setFhName("");
				if (rs.getString("fhflag") != null)
					editBean.setFhFlag(rs.getString("fhflag"));
				else
					editBean.setFhFlag("");
				if (rs.getString("maritalstatus") != null)
					editBean.setMaritalStatus(rs.getString("maritalstatus"));
				else
					editBean.setMaritalStatus("");
				log.info("PERMANENTADDRESS" + rs.getString("PERMANENTADDRESS"));
				if (rs.getString("PERMANENTADDRESS") != null)
					editBean.setPermanentAddress(rs
							.getString("PERMANENTADDRESS"));
				else
					editBean.setPermanentAddress("");

				if (rs.getString("TEMPORATYADDRESS") != null)
					editBean.setTemporatyAddress(rs
							.getString("TEMPORATYADDRESS"));
				else
					editBean.setTemporatyAddress("");
				if (rs.getString("wetherOption") != null) {
					editBean.setWetherOption(rs.getString("wetherOption"));
					log.info("wetheroption is" + editBean.getWetherOption());
				} else
					editBean.setWetherOption("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region is" + editBean.getRegion());
				} else
					editBean.setRegion("");

				if (rs.getString("otherreason") != null) {
					editBean.setOtherReason(rs.getString("otherreason"));
					log.info("otherreason is" + editBean.getOtherReason());
				} else
					editBean.setOtherReason("");

				if (rs.getString("department") != null) {
					editBean.setDepartment(rs.getString("department"));
					log.info("department is" + editBean.getDepartment());
				} else
					editBean.setDepartment("");
				if (rs.getString("division") != null) {
					editBean.setDivision(rs.getString("division"));
					log.info("division is" + editBean.getDivision());
				} else
					editBean.setDivision("");

				if (rs.getString("empnomineesharable") != null) {
					editBean.setEmpNomineeSharable(rs
							.getString("empnomineesharable"));
					log.info("empnomineesharable is"
							+ editBean.getEmpNomineeSharable());
				} else
					editBean.setEmpNomineeSharable("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region  is" + editBean.getRegion());
				} else
					editBean.setRegion("");
				if (rs.getString("OPTIONFORMRECEIVED") != null) {
					editBean.setOptionForm(rs.getString("OPTIONFORMRECEIVED"));
				} else {
					editBean.setOptionForm("");
				}
				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					log.info("setEmpSerialNo  is" + editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");

				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					editBean.setPfid(commonDAO.getPFID(editBean.getEmpName(),
							editBean.getDateofBirth(), commonUtil.leadingZeros(
									5, editBean.getEmpSerialNo())));
					log.info("setEmpSerialNo  is" + editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");
				/* Editing Family records */

				String familyInfo = this.loadFamilyDetails(pfid, con);

				editBean.setFamilyRow(familyInfo);
				/* End of Editing Family of records */

				/* Editing nominee records */
				String nomineeInfo = this.loadNomineeDetails(pfid, con);
				editBean.setNomineeRow(nomineeInfo);

			}

		} catch (Exception e) {
			log.printStackTrace(e);
			System.out.println("Exception is" + e.getMessage());
		}

		finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersionalDAO:empPersonalEdit leaving method ");
		return editBean;
	}

	public EmpMasterBean getDesegnation(String empLevel) {
		Connection con = null;
		EmpMasterBean bean = new EmpMasterBean();
		ResultSet rs = null;
		String designation = "";
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			String sql = "select * from employee_desegnation where empLevel='"
					+ empLevel.trim() + "'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				designation = (String) rs.getString("DESIGNATION").toString()
						.trim();
				if (!designation.equals("")) {
					bean.setDesegnation(designation);
				} else {
					bean.setDesegnation("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return bean;
	}

	public int personalUpdate(EmpMasterBean bean, String flag)
			throws InvalidDataException {
		log.info("PersonalDAO:updatePensionMaster entering method ");
		//		PensionBean editBean =new PensionBean();
		Connection con = null;
		Statement st = null;
		int count = 0;
		String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "", newCpfAcno = "";
		String empName = "", desegnation = "", empLevel = "", seperationReason = "", whetherOptionA = "";
		String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
		String remarks = "", station = "", dateofBirth = "", dateofJoining = "", dateofSeperationDate = "";
		String fMemberName = "", fDateofBirth = "", frelation = "", familyrows = "";
		String wetherOption = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "", dateOfAnnuation = "", otherReason = "";
		String pfId = "", personalQry = "", empQuery = "", sqlQuery = "";
		int count1[] = new int[3];
		pfId = bean.getPfid();
		airportSerialNumber = bean.getAirportSerialNumber();
		empNumber = bean.getEmpNumber();
		cpfAcNo = bean.getCpfAcNo().trim();
		empName = bean.getEmpName();
		desegnation = bean.getDesegnation();
		empLevel = bean.getEmpLevel();
		seperationReason = bean.getSeperationReason();
		whetherOptionA = bean.getWhetherOptionA();
		whetherOptionB = bean.getWhetherOptionB();
		whetherOptionNO = bean.getWhetherOptionNO();
		remarks = bean.getRemarks();
		dateofBirth = bean.getDateofBirth();
		dateofJoining = bean.getDateofJoining();
		dateofSeperationDate = bean.getDateofSeperationDate();
		form2Nomination = bean.getForm2Nomination();
		String pensionNumber = bean.getPensionNumber();
		String empNomineeSharable = bean.getEmpNomineeSharable();
		wetherOption = bean.getWetherOption();
		sex = bean.getSex();
		maritalStatus = bean.getMaritalStatus();
		fhName = bean.getFhName();
		permanentAddress = bean.getPermanentAddress();
		temporatyAddress = bean.getTemporatyAddress();
		dateOfAnnuation = bean.getDateOfAnnuation();
		//		String pensionNumber = this.getPensionNumber(empName.toUpperCase(),
		//		dateofBirth, cpfAcNo);
		otherReason = bean.getOtherReason().trim();
		String division = bean.getDivision();
		String department = bean.getDepartment();
		String emailId = bean.getEmailId();
		String empOldName = bean.getEmpOldName();
		String empOldNumber = bean.getEmpOldNumber();
		String setRecordVerified = bean.getRecordVerified();
		log.info("PersonalDAO:userName " + bean.getUserName());
		log.info("PersonalDAO:computer Name" + bean.getComputerName());
		java.util.Date now = new java.util.Date();
		String MY_DATE_FORMAT = "dd-MM-yyyy hh:mm a";
		String currDateTime = new SimpleDateFormat(MY_DATE_FORMAT).format(now);
		log.info("date is  " + currDateTime);
		String fhFlag = bean.getFhFlag();

		if (!bean.getChangedRegion().equals("")) {
			bean.setRegion(bean.getChangedRegion());
		}
		if (!bean.getChangedStation().equals("")) {
			bean.setStation(bean.getChangedStation());
		}

		try {

			con = commonDB.getConnection();
			st = con.createStatement();

			// new code
			// for familydata updation

			personalQry = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
					+ bean.getStation().trim() + "',employeename='"
					+ empName.trim() + "',desegnation='" + desegnation
					+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
					+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='" + empLevel
					+ "',DATEOFBIRTH ='" + dateofBirth + "',DATEOFJOINING='"
					+ dateofJoining + "',DATEOFSEPERATION_REASON='"
					+ seperationReason + "',DATEOFSEPERATION_DATE='"
					+ dateofSeperationDate + "',REMARKS='" + remarks.trim()
					+ "',gender='" + sex + "',maritalStatus='" + maritalStatus
					+ "',permanentAddress='" + permanentAddress
					+ "',temporatyAddress='" + temporatyAddress
					+ "',wetherOption='" + wetherOption
					+ "', WHETHER_FORM1_NOM_RECEIVED ='" + form2Nomination
					+ "',fhname='" + fhName + "',setDateOfAnnuation='"
					+ dateOfAnnuation + "',REFPENSIONNUMBER='" + pensionNumber
					+ "',otherreason='" + otherReason + "',division='"
					+ division + "',department='" + department + "',emailId='"
					+ emailId + "',lastactive='"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "',empnomineesharable='" + empNomineeSharable
					+ "',userName='" + bean.getUserName() + "',fhflag='"
					+ bean.getFhFlag() + "',region='" + bean.getRegion().trim()
					+ "',optionformreceived='" + bean.getOptionForm()
					+ "',CPFACNO='" + bean.getNewCpfAcNo()
					+ "'  where empflag='Y' and PENSIONNO='"
					+ bean.getEmpSerialNo() + "'";

			log.info("personalQry is " + personalQry);
			count = st.executeUpdate(personalQry);

			empQuery = "update  employee_info set DATEOFBIRTH='" + dateofBirth
					+ "',DATEOFJOINING='" + dateofJoining
					+ "',WHETHER_OPTION_A ='" + whetherOptionA
					+ "',WHETHER_OPTION_B ='" + whetherOptionB
					+ "',WHETHER_OPTION_NO='" + whetherOptionNO
					+ "',wetherOption='" + wetherOption
					+ "', WHETHER_FORM1_NOM_RECEIVED ='" + form2Nomination
					+ "',lastactive='"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy") + "',userName='"
					+ bean.getUserName()
					+ "' where empflag='Y'  and PENSIONNUMBER='"
					+ bean.getEmpSerialNo() + "'";

			log.info("empQuery is " + empQuery);
			st.executeUpdate(empQuery);

			this.updateNomineeDetails(bean, con);
			this.updateFamilyDetails(bean, con);

		} catch (SQLException sqle) {
			log.printStackTrace(sqle);
			if (sqle.getErrorCode() == 00001) {
				throw new InvalidDataException("PensionNumber Already Exist");
			}
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			commonDB.closeConnection(con, st, null);
		}
		log.info("PersonalDAO:updatePensionMaster leaving method ");
		return count;
	}

	public int checkPfAcnoInEmployeeInfo(String pfId, String region) {
		Connection con = null;
		Statement st = null;
		int count = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String query = "select EMPSERIALNUMBER from employee_info where region='"
					+ region + "' and EMPSERIALNUMBER='" + pfId + "'";
			count = st.executeUpdate(query);
		} catch (Exception e) {

		}

		return count;
	}

	public String addPersonalInfo(EmployeePersonalInfo bean,
			EmpMasterBean empBean, String userName, String ipAddress)
			throws Exception {
		log.info("PersonalDao :addPersonalInfo() entering method");
		boolean isaddPensionRecord = false;
		Connection conn = null;
		String pensionNo = "", refPensionNo = "", familyrows = "", nomineeRows = "", fMemberName = "", fDateofBirth = "", frelation = "";
		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", gaddress = "", totalShare = "";
		int success = 0;
		String uniqueID = "";

		Statement st = null;
		try {
			conn = commonDB.getConnection();
			pensionNo = this.getSequenceNo(conn);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
			throw e1;
		}
		log.info(" maritalStatus " + bean.getMaritalStatus());

		refPensionNo = commonDAO.getPFID(bean.getEmployeeName(), bean
				.getDateOfBirth(), pensionNo);
		log.info("Pension No" + pensionNo + "refPensionNo" + refPensionNo);
		String empFlag = "Y";
		try {

			st = conn.createStatement();
			String sql = "insert into employee_personal_info(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
					+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,FHFLAG,IPAddress)"
					+ " VALUES "
					+ "('"
					+ refPensionNo
					+ "','"
					+ bean.getCpfAccno()
					+ "','"
					+ bean.getAirportSerialNumber()
					+ "','"
					+ bean.getEmployeeNumber()
					+ "','"
					+ bean.getEmployeeName().toUpperCase()
					+ "','"
					+ bean.getDesignation()
					+ "','"
					+ bean.getEmpDesLevel()
					+ "','"
					+ bean.getDateOfBirth()
					+ "','"
					+ bean.getDateOfJoining()
					+ "','"
					+ bean.getSeperationReason()
					+ "','"
					+ bean.getSeperationDate()
					+ "','"
					+ bean.getForm2Nominee()
					+ "','"
					+ bean.getRemarks()
					+ "','"
					+ bean.getAirportCode()
					+ "','"
					+ bean.getGender()
					+ "','"
					+ bean.getFhName()
					+ "','"
					+ bean.getMaritalStatus()
					+ "','"
					+ bean.getPerAddress()
					+ "','"
					+ bean.getTempAddress()
					+ "','"
					+ bean.getWetherOption()
					+ "','"
					+ bean.getDateOfAnnuation()
					+ "','"
					+ bean.getDivision()
					+ "','"
					+ bean.getDepartment()
					+ "','"
					+ bean.getEmailID()
					+ "','"
					+ bean.getEmpNomineeSharable()
					+ "','"
					+ bean.getRegion()
					+ "','"
					+ pensionNo
					+ "','"
					+ userName
					+ "','"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ bean.getFhFlag() + "','" + ipAddress + "')";
			log.info(sql);
			success = st.executeUpdate(sql);
			if (success != 0) {
				uniqueID = pensionNo;
			}

			empBean.setPensionNumber(pensionNo);
			this.updateNomineeDetails(empBean, conn);
			this.updateFamilyDetails(empBean, conn);
			isaddPensionRecord = true;

		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new InvalidDataException();
			// e.printStackTrace();
		} catch (Exception e) {
			log.printStackTrace(e);
			// e.printStackTrace();
		} finally {
			commonDB.closeConnection(conn, st, null);
		}
		log.info("PersonalDao :addPersonalInfo()  leaving method");
		return uniqueID;
	}

	/*
	 * public String getPFID(String empName, String dateofBirth, String
	 * sequenceNo) { log.info("PersonalDao:getPFID() entering method");
	 * log.info("PersonalDao:getPFID() dateofBirth" + dateofBirth + "empName" +
	 * empName); // TODO Auto-generated method stub String finalName = "", fname =
	 * ""; SimpleDateFormat fromDate = null; int endIndxName = 0; String quats[] = {
	 * "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." }; String tempName = "",
	 * convertedDt = "";
	 * 
	 * try { if (dateofBirth.indexOf("-") != -1) { fromDate = new
	 * SimpleDateFormat("dd-MMM-yyyy"); } else { fromDate = new
	 * SimpleDateFormat("dd/MMM/yyyy"); } SimpleDateFormat toDate = new
	 * SimpleDateFormat("ddMMyy"); convertedDt =
	 * toDate.format(fromDate.parse(dateofBirth)); log.info("convertedDt" +
	 * convertedDt); int startIndxName = 0, index = 0; endIndxName = 1; for (int
	 * i = 0; i < quats.length; i++) { if (empName.indexOf(quats[i]) != -1) {
	 * tempName = empName.replaceAll(quats[i], "").trim(); //
	 * tempName=empName.substring(index+1,empName.length()); empName = tempName; } }
	 * finalName = empName.substring(startIndxName, endIndxName); finalName =
	 * empName.substring(startIndxName, endIndxName); if (empName.indexOf(" ") !=
	 * -1) { endIndxName = empName.lastIndexOf(" "); finalName = finalName +
	 * empName.substring(endIndxName).trim(); } else if (empName.indexOf(".") !=
	 * -1) { endIndxName = empName.lastIndexOf("."); finalName = finalName +
	 * empName.substring(endIndxName + 1, empName.length()) .trim(); } else {
	 * finalName = empName.substring(0, 2); } log.info("final name is" +
	 * finalName); char[] cArray = finalName.toCharArray(); fname =
	 * String.valueOf(cArray[0]); fname = fname + String.valueOf(cArray[1]);
	 * log.info(empName + " Short Name of " + fname);
	 *  } catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (Exception e) { log.info("Exception is " +
	 * e);
	 *  } log.info("PersonalDao:getPFID() leaving method"); return convertedDt +
	 * fname + sequenceNo;
	 *  }
	 */

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

	public ArrayList checkPersonalDtOfBirthInfo(
			EmployeePersonalInfo personalInfo, boolean empflag, boolean dobFlag) {
		log.info("PersonalDAO :checkPersonalDtOfBirthInfo() entering method");
		Connection con = null;
		String empNameCheck = "";
		EmployeePersonalInfo personal = null;
		ArrayList reportPersList = new ArrayList();

		int totalRecords = 0;

		String selectSQL = this.personalConditionsBuildQuery(personalInfo,
				empflag, dobFlag);
		log.info("Query =====" + selectSQL);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			while (rs.next()) {
				personal = new EmployeePersonalInfo();
				personal = loadPersonalInfo(rs);
				reportPersList.add(personal);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("PersonalDAO :checkPersonalDtOfBirthInfo() leaving method");
		return reportPersList;
	}

	public String personalConditionsBuildQuery(EmployeePersonalInfo bean,
			boolean empflag, boolean dobFlag) {

		log.info("PersonalDao:personalConditionsBuildQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
		sqlQuery = "SELECT  * from employee_personal_info where empflag='Y' ";
		log.info("EMployeename" + bean.getEmployeeName() + "empflag" + empflag);
		if (!bean.getEmployeeName().equals("") && empflag == true) {
			whereClause.append(" LOWER(employeename) like'%"
					+ bean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfBirth().equals("") && dobFlag == true) {
			whereClause.append(" TO_CHAR(DATEOFBIRTH,'dd-Mon-yyyy')='"
					+ bean.getDateOfBirth() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (bean.getEmployeeName().equals("")
				&& (bean.getDateOfBirth().equals(""))) {
			log.info("inside if");
		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" AND ");
			}
		}

		query.append(this.sTokenFormat(whereClause));
		String orderBy = " ORDER BY cpfacno";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("PersonalDao:personalConditionsBuildQuery-- Leaving Method");
		return dynamicQuery;
	}

	public int processinPersonalInfoMissingRecords(String cpfAccno,
			String region) throws SQLException {
		log.info("processinPersonalInfo========");
		String sqlSelQuery = "", totalRecords = "", pensionNumber = "", insertQry = "", updateQry = "", updateNomineeQry = "", updateFamilDetails = "";
		Connection con = null;
		Statement st = null;
		Statement perSt = null;
		Statement nomineeSt = null;
		Statement familySt = null;
		String userName = "SEHGAL";
		String selectedDate = "01-Sep-2007";
		String IPAddress = "192.168.2.13";

		int i = 0, totalInserted = 0, totalFail = 0, j = 0, k = 0;
		try {
			con = commonDB.getConnection();
			pensionNumber = this.getSequenceNo(con);
			if (!pensionNumber.equals("")) {
				sqlSelQuery = "select PENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
						+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,SEX,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,"
						+ Long.parseLong(pensionNumber)
						+ ",'"
						+ userName
						+ "','"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "','"
						+ selectedDate
						+ "','"
						+ IPAddress
						+ "' "
						+ " FROM employee_info WHERE  ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ cpfAccno.trim()
						+ "' AND REGION='"
						+ region.trim()
						+ "')";
				log.info(sqlSelQuery);
				insertQry = "insert into employee_personal_info(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
						+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,IPAddress)  "
						+ sqlSelQuery;
				updateQry = "UPDATE EMPLOYEE_INFO SET EMPSERIALNUMBER='"
						+ pensionNumber
						+ "' WHERE ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ cpfAccno.trim() + "' AND REGION='" + region.trim()
						+ "')";
				updateNomineeQry = "UPDATE EMPLOYEE_NOMINEE_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ cpfAccno.trim() + "' AND REGION='" + region.trim()
						+ "'";
				updateFamilDetails = "UPDATE EMPLOYEE_FAMILY_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ cpfAccno.trim() + "' AND REGION='" + region.trim()
						+ "'";

				log.info("insertQry  " + insertQry);
				log.info("updateQry " + updateQry);
				log.info("updateNomineeQry  " + updateNomineeQry);
				log.info("updateFamilDetails   " + updateFamilDetails);
				// con = commonDB.getConnection();
				st = con.createStatement();
				perSt = con.createStatement();
				nomineeSt = con.createStatement();
				familySt = con.createStatement();
				con.setAutoCommit(false);
				log.info(insertQry);
				i = st.executeUpdate(insertQry);

				i = perSt.executeUpdate(updateQry);
				j = nomineeSt.executeUpdate(updateNomineeQry);
				k = familySt.executeUpdate(updateFamilDetails);

				con.commit();
				con.setAutoCommit(true);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
			if (con != null) {
				con.rollback();
			}

		} catch (Exception e) {
			log.printStackTrace(e);
			if (con != null) {
				con.rollback();
			}

		} finally {
			if (perSt != null) {
				try {
					perSt.close();
					perSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (nomineeSt != null) {
				try {
					nomineeSt.close();
					nomineeSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (familySt != null) {
				try {
					familySt.close();
					familySt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			commonDB.closeConnection(con, st, null);
		}

		return i;
	}

	public ArrayList form2NomineeReport(EmployeePersonalInfo personalInfo) {
		log.info("PersonalDAO :form2NomineeReport() entering method");
		Connection con = null;
		String empNameCheck = "";
		NomineeBean nomineeInfo = null;
		ArrayList reportNomineeList = new ArrayList();

		if (personalInfo.getPensionNo() != null) {
			personalInfo.setPensionNo(commonUtil.trailingZeros(personalInfo
					.getPensionNo().toCharArray()));
		}
		String selectNomineeSQL = "SELECT * FROM EMPLOYEE_NOMINEE_DTLS WHERE PENSIONNO='"
				+ personalInfo.getPensionNo() + "' ORDER BY SRNO";
		log.info("Query =====" + selectNomineeSQL);
		Statement st = null;
		ResultSet rs = null;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selectNomineeSQL);
			while (rs.next()) {
				nomineeInfo = new NomineeBean();
				nomineeInfo = loadNominneeInfo(rs);
				reportNomineeList.add(nomineeInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersonalDAO :form2NomineeReport() leaving method");
		return reportNomineeList;
	}

	public ArrayList form2FamilyReport(EmployeePersonalInfo personalInfo) {
		log.info("PersonalDAO :form2FamilyReport() entering method");
		Connection con = null;
		NomineeBean familyInfo = null;
		ArrayList reportFamilyList = new ArrayList();
		int totalRecords = 0;
		if (personalInfo.getPensionNo() != null) {
			personalInfo.setPensionNo(commonUtil.trailingZeros(personalInfo
					.getPensionNo().toCharArray()));
		}
		/*
		 * String selectFamilySQL = "SELECT * FROM EMPLOYEE_FAMILY_DTLS WHERE
		 * SRNO='" + personalInfo.getPensionNo() + "' AND CPFACCNO='" +
		 * personalInfo.getCpfAccno() + "'";
		 */
		String selectFamilySQL = "SELECT * FROM EMPLOYEE_FAMILY_DTLS WHERE PENSIONNO='"
				+ personalInfo.getPensionNo() + "' ORDER BY SRNO";
		log.info("Query =====" + selectFamilySQL);
		Statement st = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selectFamilySQL);
			while (rs.next()) {
				familyInfo = new NomineeBean();
				familyInfo = loadFamilyInfo(rs);
				reportFamilyList.add(familyInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersonalDAO :form2FamilyReport() leaving method");
		return reportFamilyList;
	}

	private NomineeBean loadNominneeInfo(ResultSet rs) throws SQLException {
		NomineeBean nominee = new NomineeBean();
		log.info("loadNominneeInfo==============");
		log.info("loadNominneeInfo==============" + rs.getString("SRNO"));
		if (rs.getString("CPFACCNO") != null) {
			nominee.setCpfaccno(rs.getString("CPFACCNO"));
		} else {
			nominee.setCpfaccno("");
		}
		if (rs.getString("SRNO") != null) {
			nominee.setSrno(rs.getString("SRNO"));
		} else {
			nominee.setSrno("---");
		}
		if (rs.getString("NOMINEENAME") != null) {
			nominee.setNomineeName(rs.getString("NOMINEENAME"));
		} else {
			nominee.setNomineeName("---");
		}
		if (rs.getString("NOMINEEDOB") != null) {
			nominee.setNomineeDob(commonUtil.converDBToAppFormat(rs
					.getDate("NOMINEEDOB")));
		} else {
			nominee.setNomineeDob("---");
		}
		if (rs.getString("NOMINEEADDRESS") != null) {
			nominee.setNomineeAddress(rs.getString("NOMINEEADDRESS"));
		} else {
			nominee.setNomineeAddress("---");
		}
		if (rs.getString("NOMINEERELATION") != null) {
			nominee.setNomineeRelation(rs.getString("NOMINEERELATION"));
		} else {
			nominee.setNomineeRelation("---");
		}
		if (rs.getString("NAMEOFGUARDIAN") != null) {
			nominee.setNameOfGuardian(rs.getString("NAMEOFGUARDIAN"));
		} else {
			nominee.setNameOfGuardian("---");
		}

		if (rs.getString("TOTALSHARE") != null) {
			nominee.setTotalShare(rs.getString("TOTALSHARE"));
		} else {
			nominee.setTotalShare("---");
		}
		if (rs.getString("GAURDIANADDRESS") != null) {
			nominee.setGaurdianAddress(rs.getString("GAURDIANADDRESS"));
		} else {
			nominee.setGaurdianAddress("---");
		}
		if (rs.getString("GAURDIANADDRESS") != null) {
			nominee.setGaurdianAddress(rs.getString("GAURDIANADDRESS"));
		} else {
			nominee.setGaurdianAddress("---");
		}
		if (rs.getString("GAURDIANADDRESS") != null) {
			nominee.setGaurdianAddress(rs.getString("GAURDIANADDRESS"));
		} else {
			nominee.setGaurdianAddress("---");
		}
		if (rs.getString("SAVINGACC") != null) {
			nominee.setNomineeAccNo(rs.getString("SAVINGACC"));
		} else {
			nominee.setNomineeAccNo("---");
		}
		if (rs.getString("NOMINEERETCAP") != null) {
			nominee.setNomineeReturnFlag(rs.getString("NOMINEERETCAP"));
		} else {
			nominee.setNomineeReturnFlag("");
		}
		return nominee;

	}

	private NomineeBean loadFamilyInfo(ResultSet rs) throws SQLException {
		NomineeBean nominee = new NomineeBean();
		log.info("loadFamilyInfo==============");
		log.info("loadFamilyInfo==============" + rs.getString("SRNO"));
		if (rs.getString("CPFACCNO") != null) {
			nominee.setCpfaccno(rs.getString("CPFACCNO"));
		} else {
			nominee.setCpfaccno("---");
		}
		if (rs.getString("SRNO") != null) {
			nominee.setSrno(rs.getString("SRNO"));
		} else {
			nominee.setSrno("---");
		}
		if (rs.getString("FAMILYMEMBERNAME") != null) {
			nominee.setFamilyMemberName(rs.getString("FAMILYMEMBERNAME"));
		} else {
			nominee.setFamilyMemberName("---");
		}
		if (rs.getString("DATEOFBIRTH") != null) {
			nominee.setFamilyDateOfBirth(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFBIRTH")));
		} else {
			nominee.setFamilyDateOfBirth("---");
		}
		if (rs.getString("FAMILYRELATION") != null) {
			nominee.setFamilyRelation(rs.getString("FAMILYRELATION"));
		} else {
			nominee.setFamilyRelation("---");
		}
		return nominee;

	}

	public String personalReportBuildQuery(EmployeePersonalInfo bean,
			String flag, String empNamechek, String sortingColumn) {

		log.info("PersonalDao:personalBuildQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
		log.info("empname check " + bean.getChkEmpNameFlag()
				+ "empNamechek====" + empNamechek);
		if (!flag.equals("count")) {
			sqlQuery = "SELECT INFO.CPFACNO  AS  CPFACNO,INFO.REFPENSIONNUMBER  AS  REFPENSIONNUMBER,INFO.AIRPORTSERIALNUMBER AS  AIRPORTSERIALNUMBER,INFO.EMPLOYEENO AS  EMPLOYEENO,INFO.EMPLOYEENAME AS  EMPLOYEENAME,INFO.DESEGNATION  AS  DESEGNATION,INFO.EMP_LEVEL  AS  EMP_LEVEL,INFO.DATEOFBIRTH  AS  DATEOFBIRTH, "
					+ "INFO.DATEOFJOINING AS  DATEOFJOINING,INFO.DATEOFSEPERATION_REASON AS  DATEOFSEPERATION_REASON,INFO.DATEOFSEPERATION_DATE  AS  DATEOFSEPERATION_DATE,INFO.WHETHER_FORM1_NOM_RECEIVED AS  WHETHER_FORM1_NOM_RECEIVED,"
					+ "INFO.AIRPORTCODE AS  AIRPORTCODE,INFO.GENDER AS  GENDER,INFO.FHNAME AS  FHNAME,INFO.MARITALSTATUS AS  MARITALSTATUS,INFO.PERMANENTADDRESS AS  PERMANENTADDRESS,INFO.TEMPORATYADDRESS AS  TEMPORATYADDRESS,INFO.WETHEROPTION  AS  WETHEROPTION,INFO.SETDATEOFANNUATION AS  SETDATEOFANNUATION,INFO.EMPFLAG AS  EMPFLAG,"
					+ "INFO.OTHERREASON AS  OTHERREASON,INFO.DIVISION  AS  DIVISION, INFO.DEPARTMENT  AS  DEPARTMENT, INFO.EMAILID AS  EMAILID,INFO.EMPNOMINEESHARABLE AS  EMPNOMINEESHARABLE,INFO.REMARKS AS  REMARKS,INFO.PENSIONNO AS  PENSIONNO,INFO.REGION  AS  REGION,INFO.USERNAME  AS  USERNAME,"
					+ "INFO.LASTACTIVE AS  LASTACTIVE,INFO.REFMONTHYEAR AS  REFMONTHYEAR,INFO.IPADDRESS AS  IPADDRESS,INFO.FHFLAG AS  FHFLAG,mvinfo.TRANSFERFLAG  AS TRANSFERFLAG"
					+ " from employee_personal_info info,mv_employees_transfer_info mvinfo where info.PENSIONNO=mvinfo.PENSIONNO and info.empflag='Y' "
					+ empNamechek;
			/*
			 * sqlQuery = "SELECT INFO.CPFACNO AS CPFACNO,INFO.REFPENSIONNUMBER
			 * AS REFPENSIONNUMBER,INFO.AIRPORTSERIALNUMBER AS
			 * AIRPORTSERIALNUMBER,INFO.EMPLOYEENO AS
			 * EMPLOYEENO,INFO.EMPLOYEENAME AS EMPLOYEENAME,INFO.DESEGNATION AS
			 * DESEGNATION,INFO.EMP_LEVEL AS EMP_LEVEL,INFO.DATEOFBIRTH AS
			 * DATEOFBIRTH, " + "INFO.DATEOFJOINING AS
			 * DATEOFJOINING,INFO.DATEOFSEPERATION_REASON AS
			 * DATEOFSEPERATION_REASON,INFO.DATEOFSEPERATION_DATE AS
			 * DATEOFSEPERATION_DATE,INFO.WHETHER_FORM1_NOM_RECEIVED AS
			 * WHETHER_FORM1_NOM_RECEIVED,"+ "INFO.AIRPORTCODE AS
			 * AIRPORTCODE,INFO.GENDER AS GENDER,INFO.FHNAME AS
			 * FHNAME,INFO.MARITALSTATUS AS MARITALSTATUS,INFO.PERMANENTADDRESS
			 * AS PERMANENTADDRESS,INFO.TEMPORATYADDRESS AS
			 * TEMPORATYADDRESS,INFO.WETHEROPTION AS
			 * WETHEROPTION,INFO.SETDATEOFANNUATION AS
			 * SETDATEOFANNUATION,INFO.EMPFLAG AS EMPFLAG,"+ "INFO.OTHERREASON
			 * AS OTHERREASON,INFO.DIVISION AS DIVISION, INFO.DEPARTMENT AS
			 * DEPARTMENT, INFO.EMAILID AS EMAILID,INFO.EMPNOMINEESHARABLE AS
			 * EMPNOMINEESHARABLE,INFO.REMARKS AS REMARKS,INFO.PENSIONNO AS
			 * PENSIONNO,INFO.REGION AS REGION,INFO.USERNAME AS USERNAME,"+
			 * "INFO.LASTACTIVE AS LASTACTIVE,INFO.REFMONTHYEAR AS
			 * REFMONTHYEAR,INFO.IPADDRESS AS IPADDRESS,INFO.FHFLAG AS FHFLAG
			 * from employee_personal_info info where info.empflag='Y' " +
			 * empNamechek;
			 */
			/*
			 * sqlQuery = "SELECT
			 * CPFACNO,REFPENSIONNUMBER,EMPLOYEENO,EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,AIRPORTCODE,GENDER,FHNAME" +
			 * ",MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REMARKS,PENSIONNO,REGION,LASTACTIVE
			 * from employee_personal_info where empflag='Y'";
			 */
		} else {
			sqlQuery = "SELECT COUNT(*) as COUNT from employee_personal_info info where info.empflag='Y' "
					+ empNamechek;
		}

		if (!bean.getCpfAccno().equals("")) {
			whereClause.append(" LOWER(info.cpfacno) ='"
					+ bean.getCpfAccno().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getPensionNo().equals("")) {
			whereClause.append(" info.PENSIONNO =" + bean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!bean.getDateOfBirth().equals("")) {
			whereClause
					.append(" TO_CHAR(info.DATEOFBIRTH,'dd-Mon-yyyy') like '"
							+ bean.getDateOfBirth() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getAirportCode().equals("")) {
			whereClause.append(" LOWER(info.airportcode)='"
					+ bean.getAirportCode().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(info.employeename) like'%"
					+ bean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!bean.getDesignation().equals("")) {
			whereClause.append(" LOWER(info.desegnation)='"
					+ bean.getDesignation().toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getEmployeeNumber().equals("")) {
			whereClause.append(" info.EMPLOYEENO='" + bean.getEmployeeNumber()
					+ "'");
			whereClause.append(" AND ");
		}
		if (!bean.getRegion().equals("")) {
			whereClause.append(" info.region='" + bean.getRegion() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (bean.getAirportCode().equals("")
				&& bean.getPensionNo().equals("")
				&& (bean.getDateOfBirth().equals(""))
				&& bean.getCpfAccno().equals("")
				&& (bean.getEmployeeName().equals(""))
				&& (bean.getDesignation().equals("") && (bean.getCpfAccno()
						.equals("")
						&& (bean.getEmployeeNumber().equals("")) && (bean
						.getRegion().equals(""))))) {
			log.info("inside if");
		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" AND ");
			}
		}

		query.append(this.sTokenFormat(whereClause));
		String orderBy = " ORDER BY info." + sortingColumn;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("dynamicQuery " + dynamicQuery);
		log.info("PersonalDAO:buildQuery Leaving Method");
		return dynamicQuery;
	}

	public RPFCForm9Bean rpfcForm9Report(EmployeePersonalInfo personalInfo,
			String flag, String empNameCheak, String sortingColumn) {
		Connection con = null;
		String empNameCheck = "";
		RPFCForm9Bean personal = null;
		ArrayList reportPersList = new ArrayList();

		int totalRecords = 0;
		if (personalInfo.getPensionNo() != null) {
			personalInfo.setPensionNo(commonUtil.trailingZeros(personalInfo
					.getPensionNo().toCharArray()));
		}
		if (personalInfo.getChkEmpNameFlag().equals("true")) {
			empNameCheck = " and info.employeename is null";
		}
		log.info("empNameCheck" + empNameCheck);
		String selectSQL = this.personalBuildQuery(personalInfo, flag,
				empNameCheck, sortingColumn);
		log.info("Query =====" + selectSQL);
		String pensionID = "";

		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			if (rs.next()) {
				personal = new RPFCForm9Bean();
				pensionID = rs.getString("PENSIONNO");
				personal = this.loadRPFCForm9Info(rs);
				String findDates = getPensionDatesByEmployee(pensionID,
						personal.getCpfaccno(), personal.getRegion());
				String[] pensionDts = findDates.split(",");
				personal.setFromEmpYear(pensionDts[0]);
				personal.setToEmpYear(pensionDts[1]);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("PersonalDAO :rpfcForm9Report() leaving method");
		return personal;
	}

	public String getPensionDatesByEmployee(String pensionNo, String cpfaccno,
			String region) {
		String cpfacno = this.getCPFACCNOByPension(pensionNo, cpfaccno, region);
		String[] cpfaccnoList = cpfacno.split("=");
		String cpfString = preparedCPFString(cpfaccnoList);
		String sql = "SELECT MIN(MONTHYEAR) AS MINMONTHYEAR,MAX(MONTHYEAR) AS MAXMONTHYEAR FROM EMPLOYEE_PENSION_VALIDATE WHERE  empflag='Y' and "
				+ cpfString;
		Connection con = null;
		StringBuffer buffer = new StringBuffer();
		log.info("Query =====" + sql);
		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				buffer.append(commonUtil.getDatetoString(rs
						.getDate("MINMONTHYEAR"), "dd-MMM-yyyy"));
				buffer.append(",");
				buffer.append(commonUtil.getDatetoString(rs
						.getDate("MAXMONTHYEAR"), "dd-MMM-yyyy"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public String preparedCPFString(String[] cpfaccnoList) {
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

	private String getCPFACCNOByPension(String pensionNo, String cpfacno,
			String region) {
		Connection con = null;
		StringBuffer buffer = new StringBuffer();
		String cpfaccno = "";
		String selectFamilySQL = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE SRNO='"
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

	private RPFCForm9Bean loadRPFCForm9Info(ResultSet rs) throws SQLException {
		RPFCForm9Bean personal = new RPFCForm9Bean();
		log.info("loadPersonalInfo==============");

		if (rs.getString("cpfacno") != null) {
			personal.setCpfaccno(rs.getString("cpfacno"));
		} else {
			personal.setCpfaccno("---");
		}
		log
				.info("loadPersonalInfo=============="
						+ rs.getString("airportcode"));
		if (rs.getString("airportcode") != null) {
			personal.setEmpAirportCode(rs.getString("airportcode"));
		} else {
			personal.setEmpAirportCode("---");
		}
		if (rs.getString("desegnation") != null) {
			personal.setEmpDesig(rs.getString("desegnation"));
		} else {
			personal.setEmpDesig("---");
		}

		if (rs.getString("PENSIONNO") != null) {
			personal.setEmpPensionNo(commonUtil.leadingZeros(5, rs
					.getString("PENSIONNO")));

		} else {
			personal.setEmpPensionNo("---");
		}

		if (rs.getString("employeename") != null) {
			personal.setEmployeeName(rs.getString("employeename"));
		} else {
			personal.setEmployeeName("---");
		}
		if (rs.getString("EMPLOYEENO") != null) {
			personal.setEmployeeNo(rs.getString("EMPLOYEENO"));
		} else {
			personal.setEmployeeNo("---");
		}
		if (rs.getString("DATEOFJOINING") != null) {
			personal.setEmpDOJ(commonUtil.converDBToAppFormat(rs
					.getDate("DATEOFJOINING")));
		} else {
			personal.setEmpDOJ("---");
		}

		if (rs.getString("region") != null) {
			personal.setRegion(rs.getString("region"));
		} else {
			personal.setRegion("---");
		}
		if (rs.getString("dateofbirth") != null) {
			personal.setDateOfBirth(commonUtil.converDBToAppFormat(rs
					.getDate("dateofbirth")));
		} else {
			personal.setDateOfBirth("---");
		}

		if (rs.getString("FHNAME") != null) {
			personal.setFhName(rs.getString("FHNAME"));
		} else {
			personal.setFhName("---");
		}

		if (rs.getString("DATEOFSEPERATION_DATE") != null) {
			personal.setEmpDOS(rs.getString("DATEOFSEPERATION_DATE"));
		} else {
			personal.setEmpDOS("---");
		}

		if (!personal.getDateOfBirth().equals("---")) {
			String personalPFID = commonDAO.getPFID(personal.getEmployeeName(),
					personal.getDateOfBirth(), personal.getEmpPensionNo());
			personal.setEmpPFID(personalPFID);
		} else {
			personal.setEmpPFID(personal.getEmpPensionNo());
		}

		return personal;

	}

	public String autoPendingProcessingPersonalInfo(String selectedDate,
			String retiredDate, String userName, String ipName)
			throws IOException {
		ArrayList airportList = new ArrayList();
		String lastactive = "", region = "";
		ArrayList form3List = new ArrayList();

		int totalFailed = 0, totalInserted = 0, totalRecords = 0, form3Cnt = 0;

		BufferedWriter fw = new BufferedWriter(new FileWriter(
				"c://missingImportData.txt"));
		BufferedWriter fw1 = new BufferedWriter(new FileWriter(
				"c://pending_pfids_report.txt"));
		lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");

		form3List = financeDAO.financeForm3ReportPFIDSAll(selectedDate, region,
				retiredDate, "cpfaccno", "", "false");
		form3Cnt = form3Cnt + form3List.size();
		String format = "";
		for (int j = 0; j < form3List.size(); j++) {
			form3Bean form3 = new form3Bean();
			form3 = (form3Bean) form3List.get(j);
			try {
				format = form3.getCpfaccno() + "," + form3.getEmployeeName()
						+ "," + form3.getAirportCode() + ","
						+ form3.getRegion();
				try {
					fw1.write(format);
					fw1.newLine();
					fw1.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					log.printStackTrace(e1);
				}

				totalRecords = this.processingPendingPersonalInfo(form3,
						lastactive, form3.getRegion(), fw, userName, ipName,
						selectedDate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
			if (totalRecords == 0) {
				totalFailed = totalFailed + 1;
			} else {
				totalInserted = totalInserted + 1;
			}
		}

		String message = "Total Form 3 Records=====" + form3Cnt + "<br/>";
		message = "Total Inserted=====" + totalInserted + "<br/>";
		message = message + "Total Failed=====" + totalFailed + "<br/>";
		return message;
	}

	private int processingPendingPersonalInfo(form3Bean form3Bean,
			String lastactive, String region, BufferedWriter fw,
			String userName, String IPAddress, String selectedDate)
			throws SQLException {
		log.info("processinPersonalInfo========");
		String sqlSelQuery = "", totalRecords = "", pensionNumber = "", insertQry = "", updateQry = "", updateNomineeQry = "", updateFamilDetails = "";
		Connection con = null;
		Statement st = null;
		Statement perSt = null;
		Statement nomineeSt = null;
		Statement familySt = null;
		int i = 0, j = 0, k = 0;
		selectedDate = selectedDate.replaceAll("%", "01");
		try {
			con = commonDB.getConnection();
			pensionNumber = this.getSequenceNo(con);
			if (!pensionNumber.equals("")) {
				sqlSelQuery = "select PENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
						+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,SEX,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,"
						+ Long.parseLong(pensionNumber)
						+ ",'"
						+ userName
						+ "','"
						+ lastactive
						+ "','"
						+ selectedDate
						+ "','"
						+ IPAddress
						+ "' "
						+ " FROM employee_info WHERE  ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ form3Bean.getCpfaccno().trim()
						+ "' AND REGION='"
						+ region.trim() + "')";
				log.info(sqlSelQuery);
				String chkPFID = checkPFID(form3Bean.getCpfaccno().trim(),
						region.trim());
				if (chkPFID.equals("")) {
					insertQry = "insert into employee_personal_info(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
							+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,IPAddress)  "
							+ sqlSelQuery;
				} else {
					pensionNumber = chkPFID;
				}

				updateQry = "UPDATE EMPLOYEE_INFO SET EMPSERIALNUMBER='"
						+ pensionNumber
						+ "' WHERE ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE EMPFLAG='Y' AND CPFACNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "')";
				updateNomineeQry = "UPDATE EMPLOYEE_NOMINEE_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "'";
				updateFamilDetails = "UPDATE EMPLOYEE_FAMILY_DTLS SET SRNO='"
						+ pensionNumber + "' WHERE EMPFLAG='Y' AND CPFACCNO='"
						+ form3Bean.getCpfaccno().trim() + "' AND REGION='"
						+ region.trim() + "'";

				// con = commonDB.getConnection();
				st = con.createStatement();
				perSt = con.createStatement();
				nomineeSt = con.createStatement();
				familySt = con.createStatement();
				con.setAutoCommit(false);
				log.info(insertQry);
				if (chkPFID.equals("")) {
					i = st.executeUpdate(insertQry);
				} else {
					i = 1;
				}

				if (i == 0) {
					writeFailedQueryErrorLog(insertQry, fw);
					writeFailedQueryErrorLog(updateQry, fw);
				} else {
					i = perSt.executeUpdate(updateQry);
					j = nomineeSt.executeUpdate(updateNomineeQry);
					k = familySt.executeUpdate(updateFamilDetails);
				}
				con.commit();
				con.setAutoCommit(true);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
			if (con != null) {
				con.rollback();
			}
			if (!(insertQry.equals("") || updateQry.equals(""))) {
				writeFailedQueryErrorLog(insertQry, fw);
				writeFailedQueryErrorLog(updateQry, fw);
				writeFailedQueryErrorLog(updateNomineeQry, fw);
				writeFailedQueryErrorLog(updateFamilDetails, fw);
			}

		} catch (Exception e) {
			log.printStackTrace(e);
			if (con != null) {
				con.rollback();
			}
			if (!(insertQry.equals("") || updateQry.equals(""))) {
				writeFailedQueryErrorLog(insertQry, fw);
				writeFailedQueryErrorLog(updateQry, fw);
				writeFailedQueryErrorLog(updateNomineeQry, fw);
				writeFailedQueryErrorLog(updateFamilDetails, fw);
			}

		} finally {
			if (perSt != null) {
				try {
					perSt.close();
					perSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (nomineeSt != null) {
				try {
					nomineeSt.close();
					nomineeSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			if (familySt != null) {
				try {
					familySt.close();
					familySt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			commonDB.closeConnection(con, st, null);
		}

		return i;
	}

	private String checkPFID(String cpfaccno, String region) {
		boolean chkPFIDFlag = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		String pensionNo = "", selQuery = "";
		selQuery = "SELECT PENSIONNO AS PENSIONNO FROM EMPLOYEE_PERSONAL_INFO WHERE REGION='"
				+ region + "' AND CPFACNO='" + cpfaccno + "'";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selQuery);
			if (rs.next()) {
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);

		} catch (Exception e) {

			log.printStackTrace(e);

		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return pensionNo;
	}

	public String autoUpdateProcessingPersonalInfo(String selectedDate,
			String retiredDate, HashMap regionMap, String selectedairportCode,
			String userName, String ipName) throws IOException {
		ArrayList airportList = new ArrayList();
		String lastactive = "", region = "", airportCode = "", message = "";
		ArrayList form3List = new ArrayList();
		Set set = regionMap.entrySet();
		Iterator iter = set.iterator();
		ArrayList regionList = new ArrayList();
		RegionBean regionInfo = new RegionBean();
		int totalFailed = 0, totalInserted = 0, totalRecords = 0, form3Cnt = 0;
		Connection con = null;
		try {
			con = commonDB.getConnection();
			BufferedWriter fw = new BufferedWriter(new FileWriter(
					"c://missingImportData.txt"));
			BufferedWriter fw1 = new BufferedWriter(new FileWriter(
					"c://chqiadData.txt"));
			lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
			regionList = commonUtil.loadRegions();

			boolean chkMnthYearFlag = false;

			for (int rg = 0; rg < regionList.size(); rg++) {
				regionInfo = (RegionBean) regionList.get(rg);

				if (selectedairportCode.equals("NO-SELECT")) {
					if (regionInfo.getAaiCategory().equals("METRO AIRPORT")) {
						region = regionInfo.getRegion();
						airportList = null;
						airportList = new ArrayList();
						airportList.add(regionInfo.getAirportcode());
					} else {
						if (regionInfo.getAaiCategory().equals(
								"NON-METRO AIRPORT")) {
							region = regionInfo.getRegion();
							airportList = financeDAO.getPensionAirportList(
									region, "");
						}

					}

				} else {
					airportList.add(selectedairportCode);
				}

				if (chkMnthYearFlag = true) {
					for (int k = 0; k < airportList.size(); k++) {
						airportCode = (String) airportList.get(k);
						form3List = financeDAO.financeForm3Report(airportCode,
								selectedDate, region, retiredDate,
								"employeename", "", "false");
						form3Cnt = form3Cnt + form3List.size();
						for (int j = 0; j < form3List.size(); j++) {
							String ms = "";
							form3Bean form3 = new form3Bean();
							form3 = (form3Bean) form3List.get(j);

							try {
								totalRecords = this.updatePersonalInfo(con,
										form3, lastactive, region, fw,
										userName, ipName, selectedDate);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								log.printStackTrace(e);
							}
							if (totalRecords == 0) {
								totalFailed = totalFailed + 1;
							} else {
								totalInserted = totalInserted + 1;
							}
						}

					}
					form3List = null;
					form3List = new ArrayList();
					if (region.equals("CHAIAD")
							&& airportCode.equals("DPO IAD")) {
						chkMnthYearFlag = true;
					}

				}
				message = "Total Form 3 Records=====" + form3Cnt + "<br/>";
				message = "Total Inserted=====" + totalInserted + "<br/>";
				message = message + "Total Failed=====" + totalFailed + "<br/>";
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return message;
	}

	private int updatePersonalInfo(Connection con, form3Bean form3Bean,
			String lastactive, String region, BufferedWriter fw,
			String userName, String IPAddress, String selectedDate)
			throws SQLException {
		log.info("processinPersonalInfo========");
		String sqlSelQuery = "", totalRecords = "", pensionNumber = "", insertQry = "", updateQry = "", updateNomineeQry = "", updateFamilDetails = "";

		Statement st = null;
		Statement perSt = null;
		Statement nomineeSt = null;
		Statement familySt = null;
		int i = 0, totalInserted = 0, totalFail = 0, j = 0, k = 0;
		selectedDate = selectedDate.replaceAll("%", "01");
		try {
			perSt = con.createStatement();
			updateQry = "UPDATE EMPLOYEE_PERSONAL_INFO SET AIRPORTCODE='"
					+ form3Bean.getAirportCode() + "' WHERE CPFACNO='"
					+ form3Bean.getCpfaccno() + "' AND REGION='"
					+ region.trim() + "'";
			i = perSt.executeUpdate(updateQry);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (perSt != null) {
				try {
					perSt.close();
					perSt = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}

		}

		return i;
	}

	public void updatePFIDTransTbl(String range, String region,
			String empNameFlag, String empName, String pensionno, FileWriter fw) {
		log.info("PersonalDAO::updatePFIDTransTbl");
		Connection con = null;

		ArrayList empDataList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		int totalRecords = 0, totalAdvRecords = 0, totalLoansRecords = 0;
		try {
			con = commonDB.getConnection();
			empDataList = this.getEmployeePFInfo(con, range, region,
					empNameFlag, empName, pensionno);

			for (int i = 0; i < empDataList.size(); i++) {

				personalInfo = (EmployeePersonalInfo) empDataList.get(i);
				if (!personalInfo.getPfIDString().equals("")) {
					totalRecords = this.updateTranTbl(con, personalInfo
							.getPfIDString(), personalInfo.getOldPensionNo());
					totalAdvRecords = this.updateTranAdvanceTbl(con,
							personalInfo.getPfIDString(), personalInfo
									.getOldPensionNo());
					totalLoansRecords = this.updateTranLoansTbl(con,
							personalInfo.getPfIDString(), personalInfo
									.getOldPensionNo());
					fw.write(commonUtil.leadingZeros(5, personalInfo
							.getPensionNo())
							+ "================"
							+ totalRecords
							+ "========================"
							+ personalInfo.getPfIDString() + "\n");
					fw.write(commonUtil.leadingZeros(5, personalInfo
							.getPensionNo())
							+ "================"
							+ totalAdvRecords
							+ "==========A=============="
							+ personalInfo.getPfIDString() + "\n");
					fw.write(commonUtil.leadingZeros(5, personalInfo
							.getPensionNo())
							+ "================"
							+ totalLoansRecords
							+ "========L================"
							+ personalInfo.getPfIDString() + "\n");
				} else {
					fw.write(commonUtil.leadingZeros(5, personalInfo
							.getPensionNo())
							+ "================"
							+ totalRecords
							+ "========================" + "Un mapped PFID\n");
				}

				fw.flush();
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, null, null);
		}

		return;
	}

	private ArrayList getEmployeePFInfo(Connection con, String range,
			String region, String empNameFlag, String empName, String pensionno) {
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
			sqlQuery = this.buildQueryEmpPFIDInfo(range, region, empNameFlag,
					empName, pensionno);
			log.info("PersonalDAO::getEmployeePFInfo" + sqlQuery);
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				data = new EmployeePersonalInfo();

				data = commonDAO.loadPersonalInfo(rs);

				pfidWithRegion = this.getEmpMapPFInfo(con, data.getPensionNo(),
						data.getCpfAccno(), data.getRegion());
				if (!pfidWithRegion.equals("")) {
					String[] pfIDLists = pfidWithRegion.split("=");
					log.info(data.getOldPensionNo() + "============"
							+ pfidWithRegion);
					if (pfIDLists.length != 0) {
						data.setPfIDString(this.preparedCPFString(pfIDLists));
					} else {
						data.setPfIDString("");
					}
				} else {
					data.setPfIDString("");
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

	private String getEmpMapPFInfo(Connection con, String personalPFID,
			String personalCPFACCNO, String personalRegion) {

		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		String sqlQuery = "", pfID = "", region = "", regionPFIDS = "";
		if (("---".equals(personalCPFACCNO))
				|| ("---".equals(personalRegion))
				|| ("---".equals(personalRegion) && "---"
						.equals(personalCPFACCNO))) {
			flag = true;
		}
		try {

			st = con.createStatement();
			sqlQuery = "SELECT CPFACNO,REGION FROM EMPLOYEE_INFO WHERE EMPSERIALNUMBER='"
					+ personalPFID + "'";
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
				if (flag = true) {
					regionPFIDS = regionPFIDS + "=";
				} else {
					regionPFIDS = regionPFIDS + personalCPFACCNO + ","
							+ personalRegion + "=";
				}
			} else {
				if (flag = true) {
					regionPFIDS = "";
				} else {
					regionPFIDS = personalCPFACCNO + "," + personalRegion + "=";
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return regionPFIDS;
	}

	private String buildQueryEmpPFIDInfo(String range, String region,
			String empNameFlag, String empName, String pensionno) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		sqlQuery = "SELECT REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO, INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,username,LASTACTIVE,RefMonthYear,IPAddress,OTHERREASON,decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO";
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

		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if ((region.equals(""))
				&& (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" WHERE ");
			query.append(this.sTokenFormat(whereClause));
		}
		String orderBy = "  ORDER BY PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();

		return dynamicQuery;
	}

	private int updateTranTbl(Connection con, String pfidString,
			String pensionno) {
		String seltransTbl = "UPDATE EMPLOYEE_PENSION_VALIDATE SET PENSIONNO="
				+ pensionno + " WHERE (" + pfidString + ")";

		Statement st = null;
		ResultSet rs = null;
		int updatedStatus = 0;
		try {

			st = con.createStatement();
			log.info("PersonalDA::updateTranTbl" + seltransTbl);
			updatedStatus = st.executeUpdate(seltransTbl);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return updatedStatus;
	}

	private int updateTranLoansTbl(Connection con, String pfidString,
			String pensionno) {
		String seltransTbl = "UPDATE EMPLOYEE_PENSION_LOANS SET PENSIONNO="
				+ pensionno + " WHERE (" + pfidString + ")";

		Statement st = null;
		ResultSet rs = null;
		int updatedStatus = 0;
		try {

			st = con.createStatement();
			log.info("PersonalDA::updateTranTbl" + seltransTbl);
			updatedStatus = st.executeUpdate(seltransTbl);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return updatedStatus;
	}

	private int updateTranAdvanceTbl(Connection con, String pfidString,
			String pensionno) {
		String seltransTbl = "UPDATE EMPLOYEE_PENSION_ADVANCES SET PENSIONNO="
				+ pensionno + " WHERE (" + pfidString + ")";

		Statement st = null;
		ResultSet rs = null;
		int updatedStatus = 0;
		try {

			st = con.createStatement();
			log.info("PersonalDAO::updateTranAdvanceTbl" + seltransTbl);
			updatedStatus = st.executeUpdate(seltransTbl);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return updatedStatus;
	}

	public int updateForm10D(EmpMasterBean bean,
			EmployeeAdlPensionInfo empAdlPensionInfo)
			throws InvalidDataException {
		log.info("PersonalDAO:updateForm10D entering method ");
		// PensionBean editBean =new PensionBean();
		Connection con = null;
		Statement st = null;

		int count = 0;
		String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "", newCpfAcno = "";
		String empName = "", desegnation = "", empLevel = "", seperationReason = "", whetherOptionA = "";
		String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
		String remarks = "", station = "", dateofBirth = "", dateofJoining = "", dateofSeperationDate = "";
		String fMemberName = "", fDateofBirth = "", frelation = "", familyrows = "";
		String wetherOption = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "", dateOfAnnuation = "", otherReason = "";
		String pfId = "";
		int count1[] = new int[3];
		pfId = bean.getPfid();
		airportSerialNumber = bean.getAirportSerialNumber();
		empNumber = bean.getEmpNumber();
		cpfAcNo = bean.getCpfAcNo().trim();
		newCpfAcno = bean.getNewCpfAcNo();
		station = bean.getStation();
		empName = bean.getEmpName();
		desegnation = bean.getDesegnation();
		empLevel = bean.getEmpLevel();
		seperationReason = bean.getSeperationReason();
		whetherOptionA = bean.getWhetherOptionA();
		whetherOptionB = bean.getWhetherOptionB();
		whetherOptionNO = bean.getWhetherOptionNO();
		remarks = bean.getRemarks();
		dateofBirth = bean.getDateofBirth();
		dateofJoining = bean.getDateofJoining();
		dateofSeperationDate = bean.getDateofSeperationDate();
		form2Nomination = bean.getForm2Nomination();
		String pensionNumber = bean.getPensionNumber();
		String empNomineeSharable = bean.getEmpNomineeSharable();
		wetherOption = bean.getWetherOption();
		sex = bean.getSex();
		maritalStatus = bean.getMaritalStatus();
		fhName = bean.getFhName();
		permanentAddress = bean.getPermanentAddress();
		temporatyAddress = bean.getTemporatyAddress();
		dateOfAnnuation = bean.getDateOfAnnuation();
		// String pensionNumber = this.getPensionNumber(empName.toUpperCase(),
		// dateofBirth, cpfAcNo);
		otherReason = bean.getOtherReason().trim();
		String division = bean.getDivision();
		String department = bean.getDepartment();
		String emailId = bean.getEmailId();
		String empOldName = bean.getEmpOldName();
		String empOldNumber = bean.getEmpOldNumber();
		String region = bean.getRegion();
		String setRecordVerified = bean.getRecordVerified();

		java.util.Date now = new java.util.Date();
		String MY_DATE_FORMAT = "dd-MM-yyyy hh:mm a";
		String currDateTime = new SimpleDateFormat(MY_DATE_FORMAT).format(now);
		log.info("date is  " + currDateTime);
		String fhFlag = bean.getFhFlag();
		if (!bean.getChangedRegion().equals("")) {
			region = bean.getChangedRegion();
		}
		if (!bean.getChangedStation().equals("")) {
			station = bean.getChangedStation();
		}

		String changedStation = bean.getChangedStation();

		try {
			String query = "";

			con = commonDB.getConnection();
			st = con.createStatement();

			pensionDAO.insertEmployeeHistory(cpfAcNo, "", true, "", region,
					currDateTime, bean.getComputerName().trim(), bean
							.getUserName());

			log.info("updateForm10D::pensionNumber  " + pensionNumber
					+ "cpfAcNo  " + cpfAcNo + "bean.getEmpOldNumber()"
					+ bean.getEmpOldNumber() + "region===========" + region
					+ "dateofBirth" + dateofBirth);

			String nomineeDmlQuery = "";
			String nomineeName = "", tempData = "", nomineeAddress = "", nrowid = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
			String nomineeAccounts = "", nomineeRetCaptOtpion = "";
			String nomineeOldName = "";
			String tempInfo[] = null;
			nomineeRows = bean.getNomineeRow();
			log.info("updateForm10D::nomineeRows " + nomineeRows);
			ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
			DateValidation dateValidation = new DateValidation();
			int serialno = 0;
			for (int j = 0; j < nomineeList.size(); j++) {
				tempData = nomineeList.get(j).toString();
				tempInfo = tempData.split("@");
				nomineeName = tempInfo[0];
				log.info("updateForm10D::tempInfo(updateForm10D)" + tempInfo);
				if (!tempInfo[1].equals("XXX")) {
					nomineeAddress = tempInfo[1];
				} else {
					nomineeAddress = "";
				}
				if (!tempInfo[2].equals("XXX")) {
					nomineeDob = tempInfo[2].toString().trim();
				} else {
					nomineeDob = "";
				}

				if (!tempInfo[3].equals("XXX")) {
					nomineeRelation = tempInfo[3];
				} else {
					nomineeRelation = "";
				}
				if (!tempInfo[4].equals("XXX")) {
					nameofGuardian = tempInfo[4];
				} else {
					nameofGuardian = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					gaurdianAddress = tempInfo[5];
				} else {
					gaurdianAddress = "";
				}
				if (!tempInfo[6].equals("XXX")) {
					nomineeOldName = tempInfo[6];
				} else {
					nomineeOldName = "";
				}

				if (!tempInfo[7].equals("XXX")) {
					nomineeRetCaptOtpion = tempInfo[7];
				} else {
					nomineeRetCaptOtpion = "N";
				}

				if (!tempInfo[8].equals("XXX")) {
					nomineeAccounts = tempInfo[8];
				} else {
					nomineeAccounts = "";
				}
				if (!tempInfo[9].equals("XXX")) {
					nrowid = tempInfo[9];
				} else {
					nrowid = "";
				}
				log.info("updateForm10D::rowid " + nrowid + "nomineeDob"
						+ nomineeDob);

				// nomineeRows=tempInfo[5];
				if (!this.checkFamilyNomineeDtls(nomineeOldName,
						bean.getEmpSerialNo(), "", nrowid).equals("NOT FOUND")) {
					nomineeDmlQuery = "update employee_nominee_dtls set nomineeName='"
							+ nomineeName
							+ "',nomineeAddress='"
							+ nomineeAddress
							+ "',nomineeDob='"
							+ nomineeDob
							+ "',nomineeRelation='"
							+ nomineeRelation
							+ "',nameofGuardian='"
							+ nameofGuardian
							+ "',nomineeretcap='"
							+ nomineeRetCaptOtpion
							+ "',GAURDIANADDRESS='"
							+ gaurdianAddress
							+ "',savingacc='"
							+ nomineeAccounts
							+ "' where nomineeName='"
							+ nomineeOldName.trim()
							+ "' and PENSIONNO='"
							+ bean.getEmpSerialNo()
							+ "' and SRNO='" + nrowid + "'";

				} else {
					serialno++;
					nomineeDmlQuery = "insert into employee_nominee_dtls(cpfaccno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,region,SRNO,nomineeretcap,savingacc,PENSIONNO)values('"
							+ newCpfAcno
							+ "','"
							+ nomineeName
							+ "','"
							+ nomineeAddress
							+ "','"
							+ nomineeDob
							+ "','"
							+ nomineeRelation
							+ "','"
							+ nameofGuardian
							+ "','"
							+ gaurdianAddress
							+ "','"
							+ region
							+ "','"
							+ serialno
							+ "','"
							+ nomineeRetCaptOtpion
							+ "','"
							+ nomineeAccounts
							+ "','"
							+ bean.getEmpSerialNo()
							+ "')";
				}
				log.info("Form10DUpdate::nomineeDmlQuery is" + nomineeDmlQuery);
				st.executeUpdate(nomineeDmlQuery);
			}
			int otherPencnt = this.addOtherPensionDtls(bean.getEmpSerialNo(),
					con, empAdlPensionInfo);
			int schemeCnt = this.addSchmemeDtls(bean.getEmpSerialNo(), con,
					bean);
			/*
			 * String empPersonalInfo = "update EMPLOYEE_PERSONAL_INFO set
			 * airportcode='" + station + "',cpfacno='" + newCpfAcno +
			 * "',employeename='" + empName.trim() + "',desegnation='" +
			 * desegnation + "',AIRPORTSERIALNUMBER='" + airportSerialNumber +
			 * "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='" + empLevel +
			 * "',DATEOFBIRTH ='" + dateofBirth + "',DATEOFJOINING='" +
			 * dateofJoining + "',DATEOFSEPERATION_REASON='" + seperationReason +
			 * "',DATEOFSEPERATION_DATE='" + dateofSeperationDate +
			 * "',REMARKS='" + remarks.trim() + "',gender='" + sex +
			 * "',maritalStatus='" + maritalStatus + "',permanentAddress='" +
			 * permanentAddress + "',temporatyAddress='" + temporatyAddress +
			 * "',wetherOption='" + wetherOption + "',
			 * WHETHER_FORM1_NOM_RECEIVED ='" + form2Nomination + "',fhname='" +
			 * fhName + "',setDateOfAnnuation='" + dateOfAnnuation +
			 * "',REFPENSIONNUMBER='" + pensionNumber + "',otherreason='" +
			 * otherReason + "',division='" + division + "',department='" +
			 * department + "',emailId='" + emailId + "',lastactive='" +
			 * commonUtil.getCurrentDate("dd-MMM-yyyy") +
			 * "',empnomineesharable='" + empNomineeSharable + "',userName='" +
			 * bean.getUserName() + "',fhflag='" + bean.getFhFlag() +
			 * "',region='" + region.trim() + "' where empflag='Y' and
			 * PENSIONNO='" + bean.getEmpSerialNo() + "' and
			 * trim(employeename)='" + empOldName.trim() + "' and region='" +
			 * bean.getRegion() + "'";
			 */
			String personalUpdQry = "update  EMPLOYEE_PERSONAL_INFO set permanentAddress='"
					+ permanentAddress
					+ "',temporatyAddress='"
					+ temporatyAddress
					+ "',NATIONALITY='"
					+ bean.getNationality()
					+ "',HEIGHT='"
					+ bean.getHeightWithInches()
					+ "'  where empflag='Y' and PENSIONNO="
					+ bean.getEmpSerialNo();
			st.executeUpdate(personalUpdQry);
			log.info("updateForm10D::empPersonalInfoQuery" + personalUpdQry);

		} catch (SQLException sqle) {
			log.printStackTrace(sqle);
			if (sqle.getErrorCode() == 00001) {
				throw new InvalidDataException("PensionNumber Already Exist");
			}
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			commonDB.closeConnection(con, st, null);
		}
		log.info("PersonalDAO:updateForm10D leaving method ");
		return count;
	}

	public String checkFamilyNomineeDtls(String memberName, String pensionNo,
			String flag, String serialNo) {

		log.info("PersonalDAO :checkFamilyNomineeDtls() entering method ");
		String foundEmpFlag = "", query = "";
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		if (flag.trim().equals("family")) {
			query = "SELECT  'X' as COLUMNNM FROM EMPLOYEE_FAMILY_DTLS where  PENSIONNO="
					+ pensionNo
					+ "  AND lower(NOMINEENAME)='"
					+ memberName.toLowerCase() + "'";
		} else {
			query = "select 'X' as COLUMNNM from EMPLOYEE_NOMINEE_DTLS where  PENSIONNO="
					+ pensionNo
					+ "  AND lower(NOMINEENAME)='"
					+ memberName.toLowerCase() + "'";
		}

		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getString("COLUMNNM") != null) {
					foundEmpFlag = rs.getString("COLUMNNM");
				}
			} else {
				foundEmpFlag = "NOT FOUND";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersonalDAO :checkFamilyNomineeDtls() leaving method");
		return foundEmpFlag;
	}

	public EmpMasterBean empForm10DPersonalEdit(String cpfacno, String empName,
			String region, String pfid) throws Exception {
		log.info("PensionDAO:empForm10DPersonalEdit entering method ");
		EmpMasterBean editBean = new EmpMasterBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String personalSelQuery = "", query1 = "";
		personalSelQuery = " SELECT * FROM EMPLOYEE_PERSONAL_INFO where  empflag='Y' AND PENSIONNO ="
				+ pfid.toLowerCase().trim();
		log.info("PersonalDAO::empForm10DPersonalEdit is " + personalSelQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(personalSelQuery);
			while (rs.next()) {

				if (rs.getString("employeename") != null)
					editBean.setEmpName(rs.getString("employeename").trim());
				else
					editBean.setEmpName("");
				if (rs.getString("EMPLOYEENO") != null)
					editBean.setEmpNumber(rs.getString("EMPLOYEENO").trim());
				else
					editBean.setEmpNumber("");

				if (rs.getString("desegnation") != null)
					editBean.setDesegnation(rs.getString("desegnation").trim());
				else
					editBean.setDesegnation("");

				if (rs.getString("EMP_LEVEL") != null)
					editBean.setEmpLevel(rs.getString("EMP_LEVEL"));
				else
					editBean.setEmpLevel("");

				if (rs.getString("DATEOFBIRTH") != null) {
					editBean.setDateofBirth(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFBIRTH").toString(), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
				} else {
					editBean.setDateofBirth("");
				}

				if (rs.getString("DATEOFJOINING") != null)
					// editBean.setDateofJoining(CommonUtil.getStringtoDate(rs.getString("DATEOFJOINING").toString()));
					editBean.setDateofJoining(commonUtil.converDBToAppFormat(rs
							.getString("DATEOFJOINING").toString(),
							"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofJoining("");

				if (rs.getString("DATEOFSEPERATION_REASON") != null)
					editBean.setSeperationReason(rs
							.getString("DATEOFSEPERATION_REASON"));
				else
					editBean.setSeperationReason("");

				if (rs.getString("DATEOFSEPERATION_DATE") != null)
					// editBean.setDateofSeperationDate(CommonUtil.getStringtoDate(rs.getString("DATEOFSEPERATION_DATE").toString()));
					editBean.setDateofSeperationDate(commonUtil
							.converDBToAppFormat(rs.getString(
									"DATEOFSEPERATION_DATE").toString(),
									"yyyy-MM-dd", "dd-MMM-yyyy"));
				else
					editBean.setDateofSeperationDate("");

				if (rs.getString("WHETHER_FORM1_NOM_RECEIVED") != null)
					editBean.setForm2Nomination(rs
							.getString("WHETHER_FORM1_NOM_RECEIVED"));
				else
					editBean.setForm2Nomination("");

				if (rs.getString("airportcode") != null)
					editBean.setStation(rs.getString("airportcode"));
				else
					editBean.setStation("");
				if (rs.getString("REFPENSIONNUMBER") != null)
					editBean.setPensionNumber(rs.getString("REFPENSIONNUMBER"));
				else
					editBean.setPensionNumber("");
				if (rs.getString("emailId") != null)
					editBean.setEmailId(rs.getString("emailId"));
				else
					editBean.setEmailId("");
				if (rs.getString("AIRPORTSERIALNUMBER") != null)
					editBean.setAirportSerialNumber(rs
							.getString("AIRPORTSERIALNUMBER"));
				else
					editBean.setAirportSerialNumber("");
				if (rs.getString("employeeno") != null)
					editBean.setEmpNumber(rs.getString("employeeno"));
				else
					editBean.setEmpNumber("");
				if (rs.getString("remarks") != null)
					editBean.setRemarks(rs.getString("remarks"));
				else
					editBean.setRemarks("");
				if (rs.getString("GENDER") != null)
					editBean.setSex(rs.getString("GENDER"));
				else
					editBean.setSex("");

				if (rs.getString("setDateOfAnnuation") != null)
					// editBean.setDateOfAnnuation(CommonUtil.getStringtoDate(rs.getString("setDateOfAnnuation")));
					editBean.setDateOfAnnuation(commonUtil.converDBToAppFormat(
							rs.getString("setDateOfAnnuation"), "yyyy-MM-dd",
							"dd-MMM-yyyy"));
				else
					editBean.setDateOfAnnuation("");
				if (rs.getString("fhname") != null)
					editBean.setFhName(rs.getString("fhname"));
				else
					editBean.setFhName("");
				if (rs.getString("fhflag") != null)
					editBean.setFhFlag(rs.getString("fhflag"));
				else
					editBean.setFhFlag("");
				if (rs.getString("maritalstatus") != null)
					editBean.setMaritalStatus(rs.getString("maritalstatus"));
				else
					editBean.setMaritalStatus("");
				log.info("PERMANENTADDRESS" + rs.getString("PERMANENTADDRESS"));
				if (rs.getString("PERMANENTADDRESS") != null)
					editBean.setPermanentAddress(rs
							.getString("PERMANENTADDRESS"));
				else
					editBean.setPermanentAddress("");

				if (rs.getString("TEMPORATYADDRESS") != null)
					editBean.setTemporatyAddress(rs
							.getString("TEMPORATYADDRESS"));
				else
					editBean.setTemporatyAddress("");
				if (rs.getString("wetherOption") != null) {
					editBean.setWetherOption(rs.getString("wetherOption"));
					log.info("wetheroption is" + editBean.getWetherOption());
				} else
					editBean.setWetherOption("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region is" + editBean.getRegion());
				} else
					editBean.setRegion("");

				if (rs.getString("otherreason") != null) {
					editBean.setOtherReason(rs.getString("otherreason"));
					log.info("otherreason is" + editBean.getOtherReason());
				} else
					editBean.setOtherReason("");

				if (rs.getString("department") != null) {
					editBean.setDepartment(rs.getString("department"));
					log.info("department is" + editBean.getDepartment());
				} else
					editBean.setDepartment("");
				if (rs.getString("division") != null) {
					editBean.setDivision(rs.getString("division"));
					log.info("division is" + editBean.getDivision());
				} else
					editBean.setDivision("");

				if (rs.getString("empnomineesharable") != null) {
					editBean.setEmpNomineeSharable(rs
							.getString("empnomineesharable"));
					log.info("empnomineesharable is"
							+ editBean.getEmpNomineeSharable());
				} else
					editBean.setEmpNomineeSharable("");

				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
					log.info("region  is" + editBean.getRegion());
				} else
					editBean.setRegion("");
				if (rs.getString("OPTIONFORMRECEIVED") != null) {
					editBean.setOptionForm(rs.getString("OPTIONFORMRECEIVED"));
				} else {
					editBean.setOptionForm("");
				}
				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					log.info("setEmpSerialNo  is" + editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");
				if (rs.getString("NATIONALITY") != null) {
					editBean.setNationality(rs.getString("NATIONALITY"));
				} else {
					editBean.setNationality("");
				}

				if (rs.getString("HEIGHT") != null) {
					editBean.setHeightWithInches(rs.getString("HEIGHT"));
				} else {
					editBean.setHeightWithInches("");
				}

				// for pfacno
				if (rs.getString("PENSIONNO") != null) {
					editBean.setEmpSerialNo(rs.getString("PENSIONNO"));
					editBean.setPfid(commonDAO.getPFID(editBean.getEmpName(),
							editBean.getDateofBirth(), commonUtil.leadingZeros(
									5, editBean.getEmpSerialNo())));
					log.info("empForm10DPersonalEdit::EmpSerialNo  is"
							+ editBean.getEmpSerialNo());
				} else
					editBean.setEmpSerialNo("");
				editBean.setFamilyRow(this.getFamilyDtlsByPFID(editBean
						.getEmpSerialNo(), con));
				editBean.setNomineeRow(this.getNomineeDtlsByPFID(editBean
						.getEmpSerialNo(), con));
				editBean.setSchemeList(this.getSchemeDtls(editBean
						.getEmpSerialNo(), con));
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersionalDAO:empForm10DPersonalEdit leaving method ");
		return editBean;
	}

	public String getNomineeDtlsByPFID(String pensionno, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String nomineeDtlSelQuery = "", nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRetCap = "", nomineeSvingAccno = "";
		String nomineeRelation = "", nameofGuardian = "", guardianAddress = "", totalShare = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		nomineeDtlSelQuery = "SELECT NOMINEENAME,NOMINEEADDRESS,TO_CHAR(NOMINEEDOB,'dd-Mon-yyyy') AS NOMINEEDOB,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,SRNO,NOMINEERETCAP,SAVINGACC FROM EMPLOYEE_NOMINEE_DTLS WHERE EMPFLAG='Y' AND  PENSIONNO="
				+ pensionno + " ORDER BY SRNO";
		try {
			st = con.createStatement();
			rs = st.executeQuery(nomineeDtlSelQuery);
			while (rs.next()) {
				if (rs.getString("NOMINEENAME") != null) {
					nomineeName = rs.getString("NOMINEENAME");
				} else {
					nomineeName = "xxx";
				}
				if (rs.getString("NOMINEEADDRESS") != null) {
					nomineeAddress = rs.getString("NOMINEEADDRESS");
				} else {
					nomineeAddress = "xxx";
				}
				if (rs.getString("NOMINEEDOB") != null) {

					nomineeDob = rs.getString("NOMINEEDOB");
				} else {
					nomineeDob = "xxx";
				}
				if (rs.getString("NOMINEERELATION") != null) {
					nomineeRelation = rs.getString("NOMINEERELATION");
				} else {
					nomineeRelation = "xxx";
				}
				if (rs.getString("NAMEOFGUARDIAN") != null) {
					nameofGuardian = rs.getString("NAMEOFGUARDIAN");

				} else {
					nameofGuardian = "xxx";
				}
				if (rs.getString("GAURDIANADDRESS") != null) {
					guardianAddress = rs.getString("GAURDIANADDRESS");
					log.info("guardianAddress  " + guardianAddress);
				} else {
					guardianAddress = "xxx";
				}
				if (rs.getString("TOTALSHARE") != null) {
					totalShare = rs.getString("TOTALSHARE");
				} else {
					totalShare = "xxx";
				}

				if (rs.getString("SRNO") != null) {
					rowId = rs.getString("SRNO");
				} else {
					rowId = "xxx";
				}
				if (rs.getString("NOMINEERETCAP") != null) {
					nomineeRetCap = rs.getString("NOMINEERETCAP");
				} else {
					nomineeRetCap = "xxx";
				}
				if (rs.getString("SAVINGACC") != null) {
					nomineeSvingAccno = rs.getString("SAVINGACC");
				} else {
					nomineeSvingAccno = "xxx";
				}
				buffer.append(nomineeName + "@");
				buffer.append(nomineeAddress + "@");
				buffer.append(nomineeDob + "@");
				buffer.append(nomineeRelation + "@");
				buffer.append(nameofGuardian + "@");
				buffer.append(guardianAddress + "@");
				buffer.append(totalShare + "@");
				buffer.append(nomineeRetCap + "@");
				buffer.append(nomineeSvingAccno + "@");
				buffer.append(rowId + "***");

			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return buffer.toString();
	}

	public String getFamilyDtlsByPFID(String pensionno, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String familyDtlSelQuery = "", familyMemberName = "", fmlyMbrDOB = "", fmlyMbrRelation = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		familyDtlSelQuery = "SELECT FAMILYMEMBERNAME,TO_CHAR(DATEOFBIRTH,'dd-Mon-yyyy') AS DATEOFBIRTH,FAMILYRELATION,SRNO from employee_family_dtls where EMPFLAG='Y' AND  PENSIONNO="
				+ pensionno + "ORDER BY SRNO";
		try {
			st = con.createStatement();
			rs = st.executeQuery(familyDtlSelQuery);
			while (rs.next()) {
				if (rs.getString("FAMILYMEMBERNAME") != null) {
					familyMemberName = rs.getString("FAMILYMEMBERNAME");
				} else {
					familyMemberName = "xxx";
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					fmlyMbrDOB = rs.getString("DATEOFBIRTH");
				} else {
					fmlyMbrDOB = "xxx";
				}
				if (rs.getString("FAMILYRELATION") != null) {
					fmlyMbrRelation = rs.getString("FAMILYRELATION");
				} else {
					fmlyMbrRelation = "xxx";
				}
				if (rs.getString("SRNO") != null) {
					rowId = rs.getString("SRNO");
				} else {
					rowId = "xxx";
				}
				buffer.append(familyMemberName + "@");
				buffer.append(fmlyMbrDOB + "@");
				buffer.append(fmlyMbrRelation + "@");
				buffer.append(rowId + "***");
			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return buffer.toString();
	}

	public ArrayList getSchemeDtls(String pensionno, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String familyDtlSelQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		ArrayList schmeList = new ArrayList();
		familyDtlSelQuery = "SELECT PENSIONNO,SRNO,CERTIFICONTROLNO,CERTIFIAUTHORITY from EPIS_PENSION_CERTIFICATE_DTLS where EMPFLAG='Y' AND  PENSIONNO="
				+ pensionno + "ORDER BY SRNO";
		try {
			st = con.createStatement();
			rs = st.executeQuery(familyDtlSelQuery);
			while (rs.next()) {
				buffer = new StringBuffer();

				if (rs.getString("CERTIFICONTROLNO") != null) {
					certiControlNO = rs.getString("CERTIFICONTROLNO");
				} else {
					certiControlNO = "xxx";
				}
				if (rs.getString("CERTIFIAUTHORITY") != null) {
					certifAuthority = rs.getString("CERTIFIAUTHORITY");
				} else {
					certifAuthority = "xxx";
				}

				if (rs.getString("SRNO") != null) {
					rowId = rs.getString("SRNO");
				} else {
					rowId = "xxx";
				}
				buffer.append(certiControlNO + "@");
				buffer.append(certifAuthority + "@");
				buffer.append(rowId);
				schmeList.add(buffer.toString());

			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return schmeList;
	}

	public boolean delSchemeDtls(String pensionno, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String familyDtlDelQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		ArrayList schmeList = new ArrayList();
		int dbCnt = 0;
		boolean schemeDtlCnt = true;
		familyDtlDelQuery = "DELETE from EPIS_PENSION_CERTIFICATE_DTLS where PENSIONNO="
				+ pensionno;
		try {
			st = con.createStatement();
			dbCnt = st.executeUpdate(familyDtlDelQuery);
			if (dbCnt != 0) {
				schemeDtlCnt = true;
			} else {
				schemeDtlCnt = false;
			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return schemeDtlCnt;
	}

	public int addOtherPensionDtls(String pensionno, Connection con,
			EmployeeAdlPensionInfo additonalInfo) {
		Statement st = null;
		String otherPensQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		ArrayList schmeList = new ArrayList();
		int dbCnt = 0;
		boolean schemeDtlCnt = true;
		if (this.checkOtherPensionDtls(pensionno, con).equals("NOTFOUND")) {
			otherPensQuery = "INSERT INTO EPIS_EMPLOYEE_PENSION_DTLS(PENSIONNO,REDPENSION,REDPENSION_DATE,REDDATEOFSUBMSION,QUANTUMOPT,QUANTUMOPTAMOUNT,NOMINEERETCAPTIAL,MEMBERDEATHDATE,PAYMENTFLAG,NAMEOFPAYMENTBRANCH,PAYMENTBRANCHADDR,PINCODE,CLAIMPREBYNOMINEENAME,CLAIMPREBYNOMINEERELATION,MEMBERSCHEMECERT,POSSESSIONMEMBER,SCHEMEADDRESS,SCHEMEPINCODE,DOCUMENTINFO,PENSIONDRWNUNDEPS,PPOISSUEDBY) VALUES "
					+ "("
					+ pensionno
					+ ",'"
					+ additonalInfo.getEarlyPensionTaken()
					+ "','"
					+ additonalInfo.getEarlyPensionDate()
					+ "','"
					+ additonalInfo.getEpForm10DSubDate()
					+ "','"
					+ additonalInfo.getQuantum1By3Option()
					+ "','"
					+ additonalInfo.getQuantum1By3Amount()
					+ "','"
					+ additonalInfo.getOptionReturnCaptial()
					+ "','"
					+ additonalInfo.getMemberDeathDate()
					+ "','"
					+ additonalInfo.getPaymentInfoType()
					+ "','"
					+ additonalInfo.getNameofPaymentBranch()
					+ "','"
					+ additonalInfo.getAddressofPayementBranch()
					+ "','"
					+ additonalInfo.getPaymentBranchPincode()
					+ "','"
					+ additonalInfo.getClaimNomineeRefName()
					+ "','"
					+ additonalInfo.getClaimNomineeRefRelation()
					+ "','"
					+ additonalInfo.getSchemeCertificateRecEncl()
					+ "','"
					+ additonalInfo.getPossesionMember()
					+ "','"
					+ additonalInfo.getNomineePostalAddrss()
					+ "','"
					+ additonalInfo.getNomineePincode()
					+ "','"
					+ additonalInfo.getDocumentInfo()
					+ "','"
					+ additonalInfo.getPensionDrwnFrom1995()
					+ "','"
					+ additonalInfo.getPponoIssuedBy() + "')";
		} else {
			otherPensQuery = "UPDATE	EPIS_EMPLOYEE_PENSION_DTLS SET REDPENSION='"
					+ additonalInfo.getEarlyPensionTaken()
					+ "',REDPENSION_DATE='"
					+ additonalInfo.getEarlyPensionDate()
					+ "',REDDATEOFSUBMSION='"
					+ additonalInfo.getEpForm10DSubDate()
					+ "',QUANTUMOPT='"
					+ additonalInfo.getQuantum1By3Option()
					+ "',QUANTUMOPTAMOUNT='"
					+ additonalInfo.getQuantum1By3Amount()
					+ "',NOMINEERETCAPTIAL='"
					+ additonalInfo.getOptionReturnCaptial()
					+ "',MEMBERDEATHDATE='"
					+ additonalInfo.getMemberDeathDate()
					+ "',PAYMENTFLAG='"
					+ additonalInfo.getPaymentInfoType()
					+ "',NAMEOFPAYMENTBRANCH='"
					+ additonalInfo.getNameofPaymentBranch()
					+ "',PAYMENTBRANCHADDR='"
					+ additonalInfo.getAddressofPayementBranch()
					+ "',PINCODE='"
					+ additonalInfo.getPaymentBranchPincode()
					+ "',CLAIMPREBYNOMINEENAME='"
					+ additonalInfo.getClaimNomineeRefName()
					+ "',DOCUMENTINFO='"
					+ additonalInfo.getDocumentInfo()
					+ "',CLAIMPREBYNOMINEERELATION='"
					+ additonalInfo.getClaimNomineeRefRelation()
					+ "',MEMBERSCHEMECERT='"
					+ additonalInfo.getSchemeCertificateRecEncl()
					+ "',POSSESSIONMEMBER='"
					+ additonalInfo.getPossesionMember()
					+ "',SCHEMEADDRESS='"
					+ additonalInfo.getNomineePostalAddrss()
					+ "',SCHEMEPINCODE='"
					+ additonalInfo.getNomineePincode()
					+ "',PENSIONDRWNUNDEPS='"
					+ additonalInfo.getPensionDrwnFrom1995()
					+ "',PPOISSUEDBY='"
					+ additonalInfo.getPponoIssuedBy()
					+ "',LASTACTIVE=CURRENT_DATE WHERE DELETEFLAG='N' AND PENSIONNO="
					+ pensionno;
		}
		log.info("PersonalDAO::addOtherPensionDtls" + otherPensQuery);
		try {
			st = con.createStatement();
			dbCnt = st.executeUpdate(otherPensQuery);
		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeStatement(st);
		}
		return dbCnt;
	}

	public String checkOtherPensionDtls(String pensionno, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String otherPensionDtlSelQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		ArrayList schmeList = new ArrayList();
		int dbCnt = 0;
		String returnFlag = "";

		otherPensionDtlSelQuery = "SELECT 'X' AS FLAG FROM EPIS_EMPLOYEE_PENSION_DTLS  WHERE PENSIONNO="
				+ pensionno + " AND DELETEFLAG='N'";
		log.info("Pension Details Query" + otherPensionDtlSelQuery);
		try {
			st = con.createStatement();
			rs = st.executeQuery(otherPensionDtlSelQuery);
			if (rs.next()) {
				returnFlag = rs.getString("FLAG");
			} else {
				returnFlag = "NOTFOUND";
			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return returnFlag;
	}

	public int addSchmemeDtls(String pensionno, Connection con,
			EmpMasterBean bean) {
		Statement schemeSt = null;
		String otherPensQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		StringBuffer buffer = new StringBuffer();
		ArrayList schmeList = new ArrayList();
		int dbCnt = 0;
		ArrayList schemeList = new ArrayList();
		schemeList = bean.getSchemeList();
		String schmeInfo = "", schmeNo = "", schmeControlNo = "", schmeAuthority = "", insertSchQry = "";
		int schmeCnt = 0;
		log.info("Scheme Certificate List size" + schemeList.size());

		try {

			boolean deleSchmeDtls = delSchemeDtls(bean.getEmpSerialNo(), con);
			log.info("Scheme Informaiton" + deleSchmeDtls);
			for (int i = 0; i < schemeList.size(); i++) {
				schemeSt = con.createStatement();
				schmeInfo = (String) schemeList.get(i);
				schmeCnt++;
				String[] schmemData = schmeInfo.split("#");

				if (schmemData.length == 2) {
					schmeControlNo = schmemData[0];
					schmeAuthority = schmemData[1];
					insertSchQry = "INSERT INTO epis_pension_certificate_dtls(pensionno,srno,CERTIFICONTROLNO,CERTIFIAUTHORITY) VALUES("
							+ bean.getEmpSerialNo()
							+ ","
							+ schmeCnt
							+ ",'"
							+ schmeControlNo + "','" + schmeAuthority + "')";
					log.info("INSERT QUERY" + insertSchQry);
					schemeSt.executeUpdate(insertSchQry);
					if (schemeSt != null) {
						schemeSt.close();
					}
				}

			}
		} catch (SQLException se) {
			log.printStackTrace(se);
		} finally {
			commonDB.closeStatement(schemeSt);

		}
		return dbCnt;
	}

	public EmployeeAdlPensionInfo getPensionForm10DDtls(String pensionno) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String familyDtlSelQuery = "";
		String certiControlNO = "", certifAuthority = "", rowId = "";
		EmployeeAdlPensionInfo adlPensionInfo = new EmployeeAdlPensionInfo();
		ArrayList schmeList = new ArrayList();
		familyDtlSelQuery = "SELECT PENSIONNO, REDPENSION, REDPENSION_DATE  , REDDATEOFSUBMSION, QUANTUMOPT , QUANTUMOPTAMOUNT   ,"
				+ " MEMBERDEATHDATE, PAYMENTFLAG , NAMEOFPAYMENTBRANCH , PAYMENTBRANCHADDR   , PINCODE , CLAIMPREBYNOMINEENAME , "
				+ "CLAIMPREBYNOMINEERELATION , MEMBERSCHEMECERT, POSSESSIONMEMBER, SCHEMEADDRESS, SCHEMEPINCODE, PENSIONDRWNUNDEPS , PPOISSUEDBY , LASTACTIVE  , "
				+ "DELETEFLAG,NOMINEERETCAPTIAL,DOCUMENTINFO FROM EPIS_EMPLOYEE_PENSION_DTLS where DELETEFLAG='N' AND  PENSIONNO="
				+ pensionno;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(familyDtlSelQuery);
			if (rs.next()) {
				adlPensionInfo = new EmployeeAdlPensionInfo();
				if (rs.getString("REDPENSION") != null) {
					adlPensionInfo.setEarlyPensionTaken(rs
							.getString("REDPENSION"));
				} else {
					adlPensionInfo.setEarlyPensionTaken("");
				}
				if (rs.getString("REDPENSION_DATE") != null) {
					adlPensionInfo.setEarlyPensionDate(rs
							.getString("REDPENSION_DATE"));
				} else {
					adlPensionInfo.setEarlyPensionDate("");
				}
				if (rs.getString("REDDATEOFSUBMSION") != null) {
					adlPensionInfo.setEpForm10DSubDate(rs
							.getString("REDDATEOFSUBMSION"));
				} else {
					adlPensionInfo.setEpForm10DSubDate("");
				}
				if (rs.getString("QUANTUMOPT") != null) {
					adlPensionInfo.setQuantum1By3Option(rs
							.getString("QUANTUMOPT"));
				} else {
					adlPensionInfo.setQuantum1By3Option("");
				}
				if (rs.getString("QUANTUMOPTAMOUNT") != null) {
					adlPensionInfo.setQuantum1By3Amount(rs
							.getString("QUANTUMOPTAMOUNT"));
				} else {
					adlPensionInfo.setQuantum1By3Amount("");
				}
				if (rs.getString("MEMBERDEATHDATE") != null) {
					adlPensionInfo.setMemberDeathDate(rs
							.getString("MEMBERDEATHDATE"));
				} else {
					adlPensionInfo.setMemberDeathDate("");
				}
				if (rs.getString("DOCUMENTINFO") != null) {
					adlPensionInfo
							.setDocumentInfo(rs.getString("DOCUMENTINFO"));
				} else {
					adlPensionInfo.setDocumentInfo("");
				}
				if (rs.getString("PAYMENTFLAG") != null) {
					adlPensionInfo.setPaymentInfoType(rs
							.getString("PAYMENTFLAG"));
					if (rs.getString("PAYMENTFLAG").trim().toUpperCase()
							.equals("B")) {
						adlPensionInfo.setPaymentInfoTypeDesc("Bank");
					} else {
						adlPensionInfo.setPaymentInfoTypeDesc("Post Office");
					}

				} else {
					adlPensionInfo.setPaymentInfoType("");
				}
				if (rs.getString("NAMEOFPAYMENTBRANCH") != null) {
					adlPensionInfo.setNameofPaymentBranch(rs
							.getString("NAMEOFPAYMENTBRANCH"));
				} else {
					adlPensionInfo.setNameofPaymentBranch("");
				}
				if (rs.getString("PAYMENTBRANCHADDR") != null) {
					adlPensionInfo.setAddressofPayementBranch(rs
							.getString("PAYMENTBRANCHADDR"));
				} else {
					adlPensionInfo.setAddressofPayementBranch("");
				}
				if (rs.getString("PINCODE") != null) {
					adlPensionInfo.setPaymentBranchPincode(rs
							.getString("PINCODE"));
				} else {
					adlPensionInfo.setPaymentBranchPincode("");
				}
				if (rs.getString("CLAIMPREBYNOMINEENAME") != null) {
					adlPensionInfo.setClaimNomineeRefName(rs
							.getString("CLAIMPREBYNOMINEENAME"));
				} else {
					adlPensionInfo.setClaimNomineeRefName("");
				}
				if (rs.getString("CLAIMPREBYNOMINEERELATION") != null) {
					adlPensionInfo.setClaimNomineeRefRelation(rs
							.getString("CLAIMPREBYNOMINEERELATION"));
				} else {
					adlPensionInfo.setClaimNomineeRefRelation("");
				}
				if (rs.getString("MEMBERSCHEMECERT") != null) {
					adlPensionInfo.setSchemeCertificateRecEncl(rs
							.getString("MEMBERSCHEMECERT"));
				} else {
					adlPensionInfo.setSchemeCertificateRecEncl("");
				}
				if (rs.getString("POSSESSIONMEMBER") != null) {
					adlPensionInfo.setPossesionMember(rs
							.getString("POSSESSIONMEMBER"));
				} else {
					adlPensionInfo.setPossesionMember("");
				}
				if (rs.getString("SCHEMEADDRESS") != null) {
					adlPensionInfo.setNomineePostalAddrss(rs
							.getString("SCHEMEADDRESS"));
				} else {
					adlPensionInfo.setNomineePostalAddrss("");
				}
				if (rs.getString("SCHEMEPINCODE") != null) {
					adlPensionInfo.setNomineePincode(rs
							.getString("SCHEMEPINCODE"));
				} else {
					adlPensionInfo.setNomineePincode("");
				}
				if (rs.getString("PENSIONDRWNUNDEPS") != null) {
					adlPensionInfo.setPensionDrwnFrom1995(rs
							.getString("PENSIONDRWNUNDEPS"));
				} else {
					adlPensionInfo.setPensionDrwnFrom1995("");
				}
				if (rs.getString("PPOISSUEDBY") != null) {
					adlPensionInfo
							.setPponoIssuedBy(rs.getString("PPOISSUEDBY"));
				} else {
					adlPensionInfo.setPponoIssuedBy("");
				}
				if (rs.getString("NOMINEERETCAPTIAL") != null) {
					adlPensionInfo.setOptionReturnCaptial(rs
							.getString("NOMINEERETCAPTIAL"));
				} else {
					adlPensionInfo.setOptionReturnCaptial("");
				}
			}

		} catch (SQLException se) {
			log.printStackTrace(se);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return adlPensionInfo;
	}

	public ArrayList getForm10DInfo(String pensionNo) {
		Connection con = null;
		ArrayList familyDetailsList = new ArrayList();
		ArrayList pensionDetailsList = new ArrayList();
		ArrayList nomieeDetailList = new ArrayList();
		ArrayList form10DList = new ArrayList();
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		EmployeeAdlPensionInfo adlPensionInfo = new EmployeeAdlPensionInfo();
		ArrayList schemeList = new ArrayList();
		FinancialReportDAO reportDAO = new FinancialReportDAO();
		String fromDate = "", toDate = "", cpfaccnosString = "", dateOfRetriment = "";
		boolean loadTransList = false;
		try {
			con = commonDB.getConnection();
			personalInfo = this.getPersonalInfo(con, pensionNo);
			familyDetailsList = this.form2FamilyReport(personalInfo);
			nomieeDetailList = this.form2NomineeReport(personalInfo);
			schemeList = this.getSchemeDtls(pensionNo, con);
			adlPensionInfo = this.getPensionForm10DDtls(pensionNo);
			try {
				dateOfRetriment = reportDAO.getRetriedDate(personalInfo
						.getDateOfBirth());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
			fromDate = personalInfo.getSeperationFromDate();
			fromDate = "01" + fromDate.substring(2, fromDate.length());
			toDate = personalInfo.getSeperationDate();
			if (fromDate.equals("---")) {
				loadTransList = true;
			} else if (toDate.equals("---")) {
				loadTransList = true;
			}
			log.info("fromDate" + fromDate + "toDate" + toDate
					+ "Wether Option" + personalInfo.getWetherOption().trim());
			if (loadTransList == false) {
				pensionDetailsList = reportDAO.getForm10DPensionInfo(con,
						fromDate, toDate, cpfaccnosString, personalInfo
								.getWetherOption().trim(), dateOfRetriment,
						personalInfo.getDateOfBirth(), pensionNo, true);
			}

			form10DList.add(personalInfo);
			form10DList.add(familyDetailsList);
			form10DList.add(nomieeDetailList);
			form10DList.add(pensionDetailsList);
			form10DList.add(schemeList);
			form10DList.add(adlPensionInfo);

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, null, null);
		}
		return form10DList;
	}

	public int updateNomineeDetails(EmpMasterBean bean, Connection con) {

		ResultSet rs = null;
		Statement st = null;

		String delQry = "", sqlQuery = "", Query = "";
		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
		int slno = 0, i = 0, count = 0;
		try {
			nomineeRows = bean.getNomineeRow();

			log.info("......nomineeRows......." + nomineeRows);

			StringTokenizer est = new StringTokenizer(nomineeRows, ":");

			int lengt = est.countTokens();
			String estrarr[] = new String[lengt];

			Query = "select pensionno from  employee_nominee_dtls where pensionno='"
					+ bean.getPensionNumber() + "'";
			log.info("---- Select Query-----" + Query);
			st = con.createStatement();
			rs = st.executeQuery(Query);
			if (rs.next()) {
				i++;
			}

			if (i > 0) {
				delQry = "delete from employee_nominee_dtls where pensionno='"
						+ bean.getPensionNumber() + "'";
				log.info("----delQry-----" + delQry);
				st.executeUpdate(delQry);
			}

			for (int e = 0; est.hasMoreTokens(); e++) {

				estrarr[e] = est.nextToken();
				String expsplit = estrarr[e];

				String[] strArr = expsplit.split("#");

				for (int ii = 0; ii < strArr.length; ii++) {
					slno = Integer.parseInt(strArr[0]);
					nomineeName = strArr[1];
					nomineeAddress = strArr[2];
					nomineeDob = strArr[3];
					nomineeRelation = strArr[4];
					nameofGuardian = strArr[5];
					gaurdianAddress = strArr[6];
					totalShare = strArr[7];
				}

				sqlQuery = "insert into employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,equalshare,region,pensionno,CPFACCNO)values("
						+ slno
						+ ",'"
						+ nomineeName
						+ "','"
						+ nomineeAddress
						+ "','"
						+ nomineeDob
						+ "','"
						+ nomineeRelation.toUpperCase()
						+ "','"
						+ nameofGuardian
						+ "','"
						+ gaurdianAddress
						+ "','"
						+ totalShare
						+ "','"
						+ bean.getEqualShare()
						+ "','"
						+ bean.getRegion()
						+ "','"
						+ bean.getPensionNumber()
						+ "','" + bean.getNewCpfAcNo() + "')";

				log.info("----sqlQuery-----" + sqlQuery);
				count = st.executeUpdate(sqlQuery);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(null, st, rs);
		}

		return count;

	}

	// New Method
	public String loadNomineeDetails(String pensionNo, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean empBean = null;
		ArrayList loadNomineeList = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		String sqlQuery = "";
		sqlQuery = "select SRNO,NOMINEENAME,NOMINEEDOB,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,EQUALSHARE from employee_nominee_dtls where EMPFLAG='Y' and  PENSIONNO='"
				+ pensionNo + "' order by  SRNO";
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadNomineeDetails" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				empBean = new EmpMasterBean();

				if (rs.getString("SRNO") != null) {
					empBean.setSrno(rs.getString("SRNO"));
				} else {
					empBean.setSrno("");
				}

				if (rs.getString("NOMINEENAME") != null) {
					empBean.setNomineeName(rs.getString("NOMINEENAME"));

				} else {
					empBean.setNomineeName("");
				}

				if (rs.getString("NOMINEEDOB") != null) {
					empBean.setNomineeDob(CommonUtil.getDatetoString(rs
							.getDate("NOMINEEDOB"), "dd/MMM/yyyy"));
				} else {
					empBean.setNomineeDob("");
				}

				if (rs.getString("NOMINEEADDRESS") != null) {
					empBean.setNomineeAddress(rs.getString("NOMINEEADDRESS"));
				} else {
					empBean.setNomineeAddress("");
				}

				if (rs.getString("NOMINEERELATION") != null) {
					empBean.setNomineeRelation(rs.getString("NOMINEERELATION"));
				} else {
					empBean.setNomineeRelation("");
				}

				if (rs.getString("NAMEOFGUARDIAN") != null) {
					empBean.setNameofGuardian(rs.getString("NAMEOFGUARDIAN"));
				} else {
					empBean.setNameofGuardian("");
				}

				if (rs.getString("GAURDIANADDRESS") != null) {
					empBean.setGaddress(rs.getString("GAURDIANADDRESS"));
				} else {
					empBean.setGaddress("");
				}

				if (rs.getString("TOTALSHARE") != null) {
					empBean.setTotalShare(rs.getString("TOTALSHARE"));
				} else {
					empBean.setTotalShare("");
				}

				if (rs.getString("EQUALSHARE") != null) {
					empBean.setEqualShare(rs.getString("EQUALSHARE"));
				} else {
					empBean.setEqualShare("");
				}

				buffer.append(empBean.getSrno());
				buffer.append("#");
				buffer.append(empBean.getNomineeName());
				buffer.append("#");
				buffer.append(empBean.getNomineeDob());
				buffer.append("#");
				buffer.append(empBean.getNomineeAddress());
				buffer.append("#");
				buffer.append(empBean.getNomineeRelation());
				buffer.append("#");
				buffer.append(empBean.getNameofGuardian());
				buffer.append("#");
				buffer.append(empBean.getGaddress());
				buffer.append("#");
				buffer.append(empBean.getTotalShare());
				buffer.append("#");
				buffer.append(empBean.getEqualShare());
				buffer.append(":");
				// reportList.add(buffer.toString());
				log.info("buffer.toString()" + buffer.toString());
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return buffer.toString();
	}
	public String loadNomineeDetails(String employeeName, String region,
			String pensionNo, String monthYear) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean empBean = null;
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
				empBean = new EmpMasterBean();

				if (rs.getString("SRNO") != null) {
					empBean.setSrno(rs.getString("SRNO"));
				} else {
					empBean.setSrno("");
				}

				if (rs.getString("NOMINEENAME") != null) {
					empBean.setNomineeName(rs.getString("NOMINEENAME"));

				} else {
					empBean.setNomineeName("");
				}

				if (rs.getString("NOMINEEDOB") != null) {
					empBean.setNomineeDob(CommonUtil.getDatetoString(rs
							.getDate("NOMINEEDOB"), "dd/MMM/yyyy"));
				} else {
					empBean.setNomineeDob("");
				}

				if (rs.getString("NOMINEEADDRESS") != null) {
					empBean.setNomineeAddress(rs.getString("NOMINEEADDRESS"));
				} else {
					empBean.setNomineeAddress("");
				}

				if (rs.getString("NOMINEERELATION") != null) {
					empBean.setNomineeRelation(rs.getString("NOMINEERELATION"));
				} else {
					empBean.setNomineeRelation("");
				}

				if (rs.getString("NAMEOFGUARDIAN") != null) {
					empBean.setNameofGuardian(rs.getString("NAMEOFGUARDIAN"));
				} else {
					empBean.setNameofGuardian("");
				}

				if (rs.getString("GAURDIANADDRESS") != null) {
					empBean.setGaddress(rs.getString("GAURDIANADDRESS"));
				} else {
					empBean.setGaddress("");
				}

				if (rs.getString("TOTALSHARE") != null) {
					empBean.setTotalShare(rs.getString("TOTALSHARE"));
				} else {
					empBean.setTotalShare("");
				}

				if (rs.getString("EQUALSHARE") != null) {
					empBean.setEqualShare(rs.getString("EQUALSHARE"));
				} else {
					empBean.setEqualShare("");
				}

				//loadNomineeList.add(advanceBasicBean);

				buffer.append(empBean.getSrno());
				buffer.append("#");
				buffer.append(empBean.getNomineeName());
				buffer.append("#");
				buffer.append(empBean.getNomineeDob());
				buffer.append("#");
				buffer.append(empBean.getNomineeAddress());
				buffer.append("#");
				buffer.append(empBean.getNomineeRelation());
				buffer.append("#");
				buffer.append(empBean.getNameofGuardian());
				buffer.append("#");
				buffer.append(empBean.getGaddress());
				buffer.append("#");
				buffer.append(empBean.getTotalShare());
				buffer.append("#");
				buffer.append(empBean.getEqualShare());
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
	public int updateFamilyDetails(EmpMasterBean bean, Connection con) {

		ResultSet rs = null;
		Statement st = null;

		String delQry = "", sqlQuery = "", Query = "";
		String familyMemberName = "", familyMemberDob = "", familyMemberRelation = "", familyRows = "";
		String region = "", station = "";
		int slno = 0, i = 0, count = 0;
		try {

			familyRows = bean.getFamilyRow();

			log.info("......familyRows......." + familyRows);

			StringTokenizer est = new StringTokenizer(familyRows, ":");

			int lengt = est.countTokens();
			String estrarr[] = new String[lengt];

			Query = "select pensionno from  employee_family_dtls where pensionno='"
					+ bean.getPensionNumber() + "'";
			log.info("---- Select Query-----" + Query);
			st = con.createStatement();
			rs = st.executeQuery(Query);
			if (rs.next()) {
				i++;
			}

			if (i > 0) {
				delQry = "delete from employee_family_dtls where pensionno='"
						+ bean.getPensionNumber() + "'";
				log.info("----delQry-----" + delQry);
				st.executeUpdate(delQry);
			}

			for (int e = 0; est.hasMoreTokens(); e++) {

				estrarr[e] = est.nextToken();
				String expsplit = estrarr[e];

				String[] strArr = expsplit.split("#");

				for (int ii = 0; ii < strArr.length; ii++) {
					slno = Integer.parseInt(strArr[0]);
					familyMemberName = strArr[1];
					familyMemberDob = strArr[2];
					familyMemberRelation = strArr[3];

				}

				sqlQuery = "insert into employee_family_dtls(SRNO,FAMILYMEMBERNAME,DATEOFBIRTH,FAMILYRELATION,region,CPFACCNO,PENSIONNO)values("
						+ slno
						+ ",'"
						+ familyMemberName
						+ "','"
						+ familyMemberDob
						+ "','"
						+ familyMemberRelation.toUpperCase()
						+ "','"
						+ bean.getRegion()
						+ "','"
						+ bean.getNewCpfAcNo()
						+ "','" + bean.getPensionNumber() + "')";

				log.info("----sqlQuery-----" + sqlQuery);
				count = st.executeUpdate(sqlQuery);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(null, st, rs);
		}

		return count;

	}

	public String loadFamilyDetails(String pensionNo, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean empBean = null;
		ArrayList loadNomineeList = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		String sqlQuery = "";
		sqlQuery = "select SRNO,FAMILYMEMBERNAME,DATEOFBIRTH,FAMILYRELATION from employee_family_dtls where EMPFLAG='Y' and  PENSIONNO='"
				+ pensionNo + "' order by  SRNO";
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadNomineeDetails" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				empBean = new EmpMasterBean();

				if (rs.getString("SRNO") != null) {
					empBean.setSrno(rs.getString("SRNO"));
				} else {
					empBean.setSrno("");
				}

				if (rs.getString("FAMILYMEMBERNAME") != null) {
					empBean.setFMemberName(rs.getString("FAMILYMEMBERNAME"));

				} else {
					empBean.setFMemberName("");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					empBean.setFDateofBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd/MMM/yyyy"));
				} else {
					empBean.setFDateofBirth("");
				}

				if (rs.getString("FAMILYRELATION") != null) {
					empBean.setFrelation(rs.getString("FAMILYRELATION"));
				} else {
					empBean.setFrelation("");
				}

				buffer.append(empBean.getSrno());
				buffer.append("#");
				buffer.append(empBean.getFMemberName());
				buffer.append("#");
				buffer.append(empBean.getFDateofBirth());
				buffer.append("#");
				buffer.append(empBean.getFrelation());
				buffer.append(":");
				// reportList.add(buffer.toString());
				log.info("buffer.toString()" + buffer.toString());
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return buffer.toString();
	}
	 //New Method
	
	public void getImage(String path, String userID) throws IOException,
		SQLException, EPISException {
	FileOutputStream outputFileOutputStream = null;
	String sqlText = null;
	Statement stmt = null;
	ResultSet rset = null;
	long blobLength;
	long position;
	BLOB image = null;
	int chunkSize;
	byte[] binaryBuffer;
	int bytesRead = 0;
	int totbytesRead = 0;
	int totbytesWritten = 0;
	Connection con = null;
	try {
		
		log.info("-----*******----- "+path);
		con = DBUtility.getConnection();
		stmt = con.createStatement();
		con.setAutoCommit(false);
		File outputBinaryFile1 = new File(path);
		outputFileOutputStream = new FileOutputStream(outputBinaryFile1);
		sqlText = "SELECT esignatory  FROM   epis_signatures "
				+ "WHERE  USERID = '" + userID + "' FOR UPDATE";
		log.info("PersonalDAO::getImage()-----"+sqlText);
		rset = stmt.executeQuery(sqlText);
		rset.next();
		image = ((OracleResultSet) rset).getBLOB("esignatory");
		
		blobLength = image.length();
		//log.info("------Image Length------"+blobLength);
		chunkSize = image.getChunkSize();
		binaryBuffer = new byte[chunkSize];
		for (position = 1; position <= blobLength; position += chunkSize) {
			bytesRead = image.getBytes(position, chunkSize, binaryBuffer);
			outputFileOutputStream.write(binaryBuffer, 0, bytesRead);
			totbytesRead += bytesRead;
			totbytesWritten += bytesRead;
		}
		outputFileOutputStream.close();
		con.commit();
		rset.close();
		stmt.close();

	} catch (IOException e) {
		con.rollback();
		log.error("Caught I/O Exception: (Write BLOB value to file - Get Method).");
		e.printStackTrace();
		throw e;
	} catch (SQLException e) {
		con.rollback();
		log.error("Caught SQL Exception: (Write BLOB value to file - Get Method).");
		log.error("SQL:\n" + sqlText);
		e.printStackTrace();
		throw e;
	}
	}

//	New Method
		public void uploadSignature(SignatureMappingBean signBean,String screenType) throws EPISException,
				Exception {
			 String esignature="",frmFinDate="",toFinDate="",query="",subQuery="",updateQry="";
			int result=0,k=0,j=0,updateVal=0;
			Connection con = null;
			Statement st=null;
			PreparedStatement pst = null;
			PreparedStatement pst1 = null;
			ResultSet rset = null;
			SignatureMappingBean screenInfo=null;
			try {

		con = DBUtility.getConnection();
		
		if(screenType.equals("EDIT")){		
			updateQry="update epis_signatures set ACTIVTY='"+signBean.getActivity()+"' where USERID="+signBean.getUserID();
			log.info("PersonalDAO::uploadSignature()======="+updateQry);
			st=con.createStatement();
			updateVal=st.executeUpdate(updateQry);
			 log.info("--updateVal--"+updateVal);
		}else{
		 
		con.setAutoCommit(false);
		
		query = "insert into epis_signatures(SIGNID,USERID,DISPMNGRNAME,STATION,DESIGDESCR,ESIGNATORY,ACTIVTY,ESIGNNAME) values((select Nvl((select max(SIGNID)+1 from epis_signatures),1) from dual),?,?,?,?,EMPTY_BLOB(),?,?) ";
		pst = con.prepareStatement(query);	
		 
		pst.setString(1,signBean.getUserID()); 
		pst.setString(2, signBean.getDispMgrName());
		pst.setString(3, signBean.getStation());
		pst.setString(4, signBean.getDesgNRemarks());
		pst.setString(5,signBean.getActivity());
		if(signBean.getSignature().getFileSize()>0){
			pst.setString(6, StringUtility.checknull("".equals(signBean.getSignature()) ? ""
					: (signBean.getUserName() + signBean.getSignature().getFileName()
							.substring(
									signBean.getSignature().getFileName()
											.lastIndexOf(".")))));
			
		log.info("---sign--"+signBean.getSignature()+"--actual value---"+signBean.getUserName() + signBean.getSignature().getFileName()
				.substring(
						signBean.getSignature().getFileName()
								.lastIndexOf(".")));	
			
		}else{
			pst.setString(6,"");
		}
		
		 
		
		 
		int i=0;
		i=pst.executeUpdate();
		log.info("----------------insertion-----------"+i);
		pst.close();
		if (signBean.getSignature().getFileSize()>0) {
			pst = con
					.prepareStatement("select esignatory from epis_signatures where USERID ='"
							+ StringUtility.checknull(signBean.getUserID())
							+ "' FOR UPDATE");
			rset = pst.executeQuery();
			
			int bytesRead = 0;
			int bytesWritten = 0;
			int totbytesRead = 0;
			int totbytesWritten = 0;

			if (rset.next()) {
				BLOB image = ((OracleResultSet) rset).getBLOB("esignatory");					
				int chunkSize = image.getChunkSize();
				byte[] binaryBuffer = new byte[chunkSize];
				int position = 1;
				InputStream inputFileInputStream = signBean.getSignature()
						.getInputStream();
				while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1) {
					bytesWritten = image.putBytes(position, binaryBuffer,
							bytesRead);
					position += bytesRead;
					totbytesRead += bytesRead;
					totbytesWritten += bytesWritten;
				}
				inputFileInputStream.close();
			}
		}
		con.commit();
		} 
		 
	} catch (SQLException sqle) {
		con.rollback();
		log.error("PersonalDAO:uploadSignature:Exception:" + sqle.getMessage());
		throw new EPISException(sqle);
	} catch (Exception e) {
		con.rollback();
		log.error("PersonalDAO:uploadSignature:Exception:" + e.getMessage());
		throw new EPISException(e);
	} finally {
		DBUtility.closeConnection(rset, pst, con);
	}
	}

//	New Method
	public int saveScreenSignDetails(SignatureMappingBean signBean,String screenType){
			String subQuery="",frmFinDate="",toFinDate="",updateQry="";
			int j=0,result=0,updateVal=0;
			Connection con=null;
			Statement st=null;
			PreparedStatement pst = null;	
			ResultSet rs=null;
			SignatureMappingBean screenInfo=null;
			 
			
		subQuery= "insert into epis_sign_permissions(USERID,MODULECD,SCREENCODE,FROMFINANCEDATE,TOFINANCEDATE) values(?,?,?,?,?) ";
		  
		try{
			con = DBUtility.getConnection();
			if(screenType.equals("EDIT")){
				updateQry="delete from epis_sign_permissions   where USERID="+signBean.getUserID();
				log.info("PersonalDAO::saveScreenSignDetails======"+updateQry);
				st=con.createStatement();
				updateVal=st.executeUpdate(updateQry);
				 
			}
			
			if(signBean.getScreenPermissionDetails()!=null){
				List screenDetails = signBean.getScreenPermissionDetails();			 			
				pst = con.prepareStatement(subQuery);
				
				for(j=0;j<screenDetails.size();j++){		 
			screenInfo = (SignatureMappingBean)screenDetails.get(j);		 
			frmFinDate= commonUtil.converDBToAppFormat(screenInfo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			toFinDate= commonUtil.converDBToAppFormat(screenInfo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			
			pst.setString(1, signBean.getUserID()); 
			pst.setString(2, screenInfo.getModuleCode());
			pst.setString(3, screenInfo.getScreenCode());
			pst.setString(4, frmFinDate);
			pst.setString(5, toFinDate);	
			result=pst.executeUpdate();
			 }
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, pst, null);
		}
		
		log.info("-------subQuery Result---------"+result);
		return result; 
	}
//	New Method
	public ArrayList editSignatures(String userID){
		Connection con = null;		 
		Statement st = null;		 
		ResultSet rs = null; 		
		String sqlQuery="";
		ArrayList list = new ArrayList();
		ArrayList screenDetList= new ArrayList();
		SignatureMappingBean signBean = null;
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		try{
		con = commonDB.getConnection();
		st = con.createStatement();	 

		sqlQuery = "SELECT ES.USERID AS USERID,ES.DISPMNGRNAME AS DISPMNGRNAME,ES.STATION AS STATION,ES.DESIGDESCR AS DESIGDESCR,ES.ACTIVTY AS ACTIVTY, ES.ESIGNNAME AS ESIGNNAME,EU.USERNAME  AS USERNAME FROM EPIS_SIGNATURES ES ,EPIS_USER EU WHERE ES.ACTIVTY='Y' AND EU.USERID=ES.USERID AND  ES.USERID='"+userID+"'";
		  
		log.info("editSignatures::sqlQuery  "+sqlQuery);
		rs=st.executeQuery(sqlQuery);
		while (rs.next()) {
			signBean = new SignatureMappingBean();
			if (rs.getString("USERID") != null) {
				signBean.setUserID(rs.getString("USERID"));
			} else {
				signBean.setUserID("");
			}
			if (rs.getString("USERNAME") != null) {
				signBean.setUserName(rs.getString("USERNAME"));
			} else {
				signBean.setUserName("");
			}
			if (rs.getString("DISPMNGRNAME") != null) {
				signBean.setDispMgrName(rs.getString("DISPMNGRNAME"));
			} else {
				signBean.setDispMgrName("");
			}	
			if (rs.getString("STATION") != null) {
				signBean.setStation(rs.getString("STATION"));
			} else {
				signBean.setStation("");
			}	
			if (rs.getString("DESIGDESCR") != null) {
				signBean.setDesgNRemarks(rs.getString("DESIGDESCR"));
			} else {
				signBean.setDesgNRemarks("");
			}		 		
			if (rs.getString("ACTIVTY") != null) {
				signBean.setActivity(rs.getString("ACTIVTY"));
			} else {
				signBean.setActivity("");
			}
			if (rs.getString("ESIGNNAME") != null) {
				signBean.setSignatureName(rs.getString("ESIGNNAME"));
			} else {
				signBean.setSignatureName("");
			}
			
			list.add(signBean);
		
		}
		
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return  list;	 

	}

//	New Method
	public ArrayList getScreenSignDetails(String userID){
		String sqlQuery="";
		ResultSet rs=null;
		Statement st=null;
		Connection con=null;
		SignatureMappingBean signBean=null;
		ArrayList list=new ArrayList();
		 
		sqlQuery = "SELECT ESP.MODULECD  AS MODULECD,ESP.SCREENCODE AS SCREENCODE,ESP.FROMFINANCEDATE AS FROMFINANCEDATE,ESP.TOFINANCEDATE  AS TOFINANCEDATE,"
				+" EAM.SCREENNAME AS SCREENNAME FROM EPIS_SIGN_PERMISSIONS ESP,EPIS_ACCESSCODES_MT EAM WHERE   EAM.MODULECODE=ESP.MODULECD AND ESP.SCREENCODE=EAM.SCREENCODE AND ESP.USERID = '"+userID+"'";
		  
		log.info("getScreenDetails::sqlQuery  "+sqlQuery);
		
		try{
			con = commonDB.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sqlQuery);
		while (rs.next()) {		
			signBean = new SignatureMappingBean();
			if (rs.getString("MODULECD") != null) {
				signBean.setModuleCode(rs.getString("MODULECD"));
			} else {
				signBean.setModuleCode("");
			}
			if (rs.getString("SCREENCODE") != null) {
				signBean.setScreenCode(rs.getString("SCREENCODE"));
			} else {
				signBean.setScreenCode("");
			}		 
			if (rs.getString("SCREENNAME") != null) {
				signBean.setScreenName(rs.getString("SCREENNAME"));
			} else {
				signBean.setScreenName("");
			}	
			if (rs.getString("FROMFINANCEDATE") != null) {
				signBean.setFromDate(commonUtil.converDBToAppFormat(rs.getDate("FROMFINANCEDATE"), "dd-MMM-yyyy","dd/MM/yyyy"));
			} else {
				signBean.setFromDate("");
			} 
			if (rs.getString("TOFINANCEDATE") != null) {
				signBean.setToDate(commonUtil.converDBToAppFormat(rs.getDate("TOFINANCEDATE"), "dd-MMM-yyyy","dd/MM/yyyy"));
			} else {
				signBean.setToDate("");
			}
			 
			list.add(signBean);
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


//	New Method
		public ArrayList searchSignatures(String userID){
			Connection con = null;		 
			Statement st = null;		 
			ResultSet rs = null; 		
			String sqlQuery="",dynamicQuery="";
			ArrayList list = new ArrayList();
			SignatureMappingBean signBean = null;
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			try{
			con = commonDB.getConnection();
			st = con.createStatement();	
			
			 
			sqlQuery = "SELECT ES.USERID AS USERID, ES.DISPMNGRNAME AS DISPMNGRNAME, ES.STATION  AS STATION, ES.DESIGDESCR   AS DESIGDESCR, ES.ACTIVTY  AS ACTIVTY, ES.ESIGNNAME AS ESIGNNAME, EU.USERNAME AS USERNAME,"
						+" ESP.FROMFINANCEDATE AS FROMFINANCEDATE, ESP.TOFINANCEDATE AS TOFINANCEDATE from epis_signatures ES, epis_user EU,epis_sign_permissions ESP where ES.userid = EU.USERID AND ES.USERID=ESP.USERID AND ES.ACTIVTY = 'Y'";
	 
			query.append(sqlQuery);
			if (!userID.equals("")) {
				whereClause.append(" AND ES.USERID='"+ userID+ "'");
				query.append(whereClause);
			}
			
			dynamicQuery = query.toString();
			log.info("searchSignatures::dynamicQuery  "+dynamicQuery);
			rs=st.executeQuery(dynamicQuery);
			while (rs.next()) {
				signBean = new SignatureMappingBean();
				 
				 
				if (rs.getString("USERID") != null) {
					signBean.setUserID(rs.getString("USERID"));
				} else {
					signBean.setUserID("");
				}	 			 
				if (rs.getString("STATION") != null) {
					signBean.setStation(rs.getString("STATION"));
				} else {
					signBean.setStation("");
				}
				if (rs.getString("DESIGDESCR") != null) {
					signBean.setDesgNRemarks(rs.getString("DESIGDESCR"));
				} else {
					signBean.setDesgNRemarks("");
				}
				if (rs.getString("ESIGNNAME") != null) {
					signBean.setSignatureName(rs.getString("ESIGNNAME"));
				} else {
					signBean.setSignatureName("");
				}
				if (rs.getString("USERNAME") != null) {
					signBean.setUserName(rs.getString("USERNAME"));
				} else {
					signBean.setUserName("");
				}
				 
				if (rs.getString("ACTIVTY") != null) {
					signBean.setActivity(rs.getString("ACTIVTY"));
				} 
				
				if (rs.getString("FROMFINANCEDATE") != null) {
					signBean.setFromDate(commonUtil.converDBToAppFormat(rs.getDate("FROMFINANCEDATE"),"dd/mm/yyyy","dd-MMM-yyyy"));
				} else {
					signBean.setFromDate("---");
				}
				if (rs.getString("TOFINANCEDATE") != null) {
					signBean.setToDate(commonUtil.converDBToAppFormat(rs.getDate("TOFINANCEDATE"),"dd/mm/yyyy","dd-MMM-yyyy"));
				} else {
					signBean.setToDate("---");
				}
				list.add(signBean);
			
			}
			
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return  list;	 
		
		}
}
