package com.epis.dao.investment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CurrencyFormat;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class InvestmentRegisterDao {
	Log log=new Log(InvestmentRegisterDao.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private InvestmentRegisterDao()
	{
		
	}
	private static final InvestmentRegisterDao investmentregisterdao=new InvestmentRegisterDao();
	public static InvestmentRegisterDao getInstance()
	{
		return investmentregisterdao;
	}
	public List searchInvestmentRegister(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		List list=new ArrayList();
		try{
			InvestmentRegisterBean registerbean=null;
			String selectQuery=null;
			con=DBUtility.getConnection();
			if(con!=null)
			{
			st=con.createStatement();
			log.info("the dao mode is..."+bean.getMode());
			if(bean.getMode().equals("psuprimary"))
			 selectQuery="select REGISTER_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,reg.CATEGORYCD,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE from investment_register reg,invest_proposal proposal  where REGISTER_CD is not null and reg.proposal_ref_no=proposal.ref_no and reg.CATEGORYCD = 'PSU' and proposal.market_type='P' ";
			else if(bean.getMode().equals("rbiauction"))
				selectQuery="select REGISTER_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,reg.CATEGORYCD,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,nvl(SURITY_FULLNAME,SECURITY_NAME)SECURITY_NAME,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE from investment_register reg,invest_proposal proposal  where REGISTER_CD is not null and reg.proposal_ref_no=proposal.ref_no AND reg.CATEGORYCD in('SDL','GOI') and proposal.market_type in('R','RB') ";
			else if(bean.getMode().equals("rbiauctionManual"))
				selectQuery="select REGISTER_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,reg.CATEGORYCD,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,nvl(SURITY_FULLNAME,SECURITY_NAME)SECURITY_NAME,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE from investment_register reg,invest_proposal proposal  where REGISTER_CD is not null and reg.proposal_ref_no=proposal.ref_no  ";
				
			else
				selectQuery="select REGISTER_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,reg.CATEGORYCD,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE from investment_register reg,invest_proposal proposal  where REGISTER_CD is not null and reg.proposal_ref_no=proposal.ref_no  and proposal.market_type not in('P','R','RB') ";
				
			if(!bean.getLetterNo().equals(""))
			{
				selectQuery +=" and upper(LETTER_NO) like upper('"+bean.getLetterNo()+"%')";
			}
			if(!bean.getRefNo().equals(""))
			{
				selectQuery +=" and upper(reg.PROPOSAL_REF_NO) like upper('"+bean.getRefNo()+"%')";
			}
			if(!bean.getSecurityCategory().equals(""))
			{
				selectQuery +=" and upper(reg.CATEGORYCD) like upper('"+bean.getSecurityCategory()+"%')";
			}
			if(!bean.getSecurityName().equals(""))
			{
				selectQuery +=" and upper(reg.SECURITY_NAME) like upper('"+bean.getSecurityName()+"%')";
			}
			selectQuery +=" order by reg.CREATED_DT DESC";
			System.out.println(":::::::::::selectQuery:"+selectQuery);
			rs=DBUtility.getRecordSet(selectQuery,st);
			while(rs.next())
			{
				registerbean=new InvestmentRegisterBean();
				registerbean.setRegisterCd(StringUtility.checknull(rs.getString("REGISTER_CD")));
				registerbean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
				registerbean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
				registerbean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
				registerbean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
				registerbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
				registerbean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
				registerbean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
				
				list.add(registerbean);
			}
			
			}
			else
			{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	
	}
	public List getLetterNumbers(String mode)throws EPISException,ServiceNotAvailableException
	{
			List list=new ArrayList();
			try{
				InvestmentRegisterBean registerBean=null;
				String selectQuery=null;
				con=DBUtility.getConnection();
				if(con!=null)
				{
					st=con.createStatement();
					if(mode.equals("psuprimary"))
					 selectQuery="Select LETTER_NO LETTERNO,LETTER_NO||'-'||company.SECURITY_NAME securityname,LETTER_NO from invest_confirmation_company company,invest_formfilling form where company.APPROVED_4 = 'A' and((company.approved_5='A' and company.APPROVED_6='A')or(company.approved_6='A' and company.APPROVED_7='A') or(company.approved_5='A' and company.APPROVED_7='A')) and company.PROPOSAL_REF_NO=form.PROPOSAL_REF_NO and company.REGFLAG is null and company.categorycd='PSU' AND COMPANY.MARKET_TYPE='P'";
					else if(mode.equals("rbiauction"))
						selectQuery="Select company.LETTER_NO LETTERNO,company.LETTER_NO||'-'||company.SECURITY_NAME||'-'||company.SURITY_FULLNAME securityname from invest_confirmation_company company,invest_bankletter letter where company.APPROVED_4 = 'A' and((company.approved_5='A' and company.APPROVED_6='A')or(company.approved_6='A' and company.APPROVED_7='A') or(company.approved_5='A' and company.APPROVED_7='A')) and company.PROPOSAL_REF_NO||company.security_name||company.SURITY_FULLNAME=letter.PROPOSAL_REF_NO||letter.security_name||letter.SURITY_FULLNAME  and LETTER.REGFLAGE is null and company.categorycd<>'PSU' AND COMPANY.MARKET_TYPE<>'P' and letter.categorycd<>'PSU' AND letter.market_type<>'P'";
						
					rs=DBUtility.getRecordSet(selectQuery,st);
					while(rs.next())
					{
						registerBean=new InvestmentRegisterBean();
						registerBean.setLetterNo(StringUtility.checknull(rs.getString("LETTERNO")));
						registerBean.setSecurityName(StringUtility.checknull(rs.getString("securityname")));
						list.add(registerBean);
					}
				}
				else
				{
					throw new ServiceNotAvailableException();
				}
			}
			catch(ServiceNotAvailableException snex){
				throw snex;
			}catch(SQLException sqlex){			
				log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
				throw  new EPISException(sqlex);
			}catch(Exception e){
				
				log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs,st,con);			
			}
			log.info("the size"+list.size());
		return list;
	}
	public InvestmentRegisterBean getRegisterDetails(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentRegisterBean registerBean=new InvestmentRegisterBean();
		String selectQuery="";
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				if(bean.getMode().equals("rbiauction"))
					selectQuery="SELECT company.SECURITY_NAME,company.SURITY_FULLNAME securityfullname,LETTER_NO,company.PROPOSAL_REF_NO,to_char(proposal.proposaldate,'dd/Mon/yyyy')proposaldate,company.TRUSTTYPE,company.CATEGORYCD,company.AMT_INV,company.NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid Per Unit','D','Discount Per Unit')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,INTEREST_DATE,nvl(INTEREST_DATE1,'')INTEREST_DATE1,PURCHASE_PRICE,(select nvl(SETTLEMENT_AMOUNT,0) from invest_bankletter bankletter where bankletter.proposal_ref_no||bankletter.security_name||bankletter.surity_fullname = company.PROPOSAL_REF_NO||company.security_name||company.surity_fullname)settlementamount FROM invest_confirmation_company company,invest_formfilling form,invest_proposal proposal  where company.PROPOSAL_REF_NO||company.security_name = form.PROPOSAL_REF_NO||form.security_name and proposal.ref_no=company.proposal_ref_no and LETTER_NO='"+bean.getLetterNo()+"'";
				 
				else if(bean.getMode().equals("psuprimary"))
					selectQuery="SELECT company.SECURITY_NAME,LETTER_NO,company.PROPOSAL_REF_NO,to_char(proposal.proposaldate,'dd/Mon/yyyy')proposaldate,company.TRUSTTYPE,company.CATEGORYCD,company.AMT_INV,company.NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid Per Unit','D','Discount Per Unit')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,INTEREST_DATE,nvl(INTEREST_DATE1,'')INTEREST_DATE1,PURCHASE_PRICE,(select nvl(SETTLEMENT_AMOUNT,0) from invest_bankletter where proposal_ref_no=company.PROPOSAL_REF_NO)settlementamount FROM invest_confirmation_company company,invest_formfilling form,invest_proposal proposal  where company.PROPOSAL_REF_NO=form.PROPOSAL_REF_NO and proposal.ref_no=company.proposal_ref_no and LETTER_NO='"+bean.getLetterNo()+"'";
					
					
				log.info("the select query is..."+selectQuery);
				rs=DBUtility.getRecordSet(selectQuery,st);
				if(rs.next())
				{
					registerBean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					registerBean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
					registerBean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
					registerBean.setInvestdate(StringUtility.checknull(rs.getString("PROPOSALDATE")));
					registerBean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
					registerBean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
					registerBean.setAmountInv(StringUtility.checknull(rs.getString("AMT_INV")));
					registerBean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
					registerBean.setConfirm(StringUtility.checknull(rs.getString("CONFIRMATION")));
					registerBean.setConfirmationDef(StringUtility.checknull(rs.getString("CONFIRMATIONDEF")));
					registerBean.setDealDate(StringUtility.checknull(rs.getString("DEALDATE")));
					registerBean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
					registerBean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
					registerBean.setCuponRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
					registerBean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
					registerBean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
					registerBean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
					registerBean.setPurchaseOptionDef(StringUtility.checknull(rs.getString("PURCHASE_OPTIONDEF")));
					registerBean.setOfferedPrice(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
					registerBean.setYtmValue(StringUtility.checknull(rs.getString("YTM_VALUE")));
					registerBean.setCallOption(StringUtility.checknull(rs.getString("CALL_OPTION")));
					registerBean.setCallOptinDef(StringUtility.checknull(rs.getString("CALLOPTIONDEF")));
					registerBean.setCallDate(StringUtility.checknull(rs.getString("CALL_DATE")));
					registerBean.setModeOfInterest(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
					registerBean.setModeOfInterestDef(StringUtility.checknull(rs.getString("MODEOFINTERESTDEF")));
					registerBean.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
					registerBean.setInterestDate1(StringUtility.checknull(rs.getString("INTEREST_DATE1")));
					registerBean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
					registerBean.setSettlementAmt(StringUtility.checknull(rs.getString("settlementamount")));
					registerBean.setMode(bean.getMode());
					if(bean.getMode().equals("rbiauction"))
						registerBean.setSecurityFullName(StringUtility.checknull(rs.getString("securityfullname")));
						
					
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}
		
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
		return registerBean;
	}
	public void addInvestmentRegister(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		String registerCd=null;
		try
		{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				log.info("the mode is insert investment register"+bean.getMode());
				registerCd =AutoGeneration.getNextCode("investment_register","REGISTER_CD",5,con);
				String insertQuery="insert into investment_register(REGISTER_CD,LETTER_NO ,SECURITY_NAME ,SURITY_FULLNAME,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,AMT_INV,NO_OF_BONDS,CONFIRMATION," +
						"DEALDATE,SETTLEMENT_DATE,MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,PURCHASE_OPTION,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,CALL_DATE,MODEOF_INTEREST,INTEREST_DATE,INTEREST_DATE1,PURCHASE_PRICE,CREATED_BY,CREATED_DT,REGISTER_ACCEPTED,FACEVALUE,INVEST_DATE,SETTLEMENT_AMOUNT) " +
						" values('"+registerCd+"','"+StringUtility.checknull(bean.getLetterNo())+"','"+StringUtility.checknull(bean.getSecurityName())+"','"+StringUtility.checknull(bean.getSecurityFullName())+"','"+StringUtility.checknull(bean.getRefNo())+"','"+StringUtility.checknull(bean.getTrustType())+"','"+StringUtility.checknull(bean.getSecurityCategory())+"','"+StringUtility.checknull(bean.getAmountInv())+"','"+StringUtility.checknull(bean.getNoofBonds())+"','"+StringUtility.checknull(bean.getConfirm())+"','"+StringUtility.checknull(bean.getDealDate())+"','"+StringUtility.checknull(bean.getSettlementDate())+"','"+StringUtility.checknull(bean.getMaturityDate())+"','"+StringUtility.checknull(bean.getCuponRate())+"','"+StringUtility.checknull(bean.getPremiumPaid())+"','"+StringUtility.checknull(bean.getPurchaseOption())+"','"+StringUtility.checknull(bean.getOfferedPrice())+"','"+StringUtility.checknull(bean.getYtmValue())+"','"+StringUtility.checknull(bean.getCallOption())+"','"+StringUtility.checknull(bean.getCallDate())+"','"+StringUtility.checknull(bean.getModeOfInterest())+"','"+StringUtility.checknull(bean.getInterestDate())+"','"+StringUtility.checknull(bean.getInterestDate1())+"','"+StringUtility.checknull(bean.getPurchasePrice())+"','"+StringUtility.checknull(bean.getLoginUserId())+"',sysdate,'"+StringUtility.checknull(bean.getAcceptence())+"','"+StringUtility.checknull(bean.getFaceValue())+"','"+StringUtility.checknull(bean.getInvestdate())+"','"+StringUtility.checknull(bean.getSettlementAmt())+"') ";
				String updateQuery="update invest_confirmation_company set REGFLAG='Y' where LETTER_NO='"+StringUtility.checknull(bean.getLetterNo())+"'";
				
				
				
				DBUtility.setAutoCommit(false,con);
				
			DBUtility.executeUpdate(insertQuery);
			DBUtility.executeUpdate(updateQuery);
			if(bean.getMode().equals("rbiauction"))
			{
				log.info("this is calling.... fromrbi auction...");
				String letterUpdate="update invest_bankletter set REGFLAGE='Y' where PROPOSAL_REF_NO='"+StringUtility.checknull(bean.getRefNo())+"' and SECURITY_NAME='"+StringUtility.checknull(bean.getSecurityName())+"' and SURITY_FULLNAME='"+StringUtility.checknull(bean.getSecurityFullName())+"' ";
				DBUtility.executeUpdate(letterUpdate);
			}
			
			DBUtility.setAutoCommit(true,con);
			
			
			}
			else{
				
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){	
			try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
	}
public void addInvestmentRegisterManual(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
{
	
	
	String registerCd=null;
	String interestDate="";
	String interestDate1="";
	String letterNo="";
	String trustname="";
	String companyCd="";
	String dealDate="";
	String settlementDate="";
	String maturityDate="";
	String sssDate="";
	try
	{
		
		con=DBUtility.getConnection();
		if(con!=null)
		{
			if(!StringUtility.checknull(bean.getInterestDate()).equals("")) {
				interestDate=bean.getInterestDate()+"/"+bean.getInterestMonth();
				
			}
			log.info("the monthand date is..."+bean.getInterestDate()+"----"+bean.getInterestMonth());
			log.info("the first interestdate ..."+interestDate);
			if(!StringUtility.checknull(bean.getInterestDate1()).equals("")) {
					interestDate1=bean.getInterestDate1()+"/"+bean.getInterestMonth1();
					
			}
			
				com.epis.utilities.DateValidation datevalidation=new com.epis.utilities.DateValidation();
				String year=datevalidation.getLetterYear(bean.getDealDate());
				letterNo=AutoGeneration.getNextCodeGBy("invest_confirmation_company","LETTER_NO",23,"FA/"+bean.getSecurityCategory()+"/INVEST/"+year,con);
				companyCd =AutoGeneration.getNextCode("invest_confirmation_company","COMPANY_CD",5,con);
			st=con.createStatement();
			
			ResultSet rs=DBUtility.getRecordSet("select TRUSTTYPE from invest_trusttype where TRUSTCD='"+bean.getTrustType()+"'",st);
			if(rs.next())
				trustname=rs.getString(1);
			log.info("the trust type is..."+trustname);
			
			if(rs!=null)
			{
				rs.close();
			}
			
			
			log.info("the mode is insert investment register"+bean.getMode());
			registerCd =AutoGeneration.getNextCode("investment_register","REGISTER_CD",5,con);
			String proposalQuery="insert into invest_proposal(REF_NO,TRUSTCD,CATEGORYCD,AMT_INV,CREATED_BY,CREATED_DT,PROPOSALDATE,MARKET_TYPE,MODULE_FLAG) " +
					"values('"+StringUtility.checknull(bean.getRefNo())+"','"+StringUtility.checknull(bean.getTrustType())+"','"+StringUtility.checknull(bean.getSecurityCategory())+"','"+StringUtility.checknull(bean.getAmountInv())+"','"+StringUtility.checknull(bean.getLoginUserId())+"',sysdate,'"+StringUtility.checknull(bean.getDealDate())+"','"+StringUtility.checknull(bean.getMarketType())+"','M')";
			
			
			System.out.println("--p--"+proposalQuery);
			String Savequery="INSERT INTO invest_confirmation_company(COMPANY_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,MARKET_TYPE,AMT_INV,NO_OF_BONDS,CONFIRMATION,DEALDATE,SETTLEMENT_DATE,MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION," +
					"OFFERED_PRICE,YTM_VALUE,CALL_OPTION,CALL_DATE,MODEOF_INTEREST,INTEREST_DATE,INTEREST_DATE1,PURCHASE_PRICE," +
					"CREATED_BY,SECURITY_NAME,CREATED_DT,MODULE_FLAG) " +
					"values('"+StringUtility.checknull(companyCd)+"','"+StringUtility.checknull(letterNo)+"','"+StringUtility.checknull(bean.getRefNo())+"','"+StringUtility.checknull(trustname)+"','"+StringUtility.checknull(bean.getSecurityCategory())+"','"+StringUtility.checknull(bean.getMarketType())+"'," +
							"'"+StringUtility.checknull(bean.getAmountInv())+"','"+StringUtility.checknull(bean.getNoofBonds())+"'," +
									"'"+StringUtility.checknull(bean.getConfirm())+"','"+StringUtility.checknull(bean.getDealDate())+"','"+StringUtility.checknull(bean.getSettlementDate())+"','"+StringUtility.checknull(bean.getMaturityDate())+"','"+StringUtility.checknull(bean.getCuponRate())+"','"+StringUtility.checknull(bean.getPremiumPaid())+"','"+StringUtility.checknull(bean.getFaceValue())+"','"+StringUtility.checknull(bean.getPurchaseOption())+"'," +
											"'"+StringUtility.checknull(bean.getOfferedPrice())+"','"+StringUtility.checknull(bean.getYtmValue())+"'," +
													"'"+StringUtility.checknull(bean.getCallOption())+"','"+StringUtility.checknull(bean.getCallDate())+"'," +
															"'"+StringUtility.checknull(bean.getModeOfInterest())+"','"+StringUtility.checknull(interestDate)+"','"+StringUtility.checknull(interestDate1)+"','"+StringUtility.checknull(bean.getPurchasePrice())+"','"+StringUtility.checknull(bean.getLoginUserId())+"','"+StringUtility.checknull(bean.getSecurityName())+"',sysdate,'M')";
			
			
			System.out.println("----s-"+Savequery);
			String securityProposal=StringUtility.checknull(bean.getSecurityName())+"-"+StringUtility.checknull(bean.getRefNo()).toUpperCase();
			System.out.println("----s-"+bean.getArrangerName());
			String insertQuery="insert into investment_register(REGISTER_CD,LETTER_NO ,SECURITY_NAME ,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,AMT_INV,NO_OF_BONDS,CONFIRMATION," +
					"DEALDATE,SETTLEMENT_DATE,MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,PURCHASE_OPTION,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,CALL_DATE,MODEOF_INTEREST,INTEREST_DATE,INTEREST_DATE1,PURCHASE_PRICE,CREATED_BY,CREATED_DT,FACEVALUE,INVEST_DATE,MODULE_FLAG,facevalue_inrs,SECURITY_PROPOSAL,ACCURED_INTEREST_AMOUNT,ARRANGER_NAME,INCENTIVE_PAYABLE,INCENTIVE_TEXT,ISIN) " +
					" values('"+registerCd+"','"+StringUtility.checknull(letterNo)+"','"+StringUtility.checknull(bean.getSecurityName())+"','"+StringUtility.checknull(bean.getRefNo())+"','"+StringUtility.checknull(trustname)+"','"+StringUtility.checknull(bean.getSecurityCategory())+"','"+StringUtility.checknull(bean.getAmountInv())+"','"+StringUtility.checknull(bean.getNoofBonds())+"','"+StringUtility.checknull(bean.getConfirm())+"','"+StringUtility.checknull(bean.getDealDate())+"','"+StringUtility.checknull(bean.getSettlementDate())+"','"+StringUtility.checknull(bean.getMaturityDate())+"','"+StringUtility.checknull(bean.getCuponRate())+"','"+StringUtility.checknull(bean.getPremiumPaid())+"','"+StringUtility.checknull(bean.getPurchaseOption())+"','"+StringUtility.checknull(bean.getOfferedPrice())+"','"+StringUtility.checknull(bean.getYtmValue())+"','"+StringUtility.checknull(bean.getCallOption())+"','"+StringUtility.checknull(bean.getCallDate())+"','"+StringUtility.checknull(bean.getModeOfInterest())+"','"+StringUtility.checknull(interestDate)+"','"+StringUtility.checknull(interestDate1)+"','"+StringUtility.checknull(bean.getPurchasePrice())+"','"+StringUtility.checknull(bean.getLoginUserId())+"',sysdate,'"+StringUtility.checknull(bean.getFaceValue())+"',sysdate,'M','"+StringUtility.checknull(bean.getAmountInv())+"','"+securityProposal+"','"+StringUtility.checknull(bean.getAccuredInterestAmount())+"','"+StringUtility.checknull(bean.getArrangerName())+"','"+StringUtility.checknull(bean.getIncentivePayable())+"','"+StringUtility.checknull(bean.getIncentivetext())+"','"+StringUtility.checknull(bean.getISIN())+"') ";
			
			System.out.println("----i--"+insertQuery);
			DBUtility.setAutoCommit(false,con);
			
			DBUtility.executeUpdate(proposalQuery);
			
			DBUtility.executeUpdate(Savequery);
			
			
			DBUtility.executeUpdate(insertQuery);
		
		
		DBUtility.setAutoCommit(true,con);
		
		
		}
		else{
			
			throw new ServiceNotAvailableException();
		}
		
	}
	catch(ServiceNotAvailableException snex){
		throw snex;
	}catch(SQLException sqlex){	
		try {
			DBUtility.rollbackTrans(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
		throw  new EPISException(sqlex);
	}catch(Exception e){
		
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
		throw new EPISException(e);
	}finally {			
			DBUtility.closeConnection(rs,st,con);			
	}
}
public InvestmentRegisterBean generateInvestmentRegisterReport(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
{
	InvestmentRegisterBean registerBean=new InvestmentRegisterBean();
	try{
		con=DBUtility.getConnection();
		if(con!=null)
		{
			st=con.createStatement();
			String selectQuery="SELECT REGISTER_CD,LETTER_NO,decode(REGISTER_ACCEPTED,'Y','Accept','N','Reject')REGISTER_ACCEPTED,company.SECURITY_NAME,nvl(company.SURITY_FULLNAME,' ')SURITY_FULLNAME,LETTER_NO,company.PROPOSAL_REF_NO,company.TRUSTTYPE,company.CATEGORYCD,company.AMT_INV,company.NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid (Per Unit)','D','Discount Received (Per Unit)')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')INTEREST_DATE,nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'')INTEREST_DATE1,PURCHASE_PRICE,ISIN FROM investment_register company where REGISTER_CD='"+bean.getRegisterCd()+"'";
			System.out.println("sellllllllllll"+selectQuery);
			
			rs=DBUtility.getRecordSet(selectQuery,st);
			if(rs.next())
			{
				registerBean.setRegisterCd(StringUtility.checknull(rs.getString("REGISTER_CD")));
				registerBean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
				registerBean.setAcceptence(StringUtility.checknull(rs.getString("REGISTER_ACCEPTED")));
				registerBean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
				registerBean.setSecurityFullName(StringUtility.checknull(rs.getString("SURITY_FULLNAME")));
				registerBean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
				registerBean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
				registerBean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
				registerBean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
				registerBean.setAmountInv(StringUtility.checknull(rs.getString("AMT_INV")));
				registerBean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
				registerBean.setConfirm(StringUtility.checknull(rs.getString("CONFIRMATION")));
				registerBean.setConfirmationDef(StringUtility.checknull(rs.getString("CONFIRMATIONDEF")));
				registerBean.setDealDate(StringUtility.checknull(rs.getString("DEALDATE")));
				registerBean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
				registerBean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
				registerBean.setCuponRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
				registerBean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
				registerBean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
				registerBean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
				registerBean.setPurchaseOptionDef(StringUtility.checknull(rs.getString("PURCHASE_OPTIONDEF")));
				registerBean.setOfferedPrice(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
				registerBean.setYtmValue(StringUtility.checknull(rs.getString("YTM_VALUE")));
				registerBean.setCallOption(StringUtility.checknull(rs.getString("CALL_OPTION")));
				registerBean.setCallOptinDef(StringUtility.checknull(rs.getString("CALLOPTIONDEF")));
				registerBean.setCallDate(StringUtility.checknull(rs.getString("CALL_DATE")));
				registerBean.setModeOfInterest(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
				registerBean.setModeOfInterestDef(StringUtility.checknull(rs.getString("MODEOFINTERESTDEF")));
				registerBean.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
				registerBean.setInterestDate1(StringUtility.checknull(rs.getString("INTEREST_DATE1")));
				registerBean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
				registerBean.setISIN(StringUtility.checknull(rs.getString("ISIN")));
				
			}
		}
		else{
			throw new ServiceNotAvailableException();
		}
		
	}
	
	catch(ServiceNotAvailableException snex){
		throw snex;
	}catch(SQLException sqlex){			
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
		throw  new EPISException(sqlex);
	}catch(Exception e){
		
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
		throw new EPISException(e);
	}finally {			
			DBUtility.closeConnection(rs,st,con);			
	}
	
	return registerBean;
}
public int checksecurityProposalRefno(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException,Exception
{
	int recordcount=0;
	try{
		con=DBUtility.getConnection();
		if(con!=null)
		{
			recordcount=DBUtility.getRecordCount("select count(*) from investment_register where security_proposal='"+bean.getSecurityName()+"-"+bean.getRefNo()+"'");
			
		}
		else{
			throw new ServiceNotAvailableException();
		}
		
	}
	
	catch(ServiceNotAvailableException snex){
		throw snex;
	}catch(SQLException sqlex){			
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
		throw  new EPISException(sqlex);
	}catch(Exception e){
		
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
		throw new EPISException(e);
	}finally {			
			DBUtility.closeConnection(rs,st,con);			
	}
	
	
	return recordcount;
}
public int updateInvestmentISIN(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
{
	
	int n;
	try{
		con=DBUtility.getConnection();
		if(con!=null)
		{
			st=con.createStatement();
			String selectQuery="update investment_register set isin='"+bean.getISIN()+"' where REGISTER_CD='"+bean.getRegisterCd()+"'";
			System.out.println("sellllllllllll"+selectQuery);
			
			n=st.executeUpdate(selectQuery);
			
		}
		else{
			throw new ServiceNotAvailableException();
		}
		
	}
	
	catch(ServiceNotAvailableException snex){
		throw snex;
	}catch(SQLException sqlex){			
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:SQLException:"+sqlex.getMessage());
		throw  new EPISException(sqlex);
	}catch(Exception e){
		
		log.error("InvestmentRegisterDAO:searchInvestmentRegister:Exception:"+e.getMessage());
		throw new EPISException(e);
	}finally {			
			DBUtility.closeConnection(rs,st,con);			
	}
	
	return n;
}

}
