package com.epis.dao.rpfc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;



import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.NomineeBean;
import com.epis.services.rpfc.PersonalService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.DateValidation;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ImportDao {
	Log log = new Log(ImportDao.class);

	 DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();

	PensionDAO PDAO = new PensionDAO();

	PersonalDAO PersonalDAO = new PersonalDAO();
	FinancialReportDAO fDao = new FinancialReportDAO();

	CommonDAO commonDAO = new CommonDAO();

	public void readXLSData(String xlsData) {
		log.info("ImportDao :readXLSData() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		String xlsWhetherOption = "";
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		for (int i = 1; i < xlsDataList.size(); i++) {

			bean = new EmpMasterBean();
			tempData = xlsDataList.get(i).toString();
			tempInfo = tempData.split("@");
			try {
				if (!tempInfo[0].equals("XXX")) {
					bean.setSrno(tempInfo[0]);
				} else {
					bean.setSrno("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setAirportSerialNumber(tempInfo[1]);
				} else {
					bean.setAirportSerialNumber("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[3].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}

				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				if (!tempInfo[4].equals("XXX")) {
					if (tempInfo[4].trim().indexOf("'") != -1) {
						delimiter = "'";
						xlsDesignation = StringUtility.replace(
								tempInfo[4].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setDesegnation(xlsDesignation);
					} else {
						bean.setDesegnation(tempInfo[4].trim());
					}
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpLevel(tempInfo[5].trim());
				} else {
					bean.setEmpLevel("");
				}
				if (!tempInfo[6].equals("XXX")) {
					log.info("tempInfo[6]" + tempInfo[6]);
					bean.setCpfAcNo(tempInfo[6].trim());
				} else {
					bean.setCpfAcNo(PDAO.getMaxValue());
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].trim().indexOf("'") != -1) {
						int index = tempInfo[7].trim().indexOf("'");
						tempInfo[7] = tempInfo[7].trim().substring(index + 1,
								tempInfo[7].length());
					}
					bean.setDateofBirth(tempInfo[7].trim());
				} else {
					bean.setDateofBirth("");
				}
				if (!tempInfo[8].equals("XXX")) {
					if (tempInfo[8].trim().indexOf("'") != -1) {
						int index = tempInfo[8].trim().indexOf("'");
						tempInfo[8] = tempInfo[8].trim().substring(index + 1,
								tempInfo[8].length());
					}
					bean.setDateofJoining(tempInfo[8].toString().trim());

				} else {
					bean.setDateofJoining("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setSeperationReason(tempInfo[9]);
				} else {
					bean.setSeperationReason("");
				}
				if (!tempInfo[10].equals("XXX") && tempInfo[10].length() > 8) {
					if (tempInfo[10].trim().indexOf("'") != -1) {
						int index = tempInfo[10].trim().indexOf("'");
						tempInfo[10] = tempInfo[10].trim().substring(index + 1,
								tempInfo[10].length());
					}
					bean
							.setDateofSeperationDate(tempInfo[10].toString()
									.trim());
				} else {
					bean.setDateofSeperationDate("");
				}

				if (!tempInfo[11].equals("XXX")) {
					if (tempInfo[11].indexOf("*") != -1) {
						delimiter = "*";
						xlsWhetherOption = StringUtility.replace(
								tempInfo[11].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setWhetherOptionA(xlsWhetherOption);
					} else {
						bean.setWhetherOptionA(tempInfo[11]);
					}
				} else {
					bean.setWhetherOptionA("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setWhetherOptionB(tempInfo[12]);
				} else {
					bean.setWhetherOptionB("");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setWhetherOptionNO(tempInfo[13]);
				} else {
					bean.setWhetherOptionNO("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setForm2Nomination(tempInfo[14]);
				} else {
					bean.setForm2Nomination("");
				}
				if (!tempInfo[16].equals("XXX")) {
					bean.setRemarks(tempInfo[16]);
				} else {
					bean.setRemarks("");
				}
				if (!tempInfo[17].equals("XXX")) {
					bean.setStation(tempInfo[17].trim());
				} else {
					bean.setStation("");
				}
				if (!bean.getWhetherOptionA().trim().equals("")) {
					bean.setWetherOption("A");
				} else if (!bean.getWhetherOptionB().trim().equals("")) {
					bean.setWetherOption("B");
				} else if (!bean.getWhetherOptionNO().trim().equals("")) {
					bean.setWetherOption("No");
				} else {
					bean.setWetherOption("");
				}

				if (!bean.getEmpName().equals("")
						&& !bean.getDesegnation().equals("")) {
					PDAO.addPensionRecord(bean);
				}

			} catch (InvalidDataException e) {
				log.printStackTrace(e);
				try {
					fw.write(tempData + "***");
					fw.flush();
				} catch (FileNotFoundException fe) {
					log.printStackTrace(fe);
					// System.err.println("FileStreamsTest: " + fe);
				} catch (IOException io) {
					// System.err.println("FileStreamsTest: " + io);
					log.printStackTrace(io);
				}
			}
		}
		log.info("ImportDao :readXLSData() leaving method");
		pensionList.add(bean);

	}

	public void igiCpfaccnoUpdate(String xlsData) throws InvalidDataException {

		log.info("ImportDao :igiCpfaccnoUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setStation(tempInfo[3].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setNewCpfAcNo(tempInfo[4].trim());
				} else {
					bean.setNewCpfAcNo("");
				}

				String sql3 = "";
				if (bean.getCpfAcNo() != "") {
					sql3 = "update employee_pension_validate set cpfaccno='"
							+ bean.getNewCpfAcNo().trim()
							+ "', REMARKS=REMARKS||'CPF CHAGED TO D-00' where region='CHQIAD' and airportcode='"
							+ bean.getStation() + "' and cpfaccno='"
							+ bean.getCpfAcNo() + "'";
					log.info(sql3);
					st.execute(sql3);
				}
				// st.execute(sql3);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :igiCpfaccnoUpdate leaving method");

	}

	public void updateMasterNmtoTrans(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :updateMasterNmtoTrans() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		String tempName = "", sql3 = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setRegion(tempInfo[3].trim());
				} else {
					bean.setRegion("");
				}

				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}

				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					sql3 = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='" + bean.getCpfAcNo() + "' ";
					log.info(sql3);
					// st.execute(sql3);
				} else if (bean.getCpfAcNo() != "") {
					sql3 = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='" + bean.getCpfAcNo() + "'  ";
				} else {
					sql3 = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and employeeno='" + bean.getEmpNumber() + "'";
				}
				st.execute(sql3);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :updateMasterNmtoTrans leaving method");

	}

	public String[] readLables(String xlsData) throws Exception {

		log.info("ImportDao :readLables() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :deleteTransactions leaving method");
		return tempInfo;
	}

	public void deleteTransactions(String xlsData) throws InvalidDataException {

		log.info("ImportDao :deleteTransactions() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("hedername" + tempInfo[1].trim());
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmpNumber(tempInfo[0].trim());
				} else {
					bean.setEmpNumber("");
				}
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				String deleteTransaction = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					deleteTransaction = "delete from  employee_pension_validate  where region='East Region' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ monthYear + "'";
					log.info(deleteTransaction);
					// st.execute(sql3);
				}
				st.execute(deleteTransaction);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :deleteTransactions leaving method");

	}

	public void deleteDuplicateRecorsRausap(String xlsData, String userName,
			String ipAddress) throws InvalidDataException {

		log.info("ImportDao :deleteDuplicateRecorsRausap() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setStation(tempInfo[6].trim());
				} else {
					bean.setStation("");
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setRegion(tempInfo[7].trim());
				} else {
					bean.setRegion("");
				}

				String sql = "";
				if (bean.getCpfAcNo() != "") {
					sql = "update employee_info set empflag='N',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='" + bean.getRegion()
							+ "'  AND airportcode='" + bean.getStation()
							+ "' and cpfacno='" + bean.getCpfAcNo() + "' ";
					log.info(sql);
					// st.execute(sql3);
				}
				st.execute(sql);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :deleteDuplicateRecorsRausap leaving method");

	}

	public void processinPersonalInfoMissingRecords(String xlsData)
			throws InvalidDataException {

		log
				.info("ImportDao :processinPersonalInfoMissingRecords() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setRegion(tempInfo[1].trim());
				} else {
					bean.setRegion("");
				}
				PersonalDAO pDAO = new PersonalDAO();
				pDAO.processinPersonalInfoMissingRecords(bean.getCpfAcNo(),
						bean.getRegion());
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log
				.info("ImportDao :processinPersonalInfoMissingRecords leaving method");

	}

	public void deleteFinaceDuplicateData(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :deleteFinaceDuplicateData() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int count = 0, totalCount = 0;
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setStation(tempInfo[14].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setRegion(tempInfo[15].trim());
				} else {
					bean.setRegion("");
				}

				String sql3 = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					sql3 = "update employee_pension_validate set empflag='N' where region='"
							+ bean.getRegion().trim()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "' and airportcode='"
							+ bean.getStation()
							+ "' AND  TO_CHAR(MONTHYEAR,'dd-Mon-yyyy') like '%-Sep-2007'";

				} else {
					sql3 = "update employee_pension_validate set empflag='N' where region='"
							+ bean.getRegion().trim()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and employeeno is null and airportcode='"
							+ bean.getStation()
							+ "' AND  TO_CHAR(MONTHYEAR,'dd-Mon-yyyy') like '%-Sep-2007'";

				}
				log.info("Query is" + sql3);
				count = st.executeUpdate(sql3);
				totalCount = totalCount + count;
				log.info("Total No.of Records is " + totalCount + "coutn is"
						+ count);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :updateMasterNmtoTrans leaving method");

	}

	public void readXLSWestData(String xlsData) throws InvalidDataException {
		log.info("ImportDao :readXLSWestData() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0]);
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());

				if (!tempInfo[2].equals("XXX")) {
					if (tempInfo[2].trim().indexOf("'") != -1) {
						delimiter = "'";
						xlsDesignation = StringUtility.replace(
								tempInfo[2].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setDesegnation(xlsDesignation);

					} else {
						bean.setDesegnation(tempInfo[2].trim());
					}

				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setFhName(tempInfo[3].trim());
				} else {
					bean.setFhName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					if (tempInfo[4].trim().indexOf("'") != -1) {
						int index = tempInfo[4].trim().indexOf("'");
						tempInfo[4] = tempInfo[4].trim().substring(index + 1,
								tempInfo[4].length());
					}
					bean.setDateofBirth(tempInfo[4].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setSex(tempInfo[5].trim());
				} else {
					bean.setSex("");
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setSeperationReason(tempInfo[7].trim());
				} else {
					bean.setSeperationReason("");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setDateofSeperationDate(tempInfo[8].trim());
				} else {
					bean.setDateofSeperationDate("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setRemarks(tempInfo[9].trim());
				} else {
					bean.setRemarks("");
				}

				String dtOfJoining = "";

				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].trim().indexOf("'") != -1) {
						int index = tempInfo[6].trim().indexOf("'");
						tempInfo[6] = tempInfo[6].trim().substring(index + 1,
								tempInfo[6].length());
					}
					bean.setDateofJoining(tempInfo[6].trim());

				} else {
					bean.setDateofJoining("");
				}
				String dtOfApt = "";
				/*
				 * if (!tempInfo[7].equals("XXX")) { if
				 * (tempInfo[7].trim().indexOf("'") != -1) { int index =
				 * tempInfo[7].trim().indexOf("'"); tempInfo[7] =
				 * tempInfo[7].trim().substring(index + 1,
				 * tempInfo[7].length()); }
				 * 
				 * dtOfApt = commonUtil.converDBToAppFormat( tempInfo[7].trim(),
				 * "dd/MM/yyyy", "dd-MMM-yyyy"); bean.setDateofApt(dtOfApt); }
				 * else { bean.setDateofApt(""); }
				 * 
				 * if (!tempInfo[9].equals("XXX")) { log.info("temp9 is " +
				 * tempInfo[9]); bean.setStation(tempInfo[9].trim()); } else {
				 * bean.setStation(""); }
				 */
							

				String sql3 = "update employee_info set DATEOFJOINING='"
						+ bean.getDateofJoining()
						+ "' where region='West Region' and cpfacno='"
						+ bean.getCpfAcNo() + "' and DATEOFJOINING is null";
				log.info(sql3);
				st.execute(sql3);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :readXLSWestData leaving method");
	}

	public void readXLSEastPersonalData(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :readXLSEastData() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		String xlsWhetherOption = "";
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };

		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0]);
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}

				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmpName());
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());

				if (!tempInfo[3].equals("XXX")) {
					if (tempInfo[3].trim().indexOf("'") != -1) {
						delimiter = "'";
						xlsDesignation = StringUtility.replace(
								tempInfo[3].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setDesegnation(xlsDesignation);

					} else {
						bean.setDesegnation(tempInfo[3].trim());
					}
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setSex(tempInfo[5].trim());
				} else {
					bean.setSex("");
				}
				if (!tempInfo[4].equals("XXX")) {
					if (tempInfo[4].trim().indexOf("'") != -1) {
						int index = tempInfo[4].trim().indexOf("'");
						tempInfo[4] = tempInfo[4].trim().substring(index + 1,
								tempInfo[4].length());
					}
					bean.setDateofBirth(tempInfo[4].trim());
				} else {
					bean.setDateofBirth("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].trim().indexOf("'") != -1) {
						int index = tempInfo[6].trim().indexOf("'");
						tempInfo[6] = tempInfo[6].trim().substring(index + 1,
								tempInfo[6].length());
					}
					bean.setDateofJoining(tempInfo[6].trim());
				} else {
					bean.setDateofJoining("");
				}
				log.info("DateofBirth" + bean.getDateofBirth()
						+ "DateofJoining" + bean.getDateofJoining());
				if (!tempInfo[7].equals("XXX")) {
					log.info("temp7 is " + tempInfo[7]);
					bean.setStation(tempInfo[7].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[8].equals("XXX")) {
					log.info("temp8 is " + tempInfo[8]);
					bean.setFhName(tempInfo[8].trim());
				} else {
					bean.setFhName("");
				}
				log.info("before add pensionRecord empname" + bean.getEmpName()
						+ " cpfacno " + bean.getCpfAcNo() + "desegnation "
						+ bean.getDesegnation() + "dateofbirth"
						+ bean.getDateofBirth() + "station "
						+ bean.getStation() + "fhName " + bean.getFhName());
				String region = "East Region".trim();
				String sql = "insert into employee_westinfo(cpfacno,employeeName,"
						+ "desegnation,dateofbirth,dateofjoining,dateofApt,airportcode,"
						+ "sex,fhname,region,employeeno)" + " VALUES " + "('"
						+ bean.getCpfAcNo().trim()
						+ "','"
						+ bean.getEmpName().trim()
						+ "','"
						+ bean.getDesegnation()
						+ "','"
						+ bean.getDateofBirth()
						+ "','"
						+ bean.getDateofJoining()
						+ "','"
						+ bean.getDateofApt()
						+ "','"
						+ bean.getStation()
						+ "','"
						+ bean.getSex()
						+ "','"
						+ bean.getFhName()
						+ "', '" + region + "','" + bean.getEmpNumber() + "')";
				log.info(sql);
				st.execute(sql);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("ImportDao :readXLSEastData leaving method");
	}

	public void readXLSChqIadData(String xlsData) throws InvalidDataException {
		log.info("ImportDao :readXLSChqIadData() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResourcess");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmpNumber(tempInfo[0]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1]);
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				if (!tempInfo[3].equals("XXX")) {
					if (tempInfo[3].trim().indexOf("'") != -1) {
						delimiter = "'";
						xlsDesignation = StringUtility.replace(
								tempInfo[3].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setDesegnation(xlsDesignation);

					} else {
						bean.setDesegnation(tempInfo[3].trim());
					}
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					//bean.setDateofBirth(commonUtil.converDBToAppFormat(tempInfo
					// [4].trim(),
					// "dd/MM/yyyy","dd-MMM-yyyy"));
					bean.setDateofBirth(tempInfo[4].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[5].equals("XXX")) {
					// bean.setDateofJoining(commonUtil.converDBToAppFormat(
					// tempInfo[5].trim(),
					// "dd/MM/yyyy","dd-MMM-yyyy"));
					bean.setDateofJoining(tempInfo[5].trim());
				} else {
					bean.setDateofJoining("");
				}
				log.info("before add pensionRecord empname" + bean.getEmpName()
						+ " cpfacno " + bean.getCpfAcNo() + "desegnation "
						+ bean.getDesegnation());
				if (!tempInfo[6].equals("XXX")) {
					bean.setSex(tempInfo[6]);
				} else {
					bean.setSex("");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setFhName(tempInfo[7]);
				} else {
					bean.setFhName("");
				}

				/*
				 * if (!tempInfo[0].equals("XXX")) {
				 * bean.setEmpNumber(tempInfo[0]); } else {
				 * bean.setEmpNumber(""); }
				 * 
				 * if (!tempInfo[1].equals("XXX")) {
				 * bean.setEmpLevel(tempInfo[1]); } else { bean.setEmpLevel("");
				 * } if (!tempInfo[2].equals("XXX")) {
				 * bean.setFhName(tempInfo[2]); } else { bean.setFhName(""); }
				 * if (!tempInfo[3].equals("XXX")) { bean.setSex(tempInfo[3]); }
				 * else { bean.setSex(""); }
				 */

				String station = "OFFICE COMPLEX";
				String region = "CHQIAD";
				String pensionNumber = "";
				int countEmployeeInfo = 0;
				// String updateSql="update employee_pension_validate set
				// employeename=
				// '"+bean.getEmpName().trim()+"',cpfaccno='"+bean.getCpfAcNo().trim()+"',desegnation
				// ='"+bean.getDesegnation()+"' where cpfaccno is null and
				// employeename is null and region='CHQNAD' and
				// employeeno='"+bean.getEmpNumber().trim()+"'";

				// String updateSql="update employee_pension_validate set
				// employeename=
				// '"+bean.getEmpName().trim()+"',cpfaccno='"+bean.getCpfAcNo().trim()+"',desegnation
				// ='"+bean.getDesegnation()+"' where cpfaccno is null and
				// employeename is not null and region='CHQNAD' and
				// employeeno='"+bean.getEmpNumber().trim()+"'";
				// log.info("update sql "+updateSql);
				// st.executeUpdate(updateSql);
				FinancialDAO fDAO = new FinancialDAO();
				// for new update of sex and fhname , emplevel
				// countEmployeeInfo= fDAO.checkEmployeeInfo(conn ,
				// bean.getEmpNumber(),bean.getCpfAcNo(),bean.getEmpNumber(),
				// "RAUSAP");
				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(),
						bean.getEmpNumber(), "CHQIAD");
				// st.executeUpdate(updateSql);
				if (countEmployeeInfo == 0) {
					if (bean.getDateofBirth() != "") {
						// pensionNumber=this.getPensionNumberError(bean.
						// getEmpName
						// ().trim(),bean.getDateofBirth().trim(),bean.
						// getCpfAcNo().trim(),"");
						// pensionNumber=
						//this.checkPensionNumber(pensionNumber,bean.getCpfAcNo(
						// )
						// .trim(),bean.getEmpNumber(),bean.getEmpName().trim(),
						// "CHQNAD",bean.getDateofBirth().trim());
						pensionNumber = commonDAO.getPensionNumber(bean
								.getEmpName(), bean.getDateofBirth(), bean
								.getCpfAcNo(), true);

						// }
						String sql = "insert into employee_info(cpfacno,employeeno,employeeName,"
								+ "desegnation,airportcode,"
								+ "region,dateofbirth,dateofjoining,pensionNumber,sex,fhname)"
								+ " VALUES " + "('"
								+ bean.getCpfAcNo().trim()
								+ "','"
								+ bean.getEmpNumber().trim()
								+ "','"
								+ bean.getEmpName().trim()
								+ "','"
								+ bean.getDesegnation()
								+ "','"
								+ station
								+ "','"
								+ region
								+ "','"
								+ bean.getDateofBirth()
								+ "','"
								+ bean.getDateofJoining()
								+ "','"
								+ pensionNumber.toUpperCase()
								+ "','"
								+ bean.getSex()
								+ "','"
								+ bean.getFhName()
								+ "')";
						log.info(sql);
						// String updateMastersql="update employee_info set
						// fhname=
						// '"+bean.getFhName()+"',sex='"+bean.getSex()+"',dateofbirth='"+bean.getDateofBirth()+"',dateofjoining='"+bean.getDateofJoining()+"',
						// pensionNumber='"+pensionNumber+"', WETHEROPTION
						// ='"+bean.getWetherOption()+"' where
						// cpfacno='"+bean.getCpfAcNo()+"' and
						// employeeno='"+bean.getEmpNumber()+"' and
						// region='CHQNAD'";

						// String updateSql="update employee_pension_validate
						// set
						// employeename=
						// '"+bean.getEmpName().trim()+"',cpfaccno='"+bean.getCpfAcNo().trim()+"',desegnation
						// ='"+bean.getDesegnation()+"' where cpfaccno is null
						// and employeename is null and region='CHQNAD' and
						// employeeno='"+bean.getEmpNumber().trim()+"'";

						// String updateSql="update employee_pension_validate
						// set
						// employeename=
						// '"+bean.getEmpName().trim()+"',cpfaccno='"+bean.getCpfAcNo().trim()+"',desegnation
						// ='"+bean.getDesegnation()+"' where cpfaccno is null
						// and employeename is not null and region='CHQNAD' and
						// employeeno='"+bean.getEmpNumber().trim()+"'";
						log.info("update sql " + sql);

						st.execute(sql);
						log.info("record update");
					}
				}
			}
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		log.info("ImportDao :readXLSChqIadData leaving method");
	}

	public void readXLSChqNadData(String xlsData) throws Exception {
		log.info("ImportDao :readXLSChqNadData() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "", tempName = "";
		;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmpNumber(tempInfo[0]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1]);
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}

				for (int j = 0; j < quats.length; j++) {
					// log.info("getName " + bean.getEmpName());
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				if (!tempInfo[3].equals("XXX")) {
					if (tempInfo[3].trim().indexOf("'") != -1) {
						delimiter = "'";
						xlsDesignation = StringUtility.replace(
								tempInfo[3].trim().toCharArray(),
								delimiter.toCharArray(), "").toString();
						bean.setDesegnation(xlsDesignation);

					} else {
						bean.setDesegnation(tempInfo[3].trim());
					}
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDateofBirth(commonUtil.converDBToAppFormat(
							tempInfo[4].trim(), "dd/MM/yyyy", "dd-MMM-yyyy"));
					// bean.setDateofBirth(tempInfo[4].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setDateofJoining(commonUtil.converDBToAppFormat(
							tempInfo[5].trim(), "dd/MM/yyyy", "dd-MMM-yyyy"));
					// bean.setDateofJoining(tempInfo[5].trim());
				} else {
					bean.setDateofJoining("");
				}

				String station = "CHQNAD";
				String region = "CHQNAD";
				String pensionNumber = "";
				int countEmployeeInfo = 0;

				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(),
						bean.getEmpNumber(), "CHQNAD");
				// st.executeUpdate(updateSql);
				log.info("date of birth " + bean.getDateofBirth() + "doj  "
						+ bean.getDateofJoining());
				// if (bean.getDateofBirth() != "") {
				// pensionNumber = commonDAO.getPensionNumber(bean.getEmpName(),
				// bean.getDateofBirth(), bean.getCpfAcNo(), true);

				if (countEmployeeInfo == 0) {
					// }
					String sql = "insert into employee_info(cpfacno,employeeno,employeeName,"
							+ "desegnation,airportcode,"
							+ "region,dateofbirth,dateofjoining,pensionNumber,sex,fhname)"
							+ " VALUES " + "('"
							+ bean.getCpfAcNo().trim()
							+ "','"
							+ bean.getEmpNumber().trim()
							+ "','"
							+ bean.getEmpName().trim()
							+ "','"
							+ bean.getDesegnation()
							+ "','"
							+ station
							+ "','"
							+ region
							+ "','"
							+ bean.getDateofBirth()
							+ "','"
							+ bean.getDateofJoining()
							+ "','"
							+ pensionNumber.toUpperCase()
							+ "','"
							+ bean.getSex() + "','" + bean.getFhName() + "')";
					log.info(sql);

					String updateTrasaction = "update employee_pension_validate set MASTER_EMPNAME='"
							+ bean.getEmpName().trim()
							+ "' where cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and  region='CHQNAD'";
					/*
					 * String updateMastersql = "update employee_info set
					 * dateofbirth='" + bean.getDateofBirth() +
					 * "',dateofjoining='" + bean.getDateofJoining() +
					 * "',pensionNumber='" + pensionNumber + "'" + " where
					 * cpfacno='" + bean.getCpfAcNo() + "' and employeeno='" +
					 * bean.getEmpNumber() + "' and region='CHQNAD'";
					 * log.info("update sql " + sql);
					 */
					st.execute(updateTrasaction);
					log.info("updateTrasaction " + updateTrasaction);
					st.execute(sql);
					log.info("record inserted , transaction updated");
				} else {
					String updateMastersql = "update employee_info set dateofbirth='"
							+ bean.getDateofBirth()
							+ "',dateofjoining='"
							+ bean.getDateofJoining()
							+ "',pensionNumber='"
							+ pensionNumber
							+ "',employeeno='"
							+ bean.getEmpNumber()
							+ "',employeename ='"
							+ bean.getEmpName().trim()
							+ "'"
							+ "where   cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and  region='CHQNAD'";
					log.info("record updated " + updateMastersql);
					st.execute(updateMastersql);
				}

			}

			// }
		} catch (Exception e) {
			log.printStackTrace(e);

		} finally {
			conn.close();
		}
		log.info("ImportDao :readXLSChqIadData leaving method");
	}

	public void readXLSChqNadData1(String xlsData) throws InvalidDataException {
		log.info("ImportDao :readXLSChqNadData1() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int count = 0;
			for (int i = 1; i < xlsDataList.size(); i++) {
				count++;
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0]);
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					// log.info("getName " + bean.getEmpName());
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmpName(tempName);
					}
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setDesegnation(tempInfo[2]);
				} else {
					bean.setDesegnation("");
				}

				String station = "CHQNAD";
				String region = "CHQNAD";
				String pensionNumber = "";
				int countEmployeeInfo = 0;

				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(),
						bean.getEmpNumber(), "CHQNAD");
				// st.executeUpdate(updateSql);
				log.info("date of birth " + bean.getDateofBirth() + "doj  "
						+ bean.getDateofJoining());
				// if (bean.getDateofBirth() != "") {
				// pensionNumber = commonDAO.getPensionNumber(bean.getEmpName(),
				// bean.getDateofBirth(), bean.getCpfAcNo(), true);

				if (countEmployeeInfo == 0) {
					// }
					String sql = "insert into employee_info(cpfacno,employeeno,employeeName,"
							+ "desegnation,airportcode,"
							+ "region,dateofbirth,dateofjoining,pensionNumber,sex,fhname)"
							+ " VALUES " + "('"
							+ bean.getCpfAcNo().trim()
							+ "','"
							+ bean.getEmpNumber().trim()
							+ "','"
							+ bean.getEmpName().trim()
							+ "','"
							+ bean.getDesegnation()
							+ "','"
							+ station
							+ "','"
							+ region
							+ "','"
							+ bean.getDateofBirth()
							+ "','"
							+ bean.getDateofJoining()
							+ "','"
							+ pensionNumber.toUpperCase()
							+ "','"
							+ bean.getSex() + "','" + bean.getFhName() + "')";
					log.info(sql);

					String updateTrasaction = "update employee_pension_validate set MASTER_EMPNAME='"
							+ bean.getEmpName().trim().toUpperCase()
							+ "' where cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and  region='CHQNAD'";
					/*
					 * String updateMastersql = "update employee_info set
					 * dateofbirth='" + bean.getDateofBirth() +
					 * "',dateofjoining='" + bean.getDateofJoining() +
					 * "',pensionNumber='" + pensionNumber + "'" + " where
					 * cpfacno='" + bean.getCpfAcNo() + "' and employeeno='" +
					 * bean.getEmpNumber() + "' and region='CHQNAD'";
					 * log.info("update sql " + sql);
					 */
					st.execute(updateTrasaction);
					log.info("updateTrasaction " + updateTrasaction);
					st.execute(sql);
					log.info("record inserted , transaction updated");
				} else {
					String updateMastersql = "update employee_info set employeename ='"
							+ bean.getEmpName().trim().toUpperCase()
							+ "',desegnation='"
							+ bean.getDesegnation()
							+ "'"
							+ "where   cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and  region='CHQNAD'";
					String updateTrasaction = "update employee_pension_validate set MASTER_EMPNAME='"
							+ bean.getEmpName().trim().toUpperCase()
							+ "' where cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and  region='CHQNAD'";

					log.info("record updated " + updateMastersql);
					st.execute(updateTrasaction);
					log.info("updateTrasaction " + updateTrasaction);
					st.execute(updateMastersql);
					log.info("count " + count);
				}

			}

			// }
		} catch (Exception e) {
			log.printStackTrace(e);

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		log.info("ImportDao :readXLSChqNadData1 leaving method");
	}

	public void readXLSSouthFinalcialData(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSSouthFinalcialData() entering method");
		log.info("readXLSData");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", cpfInterest = "";
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		int countEmployeeInfo = 0;
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[4].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[4].equals("XXX")) {
					String empName = tempInfo[4];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[7].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}
				String salary = "";
				if (!tempInfo[8].equals("XXX")) {
					salary = tempInfo[8].trim();
				} else {
					salary = "0.00";
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setCpfArriers(tempInfo[10].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				/*
				 * Flag "Y" Should be edit the basic or dailyallowanc values Flag
				 * "N" The values are available
				 */

				log.info("basic " + bean.getBasic() + "dailyAllowance "
						+ bean.getDailyAllowance() + "salary  " + salary
						+ "cpfarrear " + bean.getCpfArriers());
				DateFormat df = new SimpleDateFormat("dd-MMM-yy");

				Date transdate = df.parse(bean.getMonthYear());
				float cpfarrear = 0;
				/*
				 * if (transdate.after(new Date("31-Mar-98"))) { cpfarrear =
				 * Float.parseFloat(bean.getCpfArriers().trim()) 100 / 12; }
				 * else { cpfarrear =
				 * Float.parseFloat(bean.getCpfArriers().trim()) 100 / 10; }
				 */
				String emoluments = String.valueOf(Float.parseFloat(salary)
						+ cpfarrear);

				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);
				String cpf = "";
				if (!tempInfo[9].equals("XXX")) {
					cpf = tempInfo[9].trim();
				} else {
					cpf = "0.00";
				}

				bean.setEmpPfStatuary(String.valueOf(Float.parseFloat(bean
						.getCpfArriers())
						+ Float.parseFloat(cpf)));

				log.info("pfstatutory " + bean.getEmpPfStatuary());
				if (!tempInfo[12].equals("XXX")) {
					bean.setEmpVpf(tempInfo[12]);
				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setPfAdvance(tempInfo[14].trim());
				} else {
					bean.setPfAdvance("0.00");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setCpfInterest(tempInfo[15].trim());
				} else {
					bean.setCpfInterest("0.00");
				}

				if (!tempInfo[3].equals("XXX")) {
					bean.setAirportCode(tempInfo[3].trim());
				} else {
					bean.setAirportCode("");
				}

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "South Region");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getAirportCode());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "South Region");
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,DAILYALLOWANCE,PFADVANCE,employeeno,DESEGNATION,cpfarrear,region,cpf)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + bean.getEmployeeName()
						+ "Designation" + bean.getDesignation() + "CpfAccNo "
						+ bean.getCpfAccNo().trim() + "airport code "
						+ bean.getAirportCode());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "South Region");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, bean.getAirportCode());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getDailyAllowance());
					pst.setString(12, bean.getPfAdvance());
					pst.setString(13, bean.getEmployeeNewNo());
					pst.setString(14, bean.getDesignation());
					pst.setString(15, bean.getCpfArriers());
					pst.setString(16, "South Region");
					pst.setString(17, cpf);
					try {
						pst.executeUpdate();

					} catch (Exception e) {
						log.printStackTrace(e);
					}
					// count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.0")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getBasic()
								+ "')), DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
								+ bean.getDailyAllowance()
								+ "')) ,SPECIALBASIC=to_char(TO_NUMBER(nvl(SPECIALBASIC,0)+'"
								+ bean.getSpecialBasic()
								+ "')), emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='South Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					} else {
						float pfstaturoty = Float.parseFloat(bean
								.getEmpPfStatuary().trim()) * 100 / 10;
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ cpfarrear
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getCpfArriers().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='South Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}

				// this.addFinancialDataRecord(bean);

				log
						.info("FinaceDAO :readXLSSouthFinalcialData() end method========= count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	public void readXLSFinalcialData(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSEastFinalcialData() entering method");
		log.info("readXLSData");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", cpfInterest = "";
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		int countEmployeeInfo = 0;
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					if ((bean.getEmployeeNewNo() != "")
							|| (bean.getCpfAccNo() != "")) {
						employeeName = this.checkEmployeeMaster(con, bean
								.getEmployeeNewNo(), bean.getCpfAccNo());
						bean.setEmployeeName(employeeName);
					} else {
						bean.setEmployeeName("");
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[7].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setSpecialBasic(tempInfo[8].trim());
				} else {
					bean.setSpecialBasic("0.00");
				}
				/*
				 * Flag "Y" Should be edit the basic or dailallowanc values Flag
				 * "N" The values are available
				 */

				log.info("basic " + bean.getBasic() + "dailyAllowance "
						+ bean.getDailyAllowance() + "specialBasic  "
						+ bean.getSpecialBasic());

				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic().trim())
						+ Float.parseFloat(bean.getDailyAllowance())
						+ Float.parseFloat(bean.getSpecialBasic()));
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[9]);
				} else {
					bean.setEmpPfStatuary("0.00");
				}
				String cpf = "";
				if (!tempInfo[9].equals("XXX")) {
					cpf = tempInfo[9].trim();
				} else {
					cpf = "0.00";
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setEmpVpf(tempInfo[10]);
				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setPfAdvance(tempInfo[11].trim());
				} else {
					bean.setPfAdvance("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setAdvanceDrawn(tempInfo[12].trim());
				} else {
					bean.setAdvanceDrawn("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setPartFinal(tempInfo[13].trim());
				} else {
					bean.setPartFinal("0.00");
				}
				log.info("pfadvance " + bean.getPfAdvance() + "advanceDrawn"
						+ bean.getAdvanceDrawn() + "partFinal"
						+ bean.getPartFinal());
				float pfAdvance1 = Math.abs(Float.parseFloat(bean
						.getPfAdvance())
						- Float.parseFloat(bean.getAdvanceDrawn())
						- Float.parseFloat(bean.getPartFinal()));

				if (!tempInfo[14].equals("XXX")) {

					bean.setCpfInterest(tempInfo[14].trim());
				} else {
					bean.setCpfInterest("0.00");
				}
				log.info("pfstaturay " + bean.getEmpPfStatuary() + " vpf "
						+ bean.getEmpVpf() + "cpfInterest" + cpfInterest
						+ "pfAdvance1" + pfAdvance1);
				if (!tempInfo[15].equals("XXX")) {
					bean.setGpf(tempInfo[15].trim());
				} else {
					bean.setGpf("0.00");
				}
				if (!tempInfo[16].equals("XXX")) {
					bean.setRemarks(tempInfo[16].trim());
				} else {
					bean.setRemarks("");
				}

				// cpf Arriers for southern RegionData only
				/*
				 * if (!tempInfo[17].equals("XXX")) {
				 * 
				 * bean.setCpfArriers(tempInfo[17].trim()); } else {
				 * bean.setCpfArriers(""); }
				 */

				/* For Easter Region empTotal calculation */
				log.info(" " + bean.getEmpPfStatuary() + " " + bean.getEmpVpf()
						+ " " + cpfInterest + " " + pfAdvance1);

				float empTotal = Float.parseFloat(bean.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getCpfInterest()) + pfAdvance1;
				log.info("empTotal " + empTotal);

				/* For Southern Region */

				/*
				 * log.info("pfstatury " + bean.getEmpPfStatuary() + "vpf" +
				 * bean.getEmpVpf() + " PfAdvance " + bean.getPfAdvance() +
				 * "AdvanceDrawn" + bean.getAdvanceDrawn()); float empTotal =
				 * Float.parseFloat(bean.getEmpPfStatuary()) +
				 * Float.parseFloat(bean.getEmpVpf()) +
				 * Float.parseFloat(bean.getPfAdvance()) +
				 * Float.parseFloat(bean.getAdvanceDrawn());
				 */

				bean.setEmpTotal(String.valueOf(empTotal));
				bean.setAaiTotal(bean.getEmpTotal());

				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				// String
				//Dateformat=commonUtil.converDBToAppFormat(bean.getMonthYear(),
				// "dd-MMM-yyyy",
				// "dd-MM-yyyy");
				// String incrementDate=commonUtil.AddMonth(Dateformat);
				// bean.setMonthYear(incrementDate);

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "East Region");
				if (countEmployeeInfo == 0) {

					/*
					 * String sql = "insert into
					 * employee_info(cpfacno,airportCode,
					 * employeeno,employeeName,desegnation) VALUES " + "('" +
					 * bean.getCpfAccNo().trim() + "','" + bean.getAirportCode()
					 * + "','" + bean.getEmployeeNewNo() + "','" +
					 * tempInfo[3].toString().trim() + "','" +
					 * bean.getDesignation() + "'" + ")";
					 */
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getAirportCode());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "East Region");
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,DAILYALLOWANCE,SPECIALBASIC,PFADVANCE,ADVANCEDRWAN,PARTFINAL,"
						+ "CPFINTEREST,MISSINGFLAG,employeeno,DESEGNATION,gpf,remarks,cpfarrear,region,cpf)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo "
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region
				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "East Region");
				bean.setMonthYear("01-" + bean.getMonthYear());
				log.info(" dd" + bean.getMonthYear());
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, bean.getAirportCode());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getDailyAllowance());
					pst.setString(12, bean.getSpecialBasic());
					pst.setString(13, bean.getPfAdvance());
					pst.setString(14, bean.getAdvanceDrawn());
					pst.setString(15, bean.getPartFinal());
					pst.setString(16, bean.getCpfInterest());
					pst.setString(17, bean.getMissingFlag());
					pst.setString(18, bean.getEmployeeNewNo());
					pst.setString(19, bean.getDesignation());
					pst.setString(20, bean.getGpf());
					pst.setString(21, bean.getRemarks());
					pst.setString(22, bean.getCpfArriers());
					pst.setString(23, "East Region");
					pst.setString(24, cpf);
					try {
						pst.executeUpdate();

					} catch (Exception e) {
						log.printStackTrace(e);
					}
					// count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.0")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getBasic()
								+ "')), DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
								+ bean.getDailyAllowance()
								+ "')) ,SPECIALBASIC=to_char(TO_NUMBER(nvl(SPECIALBASIC,0)+'"
								+ bean.getSpecialBasic()
								+ "')), emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='East Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					} else {

						DateFormat df = new SimpleDateFormat("dd-MMM-yy");

						Date transdate = df.parse(bean.getMonthYear());
						float cpfarrear = 0, pfstaturoty = 0;
						if (transdate.after(new Date("31-Mar-98"))) {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 12;
						} else {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 10;
						}

						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ pfstaturoty
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='East Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}

				// this.addFinancialDataRecord(bean);

				log
						.info("FinaceDAO :readXLSEastFinalcialData() end method========= count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	public void readXLSFinalcialDataNRegion(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataNRegion() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "", dailyAllowance = "", specialBasic = "", cpfAccno = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;

			for (int i = 0; i < xlsDataList.size(); i++) {

				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
		
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					basic = tempInfo[6];
					bean.setBasic(basic);
				} else {
					basic = "0.00";
					bean.setBasic(basic);
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[7].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}

				// for Da_arriers
				if (!tempInfo[8].equals("XXX")) {
					bean.setSpecialBasic(tempInfo[8].trim());
				} else {
					bean.setSpecialBasic("0.00");
				}
				if (!tempInfo[18].equals("XXX")) {
					bean.setCpfArriers(tempInfo[18].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				log.info("basic " + basic + "dailyAllowance " + dailyAllowance
						+ "specialBasic  " + specialBasic);
				// logic chaged for 1995-1996 shheet Emoul. :- BASIC + DA +
				// DA+ARR + (CPF_ARR/10%)
				// CPF :- PF_CONT + ARR_CONT + CPF_ARR

				String emoluments = String.valueOf(Float.parseFloat(basic)
						+ Float.parseFloat(bean.getDailyAllowance())
						+ Float.parseFloat(bean.getSpecialBasic()));
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// for Pf_count
				String pfCount = "";
				if (!tempInfo[9].equals("XXX")) {
					pfCount = tempInfo[9];
				} else {
					pfCount = "0.00";
				}

				String arrCount = "";
				if (!tempInfo[10].equals("XXX")) {
					arrCount = tempInfo[10];
				} else {
					arrCount = "0.00";
				}

				log.info("pfCount " + pfCount + "arrCount " + arrCount
						+ "cpfarrears " + bean.getCpfArriers());
				String pfStatuary = String.valueOf(Float.parseFloat(pfCount)
						+ Float.parseFloat(arrCount));
				log.info("pfStatuary " + pfStatuary);
				bean.setEmpPfStatuary(pfStatuary);

				String optCpf = "";
				if (!tempInfo[11].equals("XXX")) {
					optCpf = tempInfo[11];
				} else {
					optCpf = "0.00";
				}
				String adDa = "";
				if (!tempInfo[12].equals("XXX")) {
					adDa = tempInfo[12];

				} else {
					adDa = "0.00";
				}
				String vpf = String.valueOf(Float.parseFloat(optCpf)
						+ Float.parseFloat(adDa));
				bean.setEmpVpf(vpf);

				String refAdv = "";
				if (!tempInfo[13].equals("XXX")) {
					refAdv = tempInfo[13];
				} else {
					refAdv = "0.00";
				}

				String dedAdv = "";
				if (!tempInfo[14].equals("XXX")) {
					dedAdv = tempInfo[14];

				} else {
					dedAdv = "0.00";
				}

				String principal = String.valueOf(Float.parseFloat(refAdv)
						- Float.parseFloat(dedAdv));

				bean.setPrincipal(principal);

				String empTotal = String.valueOf(Float.parseFloat(pfCount)
						+ Float.parseFloat(arrCount)
						+ Float.parseFloat(bean.getEmpVpf())
						+ Math.abs(Float.parseFloat(bean.getPrincipal())));
				bean.setEmpTotal(empTotal);
				String dedFemp = "";

				if (!tempInfo[15].equals("XXX")) {
					dedFemp = tempInfo[15];

				} else {
					dedFemp = "0.00";
				}

				String dedFnaa = "";
				if (!tempInfo[16].equals("XXX")) {
					dedFnaa = tempInfo[16];

				} else {
					dedFnaa = "0.00";

				}
				if (!tempInfo[17].equals("XXX")) {
					bean.setRemarks(tempInfo[17].trim());

				} else {
					bean.setRemarks("");
				}

				bean.setAaiTotal(bean.getEmpTotal());

				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}

				log.info("cpfaccno " + bean.getCpfAccNo());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = this.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "North Region");
				if (countEmployeeInfo == 0) {

					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getAirportCode());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "North Region");
					// pst.setString(7,String.valueOf(sequenceNumber));
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,DAILYALLOWANCE,DAARREAR,pfCount,arrCount,optCpf,"
						+ "adDa,refAdv,dedAdv,dedFemp,dedFnaa,employeeno,DESEGNATION,remarks,region,CPFARREAR)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "North Region");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, bean.getAirportCode());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getDailyAllowance());
					pst.setString(12, bean.getSpecialBasic());
					pst.setString(13, pfCount);
					pst.setString(14, arrCount);
					pst.setString(15, optCpf);
					pst.setString(16, adDa);
					pst.setString(17, refAdv);
					pst.setString(18, dedAdv);
					pst.setString(19, dedFemp);
					pst.setString(20, dedFnaa);
					pst.setString(21, bean.getEmployeeNewNo());
					pst.setString(22, bean.getDesignation());
					pst.setString(23, bean.getRemarks());
					pst.setString(24, "North Region");
					pst.setString(25, bean.getCpfArriers());
					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						basicDaSum = Float.parseFloat(bean.getBasic())
								+ Float.parseFloat(bean.getDailyAllowance()
										+ Float.parseFloat(bean
												.getSpecialBasic()));
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getBasic()
								+ "')), DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
								+ bean.getDailyAllowance()
								+ "')) , emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ basicDaSum
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='North Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and  monthyear='"
								+ bean.getMonthYear()
								+ "'";

					}
					// this.addFinancialDataRecord(bean);

					log
							.info("FinaceDAO :readXLSFinalcialDataNRegion() end method count"
									+ count);
				}
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	public void readXLSFinalcialDataWRegion(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataWRegion() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "", dailyAllowance = "", specialBasic = "", cpfAccno = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
					if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
					if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[7].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}
				log.info("basic " + basic + "dailyAllowance " + dailyAllowance);
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic())
						+ Float.parseFloat(bean.getDailyAllowance()));
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);
				String cpf = "";
				if (!tempInfo[8].equals("XXX")) {
					cpf = tempInfo[8];
				} else {
					cpf = "0.00";
				}
				String fpf = "";
				if (!tempInfo[9].equals("XXX")) {
					fpf = tempInfo[9];
				} else {
					fpf = "0.00";
				}

				log.info("cpf " + cpf + "fpf " + fpf);
				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(fpf));
				log.info("pfStatuary " + pfStatuary);
				bean.setEmpPfStatuary(pfStatuary);

				String vpf = "";
				if (!tempInfo[10].equals("XXX")) {
					vpf = tempInfo[10];
				} else {
					vpf = "0.00";
				}
				bean.setEmpVpf(vpf);
				String advRec = "";
				if (!tempInfo[11].equals("XXX")) {
					advRec = tempInfo[11];
				} else {
					advRec = "0.00";
				}
				String advGiv = "";
				if (!tempInfo[12].equals("XXX")) {
					advGiv = tempInfo[12];
				} else {
					advGiv = "0.00";
				}
				String empTotal = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(fpf)
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(advRec));
				bean.setEmpTotal(empTotal);
				if (!tempInfo[13].equals("XXX")) {
					bean.setRemarks(tempInfo[13].trim());

				} else {
					bean.setRemarks("");
				}
				bean.setAaiTotal(bean.getEmpTotal());
				String shortRec = "";
				if (!tempInfo[14].equals("XXX")) {
					shortRec = tempInfo[14].trim();

				} else {
					shortRec = "0.00";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				log.info("cpfaccno " + bean.getCpfAccNo());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "West Region");
				if (countEmployeeInfo == 0) {

					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,dateofbirth) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getAirportCode());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "West Region");
					pst.setString(7, bean.getDateofBirth());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,DAILYALLOWANCE,cpf,fpf,advRec,advGiv,shortRec,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "West Region");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, bean.getAirportCode());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getDailyAllowance());
					pst.setString(12, cpf);
					pst.setString(13, fpf);
					pst.setString(14, advRec);
					pst.setString(15, advGiv);
					pst.setString(16, shortRec);
					pst.setString(17, bean.getEmployeeNewNo());
					pst.setString(18, bean.getDesignation());
					pst.setString(19, bean.getRemarks());
					pst.setString(20, "West Region");

					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						basicDaSum = Float.parseFloat(bean.getBasic())
								+ Float.parseFloat(bean.getDailyAllowance());
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getBasic()
								+ "')), DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
								+ bean.getDailyAllowance()
								+ "')) , emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ basicDaSum
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "'+'"
								+ fpf
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='West Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and  monthyear='"
								+ bean.getMonthYear()
								+ "'";

					} else {
						float pfstaturoty = Float.parseFloat(bean
								.getEmpPfStatuary().trim()) * 100 / 10;
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ pfstaturoty
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='West Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and monthyear='"
								+ bean.getMonthYear()
								+ "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}

				// this.addFinancialDataRecord(bean);

				log
						.info("FinaceDAO :readXLSFinalcialDataWRegion() end method========= count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	public void readXLSFinalcialDataChqNad(String xlsData) throws Exception {
		log.info("FinaceDAO :readXLSFinalcialDataChqNad() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		String fpf = "",ptw = "",adDa="",advance = "",daArrear = "",cpfArrear="";;
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			// logic for CHQNAD FROM 95 TO 9900

			for (int i = 0; i < xlsDataList.size(); i++) {
				st = con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
        		String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6]);
				} else {
					bean.setBasic("0.00");
				}
				String pfCount = "",refAdv = "";
				if (!tempInfo[7].equals("XXX")) {
					pfCount = tempInfo[7];
				} else {
					pfCount = "0.00";
				}
				String emoluments = String.valueOf(Float.parseFloat(basic));
				bean.setEmoluments(emoluments);
				if (!tempInfo[8].equals("XXX")) {
					refAdv = tempInfo[8];
				} else {
					refAdv = "0.00";
				}

				String pfStatuary = String.valueOf(Float.parseFloat(bean
						.getBasic()) * 12 / 100);
				log.info("pfStatuary " + pfStatuary);
				if (!pfStatuary.endsWith("0.0")) {
					bean.setEmpPfStatuary(pfStatuary);
				} else
					bean.setEmpPfStatuary("0.00");

				String vpf = String.valueOf(Float.parseFloat(pfCount)
						- Float.parseFloat(pfStatuary));
				
				if (!tempInfo[9].equals("XXX")) {
					adDa = tempInfo[9];
				} else {
					adDa = "0.00";
				}
			if (!tempInfo[10].equals("XXX")) {
					fpf = tempInfo[10];
				} else {
					fpf = "0.00";
				}

				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(vpf) + Float.parseFloat(refAdv));

				bean.setEmpTotal(empTotal);
				if (!tempInfo[11].equals("XXX")) {
					advance = tempInfo[11].trim();
				} else {
					advance = "0.00";
				}
				if (!tempInfo[12].equals("XXX")) {
					daArrear = tempInfo[12].trim();
				} else {
					daArrear = "0.00";
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setCpfArriers(tempInfo[13].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setRemarks(tempInfo[14].trim());
				} else {
					bean.setRemarks("");
				}
				if (!tempInfo[15].equals("XXX")) {
					ptw = tempInfo[15].trim();
				} else {
					ptw = "";
				}

				bean.setAaiTotal(bean.getEmpTotal());

				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}

				log.info("cpfaccno " + bean.getCpfAccNo()); // for Northern
				// Region checking cpfaccno instead employeename // employeeName
				// = commonDAO.checkEmployeeInfo(con, //
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(), //
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQNAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,dateofbirth) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "CHQNAD".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation()); // for southern
					// region Data
					pst.setString(6, "CHQNAD".trim());
					pst.setString(7, bean.getDateofBirth());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into  employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,pfCount,advRec,adDa,fpf,advance,daArrear,cpfArrear,employeeno,DESEGNATION,remarks,region)   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "CHQNAD".trim());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, pfCount);
					pst.setString(12, refAdv);
					pst.setString(13, adDa);
					pst.setString(14, fpf);
					pst.setString(15, advance);
					pst.setString(16, daArrear);
					pst.setString(17, cpfArrear);
					pst.setString(18, bean.getEmployeeNewNo());
					pst.setString(19, bean.getDesignation());
					pst.setString(20, bean.getRemarks());
					pst.setString(21, "CHQNAD".trim());

					count = pst.executeUpdate();
					count++;
					pst.close();
				} //
				// this.addFinancialDataRecord(bean);

				log
						.info("FinaceDAO :readXLSFinalcialDataChqNad() end   method========= count"
								+ count);

				// Logic from 2000 to 2008 CHQNAD REGION
				/*
				 * for (int i = 0; i < xlsDataList.size(); i++) { //
				 * st=con.createStatement(); bean = new FinacialDataBean();
				 * tempData = xlsDataList.get(i).toString(); tempInfo =
				 * tempData.split("@"); log.info("Employee Code" +
				 * tempInfo[0].toString().trim() + "CPFACCNO" +
				 * tempInfo[1].toString().trim() + "Employee Name" +
				 * tempInfo[3].toString().trim());
				 * 
				 * if (!tempInfo[0].equals("XXX")) {
				 * bean.setEmployeeNewNo(tempInfo[0]); } else {
				 * bean.setEmployeeNewNo(""); }
				 * 
				 * if (!tempInfo[1].equals("XXX")) {
				 * bean.setCpfAccNo(tempInfo[1].trim()); } else {
				 * bean.setCpfAccNo(""); } if (!tempInfo[2].equals("XXX")) {
				 * bean.setMonthYear(tempInfo[2]); } else {
				 * bean.setMonthYear(""); } log.info("year " +
				 * bean.getMonthYear()); if (!tempInfo[3].equals("XXX")) {
				 * String empName = tempInfo[3]; int empNameLength =
				 * empName.length();
				 * 
				 * if (empName.charAt(empNameLength - 1) == '.') { empName =
				 * empName.substring(0, empNameLength - 1); }
				 * bean.setEmployeeName(empName); } else {
				 * bean.setEmployeeName(""); }
				 * 
				 * int startIndxName = 0; int endIndxName = 1;
				 * 
				 * String tempName = ""; for (int j = 0; j < quats.length; j++)
				 * { log.info("getName " + bean.getEmployeeName()); if
				 * (bean.getEmployeeName().toLowerCase().indexOf(
				 * quats[j].toLowerCase()) != -1) { tempName =
				 * bean.getEmployeeName().toLowerCase()
				 * .replaceAll(quats[j].toLowerCase(), "").trim();
				 * log.info("tempName " + tempName); //
				 * tempName=empName.substring(index+1,empName.length());
				 * bean.setEmployeeName(tempName); } } log.info("emp name " +
				 * bean.getEmployeeName());
				 * 
				 * if (!tempInfo[4].equals("XXX")) {
				 * bean.setDesignation(tempInfo[4]); } else {
				 * bean.setDesignation(""); }
				 * 
				 * if (!tempInfo[6].equals("XXX")) { basic = tempInfo[6]; } else
				 * { basic = "0.00"; } bean.setBasic(basic);
				 * 
				 * String vda = ""; if (!tempInfo[7].equals("XXX")) { vda =
				 * tempInfo[7]; } else { vda = "0.00"; }
				 * 
				 * log.info("salary " + basic + "vda " + vda);
				 * 
				 * String cpf = ""; if (!tempInfo[8].equals("XXX")) { cpf =
				 * tempInfo[8]; } else { cpf = "0.00"; }
				 * 
				 * log.info("cpf " + cpf); String vpf = "";
				 * 
				 * if (!tempInfo[9].equals("XXX")) { vpf = tempInfo[9]; } else {
				 * vpf = "0.00"; }
				 * 
				 * String cpfArrear = ""; if (!tempInfo[10].equals("XXX")) {
				 * cpfArrear = tempInfo[10]; } else { cpfArrear = "0.00"; }
				 * bean.setCpfArriers(cpfArrear);
				 * 
				 * String pfStatuary = String.valueOf(Float.parseFloat(cpf) +
				 * Float.parseFloat(bean.getCpfArriers()));
				 * log.info("pfStatuary " + pfStatuary); if
				 * (!pfStatuary.endsWith("0.0")) {
				 * bean.setEmpPfStatuary(pfStatuary); } else
				 * bean.setEmpPfStatuary("0.00");
				 * 
				 * String emoluments = String
				 * .valueOf(Float.parseFloat(pfStatuary) 100 / 12);
				 * log.info("emoluments " + emoluments);
				 * bean.setEmoluments(emoluments); String cpfAdvance = ""; if
				 * (!tempInfo[11].equals("XXX")) { cpfAdvance =
				 * tempInfo[11].trim(); } else { cpfAdvance = "0.00"; }
				 * 
				 * String empTotal = String.valueOf(Float.parseFloat(bean
				 * .getEmpPfStatuary()) + Float.parseFloat(vpf) +
				 * Float.parseFloat(cpfAdvance));
				 * 
				 * bean.setEmpTotal(empTotal);
				 * 
				 * if (!tempInfo[12].equals("XXX")) {
				 * bean.setRemarks(tempInfo[12].trim()); } else {
				 * bean.setRemarks(""); }
				 * 
				 * bean.setAaiTotal(bean.getEmpTotal()); if
				 * (!tempInfo[5].equals("XXX")) {
				 * bean.setAirportCode(tempInfo[5].trim()); } else {
				 * bean.setAirportCode(""); }
				 * 
				 * DateValidation dateValidation = new DateValidation(); if
				 * (!tempInfo[2].equals("XXX")) {
				 * bean.setMonthYear(tempInfo[2].toString()); } else {
				 * bean.setMonthYear(""); } // log.info("cpfaccno " +
				 * bean.getCpfAccNo());
				 * 
				 * FinacialDataBean bean1 = this.getEmployeeInfo(bean
				 * .getEmployeeNewNo()); // for Northern Region checking
				 * cpfaccno nstead employeename // employeeName =
				 * commonDAO.checkEmployeeInfo(con, // bean.getEmployeeNewNo(),
				 * bean.getCpfAccNo(), // bean.getEmployeeName());
				 * countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
				 * .getEmployeeNewNo(), bean.getCpfAccNo(), bean
				 * .getEmployeeName(), "CHQNAD"); log.info("empName" +
				 * bean1.getEmployeeName() + "deseg " + bean1.getDesignation() +
				 * "cpfacno " + bean1.getCpfAccNo()); if (countEmployeeInfo ==
				 * 0) { String sql = "insert into
				 * employee_info(cpfacno,airportCode,
				 * employeeno,employeeName,desegnation,region,dateofbirth)
				 * VALUES " + "(?,?,?,?,?,?,?)"; pst =
				 * con.prepareStatement(sql); pst.setString(1,
				 * bean1.getCpfAccNo().trim()); pst.setString(2,
				 * "CHQNAD".trim()); pst.setString(3, bean.getEmployeeNewNo());
				 * pst.setString(4, bean1.getEmployeeName()); pst.setString(5,
				 * bean1.getDesignation()); // for southern region Data
				 * pst.setString(6, "CHQNAD".trim()); pst.setString(7,
				 * bean.getDateofBirth()); pst.executeUpdate(); pst.close(); }
				 * String finaceInsert = "insert into
				 * employee_pension_validate(cpfaccno
				 * ,emoluments,emppfstatuary,empvpf
				 * ,emptotal,aaitotal,AIRPORTCODE," +
				 * "MONTHYEAR,employeeName,BASIC,vda,cpf,cpfArrear,cpfAdvance,employeeno,DESEGNATION,remarks,region)"
				 * + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 * 
				 * log.info(" employeeName" + employeeName + "Designation" +
				 * bean.getDesignation() + "CpfAccNo" +
				 * bean.getCpfAccNo().trim());
				 * 
				 * String foundSalaryDate = this.checkFinanceDuplicate(con, bean
				 * .getMonthYear().trim(), bean.getCpfAccNo(), bean
				 * .getEmployeeNewNo(), "CHQNAD");
				 * 
				 * if (foundSalaryDate.equals("")) { pst =
				 * con.prepareStatement(finaceInsert); pst.setString(1,
				 * bean1.getCpfAccNo().trim()); pst.setString(2,
				 * bean.getEmoluments()); pst.setString(3,
				 * bean.getEmpPfStatuary().trim()); pst.setString(4,
				 * bean.getEmpVpf().trim()); pst.setString(5,
				 * bean.getEmpTotal().trim()); pst.setString(6,
				 * bean.getAaiTotal().trim()); pst.setString(7,
				 * "CHQNAD".trim()); pst.setString(8, bean.getMonthYear());
				 * pst.setString(9, bean1.getEmployeeName()); pst.setString(10,
				 * bean.getBasic()); pst.setString(11, vda); pst.setString(12,
				 * cpf); pst.setString(13, bean.getCpfArriers());
				 * pst.setString(14, cpfAdvance); pst.setString(15,
				 * bean.getEmployeeNewNo()); pst.setString(16,
				 * bean1.getDesignation()); pst.setString(17,
				 * bean.getRemarks()); pst.setString(18, "CHQNAD".trim());
				 * 
				 * count = pst.executeUpdate(); count++; pst.close(); } //
				 * this.addFinancialDataRecord(bean); log .info("FinaceDAO
				 * :readXLSFinalcialDataChqNad() End method count" + count); }
				 */

				finalcialDataList.add(bean);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			con.close();
		}

	}

	public void readXLSFinalcialDataChqNadKamalDeep(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataChqNad Kamldeep() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", empName = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString(), tempName = "";
		;
		String monthYear = xlsDataList.get(1).toString(), cpf = "", cpfArrear = "", pfStatuary = "", emoluments = "", empTotal = "";
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			// logic for CHQNAD FROM 95 TO 9900

			for (int i = 0; i < xlsDataList.size(); i++) {
				st = con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					empName = tempInfo[3];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				/*
				 * if (!tempInfo[6].equals("XXX")) { basic = tempInfo[6]; } else
				 * { basic = "0.00"; } bean.setBasic(basic); String vda = ""; if
				 * (!tempInfo[7].equals("XXX")) { vda = tempInfo[7]; } else {
				 * vda = "0.00"; }
				 * 
				 * log.info("salary " + basic + "vda " + vda);
				 */
				if (!tempInfo[13].equals("XXX")) {
					cpf = tempInfo[13];
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setEmpVpf(tempInfo[14]);
				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[15].equals("XXX")) {
					cpfArrear = tempInfo[15];
				} else {
					cpfArrear = "0.00";
				}
				bean.setCpfArriers(cpfArrear);

				pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(bean.getCpfArriers()));
				log.info("pfStatuary " + pfStatuary);
				if (!pfStatuary.endsWith("0.0")) {
					bean.setEmpPfStatuary(pfStatuary);
				} else
					bean.setEmpPfStatuary("0.00");
				emoluments = String
						.valueOf(Float.parseFloat(pfStatuary) * 100 / 12);
				bean.setEmoluments(emoluments);
				String cpfAdvance = "";
				if (!tempInfo[16].equals("XXX")) {
					cpfAdvance = tempInfo[16].trim();
				} else {
					cpfAdvance = "0.00";
				}

				empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(cpfAdvance));

				bean.setEmpTotal(empTotal);

				if (!tempInfo[12].equals("XXX")) {
					bean.setRemarks(tempInfo[12].trim());
				} else {
					bean.setRemarks("");
				}

				bean.setAaiTotal(bean.getEmpTotal());
				
				if (!tempInfo[5].equals("XXX")) {
					bean.setDateofBirth(tempInfo[5].trim());
				} else {
					bean.setDateofBirth("");
				}

				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				// log.info("cpfaccno " + bean.getCpfAccNo());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQNAD");

				log.info("empName" + bean.getEmployeeName() + "deseg "
						+ bean.getDesignation() + "cpfacno "
						+ bean.getCpfAccNo());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,dateofbirth,WETHEROPTION) VALUES "
							+ "(?,?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "CHQNAD".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation()); // for southern
					// region Data
					pst.setString(6, "CHQNAD".trim());
					pst.setString(7, bean.getDateofBirth());
					pst.setString(8, "B");

					// pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into  employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,vda,cpf,cpfArrear,cpfAdvance,employeeno,DESEGNATION,remarks,region)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "CHQNAD".trim());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, "0.00");
					pst.setString(12, cpf);
					pst.setString(13, bean.getCpfArriers());
					pst.setString(14, cpfAdvance);
					pst.setString(15, bean.getEmployeeNewNo());
					pst.setString(16, bean.getDesignation());
					pst.setString(17, bean.getRemarks());
					pst.setString(18, "CHQNAD".trim());

					// count = pst.executeUpdate();
					count++;
					pst.close();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			// con.close();
		}

	}

	public void readXLSFinalcialDataChqNadAllahabad(String xlsData)
			throws Exception {
		log
				.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "",advance = "",cpf = "",arrCount="",refAdvance = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[0].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setMonthYear(tempInfo[1]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}

				String tempName = "";
				for (int j = 0; j < quats.length; j++) {

					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
							bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					basic = tempInfo[4];
				} else {
					basic = "0.00";
				}
				bean.setBasic(basic);
				if (!tempInfo[5].equals("XXX")) {
					cpf = tempInfo[5];
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					arrCount = tempInfo[6];

				} else {
					arrCount = "0.00";
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpVpf(tempInfo[7]);
				} else {
					bean.setEmpVpf("0.00");
				}
				
				if (!tempInfo[8].equals("XXX")) {
					refAdvance = tempInfo[8].trim();
				} else {
					refAdvance = "0.00";
				}
				if (!tempInfo[9].equals("XXX")) {
					advance = tempInfo[9].trim();

				} else {
					advance = "0.00";
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setRemarks(tempInfo[10].trim());

				} else {
					bean.setRemarks("");
				}

				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(arrCount));
				log.info("pfStatuary " + pfStatuary);
				// LOGIC FROM 95-98
				// String emoluments =
				// String.valueOf(Float.parseFloat(pfStatuary) * 100 / 10);

				// LOGIC FROM 98-2001
				String emoluments = String
						.valueOf(Float.parseFloat(pfStatuary) * 100 / 12);
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// FinacialDataBean bean1 =
				// this.getEmployeeInfo(bean.getCpfAccNo().trim());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQNAD");
				log.info("empName" + bean.getEmployeeName() + "deseg "
						+ bean.getDesignation() + "cpfacno "
						+ bean.getCpfAccNo());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "Allahabad".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "CHQNAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,arrCount,REFADV,advance,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "Allahabad".trim());
					pst.setString(6, bean.getMonthYear());
					pst.setString(7, bean.getEmployeeName());
					pst.setString(8, bean.getBasic());
					pst.setString(9, cpf);
					pst.setString(10, arrCount);
					pst.setString(11, refAdvance);
					pst.setString(12, advance);
					pst.setString(13, bean.getDesignation());
					pst.setString(14, bean.getRemarks());
					pst.setString(15, "CHQNAD".trim());

					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				// this.addFinancialDataRecord(bean);
				log
						.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad() End method count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			con.close();
		}

	}

	public void readXLSFinalcialDataChqNadAllahabad20012003(String xlsData)
			throws Exception {
		log
				.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20012003() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "", tempName = "", cpf = "", cpfArrear = "", cpfAdv = "", advance = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("CPFACCNO" + tempInfo[0].toString().trim()
						+ "Employee Name" + tempInfo[2].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[0].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setMonthYear(tempInfo[1]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}

				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setBasic(tempInfo[4]);
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[5].equals("XXX")) {
					cpf = tempInfo[5];
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					cpfArrear = tempInfo[6].trim();
				} else {
					cpfArrear = "0.00";
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpVpf(tempInfo[7].trim());
				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[9].equals("XXX")) {
					cpfAdv = tempInfo[9].trim();

				} else {
					cpfAdv = "0.00";
				}
				if (!tempInfo[10].equals("XXX")) {
					advance = tempInfo[10].trim();

				} else {
					advance = "0.00";
				}

				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(cpfArrear));
				String emoluments = String
						.valueOf(Float.parseFloat(pfStatuary) * 100 / 12);

				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// FinacialDataBean bean1 =
				// this.getEmployeeInfo(bean.getCpfAccNo().trim());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQNAD");
				log.info("empName" + bean.getEmployeeName() + "deseg "
						+ bean.getDesignation() + "cpfacno "
						+ bean.getCpfAccNo());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "Allahabad".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "CHQNAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,CPFARREAR,REFADV,advrec,advance,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "Allahabad".trim());
					pst.setString(6, bean.getMonthYear());
					pst.setString(7, bean.getEmployeeName());
					pst.setString(8, bean.getBasic());
					pst.setString(9, cpf.trim());
					pst.setString(10, cpfArrear.trim());
					pst.setString(11, cpfAdv.trim());
					String advrec = "";
					pst.setString(12, advrec);
					pst.setString(13, advance.trim());
					pst.setString(14, bean.getDesignation());
					pst.setString(15, bean.getRemarks());
					pst.setString(16, "CHQNAD".trim());

					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				// this.addFinancialDataRecord(bean);
				log
						.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20012003() End method count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			con.close();
		}

	}

	public void readXLSFinalcialDataChqNadAllahabad20062007(String xlsData)
			throws Exception {
		log
				.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20062007() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };

		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[0].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setMonthYear(tempInfo[1]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {

					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					basic = tempInfo[4];
				} else {
					basic = "0.00";
				}
				bean.setBasic(basic);
				String total = "", cpf = "", pfAdvance = "", advrec = "";
				if (!tempInfo[5].equals("XXX")) {
					total = tempInfo[5];
				} else {
					total = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					cpf = tempInfo[6];
				} else {
					cpf = "0.00";
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpVpf(tempInfo[7]);

				} else {
					bean.setEmpVpf("0.00");
				}

				if (!tempInfo[8].equals("XXX")) {
					pfAdvance = tempInfo[8].trim();

				} else {
					pfAdvance = "0.00";
				}
				if (!tempInfo[9].equals("XXX")) {
					advrec = tempInfo[9].trim();

				} else {
					advrec = "0.00";
				}
				String pfStatuary = String.valueOf(Float.parseFloat(cpf));
				log.info("pfStatuary " + pfStatuary);
				// LOGIC FROM 95-98
				// String emoluments =
				// String.valueOf(Float.parseFloat(pfStatuary) * 100 / 10);

				// LOGIC FROM 98-2001
				String emoluments = String.valueOf(Float.parseFloat(total));
				bean.setEmoluments(emoluments);

				// FinacialDataBean bean1 =
				// this.getEmployeeInfo(bean.getCpfAccNo().trim());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQNAD");
				log.info("empName" + bean.getEmployeeName() + "deseg "
						+ bean.getDesignation() + "cpfacno "
						+ bean.getCpfAccNo());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "Allahabad".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "CHQNAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,PFADVANCE,advrec,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "Allahabad".trim());
					pst.setString(6, bean.getMonthYear());
					pst.setString(7, bean.getEmployeeName());
					pst.setString(8, bean.getBasic());
					pst.setString(9, cpf);
					pst.setString(10, pfAdvance);
					pst.setString(11, advrec);
					pst.setString(12, bean.getDesignation());
					pst.setString(13, bean.getRemarks());
					pst.setString(14, "CHQNAD".trim());

					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				// this.addFinancialDataRecord(bean);
				log
						.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20062007() End method count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			con.close();
		}

	}

	public void readXLSFinalcialDataChqNadAllahabad01020203(String xlsData)
			throws InvalidDataException {
		log
				.info("ImportDao :readXLSFinalcialDataChqNadAllahabad0102-0203() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String cpf = "", cpfArrear = "", vpf = "", pfAdvance = "", emoluments = "", pfStatuary = "", tempName = "";
		;

		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[0].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[1].trim());
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
			
				for (int j = 0; j < quats.length; j++) {

					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[5].equals("XXX")) {
					basic = tempInfo[5];
				} else {
					basic = "0.00";
				}
				bean.setBasic(basic);
				if (!tempInfo[6].equals("XXX")) {
					cpf = tempInfo[6];
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[7].equals("XXX")) {
					cpfArrear = tempInfo[7];
				} else {
					cpfArrear = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setEmpVpf(tempInfo[8]);

				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[9].equals("XXX")) {
					pfAdvance = tempInfo[9].trim();
				} else {
					pfAdvance = "0.00";
				}
				pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(cpfArrear));
				bean.setEmpPfStatuary(pfStatuary);
				emoluments = String
						.valueOf(Float.parseFloat(pfStatuary) * 100 / 12);
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// FinacialDataBean bean1 =
				// this.getEmployeeInfo(bean.getCpfAccNo().trim());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "North Region");
				log.info("empName" + bean.getEmployeeName() + "deseg "
						+ bean.getDesignation() + "cpfacno "
						+ bean.getCpfAccNo());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "Allahabad".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "North Region".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,PFADVANCE,CPFARREAR,DESEGNATION,remarks,region,EMPLOYEENO)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "North Region");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "Allahabad".trim());
					pst.setString(6, bean.getMonthYear());
					pst.setString(7, bean.getEmployeeName());
					pst.setString(8, bean.getBasic());
					pst.setString(9, cpf);
					pst.setString(10, pfAdvance);
					pst.setString(11, cpfArrear);
					pst.setString(12, bean.getDesignation());
					pst.setString(13, bean.getRemarks());
					pst.setString(14, "North Region".trim());
					pst.setString(15, bean.getEmployeeNewNo().trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				// this.addFinancialDataRecord(bean);
				log
						.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad0102-0203() End method count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	public void readXLSFinalcialDataChqNadAllahabad20072008(String xlsData)
			throws Exception {
		log
				.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20072008() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "", dailyAllowance = "", specialBasic = "", cpfAccno = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString(), cpf = "", optCpf = "", emoluments = "";
		String monthYear = xlsDataList.get(1).toString();
		String vpf = "", pfAdvance = "", interest = "", pfStatuary = "";
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("CPFACCNO" + tempInfo[0].toString().trim()
						+ "Employee Name" + tempInfo[2].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[0].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setMonthYear(tempInfo[1]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}

				int startIndxName = 0;
				int endIndxName = 1;

				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setBasic(tempInfo[4]);
				} else {
					bean.setBasic("0.00");
				}

				if (!tempInfo[5].equals("XXX")) {
					cpf = tempInfo[5];
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					optCpf = tempInfo[6];
				} else {
					optCpf = "0.00";
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpVpf(tempInfo[7]);

				} else {
					bean.setEmpVpf("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					pfAdvance = tempInfo[8].trim();

				} else {
					pfAdvance = "0.00";
				}

				if (!tempInfo[9].equals("XXX")) {
					interest = tempInfo[9].trim();

				} else {
					interest = "0.00";
				}
				pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(optCpf));
				log.info("pfStatuary " + pfStatuary);
				bean.setEmpPfStatuary(pfStatuary);
				emoluments = String
						.valueOf(Float.parseFloat(pfStatuary) * 100 / 12);

				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// FinacialDataBean bean1 =
				// this.getEmployeeInfo(bean.getCpfAccNo().trim());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "North Region");
				
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "Allahabad".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "North Region".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,PFADVANCE,ADDCPF,CPFINTEREST,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());

				// String foundSalaryDate = this.checkFinanceDuplicate(con,
				// bean.getMonthYear().trim(), bean.getCpfAccNo(),
				// bean.getEmployeeNewNo(), "North Region");

				// if (foundSalaryDate.equals("")) {
				pst = con.prepareStatement(finaceInsert);
				pst.setString(1, bean.getCpfAccNo().trim());
				pst.setString(2, bean.getEmoluments());
				pst.setString(3, bean.getEmpPfStatuary().trim());
				pst.setString(4, bean.getEmpVpf().trim());
				pst.setString(5, "Allahabad".trim());
				pst.setString(6, bean.getMonthYear());
				pst.setString(7, bean.getEmployeeName());
				pst.setString(8, bean.getBasic());
				pst.setString(9, cpf);
				pst.setString(10, pfAdvance);
				pst.setString(11, optCpf);
				pst.setString(12, interest);
				pst.setString(13, bean.getDesignation());
				pst.setString(14, "one month escalated");
				pst.setString(15, "North Region".trim());

				count = pst.executeUpdate();
				count++;
				pst.close();
				// }

				// this.addFinancialDataRecord(bean);
				log
						.info("FinaceDAO :readXLSFinalcialDataChqNadAllahabad20072008() End method count"
								+ count);
			}

			finalcialDataList.add(bean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			log.printStackTrace(e);
		} finally {

			con.close();
		}

	}

	public void readXLSFinalcialDataNERegion(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataNERegion() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "", empName = "", tempName = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();

			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					empName = tempInfo[3];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}

				int startIndxName = 0;
				int endIndxName = 1;
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					basic = tempInfo[6];
					bean.setBasic(basic);
				} else {
					basic = "0.00";
					bean.setBasic(basic);
				}
				String vda = "", cpf = "", empTotal = "";
				if (!tempInfo[7].equals("XXX")) {
					vda = tempInfo[7];
				} else {
					vda = "0.00";
				}

				if (!tempInfo[8].equals("XXX")) {
					cpf = tempInfo[8];
				} else {
					cpf = "0.00";

				}
				bean.setEmpPfStatuary(cpf);
				log.info("basic " + basic + "vda " + vda);

				String emoluments = String.valueOf(Float.parseFloat(basic)
						+ Float.parseFloat(vda));
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				// new column added for NER REGION
				/*
				 * String addCpf = ""; if (!tempInfo[9].equals("XXX")) { addCpf
				 * = tempInfo[9]; } else { addCpf = "0.00"; }
				 * 
				 * String pfAdvance = ""; if (!tempInfo[10].equals("XXX")) {
				 * bean.setPfAdvance(tempInfo[10].trim()); } else {
				 * bean.setPfAdvance("0.00"); }
				 * 
				 * log.info("addCpf " + addCpf + "pfAdvance " +
				 * bean.getPfAdvance()); String pfStatuary =
				 * String.valueOf(Float.parseFloat(cpf) +
				 * Float.parseFloat(addCpf)); log.info("pfStatuary " +
				 * pfStatuary); if (!pfStatuary.endsWith("0.0")) {
				 * bean.setEmpPfStatuary(pfStatuary); } else
				 * bean.setEmpPfStatuary("0.00"); String vpf =
				 * String.valueOf(Float.parseFloat(bean .getPfAdvance()));
				 * bean.setEmpVpf(vpf); String advance = ""; if
				 * (!tempInfo[11].equals("XXX")) { advance = tempInfo[11]; }
				 * else { advance = "0.00"; }
				 */
				empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}

				log.info("cpfaccno " + bean.getCpfAccNo() + "month year "
						+ bean.getMonthYear());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "North-East Region");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,dateofbirth) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getAirportCode());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "North-East Region");
					pst.setString(7, bean.getDateofBirth());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,vda,cpf,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "North-East Region");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, bean.getAirportCode());
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, vda);
					pst.setString(12, cpf);
					pst.setString(13, bean.getEmployeeNewNo());
					pst.setString(14, bean.getDesignation());
					pst.setString(15, bean.getRemarks());
					pst.setString(16, "North-East Region");

					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yy", "-MMM-yy");
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.0")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getBasic()
								+ "')), vda=to_char(TO_NUMBER(nvl(vda,0)+'"
								+ vda
								+ "')), emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='North-East Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";

					} else {
						DateFormat df = new SimpleDateFormat("dd-MMM-yy");

						Date transdate = df.parse(bean.getMonthYear());
						float pfstaturoty = 0;
						if (transdate.after(new Date("31-Mar-98"))) {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 12;
						} else {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 10;
						}
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ pfstaturoty
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "'))  where region='North-East Region' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}

				// this.addFinancialDataRecord(bean);

				log
						.info("FinaceDAO :readXLSFinalcialDataNERegion() end method========= count"
								+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataCHQIADChennaiRegion(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataCHQIADChennaiRegion() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		PensionDAO PensionDao = new PensionDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String whetherOption = "", month = "", year = "", fullMonthYear = "", whetherOptionA = "", whetherOptionB = "";
			String employeeName = "";
			String pensionNumber = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(commonUtil
							.validateEmployeeName(tempInfo[0].toCharArray()));
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(commonUtil
							.validateEmployeeName(tempInfo[1].trim()
									.toCharArray()));
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					empName = commonUtil.replaceAllWords2(empName, " .", "");
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}

				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
						if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setBasic(tempInfo[9].trim());
				} else {
					bean.setBasic("0.00");
				}

				/*
				 * log.info("basic " + basic ); String emoluments =
				 * String.valueOf(bean.getBasic()); log.info("emoluments " +
				 * emoluments);
				 */
				bean.setEmoluments(bean.getBasic());
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[10].trim());
				} else
					bean.setEmpPfStatuary("0.00");

				if (!tempInfo[16].equals("XXX")) {
					bean.setCpfArriers(tempInfo[16].trim());
				} else {
					bean.setCpfArriers("0.00");
				}

				bean.setEmpPfStatuary(String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getCpfArriers())));
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmpVpf(tempInfo[11].trim());
				} else
					bean.setEmpVpf("0.00");

				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else
					bean.setPrincipal("0.00");
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setAdvanceDrawn(tempInfo[14].trim());
				} else {
					bean.setAdvanceDrawn("0.00");
				}

				if (!tempInfo[15].equals("XXX")) {
					bean.setPartFinal(tempInfo[15].trim());
				} else {
					bean.setPartFinal("0.00");
				}

				if (!tempInfo[4].equals("XXX")) {
					whetherOption = tempInfo[4].toString();
				} else {
					whetherOption = "";
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setDateofBirth(tempInfo[5].toString());
				} else {
					bean.setDateofBirth("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDateofJoining(tempInfo[6].toString());
				} else {
					bean.setDateofJoining("");
				}
				if (!tempInfo[7].equals("XXX")) {
					month = tempInfo[7].toString().trim();
				} else {
					month = "";
				}
				if (!tempInfo[8].equals("XXX")) {
					year = tempInfo[8].toString().trim();
				} else {
					year = "";
				}
				if (!year.equals("") && !month.equals("")) {
					fullMonthYear = "01-" + month + "-" + year;
					fullMonthYear = commonUtil.converDBToAppFormat(
							fullMonthYear, "dd-MM-yyyy", "dd-MMM-yyyy");
					bean.setMonthYear(fullMonthYear);
				}
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				bean.setAirportCode("CHENNAI IAD");

				if (!bean.getDateofBirth().equals("")
						&& !bean.getCpfAccNo().equals("")) {
					pensionNumber = commonDAO.getPensionNumber(bean
							.getEmployeeName().trim(), bean.getDateofBirth(),
							bean.getCpfAccNo(), "CHQIAD");
				} else {
					pensionNumber = "";
				}

				log
						.info("==========================Final Parameters Chennai IAD==========================================================");
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,pensionnumber,airportCode, employeeno,employeeName,desegnation,region,dateofbirth,dateofJoining,WETHEROPTION) VALUES "
							+ "(?,?,?,?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, pensionNumber.trim());
					pst.setString(3, "CHENNAI IAD".trim());
					pst.setString(4, bean.getEmployeeNewNo().trim());
					pst.setString(5, bean.getEmployeeName().trim());
					pst.setString(6, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(7, "CHQIAD");
					pst.setString(8, bean.getDateofBirth().trim());
					pst.setString(9, bean.getDateofJoining().trim());
					pst.setString(10, whetherOption);

					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,advance,PARTFINAL,employeeno,DESEGNATION,remarks,region,CPFARREAR)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "CHENNAI IAD");
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getPrincipal());
					pst.setString(12, bean.getInterest());
					pst.setString(13, bean.getAdvanceDrawn());
					pst.setString(14, bean.getPartFinal());
					pst.setString(15, bean.getEmployeeNewNo());
					pst.setString(16, bean.getDesignation());
					pst.setString(17, bean.getRemarks());
					pst.setString(18, "CHQIAD");
					pst.setString(19, bean.getCpfArriers());
					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yy", "-MMM-yy");
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.0")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";

					} else {
						DateFormat df = new SimpleDateFormat("dd-MMM-yy");

						Date transdate = df.parse(bean.getMonthYear());
						float pfstaturoty = 0;
						if (transdate.after(new Date("31-Mar-98"))) {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 12;
						} else {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 10;
						}
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ pfstaturoty
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataCHQIADChennaiRegion() end method "
								+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataCHQIADKolProjRegion(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataCHQIADRegion() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		String empName = "", tempName = "";
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					empName = tempInfo[3];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}

				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				String emoluments = String.valueOf(bean.getBasic());
				bean.setEmoluments(emoluments);
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[7].trim());
				} else
					bean.setEmpPfStatuary("0.00");
				if (!tempInfo[8].equals("XXX")) {
					bean.setEmpVpf(tempInfo[8].trim());
				} else
					bean.setEmpVpf("0.00");
				if (!tempInfo[9].equals("XXX")) {
					bean.setPrincipal(tempInfo[9].trim());
				} else
					bean.setPrincipal("0.00");

				if (!tempInfo[10].equals("XXX")) {
					bean.setInterest(tempInfo[10].trim());
				} else {
					bean.setInterest("0.00");

				}
				String advance = "", nonrefMem = "", nonrefCom = "", partFinal = "", pensionNumber = "", empTotal = "";
				if (!tempInfo[11].equals("XXX")) {
					advance = tempInfo[11];
				} else {
					advance = "0.00";
				}

				if (!tempInfo[12].equals("XXX")) {
					nonrefMem = tempInfo[12];
				} else {
					nonrefMem = "0.00";
				}
				if (!tempInfo[13].equals("XXX")) {
					nonrefCom = tempInfo[13];
				} else {
					nonrefCom = "0.00";
				}

				partFinal = String.valueOf(Float.parseFloat(nonrefMem)
						+ Float.parseFloat(nonrefCom));
				bean.setPartFinal(partFinal);

				if (!tempInfo[15].equals("XXX")) {
					bean.setDateofBirth(tempInfo[15].toString());
				} else {
					bean.setDateofBirth("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setPensionNumber(commonUtil
							.getSearchPFID1(tempInfo[5]).trim());
				}
				log.info("pension number is " + bean.getPensionNumber());
				if (!tempInfo[16].equals("XXX")) {
					bean.setDateofJoining(tempInfo[16].toString());
				} else {
					bean.setDateofJoining("");
				}
				empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth"
						+ bean.getDateofBirth() + " dateofjoin "
						+ bean.getDateofJoining());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,dateofbirth,dateofJoining,pensionnumber) VALUES "
							+ "(?,?,?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "KOLKATA PROJ".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					// for southern region Data
					pst.setString(6, "CHQIAD");
					pst.setString(7, bean.getDateofBirth());
					pst.setString(8, bean.getDateofJoining());
					pst.setString(9, bean.getPensionNumber());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,advance,nonrefMem,nonrefCom,PARTFINAL,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region
				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "KOLKATA PROJ");
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getPrincipal());
					pst.setString(12, bean.getInterest());
					pst.setString(13, advance);
					pst.setString(14, nonrefMem);
					pst.setString(15, nonrefCom);
					pst.setString(16, bean.getPartFinal());
					pst.setString(17, bean.getEmployeeNewNo());
					pst.setString(18, bean.getDesignation());
					pst.setString(19, bean.getRemarks());
					pst.setString(20, "CHQIAD");

					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				log
						.info("FinaceDAO :readXLSFinalcialDataCHQIADRegion() end method========= count"
								+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataCHQIADKolRegion(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataCHQIADKolRegion() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "", basic = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();

					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}

				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				String emoluments = String.valueOf(bean.getBasic());
				bean.setEmoluments(emoluments);
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[7].trim());
				} else
					bean.setEmpPfStatuary("0.00");
				if (!tempInfo[12].equals("XXX")) {
					bean.setDateofBirth(tempInfo[12].toString());
				} else {
					bean.setDateofBirth("");
				}
				String pensionNumber = "";
				if (!bean.getDateofBirth().equals("")) {
					pensionNumber = commonDAO.getPensionNumber(bean
							.getEmployeeName().toUpperCase(), bean
							.getDateofBirth(), bean.getCpfAccNo(), "");
				} else
					pensionNumber = "";
				if (!tempInfo[13].equals("XXX")) {
					bean.setDateofJoining(tempInfo[13].toString());
				} else {
					bean.setDateofJoining("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setPensionOption(tempInfo[14].toString());
				} else {
					bean.setPensionOption("");
				}
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth"
						+ bean.getDateofBirth() + " dateofjoin "
						+ bean.getDateofJoining());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,PENSIONNUMBER,region,dateofbirth,dateofJoining,WETHEROPTION) VALUES "
							+ "(?,?,?,?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "KOLKATA".trim());
					pst.setString(3, bean.getEmployeeNewNo());
					pst.setString(4, bean.getEmployeeName());
					pst.setString(5, bean.getDesignation());
					pst.setString(6, pensionNumber);

					// for southern region Data
					pst.setString(7, "CHQIAD");
					pst.setString(8, bean.getDateofBirth());
					pst.setString(9, bean.getDateofJoining());
					pst.setString(10, bean.getPensionOption());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "KOLKATA");
					pst.setString(8, bean.getMonthYear());
					pst.setString(9, bean.getEmployeeName());
					pst.setString(10, bean.getBasic());
					pst.setString(11, bean.getEmployeeNewNo());
					pst.setString(12, bean.getDesignation());
					pst.setString(13, bean.getRemarks());
					pst.setString(14, "CHQIAD");

					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					st = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yy", "-MMM-yy");
					if (!bean.getBasic().equals("0.00")
							&& !bean.getBasic().equals("0.0")
							&& !bean.getBasic().equals("0")
							&& !bean.getBasic().equals("")) {
						insertArrearCount = "update employee_pension_validate set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getEmoluments()
								+ "')), emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";

					} else {
						DateFormat df = new SimpleDateFormat("dd-MMM-yy");

						Date transdate = df.parse(bean.getMonthYear());
						float pfstaturoty = 0;
						if (transdate.after(new Date("31-Mar-98"))) {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 12;
						} else {
							pfstaturoty = Float.parseFloat(bean
									.getEmpPfStatuary().trim()) * 100 / 10;
						}
						insertArrearCount = "update employee_pension_validate  set basic=to_char(TO_NUMBER(nvl(basic,0)+'"
								+ bean.getEmoluments()
								+ "')), emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ pfstaturoty
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear + "'";
					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataAmritsarIADCPF(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataAmritsarIADCPF() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {

				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {

					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}

				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				String emoluments = String.valueOf(bean.getBasic());
				bean.setEmoluments(emoluments);
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[7].trim());
				} else
					bean.setEmpPfStatuary("0.00");
				if (!tempInfo[8].equals("XXX")) {
					bean.setEmpVpf(tempInfo[8].trim());
				} else
					bean.setEmpVpf("0.00");

				if (!tempInfo[9].equals("XXX")) {
					bean.setPrincipal(tempInfo[9].trim());
				} else
					bean.setPrincipal("0.00");
				if (!tempInfo[10].equals("XXX")) {
					bean.setInterest(tempInfo[10].trim());
				} else
					bean.setInterest("0.00");
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				DateValidation dateValidation = new DateValidation();
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2].toString());
				} else {
					bean.setMonthYear("");
				}
				log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth"
						+ bean.getDateofBirth() + " dateofjoin "
						+ bean.getDateofJoining());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "AmritsarIAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "AmritsarIAD".trim());
					pst.setString(8, bean.getMonthYear().trim());
					pst.setString(9, bean.getEmployeeName().trim());
					pst.setString(10, bean.getBasic().trim());
					pst.setString(11, bean.getPrincipal().trim());
					pst.setString(12, bean.getInterest().trim());
					pst.setString(13, bean.getEmployeeNewNo().trim());
					pst.setString(14, bean.getDesignation().trim());
					pst.setString(15, bean.getRemarks().trim());
					pst.setString(16, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log.info("FinaceDAO :readXLSFinalcialDataAmritsarIADCPF count"
						+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialIGICargoIADCPF(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialIGICargoIADCPF() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {

				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					monthYear = commonUtil.converDBToAppFormat(tempInfo[2]
							.trim(), "yyyyMM", "dd-MMM-yyyy");
					bean.setMonthYear(monthYear);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}
				String vda = "", fda = "";
				if (!tempInfo[9].equals("XXX")) {
					vda = tempInfo[9].trim();
				} else {
					vda = "0.00";
				}
				if (!tempInfo[10].equals("XXX")) {
					fda = tempInfo[10].trim();
				} else {
					fda = "0.00";
				}
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic())
						+ Float.parseFloat(vda) + Float.parseFloat(fda));
				bean.setEmoluments(emoluments);

				if (!tempInfo[11].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[11].trim());
				} else
					bean.setEmpPfStatuary("0.00");

				if (!tempInfo[12].equals("XXX")) {
					bean.setEmpVpf(tempInfo[12].trim());
				} else
					bean.setEmpVpf("0.00");

				if (!tempInfo[13].equals("XXX")) {
					bean.setPrincipal(tempInfo[13].trim());
				} else
					bean.setPrincipal("0.00");

				if (!tempInfo[14].equals("XXX")) {
					bean.setInterest(tempInfo[14].trim());
				} else
					bean.setInterest("0.00");
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				log.info("vpf" + bean.getEmpVpf() + "principle "
						+ bean.getPrincipal() + "interest "
						+ bean.getInterest() + "pfstatutory"
						+ bean.getEmpPfStatuary());
				log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth"
						+ bean.getDateofBirth() + " dateofjoin "
						+ bean.getDateofJoining() + "empToal "
						+ bean.getEmpTotal());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "IGICargo IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,vda,fda,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "IGICargo IAD".trim());
					pst.setString(8, bean.getMonthYear().trim());
					pst.setString(9, bean.getEmployeeName().trim());
					pst.setString(10, bean.getBasic().trim());
					pst.setString(11, vda.trim());
					pst.setString(12, fda.trim());

					pst.setString(13, bean.getPrincipal().trim());
					pst.setString(14, bean.getInterest().trim());
					pst.setString(15, bean.getEmployeeNewNo().trim());
					pst.setString(16, bean.getDesignation().trim());
					pst.setString(17, bean.getRemarks().trim());
					pst.setString(18, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialIGICargoIADCPF leaving method ");
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataCSIAIAD(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataCSIAIAD() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setMonthYear(tempInfo[2]);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[6].trim());
				} else
					bean.setEmpPfStatuary("0.00");
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmoluments(tempInfo[7].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setPensionOption(tempInfo[8].trim());
				} else
					bean.setPensionOption("");

				log.info("cpfaccno " + bean.getCpfAccNo());
				// for Northern Region checking cpfaccno instead employeename
				// employeeName = commonDAO.checkEmployeeInfo(con,
				// bean.getEmployeeNewNo(), bean.getCpfAccNo(),
				// bean.getEmployeeName());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,WETHEROPTION) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "CSIA IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.setString(7, bean.getPensionOption().trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region)"
						+ " values(?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, "CSIA IAD".trim());
					pst.setString(5, bean.getMonthYear().trim());
					pst.setString(6, bean.getEmployeeName().trim());
					pst.setString(7, bean.getEmployeeNewNo().trim());
					pst.setString(8, bean.getDesignation().trim());
					pst.setString(9, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataCSIAIAD count"
								+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataDPOIAD(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataDPOIAD() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					monthYear = commonUtil.converDBToAppFormat(tempInfo[2]
							.trim(), "yyyyMM", "dd-MMM-yyyy");
					bean.setMonthYear(monthYear);
				} else {
					bean.setMonthYear("");
				}
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else
					bean.setBasic("0.00");

				String pPay = "";
				if (!tempInfo[7].equals("XXX")) {
					pPay = tempInfo[7].trim();
				} else {
					pPay = "0.00";
				}
				String sPay = "";
				if (!tempInfo[8].equals("XXX")) {
					sPay = tempInfo[8].trim();
				} else
					sPay = "0.00";

				String vda = "";
				if (!tempInfo[9].equals("XXX")) {
					vda = tempInfo[9].trim();
				} else
					vda = "0.00";
				String fda = "";
				if (!tempInfo[10].equals("XXX")) {
					fda = tempInfo[10].trim();
				} else
					fda = "0.00";
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic())
						+ Float.parseFloat(vda)
						+ Float.parseFloat(fda)
						+ Float.parseFloat(sPay) + Float.parseFloat(pPay));
				bean.setEmoluments(emoluments);

				log.info("Emoluments " + bean.getEmoluments());
				String cpf = "";
				if (!tempInfo[11].equals("XXX")) {
					cpf = tempInfo[11].trim();
					bean.setEmpPfStatuary(cpf);
				} else
					bean.setEmpPfStatuary("0.00");

				if (!tempInfo[12].equals("XXX")) {
					bean.setEmpVpf(tempInfo[12].trim());
				} else
					bean.setEmpVpf("0.00");
				if (!tempInfo[13].equals("XXX")) {
					bean.setPrincipal(tempInfo[13].trim());
				} else
					bean.setPrincipal("0.00");
				if (!tempInfo[14].equals("XXX")) {
					bean.setInterest(tempInfo[14].trim());
				} else
					bean.setInterest("0.00");

				// TOTAL=PFSTATUTARY+VPF+CPF ADV RECOVERY(O)+INTEREST
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				log.info("emp total " + bean.getEmpTotal());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "DPO IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					pst.setString(6, "CHQIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,vda,fda,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,dnspay,spay,employeeno,DESEGNATION,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region
				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, "DPO IAD".trim());
					pst.setString(5, bean.getMonthYear().trim());
					pst.setString(6, bean.getEmployeeName().trim());
					pst.setString(7, bean.getBasic().trim());
					pst.setString(8, vda.trim());
					pst.setString(9, fda.trim());
					pst.setString(10, bean.getPrincipal().trim());
					pst.setString(11, bean.getInterest().trim());
					pst.setString(12, pPay.trim());
					pst.setString(13, sPay.trim());
					pst.setString(14, bean.getEmployeeNewNo().trim());
					pst.setString(15, bean.getDesignation().trim());
					pst.setString(16, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log.info("FinaceDAO :readXLSFinalcialDataDPOIAD count" + count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataCAPIAD(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataCAPIAD() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[2].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				String month = "";
				if (!tempInfo[4].equals("XXX")) {
					month = tempInfo[4].toString().trim();
				} else {
					month = "";
				}
				String year = "";
				if (!tempInfo[5].equals("XXX")) {
					year = tempInfo[5].toString().trim();
				} else {
					year = "";
				}
				String fullMonthYear = "";
				if (!year.equals("") && !month.equals("")) {
					fullMonthYear = "01-" + month + "-" + year;
					fullMonthYear = commonUtil.converDBToAppFormat(
							fullMonthYear, "dd-MM-yyyy", "dd-MMM-yyyy");
					bean.setMonthYear(fullMonthYear);
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}

				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesignation(tempInfo[3]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setEmoluments(tempInfo[6].trim());
				} else
					bean.setEmoluments("0.00");
				log.info("Emoluments " + bean.getEmoluments());
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[7].trim());
				} else {
					bean.setEmpPfStatuary("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setEmpVpf(tempInfo[8].trim());
				} else
					bean.setEmpVpf("0.00");
				if (!tempInfo[9].equals("XXX")) {
					bean.setCpfArriers(tempInfo[9].trim());
				} else
					bean.setCpfArriers("0.00");
				if (!tempInfo[10].equals("XXX")) {
					bean.setPrincipal(tempInfo[10].trim());
				} else
					bean.setPrincipal("0.00");
				if (!tempInfo[11].equals("XXX")) {
					bean.setInterest(tempInfo[11].trim());
				} else
					bean.setInterest("0.00");
				if (!tempInfo[12].equals("XXX")) {
					bean.setPensionOption(tempInfo[12].trim());
				} else
					bean.setPensionOption("");
				if (!tempInfo[13].equals("XXX")) {
					bean.setDateofBirth(tempInfo[13].trim());
				} else
					bean.setDateofBirth("");
				if (!tempInfo[14].equals("XXX")) {
					bean.setDateofJoining(tempInfo[14].trim());
				} else
					bean.setDateofJoining("");
				log.info("pensionOption " + bean.getPensionOption() + "dob "
						+ bean.getDateofBirth() + "doj"
						+ bean.getDateofJoining());
				// TOTAL=PFSTATUTARY+VPF+CPF ADV RECOVERY(O)+INTEREST
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				log.info("emp total " + bean.getEmpTotal());

				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo().trim(), bean.getCpfAccNo().trim(),
						bean.getEmployeeName().trim(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String pensionNumber = "";
					if (bean.getDateofBirth() != "") {
						pensionNumber = commonDAO.getPensionNumberError(bean
								.getEmployeeName().trim(), bean
								.getDateofBirth().trim(), bean.getCpfAccNo()
								.trim(), "");
						pensionNumber = commonDAO.checkPensionNumber(
								pensionNumber, bean.getCpfAccNo().trim(), bean
										.getEmployeeNewNo(), bean
										.getEmployeeName().trim(), "CHQIAD",
								bean.getDateofBirth().trim());
					}
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,WETHEROPTION,dateofbirth,dateofjoining,pensionnumber) VALUES "
							+ "(?,?,?,?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "CAP IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					pst.setString(6, "CHQIAD".trim());
					pst.setString(7, bean.getPensionOption());
					pst.setString(8, bean.getDateofBirth());
					pst.setString(9, bean.getDateofJoining());
					pst.setString(10, pensionNumber);
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,CPFARREAR,employeeno,DESEGNATION,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";
				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, "CAP IAD".trim());
					pst.setString(5, bean.getMonthYear().trim());
					pst.setString(6, bean.getEmployeeName().trim());
					pst.setString(7, bean.getPrincipal().trim());
					pst.setString(8, bean.getInterest().trim());
					pst.setString(9, bean.getCpfArriers());
					pst.setString(10, bean.getEmployeeNewNo().trim());
					pst.setString(11, bean.getDesignation().trim());
					pst.setString(12, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log.info("FinaceDAO :readXLSFinalcialDataCAPIAD count" + count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataVOTVIAD(String xlsData)
			throws InvalidDataException {
		log.info("FinaceDAO :readXLSFinalcialDataVOTVIAD() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {

				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[2].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					String empName = tempInfo[2];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				// Emoluments=Pay(G)
				// PFSTATUTARY=CPFDED(H)
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmoluments(tempInfo[7].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				log.info("Emoluments " + bean.getEmoluments());
				if (!tempInfo[8].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[8].trim());
				} else
					bean.setEmpPfStatuary("0.00");
				String month = "";
				if (!tempInfo[4].equals("XXX")) {
					month = tempInfo[4].toString().trim();
				} else {
					month = "";
				}
				String year = "";
				if (!tempInfo[5].equals("XXX")) {
					year = tempInfo[5].toString().trim();
				} else {
					year = "";
				}
				String fullMonthYear = "";
				if (!year.equals("") && !month.equals("")) {
					fullMonthYear = "01-" + month + "-" + year;
					fullMonthYear = commonUtil.converDBToAppFormat(
							fullMonthYear, "dd-MM-yyyy", "dd-MMM-yyyy");
					bean.setMonthYear(fullMonthYear);
				}
				log.info("year  " + bean.getMonthYear());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,region) VALUES "
							+ "(?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "TRIVANDRUM IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, "CHQIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,region)"
						+ " values(?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region
				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, "TRIVANDRUM IAD".trim());
					pst.setString(5, bean.getMonthYear().trim());
					pst.setString(6, bean.getEmployeeName().trim());
					pst.setString(7, bean.getEmployeeNewNo().trim());
					pst.setString(8, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataVOTVIAD count"
								+ count);
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

	}

	/*
	 * For IGI IAD 2002-2009
	 */

	public void readXLSFinalcialIGIIADCPF(String xlsData) throws Exception {
		log.info("FinaceDAO :readXLSFinalcialIGIIADCPF() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					monthYear = commonUtil.converDBToAppFormat(tempInfo[2]
							.trim(), "yyMM", "dd-MMM-yyyy");
					bean.setMonthYear(monthYear);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}

				log.info("emp name " + bean.getEmployeeName());

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}

				String dnspay = "";
				if (!tempInfo[7].equals("XXX")) {
					dnspay = tempInfo[7].trim();
				} else {
					dnspay = "0.00";
				}

				String spay = "";
				if (!tempInfo[8].equals("XXX")) {
					spay = tempInfo[8].trim();
				} else {
					spay = "0.00";
				}

				String vda = "";
				if (!tempInfo[9].equals("XXX")) {
					vda = tempInfo[9].trim();
				} else {
					vda = "0.00";
				}

				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic())
						+ Float.parseFloat(dnspay)
						+ Float.parseFloat(spay)
						+ Float.parseFloat(vda));
				log.info("emoluments  " + emoluments);
				bean.setEmoluments(emoluments);

				if (!tempInfo[10].equals("XXX")) {
					bean.setEmpPfStatuary(tempInfo[10].trim());
				} else
					bean.setEmpPfStatuary("0.00");

				if (!tempInfo[11].equals("XXX")) {
					bean.setEmpVpf(tempInfo[11].trim());
				} else
					bean.setEmpVpf("0.00");

				if (!tempInfo[12].equals("XXX")) {
					bean.setPfAdvance(tempInfo[12].trim());
				} else
					bean.setPfAdvance("0.00");

				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else
					bean.setInterest("0.00");
				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPfAdvance())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				if (!tempInfo[5].equals("XXX")) {
					bean.setAirportCode(tempInfo[5].trim());
				} else {
					bean.setAirportCode("");
				}
				log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth"
						+ bean.getDateofBirth() + " dateofjoin "
						+ bean.getDateofJoining() + "empToal "
						+ bean.getEmpTotal());
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "IGI IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,dnspay,spay,vda,advance,EMPADVRECINTEREST,employeeno,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "IGI IAD".trim());
					pst.setString(8, bean.getMonthYear().trim());
					pst.setString(9, bean.getEmployeeName().trim());
					pst.setString(10, bean.getBasic().trim());
					pst.setString(11, dnspay.trim());
					pst.setString(12, spay.trim());
					pst.setString(13, vda.trim());
					pst.setString(14, bean.getPfAdvance().trim());
					pst.setString(15, bean.getInterest().trim());
					pst.setString(16, bean.getEmployeeNewNo().trim());
					pst.setString(17, bean.getRemarks().trim());
					pst.setString(18, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialIGIIADCPF leaving method ");
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			con.close();
		}
	}

	public void readXLSFinalcialIGIIADCPF1995to2000(String xlsData,
			String username, String computername) throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialIGIIADCPF1995to2000() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}

		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			st = con.createStatement();
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					// monthYear =
					// commonUtil.converDBToAppFormat(tempInfo[3].trim(),
					// "yyMM", "dd-MMM-yyyy");
					bean.setMonthYear(tempInfo[2].trim());
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}

				log.info("emp name " + bean.getEmployeeName());

				if (!tempInfo[4].equals("XXX")) {
					bean.setBasic(tempInfo[4].trim());
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[5].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setPrincipal(tempInfo[8].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setInterest(tempInfo[9].trim());
				} else {
					bean.setInterest("0.00");
				}
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic()));
				bean.setEmoluments(emoluments);

				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

				Date transdate = df.parse(bean.getMonthYear());
				float cpfarrear = 0;
				if (transdate.after(new Date("31-Mar-1998"))) {
					bean.setEmpPfStatuary(String.valueOf(Float.parseFloat(bean
							.getEmoluments()) * 12 / 100));
				} else {
					bean.setEmpPfStatuary(String.valueOf(Float.parseFloat(bean
							.getEmoluments()) * 10 / 100));
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmpVpf(tempInfo[7].trim());
				} else
					bean.setEmpVpf("0.00");

				String empTotal = String.valueOf(Float.parseFloat(bean
						.getEmpPfStatuary())
						+ Float.parseFloat(bean.getEmpVpf())
						+ Float.parseFloat(bean.getPrincipal())
						+ Float.parseFloat(bean.getInterest()));
				bean.setEmpTotal(empTotal.trim());
				bean.setAaiTotal(bean.getEmpTotal());
				log.info("aaiTotal " + bean.getAaiTotal());
				double pensioncontri = 0.00, pf = 0.00;
				if (!bean.getCpfAccNo().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption from employee_info where cpfacno='"
							+ bean.getCpfAccNo() + "'";
					log.info(checkPFID);
					rs = st.executeQuery(checkPFID);
					/*
					 * while(rs.next()){ if
					 * (!rs.getString("wetheroption").equals(null)&&
					 * rs.getString("wetheroption").trim()!="") {
					 * log.info("wether option " +rs.getString("wetheroption"));
					 * bean.setPensionOption(rs.getString("wetheroption")); } }
					 */

					pensioncontri = fDao.pensionCalculation(
							bean.getMonthYear(), bean.getEmoluments(), bean
									.getPensionOption(), bean.getRegion(),"");
					log.info("pensioncontri... " + pensioncontri);
					log.info("pf " + bean.getEmpPfStatuary().toString());
					pf = Double.parseDouble(bean.getEmpPfStatuary().toString())
							- pensioncontri;

				}
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,remarks) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "IGI IAD".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.setString(7, "new records from IGIIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,empvpf,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,employeeno,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				// two perameters added for southern region for checking
				// duplicate record i.e employeeno, region

				String foundSalaryDate = this.checkFinanceDuplicate(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "CHQIAD");
				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, bean.getEmpTotal().trim());
					pst.setString(6, bean.getAaiTotal().trim());
					pst.setString(7, "IGI IAD".trim());
					pst.setString(8, bean.getMonthYear().trim());
					pst.setString(9, bean.getEmployeeName().trim());
					pst.setString(10, bean.getBasic().trim());
					pst.setString(11, bean.getPrincipal().trim());
					pst.setString(12, bean.getInterest().trim());
					pst.setString(13, bean.getEmployeeNewNo().trim());
					pst.setString(14, bean.getRemarks().trim());
					pst.setString(15, "CHQIAD".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					Statement st1 = con.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					log.info("month year " + bean.getMonthYear());
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getMonthYear().trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					if (!bean.getEmpVpf().equals("0.00")) {
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ bean.getEmoluments()
								+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmpPfStatuary().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmpVpf().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAccNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";
						log.info("update emolumentss " + insertArrearCount);
						count = st.executeUpdate(insertArrearCount);

					}
					st1.close();
				}

				log
						.info("FinaceDAO :readXLSFinalcialIGIIADCPF1995to2000 leaving method ");
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataRAUSAP(String xlsData) throws Exception {
		log.info("FinaceDAO :readXLSFinalcialDataRAUSAP() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					monthYear = commonUtil.converDBToAppFormat(tempInfo[2]
							.trim(), "MM/yy", "dd-MMM-yyyy");
					bean.setMonthYear(monthYear);
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}

				/*
				 * String cpfCount = ""; if (!tempInfo[8].equals("XXX")) {
				 * cpfCount = tempInfo[8].trim(); } else { cpfCount = "0.00"; }
				 * 
				 * String cpfArriers = ""; if (!tempInfo[9].equals("XXX")) {
				 * bean.setCpfArriers(tempInfo[9].trim()); } else {
				 * bean.setCpfArriers("0.00"); } log.info("basic " +
				 * bean.getBasic());
				 * 
				 * 
				 * if (!tempInfo[10].equals("XXX")) {
				 * bean.setPfAdvance(tempInfo[10].trim()); } else
				 * bean.setPfAdvance("0.00");
				 * 
				 * String pfwPaid = ""; if (!tempInfo[10].equals("XXX")) {
				 * pfwPaid=tempInfo[10].trim(); } else{ pfwPaid="0.00"; }
				 * 
				 * String advanceRecovery = ""; if (!tempInfo[11].equals("XXX"))
				 * { advanceRecovery=tempInfo[11].trim(); } else{
				 * advanceRecovery="0.00"; }
				 * 
				 * if (!tempInfo[12].equals("XXX")) {
				 * bean.setRemarks(tempInfo[12].trim()); } else {
				 * bean.setRemarks(""); }
				 * 
				 * String emoluments =
				 * String.valueOf(((Float.parseFloat(cpfCount)+
				 * Float.parseFloat(bean.getCpfArriers()))100)/12);
				 * 
				 * log.info("emoluments " + emoluments);
				 * bean.setEmoluments(emoluments);
				 * 
				 * String pfStatuary=String.valueOf(Float.parseFloat(cpfCount)+
				 * Float.parseFloat(bean.getCpfArriers()));
				 * 
				 * log.info("pfStatuary " + pfStatuary);
				 * bean.setEmpPfStatuary(pfStatuary); String empTotal =
				 * String.valueOf(Float.parseFloat(cpfCount) +
				 * Float.parseFloat(bean.getCpfArriers()) +
				 * Float.parseFloat(advanceRecovery) -
				 * Float.parseFloat(bean.getPfAdvance()));
				 * bean.setEmpTotal(empTotal.trim());
				 * bean.setAaiTotal(bean.getEmpTotal());
				 * 
				 * log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth" +
				 * bean.getDateofBirth() + " dateofjoin " +
				 * bean.getDateofJoining() + "empToal " + bean.getEmpTotal());
				 */

				// logic from 01-07
				String cpf = "";
				if (!tempInfo[7].equals("XXX")) {
					cpf = tempInfo[7].trim();
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setCpfArriers(tempInfo[8].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				String emoluments = String
						.valueOf(((Float.parseFloat(cpf) + Float
								.parseFloat(bean.getCpfArriers())) * 100) / 12);
				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(bean.getCpfArriers()));
				log.info("emoluments  " + emoluments);
				log.info("pfStatuary  " + pfStatuary);
				bean.setEmpPfStatuary(pfStatuary);
				// update emoulements done on 8th Apr09(added update method
				// below)
				bean.setEmoluments(emoluments);

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmpVpf(tempInfo[9].trim());
				} else {
					bean.setEmpVpf("0.00");
				}
				String cpfadv = "";
				if (!tempInfo[10].equals("XXX")) {
					cpfadv = tempInfo[10].trim();
				} else {
					cpfadv = "0.00";
				}

				if (!tempInfo[11].equals("XXX")) {
					bean.setAdvanceDrawn(tempInfo[11].trim());
				} else {
					bean.setAdvanceDrawn("0.00");
				}
				String partAdv = "";
				if (!tempInfo[12].equals("XXX")) {
					partAdv = tempInfo[12].trim();
				} else {
					partAdv = "0.00";
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setRemarks(tempInfo[13].trim());
				} else {
					bean.setRemarks("");
				}

				String cpfacno = this.getCpfAccnoDetails(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "RAUSAP");
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "RAUSAP");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "RAUSAP".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "RAUSAP".trim());
					pst.executeUpdate();
					pst.close();
				}

				// logic from 95 -01
				/*
				 * String finaceInsert = "insert into
				 * employee_pension_validate(cpfaccno
				 * ,emoluments,emppfstatuary,emptotal,aaitotal,AIRPORTCODE," +
				 * "MONTHYEAR,employeeName,BASIC,PFCOUNT,CPFARREAR,ADVANCE,REFADV,employeeno,DESEGNATION,remarks,region)"
				 * + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 * 
				 * log.info(" employeeName" + employeeName + "Designation" +
				 * bean.getDesignation() + "CpfAccNo" +
				 * bean.getCpfAccNo().trim()); // two perameters added for
				 * southern region for checking // duplicate record i.e
				 * employeeno, region
				 * 
				 * String foundSalaryDate =
				 * this.checkFinanceDuplicateRAUSAP(con, bean
				 * .getMonthYear().trim(), bean.getCpfAccNo(), bean
				 * .getEmployeeNewNo(), "RAUSAP","");
				 * 
				 * if (foundSalaryDate.equals("")) { pst =
				 * con.prepareStatement(finaceInsert); pst.setString(1,
				 * bean.getCpfAccNo().trim()); pst.setString(2,
				 * bean.getEmoluments().trim()); pst.setString(3,
				 * bean.getEmpPfStatuary().trim()); pst.setString(4,
				 * bean.getEmpTotal().trim()); pst.setString(5,
				 * bean.getAaiTotal().trim()); pst.setString(6,
				 * "RAUSAP".trim()); pst.setString(7,
				 * bean.getMonthYear().trim()); pst.setString(8,
				 * bean.getEmployeeName().trim()); pst.setString(9,
				 * bean.getBasic().trim()); pst.setString(10, cpfCount.trim());
				 * pst.setString(11,bean.getCpfArriers().trim());
				 * pst.setString(12, bean.getPfAdvance().trim());
				 * pst.setString(13, advanceRecovery.trim()); pst.setString(14,
				 * bean.getEmployeeNewNo().trim()); pst.setString(15,
				 * bean.getDesignation().trim()); pst.setString(16,
				 * bean.getRemarks().trim()); pst.setString(17,
				 * "RAUSAP".trim()); count = pst.executeUpdate(); count++;
				 * pst.close(); }
				 */

				// logic from 01-08
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,EMPVPF,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,CPFARREAR,REFADV,ADVANCE,pfw,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				String foundSalaryDate = this.checkFinanceDuplicateRAUSAP(con,
						bean.getMonthYear().trim(), cpfacno.trim(), bean
								.getEmployeeNewNo(), "RAUSAP", "");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, cpfacno.trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "RAUSAP".trim());
					pst.setString(6, bean.getMonthYear().trim());
					pst.setString(7, bean.getEmployeeName().trim());
					pst.setString(8, bean.getBasic().trim());
					pst.setString(9, cpf.trim());
					pst.setString(10, bean.getCpfArriers());
					pst.setString(11, cpfadv.trim());
					pst.setString(12, bean.getAdvanceDrawn());
					pst.setString(13, partAdv.trim());
					pst.setString(14, bean.getEmployeeNewNo().trim());
					pst.setString(15, bean.getDesignation().trim());
					pst.setString(16, bean.getRemarks().trim());
					pst.setString(17, "RAUSAP".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					try {
						String finaceUpdate = "update employee_pension_validate set emoluments='"
								+ bean.getEmoluments()
								+ "'  where cpfaccno='"
								+ cpfacno.trim()
								+ "' and "
								+ "employeeno='"
								+ bean.getEmployeeNewNo()
								+ "' and monthyear='"
								+ bean.getMonthYear().trim()
								+ "' and region='RAUSAP'";
						log.info(" finaceUpdate Rausap " + finaceUpdate);
						st = con.createStatement();
						count = st.executeUpdate(finaceUpdate);
						count++;
						st.close();
					} catch (Exception e) {
						log.printStackTrace(e);
					}
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataRAUSAP Leaving Method");
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			con.close();
		}
	}

	public void readXLSFinalcialDataRAUSAPMissing(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataRAUSAPMissing() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					// monthYear =
					// commonUtil.converDBToAppFormat(tempInfo[2].trim(),
					// "MM/yy", "dd-MMM-yyyy");
					bean.setMonthYear(tempInfo[2].trim());
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0.00");
				}

				/*
				 * String cpfCount = ""; if (!tempInfo[8].equals("XXX")) {
				 * cpfCount = tempInfo[8].trim(); } else { cpfCount = "0.00"; }
				 * 
				 * String cpfArriers = ""; if (!tempInfo[9].equals("XXX")) {
				 * bean.setCpfArriers(tempInfo[9].trim()); } else {
				 * bean.setCpfArriers("0.00"); } log.info("basic " +
				 * bean.getBasic());
				 * 
				 * 
				 * if (!tempInfo[10].equals("XXX")) {
				 * bean.setPfAdvance(tempInfo[10].trim()); } else
				 * bean.setPfAdvance("0.00");
				 * 
				 * String pfwPaid = ""; if (!tempInfo[10].equals("XXX")) {
				 * pfwPaid=tempInfo[10].trim(); } else{ pfwPaid="0.00"; }
				 * 
				 * String advanceRecovery = ""; if (!tempInfo[11].equals("XXX"))
				 * { advanceRecovery=tempInfo[11].trim(); } else{
				 * advanceRecovery="0.00"; }
				 * 
				 * if (!tempInfo[12].equals("XXX")) {
				 * bean.setRemarks(tempInfo[12].trim()); } else {
				 * bean.setRemarks(""); }
				 * 
				 * String emoluments =
				 * String.valueOf(((Float.parseFloat(cpfCount)+
				 * Float.parseFloat(bean.getCpfArriers()))100)/12);
				 * 
				 * log.info("emoluments " + emoluments);
				 * bean.setEmoluments(emoluments);
				 * 
				 * String pfStatuary=String.valueOf(Float.parseFloat(cpfCount)+
				 * Float.parseFloat(bean.getCpfArriers()));
				 * 
				 * log.info("pfStatuary " + pfStatuary);
				 * bean.setEmpPfStatuary(pfStatuary); String empTotal =
				 * String.valueOf(Float.parseFloat(cpfCount) +
				 * Float.parseFloat(bean.getCpfArriers()) +
				 * Float.parseFloat(advanceRecovery) -
				 * Float.parseFloat(bean.getPfAdvance()));
				 * bean.setEmpTotal(empTotal.trim());
				 * bean.setAaiTotal(bean.getEmpTotal());
				 * 
				 * log.info("cpfaccno " + bean.getCpfAccNo() + "dateofbirth" +
				 * bean.getDateofBirth() + " dateofjoin " +
				 * bean.getDateofJoining() + "empToal " + bean.getEmpTotal());
				 */

				// logic from 01-07
				String cpf = "";
				if (!tempInfo[7].equals("XXX")) {
					cpf = tempInfo[7].trim();
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setCpfArriers(tempInfo[8].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				String emoluments = String
						.valueOf(((Float.parseFloat(cpf) + Float
								.parseFloat(bean.getCpfArriers())) * 100) / 10);
				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(bean.getCpfArriers()));
				log.info("emoluments  " + emoluments);
				log.info("pfStatuary  " + pfStatuary);
				bean.setEmpPfStatuary(pfStatuary);
				// update emoulements done on 8th Apr09(added update method
				// below)
				bean.setEmoluments(emoluments);

				/*
				 * if (!tempInfo[9].equals("XXX")) {
				 * bean.setEmpVpf(tempInfo[9].trim()); } else {
				 * bean.setEmpVpf("0.00"); } String cpfadv = ""; if
				 * (!tempInfo[10].equals("XXX")) { cpfadv = tempInfo[10].trim();
				 * } else { cpfadv = "0.00"; }
				 * 
				 * if (!tempInfo[11].equals("XXX")) {
				 * bean.setAdvanceDrawn(tempInfo[11].trim()); } else {
				 * bean.setAdvanceDrawn("0.00"); } String partAdv = ""; if
				 * (!tempInfo[12].equals("XXX")) { partAdv =
				 * tempInfo[12].trim(); } else { partAdv = "0.00"; } if
				 * (!tempInfo[13].equals("XXX")) {
				 * bean.setRemarks(tempInfo[13].trim()); } else {
				 * bean.setRemarks(""); }
				 */

				//String cpfacno = this.getCpfAccnoDetails(con, bean.getMonthYear().trim(), bean.getCpfAccNo(), bean.getEmployeeNewNo(), "RAUSAP");
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "RAUSAP");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "RAUSAP".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "RAUSAP".trim());
					pst.executeUpdate();
					pst.close();
				}

				// logic from 95 -01

				String finaceInsert = "insert into  employee_pension_validate(cpfaccno,emoluments,emppfstatuary,emptotal,aaitotal,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,cpf,CPFARREAR,employeeno,DESEGNATION,remarks,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim()); // two perameters added
				// for

				String foundSalaryDate = this.checkFinanceDuplicateRAUSAP(con,
						bean.getMonthYear().trim(), bean.getCpfAccNo(), bean
								.getEmployeeNewNo(), "RAUSAP", "");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpTotal().trim());
					pst.setString(5, bean.getAaiTotal().trim());
					pst.setString(6, "RAUSAP".trim());
					pst.setString(7, bean.getMonthYear().trim());
					pst.setString(8, bean.getEmployeeName().trim());
					pst.setString(9, bean.getBasic().trim());
					pst.setString(10, cpf.trim());
					pst.setString(11, bean.getCpfArriers().trim());
					pst.setString(12, bean.getEmployeeNewNo().trim());
					pst.setString(13, bean.getDesignation().trim());
					pst.setString(14, bean.getRemarks().trim());
					pst.setString(15, "RAUSAP".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}

				// logic from 01-08
				/*
				 * String finaceInsert = "insert into
				 * employee_pension_validate(cpfaccno
				 * ,emoluments,emppfstatuary,EMPVPF,AIRPORTCODE," +
				 * "MONTHYEAR,employeeName,BASIC,cpf,CPFARREAR,REFADV,ADVANCE,pfw,employeeno,DESEGNATION,remarks,region)"
				 * + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; log.info("
				 * employeeName" + employeeName + "Designation" +
				 * bean.getDesignation() + "CpfAccNo" +
				 * bean.getCpfAccNo().trim()); String foundSalaryDate =
				 * this.checkFinanceDuplicateRAUSAP(con,
				 * bean.getMonthYear().trim(), cpfacno.trim(), bean
				 * .getEmployeeNewNo(), "RAUSAP", "");
				 * 
				 * if (foundSalaryDate.equals("")) { pst =
				 * con.prepareStatement(finaceInsert); pst.setString(1,
				 * cpfacno.trim()); pst.setString(2,
				 * bean.getEmoluments().trim()); pst.setString(3,
				 * bean.getEmpPfStatuary().trim()); pst.setString(4,
				 * bean.getEmpVpf().trim()); pst.setString(5, "RAUSAP".trim());
				 * pst.setString(6, bean.getMonthYear().trim());
				 * pst.setString(7, bean.getEmployeeName().trim());
				 * pst.setString(8, bean.getBasic().trim()); pst.setString(9,
				 * cpf.trim()); pst.setString(10, bean.getCpfArriers());
				 * pst.setString(11, cpfadv.trim()); pst.setString(12,
				 * bean.getAdvanceDrawn()); pst.setString(13, partAdv.trim());
				 * pst.setString(14, bean.getEmployeeNewNo().trim());
				 * pst.setString(15, bean.getDesignation().trim());
				 * pst.setString(16, bean.getRemarks().trim());
				 * pst.setString(17, "RAUSAP".trim()); count =
				 * pst.executeUpdate(); count++; pst.close(); } else { try {
				 * String finaceUpdate = "update employee_pension_validate set
				 * emoluments='" + bean.getEmoluments() + "' where cpfaccno='" +
				 * cpfacno.trim() + "' and " + "employeeno='" +
				 * bean.getEmployeeNewNo() + "' and monthyear='" +
				 * bean.getMonthYear().trim() + "' and region='RAUSAP'";
				 * log.info(" finaceUpdate Rausap " + finaceUpdate); st =
				 * con.createStatement(); count =
				 * st.executeUpdate(finaceUpdate); count++; st.close(); } catch
				 * (Exception e) { log.printStackTrace(e); } } log
				 * .info("FinaceDAO :readXLSFinalcialDataRAUSAP Leaving
				 * Method"); }
				 */
				finalcialDataList.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataRAUSAP200708(String xlsData)
			throws Exception {
		log
				.info("FinaceDAO :readXLSFinalcialDataRAUSAP200708() entering method");
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {
				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("Employee Code" + tempInfo[0].toString().trim()
						+ "CPFACCNO" + tempInfo[1].toString().trim()
						+ "Employee Name" + tempInfo[3].toString().trim());

				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					// monthYear =
					// commonUtil.converDBToAppFormat(tempInfo[2].trim(),
					// "MM/yy", "dd-MMM-yyyy");
					bean.setMonthYear(tempInfo[2].trim());
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {
					bean.setEmployeeName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					// log.info("getName " + bean.getEmployeeName());
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						log.info("tempName " + tempName);
						// tempName=empName.substring(index+1,empName.length());
						bean.setEmployeeName(tempName);
					}
				}
				log.info("emp name " + bean.getEmployeeName());
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}
				String payforCpf = "";
				if (!tempInfo[6].equals("XXX")) {
					payforCpf = tempInfo[6].trim();
				} else {
					payforCpf = "0.00";
				}
				String cpf = "";
				if (!tempInfo[7].equals("XXX")) {
					cpf = tempInfo[7].trim();
				} else {
					cpf = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setCpfArriers(tempInfo[8].trim());
				} else {
					bean.setCpfArriers("0.00");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmpVpf(tempInfo[9].trim());
				} else {
					bean.setEmpVpf("0.00");
				}
				log.info("vpf " + bean.getEmpVpf());
				String payArrear = "";
				if (!tempInfo[10].equals("XXX")) {
					payArrear = tempInfo[10].trim();
				} else {
					payArrear = "0.00";
				}
				String emoluments = String
						.valueOf((Float.parseFloat(payforCpf) + Float
								.parseFloat(payArrear)));
				String pfStatuary = String.valueOf(Float.parseFloat(cpf)
						+ Float.parseFloat(bean.getCpfArriers()));
				bean.setEmpPfStatuary(pfStatuary);
				bean.setEmoluments(emoluments);
				String cpfacno = this.getCpfAccnoDetails(con, bean
						.getMonthYear().trim(), bean.getCpfAccNo(), bean
						.getEmployeeNewNo(), "RAUSAP");
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "RAUSAP");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region) VALUES "
							+ "(?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "RAUSAP".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					pst.setString(6, "RAUSAP".trim());
					pst.executeUpdate();
					pst.close();
				}

				// logic from 01-08
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,EMPVPF,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,cpf,CPFARREAR,payforcpf,payArrear,employeeno,DESEGNATION,region)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				String foundSalaryDate = this.checkFinanceDuplicateRAUSAP(con,
						bean.getMonthYear().trim(), cpfacno.trim(), bean
								.getEmployeeNewNo(), "RAUSAP", "");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, bean.getEmpVpf().trim());
					pst.setString(5, "RAUSAP".trim());
					pst.setString(6, bean.getMonthYear().trim());
					pst.setString(7, bean.getEmployeeName().trim());
					// pst.setString(8, bean.getBasic().trim());
					pst.setString(8, cpf.trim());
					pst.setString(9, bean.getCpfArriers().trim());
					pst.setString(10, payforCpf.trim());
					pst.setString(11, payArrear.trim());
					// pst.setString(11,bean.getBasic().trim());
					pst.setString(12, bean.getEmployeeNewNo().trim());
					pst.setString(13, bean.getDesignation().trim());
					pst.setString(14, "RAUSAP".trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				} else {
					try {
						String finaceUpdate = "update employee_pension_validate set emoluments='"
								+ bean.getEmoluments()
								+ "'  where cpfaccno='"
								+ cpfacno.trim()
								+ "' and "
								+ "employeeno='"
								+ bean.getEmployeeNewNo()
								+ "' and monthyear='"
								+ bean.getMonthYear().trim()
								+ "' and region='RAUSAP'";
						log.info(" finaceUpdate Rausap " + finaceUpdate);
						st = con.createStatement();
						count = st.executeUpdate(finaceUpdate);
						count++;
						st.close();
					} catch (Exception e) {
						log.printStackTrace(e);
					}
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataRAUSAP Leaving Method");
			}
			// finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			con.close();
		}
	}

	public void readXLSFinalcialDataOfficeComplex(String xlsData)
			throws InvalidDataException {
		log
				.info("FinaceDAO :readXLSFinalcialDataOfficeComplex() entering method");
		int maxValue = 0;
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ArrayList xlsDataList = new ArrayList();
		ArrayList finalcialDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinacialDataBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String airportCode = xlsDataList.get(0).toString();
		String monthYear = xlsDataList.get(1).toString();
		if (airportCode.lastIndexOf(",") != -1) {
			airportCode = airportCode.substring(
					airportCode.lastIndexOf(",") + 1, airportCode.length() - 1);
		}
		if (monthYear.lastIndexOf("MONTH OF") != -1) {
			monthYear = monthYear.substring(
					monthYear.lastIndexOf("MONTH OF") + 8,
					monthYear.length() - 1);
		}
		try {
			con = commonDB.getConnection();
			String employeeName = "";
			int count = 0, countEmployeeInfo = 0;
			for (int i = 0; i < xlsDataList.size(); i++) {

				// st=con.createStatement();
				bean = new FinacialDataBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmployeeNewNo(tempInfo[0]);
				} else {
					bean.setEmployeeNewNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAccNo(tempInfo[1].trim());
				} else {
					bean.setCpfAccNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					//monthYear=commonUtil.converDBToAppFormat(tempInfo[2].trim(
					// ),"MM/yy",
					// "dd-MMM-yyyy");
					bean.setMonthYear(tempInfo[2].trim());
				} else {
					bean.setMonthYear("");
				}
				log.info("year  " + bean.getMonthYear());
				if (!tempInfo[3].equals("XXX")) {
					String empName = tempInfo[3];
					int empNameLength = empName.length();
					if (empName.charAt(empNameLength - 1) == '.') {
						empName = empName.substring(0, empNameLength - 1);
					}
					bean.setEmployeeName(empName);
				} else {

					bean.setEmployeeName("");
				}
				int startIndxName = 0;
				int endIndxName = 1;
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmployeeName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmployeeName().toLowerCase()
								.replaceAll(quats[j].toLowerCase(), "").trim();
						bean.setEmployeeName(tempName);
					}
				}
			
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesignation(tempInfo[4]);
				} else {
					bean.setDesignation("");
				}

				if (!tempInfo[6].equals("XXX")) {
					bean.setBasic(tempInfo[6].trim());
				} else {
					bean.setBasic("0");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[10].trim());
				} else {
					bean.setDailyAllowance("0");
				}
				String daArrear = "";
				if (!tempInfo[11].equals("XXX")) {
					daArrear = tempInfo[11].trim();
				} else {
					daArrear = "0";
				}
				log.info(bean.getBasic() + "  " + bean.getDailyAllowance()
						+ " " + daArrear);
				String emoluments = String.valueOf((Float.parseFloat(bean
						.getBasic()) + Float.parseFloat(bean
						.getDailyAllowance()
						+ Float.parseFloat(daArrear))));
				bean.setEmoluments(emoluments);

				String pfCount = "";
				if (!tempInfo[7].equals("XXX")) {
					pfCount = tempInfo[7].trim();
				} else {
					pfCount = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setCpfArriers(tempInfo[8].trim());
				} else {
					bean.setCpfArriers("0.00");
				}

				String pfStatuary = String.valueOf(Float.parseFloat(pfCount)
						+ Float.parseFloat(bean.getCpfArriers()));
				bean.setEmpPfStatuary(pfStatuary);
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmpVpf(tempInfo[9].trim());
				} else {
					bean.setEmpVpf("0.00");
				}
				String refAdv = "";
				if (!tempInfo[22].equals("XXX")) {
					refAdv = tempInfo[22].trim();
				} else {
					refAdv = "0.00";
				}
				String dedAdv = "";
				if (!tempInfo[24].equals("XXX")) {
					dedAdv = tempInfo[24].trim();
				} else {
					dedAdv = "0.00";
				}

				String empAdvanceReCPrincipal = "";
				empAdvanceReCPrincipal = String.valueOf(Float
						.parseFloat(refAdv)
						+ Float.parseFloat(dedAdv));

				String empAdvrecInterest = "";
				if (!tempInfo[25].equals("XXX")) {
					empAdvrecInterest = tempInfo[25].trim();
				} else {
					empAdvrecInterest = "0.00";
				}

				// String cpfacno= this.getCpfAccnoDetails(con,
				// bean.getMonthYear().trim(), bean.getCpfAccNo(),
				// bean.getEmployeeNewNo(), "RAUSAP");
				log.info("emoluments  " + emoluments + "pfStatuary  "
						+ pfStatuary + "empAdvanceReCPrincipal "
						+ empAdvanceReCPrincipal + "empAdvrecInterest "
						+ empAdvrecInterest + "dedAdv" + dedAdv + "refAdv"
						+ refAdv);
				countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
						.getEmployeeNewNo(), bean.getCpfAccNo(), bean
						.getEmployeeName(), "CHQIAD");
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,remarks) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, "OFFICE COMPLEX".trim());
					pst.setString(3, bean.getEmployeeNewNo().trim());
					pst.setString(4, bean.getEmployeeName().trim());
					pst.setString(5, bean.getDesignation().trim());
					// for southern region Data
					pst.setString(6, "CHQIAD".trim());
					pst.setString(7, "new records from IGIIAD".trim());
					pst.executeUpdate();
					pst.close();
				}
				// logic from 01-08
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,BASIC,PFCOUNT ,CPFARREAR,employeeno,DESEGNATION,region,EMPVPF,DAILYALLOWANCE,DAARREAR,EMPADVRECPRINCIPAL,REFADV,DEDADV,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				log.info(" employeeName" + employeeName + "Designation"
						+ bean.getDesignation() + "CpfAccNo"
						+ bean.getCpfAccNo().trim());
				String foundSalaryDate = this.checkFinanceDuplicateRAUSAP(con,
						bean.getMonthYear().trim(), bean.getCpfAccNo(), bean
								.getEmployeeNewNo(), "CHQIAD", "");

				if (foundSalaryDate.equals("")) {
					pst = con.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAccNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmpPfStatuary().trim());
					pst.setString(4, "OFFICE COMPLEX".trim());
					pst.setString(5, bean.getMonthYear().trim());
					pst.setString(6, bean.getEmployeeName().trim());
					pst.setString(7, bean.getBasic().trim());
					pst.setString(8, pfCount.trim());
					pst.setString(9, bean.getCpfArriers());
					pst.setString(10, bean.getEmployeeNewNo().trim());
					pst.setString(11, bean.getDesignation().trim());
					pst.setString(12, "CHQIAD".trim());
					pst.setString(13, bean.getEmpVpf().trim());

					pst.setString(14, bean.getDailyAllowance().trim());
					pst.setString(15, daArrear.trim());
					pst.setString(16, empAdvanceReCPrincipal.trim());
					pst.setString(17, refAdv.trim());
					pst.setString(18, dedAdv.trim());
					pst.setString(19, empAdvrecInterest.trim());
					count = pst.executeUpdate();
					count++;
					pst.close();
				}
				log
						.info("FinaceDAO :readXLSFinalcialDataOfficeComplex Leaving Method");
			}
			finalcialDataList.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void readXLSFinalcialDataOfficeComplex1(String xlsData)
			throws Exception {
		// old logic commented
		/*
		 * log .info("FinaceDAO :readXLSFinalcialDataOfficeComplex() entering
		 * method"); int maxValue = 0; Connection con = null; Statement st =
		 * null; PreparedStatement pst = null; ArrayList xlsDataList = new
		 * ArrayList(); ArrayList finalcialDataList = new ArrayList();
		 * xlsDataList = commonUtil.getTheList(xlsData, "***"); FinacialDataBean
		 * bean = null; String tempInfo[] = null; String tempData = ""; String
		 * quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." }; String
		 * airportCode = xlsDataList.get(0).toString(); String monthYear =
		 * xlsDataList.get(1).toString(); if (airportCode.lastIndexOf(",") !=
		 * -1) { airportCode = airportCode.substring(
		 * airportCode.lastIndexOf(",") + 1, airportCode.length() - 1); }
		 * 
		 * if (monthYear.lastIndexOf("MONTH OF") != -1) { monthYear =
		 * monthYear.substring( monthYear.lastIndexOf("MONTH OF") + 8,
		 * monthYear.length() - 1); } try { con = commonDB.getConnection();
		 * String employeeName = ""; int count = 0, countEmployeeInfo = 0; for
		 * (int i = 0; i < xlsDataList.size(); i++) { //
		 * st=con.createStatement(); bean = new FinacialDataBean(); tempData =
		 * xlsDataList.get(i).toString(); tempInfo = tempData.split("@");
		 * log.info("Employee Code" + tempInfo[0].toString().trim() + "CPFACCNO"
		 * + tempInfo[1].toString().trim() + "Employee Name" +
		 * tempInfo[3].toString().trim());
		 * 
		 * if (!tempInfo[0].equals("XXX")) { bean.setEmployeeNewNo(tempInfo[0]);
		 * } else { bean.setEmployeeNewNo(""); }
		 * 
		 * if (!tempInfo[1].equals("XXX")) {
		 * bean.setCpfAccNo(tempInfo[1].trim()); } else { bean.setCpfAccNo("");
		 * } if (!tempInfo[2].equals("XXX")) { //
		 * monthYear=commonUtil.converDBToAppFormat(tempInfo[2].trim(),"MM/yy",
		 * // "dd-MMM-yyyy"); bean.setMonthYear(tempInfo[2].trim()); } else {
		 * bean.setMonthYear(""); } log.info("year " + bean.getMonthYear()); if
		 * (!tempInfo[3].equals("XXX")) { String empName = tempInfo[3]; int
		 * empNameLength = empName.length(); if (empName.charAt(empNameLength -
		 * 1) == '.') { empName = empName.substring(0, empNameLength - 1); }
		 * bean.setEmployeeName(empName); } else {
		 * 
		 * bean.setEmployeeName(""); } int startIndxName = 0; int endIndxName =
		 * 1; String tempName = ""; for (int j = 0; j < quats.length; j++) {
		 * 
		 * if (bean.getEmployeeName().toLowerCase().indexOf(
		 * quats[j].toLowerCase()) != -1) { tempName =
		 * bean.getEmployeeName().toLowerCase()
		 * .replaceAll(quats[j].toLowerCase(), "").trim(); log.info("tempName "
		 * + tempName); // tempName=empName.substring(index+1,empName.length());
		 * bean.setEmployeeName(tempName); } } log.info("emp name " +
		 * bean.getEmployeeName()); if (!tempInfo[4].equals("XXX")) {
		 * bean.setDesignation(tempInfo[4]); } else { bean.setDesignation(""); }
		 * 
		 * if (!tempInfo[6].equals("XXX")) { bean.setBasic(tempInfo[6].trim());
		 * } else { bean.setBasic("0.00"); } String pfCount = ""; if
		 * (!tempInfo[7].equals("XXX")) { pfCount = tempInfo[7].trim(); } else {
		 * pfCount = "0.00"; } if (!tempInfo[8].equals("XXX")) {
		 * bean.setCpfArriers(tempInfo[8].trim()); } else {
		 * bean.setCpfArriers("0.00"); }
		 * 
		 * String emoluments = String .valueOf(((Float.parseFloat(pfCount) +
		 * Float .parseFloat(bean.getCpfArriers())) 100) / 12); String
		 * pfStatuary = String .valueOf((Float.parseFloat(pfCount) + Float
		 * .parseFloat(bean.getCpfArriers()) 10) / 12); log.info("emoluments " +
		 * emoluments); log.info("pfStatuary " + pfStatuary);
		 * bean.setEmoluments(emoluments); bean.setEmpPfStatuary(pfStatuary); if
		 * (!tempInfo[9].equals("XXX")) { bean.setEmpVpf(tempInfo[9].trim()); }
		 * else { bean.setEmpVpf("0.00"); } // String cpfacno=
		 * this.getCpfAccnoDetails(con, // bean.getMonthYear().trim(),
		 * bean.getCpfAccNo(), // bean.getEmployeeNewNo(), "RAUSAP");
		 * 
		 * countEmployeeInfo = commonDAO.checkEmployeeInfo(con, bean
		 * .getEmployeeNewNo(), bean.getCpfAccNo(), bean .getEmployeeName(),
		 * "CHQIAD"); if (countEmployeeInfo == 0) { String sql = "insert into
		 * employee_info(cpfacno,airportCode,
		 * employeeno,employeeName,desegnation,region) VALUES " +
		 * "(?,?,?,?,?,?)"; pst = con.prepareStatement(sql); pst.setString(1,
		 * bean.getCpfAccNo().trim()); pst.setString(2, "OFFICE
		 * COMPLEX".trim()); pst.setString(3, bean.getEmployeeNewNo().trim());
		 * pst.setString(4, bean.getEmployeeName().trim()); pst.setString(5,
		 * bean.getDesignation().trim()); // for southern region Data
		 * pst.setString(6, "CHQIAD".trim()); pst.executeUpdate(); pst.close();
		 * } // logic from 01-08 String finaceInsert = "insert into
		 * employee_pension_validate
		 * (cpfaccno,emoluments,emppfstatuary,AIRPORTCODE," +
		 * "MONTHYEAR,employeeName,BASIC,PFCOUNT
		 * ,CPFARREAR,employeeno,DESEGNATION,region,EMPVPF)" + "
		 * values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 * 
		 * log.info(" employeeName" + employeeName + "Designation" +
		 * bean.getDesignation() + "CpfAccNo" + bean.getCpfAccNo().trim());
		 * String foundSalaryDate = this.checkFinanceDuplicateRAUSAP(con,
		 * bean.getMonthYear().trim(), bean.getCpfAccNo(), bean
		 * .getEmployeeNewNo(), "CHQIAD", "");
		 * 
		 * if (foundSalaryDate.equals("")) { pst =
		 * con.prepareStatement(finaceInsert); pst.setString(1,
		 * bean.getCpfAccNo().trim()); pst.setString(2,
		 * bean.getEmoluments().trim()); pst.setString(3,
		 * bean.getEmpPfStatuary().trim()); pst.setString(4, "OFFICE
		 * COMPLEX".trim()); pst.setString(5, bean.getMonthYear().trim());
		 * pst.setString(6, bean.getEmployeeName().trim()); pst.setString(7,
		 * bean.getBasic().trim()); pst.setString(8, pfCount.trim());
		 * pst.setString(9, bean.getCpfArriers()); pst.setString(10,
		 * bean.getEmployeeNewNo().trim()); pst.setString(11,
		 * bean.getDesignation().trim()); pst.setString(12, "CHQIAD".trim());
		 * pst.setString(13, bean.getEmpVpf().trim()); count =
		 * pst.executeUpdate(); count++; pst.close(); } log .info("FinaceDAO
		 * :readXLSFinalcialDataOfficeComplex Leaving Method"); }
		 * finalcialDataList.add(bean); } catch (SQLException e) { // TODO
		 * Auto-generated catch block log.printStackTrace(e); } catch (Exception
		 * e) { // TODO Auto-generated catch block log.printStackTrace(e); }
		 * finally { con.close(); }
		 */}

	public String checkFinanceDuplicate(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region) {

		// log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		// System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
		/** for Eastern Region and Northern region* */
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yyyy", "-MMM-yy");
			String query="";
			if (region.equals("CHQIAD")) {
			 query = "select CPFACCNO as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "'  and empflag='Y' ";
			}else{
				 query = "select CPFACCNO as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "' and empflag='Y' ";
			}
			
			
			
			/* and activeflag =	'N'"; */

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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}
	public String checkFinanceDuplicate2(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region,String airportcode,String pfid) {

		// log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		// System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
		/** for Eastern Region and Northern region* */
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yyyy", "-MMM-yy");
			String query="";
			if (region.equals("CHQIAD")) {
			 query = "select pensionno as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and pensionno='"
					+ pfid
					+ "' and region='" + region + "' and lower(airportcode)='"+airportcode.toLowerCase()+"'  and empflag='Y' ";
			}else{
				 query = "select pensionno as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and pensionno='"
					+ pfid
					+ "' and region='" + region + "' and empflag='Y' ";
			}
			
			log.info("query" + query);
			
			/* and activeflag =	'N'"; */

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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	public FinacialDataBean getEmolumentsBean(Connection con, String fromDate,
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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return bean;
	}
	public FinacialDataBean getEmolumentsBean1(Connection con, String fromYear,
			String toYear, String employeeNo, String region,String Pensionno) {

		String foundEmpFlag = "";
		Statement st = null;
		ResultSet rs = null;
		FinacialDataBean bean = new FinacialDataBean();
		try {
				String query="";
				query = "select emoluments,EMPPFSTATUARY,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI from employee_Pension_validate where monthyear BETWEEN '"
					+ fromYear
					+ "' AND last_day('" + toYear + "') and pensionno='"+ Pensionno+ "'  and empflag='Y' ";	
			
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				if (rs.getString("emoluments") != null) {
					bean.setEmoluments(rs.getString("emoluments").trim());
				}
				if (rs.getString("EMPPFSTATUARY") != null) {
					bean.setEmpPfStatuary(rs.getString("EMPPFSTATUARY").trim());
				}
				if (rs.getString("EMPVPF") != null) {
					bean.setEmpVpf(rs.getString("EMPVPF").trim());
				}
				
				if (rs.getString("EMPADVRECPRINCIPAL") != null) {
					bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL").trim());
				}
				
				if (rs.getString("EMPADVRECINTEREST") != null) {
					bean.setInterest(rs.getString("EMPADVRECINTEREST").trim());
				}
				
				if (rs.getString("PENSIONCONTRI") != null) {
					bean.setPenContri(rs.getString("PENSIONCONTRI").trim());
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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return bean;
	}

	public String getEmolumentsBesedonPFID(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region, String PFID) {

		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yyyy", "-MMM-yy");
			String query = "select emoluments as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and pensionno='"
					+ PFID
					+ "'  and empflag='Y' ";
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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	public String checkFinanceDuplicate1(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region,
			String pensionno, String airportcode, String flag) {

		// log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "", tbl_name = "";
		Statement st = null;

		ResultSet rs = null;
		if (flag.equals("temp")) {
			tbl_name = "employee_pension_Validate_temp";
		} else {
			tbl_name = "employee_Pension_validate";
		}
		/** for Eastern Region and Northern region* */
		try {
			String query = "";
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yy", "-MMM-yy");
			if (region.equals("CHQIAD")) {
				query = "select pensionno as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and pensionno='"
						+ pensionno
						+ "' and empflag='Y' and airportcode='"
						+ airportcode
						+ "'";
			} else {
				query = "select pensionno as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear
						+ "' and pensionno='"
						+ pensionno
						+ "' and empflag='Y' and region='" + region + "' ";
			}
			log.info(query);
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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	public String checkFinanceArrear(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region) {

		log.info("PensionDAO :checkFinanceArrear() entering method ");
		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
		/** for Eastern Region and Northern region* */
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yy", "-MMM-yy");
			String query = "select CPFACCNO as COLUMNNM from employee_pension_arrear where to_char(ARREARDATE,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "'";/* and activeflag='N'"; */

			/*
			 * String query = "select CPFACCNO as COLUMNNM from
			 * employee_Pension_validate where monthyear='" + fromDate + "' and
			 * cpfaccno='" + cpfaccno + "' and region='"+region+"'";
			 */

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
		log.info("PensionDAO :checkFinanceArrear() leaving method");
		return foundEmpFlag;
	}

	public FinacialDataBean getEmployeeInfo(String employeeNo) {
		Statement st = null;
		ResultSet rs = null;
		FinacialDataBean bean = new FinacialDataBean();
		String query = "";
		try {
			Connection con = commonDB.getConnection();

			if (!employeeNo.equals("")) {
				query = "select employeeNo,employeeName,cpfacno,DESEGNATION from employee_info where employeeNo='"
						+ employeeNo + "' and region='CHQNAD'";
			}

			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {

				log.info("employee no" + rs.getString("employeeno"));
				if (rs.getString("employeeno") != null) {
					bean.setEmployeeNewNo(rs.getString("employeeno"));
				} else {
					bean.setEmployeeNewNo("");
				}

				if (rs.getString("desegnation") != null) {
					bean.setDesignation(rs.getString("desegnation"));
				} else {
					bean.setDesignation("");
				}

				if (rs.getString("cpfacno") != null) {
					bean.setCpfAccNo(rs.getString("cpfacno"));
				} else {
					bean.setCpfAccNo("");
				}

				if (rs.getString("employeename") != null) {
					bean.setEmployeeName(rs.getString("employeename"));
				} else {
					bean.setEmployeeName("");
				}

			}
		} catch (Exception e) {
			log.printStackTrace(e);

		}
		log.info("deseg " + bean.getDesignation());
		return bean;
	}

	private String getCpfAccnoDetails(Connection con, String monthYear,
			String cpfaccno, String employeeNo, String region) {
		String cpfacno = "";
		Statement st = null;
		ResultSet rs = null;
		String query = "select CPFACNO as COLUMNNM from employee_info where   region='"
				+ region + "'  and employeeno='" + employeeNo + "'";

		log.info("query is " + query);
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getString("COLUMNNM") != null) {
					cpfacno = rs.getString("COLUMNNM");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		return cpfacno;
	}

	public String checkFinanceDuplicateRAUSAP(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region,
			String airportcode) {
		log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "";
		Statement st = null;
		ResultSet rs = null;
		System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
		String query = "select CPFACCNO as COLUMNNM from employee_Pension_validate where monthyear='"
				+ fromDate
				+ "' and cpfaccno='"
				+ cpfaccno
				+ "' and region='"
				+ region + "'";// and employeeno='" + employeeNo + "'";

		log.info("query is " + query);
		try {
			// con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				if (rs.getString("COLUMNNM") != null) {
					foundEmpFlag = rs.getString("COLUMNNM");
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		log.info("FinanceDao :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	private String checkEmployeeMaster(Connection con, String empCode,
			String cpfaccno) {
		log.info("FinanceDAO :checkEmployeeMaster() entering method ");
		String foundEmpFlag = "", query = "";
		Statement st = null;

		ResultSet rs = null;
		if (!cpfaccno.equals("")) {
			query = "select EMPLOYEENAME as EMPLOYEENAME from employee_info where  CPFACNO='"
					+ cpfaccno + "' and EMPLOYEENO='" + empCode + "'";
		} else {
			query = "select EMPLOYEENAME as EMPLOYEENAME from employee_info where EMPLOYEENO='"
					+ empCode + "'";
		}
		log.info("query is " + query);
		try {

			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				if (rs.getString("EMPLOYEENAME") != null) {
					foundEmpFlag = rs.getString("EMPLOYEENAME");
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
		return foundEmpFlag.trim();
	}

	public void readXLSPersonalForm3updateSeparation(String xlsData) {
		log
				.info("PensionDAO :readXLSPersonalForm3updateSeparation() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		for (int i = 1; i < xlsDataList.size(); i++) {
			bean = new EmpMasterBean();
			tempData = xlsDataList.get(i).toString();
			tempInfo = tempData.split("@");
			try {
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(),
							"'".toCharArray(), "").toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}

				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setDesegnation(tempInfo[3].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setFhName(tempInfo[4].trim());
				} else {
					bean.setFhName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					if (tempInfo[5].trim().indexOf("'") != -1) {
						int index = tempInfo[5].trim().indexOf("'");
						tempInfo[5] = tempInfo[5].trim().substring(index + 1,
								tempInfo[5].length());
					}
					bean.setDateofBirth(tempInfo[5].trim());

				} else {
					bean.setDateofBirth("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].trim().indexOf("'") != -1) {
						int index = tempInfo[6].trim().indexOf("'");
						tempInfo[6] = tempInfo[6].trim().substring(index + 1,
								tempInfo[6].length());
					}
					bean.setDateofJoining(tempInfo[6].toString().trim());

				} else {
					bean.setDateofJoining("");
				}

				log.info("DateofBirth" + bean.getDateofBirth()
						+ "DateofJoining" + bean.getDateofJoining());

				if (!tempInfo[7].equals("XXX")) {
					bean.setSex(tempInfo[7].trim());
				} else {
					bean.setSex("");
				}
				/*
				 * if (!tempInfo[8].equals("XXX")) {
				 * bean.setSeperationReason(tempInfo[8].trim()); } else {
				 * bean.setSeperationReason(""); } if
				 * (!tempInfo[9].equals("XXX")) {
				 * bean.setOtherReason(tempInfo[9].trim()); } else {
				 * bean.setOtherReason(""); } if (!tempInfo[10].equals("XXX")) {
				 * bean.setDateofSeperationDate(tempInfo[10].trim()); } else {
				 * bean.setDateofSeperationDate(""); }
				 */

				PDAO.updatePersional(bean, "South Region");

			} catch (Exception e) {
				log.printStackTrace(e);
				try {
					fw.write(tempData + "***");
					fw.flush();

				} catch (FileNotFoundException fe) {
					log.printStackTrace(fe);
					// System.err.println("FileStreamsTest: " + fe);
				} catch (IOException io) {
					// System.err.println("FileStreamsTest: " + io);
					log.printStackTrace(io);
				}
			}
		}
		log
				.info("PensionDAO :readXLSPersonalForm3updateSeparation() leaving method");
	}

	public void updateEmployeePersonalInfo(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :updateEmployeePersonalinfo() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int count = 0, totalCount = 0;
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpName(tempInfo[1].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setRegion(tempInfo[3].trim());
				} else {
					bean.setRegion("");
				}
				EmpMasterBean editBean = new EmpMasterBean();
				editBean = PDAO.editPensionMaster1(bean.getCpfAcNo(), bean
						.getEmpName(), true, bean.getEmpNumber(), bean
						.getRegion());
				PDAO.updatePensionMaster1(editBean, "true");
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :updateEmployeePersonalinfo leaving method");

	}

	public void readXLSPersonalData(String xlsData, String userName,
			String ipAddress) throws InvalidDataException {
		log.info("ImportDao :personalCount() entering method");
		ArrayList xlsDataList = new ArrayList();
		ArrayList pensionList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String delimiter = "", xlsEmpName = "", xlsDesignation = "";
		int count = 0, personalCount = 0;
		log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmpNumber(tempInfo[0]);
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1]);
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}

				if (!tempInfo[3].equals("XXX")) {
					bean.setWetherOption(tempInfo[3]);
				} else {
					bean.setWetherOption("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setForm2Nomination(tempInfo[4]);
				} else {
					bean.setForm2Nomination("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setStation(tempInfo[5]);
				} else {
					bean.setStation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setRegion(tempInfo[6]);
				} else {
					bean.setRegion("");
				}
				String remarks = " Pension is updated from Final AAI Sheet";
				String updatePersonalsql = "", updatePersonalInfosql = "";
				if (!bean.getStation().equals("")
						|| !bean.getEmpNumber().equals("")) {
					updatePersonalsql = "update employee_info set WETHEROPTION='"
							+ bean.getWetherOption()
							+ "',WHETHER_FORM1_NOM_RECEIVED='"
							+ bean.getForm2Nomination()
							+ "',"
							+ "username='"
							+ userName
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "',REMARKS=REMARKS||'"
							+ remarks
							+ "'"
							+ " where cpfacno='"
							+ bean.getCpfAcNo().trim()
							+ "' and  employeeno='"
							+ bean.getEmpNumber().trim()
							+ "' and airportcode='"
							+ bean.getStation().trim()
							+ "' and  region='" + bean.getRegion().trim() + "'";
					updatePersonalInfosql = "update employee_personal_info set WETHEROPTION='"
							+ bean.getWetherOption()
							+ "',WHETHER_FORM1_NOM_RECEIVED='"
							+ bean.getForm2Nomination()
							+ "',"
							+ "username='"
							+ userName
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "',REMARKS=REMARKS||'"
							+ remarks
							+ "',IPADDRESS='"
							+ ipAddress
							+ "' "
							+ " where cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and  employeeno='"
							+ bean.getEmpNumber()
							+ "' and airportcode='"
							+ bean.getStation()
							+ "' and  region='" + bean.getRegion() + "'";
				} else {
					updatePersonalsql = "update employee_info set WETHEROPTION='"
							+ bean.getWetherOption()
							+ "',WHETHER_FORM1_NOM_RECEIVED='"
							+ bean.getForm2Nomination()
							+ "',"
							+ "username='"
							+ userName
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "',REMARKS=REMARKS||'"
							+ remarks
							+ "'"
							+ " where cpfacno='"
							+ bean.getCpfAcNo().trim()
							+ "' and  region='" + bean.getRegion().trim() + "'";
					updatePersonalInfosql = "update employee_personal_info set WETHEROPTION='"
							+ bean.getWetherOption()
							+ "',WHETHER_FORM1_NOM_RECEIVED='"
							+ bean.getForm2Nomination()
							+ "',"
							+ "username='"
							+ userName
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "',REMARKS=REMARKS||'"
							+ remarks
							+ "',IPADDRESS='"
							+ ipAddress
							+ "' "
							+ " where cpfacno='"
							+ bean.getCpfAcNo()
							+ "'  and  region='" + bean.getRegion() + "'";
				}

				log.info("update sql " + updatePersonalsql);
				count = st.executeUpdate(updatePersonalsql);
				personalCount = st.executeUpdate(updatePersonalInfosql);
				count = count + 1;
				personalCount = personalCount + 1;
				log.info("record update employee_info" + count
						+ "employee_personal_Info" + personalCount);

			}
		} catch (Exception e) {
			log.printStackTrace(e);

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		log.info("ImportDao :readXLSChqIadData leaving method");
	}

	public void advanceTable(String xlsData) throws Exception {

		log.info("ImportDao :advanceTable() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				String amount = "",aaiPartAmount="",advTransDate = "",tempName = "",advPurpose="";;
				if (!tempInfo[3].equals("XXX")) {
					amount = tempInfo[3].trim();
				} else {
					amount = "0.00";
				}
				String partAmount = "";
				if (!tempInfo[4].equals("XXX")) {
					partAmount = tempInfo[4].trim();
				} else {
					partAmount = "0.00";
				}
				if (!tempInfo[5].equals("XXX")) {
					aaiPartAmount = tempInfo[5].trim();
				} else {
					aaiPartAmount = "0.00";
				}

				String advance = String.valueOf((Float.parseFloat(amount)
						+ Float.parseFloat(partAmount) + Float
						.parseFloat(aaiPartAmount)));
				if (!tempInfo[6].equals("XXX")) {
					advTransDate = tempInfo[6].trim();
				} else {
					advTransDate = "0.00";
				}
				if (!tempInfo[7].equals("XXX")) {
					advPurpose = tempInfo[7].trim();
				} else {
					advPurpose = "";
				}
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}

				String updateTransaction = "";
				String insertAdvanceTabledata = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					insertAdvanceTabledata = "insert into  employee_pension_advances (cpfaccno,employeeno,employeename,amount,Partamt,aaipartamt,advPurpose,AdvTransDate)values('"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getEmpNumber()
							+ "','"
							+ bean.getEmpName()
							+ "','"
							+ Float.parseFloat(amount)
							+ "','"
							+ Float.parseFloat(partAmount)
							+ "','"
							+ Float.parseFloat(aaiPartAmount)
							+ "','"
							+ advPurpose + "','" + advTransDate + "')";
					String transMonthYear = commonUtil.converDBToAppFormat(
							advTransDate, "dd-MMM-yyyy", "-MMM-yyyy");
					String remarks = "CHQNAD ADVANCES UPDATED";
					updateTransaction = "UPDATE EMPLOYEE_PENSION_VALIDATE SET ADVANCE='"
							+ Float.parseFloat(advance)
							+ "' ,EMPADVRECPRINCIPAL=TO_NUMBER(CPFADVANCE)-nvl(TO_NUMBER('"
							+ advance
							+ "'),0),REMARKS=REMARKS||'"
							+ remarks
							+ "' WHERE to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo() + "' and region='CHQNAD' ";

				}

				log.info(insertAdvanceTabledata);
				log.info(updateTransaction);
				st.execute(insertAdvanceTabledata);
				st.execute(updateTransaction);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :advanceTable leaving method");

	}

	public void IgiCargoAdvanceTable(String xlsData, String region)
			throws InvalidDataException {

		log.info("ImportDao :IgiCargoAdvanceTable() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", advTransDate = "", amount = "", advPurpose = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				/*
				 * if (!tempInfo[1].equals("XXX")) {
				 * bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				 * } else { bean.setPfid(""); }
				 */
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[3].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}

				if (!tempInfo[4].equals("XXX")) {

					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[5].equals("XXX")) {
					// advTransDate =
					// commonUtil.converDBToAppFormat(tempInfo[5].
					// trim(),"dd.MM.yyyy", "dd-MMM-yyyy");
					advTransDate = tempInfo[5].trim();
					log.info("advTransDate " + advTransDate);

				} else {
					advTransDate = "";
				}

				if (!tempInfo[6].equals("XXX")) {
					advPurpose = tempInfo[6].trim();
				} else {
					advPurpose = "";
				}

				if (!tempInfo[7].equals("XXX")) {
					amount = tempInfo[7].trim();
				} else {
					amount = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setStation(tempInfo[8].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[9].trim()));
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setRegion(tempInfo[10].trim());
				}
				String updateTransaction = "";
				String insertAdvanceTabledata = "";
				log.info(bean.getCpfAcNo());
				if (bean.getCpfAcNo() != "") {
					/*
					 * insertAdvanceTabledata =
					 * "insert into  employee_pension_advances (cpfaccno,employeeno,employeename,amount,advPurpose,AdvTransDate,airportcode,region,PARTAMT)values('"
					 * + bean.getCpfAcNo() + "','" + bean.getEmpNumber() + "','"
					 * + bean.getEmpName() + "','" + Float.parseFloat(amount) +
					 * "','" + advPurpose + "','" + advTransDate +
					 * "','IGICargo IAD','CHQIAD','0.00')";
					 */
					insertAdvanceTabledata = "insert into  employee_pension_advances (cpfaccno,employeeno,employeename,amount,advPurpose,AdvTransDate,airportcode,region,PARTAMT,PENSIONNO)values('"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getEmpNumber()
							+ "','"
							+ bean.getEmpName()
							+ "','"
							+ Float.parseFloat(amount)
							+ "','"
							+ advPurpose
							+ "','"
							+ advTransDate
							+ "','"
							+ bean.getStation().trim()
							+ "','"
							+ bean.getRegion()
							+ "','0.00','"
							+ bean.getPfid()
							+ "')";
					log.info(insertAdvanceTabledata);
					st.execute(insertAdvanceTabledata);
				}

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :IgiCargoAdvanceTable leaving method");

	}

	public void IgiCargoPFwTable(String xlsData, String region)
			throws InvalidDataException {

		log.info("ImportDao :IgiCargoPFwTable() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[3].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					// advTransDate =
					// commonUtil.converDBToAppFormat(tempInfo[5].
					// trim(),"dd.MM.yyyy", "dd-MMM-yyyy");
					advTransDate = tempInfo[5].trim();
					log.info("advTransDate " + advTransDate);

				} else {
					advTransDate = "";
				}

				if (!tempInfo[6].equals("XXX")) {
					advPurpose = tempInfo[6].trim();
				} else {
					advPurpose = "";
				}

				if (!tempInfo[7].equals("XXX")) {
					empShare = tempInfo[7].trim();
				} else {
					empShare = "0.00";
				}
				if (!tempInfo[8].equals("XXX")) {
					aaiShare = tempInfo[8].trim();
				} else {
					aaiShare = "0.00";
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setStation(tempInfo[9].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean
							.setPfid(commonUtil.getSearchPFID1(tempInfo[10]
									.trim()));
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setRegion(tempInfo[11].trim());
				}
				String updateTransaction = "";
				String insertPfwTabledata = "";
				log.info(bean.getCpfAcNo());
				if (bean.getCpfAcNo() != "") {
					/*
					 * insertAdvanceTabledata =
					 * "insert into  employee_pension_loans (cpfaccno,employeeno,employeename,SUB_AMT,CONT_AMT,LOANPURPOSE,LOANDATE,airportcode,region,LOANTYPE)values('"
					 * + bean.getCpfAcNo() + "','" + bean.getEmpNumber() + "','"
					 * + bean.getEmpName() + "','" + Float.parseFloat(empShare)
					 * + "','" + Float.parseFloat(aaiShare) + "','" + advPurpose
					 * + "','" + advTransDate +
					 * "','IGICargo IAD','CHQIAD','NRF')";
					 */
					insertPfwTabledata = "insert into  employee_pension_loans(cpfaccno,employeeno,employeename,SUB_AMT,CONT_AMT,LOANPURPOSE,LOANDATE,airportcode,region,LOANTYPE,PENSIONNO)values('"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getEmpNumber()
							+ "','"
							+ bean.getEmpName()
							+ "','"
							+ Float.parseFloat(empShare)
							+ "','"
							+ Float.parseFloat(aaiShare)
							+ "','"
							+ advPurpose
							+ "','"
							+ advTransDate
							+ "','"
							+ bean.getStation()
							+ "','"
							+ bean.getRegion()
							+ "','NRF','"
							+ bean.getPfid() + "')";
					log.info(insertPfwTabledata);
					st.execute(insertPfwTabledata);
				}

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :IgiCargoPFwTable leaving method");

	}

	public void arrearTable(String xlsData) throws InvalidDataException {

		log.info("ImportDao :arrearTable() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo;
				if (!tempInfo[0].equals("XXX")) {
					cpfaccno = tempInfo[0].trim();
				} else {
					cpfaccno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					employeeNo = tempInfo[1].trim();
				} else {
					employeeNo = "";
				}
				if (!tempInfo[2].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[2].trim().toCharArray(), delimiters, "")
							.toString();
					employeeName = xlsEmpName.trim();
				} else {
					employeeName = "";
				}
				String arrearAmount = "";
				if (!tempInfo[3].equals("XXX")) {
					arrearAmount = tempInfo[3].trim();
				} else {
					arrearAmount = "0.00";
				}
				String aaiContAmount = "";
				if (!tempInfo[4].equals("XXX")) {
					aaiContAmount = tempInfo[4].trim();
				} else {
					aaiContAmount = "0.00";
				}

				String arrearDate = "";
				if (!tempInfo[5].equals("XXX")) {
					arrearDate = tempInfo[5].trim();
				} else {
					arrearDate = "";
				}

				String remarks = "";
				if (!tempInfo[6].equals("XXX")) {
					remarks = tempInfo[6].trim();
				} else {
					remarks = "";
				}

				String insertArrearTabledata = "";
				String transMonthYear = commonUtil.converDBToAppFormat(
						arrearDate, "dd-MMM-yyyy", "-MMM-yyyy");

				insertArrearTabledata = "insert into  employee_pension_arrear(cpfaccno,employeeno,employeename,arreardate,arrearAmt,aaiContrAmt,airportcode,region,remarks)values('"
						+ cpfaccno
						+ "','"
						+ employeeNo
						+ "','"
						+ employeeName
						+ "','"
						+ arrearDate
						+ "','"
						+ Float.parseFloat(arrearAmount)
						+ "','"
						+ Float.parseFloat(aaiContAmount)
						+ "','CHQNAD','CHQNAD','" + remarks + "')";

				remarks = "updating emoluments with AAIContrAmt";
				String updateTransaction = "UPDATE EMPLOYEE_PENSION_VALIDATE set EMOLUMENTS=to_char(((TO_NUMBER(nvl(CPFARREAR,0))+TO_NUMBER(nvl(cpf,0))+"
						+ aaiContAmount
						+ ")*100/12),'9999999.99'),EMPPFSTATUARY=to_char(TO_NUMBER(nvl(CPFARREAR,0))+TO_NUMBER(nvl(cpf,0))+"
						+ aaiContAmount
						+ ",'999999.99') WHERE to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear
						+ "' and cpfaccno='"
						+ cpfaccno
						+ "' and region='CHQNAD'";
				// String updateTransaction = "UPDATE EMPLOYEE_PENSION_VALIDATE
				// set
				//EMOLUMENTS=(TO_NUMBER(nvl(CPFARREAR,0))+nvl(TO_NUMBER(nvl(cpf,
				// 0
				// )))+'"+aaiContAmount+"')*100/12),EMPPFSTATUARY=TO_NUMBER(nvl(
				// CPFADVANCE
				// ,0))+nvl(TO_NUMBER(nvl(advance,0)))+'"+aaiContAmount+"'),
				// REMARKS=REMARKS||'"
				// + remarks+ "' WHERE to_char(monthyear,'dd-Mon-yyyy') like
				// '%"+arrearDate+ "' and cpfaccno='"+cpfaccno+"' and
				// region='CHQNAD' ";

				String empVpfCalculation = "UPDATE EMPLOYEE_PENSION_VALIDATE  set empvpf=to_char((TO_NUMBER(nvl(empvpf,0))+TO_NUMBER((nvl("
						+ arrearAmount
						+ ",0))-("
						+ aaiContAmount
						+ "))),'999999.99') WHERE to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear
						+ "' and cpfaccno='"
						+ cpfaccno
						+ "' and region='CHQNAD'";

				log.info(insertArrearTabledata);
				log.info(updateTransaction);
				log.info(empVpfCalculation);
				st.execute(insertArrearTabledata);
				// log.info(updateTransaction);
				st.execute(updateTransaction);
				st.execute(empVpfCalculation);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :arrearTable leaving method");

	}

	public void loansTable(String xlsData) throws InvalidDataException {

		log.info("ImportDao :loanTable() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeno = "";
				if (!tempInfo[0].equals("XXX")) {
					employeeno = tempInfo[0].trim();
				} else {
					employeeno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					cpfaccno = tempInfo[1].trim();
				} else {
					cpfaccno = "";
				}
				String loanPurpose = "";
				if (!tempInfo[1].equals("XXX")) {
					loanPurpose = tempInfo[1].trim();
				} else {
					loanPurpose = "";
				}
				String loanDate = "";
				if (!tempInfo[2].equals("XXX")) {
					loanDate = tempInfo[2].trim();
				} else {
					loanDate = "";
				}
				String subAmount = "";
				if (!tempInfo[3].equals("XXX")) {
					subAmount = tempInfo[3].trim();
				} else {
					subAmount = "0.00";
				}
				String cntAmount = "";
				if (!tempInfo[4].equals("XXX")) {
					cntAmount = tempInfo[4].trim();
				} else {
					cntAmount = "0.00";
				}
				String loanType = "";
				if (!tempInfo[5].equals("XXX")) {
					loanType = tempInfo[5].trim();
				} else {
					loanType = "";
				}
				String insertLoanTabledata = "";
				insertLoanTabledata = "insert into  employee_pension_loans(cpfaccno,loandate,loanpurpose,loantype,sub_amt,CONT_AMT,airportcode,region)values('"
						+ cpfaccno
						+ "','"
						+ loanDate
						+ "','"
						+ loanPurpose
						+ "','"
						+ loanType
						+ "','"
						+ Float.parseFloat(subAmount)
						+ "','"
						+ cntAmount
						+ "','OFFICE COMPLEX','CHQIAD')";

				log.info(insertLoanTabledata);
				st.execute(insertLoanTabledata);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :loanTable leaving method");

	}

	public void loansPrincipleUpdate(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :loansPrincipleUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "";
				if (!tempInfo[0].equals("XXX")) {
					cpfaccno = tempInfo[0].trim();
				} else {
					cpfaccno = "";
				}

				String loanType = "";
				if (!tempInfo[5].equals("XXX")) {
					loanType = tempInfo[5].trim();
				} else {
					loanType = "";
				}
				String loanDate = "";
				if (!tempInfo[2].equals("XXX")) {
					loanDate = tempInfo[2].trim();
				} else {
					loanDate = "";
				}

				String transMonthYear = commonUtil.converDBToAppFormat(
						loanDate, "dd-MMM-yyyy", "-MMM-yyyy");
				String selectrefadv = " select  nvl(refadv,0) as refadv  from employee_pension_validate where region='CHQIAD' and airportcode='OFFICE COMPLEX' and cpfaccno='"
						+ cpfaccno
						+ "'  and to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "'";
				log.info("selectrefadv query " + selectrefadv);
				ResultSet rs1 = st.executeQuery(selectrefadv);
				String refAdv = "";
				if (rs1.next()) {
					if (rs1.getString("refAdv") != null) {
						refAdv = rs1.getString(1).toString();
					} else
						refAdv = "0.00";
					log.info("refAdv is " + refAdv);
				} else {
					refAdv = "0.00";
				}
				String empPrinciple = "";
				String loanamt = "select nvl(SUB_AMT,0)as SUB_AMT ,nvl(CONT_AMT,0) as CONT_AMT,LOANTYPE,loandate from employee_pension_loans where region='CHQIAD' and airportcode='OFFICE COMPLEX' and cpfaccno='"
						+ cpfaccno
						+ "' and  to_char(LOANDATE,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "'";
				log.info("loanamt query " + loanamt);
				ResultSet rs = st.executeQuery(loanamt);
				if (rs.next()) {
					String loantype = rs.getString("loantype");
					if (loantype.equals("RF")) {
						String subAmount = rs.getString("SUB_AMT").toString();
						empPrinciple = String.valueOf(Float
								.parseFloat(subAmount)
								- Float.parseFloat(refAdv));
						log.info("empPrinciple in if " + empPrinciple);
					} else if (loantype.equals("NRF")) {
						String subAmount = rs.getString("SUB_AMT").toString();
						String contAmount = rs.getString("CONT_AMT").toString();
						log.info("subAmount " + subAmount + "contAmount "
								+ contAmount + "refadv" + refAdv);
						empPrinciple = String.valueOf(Float
								.parseFloat(subAmount)
								+ Float.parseFloat(contAmount)
								- Float.parseFloat(refAdv));
						log.info("empPrinciple in else " + empPrinciple);
					}

				}
				String updatePrinciple = "update employee_pension_validate set EMPADVRECPRINCIPAL='"
						+ empPrinciple
						+ "' where region='CHQIAD' and airportcode='OFFICE COMPLEX' and cpfaccno='"
						+ cpfaccno
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "'";

				log.info("updatePrinciple " + updatePrinciple);
				st.execute(updatePrinciple);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :loanTable leaving method");

	}

	public void eastRegionEmployeeNamesUpdate(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :eastRegionEmployeeNamesUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setRegion(tempInfo[3].trim());
				} else {
					bean.setRegion("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				String updateTransaction = "";
				String updatePersonalinfo = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and master_empname is null ";

				} else if (bean.getCpfAcNo() != "") {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfacno='"
							+ bean.getCpfAcNo().trim()
							+ "' and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and master_empname is null  ";
				} else {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "'and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "' and master_empname is null";
				}
				log.info(updatePersonalinfo);
				log.info(updateTransaction);
				st.execute(updatePersonalinfo);
				st.execute(updateTransaction);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :eastRegionEmployeeNamesUpdate leaving method");

	}

	public void northEastRegionEmployeeNamesUpdate(String xlsData)
			throws InvalidDataException {

		log
				.info("ImportDao :northEastRegionEmployeeNamesUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setRegion(tempInfo[3].trim());
				} else {
					bean.setRegion("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				String updateTransaction = "";
				String updatePersonalinfo = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and master_empname is null ";

				} else if (bean.getCpfAcNo() != "") {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfacno='"
							+ bean.getCpfAcNo().trim()
							+ "' and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and master_empname is null  ";
				} else {
					updatePersonalinfo = "update employee_info set employeename='"
							+ bean.getEmpName().toUpperCase()
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='"
							+ bean.getRegion()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "'and employeename is null";
					updateTransaction = "update employee_pension_validate set master_empname='"
							+ bean.getEmpName().toUpperCase().trim()
							+ "' where region='"
							+ bean.getRegion()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "' and master_empname is null";
				}
				log.info(updatePersonalinfo);
				log.info(updateTransaction);
				st.execute(updatePersonalinfo);
				st.execute(updateTransaction);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log
				.info("ImportDao :northEastRegionEmployeeNamesUpdate leaving method");

	}

	public void SouthGenderUpdates(String xlsData) throws InvalidDataException {

		log.info("ImportDao :SouthGenderUpdates() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("aims.resource.DbProperties");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[1].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setSex(tempInfo[2].trim());
				} else {
					bean.setSex("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setRegion(tempInfo[3].trim());
				} else {
					bean.setRegion("");
				}
				String tempName = "";
				for (int j = 0; j < quats.length; j++) {
					if (bean.getEmpName().toLowerCase().indexOf(
							quats[j].toLowerCase()) != -1) {
						tempName = bean.getEmpName().toLowerCase().replaceAll(
								quats[j].toLowerCase(), "").trim();
						bean.setEmpName(tempName);
					}
				}
				log.info("Name===" + bean.getEmpName());
				String updateTransaction = "";
				String updatePersonalinfo = "";
				if (bean.getCpfAcNo() != "" && bean.getEmpNumber() != "") {
					updatePersonalinfo = "update employee_info set sex='"
							+ bean.getSex() + "' LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='" + bean.getRegion()
							+ "' and cpfacno='" + bean.getCpfAcNo()
							+ "' and empl and sex is null";

				} else if (bean.getCpfAcNo() != "") {
					updatePersonalinfo = "update employee_info set  sex='"
							+ bean.getSex() + "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='" + bean.getRegion()
							+ "' and cpfacno='" + bean.getCpfAcNo().trim()
							+ "' and  sex is null";

				} else {
					updatePersonalinfo = "update employee_info set  sex='"
							+ bean.getSex() + "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where region='" + bean.getRegion()
							+ "' and employeeno='" + bean.getEmpNumber()
							+ "'and  sex is null";

				}
				log.info(updatePersonalinfo);
				st.execute(updatePersonalinfo);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :eastRegionEmployeeNamesUpdate leaving method");

	}

	public void chqnadVPFUpdate(String xlsData) throws Exception {

		log.info("ImportDao :chqnadVPFUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[9].trim());
				} else {
					bean.setEmployeeVPF("");
				}
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}

				String remarks = "CHQNAD empvpf UPDATED";
				String transMonthYear = commonUtil.converDBToAppFormat(
						monthYear, "dd-MMM-yyyy", "-MMM-yyyy");
				log.info("transMonthYear " + transMonthYear);
				String updateEmpVpf = "update employee_pension_validate set empvpf='"
						+ bean.getEmployeeVPF()
						+ "',REMARKS=REMARKS||'"
						+ remarks
						+ "' where region='CHQNAD' AND CPFACCNO='"
						+ bean.getCpfAcNo() + "'";

				log.info(updateEmpVpf);
				st.execute(updateEmpVpf);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :chqnadVPFUpdate leaving method");

	}

	public void eastRegionEmoluemntUpdates(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :eastRegionEmoluemntUpdates() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", empPfstatuary = "", DAILYALLOWANCE = "";

				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					cpfaccno = tempInfo[1].trim();
				} else {
					cpfaccno = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					DAILYALLOWANCE = tempInfo[5].trim();
				} else {
					DAILYALLOWANCE = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					empPfstatuary = tempInfo[6].trim();
				} else {
					empPfstatuary = "0.00";
				}
				String airportCode = "";
				if (!tempInfo[8].equals("XXX")) {
					airportCode = tempInfo[8].trim();
				} else {
					airportCode = "";
				}

				String insertArrearCount = "";
				String checkBasic = "select count(*) as count from employee_pension_validate where (basic!='0' and basic!='0.00') and region='East Region' and cpfaccno='"
						+ cpfaccno + "' and monthyear='" + monthYear + "'";
				ResultSet rs = st.executeQuery(checkBasic);
				int count = 0, recordCount = 0;
				if (rs.next()) {
					recordCount = Integer.parseInt(rs.getString(1));
				}
				log.info("recordCount " + recordCount);
				if (recordCount >= 1) {
					insertArrearCount = "update employee_pension_validate set DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
							+ DAILYALLOWANCE
							+ "')) , emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ DAILYALLOWANCE
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ empPfstatuary
							+ "')) where region='East Region' and cpfaccno='"
							+ cpfaccno + "' and monthyear='" + monthYear + "'";
					count = st.executeUpdate(insertArrearCount);
				}
				String deleteRecord = "delete from employee_pension_validate where  region='East Region' and cpfaccno='"
						+ cpfaccno
						+ "' and monthyear='"
						+ monthYear
						+ "' and airportcode='" + airportCode + "'";
				log.info("count is" + count);
				if (count >= 2) {
					log.info("inside delete");
					st.executeUpdate(deleteRecord);
				}
				log.info("Update emoluemnts query " + insertArrearCount);
				log.info("deleteRecord  " + deleteRecord);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :eastRegionEmoluemntUpdates leaving method");

	}

	public void igiCargoArrearEmoluemntUpdates(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :igiCargoArrearEmoluemntUpdates() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[5].equals("XXX")) {
					cpfarrear = tempInfo[5].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					emoluments = tempInfo[6].trim();
				} else {
					emoluments = "0.00";
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"CHQIAD");
				String updateArrears = "";
				int count = 0;
				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,AIRPORTCODE,REGION) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ bean.getEmpNumber()
						+ "','"
						+ bean.getEmpName()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','IGICargo IAD','CHQIAD')";
				st.executeUpdate(insertArrear);
				if (!foundSalaryDate.equals("")) {
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear
							+ "'))  where   region='CHQIAD' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";
					count = st.executeUpdate(updateArrears);
				}
				log.info("insertArrear " + insertArrear);
				log.info("updateArrears " + updateArrears);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :igiCargoArrearEmoluemntUpdates leaving method");

	}

	public void westRegionEmoluemntUpdates(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :westRegionEmoluemntUpdates() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", empPfstatuary = "", DAILYALLOWANCE = "";
				if (!tempInfo[1].equals("XXX")) {
					cpfaccno = tempInfo[1].trim();
				} else {
					cpfaccno = "";
				}
				String monthYear = "";
				if (!tempInfo[8].equals("XXX")) {
					monthYear = tempInfo[8].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[10].equals("XXX")) {
					DAILYALLOWANCE = tempInfo[10].trim();
				} else {
					DAILYALLOWANCE = "0.00";
				}
				if (!tempInfo[11].equals("XXX")) {
					empPfstatuary = tempInfo[11].trim();
				} else {
					empPfstatuary = "0.00";
				}
				String fpf = "";
				if (!tempInfo[12].equals("XXX")) {
					fpf = tempInfo[12].trim();
				} else {
					fpf = "0.00";
				}
				String vpf = "";
				if (!tempInfo[13].equals("XXX")) {
					vpf = tempInfo[13].trim();
				} else {
					vpf = "0.00";
				}
				String airportCode = "";
				if (!tempInfo[6].equals("XXX")) {
					airportCode = tempInfo[6].trim();
				} else {
					airportCode = "";
				}

				String insertArrearCount = "";
				// upto 2002(becoz transaction is escalated by one month
				// earlier.
				String checkBasic = "select count(*) as count from employee_pension_validate where (basic!='0' and basic!='0.00' and basic is not null) and region='West Region' and cpfaccno='"
						+ cpfaccno
						+ "' and  monthyear=to_char(add_months('"
						+ monthYear + "',1),'dd-Mon-yyyy')";
				// String checkBasic="select count(*) as count from
				// employee_pension_validate where (basic!='0' and
				// basic!='0.00') and region='West Region' and
				// cpfaccno='"+cpfaccno+"' and monthyear='"+monthYear+"'";
				log.info("checkBasic " + checkBasic);
				ResultSet rs = st.executeQuery(checkBasic);
				int count = 0, recordCount = 0;
				if (rs.next()) {
					recordCount = Integer.parseInt(rs.getString(1));
				}
				log.info("recordCount " + recordCount);
				if (recordCount >= 1) {
					insertArrearCount = "update employee_pension_validate set DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+'"
							+ DAILYALLOWANCE
							+ "')) , emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ DAILYALLOWANCE
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ empPfstatuary
							+ "'+'"
							+ fpf
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ vpf
							+ "'))  where region='West Region' and cpfaccno='"
							+ cpfaccno
							+ "' and  monthyear=to_char(add_months('"
							+ monthYear + "',1),'dd-Mon-yyyy')";
					// insertArrearCount ="update employee_pension_validate set
					// DAILYALLOWANCE=to_char(TO_NUMBER(nvl(DAILYALLOWANCE,0)+
					// '"+DAILYALLOWANCE+"'))
					// ,
					// emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+
					// '"+DAILYALLOWANCE+"'))
					// ,
					// EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+
					// '"+empPfstatuary+"'+'"+fpf+"')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"+vpf+"'))
					// where region='West Region' and cpfaccno='"+cpfaccno+"'
					// and monthyear='"+monthYear+"'";
					count = st.executeUpdate(insertArrearCount);
				}

				log.info("count is" + count);
				if (count >= 2) {
					log.info("inside delete");
					// st.executeUpdate(deleteRecord);
				}
				log.info("Update emoluments query " + insertArrearCount);
				// log.info("deleteRecord "+deleteRecord);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :westRegionEmoluemntUpdates leaving method");

	}

	public void importArrCountCHQNAD(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importArrCountCHQNAD() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo;
				if (!tempInfo[1].equals("XXX")) {
					cpfaccno = tempInfo[1].trim();
				} else {
					cpfaccno = "";
				}
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				String arrCount = "";
				if (!tempInfo[16].equals("XXX")) {
					arrCount = tempInfo[16].trim();
				} else {
					arrCount = "0.00";
				}

				String insertArrearCount = "";
				insertArrearCount = "update employee_pension_validate set ARRCOUNT='"
						+ arrCount
						+ "' where region='CHQNAD' and cpfaccno='"
						+ cpfaccno + "' and monthyear='" + monthYear + "'";
				st.executeUpdate(insertArrearCount);
				log.info("Update ArrearCount " + insertArrearCount);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importArrCountCHQNAD leaving method");

	}

	public void importEastArr9697(String xlsData) throws Exception {
		log.info("ImportDao :importEastArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("aims.resource.DbProperties");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[5].equals("XXX")) {
					cpfarrear = tempInfo[5].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					emoluments = tempInfo[6].trim();
				} else {
					emoluments = "0.00";
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"CHQIAD");
				String updateArrears = "";
				int count = 0;
				String noofMonthsQuery = "select round(months_between('01-Mar-1996',NVL(DATEOFJOINING,'01-Apr-1995'))) as months,to_date(to_char(NVL(DATEOFJOINING,'01-Apr-1995'),'dd-Mon-yyyy')) as doj from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' AND REGION='CHQIAD'";
				ResultSet rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				Date dateofjoing = null;
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
					dateofjoing = rs.getDate("doj");

				}
				String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and region='CHQIAD' AND airportcode='KOLKATA'";
				ResultSet rs1 = st.executeQuery(emolumentsCount);
				int emolumentsCountno = 0;

				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
					// for East Region 9
					// emolumentsCountno += 12;
					emolumentsCountno += 4;
				}
				String emoluments1 = "", cpfarrear1 = "";
				log.info("dateofjoining " + dateofjoing);
				if (!dateofjoing.equals(null)
						&& dateofjoing.before(new Date("01-Apr-95"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,REGION,AIRPORTCODE) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ bean.getEmpNumber()
						+ "','"
						+ bean.getEmpName()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','CHQIAD','KOLKATA')";
				st.addBatch(insertArrear);
				if (!foundSalaryDate.equals("")) {
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments1
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear1
							+ "'))  where   region='CHQIAD' AND AIRPORTCODE='KOLKATA' and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";
					st.addBatch(updateArrears);

				}
				int count1[] = st.executeBatch();
				log.info("count is" + count1.length);
				log.info("insertArrear " + insertArrear);
				log.info("updateArrears " + updateArrears);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importEastArr9697 leaving method");

	}

	public void importNorthArr9697(String xlsData) throws InvalidDataException {
		log.info("ImportDao :importNorthArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		ResultSet rs=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("aims.resource.DbProperties");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "",pensionNo="";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					emoluments = tempInfo[2].trim();
				} else {
					emoluments = "0.00";
				}
				if (!tempInfo[3].equals("XXX")) {
					cpfarrear = tempInfo[3].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setRegion(tempInfo[4].trim());
				} else {
					bean.setRegion("");
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						bean.getRegion());
				String updateArrears = "";
				int count = 0;
				String dateofjoing = "";
				String dojquery="select (CASE WHEN (DATEOFJOINING between '01-Apr-1945' and '01-Jan-1989') THEN '01-Jan-1989'  ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy')  END) as doj,empserialnumber   from employee_info WHERE CPFACNO='"+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "'"; 
				 rs = st.executeQuery(dojquery);
				 if(rs.next()){
				 if(rs.getString("doj")!=null){
					 dateofjoing= rs.getString("doj").toString();
				 } else{
					 dateofjoing="";
				  }
				 if(rs.getString("empserialnumber")!=null){
					 pensionNo= rs.getString("empserialnumber");
				 }
				 }
			   int months = 0;
				 if(dateofjoing!=""){
				String noofMonthsQuery = "select round(months_between(last_day('01-Aug-1996'),'"+dateofjoing+"')) as months,to_date(to_char(NVL(DATEOFJOINING,'01-Apr-1995'),'dd-Mon-yyyy')) as doj from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "'";
				 log.info("noofMonthsQuery "+noofMonthsQuery);
				rs = st.executeQuery(noofMonthsQuery);
				 			
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
				}
				 }
				String countMonthsfromMaytoArreardate="select round(months_between(last_day('01-Aug-1996'),'01-Dec-1995')) as months from dual";
				rs2 = st.executeQuery(countMonthsfromMaytoArreardate);
				int countMonthsfromMaytoArrearsdate=0;
				if (rs2.next()) {
					countMonthsfromMaytoArrearsdate = Integer.parseInt(rs.getString("months"));
				}
				String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
						+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "'";
				 rs1 = st.executeQuery(emolumentsCount);
				int emolumentsCountno = 0;

				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
					// for East Region 9
					// for CHQIAD KOLKATA 12
					emolumentsCountno += 9;
				}
				String emoluments1 = "0.00", cpfarrear1 = "0.00";
				log.info("dateofjoining " + dateofjoing);
				 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				 Date	transdate = null;
				if (dateofjoing!=""){
				  	transdate = df.parse(dateofjoing);
                 if(transdate.before(new Date("01-Apr-1995"))) {
					df.parse(dateofjoing);
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}	
				}
				log.info("dateofjoing"+dateofjoing);
				log.info("foundSalaryDate"+foundSalaryDate);
				if (!dateofjoing.equals(null)&& dateofjoing !="" && !foundSalaryDate.trim().equals("")) {
				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION,pensionno) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','"+ bean.getRegion() + "','"+pensionNo+"')";
					st.addBatch(insertArrear);
				String transMonthYear = commonUtil.converDBToAppFormat(monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments1
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear1
							+ "')),pensionNo='"+pensionNo+"',remarks='Updated as on 05-Oct-2010'  where REGION='"+ bean.getRegion() + "' and empflag='Y'  and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";

					st.addBatch(updateArrears);
					int count1[] = st.executeBatch();
					log.info("count is" + count1.length);
					conn.commit();
					String updateemoluments_log="update emoluments_log set (newemoluments,newemppfstatuary)=(select emoluments, emppfstatuary from employee_pension_validate   where region = '"+ bean.getRegion()+ "' and   to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "' and  cpfaccno='"+bean.getCpfAcNo()+"' and empflag='Y') where region = '"+ bean.getRegion()+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"+ transMonthYear + "' and  cpfacno='"+bean.getCpfAcNo()+"' ";					
					log.info(updateemoluments_log);
					st.executeUpdate(updateemoluments_log);
					
				}else{
					String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION,arrearflag,pensionno) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','"+ bean.getRegion() + "','N','"+pensionNo+"')";
					st.executeUpdate(insertArrear);
				}
				
			
			//log.info("insertArrear " + insertArrear);
				log.info("updateArrears " + updateArrears);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importNorthArr9697 leaving method");

	}
	public void importIADArr9697(String xlsData)
	throws InvalidDataException {
log.info("ImportDao :importIAD9697 entering method");
	ArrayList xlsDataList = new ArrayList();
	xlsDataList = commonUtil.getTheList(xlsData, "***");
	Connection conn = null;
	Statement st = null;
	Statement st1 = null;
	EmpMasterBean bean = null;
	String tempInfo[] = null;
	String tempData = "";
	FileWriter fw = null;
	char[] delimiters = { '"', ',', '\'', '*', ',' };
	String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
	String uploadFilePath = "";
	String xlsEmpName = "";
	ResultSet rs=null;
	ResultSet rs1=null;
	ResultSet rs2=null;
	log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

	try {
		ResourceBundle bundle = ResourceBundle
				.getBundle("aims.resource.DbProperties");
		uploadFilePath = bundle.getString("upload.folder.path");
		File filePath = new File(uploadFilePath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
		conn = commonDB.getConnection();
		conn.setAutoCommit(false);
		st = conn.createStatement();
		st1 = conn.createStatement();
		for (int i = 1; i < xlsDataList.size(); i++) {
			bean = new EmpMasterBean();
			tempData = xlsDataList.get(i).toString();
			tempInfo = tempData.split("@");
			String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
			String monthYear = "",pensionNo="";
			if (!tempInfo[0].equals("XXX")) {
				monthYear = tempInfo[0].trim();
			} else {
				monthYear = "";
			}
			if (!tempInfo[1].equals("XXX")) {
				bean.setCpfAcNo(tempInfo[1].trim());
			} else {
				bean.setCpfAcNo("");
			}

			if (!tempInfo[2].equals("XXX")) {
				emoluments = tempInfo[2].trim();
			} else {
				emoluments = "0.00";
			}
			if (!tempInfo[3].equals("XXX")) {
				cpfarrear = tempInfo[3].trim();
			} else {
				cpfarrear = "0.00";
			}
			if (!tempInfo[4].equals("XXX")) {
				bean.setRegion(tempInfo[4].trim());
			} else {
				bean.setRegion("");
			}
			if (!tempInfo[5].equals("XXX")) {
				bean.setStation(tempInfo[5].trim());
			} else {
				bean.setStation("");
			}
			if (!tempInfo[6].equals("XXX")) {
				bean.setRemarks(tempInfo[6].trim());
			} else {
				bean.setRemarks("");
			}

			String foundSalaryDate = this.checkFinanceDuplicate3(conn,
					monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
					bean.getRegion(),bean.getStation());
			String updateArrears = "";
			int count = 0;
			String dateofjoing = "";
			String dojquery="select (CASE WHEN (DATEOFJOINING between '01-Apr-1945' and '01-Jan-1992') THEN '01-Jan-1992'  ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy')  END) as doj,empserialnumber   from employee_info WHERE CPFACNO='"+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "' and airportcode='"+ bean.getStation()+"'"; 
			 rs = st.executeQuery(dojquery);
			 if(rs.next()){
			 if(rs.getString("doj")!=null){
				 dateofjoing= rs.getString("doj").toString();
			 } else{
				 dateofjoing="";
			  }
			 if(rs.getString("empserialnumber")!=null){
				 pensionNo= rs.getString("empserialnumber");
			 }
			 }
		   int months = 0;
			 if(dateofjoing!=""){
			String noofMonthsQuery = "select round(months_between('"+monthYear+"','"+dateofjoing+"')) as months,to_date(to_char(NVL(DATEOFJOINING,'01-Apr-1995'),'dd-Mon-yyyy')) as doj from employee_info WHERE CPFACNO='"
					+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "' and airportcode='"+ bean.getStation()+"'";
			 log.info("noofMonthsQuery "+noofMonthsQuery);
			rs = st.executeQuery(noofMonthsQuery);
			 			
			if (rs.next()) {
				months = Integer.parseInt(rs.getString("months"));
			}
			 }
			String countMonthsfromMaytoArreardate="select round(months_between('"+monthYear+"','01-Dec-1995')) as months from dual";
			rs2 = st.executeQuery(countMonthsfromMaytoArreardate);
			int countMonthsfromMaytoArrearsdate=0;
			if (rs2.next()) {
				countMonthsfromMaytoArrearsdate = Integer.parseInt(rs.getString("months"));
			}
			String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
					+ bean.getCpfAcNo() + "' and REGION='"+ bean.getRegion() + "' and airportcode='"+ bean.getStation()+"'";
			 rs1 = st.executeQuery(emolumentsCount);
			int emolumentsCountno = 0;

			if (rs1.next()) {
				emolumentsCountno = Integer.parseInt(rs.getString("count"));
				// for East Region 9
				// for CHQIAD KOLKATA 12
				emolumentsCountno += countMonthsfromMaytoArrearsdate;
			}
			String emoluments1 = "0.00", cpfarrear1 = "0.00";
			log.info("dateofjoining " + dateofjoing);
			 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			 Date	transdate = null;
			if (dateofjoing!=""){
			  	transdate = df.parse(dateofjoing);
             if(transdate.before(new Date("01-Apr-1995"))) {
				df.parse(dateofjoing);
				emoluments1 = String.valueOf(Math.round(Double
						.parseDouble(emoluments)
						* emolumentsCountno / (months)));
				cpfarrear1 = String.valueOf(Math.round(Double
						.parseDouble(emoluments1) * (0.1)));
			} else {
				emoluments1 = emoluments;
				cpfarrear1 = cpfarrear;
			}	
			}
			String Insertemoluments_log="insert into emoluments_log(oldemoluments,oldemppfstatuary,cpfacno,pensionno,region,monthyear,updateddate)(select emoluments,emppfstatuary,cpfaccno,pensionno,region,'"+monthYear+"','06-Oct-2010' from employee_pension_validate where region='"+bean.getRegion()+"' and airportcode='"+bean.getStation()+"' and to_char(monthyear,'dd-Mon-yyyy')='"+monthYear+"' and cpfaccno='"+bean.getCpfAcNo()+"' and empflag='N')";
			log.info("Insertemoluments_log"+Insertemoluments_log);
			st1.executeUpdate(Insertemoluments_log);
			log.info("dateofjoing"+dateofjoing);
			log.info("foundSalaryDate"+foundSalaryDate);
			if (!dateofjoing.equals(null)&& dateofjoing !="" && !foundSalaryDate.trim().equals("")) {
			String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION,pensionno,airportcode) values('"
					+ bean.getCpfAcNo()
					+ "','"
					+ monthYear
					+ "','"
					+ cpfarrear + "','"+ bean.getRegion() + "','"+pensionNo+"','"+bean.getStation()+"')";
				st.addBatch(insertArrear);
			String transMonthYear = commonUtil.converDBToAppFormat(monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
				updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
						+ emoluments1
						+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
						+ cpfarrear1
						+ "')),pensionNo='"+pensionNo+"',remarks=remarks||'"+bean.getRemarks()+"'  where REGION='"+ bean.getRegion() + "' and empflag='Y'  and cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
						+ transMonthYear + "' and airportcode='"+bean.getStation()+"'";

				st.addBatch(updateArrears);
				int count1[] = st.executeBatch();
				log.info("count is" + count1.length);
				conn.commit();										
				String updateemoluments_log="update emoluments_log set (newemoluments,newemppfstatuary)=(select emoluments, emppfstatuary from employee_pension_validate   where region = '"+bean.getRegion()+"' and  monthyear = '"+monthYear+"' and  cpfaccno='"+bean.getCpfAcNo()+"' and empflag='Y') where region = '"+bean.getRegion()+"' and  monthyear = '"+monthYear+"' and  cpfacno='"+bean.getCpfAcNo()+"' ";					
				log.info(updateemoluments_log);
				st.executeUpdate(updateemoluments_log);
				
			}else{
				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION,arrearflag,pensionno,AIRPORTCODE) values('"
					+ bean.getCpfAcNo()
					+ "','"
					+ monthYear
					+ "','"
					+ cpfarrear + "','"+ bean.getRegion() + "','N','"+pensionNo+"','"+bean.getStation()+"')";
				st.executeUpdate(insertArrear);
			}
			
		
		//log.info("insertArrear " + insertArrear);
			log.info("updateArrears " + updateArrears);

		}
	} catch (Exception e) {
		log.printStackTrace(e);
	}
	 finally {

			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException se) {
					System.out.println("Problem in closing Resultset ");
				}
			}

			if (st != null ||st1!=null) {
				try {
					st.close();
					st1.close();
					st = null;
					conn.close();
				} catch (SQLException se) {
					System.out.println("Problem in closing Statement.");
				}
			}

			// this.closeConnection(con, st, rs);
		}

	log.info("ImportDao :importIADArr9697 leaving method");
	}
	public void importChqnadArr9697(String xlsData) throws InvalidDataException {
		log
				.info("ImportDao :importChqnadArr9697 bifurcalculaiton entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[3].equals("XXX")) {
					cpfarrear = tempInfo[3].trim();
				} else {
					cpfarrear = "0";
				}

				emoluments = String.valueOf((Integer.parseInt(cpfarrear) * 10));
				/*
				 * if (!tempInfo[2].equals("XXX")) { emoluments =
				 * tempInfo[2].trim(); } else { emoluments = "0.00"; }
				 */

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"CHQNAD");
				String updateArrears = "";
				int count = 0;
				String noofMonthsQuery = "select round(months_between('01-Nov-1996',NVL(DATEOFJOINING,'01-Apr-1995'))) as months,to_date(to_char(NVL(DATEOFJOINING,'01-Apr-1995'),'dd-Mon-yyyy')) as doj from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' AND REGION='CHQNAD'";
				ResultSet rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				Date dateofjoing = null;
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
					dateofjoing = rs.getDate("doj");

				}
				String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
						+ bean.getCpfAcNo() + "' and region='CHQNAD'";
				ResultSet rs1 = st.executeQuery(emolumentsCount);
				int emolumentsCountno = 0;

				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
					// for East Region 9
					// for CHQIAD KOLKATA 12
					emolumentsCountno += 9;
				}
				String emoluments1 = "", cpfarrear1 = "";
				log.info("dateofjoining " + dateofjoing);
				if (!dateofjoing.equals(null)
						&& dateofjoing.before(new Date("01-Apr-95"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','CHQNAD')";
				st.addBatch(insertArrear);
				if (!foundSalaryDate.equals("")) {
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments1
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear1
							+ "'))  where   region='CHQNAD'  and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";
					st.addBatch(updateArrears);

				}
				int count1[] = st.executeBatch();
				log.info("count is" + count1.length);
				log.info("insertArrear " + insertArrear);
				log.info("updateArrears " + updateArrears);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log
				.info("ImportDao :importChqnadArr9697  bifulcalculation leaving method");

	}

	public void importOfficeComplexArr9697(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :importOfficeComplexArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[7].equals("XXX")) {
					cpfarrear = tempInfo[7].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[7].equals("XXX")) {
					emoluments = String.valueOf(Math.round(Double
							.parseDouble(cpfarrear) * 10));
				} else {
					emoluments = "0.00";
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"CHQIAD");

				String updateArrears = "";
				int count = 0;
				String noofmonthsFrmmay95toTransactionDate = "select round(months_between('"
						+ monthYear
						+ "',NVL('01-May-1995','01-May-1995'))) as count from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' AND REGION='CHQIAD'";
				String noofMonthsQuery = "select round(months_between('"
						+ monthYear
						+ "', NVL( (CASE WHEN DATEOFJOINING <= to_date('01-Jan-1992', 'dd-Mon-yyyy') then  '01-Jan-1992'   ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy') END), '01-Apr-1995'))) as months, to_date(to_char(NVL(DATEOFJOINING, '01-Apr-1995'), 'dd-Mon-yyyy')) as doj from employee_info info WHERE CPFACNO = '"
						+ bean.getCpfAcNo() + "' AND REGION = 'CHQIAD'";
				ResultSet rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				Date dateofjoing = null;
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
					dateofjoing = rs.getDate("doj");

				}

				ResultSet rs1 = st
						.executeQuery(noofmonthsFrmmay95toTransactionDate);
				int emolumentsCountno = 0;

				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));

				}
				String emoluments1 = "", cpfarrear1 = "";
				log.info("dateofjoining " + dateofjoing);
				if (!dateofjoing.equals(null)
						&& dateofjoing.before(new Date("01-Apr-95"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}
				String transMonthYear = commonUtil.converDBToAppFormat(
						monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				String updateTransaction = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
						+ emoluments1
						+ "')),EMPPFSTATUARY =to_char(TO_NUMBER(nvl(EMPPFSTATUARY ,0)+'"
						+ cpfarrear1
						+ "')) where cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and region='CHQIAD' and to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "'";
				log.info("update transaction " + updateTransaction);
				st.executeUpdate(updateTransaction);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("ImportDao : OfficeCOmplex leaving method");

	}

	public void importIGIArr9697(String xlsData, String username,
			String computername) throws InvalidDataException {
		log.info("ImportDao :importIGIArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		String emoluments_log = "", emoluments_log_history = "";
		String emppfstatuary = "0.00", oldemoluments = "0.00", oldemppfstatuary = "";
		String emoluments1 = "", cpfarrear1 = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					bean.setEmpNumber(tempInfo[0].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					cpfarrear = tempInfo[6].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					emoluments = String.valueOf(Math.round(Double
							.parseDouble(cpfarrear) * 10));
				} else {
					emoluments = "0.00";
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"CHQIAD");
				String updateArrears = "";
				int count = 0;
				String noofmonthsFrmmay95toTransactionDate = "select round(months_between('"
						+ monthYear
						+ "',NVL('01-May-1995','01-May-1995'))) as count from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' AND REGION='CHQIAD'";
				String noofMonthsQuery = "select round(months_between('"
						+ monthYear
						+ "', NVL( (CASE WHEN DATEOFJOINING <= to_date('01-Jan-1992', 'dd-Mon-yyyy') then  '01-Jan-1992'   ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy') END), '01-Apr-1995'))) as months, to_date(to_char(NVL(DATEOFJOINING, '01-Apr-1995'), 'dd-Mon-yyyy')) as doj from employee_info info WHERE CPFACNO = '"
						+ bean.getCpfAcNo() + "' AND REGION = 'CHQIAD'";
				rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				Date dateofjoing = null;
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
					dateofjoing = rs.getDate("doj");

				}
				ResultSet rs1 = st
						.executeQuery(noofmonthsFrmmay95toTransactionDate);
				int emolumentsCountno = 0;
				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
				}
				if (!dateofjoing.equals(null)
						&& dateofjoing.before(new Date("01-Apr-95"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}
				String transMonthYear = commonUtil.converDBToAppFormat(
						monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				Date transdate = df.parse(monthYear);
				String updateTransaction = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
						+ emoluments1
						+ "')),EMPPFSTATUARY =to_char(TO_NUMBER(nvl(EMPPFSTATUARY ,0)+'"
						+ cpfarrear1
						+ "')) where cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and region='CHQIAD' and to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "'";
				log.info("update transaction " + updateTransaction);

				// new code for tracking
				ImportDao IDAO = new ImportDao();
				oldemoluments = IDAO.getEmoluments(conn, monthYear, bean
						.getCpfAcNo(), "", "CHQIAD");
				if ((transdate.after(new Date("31-Mar-1998"))
						&& oldemoluments != "" && Float
						.parseFloat(oldemoluments) != 0.00)) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 12 / 100);
				} else if (oldemoluments != ""
						&& Float.parseFloat(oldemoluments) != 0.00) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 10 / 100);
				}

				String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");

				log.info(" update transaction" + updateTransaction);
				st.executeUpdate(updateTransaction);
				String newemolumentsQuery = "select emoluments,EMPPFSTATUARY from employee_pension_validate where cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "' and region='CHQIAD'";
				String newemoluments = "", newcpf = "";
				rs = st.executeQuery(newemolumentsQuery);
				while (rs.next()) {
					newemoluments = rs.getString("emoluments");
					newcpf = rs.getString("EMPPFSTATUARY");
				}

				emoluments_log = "update emoluments_log set newemoluments='"
						+ newemoluments + "',NEWEMPPFSTATUARY='" + newcpf
						+ "', monthyear='" + monthYear + "',UPDATEDDATE='"
						+ updatedDate
						+ "',pensionno='',region='CHQIAD',username='"
						+ username + "',computername='" + computername
						+ "' where cpfacno='" + bean.getCpfAcNo()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "' and region='CHQIAD'";
				emoluments_log_history = "update emoluments_log_history set oldemoluments='"
						+ oldemoluments
						+ "',OLDEMPPFSTATUARY='"
						+ oldemppfstatuary
						+ "', newemoluments='"
						+ newemoluments
						+ "',NEWEMPPFSTATUARY='"
						+ newcpf
						+ "', monthyear='"
						+ monthYear
						+ "',UPDATEDDATE='"
						+ updatedDate
						+ "',pensionno='',region='CHQIAD',username='"
						+ username
						+ "',computername='"
						+ computername
						+ "' where cpfacno='"
						+ bean.getCpfAcNo()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "' and region='CHQIAD'";
				log.info("emoluments_log .." + emoluments_log);
				log.info("emoluments_log_history " + emoluments_log_history);

				st.executeUpdate(emoluments_log);
				st.executeUpdate(emoluments_log_history);

			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(conn, st, rs);
		}

		log.info("ImportDao : importIGIArr9697 leaving method");

	}

	public void importNERArr9697(String xlsData) throws InvalidDataException {
		log.info("ImportDao :importNERArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpNumber(tempInfo[2].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setDesegnation(tempInfo[4].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[5].equals("XXX")) {
					cpfarrear = tempInfo[5].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					emoluments = String.valueOf(Math.round(Double
							.parseDouble(cpfarrear) * 10));
				} else {
					emoluments = "0.00";
				}
				emoluments = String.valueOf(Math.round(Double
						.parseDouble(cpfarrear) * 10));
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"North-East Region");
				String foundArrearDate = this.checkFinanceArrear(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"North-East Region");
				String updateArrears = "";
				int count = 0;
				String noofMonthsQuery = "select round(months_between('01-Aug-1996',NVL(DATEOFJOINING,'01-Apr-1995'))) as months,to_date(to_char(NVL(DATEOFJOINING,'01-Apr-1995'),'dd-Mon-yyyy')) as doj from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo()
						+ "' AND REGION='North-East Region'";
				ResultSet rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				Date dateofjoing = null;
				if (rs.next()) {
					months = Integer.parseInt(rs.getString("months"));
					dateofjoing = rs.getDate("doj");

				}
				String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and region='North-East Region'";
				ResultSet rs1 = st.executeQuery(emolumentsCount);
				int emolumentsCountno = 0;

				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
					// for East Region 9
					emolumentsCountno += 9;
				}
				String emoluments1 = "", cpfarrear1 = "";
				log.info("dateofjoining " + dateofjoing);
				if (!dateofjoing.equals(null)
						&& dateofjoing.before(new Date("01-Apr-95"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR(CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,REGION) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ bean.getEmpNumber()
						+ "','"
						+ bean.getEmpName()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','North-East Region')";
				String transMonthYear = commonUtil.converDBToAppFormat(
						monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
				if (foundArrearDate.equals("")) {
					st.addBatch(insertArrear);
				} else {
					insertArrear = "update EMPLOYEE_PENSION_ARREAR  set ARREARAMT=to_char(TO_NUMBER(nvl(ARREARAMT,0)+'"
							+ cpfarrear
							+ "'))  where   region='North-East Region'  and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(ARREARDATE,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";

					st.addBatch(insertArrear);
				}
				if (!foundSalaryDate.equals("")) {

					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments1
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear1
							+ "'))  where   region='North-East Region'  and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";
					st.addBatch(updateArrears);

				}
				int count1[] = st.executeBatch();
				log.info("count is" + count1.length);
				log.info("insertArrear " + insertArrear);
				log.info("updateArrears " + updateArrears);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao : importEastArr9697 leaving method");

	}

	public void importArrCountCHQNAD9697(String xlsData)
			throws InvalidDataException {
		int total = 0;
		log.info("ImportDao :importArrCountCHQNAD() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", empName = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo;
				if (!tempInfo[0].equals("XXX")) {
					cpfaccno = tempInfo[0].trim();
				} else {
					cpfaccno = "";
				}
				String monthYear = "";
				if (!tempInfo[1].equals("XXX")) {
					monthYear = tempInfo[1].trim();
				} else {
					monthYear = "";
				}

				if (!tempInfo[2].equals("XXX")) {
					empName = tempInfo[2].trim();
				} else {
					empName = "";
				}
				int arrCount = 0, arreCal = 0;
				if (!tempInfo[4].equals("XXX")) {
					arrCount = Integer.parseInt(tempInfo[4].trim());
				}

				if (!tempInfo[5].equals("XXX")) {
					arreCal = Integer.parseInt(tempInfo[5].trim());
				}
				String empNumber = "";
				if (!tempInfo[6].equals("XXX")) {
					empNumber = tempInfo[6].trim();
				}
				log.info("empnm " + empNumber);
				// for chqnad arreCal
				// arreCal = (arrCount) * 100 / 10;

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,REGION,AIRPORTCODE) values('"
						+ cpfaccno
						+ "','"
						+ empNumber
						+ "','"
						+ empName
						+ "','"
						+ monthYear
						+ "','"
						+ arrCount
						+ "','CHQNAD','CHQNAD')";
				String insertArrearCount = "";
				monthYear = commonUtil.converDBToAppFormat(monthYear,
						"dd-MMM-yyyy", "MMM-yyyy");
				insertArrearCount = "update employee_pension_validate set EMOLUMENTS=EMOLUMENTS+"
						+ arreCal
						+ ", EMPPFSTATUARY=EMPPFSTATUARY+"
						+ arrCount
						+ " where region='CHQNAD' and cpfaccno='"
						+ cpfaccno
						+ "' and to_char(monthyear,'Mon-yyyy')='"
						+ monthYear
						+ "'";
				log.info("Update ArrearCount " + insertArrearCount);
				log.info("insertArrear " + insertArrear);
				// int cout = st.executeUpdate(insertArrearCount);
				// total = total + cout;
				st.addBatch(insertArrear);
				st.addBatch(insertArrearCount);
				int count[] = st.executeBatch();
				log.info("count is" + count.length);

			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importArrCountCHQNAD leaving method");

	}

	public void generateNewPFIDs(String xlsData, String userName,
			String ipAddress) {

		log.info("ImportDao :generateNewPFIDs() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setRegion(tempInfo[5].trim());
				} else {
					bean.setRegion("");
				}

				String sequenceNO = PersonalDAO.getSequenceNo(conn);

				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				PersonalDAO.addPersonalInfo(sequenceNO, bean.getCpfAcNo(),
						lastactive, bean.getRegion(), userName, ipAddress,
						lastactive);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :updateMasterNmtoTrans leaving method");

	}

	public void updateEmployeenametoMasterFromTrans(String xlsData,
			String userName, String ipAddress) {

		log
				.info("ImportDao :updateEmployeenametoMasterFromTrans() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setRegion(tempInfo[4].trim());
				} else {
					bean.setRegion("");
				}
				log.info("cpfaccno " + bean.getCpfAcNo() + " region "
						+ bean.getRegion());
				String empName = "";
				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				String selectEmpName = "select distinct(employeename) from employee_pension_validate where region='"
						+ bean.getRegion()
						+ "' and cpfaccno='"
						+ bean.getCpfAcNo() + "'";
				ResultSet rs = st.executeQuery(selectEmpName);

				if (rs.next()) {
					if (rs.getString("employeename") != null) {
						empName = rs.getString("employeename");
						st
								.executeQuery("update employee_info set employeename='"
										+ empName
										+ "',LASTACTIVE='"
										+ lastactive
										+ "',username='"
										+ userName
										+ "' where region='"
										+ bean.getRegion()
										+ "' and cpfacno='"
										+ bean.getCpfAcNo() + "'");
					}
				}

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :updateMasterNmtoTrans leaving method");

	}

	public void updateFinaceDatahavingCpfaccnoBlank(String xlsData,
			String userName, String ipAddress) {

		log
				.info("ImportDao :updateFinaceDatahavingCpfaccnoBlank() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[3].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setRegion(tempInfo[8].trim());
				} else {
					bean.setRegion("");
				}
				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				String cpfaccno = commonDAO.checkEmployeeInfo1(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						bean.getRegion());
				String insertCpfaccno = "";
				if (!cpfaccno.equals("")) {
					bean.setCpfAcNo(cpfaccno);
				} else {
					cpfaccno = bean.getRegion().substring(0, 2) + "-"
							+ bean.getEmpNumber();
					bean.setCpfAcNo(cpfaccno);
					insertCpfaccno = "insert into employee_info (cpfacno,employeeno,employeename,region,userName,LASTACTIVE) values('"
							+ cpfaccno
							+ "','"
							+ bean.getEmpNumber()
							+ "','"
							+ bean.getEmpName()
							+ "','"
							+ bean.getRegion()
							+ "','" + userName + "','" + lastactive + "')";
					st.executeUpdate(insertCpfaccno);
				}
				String foundSalaryDate = commonDAO.checkFinanceDuplicate(conn,
						monthYear, "", bean.getEmpNumber(), bean.getRegion()
								.trim());
				String updateCpfaccno = "";
				if (!foundSalaryDate.equals("")) {
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateCpfaccno = "update employee_pension_validate set  cpfaccno='"
							+ bean.getCpfAcNo()
							+ "',activeflag='N'  where   region='"
							+ bean.getRegion()
							+ "' and employeeno='"
							+ bean.getEmpNumber()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";
					st.executeUpdate(updateCpfaccno);
				}
				log.info("updateCpfaccno " + updateCpfaccno);
				log.info("insertCpfaccno " + insertCpfaccno);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log
				.info("ImportDao :updateFinaceDatahavingCpfaccnoBlank leaving method");

	}

	public void updateFinaceDatahavingCpfaccnoBlank1(String xlsData,
			String userName, String ipAddress) {
		log
				.info("ImportDao :updateFinaceDatahavingCpfaccnoBlank1() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String monthYear = "";
				if (!tempInfo[0].equals("XXX")) {
					monthYear = tempInfo[0].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[3].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmpName(xlsEmpName.trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setBasic(tempInfo[5].trim());
				} else {
					bean.setBasic("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setEmoluments(tempInfo[6].trim());
				} else {
					bean.setEmoluments("");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setRegion(tempInfo[8].trim());
				} else {
					bean.setRegion("");
				}
				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				String cpfaccno = commonDAO.checkEmployeeInfo1(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						bean.getRegion());
				bean.setCpfAcNo(cpfaccno);

				int count = commonDAO.checkRecordCount(conn, monthYear,
						cpfaccno, bean.getEmpNumber(), bean.getRegion().trim());
				String updateCpfaccno = "";
				if (count == 2) {
				}
				}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log
				.info("ImportDao :updateFinaceDatahavingCpfaccnoBlank1 leaving method");

	}

	public void importIgiCargo0809Data(String xlsData) {

		log.info("ImportDao :importIgiCargo0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				log.info("emoluments " + tempInfo[9].trim());
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.0");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.0");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.0");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.0");
				}
				/* for east Region */
				if (!tempInfo[14].equals("XXX")) {
					bean.setStation(tempInfo[14].trim());
				} else {
					bean.setStation("");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF()
						+ "emoluments " + bean.getEmoluments());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "East Region");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation().trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "East Region".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "'))   where region='East Region' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importIgiCargo0809Data leaving method");

	}

	public void importOfficeComplex0809Data(String xlsData) {

		log.info("ImportDao :importOfficeComplex0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.0");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.0");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.0");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.0");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.0");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "OFFICE COMPLEX".trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "CHQIAD".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "'))  where region='CHQIAD' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear
							+ "' and airportcode='OFFICE COMPLEX'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importOfficeComplex0809Data leaving method");

	}

	public void importTrivendrum0809Data(String xlsData) {
		log.info("ImportDao :importTrivendrum0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[12].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setPrincipal(tempInfo[13].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setInterest(tempInfo[14].trim());
				} else {
					bean.setInterest("0.00");
				}
				double pf = 0.0, pensionContri = 0.0;
				if (!tempInfo[19].equals("XXX")) {
					pf = Double.parseDouble(tempInfo[19].trim());
				} else {
					pf = 0.00;
				}
				if (!tempInfo[20].equals("XXX")) {
					pensionContri = Double.parseDouble(tempInfo[20].trim());
				} else {
					pensionContri = 0.00;
				}

				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,empvpf,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PF,PENSIONCONTRI)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "TRIVANDRUM IAD".trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "CHQIAD".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setDouble(14, pf);
					pst.setDouble(15, pensionContri);
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "'))  where region='CHQIAD' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";
		int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importTrivendrum0809Data leaving method");

	}

	public void importAmritserIAD0809Data(String xlsData) {
		log.info("ImportDao :importAmritserIAD0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("");
				}

				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "AmritsarIAD".trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "CHQIAD".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')) where region='CHQIAD' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";
					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importAmritserIAD0809Data leaving method");

	}

	public void importCHQNAD0809Data(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importCHQNAD0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					String empnumber = tempInfo[3].trim();
					// empnumber = empnumber.substring(2, empnumber.length());
					// empnumber =
					// commonUtil.trailingZeros(empnumber.toCharArray());
					/*
					 * if(empnumber.startsWith("0")){
					 * empnumber=empnumber.substring(1,empnumber.length()); }
					 */
					bean.setEmpNumber(tempInfo[3].trim());

					// bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.00");
				}
				int count = 0, countEmployeeInfo = 0;
				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						"CHQNAD");

				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation,region,empserialnumber) VALUES "
							+ "(?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, "CHQNAD".trim());
					pst.setString(3, bean.getEmpNumber());
					pst.setString(4, bean.getEmpName());
					pst.setString(5, bean.getDesegnation());
					// for southern region Data
					pst.setString(6, "CHQNAD".trim());
					pst.setString(7, bean.getPfid().trim());
					pst.executeUpdate();
					pst.close();
				}

				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,pensionno,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "CHQNAD");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "CHQNAD".trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "CHQNAD".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')) where region='CHQNAD' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";

					log.info("update emolumentss " + insertArrearCount);
					st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		}

		log.info("ImportDao :importCHQNAD0809Data leaving method");

	}

	public void importKolkataIad0809Data(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :importCAPIAD0809Data() entering method");
		// log.info("ImportDao :importKolkataIad0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {

					bean.setEmpNumber(tempInfo[3].trim());

				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setEmpName(tempInfo[4].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesegnation(tempInfo[5].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[8].equals("XXX")) {
					monthYear = tempInfo[8].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.00");
				}
				if (!tempInfo[18].equals("XXX")) {
					bean.setStation(tempInfo[18].trim());
				} else {
					bean.setStation("");
				}

				if (!tempInfo[20].equals("XXX")) {
					bean.setRegion(tempInfo[20].trim());
				} else {
					bean.setRegion("");
				}
				double pensioncontri = 0.00, pf = 0.00;
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for Cpfaccno " + bean.getCpfAcNo()
								+ " . PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}

					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					pensioncontri = fDao.pensionCalculation(monthYear, bean
							.getEmoluments(), bean.getWetherOption(), bean
							.getRegion(),"");
				 pf = Double.parseDouble(bean.getEmployeePF().toString())
							- pensioncontri;

				}
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), bean.getRegion().trim());

				if (foundSalaryDate.equals("") && bean.getPfid() != "") {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation().trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					float emoluments = 0.0f;
					if (bean.getEmployeePF().trim() != ""
							|| (!bean.getEmployeePF().trim().equals(""))) {
						emoluments = Float.parseFloat(bean.getEmployeePF()
								.trim()) * 100 / 12;
					}

					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					if (!bean.getEmoluments().equals("0.00")
							&& !bean.getEmoluments().equals("0.0")
							&& !bean.getEmoluments().equals("0")
							&& !bean.getEmoluments().equals("")) {
						insertArrearCount = "update employee_pension_validate set EMOLUMENTS=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='"
								+ bean.getRegion()
								+ "' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					} else {
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='"
								+ bean.getRegion()
								+ "' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					}
					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);

				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			if (st != null) {
				try {
					rs.close();
					st.close();
					st = null;

				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
			log.info("ImportDao :importEastRegion0809Data leaving method");
			// log.info("ImportDao :importKolkataIad0809Data leaving method");

		}
	}

	public void deductSouth9697ArrearData(String xlsData) {
		log.info("ImportDao :deductSouth9697ArrearData() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				String cpf = "";
				if (!tempInfo[7].equals("XXX")) {
					cpf = tempInfo[7].trim();
				} else {
					cpf = "";
				}
				int emoluments = 0;
				emoluments = Integer.parseInt(cpf) * 10;
				// for South and westDeduction need below column
				/*
				 * if (!tempInfo[6].equals("XXX")) { emoluments =
				 * Integer.parseInt(tempInfo[6].trim()); }
				 */

				monthYear = commonUtil.converDBToAppFormat(monthYear,
						"dd-MMM-yyyy", "MMM-yyyy");
				String deductArrear = "update employee_pension_validate set EMOLUMENTS=EMOLUMENTS-("
						+ emoluments
						+ "), EMPPFSTATUARY=EMPPFSTATUARY-("
						+ cpf
						+ ") where region='CHQNAD' and cpfaccno='"
						+ bean.getCpfAcNo()
						+ "' and to_char(monthyear,'Mon-yyyy')='"
						+ monthYear
						+ "'";
				log.info(deductArrear);
				st.executeUpdate(deductArrear);

			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :deductSouth9697ArrearData leaving method");

	}

	public void importSouthArr9697(String xlsData) throws Exception {
		log.info("ImportDao :importSouthArr9697 entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			Statement st1 = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", cpfarrear = "", emoluments = "", empPfstatuary = "", DAILYALLOWANCE = "";
				String monthYear = "";
				if (!tempInfo[2].equals("XXX")) {
					monthYear = tempInfo[2].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[7].equals("XXX")) {
					cpfarrear = tempInfo[7].trim();
				} else {
					cpfarrear = "0.00";
				}
				if (!tempInfo[6].equals("XXX")) {
					emoluments = tempInfo[6].trim();
				} else {
					emoluments = "0.00";
				}

				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear, bean.getCpfAcNo(), bean.getEmpNumber(),
						"South Region");
				String updateArrears = "";
				int count = 0;
				// String noofmonthsFrmmay95toTransactionDate=
				// "select round(months_between('"+monthYear+
				// "',NVL('01-May-1995','01-May-1995'))) as count from employee_info WHERE CPFACNO='"
				// +bean.getCpfAcNo()+"' AND REGION='CHQIAD'";
				// String noofMonthsQuery =
				// "select round(months_between('"+monthYear+
				// "', NVL( (CASE WHEN DATEOFJOINING <= to_date('01-Jan-1992', 'dd-Mon-yyyy') then  '01-Jan-1992'   ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy') END), '01-Apr-1995'))) as months, to_date(to_char(NVL(DATEOFJOINING, '01-Apr-1995'), 'dd-Mon-yyyy')) as doj from employee_info info WHERE CPFACNO = '"
				// +bean.getCpfAcNo()+"' AND REGION = 'CHQIAD'";

				String noofMonthsQuery = "select round(months_between('01-Aug-1996',CASE WHEN DATEOFJOINING <= to_date('01-Jan-1992', 'dd-Mon-yyyy') then  '01-Jan-1992'   ELSE  to_char(DATEOFJOINING,'dd-Mon-yyyy') END)) as months,to_char(DATEOFJOINING,'dd-Mon-yyyy') as doj from employee_info WHERE CPFACNO='"
						+ bean.getCpfAcNo() + "' AND REGION='South Region'";
				ResultSet rs = st.executeQuery(noofMonthsQuery);
				int months = 0;
				String dateofjoing = "";
				if (rs.next()) {
					if (rs.getString("doj") != null) {
						months = Integer.parseInt(rs.getString("months"));
						dateofjoing = rs.getString("doj");
					}
					// dateofjoing = rs.getDate("doj");
				}
				String emolumentsCount = "SELECT count(*) as count FROM EMPLOYEE_PENSION_VALIDATE WHERE EMOLUMENTS<5000 AND  monthyear>=to_date('1-May-1995','dd-Mon-yyyy') and monthyear<=to_date('30-Nov-1995','dd-Mon-yyyy')  and cpfaccno='"
						+ bean.getCpfAcNo() + "' and region='South Region'";
				ResultSet rs1 = st.executeQuery(emolumentsCount);
				int emolumentsCountno = 0;
				if (rs1.next()) {
					emolumentsCountno = Integer.parseInt(rs.getString("count"));
					// for East Region 9
					// for CHQIAD KOLKATA 12
					// for west region emolumentsCountno += 10;
					emolumentsCountno += 9;
				}
				String emoluments1 = "", cpfarrear1 = "";
				log.info("dateofjoining " + dateofjoing);
				if (dateofjoing != null
						&& dateofjoing != ""
						&& new Date(dateofjoing)
								.before(new Date("01-Apr-1995"))) {
					emoluments1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments)
							* emolumentsCountno / (months)));
					cpfarrear1 = String.valueOf(Math.round(Double
							.parseDouble(emoluments1) * (0.1)));
				} else {
					emoluments1 = emoluments;
					cpfarrear1 = cpfarrear;
				}

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,ARREARDATE,ARREARAMT,REGION) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ monthYear
						+ "','"
						+ cpfarrear + "','South Region')";

				if (!foundSalaryDate.equals("") && !dateofjoing.equals(null)
						&& dateofjoing != "") {
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yy", "-MMM-yy");
					updateArrears = "update employee_pension_validate set  emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ emoluments1
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ cpfarrear1
							+ "'))  where   region='South Region'  and cpfaccno='"
							+ bean.getCpfAcNo()
							+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
							+ transMonthYear + "'";

				}
				log.info("dateofjoing" + dateofjoing);
				if (!dateofjoing.equals(null)
						&& dateofjoing != ""
						&& new Date(dateofjoing)
								.before(new Date("31-Mar-1997"))
						&& !foundSalaryDate.equals("")) {
					log.info("insertArrear " + insertArrear);
					log.info("updateArrears " + updateArrears);

					st1.addBatch(insertArrear);
					st1.addBatch(updateArrears);
					log.info("cpfaccno updated is" + bean.getCpfAcNo());
					int count1[] = st1.executeBatch();
					log.info("count is" + count1.length);
				} else {
					log.info("Missing cpfaccno doj is" + bean.getCpfAcNo());
				}

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importSouthArr9697 leaving method");

	}

	public void pensionCalculation(String xlsData) throws InvalidDataException {

		log.info("ImportDao :pensionCalculation() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement upDst = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", emoluments = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		FinancialReportDAO FDAO = new FinancialReportDAO();
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();

			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					bean.setPfid(tempInfo[0].trim());
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setRegion(tempInfo[10].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setWetherOption(tempInfo[6].trim());
				} else {
					bean.setWetherOption("");
				}

				String sql3 = "";

				sql3 = "select  emoluments, to_char(monthyear,'dd-Mon-yyyy')as monthyear from  employee_pension_validate 	 where region='"
						+ bean.getRegion()
						+ "' and cpfaccno='"
						+ bean.getCpfAcNo() + "' ";
				log.info(sql3);
				try {
					rs = st.executeQuery(sql3);
					int j = 0, k = 0;
					while (rs.next()) {
						String monthyear = rs.getString("monthyear");
						emoluments = rs.getString("emoluments");
						emoluments = FDAO.calWages(emoluments, monthyear, bean
								.getWetherOption(), false, false,"1");
						double pensioncont = FDAO.pensionCalculation(rs
								.getString("monthyear"), emoluments, bean
								.getWetherOption(), bean.getRegion(),"");
						j++;
						log.info(j + "=" + bean.getCpfAcNo() + "="
								+ pensioncont);
						upDst = conn.createStatement();
						String updateQuery = "update employee_pension_validate set pensioncont='"
								+ pensioncont
								+ "',pensionno='"
								+ bean.getPfid()
								+ "'  where region='"
								+ bean.getRegion()
								+ "' and cpfaccno='"
								+ bean.getCpfAcNo()
								+ "' and monthyear='"
								+ monthyear + "'";
						log.info(updateQuery);
						k = upDst.executeUpdate(updateQuery);
						upDst.close();
						upDst = null;

					}

				} catch (Exception e) {
					log.printStackTrace(e);
				}
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("ImportDao :pensionCalculation leaving method");

	}

	public void writeIgiCargo0809DataPFID(String xlsData) {

		log.info("ImportDao :writeIgiCargo0809DataPFID() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		ArrayList fnlDataList = new ArrayList();
		FileWriter fw = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", empDbName = "", dateofbirth = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[2].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[2].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[1].equals("XXX")) {
					// for CHQNAD getting cpfaccno passing empnumber
					/*
					 * String empnumber= tempInfo[3].trim();
					 * log.info("empno "+empnumber);
					 * empnumber=empnumber.substring(2,empnumber.length());
					 * empnumber=
					 * commonUtil.trailingZeros(empnumber.toCharArray());
					 * log.info("empno "+empnumber); String cpfaccnoQuery=
					 * "select cpfacno from employee_info where employeeno='"
					 * +empnumber+"' and region='CHQNAD' and rownum=1";
					 * rs1=st.executeQuery(cpfaccnoQuery); while(rs1.next()){
					 * if(rs1.getString("cpfacno")!=null){
					 * bean.setCpfAcNo(rs1.getString("cpfacno")); }else{
					 * bean.setCpfAcNo(""); }
					 */

					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}
				log.info("cpfaccno " + bean.getCpfAcNo());
				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				bean.setDateofBirth(monthYear);
				log.info("monthyear " + monthYear);
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("");
				}
				String sequenceNoQuery = "";
				if (bean.getCpfAcNo() != "") {
					// sequenceNoQuery=
					// "select pensionno,to_char(dateofbirth,'dd-Mon-yyyy')as dob,employeename from employee_personal_info where cpfacno='"
					// +bean.getCpfAcNo()+"'  and rownum=1";
					sequenceNoQuery = "select WETHEROPTION,pensionno,to_char(dateofbirth,'dd-Mon-yyyy')as dob,employeename from employee_personal_info where cpfacno='"
							+ bean.getCpfAcNo()
							+ "' and region='CHQIAD' and rownum=1";
				}
				log.info(sequenceNoQuery);
				if (sequenceNoQuery != "") {
					rs = st.executeQuery(sequenceNoQuery);
					while (rs.next()) {
						if (rs.getString("pensionno") != null) {
							bean.setEmpSerialNo(rs.getString("pensionno"));
						} else {
							bean.setEmpSerialNo("");
						}
						empDbName = rs.getString("employeename");
						if (rs.getString("dob") != null) {
							bean.setDateofBirth(rs.getString("dob"));
						} else {
							bean.setDateofBirth("");
						}

						if (rs.getString("WETHEROPTION") != null) {
							bean.setWetherOption(rs.getString("WETHEROPTION"));
						} else {
							bean.setWetherOption("");
						}
					}
				}
				log.info("serial no" + bean.getEmpSerialNo());
				if ((!bean.getEmpSerialNo().equals("") || (!bean
						.getEmpSerialNo().equals(null) || bean.getEmpSerialNo() != "00000"))) {
					bean.setPensionNumber(commonDAO.getPFID(empDbName, bean
							.getDateofBirth(), commonUtil.leadingZeros(5, bean
							.getEmpSerialNo())));
				} else {
					bean.setPensionNumber("");
				}

				fnlDataList.add(bean);
			}

			File outputFile = null;
			outputFile = new File("c://pensionoptionanddob.xls");
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(outputFile, ws);
			CommonUtil cUtil = new CommonUtil();
			ArrayList hdrsList = new ArrayList();
			cUtil.createSheet(fnlDataList, "test", workbook, hdrsList);
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :writeIgiCargo0809DataPFID leaving method");

	}

	public void north0809AutoMapping(String xlsData) {

		log.info("ImportDao :North0809AutoMapping() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		ArrayList fnlDataList = new ArrayList();
		FileWriter fw = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", empDbName = "", dateofbirth = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		String sqlSelQuery = "", insertQry = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("tempinfo  " + tempInfo[1].trim());
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));

				} else {
					bean.setPfid("");
				}

				if (!tempInfo[14].equals("XXX")) {
					bean.setRegion(tempInfo[14].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				log.info("pfid is " + bean.getPfid() + "region is "
						+ bean.getRegion());
				if (bean.getPfid() != "") {
					sqlSelQuery = "select to_char(PENSIONNO),AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
							+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,'CHQNAD','NAGA','"
							+ bean.getCpfAcNo()
							+ "','"
							+ lastactive
							+ "'"
							+ " FROM employee_personal_info WHERE  ROWID=(SELECT Max(ROWID) FROM EMPLOYEE_personal_INFO WHERE EMPFLAG='Y' AND PENSIONNO='"
							+ bean.getPfid()
							+ "' AND REGION='"
							+ bean.getRegion() + "')";
					insertQry = "insert into employee_info(EMPSERIALNUMBER,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
							+ "sex,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,USERNAME,cpfacno,lastactive)  "
							+ sqlSelQuery;

					log.info("insert query " + insertQry);
				}
				st.executeUpdate(insertQry);
				bean.setPfid("");
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException se) {
					log.printStackTrace(se);
				}
			}
		}

		log.info("ImportDao :North0809AutoMapping leaving method");

	}

	public void importWR0809Data(String xlsData) {

		log.info("ImportDao :importWR0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setBasic(tempInfo[7].trim());
				} else {
					bean.setBasic("0.00");
				}
				if (!tempInfo[8].equals("XXX")) {
					bean.setDailyAllowance(tempInfo[8].trim());
				} else {
					bean.setDailyAllowance("0.00");
				}
				String emoluments = String.valueOf(Float.parseFloat(bean
						.getBasic())
						+ Float.parseFloat(bean.getDailyAllowance()));

				bean.setEmoluments(emoluments.trim());

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmployeePF(tempInfo[9].trim());
				} else {
					bean.setEmployeePF("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("");
				}
				if (!tempInfo[0].equals("XXX")) {
					bean.setStation(tempInfo[0].trim());
				} else {
					bean.setStation("");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,basic,DAILYALLOWANCE)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "West Region");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation().trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "West Region".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getBasic().trim());
					pst.setString(14, bean.getDailyAllowance().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					float emoluments1 = 0.0f;
					if (bean.getEmployeePF().trim() != ""
							|| (!bean.getEmployeePF().trim().equals(""))) {
						emoluments1 = Float.parseFloat(bean.getEmployeePF()
								.trim()) * 100 / 12;
					}
					bean.setEmoluments(String.valueOf(emoluments1));
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "'))  where region='West Region' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importWR0809Data leaving method");

	}

	public void importNorth0809Data(String xlsData) {

		log.info("ImportDao :importNorth0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "North Region");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "North Region".trim());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "North Region".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "'))  where region='North Region' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importNorth0809Data leaving method");

	}

	public void importSouth0809Data(String xlsData) {

		log.info("ImportDao :importSouth0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		FinancialReportDAO fDao = new FinancialReportDAO();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String foundSalaryDate = "";
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setFromDate(tempInfo[4].trim());
				} else {
					bean.setFromDate("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setStation(tempInfo[7].trim());
				} else {
					bean.setStation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setRegion(tempInfo[14].trim());
				} else {
					bean.setRegion("");
				}
				int countEmployeeInfo = 0;
				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						bean.getRegion());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(empserialnumber,cpfacno,airportCode, employeeno,employeeName,desegnation,region,remarks,MAPPINGFLAG) VALUES "
							+ "(?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, bean.getPfid().trim());
					pst.setString(2, bean.getCpfAcNo().trim());
					pst.setString(3, bean.getStation().trim());
					pst.setString(4, bean.getEmpNumber().trim());
					pst.setString(5, bean.getEmpName().trim());
					pst.setString(6, bean.getDesegnation().trim());
					// for southern region Data
					pst.setString(7, bean.getRegion().trim());
					pst.setString(8, "new records from South Region".trim());
					pst.setString(9, "Y".trim());
					pst.executeUpdate();
					pst.close();
				}
				double pensioncontri = 0.00, pf = 0.00;
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					String checkPFID = "select wetheroption from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st.executeQuery(checkPFID);
					if (rs.next()) {
						if (rs.getString("wetheroption") != null) {
							bean.setWetherOption(rs.getString("wetheroption"));
						} else {
							bean.setWetherOption("");
						}
					}
					log.info("wetheroption" + bean.getWetherOption());
					foundSalaryDate = this.checkFinanceDuplicate(conn, bean
							.getFromDate().trim(), bean.getCpfAcNo(), bean
							.getEmpNumber(), bean.getRegion());

					pensioncontri = fDao.pensionCalculation(bean.getFromDate(),
							bean.getEmoluments(), bean.getWetherOption(), bean
									.getRegion(),"");
					pf = Double.parseDouble(bean.getEmployeePF().toString())
							- pensioncontri;
				}
				log.info("finaceInsert-----" + finaceInsert);
				/*
				 * String foundSalaryDate = this.checkFinanceDuplicate(conn,
				 * monthYear.trim(), bean.getCpfAcNo(), bean .getEmpNumber(),
				 * bean.getRegion());
				 */

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation().trim());
					pst.setString(5, bean.getFromDate().trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setDouble(14, pensioncontri);
					pst.setDouble(15, pf);
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(bean
							.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')), PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'pensioncontri')), PF=to_char(TO_NUMBER(nvl(PF,0)+'pf')) where region='"
							+ bean.getRegion().trim()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importSouth0809Data leaving method");

	}

	public void importRAUSAP0809Data(String xlsData) {

		log.info("ImportDao :importRausap0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				/*
				 * if (!tempInfo[7].equals("XXX")) {
				 * bean.setBasic(tempInfo[7].trim()); } else {
				 * bean.setBasic("0.00"); } if (!tempInfo[8].equals("XXX")) {
				 * bean.setDailyAllowance(tempInfo[8].trim()); } else {
				 * bean.setDailyAllowance("0.00"); }
				 */

				if (!tempInfo[8].equals("XXX")) {
					bean.setEmoluments(tempInfo[8].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmployeePF(tempInfo[9].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[10].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setPrincipal(tempInfo[11].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setInterest(tempInfo[12].trim());
				} else {
					bean.setInterest("0.00");
				}
				if (!tempInfo[0].equals("XXX")) {
					bean.setStation(tempInfo[0].trim());
				} else {
					bean.setStation("");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,ACTIVEFLAG)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "RAUSAP");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "RAUSAP");
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "RAUSAP");
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setString(14, "N");

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					pst.executeUpdate();
					pst.close();
				} else {
					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "'))  where region='RAUSAP' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' AND ACTIVEFLAG='N'";

					log.info("update emolumentss " + insertArrearCount);
					int count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importRausap0809Data leaving method");

	}

	public void importNER0809Data(String xlsData, String userName,
			String ipAddress) throws InvalidDataException{
		log.info("ImportDao :importNER0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String foundSalaryDate = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "", arrearFlag="";;
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		String retirementDate = "", dateofbirth = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("aims.resource.DbProperties");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			String transMonthYear = "";
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				//log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setFromDate(tempInfo[4].trim());
				} else {
					bean.setFromDate("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].replaceAll(",", "").trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				log.info("emoluments"+tempInfo[9].trim());
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.0");
				}
				log.info("emoluments"+bean.getEmoluments());
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.0");
				}

				DateFormat df = new SimpleDateFormat("dd-MMM-yy");
				if (bean.getFromDate() != "") {
					Date transdate = df.parse(bean.getFromDate());

					float cpfarrear = 0;
					if (transdate.after(new Date("31-Mar-98"))) {
						bean.setEmoluments(String.valueOf(Math.round(Float.parseFloat(bean.getEmployeePF()) * 100 / 12)));
					} else {
						bean.setEmoluments(String.valueOf(Math.round(Float.parseFloat(bean
								.getEmployeePF()) * 100 / 10)));
					}
				}
				
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.0");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.0");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.0");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setStation(tempInfo[14].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setRegion(tempInfo[15].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[16].equals("XXX")) {
					bean.setRemarks(tempInfo[16].trim());
				} else {
					bean.setRemarks("");
				}
				/*if (!tempInfo[17].equals("XXX")) {
					bean.setEmployeeTotal(tempInfo[17].trim());
				} else {
					bean.setEmployeeTotal("");
				}*/
			
				if (!tempInfo[17].equals("XXX")) {
					arrearFlag=tempInfo[17].trim();
				} else {
					arrearFlag="";
				}
				
				int countEmployeeInfo = 0;
				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						bean.getRegion());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(empserialnumber,cpfacno,airportCode, employeeno,employeeName,desegnation,region,remarks,MAPPINGFLAG) VALUES "
							+ "(?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, bean.getPfid());
					pst.setString(2, bean.getCpfAcNo());
					pst.setString(3, bean.getStation());
					pst.setString(4, bean.getEmpNumber());
					pst.setString(5, bean.getEmpName());
					pst.setString(6, bean.getDesegnation());
					// for southern region Data
					pst.setString(7, bean.getRegion().trim());
					pst.setString(8, "new records from"
							+ bean.getRegion().trim());
					pst.setString(9, "Y".trim());
					pst.executeUpdate();
					pst.close();
				} else {
					String sql = "update employee_info set empserialnumber='"
							+ bean.getPfid()
							+ "',MAPPINGFLAG='Y' where cpfacno='"
							+ bean.getCpfAcNo() + "' and region='"
							+ bean.getRegion() + "'";
					//st.executeUpdate(sql);
				}
				double pensioncontri = 0.00, pf = 0.00, calculatedPension = 0.00;
				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf,REMARKS,ARREARFLAG)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date('"
							+ bean.getFromDate()
							+ "','DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs.getString("REIREMENTDATE") != null) {
						retirementDate = rs.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs.getString("dateofbirth") != null) {
						dateofbirth = rs.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs.getString("days") != null) {
						days = rs.getString("days");
					} else {
						days = "0";
					}

					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}

					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					foundSalaryDate = this.checkFinanceDuplicate2(conn, bean
							.getFromDate(), bean.getCpfAcNo(), bean
							.getEmpNumber(), bean.getRegion(),bean.getStation(),bean.getPfid());
					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = Math
							.round(fdao.calclatedPF(bean.getFromDate(),
									retirementDate, dateofbirth, bean
											.getEmoluments(), bean
											.getWetherOption(), "", days,"1"));
					pf = Math.round(Double.parseDouble(bean.getEmployeePF()
							.toString())
							- calculatedPension);

				}
				foundSalaryDate = this.checkFinanceDuplicate2(conn, bean
						.getFromDate(), bean.getCpfAcNo(), bean
						.getEmpNumber(), bean.getRegion(),bean.getStation(),bean.getPfid());
				
				// inserting log
				transMonthYear = commonUtil.converDBToAppFormat(bean
						.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				FinancialReportDAO FDAO = new FinancialReportDAO();
				String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");

				String emoluments_log = "", emoluments_log_history = "";
				String oldemppfstatuary = "0.00", oldemoluments = "0.00";
				Date transdate1 = df.parse(bean.getFromDate());
				oldemoluments = this.getEmoluments(conn, bean.getFromDate(),
						bean.getCpfAcNo(), "", bean.getRegion());
				if(oldemoluments==""){
					oldemoluments="0.00";
				}
				if(oldemppfstatuary==""){
					oldemppfstatuary="0.00";	
				}
				if ((transdate1.after(new Date("31-Mar-98"))
						&& oldemoluments != "" && Float
						.parseFloat(oldemoluments) != 0.00)) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 12 / 100);
				} else if (oldemoluments != ""
						&& Float.parseFloat(oldemoluments) != 0.00) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 10 / 100);
				}
				String selectEmolumentsLog = "select count(*) as count from emoluments_log where cpfacno='"
						+ bean.getCpfAcNo().trim()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear
						+ "' and region='"
						+ bean.getRegion()
						+ "' ";
				rs = st.executeQuery(selectEmolumentsLog);
				float newemppfstatuary = Float.parseFloat(bean
						.getEmployeePF())
						+ Float.parseFloat(oldemppfstatuary);
				float newemoluments = Float
						.parseFloat(bean.getEmoluments())
						+ Float.parseFloat(oldemoluments);
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count == 0) {
						emoluments_log = "insert into emoluments_log(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
								+ oldemoluments
								+ "','"
								+ newemoluments
								+ "','"
								+ oldemppfstatuary
								+ "','"
								+ bean.getEmployeePF()
								+ "','"
								+ bean.getFromDate()
								+ "','"
								+ updatedDate
								+ "','"
								+ bean.getPfid()
								+ "','"
								+ bean.getCpfAcNo()
								+ "','"
								+ bean.getRegion()
								+ "','" + userName + "','" + ipAddress + "')";
					} else {
						emoluments_log = "update emoluments_log set oldemoluments='"
								+ oldemoluments
								+ "',newemoluments='"
								+ bean.getEmoluments()
								+ "',oldemppfstatuary='"
								+ oldemppfstatuary
								+ "',newemppfstatuary='"
								+ bean.getEmployeePF()
								+ "',monthyear='"
								+ bean.getFromDate()
								+ "',UPDATEDDATE='"
								+ updatedDate
								+ "',pensionno='"
								+ bean.getPfid()
								+ "',region='"
								+ bean.getRegion()
								+ "',username='"
								+ userName
								+ "',computername='"
								+ ipAddress
								+ "' where cpfacno='"
								+ bean.getCpfAcNo()
								+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear
								+ "' and region='"
								+ bean.getRegion() + "'";

					}
					emoluments_log_history = "insert into emoluments_log_history(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
							+ oldemoluments
							+ "','"
							+ bean.getEmoluments()
							+ "','"
							+ oldemppfstatuary
							+ "','"
							+ bean.getEmployeePF()
							+ "','"
							+ bean.getFromDate()
							+ "','"
							+ updatedDate
							+ "','"
							+ bean.getPfid()
							+ "','"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getRegion()
							+ "','"
							+ userName
							+ "','"
							+ ipAddress + "')";
					// st.executeUpdate(emoluments_log);
				// st.executeUpdate(emoluments_log_history);
				}
				String insertarrear="";
				insertarrear = "insert into employee_pension_arrear(CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,AIRPORTCODE,REGION,REMARKS,PENSIONNO)values('"
					+bean.getCpfAcNo()+ "','"
					+bean.getEmpNumber()+"','"
					+bean.getEmpName()+"','"
					+bean.getFromDate()+"','"
					+bean.getEmoluments()+"','"
					+bean.getStation()+"','"
					+bean.getRegion()+"','"
					+bean.getRemarks()+"','"
					+bean.getPfid()+"')";
				//log.info("insertarrear"+insertarrear);
			     st.executeUpdate(insertarrear);
				String updateemoluments = "";
				// code commented on 20-04-2010 , foundSalaryDate.equals("") &&
				if (foundSalaryDate.equals("") && bean.getFromDate() != "") {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmployeePF());
					pst.setString(4, bean.getStation());
					pst.setString(5, bean.getFromDate());
					pst.setString(6, bean.getEmpName());
					pst.setString(7, bean.getEmpNumber());
					pst.setString(8, bean.getDesegnation());
					pst.setString(9, bean.getRegion());
					pst.setString(10, bean.getPfid());
					pst.setString(11, bean.getEmployeeVPF());
					pst.setString(12, bean.getPrincipal());
					pst.setString(13, bean.getInterest());
					pst.setDouble(14, calculatedPension);
					//pst.setString(14, calculatedPension);
					pst.setDouble(15, pf);
					pst.setString(16, bean.getRemarks());
					//pst.setString(17, bean.getFhFlag().trim());
					pst.setString(17, arrearFlag.trim());
					pst.executeUpdate();
					pst.close();
					log.info("inside insert");
					
				} else if (bean.getFromDate() != "" && bean.getRegion().equals("CHQIAD")) {
					log.info(bean.getFromDate().trim());
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(bean
							.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				
					/*updateemoluments ="update employee_pension_validate set emoluments='" +
					  bean.getEmoluments() + "' , EMPPFSTATUARY='" +
					  bean.getEmployeePF().trim() +
					  "' ,empvpf='"+bean.getEmployeeVPF()+"',EMPADVRECPRINCIPAL='"+bean.getPrincipal()
					  +"',EMPADVRECINTEREST='"+bean.getInterest()
					  +"',EMPFLAG='Y',pensionno='"
					  +bean.getPfid()+"',remarks=REMARKS||'"+bean.getRemarks()+"' where region='" +
					  bean.getRegion().trim() + "' and cpfaccno='" +
					  bean.getCpfAcNo().trim() +
					  "' and airportcode='"+bean.getStation()+"' and to_char(monthyear,'dd-Mon-yyyy') like '%" +
					  transMonthYear + "' and EMPFLAG='Y'";*/
								
					 updateemoluments = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')),remarks=remarks||'"
							+ bean.getRemarks()
							+ "',EMPFLAG='Y',pensionno='"
							+ bean.getPfid()
							+ "',pf= to_char(TO_NUMBER(nvl(pf,0)+'"
						    + pf
						    + "')),PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'"
							+ calculatedPension
							+ "')),ARREARFLAG='"+arrearFlag+"' where region='"
							+ bean.getRegion().trim()
							+ "' and pensionno='"
							+ bean.getPfid().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' and empflag='Y' and airportcode='"+bean.getStation()+"'"; 

					log.info("update query " + updateemoluments);
					int count = st.executeUpdate(updateemoluments);
				}else if (bean.getFromDate() != ""&&!bean.getRegion().equals("CHQIAD")){
					log.info(bean.getFromDate().trim());
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(bean
							.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					updateemoluments = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
						+ bean.getEmoluments()
						+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
						+ bean.getEmployeePF().trim()
						+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
						+ bean.getPrincipal().trim()
						+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
						+ bean.getInterest().trim()
						+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
						+ bean.getEmployeeVPF().trim()
						+ "')),remarks=remarks||'"
							+ bean.getRemarks()
							+ "',EMPFLAG='Y',pensionno='"
						+ bean.getPfid()
						+ "',pf= to_char(TO_NUMBER(nvl(pf,0)+'"
						+ pf
						+ "')),PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'"
						+ calculatedPension
						+ "')),ARREARFLAG='"+arrearFlag+"' where region='"
						+ bean.getRegion().trim()
						+ "' and pensionno='"
						+ bean.getPfid().trim()
						+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "' and empflag='Y' ";
					/*	 updateemoluments ="update employee_pension_validate set emoluments='" +
					  bean.getEmoluments() + "' , EMPPFSTATUARY='" +
					  bean.getEmployeePF().trim()
					  +"',empvpf='"+bean.getEmployeeVPF()
					  +"',EMPADVRECPRINCIPAL='"+bean.getPrincipal()
					  +"',EMPADVRECINTEREST='"+bean.getInterest()
					  +"',EMPFLAG='Y',pensionno='"
					  +bean.getPfid()+"',remarks=REMARKS||'"+bean.getRemarks()+"' where region='" +
					  bean.getRegion().trim() + "' and cpfaccno='" +
					  bean.getCpfAcNo().trim() +
					  "' and to_char(monthyear,'dd-Mon-yyyy') like '%" +
					  transMonthYear + "' and EMPFLAG='Y'"; */
					log.info("update query " + updateemoluments);
					int count = st.executeUpdate(updateemoluments);
				}
			}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
				throw new InvalidDataException(e.getMessage());

		
		} catch (Exception e) {
			log.printStackTrace(e);
		}finally {
			try {
				//st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

		
			}
		}
		log.info("ImportDao :importNER0809Data leaving method");

	}

	/*public void importNER0809Data(String xlsData, String userName,
			String ipAddress) {
		log.info("ImportDao :importNER0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		String foundSalaryDate = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		String retirementDate = "", dateofbirth = "";
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			String transMonthYear = "";
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				//log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setFromDate(tempInfo[4].trim());
				} else {
					bean.setFromDate("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].replaceAll(",", "").trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.0");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.0");
				}

				DateFormat df = new SimpleDateFormat("dd-MMM-yy");
				if (bean.getFromDate() != "") {
					Date transdate = df.parse(bean.getFromDate());

					float cpfarrear = 0;
					if (transdate.after(new Date("31-Mar-98"))) {
						bean.setEmoluments(String.valueOf(Math.round(Float.parseFloat(bean.getEmployeePF()) * 100 / 12)));
					} else {
						bean.setEmoluments(String.valueOf(Math.round(Float.parseFloat(bean
								.getEmployeePF()) * 100 / 10)));
					}
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.0");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.0");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.0");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setStation(tempInfo[14].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setRegion(tempInfo[15].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[16].equals("XXX")) {
					bean.setRemarks(tempInfo[16].trim());
				} else {
					bean.setRemarks("");
				}
				int countEmployeeInfo = 0;
				countEmployeeInfo = commonDAO.checkEmployeeInfo(conn, bean
						.getEmpNumber(), bean.getCpfAcNo(), bean.getEmpName(),
						bean.getRegion());
				if (countEmployeeInfo == 0) {
					String sql = "insert into employee_info(empserialnumber,cpfacno,airportCode, employeeno,employeeName,desegnation,region,remarks,MAPPINGFLAG) VALUES "
							+ "(?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, bean.getPfid());
					pst.setString(2, bean.getCpfAcNo());
					pst.setString(3, bean.getStation());
					pst.setString(4, bean.getEmpNumber());
					pst.setString(5, bean.getEmpName());
					pst.setString(6, bean.getDesegnation());
					// for southern region Data
					pst.setString(7, bean.getRegion().trim());
					pst.setString(8, "new records from"
							+ bean.getRegion().trim());
					pst.setString(9, "Y".trim());
					pst.executeUpdate();
					pst.close();
				} else {
					String sql = "update employee_info set empserialnumber='"
							+ bean.getPfid()
							+ "',MAPPINGFLAG='Y' where cpfacno='"
							+ bean.getCpfAcNo() + "' and region='"
							+ bean.getRegion() + "'";
					//st.executeUpdate(sql);
				}
				double pensioncontri = 0.00, pf = 0.00, calculatedPension = 0.00;
				//log.info(" Principle " + bean.getPrincipal() + "interest  "
				//		+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf,REMARKS)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date('"
							+ bean.getFromDate()
							+ "','DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs.getString("REIREMENTDATE") != null) {
						retirementDate = rs.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs.getString("dateofbirth") != null) {
						dateofbirth = rs.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs.getString("days") != null) {
						days = rs.getString("days");
					} else {
						days = "0";
					}

					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}

					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					foundSalaryDate = this.checkFinanceDuplicate2(conn, bean
							.getFromDate(), bean.getCpfAcNo(), bean
							.getEmpNumber(), bean.getRegion(),bean.getStation());
					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = Math
							.round(fdao.calclatedPF(bean.getFromDate(),
									retirementDate, dateofbirth, bean
											.getEmoluments(), bean
											.getWetherOption(), "", days));
					pf = Math.round(Double.parseDouble(bean.getEmployeePF()
							.toString())
							- calculatedPension);

				}
				foundSalaryDate = this.checkFinanceDuplicate2(conn, bean
						.getFromDate(), bean.getCpfAcNo(), bean
						.getEmpNumber(), bean.getRegion(),bean.getStation());

				// inserting log
				transMonthYear = commonUtil.converDBToAppFormat(bean
						.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				FinancialReportDAO FDAO = new FinancialReportDAO();
				String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");

				String emoluments_log = "", emoluments_log_history = "";
				String oldemppfstatuary = "", oldemoluments = "";
				Date transdate1 = df.parse(bean.getFromDate());
				oldemoluments = this.getEmoluments(conn, bean.getFromDate(),
						bean.getCpfAcNo(), "", bean.getRegion());
				if ((transdate1.after(new Date("31-Mar-98"))
						&& oldemoluments != "" && Float
						.parseFloat(oldemoluments) != 0.00)) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 12 / 100);
				} else if (oldemoluments != ""
						&& Float.parseFloat(oldemoluments) != 0.00) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 10 / 100);
				}
				String selectEmolumentsLog = "select count(*) as count from emoluments_log where cpfacno='"
						+ bean.getCpfAcNo().trim()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear
						+ "' and region='"
						+ bean.getRegion()
						+ "' ";
				rs = st.executeQuery(selectEmolumentsLog);
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count == 0) {
						emoluments_log = "insert into emoluments_log(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
								+ oldemoluments
								+ "','"
								+ bean.getEmoluments()
								+ "','"
								+ oldemppfstatuary
								+ "','"
								+ bean.getEmployeePF()
								+ "','"
								+ bean.getFromDate()
								+ "','"
								+ updatedDate
								+ "','"
								+ bean.getPfid()
								+ "','"
								+ bean.getCpfAcNo()
								+ "','"
								+ bean.getRegion()
								+ "','" + userName + "','" + ipAddress + "')";
					} else {
						emoluments_log = "update emoluments_log set oldemoluments='"
								+ oldemoluments
								+ "',newemoluments='"
								+ bean.getEmoluments()
								+ "',oldemppfstatuary='"
								+ oldemppfstatuary
								+ "',newemppfstatuary='"
								+ bean.getEmployeePF()
								+ "',monthyear='"
								+ bean.getFromDate()
								+ "',UPDATEDDATE='"
								+ updatedDate
								+ "',pensionno='"
								+ bean.getPfid()
								+ "',region='"
								+ bean.getRegion()
								+ "',username='"
								+ userName
								+ "',computername='"
								+ ipAddress
								+ "' where cpfacno='"
								+ bean.getCpfAcNo()
								+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear
								+ "' and region='"
								+ bean.getRegion() + "'";

					}
					emoluments_log_history = "insert into emoluments_log_history(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
							+ oldemoluments
							+ "','"
							+ bean.getEmoluments()
							+ "','"
							+ oldemppfstatuary
							+ "','"
							+ bean.getEmployeePF()
							+ "','"
							+ bean.getFromDate()
							+ "','"
							+ updatedDate
							+ "','"
							+ bean.getPfid()
							+ "','"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getRegion()
							+ "','"
							+ userName
							+ "','"
							+ ipAddress + "')";
					 st.executeUpdate(emoluments_log);
					 st.executeUpdate(emoluments_log_history);
				}
				String updateemoluments = "";
				// code commented on 20-04-2010 , foundSalaryDate.equals("") &&
				if (foundSalaryDate.equals("") && bean.getFromDate() != "") {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo());
					pst.setString(2, bean.getEmoluments());
					pst.setString(3, bean.getEmployeePF());
					pst.setString(4, bean.getStation());
					pst.setString(5, bean.getFromDate());
					pst.setString(6, bean.getEmpName());
					pst.setString(7, bean.getEmpNumber());
					pst.setString(8, bean.getDesegnation());
					pst.setString(9, bean.getRegion());
					pst.setString(10, bean.getPfid());
					pst.setString(11, bean.getEmployeeVPF());
					pst.setString(12, bean.getPrincipal());
					pst.setString(13, bean.getInterest());
					pst.setDouble(14, calculatedPension);
					pst.setDouble(15, pf);
					pst.setString(16, bean.getRemarks());
					pst.executeUpdate();
					pst.close();
					log.info("inside insert");
					
				} else if (bean.getFromDate() != "" && bean.getRegion().equals("CHQIAD")) {
					log.info(bean.getFromDate().trim());
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(bean
							.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
				
					updateemoluments ="update employee_pension_validate set emoluments='" +
					  bean.getEmoluments() + "' , EMPPFSTATUARY='" +
					  bean.getEmployeePF().trim() +
					  "' ,empvpf='"+bean.getEmployeeVPF()+"',EMPADVRECPRINCIPAL='"+bean.getPrincipal()
					  +"',EMPADVRECINTEREST='"+bean.getInterest()
					  +"',EMPFLAG='Y',pensionno='"
					  +bean.getPfid()+"',remarks=REMARKS||'"+bean.getRemarks()+"' where region='" +
					  bean.getRegion().trim() + "' and cpfaccno='" +
					  bean.getCpfAcNo().trim() +
					  "' and airportcode='"+bean.getStation()+"' and to_char(monthyear,'dd-Mon-yyyy') like '%" +
					  transMonthYear + "' and EMPFLAG='Y'";  
								
					  updateemoluments = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')),EMPFLAG='Y',pensionno='"
							+ bean.getPfid()
							+ "',pf= to_char(TO_NUMBER(nvl(pf,0)+'"
							+ pf
							+ "')),PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'"
							+ calculatedPension
							+ "'))  where region='"
							+ bean.getRegion().trim()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' and empflag='Y' and airportcode='"+bean.getStation()+"'";

					log.info("update query " + updateemoluments);
					int count = st.executeUpdate(updateemoluments);
				}else if (bean.getFromDate() != ""&&!bean.getRegion().equals("CHQIAD")){
					log.info(bean.getFromDate().trim());
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(bean
							.getFromDate().trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					 updateemoluments = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
						+ bean.getEmoluments()
						+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
						+ bean.getEmployeePF().trim()
						+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
						+ bean.getPrincipal().trim()
						+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
						+ bean.getInterest().trim()
						+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
						+ bean.getEmployeeVPF().trim()
						+ "')),EMPFLAG='Y',pensionno='"
						+ bean.getPfid()
						+ "',pf= to_char(TO_NUMBER(nvl(pf,0)+'"
						+ pf
						+ "')),PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'"
						+ calculatedPension
						+ "'))  where region='"
						+ bean.getRegion().trim()
						+ "' and cpfaccno='"
						+ bean.getCpfAcNo().trim()
						+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear + "' and empflag='Y' ";
						updateemoluments ="update employee_pension_validate set emoluments='" +
					  bean.getEmoluments() + "' , EMPPFSTATUARY='" +
					  bean.getEmployeePF().trim()
					  +"',empvpf='"+bean.getEmployeeVPF()
					  +"',EMPADVRECPRINCIPAL='"+bean.getPrincipal()
					  +"',EMPADVRECINTEREST='"+bean.getInterest()
					  +"',EMPFLAG='Y',pensionno='"
					  +bean.getPfid()+"',remarks=REMARKS||'"+bean.getRemarks()+"' where region='" +
					  bean.getRegion().trim() + "' and cpfaccno='" +
					  bean.getCpfAcNo().trim() +
					  "' and to_char(monthyear,'dd-Mon-yyyy') like '%" +
					  transMonthYear + "' and EMPFLAG='Y'"; 
					log.info("update query " + updateemoluments);
					int count = st.executeUpdate(updateemoluments);
				}
			
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importNER0809Data leaving method");

	}
*/

	public void importOfficeComplexDparrears(String xlsData, String userName,
			String ipAddress) throws InvalidDataException {
		int total = 0;
		log.info("ImportDao :importOfficeComplexDparrears() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", empName = "";

		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setFromDate(tempInfo[4].trim());
				} else {
					bean.setFromDate("");
				}

				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[7].equals("XXX")) {
					bean.setEmployeePF(tempInfo[7].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				bean.setEmoluments(String.valueOf(Float.parseFloat(bean
						.getEmployeePF()) * 100 / 12));

				if (!tempInfo[9].equals("XXX")) {
					bean.setPrincipal(tempInfo[9].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setInterest(tempInfo[10].trim());
				} else {
					bean.setInterest("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setStation(tempInfo[11].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setRegion(tempInfo[12].trim());
				} else {
					bean.setRegion("");
				}
				String transMonthYear = commonUtil.converDBToAppFormat(bean
						.getFromDate(), "dd-MMM-yyyy", "MMM-yyyy");
				String emoluments_log = "", emoluments_log_history = "";
				String updatedDate = commonUtil.getCurrentDate("dd-MMM-yyyy");
				String selectEmolumentsLog = "select count(*) as count from emoluments_log where cpfacno='"
						+ bean.getCpfAcNo().trim()
						+ "' and  to_char(monthyear,'dd-Mon-yyyy') like '%"
						+ transMonthYear
						+ "' and region='"
						+ bean.getRegion()
						+ "' ";
				rs = st.executeQuery(selectEmolumentsLog);
				String oldemppfstatuary = "0.00", oldemoluments = "0.00";
				oldemoluments = this.getEmolumentsBesedonPFID(conn, bean
						.getFromDate(), bean.getCpfAcNo(), "",
						bean.getRegion(), bean.getPfid());
				DateFormat df = new SimpleDateFormat("dd-MMM-yy");
				Date transdate = df.parse(bean.getFromDate());
				if ((transdate.after(new Date("31-Mar-98"))
						&& oldemoluments != "" && Float
						.parseFloat(oldemoluments) != 0.00)) {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 12 / 100);
				} else if (oldemoluments != "") {
					oldemppfstatuary = String.valueOf(Float
							.parseFloat(oldemoluments) * 10 / 100);
				}
				while (rs.next()) {
					int count = rs.getInt(1);
					if (oldemppfstatuary == "" || oldemppfstatuary == "0.00") {
						oldemppfstatuary = "0.00";
						oldemoluments = "0.00";
					}
					float newemppfstatuary = Float.parseFloat(bean
							.getEmployeePF())
							+ Float.parseFloat(oldemppfstatuary);
					float newemoluments = Float
							.parseFloat(bean.getEmoluments())
							+ Float.parseFloat(oldemoluments);
					if (count == 0) {
						emoluments_log = "insert into emoluments_log(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
								+ oldemoluments
								+ "','"
								+ newemoluments
								+ "','"
								+ oldemppfstatuary
								+ "','"
								+ newemppfstatuary
								+ "','"
								+ bean.getFromDate()
								+ "','"
								+ updatedDate
								+ "','"
								+ bean.getPfid()
								+ "','"
								+ bean.getCpfAcNo()
								+ "','"
								+ bean.getRegion()
								+ "','" + userName + "','" + ipAddress + "')";
					} else {
						emoluments_log = "update emoluments_log set oldemoluments='"
								+ oldemoluments
								+ "',newemoluments='"
								+ newemoluments
								+ "',oldemppfstatuary='"
								+ oldemppfstatuary
								+ "',newemppfstatuary='"
								+ newemppfstatuary
								+ "',monthyear='"
								+ bean.getFromDate()
								+ "',UPDATEDDATE='"
								+ updatedDate
								+ "',pensionno='"
								+ bean.getPfid()
								+ "',region='"
								+ bean.getRegion()
								+ "',username='"
								+ userName
								+ "',computername='"
								+ ipAddress
								+ "' where cpfacno='"
								+ bean.getCpfAcNo()
								+ "' and  to_char(monthyear,'dd-Mon-yy') like '%"
								+ transMonthYear
								+ "' and region='"
								+ bean.getRegion() + "'";

					}

					emoluments_log_history = "insert into emoluments_log_history(oldemoluments,newemoluments,oldemppfstatuary,newemppfstatuary,monthyear,UPDATEDDATE,pensionno,cpfacno,region,username,computername)values('"
							+ oldemoluments
							+ "','"
							+ newemoluments
							+ "','"
							+ oldemppfstatuary
							+ "','"
							+ newemppfstatuary
							+ "','"
							+ bean.getFromDate()
							+ "','"
							+ updatedDate
							+ "','"
							+ bean.getPfid()
							+ "','"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getRegion()
							+ "','" + userName + "','" + ipAddress + "')";
					st.executeUpdate(emoluments_log);
					st.executeUpdate(emoluments_log_history);
				}

				String insertArrear = "insert into EMPLOYEE_PENSION_ARREAR (CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ARREARDATE,ARREARAMT,REGION,AIRPORTCODE,remarks) values('"
						+ bean.getCpfAcNo()
						+ "','"
						+ bean.getEmpNumber()
						+ "','"
						+ bean.getEmpName()
						+ "','"
						+ bean.getFromDate()
						+ "','"
						+ bean.getEmoluments()
						+ "','"
						+ bean.getStation()
						+ "','"
						+ bean.getRegion()
						+ "','Supplimentory_AAI_GL')";
				String updateEmoluments = "";
				transMonthYear = commonUtil.converDBToAppFormat(bean
						.getFromDate(), "dd-MMM-yyyy", "MMM-yyyy");
				String foundSalaryDate = this.getEmolumentsBesedonPFID(conn,
						bean.getFromDate(), bean.getCpfAcNo(), bean
								.getEmpNumber(), bean.getRegion(), bean
								.getPfid());

				if (foundSalaryDate != "" && foundSalaryDate != "0.00") {
					updateEmoluments = "update employee_pension_validate set EMOLUMENTS=EMOLUMENTS+"
							+ bean.getEmoluments()
							+ ", EMPPFSTATUARY=EMPPFSTATUARY+"
							+ bean.getEmployeePF()
							+ ",EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "'))  where pensionno='"
							+ bean.getPfid()
							+ "' and to_char(monthyear,'Mon-yyyy')='"
							+ transMonthYear + "'";
				} else {
					updateEmoluments = "insert into employee_pension_validate(cpfaccno,pensionno,employeename,emoluments,EMPPFSTATUARY,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,region,monthyear,empvpf)"
							+ "values('"
							+ bean.getCpfAcNo()
							+ "','"
							+ bean.getPfid()
							+ "','"
							+ bean.getEmpName()
							+ "','"
							+ bean.getEmoluments()
							+ "','"
							+ bean.getEmployeePF()
							+ "','"
							+ bean.getPrincipal()
							+ "','"
							+ bean.getInterest()
							+ "','"
							+ bean.getRegion()
							+ "','"
							+ bean.getFromDate()
							+ "','"
							+ bean.getEmployeeVPF() + "')";
				}
				log.info("Update arrears " + updateEmoluments);

				st.addBatch(insertArrear);
				st.addBatch(updateEmoluments);
				int count[] = st.executeBatch();
				log.info("count is" + count.length);

			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("ImportDao :importOfficeComplexDparrears leaving method");

	}

	public String importMonthlyTrData(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {

		log.info("ImportDao :importMonthlyTrData() entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", dateofbirth = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		String foundSalaryDate = "", retirementDate = "";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st1 = conn.createStatement();
			String convertedMonthYear = commonUtil.getMonthYear(month, year);
			String lastactiveMonth = "01" + "-" + convertedMonthYear;
			String monthyear = convertedMonthYear;
			log.info("lastactiveMonth" + lastactiveMonth);
			String importMonthStatus = "select FILENAME,IMPORTSTATUS,to_char(MONTHYEAR,'dd-Mon-yyyy')as monthyear FROM payment_recov_mnth_upload_log where  to_char(MONTHYEAR,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			rs = st1.executeQuery(importMonthStatus);
			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported for Salary Month "
							+ month + "-" + year);
				}
			}
			double pensioncontri = 0.00, pf = 0.00;
			String srno = "";
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
					+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf,FINYEAR )"
					+ " values(?,?,?,?,to_char(add_months(?,1),'dd-Mon-yyyy'),?,?,?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(finaceInsert);

			for (int i = 1; i < xlsDataList.size(); i++) {
				double calculatedPension = 0.00;
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				bean.setRegion(region);
				if (!tempInfo[0].equals("XXX")) {
					srno = tempInfo[0];
				} else {
					srno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setEmpName(tempInfo[4].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesegnation(tempInfo[5].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setFhName(tempInfo[6].trim());
				} else {
					bean.setFhName("");
				}
				String monthyear2 = "";
				if (!tempInfo[8].equals("XXX") && !srno.equals("")) {

					log.info(tempInfo[8]);
					String convertedDate = commonUtil
							.convertDateFormat(tempInfo[8].trim());
					log.info("converted date is " + convertedDate);
					int index = tempInfo[8].trim().indexOf("-");
					String count[] = tempInfo[8].split("-");
					log.info(" countlength " + count.length);
					if (index == -1 || count.length != 3) {
						throw new InvalidDataException(
								"PFID "
										+ bean.getPfid().trim()
										+ " doesn't have valide date format(i.e dd-Mon-yyyy) in Column I of the Uploaded sheet ");
					}
					String sheetDateMonthYear = tempInfo[8];
					String sheetDateMonthyear[] = tempInfo[8].split("-");
					// String sheetDateMonthyear[] = tempInfo[8].split("-");
					String sheetMonthYear = sheetDateMonthyear[1] + "-"
							+ sheetDateMonthyear[2];
					if (!monthyear.equals(sheetMonthYear)) {
						String monthyear1[] = tempInfo[8].split("-");
						String month1 = monthyear1[1];
						String year1 = monthyear1[2];
						throw new InvalidDataException(
								"Please make sure that the Salary Month Selected above (i.e "
										+ convertedMonthYear
										+ " is matching with month given in the excel sheet at Column I against PFID "
										+ tempInfo[1].trim() + " ( i.e "
										+ sheetMonthYear + ")");
					}
				}

				if (!tempInfo[8].equals("XXX")
						&& !bean.getPfid().trim().equals("")) {
					String monthyearQuery = "SELECT  to_char(add_months('"
							+ tempInfo[8].trim()
							+ "',1),'dd-Mon-yyyy') as monthyear from dual	";
			//		String monthyearQuery="SELECT  to_char(add_months(replace( '"+tempInfo[8].trim()+"', SUBSTR('"+tempInfo[8].trim()+"',0,2), '01'),1),'dd-Mon-yyyy') as monthyear from dual";
            	log.info(monthyearQuery);

					rs = st1.executeQuery(monthyearQuery);
					if (rs.next()) {
						monthyear2 = rs.getString(1);
					}
				}
				log.info("monthyear 2 " + monthyear2);

				if (!tempInfo[7].equals("XXX")) {
					bean.setDateofBirth(tempInfo[7].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[8].equals("XXX")) {
					monthYear = tempInfo[8].trim();
				} else {
					monthYear = "";
				}

				String finyear = "";
				Date transdate = null;
				log.info("monthyear" + monthyear);
				if (!tempInfo[8].equals("XXX")
						&& !bean.getPfid().trim().equals("")) {
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					transdate = df.parse(monthYear);
					/*if (transdate.after(new Date("31-Mar-2009"))
							&& transdate.before(new Date("1-Apr-2010"))) {
						finyear = "2009-10";
					} else if (transdate.after(new Date("31-Mar-2010"))
							&& transdate.before(new Date("1-Apr-2011"))) {
						finyear = "2010-11";
					}*/
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].replaceAll(",", "").trim());
				} else {
					bean.setEmoluments("0.00");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].replaceAll(",", "").trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				/*log.info("pf /12% " +Math.round(Double.parseDouble(bean.getEmployeePF())*100/12)  +"emoluments "+bean.getEmoluments());
				if(Double.parseDouble(bean.getEmoluments())!=Math.round(Double.parseDouble(bean.getEmployeePF())*100/12)){
					throw new InvalidDataException("(EPF/12%) Is Not Equal to Emoluments , for the PFID "+bean.getPfid().trim()+" and EmployeeName "+bean.getEmpName());	
				}*/
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].replaceAll(",", "")
									.trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].replaceAll(",", "").trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].replaceAll(",", "").trim());
				} else {
					bean.setInterest("0.00");
				}

				if (!tempInfo[18].equals("XXX")) {
					bean.setStation(tempInfo[18].trim());
				} else {
					bean.setStation("");
				}
				if (airportcode != null && !bean.getPfid().trim().equals("")
						&& region.equals("CHQIAD")) {
					log.info("sheet airportcode "
							+ bean.getStation().replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", "") + "srno " + srno);
					if (!bean.getStation().replaceAll(" ", "").equals(
							airportcode.replaceAll(" ", ""))
							&& !srno.equals("")) {
						throw new InvalidDataException(
								"Please make sure that the Seleted Airportcode above  (i.e "
										+ airportcode
										+ ") and is matching with AirportCode given in the excel sheet at Column 18. i.e"
										+ bean.getStation());
					}
				}
				log.info("cpfaccno " + bean.getCpfAcNo() + " " + bean.getPfid()
						+ "bean.getPfid().trim().equals()"
						+ bean.getPfid().trim().equals("")
						+ "bean.getCpfAcNo().trim().equals()"
						+ bean.getCpfAcNo().trim().equals(""));
				if(!srno.equals("")&& bean.getPfid().trim().equals("")){
					throw new InvalidDataException("PFID "
							+ bean.getPfid().trim() + " doesn't Exist"
							+ " for  Employee " + bean.getEmpName()
							+ ". PFID is Mandatory for All The Employees");	
				}
				if (!bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date('"
							+ tempInfo[8]
							+ "','DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs.getString("REIREMENTDATE") != null) {
						retirementDate = rs.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs.getString("dateofbirth") != null) {
						dateofbirth = rs.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs.getString("days") != null) {
						days = rs.getString("days");
					} else {
						days = "0";
					}

					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}

					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					foundSalaryDate = this.checkFinanceDuplicate1(conn,
							monthyear2.trim(), bean.getCpfAcNo(), bean
									.getEmpNumber(), bean.getRegion(), bean
									.getPfid(), airportcode, "");
					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = fdao.calclatedPF(monthYear,
							retirementDate, dateofbirth, bean.getEmoluments(),
							bean.getWetherOption(), "", days,"1");
					
					// pensioncontri = fDao.pensionCalculation(monthYear,
					// bean.getEmoluments(), bean.getWetherOption(),
					// bean.getRegion());
					// log.info("pensioncontri... " + pensioncontri);
					// log.info("pf " + bean.getEmployeePF().toString());
					// pf = Double.parseDouble(bean.getEmployeePF().toString())-
					// pensioncontri;
					pf = Double.parseDouble(bean.getEmployeePF().toString())
							- calculatedPension;

				}
				// this cofe snippet is removed from if condition
				// foundSalaryDate.equals("") &&
				if (!bean.getPfid().equals("") && !srno.equals("")) {
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setDouble(14, calculatedPension);
					pst.setDouble(15, pf);
					pst.setString(16, finyear);
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					// pst.executeUpdate();
					pst.addBatch();
					// pst.close();
					addflag = true;
				} else if (!bean.getPfid().equals("") && !srno.equals("")) {
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthyear2.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "'))  where region='"
							+ bean.getRegion().trim()
							+ "' and airportcode='"
							+ bean.getStation()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";
					st.addBatch(insertArrearCount);

					updateflag = true;
				}
				// }
			}

			if (addflag) {
				addBatchcount = pst.executeBatch();
				log.info("Inserted Records Count is " + addBatchcount.length);
			}
			if (updateflag) {
				int updatedBatchCount[] = st.executeBatch();
				log
						.info("Updated Records count is "
								+ updatedBatchCount.length);
			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			st.executeUpdate(importStausupdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :importMonthlyTrData leaving method");
		message = String.valueOf(addBatchcount.length);
		return message;
	}

	public String cpfRecievedFromOtherOrg(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {

		log.info("ImportDao :cpfRecievedFromOtherOrg() entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		String foundSalaryDate = "";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
			String lastactiveMonth = 1 + "-" + month + "-" + year;
			String monthyear = month + "-" + year;
			log.info(lastactiveMonth + "lastactiveMonth");
			String importMonthStatus = "select FILENAME,IMPORTSTATUS FROM payment_recov_mnth_upload_log where  to_char(monthyear,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			rs = st1.executeQuery(importMonthStatus);
			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported");
				}
			}
			double pensioncontri = 0.00, pf = 0.00;
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
					+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,recfromotherorg)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(finaceInsert);

			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				bean.setRegion(region);

				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setEmpName(tempInfo[4].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesegnation(tempInfo[5].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setFhName(tempInfo[6].trim());
				} else {
					bean.setFhName("");
				}

				if (!tempInfo[8].equals("XXX")
						&& !bean.getPfid().trim().equals("")) {
					log.info(tempInfo[8]);
					String convertedDate = commonUtil
							.convertDateFormat(tempInfo[8].trim());
					log.info("converted date is " + convertedDate);
					int index = tempInfo[8].trim().indexOf("-");
					String count[] = tempInfo[8].split("-");
					if (index == -1 && count.length != 3) {
						throw new InvalidDataException(
								"PFID "
										+ bean.getPfid().trim()
										+ " doesn't have valide date format(i.e dd-Mon-yyyy) in Column I of the Uploaded sheet");
					}
					String sheetDateMonthYear = tempInfo[8];
					String sheetDateMonthyear[] = tempInfo[8].split("-");
					String sheetMonthYear = sheetDateMonthyear[1] + "-"
							+ sheetDateMonthyear[2];
					log.info("sheetMonthYear " + sheetMonthYear);
					if (!monthyear.equals(sheetMonthYear)) {
						String monthyear1[] = lastactiveMonth.split("-");
						String month1 = monthyear1[1];
						String year1 = monthyear1[2];
						throw new InvalidDataException("PFID "
								+ tempInfo[1].trim() + " doesn't have Month "
								+ month1 + "-" + year1);
					}
				}

				if (!tempInfo[7].equals("XXX")) {
					bean.setDateofBirth(tempInfo[7].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[8].equals("XXX")) {
					monthYear = tempInfo[8].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmoluments(tempInfo[10].replaceAll(",", "").trim());
				} else {
					bean.setEmoluments("0.00");
				}

				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeePF(tempInfo[11].replaceAll(",", "").trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean
							.setEmployeeVPF(tempInfo[12].replaceAll(",", "")
									.trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setPrincipal(tempInfo[13].replaceAll(",", "").trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setInterest(tempInfo[14].replaceAll(",", "").trim());
				} else {
					bean.setInterest("0.00");
				}

				if (!tempInfo[18].equals("XXX")) {
					bean.setStation(tempInfo[18].trim());
				} else {
					bean.setStation("");
				}
				if (airportcode != null && !bean.getPfid().trim().equals("")) {
					log.info("sheet airportcode "
							+ bean.getStation().replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", ""));
					if (!bean.getStation().replaceAll(" ", "").equals(
							airportcode.replaceAll(" ", ""))) {
						throw new InvalidDataException(
								"Seleted Airportcode and upload sheet Airportcode are not Equal");
					}
				}
				log.info("cpfaccno " + bean.getCpfAcNo() + " " + bean.getPfid()
						+ "bean.getPfid().trim().equals()"
						+ bean.getPfid().trim().equals("")
						+ "bean.getCpfAcNo().trim().equals()"
						+ bean.getCpfAcNo().trim().equals(""));
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for Cpfaccno " + bean.getCpfAcNo());
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}
					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					foundSalaryDate = this.checkFinanceDuplicate(conn,
							monthYear.trim(), bean.getCpfAcNo(), bean
									.getEmpNumber(), bean.getRegion());
					if (!tempInfo[17].equals("XXX")) {
						pensioncontri = Double.parseDouble(tempInfo[17].trim());
					}

				}

				if (foundSalaryDate.equals("") && !bean.getCpfAcNo().equals("")) {
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setDouble(14, pensioncontri);
					pst.setString(15, "Y");
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					// pst.executeUpdate();
					pst.addBatch();
					// pst.close();
					addflag = true;
				} else if (!bean.getCpfAcNo().equals("")) {
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "'))  where region='"
							+ bean.getRegion().trim()
							+ "' and airportcode='"
							+ bean.getStation()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";
					st.addBatch(insertArrearCount);

					updateflag = true;
				}
				// }
			}

			if (addflag) {
				addBatchcount = pst.executeBatch();
				log.info("Inserted Records Count is " + addBatchcount.length);
			}
			if (updateflag) {
				int updatedBatchCount[] = st.executeBatch();
				log
						.info("Updated Records count is "
								+ updatedBatchCount.length);
			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			st.executeUpdate(importStausupdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :cpfRecievedFromOtherOrg leaving method");
		message = String.valueOf(addBatchcount.length);
		return message;
	}

	public void importOpeningBalance(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {
		int total = 0;
		log.info("ImportDao :importOpeningBalance() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", empName = "", designation = "", station = "", insertOB = "";

		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");

			conn = commonDB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st1 = conn.createStatement();
			// String lastactiveMonth = 1 + "-" + month + "-" + year;

			String convertedMonthYear = commonUtil.getMonthYear(month, year);
			String lastactiveMonth = "01" + "-" + convertedMonthYear;
			log.info("lastactiveMonth" + lastactiveMonth);
			String monthyear = convertedMonthYear;
			String importMonthStatus = "select FILENAME,IMPORTSTATUS,to_char(MONTHYEAR,'dd-Mon-yyyy')as monthyear FROM payment_recov_mnth_upload_log where  to_char(monthyear,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			rs = st.executeQuery(importMonthStatus);
			// log.info("file name is "+ rs.getString("FILENAME"));

			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported");
				}
			}
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", pensionNo = "";
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[2].equals("XXX")) {
					cpfaccno = tempInfo[2].trim();
				} else {
					cpfaccno = "";
				}
				if (!tempInfo[3].equals("XXX")) {
					employeeNo = tempInfo[3].trim();
				}
				if (!tempInfo[4].equals("XXX")) {
					empName = tempInfo[4].trim();
				} else {
					empName = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					designation = tempInfo[5].trim();
				} else {
					designation = "";
				}
				String monthYear = "01-Apr-2007";

				double empOB = 0.00, aaiOB = 0.00, outStandingOB = 0.00;

				if (!tempInfo[11].equals("XXX")) {
					station = tempInfo[11].trim();
				} else {
					station = "";
				}
				if (airportcode != null && !bean.getPfid().trim().equals("")) {
					log.info("pfid" + bean.getPfid() + "sheet airportcode "
							+ station.replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", ""));

					if (region.equals("CHQIAD")) {
						if (!station.replaceAll(" ", "").equals(
								airportcode.replaceAll(" ", ""))) {
							throw new InvalidDataException(
									"Please make sure that the Seleted Airportcode above  (i.e "
											+ airportcode
											+ ") and is matching with AirportCode given in the excel sheet at Column 11. i.e"
											+ station);
						}
					}
				}
				if (!tempInfo[8].equals("XXX")
						&& !tempInfo[8].trim().equals("")) {
					log.info("ob " + tempInfo[8]);
					empOB = Double.parseDouble(tempInfo[8].trim().replaceAll(
							",", "").trim());
				}

				if (!tempInfo[9].equals("XXX")
						&& !tempInfo[9].trim().equals("")) {
					aaiOB = Double.parseDouble(tempInfo[9].replaceAll(",", "")
							.trim());
				}

				if (!tempInfo[10].equals("XXX")
						&& !tempInfo[10].trim().equals("")) {
					outStandingOB = Double.parseDouble(tempInfo[10].replaceAll(
							",", "").trim());
				} else {
					outStandingOB = 0.00;
				}
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for Cpfaccno " + cpfaccno);
					}

				}
				// if (!bean.getPfid().trim().equals("")) {

				insertOB = "insert into employee_pension_ob (pensionno,cpfaccno,OBYEAR,EMPNETOB ,AAINETOB,outstandadv,region,airportcode,employeename,DESEGNATION) values('"
						+ bean.getPfid()
						+ "','"
						+ cpfaccno
						+ "','"
						+ monthYear
						+ "','"
						+ empOB
						+ "','"
						+ aaiOB
						+ "','"
						+ outStandingOB
						+ "','" + region + "','" + station + "','"+empName+"','"+designation+"')";
				String insertArrearCount = "";
				log.info(insertOB);
				st.addBatch(insertOB);
				// }

			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			st.addBatch(importStausupdate);
			int count[] = st.executeBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :importOpeningBalance leaving method");

	}

	public void importAdjOpeningBalance(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {
		int total = 0;
		log.info("ImportDao :importAdjOpeningBalance() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", empName = "", designation = "", station = "", insertAdjOB = "";

		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");

			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
			String lastactiveMonth = 1 + "-" + month + "-" + year;
			log.info(lastactiveMonth + "lastactiveMonth");
			String importMonthStatus = "select FILENAME,IMPORTSTATUS FROM payment_recov_mnth_upload_log where  to_char(monthyear,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			rs = st.executeQuery(importMonthStatus);
			// log.info("file name is "+ rs.getString("FILENAME"));

			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported");
				}
			}
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				String cpfaccno = "", employeeName = "", employeeNo = "", pensionNo = "";
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				if (!tempInfo[2].equals("XXX")) {
					cpfaccno = tempInfo[2].trim();
				} else {
					cpfaccno = "";
				}
				if (!tempInfo[3].equals("XXX")) {
					employeeNo = tempInfo[3].trim();
				}
				if (!tempInfo[4].equals("XXX")) {
					empName = tempInfo[4].trim();
				} else {
					empName = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					designation = tempInfo[5].trim();
				} else {
					designation = "";
				}
				String monthYear = "01-Apr-2008";
				double empsub = 0.00, empsubInterest = 0.00, aaiAmount = 0.00, aaiInterest = 0.00, outstandadv = 0.00;
				if (!tempInfo[17].equals("XXX")) {
					station = tempInfo[17].trim();
				} else {
					station = "";
				}
				if (airportcode != null && !bean.getPfid().trim().equals("")) {
					log.info("pfid" + bean.getPfid() + "sheet airportcode "
							+ station.replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", ""));

					if (region.equals("CHQIAD")) {
						if (!station.replaceAll(" ", "").equals(
								airportcode.replaceAll(" ", ""))) {
							throw new InvalidDataException(
									"Please make sure that the Seleted Airportcode above  (i.e "
											+ airportcode
											+ ") and is matching with AirportCode given in the excel sheet at Column 17. i.e"
											+ station);
						}
					}
				}
				if (!tempInfo[10].equals("XXX")
						&& !tempInfo[10].trim().equals("")) {
					log.info("ob " + tempInfo[10]);
					empsub = Double.parseDouble(tempInfo[10].trim().replaceAll(
							",", "").trim());
				}

				if (!tempInfo[11].equals("XXX")
						&& !tempInfo[11].trim().equals("")) {
					empsubInterest = Double.parseDouble(tempInfo[11]
							.replaceAll(",", "").trim());
				}

				if (!tempInfo[13].equals("XXX")
						&& !tempInfo[13].trim().equals("")) {
					aaiAmount = Double.parseDouble(tempInfo[13].replaceAll(",",
							"").trim());
				} else {
					aaiAmount = 0.00;
				}
				if (!tempInfo[14].equals("XXX")
						&& !tempInfo[14].trim().equals("")) {
					aaiInterest = Double.parseDouble(tempInfo[14].replaceAll(
							",", "").trim());
				} else {
					aaiInterest = 0.00;
				}
				if (!tempInfo[16].equals("XXX")
						&& !tempInfo[16].trim().equals("")) {
					outstandadv = Double.parseDouble(tempInfo[16].replaceAll(
							",", "").trim());
				} else {
					outstandadv = 0.00;
				}
				if (!bean.getCpfAcNo().trim().equals("")
						|| !bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for Cpfaccno " + cpfaccno);
					}

				}
				if (!bean.getPfid().trim().equals("")) {

					insertAdjOB = "update employee_adj_ob  set PENSIONTOTAL=to_char(TO_NUMBER(nvl(PENSIONTOTAL,0)-'"
							+ aaiAmount
							+ "')), PENSIONINTEREST=to_char(TO_NUMBER(nvl(PENSIONINTEREST,0)-'"
							+ aaiInterest
							+ "')),EMPSUB=to_char(TO_NUMBER(nvl(EMPSUB,0)+'"
							+ empsub
							+ "')),EMPSUBINTEREST=to_char(TO_NUMBER(nvl(EMPSUBINTEREST,0)+'"
							+ empsubInterest
							+ "')),OUTSTANDADV=to_char(TO_NUMBER(nvl(OUTSTANDADV,0)+'"
							+ outstandadv
							+ "')),REMARKS=REMARKS ||'aaiAmount:'||'"
							+ aaiAmount
							+ "'||'interest:'||'"
							+ aaiInterest
							+ "' where pensionno='"
							+ bean.getPfid()
							+ "' and region='" + region + "'";
					String insertArrearCount = "";
					log.info(insertAdjOB);
					st.addBatch(insertAdjOB);
				}
			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			st.addBatch(importStausupdate);
			int count[] = st.executeBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :importAdjOpeningBalance leaving method");

	}

	public void advancePfwFinalsettlement(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {
		int total = 0;
		log.info("ImportDao :advancePfwFinalsettlement() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", empName = "", designation = "", station = "";
		String cpfaccno = "", employeeName = "", employeeNo = "", pensionNo = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "", advAmount = "";
		String pfwPurpose = "", settlementdate = "";
		String reasonofSettlement = "";
		double finemp = 0.00, finaai = 0.00, pencon = 0.00, netamount = 0.00;
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");

			conn = commonDB.getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			// String lastactiveMonth = 1 + "-" + month + "-" + year;
			String convertedMonthYear = commonUtil.getMonthYear(month, year);
			String lastactiveMonth = "01" + "-" + convertedMonthYear;
			log.info("lastactiveMonth" + lastactiveMonth);
			String monthyear = convertedMonthYear;
			String importMonthStatus = "select FILENAME,IMPORTSTATUS,to_char(MONTHYEAR,'dd-Mon-yyyy')as monthyear FROM payment_recov_mnth_upload_log where  to_char(monthyear,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			log.info(importMonthStatus);

			rs = st.executeQuery(importMonthStatus);
			// log.info("file name is "+ rs.getString("FILENAME"));

			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported");
				}
			}
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			for (int i = 1; i < xlsDataList.size(); i++) {
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[1].equals("XXX")) {
					pensionNo = commonUtil.getSearchPFID1(tempInfo[1].trim());
				} else {
					pensionNo = "";
				}
				if (!tempInfo[2].equals("XXX")) {
					cpfaccno = tempInfo[2].trim();
				} else {
					cpfaccno = "";
				}
				if (!tempInfo[3].equals("XXX")) {
					employeeNo = tempInfo[3].trim();
				}
				if (!tempInfo[4].equals("XXX")) {
					empName = tempInfo[4].trim().replaceAll("'", "");
				} else {
					empName = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					designation = tempInfo[5].trim();
				} else {
					designation = "";
				}
				log.info("" + tempInfo[8]);
				if (!tempInfo[8].equals("XXX") && !pensionNo.trim().equals("")) {

					int index = tempInfo[8].trim().indexOf("-");
					String count[] = tempInfo[8].split("-");
					log.info(" countlength " + count.length);
					if (index == -1 || count.length != 3) {
						throw new InvalidDataException(
								"PFID "
										+ pensionNo.trim()
										+ " doesn't have valid date format(i.e dd-Mon-yyyy) in Column I of the Uploaded sheet");
					}
					advTransDate = tempInfo[8].trim();
				} else {
					advTransDate = "";
				}

				// Advance Attributes
				if (!tempInfo[9].equals("XXX")) {
					advPurpose = tempInfo[9].trim().replaceAll("'", "");
					;
				} else {
					advPurpose = "";
				}

				if (!tempInfo[10].equals("XXX") && !tempInfo[10].equals(".")) {
					advAmount = tempInfo[10].replaceAll(",", "").trim();
				} else {
					advAmount = "0.00";
				}
				// Pfw Attributes
				if (!tempInfo[11].equals("XXX")) {
					pfwPurpose = tempInfo[11].trim().replaceAll("'", "");
				} else {
					pfwPurpose = "";
				}
				if (!tempInfo[12].equals("XXX")) {
					empShare = tempInfo[12].replaceAll(",", "").trim();
				} else {
					empShare = "0.00";
				}
				if (!tempInfo[13].equals("XXX")) {
					aaiShare = tempInfo[13].replaceAll(",", "").trim();
				} else {
					aaiShare = "0.00";
				}

				// final settlement Attributes

				if (!tempInfo[15].equals("XXX")) {
					reasonofSettlement = tempInfo[15].trim();
				} else {
					reasonofSettlement = "";
				}
				if (!tempInfo[16].equals("XXX")) {
					finemp = Double.parseDouble(tempInfo[16]
							.replaceAll(",", "").trim());
				} else {
					finemp = 0.00;
				}
				if (!tempInfo[17].equals("XXX")) {
					finaai = Double.parseDouble(tempInfo[17]
							.replaceAll(",", "").trim());
				} else {
					finaai = 0.00;
				}
				if (!tempInfo[18].equals("XXX")) {
					pencon = Double.parseDouble(tempInfo[18]
							.replaceAll(",", "").trim());
				} else {
					pencon = 0.00;
				}
				if (!tempInfo[19].equals("XXX")) {
					netamount = Double.parseDouble(tempInfo[19].replaceAll(",",
							"").trim());
				} else {
					netamount = 0.00;
				}

				if (!tempInfo[21].equals("XXX") && !pensionNo.trim().equals("")) {
					int index = tempInfo[21].trim().indexOf("-");
					String count[] = tempInfo[21].split("-");
					log.info(" countlength " + count.length);
					if (index == -1 && count.length != 3) {
						throw new InvalidDataException(
								"PFID "
										+ pensionNo.trim()
										+ " doesn't have valid date format(i.e dd-Mon-yyyy) in column V of the Uploaded sheet");
					}
					settlementdate = tempInfo[21].trim();
				} else {
					settlementdate = "";
				}
				if (!tempInfo[22].equals("XXX")) {
					station = tempInfo[22].trim();
				} else {
					station = "";
				}

				if (airportcode != null && !pensionNo.trim().equals("")) {
					log.info("sheet airportcode " + station.replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", ""));

					if (region.equals("CHQIAD") && !pensionNo.equals("")) {
						if (!station.replaceAll(" ", "").equals(
								airportcode.replaceAll(" ", ""))) {
							throw new InvalidDataException(
									"Please make sure that the Seleted Airportcode above  (i.e "
											+ airportcode
											+ ") and is matching with AirportCode given in the excel sheet at Column W. i.e"
											+ station);
						}
					}
				}
				String insertAdvanceTabledata = "", insertPfwTabledata = "", insertFinalSettlementTabledata = "";
				if (pensionNo != "") {
					insertAdvanceTabledata = "insert into  employee_pension_advances (cpfaccno,employeeno,employeename,amount,advPurpose,AdvTransDate,airportcode,region,PARTAMT,PENSIONNO)values('"
							+ cpfaccno
							+ "','"
							+ employeeNo
							+ "','"
							+ empName
							+ "','"
							+ Float.parseFloat(advAmount)
							+ "','"
							+ advPurpose
							+ "','"
							+ advTransDate
							+ "','"
							+ station.trim()
							+ "','"
							+ region
							+ "','0.00','"
							+ pensionNo + "')";
					st.addBatch(insertAdvanceTabledata);
					log.info(insertAdvanceTabledata);
				}

				if (pensionNo != "") {
					insertPfwTabledata = "insert into  employee_pension_loans(cpfaccno,employeeno,employeename,SUB_AMT,CONT_AMT,LOANPURPOSE,LOANDATE,airportcode,region,LOANTYPE,PENSIONNO)values('"
							+ cpfaccno
							+ "','"
							+ employeeNo
							+ "','"
							+ empName
							+ "','"
							+ Float.parseFloat(empShare)
							+ "','"
							+ Float.parseFloat(aaiShare)
							+ "','"
							+ pfwPurpose
							+ "','"
							+ advTransDate
							+ "','"
							+ station
							+ "','"
							+ region + "','NRF','" + pensionNo + "')";
					log.info(insertPfwTabledata);
					st.addBatch(insertPfwTabledata);

				}
				if (pensionNo != "") {
					insertFinalSettlementTabledata = "insert into employee_pension_finsettlement(cpfaccno,employeeno,employeename,pensionno,purpose,finemp,finaai,pencon,netamount,settlementdate,airportcode,region)values('"
							+ cpfaccno
							+ "','"
							+ employeeNo
							+ "','"
							+ empName
							+ "','"
							+ pensionNo
							+ "','"
							+ reasonofSettlement
							+ "',"
							+ finemp
							+ ","
							+ finaai
							+ ","
							+ pencon
							+ ","
							+ netamount
							+ ",'"
							+ settlementdate
							+ "','"
							+ airportcode + "','" + region + "')";
					log.info(insertFinalSettlementTabledata);
					st.addBatch(insertFinalSettlementTabledata);
				}

			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			log.info(importStausupdate);
			st.addBatch(importStausupdate);
			int count[] = st.executeBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :advancePfwFinalsettlement leaving method");

	}

	public void createNewPFIDS(String xlsData, String region, String userName,
			String ipAddress) throws InvalidDataException {

		log.info("ImportDao :createNewPFIDS() entering method");
		NomineeBean nomineeBean = new NomineeBean();
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		PreparedStatement pst = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "";
		int updateEmpinfoCount = 0, updateTransactionCount = 0, totalEmpInfoCount = 0, totalTransCount = 0;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}

				if (!tempInfo[8].equals("XXX")) {
					bean.setGender(tempInfo[8].trim());
				} else {
					bean.setGender("");
				}
				// FOR UTILIZING OLF PFIDS
				if (!tempInfo[1].equals("XXX")) {
					bean.setPensionNo(tempInfo[1].trim());
				} else {
					bean.setPensionNo("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setAirportCode(tempInfo[9].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setRegion(tempInfo[10].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setWetherOption(tempInfo[11].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[12].equals("XXX")) {
					bean.setRemarks(tempInfo[12].trim());
				} else {
					bean.setRemarks("");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setDivision(tempInfo[13].trim());
				} else {
					bean.setDivision("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setFhName(tempInfo[14].trim());
				} else {
					bean.setFhName("");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setSeperationReason(tempInfo[15].trim());
				} else {
					bean.setSeperationReason("");
				}
				if (!tempInfo[16].equals("XXX")) {
					bean.setSeperationDate(tempInfo[16].trim());
				} else {
					bean.setSeperationDate("");
				}
				PersonalService personalService = new PersonalService();
				String uniqueID = "", pensionNo = "";
				// below mentioned method only for creation of new PFIDs
				// uniqueID = personalService.addPersonalInfo(bean,
				// nomineeBean,userName, ipAddress);
				/*
				 * try { pensionNo = PersonalDAO.getSequenceNo(conn);
				 * bean.setPensionNo(pensionNo); } catch (Exception e1) { //
				 * TODO Auto-generated catch block log.printStackTrace(e1);
				 * throw e1; }
				 */
				// for utilizing old pfid's
				String updateEmpSerialNumber = "", updateTransaction = "";
				log.info("Pension No" + bean.getPensionNo() + "Employee Name"
						+ bean.getEmployeeName());
				log.info(" dob " + bean.getDateOfBirth() + "doj "
						+ bean.getDateOfJoining());
				log.info(" CPFACCNO " + bean.getCpfAccno() + "Employeeno"
						+ bean.getEmployeeNumber() + "Desigantion"
						+ bean.getDesignation());
				if (!bean.getCpfAccno().equals("")) {
					updateEmpSerialNumber = "update employee_info set EMPSERIALNUMBER='"
							+ bean.getPensionNo()
							+ "' ,DATEOFBIRTH='"
							+ bean.getDateOfBirth()
							+ "',dateofjoining='"
							+ bean.getDateOfJoining()
							+ "',FHNAME='"
							+ bean.getFhName()
							+ "',DESEGNATION ='"
							+ bean.getDesignation()
							+ "',wetheroption='"
							+ bean.getWetherOption()
							+ "',sex='"
							+ bean.getGender()
							+ "' where cpfacno='"
							+ bean.getCpfAccno()
							+ "' and region='"
							+ bean.getRegion() + "'";
					updateTransaction = "update employee_pension_validate set pensionno='"
							+ bean.getPensionNo()
							+ "' where cpfaccno='"
							+ bean.getCpfAccno()
							+ "' and region='"
							+ bean.getRegion() + "'";
				}
				String insertNewRecordInPersonalInfo = "insert into employee_personal_info (cpfacno,employeename,dateofbirth,dateofjoining,GENDER,airportcode,region,USERNAME,ipaddress,lastactive,pensionno,WETHEROPTION,EMPLOYEENO,DESEGNATION,FHNAME,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,DIVISION) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				pst = conn.prepareStatement(insertNewRecordInPersonalInfo);
				pst.setString(1, bean.getCpfAccno());
				pst.setString(2, bean.getEmployeeName());
				pst.setString(3, bean.getDateOfBirth());
				pst.setString(4, bean.getDateOfJoining());
				pst.setString(5, bean.getGender());
				pst.setString(6, bean.getAirportCode());
				pst.setString(7, bean.getRegion());
				pst.setString(8, userName);
				pst.setString(9, ipAddress.trim());
				pst.setString(10, commonUtil.getCurrentDate("dd-MMM-yyyy"));
				pst.setString(11, bean.getPensionNo().trim());
				pst.setString(12, bean.getWetherOption());
				pst.setString(13, bean.getEmployeeNumber());
				pst.setString(14, bean.getDesignation());
				pst.setString(15, bean.getFhName());
				pst.setString(16, bean.getSeperationReason());
				pst.setString(17, bean.getSeperationDate());
				pst.setString(18, bean.getDivision());
				int personalInsertCount = pst.executeUpdate();
				log.info(insertNewRecordInPersonalInfo);
				if (!bean.getCpfAccno().equals("") && personalInsertCount != 0) {
					updateEmpinfoCount = st
							.executeUpdate(updateEmpSerialNumber);
					updateTransactionCount = st
							.executeUpdate(updateTransaction);
				}

				totalEmpInfoCount = totalEmpInfoCount + updateEmpinfoCount;
				totalTransCount = totalTransCount + updateTransactionCount;

			}
			log.info(" updateTransactionCount" + totalTransCount
					+ "totalEmpInfoCount" + totalEmpInfoCount);
		} catch (Exception e) {
			throw new InvalidDataException(e.getMessage());
		}

		log.info("ImportDao :createNewPFIDS leaving method");

	}

	public void createNewPFIDSFrom19100(String xlsData, String region,
			String userName, String ipAddress) throws InvalidDataException {

		log.info("ImportDao :createNewPFIDS() entering method");
		NomineeBean nomineeBean = new NomineeBean();
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement insst = null;
		PreparedStatement pst = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", sql = "", remarksinfo = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "", refPensionNo = "";
		int updateEmpinfoCount = 0, updateTransactionCount = 0, totalEmpInfoCount = 0, totalTransCount = 0, personalInsertCount = 0;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			String srlno = "";
			for (int i = 1; i < xlsDataList.size(); i++) {
				st = conn.createStatement();
				insst = conn.createStatement();
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					srlno = tempInfo[0].trim();
				} else {
					srlno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setOldPensionNo(tempInfo[1].trim());
				} else {
					bean.setOldPensionNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}
				log.info(" dob " + bean.getDateOfBirth() + "doj "
						+ bean.getDateOfJoining());
				if (!tempInfo[8].equals("XXX")) {
					bean.setGender(tempInfo[8].trim());
				} else {
					bean.setGender("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setAirportCode(tempInfo[9].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setRegion(tempInfo[10].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setWetherOption(tempInfo[11].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[12].equals("XXX")) {
					bean.setSeperationReason(tempInfo[12].trim());
				} else {
					bean.setSeperationReason("");
				}
				if (!tempInfo[13].equals("XXX")) {
					if (tempInfo[13].indexOf(".") != -1) {
						bean
								.setSeperationDate(commonUtil
										.converDBToAppFormat(tempInfo[13]
												.trim(), "dd.MM.yy",
												"dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setSeperationDate(commonUtil.converDBToAppFormat(
								tempInfo[13].trim(), "dd/MM/yyyy",
								"dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setSeperationDate(commonUtil.converDBToAppFormat(
								tempInfo[13].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setSeperationDate("");
					}

				} else {
					bean.setSeperationDate("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setForm2Nominee(tempInfo[14].trim());
				} else {
					bean.setForm2Nominee("");
				}

				if (!tempInfo[15].equals("XXX")) {
					bean.setEmpDesLevel(tempInfo[15].trim());
				} else {
					bean.setEmpDesLevel("");
				}

				if (!tempInfo[16].equals("XXX")) {
					bean.setFhName(tempInfo[16].trim());
				} else {
					bean.setFhName("");
				}
				if (!tempInfo[17].equals("XXX")) {
					bean.setMaritalStatus(tempInfo[17].trim());
				} else {
					bean.setMaritalStatus("");
				}

				if (!tempInfo[18].equals("XXX")) {
					bean.setPerAddress(tempInfo[18].trim());
				} else {
					bean.setPerAddress("");
				}

				if (!tempInfo[19].equals("XXX")) {
					bean.setTempAddress(tempInfo[19].trim());
				} else {
					bean.setTempAddress("");
				}
				if (!tempInfo[20].equals("XXX")) {
					bean.setDateOfAnnuation(tempInfo[20].trim());
				} else {
					bean.setDateOfAnnuation("");
				}

				if (!tempInfo[21].equals("XXX")) {
					bean.setDivision(tempInfo[21].trim());
				} else {
					bean.setDivision("");
				}

				if (!tempInfo[22].equals("XXX")) {
					bean.setDepartment(tempInfo[22].trim());
				} else {
					bean.setDepartment("");
				}

				if (!tempInfo[23].equals("XXX")) {
					bean.setEmailID(tempInfo[23].trim());
				} else {
					bean.setEmailID("");
				}
				if (!tempInfo[24].equals("XXX")) {
					bean.setEmpNomineeSharable(tempInfo[24].trim());
				} else {
					bean.setEmpNomineeSharable("");
				}
				if (!tempInfo[25].equals("XXX")) {
					bean.setRemarks(tempInfo[25].trim());
				} else {
					bean.setRemarks("");
				}
				if (!tempInfo[26].equals("XXX")) {
					bean.setFhFlag(tempInfo[26].trim());
				} else {
					bean.setFhFlag("");
				}
				if (!tempInfo[27].equals("XXX")) {
					bean.setMappingFlag(tempInfo[27].trim());
				} else {
					bean.setMappingFlag("");
				}
				log.info("Old Pension No" + bean.getOldPensionNo()
						+ "CPF ACCNO" + bean.getCpfAccno());
				log.info("Employee Name" + bean.getEmployeeName()
						+ "Date Of Birth" + bean.getDateOfBirth());
				log.info("DateOfAnnuation" + bean.getDateOfAnnuation()
						+ "Date Of Joining" + bean.getDateOfJoining());
				log.info("Employee No" + bean.getEmployeeNumber()
						+ "Designaiton" + bean.getDesignation() + "Emp Level"
						+ bean.getEmpDesLevel());
				log.info("getSeperationReason" + bean.getSeperationReason()
						+ "SeperationReasonDate" + bean.getSeperationDate()
						+ "Form-2 Nominee" + bean.getForm2Nominee());
				log.info("Remarks" + bean.getRemarks() + "Airportcode"
						+ bean.getAirportCode() + "Gender" + bean.getGender());
				log.info("FatherName" + bean.getFhName() + "Department"
						+ bean.getDepartment() + "MaritalStatus"
						+ bean.getMaritalStatus());

				String uniqueID = "", pensionNo = "";
				String updateEmpSerialNumber = "", updateTransaction = "";
				// creating new pfids
				try {
					pensionNo = PersonalDAO.getSequenceNo(conn);
					log.info("New Sequence ID" + pensionNo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					log.printStackTrace(e1);
					throw e1;
				}
				log.info("Old Pension No" + bean.getOldPensionNo()
						+ "New pensionNo" + pensionNo);
				sql = "insert into employee_personal_info(CPFACNO,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
						+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,FHFLAG,MAPINGFLAG,IPAddress)"
						+ " VALUES " + "('"
						+ bean.getCpfAccno().trim()
						+ "','"
						+ bean.getEmployeeNumber().trim()
						+ "','"
						+ bean.getEmployeeName().toUpperCase().trim()
						+ "','"
						+ bean.getDesignation().trim()
						+ "','"
						+ bean.getEmpDesLevel().trim()
						+ "','"
						+ bean.getDateOfBirth().trim()
						+ "','"
						+ bean.getDateOfJoining().trim()
						+ "','"
						+ bean.getSeperationReason().trim()
						+ "','"
						+ bean.getSeperationDate().trim()
						+ "','"
						+ bean.getForm2Nominee().trim()
						+ "','"
						+ bean.getRemarks().trim()
						+ "','"
						+ bean.getAirportCode().trim()
						+ "','"
						+ bean.getGender().trim()
						+ "','"
						+ bean.getFhName().trim()
						+ "','"
						+ bean.getMaritalStatus().trim()
						+ "','"
						+ bean.getPerAddress().trim()
						+ "','"
						+ bean.getTempAddress().trim()
						+ "','"
						+ bean.getWetherOption().trim()
						+ "','"
						+ bean.getDateOfAnnuation().trim()
						+ "','"
						+ bean.getDivision().trim()
						+ "','"
						+ bean.getDepartment().trim()
						+ "','"
						+ bean.getEmailID().trim()
						+ "','"
						+ bean.getEmpNomineeSharable().trim()
						+ "','"
						+ bean.getRegion().trim()
						+ "','"
						+ pensionNo
						+ "','"
						+ userName
						+ "','"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "','"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "','"
						+ bean.getFhFlag()
						+ "','"
						+ bean.getMappingFlag()
						+ "','" + ipAddress + "')";
				log.info("sql" + sql);

				personalInsertCount = insst.executeUpdate(sql);

				/*
				 * remarksinfo=pensionNo+"="+bean.getEmployeeName();
				 * 
				 * updateEmpSerialNumber =
				 * "update employee_info set EMPSERIALNUMBER='" + pensionNo
				 * +"',REMARKS=REMARKS ||'" +remarksinfo +
				 * "' where EMPSERIALNUMBER='" + bean.getOldPensionNo() + "'";
				 * updateTransaction =
				 * "update employee_pension_validate set pensionno='" +
				 * pensionNo + "' where pensionno='" + bean.getOldPensionNo()+
				 * "'";
				 * 
				 * sql="update employee_personal_info set CPFACNO='"+
				 * bean.getCpfAccno
				 * ().trim()+"',EMPLOYEENO='"+bean.getEmployeeNumber
				 * ().trim()+"', EMPLOYEENAME='"
				 * +bean.getEmployeeName().toUpperCase
				 * ().trim()+"',DESEGNATION='"
				 * +bean.getDesignation().trim()+"',EMP_LEVEL='"
				 * +bean.getEmpDesLevel
				 * ().trim()+"',DATEOFBIRTH='"+bean.getDateOfBirth
				 * ().trim()+"',DATEOFJOINING='"
				 * +bean.getDateOfJoining().trim()+"',DATEOFSEPERATION_REASON='"
				 * +
				 * bean.getSeperationReason().trim()+"',DATEOFSEPERATION_DATE='"
				 * +
				 * bean.getSeperationDate().trim()+"',WHETHER_FORM1_NOM_RECEIVED='"
				 * +
				 * bean.getForm2Nominee().trim()+"',REMARKS='"+bean.getRemarks()
				 * .trim()+"',AIRPORTCODE='"+bean.getAirportCode().trim()+
				 * "',GENDER='"
				 * +bean.getGender().trim()+"',FHNAME='"+bean.getFhName
				 * ().trim()+"',MARITALSTATUS='"+bean.getMaritalStatus().trim()+
				 * "',PERMANENTADDRESS='"
				 * +bean.getPerAddress().trim()+"',TEMPORATYADDRESS='"
				 * +bean.getTempAddress
				 * ().trim()+"',WETHEROPTION='"+bean.getWetherOption
				 * ().trim()+"',SETDATEOFANNUATION='"
				 * +bean.getDateOfAnnuation().trim
				 * ()+"',DIVISION='"+bean.getDivision
				 * ().trim()+"',DEPARTMENT='"+bean
				 * .getDepartment().trim()+"',EMAILID='"
				 * +bean.getEmailID().trim()
				 * +"',EMPNOMINEESHARABLE='"+bean.getEmpNomineeSharable
				 * ().trim()+
				 * "',REGION='"+bean.getRegion().trim()+"',PENSIONNO='"
				 * +srlno+"',username='"
				 * +userName+"',LASTACTIVE='"+commonUtil.getCurrentDate
				 * ("dd-MMM-yyyy"
				 * )+"',RefMonthYear='"+commonUtil.getCurrentDate("dd-MMM-yyyy"
				 * )+"',FHFLAG='"+bean.getFhFlag()+"',MAPINGFLAG='"+bean.
				 * getMappingFlag
				 * ()+"',IPAddress='"+ipAddress+"' where pensionno='"+srlno+"'";
				 * log.info(sql); personalInsertCount=insst.executeUpdate(sql);
				 * 
				 * remarksinfo=srlno+"="+bean.getEmployeeName();
				 * 
				 * updateEmpSerialNumber =
				 * "update employee_info set EMPSERIALNUMBER='" + srlno
				 * +"',REMARKS=REMARKS ||'" +remarksinfo +
				 * "' where EMPSERIALNUMBER='" + bean.getOldPensionNo() + "'";
				 * updateTransaction =
				 * "update employee_pension_validate set pensionno='" + srlno +
				 * "' where pensionno='" + bean.getOldPensionNo()+ "'";
				 */
				commonDB.closeStatement(insst);
				/*
				 * if(personalInsertCount!=0 &&
				 * !bean.getOldPensionNo().equals("")){ updateEmpinfoCount =
				 * st.executeUpdate(updateEmpSerialNumber);
				 * updateTransactionCount = st
				 * .executeUpdate(updateTransaction); }
				 * commonDB.closeConnection(null,st, null);
				 */
				totalEmpInfoCount = totalEmpInfoCount + updateEmpinfoCount;
				totalTransCount = totalTransCount + updateTransactionCount;

			}
			log.info(" updateTransactionCount" + totalTransCount
					+ "totalEmpInfoCount" + totalEmpInfoCount);
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :createNewPFIDS leaving method");

	}

	public void updateCPFAccnoMIAL(String xlsData, String region,
			String userName, String ipAddress) throws InvalidDataException {

		log.info("ImportDao :createNewPFIDS() entering method");
		NomineeBean nomineeBean = new NomineeBean();
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement insst = null;
		PreparedStatement pst = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", sql = "", remarksinfo = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "", refPensionNo = "";
		int updateEmpinfoCount = 0, updateTransactionCount = 0, totalEmpInfoCount = 0, totalTransCount = 0, personalInsertCount = 0;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			String srlno = "";
			for (int i = 1; i < xlsDataList.size(); i++) {
				st = conn.createStatement();
				insst = conn.createStatement();
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					srlno = tempInfo[0].trim();
				} else {
					srlno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setOldPensionNo(tempInfo[1].trim());
				} else {
					bean.setOldPensionNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}
				log.info(" dob " + bean.getDateOfBirth() + "doj "
						+ bean.getDateOfJoining());

				if (!tempInfo[8].equals("XXX")) {
					bean.setRegion(tempInfo[8].trim());
				} else {
					bean.setRegion("");
				}

				/*
				 * if (!tempInfo[9].equals("XXX")) {
				 * bean.setAirportCode(tempInfo[9].trim()); } else {
				 * bean.setAirportCode(""); } if (!tempInfo[10].equals("XXX")) {
				 * bean.setRegion(tempInfo[10].trim()); } else {
				 * bean.setRegion(""); } if (!tempInfo[11].equals("XXX")) {
				 * bean.setWetherOption(tempInfo[11].trim()); } else {
				 * bean.setWetherOption(""); }
				 * 
				 * if (!tempInfo[12].equals("XXX")) {
				 * bean.setSeperationReason(tempInfo[12].trim()); } else {
				 * bean.setSeperationReason(""); } if
				 * (!tempInfo[13].equals("XXX")) {
				 * if(tempInfo[13].indexOf(".")!= -1){
				 * bean.setSeperationDate(commonUtil
				 * .converDBToAppFormat(tempInfo[13].trim(),"dd.MM.yy",
				 * "dd-MMM-yyyy")); }else if(tempInfo[7].indexOf("/")!= -1){
				 * bean
				 * .setSeperationDate(commonUtil.converDBToAppFormat(tempInfo
				 * [13].trim(),"dd/MM/yyyy", "dd-MMM-yyyy")); }else
				 * if(tempInfo[7].indexOf("-")!= -1){
				 * bean.setSeperationDate(commonUtil
				 * .converDBToAppFormat(tempInfo[13].trim(),"dd-MMM-yyyy",
				 * "dd-MMM-yyyy")); }else { bean.setSeperationDate(""); }
				 * 
				 * } else { bean.setSeperationDate(""); } if
				 * (!tempInfo[14].equals("XXX")) {
				 * bean.setForm2Nominee(tempInfo[14].trim()); } else {
				 * bean.setForm2Nominee(""); }
				 * 
				 * if (!tempInfo[15].equals("XXX")) {
				 * bean.setEmpDesLevel(tempInfo[15].trim()); } else {
				 * bean.setEmpDesLevel(""); }
				 * 
				 * if (!tempInfo[16].equals("XXX")) {
				 * bean.setFhName(tempInfo[16].trim()); } else {
				 * bean.setFhName(""); } if (!tempInfo[17].equals("XXX")) {
				 * bean.setMaritalStatus(tempInfo[17].trim()); } else {
				 * bean.setMaritalStatus(""); }
				 * 
				 * if (!tempInfo[18].equals("XXX")) {
				 * bean.setPerAddress(tempInfo[18].trim()); } else {
				 * bean.setPerAddress(""); }
				 * 
				 * if (!tempInfo[19].equals("XXX")) {
				 * bean.setTempAddress(tempInfo[19].trim()); } else {
				 * bean.setTempAddress(""); } if (!tempInfo[20].equals("XXX")) {
				 * bean.setDateOfAnnuation(tempInfo[20].trim()); } else {
				 * bean.setDateOfAnnuation(""); }
				 * 
				 * if (!tempInfo[21].equals("XXX")) {
				 * bean.setDivision(tempInfo[21].trim()); } else {
				 * bean.setDivision(""); }
				 * 
				 * if (!tempInfo[22].equals("XXX")) {
				 * bean.setDepartment(tempInfo[22].trim()); } else {
				 * bean.setDepartment(""); }
				 * 
				 * if (!tempInfo[23].equals("XXX")) {
				 * bean.setEmailID(tempInfo[23].trim()); } else {
				 * bean.setEmailID(""); } if (!tempInfo[24].equals("XXX")) {
				 * bean.setEmpNomineeSharable(tempInfo[24].trim()); } else {
				 * bean.setEmpNomineeSharable(""); } if
				 * (!tempInfo[25].equals("XXX")) {
				 * bean.setRemarks(tempInfo[25].trim()); } else {
				 * bean.setRemarks(""); } if (!tempInfo[26].equals("XXX")) {
				 * bean.setFhFlag(tempInfo[26].trim()); } else {
				 * bean.setFhFlag(""); } if (!tempInfo[27].equals("XXX")) {
				 * bean.setMappingFlag(tempInfo[27].trim()); } else {
				 * bean.setMappingFlag(""); }
				 */
				log.info("Old Pension No" + bean.getOldPensionNo()
						+ "CPF ACCNO" + bean.getCpfAccno());
				log.info("Employee Name" + bean.getEmployeeName()
						+ "Date Of Birth" + bean.getDateOfBirth());
				log.info("DateOfAnnuation" + bean.getDateOfAnnuation()
						+ "Date Of Joining" + bean.getDateOfJoining());
				log.info("Employee No" + bean.getEmployeeNumber()
						+ "Designaiton" + bean.getDesignation() + "Emp Level"
						+ bean.getEmpDesLevel());
				log.info("getSeperationReason" + bean.getSeperationReason()
						+ "SeperationReasonDate" + bean.getSeperationDate()
						+ "Form-2 Nominee" + bean.getForm2Nominee());
				log.info("Remarks" + bean.getRemarks() + "Airportcode"
						+ bean.getAirportCode() + "Gender" + bean.getGender());
				log.info("FatherName" + bean.getFhName() + "Department"
						+ bean.getDepartment() + "MaritalStatus"
						+ bean.getMaritalStatus());

				String uniqueID = "", pensionNo = "";
				String updateEmpSerialNumber = "", updateTransaction = "";

				sql = "update employee_personal_info set CPFACNO='"
						+ bean.getCpfAccno().trim() + "' where pensionno='"
						+ bean.getOldPensionNo() + "'";
				log.info(sql);
				personalInsertCount = insst.executeUpdate(sql);

				updateEmpSerialNumber = "update employee_info set EMPSERIALNUMBER='"
						+ bean.getOldPensionNo()
						+ "' where cpfacno='"
						+ bean.getCpfAccno()
						+ "' and region='"
						+ bean.getRegion() + "'";
				updateTransaction = "update employee_pension_validate set pensionno='"
						+ bean.getOldPensionNo()
						+ "' where cpfaccno='"
						+ bean.getCpfAccno()
						+ "' and region='"
						+ bean.getRegion() + "'";

				commonDB.closeStatement(insst);
				if (personalInsertCount != 0
						&& !bean.getOldPensionNo().equals("")) {
					updateEmpinfoCount = st
							.executeUpdate(updateEmpSerialNumber);
					updateTransactionCount = st
							.executeUpdate(updateTransaction);
				}
				commonDB.closeStatement(st);
				totalEmpInfoCount = totalEmpInfoCount + updateEmpinfoCount;
				totalTransCount = totalTransCount + updateTransactionCount;

			}
			log.info(" updateTransactionCount" + totalTransCount
					+ "totalEmpInfoCount" + totalEmpInfoCount);
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :createNewPFIDS leaving method");

	}

	public void importIgiIad0809Data(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importIgiIad0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int count = 0;
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.00");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), "CHQIAD");

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, "IGI IAD");
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, "CHQIAD".trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					log.info("vpf" + bean.getEmployeeVPF() + "Principal"
							+ bean.getPrincipal() + "Interest"
							+ bean.getInterest());
					pst.executeUpdate();
					pst.close();
				} else {
					float emoluments = 0.0f;
					if (bean.getEmployeePF().trim() != ""
							|| (!bean.getEmployeePF().trim().equals(""))) {
						emoluments = Float.parseFloat(bean.getEmployeePF()
								.trim()) * 100 / 12;
					}

					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					if (!bean.getEmoluments().equals("0.00")
							&& !bean.getEmoluments().equals("0.0")
							&& !bean.getEmoluments().equals("0")
							&& !bean.getEmoluments().equals("")) {
						insertArrearCount = "update employee_pension_validate set EMOLUMENTS=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					} else {
						insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		}
		log.info("ImportDao :IGI IAD leaving method");

	}

	public void importChennaiIad0809Data(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importIgiIad0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int count = 0;
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}

				if (!tempInfo[4].equals("XXX")) {
					monthYear = tempInfo[4].trim();
				} else {
					monthYear = "";
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmpName(tempInfo[5].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setDesegnation(tempInfo[6].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].trim());
				} else {
					bean.setEmoluments("0.00");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].trim());
				} else {
					bean.setInterest("0.00");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setStation(tempInfo[14].trim());
				} else {
					bean.setStation("");
				}
				if (!tempInfo[15].equals("XXX")) {
					bean.setRegion(tempInfo[15].trim());
				} else {
					bean.setRegion("");
				}

				log.info(" Principle " + bean.getPrincipal() + "interest  "
						+ bean.getInterest() + " vpf " + bean.getEmployeeVPF());
				String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
						+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String foundSalaryDate = this.checkFinanceDuplicate(conn,
						monthYear.trim(), bean.getCpfAcNo(), bean
								.getEmpNumber(), bean.getRegion());

				if (foundSalaryDate.equals("")) {
					pst = conn.prepareStatement(finaceInsert);
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation());
					pst.setString(5, monthYear.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());

					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					log.info("vpf" + bean.getEmployeeVPF() + "Principal"
							+ bean.getPrincipal() + "Interest"
							+ bean.getInterest());
					pst.executeUpdate();
					pst.close();
				} else {
					float emoluments = 0.0f;
					if (bean.getEmployeePF().trim() != ""
							|| (!bean.getEmployeePF().trim().equals(""))) {
						emoluments = Float.parseFloat(bean.getEmployeePF()
								.trim()) * 100 / 12;
					}

					st = conn.createStatement();
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					if (!bean.getEmoluments().equals("0.00")
							&& !bean.getEmoluments().equals("0.0")
							&& !bean.getEmoluments().equals("0")
							&& !bean.getEmoluments().equals("")) {
						insertArrearCount = "update employee_pension_validate set EMOLUMENTS=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					} else {

						insertArrearCount = "update employee_pension_validate set EMOLUMENTS=to_char(TO_NUMBER(nvl(emoluments,0)+'"
								+ emoluments
								+ "')) ,  EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
								+ bean.getEmployeePF().trim()
								+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
								+ bean.getEmployeeVPF().trim()
								+ "')),EMPADVRECINTEREST =to_char(TO_NUMBER(nvl(EMPADVRECINTEREST ,0)+'"
								+ bean.getInterest().trim()
								+ "')),EMPADVRECPRINCIPAL =to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL ,0)+'"
								+ bean.getPrincipal().trim()
								+ "'))  where region='CHQIAD' and cpfaccno='"
								+ bean.getCpfAcNo().trim()
								+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
								+ transMonthYear + "'";

					}
					log.info("update emolumentss " + insertArrearCount);
					count = st.executeUpdate(insertArrearCount);
					st.close();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		}
		log.info("ImportDao :Chennai IAD leaving method");

	}

	public void deleteRetiredEmployees(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :deleteRetiredEmployees() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		int count = 0;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 0; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				// log.info("hedername" + tempInfo[1].trim());
				/*
				 * if (!tempInfo[1].equals("XXX")) {
				 * bean.setCpfAcNo(tempInfo[1].trim()); } else {
				 * bean.setCpfAcNo(""); }
				 */
				if (!tempInfo[2].equals("XXX")) {
					bean.setPensionNumber(tempInfo[2].trim());
				} else {
					bean.setPensionNumber("");
				}
				String monthYear = "";
				if (!tempInfo[3].equals("XXX")) {
					monthYear = tempInfo[3].trim();
				} else {
					monthYear = "";
				}
				/*
				 * if (!tempInfo[4].equals("XXX")) {
				 * bean.setStation(tempInfo[4].trim()); } else {
				 * bean.setStation(""); }
				 */
				// String transMonthYear =
				// commonUtil.converDBToAppFormat(monthYear, "dd-MMM-yyyy",
				// "-MMM-yyyy");
				String deleteTransaction = "";
				if (bean.getPensionNumber() != "") {
					deleteTransaction = "delete from employee_personal_info where PENSIONNO='"
							+ bean.getPensionNumber()
							+ "' and to_char(DATEOFBIRTH,'dd-Mon-yyyy') like '%"
							+ monthYear + "'";
					log.info(deleteTransaction);

				}

				count = st.executeUpdate(deleteTransaction);
				count = count++;
				log.info("deleteedrecords" + count);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :deleteRetiredEmployees leaving method");

	}

	public void importPensionOptUpdate(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importPensionOptUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.info("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPensionNo(commonUtil.getSearchPFID1(tempInfo[1]
							.trim()));
				} else {
					bean.setPensionNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}

				if (!tempInfo[8].equals("XXX")) {
					bean.setFhName(tempInfo[8].trim());
				} else {
					bean.setFhName("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setWetherOption(tempInfo[9].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setAirportCode(tempInfo[10].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setRegion(tempInfo[11].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setRemarks(tempInfo[12].trim());
				} else {
					bean.setRemarks("");
				}

				if (!(bean.getRemarks().equals("O") || bean.getRemarks()
						.equals("E"))) {
					bean.setWetherOption(bean.getRemarks());
				}
				log.info("CPFACCNO :" + bean.getCpfAccno() + " Pensionno :"
						+ bean.getPensionNo() + "wether option:"
						+ bean.getWetherOption());

				String sql3 = "";
				if (bean.getWetherOption() != "") {
					sql3 = "update employee_personal_info set WETHEROPTION='"
							+ bean.getWetherOption().trim()
							+ "' where pensionno='" + bean.getPensionNo() + "'";
					log.info("importPensionOptUpdate" + sql3);
					st.execute(sql3);
				}
				// st.execute(sql3);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importPensionOptUpdate leaving method");

	}

	public void importCPFACCNOUpdate(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :importCPFACCNOUpdate() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.info("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPensionNo(commonUtil.getSearchPFID1(tempInfo[1]
							.trim()));
				} else {
					bean.setPensionNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}

				if (!tempInfo[8].equals("XXX")) {
					bean.setFhName(tempInfo[8].trim());
				} else {
					bean.setFhName("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setWetherOption(tempInfo[9].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setAirportCode(tempInfo[10].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setRegion(tempInfo[11].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setRemarks(tempInfo[12].trim());
				} else {
					bean.setRemarks("");
				}

				if (!(bean.getRemarks().equals("O") || bean.getRemarks()
						.equals("E"))) {
					bean.setWetherOption(bean.getRemarks());
				}
				log.info("CPFACCNO :" + bean.getCpfAccno() + " Pensionno :"
						+ bean.getPensionNo() + "wether option:"
						+ bean.getWetherOption());

				String sql3 = "";
				if (bean.getWetherOption() != "") {
					sql3 = "update employee_personal_info set cpfacno='"
							+ bean.getCpfAccno().trim() + "' where pensionno='"
							+ bean.getPensionNo() + "'";
					log.info("importPensionOptUpdate" + sql3);
					st.execute(sql3);
				}
				// st.execute(sql3);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :importCPFACCNOUpdate leaving method");

	}

	public void UpdateOtherCPFACCNOFields(String xlsData)
			throws InvalidDataException {

		log.info("ImportDao :UpdateOtherCPFACCNOFields() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.info("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[1].equals("XXX")) {
					bean.setPensionNo(commonUtil.getSearchPFID1(tempInfo[1]
							.trim()));
				} else {
					bean.setPensionNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}

				if (!tempInfo[8].equals("XXX")) {
					bean.setFhName(tempInfo[8].trim());
				} else {
					bean.setFhName("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setWetherOption(tempInfo[9].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setAirportCode(tempInfo[10].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setRegion(tempInfo[11].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setRemarks(tempInfo[12].trim());
				} else {
					bean.setRemarks("");
				}

				if (!(bean.getRemarks().equals("O") || bean.getRemarks()
						.equals("E"))) {
					bean.setWetherOption(bean.getRemarks());
				}
				log.info("CPFACCNO :" + bean.getCpfAccno() + " Pensionno :"
						+ bean.getPensionNo() + "wether option:"
						+ bean.getWetherOption());

				String sql3 = "";
				sql3 = "update employee_personal_info set EMPLOYEENAME='"
						+ bean.getEmployeeName().trim() + "',FHNAME='"
						+ bean.getFhName().trim() + "',DATEOFBIRTH='"
						+ bean.getDateOfBirth().trim() + "',DATEOFJOINING='"
						+ bean.getDateOfJoining().trim() + "',DESEGNATION='"
						+ bean.getDesignation().trim() + "',EMPLOYEENO='"
						+ bean.getEmployeeNumber().trim()
						+ "' where pensionno='" + bean.getPensionNo() + "'";
				log.info("UpdateOtherCPFACCNOFields" + sql3);
				st.execute(sql3);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :UpdateOtherCPFACCNOFields leaving method");

	}

	public void autoMapping0809Data(String xlsData, String userName,
			String ipaddress) {

		log.info("ImportDao :autoMapping0809Data() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement transSt = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		ArrayList fnlDataList = new ArrayList();
		FileWriter fw = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", empDbName = "", dateofbirth = "", transQuery = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());
		PreparedStatement pst = null;
		String sqlSelQuery = "", insertQry = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			transSt = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				log.info("tempinfo  " + tempInfo[1].trim());
				if (!tempInfo[2].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[2].trim()));

				} else {
					bean.setPfid("");
				}

				if (!tempInfo[4].equals("XXX")) {
					bean.setRegion(tempInfo[4].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[0].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[0].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setEmpNumber(tempInfo[1].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpName(tempInfo[3].trim());
				} else {
					bean.setEmpName("");
				}
				String lastactive = commonUtil.getCurrentDate("dd-MMM-yyyy");
				log.info("PFID " + bean.getPfid() + "CPFACCNO"
						+ bean.getCpfAcNo() + "region ==" + bean.getRegion()
						+ "employeename" + bean.getEmpName());
				if (bean.getPfid() != "") {
					sqlSelQuery = "select to_char(PENSIONNO),AIRPORTSERIALNUMBER,'"
							+ bean.getEmpNumber()
							+ "', EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,"
							+ "WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,'"
							+ bean.getRegion()
							+ "','"
							+ userName
							+ "','"
							+ bean.getCpfAcNo()
							+ "','"
							+ lastactive
							+ "'"
							+ " FROM employee_personal_info WHERE PENSIONNO="
							+ bean.getPfid();

					if (this.chkEmployeeInfoTbl(conn, bean.getEmpNumber(), bean
							.getCpfAcNo(), bean.getRegion()) == true) {
						insertQry = "UPDATE EMPLOYEE_INFO SET EMPSERIALNUMBER='"
								+ bean.getPfid()
								+ "',dateofbirth= (select dateofbirth from employee_personal_info where pensionno ="
								+ bean.getPfid()
								+ " ),dateofjoining= (select dateofjoining from employee_personal_info where pensionno ="
								+ bean.getPfid()
								+ " ),USERNAME='"
								+ userName
								+ "',lastactive='"
								+ lastactive
								+ "' WHERE cpfacno='"
								+ bean.getCpfAcNo()
								+ "' and region='" + bean.getRegion() + "'";
					} else {
						insertQry = "insert into employee_info(EMPSERIALNUMBER,AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
								+ "sex,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,USERNAME,cpfacno,lastactive)  "
								+ sqlSelQuery;
					}
					transQuery = "UPDATE EMPLOYEE_PENSION_VALIDATE SET PENSIONNO="
							+ bean.getPfid()
							+ " WHERE CPFACCNO='"
							+ bean.getCpfAcNo()
							+ "' AND REGION='"
							+ bean.getRegion() + "'";

					log.info("insert query " + insertQry);
					log.info("insert query " + transQuery);
				}
				st.executeUpdate(insertQry);
				transSt.executeUpdate(transQuery);
				bean.setPfid("");
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeStatement(transSt);
			commonDB.closeConnection(conn, st, rs);
		}

		log.info("ImportDao :North0809AutoMapping leaving method");

	}

	private boolean chkEmployeeInfoTbl(Connection con, String empCode,
			String cpfaccno, String region) {
		log.info("ImportDAO :chkEmployeeInfoTbl() entering method ");
		String foundEmpFlag = "", query = "";
		Statement st = null;
		ResultSet rs = null;
		boolean chkTblFlag = false;
		int count = 0;
		query = "SELECT COUNT(*) as COUNT FROM EMPLOYEE_INFO WHERE empflag='Y' and cpfacno='"
				+ cpfaccno + "' and region='" + region + "'";
		log.info("query is " + query);
		try {

			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				count = rs.getInt("COUNT");
				if (count == 0) {
					chkTblFlag = false;
				} else {
					chkTblFlag = true;
				}

			} else {
				chkTblFlag = false;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, rs);
		}
		log.info("FinanceDAO :checkEmployeeInfo() leaving method"
				+ foundEmpFlag);
		return chkTblFlag;
	}

	public void insertNomineeDtls(String xlsData) throws InvalidDataException {

		log.info("ImportDao :insertNomineeDtls() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.info("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				String nomineeName = "", nomineedob = "", nomineeaddress = "", nomineerelation = "", nameofguardian = "";
				String gaurdianaddress = "", region = "", empflag = "", pensionno = "", srno = "", cpfaccno = "";
				if (!tempInfo[0].equals("XXX")) {
					cpfaccno = tempInfo[0].trim();
				}
				if (!tempInfo[1].equals("XXX")) {
					srno = tempInfo[1].trim();
				}

				if (!tempInfo[2].equals("XXX")) {
					nomineeName = tempInfo[2].trim();
				}
				if (!tempInfo[3].equals("XXX")) {
					nomineedob = tempInfo[3].trim();
				}
				if (!tempInfo[4].equals("XXX")) {
					nomineeaddress = tempInfo[4].trim();
				}
				if (!tempInfo[5].equals("XXX")) {
					nomineerelation = tempInfo[5].trim();
				}
				if (!tempInfo[6].equals("XXX")) {
					nameofguardian = tempInfo[6].trim();
				}
				if (!tempInfo[7].equals("XXX")) {
					gaurdianaddress = tempInfo[7].trim();
				}
				if (!tempInfo[9].equals("XXX")) {
					region = tempInfo[9].trim();
				}
				if (!tempInfo[10].equals("XXX")) {
					empflag = tempInfo[10].trim();
				}
				if (!tempInfo[11].equals("XXX")) {
					pensionno = tempInfo[11].trim();
				}

				String sql3 = "";
				sql3 = "insert into employee_nominee_dtls (cpfaccno,srno,NOMINEENAME,NOMINEEDOB,NOMINEEADDRESS,NOMINEERELATION,NAMEOFGUARDIAN,REGION,PENSIONNO )"
						+ "	 values('"
						+ cpfaccno
						+ "','"
						+ srno
						+ "','"
						+ nomineeName
						+ "','"
						+ nomineedob
						+ "','"
						+ nomineeaddress
						+ "','"
						+ nomineerelation
						+ "','"
						+ nameofguardian
						+ "','"
						+ region
						+ "','"
						+ pensionno
						+ "')";
				log.info("insert nominee dtls " + sql3);
				st.execute(sql3);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :UpdateOtherCPFACCNOFields leaving method");

	}

	public void createEmpUtilzeOldPfid(String xlsData, String region,
			String userName, String ipAddress) throws InvalidDataException {

		log.info("ImportDao :createEmpUtilzeOldPfid() entering method");
		NomineeBean nomineeBean = new NomineeBean();
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement insst = null;
		PreparedStatement pst = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", sql = "", remarksinfo = "", advTransDate = "", empShare = "", aaiShare = "", advPurpose = "", refPensionNo = "";
		int updateEmpinfoCount = 0, updateTransactionCount = 0, totalEmpInfoCount = 0, totalTransCount = 0, personalInsertCount = 0;
		// cpfAccNum = this.getMaxValue();
		// bean.setCpfAcNo(cpfAccNum);
		log.debug("xlsData " + xlsData + "Size" + xlsDataList.size());

		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			String srlno = "";
			for (int i = 1; i < xlsDataList.size(); i++) {
				st = conn.createStatement();
				insst = conn.createStatement();
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				if (!tempInfo[0].equals("XXX")) {
					srlno = tempInfo[0].trim();
				} else {
					srlno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setOldPensionNo(tempInfo[1].trim());
				} else {
					bean.setOldPensionNo("");
				}
				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAccno(tempInfo[2].trim());
				} else {
					bean.setCpfAccno("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmployeeNumber(tempInfo[3].trim());
				} else {
					bean.setEmployeeNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					xlsEmpName = StringUtility.replaces(
							tempInfo[4].trim().toCharArray(), delimiters, "")
							.toString();
					bean.setEmployeeName(xlsEmpName.trim());
				} else {
					bean.setEmployeeName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesignation(tempInfo[5].trim());
				} else {
					bean.setDesignation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					if (tempInfo[6].indexOf(".") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("/") != -1) {
						bean.setDateOfBirth(commonUtil
								.converDBToAppFormat(tempInfo[6].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[6].indexOf("-") != -1) {
						bean.setDateOfBirth(commonUtil.converDBToAppFormat(
								tempInfo[6].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfBirth("");
					}
				} else {
					bean.setDateOfBirth("");
				}
				if (!tempInfo[7].equals("XXX")) {
					if (tempInfo[7].indexOf(".") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd.MM.yy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setDateOfJoining(commonUtil
								.converDBToAppFormat(tempInfo[7].trim(),
										"dd/MM/yyyy", "dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setDateOfJoining(commonUtil.converDBToAppFormat(
								tempInfo[7].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setDateOfJoining("");
					}
				} else {
					bean.setDateOfJoining("");
				}
				log.info(" dob " + bean.getDateOfBirth() + "doj "
						+ bean.getDateOfJoining());
				if (!tempInfo[8].equals("XXX")) {
					bean.setGender(tempInfo[8].trim());
				} else {
					bean.setGender("");
				}

				if (!tempInfo[9].equals("XXX")) {
					bean.setAirportCode(tempInfo[9].trim());
				} else {
					bean.setAirportCode("");
				}
				if (!tempInfo[10].equals("XXX")) {
					bean.setRegion(tempInfo[10].trim());
				} else {
					bean.setRegion("");
				}
				if (!tempInfo[11].equals("XXX")) {
					bean.setWetherOption(tempInfo[11].trim());
				} else {
					bean.setWetherOption("");
				}

				if (!tempInfo[12].equals("XXX")) {
					bean.setSeperationReason(tempInfo[12].trim());
				} else {
					bean.setSeperationReason("");
				}
				if (!tempInfo[13].equals("XXX")) {
					if (tempInfo[13].indexOf(".") != -1) {
						bean
								.setSeperationDate(commonUtil
										.converDBToAppFormat(tempInfo[13]
												.trim(), "dd.MM.yy",
												"dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("/") != -1) {
						bean.setSeperationDate(commonUtil.converDBToAppFormat(
								tempInfo[13].trim(), "dd/MM/yyyy",
								"dd-MMM-yyyy"));
					} else if (tempInfo[7].indexOf("-") != -1) {
						bean.setSeperationDate(commonUtil.converDBToAppFormat(
								tempInfo[13].trim(), "dd-MMM-yyyy",
								"dd-MMM-yyyy"));
					} else {
						bean.setSeperationDate("");
					}

				} else {
					bean.setSeperationDate("");
				}
				if (!tempInfo[14].equals("XXX")) {
					bean.setForm2Nominee(tempInfo[14].trim());
				} else {
					bean.setForm2Nominee("");
				}

				if (!tempInfo[15].equals("XXX")) {
					bean.setEmpDesLevel(tempInfo[15].trim());
				} else {
					bean.setEmpDesLevel("");
				}

				if (!tempInfo[16].equals("XXX")) {
					bean.setFhName(tempInfo[16].trim());
				} else {
					bean.setFhName("");
				}
				if (!tempInfo[17].equals("XXX")) {
					bean.setMaritalStatus(tempInfo[17].trim());
				} else {
					bean.setMaritalStatus("");
				}

				if (!tempInfo[18].equals("XXX")) {
					bean.setPerAddress(tempInfo[18].trim());
				} else {
					bean.setPerAddress("");
				}

				if (!tempInfo[19].equals("XXX")) {
					bean.setTempAddress(tempInfo[19].trim());
				} else {
					bean.setTempAddress("");
				}
				if (!tempInfo[20].equals("XXX")) {
					bean.setDateOfAnnuation(tempInfo[20].trim());
				} else {
					bean.setDateOfAnnuation("");
				}

				if (!tempInfo[21].equals("XXX")) {
					bean.setDivision(tempInfo[21].trim());
				} else {
					bean.setDivision("");
				}

				if (!tempInfo[22].equals("XXX")) {
					bean.setDepartment(tempInfo[22].trim());
				} else {
					bean.setDepartment("");
				}

				if (!tempInfo[23].equals("XXX")) {
					bean.setEmailID(tempInfo[23].trim());
				} else {
					bean.setEmailID("");
				}
				if (!tempInfo[24].equals("XXX")) {
					bean.setEmpNomineeSharable(tempInfo[24].trim());
				} else {
					bean.setEmpNomineeSharable("");
				}
				if (!tempInfo[25].equals("XXX")) {
					bean.setRemarks(tempInfo[25].trim());
				} else {
					bean.setRemarks("");
				}
				if (!tempInfo[26].equals("XXX")) {
					bean.setFhFlag(tempInfo[26].trim());
				} else {
					bean.setFhFlag("");
				}
				if (!tempInfo[27].equals("XXX")) {
					bean.setMappingFlag(tempInfo[27].trim());
				} else {
					bean.setMappingFlag("");
				}
				log.info("Old Pension No" + bean.getOldPensionNo()
						+ "CPF ACCNO" + bean.getCpfAccno());
				log.info("Employee Name" + bean.getEmployeeName()
						+ "Date Of Birth" + bean.getDateOfBirth());
				log.info("DateOfAnnuation" + bean.getDateOfAnnuation()
						+ "Date Of Joining" + bean.getDateOfJoining());
				log.info("Employee No" + bean.getEmployeeNumber()
						+ "Designaiton" + bean.getDesignation() + "Emp Level"
						+ bean.getEmpDesLevel());
				log.info("getSeperationReason" + bean.getSeperationReason()
						+ "SeperationReasonDate" + bean.getSeperationDate()
						+ "Form-2 Nominee" + bean.getForm2Nominee());
				log.info("Remarks" + bean.getRemarks() + "Airportcode"
						+ bean.getAirportCode() + "Gender" + bean.getGender());
				log.info("FatherName" + bean.getFhName() + "Department"
						+ bean.getDepartment() + "MaritalStatus"
						+ bean.getMaritalStatus());

				String uniqueID = "", pensionNo = "";
				String updateEmpSerialNumber = "", updateTransaction = "";

				log.info("Old Pension No" + bean.getOldPensionNo());
				sql = "insert into employee_personal_info(CPFACNO,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,"
						+ "GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,username,LASTACTIVE,RefMonthYear,FHFLAG,MAPINGFLAG,IPAddress)"
						+ " VALUES " + "('"
						+ bean.getCpfAccno().trim()
						+ "','"
						+ bean.getEmployeeNumber().trim()
						+ "','"
						+ bean.getEmployeeName().toUpperCase().trim()
						+ "','"
						+ bean.getDesignation().trim()
						+ "','"
						+ bean.getEmpDesLevel().trim()
						+ "','"
						+ bean.getDateOfBirth().trim()
						+ "','"
						+ bean.getDateOfJoining().trim()
						+ "','"
						+ bean.getSeperationReason().trim()
						+ "','"
						+ bean.getSeperationDate().trim()
						+ "','"
						+ bean.getForm2Nominee().trim()
						+ "','"
						+ bean.getRemarks().trim()
						+ "','"
						+ bean.getAirportCode().trim()
						+ "','"
						+ bean.getGender().trim()
						+ "','"
						+ bean.getFhName().trim()
						+ "','"
						+ bean.getMaritalStatus().trim()
						+ "','"
						+ bean.getPerAddress().trim()
						+ "','"
						+ bean.getTempAddress().trim()
						+ "','"
						+ bean.getWetherOption().trim()
						+ "','"
						+ bean.getDateOfAnnuation().trim()
						+ "','"
						+ bean.getDivision().trim()
						+ "','"
						+ bean.getDepartment().trim()
						+ "','"
						+ bean.getEmailID().trim()
						+ "','"
						+ bean.getEmpNomineeSharable().trim()
						+ "','"
						+ bean.getRegion().trim()
						+ "','"
						+ bean.getOldPensionNo()
						+ "','"
						+ userName
						+ "','"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "','"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "','"
						+ bean.getFhFlag()
						+ "','"
						+ bean.getMappingFlag()
						+ "','" + ipAddress + "')";
				log.info("sql" + sql);

				personalInsertCount = insst.executeUpdate(sql);

				commonDB.closeStatement(insst);
				/*
				 * if(personalInsertCount!=0 &&
				 * !bean.getOldPensionNo().equals("")){ updateEmpinfoCount =
				 * st.executeUpdate(updateEmpSerialNumber);
				 * updateTransactionCount = st
				 * .executeUpdate(updateTransaction); }
				 * commonDB.closeConnection(null,st, null);
				 */
				totalEmpInfoCount = totalEmpInfoCount + updateEmpinfoCount;
				totalTransCount = totalTransCount + updateTransactionCount;

			}
			log.info(" updateTransactionCount" + totalTransCount
					+ "totalEmpInfoCount" + totalEmpInfoCount);
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :createNewPFIDS leaving method");

	}

	public void readXLSFinalSettlement(String xlsData)
			throws InvalidDataException {
		log.info("ImportDao :readXLSFinalSettlement() entering method");
		ArrayList xlsDataList = new ArrayList();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		EmployeePersonalInfo bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "";
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				bean = new EmployeePersonalInfo();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");

				String region = "", pensionno = "", cpfaccno = "", employeeno = "", employeename = "", purpose = "", settlementdate = "", remarks = "", airportcode = "";
				double finemp = 0.00, finaai = 0.00, pencon = 0.00, netamount = 0.00;
				if (!tempInfo[1].equals("XXX")) {
					pensionno = commonUtil.getSearchPFID1(tempInfo[1].trim());
				}
				if (!tempInfo[3].equals("XXX")) {
					cpfaccno = tempInfo[3].trim();
				}
				if (!tempInfo[4].equals("XXX")) {
					employeeno = tempInfo[4].trim();
				}
				if (!tempInfo[5].equals("XXX")) {
					employeename = tempInfo[5].trim();
				}
				if (!tempInfo[8].equals("XXX")) {
					purpose = tempInfo[8].trim();
				}
				if (!tempInfo[9].equals("XXX")) {
					finemp = Double.parseDouble(tempInfo[9].trim());
				}
				if (!tempInfo[10].equals("XXX")) {
					finaai = Double.parseDouble(tempInfo[10].trim());
				}
				if (!tempInfo[11].equals("XXX")) {
					pencon = Double.parseDouble(tempInfo[11].trim());
				}
				if (!tempInfo[12].equals("XXX")) {
					netamount = Double.parseDouble(tempInfo[12].trim());
				}

				if (!tempInfo[13].equals("XXX")) {
					settlementdate = tempInfo[13].trim();
				}

				if (!tempInfo[15].equals("XXX")) {
					airportcode = tempInfo[15].trim();
				}
				log.info(airportcode);
				if (!tempInfo[16].equals("XXX")) {
					region = tempInfo[16].trim();
				}
				String query = "";
				query = "insert into employee_pension_finsettlement(cpfaccno,employeeno,employeename,pensionno,purpose,finemp,finaai,pencon,netamount,settlementdate,airportcode,region)values('"
						+ cpfaccno
						+ "','"
						+ employeeno
						+ "','"
						+ employeename
						+ "','"
						+ pensionno
						+ "','"
						+ purpose
						+ "',"
						+ finemp
						+ ","
						+ finaai
						+ ","
						+ pencon
						+ ","
						+ netamount
						+ ",'"
						+ settlementdate
						+ "','"
						+ airportcode
						+ "','"
						+ region
						+ "')";

				log.info("query " + query);
				st.execute(query);

			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("ImportDao :readXLSFinalSettlement leaving method");

	}

	public String getEmoluments(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region) {

		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yyyy", "-MMM-yy");
			String query = "select emoluments as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "' and empflag='Y' ";

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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
	}

	public void pentionContributionProcess(String xlsData, String region,
			String userName, String ipAddress, String fileName1, String year,
			String month) {

		log.info("ImportDao :pentionContributionProcess() entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", dateofbirth = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String foundSalaryDate = "", retirementDate = "",emppfstatuary="0.00";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
			for (int i = 1; i < xlsDataList.size(); i++) {
				double calculatedPension = 0.00, pf = 0.00;
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				bean.setRegion(region);

				if (!tempInfo[0].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[0].trim()));
				} else {
					bean.setPfid("");
				}
				log.info(bean.getPfid());

				if (!tempInfo[1].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[1].trim());
				} else {
					bean.setCpfAcNo("");
				}

				if (!tempInfo[2].equals("XXX")) {
					bean.setEmpName(tempInfo[2].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[3].equals("XXX")) {
					monthYear = tempInfo[3].trim();
				} else {
					monthYear = "";
				}
				
				if (!tempInfo[5].equals("XXX")) {
					bean.setEmployeePF(tempInfo[5].trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				

				String transMonthYear = "";

				if (!bean.getPfid().trim().equals("")) {

					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date(to_char(add_months(TO_DATE('"
							+ monthYear
							+ "'), -1),'dd-Mon-yyyy'),'DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days,to_char(add_months(dateofbirth, 697),'dd-Mon-yyyy')AS calPensionupto from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs.getString("REIREMENTDATE") != null) {
						retirementDate = rs.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs.getString("dateofbirth") != null) {
						dateofbirth = rs.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs.getString("days") != null) {
						days = rs.getString("days");
					} else {
						days = "0";
					}
					String calPensionupto = "";
					if (rs.getString("calPensionupto") != null) {
						calPensionupto = rs.getString("calPensionupto");
					} else {
						calPensionupto = "0";
					}

					transMonthYear = commonUtil.converDBToAppFormat(
							calPensionupto.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					/*String emolumentsQuery = "select emppfstatuary from employee_pension_validate where pensionno='"
							+ bean.getPfid()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ monthYear.trim() + "'";
					log.info(emolumentsQuery);
					rs2 = st1.executeQuery(emolumentsQuery);
					if (rs2.next()) {
						if (rs2.getString("emppfstatuary") != null) {
							bean.setEmployeePF(rs2.getString("emppfstatuary"));
						}
					} else {
						bean.setEmployeePF("0.00");
					}*/
					Date transdate = null;
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					transdate = df.parse(monthYear);
					float cpfarrear = 0;
					if (transdate.after(new Date("31-Mar-1998"))) {
						bean.setEmoluments(String.valueOf(Float.parseFloat(bean
								.getEmployeePF()) * 100 / 12));
					} else {
						bean.setEmoluments(String.valueOf(Float.parseFloat(bean
								.getEmployeePF()) * 100 / 10));
					}

					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = fdao.calclatedPF(monthYear,
							retirementDate, dateofbirth, bean.getEmoluments(),
							bean.getWetherOption(), "", days,"1");
					calculatedPension = Math.round(calculatedPension);
					pf = Double.parseDouble(bean.getEmployeePF().toString())
							- calculatedPension;
					pf = Math.round(pf);

				}
				// this code snippet is removed from if condition
				// foundSalaryDate.equals("") &&
				if (!bean.getPfid().equals("")) {
					String insertPensionCon = "", updatePensionCon = "";
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(monthYear
							.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertPensionCon = "update employee_pension_validate set pf='"
							+ pf
							+ "',PENSIONCONTRI='"
							+ calculatedPension
							+ "'  where pensionno='"
							+ bean.getPfid()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' AND EMPFLAG='Y' ";

				/*	updatePensionCon = "update employee_pension_validate set PF=EMPPFSTATUARY,PENSIONCONTRI='0.00' where pensionno='"
							+ bean.getPfid()
							+ "' and monthyear>=add_months(to_date('"
							+ monthYear + "','dd-Mon-yyyy'),1) ";*/
					log.info(insertPensionCon);
				//	log.info(updatePensionCon);
					st.executeUpdate(insertPensionCon);
				//	st.executeUpdate(updatePensionCon);
				}

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

	public void pentionContributionProcess2008to11(String region, String Pfid) {
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
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", dateofbirth = "", pensionno = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String foundSalaryDate = "", retirementDate = "";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
			String airportQuery="select nvl(airportcode,'X')as airportcode from epis_bulk_print_pfids where pensionno='"+Pfid+"'";
			rs1=st.executeQuery(airportQuery);
			String airportcode="";
			if(rs1.next()){
				airportcode=rs1.getString("airportcode");
			}
			 CommonUtil cu=new CommonUtil();
			String todate=cu.getDateTime("dd-MMM-yyyy");
			String updateQuery = "select pensionno,to_char(monthyear,'dd-Mon-yyyy') as monthyear from employee_pension_validate where pensionno='"
					+ Pfid
					+ "' and monthyear>=to_date('01-Apr-2009','dd-Mon-yyyy') and monthyear<=to_date('"+todate+"','dd-Mon-yyyy') and edittrans='N' AND empflag='Y'";
			
			rs = st1.executeQuery(updateQuery);

			while (rs.next()) {
				double calculatedPension = 0.00, pf = 0.00;
				bean = new EmpMasterBean();

				if (rs.getString("monthyear") != null) {
					monthYear = rs.getString("monthyear").toString();
				} else {
					monthYear = "";
				}
				if (rs.getString("pensionno") != null) {
					pensionno = rs.getString("pensionno").trim();
				} else {
					pensionno = "";
				}

				String transMonthYear = "";

				if (!pensionno.trim().equals("")) {

					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date(to_char(add_months(TO_DATE('"
							+ monthYear
							+ "'), -1),'dd-Mon-yyyy'),'DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days,to_char(add_months(dateofbirth, 697),'dd-Mon-yyyy')AS calPensionupto from employee_personal_info where to_char(pensionno)='"
							+ pensionno + "'";
					log.info(checkPFID);
					rs1 = st.executeQuery(checkPFID);

					if (!rs1.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs1.getString("wetheroption") != null) {
						bean.setWetherOption(rs1.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs1.getString("REIREMENTDATE") != null) {
						retirementDate = rs1.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs1.getString("dateofbirth") != null) {
						dateofbirth = rs1.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs1.getString("days") != null) {
						days = rs1.getString("days");
					} else {
						days = "0";
					}
					String calPensionupto = "";
					if (rs1.getString("calPensionupto") != null) {
						calPensionupto = rs1.getString("calPensionupto");
					} else {
						calPensionupto = "0";
					}

					transMonthYear = commonUtil.converDBToAppFormat(monthYear
							.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					String emolumentsQuery = "select emoluments,EMPPFSTATUARY,airportcode from employee_pension_validate where pensionno='"
							+ pensionno
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' and edittrans='N' and empflag='Y' order by monthyear";
					log.info(emolumentsQuery);
					rs2 = st.executeQuery(emolumentsQuery);
					
					//code changed as on 25-Oct-2010
					/*if (rs2.next()) {
						
						if (rs2.getString("emoluments") != null) {
							bean.setEmoluments(rs2.getString("emoluments"));
						}
					} else {
						bean.setEmoluments("0.00");
					}*/
					while (rs2.next()) {
						if (rs2.getString("EMPPFSTATUARY") != null) {
							bean.setEmployeePF(rs2.getString("EMPPFSTATUARY"));
						}else{
							bean.setEmployeePF("0.00");
						}
						
						if (rs2.getString("emoluments") != null) {
							bean.setEmoluments(rs2.getString("emoluments"));
						}else {
							bean.setEmoluments("0.00");
						}
						if (rs2.getString("airportcode") != null) {
							bean.setStation(rs2.getString("airportcode"));
						}
					
				
					
					log.info("emoluments" + bean.getEmoluments() + "monthYear "
							+ monthYear);
					Date transdate = null;
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					transdate = df.parse(monthYear);
					float cpfarrear = 0;
					String emolumetns="0";
					if(airportcode.equals("CHQNAD") ||airportcode.equals("OFFICE COMPLEX")){
					if (transdate.after(new Date("31-Mar-1998"))&& transdate.before(new Date("31-Mar-2008"))) {
						bean.setEmployeePF(String.valueOf(Float.parseFloat(bean
								.getEmoluments()) * 12 / 100));
					} else {
						//bean.setEmployeePF(String.valueOf(Float.parseFloat(bean.getEmoluments()) * 10 / 100));
					}
					emolumetns=bean.getEmoluments();
					}else{
						if (transdate.after(new Date("31-Mar-1998"))&& transdate.before(new Date("31-Mar-2008"))) {
							bean.setEmployeePF(String.valueOf(Math.round(Double.parseDouble(bean.getEmoluments()) * 12 / 100)));
							  log.info(" pf is : " + bean.getEmployeePF());
						} else {
						//	bean.setEmployeePF(String.valueOf(Math.round(Double.parseDouble(bean.getEmoluments())* 10 / 100)));
						}	
						emolumetns=String.valueOf(Math.round(Double.parseDouble(bean.getEmoluments())));
					}
         log.info(" pf is : " + bean.getEmployeePF());
					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = fdao.calclatedPF1(monthYear,
							calPensionupto, dateofbirth, emolumetns,
							bean.getWetherOption(), "", days,"1",bean.getEmployeePF());
					if(airportcode.equals("CHQNAD") ||airportcode.equals("OFFICE COMPLEX")){
						calculatedPension =Math.round(calculatedPension);
						pf = Double.parseDouble(bean.getEmployeePF().toString())
						- calculatedPension;
				   //  pf = Math.round(pf);
					}else{
					calculatedPension =Math.round(calculatedPension);
					pf = Double.parseDouble(bean.getEmployeePF())- calculatedPension;
				
					}
					
	
				}
				if (!pensionno.equals("")) {
					String condition="";
					if((bean.getStation()!=null || bean.getStation()=="")){
						condition= "and airportcode='"+bean.getStation()+"'"; 
					}
					String insertPensionCon = "", updatePensionCon = "";
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(monthYear
							.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertPensionCon = "update employee_pension_validate set pf='"
							+ pf
							+ "',PENSIONCONTRI='"
							+ calculatedPension
							+ "'  where pensionno='"
							+ pensionno
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "' "+condition+" and empflag='Y' and edittrans='N'";

					
					log.info(insertPensionCon);
					// log.info(updatePensionCon);
					st.executeUpdate(insertPensionCon);
					
				}

			}

		}} catch (SQLException e) {
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
	public void pentionContributionProcess2008to11(String range,String region,String airportcode, String Pfid,String fromYear,String toYear,FileWriter fw,String checkPendingFlag) {
		
		log.info("ImportDao :pentionContributionProcess2008to11(range,region) entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
	
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", dateofbirth = "", pensionno = "",updateQuery="";
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String foundSalaryDate = "", retirementDate = "";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			st1 = conn.createStatement();
			
			if(checkPendingFlag.equals("false")){
				updateQuery=buildQueryTransInfoPrinting("NO-SELECT",airportcode,region,Pfid,fromYear,toYear);
			}else{
				updateQuery = "select pensionno,to_char(monthyear,'dd-Mon-yyyy') as monthyear from employee_pension_validate where pensionno in(select pensionno from employee_personal_info where to_char(LASTACTIVE,'dd-Mon-yyyy')=to_date(current_date)) "+
				 " and monthyear>=to_date('01-Apr-2008','dd-Mon-yyyy') and monthyear<=to_date('31-May-2010','dd-Mon-yyyy') and edittrans='N' AND empflag='Y' order by pensionno";
			}
			
			rs = st1.executeQuery(updateQuery);
			log.info("pentionContributionProcess2008to11"+updateQuery);
			while (rs.next()) {
				double calculatedPension = 0.00, pf = 0.00;
				bean = new EmpMasterBean();

				if (rs.getString("monthyear") != null) {
					monthYear = rs.getString("monthyear").toString();
				} else {
					monthYear = "";
				}
				if (rs.getString("pensionno") != null) {
					pensionno = rs.getString("pensionno").trim();
				} else {
					pensionno = "";
				}

				String transMonthYear = "";

				if (!pensionno.trim().equals("")) {

					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date(to_char(add_months(TO_DATE('"
							+ monthYear
							+ "'), -1),'dd-Mon-yyyy'),'DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days,to_char(add_months(dateofbirth, 697),'dd-Mon-yyyy')AS calPensionupto from employee_personal_info where to_char(pensionno)='"
							+ pensionno + "'";
					log.info(checkPFID);
					rs1 = st.executeQuery(checkPFID);

					if (!rs1.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs1.getString("wetheroption") != null) {
						bean.setWetherOption(rs1.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs1.getString("REIREMENTDATE") != null) {
						retirementDate = rs1.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs1.getString("dateofbirth") != null) {
						dateofbirth = rs1.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs1.getString("days") != null) {
						days = rs1.getString("days");
					} else {
						days = "0";
					}
					String calPensionupto = "";
					if (rs1.getString("calPensionupto") != null) {
						calPensionupto = rs1.getString("calPensionupto");
					} else {
						calPensionupto = "0";
					}

					transMonthYear = commonUtil.converDBToAppFormat(monthYear
							.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					String emolumentsQuery = "select nvl(emoluments,'0.0') as emoluments from employee_pension_validate where pensionno='"
							+ pensionno
							+ "' and to_char(monthyear,'dd-Mon-yyyy') = '"
							+ monthYear.trim() + "' and edittrans='N' and empflag='Y'";
					log.info(emolumentsQuery);
					rs2 = st.executeQuery(emolumentsQuery);
					if (rs2.next()) {
						if (rs2.getString("emoluments") != null) {
							bean.setEmoluments(rs2.getString("emoluments"));
						}
					} else {
						bean.setEmoluments("0.00");
					}
					log.info("emoluments" + bean.getEmoluments() + "monthYear "
							+ monthYear);
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

					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = fdao.calclatedPF1(monthYear,
							calPensionupto, dateofbirth, bean.getEmoluments(),
							bean.getWetherOption(), "", days,"1",bean.getEmployeePF());

					calculatedPension = Math.round(calculatedPension);
					pf = Double.parseDouble(bean.getEmployeePF().toString())
							- calculatedPension;
					pf = Math.round(pf);

				}
				if (!pensionno.equals("")) {
					String insertPensionCon = "", updatePensionCon = "";
					float basicDaSum = 0;
					transMonthYear = commonUtil.converDBToAppFormat(monthYear
							.trim(), "dd-MMM-yyyy", "-MMM-yyyy");

					insertPensionCon = "update employee_pension_validate set pf='"
							+ pf
							+ "',PENSIONCONTRI='"
							+ calculatedPension
							+ "'  where pensionno='"
							+ pensionno
							+ "' and to_char(monthyear,'dd-Mon-yyyy') ='"
							+ monthYear.trim() + "' ";

					
					log.info(insertPensionCon);
					// log.info(updatePensionCon);
					st.executeUpdate(insertPensionCon);
					fw.write(commonUtil.leadingZeros(5,pensionno)+"==============================="+transMonthYear+"=========================="+pf+"========================"+calculatedPension+ "\n");
				}
				fw.flush();
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
				rs1.close();
				rs2.close();
				st1.close();
				st.close();
				conn.close();
			} catch (Exception e) {

			}
		}

	}
	public String buildQueryTransInfoPrinting(String range,String airportcode, String region,			
			String pensionno, String fromYear,String toYear) {
		log
		.info("ImportDAO::buildQueryTransInfoPrinting-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "";
		int startIndex = 0, endIndex = 0;
		
		sqlQuery = "select pensionno,to_char(monthyear,'dd-Mon-yyyy') as monthyear from employee_pension_validate where monthyear between to_date('"+fromYear+"','dd-Mon-yyyy') and to_date('"+toYear+"','dd-Mon-yyyy') and edittrans='N' AND empflag='Y'";
		if (!range.equals("NO-SELECT")) {
			String[] findRnge = range.split(" - ");
			startIndex = Integer.parseInt(findRnge[0]);
			endIndex = Integer.parseInt(findRnge[1]);
			
			whereClause.append("  (EPV.PFID BETWEEN " + startIndex
					+ " AND " + endIndex + ")");
			whereClause.append(" AND ");
			
		}
		
		if (!region.equals("") && (pensionno.equals(""))) {
			whereClause.append(" REGION ='" + region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("") && (pensionno.equals(""))) {
			whereClause.append(" AIRPORTCODE ='" + airportcode + "'");
			whereClause.append(" AND ");
		}
	
	
			if (!pensionno.equals("")) {
				whereClause.append("PENSIONNO='" + pensionno + "' ");
				whereClause.append(" AND ");
			}
		
		query.append(sqlQuery);
		if ((region.equals("")) && (airportcode.equals(""))
				&& (range.equals("NO-SELECT") && (pensionno.equals("")))) {
			
		} else {
			query.append(" AND ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}
		String orderBy = " ORDER BY PENSIONNO";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
		.info("ImportDAO::buildQueryTransInfoPrinting Leaving Method");
		return dynamicQuery;
	}
	
	public String importArrearsTRData(String xlsData, String region,
			String userName, String ipAddress, String fileName, String year,
			String month, String airportcode) throws InvalidDataException {
		log.info("ImportDao :importArrearTrData() entering method");
		String message = "";
		ArrayList xlsDataList = new ArrayList();
		FinancialReportDAO fDao = new FinancialReportDAO();
		xlsDataList = commonUtil.getTheList(xlsData, "***");
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		EmpMasterBean bean = null;
		String tempInfo[] = null;
		String tempData = "";
		FileWriter fw = null;
		char[] delimiters = { '"', ',', '\'', '*', ',' };
		String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
		String uploadFilePath = "";
		String xlsEmpName = "", monthYear = "", dateofbirth = "",monthYear1="";
		PreparedStatement pst = null;
		ResultSet rs = null;
		String foundSalaryDate = "", retirementDate = "";
		boolean addflag = false;
		boolean updateflag = false;
		int addBatchcount[] = { 0 };
		int updatedBatchCount[]={ 0 };
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.epis.resource.ApplicationResources");
			uploadFilePath = bundle.getString("upload.folder.path");
			File filePath = new File(uploadFilePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			fw = new FileWriter(new File(filePath + "//recordMissing.txt"));
			conn = commonDB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st1 = conn.createStatement();
			String convertedMonthYear = commonUtil.getMonthYear(month, year);
			String lastactiveMonth = "01" + "-" + convertedMonthYear;
			String monthyear = convertedMonthYear;
			log.info("lastactiveMonth" + lastactiveMonth);
			String importMonthStatus = "select FILENAME,IMPORTSTATUS,to_char(MONTHYEAR,'dd-Mon-yyyy')as monthyear FROM payment_recov_mnth_upload_log where  to_char(MONTHYEAR,'dd-Mon-yyyy') like  to_char('%"
					+ lastactiveMonth + "')";
			log.info(importMonthStatus);

			rs = st1.executeQuery(importMonthStatus);
			while (rs.next()) {
				if (rs.getString("IMPORTSTATUS").equals("Y")
						&& rs.getString("FILENAME").equals(fileName)
						&& rs.getString("monthyear").equals(lastactiveMonth)) {
					throw new InvalidDataException(fileName.trim()
							+ " Sheet Already Imported for Salary Month "
							+ month + "-" + year);
				}
			}
			double pensioncontri = 0.00, pf = 0.00;
			String srno = "";
			String payment_recov_mnth_upload_log = "insert into payment_recov_mnth_upload_log(FILENAME,USERNAME,IPADDRESS,AIRPORT,REGION,MONTHYEAR) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(payment_recov_mnth_upload_log);
			pst.setString(1, fileName.trim());
			pst.setString(2, userName.trim());
			pst.setString(3, ipAddress.trim());
			pst.setString(4, " ");
			pst.setString(5, region);
			pst.setString(6, lastactiveMonth.trim());
			pst.executeUpdate();
			String finaceInsert = "insert into employee_pension_validate(cpfaccno,emoluments,emppfstatuary,AIRPORTCODE,"
					+ "MONTHYEAR,employeeName,employeeno,DESEGNATION,region,PENSIONNO,EMPVPF,EMPADVRECPRINCIPAL,EMPADVRECINTEREST,PENSIONCONTRI,pf,FINYEAR,remarks )"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(finaceInsert);

			for (int i = 1; i < xlsDataList.size(); i++) {
				double calculatedPension = 0.00;
				bean = new EmpMasterBean();
				tempData = xlsDataList.get(i).toString();
				tempInfo = tempData.split("@");
				bean.setRegion(region);
				if (!tempInfo[0].equals("XXX")) {
					srno = tempInfo[0];
				} else {
					srno = "";
				}
				if (!tempInfo[1].equals("XXX")) {
					bean.setPfid(commonUtil.getSearchPFID1(tempInfo[1].trim()));
				} else {
					bean.setPfid("");
				}
				
				log.info(bean.getPfid());

				if (!tempInfo[2].equals("XXX")) {
					bean.setCpfAcNo(tempInfo[2].trim());
				} else {
					bean.setCpfAcNo("");
				}
				if (!tempInfo[3].equals("XXX")) {
					bean.setEmpNumber(tempInfo[3].trim());
				} else {
					bean.setEmpNumber("");
				}
				if (!tempInfo[4].equals("XXX")) {
					bean.setEmpName(tempInfo[4].trim());
				} else {
					bean.setEmpName("");
				}
				if (!tempInfo[5].equals("XXX")) {
					bean.setDesegnation(tempInfo[5].trim());
				} else {
					bean.setDesegnation("");
				}
				if (!tempInfo[6].equals("XXX")) {
					bean.setFhName(tempInfo[6].trim());
				} else {
					bean.setFhName("");
				}
				//String monthyear2 = "";
			/*	if (!tempInfo[8].equals("XXX") && !srno.equals("")) {

					log.info(tempInfo[8]);
					String convertedDate = commonUtil
							.convertDateFormat(tempInfo[8].trim());
					log.info("converted date is " + convertedDate);
					int index = tempInfo[8].trim().indexOf("-");
					String count[] = tempInfo[8].split("-");
					log.info(" countlength " + count.length);
					if (index == -1 || count.length != 3) {
						throw new InvalidDataException(
								"PFID "
										+ bean.getPfid().trim()
										+ " doesn't have valide date format(i.e dd-Mon-yyyy) in Column I of the Uploaded sheet ");
					}
					String sheetDateMonthYear = tempInfo[8];
					String sheetDateMonthyear[] = tempInfo[8].split("-");
					// String sheetDateMonthyear[] = tempInfo[8].split("-");
					String sheetMonthYear = sheetDateMonthyear[1] + "-"
							+ sheetDateMonthyear[2];
					if (!monthyear.equals(sheetMonthYear)) {
						String monthyear1[] = tempInfo[8].split("-");
						String month1 = monthyear1[1];
						String year1 = monthyear1[2];
						throw new InvalidDataException(
								"Please make sure that the Salary Month Selected above (i.e "
										+ convertedMonthYear
										+ " is matching with month given in the excel sheet at Column I against PFID "
										+ tempInfo[1].trim() + " ( i.e "
										+ sheetMonthYear + ")");
					}
				}

				if (!tempInfo[8].equals("XXX")
						&& !bean.getPfid().trim().equals("")) {
					String monthyearQuery = "SELECT  to_char(add_months('"
							+ tempInfo[8].trim()
							+ "',1),'dd-Mon-yyyy') as monthyear from dual	";
					log.info(monthyearQuery);

					rs = st1.executeQuery(monthyearQuery);
					if (rs.next()) {
						monthyear2 = rs.getString(1);
					}
				}
				log.info("monthyear 2 " + monthyear2);
*/
				
				if (!tempInfo[7].equals("XXX")) {
					bean.setDateofBirth(tempInfo[7].trim());
				} else {
					bean.setDateofBirth("");
				}

				if (!tempInfo[8].equals("XXX")) {
					monthYear1 = tempInfo[8].trim();
				} else {
					monthYear1 = "";
				}

				String finyear = "";
				//Date transdate = null;
				log.info("monthyear1 " + monthYear1);
				/*if (!tempInfo[8].equals("XXX")
						&& !bean.getPfid().trim().equals("")) {
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					transdate = df.parse(monthYear);
					/*if (transdate.after(new Date("31-Mar-2009"))
							&& transdate.before(new Date("1-Apr-2010"))) {
						finyear = "2009-10";
					} else if (transdate.after(new Date("31-Mar-2010"))
							&& transdate.before(new Date("1-Apr-2011"))) {
						finyear = "2010-11";
					}*/
				/*}*/

				if (!tempInfo[9].equals("XXX")) {
					bean.setEmoluments(tempInfo[9].replaceAll(",", "").trim());
				} else {
					bean.setEmoluments("0.00");
				}

				if (!tempInfo[10].equals("XXX")) {
					bean.setEmployeePF(tempInfo[10].replaceAll(",", "").trim());
				} else {
					bean.setEmployeePF("0.00");
				}
				
				if (!tempInfo[11].equals("XXX")) {
					bean.setEmployeeVPF(tempInfo[11].replaceAll(",", "")
									.trim());
				} else {
					bean.setEmployeeVPF("0.00");
				}
				if (!tempInfo[12].equals("XXX")) {
					bean.setPrincipal(tempInfo[12].replaceAll(",", "").trim());
				} else {
					bean.setPrincipal("0.00");
				}
				if (!tempInfo[13].equals("XXX")) {
					bean.setInterest(tempInfo[13].replaceAll(",", "").trim());
				} else {
					bean.setInterest("0.00");
				}

				if (!tempInfo[18].equals("XXX")) {
					bean.setStation(tempInfo[18].trim());
				} else {
					bean.setStation("");
				}
				if (airportcode != null && !bean.getPfid().trim().equals("")
						&& region.equals("CHQIAD")) {
					log.info("sheet airportcode "
							+ bean.getStation().replaceAll(" ", "")
							+ " selected airportcode "
							+ airportcode.replaceAll(" ", "") + "srno " + srno);
					if (!bean.getStation().replaceAll(" ", "").equals(
							airportcode.replaceAll(" ", ""))
							&& !srno.equals("")) {
						throw new InvalidDataException(
								"Please make sure that the Seleted Airportcode above  (i.e "
										+ airportcode
										+ ") and is matching with AirportCode given in the excel sheet at Column 18. i.e"
										+ bean.getStation());
					}
				}
				log.info("cpfaccno " + bean.getCpfAcNo() + " " + bean.getPfid()
						+ "bean.getPfid().trim().equals()"
						+ bean.getPfid().trim().equals("")
						+ "bean.getCpfAcNo().trim().equals()"
						+ bean.getCpfAcNo().trim().equals(""));
				if(!srno.equals("")&& bean.getPfid().trim().equals("")){
					throw new InvalidDataException("PFID "
							+ bean.getPfid().trim() + " doesn't Exist"
							+ " for  Employee " + bean.getEmpName()
							+ ". PFID is Mandatory for All The Employees");	
				}
				if (!bean.getPfid().trim().equals("")) {
					// if(!tempInfo[1].equals("XXX")){
					String checkPFID = "select wetheroption,pensionno, to_char(add_months(dateofbirth, 696),'dd-Mon-yyyy')AS REIREMENTDATE,to_char(dateofbirth,'dd-Mon-yyyy') as dateofbirth,to_date('"
							+ tempInfo[8]
							+ "','DD-Mon-RRRR')-to_date(add_months(TO_DATE(dateofbirth), 696),'dd-Mon-RRRR')+1 as days from employee_personal_info where to_char(pensionno)='"
							+ bean.getPfid() + "'";
					log.info(checkPFID);
					rs = st1.executeQuery(checkPFID);

					if (!rs.next()) {
						throw new InvalidDataException("PFID "
								+ bean.getPfid().trim() + " doesn't Exist"
								+ " for  Employee " + bean.getEmpName()
								+ ". PFID is Mandatory for All The Employees");
					}
					if (rs.getString("wetheroption") != null) {
						bean.setWetherOption(rs.getString("wetheroption"));
					} else {
						bean.setWetherOption("");
					}
					if (rs.getString("REIREMENTDATE") != null) {
						retirementDate = rs.getString("REIREMENTDATE");
					} else {
						retirementDate = "";
					}
					if (rs.getString("dateofbirth") != null) {
						dateofbirth = rs.getString("dateofbirth");
					} else {
						dateofbirth = "";
					}
					String days = "0";
					if (rs.getString("days") != null) {
						days = rs.getString("days");
					} else {
						days = "0";
					}

					if (tempInfo[1].equals("XXX")) {
						throw new InvalidDataException("PFID is Blank for "
								+ bean.getCpfAcNo());
					}

					log.info(" Principle " + bean.getPrincipal() + "interest  "
							+ bean.getInterest() + " vpf "
							+ bean.getEmployeeVPF());

					foundSalaryDate = this.checkFinanceDuplicate1(conn,
							monthYear1, bean.getCpfAcNo(), bean
									.getEmpNumber(), bean.getRegion(), bean
									.getPfid(), airportcode, "");
					FinancialReportDAO fdao = new FinancialReportDAO();
					calculatedPension = fdao.calclatedPF(monthYear1,
							retirementDate, dateofbirth, bean.getEmoluments(),
							bean.getWetherOption(), "", days,"1");
									
					pf = Double.parseDouble(bean.getEmployeePF().toString())
							- calculatedPension;

				}
				
				if (foundSalaryDate.equals("") && !bean.getPfid().equals("") && !srno.equals("")) {
					pst.setString(1, bean.getCpfAcNo().trim());
					pst.setString(2, bean.getEmoluments().trim());
					pst.setString(3, bean.getEmployeePF().trim());
					pst.setString(4, bean.getStation());
					pst.setString(5, monthYear1.trim());
					pst.setString(6, bean.getEmpName().trim());
					pst.setString(7, bean.getEmpNumber().trim());
					pst.setString(8, bean.getDesegnation().trim());
					pst.setString(9, bean.getRegion().trim());
					pst.setString(10, bean.getPfid().trim());
					pst.setString(11, bean.getEmployeeVPF().trim());
					pst.setString(12, bean.getPrincipal().trim());
					pst.setString(13, bean.getInterest().trim());
					pst.setDouble(14, calculatedPension);
					pst.setDouble(15, pf);
					pst.setString(16, finyear);
					pst.setString(17, "arrears amount added "+ bean.getEmoluments());
					log.info("cpfaccno " + bean.getCpfAcNo() + " emoluments "
							+ bean.getEmoluments() + "," + bean.getEmployeePF()
							+ "PFID " + bean.getPfid() + ","
							+ bean.getEmpName() + "," + bean.getEmpNumber()
							+ "," + bean.getDesegnation());
					// pst.executeUpdate();
					pst.addBatch();
					// pst.close();
					addflag = true;
				} else if (!bean.getPfid().equals("") && !srno.equals("")) {
					String insertArrearCount = "";
					float basicDaSum = 0;
					String transMonthYear = commonUtil.converDBToAppFormat(
							monthYear1.trim(), "dd-MMM-yyyy", "-MMM-yyyy");
					if(bean.getWetherOption().equals("B") && String.valueOf(calculatedPension).equals("541")){
						calculatedPension=0;
						log.info("calculatedPension testing for wetheroption");
					}
					insertArrearCount = "update employee_pension_validate set emoluments=to_char(TO_NUMBER(nvl(emoluments,0)+'"
							+ bean.getEmoluments()
							+ "')) , EMPPFSTATUARY=to_char(TO_NUMBER(nvl(EMPPFSTATUARY,0)+'"
							+ bean.getEmployeePF().trim()
							+ "')),EMPADVRECPRINCIPAL=to_char(TO_NUMBER(nvl(EMPADVRECPRINCIPAL,0)+'"
							+ bean.getPrincipal().trim()
							+ "')),EMPADVRECINTEREST= to_char(TO_NUMBER(nvl(EMPADVRECINTEREST,0)+'"
							+ bean.getInterest().trim()
							+ "')),EMPVPF=to_char(TO_NUMBER(nvl(EMPVPF,0)+'"
							+ bean.getEmployeeVPF().trim()
							+ "')),PENSIONCONTRI=to_char(TO_NUMBER(nvl(PENSIONCONTRI,0)+'"+calculatedPension
							+"')),PF=to_char(TO_NUMBER(nvl(PF,0)+'"+pf
							+"')),remarks='arrears amount added "+ bean.getEmoluments()+"'  where region='"
							+ bean.getRegion().trim()
							+ "' and airportcode='"
							+ bean.getStation()
							+ "' and cpfaccno='"
							+ bean.getCpfAcNo().trim()
							+ "' and to_char(monthyear,'dd-Mon-yyyy') like '%"
							+ transMonthYear + "'";
					st.addBatch(insertArrearCount);
log.info("update query="+insertArrearCount);
					updateflag = true;
				}
				// }
			}

			if (addflag) {
				addBatchcount = pst.executeBatch();
				log.info("Inserted Records Count is " + addBatchcount.length);
			}
			if (updateflag) {
				 updatedBatchCount = st.executeBatch();
				log	.info("Updated Records count is " + updatedBatchCount.length);
			}
			String importStausupdate = "update payment_recov_mnth_upload_log  set IMPORTSTATUS='Y' where filename='"
					+ fileName + "'";
			st.executeUpdate(importStausupdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw new InvalidDataException(e.getMessage());
		} finally {
			try {
				st1.close();
				st.close();
				pst.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}

		log.info("ImportDao :importArrearTrData leaving method");
		if(addflag==true){
		message = String.valueOf(addBatchcount.length);
		}else if(updateflag==true){
			message = String.valueOf(updatedBatchCount.length);
		}
		return message;
	}
	public String checkFinanceDuplicate3(Connection con, String fromDate,
			String cpfaccno, String employeeNo, String region,String airportcode) {

		// log.info("PensionDAO :checkPensionDuplicate() entering method ");
		String foundEmpFlag = "";
		Statement st = null;

		ResultSet rs = null;
		// System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
		/** for Eastern Region and Northern region* */
		try {
			String transMonthYear = commonUtil.converDBToAppFormat(fromDate
					.trim(), "dd-MMM-yyyy", "-MMM-yy");
			String query="";
			if (region.equals("CHQIAD")) {
			 query = "select CPFACCNO as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "' and airportcode='" + airportcode + "' and empflag='Y' ";
			}else{
				 query = "select CPFACCNO as COLUMNNM from employee_Pension_validate where to_char(monthyear,'dd-Mon-yy') like '%"
					+ transMonthYear
					+ "' and cpfaccno='"
					+ cpfaccno
					+ "' and region='" + region + "' and empflag='Y' ";
			}
			st = con.createStatement();
			rs = st.executeQuery(query);
            log.info("query"+query);
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
		// log.info("PensionDAO :checkPensionDuplicate() leaving method");
		return foundEmpFlag;
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

			commonDB.closeConnection(null,st,rs);
		}
		
		return arrearamt;
	}
}
