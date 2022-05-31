
package com.epis.dao.investment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.epis.bean.admin.Bean;
import com.epis.bean.cashbook.PartyInfo;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.bean.investment.InvestmentReportsBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.bean.investment.RatioBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.CurrencyFormat;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;


public class InvestmentReportDAO {
	Log log=new Log(InvestmentReportDAO.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private InvestmentReportDAO(){
			
		}
	   private static final InvestmentReportDAO reportsDAO= new InvestmentReportDAO();
	   public static InvestmentReportDAO getInstance(){
		return reportsDAO;   
	   }
	public List InterestReceivedStatement(InvestmentReportsBean bean) throws Exception {
		List list =new ArrayList();
		try{
			float inttamt =0;
			QuotationBean bean1 =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select security_name,interest_rate,investmentmode,interest_date,invest_date,purchaeprice,totalinterest,registercd from ((select distinct(security_name)security_name,interest_rate,investmentmode,to_char(to_date(interest_date, 'dd/MM'),'dd/Mon') interest_date,to_char(invest_date, 'dd/Mon/yyyy') invest_date,purchaeprice,(select sum(credit - debit) from voucher_details det,cb_voucher_info info, invest_sec_category sec, invest_register reg where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and info.vouchertype = 'R' and info.partytype = 'P' and sec.investinterest = det.accounthead) totalinterest,reg.registercd registercd from invest_register reg, invest_sec_category sec, cb_voucher_info info where DELSTATUS='N' and sec.categoryid = reg.categoryid) UNION ALL (select distinct (ss.security_name) security_name,ss.cupon_rate  interest_rate,ss.modeof_interest investmentmode,to_char(to_date(ss.interest_date, 'dd/MM'),'dd/Mon') ||','||nvl(to_char(to_date(interest_date1, 'dd/MM'),'dd/Mon'),'')  interest_date,to_char(ss.SETTLEMENT_DATE, 'dd/Mon/yyyy') invest_date,ss.purchase_price purchaeprice,(select sum(credit - debit) from voucher_details     det,cb_voucher_info     info,invest_sec_category sec,invest_register     reg  where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and info.vouchertype = 'R' and info.partytype = 'P' and  sec.investinterest = det.accounthead) totalinterest,ss.register_cd registercd  from investment_register ss, invest_sec_category sec, cb_voucher_info info  where ss.register_cd is not null and sec.categorycd =ss.categorycd)) WHERE registercd IS NOT NULL " ;
			
			if(bean.getInvestmentMode()!=null)
				query +=" and INVESTMENTMODE = '"+bean.getInvestmentMode()+"'";
			if(!"".equals(bean.getSecurityNames()))
				query +=" and SECURITY_NAME in("+bean.getSecurityNames()+")";
			if(!bean.getFromDate().equals(""))
				query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) >=  '"+bean.getFromDate()+"' ";
			if(!bean.getToDate().equals(""))
				query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) <=  '"+bean.getToDate()+"' ";
			query+=" order by invest_date";
			log.info("InvestmentReportDAO:InterestReceivedStatement(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			
				//ps.setString(1, validdate);
			rs=ps.executeQuery();
			while(rs.next()){
				bean1 = new QuotationBean();
				bean1.setSecurityName(rs.getString("SECURITY_NAME"));
				bean1.setInterestRate(rs.getString("INTEREST_RATE"));
				bean1.setInvestmentMode(rs.getString("INVESTMENTMODE"));
				bean1.setInterestDate(rs.getString("interest_date"));
				bean1.setInvestmentdate(rs.getString("invest_date"));
				bean1.setPurchasePrice(rs.getString("purchaeprice"));
				inttamt = rs.getFloat("INTEREST_RATE")*rs.getFloat("purchaeprice")/100;
				bean1.setAmt(inttamt);
				bean1.setAmountRceived(rs.getString("totalinterest"));
				list.add(bean1);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:InterestReceivedStatement():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	public List getinterestMonths()
	{
		String monthName[]={"Jan","Feb","Mar","APr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String monthValue[]={"Jan","Feb","Mar","APr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		List list= new ArrayList();
		for(int i=0; i<monthName.length; i++)
		{
			Bean bean=new Bean();
			bean.setName(monthName[i]);
			bean.setCode(monthValue[i]);
			list.add(bean);
		}
		return list;
		
	}
	public List getMaturityYears()
	{
		List list=new ArrayList();
		for(int i=2010; i<2040; i++)
		{
			Bean bean=new Bean();
			bean.setName(i+"");
			bean.setCode(i+"");
			list.add(bean);
		}
		return list;
	}
	
	
	public List InvestmentRegisterReport(InvestmentReportsBean bean) throws Exception {
		List list =new ArrayList();
		try{
			
			QuotationBean bean1 =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select SETTLEMENT_DATE,SECURITYfullNAME,CUPON_RATE,YTM_VALUE,facevalue,FACEVALUEperunit,NO_OF_BONDS,OFFERED_PRICE,PURCHASE_PRICE,ACCURED_INTEREST_AMOUNT,SETTLEMENT_AMOUNT,MODEOF_INTEREST,INTEREST_DATE,MATURITY_DATE,annualinterest,SECURITY_PROPOSAL,interestrvdate,interestrvamount,maturityincbdate,maturityincbamount,totalInterest,totalMaturity,closingbal,incentivepayable,MATURITYDATE,(CASE WHEN SYSDATE-MATURITYDATE>1 AND CLOSINGBAL>0 THEN 'Overdue Investment' ELSE '--' END)REMARKS from(select to_char(nvl(SETTLEMENT_DATE,DEALDATE),'dd/Mon/yyyy')SETTLEMENT_DATE,nvl(SURITY_FULLNAME,SECURITY_NAME)SECURITYfullNAME,nvl(CUPON_RATE,0)CUPON_RATE,YTM_VALUE,(case when nvl(reg.MODULE_FLAG,'Y')='M' then nvl(FACEVALUE_INRS,0) else (FACEVALUE*NO_OF_BONDS) end) facevalue,nvl(FACEVALUE,0)FACEVALUEperunit,nvl(NO_OF_BONDS,0)NO_OF_BONDS,nvl(OFFERED_PRICE,0)OFFERED_PRICE,nvl(PURCHASE_PRICE,0)PURCHASE_PRICE,( case when (reg.categorycd='PSU' and pr.market_type='P') then 0 when(reg.module_flag='M') then nvl(reg.accured_interest_amount,0) else (nvl(SETTLEMENT_AMOUNT, 0) - nvl(PURCHASE_PRICE, 0)) end)  ACCURED_INTEREST_AMOUNT,(case when reg.module_flag='M' then (nvl(PURCHASE_PRICE,0)+nvl(ACCURED_INTEREST_AMOUNT,0)) else  nvl(SETTLEMENT_AMOUNT,0) end)SETTLEMENT_AMOUNT,nvl(decode(MODEOF_INTEREST,'H','Half-Yearly','Y','Yearly'),' ')MODEOF_INTEREST,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')||','||nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'')INTEREST_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,(case when nvl(reg.MODULE_FLAG,'Y')='M' then round(((nvl(FACEVALUE_INRS,0)*nvl(CUPON_RATE,0))/100),2) else round(((nvl(FACEVALUE,0)*nvl(NO_OF_BONDS,0)*nvl(CUPON_RATE,0))/100),2) end)annualinterest,SECURITY_PROPOSAL,NVL(GET_DETIALDATEWITHCOMMA(SECURITY_PROPOSAL, 'Interest'), '--') interestrvdate,NVL(GET_DETIALAMOUNTWITHCOMMA(SECURITY_PROPOSAL, 'Interest'), '--') interestrvamount,NVL(GET_DETIALDATEWITHCOMMA(SECURITY_PROPOSAL, 'Maturity'), '--') maturityincbdate, NVL(GET_DETIALAMOUNTWITHCOMMA(SECURITY_PROPOSAL, 'Maturity'), '--') maturityincbamount, (case  when nvl(reg.MODULE_FLAG, 'Y') = 'M' then  nvl(FACEVALUE_INRS, 0)  else  (FACEVALUE * NO_OF_BONDS)  end)-nvl(GETtotalInterest(SECURITY_PROPOSAL, 'Maturity'), 0) closingbal,nvl(GETtotalInterest(SECURITY_PROPOSAL,'Interest'),0)totalInterest,nvl(GETtotalInterest(SECURITY_PROPOSAL,'Maturity'),0)totalMaturity,nvl(INCENTIVE_TEXT,'--')incentivepayable,REG.MATURITY_DATE MATURITYDATE from investment_register reg,invest_proposal pr where REGISTER_CD is not null and reg.proposal_ref_no=pr.ref_no and reg.status='A' " ;
			
			if(!bean.getInvestDate().equals(""))
				query +=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) = '"+bean.getInvestDate()+"'";
			if(!bean.getFromDate().equals(""))
				query+=" and to_date(to_char(dealdate,'DD/MON/YYYY')) >=  '"+bean.getFromDate()+"' ";
			if(!bean.getToDate().equals(""))
				query+=" and to_date(to_char(dealdate,'DD/MON/YYYY')) <=  '"+bean.getToDate()+"' ";
			
			if(!"".equals(bean.getSecurityNames()))
				query +=" and SECURITY_NAME in("+bean.getSecurityNames()+")";
			if(!bean.getFinYear().equals(""))
				query +=" and dealdate is not null and GET_INVEST_FINYEAR(dealdate)='"+bean.getFinYear()+"'";
			if(!bean.getSecurityCategory().equals(""))
				query+=" and reg.CATEGORYCD='"+bean.getSecurityCategory()+"'";
			if(!bean.getInterestMonth().equals(""))
				query+=" and to_char(to_date(reg.INTEREST_DATE,'dd/MM'),'Mon')='"+bean.getInterestMonth()+"'";
			if(!(bean.getMaturityMonth().equals("")&& bean.getMaturityYear().equals("")))
				query+=" and to_char(reg.maturity_date,'Mon/yyyy')='"+bean.getMaturityMonth()+"/"+bean.getMaturityYear()+"'";
			
			query+=" )order by MATURITYDATE";
			log.info("InvestmentReportDAO:InterestReceivedStatement(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			
				//ps.setString(1, validdate);
			rs=ps.executeQuery();
			while(rs.next()){
				bean1 = new QuotationBean();
				bean1.setInvestmentdate(rs.getString("SETTLEMENT_DATE"));
				bean1.setSecurityName(StringUtility.checknull(rs.getString("SECURITYfullNAME")));
				bean1.setInterestRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
				bean1.setYtm(StringUtility.checknull(rs.getString("YTM_VALUE")));
				bean1.setFaceValue(StringUtility.checknull(rs.getString("facevalue")));
				bean1.setNumberOfUnits(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
				bean1.setPriceoffered(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
				bean1.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
				bean1.setAccuredInterest(StringUtility.checknull(rs.getString("ACCURED_INTEREST_AMOUNT")));
				bean1.setSettlementAmount(StringUtility.checknull(rs.getString("SETTLEMENT_AMOUNT")));
				bean1.setInvestmode(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
				bean1.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
				bean1.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
				bean1.setAnnualInterest(StringUtility.checknull(rs.getString("annualinterest")));
				bean1.setInvestmentFaceValue(StringUtility.checknull(rs.getString("FACEVALUEperunit")));
				bean1.setSecurityproposalNo(StringUtility.checknull(rs.getString("SECURITY_PROPOSAL")));
				bean1.setInterestrvincbdate(StringUtility.checknull(rs.getString("interestrvdate")));
				bean1.setMaturityincbdate(StringUtility.checknull(rs.getString("maturityincbdate")));
				bean1.setInterestrvincb(StringUtility.checknull(rs.getString("interestrvamount")));
				bean1.setMaturityincb(StringUtility.checknull(rs.getString("maturityincbamount")));
				bean1.setTotalInterest(StringUtility.checknull(rs.getString("totalInterest")));
				bean1.setTotalMaturity(StringUtility.checknull(rs.getString("totalMaturity")));
				bean1.setClosingBal(StringUtility.checknull(rs.getString("closingbal")));
				bean1.setIncentivepayable(StringUtility.checknull(rs.getString("incentivepayable")));
				bean1.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
					
				
				list.add(bean1);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:InterestReceivedStatement():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	
	
	
	public List InvestmentgroupRegisterReport(InvestmentReportsBean bean) throws Exception {
		List list =new ArrayList();
		try{
			
			QuotationBean bean1 =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select to_char(nvl(SETTLEMENT_DATE,DEALDATE),'dd/Mon/yyyy')SETTLEMENT_DATE,reg.security_proposal securityproposal,group_name groupname,nvl(SURITY_FULLNAME,SECURITY_NAME)SECURITYfullNAME,nvl(CUPON_RATE,0)CUPON_RATE,YTM_VALUE,(case when nvl(reg.MODULE_FLAG,'Y')='M' then nvl(FACEVALUE_INRS,0) else (FACEVALUE*NO_OF_BONDS) end) facevalue,nvl(FACEVALUE,0)FACEVALUEperunit,nvl(NO_OF_BONDS,0)NO_OF_BONDS,nvl(OFFERED_PRICE,0)OFFERED_PRICE,nvl(PURCHASE_PRICE,0)PURCHASE_PRICE,( case when (reg.categorycd='PSU' and pr.market_type='P') then 0 when(reg.module_flag='M') then nvl(reg.accured_interest_amount,0) else (nvl(SETTLEMENT_AMOUNT, 0) - nvl(PURCHASE_PRICE, 0)) end)  ACCURED_INTEREST_AMOUNT,(case when reg.module_flag='M' then (nvl(PURCHASE_PRICE,0)+nvl(ACCURED_INTEREST_AMOUNT,0)) else  nvl(SETTLEMENT_AMOUNT,0) end)SETTLEMENT_AMOUNT,nvl(decode(MODEOF_INTEREST,'H','Half-Yearly','Y','Yearly'),' ')MODEOF_INTEREST,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')||','||nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'')INTEREST_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,(case when nvl(reg.MODULE_FLAG,'Y')='M' then round(((nvl(FACEVALUE_INRS,0)*nvl(CUPON_RATE,0))/100),2) else round(((nvl(FACEVALUE,0)*nvl(NO_OF_BONDS,0)*nvl(CUPON_RATE,0))/100),2) end)annualinterest,SECURITY_PROPOSAL,NVL(decode(party.mapping_flag,'Y',get_detialdatawithcomma(security_proposal,'Interest'), get_cashbookdata(group_name, 'Interest')), '--') interestrvincb,NVL(decode(party.mapping_flag,'Y',get_detialdatawithcomma(security_proposal,'Maturity'),get_cashbookdata(group_name, 'Maturity')), '--') maturityincb from investment_register reg,invest_proposal pr,cb_party_info party where REGISTER_CD is not null and reg.proposal_ref_no=pr.ref_no and party.group_name is not null and (reg.security_proposal=party.partyname or reg.security_proposal=party.invest_cash_partyname) and party.module_type='I' and reg.status='A' " ;
			
			if(!bean.getInvestDate().equals(""))
				query +=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) = '"+bean.getInvestDate()+"'";
			if(!"".equals(bean.getSecurityNames()))
				query +="  and party.group_name in("+bean.getSecurityNames()+")";
			if(!bean.getFromDate().equals(""))
				query+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) >=  '"+bean.getFromDate()+"' ";
			if(!bean.getToDate().equals(""))
				query+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) <=  '"+bean.getToDate()+"' ";
			if(!bean.getSecurityCategory().equals(""))
				query+=" and reg.CATEGORYCD='"+bean.getSecurityCategory()+"'";
			if(!bean.getInterestMonth().equals(""))
				query+=" and to_char(to_date(reg.INTEREST_DATE,'dd/MM'),'Mon')='"+bean.getInterestMonth()+"'";
			if(!(bean.getMaturityMonth().equals("")&& bean.getMaturityYear().equals("")))
				query+=" and to_char(reg.maturity_date,'Mon/yyyy')='"+bean.getMaturityMonth()+"/"+bean.getMaturityYear()+"'";
			
			query+=" order by reg.maturity_date,party.group_name";
			log.info("InvestmentReportDAO:InterestReceivedStatement(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			
				//ps.setString(1, validdate);
			rs=ps.executeQuery();
			while(rs.next()){
				bean1 = new QuotationBean();
				bean1.setInvestmentdate(rs.getString("SETTLEMENT_DATE"));
				bean1.setGroupName(StringUtility.checknull(rs.getString("groupname")));
				bean1.setSecurityProposal(StringUtility.checknull(rs.getString("securityproposal")));
				bean1.setSecurityName(StringUtility.checknull(rs.getString("SECURITYfullNAME")));
				bean1.setInterestRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
				bean1.setYtm(StringUtility.checknull(rs.getString("YTM_VALUE")));
				bean1.setFaceValue(StringUtility.checknull(rs.getString("facevalue")));
				bean1.setNumberOfUnits(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
				bean1.setPriceoffered(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
				bean1.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
				bean1.setAccuredInterest(StringUtility.checknull(rs.getString("ACCURED_INTEREST_AMOUNT")));
				bean1.setSettlementAmount(StringUtility.checknull(rs.getString("SETTLEMENT_AMOUNT")));
				bean1.setInvestmode(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
				bean1.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
				bean1.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
				bean1.setAnnualInterest(StringUtility.checknull(rs.getString("annualinterest")));
				bean1.setInvestmentFaceValue(StringUtility.checknull(rs.getString("FACEVALUEperunit")));
				bean1.setSecurityproposalNo(StringUtility.checknull(rs.getString("SECURITY_PROPOSAL")));
				bean1.setInterestrvincb(StringUtility.checknull(rs.getString("interestrvincb")));
				bean1.setMaturityincb(StringUtility.checknull(rs.getString("maturityincb")));
					
				
				list.add(bean1);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:InterestReceivedStatement():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	
	public List InterestReceivedRegister(InvestmentReportsBean bean)throws Exception{
		List list=new ArrayList();
		try{
		VoucherInfo info=null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		String query="select voucherno,to_char(voucher_dt,'dd/Mon/yyyy')voucherdate, sum(nvl(details.credit,0)) amount from cb_voucher_info info,cb_voucher_details details where info.keyno=details.keyno and info.vouchertype='R' and info.emp_party_code='"+StringUtility.checknull(bean.getSecurityproposalNo())+"'  group by voucherno,voucher_dt";
		
		rs=st.executeQuery(query);
		while(rs.next())
		{
			
			info=new VoucherInfo();
			info.setVoucherNo(StringUtility.checknull(rs.getString("voucherno")));
			info.setVoucherDt(StringUtility.checknull(rs.getString("voucherdate")));
			info.setCreditAmount(StringUtility.checknull(rs.getString("amount")));
			list.add(info);
			
		}
		}
		catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:InterestReceivedStatement():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		log.info("the list size is..."+list.size());
		return list;
	}
	
	
	
	
	public List SecurityNames(String validdate) throws Exception {
		List list =new ArrayList();
		try{
			InvestmentReportsBean bean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select distinct(replace(SECURITY_NAME,'&','ampersand')) securityname from invest_register union select distinct(replace(SECURITY_NAME,'&','ampersand')) securityname from investment_register" ;
			
			
			log.info("InvestmentReportDAO:SecurityNames(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			
			rs=ps.executeQuery();
			while(rs.next()){
				bean = new InvestmentReportsBean();
				bean.setSecurityName(rs.getString("securityname"));
				
				list.add(bean);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:SecurityNames():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	public List groupNames()throws Exception
	{
		List list =new ArrayList();
		try{
			PartyInfo bean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select distinct(GROUP_NAME) GROUPNAME from cb_party_info where GROUPING_FLAG='Y'" ;
			
			
			log.info("InvestmentReportDAO:groupNames(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			
			rs=ps.executeQuery();
			while(rs.next()){
				bean = new PartyInfo();
				bean.setGroupName(rs.getString("GROUPNAME"));
				
				list.add(bean);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:SecurityNames():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;

	}
	public List getTrustInvestmentMadeDetails(InvestmentReportsBean bean) throws Exception {
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		Statement stmt=null;	
		try{
			QuotationBean  investbean =null;
			String fromDate = "",toDate="";
			StringTokenizer st = new StringTokenizer(bean.getDate(),"-");
			while(st.hasMoreTokens())
			{
				fromDate=st.nextToken();
				toDate = st.nextToken();
			}
			String query = " select security_name,interest_rate,purchaeprice,facevalue,premium_paid,to_char(invest_date, 'dd/Mon/yyyy') invest_date,ytmpercentage,investmenttype,brokername,accuredinterest,TRUSTTYPE,registercd from ((select security_name,interest_rate,purchaeprice,facevalue, premium_paid, invest_date,ytmpercentage,decode(investmenttype, 'P', 'Primary', 'S', 'Secondary') investmenttype,brokername,(select sum(credit - debit) from voucher_details det, cb_voucher_info info,invest_sec_category sec,invest_register reg where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and info.vouchertype = 'R' and info.partytype = 'P' and sec.investinterest = det.accounthead) accuredinterest,reg.trusttype TRUSTTYPE,reg.registercd registercd from invest_register reg, invest_sec_category sec where DELSTATUS='N' and sec.categoryid = reg.categoryid) union all (select ss.security_name security_name,ss.cupon_rate  interest_rate,ss.purchase_price  purchaeprice,ss.facevalue facevalue,ss.premium_paid premium_paid,SETTLEMENT_DATE invest_date,0 ytmpercentage,'' investmenttype,'' brokername,(select sum(credit - debit) from voucher_details     det,cb_voucher_info     info,invest_sec_category sec,investment_register     reg  where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and  info.vouchertype = 'R' and info.partytype = 'P' and sec.investinterest = det.accounthead) accuredinterest,ss.trusttype TRUSTTYPE,ss.register_cd registercd  from investment_register ss, invest_sec_category sec  where ss.register_cd is not null and sec.categorycd =ss.categorycd))where registercd is not null " ;
			if(!"".equals(bean.getTrustType()))
			{
				query +=" and upper(TRUSTTYPE) like upper('%"+bean.getTrustType()+"%')";
			}
			if(!"".equals(bean.getDate())){
				query +="and  nvl(to_char(INVEST_DATE, 'YYYY'), ' ')  between upper(nvl('"+fromDate+"', '')) and upper(nvl('"+toDate+"', ''))";
			}
			log.info("InvestmentReportDAO:getTrustInvestmentMadeDetails(): "+query);	
			connection = DBUtility.getConnection();
			if(connection!=null){
				stmt = connection.createStatement();	
				if(stmt!=null){
							reusltSet = stmt.executeQuery(query);			
			while(reusltSet.next()){
				investbean = new QuotationBean();
				investbean.setSecurityName(StringUtility.checknull(reusltSet.getString("security_name")));
				investbean.setInterestRate(StringUtility.checknull(reusltSet.getString("interest_rate")));
				investbean.setPurchasePrice(StringUtility.checknull(new CurrencyFormat().getDecimalCurrency(reusltSet.getDouble("purchaeprice"))));
				investbean.setFaceValue(StringUtility.checknull(new CurrencyFormat().getDecimalCurrency((reusltSet.getDouble("facevalue")))));
				investbean.setPremiumPaid(StringUtility.checknull(new CurrencyFormat().getDecimalCurrency(reusltSet.getDouble("premium_paid"))));
				investbean.setInvestmentdate(StringUtility.checknull(reusltSet.getString("invest_date")));
				investbean.setYtm(StringUtility.checknull(reusltSet.getString("ytmpercentage")));
				investbean.setInvestmentType(StringUtility.checknull(reusltSet.getString("investmenttype")));
				investbean.setBrokerName(StringUtility.checknull(reusltSet.getString("brokername")));
				investbean.setAmountRceived(StringUtility.checknull(new CurrencyFormat().getDecimalCurrency(reusltSet.getDouble("accuredinterest"))));
				list.add(investbean);
			}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentReportDAO:getTrustInvestmentMadeDetails():SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			
				DBUtility.closeConnection(reusltSet,stmt,connection);	
		}
		return list;
	}
	
	public List getInvStReportData(String year,String security,String trust,String fromDate,String toDate) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		Statement stmt=null;	
		QuotationBean qbean = new QuotationBean();
		try{	
			String searchQry="SELECT DISNICNO,FACEVALUE,INTEREST_RATE,SECURITY_NAME,RATING,DMATNO,to_char(INVEST_DATE,'dd/Mon/YYYY') INVEST_DATE,INVEST_DATE as INVEST_DATE_ORDER,INVESTMENT_FACEVALUE,to_char(REDEMPTIONDATE,'YYYY')REDEMPTIONDATE,to_char(MATURITYDATE,'dd/Mon/YYYY') MATURITYDATE,NUMBER_OF_UNITS,REMARKS,INVESTMENTMODE,to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon') INTEREST_DATE from invest_register where DELSTATUS='N'  ";
			
			String searchQry1="SELECT ' ' DISNICNO,ss.cupon_rate FACEVALUE,ss.cupon_rate INTEREST_RATE,ss.security_name SECURITY_NAME,' ' RATING,' ' DMATNO,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY') INVEST_DATE,SETTLEMENT_DATE as INVEST_DATE_ORDER,ss.facevalue INVESTMENT_FACEVALUE,' ' REDEMPTIONDATE,to_char(ss.maturity_date,'dd/Mon/YYYY') MATURITYDATE,ss.no_of_bonds NUMBER_OF_UNITS,' ' REMARKS,ss.modeof_interest INVESTMENTMODE,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')||','||nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'') INTEREST_DATE from investment_register ss where ss.register_cd is not null";
			/*if(!year.equals(""))
			{
				searchQry+=" and nvl(to_char(INVEST_DATE,'YYYY'),' ') like upper(nvl('"+year+"',''))";
				searchQry1+=" and nvl(to_char(SETTLEMENT_DATE,'YYYY'),' ') like upper(nvl('"+year+"',''))";
			}*/

			if(!security.equals(""))
			{
				searchQry+=" and upper(SECURITY_NAME) like upper('%"+security+"%')";
				searchQry1+=" and upper(SECURITY_NAME) like upper('%"+security+"%')";
			}

			if(!trust.equals(""))
			{
				searchQry+=" and upper(TRUSTTYPE) like upper('%"+trust+"%')";
				searchQry1+=" and upper(TRUSTTYPE) like upper('%"+trust+"%')";
			}

			if(!fromDate.equals(""))
			{
				searchQry+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
				searchQry1+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
			}
				

			if(!toDate.equals(""))
			{
				searchQry+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
				searchQry1+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
			}
			
			searchQry1+=" order by INVEST_DATE_ORDER ";
			
			searchQry=searchQry+" Union "+searchQry1;
			log.info("hi anilll report qry "+searchQry);
			
			connection = DBUtility.getConnection();
			if(connection!=null){
				stmt = connection.createStatement();	
				if(stmt!=null){
							reusltSet = stmt.executeQuery(searchQry);
					while(reusltSet.next()){
						qbean = new QuotationBean();
						
						qbean.setDisIncNo(StringUtility.checknull(reusltSet.getString("DISNICNO")));
						qbean.setFaceValue(StringUtility.checknull(reusltSet.getString("FACEVALUE")));
						qbean.setDematno(StringUtility.checknull(reusltSet.getString("DMATNO")));
						qbean.setInvestmentdate(StringUtility.checknull(reusltSet.getString("INVEST_DATE")));
						qbean.setInterestDate(StringUtility.checknull(reusltSet.getString("INTEREST_DATE")));
						qbean.setInterestRate(StringUtility.checknull(reusltSet.getString("Interest_Rate")));
						qbean.setInvestmentFaceValue(StringUtility.checknull(reusltSet.getString("investment_facevalue")));
						qbean.setInvestmentMode(StringUtility.checknull(reusltSet.getString("investmentmode")));
						//qbean.setInvestmentType(StringUtility.checknull(reusltSet.getString("investmenttype")));
						//qbean.setLetterNo(StringUtility.checknull(reusltSet.getString("LETTER_NO")));
						qbean.setMaturityDate(StringUtility.checknull(reusltSet.getString("MaturityDate")));
						qbean.setNumberOfUnits(StringUtility.checknull(reusltSet.getString("number_of_units")));
						//qbean.setPremiumPaid(StringUtility.checknull(reusltSet.getString("premium_paid")));
						//qbean.setPurchasePrice(StringUtility.checknull(reusltSet.getString("purchaeprice")));
						//qbean.setQuotationCd(reusltSet.getString("quotationcd"));
						qbean.setRating(StringUtility.checknull(reusltSet.getString("RATING")));
						qbean.setRedemptionDate(StringUtility.checknull(reusltSet.getString("redemptiondate")));
						qbean.setRemarks(StringUtility.checknull(reusltSet.getString("remarks")));
						//qbean.setSecurityCategory(StringUtility.checknull(reusltSet.getString("CATEGORYCD")));
						qbean.setSecurityName(StringUtility.checknull(reusltSet.getString("SECURITY_NAME")));
						//qbean.setTrustName(StringUtility.checknull(reusltSet.getString("TRUSTTYPE")));
						//qbean.setYtm(StringUtility.checknull(reusltSet.getString("YTMPercentage")));
						list.add(qbean);
						
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentReportDAO:getInvStReportData:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentReportDAO:getInvStReportData:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,stmt,connection);			
		}
		
		return list;
		
	}
	public List SecurityCategories() throws Exception {
		
		List list =new ArrayList();
		try{
			InvestmentReportsBean bean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select CATEGORYID,CATEGORYCD from invest_sec_category" ;
			log.info("InvestmentReportDAO:SecurityNames(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()){
				bean = new InvestmentReportsBean();
				bean.setSecurityCategoryId(rs.getString("CATEGORYID"));
				bean.setSecurityCategory(rs.getString("CATEGORYCD"));
				list.add(bean);
			}
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:SecurityCategories():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	public List InvestmentStatisticsDetails(InvestmentReportsBean bean) throws Exception {

		List list =new ArrayList();
		try{ 
			float totfacevalue=0,totinvest=0;
			float totpercent=0;
			QuotationBean ibean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = " select facevalue,totprice,purchaeprice,registercd,INVEST_DATE,categoryid,securityname from  ((select facevalue,(select sum(purchaeprice) from invest_register) totprice,purchaeprice,reg.registercd registercd,reg.categoryid categoryid,INVEST_DATE,reg.security_name securityname  from invest_register reg  where DELSTATUS = 'N')  union all  (select ss.facevalue facevalue,(select sum(settlement_amount) from investment_register) totprice,ss.purchase_price purchaeprice,ss.register_cd,category.categoryid categoryid,ss.invest_date INVEST_DATE, ss.security_name securityname from  investment_register ss,invest_sec_category category where ss.categorycd=category.categorycd)) where registercd is not null ";
			if(!"".equals(bean.getSecurityName()))
				query+=" and security_name = '"+bean.getSecurityName()+"'";
			if(!bean.getFromDate().equals(""))
				query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) >=  '"+bean.getFromDate()+"' ";
			if(!bean.getToDate().equals(""))
				query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) <=  '"+bean.getToDate()+"' ";
			if(!"".equals(bean.getSecurityCategory()))
				query+="and categoryid = '"+bean.getSecurityCategory()+"' " ;
			log.info("InvestmentReportDAO:SecurityNames(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()){
				ibean = new QuotationBean();
				ibean.setFaceValue(new CurrencyFormat().getCurrency(rs.getDouble("facevalue")));
				totfacevalue +=Float.parseFloat(rs.getString("facevalue"));
				DecimalFormat df = new DecimalFormat("#.##");
				String investpercent = df.format(Float.parseFloat(rs.getString("purchaeprice"))/Float.parseFloat(rs.getString("totprice")));
				ibean.setInvestmentFaceValue(String.valueOf(investpercent));
				ibean.setPurchasePrice(new CurrencyFormat().getCurrency(rs.getDouble("purchaeprice")));
				totinvest +=Float.parseFloat(rs.getString("purchaeprice"));
				totpercent += Float.parseFloat(investpercent);
				list.add(ibean);
			}
			ibean = new QuotationBean();
			ibean.setFaceValue("Total: "+new CurrencyFormat().getDecimalCurrency(totfacevalue));
			ibean.setInvestmentFaceValue("Total: "+totpercent);
			ibean.setPurchasePrice("Total: "+new CurrencyFormat().getDecimalCurrency(totinvest));
			list.add(ibean);
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("InvestmentReportDAO:InvestmentStatisticsDetails():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}

	public List getCurrentRatio() throws Exception {
		RatioBean bean =null;
		
		List list = new ArrayList();
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String catQuery ="select categorycd,cat.categoryid categoryid, cat.percentage percentage from invest_sec_category sec,invest_ratio ratio,invest_ratio_category cat where sec.categoryid = cat.categoryid and cat.ratiocd = ratio.ratiocd and ratio.ratiocd=(select max(ratiocd)from invest_ratio) order by categorycd";
			PreparedStatement pst = con.prepareStatement(catQuery);
			rs=pst.executeQuery();
			while(rs.next()){
				bean = new RatioBean();
				bean.setCategoryCd(rs.getString("categorycd"));
				bean.setCategoryId(rs.getString("categoryid"));
				bean.setPercentage(rs.getString("percentage"));
				list.add(bean);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("RatioDAO:getCurrentRatio():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
public Map getAmountInvested(String security,String trust,String fromDate,String toDate) throws Exception {
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Statement st = null;
		
		LinkedHashMap map = new LinkedHashMap();
		ArrayList list=new ArrayList();
		ArrayList ratoList=new ArrayList();
		ArrayList ratoListCd=new ArrayList();
		Bean bean=new Bean();
		
		try{
			con = DBUtility.getConnection();
			//System.out.println("ANil111111111111111"+catCT);
			ratoList.add("TOT_AMT");
			ratoListCd.add("TOT_AMT");
			String catQuery ="select categorycd,cat.categoryid categoryid, cat.percentage percentage from invest_sec_category sec,invest_ratio ratio,invest_ratio_category cat where sec.categoryid = cat.categoryid and cat.ratiocd = ratio.ratiocd and ratio.ratiocd=(select max(ratiocd)from invest_ratio) order by categorycd";
			pst = con.prepareStatement(catQuery);
			rs=pst.executeQuery();
			while(rs.next()){
				ratoList.add(rs.getString("CATEGORYID"));
				ratoListCd.add(rs.getString("categorycd"));
				}
			if(rs!=null)
				rs.close();
			if(pst!=null)
				pst.close();
			//System.out.println("ANil222222222222222222222222"+cnt);
			int listSize=ratoList.size();
			String totQry="select ' Fund Invested' as SECURITY_NAME,(select sum(INVESTMENT_FACEVALUE) from invest_register) ATOT_AMT";
			String totQry1="select ' Fund Invested' as SECURITY_NAME,(select sum(SETTLEMENT_AMOUNT) from investment_register) ATOT_AMT";
			String query="select SECURITY_NAME,sum(0) as ATOT_AMT";
			String query1="select SECURITY_NAME,sum(0) as ATOT_AMT";
			for(int i=1;i<listSize;i++){
				totQry+=",(select sum(INVESTMENT_FACEVALUE) from invest_register where CATEGORYID='"+ratoList.get(i)+"') A"+ratoList.get(i)+"";
				totQry1+=",(select sum(SETTLEMENT_AMOUNT) from investment_register where CATEGORYCD='"+ratoListCd.get(i)+"') A"+ratoListCd.get(i)+"";
				query+=",(select sum(INVESTMENT_FACEVALUE) from invest_register where CATEGORYID='"+ratoList.get(i)+"' and SECURITY_NAME=mt.SECURITY_NAME) A"+ratoList.get(i)+"";
				query1+=",(select sum(SETTLEMENT_AMOUNT) from investment_register where CATEGORYCD='"+ratoListCd.get(i)+"' and SECURITY_NAME=mt.SECURITY_NAME) A"+ratoListCd.get(i)+"";
				}
			totQry+=" from invest_register mt where DELSTATUS='N' and CATEGORYID is not null";
			totQry1+=" from investment_register mt where REGISTER_CD is not null and CATEGORYCD is not null";
			query+=" from invest_register mt where DELSTATUS='N' and CATEGORYID is not null ";
			query1+=" from investment_register mt where REGISTER_CD is not null and CATEGORYCD is not null ";
			if(!security.equals("")){
				totQry+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
				totQry1+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
				query+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
				query1+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
			}

			if(!trust.equals("")){
				totQry+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
				totQry1+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
				query+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
				query1+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
			}

			if(!fromDate.equals("")){
				totQry+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
				totQry1+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
				query+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
				query1+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
			}

			if(!toDate.equals("")){
				totQry+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
				totQry1+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
				query+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
				query1+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
			}
			
			query+=" group by SECURITY_NAME";
			query1+=" group by SECURITY_NAME";
			String sqlQry="SELECT * FROM (("+totQry+") UNION ("+totQry1+") UNION ("+query+") UNION ("+query1+")) ORDER BY SECURITY_NAME";
			System.out.println("hi query::"+sqlQry);
			pst = con.prepareStatement(sqlQry);
			rs=pst.executeQuery();
			//System.out.println("hi query:11111111111:");
			while(rs.next())
			 {
				String key=rs.getString(1);
				list=new ArrayList();
				for(int i=0;i<listSize;i++){
					bean=new Bean();
					bean.setCode(rs.getString("A"+(String)ratoList.get(i)));
					list.add(bean);
				 }
				map.put(key,list);
			  }
		
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("RatioDAO:searchRatio():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return map;
	}
public List getYTMInvest(InvestmentReportsBean ibean) throws Exception {
	List list =new ArrayList();
	try{ 
		QuotationBean qbean =null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		String query = "select security_name,interest_rate,categorycd,facevalue,to_char(invest_date,'dd/Mon/yyyy')invest_date,ytmpercentage from invest_register reg,invest_sec_category sec where DELSTATUS='N' and reg.categoryid=sec.categoryid and REGISTERCD is not null";
		String query1="select security_name,ss.cupon_rate interest_rate,ss.categorycd,facevalue,to_char(SETTLEMENT_DATE, 'dd/Mon/yyyy') invest_date,0 ytmpercentage  from investment_register ss, invest_sec_category sec where  ss.categorycd= sec.categorycd and REGISTER_CD is not null";
		if(!"".equals(ibean.getTrustType()))
		{
			query+=" and TRUSTTYPE = '"+ibean.getTrustType()+"'";
			query1+=" and TRUSTTYPE = '"+ibean.getTrustType()+"'";
		}
		     
		
		if(!ibean.getFromDate().equals(""))
		{
			query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) >=  '"+ibean.getFromDate()+"' ";
			query1+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) >=  '"+ibean.getFromDate()+"' ";
		}
		if(!ibean.getToDate().equals(""))
		{
			query+=" and to_date(to_char(INVEST_DATE,'DD/MON/YYYY')) <=  '"+ibean.getToDate()+"' ";
			query1+=" and to_date(to_char(SETTLEMENT_DATE,'DD/MON/YYYY')) <=  '"+ibean.getToDate()+"' ";
		}
		query=query+" union "+query1;
		
		//log.info("InvestmentReportDAO:getYTMInvest(): "+query);	
		PreparedStatement ps = con.prepareStatement(query);
		rs=ps.executeQuery();
		while(rs.next()){
			qbean = new QuotationBean();
			qbean.setSecurityName(rs.getString("security_name"));
			qbean.setInterestRate(rs.getString("interest_rate"));
			qbean.setSecurityCategory(rs.getString("categorycd"));
			qbean.setFaceValue(rs.getString("facevalue"));
			qbean.setInvestmentdate(rs.getString("invest_date"));
			qbean.setYtm(rs.getString("ytmpercentage"));
			list.add(qbean);
		}
		
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("InvestmentReportDAO:getYTMInvest():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return list;
}
public List getRedumptionBonds(InvestmentReportsBean ibean) throws Exception {
	List list =new ArrayList();
	try{ 
		QuotationBean qbean =null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		String fromDate = "",toDate="";
		StringTokenizer st = new StringTokenizer(ibean.getDate(),"-");
		while(st.hasMoreTokens())
		{
			fromDate=st.nextToken();
			toDate = st.nextToken();
		}
		String query = "select security_name,facevalue,to_char(reg.redemptiondate,'dd/Mon/yyyy') redemptiondate, sum(det.credit) - sum(det.debit)redumptionamt,max(to_char(info.voucher_dt,'dd/Mon/yyyy'))recvdredumptiondate from invest_register reg, cb_voucher_info info, voucher_details det where DELSTATUS='N' and vouchertype = 'R' and info.keyno = det.keyno and PARTYTYPE like 'P' and APPROVAL = 'Y' and security_name = EMP_PARTY_CODE ";
		String query1 = "select security_name,facevalue,to_char(reg.MATURITY_DATE, 'dd/Mon/yyyy') redemptiondate,sum(det.credit) - sum(det.debit) redumptionamt,max(to_char(info.voucher_dt, 'dd/Mon/yyyy')) recvdredumptiondate from investment_register reg,cb_voucher_info info,voucher_details det where REGISTER_CD is not null and vouchertype = 'R' and  info.keyno = det.keyno and PARTYTYPE like 'P' and APPROVAL = 'Y' and security_name = EMP_PARTY_CODE ";
		if(!"".equals(ibean.getTrustType()))
		{
			query+=" and reg.TRUSTTYPE = '"+ibean.getTrustType()+"'";
			query1+=" and reg.TRUSTTYPE = '"+ibean.getTrustType()+"'";
		}
		if(!ibean.getDate().equals(""))
		{
			query+=" and to_char(invest_date, 'Mon/yyyy') between 'Apr/"+fromDate+"' and 'Mar/"+toDate+"' and to_char(info.voucher_dt, 'Mon/yyyy') between 'Apr/"+fromDate+"' and'Mar/"+toDate+"' ";
			
			query1+=" and to_char(invest_date, 'Mon/yyyy') between 'Apr/"+fromDate+"' and 'Mar/"+toDate+"' and to_char(info.voucher_dt, 'Mon/yyyy') between 'Apr/"+fromDate+"' and'Mar/"+toDate+"' ";
			
		}
		 query+=" group by security_name, facevalue, reg.redemptiondate, EMP_PARTY_CODE";
		 query1+=" group by security_name, facevalue, reg.MATURITY_DATE, EMP_PARTY_CODE";
		 
		 query=query+" union "+query1;
		 
		//log.info("InvestmentReportDAO:trustTotalInvestment(): "+query);	
		PreparedStatement ps = con.prepareStatement(query);
		rs=ps.executeQuery();
		while(rs.next()){
			qbean = new QuotationBean();
			qbean.setSecurityName(rs.getString("security_name"));;
			qbean.setFaceValue(rs.getString("facevalue"));
			qbean.setRedemptionDate(rs.getString("redemptiondate"));
			qbean.setAmountRceived(rs.getString("redumptionamt"));
			qbean.setRedumptionDueDate(rs.getString("recvdredumptiondate"));
			list.add(qbean);
		}
		
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("InvestmentReportDAO:getRedumptionBonds():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return list;
}
public List trustTotalInvestment(InvestmentReportsBean ibean) throws Exception {
	List list =new ArrayList();
	try{ 
		QuotationBean qbean =null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		float total=0,investtotal=0;
		String query = "select sec.categorycd categorycd,reg.purchaeprice purchaeprice,(select acc.amount from accountcode_details acc where DELSTATUS='N' and reg.trusttype = acc.trusttype and sec.openingbal = acc.accounthead) openingamount,(select sum(credit - debit) from voucher_details det, cb_voucher_info info where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and info.vouchertype = 'R' and info.partytype = 'P' and sec.investbaseamt = det.accounthead) meturedamount from invest_register reg, invest_sec_category sec where sec.categoryid = reg.categoryid ";
		String query1="select sec.categorycd categorycd,reg.purchase_price purchaeprice,(select acc.amount from accountcode_details acc where reg.register_cd is not null and reg.trusttype = acc.trusttype and sec.openingbal = acc.accounthead) openingamount,(select sum(credit - debit) from voucher_details det, cb_voucher_info info where info.keyno = det.keyno and trim(info.trusttype) = reg.trusttype and info.vouchertype = 'R' and info.partytype = 'P' and sec.investbaseamt = det.accounthead) meturedamount from investment_register reg, invest_sec_category sec where sec.categorycd = reg.categorycd";
		if(!"".equals(ibean.getTrustType()))
		{
			query+=" and reg.trusttype = '"+ibean.getTrustType()+"'";
			query1+=" and reg.trusttype = '"+ibean.getTrustType()+"'";
		}
		
		if(!ibean.getDate().equals(""))
		{
			query+=" and reg.invest_date <= '"+ibean.getDate()+"'";
			query1+=" and reg.invest_date <= '"+ibean.getDate()+"'";
		}
		query=query+" union "+query1;
		log.info("InvestmentReportDAO:trustTotalInvestment(): "+query);	
		PreparedStatement ps = con.prepareStatement(query);
		rs=ps.executeQuery();
		while(rs.next()){
			qbean = new QuotationBean();
			qbean.setSecurityCategory(rs.getString("categorycd"));;
			qbean.setOpeningbal(rs.getString("openingamount"));
			qbean.setPurchasePrice(rs.getString("purchaeprice"));
			total= rs.getFloat("purchaeprice")+rs.getFloat("openingamount");
			qbean.setAmountRceived(String.valueOf(total));
			qbean.setBaseamount(rs.getString("meturedamount"));
			investtotal=total-rs.getFloat("meturedamount");
			qbean.setInvestmentFaceValue(String.valueOf(investtotal));
			list.add(qbean);
		}
		
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("InvestmentReportDAO:trustTotalInvestment():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return list;
}
public List getInterestDetails(String year,String security,String trust,String fromDate,String toDate) throws ServiceNotAvailableException, EPISException{
	List list =new ArrayList();
	Connection connection=null;
	ResultSet reusltSet=null;
	Statement stmt=null;	
	QuotationBean qbean = new QuotationBean();
	try{	
		String searchQry="SELECT nvl(FACEVALUE,'0') FACEVALUE,INTEREST_RATE,SECURITY_NAME,to_char(INVEST_DATE,'dd/Mon/YYYY') INVEST_DATE,to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon') INTEREST_DATE,REMARKS,INVESTMENTMODE,(select sum(credit)-sum(debit) from cb_voucher_info info,voucher_details det where info.keyno=det.keyno and vouchertype='R' and ACCOUNTHEAD=(select INVESTINTEREST from invest_sec_category dt where dt.CATEGORYCD=CATEGORYCD and rownum=1)) int_rec from invest_register where DELSTATUS='N'";
		String searchQry1="SELECT nvl(FACEVALUE, '0') FACEVALUE,cupon_rate INTEREST_RATE,SECURITY_NAME,to_char(SETTLEMENT_DATE, 'dd/Mon/YYYY') INVEST_DATE,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'), 'dd/Mon'),'')||','||nvl(to_char(to_date(INTEREST_DATE1,'dd/MM') ,'dd/Mon'),'') INTEREST_DATE,'' REMARKS,modeof_interest INVESTMENTMODE,(select sum(credit) - sum(debit) from cb_voucher_info info, voucher_details det  where info.keyno = det.keyno and vouchertype = 'R' and ACCOUNTHEAD =(select INVESTINTEREST from invest_sec_category dt  where dt.CATEGORYCD = CATEGORYCD and rownum = 1)) int_rec from investment_register ss where REGISTER_CD is not null";
		
		if(!year.equals(""))
		{
			searchQry+=" and nvl(to_char(INVEST_DATE,'YYYY'),' ') like upper(nvl('"+year+"',''))";
			searchQry1+=" and nvl(to_char(SETTLEMENT_DATE,'YYYY'),' ') like upper(nvl('"+year+"',''))";
		}

		if(!security.equals(""))
		{
			searchQry+=" and upper(SECURITY_NAME) like upper('%"+security+"%')";
			searchQry1+=" and upper(SECURITY_NAME) like upper('%"+security+"%')";
		}

		if(!trust.equals(""))
		{
			searchQry+=" and upper(TRUSTTYPE) like upper('%"+trust+"%')";
			searchQry1+=" and upper(TRUSTTYPE) like upper('%"+trust+"%')";
		}

		if(!fromDate.equals(""))
		{
			searchQry+=" and to_date(INTEREST_DATE,'DD/MM') >=  '"+fromDate+"' ";
			searchQry1+=" and to_date(SETTLEMENT_DATE,'DD/MM') >=  '"+fromDate+"' ";
		}

		if(!toDate.equals(""))
		{
			searchQry+=" and to_date(INTEREST_DATE,'DD/MM') <=  '"+toDate+"' ";
			searchQry1+=" and to_date(SETTLEMENT_DATE,'DD/MM') <=  '"+toDate+"' ";
		}
		searchQry=searchQry+" union "+searchQry1;
		//log.info("hi report qry "+searchQry);
		
		connection = DBUtility.getConnection();
		if(connection!=null){
			stmt = connection.createStatement();	
			if(stmt!=null){
						reusltSet = stmt.executeQuery(searchQry);
				while(reusltSet.next()){
					qbean = new QuotationBean();
					qbean.setFaceValue(StringUtility.checknull(reusltSet.getString("FACEVALUE")));
					qbean.setInvestmentdate(StringUtility.checknull(reusltSet.getString("INVEST_DATE")));
					qbean.setInterestDate(StringUtility.checknull(reusltSet.getString("INTEREST_DATE")));
					qbean.setInterestRate(StringUtility.checknull(reusltSet.getString("Interest_Rate")));
					qbean.setInvestmentMode(StringUtility.checknull(reusltSet.getString("investmentmode")));
					qbean.setRemarks(StringUtility.checknull(reusltSet.getString("remarks")));
					qbean.setSecurityName(StringUtility.checknull(reusltSet.getString("SECURITY_NAME")));
					qbean.setInvestmentFaceValue(StringUtility.checknull(reusltSet.getString("int_rec")));
					double amtDiff=reusltSet.getDouble("FACEVALUE")-reusltSet.getDouble("int_rec");
					qbean.setAmt(amtDiff);
					list.add(qbean);
					
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		}else{
			throw new ServiceNotAvailableException();
		}
	}catch(ServiceNotAvailableException snex){
		throw snex;
	}catch(SQLException sqlex){			
		log.error("InvestmentReportDAO:getInterestDetails:SQLException:"+sqlex.getMessage());
		throw  new EPISException(sqlex);
	}catch(Exception e){
		
		log.error("InvestmentReportDAO:getInterestDetails:Exception:"+e.getMessage());
		throw new EPISException(e);
	}finally {			
			DBUtility.closeConnection(reusltSet,stmt,connection);			
	}
	
	return list;
	
}

public Map getFileDetails(String security,String trust,String fromDate,String toDate) throws Exception {
	
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Statement st = null;
	
	LinkedHashMap map = new LinkedHashMap();
	ArrayList list=new ArrayList();
	ArrayList childList=new ArrayList();
	ArrayList ratoList=new ArrayList();
	ArrayList ratoListCd=new ArrayList();
	Bean bean=new Bean();
	QuotationBean qbean = new QuotationBean();
	try{
		con = DBUtility.getConnection();
		//System.out.println("ANil111111111111111"+catCT);
		
		String catQuery ="select categorycd,cat.categoryid categoryid, cat.percentage percentage from invest_sec_category sec,invest_ratio ratio,invest_ratio_category cat where sec.categoryid = cat.categoryid and cat.ratiocd = ratio.ratiocd and ratio.ratiocd=(select max(ratiocd)from invest_ratio) order by categorycd";
		pst = con.prepareStatement(catQuery);
		rs=pst.executeQuery();
		while(rs.next()){
			ratoList.add(rs.getString("CATEGORYID"));
			ratoListCd.add(rs.getString("categorycd"));
			}
		
		if(rs!=null)
			rs.close();
		if(pst!=null)
			pst.close();
		//System.out.println("ANil222222222222222222222222"+cnt);
		int listSize=ratoList.size();
		
		String query="select SECURITY_NAME,INTEREST_RATE,to_char(INVEST_DATE,'dd/Mon/YYYY') INVEST_DATE,(select sum(INVESTMENT_FACEVALUE) from invest_register where SECURITY_NAME=mt.SECURITY_NAME) ATOTAMT,REMARKS";
		String query1="select SECURITY_NAME,CUPON_RATE INTEREST_RATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY') INVEST_DATE,(select sum(SETTLEMENT_AMOUNT) from investment_register where SECURITY_NAME=mt.SECURITY_NAME) ATOTAMT,'' REMARKS";
		for(int i=0;i<listSize;i++)
		{
			query+=",(select sum(INVESTMENT_FACEVALUE) from invest_register where CATEGORYID='"+ratoList.get(i)+"' and SECURITY_NAME=mt.SECURITY_NAME) A"+ratoList.get(i)+"";
			query1+=",(select sum(SETTLEMENT_AMOUNT) from investment_register where CATEGORYCD='"+ratoListCd.get(i)+"' and SECURITY_NAME=mt.SECURITY_NAME) A"+ratoList.get(i)+"";
		}
			
		
		query+=" from invest_register mt where DELSTATUS='N' and CATEGORYID is not null ";
		query1+=" from investment_register mt where REGISTER_CD is not null and CATEGORYCD is not null ";
		if(!security.equals(""))
		{
			query+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
			query1+=" and upper(mt.SECURITY_NAME) like upper('%"+security+"%')";
		}
		

		if(!trust.equals(""))
		{
			query+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
			query1+=" and upper(mt.TRUSTTYPE) like upper('%"+trust+"%')";
		}
		

		if(!fromDate.equals(""))
		{
			query+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
			query1+=" and to_date(to_char(mt.SETTLEMENT_DATE,'DD/MON/YYYY')) >=  '"+fromDate+"' ";
		}
		

		if(!toDate.equals(""))
		{
			query+=" and to_date(to_char(mt.INVEST_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
			query1+=" and to_date(to_char(mt.SETTLEMENT_DATE,'DD/MON/YYYY')) <=  '"+toDate+"' ";
		}
		
		
		query=query+" union "+query1;
		ratoList.add("TOTAMT");
		listSize=ratoList.size();
		//System.out.println("hi query::"+query);
		pst = con.prepareStatement(query);
		rs=pst.executeQuery();
		while(rs.next()){
			list=new ArrayList();
			qbean = new QuotationBean();
			qbean.setInvestmentdate(StringUtility.checknull(rs.getString("INVEST_DATE")));
			qbean.setInterestRate(StringUtility.checknull(rs.getString("Interest_Rate")));
			qbean.setRemarks(StringUtility.checknull(rs.getString("remarks")));
			qbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
		 list.add(qbean);
		 childList=new ArrayList();
			for(int i=0;i<listSize;i++){
				bean=new Bean();
				bean.setCode(rs.getString("A"+(String)ratoList.get(i)));
				childList.add(bean);
			 }
			map.put(list,childList);
		  }
	
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("RatioDAO:searchRatio():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return map;
}

public int getGroupCount(String groupName)throws Exception
{
	Connection con = null;
	ResultSet rs = null;
	int recordCount=0;
	Statement st = null;
	
	try{
		con = DBUtility.getConnection();
		String countQuery="select nvl(INVEST_CASH_PARTYNAME,partyname) partyname from cb_party_info where group_name='"+groupName+"' and module_type='I'";
		st=con.createStatement();
		rs=st.executeQuery(countQuery);
		String checkPartyname="";
		while(rs.next())
		{
			if(!checkPartyname.equals(rs.getString("partyname")))
			{
			recordCount++;
			}
			checkPartyname=rs.getString("partyname");
		}
	}
	catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("RatioDAO:getGroupCount():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	
}
	return recordCount;
}
public int checkGroupcount(String groupName)throws Exception
{
	Connection con = null;
	ResultSet rs = null;
	int recordCount=0;
	Statement st = null;
	
	
	try{
		
		recordCount=DBUtility.getRecordCount("select count(*) from cb_party_info where trim(upper(group_name))= trim(upper('"+groupName+"'))");
	}
	catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("RatioDAO:getGroupCount():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	
}
	return recordCount;

	
}

	
}
	   

