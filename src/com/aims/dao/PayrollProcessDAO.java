package com.aims.dao;




import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.epis.utilities.ActivityLog;
import com.epis.utilities.ApplicationException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;

import com.aims.info.payrollprocess.EmpPaySlipInfo;
import com.aims.info.payrollprocess.JournalVoucherInfo;
import com.aims.info.payrollprocess.MonthlyPayrollInfo;
import com.aims.info.payrollprocess.PayProcessInfo;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.payrollprocess.PayrollProcessInfo;
import com.aims.info.payrollprocess.RecoveryDetailsInfo;
import com.aims.info.payrollprocess.SearchMonthlyPayroll;
import com.aims.info.payrollprocess.SearchPayrollProcessInfo;
import com.aims.info.staffconfiguration.EarningMasterInfo;
import com.aims.info.staffconfiguration.EmployeeInfo;

public class PayrollProcessDAO {
	
	
	Properties prop=null;	
	Properties prop1=null;	
	DBAccess db = DBAccess.getInstance();
	SQLHelper sh = new SQLHelper();
	Connection con = null;
	ResultSet rs = null;
	Log log = new Log(PayrollProcessDAO.class);
	
	private static PayrollProcessDAO payrollprocessInstance = new PayrollProcessDAO();
	
	private PayrollProcessDAO() {
		try {
			//db.makeConnection();
		//	con = db.getConnection();
			prop=CommonUtil.getPropsFile(Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,PayrollProcessDAO.class);
			prop1=CommonUtil.getPropsFile(Constants.APPLICATION_CONFIG_PROPERTIES_FILE_NAME,PayrollProcessDAO.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PayrollProcessDAO getInstance() {
		return payrollprocessInstance;
	}
	public void saveMonthlyPayroll(MonthlyPayrollInfo monthlypayroll) throws ApplicationException{
		log.info("PayrollProcessDAO:saveMonthlyPayroll() Entering method()");
		int monthId=0;
		int recCnt = 0;
		try {	
			db.makeConnection();con = db.getConnection();
			log.info("before saving monthly payroll");
			recCnt = db.getRecordCount("Select count(*) from monthlypayroll where PAYROLLMONTHNM='"+monthlypayroll.getPayrollmonthnm()+"' and PAYROLLYEAR='"+monthlypayroll.getPayrollyear()+"'");
			if(recCnt>0){
				throw new ApplicationException("Payroll Month for '"+monthlypayroll.getPayrollmonthnm()+"/"+monthlypayroll.getPayrollyear()+"' has been already Exists");
			}
			monthId = Integer.parseInt(AutoGeneration.getNextCode("monthlypayroll","payrollmonthid",11,con));
			String sSQL ="insert into monthlypayroll(payrollmonthid, payrollmonthnm, payrollyear, payrollfromdt, payrolltodt  ) values ("+monthId+",'"+monthlypayroll.getPayrollmonthnm()+"','"+monthlypayroll.getPayrollyear()+"',to_date('"+monthlypayroll.getPayrollfromdt()+"','yyyy/mm/dd'),to_date('"+monthlypayroll.getPayrolltodt()+"','yyyy/mm/dd'))";
			log.info("-------saveMonthlyPayroll Query----------"+sSQL);	
			db.executeUpdate(sSQL);		
			db.commitTrans();
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		log.info("PayrollProcessDAO:saveMonthlyPayroll() Leaving method()");
	}	
	public SearchMonthlyPayroll searchsearchMonthlyPayroll(SearchMonthlyPayroll monthlypayroll){
		log.info("PayrollProcessDAO:searchsearchMonthlyPayroll()  method()");
		
			ArrayList payrollList = new ArrayList();
			String monthlypayrollcountQuery = "", monthlypayrollSearch = "";
			int st = 0;
			int end = 0;
			int showrec = 5;

			try {
				
				db.makeConnection();con = db.getConnection();
				monthlypayrollcountQuery = "select count(*) as countq from monthlypayroll mp   ";
				//monthlypayrollSearch = "select rownum as limit,mp.payrollmonthid,mp.payrollmonthnm,mp.payrollyear,to_char(mp.payrollfromdt,'dd-mm-yyyy') as payrollfromdt,to_char(mp.payrolltodt,'dd-mm-yyyy') as payrolltodt,prp.processstatus from monthlypayroll mp,payrollprocess prp where mp.payrollmonthid = prp.payrollmonthid";
				monthlypayrollSearch = "select rownum as limit,mp.payrollmonthid,mp.payrollmonthnm,mp.payrollyear,to_char(mp.payrollfromdt,'dd-mm-yyyy') as payrollfromdt,to_char(mp.payrolltodt,'dd-mm-yyyy') as payrolltodt from monthlypayroll mp";

				if (!monthlypayroll.getSearchpayrollmonthnm().equals("")) {
					String q = " and mp.payrollmonthnm like '%"
							+ monthlypayroll.getSearchpayrollmonthnm() + "%'";
					monthlypayrollcountQuery = monthlypayrollcountQuery + q;
					monthlypayrollSearch = monthlypayrollSearch + q;

				}
				if (!monthlypayroll.getSearchpayrollyear().equals("")) {
					String q = " and mp.payrollyear like '%"
							+ monthlypayroll.getSearchpayrollyear() + "%'";
					monthlypayrollcountQuery = monthlypayrollcountQuery + q;
					monthlypayrollSearch = monthlypayrollSearch + q;
				}			
			
				log.info(" monthlypayrollcountQuery " + monthlypayrollcountQuery);
				log.info("monthlypayrollSearch " + monthlypayrollSearch);
				
				int cnt = db.getRecordCount(monthlypayrollcountQuery);
				String count=cnt+"";
				monthlypayroll.setTotolRecords(count);
				log.info("chequeinfo set totalrecords" +count);
			
				
				if (!monthlypayroll.getSortfield().equals("")) {
					log.info("-------------sort field-------------"+monthlypayroll.getSortfield());
					monthlypayrollSearch += " order by " + monthlypayroll.getSortfield() + " "+ monthlypayroll.getSorttype();
				}
				
				if (!monthlypayroll.getNavigation().equals("")) {
					if (monthlypayroll.getNavigation().equals("next")) {
						log.info("nav next ");
						monthlypayroll.setPageNo(monthlypayroll.getPageNo() + 1);
						log.info("set page no "+monthlypayroll.getPageNo());
					}

					if (monthlypayroll.getNavigation().equals("previous"))
						monthlypayroll.setPageNo(monthlypayroll.getPageNo() - 1);
				}
				log.info(" 3 " + (monthlypayroll.getPageNo() > 1));
				if (monthlypayroll.getPageNo() > 1) {
					monthlypayroll.setHasPrevious(true);
					st = (monthlypayroll.getPageNo() - 1) * showrec;
				}

				if (Integer.parseInt(monthlypayroll.getTotolRecords()) > (monthlypayroll
						.getPageNo() * showrec)) {
					monthlypayroll.setHasNext(true);
					end = showrec;
				} else {
					end = Integer.parseInt(monthlypayroll.getTotolRecords())
							- (st - 1);
				}
				if (monthlypayroll.getNavigation() != null) {
					if (monthlypayroll.getNavigation().equals("last")) {
						if (((Integer.parseInt(monthlypayroll.getTotolRecords()) / showrec) > 1)) {
							st = ((Integer.parseInt(monthlypayroll.getTotolRecords()) / showrec) * showrec);
						} else {
							st = showrec;
						}
						end = st
								+ (Integer.parseInt(monthlypayroll.getTotolRecords()) - st);
						monthlypayroll.setHasPrevious(true);
						monthlypayroll.setHasNext(false);
						monthlypayroll.setPageNo((Integer.parseInt(monthlypayroll.getTotolRecords()) / showrec) + 1);
					}
					if (monthlypayroll.getNavigation().equals("first")) {
						monthlypayroll.setPageNo(1);
						monthlypayroll.setHasPrevious(false);
						monthlypayroll.setHasNext(true);
						st = 0;
						end = showrec;
					}
				}

				//monthlypayrollSearch = monthlypayrollSearch + " LIMIT " + st + "," + showrec;
				log.info("sSQL in monthlypayroll DAO is " + monthlypayrollSearch);
				log.info("monthlypayroll.getTotolRecords( "
						+ monthlypayroll.getTotolRecords());
				if (Integer.parseInt(monthlypayroll.getTotolRecords()) > (showrec + (st))) {
					end = showrec + st;
				} else {
					end = Integer.parseInt(monthlypayroll.getTotolRecords());
				}
			
				monthlypayroll.setHeading(prop.getProperty("common.dispsearchrecname") + (st + 1) + " "+prop.getProperty("common.to")
						+ end + " "+prop.getProperty("common.of")+ monthlypayroll.getTotolRecords());
						
				//rs = db.getRecordSet(monthlypayrollSearch);
				rs = db.getRecordSet(" Select * from ( "+monthlypayrollSearch+" ) where limit >"+st+" and limit <="+end);
				
				while(rs.next()){
					SearchMonthlyPayroll searpayrollinfo = new SearchMonthlyPayroll();
					searpayrollinfo.setSearchpayrollmonthid(rs.getString("payrollmonthid"));
					log.info("searpayrollinfo.getSearchpayrollmonthid() : "+searpayrollinfo.getSearchpayrollmonthid());
					searpayrollinfo.setSearchpayrollmonthnm(rs.getString("payrollmonthnm"));					
					searpayrollinfo.setSearchpayrollyear(rs.getString("payrollyear"));							
					searpayrollinfo.setSearchpayrollfromdt(rs.getString("payrollfromdt"));					
					searpayrollinfo.setSearchpayrolltodt(rs.getString("payrolltodt"));					
					//searpayrollinfo.setSearchprocessstatus(rs.getString("processstatus"));				
					payrollList.add(searpayrollinfo);
				
				}
				db.closeRs();
				log.info("Monthly payroll search list size " + payrollList.size());
				monthlypayroll.setSearchList(payrollList);
				log.info("before tx committed in search");
				
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				try {
					db.closeRs();
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}		
				
			log.info("before returning pageno"+monthlypayroll.getPageNo());
			log.info("before returning searchfield"+monthlypayroll.getSortfield());
			log.info("PayrollProcessDAO:searchsearchMonthlyPayroll() Leaving method()");
			return monthlypayroll;
		
	}
	
	public List searchPayrollProcess(SearchPayrollProcessInfo payprocessinfo){
		ArrayList payrollList = new ArrayList();
		//String searchpayprocessqry = "select prp.PAYTRANSID,prp.PAYROLLMONTHID,prp.STAFFCTGRYCD,stc.STAFFCTGRYNM,prp.DISCIPLINECD,std.DISCIPLINENM,mp.PAYROLLMONTHNM,mp.PAYROLLYEAR,prp.PROCESSSTATUS from PAYROLLPROCESS prp,MONTHLYPAYROLL mp,STAFFCATEGORY stc,STAFFDISCIPLINE std where prp.PAYROLLMONTHID = mp.PAYROLLMONTHID and prp.STAFFCTGRYCD = stc.STAFFCTGRYCD and prp.DISCIPLINECD=std.DISCIPLINECD order by PROCESSSTARTDATE desc ";
		StringBuffer searchpayprocessqry = new StringBuffer();
		searchpayprocessqry.append("select prp.PAYTRANSID,prp.PAYROLLMONTHID,mp.PAYROLLMONTHNM,mp.PAYROLLYEAR,prp.PROCESSSTATUS,STATIONCD from PAYROLLPROCESS prp,MONTHLYPAYROLL mp where prp.PAYROLLMONTHID = mp.PAYROLLMONTHID ");
		if(!"".equals(payprocessinfo.getSearchPayrollMonth()))
			searchpayprocessqry.append(" and prp.PAYROLLMONTHID='"+payprocessinfo.getSearchPayrollMonth()+"'");
		if(!"".equals(payprocessinfo.getStation()))
			searchpayprocessqry.append(" and STATIONCD='"+payprocessinfo.getStation()+"'");
		
		searchpayprocessqry.append(" order by PROCESSSTARTDATE desc");
		log.info("searchpayprocessqry "+searchpayprocessqry.toString());
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(searchpayprocessqry.toString());
			while(rs.next()){
				SearchPayrollProcessInfo ppinfo = new SearchPayrollProcessInfo();
				ppinfo.setPayTransId(rs.getString("PAYTRANSID"));
				ppinfo.setPayrollMonth(rs.getString("PAYROLLMONTHNM")+"-"+rs.getString("PAYROLLYEAR"));
				//ppinfo.setDiscplineName(rs.getString("DISCIPLINENM"));
				
				//ppinfo.setStaffCategory(rs.getString("STAFFCTGRYNM"));
				ppinfo.setStation(rs.getString("STATIONCD"));
				log.info("payrollmonth "+ppinfo.getPayrollMonth()+" scg "+ppinfo.getStaffCategory());
				ppinfo.setProcessStatus(rs.getString("PROCESSSTATUS"));
				payrollList.add(ppinfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< searchPayrollProcess Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return payrollList;
	}
	
	public List viewPaySlip(PaySlipInfo psinfo){
		List empPayList = new ArrayList();
		List list = new ArrayList();
		Map mp = new HashMap();
		Hashtable ht = new Hashtable();
		EmployeeInfo empinfo = new EmployeeInfo();
		EmpPaySlipInfo empPayInfo = new EmpPaySlipInfo();
		PaySlipInfo psinfoo = new PaySlipInfo();
		boolean flag = false;
		SQLHelper sq = new SQLHelper();
		try{
			db.makeConnection();con = db.getConnection();
			StringBuffer empSQL = new StringBuffer();
			empSQL.append("Select emplnumber, empNo,EMPLOYEENAME empname,get_description('upper(DESIGNATIONNM)','staffdesignation sds','sds.DESIGNATIONCD='''||emp.DESIGNATIONCD||'''')DESIGNATIONNM,get_description('STAFFCTGRYNM','staffcategory st','st.STAFFCTGRYCD='''||emp.STAFFCTGRYCD||'''')STAFFCTGRYNM,get_description('upper(DISCIPLINENM)','staffdiscipline sd','sd.DISCIPLINECD='''||emp.DISCIPLINECD||'''')DISCIPLINENM,PENSIONNO CPFNO,BACNO,emp.STAFFCTGRYCD,( select MINSCALE|| '-' ||MAXSCALE from payscalemaster pay where emp.DESIGNATIONCD=pay.DESIGNATIONCD) payscale,PAN" );				
					
			//if(!psinfo.getEmpNo().equals("All")){
			log.info("psinfo.getPaybillcd() "+psinfo.getPaybillcd()+" type "+psinfo.getType()+"Month"+psinfo.getMonth()+"Year"+psinfo.getYear());
			
			if(!psinfo.getEmpNo().equals("")){
				empSQL.append(",(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL  where PAYROLLMONTHID  = (select distinct eolmonth from lop where empno = '"+psinfo.getEmpNo()+"' and MONTH = (SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"')))maxdays,  ");
				empSQL.append("(select sum(eol)||':'||sum(hpl)||':'||sum(uaa)  from lop where empno = '"+psinfo.getEmpNo()+"' and month=(Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"'))eol ");
				empSQL.append(",(select BANKNM from BANKMASTER where BANKID = emp.BANKID)bankname");
				empSQL.append(",(select PAYSLIPNO EMPINFOPAYROLLCD from empinfopayroll where payrollmonthid = ( SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"') and empno= '"+psinfo.getEmpNo()+"')psno ");
				
				empSQL.append(" from  EmployeeInfo emp where emp.empno is not null ");
				empSQL.append(" and  EMPNO = '"+psinfo.getEmpNo()+"'");				
			}
			//log.info("psinfo.getEmpNo() "+psinfo.getEmpNo()+" ");
			if(psinfo.getEmpNo()== null || psinfo.getEmpNo().equals("")){
			
				if(!psinfo.getPaybillcd().trim().equals("")){
					empSQL.append(",(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL  where PAYROLLMONTHID  = (select distinct eolmonth from lop where empno = emp.empno and MONTH = (SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"')))maxdays,  ");
					empSQL.append("(select sum(eol)||':'||sum(hpl)||':'||sum(uaa)  from lop where empno = emp.empno and month=(Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"'))eol ");
					empSQL.append(",(select BANKNM from BANKMASTER where BANKID = emp.BANKID)bankname");
					empSQL.append(",   (select PAYSLIPNO EMPINFOPAYROLLCD from empinfopayroll where payrollmonthid = ( SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"') and empno = emp.empno)psno ");
					
					empSQL.append(" from  EmployeeInfo emp where emp.empno is not null ");
					empSQL.append(" and emp.paybillcd = '"+psinfo.getPaybillcd()+"' ");
				}
			}
			if(psinfo.getStation() !=null && !psinfo.getStation().equals("")){
				empSQL.append(" and emp.STATIONCD ='"+psinfo.getStation()+"' ");
			}
			empSQL.append("");
			/*if(psinfo.getStation() !=null && !psinfo.getStation().equals("")&&psinfo.getEmpNo().equals("All")){
				if(!psinfo.getPayrollmonthid().equals("")) {
					empSQL.append(" and pp.STATIONCD ='"+psinfo.getStation()+"' ");
					empSQL.append(" and emp.STATIONCD = pp.STATIONCD ");
				}else if(!psinfo.getFyearcd().equals("")) {
					empSQL.append(" and emp.STATIONCD = '"+psinfo.getStation()+"' ");
				}else {
					empSQL.append(",payrollprocess pp where pp.STATIONCD = '"+psinfo.getStation()+"' and pp.PAYROLLMONTHID in (Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"') ");
					empSQL.append(" and emp.STATIONCD = pp.STATIONCD ");
				}
				
			}*/
			/*if(!psinfo.getEmpNo().equals("All")){
				if(!psinfo.getStation().equals("") || !psinfo.getPayrollmonthid().equals("")){
					empSQL.append(" and EMPNO = '"+psinfo.getEmpNo()+"'");
				}else {
					empSQL.append(" where EMPNO = '"+psinfo.getEmpNo()+"'");
				}
			}*/
			if(!psinfo.getDiscipline().equals("")){
				empSQL.append(" and emp.DISCIPLINECD = '"+psinfo.getDiscipline()+"' ");
			}
			
			/*if(!psinfo.getStaffCategory().equals("")){
				empSQL.append(" and emp.STAFFCTGRYCD = '"+psinfo.getStaffCategory()+"' ");
			}*/
			
			
			log.info("---empSQL is "+empSQL.toString());
			//System.exit(1);
			ResultSet rss = db.getRecordSet(empSQL.toString());
			HashMap hm= new HashMap();
			while(rss.next()){
				int cnt = 0;
				empPayInfo = new EmpPaySlipInfo();
				empinfo = new EmployeeInfo();
				List earnningList = new ArrayList();
				List deducList = new ArrayList();
				List perkList = new ArrayList();
				List advList = new ArrayList();
				List rdadvlist = new ArrayList();
				List advlist = new ArrayList();
				//List miscperkList = new ArrayList();
				List recoveryList = new ArrayList();
				String dob = sh.getDescription("employeepersonalinfo","to_char(empdob,'dd-Mon-yyyy') EMPDOB","EMPNO",psinfo.getEmpNo(),con);
				empinfo.setDateofbirth(dob);
				
				String doj = sh.getDescription("employeepersonalinfo","to_char(EMPDOJ,'dd-Mon-yyyy') EMPDOJ","EMPNO",psinfo.getEmpNo(),con);
				empinfo.setDateofjoin(doj);
				
				String pensionoption = sh.getDescription("employeeinfo","nvl(PENSIONOPTION,'-') PENSIONOPTION","EMPNO",psinfo.getEmpNo(),con);
				empinfo.setPensionoption(pensionoption);
				empinfo.setEmpNo(rss.getString("emplnumber"));
				empinfo.setEmpName(rss.getString("empname").toUpperCase());
				empinfo.setEmpDesignation(rss.getString("DESIGNATIONNM"));
				empinfo.setStaffCategoryNm(rss.getString("STAFFCTGRYNM"));
				//empinfo.setEmpDescipline(rss.getString("DISCIPLINENM"));
				empinfo.setPan(rss.getString("PAN"));
				String eolstr = rss.getString("eol");
				int maxdays = rss.getInt("maxdays");
				empinfo.setBankId(rss.getString("bankname"));
				empinfo.setPayslipno(rss.getString("psno"));
				//log.info("eolstr "+eolstr);
				if(eolstr!=null){
					String s[] = eolstr.split(":");
					log.info("s leng "+s.length);
					if(s.length > 2){
						empinfo.setEol(s[0]);
						empinfo.setHpl(s[1]);
						empinfo.setUaa(s[2]);
						int absdays = Integer.parseInt(s[0])+Integer.parseInt(s[1])+Integer.parseInt(s[2]);
						empinfo.setDays((maxdays-absdays)+"");						
					}else{
						empinfo.setEol("");
						empinfo.setHpl("");
						empinfo.setUaa("");
						empinfo.setDays("FULL");
					}
				}
				empinfo.setEmpDescipline("");
				empinfo.setCpfNo(rss.getString("CPFNO"));
				empinfo.setBacno(rss.getString("BACNO"));
				empinfo.setPayscale(rss.getString("payscale"));
				double grossamt = 0.0;
				double deducamt = 0.0;
				double netamt = 0.0;
				double earnTotal = 0.0,deducTotal = 0.0,perkTotal =0.0,miscperkTotal =0.0,recTotal = 0.0,arrTotal=0.0,recoTotal=0.0;;
				//ht.put(rss.getString("empNo")+"1",empinfo);
				/*String sql = "Select PAYROLLMONTHID,(select PAYROLLMONTHNM||'-'||PAYROLLYEAR from monthlypayroll m where m.PAYROLLMONTHID=prs.PAYROLLMONTHID)nm,dt.EARNDEDUCD, sum( AMOUNT)amount,dt.type,EARNDEDUNM,EARNDEDUDESC from payrolldet dt,payrollprocess prs,earndedumaster edm where dt.PAYTRANSID=prs.PAYTRANSID and dt.type not in ('AAI','AAI-BF') and edm.earndeducd=dt.earndeducd ";
				if(!psinfo.getPayrollmonthid().equals("")) {
					sql += " and PAYROLLMONTHID ='"+psinfo.getPayrollmonthid()+"' ";
				}else if(!psinfo.getFyearcd().equals("")) {
					sql += " and PAYROLLMONTHID in (Select mp.PAYROLLMONTHID from monthlypayroll mp where fyearcd='"+psinfo.getFyearcd()+"' ) ";
				}else {
					sql += " and PAYROLLMONTHID in (Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"') ";
				}
				
				sql += " and EMPNO = '"+rss.getString("empNo")+"'  group by dt.earndeducd,dt.type,payrollmonthid,EARNDEDUNM,EARNDEDUDESC,edm.printorder order by  PAYROLLMONTHID,edm.printorder";*/
				
				String sql1 = "SELECT   dt.earndeducd, amount, adjustments adjustments,  dt.TYPE, earndedunm,printorder,prs.PAYROLLMONTHID,nvl(ARREARS,0) ARREARS,nvl(RECOVERIES,0) RECOVERIES  FROM payrolldet dt, payrollprocess prs, earndedumaster edm" +
				" WHERE dt.paytransid = prs.paytransid AND dt.TYPE NOT IN ('AAI', 'AAI-BF')  AND edm.earndeducd = dt.earndeducd and instr((','||join(cursor(select COMBINECDS from PAYSLIPCOMBINECDS))||','),(','||dt.earndeducd||','))<=0 " +
				"  AND payrollmonthid IN (  SELECT mp.payrollmonthid FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"'  AND payrollyear = '"+psinfo.getYear()+"') AND empno = '"+rss.getString("empNo")+"' and (amount<>0 or adjustments<>0)" +
				" union all" +
				" SELECT    psc.id,SUM (amount) amount, SUM (adjustments) adjustments, psc.TYPE, psc.COMBINEDNAME,psc.PRINTORDER,prs.PAYROLLMONTHID,nvl(ARREARS,0) ARREARS,nvl(RECOVERIES,0) RECOVERIES  " +
				" FROM payrolldet dt, payrollprocess prs, earndedumaster edm,PAYSLIPCOMBINECDS psc " +
				" WHERE dt.paytransid = prs.paytransid  AND dt.TYPE NOT IN ('AAI', 'AAI-BF') AND edm.earndeducd = dt.earndeducd  AND payrollmonthid IN (   SELECT mp.payrollmonthid FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"')  AND empno = '"+rss.getString("empNo")+"'  and instr((','||psc.COMBINECDS||','),(','||dt.earndeducd||','))>=1  and (amount<>0 or adjustments<>0) GROUP BY psc.id, psc.type,psc.COMBINEDNAME ,psc.PRINTORDER,prs.PAYROLLMONTHID ,dt.ARREARS,dt.RECOVERIES" +
				"  order by printorder ";
				
				empPayInfo.setMonth(psinfo.getMonth());
				empPayInfo.setYear(psinfo.getYear());
				log.info("sql is "+sql1);
				double advtotal = 0;
				String payrollmomthid = "",name = "";
				//ps = con.prepareStatement(sql);
				//ps.setString(1,rss.getString("empNo"));
				rs = db.getRecordSet(sql1);//ps.executeQuery();
				
				
				log.info(" log.info(psinfo.getType()); "+psinfo.getType());
				while(rs.next()){
					flag = true;
					//log.info(" payrollmomthid "+payrollmomthid+" rs.getString(PAYROLLMONTHID) "+rs.getString("PAYROLLMONTHID"));
					if(!payrollmomthid.equals("") && !payrollmomthid.equals(rs.getString("PAYROLLMONTHID"))){
						
						if(psinfo.getType().equals("PBR")){
							cnt++;
							log.info("---- cnt "+cnt);
							empPayInfo.setPayrollmonthid(name);
							empPayInfo.setCount(cnt);
							empPayInfo.setEmpInfo(empinfo);
							empPayInfo.setPerkList(perkList);
							//empPayInfo.setMiscperkList(miscperkList);
							log.info("PBR report adding advlist1");
							empPayInfo.setRecList(advlist);
							empPayInfo.setEarnList(earnningList);
							empPayInfo.setDeducList(deducList);
							empPayInfo.setAdvtotal(advtotal);
							/*empPayInfo.setEarnTotal(earnTotal);
							 empPayInfo.setDeducTotal(deducTotal);
							 empPayInfo.setPerkTotal(perkTotal);
							 empPayInfo.setRecTotal(recTotal);
							 empPayInfo.setGrossAmt(grossamt);
							 empPayInfo.setDeducAmt(deducamt);
							 empPayInfo.setNetAmt(grossamt-deducamt);*/
							empPayList.add(empPayInfo);
							list = empPayList;
						}
						
						payrollmomthid = rs.getString("PAYROLLMONTHID");
						//name = rs.getString("nm");
						empPayInfo = new EmpPaySlipInfo();
						earnningList = new ArrayList();
						deducList = new ArrayList();
						perkList = new ArrayList();
						recoveryList = new ArrayList();
					}
					
					if(payrollmomthid.equals("")){
						payrollmomthid = rs.getString("PAYROLLMONTHID");
						//name = rs.getString("nm");
					}
					psinfoo = new PaySlipInfo();
					
						//psinfoo.setEarnDeduName(sh.getDescription("EARNDEDUMASTER","EARNDEDUNM","EARNDEDUCD",rs.getString("EARNDEDUCD"),con));
						//psinfoo.setShortName(sh.getDescription("EARNDEDUMASTER","SHORTNAME","EARNDEDUCD",rs.getString("EARNDEDUCD"),con));
						psinfoo.setEarnDeduName(rs.getString("EARNDEDUNM"));
						psinfoo.setShortName(rs.getString("EARNDEDUNM"));
						double amt = rs.getDouble("AMOUNT");
						double adj = rs.getInt("ADJUSTMENTS");
						double arr = rs.getInt("ARREARS");
						double rec = rs.getInt("RECOVERIES");
					
						psinfoo.setAdjustments(rs.getString("ADJUSTMENTS"));
						psinfoo.setAdjustmentsArr(rs.getString("ARREARS"));
						psinfoo.setAdjustmentsRec(rs.getString("RECOVERIES"));
						double famt = amt + (adj);
						if(rs.getString("type").equals("E")||rs.getString("type").equals("PK") || rs.getString("type").equals("PRK")||rs.getString("type").equals("OTA")||rs.getString("type").equals("GPROF")||rs.getString("type").equals("AR")||rs.getString("type").equals("A")||rs.getString("type").equals("MISCADV") ){		
							psinfoo.setAmount(rs.getString("AMOUNT"));
						}else{
							psinfoo.setAmount( new Integer((int)famt)+"");
						}
						
						
						
						
						//psinfoo.setEarnDeduCd(rs.getString(rs.getString("EARNDEDUCD")));
					
					psinfoo.setType(rs.getString("type"));
					if(rs.getString("type").equals("E")||rs.getString("type").equals("PK") || rs.getString("type").equals("PRK")||rs.getString("type").equals("OTA")||rs.getString("type").equals("GPROF")||rs.getString("type").equals("AR")||rs.getString("type").equals("A")||rs.getString("type").equals("MISCADV") ){
						
						if(rs.getString("type").equals("PK") ){
							perkList.add(psinfoo);
						}else{
							earnningList.add(psinfoo);
						}
						/*grossamt+=rs.getDouble("AMOUNT");
						earnTotal +=rs.getDouble("AMOUNT");*/						
						grossamt+=famt;
						earnTotal +=famt;
						arrTotal+=arr;
						recoTotal+=rec;
						
					}
					/*if(rs.getString("type").equals("PK") || rs.getString("type").equals("PRK")){
						//log.info("adding perks");
						perkList.add(psinfoo);
						perkTotal+=rs.getDouble("AMOUNT");
						grossamt+=rs.getDouble("AMOUNT");
						perkTotal+=famt;
						grossamt+=famt;
					}*/
					/*if(rs.getString("type").equals("MISCPK")){
						//log.info("adding perks");
						miscperkList.add(psinfoo);
						miscperkTotal+=rs.getDouble("AMOUNT");
						grossamt+=rs.getDouble("AMOUNT");
					}*/
					if(rs.getString("type").equals("D")||rs.getString("type").equals("OR")||rs.getString("type").equals("CLCRC")||rs.getString("type").equals("MISCREC")|| rs.getString("type").equals("EACPF")||rs.getString("type").equals("OD")|| rs.getString("type").equals("DIES") || rs.getString("type").equals("IT")|| rs.getString("type").equals("ADV") || rs.getString("type").equals("ADVINT") ) {
						if(rs.getString("type").equals("ADV")|| rs.getString("type").equals("ADVINT")){
							advtotal = advtotal +famt;
							if(psinfo.getType().equals("PBR")){
								advlist.add(psinfoo);
							}
						}else{
							deducList.add(psinfoo);
						}
						
						/*deducamt+=rs.getDouble("AMOUNT");
						deducTotal+=rs.getDouble("AMOUNT");*/
						deducamt+=famt;
						deducTotal+=famt;
					}
					/*if(rs.getString("type").equals("OR")||rs.getString("type").equals("CLCRC") ){
						//log.info("adding Other Reecoveries ");
						recoveryList.add(psinfoo);
						recTotal+=rs.getDouble("AMOUNT");
						deducamt+=rs.getDouble("AMOUNT");
						recTotal+=famt;
						deducamt+=famt;
					}*/
					
				}
				
				db.closeRs();
				log.info("psinfo.getType() "+psinfo.getType());
				//log.info("perkList "+perkList.size());
				
				/*ht.put(rss.getString("empNo")+"2",earnningList);
				ht.put(rss.getString("empNo")+"3",deducList);
				mp.put(rss.getString("empNo"),ht);*/
				//	String advqry = "select ead.ADVAMT,ead.AMOUNTRECOVERED,ead.NOOFINSTALLMENTS,ead.RECOVEREDINSTALLMENTS,am.ADVNAME from EMPLOYEEADVANCEREC empadvrec,EMPLOYEEADVANCES ead ,ADVANCESMASTER am  where am.ADVCD = ead.ADVCD and empadvrec.EMPLOYEEADVCD  =  ead.EMPLOYEEADVCD  and  empadvrec.EMPNO = '"+psinfo.getEmpNo()+"' and empadvrec.PAYROLLMONTHID in (Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"')";
				//String advqry = "select nvl(ead.ADVAMT,0)ADVAMT,nvl(ead.AMOUNTRECOVERED,0)AMOUNTRECOVERED,nvl(ead.NOOFINSTALLMENTS,0)NOOFINSTALLMENTS,nvl(ead.RECOVEREDINSTALLMENTS,0)RECOVEREDINSTALLMENTS, nvl(ead.INTRESTAMOUNT, 0) INTRESTAMOUNT,nvl(ead.RECOVEREDINTREST, 0) RECOVEREDINTREST,nvl(ead.INTRESTINSTALLMENTS, 0) INTRESTINSTALLMENTS,nvl(ead.RECOVEREDINTRESTINSTALLMENTS, 0) RECOVEREDINTRESTINSTALLMENTS,nvl(am.ADVNAME,'--')ADVNAME from EMPLOYEEADVANCES ead ,ADVANCESMASTER am  where am.ADVCD = ead.ADVCD(+) and  ead.EMPNO(+) = '"+rss.getString("empNo")+"' ";
				//String advqry = "select nvl(a.ADVAMT, 0) ADVAMT, nvl(a.AMOUNTRECOVERED, 0) AMOUNTRECOVERED, nvl(a.NOOFINSTALLMENTS, 0) NOOFINSTALLMENTS,nvl(a.RECOVEREDINSTALLMENTS, 0) RECOVEREDINSTALLMENTS,nvl(a.INTRESTAMOUNT, 0) INTRESTAMOUNT, nvl(a.RECOVEREDINTREST, 0) RECOVEREDINTREST, nvl(a.INTRESTINSTALLMENTS, 0) INTRESTINSTALLMENTS, nvl(a.RECOVEREDINTRESTINSTALLMENTS, 0) RECOVEREDINTRESTINSTALLMENTS,nvl(am.ADVNAME, '--') ADVNAME from (select ead.ADVCD,ead.ADVAMT,(nvl(AMOUNTPAID,0)+(select sum(nvl(amount,0)) from employeeadvancerec e where e.EMPLOYEEADVCD=empadvrec.EMPLOYEEADVCD and INSTALLMENTNO <=empadvrec.installmentno))AMOUNTRECOVERED,ead.NOOFINSTALLMENTS,(case PAYMENTTYPE  when 'P' then  empadvrec.installmentno else RECOVEREDINSTALLMENTS end ) RECOVEREDINSTALLMENTS,(case PAYMENTTYPE when 'I' then ead.INTRESTAMOUNT else 0 end) INTRESTAMOUNT,(case PAYMENTTYPE when 'I' then ead.RECOVEREDINTREST else 0 end) RECOVEREDINTREST,(case PAYMENTTYPE when 'I' then ead.INTRESTINSTALLMENTS else 0 end) INTRESTINSTALLMENTS,(case PAYMENTTYPE when 'I' then  empadvrec.installmentno else 0 end ) RECOVEREDINTRESTINSTALLMENTS  from EMPLOYEEADVANCEREC empadvrec,EMPLOYEEADVANCES   ead where empadvrec.EMPLOYEEADVCD = ead.EMPLOYEEADVCD and empadvrec.EMPNO(+) = '"+rss.getString("empNo")+"' and empadvrec.PAYROLLMONTHID = (Select mp.PAYROLLMONTHID from monthlypayroll mp  where PAYROLLMONTHNM = '"+psinfo.getMonth()+"' and payrollyear = '"+psinfo.getYear()+"') )a, ADVANCESMASTER am where a.advcd(+)=am.advcd";
				
				if(!psinfo.getType().equals("PBR")) {
					if(!psinfo.getType().equals("PA")) {
						//String advqry = "select sum(nvl(a.ADVAMT, 0)) ADVAMT, sum(nvl(a.AMOUNTRECOVERED, 0)) AMOUNTRECOVERED, sum(nvl(a.NOOFINSTALLMENTS, 0)) NOOFINSTALLMENTS,sum(nvl(a.RECOVEREDINSTALLMENTS, 0)) RECOVEREDINSTALLMENTS,sum(nvl(a.INTRESTAMOUNT, 0)) INTRESTAMOUNT, sum(nvl(a.RECOVEREDINTREST, 0)) RECOVEREDINTREST, sum(nvl(a.INTRESTINSTALLMENTS, 0)) INTRESTINSTALLMENTS, sum(nvl(a.RECOVEREDINTRESTINSTALLMENTS, 0)) RECOVEREDINTRESTINSTALLMENTS,nvl(am.ADVNAME, '--') ADVNAME,nvl(sum(emi),0)emi from (select ead.ADVCD,empadvrec.AMOUNT emi,(decode(trim(PAYMENTTYPE), 'P', ead.ADVAMT,0))ADVAMT,(decode(trim(PAYMENTTYPE),'P',empadvrec.recoveredamt,0)) AMOUNTRECOVERED,(decode(trim(PAYMENTTYPE), 'P', ead.NOOFINSTALLMENTS,0))NOOFINSTALLMENTS,(decode(trim(PAYMENTTYPE),'P',empadvrec.installmentno,0)) RECOVEREDINSTALLMENTS,(decode(trim(PAYMENTTYPE),'I', ead.INTRESTAMOUNT,0)) INTRESTAMOUNT,(decode(trim(PAYMENTTYPE),'I',empadvrec.recoveredamt,0)) RECOVEREDINTREST,(decode(trim(PAYMENTTYPE),'I', ead.INTRESTINSTALLMENTS,0)) INTRESTINSTALLMENTS,(decode(trim(PAYMENTTYPE),'I',  empadvrec.installmentno,0)) RECOVEREDINTRESTINSTALLMENTS  from EMPLOYEEADVANCEREC empadvrec,EMPLOYEEADVANCES   ead where empadvrec.EMPLOYEEADVCD = ead.EMPLOYEEADVCD and empadvrec.EMPNO(+) = '"+rss.getString("empNo")+"' and empadvrec.PAYROLLMONTHID = (Select mp.PAYROLLMONTHID from monthlypayroll mp  where PAYROLLMONTHNM = '"+psinfo.getMonth()+"' and payrollyear = '"+psinfo.getYear()+"') )a, ADVANCESMASTER am where a.advcd(+)=am.advcd group by am.ADVNAME, am.advcd order by am.advcd";
						/*String advqry = " select advnm ADVNAME,(NVL (a.advamt, 0)) advamt,    (NVL (a.amountrecovered, 0)) amountrecovered,  (NVL (a.noofinstallments, 0)) noofinstallments,  (NVL (a.recoveredinstallments, 0)) recoveredinstallments,   (NVL (a.intrestamount, 0)) intrestamount,   (NVL (a.recoveredintrest, 0)) recoveredintrest,     (NVL (a.intrestinstallments, 0)) intrestinstallments,    (NVL (a.recoveredintrestinstallments, 0)) recoveredintrestinstallments,  NVL ((emi), 0) emi,a.advcd,a.earndeducd,  nvl(a.advprintorder,b.advprintorder)advprintorder, nvl(a.paymenttype,b.paymenttype)paymenttype from " +
						" ( select paymenttype,trec.amount emi,tpd.earndeducd,trec.advcd,em.EARNDEDUNM EARNDEDUNM,  (DECODE (TRIM (paymenttype), 'P', tea.advamt, 0)) advamt,  (DECODE (TRIM (paymenttype), 'I', tea.intrestamount, 0)  ) intrestamount,   (DECODE (TRIM (paymenttype), 'P',trec.recoveredamt, 0) ) amountrecovered,    (DECODE (TRIM (paymenttype), 'P', tea.noofinstallments, 0) ) noofinstallments,    (DECODE (TRIM (paymenttype),'P', trec.installmentno, 0 )) recoveredinstallments,   (DECODE (TRIM (paymenttype), 'I', trec.recoveredamt, 0) ) recoveredintrest,   (DECODE (TRIM (paymenttype), 'I', tea.intrestinstallments, 0 ) ) intrestinstallments,  (DECODE (TRIM (paymenttype),'I', trec.installmentno, 0 ) ) recoveredintrestinstallments,am.ADVPRINTORDER " +
						"  from payrolldet tpd,EMPLOYEEADVANCEREC trec,  earndedumaster em,employeeadvances tea,advancesmaster am where tpd.type in ('ADV','ADVINT')  and tpd.EARNDEDUCD = trec.earndeducd  and tpd.EARNDEDUCD = em.earndeducd   and trec.ADVCD = tea.advcd  and trec.ADVCD = am.advcd  and trec.empno = '"+rss.getString("empNo")+"'  and tea.empno = '"+rss.getString("empNo")+"' and tpd.empno = '"+rss.getString("empNo")+"'  " +
								" and trec.PAYROLLMONTHID = (Select mp.PAYROLLMONTHID from monthlypayroll mp  where PAYROLLMONTHNM = '"+psinfo.getMonth()+"' and payrollyear = '"+psinfo.getYear()+"')  and tpd.PAYTRANSID = ( select PAYTRANSID from trailpayrollprocess where payrollmonthid = (  SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"' )  ) order by am.ADVPRINTORDER)a," +
										" (  SELECT am.earndeducd, am.advprintorder,em.EARNDEDUNM advnm,'P' paymenttype  FROM advancesmaster am,earndedumaster em  where am.earndeducd = em.earndeducd   union all  " +
										"  SELECT INTEARNDEDUCD, advprintorder,em.EARNDEDUNM advnm,'I' paymenttype  FROM advancesmaster am,earndedumaster em  WHERE INTEARNDEDUCD is not null and am.INTEARNDEDUCD = em.earndeducd)b  where a.earndeducd (+)= b.earndeducd  order by advprintorder,paymenttype desc";*/
						String advqry = " SELECT   advnm advname, (NVL (a.advamt, 0)) advamt,  (NVL (a.amountrecovered, 0)) amountrecovered,  (NVL (a.noofinstallments, 0)) noofinstallments,  (NVL (a.recoveredinstallments, 0)) recoveredinstallments,  (NVL (a.intrestamount, 0)) intrestamount,  (NVL (a.recoveredintrest, 0)) recoveredintrest,  (NVL (a.intrestinstallments, 0)) intrestinstallments,   (NVL (a.recoveredintrestinstallments, 0)) recoveredintrestinstallments, NVL ((emi), 0) emi, a.advcd, a.earndeducd,   NVL (a.advprintorder, b.advprintorder) advprintorder,  NVL (a.paymenttype, b.paymenttype) paymenttype FROM ( " +
						"  SELECT   paymenttype, trec.amount emi, trec.earndeducd, trec.advcd,  em.earndedunm earndedunm,   (DECODE (TRIM (paymenttype), 'P', tea.advamt, 0)) advamt,  (DECODE (TRIM (paymenttype), 'I', tea.intrestamount, 0)) intrestamount, (DECODE (TRIM (paymenttype), 'P', trec.recoveredamt, 0)) amountrecovered, (DECODE (TRIM (paymenttype), 'P', tea.noofinstallments, 0) ) noofinstallments,  (DECODE (TRIM (paymenttype), 'P', trec.installmentno, 0)) recoveredinstallments, (DECODE (TRIM (paymenttype), 'I', trec.recoveredamt, 0)) recoveredintrest, (DECODE (TRIM (paymenttype),'I', tea.intrestinstallments,0)) intrestinstallments,(DECODE (TRIM (paymenttype), 'I', trec.installmentno, 0)) recoveredintrestinstallments, am.advprintorder" +
						" FROM employeeadvancerec trec,earndedumaster em,employeeadvances tea, advancesmaster am WHERE  trec.advcd = tea.advcd AND trec.advcd = am.advcd   AND trec.empno = '"+rss.getString("empNo")+"'   and  trec.EARNDEDUCD = em.EARNDEDUCD and tea.EMPNO = trec.empno and trec.EMPLOYEEADVCD = tea.EMPLOYEEADVCD " +
						"   AND trec.payrollmonthid =(SELECT mp.payrollmonthid  FROM monthlypayroll mp  WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"') ORDER BY am.advprintorder  ) a," +
						" (SELECT am.earndeducd, am.advprintorder, em.earndedunm advnm, 'P' paymenttype  FROM advancesmaster am, earndedumaster em WHERE am.earndeducd = em.earndeducd and am.status = 'A' UNION ALL SELECT intearndeducd, advprintorder, em.earndedunm advnm, 'I' paymenttype  FROM advancesmaster am, earndedumaster em  WHERE intearndeducd IS NOT NULL and am.status = 'A' AND am.intearndeducd = em.earndeducd) b " +
						" WHERE a.earndeducd(+) = b.earndeducd and (amountrecovered > 0 or emi>0) ORDER BY advprintorder, paymenttype DESC" +
						"";
						log.info(" advqry "+advqry);
						ResultSet advrs = db.getRecordSet(advqry);
						int othersadv = 0;
						while(advrs.next()){
							RecoveryDetailsInfo rdinfo = new RecoveryDetailsInfo();							
							int advprord = advrs.getInt("advprintorder");
							//if(advprord<=10){
								rdinfo.setGrantedAmt(advrs.getString("ADVAMT"));
								int advamount = advrs.getInt("ADVAMT");
								if(advamount!=0){
									if(advrs.getDouble("AMOUNTRECOVERED")>0){
										rdinfo.setEmi(advrs.getString("emi"));
									}
								}else{
									if(advrs.getDouble("AMOUNTRECOVERED")>0){
										rdinfo.setEmi("");
									}
								}
								//log.info(" advamount "+advamount);
								if(advamount!=0){
									rdinfo.setRecoveredAmt(advrs.getString("AMOUNTRECOVERED"));
									rdinfo.setTotInst(advrs.getString("NOOFINSTALLMENTS"));
									rdinfo.setCurrentInst(advrs.getString("RECOVEREDINSTALLMENTS"));
									rdinfo.setBalAmount(advrs.getInt("ADVAMT")-advrs.getInt("AMOUNTRECOVERED")+"");
								}else{
									//log.info("setting empty");
									rdinfo.setRecoveredAmt("");
									rdinfo.setTotInst("");
									rdinfo.setCurrentInst("");
								}
								rdinfo.setAdvName(advrs.getString("ADVNAME"));
								if(advrs.getDouble("RECOVEREDINTREST")<= 0){
									rdadvlist.add(rdinfo);
								}
								if(advrs.getDouble("RECOVEREDINTREST")>0){
									rdinfo = new RecoveryDetailsInfo();
									rdinfo.setGrantedAmt(advrs.getString("INTRESTAMOUNT"));
									int intamt = advrs.getInt("INTRESTAMOUNT");
									//log.info("intamt "+intamt);
									if(intamt !=0){
										rdinfo.setEmi(advrs.getString("emi"));
										rdinfo.setRecoveredAmt(advrs.getString("RECOVEREDINTREST"));
										rdinfo.setTotInst(advrs.getString("INTRESTINSTALLMENTS"));
										rdinfo.setCurrentInst(advrs.getString("RECOVEREDINTRESTINSTALLMENTS"));
										rdinfo.setBalAmount(advrs.getInt("INTRESTAMOUNT")-advrs.getInt("RECOVEREDINTREST")+"");
									}else{
										//log.info("setting int empty");
										rdinfo.setEmi("");
										rdinfo.setRecoveredAmt("");
										rdinfo.setTotInst("");
										rdinfo.setCurrentInst("");
									}
									rdinfo.setAdvName(advrs.getString("ADVNAME"));
									rdadvlist.add(rdinfo);
								}
							
							//}else{
							//	othersadv = othersadv+advrs.getInt("emi");
								
							//}
							
						}
						//RecoveryDetailsInfo rdinfo1 = new RecoveryDetailsInfo();	
						//rdinfo1.setGrantedAmt(othersadv+"");
						//rdinfo1.setAdvName("Others");
						//rdadvlist.add(rdinfo1);
					}
				}
				db.closeRs();
				if(psinfo.getType().equals("PBR")) {
					
					if(flag){
						cnt++;
						empPayInfo.setPayrollmonthid(sh.getDescription("monthlypayroll","PAYROLLMONTHNM||'-'||PAYROLLYEAR","PAYROLLMONTHID",payrollmomthid,con));
						//info1.setPayrollmonthid(sh.getDescription("monthlypayroll","PAYROLLMONTHNM||'-'||PAYROLLYEAR","PAYROLLMONTHID",rs.getString("PAYROLLMONTHID"),con));
						empPayInfo.setCount(cnt);
						empPayInfo.setAdvtotal(advtotal);
						empPayInfo.setEmpInfo(empinfo);
						empPayInfo.setPerkList(perkList);
						//empPayInfo.setMiscperkList(miscperkList);
						empPayInfo.setRecList(advlist);
						log.info("NO PBR report adding rdadvlist");
						empPayInfo.setEarnList(earnningList);
						empPayInfo.setDeducList(deducList);
						empPayInfo.setEarnTotal(earnTotal-(arrTotal-recoTotal));
						empPayInfo.setArrTotal(arrTotal);
						empPayInfo.setRecoTotal(recoTotal);
						empPayInfo.setDeducTotal(deducTotal-advtotal);
						empPayInfo.setPerkTotal(perkTotal);
						empPayInfo.setMiscperkTotal(miscperkTotal);
						empPayInfo.setRecTotal(recTotal);
						empPayInfo.setGrossAmt(Double.toString(grossamt));
						empPayInfo.setDeducAmt(deducamt);
						empPayInfo.setNetAmt(grossamt-deducamt);
						empPayInfo.setNetamtinwords(sq.ConvertInWords(new Double(empPayInfo.getNetAmt()).doubleValue()));
						empPayList.add(empPayInfo);
					}
					list = empPayList;
				}
				//For ind payslip
				if(!psinfo.getType().equals("PBR")) {
					empPayInfo.setRdinfoList(rdadvlist);
					empPayInfo.setEmpInfo(empinfo);
					empPayInfo.setPerkList(perkList);
					empPayInfo.setAdvtotal(advtotal);
					log.info("no pbr cond");
					//empPayInfo.setMiscperkList(miscperkList);
					empPayInfo.setRecList(rdadvlist);
					empPayInfo.setEarnList(earnningList);
					empPayInfo.setDeducList(deducList);
					empPayInfo.setEarnTotal(earnTotal-(arrTotal-recoTotal));
					empPayInfo.setArrTotal(arrTotal);
					empPayInfo.setRecoTotal(recoTotal);
					empPayInfo.setDeducTotal(deducTotal-advtotal);
					empPayInfo.setPerkTotal(perkTotal);
					empPayInfo.setMiscperkTotal(miscperkTotal);
					empPayInfo.setRecTotal(recTotal);
					empPayInfo.setGrossAmt(Double.toString(grossamt));
					empPayInfo.setDeducAmt(deducamt);
					empPayInfo.setNetAmt(grossamt-deducamt);
					empPayInfo.setNetamtinwords(sq.ConvertInWords(new Double(empPayInfo.getNetAmt()).doubleValue()));
					String ctgry = "";
					if(flag){
						if(psinfo.getType().equals("PA")){
							ctgry = rss.getString("STAFFCTGRYCD");
							if(hm.containsKey(ctgry)){
								List lst = (List)hm.get(ctgry);
								lst.add(empPayInfo);
								hm.remove(ctgry);
								hm.put(ctgry,lst);
								log.info("lst size is "+lst.size());
							}else{
								empPayList = new ArrayList();
								empPayList.add(empPayInfo);
								hm.put(ctgry,empPayList);
								log.info("empPayList size is "+empPayList.size());
							}
							
						}else {
							empPayList.add(empPayInfo);
							list = empPayList;
						}
					}
					/*if(flag)
						empPayList.add(empPayInfo);*/
				}
			}
			if(psinfo.getType().equals("PA")){
				list.add(hm);
			}
			log.info("list size is "+list.size());
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception ------>>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public List viewPayAbstractReport(PaySlipInfo psinfo,javax.servlet.http.HttpServletRequest request){
		List empPayList = new ArrayList();
		double grandEarn = 0.0;
		double grandDedu = 0.0;
		double grandPerk = 0.0;
		double grandRec = 0.0;
		double grandNet = 0.0;
		String sql = "";
		
		Map mp = new HashMap();
		Hashtable ht = new Hashtable();
		EmployeeInfo empinfo = new EmployeeInfo();
		EmpPaySlipInfo empPayInfo = new EmpPaySlipInfo();
		PaySlipInfo psinfoo = new PaySlipInfo();
		boolean flag = false;
		try{
			DecimalFormat df1 = new DecimalFormat("####.00");
			db.makeConnection();
			con = db.getConnection();
			StringBuffer empSQL = new StringBuffer();
			empSQL.append("Select emplnumber,emp.empno,emp.DISCIPLINECD,DESGCODE,CADRECOD,emp.STAFFCTGRYCD,EMPLOYEENAME empname,get_description('upper(DESIGNATIONNM)','staffdesignation sds','sds.DESIGNATIONCD='''||emp.DESIGNATIONCD||'''')DESIGNATIONNM,get_description('STAFFCTGRYNM','staffcategory st','st.STAFFCTGRYCD='''||emp.STAFFCTGRYCD||'''')STAFFCTGRYNM,get_description('upper(DISCIPLINENM)','staffdiscipline sd','sd.DISCIPLINECD='''||emp.DISCIPLINECD||'''')DISCIPLINENM,CPFNO,BACNO,( select MINSCALE|| '-' ||MAXSCALE from payscalemaster pay where emp.DESIGNATIONCD=pay.DESIGNATIONCD) payscale from  EmployeeInfo emp,staffdesignation dsg,employeepersonalinfo empp  ");
			if(!psinfo.getPayrollmonthid().equals("")){
				empSQL.append(",PAYROLLPROCESS pp where pp.PAYROLLMONTHID = '"+psinfo.getPayrollmonthid()+"' ");
			} 
			if(psinfo.getStation() !=null && !psinfo.getStation().equals("")){
				if(!psinfo.getPayrollmonthid().equals("")) {
					empSQL.append(" and pp.STATIONCD ='"+psinfo.getStation()+"' ");
				}
				empSQL.append(" and emp.STATIONCD = pp.STATIONCD ");
			}
			if(!psinfo.getEmpNo().equals("All")){
				if(!psinfo.getStation().equals("") || !psinfo.getPayrollmonthid().equals("")){
					empSQL.append(" and EMPNO = '"+psinfo.getEmpNo()+"'");
				}else {
					empSQL.append(" where EMPNO = '"+psinfo.getEmpNo()+"'");
				}
			}
			/*if(!psinfo.getDiscipline().equals("")){
				empSQL.append(" and emp.DISCIPLINECD = '"+psinfo.getDiscipline()+"' ");
			}*/
			if(!psinfo.getPaybillcd().equals("")){
				empSQL.append(" and emp.paybillcd = '"+psinfo.getPaybillcd()+"' ");
			}
			if(!psinfo.getStaffCategory().equals("")){
				empSQL.append(" and emp.STAFFCTGRYCD = '"+psinfo.getStaffCategory()+"' ");
			}
			empSQL.append(" and emp.empno=empp.empno and empp.seperationdate is null and  emp.DESIGNATIONCD=dsg.DESIGNATIONCD order by to_number(empno) ");
			
			log.info("empSQL is "+empSQL.toString());
			PreparedStatement ps = null;
			ResultSet rss = db.getRecordSet(empSQL.toString());
			HashMap hm= new HashMap();
			while(rss.next()){
				empPayInfo = new EmpPaySlipInfo();
				empinfo = new EmployeeInfo();
				List earnningList = new ArrayList();
				List deducList = new ArrayList();
				List perkList = new ArrayList();
				List recoveryList = new ArrayList();
				empinfo.setEmpNo(rss.getString("emplnumber"));
				empinfo.setEmpName(rss.getString("empname").toUpperCase());
				empinfo.setDisciplineCd(new Integer(rss.getInt("DISCIPLINECD")));
				empinfo.setDesignationCd(new Integer(rss.getInt("DESGCODE")));
				empinfo.setStaffCategoryCd(new Integer(rss.getInt("STAFFCTGRYCD")));
				empinfo.setCadreCd(sh.getDescription("staffcadre","CADRENM","CADRECD",rss.getString("CADRECOD"),con));
				empinfo.setEmpDesignation(rss.getString("DESIGNATIONNM"));
				empinfo.setStaffCategoryNm(rss.getString("STAFFCTGRYNM"));
				empinfo.setEmpDescipline(rss.getString("DISCIPLINENM"));
				empinfo.setCpfNo(rss.getString("CPFNO"));
				empinfo.setBacno(rss.getString("BACNO"));
				empinfo.setPayscale(rss.getString("payscale"));
				empPayInfo.setMonth(psinfo.getMonth());
				empPayInfo.setYear(psinfo.getYear());
				
				double grossamt = 0.0;
				double deducamt = 0.0;
				double netamt = 0.0;
				double earnTotal = 0.0,deducTotal = 0.0,perkTotal =0.0,recTotal = 0.0;
				
				sql = " select EARNDEDUCD, AMOUNT, adjustments, type, EARNDEDUNM, SHORTNAME, PRINTORDER from ";
				sql += " (Select dt.EARNDEDUCD, sum(AMOUNT) AMOUNT,sum(adjustments) adjustments, dt.type, EARNDEDUNM, SHORTNAME, em.PRINTORDER "; 
				sql += " from PAYROLLDET dt, earndedumaster em ";
				sql += " where dt.type not in ('AAI','AAI-BF') ";
				sql += " and em.earndeducd=dt.earndeducd  ";
				sql += " and EMPNO = '"+rss.getString("empNo")+"' ";
				sql += " and paytransid in (select paytransid from payrollprocess where PAYROLLMONTHID ='"+psinfo.getPayrollmonthid()+"') ";
				sql += " group by dt.earndeducd, dt.type, EARNDEDUNM, SHORTNAME, em.PRINTORDER ";

				sql += " union ";

				sql += " Select dt.EARNDEDUCD, 0 AMOUNT,0 adjustments, dt.type, EARNDEDUNM, SHORTNAME, em.PRINTORDER "; 
				sql += " from PAYROLLDET dt, earndedumaster em ";
				sql += " where dt.type not in ('AAI','AAI-BF') ";
				sql += " and em.earndeducd=dt.earndeducd ";
				sql += " and dt.EARNDEDUCD not in (select EARNDEDUCD from PAYROLLDET where EMPNO = '"+rss.getString("empNo")+"' ";
				sql += " and paytransid in (select paytransid from payrollprocess where PAYROLLMONTHID ='"+psinfo.getPayrollmonthid()+"')) ";
				sql += " and paytransid in (select paytransid from payrollprocess where PAYROLLMONTHID ='"+psinfo.getPayrollmonthid()+"') ";
				sql += " group by dt.earndeducd, dt.type, EARNDEDUNM, SHORTNAME, em.PRINTORDER) ";
				sql += " order by PRINTORDER, EARNDEDUCD ";
				
				/*sql = "Select dt.EARNDEDUCD, sum(AMOUNT)AMOUNT,sum(adjustments)adjustments, dt.type,EARNDEDUNM,SHORTNAME ";
				sql += " from PAYROLLDET dt, PAYROLLPROCESS prs, earndedumaster em ";
				sql += " where dt.PAYTRANSID=prs.PAYTRANSID and dt.type not in ('AAI','AAI-BF') ";
				sql += " and em.earndeducd = dt.earndeducd and PAYROLLMONTHID ='"+psinfo.getPayrollmonthid()+"' ";
				sql += " and EMPNO = '"+rss.getString("empNo")+"' ";
				sql += " group by dt.earndeducd,dt.type,EARNDEDUNM, SHORTNAME,em.PRINTORDER ";
				sql += " order by PRINTORDER,dt.EARNDEDUCD";*/
				
				log.info("sql is "+sql);
				
				rs = db.getRecordSet(sql);
				while(rs.next()){

					flag = true;
					psinfoo = new PaySlipInfo();
					String salheadtype = rs.getString("type");
					String amount = rs.getString("AMOUNT");
					String adjustments = rs.getString("adjustments");
						psinfoo.setEarnDeduName(rs.getString("EARNDEDUNM"));
						psinfoo.setShortName(rs.getString("SHORTNAME"));
						psinfoo.setAmount(Math.round(rs.getDouble("AMOUNT")+rs.getDouble("adjustments"))+"");	
						psinfoo.setType(salheadtype);
					//log.info("salheadtype "+salheadtype);
					if(salheadtype.equals("E")||salheadtype.equals("GPROF")||salheadtype.equals("AR")||salheadtype.equals("A")||salheadtype.equals("MISCADV") ||salheadtype.equals("PK") || salheadtype.equals("MISCPK")){
						earnningList.add(psinfoo);						
						grossamt+=(rs.getDouble("AMOUNT")+rs.getDouble("adjustments"));
						earnTotal +=rs.getDouble("AMOUNT");
					}
					
					
						if(salheadtype.equals("D")||salheadtype.equals("MISCREC")|| salheadtype.equals("EACPF")||salheadtype.equals("OD")|| salheadtype.equals("DIES") || salheadtype.equals("IT") || salheadtype.equals("OR")||salheadtype.equals("CLCRC")||salheadtype.equals("ADV")|| salheadtype.equals("ADVINT")|| salheadtype.equals("Int")) {
							if(salheadtype.equals("ADV")|| salheadtype.equals("ADVINT")|| salheadtype.equals("Int")){
								recoveryList.add(psinfoo);
								//log.info("adding reclist ");
								recTotal = recTotal+rs.getDouble("AMOUNT");
								deducamt+=rs.getDouble("AMOUNT");
							}else{
								deducList.add(psinfoo);
								deducamt+=(rs.getDouble("AMOUNT")+rs.getDouble("adjustments"));
								deducTotal+=rs.getDouble("AMOUNT");
							}
							
						}
					}
				db.closeRs();
				
				
				empPayInfo.setPerkList(perkList);
				empPayInfo.setRecList(recoveryList);
				empPayInfo.setEarnList(earnningList);
				empPayInfo.setDeducList(deducList);
				empPayInfo.setEarnTotal(earnTotal);
				empPayInfo.setDeducTotal(deducTotal);
				empPayInfo.setPerkTotal(perkTotal);
				empPayInfo.setRecTotal(recTotal);
				empPayInfo.setGrossAmt(Double.toString(grossamt));
				empPayInfo.setDeducAmt(deducamt);
				empPayInfo.setNetAmt(grossamt-deducamt);
				
				int ianamt = (int)empPayInfo.getNetAmt();
				empinfo.setNetAmt(ianamt+"");
				empPayInfo.setEmpInfo(empinfo);
				
				empPayList.add(empPayInfo);
				
				grandEarn += earnTotal;
				grandDedu += deducTotal;
				grandPerk += perkTotal;
				grandRec += recTotal;
				grandNet += (grossamt-deducamt);
				
			}
			
			EmpPaySlipInfo totinfo = new EmpPaySlipInfo();
			
			totinfo.setStrEarnTotal(df1.format(grandEarn)+"");
			totinfo.setDeducTotal(grandDedu);
			totinfo.setPerkTotal(grandPerk);
			totinfo.setRecTotal(grandRec);
			totinfo.setNetAmt(grandNet);
			request.setAttribute("totinfo", totinfo);
			
			log.info("PBR list size "+empPayList.size());		
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception ------>>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return empPayList;
	}
	public List getPayrollMonths(){
		List pmlist = new ArrayList();
		String getpayrollmonthquery = "SELECT *  FROM monthlypayroll mp, financialyear fy WHERE mp.fyearcd = fy.fyearcd AND fy.status = 'A' order by fy.FROMDATE  ";
		try{
			db.makeConnection();
			con = db.getConnection();
			//log.info(" getpayrollmonthquery "+getpayrollmonthquery);
			rs = db.getRecordSet(getpayrollmonthquery);
			while(rs.next()){
				MonthlyPayrollInfo mpinfo = new MonthlyPayrollInfo();
				mpinfo.setPayrollmonthnm(rs.getString("PAYROLLMONTHNM")+"-"+rs.getString("PAYROLLYEAR"));
				mpinfo.setPayrollmonthid(rs.getInt("PAYROLLMONTHID"));
				mpinfo.setPayrollstatus(rs.getString("MONTHLYSTATUS"));
				
				pmlist.add(mpinfo);
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< getPayrollMonths() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		//
		return pmlist;
	}
	
	private Map addtoMap(Map mp,String earncd,double pamt,String discplinecd,String staffctgrycd,String type,double adj,double arr,double rec ){
		
				/*log.info("discplinecd "+discplinecd+" staffctgrycd "+staffctgrycd+" earncd "+earncd+" pamt "+pamt);
				if(staffctgrycd.equalsIgnoreCase("7")){
					log.info("staffctgrycd +++++++++++++++++++++++++++"+staffctgrycd);
				}*/
		if(mp.containsKey(discplinecd)){
			
			HashMap scgmap = (HashMap)mp.get(discplinecd);
			//log.info("################# scgmap "+(scgmap==null)+" staffctgrycd "+staffctgrycd);
			if(scgmap ==null){
				scgmap = new HashMap();
			}
			if(scgmap.containsKey(staffctgrycd)){
				HashMap earndmap = (HashMap)scgmap.get(staffctgrycd);
				if(earndmap == null){
					//log.info("*********************earndmap map is null creating new map");
					earndmap = new HashMap();
				}
				 if(earndmap.containsKey(earncd)){				 	
				 	String am = earndmap.get(earncd).toString();
				 	String [] a = am.split(",");
				 	double amt =  new Double(a[1].toString()).doubleValue();
				 	double adjamt =  new Double(a[2].toString()).doubleValue();
				 	double arramt =  new Double(a[3].toString()).doubleValue();
				 	double recamt =  new Double(a[4].toString()).doubleValue();
				 	double tamt = pamt+amt;
				 	double tadjamt = adj+adjamt;
				 	double tarramt = arr+arramt;
				 	double trecamt = rec+recamt;
				 	String te = type+","+tamt+","+tadjamt+","+tarramt+","+trecamt;
				 	/*if(earncd.equals("11")){
				 		log.info("gprof total amt "+amt+" pamt "+pamt);
				 	}*/
				 	//log.info("*********************contains earncd "+earncd+" te "+te);
				 	earndmap.put(earncd,te);
				 	
				 }else{				 	
				 	String te = type+","+pamt+","+adj+","+arr+","+rec;
				 	//log.info("*********************does not contain earncd "+earncd+" te "+te);
				 	earndmap.put(earncd,te);	
				 }
				 scgmap.put(staffctgrycd,earndmap);
			}else{
				
				HashMap stcgmap = new HashMap();
				String te = type+","+pamt+","+adj+","+arr+","+rec;
				stcgmap.put(earncd,te);
				//log.info("#############no staffcategory creating new map earncd "+earncd+" staffctgrycd "+staffctgrycd+" te "+te);
				scgmap.put(staffctgrycd,stcgmap);
				
			}
		}else{	
			HashMap eardmap1 = new HashMap();
			String te = type+","+pamt+","+adj+","+arr+","+rec;
			eardmap1.put(earncd,te);
			HashMap sctgmap = new HashMap();
			sctgmap.put(staffctgrycd,eardmap1);
			//log.info("#############no discplinecd creating new map earncd "+earncd+" staffctgrycd "+staffctgrycd+" te "+te+" discplinecd "+discplinecd);
			mp.put(discplinecd,sctgmap);
		}
		
		return mp;
	}
	private String getEarnCdFrmMap(Map m,String staffctgry,String type){
		String earndeducd = "";
		//HashMap hm = (HashMap)m.get(new Integer(1));
		HashMap hm = (HashMap)m.get(new Integer(staffctgry));
		//log.info("type"+type);
		earndeducd = hm.get(type).toString();
		//log.info("earndeducd"+earndeducd);
		
		return earndeducd;
	}
	public void payProcess(PayrollProcessInfo ppinfo) throws ApplicationException{
		
		EmployeeInfo empinfo = new EmployeeInfo();
		
		
		//String getEmployeesQuery = "select empNo,EMPFIRSTNAME||' '||EMPLASTNAME||'.'||EMPSURNAME empname,DESIGNATIONCD,STAFFCTGRYCD,DISCIPLINECD from EMPLOYEEINFO where empNo is not null";
		String getEmployeesQuery = "select emi.empNo from EMPLOYEEINFO emi,EMPLOYEEPERSONALINFO epi where  emi.EMPNO = epi.EMPNO  and emi.empNo is not null and epi.seperationdate is null  ";
		/*ResourceBundle bundle = ResourceBundle.getBundle("com.aims.resource.ApplicationConfig");
		String salpaybleledgcode = bundle.getString("Salaraypayable");
		String empcpf = bundle.getString("EMPCPF");
		String aaicpf = bundle.getString("AAICPF");
		String benfund = bundle.getString("BENFUND");
		String aaibenfund = bundle.getString("AAIBENFUND");*/
		String empcpf =  "";
		String benfund = "";
		String aaicpf = "";
		String aaibenfund = "";
		String salpaybleledgcode = "";
		String retirefrmPension = "";
		String status = "";
		/*String sbipercent = bundle.getString("SBIPERCENTAGE");
		double sbipercentage = 0;
		if(sbipercent == null || sbipercent.equals("")){
			sbipercentage = 10;
		}else{
			sbipercentage = new Double(sbipercent).intValue();
		}*/
		
		/*if(ppinfo.getDisciplinecd()>0){
			getEmployeesQuery = getEmployeesQuery+" and emi.DISCIPLINECD ="+ppinfo.getDisciplinecd();
		}*/
		/*if(ppinfo.getStaffctgrycd()>0){
			getEmployeesQuery = getEmployeesQuery+" and emi.STAFFCTGRYCD ="+ppinfo.getStaffctgrycd();
		}*/		
		if(!"".equals(ppinfo.getStation())){
			getEmployeesQuery = getEmployeesQuery+" and emi.STATIONCD ='"+ppinfo.getStation()+"'";
		}
		PreparedStatement ps = null;
		
		Statement stmt = null;
		Map earnMap = new HashMap();
		Map emplistMap = new HashMap();
		Map empCpfMap = new HashMap();
		Map empCpfAdjMap = new HashMap();
		Map empDiscMap = new HashMap();
		Map emppensionMap = new HashMap();
		Map empcadrecodMap = new HashMap();
		Map vcvmMap = new HashMap();
		int maxnoofdaysformoth = 0;
		String lopearndedcds = "";
		String uaaearndedcds = "";
		String lopearningdedcds[];
		String cpfdepends = "";
		Map cpfbaseamt = new HashMap();
		Map psnoMap = new HashMap();
		Map psnoMap1 = new HashMap();
		try{
			db.makeConnection();
			con = db.getConnection();
			
			/*  
			 *  check whether sal has been paid for staff category for the selected month
			 */
			String checkQuery="select paytransid from PAYROLLPROCESS where  PAYROLLMONTHID = '"+ppinfo.getPayrollmonthid()+"' and STATIONCD='"+ppinfo.getStation()+"'";
			
			log.info("check payroll month query "+checkQuery);
			ResultSet crs = db.getRecordSet(checkQuery);
			int cp = 0;
			while(crs.next()){
				cp =  new Integer(crs.getString("paytransid")).intValue();
			}
			crs.close();
			db.closeRs();
			if(cp > 0 )
				 throw new ApplicationException("Salay has been paid for selected station and month");
			
			//log.info("no exception ");
			int paytransid = Integer.parseInt(AutoGeneration.getNextCode("PAYROLLPROCESS","PAYTRANSID",11,con));
			String payrollprocessquery = "insert into PAYROLLPROCESS(PAYTRANSID,PAYROLLMONTHID,stationcd,PROCESSSTATUS) values('"+paytransid+"','"+ppinfo.getPayrollmonthid()+"','"+ppinfo.getStation()+"','not paid')";
			//log.info("payrollprocessquery "+payrollprocessquery);
			stmt = con.createStatement();
			
			Map staffctgrymap = new HashMap();
			
			
			/*
			 *  getting earndedcd's for cpf,aaicpf,cpfarr,benfund,aaibenfund
			 */
			//String getearndedqry = "select EARNDEDUCD,EARNDEDUNM,staffctgrycd,type,ledgercd from EARNDEDUMASTER where LEDGERCD in('"+empcpf+"','"+aaicpf+"','"+benfund+"','"+aaibenfund+"') order by  staffctgrycd,EARNDEDUCD";
			//String getearndedqry = "select EARNDEDUCD,cadrecod staffctgrycd,type,ledgercd from SALHEAD_LEDG_MAPPING where LEDGERCD in('"+empcpf+"','"+aaicpf+"','"+benfund+"','"+aaibenfund+"') order by  staffctgrycd,EARNDEDUCD";
			String getearndedqry = "select slg.earndeducd,slg.ledgercd,slg.cadrecod staffctgrycd,slg.TYPE,cfm.type configtype,cfm.configname,((select dependson from SALHEAD_LEDG_MAPPING where earndeducd=19 and cadrecod=1))cpfdepends from salhead_ledg_mapping slg,configmapping cfm  where slg.earndeducd in ( select earndeducd from configmapping where configname in ('CPF','AAICPF','OTA','ELECREC','OTA1','HRR','WATER','CONS') or type in ('BF','AAIBF','AAICPFDAAR','EMPCPFDAAR','SALPAY')) and slg.EARNDEDUCD = cfm.EARNDEDUCD ";
			log.info("getearndedqry**** "+getearndedqry);
			ResultSet cdrs = db.getRecordSet(getearndedqry);
			while(cdrs.next()){
				String stctgry = cdrs.getString("staffctgrycd");
				String type = cdrs.getString("type");
				String earndedcd = cdrs.getString("EARNDEDUCD");
				String ledcd = cdrs.getString("ledgercd");
				String configtype = cdrs.getString("configtype");
				String configname = cdrs.getString("configname");
				if(cpfdepends.equals("")){
				 	cpfdepends = cdrs.getString("cpfdepends");
				 }
				Map tpMap = (HashMap)staffctgrymap.get(new  Integer(stctgry));
				if(tpMap == null){					
					staffctgrymap.put(new Integer(stctgry),new HashMap());
					tpMap = (HashMap)staffctgrymap.get(new  Integer(stctgry));
				}
				
				if(type.equals("AAI")){
				 	aaicpf = ledcd;
					tpMap.put("AAICPF",earndedcd);
				}
				 if(type.equals("AAICPF")){
					tpMap.put("AAICPFAR",earndedcd);
				}
				 if(type.equals("EACPF")){
					tpMap.put("CPFAR",earndedcd);
				}
				 if(type.equals("MISCPK")){				 	
				 	//log.info("salpaybleledgcode "+salpaybleledgcode);
					tpMap.put("OTA",earndedcd);
				 }
				 if(type.equals("AAI-BF")){
				 	aaibenfund = ledcd;
					tpMap.put("AAIBF",earndedcd);
				 }
				 if(type.equals("SALPAY")){
				 	salpaybleledgcode = ledcd;
				 	//log.info("salpaybleledgcode "+salpaybleledgcode);
					tpMap.put("SALPAY",earndedcd);
				 }
				 if(configname.equalsIgnoreCase("CPF")){
				 	empcpf = ledcd;
				 	tpMap.put("CPF",earndedcd);
				 }
				 if(configtype.equalsIgnoreCase("BF")){
				 	benfund = ledcd;
				 	tpMap.put("BF",earndedcd);
				 }
				 if(configname.equalsIgnoreCase("ELECREC")){				 	
				 	tpMap.put("ELECREC",earndedcd);
				 }
				 if(configname.equalsIgnoreCase("WATER")){				 	
					 	tpMap.put("WATER",earndedcd);
					 }
				 if(configname.equalsIgnoreCase("CONS")){				 	
					 	tpMap.put("CONS",earndedcd);
					 }
				 if(configname.equalsIgnoreCase("OTA1")){				 	
					 	tpMap.put("OTA1",earndedcd);
					 }
				 if(configname.equalsIgnoreCase("HRR")){				 	
					 	tpMap.put("HRR",earndedcd);
					 }
				staffctgrymap.put(new  Integer(stctgry),tpMap);
			}
			cdrs.close();
			db.closeRs();
			
			String lossofpayqry = "select empno,EOLMONTH,eoltype,HPL,EOL,UAA,(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL where PAYROLLMONTHID  = EOLMONTH)maxdays,(select value from VALUE_CONFIGURATIONS where name = 'LOPEarncds')lopvalue,(select value from VALUE_CONFIGURATIONS where name = 'UAACDS')uaavalue    from lop where MONTH = "+ppinfo.getPayrollmonthid();
			log.info("lossofpayqry "+lossofpayqry);
			ResultSet rslop = db.getRecordSet(lossofpayqry);
			Map maplop = new HashMap();
			while(rslop.next()){
				int hpl = rslop.getInt("HPL");
				int eol = rslop.getInt("EOL");
				int uaa = rslop.getInt("UAA");
				String eolmonth = rslop.getString("EOLMONTH");
				Object o9 = maplop.get(rslop.getString("empno"));
				if(o9==null){
					maplop.put(rslop.getString("empno"),(hpl+":"+eol+":"+uaa+":"+eolmonth));
				}else{
					String s[] = o9.toString().split(":");
					int phpl = rslop.getInt("HPL")+Integer.parseInt(s[0]);
					int peol = rslop.getInt("EOL")+Integer.parseInt(s[1]);
					int puaa = rslop.getInt("UAA")+Integer.parseInt(s[2]);
					maplop.put(rslop.getString("empno"),(phpl+":"+peol+":"+puaa+":"+eolmonth));
				}
				if(maxnoofdaysformoth==0){
					maxnoofdaysformoth = rslop.getInt("maxdays");
				lopearndedcds = rslop.getString("lopvalue");
				uaaearndedcds = rslop.getString("uaavalue");
				
				}
			}
			rslop.close();
			db.closeRs();
			/*
			 * Checking whether the employee is On Tour / Leave
			 */
			/*String vcvmqry = "Select empno,purposetype,DAYS from emp_vc_vm_monthly where instr(PAYROLLMONTHID, '"+ppinfo.getPayrollmonthid()+"') > 0 ";
			log.info("vcvmqry-------------- "+vcvmqry);
			ResultSet vcrs = db.getRecordSet(vcvmqry);
			while(vcrs.next()){
				if(vcrs.getInt("DAYS")>15)
					vcvmMap.put(vcrs.getString("empno"),vcrs.getString("purposetype"));
			}*/
			PreparedStatement tpdpstmt6 = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS,ARREARS,RECOVERIES) values (?,?,?,?,?,?,?,?,?)");
			PreparedStatement noarrpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,(SELECT earndeducd FROM SALHEAD_LEDG_MAPPING   WHERE ledgercd = (select LEDGERCD from arrears where ARREARNM='DA Arrears') and cadrecod=?),'AR',(select LEDGERCD from arrears where ARREARNM='DA Arrears'),'0',?)");
			PreparedStatement gprofpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS,ARREARS,RECOVERIES) values (?,?,(select EARNDEDUCD from EARNDEDUMASTER where  type='GPROF'),'E',(select ledgercd from SALHEAD_LEDG_MAPPING where  cadrecod=? and type='GPROF'),?,?,?,?)");
			PreparedStatement miscpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,?,?,(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD=? and cadrecod=?),?,?)");
			PreparedStatement nomisrecpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,(select earndeducd from configmapping where CONFIGNAME=? ),?,(select ledgercd from SALHEAD_LEDG_MAPPING where  EARNDEDUCD=(select earndeducd from configmapping where CONFIGNAME=? )and cadrecod =?),'0',?)");
			PreparedStatement empadvrecpstmt = con.prepareStatement("insert into EMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt,EARNDEDUCD) values(EMPLOYEEADVANCEREC_SEQ.nextval,?,?,?,?,?,?,?,?,?)");
			PreparedStatement empadvpstmt = con.prepareStatement("update EMPLOYEEADVANCES set AMOUNTRECOVERED=? ,RECOVEREDINSTALLMENTS =? where EMPLOYEEADVCD = ? ");
			PreparedStatement advfinpstmt = con.prepareStatement(" update EMPLOYEEADVANCES set AMOUNTRECOVERED=? ,RECOVEREDINSTALLMENTS =?, PRNCPLFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID=?) where EMPLOYEEADVCD = ?");
			//PreparedStatement updadvpstmt = con.prepareStatement("update EMPLOYEEADVANCES set principalstatus='N'  where EMPLOYEEADVCD = ? ");
			PreparedStatement cpfadvpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,?,'ADVINT',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD = ? and cadrecod=?),?,?)");
			PreparedStatement intupdadvpstmt = con.prepareStatement("update EMPLOYEEADVANCES set RECOVEREDINTREST=? ,RECOVEREDINTRESTINSTALLMENTS =? where EMPLOYEEADVCD = ? ");
			PreparedStatement intcompadvpstmt = con.prepareStatement(" update EMPLOYEEADVANCES set RECOVEREDINTREST=? ,RECOVEREDINTRESTINSTALLMENTS =?, ADVFINISHEDDT = ?, INTRSTFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID=?) where EMPLOYEEADVCD = ? ");
			PreparedStatement nointcpfpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,(SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ),'ADVINT',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD = (SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ) and cadrecod=?),'0',?)");
			PreparedStatement nointadvpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,(select EARNDEDUCD from SALHEAD_LEDG_MAPPING where ledgercd=? and cadrecod=? ),'Int',?,'0',?)");
			PreparedStatement nopmnrpstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,(select earndeducd from SALHEAD_LEDG_MAPPING where ledgercd=? and cadrecod=?),'CLCRC',?,'0',?)");
			PreparedStatement loppstmt = con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,?,?,( select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD=? and cadrecod=? ),?,?)");
			PreparedStatement jvcpfpstmt  =con.prepareStatement("insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT,ADJUSTMENTS) values (?,?,?,'JVCPF',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD=? and CADRECOD = ?),?,?)");
			PreparedStatement mergeitpstmt = con.prepareStatement(" merge into EMPITRECDET ed using dual on (dual.dummy is not null and ed.empno = ? and ed.EMPITRECCD = ?) " +
					" when not matched then insert values(?,?,?,?,?,'A',sysdate) " +
							" when matched then update set ed.ITRECAMT = ed.ITRECAMT+ ?");
			
			
			PreparedStatement tpppstmt = con.prepareStatement("insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD,ADJAMT,ARREARS,RECOVERIES) values(PAYPROCESS_SEQ.nextval,?,(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD=? and cadrecod=?),?,?,?,?,?,?,?)");
			PreparedStatement toperkpstmt = con.prepareStatement("insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD,ADJAMT,ARREARS,RECOVERIES) values(PAYPROCESS_SEQ.nextval,?,'',?,(select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME='TOTPERK' ) ,?,?,?,?,?)");
			PreparedStatement salledgpstmt = con.prepareStatement("insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD,ADJAMT,ARREARS,RECOVERIES) values(PAYPROCESS_SEQ.nextval,?,?,?,(select EARNDEDUCD from CONFIGMAPPING where type='SALPAY'),?,?,?,?,?)");

			
			/*
			 * Checking whether the employee has paystop for the selected payrollmonth
			 */
			//String paystopqry = "select EMPNO from emppaystop where instr(PAYROLLMONTHID, '"+ppinfo.getPayrollmonthid()+"') > 0 and status = 'A' and stationcd = '"+ppinfo.getStation()+"' ";
			String paystopqry = "select eps.empno,ei.CADRECOD from emppaystop eps ,employeeinfo ei where eps.empno = ei.empno and instr(PAYROLLMONTHID, '"+ppinfo.getPayrollmonthid()+"') > 0 and eps.status = 'A' and eps.stationcd = '"+ppinfo.getStation()+"' ";
			log.info("paystopqry-------------- "+paystopqry);
			ResultSet psrs = db.getRecordSet(paystopqry);
			
			/*
			 * redefine paystop query coz it is used in getting all emplist with sal head values
			 * if qry had cadrecod also will give error as too many columns
			 */
			paystopqry = "select EMPNO from emppaystop where instr(PAYROLLMONTHID, '"+ppinfo.getPayrollmonthid()+"') > 0 and status = 'A' and stationcd = '"+ppinfo.getStation()+"'";
						
			String sg1 = "";
			String discd1 = "";			
			String empname = "";
			String emplnumber = "";
			String pftaxopt = "";
			String hraoption = "";
			String desgcd = "";
			String paymenttype = "";
			String bacno = "";
			String cpfno = "";
			String cadrecod= "";
			String cadrecd= "";
			int cnt = 0;
			
			while(psrs.next()){
				double earntot2 = 0.0;
				double dedtot2 = 0.0;
						
				/*String paystopempdetqry = "SELECT empd.empno,nvl(empd.GPROFEOLAMOUNT,0)GPROFEOLAMOUNT, NVL (empd.amount, 0) amount, edm.earndeducd, " +
				"  edm.earndedunm, edm.ledgercd, TYPE " +
				" ,ei.staffctgrycd, ei.disciplinecd, ei.EMPLNUMBER,ei.cadrecod,ei.EMPLOYEENAME,ei.PFTAXOPTION,ei.HRAOPTION,ei.DESIGNATIONCD,ei.BACNO,ei.CPFNO,ei.PAYMENTTYPE " +
				" FROM (SELECT ps.*, esd.earndeducd, esd.amount,nvl(eol.GPROFEOLAMOUNT,0)GPROFEOLAMOUNT FROM (SELECT es.empno, paystopcd FROM emppaystop es WHERE INSTR (payrollmonthid, '"+ppinfo.getPayrollmonthid()+"') > 0 AND es.empno = '"+psrs.getString("empno")+"') ps,emppaystopdet esd,gprofeoldata eol " +
				" WHERE ps.paystopcd = esd.paystopcd and ps.empno=eol.empno(+) and INSTR(eol.payrollmonthid(+), '"+ppinfo.getPayrollmonthid()+"')>0) empd,  earndedumaster edm,employeeinfo ei WHERE empd.empno=ei.empno(+) and edm.earndeducd = empd.earndeducd(+) " +
				" AND edm.staffctgrycd = (SELECT staffctgrycd  FROM employeeinfo WHERE empno = '"+psrs.getString("empno")+"') AND edm.TYPE NOT IN ('TOTPERK','EACPF','AAICPF','CPFAR','AAI-BF','MISCPK') and edm.earndeducd  not in(126,125) " ;*/
				String paystopempdetqry = "sELECT empd.empno, empd.gprofeolamount, NVL (empd.amount, 0) amount,edm.earndeducd, edm.earndedunm, NVL (slm.ledgercd, 0) ledgercd,edm.TYPE, empin.*" +
						" FROM (SELECT ps.*, esd.earndeducd, esd.amount,  NVL (eol.gprofeolamount, 0) gprofeolamount FROM (SELECT es.empno, paystopcd    FROM emppaystop es  WHERE INSTR (payrollmonthid, '"+ppinfo.getPayrollmonthid()+"') > 0 AND es.empno = '"+psrs.getString("empno")+"') ps,  emppaystopdet esd, gprofeoldata eol WHERE ps.paystopcd = esd.paystopcd  AND ps.empno = eol.empno(+) AND INSTR (eol.payrollmonthid(+), '"+ppinfo.getPayrollmonthid()+"') > 0) empd, " +
						"(SELECT PAYBILLCD disciplinecd, staffctgrycd, employeename, emplnumber, pftaxoption, hraoption, designationcd, paymenttype, bacno, cpfno, cadrecod,cadrecd FROM employeeinfo WHERE empno = '"+psrs.getString("empno")+"') empin," +
						" earndedumaster edm, salhead_ledg_mapping slm " +
						" WHERE edm.earndeducd = slm.earndeducd   AND slm.cadrecod = "+psrs.getString("cadrecod")+"  AND edm.earndeducd = empd.earndeducd(+)  AND edm.TYPE NOT IN  ('TOTPERK', 'EACPF', 'AAICPF', 'AAI-BF', 'CPFAR', 'MISCPK','JVCPF','SALPAY')  " ;						
				log.info("paystopempdetqry "+paystopempdetqry);																						
				ResultSet psrss = db.getRecordSet(paystopempdetqry);
				//ResultSet psrss = db.getRecordSet("select empd.empno, nvl(empd.amount, 0) amount, edm.EARNDEDUCD,edm.EARNDEDUNM, edm.ledgercd,type from (select ps.*, esd.EARNDEDUCD, esd.amount  from (select es.empno, PAYSTOPCD  from EMPPAYSTOP es  where instr(PAYROLLMONTHID, '"+ppinfo.getPayrollmonthid()+"') > 0 and es.empno = '"+psrs.getString("empno")+"') ps, EMPPAYSTOPDET esd  where ps.PAYSTOPCD = esd.PAYSTOPCD) empd,EARNDEDUMASTER edm where edm.EARNDEDUCD = empd.EARNDEDUCD(+) and edm.staffctgrycd = (select staffctgrycd from employeeinfo where empno = '"+psrs.getString("empno")+"') and edm.type not in ('AAI', 'AAICPF')");
				cnt = 0;
				while(psrss.next()){					
					String earndedcd = psrss.getString("EARNDEDUCD");
					String amt = psrss.getString("amount")==null?"0":psrss.getString("amount");;
					String type = psrss.getString("TYPE");
					String ledgercd = psrss.getString("ledgercd");
														
					cnt++;				
										
					if(cnt==1){
						sg1 = psrss.getString("staffctgrycd");
						discd1 = psrss.getString("DISCIPLINECD");
						empname = psrss.getString("EMPLOYEENAME");
						emplnumber = psrss.getString("EMPLNUMBER");
						pftaxopt = psrss.getString("PFTAXOPTION");
						hraoption = psrss.getString("HRAOPTION");
						desgcd = psrss.getString("DESIGNATIONCD");
						paymenttype = psrss.getString("PAYMENTTYPE");
						bacno = psrss.getString("BACNO");
						cpfno = psrss.getString("CPFNO");
						cadrecod = psrss.getString("cadrecod");
						cadrecd = psrss.getString("cadrecd");
						//log.info("******************cadrecod "+cadrecod);
					}
					
					if(type.equals("GPROF")){
						/*if(psrss.getString("GPROFEOLAMOUNT")!=null){
							if(psrss.getDouble("GPROFEOLAMOUNT")!=0){
								amt = psrss.getString("GPROFEOLAMOUNT");
								earntot2 = earntot2+Double.parseDouble(amt);
							}
						}*/
					}
					if(type.equals("E") || type.equals("PK")){
						earntot2 = earntot2+Double.parseDouble(amt);
					}
					if(type.equals("D") || type.equals("OR")){
						dedtot2 = dedtot2+Double.parseDouble(amt);
					}
					//log.info("#############################cadrecod "+cadrecod);
					if(ledgercd.equals(benfund)){
						//dedtot = dedtot+amt;
						//log.info("!!!!!!!!!!!!!!!!!!cadrecod "+cadrecod+" staffctgrymap "+staffctgrymap);
						String aaibfcd = getEarnCdFrmMap(staffctgrymap,cadrecod,"AAIBF");
						String aaibfpayrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+psrss.getString("EMPNO")+"','"+aaibfcd+"','AAI-BF','"+aaibenfund+"','"+amt+"')";
						//log.info("payrolldetQry---AAIBF "+aaibfpayrolldetQry);
						//log.info("AAI BF code "+aaibfcd);
						earnMap = addtoMap(earnMap,aaibfcd,new Double(amt).doubleValue(),discd1,cadrecod,"AAI-BF",0,0,0);
						//stmt.addBatch(aaibfpayrolldetQry);
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,psrss.getString("EMPNO"));
						tpdpstmt6.setString(3,aaibfcd);
						tpdpstmt6.setString(4,"AAI-BF");
						tpdpstmt6.setString(5,aaibenfund);
						tpdpstmt6.setDouble(6,new Double(amt).doubleValue());
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.setDouble(8,0);
						tpdpstmt6.setDouble(9,0);
						tpdpstmt6.addBatch();
					
					}
										
					String payrolldetQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+psrs.getString("EMPNO")+"','"+earndedcd+"','"+type+"','"+psrss.getString("ledgercd")+"','"+amt+"')";
					//log.info(" ::payrolldetQry: "+payrolldetQry);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,psrs.getString("EMPNO"));
					tpdpstmt6.setString(3,earndedcd);
					tpdpstmt6.setString(4,type);
					tpdpstmt6.setString(5,psrss.getString("ledgercd"));
					tpdpstmt6.setDouble(6,new Double(amt).doubleValue());
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					//stmt.addBatch(payrolldetQry);
					
					//String earncd =rs.getString("EARNDEDUCD");
					earnMap = addtoMap(earnMap,earndedcd,new Double(amt).doubleValue(),discd1,cadrecod,type,0,0,0);
					//log.info("****************%%%%%%%%%%%%%%%%%%%%%%%%staffctgrycd "+staffctgrycd+" disciplinecd "+disciplinecd);
				}
				int psn = 0;
				if(psnoMap1.containsKey(discd1)){
					 psn = Integer.parseInt(psnoMap1.get(discd1).toString());
					psn = psn +1;
					psnoMap1.put(discd1,new Integer(psn));
				}else{
					psn = psn+1;
					psnoMap1.put(discd1,new Integer(psn));
				}
				String insertempinfopayqry = "INSERT INTO EMPINFOPAYROLL (EMPINFOPAYROLLCD,PAYTRANSID,PAYSLIPNO, " +
						"PAYROLLMONTHID, EMPNO,EMPLNUMBER, EMPLOYEENAME, PFTAXOPTION, STAFFCTGRYCD, HRAOPTION, " +
						"DESIGNATIONCD, DISCIPLINECD, PAYMENTTYPE, STATIONCD,  BACNO, CPFNO,NETAMOUNT,cadrecod," +
						"cadrecd) values(EMPLOYEEINFOPAYROLL_SEQ.nextval,'"+paytransid+"',"+psn+",'"+ppinfo.getPayrollmonthid()+"','"+psrs.getString("empno")+"','"+emplnumber+"','"+empname+"','"+pftaxopt+"','"+sg1+"','"+hraoption+"','"+desgcd+"','"+discd1+"','"+paymenttype+"','"+ppinfo.getStation()+"','"+bacno+"','"+cpfno+"','"+(earntot2-dedtot2)+"','"+cadrecod+"','"+cadrecd+"') ";
				log.info("insertempinfopayqry in paystop "+insertempinfopayqry);
				stmt.addBatch(insertempinfopayqry);
				psrss.close();
				db.closeRs();
			}
			psrs.close();
			db.closeRs();
			/*
			 * checking DA hike for the selected month
			 */
			String command = "{call dahike_updation(?,?,?)}";     // 4 placeholders
			CallableStatement cstmt = con.prepareCall (command);
			cstmt.setString(1, ppinfo.getPayrollmonthid());
			cstmt.setString(2, ppinfo.getStation());    
			cstmt.setString(3, ppinfo.getUsernm());    
			cstmt.execute();
			cstmt.close();
			/*String chkdaqry = "select DAPERCENTAGE, STAFFCTGRYCD from dapercentage where frommnth='"+ppinfo.getPayrollmonthid()+"' and STATUS='I' and UPDATEDEMPL='N'";
			log.info("checking da hike "+chkdaqry);
			ResultSet dars = db.getRecordSet(chkdaqry);
			while(dars.next()){
				log.info("----category--- "+dars.getString("STAFFCTGRYCD")+" -- percentage---- "+dars.getString("DAPERCENTAGE"));
				//dahikeMap.put(dars.getString("STAFFCTGRYCD"),dars.getString("DAPERCENTAGE"));
				String dahikeQry = "Select emp.EMPNO,emp.EARNDEDUCD, AMOUNT,type,mt.LEDGERCD,mt.type,mt.PERCENTAGEVALUE,ei.STAFFCTGRYCD,mt.DEPENDSON from empernsdeducs emp,earndedumaster mt,EMPLOYEEINFO ei where emp.EARNDEDUCD=mt.EARNDEDUCD and ei.empno = emp.empno and mt.TYPE in('E','D')";
				dahikeQry += " and emp.EMPNO in ("+getEmployeesQuery+") and ei.STAFFCTGRYCD='"+dars.getString("STAFFCTGRYCD")+"' and mt.LEDGERCD in ('"+bundle.getString("EVDA")+"','"+bundle.getString("NEVDA")+"','"+bundle.getString("EMPCPF")+"') and '"+ppinfo.getPayrollmonthid()+"' = (select trim(PAYROLLMONTHID) from monthlypayroll where to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') between PAYROLLFROMDT and  PAYROLLTODT)  order by emp.empno,emp.EARNDEDUCD,mt.TYPE desc";
				log.info("dahikeQry is --"+dahikeQry);
				calcDAHike2Emp(db,dahikeQry,dars.getDouble("DAPERCENTAGE"),ppinfo.getUsernm(),bundle.getString("EMPCPF"));
			}
			db.closeRs();	*/	
			
			/*
			 * Checking whether the incometax recovered for the selected financial year or not
			 */
			String fyrcd = sh.getDescription("monthlypayroll","FYEARCD","PAYROLLMONTHID",ppinfo.getPayrollmonthid(),con);
			HashMap recitmap = new HashMap();
			//ResultSet recitrs = db.getRecordSet(" select STAFFCTGRYCD from staffcategory where STAFFCTGRYCD not in (select STAFFCTGRYCD from empitrec where FYEARCD='"+fyrcd+"')");
			ResultSet recitrs = db.getRecordSet(" select eir.EMPITRECCD from EMPITREC eir where fyearcd = '"+fyrcd+"'");
			int recCd = 0;
			if(recitrs.next()){
				String recd = recitrs.getString("EMPITRECCD");
				if(recd == null){
					recCd = Integer.parseInt(AutoGeneration.getNextCode("empitrec","EMPITRECCD",5,con));
					String recmtqry = " insert into empitrec(EMPITRECCD,STAFFCTGRYCD,USERCD,FYEARCD,PAYROLLMONTHID) values ("+recCd+",'"+recitrs.getString("STAFFCTGRYCD")+"','"+ppinfo.getUsernm()+"','"+fyrcd+"','"+ppinfo.getPayrollmonthid()+"') ";
					log.info("recmtqry mt is  --- "+recmtqry);						
					db.executeUpdate(recmtqry);
				}else{
					recCd = Integer.parseInt(recd);
				}
				recitmap.put(fyrcd,new Integer(recCd));
			}else{
				recCd = Integer.parseInt(AutoGeneration.getNextCode("empitrec","EMPITRECCD",5,con));
				String recmtqry = " insert into empitrec(EMPITRECCD,STAFFCTGRYCD,USERCD,FYEARCD,PAYROLLMONTHID) values ("+recCd+",'','"+ppinfo.getUsernm()+"','"+fyrcd+"','"+ppinfo.getPayrollmonthid()+"') ";
				log.info("recmtqry mt is  --- "+recmtqry);						
				db.executeUpdate(recmtqry);
				recitmap.put(fyrcd,new Integer(recCd));
			}
			recitrs.close();
			db.closeRs();
			/*
			 * Select employee earnings and deductions from empernsdeducs
			 */
			//String getEarnDeductionqry = "Select emp.empno,ei.EMPLNUMBER,epi.RETIREFRMPENSION,ei.cadrecod,ei.EMPLOYEENAME,ei.PFTAXOPTION,ei.HRAOPTION,ei.DESIGNATIONCD,ei.BACNO,ei.CPFNO,ei.PAYMENTTYPE,emp.earndeducd, amount, TYPE, mt.ledgercd, mt.TYPE,mt.percentagevalue, ei.staffctgrycd, ei.disciplinecd from empernsdeducs emp,earndedumaster mt,EMPLOYEEINFO ei,employeepersonalinfo epi where ei.empno=epi.empno and emp.EARNDEDUCD=mt.EARNDEDUCD and ei.empno = emp.empno and mt.TYPE in('E','D','PK','OR') ";
			/* 
			 * block on 03/07/2012
			String getEarnDeductionqry = "select mas.empno, mas.earndeducd, mas.retirefrmpension, mas.amount, mas.type, mas.ledgercd, mas.percentagevalue, mas.EMPLNUMBER, mas.staffctgrycd, mas.cadrecod, mas.cadrecd, mas.disciplinecd, mas.employeename, mas.PFTAXOPTION, mas.HRAOPTION, mas.DESIGNATIONCD, mas.BACNO, mas.CPFNO, mas.PAYMENTTYPE, adjdt.saladjamt saladjamt, adjdt.ARREARS ARREARS, adjdt.RECOVERIES RECOVERIES   From (SELECT emp.empno, emp.earndeducd, epi.retirefrmpension, amount, mt.TYPE, slm.ledgercd, slm.percentagevalue, ei.EMPLNUMBER, ei.staffctgrycd, ei.cadrecod, ei.cadrecd, ei.PAYBILLCD disciplinecd, ei.employeename, ei.PFTAXOPTION, ei.HRAOPTION, ei.DESIGNATIONCD, ei.BACNO, ei.CPFNO, ei.PAYMENTTYPE, 0 saladjamt, 0 ARREARS, 0 RECOVERIES FROM empernsdeducs emp, earndedumaster mt, employeeinfo ei, employeepersonalinfo epi, SALHEAD_LEDG_MAPPING slm WHERE ei.empno = epi.empno  AND emp.earndeducd = mt.earndeducd AND ei.empno = emp.empno and emp.EARNDEDUCD = slm.EARNDEDUCD and slm.CADRECOD = ei.CADRECOD AND mt.TYPE IN ('E', 'D', 'PK', 'OR') and emp.EMPNO in (select emi.empNo from EMPLOYEEINFO emi, EMPLOYEEPERSONALINFO epi   where emi.EMPNO = epi.EMPNO     and emi.empNo is not null and epi.seperationdate is null  and emi.STATIONCD = 'CHQD') and emp.empno not in (select EMPNO from emppaystop   where instr(PAYROLLMONTHID, "+ppinfo.getPayrollmonthid()+") > 0     and status = 'A' and stationcd = 'CHQD')) mas, (SELECT a.empno, b.earndeducd, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '' , b.ADJAMT saladjamt, b.ARREARS ARREARS, b.RECOVERIES RECOVERIES from monthly_saladj     a, monthly_saladj_det b where a.msaladjcd = b.msaladjcd and a.payrollmonthid = "+ppinfo.getPayrollmonthid()+" ) adjdt where  mas.empno=adjdt.empno(+) and mas.earndeducd=adjdt.earndeducd(+) and (amount<>0 or adjdt.saladjamt<>0)";
			getEarnDeductionqry += " and mas.EMPNO in ("+getEmployeesQuery+") and mas.empno not in ("+paystopqry+")     order by to_number(empno)";
			 end  block on 03/07/2012 
             */
			String getEarnDeductionqry ="select a.*,b.* from (SELECT emp.empno empno,ei.EMPLOYEENAME EMPLOYEENAME,ei.EMPLNUMBER EMPLNUMBER,ei.PFTAXOPTION PFTAXOPTION,ei.HRAOPTION HRAOPTION,ei.DESIGNATIONCD DESIGNATIONCD,ei.PAYMENTTYPE PAYMENTTYPE,ei.BACNO BACNO,ei.CPFNO CPFNO , ei.cadrecd cadrecd,emp.earndeducd earndeducd,epi.retirefrmpension retirefrmpension,amount amount,mt.TYPE TYPE,slm.ledgercd ledgercd,slm.percentagevalue percentagevalue,ei.staffctgrycd staffctgrycd,ei.cadrecod cadrecod,ei.PAYBILLCD disciplinecd   FROM empernsdeducs  emp,earndedumaster mt, employeeinfo ei, employeepersonalinfo epi,SALHEAD_LEDG_MAPPING slm  WHERE ei.empno = epi.empno AND emp.earndeducd = mt.earndeducd AND ei.empno = emp.empno and emp.EARNDEDUCD = slm.EARNDEDUCD  and slm.CADRECOD = ei.CADRECOD AND mt.TYPE IN ('E', 'D', 'PK', 'OR')) a,(select ma.empno,mad.adjamt saladjamt,mad.arrears,mad.recoveries,mad.earndeducd from  monthly_saladj ma,monthly_saladj_det mad where ma.msaladjcd=mad.msaladjcd and ma.payrollmonthid="+ppinfo.getPayrollmonthid()+") b where a.empno=b.empno(+) and a.earndeducd=b.earndeducd(+)  and (amount <> 0 or saladjamt <> 0 or arrears <> 0 or recoveries <> 0) and a.EMPNO in ("+getEmployeesQuery+") and a.empno not in ("+paystopqry+")  order by to_number(a.empno)	";
			
			
			log.info("payProcess() getEarnDeductionqry : "+getEarnDeductionqry);
			rs = db.getRecordSet(getEarnDeductionqry);
						
			stmt.addBatch(payrollprocessquery);
			boolean hasEmployees = false;
			double aacpf = 0.0;
			double salpayable= 0.0;
			double earntot = 0.0;
			double dedtot = 0.0;
			double netamt = 0;
			String cpfdedcd = "";
			int cpfpercentage = 0;
			double basicsal = 0;
			String aaicpfQry = "",empcpfQry = "";
			String oldempno = "";
			Map netpaymap = new HashMap();
			Map earndedpaymap = new HashMap();
			
			String en = "";
			String sg = "";
			String scc = "";
			String discd = "";
			String pensioncond ="";
			double basictotal = 0;
			while(rs.next()){
				hasEmployees = true;
				
				en = rs.getString("EMPNO");
				//log.info("**************empno "+en);
				pensioncond = rs.getString("RETIREFRMPENSION");
				if(pensioncond==null){
					pensioncond = "";
				}
				if(!en.equals(oldempno)){
					if(!oldempno.equals("")){
						int psn = 0;
						if(psnoMap.containsKey(discd)){
							 psn = Integer.parseInt(psnoMap.get(discd).toString());
							psn = psn +1;
							psnoMap.put(discd,new Integer(psn));
						}else{
							psn = psn+1;
							psnoMap.put(discd,new Integer(psn));
						}
						String insertempinfopayqry = "INSERT INTO EMPINFOPAYROLL (EMPINFOPAYROLLCD,PAYTRANSID,PAYSLIPNO, PAYROLLMONTHID, EMPNO,EMPLNUMBER, EMPLOYEENAME, PFTAXOPTION, STAFFCTGRYCD, HRAOPTION, DESIGNATIONCD, DISCIPLINECD, PAYMENTTYPE, STATIONCD,  BACNO, CPFNO,NETAMOUNT,cadrecod,cadrecd,GROSSEARNINGS,GROSSDEDUCTIONS) values(EMPLOYEEINFOPAYROLL_SEQ.nextval,'"+paytransid+"','"+psn+"','"+ppinfo.getPayrollmonthid()+"','"+oldempno+"','"+emplnumber+"','"+empname+"','"+pftaxopt+"','"+sg+"','"+hraoption+"','"+desgcd+"','"+discd+"','"+paymenttype+"','"+ppinfo.getStation()+"','"+bacno+"','"+cpfno+"','NETPAY','"+cadrecod+"','"+cadrecd+"','NETEARN','NETDEDU') ";
						
						//stmt.addBatch(insertempinfopayqry);
						//log.info("earntot--"+earntot+"   dedtot--- "+dedtot);
						netpaymap.put(oldempno,insertempinfopayqry+":"+(earntot-dedtot));
						earndedpaymap.put(oldempno,earntot+":"+dedtot);
						
						//log.info("yyyyyyyyyy"+(earntot-dedtot));
						//log.info("XXXXXXXXXX"+dedtot);
						//log.info("insertempinfopayqry888888 "+insertempinfopayqry);
					}
					oldempno = en;
					earntot = 0.0;
					dedtot = 0.0;
					cnt = 0;
				}else {
					cnt++;
					if(cnt==1){ 
						empname = rs.getString("EMPLOYEENAME");
						emplnumber = rs.getString("EMPLNUMBER");
						pftaxopt = rs.getString("PFTAXOPTION");
						hraoption = rs.getString("HRAOPTION");
						desgcd = rs.getString("DESIGNATIONCD");
						paymenttype = rs.getString("PAYMENTTYPE");
						bacno = rs.getString("BACNO");
						cpfno = rs.getString("CPFNO");
						cadrecod = rs.getString("cadrecod");
						cadrecd = rs.getString("cadrecd");
						//log.info("#$%  emplnumber "+emplnumber+" cadrecod "+cadrecod);
					}
				}				
				
				sg = rs.getString("STAFFCTGRYCD");
				discd = rs.getString("DISCIPLINECD");
				 scc = rs.getString("cadrecod");
				if(!emplistMap.containsKey(en)){
					 emplistMap.put(en,scc);
					 //log.info("putting into emplist map scc "+en+" scc "+scc+" sg "+sg);
				}	
				if(!emppensionMap.containsKey(en)){
					emppensionMap.put(en,pensioncond);
				}			
					
				if(!empDiscMap.containsKey(en)){
					empDiscMap.put(en,discd);
				}
				if(!empcadrecodMap.containsKey(en)){
					empcadrecodMap.put(en,scc);
					}
				
				double pamt  = new Double(rs.getString("AMOUNT")).doubleValue();
				double saladjamt = rs.getDouble("saladjamt");
				double arrears = rs.getDouble("ARREARS");
				double recoveries = rs.getDouble("RECOVERIES");
				String ledgercd = rs.getString("LEDGERCD");
				String earndedtype = rs.getString("type");
				
				
				
				if(ledgercd.equals(empcpf)&& rs.getString("TYPE").equals("D")){
					aacpf = aacpf+(pamt+(saladjamt));
					//dedtot = dedtot+aacpf; //summing aaicpf also for calculating salpayable
					//log.info("after aaicpf en "+en+" pamt "+pamt+" dedtot "+dedtot+"earntot "+earntot);
					cpfdedcd = rs.getString("EARNDEDUCD");
					cpfpercentage = new Integer(rs.getString("PERCENTAGEVALUE")).intValue();
					empCpfAdjMap.put(en,new Double(saladjamt));
					empCpfMap.put(en,new Double(pamt));
					//log.info("MAP::en "+en+" pamt "+pamt);
					/*
					 * qry for inserting aaicpf amount into payrolldet
					 */
					
					//aaicpfQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"',(select EARNDEDUCD from EARNDEDUMASTER where LEDGERCD='"+aaicpf+"' and STAFFCTGRYCD='"+sg+"' and type='AAI'),'AAI','"+aaicpf+"','CPFAMT')";	
					//log.info("aaicpfQry "+aaicpfQry);
					//stmt.addBatch(aaicpfQry);
				}
				else if(ledgercd.equals(empcpf) && rs.getString("TYPE").equals("E")){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,rs.getString("EMPNO"));
					tpdpstmt6.setString(3,rs.getString("EARNDEDUCD"));
					tpdpstmt6.setString(4,"E");
					tpdpstmt6.setString(5,ledgercd);
					tpdpstmt6.setDouble(6,pamt);
					tpdpstmt6.setDouble(7,saladjamt);
					tpdpstmt6.setDouble(8,arrears);
					tpdpstmt6.setDouble(9,recoveries); 
					tpdpstmt6.addBatch();
					earnMap = addtoMap(earnMap,rs.getString("EARNDEDUCD"),(pamt),discd,scc,"E",saladjamt,arrears,recoveries);
					
				
				}
				
				/*
				 * inserting sal head values with ledgercd into payrolldet
				 */
				if(ledgercd.equals(benfund)){
					//dedtot = dedtot+pamt;
					String aaibfcd = getEarnCdFrmMap(staffctgrymap,scc,"AAIBF");
					String aaibfpayrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+aaibfcd+"','AAI-BF','"+aaibenfund+"','"+pamt+"')";
					//log.info("payrolldetQry---AAIBF "+aaibfpayrolldetQry);
					//log.info("AAI BF code "+aaibfcd);
					earnMap = addtoMap(earnMap,aaibfcd,pamt,discd,scc,"AAI-BF",0,0,0);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,rs.getString("EMPNO"));
					tpdpstmt6.setString(3,aaibfcd);
					tpdpstmt6.setString(4,"AAI-BF");
					tpdpstmt6.setString(5,aaibenfund);
					tpdpstmt6.setDouble(6,pamt);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					//stmt.addBatch(aaibfpayrolldetQry);
				}
				String payrolldetQry1 = "";
				String purpose ="";
				String vccd = "",vmcd = "";
				boolean vcvmflag = false;
				if(!ledgercd.equals(empcpf)){
//					 Setting values for Vcon(c-Reimb) and Vechicle Maintainance depending upon onTour/Training/MedicalLeave/Eol/Maternity Leave
					/*if(vcvmMap.containsKey(rs.getString("EMPNO"))){
						vcvmflag = true;
						
							//ResultSet vccdrs = db.getRecordSet("select (SELECT EARNDEDUCD FROM CONFIGMAPPING WHERE CONFIGNAME='VCON' AND STAFFCTGRYCD='"+sg+"')vccd,(SELECT EARNDEDUCD FROM CONFIGMAPPING WHERE CONFIGNAME='VM' AND STAFFCTGRYCD='"+sg+"')vmcd from dual ");
						ResultSet vccdrs = db.getRecordSet("select (SELECT EARNDEDUCD FROM CONFIGMAPPING WHERE CONFIGNAME='VCON' )vccd,(SELECT EARNDEDUCD FROM CONFIGMAPPING WHERE CONFIGNAME='VM' )vmcd from dual ");
							
							if(vccdrs.next()){
								vccd = vccdrs.getString("vccd");
								vmcd = vccdrs.getString("vmcd");
							}
							purpose = vcvmMap.get(rs.getString("EMPNO")).toString().trim();
							//if(rs.getString("EARNDEDUCD").equals(vccd) || rs.getString("EARNDEDUCD").equals(vmcd)){
								if(rs.getString("EARNDEDUCD").equals(vccd)){		
							if(purpose.equals("TOUR")||purpose.equals("TRNG")){
									payrolldetQry1 = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+vccd+"','"+rs.getString("TYPE")+"','"+ledgercd+"','0')";
									log.info(" payrolldetQry1: "+payrolldetQry1);
									//stmt.addBatch(payrolldetQry1);
									tpdpstmt6.setInt(1,paytransid);
									tpdpstmt6.setString(2,rs.getString("EMPNO"));
									tpdpstmt6.setString(3,vccd);
									tpdpstmt6.setString(4,rs.getString("TYPE"));
									tpdpstmt6.setString(5,ledgercd);
									tpdpstmt6.setInt(6,0);
									tpdpstmt6.addBatch();
								
									payrolldetQry1 = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+vmcd+"','"+rs.getString("TYPE")+"','"+ledgercd+"','0')";
									log.info(" payrolldetQry1: "+payrolldetQry1);
									//stmt.addBatch(payrolldetQry1);
									tpdpstmt6.setInt(1,paytransid);
									tpdpstmt6.setString(2,rs.getString("EMPNO"));
									tpdpstmt6.setString(3,vmcd);
									tpdpstmt6.setString(4,rs.getString("TYPE"));
									tpdpstmt6.setString(5,ledgercd);
									tpdpstmt6.setInt(6,0);
									tpdpstmt6.addBatch();
								
							}
							if(purpose.equals("MEDCL")||purpose.equals("EOL")||purpose.equals("MATRNTY")){
									payrolldetQry1 = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+vccd+"','"+rs.getString("TYPE")+"','"+ledgercd+"','0')";
									log.info(" payrolldetQry1: "+payrolldetQry1);
									//stmt.addBatch(payrolldetQry1);
									tpdpstmt6.setInt(1,paytransid);
									tpdpstmt6.setString(2,rs.getString("EMPNO"));
									tpdpstmt6.setString(3,vccd);
									tpdpstmt6.setString(4,rs.getString("TYPE"));
									tpdpstmt6.setString(5,ledgercd);
									tpdpstmt6.setInt(6,0);
									tpdpstmt6.addBatch();
									
									payrolldetQry1 = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+vmcd+"','"+rs.getString("TYPE")+"','"+ledgercd+"','"+pamt+"')";
									log.info(" payrolldetQry1: "+payrolldetQry1);
									//stmt.addBatch(payrolldetQry1);
									tpdpstmt6.setInt(1,paytransid);
									tpdpstmt6.setString(2,rs.getString("EMPNO"));
									tpdpstmt6.setString(3,vmcd);
									tpdpstmt6.setString(4,rs.getString("TYPE"));
									tpdpstmt6.setString(5,ledgercd);
									tpdpstmt6.setDouble(6,pamt);
									tpdpstmt6.addBatch();
								
							}
						}else {
							
							String payrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+rs.getString("EARNDEDUCD")+"','"+rs.getString("TYPE")+"','"+ledgercd+"','"+pamt+"')";
							log.info(" payrolldetQry: "+payrolldetQry);
							//stmt.addBatch(payrolldetQry);
							tpdpstmt6.setInt(1,paytransid);
							tpdpstmt6.setString(2,rs.getString("EMPNO"));
							tpdpstmt6.setString(3,rs.getString("EARNDEDUCD"));
							tpdpstmt6.setString(4,rs.getString("TYPE"));
							tpdpstmt6.setString(5,ledgercd);
							tpdpstmt6.setDouble(6,pamt);
							tpdpstmt6.addBatch();
						}
					}*/
					//if(!vcvmflag){
						String payrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+rs.getString("EMPNO")+"','"+rs.getString("EARNDEDUCD")+"','"+rs.getString("TYPE")+"','"+ledgercd+"','"+pamt+"')";
						//log.info(" payrolldetQry: "+payrolldetQry);
						//stmt.addBatch(payrolldetQry);
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,rs.getString("EMPNO"));
						String ecd = rs.getString("EARNDEDUCD");
						tpdpstmt6.setString(3,ecd);
						tpdpstmt6.setString(4,rs.getString("TYPE"));
						tpdpstmt6.setString(5,ledgercd);
						//log.info("!vcvmfla pamt "+pamt);
						
						Object o = maplop.get(rs.getString("EMPNO"));
						double adj = 0;	
						if(o != null){
							String lopstr = o.toString();
							//log.info("lopstr "+lopstr);
							String s[] = lopstr.split(":");
							int hpl = Integer.parseInt(s[0]);
							int eol = Integer.parseInt(s[1]);
							int uaa = Integer.parseInt(s[2]);
							String eolmonth=s[3];
						    	
							CallableStatement pramtcs =
								con.prepareCall("{call getpayamt(?,?,?,?)}");
							pramtcs.setString(1, eolmonth);
							pramtcs.setString(2, ecd);
							pramtcs.setString(3, rs.getString("EMPNO"));
							
							pramtcs.registerOutParameter(4,java.sql.Types.VARCHAR);
							pramtcs.executeQuery();
						    double pramt = pramtcs.getDouble(4);
						    if(pramt==0){
						    	pramt = pamt;
						    }
						    pramtcs.close();
							//log.info("lopstr "+lopstr);						
							if(uaa>0 &&(","+uaaearndedcds+",").indexOf(","+ecd+",")> -1){
								adj = (pramt/maxnoofdaysformoth)*uaa;							
								//pamt = pamt - adj;
							}
							
							if(hpl>0 &&(","+lopearndedcds+",").indexOf(","+ecd+",")> -1 ){
								//log.info("reducing amt "+ecd);
								adj = adj+((pramt/maxnoofdaysformoth)/2)*hpl;							
								//adj = pamt;
								//log.info("reducing amt "+ecd+"HPL adj "+adj+"pamt"+pamt);
							}
							if(eol > 0&&(","+lopearndedcds+",").indexOf(","+ecd+",")> -1){
								adj = adj+(pramt/maxnoofdaysformoth)*eol;
								//log.info("reducing amt "+ecd+"EOL adj "+adj+"pamt"+pamt);
								//pamt = pamt - adj;
							}
							if((","+cpfdepends+",").indexOf(","+ecd+",")>-1){
								Object cempo = cpfbaseamt.get(rs.getString("EMPNO"));
								//log.info("cempo "+cempo);
								if(cempo !=null){
									double a = Double.parseDouble(cpfbaseamt.get(rs.getString("EMPNO")).toString());
									
									double fam = pamt-adj;
									//log.info("existing eamt "+a+" fam "+fam);
									//log.info("adding amt "+(a+fam));
									cpfbaseamt.put(rs.getString("EMPNO"),new Double((double)(a+(fam))));
								}else{
									double fam = pamt-adj;
									//log.info("first tim adding amt "+fam);
									cpfbaseamt.put(rs.getString("EMPNO"),new Double((double)fam));
								}
							}
							adj = Math.round(adj);
							recoveries=recoveries+adj;
							adj = -adj;
							if(saladjamt!=0 )
								adj = adj+(saladjamt);
							
						}else{
							adj=saladjamt;
						}
						
						tpdpstmt6.setDouble(6,pamt);
						tpdpstmt6.setDouble(7,adj);
						tpdpstmt6.setDouble(8,arrears);
						tpdpstmt6.setDouble(9,recoveries);
						tpdpstmt6.addBatch();
						
						if(earndedtype.equalsIgnoreCase("E") || earndedtype.equalsIgnoreCase("PK")){
							earntot = earntot+(pamt+(adj));
							//log.info("EARNING: en "+en+" pamt "+pamt+" dedtot "+dedtot+"earntot "+earntot);
						}
						if(earndedtype.equalsIgnoreCase("D") || earndedtype.equalsIgnoreCase("OR")){
							dedtot = dedtot+(pamt+(adj));
							//log.info("en "+en+" pamt "+pamt+" dedtot "+dedtot+"earntot "+earntot);
						}
						
					//}
					String earncd =rs.getString("EARNDEDUCD");
					/*
					 *  Calculating the total amount for specific ledgercd for earnings/deductions
					 */
					
					/*if(earncd.equalsIgnoreCase("1") && discd.equalsIgnoreCase("1")&&scc.equalsIgnoreCase("2")){
						log.info("adding to tot "+pamt+" pamt "+basictotal+" basictotal");
						basictotal = basictotal+pamt;
					}
					log.info("addtomap %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%scc "+scc+" pamt "+pamt+"earncd "+earncd);*/
					earnMap = addtoMap(earnMap,earncd,(pamt),discd,scc,earndedtype,adj,arrears,recoveries);
				}
				else if(ledgercd.equals(empcpf) && earndedtype.equalsIgnoreCase("E")){
					earntot = earntot+(pamt+(saladjamt));
					earnMap = addtoMap(earnMap,rs.getString("EARNDEDUCD"),(pamt),discd,scc,earndedtype,saladjamt,0,0);
				}
				
				
			}
			if(!oldempno.equals("")){
				//
				int psn = 0;
				if(psnoMap.containsKey(discd)){
					 psn = Integer.parseInt(psnoMap.get(discd).toString());
					psn = psn +1;
					psnoMap.put(discd,new Integer(psn));
				}else{
					psn = psn+1;
					psnoMap.put(discd,new Integer(psn));
				}
				String insertempinfopayqry = "INSERT INTO EMPINFOPAYROLL (EMPINFOPAYROLLCD,PAYTRANSID,PAYSLIPNO, PAYROLLMONTHID, EMPNO,EMPLNUMBER, EMPLOYEENAME, PFTAXOPTION, STAFFCTGRYCD, HRAOPTION, DESIGNATIONCD, DISCIPLINECD, PAYMENTTYPE, STATIONCD,  BACNO, CPFNO,NETAMOUNT,cadrecod,GROSSEARNINGS,GROSSDEDUCTIONS) values(EMPLOYEEINFOPAYROLL_SEQ.nextval,'"+paytransid+"','"+psn+"','"+ppinfo.getPayrollmonthid()+"','"+oldempno+"','"+emplnumber+"','"+empname+"','"+pftaxopt+"','"+sg+"','"+hraoption+"','"+desgcd+"','"+discd+"','"+paymenttype+"','"+ppinfo.getStation()+"','"+bacno+"','"+cpfno+"','NETPAY','"+cadrecod+"','NETEARN','NETDEDU') ";
				//log.info("insertempinfopayqry "+insertempinfopayqry);
				//stmt.addBatch(insertempinfopayqry);
				//log.info("earntot--"+earntot+"   dedtot--- "+dedtot);
				netpaymap.put(oldempno,insertempinfopayqry+":"+(earntot-dedtot));
				earndedpaymap.put(oldempno,earntot+":"+dedtot);
			}
			rs.close();
			db.closeRs();
			//log.info("after initial earn&ded earntot "+earntot+" dedtot "+dedtot);
			int recdetcd = 0;
			recdetcd = Integer.parseInt(AutoGeneration.getNextCode("empitrecdet","EMPITRECDETCD",5,con));
			
			///log.info("*****************bastot "+basictotal);
			/*
			 *  code for inserting OTA and Electricty charges for the payroll month
			 * 
			 */
			Map otamap = new HashMap();
			Map elcmap = new HashMap();
			Map watermap = new HashMap();
			Map consmap = new HashMap();
			Map ota1map = new HashMap();
			Map hrrmap = new HashMap();
//			*************************************************
		/*	Map otanewmap = new HashMap();
			
			String otanewquery = "SELECT dt.nett nett, dt.empno empno, mt.PAYBILLCD PAYBILLCD,ei.STAFFCTGRYCD STAFFCTGRYCD  from otatransaction_new_mt mt, otatransaction_new_dt dt, employeeinfo ei where mt.TRANSACTIONNEWID = dt.TRANSACTIONNEWID and ei.empno = dt.empno  ";
			otanewquery += " and mt.PAYROLLMONTHID ='" + ppinfo.getPayrollmonthid() + "'";
			log.info("otanewquery    ***"+otanewquery);
			rs = db.getRecordSet(otanewquery);
			
			while(rs.next()){
				double amt = rs.getDouble("nett");
				String eno = rs.getString("empno");
				String discp = rs.getString("PAYBILLCD");
				
				String scg = rs.getString("STAFFCTGRYCD");
				//String otacd1 = getEarnCdFrmMap(staffctgrymap,scg,"OTANEW");
				String otacd1 = "183";
				
				earnMap = addtoMap(earnMap,otacd1,amt,discp,scg,"E");
				otanewmap.put(eno,new Double(amt));
			
				tpdpstmt6.setInt(1,paytransid);
				tpdpstmt6.setString(2,eno);
				tpdpstmt6.setString(3,otacd1);
				tpdpstmt6.setString(4,"E");
				tpdpstmt6.setString(5,"0");
				tpdpstmt6.setDouble(6,amt);
				tpdpstmt6.setDouble(7,0);
				tpdpstmt6.addBatch();
				
			}
			rs.close();
			db.closeRs();*/
			//*************************************************
			CallableStatement elotacsttmt =
				con.prepareCall("begin  getelotadata(?,?); end;");
			elotacsttmt.setString(1, ppinfo.getPayrollmonthid());
			elotacsttmt.registerOutParameter(2,oracle.jdbc.driver.OracleTypes.CURSOR);
			elotacsttmt.executeQuery();
			ResultSet refrs = (ResultSet)elotacsttmt.getObject(2);
			while(refrs.next()){
				
				String eno = refrs.getString("empno");
				double amt = refrs.getDouble("amount");
				double adj = refrs.getDouble("adj");
				double arrears = refrs.getDouble("arr");
				double recoveries = refrs.getDouble("rec");
				String scg = refrs.getString("STAFFCTGRYCD");
				String discp = refrs.getString("PAYBILLCD");
				String type =refrs.getString("type"); 
				String otacd1 = getEarnCdFrmMap(staffctgrymap,scg,"OTA");
				String elecreccd = getEarnCdFrmMap(staffctgrymap,scg,"ELECREC");
				String watercd = getEarnCdFrmMap(staffctgrymap,scg,"WATER");
				String conscd = getEarnCdFrmMap(staffctgrymap,scg,"CONS");
				String ota1cd = getEarnCdFrmMap(staffctgrymap,scg,"OTA1");
				String hrrcd = getEarnCdFrmMap(staffctgrymap,scg,"HRR");
				
				if(type.equalsIgnoreCase("OTA")){
					earnMap = addtoMap(earnMap,otacd1,amt,discp,scg,"E",adj,arrears,recoveries);
					otamap.put(eno,new Double(amt));
					
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,eno);
					tpdpstmt6.setString(3,otacd1);
					tpdpstmt6.setString(4,"OTA");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,amt);
					tpdpstmt6.setDouble(7,adj);
					tpdpstmt6.setDouble(8,arrears);
					tpdpstmt6.setDouble(9,recoveries);
					tpdpstmt6.addBatch();
				}else if(type.equalsIgnoreCase("ELEC") ){
					earnMap = addtoMap(earnMap,elecreccd,amt,discp,scg,"D",adj,arrears,recoveries);
					elcmap.put(eno,new Double(amt));	
					
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,eno);
					tpdpstmt6.setString(3,elecreccd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,amt);
					tpdpstmt6.setDouble(7,adj);
					tpdpstmt6.setDouble(8,arrears);
					tpdpstmt6.setDouble(9,recoveries);
					tpdpstmt6.addBatch();
				}else if(type.equalsIgnoreCase("WATER") ){
					
					earnMap = addtoMap(earnMap,watercd,amt,discp,scg,"D",adj,arrears,recoveries);
					watermap.put(eno,new Double(amt));
					
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,eno);
					tpdpstmt6.setString(3,watercd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,amt);
					tpdpstmt6.setDouble(7,adj);
					tpdpstmt6.setDouble(8,arrears);
					tpdpstmt6.setDouble(9,recoveries);
					// log.info(eno+"------3----ELEC");
					tpdpstmt6.addBatch();
				}else if(type.equalsIgnoreCase("CONS") ){
				
				earnMap = addtoMap(earnMap,conscd,amt,discp,scg,"D",adj,arrears,recoveries);
				consmap.put(eno,new Double(amt));
				
				tpdpstmt6.setInt(1,paytransid);
				tpdpstmt6.setString(2,eno);
				tpdpstmt6.setString(3,conscd);
				tpdpstmt6.setString(4,"D");
				tpdpstmt6.setString(5,"");
				tpdpstmt6.setDouble(6,amt);
				tpdpstmt6.setDouble(7,adj);
				tpdpstmt6.setDouble(8,arrears);
				tpdpstmt6.setDouble(9,recoveries);
				// log.info(eno+"------3----ELEC");
				tpdpstmt6.addBatch();
			}else if(type.equalsIgnoreCase("OTA1") ){
				
				earnMap = addtoMap(earnMap,ota1cd ,amt,discp,scg,"E",adj,arrears,recoveries);
				ota1map.put(eno,new Double(amt));
				
				tpdpstmt6.setInt(1,paytransid);
				tpdpstmt6.setString(2,eno);
				tpdpstmt6.setString(3, ota1cd);
				tpdpstmt6.setString(4,"E");
				tpdpstmt6.setString(5,"");
				tpdpstmt6.setDouble(6,amt);
				tpdpstmt6.setDouble(7,adj);
				tpdpstmt6.setDouble(8,arrears);
				tpdpstmt6.setDouble(9,recoveries);
				// log.info(eno+"------3----ELEC");
				tpdpstmt6.addBatch();
			}else if(type.equalsIgnoreCase("HRR") ){
				
				earnMap = addtoMap(earnMap,hrrcd ,amt,discp,scg,"D",adj,arrears,recoveries);
				hrrmap.put(eno,new Double(amt));
				
				tpdpstmt6.setInt(1,paytransid);
				tpdpstmt6.setString(2,eno);
				tpdpstmt6.setString(3, hrrcd);
				tpdpstmt6.setString(4,"D");
				tpdpstmt6.setString(5,"");
				tpdpstmt6.setDouble(6,amt);
				tpdpstmt6.setDouble(7,adj);
				tpdpstmt6.setDouble(8,arrears);
				tpdpstmt6.setDouble(9,recoveries);
				// log.info(eno+"------3----ELEC");
				tpdpstmt6.addBatch();
			}
			}
			refrs.close();
			elotacsttmt.close();
			db.closeRs();
			refrs = null;
			elotacsttmt=null;
			Iterator empite = emplistMap.keySet().iterator();
			double earntot1 = 0.0;
			double dedtot1 = 0.0;
			while(empite.hasNext()){
				
				earntot1 = 0.0;
				dedtot1 = 0.0;
				
				String empno = empite.next().toString();//key
				//log.info("empno"+empno);
				String scg = emplistMap.get(empno).toString();//value
				//log.info("scg "+scg);
				
				String empcc = empcadrecodMap.get(empno).toString();//value
				
				String disciplinecd = empDiscMap.get(empno).toString();
				//log.info("########### empno "+empno+" empcc "+empcc+" disciplinecd "+disciplinecd);
				
				String arrearqry = " SELECT ai.arrearamt, ai.EMPARREARCD, arrearnm, ar.ledgercd, " +
						" (SELECT earndeducd FROM earndedumaster  WHERE ledgercd = ar.ledgercd )earndedcd" +
						"  FROM EMPARREARSINFO ai, arrears ar, EMPARREARS ad1 " +
						" WHERE ai.EMPARREARCD IN ( SELECT EMPARREARCD   FROM EMPARREARS aad  WHERE payrollmonthid = '"+ppinfo.getPayrollmonthid()+"')" +
						"   AND ar.arrearscd = ai.arrearscd AND ai.EMPARREARCD = ad1.EMPARREARCD and empno = '"+empno+"' ";
				//log.info("getting arreas "+arrearqry);
				/*ResultSet arrearsrs = db.getRecordSet(arrearqry);
				double arrearAmt = 0.0;
				boolean hasArrears = false;
				double totalarrears = 0.0;
				while(arrearsrs.next()){
					hasArrears = true;
					String ledgercd = arrearsrs.getString("LEDGERCD");
					arrearAmt = arrearsrs.getDouble("ARREARAMT");
					String eardedcd = arrearsrs.getString("earndedcd");
					totalarrears+=arrearAmt;
					//log.info("arrearAmt is --------- "+arrearAmt);
					if(ledgercd == null)
						ledgercd = "";
					String payrolldetQry1 = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+arrearsrs.getString("earndedcd")+"','AR','"+ledgercd+"','"+arrearAmt+"')";
					log.info("inserting arrrears "+payrolldetQry1);
					//stmt.addBatch(payrolldetQry1);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,arrearsrs.getString("earndedcd"));
					tpdpstmt6.setString(4,"AR");
					tpdpstmt6.setString(5,ledgercd);
					tpdpstmt6.setDouble(6,arrearAmt);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.addBatch();
					
					earnMap = addtoMap(earnMap,eardedcd,arrearAmt,disciplinecd,empcc,"E");
					
					 * calculating cpf  for the arrear amount
					 
					double cpfarrearamt = 0.0;
					if(arrearAmt > 0)
						cpfarrearamt = Math.round((arrearAmt*cpfpercentage)/100);
					double cpfaaiarrearamt = cpfarrearamt;
					dedtot1 = dedtot1+cpfarrearamt;
					
					 * query for inserting employee cpf arreat amount
					 
										
					String empcpfearndedcd = getEarnCdFrmMap(staffctgrymap,scg,"CPFAR");
					earnMap = addtoMap(earnMap,empcpfearndedcd,cpfarrearamt,disciplinecd,empcc,"D");
															
					//log.info("empcpfearndedcd "+empcpfearndedcd+" aaicpfearndedcd "+aaicpfearndedcd);
					String ecpfarrqry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+empcpfearndedcd+"','EACPF','"+empcpf+"','"+cpfarrearamt+"')";
					log.info("ecpfarrqry "+ecpfarrqry);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,empcpfearndedcd);
					tpdpstmt6.setString(4,"EACPF");
					tpdpstmt6.setString(5,empcpf);
					tpdpstmt6.setDouble(6,cpfarrearamt);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.addBatch();
					//stmt.addBatch(ecpfarrqry);
					earntot1 = earntot1+arrearAmt;
					
					 * query for inserting AAI cpf arreat amount
					 
					String pension = "";
					pension = emppensionMap.get(empno).toString();
					
					 * This loop is necessary if employee crossed 58 years and CPF amount from AAi is not added 
					 * and selected the option 'Retire From Pension'
					 
					if(!pension.equals("Y")){
						String aaicpfearndedcd = getEarnCdFrmMap(staffctgrymap,scg,"AAICPFAR");
						String aaiarrcpfQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+aaicpfearndedcd+"','AAICPF','"+aaicpf+"','"+cpfaaiarrearamt+"')";
						log.info("aaiarrcpfQry "+aaiarrcpfQry);
						//stmt.addBatch(aaiarrcpfQry);
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,empno);
						tpdpstmt6.setString(3,aaicpfearndedcd);
						tpdpstmt6.setString(4,"AAICPF");
						tpdpstmt6.setString(5,aaicpf);
						tpdpstmt6.setDouble(6,cpfaaiarrearamt);
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.addBatch();
						earnMap = addtoMap(earnMap,aaicpfearndedcd,cpfaaiarrearamt,disciplinecd,empcc,"D");
												
						dedtot1 = dedtot1+cpfaaiarrearamt;
					}
				}
				if(!hasArrears){
					//String payrolldetQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(SELECT earndeducd FROM earndedumaster  WHERE ledgercd = (select LEDGERCD from arrears where ARREARNM='DA Arrears') ),'AR',(select LEDGERCD from arrears where ARREARNM='DA Arrears'),'0')";
					String payrolldetQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(SELECT earndeducd FROM SALHEAD_LEDG_MAPPING   WHERE ledgercd = (select LEDGERCD from arrears where ARREARNM='DA Arrears') and cadrecod="+empcc+"),'AR',(select LEDGERCD from arrears where ARREARNM='DA Arrears'),'0')";
					log.info("!hasArrears payrolldetQry "+payrolldetQry);
					//stmt.addBatch(payrolldetQry);
					noarrpstmt.setInt(1,paytransid);
					noarrpstmt.setString(2,empno);
					noarrpstmt.setString(3,empcc);
					noarrpstmt.setDouble(4,0);
					noarrpstmt.addBatch();
					
				}
				db.closeRs();*/
				/*
				 * Calculating Income tax of an employee
				 */
				//List prjlist = IncomeTaxDAO.getInstance().empProjectedITInfo(empno,String.valueOf(ppinfo.getFinancialyearcd()),db);
				//List prjlist = IncomeTaxDAO.getInstance().empProjectedITInfo(empno,fyrcd,db);
				//ProjectedIncomeTaxInfo prjinfo= (ProjectedIncomeTaxInfo)prjlist.get(0);
				double taxamt = 0.0;
				double ptax = 0;
				String itaxqry = "select incometax,ptax from empincometax eit,empincometaxdet eitd  where eit.EMPINCOMETAXCD = eitd.EMPINCOMETAXCD and eit.PAYROLLMONTHID = '"+ppinfo.getPayrollmonthid()+"'    and eitd.EMPNO= '"+empno+"'";
				//log.info("get incometax qry: "+itaxqry);
				
				ResultSet itrss = db.getRecordSet(itaxqry);
				//ResultSet itrss = db.getRecordSet("select incometax from empincometaxdet dt,empincometax m where m.empincometaxcd=m.empincometaxcd and EMPNO='"+empno+"' and m.PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"' ");
				if(itrss.next()){
					taxamt = itrss.getDouble("INCOMETAX");
				//}else{
					
					ptax = itrss.getDouble("PTAX");
				}
				itrss.close();
				db.closeRs();
				//String itqry = "select EARNDEDUCD,LEDGERCD,EARNDEDUNM,type from earndedumaster where type='IT' ";
				String itqry = "select em.EARNDEDUCD,LEDGERCD,EARNDEDUNM,em.type  from earndedumaster em, SALHEAD_LEDG_MAPPING slm where em.EARNDEDUCD = slm.EARNDEDUCD and  em.type='IT' and slm.cadrecod="+empcc;
				//log.info(db+"    getting itqry  "+itqry);
				ResultSet itrs = db.getRecordSet(itqry);
				String ledcd = "";
				String edcd = "";
				String type = "",recitqry="";
				while(itrs.next()){
					ledcd = itrs.getString("LEDGERCD");
					edcd = itrs.getString("EARNDEDUCD");
					type = itrs.getString("type");
				
					if(ledcd == null)
						ledcd = "";
					String payrolldetQry = "";
					
					if(prop1.getProperty("IT").equals(ledcd)){
						payrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+edcd+"','"+type+"','"+ledcd+"','"+taxamt+"')";
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,empno);
						tpdpstmt6.setString(3,edcd);
						tpdpstmt6.setString(4,type);
						tpdpstmt6.setString(5,ledcd);
						tpdpstmt6.setDouble(6,taxamt);
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.setDouble(8,0);
						tpdpstmt6.setDouble(9,0);
						tpdpstmt6.addBatch();
						dedtot1 = dedtot1+taxamt;
						/*if(recitmap.containsKey(fyrcd)){
							recitqry = "Insert into empitrecdet(EMPITRECDETCD,EMPITRECCD,EMPNO,ITRECAMT,STATUS) values('"+recdetcd+"','"+recitmap.get(scg)+"','"+empno+"','"+taxamt+"','A')";
						}else {
							recitqry = "Update empitrecdet set ITRECAMT=ITRECAMT+"+taxamt+" where EMPNO='"+empno+"' and EMPITRECCD=(select EMPITRECCD from empitrec where fyearcd='"+fyrcd+"' and STAFFCTGRYCD='"+scg+"')";
						}*/
						/*PreparedStatement mergeitpstmt = con.prepareStatement(" merge into EMPITRECDET ed using dual on (dual.dummy is not null and ed.empno = ? and ed.EMPITRECCD = ?) " +
								" when not matched then insert values(?,?,?,?,?,'A',sysdate) " +
										" when matched then update set ed.ITRECAMT = ed.ITRECAMT+ ?");*/
						
						recitqry =  " merge into EMPITRECDET ed using dual on (dual.dummy is not null and ed.empno = '"+empno+"' and ed.EMPITRECCD = '"+recitmap.get(fyrcd)+"') " +
								" when not matched then insert values('"+recdetcd+"','"+recitmap.get(fyrcd)+"','"+empno+"','"+taxamt+"','"+ppinfo.getPayrollmonthid()+"','A',sysdate) " +
										" when matched then update set ed.ITRECAMT = ed.ITRECAMT+ "+taxamt;
						//log.info("recitqry det s  --- "+recitqry);
						mergeitpstmt.setString(1,empno);
						mergeitpstmt.setString(2,recitmap.get(fyrcd).toString());
						mergeitpstmt.setInt(3,recdetcd);
						mergeitpstmt.setString(4,recitmap.get(fyrcd).toString());
						mergeitpstmt.setString(5,empno);
						mergeitpstmt.setDouble(6,taxamt);
						mergeitpstmt.setString(7,ppinfo.getPayrollmonthid());
						mergeitpstmt.setDouble(8,taxamt);
						mergeitpstmt.addBatch();
						//stmt.addBatch(recitqry);
					}
					else{
						taxamt = ptax;
						payrolldetQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+edcd+"','"+type+"','"+ledcd+"','"+taxamt+"')";
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,empno);
						tpdpstmt6.setString(3,edcd);
						tpdpstmt6.setString(4,type);
						tpdpstmt6.setString(5,ledcd);
						tpdpstmt6.setDouble(6,taxamt);
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.setDouble(8,0);
						tpdpstmt6.setDouble(9,0);
						tpdpstmt6.addBatch();
						dedtot1 = dedtot1+taxamt;
					}
					//log.info("inserting tax qry is  "+payrolldetQry);
					//stmt.addBatch(payrolldetQry);
					/*
					 * calculating the total income tax for the empoyee and setting earnMap
					 */
					//earnMap = addtoMap(earnMap,edcd,taxamt,disciplinecd,scg,"D");
					earnMap = addtoMap(earnMap,edcd,taxamt,disciplinecd,empcc,"D",0,0,0);
					
					
				//}
				}
				itrs.close();
				db.closeRs();
				//log.info("after income tax earntot1 "+earntot1+" dedtot1 "+dedtot1);
				
				/*
				 *  Getting Gprof Amount for that month
				 */
				String gprofamt = "";
				
				/*String gprofqry = " select dd.* from (SELECT empno, gpfdet.cadrecd, gpfdet.gprofamount, gpfamt.payrollmonthid, " +
				" (SELECT gprofeolamount FROM gprofeoldata gprofeol  WHERE gprofeol.payrollmonthid = "+ppinfo.getPayrollmonthid()+" AND gprofeol.empno = '"+empno+"' ) eolamt," +
				" (select EARNDEDUCD from EARNDEDUMASTER where STAFFCTGRYCD='"+scg+"' and type='GPROF')eard "+
				"  FROM employeeinfo ei, gprofamount gpfamt,  gprofamountdet gpfdet " +
				" WHERE gpfdet.cadrecd = ei.cadrecd  " +
				" AND gpfdet.gprofamtcd = gpfamt.gprofamtcd " +
				" AND gpfamt.payrollmonthid = "+ppinfo.getPayrollmonthid()+")dd where empno = '"+empno+"'  " +						
				"";*/
				/*String gprofqry = " select emg.empno,emg.GPROFAMT as gprofamount,emg.ADJUSTMENTS, (SELECT gprofeolamount FROM gprofeoldata gprofeol  WHERE gprofeol.payrollmonthid = "+ppinfo.getPayrollmonthid()+" AND gprofeol.empno = '"+empno+"')eolamt,(select EARNDEDUCD from EARNDEDUMASTER where   type='GPROF')eard" +
				" from employeeinfo ei,EMP_MONTHLY_GPROF emg where ei.EMPNO = emg.EMPNO and emg.PAYROLLMONTHID = "+ppinfo.getPayrollmonthid()+" and emg.empno = '"+empno+"' ";*/
				String gprofqry = " select emg.empno,emg.GPROFAMT as gprofamount,emg.ADJUSTMENTS, (select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL where PAYROLLMONTHID  = emg.GPROFAMTMONTH)gprofmaxdays,(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL where PAYROLLMONTHID  = to_number(emg.GPROFAMTMONTH)-1)adjgprofmaxdays,(select EARNDEDUCD from EARNDEDUMASTER where type='GPROF')eard" +
				" ,(select sum(hpl)||','||sum(uaa)||','||sum(eol)eol from LOP where empno = emg.EMPNO and eolmonth=emg.GPROFAMTMONTH)eol "+
				" ,(select sum(hpl)||','||sum(uaa)||','||sum(eol)eol from LOP where empno = emg.EMPNO and eolmonth=to_number(emg.GPROFAMTMONTH)-1)adjeol "+				
				" , nvl((select ADJAMT from MONTHLY_SALADJ ms,MONTHLY_SALADJ_DET msd where ms.MSALADJCD = msd.MSALADJCD and ms.empno = '"+empno+"'  and ms.payrollmonthid="+ppinfo.getPayrollmonthid()+" and msd.earndeducd=(select EARNDEDUCD from EARNDEDUMASTER where type='GPROF')),0)monsaladj"+
				" , nvl((select ARREARS from MONTHLY_SALADJ ms,MONTHLY_SALADJ_DET msd where ms.MSALADJCD = msd.MSALADJCD and ms.empno = '"+empno+"'  and ms.payrollmonthid="+ppinfo.getPayrollmonthid()+" and msd.earndeducd=(select EARNDEDUCD from EARNDEDUMASTER where type='GPROF')),0)monsaladjarr"+
				" , nvl((select RECOVERIES from MONTHLY_SALADJ ms,MONTHLY_SALADJ_DET msd where ms.MSALADJCD = msd.MSALADJCD and ms.empno = '"+empno+"'  and ms.payrollmonthid="+ppinfo.getPayrollmonthid()+" and msd.earndeducd=(select EARNDEDUCD from EARNDEDUMASTER where type='GPROF')),0)monsaladjrec"+
				" from employeeinfo ei,EMP_MONTHLY_GPROF emg where ei.EMPNO = emg.EMPNO and emg.PAYROLLMONTHID = "+ppinfo.getPayrollmonthid()+" and emg.empno = '"+empno+"' ";
				//log.info("gprofquery "+gprofqry);
				
				ResultSet rsgprof = db.getRecordSet(gprofqry);
				String gprofeardedcd = ""; 
				double ghpl = 0;
				double geol = 0;
				double guaa = 0;
				double adjghpl = 0;
				double adjgeol = 0;
				double adjguaa = 0;
				String glopstr = "";
				String adjglopstr = "";
				double gprofmaxdays = 0;
				double adjgprofmaxdays = 0;
				double saladj1 = 0;
				double monsaladjarr = 0;
				double monsaladjrec = 0;
				boolean glopay = false;
				boolean adjglopay = false;
				double adj1 =0;
				
				while(rsgprof.next()){
					
					String gprofamount = rsgprof.getString("gprofamount");
					//String eolamt = rsgprof.getString("eolamt");
					gprofeardedcd = rsgprof.getString("eard");
					gprofmaxdays = rsgprof.getInt("gprofmaxdays");
					adjgprofmaxdays = rsgprof.getDouble("adjgprofmaxdays");
					saladj1 = rsgprof.getInt("monsaladj");
					monsaladjarr = rsgprof.getDouble("monsaladjarr");
					monsaladjrec = rsgprof.getDouble("monsaladjrec");
					/*if(eolamt!= null ){
						gprofamt = eolamt;
					}else{
						gprofamt = gprofamount;
					}*/
					//log.info("gprofamount "+gprofamount+"  eolamt "+eolamt+" gprofamt "+gprofamt);
					glopstr = rsgprof.getString("eol");
					 adjglopstr = rsgprof.getString("adjeol");
						String a[] = glopstr.split(",");
						String b[] = adjglopstr.split(",");					
					if(a.length>2){
						ghpl = Integer.parseInt(a[0]);
						guaa  =  Integer.parseInt(a[1]);
						geol = Integer.parseInt(a[2]);
						glopay = true;
					}
					if(b.length>2){
						adjghpl = Double.parseDouble(b[0]);
						adjguaa  =  Double.parseDouble(b[1]);
						adjgeol = Double.parseDouble(b[2]);
						adjglopay = true;
					}
					//log.info("gprofamount "+gprofamount+" gprofamt "+gprofamt);
					if(gprofamt.trim().equals("")){
						gprofamt = "0";
					}
					int gpamt =  Integer.parseInt(gprofamount);
				
					 adj1 = rsgprof.getInt("ADJUSTMENTS");
					 gprofamt = (gpamt)+"";
				}
				rsgprof.close();
				db.closeRs();
				if(gprofamt.trim().equals("")){
					gprofamt = "0";
				}
				double gamt = Integer.parseInt(gprofamt);
					Object o1 = maplop.get(empno);
					double adj = 0;	
					double gprofadj = 0;
					if(glopay ){
						
						if(guaa >0 &&(","+uaaearndedcds+",").indexOf(","+gprofeardedcd+",")> -1){
							adj = (gamt/gprofmaxdays)*guaa;		
							//pamt = pamt - adj;
						}
						
						if(ghpl >0 &&(","+lopearndedcds+",").indexOf(","+gprofeardedcd+",")> -1 ){
							//log.info("reducing amt "+gprofeardedcd);
							//	adj = adj+Math.round(((gamt/gprofmaxdays)/2)*ghpl);
							adj = adj+((gamt/gprofmaxdays)*Math.round(ghpl/2));
							//adj = adj+((gamt/gprofmaxdays))*ghpl;							
							//adj = pamt;
							//log.info("amt "+gamt+"HPL adj "+adj+"pamt"+gamt);
						}
						if(geol >0 &&(","+lopearndedcds+",").indexOf(","+gprofeardedcd+",")> -1){
							adj = adj+((gamt/gprofmaxdays)*geol);							
							//log.info("reducing gamt "+gamt+"gprofmaxdays"+gprofmaxdays+"EOL adj "+adj+" lop dyas "+geol+" (gamt/gprofmaxdays) "+gamt/gprofmaxdays+" (gamt/gprofmaxdays)*geol "+(gamt/gprofmaxdays)*geol);
							//pamt = pamt - adj;
						}
						//log.info("inside glopst adj "+adj+"saladj1 "+saladj1);
						
							adj = Math.round(adj);
							adj = -adj;
							if(monsaladjarr!=0)
								adj = adj+(monsaladjarr);
				}else{
					adj = monsaladjarr;
				}
					
					if(adjglopay ){
						
						//if(adjguaa >0 &&(","+uaaearndedcds+",").indexOf(","+gprofeardedcd+",")> -1){	
						if(adjguaa >0){
							gprofadj=(adj1/adjgprofmaxdays)*adjguaa;
							//log.info("adj in uaa lop is :"+adj1);
							//log.info("max days in uaa lop is :"+adjgprofmaxdays);
							//log.info("adjguaa in uaa lop is :"+adjguaa);
							//log.info("inside adj lop uaa:"+gprofadj);
						}
						
						//if(adjghpl >0 &&(","+lopearndedcds+",").indexOf(","+gprofeardedcd+",")> -1 ){	
						if(adjghpl >0){
							//gprofadj=gprofadj+(adj1/adjgprofmaxdays)*adjghpl;
							gprofadj=gprofadj+((adj1/adjgprofmaxdays)/2)*adjghpl;
							//log.info("adj in uaa lop is :"+adj1);
							//log.info("max days in uaa lop is :"+adjgprofmaxdays);
							//log.info("adjguaa in uaa lop is :"+adjghpl);
							//log.info("inside adj lop uaa:"+gprofadj);
						}
						//if(adjgeol >0 &&(","+lopearndedcds+",").indexOf(","+gprofeardedcd+",")> -1){	
						if(adjgeol >0){
								gprofadj=gprofadj+(adj1/adjgprofmaxdays)*adjgeol;
								//log.info("adj in uaa lop is :"+adj1);
								//log.info("max days in uaa lop is :"+adjgprofmaxdays);
								//log.info("adjguaa in uaa lop is :"+adjgeol);
								//log.info("inside adj lop uaa:"+gprofadj);
							
						}					
						gprofadj=adj1-gprofadj;
					}else{					
						gprofadj = adj1;
					}
					//log.info("adj lop gprof :"+gprofadj);
					adj+=gprofadj;
					//long fgamt = (gamt+adj)<0?0:(Math.round(gamt)+Math.round(adj));
					double fgamt = gamt+adj;
				earntot1 = earntot1+fgamt;				
				//log.info(" gprofamt "+gprofamt);
				//String insertgrofqry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select EARNDEDUCD from EARNDEDUMASTER where type='GPROF'),'E',(select ledgercd from EARNDEDUMASTER where  type='GPROF'),'"+gprofamt+"')";
				String insertgrofqry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select EARNDEDUCD from EARNDEDUMASTER where  type='GPROF'),'E',(select ledgercd from SALHEAD_LEDG_MAPPING where  cadrecod="+empcc+" and type='GPROF'),'"+gprofamt+"')";
				//log.info("insertgrofqry "+insertgrofqry);
				gprofpstmt.setInt(1,paytransid);
				gprofpstmt.setString(2,empno);
				gprofpstmt.setString(3,empcc);
				gprofpstmt.setString(4,Math.round(gamt)+"");
				gprofpstmt.setDouble(5,Math.round(adj));
				gprofpstmt.setDouble(6,monsaladjarr);
				gprofpstmt.setDouble(7,monsaladjrec);
				gprofpstmt.addBatch();
				
				//stmt.addBatch(insertgrofqry);
				//earnMap = addtoMap(earnMap,gprofeardedcd,new Double(gprofamt).doubleValue(),disciplinecd,scg,"E");
				earnMap = addtoMap(earnMap,gprofeardedcd,gamt,disciplinecd,empcc,"E",adj,monsaladjarr,monsaladjrec);
				
				
				/*
				 * adding electricity charges /OTA amount
				 */
				/*double otaamount = Double.parseDouble(otamap.get(empno)==null?"0":otamap.get(empno).toString());
				double elecamount = Double.parseDouble(elcmap.get(empno)==null?"0":elcmap.get(empno).toString());
				double wateramount = Double.parseDouble(watermap.get(empno)==null?"0":watermap.get(empno).toString());
				double consamount = Double.parseDouble(consmap.get(empno)==null?"0":consmap.get(empno).toString());
				double ota1amount = Double.parseDouble(ota1map.get(empno)==null?"0":ota1map.get(empno).toString());
				double hrramount = Double.parseDouble(hrrmap.get(empno)==null?"0":hrrmap.get(empno).toString());
				String otacd1 = getEarnCdFrmMap(staffctgrymap,scg,"OTA");
				String elecreccd = getEarnCdFrmMap(staffctgrymap,scg,"ELECREC");
				String watercd = getEarnCdFrmMap(staffctgrymap,scg,"WATER");
				String conscd = getEarnCdFrmMap(staffctgrymap,scg,"CONS");
				String ota1cd = getEarnCdFrmMap(staffctgrymap,scg,"OTA1");
				String hrrcd = getEarnCdFrmMap(staffctgrymap,scg,"HRR");
				if(otaamount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,otacd1);
					tpdpstmt6.setString(4,"OTA");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
				}
				if(elecamount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,elecreccd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
				}
				if(wateramount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,watercd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					// log.info(empno+"------7----ELEC");
				}
				if(consamount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,conscd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					// log.info(empno+"------7----ELEC");
				}
				if(ota1amount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,ota1cd);
					tpdpstmt6.setString(4,"E");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					// log.info(empno+"------7----ELEC");
				}
				/*if(hrramount == 0 ){
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,hrrcd);
					tpdpstmt6.setString(4,"D");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,0);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					// log.info(empno+"------7----ELEC");
				}*/
				/*earntot1 = earntot1+otaamount+ota1amount;
				dedtot1 = dedtot1+elecamount+wateramount+consamount+hrramount;
				*/
				/*String otaqry = "select empno,ota_amount from  emplota_mt emt ,emplota_dt edt where emt.EMPLOTA_MT_CD  = edt.EMPLOTA_MT_CD  and payrollmonthid = "+ppinfo.getPayrollmonthid()+" and ota_amount >0 and empno = '"+empno+"'";
				log.info("otaqry "+otaqry);
				ResultSet otars = db.getRecordSet(otaqry);
				
				if(otars.next()){
					String otacd1 = getEarnCdFrmMap(staffctgrymap,scg,"OTA");;
					double otaamt = otars.getDouble("ota_amount");
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,otacd1);
					tpdpstmt6.setString(4,"OTA");
					tpdpstmt6.setString(5,"");
					tpdpstmt6.setDouble(6,otaamt);
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.addBatch();
					earnMap = addtoMap(earnMap,otacd1,otaamt,disciplinecd,empcc,"E");
					earntot1 = earntot1+otaamt;
				}*/
				
				/*
				 * MISC-rec/MISC-adv
				 */
				
				/*String miscpayqry =" select miscamt.*,(select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME = miscamt.type )earndedcd from (select EMPNO,MISCRECAMT,TYPE,DESCRIPTION from EMPMISCREC " +
						" where PAYROLLMONTHID ="+ppinfo.getPayrollmonthid()+"  and empno = '"+empno+"') miscamt " ;
				log.info("miscpayqry "+miscpayqry);
				ResultSet miscrs = db.getRecordSet(miscpayqry);
				boolean recflag = false,advflag = false;
				while(miscrs.next()){
					String earndedcd = miscrs.getString("earndedcd");
					String amt = miscrs.getString("MISCRECAMT");
					String misctype = miscrs.getString("type");
					//String insertmiscqry= "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+earndedcd+"','"+misctype+"',(select ledgercd from EARNDEDUMASTER where  EARNDEDUCD='"+earndedcd+"'),'"+amt+"')";
					String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+earndedcd+"','"+misctype+"',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD='"+earndedcd+"' and cadrecod="+empcc+"),'"+amt+"')";
					log.info("insertmiscqry "+insertmiscqry);
					//stmt.addBatch(insertmiscqry);
					miscpstmt.setInt(1,paytransid);
					miscpstmt.setString(2,empno);
					miscpstmt.setString(3,earndedcd);
					miscpstmt.setString(4,misctype);
					miscpstmt.setString(5,earndedcd);
					miscpstmt.setString(6,empcc);
					miscpstmt.setString(7,amt);
					miscpstmt.addBatch();
					if(misctype.equals("MISCREC")){
						recflag = true;
						dedtot1 = dedtot1+new Double(amt).doubleValue();
						earnMap = addtoMap(earnMap,earndedcd,new Double(amt).doubleValue(),disciplinecd,empcc,"MISCREC");
					}
					if(misctype.equals("MISCADV")){
						advflag = true;
						earntot1 = earntot1+new Double(amt).doubleValue();
						earnMap = addtoMap(earnMap,earndedcd,new Double(amt).doubleValue(),disciplinecd,empcc,"MISCADV");
					}
				}
				if(!recflag){
					//String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from configmapping where CONFIGNAME='MISCREC' ),'MISCREC',(select ledgercd from EARNDEDUMASTER where  EARNDEDUCD=(select earndeducd from configmapping where CONFIGNAME='MISCREC' )),'0')";
					String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from configmapping where CONFIGNAME='MISCREC' ),'MISCREC',(select ledgercd from SALHEAD_LEDG_MAPPING where  EARNDEDUCD=(select earndeducd from configmapping where CONFIGNAME='MISCREC' )and cadrecod ="+empcc+"),'0')";
					log.info("insertmiscqry "+insertmiscqry);
					//stmt.addBatch(insertmiscqry);
					nomisrecpstmt.setInt(1,paytransid);
					nomisrecpstmt.setString(2,empno);
					nomisrecpstmt.setString(3,"MISCREC");
					nomisrecpstmt.setString(4,"MISCREC");
					nomisrecpstmt.setString(5,"MISCREC");
					nomisrecpstmt.setString(6,empcc);
					nomisrecpstmt.addBatch();
				}
				if(!advflag){	
					//String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from configmapping where CONFIGNAME='MISCADV' ),'MISCADV',(select ledgercd from EARNDEDUMASTER where  EARNDEDUCD=(select earndeducd from configmapping where CONFIGNAME='MISCADV' )),'0')";
					String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from configmapping where CONFIGNAME='MISCADV' ),'MISCADV',(select ledgercd from SALHEAD_LEDG_MAPPING where   EARNDEDUCD=(select earndeducd from configmapping where CONFIGNAME='MISCADV' )and cadrecod ="+empcc+"),'0')";
					//stmt.addBatch(insertmiscqry);
					nomisrecpstmt.setInt(1,paytransid);
					nomisrecpstmt.setString(2,empno);
					nomisrecpstmt.setString(3,"MISCADV");
					nomisrecpstmt.setString(4,"MISCADV");
					nomisrecpstmt.setString(5,"MISCADV");
					nomisrecpstmt.setString(6,empcc);
					nomisrecpstmt.addBatch();
					log.info("insertmiscqry "+insertmiscqry);
				}
				db.closeRs();	*/
				/*
				 * MISC Perks
				 */
				//String miscperkyqry =" Select mp.empno,ot,nwcc,med,ta,ltc,misc,(select earndeducd from earndedumaster e where e.shortname='OT' and staffctgrycd="+scg+")otedcd,(select earndeducd from earndedumaster e where e.shortname='NWCC' and staffctgrycd="+scg+")nwccedcd,(select earndeducd from earndedumaster e where e.shortname='MED' and staffctgrycd="+scg+")mededcd,(select earndeducd from earndedumaster e where e.shortname='TA' and staffctgrycd="+scg+")taedcd,(select earndeducd from earndedumaster e where e.shortname='LTC' and staffctgrycd="+scg+")ltcedcd,(select earndeducd from earndedumaster e where e.shortname='MISC' and staffctgrycd="+scg+")miscedcd from miscperks mp where miscperkcd is not null  " +
				//" and PAYROLLMONTHID ="+ppinfo.getPayrollmonthid()+"  and empno = '"+empno+"' " ;
				/*
				String miscperkqry = " select nvl(a.ot,0)ot,nvl(a.nwcc,0)nwcc,nvl(a.med,0)med,nvl(a.ta,0)ta,nvl(a.ltc,0)ltc,nvl(a.misc,0)misc,b.otedcd,b.nwccedcd,b.mededcd,b.taedcd,b.ltcedcd,b.miscedcd from (Select mp.empno,ot,nwcc,med,ta,ltc,misc from miscperks mp where  empno='"+empno+"' and payrollmonthid="+ppinfo.getPayrollmonthid()+") a,( select '"+empno+"' empno, (select earndeducd from earndedumaster e where e.shortname='OT' and staffctgrycd="+scg+")otedcd,(select earndeducd from earndedumaster e where e.shortname='NWCC' and staffctgrycd="+scg+")nwccedcd,(select earndeducd from earndedumaster e where e.shortname='MED' and staffctgrycd="+scg+")mededcd,(select earndeducd from earndedumaster e where e.shortname='TA' and staffctgrycd="+scg+")taedcd,(select earndeducd from earndedumaster e where e.shortname='LTC' and staffctgrycd="+scg+")ltcedcd,(select earndeducd from earndedumaster e where e.shortname='MISC' and staffctgrycd="+scg+")miscedcd  from dual)b where a.empno(+)=b.empno ";
				log.info("miscpayqry "+miscperkqry);
				ResultSet miscprs = db.getRecordSet(miscperkqry);
				boolean pkflag = false;
				while(miscprs.next()){
					pkflag = true;
					String insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("otedcd")+"','MISCPK','','"+miscprs.getString("ot")+"')";  
					log.info("insertmiscqry 1"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("otedcd"),miscprs.getDouble("ot"),disciplinecd,scg,"MISCPK");
					insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("nwccedcd")+"','MISCPK','','"+miscprs.getString("nwcc")+"')";  
					log.info("insertmiscqry 2"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("nwccedcd"),miscprs.getDouble("nwcc"),disciplinecd,scg,"MISCPK");
					insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("mededcd")+"','MISCPK','','"+miscprs.getString("med")+"')";  
					log.info("insertmiscqry 3"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("mededcd"),miscprs.getDouble("med"),disciplinecd,scg,"MISCPK");
					insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("taedcd")+"','MISCPK','','"+miscprs.getString("ta")+"')";  
					log.info("insertmiscqry 4"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("taedcd"),miscprs.getDouble("ta"),disciplinecd,scg,"MISCPK");
					insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("ltcedcd")+"','MISCPK','','"+miscprs.getString("ltc")+"')";  
					log.info("insertmiscqry 5"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("ltcedcd"),miscprs.getDouble("ltc"),disciplinecd,scg,"MISCPK");
					insertmiscqry= "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+miscprs.getString("miscedcd")+"','MISCPK','','"+miscprs.getString("misc")+"')";  
					log.info("insertmiscqry 6"+insertmiscqry);
					stmt.addBatch(insertmiscqry);
					earnMap = addtoMap(earnMap,miscprs.getString("miscedcd"),miscprs.getDouble("misc"),disciplinecd,scg,"MISCPK");
										
					earntot1 = earntot1+miscprs.getDouble("ot")+miscprs.getDouble("nwcc")+miscprs.getDouble("med")+miscprs.getDouble("ta")+miscprs.getDouble("ltc")+miscprs.getDouble("misc");
					//dedtot = dedtot+new Double(amt).doubleValue();
					
				}
								
				db.closeRs();
				*/
				/*
				 * getting unfinished advance details of an employee
				 */
				//String advquery = "select EMPLOYEEADVCD,ea.ADVCD,ADVAMT,EMPLOYEEADVCD,emi,am.EARNDEDUCD,ea.RECOVEREDINSTALLMENTS,ea.AMOUNTRECOVERED,ledgercd from EMPLOYEEADVANCES ea,ADVANCESMASTER am,EARNDEDUMASTER edm where ADVFINISHEDDT is null and am.EARNDEDUCD = edm.EARNDEDUCD and ea.ADVCD=am.ADVCD and empno = '"+empno+"'";
				//String advquery = "select empno,EMPLOYEEADVCD,ea.ADVCD,ADVAMT,RATEOFINT,EMPLOYEEADVCD,emi,am.EARNDEDUCD,NOOFINSTALLMENTS,ea.RECOVEREDINSTALLMENTS,ea.AMOUNTRECOVERED,ledgercd,INTRESTAMOUNT,INTRESTINSTALLMENTS,RECOVEREDINTREST,RECOVEREDINTRESTINSTALLMENTS,INTERESTPAID,PAIDINTRESTINSTALLMENTS,interestemi,(select EARNDEDUCD from EARNDEDUMASTER where ledgercd='"+prop1.getProperty("INTONADV")+"' and STAFFCTGRYCD='"+scg+"') intearndedcd from EMPLOYEEADVANCES ea,ADVANCESMASTER am,EARNDEDUMASTER edm where ADVFINISHEDDT is null and am.EARNDEDUCD = edm.EARNDEDUCD and ea.ADVCD(+)=am.ADVCD ";// and empno = '"+empno+"'";//  and ADVANCEDATE between (select PAYROLLFROMDT from monthlypayroll where PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"') and (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"')";
				/*String advquery = "select am.ADVCD,am.advname ,am.EARNDEDUCD,(select ledgercd from earndedumaster e where" +
				" e.earndeducd=am.earndeducd)ledgercd, nvl(adv.ADVAMT, 0)ADVAMT, adv.*,(select EARNDEDUCD from " +
				"EARNDEDUMASTER where ledgercd='"+prop1.getProperty("INTONADV")+"' ) intearndedcd ,  " +
				" (SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ) cpfearndedcd from"+
				"( select empa.ADVCD,empa.ADVAMT,NVL(empa.FIRSTEMI,0)FIRSTEMI, empa.STARTFROMMONTH,mp.PAYROLLFROMDT,empa.EMPLOYEEADVCD,empa.RATEOFINT,empa.EMI," +
				"nvl(empa.NOOFINSTALLMENTS,0)NOOFINSTALLMENTS,nvl(empa.RECOVEREDINSTALLMENTS,0)RECOVEREDINSTALLMENTS,nvl(empa.AMOUNTRECOVERED,0)AMOUNTRECOVERED," +
				"empa.INTRESTAMOUNT,NVL(empa.FIRSTINTERESTEMI,0)FIRSTINTERESTEMI,nvl( empa.INTRESTINSTALLMENTS,0) INTRESTINSTALLMENTS,nvl(empa.RECOVEREDINTREST,0)RECOVEREDINTREST," +
				"nvl(empa.RECOVEREDINTRESTINSTALLMENTS,0)RECOVEREDINTRESTINSTALLMENTS,nvl(empa.INTERESTPAID,0)INTERESTPAID," +
				"nvl(empa.PAIDINTRESTINSTALLMENTS,0)PAIDINTRESTINSTALLMENTS,nvl(empa.interestemi,0)interestemi,principalstatus,intereststatus from EMPLOYEEADVANCES empa," +
				"monthlypayroll mp where empa.STARTFROMMONTH=mp.PAYROLLMONTHID and empa.EMPNO='"+empno+"' and mp.PAYROLLFROMDT < " +
				"(select payrolltodt from monthlypayroll where payrollmonthid='"+ppinfo.getPayrollmonthid()+"')) adv,ADVANCESMASTER am where am.ADVCD= adv.ADVCD(+)";*/
				String advquery = "select am.ADVCD,am.INTEARNDEDUCD intrestcd,am.INTEARNDEDUCD,am.advname ,am.EARNDEDUCD,(select ledgercd from SALHEAD_LEDG_MAPPING	 e where" +
				" e.earndeducd=am.earndeducd and cadrecod="+empcc+")ledgercd, nvl(adv.ADVAMT, 0)ADVAMT, adv.*,(select EARNDEDUCD from " +
				"SALHEAD_LEDG_MAPPING where ledgercd='"+prop1.getProperty("INTONADV")+"' and cadrecod="+empcc+" and earndeducd=am.earndeducd) intearndedcd ,  " +
				" (SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ) cpfearndedcd from"+
				"( select empa.ADVCD,empa.ADVAMT,NVL(empa.FIRSTEMI,0)FIRSTEMI, empa.STARTFROMMONTH,mp.PAYROLLFROMDT,empa.EMPLOYEEADVCD,empa.RATEOFINT,empa.EMI," +
				"nvl(empa.NOOFINSTALLMENTS,0)NOOFINSTALLMENTS,nvl(empa.RECOVEREDINSTALLMENTS,0)RECOVEREDINSTALLMENTS,nvl(empa.AMOUNTRECOVERED,0)AMOUNTRECOVERED," +
				"empa.INTRESTAMOUNT,NVL(empa.FIRSTINTERESTEMI,0)FIRSTINTERESTEMI,nvl( empa.INTRESTINSTALLMENTS,0) INTRESTINSTALLMENTS,nvl(empa.RECOVEREDINTREST,0)RECOVEREDINTREST," +
				"nvl(empa.RECOVEREDINTRESTINSTALLMENTS,0)RECOVEREDINTRESTINSTALLMENTS,nvl(empa.INTERESTPAID,0)INTERESTPAID," +
				"nvl(empa.PAIDINTRESTINSTALLMENTS,0)PAIDINTRESTINSTALLMENTS,nvl(empa.interestemi,0)interestemi,principalstatus,intereststatus from EMPLOYEEADVANCES empa," +
				"monthlypayroll mp where empa.STARTFROMMONTH=mp.PAYROLLMONTHID and empa.EMPNO='"+empno+"' and empa.ADVCLOSEDSTATUS='N' and mp.PAYROLLFROMDT < " +
				"(select payrolltodt from monthlypayroll where payrollmonthid='"+ppinfo.getPayrollmonthid()+"') and (PRINCIPALSTATUS='Y' or INTERESTSTATUS='Y') " +
				"and empa.AdvClosedStatus = 'N' and ((empa.amountrecovered<empa.advamt and empa.principalstatus='Y') or  " +
				"(empa.recoveredintrest<empa.intrestamount and empa.intereststatus='Y')) ) adv," +
				"ADVANCESMASTER am where am.status='A' and am.ADVCD= adv.ADVCD(+)";
				//log.info(" advquery ---------  "+advquery);
				ResultSet adrs = db.getRecordSet(advquery);
				Date date = new Date();
				String DATE_FORMAT = "dd/MMM/yyyy";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				String currentdt =sdf.format(date); 
				boolean hasIntOnAdv = false;
				boolean hasCPFInt = false;
				boolean hasAdv = false;
				List advintlist = new ArrayList();
				List empadvintlist =  new ArrayList();
				while(adrs.next()){
					hasAdv = false;
					//if(adrs.getDouble("ADVAMT")>0) {
					int emi = 0,iemi = 0;
					int monthlypay = 0;
					int ri = 0,ramt=0,iri=0,iramt=0,noofi=0,arc=0,iarc=0,advamt=0,intrstadvamt=0;
					String EMPLOYEEADVCD="",ADVCD="",earndedcd="",intrestcd="";;
					double roi =0;
					String pstatus = "",istatus= "";
					intrestcd = adrs.getString("intrestcd");
					if(intrestcd != null){
						if(!advintlist.contains(intrestcd)){
							//log.info("**adding int cd "+intrestcd);
							advintlist.add(intrestcd);
						}
					}
					//log.info("---principalstatus---"+adrs.getString("principalstatus")+"---intereststatus--"+adrs.getString("intereststatus"));
					if(adrs.getString("STARTFROMMONTH")!=null){
						pstatus = adrs.getString("principalstatus");
						istatus = adrs.getString("intereststatus");
						if(adrs.getDouble("RECOVEREDINSTALLMENTS")==0)
							emi = Integer.parseInt(adrs.getString("FIRSTEMI")); // EMI for Advance Principle amount
						else 
							emi = Integer.parseInt(adrs.getString("emi")); // EMI for Advance Principle amount
						if(adrs.getDouble("RECOVEREDINTRESTINSTALLMENTS")==0)
							iemi = Integer.parseInt(adrs.getString("FIRSTINTERESTEMI"));// EMI for Advance Interest amount
						else
							iemi = Integer.parseInt(adrs.getString("interestemi"));// EMI for Advance Interest amount
						
						ri = Integer.parseInt(adrs.getString("RECOVEREDINSTALLMENTS"));// Advance Principle Recovered Installments
						ramt = Integer.parseInt(adrs.getString("AMOUNTRECOVERED"));// Advance Principle Recovered Amount
						iri = Integer.parseInt(adrs.getString("RECOVEREDINTRESTINSTALLMENTS"));// Advance Interest Recovered Installments
						iramt = Integer.parseInt(adrs.getString("RECOVEREDINTREST"));// Advance Interest Recovered Amount
						noofi = Integer.parseInt(adrs.getString("NOOFINSTALLMENTS"));// Total Advance Principle Installlments
						arc = Math.round(ramt +emi);
						iarc = Math.round(iramt +iemi);
						advamt = Integer.parseInt(adrs.getString("ADVAMT"));// Total Advance Amount
						intrstadvamt = adrs.getInt("INTRESTAMOUNT"); // Total Advance Interest Amount
						EMPLOYEEADVCD = adrs.getString("EMPLOYEEADVCD");
						ADVCD = adrs.getString("ADVCD");
						earndedcd = adrs.getString("EARNDEDUCD");
						
						roi = adrs.getDouble("RATEOFINT");
					}
					// If Recovered Principle Installments is less than Total no.of Advance Principle Installments then deduction of principle amount 
					if("Y".equals(pstatus)) {
						hasAdv = true;
						if(ri< noofi ||ramt<advamt){
							// IF Total previous recovered amount+ emi > Total advance amount then deduct only remaining amount  
							if(arc  > advamt){
								//monthlypay = arc - advamt;
								monthlypay = advamt - arc+emi;
							}
							// IF Toral previous recovered amount+ emi <= Total advance amount then deduct emi.
							if(arc  <= advamt){
								monthlypay = emi;
							}				
							//monthlypay = emi;
							arc = ramt+monthlypay;
							//log.info(" monthlypay "+monthlypay);
							
							
							earnMap = addtoMap(earnMap,earndedcd,monthlypay,disciplinecd,empcc,"D",0,0,0);
							dedtot1 = dedtot1+monthlypay;
							
							// Inserting advance info to payrolldet table
							String qry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("EARNDEDUCD")+"','ADV','"+adrs.getString("ledgercd")+"','"+monthlypay+"')";
							//log.info("qry "+qry);							
							//stmt.addBatch(qry);
							tpdpstmt6.setInt(1,paytransid);
							tpdpstmt6.setString(2,empno);
							tpdpstmt6.setString(3,adrs.getString("EARNDEDUCD"));
							tpdpstmt6.setString(4,"ADV");
							tpdpstmt6.setString(5,adrs.getString("ledgercd"));
							tpdpstmt6.setInt(6,monthlypay);
							tpdpstmt6.setDouble(7,0);
							tpdpstmt6.setDouble(8,0);
							tpdpstmt6.setDouble(9,0);
							tpdpstmt6.addBatch();
							
							//Updateing EMPLOYEEADVANCES with recoverdamt and recovered installment
							String updqry = " update EMPLOYEEADVANCES set AMOUNTRECOVERED='"+arc+"' ,RECOVEREDINSTALLMENTS ='"+(ri+1)+"' where EMPLOYEEADVCD = '"+adrs.getString("EMPLOYEEADVCD")+"'";
							//PreparedStatement empadvpstmt = con.prepareStatement("update EMPLOYEEADVANCES set AMOUNTRECOVERED=? ,RECOVEREDINSTALLMENTS =? where EMPLOYEEADVCD = ? ");
							empadvpstmt.setInt(1,arc);
							empadvpstmt.setInt(2,(ri+1));
							empadvpstmt.setString(3,adrs.getString("EMPLOYEEADVCD"));
							empadvpstmt.addBatch();
							
							if(advamt == arc){
								updqry = " update EMPLOYEEADVANCES set AMOUNTRECOVERED='"+arc+"' ,RECOVEREDINSTALLMENTS ='"+(ri+1)+"', PRNCPLFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"') where EMPLOYEEADVCD = '"+adrs.getString("EMPLOYEEADVCD")+"'";
								//PreparedStatement advfinpstmt = con.prepareStatement(" update TRAILEMPLOYEEADVANCES set AMOUNTRECOVERED=? ,RECOVEREDINSTALLMENTS =?, PRNCPLFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID=?),principalstatus='N' where EMPLOYEEADVCD = ?");
								advfinpstmt.setInt(1,arc);
								advfinpstmt.setInt(2,(ri+1));
								advfinpstmt.setString(3,ppinfo.getPayrollmonthid());
								advfinpstmt.setString(4,adrs.getString("EMPLOYEEADVCD"));
								advfinpstmt.addBatch();
								
							}
							//log.info("updating employee advances updqry "+updqry);
							//stmt.addBatch(updqry);
							// Inserting detail of advance revory to EMPLOYEEADVANCEREC table
							String insertadvqry = "insert into EMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt) values(EMPLOYEEADVANCEREC_seq.nextval,'"+EMPLOYEEADVCD+"','"+ADVCD+"','"+empno+"','"+ppinfo.getPayrollmonthid()+"','"+monthlypay+"','P','"+(ri+1)+"','"+(adrs.getDouble("amountrecovered")+monthlypay)+"')";
							//log.info("insertadvqry "+insertadvqry);
							//stmt.addBatch(insertadvqry);
							empadvrecpstmt.setString(1,EMPLOYEEADVCD);
							empadvrecpstmt.setString(2,ADVCD);
							empadvrecpstmt.setString(3,empno);
							empadvrecpstmt.setString(4,ppinfo.getPayrollmonthid());
							empadvrecpstmt.setInt(5,monthlypay);
							empadvrecpstmt.setString(6,"P");
							empadvrecpstmt.setInt(7,(ri+1));
							empadvrecpstmt.setDouble(8,(adrs.getDouble("amountrecovered")+monthlypay));
							empadvrecpstmt.setString(9,adrs.getString("EARNDEDUCD"));
							empadvrecpstmt.addBatch();
						}
//						 block remove on 09/June/2012 due to change in advance 
						/*else {
							String updqry = "update EMPLOYEEADVANCES set principalstatus='N'  where EMPLOYEEADVCD = '"+adrs.getString("EMPLOYEEADVCD")+"' ";
							//stmt.addBatch(updqry);
							//PreparedStatement updadvpstmt = con.prepareStatement("update EMPLOYEEADVANCES set principalstatus='N'  where EMPLOYEEADVCD = ? ");
							updadvpstmt.setString(1,adrs.getString("EMPLOYEEADVCD"));
							updadvpstmt.addBatch();
							//log.info("updqry "+updqry);
							pstatus = "N";
						}*/
//						 end block remove on 09/June/2012 due to change in advance 
						// For November payroll process
						/*dedtot1 = dedtot1+adrs.getDouble("emi");
						
						String qry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("EARNDEDUCD")+"','ADV','"+adrs.getString("ledgercd")+"','"+adrs.getString("emi")+"')";
						log.info("qry "+qry);
						stmt.addBatch(qry);
						earnMap = addtoMap(earnMap,earndedcd,new Double(adrs.getString("emi")).doubleValue(),disciplinecd,scg,"D");
						String insertadvqry = "insert into EMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt) values(EMPLOYEEADVANCEREC_seq.nextval,'"+EMPLOYEEADVCD+"','"+ADVCD+"','"+empno+"','"+ppinfo.getPayrollmonthid()+"','"+adrs.getString("emi")+"','P','"+(ri)+"','"+adrs.getString("amountrecovered")+"')";
						log.info("insertadvqry "+insertadvqry);
						stmt.addBatch(insertadvqry);*/
						
					}
					if("Y".equalsIgnoreCase(istatus) && "N".equalsIgnoreCase((pstatus))){
						// IF Total previous recovered amount+ emi > Total advance amount then deduct only remaining amount  
						
						if(iarc  > intrstadvamt){
							monthlypay = intrstadvamt - iarc + iemi ;
						}
						// IF Toral previous recovered amount+ emi <= Total advance amount then deduct emi.
						if(iarc  <= intrstadvamt){
							monthlypay = iemi;
						}				
						//monthlypay = iemi;
						iarc = iramt+monthlypay;
						//log.info(" monthlypay "+monthlypay);
						
						
						dedtot1 = dedtot1+monthlypay;
						
						if(ADVCD.equals("7")){
							hasCPFInt = true;
							//CPF ADVANCE
							//String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("cpfearndedcd")+"','ADVINT',(select ledgercd from EARNDEDUMASTER where EARNDEDUCD = "+adrs.getString("cpfearndedcd")+"),'"+monthlypay+"')";
							String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("cpfearndedcd")+"','ADVINT',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD = "+adrs.getString("cpfearndedcd")+" and cadrecod="+empcc+"),'"+monthlypay+"')";
							//log.info("CPFINTrestqry "+qry);
							//stmt.addBatch(qry);
							
							cpfadvpstmt.setInt(1,paytransid);
							cpfadvpstmt.setString(2,empno);
							String cpfintrestearncd = adrs.getString("cpfearndedcd");
							cpfadvpstmt.setString(3,cpfintrestearncd);
							cpfadvpstmt.setString(4,cpfintrestearncd);
							cpfadvpstmt.setString(5,empcc);
							cpfadvpstmt.setInt(6,monthlypay);
							cpfadvpstmt.setDouble(7,0);
							
							cpfadvpstmt.addBatch();		
							empadvintlist.add(cpfintrestearncd);
							earnMap = addtoMap(earnMap,cpfintrestearncd,monthlypay,disciplinecd,empcc,"ADVINT",0,0,0);
							intrestcd = cpfintrestearncd;
						}else{
							hasIntOnAdv = true;
							String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("intearndedcd")+"','ADVINT','"+prop1.getProperty("INTONADV")+"','"+monthlypay+"')";
							//log.info("qry "+qry);
							//stmt.addBatch(qry);
							if(intrestcd != null){
								tpdpstmt6.setInt(1,paytransid);
								tpdpstmt6.setString(2,empno);
								tpdpstmt6.setString(3,intrestcd);
								empadvintlist.add(intrestcd);
								tpdpstmt6.setString(4,"ADVINT");
								tpdpstmt6.setString(5,prop1.getProperty("INTONADV"));
								tpdpstmt6.setInt(6,monthlypay);
								tpdpstmt6.setDouble(7,0);
								tpdpstmt6.setDouble(8,0);
								tpdpstmt6.setDouble(9,0);
								tpdpstmt6.addBatch();
								earnMap = addtoMap(earnMap,intrestcd,monthlypay,disciplinecd,empcc,"ADVINT",0,0,0);
							}
						}
						
						//	Inserting advance info to payrolldet table
						/*String qry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("intearndedcd")+"','ADV','"+prop1.getProperty("INTONADV")+"','"+monthlypay+"')";
						log.info("qry "+qry);
						stmt.addBatch(qry);*/
						//Updateing EMPLOYEEADVANCES with recoverdamt and recovered installment
						
//						Updateing TRAILEMPLOYEEADVANCES with recoverdamt and recovered installment
						String updqry = "update EMPLOYEEADVANCES set RECOVEREDINTREST='"+iarc+"' ,RECOVEREDINTRESTINSTALLMENTS ='"+(iri+1)+"' where EMPLOYEEADVCD = '"+adrs.getString("EMPLOYEEADVCD")+"' ";
						//PreparedStatement intupdadvpstmt = con.prepareStatement("update TRAILEMPLOYEEADVANCES set RECOVEREDINTREST=? ,RECOVEREDINTRESTINSTALLMENTS =? where EMPLOYEEADVCD = ? ");
						intupdadvpstmt.setInt(1,iarc);
						intupdadvpstmt.setInt(2,(iri+1));
						intupdadvpstmt.setString(3,adrs.getString("EMPLOYEEADVCD"));
						intupdadvpstmt.addBatch();
						
						if(intrstadvamt == iarc){
							updqry = " update EMPLOYEEADVANCES set RECOVEREDINTREST='"+iarc+"' ,RECOVEREDINTRESTINSTALLMENTS ='"+(iri+1)+"', ADVFINISHEDDT = '"+currentdt+"', INTRSTFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"'),intereststatus='N' where EMPLOYEEADVCD = '"+adrs.getString("EMPLOYEEADVCD")+"'";
							//PreparedStatement intcompadvpstmt = con.prepareStatement(" update TRAILEMPLOYEEADVANCES set RECOVEREDINTREST=? ,RECOVEREDINTRESTINSTALLMENTS =?, ADVFINISHEDDT = ?, INTRSTFINISHEDDT = (select PAYROLLTODT from monthlypayroll where PAYROLLMONTHID=?),intereststatus='N' where EMPLOYEEADVCD = ? ");
							intcompadvpstmt.setInt(1,iarc);
							intcompadvpstmt.setInt(2,(iri+1));
							intcompadvpstmt.setString(3,currentdt);
							intcompadvpstmt.setString(4,ppinfo.getPayrollmonthid());
							intcompadvpstmt.setString(5,adrs.getString("EMPLOYEEADVCD"));
							intcompadvpstmt.addBatch();
							//log.info("updating employee advances interest updqry "+updqry+" when intrstadvamt == iarc ");
						}
						//log.info("updating employee advances interest updqry "+updqry);
						// Inserting detail of advance revory to EMPLOYEEADVANCEREC table
						String insertadvqry = "insert into EMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt) values(EMPLOYEEADVANCEREC_seq.nextval,'"+EMPLOYEEADVCD+"','"+ADVCD+"','"+empno+"','"+ppinfo.getPayrollmonthid()+"','"+monthlypay+"','I','"+(iri+1)+"','"+(adrs.getDouble("RECOVEREDINTREST")+monthlypay)+"')";
						//log.info("insertadv interest qry "+insertadvqry);
						//PreparedStatement empadvrecpstmt = con.prepareStatement("insert into TRAILEMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt) values(TRAILEMPLOYEEADVANCEREC_SEQ.nextval,?,?,?,?,?,?,?,?)");
						empadvrecpstmt.setString(1,EMPLOYEEADVCD);
						empadvrecpstmt.setString(2,ADVCD);
						empadvrecpstmt.setString(3,empno);
						empadvrecpstmt.setString(4,ppinfo.getPayrollmonthid());
						empadvrecpstmt.setInt(5,monthlypay);
						empadvrecpstmt.setString(6,"I");
						empadvrecpstmt.setInt(7,(iri+1));
						empadvrecpstmt.setDouble(8,(adrs.getDouble("RECOVEREDINTREST")+monthlypay));
						empadvrecpstmt.setString(9,intrestcd);
						empadvrecpstmt.addBatch();
						//stmt.addBatch(insertadvqry);
						
						//For November payroll process
						/*dedtot1 = dedtot1+adrs.getDouble("interestemi");
						if(ADVCD.equals("7")){
							hasCPFInt = true;
							//CPF ADVANCE
							String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("cpfearndedcd")+"','ADVINT',(select ledgercd from EARNDEDUMASTER where EARNDEDUCD = "+adrs.getString("cpfearndedcd")+"),'"+adrs.getString("interestemi")+"')";
							log.info("CPFINTrestqry "+qry);
							stmt.addBatch(qry);
							earnMap = addtoMap(earnMap,adrs.getString("cpfearndedcd"),new Double(adrs.getString("interestemi")).doubleValue(),disciplinecd,scg,"D");
						}
						else
						{
							hasIntOnAdv = true;
							String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("intearndedcd")+"','ADVINT','"+prop1.getProperty("INTONADV")+"','"+adrs.getString("interestemi")+"')";
							stmt.addBatch(qry);
							earnMap = addtoMap(earnMap,adrs.getString("intearndedcd"),new Double(adrs.getString("interestemi")).doubleValue(),disciplinecd,scg,"D");
						}
						
						String insertadvqry = "insert into EMPLOYEEADVANCEREC (EMPADVRECCD,EMPLOYEEADVCD,ADVCD,EMPNO,PAYROLLMONTHID,AMOUNT,PAYMENTTYPE,INSTALLMENTNO,recoveredamt) values(EMPLOYEEADVANCEREC_seq.nextval,'"+EMPLOYEEADVCD+"','"+ADVCD+"','"+empno+"','"+ppinfo.getPayrollmonthid()+"','"+adrs.getString("interestemi")+"','I','"+(iri)+"','"+adrs.getString("RECOVEREDINTREST")+"')";
						log.info("insertadv interest qry "+insertadvqry);
						stmt.addBatch(insertadvqry);*/
						
					}
					if(!hasAdv){
						String qry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("EARNDEDUCD")+"','ADV','"+adrs.getString("ledgercd")+"','0')";
						//log.info("qry if employee not having advances --  "+qry);
						//stmt.addBatch(qry);
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,empno);
						tpdpstmt6.setString(3,adrs.getString("EARNDEDUCD"));
						tpdpstmt6.setString(4,"ADV");
						tpdpstmt6.setString(5,adrs.getString("ledgercd"));
						tpdpstmt6.setInt(6,0);
						tpdpstmt6.addBatch();
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.setDouble(8,0);
						tpdpstmt6.setDouble(9,0);
						earnMap = addtoMap(earnMap,adrs.getString("EARNDEDUCD"),0,disciplinecd,empcc,"D",0,0,0);
					}
					
					/*
					 * if recovered installments is zero then advance has been taken this month. so add 3.5% of amt  to perks
					 */
					/*if(ri == 0 && ("Y".equals(pstatus)) && ("N".equals(istatus))){
						
						 * By default sbipercenage is 10. If for any advance rate of intrest is less than sbipercenage
						 * then we have calculate perk for that advance to that employee for that month 
						 
						//if(roi > 0) { 
						double diffint = 0.0;
						log.info("roi "+roi+" sbipercentage "+sbipercentage);
						if(roi  < sbipercentage){
							diffint = sbipercentage-roi;
							String earndedcd1 = adrs.getString("EARNDEDUCD");
							double perk = (advamt *diffint)/100;
							String perkqry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+earndedcd1+"','PRK','"+adrs.getString("ledgercd")+"','"+perk+"')";
							log.info("perkqry "+perkqry);
							stmt.addBatch(perkqry);									
							earnMap = addtoMap(earnMap,earndedcd1,perk,disciplinecd,scg,"PRK");
							earntot1 = earntot1+perk;
							//dedtot = dedtot+monthlypay;									
						}
						//}
					}*/						
					/*}else {
					 // Inserting all other advances info as dummy to payrolldet table
					  String qry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+adrs.getString("EARNDEDUCD")+"','ADV','"+adrs.getString("ledgercd")+"','0')";
					  log.info("qry if employee not having advances --  "+qry);
					  stmt.addBatch(qry);
					  }*/
				}
				for(int av=0;av<advintlist.size();av++){
					String cd = advintlist.get(av).toString();
					//log.info("checking for int cd "+cd);
					if(!empadvintlist.contains(cd)){
						//log.info("has cd "+cd);
						tpdpstmt6.setInt(1,paytransid);
						tpdpstmt6.setString(2,empno);
						tpdpstmt6.setString(3,cd);
						tpdpstmt6.setString(4,"ADVINT");
						tpdpstmt6.setString(5,"");
						tpdpstmt6.setInt(6,0);
						tpdpstmt6.setDouble(7,0);
						tpdpstmt6.setDouble(8,0);
						tpdpstmt6.setDouble(9,0);
						tpdpstmt6.addBatch();
						earnMap = addtoMap(earnMap,cd,0,disciplinecd,empcc,"ADVINT",0,0,0);
					}
					
				}
				/*if(!hasCPFInt){
					//String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ),'ADVINT',(select ledgercd from EARNDEDUMASTER where EARNDEDUCD = (SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' )),'0')";
					String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ),'ADVINT',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD = (SELECT earndeducd FROM CONFIGMAPPING WHERE configname = 'CPFINT' ) and cadrecod="+empcc+"),'0')";
					//stmt.addBatch(qry);
					nointcpfpstmt.setInt(1,paytransid);
					nointcpfpstmt.setString(2,empno);
					nointcpfpstmt.setString(3,empcc);
					nointcpfpstmt.setDouble(4,0);
					nointcpfpstmt.addBatch();
					log.info("!hasCPFInt "+qry);
				}
				if(!hasIntOnAdv){
				//	String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select EARNDEDUCD from EARNDEDUMASTER where ledgercd='"+prop1.getProperty("INTONADV")+"' ),'Int','"+prop1.getProperty("INTONADV")+"','0')";
					String qry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select EARNDEDUCD from SALHEAD_LEDG_MAPPING where ledgercd='"+prop1.getProperty("INTONADV")+"' and cadrecod="+empcc+" ),'Int','"+prop1.getProperty("INTONADV")+"','0')";
					//stmt.addBatch(qry);
					nointadvpstmt.setInt(1,paytransid);
					nointadvpstmt.setString(2,empno);
					nointadvpstmt.setString(3,prop1.getProperty("INTONADV"));
					nointadvpstmt.setString(4,empcc);
					nointadvpstmt.setString(5,prop1.getProperty("INTONADV"));
					nointadvpstmt.setDouble(6,0);
					nointadvpstmt.addBatch();
					log.info("!!hasIntOnAdv "+qry);
				}*/
				adrs.close();
				db.closeRs();
				/**
				 * Recovery of PMNRFund 
				 *//*
				//rs = db.getRecordSet("select PMNRFUND,(select earndeducd from earndedumaster where ledgercd='"+prop1.getProperty("PMNRF")+"' )earndeducd from emppmnrfund m,emppmnrfunddet dt where m.EMPPMNRFUNDCD=dt.EMPPMNRFUNDCD and  PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"' and empno='"+empno+"' ");
				rs = db.getRecordSet("select PMNRFUND,(select earndeducd from SALHEAD_LEDG_MAPPING where ledgercd='"+prop1.getProperty("PMNRF")+"' )earndeducd from emppmnrfund m,emppmnrfunddet dt where m.EMPPMNRFUNDCD=dt.EMPPMNRFUNDCD and  PAYROLLMONTHID='"+ppinfo.getPayrollmonthid()+"' and empno='"+empno+"' ");
				if(rs.next()){
					String pmnrfQry = "insert into payrolldet(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+rs.getString("earndeducd")+"','CLCRC','"+prop1.getProperty("PMNRF")+"','"+rs.getDouble("PMNRFUND")+"')";
					log.info("pmnrfQry "+pmnrfQry);
					dedtot1 = dedtot1+rs.getDouble("PMNRFUND");
					//stmt.addBatch(pmnrfQry);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,rs.getString("earndeducd"));
					tpdpstmt6.setString(4,"CLCRC");
					tpdpstmt6.setString(5,prop1.getProperty("PMNRF"));
					tpdpstmt6.setDouble(6,rs.getDouble("PMNRFUND"));
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.addBatch();
					earnMap = addtoMap(earnMap,rs.getString("earndeducd"),rs.getDouble("PMNRFUND"),disciplinecd,empcc,"D");
				}else {
					//String pmnrfQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from earndedumaster where ledgercd='"+prop1.getProperty("PMNRF")+"' ),'CLCRC','"+prop1.getProperty("PMNRF")+"','0')";
					String pmnrfQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"',(select earndeducd from SALHEAD_LEDG_MAPPING where ledgercd='"+prop1.getProperty("PMNRF")+"' and cadrecod="+empcc+"),'CLCRC','"+prop1.getProperty("PMNRF")+"','0')";
					//stmt.addBatch(pmnrfQry);
					nopmnrpstmt.setInt(1,paytransid);
					nopmnrpstmt.setString(2,empno);
					nopmnrpstmt.setString(3,prop1.getProperty("PMNRF"));
					nopmnrpstmt.setString(4,empcc);
					nopmnrpstmt.setString(5,prop1.getProperty("PMNRF"));
					nopmnrpstmt.setDouble(6,0);
					nopmnrpstmt.addBatch();
				}
				db.closeRs();*/
				
				String cpfeardedcd = "";
				String aaicpfearndedcd = "";
				String jvcpfcd = "";
				double totLOPAmt = 0;
				String lopqry = " select  nvl(ebasic,0)ebasic,nvl(evda,0)evda,nvl(diesnonamt,0)diesnonamt,(select EARNDEDUCD from configmapping  where configname = 'EBASIC' ) ebasiccd,(select EARNDEDUCD from configmapping where configname = 'EVDA' ) evdacd,(select EARNDEDUCD from earndedumaster where type = 'DIES' ) diescd,(select EARNDEDUCD from configmapping  where configname = 'CPF' ) empcpfcd,(select EARNDEDUCD from configmapping where configname = 'AAICPF' ) aaicpfcd,(select EARNDEDUCD from configmapping  where type = 'JVCPF' ) jvcpfcd from lop where empno='"+empno+"' and MONTH='"+ppinfo.getPayrollmonthid()+"'";
				//ResultSet loprs = db.getRecordSet(lopqry);
				boolean lopflag = false;
				
				double cpflopamt = Math.round((totLOPAmt*cpfpercentage)/100);
				/*double cpfamt = Double.parseDouble(empCpfMap.get(empno).toString());*/
				String ecpdf = "0" ;
				String cpfsaladj = "0" ;
				if (empCpfMap.containsKey(empno)==true){
					
					 ecpdf = empCpfMap.get(empno).toString();
					 cpfsaladj = empCpfAdjMap.get(empno).toString();
				}
				
				if(ecpdf == null || ecpdf.equals("")){
					ecpdf = "0";
				}
				if(ecpdf == null || ecpdf.equals("")){
					cpfsaladj = "0";
				}
				
				double cpfamt = Double.parseDouble(ecpdf);
				double saladjcpf = Double.parseDouble(cpfsaladj);
				//cpfamt = cpfamt - cpflopamt;
				
				String cpfearncd = getEarnCdFrmMap(staffctgrymap,scg,"CPF");
				
				/*
				 *  caluclating adjustments for CPF LOP
				 */
				//String lopstr = maplop.get(rs.getString("EMPNO")).toString();
				Object co1 = maplop.get(empno);
				
				double cadj = 0;	
				if(co1 != null ){
					String baseamtforcpf = cpfbaseamt.get(empno).toString();
					//log.info("baseamtforcpf "+baseamtforcpf);
					cadj = Math.round((Double.parseDouble(baseamtforcpf)*cpfpercentage)/100);
				}else{
					cadj = Math.round(cpfamt);
				}
				if(saladjcpf!=0)
					cadj = Math.round(cadj+saladjcpf);
				/*
				 *  caluclating adjustments for CPF LOP
				 */
				
				
				//log.info("cpfamt is ---- "+cpfamt+" cpfearncd "+cpfearncd+" aaicpfearncd "+aaicpfearncd);
				empcpfQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+cpfearncd+"','D','"+empcpf+"','"+cpfamt+"')";
				
				//log.info("empcpfQry is --2-- "+empcpfQry);
				
				earnMap = addtoMap(earnMap,cpfearncd,cadj,disciplinecd,empcc,"D",0,0,0);	
				//stmt.addBatch(empcpfQry);
				tpdpstmt6.setInt(1,paytransid);
				tpdpstmt6.setString(2,empno);
				tpdpstmt6.setString(3,cpfearncd);
				tpdpstmt6.setString(4,"D");
				tpdpstmt6.setString(5,empcpf);
				tpdpstmt6.setDouble(6,Math.round(cadj));
				tpdpstmt6.setDouble(7,0);
				tpdpstmt6.setDouble(8,0);
				tpdpstmt6.setDouble(9,0);
				tpdpstmt6.addBatch();
				dedtot1 = dedtot1+cadj;
				//	If the employee retired from pension (pension=Y) then the employee has no AAI-CPF amount.
				String pension = "";
				pension = emppensionMap.get(empno).toString();
				if(!pension.equals("Y")){
					String aaicpfearncd = getEarnCdFrmMap(staffctgrymap,scg,"AAICPF");
					
					aaicpfQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+aaicpfearncd+"','AAI','"+aaicpf+"','"+cpfamt+"')";	
					//log.info("aaicpfQry is --1-- "+aaicpfQry);
					earnMap = addtoMap(earnMap,aaicpfearncd,cadj,disciplinecd,empcc,"AAI",0,0,0);
					//stmt.addBatch(aaicpfQry);
					
					String jvcpfqry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','"+jvcpfcd+"','JVCPF',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD='"+jvcpfcd+"' and CADRECOD = '"+empcc+"'),'"+cpfamt+"')";	
					//log.info("jvcpfqry "+aaicpfQry);
					//stmt.addBatch(jvcpfqry);
					tpdpstmt6.setInt(1,paytransid);
					tpdpstmt6.setString(2,empno);
					tpdpstmt6.setString(3,aaicpfearncd);
					tpdpstmt6.setString(4,"AAI");
					tpdpstmt6.setString(5,aaicpf);
					tpdpstmt6.setDouble(6,Math.round(cadj));
					tpdpstmt6.setDouble(7,0);
					tpdpstmt6.setDouble(8,0);
					tpdpstmt6.setDouble(9,0);
					tpdpstmt6.addBatch();
					
					/*jvcpfpstmt.setInt(1,paytransid);
					jvcpfpstmt.setString(2,empno);
					jvcpfpstmt.setString(3,jvcpfcd);
					jvcpfpstmt.setString(4,jvcpfcd);
					jvcpfpstmt.setString(5,empcc);
					jvcpfpstmt.setDouble(6,cpfamt);					
					jvcpfpstmt.addBatch();*/
					
					//earnMap = addtoMap(earnMap,jvcpfcd,cadj,disciplinecd,empcc,"JVCPF");
					
				}
				
				/*if(cpflopamt>0){
					dedtot1 = dedtot1+cpflopamt;
					String lopcpfQry = "insert into PAYROLLDET(PAYTRANSID,EMPNO,EARNDEDUCD,TYPE,LEDGERCD,AMOUNT) values ('"+paytransid+"','"+empno+"','','LOPCPF','','"+cpflopamt+"')";
					log.info("lopcpfQry is --3-- "+lopcpfQry);
					stmt.addBatch(lopcpfQry);
				}*/
				recdetcd++;
				
				String netpayqry = (String)netpaymap.get(empno);
				String amt = "";
				amt = netpayqry.substring(netpayqry.indexOf(":")+1,netpayqry.length());
				String edstr = earndedpaymap.get(empno).toString();
				
				String earnstr = edstr.substring(0,edstr.indexOf(":"));
				String dedstr = edstr.substring(edstr.indexOf(":")+1,edstr.length());
				
				double etot = Double.parseDouble(earnstr);
				double dtot = Double.parseDouble(dedstr);
				
				//log.info("earntot1--"+earntot1+"   dedtot--- "+dedtot1);
				
				netamt = Math.round(earntot1-dedtot1);
				netamt = Math.round(netamt + Double.parseDouble(amt));
				
				double netearn = Math.round(etot+earntot1);
				double netded = Math.round(dtot+dedtot1);
				
				//log.info("after advances earntot "+earntot+" dedtot "+dedtot+" netamount is "+netamt);
				
				netpayqry = netpayqry.replaceAll("NETPAY",String.valueOf(netamt));
				netpayqry = netpayqry.replaceAll("NETEARN",String.valueOf(netearn));
				netpayqry = netpayqry.replaceAll("NETDEDU",String.valueOf(netded));
				netpayqry = netpayqry.substring(0,netpayqry.indexOf(":"));
				//log.info("netpayqry "+netpayqry);
				stmt.addBatch(netpayqry);
				//fos.write((netpayqry+";\n\n").getBytes());
				
			}
			//fos.write(("END; \n\n").getBytes());
			//fos.write(("/ \n\ncommit;").getBytes());
			
			
			Iterator i = earnMap.keySet().iterator();
			/*
			 * PayProcess table Contain all the ledger code amounts after generating the salary 
			 */
			while(i.hasNext()){
				//String key = i.next().toString();
				String disckey = i.next().toString();//contains disciplinecd
				HashMap scgMap = (HashMap)earnMap.get(disckey);
				Iterator scgit = scgMap.keySet().iterator();//get staffcategory map
				//log.info("*****************************************staffcategory map size "+scgMap.size()+" disckey "+disckey);
				while(scgit.hasNext()){
					String scgkey = scgit.next().toString();
					//log.info("*****************************************scgkey "+scgkey);
					HashMap earmap = (HashMap)scgMap.get(scgkey);
					//log.info("earmap "+earmap.size()+" scgkey "+scgkey);
					Iterator earite = earmap.keySet().iterator();
					double etot = 0;
					double dtot = 0;
					double perktot = 0;
					double eadjtot = 0;
					double earrtot = 0;
					double erectot = 0;
					double dadjtot = 0;
					double darrtot = 0;
					double drectot = 0;
					
					while(earite.hasNext()){
						String earncd = earite.next().toString();
						String str = earmap.get(earncd).toString();
						String s[] = str.split(",");
						
						String type = s[0];
						double amt = new Double(s[1]).doubleValue();
						double adj = new Double(s[2]).doubleValue();
						double arr = new Double(s[3]).doubleValue();
						double rec = new Double(s[4]).doubleValue();
						
						if(type.equalsIgnoreCase("E") || type.equalsIgnoreCase("PK") || type.equalsIgnoreCase("GPROF")|| type.equalsIgnoreCase("PRK")|| type.equalsIgnoreCase("AR")||type.equalsIgnoreCase("A")||type.equalsIgnoreCase("MISCADV")|| type.equalsIgnoreCase("MISCPK")|| type.equalsIgnoreCase("CPFAR")){
							
							etot = etot+amt;
							eadjtot=eadjtot+adj;
							earrtot=earrtot+arr;
							erectot=erectot+rec;
							
							
						}
						/*if(type.equalsIgnoreCase("PK")|| type.equalsIgnoreCase("PRK")|| type.equalsIgnoreCase("MISCPK")){
							perktot = perktot+amt;
						}*/
						if(type.equalsIgnoreCase("D") ||type.equalsIgnoreCase("OR") ||type.equalsIgnoreCase("CLCRC")||type.equalsIgnoreCase("EACPF")||type.equalsIgnoreCase("AAICPF")||type.equalsIgnoreCase("OD")||type.equalsIgnoreCase("DIES")||type.equalsIgnoreCase("ADV")||type.equalsIgnoreCase("ADVINT")||type.equalsIgnoreCase("MISCREC")||type.equalsIgnoreCase("AAI")||type.equalsIgnoreCase("Int") ||type.equalsIgnoreCase("AAI-BF")||type.equalsIgnoreCase("IT")){
							
							dtot =dtot+amt;
							dadjtot=dadjtot+adj;
							darrtot=darrtot+arr;
							drectot=drectot+rec;
							
							
						}
						
						//log.info("******earncd "+earncd+" type "+type+" amt "+amt+" etot "+etot+"perktot"+perktot+"dtot"+dtot);
						
						int payprocessid = Integer.parseInt(AutoGeneration.getNextCode("PAYPROCESS","PAYPROCESSID",11,con));
						//String payprocessquery = "insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD) values(PAYPROCESS_SEQ.nextval,'"+paytransid+"',(select ledgercd from EARNDEDUMASTER where EARNDEDUCD='"+earncd+"'),'"+amt+"','"+earncd+"','"+disckey+"','"+scgkey+"')";
						String payprocessquery = "insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD) values(PAYPROCESS_SEQ.nextval,'"+paytransid+"',(select ledgercd from SALHEAD_LEDG_MAPPING where EARNDEDUCD='"+earncd+"' and cadrecod="+scgkey+"),'"+amt+"','"+earncd+"','"+disckey+"','"+scgkey+"')";
						//log.info("payprocessquery "+payprocessquery);
						//stmt.addBatch(payprocessquery);
						tpppstmt.setInt(1,paytransid);
						tpppstmt.setString(2,earncd);
						tpppstmt.setString(3,scgkey);
						tpppstmt.setDouble(4,amt);
						tpppstmt.setString(5,earncd);
						tpppstmt.setString(6,disckey);
						tpppstmt.setString(7,scgkey);
						tpppstmt.setDouble(8,adj);
						tpppstmt.setDouble(9,arr);
						tpppstmt.setDouble(10,rec);
						tpppstmt.addBatch();
						
						
					}
					//log.info("etot "+etot+" dtot "+dtot+"perktot "+perktot);
					salpayable = etot+earrtot-erectot-(dtot+darrtot-drectot+perktot);
					String payprocesssptoperk = "insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD) values(PAYPROCESS_SEQ.nextval,'"+paytransid+"','','"+perktot+"',(select EARNDEDUCD from CONFIGMAPPING where  CONFIGNAME='TOTPERK' ) ,'"+disckey+"','"+scgkey+"')";
					//log.info("payprocesssptoperk qry "+payprocesssptoperk);
					//stmt.addBatch(payprocesssptoperk);
					/*toperkpstmt.setInt(1,paytransid);
					toperkpstmt.setDouble(2,perktot);
					toperkpstmt.setString(3,disckey);
					toperkpstmt.setString(4,scgkey);
					toperkpstmt.addBatch();*/
					
					String payprocessspcpfquery = "insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD,STAFFCTGRYCD) values(PAYPROCESS_SEQ.nextval,'"+paytransid+"','"+salpaybleledgcode+"','"+salpayable+"','','"+disckey+"','"+scgkey+"')";
					//log.info("salpaybleledgcode qry "+payprocessspcpfquery);
					//stmt.addBatch(payprocessspcpfquery);
					salledgpstmt.setInt(1,paytransid);
					salledgpstmt.setString(2,salpaybleledgcode);
					salledgpstmt.setDouble(3,salpayable);
					salledgpstmt.setString(4,disckey);
					salledgpstmt.setString(5,scgkey);	
					salledgpstmt.setDouble(6,0);
					salledgpstmt.setDouble(7,0);
					salledgpstmt.setDouble(8,0);
					salledgpstmt.addBatch();
					
				}
				
				
				//log.info("payprocessquery "+payprocessquery);
				//String payprocessaaicpfquery = "insert into PAYPROCESS (PAYPROCESSID,PAYTRANSID,LEDGERCD,PAYPROCESSAMT,EARNDEDUCD,DISCIPLINECD) values(PAYPROCESS_SEQ.nextval,'"+paytransid+"','"+aaicpf+"','"+aacpf+"','','"+key+"')";
				//stmt.addBatch(payprocessaaicpfquery);
				
				
				//log.info("earntot "+earntot+" dedtot "+dedtot);
				
				
			}
			
			con.setAutoCommit(false);
			int[] updateCounts = null;
			if(hasEmployees){				
				
				updateCounts = stmt.executeBatch();	
				tpdpstmt6.executeBatch();				
				noarrpstmt.executeBatch();
				gprofpstmt.executeBatch();
				miscpstmt.executeBatch();
				nomisrecpstmt.executeBatch();
				empadvrecpstmt.executeBatch();
				mergeitpstmt.executeBatch();
				empadvpstmt.executeBatch();
				advfinpstmt.executeBatch();
				//updadvpstmt.executeBatch();
				cpfadvpstmt.executeBatch();
				intupdadvpstmt.executeBatch();
				intcompadvpstmt.executeBatch();
				nointcpfpstmt.executeBatch();
				nointadvpstmt.executeBatch();
				nopmnrpstmt.executeBatch();
				loppstmt.executeBatch();
				jvcpfpstmt.executeBatch();
				
				tpppstmt.executeBatch();
				toperkpstmt.executeBatch();
				salledgpstmt.executeBatch();
				
				tpdpstmt6.close();				
				noarrpstmt.close();
				gprofpstmt.close();
				miscpstmt.close();
				nomisrecpstmt.close();
				empadvrecpstmt.close();
				mergeitpstmt.close();
				empadvpstmt.close();
				advfinpstmt.close();
				//updadvpstmt.close();
				cpfadvpstmt.close();
				intupdadvpstmt.close();
				intcompadvpstmt.close();
				nointcpfpstmt.close();
				nointadvpstmt.close();
				nopmnrpstmt.close();
				loppstmt.close();
				jvcpfpstmt.close();
				stmt.close();
				tpppstmt.close();
				toperkpstmt.close();
				salledgpstmt.close();
			    //log.info("updateCounts "+updateCounts.length);
			}else{
				throw new ApplicationException("No Employees in this Station / Sal has not been assigned for employees in this Station");
			}
			/*
			 * updating opening and closing balances at the financial year end
			 */
			/*String opclbalcommand = "{call emp_adv_opcl_bal_update(?,?)}";    
			CallableStatement opcstmt = con.prepareCall (opclbalcommand);
			opcstmt.setString(1, ppinfo.getPayrollmonthid());
			opcstmt.setString(2, fyrcd);    
			
			opcstmt.execute();
			opcstmt.close();*/
			
			con.commit();
		} catch(BatchUpdateException b) {
			log.info("<<<<<<<<<< payProcess() Batch update Exception ------>>>>>>>>>>>>");
			int[] updateCounts = b.getUpdateCounts();
		      checkUpdateCounts(updateCounts);
			
			log.printStackTrace(b);	
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
			throw new ApplicationException(b.getMessage());
		}
		catch(ApplicationException e){
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		}
		catch (Exception e) {
			log.info("<<<<<<<<<< payProcess() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
			throw new ApplicationException("PayProcess caught exceptions and cant continue");
			
		}finally {
			try {
				
				db.closeRs();
				db.closeCon();				
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
		
	}
	
	private  void checkUpdateCounts(int[] updateCounts) {
		log.info("updateCounts.length "+updateCounts.length);
	    for (int i = 0; i < updateCounts.length; i++) {
	      if (updateCounts[i] >= 0) {
	        // Successfully executed; the number represents number of affected rows
	        log.info("OK: updateCount=" + updateCounts[i]+" i "+i);
	      } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
	        // Successfully executed; number of affected rows not available
	      	log.info("OK: updateCount=Statement.SUCCESS_NO_INFO");
	      } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
	      	log.info("updateCount=Statement.EXECUTE_FAILED i  "+i);
	      }
	    }
	  }
	/*public void calcDAHike2Emp(DBAccess db,String qry,double daper,String usernm,String ledcd) throws NumberFormatException, SQLException, Exception{
		Connection con = db.getConnection();
		Statement stmt = con.createStatement();
		double dapercent = 0;
		ResultSet rs = db.getRecordSet(qry);
		String en = "";
		String earndedcd = "";
		String sg ="",ledgercd="";
		double basicsal = 0;
		
		while(rs.next()){
			earndedcd = rs.getString("EARNDEDUCD");
			en = rs.getString("empno");
			sg = rs.getString("STAFFCTGRYCD");
			ledgercd = rs.getString("LEDGERCD");
			ResultSet amtrs = db.getRecordSet("Select sum(amount)amt from empernsdeducs where EARNDEDUCD in ("+rs.getString("DEPENDSON")+") and empno='"+en+"' ");
			if(amtrs.next()){
				basicsal = amtrs.getDouble("amt");
			}
			db.closeRs();
			log.info(ledgercd+"----ledgercodes are -------"+ledcd);
			if(!ledgercd.equals(ledcd)){
				String updearnmsterqry = "Update earndedumaster set PERCENTAGEVALUE='"+daper+"' where EARNDEDUCD='"+earndedcd+"'";
				log.info(updearnmsterqry);
				
				stmt.addBatch(updearnmsterqry);
				
				int slno = Integer.parseInt(AutoGeneration.getNextCode("historyempernsdeducs","slno",10,con));
				stmt.addBatch("Insert into historyempernsdeducs (slno,EMPNO,STAFFCTGRYCD,EARNDEDUCD,AMOUNT,USERCD,STATUS) values ('"+slno+"','"+en+"','"+sg+"','"+rs.getString("EARNDEDUCD")+"','"+rs.getString("amount")+"','"+usernm+"','A') ");
				
				log.info("Basic salary is ---- "+basicsal);
				int daamt = (int)Math.round(basicsal*daper/100);
				log.info("daamt is "+daamt);
				String updempdaqry ="Update empernsdeducs set AMOUNT='"+daamt+"' where EMPNO='"+en+"' and EARNDEDUCD='"+earndedcd+"'"; 
				log.info(updempdaqry);
				stmt.addBatch(updempdaqry);
				stmt.executeBatch();
			}else {
				int cpfamt = 0;
				cpfamt = (int)Math.round(basicsal*rs.getDouble("PERCENTAGEVALUE")/100);
				String upQry = "Update empernsdeducs set AMOUNT='"+(cpfamt)+"' where EMPNO='"+en+"' and EARNDEDUCD='"+earndedcd+"' ";
				log.info("DA Hike cpf update Qry is "+upQry);
				db.executeUpdate(upQry);
			}
		}
		rs.close();
		stmt.close();
		
		String daqr1 =" Update dapercentage set STATUS='I' where STAFFCTGRYCD='"+sg+"' and STATUS='A' and UPDATEDEMPL='Y'";
		db.executeUpdate(daqr1);
		String daqry2 ="Update dapercentage set STATUS='A' ,UPDATEDEMPL='Y' where STAFFCTGRYCD='"+sg+"' and STATUS='I' and UPDATEDEMPL='N'"; 
		db.executeUpdate(daqry2);
		
		//stmt.addBatch(daqr1);
		//stmt.addBatch(daqry2);
		log.info(daqr1);
		log.info(daqry2);
	}*/
	public String getPayMonths(PayrollProcessInfo ppinfo){
		String paidMonths = "";
		String checkQry = "select PAYROLLMONTHID from PAYROLLPROCESS where DISCIPLINECD= '"+ppinfo.getDisciplinecd()+"' and STAFFCTGRYCD = '"+ppinfo.getStaffctgrycd()+"'";
		String paymonthid = "";
		try{
			db.makeConnection();
			con = db.getConnection();
			log.info("checkQry "+checkQry);
			rs = db.getRecordSet(checkQry);
			while(rs.next()){
				paymonthid = rs.getString("PAYROLLMONTHID");
				paidMonths = paidMonths+paymonthid+",";
			}
			if(!paidMonths.equals("")){
			 paidMonths = paidMonths.substring(0,paidMonths.length()-1);
			}
			log.info("paidMonths "+paidMonths);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< getPayMonths() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return paidMonths;
	}
	public JournalVoucherInfo getJounralVoucher(String paytransid){
		JournalVoucherInfo jvinfo = new JournalVoucherInfo();
		List earnList = new ArrayList();
		double earnTot = 0.0;
		List dedList = new ArrayList();
		double dedTot = 0.0;
		//String query = "select prp.STAFFCTGRYCD,prp.PAYROLLMONTHID,sd.DISCIPLINENM,sc.STAFFCTGRYNM,mp.PAYROLLMONTHNM,mp.PAYROLLYEAR from PAYROLLPROCESS prp,STAFFCATEGORY sc,MONTHLYPAYROLL mp where PAYTRANSID="+paytransid+"  and prp.STAFFCTGRYCD = sc.STAFFCTGRYCD and prp.PAYROLLMONTHID = mp.PAYROLLMONTHID";
		String query = "select prp.STAFFCTGRYCD,prp.PAYROLLMONTHID,sc.STAFFCTGRYNM,mp.PAYROLLMONTHNM,mp.PAYROLLYEAR from PAYROLLPROCESS prp,STAFFCATEGORY sc,MONTHLYPAYROLL mp where PAYTRANSID="+paytransid+" and prp.STAFFCTGRYCD = sc.STAFFCTGRYCD and prp.PAYROLLMONTHID = mp.PAYROLLMONTHID";
		log.info("getJounralVoucher "+query);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(query);
			if(rs.next()){
				jvinfo.setStaffcategory(rs.getString("STAFFCTGRYNM"));
				//jvinfo.setDiscipline(rs.getString("DISCIPLINENM"));
				jvinfo.setMonth(rs.getString("PAYROLLMONTHNM")+"-"+rs.getString("PAYROLLYEAR"));;
			}
			db.closeRs();
			String payprocessqry = "select PAYTRANSID,ed.TYPE,pp.LEDGERCD,lm.LEDGERNM,PAYPROCESSAMT,pp.EARNDEDUCD,ed.EARNDEDUNM from PAYPROCESS pp,LEDGERMASTER lm,EARNDEDUMASTER ed where PAYTRANSID="+paytransid+" and pp.ledgercd = lm.LEDGERCD and pp.EARNDEDUCD = ed.EARNDEDUCD";
			log.info("payprocessqry "+payprocessqry);
			ResultSet rs1 = db.getRecordSet(payprocessqry);
			while(rs1.next()){
				PayProcessInfo pinfo = new PayProcessInfo();
				pinfo.setEarndednm(rs1.getString("EARNDEDUNM"));
				pinfo.setLedgercd(rs1.getString("LEDGERCD"));
				double amt = rs1.getDouble("PAYPROCESSAMT");
				pinfo.setAmount(amt);
			
				String type =rs1.getString("TYPE"); 
				//log.info("amt: "+amt+" type "+type);
				if(type.equals("A") || type.equals("E")){
					earnTot = earnTot+amt;
					earnList.add(pinfo);
				}
				if(type.equals("D")|| type.equals("IT")|| type.equals("ADV")){
					dedTot = dedTot+amt;
					dedList.add(pinfo);
				}				
			}
			db.closeRs();
			ResourceBundle bundle = ResourceBundle.getBundle("com.aims.resource.ApplicationConfig");
			String salpaybleledgcode = bundle.getString("Salaraypayable");			
			String aaicpf = bundle.getString("AAICPF");
			
			String selqry = "select pp.LEDGERCD,lm.LEDGERNM,pp.PAYPROCESSAMT from PAYPROCESS pp,LEDGERMASTER lm where pp.ledgercd = lm.ledgercd and pp.ledgercd in ("+aaicpf+","+salpaybleledgcode+") and paytransid="+paytransid;
			log.info("getting salpaybale,aaicpf qry"+selqry);
			ResultSet rs2 = db.getRecordSet(selqry);
			while(rs2.next()){
				PayProcessInfo pinfo = new PayProcessInfo();
				pinfo.setEarndednm(rs2.getString("LEDGERNM"));
				pinfo.setLedgercd(rs2.getString("LEDGERCD"));
				double amt = rs2.getDouble("PAYPROCESSAMT");
				pinfo.setAmount(amt);
				dedTot = dedTot+amt;
				dedList.add(pinfo);
			}
			db.closeRs();
		}catch (Exception e) {
			log.info("<<<<<<<<<< getJounralVoucher() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		jvinfo.setDeductionList(dedList);
		log.info("dedlist "+jvinfo.getDeductionList().size());
		jvinfo.setEarningList(earnList);
		log.info("earnlist "+jvinfo.getEarningList().size());
		jvinfo.setDedTotal(dedTot);
		jvinfo.setEarnTotal(earnTot);
		log.info("dedTot "+dedTot+"earnTot "+earnTot);
		
		return jvinfo;
	}
	/*public double getLOPAmt(String type,String monthid,String empNo,double salamt,DBAccess db){
		double lopAmt = 0.0;
		try{
			String lopQry = "select nvl(lopdays,0)lopdays,(select to_char(last_day(payrollfromdt),'dd') from monthlypayroll where PAYROLLMONTHID='"+monthid+"')lastday,ISLOP, ISDIESNON from lop where empno='"+empNo+"' and month='"+monthid+"' ";
			log.info("lopQry is --- "+lopQry);
			ResultSet rs = db.getRecordSet(lopQry);
			if(rs.next()){
				
				 * Calculating amount based on lop, diesnon exists or not
				 
				if( ("OD".equals(type) && "Y".equals(rs.getString("ISLOP"))) || ("DIES".equals(type) && "Y".equals(rs.getString("ISDIESNON")))){
					lopAmt = Math.round((salamt/rs.getInt("lastday"))*rs.getInt("lopdays"));
				}else {
					lopAmt = 0;
				}
			}
			log.info("lopAmt is ---- "+lopAmt);
			db.closeRs();
		}catch (Exception e) {
			log.info("<<<<<<<<<< getLOPAmt() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		}
		return lopAmt;
	}*/
	public EmpPaySlipInfo getPayStopEarnDedcs(String cd){
		EmpPaySlipInfo info = new EmpPaySlipInfo();
		
		try{
			db.makeConnection();
			con = db.getConnection();
			String psQry = " select EARNINGS,DEDUCTIONS,PERKS,RECOVERIES from paystopconfig  where  STATUS='A' ";
			String empname = sh.getDescription("employeeinfo","EMPLOYEENAME","empno",cd,con);
			log.info("psQry is --- "+psQry);
			ResultSet rs = db.getRecordSet(psQry);
			StringTokenizer st = null;
			
			//info1.setEmpno(cd);
			//info1.setEmpname(empname);
			if(rs.next()){
				List list = new ArrayList();
				if(rs.getString("EARNINGS")!=null){
					st = new StringTokenizer(rs.getString("EARNINGS"),",");
					while(st.hasMoreTokens()){
						String edcd = st.nextToken();
						//log.info("----1----"+edcd);
						EarningMasterInfo edinfo = new EarningMasterInfo();
						edinfo.setEarningCd(new Integer(edcd));
						edinfo.setEarningName(sh.getDescription("earndedumaster","EARNDEDUNM","EARNDEDUCD",edcd,con));
						edinfo.setType(sh.getDescription("earndedumaster","type","EARNDEDUCD",edcd,con));
						list.add(edinfo);
					}
				}
				info.setEarnList(list);
				list = new ArrayList();
				if(rs.getString("DEDUCTIONS")!=null){
					st = new StringTokenizer(rs.getString("DEDUCTIONS"),",");
					while(st.hasMoreTokens()){
						String edcd = st.nextToken();
						//log.info("----2----"+edcd);
						EarningMasterInfo edinfo = new EarningMasterInfo();
						edinfo.setEarningCd(new Integer(edcd));
						edinfo.setEarningName(sh.getDescription("earndedumaster","EARNDEDUNM","EARNDEDUCD",edcd,con));
						edinfo.setType(sh.getDescription("earndedumaster","type","EARNDEDUCD",edcd,con));
						list.add(edinfo);
					}
				}
				info.setDeducList(list);
				list = new ArrayList();
				if(rs.getString("PERKS")!=null){
					st = new StringTokenizer(rs.getString("PERKS"),",");
					while(st.hasMoreTokens()){
						String edcd = st.nextToken();
						//log.info("----3----"+edcd);
						EarningMasterInfo edinfo = new EarningMasterInfo();
						edinfo.setEarningCd(new Integer(edcd));
						edinfo.setEarningName(sh.getDescription("earndedumaster","EARNDEDUNM","EARNDEDUCD",edcd,con));
						edinfo.setType(sh.getDescription("earndedumaster","type","EARNDEDUCD",edcd,con));
						list.add(edinfo);
					}
				}
				info.setPerkList(list);
				list = new ArrayList();
				if(rs.getString("RECOVERIES")!=null){
					st = new StringTokenizer(rs.getString("RECOVERIES"),",");
					while(st.hasMoreTokens()){
						String edcd = st.nextToken();
						//log.info("----4----"+edcd);
						EarningMasterInfo edinfo = new EarningMasterInfo();
						edinfo.setEarningCd(new Integer(edcd));
						edinfo.setEarningName(sh.getDescription("earndedumaster","EARNDEDUNM","EARNDEDUCD",edcd,con));
						edinfo.setType(sh.getDescription("earndedumaster","type","EARNDEDUCD",edcd,con));
						list.add(edinfo);
					}
				}
				info.setRecList(list);
				//list.removeAll(list);
				//info.set
			}
			db.closeRs();
			//log.info("Earn list size  is --3-- "+info.getEarnList().size());
			//info1.setInfo(info);
		}catch (Exception e) {
			log.info("<<<<<<<<<< getPayStopEarnDedcs() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return info;
	}
	public void addEmpPayStop(EmpPaySlipInfo info) throws ApplicationException {
		String name="";
		try{
			db.makeConnection();
			con = db.getConnection();
			int cnt = 0;
			cnt = db.getRecordCount("select count(*) from payrollprocess where payrollmonthid='"+info.getPayrollmonthid()+"' and stationcd = (select stationcd from employeeinfo where empno='"+info.getEmpNo()+"')");
			if(cnt>0){
				throw new ApplicationException("Salary has been generated for the selected PayrollMonth.");
			}
			cnt = 0;
			cnt = db.getRecordCount("select count(*) from emppaystop where payrollmonthid='"+info.getPayrollmonthid()+"' and empno='"+info.getEmpNo()+"'");
			if(cnt>0){
				throw new ApplicationException("Amounts have been already assigned for the selected employee and PayrollMonth ");
			}
			
			db.setAutoCommit(false);
			int paystopid = Integer.parseInt(AutoGeneration.getNextCode("emppaystop","PAYSTOPCD",5,con));
			
			StringBuffer insrtQry = new StringBuffer();
			//insrtQry.append("Insert into emppaystop(PAYSTOPCD,EMPNO,PAYROLLMONTHID,USERCD) values('"+paystopid+"','"+info.getEmpNo()+"','"+info.getPayrollmonthid()+"','"+info.getUsercd()+"')");
			insrtQry.append("Insert into emppaystop(PAYSTOPCD,EMPNO,PAYROLLMONTHID,USERCD,STATIONCD) values('"+paystopid+"','"+info.getEmpNo()+"','"+info.getPayrollmonthid()+"','"+info.getUsercd()+"',(select stationcd from employeeinfo where empno = '"+info.getEmpNo()+"'))");
			log.info("insrtQry addEmpPayStop is --- "+insrtQry.toString());
			
			db.executeUpdate(insrtQry.toString());
			insrtQry.delete(0,insrtQry.length());
			List list = info.getEarnList();
			int size = list.size();
			for(int i=0;i<size;i++){
				insrtQry.delete(0,insrtQry.length());
				PaySlipInfo pinfo=(PaySlipInfo)list.get(i);
				int paystopdetid = Integer.parseInt(AutoGeneration.getNextCode("emppaystopdet","EMPPAYSTOPDETCD",5,con));
				
				insrtQry.append(" Insert into emppaystopdet  values('"+paystopdetid+"','"+paystopid+"','"+pinfo.getEarnDeduCd()+"','"+pinfo.getAmount()+"','"+pinfo.getType()+"') ");
				log.info("insrtQry Detauils addEmpPayStop is --- "+insrtQry.toString());
				db.executeUpdate(insrtQry.toString());
			}
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpNo()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Employee Pay Stop New Of "+name+" Saved.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "N", String.valueOf(paystopid), con);
			db.commitTrans();
		}catch (Exception e) {
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("<<<<<<<<<< addEmpPayStop() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
			
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public List searchEmpPayStop(String cd) {
		List list = new ArrayList();
		PaySlipInfo info = new PaySlipInfo();
		try{
			db.makeConnection();
			con = db.getConnection();
			String qry = " select PAYSTOPCD,(EMPLOYEENAME)EMPNO,(PAYROLLMONTHNM||'-'||PAYROLLYEAR)PAYROLLMONTHID,(select count(*) From payrollprocess p where p.PAYROLLMONTHID=e.payrollmonthid)cnt from emppaystop e,employeeinfo emp,monthlypayroll mp where e.empno is not null and e.empno=emp.empno and e.payrollmonthid=mp.payrollmonthid ";
			if(!"".equals(cd))
				qry += " and empno='"+cd+"'";
			rs = db.getRecordSet(qry);
			while(rs.next()){
				info = new PaySlipInfo();
				info.setPaystopid(rs.getString("PAYSTOPCD"));
				info.setEmpNo(rs.getString("empno"));
				info.setPayrollmonthid(rs.getString("PAYROLLMONTHID"));
				if(rs.getInt("cnt")==0)
					info.setType("Y");
				else
					info.setType("N");
				list.add(info);
			}
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< addEmpPayStop() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public EmpPaySlipInfo findByPayStopId(String id){
		EmpPaySlipInfo epsinfo = new EmpPaySlipInfo();
		
		List el = new ArrayList();
		List dl = new ArrayList();
		List pl = new ArrayList();
		List recl = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();
			StringBuffer qry = new StringBuffer();
			qry.append(" select eps.PAYSTOPCD,epsdet.EMPPAYSTOPDETCD,epsdet.EARNDEDUCD,epsdet.AMOUNT ,edm.EARNDEDUNM,epsdet.TYPE,eps.EMPNO,ei.EMPLOYEENAME empname,eps.STATUS " +
					 " ,eps.PAYROLLMONTHID,mp.PAYROLLMONTHNM || '-' || mp.PAYROLLYEAR paymonth "+
					" from EMPPAYSTOP eps,EMPPAYSTOPDET epsdet,EARNDEDUMASTER edm,employeeinfo ei ,monthlypayroll mp" +
					" where eps.PAYSTOPCD = epsdet.PAYSTOPCD and eps.PAYROLLMONTHID = mp.PAYROLLMONTHID  " +
					" and ei.EMPNO = eps.EMPNO  " +
					" and epsdet.EARNDEDUCD = edm.EARNDEDUCD and eps.PAYSTOPCD = "+id);
			log.info("findByPayStopId qry:"+qry.toString());
			rs = db.getRecordSet(qry.toString());
			while(rs.next()){
				epsinfo.setEmpNo(rs.getString("empno"));
				epsinfo.setEmpname(rs.getString("empname"));
				epsinfo.setPayrollmonthid(rs.getString("paymonth"));
				epsinfo.setStatus(rs.getString("status"));
				epsinfo.setPaystopcd(rs.getString("PAYSTOPCD"));
				PaySlipInfo psinfo = new PaySlipInfo();
				
				psinfo.setEarnDeduName(rs.getString("EARNDEDUNM"));
				psinfo.setType(rs.getString("TYPE"));
				psinfo.setAmount(rs.getString("AMOUNT"));
				psinfo.setEmppaystopcd(rs.getString("EMPPAYSTOPDETCD"));
				if(psinfo.getType().equals("E") || psinfo.getType().equals("GPROF")){
					el.add(psinfo);
				}
				if(psinfo.getType().equals("D")||psinfo.getType().equals("IT")){
					dl.add(psinfo);
				}
				if(psinfo.getType().equals("PK")){
					pl.add(psinfo);
				}
				if(psinfo.getType().equals("OR")){
					recl.add(psinfo);
				}
			}
			epsinfo.setEarnList(el);
			epsinfo.setDeducList(dl);
			epsinfo.setPerkList(pl);
			epsinfo.setRecList(recl);
			log.info("el size "+el.size());
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< findByPayStopId() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		return epsinfo;
	}
	public void updateEmpPayStop(List l,String status,String empname,String usercd,String paystopcd1) throws ApplicationException{
		try{
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String paystopcd = "";
			if(status.equals("A")){
				for(int i=0;i<l.size();i++){
					PaySlipInfo info = (PaySlipInfo)l.get(i);
					String qry = "update EMPPAYSTOPDET set AMOUNT = '"+info.getAmount()+"'  where EMPPAYSTOPDETCD='"+info.getEarnDeduCd()+"' AND PAYSTOPCD='"+paystopcd1+"'";
					log.info("updateEmpPayStop qry: "+qry);
					db.executeUpdate(qry);
					paystopcd = info.getPaytransid();
				}
			}
			
			if(status.equals("A")){
				String qry = "update EMPPAYSTOP set status='A' where PAYSTOPCD = "+paystopcd1;
				log.info(qry);
				db.executeUpdate(qry);
			}
			if(status.equals("I")){
				String qry = "update EMPPAYSTOP set status='I' where PAYSTOPCD = "+paystopcd1;
				log.info(qry);
				db.executeUpdate(qry);
			}
			String activityDesc="Employee Pay Stop Of "+empname+" Updated.";
			ActivityLog.getInstance().write(usercd, activityDesc, "E", paystopcd1, con);
			db.commitTrans();
			
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< updateEmpPayStop() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		
		
	}
	
	public List viewPBRReport(PaySlipInfo psinfo,javax.servlet.http.HttpServletRequest request){
		List pbrList = new ArrayList();
		List empList = new ArrayList();
		EmployeeInfo empinfo = new EmployeeInfo();
		EmpPaySlipInfo empPayInfo = new EmpPaySlipInfo();
		PaySlipInfo psinfoo = null;
		
		String paytransids = "";
		double earnTotal = 0.0,deducTotal = 0.0;
		double advtotal = 0.0;
		double netamt = 0.0;
		
		double aprEarnAmount = 0.0;
		double mayEarnAmount = 0.0;
		double junEarnAmount = 0.0;
		double julEarnAmount = 0.0;
		double augEarnAmount = 0.0;
		double sepEarnAmount = 0.0;
		double octEarnAmount = 0.0;
		double novEarnAmount = 0.0;
		double decEarnAmount = 0.0;
		double janEarnAmount = 0.0;
		double febEarnAmount = 0.0;
		double marEarnAmount = 0.0;
	    
		double aprDeduAmount = 0.0;
		double mayDeduAmount = 0.0;
		double junDeduAmount = 0.0;
		double julDeduAmount = 0.0;
		double augDeduAmount = 0.0;
		double sepDeduAmount = 0.0;
		double octDeduAmount = 0.0;
		double novDeduAmount = 0.0;
		double decDeduAmount = 0.0;
		double janDeduAmount = 0.0;
		double febDeduAmount = 0.0;
		double marDeduAmount = 0.0;
	    
		double aprAdvAmount = 0.0;
		double mayAdvAmount = 0.0;
		double junAdvAmount = 0.0;
		double julAdvAmount = 0.0;
		double augAdvAmount = 0.0;
		double sepAdvAmount = 0.0;
		double octAdvAmount = 0.0;
		double novAdvAmount = 0.0;
		double decAdvAmount = 0.0;
		double janAdvAmount = 0.0;
		double febAdvAmount = 0.0;
		double marAdvAmount = 0.0;
		
		double aprNetAmount = 0.0;
		double mayNetAmount = 0.0;
		double junNetAmount = 0.0;
		double julNetAmount = 0.0;
		double augNetAmount = 0.0;
		double sepNetAmount = 0.0;
		double octNetAmount = 0.0;
		double novNetAmount = 0.0;
		double decNetAmount = 0.0;
		double janNetAmount = 0.0;
		double febNetAmount = 0.0;
		double marNetAmount = 0.0;
		
		try{
			db.makeConnection();
			con = db.getConnection();
			
			StringBuffer empSQL = new StringBuffer();
			empSQL.append("Select emplnumber,emp.empno,EMPLOYEENAME empname,get_description('upper(DESIGNATIONNM)','staffdesignation sds','sds.DESIGNATIONCD='''||emp.DESIGNATIONCD||'''')DESIGNATIONNM,CPFNO,BACNO,payids from  EmployeeInfo emp,staffdesignation dsg,employeepersonalinfo empp,(select join(cursor(SELECT paytransid FROM payrollprocess WHERE payrollmonthid IN (SELECT payrollmonthid FROM monthlypayroll WHERE fyearcd = "+psinfo.getFyearcd()+")))payids from dual)  ");
			empSQL.append(" where emp.empno = empp.empno and emp.DESIGNATIONCD = dsg.DESGCODE");
			
			if(!psinfo.getEmpNo().trim().equals("")){
				empSQL.append(" and emp.empno = "+psinfo.getEmpNo());
			}
			log.info(empSQL.toString());
			
			empSQL.append(" order by to_number(empno)");
			ResultSet emprs = db.getRecordSet(empSQL.toString());
			while(emprs.next()){
				int cnt = 0;
				cnt++;
				empPayInfo = new EmpPaySlipInfo();
				empinfo = new EmployeeInfo();
				
				empinfo.setEmplnumber(emprs.getString("emplnumber"));
				empinfo.setEmpName(emprs.getString("empname").toUpperCase());
				empinfo.setEmpDesignation(emprs.getString("DESIGNATIONNM"));
				empinfo.setCpfNo(emprs.getString("CPFNO"));
				empinfo.setBacno(emprs.getString("BACNO"));
				String pempno = emprs.getString("empno");
				empinfo.setEmpNo(pempno);
				if(paytransids.trim().equals("")){
					paytransids = emprs.getString("payids");
				}
				empPayInfo.setCount(cnt);
				empPayInfo.setEmpInfo(empinfo);
				empList.add(empPayInfo);
				
			}
			
			emprs.close();
	
			if(!paytransids.trim().equals("")){
				for(int i=0;i<empList.size();i++){
					EmpPaySlipInfo empPayInfo1 = (EmpPaySlipInfo)empList.get(i);
					
					EmployeeInfo empinfo1 = empPayInfo1.getEmpInfo();
					String empno1 = empinfo1.getEmpNo();
		
					String sql1 = "SELECT   dt.empno,dt.earndeducd, sum(amount+adjustments) amount,  dt.TYPE, earndedunm,printorder, ";
					
					int new1 = 1;
					String sql2 = "";
					sql2 = "select distinct paytransid from payrolldet where paytransid in ( "+paytransids+" )";
					ResultSet rsnew = db.getRecordSet(sql2);
					while(rsnew.next()){
						sql1 += " (select (amount+adjustments) apr from payrolldet where earndeducd = dt.earndeducd and empno = dt.empno and paytransid = '" + rsnew.getString("paytransid") + "') amt" + (new1++) + ",";
					}
					db.closeRs();
					
					sql1 = sql1.substring(0, sql1.length()-1); 
					
					sql1 += " FROM payrolldet dt, payrollprocess prs, earndedumaster edm";
					sql1 += " WHERE dt.paytransid = prs.paytransid AND dt.TYPE NOT IN ('AAI', 'AAI-BF')  AND edm.earndeducd = dt.earndeducd  ";
					sql1 += "  AND dt.paytransid IN ( "+paytransids+" ) AND empno = '"+empno1+"' and AMOUNT + ADJUSTMENTS <> 0 ";
					sql1 += " group by dt.empno, dt.earndeducd, dt.TYPE, earndedunm, printorder";
					sql1 += " order by printorder";
					log.info(sql1);
					ResultSet rs2 = db.getRecordSet(sql1);
					
					List earnningList = new ArrayList();
					List deducList = new ArrayList();
					List advList = new ArrayList();
					EmpPaySlipInfo empPayInfo2 = null;
					
					while(rs2.next()){
						
						empPayInfo2 = new EmpPaySlipInfo();
						
						psinfoo = new PaySlipInfo();
						psinfoo.setEarnDeduName(rs2.getString("EARNDEDUNM"));
						psinfoo.setShortName(rs2.getString("EARNDEDUNM"));
						
						double amt = rs2.getDouble("AMOUNT");
						double adj = 0;
						double famt = amt + adj;
						psinfoo.setAmount( new Integer((int)famt)+"");
						
						if (new1 > 1) psinfoo.setAprAmount(rs2.getDouble("AMT1")+"");
						if (new1 > 2) psinfoo.setMayAmount(rs2.getDouble("AMT2")+"");
						if (new1 > 3) psinfoo.setJunAmount(rs2.getDouble("AMT3")+"");
						if (new1 > 4) psinfoo.setJulAmount(rs2.getDouble("AMT4")+"");
						
						if (new1 > 5) psinfoo.setAugAmount(rs2.getDouble("AMT5")+"");
						if (new1 > 6) psinfoo.setSepAmount(rs2.getDouble("AMT6")+"");
						if (new1 > 7) psinfoo.setOctAmount(rs2.getDouble("AMT7")+"");
						if (new1 > 8) psinfoo.setNovAmount(rs2.getDouble("AMT8")+"");
						
						if (new1 > 9) psinfoo.setDecAmount(rs2.getDouble("AMT9")+"");
						if (new1 > 10) psinfoo.setJanAmount(rs2.getDouble("AMT10")+"");
						if (new1 > 11) psinfoo.setFebAmount(rs2.getDouble("AMT11")+"");
						if (new1 > 12) psinfoo.setMarAmount(rs2.getDouble("AMT12")+"");
						
						if(rs2.getString("type").equals("E"))
							psinfoo.setType("Earning");
						else if(rs2.getString("type").equals("PK"))
							psinfoo.setType("Perk");
						else if(rs2.getString("type").equals("PRK"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("OTA"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("GPROF"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("AR"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("A"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("MISCADV"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("D"))
							psinfoo.setType("Deduction");
						else if(rs2.getString("type").equals("OR"))
							psinfoo.setType("Other Recovery");
						else if(rs2.getString("type").equals("CLCRC"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("MISCREC"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("EACPF"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("OD"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("DIES"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("IT"))
							psinfoo.setType(rs2.getString("type"));
						else if(rs2.getString("type").equals("ADV"))
							psinfoo.setType("Advance");
						else if(rs2.getString("type").equals("ADVINT"))
							psinfoo.setType(rs2.getString("type"));
						else
						{
							psinfoo.setType(rs2.getString("type"));
						}
						
						if(rs2.getString("type").equals("E")||rs2.getString("type").equals("PK") || rs2.getString("type").equals("PRK")||rs2.getString("type").equals("OTA")||rs2.getString("type").equals("GPROF")||rs2.getString("type").equals("AR")||rs2.getString("type").equals("A")||rs2.getString("type").equals("MISCADV") ){
							
							earnningList.add(psinfoo);					
							earnTotal += famt;
							
							if (new1 > 1) aprEarnAmount += rs2.getDouble("AMT1");
							if (new1 > 2) mayEarnAmount += rs2.getDouble("AMT2");
							if (new1 > 3) junEarnAmount += rs2.getDouble("AMT3");
							if (new1 > 4) julEarnAmount += rs2.getDouble("AMT4");
							
							if (new1 > 5) augEarnAmount += rs2.getDouble("AMT5");
							if (new1 > 6) sepEarnAmount += rs2.getDouble("AMT6");
							if (new1 > 7) octEarnAmount += rs2.getDouble("AMT7");
							if (new1 > 8) novEarnAmount += rs2.getDouble("AMT8");
							
							if (new1 > 9) decEarnAmount += rs2.getDouble("AMT9");
							if (new1 > 10) janEarnAmount += rs2.getDouble("AMT10");
							if (new1 > 11) febEarnAmount += rs2.getDouble("AMT11");
							if (new1 > 12) marEarnAmount += rs2.getDouble("AMT12");
							
						}
						if(rs2.getString("type").equals("D")||rs2.getString("type").equals("OR")||rs2.getString("type").equals("CLCRC")||rs2.getString("type").equals("MISCREC")|| rs2.getString("type").equals("EACPF")||rs2.getString("type").equals("OD")|| rs2.getString("type").equals("DIES") || rs2.getString("type").equals("IT")|| rs2.getString("type").equals("ADV") || rs2.getString("type").equals("ADVINT") ) {
							if(rs2.getString("type").equals("ADV")|| rs2.getString("type").equals("ADVINT"))
							{
								advList.add(psinfoo);
								advtotal += famt;	
								
								if (new1 > 1) aprAdvAmount += rs2.getDouble("AMT1");
								if (new1 > 2) mayAdvAmount += rs2.getDouble("AMT2");
								if (new1 > 3) junAdvAmount += rs2.getDouble("AMT3");
								if (new1 > 4) julAdvAmount += rs2.getDouble("AMT4");
								
								if (new1 > 5) augAdvAmount += rs2.getDouble("AMT5");
								if (new1 > 6) sepAdvAmount += rs2.getDouble("AMT6");
								if (new1 > 7) octAdvAmount += rs2.getDouble("AMT7");
								if (new1 > 8) novAdvAmount += rs2.getDouble("AMT8");
								
								if (new1 > 9) decAdvAmount += rs2.getDouble("AMT9");
								if (new1 > 10) janAdvAmount += rs2.getDouble("AMT10");
								if (new1 > 11) febAdvAmount += rs2.getDouble("AMT11");
								if (new1 > 12) marAdvAmount += rs2.getDouble("AMT12");
								
							}
							else
							{
								deducList.add(psinfoo);
								deducTotal += famt;
								
								if (new1 > 1) aprDeduAmount += rs2.getDouble("AMT1");
								if (new1 > 2) mayDeduAmount += rs2.getDouble("AMT2");
								if (new1 > 3) junDeduAmount += rs2.getDouble("AMT3");
								if (new1 > 4) julDeduAmount += rs2.getDouble("AMT4");
								
								if (new1 > 5) augDeduAmount += rs2.getDouble("AMT5");
								if (new1 > 6) sepDeduAmount += rs2.getDouble("AMT6");
								if (new1 > 7) octDeduAmount += rs2.getDouble("AMT7");
								if (new1 > 8) novDeduAmount += rs2.getDouble("AMT8");
								
								if (new1 > 9) decDeduAmount += rs2.getDouble("AMT9");
								if (new1 > 10) janDeduAmount += rs2.getDouble("AMT10");
								if (new1 > 11) febDeduAmount += rs2.getDouble("AMT11");
								if (new1 > 12) marDeduAmount += rs2.getDouble("AMT12");	
							}
						}
						
						empPayInfo2.setEarnList(earnningList);
						empPayInfo2.setDeducList(deducList);
						empPayInfo2.setRecList(advList);
					}
					empPayInfo2.setEmpInfo(empinfo1);
					pbrList.add(empPayInfo2);
				}
		  }
			netamt =  earnTotal - deducTotal - advtotal;
			
			aprNetAmount = aprEarnAmount - aprDeduAmount - aprAdvAmount;
			mayNetAmount = mayEarnAmount - mayDeduAmount - mayAdvAmount;
			junNetAmount = junEarnAmount - junDeduAmount - junAdvAmount;
			julNetAmount = julEarnAmount - julDeduAmount - julAdvAmount;
			augNetAmount = augEarnAmount - augDeduAmount - augAdvAmount;
			sepNetAmount = sepEarnAmount - sepDeduAmount - sepAdvAmount;
			octNetAmount = octEarnAmount - octDeduAmount - octAdvAmount;
			novNetAmount = novEarnAmount - novDeduAmount - novAdvAmount;
			decNetAmount = decEarnAmount - decDeduAmount - decAdvAmount;
			janNetAmount = janEarnAmount - janDeduAmount - janAdvAmount;
			febNetAmount = febEarnAmount - febDeduAmount - febAdvAmount;
			marNetAmount = marEarnAmount - marDeduAmount - marAdvAmount;
			
			EmpPaySlipInfo totinfo = new EmpPaySlipInfo();
			
			totinfo.setEarnTotal(earnTotal);
			totinfo.setDeducTotal(deducTotal);
			totinfo.setAdvtotal(advtotal);
			totinfo.setNetAmt(netamt);
			
			request.setAttribute("totinfo", totinfo);
			
			PaySlipInfo monthinfo = new PaySlipInfo();
			
			monthinfo.setAprEarnAmount(aprEarnAmount);
			monthinfo.setMayEarnAmount(mayEarnAmount);
			monthinfo.setJunEarnAmount(junEarnAmount);
			monthinfo.setJulEarnAmount(julEarnAmount);
			monthinfo.setAugEarnAmount(augEarnAmount);
			monthinfo.setSepEarnAmount(sepEarnAmount);
			monthinfo.setOctEarnAmount(octEarnAmount);
			monthinfo.setNovEarnAmount(novEarnAmount);
			monthinfo.setDecEarnAmount(decEarnAmount);
			monthinfo.setJanEarnAmount(janEarnAmount);
			monthinfo.setFebEarnAmount(febEarnAmount);
			monthinfo.setMarEarnAmount(marEarnAmount);
			
			monthinfo.setAprDeduAmount(aprDeduAmount);
			monthinfo.setMayDeduAmount(mayDeduAmount);
			monthinfo.setJunDeduAmount(junDeduAmount);
			monthinfo.setJulDeduAmount(julDeduAmount);
			monthinfo.setAugDeduAmount(augDeduAmount);
			monthinfo.setSepDeduAmount(sepDeduAmount);
			monthinfo.setOctDeduAmount(octDeduAmount);
			monthinfo.setNovDeduAmount(novDeduAmount);
			monthinfo.setDecDeduAmount(decDeduAmount);
			monthinfo.setJanDeduAmount(janDeduAmount);
			monthinfo.setFebDeduAmount(febDeduAmount);
			monthinfo.setMarDeduAmount(marDeduAmount);
			
			monthinfo.setAprAdvAmount(aprAdvAmount);
			monthinfo.setMayAdvAmount(mayAdvAmount);
			monthinfo.setJunAdvAmount(junAdvAmount);
			monthinfo.setJulAdvAmount(julAdvAmount);
			monthinfo.setAugAdvAmount(augAdvAmount);
			monthinfo.setSepAdvAmount(sepAdvAmount);
			monthinfo.setOctAdvAmount(octAdvAmount);
			monthinfo.setNovAdvAmount(novAdvAmount);
			monthinfo.setDecAdvAmount(decAdvAmount);
			monthinfo.setJanAdvAmount(janAdvAmount);
			monthinfo.setFebAdvAmount(febAdvAmount);
			monthinfo.setMarAdvAmount(marAdvAmount);
			
			monthinfo.setAprNetAmount(aprNetAmount);
			monthinfo.setMayNetAmount(mayNetAmount);
			monthinfo.setJunNetAmount(junNetAmount);
			monthinfo.setJulNetAmount(julNetAmount);
			monthinfo.setAugNetAmount(augNetAmount);
			monthinfo.setSepNetAmount(sepNetAmount);
			monthinfo.setOctNetAmount(octNetAmount);
			monthinfo.setNovNetAmount(novNetAmount);
			monthinfo.setDecNetAmount(decNetAmount);
			monthinfo.setJanNetAmount(janNetAmount);
			monthinfo.setFebNetAmount(febNetAmount);
			monthinfo.setMarNetAmount(marNetAmount);
			
			request.setAttribute("monthinfo", monthinfo);
		}catch(Exception e){
			log.printStackTrace(e);
		}
		
		return pbrList;
	}
	public JournalVoucherInfo getPayJounralVoucher(String staffcategorycd, String payrollmonthid, String discplinecd, String station) {
		// TODO Auto-generated method stub
		return getPayJounralVoucher( staffcategorycd,  payrollmonthid,  discplinecd,  station,"");
	}
	public JournalVoucherInfo getPayJounralVoucher(String staffcategorycd,String payrollmonthid,String discplinecd,String station, String ledgerCd){
		JournalVoucherInfo jvinfo = new JournalVoucherInfo();
		String paytrasid = "";
		String StaffCd="";
		log.info("staffcategorycd"+staffcategorycd);
		if(staffcategorycd.equals("B")  )
			StaffCd="1,2";
		else
			StaffCd=staffcategorycd;
			
		String pjvqry = " SELECT paytransid, ed.TYPE, pp.ledgercd newcode,(select distinct OLDLEDGERCD from ledgermaster where ledgercd=pp.ledgercd)ledgercd, " +
				" ( select ledgernm from ledgermaster where ledgercd=pp.ledgercd ) ledgernm , " +
				" sum(payprocessamt) payprocessamt,sum(ADJAMT) ADJAMT,sum(ARREARS) ARREARS ,sum(RECOVERIES) RECOVERIES, " +
				"  ed.earndedunm, " +
				//",scg.STAFFCTGRYNM, " +
				" (select PAYROLLMONTHNM ||'-'||PAYROLLYEAR from MONTHLYPAYROLL where PAYROLLMONTHID = "+payrollmonthid+")month"+
				" FROM payprocess pp,  earndedumaster ed " +
				//",staffcategory scg" +
				"  WHERE paytransid = (SELECT prp.PAYTRANSID  FROM payrollprocess prp WHERE  prp.payrollmonthid = "+payrollmonthid+"  and stationcd = '"+station+"')" +
				
				"  AND pp.earndeducd = ed.earndeducd " +
				//"  and pp.DISCIPLINECD = sd.DISCIPLINECD " +
				"  and pp.DISCIPLINECD like '"+("".equals(discplinecd)?"%":discplinecd)+"' " +
				//" and pp.STAFFCTGRYCD = scg.STAFFCTGRYCD"+
				"  and pp.STAFFCTGRYCD in( "+StaffCd +")"+
				"  and pp.ledgercd like '"+("".equals(ledgerCd)?"%":ledgerCd)+"%'  " +
				" group by paytransid,ed.TYPE,pp.ledgercd ,pp.earndeducd,ed.earndedunm,ed.PRINTORDER"+
				" order by ed.PRINTORDER asc ";
		log.info("pjvqry"+pjvqry);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(pjvqry);
			List earnList = new ArrayList();
			double earnTot = 0.0;
			List dedList = new ArrayList();
			double dedTot = 0.0;
			while(rs.next()){
				
				jvinfo.setMonth(rs.getString("month"));
				//jvinfo.setStaffcategory(rs.getString("STAFFCTGRYNM"));
				
				paytrasid = rs.getString("paytransid");
							
				String type =rs.getString("TYPE"); 
				
				
					
					if(type.equalsIgnoreCase("E")||type.equalsIgnoreCase("OTA")||type.equalsIgnoreCase("PK")||type.equalsIgnoreCase("GPROF")|| type.equalsIgnoreCase("PRK")|| type.equalsIgnoreCase("MISCPK")||type.equalsIgnoreCase("AR")||type.equalsIgnoreCase("A")||type.equalsIgnoreCase("MISCADV")|| type.equalsIgnoreCase("CPFAR")||type.equalsIgnoreCase("JVCPF")){
						double amt = rs.getDouble("PAYPROCESSAMT")+rs.getDouble("ARREARS");
						if(amt !=0){
						PayProcessInfo pinfo = new PayProcessInfo();
						pinfo.setEarndednm(rs.getString("EARNDEDUNM"));
						pinfo.setLedgercd(rs.getString("newcode"));				
						pinfo.setAmount(amt);
						earnTot = earnTot+amt;
						earnList.add(pinfo);
						}
					}
					if(type.equalsIgnoreCase("D")||type.equalsIgnoreCase("SALPAY") || type.equalsIgnoreCase("IT")|| type.equalsIgnoreCase("ADV")||type.equalsIgnoreCase("TOTPERK")||type.equalsIgnoreCase("AAI")|| type.equalsIgnoreCase("OD")||type.equalsIgnoreCase("AAI-BF") ||type.equalsIgnoreCase("OR")|| type.toUpperCase().equals("INT")|| type.equalsIgnoreCase("ADVINT")|| type.equalsIgnoreCase("MISCREC")||type.equalsIgnoreCase("CLCRC")||type.equalsIgnoreCase("DIES")||type.equalsIgnoreCase("EACPF")||type.equalsIgnoreCase("AAICPF")){
						double amt = rs.getDouble("PAYPROCESSAMT")+rs.getDouble("ARREARS");
						if(amt !=0){
						PayProcessInfo pinfo = new PayProcessInfo();
						pinfo.setEarndednm(rs.getString("EARNDEDUNM"));
						pinfo.setLedgercd(rs.getString("newcode"));				
						pinfo.setAmount(amt-rs.getDouble("RECOVERIES"));
						dedTot = dedTot+amt-rs.getDouble("RECOVERIES");
						dedList.add(pinfo);
						}
					}		
					if((type.equalsIgnoreCase("E")||type.equalsIgnoreCase("OTA")||type.equalsIgnoreCase("PK")||type.equalsIgnoreCase("GPROF")|| type.equalsIgnoreCase("PRK")|| type.equalsIgnoreCase("MISCPK")||type.equalsIgnoreCase("AR")||type.equalsIgnoreCase("A")||type.equalsIgnoreCase("MISCADV")|| type.equalsIgnoreCase("CPFAR")||type.equalsIgnoreCase("JVCPF"))&& rs.getDouble("RECOVERIES")>0){
						double amt = rs.getDouble("RECOVERIES");
						if(amt !=0){
						PayProcessInfo pinfo = new PayProcessInfo();
						pinfo.setEarndednm(rs.getString("EARNDEDUNM")+" REC");
						pinfo.setLedgercd(rs.getString("newcode"));				
						pinfo.setAmount(amt);
						dedTot = dedTot+amt;
						dedList.add(pinfo);
						}
					}
				
				
				
			}
			
			/*ResourceBundle bundle = ResourceBundle.getBundle("com.aims.resource.ApplicationConfig");
			String salpaybleledgcode = bundle.getString("Salaraypayable");			
			
			
			String selqry = "select pp.LEDGERCD newcode,lm.LEDGERNM LEDGERNM,(select OLDLEDGERCD from ledgermaster where ledgercd=pp.ledgercd)ledgercd,pp.PAYPROCESSAMT from PAYPROCESS pp,LEDGERMASTER lm where pp.ledgercd = lm.ledgercd and pp.ledgercd in ("+salpaybleledgcode+") and paytransid="+paytrasid+" and STAFFCTGRYCD = "+staffcategorycd+" and DISCIPLINECD = "+discplinecd ;
			log.info("getting salpaybale,aaicpf qry"+selqry);
			ResultSet rs2 = db.getRecordSet(selqry);
			while(rs2.next()){
				PayProcessInfo pinfo = new PayProcessInfo();
				pinfo.setEarndednm(rs2.getString("LEDGERNM"));
				pinfo.setLedgercd(rs2.getString("LEDGERCD"));
				double amt = rs2.getDouble("PAYPROCESSAMT");
				pinfo.setAmount(amt);
				dedTot = dedTot+amt;
				dedList.add(pinfo);
			}*/
			
			
			
			jvinfo.setDeductionList(dedList);
			log.info("dedlist "+jvinfo.getDeductionList().size());
			jvinfo.setEarningList(earnList);
			log.info("earnlist "+jvinfo.getEarningList().size());
			jvinfo.setDedTotal(dedTot);
			jvinfo.setEarnTotal(earnTot);
			log.info("dedTot "+dedTot+"earnTot "+earnTot);
			
			
			
			
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< getPayJounralVoucher() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return jvinfo;
	}

	public JournalVoucherInfo getSummaryJV(String staffcategorycd, String payrollmonthid, String discplinecd, String station, String ledgerCd) {
		JournalVoucherInfo jvinfo = new JournalVoucherInfo();
		discplinecd = discplinecd==null?"":discplinecd.trim();
		String pjvqry = " SELECT ed.earndedunm, ed.TYPE, ceil(sum(payprocessamt)) payprocessamt, " +
				" (select PAYROLLMONTHNM ||'-'||PAYROLLYEAR from MONTHLYPAYROLL where PAYROLLMONTHID = "+payrollmonthid+")month"+
				" FROM payprocess pp,  earndedumaster ed " +
				"  WHERE paytransid = (SELECT prp.PAYTRANSID  FROM payrollprocess prp WHERE  prp.payrollmonthid = "+payrollmonthid+"  and stationcd = '"+station+"')" +
				"  AND pp.earndeducd = ed.earndeducd and pp.DISCIPLINECD like '"+("".equals(discplinecd)?"%":discplinecd)+"' " +
				"  and ed.EARNDEDUCD not in('74','150','132') group by ed.earndedunm,ed.PRINTORDER, ed.TYPE"+
				" order by ed.PRINTORDER asc ";					
				
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(pjvqry);
			List earnList = new ArrayList();
			double earnTot = 0.0;
			List dedList = new ArrayList();
			double dedTot = 0.0;
			if(!"".equals(discplinecd))
				jvinfo.setStaffcategory((new SQLHelper()).getDescription("paybillmt","PAYBILLNM","PAYBILLCD",discplinecd,con));
			while(rs.next()){
				PayProcessInfo pinfo = new PayProcessInfo();
				jvinfo.setMonth(rs.getString("month"));
				pinfo.setEarndednm(rs.getString("EARNDEDUNM"));
				pinfo.setAmount(rs.getDouble("PAYPROCESSAMT"));	
				String type =rs.getString("TYPE"); 
				if(pinfo.getAmount()!=0){
					if(type.equalsIgnoreCase("E")||type.equalsIgnoreCase("OTA")||type.equalsIgnoreCase("PK")||type.equalsIgnoreCase("GPROF")|| type.equalsIgnoreCase("PRK")|| type.equalsIgnoreCase("MISCPK")||type.equalsIgnoreCase("AR")||type.equalsIgnoreCase("A")||type.equalsIgnoreCase("MISCADV")|| type.equalsIgnoreCase("CPFAR")||type.equalsIgnoreCase("JVCPF")){
						earnTot = earnTot+pinfo.getAmount();
						earnList.add(pinfo);
					}
					if(type.equalsIgnoreCase("D")||type.equalsIgnoreCase("SALPAY") || type.equalsIgnoreCase("IT")|| type.equalsIgnoreCase("ADV")||type.equalsIgnoreCase("TOTPERK")||type.equalsIgnoreCase("AAI")|| type.equalsIgnoreCase("OD")||type.equalsIgnoreCase("AAI-BF") ||type.equalsIgnoreCase("OR")|| type.toUpperCase().equals("INT")|| type.equalsIgnoreCase("ADVINT")|| type.equalsIgnoreCase("MISCREC")||type.equalsIgnoreCase("CLCRC")||type.equalsIgnoreCase("DIES")||type.equalsIgnoreCase("EACPF")||type.equalsIgnoreCase("AAICPF")){
						dedTot = dedTot+pinfo.getAmount();
						dedList.add(pinfo);
					}		
				}
			}
			jvinfo.setDeductionList(dedList);
			jvinfo.setEarningList(earnList);
			jvinfo.setDedTotal(dedTot);
			jvinfo.setEarnTotal(earnTot);			
		}catch (Exception e) {
			log.info("<<<<<<<<<< getSummaryJV() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return jvinfo;
	}
}
