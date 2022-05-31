package com.epis.dao.rpfc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.epis.bean.rpfc.ArrearsTransactionBean;
import com.epis.bean.rpfc.CompartiveReportAaiDataBean;
import com.epis.bean.rpfc.CompartiveReportForm3DataBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.EmployeeValidateInfo;
import com.epis.bean.rpfc.FinaceDataAvailable;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.PensionBean;
import com.epis.bean.rpfc.form3Bean;
import com.epis.bean.rpfc.form3CommonBean;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class FinancialDAO {
	Log log = new Log(FinancialDAO.class);
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil = new CommonUtil();
	PensionDAO pensionDAO = new PensionDAO();
	CommonDAO commonDAO=new CommonDAO();
	protected int gridLength = commonUtil.gridLength();

	public Map getYearPension() {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Map yearMap = new LinkedHashMap();
		String query = "SELECT distinct TO_CHAR(MONTHYEAR,'YYYY') AS YEAR FROM employee_pension_validate";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			String yearDesc = "";
			while (rs.next()) {
				if (rs.getString("YEAR") != null) {
					yearDesc = rs.getString("YEAR");
					yearMap.put(yearDesc, yearDesc);
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return yearMap;
	}

	public Map getPensionAirportList() {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Map airportMap = new LinkedHashMap();
		String query = "SELECT distinct AIRPORTCODE  FROM employee_pension_validate";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			String airportDesc = "";
			while (rs.next()) {
				if (rs.getString("AIRPORTCODE") != null) {
					airportDesc = rs.getString("AIRPORTCODE");
					airportMap.put(airportDesc, airportDesc);
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return airportMap;
	}

	public ArrayList financialDataAll(String date, int startIndex,
			String stationCode) {
		log.info(" FinacialDAO :financialDataAll() entering method()");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Date dobDt = new Date();
		ArrayList financialList = null;
		ArrayList validateList = new ArrayList();
		String dob = "", whetherOption = "", validateDataAAI = "";
		int emoluments = 0;
		double validePF = 0, validatePension = 0, validateTotal = 0, aaiPF = 0, aaiPension = 0, aaiTotal = 0, calEmoluments = 0, pfStatuary = 0;
		EmployeeValidateInfo employeValidate = null;
		log.info("=========financialDataAll=======================" + date
				+ "stationCode" + stationCode + "startIndex" + startIndex);
		String query = "SELECT EMPINFO.DATEOFBIRTH AS DOB,EMPINFO.DESEGNATION AS DESG,EMPINFO.EMPLOYEENAME AS EMPNAME,EMPINFO.EMPLOYEENO AS EMPNO,"
				+ "EMPINFO.WETHEROPTION AS WETHEROPTION, EMPPNSVAL.CPFACCNO AS CPFACCNO,EMPPNSVAL.EMOLUMENTS AS EMOLUMENTS,EMPPNSVAL.EMPPFSTATUARY AS EMPPFSTATUARY,EMPPNSVAL.EMPVPF AS EMPVPF,"
				+ "EMPPNSVAL.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,EMPPNSVAL.EMPADVRECINTEREST AS EMPADVRECINTEREST,EMPPNSVAL.EMPTOTAL AS EMPTOTAL,EMPPNSVAL.AAICONPF AS AAICONPF,EMPPNSVAL.AAICONPENSION AS AAICONPENSION,"
				+ "EMPPNSVAL.AAITOTAL  AS AAITOTAL FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL,EMPLOYEE_INFO EMPINFO WHERE EMPPNSVAL.empflag='Y' AND EMPINFO.CPFACNO=EMPPNSVAL.CPFACCNO AND EMPINFO.EMPLOYEENAME=EMPPNSVAL.EMPLOYEENAME AND "
				+ "TO_char(MONTHYEAR, 'Mon-yy')='"
				+ date
				+ "' AND  EMPPNSVAL.AIRPORTCODE='"
				+ stationCode
				+ "' AND EMPINFO.EMPLOYEENO=EMPPNSVAL.EMPLOYEENO ORDER BY EMPINFO.EMPLOYEENAME";
		log.info("query is " + query);
		int countGridLength = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			con = commonDB.getConnection();
			st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery();
			DecimalFormat df2 = new DecimalFormat("#########0.00");
			if (rs.next()) {
				financialList = new ArrayList();
				if (rs.absolute(startIndex)) {
					countGridLength++;
					employeValidate = new EmployeeValidateInfo();
					if (rs.getString("DOB") != null) {
						dobDt = rs.getDate("DOB");
						dob = dateFormat.format(dobDt);
						employeValidate.setDateOfBirth(dob);
					}

					employeValidate.setDesegnation(rs.getString("DESG"));
					if (rs.getString("EMPNAME") != null) {
						employeValidate
								.setEmployeeName(rs.getString("EMPNAME"));
					} else {
						employeValidate.setEmployeeName("");
					}
					log.info("DOB============" + dob + "EMployee Name"
							+ rs.getString("EMPNAME"));
					employeValidate.setEmployeeNo(rs.getString("EMPNO"));
					log
							.info("=======financialDataAll==========whetherOption==empname"
									+ rs.getString("EMPNAME")
									+ "Whether Option"
									+ rs.getString("WETHEROPTION"));
					if (rs.getString("WETHEROPTION") != null) {
						whetherOption = rs.getString("WETHEROPTION");

						employeValidate.setWetherOption(whetherOption);
					} else {
						employeValidate.setWetherOption("");
					}
					employeValidate.setCpfaccno(rs.getString("CPFACCNO"));
					if (rs.getString("EMOLUMENTS") != null) {
						employeValidate.setEmoluments(rs
								.getString("EMOLUMENTS"));
					} else {
						employeValidate.setEmoluments("0");
					}
					if (rs.getString("EMPPFSTATUARY") != null) {
						pfStatuary = Double.parseDouble(rs
								.getString("EMPPFSTATUARY"));
						employeValidate.setEmpPFStatuary(Double
								.toString(pfStatuary));
					} else {
						employeValidate.setEmpPFStatuary(rs
								.getString("EMPPFSTATUARY"));
					}
					employeValidate.setEmpVPF(rs.getString("EMPVPF"));
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						employeValidate.setEmpAdvRecPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						employeValidate.setEmpAdvRecPrincipal("0");
					}

					if (rs.getString("EMPADVRECINTEREST") != null) {
						employeValidate.setEmpAdvRecInterest(rs
								.getString("EMPADVRECINTEREST"));
					} else {
						employeValidate.setEmpAdvRecInterest("0");
					}
					employeValidate.setEmptotal(rs.getString("EMPTOTAL"));
					if (rs.getString("AAICONPF") != null) {
						aaiPF = Double.parseDouble(rs.getString("AAICONPF"));
						employeValidate.setAaiconPF(Double.toString(aaiPF));
					}
					if (rs.getString("AAICONPENSION") != null) {
						aaiPension = Double.parseDouble(rs
								.getString("AAICONPENSION"));
						employeValidate.setAaiconPension(Double
								.toString(aaiPension));
					}
					if (rs.getString("AAITOTAL") != null) {
						aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
						employeValidate.setAaiTotal(Double.toString(aaiTotal));
					}

					/*
					 * Need to implemented one point 1)Total AAI's contribution
					 * [PF (column 13) + PENSION(column 14)] shall not be more
					 * than 12% of emoluments.
					 */
					log
							.info("==========financialDataAll================Employee Name"
									+ employeValidate.getEmployeeName());
					validateDataAAI = validatePFPension(dob, employeValidate
							.getEmoluments(),
							employeValidate.getWetherOption(), date);

					validateList = commonUtil.getTheList(validateDataAAI, ",");
					if (validateList.size() != 0) {
						validatePension = Double.parseDouble(validateList
								.get(0).toString());
						validePF = Double.parseDouble(validateList.get(1)
								.toString());
						validateTotal = Double.parseDouble(validateList.get(2)
								.toString());
						employeValidate.setValAAIPension(Double
								.toString(validatePension));
						employeValidate.setValAAIPF(Double.toString(validePF));
						employeValidate.setValAAITotal(Double
								.toString(validateTotal));
					}
					log.info("aaiPF" + aaiPF + "validePF" + validePF);
					log.info("aaiPension" + aaiPension + "validatePension"
							+ validatePension);
					log.info("aaiTotal" + aaiTotal + "validateTotal"
							+ validateTotal);
					calEmoluments = new Double(df2
							.format((emoluments * 12) / 100f)).doubleValue();
					pfStatuary = new Double(df2.format(pfStatuary / 100f))
							.doubleValue();
					log.info("calEmoluments" + calEmoluments + "pfStatuary"
							+ pfStatuary);
					if (((aaiPF != validePF) || (aaiPension != validatePension) || (aaiTotal != validateTotal))
							&& (calEmoluments != pfStatuary)) {
						log.info("Checked Flag aaiPF" + aaiPF + "validePF"
								+ validePF);
						employeValidate.setValidateFlag(true);
					} else {
						log.info("false Checked Flag aaiPF" + aaiPF
								+ "validePF" + validePF);
						employeValidate.setValidateFlag(false);
					}
					employeValidate.setEffectiveDate(date);
					financialList.add(employeValidate);
				}

				while (rs.next() && countGridLength < gridLength) {
					countGridLength++;
					employeValidate = new EmployeeValidateInfo();

					if (rs.getString("DOB") != null) {
						dobDt = rs.getDate("DOB");
						dob = dateFormat.format(dobDt);
						employeValidate.setDateOfBirth(dob);
					}
					employeValidate.setDesegnation(rs.getString("DESG"));
					if (rs.getString("EMPNAME") != null) {
						employeValidate
								.setEmployeeName(rs.getString("EMPNAME"));
					} else {
						employeValidate.setEmployeeName("");
					}

					employeValidate.setEmployeeNo(rs.getString("EMPNO"));
					log
							.info("=======financialDataAll==========whetherOption==empname"
									+ rs.getString("EMPNAME")
									+ "Whether Option"
									+ rs.getString("WETHEROPTION"));
					if (rs.getString("WETHEROPTION") != null) {
						whetherOption = rs.getString("WETHEROPTION");

						employeValidate.setWetherOption(whetherOption);
					}

					employeValidate.setCpfaccno(rs.getString("CPFACCNO"));

					if (rs.getString("EMOLUMENTS") != null) {
						employeValidate.setEmoluments(rs
								.getString("EMOLUMENTS"));
					} else {
						employeValidate.setEmoluments("0");
					}

					if (rs.getString("EMPPFSTATUARY") != null) {
						pfStatuary = Double.parseDouble(rs
								.getString("EMPPFSTATUARY"));
						employeValidate.setEmpPFStatuary(Double
								.toString(pfStatuary));
					} else {
						employeValidate.setEmpPFStatuary(rs
								.getString("EMPPFSTATUARY"));
					}

					employeValidate.setEmpVPF(rs.getString("EMPVPF"));
					if (rs.getString("EMPADVRECPRINCIPAL") != null) {
						employeValidate.setEmpAdvRecPrincipal(rs
								.getString("EMPADVRECPRINCIPAL"));
					} else {
						employeValidate.setEmpAdvRecPrincipal("0");
					}

					if (rs.getString("EMPADVRECINTEREST") != null) {
						employeValidate.setEmpAdvRecInterest(rs
								.getString("EMPADVRECINTEREST"));
					} else {
						employeValidate.setEmpAdvRecInterest("0");
					}
					employeValidate.setEmptotal(rs.getString("EMPTOTAL"));
					if (rs.getString("AAICONPF") != null) {
						aaiPF = Double.parseDouble(rs.getString("AAICONPF"));
						employeValidate.setAaiconPF(Double.toString(aaiPF));
					}
					if (rs.getString("AAICONPENSION") != null) {
						aaiPension = Double.parseDouble(rs
								.getString("AAICONPENSION"));
						employeValidate.setAaiconPension(Double
								.toString(aaiPension));
					}
					if (rs.getString("AAITOTAL") != null) {
						aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
						employeValidate.setAaiTotal(Double.toString(aaiTotal));
					}

					/*
					 * Need to implemented one point 1)Total AAI's contribution
					 * [PF (column 13) + PENSION(column 14)] shall not be more
					 * than 12% of emoluments.
					 */
					log
							.info("==========financialDataAll================Employee Name"
									+ employeValidate.getEmployeeName());
					validateDataAAI = validatePFPension(dob, employeValidate
							.getEmoluments(),
							employeValidate.getWetherOption(), date);

					validateList = commonUtil.getTheList(validateDataAAI, ",");
					if (validateList.size() != 0) {

						validatePension = Double.parseDouble(validateList
								.get(0).toString());
						validePF = Double.parseDouble(validateList.get(1)
								.toString());
						validateTotal = Double.parseDouble(validateList.get(2)
								.toString());
						employeValidate.setValAAIPension(Double
								.toString(validatePension));
						employeValidate.setValAAIPF(Double.toString(validePF));
						employeValidate.setValAAITotal(Double
								.toString(validateTotal));
					}
					log.info("aaiPF" + aaiPF + "validePF" + validePF);
					log.info("aaiPension" + aaiPension + "validatePension"
							+ validatePension);
					log.info("aaiTotal" + aaiTotal + "validateTotal"
							+ validateTotal);
					calEmoluments = new Double(df2
							.format((emoluments * 12) / 100f)).doubleValue();
					pfStatuary = new Double(df2.format(pfStatuary / 100f))
							.doubleValue();
					log.info("calEmoluments" + calEmoluments + "pfStatuary"
							+ pfStatuary);
					if (((aaiPF != validePF) || (aaiPension != validatePension) || (aaiTotal != validateTotal))
							&& (calEmoluments != pfStatuary)) {
						log.info("Checked Flag aaiPF" + aaiPF + "validePF"
								+ validePF);
						employeValidate.setValidateFlag(true);
					} else {
						log.info("false Checked Flag aaiPF" + aaiPF
								+ "validePF" + validePF);
						employeValidate.setValidateFlag(false);
					}
					employeValidate.setEffectiveDate(date);
					financialList.add(employeValidate);
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			// e.printStackTrace();
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info(" FinacialDAO :financialDataAll() entering method()");
		return financialList;
	}

	public String validatePFPension(String dateOfBirth, String formEmoluments,
			String whetherOption, String effectiveDate) {
		log.info(" FinacialDAO :validatePFPension() entering method()");
		int cellingRate = 0;
		double emoluments = 0.0;
		long employeeOFYears = 0;
		double calPension = 0, caltdPF = 0, calTotalAAICon = 0;
		StringBuffer buffer = new StringBuffer();
		emoluments = Double.parseDouble(formEmoluments);
		if (!dateOfBirth.equals("")) {
			employeeOFYears = commonUtil.getDateDifference(dateOfBirth, this
					.getCurrentDate("dd-MMM-yyyy"));
		}
		try {
			effectiveDate = commonUtil.converDBToAppFormat(effectiveDate,
					"MMM-yy", "dd-MMM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info("employeeOFYears (validatePFPension) Retired"
				+ employeeOFYears + "whetherOption" + whetherOption);
		DecimalFormat df2 = new DecimalFormat("#########0.00");
		if (employeeOFYears > 58) {
			calPension = 0;
			caltdPF = new Double(df2.format((emoluments * 12) / 100f))
					.doubleValue();
			calTotalAAICon = caltdPF;
		} else if (employeeOFYears < 58) {
			if (whetherOption.trim().equals("A")) {
				calPension = new Double(df2
						.format((emoluments * 8.33) / 100.0f)).doubleValue();
				calTotalAAICon = new Double(df2
						.format((emoluments * 12) / 100.0f)).doubleValue();
				caltdPF = new Double(df2.format(calTotalAAICon - calPension))
						.doubleValue();
			} else {
				if (whetherOption.trim().equals("B")
						|| whetherOption.trim().toLowerCase().equals("yes")) {

					cellingRate = this.getCellingRate(effectiveDate);
					log
							.info("========validatePFPension===========cellingRate(validatePFPension)"
									+ cellingRate);
					calPension = new Double(df2
							.format((cellingRate * 8.33) / 100)).doubleValue();
					caltdPF = new Double(df2.format((emoluments * 12) / 100
							- calPension)).doubleValue();
					calTotalAAICon = new Double(df2
							.format((emoluments * 12) / 100f)).doubleValue();
				}

			}
		}
		log.info("=======End=of=validatePFPension===========calPension"
				+ calPension + "caltdPF" + caltdPF + "calTotalAAICon"
				+ calTotalAAICon);
		buffer.append(calPension + "," + caltdPF + "," + calTotalAAICon);
		log.info(" FinacialDAO :validatePFPension() leaving method()");
		return buffer.toString();
	}

	public int getCellingRate(String effectiveDate) {
		log.info(" FinacialDAO :getCellingRate() entering method()");
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		int cellingRate = 0;
		String query = "SELECT TO_CHAR(WITHEFFCTDATE,'mm') , CEILINGRATE FROM EMPLOYEE_CEILING_MASTER WHERE  TO_CHAR(WITHEFFCTDATE,'mon-yyyy') = TO_CHAR(TO_DATE('"
				+ effectiveDate + "'),'mon-yyyy')";

		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				cellingRate = rs.getInt("CEILINGRATE");
				log.info("===============getCellingRate================="
						+ cellingRate);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info(" FinacialDAO :getCellingRate() leaving method()");
		return cellingRate;
	}

	public String getCurrentDate(String format) {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.format(new Date());
		return date;
	}

	public EmployeeValidateInfo getPensionValidateDetails(String cpfaccno,
			String unitCD, String effectiveDate) {
		log.info(" FinacialDAO :getPensionValidateDetails() entering method()");
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		EmployeeValidateInfo employeeValidate = new EmployeeValidateInfo();
		log.info("cpfaccno(getPensionValidateDetails)" + cpfaccno + "unitCD"
				+ unitCD);
		String query = "SELECT EMPINFO.DATEOFBIRTH AS DOB,EMPINFO.DESEGNATION AS DESG,EMPINFO.EMPLOYEENAME AS EMPNAME,EMPINFO.EMPLOYEENO AS EMPNO,"
				+ "EMPINFO.WETHEROPTION AS WETHEROPTION, EMPPNSVAL.CPFACCNO AS CPFACCNO,EMPPNSVAL.EMOLUMENTS AS EMOLUMENTS,EMPPNSVAL.EMPPFSTATUARY AS EMPPFSTATUARY,EMPPNSVAL.EMPVPF AS EMPVPF,"
				+ "EMPPNSVAL.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,EMPPNSVAL.EMPADVRECINTEREST AS EMPADVRECINTEREST,EMPPNSVAL.EMPTOTAL AS EMPTOTAL,EMPPNSVAL.AAICONPF AS AAICONPF,EMPPNSVAL.AAICONPENSION AS AAICONPENSION,"
				+ "EMPPNSVAL.AAITOTAL  AS AAITOTAL FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL,EMPLOYEE_INFO EMPINFO WHERE EMPPNSVAL.empflag='Y' AND  EMPINFO.CPFACNO=EMPPNSVAL.CPFACCNO AND  EMPINFO.CPFACNO='"
				+ cpfaccno
				+ "' "
				+ "AND  EMPPNSVAL.AIRPORTCODE='"
				+ unitCD
				+ "' AND  TO_char(EMPPNSVAL.MONTHYEAR, 'Mon-yy')='"
				+ effectiveDate + "'";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				employeeValidate.setCpfaccno(cpfaccno);
				employeeValidate.setDesegnation(rs.getString("DESG"));
				employeeValidate.setEmployeeName(rs.getString("EMPNAME"));
				employeeValidate.setEmployeeNo(rs.getString("EMPNO"));
				if (rs.getString("WETHEROPTION") != null) {
					employeeValidate.setWetherOption(rs
							.getString("WETHEROPTION"));
				} else {
					employeeValidate.setWetherOption("");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					employeeValidate.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					employeeValidate.setEmoluments("");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					employeeValidate.setEmpPFStatuary(rs
							.getString("EMPPFSTATUARY"));
				} else {
					employeeValidate.setEmpPFStatuary("");
				}
				if (rs.getString("EMPVPF") != null) {
					employeeValidate.setEmpVPF(rs.getString("EMPVPF"));
				} else {
					employeeValidate.setEmpVPF("");
				}
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					employeeValidate.setEmpAdvRecPrincipal(rs
							.getString("EMPADVRECPRINCIPAL"));
				} else {
					employeeValidate.setEmpAdvRecPrincipal("");
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					employeeValidate.setEmpAdvRecInterest(rs
							.getString("EMPADVRECINTEREST"));
				} else {
					employeeValidate.setEmpAdvRecInterest("");
				}
				if (rs.getString("EMPTOTAL") != null) {
					employeeValidate.setEmptotal(rs.getString("EMPTOTAL"));
				} else {
					employeeValidate.setEmptotal("");
				}
				if (rs.getString("AAICONPF") != null) {
					employeeValidate.setAaiconPF(rs.getString("AAICONPF"));
				} else {
					employeeValidate.setAaiconPF("");
				}
				if (rs.getString("AAICONPENSION") != null) {
					employeeValidate.setAaiconPension(rs
							.getString("AAICONPENSION"));
				} else {
					employeeValidate.setAaiconPension("");
				}
				double aaiTotal = 0;
				if (rs.getString("AAITOTAL") != null) {
					aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				} else {
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info(" FinacialDAO :getPensionValidateDetails() leaving method()");
		return employeeValidate;
	}

	public int updatePensionValidateDetails(String cpfaccno, String unitCD,
			String valPF, String valPension, String valTotal, String pfStatury,
			String pfDedcution, String userName, String effectiveDate) {
		log
				.info(" FinacialDAO :updatePensionValidateDetails() entering method()");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		EmployeeValidateInfo updateEmployeeValidate = new EmployeeValidateInfo();
		log.info("cpfaccno(getPensionValidateDetails)" + cpfaccno + "unitCD"
				+ unitCD);
		log.info("valPF(getPensionValidateDetails)" + valPF + "valPension"
				+ valPension + "valTotal" + valTotal);
		log.info("pfStatury(getPensionValidateDetails)" + pfStatury
				+ "pfDedcution" + pfDedcution);
		String query = "";
		int updateVal = 0;
		updateEmployeeValidate = this.getPensionValidateDetails(cpfaccno,
				unitCD, effectiveDate);
		updateEmployeeValidate.setValAAIPension(valPension);
		updateEmployeeValidate.setValAAIPF(valPF);
		updateEmployeeValidate.setValAAITotal(valTotal);

		query = "UPDATE EMPLOYEE_PENSION_VALIDATE SET AAICONPF=?,AAICONPENSION=?,AAITOTAL=?,EMOLUMENTS=?,EMPPFSTATUARY=? WHERE CPFACCNO=? AND AIRPORTCODE=? AND empflag='Y'";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.prepareStatement(query);
			st.setString(1, valPF);
			st.setString(2, valPension);
			st.setString(3, valTotal);
			st.setString(4, pfDedcution);
			st.setString(5, pfStatury);
			st.setString(6, cpfaccno);
			st.setString(7, unitCD);
			updateVal = st.executeUpdate();
			commonDB.closeConnection(con, st, rs);
			if (updateVal != 0) {
				updateVal = this.insertValidateHistory(updateEmployeeValidate,
						userName, unitCD);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log
				.info(" FinacialDAO :updatePensionValidateDetails() leaving method()");
		return updateVal;
	}

	private int insertValidateHistory(EmployeeValidateInfo bean,
			String userName, String unitCD) throws InvalidDataException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		EmployeeValidateInfo updateEmployeeValidate = new EmployeeValidateInfo();
		String query_history = "";
		int insertVal = 0;

		double aaiTotal = 0, aaiPF = 0, aaiPension;
		log.info("bean.getAaiconPF()" + bean.getAaiconPF());
		if (!bean.getAaiconPF().equals("")) {
			aaiPF = Double.parseDouble(bean.getAaiconPF());
		} else {
			aaiPF = 0;
		}
		if (!bean.getAaiconPension().equals("")) {
			aaiPension = Double.parseDouble(bean.getAaiconPension());
		} else {
			aaiPension = 0;
		}
		log.info("bean.getAaiTotal()" + bean.getAaiTotal());
		if (!bean.getAaiTotal().equals("")) {
			aaiTotal = Double.parseDouble(bean.getAaiTotal());
		} else {
			aaiTotal = 0;
		}
		query_history = "INSERT INTO EMPLOYEE_VALIDATE_HISTORY (CPFACCNO,UNITCODE,EFFECTDATE,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,"
				+ "EMPADVRECPRINCIPAL,EMPADVRECINTEREST,EMPTOTAL,NEWEMOLUMENTS,NEWEMPPFSTATUARY,NEWEMPVPF,"
				+ "NEWEMPADVRECPRINCIPAL,NEWEMPADVRECINTEREST,NEWEMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,NEWAAICONPF,NEWAAICONPENSION,NEWAAITOTAL,USERNAME)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		log.info("query is " + query_history);
		try {
			con = commonDB.getConnection();
			st = con.prepareStatement(query_history);
			st.setString(1, bean.getCpfaccno());
			st.setString(2, unitCD);
			String currentDate = this.getCurrentDate("dd-MMM-yyyy");
			log.info("currentDate(insertValidateHistory)" + currentDate);
			log.info("bean.getAaiTotal()(insertValidateHistory)"
					+ bean.getAaiTotal());
			st.setString(3, currentDate);
			st.setString(4, bean.getEmoluments());
			st.setString(5, bean.getEmpPFStatuary());
			st.setString(6, bean.getEmpVPF());
			st.setString(7, bean.getEmpAdvRecPrincipal());
			st.setString(8, bean.getEmpAdvRecInterest());
			st.setString(9, bean.getEmptotal());
			st.setString(10, bean.getEmoluments());
			st.setString(11, bean.getEmpPFStatuary());
			st.setString(12, bean.getEmpVPF());
			st.setString(13, bean.getEmpAdvRecPrincipal());
			st.setString(14, bean.getEmpAdvRecInterest());
			st.setString(15, bean.getEmptotal());
			st.setString(16, bean.getAaiconPF());
			st.setString(17, bean.getAaiconPension());
			st.setDouble(18, aaiTotal);
			st.setString(19, bean.getValAAIPF());
			st.setString(20, bean.getValAAIPension());
			st.setString(21, bean.getValAAITotal());
			st.setString(22, "Aims");
			insertVal = st.executeUpdate();
		} catch (SQLException e) {
			throw new InvalidDataException(e.getMessage());
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return insertVal;
	}

	public void addFinancialDetals(EmpMasterBean bean) {
		log.info(" FinacialDAO :addFinancialDetals() entering method()");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		String cpfacno = "", employeeNo = "", employeeName = "", emoluments = "", pensionnumber = "";
		String pf = "", vpf = "", principal = "", interest = "", AAIPf = "", AAIPension = "", AAITotal = "", empPf = "", empVpf = "", empTotal = "";
		cpfacno = bean.getCpfAcNo();
		employeeNo = bean.getEmpNumber();
		pensionnumber = bean.getPensionNumber();
		employeeName = bean.getEmpName();
		emoluments = bean.getEmoluments();
		AAIPf = bean.getAaiPf();
		AAIPension = bean.getAaiPension();
		AAITotal = bean.getAaiTotal();
		interest = bean.getInterest();
		principal = bean.getPrincipal();
		pf = bean.getEmployeePF();
		vpf = bean.getEmployeeVPF();
		// monthyear = bean.getDateofBirth();
		empPf = bean.getEmployeePF();
		empVpf = bean.getEmployeeVPF();
		empTotal = bean.getEmployeeTotal();

		String basic = bean.getBasic();
		String specialBasic = bean.getSpecialBasic();
		String dailyAllowance = bean.getDailyAllowance();
		String advDrawn = bean.getAdvDrawn();
		String partFinal = bean.getPartFinal();
		String monthYear = bean.getFromDate();

		String query = "insert into EMPLOYEE_PENSION_VALIDATE (PENSIONNO,CPFACCNO,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,"
				+ "EMPADVRECPRINCIPAL, EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION, AAITOTAL,employeeName,basic,DAILYALLOWANCE,specialbasic,"
				+ "ADVANCEDRWAN,partfinal,MONTHYEAR,EMPLOYEENO,region,airportcode) values"
				+ "('"
				+ pensionnumber
				+ "','"
				+ cpfacno
				+ "','"
				+ emoluments
				+ "','"
				+ empPf
				+ "','"
				+ empVpf
				+ "','"
				+ empTotal
				+ "','"
				+ principal
				+ "','"
				+ interest
				+ "','"
				+ AAIPf
				+ "','"
				+ AAIPension
				+ "','"
				+ AAITotal
				+ "','"
				+ employeeName
				+ "',"
				+ "'"
				+ basic
				+ "','"
				+ dailyAllowance
				+ "','"
				+ specialBasic
				+ "','"
				+ advDrawn
				+ "','"
				+ partFinal
				+ "','"
				+ monthYear
				+ "','"
				+ employeeNo
				+ "','" + bean.getRegion() + "','" + bean.getStation() + "')";
		try {
			con = commonDB.getConnection();
			log.info("query " + query);
			st = con.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, null);
		}
		log.info(" FinacialDAO :addFinancialDetals() leaving method()");
	}

	public ArrayList SearchFinanceAllMonths(FinacialDataBean dbBean, int start)
			throws Exception {

		log.info("FinanceDAO :SearchFinanceAllMonths() entering method");
		ArrayList hindiData = new ArrayList();
		Connection con = null;
		// empInfo=this.getEmployeeInfo(dbBean.getEmployeeName(),dbBean.
		// getCpfAccNo());
		log.info("Contextpath " + dbBean.getContextPath());

		log.info("From date" + dbBean.getFromDate() + "To Date"
				+ dbBean.getToDate());
		log.info("missing flag  is********** " + dbBean.getMissingFlag());
		log.info("emp Name" + dbBean.getEmployeeName());
		String region = dbBean.getRegion();
		String sqlQuery = this.buildQueryForFinanceDataSearch(dbBean);
		log.info("Query is(retriveByAll)" + sqlQuery);
		Statement st = null;
		ResultSet rs = null;
		String missingflag = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				dbBean = new FinacialDataBean();
				if (rs.getString(2) != null) {
					String icon = dbBean.getContextPath();
					icon += "PensionView/images/page_edit.png";
					dbBean.setIcon(icon);
					missingflag = "N";
					dbBean.setMissingFlag(missingflag);
					if (rs.getString(3) != null) {
						dbBean.setEmployeeName(rs.getString(3));
					}
				} else {
					missingflag = "Y";
					String icon = dbBean.getContextPath();
					icon += "PensionView/images/addIcon.gif";
					dbBean.setIcon(icon);
					dbBean.setMissingFlag(missingflag);
					dbBean.setEmployeeName("");
				}
				if (rs.getString(1) != null) {
					dbBean.setMonthYear(commonUtil.getDatetoString(rs
							.getDate(1), "yyyy-MM-dd"));
				} else {
					dbBean.setMonthYear("");
				}
				if (rs.getString(5) != null) {
					dbBean.setAirportCode((rs.getString(5)));
				} else {
					dbBean.setAirportCode("");
				}
				if (rs.getString(3) != null) {
					dbBean.setCpfAccNo((rs.getString(3)));
				} else {
					dbBean.setCpfAccNo("");
				}
				if (rs.getString(2) != null) {
					dbBean.setPensionNumber((rs.getString(2)));
				} else {
					dbBean.setPensionNumber("");
				}
				if (rs.getString(4) != null) {
					dbBean.setEmployeeName((rs.getString(4)));
				} else {
					dbBean.setEmployeeName("");
				}
				if (rs.getString(7) != null) {
					dbBean.setDesignation((rs.getString(7)));
				} else {
					dbBean.setDesignation("");
				}
				if (rs.getString(8) != null) {
					dbBean.setRegion((rs.getString(8)));
				} else {
					dbBean.setRegion("");
				}
				/*
				 * pensionString = this.getPensionNumber(con,
				 * dbBean.getCpfAccNo(), "", region); pensionArray =
				 * pensionString.split(","); if (pensionArray.length > 2) {
				 * 
				 * dateOfJoining = pensionArray[1]; if
				 * (!pensionArray[4].equals("nodata") &&
				 * !pensionArray[3].equals("")) {
				 * dbBean.setDesignation(pensionArray[3]); } else {
				 * dbBean.setDesignation(""); } log.info("pensionArray[5] " +
				 * pensionArray[5]); if (!pensionArray[4].equals("nodata") &&
				 * !pensionArray[4].equals("")) {
				 * dbBean.setEmployeeName(pensionArray[5]); } else {
				 * dbBean.setEmployeeName(""); }
				 * 
				 * if (!pensionArray[0].equals("")) {
				 * dbBean.setPensionNumber(pensionArray[0]); } else {
				 * dbBean.setPensionNumber(""); } }
				 */

				if (rs.getString(6) != null) {
					dbBean.setEmployeeNewNo((rs.getString(6)));
				} else {
					dbBean.setEmployeeNewNo("");
				}

				// dbBean.setRegion(region);

				// dbBean.setEmployeeNewNo(empInfo.getEmployeeNewNo());
				// dbBean.setDesignation(empInfo.getDesignation());
				hindiData.add(dbBean);
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
		log.info("FinanceDAO :SearchFinanceAllMonths() leaving method");
		return hindiData;
	}

	public String buildQueryForFinanceDataSearch(FinacialDataBean dbBean) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "";
		String sqlQuery1 = "select pensionno,employeename,cpfaccno,AIRPORTCODE,monthyear,employeeno,desegnation,region,to_char(MONTHYEAR,'MONYYYY') empmonyear from employee_pension_validate where empflag='Y'";

		if (!dbBean.getPensionNumber().equals("")) {
			whereClause.append(" LOWER(pensionno)='"
					+ dbBean.getPensionNumber().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if (!dbBean.getCpfAccNo().equals("")) {
			whereClause.append(" LOWER(cpfaccno)='"
					+ dbBean.getCpfAccNo().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if (!dbBean.getEmployeeNewNo().equals("")) {
			whereClause.append(" LOWER(employeeno)='"
					+ dbBean.getEmployeeNewNo().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if (!dbBean.getRegion().equals("")) {
			whereClause.append(" LOWER(region)='"
					+ dbBean.getRegion().trim().toLowerCase() + "'");
			whereClause.append(" AND ");

		}
		query.append(sqlQuery1);
		if ((dbBean.getPensionNumber().equals(""))
				&& (dbBean.getCpfAccNo().equals(""))
				&& (dbBean.getEmployeeNewNo().equals(""))
				&& (dbBean.getRegion().equals(""))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();

		if (dbBean.getMissingPF().equals("N")) {
			sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.pensionno,emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,emp.employeeno,emp.desegnation,emp.region from "
					+ "(select distinct to_char(to_date('"
					+ dbBean.getFromDate()
					+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' AND  rownum <= to_date('"
					+ dbBean.getToDate()
					+ "','dd-mon-yyyy')-to_date('"
					+ dbBean.getFromDate()
					+ "','dd-mon-yyyy')+1) empdt ,"
					+ "("
					+ dynamicQuery
					+ ") emp "
					+ "where empdt.monyear=emp.empmonyear (+)  order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";
		} else if (dbBean.getMissingPF().equals("Y")) {
			sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.pensionno,emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,emp.employeeno,emp.desegnation,emp.region from "
					+ "(select distinct to_char(to_date('"
					+ dbBean.getFromDate()
					+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y'AND rownum <= to_date('"
					+ dbBean.getToDate()
					+ "','dd-mon-yyyy')-to_date('"
					+ dbBean.getFromDate()
					+ "','dd-mon-yyyy')+1) empdt ,"
					+ "("
					+ dynamicQuery
					+ ") emp "
					+ "where empdt.monyear=emp.empmonyear  (+) and emp.pensionno is null  and emp.cpfaccno is null and emp.employeeno is null order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";

		}

		return sqlQuery;
	}

	public ArrayList SearchFinanceAll(FinacialDataBean dbBean, int start)
			throws Exception {
		log.info("FinanceDAO :SearchFinanceAll() entering method");
		ArrayList hindiData = new ArrayList();
		Connection con = null;
		log.info("start" + start + "end" + gridLength);
		log.info("missing flag  " + dbBean.getMissingFlag());
		String sqlQuery = this.buildQuery(dbBean, "");
		int countGridLength = 0;
		log.info("Query is(retriveByAll)" + sqlQuery);
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			hindiData = new ArrayList();
			while (rs.next()) {
				dbBean = new FinacialDataBean();
				countGridLength++;
				if (rs.getString("CODE") != null) {
					dbBean.setAirportCode(rs.getString("CODE"));
				} else {
					dbBean.setAirportCode("");
				}
				if (rs.getString("cpfacno") != null) {
					dbBean.setCpfAccNo(rs.getString("cpfacno"));
				} else {
					dbBean.setCpfAccNo("");
				}
				if (rs.getString("NAME") != null) {
					dbBean.setEmployeeName(rs.getString("NAME"));
				} else {
					dbBean.setEmployeeName("");
				}
				if (rs.getString("DESEG") != null) {
					dbBean.setDesignation(rs.getString("DESEG"));
				} else {
					dbBean.setDesignation("");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					dbBean.setEmployeeNewNo(rs.getString("EMPLOYEENO"));
				} else {
					dbBean.setEmployeeNewNo("");
				}
				if (rs.getString("FLAG") != null) {
					dbBean.setMissingFlag(rs.getString("FLAG"));
				} else {
					dbBean.setMissingFlag("");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					dbBean.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					dbBean.setEmoluments("");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					dbBean.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
				} else {
					dbBean.setEmpPfStatuary("");
				}
				if (rs.getString("EMPVPF") != null) {
					dbBean.setEmpVpf(rs.getString("EMPVPF"));
				} else {
					dbBean.setEmpVpf("");
				}
				if (rs.getString("EMPTOTAL") != null) {
					dbBean.setEmpTotal(rs.getString("EMPTOTAL"));
				} else {
					dbBean.setEmpTotal("");
				}
				if (rs.getString("monthyear") != null) {
					dbBean.setMonthYear(commonUtil.getDatetoString(rs
							.getDate("monthyear"), "dd/MMM/yyyy"));
				} else {
					dbBean.setMonthYear("");
				}
				if (rs.getString("dateofbirth") != null) {
					dbBean.setDateofBirth(commonUtil.getDatetoString(rs
							.getDate("dateofbirth"), "dd/MMM/yy"));
				} else {
					dbBean.setDateofBirth("");
				}
				if (rs.getString("pensionnumber") != null) {
					dbBean.setPensionNumber(rs.getString("pensionnumber"));
				} else {
					dbBean.setPensionNumber("");
				}

				log.info("FinanceDAO :SearchFinanceAll() Leaving method");
				hindiData.add(dbBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			pst.close();
			rs.close();
			con.close();
		}
		return hindiData;
	}

	public String buildQuery(FinacialDataBean bean, String flag) {
		log.info("FinanceDAO :buildQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String dynamicQuery = "", prefixWhereClause = "";
		log.info("missing flag  " + bean.getMissingFlag());
		if (flag != "") {
			sqlQuery = "select count(*) as totalcount from employee_pension_validate ev ,"
					+ "employee_info ei where ev.CPFACCNO=ei.CPFACNO AND ev.empflag='Y' AND ev.employeeName=ei.employeeName and ei.EMPFLAG='Y' ";

		} else {

			sqlQuery = "select ei.EMPLOYEENO AS EMPLOYEENO, ev.AIRPORTCODE AS CODE,ei.EMPLOYEENAME AS NAME,ei.CPFACNO as CPFACNO, ei.DESEGNATION AS DESEG,ei.dateofbirth AS dateofbirth,ei.pensionnumber as pensionnumber,"
					+ "ev.EMOLUMENTS AS EMOLUMENTS,ev.EMPPFSTATUARY AS EMPPFSTATUARY , ev.empvpf as EMPVPF, ev.EMPTOTAL AS EMPTOTAL,ev.monthyear,ev.MISSINGFLAG AS FLAG from employee_pension_validate ev,"
					+ "employee_info ei where ev.CPFACCNO=ei.CPFACNO AND ev.empflag='Y' AND ev.employeeName=ei.employeeName and ei.EMPFLAG='Y' ";
		}
		log.info("Employee" + bean.getEmployeeName() + "airport code"
				+ bean.getAirportCode());

		if (!bean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(ev.EMPLOYEENAME) like'%"
					+ bean.getEmployeeName().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!bean.getAirportCode().equals("")) {
			whereClause.append(" ev.AIRPORTCODE like'%"
					+ bean.getAirportCode().trim() + "%'");
			whereClause.append(" AND ");
		}
		if (!bean.getMonthYear().equals("")) {
			whereClause.append(" ev.monthyear ='" + bean.getMonthYear() + "'");
			whereClause.append(" AND ");
		}

		if (!bean.getCpfAccNo().equals("")) {
			whereClause.append(" LOWER(ev.CPFACCNO)='"
					+ bean.getCpfAccNo().trim() + "'");
			whereClause.append(" AND ");

		}
		/*
		 * if (!bean.getMissingFlag().equals("")) { whereClause.append("
		 * LOWER(ev.missingflag)='" + bean.getMissingFlag().trim() + "'");
		 * whereClause.append(" AND "); }
		 */
		if (!bean.getRegion().equals("")) {
			whereClause.append(" LOWER(ev.region)='"
					+ bean.getRegion().trim().toLowerCase() + "'");
			whereClause.append(" AND ");

		}

		query.append(sqlQuery);
		if ((bean.getEmployeeName().equals(""))
				&& (bean.getAirportCode().equals(""))
				&& (bean.getCpfAccNo().equals("") && (bean.getMonthYear()
						.equals("") && (bean.getRegion().equals(""))))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();

		log.info("Query " + dynamicQuery);
		log.info("FinanceDAO :buildQuery() leaving method");
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

	public int financialDataCountAll(String date, String stationCode) {
		log.info("PensionDAO :financialDataCountAll() entering method");
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		int count = 0;
		log.info("=========financialDataCountAll=======================" + date
				+ "stationCode" + stationCode);
		String query = "SELECT count(*) AS COUNT FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL,EMPLOYEE_INFO EMPINFO WHERE EMPINFO.CPFACNO=EMPPNSVAL.CPFACCNO AND EMPPNSVAL.empflag='Y' AND "
				+ "TO_char(MONTHYEAR, 'Mon-yy')='"
				+ date
				+ "' AND  EMPPNSVAL.AIRPORTCODE='" + stationCode + "'";
		log.info("query is " + query);
		try {

			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				count = rs.getInt("COUNT");
			}
		} catch (SQLException sql) {
			log.printStackTrace(sql);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PensionDAO :financialDataCountAll() leaving method");
		return count;
	}

	public EmployeeValidateInfo getFinanceEditDetails(String cpfaccno,
			String pensionno, String unitCD, String effectiveDate,
			String employeeNo, String designation, String employeeName,
			String region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		String query;
		EmployeeValidateInfo employeeValidate = new EmployeeValidateInfo();
		log.info("pensionno" + pensionno);
		log.info("cpfaccno(getPensionValidateDetails)" + cpfaccno
				+ "pensionno(getPensionValidateDetails)" + pensionno + "unitCD"
				+ unitCD);
		// here we modified unitcd with region
		if (pensionno != "") {
			query = "SELECT EMPPNSVAL.EMPLOYEENO AS EMPLOYEENO, CPFACCNO,EMPPNSVAL.CPFACCNO AS CPFACCNO,EMPPNSVAL.EMOLUMENTS AS EMOLUMENTS,EMPPNSVAL.EMPPFSTATUARY AS EMPPFSTATUARY,EMPPNSVAL.EMPVPF AS EMPVPF,"
					+ "EMPPNSVAL.BASIC AS BASIC,EMPPNSVAL.DAILYALLOWANCE AS DAILYALLOWANCE,EMPPNSVAL.SPECIALBASIC AS SPECIALBASIC,EMPPNSVAL.ADVANCEDRWAN AS ADVANCEDRWAN,"
					+ "EMPPNSVAL.PARTFINAL AS PARTFINAL,EMPPNSVAL.PFADVANCE AS PFADVANCE,EMPPNSVAL.CPFINTEREST AS CPFINTEREST,"
					+ "EMPPNSVAL.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,EMPPNSVAL.EMPADVRECINTEREST AS EMPADVRECINTEREST,EMPPNSVAL.EMPTOTAL AS EMPTOTAL,EMPPNSVAL.AAICONPF AS AAICONPF,EMPPNSVAL.AAICONPENSION AS AAICONPENSION,"
					+ "EMPPNSVAL.AAITOTAL  AS AAITOTAL,EMPPNSVAL.REGION AS REGION,EMPPNSVAL.REMARKS AS REMARKS FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL WHERE "
					+ "EMPPNSVAL.empflag='Y' AND  TO_char(EMPPNSVAL.MONTHYEAR, 'Mon-yy')='"
					+ effectiveDate
					+ "' AND EMPPNSVAL.pensionno='"
					+ pensionno
					+ "'";

		} else {
			query = "SELECT EMPPNSVAL.EMPLOYEENO AS EMPLOYEENO, CPFACCNO,EMPPNSVAL.CPFACCNO AS CPFACCNO,EMPPNSVAL.EMOLUMENTS AS EMOLUMENTS,EMPPNSVAL.EMPPFSTATUARY AS EMPPFSTATUARY,EMPPNSVAL.EMPVPF AS EMPVPF,"
					+ "EMPPNSVAL.BASIC AS BASIC,EMPPNSVAL.DAILYALLOWANCE AS DAILYALLOWANCE,EMPPNSVAL.SPECIALBASIC AS SPECIALBASIC,EMPPNSVAL.ADVANCEDRWAN AS ADVANCEDRWAN,"
					+ "EMPPNSVAL.PARTFINAL AS PARTFINAL,EMPPNSVAL.PFADVANCE AS PFADVANCE,EMPPNSVAL.CPFINTEREST AS CPFINTEREST,"
					+ "EMPPNSVAL.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,EMPPNSVAL.EMPADVRECINTEREST AS EMPADVRECINTEREST,EMPPNSVAL.EMPTOTAL AS EMPTOTAL,EMPPNSVAL.AAICONPF AS AAICONPF,EMPPNSVAL.AAICONPENSION AS AAICONPENSION,"
					+ "EMPPNSVAL.AAITOTAL  AS AAITOTAL,EMPPNSVAL.REGION AS REGION,EMPPNSVAL.REMARKS AS REMARKS FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL WHERE "
					+ "EMPPNSVAL.empflag='Y' AND EMPPNSVAL.region='"
					+ region
					+ "' AND  TO_char(EMPPNSVAL.MONTHYEAR, 'Mon-yy')='"
					+ effectiveDate
					+ "' AND EMPPNSVAL.CPFACCNO='"
					+ cpfaccno
					+ "'";

			log.info("query is " + query);
		}

		String pensionString = "";
		String pensionArray[] = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			while (rs.next()) {

				employeeValidate.setCpfaccno(cpfaccno);
				employeeValidate.setPensionNumber(pensionno.toString());
				pensionString = this.getPensionNumber(con, cpfaccno,
						employeeNo, rs.getString("REGION"));
				pensionArray = pensionString.split(",");
				if (pensionArray.length > 2) {

					if (!pensionArray[3].equals("nodata")
							&& !pensionArray[3].equals("")) {
						employeeValidate.setDesegnation(pensionArray[3]);
					} else {
						employeeValidate.setDesegnation("---");
					}
					if (!pensionArray[5].equals("nodata")
							&& !pensionArray[5].equals("")) {
						employeeValidate.setEmployeeName(pensionArray[5]);
					} else {
						employeeValidate.setEmployeeName("---");
					}

				}
				if (rs.getString("EMPLOYEENO") != null) {
					employeeValidate.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					employeeValidate.setEmployeeNo("---");
				}
				if (rs.getString("DAILYALLOWANCE") != null) {
					employeeValidate.setDailyAllowance(rs
							.getString("DAILYALLOWANCE"));
				} else {
					employeeValidate.setDailyAllowance("0.00");
				}
				if (rs.getString("BASIC") != null) {
					employeeValidate.setBasic(rs.getString("BASIC"));
				} else {
					employeeValidate.setBasic("0.00");
				}
				if (rs.getString("SPECIALBASIC") != null) {
					employeeValidate.setSpecialBasic(rs
							.getString("SPECIALBASIC"));
				} else {
					employeeValidate.setSpecialBasic("0.00");
				}
				if (rs.getString("ADVANCEDRWAN") != null) {
					employeeValidate.setAdvanceDrawn(rs
							.getString("ADVANCEDRWAN"));
				} else {
					employeeValidate.setAdvanceDrawn("0.00");
				}
				if (rs.getString("PARTFINAL") != null) {
					employeeValidate.setPartFinal(rs.getString("PARTFINAL"));
				} else {
					employeeValidate.setPartFinal("0.00");
				}
				if (rs.getString("PFADVANCE") != null) {
					employeeValidate.setPfAdvance(rs.getString("PFADVANCE"));
				} else {
					employeeValidate.setPfAdvance("0.00");
				}
				if (rs.getString("CPFINTEREST") != null) {
					employeeValidate
							.setCpfInterest(rs.getString("CPFINTEREST"));
				} else {
					employeeValidate.setCpfInterest("0.00");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					employeeValidate.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					employeeValidate.setEmoluments("0.00");
				}

				if (rs.getString("EMPPFSTATUARY") != null) {
					employeeValidate.setEmpPFStatuary(rs.getString(
							"EMPPFSTATUARY").trim());
				} else {
					employeeValidate.setEmpPFStatuary("0.00");
				}

				if (rs.getString("EMPVPF") != null) {
					employeeValidate.setEmpVPF(rs.getString("EMPVPF"));
				} else {
					employeeValidate.setEmpVPF("0.00");
				}

				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					employeeValidate.setEmpAdvRecPrincipal(rs
							.getString("EMPADVRECPRINCIPAL"));
				} else {
					employeeValidate.setEmpAdvRecPrincipal("0.00");
				}

				if (rs.getString("EMPADVRECINTEREST") != null) {
					employeeValidate.setEmpAdvRecInterest(rs
							.getString("EMPADVRECINTEREST"));
				} else {
					employeeValidate.setEmpAdvRecInterest("0.00");
				}

				if (rs.getString("EMPTOTAL") != null) {
					employeeValidate.setEmptotal(rs.getString("EMPTOTAL"));
				} else {
					employeeValidate.setEmptotal("0.00");
				}
				if (rs.getString("AAICONPF") != null) {
					employeeValidate.setAaiconPF(rs.getString("AAICONPF"));
				} else {
					employeeValidate.setAaiconPF("0.00");
				}

				if (rs.getString("AAICONPENSION") != null) {
					employeeValidate.setAaiconPension(rs
							.getString("AAICONPENSION"));
				} else {
					employeeValidate.setAaiconPension("");
				}
				if (rs.getString("remarks") != null
						&& rs.getString("remarks").equals(",")) {
					employeeValidate.setRemarks(rs.getString("remarks")
							.substring(0, rs.getString("remarks").length()));
				} else {
					employeeValidate.setRemarks("---");
				}

				double aaiTotal = 0;
				if (rs.getString("AAITOTAL") != null) {
					aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				} else {
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				}
				employeeValidate.setRegion(region);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return employeeValidate;
	}

	public int updateFinanceDetail(EmployeeValidateInfo updateValidateInfo,
			String airportCD, String effectiveDate, String pensionno,
			String cpfacno, String employeeName, String userName,
			String ipaddress, String region) {
		int count = 0;
		log.info("updateFinanceDetail===========================");
		String missingFlag = "", finalEmpTotal = "";
		String updateQuery = "";
		PreparedStatement pst = null;
		Connection con = null;
		/*
		 * Seghal Requested to add column emoluments,pfstatury in the front end
		 * only no need calculations
		 */
		/*
		 * String emoluments =
		 * String.valueOf(Float.parseFloat(updateValidateInfo .getBasic()) +
		 * Float.parseFloat(updateValidateInfo.getDailyAllowance()) +
		 * Float.parseFloat(updateValidateInfo.getSpecialBasic()));
		 */
		float pfAdvance1 = Math.abs(Float.parseFloat(updateValidateInfo
				.getPfAdvance())
				- Float.parseFloat(updateValidateInfo.getAdvanceDrawn())
				- Float.parseFloat(updateValidateInfo.getPartFinal()));
		float empTotal = Float
				.parseFloat(updateValidateInfo.getEmpPFStatuary())
				+ Float.parseFloat(updateValidateInfo.getEmpVPF())
				+ Float.parseFloat(updateValidateInfo.getCpfInterest())
				+ pfAdvance1;
		finalEmpTotal = String.valueOf(empTotal);
		/*
		 * Flag "Y" Should be edit the basic or dailallowanc values Flag "N" The
		 * values are available
		 */
		if (updateValidateInfo.getBasic().equals("0.0")
				|| updateValidateInfo.getDailyAllowance().equals("0.0")
				|| updateValidateInfo.getSpecialBasic().equals("0.0")) {
			missingFlag = "Y";
		} else {
			missingFlag = "N";
		}
		int updateHistory = 0;
		log.info("pensionno" + pensionno);

		if (!pensionno.equals("")) {
			updateQuery = "UPDATE  EMPLOYEE_PENSION_VALIDATE SET EMOLUMENTS=?,EMPPFSTATUARY=?,EMPVPF=?,EMPTOTAL=?,BASIC=?,DAILYALLOWANCE=?,SPECIALBASIC=?, PFADVANCE=?,ADVANCEDRWAN=?,PARTFINAL=?,CPFINTEREST=?,MISSINGFLAG=?,REMARKS=REMARKS || ',' || ? WHERE PENSIONNO=?  AND empflag='Y' AND TO_char(MONTHYEAR, 'Mon-yy')=? AND region=?";
			log
					.info("updateFinanceDetail==================updateQuery========="
							+ updateQuery);
		} else {
			updateQuery = "UPDATE  EMPLOYEE_PENSION_VALIDATE SET EMOLUMENTS=?,EMPPFSTATUARY=?,EMPVPF=?,EMPTOTAL=?,BASIC=?,DAILYALLOWANCE=?,SPECIALBASIC=?, PFADVANCE=?,ADVANCEDRWAN=?,PARTFINAL=?,CPFINTEREST=?,MISSINGFLAG=?,REMARKS=REMARKS || ? WHERE CPFACCNO=?  AND empflag='Y' AND TO_char(MONTHYEAR, 'Mon-yy')=? AND region=?";
			log
					.info("updateFinanceDetail==================updateQuery========="
							+ updateQuery);
		}
		try {
			con = commonDB.getConnection();
			updateHistory = this.addFinaceHistoryInfo(con, pensionno, cpfacno,
					effectiveDate, airportCD, userName, ipaddress);
			log.info("History Status" + updateHistory);
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, updateValidateInfo.getEmoluments().trim());
			log.info("emoluments" + updateValidateInfo.getEmoluments());
			pst.setString(2, updateValidateInfo.getEmpPFStatuary());
			pst.setString(3, updateValidateInfo.getEmpVPF());
			pst.setString(4, finalEmpTotal);
			pst.setString(5, updateValidateInfo.getBasic());
			pst.setString(6, updateValidateInfo.getDailyAllowance());
			pst.setString(7, updateValidateInfo.getSpecialBasic());
			pst.setString(8, updateValidateInfo.getPfAdvance());
			pst.setString(9, updateValidateInfo.getAdvanceDrawn());
			pst.setString(10, updateValidateInfo.getPartFinal());
			pst.setString(11, updateValidateInfo.getCpfInterest());
			pst.setString(12, missingFlag);
			pst.setString(13, updateValidateInfo.getRemarks());
			if (!pensionno.equals("")) {
				pst.setString(14, pensionno);
			} else {
				pst.setString(14, cpfacno);
			}
			pst.setString(15, effectiveDate);
			pst.setString(16, region);
			count = pst.executeUpdate();
			log.info("count" + count);
			log.info("pst" + pst);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, pst, null);
		}
		return count;
	}

	public int addFinaceHistoryInfo(Connection con, String pensionno,
			String cpfacno, String effectiveDate, String airportCode,
			String userName, String ipaddress) {
		Statement st = null;
		int i = 0;

		String selQuery = "SELECT PENSIONNO,CPFACCNO,AIRPORTCODE,MONTHYEAR,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,"
				+ "PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION ,AAITOTAL ,"
				+ "EMPLOYEENAME,MISSINGFLAG,EMPLOYEENO,DESEGNATION,GPF,REMARKS,REGION,CPFARREAR,CPF,FPF,ADVREC,ADVGIV,SHORTREC,VDA,"
				+ "ADDCPF,ADVANCE,PFCOUNT,ADDA,DAARREAR ,CPFADVANCE,DEDADV,DEDFEMP,DEDFNAA,REFADV,ARRCOUNT,OPTCPF,NONREFMEM,NONREFCOM,"
				+ "FDA ,DNSPAY ,SPAY,PFW ,PAYFORCPF ,PAYARREAR ,ACTIVEFLAG,MASTER_EMPNAME,EMPFLAG,'"
				+ userName
				+ "','"
				+ ipaddress
				+ "' FROM EMPLOYEE_PENSION_VALIDATE "
				+ "WHERE CPFACCNO='"
				+ cpfacno
				+ "' AND PENSIONNO='"
				+ pensionno
				+ "' AND TO_char(MONTHYEAR, 'Mon-yy')='"
				+ effectiveDate
				+ "' AND AIRPORTCODE='" + airportCode + "'";
		String insertSQL = "INSERT INTO EMPLOYEE_PENSION_TR_HISTORY (pensionno,CPFACCNO,AIRPORTCODE,MONTHYEAR,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,"
				+ "PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION ,AAITOTAL ,"
				+ "EMPLOYEENAME,MISSINGFLAG,EMPLOYEENO,DESEGNATION,GPF,REMARKS,REGION,CPFARREAR,CPF,FPF,ADVREC,ADVGIV,SHORTREC,VDA,"
				+ "ADDCPF,ADVANCE,PFCOUNT,ADDA,DAARREAR ,CPFADVANCE,DEDADV,DEDFEMP,DEDFNAA,REFADV,ARRCOUNT,OPTCPF,NONREFMEM,NONREFCOM,"
				+ "FDA ,DNSPAY ,SPAY,PFW ,PAYFORCPF ,PAYARREAR ,ACTIVEFLAG,MASTER_EMPNAME,EMPFLAG,USERNAME,COMPUTURENAME) "
				+ selQuery;

		try {
			log.info(selQuery);
			log.info(insertSQL);
			st = con.createStatement();
			i = st.executeUpdate(insertSQL);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public ArrayList getFinaanceDataAllReport(String fromDate, String toDate) {
		ArrayList dataList = new ArrayList();
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		EmployeeValidateInfo employeeValidate = new EmployeeValidateInfo();
		String query = "SELECT CPFACCNO,AIRPORTCODE,MONTHYEAR,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,EMPLOYEENAME,REGION FROM EMPLOYEE_PENSION_VALIDATE WHERE MONTHYEAR >='"
				+ fromDate
				+ "' AND MONTHYEAR <='"
				+ toDate
				+ "' AND empflag='Y' ORDER BY CPFACCNO";
		log.info("query=getFinaanceDataAllReport===" + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				employeeValidate = new EmployeeValidateInfo();
				if (rs.getString("MONTHYEAR") != null) {
					employeeValidate.setEffectiveDate(commonUtil
							.getDatetoString(rs.getDate("MONTHYEAR"),
									"yyyy-MM-dd"));
				} else {
					employeeValidate.setEffectiveDate("");
				}

				employeeValidate.setCpfaccno(rs.getString("CPFACCNO"));

				if (rs.getString("REGION") != null) {
					employeeValidate.setRegion(rs.getString("REGION"));
				} else {
					employeeValidate.setRegion("");
				}
				if (rs.getString("AIRPORTCODE") != null) {
					employeeValidate.setAirportCD(rs.getString("AIRPORTCODE"));
				} else {
					employeeValidate.setAirportCD("");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					employeeValidate.setEmployeeName(rs
							.getString("EMPLOYEENAME"));
				} else {
					employeeValidate.setEmployeeName("");
				}

				if (rs.getString("DAILYALLOWANCE") != null) {
					employeeValidate.setDailyAllowance(rs
							.getString("DAILYALLOWANCE"));
				} else {
					employeeValidate.setDailyAllowance("");
				}
				if (rs.getString("BASIC") != null) {
					employeeValidate.setBasic(rs.getString("BASIC"));
				} else {
					employeeValidate.setBasic("");
				}
				if (rs.getString("SPECIALBASIC") != null) {
					employeeValidate.setSpecialBasic(rs
							.getString("SPECIALBASIC"));
				} else {
					employeeValidate.setSpecialBasic("");
				}
				if (rs.getString("ADVANCEDRWAN") != null) {
					employeeValidate.setAdvanceDrawn(rs
							.getString("ADVANCEDRWAN"));
				} else {
					employeeValidate.setAdvanceDrawn("");
				}
				if (rs.getString("PARTFINAL") != null) {
					employeeValidate.setPartFinal(rs.getString("PARTFINAL"));
				} else {
					employeeValidate.setPartFinal("");
				}
				if (rs.getString("PFADVANCE") != null) {
					employeeValidate.setPfAdvance(rs.getString("PFADVANCE"));
				} else {
					employeeValidate.setPfAdvance("");
				}
				if (rs.getString("CPFINTEREST") != null) {
					employeeValidate
							.setCpfInterest(rs.getString("CPFINTEREST"));
				} else {
					employeeValidate.setCpfInterest("");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					employeeValidate.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					employeeValidate.setEmoluments("");
				}

				if (rs.getString("EMPPFSTATUARY") != null) {
					employeeValidate.setEmpPFStatuary(rs
							.getString("EMPPFSTATUARY"));
				} else {
					employeeValidate.setEmpPFStatuary("");
				}

				if (rs.getString("EMPVPF") != null) {
					employeeValidate.setEmpVPF(rs.getString("EMPVPF"));
				} else {
					employeeValidate.setEmpVPF("");
				}

				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					employeeValidate.setEmpAdvRecPrincipal(rs
							.getString("EMPADVRECPRINCIPAL"));
				} else {
					employeeValidate.setEmpAdvRecPrincipal("");
				}

				if (rs.getString("EMPADVRECINTEREST") != null) {
					employeeValidate.setEmpAdvRecInterest(rs
							.getString("EMPADVRECINTEREST"));
				} else {
					employeeValidate.setEmpAdvRecInterest("");
				}

				if (rs.getString("EMPTOTAL") != null) {
					employeeValidate.setEmptotal(rs.getString("EMPTOTAL"));
				} else {
					employeeValidate.setEmptotal("");
				}
				if (rs.getString("AAICONPF") != null) {
					employeeValidate.setAaiconPF(rs.getString("AAICONPF"));
				} else {
					employeeValidate.setAaiconPF("");
				}

				if (rs.getString("AAICONPENSION") != null) {
					employeeValidate.setAaiconPension(rs
							.getString("AAICONPENSION"));
				} else {
					employeeValidate.setAaiconPension("");
				}

				double aaiTotal = 0;
				if (rs.getString("AAITOTAL") != null) {
					aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				} else {
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				}
				dataList.add(employeeValidate);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return dataList;
	}

	public int totalDataForAllMonths(FinacialDataBean dbBean) throws Exception {
		log.info("FinanceDAO :totalData() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = this.buildQueryForFinanceDataSearch(dbBean);
		log.info("Query " + sqlQuery);
		int totalRecords = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery.trim());
			while (rs.next()) {
				totalRecords++;
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
		log.info("FinanceDAO :totalData() leaving method");
		return totalRecords;
	}

	public ArrayList financeReportByEachEmp(String cpfaccno, String employeeno) {
		String fromDate = "01/Mar/1995", toDate = "", sqlQuery = "";
		ArrayList financeReportList = new ArrayList();
		toDate = this.getCurrentDate("dd/MMM/yyyy");
		if (!cpfaccno.equals("") && employeeno.equals("")) {
			sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,"
					+ "emp.desegnation,emp.EMPLOYEENO,emp.BASIC,emp.DAILYALLOWANCE,emp.SPECIALBASIC,emp.EMOLUMENTS,emp.EMPPFSTATUARY,emp.EMPVPF,emp.ADVANCEDRWAN,emp.PARTFINAL,emp.PFADVANCE,emp.CPFINTEREST,emp.EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST,"
					+ "emp.EMPTOTAL,emp.AAICONPF,emp.AAICONPENSION,emp.AAITOTAL,emp.region  from "
					+ "(select distinct to_char(to_date('"
					+ fromDate
					+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' AND rownum <= to_date('"
					+ toDate
					+ "','dd-mon-yyyy')-to_date('"
					+ fromDate
					+ "','dd-mon-yyyy')+1) empdt ,"
					+ "(select cpfaccno,employeename,AIRPORTCODE,desegnation,EMPLOYEENO,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,"
					+ "EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,region,monthyear, to_char(MONTHYEAR,'MONYYYY') empmonyear from employee_pension_validate where empflag='Y' AND cpfaccno='"
					+ cpfaccno
					+ "') emp "
					+ "where empdt.monyear=emp.empmonyear (+)  order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";

		} else if (!employeeno.equals("") && cpfaccno.equals("")) {
			sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,"
					+ "emp.desegnation,emp.EMPLOYEENO,emp.BASIC,emp.DAILYALLOWANCE,emp.SPECIALBASIC,emp.EMOLUMENTS,emp.EMPPFSTATUARY,emp.EMPVPF,emp.ADVANCEDRWAN,emp.PARTFINAL,emp.PFADVANCE,emp.CPFINTEREST,emp.EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST,"
					+ "emp.EMPTOTAL,emp.AAICONPF,emp.AAICONPENSION,emp.AAITOTAL,emp.region  from "
					+ "(select distinct to_char(to_date('"
					+ fromDate
					+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' AND rownum <= to_date('"
					+ toDate
					+ "','dd-mon-yyyy')-to_date('"
					+ fromDate
					+ "','dd-mon-yyyy')+1) empdt ,"
					+ "(select cpfaccno,employeename,AIRPORTCODE,desegnation,EMPLOYEENO,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,"
					+ "EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,region,monthyear, to_char(MONTHYEAR,'MONYYYY') empmonyear from employee_pension_validate where empflag='Y' AND EMPLOYEENO='"
					+ employeeno
					+ "') emp "
					+ "where empdt.monyear=emp.empmonyear (+)  order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";
		} else {
			sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,"
					+ "emp.desegnation,emp.EMPLOYEENO,emp.BASIC,emp.DAILYALLOWANCE,emp.SPECIALBASIC,emp.EMOLUMENTS,emp.EMPPFSTATUARY,emp.EMPVPF,emp.ADVANCEDRWAN,emp.PARTFINAL,emp.PFADVANCE,emp.CPFINTEREST,emp.EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST,"
					+ "emp.EMPTOTAL,emp.AAICONPF,emp.AAICONPENSION,emp.AAITOTAL,emp.region  from "
					+ "(select distinct to_char(to_date('"
					+ fromDate
					+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' AND rownum <= to_date('"
					+ toDate
					+ "','dd-mon-yyyy')-to_date('"
					+ fromDate
					+ "','dd-mon-yyyy')+1) empdt ,"
					+ "(select cpfaccno,employeename,AIRPORTCODE,desegnation,EMPLOYEENO,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,"
					+ "EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,region,monthyear, to_char(MONTHYEAR,'MONYYYY') empmonyear from employee_pension_validate where  empflag='Y' AND cpfaccno='"
					+ cpfaccno
					+ "' and EMPLOYEENO='"
					+ employeeno
					+ "') emp "
					+ "where empdt.monyear=emp.empmonyear (+)  order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";
		}

		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		EmployeeValidateInfo employeeValidate = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			log.info("Query" + sqlQuery);
			while (rs.next()) {
				employeeValidate = new EmployeeValidateInfo();

				employeeValidate.setEffectiveDate(commonUtil.getDatetoString(rs
						.getDate(1), "yyyy-MM-dd"));
				if (rs.getString(2) != null) {
					employeeValidate.setCpfaccno(rs.getString(2));
				} else {
					employeeValidate.setCpfaccno("");
				}
				// log.info("CPF ACCNO financeReportByEachEmp" +
				// rs.getString(2));
				employeeValidate.setEmployeeName(rs.getString(3));
				employeeValidate.setAirportCD(rs.getString(4));
				employeeValidate.setDesegnation(rs.getString(5));
				if (rs.getString(6) != null) {
					employeeValidate.setEmployeeNo(rs.getString(6));
				} else {
					employeeValidate.setEmployeeNo("");
				}

				if (rs.getString(8) != null) {
					employeeValidate.setDailyAllowance(rs.getString(8));
				} else {
					employeeValidate.setDailyAllowance("0.0");
				}
				if (rs.getString(7) != null) {
					employeeValidate.setBasic(rs.getString(7));
				} else {
					employeeValidate.setBasic("0.0");
				}
				if (rs.getString(9) != null) {
					employeeValidate.setSpecialBasic(rs.getString(9));
				} else {
					employeeValidate.setSpecialBasic("0.0");
				}
				if (rs.getString(10) != null) {
					employeeValidate.setEmoluments(rs.getString(10));
				} else {
					employeeValidate.setEmoluments("0.0");
				}
				if (rs.getString(11) != null) {
					employeeValidate.setEmpPFStatuary(rs.getString(11));
				} else {
					employeeValidate.setEmpPFStatuary("0.0");
				}

				if (rs.getString(12) != null) {
					employeeValidate.setEmpVPF(rs.getString(12));
				} else {
					employeeValidate.setEmpVPF("0.0");
				}
				if (rs.getString(13) != null) {
					employeeValidate.setAdvanceDrawn(rs.getString(13));
				} else {
					employeeValidate.setAdvanceDrawn("0.0");
				}
				if (rs.getString(14) != null) {
					employeeValidate.setPartFinal(rs.getString(14));
				} else {
					employeeValidate.setPartFinal("0.0");
				}
				if (rs.getString(15) != null) {
					employeeValidate.setPfAdvance(rs.getString(15));
				} else {
					employeeValidate.setPfAdvance("0.0");
				}
				if (rs.getString(16) != null) {
					employeeValidate.setCpfInterest(rs.getString(16));
				} else {
					employeeValidate.setCpfInterest("0.0");
				}

				if (rs.getString(17) != null) {
					employeeValidate.setEmpAdvRecPrincipal(rs.getString(27));
				} else {
					employeeValidate.setEmpAdvRecPrincipal("0.0");
				}

				if (rs.getString(18) != null) {
					employeeValidate.setEmpAdvRecInterest(rs.getString(18));
				} else {
					employeeValidate.setEmpAdvRecInterest("0");
				}

				if (rs.getString(19) != null) {
					employeeValidate.setEmptotal(rs.getString(19));
				} else {
					employeeValidate.setEmptotal("0.0");
				}
				if (rs.getString(20) != null) {
					employeeValidate.setAaiconPF(rs.getString(20));
				} else {
					employeeValidate.setAaiconPF("0.0");
				}

				if (rs.getString(21) != null) {
					employeeValidate.setAaiconPension(rs.getString(21));
				} else {
					employeeValidate.setAaiconPension("0.0");
				}

				double aaiTotal = 0.0;
				if (rs.getString(22) != null) {
					aaiTotal = Double.parseDouble(rs.getString(22));
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				} else {
					employeeValidate.setAaiTotal(Double.toString(aaiTotal));
				}
				if (rs.getString(23) != null) {
					employeeValidate.setRegion(rs.getString(23));
				}
				financeReportList.add(employeeValidate);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return financeReportList;
	}

	public ArrayList financeValidateReport(String date, String stationCode) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Date dobDt = new Date();
		ArrayList financialList = new ArrayList();
		ArrayList validateList = new ArrayList();
		String dob = "", whetherOption = "", validateDataAAI = "";
		int emoluments = 0;
		double validePF = 0, validatePension = 0, validateTotal = 0, aaiPF = 0, aaiPension = 0, aaiTotal = 0, calEmoluments = 0, pfStatuary = 0;
		EmployeeValidateInfo employeValidate = null;
		log.info("=========financialDataAll=======================" + date
				+ "stationCode" + stationCode);
		String query = "SELECT EMPINFO.DATEOFBIRTH AS DOB,EMPINFO.DESEGNATION AS DESG,EMPINFO.EMPLOYEENAME AS EMPNAME,EMPINFO.EMPLOYEENO AS EMPNO,"
				+ "EMPINFO.WETHEROPTION AS WETHEROPTION, EMPPNSVAL.CPFACCNO AS CPFACCNO,EMPPNSVAL.EMOLUMENTS AS EMOLUMENTS,EMPPNSVAL.EMPPFSTATUARY AS EMPPFSTATUARY,EMPPNSVAL.EMPVPF AS EMPVPF,"
				+ "EMPPNSVAL.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL,EMPPNSVAL.EMPADVRECINTEREST AS EMPADVRECINTEREST,EMPPNSVAL.EMPTOTAL AS EMPTOTAL,EMPPNSVAL.AAICONPF AS AAICONPF,EMPPNSVAL.AAICONPENSION AS AAICONPENSION,"
				+ "EMPPNSVAL.AAITOTAL  AS AAITOTAL FROM EMPLOYEE_PENSION_VALIDATE EMPPNSVAL,EMPLOYEE_INFO EMPINFO WHERE  EMPPNSVAL.empflag='Y' AND EMPINFO.CPFACNO=EMPPNSVAL.CPFACCNO AND EMPINFO.EMPLOYEENAME=EMPPNSVAL.EMPLOYEENAME AND "
				+ "TO_char(MONTHYEAR, 'Mon-yy')='"
				+ date
				+ "' AND  EMPPNSVAL.AIRPORTCODE='"
				+ stationCode
				+ "' ORDER BY EMPINFO.EMPLOYEENAME";
		log.info("query is " + query);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			DecimalFormat df2 = new DecimalFormat("#########0.00");

			while (rs.next()) {
				employeValidate = new EmployeeValidateInfo();
				if (rs.getString("DOB") != null) {
					dobDt = rs.getDate("DOB");
					dob = dateFormat.format(dobDt);
					employeValidate.setDateOfBirth(dob);
				}
				employeValidate.setDesegnation(rs.getString("DESG"));
				if (rs.getString("EMPNAME") != null) {
					employeValidate.setEmployeeName(rs.getString("EMPNAME"));
				} else {
					employeValidate.setEmployeeName("");
				}

				employeValidate.setEmployeeNo(rs.getString("EMPNO"));
				log
						.info("=======financialDataAll==========whetherOption==empname"
								+ rs.getString("EMPNAME")
								+ "Whether Option"
								+ rs.getString("WETHEROPTION"));
				if (rs.getString("WETHEROPTION") != null) {
					whetherOption = rs.getString("WETHEROPTION");

					employeValidate.setWetherOption(whetherOption);
				}
				employeValidate.setCpfaccno(rs.getString("CPFACCNO"));
				if (rs.getString("EMOLUMENTS") != null) {
					employeValidate.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					employeValidate.setEmoluments("0");
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					pfStatuary = Double.parseDouble(rs
							.getString("EMPPFSTATUARY"));
					employeValidate.setEmpPFStatuary(Double
							.toString(pfStatuary));
				} else {
					employeValidate.setEmpPFStatuary(rs
							.getString("EMPPFSTATUARY"));
				}

				employeValidate.setEmpVPF(rs.getString("EMPVPF"));
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					employeValidate.setEmpAdvRecPrincipal(rs
							.getString("EMPADVRECPRINCIPAL"));
				} else {
					employeValidate.setEmpAdvRecPrincipal("0");
				}
				if (rs.getString("EMPADVRECINTEREST") != null) {
					employeValidate.setEmpAdvRecInterest(rs
							.getString("EMPADVRECINTEREST"));
				} else {
					employeValidate.setEmpAdvRecInterest("0");
				}

				employeValidate.setEmptotal(rs.getString("EMPTOTAL"));
				if (rs.getString("AAICONPF") != null) {
					aaiPF = Double.parseDouble(rs.getString("AAICONPF"));
					employeValidate.setAaiconPF(Double.toString(aaiPF));
				}
				if (rs.getString("AAICONPENSION") != null) {
					aaiPension = Double.parseDouble(rs
							.getString("AAICONPENSION"));
					employeValidate.setAaiconPension(Double
							.toString(aaiPension));
				}
				if (rs.getString("AAITOTAL") != null) {
					aaiTotal = Double.parseDouble(rs.getString("AAITOTAL"));
					employeValidate.setAaiTotal(Double.toString(aaiTotal));
				}

				/*
				 * Need to implemented one point 1)Total AAI's contribution [PF
				 * (column 13) + PENSION(column 14)] shall not be more than 12%
				 * of emoluments.
				 */
				log
						.info("==========financialDataAll================Employee Name"
								+ employeValidate.getEmployeeName());
				validateDataAAI = validatePFPension(dob, employeValidate
						.getEmoluments(), employeValidate.getWetherOption(),
						date);

				validateList = commonUtil.getTheList(validateDataAAI, ",");
				if (validateList.size() != 0) {

					validatePension = Double.parseDouble(validateList.get(0)
							.toString());
					validePF = Double.parseDouble(validateList.get(1)
							.toString());
					validateTotal = Double.parseDouble(validateList.get(2)
							.toString());
					employeValidate.setValAAIPension(Double
							.toString(validatePension));
					employeValidate.setValAAIPF(Double.toString(validePF));
					employeValidate.setValAAITotal(Double
							.toString(validateTotal));
				}
				log.info("aaiPF" + aaiPF + "validePF" + validePF);
				log.info("aaiPension" + aaiPension + "validatePension"
						+ validatePension);
				log.info("aaiTotal" + aaiTotal + "validateTotal"
						+ validateTotal);
				calEmoluments = new Double(df2.format((emoluments * 12) / 100f))
						.doubleValue();
				pfStatuary = new Double(df2.format(pfStatuary / 100f))
						.doubleValue();
				log.info("calEmoluments" + calEmoluments + "pfStatuary"
						+ pfStatuary);
				if (((aaiPF != validePF) || (aaiPension != validatePension) || (aaiTotal != validateTotal))
						&& (calEmoluments != pfStatuary)) {
					log.info("Checked Flag aaiPF" + aaiPF + "validePF"
							+ validePF);
					employeValidate.setValidateFlag(true);
				} else {
					log.info("false Checked Flag aaiPF" + aaiPF + "validePF"
							+ validePF);
					employeValidate.setValidateFlag(false);
				}
				employeValidate.setEffectiveDate(date);
				financialList.add(employeValidate);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return financialList;
	}

	public ArrayList getFinancalInfo(String frm_region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		EmployeePersonalInfo personalInfo = null;
		ArrayList list = new ArrayList();
		String query = "SELECT distinct(EMPLOYEENO) AS EMPLOYEENO,EMPLOYEENAME,DESEGNATION,REGION,CPFACCNO,AIRPORTCODE FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and  REGION='"
				+ frm_region + "' ";
		log.info("Query======" + query);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				personalInfo = new EmployeePersonalInfo();
				personalInfo.setAirportCode(rs.getString("AIRPORTCODE"));
				// log.info("Airport Code"+personalInfo.getAirportCode());
				personalInfo.setEmployeeName(rs.getString("EMPLOYEENAME"));
				personalInfo.setCpfAccno(rs.getString("CPFACCNO"));
				personalInfo.setRegion(rs.getString("REGION"));
				personalInfo.setDesignation(rs.getString("DESEGNATION"));
				personalInfo.setEmployeeNumber(rs.getString("EMPLOYEENO"));
				list.add(personalInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return list;
	}

	public ArrayList financeReportMissingMonthsByEachEmp(String cpfaccno,
			String employeeno, String region) {
		String fromDate = "01/Mar/1995", toDate = "", sqlQuery = "";
		boolean flag = false, dataFlag = false;
		ArrayList financeReportList = new ArrayList();
		toDate = this.getCurrentDate("dd/MMM/yyyy");
		sqlQuery = "select TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4)),emp.cpfaccno,emp.employeename,emp.AIRPORTCODE,"
				+ "emp.desegnation,emp.EMPLOYEENO,emp.BASIC,emp.DAILYALLOWANCE,emp.SPECIALBASIC,emp.EMOLUMENTS,emp.EMPPFSTATUARY,emp.EMPVPF,emp.ADVANCEDRWAN,emp.PARTFINAL,emp.PFADVANCE,emp.CPFINTEREST,emp.EMPADVRECPRINCIPAL,emp.EMPADVRECINTEREST,"
				+ "emp.EMPTOTAL,emp.AAICONPF,emp.AAICONPENSION,emp.AAITOTAL,emp.region  from "
				+ "(select distinct to_char(to_date('"
				+ fromDate
				+ "','dd-mon-yyyy') + rownum -1,'MONYYYY') monyear from employee_pension_validate where empflag='Y' and rownum <= to_date('"
				+ toDate
				+ "','dd-mon-yyyy')-to_date('"
				+ fromDate
				+ "','dd-mon-yyyy')+1) empdt ,"
				+ "(select cpfaccno,employeename,AIRPORTCODE,desegnation,EMPLOYEENO,BASIC,DAILYALLOWANCE,SPECIALBASIC,EMOLUMENTS,EMPPFSTATUARY,EMPVPF,ADVANCEDRWAN,PARTFINAL,PFADVANCE,CPFINTEREST,EMPADVRECPRINCIPAL,"
				+ "EMPADVRECINTEREST,EMPTOTAL,AAICONPF,AAICONPENSION,AAITOTAL,region,monthyear, to_char(MONTHYEAR,'MONYYYY') empmonyear from employee_pension_validate where empflag='Y' and cpfaccno='"
				+ cpfaccno
				+ "' and region='"
				+ region
				+ "') emp "
				+ "where empdt.monyear=emp.empmonyear (+)  order by TO_DATE('01-'||SUBSTR(empdt.MONYEAR,0,3)||'-'||SUBSTR(empdt.MONYEAR,4,4))";
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		EmployeeValidateInfo employeeValidate = null;
		ArrayList missingList = new ArrayList();
		String missing[] = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			log
					.info("Query==============financeReportMissingMonthsByEachEmp================="
							+ sqlQuery);
			StringBuffer sBuffer = new StringBuffer();
			while (rs.next()) {
				employeeValidate = new EmployeeValidateInfo();

				if (rs.getString(2) != null) {
					employeeValidate.setCpfaccno(rs.getString(2));
				} else {
					employeeValidate.setCpfaccno("");
				}
				if (rs.getString(3) != null) {
					employeeValidate.setEmployeeName(rs.getString(3));
				} else {
					employeeValidate.setEmployeeName("");
				}
				if (rs.getString(4) != null) {
					employeeValidate.setAirportCD(rs.getString(4));
				} else {
					employeeValidate.setAirportCD("");
				}
				if (rs.getString(5) != null) {
					employeeValidate.setDesegnation(rs.getString(5));
				} else {
					employeeValidate.setDesegnation("");
				}

				if (rs.getString(6) != null) {
					employeeValidate.setEmployeeNo(rs.getString(6));
				} else {
					employeeValidate.setEmployeeNo("");
				}
				// log.info("Month
				// year"+commonUtil.getDatetoString(rs.getDate(1),"MMM-yyyy")+"
				// Employee
				// Name"+employeeValidate.getEmployeeName()+"Employee
				// No"+employeeValidate.getEmployeeNo());
				if (employeeValidate.getCpfaccno().equals("")
						&& employeeValidate.getEmployeeName().equals("")
						&& employeeValidate.getAirportCD().equals("")) {

					sBuffer.append(commonUtil.getDatetoString(rs.getDate(1),
							"yyyy-MM-dd")
							+ ",");
					dataFlag = true;
					// log.info(sBuffer.toString());
					if (commonUtil.getDatetoString(rs.getDate(1), "MMM-yyyy")
							.equals(this.getCurrentDate("MMM-yyyy"))) {
						// log.info(""+this.getCurrentDate("MMM-yyyy"));
						missing = findOutString(sBuffer);
						if (missing.length != 0) {
							String heading = missing[0] + " To "
									+ missing[missing.length - 1];
							log.info("heading==1==" + heading);
							employeeValidate.setMissingInfo(heading);
							sBuffer.delete(0, sBuffer.length());
							flag = false;
						}
					}
				} else {

					if (sBuffer.length() == 0) {
						sBuffer.append("$$");
					} else {
						if (dataFlag == true) {
							sBuffer.append("$$");
							flag = true;
							dataFlag = false;
						}
						if (flag == true) {
							missing = findOutString(sBuffer);
							if (missing.length != 0) {
								String heading = missing[0] + " To "
										+ missing[missing.length - 1];
								// log.info("heading==2=="+heading);
								employeeValidate.setMissingInfo(heading);
								sBuffer.delete(0, sBuffer.length());
								flag = false;
							}
						}
					}
				}
				financeReportList.add(employeeValidate);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return financeReportList;
	}

	private String[] findOutString(StringBuffer buffer) {
		String tempInfo[] = null;
		StringTokenizer strTokens = new StringTokenizer(buffer.toString(), "$$");

		tempInfo = strTokens.nextToken().split(",");

		return tempInfo;
	}

	public ArrayList getFinancalInfo(String frm_region, String flag,
			int startIndex, int gridLength) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		EmployeePersonalInfo personalInfo = null;
		ArrayList list = new ArrayList();
		String query = "SELECT distinct(CPFACCNO) AS CPFACCNO FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and REGION='"
				+ frm_region + "'order by cpfaccno ";
		log.info("Query======" + query);
		int countGridLength = 0;
		try {
			con = commonDB.getConnection();
			st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.absolute(startIndex)) {
					countGridLength++;
					log.info("countGridLength  " + countGridLength);
					personalInfo = new EmployeePersonalInfo();
					personalInfo.setCpfAccno(rs.getString("CPFACCNO"));
					list.add(personalInfo);
				}
				while (rs.next() && countGridLength < gridLength) {
					countGridLength++;
					personalInfo = new EmployeePersonalInfo();
					personalInfo.setCpfAccno(rs.getString("CPFACCNO"));
					list.add(personalInfo);
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return list;
	}

	public int getFinancalInfoCount(String frm_region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		EmployeePersonalInfo personalInfo = null;
		ArrayList list = new ArrayList();
		String query = "SELECT distinct(CPFACCNO) AS CPFACCNO FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and  REGION='"
				+ frm_region + "'  ";
		log.info("Query======" + query);
		int countGridLength = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				countGridLength++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return countGridLength;
	}

	public ArrayList financeForm3Report(String airportCode, String date,
			String region, String retriedDate, String sortingOrder,
			String empName, String empNameFlag) {
		form3Bean formsetBean = null;
		ArrayList form3ReportList = new ArrayList();
		String appEmpNameQry = "";
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "", mastEmpName = "", cpfaccno = "", employeeno = "", airportcodeString = "";
		String pensionArray[] = null;
		double emouluments = 0.0;
		if (empNameFlag.equals("true")) {
			if (!empName.equals("")) {
				appEmpNameQry = " AND LOWER(EMPLOYEENAME) LIKE'"
						+ empName.toLowerCase().trim() + "%'";
			} else {
				appEmpNameQry = " AND EMPLOYEENAME IS NULL";
			}
		} else {
			appEmpNameQry = "";
		}

		if (sortingOrder.equalsIgnoreCase("employeename")) {
			// sortingOrder="LOWER("+sortingOrder+")";
			sortingOrder = "LOWER(MASTER_EMPNAME)";
		}
		String query = "";
		if (!airportCode.equals("NO-SELECT")) {
			airportcodeString = " and AIRPORTCODE='" + airportCode + "' ";
		}
		try {
			retriedDate = commonUtil.converDBToAppFormat(retriedDate,
					"MMMM-yyyy", "dd-MMM-yyyy");
			retriedDate = date.replaceAll("%", "01");

		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
		if (date.length() == 4) {
			query = "select  cpfaccno,EMOLUMENTS,employeeno,MASTER_EMPNAME from employee_pension_validate WHERE  empflag='Y' and  to_char(MONTHYEAR, 'yyyy')like '"
					+ date
					+ "' AND REGION='"
					+ region
					+ "' "
					+ airportcodeString + " order by " + sortingOrder;

		} else {
			query = "select  cpfaccno,EMOLUMENTS,employeeno,MASTER_EMPNAME from employee_pension_validate WHERE empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
					+ date
					+ "' AND REGION='"
					+ region
					+ "' "
					+ airportcodeString + " order by " + sortingOrder;

		}
		log.info("query============" + query);

		DecimalFormat df2 = new DecimalFormat("#########0");
		form3CommonBean memberInfo = new form3CommonBean();
		ArrayList memberList = new ArrayList();
		String cpflist = "";
		String[] cpfAccnoList = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (region.equals("CHQNAD") && airportCode.equals("CHQNAD")) {
				memberInfo = loadFirstBoardList("CHQNAD", date, retriedDate,
						form3ReportList);
				form3ReportList = memberInfo.getForm3MemberList();
				cpflist = memberInfo.getCpflist();
				cpfAccnoList = cpflist.split(",");
				for (int i = 0; i < cpfAccnoList.length; i++) {
					memberList.add(cpfAccnoList[i]);
				}
			}

			while (rs.next()) {
				if (rs.getString("CPFACCNO") != null) {
					cpfaccno = rs.getString("CPFACCNO");
				} else {
					cpfaccno = "";
				}
				if (rs.getString("EMPLOYEENO") != null) {
					employeeno = rs.getString("EMPLOYEENO");
				} else {
					employeeno = "";
				}
				if (!(region.equals("CHQNAD") && memberList.contains(cpfaccno))) {
					pensionString = this.getEmployeeInfoBasedCPFAccno(con,
							cpfaccno, employeeno, region, retriedDate,
							appEmpNameQry);

					pensionArray = pensionString.split(",");
					if (pensionArray.length > 2) {
						if (rs.getString("MASTER_EMPNAME") != null) {
							mastEmpName = rs.getString("MASTER_EMPNAME");
						} else {
							mastEmpName = "";
						}

						formsetBean = loadForm3Info(pensionArray, mastEmpName);
						formsetBean.setRegion(region);
						formsetBean.setAirportCode(airportCode);
						if (!mastEmpName.equals("")) {
							formsetBean.setEmployeeName(mastEmpName);
						} else {
							formsetBean.setEmployeeName("---");
						}
						if (rs.getString("EMOLUMENTS") != null) {
							emouluments = Double.parseDouble(rs
									.getString("EMOLUMENTS"));

							formsetBean.setEmoluments(df2.format(emouluments));
						} else {
							formsetBean.setEmoluments("0");
						}
						if (rs.getString("CPFACCNO") != null) {
							formsetBean.setCpfaccno(rs.getString("CPFACCNO"));
						} else {
							formsetBean.setCpfaccno("---");
						}

						if (rs.getString("EMPLOYEENO") != null) {
							formsetBean.setEmployeeNo(rs
									.getString("EMPLOYEENO"));
						} else {
							formsetBean.setEmployeeNo("---");
						}

						form3ReportList.add(formsetBean);
					}

				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		// return form3ReportDulicateList;
		return form3ReportList;
	}

	private String getPensionNumber(Connection con, String cpfaccno,
			String employeeno, String region) throws SQLException {
		String pensionNumber = "", sqlQuery = "";
		if (region.equals("CHQNAD") || region.equals("RAUSAP")) {
			sqlQuery = "SELECT PENSIONNUMBER,DATEOFJOINING,DATEOFBIRTH,DESEGNATION,FHNAME,EMPLOYEENAME,SEX,LASTACTIVE FROM EMPLOYEE_INFO WHERE CPFACNO='"
					+ cpfaccno
					+ "'AND REGION='"
					+ region
					+ "' AND EMPLOYEENO='" + employeeno + "'";
		} else {
			sqlQuery = "SELECT PENSIONNUMBER,DATEOFJOINING,DATEOFBIRTH, DESEGNATION,FHNAME,EMPLOYEENAME,SEX,LASTACTIVE FROM EMPLOYEE_INFO WHERE CPFACNO='"
					+ cpfaccno + "'AND REGION='" + region + "'";
		}
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				if (flag != true) {
					if (rs.getString("PENSIONNUMBER") != null) {
						pensionNumber = rs.getString("PENSIONNUMBER");
						flag = true;
					} else {
						pensionNumber = "";
					}
					if (rs.getString("DATEOFJOINING") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFJOINING"),
										"dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("DATEOFBIRTH") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFBIRTH"), "dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}

					if (rs.getString("DESEGNATION") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ StringUtility.replace(rs.getString(
										"DESEGNATION").toCharArray(), ","
										.toCharArray(), "-");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("FHNAME") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("FHNAME");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("EMPLOYEENAME") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("EMPLOYEENAME");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("SEX") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("SEX");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("LASTACTIVE") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("LASTACTIVE"), "dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
				}
			}
			log.info("pensionNumber========================" + pensionNumber);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
		}
		return pensionNumber;
	}

	public ArrayList getPensionAirportList(String region, String date) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		ArrayList airportList = new ArrayList();
		String query = "SELECT distinct AIRPORTCODE  FROM MV_EMPLOYEE_PENSION_AIRPORTS where region='"
				+ region + "'";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			String airportDesc = "";
			while (rs.next()) {
				if (rs.getString("AIRPORTCODE") != null) {
					airportDesc = rs.getString("AIRPORTCODE");
					airportList.add(airportDesc);

				}
			}
			log.info("airportList" + airportList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return airportList;
	}

	public ArrayList financeForm6AReport(String airportCode, String date,
			String region, String sortingOrder) {
		form3Bean formsetBean = null;
		ArrayList form6AReportList = new ArrayList();
		String query = "";
		String empSub = "";
		String pfadvrec = "";
		String retiredDate = StringUtility.replace(date.toCharArray(),
				"%".toCharArray(), "01").toString();
		/*
		 * if (region.equals("East Region")) { pfadvrec =
		 * "(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0))principle,nvl(pv.CPFINTEREST,0)
		 * interest
		 * ,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,
		 * 0)+nvl(pv.CPFINTEREST,0))total"; } else { pfadvrec =
		 * "nvl(EMPADVRECPRINCIPAL,0) principle,nvl(EMPADVRECINTEREST,0)
		 * interest,Decode(nvl(pv.ADVREC,0),0,Decode(nvl(CPFADVANCE,0),0,(nvl(
		 * EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0)),CPFADVANCE),ADVREC)
		 * total"; }
		 */
		pfadvrec = "Decode(nvl(EMPADVRECPRINCIPAL,0),0,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0)),EMPADVRECPRINCIPAL) principle,Decode(nvl(EMPADVRECINTEREST,0),0,nvl(pv.CPFINTEREST,0),EMPADVRECINTEREST) interest,Decode(nvl(pv.ADVREC,0),0,Decode(nvl(CPFADVANCE,0),0,Decode((nvl(EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0)),0,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0)+nvl(pv.CPFINTEREST,0)),(nvl(EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0))),CPFADVANCE),ADVREC) total";

		if (date.length() == 4) {
			query = "Select TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR,Decode(pv.AIRPORTCODE,'0','--',nvl(pv.AIRPORTCODE,'--'))AIRPORTCODE,"
					+ "trim(nvl(CPFACCNO,'--')),nvl(pv.EMPLOYEENO,'--') EMPLOYEENO,nvl(pv.EMPLOYEENAME,'--'),nvl(pv.DESEGNATION,'--'),EMOLUMENTS,"
					+ "nvl(pv.EMPPFSTATUARY,0),nvl(pv.EMPVPF,0),"
					+ pfadvrec
					+ ",nvl(pv.REMARKS,'--'),TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR  from "
					+ "employee_pension_validate pv where  pv.empflag='Y' and  to_char(MONTHYEAR, 'yyyy')like '"
					+ date
					+ "' AND pv.REGION='"
					+ region
					+ "' AND pv.AIRPORTCODE = '"
					+ airportCode
					+ "' order by "
					+ sortingOrder;
		} else {
			query = "Select Decode(pv.AIRPORTCODE,'0','--',nvl(pv.AIRPORTCODE,'--'))AIRPORTCODE,"
					+ "trim(nvl(CPFACCNO,'--')),nvl(pv.EMPLOYEENO,'--') EMPLOYEENO,nvl(pv.EMPLOYEENAME,'--'),nvl(pv.DESEGNATION,'--'),EMOLUMENTS,"
					+ "nvl(pv.EMPPFSTATUARY,0) AS EMPPFSTATUARY,nvl(pv.EMPVPF,0) AS EMPVPF,"
					+ pfadvrec
					+ ",nvl(pv.REMARKS,'--'),TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR from "
					+ "employee_pension_validate pv where  pv.empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
					+ date
					+ "' AND pv.REGION='"
					+ region
					+ "' AND pv.AIRPORTCODE = '"
					+ airportCode
					+ "' order by "
					+ sortingOrder;
		}

		log.info("query======== for form-6A ====" + query);
		Statement st = null;
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs = null;
		Connection con = null;
		FinancialReportDAO reportDAO = new FinancialReportDAO();
		String cpfaccno = "", employeeno = "", pensionString = "", dateOfJoining = "", whetherOption = "";
		String pensionArray[] = null;
		double pensionAsPerOption = 0.0;
		long transMntYear = 0, empRetriedDt = 0;
		long employeeOFYears = 0;
		int cellingRate = 0;
		boolean contrFlag = false, chkDOBFlag = false;
		String monthYear = "", days = "";
		int getDaysBymonth = 0;
		String dateOfRetriment = "";
		DecimalFormat df = new DecimalFormat("#########0.00");
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			st1 = con.createStatement();
			st2 = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				pensionAsPerOption = 0.0;
				cpfaccno = rs.getString(2);
				employeeno = rs.getString("EMPLOYEENO");
				pensionString = this.getEmployeeInfoBasedCPFAccno(con,
						cpfaccno, employeeno, region, retiredDate, "");
				pensionArray = pensionString.split(",");

				if (pensionArray.length > 2) {
					formsetBean = new form3Bean();
					if (rs.getString(2) != null) {
						formsetBean.setCpfaccno(cpfaccno);
					} else {
						formsetBean.setCpfaccno("---");
					}
					if (rs.getString("EMPLOYEENO") != null) {
						formsetBean.setEmployeeNo(employeeno);
					} else {
						formsetBean.setEmployeeNo("---");
					}
					dateOfJoining = pensionArray[1];
					if (!pensionArray[3].equals("nodata")
							&& !pensionArray[3].equals("")) {
						formsetBean.setDesignation(pensionArray[3]);
					} else {
						formsetBean.setDesignation("---");
					}
					if (!pensionArray[5].equals("nodata")
							&& !pensionArray[5].equals("")) {
						formsetBean.setEmployeeName(pensionArray[5]);
					} else {
						formsetBean.setEmployeeName("---");
					}

					if (!pensionArray[0].equals("")) {
						formsetBean.setPensionNumber(pensionArray[0]);
					} else {
						formsetBean.setPensionNumber("---");
					}
					if (!pensionArray[8].equals("nodata")) {
						formsetBean.setWetherOption(pensionArray[8]);
					} else {
						formsetBean.setWetherOption("---");
					}
					if (!pensionArray[2].equals("nodata")) {
						formsetBean.setDateOfBirth(pensionArray[2].trim());
					} else {
						formsetBean.setDateOfBirth("---");
					}
					formsetBean.setEmoluments(rs.getString("EMOLUMENTS"));
					// log.info("pfstatuatory " + rs.getDouble(7));
					formsetBean.setEmpSubStatutory(rs
							.getDouble("EMPPFSTATUARY"));
					formsetBean.setEmpSubOptional(rs.getDouble(8));
					formsetBean.setPrinciple(rs.getDouble(9));
					formsetBean.setInterest(rs.getDouble(10));
					formsetBean.setPfAdvRecTotal(rs.getDouble(11));
					CommonDAO commonDAO = new CommonDAO();
					if (!formsetBean.getDateOfBirth().equals("---")) {
						String personalPFID = commonDAO.getPFID(formsetBean
								.getEmployeeName(), pensionArray[2],
								formsetBean.getPensionNumber());
						formsetBean.setPfID(personalPFID);
					} else {
						formsetBean.setPfID(formsetBean.getPensionNumber());
					}
					try {
						if (!formsetBean.getDateOfBirth().trim().equals("---")) {
							dateOfRetriment = reportDAO
									.getRetriedDate(formsetBean
											.getDateOfBirth());
						}

					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (rs.getString("MONTHYEAR") != null) {
						monthYear = rs.getString("MONTHYEAR");
					} else {
						monthYear = "-NA-";
					}

					employeeOFYears = Long.parseLong(pensionArray[9]);
					log.info("monthYear" + monthYear + "dateOfRetriment"
							+ dateOfRetriment);
					if (!monthYear.equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(monthYear);
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
						pensionAsPerOption = reportDAO.pensionCalculation(
								monthYear, rs.getString("EMOLUMENTS"),
								formsetBean.getWetherOption(), region,"");
						if (chkDOBFlag == true) {
							String[] dobList = formsetBean.getDateOfBirth()
									.split("-");
							days = dobList[0];
							getDaysBymonth = reportDAO.getNoOfDays(formsetBean
									.getDateOfBirth());
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}
					// log.info(""+rs.getString("EMOLUMENTS")+"whetherOption"+
					// whetherOption+"monthYear"+monthYear+"pensionAsPerOption"+
					// pensionAsPerOption);
					formsetBean.setEmpContrPF(rs.getDouble("EMPPFSTATUARY")
							- pensionAsPerOption);
					formsetBean.setEmpContrPension(pensionAsPerOption);
					formsetBean.setEmpContrTotal(rs.getDouble("EMPPFSTATUARY"));
					log.info("Emp Name" + formsetBean.getEmployeeName()
							+ "Total" + formsetBean.getEmpContrTotal());
					formsetBean.setRemarks(rs.getString(12));
					formsetBean.setAirportCode(rs.getString("AIRPORTCODE"));
					contrFlag = false;
					chkDOBFlag = false;
					form6AReportList.add(formsetBean);
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form6AReportList;
	}

	public ArrayList financeForm6BReport(String airportCode, String date,
			String region, String sortingOrder) {

		ArrayList form6BReportList = new ArrayList();
		form6BReportList = financeForm6AReport(airportCode, date, region,
				sortingOrder);

		return form6BReportList;
	}

	public int[] financeForm6BCountData(String airportCode, String date,
			String region, String prevDate) {
		Statement st = null;
		Statement st1 = null;

		ResultSet rs = null;
		Connection con = null;
		int form4count = 0, form5count = 0, lastMonthCount = 0;
		int dataAry[] = new int[3];
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			st1 = con.createStatement();
			String query = "select count(*) from ((select CPFACCNO,employeeno,AIRPORTCODE from employee_pension_validate where  empflag='Y' and  trim(REGION) = '"
					+ region.trim()
					+ "'  and to_char(MONTHYEAR,'DD-Mon-YYYY') like '"
					+ date
					+ "'  group by CPFACCNO,employeeno,AIRPORTCODE) minus (select CPFACCNO,employeeno,AIRPORTCODE from employee_pension_validate where  empflag='Y' and trim(REGION) = '"
					+ region.trim()
					+ "' and  to_char(MONTHYEAR,'DD-Mon-YYYY') like '"
					+ prevDate
					+ "' group by CPFACCNO,employeeno,AIRPORTCODE ))";
			log.info("query is ====11111======" + query);
			rs = st.executeQuery(query);
			if (rs.next())
				form4count = rs.getInt(1);
			query = "select count(*) from ((select CPFACCNO,employeeno,AIRPORTCODE from employee_pension_validate where empflag='Y' and trim(REGION) = '"
					+ region.trim()
					+ "'  and to_char(MONTHYEAR,'DD-Mon-YYYY') like '"
					+ prevDate
					+ "'  group by CPFACCNO,employeeno,AIRPORTCODE) minus ( select CPFACCNO,employeeno,AIRPORTCODE from employee_pension_validate where empflag='Y' and trim(REGION) = '"
					+ region.trim()
					+ "' and  to_char(MONTHYEAR,'DD-Mon-YYYY') like '"
					+ date
					+ "' group by CPFACCNO,employeeno,AIRPORTCODE))";
			log.info("query is ====2222222======" + query);
			rs = st.executeQuery(query);
			if (rs.next())
				form5count = rs.getInt(1);
			query = "select count(*) from (select CPFACCNO,employeeno,AIRPORTCODE from employee_pension_validate where empflag='Y' and trim(REGION) = '"
					+ region.trim()
					+ "'  and to_char(MONTHYEAR,'DD-Mon-YYYY') like '"
					+ prevDate + "' group by CPFACCNO,employeeno,AIRPORTCODE)";
			log.info("query is ====333333======" + query);
			rs = st.executeQuery(query);
			if (rs.next())
				lastMonthCount = rs.getInt(1);

			dataAry[0] = form4count;
			dataAry[1] = form5count;
			dataAry[2] = lastMonthCount;
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info(dataAry[0] + "==========" + dataAry[1]);
		return dataAry;
	}

	public ArrayList financeForm6Report(String airportCode, String date,
			String region, String sortingOrder) {

		form3Bean formsetBean = null;
		form3Bean formFinalsetBean = null;
		ArrayList form6ReportList = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int cnt = 0;
		double drawnSal = 0.0, empContrPension = 0.0;
		ArrayList alist = alist = new ArrayList();

		// totalCnt = totalCnt + cnt;

		// log.info("form6ReportList size ==
		// "+form6ReportList.size());
		// formsetBean = (form3Bean)form6ReportList.get(0);
		log.info("========cnt is ============" + cnt);
		String sqlQuery = "Select REMITTED_AMOUNT, to_char(REMITTED_DATE,'dd/Mon/YYYY')REMITTED_DATE, BANKNAME,BANKADDRESS from REMITTANCE_INFO where AIRPORT='"
				+ airportCode
				+ "' and to_char(monthyear,'dd-Mon-YYYY') like '"
				+ date + "' ";
		log.info("sqlQuery is ======form6====== " + sqlQuery);

		try {
			con = commonDB.getConnection();
			form6ReportList = financeForm6AReport(con, airportCode, date,
					region, sortingOrder);
			for (int k = 0; k < form6ReportList.size(); k++) {
				formsetBean = (form3Bean) form6ReportList.get(k);
				/* if (!alist.contains(formsetBean.getCpfaccno())) { */
				cnt++;
				alist.add(formsetBean.getCpfaccno());
				drawnSal = drawnSal
						+ Double.parseDouble(formsetBean.getEmoluments());
				empContrPension = empContrPension
						+ formsetBean.getEmpContrPension();
				/* } */
				// form6ReportList.remove(k);
			}
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			formFinalsetBean = new form3Bean();
			formFinalsetBean.setSubScribersCount(cnt);
			formFinalsetBean.setEmoluments(drawnSal + "");
			formFinalsetBean.setEmpContrPension(empContrPension);
			if (rs.next()) {
				formFinalsetBean
						.setRemittedAmt(rs.getDouble("REMITTED_AMOUNT"));
				formFinalsetBean.setRemittedDate(rs.getString("REMITTED_DATE"));
				formFinalsetBean.setBankName(rs.getString("BANKNAME"));
				formFinalsetBean.setBankAddr(rs.getString("BANKADDRESS"));
			}
			form6ReportList = new ArrayList();
			form6ReportList.add(formFinalsetBean);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, null);
		}

		return form6ReportList;
	}

	public int addRemittanceInfo(form3Bean formbean) {
		Connection con = null;
		Statement st = null;
		int i = 0;
		String displayDate = "";

		try {
			displayDate = commonUtil.converDBToAppFormat(formbean
					.getRemittedDate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			log.info("displayDate-----" + displayDate);
		} catch (InvalidDataException e1) {
			e1.printStackTrace();
		}
		String query = "INSERT INTO REMITTANCE_INFO (AIRPORT,REGION,MONTHYEAR,REMITTED_AMOUNT,REMITTED_DATE,BANKNAME,BANKADDRESS)";
		query += " VALUES ('" + formbean.getAirportCode() + "','"
				+ formbean.getRegion() + "','01-" + formbean.getMonthYear()
				+ "'," + formbean.getRemittedAmt() + ",'" + displayDate + "','"
				+ formbean.getBankName() + "','" + formbean.getBankAddr()
				+ "')";
		log.info("Remittance Query is ===== " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			i = st.executeUpdate(query);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, null);
		}
		return i;
	}

	/*
	 * public ArrayList financeForm45Report(String airportCode, String date,
	 * String prevDate, String region) { form3Bean formsetBean = null; ArrayList
	 * form45ReportList = new ArrayList(); String query = "";
	 * 
	 * query = "select CPFACCNO,EMPLOYEENO,AIRPORTCODE from
	 * employee_pension_validate where trim(REGION) = '" + region.trim() + "'
	 * and airportcode='" + airportCode + "' and
	 * to_char(MONTHYEAR,'DD/Mon/YYYY') like '%" + date + "' minus select
	 * CPFACCNO,EMPLOYEENO,AIRPORTCODE from employee_pension_validate where
	 * trim(REGION) = '" + region.trim() + "' and airportcode='" + airportCode +
	 * "' and to_char(MONTHYEAR,'DD/Mon/YYYY') like '%" + prevDate + "'";
	 * 
	 * log.info("query============" + query); Statement st = null; ResultSet rs
	 * = null; Connection con = null; String pensionString = "", dateOfJoining =
	 * ""; String pensionArray[] = null; try { con = commonDB.getConnection();
	 * st = con.createStatement(); rs = st.executeQuery(query); while
	 * (rs.next()) { formsetBean = new form3Bean();
	 * 
	 * if (rs.getString("CPFACCNO") != null) {
	 * formsetBean.setCpfaccno(rs.getString("CPFACCNO")); } else {
	 * formsetBean.setCpfaccno("---"); } if (rs.getString("EMPLOYEENO") != null)
	 * { formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO")); } else {
	 * formsetBean.setEmployeeNo("---"); }
	 * 
	 * pensionString = this.getPensionNumber(con, formsetBean .getCpfaccno(),
	 * formsetBean.getEmployeeNo(), region); pensionArray =
	 * pensionString.split(","); if (pensionArray.length > 2) { if
	 * (!pensionArray[2].equals("nodata") && !pensionArray[2].equals("")) {
	 * formsetBean.setDateOfBirth(pensionArray[2].trim()); } else {
	 * formsetBean.setDateOfBirth("---"); } dateOfJoining = pensionArray[1]; if
	 * (!pensionArray[3].equals("nodata") && !pensionArray[3].equals("")) {
	 * formsetBean.setDesignation(pensionArray[3]); } else {
	 * formsetBean.setDesignation("---"); } if
	 * (!pensionArray[4].equals("nodata") && !pensionArray[3].equals("")) {
	 * formsetBean.setFamilyMemberName(pensionArray[4]); } else {
	 * formsetBean.setFamilyMemberName("---"); } if
	 * (!pensionArray[5].equals("nodata") && !pensionArray[5].equals("")) {
	 * formsetBean.setEmployeeName(pensionArray[5]); } else {
	 * formsetBean.setEmployeeName("---"); } if
	 * (!pensionArray[6].equals("nodata") && !pensionArray[6].equals("")) {
	 * formsetBean.setSex(pensionArray[6]); } else { formsetBean.setSex("---");
	 * } if (!pensionArray[7].equals("nodata") && !pensionArray[7].equals("")) {
	 * formsetBean.setDateOfLeaving(pensionArray[7]); } else {
	 * formsetBean.setDateOfLeaving("---"); } if
	 * (dateOfJoining.equals("nodata")) { dateOfJoining = ""; } if
	 * (!pensionArray[0].equals("")) {
	 * formsetBean.setPensionNumber(pensionArray[0]); } else {
	 * formsetBean.setPensionNumber("---"); }
	 * 
	 * if (!dateOfJoining.equals("")) { if
	 * (commonUtil.getDateDifference("01-Apr-1995", dateOfJoining) < 1) {
	 * formsetBean.setDateOfEntitle("01-Apr-1995"); } else {
	 * formsetBean.setDateOfEntitle(dateOfJoining); } } else {
	 * formsetBean.setDateOfEntitle("---"); } } formsetBean.setRemarks("---");
	 * form45ReportList.add(formsetBean); } } catch (SQLException e) {
	 * log.printStackTrace(e); } catch (Exception e) { log.printStackTrace(e); }
	 * finally { commonDB.closeConnection(con, st, rs); } return
	 * form45ReportList; }
	 */

	public ArrayList getPensionValidateData(String region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		ArrayList cpfList = new ArrayList();

		CompartiveReportForm3DataBean compartiveReportBean = null;

		String query = this.buildCompartiveForm3Query(region);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				compartiveReportBean = new CompartiveReportForm3DataBean();
				compartiveReportBean.setForm3CPFaccno(rs.getString("CPFACCNO"));
				compartiveReportBean.setForm3AirportCode(rs
						.getString("AIRPORTCODE"));
				compartiveReportBean.setForm3EmpNo(rs.getString("EMPLOYEENO"));
				compartiveReportBean.setForm3EmpName(rs
						.getString("EMPLOYEENAME"));
				compartiveReportBean.setForm3Designation(rs
						.getString("DESEGNATION"));
				cpfList.add(compartiveReportBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return cpfList;
	}

	public ArrayList getAAIPensionData(String region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		ArrayList cpfList = new ArrayList();
		CompartiveReportAaiDataBean compartiveReportBean = null;

		String query = this.buildCompartiveAaiQuery(region);
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				compartiveReportBean = new CompartiveReportAaiDataBean();

				compartiveReportBean.setAaiCPFaccno(rs.getString("CPFACNO"));
				compartiveReportBean.setAaiAirportCode(rs
						.getString("AIRPORTCODE"));
				compartiveReportBean.setAaiEmpNo(rs.getString("EMPLOYEENO"));
				compartiveReportBean
						.setAaiEmpName(rs.getString("EMPLOYEENAME"));
				compartiveReportBean.setAai3Designation(rs
						.getString("DESEGNATION"));
				cpfList.add(compartiveReportBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return cpfList;
	}

	public String buildCompartiveForm3Query(String region) {
		log.info("FinanceDAO :buildCompartiveQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String sqlQuery = "";
		String dynamicQuery = "";
		String reg = "", airportcode = "";

		if (region != "") {
			reg = region;

			if ((reg.substring(0, 3)).equals("IAD")) {
				region = "CHQ" + reg.substring(0, 3);
				airportcode = reg.substring(4, reg.length());

			} else
				region = reg;

		}

		sqlQuery = "select nvl(CPFACCNO,'-') CPFACCNO,nvl(AIRPORTCODE,'-') AIRPORTCODE,nvl(EMPLOYEENO,'-') EMPLOYEENO,nvl(MASTER_EMPNAME,'-') EMPLOYEENAME,nvl(DESEGNATION,'-') DESEGNATION from employee_pension_validate where  empflag='Y' and to_char(monthyear,'dd-Mon-yyyy') like '%-Aug-2007'";

		if (!region.equals("")) {
			whereClause.append(" region='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("")) {
			whereClause.append(" airportcode='" + airportcode + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "ORDER BY CPFACCNO";

		query.append(orderBy);

		dynamicQuery = query.toString();

		log.info("Query " + dynamicQuery);
		log.info("FinanceDAO :buildCompartiveQuery() leaving method");
		return dynamicQuery;
	}

	public String buildCompartiveAaiQuery(String region) {
		log.info("FinanceDAO :buildCompartiveQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";

		String reg = "", airportcode = "";

		if (region != "") {
			reg = region;

			if ((reg.substring(0, 3)).equals("IAD")) {
				region = reg.substring(0, 3);
				airportcode = reg.substring(4, reg.length());

			} else
				region = reg;
		}

		sqlQuery = "select nvl(CPFACNO,'-') CPFACNO,nvl(AIRPORTCODE,'-') AIRPORTCODE,nvl(EMPLOYEENO,'-') EMPLOYEENO,nvl(EMPLOYEENAME,'-') EMPLOYEENAME,nvl(DESEGNATION,'-') DESEGNATION from employee_westinfo";
		if (!region.equals("")) {
			whereClause.append(" region='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("")) {
			whereClause.append(" airportcode='" + airportcode + "'");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))) {
			log.info("inside if");

		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" WHERE ");
			}
			query.append(this.sTokenFormat(whereClause));

		}

		String orderBy = "ORDER BY CPFACNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("Query " + dynamicQuery);
		log.info("FinanceDAO :buildCompartiveQuery() leaving method");
		return dynamicQuery;
	}

	/*
	 * financeForm4ReportByDJ:Based By Date Of joining for selected month
	 */
	public ArrayList financeForm4ReportByDJ(String airportCode, String date,
			String prevDate, String region) {
		form3Bean formsetBean = null;
		ArrayList form45ReportList = new ArrayList();
		String query = "";
		log.info("date======financeForm4ReportByDJ=======" + date);
		query = "select CPFACNO,EMPLOYEENO,EMPLOYEENAME,PENSIONNUMBER,AIRPORTCODE,DATEOFBIRTH,DATEOFJOINING,DESEGNATION,SEX,FHNAME from employee_info where trim(REGION) = '"
				+ region.trim()
				+ "' and AIRPORTCODE='"
				+ airportCode
				+ "'  and to_char(DATEOFJOINING,'DD/Mon/YYYY') like '%"
				+ date
				+ "'";

		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String dateOfJoining = "";
		String pensionArray[] = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				formsetBean = new form3Bean();

				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					formsetBean.setCpfaccno("---");
				}
				if (rs.getString("PENSIONNUMBER") != null) {
					formsetBean.setPensionNumber(rs.getString("PENSIONNUMBER"));
				} else {
					formsetBean.setPensionNumber("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					formsetBean.setEmployeeNo("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					dateOfJoining = commonUtil.converDBToAppFormat(commonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"dd/MMM/yyyy"), "dd/MMM/yyyy",
							"dd-MMM-yyyy");
				} else {
					dateOfJoining = "";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					formsetBean.setDateOfBirth(commonUtil.converDBToAppFormat(
							commonUtil.getDatetoString(rs
									.getDate("DATEOFBIRTH"), "dd/MMM/yyyy"),
							"dd/MMM/yyyy", "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					formsetBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					formsetBean.setDesignation("---");
				}

				if (rs.getString("FHNAME") != null) {
					formsetBean.setFamilyMemberName(rs.getString("FHNAME"));
				} else {
					formsetBean.setFamilyMemberName("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					formsetBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					formsetBean.setEmployeeName("---");
				}

				if (rs.getString("SEX") != null) {
					formsetBean.setSex(rs.getString("SEX"));
				} else {
					formsetBean.setSex("---");
				}
				if (!dateOfJoining.equals("")) {
					if (commonUtil.getDateDifference("01-Apr-1995",
							dateOfJoining) < 1) {
						formsetBean.setDateOfEntitle("01-Apr-1995");
					} else {
						formsetBean.setDateOfEntitle(dateOfJoining);
					}
				} else {
					formsetBean.setDateOfEntitle("---");
				}

				formsetBean.setRemarks("---");
				form45ReportList.add(formsetBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form45ReportList;
	}

	public form3Bean financeForm4ReportByDJ(Connection con, String airportCode,
			String date, String prevDate, String region, String cpfaccno,
			String employeeno) {
		form3Bean formsetBean = null;
		ArrayList form45ReportList = new ArrayList();
		String query = "";
		log.info("date======financeForm4ReportByDJ=======" + date);
		query = "select CPFACNO,EMPLOYEENO,EMPLOYEENAME,PENSIONNUMBER,AIRPORTCODE,DATEOFBIRTH,DATEOFJOINING,DESEGNATION,SEX,FHNAME from employee_info where trim(REGION) = '"
				+ region.trim()
				+ "' and cpfacno='"
				+ cpfaccno.trim()
				+ "'  and to_char(DATEOFJOINING,'DD/Mon/YYYY') like '%"
				+ date
				+ "'";

		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;

		String dateOfJoining = "";
		String pensionArray[] = null;
		try {

			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				formsetBean = new form3Bean();

				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					formsetBean.setCpfaccno("---");
				}
				if (rs.getString("PENSIONNUMBER") != null) {
					formsetBean.setPensionNumber(rs.getString("PENSIONNUMBER"));
				} else {
					formsetBean.setPensionNumber("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					formsetBean.setEmployeeNo("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					dateOfJoining = commonUtil.converDBToAppFormat(commonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"dd/MMM/yyyy"), "dd/MMM/yyyy",
							"dd-MMM-yyyy");
				} else {
					dateOfJoining = "";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					formsetBean.setDateOfBirth(commonUtil.converDBToAppFormat(
							commonUtil.getDatetoString(rs
									.getDate("DATEOFBIRTH"), "dd/MMM/yyyy"),
							"dd/MMM/yyyy", "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					formsetBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					formsetBean.setDesignation("---");
				}

				if (rs.getString("FHNAME") != null) {
					formsetBean.setFamilyMemberName(rs.getString("FHNAME"));
				} else {
					formsetBean.setFamilyMemberName("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					formsetBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					formsetBean.setEmployeeName("---");
				}

				if (rs.getString("SEX") != null) {
					formsetBean.setSex(rs.getString("SEX"));
				} else {
					formsetBean.setSex("---");
				}
				if (!dateOfJoining.equals("")) {
					if (commonUtil.getDateDifference("01-Apr-1995",
							dateOfJoining) < 1) {
						formsetBean.setDateOfEntitle("01-Apr-1995");
					} else {
						formsetBean.setDateOfEntitle(dateOfJoining);
					}
				} else {
					formsetBean.setDateOfEntitle("---");
				}

				formsetBean.setRemarks("---");
				form45ReportList.add(formsetBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return formsetBean;
	}

	/*
	 * financeForm5ReportByDJ:Based By Date Of joining for selected month
	 */
	public form3Bean financeForm5ReportByDJ(Connection con, String airportCode,
			String date, String prevDate, String region, String cpfacno,
			String employeeno, boolean selectedDateFlag) {
		form3Bean formsetBean = new form3Bean();
		ArrayList form45ReportList = new ArrayList();
		String query = "", retriredPrevMonth = "", dobMnthString = "", seperDtString = "";
		try {
			retriredPrevMonth = commonUtil.converDBToAppFormat(prevDate,
					"/MMM/yyyy", "MMM");
			retriredPrevMonth = "%" + retriredPrevMonth + "%";
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (selectedDateFlag == true) {
			dobMnthString = " and to_char(dateofbirth,'Mon') like '"
					+ retriredPrevMonth + "' ";
			seperDtString = " AND DATEOFSEPERATION_DATE>=add_months('01"
					+ prevDate + "',1)";
		}
		log.info("date======financeForm4ReportByDJ=======" + date
				+ "prevDate============" + prevDate);
		query = "SELECT CPFACNO,EMPLOYEENO,EMPLOYEENAME,PENSIONNUMBER,AIRPORTCODE,DATEOFBIRTH,DATEOFJOINING,DESEGNATION,SEX,FHNAME,DATEOFSEPERATION_REASON,ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('01"
				+ prevDate
				+ "')),dateofbirth)/12) AS YEARS,DATEOFSEPERATION_DATE  FROM  employee_info "
				+ "where rowid=(select min(rowid) from employee_info where EMPFLAG='Y' AND CPFACNO='"
				+ cpfacno
				+ "' AND region='"
				+ region.trim()
				+ "')  and dateofbirth is not null "
				+ dobMnthString
				+ "  and  ((ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('01"
				+ prevDate
				+ "','dd/Mon/yyyy')),dateofbirth)/12)>=58 AND ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('01"
				+ prevDate
				+ "','dd/Mon/yyyy')),dateofbirth)/12)<59)OR  (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE<='01"
				+ prevDate + "'" + seperDtString + "))";
		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;

		String pensionString = "", dateOfJoining = "";
		String pensionArray[] = null;
		try {

			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				log.info("CPFACCNO==========" + rs.getString("CPFACNO")
						+ "Years" + rs.getString("YEARS"));

				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					formsetBean.setCpfaccno("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					formsetBean.setEmployeeNo("---");
				}
				if (rs.getString("PENSIONNUMBER") != null) {
					formsetBean.setPensionNumber(rs.getString("PENSIONNUMBER"));
				} else {
					formsetBean.setPensionNumber("---");
				}
				if (rs.getString("DATEOFJOINING") != null) {
					dateOfJoining = commonUtil.converDBToAppFormat(commonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"yyyy-MM-dd"), "yyyy-MM-dd", "dd-MMM-yyyy");
				} else {
					dateOfJoining = "";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					formsetBean.setDateOfBirth(commonUtil.converDBToAppFormat(
							commonUtil.getDatetoString(rs
									.getDate("DATEOFBIRTH"), "yyyy-MM-dd"),
							"yyyy-MM-dd", "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					formsetBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					formsetBean.setDesignation("---");
				}

				if (rs.getString("FHNAME") != null) {
					formsetBean.setFamilyMemberName(rs.getString("FHNAME"));
				} else {
					formsetBean.setFamilyMemberName("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					formsetBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					formsetBean.setEmployeeName("---");
				}

				if (rs.getString("SEX") != null) {
					formsetBean.setSex(rs.getString("SEX"));
				} else {
					formsetBean.setSex("---");
				}
				if (rs.getString("DATEOFSEPERATION_DATE") != null) {
					formsetBean.setDateOfLeaving(commonUtil.getDatetoString(rs
							.getDate("DATEOFSEPERATION_DATE"), "dd-MMM-yyyy"));
				} else {
					if (rs.getString("YEARS") != null) {
						formsetBean.setDateOfLeaving(rs.getString("YEARS"));
					} else {
						formsetBean.setDateOfLeaving("---");
					}
				}

				if (rs.getString("DATEOFSEPERATION_REASON") != null) {
					formsetBean.setLeavingReason(rs
							.getString("DATEOFSEPERATION_REASON"));
				} else {
					formsetBean.setLeavingReason("---");
				}
				if (!dateOfJoining.equals("")) {
					if (commonUtil.getDateDifference("01-Apr-1995",
							dateOfJoining) < 1) {
						formsetBean.setDateOfEntitle("01-Apr-1995");
					} else {
						formsetBean.setDateOfEntitle(dateOfJoining);
					}
				} else {
					formsetBean.setDateOfEntitle("---");
				}
				formsetBean.setRemarks("---");

			}

			// log.info("CPFACCNO======financeForm5ReportByDJ=="+formsetBean.
			// getCpfaccno()+"Name"+formsetBean.getEmployeeName());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return formsetBean;
	}

	public ArrayList getAirportListByForm45Report(String region) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		ArrayList airportList = new ArrayList();
		String query = "SELECT distinct AIRPORTCODE  FROM employee_info where region='"
				+ region + "'";
		log.info("query is " + query);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			String airportDesc = "";
			while (rs.next()) {
				if (rs.getString("AIRPORTCODE") != null) {
					airportDesc = rs.getString("AIRPORTCODE");
					airportList.add(airportDesc);
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return airportList;
	}

	private String getEmployeeInfoBasedCPFAccno(Connection con,
			String cpfaccno, String employeeno, String region,
			String retriedDate, String appEmpNameQry) throws SQLException,
			InvalidDataException {
		String pensionNumber = "", sqlQuery = "", retriredprevmonth = "", cpfacnoString = "", monthString = "", employeenoString = "";

		if (region.equals("RAUSAP") || cpfaccno.equals("")) {
			if (cpfaccno.equals("")) {
				cpfacnoString = "";
			} else {
				cpfacnoString = " ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE empflag='Y' AND CPFACNO='"
						+ cpfaccno + "' AND REGION='" + region + "'  ) ";
			}

			if (employeeno.equals("")) {
				employeenoString = "";
			} else {
				if (cpfaccno.equals("")) {
					employeenoString = "  EMPLOYEENO='" + employeeno
							+ "' AND REGION='" + region + "'";
				} else {
					employeenoString = "";
				}

			}
			sqlQuery = "SELECT  EMPSERIALNUMBER,EMPLOYEENO,PENSIONNUMBER,DATEOFJOINING,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF,DATEOFBIRTH,ROUND(months_between('"
					+ retriedDate
					+ "',dateofbirth)/12,3) EMPAGE,DESEGNATION,FHNAME,EMPLOYEENAME,SEX,WETHEROPTION,LASTACTIVE,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE AS SEPERATION_DATE,OTHERREASON FROM EMPLOYEE_INFO WHERE "
					+ cpfacnoString
					+ employeenoString
					+ " AND (DATEOFSEPERATION_REASON IN('Termination','Retirement','Death','Resignation','Other') or DATEOFSEPERATION_REASON is null) AND  "
					+ "NVL(DATEOFSEPERATION_DATE,'"
					+ retriedDate
					+ "') >= '"
					+ retriedDate
					+ "' and (round(MONTHS_BETWEEN( '"
					+ retriedDate
					+ "',nvl(DATEOFBIRTH,'"
					+ retriedDate
					+ "'))/12,2)<60) and   NVL(DATEOFJOINING,'01-Sep-2007') <= '"
					+ retriedDate + "' " + appEmpNameQry;
		} else {
			sqlQuery = "SELECT  EMPSERIALNUMBER,EMPLOYEENO,PENSIONNUMBER,DATEOFJOINING,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF,DATEOFBIRTH,ROUND(months_between('"
					+ retriedDate
					+ "',dateofbirth)/12,3) EMPAGE,DESEGNATION,FHNAME,EMPLOYEENAME,SEX,WETHEROPTION,LASTACTIVE,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE AS SEPERATION_DATE,OTHERREASON FROM EMPLOYEE_INFO WHERE rowid="
					+ "(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE empflag='Y' AND CPFACNO='"
					+ cpfaccno
					+ "'"
					+ " AND REGION='"
					+ region
					+ "')  "
					+ " AND (DATEOFSEPERATION_REASON IN('Termination','Retirement','Death','Resignation','Other') or DATEOFSEPERATION_REASON is null) AND  "
					+ "NVL(DATEOFSEPERATION_DATE,'"
					+ retriedDate
					+ "') >= '"
					+ retriedDate
					+ "' and (round(MONTHS_BETWEEN( '"
					+ retriedDate
					+ "',nvl(DATEOFBIRTH,'"
					+ retriedDate
					+ "'))/12,2)<60) and   NVL(DATEOFJOINING,'01-Sep-2007') <= '"
					+ retriedDate + "' " + appEmpNameQry;

			/*
			 * + " AND ((MONTHS_BETWEEN(LAST_DAY(TO_DATE('" + retriedDate +
			 * "','dd-Mon-yyyy')),NVL(DATEOFSEPERATION_DATE,'" + retriedDate +
			 * "'))/12<0 and DATEOFSEPERATION_REASON is not null and
			 * lower(DATEOFSEPERATION_REASON)=lower('Retirement')) OR
			 * (MONTHS_BETWEEN(LAST_DAY(TO_DATE('" + retriedDate +
			 * "','dd-Mon-yyyy')),NVL(DATEOFBIRTH,'" + retriedDate +
			 * "'))/12<60))";
			 */
		}

		log.info("sqlQuery========getEmployeeInfoBasedCPFAccno============"
				+ sqlQuery);
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;

		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			String remarks = "";
			while (rs.next()) {

				if (flag != true) {
					if (rs.getString("EMPSERIALNUMBER") != null) {
						pensionNumber = commonUtil.leadingZeros(5, rs
								.getString("EMPSERIALNUMBER"));

						flag = true;
					} else {
						pensionNumber = "";
					}
					if (rs.getString("DATEOFJOINING") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFJOINING"),
										"dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("DATEOFBIRTH") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFBIRTH"), "dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("DESEGNATION") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ StringUtility.replace(rs.getString(
										"DESEGNATION").toCharArray(), ","
										.toCharArray(), "-");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("FHNAME") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("FHNAME");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("EMPLOYEENAME") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ StringUtility.replace(
										rs.getString("EMPLOYEENAME")
												.toCharArray(),
										",".toCharArray(), "").toString()
										.toUpperCase();
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("SEX") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("SEX");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}

					pensionNumber = pensionNumber + ","
							+ rs.getLong("ENTITLEDIFF");
					if (rs.getString("WETHEROPTION") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("WETHEROPTION").trim();
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}

					pensionNumber = pensionNumber + "," + rs.getLong("EMPAGE");
					if (rs.getString("DATEOFSEPERATION_REASON") != null) {

						if (rs.getString("SEPERATION_DATE") != null) {
							remarks = "---";
						} else {
							remarks = remarks
									+ "SEPERATION DATE IS NOT AVAILABLE";
						}
						if (rs.getString("OTHERREASON") != null) {
							if (remarks.equals("---")) {
								remarks = "";
							}
							remarks = remarks + rs.getString("OTHERREASON");
						} else {
							remarks = "---";
						}
					} else {
						remarks = "---";
					}

					pensionNumber = pensionNumber + "," + remarks;

				}
				log.info("cpfaccno===getEmployeeInfoBasedCPFAccno====="
						+ cpfaccno + "pensionNumber===" + pensionNumber);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return pensionNumber;
	}

	public FinaceDataAvailable financeDataAvailableCheck() {
		log.info("FinanceDao :FinaceDataAvailable entering method");
		String query = "";
		query = "select cpfacno,airportcode,pensionnumber,desegnation,region,employeename,employeeno"
				+ ",dateofbirth,WETHEROPTION,SEX,fhname from employee_info where empflag='Y' and region='South Region' order by cpfacno,region";
		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;

		boolean flag = false;
		ArrayList availableList = new ArrayList();
		ArrayList unAvailableList = new ArrayList();
		FinaceDataAvailable bean = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				PensionBean data = new PensionBean();
				data = new PensionBean();
				if (rs.getString("cpfacno") != null) {
					data.setCpfAcNo(rs.getString("cpfacno"));
				} else {
					data.setCpfAcNo("");
				}
				if (rs.getString("airportcode") != null) {
					data.setAirportCode(rs.getString("airportcode"));
				} else {
					data.setAirportCode("--");
				}
				if (rs.getString("pensionnumber") != null) {
					data.setPensionnumber(rs.getString("pensionnumber"));
				} else {
					data.setPensionnumber("--");
				}
				if (rs.getString("desegnation") != null) {
					data.setDesegnation(rs.getString("desegnation"));
				} else {
					data.setDesegnation("--");
				}
				if (rs.getString("region") != null) {
					data.setRegion(rs.getString("region"));
				} else {
					data.setRegion("--");
				}
				log.info("EMPLOYEE (SearchPensionMasterAll)"
						+ rs.getString("employeename"));
				if (rs.getString("employeename") != null) {
					data.setEmployeeName(rs.getString("employeename"));
				} else {
					data.setEmployeeName("--");
				}
				if (rs.getString("employeeno") != null) {
					data.setEmployeeCode(rs.getString("employeeno"));
				} else {
					data.setEmployeeCode("--");
				}
				if (rs.getString("dateofbirth") != null) {
					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth")));
				} else {
					data.setDateofBirth("--");
				}
				if (rs.getString("fhname") != null) {
					data.setFHName(rs.getString("fhname"));
				} else {
					data.setFHName("--");
				}

				if (rs.getString("WETHEROPTION") != null) {
					data.setPensionOption(rs.getString("WETHEROPTION"));
				} else {
					data.setPensionOption("--");
				}

				if (rs.getString("sex") != null) {
					data.setSex(rs.getString("sex"));
				} else {
					data.setSex("--");
				}

				flag = this.getFinaceDataAvailableCheckFromMaster(con, data);
				if (flag) {
					availableList.add(data);
				} else {
					unAvailableList.add(data);
				}
				bean = new FinaceDataAvailable();
				bean.setAvailableList(availableList);
				bean.setUnAvailableList(unAvailableList);

			}
			log.info("availableList " + availableList.size());
			log.info("unavailableList " + unAvailableList.size());

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return bean;
	}

	private boolean getFinaceDataAvailableCheckFromMaster(Connection con,
			PensionBean data) throws SQLException, InvalidDataException {
		String sqlQuery = "";

		boolean avail = false;
		sqlQuery = "SELECT cpfaccno from  employee_pension_validate WHERE  empflag='Y' and CPFACCNO='"
				+ data.getCpfAcNo() + "'AND REGION='" + data.getRegion() + "'";

		log.info("sqlQuery========" + sqlQuery);
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("cpfaccno") != null) {
					avail = true;
				}

			}
			log.info("avail==========" + avail);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return avail;
	}

	// Un necessary Code
	/*
	 * public Date getCurrentDates(String format) { Date date1 = new Date();
	 * SimpleDateFormat sdf = new SimpleDateFormat(format); try { date1 =
	 * sdf.parse(sdf.format(new Date())); } catch (ParseException e) { // TODO
	 * Auto-generated catch block log.printStackTrace(e); } return date1; }
	 * 
	 * public int totalData(FinacialDataBean bean) throws Exception {
	 * log.info("FinanceDAO :totalData() entering method"); StringBuffer
	 * whereClause = new StringBuffer(); StringBuffer query = new
	 * StringBuffer(); String dynamicQuery = ""; Connection con = null;
	 * Statement st = null; ResultSet rs = null; String sqlQuery = "select
	 * ei.AIRPORTCODE AS CODE,ei.EMPLOYEENAME AS NAME,ei.DESEGNATION AS
	 * DESEG,ev.EMOLUMENTS AS EMOLUMENTS,ev.EMPPFSTATUARY AS EMPPFSTATUARY
	 * ,ev.EMPTOTAL AS EMPTOTAL from employee_pension_validate ev,employee_info
	 * ei where ev.CPFACCNO=ei.CPFACNO AND ev.employeeName=ei.employeeName ";
	 * 
	 * log.info("Employee " + bean.getEmployeeName() + "airport code" +
	 * bean.getAirportCode());
	 * 
	 * if (!bean.getEmployeeName().equals("")) { whereClause.append("
	 * LOWER(ei.EMPLOYEENAME) like'%" +
	 * bean.getEmployeeName().toLowerCase().trim() + "%'"); whereClause.append("
	 * AND "); }
	 * 
	 * if (!bean.getAirportCode().equals("")) { whereClause.append("
	 * LOWER(ei.AIRPORTCODE) like'%" +
	 * bean.getAirportCode().toLowerCase().trim() + "%'"); whereClause.append("
	 * AND "); }
	 * 
	 * if (!bean.getCpfAccNo().equals("")) { whereClause.append("
	 * LOWER(ei.CPFACNO)='" + bean.getCpfAccNo().trim() + "'");
	 * whereClause.append(" AND "); } if (!bean.getRegion().equals("")) {
	 * whereClause.append(" LOWER(ev.region)='" +
	 * bean.getRegion().trim().toLowerCase() + "'");
	 * whereClause.append(" AND "); } if (!bean.getMonthYear().equals("")) {
	 * 
	 * whereClause.append(" ev.monthyear ='" + bean.getMonthYear().trim() +
	 * "'"); whereClause.append(" AND "); } query.append(sqlQuery); if
	 * ((bean.getEmployeeName().equals("")) &&
	 * (bean.getAirportCode().equals("")) && (bean.getCpfAccNo().equals("") &&
	 * (bean.getMonthYear() .equals("") && (bean.getRegion().equals(""))))) { ;
	 * } else { query.append(" AND ");
	 * query.append(this.sTokenFormat(whereClause)); } / String orderBy="ORDER
	 * BY ei.CPFACCNO"; query.append(orderBy);
	 */
	/*
	 * dynamicQuery = query.toString();
	 * 
	 * log.info("Query " + dynamicQuery); int totalRecords = 0; try { con =
	 * commonDB.getConnection();
	 * 
	 * st = con.createStatement();
	 * 
	 * rs = st.executeQuery(dynamicQuery.trim());
	 * 
	 * while (rs.next()) { totalRecords++; } } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (Exception e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } finally {
	 * commonDB.closeConnection(con,st,rs); }
	 * log.info("FinanceDAO :totalData() leaving method");
	 * 
	 * return totalRecords; }
	 */

	public ArrayList getMappedUniqueNolist() {

		PensionBean data = null;
		ArrayList empinfo = new ArrayList();
		String sqlQuery = " select distinct(empserialnumber),cpfacno,dateofbirth,employeename,dateofjoining from   employee_info where empflag='Y' and EMPSERIALNUMBER is not null  order by EMPSERIALNUMBER";
		Connection con = null;
		PreparedStatement pst = null;
		Statement st = null;
		ResultSet rs = null;
		log.info("sqlQuery========" + sqlQuery);

		try {
			con = commonDB.getConnection();
			// st=con.createStatement();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			empinfo = new ArrayList();
			while (rs.next()) {

				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
				data = new PensionBean();
				if (rs.getString("empserialnumber") != null) {
					data.setEmpSerialNumber(rs.getString("empserialnumber"));
				} else {
					data.setEmpSerialNumber("");
				}
				if (rs.getString("employeename") != null) {
					data.setEmployeeName(rs.getString("employeename"));
				} else {
					data.setEmployeeName("");
				}
				empinfo.add(data);

			}
			log.info("empsize " + empinfo.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		log.info("PensionDAO :getMappedUniqueNolist() leaving method");
		return empinfo;

	}

	public ArrayList financeForm45Report(String airportCode, String date,
			String prevDate, String region, String sortingOrder,
			String reportType) {
		form3Bean formsetBean = new form3Bean();
		ArrayList form45ReportList = new ArrayList();
		String query = "", mnthYearString = "", frmMnth = "01/Apr/1995", toMnth = "", airportcodeString = "";
		boolean sltdMnthYear = false;

		if (prevDate.equals("/Aug/2007")) {
			sltdMnthYear = false;
			toMnth = "31" + prevDate;
			mnthYearString = "and to_char(MONTHYEAR, 'dd/Mon/yyyy') >='"
					+ frmMnth + "' AND to_char(MONTHYEAR, 'dd/Mon/yyyy') <='"
					+ toMnth + "'";
		} else {
			try {
				toMnth = "01" + prevDate;

				toMnth = commonUtil.converDBToAppFormat(toMnth, "dd/MMM/yyyy",
						"dd-MMM-yyyy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sltdMnthYear = true;
			mnthYearString = " and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
					+ toMnth + "'";

		}
		if (!airportCode.equals("NO-SELECT")) {
			airportcodeString = " and AIRPORTCODE='" + airportCode + "' ";
		}
		query = "select distinct cpfaccno from employee_pension_validate WHERE  empflag='Y' "
				+ mnthYearString
				+ " AND REGION='"
				+ region
				+ "' "
				+ airportcodeString + "  order by " + sortingOrder + "";

		log.info("query===" + reportType + "===financeForm45Report======"
				+ query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "";
		String pensionArray[] = null;
		String cpfacno = "", employeeno = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("CPFACCNO") != null) {
					cpfacno = rs.getString("CPFACCNO").trim();
				} else {
					cpfacno = "";
				}

				log.info("cpfacno=============================" + cpfacno
						+ "Airport code" + airportCode);
				if (reportType.equals("form-5")) {
					formsetBean = this.financeForm5ReportByDJ(con, airportCode,
							date, prevDate, region, cpfacno, employeeno,
							sltdMnthYear);
					if (!formsetBean.getCpfaccno().equals("")) {
						form45ReportList.add(formsetBean);
					} else {
						formsetBean = null;
					}
				} else {
					if (reportType.equals("form-4")) {

						formsetBean = this.financeForm4ReportByDJ(con,
								airportCode, date, prevDate, region, cpfacno,
								employeeno);
						if (formsetBean != null) {
							if (!formsetBean.getCpfaccno().equals("")) {
								form45ReportList.add(formsetBean);
							} else {
								formsetBean = null;
							}
						}

					}
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form45ReportList;
	}

	public ArrayList duplicateForm3Report(String date, String retriedDate) {

		form3Bean formsetBean = null;
		String appCpfaccnoQry = "";
		ArrayList form3ReportList = new ArrayList();

		String query = "";
		try {
			retriedDate = commonUtil.converDBToAppFormat(retriedDate,
					"MMMM-yyyy", "dd-MMM-yyyy");
			retriedDate = date.replaceAll("%", "01");

		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}

		query = "SELECT CPFACCNO,EMPLOYEENAME,EMPLOYEENO,AIRPORTCODE,REGION,TO_CHAR(MONTHYEAR,'dd-Mon-yyyy') AS MONTHYEAR,ROUND(EMOLUMENTS,2) AS  EMOLUMENTS FROM EMPLOYEE_PENSION_VALIDATE WHERE CPFACCNO IN(SELECT CPFACCNO FROM EMPLOYEE_PENSION_VALIDATE WHERE empflag='Y' and  TO_CHAR(MONTHYEAR, 'dd-Mon-yyyy') LIKE '"
				+ date
				+ "' GROUP BY CPFACCNO HAVING COUNT(*)>1) AND   empflag='Y'  and TO_CHAR(MONTHYEAR, 'dd-Mon-yyyy') LIKE '"
				+ date + "' ORDER BY CPFACCNO";
		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "";
		String cpfaccno = "", employeeno = "", entitleDiff = "", region = "";
		String pensionArray[] = null;
		long employeeOFYears = 0;
		double emouluments = 0.0;
		DecimalFormat df2 = new DecimalFormat("#########0");
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				log.info("CPFACCNO " + rs.getString("CPFACCNO"));
				if (rs.getString("CPFACCNO") != null) {
					cpfaccno = rs.getString("CPFACCNO");
				} else {
					cpfaccno = "";
				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");
				} else {
					region = "";
				}

				pensionString = this.getEmployeeInfoBasedCPFAccno(con,
						cpfaccno, employeeno, region, retriedDate, "");

				pensionArray = pensionString.split(",");
				if (pensionArray.length > 2) {

					formsetBean = new form3Bean();
					formsetBean.setRegion(region);
					formsetBean.setAirportCode(rs.getString("AIRPORTCODE"));
					if (rs.getString("EMOLUMENTS") != null) {

						emouluments = Double.parseDouble(rs
								.getString("EMOLUMENTS"));

						formsetBean.setEmoluments(df2.format(emouluments));
					} else {
						formsetBean.setEmoluments("0");
					}
					if (rs.getString("CPFACCNO") != null) {
						formsetBean.setCpfaccno(rs.getString("CPFACCNO"));
					} else {
						formsetBean.setCpfaccno("---");
					}

					if (rs.getString("EMPLOYEENO") != null) {
						formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
					} else {
						formsetBean.setEmployeeNo("---");
					}

					if (!pensionArray[2].equals("nodata")) {
						formsetBean.setDateOfBirth(pensionArray[2].trim());
					} else {
						formsetBean.setDateOfBirth("---");
					}

					dateOfJoining = pensionArray[1];
					if (!pensionArray[3].equals("nodata")) {
						formsetBean.setDesignation(pensionArray[3]);
					} else {
						formsetBean.setDesignation("---");
					}
					if (!pensionArray[4].equals("nodata")) {
						formsetBean.setFamilyMemberName(pensionArray[4]);
					} else {
						formsetBean.setFamilyMemberName("---");
					}
					// code changed name is getting from
					// employee_pension_validate table
					if (!pensionArray[5].equals("nodata")) {
						formsetBean.setEmployeeName(pensionArray[5]);
					} else {
						formsetBean.setEmployeeName("---");
					}
					/** new code for employeename * */
					/*
					 * if (rs.getString("MASTER_EMPNAME") != null) {
					 * formsetBean.
					 * setEmployeeName(rs.getString("MASTER_EMPNAME")); } else{
					 * formsetBean.setEmployeeName("---"); }
					 */
					if (!pensionArray[6].equals("nodata")) {
						formsetBean.setSex(pensionArray[6]);
					} else {
						formsetBean.setSex("---");
					}
					if (!pensionArray[8].equals("nodata")) {
						formsetBean.setWetherOption(pensionArray[8]);
					} else {
						formsetBean.setWetherOption("---");
					}
					if (dateOfJoining.equals("nodata")) {
						dateOfJoining = "";
					}
					if (!pensionArray[0].equals("")) {
						formsetBean.setPensionNumber(pensionArray[0]);
					} else {
						formsetBean.setPensionNumber("---");
					}

					if (!dateOfJoining.equals("")
							&& !formsetBean.getDateOfBirth().equals("---")) {

						long noOfYears = 0;
						if (!pensionArray[7].equals("nodata")) {
							noOfYears = Long.parseLong(pensionArray[7]);
						}

						if (noOfYears > 0) {
							employeeOFYears = commonUtil
									.getDateDifference(formsetBean
											.getDateOfBirth(), dateOfJoining);
							// log.info("CPFACCNO=======" +
							// formsetBean.getCpfaccno() +
							// "DateOfEntitle===========" + noOfYears+
							// "setAgeAtEntry===" + employeeOFYears);
							formsetBean.setAgeAtEntry(Long
									.toString(employeeOFYears));
							formsetBean.setDateOfEntitle(dateOfJoining);
						} else {
							employeeOFYears = commonUtil
									.getDateDifference(formsetBean
											.getDateOfBirth(), "01-Apr-1995");
							formsetBean.setAgeAtEntry(Long
									.toString(employeeOFYears));

							formsetBean.setDateOfEntitle("01-Apr-1995");

						}
					} else {
						formsetBean.setDateOfEntitle("---");
						formsetBean.setAgeAtEntry("---");
					}
					// log.info("Age
					// Entery"+formsetBean.getAgeAtEntry()+"CPFACCNO
					// "+formsetBean.getCpfaccno());
					// formsetBean.setRemarks("---");

					if (!pensionArray[10].equals("")) {
						formsetBean.setRemarks(pensionArray[10].toString());
					} else {
						formsetBean.setRemarks("---");
					}
					form3ReportList.add(formsetBean);
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form3ReportList;

	}

	public ArrayList financeForm3ReportAll(String date, String region,
			String retriedDate, String sortingOrder, String empName,
			String empNameFlag) {
		form3Bean formsetBean = null;
		ArrayList form3ReportList = new ArrayList();
		String appEmpNameQry = "", airportCode = "";
		if (empNameFlag.equals("true")) {
			if (!empName.equals("")) {
				appEmpNameQry = " AND LOWER(EMPLOYEENAME) LIKE'"
						+ empName.toLowerCase().trim() + "%'";
			} else {
				appEmpNameQry = " AND EMPLOYEENAME IS NULL";
			}
		} else {
			appEmpNameQry = "";
		}

		if (sortingOrder.equalsIgnoreCase("employeename")) {
			// sortingOrder="LOWER("+sortingOrder+")";
			sortingOrder = "LOWER(MASTER_EMPNAME)";
		}
		String query = "";
		try {
			retriedDate = commonUtil.converDBToAppFormat(retriedDate,
					"MMMM-yyyy", "dd-MMM-yyyy");
			retriedDate = date.replaceAll("%", "01");

		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
		query = "select  cpfaccno,EMOLUMENTS,employeeno,MASTER_EMPNAME,AIRPORTCODE,REGION from employee_pension_validate WHERE empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
				+ date + "'  order by " + sortingOrder;

		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "";
		String cpfaccno = "", employeeno = "", entitleDiff = "";
		String pensionArray[] = null;
		long employeeOFYears = 0;
		double emouluments = 0.0;
		DecimalFormat df2 = new DecimalFormat("#########0");
		String tempDOB = "", serailNumber = "", pfid = "", mastEmpName = "";
		form3CommonBean memberInfo = new form3CommonBean();
		ArrayList memberList = new ArrayList();
		String cpflist = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			memberInfo = loadFirstBoardList("CHQNAD", date, retriedDate,
					form3ReportList);
			form3ReportList = memberInfo.getForm3MemberList();
			cpflist = memberInfo.getCpflist();
			String[] cpfAccnoList = cpflist.split(",");
			for (int i = 0; i < cpfAccnoList.length; i++) {
				memberList.add(cpfAccnoList[i]);
			}

			while (rs.next()) {
				if (rs.getString("CPFACCNO") != null) {
					cpfaccno = rs.getString("CPFACCNO");
				} else {
					cpfaccno = "";
				}
				if (rs.getString("EMPLOYEENO") != null) {
					employeeno = rs.getString("EMPLOYEENO");
				} else {
					employeeno = "";
				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");
				} else {
					region = "";
				}
				if (rs.getString("AIRPORTCODE") != null) {
					airportCode = rs.getString("AIRPORTCODE");
				} else {
					airportCode = "";
				}
				if (!(region.equals("CHQNAD") && memberList.contains(cpfaccno))) {
					pensionString = this.getEmployeeInfoBasedCPFAccno(con,
							cpfaccno, employeeno, region, retriedDate,
							appEmpNameQry);

					pensionArray = pensionString.split(",");
					if (pensionArray.length > 2) {
						if (rs.getString("MASTER_EMPNAME") != null) {
							mastEmpName = rs.getString("MASTER_EMPNAME");
						} else {
							mastEmpName = "";
						}

						formsetBean = loadForm3Info(pensionArray, mastEmpName);
						formsetBean.setRegion(region);
						formsetBean.setAirportCode(airportCode);
						if (!mastEmpName.equals("")) {
							formsetBean.setEmployeeName(mastEmpName);
						} else {
							formsetBean.setEmployeeName("---");
						}

						if (rs.getString("EMOLUMENTS") != null) {
							emouluments = Double.parseDouble(rs
									.getString("EMOLUMENTS"));

							formsetBean.setEmoluments(df2.format(emouluments));
						} else {
							formsetBean.setEmoluments("0");
						}
						if (rs.getString("CPFACCNO") != null) {
							formsetBean.setCpfaccno(rs.getString("CPFACCNO"));
						} else {
							formsetBean.setCpfaccno("---");
						}

						if (rs.getString("EMPLOYEENO") != null) {
							formsetBean.setEmployeeNo(rs
									.getString("EMPLOYEENO"));
						} else {
							formsetBean.setEmployeeNo("---");
						}

						form3ReportList.add(formsetBean);
					}
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		return form3ReportList;
	}

	private form3Bean loadForm3Info(String pensionArray[], String mastEmpName) {
		long employeeOFYears = 0;
		String tempDOB = "", serailNumber = "", pfid = "", dateOfJoining = "";

		form3Bean formsetBean = new form3Bean();
		CommonDAO commonDAO = new CommonDAO();
		System.out.println(pensionArray.length);
		if (!pensionArray[2].equals("nodata")) {
			formsetBean.setDateOfBirth(pensionArray[2].trim());
		} else {
			formsetBean.setDateOfBirth("---");
		}

		dateOfJoining = pensionArray[1];
		if (!pensionArray[3].equals("nodata")) {
			formsetBean.setDesignation(pensionArray[3]);
		} else {
			formsetBean.setDesignation("---");
		}
		if (!pensionArray[4].equals("nodata")) {
			formsetBean.setFamilyMemberName(pensionArray[4]);
		} else {
			formsetBean.setFamilyMemberName("---");
		}

		/** new code for employeename * */

		if (!pensionArray[6].equals("nodata")) {
			formsetBean.setSex(pensionArray[6]);
		} else {
			formsetBean.setSex("---");
		}
		if (!pensionArray[8].equals("nodata")) {
			formsetBean.setWetherOption(pensionArray[8]);
		} else {
			formsetBean.setWetherOption("---");
		}
		if (dateOfJoining.equals("nodata")) {
			dateOfJoining = "";
		} else {
			formsetBean.setDateOfJoining(dateOfJoining);
		}

		if (!pensionArray[0].equals("")) {
			formsetBean.setPensionNumber(pensionArray[0]);
		} else {
			formsetBean.setPensionNumber("---");
		}

		if (pensionArray[2].equals("nodata")) {
			tempDOB = "";
		} else {
			tempDOB = pensionArray[2];
		}
		if (pensionArray[0].equals("nodata")) {
			serailNumber = "";
		} else {
			serailNumber = pensionArray[0];
		}
		if (!serailNumber.equals("")) {
			serailNumber = commonUtil.leadingZeros(5, serailNumber);
		}
		formsetBean.setAccntNo(serailNumber);
		if (!mastEmpName.equals("")) {
			pfid = commonDAO.getPFID(mastEmpName, tempDOB, serailNumber);
		} else {
			pfid = serailNumber;
		}

		log.info("pfid" + pfid);

		formsetBean.setPensionNumber(pfid);
		if (!dateOfJoining.equals("")
				&& !formsetBean.getDateOfBirth().equals("---")) {

			long noOfYears = 0;
			if (!pensionArray[7].equals("nodata")) {
				noOfYears = Long.parseLong(pensionArray[7]);
			}

			if (noOfYears > 0) {
				employeeOFYears = commonUtil.getDateDifference(formsetBean
						.getDateOfBirth(), dateOfJoining);

				formsetBean.setAgeAtEntry(Long.toString(employeeOFYears));
				formsetBean.setDateOfEntitle(dateOfJoining);
			} else {
				employeeOFYears = commonUtil.getDateDifference(formsetBean
						.getDateOfBirth(), "01-Apr-1995");
				formsetBean.setAgeAtEntry(Long.toString(employeeOFYears));

				formsetBean.setDateOfEntitle("01-Apr-1995");

			}
		} else {
			formsetBean.setDateOfEntitle("---");
			formsetBean.setAgeAtEntry("---");
		}

		if (!pensionArray[10].equals("")) {
			formsetBean.setRemarks(pensionArray[10].toString());
		} else {
			formsetBean.setRemarks("---");
		}
		return formsetBean;
	}

	private String getEmployeePendingPFIDSInfo(Connection con, String cpfaccno,
			String employeeno, String region, String retriedDate,
			String appEmpNameQry) throws SQLException, InvalidDataException {
		String pensionNumber = "", sqlQuery = "", cpfacnoString = "", employeenoString = "";

		if (region.equals("RAUSAP") || cpfaccno.equals("")) {
			if (cpfaccno.equals("")) {
				cpfacnoString = "";
			} else {
				cpfacnoString = " ROWID=(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE empflag='Y' AND CPFACNO='"
						+ cpfaccno + "' AND REGION='" + region + "'  ) ";
			}

			if (employeeno.equals("")) {
				employeenoString = "";
			} else {
				if (cpfaccno.equals("")) {
					employeenoString = "  EMPLOYEENO='" + employeeno
							+ "' AND REGION='" + region + "'";
				} else {
					employeenoString = "";
				}

			}
			sqlQuery = "SELECT  EMPSERIALNUMBER,EMPLOYEENO,PENSIONNUMBER,DATEOFJOINING,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF,DATEOFBIRTH,ROUND(months_between('"
					+ retriedDate
					+ "',dateofbirth)/12,3) EMPAGE,DESEGNATION,FHNAME,EMPLOYEENAME,SEX,WETHEROPTION,LASTACTIVE,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE AS SEPERATION_DATE FROM EMPLOYEE_INFO WHERE "
					+ cpfacnoString
					+ employeenoString
					+ " AND (DATEOFSEPERATION_REASON IN('Termination','Resignation','Death','Resignation','Other') or DATEOFSEPERATION_REASON is null) AND  "
					+ "NVL(DATEOFSEPERATION_DATE,'"
					+ retriedDate
					+ "') >= '"
					+ retriedDate
					+ "' and EMPSERIALNUMBER is null and (round(MONTHS_BETWEEN( '"
					+ retriedDate
					+ "',nvl(DATEOFBIRTH,'"
					+ retriedDate
					+ "'))/12,2)<60) and   NVL(DATEOFJOINING,'01-Sep-2007') <= '"
					+ retriedDate + "' " + appEmpNameQry;
		} else {
			sqlQuery = "SELECT  EMPSERIALNUMBER,EMPLOYEENO,PENSIONNUMBER,DATEOFJOINING,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF,DATEOFBIRTH,ROUND(months_between('"
					+ retriedDate
					+ "',dateofbirth)/12,3) EMPAGE,DESEGNATION,FHNAME,EMPLOYEENAME,SEX,WETHEROPTION,LASTACTIVE,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE AS SEPERATION_DATE FROM EMPLOYEE_INFO WHERE rowid="
					+ "(SELECT MIN(ROWID) FROM EMPLOYEE_INFO WHERE empflag='Y' AND CPFACNO='"
					+ cpfaccno
					+ "'"
					+ " AND REGION='"
					+ region
					+ "')  "
					+ " AND (DATEOFSEPERATION_REASON IN('Termination','Resignation','Death','Resignation','Other') or DATEOFSEPERATION_REASON is null) AND  "
					+ "NVL(DATEOFSEPERATION_DATE,'"
					+ retriedDate
					+ "') >= '"
					+ retriedDate
					+ "' and EMPSERIALNUMBER is null and (round(MONTHS_BETWEEN( '"
					+ retriedDate
					+ "',nvl(DATEOFBIRTH,'"
					+ retriedDate
					+ "'))/12,2)<60) and   NVL(DATEOFJOINING,'01-Sep-2007') <= '"
					+ retriedDate + "' " + appEmpNameQry;

			/*
			 * + " AND ((MONTHS_BETWEEN(LAST_DAY(TO_DATE('" + retriedDate +
			 * "','dd-Mon-yyyy')),NVL(DATEOFSEPERATION_DATE,'" + retriedDate +
			 * "'))/12<0 and DATEOFSEPERATION_REASON is not null and
			 * lower(DATEOFSEPERATION_REASON)=lower('Retirement')) OR
			 * (MONTHS_BETWEEN(LAST_DAY(TO_DATE('" + retriedDate +
			 * "','dd-Mon-yyyy')),NVL(DATEOFBIRTH,'" + retriedDate +
			 * "'))/12<60))";
			 */
		}

		log.info("sqlQuery========getEmployeeInfoBasedCPFAccno============"
				+ sqlQuery);
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;

		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			String remarks = "";
			while (rs.next()) {

				if (flag != true) {
					if (rs.getString("EMPSERIALNUMBER") != null) {
						pensionNumber = rs.getString("EMPSERIALNUMBER");

						flag = true;
					} else {
						pensionNumber = "";
					}
					if (rs.getString("DATEOFJOINING") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFJOINING"),
										"dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("DATEOFBIRTH") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ commonUtil.getDatetoString(rs
										.getDate("DATEOFBIRTH"), "dd-MMM-yyyy");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("DESEGNATION") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ StringUtility.replace(rs.getString(
										"DESEGNATION").toCharArray(), ","
										.toCharArray(), "-");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("FHNAME") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("FHNAME");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("EMPLOYEENAME") != null) {
						pensionNumber = pensionNumber
								+ ","
								+ StringUtility.replace(
										rs.getString("EMPLOYEENAME")
												.toCharArray(),
										",".toCharArray(), "").toString()
										.toUpperCase();
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}
					if (rs.getString("SEX") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("SEX");
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}

					pensionNumber = pensionNumber + ","
							+ rs.getLong("ENTITLEDIFF");
					if (rs.getString("WETHEROPTION") != null) {
						pensionNumber = pensionNumber + ","
								+ rs.getString("WETHEROPTION").trim();
					} else {
						pensionNumber = pensionNumber + "," + "nodata";
					}

					pensionNumber = pensionNumber + "," + rs.getLong("EMPAGE");
					if (rs.getString("DATEOFSEPERATION_REASON") != null) {
						if (rs.getString("SEPERATION_DATE") != null) {
							remarks = "---";
						} else {
							remarks = "SEPERATION DATE IS NOT AVAILABLE";
						}

					} else {
						remarks = "---";
					}
					pensionNumber = pensionNumber + "," + remarks;

				}
				log.info("cpfaccno===getEmployeeInfoBasedCPFAccno====="
						+ cpfaccno + "pensionNumber===" + pensionNumber);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return pensionNumber;
	}

	public ArrayList financeForm3ReportPFIDSAll(String date, String region,
			String retriedDate, String sortingOrder, String empName,
			String empNameFlag) {
		form3Bean formsetBean = null;
		ArrayList form3ReportList = new ArrayList();
		String appEmpNameQry = "", airportCode = "";
		if (empNameFlag.equals("true")) {
			if (!empName.equals("")) {
				appEmpNameQry = " AND LOWER(EMPLOYEENAME) LIKE'"
						+ empName.toLowerCase().trim() + "%'";
			} else {
				appEmpNameQry = " AND EMPLOYEENAME IS NULL";
			}
		} else {
			appEmpNameQry = "";
		}

		if (sortingOrder.equalsIgnoreCase("employeename")) {
			// sortingOrder="LOWER("+sortingOrder+")";
			sortingOrder = "LOWER(MASTER_EMPNAME)";
		}
		String query = "";
		try {
			retriedDate = commonUtil.converDBToAppFormat(retriedDate,
					"MMMM-yyyy", "dd-MMM-yyyy");
			retriedDate = date.replaceAll("%", "01");

		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}

		query = "select  cpfaccno,EMOLUMENTS,employeeno,MASTER_EMPNAME,AIRPORTCODE,REGION from employee_pension_validate WHERE empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
				+ date + "'  order by " + sortingOrder;

		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "";
		String cpfaccno = "", employeeno = "", entitleDiff = "";
		String pensionArray[] = null;
		long employeeOFYears = 0;
		double emouluments = 0.0;
		DecimalFormat df2 = new DecimalFormat("#########0");
		String tempDOB = "", serailNumber = "", pfid = "", mastEmpName = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("CPFACCNO") != null) {
					cpfaccno = rs.getString("CPFACCNO");
				} else {
					cpfaccno = "";
				}
				if (rs.getString("EMPLOYEENO") != null) {
					employeeno = rs.getString("EMPLOYEENO");
				} else {
					employeeno = "";
				}
				if (rs.getString("REGION") != null) {
					region = rs.getString("REGION");
				} else {
					region = "";
				}
				if (rs.getString("AIRPORTCODE") != null) {
					airportCode = rs.getString("AIRPORTCODE");
				} else {
					airportCode = "";
				}
				pensionString = this.getEmployeePendingPFIDSInfo(con, cpfaccno,
						employeeno, region, retriedDate, appEmpNameQry);
				log.info("pensionString *** " + pensionString);
				pensionArray = pensionString.split(",");
				if (pensionArray.length > 2) {
					if (rs.getString("MASTER_EMPNAME") != null) {
						mastEmpName = rs.getString("MASTER_EMPNAME");
					} else {
						mastEmpName = "";
					}

					formsetBean = loadForm3Info(pensionArray, mastEmpName);
					formsetBean.setRegion(region);
					formsetBean.setAirportCode(airportCode);
					if (!mastEmpName.equals("")) {
						formsetBean.setEmployeeName(mastEmpName);
					} else {
						formsetBean.setEmployeeName("---");
					}
					if (rs.getString("EMOLUMENTS") != null) {
						emouluments = Double.parseDouble(rs
								.getString("EMOLUMENTS"));

						formsetBean.setEmoluments(df2.format(emouluments));
					} else {
						formsetBean.setEmoluments("0");
					}
					if (rs.getString("CPFACCNO") != null) {
						formsetBean.setCpfaccno(rs.getString("CPFACCNO"));
					} else {
						formsetBean.setCpfaccno("---");
					}

					if (rs.getString("EMPLOYEENO") != null) {
						formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
					} else {
						formsetBean.setEmployeeNo("---");
					}

					form3ReportList.add(formsetBean);
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		return form3ReportList;
	}

	private form3CommonBean loadFirstBoardList(String region, String date,
			String retriedDate, ArrayList form3ReportList) {

		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String cpf = "";
		form3Bean formsetBean = null;
		String pensionString = "", dateOfJoining = "", appEmpNameQry = "", airportCode = "", cpfaccno = "";
		String transCpfaccno = "", employeeno = "", entitleDiff = "";
		String pensionArray[] = null;
		long employeeOFYears = 0;
		double emouluments = 0.0;
		form3CommonBean memberInfo = new form3CommonBean();
		DecimalFormat df2 = new DecimalFormat("#########0");
		String tempDOB = "", serailNumber = "", pfid = "", mastEmpName = "";
		try {
			con = commonDB.getConnection();
			cpf = this.getPersonalCPFACCNO(con, region);
			String[] cpfnolist = cpf.split(",");

			log.info("cpfnolist====Size" + cpfnolist.length + "cpfnolist===="
					+ cpfnolist);
			for (int i = 0; i < cpfnolist.length; i++) {
				cpfaccno = cpfnolist[i];
				String selTransQuery = "select  cpfaccno,EMOLUMENTS,employeeno,MASTER_EMPNAME,AIRPORTCODE,REGION from employee_pension_validate WHERE empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
						+ date
						+ "' AND  CPFACCNO='"
						+ cpfaccno
						+ "' and region='" + region + "'";
				st = con.createStatement();
				rs = st.executeQuery(selTransQuery);
				log.info(selTransQuery);
				while (rs.next()) {
					if (rs.getString("CPFACCNO") != null) {
						transCpfaccno = rs.getString("CPFACCNO");
					} else {
						transCpfaccno = "";
					}
					if (rs.getString("EMPLOYEENO") != null) {
						employeeno = rs.getString("EMPLOYEENO");
					} else {
						employeeno = "";
					}
					if (rs.getString("REGION") != null) {
						region = rs.getString("REGION");
					} else {
						region = "";
					}
					if (rs.getString("AIRPORTCODE") != null) {
						airportCode = rs.getString("AIRPORTCODE");
					} else {
						airportCode = "";
					}
					pensionString = this.getEmployeeInfoBasedCPFAccno(con,
							transCpfaccno, employeeno, region, retriedDate,
							appEmpNameQry);

					pensionArray = pensionString.split(",");
					if (pensionArray.length > 2) {
						if (rs.getString("MASTER_EMPNAME") != null) {
							mastEmpName = rs.getString("MASTER_EMPNAME");
						} else {
							mastEmpName = "";
						}

						formsetBean = loadForm3Info(pensionArray, mastEmpName);
						log.info("Pension Number"
								+ formsetBean.getPensionNumber());
						formsetBean.setRegion(region);
						formsetBean.setAirportCode(airportCode);
						if (!mastEmpName.equals("")) {
							formsetBean.setEmployeeName(mastEmpName);
						} else {
							formsetBean.setEmployeeName("---");
						}

						if (rs.getString("EMOLUMENTS") != null) {
							emouluments = Double.parseDouble(rs
									.getString("EMOLUMENTS"));

							formsetBean.setEmoluments(df2.format(emouluments));
						} else {
							formsetBean.setEmoluments("0");
						}
						if (rs.getString("CPFACCNO") != null) {
							formsetBean.setCpfaccno(rs.getString("CPFACCNO"));
						} else {
							formsetBean.setCpfaccno("---");
						}

						if (rs.getString("EMPLOYEENO") != null) {
							formsetBean.setEmployeeNo(rs
									.getString("EMPLOYEENO"));
						} else {
							formsetBean.setEmployeeNo("---");
						}

					}
				}
				form3ReportList.add(formsetBean);
			}
			memberInfo.setCpflist(cpf);
			memberInfo.setForm3MemberList(form3ReportList);
		} catch (SQLException se) {
			log.printStackTrace(se);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberInfo;
	}

	private String getPersonalCPFACCNO(Connection con, String region)
			throws Exception {
		String selPerQuery = "SELECT CPFACNO FROM EMPLOYEE_PERSONAL_INFO WHERE REGION='"
				+ region + "' AND PENSIONNO>=1 AND PENSIONNO<=49";

		Statement st = null;
		ResultSet rs = null;
		String pensionNo = "";
		try {
			log.info("getPersonalCPFACCNO====SEL===" + selPerQuery);
			st = con.createStatement();
			rs = st.executeQuery(selPerQuery);
			while (rs.next()) {

				if (rs.getString("CPFACNO") != null) {
					if (pensionNo.equals("")) {
						pensionNo = rs.getString("CPFACNO").trim();
					} else {
						pensionNo = pensionNo + ","
								+ rs.getString("CPFACNO").trim();
					}
				}

			}
			log.info("pensionNo" + pensionNo);
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

	public int[] financeRpfcFormsCountData(String airportCode, String date,
			String region, String prevDate) {
		Statement st = null;
		Statement st1 = null;
		ArrayList list = new ArrayList();
		ArrayList list4 = new ArrayList();
		ResultSet rs = null;
		Connection con = null;
		int form4count = 0, form5count = 0, lastMonthCount = 0;
		int dataAry[] = new int[3];
		String form4Query = "", form6Query = "", form5PrvDt = "", prvsDt = "", form3prvsDt = "", airportcodeString = "";
		if (!airportCode.equals("NO-SELECT")) {
			airportcodeString = " and AIRPORTCODE='" + airportCode + "' ";
		}
		form4Query = "select count(*) AS FORM4COUNT from employee_info where trim(REGION) = '"
				+ region.trim()
				+ "'  and to_char(DATEOFJOINING,'DD-Mon-yyyy') like '"
				+ prevDate + "'";

		try {
			prvsDt = prevDate.replaceAll("%", "01");
			form5PrvDt = commonUtil.converDBToAppFormat(prvsDt, "dd-MMM-yyyy",
					"dd/MMM/yyyy");
			form5PrvDt = form5PrvDt.substring(2, form5PrvDt.length());
			form3prvsDt = commonUtil.converDBToAppFormat(prvsDt, "dd-MMM-yyyy",
					"MMMM-yyyy");
			lastMonthCount = this.financeForm3Report(airportCode, prevDate,
					region, form3prvsDt, "cpfaccno", "", "false").size();
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
		list = financeForm45Report(airportCode, date, form5PrvDt, region,
				"cpfaccno", "form-5");
		list4 = financeForm45Report(airportCode, form5PrvDt, form5PrvDt,
				region, "cpfaccno", "form-4");
		form5count = list.size();
		form4count = list4.size();

		dataAry[0] = form4count;
		dataAry[1] = form5count;
		dataAry[2] = lastMonthCount;
		log.info("airportCode" + airportCode + "form4count" + dataAry[0]
				+ "form5count====" + dataAry[1]);
		return dataAry;

	}

	public ArrayList financeForm6AReport(Connection con, String airportCode,
			String date, String region, String sortingOrder) {
		form3Bean formsetBean = null;
		ArrayList form6AReportList = new ArrayList();
		String query = "";
		String empSub = "";
		String pfadvrec = "";
		String retiredDate = StringUtility.replace(date.toCharArray(),
				"%".toCharArray(), "01").toString();
		/*
		 * if (region.equals("East Region")) { pfadvrec =
		 * "(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0))principle,nvl(pv.CPFINTEREST,0)
		 * interest
		 * ,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,
		 * 0)+nvl(pv.CPFINTEREST,0))total"; } else { pfadvrec =
		 * "nvl(EMPADVRECPRINCIPAL,0) principle,nvl(EMPADVRECINTEREST,0)
		 * interest,Decode(nvl(pv.ADVREC,0),0,Decode(nvl(CPFADVANCE,0),0,(nvl(
		 * EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0)),CPFADVANCE),ADVREC)
		 * total"; }
		 */
		pfadvrec = "Decode(nvl(EMPADVRECPRINCIPAL,0),0,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0)),EMPADVRECPRINCIPAL) principle,Decode(nvl(EMPADVRECINTEREST,0),0,nvl(pv.CPFINTEREST,0),EMPADVRECINTEREST) interest,Decode(nvl(pv.ADVREC,0),0,Decode(nvl(CPFADVANCE,0),0,Decode((nvl(EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0)),0,(nvl(pv.PFADVANCE,0)-nvl(pv.ADVANCEDRWAN,0)-nvl(pv.PARTFINAL,0)+nvl(pv.CPFINTEREST,0)),(nvl(EMPADVRECPRINCIPAL,0)+nvl(EMPADVRECINTEREST,0))),CPFADVANCE),ADVREC) total";

		if (date.length() == 4) {
			query = "Select TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR,Decode(pv.AIRPORTCODE,'0','--',nvl(pv.AIRPORTCODE,'--'))AIRPORTCODE,"
					+ "trim(nvl(CPFACCNO,'--')),nvl(pv.EMPLOYEENO,'--') EMPLOYEENO,nvl(pv.EMPLOYEENAME,'--'),nvl(pv.DESEGNATION,'--'),EMOLUMENTS,"
					+ "nvl(pv.EMPPFSTATUARY,0),nvl(pv.EMPVPF,0),"
					+ pfadvrec
					+ ",nvl(pv.REMARKS,'--'),TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR  from "
					+ "employee_pension_validate pv where  pv.empflag='Y' and  to_char(MONTHYEAR, 'yyyy')like '"
					+ date
					+ "' AND pv.REGION='"
					+ region
					+ "' AND pv.AIRPORTCODE = '"
					+ airportCode
					+ "' order by "
					+ sortingOrder;
		} else {
			query = "Select Decode(pv.AIRPORTCODE,'0','--',nvl(pv.AIRPORTCODE,'--'))AIRPORTCODE,"
					+ "trim(nvl(CPFACCNO,'--')),nvl(pv.EMPLOYEENO,'--') EMPLOYEENO,nvl(pv.EMPLOYEENAME,'--'),nvl(pv.DESEGNATION,'--'),EMOLUMENTS,"
					+ "nvl(pv.EMPPFSTATUARY,0) AS EMPPFSTATUARY,nvl(pv.EMPVPF,0) AS EMPVPF,"
					+ pfadvrec
					+ ",nvl(pv.REMARKS,'--'),TO_CHAR(MONTHYEAR,'DD-MON-YYYY') AS MONTHYEAR from "
					+ "employee_pension_validate pv where  pv.empflag='Y' and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
					+ date
					+ "' AND pv.REGION='"
					+ region
					+ "' AND pv.AIRPORTCODE = '"
					+ airportCode
					+ "' order by "
					+ sortingOrder;
		}

		log.info("query======== for form-6A ====" + query);
		Statement st = null;
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs = null;

		FinancialReportDAO reportDAO = new FinancialReportDAO();
		String cpfaccno = "", employeeno = "", pensionString = "", dateOfJoining = "", whetherOption = "";
		String pensionArray[] = null;
		double pensionAsPerOption = 0.0;
		long transMntYear = 0, empRetriedDt = 0;
		long employeeOFYears = 0;
		int cellingRate = 0;
		boolean contrFlag = false, chkDOBFlag = false;
		String monthYear = "", days = "";
		int getDaysBymonth = 0;
		String dateOfRetriment = "";
		DecimalFormat df = new DecimalFormat("#########0.00");
		try {

			st = con.createStatement();
			st1 = con.createStatement();
			st2 = con.createStatement();
			rs = st.executeQuery(query);
			double totalPension = 0, totalEmoluments = 0.0, emoluments = 0.0;
			while (rs.next()) {
				pensionAsPerOption = 0.0;
				cpfaccno = rs.getString(2);
				employeeno = rs.getString("EMPLOYEENO");
				pensionString = this.getEmployeeInfoBasedCPFAccno(con,
						cpfaccno, employeeno, region, retiredDate, "");
				pensionArray = pensionString.split(",");

				if (pensionArray.length > 2) {
					formsetBean = new form3Bean();
					if (rs.getString(2) != null) {
						formsetBean.setCpfaccno(cpfaccno);
					} else {
						formsetBean.setCpfaccno("---");
					}
					if (rs.getString("EMPLOYEENO") != null) {
						formsetBean.setEmployeeNo(employeeno);
					} else {
						formsetBean.setEmployeeNo("---");
					}
					dateOfJoining = pensionArray[1];
					if (!pensionArray[3].equals("nodata")
							&& !pensionArray[3].equals("")) {
						formsetBean.setDesignation(pensionArray[3]);
					} else {
						formsetBean.setDesignation("---");
					}
					if (!pensionArray[5].equals("nodata")
							&& !pensionArray[5].equals("")) {
						formsetBean.setEmployeeName(pensionArray[5]);
					} else {
						formsetBean.setEmployeeName("---");
					}

					if (!pensionArray[0].equals("")) {
						formsetBean.setPensionNumber(pensionArray[0]);
					} else {
						formsetBean.setPensionNumber("---");
					}
					if (!pensionArray[8].equals("nodata")) {
						formsetBean.setWetherOption(pensionArray[8]);
					} else {
						formsetBean.setWetherOption("---");
					}
					if (!pensionArray[2].equals("nodata")) {
						formsetBean.setDateOfBirth(pensionArray[2].trim());
					} else {
						formsetBean.setDateOfBirth("---");
					}
					formsetBean.setEmoluments(rs.getString("EMOLUMENTS"));
					emoluments = Double.parseDouble(rs.getString("EMOLUMENTS"));
					totalEmoluments = totalEmoluments + emoluments;

					// log.info("pfstatuatory " + rs.getDouble(7));
					formsetBean.setEmpSubStatutory(rs
							.getDouble("EMPPFSTATUARY"));
					formsetBean.setEmpSubOptional(rs.getDouble(8));
					formsetBean.setPrinciple(rs.getDouble(9));
					formsetBean.setInterest(rs.getDouble(10));
					formsetBean.setPfAdvRecTotal(rs.getDouble(11));
					CommonDAO commonDAO = new CommonDAO();
					if (!formsetBean.getDateOfBirth().equals("---")) {
						String personalPFID = commonDAO.getPFID(formsetBean
								.getEmployeeName(), pensionArray[2],
								formsetBean.getPensionNumber());
						formsetBean.setPfID(personalPFID);
					} else {
						formsetBean.setPfID(formsetBean.getPensionNumber());
					}
					try {
						if (!formsetBean.getDateOfBirth().trim().equals("---")) {
							dateOfRetriment = reportDAO
									.getRetriedDate(formsetBean
											.getDateOfBirth());
						}

					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (rs.getString("MONTHYEAR") != null) {
						monthYear = rs.getString("MONTHYEAR");
					} else {
						monthYear = "-NA-";
					}

					employeeOFYears = Long.parseLong(pensionArray[9]);
					log.info("monthYear" + monthYear + "dateOfRetriment"
							+ dateOfRetriment);
					if (!monthYear.equals("-NA-")
							&& !dateOfRetriment.equals("")) {
						transMntYear = Date.parse(monthYear);
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
						pensionAsPerOption = reportDAO.pensionCalculation(
								monthYear, rs.getString("EMOLUMENTS"),
								formsetBean.getWetherOption(), region,"");
						if (chkDOBFlag == true) {
							String[] dobList = formsetBean.getDateOfBirth()
									.split("-");
							days = dobList[0];
							getDaysBymonth = reportDAO.getNoOfDays(formsetBean
									.getDateOfBirth());
							pensionAsPerOption = Math.round(pensionAsPerOption
									* (Double.parseDouble(days) - 1)
									/ getDaysBymonth);

						}

					} else {
						pensionAsPerOption = 0;
					}

					formsetBean.setEmpContrPF(rs.getDouble("EMPPFSTATUARY")
							- pensionAsPerOption);
					totalPension = totalPension + pensionAsPerOption;

					formsetBean.setEmpContrPension(pensionAsPerOption);
					formsetBean.setTotalPension(totalPension);
					formsetBean.setTotalEmoluments(totalPension);
					formsetBean.setEmpContrTotal(rs.getDouble("EMPPFSTATUARY"));
					log.info("Emp Name" + formsetBean.getEmployeeName()
							+ "Total" + formsetBean.getEmpContrTotal());
					formsetBean.setRemarks(rs.getString(12));
					formsetBean.setAirportCode(rs.getString("AIRPORTCODE"));
					contrFlag = false;
					chkDOBFlag = false;
					form6AReportList.add(formsetBean);
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return form6AReportList;
	}

	public ArrayList exportPFIDSReport(String frmyear, String frmMonth,
			String region) {
		form3Bean formsetBean = null;
		ArrayList form3ReportList = new ArrayList();
		String appEmpNameQry = "", airportCode = "";
		String query = " select pensionno,cpfacno,employeename,employeeno,desegnation,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,WETHEROPTION,airportcode,region from employee_personal_info where pensionno in (select distinct pensionno from employee_pension_validate where region='"
				+ region
				+ "' and monthyear>=to_date('01-Apr-2008','dd-Mon-yyyy') and pensionno is not null)";
		// log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String pensionString = "", dateOfJoining = "";
		String cpfaccno = "", employeeno = "", entitleDiff = "";
		String pensionArray[] = null;
		long employeeOFYears = 0;
		double emouluments = 0.0;
		DecimalFormat df2 = new DecimalFormat("#########0");
		String tempDOB = "", serailNumber = "", pfid = "", mastEmpName = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				formsetBean = new form3Bean();
				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO").toString());
				} else {
					formsetBean.setCpfaccno("");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO")
							.toString());
				} else {
					formsetBean.setEmployeeNo("");
				}
				if (rs.getString("REGION") != null) {
					formsetBean.setRegion(rs.getString("REGION").toString());
				} else {
					formsetBean.setRegion("");
				}
				if (rs.getString("AIRPORTCODE") != null) {
					formsetBean.setAirportCode(rs.getString("AIRPORTCODE")
							.toString());
				} else {
					formsetBean.setAirportCode("");
				}
				if (rs.getString("pensionno") != null) {
					formsetBean.setPfID(rs.getString("pensionno").toString());
				} else {
					formsetBean.setPfID("");
				}
				if (rs.getString("WETHEROPTION") != null) {
					formsetBean.setWetherOption(rs.getString("WETHEROPTION")
							.toString());
				} else {
					formsetBean.setWetherOption("");
				}
				if (rs.getString("employeename") != null) {
					formsetBean.setEmployeeName(rs.getString("employeename")
							.toString());
				} else {
					formsetBean.setEmployeeName("");
				}
				if (rs.getString("desegnation") != null) {
					formsetBean.setDesignation(rs.getString("desegnation")
							.toString());
				} else {
					formsetBean.setDesignation("");
				}
				if (rs.getString("dateofbirth") != null) {
					formsetBean.setDateOfBirth(rs.getString("dateofbirth")
							.toString());
				} else {
					formsetBean.setDateOfBirth("");
				}
				form3ReportList.add(formsetBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("pfidlist for" + region + "" + form3ReportList.size());

		return form3ReportList;
	}

	public ArrayList financeDataSearch(EmpMasterBean empSerach, int gridLength,
			int startIndex, String empNameCheck, String unmappedFlag,String reportType,String allRecordsFlag,String pfidfrom) {
		PensionBean pensionBean = null;
		ArrayList financedata = new ArrayList();
		String query = this.buildQueryforMapping(empSerach, gridLength,
				startIndex, empNameCheck, "", "", unmappedFlag,allRecordsFlag,pfidfrom);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = commonDB.getConnection();
			log.info("query is " + query.toString());
			PreparedStatement pst = null;
			int countGridLength = -1;
			st=con.createStatement();
			rs=st.executeQuery(query);
			while (rs.next()) {
				
					pensionBean = new PensionBean();

					if (rs.getString("cpfaccno") != null) {
						pensionBean.setCpfAcNo(rs.getString("cpfaccno")
								.toString());
					} else {
						pensionBean.setCpfAcNo("");
					}
					if (rs.getString("employeeno") != null) {
						pensionBean.setEmployeeCode(rs.getString("employeeno")
								.toString());
					} else {
						pensionBean.setEmployeeCode("");
					}
					if (rs.getString("REGION") != null) {
						pensionBean
								.setRegion(rs.getString("REGION").toString());
					} else {
						pensionBean.setRegion("");
					}
					if (rs.getString("AIRPORTCODE") != null) {
						pensionBean.setAirportCode(rs.getString("AIRPORTCODE")
								.toString());
					} else {
						pensionBean.setAirportCode("");
					}
					if (rs.getString("dateofjoining") != null) {
						pensionBean.setDateofJoining(commonUtil.getDatetoString(rs.getDate("dateofjoining"), "dd-MMM-yyyy"));
					} else {
						pensionBean.setDateofJoining("---");
					}
					if (rs.getString("dateofbirth") != null) {
						pensionBean.setDateofBirth(commonUtil.getDatetoString(rs.getDate("dateofbirth"), "dd-MMM-yyyy"));
					} else {
						pensionBean.setDateofBirth("---");
					}

					if (rs.getString("employeename") != null) {
						pensionBean.setEmployeeName(rs
								.getString("employeename").toString());
					} else {
						pensionBean.setEmployeeName("");
					}
					if (rs.getString("desegnation") != null) {
						pensionBean.setDesegnation(rs.getString("desegnation")
								.toString());
					} else {
						pensionBean.setDesegnation("---");
					}
					if (rs.getString("empserialnumber") != null) {
						pensionBean.setPensionnumber(rs.getString(
								"empserialnumber").toString());
					} else {
						pensionBean.setPensionnumber("");
					}
				
					if(unmappedFlag.equals("true")){
					if (rs.getString("TOTALRECORDS") != null) {
						pensionBean.setTotalRecrods(rs.getString(
								"TOTALRECORDS").toString());
					} else {
						pensionBean.setTotalRecrods("0");
					}
					}
					if (rs.getString("REASON") != null) {
						pensionBean.setSeperationReason(rs.getString("REASON")
						.toString());
						} else {
						pensionBean.setSeperationReason("");
						}
					if (rs.getString("formsdisable") != null) {
						pensionBean.setFormsdisable(rs.getString("formsdisable")
						.toString());
						} else {
						pensionBean.setFormsdisable("");
						}
					if (rs.getString("PCREPORTVERIFIED") != null) {
						pensionBean.setPcreportverified(rs.getString("PCREPORTVERIFIED")
						.toString());
						} else {
						pensionBean.setPcreportverified("");
						}
					if(allRecordsFlag.equals("true")){
						if(rs.getString("DATEOFSEPERATION_DATE")!=null){
							pensionBean.setSeperationDate(commonUtil.getDatetoString(rs.getDate("DATEOFSEPERATION_DATE"), "dd-MMM-yyyy"));
						}else{
							pensionBean.setSeperationDate("");
						}
					}
					
					if (rs.getString("pensioncliamsProcess") != null) {
						pensionBean.setClaimsprocess(rs.getString(
								"pensioncliamsProcess").toString());
					} else {
						pensionBean.setClaimsprocess("");
					}
					financedata.add(pensionBean);
					}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			log.info("inside finally block");
			commonDB.closeConnection(con, st, rs);
		}

		return financedata;
	}

	public int financePfidUpdate(String cpfaccno, String employeeCode,
			String region, String pfid) {
		int count = 0;
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String currentDate = this.getCurrentDate("dd-MMM-yyyy");
		String updateInfoTable = "update employee_info set empserialnumber='"
				+ pfid + "',lastactive='" + currentDate + "' where cpfacno='"
				+ cpfaccno + "' and region='" + region + "'";
		String loansTable = "update employee_pension_loans  set pensionno='"
				+ pfid + "' where cpfaccno='" + cpfaccno + "' and region='"
				+ region + "'";
		String obTable = "update employee_pension_ob  set pensionno='" + pfid
				+ "' where cpfaccno='" + cpfaccno + "' and region='" + region
				+ "'";
		String advanceTable = "update employee_pension_advances set pensionno='"
				+ pfid
				+ "' where cpfaccno='"
				+ cpfaccno
				+ "' and region='"
				+ region + "'";
		String transactionTable = "update employee_pension_validate set pensionno='"
				+ pfid
				+ "' where cpfaccno='"
				+ cpfaccno
				+ "' and region='"
				+ region + "'";
		log.info("" + updateInfoTable);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			count = st.executeUpdate(updateInfoTable);
			int count1 = st.executeUpdate(loansTable);
			int count2 = st.executeUpdate(obTable);
			int count3 = st.executeUpdate(advanceTable);
			int count4 = st.executeUpdate(transactionTable);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return count;
	}

	public int totalCountPersonal(EmpMasterBean empMasterBean, int gridLength,
			int startIndex, String empNameCheck, String total,
			String unmappedFlag,String allRecordsFlag,String pfidfrom) {
		log.info("FinancialDAO :totalCountPersonal() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int totalRecords = 0;
		String count = "Count";
		String selectSQL = "";

		selectSQL = this.buildQueryforMapping(empMasterBean, gridLength,
				startIndex, empNameCheck, count, total, unmappedFlag,allRecordsFlag,pfidfrom);

		log.info("Query =====" + selectSQL);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selectSQL);
			if (rs.next()) {
				totalRecords = Integer.parseInt(rs.getString("count"));
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		log.info("PersonalDAO :totalCountPersonal() leaving method");
		return totalRecords;
	}

	public String buildQueryforMapping(EmpMasterBean empSerach, int gridLength,
			int startIndex, String empNameCheck, String count, String total,
			String unmappedFlag,String allRecordsFlag,String pfidfrom) {
		PensionBean pensionBean = null;
		ArrayList financedata = new ArrayList();
		String sqlQuery = "";
		
		if (count == "" && unmappedFlag.equals("true")) {
			sqlQuery = "select * from v_unmapped_personal_info";
		} else if (count != "" && unmappedFlag.equals("true")) {
			sqlQuery = "select count(*)as count from v_unmapped_personal_info";
		} else if(count != "" && allRecordsFlag.equals("true")){
			sqlQuery="select count(*)as count  from employee_personal_info";
		}else if(count == "" && allRecordsFlag.equals("true")){
			sqlQuery="select pensionno as empserialnumber,employeeno,desegnation,airportcode,cpfacno as cpfaccno,employeename,dateofbirth,dateofjoining,pfsettled,region,DATEOFSEPERATION_REASON as REASON,DATEOFSEPERATION_DATE,pcreportverified,formsdisable,pensioncliamsProcess from employee_personal_info";
		}else if (count == "") {
			sqlQuery = "select * from (select * from mv_employee_Mapping";
		}

		else {
			sqlQuery = "select count(*) as count from (select * from mv_employee_Mapping";

		}
		
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";

		if (!empSerach.getCpfAcNo().equals("")) {
			whereClause.append(" LOWER(cpfaccno)='"
					+ empSerach.getCpfAcNo().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if (!empSerach.getEmpNumber().equals("")) {
			whereClause.append(" LOWER(employeeno)='"
					+ empSerach.getEmpNumber().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if (!empSerach.getEmpName().equals("") && empNameCheck.equals("true")) {
			whereClause.append(" LOWER(employeename) like'%"
					+ empSerach.getEmpName().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}
		if (!empSerach.getEmpName().equals("") && empNameCheck.equals("false")) {
			whereClause.append(" LOWER(employeename) like'"
					+ empSerach.getEmpName().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!empSerach.getRegion().equals("")) {
			whereClause.append(" LOWER(region)='"
					+ empSerach.getRegion().trim().toLowerCase() + "'");
			whereClause.append(" AND ");

		}
		if(!empSerach.getStation().equals("")) {
			whereClause.append(" LOWER(airportcode)='"
					+ empSerach.getStation().trim().toLowerCase() + "'");
			whereClause.append(" AND ");

		}

		if (!empSerach.getPfid().equals("")&&!allRecordsFlag.equals("true")) {
			whereClause.append(" LOWER(EMPSERIALNUMBER)='"
					+ empSerach.getPfid().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}else if(!empSerach.getPfid().equals("")&& allRecordsFlag.equals("true")){
			whereClause.append(" LOWER(pensionno)='"
					+ empSerach.getPfid().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}
		if(!empSerach.getPcverified().equals("")) {
			whereClause.append(" PCREPORTVERIFIED='"
					+ empSerach.getPcverified().trim() + "'");
			whereClause.append(" AND ");
		}
		
		query.append(sqlQuery);
		if (empSerach.getEmpName().equals("")
				&& empSerach.getRegion().equals("")
				&& empSerach.getCpfAcNo().equals("")
				&& empSerach.getEmpNumber().equals("")
				&& unmappedFlag.equals("false")&&allRecordsFlag.equals("false")
				&& empSerach.getPfid().equals("") && empSerach.getStation().equals("")) {
			;
		} else if ((unmappedFlag.equals("true")||allRecordsFlag.equals("true"))&&(!empSerach.getRegion().equals("")||(!empSerach.getStation().equals("")||(!empSerach.getPfid().equals(""))||(!empSerach.getCpfAcNo().equals(""))||(!empSerach.getEmpName().equals(""))||(!empSerach.getPcverified().equals(""))))) {
			query.append(" where ");
			query.append(this.sTokenFormat(whereClause));
		} else if (unmappedFlag.equals("true")||allRecordsFlag.equals("true")) {
          
		}else if ((!empSerach.getRegion().equals("")||(!empSerach.getStation().equals("")||(!empSerach.getPfid().equals(""))||(!empSerach.getCpfAcNo().equals(""))||(!empSerach.getEmpName().equals(""))))) {
			query.append(" where ");
			query.append(this.sTokenFormat(whereClause));
		}
		log.info(query.toString());
		String orderBy = "";
		if (count == "" && unmappedFlag.equals("true")) {
			// orderBy=" order by trim(employeename),cpfacno ";
		} else if (count != "" && unmappedFlag.equals("true")) {
			// orderBy=" order by trim(employeename),cpfacno";
		} else if (allRecordsFlag.equals("true")) {
			if(!pfidfrom.equals("")){
				int pfidfrm=Integer.parseInt(pfidfrom);
				int pfidto=23500;
			orderBy = " where pensionno between "+pfidfrm+" and "+pfidto+" order by pensionno";
			}else{
				orderBy =" order by pensionno";
			}
		}else if(!empSerach.getEmpName().equals("")||(!empSerach.getCpfAcNo().equals("")||!empSerach.getPfid().equals("")||!empSerach.getEmpNumber().equals(""))){
			orderBy = "and transferflag='Y'  and mappingflag='N' order by trim(employeename),cpfaccno)";
		}else if (count == "" && !allRecordsFlag.equals("true")) {
			orderBy = " where transferflag='Y' and mappingflag='N' order by trim(employeename),cpfaccno) ";
		} else if (total != "" && count != "") {
			orderBy = " where transferflag='Y'  and mappingflag='N' order by trim(employeename),cpfaccno) ";
		} else {
			orderBy = " where transferflag='Y' and mappingflag='N' order by trim(employeename),cpfaccno) ";
		}
		query.append(orderBy);
		return query.toString();
	}

	public void updateMappingFlag(String pfid, String cpfaccno, String region,String userName,String ipaddress) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int totalRecords = 0;
		String count = "Count";
		String selectSQL = "", selectSQL1 = "";

		selectSQL = "update employee_info set mappingflag='Y',IPADDRESS='"+ipaddress+"',LASTACTIVE=sysdate,USERNAME='"+userName+"' where empserialnumber='" + pfid
				+ "' ";
		selectSQL1 = "update employee_personal_info set mapingflag='Y',IPADDRESS='"+ipaddress+"',LASTACTIVE=sysdate,USERNAME='"+userName+"' WHERE pensionno='"
				+ pfid
				+ "' ";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			st.executeUpdate(selectSQL);
			st.executeUpdate(selectSQL1);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

	}
	public void formsDisable(String pfid,String formsstatus,String pcreportstatus,String username,String ipaddress) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int totalRecords = 0;
		String form3_7disable = Constants.form3_7disable;	
		String count = "Count";
		String selectSQL = "", selectSQL1 = "",pcreport_forms_stastus_log="";
        if(formsstatus!=""){
		selectSQL = "update employee_info set formsdisable='"+formsstatus+"' where empserialnumber='" + pfid
				+ "' ";
		selectSQL1 = "update employee_personal_info set formsdisable='"+formsstatus+"' where pensionno='"
		+ pfid
		+ "' ";
	
        }else{
        	selectSQL = "update employee_info set PCREPORTVERIFIED='"+pcreportstatus+"' where empserialnumber='" + pfid
			+ "' ";
        	selectSQL1 = "update employee_personal_info set PCREPORTVERIFIED='"+pcreportstatus+"' where pensionno='" + pfid
			+ "' ";
        }
        pcreport_forms_stastus_log="insert into pcreport_forms_stastus_log(pensionno,PCREPORTVERIFIED,FORMSDISABLE,username,ipaddress)values('"+pfid+"','"+pcreportstatus+"','"+formsstatus+"','"+username+"','"+ipaddress+"')";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			st.executeUpdate(selectSQL);
			st.executeUpdate(selectSQL1);
			st.executeUpdate(pcreport_forms_stastus_log);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

	}
	
	public void updateMapping(String pfid, String cpfaccno, String region,String username,String ipaddress) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int totalRecords = 0;
		String count = "Count";
		String selectSQL = "", selectSQL1 = "";

		selectSQL = "update employee_info set   empserialnumber='" + pfid
				+ "',mappedusernm='"+username+"',ipaddress='"+ipaddress+"' where cpfacno='"+cpfaccno+"' and region='"+region+"' ";
		selectSQL1 = "update employee_personal_info set pensionno='"
				+ pfid
				+ "',mappedusernm='"+username+"' where cpfacno='"+cpfaccno+"' and region='"+region+"' ";
		log.info("selectSQL1"+selectSQL1);
		try {
			log.info(selectSQL);
			con = commonDB.getConnection();
			st = con.createStatement();
			st.executeUpdate(selectSQL);
			st.executeUpdate(selectSQL1);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

	}
	public ArrayList SearchDeviationData(FinacialDataBean dbBean, int start)
			throws Exception {

		log.info("FinanceDAO :SearchDeviationData() entering method");
		ArrayList hindiData = new ArrayList();
		Connection con = null;
		String sqlQuery = this.buildQueryForDeviationDataSearch(dbBean);
		log.info("Query is(retriveByAll)" + sqlQuery);
		Statement st = null;
		ResultSet rs = null;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				dbBean = new FinacialDataBean();

				if (rs.getString("pensionno") != null) {
					dbBean.setPensionNumber(rs.getString("pensionno"));
				} else {
					dbBean.setPensionNumber("");
				}

				if (rs.getString("monthyear") != null) {
					dbBean.setMonthYear(commonUtil.getDatetoString(rs
							.getDate("monthyear"), "yyyy-MM-dd"));
				} else {
					dbBean.setMonthYear("");
				}

				dbBean.setMissingFlag("N");

				if (rs.getString("cpfaccno") != null) {
					dbBean.setCpfAccNo(rs.getString("cpfaccno"));
				} else {
					dbBean.setCpfAccNo("");
				}

				if (rs.getString("employeeno") != null) {
					dbBean.setEmployeeNewNo(rs.getString("employeeno"));
				} else {
					dbBean.setEmployeeNewNo("");
				}

				if (rs.getString("employeename") != null) {
					dbBean.setEmployeeName(rs.getString("employeename"));
				} else {
					dbBean.setEmployeeName("");
				}

				if (rs.getString("desegnation") != null) {
					dbBean.setDesignation(rs.getString("desegnation"));
				} else {
					dbBean.setDesignation("");
				}

				if (rs.getString("emoluments") != null) {
					dbBean.setEmoluments(rs.getString("emoluments"));
				} else {
					dbBean.setEmoluments("");
				}

				if (rs.getString("EMPPFSTATUARY") != null) {
					dbBean.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
				} else {
					dbBean.setEmpPfStatuary("");
				}

				if (rs.getString("CALCCPF") != null) {
					dbBean.setCpfArriers(rs.getString("CALCCPF"));
				} else {
					dbBean.setCpfArriers("");
				}

				if (rs.getString("DIFFCPF") != null) {
					dbBean.setCpfDifference(rs.getString("DIFFCPF"));
				} else {
					dbBean.setCpfDifference("");
				}

				if (rs.getString("AIRPORTCODE") != null) {
					dbBean.setAirportCode(rs.getString("AIRPORTCODE"));
				} else {
					dbBean.setAirportCode("");
				}

				if (rs.getString("REGION") != null) {
					dbBean.setRegion(rs.getString("REGION"));
				} else {
					dbBean.setRegion("");
				}

				hindiData.add(dbBean);
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
		log.info("FinanceDAO :SearchDeviationData() leaving method");
		return hindiData;
	}

	public String buildQueryForDeviationDataSearch(FinacialDataBean dbBean) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "";
		String sqlQuery1 = "  select pensionno, monthyear, cpfaccno,desegnation,employeeno,employeename,emoluments, EMPPFSTATUARY,ROUND((emoluments * 12 / 100)) AS CALCCPF,ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY AS DIFFCPF,AIRPORTCODE,REGION "
				+ " from employee_pension_validate  where  ((ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY) > 0 OR (ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY) < 0)";

		if (!dbBean.getPensionNumber().equals("")) {
			whereClause.append(" LOWER(pensionno)='"
					+ dbBean.getPensionNumber().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}

		if ((!dbBean.getFromDate().equals(""))
				&& (!dbBean.getToDate().equals(""))) {
			whereClause.append(" monthyear between  to_char(to_date('"
					+ dbBean.getFromDate().trim() + "','dd-mon-yyyy'))");
			whereClause.append(" and ");
			whereClause.append(" to_char(to_date('" + dbBean.getToDate().trim()
					+ "','dd-mon-yyyy')) ");
			whereClause.append(" AND ");
		}

		//whereClause.append("monthyear between '01-Apr-2005' and '31-Mar-2006'"
		// );
		// whereClause.append(" AND ");

		query.append(sqlQuery1);
		if ((dbBean.getPensionNumber().equals(""))
				&& (dbBean.getFromDate().equals(""))
				&& (dbBean.getToDate().equals(""))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = " order by pensionno";
		query.append(orderBy);

		dynamicQuery = query.toString();

		return dynamicQuery;
	}

	public ArrayList deviationReport(String range, String region,
			String airportcode, String frmSelectedDts, String empNameFlag,
			String empName, String sortedColumn, String pensionno) {

		log.info("FinancialDAO::deviationReport");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String fromYear = "", toYear = "", dateOfRetriment = "";
		FinacialDataBean dbBean = null;
		ArrayList deviationReportList = new ArrayList();

		log.info("......frmSelectedDts in DAO......." + frmSelectedDts);

		if (!frmSelectedDts.equals("")) {
			String[] dateArr = frmSelectedDts.split(",");
			fromYear = dateArr[0];
			toYear = dateArr[1];
		}

		String form1Query = this.buildQueryforDeviationReport(range, region,
				airportcode, fromYear, toYear, empNameFlag, empName,
				sortedColumn, pensionno);
		log.info("-------form1Query------" + form1Query);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(form1Query);

			while (rs.next()) {

				dbBean = new FinacialDataBean();

				if (rs.getString("pensionno") != null) {
					dbBean.setPensionNumber(rs.getString("pensionno"));
				} else {
					dbBean.setPensionNumber("---");
				}

				if (rs.getString("monthyear") != null) {
					dbBean.setMonthYear(commonUtil.getDatetoString(rs
							.getDate("monthyear"), "yyyy-MM-dd"));
				} else {
					dbBean.setMonthYear("---");
				}

				dbBean.setMissingFlag("N");

				if (rs.getString("cpfaccno") != null) {
					dbBean.setCpfAccNo(rs.getString("cpfaccno"));
				} else {
					dbBean.setCpfAccNo("---");
				}

				if (rs.getString("employeeno") != null) {
					dbBean.setEmployeeNewNo(rs.getString("employeeno"));
				} else {
					dbBean.setEmployeeNewNo("---");
				}

				if (rs.getString("employeename") != null) {
					dbBean.setEmployeeName(rs.getString("employeename"));
				} else {
					dbBean.setEmployeeName("---");
				}

				if (rs.getString("desegnation") != null) {
					dbBean.setDesignation(rs.getString("desegnation"));
				} else {
					dbBean.setDesignation("---");
				}

				if (rs.getString("emoluments") != null) {
					dbBean.setEmoluments(rs.getString("emoluments"));
				} else {
					dbBean.setEmoluments("---");
				}

				if (rs.getString("EMPPFSTATUARY") != null) {
					dbBean.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
				} else {
					dbBean.setEmpPfStatuary("---");
				}

				if (rs.getString("CALCCPF") != null) {
					dbBean.setCpfArriers(rs.getString("CALCCPF"));
				} else {
					dbBean.setCpfArriers("---");
				}

				if (rs.getString("DIFFCPF") != null) {
					dbBean.setCpfDifference(rs.getString("DIFFCPF"));
				} else {
					dbBean.setCpfDifference("---");
				}

				if (rs.getString("AIRPORTCODE") != null) {
					dbBean.setAirportCode(rs.getString("AIRPORTCODE"));
				} else {
					dbBean.setAirportCode("---");
				}

				if (rs.getString("REGION") != null) {
					dbBean.setRegion(rs.getString("REGION"));
				} else {
					dbBean.setRegion("---");
				}

				deviationReportList.add(dbBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		log.info("===========deviationReportList Size in DAO====="
				+ deviationReportList.size());
		return deviationReportList;
	}

	public String buildQueryforDeviationReport(String range, String region,
			String airportcode, String fromYear, String toYear,
			String empNameFlag, String empName, String sortedColumn,
			String pensionno) {
		log
				.info("AAIEPFReportDAO::buildQueryforDeviationReport-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "", orderBy = "";
		int startIndex = 0, endIndex = 0;

		sqlQuery = "  select pensionno, monthyear, cpfaccno,desegnation,employeeno,employeename,emoluments, EMPPFSTATUARY,ROUND((emoluments * 12 / 100)) AS CALCCPF,ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY AS DIFFCPF,AIRPORTCODE,REGION "
				+ " from employee_pension_validate  where  ((ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY) > 0 OR (ROUND((emoluments * 12 / 100)) - EMPPFSTATUARY) < 0)";

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

		if (!airportcode.equals("NO-SELECT")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}

		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		}

		query.append(sqlQuery);
		if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))
				&& (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {

		} else {
			query.append(" AND ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}
		orderBy = " ORDER BY  " + sortedColumn + " ASC";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("AAIEPFReportDAO::buildQueryforDeviationReport Leaving Method");
		return dynamicQuery;

	}

	public FinacialDataBean searcTransferData(FinacialDataBean dbBean)
			throws Exception {

		log.info("FinanceDAO :searcTransferData() entering method");
		ArrayList transferInList = new ArrayList();
		ArrayList transferOutList = new ArrayList();
		FinacialDataBean finBean = new FinacialDataBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = this.buildQueryForTransferDataSearch(dbBean);
		log.info("Query is(retriveByAll)" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				dbBean = new FinacialDataBean();

				if (rs.getString("PENSIONNO") != null) {
					dbBean.setPensionNumber(rs.getString("PENSIONNO"));
				} else {
					dbBean.setPensionNumber("");
				}

				if (rs.getString("CPFACNO") != null) {
					dbBean.setCpfAccNo(rs.getString("CPFACNO"));
				} else {
					dbBean.setCpfAccNo("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					dbBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					dbBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					dbBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					dbBean.setDesignation("");
				}

				if (rs.getString("AIRPORTCODE") != null) {
					dbBean.setAirportCode(rs.getString("AIRPORTCODE"));
				} else {
					dbBean.setAirportCode("");
				}

				if (rs.getString("REGION") != null) {
					dbBean.setRegion(rs.getString("REGION"));
				} else {
					dbBean.setRegion("");
				}

				if (rs.getString("INFORMATION") != null) {
					dbBean.setRemarks(rs.getString("INFORMATION"));
				} else {
					dbBean.setRemarks("");
				}

				if (rs.getString("PREVIOUSMONTH") != null) {
					String previousMon = commonUtil.getDatetoString(rs
							.getDate("PREVIOUSMONTH"), "dd/MMM/yyyy");
					dbBean.setPreviousMonth(commonUtil.converDBToAppFormat(
							previousMon, "dd/MMM/yyyy", "MMM-yy"));
				} else {
					dbBean.setPreviousMonth("");
				}

				if (dbBean.getRemarks().equals("TRANSFER IN/OTHERS")) {
					transferInList.add(dbBean);
				}

				if (dbBean.getRemarks().equals("TRANSFER OUT/OTHERS")) {
					transferOutList.add(dbBean);
				}
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
		log.info("FinanceDAO :searcTransferData() leaving method");

		finBean.setTransferInList(transferInList);
		finBean.setTransferOutList(transferOutList);
		return finBean;
	}

	public String buildQueryForTransferDataSearch(FinacialDataBean dbBean) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "", sqlQuery1 = "";

		String fromDate = dbBean.getFromDate().trim();

		sqlQuery1 = "SELECT DEVATIONST1.PENSIONNO AS PENSIONNO,VINFO.CPFACNO AS CPFACNO,VINFO.EMPLOYEENAME AS EMPLOYEENAME,VINFO.DESEGNATION AS DESEGNATION,VINFO.AIRPORTCODE AS AIRPORTCODE,VINFO.REGION AS REGION,DEVATIONST1.INFORMATION AS INFORMATION, add_months(to_date('"
				+ dbBean.getFromDate().trim()
				+ "'), -1) AS PREVIOUSMONTH"
				+ " FROM (SELECT DEVATIONST.PENSIONNO, DEVATIONST.INFORMATION  FROM (SELECT CURRENTMONTH.PENSIONNO, 'TRANSFER IN/OTHERS' AS INFORMATION "
				+ " FROM (SELECT PENSIONNO   FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN to_char(to_date('"
				+ dbBean.getFromDate().trim()
				+ "','dd-mon-yyyy')) and  LAST_DAY(to_char(to_date('"
				+ dbBean.getFromDate().trim()
				+ "','dd-mon-yyyy')))"
				+ " MINUS "
				+ " SELECT PENSIONNO  FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN 	add_months(to_date('"
				+ dbBean.getFromDate().trim()
				+ "'),-1) and  LAST_DAY(add_months(to_date('"
				+ dbBean.getFromDate().trim()
				+ "'),-1))) CURRENTMONTH"
				+ " UNION ALL "
				+ " SELECT PRIVOUSMONTH.PENSIONNO, 'TRANSFER OUT/OTHERS' AS INFORMATION FROM (SELECT PENSIONNO FROM EMPLOYEE_PENSION_VALIDATE"
				+ " WHERE MONTHYEAR BETWEEN add_months(to_date('"
				+ dbBean.getFromDate().trim()
				+ "'),-1) and LAST_DAY(add_months(to_date('"
				+ dbBean.getFromDate().trim()
				+ "'),-1))"
				+ " MINUS "
				+ " SELECT PENSIONNO FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN to_char(to_date('"
				+ dbBean.getFromDate().trim()
				+ "','dd-mon-yyyy')) and  LAST_DAY(to_char(to_date('"
				+ dbBean.getFromDate().trim()
				+ "','dd-mon-yyyy')))) PRIVOUSMONTH) DEVATIONST) DEVATIONST1,"
				+ " (SELECT CPFACNO,PENSIONNO,EMPLOYEENAME,DESEGNATION,AIRPORTCODE,REGION FROM employee_personal_info) VINFO"
				+ "  WHERE DEVATIONST1.PENSIONNO = VINFO.PENSIONNO ";

		if (!dbBean.getPensionNumber().equals("")) {
			whereClause.append(" LOWER(DEVATIONST1.PENSIONNO)='"
					+ dbBean.getPensionNumber().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}

		if (!dbBean.getRegion().equals("NO-SELECT")) {
			whereClause.append(" VINFO.REGION='" + dbBean.getRegion().trim()
					+ "'");
			whereClause.append(" AND ");
		}

		if (!dbBean.getAirportCode().equals("NO-SELECT")) {
			whereClause.append(" VINFO.AIRPORTCODE='"
					+ dbBean.getAirportCode().trim() + "'");
			whereClause.append(" AND ");
		}

		/*
		 * if ((!dbBean.getFromDate().equals("")) &&
		 * (!dbBean.getToDate().equals(""))) {
		 * whereClause.append(" monthyear between  to_char(to_date('"+
		 * dbBean.getFromDate().trim()+ "','dd-mon-yyyy'))");
		 * whereClause.append(" and "); whereClause.append(
		 * " to_char(to_date('"+ dbBean.getToDate().trim()+
		 * "','dd-mon-yyyy')) "); whereClause.append(" AND "); }
		 */

		//whereClause.append("monthyear between '01-Apr-2005' and '31-Mar-2006'"
		// );
		// whereClause.append(" AND ");
		query.append(sqlQuery1);
		if ((dbBean.getPensionNumber().equals(""))
				&& (dbBean.getRegion().equals("NO-SELECT"))
				&& (dbBean.getAirportCode().equals("NO-SELECT"))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = " ORDER BY DEVATIONST1.INFORMATION";

		query.append(orderBy);

		dynamicQuery = query.toString();

		return dynamicQuery;
	}

	public ArrayList searchYearlyEmpCount(FinacialDataBean dbBean)
			throws Exception {

		log.info("FinanceDAO :searchYearlyEmpCount() entering method");
		ArrayList empCountList = new ArrayList();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = this.buildQueryForYearlyEmpCountSearch(dbBean);
		log.info("Query is(retriveByAll)" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				dbBean = new FinacialDataBean();

				if (rs.getString("PENSIONNO") != null) {
					dbBean.setPensionNumber(rs.getString("PENSIONNO"));
				} else {
					dbBean.setPensionNumber("");
				}

				if (rs.getString("AIRPORTCODE") != null) {
					dbBean.setAirportCode(rs.getString("AIRPORTCODE"));
				} else {
					dbBean.setAirportCode("");
				}

				if (rs.getString("REGION") != null) {
					dbBean.setRegion(rs.getString("REGION"));
				} else {
					dbBean.setRegion("");
				}

				if (rs.getString("TOTALREC") != null) {
					dbBean.setRemarks(rs.getString("TOTALREC"));
				} else {
					dbBean.setRemarks("");
				}

				if (rs.getString("FINYEAR") != null) {
					dbBean.setMonthYear(rs.getString("FINYEAR"));
				} else {
					dbBean.setMonthYear("");
				}

				empCountList.add(dbBean);

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
		log.info("FinanceDAO :searchYearlyEmpCount() leaving method");

		return empCountList;
	}

	public String buildQueryForYearlyEmpCountSearch(FinacialDataBean dbBean) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "", sqlQuery1 = "", year = "", toYear = "", fromYear = "", yearVal = "";
		String fromDate = dbBean.getFromDate().trim();

		if (!dbBean.getMonthYear().equals("")) {
			year = dbBean.getMonthYear();
			String[] strArr = year.split("-");
			fromYear = strArr[0];
			toYear = Integer.toString(Integer.parseInt(fromYear) + 1);
		}
		yearVal = fromYear + "-" + toYear;

		sqlQuery1 = "  SELECT PENSIONNO, COUNT(*) AS TOTALREC,FINYEAR,REGION,AIRPORTCODE FROM EMPLOYEE_PENSION_VALIDATE WHERE FINYEAR ='"
				+ yearVal + "' ";

		if (!dbBean.getPensionNumber().equals("")) {
			whereClause.append(" LOWER(PENSIONNO)='"
					+ dbBean.getPensionNumber().toLowerCase().trim() + "'");
			whereClause.append(" AND ");
		}

		if (!dbBean.getRegion().equals("NO-SELECT")) {
			whereClause.append(" REGION='" + dbBean.getRegion().trim() + "'");
			whereClause.append(" AND ");
		}

		if (!dbBean.getAirportCode().equals("NO-SELECT")) {
			whereClause.append(" AIRPORTCODE='"
					+ dbBean.getAirportCode().trim() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery1);
		if ((dbBean.getPensionNumber().equals(""))
				&& (dbBean.getRegion().equals("NO-SELECT"))
				&& (dbBean.getAirportCode().equals("NO-SELECT"))) {
			;
		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		String orderBy = "  GROUP BY PENSIONNO,FINYEAR,REGION,AIRPORTCODE  ORDER BY PENSIONNO";

		query.append(orderBy);
		dynamicQuery = query.toString();

		return dynamicQuery;
	}

	public ArrayList transferReport(String region, String airportcode,
			String frmSelectedDts, String sortedColumn, String pensionno) {

		log.info("FinancialDAO::transferReport");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String fromYear = "", toYear = "", dateOfRetriment = "";
		FinacialDataBean dbBean = null;
		ArrayList deviationReportList = new ArrayList();

		log.info("......frmSelectedDts in DAO......." + frmSelectedDts);

		if (!frmSelectedDts.equals("")) {
			String[] dateArr = frmSelectedDts.split(",");
			fromYear = dateArr[0];
			toYear = dateArr[1];
		}

		String form1Query = this.buildQueryforTransferReport(region,
				airportcode, fromYear, toYear, sortedColumn, pensionno);
		log.info("-------form1Query------" + form1Query);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(form1Query);

			while (rs.next()) {
				dbBean = new FinacialDataBean();

				if (rs.getString("PENSIONNO") != null) {
					dbBean.setPensionNumber(rs.getString("PENSIONNO"));
				} else {
					dbBean.setPensionNumber("");
				}

				if (rs.getString("CPFACNO") != null) {
					dbBean.setCpfAccNo(rs.getString("CPFACNO"));
				} else {
					dbBean.setCpfAccNo("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					dbBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					dbBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					dbBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					dbBean.setDesignation("");
				}

				if (rs.getString("AIRPORTCODE") != null) {
					dbBean.setAirportCode(rs.getString("AIRPORTCODE"));
				} else {
					dbBean.setAirportCode("");
				}

				if (rs.getString("REGION") != null) {
					dbBean.setRegion(rs.getString("REGION"));
				} else {
					dbBean.setRegion("");
				}

				if (rs.getString("INFORMATION") != null) {
					dbBean.setRemarks(rs.getString("INFORMATION"));
				} else {
					dbBean.setRemarks("");
				}
				deviationReportList.add(dbBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

		log.info("===========deviationReportList Size in DAO====="
				+ deviationReportList.size());
		return deviationReportList;
	}

	public String buildQueryforTransferReport(String region,
			String airportcode, String fromYear, String toYear,
			String sortedColumn, String pensionno) {
		log
				.info("AAIEPFReportDAO::buildQueryforTransferReport-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "", orderBy = "";
		int startIndex = 0, endIndex = 0;

		sqlQuery = "SELECT DEVATIONST1.PENSIONNO AS PENSIONNO,VINFO.CPFACNO AS CPFACNO,VINFO.EMPLOYEENAME AS EMPLOYEENAME,VINFO.DESEGNATION AS DESEGNATION,VINFO.AIRPORTCODE AS AIRPORTCODE,VINFO.REGION AS REGION,DEVATIONST1.INFORMATION AS INFORMATION"
				+ " FROM (SELECT DEVATIONST.PENSIONNO, DEVATIONST.INFORMATION  FROM (SELECT CURRENTMONTH.PENSIONNO, 'NEWJOINEES' AS INFORMATION "
				+ " FROM (SELECT PENSIONNO   FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN to_char(to_date('"
				+ fromYear.trim()
				+ "','dd-mon-yyyy')) and  LAST_DAY(to_char(to_date('"
				+ fromYear.trim()
				+ "','dd-mon-yyyy')))"
				+ " MINUS "
				+ " SELECT PENSIONNO  FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN 	add_months(to_date('"
				+ fromYear.trim()
				+ "'),-1) and  LAST_DAY(add_months(to_date('"
				+ fromYear.trim()
				+ "'),-1))) CURRENTMONTH"
				+ " UNION ALL "
				+ " SELECT PRIVOUSMONTH.PENSIONNO, 'MISSINGDATA' AS INFORMATION FROM (SELECT PENSIONNO FROM EMPLOYEE_PENSION_VALIDATE"
				+ " WHERE MONTHYEAR BETWEEN add_months(to_date('"
				+ fromYear.trim()
				+ "'),-1) and LAST_DAY(add_months(to_date('"
				+ fromYear.trim()
				+ "'),-1))"
				+ " MINUS "
				+ " SELECT PENSIONNO FROM EMPLOYEE_PENSION_VALIDATE  WHERE MONTHYEAR BETWEEN to_char(to_date('"
				+ fromYear.trim()
				+ "','dd-mon-yyyy')) and  LAST_DAY(to_char(to_date('"
				+ fromYear.trim()
				+ "','dd-mon-yyyy')))) PRIVOUSMONTH) DEVATIONST) DEVATIONST1,"
				+ " (SELECT CPFACNO,PENSIONNO,EMPLOYEENAME,DESEGNATION,AIRPORTCODE,REGION FROM employee_personal_info) VINFO"
				+ "  WHERE DEVATIONST1.PENSIONNO = VINFO.PENSIONNO ";

		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}

		if (!airportcode.equals("NO-SELECT")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))) {

		} else {
			query.append(" AND ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}

		orderBy = " ORDER BY DEVATIONST1.INFORMATION";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("AAIEPFReportDAO::buildQueryforTransferReport Leaving Method");
		return dynamicQuery;

	}

	public void updateTransferData(String pensionno, String seperationreason,
			String sepetaionDt, String airportcode, String region,
			String fromairportcode, String fromregion, String status) {

		log.info("FinancialDAO::updateTransferData");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sepetaionDate = "", updateQuery = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (!sepetaionDt.equals("")) {
				sepetaionDate = commonUtil.converDBToAppFormat(sepetaionDt,
						"dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (status.equals("TRANSFER IN/OTHERS")) {
				updateQuery = "update employee_personal_info set AIRPORTCODE='"
						+ airportcode + "',REGION='" + region
						+ "' where PENSIONNO='" + pensionno + "'";

				String insertQuery = "insert into employee_transfer_info(PENSIONNO,OLDAIRPORTCODE,NEWAIRPORTCODE,OLDREGION,NEWREGION,TRANSFERDATE) values('"
						+ pensionno
						+ "','"
						+ fromairportcode
						+ "','"
						+ airportcode
						+ "','"
						+ fromregion
						+ "','"
						+ region
						+ "','" + sepetaionDate + "')";
				log.info("---------insertQuery---------" + insertQuery);
				int cnt = st.executeUpdate(insertQuery);
			} else {
				updateQuery = "update employee_personal_info set DATEOFSEPERATION_REASON='"
						+ seperationreason
						+ "',DATEOFSEPERATION_DATE='"
						+ sepetaionDate
						+ "' where PENSIONNO='"
						+ pensionno
						+ "'";
			}

			log.info("-----updateQuery-------" + updateQuery);
			int count = st.executeUpdate(updateQuery);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}

	}
	public ArrayList rpfcForm5ReportByDJ(String date, String prevDate, String region, String pensionNo,
			String airportCode,String sortingOrder) {
		form3Bean formsetBean = null;
		
		ArrayList form5ReportList = new ArrayList();
		String query = "", retriredPrevMonth = "", dobMnthString = "", seperDtString = "";
		String mnthYearString = "", frmMnth = "01/Apr/1995", toMnth = "", airportcodeString = "";
		boolean sltdMnthYear = false;
		Connection con=null;
		if (prevDate.equals("01/Aug/2007")) {
			sltdMnthYear = false;
			toMnth = "31/Aug/2007";
			mnthYearString = "and to_char(MONTHYEAR, 'dd/Mon/yyyy') >='"
					+ frmMnth + "' AND to_char(MONTHYEAR, 'dd/Mon/yyyy') <='"
					+ toMnth + "'";
		} else {
			try {
				toMnth =prevDate;

				toMnth = commonUtil.converDBToAppFormat(toMnth, "dd/MMM/yyyy",
						"dd-MMM-yyyy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sltdMnthYear = true;
			mnthYearString = " and to_char(MONTHYEAR, 'dd-Mon-yyyy') like '"
					+ toMnth + "'";

		}
		try {
			retriredPrevMonth = commonUtil.converDBToAppFormat(prevDate,
					"dd/MMM/yyyy", "MMM");
			retriredPrevMonth = "%" + retriredPrevMonth + "%";
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (sltdMnthYear == true) {
			dobMnthString = " and to_char(dateofbirth,'Mon') like '"
					+ retriredPrevMonth + "' ";
			seperDtString = " AND DATEOFSEPERATION_DATE>=add_months('"
					+ prevDate + "',1)";
		}
		String pensionQuryString="",dateOfLeaving="";
		if(!pensionNo.equals("")){
			pensionQuryString=" PENSIONNO="+pensionNo+" and";
		}else{
			pensionQuryString="";
		}
		log.info("date======rpfcForm5ReportByDJ=======" + date
				+ "prevDate============" + prevDate);
		query = "SELECT CPFACNO,EMPLOYEENO,EMPLOYEENAME,PENSIONNO,AIRPORTCODE,DATEOFBIRTH,DATEOFJOINING,DESEGNATION,GENDER,FHNAME, to_char(dateofbirth + INTERVAL'58' year,'dd-Mon-yyyy')  as DOM,DATEOFSEPERATION_REASON,ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('"
				+ prevDate
				+ "')),dateofbirth)/12) AS YEARS,DATEOFSEPERATION_DATE  FROM  EMPLOYEE_PERSONAL_INFO "
				+ "where "+pensionQuryString+"  dateofbirth is not null "
				+ dobMnthString
				+ "  and  ((ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('"
				+ prevDate
				+ "','dd/Mon/yyyy')),dateofbirth)/12)>=58 AND ROUND(MONTHS_BETWEEN(LAST_DAY(TO_DATE('"
				+ prevDate
				+ "','dd/Mon/yyyy')),dateofbirth)/12)<59)OR  (DATEOFSEPERATION_REASON IN ('Death','Resignation') AND DATEOFSEPERATION_DATE<='"
				+ prevDate + "'" + seperDtString + ")) ORDER BY "+sortingOrder;
		log.info("query============" + query);
		Statement st = null;
		ResultSet rs = null;

		String pensionString = "", dateOfJoining = "";
		String pensionArray[] = null;
		try {
			con=commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				formsetBean=new form3Bean();
				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					formsetBean.setCpfaccno("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					formsetBean.setEmployeeNo("---");
				}
				if (rs.getString("PENSIONNO") != null) {
					formsetBean.setPensionNumber(rs.getString("PENSIONNO"));
				} else {
					formsetBean.setPensionNumber("---");
				}
				if (rs.getString("DATEOFJOINING") != null) {
					dateOfJoining = commonUtil.converDBToAppFormat(commonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"yyyy-MM-dd"), "yyyy-MM-dd", "dd-MMM-yyyy");
				} else {
					dateOfJoining = "";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					formsetBean.setDateOfBirth(commonUtil.converDBToAppFormat(
							commonUtil.getDatetoString(rs
									.getDate("DATEOFBIRTH"), "yyyy-MM-dd"),
							"yyyy-MM-dd", "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					formsetBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					formsetBean.setDesignation("---");
				}

				if (rs.getString("FHNAME") != null) {
					formsetBean.setFamilyMemberName(rs.getString("FHNAME"));
				} else {
					formsetBean.setFamilyMemberName("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					formsetBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					formsetBean.setEmployeeName("---");
				}

				if (rs.getString("GENDER") != null) {
					formsetBean.setSex(rs.getString("GENDER"));
				} else {
					formsetBean.setSex("---");
				}
				if (rs.getString("DATEOFSEPERATION_DATE") != null) {
					formsetBean.setDateOfLeaving(commonUtil.getDatetoString(rs
							.getDate("DATEOFSEPERATION_DATE"), "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfLeaving(rs.getString("DOM"));
				}
				
				if (rs.getString("YEARS") != null) {
					dateOfLeaving=rs.getString("YEARS");
				} else {
					dateOfLeaving="0";
				}
				
				if (rs.getString("DATEOFSEPERATION_REASON") != null) {
					formsetBean.setLeavingReason(rs
							.getString("DATEOFSEPERATION_REASON"));
				} else {
					if (dateOfLeaving.equals("58")) {
						formsetBean.setLeavingReason("Attain 58 years");
					}else{
						formsetBean.setLeavingReason("---");
					}
					
				}
				if (!dateOfJoining.equals("")) {
					if (commonUtil.getDateDifference("01-Apr-1995",
							dateOfJoining) < 1) {
						formsetBean.setDateOfEntitle("01-Apr-1995");
					} else {
						formsetBean.setDateOfEntitle(dateOfJoining);
					}
				} else {
					formsetBean.setDateOfEntitle("---");
				}
				formsetBean.setRemarks("---");
				form5ReportList.add(formsetBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form5ReportList;
	}
	public ArrayList rpfcForm4ReportByDJ(String airportCode, String date,
			String prevDate, String region,String pensionno,String sortingOrder) {
		form3Bean formsetBean = null;
		ArrayList form45ReportList = new ArrayList();
		String query = "",currentDate="",regionString="";
		log.info("date======rpfcForm4ReportByDJ=======" + date);
		try {
			currentDate=commonUtil.converDBToAppFormat(date,"dd/MMM/yyyy","/MMM/yyyy");
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			log.printStackTrace(e1);
		}
		if(region.equals("NO-SELECT")){
			regionString="";
		}else{
			regionString=region;
		}
		query = this.buildQueryForm4(regionString,airportCode,currentDate,sortingOrder,pensionno);
		log.info("rpfcForm4ReportByDJ:query============" + query);
		Statement st = null;
		ResultSet rs = null;
		Connection con = null;
		String dateOfJoining = "";
		String pensionArray[] = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				formsetBean = new form3Bean();

				if (rs.getString("CPFACNO") != null) {
					formsetBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					formsetBean.setCpfaccno("---");
				}
				if (rs.getString("PENSIONNO") != null) {
					formsetBean.setPensionNumber(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
				} else {
					formsetBean.setPensionNumber("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					formsetBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					formsetBean.setEmployeeNo("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					dateOfJoining = commonUtil.converDBToAppFormat(commonUtil
							.getDatetoString(rs.getDate("DATEOFJOINING"),
									"dd/MMM/yyyy"), "dd/MMM/yyyy",
							"dd-MMM-yyyy");
				} else {
					dateOfJoining = "";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					formsetBean.setDateOfBirth(commonUtil.converDBToAppFormat(
							commonUtil.getDatetoString(rs
									.getDate("DATEOFBIRTH"), "dd/MMM/yyyy"),
							"dd/MMM/yyyy", "dd-MMM-yyyy"));
				} else {
					formsetBean.setDateOfBirth("---");
				}

				if (rs.getString("DESEGNATION") != null) {
					formsetBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					formsetBean.setDesignation("---");
				}

				if (rs.getString("FHNAME") != null) {
					formsetBean.setFamilyMemberName(rs.getString("FHNAME"));
				} else {
					formsetBean.setFamilyMemberName("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					formsetBean.setEmployeeName(rs.getString("EMPLOYEENAME").toUpperCase());
				} else {
					formsetBean.setEmployeeName("---");
				}

				if (rs.getString("GENDER") != null) {
					formsetBean.setSex(rs.getString("GENDER"));
				} else {
					formsetBean.setSex("---");
				}
				if(rs.getString("employeeOFYears")!=null){
					formsetBean.setAgeAtEntry(rs.getString("employeeOFYears"));
				}else{
					formsetBean.setAgeAtEntry("---");
				}
				
				
				if (!dateOfJoining.equals("")) {
					if (commonUtil.getDateDifference("01-Apr-1995",
							dateOfJoining) < 1) {
						formsetBean.setDateOfEntitle("01-Apr-1995");
					} else {
						formsetBean.setDateOfEntitle(dateOfJoining);
					}
				} else {
					formsetBean.setDateOfEntitle("---");
				}

				formsetBean.setRemarks("---");
				form45ReportList.add(formsetBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con, st, rs);
		}
		return form45ReportList;
	}
	public String buildQueryForm4(String region, String airportcode, String date,
			String sortingOrder,String pensionno) {
		log
				.info("FinancialDAO::buildQueryForm4-- Entering Method");
		String dataString = "";
		boolean chkflag = false;
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";

		sqlQuery = "SELECT REFPENSIONNUMBER,LASTACTIVE,OTHERREASON,region,CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,INITCAP(DESEGNATION) AS DESEGNATION, DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
				+ "DATEOFSEPERATION_DATE, WHETHER_FORM1_NOM_RECEIVED, AIRPORTCODE,GENDER ,INITCAP(FHNAME) AS FHNAME,MARITALSTATUS,WETHEROPTION ,"
				+ "SETDATEOFANNUATION,OTHERREASON,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,PENSIONNO,round(months_between(dateofjoining,'01-Apr-1995'),3) ENTITLEDIFF,round(months_between(DATEOFJOINING,DATEOFBIRTH)/12) employeeOFYears FROM EMPLOYEE_PERSONAL_INFO WHERE  to_char(DATEOFJOINING,'DD/Mon/YYYY') like '%"+ date+"' ";
		if (!region.equals("")) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		
		if (!airportcode.equals("")) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}
		if (!pensionno.equals("")) {
			whereClause.append(" PENSIONNO ='" + pensionno + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals("")) && (pensionno.equals(""))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = " ORDER BY " + sortingOrder + " ASC";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("FinancialDAO::buildQueryForm4 Leaving Method");
		return dynamicQuery;
	}
	public ArrayList getArrearsData(String pensionno) {
		String condition = "";
		ArrayList arrearslist = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		if (pensionno != "" && !pensionno.equals("")) {
			condition = "and pensionno=" + pensionno + "";
		}
		String arrearsQuery = "select pensionno,employeename,region,airportcode,cpfacno,to_char(dateofbirth,'dd-Mon-yyyy')as dateofbirth ,to_char(LAST_DAY((dateofbirth) + INTERVAL '58' year ) ,'dd-Mon-yyyy') AS DATEOFANNUATION from employee_personal_info where round(months_between(sysdate,dateofbirth),3)/12 >58 and to_date(add_months(dateofbirth, 697))>'01-Jan-2007' "
				+ condition + " order by pensionno";
		log.info(arrearsQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(arrearsQuery);
			while (rs.next()) {
				EmpMasterBean empbean = new EmpMasterBean();
				empbean.setPensionNumber(rs.getString("pensionno"));
				empbean.setEmpName(rs.getString("employeename"));
				empbean.setRegion(rs.getString("region"));
				empbean.setStation(rs.getString("airportcode"));
				empbean.setDateofBirth(rs.getString("dateofbirth"));
				empbean.setCpfAcNo(rs.getString("cpfacno"));
				empbean.setDateOfAnnuation(rs.getString("DATEOFANNUATION"));
				arrearslist.add(empbean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		return arrearslist;

	}
	//Don't Replace this method
	public ArrayList getArrearsTransactionData(String pensionno,
			String dateOfAnnuation,String flag) {
		String condition = "";
		ArrayList arrearslist = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		log.info("flag is "+ flag);
		if(pensionno!="" && flag.trim()=="12Months"){
			 condition="and monthyear between to_char(add_months(TO_DATE('"+dateOfAnnuation+"'), -12),'dd-Mon-yyyy') and '"+dateOfAnnuation+"'";
		}else if(pensionno!="" && flag.equals("")){
			 condition="and monthyear between '1-Jan-2007' and '"+dateOfAnnuation+"'";
		}
	
		String arrearsQuery = "select pensionno,emoluments,employeename,emppfstatuary,to_char(last_day(monthyear),'dd-Mon-yyyy') as monthyear,arrearamount,DUEEMOLUMENTS from employee_pension_validate where pensionno='"
				+ pensionno
				+ "' "+condition+"";

		log.info(arrearsQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(arrearsQuery);
			while (rs.next()) {
				ArrearsTransactionBean empbean = new ArrearsTransactionBean();
				empbean.setPensionNumber(rs.getString("pensionno"));
				empbean.setEmoluments(rs.getString("Emoluments"));
				empbean.setEmployeePF(rs.getString("emppfstatuary"));
				empbean.setEmpName(rs.getString("employeename"));
				empbean.setMonthYear(rs.getString("monthyear"));
				if (rs.getString("arrearamount") != null) {
					empbean.setArrearAmount(rs.getString("arrearamount"));
				} else {
					empbean.setArrearAmount("0");
				}
				if (rs.getString("DUEEMOLUMENTS") != null) {
					empbean.setDueEmoluments(rs.getString("DUEEMOLUMENTS"));
				} else {
					empbean.setDueEmoluments(new Long(Math.round(Double.parseDouble(empbean.getArrearAmount())*100/8.33)).toString());
				}
				arrearslist.add(empbean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}finally{
			commonDB.closeConnection(con,st,rs);
		}

		return arrearslist;

	}
	public void addArrearsData(String pensionno, String monthyear,
			String dueArrearamt,String dueEmoluments) {
		Connection con = null;
		Statement st = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			monthyear = commonUtil.converDBToAppFormat(monthyear.trim(),
					"dd-MMM-yyyy", "-MMM-yyyy");
			String addArrears = "update employee_pension_validate set arrearamount='"
					+ dueArrearamt
					+ "',dueemoluments='"+dueEmoluments+"' where pensionno='"
					+ pensionno
					+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
					+ monthyear + "' AND EMPFLAG='Y'";
			st.executeUpdate(addArrears);
			log.info("FinancialDAO:addArrearsData"+addArrears);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con,st,null);
		}
	}
	public void updateArearData(String pensionno, String arreartotal,
			String arreardate) {
		Connection con = null;
		Statement st = null;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			double emoluments = Math
					.round(Double.parseDouble(arreartotal) * 100 / 8.33);
			double emppfstatuary=Math.round(emoluments* 12/100);
			arreardate = commonUtil.converDBToAppFormat(arreardate.trim(),
					"dd/MMM/yyyy", "-MMM-yyyy");
			String updatearrears = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
					+ emoluments
					+ "'))  ,EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
					+ emppfstatuary
					+ "')) where pensionno='"
					+ pensionno
					+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
					+ arreardate + "' AND EMPFLAG='Y'";
			log.info("FinancialDAO::updateArearData"+updatearrears);
			st.executeUpdate(updatearrears);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(con,st,null);
		}
	}
	public ArrayList getArrearsInfo(String pensionno){
		Connection con = null;
		Statement st = null;
		ResultSet rs=null;
		ArrayList arrearsinfo = new ArrayList();
		String arrearDateQuery="Select ARREARAMT,to_char(ARREARDATE,'dd-Mon-yyyy') as arrearMonth from employee_pension_arrear where  PENSIONNO='"+pensionno+"' and arreardate>'01-Jan-2007'";
		log.info("arrearDateQuery" +arrearDateQuery);
		try{
			con = commonDB.getConnection();
		st = con.createStatement();
		rs=st.executeQuery(arrearDateQuery);
		
		while (rs.next()) {
			ArrearsTransactionBean empbean = new ArrearsTransactionBean();
			double arrearAmount=0.00;
			if(rs.getString("ARREARAMT")!=null){
			arrearAmount=Math.round(Double.parseDouble(rs.getString("ARREARAMT"))*100/12);
			empbean.setEmolumentsArrear(String.valueOf(arrearAmount));
			}else{
				empbean.setEmolumentsArrear("0.00");	
			}
			if(rs.getString("arrearMonth")!=null){
			empbean.setArrearMonth(rs.getString("arrearMonth"));
			}else{
				empbean.setArrearMonth("");
			}
			arrearsinfo.add(empbean);
		}
		}catch(Exception e){
			
		}
		
		return arrearsinfo;
	}
}