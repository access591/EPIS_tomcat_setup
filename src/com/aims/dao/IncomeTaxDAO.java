package com.aims.dao;

/**
 * @author naveenk
 *
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Properties;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import com.epis.utilities.ActivityLog;
import com.epis.utilities.ApplicationException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.DateConverter;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;
import com.aims.info.incometax.EmpMiscITInfo;
import com.aims.info.incometax.IncomeTaxSlabInfo;
import com.aims.info.incometax.ProjectedITDetail;
import com.aims.info.incometax.ProjectedIncomeTaxInfo;
import com.aims.info.incometax.SBILendingRatesInfo;
import com.aims.info.incometax.SavingsDeclarationInfo;
import com.aims.info.payrollprocess.EmpPaySlipInfo;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.staffconfiguration.EmployeeInfo;
import com.aims.info.accountsetup.FinancialYearInfo;
import com.aims.info.incometax.ProfessionalTaxSlabInfo;

public class IncomeTaxDAO {
	Properties prop = null;

	DBAccess db = DBAccess.getInstance();

	DateConverter dc = new DateConverter();

	SQLHelper sh = new SQLHelper();

	Connection con = null;

	ResultSet rs = null;

	public String checkNull(String str) {
		return str != null ? str.trim() : "";
	}

	private IncomeTaxDAO() {
		prop = CommonUtil.getPropsFile(
			Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,
			IncomeTaxDAO.class);
	}

	private static IncomeTaxDAO slabDAO = new IncomeTaxDAO();

	public static IncomeTaxDAO getInstance() {
		return slabDAO;
	}

	Log log = new Log(IncomeTaxDAO.class);

	public List selectTaxYearCd() {
		log.info("Inside Slab DAO selectTaxYear()");
		List list = new ArrayList();
		String Qry = "";
		try {
			db.makeConnection();
			con = db.getConnection();
			Qry = "SELECT FYEARCD,TO_CHAR(FROMDATE,'DD/Mon/YY') FROMDATE, TO_CHAR(TODATE,'DD/Mon/YY') TODATE FROM FINANCIALYEAR where status='A' order by FROMDATE";
			rs = db.getRecordSet(Qry);
			while (rs.next()) {
				FinancialYearInfo finyearinfo = new FinancialYearInfo();
				finyearinfo.setFinyearCd(rs.getString("FYEARCD"));
				finyearinfo.setFinyearDesc(rs.getString("FROMDATE") + " - "
					+ rs.getString("TODATE"));
				list.add(finyearinfo);
			}
			// log.info("List in selectTaxYearCd() : " + list);
		} catch (Exception e) {
			log.info("Exception in catch, selectTaxYearCd() : "
				+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, selectTaxYear() : "
					+ e.getMessage());
			}
		}
		return list;
	}

	public void saveSlab(IncomeTaxSlabInfo slabInfo) {
		int slabMtCd = 0;
		int slabDtCd = 0;
		String name="";
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String insMasterQry = "";
			String[] taxDetails = slabInfo.getTaxDetails();
			slabMtCd = Integer.parseInt(AutoGeneration.getNextCode(
				"INCOMETAXSLABMASTER", "TAXSLABCD", 30, con));
			insMasterQry = "INSERT INTO INCOMETAXSLABMASTER(TAXSLABCD,FYEARCD,USERCD,ASSESSETYPE) VALUES('"
				+ slabMtCd
				+ "','"
				+ slabInfo.getTaxYear()
				+ "','"
				+ slabInfo.getUserCd()
				+ "','"
				+ slabInfo.getAssesseType()
				+ "')";
			db.executeUpdate(insMasterQry);
			for (int i = 0; i < taxDetails.length; i++) {
				String insDetailsQry = "";
				StringTokenizer st = new StringTokenizer(taxDetails[i], "|");
				while (st.hasMoreTokens()) {
					String fixedAmount = st.nextToken();
					String fromAmount = st.nextToken();
					String toAmount = st.nextToken();
					String taxPercent = st.nextToken();
					String deductionAmount = st.nextToken();
					slabDtCd = Integer.parseInt(AutoGeneration.getNextCode(
						"INCOMETAXSLABDETAILS", "TAXSLABDETCD", 10, con));
					insDetailsQry = "INSERT INTO INCOMETAXSLABDETAILS(TAXSLABDETCD,TAXSLABCD,FIXEDAMOUNT,FROMAMOUNT,TOAMOUNT,TAXPERCENT,DEDUCTIONAMT) VALUES('"
						+ slabDtCd
						+ "','"
						+ slabMtCd
						+ "','"
						+ fixedAmount
						+ "','"
						+ fromAmount
						+ "','"
						+ toAmount
						+ "','"
						+ taxPercent + "','" + deductionAmount + "')";
					db.executeUpdate(insDetailsQry);
				}
			}
			String qry="select fyearnm from financialyear where fyearcd='"+slabInfo.getTaxYear()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next()){
				 name=rs.getString("FYEARNM");}
			String activityDesc="Income Tax Slab New Of ASSESSETYPE---> "+slabInfo.getAssesseType()+" For Financial Year "+name+" Saved.";
			ActivityLog.getInstance().write(slabInfo.getUserCd(), activityDesc, "N", String.valueOf(slabMtCd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch, saveSlab() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, saveSlab() : "
					+ e.getMessage());
			}
		}
	}

	public List searchSlab(IncomeTaxSlabInfo searchSlabInfo) {
		List slabList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();
			String searhSlabquery = "";
			searhSlabquery = "SELECT TAXSLABCD,DECODE(ASSESSETYPE,'M','Male','F','Female','S','Senior Citizen')ASSESSETYPE,FYEARCD,DECODE(STATUS,'A','Active','Inactive')STATUS FROM INCOMETAXSLABMASTER WHERE TAXSLABCD IS NOT NULL ";
			if (!searchSlabInfo.getAssesseType().trim().equals("")) {
				searhSlabquery += " AND ASSESSETYPE='"
					+ searchSlabInfo.getAssesseType() + "'";
			}
			if (!searchSlabInfo.getTaxYear().equals("")) {
				searhSlabquery += " AND FYEARCD='"
					+ searchSlabInfo.getTaxYear() + "'";
			}
			if (!searchSlabInfo.getStatus().equals("")) {
				searhSlabquery += " AND STATUS='" + searchSlabInfo.getStatus()
					+ "'";
			}
			searhSlabquery += " ORDER BY TAXSLABCD ";
			rs = db.getRecordSet(searhSlabquery);
			IncomeTaxSlabInfo slabInfo;
			while (rs.next()) {
				String datesQry = "";
				ResultSet datesRs = null;
				datesQry = " SELECT FYEARCD,TO_CHAR(FROMDATE,'dd/Mon/yyyy')FROMDATE,TO_CHAR(TODATE,'dd/Mon/yyyy')TODATE FROM FINANCIALYEAR ";
				datesRs = db.getRecordSet(datesQry);
				slabInfo = new IncomeTaxSlabInfo();
				slabInfo.setSlabCd(rs.getString("TAXSLABCD"));
				while (datesRs.next()) {
					if (datesRs.getString("FYEARCD").equals(
						rs.getString("FYEARCD"))) {
						slabInfo.setFinYear(datesRs.getString("FROMDATE")
							+ " - " + datesRs.getString("TODATE"));

					}
				}
				slabInfo.setTaxYear(rs.getString("FYEARCD"));
				slabInfo.setAssesseType(rs.getString("ASSESSETYPE"));
				slabInfo.setStatus(rs.getString("STATUS"));
				slabList.add(slabInfo);
			}
		} catch (Exception e) {
			log.info("Exception in catch, searchSlab() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , searchSlab() : "
					+ e.getMessage());
			}
		}
		return slabList;
	}

	public List editDetSlab(IncomeTaxSlabInfo slabInfo) {
		List list = new ArrayList();
		try {
			IncomeTaxSlabInfo info = null;
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String editSlabSql = "SELECT TAXSLABDETCD,FIXEDAMOUNT,FROMAMOUNT,TOAMOUNT,TAXPERCENT,DEDUCTIONAMT FROM INCOMETAXSLABDETAILS WHERE TAXSLABCD='"
				+ slabInfo.getSlabCd() + "' ORDER BY FROMAMOUNT";
			rs = db.getRecordSet(editSlabSql);
			while (rs.next()) {
				info = new IncomeTaxSlabInfo();
				info.setTaxSlabDtCd(rs.getString("TAXSLABDETCD"));
				info.setFixedAmt(rs.getString("FIXEDAMOUNT"));
				info.setFromAmount(rs.getString("FROMAMOUNT"));
				info.setToAmount(rs.getString("TOAMOUNT"));
				info.setTaxPercent(rs.getString("TAXPERCENT"));
				info.setDeductionAmt(rs.getString("DEDUCTIONAMT"));
				list.add(info);
			}
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch, editSlab() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, editSlab() : "
					+ e.getMessage());
			}
		}
		return list;
	}

	public List editMastSlab(IncomeTaxSlabInfo slabInfo) {
		List list = new ArrayList();
		try {
			IncomeTaxSlabInfo info = null;
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String editMasSlabSql = "SELECT TAXSLABCD,ASSESSETYPE,FYEARCD,STATUS FROM INCOMETAXSLABMASTER WHERE TAXSLABCD='"
				+ slabInfo.getSlabCd() + "'";
			rs = db.getRecordSet(editMasSlabSql);
			while (rs.next()) {
				info = new IncomeTaxSlabInfo();
				info.setSlabCd(rs.getString("TAXSLABCD"));
				info.setAssesseType(rs.getString("ASSESSETYPE"));
				info.setTaxYear(rs.getString("FYEARCD"));
				info.setStatus(rs.getString("STATUS"));
				list.add(info);
			}
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch, editMastSlab() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, editMastSlab() : "
					+ e.getMessage());
			}
		}
		return list;
	}

	public void updateSlab(IncomeTaxSlabInfo slabInfo) {
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String updateMasterQry = "";
			String updateDetailsQry = "";
			String insertDetailsQry = "";
			updateMasterQry = "UPDATE INCOMETAXSLABMASTER SET ASSESSETYPE='"
				+ slabInfo.getAssesseType() + "',FYEARCD='"
				+ slabInfo.getTaxYear() + "',STATUS='" + slabInfo.getStatus()
				+ "' WHERE TAXSLABCD='" + slabInfo.getSlabCd() + "'";
			db.executeUpdate(updateMasterQry);
			String[] taxDetails = slabInfo.getTaxDetails();
			for (int i = 0; i < taxDetails.length; i++) {
				int slabMtCd = 0;
				int slabDtCd = 0;
				StringTokenizer st = new StringTokenizer(taxDetails[i], "|");
				while (st.hasMoreTokens()) {
					String taxSlabDtCd = st.nextToken();
					String fixedAmount = st.nextToken();
					String fromAmount = st.nextToken();
					String toAmount = st.nextToken();
					String taxPercent = st.nextToken();
					String deductionAmount = st.nextToken();
					if (taxSlabDtCd.equals("N")) {
						slabMtCd = Integer.parseInt(slabInfo.getSlabCd());
						slabDtCd = Integer.parseInt(AutoGeneration.getNextCode(
							"INCOMETAXSLABDETAILS", "TAXSLABDETCD", 10, con));
						insertDetailsQry = "INSERT INTO INCOMETAXSLABDETAILS (TAXSLABCD,TAXSLABDETCD,FIXEDAMOUNT,FROMAMOUNT,TOAMOUNT,TAXPERCENT,DEDUCTIONAMT) VALUES('"
							+ slabMtCd
							+ "','"
							+ slabDtCd
							+ "','"
							+ fixedAmount
							+ "','"
							+ fromAmount
							+ "','"
							+ toAmount
							+ "','"
							+ taxPercent + "','" + deductionAmount + "')";
						db.executeUpdate(insertDetailsQry);
					} else {
						updateDetailsQry = "UPDATE INCOMETAXSLABDETAILS SET FIXEDAMOUNT='"
							+ fixedAmount
							+ "',DEDUCTIONAMT='"
							+ deductionAmount
							+ "',FROMAMOUNT='"
							+ fromAmount
							+ "',TOAMOUNT='"
							+ toAmount
							+ "',TAXPERCENT='"
							+ taxPercent
							+ "' WHERE TAXSLABDETCD='"
							+ taxSlabDtCd + "'";
						db.executeUpdate(updateDetailsQry);
					}

				}
			}
			String activityDesc="Income Tax Slab Of ASSESSETYPE "+slabInfo.getAssesseType()+" For Financial Year "+slabInfo.getTaxYear()+" Updated.";
			ActivityLog.getInstance().write(slabInfo.getUserCd(), activityDesc, "E", String.valueOf( slabInfo.getSlabCd()), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch, updateSlab() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, updateSlab() : "
					+ e.getMessage());
			}
		}
	}

	public void validateAssTaxYear(IncomeTaxSlabInfo slabInfo) throws Exception {
		ResultSet rs;
		db.makeConnection();
		con = db.getConnection();
		String searchAssTaxYearquery = "";
		String taxSlabCd = "";
		String taxYearCd = "";
		String assesseType = "";
		searchAssTaxYearquery = "SELECT TAXSLABCD,FYEARCD,ASSESSETYPE FROM INCOMETAXSLABMASTER";
		rs = db.getRecordSet(searchAssTaxYearquery);
		System.out.println("slabInfo.getMode() : " + slabInfo.getMode());
		if (slabInfo.getMode().equals("E")) {
			while (rs.next()) {
				System.out.println("For Edit");
				taxSlabCd = rs.getString("TAXSLABCD");
				taxYearCd = rs.getString("FYEARCD");
				assesseType = rs.getString("ASSESSETYPE");
				if (taxYearCd.equals(slabInfo.getTaxYear())
					&& assesseType.equals(slabInfo.getAssesseType())
					&& !taxSlabCd.equals(slabInfo.getSlabCd())) {
					throw new Exception(
						"Slab already defined for these Financial Year and Assesse Type");
				}
			}
		} else {
			while (rs.next()) {
				System.out.println("For New");
				taxYearCd = rs.getString("FYEARCD");
				assesseType = rs.getString("ASSESSETYPE");
				if (taxYearCd.equals(slabInfo.getTaxYear())
					&& assesseType.equals(slabInfo.getAssesseType())) {
					throw new Exception(
						"Slab already defined for these Financial Year and Assesse Type");
				}
			}
		}
	}

	public List getDates(IncomeTaxSlabInfo slabInfo) {
		List slabList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();
			String datesQry = "";
			ResultSet datesrs = null;
			System.out.println("In details : " + slabInfo.getTaxYear());
			datesQry = " SELECT FYEARCD,TO_CHAR(FROMDATE,'dd/Mon/yyyy')FROMDATE,TO_CHAR(TODATE,'dd/Mon/yyyy')TODATE FROM FINANCIALYEAR WHERE FYEARCD='"
				+ slabInfo.getTaxYear() + "'";
			System.out.println(datesQry);
			datesrs = db.getRecordSet(datesQry);
			while (datesrs.next()) {
				slabInfo.setFromDate(datesrs.getString("FROMDATE"));
				slabInfo.setToDate(datesrs.getString("TODATE"));
				slabList.add(slabInfo);
			}
		} catch (Exception e) {
			log.info("Exception in catch, getDates() : " + e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , getDates() : "
					+ e.getMessage());
			}
		}
		return slabList;
	}

	// Savings Declaraion Done By Srilakshmi 15/09/09
	public List getAllSavings() {
		List slist = new ArrayList();
		SavingsDeclarationInfo info = new SavingsDeclarationInfo();
		String sSQL = "";
		try {
			db.makeConnection();
			con = db.getConnection();
			sSQL = "select SAVINGSCD,SAVINGNAME from savingsmaster";
			rs = db.getRecordSet(sSQL);
			
			
			while (rs.next()) {
				info = new SavingsDeclarationInfo();
				info.setSavingsCd(rs.getString("SAVINGSCD"));
				info.setSavingsName(rs.getString("SAVINGNAME"));
				slist.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch, getAllSavings() : " + e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , getAllSavings() : "
					+ e.getMessage());
			}
		}
		return slist;
	}

	public void addSavingsDeclaration(SavingsDeclarationInfo info)
		throws ApplicationException {
		String sSQL = "";
		String detStr = "";
		String name="";
		double totSavings = 0.0;
		StringBuffer detQry = new StringBuffer();
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			int cnt = 0;
			cnt = db
				.getRecordCount(" select count(*) from savingsdeclartion where empno='"
					+ info.getEmpno()
					+ "' and fyearcd='"
					+ info.getFinyearcd()
					+ "' ");
			if (cnt > 0) {
				throw new ApplicationException(
					"Savings Declaration has been already submitted for the selected Employee and Financial Year");
			}
			int cd = Integer.parseInt(AutoGeneration.getNextCode(
				"savingsdeclartion", "savdeclcd", 10, con));
			sSQL = "Insert into savingsdeclartion(savdeclcd,FYEARCD,empno,Status) values('"
				+ cd
				+ "','"
				+ info.getFinyearcd()
				+ "','"
				+ info.getEmpno()
				+ "','A')";
			db.executeUpdate(sSQL);
			String[] detArray = info.getDetArray();
			int arrLen = detArray.length;
			int detcd = 0;
			for (int i = 0; i < arrLen; i++) {
				detStr = detArray[i];
				log.info("Details string is ---" + i + "---- " + detStr);
				detQry.delete(0, detQry.length());

				String[] details = getStringArrayFromStr(detStr, "|");// detStr.split("|");

				log.info("details length is ======= " + details.length);
				// if(!"8".equals(details[0])){
				detcd = Integer.parseInt(AutoGeneration.getNextCode(
					"savingsdeclartiondet", "savdecdetcd", 10, con));
				if ("12".equals(details[0]))
					detQry.append("Insert into savingsdeclartiondet values ('"
						+ detcd + "','" + cd + "','" + details[0] + "','"
						+ details[1] + "','" + details[2] + "','" + details[3]
						+ "')");
				else
					detQry
						.append("Insert into savingsdeclartiondet(SAVDECDETCD,SAVDECLCD,SAVINGSCD,NAME,SAVINGAMT) values ('"
							+ detcd
							+ "','"
							+ cd
							+ "','"
							+ details[0]
							+ "','"
							+ details[1] + "','" + details[2] + "')");
				log.info("detQry in otherthan NSC " + detQry.toString());
				db.executeUpdate(detQry.toString());
				// }
				/*
				 * else if("8".equals(details[0])){ int detcd1 =
				 * Integer.parseInt(AutoGeneration.getNextCode("savingsdeclartiondet",
				 * "savdecdetcd", 10, con)); detQry.append("Insert into
				 * savingsdeclartiondet(SAVDECDETCD,SAVDECLCD,SAVINGSCD) values
				 * ('"+detcd1+"','"+cd+"','"+details[0]+"')"); log.info("Master
				 * Detail in NSC "+detQry.toString());
				 * db.executeUpdate(detQry.toString());
				 * detQry.delete(0,detQry.length()); String[] nscFcdArray =
				 * getStringArrayFromStr(details[1],":");//details[1].split(":");
				 * String[] nscAmtArray =
				 * getStringArrayFromStr(details[2],":");//details[2].split(":");
				 * String[] nscIntrArray =
				 * getStringArrayFromStr(details[3],":");//)details[3].split(":");
				 * double nscsavings = 0.0; for(int k=0;k<nscFcdArray.length;k++){
				 * detQry.delete(0,detQry.length()); detcd =
				 * Integer.parseInt(AutoGeneration.getNextCode("SAVINGSNSCDET",
				 * "SAVINGSNSCDETCD", 10, con)); detQry.append("Insert into
				 * SAVINGSNSCDET values ('"+detcd+"','"+cd+"',");
				 * detQry.append("'"+nscFcdArray[k]+"','"+nscAmtArray[k]+"','"+nscIntrArray[k]+"'");
				 * detQry.append(")"); log.info(" Detail Query in NSC
				 * "+detQry.toString()); nscsavings +=
				 * Double.parseDouble(nscAmtArray[k]);
				 * db.executeUpdate(detQry.toString()); } sSQL = " Update
				 * savingsdeclartiondet set SAVINGAMT="+nscsavings+" where
				 * SAVDECDETCD='"+detcd1+"' "; db.executeUpdate(sSQL); }
				 */
				// log.info("Details Savings Amount is ---------- "+details[2]);
				// totSavings += Double.parseDouble(details[2]);
			}
			// sSQL = " Update savingsdeclartion set totalsaving="+totSavings+"
			// where savdeclcd='"+cd+"' ";
			// db.executeUpdate(sSQL);
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpno()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Savings Declaration  New Of "+name+" Saved.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "N", String.valueOf(cd), con);
			db.commitTrans();
			log.info("Total Savings Amount is ---------- " + totSavings);
		} catch (Exception e) {
			try {
				db.rollbackTrans();
				throw new ApplicationException(e.toString());
			} catch (SQLException e1) {
				log
					.info("Exception in Rollback Trans , addSavingsDeclaration() : "
						+ e1.getMessage());
				e1.printStackTrace();
			}
			log.info("Exception in catch , addSavingsDeclaration() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , addSavingsDeclaration() : "
						+ e.getMessage());
			}
		}
	}

	public List searchSavingsDeclaration(String empno) {
		List slist = new ArrayList();
		SavingsDeclarationInfo info = new SavingsDeclarationInfo();
		String sSQL = "";
		try {
			db.makeConnection();
			con = db.getConnection();
			sSQL = "select m.SAVDECLCD SAVDECLCD, (select to_char(FROMDATE,'dd/Mon/yyyy')||' - '||to_char(TODATE,'dd/Mon/yyyy') from financialyear f "
				+ "where f.fyearcd=m.fyearcd)FYEARCD,(select EMPLOYEENAME from employeeinfo e"
				+ " where e.empno=m.empno)empname from savingsdeclartion m where  status='A' and m.SAVDECLCD  is not null ";
			if (!"".equals(empno)) {
				sSQL += " and empno='" + empno + "'";
			}
			log.info("searchSavingsDeclaration Qry is --- " + sSQL);
			rs = db.getRecordSet(sSQL);
			while (rs.next()) {
				info = new SavingsDeclarationInfo();
				info.setSavDeclCd(rs.getString("SAVDECLCD"));
				info.setFinyearcd(rs.getString("FYEARCD"));
				info.setEmpName(rs.getString("empname"));
				// info.setTotalSaving(rs.getString(""));
				slist.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch, getAllSavings() : " + e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , getAllSavings() : "
					+ e.getMessage());
			}
		}
		return slist;
	}

	public List showNSCFinYears(String fyearcd) {
		List flist = new ArrayList();
		SavingsDeclarationInfo info = new SavingsDeclarationInfo();
		String sSQL = "";
		try {
			db.makeConnection();
			con = db.getConnection();
			sSQL = " select fyearcd,fd,td from (select FYEARCD,to_char(FROMDATE, 'dd/Mon/yyyy') fd, to_char(TODATE, 'dd/Mon/yyyy')td,"
				+ "dense_rank() over (order by fromdate desc) as rank  from financialyear f where fromdate <= "
				+ "(select fromdate from financialyear f1 where f1.fyearcd='"
				+ fyearcd + "') order by fromdate desc) where rank <=6 ";
			log.info("showNSCFinYears qey is " + sSQL);
			rs = db.getRecordSet(sSQL);
			while (rs.next()) {
				info = new SavingsDeclarationInfo();
				info.setFinyearcd(rs.getString("fyearcd"));
				info.setFinYearName(rs.getString("fd") + " - "
					+ rs.getString("td"));
				flist.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch, showNSCFinYears() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , showNSCFinYears() : "
					+ e.getMessage());
			}
		}
		return flist;
	}

	public String[] getStringArrayFromStr(String str, String sep) {
		int cnt = 0;
		StringTokenizer st = new StringTokenizer(str, sep);
		String[] details = new String[st.countTokens()];// detStr.split("|");
		while (st.hasMoreTokens()) {
			details[cnt] = st.nextToken();
			cnt++;
		}
		return details;
	}

	public List getEmpSavDeclFinYears(String empcd) {
		List flist = new ArrayList();
		SavingsDeclarationInfo info = new SavingsDeclarationInfo();
		String sSQL = "";
		try {
			db.makeConnection();
			con = db.getConnection();
			sSQL = " select FYEARCD,to_char(FROMDATE, 'dd/Mon/yy') fd,to_char(TODATE, 'dd/Mon/yy') td from "
				+ "financialyear f where fromdate > (select nvl(max(fromdate),'31/Dec/1900') from financialyear f1 where f1.fyearcd in "
				+ "(select FYEARCD From savingsdeclartion where EMPNO='"
				+ empcd + "' and status='A')) order by fromdate ";
			log.info("getEmpSavDeclFinYears qey is " + sSQL);
			rs = db.getRecordSet(sSQL);
			while (rs.next()) {
				info = new SavingsDeclarationInfo();
				info.setFinyearcd(rs.getString("fyearcd"));
				info.setFinYearName(rs.getString("fd") + " - "
					+ rs.getString("td"));
				flist.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch, getEmpSavDeclFinYears() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , getEmpSavDeclFinYears() : "
						+ e.getMessage());
			}
		}
		return flist;
	}

	public List empProjectedITInfo(String empno, String fyearcd, DBAccess db) {
		List prjITList = new ArrayList();
		List sectionList = new ArrayList();

		ProjectedIncomeTaxInfo info = new ProjectedIncomeTaxInfo();
		String itQry = "";
		double hra = 0.0, basic = 0.0, hraFromEmplr = 0.0, hraFromEmpleLess10Perc = 0.0, hraFromEmplr40Perc = 0.0;
		double donationAmt = 0.0, dedAmt = 0.0, otherIncome = 0.0;
		double totalSec80ApplAmt = 0.0, totalSec80Savings = 0.0, chptrlimt = 0.0;
		double totIT = 0.0, cpf = 0.0, vpf = 0.0, GSLIS = 0.0,LIC = 0.0,PLI = 0.0;
		String ctgry = "";
		String station = "";
		String stationdiv = "";
		String pftaxoption = "", mnthnm = "", hraoption = "";
		double recit = 0, itfrmcurmnth = 0, licensefee = 0, rentpaid = 0;
		int mnthcnt = 0;
		double nscIntr = 0;
		double medicalIns = 0;
		double incomehouseproperty = 0;
		double sec24tot = 0;
		double sec24totexcempt = 0;
		double medicalExcempt = 0;
		double defsec1limit = 0.0;
		double defsec24limit = 0.0;
		double ded80EUD = 0.0;
		double helpageamt = 0;
		double quartersrent = 0;
		double recquartersrent = 0;
		int sysgenpaycnt = 0;
		
		try {
			// db.makeConnection();
			con = db.getConnection();

			/*
			 * ctgry =
			 * sh.getDescription("employeeinfo","STAFFCTGRYCD","empno",empno,con);
			 * station =
			 * sh.getDescription("employeeinfo","STATIONCD","empno",empno,con);
			 * stationdiv =
			 * sh.getDescription("stationmaster","trim(division)","STATIONCD",station,con);
			 */
			// 168 code is for quarters rent
			/*
			 * String empdetqry = " select
			 * info.staffctgrycd,info.stationcd,info.PFTAXOPTION,info.HRAOPTION,(select
			 * division from stationmaster sm where sm.stationcd =
			 * info.stationcd)division,to_char(sysdate,'Mon-yy')currdt,(select
			 * count(*) from payrollprocess where stationcd=info.stationcd and
			 * payrollmonthid in (select payrollmonthid from monthlypayroll
			 * where fyearcd = "+fyearcd+"))monthcnt,(select limitamount from
			 * SECTION where sectioncd = 1)section1limit,(select amount from
			 * empernsdeducs where empno = '"+empno+"' and earndeducd =
			 * '32')licensefee,(select amount from empernsdeducs where empno =
			 * '"+empno+"' and earndeducd = (select earndeducd from
			 * configmapping where CONFIGNAME = 'Helpage'))helpage,(select
			 * amount from empernsdeducs where empno = '"+empno+"' and
			 * earndeducd = 168)quartersrent from " + " (select
			 * STAFFCTGRYCD,stationcd,PFTAXOPTION,HRAOPTION from employeeinfo ei
			 * where ei.empno = '"+empno+"')info" + "";
			 */
			String empdetqry = " select info.staffctgrycd,info.stationcd,info.PFTAXOPTION,info.HRAOPTION,(select division from stationmaster sm where sm.stationcd = info.stationcd)division,(select PAYROLLMONTHNM||'-'||PAYROLLYEAR from monthlypayroll where payrollmonthid =( select max(payrollmonthid) from payrollprocess where stationcd=info.stationcd and payrollmonthid in (select payrollmonthid from monthlypayroll where fyearcd = "
				+ fyearcd
				+ ")))currdt,(select count(*) from payrollprocess where stationcd=info.stationcd and payrollmonthid in (select payrollmonthid from monthlypayroll where fyearcd = "
				+ fyearcd
				+ "))monthcnt,(select limitamount from SECTION where sectioncd = 1)section1limit,(select limitamount from SECTION where sectioncd = 4) section24limit,(select amount from empernsdeducs where empno = '"
				+ empno
				+ "' and earndeducd = '32')licensefee,(select amount from empernsdeducs where empno = '"
				+ empno
				+ "' and earndeducd = (select earndeducd from configmapping where CONFIGNAME = 'Helpage'))helpage,(select b.adjamt hrr  from monthly_saladj a ,monthly_saladj_det b  where a.msaladjcd=b.msaladjcd and b.earndeducd=168 and a.empno ='"
				+ empno
				+ "' and a.payrollmonthid in(select max(PAYROLLMONTHID) from payrollprocess where financialyearcd ='"
				+ fyearcd
				+ "' ))quartersrent  "
				+" ,(select sum(amount+adjustments) from payrolldet a ,payrollprocess b where  a.paytransid=b.paytransid and a.earndeducd = 168 and a.empno ='"
				+ empno
				+ "' and b.payrollmonthid in(select  PAYROLLMONTHID from monthlypayroll where FYEARCD ='"
				+ fyearcd
				+ "' ))recquartersrent from "
				+ " (select STAFFCTGRYCD,stationcd,PFTAXOPTION,HRAOPTION from employeeinfo ei where ei.empno = '"
				+ empno+ "')info" + "";
			
			/*
			 * String empdetqry = " select
			 * info.staffctgrycd,info.stationcd,info.PFTAXOPTION,info.HRAOPTION,(select
			 * division from stationmaster sm where sm.stationcd =
			 * info.stationcd)division,(select PAYROLLMONTHNM||'-'||PAYROLLYEAR
			 * from monthlypayroll where payrollmonthid =( select
			 * max(payrollmonthid) from payrollprocess where
			 * stationcd=info.stationcd and payrollmonthid in (select
			 * payrollmonthid from monthlypayroll where fyearcd =
			 * "+fyearcd+")))currdt,( select count(distinct paytransid)cnt from
			 * payrolldet where paytransid in ( select paytransid from
			 * payrollprocess where payrollmonthid in (SELECT payrollmonthid
			 * FROM monthlypayroll WHERE fyearcd = "+fyearcd+")) and empno =
			 * '"+empno+"')monthcnt,select count(paytransid)sysgenpaymonths from
			 * payrollprocess where payrollmonthid in (select payrollmonthid
			 * from monthlypayroll where fyearcd
			 * ="+fyearcd+")sysgenpaymonths,(select limitamount from SECTION
			 * where sectioncd = 1)section1limit,(select amount from
			 * empernsdeducs where empno = '"+empno+"' and earndeducd =
			 * '32')licensefee,(select amount from empernsdeducs where empno =
			 * '"+empno+"' and earndeducd = (select earndeducd from
			 * configmapping where CONFIGNAME = 'Helpage'))helpage,(select
			 * amount from empernsdeducs where empno = '"+empno+"' and
			 * earndeducd = 168)quartersrent from " + " (select
			 * STAFFCTGRYCD,stationcd,PFTAXOPTION,HRAOPTION from employeeinfo ei
			 * where ei.empno = '"+empno+"')info" + "";
			 */
			log.info("empdetqry " + empdetqry);
			ResultSet empdetrs = db.getRecordSet(empdetqry);
			if (empdetrs.next()) {

				ctgry = empdetrs.getString("STAFFCTGRYCD");
				station = empdetrs.getString("stationcd");
				stationdiv = empdetrs.getString("division");
				pftaxoption = empdetrs.getString("PFTAXOPTION");
				mnthnm = empdetrs.getString("currdt");
				mnthcnt = empdetrs.getInt("monthcnt");
				hraoption = empdetrs.getString("HRAOPTION");
				defsec1limit = empdetrs.getDouble("section1limit");
				defsec24limit = empdetrs.getDouble("section24limit");
				licensefee = empdetrs.getDouble("licensefee");
				helpageamt = empdetrs.getDouble("helpage");
				quartersrent = empdetrs.getDouble("quartersrent");
				recquartersrent = empdetrs.getDouble("recquartersrent");
				// sysgenpaycnt = empdetrs.getInt("sysgenpaymonths");
			}
			db.closeRs();
			log.info("---Station ---- " + station + "  ---  Diviosn -- "
				+ stationdiv + " pftaxoption " + pftaxoption);
			String hraldgrcd = "";
			if (ctgry.equals("1"))
				hraldgrcd = prop.getProperty("EHRA");
			else
				hraldgrcd = prop.getProperty("NEHRA");
			log.info("hraldgrcd" + hraldgrcd);
			// Query to get Selected Employee Earnings and deductions data
			// rs = db.getRecordSet("select EARNDEDUCD,(select EARNDEDUNM from
			// earndedumaster m where m.EARNDEDUCD=dt.EARNDEDUCD) EARNDEDUNM
			// ,AMOUNT,(select m.isbasicsalary from earndedumaster m where
			// m.EARNDEDUCD=dt.EARNDEDUCD) bp from empernsdeducs dt where empno
			// = '"+empno+"'");
			// log.info("Qry is ---1--- select EARNDEDUCD,(select EARNDEDUNM
			// from earndedumaster m where m.EARNDEDUCD=dt.EARNDEDUCD)
			// EARNDEDUNM ,AMOUNT,(select m.isbasicsalary from earndedumaster m
			// where m.EARNDEDUCD=dt.EARNDEDUCD) bp from empernsdeducs dt where
			// empno = '"+empno+"' ");
			// rs = db.getRecordSet("select
			// m.EARNDEDUCD,m.EARNDEDUNM,amount,m.isbasicsalary bp,m.ledgercd
			// from earndedumaster m,empernsdeducs dt where empno = '"+empno+"'
			// and m.EARNDEDUCD=dt.EARNDEDUCD");
			String sqry = "select EARNDEDUCD,EARNDEDUNM,AMOUNT,BP,LEDGERCD,TYPE,tot_earndeducd,REVAMOUNT from  (SELECT m.earndeducd,m.earndedunm,amount,m.isbasicsalary bp,slm.ledgercd,m.type FROM earndedumaster m, empernsdeducs dt, SALHEAD_LEDG_MAPPING slm WHERE m.EARNDEDUCD = slm.EARNDEDUCD and m.EARNDEDUCD = dt.EARNDEDUCD and empno = '"+empno+"' and cadrecod = (select cadrecod from employeeinfo where empno = '"+empno+"')) mt,(select earndeducd tot_earndeducd,sum(amount) revamount from payrolldet where empno='"+empno+"' group by earndeducd union select earndeducd tot_earndeducd, sum(TRKAMT) revamount from monthly_saltrack a ,monthly_saltrack_det b where a.msaltrkcd=b.msaltrkcd and a.empno = '"+empno+"' group by earndeducd) tot where mt.earndeducd=tot_earndeducd(+) order by mt.earndeducd";
			log.info("sqry " + sqry);
			rs = db.getRecordSet(sqry);
			double empearnamt = 0;
			
			while (rs.next()) {
				String ty = rs.getString("type");
				// HRA Amount received from Employer -- per year
				if (hraldgrcd.equals(rs.getString("ledgercd"))) {
					
					hraFromEmplr = rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12 - mnthcnt));
					log.info("hraFromEmplr" + hraFromEmplr);
				}

				// Getting Basic Pay amount -- per year
				if ("Y".equals(rs.getString("bp").trim())) {
					basic =  rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12 - mnthcnt));
				}
				
				// CPF Amount -- per year
		
				if (prop.getProperty("EMPCPF").equals(rs.getString("ledgercd"))&& ty.equals("D")) {
					
					cpf =  rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12 - mnthcnt));
					//log.info("cpfcpf"+cpf);
				}
				//log.info("111111111");
				// VPF Amount -- per year
				if (prop.getProperty("VPF").equals(rs.getString("ledgercd"))) {
					vpf =  rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12 - mnthcnt));
				}
				//log.info("222222");
				// GSLIS Amount -- per year
				if (prop.getProperty("GSLIS").equals(rs.getString("ledgercd"))) {
					GSLIS =  rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12 - mnthcnt));
				}//log.info("33333");
				if (ty.equalsIgnoreCase("E")) {
					empearnamt = empearnamt + (rs.getDouble("amount"));
				}
				//Lic Amount --per year
				//log.info("4444444"+rs.getString("ledgercd"));
				if (prop.getProperty("LIC").equals(rs.getString("ledgercd"))) {
					LIC = rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12-mnthcnt));
				}
				//PLI Amount --per year
				//log.info("6666"+rs.getString("ledgercd"));
				if (prop.getProperty("PLI").equals(rs.getString("ledgercd"))) {
					PLI = rs.getDouble("REVAMOUNT")+(rs.getDouble("amount") * (12-mnthcnt));
				}
				//log.info("55555");
			}
			db.closeRs();
			log.info("**empearnamt " + empearnamt);
			// HRA = 40% of basic if Employee live in Non Metro otherwise 50%
			/*
			 * if("NAD".equals(stationdiv)){ hraFromEmplr40Perc =
			 * (basic*40)/100; log.info("stationdiv is "+stationdiv+"
			 * hraFromEmplr40Perc is "+hraFromEmplr40Perc); } else{
			 * hraFromEmplr40Perc = (basic*50)/100; log.info("stationdiv is
			 * "+stationdiv+" hraFromEmplr50Perc is "+hraFromEmplr40Perc); }
			 */
			hraFromEmplr40Perc = (basic * 40) / 100;
			log.info("stationdiv is " + stationdiv
				+ "   hraFromEmplr40Perc is  " + hraFromEmplr40Perc);
			// Query to get total savings from savings declaration of selected
			// employee
			// itQry = " Select SAVDECDETCD,dt.SAVINGSCD,SECTIONCD,mt.savingname
			// savingnm, decode(dt.savingscd,8,(select AMOUNT from savingsnscdet
			// n where n.savdeclcd=dt.savdeclcd and dt.savingscd=8 and
			// FYEARCD='"+fyearcd+"'),SAVINGAMT)SAVINGAMT,PERCENT, " +
			itQry = " Select SAVDECDETCD,dt.SAVINGSCD,SECTIONCD,mt.savingname savingnm, SAVINGAMT,PERCENT, "
				+ "(SELECT SECNM  FROM section sc WHERE sc.sectioncd = mt.sectioncd) SECNM,"
				+ "(select limitamount from section sc where sc.sectioncd = mt.sectioncd) seclimit,"
				+ "(select limitamount from chapter ch where ch.chaptercd =(select CHAPTERCD from section sc where sc.sectioncd = mt.sectioncd)) chplimit "
				+ " from savingsdeclartiondet dt, savingsmaster mt where dt.savingscd = mt.savingscd and "
				+ " SAVDECLCD = (select SAVDECLCD from savingsdeclartion where empno = '"
				+ empno + "' and fyearcd='" + fyearcd + "' and status='A') and SAVINGAMT>0";
			log.info("Qry is ---1--- " + itQry);
			rs = db.getRecordSet(itQry);
			
			while (rs.next()) {
				String secname = rs.getString("SECNM");
				if (secname == null)
					secname = "";
				String savcd = rs.getString("SAVINGSCD");
				// log.info("savcd "+savcd);
				// log.info("ded80EUD secname "+secname+" ind
				// "+(secname.indexOf("80E")!=-1));
				if ((secname.indexOf("80E") != -1)
					|| (secname.indexOf("80D") != -1)
					|| (secname.indexOf("80U") != -1)) {
					ded80EUD += exemptedTaxAmount(rs.getDouble("SAVINGAMT"), rs
						.getDouble("seclimit"));
					// log.info("ded80EUD "+ded80EUD+"SAVINGAMT
					// "+rs.getDouble("SAVINGAMT"));
				}
				if ("15".equals(rs.getString("SAVINGSCD"))) {
					nscIntr = rs.getDouble("SAVINGAMT");
					log.info("nscint " + nscIntr);
				} else {

					// 1 = Section 80C data
					if ("1".equals(rs.getString("SECTIONCD"))) {
						info = new ProjectedIncomeTaxInfo();
						chptrlimt = rs.getDouble("chplimit");

						info.setSectionCd(rs.getString("SECTIONCD"));
						if ("8".equals(rs.getString("SAVINGSCD"))) {
							info.setSavingNm("NSC Curr Year Invst ");
						} else
							info.setSavingNm(rs.getString("savingnm"));
						if ("9".equals(rs.getString("SAVINGSCD"))) {
							info.setSavingAmt(Double.toString(exemptedTaxAmount(rs
								.getDouble("SAVINGAMT"), rs
								.getDouble("seclimit"))));
							totalSec80Savings += exemptedTaxAmount(rs
								.getDouble("SAVINGAMT"), rs
								.getDouble("seclimit"));
						} else {
							log.info("in else savcd " + savcd);
							info.setSavingAmt(Double.toString(rs.getDouble("SAVINGAMT")));
							totalSec80Savings += rs.getDouble("SAVINGAMT");
						}
						sectionList.add(info);
					} else {
						/*
						 * Savings Cd 1= Rent Paid 2 to 11 all are savings under
						 * 80C 12 = Donations
						 */
						if ("1".equals(rs.getString("SAVINGSCD"))) {
							// Calculating HRA Paid By Employee - 10% of basic
							rentpaid = rs.getDouble("SAVINGAMT");
							log
								.info("Rent Paid by employee is --- "
									+ rentpaid);
							hraFromEmpleLess10Perc = rs.getDouble("SAVINGAMT")
								- (basic * 10) / 100;
							hraFromEmpleLess10Perc = (hraFromEmpleLess10Perc < 0 ? 0
								: hraFromEmpleLess10Perc);
							log.info("hraFromEmplr 1111 -- " + hraFromEmplr
								+ " hraFromEmpleLess10Perc --- "
								+ hraFromEmpleLess10Perc
								+ " hraFromEmplr40Perc -- "
								+ hraFromEmplr40Perc);

							// Calculating HRA to be tax exempted is lowest
							// amount of all three above amounts
							// (hraFromEmplr,hraFromEmpleLess10Perc,hraFromEmplr40Perc)

							hra = CommonUtil
								.getLeastValue(CommonUtil.getLeastValue(
									hraFromEmplr, hraFromEmpleLess10Perc),
									hraFromEmplr40Perc);
							log.info("hra     "+hra);
						}else if ("2".equals(rs.getString("SAVINGSCD"))) {
							// Calculating Donation amount is how much percent
							// of amount is to be tax exempted.
							donationAmt = (rs.getDouble("SAVINGAMT")) ;
							ded80EUD = ded80EUD + donationAmt;
						}else if ("6".equals(rs.getString("SAVINGSCD"))) {
							// Calculating Donation amount is how much percent
							// of amount is to be tax exempted.
							donationAmt = (rs.getDouble("SAVINGAMT"));
							ded80EUD = ded80EUD + donationAmt;
						}
						
						else if ("12".equals(rs.getString("SAVINGSCD"))) {
							// Calculating Donation amount is how much percent
							// of amount is to be tax exempted.
							donationAmt = (rs.getDouble("SAVINGAMT") * rs
								.getInt("PERCENT")) / 100;
							ded80EUD = ded80EUD + donationAmt;
						} else if ("14".equals(rs.getString("SAVINGSCD"))) {
							// Calculating Donation amount is how much percent
							// of amount is to be tax exempted.
							otherIncome = rs.getDouble("SAVINGAMT");
						} else if ("11".equals(rs.getString("SAVINGSCD"))) {
							// Calculating Medical Insurance
							medicalIns = rs.getDouble("SAVINGAMT");
							log.info("medicalIns"+medicalIns);
						}
						//Block on 28/June/2012
						/*if (secname.trim().equalsIgnoreCase("section24")) {
							if ("13".equals(rs.getString("SAVINGSCD"))) {
								// Calculating Medical Insurance
								incomehouseproperty = exemptedTaxAmount(rs
									.getDouble("SAVINGAMT"), rs
									.getDouble("seclimit"));
							}
							sec24totexcempt = rs.getDouble("seclimit");
							sec24tot = sec24tot + rs.getDouble("SAVINGAMT");
							// log.info("sec24tot loop "+sec24tot);
						}*/
                      // End	Block on 28/June/2012
						else {
							dedAmt += exemptedTaxAmount(rs
								.getDouble("SAVINGAMT"), rs
								.getDouble("seclimit"));
						}
					}

				}

			}
			
			if (chptrlimt == 0.0) {
				chptrlimt = defsec1limit;
			}
			// log.info("11 sec24tot "+sec24tot+" sec24totexcempt
			// "+sec24totexcempt);
			//sec24tot = exemptedTaxAmount(sec24tot, sec24totexcempt);
			// log.info("final 11 sec24tot "+sec24tot+" sec24totexcempt
			// "+sec24totexcempt);
			// Adding Donation (Sec 80G) to Deductions
			dedAmt += donationAmt;
			db.closeRs();

			// Settibng CPF,VPF,GSLIS to section80CList.
			info = new ProjectedIncomeTaxInfo();
			info.setSavingNm("C.P.F");
			info.setSavingAmt(Double.toString(cpf));
			sectionList.add(info);
			info = new ProjectedIncomeTaxInfo();
			info.setSavingNm("V.P.F");
			info.setSavingAmt(Double.toString(vpf));
			sectionList.add(info);
			info = new ProjectedIncomeTaxInfo();
			info.setSavingNm("G.S.L.I.S");
			info.setSavingAmt(Double.toString(GSLIS));
			sectionList.add(info);
			info = new ProjectedIncomeTaxInfo();
			info.setSavingNm("L.I.C(From salary)");
			info.setSavingAmt(Double.toString(LIC));
			sectionList.add(info);
			info = new ProjectedIncomeTaxInfo();
			info.setSavingNm("P.L.I(From salary)");
			info.setSavingAmt(Double.toString(PLI));
			sectionList.add(info);
			totalSec80Savings += cpf + vpf + GSLIS+LIC+PLI;

			// dedAmt = exemptedTaxAmount(dedAmt,rs.getDouble("seclimit"));
			// Calculating total tax exempted savings lowest amount of
			// (totalSec80Savings,chptrlimt)
			log.info("totalSec80Savings " + totalSec80Savings + " chptrlimt "
				+ chptrlimt);
			totalSec80ApplAmt = exemptedTaxAmount(totalSec80Savings, chptrlimt);
			//log.info("***************totalSec80ApplAmt " + totalSec80ApplAmt);
			/*
			 * String advquery = "select ADVAMT from EMPLOYEEADVANCES
			 * ea,ADVANCESMASTER am,EARNDEDUMASTER edm where ADVFINISHEDDT is
			 * null and am.EARNDEDUCD = edm.EARNDEDUCD and ea.ADVCD=am.ADVCD and
			 * empno = '"+empno+"' and fyearcd='"+fyearcd+"' "; ResultSet advrs =
			 * db.getRecordSet(advquery); doublebye
			 *  advamt = 0.0; if(advrs.next()){
			 * advamt = advrs.getDouble("ADVAMT"); } db.closeRs(); todo: if
			 * RATEOFINT is more than SBI rate then we have to calculate and add
			 * as perk otherwise no need double perk = (advamt *3.5)/100;
			 */
			// Getting all earnings and deductions list and gross,net amounts of
			// selected employee for a particular financial year
			List empList = empTotalEarningsAndDeductions(empno, db, fyearcd);
			EmpPaySlipInfo payInfo = (EmpPaySlipInfo) empList.get(0);
			EmployeeInfo einfo = payInfo.getEmpInfo();
			List earnList = payInfo.getEarnList();
			double grossamt = Double.parseDouble(payInfo.getGrossAmt());
			double etot = payInfo.getEtot();
			double taxablesal = etot;
			/*
			 * Getting Misclleneous IT details
			 */
			/*log
				.info("select EMPNO,FYEARCD,sum(LTC)ltc,sum(BONUS)bonus,sum(OTA)ota,sum(MEDICAL)medical,sum(LEASE)lease from  empmiscitdetails where status= 'A' and empno='"
					+ empno
					+ "' and fyearcd='"
					+ fyearcd
					+ "' group by empno,fyearcd ");*/
			rs = db
				.getRecordSet("select EMPNO,FYEARCD,sum(LTC)ltc,sum(BONUS)bonus,sum(OTA)ota,sum(MEDICAL)medical,sum(LEASE)lease from  empmiscitdetails where status= 'A' and empno='"
					+ empno
					+ "' and fyearcd='"
					+ fyearcd
					+ "' group by empno,fyearcd ");
			if (rs.next()) {
				PaySlipInfo psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("LTC Encashment");
				psinfoo.setAmount(rs.getString("ltc"));
				earnList.add(psinfoo);
				psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Bonus (PLI)");
				psinfoo.setAmount(rs.getString("bonus"));
				taxablesal += rs.getDouble("bonus");
				earnList.add(psinfoo);
				psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("OTA");
				psinfoo.setAmount(rs.getString("ota"));
				taxablesal += rs.getDouble("ota");
				earnList.add(psinfoo);
				psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Medical");
				psinfoo.setAmount(rs.getString("medical"));
				earnList.add(psinfoo);
				psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Lease Maint. Charges");
				psinfoo.setAmount(rs.getString("lease"));
				earnList.add(psinfoo);
				grossamt = grossamt + rs.getDouble("ltc")
					+ rs.getDouble("bonus") + rs.getDouble("ota")
					+ rs.getDouble("medical") + rs.getDouble("lease");
			}
			db.closeRs();//

			/*
			 * Medical Insuracne is greater than 15000 then it is treated as
			 * earning
			 */
// block on 27/June/2012
		/*	PaySlipInfo medi = new PaySlipInfo();
			medi.setEarnDeduName("Medical > Rs 15000");
			if (medicalIns > 15000) {
				medicalIns = medicalIns - 15000;
				medicalExcempt = 15000;
				log.info(">>>>>>>>>>>>"+medicalIns);
			} else {
				medicalExcempt = medicalIns;
				log.info("<<<<<<<<<"+medicalIns);
			}
			medi.setAmount(medicalIns + "");
			earnList.add(medi);
			*/
//			end  block on 27/June/2012
			/*
			 * If Hraoption is L is then the formula for lease charges is less
			 * than 1 of the following 1. Total rent paid 2. 15 % of total
			 * taxable salary (which includes amount from 1) istaxable=Y
			 * salheads and PLA amount and OTA amount
			 */
			if (hraoption.equalsIgnoreCase("L")) {
				PaySlipInfo psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Lease Maint.Chrgs");
				double am = (taxablesal * 0.15);
				double lmc = 0;
				if (am < rentpaid) {
					lmc = am;
				} else
					lmc = rentpaid;
				psinfoo.setAmount(lmc + "");
				earnList.add(psinfoo);
				grossamt += lmc;
			}

			/*
			 * If Hraoption is Q is then the formula for lease charges is 15% of
			 * taxable salary - licensefee
			 * 
			 */
			// Block on 28/June/2012
			/*
			if (hraoption.equalsIgnoreCase("Q")) {
				PaySlipInfo psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Perk on Lease/Re.Qrts");
				log.info("taxablesal====="+taxablesal);
				
				double am = (taxablesal * 0.15);
				log.info("am"+am);
				log.info("recquartersrent"+recquartersrent);
				log.info("quartersrent"+quartersrent);
				double qamt = Math.round(am - (Math.round(recquartersrent)+Math.round(quartersrent * (12 - mnthcnt))));
				log.info("qamt====="+qamt);
				psinfoo.setAmount(qamt+"");
				earnList.add(psinfoo);
				grossamt += qamt;
			}*/
log.info(">>>>>>>>>>>>");
			payInfo.setEarnList(earnList);
			payInfo.setGrossAmt(Double.toString(grossamt));
			empList.remove(0);
			// grossamt = Math.round(grossamt);
			empList.add(payInfo);
			/*log.info("gross sal is --- " + grossamt / 12
				+ " perk amt is ----- " + payInfo.getPerkAmt() / 12);*/
			
			/*
			 * Other Earnings 
             * Calculate Other Earnings  on AAI Advance Interst compare with Bank Interst
			 */
            
			double qq = 0.0;
			String otherEarning=" select round(sum(ADVAMT *(bankrateofint - aairateofint)/100)) amt from employeeadvances adv, earningbyloanmt mt where EMPNO = '"+empno+"' and  adv.ADVCD in ('4', '6', '2', '5') and  PRINCIPALSTATUS = 'Y' and mt.advcd = adv.advcd and   mt.fyearcd = '"+fyearcd+"' and ADVAMT between FROMSLAB and TOSLAB";
			log.info("otherEarning"+otherEarning);
			
			rs = db.getRecordSet(otherEarning);
			PaySlipInfo psinfoo = new PaySlipInfo();
			psinfoo = new PaySlipInfo();
			psinfoo.setEarnDeduName("AAI Interst");
			psinfoo.setAmount(qq+"");
			
			if(rs.next()){
				psinfoo.setAmount(rs.getDouble(1)+"");
				earnList.add(psinfoo);
				grossamt += rs.getDouble(1);
			}
			//earnamt+=rs.getDouble(1);
			
			
			
			payInfo.setEarnList(earnList);
			payInfo.setGrossAmt(Double.toString(grossamt));
			empList.remove(0);
			// grossamt = Math.round(grossamt);
			empList.add(payInfo);
			db.closeRs();
				
			
			/*
			 * If PF tax option is 1 then there is no PF tax
			 */
			double pftax = 0;
			if (pftaxoption.equals("0")) {
				/*log
					.info("Qry is ---3--- select PROFTAX from professionaltaxslab where "
						+ Math.round(((grossamt - payInfo.getPerkAmt()) / 12))
						+ " between FROMSAL and TOSAL and fyearcd='"
						+ fyearcd
						+ "'");*/
				// Calculating Pofessional tax from professionaltaxslab table
				// based on gross amount
				rs = db
					.getRecordSet("select nvl(PROFTAX,0) from professionaltaxslab where "
						+ Math.round(((grossamt - payInfo.getPerkAmt()) / 12))
						+ " between FROMSAL and TOSAL and fyearcd='"
						+ fyearcd
						+ "'");
				// rs = db.getRecordSet("select nvl(PROFTAX,0) from
				// professionaltaxslab where "+Math.round(((grossamt)/12))+"
				// between FROMSAL and TOSAL and fyearcd='"+fyearcd+"'");
				if (rs.next())
					pftax = rs.getDouble(1);
				rs.close();
				db.closeRs();
			}
			String qry = "";
			/*
			 * qry = "select sum(INTERESTAMT) from savingsnscdet
			 * ns,savingsdeclartiondet dt where dt.savingscd=8 and
			 * ns.savdeclcd=dt.savdeclcd and dt.SAVDECLCD = (select SAVDECLCD
			 * from savingsdeclartion where empno = '"+empno+"' and
			 * fyearcd='"+fyearcd+"' and status='A')"; log.info("Qry is ---4---
			 * "+qry); // Calculating NSC INTERESTAMT amount if NSC data
			 * available in savings declaration rs = db.getRecordSet(qry);
			 * if(rs.next()) nscIntr = rs.getDouble(1);
			 * log.info("getProjectedIncomeTaxInfo Qry is === "+itQry);
			 * db.closeRs();
			 */

			double taxableIncome = 0.0;
			// Calculating Total Taxable Income if gross amount is greater than
			// zero

			double exempEduAll = 0, exempWashAll = 0, exemAllUS10 = 0,exempHostalAll=0,exempTransportAll=0,exempMedicalAll=0;
			// rs = db.getRecordSet("select (AMOUNT*12)amount,( select count(*)
			// from empdependents where empno=e.empno and
			// relationship='Children')cnt from empernsdeducs e,configmapping cm
			// where e.earndeducd=cm.earndeducd and cm.configname='EDUALL' and
			// empno='"+empno+"'");
			rs = db
				.getRecordSet("select configname,(AMOUNT*12)amount,nvl((select b.name from savingsdeclartion a, savingsdeclartiondet b where a.savdeclcd = b.savdeclcd and a.fyearcd = '"+fyearcd+"' and b.savingscd = 24 and a.empno=e.empno and a.status='A'),0) cnt,nvl((select b.name from savingsdeclartion a, savingsdeclartiondet b where a.savdeclcd = b.savdeclcd and a.fyearcd = '"+fyearcd+"' and b.savingscd = 25 and a.empno=e.empno and a.status='A'),'Others') transType from empernsdeducs e,configmapping cm where e.earndeducd=cm.earndeducd and cm.configname  IN ('EDUALL','WASHALL','TRANS','HOSTAL','MEDICAL') and empno='"
					+ empno + "'");
			log
				.info("select configname,(AMOUNT*12)amount,nvl((select b.name from savingsdeclartion a, savingsdeclartiondet b where a.savdeclcd = b.savdeclcd and a.fyearcd = '"+fyearcd+"' and b.savingscd = 24 and a.empno=e.empno and a.status='A'),0)cnt,nvl((select b.name from savingsdeclartion a, savingsdeclartiondet b where a.savdeclcd = b.savdeclcd and a.fyearcd = '"+fyearcd+"' and b.savingscd = 25 and a.empno=e.empno and a.status='A'),'Others') transType from empernsdeducs e,configmapping cm where e.earndeducd=cm.earndeducd and cm.configname  IN ('EDUALL','WASHALL','TRANS','HOSTAL','MEDICAL') and empno='"
					+ empno + "'");
			while (rs.next()) {
				
				/*log.info(" -----1----- " + rs.getDouble("amount")
					+ "- ----2------ " + rs.getDouble("cnt"));*/
				log.info(">>>>>>"+rs.getString("configname"));
				if ("EDUALL".equals(rs.getString("configname"))) {
					log.info("EDUALL");
					if (rs.getDouble("cnt") == 0)
						exempEduAll = 0;
					else if (rs.getDouble("cnt") >= 2) {
						log.info("child count > 2" + exempEduAll);
						// exempEduAll = rs.getDouble("amount") - 2*1200;
						if ((rs.getDouble("amount") * 12) > (2 * 1200)) {
							exempEduAll = 2 * 1200;
						}
					} else {
						if ((rs.getDouble("amount") * 12) > (rs
							.getDouble("cnt") * 1200)) {
							exempEduAll = (rs.getDouble("cnt") * 1200);
						}
						// exempEduAll = rs.getDouble("amount") -
						// (rs.getDouble("cnt")*1200);
						//log.info("child count < 2 " + exempEduAll);
					}
					log.info(">>>>>>>>>>");
				} else if ("HOSTAL".equals(rs.getString("configname"))) {
					if (rs.getDouble("cnt") == 0)
						exempHostalAll = 0;
					else if (rs.getDouble("cnt") >= 2) {
						log.info("child count > 2" + exempHostalAll);
						// exempEduAll = rs.getDouble("amount") - 2*1200;
						if ((rs.getDouble("amount") * 12) > (2 * 3600)) {
							exempHostalAll = 2 * 3600;
						}
					} else {
						if ((rs.getDouble("amount") * 12) > (rs
							.getDouble("cnt") * 3600)) {
							exempHostalAll = (rs.getDouble("cnt") * 3600);
						}
						// exempEduAll = rs.getDouble("amount") -
						// (rs.getDouble("cnt")*1200);
						//log.info("child count < 2 " + exempEduAll);
					}
				}else if ("TRANS".equals(rs.getString("configname"))) {
					log.info(""+rs.getString("transType"));
					if(rs.getString("transType").equals("Car")){
						exempTransportAll=1800*12;
					}else if(rs.getString("transType").equals("Bike")){
						exempTransportAll=900*12;
					}else {
						exempTransportAll=800*12;
					}
					log.info("TRANS");
					exempTransportAll = exemptedTaxAmount(exempTransportAll, rs
						.getDouble("AMOUNT"));
					log.info("TRANS"+exempTransportAll);
				}else if ("MEDICAL".equals(rs.getString("configname"))) {
					log.info("MEDICAL");
					exempMedicalAll = 15000;
				}  
				else {
					exempWashAll = rs.getDouble("amount");
				}
				
				
			}
			db.closeRs();
			taxableIncome = (grossamt > 0 ? (grossamt - exempWashAll
				- otherIncome - exempEduAll-exempHostalAll-exempTransportAll-exempMedicalAll - hra - (pftax * 12) - nscIntr
				- totalSec80ApplAmt - ded80EUD) : 0);
			// Calculating total Income tax applicable on taxableIncome from
			// incometaxslabmaster

			exemAllUS10 = hra + exempWashAll + exempEduAll + exempHostalAll + exempTransportAll+exempMedicalAll;
			log.info("hra"+hra);
			log.info("exempWashAll"+exempWashAll);
			log.info("exempEduAll"+exempEduAll);
			log.info("exempHostalAll"+exempHostalAll);
			log.info("exempTransportAll"+exempTransportAll);
			/* 
			 * rs = db.getRecordSet("select
			 * m.EMPITRECCD,EMPITRECDETCD,empno,ITRECAMT,m.PAYROLLMONTHID,to_char(sysdate,'Mon-yy')currdt,(select
			 * count(*) from monthlypayroll where
			 * to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') <=
			 * PAYROLLTODT and FYEARCD in (select FYEARCD from financialyear
			 * where TODATE >=
			 * to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy')))monthcnt
			 * from empitrec m , empitrecdet d where m.empitreccd=d.empitreccd
			 * and FYEARCD=10 and empno='"+empno+"' and d.Payrollmonthid=(select
			 * payrollmonthid from monthlypayroll mp where
			 * to_date(add_months(sysdate,-1),'mm/dd/yyyy') between
			 * mp.payrollfromdt and mp.payrolltodt )"); if(rs.next()){
			 * //log.info(" -----1----- "+rs.getDouble("FIXEDAMOUNT")+"-
			 * ----2------ "+(grossamt*rs.getDouble("TAXPERCENT")/100)+"
			 * -------3---- "+rs.getDouble("DEDUCTIONAMT")); recit =
			 * rs.getDouble("ITRECAMT"); mnthcnt = rs.getInt("monthcnt");
			 * if(mnthcnt >0) itfrmcurmnth = Math.round(recit/mnthcnt); }
			 * db.closeRs();
			 */
			// Educational Cess at 3% of income tax
			double netsalbefpftax = Double.parseDouble(payInfo.getGrossAmt()) - exemAllUS10;

			double salafterpftax = netsalbefpftax - (pftax * 12);
			//log.info("salafterpftax " + salafterpftax);
			double saladj = 0;
			if (((quartersrent*(12-mnthcnt))+recquartersrent) > 0) {
				salafterpftax = salafterpftax - ((quartersrent*(12-mnthcnt))+recquartersrent);
				saladj = Math.round(salafterpftax * 0.15);
			} else {
				saladj = 0;
			}
			salafterpftax = salafterpftax + saladj;
// block on 28/June/2012
			//double grosstotalincome = salafterpftax + nscIntr + otherIncome + (sec24tot);
			
			double grosstotalincome = salafterpftax + nscIntr + otherIncome;
			
			/*log.info("nscIntr " + nscIntr + "otherIncome " + otherIncome
				+ " sec24tot " + sec24tot + " grosstotalincome "
				+ grosstotalincome);*/
			double totincome = grosstotalincome - ded80EUD - (helpageamt * 12)
				- totalSec80ApplAmt;
			
			/*log.info("grosstotalincome " + grosstotalincome + " ded80EUD "
				+ ded80EUD + " (helpageamt*12) " + (helpageamt * 12)
				+ " totalSec80ApplAmt " + totalSec80ApplAmt + " totincome "
				+ totincome);*/
            rs = db.getRecordSet("select sum(PMNRFUND) PMNRFUND from emppmnrfunddet pmrdet, emppmnrfund pmrmt where pmrmt.EMPPMNRFUNDCD=pmrdet.EMPPMNRFUNDCD and pmrdet.empno='"+empno+"' and pmrmt.PAYROLLMONTHID in (select PAYROLLMONTHID from monthlypayroll where FYEARCD='"+fyearcd+"')"); 
			double pmReliefFund=0;
            if(rs.next()){
            	pmReliefFund=rs.getDouble("PMNRFUND");
			}
          
            db.closeRs();
            totincome-=pmReliefFund;
            // 
            //rs = db.getRecordSet("select sum(PMNRFUND) PMNRFUND from payrolldet where pmrmt.EMPPMNRFUNDCD=pmrdet.EMPPMNRFUNDCD and pmrdet.empno='"+empno+"' and pmrmt.PAYROLLMONTHID in (select PAYROLLMONTHID from monthlypayroll where FYEARCD='"+fyearcd+"')"); 
            rs = db.getRecordSet("select sum(Amount) HBAInterstAmt from payrolldet a, payrollprocess b where  a.paytransid = b.paytransid and a.empno = '"+empno+"' and a.type='ADVINT' and a.earndeducd in(177,178) and  b.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll where FYEARCD = '"+fyearcd+"')");
            
            double section24=0.0;
            if(rs.next()){
            	section24=rs.getDouble("HBAInterstAmt");
            	sec24totexcempt = defsec24limit;
    			sec24tot = exemptedTaxAmount(section24, sec24totexcempt);
			}
          
            db.closeRs();
            totincome-=sec24tot;
            
            String taxCal="select FIXEDAMOUNT,TAXPERCENT,DEDUCTIONAMT from incometaxslabmaster m,incometaxslabdetails d where m.taxslabcd=d.taxslabcd and m.assessetype='"+payInfo.getEmpInfo().getGender()+"' and fyearcd='"+fyearcd+"' and  '"+totincome+"' between FROMAMOUNT and TOAMOUNT ";
            //log.info("taxCal       "+taxCal);
            rs = db.getRecordSet(taxCal);
			if (rs.next()) {
				// log.info(" -----1----- "+rs.getDouble("FIXEDAMOUNT")+"-
				// ----2------ "+(grossamt*rs.getDouble("TAXPERCENT")/100)+"
				// -------3---- "+rs.getDouble("DEDUCTIONAMT"));
				log.info("FIXEDAMOUNT"+rs.getDouble("FIXEDAMOUNT"));
				log.info("DEDUCTIONAMT"+rs.getDouble("DEDUCTIONAMT"));
				log.info("TAXPERCENT"+rs.getDouble("TAXPERCENT"));
				log.info("totincome"+totincome);
				
				totIT = Math
					.round((rs.getDouble("FIXEDAMOUNT") + ((totincome - rs
						.getDouble("DEDUCTIONAMT"))
						* rs.getDouble("TAXPERCENT") / 100)));
			}
			double ec = Math.round(totIT * 3 / 100);
			db.closeRs();
			recit = payInfo.getItrecamt();
			
			// taxableIncome =
			// (grossamt>0?(grossamt-exempWashAll-otherIncome-exempEduAll-hra-(pftax*12)-nscIntr-totalSec80ApplAmt-ded80EUD):0);
			ProjectedIncomeTaxInfo prjinfo = new ProjectedIncomeTaxInfo();
			// Setting all the data to bean to display in report.
			prjinfo.setEmpPayList(empList);
			prjinfo.setSectionList(sectionList);
			prjinfo.setHra(Double.toString(hra)); 
			prjinfo.setExempCEA(Double.toString(exempEduAll));
			prjinfo.setExempHostal(Double.toString(exempHostalAll));
			prjinfo.setExempTransport(Double.toString(exempTransportAll));
			prjinfo.setExempMedicalRemb(Double.toString(exempMedicalAll));
			prjinfo.setSec24tot(Double.toString(sec24tot));
			prjinfo.setHelpage(Double.toString(helpageamt * 12));
			prjinfo.setPmReliefFund(Double.toString(pmReliefFund));
			prjinfo.setQrtrsrent(Double.toString((quartersrent*(12-mnthcnt))+recquartersrent));
			prjinfo.setGrosssaladj(Double.toString(saladj));
			prjinfo.setExempWashAll(exempWashAll);
			prjinfo.setExemAllUS10(Double.toString(exemAllUS10));
			prjinfo.setPrfTax(Math.round(pftax));
			prjinfo.setYrlyPrfTax(Double.toString(pftax * 12));
			prjinfo.setNscIntrAmt(Double.toString(nscIntr));
			// prjinfo.setIncomehouseprop(incomehouseproperty);
			prjinfo.setOtherIncome(Double.toString(otherIncome));
			prjinfo.setDeduAmt(dedAmt);
			prjinfo.setNetSalBeforePFTax(Double.toString(netsalbefpftax));
			prjinfo.setTaxableSal(Double.toString(salafterpftax));
			prjinfo.setGrossTotalIncome(Double.toString(grosstotalincome));
			prjinfo.setTot80eud(Double.toString(ded80EUD));
			prjinfo.setTotalSavings(Double.toString(totalSec80Savings));
			prjinfo.setTotalSec80ApplAmt(Double.toString(totalSec80ApplAmt));
			prjinfo.setTaxableIncome(Double.toString(totincome));
			prjinfo.setTotITLessEc(Double.toString(totIT));
			prjinfo.setEc(Double.toString(ec));
			log.info("totIT"+totIT);
			log.info("ec"+ec);
			log.info("totincome"+totincome);
			log.info("saladj"+saladj);
			double aaiBearedtax=Math.round((((totIT + ec)/totincome)*100)*(saladj/100));
			log.info("aaiBearedtax"+aaiBearedtax);
			prjinfo.setAAIBearedTax(Double.toString(aaiBearedtax));

			prjinfo.setTotIT(Double.toString(totIT + ec + aaiBearedtax));
			
			prjinfo.setRecIT(Double.toString(recit));
			prjinfo.setItFrmCurMnth(itfrmcurmnth);
			prjinfo.setCurrMonth(mnthnm);
			//log.info("mnthcnt " + mnthcnt);
			// sysgenpaycnt

			if (mnthcnt > 0)
				itfrmcurmnth = Math
					.round((totIT + ec + aaiBearedtax - recit) / (12 - mnthcnt));
			else
				itfrmcurmnth = Math.round((totIT + ec + aaiBearedtax - recit) / 12);
			if (itfrmcurmnth < 0)
				itfrmcurmnth = 0;
			prjinfo.setTaxPerMonth(Double.toString(itfrmcurmnth));
			//log.info(prjinfo.getTaxPerMonth() + " -----1111 --- "+ prjinfo.getPrfTax());
			prjITList.add(prjinfo);

		} catch (Exception e) {
			log.info("Exception in catch, getProjectedIncomeTaxInfo() : "
				+ e.getMessage());
			log.printStackTrace(e);
		}
		return prjITList;
	}

	public List getProjectedIncomeTaxInfo(String empno, String fyearcd) {
		List prjList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();
			prjList = empProjectedITInfo(empno, fyearcd, db);
		} catch (Exception e) {
			log.info("Exception in catch, getProjectedIncomeTaxInfo() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , getProjectedIncomeTaxInfo() : "
						+ e.getMessage());
			}
		}
		return prjList;
	}

	public List empTotalEarningsAndDeductions(String empno, DBAccess db,
		String fyearcd) {
		SQLHelper help = new SQLHelper();
		List earnningList = new ArrayList();
		List deducList = new ArrayList();
		List empPayList = new ArrayList();
		Map mp = new HashMap();
		Hashtable ht = new Hashtable();
		EmployeeInfo empinfo = new EmployeeInfo();
		EmpPaySlipInfo empPayInfo = new EmpPaySlipInfo();

		PaySlipInfo psinfoo = new PaySlipInfo();
		try {
			// db.makeConnection();
			con = db.getConnection();
			// String empSQL = "Select empNo,EMPFIRSTNAME||'
			// '||EMPLASTNAME||'.'||EMPSURNAME
			// empname,DESIGNATIONCD,STAFFCTGRYCD,CPFNO,BACNO,GENDER,PAN,(
			// select MINSCALE|| '-' ||MAXSCALE from payscalemaster pay where
			// emp.cadrecd=pay.cadrecd) payscale from EmployeeInfo emp";
			String empSQL = "SELECT emplnumber, empno, EMPLOYEENAME empname, emp.designationcd, emp.staffctgrycd, cpfno, bacno, gender, pan,sd.DESIGNATIONNM ,sc.STAFFCTGRYNM , (SELECT minscale || '-' || maxscale FROM payscalemaster pay WHERE emp.DESIGNATIONCD = pay.DESIGNATIONCD) payscale,nvl((select ITRECAMT from EMPITREC er,EMPITRECDET erd where er.EMPITRECCD = erd.EMPITRECCD and erd.EMPNO = '"
				+ empno
				+ "' and er.FYEARCD= '"
				+ fyearcd
				+ "'),0)itrec FROM employeeinfo emp,STAFFDESIGNATION sd,staffcategory sc  where emp.DESIGNATIONCD = sd.DESIGNATIONCD  and emp.STAFFCTGRYCD = sc.STAFFCTGRYCD ";
			empSQL += " and emp.EMPNO = '" + empno + "'";
			double rec = 0;
			log.info("empSQL is " + empSQL);
			PreparedStatement ps = null;
			ResultSet rss = db.getRecordSet(empSQL);
			if (rss.next()) {
				// empPayInfo = new EmpPaySlipInfo();
				empinfo = new EmployeeInfo();
				earnningList = new ArrayList();
				deducList = new ArrayList();
				empinfo.setEmpNo(rss.getString("emplnumber"));

				empinfo.setEmpName(rss.getString("empname"));
				/*
				 * empinfo.setEmpDesignation(sh.getDescription("staffdesignation","DESIGNATIONNM","DESIGNATIONCD",rss.getString("DESIGNATIONCD"),con));
				 * empinfo.setStaffCategoryNm(sh.getDescription("staffcategory","STAFFCTGRYNM","STAFFCTGRYCD",rss.getString("STAFFCTGRYCD"),con));
				 */
				empinfo.setEmpDesignation(rss.getString("designationnm"));
				empinfo.setStaffCategoryNm(rss.getString("staffctgrynm"));
				empinfo.setCpfNo(rss.getString("CPFNO"));
				empinfo.setBacno(rss.getString("BACNO"));
				empinfo.setPayscale(rss.getString("payscale"));
				empinfo.setGender(rss.getString("gender"));
				empinfo.setPan(rss.getString("pan"));
				String itrec = rss.getString("itrec");

				if (itrec != null && !itrec.equals("")) {
					rec = Double.parseDouble(itrec);
				}

			}
			db.closeRs();
			double grossamt = 0.0;
			double perkamt = 0;
			double deducamt = 0.0;
			double netamt = 0.0;
			// ht.put(rss.getString("empNo")+"1",empinfo);
			// String sql = "select d.EARNDEDUCD,AMOUNT*12
			// AMOUNT,TYPE,m.EARNDEDUNM from empernsdeducs d,earndedumaster m
			// where d.EARNDEDUCD=m.EARNDEDUCD and m.TYPE in('E','D') and
			// empno='"+empno+"' and d.status='A' order by EARNDEDUCD";
			// String sql = "select
			// earndeducd,amount,nvl(miscamt,0)miscamt,type,earndedunm,nvl(gprofamt,0)gprofamt
			// from(select d.EARNDEDUCD,AMOUNT*12 AMOUNT,(select sum(miscrecamt)
			// from empmiscrec misc where misc.empno=d.empno and
			// type='MISCADV')miscamt,TYPE,m.EARNDEDUNM,( select
			// gpfdet.gprofamount*12 FROM gprofamount gpfamt, gprofamountdet
			// gpfdet,employeeinfo ei WHERE gpfdet.cadrecd = ei.cadrecd and
			// gpfdet.gprofamtcd = gpfamt.gprofamtcd AND
			// gpfamt.payrollmonthid=(select trim(PAYROLLMONTHID) from
			// monthlypayroll where
			// to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') between
			// PAYROLLFROMDT and PAYROLLTODT) and empno=d.empno)gprofamt from
			// empernsdeducs d,earndedumaster m where d.EARNDEDUCD=m.EARNDEDUCD
			// and empno='"+empno+"' and d.status='A' and m.ISTAXABLE = 'Y')
			// order by EARNDEDUCD";
			// String sql = "select
			// earndeducd,amount,nvl(miscamt,0)miscamt,type,earndedunm,nvl(gprofamt,0)gprofamt
			// from(select d.EARNDEDUCD,AMOUNT*12 AMOUNT,(select sum(miscrecamt)
			// from empmiscrec misc where misc.empno=d.empno and
			// type='MISCADV')miscamt,TYPE,m.EARNDEDUNM,( select
			// gpfdet.gprofamount*12 FROM gprofamount gpfamt, gprofamountdet
			// gpfdet,employeeinfo ei WHERE gpfdet.cadrecd = ei.cadrecd and
			// gpfdet.gprofamtcd = gpfamt.gprofamtcd AND
			// gpfamt.payrollmonthid=190 and empno=d.empno)gprofamt from
			// empernsdeducs d,earndedumaster m where d.EARNDEDUCD=m.EARNDEDUCD
			// and empno='"+empno+"' and d.status='A' and m.ISTAXABLE = 'Y')
			// order by EARNDEDUCD";

			int cnt = 0;
			cnt = db
				.getRecordCount("  select count( distinct pp.paytransid) from payrollprocess pp,employeeinfo ei,payrolldet pt where PAYROLLMONTHID in (select PAYROLLMONTHID from monthlypayroll mp,financialyear f where mp.fyearcd=f.fyearcd and f.status='A') and pp.stationcd=ei.stationcd and ei.empno='"
					+ empno
					+ "'  and pp.paytransid=pt.paytransid and ei.empno=pt.empno");

			StringBuffer sql = new StringBuffer();

			sql.append(" select nvl(sum(amount),0)amount, type,earndedunm from ( ");
			sql.append(" select d.EARNDEDUCD, (AMOUNT * (12-"
					+ cnt
					+ ")) AMOUNT, TYPE, m.EARNDEDUNM from empernsdeducs d, earndedumaster m where d.EARNDEDUCD = m.EARNDEDUCD and empno = '"
					+ empno + "' and d.status = 'A' and m.ISTAXABLE = 'Y' ");
			// sql.append(" select d.EARNDEDUCD, (AMOUNT * (12-"+cnt+")) AMOUNT,
			// TYPE, m.EARNDEDUNM from empernsdeducs d, earndedumaster m where
			// d.EARNDEDUCD = m.EARNDEDUCD and empno = '"+empno+"' and d.status
			// = 'A' ");
			sql.append(" union all ");
			sql
				.append(" select 0 earndeducd,sum(nvl(miscrecamt,0))amount,'MISCADV' type,'Other Allow' earndedunm from empmiscrec misc where misc.empno = '"
					+ empno + "' and type = 'MISCADV' ");
			sql.append(" union all ");
			sql
			.append("select 176 earndeducd,nvl(sum(NETT),0) amount,'OTA' type,earndedunm from otatransaction_new_mt otamt,otatransaction_new_dt otadt ,earndedumaster ed where otamt.TRANSACTIONNEWID=otadt.TRANSACTIONNEWID and ed.type='OTA' and otadt.empno= '"+empno+"' group by earndeducd,earndedunm union all  select 176 earndeducd, nvl(sum(OTA_AMOUNT),0) amount,'N/W' type,earndedunm from emplota_dt otadt ,emplota_mt otamt ,financialyear fy,earndedumaster ed  where otamt.EMPLOTA_MT_CD=otadt.EMPLOTA_MT_CD and ed.type='OTA' and fy.FYEARCD=otamt.FYEARCD and fy.status='A' and empno='"+empno+"' group by earndeducd,earndedunm");
			sql.append(" union ");
			// sql.append(" select 0 earndeducd,(nvl(gpfdet.gprofamount,0) *
			// (12-"+cnt+"))amount,'E' type,'PROFIENCY ALLOW' earndedunm FROM
			// gprofamount gpfamt, gprofamountdet gpfdet, employeeinfo ei WHERE
			// gpfdet.cadrecd = ei.cadrecd and gpfdet.gprofamtcd =
			// gpfamt.gprofamtcd AND gpfamt.payrollmonthid = 190 and empno =
			// '"+empno+"' ");
			sql
				.append(" select 11 earndeducd,(nvl(gpfdet.gprofamount,0) * (12-"
					+ cnt
					+ "))amount,'E' type,'Prof.Allow' earndedunm FROM gprofamount    gpfamt, gprofamountdet gpfdet, employeeinfo   ei WHERE gpfdet.cadrecd = ei.cadrecd and gpfdet.gprofamtcd = gpfamt.gprofamtcd AND  gpfamt.payrollmonthid = ( select nvl(paymonthid,maxid) paymonthid from (select (select max(payrollmonthid) from (SELECT payrollmonthid FROM gprofamount WHERE payrollmonthid NOT IN (SELECT DISTINCT payrollmonthid  FROM payrollprocess))) paymonthid, (select max(payrollmonthid)  from gprofamount)maxid  from dual ))"
					+ " and empno = '" + empno + "' ");
			sql.append(" union all");
			sql
				.append(" select det.earndeducd,sum(amount),det.type,earndedunm from payrolldet det, earndedumaster m where det.earndeducd=m.earndeducd and  m.ISTAXABLE = 'Y' and paytransid in (select paytransid from payrollprocess pp,employeeinfo ei where PAYROLLMONTHID in (select PAYROLLMONTHID from monthlypayroll mp,financialyear f where mp.fyearcd=f.fyearcd and f.status='A') and pp.stationcd=ei.stationcd and empno='"
					+ empno
					+ "') and empno = '"
					+ empno
					+ "' group by  det.earndeducd,det.type,earndedunm ");
			sql
				.append(" )where type in ('E','MISADV','OTA','PK') and amount>0 group by type,earndedunm order by type ");

			StringBuffer esql = new StringBuffer();

			log.info("sql is " + sql.toString());
			/*
			 * ps = con.prepareStatement(sql);
			 * 
			 * rs = ps.executeQuery();
			 */
			rs = db.getRecordSet(sql.toString());
			double gprofamt = 0, miscamt = 0, earnamt = 0;
			while (rs.next()) {
				if (rs.getString("type").equalsIgnoreCase("E")
					|| rs.getString("type").equalsIgnoreCase("MISCADV")
					|| rs.getString("type").equalsIgnoreCase("GPROF")
					|| rs.getString("type").equalsIgnoreCase("AR")
					|| rs.getString("type").equalsIgnoreCase("A")|| rs.getString("type").equalsIgnoreCase("PK")) {
					earnamt = earnamt + rs.getDouble("amount");
				}
				/*
				 * String istaxable = rs.getString("ISTAXABLE");
				 * if(istaxable.equalsIgnoreCase("Y")){
				 */
				psinfoo = new PaySlipInfo();
				// psinfoo.setEarnDeduName(sh.getDescription("EARNDEDUMASTER","EARNDEDUNM","EARNDEDUCD",rs.getString("EARNDEDUCD"),con));
				psinfoo.setEarnDeduName(rs.getString("EARNDEDUNM"));
				psinfoo.setAmount(rs.getDouble("AMOUNT")+"");

				log.info("ename "+psinfoo.getEarnDeduName()+" amt"+psinfoo.getAmount());
				psinfoo.setType(rs.getString("type"));
				if (rs.getString("type").equals("D")
					|| rs.getString("type").equals("IT")
					|| rs.getString("type").equals("ADV")) {
					deducList.add(psinfoo);
					deducamt += rs.getDouble("AMOUNT");
					// log.info("deducamt "+deducamt);
				} else {
					earnningList.add(psinfoo);
					if (psinfoo.getType().equals("PK")) {
						perkamt += rs.getDouble("AMOUNT");
					}
					// log.info("perkamt "+perkamt);
					// gprofamt = rs.getDouble("gprofamt");
					grossamt += rs.getDouble("AMOUNT");
					// log.info("grossamt "+grossamt);
					// miscamt = rs.getDouble("miscamt");
				}
				// }
			}
			db.closeRs();
			//int perksmonths = db.getRecordCount("select count(payrollmonthid) from PROCESSED_EMPLPERKS where fyearcd = (select fyearcd from financialyear where status = 'A')");
			//log.info("persmonths" + perksmonths);

			// perkqry = "select * from (select (amount * (12 - '"+cnt+"')) amount,ed.earndeducd,em.EARNDEDUNM,slm.LEDGERCD from empernsdeducs ed, earndedumaster em, SALHEAD_LEDG_MAPPING slm where ed.EARNDEDUCD = em.EARNDEDUCD and ed.EARNDEDUCD = slm.EARNDEDUCD and ed.STAFFCTGRYCD = slm.CADRECOD and em.TYPE = 'PK' and ed.empno = '"+empno+"' and em.ISTAXABLE = 'Y') proj,(select sum(amount) revamount, earndeducd from payrolldet where empno = '"+empno+"' group by earndeducd) pd where proj.earndeducd = pd.earndeducd order by proj.earndeducd";
			//log.info("perkqry " + perkqry);
//			 bolck due perks include in earning
		/*	StringBuffer empErnDedQry = new StringBuffer("select empperk.projecteditname ProjectedITName,empperk.amount ");
			empErnDedQry.append(" amount,procperk.amount + payrollperk.amount payroll,slm.ledgercd ledgercd from (select");
			empErnDedQry.append(" sum(amount) amount,pep.EARNDEDUCD,em.projecteditname from ");
			empErnDedQry.append(" PROCESSED_EMPLPERKS_DET pep,earndedumaster em,PROCESSED_EMPLPERKS pp where");
			empErnDedQry.append(" pep.EARNDEDUCD = em.EARNDEDUCD and em.TYPE = 'PK' and empno =").append(empno);
			empErnDedQry.append(" and pp.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll");
			empErnDedQry.append(" mp where mp.fyearcd = ").append(fyearcd).append(" ) and pep.pro_per_cd =");
			empErnDedQry.append(" pp.pro_per_cd group by pep.earndeducd, em.EARNDEDUNM, em.projecteditname) ");
			empErnDedQry.append(" procperk,(select sum(amount) amount,pep.EARNDEDUCD, em.projecteditname from ");
			empErnDedQry.append(" payrolldet pep,earndedumaster em,payrollprocess proc where pep.EARNDEDUCD = ");
			empErnDedQry.append(" em.EARNDEDUCD and em.TYPE = 'PK' and  empno = ").append(empno).append(" and ");
			empErnDedQry.append(" proc.paytransid = pep.PAYTRANSID and proc.payrollmonthid in (select ");
			empErnDedQry.append(" PAYROLLMONTHID from monthlypayroll mp where mp.fyearcd = ").append(fyearcd);
			empErnDedQry.append(" ) and proc.payrollmonthid not in (select PAYROLLMONTHID from PROCESSED_EMPLPERKS");
			empErnDedQry.append(" pp where pp.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll mp");
			empErnDedQry.append(" where mp.fyearcd = ").append(fyearcd).append(" )) group by pep.earndeducd, ");
			empErnDedQry.append(" em.EARNDEDUNM, em.projecteditname) payrollperk,(select (amount * (12 - ");
			empErnDedQry.append(cnt).append(" )) amount,ep.earndeducd,em.EARNDEDUNM,em.projecteditname");
			empErnDedQry.append(" from emplperks ep, earndedumaster em where ep.EARNDEDUCD = em.EARNDEDUCD ");
			empErnDedQry.append(" and em.TYPE = 'PK' and empno =  ").append(empno).append("  and em.ISTAXABLE = ");
			empErnDedQry.append(" 'Y') empperk,SALHEAD_LEDG_MAPPING slm where empperk.earndeducd = ");
			empErnDedQry.append(" procperk.earndeducd and empperk.earndeducd = payrollperk.earndeducd and ");
			empErnDedQry.append(" slm.earndeducd = payrollperk.earndeducd and slm.cadrecod = (select cadrecod ");
			empErnDedQry.append(" from employeeinfo where empno = ").append(empno).append(") order by ");
			empErnDedQry.append(" procperk.earndeducd ");
			*/
			
			//StringBuffer empErnDedQry = new StringBuffer("select payrollperk.projecteditname ProjectedITName, empperk.amount amount,payrollperk.amount payroll, slm.ledgercd ledgercd from ( select sum(amount) amount, pep.EARNDEDUCD, em.projecteditname from payrolldet pep, earndedumaster em, payrollprocess proc where pep.EARNDEDUCD = em.EARNDEDUCD and em.TYPE = 'PK'  and empno = ").append(empno).append(" and proc.paytransid = pep.PAYTRANSID and proc.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll mp where mp.fyearcd = ").append(fyearcd).append(") and proc.payrollmonthid not in (select PAYROLLMONTHID from PROCESSED_EMPLPERKS pp  where pp.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll mp where mp.fyearcd = ").append(fyearcd).append(")) group by pep.earndeducd, em.EARNDEDUNM, em.projecteditname) payrollperk,(select (amount * (12 - 2)) amount,ep.earndeducd,em.EARNDEDUNM,em.projecteditname from empernsdeducs ep, earndedumaster em where ep.EARNDEDUCD = em.EARNDEDUCD and em.TYPE = 'PK'  and empno = ").append(empno).append(" and em.ISTAXABLE = 'Y') empperk, SALHEAD_LEDG_MAPPING slm  where   empperk.earndeducd = payrollperk.earndeducd and slm.earndeducd = payrollperk.earndeducd and slm.cadrecod = (select cadrecod from employeeinfo where empno = ").append(empno).append(" )  order by payrollperk.earndeducd");
			//log.info("empErnDedQry        "+empErnDedQry.toString());
			//rs = db.getRecordSet(empErnDedQry.toString());
			
			//ResultSet perkrs = db.getRecordSet(perkqry);
			//double excemptedAmt = 0 ;
			//while (rs.next()) {
				
				
				/*if(prop.getProperty("TRANSALLOW").equals(rs.getString("ledgercd"))){
					
					psinfoo = new PaySlipInfo();
						String petrol = help.getDescription("employeeinfo","case when petrol > 35 then 21600 else 10800 end","empno",empno,con);					
						excemptedAmt = CommonUtil.getLeastValue((rs.getDouble("payroll")+rs.getDouble("amount")),Double.parseDouble(petrol));
						psinfoo.setEarnDeduName("Transport Allow > Rs"+excemptedAmt+"");
						double  transAllows=rs.getDouble("AMOUNT")+rs.getDouble("payroll");
						transAllows=transAllows-excemptedAmt;
						if(transAllows<0)
							transAllows=0;
						psinfoo.setAmount(String.valueOf(transAllows));
						grossamt += transAllows;
						earnningList.add(psinfoo);
						
					}else if(prop.getProperty("ENTALLOW").equals(rs.getString("ledgercd"))){
						excemptedAmt = CommonUtil.getLeastValue((rs.getDouble("AMOUNT")+rs.getDouble("payroll")),5000);
						psinfoo = new PaySlipInfo();
						psinfoo.setEarnDeduName("ENT Allow > Rs"+excemptedAmt+"");
						double  entAllows=rs.getDouble("AMOUNT")+rs.getDouble("payroll");
						entAllows=entAllows-excemptedAmt;
						if(entAllows<0)
							entAllows=0;
						psinfoo.setAmount(String.valueOf(entAllows));
						grossamt += entAllows;
						earnningList.add(psinfoo);
					}
				else {*/
					//psinfoo = new PaySlipInfo();
					//psinfoo.setEarnDeduName(rs.getString("PROJECTEDITNAME"));
					//double tot=rs.getDouble("AMOUNT")+rs.getDouble("payroll");
					//psinfoo.setAmount(String.valueOf(tot));
					//grossamt += tot;
					//earnningList.add(psinfoo);
					
				//}
				
			//}
			// bolck due perks include in earning
			db.closeRs();
			/*
			 * Less EOL Amount
			 * 
			 */
			
			String EOLRcvdQury=" select sum(ADJUSTMENTS) from payrolldet where PAYTRANSID in (select paytransid from payrollprocess where stationcd ='CHQD' and empno ='"+empno+"' and payrollmonthid in (select payrollmonthid from monthlypayroll where fyearcd= '"+fyearcd+"' )) and empno='"+empno+"' and type!='D'" ;
			rs = db.getRecordSet(EOLRcvdQury);
			psinfoo = new PaySlipInfo();
			psinfoo.setEarnDeduName("Salary Adjustments");
			double dd=0.0;
			psinfoo.setAmount(dd+"");
			
			if(rs.next()){
				psinfoo.setAmount(rs.getDouble(1)+"");
			}
			earnamt+=rs.getDouble(1);
			grossamt +=  rs.getDouble(1);
			earnningList.add(psinfoo);
			db.closeRs();
			
			/*
			 * Supplementary Amount
             * Payments To Employee
			 */
            
			
			String Supplementary=" select sum(NETAMOUNT) amt from supplementarypayments  where EMPCODE = '"+empno+"' ";
			log.info("Supplementary"+Supplementary);
			
			rs = db.getRecordSet(Supplementary);
			double ss=0.0;
			psinfoo = new PaySlipInfo();
			psinfoo.setEarnDeduName("Supplementary Paided Amount");
			psinfoo.setAmount(ss+"");
			
			if(rs.next()){
				psinfoo.setAmount(rs.getDouble(1)+"");
			}
			earnamt+=rs.getDouble(1);
			grossamt += rs.getDouble(1);
			earnningList.add(psinfoo);
			db.closeRs();
				
			
		
			
			
			// Added Gprof to calculate prf.tax and incometax in projected
			// incometax
			/*
			 * psinfoo = new PaySlipInfo(); psinfoo.setEarnDeduName("Prof.
			 * Allowance"); psinfoo.setAmount(String.valueOf(gprofamt));
			 * psinfoo.setType("GPROF"); earnningList.add(psinfoo);
			 * grossamt+=gprofamt; psinfoo = new PaySlipInfo();
			 * psinfoo.setEarnDeduName("Other Allowance");
			 * psinfoo.setAmount(String.valueOf(miscamt));
			 * psinfoo.setType("MISCADV"); earnningList.add(psinfoo);
			 * grossamt+=miscamt;
			 */
			/*
			 * ht.put(rss.getString("empNo")+"2",earnningList);
			 * ht.put(rss.getString("empNo")+"3",deducList);
			 * mp.put(rss.getString("empNo"),ht);
			 */
			/*
			 * String arrearsqry = "select
			 * sum(ai.ARREARAMT)amount,ai.ARREARSCD,ARREARNM from ARREARSINFO
			 * ai,ARREARS ar where ARREARDETCD in(" +"select ad.ARREARDETCD from
			 * ARREARSDET ad,MONTHLYPAYROLL mp where ad.PAYROLLMONTHID =
			 * mp.PAYROLLMONTHID and mp.FYEARCD ='"+fyearcd+"')" +"and
			 * ar.ARREARSCD = ai.ARREARSCD and ai.EMPNO='"+empno+"' group by
			 * ai.ARREARSCD,ARREARNM";
			 */

			// Adding Arrears To the Gross Amount
			String arrsqry = "select empno,netamt from emplarrears_mt eamt,emplarrears_dt eadt where eamt.EMPLARRMTCD = eadt.EMPLARRMTCD and eamt.fyearcd="
				+ fyearcd + " and empno = '" + empno + "'";
			ResultSet rs3 = db.getRecordSet(arrsqry);
			// log.info("grossamt "+grossamt);
			while (rs3.next()) {

				psinfoo = new PaySlipInfo();
				psinfoo.setEarnDeduName("Arrears");
				psinfoo.setType("AR");
				psinfoo.setAmount(rs3.getString("netamt"));
				earnningList.add(psinfoo);
				grossamt += rs3.getDouble("netamt");
				// log.info("type "+psinfoo.getType()+" nm
				// "+psinfoo.getEarnDeduName());
			}
			db.closeRs();
			// log.info("after arrearsqry grossamt "+grossamt);

			// Adding Advances amounts (if Any) to the Gross Amount

			/*
			 * String advqry = "select ADVAMT, RATEOFINT, ADVNAME,sdt.taxpercent
			 * from EMPLOYEEADVANCES empadv,FINANCIALYEAR fy,ADVANCESMASTER adm,
			 * sbilendingratesmt smt, sbilendingratesdet sdt where adm.ADVCD =
			 * empadv.ADVCD and empno = '"+empno+"' and empadv.FYEARCD =
			 * fy.FYEARCD and empadv.fyearcd = '"+fyearcd+"' and smt.fyearcd =
			 * empadv.fyearcd and smt.advcd = empadv.advcd and smt.sbiratecd =
			 * sdt.sbiratecd and empadv.ADVAMT between fromamount and toamount
			 * and adm.calcperk = 'Y' and (empadv.noofinstallments / 12) between
			 * fromyear and toyear"; log.info("forcalculating perks query:
			 * "+advqry); ResultSet rs4 = db.getRecordSet(advqry); log.info("rs4
			 * "+rs4); while(rs4.next()){
			 * //log.info(".........................."); psinfoo = new
			 * PaySlipInfo();
			 * psinfoo.setEarnDeduName("PERK-"+rs4.getString("ADVNAME"));
			 * psinfoo.setType("ADV"); double amt =
			 * Double.parseDouble(rs4.getString("ADVAMT")); double am =
			 * (amt*rs4.getDouble("taxpercent"))/100;//todo: if RATEOFINT is
			 * more than SBI rate then we have to calculate and add as perk
			 * otherwise no need psinfoo.setAmount(am+""); log.info("perk "+am+"
			 * nm: "+psinfoo.getEarnDeduName()); grossamt+=am;
			 * earnningList.add(psinfoo); }
			 */
			db.closeRs();
			// log.info("grossamt "+grossamt);
			empPayInfo.setEmpInfo(empinfo);
			empPayInfo.setItrecamt(rec);
			empPayInfo.setEarnList(earnningList);
			empPayInfo.setDeducList(deducList);
			empPayInfo.setGrossAmt(Double.toString(grossamt));
			empPayInfo.setDeducAmt(deducamt);
			empPayInfo.setPerkAmt(perkamt);
			empPayInfo.setEtot(earnamt);
			empPayInfo.setNetAmt(grossamt - netamt);

			empPayList.add(empPayInfo);
			// }

		} catch (Exception e) {
			log
				.info("<<<<<<<<<< Exception ------>>>>>>>>>>>>"
					+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				// db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , getProjectedIncomeTaxInfo() : "
						+ e.getMessage());
			}
		}
		return empPayList;
	}

	public double exemptedTaxAmount(double savingAmt, double limitAmt) {
		double amt = 0.0;
		// log.info("savingAm "+savingAmt+" limitAmt "+limitAmt+"savingAmt <
		// limitAmt) "+(savingAmt < limitAmt));
		if (limitAmt != 0) {
			if (savingAmt < limitAmt) {
				// log.info("22222222222222222");
				amt = savingAmt;
			} else {
				// log.info("33333333333333333333333");
				amt = limitAmt;
			}
		} else {
			amt = savingAmt;
		}
		return amt;
	}

	// End: Savings Declaraion Done By Srilakshmi 15/09/09

	public void saveProfessionalTaxSlab(ProfessionalTaxSlabInfo slabInfo)
		throws ApplicationException {
		int slabMtCd = 0, count = 0;
		int slabDtCd = 0;
		String name="";
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);

			String finYearQuery = "";

			finYearQuery = "select count(*) from professionaltaxslab where FYEARCD="
				+ slabInfo.getTaxYear();
			//log.info("------finYearQuery--------" + finYearQuery);

			count = db.getRecordCount(finYearQuery);

			if (count > 0)
				throw new ApplicationException("Financial Year already exists");

			String[] taxDetails = slabInfo.getTaxDetails();

			for (int i = 0; i < taxDetails.length; i++) {

				slabMtCd = Integer.parseInt(AutoGeneration.getNextCode(
					"professionaltaxslab", "PROFESSIONALTXSLABCD", 10, con));

				String insDetailsQry = "";
				StringTokenizer st = new StringTokenizer(taxDetails[i], "|");
				while (st.hasMoreTokens()) {

					String fromAmount = st.nextToken();
					String toAmount = st.nextToken();
					String taxPercent = st.nextToken();

					slabDtCd = Integer.parseInt(AutoGeneration.getNextCode(
						"INCOMETAXSLABDETAILS", "TAXSLABDETCD", 10, con));
					insDetailsQry = "INSERT INTO professionaltaxslab(PROFESSIONALTXSLABCD,FYEARCD,FROMSAL,TOSAL,PROFTAX,FROMDATE) VALUES('"
						+ slabMtCd
						+ "','"
						+ slabInfo.getTaxYear()
						+ "','"
						+ fromAmount
						+ "','"
						+ toAmount
						+ "','"
						+ taxPercent
						+ "','" + slabInfo.getFromDate() + "')";

					//log.info("------insDetailsQry--------" + insDetailsQry);
					db.executeUpdate(insDetailsQry);
				}
			}
			String qry="select fyearnm from financialyear where fyearcd='"+slabInfo.getTaxYear()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next()){
				 name=rs.getString("FYEARNM");}
			String activityDesc="Professional Tax New For Financial Year "+name+" Saved.";
			ActivityLog.getInstance().write(slabInfo.getUsercd(), activityDesc, "N", String.valueOf(slabMtCd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch, saveSlab() : " + e.getMessage());
			throw new ApplicationException("Financial Year already exists");
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, saveSlab() : "
					+ e.getMessage());
			}
		}
	}

	public List searchProfessionalTaxSlab(ProfessionalTaxSlabInfo searchSlabInfo) {
		List slabList = new ArrayList();
		ResultSet rs2 = null;
		try {
			db.makeConnection();
			con = db.getConnection();
			String searhSlabquery = "";
			/*
			 * String finquery="SELECT FYEARCD,TO_CHAR(FROMDATE,'DD/Mon/YY')
			 * FROMDATE, TO_CHAR(TODATE,'DD/Mon/YY') TODATE FROM FINANCIALYEAR
			 * where FYEARCD is not null"; if
			 * (!searchSlabInfo.getTaxYear().equals("")) { finquery+=" and
			 * FYEARCD='"+searchSlabInfo.getTaxYear()+"'"; }
			 * 
			 * log.info("------finquery------"+finquery);
			 * //rs2=db.getRecordSet(finquery);
			 * 
			 * if(rs2.next()){
			 * finyear=rs2.getString("FROMDATE")+"-"+rs2.getString("TODATE"); }
			 */

			// searhSlabquery = "SELECT
			// PROFESSIONALTXSLABCD,FROMSAL,TOSAL,PROFTAX FROM
			// professionaltaxslab WHERE PROFESSIONALTXSLABCD IS NOT NULL ";
			searhSlabquery = "SELECT   ptl.professionaltxslabcd, ptl.fromsal,ptl.tosal,ptl.proftax,ptl.fyearcd ,to_char(fy.fromdate,'dd/Mon/yyyy') || '-' || to_char(fy.todate,'dd/Mon/yyyy') finyear  FROM professionaltaxslab ptl,financialyear fy    WHERE  ptl.FYEARCD = fy.FYEARCD and   professionaltxslabcd IS NOT NULL ";
			if (!searchSlabInfo.getTaxYear().equals("")) {
				searhSlabquery += " AND fy.FYEARCD='"
					+ searchSlabInfo.getTaxYear() + "'";
			}
			searhSlabquery += " ORDER BY PROFESSIONALTXSLABCD ";

			//log.info("------searhSlabquery--------" + searhSlabquery);
			rs = db.getRecordSet(searhSlabquery);

			ProfessionalTaxSlabInfo slabInfo;
			while (rs.next()) {
				slabInfo = new ProfessionalTaxSlabInfo();
				slabInfo.setProfessionalslabCd(rs
					.getString("PROFESSIONALTXSLABCD"));
				slabInfo.setFromAmount(rs.getString("FROMSAL"));
				slabInfo.setToAmount(rs.getString("TOSAL"));
				slabInfo.setTaxPercent(rs.getString("PROFTAX"));
				slabInfo.setFinYear(rs.getString("finyear"));

				slabList.add(slabInfo);
			}
		} catch (Exception e) {
			log.info("Exception in catch, searchSlab() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , searchSlab() : "
					+ e.getMessage());
			}
		}
		return slabList;
	}

	public SavingsDeclarationInfo showEditSavingsDeclaration(String savDeclCd) {
		SavingsDeclarationInfo sdinfo = new SavingsDeclarationInfo();
		SavingsDeclarationInfo sdinfo1 = new SavingsDeclarationInfo();

		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List sdList = new ArrayList();
		List nscList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();

			String query1 = "select * from savingsdeclartion where status='A' and SAVDECLCD="
				+ savDeclCd;
			//log.info("query1 " + query1);
			// String query2="select
			// SAVDECDETCD,SAVDECLCD,NAME,sd.SAVINGSCD,SAVINGAMT,PERCENT,SAVINGNAME
			// from savingsdeclartiondet sd,savingsmaster sm where
			// sm.savingscd=sd.savingscd and SAVDECLCD="+savDeclCd+" order by
			// SAVDECLCD";
			String query2 = "select * from savingsmaster sm1,(SELECT savdecdetcd, savdeclcd, NAME, sd.savingscd, savingamt, PERCENT,savingname FROM savingsdeclartiondet sd, savingsmaster sm  WHERE sd.savingscd = sm.savingscd AND savdeclcd = "
				+ savDeclCd + ")sdet  where sm1.SAVINGSCD = sdet.savingscd(+)";
			//log.info("query2: " + query2);

			// String query3="select
			// SAVDECLCD,FYEARCD,AMOUNT,INTERESTAMT,(select Savingname from
			// savingsmaster m where m.savingscd = 8 and ns.SAVDECLCD =
			// "+savDeclCd+") SAVINGNAME,(select savingscd from savingsmaster m
			// where m.savingscd =8 and ns.SAVDECLCD = "+savDeclCd+")
			// savingscd,(SELECT TO_CHAR(FROMDATE, 'DD/Mon/YY') || '-'
			// ||TO_CHAR(TODATE, 'DD/Mon/YY') FYEAR FROM FINANCIALYEAR fy where
			// fy.FYEARCD = ns.FYEARCD) FYEAR,(SELECT FYEARCD FROM FINANCIALYEAR
			// fy where fy.FYEARCD = ns.FYEARCD) FYEARCD from savingsnscdet ns
			// where SAVDECLCD = "+savDeclCd+" order by SAVDECLCD";
			/*log
				.info("select SAVDECLCD,FYEARCD,AMOUNT,INTERESTAMT,(select Savingname from savingsmaster m where m.savingscd = 8 and ns.SAVDECLCD = "
					+ savDeclCd
					+ ") SAVINGNAME,(select savingscd from savingsmaster m where m.savingscd =8 and ns.SAVDECLCD = "
					+ savDeclCd
					+ ") savingscd,(SELECT TO_CHAR(FROMDATE, 'DD/Mon/YY') || '-' ||TO_CHAR(TODATE, 'DD/Mon/YY') FYEAR FROM FINANCIALYEAR fy where fy.FYEARCD = ns.FYEARCD) FYEAR,(SELECT FYEARCD FROM FINANCIALYEAR fy where fy.FYEARCD = ns.FYEARCD) FYEARCD from savingsnscdet ns where SAVDECLCD = "
					+ savDeclCd + " ");*/
			rs = db.getRecordSet(query1);
			rs1 = db.getRecordSet(query2);
			// rs2=db.getRecordSet(query3);
			if (rs.next()) {
				sdinfo = new SavingsDeclarationInfo();
				sdinfo.setSavDeclCd(rs.getString("SAVDECLCD"));
				sdinfo.setFinyearcd(rs.getString("FYEARCD"));
				sdinfo.setEmpno(rs.getString("EMPNO"));
				sdinfo.setEmpName(sh.getDescription("employeeinfo",
					"EMPLOYEENAME", "empno", rs.getString("EMPNO"), con));
				sdinfo
					.setFinYearName(sh
						.getDescription(
							"financialyear",
							"to_char(FROMDATE,'dd/Mon/YYYY')||'-'||to_char(TODATE,'dd/Mon/YYYY')",
							"FYEARCD", rs.getString("FYEARCD"), con));
			}
			while (rs1.next()) {
				sdinfo1 = new SavingsDeclarationInfo();
				sdinfo1.setSavDecDetCd(rs1.getString("SAVDECDETCD"));
				sdinfo1.setSavDeclCd(rs1.getString("SAVDECLCD"));
				sdinfo1.setSavingsCd(rs1.getString("SAVINGSCD"));
				sdinfo1.setName(rs1.getString("NAME") == null ? " " : rs1
					.getString("NAME"));
				sdinfo1.setSavingsName(rs1.getString("SAVINGNAME"));
				sdinfo1.setSavingAmt(rs1.getString("SAVINGAMT") == null ? "0"
					: rs1.getString("SAVINGAMT"));
				sdinfo1.setPercent(rs1.getString("PERCENT") == null ? "0" : rs1
					.getString("PERCENT"));
				// log.info("adding into sdList");
				sdList.add(sdinfo1);
			}
			/*
			 * while(rs2.next()){ sdinfo1 = new SavingsDeclarationInfo();
			 * sdinfo1.setSavDeclCd(rs2.getString("SAVDECLCD"));
			 * sdinfo1.setSavingsCd(rs2.getString("savingscd"));
			 * sdinfo1.setSavingsName(rs2.getString("SAVINGNAME"));
			 * sdinfo1.setFinYearName(rs2.getString("FYEAR"));
			 * sdinfo1.setFinyearcd(rs2.getString("FYEARCD"));
			 * sdinfo1.setNscAmount(rs2.getString("AMOUNT"));
			 * sdinfo1.setInterestamt(rs2.getString("INTERESTAMT"));
			 * nscList.add(sdinfo1); }
			 */
			//log.info("sdlist size " + sdList.size());
			sdinfo.setSavingDt(sdList);
			sdinfo.setNscDt(nscList);
		} catch (Exception e) {
			log.info("Exception in catch, showEditSavingsDeclaration() : "
				+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , showEditSavingsDeclaration() : "
						+ e.getMessage());
			}
		}
		return sdinfo;
	}

	public void updateSavingsDeclaration(SavingsDeclarationInfo info) {
		String sSQL = "";
		String detStr = "";
		double totSavings = 0.0;
		StringBuffer detQry = new StringBuffer();
		String name="";
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			//log.info("----decl----------" + info.getSavDeclCd());
			db
				.executeUpdate("update savingsdeclartion set status='I' where SAVDECLCD="
					+ info.getSavDeclCd());
			//log.info("update savingsdeclartion set status='I' where SAVDECLCD="+ info.getSavDeclCd());
			int cd = Integer.parseInt(AutoGeneration.getNextCode(
				"savingsdeclartion", "savdeclcd", 10, con));
			sSQL = "Insert into savingsdeclartion(savdeclcd,FYEARCD,empno,status) values('"
				+ cd
				+ "','"
				+ info.getFinyearcd()
				+ "','"
				+ info.getEmpno()
				+ "','A')";
			db.executeUpdate(sSQL);
			String[] detArray = info.getDetArray();
			int arrLen = detArray.length;
			int detcd = 0;
			//log.info("...........    " + detArray.length);
			for (int i = 0; i < arrLen; i++) {
				detStr = detArray[i];
				log.info("Details string is ---" + i + "---- " + detStr);
				detQry.delete(0, detQry.length());

				String[] details = getStringArrayFromStr(detStr, "|");// detStr.split("|");

				//log.info("details length is ======= " + details.length);
				//log.info("--------SavingsCd------------------" + details[1]);
				// if(!"8".equals(details[1])){
				detcd = Integer.parseInt(AutoGeneration.getNextCode(
					"savingsdeclartiondet", "savdecdetcd", 10, con));
				if ("12".equals(details[1]))
					detQry.append("Insert into savingsdeclartiondet values ('"
						+ detcd + "','" + cd + "','" + details[1] + "','"
						+ details[2] + "','" + details[3] + "','" + details[4]
						+ "')");
				else
					detQry
						.append("Insert into savingsdeclartiondet(SAVDECDETCD,SAVDECLCD,SAVINGSCD,NAME,SAVINGAMT) values ('"
							+ detcd
							+ "','"
							+ cd
							+ "','"
							+ details[1]
							+ "','"
							+ details[2] + "','" + details[3] + "')");
				//log.info("detQry in otherthan NSC " + detQry.toString());
				db.executeUpdate(detQry.toString());
				// }
				/*
				 * else if("8".equals(details[1])){
				 * 
				 * int detcd1 =
				 * Integer.parseInt(AutoGeneration.getNextCode("savingsdeclartiondet",
				 * "savdecdetcd", 10, con)); detQry.append("Insert into
				 * savingsdeclartiondet(SAVDECDETCD,SAVDECLCD,SAVINGSCD) values
				 * ('"+detcd1+"','"+cd+"','"+details[1]+"')"); log.info("Master
				 * Detail in NSC "+detQry.toString());
				 * db.executeUpdate(detQry.toString());
				 * detQry.delete(0,detQry.length()); String[] nscFcdArray =
				 * getStringArrayFromStr(details[2],":");//details[1].split(":");
				 * String[] nscAmtArray =
				 * getStringArrayFromStr(details[3],":");//details[2].split(":");
				 * String[] nscIntrArray =
				 * getStringArrayFromStr(details[4],":");//)details[3].split(":");
				 * double nscsavings = 0.0; for(int k=0;k<nscFcdArray.length;k++){
				 * detQry.delete(0,detQry.length()); detcd =
				 * Integer.parseInt(AutoGeneration.getNextCode("SAVINGSNSCDET",
				 * "SAVINGSNSCDETCD", 10, con)); detQry.append("Insert into
				 * SAVINGSNSCDET values ('"+detcd+"','"+cd+"',");
				 * detQry.append("'"+nscFcdArray[k]+"','"+nscAmtArray[k]+"','"+nscIntrArray[k]+"'");
				 * detQry.append(")"); log.info(" Detail Query in NSC
				 * "+detQry.toString()); nscsavings +=
				 * Double.parseDouble(nscAmtArray[k]);
				 * db.executeUpdate(detQry.toString()); } sSQL = " Update
				 * savingsdeclartiondet set SAVINGAMT="+nscsavings+" where
				 * SAVDECDETCD='"+detcd1+"' "; log.info(" Update
				 * savingsdeclartiondet set SAVINGAMT="+nscsavings+" where
				 * SAVDECDETCD='"+detcd1+"' "); db.executeUpdate(sSQL); }
				 */
				// log.info("Details Savings Amount is ---------- "+details[2]);
				// totSavings += Double.parseDouble(details[2]);
			}
			// sSQL = " Update savingsdeclartion set totalsaving="+totSavings+"
			// where savdeclcd='"+cd+"' ";
			// db.executeUpdate(sSQL);
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpno()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Savings Declaration  Of "+name+" Updated.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "E", String.valueOf(cd), con);
			db.commitTrans();
			//log.info("Total Savings Amount is ---------- " + totSavings);
		} catch (Exception e) {
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				log
					.info("Exception in Rollback Trans , updateSavingsDeclaration() : "
						+ e1.getMessage());
				e1.printStackTrace();
			}
			log.info("Exception in catch , updateSavingsDeclaration() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , updateSavingsDeclaration() : "
						+ e.getMessage());
			}
		}
	}

	public SavingsDeclarationInfo showSavingsDeclarationDetails(String savDeclCd) {
		SavingsDeclarationInfo sdinfo = new SavingsDeclarationInfo();
		SavingsDeclarationInfo sdinfo1 = new SavingsDeclarationInfo();

		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List sdList = new ArrayList();
		List nscList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();

			String query1 = "select * from savingsdeclartion where status='A' and SAVDECLCD="
				+ savDeclCd;
			//log.info(query1);
			// String query2 = " Select
			// SAVDECDETCD,SAVDECLCD,NAME,sd.SAVINGSCD,SAVINGAMT,PERCENT,SAVINGNAME
			// from savingsdeclartiondet sd,savingsmaster sm where
			// sm.savingscd=sd.savingscd and NAME is not null and
			// SAVDECLCD="+savDeclCd+" order by SAVDECLCD";
			String query2 = " Select SAVDECDETCD,SAVDECLCD,NAME,sd.SAVINGSCD,SAVINGAMT,PERCENT,SAVINGNAME from savingsdeclartiondet sd,savingsmaster sm where  sm.savingscd=sd.savingscd  and SAVDECLCD="
				+ savDeclCd + " order by SAVDECLCD";
			//log.info(query2);

			String query3 = "select SAVDECLCD,FYEARCD,AMOUNT,INTERESTAMT,(select Savingname from savingsmaster m where m.savingscd = 8 and ns.SAVDECLCD = "
				+ savDeclCd
				+ ") SAVINGNAME,(select savingscd from savingsmaster m where m.savingscd =8 and ns.SAVDECLCD = "
				+ savDeclCd
				+ ") savingscd,(SELECT TO_CHAR(FROMDATE, 'DD/Mon/YY') || '-' ||TO_CHAR(TODATE, 'DD/Mon/YY') FYEAR FROM FINANCIALYEAR fy where fy.FYEARCD = ns.FYEARCD) FYEAR,(SELECT FYEARCD FROM FINANCIALYEAR fy where fy.FYEARCD = ns.FYEARCD) FYEARCD from savingsnscdet ns where SAVDECLCD = "
				+ savDeclCd + " order by SAVDECLCD";
			//log.info(query3);
			rs = db.getRecordSet(query1);
			rs1 = db.getRecordSet(query2);
			rs2 = db.getRecordSet(query3);
			if (rs.next()) {
				sdinfo = new SavingsDeclarationInfo();
				sdinfo.setSavDeclCd(rs.getString("SAVDECLCD"));
				sdinfo.setFinyearcd(rs.getString("FYEARCD"));
				sdinfo.setEmpno(rs.getString("EMPNO"));
				sdinfo.setEmpName(sh.getDescription("employeeinfo",
					"EMPLOYEENAME", "empno", rs.getString("EMPNO"), con));
				sdinfo
					.setFinYearName(sh
						.getDescription(
							"financialyear",
							"to_char(FROMDATE,'dd/Mon/YYYY')||'-'||to_char(TODATE,'dd/Mon/YYYY')",
							"FYEARCD", rs.getString("FYEARCD"), con));
			}
			while (rs1.next()) {
				sdinfo1 = new SavingsDeclarationInfo();
				sdinfo1.setSavDecDetCd(rs1.getString("SAVDECDETCD"));
				sdinfo1.setSavDeclCd(rs1.getString("SAVDECLCD"));
				sdinfo1.setSavingsCd(rs1.getString("SAVINGSCD"));
				sdinfo1.setName(rs1.getString("NAME") == null ? " " : rs1
					.getString("NAME"));
				sdinfo1.setSavingsName(rs1.getString("SAVINGNAME"));
				sdinfo1.setSavingAmt(rs1.getString("SAVINGAMT"));
				sdinfo1.setPercent(rs1.getString("PERCENT"));
				sdList.add(sdinfo1);
			}
			while (rs2.next()) {
				sdinfo1 = new SavingsDeclarationInfo();
				sdinfo1.setSavDeclCd(rs2.getString("SAVDECLCD"));
				sdinfo1.setSavingsCd(rs2.getString("savingscd"));
				sdinfo1.setSavingsName(rs2.getString("SAVINGNAME"));
				sdinfo1.setFinYearName(rs2.getString("FYEAR"));
				sdinfo1.setFinyearcd(rs2.getString("FYEARCD"));
				sdinfo1.setNscAmount(rs2.getString("AMOUNT"));
				sdinfo1.setInterestamt(rs2.getString("INTERESTAMT"));
				nscList.add(sdinfo1);
			}
			sdinfo.setSavingDt(sdList);
			sdinfo.setNscDt(nscList);
		} catch (Exception e) {
			log.info("Exception in catch, showEditSavingsDeclaration() : "
				+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , showEditSavingsDeclaration() : "
						+ e.getMessage());
			}
		}
		return sdinfo;
	}

	public void addEmpMiscIT(EmpMiscITInfo info) {
		String name="";
		try {
			db.makeConnection();
			con = db.getConnection();
			// int cd =
			// AutoGeneration.getNextCode("empmiscitdetails","empmiscitcd",10,con);
			String instrQry = " Insert into empmiscitdetails(empmiscitcd,empno,FYEARCD,ltc,bonus,ota,medical,lease,status,usercd) values (get_nextCode('empmiscitdetails','empmiscitcd','10'),'"
				+ info.getEmpno()
				+ "','"
				+ info.getFinyearcd()
				+ "','"
				+ info.getLtc()
				+ "','"
				+ info.getBonus()
				+ "','"
				+ info.getOta()
				+ "','"
				+ info.getMedical()
				+ "','"
				+ info.getLease() + "','A','" + info.getUsercd() + "') ";
			//log.info("addEmpMiscIT instrQry is ---- " + instrQry);
			db.executeUpdate(instrQry);
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpno()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Employee Miscellaneous IT Earnings Of "+name+" Saved.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "N", String.valueOf(info.getEmpno()), con);
		} catch (Exception e) {
			log.info("Exception in catch , addEmpMiscIT() : " + e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , addEmpMiscIT() : "
					+ e.getMessage());
			}
		}
	}

	public List searchEmpMiscIT(EmpMiscITInfo info) {
		List infoList = new ArrayList();
		// EmpMiscITInfo info
		try {
			db.makeConnection();
			con = db.getConnection();
			StringBuffer srchQry = new StringBuffer();
			srchQry
				.append(" Select empmiscitcd,(EMPLOYEENAME)empname,ltc,bonus,ota,medical,lease,(to_char(FROMDATE,'dd/Mon/yyyy')||' - '||to_char(TODATE,'dd/Mon/yyyy'))fyearname from  empmiscitdetails it,employeeinfo e,financialyear f where empmiscitcd is not null and it.empno=e.empno and it.fyearcd=f.fyearcd");
			if (!info.getEmpno().equals("")) {
				srchQry.append(" and it.empno='" + info.getEmpno() + "' ");
			}
			log.info("searchEmpMiscIT srchQry is ---- " + srchQry);
			rs = db.getRecordSet(srchQry.toString());
			while (rs.next()) {
				info = new EmpMiscITInfo();
				info.setEmpmiscitcd(rs.getString("empmiscitcd"));
				info.setEmpno(rs.getString("empname"));
				// info.setEmpname(rs.getString("empname"));
				info.setFinyearcd(rs.getString("fyearname"));
				info.setLtc(rs.getString("ltc"));
				info.setBonus(rs.getString("bonus"));
				info.setOta(rs.getString("ota"));
				info.setMedical(rs.getString("medical"));
				info.setLease(rs.getString("lease"));
				infoList.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch , searchEmpMiscIT() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , searchEmpMiscIT() : "
					+ e.getMessage());
			}
		}
		return infoList;
	}

	public void addSBILendingRates(SBILendingRatesInfo info)
		throws ApplicationException {
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String insMasterQry = "";
			String[] taxDetails = info.getTaxpercentdetails();
			int cnt = 0;
			cnt = db
				.getRecordCount("select count(*) from sbilendingratesmt where advcd='"
					+ info.getAdvcd()
					+ "' and fyearcd='"
					+ info.getFyearcd()
					+ "' ");
			if (cnt > 0) {
				throw new ApplicationException(
					"Tax Percent Details have been already entered for the selected Advance and Financial Year");
			}
			int rateMtCd = Integer.parseInt(AutoGeneration.getNextCode(
				"sbilendingratesmt", "sbiratecd", 10, con));

			insMasterQry = "INSERT INTO sbilendingratesmt(sbiratecd,advcd,FYEARCD) VALUES('"
				+ rateMtCd
				+ "','"
				+ info.getAdvcd()
				+ "','"
				+ info.getFyearcd() + "')";
			db.executeUpdate(insMasterQry);
			for (int i = 0; i < taxDetails.length; i++) {
				String insDetailsQry = "";
				StringTokenizer st = new StringTokenizer(taxDetails[i], "|");
				while (st.hasMoreTokens()) {
					String fromyear = st.nextToken();
					String toyear = st.nextToken();
					String fromAmount = st.nextToken();
					String toAmount = st.nextToken();
					String taxPercent = st.nextToken();

					int rateDtCd = Integer.parseInt(AutoGeneration.getNextCode(
						"sbilendingratesdet", "sbiratedetcd", 10, con));
					insDetailsQry = "INSERT INTO sbilendingratesdet(sbiratedetcd,sbiratecd,fromyear,toyear,FROMAMOUNT,TOAMOUNT,TAXPERCENT) VALUES('"
						+ rateDtCd
						+ "','"
						+ rateMtCd
						+ "','"
						+ fromyear
						+ "','"
						+ toyear
						+ "','"
						+ fromAmount
						+ "','"
						+ toAmount + "','" + taxPercent + "')";
					db.executeUpdate(insDetailsQry);
				}
			}
			db.commitTrans();
		} catch (Exception e) {
			log.info("Exception in catch , addSBILendingRates() : "
				+ e.getMessage());
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch , addSBILendingRates() : "
					+ e.getMessage());
			}
		}
	}

	public List searchSBILendingRates(SBILendingRatesInfo info) {
		List infoList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();

			StringBuffer srchQry = new StringBuffer();
			srchQry
				.append(" Select sbiratecd,mt.advcd,advname,mt.fyearcd,(to_char(FROMDATE,'dd/Mon/yyyy')||' - '||to_char(TODATE,'dd/Mon/yyyy'))fyearname from  sbilendingratesmt mt,advancesmaster am,financialyear f where sbiratecd is not null and mt.advcd=am.advcd and mt.fyearcd=f.fyearcd  and mt.status ='A' ");
			if (!info.getAdvcd().equals("")) {
				srchQry.append(" and mt.advcd='" + info.getAdvcd() + "' ");
			}
			log.info("searchSBILendingRates srchQry is ---- " + srchQry);
			rs = db.getRecordSet(srchQry.toString());
			while (rs.next()) {
				info = new SBILendingRatesInfo();
				info.setSbiratecd(rs.getString("sbiratecd"));
				info.setAdvcd(rs.getString("advname"));
				info.setFyearcd(rs.getString("fyearname"));

				infoList.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch , searchSBILendingRates() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , searchSBILendingRates() : "
						+ e.getMessage());
			}
		}
		return infoList;
	}

	public List showSBILendingRatesDetails(SBILendingRatesInfo info) {
		List infoList = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();

			StringBuffer srchQry = new StringBuffer();
			srchQry
				.append(" select advname,mt.fyearcd,(to_char(FROMDATE,'dd/Mon/yyyy')||' - '||to_char(TODATE,'dd/Mon/yyyy'))fyearname,fromyear,toyear,fromamount,toamount,taxpercent from sbilendingratesmt mt,sbilendingratesdet dt,advancesmaster am,financialyear f where mt.advcd=am.advcd and mt.fyearcd=f.fyearcd and mt.sbiratecd=dt.sbiratecd  and detstatus='A' and mt.status ='A' and  mt.sbiratecd='"
					+ info.getSbiratecd() + "'");

			log.info("showSBILendingRatesDetails srchQry is ---- " + srchQry);
			rs = db.getRecordSet(srchQry.toString());
			while (rs.next()) {
				info = new SBILendingRatesInfo();
				// info.setSbiratecd(rs.getString("sbiratecd"));
				info.setAdvcd(rs.getString("advname"));
				info.setFyearcd(rs.getString("fyearname"));
				info.setFromyear(rs.getString("fromyear"));
				info.setToyear(rs.getString("toyear"));
				info.setFromamount(rs.getString("fromamount"));
				info.setToamount(rs.getString("toamount"));
				info.setTaxpercent(rs.getString("Taxpercent"));

				infoList.add(info);
			}
		} catch (Exception e) {
			log.info("Exception in catch , showSBILendingRatesDetails() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , showSBILendingRatesDetails() : "
						+ e.getMessage());
			}
		}
		return infoList;
	}

	public List showSBILendingRatesEdit(SBILendingRatesInfo info) {
		List list = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();
			String editSlabSql = " select mt.sbiratecd,sbiratedetcd,advname,mt.fyearcd,(to_char(FROMDATE,'dd/Mon/yyyy')||' - '||to_char(TODATE,'dd/Mon/yyyy'))fyearname,fromyear,toyear,fromamount,toamount,taxpercent from sbilendingratesmt mt,sbilendingratesdet dt,advancesmaster am,financialyear f where mt.advcd=am.advcd and mt.fyearcd=f.fyearcd and mt.sbiratecd=dt.sbiratecd and detstatus='A' and mt.status ='A' and mt.sbiratecd='"
				+ info.getSbiratecd() + "'";

			rs = db.getRecordSet(editSlabSql);
			while (rs.next()) {
				info = new SBILendingRatesInfo();
				// info.setSbiratecd(rs.getString("sbiratecd"));
				info.setSbiratecd(rs.getString("sbiratecd"));
				info.setSbiratedetcd(rs.getString("sbiratedetcd"));
				info.setAdvcd(rs.getString("advname"));
				info.setFyearcd(rs.getString("fyearname"));
				info.setFromyear(rs.getString("fromyear"));
				info.setToyear(rs.getString("toyear"));
				info.setFromamount(rs.getString("fromamount"));
				info.setToamount(rs.getString("toamount"));
				info.setTaxpercent(rs.getString("Taxpercent"));
				list.add(info);
			}

		} catch (Exception e) {
			log.info("Exception in catch, showSBILendingRatesEdit() : "
				+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch, showSBILendingRatesEdit() : "
						+ e.getMessage());
			}
		}
		return list;
	}

	public void updateSBILendingRates(SBILendingRatesInfo info) {
		String sSQL = "";
		String detStr = "";
		double totSavings = 0.0;
		StringBuffer detQry = new StringBuffer();
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			//log.info("----decl----------" + info.getSbiratecd());
			// db.executeUpdate("update savingsdeclartion set status='I' where
			// SAVDECLCD="+info.getSavDeclCd());
			// log.info("update savingsdeclartion set status='I' where
			// SAVDECLCD="+info.getSavDeclCd());
			// int cd =
			// Integer.parseInt(AutoGeneration.getNextCode("savingsdeclartion",
			// "savdeclcd", 10, con));
			// sSQL = "Insert into
			// savingsdeclartion(savdeclcd,FYEARCD,empno,status)
			// values('"+cd+"','"+info.getFinyearcd()+"','"+info.getEmpno()+"','A')";
			// db.executeUpdate(sSQL);
			detQry
				.append("Update sbilendingratesdet set detstatus='I' where sbiratecd='"
					+ info.getSbiratecd() + "'");
			db.executeUpdate(detQry.toString());
			String[] detArray = info.getTaxpercentdetails();
			int arrLen = detArray.length;
			int detcd = 0;
			for (int i = 0; i < arrLen; i++) {
				detStr = detArray[i];
				//log.info("Details string is ---" + i + "---- " + detStr);
				detQry.delete(0, detQry.length());

				String[] details = getStringArrayFromStr(detStr, "|");// detStr.split("|");

				//log.info("details length is ======= " + details.length);
				//log.info("--------sbiratedetcd------------------" + details[5]);

				int rateDtCd = Integer.parseInt(AutoGeneration.getNextCode(
					"sbilendingratesdet", "sbiratedetcd", 10, con));

				detQry
					.append("INSERT INTO sbilendingratesdet(sbiratedetcd,sbiratecd,fromyear,toyear,FROMAMOUNT,TOAMOUNT,TAXPERCENT) VALUES('"
						+ rateDtCd
						+ "','"
						+ info.getSbiratecd()
						+ "','"
						+ details[0]
						+ "','"
						+ details[1]
						+ "','"
						+ details[2]
						+ "','" + details[3] + "','" + details[4] + "')");
				db.executeUpdate(detQry.toString());

			}
			db.commitTrans();
			log.info("Total Savings Amount is ---------- " + totSavings);
		} catch (Exception e) {
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				log
					.info("Exception in Rollback Trans , updateSavingsDeclaration() : "
						+ e1.getMessage());
				e1.printStackTrace();
			}
			log.info("Exception in catch , updateSavingsDeclaration() : "
				+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log
					.info("Exception in finally catch , updateSavingsDeclaration() : "
						+ e.getMessage());
			}
		}
	}

	public void saveITExcelData(String path, String usercd) {

		DBAccess db = DBAccess.getInstance();
		Connection con = null;
		ResultSet rs = null;
		List emplist = new ArrayList();
		try {
			db.makeConnection();
			con = db.getConnection();
			// db.setAutoCommit(false);
			int empadvcd = 0;
			String exceldata = "";

			log.info("CommonUtil:readExcelSheet Entering method");
			String fileName = path;
			log.info("fileName " + fileName);

			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			try {
				Workbook workbook = Workbook
					.getWorkbook(new File(fileName), ws);
				Sheet s = workbook.getSheet(0);
				// Sheet s = workbook.getSheet(3);
				exceldata = readDataSheet(s);
			} catch (BiffException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}

			StringTokenizer st = new StringTokenizer(exceldata, "***");
			log.info("row count " + st.countTokens());
			int count = 0;
			int cnt = 0;
			while (st.hasMoreTokens()) {
				count++;
				String row = st.nextToken();
				empadvcd++;
				if (count == 1) {
					String cells[] = row.split("@@");
					String checkQry = " select count(*) from empincometax where  PAYROLLMONTHID=(select PAYROLLMONTHID from monthlypayroll where upper(PAYROLLMONTHNM)=upper('"
						+ cells[6].substring(0, 3)
						+ "') and PAYROLLYEAR='"
						+ cells[7] + "') ";
					cnt = db.getRecordCount(checkQry);
					db.closeRs();
				}
				if (cnt > 0) {
					log.info("Data has been already exists.");
					break;
				} else {
					insertITDetails(row, con, db, usercd); // (Sheet 9 -
					// Incometax)
				}
			}
			db.commitTrans();

		} catch (Exception e) {
			log.printStackTrace(e);
			e.printStackTrace();
			try {
				db.rollbackTrans();
			} catch (Exception e1) {

			}
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
	}

	private String readDataSheet(Sheet s) {
		log.info("CommonUtil:readDataSheet Entering method");
		Cell cell = null;
		StringBuffer eachRow = new StringBuffer();
		//log.info("Columns" + s.getColumns() + "Rows" + s.getRows());
		System.out.println("s " + s);
		String delimiter = "*", cellContent = "";
		// for (int j = 2; j< s.getRows(); j++) {
		for (int j = 1; j < s.getRows(); j++) {
			for (int i = 0; i < s.getColumns(); i++) {
				cell = s.getCell(i, j);
				if (!cell.getContents().equals("")) {
					cellContent = StringUtility.replace(
						cell.getContents().toCharArray(),
						delimiter.toCharArray(), "").toString();
					// System.out.println("Format"+cell.getType()+"Contents"+cell.getContents());
					if (!cellContent.equals("")) {
						String cellcontent = cell.getContents();

						eachRow.append(cellcontent + "@@");
					} else {
						eachRow.append(" " + "@@");
					}

				} else {
					eachRow.append(" " + "@@");
				}

			}

			eachRow.append("***");
		}
		log.info("CommonUtil:readDataSheet Leaving method");
		return eachRow.toString();
	}

	public void insertITDetails(String row, Connection con, DBAccess db,
		String usercd) {
		try {
			String cells[] = row.split("@@");

			String checkQry = " select count(*) from empincometax where STAFFCTGRYCD='"
				+ cells[4]
				+ "' and PAYROLLMONTHID=(select PAYROLLMONTHID from monthlypayroll where upper(PAYROLLMONTHNM)=upper('"
				+ cells[6].substring(0, 3)
				+ "') and PAYROLLYEAR='"
				+ cells[7]
				+ "') ";
			int cnt = 0;
			cnt = db.getRecordCount(checkQry);
			db.closeRs();
			int incometaxcd = 0;
			if (cnt == 0) {
				incometaxcd = Integer.parseInt(AutoGeneration.getNextCode(
					"empincometax", "EMPINCOMETAXCD", 5, con));
				String insrtQry = "Insert into empincometax(EMPINCOMETAXCD,STAFFCTGRYCD,PAYROLLMONTHID,usercd) values("
					+ incometaxcd
					+ ",'"
					+ cells[4]
					+ "',(select PAYROLLMONTHID from monthlypayroll where upper(PAYROLLMONTHNM)=upper('"
					+ cells[6].substring(0, 3)
					+ "') and PAYROLLYEAR='"
					+ cells[7] + "'),'" + usercd + "')";
				//log.info("insrtQry for empincometax is --- " + insrtQry);
				db.executeUpdate(insrtQry);
			}
			int incometaxdtcd = Integer.parseInt(AutoGeneration.getNextCode(
				"empincometaxdet", "EMPINCOMETAXDETCD", 5, con));
			String insrtQry = "Insert into empincometaxdet(EMPINCOMETAXDETCD,EMPINCOMETAXCD,EMPNO,INCOMETAX,STATUS) values("
				+ incometaxdtcd
				+ ",(select EMPINCOMETAXCD from empincometax where STAFFCTGRYCD=(select STAFFCTGRYCD from employeeinfo where emplnumber='"
				+ cells[1]
				+ "' and stationcd='"
				+ cells[0]
				+ "') and PAYROLLMONTHID=(select PAYROLLMONTHID from monthlypayroll where upper(PAYROLLMONTHNM)=upper('"
				+ cells[6].substring(0, 3)
				+ "') and PAYROLLYEAR='"
				+ cells[7]
				+ "') ),(select empno from employeeinfo where emplnumber='"
				+ cells[1]
				+ "' and stationcd='"
				+ cells[0]
				+ "'),'"
				+ cells[5]
				+ "','A')";
			//log.info("insrtQry for empincometaxdet is --- " + insrtQry);
			db.executeUpdate(insrtQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	ProjectedITDetail pit = null;
	public ProjectedITDetail getPITaxDetails() {
		return pit;
	}
	
	public static void main(String[] args) {
		EmployeeInfo emp = new EmployeeInfo();
			emp.setEmpNo("7");
			emp.setScode("17");
			IncomeTaxDAO.getInstance().getDetailedProjectedIncomeTaxInfo(emp);
			
			
		}	
    
	public Map getDetailedProjectedIncomeTaxInfo(EmployeeInfo emp) {
		Map projectedIT = new LinkedHashMap();
		pit = new ProjectedITDetail();
		try {
			db.makeConnection();
			con = db.getConnection();
			String empNo = emp.getEmpNo();
			String fyearCd = emp.getScode();
			StringBuffer empDetQry = new StringBuffer("select info.staffctgrycd,info.stationcd,info.PFTAXOPTION");
			empDetQry.append(" ,info.HRAOPTION,(select division from stationmaster sm where sm.stationcd");
			empDetQry.append("  = info.stationcd)division,(select PAYROLLMONTHNM||'-'||");
			empDetQry.append(" PAYROLLYEAR from monthlypayroll where payrollmonthid =( select ");
			empDetQry.append(" max(payrollmonthid) from payrollprocess where stationcd=info.stationcd and ");
			empDetQry.append(" payrollmonthid in (select payrollmonthid from monthlypayroll where fyearcd = ");
			empDetQry.append(fyearCd).append(" )))currdt,(select count(*) from payrollprocess where stationcd ");
			empDetQry.append(" =info.stationcd and payrollmonthid in (select payrollmonthid from monthlypayroll");
			empDetQry.append(" where fyearcd = ").append(fyearCd).append(" ))monthcnt,(select limitamount from");
			empDetQry.append(" SECTION where sectioncd = 1)section1limit,(select amount from empernsdeducs ");
			empDetQry.append(" where empno = '").append(empNo).append("' and earndeducd = '32')licensefee,(");
			empDetQry.append(" select amount from empernsdeducs where empno = '").append(empNo).append("' and");
			empDetQry.append(" earndeducd = (select earndeducd from configmapping where CONFIGNAME = 'Helpage'))");
			empDetQry.append(" helpage,(select amount from empernsdeducs where empno = '").append(empNo);
			empDetQry.append("' and earndeducd = 168)quartersrent from (select STAFFCTGRYCD,stationcd,");
			empDetQry.append(" PFTAXOPTION,HRAOPTION from employeeinfo ei where ei.empno = '").append(empNo);
			empDetQry.append("')info");
			String ctgry = null;
			String station = null;
			String stationdiv;
			String pftaxoption = null;
			String mnthnm = null;
			int mnthcnt = 0;
			String hraoption = null;
			double chptrlimt = 0;
			double licensefee;
			double helpageamt;
			double quartersrent;
			double totalIncome=0;
			double totalPerksAmt=0;
			double eol = 0;
			ResultSet empdetrs = db.getRecordSet(empDetQry.toString());
			//log.info("empDetQry        "+empDetQry.toString());
			if (empdetrs.next()) {
				ctgry = empdetrs.getString("STAFFCTGRYCD");
				station = empdetrs.getString("stationcd");
				stationdiv = empdetrs.getString("division");
				pftaxoption = empdetrs.getString("PFTAXOPTION");
				mnthnm = empdetrs.getString("currdt");
				mnthcnt = empdetrs.getInt("monthcnt");
				hraoption = empdetrs.getString("HRAOPTION");
				chptrlimt = empdetrs.getDouble("section1limit");
				licensefee = empdetrs.getDouble("licensefee");
				helpageamt = empdetrs.getDouble("helpage");
				quartersrent = empdetrs.getDouble("quartersrent");
			}
			pit.setAsOn(mnthnm);
			DateConverter dc =new DateConverter();
			pit.setDate(dc.getServerDate(con));
			db.closeRs();
			// Query to get Selected Employee Earnings and deductions data
			StringBuffer empErnDedQry = new StringBuffer(" select m.earndeducd,m.earndedunm, sum(Nvl(amount,0)) amount,sum(Nvl(ADJUSTMENTS,0)) adjamount, ");
			empErnDedQry.append("decode(m.earndeducd ,(select EARNDEDUCD from earndedumaster where isgprof='Y'), (select gprofamt * ").append("(12 - "+mnthcnt+") from emp_monthly_gprof where empno= "+empNo);
			empErnDedQry.append("and payrollmonthid=(select max(payrollmonthid) from emp_monthly_gprof where empno=").append(empNo).append(")),(Nvl((select mas.amount * ").append("(12 - "+mnthcnt+" ) from empernsdeducs mas where empno =") .append(empNo).append(" and m.EARNDEDUCD = mas.EARNDEDUCD), 0))) earnamt,");
			empErnDedQry.append(" m.isbasicsalary bp, slm.ledgercd, m.type,Nvl(ProjectedITOrder,0) ");
			empErnDedQry.append(" ProjectedITOrder,ProjectedITName from (select paytransid, empno, det.earndeducd, earn.type, led.ledgercd,det.trkamt+det.earndedamt amount,0 adjustments ");
			empErnDedQry.append(" from monthly_saltrack_det det, monthly_saltrack     mas, salhead_ledg_mapping led,");
			empErnDedQry.append(" earndedumaster earn,payrollprocess proc where mas.msaltrkcd = det.msaltrkcd and det.earndeducd = earn.earndeducd and");
			empErnDedQry.append(" led.earndeducd = earn.earndeducd and led.cadrecod = (select cadrecod from employeeinfo where empno=mas.empno)");
			empErnDedQry.append(" and proc.payrollmonthid = mas.payrollmonthid union select paytransid,empno,earndeducd,type,ledgercd,amount,adjustments from payrolldet) det,earndedumaster m, SALHEAD_LEDG_MAPPING ");
			empErnDedQry.append(" slm where m.EARNDEDUCD = slm.EARNDEDUCD and m.EARNDEDUCD = det.earndeducd and ");
			empErnDedQry.append(" det.paytransid in (select paytransid from payrollprocess where stationcd ='");
			empErnDedQry.append(station).append("'and empno = ").append(empNo).append(" and payrollmonthid in ");
			empErnDedQry.append(" (select payrollmonthid from monthlypayroll where fyearcd=").append(fyearCd);
			empErnDedQry.append(" )) and cadrecod = (select cadrecod from employeeinfo where empno = ");
			empErnDedQry.append(empNo).append(" ) group by m.earndeducd, m.earndedunm, m.isbasicsalary, ");
			empErnDedQry.append(" slm.ledgercd, m.type,ProjectedITOrder,ProjectedITName order by ProjectedITOrder");
			log.info("QQQQQQQQQQ"+empErnDedQry.toString());
			rs = db.getRecordSet(empErnDedQry.toString());
			log.info("--------");
			double donAmt = 0;
			double hraFromEmplr = 0;
			double basic = 0;
			double cpf = 0;
			double GSLIS = 0;
			double hraFromEmplrPay = 0;
			double basicPay = 0;
			double lic=0;
			int hracount=0;
			int qtrcount=0;
			int qtrcount1=0;
			// Pay && Allowances & Perks			
			ProjectedITDetail detail = null;
			String hrearndedname = null;
			String hraoptionqryCount1=null;
			SQLHelper help = new SQLHelper();
			while (rs.next()) {
				//log.info("............."+rs.getString("EARNDEDUNM"));
				log.info("............."+rs.getDouble("earnamt"));
				String ty = rs.getString("type");					
				if("70".equals(rs.getString("earndeducd"))){
					donAmt += rs.getDouble("amount")+rs.getDouble("adjamount")+rs.getDouble("earnamt");
				}
				if("32".equals(rs.getString("earndeducd"))){
					lic = rs.getDouble("amount")+rs.getDouble("adjamount")+rs.getDouble("earnamt");
				}
				if("168".equals(rs.getString("earndeducd"))){
					pit.setAddQuarters(rs.getDouble("amount"));
					pit.setAddQuartersproj(rs.getDouble("earnamt"));
					 
					hraoptionqryCount1="select count(*) from payrolldet det where PAYTRANSID in" +
		                "(select paytransid from payrollprocess where stationcd = '"+station+"' " +
		                "and empno = "+empNo+" and payrollmonthid in (select payrollmonthid from " +
		                "monthlypayroll where fyearcd = "+fyearCd+")) and  empno ="+empNo+" and EARNDEDUCD='"+rs.getString("earndeducd")+"' and amount>0";
		
				}				
				// HRA Amount received from Employer -- per year
				if (prop.getProperty("1".equals(ctgry) ? "EHRA" : "NEHRA").equals(rs.getString("ledgercd"))) {
					 
					String hraoptionqryCount="select count(*) from payrolldet det where PAYTRANSID in" +
					"(select paytransid from payrollprocess where stationcd = '"+station+"' " +
					"and empno = "+empNo+" and payrollmonthid in (select payrollmonthid from " +
					"monthlypayroll where fyearcd = "+fyearCd+")) and  empno ="+empNo+" and ledgercd='"+rs.getString("ledgercd")+"' and amount>0";
                        
					
					if("H".equals(hraoption)){						  
						 hracount=db.getRecordCount(hraoptionqryCount)+12-mnthcnt;
						 qtrcount=12-hracount;
						 qtrcount1=db.getRecordCount(hraoptionqryCount1);
						
						 
						 
					}else{
						hracount=db.getRecordCount(hraoptionqryCount);
						 qtrcount=12-hracount;
						 qtrcount1=db.getRecordCount(hraoptionqryCount1)+12-mnthcnt;
						
					}
					hraFromEmplr = rs.getDouble("amount");
					hraFromEmplrPay = rs.getDouble("earnamt");
					double excemptedAmt =0;
					double  hraFromEmplr40Perc = (basic * 40) / 100;					
					String savingsAmount = help.getDescriptionMulti("savingsdeclartiondet dt, savingsmaster mt","SAVINGAMT","dt.SAVINGSCD = 1 and dt.savingscd = mt.savingscd and SAVDECLCD = (select SAVDECLCD from savingsdeclartion  where empno = '"+empNo+"' and fyearcd = '"+fyearCd+"' and status = 'A')",con);
					log.info(""+savingsAmount);
					if(savingsAmount !=null  && !"".equals(savingsAmount) ){
					
						double hraFromEmpleLess10Perc = Double.parseDouble(savingsAmount) - (basic * 10) / 100;
						hraFromEmpleLess10Perc = (hraFromEmpleLess10Perc < 0 ? 0: hraFromEmpleLess10Perc);
						excemptedAmt = CommonUtil.getLeastValue(CommonUtil.getLeastValue(hraFromEmplr, hraFromEmpleLess10Perc),hraFromEmplr40Perc);
					}else {
						excemptedAmt = CommonUtil.getLeastValue(hraFromEmplr, hraFromEmplr40Perc);
					}
					excemptedAmt=excemptedAmt*hracount/12;
				
			}
				// Getting Basic Pay amount -- per year
				if ("Y".equals(rs.getString("bp").trim())) {
					basic = rs.getDouble("amount");
					basicPay = rs.getDouble("earnamt");
				}
				// CPF Amount -- per year
				if ("19".equals(rs.getString("earndeducd")) || "28".equals(rs.getString("earndeducd"))) {
					cpf += rs.getDouble("amount")+rs.getDouble("adjamount")+rs.getDouble("earnamt");
				}
				// GSLIS Amount -- per year
				if (prop.getProperty("GSLIS").equals(rs.getString("ledgercd"))) {
					GSLIS = rs.getDouble("amount")+rs.getDouble("adjamount")+rs.getDouble("earnamt");
				}
				if (!"0".equals(rs.getString("ProjectedITOrder"))) {
					double excemptedAmt = 0 ;					
					if(prop.getProperty("1".equals(ctgry) ? "EHRA" : "NEHRA").equals(rs.getString("ledgercd"))) {						
						double  hraFromEmplr40Perc = ((basic+basicPay) * 40) / 100;
						String savingsAmount = help.getDescriptionMulti("savingsdeclartiondet dt, savingsmaster mt","SAVINGAMT","dt.SAVINGSCD = 1 and dt.savingscd = mt.savingscd and SAVDECLCD = (select SAVDECLCD from savingsdeclartion  where empno = '"+empNo+"' and fyearcd = '"+fyearCd+"' and status = 'A')",con);
						if(savingsAmount !=null  && !"".equals(savingsAmount) ){
							double hraFromEmpleLess10Perc = Double.parseDouble(savingsAmount) - ((basic+basicPay) * 10) / 100;
							hraFromEmpleLess10Perc = (hraFromEmpleLess10Perc < 0 ? 0: hraFromEmpleLess10Perc);
							excemptedAmt = CommonUtil.getLeastValue(CommonUtil.getLeastValue((hraFromEmplr+hraFromEmplrPay), hraFromEmpleLess10Perc),hraFromEmplr40Perc);
						}else {
							excemptedAmt = CommonUtil.getLeastValue((hraFromEmplr+hraFromEmplrPay),hraFromEmplr40Perc);
						}
					}
					if("10".equals(rs.getString("earndeducd"))){
						String savingsAmount = help.getDescriptionMulti("empdependents","count(*)","empno='"+empNo+"'",con);
						excemptedAmt = Double.parseDouble(savingsAmount)*12*100;
						excemptedAmt = excemptedAmt>=2400?2400:excemptedAmt;
					}
					
					if (projectedIT
						.containsKey(rs.getString("ProjectedITName"))) {
						
						detail = (ProjectedITDetail) projectedIT.get(rs
							.getString("ProjectedITName"));
						
						detail.setEarnDedcName(rs.getString("ProjectedITName"));
						detail.setActualAmt(rs.getDouble("amount")+ detail.getActualAmt());
						detail.setProjectionAmt(rs.getDouble("earnamt")+ detail.getProjectionAmt());
						detail.setExemptedAmt(detail.getExemptedAmt()+excemptedAmt);
						detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
						projectedIT.put(rs.getString("ProjectedITName"), detail);
						if(rs.getDouble("earnamt")>0 || rs.getDouble("amount")>0){
							log.info(""+rs.getString("ProjectedITName"));
							log.info("eol  "+eol);
							eol += rs.getDouble("adjamount");
							log.info("eol111    "+eol);
								totalIncome += detail.getTotal();
							}
						/*
						log.info("if setEarnDedcName"+detail.getEarnDedcName());
						log.info("if setActualAmt"+detail.getActualAmt());
						log.info("if setProjectionAmt"+detail.getProjectionAmt());
						log.info("if setExemptedAmt"+detail.getExemptedAmt());
						log.info("if setTotal"+detail.getTotal());
						log.info(" if   totalIncome"+totalIncome);
						*/
						
					} else {
						
						/*
						 * For OTA Amount
						 */
						
						detail = new ProjectedITDetail();
						
						detail.setEarnDedcName(rs.getString("ProjectedITName"));
						if("176".equals(rs.getString("earndeducd"))){
							int otaamt=0;
							if(prop.getProperty("OTA").equals(rs.getString("ledgercd"))){
								String otaamount="select sum(NETT) from otatransaction_new_mt otam,roastermaster rm,otatransaction_new_dt otad where otam.ROASTERCD=rm.ROASTERCD and FYEARCD="+fyearCd+" and otad.TRANSACTIONNEWID=otam.TRANSACTIONNEWID and otad.empno="+empNo+" ";
							//log.info("otaamount         .."+otaamount);
								ResultSet rs1=null;
								
							rs1=db.getRecordSet(otaamount);
								if(rs1.next())
								{
									otaamt+=rs1.getDouble(1);
								}
								detail.setActualAmt(otaamt+rs.getDouble("amount"));
							}
						}
							else{
								detail.setActualAmt(rs.getDouble("amount"));
							}
						    //log.info("------------"+rs.getDouble("earnamt"));
							detail.setProjectionAmt(rs.getDouble("earnamt"));
							detail.setExemptedAmt(excemptedAmt);
							detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
							projectedIT.put(rs.getString("ProjectedITName"), detail);
							/*
							log.info("else setEarnDedcName"+detail.getEarnDedcName());
							log.info("else setActualAmt"+detail.getActualAmt());
							log.info("else setProjectionAmt"+detail.getProjectionAmt());
							log.info("else setExemptedAmt"+detail.getExemptedAmt());
							log.info("else setTotal"+detail.getTotal());
							log.info(" else   totalIncome"+totalIncome);
							*/
							if(rs.getDouble("earnamt")>0 || rs.getDouble("amount")>0){
								
								log.info(""+rs.getString("ProjectedITName"));
								log.info("eol  "+eol);
								if(rs.getString("EARNDEDUCD").equals("1")||rs.getString("EARNDEDUCD").equals("2")||rs.getString("EARNDEDUCD").equals("3")||rs.getString("EARNDEDUCD").equals("5")||rs.getString("EARNDEDUCD").equals("156")||rs.getString("EARNDEDUCD").equals("155")||rs.getString("EARNDEDUCD").equals("131")){
									eol += rs.getDouble("adjamount");
								}
									totalIncome += detail.getTotal();
							
									log.info("eol111    "+eol);
								}
						
					}
				}
			}// end while 
			db.closeRs();
			empErnDedQry = new StringBuffer("select empperk.projecteditname ProjectedITName,empperk.amount ");
			empErnDedQry.append(" amount,procperk.amount + payrollperk.amount payroll,payrollperk.ADJUSTMENTS ADJUSTMENTS,slm.ledgercd ledgercd from (select");
			empErnDedQry.append(" sum(amount) amount,pep.EARNDEDUCD,em.projecteditname from ");
			empErnDedQry.append(" PROCESSED_EMPLPERKS_DET pep,earndedumaster em,PROCESSED_EMPLPERKS pp where");
			empErnDedQry.append(" pep.EARNDEDUCD = em.EARNDEDUCD and em.TYPE = 'PK' and empno =").append(empNo);
			empErnDedQry.append(" and pp.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll");
			empErnDedQry.append(" mp where mp.fyearcd = ").append(fyearCd).append(" ) and pep.pro_per_cd =");
			empErnDedQry.append(" pp.pro_per_cd group by pep.earndeducd, em.EARNDEDUNM, em.projecteditname ) ");
			empErnDedQry.append(" procperk,(select sum(amount) amount,pep.EARNDEDUCD, em.projecteditname ,sum(ADJUSTMENTS) ADJUSTMENTS from ");
			empErnDedQry.append(" payrolldet pep,earndedumaster em,payrollprocess proc where pep.EARNDEDUCD = ");
			empErnDedQry.append(" em.EARNDEDUCD and em.TYPE = 'PK' and  empno = ").append(empNo).append(" and ");
			empErnDedQry.append(" proc.paytransid = pep.PAYTRANSID and proc.payrollmonthid in (select ");
			empErnDedQry.append(" PAYROLLMONTHID from monthlypayroll mp where mp.fyearcd = ").append(fyearCd);
			empErnDedQry.append(" ) and proc.payrollmonthid not in (select PAYROLLMONTHID from PROCESSED_EMPLPERKS");
			empErnDedQry.append(" pp where pp.payrollmonthid in (select PAYROLLMONTHID from monthlypayroll mp");
			empErnDedQry.append(" where mp.fyearcd = ").append(fyearCd).append(" )) group by pep.earndeducd, ");
			empErnDedQry.append(" em.EARNDEDUNM, em.projecteditname) payrollperk,(select (amount * (12 - ");
			empErnDedQry.append(mnthcnt).append(" )) amount,ep.earndeducd,em.EARNDEDUNM,em.projecteditname");
			empErnDedQry.append(" from emplperks ep, earndedumaster em where ep.EARNDEDUCD = em.EARNDEDUCD ");
			empErnDedQry.append(" and em.TYPE = 'PK' and empno =  ").append(empNo).append("  and em.ISTAXABLE = ");
			empErnDedQry.append(" 'Y') empperk,SALHEAD_LEDG_MAPPING slm where empperk.earndeducd = ");
			empErnDedQry.append(" procperk.earndeducd and empperk.earndeducd = payrollperk.earndeducd and ");
			empErnDedQry.append(" slm.earndeducd = payrollperk.earndeducd and slm.cadrecod = (select cadrecod ");
			empErnDedQry.append(" from employeeinfo where empno = ").append(empNo).append(") order by ");
			empErnDedQry.append(" procperk.earndeducd ");
			log.info("empErnDedQry        "+empErnDedQry.toString());
			rs = db.getRecordSet(empErnDedQry.toString());
			
			
			
			while(rs.next()){
				double excemptedAmt = 0 ;
				double perksAdjAmount=rs.getDouble("ADJUSTMENTS");
				double amount=perksAdjAmount+rs.getDouble("amount");
				totalPerksAmt+=perksAdjAmount+rs.getDouble("amount");
				if(prop.getProperty("TRANSALLOW").equals(rs.getString("ledgercd"))){
					String petrol = help.getDescription("employeeinfo","case when petrol > 35 then 21600 else 10800 end","empno",empNo,con);					
					excemptedAmt = CommonUtil.getLeastValue((rs.getDouble("payroll")+rs.getDouble("amount")),Double.parseDouble(petrol));
				}else if(prop.getProperty("ENTALLOW").equals(rs.getString("ledgercd"))){
					excemptedAmt = CommonUtil.getLeastValue((rs.getDouble("payroll")+rs.getDouble("amount")),5000);
				}
				
				detail = new ProjectedITDetail();
				
				detail.setEarnDedcName(rs.getString("ProjectedITName"));
				detail.setActualAmt(amount);
				detail.setProjectionAmt(rs.getDouble("payroll"));
				detail.setExemptedAmt(excemptedAmt);
				detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
				projectedIT.put(rs.getString("ProjectedITName"), detail);
				totalIncome += detail.getTotal();
			}
			db.closeRs();
			empErnDedQry = new StringBuffer(" select sum(APRVDAMOUNT) from MDCLTRANSACTIONMT where FYEARCD ="+fyearCd+" and empno="+empNo);
			rs = db.getRecordSet(empErnDedQry.toString());
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Medical Reimbursement");
			if(rs.next()){				
				detail.setActualAmt(rs.getDouble(1));
			}
			
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
			projectedIT.put("Medical Reimbursement", detail);
			totalIncome += detail.getTotal();
			db.closeRs();
			empErnDedQry = new StringBuffer(" select sum(amount) from empleaveencashment where FYEARCD ="+fyearCd+" and empno="+empNo);
			rs = db.getRecordSet(empErnDedQry.toString());
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Leave Encashment");
			if(rs.next()){				
				detail.setActualAmt(rs.getDouble(1));				
			}
			
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
			projectedIT.put("Leave Encashment", detail);
			totalIncome += detail.getTotal();
			db.closeRs();
			empErnDedQry = new StringBuffer(" select sum(amount) from empaltcencashment where FYEARCD ="+fyearCd+" and empno="+empNo);
			rs = db.getRecordSet(empErnDedQry.toString());
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Altc Encashment");
			if(rs.next()){				
				detail.setActualAmt(rs.getDouble(1));
			}
			
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
			projectedIT.put("Altc Encashment", detail);
			totalIncome += detail.getTotal();
			db.closeRs();
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Ex-Gratia");
			detail.setActualAmt(0);
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
			projectedIT.put("Ex-Gratia", detail);
			totalIncome += detail.getTotal();
			
			//Adding Arrears To the Gross Amount
			
			String arrsqry = "select empno,sum(netamt) netamt from emplarrears_mt eamt,emplarrears_dt eadt where eamt.EMPLARRMTCD = eadt.EMPLARRMTCD and eamt.fyearcd="
				+ fyearCd + " and empno = '" + empNo + "' group by empno ,netamt";
			ResultSet rs3 = db.getRecordSet(arrsqry);
			
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Arrears of Off/St");
			while (rs3.next()) {
				detail.setActualAmt(rs3.getDouble("netamt"));
			}
			
			db.closeRs();
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			detail.setTotal(detail.getActualAmt()+ detail.getProjectionAmt()-detail.getExemptedAmt());
			projectedIT.put("Arrears of Off/St", detail);
			totalIncome += detail.getTotal();
			double quaters = 0;
			empErnDedQry = new StringBuffer(" select sum(ADVAMT *(bankrateofint - aairateofint)/100) amt from employeeadvances adv, earningbyloanmt mt where EMPNO = '"+empNo+"' and  adv.ADVCD in ('4', '6', '2', '5') and  PRINCIPALSTATUS = 'Y' and mt.advcd = adv.advcd and   mt.fyearcd = '"+fyearCd+"' and ADVAMT between FROMSLAB and TOSLAB");
			rs = db.getRecordSet(empErnDedQry.toString());
			detail = new ProjectedITDetail();
			detail.setEarnDedcName("Other Earnings");
			detail.setActualAmt(0);
			detail.setProjectionAmt(0);
			detail.setExemptedAmt(0);
			if(rs.next()){
				detail.setTotal(rs.getDouble(1));
			}
			projectedIT.put("Other Earnings", detail);
			totalIncome += detail.getTotal();
			quaters -= detail.getTotal();
			db.closeRs();
			pit.setTotalIncome(totalIncome);		
			quaters += totalIncome;
			empErnDedQry = new StringBuffer(" select sum(ADJUSTMENTS) from payrolldet where PAYTRANSID in (select paytransid from payrollprocess where stationcd ='");
			empErnDedQry.append(station).append("'and empno = ").append(empNo).append(" and payrollmonthid in ");
			empErnDedQry.append(" (select payrollmonthid from monthlypayroll where fyearcd=").append(fyearCd);
			empErnDedQry.append(" )) and empno='"+empNo+"' ");
			rs = db.getRecordSet(empErnDedQry.toString());
			if(rs.next()){
				pit.setLessEOLRcvd(rs.getDouble(1));
			}			
			totalIncome -= pit.getLessEOLRcvd();
			db.closeRs();			
			empErnDedQry = new StringBuffer(" select sum(incometax),sum(PTAX) from empincometaxdet where payrollmonthid");
			empErnDedQry.append(" in (select payrollmonthid from monthlypayroll where fyearcd=");
			empErnDedQry.append(fyearCd).append(") and empno=").append(empNo);
			
			rs = db.getRecordSet(empErnDedQry.toString());
			if(rs.next()){
				//pit.setLessProfTax(rs.getDouble(2));
				pit.setItrecupto(rs.getDouble(1));
			}	
			
			//totalIncome -= pit.getLessProfTax();
			//quaters -= pit.getLessProfTax();
			rs.close();
			db.closeRs();
			
			/*
			 * If PF tax option is 1 then there is no PF tax
			 */
			double pftax = 0;
			if (pftaxoption.equals("0")) {
				/*log.info("Qry is ---3--- select PROFTAX from professionaltaxslab where "
						+ Math.round(((totalIncome - (totalPerksAmt*12)) / 12))
						+ " between FROMSAL and TOSAL and fyearcd='"
						+ fyearCd
						+ "'");*/
				// Calculating Pofessional tax from professionaltaxslab table
				// based on gross amount
				rs = db
					.getRecordSet("select nvl(PROFTAX,0) from professionaltaxslab where "
						+ Math.round(((totalIncome - (totalPerksAmt*12)) / 12))
						+ " between FROMSAL and TOSAL and fyearcd='"
						+ fyearCd
						+ "'");
				// rs = db.getRecordSet("select nvl(PROFTAX,0) from
				// professionaltaxslab where "+Math.round(((grossamt)/12))+"
				// between FROMSAL and TOSAL and fyearcd='"+fyearcd+"'");
				if (rs.next()){
					 pit.setLessProfTax(rs.getDouble(1)*12);
				}
				totalIncome -= pit.getLessProfTax();
				quaters -= pit.getLessProfTax();
				rs.close();
				db.closeRs();
			}
			
			quaters -= pit.getAddQuarters()+pit.getAddQuartersproj();
			quaters = (quaters*15/100)*qtrcount1/12;
			pit.setAddQuartersTot(Math.round(quaters));
			totalIncome += pit.getAddQuartersTot();	
			pit.setAddQuarters(Math.round(pit.getAddQuarters()+pit.getAddQuartersproj()));
			pit.setAddQuartersproj(Math.round(quaters));
			pit.setLessQuaters(0);
			totalIncome -= pit.getLessQuaters();
			pit.setGrossSalAdj(totalIncome);
			double totalDed = 0;			
			double donPmAmt = 0;
			Map deduc = new LinkedHashMap();
			empErnDedQry = new StringBuffer(" select mt.savingname, det.savingamt,det.savingscd from savingsdeclartiondet det, savingsmaster mt");
			empErnDedQry.append(" where det.savingscd = mt.savingscd and det.savdeclcd in (select savdeclcd ");
			empErnDedQry.append(" from savingsdeclartion where fyearcd = ").append(fyearCd).append(" and empno =");
			empErnDedQry.append(empNo).append(" and status = 'A') and savingamt != 0  and det.SAVINGSCD!=1");
			log.info("savings qury"+empErnDedQry.toString());
			rs = db.getRecordSet(empErnDedQry.toString());
			while(rs.next()){
				if("16".equals(rs.getString("savingscd"))){
					pit.setPhself(rs.getDouble(2));
				}else if("12".equals(rs.getString("savingscd"))){
					donAmt += rs.getDouble(2);
				}else if("13".equals(rs.getString("savingscd"))){
					pit.setHbaIntAcc(rs.getDouble(2));
				}else if("14".equals(rs.getString("savingscd"))){
					pit.setAddlessIncome(rs.getDouble(2));
				}else if("22".equals(rs.getString("savingscd")) || "24".equals(rs.getString("savingscd"))){
					donPmAmt += rs.getDouble(2);
				}else{
					deduc.put(rs.getString(1),new Double(rs.getDouble(2)));
					totalDed +=rs.getDouble(2);
				}
			}			
			db.closeRs();
			deduc.put("G.L.I.S",new Double(GSLIS));			
			totalDed +=GSLIS;	
			deduc.put("CPF/APF",new Double(cpf));	
			totalDed +=cpf;			
			deduc.put("L.I.C",new Double(lic));	
			totalDed +=lic;
			pit.setNetDed(deduc);
			deduc.put("Total Dedns.",new Double(totalDed));	
			totalIncome -= totalDed > chptrlimt?chptrlimt:totalDed;
			pit.setTotalDedAllow(chptrlimt);
			totalIncome -= pit.getPhself();
			pit.setDonation(donAmt);			
			totalIncome -= donAmt;			
			empErnDedQry = new StringBuffer("select sum(PMNRFUND) from emppmnrfunddet dt,emppmnrfund mt where mt.emppmnrfundcd = dt.emppmnrfundcd and empno='"+empNo+"' and mt.payrollmonthid in (select payrollmonthid from monthlypayroll roll where roll.fyearcd='"+fyearCd+"')");
			rs = db.getRecordSet(empErnDedQry.toString());
			if(rs.next()){
				donPmAmt = rs.getDouble(1);
			}
			db.closeRs();
			pit.setPmRelieffund(donPmAmt);
			totalIncome -= donPmAmt;
			totalIncome += pit.getAddlessIncome();
			totalIncome -= pit.getHbaIntAcc();
			pit.setTaxableSal(totalIncome);
			List empList = empTotalEarningsAndDeductions(empNo, db, fyearCd);
			EmpPaySlipInfo payInfo = (EmpPaySlipInfo) empList.get(0);		
			EmployeeInfo info = payInfo.getEmpInfo();
			pit.setEmpCode(info.getEmpNo());
			pit.setDesign(info.getEmpDesignation());
			pit.setPanno(info.getPan());
			pit.setName(info.getEmpName());
			SQLHelper sql = new SQLHelper();
			pit.setDepart(sql.getDescription("employeeinfo info","(select disp.disciplinenm from staffdiscipline disp where disp.disciplinecd = info.DISCIPLINECD)","empno",empNo, con));
			empErnDedQry = new StringBuffer(" select FIXEDAMOUNT,TAXPERCENT,DEDUCTIONAMT from incometaxslabmaster");
			empErnDedQry.append(" m,incometaxslabdetails d where m.taxslabcd=d.taxslabcd and m.assessetype='");
			empErnDedQry.append(payInfo.getEmpInfo().getGender()).append("' and fyearcd=").append(fyearCd);
			empErnDedQry.append(" and ").append(totalIncome).append(" between FROMAMOUNT and TOAMOUNT ");
			rs = db.getRecordSet(empErnDedQry.toString());
			if (rs.next()) {				
				totalIncome = Math
					.round((rs.getDouble("FIXEDAMOUNT") + ((totalIncome - rs
						.getDouble("DEDUCTIONAMT"))
						* rs.getDouble("TAXPERCENT") / 100)));
			}
			db.closeRs();
			pit.setTaxOnTotInc(totalIncome);
			pit.setAddEdnCess(Math.round(totalIncome*3/100));
			double tax = totalIncome+Math.round(totalIncome*3/100);
			pit.setTotTaxPay(tax);
			pit.setQuatersTax(0);			
			tax -= pit.getItrecupto();
			pit.setTaxAmtDue(tax);			
			pit.setMonthlyInt(Math.round(tax/(12-mnthcnt)));			
			db.closeCon();	
			
			/*
			 * For Printing COnsole
			 */
			/*
			Set s = projectedIT.keySet();
			Iterator iter = s.iterator();
			while (iter.hasNext()) {
				String earnCd = (String) iter.next();
				detail = (ProjectedITDetail) projectedIT.get(earnCd);
				System.out.println("--------" + detail.getEarnDedcName());
				System.out.println("--------" + detail.getActualAmt());
				System.out.println("--------" + detail.getProjectionAmt());
				System.out.println("--------" + detail.getExemptedAmt());
				System.out.println("--------" + detail.getTotal());
				System.out.println("-----------5-------------");}
           */
		} catch (Exception e) {
			log.info("-------------"+e.toString());
			log.printStackTrace(e);
			e.printStackTrace();
		}
		return projectedIT;
	}
}
