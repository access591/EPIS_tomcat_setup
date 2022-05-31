package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.cashbook.NotificationBean;
import com.epis.bean.cashbook.VoucherDetails;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;

public class NotificationsDAO {
	Log log = new Log(NotificationsDAO.class);

	private NotificationsDAO() {

	}

	private static final NotificationsDAO dao = new NotificationsDAO();

	public static NotificationsDAO getInstance() {
		return dao;
	}

	public List getNotifications(String queryType) throws EPISException {
		List notifications = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		NotificationBean info = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement("CPF".equals(queryType)?cpfNotiQueryLoad:"PFW".equals(queryType)?pfwNotiQueryLoad:"FS".equals(queryType)?fsNotiQueryLoad:fsaNotiQuery);
			pst.setString(1,"%");
			pst.setString(2,"%");			
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setAirportCode(rs.getString("airportcode"));
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		return notifications;
	}
	public List getPendingAmt(String queryType)throws EPISException{
		
		List notifications = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		NotificationBean info = null;
		try{
			con = DBUtility.getConnection();
			pst = con.prepareStatement("CPF".equals(queryType)?cpfPending:"PFW".equals(queryType)?pfwPending:"FS".equals(queryType)?fsPending:fsaNotiQuery);
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setAirportCode(rs.getString("airportcode"));
				info.setApprovedAmt(rs.getString("totalamt"));
				info.setPaidAmt(rs.getString("payamt"));
				info.setRemaingAmt(rs.getString("remainingamt"));
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		return notifications;
		
	}

	public VoucherInfo getVoucherDetails(VoucherInfo info) throws EPISException {
		Connection con = null;
		PreparedStatement pst= null;
		ResultSet rs = null;
		try {
			String queryType = info.getTransactionId().substring(info.getTransactionId().indexOf("|")+1);
			info.setTransactionId(info.getTransactionId().substring(0,info.getTransactionId().indexOf("|")));
			con = DBUtility.getConnection();
			pst = con.prepareStatement("CPF".equals(queryType)?cpfQuery:"PFW".equals(queryType)?pfwQuery:"FS".equals(queryType)?fsQuery:fsaQuery);
			pst.setString(1,info.getTransactionId());
			rs = pst.executeQuery();
			if(rs.next()){
				SQLHelper sql = new SQLHelper();
				info.setPreparedDt(rs.getString("appDt"));
				info.setPartyType("E");				
				info.setModule(queryType);			
				info.setEmpPartyCode(rs.getString("PFID"));
				info.setEmployeeName(rs.getString("empName"));
				info.setDesignation(rs.getString("DESEGNATION"));
				info.setEmpRegion(rs.getString("REGION"));
				log.info("-----"+rs.getString("AIRPORTCODE"));
				info.setUnitName(rs.getString("AIRPORTCODE"));
				info.setUnitCode(sql.getDescription("employee_unit_master","UNITCODE","UNITNAME",rs.getString("AIRPORTCODE"),DBUtility.getConnection()));
				info.setEmpCpfacno(rs.getString("CPFACNO"));
				info.setOtherTransactionType(queryType);
				info.setPurpose(rs.getString("PURPOSETYPE"));
				info.setNarration(",as per sanction/approval ref no."+rs.getString("transID")+"Dt: "+rs.getString("appDt"));
				
				List voucherDetails = new ArrayList();
				VoucherDetails vdt = null;
				if("CPF".equals(queryType)){
					vdt = new VoucherDetails();					
					vdt.setMonthYear(rs.getString("appmonyear"));
					vdt.setAccountHead("672.03");
					vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","672.03",DBUtility.getConnection()));
					vdt.setDebit(rs.getDouble("APPROVEDAMNT"));
					voucherDetails.add(vdt);
				}else if("PFW".equals(queryType)){
					vdt = new VoucherDetails();
					vdt.setMonthYear(rs.getString("appmonyear"));
					vdt.setAccountHead("672.04");
					vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","672.04",DBUtility.getConnection()));
					vdt.setDebit(rs.getDouble("APPROVEDSUBSCRIPTIONAMT"));
					voucherDetails.add(vdt);
					vdt = new VoucherDetails();
					vdt.setMonthYear(rs.getString("appmonyear"));
					vdt.setAccountHead("672.05");
					vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","672.05",DBUtility.getConnection()));
					vdt.setDebit(rs.getDouble("APPROVEDCONTRIBUTIONAMT"));
					voucherDetails.add(vdt);
				}else {
					vdt = new VoucherDetails();
					vdt.setMonthYear(rs.getString("appmonyear"));
					vdt.setAccountHead("672.06");
					vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","672.06",DBUtility.getConnection()));
					vdt.setDebit(rs.getDouble("EMPSHARESUBSCRIPITION"));
					voucherDetails.add(vdt);
					vdt = new VoucherDetails();
					vdt.setMonthYear(rs.getString("appmonyear"));
					vdt.setAccountHead("672.07");
					vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","672.07",DBUtility.getConnection()));
					vdt.setDebit(rs.getDouble("EMPSHARECONTRIBUTION"));					
					voucherDetails.add(vdt);
					if(rs.getDouble("LESSCONTRIBUTION")!=0)
					{
						
						vdt=new VoucherDetails();
						vdt.setMonthYear(rs.getString("appmonyear"));
						vdt.setAccountHead("673.00");
						vdt.setParticular(sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD","673.00",DBUtility.getConnection()));
						if(rs.getDouble("LESSCONTRIBUTION")<0)
							vdt.setDebit(-rs.getDouble("LESSCONTRIBUTION"));
						else
							vdt.setCredit(rs.getDouble("LESSCONTRIBUTION"));
						voucherDetails.add(vdt);
						
					}
				}
				info.setVoucherDetails(voucherDetails);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		return info;
	}
	
	public List getNotifications(com.epis.bean.cashbookDummy.VoucherInfo editInfo) throws EPISException {
		List notifications = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		NotificationBean info = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(cpfNotiQuery);			
			pst.setString(1,editInfo.getPfid());
			pst.setString(2,editInfo.getPfid());
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setNotificationtype("CPF");
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,null);
		}
		try {
			pst = con.prepareStatement(pfwNotiQuery);
			pst.setString(1,editInfo.getPfid());
			pst.setString(2,editInfo.getPfid());
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setNotificationtype("PFW");
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,null);
		}
		try {
			pst = con.prepareStatement(fsNotiQuery);
			pst.setString(1,editInfo.getPfid());
			pst.setString(2,editInfo.getPfid());
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setNotificationtype("FS");
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,null);
		}
		try {
			pst = con.prepareStatement(fsaNotiQuery);
			pst.setString(1,editInfo.getPfid());
			pst.setString(2,editInfo.getPfid());
			rs = pst.executeQuery();			
			while(rs.next()){
				info = new NotificationBean();
				info.setEmployeeName(rs.getString("empName"));
				info.setPensionNo(rs.getString("PFID"));
				info.setTransactionId(rs.getString("transID"));
				info.setKeyNo(rs.getString("KEYNO"));		
				info.setApprovedDate(rs.getString("appDt"));
				info.setTransactionDate(rs.getString("TRANSDT"));
				info.setNotificationtype("FSA");
				notifications.add(info);
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		return notifications;
	}
	
	String cpfNotiQuery = " select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName," +
			" form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE " +
			" || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, " +
			" (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCES_FORM where FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') != 'Y' and APPROVEDDT >= '10/May/2010') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno )" +
			" info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE " +
			" = 'CPF' and Nvl(RAISEPAYMENT, 'N') != 'Y' and APPROVEDDT >= '10/May/2010' and form.PENSIONNO like ? and form.DELETEFLAG !='Y'  union select " +
			" TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName, form.PENSIONNO PFID," +
			" ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype " +
			" || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCES_FORM where FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') = 'Y' and APPROVEDDT >= '10/May/2010') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno ) info where " +
			" info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and " +
			" Nvl(RAISEPAYMENT, 'N') = 'Y' and APPROVEDDT >= '10/May/2010' and form.APPROVEDAMNT - (select " +
			" sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno " +
			" = info.keyno and info.transid is not null and info.transid = ADVANCETRANSID and accounthead = '672.03'" +
			" ) > 0 and form.PENSIONNO like ? and form.DELETEFLAG !='Y' order by appDt ";
	String pfwNotiQuery = " select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName," +
			" form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE " +
			" || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, " +
			" (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCES_FORM where FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') != 'Y' and APPROVEDDT >= '10/May/2010') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno ) info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE " +
			" = 'PFW' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Apr/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG !='Y' union select TO_CHAR(" +
			" ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, form.PENSIONNO PFID, " +
			" ADVANCETRANSID transID, to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE || '/' || purposetype " +
			" || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCES_FORM where FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') = 'Y' and APPROVEDDT >= '10/May/2010') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno ) info where " +
			" info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'PFW' and Nvl(" +
			" RAISEPAYMENT, 'N') = 'Y' and APPROVEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Apr/2011' end ) and (APPROVEDSUBSCRIPTIONAMT - (select " +
			" sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno " +
			" = info.keyno and info.transid is not null and info.transid = ADVANCETRANSID and accounthead = '672.04'" +
			" ) > 0 or  APPROVEDCONTRIBUTIONAMT - (select sum(debit) - sum(credit) from cb_voucher_details details," +
			"  cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and " +
			" info.transid = ADVANCETRANSID and accounthead = '672.05') > 0) and form.PENSIONNO like ? and form.DELETEFLAG !='Y' order by " +
			" appDt ";
	String fsNotiQuery  = " select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, " +
			" form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,NSSANCTIONNO " +
			" KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'NON-ARREAR') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno ) info where  info.pensionno = form.pensionno and VERIFIEDBY " +
			" = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT " +
			" >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR' union select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, " +
			" form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt, " +
			" NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select validat.monthyear,validat.pensionno pensionno,validat.airportcode valairportcode, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_pension_validate validat, employee_personal_info perinfo,(select max(monthyear) last_drawn_salary_Date, val.pensionno pensionno from employee_pension_validate val where val.pensionno in (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'NON-ARREAR') group by val.pensionno) pens where monthyear = last_drawn_salary_Date and perinfo.pensionno = validat.pensionno and validat.pensionno = pens.pensionno ) info where " +
			" info.pensionno = form.pensionno and VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' " +
			" and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and ( EMPSHARESUBSCRIPITION -" +
			" (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where " +
			" details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and " +
			" accounthead = '672.06') >0  or  EMPSHARECONTRIBUTION -(select sum(debit) - sum(credit) from " +
			" cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid " +
			" is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') >0) and form.PENSIONNO like ? " +
			" and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR' order by appDt ";
	
	String fsaNotiQuery  = " select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, " +
			"form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt, NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select perinfo.pensionno, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_personal_info perinfo, (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and deleteflag = 'N' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'ARREAR') pens where perinfo.pensionno = pens.pensionno ) info where info.pensionno = form.pensionno and VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG != 'Y' and form.nstype = 'ARREAR' union select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName, form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt, NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, (select perinfo.pensionno, perinfo.airportcode airportcode, perinfo.employeename employeename from employee_personal_info perinfo, (select pensionno from EMPLOYEE_ADVANCE_NOTEPARAM where VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') = 'Y' and deleteflag = 'N' and NSSANCTIONEDDT >= '10/May/2010' and nstype = 'ARREAR') pens where perinfo.pensionno = pens.pensionno ) info where info.pensionno = form.pensionno and VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and (EMPSHARESUBSCRIPITION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '672.06') > 0 or EMPSHARECONTRIBUTION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') > 0) and form.PENSIONNO like ? and form.DELETEFLAG != 'Y' and form.nstype = 'ARREAR' order by appDt ";
	
	String cpfQuery = "select info.employeename empName,info.CPFACNO,info.DESEGNATION,nvl(form.region,info.REGION)REGION,nvl(form.airportcode,info.AIRPORTCODE)AIRPORTCODE,PURPOSETYPE,ADVANCETRANSID transID," +
			" APPROVEDAMNT-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info " +
			" info where details.keyno = info.keyno and info.transid is not null and info.transid = ADVANCETRANSID " +
			" and accounthead = '672.03'),0) APPROVEDAMNT,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(" +
			" APPROVEDDT,'dd/Mon/YYYY') appDt,to_char(APPROVEDDT,'Mon/YYYY') appmonyear,ADVANCETYPE||'/'||" +
			" purposetype||'/'||ADVANCETRANSID KEYNO from EMPLOYEE_ADVANCES_FORM form,employee_personal_info " +
			" info where info.pensionno=form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE='CPF' and  " +
			" ADVANCETRANSID = ? order by APPROVEDDT ";
	String pfwQuery = "select info.employeename empName,info.CPFACNO,info.DESEGNATION,nvl(form.region,info.REGION)REGION,nvl(form.airportcode,info.AIRPORTCODE)AIRPORTCODE,PURPOSETYPE,ADVANCETRANSID transID," +
			" APPROVEDSUBSCRIPTIONAMT-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details," +
			" cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid " +
			" = ADVANCETRANSID and accounthead = '672.04'),0) APPROVEDSUBSCRIPTIONAMT,APPROVEDCONTRIBUTIONAMT-Nvl(" +
			" (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where " +
			" details.keyno = info.keyno and info.transid is not null and info.transid = ADVANCETRANSID and" +
			" accounthead = '672.05'),0)APPROVEDCONTRIBUTIONAMT,form.PENSIONNO PFID,ADVANCETRANSID transID," +
			" to_char(APPROVEDDT,'dd/Mon/YYYY') appDt,to_char(APPROVEDDT,'Mon/YYYY') appmonyear,ADVANCETYPE||'/'||" +
			" purposetype||'/'||ADVANCETRANSID KEYNO from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info " +
			" where info.pensionno=form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE='PFW' and " +
			" ADVANCETRANSID = ?  order by APPROVEDDT ";
	String fsQuery  = "select info.employeename empName,info.CPFACNO,info.DESEGNATION,nvl(form.region,info.REGION)REGION,'' PURPOSETYPE," +
			" nvl(form.airportcode,info.AIRPORTCODE)AIRPORTCODE,EMPSHARESUBSCRIPITION-Nvl((select sum(debit) - sum(credit) from cb_voucher_details" +
			" details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and " +
			" info.transid = NSSANCTIONNO and accounthead = '672.06'),0) EMPSHARESUBSCRIPITION," +
			" EMPSHARECONTRIBUTION-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, " +
			" cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and " +
			" info.transid = NSSANCTIONNO and accounthead = '672.07'),0)EMPSHARECONTRIBUTION,(case when nvl(LESSCONTRIBUTION,0)<0 then Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '673.00'),0)+nvl(LESSCONTRIBUTION,0)  else nvl(LESSCONTRIBUTION,0)-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '673.00'),0) end)LESSCONTRIBUTION,form.PENSIONNO " +
			" PFID,NSSANCTIONNO transID,to_char(NSSANCTIONEDDT,'dd/Mon/YYYY') appDt,to_char(NSSANCTIONEDDT," +
			" 'Mon/YYYY') appmonyear,NSSANCTIONNO KEYNO from EMPLOYEE_ADVANCE_NOTEPARAM form,employee_personal_info " +
			" info where info.pensionno=form.pensionno and VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'" +
			" and  NSSANCTIONNO = ?  order by NSSANCTIONEDDT ";
	String fsaQuery  = "select info.employeename empName,info.CPFACNO,info.DESEGNATION,nvl(form.region,info.REGION)REGION,'' PURPOSETYPE," +
		" nvl(form.airportcode,info.AIRPORTCODE)AIRPORTCODE,EMPSHARESUBSCRIPITION-Nvl((select sum(debit) - sum(credit) from cb_voucher_details" +
		" details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and " +
		" info.transid = NSSANCTIONNO and accounthead = '672.06'),0) EMPSHARESUBSCRIPITION," +
		" EMPSHARECONTRIBUTION-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, " +
		" cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and " +
		" info.transid = NSSANCTIONNO and accounthead = '672.07'),0)EMPSHARECONTRIBUTION,(case when nvl(LESSCONTRIBUTION,0)<0 then Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '673.00'),0)+nvl(LESSCONTRIBUTION,0)  else nvl(LESSCONTRIBUTION,0)-Nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and accounthead = '673.00'),0) end)LESSCONTRIBUTION, form.PENSIONNO " +
		" PFID,NSSANCTIONNO transID,to_char(NSSANCTIONEDDT,'dd/Mon/YYYY') appDt,to_char(NSSANCTIONEDDT," +
		" 'Mon/YYYY') appmonyear,NSSANCTIONNO KEYNO from EMPLOYEE_ADVANCE_NOTEPARAM form,employee_personal_info " +
		" info where info.pensionno=form.pensionno and VERIFIEDBY = 'FINANCE,SRMGRREC,DGMREC,APPROVED'" +
		" and  NSSANCTIONNO = ?  order by NSSANCTIONEDDT ";
	String cpfNotiQueryLoad=" select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,perinfo.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info perinfo where form.pensionno=perinfo.pensionno  and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= '10/May/2010' and form.PENSIONNO like ? and  form.DELETEFLAG != 'Y' and form.APPROVEDAMNT>0 union select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION || '--' || form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, employee_personal_info info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') = 'Y' and  APPROVEDDT >= '10/May/2010' and  form.APPROVEDAMNT>0 and  form.APPROVEDAMNT - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID and  accounthead = '672.03') > 0.5 and   form.PENSIONNO like ? and form.DELETEFLAG != 'Y' order by appDt ";
	String pfwNotiQueryLoad=" select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO, form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= (case when(form.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Apr/2011' end) and form.PENSIONNO like  ? and  form.DELETEFLAG != 'Y'   union  select TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info  where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') = 'Y' and  APPROVEDDT >=(case when(info.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else  '01/Apr/2011' end) and (APPROVEDSUBSCRIPTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and info.transid = ADVANCETRANSID and  accounthead = '672.04') > 0 or  APPROVEDCONTRIBUTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info  where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID and  accounthead = '672.05') > 0) and  form.PENSIONNO like ? and form.DELETEFLAG != 'Y'   order by appDt ";
	String fsNotiQueryLoad=" select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,NSSANCTIONNO  KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form,employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY  = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT  >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and form.PENSIONNO like ? and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR'  union select TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,  NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'  and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and ( EMPSHARESUBSCRIPITION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where  details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and  accounthead = '672.06') >0  or  EMPSHARECONTRIBUTION -(select sum(debit) - sum(credit) from  cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid  is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') >0) and form.PENSIONNO like ?  and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR' order by appDt ";
	String cpfPending=" select form.approvedamnt totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0) payamt,(form.approvedamnt-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0))remainingamt,TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,perinfo.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info perinfo where form.pensionno=perinfo.pensionno  and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= '10/May/2010'  and  form.DELETEFLAG != 'Y' and form.approvedamnt>0 union select form.approvedamnt totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0) payamt,(form.approvedamnt-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0))remainingamt,TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION || '--' || form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCES_FORM form, employee_personal_info info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and ADVANCETYPE = 'CPF' and Nvl(RAISEPAYMENT, 'N') = 'Y' and  APPROVEDDT >= '10/May/2010' and  form.APPROVEDAMNT - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID and  accounthead = '672.03') > 0.50  and form.DELETEFLAG != 'Y' order by appDt ";
	String pfwPending=" select form.approvedamnt totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0) payamt,(form.approvedamnt-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0))remainingamt,TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt, ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO, form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') != 'Y' and  APPROVEDDT >= (case when(form.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Apr/2011' end)  and  form.DELETEFLAG != 'Y'   union  select form.approvedamnt totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0) payamt,(form.approvedamnt-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID ),0))remainingamt,TO_CHAR(ADVANCETRANSDT, 'dd-Mon-yyyy') TRANSDT,info.employeename empName,form.PENSIONNO PFID,ADVANCETRANSID transID,to_char(APPROVEDDT, 'dd/Mon/YYYY') appDt,ADVANCETYPE || '/' || purposetype || '/' || ADVANCETRANSID KEYNO,form.REGION || '--' || form.AIRPORTCODE airportcode  from EMPLOYEE_ADVANCES_FORM form,employee_personal_info info  where info.pensionno = form.pensionno and FINALTRANSSTATUS = 'A' and  ADVANCETYPE = 'PFW' and Nvl(RAISEPAYMENT, 'N') = 'Y' and  APPROVEDDT >=(case when(info.airportCode in ('CHQ', 'OPERATIONAL OFFICE','CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else  '01/Apr/2011' end) and (APPROVEDSUBSCRIPTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and info.transid = ADVANCETRANSID and  accounthead = '672.04') > 0 or  APPROVEDCONTRIBUTIONAMT - (select sum(debit) - sum(credit)  from cb_voucher_details details, cb_voucher_info info  where details.keyno = info.keyno and  info.transid is not null and  info.transid = ADVANCETRANSID and  accounthead = '672.05') > 0)  and form.DELETEFLAG != 'Y'   order by appDt ";
	String fsPending="select form.EMPSHARECONTRIBUTION totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = NSSANCTIONNO ),0) payamt,(form.EMPSHARECONTRIBUTION-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = NSSANCTIONNO ),0))remainingamt,TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,NSSANCTIONNO  KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form,employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY  = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED' and Nvl(RAISEPAYMENT, 'N') != 'Y' and NSSANCTIONEDDT  >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end )  and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR'  union select form.EMPSHARECONTRIBUTION totalamt,nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = NSSANCTIONNO ),0) payamt,(form.EMPSHARECONTRIBUTION-nvl((select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and  info.transid is not null and  info.transid = NSSANCTIONNO ),0))remainingamt,TO_CHAR(TRANSDT, 'dd-Mon-yyyy') TRANSDT, info.employeename empName,  form.PENSIONNO PFID, NSSANCTIONNO transID, to_char(NSSANCTIONEDDT, 'dd/Mon/YYYY') appDt,  NSSANCTIONNO KEYNO,form.REGION||'--'||form.AIRPORTCODE airportcode from EMPLOYEE_ADVANCE_NOTEPARAM form, employee_personal_info info where  info.pensionno = form.pensionno and VERIFIEDBY = 'PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'  and Nvl(RAISEPAYMENT, 'N') = 'Y' and NSSANCTIONEDDT >= (case when (info.airportCode in  ('CHQ', 'OPERATIONAL OFFICE', 'CHQNAD', 'OFFICE COMPLEX')) then '01/May/2010' else '01/Mar/2011' end ) and ( EMPSHARESUBSCRIPITION - (select sum(debit) - sum(credit) from cb_voucher_details details, cb_voucher_info info where  details.keyno = info.keyno and info.transid is not null and info.transid = NSSANCTIONNO and  accounthead = '672.06') >0  or  EMPSHARECONTRIBUTION -(select sum(debit) - sum(credit) from  cb_voucher_details details, cb_voucher_info info where details.keyno = info.keyno and info.transid  is not null and info.transid = NSSANCTIONNO and accounthead = '672.07') >0)   and form.DELETEFLAG !='Y' and form.nstype = 'NON-ARREAR' order by appDt";
	
	
	
}
