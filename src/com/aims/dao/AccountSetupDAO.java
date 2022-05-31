package com.aims.dao;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.sql.*;

import java.util.List;



import com.epis.utilities.ActivityLog;
import com.epis.utilities.ApplicationException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.DateConverter;
import com.epis.utilities.Log;

import com.aims.info.accountsetup.AccountTypeInfo;
import com.aims.info.accountsetup.BankMasterInfo;
import com.aims.info.accountsetup.BaseAccountInfo;
import com.aims.info.accountsetup.FinancialYearInfo;
import com.aims.info.accountsetup.GroupAccountInfo;
import com.aims.info.accountsetup.LedgerInfo;
import com.aims.info.accountsetup.SearchAccountTypeInfo;
import com.aims.info.accountsetup.SearchBaseAccInfo;
import com.aims.info.accountsetup.SearchFinYearInfo;
import com.aims.info.accountsetup.SearchLedgerInfo;
import com.aims.info.accountsetup.SearchSubLedgerInfo;
import com.aims.info.accountsetup.SubLedgerInfo;
import com.aims.info.configuration.RegionInfo;
import com.aims.info.staff.ArrearsInfo;
import com.aims.info.staffconfiguration.EarningMasterInfo;
import com.aims.info.staffconfiguration.StaffCategoryInfo;

public class AccountSetupDAO {
	Properties prop=null;	
	DBAccess db = DBAccess.getInstance();
	DateConverter dc = new DateConverter();
	
	Connection con = null;
	ResultSet rs = null;
	String[] months = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
	private static AccountSetupDAO accountsetupInstance = new AccountSetupDAO();
	public AccountSetupDAO() {
		prop=CommonUtil.getPropsFile(Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,AccountSetupDAO.class);
		
	}
	public static AccountSetupDAO getInstance() {
		return accountsetupInstance;
	}
	
	Log log = new Log(AccountSetupDAO.class);
	
	public void finYearAdd(FinancialYearInfo fyinfo) {
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
			String updqry = "update financialyear set status = 'I' where status = 'A'";
			db.executeUpdate(updqry);
			
			int cd = Integer.parseInt(AutoGeneration.getNextCode("financialyear","fyearcd",10,con));
			String sSQL = "insert into financialYear (fyearcd ,fyearnm,fyeardescr,fromdate,todate,status) values ('"+cd+"','"+fyinfo.getFinyearName()+"','"+fyinfo.getFinyearDesc()+"','"+fyinfo.getFromDate()+"','"+fyinfo.getToDate()+"','A')";
			log.info("sSQL "+sSQL);
			db.executeUpdate(sSQL);
			log.info("after group save");
			int monthId = Integer.parseInt(AutoGeneration.getNextCode("monthlypayroll","payrollmonthid",11,con));
			String fdt = "";
			fdt = fyinfo.getFromDate();
			for(int i=0;i<12;i++){
				String ssSQL ="",ssSQL1 ="";
				if(i==0)
					ssSQL1 ="Select '"+fdt+"' fromdate,to_char(LAST_DAY('"+fdt+"'),'dd/Mon/yyyy')todate,to_char(to_date('"+fdt+"','dd/Mon/yyyy'),'YYYY')year from dual";
				else
					ssSQL1 ="Select to_char(to_date('"+fdt+"','dd/Mon/yyyy')+1,'dd/Mon/YYYY') fromdate,to_char(LAST_DAY(to_date('"+fdt+"','dd/Mon/yyyy')+1),'dd/Mon/yyyy')todate,to_char(to_date('"+fdt+"','dd/Mon/yyyy')+1,'YYYY')year from dual";
				log.info("ssSQL1 is == "+ssSQL1);
				rs = db.getRecordSet(ssSQL1);
				if(rs.next()){
					ssSQL ="insert into monthlypayroll(payrollmonthid, payrollmonthnm, payrollyear, payrollfromdt, payrolltodt,fyearcd  ) values ("+(monthId+i)+",'"+months[i]+"','"+rs.getString("year")+"','"+rs.getString("fromdate")+"','"+rs.getString("todate")+"',"+cd+")";
				}
				fdt = rs.getString("todate");
				log.info("MonthlyPayroll Query is == "+ssSQL);
				db.closeRs();
				
				db.executeUpdate(ssSQL);
			}
			log.info("commiting");
			String activityDesc="Financial Year Master for Financial Year  "+fyinfo.getFinyearName()+" Saved.";
			ActivityLog.getInstance().write(fyinfo.getUsercd(), activityDesc, "N", String.valueOf(cd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			try{
				db.rollbackTrans();
			}catch(Exception e1){
				
			}
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List searchFinYearTypes(SearchFinYearInfo accountFinfo) {
		ArrayList alist = new ArrayList();
		SearchFinYearInfo info = null;	
		
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
			String searhFinYearquery ="select FYEARCD,FYEARNM,FYEARDESCR,to_char(FROMDATE,'dd/Mon/yyyy')FROMDATE,to_char(TODATE,'dd/Mon/yyyy')TODATE,decode(status,'I','InActive','A','Active')STATUS from financialyear where FYEARCD is not null ";
			System.out.println("searhFinYearquery "+searhFinYearquery);
			log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
			
		
			if (accountFinfo.getSearchfinyearCd()!=null) {
				searhFinYearquery = searhFinYearquery+" and FYEARCD like '%"+accountFinfo.getSearchfinyearCd().trim()+"%'";
			}
			
			if (!(accountFinfo.getSearchfinyearName().equals(""))) {
				searhFinYearquery = searhFinYearquery+" and upper(FYEARNM) like upper('%"+ accountFinfo.getSearchfinyearName().trim()+"%')";
			}
			
			if (accountFinfo.getSearchfromDate()!=null && !accountFinfo.getSearchfromDate().equals("")) {
				searhFinYearquery = searhFinYearquery+" and FROMDATE like to_date('"+ accountFinfo.getSearchfromDate() + "','dd/mm/yyyy')";
			}			
		
			if (accountFinfo.getSearchtoDate()!=null && !accountFinfo.getSearchtoDate().equals("")) {
				searhFinYearquery = searhFinYearquery+" and TODATE like to_date('"+ accountFinfo.getSearchtoDate() + "','dd/mm/yyyy')";
				log.info("sdfgsdhfsdf");
			}			
			
			if (accountFinfo.getStatus()!=null) {
				if(!(accountFinfo.getStatus().equals(""))){
					searhFinYearquery = searhFinYearquery+" and STATUS = '"+accountFinfo.getStatus()+"'"; 
				}
			}
			searhFinYearquery = searhFinYearquery+" order by fromdate ";
					
			System.out.println("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
			rs = db.getRecordSet(searhFinYearquery);
			
			while(rs.next()){
				info = new SearchFinYearInfo();
				info.setSearchfinyearCd(rs.getString("FYEARCD"));
				info.setSearchfinyearName(rs.getString("FYEARNM"));
				info.setSearchfinyearDesc(rs.getString("FYEARDESCR"));
				info.setSearchfromDate(rs.getString("FROMDATE"));
				info.setSearchtoDate(rs.getString("TODATE"));
				info.setStatus(rs.getString("STATUS"));
				alist.add(info);
			}
			
			log.info("Arraylist is" + alist.toString() + "size is"+ alist.size());
			
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
			try{
				db.rollbackTrans();
			}catch(Exception e1){
				
			}
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return alist;
	}
	
	public void deleteFinYear(String finYearCd) {
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			StringTokenizer st = new StringTokenizer(finYearCd, ",");
			int i = 0;
			String sSQL = "";
			while (st.hasMoreTokens()) {
				sSQL = "update FinancialYear  set deleteFlag = 'Y' where FYEARCD = '"+st.nextToken()+"'";
				db.executeUpdate(sSQL);
			}
			log.info("deleted  record successfully in deleteFinYear() " + i);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public FinancialYearInfo editFinYear(String finYearcd) {
		FinancialYearInfo finYearInfo = new FinancialYearInfo();
		try {
			db.makeConnection();con = db.getConnection();
			//finYearInfo = (FinancialYearInfo)session.createQuery("from FinancialYear where finyearCd ='" + finYearcd+"'").uniqueResult();
			rs = db.getRecordSet("Select fyearcd,fyearnm,fyeardescr,to_char(fromdate,'dd/Mon/yyyy')fromdate,to_char(todate,'dd/Mon/yyyy')todate,status from FinancialYear where FYEARCD ='" + finYearcd+"'");
			if(rs.next()){
				finYearInfo.setFinyearCd(rs.getString("fyearcd"));
				finYearInfo.setFinyearName(rs.getString("fyearnm"));
				finYearInfo.setFinyearDesc(rs.getString("fyeardescr"));
				finYearInfo.setFromDate(rs.getString("fromdate"));
				finYearInfo.setToDate(rs.getString("todate"));
				finYearInfo.setStatus(rs.getString("status"));
			}
		
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try{
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
				}
			}

		}
		return finYearInfo;
	}
	
	public void updateFinYear(FinancialYearInfo fyinfo) {
		
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			log.info("inside update");
			String sSQL = "update FinancialYear  set fyearnm = '"+fyinfo.getFinyearName()+"' , fyeardescr = '"+fyinfo.getFinyearDesc()+"' , fromdate= '"+fyinfo.getFromDate()+"' , todate='"+fyinfo.getToDate()+"' , status='"+fyinfo.getStatus()+"' where fyearcd = '"+fyinfo.getFinyearCd()+"'";
			log.info(""+sSQL);
			db.executeUpdate(sSQL);
			String activityDesc="Financial Year Overview Of Financial Year "+fyinfo.getFinyearName()+" Updated.";
			ActivityLog.getInstance().write(fyinfo.getUsercd(), activityDesc, "E", String.valueOf(fyinfo.getFinyearCd()), con);
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List selectFinYearCd() {
		log.info("inside dao select");
		List list = new ArrayList();
        String Qry ="";
		try {
			
			db.makeConnection();con = db.getConnection();
			Qry = "select FYEARCD,FYEARNM from financialyear order by fromdate desc";
			log.info("qry "+Qry);
			rs = db.getRecordSet(Qry);
			while(rs.next()){
				FinancialYearInfo fyear = new FinancialYearInfo();
				fyear.setFinyearCd(rs.getString("FYEARCD"));
				fyear.setFinyearName(rs.getString("FYEARNM"));
				list.add(fyear);
			}
			//log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		}finally {
			try {
				db.closeRs();
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
				log.printStackTrace(e);
			}
		}
		return list;
	}
	
	public void saveBaseAccount(BaseAccountInfo baseaccountinfo) throws ApplicationException{
		try {	
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
			 
			String baseacccd = AutoGeneration.getNextCode("BaseAccounts","BASEACCCD",10,con);
			if(!checkBaseaccountexists(baseaccountinfo.getBaseaccnm(),db,0)){
				String sSQL = "insert into BaseAccounts (BASEACCCD,BASEACCNM,BASEACCDESC,BASEACCTYPE,STATUS) values ("+baseacccd+",'"+baseaccountinfo.getBaseaccnm()+"','"+baseaccountinfo.getBaseaccdesc()+"','"+baseaccountinfo.getBaseacctype()+"','"+baseaccountinfo.getStatus()+"')";
				log.info("SQL Query for saveBaseAccount is ======== "+sSQL);
				//session.save(baseaccountinfo);
				
				db.executeUpdate(sSQL);
				db.commitTrans();
			}else{
				throw new ApplicationException(baseaccountinfo.getBaseaccnm());
			}
		}
		catch(ApplicationException ae){
			log.error("in catch "+ae);
			throw ae;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public boolean checkBaseaccountexists(String name,DBAccess db,int id){
		boolean exists = false;
		int i = 0;
		try {
			db.makeConnection();con = db.getConnection();
			String baseaccQuery = "select baseacccd from BaseAccounts where baseaccnm='"+name.trim()+"' and deleteflag='N'";
			if(id>0){
				log.info("in edit baseacc check id"+id);
				baseaccQuery = baseaccQuery+" and baseacccd!="+id;
			}
			
			rs = db.getRecordSet(baseaccQuery);
			
			if(rs.next()){
				Integer i1 = new Integer(rs.getString(1));
				i = i1.intValue();
				log.info(" *****existing baseacc cd "+i);
				if(i>0){
					exists = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					db.closeRs();
					//db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		log.info("baseaccountname "+name+" exists : "+exists);
		return exists;
		
	}
	public List searchBaseAcc(SearchBaseAccInfo searchbaseacc){
		log.info("-----searchBaseAcc Entering Method-----");
		ArrayList baseaccList = new ArrayList();
		
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhBaseAccquery ="select ba.baseacccd,ba.baseaccnm,ba.baseaccdesc,ba.baseacctype,decode(ba.status,'I','InActive','A','Active')STATUS from baseaccounts ba where ba.baseacccd is not null ";

			System.out.println("searhFinYearquery "+searhBaseAccquery);
			log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhBaseAccquery);
			
			if (!searchbaseacc.getSearchbaseaccnm().equals("")) {
				searhBaseAccquery = searhBaseAccquery+" and upper(ba.baseaccnm) like upper('%"+searchbaseacc.getSearchbaseaccnm().trim()+"%')";
			}
			if (!searchbaseacc.getSearchbaseacctype().equals("")) {
				searhBaseAccquery = searhBaseAccquery+" and upper(ba.baseacctype) like upper('%"+searchbaseacc.getSearchbaseacctype().trim()+"%')";
			}			
			if (searchbaseacc.getStatus()!=null) {
				if(!(searchbaseacc.getStatus().equals(""))){
					searhBaseAccquery = searhBaseAccquery+" and ba.STATUS = '"+searchbaseacc.getStatus()+"'"; 
				}
			}
			rs = db.getRecordSet(searhBaseAccquery);
			while(rs.next()){
				SearchBaseAccInfo baseaccinfo = new SearchBaseAccInfo();
				baseaccinfo.setSearchbaseacccd(rs.getString("baseacccd"));
				baseaccinfo.setSearchbaseaccnm(rs.getString("baseaccnm"));
				baseaccinfo.setSearchbaseaccdesc(rs.getString("baseaccdesc"));
				baseaccinfo.setSearchbaseacctype(rs.getString("baseacctype"));
				baseaccinfo.setStatus(rs.getString("status"));
				baseaccList.add(baseaccinfo);
			}				
		
			log.info("BaseAccount search list size " + baseaccList.size());
		
			
		} catch (Exception e) {
			log.error("error in searching baseacc : " );
			log.printStackTrace(e);
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}		
		return baseaccList;
	}
	public BaseAccountInfo findByBaseAccountCD(String baseacccd){
		BaseAccountInfo baseaccinfo = new BaseAccountInfo();
		
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select * from BaseAccounts where baseacccd='"+baseacccd+"'");
			if(rs.next()){
				baseaccinfo.setBaseacccd(rs.getInt("baseacccd"));
				baseaccinfo.setBaseaccnm(rs.getString("baseaccnm"));
				baseaccinfo.setBaseaccdesc(rs.getString("baseaccdesc"));
				baseaccinfo.setBaseacctype(rs.getString("baseacctype"));
				baseaccinfo.setStatus(rs.getString("status"));
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
		return baseaccinfo;
	}
	public void editBaseAccount(BaseAccountInfo baseaccount)throws ApplicationException{
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			if(!checkBaseaccountexists(baseaccount.getBaseaccnm(),db,baseaccount.getBaseacccd())){
							
				String updateSQL = "Update baseaccounts set baseaccnm='"+baseaccount.getBaseaccnm()+"',baseaccdesc='"+baseaccount.getBaseaccdesc()+"',baseacctype='"+baseaccount.getBaseacctype()+"',status='"+baseaccount.getStatus()+"' where baseacccd='"+baseaccount.getBaseacccd()+"'";
				log.info("-----updateSQL------"+updateSQL);
				db.executeUpdate(updateSQL);
				db.commitTrans();
			}else{
				throw new ApplicationException(baseaccount.getBaseaccnm());
			}
		} catch(ApplicationException ae){
			log.error("in catch editbaseacc "+ae);
			throw ae;
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public ArrayList getBaseAccounts(){
		ArrayList arlist = new ArrayList();
		BaseAccountInfo bainfo = null;
		try{
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select * from BaseAccounts where deleteflag ='N'");
			while(rs.next()){
				db.makeConnection();con = db.getConnection();
				bainfo = new BaseAccountInfo();
				bainfo.setBaseacccd(rs.getInt("baseacccd"));
				bainfo.setBaseaccnm(rs.getString("baseaccnm"));
				bainfo.setBaseaccdesc(rs.getString("baseaccdesc"));
				bainfo.setBaseacctype(rs.getString("baseacctype"));
				bainfo.setStatus(rs.getString("status"));
				arlist.add(bainfo);
			}
			
			log.info(" base account arlist size"+arlist.size());
		}catch (Exception e) {
			log.error("error in getBaseAccounts : " );
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return arlist;
	}
	public void deleteBaseAccounts(String str){
		
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			log.info("str before delete "+str);
			StringTokenizer st = new StringTokenizer(str, ",");
			int i = 0;
			String deleteQuery = "";
			
			while (st.hasMoreTokens()) {
				deleteQuery = "update BaseAccounts  set deleteflag = 'Y' where baseacccd = "+Integer.parseInt(st.nextToken());
				log.info("-------update Query--------"+deleteQuery);
				db.executeUpdate(deleteQuery);
				log.info(" in while loop deletebaseacc() deleted baseacc " + i);
			}
			log.info("deleted   " + i + "  records successfully in deleteCadre()");
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List getAccountTypes(){
		List acclist = new ArrayList();
		AccountTypeInfo accinfo = null;
		try{
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select * from acctypes where deleteflag='N' ");
			while(rs.next()){
				accinfo = new AccountTypeInfo();
				accinfo.setAcctypecd(rs.getString("acctypecd"));
				accinfo.setAcctypenm(rs.getString("acctypenm"));
				accinfo.setAcctypedesc(rs.getString("acctypedesc"));
				accinfo.setGroupacccd(rs.getString("group_account_cd"));
				accinfo.setDeleteflag(rs.getString("deleteflag"));
				acclist.add(accinfo);
			}
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return acclist;
	}	
	public List getAccountTypes(LedgerInfo ledgerinfo){
		List acclist = new ArrayList();
		LedgerInfo accinfo = null;
		try{
			db.makeConnection();con = db.getConnection();
			String query="Select * from acctypes where deleteflag='N' and  group_account_cd="+ledgerinfo.getGroupacccd();
			log.info("=========query============"+query);
			rs = db.getRecordSet(query);
			while(rs.next()){
				accinfo = new LedgerInfo();
				accinfo.setAcctypecd(rs.getString("acctypecd"));
				accinfo.setAcctypenm(rs.getString("acctypenm"));
				acclist.add(accinfo);
			}
			log.info("======Account Type List  in DAO==========="+acclist.size());
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return acclist;
	}	
	
	public List showLedgers(Integer acccd){
		List ledgerlist = new ArrayList();
		LedgerInfo linfo = null;
		try{
			db.makeConnection();con = db.getConnection();
			//StringTokenizer st = new StringTokenizer(earncds, ",");
			log.info("Select * from LedgerMaster where acctypecd="+acccd+" and deleteflag='N'");
			rs = db.getRecordSet("Select * from LedgerMaster where acctypecd="+acccd+" and deleteflag='N'");
			while(rs.next()){
				linfo = new LedgerInfo();
				linfo.setLedgercd(rs.getString("ledgercd"));
				linfo.setLedgernm(rs.getString("ledgernm"));
				linfo.setLedgerdesc(rs.getString("ledgerdesc"));
				linfo.setOpbalance(rs.getString("opbalance"));
				linfo.setBalancetype(rs.getString("balancetype"));
				linfo.setGroupacccd(rs.getString("acctypecd"));
				linfo.setStatus(rs.getString("status"));
				ledgerlist.add(linfo);
			}
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return ledgerlist;
	}
	  public void saveAccountType(AccountTypeInfo acctype)throws ApplicationException{
	    	
			try {	
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				String cd = AutoGeneration.getNextCode("acctypes","ACCTYPECD",11,con);  
				
				String acctypeexists = checkAccountTypeexists(acctype.getAcctypenm(),cd+"",db,"new");
				log.info("acctypeexists "+acctypeexists+" "+acctypeexists.equals(""));
				
				
				if(acctypeexists.equals("")){
					log.info("before saving");
					String sSQL  = " Insert into acctypes(acctypecd,acctypenm,acctypedesc,group_account_cd,deleteflag,status) values ('"+cd+"','"+acctype.getAcctypenm()+"','"+acctype.getAcctypedesc()+"','"+acctype.getGroupacccd()+"','"+acctype.getDeleteflag()+"','"+acctype.getStatus()+"')";
				     
					log.info("======Insert Query======="+sSQL);
					db.executeUpdate(sSQL);
					
					db.commitTrans();
				}else{
					log.info("throwing application excep");
					throw new ApplicationException(acctypeexists);
				}
			}
			catch(ApplicationException ae){
				log.error("in catch appexcp saveAccountType "+ae);
				throw ae;
			}
			catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	    public String checkAccountTypeexists (String name,String acctypecd,DBAccess db,String type){
             String acctypeexists = "";
	    	
			String checkname = "select acctypecd from accTypes where acctypenm = '"+name.trim()+"' and deleteflag='N'";
			log.info("------checkname---------"+checkname);
			if(type.equals("edit"))
				checkname = checkname+" and acctypecd != "+acctypecd;
			String checkcd = "select acctypecd from acctypes where acctypecd="+acctypecd+" and deleteflag='N'";
	    	try{
	    		
	    		rs = db.getRecordSet(checkname);
	    		
	    		if(rs.next()){
	    			acctypeexists ="acctype.nm"+",";
	    		}
	    		db.closeRs();
	    		
	    		if(type.equals("new")){
		    		rs = db.getRecordSet(checkcd);
		    		if(rs.next()){
		    		    acctypeexists = acctypeexists+"acctype.cd"+",";
		    		}
	    		}
	    	}catch (Exception e) {
	    		log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
	    		e.printStackTrace();
	    	}  
	    	return acctypeexists;
	    }
	    
		public List searchAccType(SearchAccountTypeInfo searchacctype){
			ArrayList accList = new ArrayList();
			String accSearch = "";
			int st = 0;
			int end = 0;
			int showrec = 10;

			try {
				db.makeConnection();con = db.getConnection();
				
				accSearch = "select rownum as limit,acc.acctypecd as acctypecd,acc.acctypenm as acctypenm,acc.acctypedesc as acctypedesc,acc.group_account_cd as groupacccd,acc.deleteflag deleteflag,gac.group_account_name as groupaccname from acctypes acc,groupaccount gac where acc.deleteflag='N' and acc.group_account_cd=gac.group_account_cd";
				System.out.println("searh Account Type query "+accSearch);
				System.out.println("------searchacctype.getSearchgroupacccd()-----"+searchacctype.getSearchacctypecd());
				
				if (!searchacctype.getSearchacctypecd().equals("")) {					
					accSearch=accSearch+" and upper(acc.acctypecd) like upper('%"	+ searchacctype.getSearchacctypecd().trim() + "%')";					

				}
				
				if (!searchacctype.getSearchacctypenm().equals("")) {					
					accSearch=accSearch+" and upper(acc.acctypenm) like upper('%"	+ searchacctype.getSearchacctypenm().trim() + "%')";					

				}
				if (!searchacctype.getSearchgroupaccnm().equals("")) {
					accSearch=accSearch+" and upper(gac.group_account_name) like upper('%"+ searchacctype.getSearchgroupaccnm().trim() + "%')";
					
				}		
				accSearch = accSearch+" order by acc.acctypecd";
				log.info("<<<<<<<<<<<<<<<<< searchGroupAcc query>>>>>>>>>>>"+accSearch);
				rs = db.getRecordSet(accSearch);
				
				while(rs.next()){
					SearchAccountTypeInfo accinfo = new SearchAccountTypeInfo();
					accinfo.setSearchacctypecd(rs.getString("acctypecd"));
					accinfo.setSearchacctypenm(rs.getString("acctypenm"));
					accinfo.setSearchacctypedesc(rs.getString("acctypedesc"));
					accinfo.setSearchgroupacccd(rs.getString("groupacccd"));
					accinfo.setSearchgroupaccnm(rs.getString("groupaccname"));
				
					accList.add(accinfo);
					
				}			
			
				log.info("BaseAccount search list size " + accList.size());
				
				
			} catch (Exception e) {
				log.error("error in searchAccType : " );
				log.printStackTrace(e);
			}  finally {
				try {
					db.closeRs();
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
			log.info("before returning pageno"+searchacctype.getPageNo());
			log.info("before returning searchfield"+searchacctype.getSortfield());
			return accList;
			
		}
		public AccountTypeInfo findByAccountTypeCD(String accounttypecd){
			AccountTypeInfo acctypeinfo = new AccountTypeInfo();
			
			try {
				db.makeConnection();con = db.getConnection();		
				log.info("in findbybaseacccd " + acctypeinfo.getAcctypenm());
				
				System.out.println("=============Select * from acctypes where acctypecd='"+accounttypecd+"'");
				
				rs=db.getRecordSet("Select * from acctypes where acctypecd='"+accounttypecd+"'");
				if(rs.next()){
					acctypeinfo.setAcctypecd(rs.getString("acctypecd"));
					acctypeinfo.setAcctypenm(rs.getString("acctypenm"));
					acctypeinfo.setAcctypedesc(rs.getString("acctypedesc"));
					acctypeinfo.setGroupacccd(rs.getString("group_account_cd"));
					acctypeinfo.setDeleteflag(rs.getString("deleteflag"));
				}
				
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
					}
				}
			}
			return acctypeinfo;
		}
		public void editAccountType(AccountTypeInfo acctype)throws ApplicationException{
			

			try {
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				String acctypeexists = checkAccountTypeexists(acctype.getAcctypenm(),acctype.getAcctypecd()+"",db,"edit");
				log.info("acctypeexists "+acctypeexists+" "+acctypeexists.equals(""));
				if(acctypeexists.equals("")){
					acctype.setDeleteflag("N");
					db.executeUpdate("Update acctypes set acctypenm='"+acctype.getAcctypenm()+"', acctypedesc='"+acctype.getAcctypedesc()+"',group_account_cd='"+acctype.getGroupacccd()+"',DELETEFLAG='"+acctype.getDeleteflag()+"' where acctypecd='"+acctype.getAcctypecd()+"'");
					db.commitTrans();
				}else{
					throw new ApplicationException(acctypeexists);
				}
			} catch(ApplicationException ae){
				log.error("in catch editbaseacc "+ae);
				throw ae;
			}
			catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} 
			finally {
				try {
					
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		public void deleteAccountType(String str){
			try {
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				log.info("str before delete "+str);
				if(str.endsWith(","))
					str=str.substring(0,str.length()-1);
				log.info("after removal of comma "+str);
				StringTokenizer st = new StringTokenizer(str, ",");
				int i = 0;
				String deleteQuery = "";
				
				while (st.hasMoreTokens()) {
					deleteQuery = "update acctypes  set deleteflag = 'Y' where acctypecd  in ('"+Integer.parseInt(st.nextToken())+"')";
					i = db.executeUpdate(deleteQuery);
				}
				log.info("deleted   " + i
						+ "  records successfully in deleteCadre()");
				db.commitTrans();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			}
			finally {
				try {
					
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		
		public ArrayList getGroupAccounts(){
			System.out.println("------getBaseAccounts()--------");
			ArrayList arlist = new ArrayList();
			GroupAccountInfo gainfo = null;
			try{
				db.makeConnection();con = db.getConnection();
				rs = db.getRecordSet("Select * from groupaccount where status ='A'");
				while(rs.next()){
					db.makeConnection();con = db.getConnection();
					gainfo = new GroupAccountInfo();
					gainfo.setGroupacccd(rs.getString("group_account_cd"));
					gainfo.setGroupaccname(rs.getString("group_account_name"));
					
					arlist.add(gainfo);
				}
				
				log.info(" base account arlist size"+arlist.size());
			}catch (Exception e) {
				log.error("error in getGroupAccounts : " );
				log.printStackTrace(e);
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
					}
				}
			}
			return arlist;
		}
		
		public List getStations(){
			List stationlist = new ArrayList();
			LedgerInfo stationinfo = null;
			try{
				db.makeConnection();con = db.getConnection();
				rs = db.getRecordSet("Select STATIONCD,STATIONNAME from STATIONMASTER where STATUS='A' order by STATIONNAME");
				while(rs.next()){
					stationinfo = new LedgerInfo();
					stationinfo.setStationCd(rs.getString("STATIONCD"));
					stationinfo.setStationName(rs.getString("STATIONNAME"));				
					stationlist.add(stationinfo);
				}
				
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
					}
				}
			}
			return stationlist;
		}
		public List getAllLedgers(String status){//accepted status values:Y/N or empty
			List ledgersList  = new ArrayList();
			try{
				db.makeConnection();
				con = db.getConnection();
				String getledgersqry = "select ledgercd,InitCap(ledgernm) ledgernm from ledgermaster ";
				if(status != ""){
					getledgersqry+="where deleteflag = '"+status+"'";
				}
				log.info("getledgersqry "+getledgersqry);
				LedgerInfo ldgrinfo = null;
				rs = db.getRecordSet(getledgersqry);				
				while(rs.next()){
					ldgrinfo = new LedgerInfo();
					ldgrinfo.setLedgercd(rs.getString("ledgercd"));
					ldgrinfo.setLedgernm(rs.getString("ledgernm"));
					ledgersList.add(ldgrinfo);
				}
			}catch(Exception e){
				log.info("Exception in getAllLedgers ()");
				log.printStackTrace(e);
			}
			return ledgersList;
		}
		
		public void saveLedger(LedgerInfo ledgerinfo)throws ApplicationException{
			try{
				 int ledgerno=0;
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				 rs=db.getRecordSet("select max(ledgerno)ledgerno from ledgermaster");
				 if(rs.next())
				 ledgerno =rs.getInt("ledgerno");
				 
				String sSQL = " Insert into ledgermaster (ledgercd,ledgernm,ledgerdesc,opbalance,balancetype,group_account_cd,acctypecd,status,AIRPORTCDODES,LEDGERNO) values ('"+ledgerinfo.getLedgercd()+"','"+ledgerinfo.getLedgernm()+"','"+ledgerinfo.getLedgerdesc()+"','"+ledgerinfo.getOpbalance()+"','"+ledgerinfo.getBalancetype()+"',"+ledgerinfo.getGroupacccd()+","+ledgerinfo.getAcctypecd()+",'"+ledgerinfo.getStatus()+"','"+ledgerinfo.getStationCd()+"',"+(ledgerno+1)+")";
				log.info("saveLedger "+sSQL);
				db.executeUpdate(sSQL);
				String activityDesc="Ledger Master Of "+ledgerinfo.getLedgernm()+" Saved.";
				ActivityLog.getInstance().write(ledgerinfo.getUsercd(), activityDesc, "N", String.valueOf(ledgerinfo.getLedgercd()), con);
				db.commitTrans();
			}catch (Exception e) {
				log.error("error in saveLedger : " );
				log.printStackTrace(e);
			}
			finally {
				try {
					
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		public List searchLedger(SearchLedgerInfo searchledger){
			ArrayList ledgerList = new ArrayList();
		
			try {
				db.makeConnection();con = db.getConnection();
				
				String searhFinYearquery ="select lm.ledgercd ,lm.ledgernm ,lm.ledgerdesc ,lm.opbalance ,decode(lm.status,'I','InActive','A','Active')STATUS from ledgermaster lm where lm.ledgercd is not null"; 
				System.out.println("searhFinYearquery "+searhFinYearquery);
				log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
				
				if (!searchledger.getSearchledgernm().equals("")) {
					searhFinYearquery = searhFinYearquery+" and upper(lm.ledgernm) like upper('%"+searchledger.getSearchledgernm().trim()+"%')";
				}
				
				if (!searchledger.getSearchledgercd().equals("")) {				
					searhFinYearquery = searhFinYearquery+" and upper(lm.ledgercd) like upper('%"+ searchledger.getSearchledgercd().trim()+"%')";
				}					
				
				//if (searchledger.getStatus()!=null) {
					if(!(searchledger.getStatus().equals(""))){
						searhFinYearquery = searhFinYearquery+" and lm.STATUS = '"+searchledger.getStatus()+"'"; 
					}
				//}
				searhFinYearquery = searhFinYearquery+" order by lm.ledgercd  ";					
				log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
				rs = db.getRecordSet(searhFinYearquery);
				
				while(rs.next()){
					SearchLedgerInfo ledgerinfo = new SearchLedgerInfo();
					ledgerinfo.setSearchledgercd(rs.getString("LEDGERCD"));
					ledgerinfo.setSearchledgernm(rs.getString("LEDGERNM"));
					ledgerinfo.setSearchledgerdesc(rs.getString("LEDGERDESC"));
					ledgerinfo.setSearchopbalance(rs.getString("OPBALANCE"));
					//ledgerinfo.setSearchgroupaccnm(rs.getString("GROUP_ACCOUNT_NAME"));
				    ledgerinfo.setStatus(rs.getString("STATUS"));
					ledgerList.add(ledgerinfo);
				}		
				
			} catch (Exception e) {
				log.error("error in searching Ledger : " + e);
			} finally {
				try {
					 db.closeRs();
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}			
			return ledgerList;
		}
		public LedgerInfo findByLedgerCD(String ledgercd){
			LedgerInfo ledgerinfo = new LedgerInfo();
			log.info("in find by ledgercd");
			try {
				db.makeConnection();con = db.getConnection();
				String query = "Select * from LedgerMaster  where ledgercd = '"+ledgercd+"'";
				rs = db.getRecordSet(query);
				while(rs.next()){
					ledgerinfo.setLedgercd(rs.getString("ledgercd"));
					ledgerinfo.setLedgernm(rs.getString("ledgernm"));
					ledgerinfo.setLedgerdesc(rs.getString("ledgerdesc"));
					ledgerinfo.setOpbalance(rs.getString("opbalance"));
					ledgerinfo.setBalancetype(rs.getString("balancetype"));
					ledgerinfo.setGroupacccd(rs.getString("group_account_cd"));
					ledgerinfo.setStatus(rs.getString("status"));
					ledgerinfo.setAcctypecd(rs.getString("acctypecd"));
				}
						
				log.info("in findbyledgercd " + ledgerinfo.getLedgernm());
				
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
				e.printStackTrace();
			}finally {
				try {
					 db.closeRs();
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
			return ledgerinfo;
		}
		public void editLedger(LedgerInfo ledgerinfo)throws ApplicationException{
			try{
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				//ledgerinfo.setDeleteflag("N");
				String sSQL = " Update LedgerMaster set ledgernm='"+ledgerinfo.getLedgernm()+"',ledgerdesc='"+ledgerinfo.getLedgerdesc()+"',opbalance='"+ledgerinfo.getOpbalance()+"',balancetype='"+ledgerinfo.getBalancetype()+"',group_account_cd="+ledgerinfo.getGroupacccd()+",acctypecd="+ledgerinfo.getAcctypecd()+",status='"+ledgerinfo.getStatus()+"' where ledgercd='"+ledgerinfo.getLedgercd()+"'";
				log.info("----update query------"+sSQL);
				db.executeUpdate(sSQL);
				String activityDesc="Ledger Overview Of "+ledgerinfo.getLedgernm()+" Updated.";
				ActivityLog.getInstance().write(ledgerinfo.getUsercd(), activityDesc, "E", String.valueOf(ledgerinfo.getLedgercd()), con);
				db.commitTrans();
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
				e.printStackTrace();
			} 
			finally {
				try {
					 
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		public void deleteLedger(String str){
			try {
				
				db.makeConnection();con = db.getConnection();			
				db.setAutoCommit(false);				
				if(str.endsWith(","))
					str=str.substring(0,str.length()-1);
				
				
				String deleteQuery = "update LedgerMaster  set deleteflag = 'Y'  where ledgercd  in ("+str+")";
				log.info("deleteQuery "+deleteQuery);
				int i= db.executeUpdate(deleteQuery);
				
				log.info("deleted   " + i + "  records successfully in deleteledger()");
				db.commitTrans();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
				e.printStackTrace();
			} 
			finally {
				try {
					 
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		public List loadLedger(SubLedgerInfo subledginfo){
			List ledgerlist = new ArrayList();
			SubLedgerInfo subledinfo = null;
			try{
				db.makeConnection();con = db.getConnection();
				String query="Select LEDGERCD,LEDGERNM from ledgermaster where deleteflag='N' and  ACCTYPECD="+Integer.parseInt(subledginfo.getAccTypeCd());
				log.info("=========query============"+query);
				rs = db.getRecordSet(query);
				while(rs.next()){
					subledinfo = new SubLedgerInfo();
					subledinfo.setLedgercd(rs.getString("LEDGERCD"));
					subledinfo.setLedgername(rs.getString("LEDGERNM"));
					ledgerlist.add(subledinfo);
				}
				log.info("=====Ledger List  in DAO==========="+ledgerlist.size());
				
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
					}
				}
			}
			return ledgerlist;
		}	
		
		
		public void saveSubLedger(SubLedgerInfo subledgerinfo)throws ApplicationException{
			try{
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				log.info("in savesubledger");
				String sSQL = " Insert into subledgermaster (subledgercd,subledgernm,subledgerdesc,opbalance,balancetype,ledgercd,status ) values ('"+subledgerinfo.getSubledgercd()+"','"+subledgerinfo.getSubledgernm()+"','"+subledgerinfo.getSubledgerdesc()+"','"+subledgerinfo.getOpbalance()+"','"+subledgerinfo.getBalancetype()+"','"+subledgerinfo.getLedgercd()+"','"+subledgerinfo.getStatus()+"')";
				log.info("savesubledger insert query-------"+sSQL);
				db.executeUpdate(sSQL);
				db.commitTrans();
			}
			catch(Exception e){
				log.info("in dao in saving the subledger method "+e);
			}
			finally {
				try {
					 
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		public List searchSubLedger(SearchSubLedgerInfo searchsubledger){
			ArrayList subledgerList = new ArrayList();			
			try {
				db.makeConnection();con = db.getConnection();

				String searhFinYearquery ="select subl.subledgercd ,subl.subledgernm ,subl.subledgerdesc ,subl.opbalance ,subl.ledgercd,decode(subl.status,'I','InActive','A','Active')STATUS,lm.ledgernm from subledgermaster subl,ledgermaster lm where subl.ledgercd=lm.ledgercd and subl.subledgercd is not null";
				log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
							
						
				if (!searchsubledger.getSearchsubledgercd().equals("")) {
				      searhFinYearquery = searhFinYearquery+" and upper(subl.subledgercd) like upper('%"+searchsubledger.getSearchledgercd().trim()+"%')";
				}
				if (!searchsubledger.getSearchsubledgernm().equals("")) {
				      searhFinYearquery = searhFinYearquery+" and upper(subl.subledgernm) like upper('%"+ searchsubledger.getSearchsubledgernm().trim()+"%')";
				}		
				if (searchsubledger.getStatus()!=null) {
					if(!(searchsubledger.getStatus().equals(""))){
						searhFinYearquery = searhFinYearquery+" and subl.STATUS = '"+searchsubledger.getStatus()+"'"; 
					}
				}
							
				log.info("<<<<<<<<<<<<<<<<< searh FinYear query>>>>>>>>>>>"+searhFinYearquery);
				rs = db.getRecordSet(searhFinYearquery);

				while (rs.next()) {
					SearchSubLedgerInfo subledgerinfo = new SearchSubLedgerInfo();
					subledgerinfo.setSearchsubledgercd(rs.getString("subledgercd"));
					subledgerinfo.setSearchsubledgernm(rs.getString("subledgernm"));
					subledgerinfo.setSearchsubledgerdesc(rs.getString("subledgerdesc"));
					subledgerinfo.setSearchopbalance(rs.getString("opbalance"));
					subledgerinfo.setSearchledgercd(rs.getString("ledgercd"));
					subledgerinfo.setSearchledgernm(rs.getString("ledgernm"));
					subledgerinfo.setStatus(rs.getString("status"));
					subledgerList.add(subledgerinfo);
				}							
				
			} catch (Exception e) {
				log.error("error in searching Sub Ledger : " + e);
			} finally {
				try {
					 db.closeRs();
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}	
			
			return subledgerList;
		}
		public SubLedgerInfo findBySubLedgerCD(String subledgercd){
           SubLedgerInfo subledginfo = new SubLedgerInfo();
			
			log.info("in find by sub ledgercd");
			try {db.makeConnection();con = db.getConnection();
				String query = "Select * from SubLedgerMaster  where subledgercd = '"+subledgercd+"'";
				log.info("---------Query----------"+query);
				rs = db.getRecordSet(query);
				if(rs.next()){
					subledginfo.setSubledgercd(rs.getString("subledgercd"));
					subledginfo.setSubledgernm(rs.getString("subledgernm"));
					subledginfo.setSubledgerdesc(rs.getString("subledgerdesc"));
					subledginfo.setBalancetype(rs.getString("Balancetype"));
					subledginfo.setOpbalance(rs.getDouble("opbalance"));
					subledginfo.setLedgercd(rs.getString("ledgercd"));
					subledginfo.setStatus(rs.getString("status"));
				}					
				log.info("in findbyledgercd " + subledginfo.getSubledgernm());				
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"	+ e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					 db.closeRs();
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
			
			return subledginfo;
		}
		public void editSubLedger(SubLedgerInfo subledger)throws ApplicationException{
			try{db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
			String sSQL = " Update subledgermaster set subledgernm='"+subledger.getSubledgernm()+"',subledgerdesc='"+subledger.getSubledgerdesc()+"',opbalance='"+subledger.getOpbalance()+"',balancetype='"+subledger.getBalancetype()+"',ledgercd ='"+subledger.getLedgercd()+"',status ='"+subledger.getStatus()+"' where subledgercd='"+subledger.getSubledgercd()+"'";
			
			db.executeUpdate(sSQL);
			db.commitTrans();
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"	+ e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
									+ e.getMessage());
				}
			}
		}
		public void deleteSubLedger(String str){
			try {
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				log.info("str before delete "+str);
				if(str.endsWith(","))
					str=str.substring(0,str.length()-1);
				log.info("after removal of comma indelete subledger"+str);
				
				StringTokenizer st = new StringTokenizer(str, ",");
				int i = 0;
				String deleteQuery = "";
				
				while (st.hasMoreTokens()) {
					deleteQuery = "update SubLedgerMaster  set deleteflag = 'Y'  where subledgercd  in('"+st.nextToken()+"')";
					log.info("------deleteQuery------------"+deleteQuery);
					i = db.executeUpdate(deleteQuery);
				}
				
				log.info("deleted   " + i + "  records successfully in deleteledger()");
				db.commitTrans();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
				e.printStackTrace();
			}
			finally {
				try {
					
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
		}
		
		public List loadBanks(){
			List banklist = null;
			BankMasterInfo bminfo = null;
			try{
				db.makeConnection();con = db.getConnection();
				String sSQL = "Select * from BankMaster";
				rs = db.getRecordSet(sSQL);
				while(rs.next()){
					bminfo = new BankMasterInfo();
					bminfo.setBankid(rs.getString("bankid"));
					bminfo.setBankname(rs.getString("banknm"));
					bminfo.setBranchname(rs.getString("branchnm"));
					/*bminfo.setBankMICRno(rs.getString(""));
					bminfo.setBankIFScno(rs.getString(""));
					bminfo.setBankECRno(rs.getString(""));
					bminfo.setAddress(rs.getString(""));
					bminfo.setCity(rs.getString(""));
					bminfo.setState(rs.getString(""));
					bminfo.setZipCd(rs.getString(""));*/
					
				}
			
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				try {
					
				     db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
										+ e.getMessage());
					}
				}
			return banklist;
		}
		public List searchGroupAcc(GroupAccountInfo searchgroupacc){

			List groupaccList = new ArrayList();
			try{
				db.makeConnection();
				con = db.getConnection();
				
				String searhGrouprquery = "select rownum as limit, ga.GROUP_ACCOUNT_CD as GROUP_ACCOUNT_CD,ga.GROUP_ACCOUNT_NAME as GROUP_ACCOUNT_NAME,ga.BASEACCCD as BASEACCCD,ga.STATUS as STATUS,ba.BASEACCNM  as BASEACCNM from GROUPACCOUNT ga,BASEACCOUNTS ba where ba.BASEACCCD=ga.BASEACCCD and ga.STATUS='A'";
								
				if (!searchgroupacc.getGroupacccd().equals("")) {
					searhGrouprquery = searhGrouprquery+" and upper(ga.GROUP_ACCOUNT_CD) like upper('%"+ searchgroupacc.getGroupacccd().trim() + "%')";
				}
				if (!searchgroupacc.getGroupaccname().equals("")) {
					searhGrouprquery = searhGrouprquery+" and upper(ga.GROUP_ACCOUNT_NAME) like upper('%"+ searchgroupacc.getGroupaccname().trim() + "%')";
				}
				log.info("<<<<<<<<<<<<<<<<< searchGroupAcc query>>>>>>>>>>>"+searhGrouprquery);
				rs = db.getRecordSet(searhGrouprquery);
				GroupAccountInfo groupaccinfo; 
				while(rs.next()){
					groupaccinfo= new GroupAccountInfo();
					groupaccinfo.setGroupacccd(rs.getString("GROUP_ACCOUNT_CD"));
					groupaccinfo.setGroupaccname(rs.getString("GROUP_ACCOUNT_NAME"));
					groupaccinfo.setBaseacccd(rs.getString("BASEACCCD"));
					groupaccinfo.setBaseacctype(rs.getString("BASEACCNM"));
					groupaccinfo.setStatus(rs.getString("STATUS"));					
					groupaccList.add(groupaccinfo);				
				}         			
			}catch(Exception e){
				log.info("<<<<<<<<<< searchRoaster Exception  >>>>>>>>>>>>"+ e.getMessage());
			}finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
			return groupaccList;					
		}
		
		public String checkGroupaccountexists(String acccd,String accname,DBAccess db,int id){
					
			String acctypeexists = "";
			try {
				db.makeConnection();con = db.getConnection();
				String groupaccQuery = "select GROUP_ACCOUNT_CD from  GROUPACCOUNT where GROUP_ACCOUNT_CD='"+acccd+"' and STATUS='A'";
				rs= db.getRecordSet(groupaccQuery);		
				
				if(rs.next()){
	    			acctypeexists ="groupacc.cd"+",";
	    		}
				
			} catch (Exception e) {
				e.printStackTrace();
			}		
		    return acctypeexists;			
		}		
		public void saveGroupAccount(GroupAccountInfo groupaccountinfo) throws ApplicationException{
			try {	
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
						
				String acctypeexists=checkGroupaccountexists(groupaccountinfo.getGroupacccd(),groupaccountinfo.getGroupaccname(),db,0);
				if(acctypeexists.equals("")){
					String sSQL = "insert into GROUPACCOUNT (GROUP_ACCOUNT_CD,GROUP_ACCOUNT_NAME,BASEACCCD,STATUS,USERCD) values ('"
					+groupaccountinfo.getGroupacccd()+"','"+groupaccountinfo.getGroupaccname()+"','"
					+groupaccountinfo.getBaseacctype()+
					"','"+groupaccountinfo.getStatus()+
					"','"+groupaccountinfo.getUserid()+"')";
					log.info("SQL Query for saveGroupAccount is ======== "+sSQL);
					//session.save(baseaccountinfo);
					
					db.executeUpdate(sSQL);
					db.commitTrans();
				}else{
					throw new ApplicationException(acctypeexists);
				}
			}
			catch(ApplicationException ae){
				log.error("in catch "+ae);
				throw ae;
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
		}		
		public GroupAccountInfo findByGroupAccountCD(String groupacccd){
			GroupAccountInfo groupaccinfo = new GroupAccountInfo();
			
			try {
				db.makeConnection();con = db.getConnection();
				rs = db.getRecordSet("Select * from GROUPACCOUNT where GROUP_ACCOUNT_CD='"+groupacccd+"'");
				if(rs.next()){
					groupaccinfo.setGroupacccd(rs.getString("GROUP_ACCOUNT_CD"));
					groupaccinfo.setGroupaccname(rs.getString("GROUP_ACCOUNT_NAME"));
					groupaccinfo.setBaseacctype(rs.getString("BASEACCCD"));
					groupaccinfo.setStatus(rs.getString("STATUS"));					
				}
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
					}
				}
			}			
			return groupaccinfo;
		}		
		public void editGroupAccount(GroupAccountInfo groupaccount)throws ApplicationException{
			try {
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
						
				String updateSQL = "Update GROUPACCOUNT set GROUP_ACCOUNT_NAME='"+groupaccount.getGroupaccname()+
				"',BASEACCCD="+groupaccount.getBaseacctype()+
				",STATUS='"+groupaccount.getStatus()+					
				"' where GROUP_ACCOUNT_CD="+groupaccount.getGroupacccd()+"";
				System.out.println("--**************---updateSQL------"+updateSQL);
				db.executeUpdate(updateSQL);
				db.commitTrans();
				
			} catch(ApplicationException ae){
				log.error("in catch editbaseacc "+ae);
				throw ae;
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		public void deleteGroupAccounts(String str){
			
			try {
				db.makeConnection();con = db.getConnection();
				db.setAutoCommit(false);
				log.info("str before delete "+str);
				StringTokenizer st = new StringTokenizer(str, ",");
				int i = 0;
				String deleteQuery = "";
				
				while (st.hasMoreTokens()) {
					deleteQuery = "update GROUPACCOUNT  set status = 'I' where GROUP_ACCOUNT_CD = "+Integer.parseInt(st.nextToken());
					log.info("-------deleteQuery--------"+deleteQuery);
					db.executeUpdate(deleteQuery);
					log.info(" in while loop deletegroupacc() deleted groupacc " + i);
				}
				log.info("deleted   " + i + "  records successfully in deleteCadre()");
				db.commitTrans();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		public List getBaseAccountCodes(){
			List acclist = new ArrayList();
			GroupAccountInfo accinfo = null;
			try{
				db.makeConnection();con = db.getConnection();
				rs = db.getRecordSet("Select BASEACCCD,BASEACCNM from BASEACCOUNTS where deleteflag='N' ");
				while(rs.next()){
					accinfo = new GroupAccountInfo();
					accinfo.setBaseacccd(rs.getString("BASEACCCD"));	
					accinfo.setBaseacctype(rs.getString("BASEACCNM"));
					acclist.add(accinfo);
				}
				
			}catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			} finally {
				if (rs != null) {
					try {
						log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
						db.closeRs();
						db.closeCon();
					} catch (Exception e) {
						log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
					}
				}
			}
			return acclist;
		}









public List searchBank(BankMasterInfo searchbankInfo){
	List bankList = new ArrayList();
	try{
		db.makeConnection();
		con = db.getConnection();
		String searhBankquery = "select BANKID,BANKNM,BRANCHNM,BANKIFSCNO,BANKMICRNO,BANKECRNO,BANKRTGSNO,DECODE(Status,'A','Active','N','InActive') Status from bankmaster where BANKID is not null ";
		System.out.println("searhBankquery "+searhBankquery);
		if(!searchbankInfo.getBankname().trim().equals("")){
			searhBankquery = searhBankquery+" and Upper(BANKNM) like Upper ('%"+ searchbankInfo.getBankname().trim() + "%')";
		}
		if(!searchbankInfo.getBranchname().equals("")){
			searhBankquery = searhBankquery+" and Upper(BRANCHNM) like Upper ('%"+ searchbankInfo.getBranchname().trim()+"%')"; 
		}
		if(!searchbankInfo.getMicrno().equals("")){
			searhBankquery = searhBankquery+" and Upper(BANKMICRNO) like Upper ('%"+ searchbankInfo.getMicrno().trim()+"%')"; 
		}
		if(!searchbankInfo.getRtgsno().equals("")){
			searhBankquery = searhBankquery+" and Upper(BANKRTGSNO) like Upper ('%"+ searchbankInfo.getRtgsno().trim()+"%')"; 
		}
		searhBankquery = searhBankquery+" order by  BANKNM ";
		log.info("<<<<<<<<<<<<<<<<< SearchBank query>>>>>>>>>>>"+searhBankquery);
		rs = db.getRecordSet(searhBankquery);
		RegionInfo regionInfo;
		while(rs.next()){
			BankMasterInfo bankinfo =  new BankMasterInfo();
			bankinfo.setBankid(rs.getString("BANKID"));
			bankinfo.setBankname(rs.getString("BANKNM"));				
			bankinfo.setBranchname(rs.getString("BRANCHNM"));
			bankinfo.setIfscno(rs.getString("BANKIFSCNO"));
			bankinfo.setMicrno(rs.getString("BANKMICRNO"));
			bankinfo.setEcrno(rs.getString("BANKECRNO"));
			bankinfo.setRtgsno(rs.getString("BANKRTGSNO"));
			bankinfo.setStatus(rs.getString("Status"));
			bankList.add(bankinfo);
		}
      System.out.println("bankList "+bankList);			
	}catch(Exception e){
		log.info("<<<<<<<<<< searchBank Exception  >>>>>>>>>>>>"+ e.getMessage());
	}finally {
		try {
			db.closeCon();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	return bankList;		
}
/**
 * @return
 */
public List getLedgers() {
	
	List ledgerlist = new ArrayList();
	LedgerInfo linfo = null;
	try{
		db.makeConnection();con = db.getConnection();
		String ledgerqry = "Select * from LedgerMaster where  deleteflag='N'";
		log.info("ledgerqry "+ledgerqry);
		rs = db.getRecordSet(ledgerqry);
		while(rs.next()){
			linfo = new LedgerInfo();
			linfo.setLedgercd(rs.getString("ledgercd"));
			linfo.setLedgernm(rs.getString("ledgernm"));
			ledgerlist.add(linfo);
		}
		
	}catch (Exception e) {
		log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
		log.printStackTrace(e);
	} finally {
		if (rs != null) {
			try {
				log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}
	return ledgerlist;
}
	public List getAllBankswithBranch(){
		List bankList = new ArrayList();
		String searhBankquery = "select BANKID,BANKNM,BRANCHNM from bankmaster where status='A' ";
		log.info("searhBankquery "+searhBankquery);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(searhBankquery);
			while(rs.next()){
				BankMasterInfo bankinfo =  new BankMasterInfo();
				bankinfo.setBankid(rs.getString("BANKID"));
				bankinfo.setBankname(rs.getString("BANKNM")+"-"+rs.getString("BRANCHNM"));
				bankList.add(bankinfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getAllBankswithBranch Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
			
		return bankList;
	}
	public List getFinancialYears(String status){
		List finyearList = new ArrayList();
		
		String finqry = "select FYEARCD,FYEARNM,to_char(FROMDATE,'dd/Mon/yyyy')FROMDATE,to_char(TODATE,'dd/Mon/yyyy')TODATE from FINANCIALYEAR where FYEARCD is not null";
		if(!status.trim().equals("")){
			finqry = finqry+" and STATUS = '"+status+"'";
		}
		finqry = finqry+" order by status ";
		log.info("finqry "+finqry);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(finqry);
			while(rs.next()){
				FinancialYearInfo fyinfo = new FinancialYearInfo();
				fyinfo.setFinyearCd(rs.getString("FYEARCD"));
				fyinfo.setFinyearName(rs.getString("FROMDATE")+" - "+rs.getString("TODATE"));
				finyearList.add(fyinfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getFinancialYears Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return finyearList;
		
	}
	public List getSalHeads() {
		List earnList = new ArrayList();
		
		String earnqry = "select * from earndedumaster where type='E'";
		
		log.info("earnqry "+earnqry);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(earnqry);
			while(rs.next()){
				EarningMasterInfo einfo = new EarningMasterInfo();
				einfo.setEarningCd(new Integer(rs.getString("EARNDEDUCD")));
				einfo.setEarningName(rs.getString("EARNDEDUNM"));
				earnList.add(einfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getEarnings Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return earnList;
	}
	public List searchHeadWiseArrears(ArrearsInfo info) {
			List arrList = new ArrayList();
		
		String arrqry = "select HARREARSCD,STAFFCTGRYNM,EARNDEDUNM,PAYROLLMONTHNM,ha.status from headwise_arrears ha,staffcategory cad,earndedumaster earn,monthlypayroll mp where ha.earndeducd=earn.earndeducd and ha.payrollmonthid=mp.payrollmonthid and ha.cadrecd=cad.staffctgrycd";
		if(!info.getCadreCd().equals(""))
			arrqry += " and ha.CADRECD='"+info.getCadreCd()+"'";
		if(!info.getEarnCd().equals(""))
			arrqry += " and ha.EARNDEDUCD='"+info.getEarnCd()+"'";
		arrqry +=" order by HARREARSCD";
		log.info("arrqry "+arrqry);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(arrqry);
			while(rs.next()){
				ArrearsInfo einfo = new ArrearsInfo();
				einfo.setHarrearscd(rs.getString("HARREARSCD"));
				einfo.setCadreCd(rs.getString("STAFFCTGRYNM"));
				einfo.setEarnCd(rs.getString("EARNDEDUNM"));
				einfo.setPayrollmonthnm(rs.getString("PAYROLLMONTHNM"));
				einfo.setStatus(rs.getString("status"));
				arrList.add(einfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getEarnings Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return arrList;
	}
	public ArrearsInfo showEditHeadWiseArrears(String harrearscd) {
		
		String arrqry = "select HARREARSCD,CADRECD,FYEARCD,PAYROLLMONTHID,EARNDEDUCD,AMOUNT,PERCENT,FLAG, STATUS from headwise_arrears  where HARREARSCD ='"+harrearscd+"'";
		log.info("arrqry "+arrqry);
		ArrearsInfo einfo =null;
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(arrqry);
			while(rs.next()){
				 einfo = new ArrearsInfo();
				einfo.setHarrearscd(rs.getString("HARREARSCD"));
				einfo.setCadreCd(rs.getString("CADRECD"));
				einfo.setEarnCd(rs.getString("EARNDEDUCD"));
				einfo.setFyearcd(rs.getString("FYEARCD"));
				einfo.setFlag(rs.getString("FLAG"));
				einfo.setPayrollmonthid(rs.getString("PAYROLLMONTHID"));
				einfo.setPercent(rs.getString("PERCENT"));
				einfo.setAmount(rs.getString("amount"));
				einfo.setStatus(rs.getString("status"));
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getEarnings Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return einfo;
	}
	public List selectCategory() {
		List cadList = new ArrayList();
		
		String earnqry = "select * from staffcategory ";
		
		log.info("earnqry "+earnqry);
		try{
			db.makeConnection();
			con = db.getConnection();
			rs = db.getRecordSet(earnqry);
			while(rs.next()){
				 StaffCategoryInfo sinfo = new StaffCategoryInfo();
				 sinfo.setStaffCategoryCd(new Integer(rs.getString("STAFFCTGRYCD")));
				 sinfo.setStaffCategoryName(rs.getString("STAFFCTGRYNM"));
				cadList.add(sinfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< getEarnings Exception >>>>>>>>>>>>")	;
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return cadList;
	}
	public void saveHeadWiseArrears(ArrearsInfo ainfo,String usercd) throws ApplicationException {
		try {	
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			String harrearscd = AutoGeneration.getNextCode("headwise_arrears","HARREARSCD",5,con);
			
				String sSQL = "insert into headwise_arrears (HARREARSCD,CADRECD,FYEARCD,PAYROLLMONTHID,EARNDEDUCD,amount,percent,flag,STATUS) values ("+harrearscd+",'"+ainfo.getCadreCd()+"','"+ainfo.getFyearcd()+"','"+ainfo.getPayrollmonthid()+"','"+ainfo.getEarnCd()+"',"+ainfo.getAmount()+","+ainfo.getPercent()+",'"+ainfo.getFlag()+"','"+ainfo.getStatus()+"')";
				
				db.executeUpdate(sSQL);
				
				
				/* Insering Monthly Salary Adjustments for the given payroll month*/
				String query1 ="select 'insert into monthly_saladj(msaladjcd,payrollmonthid,empno) values ((select max(msaladjcd)+1 from monthly_saladj),"+ainfo.getPayrollmonthid()+",' || empno || ')' from employeeinfo where cadrecod="+ainfo.getCadreCd()+"  and empno not in (select empno from monthly_saladj where PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+") and empno not in (select empno from employeepersonalinfo where seperationdate is not null ) and empno not in (select empno from emppaystop where PAYROLLMONTHID="+ainfo.getPayrollmonthid()+")";
				
			   rs= db.getRecordSet(query1);
			   Statement st = con.createStatement();
			   boolean bool=false;		   
			    while(rs.next()){
			    	
			    	st.addBatch(rs.getString(1));	
			    	bool=true;
			    }
			    if(bool){
			    st.executeBatch();
			    }
			    st.close();
			    rs.close();
			    String depends ="";
			    String dependsqry ="select DEPENDSON from salhead_ledg_mapping where EARNDEDUCD="+ainfo.getEarnCd()+" and DEPENDSON is not null";
			   rs=db.getRecordSet(dependsqry);
			   if(rs.next()){
			    	depends=rs.getString(1);
			    	}
			    rs.close();
			  /*  Inserting Monthly Salary Adjustments Details  if  CPF CONT Dependents contains  selected earningcd */
			    
			    String cpfDepends="";
			    String cpfQuery="select DEPENDSON from salhead_ledg_mapping where EARNDEDUCD=19 and DEPENDSON is not null";
			    
			    rs = db.getRecordSet(cpfQuery);
			    if(rs.next()){
			    	cpfDepends=rs.getString("DEPENDSON");
			    }
			    String cpfDependsStr = ","+cpfDepends+",";
			    String ecdStr = ","+ainfo.getEarnCd()+",";
			    int i = cpfDependsStr.indexOf(ecdStr);
			   if(i!=-1){   
			    String query2 ="select 'insert into monthly_saladj_det(msaladjcd,earndeducd,adjamt) values(' ||sal.msaladjcd ||',"+ainfo.getEarnCd()+",'||adj.amount||')' from (select adj.empno, round(sum(adjamt) * "+ainfo.getPercent()+") amount from monthly_saladj_det det, monthly_saladj adj where det.earndeducd in (" ;
			    if(depends.length()>0)
			    	query2 +="'"+depends.replaceAll(",","','")+"'" ;
			    else
			    	query2 +="'"+ainfo.getEarnCd()+"'" ;
			    query2 +=") and adj.msaladjcd = det.msaladjcd and adj.payrollmonthid = '"+ainfo.getPayrollmonthid()+"'  group by empno) adj ,monthly_saladj sal where  adj.empno=sal.empno and sal.payrollmonthid='"+ainfo.getPayrollmonthid()+"'";
			    
			    rs= db.getRecordSet(query2);
			    Statement st3 = con.createStatement();
			    while(rs.next()){
			    	st3.addBatch(rs.getString(1));
			    	bool=true;
			    }
			    if(bool){
			    st3.executeBatch();
			    }
			 
			    st3.close();
			   }
			    rs.close();
			    		
			    /*  Updating Monthly Salary Adjustments Details for the given Percentage    */    
			   float percent= Float.parseFloat(ainfo.getPercent());
	   if(percent>0){
		
			    		String pupdateQry ="select 'update monthly_saladj_det set adjamt= adjamt+(round(('||amount||' * "+ainfo.getPercent()+" ) / 100)) where msaladjcd=' || adj.msaladjcd || ' and earndeducd="+ainfo.getEarnCd()+"' from (select empno, sum(amount) amount from(";
			    			if(depends.length()>0){
			    				StringTokenizer stoken = new StringTokenizer(depends,",");
			    				while(stoken.hasMoreTokens())
			    				{
			    					pupdateQry+="select empno, amount  from empernsdeducs earn where earn.earndeducd = "+stoken.nextToken()+" and empno in (select empno  from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
			    					if(stoken.countTokens()!=0){
			    						pupdateQry+=" union all ";
			    						}
			    				}
			    			}else
			    			{
			    				pupdateQry+="select empno, amount  from empernsdeducs earn where earn.earndeducd = "+ainfo.getEarnCd()+" and empno in (select empno  from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";  
			    			}
			    				pupdateQry+=")group by empno) ex,monthly_saladj adj where ex.empno in (select info.empno from monthly_saladj_det ded, employeeinfo info, monthly_saladj adj where EARNDEDUCD = "+ainfo.getEarnCd()+" and info.empno = adj.empno and info.cadrecod = "+ainfo.getCadreCd()+" and adj.msaladjcd = ded.msaladjcd and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+") and ex.empno = adj.empno and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+"";
			    				
			    				rs=db.getRecordSet(pupdateQry);
			    				Statement st1 = con.createStatement();
			    				while(rs.next()){
			    					st1.addBatch(rs.getString(1));
			    					bool=true;
			    				}  
			    				if(bool){
			    					st1.executeBatch();
			    				}
			    				st1.close();
			    				rs.close();
			    				String pinsertQry="select 'insert into monthly_saladj_det(msaladjcd,earndeducd,adjamt) values(' || adj.msaladjcd || ' ,"+ainfo.getEarnCd()+", ' || round((amount * "+ainfo.getPercent()+") / 100) || ')' from (select empno, sum(amount) amount from(";	
			    				if(depends.length()>0){
			    					StringTokenizer stoken = new StringTokenizer(depends,",");
			    					while(stoken.hasMoreTokens())
			    					{
			    						pinsertQry+="select empno, amount from empernsdeducs earn where earn.earndeducd =  "+stoken.nextToken()+" and empno in (select empno from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
			    						if(stoken.countTokens()!=0){
			    							pinsertQry+=" union all ";
			    						}
			    					}
			    				}
			    				else{
			    					pinsertQry+="select empno, amount from empernsdeducs earn where earn.earndeducd =  "+ainfo.getEarnCd()+" and empno in (select empno from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
			    				}
			    					pinsertQry+=") group by empno) ex, monthly_saladj adj where ex.empno not in (select info.empno from monthly_saladj_det ded, employeeinfo info, monthly_saladj adj where EARNDEDUCD = "+ainfo.getEarnCd()+" and info.empno = adj.empno and info.cadrecod = "+ainfo.getCadreCd()+" and adj.msaladjcd = ded.msaladjcd and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+") and ex.empno = adj.empno and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+"";
			    					
			    					rs=db.getRecordSet(pinsertQry);
			    					Statement st2 = con.createStatement();
			    					while(rs.next()){
			    						st2.addBatch(rs.getString(1));
			    						bool=true;
			    					}
			  
			    					if(bool){
			    						st2.executeBatch();
			    					}
			    					st2.close();
			    					rs.close();
	}
	else if(Integer.parseInt(ainfo.getAmount())>0){
		
		/*  Updateing Monthly Salary Adjustments Details for the given Amount   */  
		
    		String aupdateQry ="select 'update monthly_saladj_det set adjamt= adjamt+("+ainfo.getAmount()+") where msaladjcd=' || adj.msaladjcd || ' and earndeducd="+ainfo.getEarnCd()+"' from (select empno, sum(amount) amount from(";
    			if(depends.length()>0){
    				StringTokenizer stoken = new StringTokenizer(depends,",");
    				while(stoken.hasMoreTokens())
    				{
    					aupdateQry+="select empno, amount  from empernsdeducs earn where earn.earndeducd = "+stoken.nextToken()+" and empno in (select empno  from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
    					if(stoken.countTokens()!=0){
    						aupdateQry+=" union all ";
    						}
    				}
    			}else
    			{
    				aupdateQry+="select empno, amount  from empernsdeducs earn where earn.earndeducd = "+ainfo.getEarnCd()+" and empno in (select empno  from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";  
    			}
    				aupdateQry+=")group by empno) ex,monthly_saladj adj where ex.empno in (select info.empno from monthly_saladj_det ded, employeeinfo info, monthly_saladj adj where EARNDEDUCD = "+ainfo.getEarnCd()+" and info.empno = adj.empno and info.cadrecod = "+ainfo.getCadreCd()+" and adj.msaladjcd = ded.msaladjcd and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+") and ex.empno = adj.empno and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+"";
    				
    				rs=db.getRecordSet(aupdateQry);
    				Statement st1 = con.createStatement();
    				while(rs.next()){
    					st1.addBatch(rs.getString(1));
    					bool=true;
    				}  
    				if(bool){
    					st1.executeBatch();
    				}
    				st1.close();
    				rs.close();
    				String ainsertQry="select 'insert into monthly_saladj_det(msaladjcd,earndeducd,adjamt) values(' || adj.msaladjcd || ' ,"+ainfo.getEarnCd()+", ' || "+ainfo.getAmount()+" || ')' from (select empno, sum(amount) amount from(";	
    				if(depends.length()>0){
    					StringTokenizer stoken = new StringTokenizer(depends,",");
    					while(stoken.hasMoreTokens())
    					{
    						ainsertQry+="select empno, amount from empernsdeducs earn where earn.earndeducd =  "+stoken.nextToken()+" and empno in (select empno from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
    						if(stoken.countTokens()!=0){
    							ainsertQry+=" union all ";
    						}
    					}
    				}
    				else{
    					ainsertQry+="select empno, amount from empernsdeducs earn where earn.earndeducd =  "+ainfo.getEarnCd()+" and empno in (select empno from employeeinfo info where info.cadrecod = "+ainfo.getCadreCd()+")";
    				}
    					ainsertQry+=") group by empno) ex, monthly_saladj adj where ex.empno not in (select info.empno from monthly_saladj_det ded, employeeinfo info, monthly_saladj adj where EARNDEDUCD = "+ainfo.getEarnCd()+" and info.empno = adj.empno and info.cadrecod = "+ainfo.getCadreCd()+" and adj.msaladjcd = ded.msaladjcd and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+") and ex.empno = adj.empno and PAYROLLMONTHID = "+ainfo.getPayrollmonthid()+"";
    					
    					rs=db.getRecordSet(ainsertQry);
    					Statement st2 = con.createStatement();
    					while(rs.next()){
    						st2.addBatch(rs.getString(1));
    						bool=true;
    					}
  
    					if(bool){
    						st2.executeBatch();
    					}
    					st2.close();
    					rs.close();
    				}
	   String activityDesc="Head Wise Arrears  Of cadre  "+ainfo.getCadreCd()+"   Saved.";
		ActivityLog.getInstance().write(usercd, activityDesc, "N", String.valueOf(harrearscd), con);
				db.commitTrans();
		}
		catch(ApplicationException ae){
			log.error("in catch "+ae);
			throw ae;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void updateHeadWiseArrears(ArrearsInfo ainfo,String usercd) throws ApplicationException {
		try {	
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
				String uSQL = "update headwise_arrears set CADRECD='"+ainfo.getCadreCd()+"',FYEARCD='"+ainfo.getFyearcd()+"',PAYROLLMONTHID='"+ainfo.getPayrollmonthid()+"',EARNDEDUCD='"+ainfo.getEarnCd()+"',amount="+ainfo.getAmount()+",percent="+ainfo.getPercent()+",flag='"+ainfo.getFlag()+"',STATUS='"+ainfo.getStatus()+"' where HARREARSCD="+ainfo.getHarrearscd();
				log.info("SQL Query for updateHeadWiseArrears is ======== "+uSQL);
				//session.save(baseaccountinfo);
				
				db.executeUpdate(uSQL);
				 String activityDesc="Head Wise Arrears  Of cadre "+ainfo.getCadreCd()+"   Updated.";
					ActivityLog.getInstance().write(usercd, activityDesc, "N", String.valueOf(ainfo.getHarrearscd()), con);
				db.commitTrans();
		}
		catch(ApplicationException ae){
			log.error("in catch "+ae);
			throw ae;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void saveEarningArrears(ArrearsInfo ainfo, String usercd) throws ApplicationException {
		try {	
			
		saveHeadWiseArrears(ainfo,usercd);
		db.makeConnection();
		con = db.getConnection();
		db.setAutoCommit(false);
		rs= db.getRecordSet("select mas.earndeducd, mas.earndedunm, map.dependson,percentagevalue from salhead_ledg_mapping map, earndedumaster mas where map.dependson is not null and cadrecod = "+ainfo.getCadreCd()+" and instr(',' || map.dependson || ',', ',"+ainfo.getEarnCd()+",') > 0 and percentagevalue != 0 and map.earndeducd = mas.earndeducd");
		
		while(rs.next()){
			ResultSet	rs1=null;
			String str=null;
			log.info("EARNDEDUCD       "+rs.getString("EARNDEDUCD"));
			if("7".equals(rs.getString("EARNDEDUCD"))){
				str="select 'update monthly_saladj_det set adjamt=' ||(select round(sum(adjamt) * "+rs.getString("percentagevalue")+" / 100) from monthly_saladj_det sal where instr(',"+rs.getString("dependson")+",', ',' || earndeducd || ',') > 0 and sal.msaladjcd = det.msaladjcd) || ' where msaladjcd=' || det.msaladjcd || ' ' from monthly_saladj_det det where earndeducd = '"+rs.getString("earndeducd")+"' and msaladjcd in(select msaladjcd from monthly_saladj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"' and hraoption='H'))  ";
                		
			}
			else{
				 str="select 'update monthly_saladj_det set adjamt=' ||(select round(sum(adjamt) * "+rs.getString("percentagevalue")+" / 100) from monthly_saladj_det sal where instr(',"+rs.getString("dependson")+",', ',' || earndeducd || ',') > 0 and sal.msaladjcd = det.msaladjcd) || ' where msaladjcd=' || det.msaladjcd || ' ' from monthly_saladj_det det where earndeducd = '"+rs.getString("earndeducd")+"' and msaladjcd in(select msaladjcd from monthly_saladj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"'))  ";
				 
			}
			rs1=db.getRecordSet(str);
		
		while(rs1.next()){
			
			db.executeUpdate(rs1.getString(1));
		}
		rs1.close();
		if("7".equals(rs.getString("EARNDEDUCD"))){
			
	        str=" select distinct 'insert into monthly_saladj_det(MSALADJCD,EARNDEDUCD,ADJAMT) values (' || det.msaladjcd || ',''"+rs.getString("earndeducd")+"'',' || (select round(sum(adjamt) *  "+rs.getString("percentagevalue")+"  / 100 )from monthly_saladj_det sal where instr(',"+rs.getString("dependson")+",', ',' || earndeducd || ',') > 0 and sal.msaladjcd = det.msaladjcd) || ')' from monthly_saladj_det det where msaladjcd in (select msaladjcd from monthly_saladj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"' and hraoption='H')) and msaladjcd not in (select msaladjcd from monthly_saladj_det where msaladjcd in (select msaladjcd from monthly_saladj adj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and adj.empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"'  and hraoption='Q')) and  earndeducd = '"+rs.getString("earndeducd")+"' )";
	        
		}
		else{
			
			str=" select distinct 'insert into monthly_saladj_det(MSALADJCD,EARNDEDUCD,ADJAMT) values (' || det.msaladjcd || ',''"+rs.getString("earndeducd")+"'',' || (select round(sum(adjamt) *  "+rs.getString("percentagevalue")+"  / 100 )from monthly_saladj_det sal where instr(',"+rs.getString("dependson")+",', ',' || earndeducd || ',') > 0 and sal.msaladjcd = det.msaladjcd) || ')' from monthly_saladj_det det where msaladjcd in (select msaladjcd from monthly_saladj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"')) and msaladjcd not in (select msaladjcd from monthly_saladj_det where msaladjcd in (select msaladjcd from monthly_saladj adj where payrollmonthid = '"+ainfo.getPayrollmonthid()+"' and adj.empno in (select empno from employeeinfo where cadrecod = '"+ainfo.getCadreCd()+"' )) and  earndeducd = '"+rs.getString("earndeducd")+"')  ";
			
		}
		
		
		rs1=db.getRecordSet(str);
	
		
		while(rs1.next()){
			log.info("7777777777"+rs1.getString(1));
			db.executeUpdate(rs1.getString(1));
		}
		rs1.close();
		}
		
		
		}
		catch(ApplicationException ae){
			log.error("in catch "+ae);
			throw ae;    
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}