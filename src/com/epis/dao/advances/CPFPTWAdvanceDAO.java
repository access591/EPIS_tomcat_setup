/**
 * File       : CPFPTWAdvanceDAO.java
 * Date       : 09/30/2009
 * Author     : Suresh Kumar Repaka 
 * Description: 
 * Copyright (2009) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.dao.advances;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer; 

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.BeanUtils;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.advances.AdvanceBasicReportBean;
import com.epis.bean.advances.AdvanceCPFForm2Bean;
import com.epis.bean.advances.AdvanceEditBean;
import com.epis.bean.advances.AdvancePFWFormBean;
import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.bean.advances.CPFPFWTransBean;
import com.epis.bean.advances.CPFPFWTransInfoBean;
import com.epis.bean.advances.EmpBankMaster;
import com.epis.common.exception.EPISException;
import com.epis.dao.CommonDAO;
import com.epis.dao.rpfc.FinancialReportDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.LetterFormates;
import com.epis.utilities.Log;
import com.epis.utilities.MailUtil;

public class CPFPTWAdvanceDAO {
	Log log = new Log(CPFPTWAdvanceDAO.class);

	DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();

	CommonDAO commonDAO = new CommonDAO();

	String userName = "", compName = "";

	FinancialReportDAO financeReport = new FinancialReportDAO();

	CPFPTWAdvanceDAO() {

	}

	public CPFPTWAdvanceDAO(String frmUserName, String frmComputerName) {
		this.userName = frmUserName;
		this.compName = frmComputerName;
	}

	public String buildSearchQueryForPFWForm3Advances(
			AdvanceSearchBean searchBean, String frmname, String unitName) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,EAF.SANCTIONDATE AS SANCTIONDATE,"
				+ "EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS,EAF.VERIFIEDBY AS VERIFIEDBY, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND  EAF.DELETEFLAG='N' ";

		if (frmname.equals("PFWForm3"))
			sqlQuery += "AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND EAF.ADVANCETRANSSTATUS ='A'";
		else if (frmname.equals("PFWForm4"))
			sqlQuery += "AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY like('%PERSONNEL,FINANCE%') AND EAF.ADVANCETRANSSTATUS ='A'";

		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EMPFID.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EMPFID.REGION) like'"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.ADVANCETRANSDT='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& unitName.equals("")
				&& searchBean.getLoginRegion().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);
		dynamicQuery = query.toString();

		return dynamicQuery;
	}

	public String buildSearchQueryForPFWRevisedVerifcationSeacrh(
			AdvanceSearchBean searchBean, String frmname, String unitName) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,  EAF.ADVANCETRANSDT AS ADVANCETRANSDT, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,EAF.SANCTIONDATE AS SANCTIONDATE,"
				+ "EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS,EAF.VERIFIEDBY AS VERIFIEDBY, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME ,REV.REVVERIFIEDBY AS REVVERIFIEDBY ,REV.REVADVANCETRANSID AS REVADVANCETRANSID  FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM   EAF,EMPLOYEE_ADVANCES_FORM_REV REV WHERE  EAF.ADVANCETRANSID  = REV.ADVANCETRANSID(+)  AND EAF.PENSIONNO=EMPFID.PENSIONNO AND  EAF.DELETEFLAG='N'"
				+ " AND  EAF.CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND EAF.ADVANCETRANSSTATUS ='A' AND EAF.FINALTRANSSTATUS ='A' AND EAF.SANCTIONDATE IS NOT NULL ";

		 
		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EMPFID.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EMPFID.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.SANCTIONDATE='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& unitName.equals("")
				&& searchBean.getLoginRegion().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY EAF.ADVANCETRANSID desc,EAF.SANCTIONDATE desc";
		query.append(orderBy);
		dynamicQuery = query.toString();

		return dynamicQuery;
	}
	public String buildSearchQueryForPFWRevisedRecommendationSeacrh(
			AdvanceSearchBean searchBean, String frmname, String unitName) {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "";
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.REVADVANCETRANSID AS REVADVANCETRANSID,  EAF.ADVANCETRANSDT AS ADVANCETRANSDT, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,EAF.SANCTIONDATE AS SANCTIONDATE,"
				+ "EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS,EAF.VERIFIEDBY AS VERIFIEDBY, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME,EAF.REVVERIFIEDBY AS REVVERIFIEDBY  FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM_REV   EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND  EAF.DELETEFLAG='N'"
				+ " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND EAF.ADVANCETRANSSTATUS ='A' AND EAF.FINALTRANSSTATUS ='A' AND EAF.SANCTIONDATE IS NOT NULL ";

		 
	 	if (frmname.equals("PFWRevisedRecommendation")){
			sqlQuery += "  AND( EAF.REVVERIFIEDBY is NOT NULL OR  EAF.REVVERIFIEDBY ='FINANCE')";	 		 
	 	} 
	 	if (frmname.equals("PFWRevisedApproval")){
			sqlQuery += "  AND   EAF.REVVERIFIEDBY ='FINANCE,RHQ' ";	 		 
	 	} 
	 	if (frmname.equals("PFWRevisedApproved")){
			sqlQuery += "  AND   EAF.REVVERIFIEDBY ='FINANCE,RHQ,APPROVED'";	 		 
	 	} 
		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EMPFID.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EMPFID.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.SANCTIONDATE='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& unitName.equals("")
				&& searchBean.getLoginRegion().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY EAF.ADVANCETRANSID desc,EAF.SANCTIONDATE desc";
		query.append(orderBy);
		dynamicQuery = query.toString();

		return dynamicQuery;
	}
	public String getUnitName(AdvanceSearchBean searchBean, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		String unitName = "";

		try {
			st = con.createStatement();
			String unitQry = "select unitname from employee_unit_master where  unitcode='"
					+ searchBean.getLoginUnitCode() + "'";

			log.info("unitQry-------" + unitQry);

			rs = st.executeQuery(unitQry);
			if (rs.next()) {
				unitName = rs.getString("unitname");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}

		return unitName;
	}
//	On 24-May-2012 for PFW Revised Search purpose
	public ArrayList searchAdvance(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchAdvance"
				+ advanceSearchBean.getPensionNo() + "Advance Type"
				+ advanceSearchBean.getAdvanceType() + "Verfied By"
				+ advanceSearchBean.getVerifiedBy());
		String pensionNo = "", dateOfBirth = "", pfid = "", selectQuery = "", unitCode = "", unitName = "", region = "";
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();

			String reg = commonUtil.getAirportsByProfile(advanceSearchBean
					.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
					advanceSearchBean.getLoginRegion());

			if (!reg.equals("")) {
				String[] regArr = reg.split(",");
				unitCode = regArr[0];
				region = regArr[1];
			}

			if (!unitCode.equals("-"))
				unitName = this.getUnitName(advanceSearchBean, con);

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");

			if ((advanceSearchBean.getFormName().equals("PFWForm3"))
					|| (advanceSearchBean.getFormName().equals("PFWForm4"))) {

				selectQuery = this.buildSearchQueryForPFWForm3Advances(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);
			}  else if ((advanceSearchBean.getFormName().equals("CPFRecommendation"))||(advanceSearchBean.getFormName().equals("CPFApproval"))||(advanceSearchBean.getFormName().equals("CPFApproved")) ) {

				selectQuery = this.buildSearchQueryForAdvancesForRectoApproved(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);  
			}  else if (advanceSearchBean.getFormName().equals("PFWRevisedVerification")) {

				selectQuery = this.buildSearchQueryForPFWRevisedVerifcationSeacrh(
						advanceSearchBean, advanceSearchBean.getFormName(),	unitName);  
			} else if ((advanceSearchBean.getFormName().equals("PFWRevisedRecommendation")) ||(advanceSearchBean.getFormName().equals("PFWRevisedFormApproved"))  ) {

				selectQuery = this.buildSearchQueryForPFWRevisedRecommendationSeacrh(
						advanceSearchBean, advanceSearchBean.getFormName(),	unitName);  
			}else {

				selectQuery = this.buildSearchQueryForAdvances(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);
			}
			log.info("CPFPTWAdvanceDAO::searchAdvance" + selectQuery);
			ResultSet rs = st.executeQuery(selectQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();
				searchBean.setRequiredamt(rs.getString("REQUIREDAMOUNT"));
				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				searchBean.setDesignation(rs.getString("DESEGNATION"));

				searchBean.setAdvanceStatus(rs.getString("ADVANCETRANSSTATUS"));

				searchBean.setTransMnthYear(CommonUtil.getDatetoString(rs
						.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("dateofbirth") != null) {
					dateOfBirth = CommonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					dateOfBirth = "---";
				}
				if (rs.getString("TOTALINATALLMENTS") != null) {
					searchBean.setCpfIntallments(rs
							.getString("TOTALINATALLMENTS"));
				} else {
					searchBean.setCpfIntallments("");
				}

				if (rs.getString("SANCTIONDATE") != null) {
					searchBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					searchBean.setSanctiondt("");
				}
				if (rs.getString("PAYMENTINFO") != null) {
					searchBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					searchBean.setPaymentinfo("");
				}

				if (rs.getString("VERIFIEDBY") != null) {
					if (advanceSearchBean.getFormName().equals("PFWForm3")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"PFWForm4")) {

						if (rs.getString("FINALTRANSSTATUS") != null) {
							searchBean.setFinalTrnStatus(rs
									.getString("FINALTRANSSTATUS"));
						}

						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if ((advanceSearchBean.getFormName()
							.equals("PFWForm2"))
							|| (advanceSearchBean.getFormName()
									.equals("CPFVerification"))) {
						if (rs.getString("ADVANCETRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("ADVANCETRANSSTATUS"));
						} else {
							searchBean.setAdvanceStatus("N");
						}

					} else if (advanceSearchBean.getFormName().equals(
							"CPFRecommendation")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"CPFApproval")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL,REC"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"AdvanceSearch")) {
						if (rs.getString("ADVANCETRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("ADVANCETRANSSTATUS"));
						}
					} else {
						if (rs.getString("FINALTRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("FINALTRANSSTATUS"));
						}

					}
					searchBean.setVerifiedBy(rs.getString("VERIFIEDBY"));
				} else {

					if (rs.getString("ADVANCETRANSSTATUS") != null) {
						searchBean.setAdvanceStatus(rs
								.getString("ADVANCETRANSSTATUS"));
					} else {
						searchBean.setAdvanceStatus("N");
					}
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					if ((advanceSearchBean.getFormName().equals("PFWCheckList") || advanceSearchBean
							.getFormName().equals("CPFCheckList"))
							&& rs.getString("CHKLISTFLAG").equals("Y"))
						searchBean.setAdvanceStatus("Y");
				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setAdvanceType(rs.getString("ADVANCETYPE")
						.toUpperCase());
				searchBean.setPurposeType(rs.getString("PURPOSETYPE")
						.toUpperCase());

				searchBean.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
				searchBean.setAdvanceTransIDDec(searchBean.getAdvanceType()
						.toUpperCase()
						+ "/"
						+ searchBean.getPurposeType().toUpperCase()
						+ "/"
						+ rs.getString("ADVANCETRANSID"));

				if (!searchBean.getVerifiedBy().equals("")) {

					if (rs.getString("FINALTRANSSTATUS") != null) {
						if (rs.getString("FINALTRANSSTATUS").equals("N")) {
							if (searchBean.getVerifiedBy().equals("PERSONNEL")) {
								searchBean
										.setTransactionStatus("Verified by Personnel");
							} else if ((searchBean.getVerifiedBy().equals(
									"PERSONNEL,FINANCE"))|| (searchBean.getVerifiedBy().equals(
									"PERSONNEL,REC")) ) {
								searchBean
										.setTransactionStatus("Verified by Personnel,Finance");
							} else if (searchBean.getVerifiedBy().equals(
									"PERSONNEL,FINANCE,RHQ")) {
								searchBean
										.setTransactionStatus("Verified by RHQ");
							}
						}else if (rs.getString("FINALTRANSSTATUS").equals("R")) {
							searchBean.setTransactionStatus("Rejected");
							searchBean.setAdvanceStatus("Rejected");
						}else { 
							searchBean.setTransactionStatus("Approved");
						}
					}

				} else {
					searchBean.setTransactionStatus("New");
				}
				
				if ((advanceSearchBean.getFormName().equals("PFWRevisedVerification")) ||
						(advanceSearchBean.getFormName().equals("PFWRevisedRecommendation")) ||
						(advanceSearchBean.getFormName().equals("PFWRevisedFormApproved"))  ) {
					
					if (rs.getString("SANCTIONDATE") != null) {
						searchBean.setSanctiondt(CommonUtil.getDatetoString(rs
								.getDate("SANCTIONDATE"), "dd-MMM-yyyy")); 
					}else{
						searchBean.setSanctiondt("---");
					}
				}
					
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;

	}
//	On 23-Jan-2012 for loading date according to user 
	public String buildSearchQueryForAdvances(AdvanceSearchBean searchBean,
			String frmname, String unitName) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",profilecondition="";
		profileName=searchBean.getProfileName();
		if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A") ||profileName.equals("R")||profileName.equals("U")){
			profilecondition="";			 
		}else{ 
			profilecondition="AND EAF.USERNAME='"+searchBean.getUserName()+"'";
			 
		}
		
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N' "+profilecondition;

		// if(frmname.equals("PFWCheckList"))
		// sqlQuery+=" AND CHKLISTFLAG='N' ";
	 
		if (frmname.equals("PFWForm2") || frmname.equals("CPFVerification"))
			sqlQuery += " AND  CHKLISTFLAG='Y'";
		else if (frmname.equals("CPFApproval"))
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY='PERSONNEL,REC' AND FINALTRANSSTATUS='N' AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
		else if (frmname.equals("CPFRecommendation"))
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND FINALTRANSSTATUS is NOT NULL  AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
		else if (frmname.equals("CPFApproved"))
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND ((FINALTRANSSTATUS='A') OR (FINALTRANSSTATUS='R')) AND EAF.ADVANCETRANSSTATUS ='A'";
		else if (frmname.equals("PFWApproval"))
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY='PERSONNEL,FINANCE,RHQ' AND ((FINALTRANSSTATUS='N') OR (FINALTRANSSTATUS='R')) AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
		else if (frmname.equals("PFWApproved"))
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY='PERSONNEL,FINANCE,RHQ' AND FINALTRANSSTATUS='A' AND EAF.ADVANCETRANSSTATUS ='A'";

		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EAF.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EAF.REGION) like'"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.ADVANCETRANSDT='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		/*
		 * if (!searchBean.getVerifiedBy().equals("")) { whereClause.append("
		 * EAF.VERIFIEDBY='" + searchBean.getVerifiedBy()+ "'");
		 * whereClause.append(" AND "); }
		 */
		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}
		
		if (!searchBean.getCheckListStatus().equals("")) {
			
			if(searchBean.getCheckListStatus().equals("N")){
			whereClause.append(" LOWER(EAF.CHKLISTFLAG) like'%"
					+ searchBean.getCheckListStatus().toLowerCase().trim()
					+ "%'");
			
			} else{
				whereClause.append("LOWER(EAF.CHKLISTFLAG) like'%"
						+ searchBean.getCheckListStatus().toLowerCase().trim()
						+ "%'");
			}
			whereClause.append(" AND ");
		}
		
		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")
				&& searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getVerifiedBy().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")
				&& searchBean.getCheckListStatus().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);		 
		dynamicQuery = query.toString();

		return dynamicQuery;

	}

	//	On 24-Jan-2012 for loading date according to user in Recommendation, Approval& Approved Level
	public String buildSearchQueryForAdvancesForRectoApproved(AdvanceSearchBean searchBean,
			String frmname, String unitName) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",stationCond="",userSpecCond="",accTypeCond="";
		int transcode=0;
		profileName=searchBean.getProfileName();
		if(profileName.equals("R") && (!searchBean.getLoginRegion().equals("CHQIAD")) ){
			accTypeCond="AND NVL(EAF.ACCOUNTTYPE,'RAU') LIKE 'RAU'";
		}
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N' "+accTypeCond;

		 
	 	if (frmname.equals("CPFRecommendation")){
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND FINALTRANSSTATUS is NOT NULL  AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
	 		transcode=3;
	 	}else if (frmname.equals("CPFApproval")){
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY='PERSONNEL,REC' AND FINALTRANSSTATUS='N' AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
			transcode=3;
	 	}else if (frmname.equals("CPFApproved")){
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND (FINALTRANSSTATUS='A' OR FINALTRANSSTATUS='R')  AND EAF.ADVANCETRANSSTATUS ='A'";
			transcode=4;
	 	} 
	 	
		  if (!unitName.equals("")) {
			whereClause.append(" LOWER(EAF.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EAF.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.ADVANCETRANSDT='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		/*
		 * if (!searchBean.getVerifiedBy().equals("")) { whereClause.append("
		 * EAF.VERIFIEDBY='" + searchBean.getVerifiedBy()+ "'");
		 * whereClause.append(" AND "); }
		 */
		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")
				&& searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getVerifiedBy().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);
		log.info("====searchBean.getLoginRegion()======"+searchBean.getLoginRegion()+"==frmname==="+frmname);
		if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
			dynamicQuery = query.toString();
		}else{ 
			if(profileName.equals("U") || searchBean.getLoginRegion().equals("CHQIAD")){
				stationCond="  AND LOWER(AIRPORTCODE) like '"+searchBean.getLoginUnitCode().toLowerCase().trim()+"'";
			}else{
				stationCond="";
			}
			if(frmname.equals("CPFApproval")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE     LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
		 	
			}else if(frmname.equals("CPFApproved")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   APPROVEDBY = '"+searchBean.getUserId()+"'   AND   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
		 	
			}else if (frmname.equals("CPFRecommendation")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (((SELECT CPFPFWTRANSID ,PENSIONNO  FROM EPIS_ADVANCES_TRANSACTIONS   WHERE LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" AND TRANSCD < '"+transcode+"')"
				+ " UNION  (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   APPROVEDBY = '"+searchBean.getUserId()+"'   AND   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"')) Minus  (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   APPROVEDBY! = '"+searchBean.getUserId()+"'   AND   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"')) TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
				
			}else{
				//for Approval Case
				userSpecCond =  query.toString();;
			}
			dynamicQuery = userSpecCond;
		} 
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
	//Radha On 07-May-2012 for updating Designation in Advances Table 
	//to Make connection as single 
//to Avoid Single Code in Text
	public String addCPFPTWAdvanceInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankBean) throws EPISException {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", advanceType = "", insertHBAQuery = "", insertHEQuery = "";
		String purposeType = "", wthDrwlTrnsdt = "", marriageDate = "",purposeOptionType="";
		log.info("Basic Info" + advanceBean.getPensionNo());
		log.info("====Course Duration in DAO======"
				+ advanceBean.getCurseDuration());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			advanceTransID = this.getAdvanceSequence(con);
			advanceType = advanceBean.getAdvanceType();
			purposeType = advanceBean.getPurposeType();
			String fmlyDOB = "";
			if (!advanceBean.getFmlyDOB().trim().equals("")) {
				fmlyDOB = commonUtil.converDBToAppFormat(advanceBean
						.getFmlyDOB(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getWthDrwlTrnsdt().trim().equals("")) {
				wthDrwlTrnsdt = commonUtil.converDBToAppFormat(advanceBean
						.getWthDrwlTrnsdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getMarriagedate().trim().equals("")) {
				marriageDate = commonUtil.converDBToAppFormat(advanceBean
						.getMarriagedate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (advanceBean.getAdvanceType().toLowerCase().equals("cpf")) {
				if (advanceBean.getChkWthdrwlInfo().equals("")) {
					advanceBean.setChkWthdrwlInfo("N");
				}
			}

			log.info("addCPFPTWAdvanceInfo::Pensionno"
					+ advanceBean.getPensionNo() + "Purpose Option Type"
					+ advanceBean.getPurposeOptionType().toUpperCase().trim()+"AccountType==="+advanceBean.getAccountType());
			/*
			 * Removed the fields SUBSCRIPTIONAMNT,USERSUBSCRIPTIONAMNT on 23-Jan-2012.This is wrongly updated the data.
			 */
		purposeOptionType = commonUtil.replaceAllWords2(advanceBean.getPurposeOptionType().toUpperCase().trim()
					 , "'","");
			insertQuery = "INSERT INTO EMPLOYEE_ADVANCES_FORM(ADVANCETRANSID,ADVANCETRANSDT,ADVANCETYPE,PURPOSETYPE,USERPURPOSETYPE,PURPOSEOPTIONTYPE,REQUIREDAMOUNT,USERREQUIREDAMOUNT,ADVANCETRANSSTATUS,reason,IPADDRESS,USERNAME,PENSIONNO,LOD,USERLOD,ADVNCERQDDEPEND,UTLISIEDAMNTDRWN,CHKWTHDRWL,USERCHKWTHDRWL,TRUST,REGION,AIRPORTCODE,paymentinfo,TRNASMNTHEMOLUMENTS,USERTRNASMNTHEMOLUMENTS,PARTYNAME,PARTYADDRESS,PLACEOFPOSTING,TOTALINATALLMENTS,USERTOTALINATALLMENTS,ACCOUNTTYPE,DESIGNATION,FIRSTINSTALLMENTSUBAMT,FIRSTINSTALLMENTCONTRIAMT,APPROVEDSUBSCRIPTIONAMT,APPROVEDCONTRIBUTIONAMT) VALUES("
					+ Long.parseLong(advanceTransID)
					+ ",'"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ advanceBean.getAdvanceType().toUpperCase().trim()
					+ "','"
					+ advanceBean.getPurposeType().toUpperCase().trim()
					+ "','"
					+ advanceBean.getPurposeType().toUpperCase().trim()
					+ "','"
					+ purposeOptionType
					+ "',"
					+ advanceBean.getAdvReqAmnt()
					+ ","
					+ advanceBean.getAdvReqAmnt()
					+ ",'"
					+ advanceTrnsStatus
					+ "','"
					+ advanceBean.getAdvReasonText()
					+ "','"
					+ compName
					+ "','"
					+ userName
					+ "',"
					+ advanceBean.getPensionNo()
					+ ",'"
					+ advanceBean.getLodInfo()
					+ "','"
					+ advanceBean.getLodInfo()
					+ "','"
					+ advanceBean.getAdvncerqddepend()
					+ "','"
					+ advanceBean.getUtlisiedamntdrwn()
					+ "','"
					+ advanceBean.getChkWthdrwlInfo()
					+ "','"
					+ advanceBean.getChkWthdrwlInfo()
					+ "','"
					+ advanceBean.getTrust()
					+ "','"
					+ advanceBean.getRegion()
					+ "','"
					+ advanceBean.getStation()
					+ "','"
					+ advanceBean.getPaymentinfo()
					+ "',"
					+ advanceBean.getEmoluments()
					+ ","
					+ advanceBean.getEmoluments()
					+ ",'"
					+ advanceBean.getPartyName()
					+ "','"
					+ advanceBean.getPartyAddress()
					+ "','"
					+ advanceBean.getPlaceofposting()
					+ "',"
					+ advanceBean.getCpfIntallments()
					+ ","
					+ advanceBean.getCpfIntallments()
					+ ",'"
					+ advanceBean.getAccountType() 
					+ "','"
					+ advanceBean.getDesignation() 
					+ "',"
					+ advanceBean.getFirstInsSubAmnt()
					+ ","
					+ advanceBean.getFirstInsConrtiAmnt()
					+ ","
					+ advanceBean.getRecEmpSubAmnt()
					+","
					+advanceBean.getRecEmpConrtiAmnt()
					+")";
			if (advanceBean.getChkWthdrwlInfo().equals("Y")) {
				log.info("=====advanceBean.getWthdrwlist()======="
						+ advanceBean.getWthdrwlist());

				this.addWithDrawalDetails(advanceTransID, con, advanceBean);

			}
			insertHBAQuery = "INSERT INTO EMPLOYEE_ADVANCE_HBA_INFO(ADVANCETRANSID,REPAYMENTLOANTYPE,HBAOTHERS,REPAYMENTNAME,REPAYMENTADDRESS,REPAYMENTAMOUNT,NAME,ADDRESS,AREA,PLOTNO,LOCALITY,MUNICIPALITY,CITY,ACTUALCOST,PROPERTYADDRESS,HBADRWNFRMAAI,USERHBADRWNFRMAAI,PERMISSIONAAI,HBADRWNFRMAAIPURPOSE,HBADRWNFRMAAIAMOUNT,HBADRWNFRMAAIADDRESS) VALUES("
					+ Long.parseLong(advanceTransID)
					+ ",'"
					+ advanceBean.getHbarepaymenttype().toUpperCase().trim()
					+ "','"
					+ advanceBean.getAdvReasonText().trim()
					+ "','"
					+ advanceBean.getHbaloanname()
					+ "','"
					+ advanceBean.getHbaloanaddress()
					+ "',"
					+ advanceBean.getOsamountwithinterest()
					+ ",'"
					+ advanceBean.getHbaownername()
					+ "','"
					+ advanceBean.getHbaowneraddress()
					+ "','"
					+ advanceBean.getHbaownerarea()
					+ "',"
					+ "'"
					+ advanceBean.getHbaownerplotno()
					+ "','"
					+ advanceBean.getHbaownerlocality()
					+ "','"
					+ advanceBean.getHbaownermuncipal()
					+ "','"
					+ advanceBean.getHbaownercity()
					+ "','"
					+ advanceBean.getActualcost()
					+ "','"
					+ advanceBean.getPropertyaddress()
					+ "','"
					+ advanceBean.getHbadrwnfrmaai()
					+ "','"
					+ advanceBean.getHbadrwnfrmaai()
					+ "','"
					+ advanceBean.getHbapermissionaai()
					+ "','"
					+ advanceBean.getHbawthdrwlpurpose()
					+ "','"
					+ advanceBean.getHbawthdrwlamount()
					+ "','"
					+ advanceBean.getHbawthdrwladdress() + "')";
			log.info("insertQuery " + insertQuery);

			insertHEQuery = "INSERT INTO EMPLOYEE_ADVANCES_HE_INFO(ADVANCETRANSID,NMCOURSE,NMINSTITUTION,INSTITUTIONADDRSS,DURATIONCOURSE,RECOGNIZED,LASTEXAMPASSED) VALUES("
					+ Long.parseLong(advanceTransID)
					+ ",'"
					+ advanceBean.getNmCourse().toUpperCase()
					+ "','"
					+ advanceBean.getNmInstitue().toUpperCase().trim()
					+ "','"
					+ advanceBean.getAddrInstitue().trim()
					+ "','"
					+ advanceBean.getCurseDuration()
					+ "','"
					+ advanceBean.getHeRecog()
					+ "','"
					+ advanceBean.getHeLastExaminfo() + "')";

			insertFmlyQuery = "INSERT INTO EMPLOYEE_ADVANCES_FAMILY_DTLS(ADVANCETRANSID,NAME,AGE,DATEOFBIRTH,AGELOD,MARRIAGEDATE) VALUES("
					+ Long.parseLong(advanceTransID)
					+ ",'"
					+ advanceBean.getFmlyEmpName().trim().toUpperCase()
					+ "','"
					+ advanceBean.getFmlyAge().toUpperCase().trim()
					+ "','"
					+ fmlyDOB
					+ "','"
					+ advanceBean.getBrthCertProve()
					+ "','"
					+ marriageDate + "')";

			log.info("CPFPTWAdvanceDAO::addCPFPTWAdvanceInfo" + insertQuery);
			log.info("CPFPTWAdvanceDAO::addCPFPTWAdvanceInfo" + insertHBAQuery);
			log.info("CPFPTWAdvanceDAO::addCPFPTWAdvanceInfo" + insertHEQuery);
			log.info("CPFPTWAdvanceDAO::addCPFPTWAdvanceInfo" + insertFmlyQuery
					+ "Purpose Type" + advanceBean.getPurposeType());
			insertedRecords = st.executeUpdate(insertQuery);
			if (insertedRecords != 0) {

				// int
				// recordCount=buildUpdateQueryToStorePersonalInfo(advanceBean.getPensionNo(),advanceBean.getUserName(),"Advances");

				// log.info("----recordCount-----"+recordCount);
				//  For restricting the updation of personal Information Frm  25-Apr-2012
				//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
				/*if ((!advanceBean.getDepartment().equals(""))
						|| (!advanceBean.getDesignation().equals(""))) {
					String updateQry = "update employee_personal_info set DESEGNATION='"
							+ advanceBean.getDesignation()
							+ "', DEPARTMENT='"
							+ advanceBean.getDepartment() 
							+ "',AIRPORTCODE='"
							+ advanceBean.getStation()
							+ "',REGION='"
							+ advanceBean.getRegion()
							+ "' where  PENSIONNO='"
							+ advanceBean.getPensionNo() + "'";
					log.info("==========update Query===========" + updateQry);
					int updatedRecord = st.executeUpdate(updateQry);
				}*/

				log.info("Advance Type" + advanceBean.getAdvanceType()
						+ "Purpose Type" + advanceBean.getPurposeType());
				if (advanceBean.getAdvanceType().toLowerCase().equals("pfw")
						&& advanceBean.getPurposeType().toUpperCase().equals(
								"HBA")) {
					log.info("House Building Details");
					insertedRecords = st.executeUpdate(insertHBAQuery);
				} else if ((advanceBean.getAdvanceType().toLowerCase().equals(
						"pfw") && advanceBean.getPurposeType().toUpperCase()
						.equals("HE"))
						|| (advanceBean.getAdvanceType().toUpperCase().equals(
								"CPF") && advanceBean.getPurposeType()
								.toUpperCase().equals("EDUCATION"))) {
					log.info("Higher Eduction");
					insertedRecords = st.executeUpdate(insertHEQuery);
					if (advanceBean.getAdvanceType().toUpperCase()
							.equals("CPF")
							&& advanceBean.getPurposeType().toUpperCase()
									.equals("EDUCATION")
							&& insertedRecords != 0) {
						insertedRecords = st.executeUpdate(insertFmlyQuery);
					}
					if (advanceBean.getAdvanceType().toLowerCase()
							.equals("pfw")
							&& advanceBean.getPurposeType().toUpperCase()
									.equals("HE") && insertedRecords != 0) {
						insertedRecords = st.executeUpdate(insertFmlyQuery);
					}
				} else if ((advanceBean.getAdvanceType().toLowerCase().equals(
						"pfw") && advanceBean.getPurposeType().toUpperCase()
						.equals("MARRIAGE"))
						|| (advanceBean.getAdvanceType().toLowerCase().equals(
								"cpf") && advanceBean.getPurposeType()
								.toUpperCase().equals(
										"obligatory".toUpperCase()))
						|| ((advanceBean.getAdvanceType().toLowerCase().equals(
								"cpf") && advanceBean.getPurposeType()
								.toUpperCase().equals("COST")))
						|| ((advanceBean.getAdvanceType().toLowerCase().equals(
								"cpf") && advanceBean.getPurposeType()
								.toUpperCase().equals("OBMARRIAGE")))
						|| ((advanceBean.getAdvanceType().toLowerCase().equals(
								"cpf") && advanceBean.getPurposeType()
								.toUpperCase().equals("illness".toUpperCase())))) {
					log.info("Family Details");
					insertedRecords = st.executeUpdate(insertFmlyQuery);
				}

				if (!advanceTransID.equals(""))
					bankBean.setTransId(advanceTransID);

				if (advanceBean.getPaymentinfo().equals("Y")) {
					this.addEmployeeBankInfo(con, bankBean, advanceBean
							.getPensionNo());
				} else {
					/*
					 * String updateQuery = "update EMPLOYEE_BANK_INFO set
					 * ADDRESS='" + bankBean.getBankempaddrs() + "',BANKNAME='" +
					 * bankBean.getBankname() + "',BRANCHADDRESS='" +
					 * bankBean.getBranchaddress() + "',SAVINGBNKACCNO='" +
					 * bankBean.getBanksavingaccno() + "',NEFTRTGSCODE='" +
					 * bankBean.getBankemprtgsneftcode() + "',MAILID='" +
					 * bankBean.getEmpmailid() + "',MICRONO='" +
					 * bankBean.getBankempmicrono() + "' where PENSIONNO='" +
					 * advanceBean.getPensionNo() + "'";
					 * log.info("==========update Query===========" +
					 * updateQuery); int updatedRecords =
					 * st.executeUpdate(updateQuery);
					 */
				}
				message = advanceType.toUpperCase() + "/" + purposeType + "/"
						+ advanceTransID;
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException("Errors||| Unable to Process Your Request");
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public void addWithDrawalDetails(String advanceTransID, Connection con,
			AdvanceBasicBean advanceBean) {

		Statement st = null;
		int insertedwthdrwRecords = 0;
		int withDrawalSeq = 1;
		String wthDrwlTrnsdt = "", insertWithdrawalQuery = "";
		String wthdrwPurpose = "", wthdrwAmount = "", wthdrwDate = "";
		String wthDrwlStatus = "Y";
		try {
			st = con.createStatement();

			String estr = advanceBean.getWthdrwlist();

			StringTokenizer est = new StringTokenizer(estr, ":");

			int lengt = est.countTokens();
			String estrarr[] = new String[lengt];

			for (int e = 0; est.hasMoreTokens(); e++) {

				estrarr[e] = est.nextToken();
				String expsplit = estrarr[e];

				String[] strArr = expsplit.split("#");
				for (int ii = 0; ii < strArr.length; ii++) {
					wthdrwPurpose = strArr[1];
					wthdrwAmount = strArr[2];
					wthdrwDate = strArr[3];
				}

				if (!wthdrwDate.trim().equals("")) {
					wthDrwlTrnsdt = commonUtil.converDBToAppFormat(wthdrwDate,
							"dd/MM/yyyy", "dd-MMM-yyyy");
				}
				if (wthdrwPurpose.equals("-")) {
					wthdrwPurpose = "";
				}
				insertWithdrawalQuery = "INSERT INTO employee_advances_wthdrwl_info(ADVANCETRANSID,WTHDRWLID,WTHDRWLPURPOSE,WTHDRWLAMOUNT,WTHDRWLTRNSDT,WTHDRWLSTATUS) VALUES("
						+ Long.parseLong(advanceTransID)
						+ ","
						+ withDrawalSeq
						+ ",'"
						+ wthdrwPurpose
						+ "','"
						+ wthdrwAmount
						+ "','"
						+ wthDrwlTrnsdt + "','" + wthDrwlStatus + "')";

				log.info("------insertWithdrawalQuery------"
						+ insertWithdrawalQuery);
				insertedwthdrwRecords = st.executeUpdate(insertWithdrawalQuery);
				withDrawalSeq++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * public int updateCPFAdvanceForm2Info(AdvanceCPFForm2Bean
	 * advanceBean,String frmName,String frmFlag) { Connection con = null;
	 * Statement st = null; Statement insertSt = null; int updatedRecords = 0;
	 * String updateQuery = "",verifiedVal="",finalTransStatus=""; String
	 * purposeType = ""; log.info("updateCPFAdvanceForm2Info::Basic Info" +
	 * advanceBean.getPensionNo()); double emolument = 0.0;
	 * 
	 * 
	 * 
	 * if (advanceBean.getPurposeType().equals("OBMARRIAGE")) emolument =
	 * Double.parseDouble(advanceBean.getEmoluments()) / 6; else emolument =
	 * Double.parseDouble(advanceBean.getEmoluments()) / 3; try { con =
	 * commonDB.getConnection(); st = con.createStatement(); insertSt =
	 * con.createStatement();
	 * 
	 * String fmlyDOB = "";
	 * 
	 * log.info(advanceBean.getPensionNo());
	 * log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());
	 * 
	 * if(frmName.equals("CPFApproval")) verifiedVal="FINANCE"; else
	 * verifiedVal="REC";
	 * 
	 * if(frmName.equals("CPFApproval")) finalTransStatus="A"; else
	 * finalTransStatus="N";
	 * 
	 * log.info("=======REMARKS==========="+advanceBean.getAuthrizedRemarks());
	 * 
	 * 
	 * if(frmFlag.equals("NewForm")){
	 * 
	 * updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET
	 * PURPOSEOPTIONCVRDCLUSE='" + advanceBean.getPrpsecvrdclse() +
	 * "',ADVANCETRANSSTATUS='" + advanceBean.getAuthrizedStatus() +
	 * "',FINALTRANSSTATUS='" + finalTransStatus + "',APPROVEDDT='" +
	 * commonUtil.getCurrentDate("dd-MMM-yyyy") + "',APPROVEDAMNT='" +
	 * advanceBean.getAmntAproved() + "',TRNASMNTHEMOLUMENTS='" + emolument +
	 * "',PREVIOUSOUTSTANDINGAMT='" + advanceBean.getOutstndamount() +
	 * "',SUBSCRIPTIONAMNT='" + advanceBean.getEmpshare() +
	 * "',MTHINSTALLMENTAMT='" + advanceBean.getMthinstallmentamt() +
	 * "',INTERESTINSTALLMENTS='" + advanceBean.getInterestinstallments() +
	 * "',INTERESTINSTALLAMT='" + advanceBean.getIntinstallmentamt() +
	 * "',RECOMMENDEDAMT='" + advanceBean.getAmntRecommended() + "',NARRATION='" +
	 * advanceBean.getAuthrizedRemarks() +
	 * "',VERIFIEDBY=VERIFIEDBY||','||'"+verifiedVal+"' WHERE ADVANCETRANSID='" +
	 * advanceBean.getAdvanceTransID() + "' AND PENSIONNO='" +
	 * advanceBean.getPensionNo() + "'";
	 * 
	 * }else{ updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET
	 * PURPOSEOPTIONCVRDCLUSE='" + advanceBean.getPrpsecvrdclse() +
	 * "',ADVANCETRANSSTATUS='" + advanceBean.getAuthrizedStatus() +
	 * "',FINALTRANSSTATUS='" + finalTransStatus + "',APPROVEDDT='" +
	 * commonUtil.getCurrentDate("dd-MMM-yyyy") + "',APPROVEDAMNT='" +
	 * advanceBean.getAmntAproved() + "',TRNASMNTHEMOLUMENTS='" + emolument +
	 * "',PREVIOUSOUTSTANDINGAMT='" + advanceBean.getOutstndamount() +
	 * "',SUBSCRIPTIONAMNT='" + advanceBean.getEmpshare() +
	 * "',MTHINSTALLMENTAMT='" + advanceBean.getMthinstallmentamt() +
	 * "',INTERESTINSTALLMENTS='" + advanceBean.getInterestinstallments() +
	 * "',INTERESTINSTALLAMT='" + advanceBean.getIntinstallmentamt() +
	 * "',NARRATION='" + advanceBean.getAuthrizedRemarks() +
	 * "',RECOMMENDEDAMT='" + advanceBean.getAmntRecommended() + "' WHERE
	 * ADVANCETRANSID='" + advanceBean.getAdvanceTransID() + "' AND PENSIONNO='" +
	 * advanceBean.getPensionNo() + "'";
	 *  } log.info("CPFPTWAdvanceDAO::updateCPFAdvanceForm2Info" + updateQuery);
	 * updatedRecords = st.executeUpdate(updateQuery);
	 * 
	 * 
	 * CPFPFWTransInfo cpfInfo=new
	 * CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());
	 * 
	 * if(frmName.equals("CPFApproval"))
	 * cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),advanceBean.getAdvanceTransID(),"CPF",Constants.APPLICATION_PROCESSING_CPF_APPROVAL);
	 * else
	 * cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),advanceBean.getAdvanceTransID(),"CPF",Constants.APPLICATION_PROCESSING_CPF_RECOMMENDATION);
	 *  } catch (SQLException e) { log.printStackTrace(e); } catch (Exception e) {
	 * log.printStackTrace(e); } finally { commonDB.closeConnection(null, st,
	 * con); } return updatedRecords; }
	 */
//	 on 13-Feb-2012 for  saving lastIntrestAmnt for(decimal points clearance ehile calc monthly intrest  )
//	Modified By Radha On 20-Jul-2011 for saving designation in transaction table
	public int updateCPFAdvanceForm2Info(AdvanceCPFForm2Bean advanceBean,
			String frmName, String frmFlag) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		String updateQuery = "", verifiedVal = "", finalTransStatus = "";
		String purposeType = "";
		log.info("updateCPFAdvanceForm2Info::Basic Info"
				+ advanceBean.getPensionNo());
		double emolument = 0.0;
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		if (advanceBean.getPurposeType().equals("OBMARRIAGE"))
			emolument = Double.parseDouble(advanceBean.getEmoluments()) / 6;
		else
			emolument = Double.parseDouble(advanceBean.getEmoluments()) / 3;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			String fmlyDOB = "";

			log.info(advanceBean.getPensionNo());
			log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());

			if (frmName.equals("CPFApproval"))
				verifiedVal = "FINANCE";
			else
				verifiedVal = "REC";

			if (frmName.equals("CPFApproval"))
				finalTransStatus = "A";
			else
				finalTransStatus = "N";

			log.info("=======REMARKS==========="
					+ advanceBean.getAuthrizedRemarks());

			if (frmFlag.equals("NewForm")) {

				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET PURPOSEOPTIONCVRDCLUSE='"
						+ advanceBean.getPrpsecvrdclse()
						+ "',ADVANCETRANSSTATUS='"
						+ advanceBean.getAuthrizedStatus()
						+ "',FINALTRANSSTATUS='"
						+ finalTransStatus
						+ "',APPROVEDDT='"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "',APPROVEDAMNT='"
						+ advanceBean.getAmntAproved()
						+ "',TRNASMNTHEMOLUMENTS='"
						+ emolument
						+ "',PREVIOUSOUTSTANDINGAMT='"
						+ advanceBean.getOutstndamount()
						+ "',SUBSCRIPTIONAMNT='"
						+ advanceBean.getEmpshare()
						+ "',MTHINSTALLMENTAMT='"
						+ advanceBean.getMthinstallmentamt()
						+ "',INTERESTINSTALLMENTS='"
						+ advanceBean.getInterestinstallments()
						+ "',INTERESTINSTALLAMT='"
						+ advanceBean.getIntinstallmentamt()
						+ "',RECOMMENDEDAMT='"
						+ advanceBean.getAmntRecommended()
						+ "',NARRATION='"
						+ advanceBean.getAuthrizedRemarks()
						+ "',LASTMTHINSTALLMENTAMT= '"
						+ advanceBean.getLastmthinstallmentamt()
						+ "',VERIFIEDBY=VERIFIEDBY||','||'"
						+ verifiedVal
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advanceBean.getPensionNo() + "'";

			} else {
				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET PURPOSEOPTIONCVRDCLUSE='"
						+ advanceBean.getPrpsecvrdclse()
						+ "',ADVANCETRANSSTATUS='"
						+ advanceBean.getAuthrizedStatus()
						+ "',FINALTRANSSTATUS='"
						+ finalTransStatus
						+ "',APPROVEDDT='"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "',APPROVEDAMNT='"
						+ advanceBean.getAmntAproved()
						+ "',TRNASMNTHEMOLUMENTS='"
						+ emolument
						+ "',PREVIOUSOUTSTANDINGAMT='"
						+ advanceBean.getOutstndamount()
						+ "',SUBSCRIPTIONAMNT='"
						+ advanceBean.getEmpshare()
						+ "',MTHINSTALLMENTAMT='"
						+ advanceBean.getMthinstallmentamt()
						+ "',INTERESTINSTALLMENTS='"
						+ advanceBean.getInterestinstallments()
						+ "',INTERESTINSTALLAMT='"
						+ advanceBean.getIntinstallmentamt()
						+ "',NARRATION='"
						+ advanceBean.getAuthrizedRemarks()
						+ "',RECOMMENDEDAMT='"
						+ advanceBean.getAmntRecommended()
						+ "',LASTMTHINSTALLMENTAMT= '"
						+ advanceBean.getLastmthinstallmentamt()
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advanceBean.getPensionNo() + "'";

			}
			log.info("CPFPTWAdvanceDAO::updateCPFAdvanceForm2Info"
					+ updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			BeanUtils.copyProperties(transBean, advanceBean);

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
					.getLoginUserId(), advanceBean.getLoginUserName(),
					advanceBean.getLoginUnitCode(), advanceBean
							.getLoginRegion(), advanceBean
							.getLoginUserDispName());

			if (frmName.equals("CPFApproval"))
				cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),
						advanceBean.getAdvanceTransID(),advanceBean.getLoginUserDesignation(), "CPF",
						Constants.APPLICATION_PROCESSING_CPF_APPROVAL,
						transBean);
			else
				cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),
						advanceBean.getAdvanceTransID(),advanceBean.getLoginUserDesignation(), "CPF",
						Constants.APPLICATION_PROCESSING_CPF_RECOMMENDATION,
						transBean);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}
//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int updatePFWAdvanceForm4Info(AdvancePFWFormBean advancePFWBean) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		String updateQuery = "", purposeType = "";

		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		log.info("updatePFWAdvanceForm4Info::Pensionno"
				+ advancePFWBean.getPensionNo());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			String fmlyDOB = "", authrizedStatus = "A";
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET ADVANCETRANSSTATUS='"
					+ advancePFWBean.getAuthrizedStatus()
					+ "',FINALTRANSSTATUS='"
					+ advancePFWBean.getAuthrizedStatus()
					+ "',NTIMESTRNASMNTHEMOLUMENTS='"
					+ advancePFWBean.getMnthsemoluments()
					+ "',APPROVEDREMARKS=APPROVEDREMARKS || '"
					+ advancePFWBean.getAuthrizedRemarks()
					+ "',APPROVEDDT='"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "',APPROVEDAMNT='"
					+ advancePFWBean.getAmntAproved()
					+ "' WHERE ADVANCETRANSID='"
					+ advancePFWBean.getAdvanceTransID()
					+ "' AND PENSIONNO='"
					+ advancePFWBean.getPensionNo() + "'";
			log.info("CPFPTWAdvanceDAO::updatePFWAdvanceForm4Info"
					+ updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			transBean = this.getCPFPFWTransDetails(advancePFWBean
					.getAdvanceTransID());

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advancePFWBean
					.getLoginUserId(), advancePFWBean.getLoginUserName(),
					advancePFWBean.getLoginUnitCode(), advancePFWBean
							.getLoginRegion(), advancePFWBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(transBean, advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID(), advancePFWBean.getLoginUserDesignation(), "PFW",
					Constants.APPLICATION_PROCESSING_PFW_APPROVAL_ED);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}
//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int updatePFWAdvanceForm3Info(AdvancePFWFormBean advancePFWBean,
			String emoluments, String subscriptionAmt, String contributionAmt,
			String cpfFund, String amountRecmded, String mnthsEmoluments,
			String flag, String narration,String firstInsSubAmnt,String firstInsConrtiAmnt) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		String updateQuery = "";
		String purposeType = "", loadElgAmnt = "", amountRecommended = "";
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();

		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();
		log.info("Basic Info" + advancePFWBean.getPensionNo());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			String verfiedBy = "FINANCE";
			String fmlyDOB = "", approvedRemarks = "";
			ArrayList list = new ArrayList();
			list = this.advanceForm2Report(advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID());
			basicReportBean = (AdvanceBasicReportBean) list.get(0);
			loadElgAmnt = this.getPFWAdvanceEmoluments(emoluments, cpfFund,
					basicReportBean.getAdvReqAmnt(), "0", basicReportBean
							.getAdvanceType(),
					basicReportBean.getPurposeType(), basicReportBean
							.getPurposeOptionType());
			String[] lodElgblAmnt = loadElgAmnt.split(",");
			if (amountRecmded.equals("")) {
				amountRecommended = lodElgblAmnt[1];
			} else {
				amountRecommended = amountRecmded;
			}

			if (!advancePFWBean.getAuthrizedRemarks().equals("")) {
				approvedRemarks = advancePFWBean.getAuthrizedRemarks()
						+ ":form3";
			} else {
				approvedRemarks = "";
			}

			log.info(advancePFWBean.getPensionNo()+"flag;;;;;"+flag);

			if (flag.trim().equals("Form3Edit")) {
				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET ADVANCETRANSSTATUS='"
						+ advancePFWBean.getAuthrizedStatus()
						+ "',APPROVEDREMARKS=APPROVEDREMARKS ||','||'"
						+ approvedRemarks
						+ "',PURPOSEOPTIONCVRDCLUSE='"
						+ advancePFWBean.getPrpsecvrdclse()
						+ "',TRNASMNTHEMOLUMENTS='"
						+ emoluments
						+ "',SUBSCRIPTIONAMNT='"
						+ subscriptionAmt
						+ "',CONTRIBUTIONAMOUNT='"
						+ contributionAmt
						+ "',APPROVEDAMNT='"
						+ amountRecommended
						+ "',CPFACCFUND='"
						+ cpfFund
						+ "',NTIMESTRNASMNTHEMOLUMENTS='"
						+ mnthsEmoluments
						+ "',APPROVEDSUBSCRIPTIONAMT='"
						+ advancePFWBean.getApprovedsubamt()
						+ "',APPROVEDCONTRIBUTIONAMT='"
						+ advancePFWBean.getApprovedconamt()
						+ "',NARRATION='"
						+ narration
						+ "',Firstinstallmentsubamt='"
						+ firstInsSubAmnt
						+ "',Firstinstallmentcontriamt='"
						+ firstInsConrtiAmnt
						+ "' WHERE ADVANCETRANSID='"
						+ advancePFWBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advancePFWBean.getPensionNo()
						+ "'";
			} else {
				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET ADVANCETRANSSTATUS='"
						+ advancePFWBean.getAuthrizedStatus()
						+ "',APPROVEDREMARKS=APPROVEDREMARKS ||','||'"
						+ approvedRemarks
						+ "',PURPOSEOPTIONCVRDCLUSE='"
						+ advancePFWBean.getPrpsecvrdclse()
						+ "',VERIFIEDBY=VERIFIEDBY||','||'"
						+ verfiedBy
						+ "',TRNASMNTHEMOLUMENTS='"
						+ emoluments
						+ "',SUBSCRIPTIONAMNT='"
						+ subscriptionAmt
						+ "',CONTRIBUTIONAMOUNT='"
						+ contributionAmt
						+ "',APPROVEDAMNT='"
						+ amountRecommended
						+ "',CPFACCFUND='"
						+ cpfFund
						+ "',NTIMESTRNASMNTHEMOLUMENTS='"
						+ mnthsEmoluments
						+ "',APPROVEDSUBSCRIPTIONAMT='"
						+ advancePFWBean.getApprovedsubamt()
						+ "',APPROVEDCONTRIBUTIONAMT='"
						+ advancePFWBean.getApprovedconamt()
						+ "',NARRATION='"
						+ narration
						+ "',Firstinstallmentsubamt='"
						+ firstInsSubAmnt
						+ "',Firstinstallmentcontriamt='"
						+ firstInsConrtiAmnt
						+ "' WHERE ADVANCETRANSID='"
						+ advancePFWBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advancePFWBean.getPensionNo()
						+ "'";
			}

			log.info("CPFPTWAdvanceDAO::addCPFAdvanceForm3Info" + updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			transBean = this.getCPFPFWTransDetails(advancePFWBean
					.getAdvanceTransID());

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advancePFWBean
					.getLoginUserId(), advancePFWBean.getLoginUserName(),
					advancePFWBean.getLoginUnitCode(), advancePFWBean
							.getLoginRegion(), advancePFWBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(transBean, advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID(), advancePFWBean.getLoginUserDesignation(), "PFW",
					Constants.APPLICATION_PROCESSING_PFW_PART_III_SRMGR);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}
//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int updatePFWForm4Verification(AdvancePFWFormBean advancePFWBean,
			String emoluments, String subscriptionAmt, String contributionAmt,
			String cpfFund, String amountRecmded, String mnthsEmoluments,
			String flag, String approvedSubAmt, String empshare,
			String advnceapplid,String firstInsSubAmnt,String firstInsConrtiAmnt) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		String updateQuery = "";
		String purposeType = "", loadElgAmnt = "", amountRecommended = "";
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			String verfiedBy = "RHQ";
			String fmlyDOB = "";
			ArrayList list = new ArrayList();
			list = this.advanceForm2Report(advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID());
			basicReportBean = (AdvanceBasicReportBean) list.get(0);
			loadElgAmnt = this.getPFWAdvanceEmoluments(emoluments, cpfFund,
					basicReportBean.getAdvReqAmnt(), "0", basicReportBean
							.getAdvanceType(),
					basicReportBean.getPurposeType(), basicReportBean
							.getPurposeOptionType());

			String[] lodElgblAmnt = loadElgAmnt.split(",");

			if (advancePFWBean.getPurposeType().equals("HBA") || advancePFWBean.getPurposeType().equals("SUPERANNUATION")|| advancePFWBean.getPurposeType().equals("PANDEMIC")) {
				if (amountRecmded.equals("")) {
					amountRecommended = lodElgblAmnt[1];
				} else {
					amountRecommended = amountRecmded;
				}
			} else {
				if (approvedSubAmt.equals("")) {
					amountRecommended = lodElgblAmnt[1];
				} else {
					amountRecommended = approvedSubAmt;
				}
			}

			log.info(advancePFWBean.getPensionNo());

			if (flag.trim().equals("Form4VerificationEdit")) {
				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET ADVANCETRANSSTATUS='"
						+ advancePFWBean.getAuthrizedStatus()
						+ "',APPROVEDREMARKS=APPROVEDREMARKS || '"
						+ advancePFWBean.getAuthrizedRemarks()
						+ "',APPROVEDAMNT='"
						+ amountRecommended
						+ "',APPROVEDSUBSCRIPTIONAMT='"
						+ advancePFWBean.getApprovedsubamt()
						+ "',APPROVEDCONTRIBUTIONAMT='"
						+ advancePFWBean.getApprovedconamt()
						+ "',SUBSCRIPTIONAMNT='"
						+ empshare
						+ "',CONTRIBUTIONAMOUNT='"
						+ contributionAmt
						+ "',firstinstallmentsubamt='"
						+ firstInsSubAmnt
						+ "',firstinstallmentcontriamt='"
						+ firstInsConrtiAmnt
						+ "',REQUIREDAMOUNT='"
						+ advnceapplid
						+ "' WHERE ADVANCETRANSID='"
						+ advancePFWBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advancePFWBean.getPensionNo()
						+ "'";
			} else {
				updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET ADVANCETRANSSTATUS='"
						+ advancePFWBean.getAuthrizedStatus()
						+ "',APPROVEDREMARKS=APPROVEDREMARKS || '"
						+ advancePFWBean.getAuthrizedRemarks()
						+ "',VERIFIEDBY=VERIFIEDBY||','||'"
						+ verfiedBy
						+ "',APPROVEDAMNT='"
						+ amountRecommended
						+ "',APPROVEDSUBSCRIPTIONAMT='"
						+ advancePFWBean.getApprovedsubamt()
						+ "',APPROVEDCONTRIBUTIONAMT='"
						+ advancePFWBean.getApprovedconamt()
						+ "',SUBSCRIPTIONAMNT='"
						+ empshare
						+ "',CONTRIBUTIONAMOUNT='"
						+ contributionAmt
						+ "',REQUIREDAMOUNT='"
						+ advnceapplid
						+ "' WHERE ADVANCETRANSID='"
						+ advancePFWBean.getAdvanceTransID()
						+ "' AND PENSIONNO='"
						+ advancePFWBean.getPensionNo()
						+ "'";
			}

			log.info("CPFPTWAdvanceDAO::updatePFWForm4Verification"
					+ updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			transBean = this.getCPFPFWTransDetails(advancePFWBean
					.getAdvanceTransID());

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advancePFWBean
					.getLoginUserId(), advancePFWBean.getLoginUserName(),
					advancePFWBean.getLoginUnitCode(), advancePFWBean
							.getLoginRegion(), advancePFWBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(transBean, advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID(), advancePFWBean.getLoginUserDesignation(), "PFW",
					Constants.APPLICATION_PROCESSING_PFW_PART_IV_SECRETARY);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	public String getAdvanceSequence(Connection con) {
		String advSeqVal = "";
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "SELECT ADVANCESEQ.NEXTVAL AS ADVANCETRANSID FROM DUAL";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				advSeqVal = rs.getString("ADVANCETRANSID");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return advSeqVal;
	}

	public EmpBankMaster loadEmployeeBankInfo(String pensionno) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmpBankMaster bankMaster = new EmpBankMaster();
		String sqlQuery = "SELECT * FROM EMPLOYEE_BANK_INFO WHERE PENSIONNO="
				+ pensionno;
		log.info("CPFPTWAdvanceDAO::loadEmployeeBankInfo()" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("NAME") != null) {
					bankMaster.setBankempname(rs.getString("NAME"));
				}
				if (rs.getString("ADDRESS") != null) {
					bankMaster.setBankempaddrs(rs.getString("ADDRESS"));
				}
				if (rs.getString("BANKNAME") != null) {
					bankMaster.setBankname(rs.getString("BANKNAME"));
				}
				if (rs.getString("BRANCHADDRESS") != null) {
					bankMaster.setBranchaddress(rs.getString("BRANCHADDRESS"));
				}
				if (rs.getString("SAVINGBNKACCNO") != null) {
					bankMaster.setBanksavingaccno(rs
							.getString("SAVINGBNKACCNO"));
				}
				if (rs.getString("NEFTRTGSCODE") != null) {
					bankMaster.setBankemprtgsneftcode(rs
							.getString("NEFTRTGSCODE"));
				}
				if (rs.getString("MICRONO") != null) {
					bankMaster.setBankempmicrono(rs.getString("MICRONO"));
				}
				if (rs.getString("MAILID") != null) {
					bankMaster.setEmpmailid(rs.getString("MAILID"));
				}
				if (rs.getString("CPFPFWTRANSID") != null) {
					bankMaster.setTransId(rs.getString("CPFPFWTRANSID"));
				}
				if (rs.getString("PAYMENTMODE") != null) {
					bankMaster.setBankpaymentmode(rs.getString("PAYMENTMODE"));
				}
				if (rs.getString("CITY") != null) {
					bankMaster.setCity(rs.getString("CITY"));
				}
				bankMaster.setChkBankInfo("Y");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return bankMaster;
	}
//	 By Radha On 26-Apr-2011 for adding party details 
	public int addEmployeeBankInfo(Connection con, EmpBankMaster bankMaster, String pensionNo) {
		int totalRecords = 0, updatedRecords = 0, updatedRecords1 = 0; 
		Statement st = null;
		ResultSet rs = null;
		String updatePaymentQry = "", updatePaymentQry1 = "";
		log.info("------Emp Mail id--------" + bankMaster.getEmpmailid());
		try { 
			st = con.createStatement();
			String insertBankMsterQry = "INSERT INTO EMPLOYEE_BANK_INFO(PENSIONNO,NAME,ADDRESS,MAILID,BANKNAME,BRANCHADDRESS,SAVINGBNKACCNO,NEFTRTGSCODE,MICRONO,PAYMENTMODE,CITY,CPFPFWTRANSID,PARTYNAME,PARTYADDRESS,USERNAME,LASTACTIVE)VALUES("
					+ pensionNo
					+ ",'"
					+ bankMaster.getBankempname()
					+ "','"
					+ bankMaster.getBankempaddrs()
					+ "','"
					+ bankMaster.getEmpmailid()
					+ "','"
					+ bankMaster.getBankname()
					+ "','"
					+ bankMaster.getBranchaddress()
					+ "','"
					+ bankMaster.getBanksavingaccno()
					+ "','"
					+ bankMaster.getBankemprtgsneftcode()
					+ "','"
					+ bankMaster.getBankempmicrono()
					+ "','"
					+ bankMaster.getBankpaymentmode()
					+ "','"
					+ bankMaster.getCity()
					+ "','"
					+ Long.parseLong(bankMaster.getTransId())
					+ "','"
					+ bankMaster.getPartyName()
					+ "','"
					+ bankMaster.getPartyAddress()
					+ "','"
					+ this.userName
					+ "','" + commonUtil.getCurrentDate("dd-MMM-yyyy") + "')";
			log
					.info("CPFPTWAdvanceDAO::addEmployeeBankInfo()--insertBankMsterQry "
							+ insertBankMsterQry);

			totalRecords = st.executeUpdate(insertBankMsterQry);

			updatePaymentQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set PAYMENTINFO='Y' where  NSSANCTIONNO='"
					+ bankMaster.getTransId()
					+ "' and pensionno='"
					+ pensionNo
					+ "'";
			updatedRecords = st.executeUpdate(updatePaymentQry);
			log
					.info("CPFPTWAdvanceDAO::addEmployeeBankInfo()--updatePaymentQry "
							+ updatePaymentQry);
 
			updatePaymentQry1 = "update EMPLOYEE_ADVANCES_FORM  set PAYMENTINFO='Y',"
			    +" PARTYNAME = '"+bankMaster.getPartyName()+"',"
			    +" PARTYADDRESS = '"+bankMaster.getPartyAddress()+"' where  ADVANCETRANSID='"
				+ bankMaster.getTransId()
				+ "' and pensionno='"
				+ pensionNo
				+ "'";
			updatedRecords1 = st.executeUpdate(updatePaymentQry1);
			log
					.info("CPFPTWAdvanceDAO::addEmployeeBankInfo()--updatePaymentQry "
							+ updatePaymentQry1);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			DBUtility.closeConnection(rs, st, null);
		}
		return totalRecords;
	}

	public ArrayList loadWithDrawalDetails(String transactionID,
			AdvanceBasicReportBean reportBean, Connection con) {

		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean withDrawalBean = null;

		String sqlQuery = "SELECT WTHDRWLID,WTHDRWLPURPOSE,WTHDRWLAMOUNT,WTHDRWLTRNSDT FROM employee_advances_wthdrwl_info WHERE WTHDRWLSTATUS='Y' and ADVANCETRANSID="
				+ transactionID + " order  by WTHDRWLID";
		log.info("loadWithDrawalDetails" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				withDrawalBean = new AdvanceBasicReportBean();

				if (rs.getString("WTHDRWLID") != null) {
					withDrawalBean.setWthdrwid(rs.getString("WTHDRWLID"));
				}

				if (rs.getString("WTHDRWLPURPOSE") != null) {
					withDrawalBean.setWthdrwlpurpose(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("WTHDRWLPURPOSE")));
				}

				if (rs.getString("WTHDRWLAMOUNT") != null) {
					withDrawalBean.setWthdrwlAmount(rs
							.getString("WTHDRWLAMOUNT"));
				}
				if (rs.getString("WTHDRWLTRNSDT") != null) {
					withDrawalBean.setWthDrwlTrnsdt(CommonUtil.getDatetoString(
							rs.getDate("WTHDRWLTRNSDT"), "dd-MMM-yyyy"));
				}

				reportList.add(withDrawalBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}
		return reportList;
	}

	public String loadWithDrawnDetails(String transactionID,
			AdvanceBasicReportBean reportBean, Connection con) {
		int totalRecords = 0;

		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean withDrawalBean = null;
		StringBuffer buffer = new StringBuffer();
		String sqlQuery = "SELECT WTHDRWLID,WTHDRWLPURPOSE,WTHDRWLAMOUNT,WTHDRWLTRNSDT FROM employee_advances_wthdrwl_info WHERE WTHDRWLSTATUS='Y' and ADVANCETRANSID="
				+ transactionID;
		log.info("loadPFWMarriage" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				withDrawalBean = new AdvanceBasicReportBean();

				if (rs.getString("WTHDRWLID") != null) {
					withDrawalBean.setWthdrwid(rs.getString("WTHDRWLID"));
				}

				if (rs.getString("WTHDRWLPURPOSE") != null) {
					withDrawalBean.setWthdrwlpurpose(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("WTHDRWLPURPOSE")));
				}

				if (rs.getString("WTHDRWLAMOUNT") != null) {
					withDrawalBean.setWthdrwlAmount(rs
							.getString("WTHDRWLAMOUNT"));
				}
				if (rs.getString("WTHDRWLTRNSDT") != null) {
					withDrawalBean.setWthDrwlTrnsdt(CommonUtil.getDatetoString(
							rs.getDate("WTHDRWLTRNSDT"), "dd-MMM-yyyy"));
				}
				buffer.append(withDrawalBean.getWthdrwid());
				buffer.append(",");
				buffer.append(withDrawalBean.getWthdrwlpurpose());
				buffer.append(",");
				buffer.append(withDrawalBean.getWthdrwlAmount());
				buffer.append(",");
				buffer.append(withDrawalBean.getWthDrwlTrnsdt());
				buffer.append(":");
				// reportList.add(buffer.toString());
				log.info("buffer.toString()" + buffer.toString());
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}
		return buffer.toString();
	}

	public ArrayList advanceReport(String pensionNo, String transactionID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String transID = "";
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		ArrayList withDrawalList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String purposeType = "", chkWithDrawal = "";
		String sqlQuery = "SELECT PI.EMPLOYEENO AS EMPLOYEENO,PI.EMPLOYEENAME AS EMPLOYEENAME,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING	AS DATEOFJOINING,PI.FHNAME AS FHNAME, PI.DEPARTMENT AS DEPARTMENT,PI.EMAILID AS EMAILID,AF.PENSIONNO,AF.ADVANCETYPE,"
				+ "AF.USERPURPOSETYPE,AF.USERTRNASMNTHEMOLUMENTS AS USERTRNASMNTHEMOLUMENTS,AF.USERTOTALINATALLMENTS AS USERTOTALINATALLMENTS,AF.ADVANCETRANSID AS  ADVANCETRANSID ,AF.ADVANCETRANSDT AS  ADVANCETRANSDT ,AF.ADVANCETYPE  AS  ADVANCETYPE ,AF.PURPOSETYPE AS  PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS  PURPOSEOPTIONTY,AF.USERREQUIREDAMOUNT AS USERREQUIREDAMOUNT ,AF.ADVANCETRANSSTATUS  AS  ADVANCETRANSSTA,"
				+ "AF.paymentinfo AS paymentinfo,AF.region AS region,AF.TRUST AS TRUST,AF.REASON AS REASON,AF.IPADDRESS  AS IPADDRESS,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.USERNAME AS USERNAME,AF.PARTYNAME AS  PARTYNAME,AF.PARTYADDRESS AS PARTYADDRESS ,AF.USERLOD AS USERLOD,AF.USERCHKWTHDRWL AS USERCHKWTHDRWL,AF.PLACEOFPOSTING AS PLACEOFPOSTING,"
				+ "round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) AS ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF WHERE AF.PENSIONNO = PI.PENSIONNO AND AF.DELETEFLAG='N' AND AF.PENSIONNO ="
				+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

		log.info("CPFPTWAdvanceDAO::advanceReport" + sqlQuery);
		String lod = "", optionTypeDesc = "", oblCermonyDesc = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("USERCHKWTHDRWL") != null) {
					chkWithDrawal = rs.getString("USERCHKWTHDRWL");
				} else {
					chkWithDrawal = "N";
				}

				if (rs.getString("PLACEOFPOSTING") != null) {
					basicBean.setPlaceofposting(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("PLACEOFPOSTING")));
				} else {
					basicBean.setPlaceofposting("");
				}
				if (chkWithDrawal.trim().equals("Y")) {

					withDrawalList = this.loadWithDrawalDetails(rs.getString(
							"ADVANCETRANSID").trim(), basicBean, con);

				}

				/*
				 * if (rs.getString("WTHDRWLTRNSDT") != null) {
				 * basicBean.setWthDrwlTrnsdt(CommonUtil.getDatetoString(rs
				 * .getDate("WTHDRWLTRNSDT"), "dd-MMM-yyyy")); } if
				 * (rs.getString("WTHDRWLAMOUNT") != null) {
				 * basicBean.setWthdrwlAmount(rs.getString("WTHDRWLAMOUNT")); }
				 * else { basicBean.setWthdrwlAmount("0.00"); }
				 */
				if (rs.getString("USERCHKWTHDRWL") != null) {
					basicBean.setChkWthdrwlInfo(rs.getString("USERCHKWTHDRWL")
							.trim());
				}
				/*
				 * if (rs.getString("WTHDRWLPURPOSE") != null) {
				 * basicBean.setWthdrwlpurpose(rs.getString("WTHDRWLPURPOSE")); }
				 */

				if (rs.getString("PARTYNAME") != null) {
					basicBean.setPartyName(rs.getString("PARTYNAME"));
				}
				if (rs.getString("PARTYADDRESS") != null) {
					basicBean.setPartyAddress(rs.getString("PARTYADDRESS"));
				}
				if (!(basicBean.getPartyName().equals("") && basicBean
						.getPartyName().equals(""))) {
					basicBean.setChkBankFlag("Y");
				} else {
					basicBean.setChkBankFlag("N");
				}
				if (rs.getString("PARTYNAME") != null) {
					basicBean.setPartyName(rs.getString("PARTYNAME"));
				}
				if (rs.getString("USERPURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("USERPURPOSETYPE"));

					if (basicBean.getPurposeType().equals("HE")) {
						basicBean.setDispPurposeTypeText("Higher Education");
					} else if (basicBean.getPurposeType().equals("MARRIAGE")) {
						basicBean.setDispPurposeTypeText("Marriage");
					} else if (basicBean.getPurposeType().equals("HBA")) {
						basicBean.setDispPurposeTypeText("HBA");
					}

				}
				if (rs.getString("ADVNCERQDDEPEND") != null) {
					basicBean.setAdvncerqddepend(rs
							.getString("ADVNCERQDDEPEND"));
				} else {
					basicBean.setAdvncerqddepend("");
				}
				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					basicBean.setUtlisiedamntdrwn(rs
							.getString("UTLISIEDAMNTDRWN"));
				} else {
					basicBean.setUtlisiedamntdrwn("");
				}

				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				}
				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setTransMnthYear(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				} else {
					basicBean.setTransMnthYear("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("---");
				}
				if (rs.getString("PURPOSEOPTIONTY") != null) {
					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTY"));
					if (basicBean.getPurposeType().equals("HBA")) {
						basicBean.setPurposeOptionType(commonDAO
								.getHBAPurposeOptionDesc(basicBean
										.getPurposeOptionType()));
					}
					basicBean.setDispPurposeOptionTxt(commonUtil
							.convertToLetterCase(basicBean
									.getPurposeOptionType()));
				}
				log.info("AdvanceReport::Purpose OptionType"
						+ basicBean.getPurposeOptionType());
				log.info("AdvanceReport::Advance Type"
						+ basicBean.getAdvanceType());
				if (basicBean.getPurposeType().equals("OBLIGATORY")) {
					String[] optionType = basicBean.getPurposeOptionType()
							.split("-");
					log.info("Purpose Option Lenght" + optionType.length);
					optionTypeDesc = optionType[0];
					oblCermonyDesc = optionType[1];
					log.info("OTHER CEREMONIES" + optionTypeDesc);
					if (optionTypeDesc.equals("OTHER CEREMONIES")) {
						basicBean.setPurposeOptionType(optionTypeDesc);
						basicBean.setOblCermoney(oblCermonyDesc);

					}
				}
				log.info("AdvanceReport::Purpose OptionType"
						+ basicBean.getPurposeOptionType());
				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfBirth("---");
				}
				if (rs.getString("USERREQUIREDAMOUNT") != null) {
					basicBean.setAdvReqAmnt(rs.getString("USERREQUIREDAMOUNT"));
				} else {
					basicBean.setAdvReqAmnt("0.0");
				}
				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {
					basicBean.setTrust("---");
				}
				if (rs.getString("paymentinfo") != null) {
					basicBean.setPaymentinfo(rs.getString("paymentinfo"));
				} else {
					basicBean.setPaymentinfo("N");
				}
				if (rs.getString("REGION") != null) {
					basicBean.setRegion(rs.getString("REGION"));
				} else {
					basicBean.setRegion("---");
				}
				if (rs.getString("USERTRNASMNTHEMOLUMENTS") != null) {
					basicBean.setEmoluments(rs
							.getString("USERTRNASMNTHEMOLUMENTS"));
				} else {
					basicBean.setEmoluments("0.0");
				}
				if (rs.getString("USERTOTALINATALLMENTS") != null) {
					basicBean.setCpfIntallments(rs
							.getString("USERTOTALINATALLMENTS"));
				} else {
					basicBean.setCpfIntallments("0");
				}
				if (rs.getString("REASON") != null) {
					basicBean.setAdvReasonText(rs.getString("REASON"));
				} else {
					basicBean.setAdvReasonText("");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}
				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				if (rs.getString("ADVANCETRANSID") != null) {
					transID = rs.getString("ADVANCETRANSID");
					basicBean.setAdvanceTransID(basicBean.getAdvanceType()
							.toUpperCase()
							+ "/"
							+ basicBean.getPurposeType().toUpperCase()
							+ "/" + rs.getString("ADVANCETRANSID"));
				} else {
					basicBean.setAdvanceTransID("");
				}

				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("FHNAME")));
				} else {
					basicBean.setFhName("");
				}
				if (rs.getString("PENSIONNO") != null) {
					basicBean.setPensionNo(rs.getString("PENSIONNO"));
				}
				if (rs.getString("USERLOD") != null) {
					lod = rs.getString("USERLOD");
					basicBean.setLodInfo(this.loadListOfDocument(lod));
				} else {
					basicBean.setLodInfo("---");
				}
				log.info("advanceReport::Purspose Type"
						+ basicBean.getPurposeType());
				if (basicBean.getPurposeType().equals("HBA")) {
					basicBean = this.loadPFWHBA(transID, basicBean);
				} else if (basicBean.getPurposeType().toUpperCase().equals(
						"MARRIAGE")
						|| basicBean.getPurposeType().equals("COST")
						|| basicBean.getPurposeType().equals("ILLNESS")
						|| basicBean.getPurposeType().trim().equals(
								"OBMARRIAGE")) {
					log.info("advanceReport::Purspose Type Next "
							+ basicBean.getPurposeType());
					basicBean = this.loadPFWMarriage(transID, basicBean);
				} else if (basicBean.getPurposeType().equals("EDUCATION")
						|| basicBean.getPurposeType().equals("HE")) {
					basicBean = this.loadPFWMarriage(transID, basicBean);
					basicBean = this.loadPFWHE(transID, basicBean);
				}
				log.info("Family Employee Name" + basicBean.getFmlyEmpName());
				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");

				/*
				 * if (noOfYears > 0) {
				 * basicBean.setDateOfMembership(basicBean.getDateOfJoining()); }
				 * else { basicBean.setDateOfMembership("01-Apr-1995"); }
				 */
				basicBean.setDateOfMembership(basicBean.getDateOfJoining());
				if (!basicBean.getDateOfBirth().equals("---")) {
					String personalPFID = commonDAO.getPFID(basicBean
							.getEmployeeName(), basicBean.getDateOfBirth(),
							commonUtil
									.leadingZeros(5, basicBean.getPensionNo()));
					basicBean.setPfid(personalPFID);
				} else {
					basicBean.setPfid(commonUtil.leadingZeros(5, basicBean
							.getPensionNo()));
				}

				bankMasterBean = loadEmployeeBankInfo(basicBean.getPensionNo(),
						transID);
				reportList.add(basicBean);
				reportList.add(bankMasterBean);
				log
						.info("withDrawalList======================================="
								+ withDrawalList.size());
				if (withDrawalList.size() > 0) {
					log
							.info("withDrawalList============sssssssss==========================="
									+ withDrawalList.size());
					reportList.add(withDrawalList);
				} else {
					reportList.add(null);
				}

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	public AdvanceBasicReportBean loadPFWHE(String transactionID,
			AdvanceBasicReportBean reportBean) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = "SELECT NMCOURSE,NMINSTITUTION,INSTITUTIONADDRSS,DURATIONCOURSE,RECOGNIZED,LASTEXAMPASSED FROM EMPLOYEE_ADVANCES_HE_INFO WHERE ADVANCETRANSID="
				+ transactionID;
		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("NMCOURSE") != null) {
					reportBean.setNmCourse(rs.getString("NMCOURSE"));
				}
				if (rs.getString("NMINSTITUTION") != null) {
					reportBean.setNmInstitue(rs.getString("NMINSTITUTION"));
				}
				if (rs.getString("INSTITUTIONADDRSS") != null) {
					reportBean.setAddrInstitue(rs
							.getString("INSTITUTIONADDRSS"));
				}
				if (rs.getString("DURATIONCOURSE") != null) {
					reportBean.setCurseDuration(rs.getString("DURATIONCOURSE"));
				}
				if (rs.getString("LASTEXAMPASSED") != null) {
					reportBean
							.setHeLastExaminfo(rs.getString("LASTEXAMPASSED"));
				}
				if (rs.getString("RECOGNIZED") != null) {
					reportBean.setHeRecog(rs.getString("RECOGNIZED"));
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return reportBean;
	}

	public AdvanceBasicReportBean loadPFWMarriage(String transactionID,
			AdvanceBasicReportBean reportBean) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = "SELECT NAME,AGE,DATEOFBIRTH,AGELOD,MARRIAGEDATE FROM EMPLOYEE_ADVANCES_FAMILY_DTLS WHERE ADVANCETRANSID="
				+ transactionID;
		log.info("loadPFWMarriage" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("NAME") != null) {
					reportBean.setFmlyEmpName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("NAME")));
				}
				if (rs.getString("AGE") != null) {
					reportBean.setFmlyAge(rs.getString("AGE"));
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					reportBean.setFmlyDOB(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
				}
				if (rs.getString("AGELOD") != null) {
					reportBean.setBrthCertProve(rs.getString("AGELOD"));
				}
				if (rs.getString("MARRIAGEDATE") != null) {
					reportBean.setMarriagedate(CommonUtil.getDatetoString(rs
							.getDate("MARRIAGEDATE"), "dd-MMM-yyyy"));
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportBean;
	}
	public int isPFWPandemic(String pfid) {
		int count = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = "select count(*) as count from employee_advances_form f where f.pensionno="+pfid+" and f.advancetype='PFW' and f.purposetype='PANDEMIC' and f.deleteflag='N'";
		log.info("check pandemic :" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				count=rs.getInt("count");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return count;
	}

	public String loadListOfDocument(String lod) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String lodDesc = "";
		String[] lods = lod.split(",");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < lods.length; i++) {
			buffer.append("'");
			buffer.append(lods[i]);
			buffer.append("',");
		}
		int j = 0;
		lodDesc = buffer.toString().substring(0,
				buffer.toString().lastIndexOf(","));
		String sqlQuery = "SELECT CDDESC FROM ENCLOSEDDOC WHERE CD IN("
				+ lodDesc + ")";
		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			buffer = new StringBuffer();
			while (rs.next()) {

				j++;
				buffer.append("<br/>");
				buffer.append("\n" + j + "." + rs.getString("CDDESC"));
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		log.info("buffer " + buffer.toString());
		return buffer.toString();
	}

	public AdvanceBasicReportBean loadPFWHBA(String transactionID,
			AdvanceBasicReportBean reportBean) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = "SELECT HBADRWNFRMAAI,PERMISSIONAAI,CITY,MUNICIPALITY,LOCALITY,PLOTNO,AREA,ADDRESS,NAME,REPAYMENTAMOUNT,REPAYMENTADDRESS,REPAYMENTNAME,HBAOTHERS,REPAYMENTLOANTYPE,ACTUALCOST,PROPERTYADDRESS,HBADRWNFRMAAIPURPOSE,HBADRWNFRMAAIAMOUNT,HBADRWNFRMAAIADDRESS FROM EMPLOYEE_ADVANCE_HBA_INFO WHERE ADVANCETRANSID="
				+ transactionID;
		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("HBADRWNFRMAAI") != null) {
					reportBean.setHbadrwnfrmaai(rs.getString("HBADRWNFRMAAI")
							.trim());
				}
				if (rs.getString("PERMISSIONAAI") != null) {
					reportBean.setHbapermissionaai(rs
							.getString("PERMISSIONAAI").trim());
				}
				if (rs.getString("CITY") != null) {
					reportBean.setHbaownercity(rs.getString("CITY"));
				}
				if (rs.getString("MUNICIPALITY") != null) {
					reportBean
							.setHbaownermuncipal(rs.getString("MUNICIPALITY"));
				}
				if (rs.getString("LOCALITY") != null) {
					reportBean.setHbaownerlocality(rs.getString("LOCALITY"));
				}
				if (rs.getString("PLOTNO") != null) {
					reportBean.setHbaownerplotno(rs.getString("PLOTNO"));
				}
				if (rs.getString("AREA") != null) {
					reportBean.setHbaownerarea(rs.getString("AREA"));
				}
				if (rs.getString("ADDRESS") != null) {
					reportBean.setHbaowneraddress(rs.getString("ADDRESS"));
				}
				if (rs.getString("NAME") != null) {
					reportBean.setHbaownername(rs.getString("NAME"));
				}

				if (rs.getString("REPAYMENTAMOUNT") != null) {
					reportBean.setOsamountwithinterest(rs
							.getString("REPAYMENTAMOUNT"));
				}
				if (rs.getString("REPAYMENTADDRESS") != null) {
					reportBean.setHbaloanaddress(rs
							.getString("REPAYMENTADDRESS"));
				}
				if (rs.getString("REPAYMENTNAME") != null) {
					reportBean.setHbaloanname(rs.getString("REPAYMENTNAME"));
				}
				if (rs.getString("HBAOTHERS") != null) {
					reportBean.setCurseDuration(rs.getString("HBAOTHERS"));
				}
				if (rs.getString("REPAYMENTLOANTYPE") != null) {
					reportBean.setHbarepaymenttype(rs
							.getString("REPAYMENTLOANTYPE"));
				}
				if (rs.getString("ACTUALCOST") != null) {
					reportBean.setActualcost(rs.getString("ACTUALCOST"));
				} else {
					reportBean.setActualcost("0.00");
				}
				if (rs.getString("PROPERTYADDRESS") != null) {
					reportBean.setPropertyaddress(rs
							.getString("PROPERTYADDRESS"));
				} else {
					reportBean.setPropertyaddress("");
				}

				if (rs.getString("HBADRWNFRMAAIPURPOSE") != null) {
					reportBean.setHbawthdrwlpurpose(rs
							.getString("HBADRWNFRMAAIPURPOSE"));
				} else {
					reportBean.setHbawthdrwlpurpose("");
				}
				if (rs.getString("HBADRWNFRMAAIAMOUNT") != null) {
					reportBean.setHbawthdrwlamount(rs
							.getString("HBADRWNFRMAAIAMOUNT"));
				} else {
					reportBean.setHbawthdrwlamount("");
				}
				if (rs.getString("HBADRWNFRMAAIADDRESS") != null) {
					reportBean.setHbawthdrwladdress(rs
							.getString("HBADRWNFRMAAIADDRESS"));
				} else {
					reportBean.setHbawthdrwladdress("");
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return reportBean;
	}

	// ----------------------------------advanceForm2Report() Added by Suneetha
	// V on 07/10/2009--------------------------------------------------

	public ArrayList advanceForm2Report(String pensionNo, String transactionID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String transID = "", dateOfBirth = "", pfid = "", optionTypeDesc = "", oblCermonyDesc = "";
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String purposeTye = "", sqlQuery = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT PI.EMPLOYEENO AS EMPLOYEENO,PI.GENDER AS GENDER,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,INITCAP(PI.FHNAME) AS FHNAME, PI.DEPARTMENT AS DEPARTMENT,PI.EMAILID AS EMAILID,AF.PENSIONNO,AF.ADVANCETYPE AS ADVANCETYPE,"
					+ "AF.PURPOSETYPE,AF.trnasmnthemoluments AS trnasmnthemoluments,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.ADVANCETRANSID AS  ADVANCETRANSID ,AF.ADVANCETRANSDT AS  ADVANCETRANSDT ,AF.ADVANCETYPE  AS  ADVANCETYPE ,AF.PURPOSETYPE AS  PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS  PURPOSEOPTIONTY,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT ,AF.LOANTAKEN AS LOANTAKEN,AF.ADVANCETRANSSTATUS  AS  ADVANCETRANSSTA,"
					+ "AF.REASON AS REASON,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,AF.APPROVEDREMARKS AS APPROVEDREMARKS,AF.IPADDRESS  AS IPADDRESS,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.USERNAME AS USERNAME,AF.PARTYNAME AS  PARTYNAME,AF.PARTYADDRESS AS PARTYADDRESS ,AF.LOD AS LOD,AF.CHKWTHDRWL AS CHKWTHDRWL,AF.PAYMENTINFO AS PAYMENTINFO,"
					+ "round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) AS ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF WHERE AF.PENSIONNO = PI.PENSIONNO AND AF.DELETEFLAG='N' AND AF.PENSIONNO ="
					+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

			log.info("-------advanceForm2Report:sqlQuery-------" + sqlQuery);
			String lod = "";

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {
					transID = rs.getString("ADVANCETRANSID");
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
					basicBean.setAdvntrnsid(rs.getString("ADVANCETRANSID"));
				}
				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("---");
				}

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));

					if (rs.getString("PURPOSETYPE").equals("HBA")) {
						basicBean.setPurposeTypeDescr(rs
								.getString("PURPOSETYPE"));
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						basicBean.setPurposeTypeDescr("Higher Education");
					} else {
						basicBean.setPurposeTypeDescr(commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE")));
					}

				} else {
					basicBean.setPurposeType("");
				}
				log.info("-----PURPOSETYPE------" + basicBean.getPurposeType()
						+ "Description" + basicBean.getPurposeTypeDescr());
				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("---");
				}
				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {
					basicBean.setGender("---");
				}

				if (rs.getString("APPROVEDREMARKS") != null) {
					basicBean.setApprovedremarks(rs
							.getString("APPROVEDREMARKS"));
				} else {
					basicBean.setApprovedremarks("");
				}
				if (rs.getString("LOANTAKEN") != null) {
					basicBean.setTakenloan(rs.getString("LOANTAKEN"));
				} else {
					basicBean.setTakenloan("");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean.setAdvtransstatus(rs
							.getString("ADVANCETRANSSTATUS"));
				} else {
					basicBean.setAdvtransstatus("");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					basicBean.setDesignation("");
				}
				log
						.info("-----DESEGNATION------"
								+ rs.getString("DESEGNATION"));
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(rs.getString("FHNAME"));
				} else {
					basicBean.setFhName("");
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				} else {
					basicBean.setAdvtransdt("");
				}

				if (rs.getString("CHKWTHDRWL") != null) {
					basicBean.setChkWthdrwlInfo(rs.getString("CHKWTHDRWL")
							.trim());
				}

				if (rs.getString("PURPOSEOPTIONTY") != null) {

					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTY"));
					if (basicBean.getPurposeType().equals("HBA")) {
						basicBean.setPurposeOptionType(commonDAO
								.getHBAPurposeOptionDesc(basicBean
										.getPurposeOptionType()));
					}

				}
				if (basicBean.getPurposeOptionType().indexOf("-") != -1) {
					String[] optionType = basicBean.getPurposeOptionType()
							.split("-");
					optionTypeDesc = optionType[0];
					oblCermonyDesc = optionType[1];
					if (optionTypeDesc.equals("OTHERCEREMONIES")) {
						basicBean.setPurposeOptionType(optionTypeDesc);
						basicBean.setOblCermoney(oblCermonyDesc);
					}
				}

				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setAdvReqAmnt(rs.getString("REQUIREDAMOUNT"));
				} else {
					basicBean.setAdvReqAmnt("0.0");
				}

				if (rs.getString("trnasmnthemoluments") != null) {
					basicBean
							.setEmoluments(rs.getString("trnasmnthemoluments"));
				} else {
					basicBean.setEmoluments("0.0");
				}

				if (basicBean.getPurposeType().equals("HBA")) {
					basicBean = this.loadPFWHBA(transID, basicBean);
				} else if (basicBean.getPurposeType().toUpperCase().equals(
						"MARRIAGE")
						|| basicBean.getPurposeType().equals("COST")
						|| basicBean.getPurposeType().equals("ILLNESS")
						|| basicBean.getPurposeType().trim().equals(
								"OBMARRIAGE")) {
					log.info("advanceReport::Purspose Type Next "
							+ basicBean.getPurposeType());
					basicBean = this.loadPFWMarriage(transID, basicBean);
				} else if (basicBean.getPurposeType().equals("EDUCATION")
						|| basicBean.getPurposeType().equals("HE")) {
					basicBean = this.loadPFWMarriage(transID, basicBean);
					basicBean = this.loadPFWHE(transID, basicBean);
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				if (!pensionNo.equals(""))
					basicBean.setPensionNo(pensionNo);

				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				}

				reportList.add(basicBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	// ----------------------------------End :
	// advanceForm2Report()--------------------------------------------------
	// ----------------------------------advanceForm3Report () Added by Suneetha
	// V on 07/10/2009--------------------------------------------------
	public ArrayList advanceForm3Report(String pensionNo, String transactionID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String transID = "", dateOfBirth = "", pfid = "";
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String purposeTye = "", sqlQuery = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "SELECT PI.EMPLOYEENO AS EMPLOYEENO,PI.EMPLOYEENAME AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.PURPOSETYPE AS PURPOSETYPE,AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.ADVANCETRANSID  AS ADVANCETRANSID from EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO AND AF.DELETEFLAG='N' and  AF.PENSIONNO ="
					+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;
			log.info("-------advanceForm3Report:sqlQuery-------" + sqlQuery);
			String lod = "";

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("EMOLUMENTS") != null) {
					basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					basicBean.setEmoluments("0.0");
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");
				log.info("-----noOfYears-----" + noOfYears);

				if (noOfYears > 0) {
					basicBean.setDateOfMembership(basicBean.getDateOfJoining());
				} else {
					basicBean.setDateOfMembership("01-Apr-1995");
				}
				bankMasterBean = loadEmployeeBankInfo(pensionNo);
				reportList.add(basicBean);
				reportList.add(bankMasterBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	// ----------------------------------End :
	// advanceForm3Report()--------------------------------------------------
	public String getCPFAdvanceEmoluments(String emoluments,
			String subscriptionAmnt, String reqstAdvnceAmnt,
			String outStandAmount, String advanceType, String purposeType,
			String purposeOptionType) throws InvalidDataException {
		double elgEmoluments = 0.0, elgSubScriptionAmnt = 0.0, elgAmnt = 0.0, finalElAmnt = 0.0, recmndAmnt = 0.0;
		StringBuffer buffer = new StringBuffer();
		log.info("getCPFAdvanceEmoluments::advanceType===" + advanceType
				+ "purposeType" + purposeType);
		log.info("-----subscriptionAmnt------" + subscriptionAmnt);
		try {
			if (advanceType.equals("CPF")) {
				if (purposeType.equals("COST") || purposeType.equals("DEFENCE")
						|| purposeType.equals("ILLNESS")
						|| purposeType.equals("OBLIGATORY")
						|| purposeType.equals("EDUCATION")|| purposeType.equals("OTHERS")) {
					elgEmoluments = (3 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(subscriptionAmnt) * 50 / 100);
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					log.info("elgAmnt==" + elgAmnt);
				} else if (purposeType.equals("OBMARRIAGE")) {
					elgEmoluments = (6 * Double.parseDouble(emoluments));
					elgSubScriptionAmnt = (Double.parseDouble(subscriptionAmnt) * 50 / 100);
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
				}
				if (elgAmnt < Double.parseDouble(reqstAdvnceAmnt)) {
					finalElAmnt = elgAmnt;
				} else {
					finalElAmnt = Double.parseDouble(reqstAdvnceAmnt);
				}
				recmndAmnt = finalElAmnt - Double.parseDouble(outStandAmount);
			}
			log.info("getCPFAdvanceEmoluments::elgAmnt" + elgAmnt
					+ "recmndAmnt" + recmndAmnt);
			buffer.append(elgEmoluments);
			buffer.append(",");
			buffer.append(recmndAmnt);
			buffer.append(",");
			buffer.append(elgSubScriptionAmnt);
		} catch (Exception e) {
			throw new InvalidDataException(e.getMessage());
		}

		return buffer.toString();
	}

	public String getPFWAdvanceEmoluments(String emoluments,
			String cpfaccntfund, String reqstAdvnceAmnt, String outStandAmount,
			String advanceType, String purposeType, String purposeOptionType)
			throws InvalidDataException {
		double elgEmoluments = 0.0, elgSubScriptionAmnt = 0.0, elgAmnt = 0.0, finalElAmnt = 0.0, recmndAmnt = 0.0;
		StringBuffer buffer = new StringBuffer();
		String emolumentsLabel = "";
		log.info("getPFWAdvanceEmoluments::advanceType===" + advanceType
				+ "purposeType" + purposeType + "purposeOptionType"
				+ purposeOptionType);
		log.info("-----cpfaccntfund------" + cpfaccntfund);

		log.info("------emoluments-------" + emoluments);
		log.info("------cpfaccntfund-------" + cpfaccntfund);
		log.info("------reqstAdvnceAmnt-------" + reqstAdvnceAmnt);
		log.info("------outStandAmount-------" + outStandAmount);
		log.info("------advanceType-------" + advanceType);
		log.info("------purposeType-------" + purposeType);
		log.info("------purposeOptionType-------" + purposeOptionType);

		try {
			if (advanceType.equals("PFW")) {
				if (purposeType.equals("HBA")
						&& (purposeOptionType.equals("PURCHASESITE")
								|| purposeOptionType.equals("ACQUIREFLAT")
								|| purposeOptionType.equals("PURCHASEHOUSE") || purposeOptionType
								.equals("CONSTRUCTIONHOUSE"))) {
					elgEmoluments = (36 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					log.info("elgAmnt==" + elgAmnt);
					emolumentsLabel = "36 months (Pay +DA)";
				} else if (purposeType.equals("HBA")
						&& (purposeOptionType.equals("RENOVATIONHOUSE"))) {
					elgEmoluments = (12 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					emolumentsLabel = "12 months (Pay +DA)";

				} else if (purposeType.equals("HBA")
						&& (purposeOptionType.equals("REPAYMENTHBA"))) {
					elgEmoluments = (36 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					emolumentsLabel = "36 months (Pay +DA)";
				} else if (purposeType.equals("MARRIAGE")) {

					log.info("--------********----------");
					elgEmoluments = (12 * Double.parseDouble(emoluments));
					log.info("--------***elgEmoluments*****----------"
							+ elgEmoluments);
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					emolumentsLabel = "12 months (Pay +DA)";
				} else if (purposeType.equals("HE")) {
					elgEmoluments = (12 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					emolumentsLabel = "12 months (Pay +DA)";
				}
				else if (purposeType.equals("PANDEMIC")) {
					elgEmoluments = (3 * Double.parseDouble(emoluments));
					log.info("emoluments" + emoluments + "elgEmoluments=="
							+ elgEmoluments);
					elgSubScriptionAmnt = (Double.parseDouble(cpfaccntfund));
					if (elgEmoluments < elgSubScriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = elgSubScriptionAmnt;
					}
					emolumentsLabel = "3 months (Pay +DA)";
				}
				if (elgAmnt < Double.parseDouble(reqstAdvnceAmnt)) {
					finalElAmnt = elgAmnt;
				} else {
					finalElAmnt = Double.parseDouble(reqstAdvnceAmnt);
				}
				recmndAmnt = finalElAmnt - Double.parseDouble(outStandAmount);
			}
			log.info("getCPFAdvanceEmoluments::elgAmnt" + elgAmnt
					+ "recmndAmnt" + recmndAmnt + "elgSubScriptionAmnt"
					+ elgSubScriptionAmnt);
			buffer.append(elgEmoluments);
			buffer.append(",");
			buffer.append(recmndAmnt);
			buffer.append(",");
			buffer.append(elgSubScriptionAmnt);
			buffer.append(",");
			buffer.append(emolumentsLabel);
		} catch (Exception e) {
			throw new InvalidDataException(e.getMessage());
		}

		return buffer.toString();
	}
//	On 10-Apr-2012 for getting display Decimal Points in ReqiredAmnt & Approved Amt
//	On 13-Feb-2012 for getting lastmnthinterest Installment Amount
//On 19-Jan-2012 for getting station ,Paymentinfo values
	public ArrayList getCPFAdvanceForm2Info(String pensionNo,
			String transactionID, String formType, String transDate)
			throws InvalidDataException {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("#########0");
		String transID = "", dateOfBirth = "", pfid = "", prssOutStandAmnt = "0", subscriptionAmnt = "", subscriptionAmntInt = "", findYear = "", aaiContribution = "", aaiContributionAmntInt = "";
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		long cpfFund = 0;
		String interestRate = "", purposeTye = "", sqlQuery = "", loadElgAmnt = "", currentYear = "", lastYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "", advtransDt="", region="", station="";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			log.info("------transDate--*******----" + transDate);
			if (!transDate.equals("")) {
				interestRate = this.getAdvanceInterestRate(transDate);
			}

			sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,PI.REGION AS REGION_PERSNL,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL, PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,PI.EMPLOYEENAME AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.MTHINSTALLMENTAMT AS MTHINSTALLMENTAMT,"
					+ "AF.TRUST AS TRUST,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE,AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,"
					+ "AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.PREVIOUSOUTSTANDINGAMT AS PREVIOUSOUTSTANDINGAMT,AF.INTERESTINSTALLMENTS AS INTERESTINSTALLMENTS,AF.INTERESTINSTALLAMT AS INTERESTINSTALLAMT,AF.RECOMMENDEDAMT AS RECOMMENDEDAMT,AF.VERIFIEDBY AS VERIFIEDBY,AF.NARRATION AS NARRATION, AF.PAYMENTINFO AS PAYMENTINFO,AF.LASTMTHINSTALLMENTAMT AS LASTMTHINSTALLMENTAMT, "
					+ "AF.USERTRNASMNTHEMOLUMENTS AS USERTRNASMNTHEMOLUMENTS,AF.REGION AS REGION,AF.AIRPORTCODE AS  AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
					+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;
			log
					.info("-------getCPFAdvanceForm2Info:sqlQuery-------"
							+ sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransIDDec(rs
							.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}
				if (rs.getString("NARRATION") != null) {
					basicBean.setAuthrizedRemarks(rs.getString("NARRATION"));
				} else {
					basicBean.setAuthrizedRemarks("");
				}
				
				//	Getting Station,Region stored in employee_advances_form from 08-May-2012
				DateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
				if (rs.getString("ADVANCETRANSDT") != null) {
					advtransDt = CommonUtil.converDBToAppFormat(rs
							.getDate("ADVANCETRANSDT"));
					Date transdate = dateformat.parse(advtransDt); 
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
					basicBean.setRegion(region);
				} else {
					basicBean.setRegion("");
				}
				if (station != null) {
					basicBean.setStation(station);
				} else {
					basicBean.setStation("");
				} 
				if (!interestRate.equals("")) {
					basicBean.setInterestRate(interestRate);
				} else {
					basicBean.setInterestRate("");
				}
				if (rs.getString("VERIFIEDBY") != null) {
					basicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {
					basicBean.setVerifiedby("");
				}
				if (rs.getString("RECOMMENDEDAMT") != null) {
					basicBean
							.setAmntRecommended(rs.getString("RECOMMENDEDAMT"));
					basicBean.setAmntRecommendedCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("RECOMMENDEDAMT"))));
				} else {
					basicBean.setAmntRecommended("0.0");
				}

				if (rs.getString("MTHINSTALLMENTAMT") != null) {
					basicBean.setMthinstallmentamt(rs
							.getString("MTHINSTALLMENTAMT"));
				} else {
					basicBean.setMthinstallmentamt("");
				}

				if (rs.getString("INTERESTINSTALLMENTS") != null) {
					basicBean.setInterestinstallments(rs
							.getString("INTERESTINSTALLMENTS"));
				} else {
					basicBean.setInterestinstallments("0");
				}
				if (formType.equals("N")) {
					if (rs.getString("INTERESTINSTALLAMT") != null) {
						basicBean
								.setIntinstallmentamt(commonUtil.getCurrency(rs
										.getDouble("INTERESTINSTALLAMT")));
					} else {
						basicBean.setIntinstallmentamt("0.0");
					}
				} else if (formType.equals("Y")) {
					if (rs.getString("INTERESTINSTALLAMT") != null) {
						basicBean.setIntinstallmentamt(rs
								.getString("INTERESTINSTALLAMT"));
					} else {
						basicBean.setIntinstallmentamt("0.0");
					}
				}
				if (rs.getString("LASTMTHINSTALLMENTAMT") != null) {
					basicBean.setLastmthinstallmentamt(rs
									.getString("LASTMTHINSTALLMENTAMT"));
				} else {
					basicBean.setLastmthinstallmentamt("0.0");
				}
				
				if (rs.getString("PREVIOUSOUTSTANDINGAMT") != null) {
					basicBean.setOutstndamount(rs
							.getString("PREVIOUSOUTSTANDINGAMT"));
					basicBean.setOutstndamountCurr(commonUtil
							.getCurrency(Double.parseDouble(basicBean
									.getOutstndamount())));
				} else {
					basicBean.setOutstndamount("");
				}
				if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
					basicBean.setPrpsecvrdclse(rs
							.getString("PURPOSEOPTIONCVRDCLUSE"));
				} else {
					basicBean.setPrpsecvrdclse("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("EMPLOYEENAME")));
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmpnamewthblk(rs.getString("EMPLOYEENAME")
							.toUpperCase());
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {
					basicBean.setTrust("");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}

				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				basicBean.setPensionNo(pensionNo);

				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("FHNAME")));
				} else {
					basicBean.setFhName("");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					basicBean.setEmoluments("0.0");
				}
				if (rs.getString("USERTRNASMNTHEMOLUMENTS") != null) {
					basicBean.setUserTransMnthEmolunts(rs.getString("USERTRNASMNTHEMOLUMENTS"));
				} else {
					basicBean.setUserTransMnthEmolunts("0.0");
				} 
				log.info("getCPFAdvanceForm2Info::Emolument:::::"
						+ basicBean.getEmoluments());
				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("");
				}
				if (rs.getString("ADVNCERQDDEPEND") != null) {
					basicBean.setAdvncerqddepend(rs
							.getString("ADVNCERQDDEPEND"));
				} else {
					basicBean.setAdvntrnsdt("");
				}
				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					basicBean.setUtlisiedamntdrwn(rs
							.getString("UTLISIEDAMNTDRWN"));
				} else {
					basicBean.setUtlisiedamntdrwn("");
				}
				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				} else {
					basicBean.setPurposeOptionType("");
				}
				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				} else {
					basicBean.setPurposeType("");
				}
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					basicBean.setPaymentinfo("N"); 
				} 
				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setTotalInst(rs.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setTotalInst("0");
				}
				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setAdvnceRequest(rs.getString("REQUIREDAMOUNT"));
					basicBean.setAdvnceRequestCurr(commonUtil
							.getCurrency(Double.parseDouble(basicBean
									.getAdvnceRequest())));
				} else {
					basicBean.setAdvnceRequest("0.0");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");
				log.info("-----noOfYears-----" + noOfYears);

				/*
				 * if (noOfYears > 0) {
				 * basicBean.setDateOfMembership(basicBean.getDateOfJoining()); }
				 * else { basicBean.setDateOfMembership("01-Apr-1995"); }
				 */
				basicBean.setDateOfMembership(basicBean.getDateOfJoining());
				/*toDate = commonUtil.getCurrentDate("MM-yyyy");
				String currentDtInfo[] = toDate.split("-");
				currentMonth = Integer.toString(Integer
						.parseInt(currentDtInfo[0]) - 1);
				currentYear = currentDtInfo[1];
				lastYear = Integer
						.toString(Integer.parseInt(currentDtInfo[1]) - 1);
				toDate = "";
				try {
					toDate = commonUtil.converDBToAppFormat(currentMonth + "-"
							+ currentYear, "MM-yyyy", "MMM-yyyy");
					toDate = "01-" + toDate;
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// fromDate="01-Apr-"+lastYear;
				fromDate = "01-Apr-2008";
				toDate = "28-Feb-2009";
				String pensionInfo = financeReport.getPensionInfo(pensionNo,
						basicBean.getEmployeeName(), fromDate, toDate);*/
				String pensionInfo=getPFCardClosingBalance(pensionNo,basicBean.getEmployeeName());
				log.info("--------pensionInfo---------" + pensionInfo);

				String[] pensionArray = pensionInfo.split(",");
				subscriptionAmnt = pensionArray[2];
				subscriptionAmntInt = pensionArray[3];
				aaiContribution = pensionArray[4];
				aaiContributionAmntInt = pensionArray[5];
				log.info("subscriptionAmnt" + subscriptionAmnt
						+ "subscriptionAmntInt" + subscriptionAmntInt);
				log.info("aaiContribution" + aaiContribution
						+ "aaiContributionAmntInt" + aaiContributionAmntInt);

				log.info("getCPFAdvanceForm2Info===========" + subscriptionAmnt
						+ "formType====" + formType);
				if (formType.equals("Y")) {
					if (basicBean.getAdvanceType().equals("CPF")) {
						try {
							loadElgAmnt = this.getCPFAdvanceEmoluments(
									basicBean.getEmoluments(),
									subscriptionAmnt, basicBean
											.getAdvnceRequest(),
									prssOutStandAmnt, basicBean
											.getAdvanceType(), basicBean
											.getPurposeType(), basicBean
											.getPurposeOptionType());
						} catch (InvalidDataException e) {
							throw e;
						}
					} else {
						try {
							loadElgAmnt = this.getPFWAdvanceEmoluments(
									basicBean.getEmoluments(),
									subscriptionAmnt, basicBean
											.getAdvnceRequest(),
									prssOutStandAmnt, basicBean
											.getAdvanceType(), basicBean
											.getPurposeType(), basicBean
											.getPurposeOptionType());
						} catch (InvalidDataException e) {
							throw e;
						}
					}

					String[] lodElgblAmnt = loadElgAmnt.split(",");
					basicBean.setMnthsemoluments(lodElgblAmnt[0]);

					if (rs.getString("RECOMMENDEDAMT") != null) {
						log.info("----------in if--------");
						basicBean.setAmntRecommended(rs
								.getString("RECOMMENDEDAMT"));
					} else {
						// basicBean.setAmntRecommended("0.0");
						log.info("----------in else--------");
						basicBean.setAmntRecommended(lodElgblAmnt[1]);
					}

					if (rs.getString("SUBSCRIPTIONAMNT") != null) {

						basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
					} else {

						basicBean.setEmpshare(lodElgblAmnt[2]);
					}

					if (!basicBean.getAdvanceType().equals("CPF")) {
						cpfFund = Long.parseLong(df.format(Math.round(Double
								.parseDouble(lodElgblAmnt[2]))))
								+ Long.parseLong(df.format(Math.round(Double
										.parseDouble(aaiContribution))));
						basicBean.setCPFFund(Long.toString(cpfFund));

						if (basicBean.getPurposeType().equals("HE")
								|| basicBean.getPurposeType()
										.equals("MARRIAGE")) {
							long chckElgble = Long.parseLong(df
									.format(Math.round(Double
											.parseDouble(lodElgblAmnt[2]))))
									- Long
											.parseLong(df
													.format(Math
															.round(Double
																	.parseDouble(lodElgblAmnt[1]))));
							if (chckElgble < 1000) {
								basicBean
										.setAuthrizedRemarks("CPF Account should be maintain Rs.1000");
							}
						}

						basicBean.setAmntRecommendedDscr(commonUtil
								.ConvertInWords(Double
										.parseDouble(lodElgblAmnt[1])));
					}
					log.info("subscriptionAmnt" + subscriptionAmnt
							+ "aaiContribution" + aaiContribution + "pensionNo"
							+ pensionNo);
					basicBean.setSubscriptionAmt(subscriptionAmnt);
					basicBean.setContributionAmt(aaiContribution);
				} else {
					if (rs.getString("SUBSCRIPTIONAMNT") != null) {
						basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
						basicBean
								.setEmpshareCurr(commonUtil
										.getDecimalCurrency(Double.parseDouble(rs
												.getString("SUBSCRIPTIONAMNT"))));
					} else {
						basicBean.setEmpshare("0.00");
					}
					if (rs.getString("APPROVEDAMNT") != null) {
						basicBean.setAmntAproved(rs.getString("APPROVEDAMNT"));
						basicBean.setAmntAprovedCurr(commonUtil
								.getCurrency(Double.parseDouble(rs
										.getString("APPROVEDAMNT"))));
						basicBean.setAmntRecommendedWords(commonUtil
								.ConvertInWords(Double.parseDouble(rs
										.getString("APPROVEDAMNT"))));
					} else {
						basicBean.setAmntAproved("0.00");
					}
					if (rs.getString("EMOLUMENTS") != null) {
						if (basicBean.getPurposeType().equals("OBMARRIAGE")) {
							basicBean.setMnthsemoluments(Double
									.toString(Double.parseDouble(rs
											.getString("EMOLUMENTS")) * 6));
						} else {
							basicBean.setMnthsemoluments(Double
									.toString(Double.parseDouble(rs
											.getString("EMOLUMENTS")) * 3));
						}
						basicBean.setMnthsemolumentsCurr(commonUtil
								.getCurrency(Double.parseDouble(basicBean
										.getMnthsemoluments())));
					} else {
						basicBean.setMnthsemoluments("0.00");
						;
					}
					basicBean.setAmntRecommendedDscr(commonUtil
							.ConvertInWords(Double.parseDouble(basicBean
									.getAmntAproved())));
				}

				bankMasterBean = loadEmployeeBankInfo(pensionNo);
				reportList.add(basicBean);
				reportList.add(bankMasterBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;

	}

	public ArrayList getPFWAdvanceForm3Info(String pensionNo,
			String transactionID, String frmName) throws InvalidDataException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("#########0");
		String transID = "", dateOfBirth = "", pfid = "", prssOutStandAmnt = "0", subscriptionAmnt = "", subscriptionAmntInt = "", findYear = "", aaiContribution = "", aaiContributionAmntInt = "";
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		long cpfFund = 0;
		String purposeTye = "", sqlQuery = "", approvedAmt = "", loadElgAmnt = "", currentYear = "", lastYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			if (frmName.equals("Form-4Report")) {
				sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,initCap(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.PAYMENTINFO AS PAYMENTINFO,AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.VERIFIEDBY AS VERIFIEDBY,"
						+ "AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE,(CASE WHEN (lower(AF.PURPOSETYPE) = 'hba' or   lower(AF.PURPOSETYPE) = 'superannuation' or   lower(AF.PURPOSETYPE) = 'pandemic' or   lower(AF.PURPOSETYPE) = 'he') THEN    'Y'  ELSE 'N'   END) AS purposeflag, AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.NTIMESTRNASMNTHEMOLUMENTS AS NTIMESEMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.CPFACCFUND AS CPFACCFUND,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.Firstinstallmentsubamt as Firstinstallmentsubamt,AF.Firstinstallmentcontriamt as Firstinstallmentcontriamt  "
						+ "FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
						+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;
			} else {
				sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,initCap(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.PAYMENTINFO AS PAYMENTINFO,AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.VERIFIEDBY AS VERIFIEDBY,"
						+ "AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE , (CASE WHEN (lower(AF.PURPOSETYPE) = 'hba' or   lower(AF.PURPOSETYPE) = 'superannuation' or   lower(AF.PURPOSETYPE) = 'pandemic' or   lower(AF.PURPOSETYPE) = 'he') THEN    'Y'  ELSE 'N'   END) AS purposeflag,  AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.NTIMESTRNASMNTHEMOLUMENTS AS NTIMESEMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.CPFACCFUND AS CPFACCFUND,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.Firstinstallmentsubamt as Firstinstallmentsubamt,AF.Firstinstallmentcontriamt as Firstinstallmentcontriamt  "
						+ "FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
						+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

			}

			log
					.info("-------getPFWAdvanceForm3Info:sqlQuery-------"
							+ sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransIDDec(rs
							.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					basicBean.setPaymentinfo("");
				}
				if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
					basicBean.setPrpsecvrdclse(rs
							.getString("PURPOSEOPTIONCVRDCLUSE"));
				} else {
					basicBean.setPrpsecvrdclse("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}

				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				basicBean.setPensionNo(pensionNo);

				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("FHNAME")));
				} else {
					basicBean.setFhName("");
				}

				if (rs.getString("APPROVEDSUBSCRIPTIONAMT") != null) {
					basicBean.setApprovedsubamt(rs
							.getString("APPROVEDSUBSCRIPTIONAMT"));
					basicBean.setApprovedsubamtcurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("APPROVEDSUBSCRIPTIONAMT")));
				} else {
					basicBean.setApprovedsubamt("");
					basicBean.setApprovedcontamtcurr("0");

				}

				if (rs.getString("APPROVEDCONTRIBUTIONAMT") != null) {
					basicBean.setApprovedconamt(rs
							.getString("APPROVEDCONTRIBUTIONAMT"));
					basicBean.setApprovedcontamtcurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("APPROVEDCONTRIBUTIONAMT")));
				} else {
					basicBean.setApprovedconamt("");
					basicBean.setApprovedcontamtcurr("0");

				}

				if (rs.getString("VERIFIEDBY") != null) {
					basicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {
					basicBean.setVerifiedby("");
				}

				/*
				 * if (rs.getString("EMOLUMENTS") != null) {
				 * basicBean.setEmoluments(rs.getString("EMOLUMENTS")); } else {
				 * basicBean.setEmoluments("0.0"); }
				 */
				/*
				 * if (rs.getString("EMOLUMENTS") != null) {
				 * basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
				 * basicBean.setEmolumentsCurr(commonUtil.getDecimalCurrency(rs.getDouble("EMOLUMENTS"))); }
				 * else { basicBean.setEmoluments("0.0"); }
				 */
				log.info("getCPFAdvanceForm2Info::Emolument:::::"
						+ basicBean.getEmoluments());
				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("");
				}
				if (rs.getString("ADVNCERQDDEPEND") != null) {
					basicBean.setAdvncerqddepend(rs
							.getString("ADVNCERQDDEPEND"));
				} else {
					basicBean.setAdvntrnsdt("");
				}
				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					basicBean.setUtlisiedamntdrwn(rs
							.getString("UTLISIEDAMNTDRWN"));
				} else {
					basicBean.setUtlisiedamntdrwn("");
				}
				if (rs.getString("PURPOSEOPTIONTYPE") != null) {

					if (rs.getString("PURPOSETYPE").equals("HBA")) {
						if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"PURCHASESITE")) {
							basicBean.setPurposeOptionTypeDesr("Purchase Site");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"PURCHASEHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Purchase House");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"CONSTRUCTIONOFCOMPOUNDWALL")) {
							basicBean
									.setPurposeOptionTypeDesr("Construction  Of Compound Wall");
						}else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"CONSTRUCTIONHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Construction House");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"ACQUIREFLAT")) {
							basicBean.setPurposeOptionTypeDesr("Acquire Flat");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"RENOVATIONHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Renovation House");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"REPAYMENTHBA")) {
							basicBean.setPurposeOptionTypeDesr("Repayment HBA");
						} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
								"HBAOTHERS")) {
							basicBean.setPurposeOptionTypeDesr("HBA Others");
						}
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						basicBean.setPurposeOptionTypeDesr("Higher Education");
					} else {
						basicBean.setPurposeOptionTypeDesr(commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE")));
					}

					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				} else {
					basicBean.setPurposeOptionType("");
				}

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				} else {
					basicBean.setPurposeType("");
				}
				if (rs.getString("purposeflag") != null) {
					basicBean.setPurposeFlag(rs.getString("purposeflag"));
				} else {
					basicBean.setPurposeFlag("N");
				} 
				
				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setTotalInst(rs.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setTotalInst("0");
				}
				if (rs.getString("NTIMESEMOLUMENTS") != null) {
					basicBean.setNtimesemoluments(rs
							.getString("NTIMESEMOLUMENTS"));
					basicBean.setNtimesemolumentscurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("NTIMESEMOLUMENTS")));

					basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
					basicBean.setEmolumentsCurr(commonUtil
							.getDecimalCurrency(rs.getDouble("EMOLUMENTS")));

				} else {
					if (rs.getString("EMOLUMENTS") != null) {
						basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
						basicBean
								.setEmolumentsCurr(commonUtil
										.getDecimalCurrency(rs
												.getDouble("EMOLUMENTS")));
					} else {
						basicBean.setEmoluments("0.0");
					}

				}
				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setAdvnceRequest(rs.getString("REQUIREDAMOUNT"));
					basicBean
							.setAdvnceRequestCurr(commonUtil
									.getDecimalCurrency(rs
											.getDouble("REQUIREDAMOUNT")));

				} else {
					basicBean.setAdvnceRequest("0.0");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				pfid = commonDAO.getPFID(rs.getString("EMPLOYEENAME"),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");
				log.info("-----noOfYears-----" + noOfYears);

				/*
				 * if (noOfYears > 0) {
				 * basicBean.setDateOfMembership(basicBean.getDateOfJoining()); }
				 * else { basicBean.setDateOfMembership("01-Apr-1995"); }
				 */
				if (rs.getString("PLACEOFPOSTING") != null) {
					basicBean.setPlaceofposting(rs.getString("PLACEOFPOSTING"));
				} else {
					basicBean.setPlaceofposting("");
				}
				basicBean.setDateOfMembership(basicBean.getDateOfJoining());
				toDate = commonUtil.getCurrentDate("MM-yyyy");
				String currentDtInfo[] = toDate.split("-");
				currentMonth = Integer.toString(Integer
						.parseInt(currentDtInfo[0]) - 1);
				currentYear = currentDtInfo[1];
				lastYear = Integer
						.toString(Integer.parseInt(currentDtInfo[1]) - 1);
				toDate = "";
				try {
					toDate = commonUtil.converDBToAppFormat(currentMonth + "-"
							+ currentYear, "MM-yyyy", "MMM-yyyy");
					toDate = "01-" + toDate;
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * fromDate="01-Apr-"+lastYear; toDate="31-Mar-2010";
				 */
				fromDate = "01-Apr-2008";
				toDate = "31-Mar-2009";
				String pensionInfo = financeReport.getPensionInfo(pensionNo,
						basicBean.getEmployeeName(), fromDate, toDate);
				/*
				 * String pensionInfo = financeReport.getPensionInfo(pensionNo,
				 * "2007");
				 */
				String[] pensionArray = pensionInfo.split(",");
				subscriptionAmnt = pensionArray[2];
				subscriptionAmntInt = pensionArray[3];
				aaiContribution = pensionArray[4];
				aaiContributionAmntInt = pensionArray[5];
				log.info("subscriptionAmnt" + subscriptionAmnt
						+ "subscriptionAmntInt" + subscriptionAmntInt);
				log.info("aaiContribution" + aaiContribution
						+ "aaiContributionAmntInt" + aaiContributionAmntInt);

				if (!subscriptionAmnt.equals("")
						&& !subscriptionAmntInt.equals("")) {
					subscriptionAmnt = Double.toString(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt));
				} else if (!subscriptionAmnt.equals("")
						&& !subscriptionAmntInt.equals("")
						&& !aaiContribution.equals("")
						&& basicBean.getAdvanceType().equals("PFW")
						&& (basicBean.getPurposeType().equals("HBA") ||basicBean.getPurposeType().equals("PANDEMIC"))) {
					subscriptionAmnt = Double.toString(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt)
							+ Double.parseDouble(aaiContribution));
				} else {

					subscriptionAmnt = Double.toString(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt)
							- Double.parseDouble("1000"));
				}

				log.info("getPFWAdvanceForm3Info===========" + subscriptionAmnt
						+ "frmName" + frmName);
				/*
				 * if(frmName.equals("PFWForm3") &&
				 * (rs.getString("VERIFIEDBY").equals("PERSONNEL"))){ try {
				 * loadElgAmnt = this.getPFWAdvanceEmoluments(basicBean
				 * .getEmoluments(), subscriptionAmnt, basicBean
				 * .getAdvnceRequest(), prssOutStandAmnt,
				 * basicBean.getAdvanceType(), basicBean .getPurposeType(),
				 * basicBean .getPurposeOptionType()); } catch
				 * (InvalidDataException e) { throw e; }
				 * 
				 * 
				 * String[] lodElgblAmnt = loadElgAmnt.split(",");
				 * basicBean.setMnthsemoluments(lodElgblAmnt[0]);
				 * basicBean.setAmntRecommended(lodElgblAmnt[1]);
				 * basicBean.setEmpshare(lodElgblAmnt[2]);
				 * if(!basicBean.getAdvanceType().equals("CPF")){
				 * cpfFund=Long.parseLong(df.format(Math.round(Double.parseDouble(lodElgblAmnt[2]))))+Long.parseLong(df.format(Math.round(Double.parseDouble(aaiContribution))));
				 * basicBean.setCPFFund(Long.toString(cpfFund));
				 * 
				 * if(basicBean.getPurposeType().equals("HE") ||
				 * basicBean.getPurposeType().equals("MARRIAGE")){ long
				 * chckElgble=Long.parseLong(df.format(Math.round(Double.parseDouble(lodElgblAmnt[2]))))-Long.parseLong(df.format(Math.round(Double.parseDouble(lodElgblAmnt[1]))));
				 * if(chckElgble<1000){ basicBean.setAuthrizedRemarks("CPF
				 * Account should be maintain Rs.1000"); } }
				 * 
				 * basicBean.setAmntRecommendedDscr(commonUtil.ConvertInWords(Double.parseDouble(lodElgblAmnt[1]))); }
				 * 
				 * basicBean.setSubscriptionAmt(subscriptionAmnt);
				 * basicBean.setContributionAmt(aaiContribution);
				 * basicBean.setAmntRecommendedDscr(commonUtil.ConvertInWords(Double.parseDouble(lodElgblAmnt[1])));
				 * basicBean.setEmolumentsLabel(lodElgblAmnt[lodElgblAmnt.length-1]);
				 * }else{ if(rs.getString("SUBSCRIPTIONAMNT")!=null){
				 * basicBean.setSubscriptionAmt(rs.getString("SUBSCRIPTIONAMNT"));
				 * basicBean.setSubscriptionAmtCurr(commonUtil.getDecimalCurrency(Double.parseDouble(rs.getString("SUBSCRIPTIONAMNT"))));
				 * basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
				 * basicBean.setEmpshareCurr(commonUtil.getDecimalCurrency(Double.parseDouble(rs.getString("SUBSCRIPTIONAMNT"))));
				 * }else{ basicBean.setSubscriptionAmt("0.00");
				 * basicBean.setEmpshare("0.00");
				 * basicBean.setEmpshareCurr("0.00");
				 * basicBean.setSubscriptionAmtCurr("0.00"); }
				 * if(rs.getString("CONTRIBUTIONAMOUNT")!=null){
				 * basicBean.setContributionAmtCurr(commonUtil.getDecimalCurrency(Double.parseDouble(rs.getString("CONTRIBUTIONAMOUNT"))));
				 * basicBean.setContributionAmt(rs.getString("CONTRIBUTIONAMOUNT"));
				 * }else{ basicBean.setContributionAmt("0.00");
				 * basicBean.setContributionAmtCurr("0.00"); }
				 * if(rs.getString("NTIMESEMOLUMENTS")!=null){
				 * basicBean.setMnthsemoluments(rs.getString("NTIMESEMOLUMENTS"));
				 * basicBean.setMnthsemolumentsCurr(commonUtil.getDecimalCurrency(Double.parseDouble(rs.getString("NTIMESEMOLUMENTS"))));
				 * loadElgAmnt
				 * =this.getPFWAdvanceEmoluments(rs.getString("EMOLUMENTS"),"0.00","0.00","0.00",basicBean.getAdvanceType(),basicBean.getPurposeType(),basicBean.getPurposeOptionType());
				 * String[] lodElgblForm4Amnt = loadElgAmnt.split(",");
				 * basicBean.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length-1]);
				 * }else{ if (rs.getString("EMOLUMENTS") != null) { loadElgAmnt
				 * =this.getPFWAdvanceEmoluments(rs.getString("EMOLUMENTS"),"0.00","0.00","0.00",basicBean.getAdvanceType(),basicBean.getPurposeType(),basicBean.getPurposeOptionType());
				 * String[] lodElgblForm4Amnt = loadElgAmnt.split(",");
				 * log.info("frmName========================"+frmName);
				 * if(frmName.equals("Form-4Report")){
				 * basicBean.setMnthsemoluments(rs.getString("EMOLUMENTS"));
				 * }else{ basicBean.setMnthsemoluments(lodElgblForm4Amnt[0]); }
				 * 
				 * basicBean.setMnthsemolumentsCurr(commonUtil.getDecimalCurrency(Double.parseDouble(basicBean.getMnthsemoluments())));
				 * basicBean.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length-1]); }
				 * else { basicBean.setMnthsemoluments("0.0");
				 * basicBean.setMnthsemolumentsCurr("0.0"); } }
				 * 
				 * 
				 * if(rs.getString("CPFACCFUND")!=null){
				 * basicBean.setCPFFund(rs.getString("CPFACCFUND"));
				 * basicBean.setCPFFundCurr((commonUtil.getDecimalCurrency(Double.parseDouble(rs.getString("CPFACCFUND")))));
				 * }else{ basicBean.setCPFFund("0.00");
				 * basicBean.setCPFFundCurr("0.00"); }
				 * if(rs.getString("APPROVEDAMNT")!=null){
				 * approvedAmt=rs.getString("APPROVEDAMNT");
				 * basicBean.setAmntRecommended(approvedAmt);
				 * basicBean.setAmntRecommendedCurr(commonUtil.getDecimalCurrency(rs.getDouble("APPROVEDAMNT")));
				 * }else{ approvedAmt="0.00";
				 * basicBean.setAmntRecommended("0.00");
				 * basicBean.setAmntRecommendedCurr("0.00"); }
				 * basicBean.setAmntRecommendedDscr(commonUtil.ConvertInWords(Double.parseDouble(approvedAmt))); }
				 */

				if (rs.getString("SUBSCRIPTIONAMNT") != null) {
					basicBean.setSubscriptionAmt(rs
							.getString("SUBSCRIPTIONAMNT"));
					basicBean.setSubscriptionAmtCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("SUBSCRIPTIONAMNT"))));
					basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
					basicBean.setEmpshareCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("SUBSCRIPTIONAMNT"))));
				} else {
					basicBean.setSubscriptionAmt("0.00");
					basicBean.setEmpshare("0.00");
					basicBean.setEmpshareCurr("0.00");
					basicBean.setSubscriptionAmtCurr("0.00");
				}
				if (rs.getString("CONTRIBUTIONAMOUNT") != null) {
					basicBean.setContributionAmtCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("CONTRIBUTIONAMOUNT"))));
					basicBean.setContributionAmt(rs
							.getString("CONTRIBUTIONAMOUNT"));
				} else {
					basicBean.setContributionAmt("0.00");
					basicBean.setContributionAmtCurr("0.00");
				}
				if (rs.getString("NTIMESEMOLUMENTS") != null) {
					basicBean.setMnthsemoluments(rs
							.getString("NTIMESEMOLUMENTS"));
					basicBean.setMnthsemolumentsCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("NTIMESEMOLUMENTS"))));
					loadElgAmnt = this.getPFWAdvanceEmoluments(rs
							.getString("EMOLUMENTS"), "0.00", "0.00", "0.00",
							basicBean.getAdvanceType(), basicBean
									.getPurposeType(), basicBean
									.getPurposeOptionType());
					String[] lodElgblForm4Amnt = loadElgAmnt.split(",");
					basicBean
							.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length - 1]);
					basicBean.setMnthsemoluments(lodElgblForm4Amnt[0]);
				} else {
					if (rs.getString("EMOLUMENTS") != null) {
						loadElgAmnt = this.getPFWAdvanceEmoluments(rs
								.getString("EMOLUMENTS"), "0.00", "0.00",
								"0.00", basicBean.getAdvanceType(), basicBean
										.getPurposeType(), basicBean
										.getPurposeOptionType());
						String[] lodElgblForm4Amnt = loadElgAmnt.split(",");
						log.info("frmName========================" + frmName);
						if (frmName.equals("Form-4Report")) {
							basicBean.setMnthsemoluments(rs
									.getString("EMOLUMENTS"));
						} else {
							basicBean.setMnthsemoluments(lodElgblForm4Amnt[0]);
						}

						basicBean.setMnthsemolumentsCurr(commonUtil
								.getDecimalCurrency(Double
										.parseDouble(basicBean
												.getMnthsemoluments())));
						basicBean
								.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length - 1]);
					} else {
						basicBean.setMnthsemoluments("0.0");
						basicBean.setMnthsemolumentsCurr("0.0");
					}
				}

				if (rs.getString("CPFACCFUND") != null) {
					basicBean.setCPFFund(rs.getString("CPFACCFUND"));
					basicBean.setCPFFundCurr((commonUtil
							.getDecimalCurrency(Double.parseDouble(rs
									.getString("CPFACCFUND")))));
				} else {
					basicBean.setCPFFund("0.00");
					basicBean.setCPFFundCurr("0.00");
				}
				if (rs.getString("APPROVEDAMNT") != null) {
					approvedAmt = rs.getString("APPROVEDAMNT");
					basicBean.setAmntRecommended(approvedAmt);
					basicBean.setAmntRecommendedCurr(commonUtil
							.getDecimalCurrency(rs.getDouble("APPROVEDAMNT")));
				} else {
					approvedAmt = "0.00";
					basicBean.setAmntRecommended("0.00");
					basicBean.setAmntRecommendedCurr("0.00");
				}
				if(rs.getString("Firstinstallmentsubamt") != null){
					basicBean.setFirstInsSubAmnt(rs.getString("Firstinstallmentsubamt"));
					basicBean.setFirstInsSubAmntCurr(commonUtil
							.getDecimalCurrency(rs.getDouble("Firstinstallmentsubamt")));
				}else{
					basicBean.setFirstInsSubAmnt("0.00");
				}
				if(rs.getString("Firstinstallmentcontriamt") !=null){
					basicBean.setFirstInsConrtiAmnt(rs.getString("Firstinstallmentcontriamt"));
					basicBean.setFirstInsConrtiAmntCurr(commonUtil
							.getDecimalCurrency(rs.getDouble("Firstinstallmentcontriamt")));
				}else{
					basicBean.setFirstInsConrtiAmnt("0.00");
				}
				basicBean.setAmntRecommendedDscr(commonUtil
						.ConvertInWords(Double.parseDouble(approvedAmt)));

				log.info("subscriptionAmnt" + subscriptionAmnt
						+ "aaiContribution" + aaiContribution + "pensionNo"
						+ pensionNo);
				log
						.info("pensionNo" + pensionNo
								+ "Recomeded subscriptionAmnt"
								+ basicBean.getApprovedsubamt()
								+ "aaiContribution"
								+ basicBean.getApprovedconamt()
								+ "Approved Amount"
								+ basicBean.getAmntRecommended() + "Frm Name"
								+ frmName+basicBean.getFirstInsSubAmnt()+basicBean.getFirstInsConrtiAmnt()+"basicBean"+basicBean.getEmoluments());

				/*
				 * To display epis_advances_transactions table values in PFW
				 * Check List,PFW Form-III Verification,PFW Form-IV
				 * Verification, PFW Final Approval Reports.
				 * 
				 * To load epis_advances_transactions table data in Approval
				 * Screens also remove the if block.
				 * 
				 */

				if (frmName.equals("PFWCheckListReport")
						|| frmName.equals("PFWForm3Report")
						|| frmName.equals("PFWForm4VerificationReport")
						|| frmName.equals("Form-4Report")) {
					basicBean = this.getAdvanceTransactionDetails(basicBean,
							frmName, pensionNo);
				}

				reportList.add(basicBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	public String getApprovedRemarks(String frmName, String approvedRemarks) {

		String remark = "";

		if (!approvedRemarks.equals("")) {

			String[] remarkArr = approvedRemarks.split(",");

			for (int i = 0; i < remarkArr.length; i++) {
				log.info("======Elements  ********====="
						+ remarkArr[i].split(":"));
				String[] arr2 = remarkArr[i].split(":");

				if (arr2[1].equals("PFWForm3")) {
					remark = arr2[0];
				} else if (arr2[1].equals("PFWForm3 Report")) {
					remark = arr2[0];
				} else {
					remark = arr2[0];
				}
			}

		}

		return remark;

	}

	// ----------------------------------NoteSheet Master Added by Suneetha V on
	// 16/10/2009--------------------------------------------------
	public String getNoteSheetSequence(Connection con) {
		String nsSeqVal = "";
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "SELECT NOTESHEET_SEQ.NEXTVAL AS  NSSANCTIONNO FROM DUAL";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				nsSeqVal = rs.getString("NSSANCTIONNO");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return nsSeqVal;
	}

	/*
	 * public String addNoteSheet(AdvanceBasicBean advanceBean, EmpBankMaster
	 * bankBean) { log.info(advanceBean.getLodInfo()); Connection con = null;
	 * Statement st = null; Statement insertSt = null; int insertedRecords = 0;
	 * String nsSanctionNo = "", message = "", insertQuery = "", paymentdt = "";
	 * String seperationdt = "", sanctiondt = "", amtadmtdt = "";
	 * log.info("Basic Info" + advanceBean.getPensionNo()); log.info("====Course
	 * Duration in DAO======" + advanceBean.getCurseDuration());
	 * 
	 * String deleteStatus = "", nomineeName = "", nomineeAddress = "",
	 * nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare =
	 * "", gaurdianAddress = "", nomineeRows = ""; String flag = "";
	 * 
	 * String tempInfo[] = null; String tempData = "", sql2 = ""; int slno = 0;
	 * try { con = commonDB.getConnection(); st = con.createStatement();
	 * insertSt = con.createStatement(); nsSanctionNo =
	 * this.getNoteSheetSequence(con);
	 * 
	 * if (!advanceBean.getSeperationdate().trim().equals("")) { seperationdt =
	 * commonUtil.converDBToAppFormat(advanceBean .getSeperationdate(),
	 * "dd/MM/yyyy", "dd-MMM-yyyy"); } if
	 * (!advanceBean.getPaymentdt().trim().equals("")) { paymentdt =
	 * commonUtil.converDBToAppFormat(advanceBean .getPaymentdt(), "dd/MM/yyyy",
	 * "dd-MMM-yyyy"); } if (!advanceBean.getSanctiondt().trim().equals("")) {
	 * sanctiondt = commonUtil.converDBToAppFormat(advanceBean .getSanctiondt(),
	 * "dd/MM/yyyy", "dd-MMM-yyyy"); } if
	 * (!advanceBean.getAmtadmtdate().trim().equals("")) { amtadmtdt =
	 * commonUtil.converDBToAppFormat(advanceBean .getAmtadmtdate(),
	 * "dd/MM/yyyy", "dd-MMM-yyyy"); }
	 * 
	 * nomineeRows = advanceBean.getNomineeRow();
	 * 
	 * log.info("......nomineeRows......." + nomineeRows);
	 * 
	 * ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
	 * 
	 * for (int j = 0; j < nomineeList.size(); j++) {
	 * 
	 * tempData = nomineeList.get(j).toString(); tempInfo = tempData.split("@");
	 * nomineeName = tempInfo[0];
	 * System.out.println("tempInfo(updatePensionMaster)" + tempInfo); if
	 * (!tempInfo[1].equals("XXX")) { nomineeAddress = tempInfo[1]; } else {
	 * nomineeAddress = ""; } if (!tempInfo[2].equals("XXX")) { nomineeDob =
	 * tempInfo[2].toString().trim(); } else { nomineeDob = ""; }
	 * 
	 * if (!tempInfo[3].equals("XXX")) { nomineeRelation = tempInfo[3]; } else {
	 * nomineeRelation = ""; } if (!tempInfo[4].equals("XXX")) { nameofGuardian =
	 * tempInfo[4]; } else { nameofGuardian = ""; } if
	 * (!tempInfo[5].equals("XXX")) { gaurdianAddress = tempInfo[5]; } else {
	 * gaurdianAddress = ""; } if (!tempInfo[6].equals("XXX")) { totalShare =
	 * tempInfo[6]; } else { totalShare = ""; } if (!tempInfo[7].equals("")) {
	 * slno = Integer.parseInt(tempInfo[7]); } else {
	 *  } if (!tempInfo[8].equals("")) { flag = tempInfo[8]; } else {
	 *  } if (!tempInfo[9].equals("")) { deleteStatus = tempInfo[9]; } else {
	 *  } if (flag.equals("Y")) { if (deleteStatus.equals("D")) { sql2 = "update
	 * employee_nominee_dtls set EMPFLAG='N' where srno=" + slno + " and
	 * pensionno='" + advanceBean.getPensionNo() + "'"; } else { sql2 = "update
	 * employee_nominee_dtls set nomineeName='" + nomineeName +
	 * "',nomineeAddress='" + nomineeAddress + "',nomineeDob='" +
	 * commonUtil.converDBToAppFormat(nomineeDob, "dd/MM/yyyy", "dd-MMM-yyyy") +
	 * "',nomineeRelation='" + nomineeRelation + "',nameofGuardian='" +
	 * nameofGuardian + "',totalshare='" + totalShare + "',GAURDIANADDRESS='" +
	 * gaurdianAddress + "' where srno=" + slno + " and pensionno='" +
	 * advanceBean.getPensionNo() + "'"; } } else {
	 * 
	 * sql2 = "insert into
	 * employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,pensionno)values(" +
	 * slno + ",'" + nomineeName + "','" + nomineeAddress + "','" +
	 * commonUtil.converDBToAppFormat(nomineeDob, "dd/MM/yyyy", "dd-MMM-yyyy") +
	 * "','" + nomineeRelation + "','" + nameofGuardian + "','" +
	 * gaurdianAddress + "','" + totalShare + "','" + advanceBean.getPensionNo() +
	 * "')"; } log.info("----sql2-----" + sql2); st.executeUpdate(sql2); } if
	 * ((!advanceBean.getDepartment().equals("")) ||
	 * (!advanceBean.getDesignation().equals(""))) { String updateQry = "update
	 * employee_personal_info set DESEGNATION='" + advanceBean.getDesignation() +
	 * "', DEPARTMENT='" + advanceBean.getDepartment() + "', AIRPORTCODE='" +
	 * advanceBean.getStation() + "',REGION='" + advanceBean.getRegion() + "'
	 * where PENSIONNO='" + advanceBean.getPensionNo() + "'";
	 * log.info("==========update Query===========" + updateQry); int
	 * updatedRecord = st.executeUpdate(updateQry); } insertQuery = "INSERT INTO
	 * EMPLOYEE_ADVANCE_NOTEPARAM(NSSANCTIONNO,PENSIONNO,SEPERATIONDT,SEPERATIONRESAON,EMPSHARESUBSCRIPITION,EMPSHARECONTRIBUTION,LESSCONTRIBUTION,NETCONTRIBUTION,ADHOCAMOUNT,NSSANCTIONEDDT,AMTADMITTEDDT,PAYMENTDT,SEPERATIONFAVOUR,SEPERATIONREMARKS,REMARKS,TRUST)
	 * VALUES(" + Long.parseLong(nsSanctionNo) + ",'" +
	 * advanceBean.getPensionNo() + "','" + seperationdt + "','" +
	 * advanceBean.getSeperationreason() + "','" + advanceBean.getEmplshare() +
	 * "','" + advanceBean.getEmplrshare() + "','" +
	 * advanceBean.getPensioncontribution() + "','" +
	 * advanceBean.getNetcontribution() + "','" + advanceBean.getAdhocamt() +
	 * "','" + sanctiondt + "','" + amtadmtdt + "','" + paymentdt + "','" +
	 * advanceBean.getSeperationfavour() + "','" +
	 * commonUtil.escapeSingleQuotes(advanceBean .getSeperationremarks()) +
	 * "','" + advanceBean.getRemarks() + "','" + advanceBean.getTrust() + "')";
	 * log.info("insertQuery " + insertQuery); insertedRecords =
	 * st.executeUpdate(insertQuery);
	 * 
	 * log.info("--------insertedRecords-------" + insertedRecords);
	 *  } catch (SQLException e) { log.printStackTrace(e); } catch (Exception e) {
	 * log.printStackTrace(e); } finally { commonDB.closeConnection(null, st,
	 * con); } return message; }
	 */

	public int checkNomineeDetails(int slno, String pensionno) {

		log.info("checkNomineeDetails() entering method ");
		String query = "";
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		int i = 0;
		query = "select count(*) from EMPLOYEE_NOMINEE_DTLS where EMPFLAG='Y' and SRNO="
				+ slno + " and PENSIONNO='" + pensionno + "'";

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
		log.info("checkNomineeDetails() leaving method");
		return i;
	}

	public String buildSearchQueryForNoteSheet(AdvanceSearchBean searchBean,
			String unitName) {

		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForNoteSheet-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "";
		sqlQuery = "SELECT EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.FINALSETTLMENTDT AS FINALSETTLMENTDT,EMPFID.RESETTLEMENTDATE AS RESETTLEMENTDATE, NVL(EN.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,EMPFID.PENSIONNO,EMPFID.AIRPORTCODE AS AIRPORTCODE_PERSNL,EMPFID.REGION AS REGION_PERSNL,"
				+ "EN.NSSANCTIONNO AS NSSANCTIONNO,EN.EMPSHARESUBSCRIPITION  AS EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION AS EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION AS LESSCONTRIBUTION,EN.VERIFIEDBY AS VERIFIEDBY,"
				+ "EN.ARREARTYPE,EN.ARREARDATE,EN.FROMFINYEAR,EN.TOFINYEAR,EN.FROMREVISEDINTERESTRATE,EN.TOREVISEDINTERESTRATE,"
				+ "EN.NETCONTRIBUTION AS NETCONTRIBUTION,EN.PAYMENTDT AS PAYMENTDT,EN.NSSANCTIONEDDT AS NSSANCTIONEDDT,EN.SEPERATIONRESAON AS SEPERATIONRESAON,EN.REGION AS REGION,EN.AIRPORTCODE AS  AIRPORTCODE,EN.TRANSDT AS TRANSDT FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO=EMPFID.PENSIONNO AND DELETEFLAG='N' AND EN.NSTYPE='NON-ARREAR'  ";

		if (searchBean.getFormName().equals("FSFormII")) {
			sqlQuery += "and EN.VERIFIEDBY is not null ";
		} else if (searchBean.getFormName().equals("FSFormIII")) {
			sqlQuery += "and ((EN.VERIFIEDBY='PERSONNEL,FINANCE') OR (EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC') OR (EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC') OR (EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED')) ";
		} else if (searchBean.getFormName().equals("FSFormIV")) {
			sqlQuery += "and ((EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC') OR (EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC') OR (EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED')) ";
		} else if (searchBean.getFormName().equals("FSApproval")) {
			sqlQuery += "and  EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC' ";
		} else if (searchBean.getFormName().equals("FSApproved")) {
			sqlQuery += "and  EN.VERIFIEDBY='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' ";
		}

		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EMPFID.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EMPFID.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EN.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}

		if (!searchBean.getNssanctionno().equals("")) {
			whereClause.append(" EN.NSSANCTIONNO='"
					+ searchBean.getNssanctionno() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getSanctiondt().equals("")) {
			try {
				sanctiondt = commonUtil.converDBToAppFormat(searchBean
						.getSanctiondt(), "dd/MM/yyyy", "dd-MMM-yyyy");
				whereClause.append(" EN.nssanctioneddt='" + sanctiondt + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}
		if (!searchBean.getPaymentdt().equals("")) {
			try {
				paymentdt = commonUtil.converDBToAppFormat(searchBean
						.getPaymentdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
				whereClause.append(" EN.PAYMENTDT='" + paymentdt + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}
		if (!searchBean.getSeperationreason().equals("")) {
			whereClause.append(" EN.SEPERATIONRESAON='"
					+ searchBean.getSeperationreason() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" EMPFID.EMPLOYEENAME='"
					+ searchBean.getEmployeeName() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getTrust().equals("")) {
			whereClause.append(" EN.TRUST='" + searchBean.getTrust() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getNssanctionno().equals("")
				&& searchBean.getSanctiondt().equals("")
				&& searchBean.getPaymentdt().equals("")
				&& searchBean.getSeperationreason().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getTrust().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY NSSANCTIONEDDT desc,NSSANCTIONNO desc";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForNoteSheet Leaving Method");
		return dynamicQuery;

	}

	public ArrayList searchNoteSheet(AdvanceSearchBean advanceSearchBean) {
		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchNoteSheet"
				+ advanceSearchBean.getPensionNo());
		String pensionNo = "", dateOfBirth = "", pfid = "", unitCode = "", unitName = "", region = "",transDt="";
		String selectQuery = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			String reg = commonUtil.getAirportsByProfile(advanceSearchBean
					.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
					advanceSearchBean.getLoginRegion());

			if (!reg.equals("")) {
				String[] regArr = reg.split(",");
				unitCode = regArr[0];
				region = regArr[1];
			}

			if (!unitCode.equals("-"))
				unitName = this.getUnitName(advanceSearchBean, con);

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");

			if ((advanceSearchBean.getFormName().equals("FSArrearProcess"))
					|| (advanceSearchBean.getFormName()
							.equals("FSArrearRecommendation"))
					|| (advanceSearchBean.getFormName()
							.equals("FSArrearVerification"))
					|| (advanceSearchBean.getFormName()
							.equals("FSArrearApproval"))
					|| (advanceSearchBean.getFormName()
							.equals("FSArrearApproved"))) {
				selectQuery = this.buildSearchQueryForNoteSheetArrear(
						advanceSearchBean, unitName);
			} else {
				selectQuery = this.buildSearchQueryForNoteSheet(
						advanceSearchBean, unitName);
			}
			log.info("CPFPTWAdvanceDAO::searchNoteSheet" + selectQuery);

			ResultSet rs = st.executeQuery(selectQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();
				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}
				if (rs.getDate("PAYMENTDT") != null) {
					searchBean.setPaymentdt(commonUtil.converDBToAppFormat(rs
							.getDate("PAYMENTDT")));
				} else {
					searchBean.setPaymentdt("---");
				}

				if (rs.getDate("NSSANCTIONEDDT") != null) {
					searchBean.setSanctiondt(commonUtil.converDBToAppFormat(rs
							.getDate("NSSANCTIONEDDT")));
				} else {
					searchBean.setSanctiondt("---");
				}

				// log.info("-------Form Name
				// is------"+advanceSearchBean.getFormName());
				// log.info("-------Verified by is
				// -------"+rs.getString("VERIFIEDBY"));

				if (rs.getString("VERIFIEDBY") != null) {

					if (advanceSearchBean.getFormName().equals("FSFormII")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals("FSFormIII")) {
						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
							searchBean.setTransactionStatus(rs
									.getString("VERIFIEDBY"));
						}
					}

					if (advanceSearchBean.getFormName().equals("FSFormIV")) {
						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals("FSApproval")) {
						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals("FSApproved")) {
						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED")) {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals(
							"FSArrearProcess")) {
						if (rs.getString("VERIFIEDBY").equals("")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals(
							"FSArrearRecommendation")) {
						if (rs.getString("VERIFIEDBY").equals("FINANCE")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
							searchBean.setTransactionStatus(rs
									.getString("VERIFIEDBY"));
						}
					}

					if (advanceSearchBean.getFormName().equals(
							"FSArrearVerification")) {
						if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals(
							"FSArrearApproval")) {
						if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC,DGMREC")) {
							searchBean.setVerifiedBy("N");
						} else {
							searchBean.setVerifiedBy("A");
						}
					}

					if (advanceSearchBean.getFormName().equals(
							"FSArrearApproved")) {
						if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC,DGMREC,APPROVED")) {
							searchBean.setVerifiedBy("A");
						}
					}

					// searchBean.setVerifiedBy("A");

					searchBean.setTransactionStatus(rs.getString("VERIFIEDBY"));

				} else {
					searchBean.setVerifiedBy("N");
				}

				if (rs.getString("ARREARTYPE") != null) {
					searchBean.setArreartype(rs.getString("ARREARTYPE"));
				} else {
					searchBean.setArreartype("N/A");
				}

				if (rs.getString("ARREARDATE") != null) {
					searchBean.setArreardate(CommonUtil.getDatetoString(rs
							.getDate("ARREARDATE"), "dd-MMM-yyyy"));
				} else {
					searchBean.setArreardate("N/A");
				}

				if (rs.getString("FROMFINYEAR") != null) {
					searchBean.setFromfinyear(CommonUtil.getDatetoString(rs
							.getDate("FROMFINYEAR"), "yyyy"));
				} else {
					searchBean.setFromfinyear("N/A");
				}

				if (rs.getString("TOFINYEAR") != null) {
					searchBean.setTofinyear(CommonUtil.getDatetoString(rs
							.getDate("TOFINYEAR"), "yy"));
				} else {
					searchBean.setTofinyear("N/A");
				}

				if (rs.getString("FROMREVISEDINTERESTRATE") != null) {
					searchBean.setInterestratefrom(rs
							.getString("FROMREVISEDINTERESTRATE"));
				} else {
					searchBean.setInterestratefrom("N/A");
				}

				if (rs.getString("TOREVISEDINTERESTRATE") != null) {
					searchBean.setInterestrateto(rs
							.getString("TOREVISEDINTERESTRATE"));
				} else {
					searchBean.setInterestrateto("N/A");
				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, commonUtil.leadingZeros(5, pensionNo));
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setNssanctionno(rs.getString("NSSANCTIONNO"));
				searchBean.setEmplshare(rs.getString("EMPSHARESUBSCRIPITION"));
				searchBean.setEmplrshare(rs.getString("EMPSHARECONTRIBUTION"));
				searchBean.setPensioncontribution(rs
						.getString("LESSCONTRIBUTION"));
				searchBean.setNetcontribution(rs.getString("NETCONTRIBUTION"));
				searchBean.setDesignation(rs.getString("DESEGNATION"));
				
				searchBean
						.setSeperationreason(rs.getString("SEPERATIONRESAON"));
				
				//Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = commonUtil.converDBToAppFormat(rs
							.getDate("TRANSDT"));
					Date transdate = df.parse(transDt); 
					if(transdate.after(new Date("08-May-2012"))){
						searchBean.setStation(rs.getString("AIRPORTCODE"));
						searchBean.setRegion(rs.getString("REGION"));
					}else{
						searchBean.setStation(rs.getString("AIRPORTCODE_PERSNL"));
						searchBean.setRegion(rs.getString("REGION_PERSNL"));
					}
				}else{
					searchBean.setStation(rs.getString("AIRPORTCODE_PERSNL"));
					searchBean.setRegion(rs.getString("REGION_PERSNL"));					
				}
			
				if (rs.getDate("FINALSETTLMENTDT") != null) {
					searchBean.setFinalSettlementDate(commonUtil.converDBToAppFormat(rs
							.getDate("FINALSETTLMENTDT")));
				} else {
					searchBean.setFinalSettlementDate("---");
				}
				if (rs.getDate("RESETTLEMENTDATE") != null) {
					searchBean.setReSettlementDate(commonUtil.converDBToAppFormat(rs
							.getDate("RESETTLEMENTDATE")));
				} else {
					searchBean.setReSettlementDate("---");
				}
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;
	}
	//08-Apr-2011 changed by radha regarding muliptle nominee changes
	public String getNomineeDetails(AdvanceBasicReportBean basicBean,
			String empName, Connection con) {
		StringBuffer buffer = new StringBuffer();
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String nominneRelation = "", nomineeName = "", totalShare = "";
		DecimalFormat df = new DecimalFormat("#########0.000000");
		DecimalFormat df1 = new DecimalFormat("#########0.00");
		int j = 0, srno = 0, k = 0;
		boolean count = false;
		int countValue=0;
		try {
			st = con.createStatement();
			
			
			countValue=this.getNomineeCount(basicBean);
			
			log.info("countValue-------"+countValue);
			
			
			String nomineeQuery = "select SRNO,NOMINEENAME,NOMINEERELATION,TOTALSHARE from employee_nominee_dtls  where EMPFLAG='Y' and  pensionno='"
				+ basicBean.getPensionNo() + "' order by SRNO desc";
			
			rs = st.executeQuery(nomineeQuery);
			while (rs.next()) {
				k++;
				if (rs.getString("SRNO") != null) {
					if (rs.getString("SRNO").equals("1") && k == 1
							&& count == false) {
						count = true;
					}
					
				}
				log.info("----Nominee Count-----" + count);
				if (countValue == 1) {
					if (rs.getString("NOMINEENAME") != null) {
						nomineeName = rs.getString("NOMINEENAME");
					} else {
						nomineeName = "";
					}
					
					if (rs.getString("NOMINEERELATION") != null) {
						nominneRelation = rs.getString("NOMINEERELATION");
					} else {
						nominneRelation = "";
					}
					
					if (nominneRelation.equals("")) {
						buffer.append("  Mr./Ms.  ");
					} else {
						if ((nominneRelation.equals("DAUGHTER"))
								|| (nominneRelation.equals("MOTHER"))
								|| (nominneRelation.equals("MOTHER-IN-LOW"))
								|| (nominneRelation.equals("SONS WIDOW"))
								|| (nominneRelation.equals("WIDOWS DAUGHTER"))) {
							buffer.append(" Ms.  ");
						}else if (nominneRelation.equals("NIECE")) {
							buffer.append("       ");
						}else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("M")) {
							buffer.append(" Smt.  ");
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("F")) {
							buffer.append(" Shri.  ");
						} else {
							buffer.append("  Mr.  ");
						}
					}
					
					buffer.append(nomineeName);
					if (nominneRelation.equals("SPOUSE")) {
						
						if (basicBean.getGender().equals("M")) {
							buffer.append(" wife of  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" husband of  ");
							buffer.append("   ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}
						
						buffer.append(empName);
					}
					
					buffer.append(" ");
					if (rs.getString("TOTALSHARE") != null) {
						totalShare = rs.getString("TOTALSHARE");
					} else {
						totalShare = "100";
					}
					
					buffer.append(" ");
					if (!nominneRelation.equals("SPOUSE")) {
						log
						.info("------------"
								+ commonUtil
								.capitalizeFirstLettersTokenizer(nominneRelation));
						buffer
						.append(commonUtil
								.capitalizeFirstLettersTokenizer(nominneRelation));
					}
					
					if (!nominneRelation.equals("SPOUSE")) {
						buffer.append(" ");
						buffer.append(" of ");
						buffer.append(" Late ");
						buffer.append(" ");
						if (basicBean.getGender().equals("M")) {
							buffer.append(" Shri.  ");
						} else {
							buffer.append(" Smt.  ");
						}
						buffer.append(" ");
						buffer.append(empName);
					}
					
					// buffer.append(totalShare);
				} else {
					j++;
					buffer.append("<br/>");
					buffer.append("\n" + j + ".");
					buffer.append(" ");
					if (rs.getString("TOTALSHARE") != null) {
						totalShare = rs.getString("TOTALSHARE");
					} else {
						totalShare = Integer.toString(100/countValue);
						
					}
					
					buffer.append(df1.format(new Double(totalShare)));
					buffer.append("  %");
					buffer.append(" of net amount to be paid ");
					buffer.append("  i.e. Rs.");
					double netamt = (Double.parseDouble(df.format(new Double(
							commonUtil.replaceAllWords2(basicBean
									.getNetcontribution(), ",", "")))) * Double
									.parseDouble(totalShare)) / 100;
					buffer.append(commonUtil.getDecimalCurrency(Double
							.parseDouble(netamt + "")));
					buffer.append(" (Rs.");
					log.info("======Amt in words======"
							+ CommonUtil.ConvertInWords(Double
									.parseDouble(netamt + "")));
					buffer.append(CommonUtil.ConvertInWords(Double
							.parseDouble(netamt + "")));
					buffer.append(" Only )");
					buffer.append(" to ");
					
					if (rs.getString("NOMINEENAME") != null) {
						nomineeName = rs.getString("NOMINEENAME");
					} else {
						nomineeName = "";
					}
					
					if (rs.getString("NOMINEERELATION") != null) {
						nominneRelation = rs.getString("NOMINEERELATION");
					} else {
						nominneRelation = "";
					}
					
					if (nominneRelation.equals("")) {
						buffer.append("  Mr./Ms.  ");
					} else {
						if ((nominneRelation.equals("DAUGHTER"))
								|| (nominneRelation.equals("MOTHER"))
								|| (nominneRelation.equals("MOTHER-IN-LOW"))
								|| (nominneRelation.equals("SONS WIDOW"))
								|| (nominneRelation.equals("WIDOWS DAUGHTER"))) {
							buffer.append(" Ms.  ");
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("M")) {
							buffer.append(" Smt.  ");
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("F")) {
							buffer.append(" Shri.  ");
						} else {
							buffer.append("  Mr.  ");
						}
					}
					
					buffer.append(nomineeName);
					if (nominneRelation.equals("SPOUSE")) {
						if (basicBean.getGender().equals("M")) {
							buffer.append(" wife of  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" husband of  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}
						
						buffer.append(empName);
						
					}
					buffer.append(" ");
					if (!nominneRelation.equals("SPOUSE")) {
						log
						.info("------------"
								+ commonUtil
								.capitalizeFirstLettersTokenizer(nominneRelation));
						buffer
						.append(commonUtil
								.capitalizeFirstLettersTokenizer(nominneRelation));
						
						buffer.append(" of  ");
						buffer.append(" ");
						buffer.append(" Late ");
						buffer.append(" ");
						if (basicBean.getGender().equals("M"))
							buffer.append(" Shri.  ");
						else
							buffer.append(" Smt.  ");
						buffer.append(" ");
						buffer.append(empName);
						
					}
					
				}
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return buffer.toString();
	}

	// Changed by radha p on 07-Apr-2011
	public String getNomineeDet(AdvanceBasicReportBean basicBean,
			String empName, Connection con) {
		StringBuffer buffer = new StringBuffer();
		Statement st = null;
		ResultSet rs = null;
		String nominneRelation = "", nomineeName = "", nomineeNames = "", totalShare = "", nominneRel = "";

		int k = 0, nomineeCount = 1;

		String totShare = "", nomineeNameStr = "", nomineeRelationStr = "";

		boolean count = false;
		boolean countFlag = false;
		try {
			st = con.createStatement();

			String nomineeQuery = "select SRNO,NOMINEENAME,NOMINEERELATION,TOTALSHARE from employee_nominee_dtls where EMPFLAG='Y' and  pensionno='"
					+ basicBean.getPensionNo() + "' order by SRNO desc";

			log.info("----nomineeQuery-----" + nomineeQuery);
			rs = st.executeQuery(nomineeQuery);

			while (rs.next()) {
				k++;
				if (rs.getString("SRNO") != null) {
					if (rs.getString("SRNO").equals("1") && k == 1
							&& count == false) {
						count = true;
					} else if (countFlag == false) {
						nomineeCount = rs.getInt("SRNO");
						countFlag = true;
					}

				}
				if (count == true) {

					buffer.append(" in favour of");
					if (rs.getString("NOMINEENAME") != null) {
						nomineeName = rs.getString("NOMINEENAME");
					} else {
						nomineeName = "";
					}

					if (rs.getString("NOMINEERELATION") != null) {
						nominneRelation = rs.getString("NOMINEERELATION");
					} else {
						nominneRelation = "";
					}

					if (nominneRelation.equals("")) {
						buffer.append("  Mr./Ms.  ");
					} else {
						if ((nominneRelation.equals("DAUGHTER"))
								|| (nominneRelation.equals("MOTHER"))
								|| (nominneRelation.equals("MOTHER-IN-LOW"))
								|| (nominneRelation.equals("SONS WIDOW"))
								|| (nominneRelation.equals("WIDOWS DAUGHTER"))) {
							buffer.append(" Ms.  ");
						}else if (nominneRelation.equals("NIECE")) {
							buffer.append("    ");
						}else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("M")) {
							buffer.append(" Smt.  ");
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("F")) {
							buffer.append(" Shri.  ");
						} else {
							buffer.append("  Mr.  ");
						}
					}

					buffer.append(nomineeName);
					buffer.append(",");

					if (nominneRelation.equals("SPOUSE")) {
						if (basicBean.getGender().equals("M")) {
							buffer.append(" W/o  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" husband of  ");
							buffer.append("   ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}
					} else if (nominneRelation.equals("SON")) {
						if (basicBean.getGender().equals("M")) {
							buffer.append(" S/o  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" S/o  ");
							buffer.append("   ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}

					} else if (nominneRelation.equals("DAUGHTER")) {
						if (basicBean.getGender().equals("M")) {
							buffer.append(" D/o  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" D/o  ");
							buffer.append("   ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}
					} else if (nominneRelation.equals("MOTHER")) {
						if (basicBean.getGender().equals("M")) {
							buffer.append(" mother of  ");
							buffer.append(" ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Shri.  ");
							buffer.append(" ");
						} else {
							buffer.append(" mother of  ");
							buffer.append("   ");
							buffer.append(" Late ");
							buffer.append(" ");
							buffer.append(" Smt.  ");
							buffer.append(" ");
						}
					}
					buffer.append(empName);
					if (basicBean.getNomineeAppointed().equals("Y")) {
						buffer.append(" as per succession certificate");
					} else {
						buffer
								.append(" who is the nominee appointed by the deceased employee. ");
					}

				} else {

					if (rs.getString("TOTALSHARE") != null) {
						totalShare = rs.getString("TOTALSHARE");
					} else {
						totalShare = "";
					}

					if (rs.getString("NOMINEENAME") != null) {
						nomineeName = rs.getString("NOMINEENAME");
					} else {
						nomineeName = "";
					}

					if (rs.getString("NOMINEERELATION") != null) {
						nominneRelation = rs.getString("NOMINEERELATION");
					} else {
						nominneRelation = "";
					}

					totalShare += "%";

					if (rs.getString("SRNO").equals("2")) {
						totalShare += " and ";
					} else {
						totalShare += ",";
					}
					totShare += totalShare;

					if (nominneRelation.equals("")) {
						nominneRel = "Mr./Ms.";
					} else {
						if ((nominneRelation.equals("DAUGHTER"))
								|| (nominneRelation.equals("MOTHER"))
								|| (nominneRelation.equals("MOTHER-IN-LOW"))
								|| (nominneRelation.equals("SONS WIDOW"))
								|| (nominneRelation.equals("WIDOWS DAUGHTER"))) {
							nominneRel = "Ms.";
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("M")) {
							nominneRel = "Smt.";
						} else if (nominneRelation.equals("SPOUSE")
								&& basicBean.getGender().equals("F")) {
							nominneRel = "Shri.";
						} else {
							nominneRel = "Sh.";
						}
					}
					nomineeNames = nominneRel + nomineeName;

					if (rs.getString("SRNO").equals("2")) {
						nomineeNames += " and ";
					} else {
						nomineeNames += ",";
					}
					nomineeNameStr += nomineeNames;
				}
			}
			if (!totShare.equals("")) {
				buffer.append(totShare.substring(0, totShare.length() - 1));
				buffer.append(" if net amount payable of ");
				buffer.append(empName);
				buffer.append(",");
				buffer.append(basicBean.getDesignation());
				buffer.append(" may be released to ");
				buffer.append(nomineeNameStr.substring(0, nomineeNameStr
						.length() - 1));
				buffer.append(" of ");
				buffer.append(empName);
				buffer.append(",");
				buffer.append(basicBean.getDesignation());
				buffer
						.append(" respectivly is the nomenees approved by the deseased employee");
			}
			buffer.append(nomineeCount);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}
		log.info("-----buffer.toString()-------" + buffer.toString());

		return buffer.toString();
	}

	// ----------------------------------End : NoteSheet
	// Master--------------------------------------------------
	// Replace the complete method on 28-Jul-2010.above commented method before
	//On 25-Apr-2012 By Radha For restricting the updation of finalsettlement date
	public String updateNoteSheet(AdvanceBasicBean advanceBean,String frmName,String frmFlag) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		log.info("updateNoteSheet==========userName=="+userName+"compName"+compName);
		String nsSanctionNo = "", message = "", deleteStatus = "";
		String seperationdt = "", sanctiondt = "", amtadmtdt = "", paymentdt = "";
		
		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
		String flag = "",verfiedBy="";
		
		String tempInfo[] = null;
		String tempData = "", sql2 = "", sql3 = "",delQry="",updateQry="";
		int slno = 0,updatedRecord=0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			//nsSanctionNo = this.getNoteSheetSequence(con);
			
			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getSanctiondt().trim().equals("")) {
				sanctiondt = commonUtil.converDBToAppFormat(advanceBean
						.getSanctiondt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getAmtadmtdate().trim().equals("")) {
				amtadmtdt = commonUtil.converDBToAppFormat(advanceBean
						.getAmtadmtdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getPaymentdt().trim().equals("")) {
				paymentdt = commonUtil.converDBToAppFormat(advanceBean
						.getPaymentdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			
			
			
			if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_PROCESS_FORM)){
				verfiedBy="FINANCE";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_RECOMMENDATION_FORM)){
				verfiedBy="SRMGRREC";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_VERIFIVATION_FORM)){
				verfiedBy="DGMREC";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_APPROVAL)){
				verfiedBy="APPROVED";
			}
			
			log.info("------frmFlag in DAO------"+frmFlag);
			log.info("--------------VERIFIEDBY--------"+verfiedBy);
			
			
			delQry="delete from employee_nominee_dtls where pensionno='"+advanceBean.getPensionNo()+"'";
			log.info("----delQry-----" + delQry);
			st.executeUpdate(delQry);
			
			nomineeRows = advanceBean.getNomineeRow();
			
			log.info("......nomineeRows......." + nomineeRows);
			
			
			StringTokenizer est=new StringTokenizer(nomineeRows,":"); 
			
			int lengt=est.countTokens();
			String estrarr[]=new String[lengt];
			
			for(int e=0;est.hasMoreTokens();e++)
			{                    			
				
				estrarr[e]=est.nextToken();				
				String expsplit=estrarr[e];
				
				String[] strArr=expsplit.split("#");
				for(int ii=0;ii<strArr.length;ii++){					
					slno=Integer.parseInt(strArr[0]);
					nomineeName=strArr[1];
					nomineeAddress=strArr[2];	
					nomineeDob=strArr[3];	
					nomineeRelation=strArr[4];
					nameofGuardian=strArr[5];
					gaurdianAddress=strArr[6];
					totalShare=strArr[7];
				}
				
				
				if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_PROCESS_FORM)){
					sql2 = "insert into employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,equalshare,pensionno)values("
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
						+ advanceBean.getEqualshare()
						+ "','"
						+ advanceBean.getPensionNo()+ "')";					
				}else{
					sql2 = "insert into employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,pensionno)values("
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
						+ advanceBean.getPensionNo() + "')";	
				}
				
				
				
				log.info("----sql2-----" + sql2);
				st.executeUpdate(sql2);
				
			}
			
			//  For  restricting the updation of personal Information
			/*  updateQry = "update employee_personal_info set DESEGNATION='"
				+ advanceBean.getDesignation()
				+ "',AIRPORTCODE='"
				+ advanceBean.getStation()
				+ "',REGION='"
				+ advanceBean.getRegion()
				+ "',DATEOFSEPERATION_DATE='"
				+ seperationdt		
				+ "',DATEOFSEPERATION_REASON='"
				+ advanceBean.getSeperationreason()		
				+ "' where  PENSIONNO='"
				+ advanceBean.getPensionNo() + "'";
			
			log.info("==========update Query===========" + updateQry);
			int updatedRecord = st.executeUpdate(updateQry);*/
			
			

			// If we provide any permission to change the region & station 
			// we have to update in EMPLOYEE_ADVANCE_NOTEPARAM table 
			
			
			if(frmFlag.equals("NewForm")){
				sql3 = "update employee_advance_noteparam set SEPERATIONDT='"
					+ seperationdt
					+ "',SEPERATIONRESAON='"
					+ advanceBean.getSeperationreason()
					+ "',EMPSHARESUBSCRIPITION="
					+ advanceBean.getEmplshare()
					+ ",EMPSHARECONTRIBUTION="
					+ advanceBean.getEmplrshare()
					+ ",LESSCONTRIBUTION="
					+ advanceBean.getPensioncontribution()
					+ ",NETCONTRIBUTION="
					+ advanceBean.getNetcontribution()
					+ ",ADHOCAMOUNT="
					+ advanceBean.getAdhocamt()
					+ ",NSSANCTIONEDDT='"
					+ sanctiondt
					+ "',AMTADMITTEDDT='"
					+ amtadmtdt
					+ "',TRUST='"
					+ advanceBean.getTrust()
					+ "',PAYMENTDT='"
					+ paymentdt
					+ "',POSTINGFLAG='"
					+ advanceBean.getPostingFlag()
					+ "',POSTINGREGION='"
					+ advanceBean.getPostingRegion()
					+ "',POSTINGSTATION='"
					+ advanceBean.getPostingStation()
					+ "',REMARKS='"					
					+ commonUtil.escapeSingleQuotes(advanceBean.getRemarks()) 				
					+ "',VERIFIEDBY=VERIFIEDBY||','||'"
					+ verfiedBy
					+ "',SEPERATIONFAVOUR='"
					+ advanceBean.getSeperationfavour()
					+ "',SEPERATIONREMARKS='"
					+ commonUtil.escapeSingleQuotes(advanceBean
							.getSeperationremarks()) + "',AAISANCTIONNO='"
							+ advanceBean.getSanctionno() + "' where NSSANCTIONNO="
							+ advanceBean.getNssanctionno() + " and pensionno='"
							+ advanceBean.getPensionNo() + "'";
			}else{
				sql3 = "update employee_advance_noteparam set SEPERATIONDT='"
					+ seperationdt
					+ "',SEPERATIONRESAON='"
					+ advanceBean.getSeperationreason()
					+ "',EMPSHARESUBSCRIPITION="
					+ advanceBean.getEmplshare()
					+ ",EMPSHARECONTRIBUTION="
					+ advanceBean.getEmplrshare()
					+ ",LESSCONTRIBUTION="
					+ advanceBean.getPensioncontribution()
					+ ",NETCONTRIBUTION="
					+ advanceBean.getNetcontribution()
					+ ",ADHOCAMOUNT="
					+ advanceBean.getAdhocamt()
					+ ",NSSANCTIONEDDT='"
					+ sanctiondt
					+ "',AMTADMITTEDDT='"
					+ amtadmtdt
					+ "',TRUST='"
					+ advanceBean.getTrust()
					+ "',PAYMENTDT='"
					+ paymentdt
					+ "',POSTINGFLAG='"
					+ advanceBean.getPostingFlag()
					+ "',POSTINGREGION='"
					+ advanceBean.getPostingRegion()
					+ "',POSTINGSTATION='"
					+ advanceBean.getPostingStation()
					+ "',REMARKS='"					
					+ commonUtil.escapeSingleQuotes(advanceBean.getRemarks()) 			
					+ "',SEPERATIONFAVOUR='"
					+ advanceBean.getSeperationfavour()
					+ "',SEPERATIONREMARKS='"
					+ commonUtil.escapeSingleQuotes(advanceBean
							.getSeperationremarks()) + "',AAISANCTIONNO='"
							+ advanceBean.getSanctionno() + "' where NSSANCTIONNO="
							+ advanceBean.getNssanctionno() + " and pensionno='"
							+ advanceBean.getPensionNo() + "'";
				
			}
			
			
			log.info("----sql3-----" + sql3);
			st.executeUpdate(sql3);
			
			
			log.info("Status in DAO ========= "+advanceBean.getAuthrizedStatus());
			
			if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_APPROVAL) && (advanceBean.getAuthrizedStatus().equals("A"))){
				String persRemarks = "Frm FinalSettlment Entry";
				String updatePersonalQry = "update employee_personal_info set PFSETTLED='Y',USERNAME='"+this.userName+"',IPADDRESS='"+this.compName+"',REMARKS=REMARKS||'"+persRemarks+"' ,LASTACTIVE=sysdate where  PENSIONNO='"
					+ advanceBean.getPensionNo() + "'";
				
				log.info("==========update Personal Query===========" + updatePersonalQry);
				updatedRecord = st.executeUpdate(updatePersonalQry);
				
			}
			CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();
			
			
			BeanUtils.copyProperties(transBean,advanceBean);
			transBean.setTransremarks(advanceBean.getRemarks());
			
			CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());
			

			if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_PROCESS_FORM)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_PROCESS_FORM,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_RECOMMENDATION_FORM)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_VERIFIVATION_FORM)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_DGM,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_APPROVAL)){				 
				 	cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_APPROVAL,advanceBean.getLoginUserDesignation());			    
			}
			
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public ArrayList consSanctionOrder(String reg, String fromDate,
			String toDate, String seperationreason, String frmstation,
			String trust, String frmName) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();
		CommonUtil commonUtil = new CommonUtil();

		String empName = "", airportCd = "", region = "", airportList = "", temp1 = "", headQuaters = "", regHeadQuarter = "", amtadmtdt = "";
		String tempAirportCd = "", seperationdt = "", sanctiondt = "", regionLabel = "", paymentDt = "", seperationReason = "";
		String nomineeNm = "", nomineeCnt = "", station = "", netContAmt = "", airportLabel = "", dateOfBirth = "", pfid = "";

		/*
		 * String sqlQuery="SELECT PI.CPFACNO AS CPFACNO,PI.EMPLOYEENAME AS
		 * EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS
		 * AIRPORTCODE,PI.REGION AS REGION,"+ "EN.SEPERATIONRESAON AS
		 * SEPERATIONRESAON,EN.SEPERATIONDT AS
		 * SEPERATIONDT,EN.AMTADMITTEDDT,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.NSSANCTIONEDDT,EN.PAYMENTDT
		 * AS PAYMENTDT "+ " FROM EMPLOYEE_PERSONAL_INFO PI,
		 * EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO = PI.PENSIONNO AND
		 * PI.REGION='"+reg+"' AND EN.SEPERATIONRESAON ='"+seperationreason+"'
		 * AND EN.PAYMENTDT between
		 * to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy') and
		 * last_day(to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy'))";
		 */
		boolean flag = false;
		StringBuffer sb = new StringBuffer();

		log.info("frmName in DAO-------------" + frmName);
		String sqlQuery = this.buildConsSanctionOrderQuery(reg, fromDate,
				toDate, seperationreason, frmstation, trust, frmName);
		log.info("CPFPTWAdvanceDAO::consSanctionOrder" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(sqlQuery);

			while (rs.next()) {

				basicBean = new AdvanceBasicReportBean();
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					empName = rs.getString("EMPLOYEENAME");
					basicBean.setEmployeeName(empName);
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("PAYMENTDT") != null) {
					paymentDt = CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MM-yy");
				}

				if (rs.getString("AIRPORTCODE") != null) {

					if ((rs.getString("AIRPORTCODE").equals("CHQNAD"))
							|| (rs.getString("AIRPORTCODE").equals("CHQ"))
							|| (rs.getString("AIRPORTCODE")
									.equals("C.S.I Airport"))
							|| (rs.getString("AIRPORTCODE").equals("CSIA IAD"))
							|| (rs.getString("AIRPORTCODE").equals("RAUSAP"))
							|| (rs.getString("AIRPORTCODE").equals("EMO"))
							|| (rs.getString("AIRPORTCODE").equals("CRSD"))
							|| (rs.getString("AIRPORTCODE").equals("DRCDU"))) {

						basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
					} else {
						basicBean.setAirportcd(commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("AIRPORTCODE")));
					}

				} else {
					basicBean.setAirportcd("---");
				}

				if (rs.getString("AIRPORTCODE") != null) {

					if (!airportLabel.equals(rs.getString("AIRPORTCODE"))) {
						airportLabel = rs.getString("AIRPORTCODE");

						if ((rs.getString("AIRPORTCODE").equals("CHQNAD"))
								|| (rs.getString("AIRPORTCODE").equals("CHQ"))
								|| (rs.getString("AIRPORTCODE")
										.equals("C.S.I Airport"))
								|| (rs.getString("AIRPORTCODE")
										.equals("CSIA IAD"))
								|| (rs.getString("AIRPORTCODE")
										.equals("RAUSAP"))
								|| (rs.getString("AIRPORTCODE").equals("EMO"))
								|| (rs.getString("AIRPORTCODE").equals("CRSD"))
								|| (rs.getString("AIRPORTCODE").equals("DRCDU"))) {

							basicBean.setAirportlabel(rs
									.getString("AIRPORTCODE"));

						} else
							basicBean
									.setAirportlabel(commonUtil
											.capitalizeFirstLettersTokenizer(airportLabel));
					} else {
						basicBean.setAirportlabel("N");
					}

				}

				if (rs.getString("REGION") != null) {
					region = commonUtil.getRegion(rs.getString("REGION"));
				} else {
					region = "---";
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(rs.getString("DATEOFBIRTH"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					basicBean.setDateOfBirth("");
				}

				/*
				 * if (rs.getString("REGION") != null) { String
				 * reg1=rs.getString("REGION");
				 * 
				 * try{ headQuaters=commonUtil.getRegionHeadQuarters(reg1);
				 * 
				 * if(!temp1.equals(reg1)){ if(headQuaters.equals(reg1)){
				 * sb.append("The General Manager (F&A), AAI,");
				 * sb.append(reg1); sb.append(","); sb.append(headQuaters);
				 * sb.append("Airport ,"); sb.append(headQuaters);
				 * sb.append("\n"); sb.append("<br/>");
				 * 
				 * flag=true; temp1=reg1; } } }catch(Exception e){ }
				 * 
				 * regHeadQuarter=sb.toString(); }
				 */

				if (rs.getString("AIRPORTCODE") != null) {
					airportCd = rs.getString("AIRPORTCODE").trim()
							.toUpperCase();
					station = commonUtil.getRegionHeadQuarters(
							commonUtil.getRegion(rs.getString("REGION")))
							.toUpperCase();

					if (station.equals("NEW DELHI"))
						station = "DELHI";

					if (!station.equals(airportCd)) {

						if (sb.length() == 0) {
							sb.append("The OIC, AAI,");
							tempAirportCd = airportCd;

							if ((airportCd.trim().equals("EMO"))
									|| (airportCd.trim().equals("DRCDU"))
									|| (airportCd.trim().equals("CRSD"))) {
								sb.append(basicBean.getAirportcd());
								sb.append(",");
								sb.append("Safdarjung Airport, New Delhi ");
							} else {
								sb
										.append(commonUtil
												.capitalizeFirstLettersTokenizer(airportCd));
								sb.append(" Airport,");
								if ((airportCd.trim().equals("SAFDARJUNG"))) {
									sb.append(" New Delhi ");
								} else {
									sb
											.append(commonUtil
													.capitalizeFirstLettersTokenizer(airportCd));
								}

							}

							sb.append("\n");
							sb.append("<br/>");
						} else {

							if (!tempAirportCd.equals(airportCd)) {
								sb.append("The OIC, AAI,");

								if ((airportCd.trim().equals("EMO"))
										|| (airportCd.trim().equals("DRCDU"))
										|| (airportCd.trim().equals("CRSD"))) {
									sb.append(basicBean.getAirportcd());
									sb.append(",");
									sb.append("Safdarjung Airport, New Delhi ");
								} else {
									sb
											.append(commonUtil
													.capitalizeFirstLettersTokenizer(airportCd));
									sb.append(" Airport,");
									if ((airportCd.trim().equals("SAFDARJUNG"))) {
										sb.append(" New Delhi ");
									} else {
										sb
												.append(commonUtil
														.capitalizeFirstLettersTokenizer(airportCd));
									}

								}

								sb.append("\n");
								sb.append("<br/>");
							}
							tempAirportCd = airportCd;
						}
						airportList = sb.toString();
					}

					log.info("----OCI airportList-----" + airportList);
				}
				// flag=false;

				if (rs.getString("AMTADMITTEDDT") != null) {
					amtadmtdt = CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MM-yyyy");
				} else {
					amtadmtdt = "---";
				}

				if (rs.getString("SEPERATIONDT") != null) {
					basicBean.setSeperationdate(CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy"));

					seperationdt = CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy");
				} else {
					seperationdt = "---";
				}

				if (rs.getString("SEPERATIONRESAON") != null) {
					seperationReason = rs.getString("SEPERATIONRESAON");
					basicBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));
				} else {
					basicBean.setSeperationreason("---");
				}

				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					basicBean.setEmplshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARESUBSCRIPITION")));
				} else {
					basicBean.setEmplshare("---");
				}

				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					basicBean.setEmplrshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARECONTRIBUTION")));
				} else {
					basicBean.setEmplrshare("---");
				}

				if (rs.getString("LESSCONTRIBUTION") != null) {
					basicBean.setPensioncontribution(commonUtil.getCurrency(rs
							.getDouble("LESSCONTRIBUTION")));
				} else {
					basicBean.setPensioncontribution("---");
				}

				if (rs.getString("NETCONTRIBUTION") != null) {
					basicBean.setNetcontribution(commonUtil.getCurrency(rs
							.getDouble("NETCONTRIBUTION")));

					netContAmt = rs.getString("NETCONTRIBUTION");
				} else {
					basicBean.setNetcontribution("---");
				}

				if (rs.getString("ADHOCAMOUNT") != null) {
					basicBean.setAdhocamt(commonUtil.getCurrency(rs
							.getDouble("ADHOCAMOUNT")));
				} else {
					basicBean.setAdhocamt("---");
				}

				if (rs.getString("NSSANCTIONEDDT") != null) {
					sanctiondt = CommonUtil.getDatetoString(rs
							.getDate("NSSANCTIONEDDT"), "dd-MM-yy");
				} else {
					sanctiondt = "---";
				}

				if (rs.getString("PENSIONNO") != null) {
					basicBean.setPensionNo(rs.getString("PENSIONNO"));
				} else {
					basicBean.setPensionNo("---");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName()
						.toUpperCase(), dateOfBirth, commonUtil.leadingZeros(5,
						basicBean.getPensionNo()));
				basicBean.setPfid(pfid);

				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {
					basicBean.setGender("---");
				}

				// basicBean.setPensionNo(pensionNo);
				String nomineeName = this
						.getNomineeDet(basicBean, empName, con);

				if (nomineeName.length() != 0) {
					nomineeNm = nomineeName.substring(0,
							nomineeName.length() - 1);
					nomineeCnt = nomineeName.substring(
							nomineeName.length() - 1, nomineeName.length());
				}

				System.out.println("=======nomineeNm======" + nomineeNm);

				if ((!nomineeNm.equals(""))
						&& (seperationReason.equals("Death"))) {
					basicBean.setNomineename(nomineeNm);
				} else {
					basicBean.setNomineename(nomineeNm);
				}

				if ((!nomineeCnt.equals(""))
						&& (seperationReason.equals("Death"))) {
					basicBean.setNomineecount(nomineeCnt);
				} else {
					basicBean.setNomineecount(nomineeCnt);
				}

				if (seperationReason.equals("Death")) {
					nomineeList = this.getNomineeDets(basicBean, empName,
							netContAmt, con);

					log.info("------Nominee list size in DAO-----"
							+ nomineeList.size());
					basicBean.setNomineeList(nomineeList);
				}

				sanctionOrderList.add(basicBean);

			}
			log.info("---------sanctionOrderList size in DAO---------"
					+ sanctionOrderList.size());
			AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();

			reportbean.setRegion(region);
			regionLabel = commonUtil.getRegionLbls(region);
			reportbean.setRegionLbl(regionLabel);
			reportbean.setAmtadmtdate(amtadmtdt);
			reportbean.setSeperationdate(seperationdt);
			reportbean.setSanctiondt(sanctiondt);
			reportbean.setSanctionList(sanctionOrderList);
			reportbean.setPaymentdate(paymentDt);
			reportbean.setAirportcd(airportList);
			// reportbean.setRegion(regHeadQuarter);
			reportbean.setSeperationreason(seperationReason);

			reportList.add(reportbean);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	public String buildConsSanctionOrderQuery(String reg, String fromDate,
			String toDate, String seperationreason, String frmstation,
			String trust, String flag) {
		log
				.info("CPFPTWAdvanceDAO::buildConsSanctionOrderQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "";

		sqlQuery = "SELECT PI.CPFACNO AS CPFACNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS AIRPORTCODE,PI.REGION AS REGION,PI.PENSIONNO AS PENSIONNO,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH,"
				+ "EN.SEPERATIONRESAON AS SEPERATIONRESAON,EN.SEPERATIONDT AS SEPERATIONDT,EN.AMTADMITTEDDT,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.ADHOCAMOUNT,EN.NSSANCTIONEDDT,EN.PAYMENTDT AS PAYMENTDT "
				+ " FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM EN  WHERE EN.PENSIONNO = PI.PENSIONNO ";

		// AND PI.REGION='"+reg+"' AND EN.SEPERATIONRESAON
		// ='"+seperationreason+"' AND EN.PAYMENTDT between
		// to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy') and
		// last_day(to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy'))";

		if (!flag.equals("")) {
			whereClause.append(" EN.NSTYPE='" + flag + "'");
			whereClause.append(" AND ");
		}

		if (!reg.equals("NO-SELECT")) {
			whereClause.append(" PI.REGION='" + reg + "'");
			whereClause.append(" AND ");
		}

		if (!frmstation.equals("NO-SELECT")) {
			whereClause.append(" PI.AIRPORTCODE='" + frmstation + "'");
			whereClause.append(" AND ");
		}

		if (!seperationreason.equals("Select One")) {
			whereClause.append(" EN.SEPERATIONRESAON='" + seperationreason
					+ "'");
			whereClause.append(" AND ");
		}

		if (!trust.equals("Select One")) {
			whereClause.append(" EN.TRUST='" + trust + "'");
			whereClause.append(" AND ");
		}

		if ((!fromDate.equals("")) && (!toDate.equals(""))) {
			try {
				whereClause.append(" EN.NSSANCTIONEDDT  between to_date('"
						+ fromDate + "','dd/mm/yyyy') and to_date('" + toDate
						+ "','dd/mm/yyyy')");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (reg.equals("") && frmstation.equals("NO-SELECT")
				&& seperationreason.equals("") && trust.equals("")
				&& fromDate.equals("") && toDate.equals("") && flag.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY AIRPORTCODE,REGION desc";
		query.append(orderBy);
		// orderBy = "ORDER BY NSSANCTIONEDDT desc";
		// query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildConsSanctionOrderQuery Leaving Method");
		return dynamicQuery;
	}
//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int updatePFWAdvanceForm2(AdvanceCPFForm2Bean advanceBean) {
		Connection con = null;
		Statement st = null;
		int updatedRecords = 0;
		String updateQuery = "";
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			log.info("CPFPTWAdvanceDAO::updatePFWAdvanceForm2"
					+ advanceBean.getPensionNo()
					+ advanceBean.getPurposeOptionType().toUpperCase().trim());
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET VERIFIEDBY='PERSONNEL',LOANTAKEN='"
					+ advanceBean.getTakenloan()
					+ "',ADVANCETRANSSTATUS='"
					+ advanceBean.getAuthrizedStatus()
					+ "',APPROVEDREMARKS='"
					+ advanceBean.getAuthrizedRemarks()
					+ "' WHERE ADVANCETRANSID='"
					+ advanceBean.getAdvanceTransID() + "'";
			log.info("CPFPTWAdvanceDAO::updatePFWAdvanceForm2Info"
					+ updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			transBean = this.getCPFPFWTransDetails(advanceBean
					.getAdvanceTransID());

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
					.getLoginUserId(), advanceBean.getLoginUserName(),
					advanceBean.getLoginUnitCode(), advanceBean
							.getLoginRegion(), advanceBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(transBean, advanceBean.getPensionNo(),
					advanceBean.getAdvanceTransID(), advanceBean.getLoginUserDesignation(), "PFW",
					Constants.APPLICATION_PROCESSING_PFW_PART_II);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	public void getOutStandingAmoutForCPF() {

	}
//	 Modified By Radha On 20-Jul-2011 for displaying bank details requset raised by JBiswas East Region
//	 Modified By Radha On 21-Jul-2011 for segrigation of Region Wise
	public ArrayList advancesReport(String reg, String fromDate, String toDate,
			String advanceType, String purposeType, String trust, String station,String userProfile, String userUnitCode, String userId,String accountType,String menuAccessFrom) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();

		String region = "", currentDate = "", mon = "", year = "", pfid = "", transID = "", sortingOrder = "";
		long totalRequiredAmt = 0, totalActualCost = 0, totalSubContAmt = 0, totalMthInstallAmt = 0, totalIntInstallAmt = 0;
		double totalSanctionedAmt=0.0;
		if (advanceType.toLowerCase().equals("pfw")) {
			sortingOrder = "SANCTIONDATE";
		} else {
			sortingOrder = "APPROVEDDT";
		}
		String sqlQuery = this.buildadvancesReportQuery(reg, fromDate, toDate,
				advanceType, purposeType, trust, station, sortingOrder, userProfile, userUnitCode, userId,accountType,menuAccessFrom);

		log.info("CPFPTWAdvanceDAO::advancesReport" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {

				basicBean = new AdvanceBasicReportBean();
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}

				/*
				 * if(!fromDate.equals("")){
				 * currentDate=commonUtil.converDBToAppFormat(fromDate,"dd-MM-yyyy","dd-MMM-yyyy");
				 * log.info("=========currentDate=========="+currentDate);
				 * 
				 * String[] strArray=currentDate.split("-");
				 * 
				 * mon=strArray[1]; year=strArray[2];
				 * 
				 * log.info("======month===="+month);
				 * 
				 * if((!mon.equals("")) && (!month.equals("0"))){
				 * basicBean.setMonth(mon); }else{ basicBean.setMonth(""); }
				 * 
				 * if(!year.equals("")){ int
				 * nextyear=Integer.parseInt(year.substring(2,4))+1; String
				 * nextyr=nextyear+"";
				 * basicBean.setTransMnthYear(year+"-"+nextyr); }else{
				 * basicBean.setTransMnthYear(""); }
				 *  }
				 */
				if (rs.getString("ADVANCETRANSID") != null) {
					transID = rs.getString("ADVANCETRANSID");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("EMPLOYEENAME")));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("status") != null) {
					basicBean.setCbstatus(rs
									.getString("status"));
				} else {
					basicBean.setCbstatus("----");
				}
				if (rs.getString("voucherno") != null) {
					basicBean.setCbvocherno(rs
									.getString("voucherno"));
				} else {
					basicBean.setCbvocherno("---");
				}
				if (advanceType.toLowerCase().equals("pfw")) {
					if (rs.getString("ADVANCETRANSDT") != null) {
						basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
								.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					} else {
						basicBean.setAdvtransdt("");
					}
				} else {
					if (rs.getString("APPROVEDDT") != null) {
						basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
								.getDate("APPROVEDDT"), "dd-MMM-yyyy"));
					} else {
						basicBean.setAdvtransdt("");
					}
				}

				if (rs.getString("AIRPORTCODE") != null) {
					basicBean.setAirportcd(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("AIRPORTCODE")));
				} else {
					basicBean.setAirportcd("---");
				}

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				}

				log
						.info("Purpose Tpe is ------- "
								+ basicBean.getPurposeType());
				if (basicBean.getPurposeType().equals("HBA")) {

					basicBean = this.loadPFWHBA(transID, basicBean);

					if ((!basicBean.getActualcost().equals("0.00"))
							&& (!basicBean.getActualcost().equals(""))) {

						totalActualCost += Long.parseLong(basicBean
								.getActualcost());
						basicBean.setTotalactualcost(commonUtil
								.getCurrency(totalActualCost));
						basicBean.setActualcost(commonUtil.getCurrency(Double
								.parseDouble(basicBean.getActualcost())));
					}

				} else {
					basicBean.setActualcost("0.00");
					basicBean.setTotalactualcost(commonUtil
							.getCurrency(totalActualCost));
				}
				if (rs.getString("REGION") != null) {
					region = commonUtil.getRegion(rs.getString("REGION"));

					if ((rs.getString("REGION").equals("CHQIAD"))
							|| (rs.getString("REGION").equals("CHQNAD"))) {
						basicBean.setRegion("CHQ");
					} else {
						basicBean.setRegion(rs.getString("REGION"));
					}
				} else {
					basicBean.setRegion("");
				}
				if (rs.getString("SUBSCRIPTIONAMNT") != null
						|| rs.getString("CONTRIBUTIONAMOUNT") != null) {
					double empAmt = rs.getDouble("SUBSCRIPTIONAMNT")
							+ rs.getDouble("CONTRIBUTIONAMOUNT");
					basicBean
							.setEmplrshare(commonUtil.getCurrency(empAmt) + "");

					totalSubContAmt += empAmt;
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubContAmt)));
				} else {
					basicBean.setEmplrshare("---");
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubContAmt)));
				}

				if (rs.getString("SANCTIONDATE") != null) {
					basicBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					basicBean.setSanctiondt("---");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					basicBean.setDesignation("---");
				}

				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("---");
				}

				if (rs.getString("PURPOSETYPE") != null) {

					String purposeTypes = "";
					if (rs.getString("PURPOSETYPE").equals("COST")) {
						purposeTypes = "Cost Of Passage";
					} else if (rs.getString("PURPOSETYPE").equals("OBMARRIAGE")) {
						purposeTypes = "Marriage";
					} else if (rs.getString("PURPOSETYPE").equals("EDUCATION")) {
						purposeTypes = "Higher Education";
					} else if (rs.getString("PURPOSETYPE").equals("DEFENCE")) {
						purposeTypes = "Defense Of Court Case";
					} else if (rs.getString("PURPOSETYPE").equals("HBA")) {
						purposeTypes = "HBA";
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						purposeTypes = "Higher Education";
					} else {
						purposeTypes = commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE"));
					}

					basicBean.setPurposeType(purposeTypes);

				} else {
					basicBean.setPurposeType("---");
				}

				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setRequiredAmt(commonUtil.getCurrency(rs
							.getDouble("REQUIREDAMOUNT")));

					totalRequiredAmt += Long.parseLong(rs
							.getString("REQUIREDAMOUNT"));
					basicBean.setTotalrequiredamt(commonUtil
							.getCurrency(totalRequiredAmt));

				} else {
					basicBean.setRequiredAmt("---");
					basicBean.setTotalrequiredamt(commonUtil
							.getCurrency(totalRequiredAmt));
				}

				if (rs.getString("APPROVEDAMNT") != null) {
					basicBean.setApprovedAmt(commonUtil.getCurrency(rs
							.getDouble("APPROVEDAMNT")));

					totalSanctionedAmt += Double.parseDouble(rs
							.getString("APPROVEDAMNT"));
					basicBean.setTotalsanctionedamt(commonUtil
							.getCurrency((totalSanctionedAmt)));
				} else {
					basicBean.setApprovedAmt("---");
					basicBean.setTotalsanctionedamt(commonUtil
							.getCurrency((totalSanctionedAmt)));
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					String PurposeOptionType = "";

					if (basicBean.getPurposeType().equals("Obligatory")) {
						String[] cer = rs.getString("PURPOSEOPTIONTYPE").split(
								"-");
						PurposeOptionType = commonUtil
								.capitalizeFirstLettersTokenizer(cer[1]);
					} else {
						PurposeOptionType = commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSEOPTIONTYPE"));
					}
					basicBean.setPurposeOptionType(PurposeOptionType);
				} else {
					basicBean.setPurposeOptionType("---");
				}

				if (rs.getString("PLACEOFPOSTING") != null) {
					basicBean.setPlaceofposting(rs.getString("PLACEOFPOSTING"));
				} else {
					basicBean.setPlaceofposting("---");
				}

				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setCpfIntallments(rs
							.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setCpfIntallments("---");
				}

				if (rs.getString("MTHINSTALLMENTAMT") != null) {
					basicBean.setMthinstallmentamt(commonUtil.getCurrency(rs
							.getDouble("MTHINSTALLMENTAMT")));

					totalMthInstallAmt += Long.parseLong(rs
							.getString("MTHINSTALLMENTAMT"));
					basicBean.setTotalMthInstallAmt(commonUtil
							.getCurrency((totalMthInstallAmt)));
				} else {
					basicBean.setMthinstallmentamt("---");
					basicBean.setTotalMthInstallAmt(commonUtil
							.getCurrency((totalMthInstallAmt)));
				}

				if (rs.getString("INTERESTINSTALLMENTS") != null) {
					basicBean.setInterestinstallments(rs
							.getString("INTERESTINSTALLMENTS"));
				} else {
					basicBean.setInterestinstallments("---");
				}

				if (rs.getString("INTERESTINSTALLAMT") != null) {
					basicBean.setIntinstallmentamt(commonUtil.getCurrency(rs
							.getDouble("INTERESTINSTALLAMT")));

					totalIntInstallAmt += Long.parseLong(rs
							.getString("INTERESTINSTALLAMT"));
					basicBean.setTotalIntInstallAmt(commonUtil
							.getCurrency((totalIntInstallAmt)));
				} else {
					basicBean.setTotalIntInstallAmt("---");
					basicBean.setTotalIntInstallAmt(commonUtil
							.getCurrency((totalIntInstallAmt)));
				}
				
				if (rs.getString("NAMEINBANK") != null) {
					basicBean.setBankempname(rs.getString("NAMEINBANK"));
				}else{
					basicBean.setBankempname("---");
				}
				if (rs.getString("BANKNAME") != null) {
					basicBean.setBankname(rs.getString("BANKNAME"));
				}else{
					basicBean.setBankname("---");
				}
				if (rs.getString("SAVINGBNKACCNO") != null) {
					basicBean.setBanksavingaccno(rs
							.getString("SAVINGBNKACCNO"));
				}else{
					basicBean.setBanksavingaccno("---");
				}
				if (rs.getString("NEFTRTGSCODE") != null) {
					basicBean.setBankemprtgsneftcode(rs
							.getString("NEFTRTGSCODE"));
				}else{
					basicBean.setBankemprtgsneftcode("---");
				}
				

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
								"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
				basicBean.setPfid(pfid);

				fromDate = "";
				sanctionOrderList.add(basicBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return sanctionOrderList;

	}
	//	Modified By Radha On 16-Sep-2011 for seperating CPF & PFW Reports Specification  
	//	Modified By Radha On 21-Jul-2011 for segrigation of Region Wise
	// 09-Nov-2010 Radha for cpf advances report
	
	public String buildadvancesReportQuery(String reg, String fromDate,
			String toDate, String advanceType, String purposeType,
			String trust, String station, String sortingOrder,String userProfile, String userUnitCode, String userId,String accountType,String menuAccesFrom) {
		log
				.info("CPFPTWAdvanceDAO::buildadvancesReportQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "", orderBy = "",queryCond = "",sqlQuery_trans = "",accessApprovedFrom="";
		 
		if(userProfile.equals("C")){
			queryCond = " AND (userS.profile = 'C' and trans.transcd = '3'))";
		}else if(userProfile.equals("R")){
			
				queryCond = "AND ((users.profile = 'R' and trans.transcd = '4') and  upper(units.accounttype) = '"+accountType+"'))";
			
			
		}else if(userProfile.equals("U")){
			queryCond = "AND ((users.profile = 'U' and trans.transcd = '4') and  upper(units.accounttype) = '"+accountType+"'))";
			//queryCond = "AND ((users.profile = 'U' and trans.transcd = '4') and  upper(units.accounttype) = '"+accountType+"' and  upper(units.unitcode) = '"+userUnitCode+"'))";
		}else{
			queryCond =")";
		}
		
		sqlQuery = "SELECT PI.CPFACNO AS CPFACNO,PI.NEWEMPCODE AS EMPLOYEENO,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.PENSIONNO AS PENSIONNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS AIRPORTCODE,PI.REGION AS REGION,"
				+ "AF.ADVANCETYPE AS ADVANCETYPE,AF.ADVANCETRANSID AS ADVANCETRANSID,AF.ADVANCETRANSDT AS ADVANCETRANSDT,AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.APPROVEDDT AS APPROVEDDT,"
				+ "AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.SANCTIONDATE AS SANCTIONDATE,AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.INTERESTINSTALLMENTS AS INTERESTINSTALLMENTS,AF.INTERESTINSTALLAMT AS INTERESTINSTALLAMT,AF.MTHINSTALLMENTAMT AS MTHINSTALLMENTAMT, "
				+ " EBI.NAME AS NAMEINBANK, EBI.BANKNAME AS BANKNAME, EBI.SAVINGBNKACCNO AS SAVINGBNKACCNO, EBI.NEFTRTGSCODE AS NEFTRTGSCODE, cinfo.voucherno as voucherno,decode (cinfo.approval,'Y','Payment Released','','Not Yet Started','N','Processing') as status" +
				" FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF , EMPLOYEE_BANK_INFO EBI,cb_voucher_info cinfo  WHERE  AF.PENSIONNO = PI.PENSIONNO  and af.advancetransid=cinfo.transid(+) and  AF.PENSIONNO = EBI.PENSIONNO(+) and  AF.ADVANCETRANSID = EBI.Cpfpfwtransid(+) AND AF.ADVANCETYPE = cinfo.othermodulelink and AF.DELETEFLAG='N' ";

		if (!advanceType.equals("NO-SELECT")) {
			whereClause.append(" AF.ADVANCETYPE='" + advanceType + "'");
			whereClause.append(" AND ");
		}

		if (!reg.equals("NO-SELECT")) {
			whereClause.append(" PI.REGION='" + reg + "'");
			whereClause.append(" AND ");
		}

		if (!station.equals("NO-SELECT")) {
			whereClause.append(" PI.AIRPORTCODE='" + station + "'");
			whereClause.append(" AND ");
		}

		if (!purposeType.equals("NO-SELECT")) {
			whereClause.append(" AF.PURPOSETYPE='" + purposeType.toUpperCase()
					+ "'");
			whereClause.append(" AND ");
		}

		if (!trust.equals("NO-SELECT")) {
			whereClause.append(" AF.TRUST='" + trust + "'");
			whereClause.append(" AND ");
		}

		if (!advanceType.equals("NO-SELECT")) {
			if (advanceType.equals("PFW")) {
				whereClause.append(" AF.VERIFIEDBY='"
						+ Constants.PFW_VERIFIED_BY + "'");
				whereClause.append(" AND ");

				if ((!fromDate.equals("")) && (!toDate.equals(""))) {
					try {
						whereClause.append(" AF.APPROVEDDT between to_date('"
								+ fromDate + "','dd-mm-yyyy') and to_date('"
								+ toDate + "','dd-mm-yyyy')");
					} catch (Exception e) {
						e.printStackTrace();
					}
					whereClause.append(" AND ");
				}

			} else {
				whereClause.append(" AF.VERIFIEDBY='"
						+ Constants.CPF_VERIFIED_BY + "'");
				whereClause.append(" AND ");

				if ((!fromDate.equals("")) && (!toDate.equals(""))) {
					try {
						whereClause
								.append("AF.APPROVEDDT IS NOT NULL AND  AF.APPROVEDDT between to_date('"
										+ fromDate
										+ "','dd-mm-yyyy') and to_date('"
										+ toDate + "','dd-mm-yyyy')");
					} catch (Exception e) {
						e.printStackTrace();
					}
					whereClause.append(" AND ");
				}

			}
		}

		query.append(sqlQuery);

		if (reg.equals("NO-SELECT") && advanceType.equals("NO-SELECT")
				&& station.equals("NO-SELECT") && trust.equals("NO-SELECT")
				&& fromDate.equals("") && toDate.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		orderBy = "ORDER BY AF." + sortingOrder;
		query.append(orderBy);
		dynamicQuery = query.toString();
		
		if (menuAccesFrom.trim().equals("N")){
			accessApprovedFrom="and trans.APPROVEDBY ='"+userId+"' ";
		}
		System.out.println("accessApprovedFrom======================="+accessApprovedFrom);
		sqlQuery_trans = " select result1.*  from ("+dynamicQuery+") result1,(select trans.pensionno as pensionno,  trans.cpfpfwtransid,trans.Purposetype,trans.aprvdsignname,TRANS.APPROVEDBY from EPIS_USER   users,"
		+" epis_advances_transactions trans, employee_unit_master  units where units.unitcode = users.unitcd and  USERS.USERID = trans.APPROVEDBY "+accessApprovedFrom
		+ " "+queryCond+" result2 where result1.pensionno=result2.pensionno and result1.ADVANCETRANSID=result2.cpfpfwtransid ";
		
		
		if (advanceType.equals("PFW")) {
			log.info("CPFPTWAdvanceDAO::buildadvancesReportQuery Leaving Method"+dynamicQuery);
			return dynamicQuery;
		}else{
			log.info("CPFPTWAdvanceDAO::buildadvancesReportQuery Leaving Method"+sqlQuery_trans);
			return sqlQuery_trans;
		}
		 
		
	}
	 
	//By Radha On 27-Dec-2011 for Displaying complete Advances Report
	public ArrayList summarizedAdvancesReport(String reg, String fromDate, String toDate,
			String advanceType, String purposeType, String trust, String station,String userProfile, String userUnitCode, String userId) {
		log.info("-----In DAO--summarizedAdvancesReport----");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();

		String region = "", currentDate = "", mon = "", year = "", pfid = "", transID = "", sortingOrder = "";
		long totalRequiredAmt = 0, totalActualCost = 0, totalSubContAmt = 0, totalMthInstallAmt = 0, totalIntInstallAmt = 0;
		double totalSanctionedAmt=0.0;
		
		if (advanceType.toLowerCase().equals("pfw")) {
			sortingOrder = "SANCTIONDATE";
		} else {
			if(reg.equals("NO-SELECT")){
			sortingOrder = "PI.Region ,AF.Approveddt";
			}else{
				sortingOrder =  "AF.APPROVEDDT";; 
			}
		}
		log.info("====summarizedAdvancesReport======"+reg+sortingOrder);
		String sqlQuery = this.buildSummarizedadvancesReportQuery(reg, fromDate, toDate,
				advanceType, purposeType, trust, station, sortingOrder, userProfile, userUnitCode, userId);

		log.info("CPFPTWAdvanceDAO::summarizedAdvancesReport" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {

				basicBean = new AdvanceBasicReportBean();
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}

				/*
				 * if(!fromDate.equals("")){
				 * currentDate=commonUtil.converDBToAppFormat(fromDate,"dd-MM-yyyy","dd-MMM-yyyy");
				 * log.info("=========currentDate=========="+currentDate);
				 * 
				 * String[] strArray=currentDate.split("-");
				 * 
				 * mon=strArray[1]; year=strArray[2];
				 * 
				 * log.info("======month===="+month);
				 * 
				 * if((!mon.equals("")) && (!month.equals("0"))){
				 * basicBean.setMonth(mon); }else{ basicBean.setMonth(""); }
				 * 
				 * if(!year.equals("")){ int
				 * nextyear=Integer.parseInt(year.substring(2,4))+1; String
				 * nextyr=nextyear+"";
				 * basicBean.setTransMnthYear(year+"-"+nextyr); }else{
				 * basicBean.setTransMnthYear(""); }
				 *  }
				 */
				if (rs.getString("ADVANCETRANSID") != null) {
					transID = rs.getString("ADVANCETRANSID");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("EMPLOYEENAME")));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (advanceType.toLowerCase().equals("pfw")) {
					if (rs.getString("ADVANCETRANSDT") != null) {
						basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
								.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					} else {
						basicBean.setAdvtransdt("");
					}
				} else {
					if (rs.getString("APPROVEDDT") != null) {
						basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
								.getDate("APPROVEDDT"), "dd-MMM-yyyy"));
					} else {
						basicBean.setAdvtransdt("");
					}
				}

				if (rs.getString("AIRPORTCODE") != null) {
					basicBean.setAirportcd(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("AIRPORTCODE")));
				} else {
					basicBean.setAirportcd("---");
				}

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				}

				log
						.info("Purpose Tpe is ------- "
								+ basicBean.getPurposeType());
				if (basicBean.getPurposeType().equals("HBA")) {

					basicBean = this.loadPFWHBA(transID, basicBean);

					if ((!basicBean.getActualcost().equals("0.00"))
							&& (!basicBean.getActualcost().equals(""))) {

						totalActualCost += Long.parseLong(basicBean
								.getActualcost());
						basicBean.setTotalactualcost(commonUtil
								.getCurrency(totalActualCost));
						basicBean.setActualcost(commonUtil.getCurrency(Double
								.parseDouble(basicBean.getActualcost())));
					}

				} else {
					basicBean.setActualcost("0.00");
					basicBean.setTotalactualcost(commonUtil
							.getCurrency(totalActualCost));
				}
				if (rs.getString("REGION") != null) {				  
						basicBean.setRegionLbl(rs.getString("REGION")); 
				} else {
					basicBean.setRegionLbl("");
				}
				
				
				if (rs.getString("SUBSCRIPTIONAMNT") != null
						|| rs.getString("CONTRIBUTIONAMOUNT") != null) {
					double empAmt = rs.getDouble("SUBSCRIPTIONAMNT")
							+ rs.getDouble("CONTRIBUTIONAMOUNT");
					basicBean
							.setEmplrshare(commonUtil.getCurrency(empAmt) + "");

					totalSubContAmt += empAmt;
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubContAmt)));
				} else {
					basicBean.setEmplrshare("---");
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubContAmt)));
				}

				if (rs.getString("SANCTIONDATE") != null) {
					basicBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					basicBean.setSanctiondt("---");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					basicBean.setDesignation("---");
				}

				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("---");
				}

				if (rs.getString("PURPOSETYPE") != null) {

					String purposeTypes = "";
					if (rs.getString("PURPOSETYPE").equals("COST")) {
						purposeTypes = "Cost Of Passage";
					} else if (rs.getString("PURPOSETYPE").equals("OBMARRIAGE")) {
						purposeTypes = "Marriage";
					} else if (rs.getString("PURPOSETYPE").equals("EDUCATION")) {
						purposeTypes = "Higher Education";
					} else if (rs.getString("PURPOSETYPE").equals("DEFENCE")) {
						purposeTypes = "Defense Of Court Case";
					} else if (rs.getString("PURPOSETYPE").equals("HBA")) {
						purposeTypes = "HBA";
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						purposeTypes = "Higher Education";
					} else {
						purposeTypes = commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE"));
					}

					basicBean.setPurposeType(purposeTypes);

				} else {
					basicBean.setPurposeType("---");
				}

				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setRequiredAmt(commonUtil.getCurrency(rs
							.getDouble("REQUIREDAMOUNT")));

					totalRequiredAmt += Long.parseLong(rs
							.getString("REQUIREDAMOUNT"));
					basicBean.setTotalrequiredamt(commonUtil
							.getCurrency(totalRequiredAmt));

				} else {
					basicBean.setRequiredAmt("---");
					basicBean.setTotalrequiredamt(commonUtil
							.getCurrency(totalRequiredAmt));
				}

				if (rs.getString("APPROVEDAMNT") != null) {
					basicBean.setApprovedAmt(commonUtil.getCurrency(rs
							.getDouble("APPROVEDAMNT")));

					totalSanctionedAmt += Double.parseDouble(rs
							.getString("APPROVEDAMNT"));
					basicBean.setTotalsanctionedamt(commonUtil
							.getCurrency((totalSanctionedAmt)));
				} else {
					basicBean.setApprovedAmt("---");
					basicBean.setTotalsanctionedamt(commonUtil
							.getCurrency((totalSanctionedAmt)));
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					String PurposeOptionType = "";

					if (basicBean.getPurposeType().equals("Obligatory")) {
						String[] cer = rs.getString("PURPOSEOPTIONTYPE").split(
								"-");
						PurposeOptionType = commonUtil
								.capitalizeFirstLettersTokenizer(cer[1]);
					} else {
						PurposeOptionType = commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSEOPTIONTYPE"));
					}
					basicBean.setPurposeOptionType(PurposeOptionType);
				} else {
					basicBean.setPurposeOptionType("---");
				}

				if (rs.getString("PLACEOFPOSTING") != null) {
					basicBean.setPlaceofposting(rs.getString("PLACEOFPOSTING"));
				} else {
					basicBean.setPlaceofposting("---");
				}

				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setCpfIntallments(rs
							.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setCpfIntallments("---");
				}

				if (rs.getString("MTHINSTALLMENTAMT") != null) {
					basicBean.setMthinstallmentamt(commonUtil.getCurrency(rs
							.getDouble("MTHINSTALLMENTAMT")));

					totalMthInstallAmt += Long.parseLong(rs
							.getString("MTHINSTALLMENTAMT"));
					basicBean.setTotalMthInstallAmt(commonUtil
							.getCurrency((totalMthInstallAmt)));
				} else {
					basicBean.setMthinstallmentamt("---");
					basicBean.setTotalMthInstallAmt(commonUtil
							.getCurrency((totalMthInstallAmt)));
				}

				if (rs.getString("INTERESTINSTALLMENTS") != null) {
					basicBean.setInterestinstallments(rs
							.getString("INTERESTINSTALLMENTS"));
				} else {
					basicBean.setInterestinstallments("---");
				}

				if (rs.getString("INTERESTINSTALLAMT") != null) {
					basicBean.setIntinstallmentamt(commonUtil.getCurrency(rs
							.getDouble("INTERESTINSTALLAMT")));

					totalIntInstallAmt += Long.parseLong(rs
							.getString("INTERESTINSTALLAMT"));
					basicBean.setTotalIntInstallAmt(commonUtil
							.getCurrency((totalIntInstallAmt)));
				} else {
					basicBean.setTotalIntInstallAmt("---");
					basicBean.setTotalIntInstallAmt(commonUtil
							.getCurrency((totalIntInstallAmt)));
				}
				
				if (rs.getString("NAMEINBANK") != null) {
					basicBean.setBankempname(rs.getString("NAMEINBANK"));
				}else{
					basicBean.setBankempname("---");
				}
				if (rs.getString("BANKNAME") != null) {
					basicBean.setBankname(rs.getString("BANKNAME"));
				}else{
					basicBean.setBankname("---");
				}
				if (rs.getString("SAVINGBNKACCNO") != null) {
					basicBean.setBanksavingaccno(rs
							.getString("SAVINGBNKACCNO"));
				}else{
					basicBean.setBanksavingaccno("---");
				}
				if (rs.getString("NEFTRTGSCODE") != null) {
					basicBean.setBankemprtgsneftcode(rs
							.getString("NEFTRTGSCODE"));
				}else{
					basicBean.setBankemprtgsneftcode("---");
				}
				

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
								"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
				basicBean.setPfid(pfid);

				fromDate = "";				 
				sanctionOrderList.add(basicBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return sanctionOrderList;

	}
//	By Radha on 27-Dec-2011 for Getting complete list advances/loans 
	public String buildSummarizedadvancesReportQuery(String reg, String fromDate,
			String toDate, String advanceType, String purposeType,
			String trust, String station, String sortingOrder,String userProfile, String userUnitCode, String userId) {
		log
				.info("CPFPTWAdvanceDAO::buildSummarizedadvancesReportQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "", orderBy = "",queryCond = "",sqlQuery_trans = "";
	 
		
		sqlQuery = "SELECT PI.CPFACNO AS CPFACNO,PI.EMPLOYEENO AS EMPLOYEENO,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.PENSIONNO AS PENSIONNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,AF.AIRPORTCODE AS AIRPORTCODE,AF.REGION AS REGION,"
				+ "AF.ADVANCETYPE AS ADVANCETYPE,AF.ADVANCETRANSID AS ADVANCETRANSID,AF.ADVANCETRANSDT AS ADVANCETRANSDT,AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.APPROVEDDT AS APPROVEDDT,"
				+ "AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.SANCTIONDATE AS SANCTIONDATE,AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.INTERESTINSTALLMENTS AS INTERESTINSTALLMENTS,AF.INTERESTINSTALLAMT AS INTERESTINSTALLAMT,AF.MTHINSTALLMENTAMT AS MTHINSTALLMENTAMT, "
				+ " EBI.NAME AS NAMEINBANK, EBI.BANKNAME AS BANKNAME, EBI.SAVINGBNKACCNO AS SAVINGBNKACCNO, EBI.NEFTRTGSCODE AS NEFTRTGSCODE FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF , EMPLOYEE_BANK_INFO EBI  WHERE  AF.PENSIONNO = PI.PENSIONNO  and  AF.PENSIONNO = EBI.PENSIONNO(+) and  AF.ADVANCETRANSID = EBI.Cpfpfwtransid(+)  and AF.DELETEFLAG='N' ";

		if (!advanceType.equals("NO-SELECT")) {
			whereClause.append(" AF.ADVANCETYPE='" + advanceType + "'");
			whereClause.append(" AND ");
		}

		if (!reg.equals("NO-SELECT")) {
			whereClause.append(" AF.REGION='" + reg + "'");
			whereClause.append(" AND ");
		}

		if (!station.equals("NO-SELECT")) {
			whereClause.append(" AF.AIRPORTCODE='" + station + "'");
			whereClause.append(" AND ");
		}

		if (!purposeType.equals("NO-SELECT")) {
			whereClause.append(" AF.PURPOSETYPE='" + purposeType.toUpperCase()
					+ "'");
			whereClause.append(" AND ");
		}

		if (!trust.equals("NO-SELECT")) {
			whereClause.append(" AF.TRUST='" + trust + "'");
			whereClause.append(" AND ");
		}

		if (!advanceType.equals("NO-SELECT")) {
			if (advanceType.equals("PFW")) {
				whereClause.append(" AF.VERIFIEDBY='"
						+ Constants.PFW_VERIFIED_BY + "'");
				whereClause.append(" AND ");

				if ((!fromDate.equals("")) && (!toDate.equals(""))) {
					try {
						whereClause.append(" AF.SANCTIONDATE between to_date('"
								+ fromDate + "','dd-mm-yyyy') and to_date('"
								+ toDate + "','dd-mm-yyyy')");
					} catch (Exception e) {
						e.printStackTrace();
					}
					whereClause.append(" AND ");
				}

			} else {
				whereClause.append(" AF.VERIFIEDBY='"
						+ Constants.CPF_VERIFIED_BY + "'");
				whereClause.append(" AND ");

				if ((!fromDate.equals("")) && (!toDate.equals(""))) {
					try {
						whereClause
								.append("AF.APPROVEDDT IS NOT NULL AND  AF.APPROVEDDT between to_date('"
										+ fromDate
										+ "','dd-mm-yyyy') and to_date('"
										+ toDate + "','dd-mm-yyyy')");
					} catch (Exception e) {
						e.printStackTrace();
					}
					whereClause.append(" AND ");
				}

			}
		}

		query.append(sqlQuery);

		if (reg.equals("NO-SELECT") && advanceType.equals("NO-SELECT")
				&& station.equals("NO-SELECT") && trust.equals("NO-SELECT")
				&& fromDate.equals("") && toDate.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		orderBy = "ORDER BY  " + sortingOrder;
		query.append(orderBy);
		dynamicQuery = query.toString(); 
		
	 
			log.info("CPFPTWAdvanceDAO::buildSummarizedadvancesReportQuery Leaving Method"+dynamicQuery);
			return dynamicQuery;
		 
		 
		
	}
	
	public String buildFinalPaymentRegisterQuery(String reg, String fromDate,
			String toDate, String seperationreason, String trust,
			String station, String arrearType) {
		log
				.info("CPFPTWAdvanceDAO::buildFinalPaymentRegisterQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "";

		log.info("============trust in DAO============" + trust);

		sqlQuery = "SELECT PI.CPFACNO AS CPFACNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS AIRPORTCODE,PI.REGION AS REGION,"
				+ "EN.SEPERATIONRESAON AS SEPERATIONRESAON,EN.PENSIONNO AS PENSIONNO,EN.SEPERATIONDT AS SEPERATIONDT,EN.AMTADMITTEDDT,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.ADHOCAMOUNT,EN.NSSANCTIONEDDT,EN.PAYMENTDT AS PAYMENTDT, "
				+ " PI.DATEOFBIRTH AS  DATEOFBIRTH FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM EN  WHERE EN.PENSIONNO = PI.PENSIONNO  AND DELETEFLAG='N' AND NSTYPE='"
				+ arrearType + "' ";

		// AND PI.REGION='"+reg+"' AND EN.SEPERATIONRESAON
		// ='"+seperationreason+"' AND EN.PAYMENTDT between
		// to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy') and
		// last_day(to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy'))";

		if (!reg.equals("NO-SELECT")) {
			whereClause.append(" PI.REGION='" + reg + "'");
			whereClause.append(" AND ");
		}

		if (!station.equals("NO-SELECT")) {
			whereClause.append(" PI.AIRPORTCODE='" + station + "'");
			whereClause.append(" AND ");
		}

		if (!seperationreason.equals("Select One")) {
			whereClause.append(" EN.SEPERATIONRESAON='" + seperationreason
					+ "'");
			whereClause.append(" AND ");
		}

		if (!trust.equals("Select One")) {
			whereClause.append(" EN.TRUST='" + trust + "'");
			whereClause.append(" AND ");
		}

		/*
		 * try { if (!sanctionDt.equals("")) { sanctiondt =
		 * commonUtil.converDBToAppFormat(sanctionDt, "dd/MM/yyyy",
		 * "dd-MMM-yyyy"); whereClause.append(" EN.NSSANCTIONEDDT='" +
		 * sanctiondt + "'"); whereClause.append(" AND "); } } catch (Exception
		 * e) { e.printStackTrace(); }
		 * 
		 * if ((!fromDate.equals("")) && (!toDate.equals(""))) { try {
		 * whereClause.append(" EN.NSSANCTIONEDDT between to_date('" + fromDate +
		 * "','dd-mm-yyyy') and last_day(to_date('" + toDate +
		 * "','dd-mm-yyyy'))"); } catch (Exception e) { e.printStackTrace(); }
		 * whereClause.append(" AND "); }
		 */

		if ((!fromDate.equals("")) && (!toDate.equals(""))) {
			try {
				whereClause.append(" EN.NSSANCTIONEDDT between to_date('"
						+ fromDate + "','dd-mm-yyyy') and last_day(to_date('"
						+ toDate + "','dd-mm-yyyy'))");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (reg.equals("") && seperationreason.equals("") && trust.equals("")
				&& station.equals("NO-SELECT") && fromDate.equals("")
				&& toDate.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		orderBy = "ORDER BY NSSANCTIONEDDT asc";
		query.append(orderBy);

		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildFinalPaymentRegisterQuery Leaving Method");
		return dynamicQuery;

	}

	public String getNomineeName(AdvanceBasicReportBean basicBean,
			String empName, Connection con) {
		StringBuffer buffer = new StringBuffer();
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String nominneRelation = "", nomineeName = "", totalShare = "";
		DecimalFormat df = new DecimalFormat("#########0");
		int j = 0, srno = 0, k = 0;
		boolean count = false;
		try {
			st = con.createStatement();
			/*
			 * String nomineeCount="select count(*) from employee_nominee_dtls
			 * where pensionno='"+basicBean.getPensionNo()+"'";
			 * 
			 * rs2 = st.executeQuery(nomineeCount); if (rs2.next()) {
			 * count=rs2.getInt(1); }
			 */

			String nomineeQuery = "select SRNO,initCap(NOMINEENAME) as NOMINEENAME,NOMINEERELATION,TOTALSHARE from employee_nominee_dtls where EMPFLAG='Y' and pensionno='"
					+ basicBean.getPensionNo() + "' order by SRNO desc";
			log.info("nomineeQuery------" + nomineeQuery);
			rs = st.executeQuery(nomineeQuery);
			while (rs.next()) {

				if (rs.getString("NOMINEENAME") != null) {
					nomineeName = rs.getString("NOMINEENAME");
				} else {
					nomineeName = "";
				}

				buffer.append(nomineeName);
				buffer.append(",");
			}
		} catch (Exception e) {

		}
		return buffer.toString();
	}

	public ArrayList finalPaymentRegister(String reg, String fromDate,
			String toDate, String seperationreason, String trust,
			String station, String arearType) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();

		String region = "", empName = "", pensionNo = "", nomineeNm = "", amtadmtdt = "", sepReason = "", seperationdt = "", sanctiondt = "", regionLabel = "", paymentDt = "", pfid = "";
		long totalSubAmt = 0, totalConAmt = 0, totalPenContriAmt = 0, totalNetConAmt = 0, totalAdhocAmt = 0;
		/*
		 * String sqlQuery="SELECT PI.CPFACNO AS CPFACNO,PI.EMPLOYEENAME AS
		 * EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS
		 * AIRPORTCODE,PI.REGION AS REGION,"+ "EN.SEPERATIONRESAON AS
		 * SEPERATIONRESAON,EN.SEPERATIONDT AS
		 * SEPERATIONDT,EN.AMTADMITTEDDT,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.NSSANCTIONEDDT,EN.PAYMENTDT
		 * AS PAYMENTDT "+ " FROM EMPLOYEE_PERSONAL_INFO PI,
		 * EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO = PI.PENSIONNO AND
		 * PI.REGION='"+reg+"' AND EN.SEPERATIONRESAON ='"+seperationreason+"'
		 * AND EN.PAYMENTDT between
		 * to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy') and
		 * last_day(to_date('"+01+"-"+month+"-"+year+"','dd-mm-yyyy'))";
		 */
		String sqlQuery = this.buildFinalPaymentRegisterQuery(reg, fromDate,
				toDate, seperationreason, trust, station, arearType);
		log.info("CPFPTWAdvanceDAO::finalPaymentRegister" + sqlQuery);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {

				basicBean = new AdvanceBasicReportBean();
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}

				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
					basicBean.setPensionNo(rs.getString("PENSIONNO"));
				} else {
					basicBean.setPensionNo("---");
				}

				if (rs.getString("EMPLOYEENAME") != null) {

					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("PAYMENTDT") != null) {
					paymentDt = CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MM-yy");
				}
				log.info("================PAYMENTDT=========================="
						+ paymentDt);
				if (rs.getString("AIRPORTCODE") != null) {
					basicBean.setAirportcd(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("AIRPORTCODE")));
				} else {
					basicBean.setAirportcd("---");
				}

				if (rs.getString("REGION") != null) {
					basicBean.setRegion(commonUtil.getRegion(rs
							.getString("REGION")));
				} else {
					basicBean.setRegion("---");
				}

				if (rs.getString("AMTADMITTEDDT") != null) {
					amtadmtdt = CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MM-yyyy");
				} else {
					amtadmtdt = "---";
				}

				if (rs.getString("SEPERATIONDT") != null) {
					seperationdt = CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy");
				} else {
					seperationdt = "---";
				}

				if (rs.getString("SEPERATIONRESAON") != null) {
					sepReason = rs.getString("SEPERATIONRESAON");
					basicBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));
				} else {
					basicBean.setSeperationreason("---");
				}

				if (rs.getString("SEPERATIONDT") != null) {
					basicBean.setSeperationdate(CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy"));
				} else {
					basicBean.setSeperationdate("---");
				}

				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {

					double empAmt = rs.getDouble("EMPSHARESUBSCRIPITION");
					basicBean.setEmplshare(commonUtil
							.getDecimalCurrency(empAmt));
					totalSubAmt += empAmt;
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubAmt)));
				} else {
					basicBean.setEmplshare("---");
					basicBean.setTotalsubamt(commonUtil
							.getCurrency((totalSubAmt)));
				}

				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					double empAmt = rs.getDouble("EMPSHARECONTRIBUTION");
					basicBean.setEmplrshare(commonUtil
							.getDecimalCurrency(empAmt));
					totalConAmt += empAmt;
					basicBean.setTotalConAmt(commonUtil
							.getCurrency((totalConAmt)));

				} else {
					basicBean.setEmplrshare("---");
					basicBean.setTotalConAmt(commonUtil
							.getCurrency((totalConAmt)));
				}

				if (rs.getString("LESSCONTRIBUTION") != null) {
					double empAmt = rs.getDouble("LESSCONTRIBUTION");
					basicBean.setPensioncontribution(commonUtil
							.getDecimalCurrency(empAmt));
					totalPenContriAmt += empAmt;
					basicBean.setTotalPenConAmt(commonUtil
							.getCurrency((totalPenContriAmt)));

				} else {
					basicBean.setPensioncontribution("---");
					basicBean.setTotalPenConAmt(commonUtil
							.getCurrency((totalPenContriAmt)));
				}

				if (rs.getString("NETCONTRIBUTION") != null) {
					double empAmt = rs.getDouble("NETCONTRIBUTION");
					basicBean.setNetcontribution(commonUtil
							.getDecimalCurrency(empAmt));
					totalNetConAmt += empAmt;
					basicBean.setTotalNetConAmt(commonUtil
							.getCurrency((totalNetConAmt)));

				} else {
					basicBean.setNetcontribution("---");
					basicBean.setTotalNetConAmt(commonUtil
							.getCurrency((totalNetConAmt)));
				}

				if (rs.getString("ADHOCAMOUNT") != null) {

					double empAmt = rs.getDouble("ADHOCAMOUNT");
					basicBean
							.setAdhocamt(commonUtil.getDecimalCurrency(empAmt));
					totalAdhocAmt += empAmt;
					basicBean.setTotalAdhocAmt(commonUtil
							.getCurrency((totalAdhocAmt)));

				} else {
					basicBean.setAdhocamt("0.00");
					basicBean.setTotalAdhocAmt(commonUtil
							.getCurrency((totalAdhocAmt)));
				}

				if (rs.getString("NSSANCTIONEDDT") != null) {
					// log.info("......NSSANCTIONEDDT......"+CommonUtil.getDatetoString(rs.getDate("NSSANCTIONEDDT"),
					// "dd-MM-yyyy"));
					basicBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("NSSANCTIONEDDT"), "dd-MM-yyyy"));
				} else {
					basicBean.setSanctiondt("---");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
								"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
				basicBean.setPfid(pfid);

				String nomineeName = this.getNomineeName(basicBean, empName,
						con);

				if (nomineeName.length() != 0) {
					nomineeNm = nomineeName;
				}

				log.info("%%%%%......nomineeNm......%%%%%" + nomineeNm);
				if ((!nomineeNm.equals("")) && (sepReason.equals("Death"))) {
					basicBean.setNomineename(nomineeNm.substring(0, nomineeNm
							.length() - 1));
				} else {
					basicBean.setNomineename("---");
				}
				nomineeNm = "";
				sanctionOrderList.add(basicBean);
			}
			AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();

			reportbean.setRegion(region);
			regionLabel = commonUtil.getRegionLbls(region);
			reportbean.setRegionLbl(regionLabel);
			reportbean.setAmtadmtdate(amtadmtdt);
			reportbean.setSeperationdate(seperationdt);
			reportbean.setSanctiondt(sanctiondt);
			reportbean.setSeperationreason(sepReason);
			reportbean.setSanctionList(sanctionOrderList);
			reportbean.setPaymentdate(paymentDt);
			if (!pensionNo.equals("")) {
				reportList.add(reportbean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}

	public AdvanceCPFForm2Bean advanceSanctionOrder(String pensionNo,
			String transactionID, String frmName) throws InvalidDataException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String transID = "", pfid = "", findYear = "";
		double firstInstotal=0.0,firstInsSubal=0.0,firstInsConal=0.0;
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		long cpfFund = 0;
		String purposeTye = "", sqlQuery = "", approvedAmt = "", loadElgAmnt = "", currentYear = "", lastYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "",advTransDt="",station="", region ="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "SELECT PI.PENSIONNO AS PENSIONNO,PI.CPFACNO AS CPFACNO,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,AF.TRUST AS TRUST,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.ADVANCETYPE AS ADVANCETYPE,"
					+ "AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.PARTYNAME AS PARTYNAME,AF.PARTYADDRESS AS PARTYADDRESS,AF.NARRATION AS NARRATION,AF.Firstinstallmentsubamt as Firstinstallmentsubamt,AF.Firstinstallmentcontriamt as Firstinstallmentcontriamt,"
					+ "AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.SANCTIONDATE  AS SANCTIONDATE,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,AF.PAYMENTINFO AS PAYMENTINFO, "
					+ " AF.SONO AS SANCTIONORDERNO, AF.REGION AS REGION, AF.AIRPORTCODE AS  AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF "
					+ " WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
					+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

			log
					.info("-------advanceSanctionOrder:sqlQuery-------"
							+ sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("");
				}

				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("NARRATION") != null) {
					basicBean.setNarration(rs.getString("NARRATION"));
				}
				// Getting Station,Region stored in employee_advances_form from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("ADVANCETRANSDT") != null) {
					advTransDt = commonUtil.converDBToAppFormat(rs
							.getDate("ADVANCETRANSDT"));
					Date transdate = df.parse(advTransDt); 
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
				
				if (station != null) {

					if (station.equals("IGICargo IAD")) {
						basicBean.setStation(station);
					} else {
						basicBean.setStation(commonUtil
								.capitalizeFirstLettersTokenizer(station));
					}
				} else {
					basicBean.setStation("");
				}
				if (region != null) {
					if ((region.equals("CHQNAD"))
							|| (region.equals("CHQIAD"))
							|| (region.equals("RAUSAP"))
							|| (region.equals("North-East Region"))) {
						basicBean.setRegion(commonUtil.getRegion(region));
					} else {
						basicBean.setRegion(commonUtil.getRegion(commonUtil
								.capitalizeFirstLettersTokenizer(region)));
					}

				} else {
					basicBean.setRegion("");
				}

				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {
					basicBean.setGender("");
				}
				if (rs.getString("PARTYNAME") != null) {
					basicBean.setPartyname(rs.getString("PARTYNAME"));
				} else {
					basicBean.setPartyname("---");
				}
				 
				if (region != null) {
					basicBean.setRegionlabel(commonUtil
							.getRegionLbls(commonUtil.getRegion(region).toLowerCase()));
				} else {
					basicBean.setRegionlabel("");
				}

				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {
					basicBean.setTrust("");
				}
				if (rs.getString("SANCTIONDATE") != null) {
					basicBean.setSanctiondate(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					basicBean.setSanctiondate("");
				}
				if (rs.getString("APPROVEDAMNT") != null) {
					basicBean.setAmntAproved(commonUtil.getDecimalCurrency(rs
							.getDouble("APPROVEDAMNT")));
					basicBean.setAdvanceApprovedCurr(commonUtil
							.ConvertInWords(Double.parseDouble(rs
									.getString("APPROVEDAMNT"))));
				} else {
					basicBean.setAmntAproved("");
				}

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("PURPOSETYPE")));
				} else {
					basicBean.setPurposeType("---");
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {

					if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"RENOVATIONHOUSE")) {
						basicBean.setPurposeOptionType("Renovation House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"PURCHASEHOUSE")) {
						basicBean.setPurposeOptionType("Purchase House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"CONSTRUCTIONHOUSE")) {
						basicBean.setPurposeOptionType("Construction House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"ACQUIREFLAT")) {
						basicBean.setPurposeOptionType("Acquiring Flat");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"PURCHASESITE")) {
						basicBean.setPurposeOptionType("Purchase Site");
					} else {
						basicBean.setPurposeOptionType(rs
								.getString("PURPOSEOPTIONTYPE"));
					}
				} else {
					basicBean.setPurposeOptionType("");
				}

				if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
					basicBean.setPrpsecvrdclse(rs
							.getString("PURPOSEOPTIONCVRDCLUSE"));
				} else {
					basicBean.setPrpsecvrdclse("");
				}

				if (rs.getString("SUBSCRIPTIONAMNT") != null) {

					if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION") ) {
						basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
								.getDouble("APPROVEDSUBSCRIPTIONAMT")));
					} else {
						basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
								.getDouble("SUBSCRIPTIONAMNT")));
					}

				} else {
					basicBean.setSubscriptionAmt("");
				}

				if (rs.getString("CONTRIBUTIONAMOUNT") != null) {

					if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION")) {
						basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
								.getDouble("APPROVEDCONTRIBUTIONAMT")));
					} else {
						basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
								.getDouble("CONTRIBUTIONAMOUNT")));
					}

				} else {
					basicBean.setContributionAmt("");
				}
				//new fields added by prasad on 03-Jun-2013
				if(rs.getString("Firstinstallmentsubamt")!= null){
					basicBean.setFirstInsSubAmnt(commonUtil.getDecimalCurrency(rs.getDouble("Firstinstallmentsubamt")));
					firstInsSubal=rs.getDouble("Firstinstallmentsubamt");
				}else{
					firstInsSubal=0.0;
					basicBean.setFirstInsSubAmnt("0.0");
				}
				if(rs.getString("Firstinstallmentcontriamt")!= null){
					basicBean.setFirstInsConrtiAmnt(commonUtil.getDecimalCurrency(rs.getDouble("Firstinstallmentcontriamt")));
					firstInsConal=rs.getDouble("Firstinstallmentcontriamt");
				}else{
					firstInsConal=0.0;
					basicBean.setFirstInsConrtiAmnt("0.0");
				}
				//log.info("basicBean.setFirstInsConrtiAmnt"+basicBean.getFirstInsConrtiAmnt()+"basicBean.setFirstInsSubAmnt"+basicBean.getFirstInsSubAmnt());
				//firstInstotal=Double.parseDouble(basicBean.getFirstInsSubAmnt())+Double.parseDouble(basicBean.getFirstInsConrtiAmnt());
				firstInstotal=firstInsConal+firstInsSubal;
				basicBean.setFirstInsTotAmnt(commonUtil.getDecimalCurrency(firstInstotal));
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				}
				if (rs.getString("SANCTIONORDERNO") != null) {
					basicBean.setPfwSanctionOrderNo(commonUtil.leadingZeros(5,
							rs.getString("SANCTIONORDERNO")));
				} else {
					basicBean.setPfwSanctionOrderNo("---");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
								"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
				basicBean.setPfid(pfid);

				basicBean.setPensionNo(pensionNo);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return basicBean;
	}

	// ----------------------------------End :
	// advanceSanctionOrder()--------------------------------------------------
	/*
	 * public int updateCPFVerification(AdvanceCPFForm2Bean advanceBean) {
	 * Connection con = null; Statement st = null; Statement insertSt = null;
	 * int updatedRecords = 0; double emolument=0.0; String updateQuery = "";
	 * String purposeType = ""; log.info("updateCPFVerification::Basic Info" +
	 * advanceBean.getPensionNo());
	 * 
	 * try { con = commonDB.getConnection(); st = con.createStatement();
	 * insertSt = con.createStatement();
	 * if(advanceBean.getPurposeType().equals("OBMARRIAGE"))
	 * emolument=Double.parseDouble(advanceBean.getEmoluments())/6; else
	 * emolument=Double.parseDouble(advanceBean.getEmoluments())/3;
	 * 
	 * String fmlyDOB = "";
	 * 
	 * log.info(advanceBean.getPensionNo());
	 * log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());
	 * updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET
	 * PURPOSEOPTIONCVRDCLUSE='" + advanceBean.getPrpsecvrdclse() +
	 * "',ADVANCETRANSSTATUS='" + advanceBean.getAuthrizedStatus() +
	 * "',SUBSCRIPTIONAMNT='" + advanceBean.getEmpshare() +
	 * "',TRNASMNTHEMOLUMENTS='" + emolument + "',MTHINSTALLMENTAMT='" +
	 * advanceBean.getMthinstallmentamt() + "',INTERESTINSTALLMENTS='" +
	 * advanceBean.getInterestinstallments() + "',INTERESTINSTALLAMT='" +
	 * advanceBean.getIntinstallmentamt() + "',PREVIOUSOUTSTANDINGAMT='" +
	 * advanceBean.getOutstndamount() + "',RECOMMENDEDAMT='" +
	 * advanceBean.getAmntRecommended() + "',VERIFIEDBY='PERSONNEL' WHERE
	 * ADVANCETRANSID='" + advanceBean.getAdvanceTransID() + "' AND PENSIONNO='" +
	 * advanceBean.getPensionNo() + "'";
	 * log.info("CPFPTWAdvanceDAO::updateCPFVerification" + updateQuery);
	 * updatedRecords = st.executeUpdate(updateQuery);
	 * 
	 * 
	 * 
	 * CPFPFWTransInfo cpfInfo=new
	 * CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());
	 * cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),advanceBean.getAdvanceTransID(),"CPF",Constants.APPLICATION_PROCESSING_CPF_VERFICATION);
	 *  } catch (SQLException e) { log.printStackTrace(e); } catch (Exception e) {
	 * log.printStackTrace(e); } finally { commonDB.closeConnection(null, st,
	 * con); } return updatedRecords; }
	 */
//	Modified By Radha On 14-Feb-2012 for saving  last month instalment Amount
	// Replace the complete method on 28-Jul-2010.above commented method before
	// replacing
	
	//	Modified By Radha On 20-Jul-2011 for saving designation in transaction table
	public int updateCPFVerification(AdvanceCPFForm2Bean advanceBean) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		double emolument = 0.0;
		String updateQuery = "";
		String purposeType = "";
		log.info("updateCPFVerification::Basic Info"
				+ advanceBean.getPensionNo());
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			if (advanceBean.getPurposeType().equals("OBMARRIAGE"))
				emolument = Double.parseDouble(advanceBean.getEmoluments()) / 6;
			else
				emolument = Double.parseDouble(advanceBean.getEmoluments()) / 3;

			String fmlyDOB = "";

			log.info(advanceBean.getPensionNo());
			log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET PURPOSEOPTIONCVRDCLUSE='"
					+ advanceBean.getPrpsecvrdclse()
					+ "',ADVANCETRANSSTATUS='"
					+ advanceBean.getAuthrizedStatus()
					+ "',SUBSCRIPTIONAMNT='"
					+ advanceBean.getEmpshare()
					+ "',TRNASMNTHEMOLUMENTS='"
					+ emolument
					+ "',MTHINSTALLMENTAMT='"
					+ advanceBean.getMthinstallmentamt()
					+ "',INTERESTINSTALLMENTS='"
					+ advanceBean.getInterestinstallments()
					+ "',INTERESTINSTALLAMT='"
					+ advanceBean.getIntinstallmentamt()
					+ "',PREVIOUSOUTSTANDINGAMT='"
					+ advanceBean.getOutstndamount()
					+ "',RECOMMENDEDAMT='"
					+ advanceBean.getAmntRecommended()
					+ "',LASTMTHINSTALLMENTAMT='"
					+ advanceBean.getLastmthinstallmentamt()
					+ "',VERIFIEDBY='PERSONNEL' WHERE ADVANCETRANSID='"
					+ advanceBean.getAdvanceTransID()
					+ "' AND PENSIONNO='"
					+ advanceBean.getPensionNo() + "'";
			log.info("CPFPTWAdvanceDAO::updateCPFVerification" + updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			BeanUtils.copyProperties(transBean, advanceBean);

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
					.getLoginUserId(), advanceBean.getLoginUserName(),
					advanceBean.getLoginUnitCode(), advanceBean
							.getLoginRegion(), advanceBean
							.getLoginUserDispName());
			cpfInfo
					.createCPFPFWTrans(advanceBean.getPensionNo(), advanceBean
							.getAdvanceTransID(), advanceBean.getLoginUserDesignation() ,"CPF",
							Constants.APPLICATION_PROCESSING_CPF_VERFICATION,
							transBean);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}
	//BY Radha On 07-May-2012 for Getting Designation of the Employee
	public AdvanceEditBean loadAdvancesEditInfo(String transID, String PensionNo) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceEditBean advanceEditBean = null;
		ArrayList loadNomineeList = new ArrayList();
		String sqlQuery = "", remarks = "";
		sqlQuery = "select ADVANCETYPE,PURPOSETYPE,PURPOSEOPTIONTYPE,REQUIREDAMOUNT,ADVANCETRANSSTATUS,REASON,PARTYNAME,PARTYADDRESS,"
				+ "LOD,CHKWTHDRWL,TRNASMNTHEMOLUMENTS,APPROVEDAMNT,APPROVEDREMARKS,APPROVEDDT,PURPOSEOPTIONCVRDCLUSE,"
				+ "TOTALINATALLMENTS,SUBSCRIPTIONAMNT,UTLISIEDAMNTDRWN,ADVNCERQDDEPEND,VERIFIEDBY,CONTRIBUTIONAMOUNT,"
				+ "CPFACCFUND,PREVIOUSOUTSTANDINGAMT,LOANTAKEN,TRUST,FINALTRANSSTATUS,PAYMENTINFO,NTIMESTRNASMNTHEMOLUMENTS,"
				+ "SANCTIONDATE,MTHINSTALLMENTAMT,PLACEOFPOSTING,ADVANCETRANSDT,DESIGNATION, AIRPORTCODE, REGION from employee_advances_form where DELETEFLAG='N' and  ADVANCETRANSID='"
				+ transID + "'";
		System.out.println(sqlQuery);
		log.info("CommonDAO::loadAdvancesEditInfo" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				advanceEditBean = new AdvanceEditBean();

				advanceEditBean.setAdvanceTransID(transID);
				advanceEditBean.setPensionNo(PensionNo);
				if (rs.getString("ADVANCETYPE") != null) {
					advanceEditBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					advanceEditBean.setAdvanceType("");
				}

				if (rs.getString("ADVANCETYPE").equals("PFW")) {
					if (rs.getString("PURPOSETYPE") != null) {
						advanceEditBean.setPfwPurpose(rs
								.getString("PURPOSETYPE"));
					} else {
						advanceEditBean.setPfwPurpose("");
					}
				}

				if (rs.getString("ADVANCETYPE").equals("CPF")) {
					if (rs.getString("PURPOSETYPE") != null) {
						advanceEditBean.setAdvPurpose(rs
								.getString("PURPOSETYPE"));
					} else {
						advanceEditBean.setAdvPurpose("");
					}
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					if (rs.getString("PURPOSETYPE").equals("OBLIGATORY")) {

						String[] cer = rs.getString("PURPOSEOPTIONTYPE").split(
								"-");
						advanceEditBean.setPurposeOptionType(commonUtil
								.capitalizeFirstLettersTokenizer(cer[1]));

					} else {
						advanceEditBean.setPurposeOptionType(rs
								.getString("PURPOSEOPTIONTYPE"));
					}
				} else {
					advanceEditBean.setPurposeOptionType("");
				}

				if (rs.getString("REQUIREDAMOUNT") != null) {
					advanceEditBean.setAdvReqAmnt(rs
							.getString("REQUIREDAMOUNT"));
				} else {
					advanceEditBean.setAdvReqAmnt("");
				}
				if (rs.getString("ADVANCETRANSDT") != null) {
					advanceEditBean.setCreatedDate(commonUtil
							.converDBToAppFormat(rs.getDate("ADVANCETRANSDT")));
				} else {
					advanceEditBean.setCreatedDate(commonUtil
							.getCurrentDate("dd-MMM-yyyy"));
				}
				log
						.info("loadAdvancesEditInfo==Created Date========================="
								+ advanceEditBean.getCreatedDate());
				if (rs.getString("TRUST") != null) {
					advanceEditBean.setTrust(rs.getString("TRUST"));
				} else {
					advanceEditBean.setTrust("");
				}

				if (rs.getString("TRNASMNTHEMOLUMENTS") != null) {
					advanceEditBean.setEmoluments(rs
							.getString("TRNASMNTHEMOLUMENTS"));
				} else {
					advanceEditBean.setEmoluments("");
				}

				if (rs.getString("TOTALINATALLMENTS") != null) {
					advanceEditBean.setCpftotalinstall(rs
							.getString("TOTALINATALLMENTS"));
				} else {
					advanceEditBean.setCpftotalinstall("");
				}

				if (rs.getString("PAYMENTINFO") != null) {
					advanceEditBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					advanceEditBean.setPaymentinfo("");
				}

				if (rs.getString("LOD") != null) {
					advanceEditBean.setLodInfo(rs.getString("LOD"));
				} else {
					advanceEditBean.setLodInfo("");
				}

				if (rs.getString("REASON") != null) {
					advanceEditBean.setAdvReasonText(rs.getString("REASON"));
				} else {
					advanceEditBean.setAdvReasonText("");
				}

				if (rs.getString("PARTYNAME") != null) {
					advanceEditBean.setModeofpartyname(rs
							.getString("PARTYNAME"));
				} else {
					advanceEditBean.setModeofpartyname("");
				}

				if (rs.getString("PARTYADDRESS") != null) {
					advanceEditBean.setModeofpartyaddrs(rs
							.getString("PARTYADDRESS"));
				} else {
					advanceEditBean.setModeofpartyaddrs("");
				}

				if (rs.getString("CHKWTHDRWL") != null) {
					advanceEditBean.setChkwthdrwlinfo(rs
							.getString("CHKWTHDRWL").trim());
				} else {
					advanceEditBean.setChkwthdrwlinfo("");
				}

				if (rs.getString("PLACEOFPOSTING") != null) {
					advanceEditBean.setPlaceofposting(rs.getString(
							"PLACEOFPOSTING").trim());
				} else {
					advanceEditBean.setPlaceofposting("");
				}

				if (rs.getString("ADVNCERQDDEPEND") != null) {
					advanceEditBean.setAdvncerqddepend(rs.getString(
							"ADVNCERQDDEPEND").trim());
				} else {
					advanceEditBean.setAdvncerqddepend("");
				}

				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					advanceEditBean.setUtlisiedamntdrwn(rs.getString(
							"UTLISIEDAMNTDRWN").trim());
				} else {
					advanceEditBean.setUtlisiedamntdrwn("");
				}
				if (rs.getString("DESIGNATION") != null) {
					advanceEditBean.setDesignation(rs.getString(
							"DESIGNATION").trim());
				} else {
					advanceEditBean.setDesignation("");
				}
				if (rs.getString("AIRPORTCODE") != null) {
					advanceEditBean.setStation(rs.getString(
							"AIRPORTCODE").trim());
				} else {
					advanceEditBean.setStation("");
				}
				if (rs.getString("REGION") != null) {
					advanceEditBean.setRegion(rs.getString(
							"REGION").trim());
				} else {
					advanceEditBean.setRegion("");
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return advanceEditBean;
	}

	// 19-Aug-2010 Radha update reason in employee_advance_hba_info from edit

	public ArrayList updateAdvacneInfo(AdvanceEditBean advanceBean,
			EmpBankMaster bankBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;

		ArrayList advanceList = new ArrayList();
		ArrayList wthdrwList = new ArrayList();
		int insertedRecords = 0, updatedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", advanceType = "", insertHBAQuery = "", insertHEQuery = "";
		String purposeType = "", wthDrwlTrnsdt = "", marriageDate = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			AdvanceBasicReportBean reportBean = new AdvanceBasicReportBean();
			AdvanceBasicReportBean reportBean1 = new AdvanceBasicReportBean();
			AdvanceBasicReportBean reportBean2 = new AdvanceBasicReportBean();
			EmpBankMaster empBankMasterBean = new EmpBankMaster();
			insertSt = con.createStatement();
			// advanceTransID = this.getAdvanceSequence(con);

			advanceType = advanceBean.getAdvanceType();
			purposeType = advanceBean.getPurposeType();
			String transID = "", fmlyDOB = "", updateQuery = "", selectHBAQuery = "", selectHEQuery = "", selectwthdrwQuery = "";

			transID = advanceBean.getAdvanceTransID();

			if (advanceBean.getAdvanceType().equals("CPF"))
				purposeType = advanceBean.getAdvPurpose();
			else
				purposeType = advanceBean.getPfwPurpose();

			if (!purposeType.equals("LIC")) {

				if (purposeType.equals("HBA")) {
					reportBean = this.loadPFWHBA(transID, reportBean);
				} else if (purposeType.equals("MARRIAGE")) {
					reportBean = this.loadPFWMarriage(transID, reportBean);
				} else if (purposeType.equals("HE")
						|| purposeType.equals("EDUCATION")) {
					reportBean = this.loadPFWHE(transID, reportBean);
					reportBean2 = this.loadPFWMarriage(transID, reportBean);
				} else {
					reportBean = this.loadPFWMarriage(transID, reportBean);
				}

				if (advanceBean.getPaymentinfo().equals("Y")) {
					empBankMasterBean = (EmpBankMaster) this
							.loadEmployeeBankInfo(advanceBean.getPensionNo(),transID);
				}

				if (advanceBean.getChkwthdrwlinfo().trim().equals("Y")) {
					wthdrwList = (ArrayList) this.loadWithDrawalDetails(
							advanceBean.getAdvanceTransID(), reportBean1, con);
				}

				advanceList.add(reportBean);
				advanceList.add(empBankMasterBean);
				advanceList.add(wthdrwList);
				advanceList.add(reportBean2);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return advanceList;
	}
	//	By Radha on 01-Jun-2012 for update purpose Type
	//	to Avoid Single Cote in Text
	public String updateAdvanceNextInfo(AdvanceEditBean advanceBean,
			EmpBankMaster bankBean, String wthDrwFlag) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", advanceType = "", insertHBAQuery = "", insertHEQuery = "";
		String purposeType = "", wthDrwlTrnsdt = "", marriageDate = "",purposeOptionType="";
		String updateQuery = "", updateHBAQuery = "", updateBankInfoQuery = "", updateMarriageQuery = "", updateHEQuery = "", updateFmlyDetQuery = "";
		int updatedRecords = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			// advanceTransID = this.getAdvanceSequence(con);
			advanceType = advanceBean.getAdvanceType();
			purposeType = advanceBean.getPurposeType();
			String fmlyDOB = "";
			if (!advanceBean.getFmlyDOB().trim().equals("")) {
				fmlyDOB = commonUtil.converDBToAppFormat(advanceBean
						.getFmlyDOB(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getWthDrwlTrnsdt().trim().equals("")) {
				wthDrwlTrnsdt = commonUtil.converDBToAppFormat(advanceBean
						.getWthDrwlTrnsdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getMarriagedate().trim().equals("")) {
				marriageDate = commonUtil.converDBToAppFormat(advanceBean
						.getMarriagedate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (advanceBean.getAdvanceType().toLowerCase().equals("cpf")) {
				if (advanceBean.getChkwthdrwlinfo().equals("")) {
					advanceBean.setChkwthdrwlinfo("N");
				}
			}
			//  For restricting the updation of personal Information Frm  25-Apr-2012
			//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
			/*if ((!advanceBean.getDepartment().equals(""))
					|| (!advanceBean.getDesignation().equals(""))) {
				String updateQry = "update employee_personal_info set DESEGNATION='"
						+ advanceBean.getDesignation()
						+ "', DEPARTMENT='"
						+ advanceBean.getDepartment() 
						+ "',AIRPORTCODE='"
						+ advanceBean.getStation()
						+ "',REGION='"
						+ advanceBean.getRegion()
						+ "' where  PENSIONNO='"
						+ advanceBean.getPensionNo() + "'";
				log.info("==========update Query===========" + updateQry);
				int updatedRecord = st.executeUpdate(updateQry);
			}*/

			log.info("------wthDrwFlag  in DAO--------" + wthDrwFlag
					+ "advanceBean.getCreatedDate()==="
					+ advanceBean.getCreatedDate());
			if (wthDrwFlag.equals("YES")) {
				this.updateWithDrawalDetails(advanceBean.getAdvanceTransID(),
						con, advanceBean);
			}
			purposeOptionType = commonUtil.replaceAllWords2(advanceBean.getPurposeOptionType().toUpperCase().trim()
					 , "'","");
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET PURPOSETYPE='"
					+ advanceBean.getPurposeType()
					+ "',USERPURPOSETYPE='"
					+ advanceBean.getPurposeType()
					+ "',PURPOSEOPTIONTYPE='"
					+ purposeOptionType
					+ "',LOD='"
					+ advanceBean.getLodInfo()
					+ "',USERLOD='"
					+ advanceBean.getLodInfo()
					+ "',PARTYNAME='"
					+ advanceBean.getPartyName()
					+ "',PARTYADDRESS='"
					+ advanceBean.getPartyAddress()
					+ "',REQUIREDAMOUNT='"
					+ advanceBean.getAdvReqAmnt()
					+ "',USERREQUIREDAMOUNT='"
					+ advanceBean.getAdvReqAmnt()
					+ "',TRUST='"
					+ advanceBean.getTrust()
					+ "',TRNASMNTHEMOLUMENTS='"
					+ advanceBean.getEmoluments()
					+ "',USERTRNASMNTHEMOLUMENTS='"
					+ advanceBean.getEmoluments()
					+ "',PAYMENTINFO='"
					+ advanceBean.getPaymentinfo()
					+ "',CHKWTHDRWL='"
					+ advanceBean.getChkwthdrwlinfo()
					+ "',ADVANCETRANSDT='"
					+ advanceBean.getCreatedDate()
					+ "',USERCHKWTHDRWL='"
					+ advanceBean.getChkwthdrwlinfo()
					+ "',PLACEOFPOSTING='"
					+ advanceBean.getPlaceofposting()
					+ "',ADVNCERQDDEPEND='"
					+ advanceBean.getAdvncerqddepend()
					+ "',UTLISIEDAMNTDRWN='"
					+ advanceBean.getUtlisiedamntdrwn()
					+ "',REASON='"
					+ advanceBean.getAdvReasonText()
					+ "',TOTALINATALLMENTS='"
					+ advanceBean.getCpftotalinstall()
					+ "',USERTOTALINATALLMENTS='"
					+ advanceBean.getCpftotalinstall()
					+ "',AIRPORTCODE='"
					+ advanceBean.getStation()
					+ "',REGION='"
					+ advanceBean.getRegion()
					+ "',DESIGNATION='"
					+ advanceBean.getDesignation()
					+ "' WHERE ADVANCETRANSID='"
					+ advanceBean.getAdvanceTransID()
					+ "' AND PENSIONNO='"
					+ advanceBean.getPensionNo() + "'";
			log
					.info("CPFPTWAdvanceDAO::updateAdvanceNextInfo----updateQuery------"
							+ updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			if (advanceBean.getAdvanceType().equals("PFW")
					&& advanceBean.getPurposeType().equals("HBA")) {

				updateHBAQuery = "UPDATE EMPLOYEE_ADVANCE_HBA_INFO SET PROPERTYADDRESS='"
						+ advanceBean.getPropertyaddress()
						+ "',ACTUALCOST='"
						+ advanceBean.getActualcost()
						+ "',NAME='"
						+ advanceBean.getHbaownername()
						+ "',ADDRESS='"
						+ advanceBean.getHbaowneraddress()
						+ "',AREA='"
						+ advanceBean.getHbaownerarea()
						+ "',PLOTNO='"
						+ advanceBean.getHbaownerplotno()
						+ "',LOCALITY='"
						+ advanceBean.getHbaownerlocality()
						+ "',MUNICIPALITY='"
						+ advanceBean.getHbaownermuncipal()
						+ "',CITY='"
						+ advanceBean.getHbaownercity()
						+ "',HBADRWNFRMAAI='"
						+ advanceBean.getHbadrwnfrmaai()
						+ "',USERHBADRWNFRMAAI='"
						+ advanceBean.getHbadrwnfrmaai()
						+ "',PERMISSIONAAI='"
						+ advanceBean.getHbapermissionaai()
						+ "',HBADRWNFRMAAIPURPOSE='"
						+ advanceBean.getHbawthdrwlpurpose()
						+ "',HBADRWNFRMAAIAMOUNT='"
						+ advanceBean.getHbawthdrwlamount()
						+ "',HBADRWNFRMAAIADDRESS='"
						+ advanceBean.getHbawthdrwladdress()
						+ "',REPAYMENTNAME='"
						+ advanceBean.getHbaloanname()
						+ "',REPAYMENTADDRESS='"
						+ advanceBean.getHbaloanaddress()
						+ "',REPAYMENTAMOUNT='"
						+ advanceBean.getOsamountwithinterest()
						+ "',REPAYMENTLOANTYPE='"
						+ advanceBean.getHbarepaymenttype()
						+ "',HBAOTHERS='"
						+ advanceBean.getAdvReasonText()
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID() + "'";

				log
						.info("CPFPTWAdvanceDAO::updateAdvanceNextInfo---updateHBAQuery-----"
								+ updateHBAQuery);
				updatedRecords = st.executeUpdate(updateHBAQuery);
			}

			if (!advanceBean.getAdvanceType().equals("")
					&& (advanceBean.getPurposeType().equals("MARRIAGE") || advanceBean
							.getPurposeType().equals("OBMARRIAGE"))) {

				updateMarriageQuery = "UPDATE EMPLOYEE_ADVANCES_FAMILY_DTLS SET NAME='"
						+ advanceBean.getFmlyEmpName()
						+ "',AGE='"
						+ advanceBean.getFmlyAge()
						+ "',DATEOFBIRTH='"
						+ fmlyDOB
						+ "',AGELOD='"
						+ advanceBean.getBrthCertProve()
						+ "',MARRIAGEDATE='"
						+ marriageDate
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID() + "'";

				log
						.info("CPFPTWAdvanceDAO::updateAdvanceNextInfo---updateMarriageQuery-----"
								+ updateMarriageQuery);
				updatedRecords = st.executeUpdate(updateMarriageQuery);
			}

			if (advanceBean.getAdvanceType().equals("CPF")
					&& (advanceBean.getPurposeType().equals("COST")
							|| advanceBean.getPurposeType().equals("ILLNESS") || advanceBean
							.getPurposeType().equals("EDUCATION"))) {

				updateFmlyDetQuery = "UPDATE EMPLOYEE_ADVANCES_FAMILY_DTLS SET NAME='"
						+ advanceBean.getFmlyEmpName()
						+ "',AGE='"
						+ advanceBean.getFmlyAge()
						+ "',DATEOFBIRTH='"
						+ fmlyDOB
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID() + "'";

				log
						.info("CPFPTWAdvanceDAO::updateAdvanceNextInfo---updateFmlyDetQuery-----"
								+ updateFmlyDetQuery);
				updatedRecords = st.executeUpdate(updateFmlyDetQuery);
			}

			if (!advanceBean.getAdvanceType().equals("")
					&& (advanceBean.getPurposeType().equals("HE") || advanceBean
							.getPurposeType().equals("EDUCATION"))) {

				updateHEQuery = "UPDATE EMPLOYEE_ADVANCES_HE_INFO SET NMCOURSE='"
						+ advanceBean.getNmCourse()
						+ "',NMINSTITUTION='"
						+ advanceBean.getNmInstitue()
						+ "',INSTITUTIONADDRSS='"
						+ advanceBean.getAddrInstitue()
						+ "',DURATIONCOURSE='"
						+ advanceBean.getCurseDuration()
						+ "',LASTEXAMPASSED='"
						+ advanceBean.getHeLastExaminfo()
						+ "',RECOGNIZED='"
						+ advanceBean.getHeRecog()
						+ "' WHERE ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID() + "'";

				log
						.info("CPFPTWAdvanceDAO::updateAdvanceNextInfo---updateHEQuery-----"
								+ updateHEQuery);
				updatedRecords = st.executeUpdate(updateHEQuery);
			}

			log.info("-----advanceBean.getPaymentinfo()--------"
					+ advanceBean.getPaymentinfo());

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public void updateWithDrawalDetails(String advanceTransID, Connection con,
			AdvanceEditBean advanceBean) {

		Statement st = null;
		ResultSet rs = null;
		int insertedwthdrwRecords = 0;
		int updatedRecords = 0;
		String wthDrwlTrnsdt = "", wthDrwlStatus = "Y", insertWithdrawalQuery = "", updateWthDrwQuery = "";
		String wthdrwPurpose = "", wthdrwAmount = "", wthdrwDate = "", wthdrwid = "", wthdrwstatus = "", deleteId = "";
		try {
			st = con.createStatement();
			String estr = advanceBean.getWthdrwlist();

			log.info("====estr===" + estr.lastIndexOf(":"));

			if (estr.lastIndexOf(":") != -1) {
				StringTokenizer est = new StringTokenizer(estr, ":");

				int lengt = est.countTokens();
				String estrarr[] = new String[lengt];

				for (int e = 0; est.hasMoreTokens(); e++) {

					estrarr[e] = est.nextToken();
					String expsplit = estrarr[e];

					String[] strArr = expsplit.split("#");
					for (int ii = 0; ii < strArr.length; ii++) {
						wthdrwPurpose = strArr[0];
						wthdrwAmount = strArr[1];
						wthdrwDate = strArr[2];
						wthdrwid = strArr[3];
						wthdrwstatus = strArr[4];
						if (ii == 5)
							deleteId = strArr[5];
					}

					if (!wthdrwDate.trim().equals("")) {
						wthDrwlTrnsdt = commonUtil.converDBToAppFormat(
								wthdrwDate, "dd/MM/yyyy", "dd-MMM-yyyy");
					}
					if (wthdrwPurpose.equals("-")) {
						wthdrwPurpose = "";
					}

					if (wthdrwstatus.equals("N")) {
						insertWithdrawalQuery = "INSERT INTO employee_advances_wthdrwl_info(ADVANCETRANSID,WTHDRWLID,WTHDRWLPURPOSE,WTHDRWLAMOUNT,WTHDRWLTRNSDT,WTHDRWLSTATUS) VALUES("
								+ Long.parseLong(advanceTransID)
								+ ","
								+ wthdrwid
								+ ",'"
								+ wthdrwPurpose
								+ "','"
								+ wthdrwAmount
								+ "','"
								+ wthDrwlTrnsdt
								+ "','"
								+ wthDrwlStatus + "')";

						log.info("------insertWithdrawalQuery------"
								+ insertWithdrawalQuery);
						insertedwthdrwRecords = st
								.executeUpdate(insertWithdrawalQuery);
					} else if (wthdrwstatus.equals("M")) {
						updateWthDrwQuery = "update employee_advances_wthdrwl_info";

						updateWthDrwQuery = "UPDATE employee_advances_wthdrwl_info SET WTHDRWLPURPOSE='"
								+ wthdrwPurpose
								+ "',WTHDRWLAMOUNT='"
								+ wthdrwAmount
								+ "',WTHDRWLTRNSDT='"
								+ wthDrwlTrnsdt
								+ "' WHERE ADVANCETRANSID='"
								+ advanceBean.getAdvanceTransID()
								+ "' and WTHDRWLID='" + wthdrwid + "'";
						log.info("------updateWthDrwQuery------"
								+ updateWthDrwQuery);
						updatedRecords = st.executeUpdate(updateWthDrwQuery);

					}
				}
			}

			if ((!deleteId.equals("")) || (estr.lastIndexOf(":") == -1)) {

				String delQuery = "delete from employee_advances_wthdrwl_info where ADVANCETRANSID='"
						+ advanceBean.getAdvanceTransID() + "'";

				if (!deleteId.equals("")) {
					delQuery += " and  WTHDRWLID in(" + deleteId + ")";
				}
				log.info("======delQuery======" + delQuery);
				updatedRecords = st.executeUpdate(delQuery);
				deleteId = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getWithDrawalDetails(String transID, String chkWthDrawInfo) {

		Connection con = null;
		Statement st = null;
		Statement insertSt = null;

		ArrayList advanceList = new ArrayList();
		ArrayList wthdrwList = new ArrayList();
		int insertedRecords = 0, updatedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", advanceType = "", insertHBAQuery = "", insertHEQuery = "";
		String purposeType = "", wthDrwlTrnsdt = "", marriageDate = "", wthdrwStr = "", str = "";

		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			log.info("------transID-------" + transID);
			log.info("------chkWthDrawInfo-------" + chkWthDrawInfo);

			if (chkWthDrawInfo.equals("Y")) {
				// wthdrwList=(ArrayList)this.loadWithDrawalDetails(transID,advanceReportBean,con);
				wthdrwStr = this.getWithDrawalDetails(transID,
						advanceReportBean, con);
			}

			log.info("-------wthdrwStr DAO--------" + wthdrwStr);

			if (!wthdrwStr.equals("")) {
				str = wthdrwStr.substring(0, wthdrwStr.length() - 1);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return str;
	}

	public ArrayList getNomineeDets(AdvanceBasicReportBean basicBean,
			String empName, String netContAmt, Connection con) {
		StringBuffer buffer = new StringBuffer();

		AdvanceBasicBean advanceBasicBean = null;
		ArrayList nomineeList = new ArrayList();

		Statement st = null;
		ResultSet rs = null;
		String nominneRelation = "", nomineeName = "", nomineeNames = "", totalShare = "", nominneRel = "";

		int k = 0, nomineeCount = 1;

		String totShare = "", nomineeNameStr = "", nomineeRelationStr = "";
		String roundShare = "", decimalVal = "";
		boolean count = false;
		boolean countFlag = false;
		try {
			st = con.createStatement();

			String nomineeQuery = "select SRNO,NOMINEENAME,NOMINEERELATION,TOTALSHARE from employee_nominee_dtls  where EMPFLAG='Y' and  pensionno='"
					+ basicBean.getPensionNo() + "' order by SRNO desc";

			log.info("----nomineeQuery-----" + nomineeQuery);
			rs = st.executeQuery(nomineeQuery);

			while (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();

				if (rs.getString("NOMINEENAME") != null) {
					advanceBasicBean
							.setNomineename(rs.getString("NOMINEENAME"));
				}
				if (rs.getString("NOMINEERELATION") != null) {
					advanceBasicBean.setNomineerelation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("NOMINEERELATION")));
				}

				if (!netContAmt.equals("")) {
					if (rs.getString("TOTALSHARE") != null) {

						double share = (Double.parseDouble(netContAmt) * Double
								.parseDouble(rs.getString("TOTALSHARE"))) / 100;

						String decimals = "";
						decimals = share + "";
						int index1 = decimals.indexOf(".");
						decimalVal = decimals.substring(
								decimals.indexOf(".") + 1, decimals.length());

						if (decimalVal.equals("0")) {
							roundShare = decimals.substring(0, index1)+"/-";
							advanceBasicBean.setTotalshare(roundShare);
						} else {
							advanceBasicBean.setTotalshare(commonUtil
									.getDecimalCurrency(share));

						}

						log.info("------shar value----------"
								+ advanceBasicBean.getTotalshare());
						/*
						 * log.info("With Math.Round================="+share);
						 * double share1 = (Double.parseDouble(netContAmt) *
						 * Double .parseDouble(rs.getString("TOTALSHARE"))) /
						 * 100;
						 */
						/*
						 * log.info("With out
						 * Math.Round================"+share1);
						 */

					}
				}

				nomineeList.add(advanceBasicBean);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}
		return nomineeList;
	}
	 
	public int deleteAdvances(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		int n = 0;
		String cpfpfwTransCD="",transType="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String delQuery = "update employee_advances_form set deleteflag='Y' where ADVANCETRANSID='"
					+ advanceSearchBean.getAdvanceTransID() + "'";
			log.info("deleteAdvances::delQuery---" + delQuery);
			n = st.executeUpdate(delQuery);
			
			 
			if(advanceSearchBean.getAdvanceType().toUpperCase().equals("CPF")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_CPF_DELETE;
				transType="CPF";
			}else if(advanceSearchBean.getAdvanceType().toUpperCase().equals("PFW")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_PFW_DELETE;;
				transType="PFW";
			}else{
				cpfpfwTransCD="";
				transType="";
			}
			 
			log.info("CPFPTWAdvanceDAO::deleteAdvances()---cpfpfwTransCD----"+cpfpfwTransCD+"----transType--"+transType);
			
			CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advanceSearchBean.getLoginUserId(),advanceSearchBean.getLoginUserName(),advanceSearchBean.getLoginUnitCode(),advanceSearchBean.getLoginRegion(),advanceSearchBean.getLoginUserDispName());			 			
			cpfInfo.deleteCPFPFWTrans(advanceSearchBean.getPensionNo(),advanceSearchBean.getAdvanceTransID(),transType,cpfpfwTransCD,advanceSearchBean.getVerifiedBy());
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;

	}

	public AdvanceBasicReportBean loadPFWHigherEducation(String transactionID,
			AdvanceBasicReportBean reportBean) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sqlQuery = "SELECT NMCOURSE,NMINSTITUTION,INSTITUTIONADDRSS,DURATIONCOURSE,RECOGNIZED,LASTEXAMPASSED FROM EMPLOYEE_ADVANCES_HE_INFO WHERE ADVANCETRANSID="
				+ transactionID;
		log.info(sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("NMCOURSE") != null) {
					reportBean.setNmCourse(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("NMCOURSE")));
				}
				if (rs.getString("NMINSTITUTION") != null) {
					reportBean.setNmInstitue(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("NMINSTITUTION")));
				}
				if (rs.getString("INSTITUTIONADDRSS") != null) {
					reportBean.setAddrInstitue(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("INSTITUTIONADDRSS")));
				}
				if (rs.getString("DURATIONCOURSE") != null) {
					reportBean.setCurseDuration(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DURATIONCOURSE")));
				}
				if (rs.getString("LASTEXAMPASSED") != null) {
					reportBean
							.setHeLastExaminfo(rs.getString("LASTEXAMPASSED"));
				}
				if (rs.getString("RECOGNIZED") != null) {
					reportBean.setHeRecog(rs.getString("RECOGNIZED"));
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return reportBean;
	}

	public String getWithDrawalDetails(String transactionID,
			AdvanceBasicReportBean reportBean, Connection con) {
		int totalRecords = 0;

		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean withDrawalBean = null;
		String wthdrwStr = "";

		StringBuffer sb = sb = new StringBuffer();

		String sqlQuery = "SELECT WTHDRWLID,WTHDRWLPURPOSE,WTHDRWLAMOUNT,WTHDRWLTRNSDT FROM employee_advances_wthdrwl_info WHERE WTHDRWLSTATUS='Y' and ADVANCETRANSID="
				+ transactionID + " order by WTHDRWLID";
		log.info("getWithDrawalDetails" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				withDrawalBean = new AdvanceBasicReportBean();

				if (rs.getString("WTHDRWLID") != null) {
					sb.append(rs.getString("WTHDRWLID"));
				}

				if (rs.getString("WTHDRWLPURPOSE") != null) {
					sb.append("#");
					sb.append(commonUtil.capitalizeFirstLettersTokenizer(rs
							.getString("WTHDRWLPURPOSE")));
				} else {
					sb.append("#");
					sb.append("-");

				}

				if (rs.getString("WTHDRWLAMOUNT") != null) {
					sb.append("#");
					sb.append(rs.getString("WTHDRWLAMOUNT"));
				}

				if (rs.getString("WTHDRWLTRNSDT") != null) {
					sb.append("#");
					sb.append(CommonUtil.getDatetoString(rs
							.getDate("WTHDRWLTRNSDT"), "dd-MMM-yyyy"));
				}

				sb.append(":");

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}

		log.info("============= sb.toString() ===========" + sb.toString());
		return sb.toString();
	}

	public int buildUpdateQueryToStorePersonalInfo(String pensionNo,
			String userName, String frmname) {
		log
				.info("CPFPTWAdvanceDAO::buildUpdateQueryToStorePersonalInfo-- Entering Method");

		String sqlQuery = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int noOfRecords = 0;

		String lastActiveDt = commonUtil.getCurrentDate("dd-MMM-yyyy");
		log.info("-----------lastActiveDt in DAO-----------" + lastActiveDt);

		log.info("-----------userName in DAO-----------" + userName);
		log.info("-----------pensionNo in DAO-----------" + pensionNo);
		log.info("-----------frmname in DAO-----------" + frmname);

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "INSERT INTO EMPLOYEE_PERSONAL_HISTORY(REFPENSIONNUMBER,CPFACNO,AIRPORTSERIALNUMBER,EMPLOYEENO,"
					+ " EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,"
					+ "DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,"
					+ "PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,"
					+ "EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,USERNAME,LASTACTIVE,MODULENAME,SCREENNAME) select REFPENSIONNUMBER,CPFACNO,"
					+ "AIRPORTSERIALNUMBER,EMPLOYEENO, EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,"
					+ "DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,"
					+ "FHNAME,MARITALSTATUS,PERMANENTADDRESS,TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,"
					+ "DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,'"
					+ userName
					+ "','"
					+ lastActiveDt
					+ "',"
					+ "'PFW/ADVANCES','"
					+ frmname
					+ "' FROM EMPLOYEE_PERSONAL_INFO WHERE PENSIONNO="
					+ pensionNo + "";

			log.info("========sqlQuery========" + sqlQuery);
			noOfRecords = st.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return noOfRecords;
	}

	public String getAdvanceInterestRate(String transDate)
			throws InvalidDataException {
		String interestRate = "";
		String sqlQuery = "", finYear = "";

		finYear = commonUtil.converDBToAppFormat(transDate, "dd-MMM-yyyy",
				"yyyy");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "select ADVANCEINTEREST from employee_advance_interest where FINYEAR='"
					+ finYear + "'";

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCEINTEREST") != null) {
					interestRate = rs.getString("ADVANCEINTEREST");
				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}

		return interestRate;
	}
	//	 On 07-May-2012 by Radha for getting Desigantion from Employee_advance_noteparam value 
	//	 On 12-Sep-2011 by Radha for getting rateofinterest value 
	// On 08-Mar-2011 by Radha for Termination Report case
	// On 08-Apr-2011 by Radha for Death Report case
//	 On 03-MAy-2011 by Radha To show Difference betn Old n New Formats of NoteSheet Reports
	public ArrayList noteSheetReport(String pensionNo, String sanctionNo) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();
		String pfid = "", dateOfBirth = "", nomineeNm = "", seperationreason = "", empName = "",netContAmt="",transDt="",station="",region="";
		
		String sqlQuery = "SELECT (SELECT EXTRACT(YEAR FROM ADD_MONTHS(EN.NSSANCTIONEDDT, -3)) || '-' || EXTRACT(YEAR FROM ADD_MONTHS(EN.NSSANCTIONEDDT, 9)) FROM DUAL) as finyear,PI.CPFACNO AS CPFACNO,PI.EMPLOYEENO AS EMPLOYEENO,PI.EMPLOYEENAME AS EMPLOYEENAME,nvl(EN.DESIGNATION,PI.DESEGNATION) AS DESIGNATION,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING	AS DATEOFJOINING,PI.FHNAME AS FHNAME,PI.GENDER AS GENDER,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,EN.PENSIONNO AS PENSIONNO,"
			+ "EN.AAISANCTIONNO,EN.AMTADMITTEDDT,EN.SEPERATIONDT,EN.TRUST,EN.SEPERATIONRESAON,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.ADHOCAMOUNT,EN.NSSANCTIONEDDT,EN.PAYMENTDT AS PAYMENTDT,EN.SEPERATIONFAVOUR AS SEPERATIONFAVOUR,EN.SEPERATIONREMARKS AS SEPERATIONREMARKS,EN.REMARKS AS REMARKS,EN.POSTINGFLAG AS POSTINGFLAG,EN.POSTINGREGION AS POSTINGREGION,EN.POSTINGSTATION AS POSTINGSTATION ,EN.NOMINEEAPPOINTED AS NOMINEEAPPOINTED,EN.PAYMENTINFO AS PAYMENTINFO,EN.SOFLAG AS SOFLAG ,"
			+ " EN.RATEOFINTEREST AS RATEOFINTEREST,EN.REGION AS REGION,EN.AIRPORTCODE AS  AIRPORTCODE,EN.TRANSDT AS TRANSDT  FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM EN  WHERE EN.PENSIONNO = PI.PENSIONNO AND EN.PENSIONNO ="
			+ pensionNo + " AND EN.NSSANCTIONNO=" + sanctionNo;
		
		log.info("CPFPTWAdvanceDAO::noteSheetReport" + sqlQuery);
		
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				
				if (rs.getString("finyear") != null) {
					basicBean.setFinyear(rs.getString("finyear"));
				} else {
					basicBean.setFinyear("---");
				}
				
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}
				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {
					basicBean.setGender("---");
				}
				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("---");
				}
				if (rs.getString("ADHOCAMOUNT") != null) {
					basicBean.setAdhocamt(commonUtil.getDecimalCurrency(rs
							.getDouble("ADHOCAMOUNT")));
				} else {
					basicBean.setAdhocamt("---");
				}
				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {
					basicBean.setTrust("---");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					empName = rs.getString("EMPLOYEENAME");
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}
				
				if (rs.getString("DESIGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESIGNATION"));
				} else {
					basicBean.setDesignation("");
				}
				
				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy");
					basicBean.setDateOfBirth(dateOfBirth);
				} else {
					basicBean.setDateOfBirth("---");
				}
				
				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("---");
				}
				
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(rs.getString("FHNAME"));
				} else {
					basicBean.setFhName("---");
				}
				

//				Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = commonUtil.converDBToAppFormat(rs
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

				
				if(rs.getString("POSTINGFLAG")!=null)
				{
					
					if(rs.getString("POSTINGFLAG").equals("Y"))
					{
						if (rs.getString("POSTINGSTATION") != null) {
							if(rs.getString("POSTINGSTATION").toUpperCase().equals("CAP IAD")){
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer("Chennai Project"));
							}else{
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("POSTINGSTATION")));
							}
							
							
							// basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
						} else {
							basicBean.setAirportcd("---");
						}
						
						if (rs.getString("POSTINGREGION") != null) {
							basicBean.setRegion(rs.getString("POSTINGREGION"));
						} else {
							basicBean.setRegion("---");
						}
						
					}else{
						if (station != null) {
							if(station.toUpperCase().equals("CAP IAD")){
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer("Chennai Project"));
							}else{
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(station));
							}
							
							
							// basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
						} else {
							basicBean.setAirportcd("---");
						}
						
						if (region!= null) {
							basicBean.setRegion(region);
						} else {
							basicBean.setRegion("---");
						}
					}
					
				}				
				
				if (rs.getString("PENSIONNO") != null) {
					basicBean.setPensionNo(rs.getString("PENSIONNO"));
				}
				
				if (rs.getString("AAISANCTIONNO") != null) {
					basicBean.setSanctionno(rs.getString("AAISANCTIONNO"));
				} else {
					basicBean.setSanctionno("---");
				}
				
				if (rs.getString("AMTADMITTEDDT") != null) {
					basicBean.setAmtadmtdate(CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MM-yyyy"));
				} else {
					basicBean.setAmtadmtdate("---");
				}
				
				if ((rs.getString("SEPERATIONRESAON") != null)) {
					seperationreason = rs.getString("SEPERATIONRESAON");
					basicBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));
				} else {
					basicBean.setSeperationreason("---");
				}
				
				log.info("------seperationreason------" + seperationreason);
				if (rs.getString("SEPERATIONDT") != null) {
					if (seperationreason.equals("Resignation")
							|| seperationreason.trim().equals("Death")) {
						String sepdate = CommonUtil.getDatetoString(rs
								.getDate("SEPERATIONDT"), "dd-MM-yyyy");
						basicBean.setSeperationdate(commonUtil
								.converDBToAppFormat(sepdate, "dd-MM-yyyy",
								"dd-MM-yy"));
					} else
						basicBean.setSeperationdate(CommonUtil.getDatetoString(
								rs.getDate("SEPERATIONDT"), "dd-MM-yyyy"));
				} else {
					basicBean.setSeperationdate("---");
				}
				
				if (rs.getString("PAYMENTDT") != null) {
					if (seperationreason.equals("Resignation")
							|| seperationreason.trim().equals("Death")) {
						String paydate = CommonUtil.getDatetoString(rs
								.getDate("PAYMENTDT"), "dd-MM-yyyy");
						basicBean.setPaymentdate(commonUtil
								.converDBToAppFormat(paydate, "dd-MM-yyyy",
								"dd-MM-yyyy"));
					} else
						basicBean.setPaymentdate(CommonUtil.getDatetoString(rs
								.getDate("PAYMENTDT"), "dd-MM-yy"));
				} else {
					basicBean.setPaymentdate("---");
				}
				
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					basicBean.setPaymentinfo("N");
				}
				
				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					basicBean.setEmplshare(commonUtil.getDecimalCurrency(rs
							.getDouble("EMPSHARESUBSCRIPITION")));
				} else {
					basicBean.setEmplshare("---");
				}
				
				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					basicBean.setEmplrshare(commonUtil.getDecimalCurrency(rs
							.getDouble("EMPSHARECONTRIBUTION")));
				} else {
					basicBean.setEmplrshare("---");
				}
				
				if (rs.getString("LESSCONTRIBUTION") != null) {
					basicBean.setPensioncontribution(commonUtil
							.getDecimalCurrency(rs
									.getDouble("LESSCONTRIBUTION")));
				} else {
					basicBean.setPensioncontribution("---");
				}
				
				if (rs.getString("NETCONTRIBUTION") != null) {
					basicBean
					.setNetcontribution(commonUtil
							.getDecimalCurrency(rs
									.getDouble("NETCONTRIBUTION")));
				} else {
					basicBean.setNetcontribution("---");
				}
				
				if (rs.getString("NETCONTRIBUTION") != null) {
					basicBean.setAmtinwords(commonUtil.ConvertInWords(Double
							.parseDouble(rs.getString("NETCONTRIBUTION"))));
					netContAmt = rs.getString("NETCONTRIBUTION");
				} else {
					basicBean.setAmtinwords("---");
				}
				
				if (rs.getString("NSSANCTIONEDDT") != null) {
					basicBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("NSSANCTIONEDDT"), "dd-MM-yyyy"));
				} else {
					basicBean.setSanctiondt("---");
				}
				
				if (rs.getString("SEPERATIONFAVOUR") != null) {
					basicBean.setSeperationfavour(rs
							.getString("SEPERATIONFAVOUR"));
				} else {
					basicBean.setSeperationfavour("---");
				}
				
				if (rs.getString("SEPERATIONREMARKS") != null) {
					basicBean.setSeperationremarks(rs.getString("SEPERATIONREMARKS"));
				} else {
					basicBean.setSeperationremarks("");
				}
				
								 
				if(rs.getString("REMARKS")!=null){					 
					basicBean.setRemarks(rs.getString("REMARKS"));
				}else{
					basicBean.setRemarks("");
				}   
				 
				 
				if(rs.getString("SOFLAG")!=null){					 
					basicBean.setSanctionOrderFlag(rs.getString("SOFLAG"));
				}else{
					basicBean.setSanctionOrderFlag("Before");
				}  
				 
				if(rs.getString("NOMINEEAPPOINTED")!=null){									
					basicBean.setNomineeAppointed(rs.getString("NOMINEEAPPOINTED"));
				}else{
					basicBean.setNomineeAppointed("");
				} 
				if(rs.getString("RATEOFINTEREST")!=null){									
					basicBean.setRateOfInterest(rs.getString("RATEOFINTEREST"));
				}else{
					basicBean.setRateOfInterest("0");
				} 
				
				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						dateOfBirth, commonUtil.leadingZeros(5, pensionNo));
				basicBean.setPfid(pfid);
				
				String nomineeName = this.getNomineeDetails(basicBean, empName,
						con);
				
				if (nomineeName.length() != 0) {
					// nomineeNm=nomineeName.substring(0,nomineeName.lastIndexOf(","));
					nomineeNm = nomineeName;
				}
				if ((!nomineeNm.equals(""))
						&& (seperationreason.equals("Death"))) {
					basicBean.setNomineename(nomineeNm);
				} else {
					basicBean.setNomineename("---");
				}
				
				basicBean.setNssanctionno(sanctionNo);
				if (seperationreason.equals("Death")) {
					multipleNomineeList = this.getMultipleNomineeDets(basicBean, empName,
							netContAmt, con);
				}
				basicBean.setMultipleNomineeList(multipleNomineeList);				 
				reportList.add(basicBean);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}
	//On 07-May-2012 by Radha for getting Desigantion from Employee_advance_noteparam value 
	// By radha On 12-Sep-2011 for getting rateofinterest value
//changes done by radhap on 09-Apr-2011
//	changes done by radhap on 03-May-2011 for show differences to old n new format of sanction order
public ArrayList sanctionOrder(String pensionNo, String nssanctionNo,String frmFlag,String frmSanctionDate) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String seperationReason = "", netContAmt = "",soremarks="";
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();
		 
		String region = "", nomineeNm = "", amtadmtdt = "",seperationRemarks="", nomineeCnt = "", seperationdt = "", sanctiondt = "", regionLabel = "", paymentDt = "", empName = "",remarks="",dateOfBirth="",pfid="",arrearDt="",nomineeAppointed="",paymentFalg="",sanctionOrderNo="",reasonForResignation="",sanctionOrderFlag="",pensioncontribution="",rateOfInterest="",station="",transDt="";
		 
		log.info("=====frmFlag====="+frmFlag);
		
		
		
		String sqlQuery = "SELECT (SELECT EXTRACT(YEAR FROM ADD_MONTHS(EN.NSSANCTIONEDDT, -3)) || '-' || EXTRACT(YEAR FROM ADD_MONTHS(EN.NSSANCTIONEDDT, 9)) FROM DUAL) as finyear,PI.CPFACNO AS CPFACNO,PI.EMPLOYEENAME AS EMPLOYEENAME, nvl(EN.DESIGNATION,PI.DESEGNATION) AS DESIGNATION,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH, "
			+ "EN.AMTADMITTEDDT,EN.SEPERATIONRESAON,EN.SOREMARKS,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.ADHOCAMOUNT,EN.NSSANCTIONEDDT,EN.PAYMENTDT AS PAYMENTDT,EN.SEPERATIONREMARKS AS SEPERATIONREMARKS,EN.REMARKS AS REMARKS,EN.ARREARDATE AS ARREARDATE,EN.POSTINGFLAG AS POSTINGFLAG,EN.POSTINGREGION AS POSTINGREGION,EN.POSTINGSTATION AS POSTINGSTATION,EN.NOMINEEAPPOINTED AS NOMINEEAPPOINTED,EN.PAYMENTINFO AS PAYMENTINFO,EN.SONO AS SANCTIONORDERNO,EN.REASONFORRESIGNATION AS REASONFORRESIGNATION,EN.SOFLAG AS SOFLAG,  "
			+ " EN.RATEOFINTEREST AS RATEOFINTEREST,EN.REGION AS REGION,EN.AIRPORTCODE AS  AIRPORTCODE,EN.TRANSDT AS TRANSDT  FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM EN  WHERE EN.PENSIONNO = PI.PENSIONNO AND EN.PENSIONNO ="
			+ pensionNo + " AND EN.NSSANCTIONNO=" + nssanctionNo;
		
		log.info("CPFPTWAdvanceDAO::sanctionOrder" + sqlQuery);
		
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			
			
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				
				basicBean = new AdvanceBasicReportBean();
				
				if (rs.getString("finyear") != null) {
					basicBean.setFinyear(rs.getString("finyear"));
				} else {
					basicBean.setFinyear("---");
				}
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					basicBean.setCpfaccno("---");
				}
				if (rs.getString("ADHOCAMOUNT") != null) {
					basicBean.setAdhocamt(commonUtil.getCurrency(rs
							.getDouble("ADHOCAMOUNT")));
				} else {
					basicBean.setAdhocamt("---");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					empName = rs.getString("EMPLOYEENAME");
					basicBean.setEmployeeName(empName);
					
				} else {
					basicBean.setEmployeeName("");
				}
				if(rs.getString("SOREMARKS")!=null){									
					
					soremarks=rs.getString("SOREMARKS");		
					
				} 
				if (rs.getString("SEPERATIONRESAON") != null) {
					seperationReason = rs.getString("SEPERATIONRESAON");
				} else {
					seperationReason = "---";
				}
				log.info("seperationReason" + seperationReason.trim());
				basicBean.setSeperationreason(seperationReason.trim());
				if (rs.getString("DESIGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESIGNATION"));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("PAYMENTDT") != null) {
					paymentDt = CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MM-yy");
				}
				
				if (rs.getString("PAYMENTINFO") != null) {
					paymentFalg=rs.getString("PAYMENTINFO");
				}  
				 
				if (rs.getString("SEPERATIONREMARKS") != null) {
					seperationRemarks = rs.getString("SEPERATIONREMARKS");
				}
				if(rs.getString("REMARKS")!=null){									
					//remarks=commonDAO.loadRemarks(rs.getString("REMARKS"),",");
					remarks=rs.getString("REMARKS");		
					
				} 
				
				//	Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = commonUtil.converDBToAppFormat(rs
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
				
				if(rs.getString("POSTINGFLAG")!=null)
				{
					if(rs.getString("POSTINGFLAG").equals("Y"))
					{
						
						if (rs.getString("POSTINGSTATION") != null) {
							
							if ((rs.getString("POSTINGSTATION").equals("C.S.I Airport"))
									|| (rs.getString("POSTINGSTATION").equals("CSIA IAD"))
									|| (rs.getString("POSTINGSTATION").equals("RAUSAP"))
									|| (rs.getString("POSTINGSTATION").equals("EMO"))
									|| (rs.getString("POSTINGSTATION").equals("CRSD"))
									|| (rs.getString("POSTINGSTATION").equals("DRCDU"))) {
								
								basicBean.setTblairportcd(rs.getString("POSTINGSTATION"));
							} else if (rs.getString("POSTINGSTATION").equals("DELHI")) {
								basicBean.setTblairportcd("New Delhi");
							} else {
								basicBean.setTblairportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("POSTINGSTATION")));
							}
							
						} else {
							basicBean.setTblairportcd("---");
						}
						
						if (station!= null) {
							
							if ((station.equals("C.S.I Airport"))
									|| (station.equals("CSIA IAD"))
									|| (station.equals("RAUSAP"))
									|| (station.equals("EMO"))
									|| (station.equals("CRSD"))
									|| (station.equals("DRCDU"))) {
								
								basicBean.setAirportcd(station);
							} else if (station.equals("DELHI")) {
								basicBean.setAirportcd("New Delhi");
							} else {
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(station));
							}
							
						} else {
							basicBean.setAirportcd("---");
						}
						
						if (region != null) {
							region = commonUtil.getRegion(rs.getString("POSTINGREGION"));
						} else {
							region = "---";
						}
						
						
					}else{
						if (station != null) {
							
							if ((station.equals("C.S.I Airport"))
									|| (station.equals("CSIA IAD"))
									|| (station.equals("RAUSAP"))
									|| (station.equals("EMO"))
									|| (station.equals("CRSD"))
									|| (station.equals("DRCDU"))) {
								
								basicBean.setAirportcd(station);
							} else if (station.equals("DELHI")) {
								basicBean.setAirportcd("New Delhi");
							} else {
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(station));
							}
							
							//  basicBean.setTblairportcd(rs.getString("AIRPORTCODE"));
							
							basicBean.setTblairportcd(basicBean.getAirportcd());
							
						} else {
							basicBean.setAirportcd("---");
							basicBean.setTblairportcd("---");
							
						}
						
						if (region != null) {
							region = commonUtil.getRegion(rs.getString("REGION"));
						} else {
							region = "---";
						}
					}
				}
				
				
				
				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {
					basicBean.setGender("");
				}
				
				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(rs.getString("DATEOFBIRTH"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					basicBean.setDateOfBirth("");
				}
				
				if (rs.getString("AMTADMITTEDDT") != null) {
					amtadmtdt = CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MM-yyyy");
				} else {
					amtadmtdt = "---";
				}
				
				if (rs.getString("SEPERATIONDT") != null) {
					seperationdt = CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy");
				} else {
					seperationdt = "---";
				}
				
				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					basicBean.setEmplshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARESUBSCRIPITION")));
				} else {
					basicBean.setEmplshare("---");
				}
				
				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					basicBean.setEmplrshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARECONTRIBUTION")));
				} else {
					basicBean.setEmplrshare("---");
				}
				
				if (rs.getString("LESSCONTRIBUTION") != null) {
					pensioncontribution=rs.getString("LESSCONTRIBUTION");
					basicBean.setPensioncontribution(commonUtil.getCurrency(rs
							.getDouble("LESSCONTRIBUTION")));
				} else {
					basicBean.setPensioncontribution("---");
					pensioncontribution="---";
				}
				
				if (rs.getString("NETCONTRIBUTION") != null) {
					basicBean.setNetcontribution(commonUtil.getCurrency(rs
							.getDouble("NETCONTRIBUTION")));
					
					netContAmt = rs.getString("NETCONTRIBUTION");
				} else {
					basicBean.setNetcontribution("---");
				}
				
				if (rs.getString("NSSANCTIONEDDT") != null) {
					sanctiondt = CommonUtil.getDatetoString(rs
							.getDate("NSSANCTIONEDDT"), "dd-MM-yy");
				} else {
					sanctiondt = "---";
				}
				
				if (rs.getString("SANCTIONORDERNO") != null) {
					sanctionOrderNo =commonUtil.leadingZeros(5, rs.getString("SANCTIONORDERNO"));
				} else {
					sanctionOrderNo = "---";
				}
				if (rs.getString("SOFLAG") != null) {
					sanctionOrderFlag =rs.getString("SOFLAG");
				} else {
					sanctionOrderFlag = "Before";
				} 
				
				if (rs.getString("REASONFORRESIGNATION") != null) {
					reasonForResignation = rs.getString("REASONFORRESIGNATION");
				} else {
					reasonForResignation = "---";
				}
				
				if (rs.getString("ARREARDATE") != null) {					
					arrearDt=CommonUtil.getDatetoString(rs.getDate("ARREARDATE"), "dd/MM/yyyy");
				} else {
					basicBean.setArreardate("---");
				}
				
				if (rs.getString("RATEOFINTEREST") != null) {
					rateOfInterest=rs.getString("RATEOFINTEREST");
				} else {
					rateOfInterest = "0";
				}
				if(rs.getString("NOMINEEAPPOINTED")!=null){									
					nomineeAppointed=rs.getString("NOMINEEAPPOINTED");
					
				}else{
					nomineeAppointed="N";
					
				} 
				basicBean.setNomineeAppointed(nomineeAppointed);
				basicBean.setPensionNo(pensionNo);
				basicBean.setNssanctionno(nssanctionNo);
				
				
				pfid = commonDAO.getPFID(basicBean.getEmployeeName().toUpperCase(),
						dateOfBirth, commonUtil.leadingZeros(5,pensionNo));
				basicBean.setPfid(pfid);
				
				log.info("******----PF ID IN DAO------*****"+basicBean.getPfid());
				
				String nomineeName = this
				.getNomineeDet(basicBean, empName, con);
				log.info("=========nomineeName in DAO========" + nomineeName);
				log.info("=========nomineeName length() in DAO========"
						+ nomineeName.length());
				
				if (nomineeName.length() != 0) {
					nomineeNm = nomineeName.substring(0,
							nomineeName.length() - 1);
					log.info("=========nomineeNm in DAO========" + nomineeNm);
					nomineeCnt = nomineeName.substring(
							nomineeName.length() - 1, nomineeName.length());
					log.info("=========nomineeCnt in DAO========" + nomineeCnt);
				}
				
				if (seperationReason.equals("Death")) {
					nomineeList = this.getNomineeDets(basicBean, empName,
							netContAmt, con);
				}
				
				if (seperationReason.equals("Death")) {
					multipleNomineeList = this.getMultipleNomineeDets(basicBean, empName,
							netContAmt, con);
				}
				
				sanctionOrderList.add(basicBean);
				
			}
			AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();
			AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
			
			reportbean.setRegion(region);
			regionLabel = commonUtil.getRegionLbls(region);
			reportbean.setRegionLbl(regionLabel);
			reportbean.setSeperationreason(seperationReason.trim());
			reportbean.setAmtadmtdate(amtadmtdt);
			reportbean.setSeperationdate(seperationdt);
			reportbean.setSanctiondt(sanctiondt);
			reportbean.setSanctionOrderNo(sanctionOrderNo);
			reportbean.setSanctionList(sanctionOrderList);
			reportbean.setPaymentdate(paymentDt);
			reportbean.setApprovedremarks(seperationRemarks);
			reportbean.setRemarks(remarks);
			reportbean.setSoremarks(soremarks);
			reportbean.setArreardate(arrearDt);
			reportbean.setNomineeAppointed(nomineeAppointed);
			reportbean.setResignationreason(reasonForResignation);
			reportbean.setSanctionOrderFlag(sanctionOrderFlag);
			reportbean.setRateOfInterest(rateOfInterest);
			reportbean.setPensioncontribution(pensioncontribution);
			reportbean.setFinyear(basicBean.getFinyear());
			
			reportbean.setPaymentinfo(paymentFalg);
			if (seperationReason.equals("Death")) {
				reportbean.setNomineeList(nomineeList);
				reportbean.setMultipleNomineeList(multipleNomineeList);
				
			}
			
			if ((!nomineeNm.equals("")) && (seperationReason.equals("Death"))) {
				reportbean.setNomineename(nomineeNm);
			} else {
				reportbean.setNomineename(nomineeNm);
			}
			
			if ((!nomineeCnt.equals("")) && (seperationReason.equals("Death"))) {
				reportbean.setNomineecount(nomineeCnt);
			} else {
				reportbean.setNomineecount(nomineeCnt);
			}
			
			
			
			log.info("basicBean.getArreardate() in DAO-----"+basicBean.getArreardate()); 
			
			reportList.add(reportbean);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
		
	}
	public ArrayList checkListApprove(String pensionNo, String transactionID,
			String frmName) throws InvalidDataException {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("#########0");
		String transID = "", dateOfBirth = "", pfid = "", prssOutStandAmnt = "0", subscriptionAmnt = "", subscriptionAmntInt = "", findYear = "", aaiContribution = "", aaiContributionAmntInt = "";
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		AdvanceBasicReportBean reportBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		long cpfFund = 0;

		String purposeTye = "", sqlQuery = "", approvedAmt = "", purposeOptionType = "", loadElgAmnt = "", currentYear = "", nextYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "", lod = "";
		String dependDOB = "", dependMD = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (frmName.equals("Form-4Report")) {
				sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,PI.DESEGNATION AS DESEGNATION,PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,initCap(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.PAYMENTINFO AS PAYMENTINFO,AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.VERIFIEDBY AS VERIFIEDBY,"
						+ "AF.CHKWTHDRWL AS CHKWTHDRWL,AF.LOD AS LOD,AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE,AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.NTIMESTRNASMNTHEMOLUMENTS AS NTIMESEMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.CPFACCFUND AS CPFACCFUND,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,  "
						+ "AF.CHKLISTFLAG  AS CHKLISTFLAG FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
						+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;
			} else {
				sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,PI.DESEGNATION AS DESEGNATION,PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,initCap(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.PAYMENTINFO AS PAYMENTINFO,AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.VERIFIEDBY AS VERIFIEDBY,"
						+ "AF.CHKWTHDRWL AS CHKWTHDRWL,AF.LOD AS LOD,AF.PLACEOFPOSTING AS PLACEOFPOSTING,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE,AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.NTIMESTRNASMNTHEMOLUMENTS AS NTIMESEMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.CPFACCFUND AS CPFACCFUND,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,  "
						+ "AF.CHKLISTFLAG  AS CHKLISTFLAG FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
						+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

			}

			log
					.info("CPFPTWAdvanceDAO: checkListApprove :sqlQuery "
							+ sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {

					transID = rs.getString("ADVANCETRANSID");
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransIDDec(rs
							.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					basicBean.setPaymentinfo("");
				}
				if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
					basicBean.setPrpsecvrdclse(rs
							.getString("PURPOSEOPTIONCVRDCLUSE"));
				} else {
					basicBean.setPrpsecvrdclse("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}

				if (rs.getString("CHKWTHDRWL") != null) {
					basicBean.setChkwthdrwlinfo(rs.getString("CHKWTHDRWL")
							.trim());
				} else {
					basicBean.setChkwthdrwlinfo("");
				}
				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}

				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				basicBean.setPensionNo(pensionNo);

				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("FHNAME")));
				} else {
					basicBean.setFhName("");
				}

				if (rs.getString("APPROVEDSUBSCRIPTIONAMT") != null) {
					basicBean.setApprovedsubamt(rs
							.getString("APPROVEDSUBSCRIPTIONAMT"));
					basicBean.setApprovedsubamtcurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("APPROVEDSUBSCRIPTIONAMT")));
				} else {
					basicBean.setApprovedsubamt("");
					basicBean.setApprovedcontamtcurr("0");

				}

				if (rs.getString("APPROVEDCONTRIBUTIONAMT") != null) {
					basicBean.setApprovedconamt(rs
							.getString("APPROVEDCONTRIBUTIONAMT"));
					basicBean.setApprovedcontamtcurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("APPROVEDCONTRIBUTIONAMT")));
				} else {
					basicBean.setApprovedconamt("");
					basicBean.setApprovedcontamtcurr("0");

				}

				if (rs.getString("VERIFIEDBY") != null) {
					basicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {
					basicBean.setVerifiedby("");
				}

				if (rs.getString("LOD") != null) {
					lod = rs.getString("LOD");

					if (frmName.equals("PFWCheckListReport")) {
						basicBean.setLodInfo(this.loadListOfDocument(lod));
					} else {
						basicBean.setLodInfo(rs.getString("LOD"));
					}

				} else {
					basicBean.setLodInfo("");
				}

				log.info("getCPFAdvanceForm2Info::Emolument:::::"
						+ basicBean.getEmoluments());
				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("");
				}
				if (rs.getString("ADVNCERQDDEPEND") != null) {
					basicBean.setAdvncerqddepend(rs
							.getString("ADVNCERQDDEPEND"));
				} else {
					basicBean.setAdvntrnsdt("");
				}
				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					basicBean.setUtlisiedamntdrwn(rs
							.getString("UTLISIEDAMNTDRWN"));
				} else {
					basicBean.setUtlisiedamntdrwn("");
				}
				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					purposeOptionType = rs.getString("PURPOSEOPTIONTYPE");
					if (purposeOptionType.equals("HBA")) {
						if (purposeOptionType.equals("PURCHASESITE")) {
							basicBean.setPurposeOptionTypeDesr("Purchase Site");
						} else if (purposeOptionType.equals("PURCHASEHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Purchase House");
						} else if (purposeOptionType
								.equals("CONSTRUCTIONHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Construction House");
						} else if (purposeOptionType.equals("ACQUIREFLAT")) {
							basicBean.setPurposeOptionTypeDesr("Acquire Flat");
						} else if (purposeOptionType.equals("RENOVATIONHOUSE")) {
							basicBean
									.setPurposeOptionTypeDesr("Renovation House");
						} else if (purposeOptionType.equals("REPAYMENTHBA")) {
							basicBean.setPurposeOptionTypeDesr("Repayment HBA");
						} else if (purposeOptionType.equals("HBAOTHERS")) {
							basicBean.setPurposeOptionTypeDesr("HBA Others");
						}
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						basicBean.setPurposeOptionTypeDesr("Higher Education");
					} else {
						basicBean.setPurposeOptionTypeDesr(commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE")));
					}

					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				} else {
					basicBean.setPurposeOptionType("");
				}

				if (frmName.equals("PFWCheckList")) {
					if (purposeOptionType.equals("DAUGHTER")
							|| purposeOptionType.equals("DEPENDENT BROTHER")
							|| purposeOptionType.equals("DEPENDENT SISTER")
							|| purposeOptionType.equals("SON")
							|| purposeOptionType.equals("SELF")) {
						reportBean = this.loadPFWMarriage(transID, reportBean);
						dependDOB = reportBean.getFmlyDOB();
						dependMD = reportBean.getMarriagedate();
					}
				}
				log.info("frmName======================" + frmName
						+ "purposeOptionType" + purposeOptionType + "dependDOB"
						+ dependDOB);
				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				} else {
					basicBean.setPurposeType("");
				}
				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setTotalInst(rs.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setTotalInst("0");
				}
				if (rs.getString("NTIMESEMOLUMENTS") != null) {
					basicBean.setNtimesemoluments(rs
							.getString("NTIMESEMOLUMENTS"));
					basicBean.setNtimesemolumentscurr(commonUtil
							.getDecimalCurrency(rs
									.getDouble("NTIMESEMOLUMENTS")));

					basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
					basicBean.setEmolumentsCurr(commonUtil
							.getDecimalCurrency(rs.getDouble("EMOLUMENTS")));

				} else {
					if (rs.getString("EMOLUMENTS") != null) {
						basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
						basicBean
								.setEmolumentsCurr(commonUtil
										.getDecimalCurrency(rs
												.getDouble("EMOLUMENTS")));
					} else {
						basicBean.setEmoluments("0.0");
					}

				}
				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setAdvnceRequest(rs.getString("REQUIREDAMOUNT"));
					basicBean
							.setAdvnceRequestCurr(commonUtil
									.getDecimalCurrency(rs
											.getDouble("REQUIREDAMOUNT")));

				} else {
					basicBean.setAdvnceRequest("0.0");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					if (frmName.equals("PFWCheckList")) {
						basicBean.setDateOfBirth(dependDOB);
					} else {
						basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
								.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
						dateOfBirth = commonUtil.converDBToAppFormat(rs
								.getDate("dateofbirth"));
					}

				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				pfid = commonDAO.getPFID(rs.getString("EMPLOYEENAME"),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");

				if (rs.getString("PLACEOFPOSTING") != null) {
					basicBean.setPlaceofposting(rs.getString("PLACEOFPOSTING"));
				} else {
					basicBean.setPlaceofposting("");
				}
				basicBean.setDateOfMembership(basicBean.getDateOfJoining());
	/*			toDate = commonUtil.getCurrentDate("MM-yyyy");
				String currentDtInfo[] = toDate.split("-");
				currentMonth = Integer.toString(Integer
						.parseInt(currentDtInfo[0]) - 1);
				currentYear = currentDtInfo[1];
				nextYear = Integer
						.toString(Integer.parseInt(currentDtInfo[1]) + 1);
				toDate = "";

				
				 * fromDate="01-Apr-"+lastYear; toDate="31-Mar-2010";
				 * fromDate="01-Apr-2009"; toDate="31-Mar-2010";
				 
				fromDate = "01-Apr-" + currentYear;
				toDate = "31-Mar-" + nextYear;
				String pensionInfo = financeReport.getPensionInfo(pensionNo,
						basicBean.getEmployeeName(), fromDate, toDate);*/
				
				String pensionInfo =getPFCardClosingBalance(pensionNo,basicBean.getEmployeeName());
				
				/*
				 * String pensionInfo = financeReport.getPensionInfo(pensionNo,
				 * "2007");
				 */
				String[] pensionArray = pensionInfo.split(",");
				subscriptionAmnt = pensionArray[2];
				subscriptionAmntInt = pensionArray[3];
				aaiContribution = pensionArray[4];
				aaiContributionAmntInt = pensionArray[5];

				log
						.info("CPFPTWAdvanceDAO: checkListApprove :subscriptionAmnt "
								+ subscriptionAmnt
								+ ":subscriptionAmntInt "
								+ subscriptionAmntInt);
				log.info("CPFPTWAdvanceDAO: checkListApprove :aaiContribution "
						+ aaiContribution + ":aaiContributionAmntInt "
						+ aaiContributionAmntInt);

				if (!subscriptionAmnt.equals("")
						&& !subscriptionAmntInt.equals("")
						&& !(basicBean.getAdvanceType().equals("PFW") && basicBean
								.getPurposeType().equals("HBA"))) {
					subscriptionAmnt = Double.toString(Math.round(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt)));
				} else if (!subscriptionAmnt.equals("")
						&& !subscriptionAmntInt.equals("")
						&& !aaiContribution.equals("")
						&& (basicBean.getAdvanceType().equals("PFW") && basicBean
								.getPurposeType().equals("HBA"))) {
					subscriptionAmnt = Long.toString(Math.round(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt)));
					aaiContribution = Double.toString(Math.round(Double
							.parseDouble(aaiContribution)
							+ Double.parseDouble(aaiContributionAmntInt)));
				} else {

					subscriptionAmnt = Double.toString(Math.round(Double
							.parseDouble(subscriptionAmnt)
							+ Double.parseDouble(subscriptionAmntInt)
							- Double.parseDouble("1000")));
				}
				log.info("CPFPTWAdvanceDAO: checkListApprove :frmName "
						+ frmName);

				/*
				 * We are adding the PFW Checklist before PFWForm3(i.e,PFW
				 * Form-3 Vefirication).So,User should be enter pfw check list
				 * that is reason we commented and add pfwchecklist flag
				 */
				/*
				 * if(frmName.equals("PFWForm3") &&
				 * (rs.getString("VERIFIEDBY").equals("PERSONNEL"))){
				 */
				if (frmName.equals("PFWCheckList")
						&& (rs.getString("CHKLISTFLAG").equals("N"))) {
					try {
						loadElgAmnt = this.getPFWAdvanceEmoluments(basicBean
								.getEmoluments(), subscriptionAmnt, basicBean
								.getAdvnceRequest(), prssOutStandAmnt,
								basicBean.getAdvanceType(), basicBean
										.getPurposeType(), basicBean
										.getPurposeOptionType());
					} catch (InvalidDataException e) {
						throw e;
					}

					String[] lodElgblAmnt = loadElgAmnt.split(",");
					basicBean.setMnthsemoluments(lodElgblAmnt[0]);
					basicBean.setAmntRecommended(lodElgblAmnt[1]);
					basicBean.setEmpshare(lodElgblAmnt[2]);
					if (!basicBean.getAdvanceType().equals("CPF")) {
						cpfFund = Long.parseLong(df.format(Math.round(Double
								.parseDouble(lodElgblAmnt[2]))))
								+ Long.parseLong(df.format(Math.round(Double
										.parseDouble(aaiContribution))));
						basicBean.setCPFFund(Long.toString(cpfFund));

						if (basicBean.getPurposeType().equals("HE")
								|| basicBean.getPurposeType()
										.equals("MARRIAGE")) {
							long chckElgble = Long.parseLong(df
									.format(Math.round(Double
											.parseDouble(lodElgblAmnt[2]))))
									- Long
											.parseLong(df
													.format(Math
															.round(Double
																	.parseDouble(lodElgblAmnt[1]))));
							if (chckElgble < 1000) {
								basicBean
										.setAuthrizedRemarks("CPF Account should be maintain Rs.1000");
							}
						}

						basicBean.setAmntRecommendedDscr(commonUtil
								.ConvertInWords(Double
										.parseDouble(lodElgblAmnt[1])));
					}

					basicBean.setSubscriptionAmt(subscriptionAmnt);
					basicBean.setContributionAmt(aaiContribution);
					basicBean
							.setAmntRecommendedDscr(commonUtil
									.ConvertInWords(Double
											.parseDouble(lodElgblAmnt[1])));
					basicBean
							.setEmolumentsLabel(lodElgblAmnt[lodElgblAmnt.length - 1]);
				} else {

					log
							.info("======================Other Than PFW Check list===========================");
					if (rs.getString("SUBSCRIPTIONAMNT") != null) {

						basicBean.setSubscriptionAmt(rs
								.getString("SUBSCRIPTIONAMNT"));
						basicBean.setSubscriptionAmtCurr(commonUtil
								.getDecimalCurrency(Double.parseDouble(rs
										.getString("SUBSCRIPTIONAMNT"))));
						basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
						basicBean.setEmpshareCurr(commonUtil
								.getDecimalCurrency(Double.parseDouble(rs
										.getString("SUBSCRIPTIONAMNT"))));
					} else {
						basicBean.setSubscriptionAmt("0.00");
						basicBean.setEmpshare("0.00");
						basicBean.setEmpshareCurr("0.00");
						basicBean.setSubscriptionAmtCurr("0.00");
					}
					if (rs.getString("CONTRIBUTIONAMOUNT") != null) {

						basicBean.setContributionAmtCurr(commonUtil
								.getDecimalCurrency(Double.parseDouble(rs
										.getString("CONTRIBUTIONAMOUNT"))));
						basicBean.setContributionAmt(rs
								.getString("CONTRIBUTIONAMOUNT"));
					} else {
						basicBean.setContributionAmt("0.00");
						basicBean.setContributionAmtCurr("0.00");
					}
					if (rs.getString("NTIMESEMOLUMENTS") != null) {
						basicBean.setMnthsemoluments(rs
								.getString("NTIMESEMOLUMENTS"));
						basicBean.setMnthsemolumentsCurr(commonUtil
								.getDecimalCurrency(Double.parseDouble(rs
										.getString("NTIMESEMOLUMENTS"))));
						loadElgAmnt = this.getPFWAdvanceEmoluments(rs
								.getString("EMOLUMENTS"), "0.00", "0.00",
								"0.00", basicBean.getAdvanceType(), basicBean
										.getPurposeType(), basicBean
										.getPurposeOptionType());
						String[] lodElgblForm4Amnt = loadElgAmnt.split(",");
						basicBean
								.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length - 1]);
					} else {
						if (rs.getString("EMOLUMENTS") != null) {
							loadElgAmnt = this.getPFWAdvanceEmoluments(rs
									.getString("EMOLUMENTS"), "0.00", "0.00",
									"0.00", basicBean.getAdvanceType(),
									basicBean.getPurposeType(), basicBean
											.getPurposeOptionType());
							String[] lodElgblForm4Amnt = loadElgAmnt.split(",");

							if (frmName.equals("Form-4Report")) {
								basicBean.setMnthsemoluments(rs
										.getString("EMOLUMENTS"));
							} else {
								basicBean
										.setMnthsemoluments(lodElgblForm4Amnt[0]);
							}

							basicBean.setMnthsemolumentsCurr(commonUtil
									.getDecimalCurrency(Double
											.parseDouble(basicBean
													.getMnthsemoluments())));
							basicBean
									.setEmolumentsLabel(lodElgblForm4Amnt[lodElgblForm4Amnt.length - 1]);
						} else {
							basicBean.setMnthsemoluments("0.0");
							basicBean.setMnthsemolumentsCurr("0.0");
						}
					}

					if (rs.getString("CPFACCFUND") != null) {
						basicBean.setCPFFund(rs.getString("CPFACCFUND"));
						basicBean.setCPFFundCurr((commonUtil
								.getDecimalCurrency(Double.parseDouble(rs
										.getString("CPFACCFUND")))));
					} else {
						basicBean.setCPFFund("0.00");
						basicBean.setCPFFundCurr("0.00");
					}
					if (rs.getString("APPROVEDAMNT") != null) {
						approvedAmt = rs.getString("APPROVEDAMNT");
						basicBean.setAmntRecommended(approvedAmt);
						basicBean.setAmntRecommendedCurr(commonUtil
								.getDecimalCurrency(rs
										.getDouble("APPROVEDAMNT")));
					} else {
						approvedAmt = "0.00";
						basicBean.setAmntRecommended("0.00");
						basicBean.setAmntRecommendedCurr("0.00");
					}
					basicBean.setAmntRecommendedDscr(commonUtil
							.ConvertInWords(Double.parseDouble(approvedAmt)));
				}

				log.info("subscriptionAmnt" + subscriptionAmnt
						+ "aaiContribution" + aaiContribution + "pensionNo"
						+ pensionNo);

				if (basicBean.getPurposeType().equals("HBA")) {
					reportBean = this.loadPFWHBA(transID, reportBean);
				}

				bankMasterBean = loadEmployeeBankInfo(pensionNo, transID);

				/*
				 * To display epis_advances_transactions table values in PFW
				 * Check List,PFW Form-III Verification,PFW Form-IV
				 * Verification, PFW Final Approval Reports.
				 * 
				 * To load epis_advances_transactions table data in Approval
				 * Screens also remove the if block.
				 * 
				 */

				if (frmName.equals("PFWCheckListReport")
						|| frmName.equals("PFWForm3Report")
						|| frmName.equals("PFWForm4VerificationReport")
						|| frmName.equals("Form-4Report")) {
					basicBean = this.getAdvanceTransactionDetails(basicBean,
							frmName, pensionNo);
				}

				reportList.add(basicBean);
				reportList.add(bankMasterBean);
				reportList.add(reportBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}
//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int savePFWCheckList(AdvancePFWFormBean advancePFWBean,
			String emoluments, String hbaDrwnFromAAI, String wthdrwlInfo,
			String subscriptionAmt, String contributionAmt,
			String lodDocumentsInfo, String purposeOptionType, String flag) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0, updatedHBARecords = 0;
		String updateQuery = "", updateHBAQuery = "";
		String purposeType = "", loadElgAmnt = "", amountRecommended = "";
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();
		log.info("Basic Info" + advancePFWBean.getPensionNo());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			String verfiedBy = "FINANCE";
			String fmlyDOB = "", approvedRemarks = "";
			ArrayList list = new ArrayList();

			/*
			 * list = this.advanceForm2Report(advancePFWBean.getPensionNo(),
			 * advancePFWBean.getAdvanceTransID()); basicReportBean =
			 * (AdvanceBasicReportBean) list.get(0); loadElgAmnt =
			 * this.getPFWAdvanceEmoluments(emoluments, cpfFund,
			 * basicReportBean.getAdvReqAmnt(), "0", basicReportBean
			 * .getAdvanceType(), basicReportBean.getPurposeType(),
			 * basicReportBean .getPurposeOptionType()); String[] lodElgblAmnt =
			 * loadElgAmnt.split(",");
			 */

			log.info("------subscriptionAmt in DAO-----" + subscriptionAmt);
			log.info("------contributionAmt in DAO-----" + contributionAmt);

			log.info(advancePFWBean.getPensionNo());

			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET TRNASMNTHEMOLUMENTS='"
					+ emoluments
					+ "',SUBSCRIPTIONAMNT='"
					+ subscriptionAmt
					+ "',CONTRIBUTIONAMOUNT='"
					+ contributionAmt
					+ "',CHKWTHDRWL='"
					+ wthdrwlInfo
					+ "',CHKLISTFLAG='"
					+ "Y"
					+ "',LOD='"
					+ lodDocumentsInfo
					+ "',PURPOSEOPTIONTYPE='"
					+ purposeOptionType
					+ "' WHERE ADVANCETRANSID='"
					+ advancePFWBean.getAdvanceTransID()
					+ "' AND PENSIONNO='"
					+ advancePFWBean.getPensionNo() + "'";

			log.info("======&&&&&&&&=======" + advancePFWBean.getPurposeType());
			if (advancePFWBean.getPurposeType().equals("HBA")) {
				updateHBAQuery = "UPDATE employee_advance_hba_info SET HBADRWNFRMAAI='"
						+ hbaDrwnFromAAI
						+ "' WHERE ADVANCETRANSID='"
						+ advancePFWBean.getAdvanceTransID() + "'";
			}

			log.info("CPFPTWAdvanceDAO::savePFWCheckList-----updateQuery"
					+ updateQuery);
			log.info("CPFPTWAdvanceDAO::savePFWCheckList-----updateHBAQuery"
					+ updateHBAQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			if (advancePFWBean.getPurposeType().equals("HBA")) {
				updatedHBARecords = st.executeUpdate(updateHBAQuery);
			}

			transBean = this.getCPFPFWTransDetails(advancePFWBean
					.getAdvanceTransID());

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advancePFWBean
					.getLoginUserId(), advancePFWBean.getLoginUserName(),
					advancePFWBean.getLoginUnitCode(), advancePFWBean
							.getLoginRegion(), advancePFWBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(transBean, advancePFWBean.getPensionNo(),
					advancePFWBean.getAdvanceTransID(), advancePFWBean.getLoginUserDesignation(), "PFW",
					Constants.APPLICATION_PROCESSING_PFW_CHECK_LIST);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	// New Method------

	public ArrayList advanceCheckListApprove(String pensionNo,
			String transactionID, String formType, String transDate)
			throws InvalidDataException {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("#########0");
		String transID = "", dateOfBirth = "", pfid = "", prssOutStandAmnt = "0", subscriptionAmnt = "", subscriptionAmntInt = "", findYear = "", aaiContribution = "", aaiContributionAmntInt = "";
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		long cpfFund = 0;
		String interestRate = "", purposeTye = "", sqlQuery = "", loadElgAmnt = "", currentYear = "", lastYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (!transDate.equals("")) {
				interestRate = this.getAdvanceInterestRate(transDate);
			}

			sqlQuery = "SELECT PI.DEPARTMENT AS DEPARTMENT,PI.DESEGNATION AS DESEGNATION,PI.REGION AS REGION,PI.FHNAME AS FHNAME,PI.EMPLOYEENO AS EMPLOYEENO,PI.EMPLOYEENAME AS EMPLOYEENAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.DATEOFJOINING AS DATEOFJOINING,round(months_between(NVL(PI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF,AF.ADVANCETYPE AS ADVANCETYPE,AF.MTHINSTALLMENTAMT AS MTHINSTALLMENTAMT,"
					+ "AF.TRUST AS TRUST,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.ADVNCERQDDEPEND AS ADVNCERQDDEPEND,AF.UTLISIEDAMNTDRWN AS UTLISIEDAMNTDRWN,AF.PURPOSETYPE AS PURPOSETYPE,AF.TRNASMNTHEMOLUMENTS AS EMOLUMENTS,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.REQUIREDAMOUNT AS REQUIREDAMOUNT,AF.TOTALINATALLMENTS AS TOTALINATALLMENTS,"
					+ "AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS ,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.PREVIOUSOUTSTANDINGAMT AS PREVIOUSOUTSTANDINGAMT,AF.INTERESTINSTALLMENTS AS INTERESTINSTALLMENTS,AF.INTERESTINSTALLAMT AS INTERESTINSTALLAMT,AF.RECOMMENDEDAMT AS RECOMMENDEDAMT,AF.LOD AS LOD  "
					+ "FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF  WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
					+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;
			log.info("=======advanceCheckListApprove======In Dao========"+sqlQuery);
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransIDDec(rs
							.getString("ADVANCETRANSID"));
				}

				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}
				if (rs.getString("REGION") != null) {
					basicBean.setRegion(rs.getString("REGION"));
				} else {
					basicBean.setRegion("");
				}

				if (!interestRate.equals("")) {
					basicBean.setInterestRate(interestRate);
				} else {
					basicBean.setInterestRate("");
				}

				if (rs.getString("RECOMMENDEDAMT") != null) {
					basicBean
							.setAmntRecommended(rs.getString("RECOMMENDEDAMT"));
					basicBean.setAmntRecommendedCurr(commonUtil
							.getCurrency(Double.parseDouble(rs
									.getString("RECOMMENDEDAMT"))));
				} else {
					basicBean.setAmntRecommended("0.0");
				}

				if (rs.getString("MTHINSTALLMENTAMT") != null) {
					basicBean.setMthinstallmentamt(rs
							.getString("MTHINSTALLMENTAMT"));
				} else {
					basicBean.setMthinstallmentamt("");
				}

				if (rs.getString("INTERESTINSTALLMENTS") != null) {
					basicBean.setInterestinstallments(rs
							.getString("INTERESTINSTALLMENTS"));
				} else {
					basicBean.setInterestinstallments("0");
				}

				if (rs.getString("INTERESTINSTALLAMT") != null) {
					basicBean.setIntinstallmentamt(commonUtil.getCurrency(rs
							.getDouble("INTERESTINSTALLAMT")));
				} else {
					basicBean.setIntinstallmentamt("0.0");
				}

				if (rs.getString("PREVIOUSOUTSTANDINGAMT") != null) {
					basicBean.setOutstndamount(rs
							.getString("PREVIOUSOUTSTANDINGAMT"));
					basicBean.setOutstndamountCurr(commonUtil
							.getCurrency(Double.parseDouble(basicBean
									.getOutstndamount())));
				} else {
					basicBean.setOutstndamount("");
				}
				if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
					basicBean.setPrpsecvrdclse(rs
							.getString("PURPOSEOPTIONCVRDCLUSE"));
				} else {
					basicBean.setPrpsecvrdclse("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("EMPLOYEENAME")));
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmpnamewthblk(rs.getString("EMPLOYEENAME")
							.toUpperCase());
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {
					basicBean.setTrust("");
				}

				if (rs.getString("LOD") != null) {
					basicBean.setLodInfo(rs.getString("LOD"));
				} else {
					basicBean.setLodInfo("");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}

				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					basicBean.setEmployeeNo("");
				}

				basicBean.setPensionNo(pensionNo);

				if (rs.getString("DEPARTMENT") != null) {
					basicBean.setDepartment(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DEPARTMENT")));
				} else {
					basicBean.setDepartment("");
				}
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}
				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("FHNAME")));
				} else {
					basicBean.setFhName("");
				}
				if (rs.getString("EMOLUMENTS") != null) {
					basicBean.setEmoluments(rs.getString("EMOLUMENTS"));
				} else {
					basicBean.setEmoluments("0.0");
				}
				log.info("getCPFAdvanceForm2Info::Emolument:::::"
						+ basicBean.getEmoluments());
				if (rs.getString("ADVANCETYPE") != null) {
					basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
				} else {
					basicBean.setAdvanceType("");
				}
				if (rs.getString("ADVNCERQDDEPEND") != null) {
					basicBean.setAdvncerqddepend(rs
							.getString("ADVNCERQDDEPEND"));
				} else {
					basicBean.setAdvntrnsdt("");
				}
				if (rs.getString("UTLISIEDAMNTDRWN") != null) {
					basicBean.setUtlisiedamntdrwn(rs
							.getString("UTLISIEDAMNTDRWN"));
				} else {
					basicBean.setUtlisiedamntdrwn("");
				}
				if (rs.getString("PURPOSEOPTIONTYPE") != null) {
					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				} else {
					basicBean.setPurposeOptionType("");
				}
				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
				} else {
					basicBean.setPurposeType("");
				}
				if (rs.getString("TOTALINATALLMENTS") != null) {
					basicBean.setTotalInst(rs.getString("TOTALINATALLMENTS"));
				} else {
					basicBean.setTotalInst("0");
				}
				if (rs.getString("REQUIREDAMOUNT") != null) {
					basicBean.setAdvnceRequest(rs.getString("REQUIREDAMOUNT"));
					basicBean.setAdvnceRequestCurr(commonUtil
							.getCurrency(Double.parseDouble(basicBean
									.getAdvnceRequest())));
				} else {
					basicBean.setAdvnceRequest("0.0");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					basicBean.setDateOfBirth("---");
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {
					basicBean.setDateOfJoining("");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName()
						.toUpperCase(), dateOfBirth, commonUtil.leadingZeros(5,
						pensionNo));
				basicBean.setPfid(pfid);

				long noOfYears = 0;
				noOfYears = rs.getLong("ENTITLEDIFF");

				/*
				 * if (noOfYears > 0) {
				 * basicBean.setDateOfMembership(basicBean.getDateOfJoining()); }
				 * else { basicBean.setDateOfMembership("01-Apr-1995"); }
				 */
				basicBean.setDateOfMembership(basicBean.getDateOfJoining());
/*				toDate = commonUtil.getCurrentDate("MM-yyyy");
				String currentDtInfo[] = toDate.split("-");
				currentMonth = Integer.toString(Integer
						.parseInt(currentDtInfo[0]) - 1);
				currentYear = currentDtInfo[1];
				lastYear = Integer
						.toString(Integer.parseInt(currentDtInfo[1]) - 1);
				toDate = "";
				try {
					toDate = commonUtil.converDBToAppFormat(currentMonth + "-"
							+ currentYear, "MM-yyyy", "MMM-yyyy");
					toDate = "01-" + toDate;
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// fromDate="01-Apr-"+lastYear;
				fromDate = "01-Apr-2008";
				toDate = "28-Feb-2009";
				String pensionInfo = financeReport.getPensionInfo(pensionNo,
						basicBean.getEmployeeName(), fromDate, toDate);*/
			String pensionInfo =getPFCardClosingBalance(pensionNo,
					basicBean.getEmployeeName());
			log.info("=======================getPFCardClosingBalance========================="+pensionInfo);
				/*
				 * String pensionInfo = financeReport.getPensionInfo(pensionNo,
				 * "2007");
				 */
				String[] pensionArray = pensionInfo.split(",");
				subscriptionAmnt = pensionArray[2];
				subscriptionAmntInt = pensionArray[3];
				aaiContribution = pensionArray[4];
				aaiContributionAmntInt = pensionArray[5];

				/*
				 * if (!subscriptionAmnt.equals("") &&
				 * !subscriptionAmntInt.equals("")) { subscriptionAmnt =
				 * Double.toString(Double .parseDouble(subscriptionAmnt) +
				 * Double.parseDouble(subscriptionAmntInt)); } else if
				 * (!subscriptionAmnt.equals("") &&
				 * !subscriptionAmntInt.equals("") &&
				 * !aaiContribution.equals("") &&
				 * basicBean.getAdvanceType().equals("PFW") &&
				 * basicBean.getPurposeType().equals("HBA")) { subscriptionAmnt =
				 * Double.toString(Double .parseDouble(subscriptionAmnt) +
				 * Double.parseDouble(subscriptionAmntInt) +
				 * Double.parseDouble(aaiContribution)); }else{
				 * 
				 * subscriptionAmnt = Double.toString(Double
				 * .parseDouble(subscriptionAmnt) +
				 * Double.parseDouble(subscriptionAmntInt)
				 * -Double.parseDouble("1000")); }
				 */

				if (formType.equals("Y")) {
					if (basicBean.getAdvanceType().equals("CPF")) {
						try {
							loadElgAmnt = this.getCPFAdvanceEmoluments(
									basicBean.getEmoluments(),
									subscriptionAmnt, basicBean
											.getAdvnceRequest(),
									prssOutStandAmnt, basicBean
											.getAdvanceType(), basicBean
											.getPurposeType(), basicBean
											.getPurposeOptionType());
						} catch (InvalidDataException e) {
							throw e;
						}
					} else {
						try {
							loadElgAmnt = this.getPFWAdvanceEmoluments(
									basicBean.getEmoluments(),
									subscriptionAmnt, basicBean
											.getAdvnceRequest(),
									prssOutStandAmnt, basicBean
											.getAdvanceType(), basicBean
											.getPurposeType(), basicBean
											.getPurposeOptionType());
						} catch (InvalidDataException e) {
							throw e;
						}
					}

					String[] lodElgblAmnt = loadElgAmnt.split(",");
					basicBean.setMnthsemoluments(lodElgblAmnt[0]);

					if (rs.getString("RECOMMENDEDAMT") != null) {

						basicBean.setAmntRecommended(rs
								.getString("RECOMMENDEDAMT"));
					} else {

						basicBean.setAmntRecommended(lodElgblAmnt[1]);
					}

					basicBean.setEmpshare(lodElgblAmnt[2]);

					if (!basicBean.getAdvanceType().equals("CPF")) {
						cpfFund = Long.parseLong(df.format(Math.round(Double
								.parseDouble(lodElgblAmnt[2]))))
								+ Long.parseLong(df.format(Math.round(Double
										.parseDouble(aaiContribution))));
						basicBean.setCPFFund(Long.toString(cpfFund));

						if (basicBean.getPurposeType().equals("HE")
								|| basicBean.getPurposeType()
										.equals("MARRIAGE")) {
							long chckElgble = Long.parseLong(df
									.format(Math.round(Double
											.parseDouble(lodElgblAmnt[2]))))
									- Long
											.parseLong(df
													.format(Math
															.round(Double
																	.parseDouble(lodElgblAmnt[1]))));
							if (chckElgble < 1000) {
								basicBean
										.setAuthrizedRemarks("CPF Account should be maintain Rs.1000");
							}
						}

						basicBean.setAmntRecommendedDscr(commonUtil
								.ConvertInWords(Double
										.parseDouble(lodElgblAmnt[1])));
					}

					basicBean.setSubscriptionAmt(subscriptionAmnt);
					basicBean.setContributionAmt(aaiContribution);
				} else {
					if (rs.getString("SUBSCRIPTIONAMNT") != null) {
						basicBean.setEmpshare(rs.getString("SUBSCRIPTIONAMNT"));
						basicBean
								.setEmpshareCurr(commonUtil
										.getCurrency(Double.parseDouble(rs
												.getString("SUBSCRIPTIONAMNT"))));
					} else {
						basicBean.setEmpshare("0.00");
					}
					if (rs.getString("APPROVEDAMNT") != null) {
						basicBean.setAmntAproved(rs.getString("APPROVEDAMNT"));
						basicBean.setAmntAprovedCurr(commonUtil
								.getCurrency(Double.parseDouble(rs
										.getString("APPROVEDAMNT"))));
						basicBean.setAmntRecommendedWords(commonUtil
								.ConvertInWords(Double.parseDouble(rs
										.getString("APPROVEDAMNT"))));
					} else {
						basicBean.setAmntAproved("0.00");
						
					}
					if (rs.getString("EMOLUMENTS") != null) {
						if (basicBean.getPurposeType().equals("OBMARRIAGE")) {
							basicBean.setMnthsemoluments(Double
									.toString(Double.parseDouble(rs
											.getString("EMOLUMENTS")) * 6));
						} else {
							basicBean.setMnthsemoluments(Double
									.toString(Double.parseDouble(rs
											.getString("EMOLUMENTS")) * 3));
						}
						basicBean.setMnthsemolumentsCurr(commonUtil
								.getCurrency(Double.parseDouble(basicBean
										.getMnthsemoluments())));
					} else {
						basicBean.setMnthsemoluments("0.00");
						;
					}
					basicBean.setAmntRecommendedDscr(commonUtil
							.ConvertInWords(Double.parseDouble(basicBean
									.getAmntAproved())));
				}

				bankMasterBean = loadEmployeeBankInfo(pensionNo);
				reportList.add(basicBean);
				reportList.add(bankMasterBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;

	}

	// New Method------

	/*
	 * public int saveCPFCheckList(AdvanceCPFForm2Bean advanceBean) { Connection
	 * con = null; Statement st = null; Statement insertSt = null; int
	 * updatedRecords = 0; String updateQuery = ""; String purposeType = "";
	 * log.info("saveCPFCheckList::Basic Info" + advanceBean.getPensionNo());
	 * 
	 * try { con = commonDB.getConnection(); st = con.createStatement();
	 * insertSt = con.createStatement();
	 * 
	 * String fmlyDOB = "";
	 * 
	 * log.info(advanceBean.getPensionNo());
	 * log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());
	 * updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET TOTALINATALLMENTS='" +
	 * advanceBean.getTotalInst() + "',PURPOSETYPE='" +
	 * advanceBean.getPurposeType() + "',REQUIREDAMOUNT='" +
	 * advanceBean.getAmntRecommended() + "',PREVIOUSOUTSTANDINGAMT='" +
	 * advanceBean.getOutstndamount() + "',CHKLISTFLAG='" + "Y" + "',LOD='" +
	 * advanceBean.getLodInfo() + "' WHERE ADVANCETRANSID='" +
	 * advanceBean.getAdvanceTransID() + "' AND PENSIONNO='" +
	 * advanceBean.getPensionNo() + "'";
	 * log.info("CPFPTWAdvanceDAO::saveCPFCheckList" + updateQuery);
	 * updatedRecords = st.executeUpdate(updateQuery);
	 * 
	 * 
	 * CPFPFWTransInfo cpfInfo=new
	 * CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());
	 * cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(),advanceBean.getAdvanceTransID(),"CPF",Constants.APPLICATION_PROCESSING_CPF_CHECK_LIST);
	 *  } catch (SQLException e) { log.printStackTrace(e); } catch (Exception e) {
	 * log.printStackTrace(e); } finally { commonDB.closeConnection(null, st,
	 * con); } return updatedRecords; }
	 */
//	Modified By Radha On 20-Jul-2011 for saving designation in transaction table
	public int saveCPFCheckList(AdvanceCPFForm2Bean advanceBean) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0;
		String updateQuery = "";
		String purposeType = "";
		log.info("saveCPFCheckList::Basic Info" + advanceBean.getPensionNo());
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			String fmlyDOB = "";

			log.info(advanceBean.getPensionNo());
			log.info(advanceBean.getPurposeOptionType().toUpperCase().trim());
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM SET TOTALINATALLMENTS='"
					+ advanceBean.getTotalInst()
					+ "',PURPOSETYPE='"
					+ advanceBean.getPurposeType()
					+ "',REQUIREDAMOUNT='"
					+ advanceBean.getAmntRecommended()
					+ "',PREVIOUSOUTSTANDINGAMT='"
					+ advanceBean.getOutstndamount()
					+ "',CHKLISTFLAG='"
					+ "Y"
					+ "',LOD='"
					+ advanceBean.getLodInfo()
					+ "' WHERE ADVANCETRANSID='"
					+ advanceBean.getAdvanceTransID()
					+ "' AND PENSIONNO='"
					+ advanceBean.getPensionNo() + "'";
			log.info("CPFPTWAdvanceDAO::saveCPFCheckList" + updateQuery);
			updatedRecords = st.executeUpdate(updateQuery);

			BeanUtils.copyProperties(transBean, advanceBean);

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
					.getLoginUserId(), advanceBean.getLoginUserName(),
					advanceBean.getLoginUnitCode(), advanceBean
							.getLoginRegion(), advanceBean
							.getLoginUserDispName());
			cpfInfo.createCPFPFWTrans(advanceBean.getPensionNo(), advanceBean
					.getAdvanceTransID(),advanceBean.getLoginUserDesignation() ,"CPF",
					Constants.APPLICATION_PROCESSING_CPF_CHECK_LIST, transBean);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	public String buildSearchQueryForFinalSettlement(
			AdvanceSearchBean searchBean, String unitName, String flag) {

		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForFinalSettlement-- Entering Method");

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "";

		sqlQuery = "SELECT EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DATEOFBIRTH as DATEOFBIRTH,NVL(EN.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,EMPFID.PENSIONNO,EMPFID.AIRPORTCODE AS AIRPORTCODE_PERSNL ,EMPFID.REGION AS REGION_PERSNL,"
				+ "EN.NSSANCTIONNO AS NSSANCTIONNO,EN.VERIFIEDBY AS VERIFIEDBY,EN.TRANSDT AS TRANSDT,"
				+ "EN.PAYMENTDT AS PAYMENTDT,EN.NSSANCTIONEDDT AS NSSANCTIONEDDT,EN.SEPERATIONRESAON AS SEPERATIONRESAON,EN.ARREARTYPE AS ARREARTYPE ,EN.REGION AS REGION,EN.AIRPORTCODE AS  AIRPORTCODE FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO=EMPFID.PENSIONNO AND EN.DELETEFLAG='N' ";

		log.info("--------flag in build query--------" + flag);
		log
				.info("searchBean.getPensionNo()-------"
						+ searchBean.getPensionNo());

		/*
		 * if(flag.equals("FSVerification")){ sqlQuery+=" AND EN.VERIFIEDBY is
		 * null "; }
		 */

		if (flag.equals("arrear")) {
			whereClause.append(" EN.NSTYPE='ARREAR'");
			whereClause.append(" AND ");
		} else {
			whereClause.append(" EN.NSTYPE='NON-ARREAR'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EN.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}

		if (!searchBean.getNssanctionno().equals("")) {
			whereClause.append(" EN.NSSANCTIONNO='"
					+ searchBean.getNssanctionno() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getSeperationreason().equals("")) {
			whereClause.append(" EN.SEPERATIONRESAON='"
					+ searchBean.getSeperationreason() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" EMPFID.EMPLOYEENAME='"
					+ searchBean.getEmployeeName().toUpperCase() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getArreartype().equals("")) {
			whereClause.append(" EN.ARREARTYPE='" + searchBean.getArreartype()
					+ "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (searchBean.getPensionNo().equals("")
				&& searchBean.getNssanctionno().equals("")
				&& searchBean.getSeperationreason().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getArreartype().equals("") && flag.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY NSSANCTIONEDDT desc,NSSANCTIONNO desc";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForFinalSettlement Leaving Method");
		return dynamicQuery;

	}
//By Radha on 07-Feb-2012 for Profile Based Condition
	public String buildSearchQueryForPrintFinalSettlement(
			AdvanceSearchBean searchBean, String region, String unitName,
			String flag) {

		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForPrintFinalSettlement-- Entering Method");

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "",profileCondition="",verifiedBy="";
		
		
		if(searchBean.getFsType().equals("NON-ARREAR")){
			verifiedBy="PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED";
		}else{
			verifiedBy="FINANCE,SRMGRREC,DGMREC,APPROVED";
		}
		sqlQuery = "SELECT EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.DESEGNATION AS DESEGNATION,EMPFID.PENSIONNO,EMPFID.AIRPORTCODE AS ,EMPFID.REGION AS REGION,"
				+ "EN.NSSANCTIONNO AS NSSANCTIONNO,EN.VERIFIEDBY AS VERIFIEDBY,EN.TRANSDT AS TRANSDT,"
				+ "EN.PAYMENTDT AS PAYMENTDT,EN.NSSANCTIONEDDT AS NSSANCTIONEDDT,EN.NETCONTRIBUTION AS NETCONTRIBUTION, EN.SEPERATIONRESAON AS SEPERATIONRESAON FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO=EMPFID.PENSIONNO AND EN.DELETEFLAG='N' AND EN.NSSANCTIONEDDT IS NOT NULL "
				+ " AND VERIFIEDBY='"+verifiedBy+"' AND NSTYPE='"+searchBean.getFsType()+"'";

		log.info("--------flag in build query--------" + flag);

		/*
		 * if(flag.equals("FSVerification")){ sqlQuery+=" AND EN.VERIFIEDBY is
		 * null "; }
		 */

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EN.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}

		if (!searchBean.getSanctionno().equals("")) {
			whereClause.append(" EN.NSSANCTIONNO='"
					+ searchBean.getSanctionno() + "'");
			whereClause.append(" AND ");
		}
		if (!region.equals("")) {
			whereClause.append(" EMPFID.REGION='" + region + "'");
			whereClause.append(" AND ");
		}
		log.info("=====searchBean.getLoginProfile()====="+searchBean.getLoginProfile()+"==searchBean.getLoginRegion()=="+searchBean.getLoginRegion());
		if(searchBean.getLoginProfile().equals("R")){
			if(!searchBean.getLoginRegion().equals("CHQIAD")){
				whereClause.append(" EN.AIRPORTCODE IN (SELECT UNITNAME   FROM EMPLOYEE_UNIT_MASTER EUM     WHERE EUM.REGION ='"+searchBean.getLoginRegion()+"' AND  EUM.ACCOUNTTYPE = 'RAU')");
				whereClause.append(" AND ");
			}else{
				if (!unitName.equals("")) {
					whereClause.append(" EMPFID.AIRPORTCODE='" + unitName + "'");
					whereClause.append(" AND ");
				}
			}
		}else{ 
		if (!unitName.equals("")) {
			whereClause.append(" EMPFID.AIRPORTCODE='" + unitName + "'");
			whereClause.append(" AND ");
		}
		}
		if (!searchBean.getSeperationreason().equals("")) {
			whereClause.append(" EN.SEPERATIONRESAON='"
					+ searchBean.getSeperationreason() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" EMPFID.EMPLOYEENAME='"
					+ searchBean.getEmployeeName() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (searchBean.getPensionNo().equals("")
				&& searchBean.getSanctionno().equals("")
				&& searchBean.getSeperationreason().equals("")
				& region.equals("") && unitName.equals("")
				&& searchBean.getEmployeeName().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY NSSANCTIONEDDT desc,NSSANCTIONNO desc";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForPrintFinalSettlement Leaving Method");
		return dynamicQuery;

	}

	public ArrayList searchFinalSettlement(AdvanceSearchBean advanceSearchBean,
			String flag) {
		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchFinalSettlement"
				+ advanceSearchBean.getPensionNo());
		String pensionNo = "", dateOfBirth = "", pfid = "", unitCode = "", unitName = "", region = "", selectQuery = "",transDt="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			String reg = commonUtil.getAirportsByProfile(advanceSearchBean
					.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
					advanceSearchBean.getLoginRegion());

			if (!reg.equals("")) {
				String[] regArr = reg.split(",");
				unitCode = regArr[0];
				region = regArr[1];
			}

			if (!unitCode.equals("-"))
				unitName = this.getUnitName(advanceSearchBean, con);

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");

			if ((flag.equals("FSSearch")) || (flag.equals("FSVerification"))) {
				flag = "nonarrear";
			} else if (flag.equals("FSArrearSearch")) {
				flag = "arrear";
			}

			selectQuery = this.buildSearchQueryForFinalSettlement(
					advanceSearchBean, unitName, flag);

			log.info("CPFPTWAdvanceDAO::searchFinalSettlement" + selectQuery);
			ResultSet rs = st.executeQuery(selectQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();
				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}
				if (rs.getDate("PAYMENTDT") != null) {
					searchBean.setPaymentdt(commonUtil.converDBToAppFormat(rs
							.getDate("PAYMENTDT")));
				} else {
					searchBean.setPaymentdt("---");
				}

				if (rs.getDate("NSSANCTIONEDDT") != null) {
					searchBean.setSanctiondt(commonUtil.converDBToAppFormat(rs
							.getDate("NSSANCTIONEDDT")));
				} else {
					searchBean.setSanctiondt("---");
				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, commonUtil.leadingZeros(5, pensionNo));
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setNssanctionno(rs.getString("NSSANCTIONNO"));
				searchBean.setDesignation(rs.getString("DESEGNATION"));
				 
				searchBean
						.setSeperationreason(rs.getString("SEPERATIONRESAON"));

				if (rs.getString("VERIFIEDBY") != null) {

					if (flag.equals("FSVerification")) {
						searchBean.setVerifiedBy("A");
					} else {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL")) {
							searchBean.setVerifiedBy("Verified by Personnel");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance,Senior Manager");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance,Senior Manager,DGM");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED")) {
							searchBean.setVerifiedBy("Approved");
						} else if (rs.getString("VERIFIEDBY").equals("FINANCE")) {
							searchBean.setVerifiedBy("Verified by Finance");
						} else if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC")) {
							searchBean
									.setVerifiedBy("Verified by Finance,Senior Manager");
						} else if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC,DGMREC")) {
							searchBean
									.setVerifiedBy("Verified by Finance,Senior Manager,DGM");
						} else if (rs.getString("VERIFIEDBY").equals(
								"FINANCE,SRMGRREC,DGMREC,APPROVED")) {
							searchBean.setVerifiedBy("Approved");
						}
					}
					searchBean.setTransactionStatus(rs.getString("VERIFIEDBY"));

				} else {
					searchBean.setVerifiedBy("N");
				}

				if (rs.getString("TRANSDT") != null) {
					searchBean.setTransdt(CommonUtil.getDatetoString(rs
							.getDate("TRANSDT"), "dd-MMM-yyyy"));
				}

				if (rs.getString("ARREARTYPE") != null) {
					searchBean.setArreartype(rs.getString("ARREARTYPE"));
				} else {
					searchBean.setArreartype("");
				}
				

				// Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = commonUtil.converDBToAppFormat(rs
							.getDate("TRANSDT"));
					Date transdate = df.parse(transDt); 
					if(transdate.after(new Date("08-May-2012"))){
						searchBean.setStation(rs.getString("AIRPORTCODE"));
						searchBean.setRegion(rs.getString("REGION"));
					}else{
						searchBean.setStation(rs.getString("AIRPORTCODE_PERSNL"));
						searchBean.setRegion(rs.getString("REGION_PERSNL"));
					}
				}else{
					searchBean.setStation(rs.getString("AIRPORTCODE_PERSNL"));
					searchBean.setRegion(rs.getString("REGION_PERSNL"));					
				}
				
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;
	}
	//By Radha on 06-Feb-2012 for seperating region & unit wise
	public ArrayList searchPrintSanctionOrder(
			AdvanceSearchBean advanceSearchBean, String flag) {
		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchPrintSanctionOrder"
				+ advanceSearchBean.getPensionNo());
		String pensionNo = "", dateOfBirth = "", pfid = "", unitCode = "", unitName = "", region = "", userStation = "", userRegion = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			String reg = commonUtil.getAirportsByProfile(advanceSearchBean
					.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
					advanceSearchBean.getLoginRegion());

			if (!reg.equals("")) {
				String[] regArr = reg.split(",");
				unitCode = regArr[0];
				region = regArr[1];
			}
			log.info("unitCode" + unitCode + "region" + region);
			if (!unitCode.equals("-"))
				unitName = this.getUnitName(advanceSearchBean, con);

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");
			if (!unitName.equals("-")) {
				userStation = unitName;
			} else {
				userStation = "";
			}
			if (!region.equals("-")) {
				userRegion = region;
			} else {
				userRegion = "";
			}

			String selectQuery = this.buildSearchQueryForPrintFinalSettlement(
					advanceSearchBean, userRegion, userStation, flag);
			log.info("CPFPTWAdvanceDAO::searchFinalSettlement" + selectQuery);
			ResultSet rs = st.executeQuery(selectQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();
				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}
				if (rs.getDate("PAYMENTDT") != null) {
					searchBean.setPaymentdt(commonUtil.converDBToAppFormat(rs
							.getDate("PAYMENTDT")));
				} else {
					searchBean.setPaymentdt("---");
				}

				if (rs.getDate("NSSANCTIONEDDT") != null) {
					searchBean.setSanctiondt(commonUtil.converDBToAppFormat(rs
							.getDate("NSSANCTIONEDDT")));
				} else {
					searchBean.setSanctiondt("---");
				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, commonUtil.leadingZeros(5, pensionNo));
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setSanctionno(rs.getString("NSSANCTIONNO"));
				searchBean.setDesignation(rs.getString("DESEGNATION"));
				searchBean.setRegion(rs.getString("REGION"));
				searchBean.setStation(rs.getString("AIRPORTCODE"));
				searchBean
						.setSeperationreason(rs.getString("SEPERATIONRESAON"));

				if (rs.getString("VERIFIEDBY") != null) {

					if (flag.equals("FSVerification")) {
						searchBean.setVerifiedBy("A");
					} else {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL")) {
							searchBean.setVerifiedBy("Verified by Personnel");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance,Senior Manager");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC")) {
							searchBean
									.setVerifiedBy("Verified by Personnel,Finance,Senior Manager,DGM");
						} else if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED")) {
							searchBean.setVerifiedBy("Approved");
						}
					}

				} else {
					searchBean.setVerifiedBy("N");
				}

				if (rs.getString("TRANSDT") != null) {
					searchBean.setTransdt(CommonUtil.getDatetoString(rs
							.getDate("TRANSDT"), "dd-MMM-yyyy"));
				}
				if (rs.getString("NETCONTRIBUTION") != null) {
					searchBean.setNetcontribution(rs
							.getString("NETCONTRIBUTION"));
				} else {
					searchBean.setNetcontribution("---");
				}
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;
	}

//	By Radha on 15-Feb-2012 for  PFW Sanction Order Region & Unit Wise
	public ArrayList searchPrintPFWSanctionOrder(
			AdvanceSearchBean advanceSearchBean) {
		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchPrintPFWSanctionOrder"
				+ advanceSearchBean.getPensionNo());
		String pensionNo = "", dateOfBirth = "", pfid = "", unitCode = "", unitName = "", region = "", userStation = "", userRegion = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			String reg = commonUtil.getAirportsByProfile(advanceSearchBean
					.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
					advanceSearchBean.getLoginRegion());

			if (!reg.equals("")) {
				String[] regArr = reg.split(",");
				unitCode = regArr[0];
				region = regArr[1];
			}
			log.info("unitCode" + unitCode + "region" + region);
			if (!unitCode.equals("-"))
				unitName = this.getUnitName(advanceSearchBean, con);

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");
			if (!unitName.equals("-")) {
				userStation = unitName;
			} else {
				userStation = "";
			}
			if (!region.equals("-")) {
				userRegion = region;
			} else {
				userRegion = "";
			}

			String selectQuery = this.buildSearchQueryForPrintPFWSanctionOrder(
					advanceSearchBean, userRegion, userStation);
			log.info("CPFPTWAdvanceDAO::searchPrintPFWSanctionOrder" + selectQuery);
			ResultSet rs = st.executeQuery(selectQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();
				searchBean.setRequiredamt(rs.getString("REQUIREDAMOUNT"));
				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				searchBean.setDesignation(rs.getString("DESEGNATION"));

				searchBean.setAdvanceStatus(rs.getString("ADVANCETRANSSTATUS"));

				searchBean.setTransMnthYear(CommonUtil.getDatetoString(rs
						.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("dateofbirth") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {
					dateOfBirth = "---";
				}
				if (rs.getString("TOTALINATALLMENTS") != null) {
					searchBean.setCpfIntallments(rs
							.getString("TOTALINATALLMENTS"));
				} else {
					searchBean.setCpfIntallments("");
				}

				if (rs.getString("SANCTIONDATE") != null) {
					searchBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					searchBean.setSanctiondt("");
				}
				if (rs.getString("PAYMENTINFO") != null) {
					searchBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {
					searchBean.setPaymentinfo("");
				}

				if (rs.getString("VERIFIEDBY") != null) {
					if (advanceSearchBean.getFormName().equals("PFWForm3")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"PFWForm4")) {

						if (rs.getString("FINALTRANSSTATUS") != null) {
							searchBean.setFinalTrnStatus(rs
									.getString("FINALTRANSSTATUS"));
						}

						if (rs.getString("VERIFIEDBY").equals(
								"PERSONNEL,FINANCE"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if ((advanceSearchBean.getFormName()
							.equals("PFWForm2"))
							|| (advanceSearchBean.getFormName()
									.equals("CPFVerification"))) {
						if (rs.getString("ADVANCETRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("ADVANCETRANSSTATUS"));
						} else {
							searchBean.setAdvanceStatus("N");
						}

					} else if (advanceSearchBean.getFormName().equals(
							"CPFRecommendation")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"CPFApproval")) {
						if (rs.getString("VERIFIEDBY").equals("PERSONNEL,REC"))
							searchBean.setAdvanceStatus("N");
						else
							searchBean.setAdvanceStatus("A");
					} else if (advanceSearchBean.getFormName().equals(
							"AdvanceSearch")) {
						if (rs.getString("ADVANCETRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("ADVANCETRANSSTATUS"));
						}
					} else {
						if (rs.getString("FINALTRANSSTATUS") != null) {
							searchBean.setAdvanceStatus(rs
									.getString("FINALTRANSSTATUS"));
						}

					}
					searchBean.setVerifiedBy(rs.getString("VERIFIEDBY"));
				} else {

					if (rs.getString("ADVANCETRANSSTATUS") != null) {
						searchBean.setAdvanceStatus(rs
								.getString("ADVANCETRANSSTATUS"));
					} else {
						searchBean.setAdvanceStatus("N");
					}
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					if ((advanceSearchBean.getFormName().equals("PFWCheckList") || advanceSearchBean
							.getFormName().equals("CPFCheckList"))
							&& rs.getString("CHKLISTFLAG").equals("Y"))
						searchBean.setAdvanceStatus("Y");
				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setAdvanceType(rs.getString("ADVANCETYPE")
						.toUpperCase());
				searchBean.setPurposeType(rs.getString("PURPOSETYPE")
						.toUpperCase());

				searchBean.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
				searchBean.setAdvanceTransIDDec(searchBean.getAdvanceType()
						.toUpperCase()
						+ "/"
						+ searchBean.getPurposeType().toUpperCase()
						+ "/"
						+ rs.getString("ADVANCETRANSID"));

				if (!searchBean.getVerifiedBy().equals("")) {

					if (rs.getString("FINALTRANSSTATUS") != null) {
						if (rs.getString("FINALTRANSSTATUS").equals("N")) {
							if (searchBean.getVerifiedBy().equals("PERSONNEL")) {
								searchBean
										.setTransactionStatus("Verified by Personnel");
							} else if ((searchBean.getVerifiedBy().equals(
									"PERSONNEL,FINANCE"))|| (searchBean.getVerifiedBy().equals(
									"PERSONNEL,REC")) ) {
								searchBean
										.setTransactionStatus("Verified by Personnel,Finance");
							} else if (searchBean.getVerifiedBy().equals(
									"PERSONNEL,FINANCE,RHQ")) {
								searchBean
										.setTransactionStatus("Verified by RHQ");
							}
						} else {
							searchBean.setTransactionStatus("Approved");
						}
					}

				} else {
					searchBean.setTransactionStatus("New");
				}
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;
	}
	
	public String buildSearchQueryForPrintPFWSanctionOrder(AdvanceSearchBean searchBean,
			String region, String unitName) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",profilecondition="";
		profileName=searchBean.getProfileName(); 
		
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,EMPFID.DESEGNATION AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N' "  
                + "AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY='PERSONNEL,FINANCE,RHQ' AND FINALTRANSSTATUS='A' AND EAF.ADVANCETRANSSTATUS ='A' AND EAF.SANCTIONDATE IS NOT NULL";

		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EAF.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EAF.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}
		log.info("=====searchBean.getLoginProfile()====="+searchBean.getLoginProfile()+"==searchBean.getLoginRegion()=="+searchBean.getLoginRegion());
		if(searchBean.getLoginProfile().equals("R")){
			if(!searchBean.getLoginRegion().equals("CHQIAD")){
				whereClause.append(" EAF.AIRPORTCODE IN (SELECT UNITNAME   FROM EMPLOYEE_UNIT_MASTER EUM     WHERE EUM.REGION ='"+searchBean.getLoginRegion()+"')");
				whereClause.append(" AND ");
			}else{
				if (!unitName.equals("")) {
					whereClause.append(" EMPFID.AIRPORTCODE='" + unitName + "'");
					whereClause.append(" AND ");
				}
			}
		}else{ 
		if (!unitName.equals("")) {
			whereClause.append(" EMPFID.AIRPORTCODE='" + unitName + "'");
			whereClause.append(" AND ");
		}
		}
		if (!searchBean.getAdvanceTransID().equals("")) {
			whereClause.append(" EAF.ADVANCETRANSID ='"
					+ searchBean.getAdvanceTransID() + "'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" LOWER(EMPFID.employeename) like'%"
					+ searchBean.getEmployeeName().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getAdvanceTransyear().equals("")) {
			try {
				whereClause.append(" EAF.ADVANCETRANSDT='"
						+ commonUtil.converDBToAppFormat(searchBean
								.getAdvanceTransyear(), "dd/MM/yyyy",
								"dd-MMM-yyyy") + "'");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		} 
		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EAF.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getPurposeType().equals("")) {
			whereClause.append(" LOWER(EAF.PURPOSETYPE) like'%"
					+ searchBean.getPurposeType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
		if (!searchBean.getAdvanceTrnStatus().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETRANSSTATUS) like'%"
					+ searchBean.getAdvanceTrnStatus().toLowerCase().trim()
					+ "%'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")
				&& searchBean.getAdvanceTransID().equals("")
				&& searchBean.getAdvanceTrnStatus().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getAdvanceType().equals("")
				&& searchBean.getVerifiedBy().equals("")
				&& searchBean.getPurposeType().equals("")
				&& searchBean.getAdvanceTransyear().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);		 
		dynamicQuery = query.toString();

		return dynamicQuery;

	}
	
	// New Method ---------
	//	BY Radha On 07-May-2012 for saving Designation of the Employee
	//By Radha on 12-Sep-2011 for saving intrest value
	public String addFinalSettlementInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0, insertedRecord = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", insertQry = "", advanceTrnsStatus = "N", message = "", insertHBAQuery = "", insertHEQuery = "";
		String wthDrwlTrnsdt = "", marriageDate = "", seperationdt = "", nsSanctionNo = "", placeOfPostingValue = "", appointmentDt = "",rateofinterest="";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			nsSanctionNo = this.getNoteSheetSequence(con);

			String fmlyDOB = "";

			log.info("======Posting Details====="
					+ advanceBean.getPostingdetails());

			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getAppointmentdate().trim().equals("")) {
				appointmentDt = commonUtil.converDBToAppFormat(advanceBean
						.getAppointmentdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getPostingdetails().equals("")) {
				placeOfPostingValue = "Y";
			} else {
				placeOfPostingValue = "N";
			}
			rateofinterest =Constants.RATEOFINTEREST;
			insertQuery = "INSERT INTO EMPLOYEE_ADVANCE_NOTEPARAM(NSSANCTIONNO,PENSIONNO,SEPERATIONDT,SEPERATIONRESAON,LOD,TRANSDT,PAYMENTINFO,PLACEOFPOSTING,REASONFORRESIGNATION,NSTYPE,REGION,AIRPORTCODE,RATEOFINTEREST,DESIGNATION) VALUES("
					+ Long.parseLong(nsSanctionNo)
					+ ",'"
					+ advanceBean.getPensionNo()
					+ "','"
					+ seperationdt
					+ "','"
					+ advanceBean.getSeperationreason()
					+ "','"
					+ advanceBean.getLodInfo()
					+ "','"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ advanceBean.getPaymentinfo()
					+ "','"
					+ placeOfPostingValue
					+ "','"
					+ advanceBean.getResignationreason()
					+ "','NON-ARREAR'"
					+ ",'"
					+ advanceBean.getRegion()
					+ "','"
					+ advanceBean.getStation()
					+ "',"
					+ rateofinterest
					+ ",'"
					+ advanceBean.getDesignation()+"')";
			log.info("insertQuery " + insertQuery);

			log.info("CPFPTWAdvanceDAO::addFinalSettlementInfo" + insertQuery);

			insertedRecords = st.executeUpdate(insertQuery);
			if (insertedRecords != 0) {

				insertQry = "INSERT INTO epis_noteparam_personal_info(PENSIONNO,SANCTIONNO,SEPERATIONREASON,EMPLOYEENAME,FHNAME,EMPLOYEEAGE,MARITALSTATUS,RELATION,ADDRESS,QUARTERALLOTED,QUARTERNO,COLONYNAME,STATION,PLACEOFDEATH) VALUES('"
						+ advanceBean.getPensionNo()
						+ "',"
						+ Long.parseLong(nsSanctionNo)
						+ ",'"
						+ advanceBean.getSeperationreason()
						+ "','"
						+ advanceBean.getEmployeeName()
						+ "','"
						+ advanceBean.getFhName()
						+ "','"
						+ advanceBean.getEmpage()
						+ "','"
						+ advanceBean.getMaritalstatus()
						+ "','"
						+ advanceBean.getEmprelation()
						+ "','"
						+ advanceBean.getEmpaddress()
						+ "','"
						+ advanceBean.getQuarterallotment()
						+ "','"
						+ advanceBean.getQuarterno()
						+ "','"
						+ advanceBean.getColonyname()
						+ "','"
						+ advanceBean.getEmpstation()
						+ "','"
						+ advanceBean.getDeathplace() + "')";

				log
						.info("CPFPTWAdvanceDAO::addFinalSettlementInfo----insertQry"
								+ insertQry);

				insertedRecord = st.executeUpdate(insertQry);
				//  For restricting the updation of personal Information Frm  25-Apr-2012 
				//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
				/*if (!advanceBean.getDesignation().equals("")) {
					String updateQry = "update employee_personal_info set DESEGNATION='"
							+ advanceBean.getDesignation()
							+ "', DEPARTMENT='"
							+ advanceBean.getDepartment()
							+ "', CPFACNO='"
							+ advanceBean.getCpfaccno() 
							+ "',AIRPORTCODE='"
							+ advanceBean.getStation()
							+ "',REGION='"
							+ advanceBean.getRegion()
							+ "',PERMANENTADDRESS='"
							+ advanceBean.getPermenentaddress()
							+ "',TEMPORATYADDRESS='"
							+ advanceBean.getPresentaddress()
							+ "',EMAILID='"
							+ advanceBean.getMailID()
							+ "',PHONENUMBER='"
							+ advanceBean.getPhoneno()
							+ "' where  PENSIONNO='"
							+ advanceBean.getPensionNo() + "'";
					log.info("==========update Query===========" + updateQry);
					int updatedRecord = st.executeUpdate(updateQry);
				}*/

				if (!nsSanctionNo.equals(""))
					bankBean.setTransId(nsSanctionNo);

				if (advanceBean.getPaymentinfo().equals("Y")) {
					this.addEmployeeBankInfo(con,bankBean, advanceBean
							.getPensionNo());
				}

				/*
				 * if (bankBean.getChkBankInfo().equals("N")) {
				 * this.addEmployeeBankInfo(bankBean, advanceBean
				 * .getPensionNo()); } else { String updateQuery = "update
				 * EMPLOYEE_BANK_INFO set BANKNAME='" + bankBean.getBankname() +
				 * "',BRANCHADDRESS='" + bankBean.getBranchaddress() +
				 * "',SAVINGBNKACCNO='" + bankBean.getBanksavingaccno() +
				 * "',NEFTRTGSCODE='" + bankBean.getBankemprtgsneftcode() + "'
				 * where PENSIONNO='" + advanceBean.getPensionNo() + "'";
				 * log.info("==========update Query===========" + updateQuery);
				 * int updatedRecords = st.executeUpdate(updateQuery); }
				 */
				message = nsSanctionNo;

				if (placeOfPostingValue.equals("Y")) {
					this.addPostingDetails(con, advanceBean);
				}

				log.info("Resignation Reason in DAO----------"
						+ advanceBean.getResignationreason());

				if (advanceBean.getResignationreason()
						.equals("otherdepartment")) {

					insertQuery = "INSERT INTO employee_noteparam_other_det(PENSIONNO,ORGANIZATIONNAME,ORGANIZATIONADDRESS,APPOINTMENTDT,POST,WORKINGPLACE) VALUES('"
							+ advanceBean.getPensionNo()
							+ "','"
							+ advanceBean.getOrganizationname()
							+ "','"
							+ advanceBean.getOrganizationaddress()
							+ "','"
							+ appointmentDt
							+ "','"
							+ advanceBean.getPostedas()
							+ "','" + advanceBean.getWorkingplace() + "')";
					log.info("insertQuery for Other Details------"
							+ insertQuery);

					insertedRecords = st.executeUpdate(insertQuery);

				}
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	 // On 07-May-2012 by Radha for getting Desigantion from Employee_advance_noteparam value 
	//	 On 12-Sep-2011 by Radha for getting rateofinterest value
	//	 On 28-Jul-2011 by Radha To show Difference betn Old n New Formats of  Death NoteSheet Reports
	public ArrayList finalSettlementVerificationApproval(String pensionNo,
			String sanctionNo, String frmName) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String transID = "", dateOfBirth = "", pfid = "", optionTypeDesc = "", oblCermonyDesc = "", nomineeNm = "", empName = "";
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean advBasicBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean otherDetBean = new AdvanceBasicReportBean();
		ArrayList reportList = new ArrayList();
		ArrayList postingDetList = new ArrayList();
		ArrayList empPersonalDetList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String purposeTye = "", sqlQuery = "", sanctionOrderFlag = "", netContAmt = "",transDt="",station="",region="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			sqlQuery = "SELECT PI.EMPLOYEENO AS EMPLOYEENO,PI.CPFACNO AS CPFACNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,NVL(AN.DESIGNATION,PI.DESEGNATION) AS DESIGNATION,PI.FHNAME AS FHNAME,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,"
					+ "PI.DATEOFJOINING AS DATEOFJOINING,PI.PERMANENTADDRESS AS PERMANENTADDRESS,PI.TEMPORATYADDRESS AS TEMPORATYADDRESS,PI.EMAILID AS EMAILID,PI.PHONENUMBER AS PHONENUMBER,"
					+ "AN.PENSIONNO AS PENSIONNO,AN.NSSANCTIONNO AS NSSANCTIONNO,AN.SEPERATIONRESAON,AN.SEPERATIONDT,AN.LOD AS LOD,AN.VERIFIEDBY AS VERIFIEDBY,AN.REASONFORRESIGNATION AS REASONFORRESIGNATION,AN.TRANSDT AS TRANSDT,AN.PAYMENTINFO AS PAYMENTINFO,"
					+ "AN.EMPSHARESUBSCRIPITION AS EMPSHARESUBSCRIPITION,AN.EMPSHARECONTRIBUTION AS EMPSHARECONTRIBUTION,AN.LESSCONTRIBUTION AS LESSCONTRIBUTION,AN.ADHOCAMOUNT AS ADHOCAMOUNT,AN.NETCONTRIBUTION AS NETCONTRIBUTION,AN.AMTADMITTEDDT AS AMTADMITTEDDT,AN.REMARKS AS REMARKS,"
					+ "AN.ARREARTYPE,AN.ARREARDATE,AN.FROMFINYEAR,AN.PAYMENTDT AS PAYMENTDT,AN.TOFINYEAR,AN.FROMREVISEDINTERESTRATE,AN.TOREVISEDINTERESTRATE,AN.TRUST,PI.GENDER AS GENDER,AN.SOFLAG AS SOFLAG,AN.RATEOFINTEREST AS RATEOFINTEREST,AN.POSTINGFLAG AS POSTINGFLAG,AN.POSTINGREGION AS POSTINGREGION,AN.POSTINGSTATION AS POSTINGSTATION,  "
					+ "AN.REGION AS REGION,AN.AIRPORTCODE AS  AIRPORTCODE FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM AN WHERE AN.PENSIONNO = PI.PENSIONNO AND AN.PENSIONNO ="
					+ pensionNo + " AND AN.NSSANCTIONNO=" + sanctionNo;

			log
					.info("-------finalSettlementVerificationApproval:sqlQuery-------"
							+ sqlQuery);

			String lod = "";

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("EMPLOYEENO") != null) {
					basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
				} else {
					if (frmName.equals("FSFormI")) {
						basicBean.setEmployeeNo("");
					} else {
						basicBean.setEmployeeNo("N/A");
					}
				}

				if (rs.getString("GENDER") != null) {
					basicBean.setGender(rs.getString("GENDER"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setGender("");
					} else {
						basicBean.setGender("N/A");
					}
				}
				if (rs.getString("CPFACNO") != null) {
					basicBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setCpfaccno("");
					} else {
						basicBean.setCpfaccno("N/A");
					}
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setEmployeeName("");
					} else {
						basicBean.setEmployeeName("N/A");
					}
				}

				if (rs.getString("DESIGNATION") != null) {
					basicBean.setDesignation(rs.getString("DESIGNATION"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setDesignation("");
					} else {
						basicBean.setDesignation("N/A");
					}
				}

				if (rs.getString("DATEOFBIRTH") != null) {
					basicBean.setDateOfBirth(CommonUtil.getDatetoString(rs
							.getDate("DATEOFBIRTH"), "dd-MMM-yyyy"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("dateofbirth"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setDateOfBirth("");
					} else {
						basicBean.setDateOfBirth("N/A");
					}
				}

				if (rs.getString("DATEOFJOINING") != null) {
					basicBean.setDateOfJoining(CommonUtil.getDatetoString(rs
							.getDate("DATEOFJOINING"), "dd-MMM-yyyy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setDateOfJoining("");
					} else {
						basicBean.setDateOfJoining("N/A");
					}
				}

				if (rs.getString("FHNAME") != null) {
					basicBean.setFhName(rs.getString("FHNAME"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setFhName("");
					} else {
						basicBean.setFhName("N/A");
					}
				}
				 
				//Getting Station,Region stored in employee_advance_noteparam from 08-May-2012
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
				if (rs.getString("TRANSDT") != null) {
					transDt = commonUtil.converDBToAppFormat(rs
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
				
				if(rs.getString("POSTINGFLAG")!=null)
				{
					
					if(rs.getString("POSTINGFLAG").equals("Y"))
					{
						if (rs.getString("POSTINGSTATION") != null) {
							if(rs.getString("POSTINGSTATION").toUpperCase().equals("CAP IAD")){
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer("Chennai Project"));
							}else{
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("POSTINGSTATION")));
							}
							
							
							// basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
						} else {
							basicBean.setAirportcd("---");
						}
						
						if (rs.getString("POSTINGREGION") != null) {
							basicBean.setRegion(rs.getString("POSTINGREGION"));
						} else {
							basicBean.setRegion("---");
						}
						
					}else{
						if (station != null) {
							if(station.toUpperCase().equals("CAP IAD")){
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer("Chennai Project"));
							}else{
								basicBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(station));
							}
							
							
							// basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
						} else {
							basicBean.setAirportcd("---");
						}
						
						if (region != null) {
							basicBean.setRegion(region);
						} else {
							basicBean.setRegion("---");
						}
					}
					
				}
				  // Commenetd on 04-Nov-2011 for dsplaying posting  station in notesheet report
				 /*if (rs.getString("AIRPORTCODE") != null) {
					if (rs.getString("AIRPORTCODE").toUpperCase().equals(
							"CAP IAD")) {
						basicBean
								.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer("Chennai Project"));
					} else {
						basicBean.setAirportcd(commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("AIRPORTCODE")));
					}

					// basicBean.setAirportcd(rs.getString("AIRPORTCODE"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setAirportcd("");
					} else {
						basicBean.setAirportcd("N/A");
					}
				}

				if (rs.getString("REGION") != null) {
					basicBean.setRegion(rs.getString("REGION"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setRegion("");
					} else {
						basicBean.setRegion("N/A");
						;
					}

				}*/

				if (rs.getString("PERMANENTADDRESS") != null) {
					basicBean.setPermenentaddress(rs
							.getString("PERMANENTADDRESS"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setPermenentaddress("");
					} else {
						basicBean.setPermenentaddress("N/A");
					}
				}

				if (rs.getString("TEMPORATYADDRESS") != null) {
					basicBean.setPresentaddress(rs
							.getString("TEMPORATYADDRESS"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setPresentaddress("");
					} else {
						basicBean.setPresentaddress("N/A");
					}
				}

				if (rs.getString("EMAILID") != null) {
					basicBean.setMailID(rs.getString("EMAILID"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setMailID("");
					} else {
						basicBean.setMailID("N/A");
					}
				}

				if (rs.getString("PHONENUMBER") != null) {
					basicBean.setPhoneno(rs.getString("PHONENUMBER"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setPhoneno("");
					} else {
						basicBean.setPhoneno("N/A");
					}
				}

				if (rs.getString("VERIFIEDBY") != null) {
					basicBean.setVerifiedby(rs.getString("VERIFIEDBY"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setVerifiedby("");
					} else {
						basicBean.setVerifiedby("N/A");
					}
				}

				if (rs.getString("REASONFORRESIGNATION") != null) {
					basicBean.setResignationreason(rs
							.getString("REASONFORRESIGNATION"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setResignationreason("");
					} else {
						basicBean.setResignationreason("N/A");
					}
				}

				if (rs.getString("TRANSDT") != null) {
					basicBean.setAdvtransdt(CommonUtil.getDatetoString(rs
							.getDate("TRANSDT"), "dd-MMM-yyyy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setAdvtransdt("");
					} else {
						basicBean.setAdvtransdt("N/A");
					}
				}

				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setPaymentinfo("");
					} else {
						basicBean.setPaymentinfo("N/A");
					}
				}

				if (rs.getString("NSSANCTIONNO") != null) {
					basicBean.setNssanctionno(rs.getString("NSSANCTIONNO"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setNssanctionno("");
					} else {
						basicBean.setNssanctionno("N/A");
					}
				}

				if (rs.getString("SEPERATIONRESAON") != null) {
					basicBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setSeperationreason("");
					} else {
						basicBean.setSeperationreason("N/A");
					}
				}

				if (rs.getString("SEPERATIONDT") != null) {
					basicBean.setSeperationdate(CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MMM-yyyy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setSeperationdate("");
					} else {
						basicBean.setSeperationdate("N/A");
					}
				}

				if (rs.getString("LOD") != null) {
					lod = rs.getString("LOD");

					if (frmName.equals("FSFormI")) {
						basicBean.setLodInfo(rs.getString("LOD"));
					} else {
						basicBean.setLodInfo(this.loadListOfDocument(lod));
					}

				} else {
					basicBean.setLodInfo("---");
				}

				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					basicBean.setEmplshare(rs
							.getString("EMPSHARESUBSCRIPITION"));
				} else {
					basicBean.setEmplshare("");
				}

				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					basicBean.setEmplrshare(rs
							.getString("EMPSHARECONTRIBUTION"));
				} else {
					basicBean.setEmplrshare("");
				}

				if (rs.getString("LESSCONTRIBUTION") != null) {
					basicBean.setPensioncontribution(rs
							.getString("LESSCONTRIBUTION"));
				} else {
					basicBean.setPensioncontribution("");
				}
				
				if (rs.getString("ADHOCAMOUNT") != null) {
					basicBean.setAdhocamt(rs
							.getString("ADHOCAMOUNT"));
				} else {
					basicBean.setAdhocamt("");
				}
					 
				if (rs.getString("NETCONTRIBUTION") != null) {
					basicBean.setNetcontribution(rs
							.getString("NETCONTRIBUTION"));

					basicBean.setAmtinwords(commonUtil.ConvertInWords(rs
							.getDouble("NETCONTRIBUTION")));
					
					netContAmt = rs.getString("NETCONTRIBUTION");
				} else {
					basicBean.setNetcontribution("");
					netContAmt = "0";
				}

				if (rs.getString("AMTADMITTEDDT") != null) {
					basicBean.setAmtadmtdate(CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MMM-yyyy"));
				} else {
					basicBean.setAmtadmtdate("");
				}

				if (rs.getString("ARREARTYPE") != null) {
					basicBean.setArreartype(rs.getString("ARREARTYPE"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setArreartype("");
					} else {
						basicBean.setArreartype("N/A");
					}
				}

				if (rs.getString("ARREARDATE") != null) {
					basicBean.setArreardate(CommonUtil.getDatetoString(rs
							.getDate("ARREARDATE"), "dd-MMM-yyyy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setArreardate("");
					} else {
						basicBean.setArreardate("N/A");
					}
				}

				if (rs.getString("FROMFINYEAR") != null) {
					basicBean.setFromfinyear(CommonUtil.getDatetoString(rs
							.getDate("FROMFINYEAR"), "yyyy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setFromfinyear("");
					} else {
						basicBean.setFromfinyear("N/A");
					}
				}

				if (rs.getString("TOFINYEAR") != null) {
					basicBean.setTofinyear(CommonUtil.getDatetoString(rs
							.getDate("TOFINYEAR"), "yy"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setTofinyear("");
					} else {
						basicBean.setTofinyear("N/A");
					}
				}

				if (rs.getString("FROMREVISEDINTERESTRATE") != null) {
					basicBean.setInterestratefrom(rs
							.getString("FROMREVISEDINTERESTRATE"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setInterestratefrom("");
					} else {
						basicBean.setInterestratefrom("N/A");
					}
				}

				if (rs.getString("TOREVISEDINTERESTRATE") != null) {
					basicBean.setInterestrateto(rs
							.getString("TOREVISEDINTERESTRATE"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setInterestrateto("");
					} else {
						basicBean.setInterestrateto("N/A");
					}
				}

				if (rs.getString("TRUST") != null) {
					basicBean.setTrust(rs.getString("TRUST"));
				} else {

					if (frmName.equals("FSFormI")) {
						basicBean.setTrust("");
					} else {
						basicBean.setTrust("N/A");
					}
				}
				if (rs.getString("REMARKS") != null) {
					basicBean.setRemarks(rs.getString("REMARKS"));
				} else {
					basicBean.setRemarks("");
				}

				if (rs.getString("PAYMENTDT") != null) {

					basicBean.setPaymentdate(CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MM-yy"));
				} else {
					basicBean.setPaymentdate("---");
				}
				if (rs.getString("SOFLAG") != null) {
					sanctionOrderFlag =rs.getString("SOFLAG");
				} else {
					sanctionOrderFlag = "Before";
				} 
				if (rs.getString("RATEOFINTEREST") != null) {
					basicBean.setRateOfInterest(rs.getString("RATEOFINTEREST"));
				} else {
					basicBean.setRateOfInterest("0");
				}
				 
				basicBean.setSanctionOrderFlag(sanctionOrderFlag);
				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						dateOfBirth, pensionNo);
				basicBean.setPfid(pfid);

				if (!pensionNo.equals(""))
					basicBean.setPensionNo(pensionNo);

				empName = basicBean.getEmployeeName();
				String nomineeName = this.getNomineeDetails(basicBean, empName,
						con);

				if (nomineeName.length() != 0) {
					// nomineeNm=nomineeName.substring(0,nomineeName.lastIndexOf(","));
					nomineeNm = nomineeName;
				}
				if ((!nomineeNm.equals(""))
						&& (basicBean.getSeperationreason().equals("Death"))) {
					basicBean.setNomineename(nomineeNm);
				} else {
					basicBean.setNomineename("---");
				}
				if (basicBean.getSeperationreason().equals("Death")) {
					multipleNomineeList = this.getMultipleNomineeDets(basicBean, empName,
							netContAmt, con);
				}
				basicBean.setMultipleNomineeList(multipleNomineeList);		
				bankMasterBean = loadEmployeeBankInfo(pensionNo, sanctionNo);
				postingDetList = loadPostingDetails(pensionNo);
				advBasicBean = loadNoteSheetPersonalDetails(pensionNo);
				otherDetBean = loadNoteSheetOtherDetails(pensionNo);

				reportList.add(basicBean);
				reportList.add(bankMasterBean);
				reportList.add(postingDetList);
				reportList.add(advBasicBean);
				reportList.add(otherDetBean);

				// reportList.add(basicBean);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return reportList;
	}
//By Radha On 10-Feb-2012 for updation of designation in inputter level
	public String addFinalSettlementVerificationInfo(
			AdvanceBasicBean advanceBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", insertHBAQuery = "", insertHEQuery = "";
		String wthDrwlTrnsdt = "", marriageDate = "", seperationdt = "";
		log.info("Basic Info" + advanceBean.getPensionNo());
		log.info("====Course Duration in DAO======"
				+ advanceBean.getCurseDuration());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();
			String fmlyDOB = "";

			String updateQry = "update employee_advance_noteparam set VERIFIEDBY='PERSONNEL',LOD='"
					+ advanceBean.getLodInfo()
					+ "' where  PENSIONNO='"
					+ advanceBean.getPensionNo()
					+ "' and NSSANCTIONNO='"
					+ advanceBean.getNssanctionno() + "'";
			log.info("==========update Query===========" + updateQry);
			int updatedRecord = st.executeUpdate(updateQry);

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
					.getLoginUserId(), advanceBean.getLoginUserName(),
					advanceBean.getLoginUnitCode(), advanceBean
							.getLoginRegion(), advanceBean
							.getLoginUserDispName());
			cpfInfo
					.createCPFPFWTrans(
							advanceBean.getPensionNo(),
							advanceBean.getNssanctionno(),
							"FS",
							Constants.APPLICATION_PROCESSING_FINAL_PERSONAL_VERFICATION,advanceBean.getLoginUserDesignation());

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public void addPostingDetails(Connection con, AdvanceBasicBean advanceBean) {

		log.info("addPostingDetails-----------");
		Statement st = null;
		int insertedwthdrwRecords = 0, slno = 1;
		;

		String wthDrwlTrnsdt = "", insertWithdrawalQuery = "";
		String cpfacno = "", fromYear = "", toYear = "", region = "", station = "", postHeld = "", remarks = "";
		String wthDrwlStatus = "Y";
		try {
			st = con.createStatement();

			String estr = advanceBean.getPostingdetails();

			StringTokenizer est = new StringTokenizer(estr, ":");

			int lengt = est.countTokens();
			String estrarr[] = new String[lengt];

			for (int e = 0; est.hasMoreTokens(); e++) {

				estrarr[e] = est.nextToken();
				String expsplit = estrarr[e];

				String[] strArr = expsplit.split("#");
				for (int ii = 0; ii < strArr.length; ii++) {
					cpfacno = strArr[0];
					fromYear = strArr[1];
					toYear = strArr[2];
					region = strArr[3];
					station = strArr[4];
					postHeld = strArr[5];
					remarks = strArr[6];
				}

				if (!fromYear.trim().equals("")) {
					fromYear = commonUtil.converDBToAppFormat(fromYear,
							"dd/MM/yyyy", "dd-MMM-yyyy");
				}

				if (!toYear.trim().equals("")) {
					toYear = commonUtil.converDBToAppFormat(toYear,
							"dd/MM/yyyy", "dd-MMM-yyyy");
				}

				insertWithdrawalQuery = "INSERT INTO employee_place_posting(PENSIONNO,CPFACCNO,AIRPORTCODE,REGION,FROMYEAR,TODATE,POSTHELD,REMARKS,SLNO) VALUES('"
						+ advanceBean.getPensionNo()
						+ "','"
						+ cpfacno
						+ "','"
						+ station
						+ "','"
						+ region
						+ "','"
						+ fromYear
						+ "','"
						+ toYear
						+ "','"
						+ postHeld
						+ "','"
						+ remarks
						+ "',"
						+ slno + ")";

				log.info("------insertWithdrawalQuery------"
						+ insertWithdrawalQuery);
				insertedwthdrwRecords = st.executeUpdate(insertWithdrawalQuery);
				slno++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList loadPostingDetails(String pensionno) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = null;
		String sqlQuery = "SELECT * FROM employee_place_posting WHERE PENSIONNO="
				+ pensionno;
		log.info("CPFPTWAdvanceDAO::loadPostingDetails()==" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				basicReportBean = new AdvanceBasicReportBean();

				if (rs.getString("CPFACCNO") != null) {
					basicReportBean.setCpfaccno(rs.getString("CPFACCNO"));
				}
				if (rs.getString("AIRPORTCODE") != null) {
					basicReportBean.setAirportcd(rs.getString("AIRPORTCODE"));
				}
				if (rs.getString("REGION") != null) {
					basicReportBean.setRegion(rs.getString("REGION"));
				}
				if (rs.getString("FROMYEAR") != null) {
					basicReportBean.setFromYear(CommonUtil.getDatetoString(rs
							.getDate("FROMYEAR"), "dd-MMM-yyyy"));
				}
				if (rs.getString("TODATE") != null) {
					basicReportBean.setToYear(CommonUtil.getDatetoString(rs
							.getDate("TODATE"), "dd-MMM-yyyy"));

				}
				if (rs.getString("POSTHELD") != null) {
					basicReportBean.setPostheld(rs.getString("POSTHELD"));
				}
				if (rs.getString("REMARKS") != null) {
					basicReportBean.setRemarks(rs.getString("REMARKS"));
				}

				reportList.add(basicReportBean);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return reportList;
	}

	// New Method----------

	public String detailsOfPosting(String pensionNo) {

		Connection con = null;
		Statement st = null;
		Statement insertSt = null;

		ArrayList advanceList = new ArrayList();
		ArrayList wthdrwList = new ArrayList();
		int insertedRecords = 0, updatedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", advanceTrnsStatus = "N", message = "", advanceType = "", insertHBAQuery = "", insertHEQuery = "";
		String purposeType = "", wthDrwlTrnsdt = "", marriageDate = "", wthdrwStr = "", str = "";

		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			wthdrwStr = this.getPostingDetails(pensionNo, con);

			log.info("-------wthdrwStr DAO--------" + wthdrwStr);

			if (!wthdrwStr.equals("")) {
				str = wthdrwStr.substring(0, wthdrwStr.length() - 1);

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return str;
	}

	// New Method----------

	public String getPostingDetails(String pensionNo, Connection con) {
		int totalRecords = 0;

		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean withDrawalBean = null;
		String wthdrwStr = "";

		StringBuffer sb = sb = new StringBuffer();

		String sqlQuery = "SELECT SLNO,CPFACCNO,AIRPORTCODE,REGION,FROMYEAR,TODATE,POSTHELD,REMARKS FROM employee_place_posting WHERE PENSIONNO="
				+ pensionNo;
		log.info("CPFPTWAdvanceDAO::getPostingDetails()" + sqlQuery);
		try {

			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				withDrawalBean = new AdvanceBasicReportBean();

				if (rs.getString("SLNO") != null) {
					sb.append(rs.getString("SLNO"));
				}

				if (rs.getString("CPFACCNO") != null) {
					sb.append(",");
					sb.append(rs.getString("CPFACCNO"));
				}

				/*
				 * if (rs.getString("WTHDRWLPURPOSE") != null) { sb.append(",");
				 * sb.append(commonUtil.capitalizeFirstLettersTokenizer(rs
				 * .getString("WTHDRWLPURPOSE"))); } else { sb.append(",");
				 * sb.append("-"); }
				 */

				if (rs.getString("FROMYEAR") != null) {
					sb.append(",");
					sb.append(CommonUtil.getDatetoString(
							rs.getDate("FROMYEAR"), "dd-MMM-yyyy"));
				}
				if (rs.getString("TODATE") != null) {
					sb.append(",");
					sb.append(CommonUtil.getDatetoString(rs.getDate("TODATE"),
							"dd-MMM-yyyy"));
				}

				if (rs.getString("REGION") != null) {
					sb.append(",");
					sb.append(rs.getString("REGION"));
				}
				if (rs.getString("AIRPORTCODE") != null) {
					sb.append(",");
					sb.append(rs.getString("AIRPORTCODE"));
				}
				if (rs.getString("POSTHELD") != null) {
					sb.append(",");
					sb.append(rs.getString("POSTHELD"));
				}
				if (rs.getString("REMARKS") != null) {
					sb.append(",");
					sb.append(rs.getString("REMARKS"));
				}
				sb.append(":");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			// commonDB.closeConnection(null, st, rs);
		}

		log.info("============= sb.toString() ===========" + sb.toString());
		return sb.toString();
	}

	public int deleteFinalSettlement(String pensionNo, String sanctionNo) {

		Connection con = null;
		Statement st = null;
		int n = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String delQuery = "update employee_advance_noteparam set deleteflag='Y' where PENSIONNO='"
					+ pensionNo + "' and  NSSANCTIONNO='" + sanctionNo + "'";

			log.info("CPFPTWAdvanceDAO::deleteFinalSettlement()==" + delQuery);
			n = st.executeUpdate(delQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;

	}

	public AdvanceBasicReportBean loadNoteSheetPersonalDetails(String pensionno) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		;
		String sqlQuery = "SELECT * FROM epis_noteparam_personal_info WHERE PENSIONNO="
				+ pensionno;
		log.info("CPFPTWAdvanceDAO::loadNoteSheetPersonalDetails()==" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("SANCTIONNO") != null) {
					basicReportBean.setNssanctionno(rs.getString("SANCTIONNO"));
				} else {
					basicReportBean.setNssanctionno("");
				}

				if (rs.getString("SEPERATIONREASON") != null) {
					basicReportBean.setSeperationreason(rs
							.getString("SEPERATIONREASON"));
				} else {
					basicReportBean.setSeperationreason("");
				}

				if (rs.getString("EMPLOYEEAGE") != null) {
					basicReportBean.setEmpage(rs.getString("EMPLOYEEAGE"));
				} else {
					basicReportBean.setEmpage("");
				}

				if (rs.getString("MARITALSTATUS") != null) {
					basicReportBean.setMaritalstatus(rs
							.getString("MARITALSTATUS"));
				} else {
					basicReportBean.setMaritalstatus("");
				}

				if (rs.getString("RELATION") != null) {
					basicReportBean.setEmprelation(rs.getString("RELATION"));
				} else {
					basicReportBean.setEmprelation("");
				}

				if (rs.getString("ADDRESS") != null) {
					basicReportBean.setEmpaddress(rs.getString("ADDRESS"));
				} else {
					basicReportBean.setEmpaddress("");
				}

				if (rs.getString("QUARTERALLOTED") != null) {
					basicReportBean.setQuarterallotment(rs
							.getString("QUARTERALLOTED"));
				} else {
					basicReportBean.setQuarterallotment("");
				}

				if (rs.getString("QUARTERNO") != null) {
					basicReportBean.setQuarterno(rs.getString("QUARTERNO"));
				} else {
					basicReportBean.setQuarterno("");
				}

				if (rs.getString("COLONYNAME") != null) {
					basicReportBean.setColonyname(rs.getString("COLONYNAME"));
				} else {
					basicReportBean.setColonyname("");
				}

				if (rs.getString("STATION") != null) {
					basicReportBean.setEmpstation(rs.getString("STATION"));
				} else {
					basicReportBean.setEmpstation("");
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return basicReportBean;
	}
//	 On 25-Apr-2012 By Radha For Preparing Mail
  	public int updateNoteSheetSanctionDate(AdvanceSearchBean advanceBean) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0, length = 0;
		double emolument = 0.0;
		String updateQuery = "", sanctionorderno = "";
		String purposeType = "",reportPath="";
		String[] nssanctionoStrip;
		log.info("updateNoteSheetSanctionDate::Basic Info"
				+ advanceBean.getPensionNo());

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			log.info("---ns sanction no---" + advanceBean.getNssanctionno());
			nssanctionoStrip = advanceBean.getNssanctionno().split(",");
			length = nssanctionoStrip.length;
			log.info("--no s--length-----" + length);
			for (int i = 0; i < length; i++) {
				sanctionorderno = this.getSanctionOrderSequence(con);
				String updateQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set NSSANCTIONEDDT='"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "' , SONO='"
						+ sanctionorderno
						+ "' where  NSSANCTIONNO = " + nssanctionoStrip[i];
				log.info("CPFPTWAdvanceDAO::updateNoteSheetSanctionDate()==" + updateQry);
				updatedRecords = st.executeUpdate(updateQry);
				/*log.info("ArrearType==" + advanceBean.getArreartype()+"Reason =="+advanceBean.getSeperationreason());
				if(advanceBean.getArreartype().equals("NON-ARREAR")){					 
						reportPath = this.generateJasperReportForSO(con,nssanctionoStrip[i],advanceBean);
						MailUtil.prePareMail(reportPath);
					 
				}*/
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	public int updatePFWSanctionDate(AdvanceSearchBean advanceBean) {
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0, length = 0,updatedSignRecords=0;
		double emolument = 0.0;
		String updateQuery = "";
		String purposeType = "", pfwsanctionorderno = "";
		String[] advTransIdStrip;
		log.info("updatePFWSanctionDate::Basic Info"
				+ advanceBean.getPensionNo());

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			log.info("---ns sanction no---" + advanceBean.getAdvanceTransID());
			advTransIdStrip = advanceBean.getAdvanceTransID().split(",");
			length = advTransIdStrip.length;
			log.info("--no s--length-----" + length);
			for (int i = 0; i < length; i++) {
			
				pfwsanctionorderno = this.getPFWSanctionOrderSequence(con);
				String updateQry = "update EMPLOYEE_ADVANCES_FORM set SANCTIONDATE='"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "' ,SONO='"
						+ pfwsanctionorderno
						+ "' where  ADVANCETRANSID =" + advTransIdStrip[i];
				log.info("CPFPTWAdvanceDAO::updatePFWSanctionDate()==" + updateQry);
				updatedRecords = st.executeUpdate(updateQry);
				String updateSignQry="UPDATE EPIS_ADVANCES_TRANSACTIONS SET APPROVEDBY=(select userid from epis_user  where username='"+this.userName+"'),APRVDSIGNNAME=(select displayname from epis_user  where username='"+this.userName+"'),designation=(select designation from epis_user  where username='"+this.userName+"') where cpfpfwtransid='"+advTransIdStrip[i]+"' and transcd = '33'";
				log.info("CPFPTWAdvanceDAO::updatePFWSanctionDate()==" + updateSignQry);
				updatedSignRecords = st.executeUpdate(updateSignQry);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return updatedRecords;
	}

	public CPFPFWTransInfoBean getCPFPFWTransDetails(String transId)
			throws InvalidDataException {
		String interestRate = "";
		String sqlQuery = "", finYear = "";
		CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			sqlQuery = "select SUBSCRIPTIONAMNT,CONTRIBUTIONAMOUNT,CPFACCFUND,APPROVEDAMNT,PENSIONNO,APPROVEDSUBSCRIPTIONAMT,APPROVEDCONTRIBUTIONAMT from employee_advances_form where ADVANCETRANSID='"
					+ transId + "'";

			log.info("CPFPTWAdvanceDAO::getCPFPFWTransDetails()==" + sqlQuery);

			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("SUBSCRIPTIONAMNT") != null) {
					transBean.setSubscriptionAmt(rs
							.getString("SUBSCRIPTIONAMNT"));
				} else {
					transBean.setSubscriptionAmt("0");
				}
				if (rs.getString("CONTRIBUTIONAMOUNT") != null) {
					transBean.setContributionAmt(rs
							.getString("CONTRIBUTIONAMOUNT"));
				} else {
					transBean.setContributionAmt("0");
				}

				if (rs.getString("CPFACCFUND") != null) {
					transBean.setCpfFund(rs.getString("CPFACCFUND"));
				} else {
					transBean.setCpfFund("0");
				}

				if (rs.getString("APPROVEDAMNT") != null) {
					transBean.setApprovedAmt(rs.getString("APPROVEDAMNT"));
				} else {
					transBean.setApprovedAmt("0");
				}

				if (rs.getString("PENSIONNO") != null) {
					transBean.setPensionNo(rs.getString("PENSIONNO"));
				} else {
					transBean.setPensionNo("0");
				}

				if (rs.getString("APPROVEDSUBSCRIPTIONAMT") != null) {
					transBean.setApprovedSubscrAmnt(rs
							.getString("APPROVEDSUBSCRIPTIONAMT"));
				} else {
					transBean.setApprovedSubscrAmnt("0");
				}

				if (rs.getString("APPROVEDCONTRIBUTIONAMT") != null) {
					transBean.setApprovedcontriAmnt(rs
							.getString("APPROVEDCONTRIBUTIONAMT"));
				} else {
					transBean.setApprovedcontriAmnt("0");
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}

		return transBean;
	}

	public AdvanceCPFForm2Bean getAdvanceTransactionDetails(
			AdvanceCPFForm2Bean basicBean, String frmName, String pensionno)
			throws EPISException {
		int totalRecords = 0;
		String approvedAmt = "", transCode = "";
		CPFPFWTransInfo transInfo = new CPFPFWTransInfo();
		CPFPFWTransBean transBean = new CPFPFWTransBean();
		if (frmName.equals("PFWCheckListReport")) {
			transCode = Constants.APPLICATION_PROCESSING_PFW_CHECK_LIST;
		} else if (frmName.equals("PFWForm3Report")) {
			transCode = Constants.APPLICATION_PROCESSING_PFW_PART_III_SRMGR;
		} else if (frmName.equals("PFWForm4VerificationReport")) {
			transCode = Constants.APPLICATION_PROCESSING_PFW_PART_IV_SECRETARY;
		} else if (frmName.equals("Form-4Report")) {
			transCode = Constants.APPLICATION_PROCESSING_PFW_APPROVAL_ED;
		}

		int id = basicBean.getAdvanceTransID().lastIndexOf("/");
		String advId = basicBean.getAdvanceTransID().substring(id + 1);

		try {
			transBean = transInfo.readTransInfo(pensionno, advId, transCode);
			if (!transBean.getTransNo().equals("")) {
				if (!transBean.getTransApprovedAmt().equals("0.00")) {
					basicBean.setSubscriptionAmt(transBean
							.getTransSubscriptionAmt());
					basicBean.setSubscriptionAmtCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(transBean
									.getTransSubscriptionAmt())));
				} else {
					basicBean.setSubscriptionAmt(transBean
							.getTransSubscriptionAmt());
					basicBean.setSubscriptionAmtCurr("0.00");
				}
				if (!transBean.getTransApprovedAmt().equals("0.00")) {
					basicBean.setContributionAmtCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(transBean
									.getTransContributionAmt())));
					basicBean.setContributionAmt(transBean
							.getTransContributionAmt());
				} else {
					basicBean.setContributionAmtCurr("0.00");
					basicBean.setContributionAmt(transBean
							.getTransContributionAmt());
				}

				basicBean.setCPFFund(transBean.getTransCPFFund());

				if (!transBean.getTransApprovedAmt().equals("0.00")) {
					basicBean.setAmntRecommended(transBean
							.getTransApprovedAmt());
					basicBean.setAmntRecommendedCurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(transBean
									.getTransApprovedAmt())));
				} else {
					basicBean.setAmntRecommended("0.00");
					basicBean.setAmntRecommendedCurr("0.00");
				}
				if (!transBean.getApprovedTransSubscriAmt().equals("0.00")) {
					basicBean.setApprovedsubamt(transBean
							.getApprovedTransSubscriAmt());
					basicBean.setApprovedsubamtcurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(transBean
									.getApprovedTransSubscriAmt())));
				} else {
					basicBean.setApprovedsubamt("");
					basicBean.setApprovedcontamtcurr("0");

				}
				if (!transBean.getApprovedTransContrAmt().equals("0.00")) {
					basicBean.setApprovedconamt(transBean
							.getApprovedTransContrAmt());
					basicBean.setApprovedcontamtcurr(commonUtil
							.getDecimalCurrency(Double.parseDouble(transBean
									.getApprovedTransContrAmt())));
				} else {
					basicBean.setApprovedconamt("");
					basicBean.setApprovedcontamtcurr("0");

				}
			}

		} catch (EPISException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
			throw e;
		}

		return basicBean;
	}

	public String updateFinalSettlementInfo(AdvanceBasicBean advanceBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0, insertedRecord = 0, updatedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", insertQry = "", advanceTrnsStatus = "N", message = "", insertHBAQuery = "", insertHEQuery = "";
		String wthDrwlTrnsdt = "", marriageDate = "", seperationdt = "", nsSanctionNo = "", appointmentDt = "";
		log.info("Basic Info" + advanceBean.getPensionNo());
		log.info("====Course Duration in DAO======"
				+ advanceBean.getCurseDuration());
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			String fmlyDOB = "";

			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getAppointmentdate().trim().equals("")) {
				appointmentDt = commonUtil.converDBToAppFormat(advanceBean
						.getAppointmentdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			log.info("Posting Details in DAO---------"
					+ advanceBean.getPostingdetails());

			// If we provide any permission to change the region & station
			// we have to update in EMPLOYEE_ADVANCE_NOTEPARAM table

			String updateFSQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set SEPERATIONDT='"
					+ seperationdt
					+ "', LOD='"
					+ advanceBean.getLodInfo()
					+ "', CPFACNO='"
					+ advanceBean.getCpfaccno()
					+ "', REASONFORRESIGNATION='"
					+ advanceBean.getResignationreason()
					+ "', DESIGNATION='"
					+ advanceBean.getDesignation()
					+ "' where  NSSANCTIONNO='"
					+ advanceBean.getNssanctionno()
					+ "'";

			log.info("updateFSQry----------" + updateFSQry);

			int updatedRecord = st.executeUpdate(updateFSQry);

			int countRec = this.checkNoteSheetOtherDetails(advanceBean);

			if (countRec == 0) {

				String insertOtherDetQry = "INSERT INTO employee_noteparam_other_det(PENSIONNO,ORGANIZATIONNAME,ORGANIZATIONADDRESS,APPOINTMENTDT,POST,WORKINGPLACE) VALUES('"
						+ advanceBean.getPensionNo()
						+ "','"
						+ advanceBean.getOrganizationname()
						+ "','"
						+ advanceBean.getOrganizationaddress()
						+ "','"
						+ appointmentDt
						+ "','"
						+ advanceBean.getPostedas()
						+ "','" + advanceBean.getWorkingplace() + "')";
				log.info("insertQuery for Other Details------"
						+ insertOtherDetQry);

				insertedRecords = st.executeUpdate(insertOtherDetQry);

			} else {
				String updateOtherDetQry = "update employee_noteparam_other_det set ORGANIZATIONNAME='"
						+ advanceBean.getOrganizationname()
						+ "', ORGANIZATIONADDRESS='"
						+ advanceBean.getOrganizationaddress()
						+ "', APPOINTMENTDT='"
						+ appointmentDt
						+ "', POST='"
						+ advanceBean.getPostedas()
						+ "', WORKINGPLACE='"
						+ advanceBean.getWorkingplace()
						+ "' where  PENSIONNO='"
						+ advanceBean.getPensionNo()
						+ "'";

				log.info("updateOtherDetQry----------" + updateOtherDetQry);

				int updatedRec = st.executeUpdate(updateOtherDetQry);
			}
			// For restricting the updation of personal Information Frm  25-Apr-2012
			//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
			/*String updateQry = "update employee_personal_info set DESEGNATION='"
					+ advanceBean.getDesignation()
					+ "', DEPARTMENT='"
					+ advanceBean.getDepartment()
					+ "', CPFACNO='"
					+ advanceBean.getCpfaccno() 
					+ "',AIRPORTCODE='"
					+ advanceBean.getStation()
					+ "',REGION='"
					+ advanceBean.getRegion()
					+ "',PERMANENTADDRESS='"
					+ advanceBean.getPermenentaddress()
					+ "',TEMPORATYADDRESS='"
					+ advanceBean.getPresentaddress()
					+ "',EMAILID='"
					+ advanceBean.getMailID()
					+ "' where  PENSIONNO='" + advanceBean.getPensionNo() + "'";
			log.info("==========update Query===========" + updateQry);
			updatedRecord = st.executeUpdate(updateQry);
*/
			if (advanceBean.getSeperationreason().equals("Death")) {

				String updatePersonalQuery = "update epis_noteparam_personal_info set EMPLOYEEAGE='"
						+ advanceBean.getEmpage()
						+ "',MARITALSTATUS='"
						+ advanceBean.getMaritalstatus()
						+ "',RELATION='"
						+ advanceBean.getEmprelation()
						+ "',ADDRESS='"
						+ advanceBean.getEmpaddress()
						+ "',QUARTERALLOTED='"
						+ advanceBean.getQuarterallotment()
						+ "',QUARTERNO='"
						+ advanceBean.getQuarterno()
						+ "',COLONYNAME='"
						+ advanceBean.getColonyname()
						+ "',STATION='"
						+ advanceBean.getEmpstation()
						+ "' where SANCTIONNO='"
						+ advanceBean.getNssanctionno() + "'";

				log.info("==========updatePersonalQuery ==========="
						+ updatePersonalQuery);
				updatedRecords = st.executeUpdate(updatePersonalQuery);
			}

			this.updatePostingDetails(advanceBean.getPensionNo(), con,
					advanceBean);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public int checkBankDetails(Connection con ,String pensionNo, String transId) {

		log.info("CPFPTWAdvanceDAO::checkBankDetails() entering method ");
		String query = "";
		Statement st = null;		 
		ResultSet rs = null;
		int i = 0;
		query = "select count(*) from EMPLOYEE_BANK_INFO where  CPFPFWTRANSID="
				+ transId + " and PENSIONNO='" + pensionNo + "'";

		log.info("query is " + query);
		try { 
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
			commonDB.closeConnection(rs, st, null);
		}
		log.info("CPFPTWAdvanceDAO::checkBankDetails() leaving method");
		return i;
	}

	public EmpBankMaster loadEmployeeBankInfo(String pensionno, String transId) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmpBankMaster bankMaster = new EmpBankMaster();
		log.info("---Pensionno---" + pensionno + "--transid----" + transId);
		String sqlQuery = "SELECT * FROM EMPLOYEE_BANK_INFO WHERE PENSIONNO="
				+ pensionno + " and CPFPFWTRANSID=" + transId;
		log.info("CPFPTWAdvanceDAO::loadEmployeeBankInfo()" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getString("NAME") != null) {
					bankMaster.setBankempname(rs.getString("NAME"));
				}
				if (rs.getString("ADDRESS") != null) {
					bankMaster.setBankempaddrs(rs.getString("ADDRESS"));
				}
				if (rs.getString("BANKNAME") != null) {
					bankMaster.setBankname(rs.getString("BANKNAME"));
				}
				if (rs.getString("BRANCHADDRESS") != null) {
					bankMaster.setBranchaddress(rs.getString("BRANCHADDRESS"));
				}
				if (rs.getString("SAVINGBNKACCNO") != null) {
					bankMaster.setBanksavingaccno(rs
							.getString("SAVINGBNKACCNO"));
				}
				if (rs.getString("NEFTRTGSCODE") != null) {
					bankMaster.setBankemprtgsneftcode(rs
							.getString("NEFTRTGSCODE"));
				}
				if (rs.getString("MICRONO") != null) {
					bankMaster.setBankempmicrono(rs.getString("MICRONO"));
				}
				if (rs.getString("MAILID") != null) {
					bankMaster.setEmpmailid(rs.getString("MAILID"));
				}
				if (rs.getString("PAYMENTMODE") != null) {
					bankMaster.setBankpaymentmode(rs.getString("PAYMENTMODE"));
				}

				if (rs.getString("CITY") != null) {
					bankMaster.setCity(rs.getString("CITY"));
				}

				if (rs.getString("PARTYNAME") != null) {
					bankMaster.setPartyName(rs.getString("PARTYNAME"));
				} else {
					bankMaster.setPartyName("---");
				}
				if (rs.getString("PARTYADDRESS") != null) {
					bankMaster.setPartyAddress(rs.getString("PARTYADDRESS"));
				} else {
					bankMaster.setPartyAddress("---");
				}

				bankMaster.setChkBankInfo("Y");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return bankMaster;
	}
	public ArrayList loadEmployeeBankInfoList(String pensionno, String transId) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		EmpBankMaster bankMaster = null;
		ArrayList al=new ArrayList();
		log.info("---Pensionno---" + pensionno + "--transid----" + transId);
		String sqlQuery = "SELECT * FROM EMPLOYEE_BANK_INFO WHERE PENSIONNO="
				+ pensionno + " and CPFPFWTRANSID=" + transId;
		log.info("CPFPTWAdvanceDAO::loadEmployeeBankInfo()" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				bankMaster = new EmpBankMaster();
				if (rs.getString("NAME") != null) {
					bankMaster.setBankempname(rs.getString("NAME"));
				}
				if (rs.getString("ADDRESS") != null) {
					bankMaster.setBankempaddrs(rs.getString("ADDRESS"));
				}
				if (rs.getString("BANKNAME") != null) {
					bankMaster.setBankname(rs.getString("BANKNAME"));
				}
				if (rs.getString("BRANCHADDRESS") != null) {
					bankMaster.setBranchaddress(rs.getString("BRANCHADDRESS"));
				}
				if (rs.getString("SAVINGBNKACCNO") != null) {
					bankMaster.setBanksavingaccno(rs
							.getString("SAVINGBNKACCNO"));
				}
				if (rs.getString("NEFTRTGSCODE") != null) {
					bankMaster.setBankemprtgsneftcode(rs
							.getString("NEFTRTGSCODE"));
				}
				if (rs.getString("MICRONO") != null) {
					bankMaster.setBankempmicrono(rs.getString("MICRONO"));
				}
				if (rs.getString("MAILID") != null) {
					bankMaster.setEmpmailid(rs.getString("MAILID"));
				}
				if (rs.getString("PAYMENTMODE") != null) {
					bankMaster.setBankpaymentmode(rs.getString("PAYMENTMODE"));
				}

				if (rs.getString("CITY") != null) {
					bankMaster.setCity(rs.getString("CITY"));
				}

				if (rs.getString("PARTYNAME") != null) {
					bankMaster.setPartyName(rs.getString("PARTYNAME"));
				} else {
					bankMaster.setPartyName("---");
				}
				if (rs.getString("PARTYADDRESS") != null) {
					bankMaster.setPartyAddress(rs.getString("PARTYADDRESS"));
				} else {
					bankMaster.setPartyAddress("---");
				}

				bankMaster.setChkBankInfo("Y");
				al.add(bankMaster);
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return al;
	}

	public int getNomineeCount(AdvanceBasicReportBean basicBean) {

		log.info("CPFPTWAdvanceDAO::getNomineeCount() entering method ");
		String query = "";
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		int i = 0;
		query = "select count(*) from employee_nominee_dtls where pensionno='"
				+ basicBean.getPensionNo() + "'";

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
		log.info("CPFPTWAdvanceDAO::getNomineeCount() leaving method");
		return i;
	}

	public void updatePostingDetails(String pensionNo, Connection con,
			AdvanceBasicBean advanceBean) {

		Statement st = null;
		ResultSet rs = null;
		int insertedwthdrwRecords = 0;
		int updatedRecords = 0;
		String wthDrwlTrnsdt = "", wthDrwlStatus = "Y", insertWithdrawalQuery = "", updateWthDrwQuery = "";
		String wthdrwPurpose = "", wthdrwAmount = "", wthdrwDate = "", wthdrwid = "", wthdrwstatus = "", deleteId = "";
		String cpfAccNo = "", fromDate = "", toDate = "", region = "", station = "", postHeld = "", remarks = "", slno = "", postingStatis = "";
		try {
			st = con.createStatement();
			String estr = advanceBean.getPostingdetails();

			log.info("estr in DAO------" + estr);

			log.info("====estr===" + estr.lastIndexOf(":"));

			if (estr.lastIndexOf(":") != -1) {
				StringTokenizer est = new StringTokenizer(estr, ":");

				int lengt = est.countTokens();
				String estrarr[] = new String[lengt];

				for (int e = 0; est.hasMoreTokens(); e++) {

					estrarr[e] = est.nextToken();
					String expsplit = estrarr[e];

					String[] strArr = expsplit.split("#");
					for (int ii = 0; ii < strArr.length; ii++) {
						cpfAccNo = strArr[0];
						fromDate = strArr[1];
						toDate = strArr[2];
						region = strArr[3];
						station = strArr[4];
						postHeld = strArr[5];
						remarks = strArr[6];
						slno = strArr[7];
						postingStatis = strArr[8];

						// wthdrwid=strArr[3];
						// wthdrwstatus=strArr[4];
						if (ii == 9)
							deleteId = strArr[9];
					}

					/*
					 * if (!wthdrwDate.trim().equals("")) { wthDrwlTrnsdt =
					 * commonUtil.converDBToAppFormat(wthdrwDate, "dd/MM/yyyy",
					 * "dd-MMM-yyyy"); } if(wthdrwPurpose.equals("-")){
					 * wthdrwPurpose=""; }
					 */

					if (!fromDate.trim().equals("")) {
						fromDate = commonUtil.converDBToAppFormat(fromDate,
								"dd/MM/yyyy", "dd-MMM-yyyy");
					}

					if (!toDate.trim().equals("")) {
						toDate = commonUtil.converDBToAppFormat(toDate,
								"dd/MM/yyyy", "dd-MMM-yyyy");
					}

					if (postingStatis.equals("N")) {
						insertWithdrawalQuery = "INSERT INTO employee_place_posting(PENSIONNO,SLNO,CPFACCNO,AIRPORTCODE,REGION,FROMYEAR,TODATE,POSTHELD,REMARKS) VALUES("
								+ Long.parseLong(pensionNo)
								+ ","
								+ slno
								+ ",'"
								+ cpfAccNo
								+ "','"
								+ station
								+ "','"
								+ region
								+ "','"
								+ fromDate
								+ "','"
								+ toDate
								+ "','"
								+ postHeld + "','" + remarks + "')";

						log.info("------insertWithdrawalQuery------"
								+ insertWithdrawalQuery);
						insertedwthdrwRecords = st
								.executeUpdate(insertWithdrawalQuery);
					} else if (postingStatis.equals("M")) {
						// updateWthDrwQuery="update employee_place_posting";

						updateWthDrwQuery = "UPDATE employee_place_posting SET CPFACCNO='"
								+ cpfAccNo
								+ "',AIRPORTCODE='"
								+ station
								+ "',REGION='"
								+ region
								+ "',FROMYEAR='"
								+ fromDate
								+ "',TODATE='"
								+ toDate
								+ "',POSTHELD='"
								+ postHeld
								+ "',REMARKS='"
								+ remarks
								+ "' WHERE PENSIONNO='"
								+ advanceBean.getPensionNo()
								+ "' and SLNO='"
								+ slno + "'";
						log.info("------updateWthDrwQuery------"
								+ updateWthDrwQuery);
						updatedRecords = st.executeUpdate(updateWthDrwQuery);

					}
				}
			}

			if ((!deleteId.equals("")) || (estr.lastIndexOf(":") == -1)) {

				String delQuery = "delete from employee_place_posting where PENSIONNO='"
						+ advanceBean.getPensionNo() + "'";

				if (!deleteId.equals("")) {
					delQuery += " and  SLNO in(" + deleteId + ")";
				}
				log.info("======delQuery======" + delQuery);
				updatedRecords = st.executeUpdate(delQuery);
				deleteId = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public AdvanceBasicReportBean loadNoteSheetOtherDetails(String pensionno) {
		int totalRecords = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		;
		String sqlQuery = "SELECT * FROM employee_noteparam_other_det WHERE PENSIONNO="
				+ pensionno;
		log.info("CPFPTWAdvanceDAO::loadNoteSheetOtherDetails()==" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getString("ORGANIZATIONNAME") != null) {
					basicReportBean.setOrganizationname(rs
							.getString("ORGANIZATIONNAME"));
				} else {
					basicReportBean.setOrganizationname("N/A");
				}

				if (rs.getString("ORGANIZATIONADDRESS") != null) {
					basicReportBean.setOrganizationaddress(rs
							.getString("ORGANIZATIONADDRESS"));
				} else {
					basicReportBean.setOrganizationaddress("N/A");
				}

				if (rs.getString("APPOINTMENTDT") != null) {
					basicReportBean.setAppointmentdate(CommonUtil
							.getDatetoString(rs.getDate("APPOINTMENTDT"),
									"dd-MMM-yyyy"));
				} else {
					basicReportBean.setAppointmentdate("N/A");
				}

				if (rs.getString("POST") != null) {
					basicReportBean.setPostedas(rs.getString("POST"));
				} else {
					basicReportBean.setPostedas("N/A");
				}

				if (rs.getString("WORKINGPLACE") != null) {
					basicReportBean.setWorkingplace(rs
							.getString("WORKINGPLACE"));
				} else {
					basicReportBean.setWorkingplace("N/A");
				}

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return basicReportBean;
	}

	public int checkNoteSheetOtherDetails(AdvanceBasicBean advanceBean) {

		log.info("CPFPTWAdvanceDAO::checkNoteSheetOtherDetails() entering method ");
		String query = "";
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		int i = 0;
		query = "select count(*) from employee_noteparam_other_det where  PENSIONNO='"
				+ advanceBean.getPensionNo() + "'";

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
		log.info("CPFPTWAdvanceDAO::checkNoteSheetOtherDetails() leaving method");
		return i;
	}
	//	BY Radha On 07-May-2012 for saving Designation of the Employee
	// By Radha on 12-Sep-2011 for saving rateofintrest value
	public String addFinalSettlementArrearInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int insertedRecords = 0, insertedRecord = 0, updatedRecord = 0, updatedRecords = 0;
		String advanceTransID = "", insertFmlyQuery = "", insertQuery = "", insertQry = "", advanceTrnsStatus = "N", message = "", insertHBAQuery = "", insertHEQuery = "";
		String wthDrwlTrnsdt = "", marriageDate = "", seperationdt = "", nsSanctionNo = "", placeOfPostingValue = "", appointmentDt = "", arreardt = "",rateofinterest="";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			nsSanctionNo = this.getNoteSheetSequence(con);

			String fmlyDOB = "";

			log.info("======Posting Details====="
					+ advanceBean.getPostingdetails());

			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getAppointmentdate().trim().equals("")) {
				appointmentDt = commonUtil.converDBToAppFormat(advanceBean
						.getAppointmentdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getArreardate().trim().equals("")) {
				arreardt = commonUtil.converDBToAppFormat(advanceBean
						.getArreardate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getPostingdetails().equals("")) {
				placeOfPostingValue = "Y";
			} else {
				placeOfPostingValue = "N";
			}
			
			rateofinterest = Constants.RATEOFINTEREST;
			insertQuery = "INSERT INTO EMPLOYEE_ADVANCE_NOTEPARAM(NSSANCTIONNO,PENSIONNO,SEPERATIONDT,SEPERATIONRESAON,ARREARTYPE,TRANSDT,PAYMENTINFO,ARREARDATE,FROMFINYEAR,TOFINYEAR,FROMREVISEDINTERESTRATE,TOREVISEDINTERESTRATE,NSTYPE,REGION,AIRPORTCODE,RATEOFINTEREST,DESIGNATION) VALUES("
					+ Long.parseLong(nsSanctionNo)
					+ ",'"
					+ advanceBean.getPensionNo()
					+ "','"
					+ seperationdt
					+ "','"
					+ advanceBean.getSeperationreason()
					+ "','"
					+ advanceBean.getArreartype()
					+ "','"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "','"
					+ advanceBean.getPaymentinfo()
					+ "','"
					+ arreardt
					+ "','"
					+ advanceBean.getFromfinyear()
					+ "','"
					+ advanceBean.getTofinyear()
					+ "','"
					+ advanceBean.getInterestratefrom()
					+ "','"
					+ advanceBean.getInterestrateto()
					+ "','ARREAR'"
					+ ",'"
					+ advanceBean.getRegion()
					+ "','"
					+ advanceBean.getStation() 
					+ "','"
					+ rateofinterest
					+ "','"
					+ advanceBean.getDesignation()+"')";
			log.info("insertQuery " + insertQuery);

			log.info("CPFPTWAdvanceDAO::addFinalSettlementInfo" + insertQuery);

			insertedRecords = st.executeUpdate(insertQuery);
			if (insertedRecords != 0) {

				insertQry = "INSERT INTO epis_noteparam_personal_info(PENSIONNO,SANCTIONNO,SEPERATIONREASON,EMPLOYEENAME,FHNAME,EMPLOYEEAGE,MARITALSTATUS,RELATION,ADDRESS,QUARTERALLOTED,QUARTERNO,COLONYNAME,STATION,PLACEOFDEATH) VALUES('"
						+ advanceBean.getPensionNo()
						+ "',"
						+ Long.parseLong(nsSanctionNo)
						+ ",'"
						+ advanceBean.getSeperationreason()
						+ "','"
						+ advanceBean.getEmployeeName()
						+ "','"
						+ advanceBean.getFhName()
						+ "','"
						+ advanceBean.getEmpage()
						+ "','"
						+ advanceBean.getMaritalstatus()
						+ "','"
						+ advanceBean.getEmprelation()
						+ "','"
						+ advanceBean.getEmpaddress()
						+ "','"
						+ advanceBean.getQuarterallotment()
						+ "','"
						+ advanceBean.getQuarterno()
						+ "','"
						+ advanceBean.getColonyname()
						+ "','"
						+ advanceBean.getEmpstation()
						+ "','"
						+ advanceBean.getDeathplace() + "')";

				log
						.info("CPFPTWAdvanceDAO::addFinalSettlementInfo----insertQry"
								+ insertQry);

				insertedRecord = st.executeUpdate(insertQry);
				//  For restricting the updation of personal Information Frm  25-Apr-2012
				//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
				/*if (!advanceBean.getDesignation().equals("")) {
					String updateQry = "update employee_personal_info set DESEGNATION='"
							+ advanceBean.getDesignation()
							+ "', DEPARTMENT='"
							+ advanceBean.getDepartment()
							+ "', CPFACNO='"
							+ advanceBean.getCpfaccno() 
							+ "',AIRPORTCODE='"
							+ advanceBean.getStation()
							+ "',REGION='"
							+ advanceBean.getRegion()
							+ "',PERMANENTADDRESS='"
							+ advanceBean.getPermenentaddress()
							+ "',TEMPORATYADDRESS='"
							+ advanceBean.getPresentaddress()
							+ "',EMAILID='"
							+ advanceBean.getMailID()
							+ "',PHONENUMBER='"
							+ advanceBean.getPhoneno()
							+ "' where  PENSIONNO='"
							+ advanceBean.getPensionNo() + "'";
					log.info("==========update Query===========" + updateQry);
					updatedRecord = st.executeUpdate(updateQry);
				}*/

				if (!nsSanctionNo.equals(""))
					bankBean.setTransId(nsSanctionNo);

				if (advanceBean.getPaymentinfo().equals("Y")) {
					this.addEmployeeBankInfo(con, bankBean, advanceBean
							.getPensionNo());

					String updatePaymentQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set PAYMENTINFO='Y' where  NSSANCTIONNO='"
							+ nsSanctionNo + "'";
					updatedRecords = st.executeUpdate(updatePaymentQry);

					log
							.info("CPFPTWAdvanceDAO::addFinalSettlementInfo-------updateQuery"
									+ updatePaymentQry);
				}
				message = nsSanctionNo;

			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public String buildSearchQueryForNoteSheetArrear(
			AdvanceSearchBean searchBean, String unitName) {

		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForNoteSheetArrear-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "", sanctiondt = "", paymentdt = "";
		sqlQuery = "SELECT EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.FINALSETTLMENTDT AS FINALSETTLMENTDT,EMPFID.RESETTLEMENTDATE AS RESETTLEMENTDATE, NVL(EN.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,EMPFID.PENSIONNO,EMPFID.AIRPORTCODE AS AIRPORTCODE_PERSNL,EMPFID.REGION AS REGION_PERSNL,"
				+ "EN.NSSANCTIONNO AS NSSANCTIONNO,EN.EMPSHARESUBSCRIPITION  AS EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION AS EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION AS LESSCONTRIBUTION,EN.VERIFIEDBY AS VERIFIEDBY,"
				+ "EN.ARREARTYPE,EN.ARREARDATE,EN.FROMFINYEAR,EN.TOFINYEAR,EN.FROMREVISEDINTERESTRATE,EN.TOREVISEDINTERESTRATE,"
				+ "EN.NETCONTRIBUTION AS NETCONTRIBUTION,EN.PAYMENTDT AS PAYMENTDT,EN.NSSANCTIONEDDT AS NSSANCTIONEDDT,EN.SEPERATIONRESAON AS SEPERATIONRESAON, EN.AIRPORTCODE AS AIRPORTCODE, EN.REGION AS REGION,EN.TRANSDT AS TRANSDT FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO=EMPFID.PENSIONNO AND DELETEFLAG='N' AND NSTYPE='ARREAR' ";

		log.info("Form Name in DAO--------- " + searchBean.getFormName());

		if (searchBean.getFormName().equals("FSArrearProcess")) {
			// sqlQuery+="and EN.VERIFIEDBY is not null ";
		} else if (searchBean.getFormName().equals("FSArrearRecommendation")) {
			sqlQuery += "and ((EN.VERIFIEDBY='FINANCE') OR (EN.VERIFIEDBY='FINANCE,SRMGRREC') OR (EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC') OR (EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC,APPROVED')) ";
		} else if (searchBean.getFormName().equals("FSArrearVerification")) {
			sqlQuery += "and ((EN.VERIFIEDBY='FINANCE,SRMGRREC') OR (EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC') OR (EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC,APPROVED')) ";
		} else if (searchBean.getFormName().equals("FSArrearApproval")) {
			sqlQuery += "and  EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC' ";
		} else if (searchBean.getFormName().equals("FSArrearApproved")) {
			sqlQuery += "and  EN.VERIFIEDBY='FINANCE,SRMGRREC,DGMREC,APPROVED' ";
		}

		if (!unitName.equals("")) {
			whereClause.append(" LOWER(EMPFID.AIRPORTCODE) like'%"
					+ unitName.toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getLoginRegion().equals("")) {
			whereClause.append(" LOWER(EMPFID.REGION) like'%"
					+ searchBean.getLoginRegion().toLowerCase().trim() + "%'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getPensionNo().equals("")) {
			whereClause.append(" EN.PENSIONNO=" + searchBean.getPensionNo());
			whereClause.append(" AND ");
		}

		if (!searchBean.getNssanctionno().equals("")) {
			whereClause.append(" EN.NSSANCTIONNO='"
					+ searchBean.getNssanctionno() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getSanctiondt().equals("")) {
			try {
				sanctiondt = commonUtil.converDBToAppFormat(searchBean
						.getSanctiondt(), "dd/MM/yyyy", "dd-MMM-yyyy");
				whereClause.append(" EN.nssanctioneddt='" + sanctiondt + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}
		if (!searchBean.getPaymentdt().equals("")) {
			try {
				paymentdt = commonUtil.converDBToAppFormat(searchBean
						.getPaymentdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
				whereClause.append(" EN.PAYMENTDT='" + paymentdt + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}
		if (!searchBean.getSeperationreason().equals("")) {
			whereClause.append(" EN.SEPERATIONRESAON='"
					+ searchBean.getSeperationreason() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getEmployeeName().equals("")) {
			whereClause.append(" EMPFID.EMPLOYEENAME='"
					+ searchBean.getEmployeeName() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getTrust().equals("")) {
			whereClause.append(" EN.TRUST='" + searchBean.getTrust() + "'");
			whereClause.append(" AND ");
		}

		if (!searchBean.getArreartype().equals("")) {
			whereClause.append(" EN.ARREARTYPE='" + searchBean.getArreartype()
					+ "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")
				&& searchBean.getPensionNo().equals("")
				&& searchBean.getNssanctionno().equals("")
				&& searchBean.getSanctiondt().equals("")
				&& searchBean.getPaymentdt().equals("")
				&& searchBean.getSeperationreason().equals("")
				&& searchBean.getEmployeeName().equals("")
				&& searchBean.getTrust().equals("")
				&& searchBean.getArreartype().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY NSSANCTIONEDDT desc,NSSANCTIONNO desc";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log
				.info("CPFPTWAdvanceDAO::buildSearchQueryForNoteSheetArrear Leaving Method");
		return dynamicQuery;

	}
	//	For restricting the updation of finalsettlement date
	public String updateNoteSheetArrear(AdvanceBasicBean advanceBean,String frmName,String frmFlag) {
		
		log.info("CPFPTWAdvanceDAO::::::updateNoteSheetArrear"+advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		
		String nsSanctionNo = "", message = "", deleteStatus = "";
		String seperationdt = "", sanctiondt = "", amtadmtdt = "", paymentdt = "";
		
		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
		String flag = "",verfiedBy="";
		
		String tempInfo[] = null;
		String tempData = "", sql2 = "", sql3 = "",delQry="";
		int slno = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			//nsSanctionNo = this.getNoteSheetSequence(con);
			
			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getSanctiondt().trim().equals("")) {
				sanctiondt = commonUtil.converDBToAppFormat(advanceBean
						.getSanctiondt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getAmtadmtdate().trim().equals("")) {
				amtadmtdt = commonUtil.converDBToAppFormat(advanceBean
						.getAmtadmtdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			if (!advanceBean.getPaymentdt().trim().equals("")) {
				paymentdt = commonUtil.converDBToAppFormat(advanceBean
						.getPaymentdt(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}
			
			if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_PROCESS)){
				verfiedBy="FINANCE";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_RECOMMENDATION)){
				verfiedBy="SRMGRREC";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_VERIFICATION)){
				verfiedBy="DGMREC";
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_APPROVAL)){
				verfiedBy="APPROVED";
			}
			
			delQry="delete from employee_nominee_dtls where pensionno='"+advanceBean.getPensionNo()+"'";
			log.info("----delQry-----" + delQry);
			st.executeUpdate(delQry);
			
			nomineeRows = advanceBean.getNomineeRow();
			
			log.info("......nomineeRows......." + nomineeRows);
			
			
			StringTokenizer est=new StringTokenizer(nomineeRows,":"); 
			
			int lengt=est.countTokens();
			String estrarr[]=new String[lengt];
			
			for(int e=0;est.hasMoreTokens();e++)
			{                    			
				
				estrarr[e]=est.nextToken();				
				String expsplit=estrarr[e];
				
				String[] strArr=expsplit.split("#");
				for(int ii=0;ii<strArr.length;ii++){					
					slno=Integer.parseInt(strArr[0]);
					nomineeName=strArr[1];
					nomineeAddress=strArr[2];	
					nomineeDob=strArr[3];	
					nomineeRelation=strArr[4];
					nameofGuardian=strArr[5];
					gaurdianAddress=strArr[6];
					totalShare=strArr[7];
				}
				
				
				if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_PROCESS)){
					sql2 = "insert into employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,equalshare,pensionno)values("
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
						+ advanceBean.getEqualshare()
						+ "','"
						+ advanceBean.getPensionNo()+ "')";					
				}else{
					sql2 = "insert into employee_nominee_dtls(srno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,pensionno)values("
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
						+ advanceBean.getPensionNo() + "')";	
				}
				
				
				
				log.info("----sql2-----" + sql2);
				st.executeUpdate(sql2);
				
			}
			
			// For restricting the updation of personal Information
			/*String updateQry = "update employee_personal_info set DESEGNATION='"
				+ advanceBean.getDesignation()
				+ "',AIRPORTCODE='"
				+ advanceBean.getStation()
				+ "',REGION='"
				+ advanceBean.getRegion()
				+ "',DATEOFSEPERATION_DATE='"
				+ seperationdt		
				+ "',DATEOFSEPERATION_REASON='"
				+ advanceBean.getSeperationreason()		
				+ "' where  PENSIONNO='"
				+ advanceBean.getPensionNo() + "'";
			
			log.info("==========update Query===========" + updateQry);
			int updatedRecord = st.executeUpdate(updateQry);
			*/
			
			// If we provide any permission to change the region & station 
			// we have to update in EMPLOYEE_ADVANCE_NOTEPARAM table 
			
			
			if(frmFlag.equals(Constants.APPLICATION_FINAL_SETTLEMENT_NEW_FORM)){
				
				if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_PROCESS)){
					
					sql3 = "update employee_advance_noteparam set SEPERATIONDT='"
						+ seperationdt
						+ "',SEPERATIONRESAON='"
						+ advanceBean.getSeperationreason()
						+ "',EMPSHARESUBSCRIPITION="
						+ advanceBean.getEmplshare()
						+ ",EMPSHARECONTRIBUTION="
						+ advanceBean.getEmplrshare()
						+ ",LESSCONTRIBUTION="
						+ advanceBean.getPensioncontribution()
						+ ",NETCONTRIBUTION="
						+ advanceBean.getNetcontribution()
						+ ",ADHOCAMOUNT="
						+ advanceBean.getAdhocamt()
						+ ",NSSANCTIONEDDT='"
						+ sanctiondt
						+ "',AMTADMITTEDDT='"
						+ amtadmtdt
						+ "',TRUST='"
						+ advanceBean.getTrust()
						+ "',PAYMENTDT='"
						+ paymentdt
						+ "',REMARKS='"					
						+ commonUtil.escapeSingleQuotes(advanceBean.getRemarks()) 	
						+"',SOREMARKS='"					
						+ commonUtil.escapeSingleQuotes(advanceBean.getSoremarks())
						+ "',VERIFIEDBY='"
						+ verfiedBy
						+ "',SEPERATIONFAVOUR='"
						+ advanceBean.getSeperationfavour()
						+ "',ARREARTYPE='"
						+ advanceBean.getArreartype()
						+ "',ARREARDATE='"
						+ advanceBean.getArreardate()
						+ "',FROMFINYEAR='"
						+ advanceBean.getFromfinyear()
						+ "',TOFINYEAR='"
						+ advanceBean.getTofinyear()
						+ "',FROMREVISEDINTERESTRATE='"
						+ advanceBean.getInterestratefrom()
						+ "',TOREVISEDINTERESTRATE='"
						+ advanceBean.getInterestrateto()						
						+ "',SEPERATIONREMARKS='"
						+ commonUtil.escapeSingleQuotes(advanceBean
								.getSeperationremarks()) + "',AAISANCTIONNO='"
								+ advanceBean.getSanctionno() + "' where NSSANCTIONNO="
								+ advanceBean.getNssanctionno() + " and pensionno='"
								+ advanceBean.getPensionNo() + "'";						
					
				}else{
					sql3 = "update employee_advance_noteparam set SEPERATIONDT='"
						+ seperationdt
						+ "',SEPERATIONRESAON='"
						+ advanceBean.getSeperationreason()
						+ "',EMPSHARESUBSCRIPITION="
						+ advanceBean.getEmplshare()
						+ ",EMPSHARECONTRIBUTION="
						+ advanceBean.getEmplrshare()
						+ ",LESSCONTRIBUTION="
						+ advanceBean.getPensioncontribution()
						+ ",NETCONTRIBUTION="
						+ advanceBean.getNetcontribution()
						+ ",ADHOCAMOUNT="
						+ advanceBean.getAdhocamt()
						+ ",NSSANCTIONEDDT='"
						+ sanctiondt
						+ "',AMTADMITTEDDT='"
						+ amtadmtdt
						+ "',TRUST='"
						+ advanceBean.getTrust()
						+ "',PAYMENTDT='"
						+ paymentdt
						+ "',REMARKS='"					
						+ commonUtil.escapeSingleQuotes(advanceBean.getRemarks()) 	
						+"',SOREMARKS='"					
						+ commonUtil.escapeSingleQuotes(advanceBean.getSoremarks())
						+ "',VERIFIEDBY=VERIFIEDBY||','||'"
						+ verfiedBy
						+ "',SEPERATIONFAVOUR='"
						+ advanceBean.getSeperationfavour()
						+ "',ARREARTYPE='"
						+ advanceBean.getArreartype()
						+ "',ARREARDATE='"
						+ advanceBean.getArreardate()
						+ "',FROMFINYEAR='"
						+ advanceBean.getFromfinyear()
						+ "',TOFINYEAR='"
						+ advanceBean.getTofinyear()
						+ "',FROMREVISEDINTERESTRATE='"
						+ advanceBean.getInterestratefrom()
						+ "',TOREVISEDINTERESTRATE='"
						+ advanceBean.getInterestrateto()						
						+ "',SEPERATIONREMARKS='"
						+ commonUtil.escapeSingleQuotes(advanceBean
								.getSeperationremarks()) + "',AAISANCTIONNO='"
								+ advanceBean.getSanctionno() + "' where NSSANCTIONNO="
								+ advanceBean.getNssanctionno() + " and pensionno='"
								+ advanceBean.getPensionNo() + "'";
				}
			}else{
				sql3 = "update employee_advance_noteparam set SEPERATIONDT='"
					+ seperationdt
					+ "',SEPERATIONRESAON='"
					+ advanceBean.getSeperationreason()
					+ "',EMPSHARESUBSCRIPITION="
					+ advanceBean.getEmplshare()
					+ ",EMPSHARECONTRIBUTION="
					+ advanceBean.getEmplrshare()
					+ ",LESSCONTRIBUTION="
					+ advanceBean.getPensioncontribution()
					+ ",NETCONTRIBUTION="
					+ advanceBean.getNetcontribution()
					+ ",ADHOCAMOUNT="
					+ advanceBean.getAdhocamt()
					+ ",NSSANCTIONEDDT='"
					+ sanctiondt
					+ "',AMTADMITTEDDT='"
					+ amtadmtdt
					+ "',TRUST='"
					+ advanceBean.getTrust()
					+ "',PAYMENTDT='"
					+ paymentdt
					+ "',REMARKS='"					
					+ commonUtil.escapeSingleQuotes(advanceBean.getRemarks()) 		
					+"',SOREMARKS='"					
					+ commonUtil.escapeSingleQuotes(advanceBean.getSoremarks())
					+ "',SEPERATIONFAVOUR='"
					+ advanceBean.getSeperationfavour()
					+ "',ARREARTYPE='"
					+ advanceBean.getArreartype()
					+ "',ARREARDATE='"
					+ advanceBean.getArreardate()
					+ "',FROMFINYEAR='"
					+ advanceBean.getFromfinyear()
					+ "',TOFINYEAR='"
					+ advanceBean.getTofinyear()
					+ "',FROMREVISEDINTERESTRATE='"
					+ advanceBean.getInterestratefrom()
					+ "',TOREVISEDINTERESTRATE='"
					+ advanceBean.getInterestrateto()
					+ "',SEPERATIONREMARKS='"
					+ commonUtil.escapeSingleQuotes(advanceBean
							.getSeperationremarks()) + "',AAISANCTIONNO='"
							+ advanceBean.getSanctionno() + "' where NSSANCTIONNO="
							+ advanceBean.getNssanctionno() + " and pensionno='"
							+ advanceBean.getPensionNo() + "'";
				
			}
			
			log.info("----sql3-----" + sql3);
			st.executeUpdate(sql3);
			
			CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();
			
			BeanUtils.copyProperties(transBean,advanceBean);
			
			
			CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());
			
			if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_PROCESS)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_PROCESS_ARREAR_FORM,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_RECOMMENDATION)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_VERIFICATION)){				
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_DGM,advanceBean.getLoginUserDesignation());
			}else if(frmName.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_APPROVAL)){
				cpfInfo.createFSFSATrans(advanceBean.getPensionNo(),transBean,advanceBean.getNssanctionno(),"FS",Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL,advanceBean.getLoginUserDesignation());
			}
			
			
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
		
	}

	public ArrayList MISReport(String reg, String fromDate, String toDate,
			String subPurposeType, String purposeType, String trust,
			String station) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		AdvanceBasicReportBean basicBean = null;
		ArrayList reportList = new ArrayList();
		ArrayList sanctionOrderList = new ArrayList();

		String region = "", currentDate = "", mon = "", year = "", pfid = "", transID = "";
		String PurposeOptionType = "";
		int dependentMarriageCount = 0, dependentEducationCount = 0, recCount = 0;
		double dependentEducationAmount = 0.0, approvedAmount = 0.0;
		long dependentMarriageAmount = 0;

		String sqlQuery = this.buildMISReportQuery(reg, fromDate, toDate,
				subPurposeType, purposeType, trust, station);

		log.info("CPFPTWAdvanceDAO::MISReport" + sqlQuery);
		basicBean = new AdvanceBasicReportBean();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {

				if (rs.getString("PURPOSETYPE") != null) {

					String purposeTypes = "";
					if (rs.getString("PURPOSETYPE").equals("COST")) {
						purposeTypes = "Cost Of Passage";
					} else if (rs.getString("PURPOSETYPE").equals("OBMARRIAGE")) {
						purposeTypes = "Marriage";
					} else if (rs.getString("PURPOSETYPE").equals("EDUCATION")) {
						purposeTypes = "Higher Education";
					} else if (rs.getString("PURPOSETYPE").equals("DEFENCE")) {
						purposeTypes = "Defense Of Court Case";
					} else if (rs.getString("PURPOSETYPE").equals("HBA")) {
						purposeTypes = "HBA";
					} else if (rs.getString("PURPOSETYPE").equals("HE")) {
						purposeTypes = "Higher Education";
					} else {
						purposeTypes = commonUtil
								.capitalizeFirstLettersTokenizer(rs
										.getString("PURPOSETYPE"));
					}

					basicBean.setPurposeType(purposeTypes);

				} else {
					basicBean.setPurposeType("---");
				}

				if (rs.getString("RECCOUNT") != null) {

					if (!rs.getString("RECCOUNT").equals("NIL")) {
						recCount += Integer.parseInt(rs.getString("RECCOUNT"));
						basicBean.setReccount(Integer.toString(recCount));
					}

				} else {
					basicBean.setReccount("");
				}

				if (rs.getString("APPROVEDAMNT") != null) {

					if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
						approvedAmount += Double.parseDouble(rs
								.getString("APPROVEDAMNT"));
						basicBean.setApprovedAmt(commonUtil
								.getCurrency(approvedAmount));
					}

				} else {
					basicBean.setApprovedAmt("---");
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {

					PurposeOptionType = commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("PURPOSEOPTIONTYPE"));

					basicBean.setPurposeOptionType(PurposeOptionType);

				} else {
					basicBean.setPurposeOptionType("---");
				}

				if (rs.getString("PURPOSETYPE").equals("HBA")) {

					if (rs.getString("PURPOSEOPTIONTYPE")
							.equals("PURCHASESITE")) {

						basicBean
								.setPurchasesitecount(rs.getString("RECCOUNT"));

						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setPurchasesiteamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"PURCHASEHOUSE")) {

						basicBean.setPurchasehousecount(rs
								.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setPurchasehouseamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"CONSTRUCTIONHOUSE")) {

						basicBean.setConstructionhousecount(rs
								.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setConstructionhouseamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"ACQUIREFLAT")) {

						basicBean.setAcquireflatcount(rs.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setAcquireflatamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"RENOVATIONHOUSE")) {

						basicBean.setRenovationhousecount(rs
								.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setRenovationhouseamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}
					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"REPAYMENTHBA")) {

						basicBean
								.setRepaymenthbacount(rs.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setRepaymenthbaamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					} else if (rs.getString("PURPOSEOPTIONTYPE").equals(
							"HBAOTHERS")) {

						basicBean.setHbaotherscount(rs.getString("RECCOUNT"));
						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setHbaothersamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}

					}
				} else if (rs.getString("PURPOSETYPE").equals("MARRIAGE")) {

					if (rs.getString("PURPOSEOPTIONTYPE").equals("SELF")) {
						basicBean
								.setSelfmarriagecount(rs.getString("RECCOUNT"));

						if (!rs.getString("APPROVEDAMNT").equals("NIL")) {
							basicBean.setSelfmarriageamount(commonUtil
									.getCurrency(rs.getDouble("APPROVEDAMNT")));
						}
					} else {
						if (!rs.getString("RECCOUNT").equals("NIL")) {
							dependentMarriageCount += Integer.parseInt(rs
									.getString("RECCOUNT"));
							basicBean.setDependentmarriagecount(Integer
									.toString(dependentMarriageCount));

							dependentMarriageAmount += Long.parseLong(rs
									.getString("APPROVEDAMNT"));
							basicBean.setDependentmarriageamount(Long
									.toString(dependentMarriageAmount));
						} else {
							basicBean.setDependentmarriagecount(rs
									.getString("RECCOUNT"));
						}
					}

				} else if (rs.getString("PURPOSETYPE").equals("HE")) {
					if (!rs.getString("RECCOUNT").equals("NIL")) {
						dependentEducationCount += Integer.parseInt(rs
								.getString("RECCOUNT"));
						basicBean.setDependenteducationcount(Integer
								.toString(dependentEducationCount));

						dependentEducationAmount += Double.parseDouble(rs
								.getString("APPROVEDAMNT"));
						basicBean.setDependenteducationamount(Double
								.toString(dependentEducationAmount));
					} else {
						basicBean.setDependenteducationcount(rs
								.getString("RECCOUNT"));
					}
				}

				fromDate = "";

			}

			sanctionOrderList.add(basicBean);

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return sanctionOrderList;

	}

	// New Method

	public String buildMISReportQuery(String reg, String fromDate,
			String toDate, String subPurposeType, String purposeType,
			String trust, String station) {
		log.info("CPFPTWAdvanceDAO::buildMISReportQuery-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", sqlQuery = "", orderBy = "";

		/*
		 * sqlQuery = "SELECT AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE
		 * AS PURPOSEOPTIONTYPE,count(*) as RECCOUNT,sum(AF.APPROVEDAMNT) as
		 * APPROVEDAMNT " + " FROM EMPLOYEE_PERSONAL_INFO PI,
		 * EMPLOYEE_ADVANCES_FORM AF WHERE AF.PENSIONNO = PI.PENSIONNO and
		 * AF.DELETEFLAG='N' and AF.ADVANCETYPE='PFW' ";
		 */

		sqlQuery = "select data2.PURPOSETYPE,data2.PURPOSEOPTIONTYPE,(CASE WHEN data1.APPROVEDAMNT is not null then TO_CHAR(data1.APPROVEDAMNT) WHEN data1.APPROVEDAMNT is null then 'NIL'"
				+ "END ) as APPROVEDAMNT,(CASE WHEN data1.RECCOUNT is not null then TO_CHAR(data1.RECCOUNT) WHEN data1.RECCOUNT is null then 'NIL'"
				+ "END ) as RECCOUNT from  (select ADVANCETYPE,PURPOSETYPE,PURPOSEOPTIONTYPE from employee_form_def) data2,"
				+ "(SELECT AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,count(*) as RECCOUNT, "
				+ " sum(AF.APPROVEDAMNT) as APPROVEDAMNT   FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF"
				+ " WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG = 'N'";

		if (!reg.equals("NO-SELECT")) {
			whereClause.append(" PI.REGION='" + reg + "'");
			whereClause.append(" AND ");
		}

		if (!station.equals("NO-SELECT")) {
			whereClause.append(" PI.AIRPORTCODE='" + station + "'");
			whereClause.append(" AND ");
		}

		if (!subPurposeType.equals("")) {
			whereClause.append(" AF.PURPOSEOPTIONTYPE='"
					+ subPurposeType.toUpperCase() + "'");
			whereClause.append(" AND ");
		}

		if (!purposeType.equals("NO-SELECT")) {
			whereClause.append(" AF.PURPOSETYPE='" + purposeType.toUpperCase()
					+ "'");
			whereClause.append(" AND ");
		}

		if (!trust.equals("NO-SELECT")) {
			whereClause.append(" AF.TRUST='" + trust + "'");
			whereClause.append(" AND ");
		}

		if ((!fromDate.equals("")) && (!toDate.equals(""))) {
			try {
				whereClause.append(" AF.ADVANCETRANSDT between to_date('"
						+ fromDate + "','dd-mm-yyyy') and last_day(to_date('"
						+ toDate + "','dd-mm-yyyy'))");
			} catch (Exception e) {
				e.printStackTrace();
			}
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);

		if (reg.equals("NO-SELECT") && subPurposeType.equals("NO-SELECT")
				&& station.equals("NO-SELECT") && trust.equals("NO-SELECT")
				&& fromDate.equals("") && toDate.equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		orderBy = "GROUP BY AF.ADVANCETYPE, AF.PURPOSETYPE, AF.PURPOSEOPTIONTYPE) data1 where data2.PURPOSEOPTIONTYPE =data1.PURPOSEOPTIONTYPE (+)  and  data2.PURPOSETYPE = data1.PURPOSETYPE(+) order by data2.PURPOSETYPE";
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("CPFPTWAdvanceDAO::buildMISReportQuery Leaving Method");
		return dynamicQuery;
	}

	public String updateFinalSettlementArrearInfo(AdvanceBasicBean advanceBean) {
		log.info(advanceBean.getLodInfo());
		Connection con = null;
		Statement st = null;
		Statement insertSt = null;
		int updatedRecords = 0, insertedRecord = 0;
		String advanceTransID = "", insertFmlyQuery = "", updateQuery = "", updateQry = "", advanceTrnsStatus = "N", message = "", insertHBAQuery = "", insertHEQuery = "";
		String seperationdt = "", nsSanctionNo = "", placeOfPostingValue = "", appointmentDt = "", arreardt = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			insertSt = con.createStatement();

			String fmlyDOB = "";

			log.info("======Posting Details====="
					+ advanceBean.getPostingdetails());

			if (!advanceBean.getSeperationdate().trim().equals("")) {
				seperationdt = commonUtil.converDBToAppFormat(advanceBean
						.getSeperationdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getAppointmentdate().trim().equals("")) {
				appointmentDt = commonUtil.converDBToAppFormat(advanceBean
						.getAppointmentdate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getArreardate().trim().equals("")) {
				arreardt = commonUtil.converDBToAppFormat(advanceBean
						.getArreardate(), "dd/MM/yyyy", "dd-MMM-yyyy");
			}

			if (!advanceBean.getPostingdetails().equals("")) {
				placeOfPostingValue = "Y";
			} else {
				placeOfPostingValue = "N";
			}

			updateQuery = "UPDATE EMPLOYEE_ADVANCE_NOTEPARAM SET SEPERATIONDT='"

					+ seperationdt
					+ "',SEPERATIONRESAON='"
					+ advanceBean.getSeperationreason()
					+ "',ARREARTYPE='"
					+ advanceBean.getArreartype()
					+ "',TRANSDT='"
					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
					+ "',ARREARDATE='"
					+ arreardt
					+ "',FROMFINYEAR='"
					+ advanceBean.getFromfinyear()
					+ "',TOFINYEAR='"
					+ advanceBean.getTofinyear()
					+ "',FROMREVISEDINTERESTRATE='"
					+ advanceBean.getInterestratefrom()
					+ "',TOREVISEDINTERESTRATE='"
					+ advanceBean.getInterestrateto()
					+ "',REGION='"
					+ advanceBean.getRegion()
					+ "',AIRPORTCODE='"
					+ advanceBean.getStation()
					+ "',DESIGNATION='"
					+ advanceBean.getDesignation()
					+ "' where  PENSIONNO='"
					+ advanceBean.getPensionNo()
					+ "' and NSSANCTIONNO='"
					+ advanceBean.getNssanctionno() + "'";

			log
					.info("CPFPTWAdvanceDAO::updateFinalSettlementArrearInfo::::EMPLOYEE_ADVANCE_NOTEPARAM"
							+ updateQuery);

			updatedRecords = st.executeUpdate(updateQuery);

			if (updatedRecords != 0) {

				updateQry = "UPDATE epis_noteparam_personal_info SET SEPERATIONREASON='"

						+ advanceBean.getSeperationreason()
						+ "',EMPLOYEENAME='"
						+ advanceBean.getEmployeeName()
						+ "',FHNAME='"
						+ advanceBean.getFhName()
						+ "',EMPLOYEEAGE='"
						+ advanceBean.getEmpage()
						+ "',ADDRESS='"
						+ advanceBean.getEmpaddress()
						+ "',STATION='"
						+ advanceBean.getEmpstation()
						+ "' where  PENSIONNO='"
						+ advanceBean.getPensionNo()
						+ "' and SANCTIONNO='"
						+ advanceBean.getNssanctionno()
						+ "'";

				log
						.info("CPFPTWAdvanceDAO::updateFinalSettlementArrearInfo----updateQry"
								+ updateQry);

				insertedRecord = st.executeUpdate(updateQry);
				// For restricting the updation of personal Information Frm  25-Apr-2012
				//  Previlages to Dateofjoing,DateOfBirth,wetherOption updating in employee_personal_info is restricted from Jun-2011
				/*if (!advanceBean.getDesignation().equals("")) {
					updateQry = "update employee_personal_info set DESEGNATION='"
							+ advanceBean.getDesignation()
							+ "', DEPARTMENT='"
							+ advanceBean.getDepartment()
							+ "', CPFACNO='"
							+ advanceBean.getCpfaccno() 
							+ "',AIRPORTCODE='"
							+ advanceBean.getStation()
							+ "',REGION='"
							+ advanceBean.getRegion()
							+ "',PERMANENTADDRESS='"
							+ advanceBean.getPermenentaddress()
							+ "',TEMPORATYADDRESS='"
							+ advanceBean.getPresentaddress()
							+ "',EMAILID='"
							+ advanceBean.getMailID()
							+ "',PHONENUMBER='"
							+ advanceBean.getPhoneno()
							+ "' where  PENSIONNO='"
							+ advanceBean.getPensionNo() + "'";
					log.info("==========update Query===========" + updateQry);
					int updatedRecord = st.executeUpdate(updateQry);
				}*/

				message = advanceBean.getNssanctionno();

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return message;
	}

	public ArrayList pfwSummaryReport(String region, String fromDate,
			String toDate, String station) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = null;
		String sqlQuery = "SELECT REGION,SUBSTR(PURPOSETYPE,0,2) as PURPOSETYPE,SUM(APPROVEDAMNT) AS APPROVEDAMNT FROM EMPLOYEE_ADVANCES_FORM WHERE DELETEFLAG='N' AND ADVANCETYPE='PFW' AND "
				+ "SANCTIONDATE BETWEEN '"
				+ fromDate
				+ "' and '"
				+ toDate
				+ "'   GROUP BY REGION,PURPOSETYPE ORDER BY REGION,PURPOSETYPE";

		log.info("PFW SummaryReport---" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			String tempRegion = "", orginalStation = "", purposeType = "", approvedAmount = "";
			StringBuffer buffer = new StringBuffer();
			StringBuffer newToken = new StringBuffer();
			while (rs.next()) {
				basicReportBean = new AdvanceBasicReportBean();

				if (rs.getString("REGION") != null) {
					orginalStation = rs.getString("REGION");
					basicReportBean.setRegion(rs.getString("REGION"));
				} else {
					basicReportBean.setRegion("N/A");
				}

				if (tempRegion.equals("")) {
					tempRegion = orginalStation;
					buffer.append(orginalStation);
					buffer.append(",");
					buffer.append(rs.getString("PURPOSETYPE"));
					buffer.append("-");
					buffer.append(rs.getString("APPROVEDAMNT"));
				} else if (tempRegion.equals(orginalStation)) {
					buffer.append(",");
					buffer.append(rs.getString("PURPOSETYPE"));
					buffer.append("-");
					buffer.append(rs.getString("APPROVEDAMNT"));
				} else if (!tempRegion.equals(orginalStation)) {
					tempRegion = orginalStation;
					buffer.append("*");
					buffer.append(orginalStation);
					buffer.append(",");
					buffer.append(rs.getString("PURPOSETYPE"));
					buffer.append("-");
					buffer.append(rs.getString("APPROVEDAMNT"));

				}

			}
			StringTokenizer tokens = new StringTokenizer(buffer.toString(), "*");
			String[] fndString = { "HB", "HE", "MA" };
			String appendText = "", needPlaceBeforeText = "", previousAppendText = "", finalString = "", availtext = "";
			int startIndex = 0, lastIndex = 0, cnt = 0, counter = 0;
			while (tokens.hasMoreElements()) {
				String token = tokens.nextElement().toString();
				for (int k = 0; k < fndString.length; k++) {
					if (checkStringContains(token, fndString[k]) == false) {
						cnt++;
						if (availtext.equals("")) {
							availtext = fndString[k] + "-0.00";
						} else {
							log
									.info("ElSE cnt" + cnt + "availtext"
											+ availtext);
							if (cnt == 2 && k == 2) {
								availtext = availtext + "," + fndString[k]
										+ "-0.00";
								availtext = token.substring(0, token.length())
										+ "," + availtext;

							} else {
								availtext = availtext + "," + fndString[k]
										+ "-0.00";
							}

						}

					} else {
						if (cnt != 0) {
							if (availtext.equals("") && counter == 0) {
								availtext = getReturnText(token, fndString[k])
										+ getStringContains(token, fndString[k]);
							} else {
								availtext = getReturnText(token, fndString[k])
										+ availtext
										+ ","
										+ getStringContains(token, fndString[k]);
							}
						} else {
							counter++;
							if (counter == 3) {
								availtext = token;
							} else if (cnt == 0 && k == 2) {
								availtext = availtext
										+ ","
										+ getStringContains(token, fndString[k]);
							}
						}
						log
								.info("nOT ElSE cnt" + cnt + "availtext"
										+ availtext);
						cnt = 0;
					}

				}

				log.info("token" + availtext);
				reportList.add(availtext);
				availtext = "";
				appendText = "";
				cnt = 0;
				counter = 0;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, con);
		}
		return reportList;

	}

	public boolean checkStringContains(String ornignal, String searchText) {
		boolean txtflag = false;
		int index1 = ornignal.indexOf(searchText);
		if (index1 != -1) {
			txtflag = true;
		}
		return txtflag;
	}

	public String getReturnText(String ornignal, String searchText) {
		String text = "";
		int index1 = ornignal.indexOf(searchText);
		text = ornignal.substring(0, index1);
		return text;
	}

	public String getStringContains(String ornignal, String searchText) {
		String returnString = "", afterFindString = "";
		int startIndex = ornignal.indexOf(searchText);
		afterFindString = ornignal.substring(startIndex, ornignal.length());

		int endIndex = afterFindString.indexOf(",");
		if (endIndex == -1) {
			endIndex = afterFindString.length();
		}
		// log.info("ornignal"+ornignal+"searchText"+searchText+"afterFindString"+afterFindString+"startIndex"+startIndex+"endIndex"+endIndex);
		returnString = afterFindString.substring(0, endIndex);
		return returnString;
	}

	public String addStringContains(String ornignal, String newString,
			int startIndex, int endIndex) {
		String afterOrignal = "", finalString = "";
		if (startIndex != 0) {
			afterOrignal = ornignal.substring(0, startIndex);
		} else {
			afterOrignal = ornignal;
		}

		finalString = afterOrignal + newString
				+ ornignal.substring(startIndex, endIndex);
		return finalString;
	}
// On 21-Jan-2012 for Checking  whether  entry  is there in cash book while deletion of  record
	public ArrayList searchRecordsToDelete(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("CPFPTWAdvanceDAO::searchRecordsToDelete"
				+ advanceSearchBean.getPfid());
		String sanctionNo = "", dateOfBirth = "", pfid = "", advanceTransId = "", requestType = "", pensionNo = "", region = "", selectQuery = "", orderBy = "", dynamicQuery = "",cashbookCondition = "";
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		sanctionNo = advanceSearchBean.getSanctionno();
		pensionNo = advanceSearchBean.getPensionNo();
		advanceTransId = advanceSearchBean.getAdvanceTransID();
		requestType = advanceSearchBean.getRequestType();

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (requestType.equals("FinalSettlement")) {

				selectQuery = "SELECT EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.DESEGNATION AS DESEGNATION,EMPFID.PENSIONNO,EMPFID.AIRPORTCODE AS ,EMPFID.REGION AS REGION,"
						+ "EN.NSSANCTIONNO AS NSSANCTIONNO,EN.VERIFIEDBY AS VERIFIEDBY,EN.TRANSDT AS TRANSDT,EN.NSTYPE AS NSTYPE,EN.VERIFIEDBY AS VERIFIEDBY,"
						+ "EN.PAYMENTDT AS PAYMENTDT,EN.NSSANCTIONEDDT AS NSSANCTIONEDDT,EN.SEPERATIONRESAON AS SEPERATIONRESAON,EN.ARREARTYPE AS ARREARTYPE  FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCE_NOTEPARAM EN WHERE EN.PENSIONNO=EMPFID.PENSIONNO AND EN.DELETEFLAG='N' ";

				if (!sanctionNo.equals("")) {
					whereClause.append(" EN.NSSANCTIONNO='" + sanctionNo + "'");
					whereClause.append(" AND ");
				}

				if (!pensionNo.equals("")) {
					whereClause.append(" EN.PENSIONNO='" + pensionNo + "'");
					whereClause.append(" AND ");
				}

				query.append(selectQuery);

				if (sanctionNo.equals("") && pensionNo.equals("")) {

				} else {
					query.append(" AND ");
					query.append(this.sTokenFormat(whereClause));
				}

				orderBy = "order by pensionno";
				query.append(orderBy);			 
				/*
				 * Checking  whether  entry  is there in cash book while deletion of  record
				 * cashbookCondition=")FS, (SELECT KEYNO,INFO.EMP_PARTY_CODE,TRANSID  FROM CB_VOUCHER_INFO INFO WHERE INFO.OTHERMODULELINK = 'FS' AND INFO.TRANSID IS NOT NULL AND KEYNO IS NOT NULL"
						+ " ORDER BY INFO.EMP_PARTY_CODE) CASHBOOK  WHERE FS.NSSANCTIONNO  = CASHBOOK.TRANSID(+)";	
				query.append(cashbookCondition);*/
				dynamicQuery = query.toString();
			} else {
				selectQuery = " SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DESEGNATION AS DESEGNATION,"
						+ "EAF.ADVANCETRANSID AS ADVANCETRANSID, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.APPROVEDDT  AS APPROVEDDT,"
						+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, "
						+ "EMPFID.PENSIONNO,EMPFID.DATEOFJOINING AS DATEOFJOINING FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N'AND EAF.ADVANCETYPE='"
						+ requestType + "'";

				if (!advanceTransId.equals("")) {
					whereClause.append(" EAF.ADVANCETRANSID='" + advanceTransId
							+ "'");
					whereClause.append(" AND ");
				}

				if (!pensionNo.equals("")) {
					whereClause.append(" EAF.PENSIONNO='" + pensionNo + "'");
					whereClause.append(" AND ");
				}

				query.append(selectQuery);

				if (pensionNo.equals("") && advanceTransId.equals("")) {

				} else {
					query.append(" AND ");
					query.append(this.sTokenFormat(whereClause));
				}

				orderBy = "order by pensionno";
				query.append(orderBy);
				/*
				 * Checking  whether  entry  is there in cash book while deletion of  record
				 * cashbookCondition=")LOANS, (SELECT KEYNO,INFO.EMP_PARTY_CODE,TRANSID  FROM CB_VOUCHER_INFO INFO WHERE INFO.OTHERMODULELINK = 'PFW' AND INFO.TRANSID IS NOT NULL AND KEYNO IS NOT NULL"
						+ " ORDER BY INFO.EMP_PARTY_CODE) CASHBOOK  WHERE LOANS.ADVANCETRANSID = CASHBOOK.TRANSID(+)";	
				query.append(cashbookCondition);*/
				dynamicQuery = query.toString();
			}

			log.info("CPFPTWAdvanceDAO::searchRecordsToDelete" + dynamicQuery);
			ResultSet rs = st.executeQuery(dynamicQuery);
			while (rs.next()) {
				searchBean = new AdvanceSearchBean();

				searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				if (rs.getString("PENSIONNO") != null) {
					pensionNo = rs.getString("PENSIONNO");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					dateOfBirth = "---";
				}

				if (requestType.equals("FinalSettlement")) {
					if (rs.getDate("PAYMENTDT") != null) {
						searchBean.setPaymentdt(commonUtil
								.converDBToAppFormat(rs.getDate("PAYMENTDT")));
					} else {
						searchBean.setPaymentdt("---");
					}

					if (rs.getDate("NSSANCTIONEDDT") != null) {
						searchBean.setSanctiondt(commonUtil
								.converDBToAppFormat(rs
										.getDate("NSSANCTIONEDDT")));
					} else {
						searchBean.setSanctiondt("---");
					}

					if (rs.getString("TRANSDT") != null) {
						searchBean.setTransdt(CommonUtil.getDatetoString(rs
								.getDate("TRANSDT"), "dd-MMM-yyyy"));
					}

					if (rs.getString("ARREARTYPE") != null) {
						searchBean.setArreartype(rs.getString("ARREARTYPE"));
					} else {
						searchBean.setArreartype("");
					}

					searchBean.setFsType(rs.getString("NSTYPE"));
					searchBean.setSanctionno(rs.getString("NSSANCTIONNO"));
					searchBean.setRegion(rs.getString("REGION"));
					searchBean.setStation(rs.getString("AIRPORTCODE"));
					searchBean.setSeperationreason(rs
							.getString("SEPERATIONRESAON"));

					if (rs.getString("VERIFIEDBY") != null) {
						
						if (searchBean.getFsType().equals("NON-ARREAR")) {

							if (rs.getString("VERIFIEDBY").equals(
									Constants.FINAL_SETTLEMENT_VERIFIED_BY)) {
								searchBean.setVerifiedByStatus("Completed");
							} else {
								searchBean.setVerifiedByStatus("On Process");
							}
						} else if (searchBean.getFsType().equals("ARREAR")) {
							if (rs
									.getString("VERIFIEDBY")
									.equals(
											Constants.FINAL_SETTLEMENT_ARREAR_VERIFIED_BY)) {
								searchBean.setVerifiedByStatus("Completed");
							} else {
								searchBean.setVerifiedByStatus("On Process");
							}

						}
					} else {
						searchBean.setVerifiedBy("On Process");
					}

				} else {

					searchBean.setAdvanceType(rs.getString("ADVANCETYPE")
							.toUpperCase());
					searchBean
							.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
					searchBean.setPurposeType(rs.getString("PURPOSETYPE")
							.toUpperCase());
					searchBean.setAdvanceTransIDDec(searchBean.getAdvanceType()
							.toUpperCase()
							+ "/"
							+ searchBean.getPurposeType()
							+ "/"
							+ rs.getString("ADVANCETRANSID"));
					if (rs.getString("APPROVEDDT") != null) {
						searchBean.setTransMnthYear(CommonUtil.getDatetoString(
								rs.getDate("APPROVEDDT"), "dd-MMM-yyyy"));
					} else {
						searchBean.setTransMnthYear("---");
					}

					if ((rs.getString("FINALTRANSSTATUS") != null)
							&& rs.getString("FINALTRANSSTATUS").equals("A")) {
						searchBean.setFinalTrnStatus("Completed");
					} else {
						searchBean.setFinalTrnStatus("On Process");
					}

				}

				pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
						dateOfBirth, commonUtil.leadingZeros(5, pensionNo));
				searchBean.setPfid(pfid);
				searchBean.setPensionNo(pensionNo);
				searchBean.setDesignation(rs.getString("DESEGNATION"));
				searchBean.setRequestType(requestType);
				if(rs.getString("VERIFIEDBY")!=null){
				searchBean.setVerifiedBy(rs.getString("VERIFIEDBY"));
				}else{
					searchBean.setVerifiedBy("");
				}

				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return searchList;

	}

	// New Method
	public int deleteRecords(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		int n = 0;
		String nssanctionNo = "", pensionNo = "", advanceTransId = "", advanceType = "", purposeType = "", fsType = "", delQuery = "", dynamicQuery = "", orderBy = "",cpfpfwTransCD="",transType="",transId="";
		nssanctionNo = advanceSearchBean.getNssanctionno();
		pensionNo = advanceSearchBean.getPensionNo();
		advanceTransId = advanceSearchBean.getAdvanceTransID();
		advanceType = advanceSearchBean.getAdvanceType();
		purposeType = advanceSearchBean.getPurposeType().trim();
		fsType = advanceSearchBean.getFsType();
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		
		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			if (advanceType.equals("CPF") || (advanceType.equals("PFW"))) {
				transId=advanceTransId;
				delQuery = "update employee_advances_form  set deleteflag='Y' where PENSIONNO='"
						+ pensionNo
						+ "' AND ADVANCETRANSID='"
						+ advanceTransId
						+ "' And "
						+ " ADVANCETYPE='"
						+ advanceType
						+ "' AND PURPOSETYPE='" + purposeType + "'";

			} else {
				transId =nssanctionNo;
				delQuery = "update employee_advance_noteparam set deleteflag='Y' where PENSIONNO='"
						+ pensionNo
						+ "' and  NSSANCTIONNO='"
						+ nssanctionNo
						+ "' and NSTYPE='" + fsType + "'";

			}

			log.info("deleteRecords::delQuery---" + delQuery);
			n = st.executeUpdate(delQuery);
			
			if(advanceType.equals("CPF")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_CPF_DELETE;
				transType="CPF";
			}else if(advanceType.equals("PFW")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_PFW_DELETE;;
				transType="PFW";
			}else if(fsType.equals("NON-ARREAR")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_FS_DELETE;
				transType="FS";
			}else if(fsType.equals("ARREAR")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_FSA_DELETE;
				transType="FS Arrear";
			}else{
				cpfpfwTransCD="";
				transType="";
			}
			 
			log.info("CPFPTWAdvanceDAO::deleteRecords()---Arrear Type--"+fsType+"---cpfpfwTransCD----"+cpfpfwTransCD+"----transType--"+transType);
			
			CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advanceSearchBean.getLoginUserId(),advanceSearchBean.getLoginUserName(),advanceSearchBean.getLoginUnitCode(),advanceSearchBean.getLoginRegion(),advanceSearchBean.getLoginUserDispName());			 			
			cpfInfo.deleteCPFPFWTrans(pensionNo,transId,transType,cpfpfwTransCD,advanceSearchBean.getVerifiedBy());
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;

	}

	public String updateBankInfo(String pensionNo, String transId,
			String bankFlag, String paymentFlag, EmpBankMaster bankBean) {

		Connection con = null;
		Statement st = null;
		String updateQuery = "", updatePaymentQry = "", updatePaymentQry1 = "";
		int updatedRecords = 0, count = 0, updatedRecords1 = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();

			count = this.checkBankDetails(con,pensionNo, transId);

			log.info("--in DAO- payment flag- " + paymentFlag
					+ "---bank flag--" + bankFlag);
			if (paymentFlag.equals("Y")) {

				if (!transId.equals(""))
					bankBean.setTransId(transId);

				if (count == 0) {
					this.addEmployeeBankInfo(con, bankBean, pensionNo);

				} else if ((count != 0) && (bankFlag.equals("Y"))) {
					updateQuery = "update EMPLOYEE_BANK_INFO set NAME='"
							+ bankBean.getBankempname() + "',SAVINGBNKACCNO='"
							+ bankBean.getBanksavingaccno() + "',BANKNAME='"
							+ bankBean.getBankname() + "',NEFTRTGSCODE='"
							+ bankBean.getBankemprtgsneftcode()
							+ "',ADDRESS='" + bankBean.getBankempaddrs()
							+ "',BRANCHADDRESS='" + bankBean.getBranchaddress()
							+ "',MICRONO='" + bankBean.getBankempmicrono()
							+ "',MAILID='" + bankBean.getEmpmailid()							 
							+ "',PARTYNAME='" + bankBean.getPartyName()
							+ "',PARTYADDRESS='" + bankBean.getPartyAddress()
							+ "',USERNAME=USERNAME||','||'" + this.userName
							+ "',LASTACTIVE='"
							+ commonUtil.getCurrentDate("dd-MMM-yyyy")
							+ "' where CPFPFWTRANSID='" + bankBean.getTransId()
							+ "' and  pensionno=" + pensionNo + "";
					log
							.info("CPFPTWAdvanceDAO::updateBankInfo()==update Query== For FS"
									+ updateQuery);
					updatedRecords = st.executeUpdate(updateQuery);

					updatePaymentQry1 = "update EMPLOYEE_ADVANCES_FORM set PARTYNAME='"
							+ bankBean.getPartyName()
							+ "' , PARTYADDRESS='"
							+ bankBean.getPartyAddress()
							+ "'   where  ADVANCETRANSID='"
							+ transId
							+ "' and pensionno='" + pensionNo + "'";
					log
							.info("CPFPTWAdvanceDAO::updateBankInfo()==update Query==For CPF/PFW"
									+ updatePaymentQry1);
					updatedRecords1 = st.executeUpdate(updatePaymentQry1);

				}
			} else {
				if (count != 0) {
					updatePaymentQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set PAYMENTINFO='N' where  NSSANCTIONNO='"
							+ transId + "' and pensionno='" + pensionNo + "'";
					updatedRecords = st.executeUpdate(updatePaymentQry);

					updatePaymentQry1 = "update EMPLOYEE_ADVANCES_FORM set PAYMENTINFO='N' where  ADVANCETRANSID='"
							+ transId + "' and pensionno='" + pensionNo + "'";
					updatedRecords1 = st.executeUpdate(updatePaymentQry1);
				}

			}
			log.info("CPFPTWAdvanceDAO::updateBankInfo()==updatePaymentQry==="
					+ updatePaymentQry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getSanctionOrderSequence(Connection con) {
		String nsSeqVal = "";
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "SELECT SANCTIONORDER_SEQ.NEXTVAL AS  SANCTIONORDERNO FROM DUAL";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				nsSeqVal = rs.getString("SANCTIONORDERNO");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return nsSeqVal;
	}

	public String getPFWSanctionOrderSequence(Connection con) {
		String nsSeqVal = "";
		Statement st = null;
		ResultSet rs = null;
		String sqlQuery = "SELECT PFW_SANCTIONORDER_SEQ.NEXTVAL AS  SANCTIONORDERNO FROM DUAL";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				nsSeqVal = rs.getString("SANCTIONORDERNO");
			}

		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(rs, st, null);
		}
		return nsSeqVal;
	}

	public String addNomineeBankDet(String pensionNo, String transId,
			EmpBankMaster bankMaster) {

		Connection con = null;
		Statement st = null;
		String updateQuery = "", updatePaymentQry = "", updatePaymentQry1 = "";
		int updatedRecords = 0, count = 0, totalRecords = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String insertBankMsterQry = "INSERT INTO EMPLOYEE_BANK_INFO(PENSIONNO,NAME,ADDRESS,MAILID,BANKNAME,BRANCHADDRESS,SAVINGBNKACCNO,NEFTRTGSCODE,MICRONO,PAYMENTMODE,CITY,CPFPFWTRANSID,PARTYNAME,PARTYADDRESS,NOMINEEID,USERNAME,LASTACTIVE)VALUES("
					+ pensionNo
					+ ",'"
					+ bankMaster.getBankempname()
					+ "','"
					+ bankMaster.getBankempaddrs()
					+ "','"
					+ bankMaster.getEmpmailid()
					+ "','"
					+ bankMaster.getBankname()
					+ "','"
					+ bankMaster.getBranchaddress()
					+ "','"
					+ bankMaster.getBanksavingaccno()
					+ "','"
					+ bankMaster.getBankemprtgsneftcode()
					+ "','"
					+ bankMaster.getBankempmicrono()
					+ "','"
					+ bankMaster.getBankpaymentmode()
					+ "','"
					+ bankMaster.getCity()
					+ "','"
					+ transId
					+ "','"
					+ bankMaster.getPartyName()
					+ "','"
					+ bankMaster.getPartyAddress()
					+ "','"
					+ bankMaster.getNomineeSerialNo()
					+ "','"
					+ this.userName
					+ "','" + commonUtil.getCurrentDate("dd-MMM-yyyy") + "')";
			log
					.info("CPFPTWAdvanceDAO::addNomineeBankDet()==insertBankMsterQry==="
							+ insertBankMsterQry);
			totalRecords = st.executeUpdate(insertBankMsterQry);
			updatePaymentQry = "update EMPLOYEE_ADVANCE_NOTEPARAM set PAYMENTINFO='Y' where  NSSANCTIONNO='"
					+ transId + "' and pensionno='" + pensionNo + "'";
			updatedRecords = st.executeUpdate(updatePaymentQry);

			log
					.info("CPFPTWAdvanceDAO::addNomineeBankDet()==updatePaymentQry==="
							+ updatePaymentQry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// New Method


	public ArrayList nomineeBankInfo(String pensionno,String transId) {
			int totalRecords = 0;
			Connection con = null;
			Statement st = null;
			String sqlQuery="";
			EmpBankMaster bankMaster = null;
			ArrayList nomineeList = new ArrayList();
			log.info("---Pensionno---"+pensionno+"--transid----"+transId);
			sqlQuery = "SELECT * FROM EMPLOYEE_BANK_INFO WHERE PENSIONNO="+ pensionno+" and CPFPFWTRANSID="+transId+" and NOMINEEID is not null";
			log.info("CPFPTWAdvanceDAO::nomineeBankInfo()" + sqlQuery); 
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				ResultSet rs  = st.executeQuery(sqlQuery);
				 
				while (rs.next()) {
					bankMaster = new EmpBankMaster();
					
					
					bankMaster.setBankempname(rs.getString("NAME"));				 
					bankMaster.setNomineeSerialNo(rs.getString("NOMINEEID"));
					//log.info("----nomineeBankInfo----"+bankMaster.getBankempname());
					if (rs.getString("ADDRESS") != null) {
						bankMaster.setBankempaddrs(rs.getString("ADDRESS"));
					}
					if (rs.getString("BANKNAME") != null) {
						bankMaster.setBankname(rs.getString("BANKNAME"));
					}
					if (rs.getString("BRANCHADDRESS") != null) {
						bankMaster.setBranchaddress(rs.getString("BRANCHADDRESS"));
					}
					if (rs.getString("SAVINGBNKACCNO") != null) {
						bankMaster.setBanksavingaccno(rs
								.getString("SAVINGBNKACCNO"));
					}
					if (rs.getString("NEFTRTGSCODE") != null) {
						bankMaster.setBankemprtgsneftcode(rs
								.getString("NEFTRTGSCODE"));
					}
					if (rs.getString("MICRONO") != null) {
						bankMaster.setBankempmicrono(rs.getString("MICRONO"));
					}
					if (rs.getString("MAILID") != null) {
						bankMaster.setEmpmailid(rs.getString("MAILID"));
					}
					if (rs.getString("PAYMENTMODE") != null) {
						bankMaster.setBankpaymentmode(rs.getString("PAYMENTMODE"));
					}
					
					if (rs.getString("CITY") != null) {
						bankMaster.setCity(rs.getString("CITY"));
					}
					 
					if (rs.getString("PARTYNAME") != null) {
						bankMaster.setPartyName(rs.getString("PARTYNAME"));
					}else{
						bankMaster.setPartyName("---");
					}
					if (rs.getString("PARTYADDRESS") != null) {
						bankMaster.setPartyAddress(rs.getString("PARTYADDRESS"));
					}else{
						bankMaster.setPartyAddress("---");
					}
					
					bankMaster.setChkBankInfo("Y");
					nomineeList.add(bankMaster);
				}
				log.info("nomineeList" + nomineeList.size());
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, con);
			}
			return nomineeList;
		}

	// New Method

	public int deleteNomineeBankDetails(String pensionNo, String nsSanctionNo,
			String nomineeSerialNo) {

		Connection con = null;
		Statement st = null;
		int n = 0;

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String delQuery = "delete from EMPLOYEE_BANK_INFO  where PENSIONNO='"
					+ pensionNo
					+ "' and  CPFPFWTRANSID='"
					+ nsSanctionNo
					+ "' and NOMINEEID=" + nomineeSerialNo;

			log.info("--deleteNomineeBankDetails()::delQuery---------"
					+ delQuery);
			n = st.executeUpdate(delQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;

	}

	public ArrayList getMultipleNomineeDets(AdvanceBasicReportBean basicBean,
			String empName, String netContAmt,Connection con) {
		StringBuffer buffer = new StringBuffer();
		
		AdvanceBasicBean advanceBasicBean = null;
		ArrayList multipleNomineeList = new ArrayList();
		
		Statement st = null;
		ResultSet rs = null;
		String nominneRelation = "", nomineeName = "", nomineeNames = "", totalShare = "", nominneRel = "";
		
		int k = 0, nomineeCount = 1;
		
		String totShare = "", nomineeNameStr = "", nomineeRelationStr = "",nomineeQuery="";
		String roundShare="",decimalVal="";
		boolean count = false;
		boolean countFlag = false;
		try {
			st = con.createStatement(); 
			 
			nomineeQuery="SELECT NOMINEE.NOMINEENAME AS NOMINEENAME,NOMINEE.NOMINEERELATION AS NOMINEERELATION, NOMINEE.TOTALSHARE  AS TOTALSHARE, BANK.NAME  AS ACCNAME, BANK.BANKNAME  AS BANKNAME, BANK.SAVINGBNKACCNO AS SAVINGBNKACCNO,BANK.NEFTRTGSCODE  AS NEFTRTGSCODE"
						  +" FROM (select SRNO,NOMINEENAME,NOMINEERELATION,TOTALSHARE,EMPFLAG,PENSIONNO from employee_nominee_dtls  where PENSIONNO='"+basicBean.getPensionNo()+"') NOMINEE,"
						  +" (select BANKNAME,SAVINGBNKACCNO, NEFTRTGSCODE, NAME, NOMINEEID, PENSIONNO,CPFPFWTRANSID  from employee_bank_info where PENSIONNO='"+basicBean.getPensionNo()+"' AND CPFPFWTRANSID = '"+basicBean.getNssanctionno()+"' ) BANK"
						  +" where NOMINEE.EMPFLAG = 'Y' and NOMINEE.pensionno = '"+basicBean.getPensionNo()+"'  and NOMINEE.SRNO=BANK.NOMINEEID(+) order by NOMINEE.SRNO desc";
			
			log.info("CPFPTWAdvanceDAO::getMultipleNomineeDets()===" + nomineeQuery);
			rs = st.executeQuery(nomineeQuery);
			
			while (rs.next()) {
				advanceBasicBean = new AdvanceBasicBean();
				
				if (rs.getString("NOMINEENAME") != null) {
					advanceBasicBean
					.setNomineename(rs.getString("NOMINEENAME"));
				}
				if (rs.getString("NOMINEERELATION") != null) { 
					advanceBasicBean.setNomineerelation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("NOMINEERELATION")));					 
				}
				
				if (!netContAmt.equals("")) {
					if (rs.getString("TOTALSHARE") != null) {
						
						double share = (Double.parseDouble(netContAmt) * Double
								.parseDouble(rs.getString("TOTALSHARE"))) / 100;						
						
						String decimals="";
							decimals=share+"";							
							int index1=decimals.indexOf(".");
							decimalVal=decimals.substring(decimals.indexOf(".")+1,decimals.length());
							 
					if(decimalVal.equals("0")){
						roundShare=decimals.substring(0,index1)+"/-";
						advanceBasicBean.setTotalshare(roundShare);						 
					}else{
						advanceBasicBean.setTotalshare(commonUtil
								.getDecimalCurrency(share));
					
					}
					
					log.info("------shar value----------"+advanceBasicBean.getTotalshare());
						/* log.info("With Math.Round================="+share); 
						double share1 = (Double.parseDouble(netContAmt) * Double
						 .parseDouble(rs.getString("TOTALSHARE"))) / 100;*/
						/*	log.info("With out Math.Round================"+share1);*/
					
					}
				}
					
					if (rs.getString("ACCNAME") != null) {
						advanceBasicBean
						.setBankempname(rs.getString("ACCNAME"));
					}else{
						advanceBasicBean
						.setBankempname("---");
					}
					if (rs.getString("BANKNAME") != null) {
						advanceBasicBean
						.setBankname(rs.getString("BANKNAME"));
					}else{
						advanceBasicBean
						.setBankname("---");
					}
					if (rs.getString("SAVINGBNKACCNO") != null) {
						advanceBasicBean
						.setBanksavingaccno(rs.getString("SAVINGBNKACCNO"));
					}else{
						advanceBasicBean
						.setBanksavingaccno("---");
					}
					if (rs.getString("NEFTRTGSCODE") != null) {
						advanceBasicBean
						.setBankemprtgsneftcode(rs.getString("NEFTRTGSCODE"));
					}else{
						advanceBasicBean
						.setBankemprtgsneftcode("---");
					}
					 
					multipleNomineeList.add(advanceBasicBean);
			}
			
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			  commonDB.closeConnection(null, st, rs);
		}
		return multipleNomineeList;
	}
public int deleteFinalSettlement(AdvanceBasicBean advanceBean) {
		
		Connection con = null;
		Statement st = null;
		int n = 0;
		String cpfpfwTransCD="",transType="";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String delQuery = "update employee_advance_noteparam set deleteflag='Y' where PENSIONNO='"
				+ advanceBean.getPensionNo() + "' and  NSSANCTIONNO='"+advanceBean.getNssanctionno()+"'";
			
			log.info("delQuery---------"+delQuery);
			n = st.executeUpdate(delQuery);
			
			if(advanceBean.getArreartype().equals("NON-ARREAR")){
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_FS_DELETE;
				transType="FS";
			}else{
				cpfpfwTransCD=Constants.APPLICATION_PROCESSING_FSA_DELETE;
				transType="FS Arrear";
			}
			 
			log.info("CPFPTWAdvanceDAO::deleteFinalSettlement()---Arrear Type--"+advanceBean.getArreartype()+"---cpfpfwTransCD----"+cpfpfwTransCD+"----transType--"+transType);
			
				CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advanceBean.getLoginUserId(),advanceBean.getLoginUserName(),advanceBean.getLoginUnitCode(),advanceBean.getLoginRegion(),advanceBean.getLoginUserDispName());			 			
				cpfInfo.deleteCPFPFWTrans(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),transType,cpfpfwTransCD,advanceBean.getVerifiedby());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
		
	}
public void addEmployeePostingDet(String pensionNo, String nsSanctionNo,
		String postingRegion,String postingStation) {

	Connection con = null;
	Statement st = null;
	String  updatePostingDetQry = "";
	int updatedRecords = 0, count = 0, totalRecords = 0;

	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		updatePostingDetQry = "UPDATE EMPLOYEE_ADVANCE_NOTEPARAM   SET  POSTINGFLAG='Y', POSTINGREGION='"+postingRegion+"' , POSTINGSTATION='"+postingStation+"' WHERE PENSIONNO="+pensionNo+" AND  NSSANCTIONNO='"+nsSanctionNo+"'" ;
				 
		log
				.info("CPFPTWAdvanceDAO::addEmployeePostingDet()==updatePostingDetQry==="
						+ updatePostingDetQry);
		totalRecords = st.executeUpdate(updatePostingDetQry);
		 
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		commonDB.closeConnection(null,st,con);
	} 
}
public void deleteEmployeePostingDetails(String pensionNo, String nsSanctionNo ) {

	Connection con = null;
	Statement st = null;
	String  updatePostingDetQry = "";
	int updatedRecords = 0, count = 0, totalRecords = 0;

	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		updatePostingDetQry = "UPDATE EMPLOYEE_ADVANCE_NOTEPARAM   SET  POSTINGFLAG='N', POSTINGREGION='' , POSTINGSTATION='' WHERE PENSIONNO="+pensionNo+" AND  NSSANCTIONNO='"+nsSanctionNo+"'" ;
				 
		log
				.info("CPFPTWAdvanceDAO::deleteEmployeePostingDetails()==updatePostingDetQry==="
						+ updatePostingDetQry);
		totalRecords = st.executeUpdate(updatePostingDetQry);
		 
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		commonDB.closeConnection(null,st,con);
	}
	 

}
private String getPFCardClosingBalance(String pensionno,String employeename){
	String pensionInfo="",toDate="",currentMonth="",currentYear="",nextYear="",fromDate="",currentDate="";
	currentDate = commonUtil.getCurrentDate("MM-yyyy");
	
	String currentDtInfo[] = currentDate.split("-");
	currentMonth = Integer.toString(Integer
			.parseInt(currentDtInfo[0]));
	if(Integer.parseInt(currentMonth)>=01 && Integer.parseInt(currentMonth)<=03){
		currentYear = Integer
		.toString(Integer.parseInt(currentDtInfo[1]) - 1);
		nextYear = currentDtInfo[1];
	}else{
		currentYear = currentDtInfo[1];
		nextYear = Integer
		.toString(Integer.parseInt(currentDtInfo[1]) + 1);
	}
	//log.info("getPFCardClosingBalance===============currentDate"+currentDate+"currentYear"+currentYear+"nextYear"+nextYear);
	fromDate = "01-Apr-" + currentYear;
	toDate = "31-Mar-" + nextYear;

	pensionInfo = financeReport.getPensionInfo(pensionno,
			employeename, fromDate, toDate);
	return pensionInfo;
}
public String getAdvanceApprovedDetails(String fromDate,String toDate,String userUnitCode,String region,String userProfile,String accountType){
	String queryCond="",query="",approvedBy="";
	Connection con = null;
	Statement st = null;
	ResultSet rs=null;
	
	if(userProfile.equals("C")){
		queryCond = " AND (userS.profile = 'C' and trans.transcd = '3')";
	}else if(userProfile.equals("R")){
		queryCond = "AND ((users.profile = 'R' and trans.transcd = '4') and units.region='"+region+"'and  upper(units.accounttype) = '"+accountType+"')";
	}else if(userProfile.equals("U")){
		queryCond = "AND ((users.profile = 'U' and trans.transcd = '4') and units.region='"+region+"'and  upper(units.accounttype) = '"+accountType+"' and  upper(units.unitcode) = '"+userUnitCode+"')";
	}
	query="select distinct TRANS.APPROVEDBY as APPROVEDBY from EPIS_USER  users,epis_advances_transactions trans,employee_unit_master units,EMPLOYEE_ADVANCES_FORM AF where af.advancetransid=trans.cpfpfwtransid and AF.APPROVEDDT between to_date('"+fromDate+"','dd-MM-yyyy') and to_date('"+toDate+"','dd-MM-yyyy') and units.unitcode = users.unitcd and USERS.USERID = trans.APPROVEDBY "+queryCond;
	log.info("getAdvanceApprovedDetails::query"+query);
	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		rs=st.executeQuery(query);
		while(rs.next()){
			if (approvedBy.equals("")){
				approvedBy=rs.getString("APPROVEDBY");
			}else{
				approvedBy=approvedBy+","+rs.getString("APPROVEDBY");
			}
			
		}
	
		 
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		commonDB.closeConnection(rs,st,con);
	}
	return approvedBy;
}
public ArrayList rejectedCPFReport(String reg, String fromDate, String toDate,
		String advanceType, String purposeType, String trust, String station,String userProfile, String userUnitCode, String userId,String accountType,String menuAccessFrom) {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	AdvanceBasicReportBean basicBean = null;
	ArrayList reportList = new ArrayList();
	ArrayList rejectedCPFList = new ArrayList();

	String region = "", currentDate = "", mon = "", year = "", pfid = "", transID = "", sortingOrder = "";
	long totalRequiredAmt = 0, totalActualCost = 0, totalSubContAmt = 0, totalMthInstallAmt = 0, totalIntInstallAmt = 0;
	double totalSanctionedAmt=0.0;	 
	sortingOrder = "PENSIONNO";
	 
	String sqlQuery = this.buildrejectedadvancesReportQuery(reg, fromDate, toDate,
			advanceType, purposeType, trust, station, sortingOrder, userProfile, userUnitCode, userId,accountType,menuAccessFrom);

	log.info("CPFPTWAdvanceDAO::rejectedCPFReport" + sqlQuery);

	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		rs = st.executeQuery(sqlQuery);
		while (rs.next()) {

			basicBean = new AdvanceBasicReportBean();
			if (rs.getString("CPFACNO") != null) {
				basicBean.setCpfaccno(rs.getString("CPFACNO"));
			} else {
				basicBean.setCpfaccno("---");
			}			 
			if (rs.getString("ADVANCETRANSID") != null) {
				transID = rs.getString("ADVANCETRANSID");
			}
			if (rs.getString("EMPLOYEENO") != null) {
				basicBean.setEmployeeNo(rs.getString("EMPLOYEENO"));
			} else {
				basicBean.setEmployeeNo("");
			}
			if (rs.getString("EMPLOYEENAME") != null) {
				basicBean.setEmployeeName(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("EMPLOYEENAME")));
			} else {
				basicBean.setEmployeeName("");
			}
			if (rs.getString("DESEGNATION") != null) {
				basicBean.setDesignation(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("DESEGNATION")));
			} else {
				basicBean.setDesignation("");
			}
			  
			if (rs.getString("AIRPORTCODE") != null) {
				basicBean.setAirportcd(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("AIRPORTCODE")));
			} else {
				basicBean.setAirportcd("---");
			}

			if (rs.getString("PURPOSETYPE") != null) {
				basicBean.setPurposeType(rs.getString("PURPOSETYPE"));
			}

			 
			if (rs.getString("REGION") != null) { 
					basicBean.setRegion(rs.getString("REGION")); 
			} else {
				basicBean.setRegion("");
			} 
			log.info("=====Region==="+basicBean.getRegion());
			if (rs.getString("DESEGNATION") != null) {
				basicBean.setDesignation(rs.getString("DESEGNATION"));
			} else {
				basicBean.setDesignation("---");
			}

			if (rs.getString("ADVANCETYPE") != null) {
				basicBean.setAdvanceType(rs.getString("ADVANCETYPE"));
			} else {
				basicBean.setAdvanceType("---");
			}
			if (rs.getString("CBREMARKS") != null) {
				basicBean.setCBRemarks(rs.getString("CBREMARKS"));
			} else {
				basicBean.setCBRemarks("---");
			}
			pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
					CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
							"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
			basicBean.setPfid(pfid);

			fromDate = "";
			rejectedCPFList.add(basicBean);
		}

	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(null, st, con);
	}
	return rejectedCPFList;

}

public String buildrejectedadvancesReportQuery(String reg, String fromDate,
		String toDate, String advanceType, String purposeType,
		String trust, String station, String sortingOrder,String userProfile, String userUnitCode, String userId,String accountType,String menuAccesFrom) {
	log
			.info("CPFPTWAdvanceDAO::buildrejectedadvancesReportQuery-- Entering Method");
	StringBuffer whereClause = new StringBuffer();
	StringBuffer query = new StringBuffer();
	String dynamicQuery = "", sqlQuery = "", orderBy = "";
	 
	 
	
	sqlQuery = "SELECT PI.CPFACNO AS CPFACNO,PI.EMPLOYEENO AS EMPLOYEENO,PI.DATEOFBIRTH AS DATEOFBIRTH,PI.PENSIONNO AS PENSIONNO,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS AIRPORTCODE,PI.REGION AS REGION,"
			+ "AF.ADVANCETYPE AS ADVANCETYPE,AF.ADVANCETRANSID AS ADVANCETRANSID,AF.ADVANCETRANSDT AS ADVANCETRANSDT,AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,  AF.CBREMARKS AS CBREMARKS"
			+ " FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCES_FORM AF    WHERE  AF.PENSIONNO = PI.PENSIONNO  and   AF.DELETEFLAG='N' AND AF.FINALTRANSSTATUS='R'";

	if (!advanceType.equals("NO-SELECT")) {
		whereClause.append(" AF.ADVANCETYPE='" + advanceType + "'");
		whereClause.append(" AND ");
	}

	if (!reg.equals("NO-SELECT")) {
		whereClause.append(" PI.REGION='" + reg + "'");
		whereClause.append(" AND ");
	}

	if (!station.equals("NO-SELECT")) {
		whereClause.append(" PI.AIRPORTCODE='" + station + "'");
		whereClause.append(" AND ");
	}

	if (!purposeType.equals("NO-SELECT")) {
		whereClause.append(" AF.PURPOSETYPE='" + purposeType.toUpperCase()
				+ "'");
		whereClause.append(" AND ");
	}
	
	if (!trust.equals("NO-SELECT")) {
		whereClause.append(" AF.TRUST='" + trust + "'");
		whereClause.append(" AND ");
	}
 
	 
	query.append(sqlQuery);

	if (reg.equals("NO-SELECT") && advanceType.equals("NO-SELECT")
			&& station.equals("NO-SELECT")  && trust.equals("NO-SELECT") 
			&& fromDate.equals("") && toDate.equals("")) {

	} else {
		query.append(" AND ");
		query.append(this.sTokenFormat(whereClause));
	}

	orderBy = "ORDER BY AF." + sortingOrder;
	query.append(orderBy);
	dynamicQuery = query.toString(); 
	 
		log.info("CPFPTWAdvanceDAO::buildrejectedadvancesReportQuery Leaving Method"+dynamicQuery);
return dynamicQuery;
	 
}
public String generateJasperReportForSO(Connection con,String nsSanctionno, AdvanceSearchBean advanceBean){
	
	ArrayList reportList = new ArrayList(); 
	Map map=new HashMap();
	LetterFormates formate=new LetterFormates(); 
	reportList = this.getSODataForJasperGeneration(con,nsSanctionno,advanceBean);
	int j=0;
	String jasperFileName="",message="",path="",reportPath="";
	AdvanceBasicReportBean bean = new AdvanceBasicReportBean();
	ResourceBundle bundle = ResourceBundle.getBundle("com.epis.resource.Configurations");
	String uploadFilePath = bundle.getString("FSSanctionOrder.folder.path");
	File filePath = new File(uploadFilePath);
	if (!filePath.exists()) {
		filePath.mkdirs();
	}
 try{
	for(j=0;j<reportList.size();j++){
		bean = (AdvanceBasicReportBean)reportList.get(j);
		log.info("==reportList Elements"+bean.getPensionNo()+bean.getEmployeeName()+bean.getSanctionOrderNo());
		
	}
log.info("==in generateJasperReportForSO PC Value"+bean.getPensioncontribution());
if(!bean.getSeperationreason().equals("Death")){
	if(!bean.getPensioncontribution().equals("0")){
		jasperFileName = "FSSanctionOrderWitPCLable.jasper";
	}else{
		jasperFileName= "FSSanctionOrderWitOutPCLable.jasper";
	}
}/*else{
	if(!bean.getPensioncontribution().equals("0")){
		jasperFileName = "FSDeathSanctionOrderWitPCLable.jasper";
	}else{
		jasperFileName= "FSSanctionDeathOrderWitOutPCLable.jasper";
	}
}*/
JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportList);
	String file=advanceBean.getDirPath()+"/soreports/"+jasperFileName;
	log.info("==file==="+file);
	
	//JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
    JasperPrint jp1 = JasperFillManager.fillReport(file, map, ds);
    
    List pages = new ArrayList(jp1.getPages());
	
	int i=1;
	for(int count=1;count<pages.size()-1;count++){
	
	jp1.addPage(i, (JRPrintPage)pages.get(count-1));
	i++;
	}
	String saveFileName=bean.getPensionNo()+"_"+bean.getSanctionOrderNo()+"_"+bean.getSanctiondt();
	  path= uploadFilePath+"/"+saveFileName;
	log.info("the path is...."+path);
	//formates.generateWordFile(jp1, path);
	formate.genPdfFile(jp1, path); 
	reportPath=path+".pdf";
/*	
	String s1="EMPReport";
	 JasperReport jasperreport = getCompiledReport(s1);
	this.generatePDFOutput(res, map,jasperreport);
	*/
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
		message ="Report is not Generated Due to System Problem";
	}
	 
return reportPath;

}
public ArrayList getSODataForJasperGeneration(Connection con,String nssanctionNo,AdvanceSearchBean basicBean){
	Statement st=null;
	ResultSet rs = null;
	List list=new ArrayList();
	Map map=new HashMap();
	AdvanceBasicReportBean reportBean=null;
	LetterFormates formate=new LetterFormates(); 
	String sqlQuery="" ; 
	  
	String seperationReason = "", netContAmt = "",soremarks="",imgagePath="",pensionNo="";
	 
	ArrayList reportList = new ArrayList(); 
		ArrayList sanctionOrderList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();
		 
		String region = "", nomineeNm = "", amtadmtdt = "",seperationRemarks="", nomineeCnt = "", seperationdt = "", sanctiondt = "", regionLabel = "", paymentDt = "", empName = "",remarks="",dateOfBirth="",pfid="",arrearDt="",nomineeAppointed="",paymentFalg="",sanctionOrderNo="",reasonForResignation="",sanctionOrderFlag="",pensioncontribution="",rateOfInterest="";
		    
		 
		  sqlQuery = "SELECT PI.PENSIONNO AS PENSIONNO, PI.CPFACNO AS CPFACNO,PI.EMPLOYEENAME AS EMPLOYEENAME,PI.DESEGNATION AS DESEGNATION,PI.AIRPORTCODE AS AIRPORTCODE,PI.REGION AS REGION,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH, "
			+ "EN.AMTADMITTEDDT,EN.SEPERATIONRESAON,EN.SOREMARKS,EN.SEPERATIONDT,EN.EMPSHARESUBSCRIPITION,EN.EMPSHARECONTRIBUTION,EN.LESSCONTRIBUTION,EN.NETCONTRIBUTION,EN.ADHOCAMOUNT,EN.NSSANCTIONEDDT,EN.PAYMENTDT AS PAYMENTDT,EN.SEPERATIONREMARKS AS SEPERATIONREMARKS,EN.REMARKS AS REMARKS,EN.ARREARDATE AS ARREARDATE,EN.POSTINGFLAG AS POSTINGFLAG,EN.POSTINGREGION AS POSTINGREGION,EN.POSTINGSTATION AS POSTINGSTATION,EN.NOMINEEAPPOINTED AS NOMINEEAPPOINTED,EN.PAYMENTINFO AS PAYMENTINFO,EN.SONO AS SANCTIONORDERNO,EN.REASONFORRESIGNATION AS REASONFORRESIGNATION,EN.SOFLAG AS SOFLAG,  "
			+ " EN.RATEOFINTEREST AS RATEOFINTEREST  FROM EMPLOYEE_PERSONAL_INFO PI, EMPLOYEE_ADVANCE_NOTEPARAM EN  WHERE EN.PENSIONNO = PI.PENSIONNO AND EN.NSSANCTIONNO=" + nssanctionNo;
		
		log.info("CPFPTWAdvanceDAO::getSODataForJasperGeneration" + sqlQuery);
		
		try {
			 
			st = con.createStatement(); 
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				
				reportBean= new AdvanceBasicReportBean();
				if (rs.getString("PENSIONNO") != null) {
					reportBean.setPensionNo(rs.getString("PENSIONNO"));
					pensionNo = rs.getString("PENSIONNO");
				} else {
					reportBean.setPensionNo("---");
				}
				if (rs.getString("CPFACNO") != null) {
					reportBean.setCpfaccno(rs.getString("CPFACNO"));
				} else {
					reportBean.setCpfaccno("---");
				}
				if (rs.getString("ADHOCAMOUNT") != null) {
					reportBean.setAdhocamt(commonUtil.getCurrency(rs
							.getDouble("ADHOCAMOUNT")));
				} else {
					reportBean.setAdhocamt("---");
				}
				if (rs.getString("EMPLOYEENAME") != null) {
					empName = rs.getString("EMPLOYEENAME");
					reportBean.setEmployeeName(empName);
					
				} else {
					reportBean.setEmployeeName("");
				}
				if(rs.getString("SOREMARKS")!=null){									
					
					soremarks=rs.getString("SOREMARKS");		
					
				} 
				if (rs.getString("SEPERATIONRESAON") != null) {
					seperationReason = rs.getString("SEPERATIONRESAON");
				} else {
					seperationReason = "---";
				}
				log.info("seperationReason" + seperationReason.trim());
				reportBean.setSeperationreason(seperationReason.trim());
				if (rs.getString("DESEGNATION") != null) {
					reportBean.setDesignation(rs.getString("DESEGNATION"));
				} else {
					reportBean.setDesignation("");
				}
				if (rs.getString("PAYMENTDT") != null) {
					paymentDt = CommonUtil.getDatetoString(rs
							.getDate("PAYMENTDT"), "dd-MM-yy");
				}
				
				if (rs.getString("PAYMENTINFO") != null) {
					paymentFalg=rs.getString("PAYMENTINFO");
				}  
				 
				if (rs.getString("SEPERATIONREMARKS") != null) {
					seperationRemarks = rs.getString("SEPERATIONREMARKS");
				}
				if(rs.getString("REMARKS")!=null){									
					//remarks=commonDAO.loadRemarks(rs.getString("REMARKS"),",");
					remarks=rs.getString("REMARKS");		
					
				} 
				
				if(rs.getString("POSTINGFLAG")!=null)
				{
					if(rs.getString("POSTINGFLAG").equals("Y"))
					{
						
						if (rs.getString("POSTINGSTATION") != null) {
							
							if ((rs.getString("POSTINGSTATION").equals("C.S.I Airport"))
									|| (rs.getString("POSTINGSTATION").equals("CSIA IAD"))
									|| (rs.getString("POSTINGSTATION").equals("RAUSAP"))
									|| (rs.getString("POSTINGSTATION").equals("EMO"))
									|| (rs.getString("POSTINGSTATION").equals("CRSD"))
									|| (rs.getString("POSTINGSTATION").equals("DRCDU"))) {
								
								reportBean.setTblairportcd(rs.getString("POSTINGSTATION"));
							} else if (rs.getString("POSTINGSTATION").equals("DELHI")) {
								reportBean.setTblairportcd("New Delhi");
							} else {
								reportBean.setTblairportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("POSTINGSTATION")));
							}
							
						} else {
							reportBean.setTblairportcd("---");
						}
						
						if (rs.getString("AIRPORTCODE") != null) {
							
							if ((rs.getString("AIRPORTCODE").equals("C.S.I Airport"))
									|| (rs.getString("AIRPORTCODE").equals("CSIA IAD"))
									|| (rs.getString("AIRPORTCODE").equals("RAUSAP"))
									|| (rs.getString("AIRPORTCODE").equals("EMO"))
									|| (rs.getString("AIRPORTCODE").equals("CRSD"))
									|| (rs.getString("AIRPORTCODE").equals("DRCDU"))) {
								
								reportBean.setAirportcd(rs.getString("AIRPORTCODE"));
							} else if (rs.getString("AIRPORTCODE").equals("DELHI")) {
								reportBean.setAirportcd("New Delhi");
							} else {
								reportBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("AIRPORTCODE")));
							}
							
						} else {
							reportBean.setAirportcd("---");
						}
						
						if (rs.getString("POSTINGREGION") != null) {
							region = commonUtil.getRegion(rs.getString("POSTINGREGION"));
						} else {
							region = "---";
						}
						
						
					}else{
						if (rs.getString("AIRPORTCODE") != null) {
							
							if ((rs.getString("AIRPORTCODE").equals("C.S.I Airport"))
									|| (rs.getString("AIRPORTCODE").equals("CSIA IAD"))
									|| (rs.getString("AIRPORTCODE").equals("RAUSAP"))
									|| (rs.getString("AIRPORTCODE").equals("EMO"))
									|| (rs.getString("AIRPORTCODE").equals("CRSD"))
									|| (rs.getString("AIRPORTCODE").equals("DRCDU"))) {
								
								reportBean.setAirportcd(rs.getString("AIRPORTCODE"));
							} else if (rs.getString("AIRPORTCODE").equals("DELHI")) {
								reportBean.setAirportcd("New Delhi");
							} else {
								reportBean.setAirportcd(commonUtil
										.capitalizeFirstLettersTokenizer(rs
												.getString("AIRPORTCODE")));
							}
							
							//  reportBean.setTblairportcd(rs.getString("AIRPORTCODE"));
							
							reportBean.setTblairportcd(reportBean.getAirportcd());
							
						} else {
							reportBean.setAirportcd("---");
							reportBean.setTblairportcd("---");
							
						}
						
						if (rs.getString("REGION") != null) {
							region = commonUtil.getRegion(rs.getString("REGION"));
						} else {
							region = "---";
						}
					}
				}
				
				
				
				if (rs.getString("GENDER") != null) {
					reportBean.setGender(rs.getString("GENDER"));
				} else {
					reportBean.setGender("");
				}
				
				if (rs.getString("DATEOFBIRTH") != null) {
					reportBean.setDateOfBirth(rs.getString("DATEOFBIRTH"));
					dateOfBirth = commonUtil.converDBToAppFormat(rs
							.getDate("DATEOFBIRTH"));
				} else {
					reportBean.setDateOfBirth("");
				}
				
				if (rs.getString("AMTADMITTEDDT") != null) {
					amtadmtdt = CommonUtil.getDatetoString(rs
							.getDate("AMTADMITTEDDT"), "dd-MM-yyyy");
				} else {
					amtadmtdt = "---";
				}
				
				if (rs.getString("SEPERATIONDT") != null) {
					seperationdt = CommonUtil.getDatetoString(rs
							.getDate("SEPERATIONDT"), "dd-MM-yyyy");
				} else {
					seperationdt = "---";
				}
				
				if (rs.getString("EMPSHARESUBSCRIPITION") != null) {
					reportBean.setEmplshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARESUBSCRIPITION")));
				} else {
					reportBean.setEmplshare("---");
				}
				
				if (rs.getString("EMPSHARECONTRIBUTION") != null) {
					reportBean.setEmplrshare(commonUtil.getCurrency(rs
							.getDouble("EMPSHARECONTRIBUTION")));
				} else {
					reportBean.setEmplrshare("---");
				}
				
				if (rs.getString("LESSCONTRIBUTION") != null) {
					pensioncontribution=rs.getString("LESSCONTRIBUTION");
					reportBean.setPensioncontribution(commonUtil.getCurrency(rs
							.getDouble("LESSCONTRIBUTION")));
				} else {
					reportBean.setPensioncontribution("---");
					pensioncontribution="---";
				}
				
				if (rs.getString("NETCONTRIBUTION") != null) {
					reportBean.setNetcontribution(commonUtil.getCurrency(rs
							.getDouble("NETCONTRIBUTION")));
					
					netContAmt = rs.getString("NETCONTRIBUTION");
				} else {
					reportBean.setNetcontribution("---");
				}
				
				if (rs.getString("NSSANCTIONEDDT") != null) {
					sanctiondt = CommonUtil.getDatetoString(rs
							.getDate("NSSANCTIONEDDT"), "dd-MM-yy");
				} else {
					sanctiondt = "---";
				}
				
				if (rs.getString("SANCTIONORDERNO") != null) {
					sanctionOrderNo =commonUtil.leadingZeros(5, rs.getString("SANCTIONORDERNO"));
				} else {
					sanctionOrderNo = "---";
				}
				if (rs.getString("SOFLAG") != null) {
					sanctionOrderFlag =rs.getString("SOFLAG");
				} else {
					sanctionOrderFlag = "Before";
				} 
				
				if (rs.getString("REASONFORRESIGNATION") != null) {
					reasonForResignation = rs.getString("REASONFORRESIGNATION");
				} else {
					reasonForResignation = "---";
				}
				
				if (rs.getString("ARREARDATE") != null) {					
					arrearDt=CommonUtil.getDatetoString(rs.getDate("ARREARDATE"), "dd/MM/yyyy");
				} else {
					reportBean.setArreardate("---");
				}
				
				if (rs.getString("RATEOFINTEREST") != null) {
					rateOfInterest=rs.getString("RATEOFINTEREST");
				} else {
					rateOfInterest = "0";
				}
				if(rs.getString("NOMINEEAPPOINTED")!=null){									
					nomineeAppointed=rs.getString("NOMINEEAPPOINTED");
					
				}else{
					nomineeAppointed="N";
					
				} 
				reportBean.setNomineeAppointed(nomineeAppointed);
				reportBean.setPensionNo(pensionNo);
				reportBean.setNssanctionno(nssanctionNo);
				reportBean.setSeperationdate(seperationdt);
				 
				
			pfid = commonDAO.getPFID(reportBean.getEmployeeName().toUpperCase(),
						dateOfBirth, commonUtil.leadingZeros(5,pensionNo));
				reportBean.setPfid(pfid);
				
			log.info("******----PF ID IN DAO------*****"+reportBean.getPfid());
				
			 String nomineeName = this
				.getNomineeDet(reportBean, empName, con);
				log.info("=========nomineeName in DAO========" + nomineeName);
				log.info("=========nomineeName length() in DAO========"
						+ nomineeName.length());
				
				if (nomineeName.length() != 0) {
					nomineeNm = nomineeName.substring(0,
							nomineeName.length() - 1);
					log.info("=========nomineeNm in DAO========" + nomineeNm);
					nomineeCnt = nomineeName.substring(
							nomineeName.length() - 1, nomineeName.length());
					log.info("=========nomineeCnt in DAO========" + nomineeCnt);
				}
				
				if (seperationReason.equals("Death")) {
					nomineeList = this.getNomineeDets(reportBean, empName,
							netContAmt, con);
				}
				
				if (seperationReason.equals("Death")) {
					multipleNomineeList = this.getMultipleNomineeDets(reportBean, empName,
							netContAmt, con);
				} 
				
				/*AdvanceBasicReportBean reportBean= new AdvanceBasicReportBean();
				AdvancereportBeanadvancereportBean= new AdvancereportBean();
				*/
				reportBean.setRegion(region);
				regionLabel= commonUtil.getRegionLbls(region);
				reportBean.setRegionLbl(regionLabel);
				reportBean.setSeperationreason(seperationReason.trim());
				reportBean.setAmtadmtdate(amtadmtdt);
				reportBean.setSeperationdate(seperationdt);
				reportBean.setSanctiondt(sanctiondt);
				reportBean.setSanctionOrderNo(sanctionOrderNo);
				reportBean.setSanctionList(sanctionOrderList);
				reportBean.setPaymentdate(paymentDt);
				reportBean.setApprovedremarks(seperationRemarks);
				reportBean.setRemarks(remarks);
				reportBean.setSoremarks(soremarks);
				reportBean.setArreardate(arrearDt);
				reportBean.setNomineeAppointed(nomineeAppointed);
				reportBean.setResignationreason(reasonForResignation);
				reportBean.setSanctionOrderFlag(sanctionOrderFlag);
				reportBean.setRateOfInterest(rateOfInterest);
				
				
				if (seperationReason.equals("Death")) {
					reportBean.setNomineeList(nomineeList);
					reportBean.setMultipleNomineeList(multipleNomineeList);
					reportBean.setEmployeeName("Late "+empName);
				}
				
				if ((!nomineeNm.equals("")) && (seperationReason.equals("Death"))) {
					reportBean.setNomineename(nomineeNm);
				} else {
					reportBean.setNomineename(nomineeNm);
				}
				
				if ((!nomineeCnt.equals("")) && (seperationReason.equals("Death"))) {
					reportBean.setNomineecount(nomineeCnt);
				} else {
					reportBean.setNomineecount(nomineeCnt);
				}
				
				
				
				
				EmpBankMaster empBean= null;
				empBean = loadEmployeeBankInfo(  pensionNo,   nssanctionNo);
				reportBean.setBankempname(empBean.getBankempname());
				reportBean.setBankname(empBean.getBankname());
				reportBean.setBanksavingaccno(empBean.getBanksavingaccno());
				reportBean.setBankemprtgsneftcode(empBean.getBankemprtgsneftcode());
				
				
				int previousYear = 0, currentMonth = 0, nextYear = 0;
				String  currentYear = "", currentYear1 = "", previousYear2 = "", previousYear1 = "",finyear="";
				
				currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
				currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
				currentYear1 = commonUtil.getCurrentDate("yyyy").substring(0, 4);					 
				nextYear = Integer.parseInt(currentYear) + 1;
				previousYear = Integer.parseInt(currentYear1) - 1;
				previousYear2 = Integer.toString(previousYear);
				
				if (previousYear <= 9)
					previousYear1 = Integer.toString(previousYear);
				
				if (currentMonth >= 04)
					finyear = currentYear1 + "-" + nextYear ;
				else if (previousYear <= 9)
					finyear = previousYear1 + "-" + currentYear;
				else
					finyear = previousYear2 + "-" + currentYear;
				
				reportBean.setFinyear(finyear); 
				String SOPara1_Cond="",SOPara1="",SORefType="",SO_As_Per_CHQ="",SOPara2_Cond="",SO_No_Payment="",SO_Interest_for="",SO_Note_Apprd_Remarks="",SO_airportCd="",payMntInfo="",SO_Death_Notes="" ;
				
				if(reportBean.getTblairportcd().equals("Office Complex")){
					SO_airportCd = "CHQ";
				}else if(reportBean.getTblairportcd().equals("CSIA IAD")){
					SO_airportCd = "Mumbai Airport";
				}else if(reportBean.getTblairportcd().equals("Igi Iad")){
					SO_airportCd = "IGI Airport New Delhi";
				}else if(reportBean.getTblairportcd().equals("Igicargo Iad")){
					SO_airportCd = "IGI Airport Cargo, New Delhi";
				}else if(reportBean.getTblairportcd().equals("Chennai Iad")){
					SO_airportCd = "Chennai";
				}else if(reportBean.getTblairportcd().equals("Cap Iad")){
					SO_airportCd = "Chennai Project";
				}else if(reportBean.getTblairportcd().equals("Safdarjung")){
					SO_airportCd = "Safdarjung Airport";
				}else{
					SO_airportCd = reportBean.getAirportcd();
				}
				reportBean.setSO_airportCd(SO_airportCd);
								if(reportBean.getSeperationreason().equals("Resignation")){
									SOPara1_Cond ="resigned";
								}else if(reportBean.getSeperationreason().equals("Retirement")){
									SOPara1_Cond ="retiring/retired";
								}else if(reportBean.getSeperationreason().equals("VRS")){
									SOPara1_Cond ="voluntary retiring/retired";
								}else if(reportBean.getSeperationreason().equals("Termination")){
									SOPara1_Cond ="terminated";
								}
				 
			 SOPara1= "											Sanction of the president AAI EPF Trust is hereby conveyed for the final payment"
						 + " of CPF dues in r/o of the following employee "+SOPara1_Cond +" on "+reportBean.getSeperationdate()+".  The detail of CPF dues is as Follows:-";

			 reportBean.setSOPara1(SOPara1);
				log.info("=========SOPara1========" + SOPara1);
			 if(reportBean.getRegion().equals("CHQIAD")){
			 SORefType =" Ref. No. FA/CPF/FINAL PAYMENT/"+finyear;
			 }else{
				 SORefType =" Ref. No. AAI/CPF/FP/"+reportBean.getRegionLbl();
			 }
			 reportBean.setSORefType(SORefType);
			 log.info("=========SORefType========" + SORefType);
			  
			 if(reportBean.getSeperationreason().equals("Death")){
				 SOPara2_Cond ="nominees";
				}else{
				 SOPara2_Cond ="employee";
				 }
			 
			 SO_As_Per_CHQ = "												As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT"
			 		+ " in the above  mentioned Bank Account Number as per Bank details furnished by the "+SOPara2_Cond; 
		 
			 	
			 	 log.info("=========SO_As_Per========" + SO_As_Per_CHQ);
			 	SO_No_Payment = "No payment to be released by region/station against this sanction order.";
			 	
			 	 log.info("=========SO_No_Payment========" + SO_No_Payment);
			 
			 	 if(!reportBean.getSeperationreason().equals("Resignation")){
			 		 if(empBean!=null){
			 			reportBean.setSO_As_Per_CHQ(SO_As_Per_CHQ);
					 	reportBean.setSO_No_Payment(SO_No_Payment);
			 		 }
			 		 
			 	 }
			 	 
			 	 if(reportBean.getSeperationreason().equals("Resignation")){				 		  
			 			reportBean.setSO_As_Per_CHQ(SO_As_Per_CHQ);
					 	reportBean.setSO_No_Payment(SO_No_Payment);
			 		 
			 	 }
			 	
			 	 SO_Interest_for = "Interest for the financial year "+reportBean.getFinyear()+" has been calculated @ "+reportBean.getRateOfInterest()+"% provisionally.";
			 	
			 	 log.info("=========SO_Interest_for========" + SO_Interest_for);
			 	 
			 	 
			 					 				 
			 	reportBean.setSO_Interest_for(SO_Interest_for);
			 	 
			 	 if(!reportBean.getApprovedremarks().equals("")){
			 		SO_Note_Apprd_Remarks = "Note: "+reportBean.getApprovedremarks();
			 	}
			 	reportBean.setSO_Note_Apprd_Remarks(SO_Note_Apprd_Remarks);
			 	
			 	 log.info("=========SO_Note_Apprd_Remarks========" + SO_Note_Apprd_Remarks);
			 	 
			 	 if(!reportBean.getSeperationreason().equals("Resignation")){		
			 		 if(!reportBean.getPaymentdate().equals("")){
			 			 payMntInfo = "Note: Payable on or after "+reportBean.getPaymentdate()+".";
			 		 }
			 	 } 
			 	reportBean.setPayMntInfo(payMntInfo);
			 	
			 	if(reportBean.getSeperationreason().equals("Death")){
			 		if(reportBean.getNomineecount().equals("1")){
			 			SO_Death_Notes="NOTE:- Net Amount payable for Rs."+reportBean.getNetcontribution()+"/- will be released by CHQ through RTGS/NEFT "+reportBean.getNomineename();
			 		}else{
			 			
			 		}
			 	}
			 	 

				reportBean.setSO_Death_Notes(SO_Death_Notes);
			 	
			}
			
			

			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(basicBean.getLoginUserId(),
					basicBean.getLoginUserName(), basicBean.getLoginUnitCode(),
					basicBean.getLoginRegion(), basicBean.getLoginUserDispName());
		 
			
			ArrayList  transList = new ArrayList();
			imgagePath = basicBean.getImgagePath();
			transList = (ArrayList) cpfInfo
			.readTransInfo(
					pensionNo,
					nssanctionNo,
					Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR,
					imgagePath);
			
			log.info("=======imalge Path ========"+imgagePath);
			CPFPFWTransBean transBean = new CPFPFWTransBean();
			for(int i=0;i<transList.size();i++){
			transBean = (CPFPFWTransBean)transList.get(i);
			if(transBean.getTransCode().equals("23")){
				reportBean.setTransDigitalSign(imgagePath+transBean.getTransDigitalSign());
				reportBean.setTransDispSignName(transBean.getTransDispSignName());
				reportBean.setLoginUserDesignation(transBean.getDesignation());
			}
			}
			
			if(transList.size()==0){
				reportBean.setTransDispSignName("(A.H. WADHVA )");
				reportBean.setLoginUserDesignation("Sr. Manager (Finance)");
			}
			reportBean.setFS_SO_Trust(Constants.APPLICATION_FINAL_SETTLEMENT_SANCTION_ORDER_TRUST);
			
			//For CC
			 String CC_Lines="",headQuaters="",reg="";
      	       
			 CommonUtil commonUtil=new CommonUtil();
      	       System.out.println("----reg----"+reg);
      	       String reg1=String.valueOf(reg);
      	       try{
      	         headQuaters=commonUtil.getRegionHeadQuarters(reportBean.getRegion());
      	         System.out.println("----headQuaters----"+headQuaters);
      	       }catch(Exception e){
      	         
      	       }
      	      
      	         
      	         if(reportBean.getRegion().equals("CHQNAD")){
      	        	 if(reportBean.getAirportcd().equals("Chqnad")){
      	        		CC_Lines="Sh. "+reportBean.getEmployeeName()+","+reportBean.getDesignation(); 
      	        	 }
      	        	 
      	         }
      	          
      	       if(!reportBean.getRegion().equals("CHQNAD")){
      	    	 if(!reportBean.getAirportcd().equals("Chqnad")){
      	    		 if(!reportBean.getAirportcd().equals("Thiruvananthapuram Airport")){	      	    			 
      	    				if(!reportBean.getAirportcd().equals("Kolkata")){
      	    					if(!reportBean.getAirportcd().equals("Kolkata Proj")){
      	    						if(!reportBean.getAirportcd().equals("Chennai Iad")){
      	    							if(!reportBean.getAirportcd().equals("Cap Iad")){
      	    								if(!reportBean.getAirportcd().equals("CSIA IAD")){
      	    									if(!reportBean.getAirportcd().equals("Igi Iad")){
      	    										if(!reportBean.getAirportcd().equals("Igicargo Iad")){
      	    											if(!reportBean.getAirportcd().equals("Operational Office")){
      	    												if(!reportBean.getAirportcd().equals("Office Complex")){
      	    													if(!reportBean.getRegion().equals("North-East Region")){
      	    														if(!reportBean.getRegion().equals("RAUSAP")){
      	    															CC_Lines="The General Manager (F&A), AAI, "+reportBean.getRegion()+",";
      	    															 if(headQuaters.equals("Mumbai")){
      	    																CC_Lines = CC_Lines + " C.S.I";																				 
      	    															 }else if(headQuaters.equals("New Delhi")){ 
      	    																CC_Lines = CC_Lines + " Operational Office, ";
      	    															 } else{
      	    																CC_Lines = CC_Lines +   headQuaters;
      	    															 }
      	    															if(!headQuaters.equals("New Delhi")){ 
      	    																CC_Lines = CC_Lines + "Airport ,";
      	    																 }  
      	    														}
      	    														CC_Lines = CC_Lines +headQuaters+"\n";
      	    													}
      	    													if(reportBean.getRegion().equals("North-East Region")){
      	    														CC_Lines = "The General Manager (F&A), AAI, "+reportBean.getRegion()+", ";
      	    														 if(headQuaters.equals("Mumbai")){
      	    															CC_Lines = CC_Lines +" C.S.I"; 																 
      	    												       		 }else if(headQuaters.equals("New Delhi")){
      	    												       		CC_Lines = CC_Lines +" Operational Office , ";
      	    																 
      	    												       		 }else{ 															
      	    												       		CC_Lines = CC_Lines +headQuaters; 												 
      	    												       		 }
      	    												       		 if(!headQuaters.equals("New Delhi")){
      	    												       		CC_Lines = CC_Lines +" Airport , ";
      	    																 } 
      	    												       	CC_Lines = CC_Lines +headQuaters;	 
      	    													}
      	    													//station name insted od airportcode
      	    													if(reportBean.getRegion().equals("RAUSAP")){
      	    														CC_Lines = "The Joint General Manager (F&A), AAI, RAU Safdarjung Airport, New Delhi"
      	    															       +"\n"+"The OIC, AAI, "+reportBean.getAirportcd()+", Safdarjung Airport, New Delhi"+"\n";
      	    													}
      	    													
      	    													if(!reportBean.getAirportcd().equals(headQuaters)){
      	    														if(!reportBean.getRegion().equals("RAUSAP")){
      	    															if(!reportBean.getRegion().equals("Northern Region")){
      	    																CC_Lines = CC_Lines + "The OIC, AAI, "+reportBean.getAirportcd()+" Airport, "+reportBean.getAirportcd();
      	    																 
      	    															}
      	    															if(reportBean.getRegion().equals("Northern Region")){
      	    																CC_Lines =CC_Lines + "The OIC, AAI, "+reportBean.getAirportcd()+" Airport, ";
      	    																if(!reportBean.getAirportcd().equals("Safdarjung")){
      	    																	CC_Lines = CC_Lines+reportBean.getAirportcd();
      	    																} 
      	    																if(reportBean.getAirportcd().equals("Safdarjung")){
      	    																	CC_Lines = CC_Lines+"New Delhi";
      	    																}
      	    																 
      	    															}
      	    														}
      	    													}
      	    													
      	    												}
      	    											}
      	    											
      	    										}
      	    											
      	    										}
      	    									}
      	    								}
      	    							}
      	    								
      	    					}
      	    				}
      	    					
      	    				}
      	    		  
      	    	 }
      	    	          
      	       } 
      	       
      	       
      	     String str="";
	         String[] strarr=new String[2]; 
	     	if(reportBean.getAirportcd().equals("Thiruvananthapuram Airport")){
	     		 str=Constants.APPLICATION_THIRUVANANTHAPURAM_AIRPORT;          
		          strarr=str.split(":");
		          for(int i=0;i<strarr.length;i++){ 
		        	  CC_Lines =  CC_Lines + strarr[i]+"\n";  
				   } 
	     	}
	     	if(reportBean.getAirportcd().equals("Kolkata")){
	     		if(!reportBean.getRegion().equals("Eastern Region")){
	     			str=Constants.APPLICATION_KOLKATA_AIRPORT;
	     			strarr=str.split(":");
	     			for(int i=0;i<strarr.length;i++){ 
	     				 CC_Lines = CC_Lines + strarr[i]+"\n";
	     				}
	     		}
	     	}

	    	if(reportBean.getRegion().equals("Eastern Region")){
	     		if(reportBean.getAirportcd().equals("Kolkata")){
	     			str=Constants.APPLICATION_KOLKATA_AIRPORT_EAST_REGION;
	     			strarr=str.split(":");
	     			for(int i=0;i<strarr.length;i++){ 
	     				 CC_Lines = CC_Lines + strarr[i]+"\n";
	     				}
	     		}
	     	}
	    	
	    	if(reportBean.getAirportcd().equals("Kolkata Proj")){
	    		str=Constants.APPLICATION_KOLKATA_PROJECT;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
	    	
	    	if(reportBean.getAirportcd().equals("Chennai Iad")){
	    		str=Constants.APPLICATION_CHENNAI_AIRPORT;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
	    	
	    	if(reportBean.getAirportcd().equals("Cap Iad")){
	    		str=Constants.APPLICATION_CHENNAI_PROJECT;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
	    	
	    	if(reportBean.getAirportcd().equals("CSIA IAD")){
	    		str=Constants.APPLICATION_CSI_AIRPORT;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
      	    
	    	if(reportBean.getAirportcd().equals("Igi Iad")){
	    		str=Constants.APPLICATION_OPERATIONAL_OFFICE;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
	    	
	    	if(reportBean.getAirportcd().equals("Igicargo Iad")){
	    		str=Constants.APPLICATION_OPERATIONAL_OFFICE;
	    		strarr=str.split(":");
	    		for(int i=0;i<strarr.length;i++){ 
     				 CC_Lines = CC_Lines + strarr[i]+"\n";
     				}
	    	}
	    	
	    	if(reportBean.getAirportcd().equals("Operational Office")  || reportBean.getAirportcd().equals("Office Complex")){ 
     				 CC_Lines = CC_Lines +  reportBean.getEmployeeName()+" , "+ reportBean.getDesignation();
     				 
	    	}
	    	 
	    	log.info("======CC_Lines==========="+CC_Lines);
      	    reportBean.setCC_Lines(CC_Lines);
			sanctionOrderList.add(reportBean);
			 
			
			log.info("reportBean.getArreardate() in DAO-----"+reportBean.getArreardate()); 
			
			reportList.add(reportBean);
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			con=null;
			commonDB.closeConnection(null, st, con);
		}
	 return   reportList;
}
public AdvanceCPFForm2Bean loadPFWRevisedDetails(String pensionNo, String transactionID,String purposeType) {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	String transID = "", dateOfBirth = "", pfid = "",insertQry="", optionTypeDesc = "", oblCermonyDesc = "",updateQry="",revisedTransID="",advTransDt="",station="", region ="",findYear="",insertHBAQry="";
	AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
	ArrayList reportList = new ArrayList();
	EmpBankMaster bankMasterBean = new EmpBankMaster();
	String purposeTye = "", sqlQuery = "";
	try {
		con = commonDB.getConnection();
		st = con.createStatement();		
		sqlQuery = "SELECT PI.PENSIONNO AS PENSIONNO,PI.CPFACNO AS CPFACNO,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,AF.TRUST AS TRUST,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.ADVANCETYPE AS ADVANCETYPE,"
			+ "AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.PARTYNAME AS PARTYNAME,AF.PARTYADDRESS AS PARTYADDRESS,AF.NARRATION AS NARRATION,"
			+ "AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.SANCTIONDATE  AS SANCTIONDATE,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,AF.PAYMENTINFO AS PAYMENTINFO, "
			+ " AF.SONO AS SANCTIONORDERNO, AF.REGION AS REGION, AF.AIRPORTCODE AS  AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM AF "
			+ " WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
			+ pensionNo + " AND AF.ADVANCETRANSID=" + transactionID;

		log.info("-------loadPFWRevisedDetails:sqlQuery-------" + sqlQuery);
		String lod = "";

		rs = st.executeQuery(sqlQuery);
		if (rs.next()) {

			if (rs.getString("CPFACNO") != null) {
				basicBean.setCpfaccno(rs.getString("CPFACNO"));
			} else {
				basicBean.setCpfaccno("");
			}

			if (rs.getString("DESEGNATION") != null) {
				basicBean.setDesignation(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("DESEGNATION")));
			} else {
				basicBean.setDesignation("");
			}

			if (rs.getString("EMPLOYEENAME") != null) {
				basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
			} else {
				basicBean.setEmployeeName("");
			}
			if (rs.getString("NARRATION") != null) {
				basicBean.setNarration(rs.getString("NARRATION"));
			}
			// Getting Station,Region stored in employee_advances_form from 08-May-2012
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
			if (rs.getString("ADVANCETRANSDT") != null) {
				advTransDt = commonUtil.converDBToAppFormat(rs
						.getDate("ADVANCETRANSDT"));
				Date transdate = df.parse(advTransDt); 
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
			
			if (station != null) {

				if (station.equals("IGICargo IAD")) {
					basicBean.setStation(station);
				} else {
					basicBean.setStation(commonUtil
							.capitalizeFirstLettersTokenizer(station));
				}
			} else {
				basicBean.setStation("");
			}
			if (region != null) {
				if ((region.equals("CHQNAD"))
						|| (region.equals("CHQIAD"))
						|| (region.equals("RAUSAP"))
						|| (region.equals("North-East Region"))) {
					basicBean.setRegion(commonUtil.getRegion(region));
				} else {
					basicBean.setRegion(commonUtil.getRegion(commonUtil
							.capitalizeFirstLettersTokenizer(region)));
				}

			} else {
				basicBean.setRegion("");
			}

			if (rs.getString("GENDER") != null) {
				basicBean.setGender(rs.getString("GENDER"));
			} else {
				basicBean.setGender("");
			}
			if (rs.getString("PARTYNAME") != null) {
				basicBean.setPartyname(rs.getString("PARTYNAME"));
			} else {
				basicBean.setPartyname("---");
			}
			 
			if (region != null) {
				basicBean.setRegionlabel(commonUtil
						.getRegionLbls(commonUtil.getRegion(region).toLowerCase()));
			} else {
				basicBean.setRegionlabel("");
			}

			if (rs.getString("TRUST") != null) {
				basicBean.setTrust(rs.getString("TRUST"));
			} else {
				basicBean.setTrust("");
			}
			if (rs.getString("SANCTIONDATE") != null) {
				basicBean.setSanctiondate(CommonUtil.getDatetoString(rs
						.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
			} else {
				basicBean.setSanctiondate("");
			}
			if (rs.getString("APPROVEDAMNT") != null) {
				basicBean.setAmntAproved(commonUtil.getDecimalCurrency(rs
						.getDouble("APPROVEDAMNT")));
				basicBean.setAdvanceApprovedCurr(commonUtil
						.ConvertInWords(Double.parseDouble(rs
								.getString("APPROVEDAMNT"))));
			} else {
				basicBean.setAmntAproved("");
			}

			if (rs.getString("PURPOSETYPE") != null) {
				basicBean.setPurposeType(rs.getString("PURPOSETYPE") );
			} else {
				basicBean.setPurposeType("---");
			}

			if (rs.getString("PURPOSEOPTIONTYPE") != null) {

				if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"RENOVATIONHOUSE")) {
					basicBean.setPurposeOptionType("Renovation House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"PURCHASEHOUSE")) {
					basicBean.setPurposeOptionType("Purchase House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"CONSTRUCTIONHOUSE")) {
					basicBean.setPurposeOptionType("Construction House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"ACQUIREFLAT")) {
					basicBean.setPurposeOptionType("Acquiring Flat");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"PURCHASESITE")) {
					basicBean.setPurposeOptionType("Purchase Site");
				} else {
					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				}
			} else {
				basicBean.setPurposeOptionType("");
			}

			if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
				basicBean.setPrpsecvrdclse(rs
						.getString("PURPOSEOPTIONCVRDCLUSE"));
			} else {
				basicBean.setPrpsecvrdclse("");
			}

			if (rs.getString("SUBSCRIPTIONAMNT") != null) {

				if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION") ) {
					basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("APPROVEDSUBSCRIPTIONAMT")));
				} else {
					basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("SUBSCRIPTIONAMNT")));
				}

			} else {
				basicBean.setSubscriptionAmt("");
			}

			if (rs.getString("CONTRIBUTIONAMOUNT") != null) {

				if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION")) {
					basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("APPROVEDCONTRIBUTIONAMT")));
				} else {
					basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("CONTRIBUTIONAMOUNT")));
				}

			} else {
				basicBean.setContributionAmt("");
			}

			if (rs.getString("ADVANCETRANSID") != null) {
				basicBean.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
			}
			if (rs.getString("ADVANCETRANSID") != null) {
				basicBean.setAdvanceTransIDDec(rs.getString("ADVANCETYPE")
						+ "/" + rs.getString("PURPOSETYPE") + "/"
						+ rs.getString("ADVANCETRANSID"));
			}
			
			if (rs.getString("ADVANCETRANSDT") != null) {
				basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
						.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				findYear = commonUtil.converDBToAppFormat(basicBean
						.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
			} else {
				basicBean.setAdvntrnsdt("---");
			}

			if (rs.getString("ADVANCETRANSSTATUS") != null) {
				basicBean
						.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
			}
			if (rs.getString("PAYMENTINFO") != null) {
				basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
			}
			if (rs.getString("SANCTIONORDERNO") != null) {
				basicBean.setPfwSanctionOrderNo(commonUtil.leadingZeros(5,
						rs.getString("SANCTIONORDERNO")));
			} else {
				basicBean.setPfwSanctionOrderNo("---");
			}

			pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
					CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
							"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
			basicBean.setPfid(pfid);

			basicBean.setPensionNo(pensionNo);

		}
	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(null, st, con);
	}
	return basicBean;
}
public String getPFWRevisedTransidSequence(Connection con) {
	String revisedId = "";
	Statement st = null;
	ResultSet rs = null;
	String sqlQuery = "SELECT PFWREVISEDTRANSID.NEXTVAL AS  REVISEDTRANSID FROM DUAL";
	try {
		st = con.createStatement();
		rs = st.executeQuery(sqlQuery);
		if (rs.next()) {
			revisedId = rs.getString("REVISEDTRANSID");
		}

	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(rs, st, null);
	}
	return revisedId;
}
//Create By Radha On 28-May-2012  
public String updatePFWRevisedDetails(AdvanceCPFForm2Bean advanceBean, EmpBankMaster bankMaster) {
	Connection con = null;
	Statement st = null;
	int updatedRecords = 0;
	String updateQuery = "",updateHBAQuery="",cpfpfwTransCD="",verifiedBy="",currentDate="",revisedTransID="",insertQry="",insertHBAQry="",message="";
	CPFPFWTransInfoBean transBean = new CPFPFWTransInfoBean();

	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		currentDate = commonUtil.getCurrentDate("dd-MMM-yyyy");
		
		if(advanceBean.getFormName().equals("PFWRevisedVerification") &&  advanceBean.getFrmFlag().equals("New") ){
		revisedTransID = this.getPFWRevisedTransidSequence(con);
		insertQry =" INSERT INTO EMPLOYEE_ADVANCES_FORM_REV(PENSIONNO ,EMPLOYEENAME ,ADVANCETRANSID ,REVADVANCETRANSID,ADVANCETRANSDT , ADVANCETYPE ,  PURPOSETYPE   ,  PURPOSEOPTIONTYPE ,REQUIREDAMOUNT ,ADVANCETRANSSTATUS ,  REASON, IPADDRESS ,USERNAME ,PARTYNAME ,PARTYADDRESS ,LOD,CHKWTHDRWL ,TRNASMNTHEMOLUMENTS, APPROVEDAMNT,APPROVEDREMARKS ,APPROVEDDT ,PURPOSEOPTIONCVRDCLUSE,TOTALINATALLMENTS ,SUBSCRIPTIONAMNT,UTLISIEDAMNTDRWN ,"
			       +" ADVNCERQDDEPEND,VERIFIEDBY ,CONTRIBUTIONAMOUNT ,CPFACCFUND,PREVIOUSOUTSTANDINGAMT  ,  LOANTAKEN ,  TRUST, FINALTRANSSTATUS,   PAYMENTINFO,REGION,  NTIMESTRNASMNTHEMOLUMENTS	,   SANCTIONDATE   ,   MTHINSTALLMENTAMT  ,   PLACEOFPOSTING  ,	  APPROVEDSUBSCRIPTIONAMT   ,	APPROVEDCONTRIBUTIONAMT	  ,   DELETEFLAG  ,   NARRATION	 ,   INTERESTINSTALLMENTS  ,   INTERESTINSTALLAMT  ,   RECOMMENDEDAMT  ,"
			        +" CHKLISTFLAG ,   USERTRNASMNTHEMOLUMENTS, USERCHKWTHDRWL ,USERLOD , USERSUBSCRIPTIONAMNT  ,	 USERCONTRIBUTIONAMOUNT	  ,   USERPREVIOUSOUTSTANDINGAMT,   USERPURPOSETYPE  ,   USERREQUIREDAMOUNT   ,	USERTOTALINATALLMENTS  ,   TRANSDATE  ,   RAISEPAYMENT	   ,   COUNTVOUCHER   ,   AIRPORTCODE  ,   SONO   ,	ACCOUNTTYPE ,   LASTMTHINSTALLMENTAMT	 ,   CBREMARKS   ,   DESIGNATION ,	 TRACKINGREMARKS)"
			        + " SELECT PENSIONNO ,EMPLOYEENAME ,ADVANCETRANSID , '"+revisedTransID+"', ADVANCETRANSDT , ADVANCETYPE ,  PURPOSETYPE   ,  PURPOSEOPTIONTYPE ,REQUIREDAMOUNT ,ADVANCETRANSSTATUS ,  REASON, IPADDRESS ,USERNAME ,PARTYNAME ,PARTYADDRESS ,LOD,CHKWTHDRWL ,TRNASMNTHEMOLUMENTS, APPROVEDAMNT,APPROVEDREMARKS ,APPROVEDDT ,PURPOSEOPTIONCVRDCLUSE,TOTALINATALLMENTS ,SUBSCRIPTIONAMNT,UTLISIEDAMNTDRWN ,"
			       +" ADVNCERQDDEPEND,VERIFIEDBY ,CONTRIBUTIONAMOUNT ,CPFACCFUND,PREVIOUSOUTSTANDINGAMT  ,  LOANTAKEN ,  TRUST, FINALTRANSSTATUS,   PAYMENTINFO,REGION,  NTIMESTRNASMNTHEMOLUMENTS	,   SANCTIONDATE   ,   MTHINSTALLMENTAMT  ,   PLACEOFPOSTING  ,	  APPROVEDSUBSCRIPTIONAMT   ,	APPROVEDCONTRIBUTIONAMT	  ,   DELETEFLAG  ,   NARRATION	 ,INTERESTINSTALLMENTS  ,   INTERESTINSTALLAMT  ,   RECOMMENDEDAMT  ,"
			        +" CHKLISTFLAG ,USERTRNASMNTHEMOLUMENTS, USERCHKWTHDRWL  ,USERLOD ,USERSUBSCRIPTIONAMNT  ,	 USERCONTRIBUTIONAMOUNT	  ,   USERPREVIOUSOUTSTANDINGAMT,   USERPURPOSETYPE  ,   USERREQUIREDAMOUNT   ,	USERTOTALINATALLMENTS  ,   TRANSDATE  ,   RAISEPAYMENT	   ,   COUNTVOUCHER   ,   AIRPORTCODE  ,   SONO   ,	ACCOUNTTYPE ,  LASTMTHINSTALLMENTAMT	 ,   CBREMARKS   ,   DESIGNATION ,	 TRACKINGREMARKS FROM EMPLOYEE_ADVANCES_FORM WHERE  ADVANCETRANSID='"+advanceBean.getAdvanceTransID()+"'";
		log.info("-------updatePFWRevisedDetails:insertQry-------" + insertQry);
		st.executeUpdate(insertQry);  
		 
		if(advanceBean.getPurposeType().toUpperCase().equals("HBA")){
			insertHBAQry="INSERT INTO EMPLOYEE_ADVANCE_HBA_INFO_REV (ADVANCETRANSID,REPAYMENTLOANTYPE,  HBAOTHERS , REPAYMENTNAME, REPAYMENTADDRESS,REPAYMENTAMOUNT , NAME, ADDRESS,AREA ,PLOTNO,LOCALITY,MUNICIPALITY,CITY, HBADRWNFRMAAI,PERMISSIONAAI,PROPERTYADDRESS,ACTUALCOST, HBADRWNFRMAAIPURPOSE,HBADRWNFRMAAIAMOUNT,HBADRWNFRMAAIADDRESS,USERHBADRWNFRMAAI)"
				         +" ( SELECT '"+revisedTransID+"',REPAYMENTLOANTYPE,  HBAOTHERS , REPAYMENTNAME, REPAYMENTADDRESS,REPAYMENTAMOUNT , NAME, ADDRESS,AREA ,PLOTNO,LOCALITY,MUNICIPALITY,CITY, HBADRWNFRMAAI,PERMISSIONAAI,PROPERTYADDRESS,ACTUALCOST, HBADRWNFRMAAIPURPOSE,       HBADRWNFRMAAIAMOUNT,HBADRWNFRMAAIADDRESS,USERHBADRWNFRMAAI  FROM EMPLOYEE_ADVANCE_HBA_INFO WHERE ADVANCETRANSID='"+advanceBean.getAdvanceTransID()+"')";
			log.info("-------updatePFWRevisedDetails:insertHBAQry-------" + insertHBAQry);
			st.executeUpdate(insertHBAQry); 
		}
		
		}
		
		
		if(revisedTransID.equals("")){
			revisedTransID= advanceBean.getRevAdvanceTransID();
		}
		if(advanceBean.getFormName().equals("PFWRevisedVerification")){
			cpfpfwTransCD= Constants.APPLICATION_PROCESSING_PFW_REVISED_VERIFICATION;
			verifiedBy = "FINANCE";
			 
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM_REV SET REVVERIFIEDBY='"+verifiedBy+"',NARRATION='"
			+ advanceBean.getNarration()
			+ "',ADVANCETRANSDT='"+currentDate+"'  WHERE REVADVANCETRANSID='"
			+ revisedTransID + "'";
			
		}else if (advanceBean.getFormName().equals("PFWRevisedRecommendation")){
			cpfpfwTransCD= Constants.APPLICATION_PROCESSING_PFW_REVISED_RECOMMENDATION;
			verifiedBy = "FINANCE,RHQ";
			
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM_REV SET REVVERIFIEDBY='"+verifiedBy+"',NARRATION='"
			+ advanceBean.getNarration()
			+ "'  WHERE REVADVANCETRANSID='"
			+ revisedTransID + "'";
		}else{
			cpfpfwTransCD= Constants.APPLICATION_PROCESSING_PFW_REVISED_APPROVAL_ED;
			verifiedBy = "FINANCE,RHQ,APPROVED";
			 
			updateQuery = "UPDATE EMPLOYEE_ADVANCES_FORM_REV SET REVVERIFIEDBY='"+verifiedBy+"',NARRATION='"
			+ advanceBean.getNarration()
			+ "',APPROVEDDT='"+currentDate+"' ,SANCTIONDATE ='"+currentDate+"'   WHERE REVADVANCETRANSID='"
			+ revisedTransID + "'";
		}
		
		
		
		log.info("CPFPTWAdvanceDAO::updatePFWRevisedDetails"
				+ advanceBean.getPensionNo()
				+ advanceBean.getPurposeOptionType().toUpperCase().trim());
		
		log.info("CPFPTWAdvanceDAO::updatePFWRevisedDetails"	+ updateQuery);
		updatedRecords = st.executeUpdate(updateQuery);
		
		if (advanceBean.getPurposeType().toUpperCase().equals("HBA")) {				
			updateHBAQuery = "UPDATE employee_advance_hba_info_rev SET PROPERTYADDRESS='"
				+ advanceBean.getPropertyAddress()
				+ "'  WHERE ADVANCETRANSID='"
				+ revisedTransID + "'";
			log.info("CPFPTWAdvanceDAO::updatePFWRevisedDetails-----updateHBAQuery"	+ updateHBAQuery);
			updatedRecords = st.executeUpdate(updateHBAQuery);
		}

		
		String bankInfo= this.updateBankInfoRev(advanceBean.getPensionNo(),revisedTransID,advanceBean.getUpdateBankFlag(),advanceBean.getPaymentinfo(),bankMaster);
		
		transBean = this.getCPFPFWTransDetails(advanceBean
				.getAdvanceTransID());

		CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(advanceBean
				.getLoginUserId(), advanceBean.getLoginUserName(),
				advanceBean.getLoginUnitCode(), advanceBean
						.getLoginRegion(), advanceBean
						.getLoginUserDispName());
		
		
		cpfInfo.createCPFPFWTrans(transBean, advanceBean.getPensionNo(),
				revisedTransID, advanceBean.getLoginUserDesignation(), "PFW",cpfpfwTransCD);
		
		message = advanceBean.getAdvanceType().toUpperCase() + "/" + advanceBean.getPurposeType() + "/"+ revisedTransID;
		log.info("CPFPTWAdvanceDAO::updatePFWRevisedDetails-----message"	+ message);
	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(null, st, con);
	}
	return message;
}

public AdvanceCPFForm2Bean advanceSanctionOrderRev(String pensionNo,
		String transactionID, String frmName) throws InvalidDataException {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String transID = "", pfid = "", findYear = "";
	AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
	ArrayList reportList = new ArrayList();
	EmpBankMaster bankMasterBean = new EmpBankMaster();
	long cpfFund = 0;
	String purposeTye = "", sqlQuery = "", approvedAmt = "", loadElgAmnt = "", currentYear = "", lastYear = "", fromDate = "", currentMonth = "", currentDate = "", toDate = "",advTransDt="",station="", region ="";
	try {
		con = commonDB.getConnection();
		st = con.createStatement();

		sqlQuery = "SELECT PI.PENSIONNO AS PENSIONNO,PI.CPFACNO AS CPFACNO,PI.GENDER AS GENDER,PI.DATEOFBIRTH AS DATEOFBIRTH,NVL(AF.DESIGNATION,PI.DESEGNATION) AS DESEGNATION,INITCAP(PI.EMPLOYEENAME) AS EMPLOYEENAME,PI.AIRPORTCODE AS AIRPORTCODE_PERSNL,PI.REGION AS REGION_PERSNL,AF.TRUST AS TRUST,AF.APPROVEDAMNT AS APPROVEDAMNT,AF.ADVANCETYPE AS ADVANCETYPE,"
				+ "AF.PURPOSETYPE AS PURPOSETYPE,AF.PURPOSEOPTIONTYPE AS PURPOSEOPTIONTYPE,AF.PURPOSEOPTIONCVRDCLUSE AS PURPOSEOPTIONCVRDCLUSE,AF.SUBSCRIPTIONAMNT AS SUBSCRIPTIONAMNT,AF.CONTRIBUTIONAMOUNT AS CONTRIBUTIONAMOUNT,AF.PARTYNAME AS PARTYNAME,AF.PARTYADDRESS AS PARTYADDRESS,AF.NARRATION AS NARRATION,"
				+ "AF.APPROVEDSUBSCRIPTIONAMT AS APPROVEDSUBSCRIPTIONAMT,AF.APPROVEDCONTRIBUTIONAMT AS APPROVEDCONTRIBUTIONAMT,AF.ADVANCETRANSID  AS ADVANCETRANSID,AF.SANCTIONDATE  AS SANCTIONDATE,AF.ADVANCETRANSDT  AS ADVANCETRANSDT,AF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,AF.PAYMENTINFO AS PAYMENTINFO, "
				+ " AF.SONO AS SANCTIONORDERNO, AF.REGION AS REGION, AF.AIRPORTCODE AS  AIRPORTCODE  FROM EMPLOYEE_PERSONAL_INFO PI,EMPLOYEE_ADVANCES_FORM_REV AF "
				+ " WHERE AF.PENSIONNO = PI.PENSIONNO and AF.DELETEFLAG='N' and  AF.PENSIONNO ="
				+ pensionNo + " AND AF.REVADVANCETRANSID=" + transactionID;

		log
				.info("-------advanceSanctionOrderRev:sqlQuery-------"
						+ sqlQuery);

		rs = st.executeQuery(sqlQuery);
		if (rs.next()) {

			if (rs.getString("CPFACNO") != null) {
				basicBean.setCpfaccno(rs.getString("CPFACNO"));
			} else {
				basicBean.setCpfaccno("");
			}

			if (rs.getString("DESEGNATION") != null) {
				basicBean.setDesignation(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("DESEGNATION")));
			} else {
				basicBean.setDesignation("");
			}

			if (rs.getString("EMPLOYEENAME") != null) {
				basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
			} else {
				basicBean.setEmployeeName("");
			}
			if (rs.getString("NARRATION") != null) {
				basicBean.setNarration(rs.getString("NARRATION"));
			}
			// Getting Station,Region stored in employee_advances_form from 08-May-2012
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 
			if (rs.getString("ADVANCETRANSDT") != null) {
				advTransDt = commonUtil.converDBToAppFormat(rs
						.getDate("ADVANCETRANSDT"));
				Date transdate = df.parse(advTransDt); 
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
			
			if (station != null) {

				if (station.equals("IGICargo IAD")) {
					basicBean.setStation(station);
				} else {
					basicBean.setStation(commonUtil
							.capitalizeFirstLettersTokenizer(station));
				}
			} else {
				basicBean.setStation("");
			}
			if (region != null) {
				if ((region.equals("CHQNAD"))
						|| (region.equals("CHQIAD"))
						|| (region.equals("RAUSAP"))
						|| (region.equals("North-East Region"))) {
					basicBean.setRegion(commonUtil.getRegion(region));
				} else {
					basicBean.setRegion(commonUtil.getRegion(commonUtil
							.capitalizeFirstLettersTokenizer(region)));
				}

			} else {
				basicBean.setRegion("");
			}

			if (rs.getString("GENDER") != null) {
				basicBean.setGender(rs.getString("GENDER"));
			} else {
				basicBean.setGender("");
			}
			if (rs.getString("PARTYNAME") != null) {
				basicBean.setPartyname(rs.getString("PARTYNAME"));
			} else {
				basicBean.setPartyname("---");
			}
			 
			if (region != null) {
				basicBean.setRegionlabel(commonUtil
						.getRegionLbls(commonUtil.getRegion(region).toLowerCase()));
			} else {
				basicBean.setRegionlabel("");
			}

			if (rs.getString("TRUST") != null) {
				basicBean.setTrust(rs.getString("TRUST"));
			} else {
				basicBean.setTrust("");
			}
			if (rs.getString("SANCTIONDATE") != null) {
				basicBean.setSanctiondate(CommonUtil.getDatetoString(rs
						.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
			} else {
				basicBean.setSanctiondate("");
			}
			if (rs.getString("APPROVEDAMNT") != null) {
				basicBean.setAmntAproved(commonUtil.getDecimalCurrency(rs
						.getDouble("APPROVEDAMNT")));
				basicBean.setAdvanceApprovedCurr(commonUtil
						.ConvertInWords(Double.parseDouble(rs
								.getString("APPROVEDAMNT"))));
			} else {
				basicBean.setAmntAproved("");
			}

			if (rs.getString("PURPOSETYPE") != null) {
				basicBean.setPurposeType(commonUtil
						.capitalizeFirstLettersTokenizer(rs
								.getString("PURPOSETYPE")));
			} else {
				basicBean.setPurposeType("---");
			}

			if (rs.getString("PURPOSEOPTIONTYPE") != null) {

				if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"RENOVATIONHOUSE")) {
					basicBean.setPurposeOptionType("Renovation House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"PURCHASEHOUSE")) {
					basicBean.setPurposeOptionType("Purchase House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"CONSTRUCTIONHOUSE")) {
					basicBean.setPurposeOptionType("Construction House");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"ACQUIREFLAT")) {
					basicBean.setPurposeOptionType("Acquiring Flat");
				} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
						"PURCHASESITE")) {
					basicBean.setPurposeOptionType("Purchase Site");
				} else {
					basicBean.setPurposeOptionType(rs
							.getString("PURPOSEOPTIONTYPE"));
				}
			} else {
				basicBean.setPurposeOptionType("");
			}

			if (rs.getString("PURPOSEOPTIONCVRDCLUSE") != null) {
				basicBean.setPrpsecvrdclse(rs
						.getString("PURPOSEOPTIONCVRDCLUSE"));
			} else {
				basicBean.setPrpsecvrdclse("");
			}

			if (rs.getString("SUBSCRIPTIONAMNT") != null) {

				if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION") ) {
					basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("APPROVEDSUBSCRIPTIONAMT")));
				} else {
					basicBean.setSubscriptionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("SUBSCRIPTIONAMNT")));
				}

			} else {
				basicBean.setSubscriptionAmt("");
			}

			if (rs.getString("CONTRIBUTIONAMOUNT") != null) {

				if (rs.getString("PURPOSETYPE").equals("HBA") || rs.getString("PURPOSETYPE").equals("SUPERANNUATION")) {
					basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("APPROVEDCONTRIBUTIONAMT")));
				} else {
					basicBean.setContributionAmt(commonUtil.getDecimalCurrency(rs
							.getDouble("CONTRIBUTIONAMOUNT")));
				}

			} else {
				basicBean.setContributionAmt("");
			}

			if (rs.getString("ADVANCETRANSID") != null) {
				basicBean.setAdvanceTransID(rs.getString("ADVANCETYPE")
						+ "/" + rs.getString("PURPOSETYPE") + "/"
						+ rs.getString("ADVANCETRANSID"));
			}

			if (rs.getString("ADVANCETRANSDT") != null) {
				basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
						.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
				findYear = commonUtil.converDBToAppFormat(basicBean
						.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
			} else {
				basicBean.setAdvntrnsdt("---");
			}

			if (rs.getString("ADVANCETRANSSTATUS") != null) {
				basicBean
						.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
			}
			if (rs.getString("PAYMENTINFO") != null) {
				basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
			}
			if (rs.getString("SANCTIONORDERNO") != null) {
				basicBean.setPfwSanctionOrderNo(commonUtil.leadingZeros(5,
						rs.getString("SANCTIONORDERNO")));
			} else {
				basicBean.setPfwSanctionOrderNo("---");
			}

			pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
					CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
							"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
			basicBean.setPfid(pfid);

			basicBean.setPensionNo(pensionNo);

		}
	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (InvalidDataException e) {
		log.printStackTrace(e);
		throw e;
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(null, st, con);
	}
	return basicBean;
}
public AdvanceBasicReportBean loadPFWHBAREV(String transactionID,
		AdvanceBasicReportBean reportBean) {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	String sqlQuery = "SELECT HBADRWNFRMAAI,PERMISSIONAAI,CITY,MUNICIPALITY,LOCALITY,PLOTNO,AREA,ADDRESS,NAME,REPAYMENTAMOUNT,REPAYMENTADDRESS,REPAYMENTNAME,HBAOTHERS,REPAYMENTLOANTYPE,ACTUALCOST,PROPERTYADDRESS,HBADRWNFRMAAIPURPOSE,HBADRWNFRMAAIAMOUNT,HBADRWNFRMAAIADDRESS FROM EMPLOYEE_ADVANCE_HBA_INFO_REV WHERE ADVANCETRANSID="
			+ transactionID;
	log.info(sqlQuery);
	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		rs = st.executeQuery(sqlQuery);
		if (rs.next()) {
			if (rs.getString("HBADRWNFRMAAI") != null) {
				reportBean.setHbadrwnfrmaai(rs.getString("HBADRWNFRMAAI")
						.trim());
			}
			if (rs.getString("PERMISSIONAAI") != null) {
				reportBean.setHbapermissionaai(rs
						.getString("PERMISSIONAAI").trim());
			}
			if (rs.getString("CITY") != null) {
				reportBean.setHbaownercity(rs.getString("CITY"));
			}
			if (rs.getString("MUNICIPALITY") != null) {
				reportBean
						.setHbaownermuncipal(rs.getString("MUNICIPALITY"));
			}
			if (rs.getString("LOCALITY") != null) {
				reportBean.setHbaownerlocality(rs.getString("LOCALITY"));
			}
			if (rs.getString("PLOTNO") != null) {
				reportBean.setHbaownerplotno(rs.getString("PLOTNO"));
			}
			if (rs.getString("AREA") != null) {
				reportBean.setHbaownerarea(rs.getString("AREA"));
			}
			if (rs.getString("ADDRESS") != null) {
				reportBean.setHbaowneraddress(rs.getString("ADDRESS"));
			}
			if (rs.getString("NAME") != null) {
				reportBean.setHbaownername(rs.getString("NAME"));
			}

			if (rs.getString("REPAYMENTAMOUNT") != null) {
				reportBean.setOsamountwithinterest(rs
						.getString("REPAYMENTAMOUNT"));
			}
			if (rs.getString("REPAYMENTADDRESS") != null) {
				reportBean.setHbaloanaddress(rs
						.getString("REPAYMENTADDRESS"));
			}
			if (rs.getString("REPAYMENTNAME") != null) {
				reportBean.setHbaloanname(rs.getString("REPAYMENTNAME"));
			}
			if (rs.getString("HBAOTHERS") != null) {
				reportBean.setCurseDuration(rs.getString("HBAOTHERS"));
			}
			if (rs.getString("REPAYMENTLOANTYPE") != null) {
				reportBean.setHbarepaymenttype(rs
						.getString("REPAYMENTLOANTYPE"));
			}
			if (rs.getString("ACTUALCOST") != null) {
				reportBean.setActualcost(rs.getString("ACTUALCOST"));
			} else {
				reportBean.setActualcost("0.00");
			}
			if (rs.getString("PROPERTYADDRESS") != null) {
				reportBean.setPropertyaddress(rs
						.getString("PROPERTYADDRESS"));
			} else {
				reportBean.setPropertyaddress("");
			}

			if (rs.getString("HBADRWNFRMAAIPURPOSE") != null) {
				reportBean.setHbawthdrwlpurpose(rs
						.getString("HBADRWNFRMAAIPURPOSE"));
			} else {
				reportBean.setHbawthdrwlpurpose("");
			}
			if (rs.getString("HBADRWNFRMAAIAMOUNT") != null) {
				reportBean.setHbawthdrwlamount(rs
						.getString("HBADRWNFRMAAIAMOUNT"));
			} else {
				reportBean.setHbawthdrwlamount("");
			}
			if (rs.getString("HBADRWNFRMAAIADDRESS") != null) {
				reportBean.setHbawthdrwladdress(rs
						.getString("HBADRWNFRMAAIADDRESS"));
			} else {
				reportBean.setHbawthdrwladdress("");
			}
		}

	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(rs, st, null);
	}
	return reportBean;
}
 
public ArrayList searchAdvancesRev(AdvanceSearchBean advanceSearchBean) {

	Connection con = null;
	Statement st = null;
	ArrayList searchList = new ArrayList();
	AdvanceSearchBean searchBean = null;
	log.info("CPFPTWAdvanceDAO::searchAdvanceRev"
			+ advanceSearchBean.getPensionNo() + "Advance Type"
			+ advanceSearchBean.getAdvanceType() + "Verfied By"
			+ advanceSearchBean.getVerifiedBy());
	String pensionNo = "", dateOfBirth = "", pfid = "", selectQuery = "", unitCode = "", unitName = "", region = "";
	try {
		con = DBUtility.getConnection();
		st = con.createStatement();

		String reg = commonUtil.getAirportsByProfile(advanceSearchBean
				.getLoginProfile(), advanceSearchBean.getLoginUnitCode(),
				advanceSearchBean.getLoginRegion());

		if (!reg.equals("")) {
			String[] regArr = reg.split(",");
			unitCode = regArr[0];
			region = regArr[1];
		}

		if (!unitCode.equals("-"))
			unitName = this.getUnitName(advanceSearchBean, con);

		if (region.equals("-"))
			advanceSearchBean.setLoginRegion("");

	   if (advanceSearchBean.getFormName().equals("PFWRevisedVerification")) {

			selectQuery = this.buildSearchQueryForPFWRevisedVerifcationSeacrh(
					advanceSearchBean, advanceSearchBean.getFormName(),	unitName);  
		} else if ((advanceSearchBean.getFormName().equals("PFWRevisedRecommendation")) ||
				(advanceSearchBean.getFormName().equals("PFWRevisedApproval"))||
				(advanceSearchBean.getFormName().equals("PFWRevisedApproved"))) {

			selectQuery = this.buildSearchQueryForPFWRevisedRecommendationSeacrh(
					advanceSearchBean, advanceSearchBean.getFormName(),	unitName);  
		} 
		log.info("CPFPTWAdvanceDAO::searchAdvanceRev" + selectQuery);
		ResultSet rs = st.executeQuery(selectQuery);
		while (rs.next()) {
			searchBean = new AdvanceSearchBean();
			searchBean.setRequiredamt(rs.getString("REQUIREDAMOUNT"));
			searchBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
			searchBean.setDesignation(rs.getString("DESEGNATION"));
			searchBean.setPurposeType(rs.getString("PURPOSETYPE"));
			searchBean.setAdvanceType(rs.getString("ADVANCETYPE"));

			searchBean.setAdvanceStatus(rs.getString("ADVANCETRANSSTATUS"));

			searchBean.setTransMnthYear(CommonUtil.getDatetoString(rs
					.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
			if (rs.getString("PENSIONNO") != null) {
				pensionNo = rs.getString("PENSIONNO");
			}
			if (rs.getString("dateofbirth") != null) {
				dateOfBirth = CommonUtil.converDBToAppFormat(rs
						.getDate("dateofbirth"));
			} else {
				dateOfBirth = "---";
			}
			if (rs.getString("TOTALINATALLMENTS") != null) {
				searchBean.setCpfIntallments(rs
						.getString("TOTALINATALLMENTS"));
			} else {
				searchBean.setCpfIntallments("");
			}

			if (rs.getString("SANCTIONDATE") != null) {
				searchBean.setSanctiondt(CommonUtil.getDatetoString(rs
						.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
			} else {
				searchBean.setSanctiondt("");
			}
			if (rs.getString("PAYMENTINFO") != null) {
				searchBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
			} else {
				searchBean.setPaymentinfo("");
			}
			 
			if (rs.getString("REVVERIFIEDBY") != null) {
				if (advanceSearchBean.getFormName().equals("PFWRevisedVerification")) {
					if (rs.getString("REVVERIFIEDBY").equals("FINANCE") || 
							rs.getString("REVVERIFIEDBY").equals("FINANCE,RHQ") || 
								rs.getString("REVVERIFIEDBY").equals("FINANCE,RHQ,APPROVED"))
						searchBean.setAdvanceStatus("A");
					else
						searchBean.setAdvanceStatus("N");
				} else if (advanceSearchBean.getFormName().equals("PFWRevisedRecommendation")) { 
					
					if (rs.getString("REVVERIFIEDBY").equals("FINANCE,RHQ")|| 
							rs.getString("REVVERIFIEDBY").equals("FINANCE,RHQ,APPROVED"))
						searchBean.setAdvanceStatus("A");
					else
						searchBean.setAdvanceStatus("N");
					
				} else if (advanceSearchBean.getFormName().equals("PFWRevisedApproval")) {
					if (rs.getString("REVVERIFIEDBY").equals("FINANCE,RHQ,APPROVED"))
						searchBean.setAdvanceStatus("A");
					else
						searchBean.setAdvanceStatus("N");

				}  
				searchBean.setVerifiedBy(rs.getString("REVVERIFIEDBY")); 
			} else { 
					searchBean.setAdvanceStatus("N"); 
					searchBean.setVerifiedBy("");			 
					
			}  
			if (rs.getString("REVADVANCETRANSID") != null) {
				searchBean.setRevAdvanceTransID(rs.getString("REVADVANCETRANSID"));
			}else{
				searchBean.setRevAdvanceTransID("---");
			}
			
			searchBean.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
			searchBean.setAdvanceTransIDDec(searchBean.getAdvanceType()
					.toUpperCase()
					+ "/"
					+ searchBean.getPurposeType().toUpperCase()
					+ "/"
					+ rs.getString("ADVANCETRANSID"));
			
			pfid = commonDAO.getPFID(searchBean.getEmployeeName(),
					dateOfBirth, pensionNo);
			searchBean.setPfid(pfid);
			searchBean.setPensionNo(pensionNo);
			searchBean.setAdvanceType(rs.getString("ADVANCETYPE")
					.toUpperCase());
			searchBean.setPurposeType(rs.getString("PURPOSETYPE")
					.toUpperCase());

			
			

			 
			if ((advanceSearchBean.getFormName().equals("PFWRevisedVerification")) ||
					(advanceSearchBean.getFormName().equals("PFWRevisedRecommendation")) ||
					(advanceSearchBean.getFormName().equals("PFWRevisedFormApproved"))  ) {
				
				if (rs.getString("SANCTIONDATE") != null) {
					searchBean.setSanctiondt(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy")); 
				}else{
					searchBean.setSanctiondt("---");
				}
			}
				
			searchList.add(searchBean);
		}
		log.info("searchLIst" + searchList.size());
	} catch (SQLException e) {
		log.printStackTrace(e);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		commonDB.closeConnection(null, st, con);
	}
	return searchList;

}
public AdvanceCPFForm2Bean editAdvancesRevised(String pensionNo,String 	transactionNo,String purposeType, String frmName) {
 
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String transID = "", dateOfBirth = "", pfid = "",insertQry="", selectQuery = "", oblCermonyDesc = "",updateQry="",revisedTransID="",advTransDt="",station="", region ="",findYear="",insertHBAQry="";
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		ArrayList reportList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String purposeTye = "", sqlQuery = "";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();		
			selectQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,NVL(EAF.DESIGNATION,EMPFID.DESEGNATION) AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,  EAF.ADVANCETRANSDT AS ADVANCETRANSDT, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,EAF.SANCTIONDATE AS SANCTIONDATE,"
				+ "EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS,EAF.VERIFIEDBY AS VERIFIEDBY, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME ,EAF.REVVERIFIEDBY AS REVVERIFIEDBY ,EAF.REVADVANCETRANSID AS REVADVANCETRANSID,EAF.NARRATION AS NARRATION ,EAF.SONO AS SANCTIONORDERNO FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM_REV   EAF WHERE     EAF.PENSIONNO=EMPFID.PENSIONNO AND  EAF.DELETEFLAG='N'"
				+ " AND  EAF.CHKLISTFLAG='Y' AND  EAF.REVVERIFIEDBY is NOT NULL AND EAF.ADVANCETRANSSTATUS ='A' AND EAF.FINALTRANSSTATUS ='A' AND EAF.SANCTIONDATE IS NOT NULL   and  EAF.PENSIONNO ="+ pensionNo + " AND EAF.REVADVANCETRANSID='" + transactionNo+"'";

		   
			log.info("-------editAdvancesRevised:sqlQuery-------" + selectQuery);
			String lod = "";

			rs = st.executeQuery(selectQuery);
			if (rs.next()) { 
				basicBean.setPensionNo(pensionNo);
				
				if (rs.getString("DESEGNATION") != null) {
					basicBean.setDesignation(commonUtil
							.capitalizeFirstLettersTokenizer(rs
									.getString("DESEGNATION")));
				} else {
					basicBean.setDesignation("");
				}

				if (rs.getString("EMPLOYEENAME") != null) {
					basicBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
				} else {
					basicBean.setEmployeeName("");
				}
				if (rs.getString("NARRATION") != null) {
					basicBean.setNarration(rs.getString("NARRATION"));
				}
				 
				 
				if (rs.getString("SANCTIONDATE") != null) {
					basicBean.setSanctiondate(CommonUtil.getDatetoString(rs
							.getDate("SANCTIONDATE"), "dd-MMM-yyyy"));
				} else {
					basicBean.setSanctiondate("");
				}
				 

				if (rs.getString("PURPOSETYPE") != null) {
					basicBean.setPurposeType(rs.getString("PURPOSETYPE") );
				} else {
					basicBean.setPurposeType("---");
				}

				if (rs.getString("PURPOSEOPTIONTYPE") != null) {

					if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"RENOVATIONHOUSE")) {
						basicBean.setPurposeOptionType("Renovation House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"PURCHASEHOUSE")) {
						basicBean.setPurposeOptionType("Purchase House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"CONSTRUCTIONHOUSE")) {
						basicBean.setPurposeOptionType("Construction House");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"ACQUIREFLAT")) {
						basicBean.setPurposeOptionType("Acquiring Flat");
					} else if (rs.getString("PURPOSEOPTIONTYPE").trim().equals(
							"PURCHASESITE")) {
						basicBean.setPurposeOptionType("Purchase Site");
					} else {
						basicBean.setPurposeOptionType(rs
								.getString("PURPOSEOPTIONTYPE"));
					}
				} else {
					basicBean.setPurposeOptionType("");
				}

				 
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransID(rs.getString("ADVANCETRANSID"));
				}
				
				if (rs.getString("ADVANCETRANSID") != null) {
					basicBean.setAdvanceTransIDDec(rs.getString("ADVANCETYPE")
							+ "/" + rs.getString("PURPOSETYPE") + "/"
							+ rs.getString("ADVANCETRANSID"));
				}
				
				if (rs.getString("REVADVANCETRANSID") != null) {
					basicBean.setRevAdvanceTransID(rs.getString("REVADVANCETRANSID"));
							 
				}else{
					basicBean.setRevAdvanceTransID("---");
				}
				
				if (rs.getString("REVVERIFIEDBY") != null) {
					basicBean.setVerifiedby(rs.getString("REVVERIFIEDBY"));
							 
				}else{
					basicBean.setVerifiedby("---");
				}
				if (rs.getString("ADVANCETRANSDT") != null) {
					basicBean.setAdvntrnsdt(CommonUtil.getDatetoString(rs
							.getDate("ADVANCETRANSDT"), "dd-MMM-yyyy"));
					findYear = commonUtil.converDBToAppFormat(basicBean
							.getAdvntrnsdt(), "dd-MMM-yyyy", "yyyy");
				} else {
					basicBean.setAdvntrnsdt("---");
				}

				if (rs.getString("ADVANCETRANSSTATUS") != null) {
					basicBean
							.setTransStatus(rs.getString("ADVANCETRANSSTATUS"));
				}
				if (rs.getString("PAYMENTINFO") != null) {
					basicBean.setPaymentinfo(rs.getString("PAYMENTINFO"));
				}
				if (rs.getString("SANCTIONORDERNO") != null) {
					basicBean.setPfwSanctionOrderNo(commonUtil.leadingZeros(5,
							rs.getString("SANCTIONORDERNO")));
				} else {
					basicBean.setPfwSanctionOrderNo("---");
				}

				pfid = commonDAO.getPFID(basicBean.getEmployeeName(),
						CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),
								"dd-MMM-yyyy"), rs.getString("PENSIONNO"));
				basicBean.setPfid(pfid);

				basicBean.setPensionNo(pensionNo);

			}
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			commonDB.closeConnection(null, st, con);
		}
		return basicBean;

		}
public String updateBankInfoRev(String pensionNo, String transId,
		String bankFlag, String paymentFlag, EmpBankMaster bankBean) {

	Connection con = null;
	Statement st = null;
	String updateQuery = "", updatePaymentQry = "", updatePaymentQry1 = "";
	int updatedRecords = 0, count = 0, updatedRecords1 = 0;

	try {
		con = commonDB.getConnection();
		st = con.createStatement();

		count = this.checkBankDetails(con,pensionNo, transId);

		log.info("--in DAO- payment flag- " + paymentFlag
				+ "---bank flag--" + bankFlag);
		if (paymentFlag.equals("Y")) {

			if (!transId.equals(""))
				bankBean.setTransId(transId);

			if (count == 0) {
				this.addEmployeeBankInfo(con, bankBean, pensionNo);

			} else if ((count != 0) && (bankFlag.equals("Y"))) {
				updateQuery = "update EMPLOYEE_BANK_INFO set NAME='"
						+ bankBean.getBankempname() + "',SAVINGBNKACCNO='"
						+ bankBean.getBanksavingaccno() + "',BANKNAME='"
						+ bankBean.getBankname() + "',NEFTRTGSCODE='"
						+ bankBean.getBankemprtgsneftcode()
						+ "',ADDRESS='" + bankBean.getBankempaddrs()
						+ "',BRANCHADDRESS='" + bankBean.getBranchaddress()
						+ "',MICRONO='" + bankBean.getBankempmicrono()
						+ "',MAILID='" + bankBean.getEmpmailid()							 
						+ "',PARTYNAME='" + bankBean.getPartyName()
						+ "',PARTYADDRESS='" + bankBean.getPartyAddress()
						+ "',USERNAME=USERNAME||','||'" + this.userName
						+ "',LASTACTIVE='"
						+ commonUtil.getCurrentDate("dd-MMM-yyyy")
						+ "' where CPFPFWTRANSID='" + bankBean.getTransId()
						+ "' and  pensionno=" + pensionNo + "";
				log
						.info("CPFPTWAdvanceDAO::updateBankInfoRev()==update Query== For FS"
								+ updateQuery);
				updatedRecords = st.executeUpdate(updateQuery);

				updatePaymentQry1 = "update EMPLOYEE_ADVANCES_FORM_REV set PARTYNAME='"
						+ bankBean.getPartyName()
						+ "' , PARTYADDRESS='"
						+ bankBean.getPartyAddress()
						+ "'   where  REVADVANCETRANSID='"
						+ transId
						+ "' and pensionno='" + pensionNo + "'";
				log
						.info("CPFPTWAdvanceDAO::updateBankInfoRev()==update Query==For CPF/PFW"
								+ updatePaymentQry1);
				updatedRecords1 = st.executeUpdate(updatePaymentQry1);

			}
		} else {
			if (count != 0) {
				 

				updatePaymentQry1 = "update EMPLOYEE_ADVANCES_FORM_REV set PAYMENTINFO='N' where  REVADVANCETRANSID='"
						+ transId + "' and pensionno='" + pensionNo + "'";
				updatedRecords1 = st.executeUpdate(updatePaymentQry1);
			}

		}
		log.info("CPFPTWAdvanceDAO::updateBankInfoRev()==updatePaymentQry==="
				+ updatePaymentQry);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;

} 
}
