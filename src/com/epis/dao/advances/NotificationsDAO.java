package com.epis.dao.advances;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.bean.advances.NotificationBean; 
import com.epis.common.exception.EPISException;
import com.epis.dao.CommonDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
 

public class NotificationsDAO {
	Log log = new Log(NotificationsDAO.class);
	 
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil = new CommonUtil();
	CommonDAO commonDAO = new CommonDAO();
	public NotificationsDAO() {

	}

	private static final NotificationsDAO dao = new NotificationsDAO();

	public static NotificationsDAO getInstance() {
		return dao;
	}

	public List getNotifications(String queryType, AdvanceSearchBean searchBean) throws EPISException {
		ArrayList rejectedCPFList = new ArrayList();
		ArrayList accessRightsList = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		NotificationBean info = null; 
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();	
			String cpfNotiQueryLoad="",advanceToBeApproved="",privilageStage="Nothing",profileName="",profilecondition="";
			profileName=searchBean.getProfileName();
			if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
				profilecondition="";			 
			}else{ 
				profilecondition=" and LOWER(EAF.AIRPORTCODE) like'%"+searchBean.getLoginUnitName().toLowerCase()+"%'";
				 
			}
			cpfNotiQueryLoad=" SELECT EPI.PENSIONNO AS PENSIONNO, EPI.EMPLOYEENAME  AS EMPLOYEENAME , EPI.DATEOFBIRTH  AS DATEOFBIRTH, EPI.DATEOFJOINING AS DATEOFJOINING,"
									+" EPI.DESEGNATION   AS DESEGNATION, EAF.REGION AS REGION, EAF.AIRPORTCODE AS AIPORTCODE, EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETYPE || '/' ||  EAF.purposetype || '/' ||  EAF.ADVANCETRANSID  AS  KEYNO,"
									+" EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS  PURPOSETYPE FROM EMPLOYEE_ADVANCES_FORM EAF, EMPLOYEE_PERSONAL_INFO EPI WHERE EAF.PENSIONNO = EPI.PENSIONNO  AND EAF.DELETEFLAG = 'N' "
									+" AND EAF.ADVANCETYPE = 'CPF'  AND EAF.FINALTRANSSTATUS = 'R' "+profilecondition+"  ORDER BY EAF.PENSIONNO"; 
			
			log.info("=======getNotifications===="+cpfNotiQueryLoad);
			rs = st.executeQuery(cpfNotiQueryLoad);			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("EMPLOYEENAME"));
				info.setPensionNo(rs.getString("PENSIONNO"));
				info.setTransactionId(rs.getString("ADVANCETRANSID"));
				info.setKeyNo(rs.getString("KEYNO"));	
				info.setAdvanceType(rs.getString("ADVANCETYPE"));			 
				info.setAirportCode(rs.getString("AIPORTCODE"));
				info.setPurposeType(rs.getString("PURPOSETYPE"));
				rejectedCPFList.add(info);
				
			} 
			 
			
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return rejectedCPFList;
	}
	 
	
	public String getNewAdvanceRequest(AdvanceSearchBean advancebean)throws EPISException{		 
		Connection con = null;
		Statement st = null;
		Statement st1 = null;
		ResultSet rs = null;
		AdvanceBasicBean basicbean = null;
		String insertQry="",updateQry="",selectQry="",updateDelFlag="",advanceTransID="",recommendAmnt="0",advanceType="",
		cpfpfwTransCD="",transType="",pensionNo="", message="",purposeType="",updateBankQry="",updateHEQry="",updateFmlyQry="",updateWithDrawQry="";
		 
		try{
			advanceType = advancebean.getAdvanceType();
			pensionNo =  advancebean.getPensionNo();
			purposeType = advancebean.getPurposeType();
			con = DBUtility.getConnection();
			st = con.createStatement();
			advanceTransID = this.getAdvanceSequence(con); 
			 insertQry="INSERT INTO EMPLOYEE_ADVANCES_FORM (PENSIONNO,ADVANCETRANSID,ADVANCETRANSDT,ADVANCETYPE,PURPOSETYPE,USERPURPOSETYPE,PURPOSEOPTIONTYPE,reason,IPADDRESS, USERNAME, LOD,USERLOD,ADVNCERQDDEPEND,UTLISIEDAMNTDRWN,CHKWTHDRWL, USERCHKWTHDRWL,TRUST,REGION, AIRPORTCODE,paymentinfo,"
						+" TRNASMNTHEMOLUMENTS,USERTRNASMNTHEMOLUMENTS,PARTYNAME,PARTYADDRESS,PLACEOFPOSTING,TOTALINATALLMENTS,USERTOTALINATALLMENTS, ACCOUNTTYPE) (SELECT PENSIONNO,'"+advanceTransID+"',to_char(sysdate,'dd-Mon-yyyy'),ADVANCETYPE,PURPOSETYPE,USERPURPOSETYPE,PURPOSEOPTIONTYPE,"
						+" reason,IPADDRESS, USERNAME, LOD,USERLOD,ADVNCERQDDEPEND,UTLISIEDAMNTDRWN,CHKWTHDRWL, USERCHKWTHDRWL,TRUST,REGION, AIRPORTCODE,paymentinfo,"
						+" USERTRNASMNTHEMOLUMENTS,USERTRNASMNTHEMOLUMENTS,PARTYNAME,PARTYADDRESS,PLACEOFPOSTING,TOTALINATALLMENTS,USERTOTALINATALLMENTS, ACCOUNTTYPE FROM EMPLOYEE_ADVANCES_FORM WHERE ADVANCETRANSID='"+advancebean.getAdvanceTransID()+"')";
			 log.info("NotificationsDAO::getNewAdvanceRequest()--insertQry "+insertQry);  
			 st.executeUpdate(insertQry); 
			 updateDelFlag = "update EMPLOYEE_ADVANCES_FORM set  DELETEFLAG='Y' where 	ADVANCETRANSID='"+advancebean.getAdvanceTransID()+"'";
			 log.info("NotificationsDAO::getNewAdvanceRequest()--updateDelFlag "+updateDelFlag);  
			 st.executeUpdate(updateDelFlag); 
			 selectQry = "SELECT RECOMMENDEDAMT FROM EPIS_ADVANCES_TRANSACTIONS WHERE CPFPFWTRANSID='"+advancebean.getAdvanceTransID()+"' AND PENSIONNO ="+pensionNo+" AND  TRANSCD=1";
			 log.info("NotificationsDAO::getNewAdvanceRequest()--selectQry  For Recommended Amnt"+selectQry);  
			 rs= st.executeQuery(selectQry); 
			 if(rs.next()){
				 if(rs.getString("RECOMMENDEDAMT")!=null){
					 recommendAmnt = rs.getString("RECOMMENDEDAMT");
				 }
			 }
		updateQry =  "update EMPLOYEE_ADVANCES_FORM set REQUIREDAMOUNT='"+recommendAmnt+"', USERREQUIREDAMOUNT='"+recommendAmnt+"' where 	ADVANCETRANSID='"+advanceTransID+"'";
		log.info("NotificationsDAO::getNewAdvanceRequest()--updateQry  "+updateQry);  
		st.executeUpdate(updateQry);
		
		updateBankQry ="update EMPLOYEE_BANK_INFO  set cpfpfwtransid='"+advanceTransID+"' where cpfpfwtransid='"+advancebean.getAdvanceTransID()+"' and pensionno ="+pensionNo;
		log.info("NotificationsDAO::getNewAdvanceRequest()--updateBankQry  "+updateBankQry);  
		st.executeUpdate(updateBankQry); 
		
		updateHEQry ="update EMPLOYEE_ADVANCES_HE_INFO  set ADVANCETRANSID='"+advanceTransID+"' where ADVANCETRANSID='"+advancebean.getAdvanceTransID()+"'";
		updateFmlyQry ="update EMPLOYEE_ADVANCES_FAMILY_DTLS  set ADVANCETRANSID='"+advanceTransID+"' where ADVANCETRANSID='"+advancebean.getAdvanceTransID()+"'";
		
		
		
	if(advancebean.getAdvanceType().toUpperCase().equals("CPF") && advancebean.getPurposeType().toUpperCase().equals("EDUCATION")) {
		log.info("NotificationsDAO::getNewAdvanceRequest()--updateHEQry  "+updateHEQry);  
		st.executeUpdate(updateHEQry); 
	}
	
	if((advancebean.getAdvanceType().toLowerCase().equals("cpf") && advancebean.getPurposeType().toUpperCase().equals("obligatory".toUpperCase()))||
	 ((advancebean.getAdvanceType().toLowerCase().equals("cpf") && advancebean.getPurposeType().toUpperCase().equals("COST")))|| 
	 ((advancebean.getAdvanceType().toLowerCase().equals("cpf") && advancebean.getPurposeType().toUpperCase().equals("OBMARRIAGE")))|| 
	 ((advancebean.getAdvanceType().toLowerCase().equals("cpf") && advancebean.getPurposeType().toUpperCase().equals("illness".toUpperCase())))) {
		log.info("Family Details");
		log.info("NotificationsDAO::getNewAdvanceRequest()--updateFmlyQry  "+updateFmlyQry);  
		st.executeUpdate(updateFmlyQry); 
	}	
	
	
	updateWithDrawQry ="update employee_advances_wthdrwl_info  set ADVANCETRANSID='"+advanceTransID+"' where ADVANCETRANSID='"+advancebean.getAdvanceTransID()+"'";
	log.info("NotificationsDAO::getNewAdvanceRequest()--updateWithDrawQry  "+updateWithDrawQry);  
	st.executeUpdate(updateWithDrawQry); 
	
	
		if(advanceType.equals("CPF")){
			cpfpfwTransCD=Constants.APPLICATION_PROCESSING_CPF_DELETE;
			transType="CPF";
		}else if(advanceType.equals("PFW")){
			cpfpfwTransCD=Constants.APPLICATION_PROCESSING_PFW_DELETE;;
			transType="PFW";
		}else{
			cpfpfwTransCD="";
			transType="";
		}
		 
		log.info("CPFPTWAdvanceDAO::deleteRecords()---cpfpfwTransCD----"+cpfpfwTransCD+"----transType--"+transType);
		
		CPFPFWTransInfo cpfInfo=new CPFPFWTransInfo(advancebean.getLoginUserId(),advancebean.getLoginUserName(),advancebean.getLoginUnitCode(),advancebean.getLoginRegion(),advancebean.getLoginUserDispName());			 			
		cpfInfo.deleteCPFPFWTrans(pensionNo,advanceTransID,transType,cpfpfwTransCD,"REJECTED CASE");

		
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException("Errors||| Unable to Process Your Request");
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		
	 
		message = advanceType.toUpperCase() + "/" + purposeType + "/"
			+ advanceTransID;
		
		try{ }catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			 
			DBUtility.closeConnection(rs,st,con);
		}
		return message;
		
	}
	
	 
	String pfwNotiQueryLoad=" select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO, form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= (case when(form.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Apr/2011' end) and form.PENSIONNO like  ? and  form.DELETEFLAG != 'Y'   union  select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info  where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') = 'Y' and  APPROVEDDT >=(case when(info.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else  '01/Apr/2011' end) and (APPROVEDSUBSCRIPTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and info.transid = ADVANCETRANSID and  accounthead = '672.04') > 0 or  APPROVEDCONTRIBUTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info  where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID and  accounthead = '672.05') > 0) and  form.PENSIONNO like ? and form.DELETEFLAG != 'Y'   order by appDt ";
	String fsNotiQueryLoad=" select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,NSSANCTIONNO  KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form,employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY  = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT  >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR'  union select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,  NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'  and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and ( EMPSHARESUBSCRIPITION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where  details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and  accounthead = '672.06') >0  or  EMPSHARECONTRIBUTION -(select sum(debit) - sum(credit) from  cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid  is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') >0) and form.PENSIONNO like ?  and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR' order by appDt ";
	String fsaNotiQuery  = " select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, " +
	"form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt, NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select perinfo.pensionno, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_personal_info perinfo, (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and deleteflag = 'N' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'ARREAR') pens where perinfo.pensionno = pens.pensionno ) info where info.pensionno = form.pensionno and VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG != 'Y' and form.nstype = 'ARREAR' union select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt, NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select perinfo.pensionno, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_personal_info perinfo, (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') = 'Y' and deleteflag = 'N' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'ARREAR') pens where perinfo.pensionno = pens.pensionno ) info where info.pensionno = form.pensionno and VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and (EMPSHARESUBSCRIPITION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '672.06') > 0 or EMPSHARECONTRIBUTION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') > 0) and form.PENSIONNO like ? and form.DELETEFLAG != 'Y' and form.nstype = 'ARREAR' order by appDt ";
 
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
			 
			DBUtility.closeConnection(rs, st, null);
		}
		return advSeqVal;
	}
	public ArrayList searchAdvanceForNotifications(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("NotificationsDAO::searchAdvanceForNotifications"
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
				unitName = advanceSearchBean.getLoginUnitName();

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");

			  if ((advanceSearchBean.getFormName().equals("CPFRecommendation"))||(advanceSearchBean.getFormName().equals("CPFApproval"))) {

				selectQuery = this.buildSearchQueryForNewAdvancesForRectoApproved(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);  
			} else {
				selectQuery = this.buildSearchQueryForNewAdvances(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);
			}
			log.info("NotificationsDAO::searchAdvanceForNotifications" + selectQuery);
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
	public String buildSearchQueryForNewAdvances(AdvanceSearchBean searchBean,
			String frmname, String unitName) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",profilecondition="";
		profileName=searchBean.getProfileName();
		if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
			profilecondition="";			 
		}else{ 
			profilecondition="AND EAF.USERNAME='"+searchBean.getUserName()+"'";
			 
		}
		
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,EMPFID.DESEGNATION AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N'"
				+ " AND CHKLISTFLAG='N' "+profilecondition; 
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

	 
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		} 
		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")			 
				&& searchBean.getAdvanceType().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);		 
		dynamicQuery = query.toString();

		return dynamicQuery;

	}
	public String buildSearchQueryForNewAdvancesForRectoApproved(AdvanceSearchBean searchBean,
			String frmname, String unitName) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",stationCond="",userSpecCond="",accTypeCond="";
		int transcode=0;
		profileName=searchBean.getProfileName();
		if(profileName.equals("R") && (!searchBean.getLoginRegion().equals("CHQIAD")) ){
			accTypeCond="AND NVL(EAF.ACCOUNTTYPE,'RAU') LIKE 'RAU'";
		}
		sqlQuery = "SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,EMPFID.DESEGNATION AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,"
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N' "+accTypeCond;

		 
	 	if (frmname.equals("CPFRecommendation")){
			sqlQuery += " AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND FINALTRANSSTATUS is NOT NULL  AND FINALTRANSSTATUS='N' AND ((EAF.ADVANCETRANSSTATUS ='A') OR (EAF.ADVANCETRANSSTATUS ='R'))";
	 		transcode=2;
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
		/*if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
			dynamicQuery = query.toString();
		}else{ */
			if(profileName.equals("U") || searchBean.getLoginRegion().equals("CHQIAD")){
				stationCond="  AND LOWER(AIRPORTCODE) like '"+searchBean.getLoginUnitCode().toLowerCase().trim()+"'";
			}else{
				stationCond="";
			}
			if(frmname.equals("CPFApproval")){
				if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE     LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'   "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
				}else{
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE     LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
				
				}
			}else if(frmname.equals("CPFApproved")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   APPROVEDBY = '"+searchBean.getUserId()+"'   AND   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
		 	
			}else if (frmname.equals("CPFRecommendation")){
				if(profileName.equals("C") || profileName.equals("S")||profileName.equals("A")){
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, "
				+ "    ((SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'     "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') MINUS (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'  "+stationCond+" "
	            +" AND TRANSCD>= '"+(transcode+1)+"'))  TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
				
				}else{				
				userSpecCond = "SELECT ADVANCEFORM.*   FROM("+query+") ADVANCEFORM, "
				+ "    ((SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE   LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD= '"+transcode+"') MINUS (SELECT  CPFPFWTRANSID  ,PENSIONNO   FROM EPIS_ADVANCES_TRANSACTIONS   WHERE APPROVEDBY = '"+searchBean.getUserId()+"' AND  LOWER(PURPOSETYPE) = '"+searchBean.getAdvanceType().toLowerCase()+"'    AND LOWER(REGION) like '"+searchBean.getLoginRegion().toLowerCase().trim()+"' "+stationCond+" "
	            +" AND TRANSCD>= '"+(transcode+1)+"'))  TRANS   WHERE ADVANCEFORM.PENSIONNO =TRANS.PENSIONNO AND ADVANCEFORM.ADVANCETRANSID= TRANS.CPFPFWTRANSID";
				}
			}else{
				//for Approval Case
				userSpecCond =  query.toString();;
			}
			dynamicQuery = userSpecCond;
		 
		return dynamicQuery;

	}
	public ArrayList searchAdvanceInCashBookEntries(AdvanceSearchBean advanceSearchBean) {

		Connection con = null;
		Statement st = null;
		ArrayList searchList = new ArrayList();
		AdvanceSearchBean searchBean = null;
		log.info("NotificationsDAO::searchAdvanceInCashBookEntries"
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
				unitName = advanceSearchBean.getLoginUnitName();

			if (region.equals("-"))
				advanceSearchBean.setLoginRegion("");

			  selectQuery = this.buildSearchQueryForCashBookApprovals(
						advanceSearchBean, advanceSearchBean.getFormName(),
						unitName);  
			 
			log.info("NotificationsDAO::searchAdvanceInCashBookEntries" + selectQuery);
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
				
				
				if (rs.getString("AIRPORTCODE") != null) {
					searchBean.setStation(rs
							.getString("AIRPORTCODE"));
				}else{
					searchBean.setStation("");
				}
				if (rs.getString("VOUCHERNO") != null) {
					searchBean.setVoucherNo(rs
							.getString("VOUCHERNO"));
				}else{
					searchBean.setVoucherNo("");
				}
				searchList.add(searchBean);
			}
			log.info("searchLIst" + searchList.size());
		} catch (SQLException e) {
			log.printStackTrace(e);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			DBUtility.closeConnection(null, st, con);
		}
		return searchList;

	}
	
	public String buildSearchQueryForCashBookApprovals(AdvanceSearchBean searchBean,
			String frmname, String unitName) { 
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", orderBy = "", sqlQuery = "",profileName="",stationCond="",userSpecCond="",accTypeCond="";
		int transcode=0;
		profileName=searchBean.getProfileName();
		if(profileName.equals("R") && (!searchBean.getLoginRegion().equals("CHQIAD")) ){
			accTypeCond="AND NVL(EAF.ACCOUNTTYPE,'RAU') LIKE 'RAU'";
		}
		sqlQuery = " SELECT EMPFID.EMPLOYEENO AS EMPLOYEENO,EMPFID.DATEOFBIRTH as DATEOFBIRTH,EMPFID.EMPLOYEENAME As EMPLOYEENAME,EMPFID.DEPARTMENT AS ,EMPFID.DESEGNATION AS DESEGNATION,"
				+ "EAF.ADVANCETRANSID AS ADVANCETRANSID,EAF.ADVANCETRANSDT AS ADVANCETRANSDT,EAF.FINALTRANSSTATUS AS FINALTRANSSTATUS, EAF.ADVANCETYPE AS ADVANCETYPE,EAF.PURPOSETYPE AS PURPOSETYPE, EAF.PURPOSEOPTIONTYPE  AS PURPOSEOPTIONTYPE,"
				+ "EAF.VERIFIEDBY AS VERIFIEDBY,EAF.REQUIREDAMOUNT AS REQUIREDAMOUNT,EAF.ADVANCETRANSSTATUS AS ADVANCETRANSSTATUS, EAF.PARTYNAME  AS PARTYNAME, EAF.PARTYADDRESS AS PARTYADDRESS, EAF.LOD AS LOD,EAF.SANCTIONDATE AS SANCTIONDATE,EAF.PAYMENTINFO AS PAYMENTINFO,EAF.AIRPORTCODE AS AIRPORTCODE, "
				+ "EAF.CHKWTHDRWL  AS CHKWTHDRWL,EAF.TRNASMNTHEMOLUMENTS AS TRNASMNTHEMOLUMENTS,EAF.TOTALINATALLMENTS AS TOTALINATALLMENTS,EAF.CHKLISTFLAG AS CHKLISTFLAG,EMPFID.PENSIONNO,"
				+ "EMPFID.DATEOFJOINING AS DATEOFJOINING,EMPFID.FHNAME AS FHNAME FROM EMPLOYEE_PERSONAL_INFO EMPFID,EMPLOYEE_ADVANCES_FORM EAF WHERE EAF.PENSIONNO=EMPFID.PENSIONNO AND EAF.DELETEFLAG='N' "
				+ "AND  CHKLISTFLAG='Y' AND  EAF.VERIFIEDBY is NOT NULL AND FINALTRANSSTATUS='A'  AND EAF.ADVANCETRANSSTATUS ='A'"+accTypeCond;
  	
		 
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
 
		 
		if (!searchBean.getAdvanceType().equals("")) {
			whereClause.append(" LOWER(EAF.ADVANCETYPE) like'%"
					+ searchBean.getAdvanceType().toLowerCase() + "%'");
			whereClause.append(" AND ");
		}
	 
		query.append(sqlQuery);
		if (unitName.equals("") && searchBean.getLoginRegion().equals("")				 
				&& searchBean.getAdvanceType().equals("")) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}
		orderBy = "ORDER BY ADVANCETRANSID desc,ADVANCETRANSDT desc";
		query.append(orderBy);
		log.info("====searchBean.getLoginRegion()======"+searchBean.getLoginRegion()+"==frmname==="+frmname);
		 
		
		dynamicQuery = "SELECT ADVANCEFORM.*,CASHBK.VOUCHERNO AS VOUCHERNO  FROM("+query+") ADVANCEFORM, "
			+" (SELECT  EMP_PARTY_CODE,APPROVEDDDT,VOUCHERNO FROM CB_VOUCHER_INFO CVI  WHERE   CVI.APPROVAL='Y' AND CVI.OTHERMODULELINK='"+searchBean.getAdvanceType()+"' AND CVI.EMP_PARTY_CODE IS NOT NULL)"
			+" CASHBK   WHERE ADVANCEFORM.PENSIONNO = CASHBK.EMP_PARTY_CODE  AND TO_CHAR(CASHBK.APPROVEDDDT, 'dd-Mon-yyyy') BETWEEN  TO_DATE(TO_CHAR(SYSDATE,'dd-Mon-yyyy'))-5 AND SYSDATE";
		
		
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
}
