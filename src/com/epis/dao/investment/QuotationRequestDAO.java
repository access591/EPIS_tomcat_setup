package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.ArrangersBean;
import com.epis.bean.investment.QuotationRequestBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class QuotationRequestDAO {
	Connection con = null;
	ResultSet rs = null;
	ResultSet rsdt=null;
	Statement st = null;
	PreparedStatement pstmt=null;
	PreparedStatement pstmtdt=null;
	PreparedStatement pstmtnotes=null;
	
	Log log  = new Log(QuotationRequestDAO.class);
	private QuotationRequestDAO()
	{
		
	}
	private final static QuotationRequestDAO quotationRequestDao=new QuotationRequestDAO();
	public static QuotationRequestDAO getInstance()
	{
		return quotationRequestDao;
	}
	public QuotationRequestBean findQuotationRequest(String letterno)throws ServiceNotAvailableException,EPISException
	{
		QuotationRequestBean bean=null;
		try{	
			String searchQry="select PROPOSAL_REF_NO,TRUSTTYPE,QT.CATEGORYID,CATEGORYCD,SURPLUS_FUND,MARKET_TYPE,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','O','Open Bid','PS','Primary And Secondary') MARKET_TYPE1,nvl(APPROVED,' ')APPROVED,nvl(MODEOFPAYMENT_REMARKS,'')MODEOFPAYMENT_REMARKS,nvl(PAYMENTTHROUGH_REMARKS,'')PAYMENTTHROUGH_REMARKS,nvl(REMARKS,' ')REMARKS,nvl(FORMATE_REMARKS,' ')FORMATE_REMARKS,SEND_QUTOTATIONCALL_LETTER,QUOTATIONREQUESTCD,LETTER_NO,MINIMUM_QUANTUM,QUOTATION_ADDRESS,to_char(QUOTATION_DATE,'dd/Mon/yyyy')QUOTATION_DATE,QUOTATION_TIME,to_char(VALID_DATE,'dd/Mon/yyyy')VALID_DATE,VALID_TIME,to_char(OPEN_DATE,'dd/Mon/yyyy')OPEN_DATE,OPEN_TIME,nvl(NAME_TENDERER,'')NAME_TENDERER,nvl(ADDRESS_TENDERER,'')ADDRESS_TENDERER,TELEPHONE_NO,FAX_NO,nvl(CONTACT_PERSON,'')CONTACT_PERSON,nvl(DELIVERY_REQUESTED,'')DELIVERY_REQUESTED,nvl(ACCOUNTNO,'')ACCOUNTNO,nvl(STATUS,'')STATUS,to_char(MATURITYDATE,'dd/Mon/yyyy')MATURITYDATE,nvl(FACEVALUE,0)FACEVALUE,nvl(NUMBER_OF_UNITS,0)NUMBER_OF_UNITS,nvl(INVESTMENT_FACEVALUE,0)INVESTMENT_FACEVALUE,nvl(PURCHASE_OPTION,'')PURCHASE_OPTION,nvl(PREMIUM_PAID,0)PREMIUM_PAID,nvl(PURCHAEPRICE,0)PURCHAEPRICE,SECURITY_NAME,FROMPERIOD,TOPERIOD from INVEST_QUOTATIONREQUEST QT,INVEST_SEC_CATEGORY SC where QT.CATEGORYID=SC.CATEGORYID AND LETTER_NO =? ";
			String searchQrydt="select * from invest_quotationrequest_dt where LETTER_NO=?";
			
			con = DBUtility.getConnection();
			int count=DBUtility.getRecordCount("Select count(1) from invest_quotationrequest_dt where LETTER_NO='"+letterno+"' ");
			String arrangersarray[]=new String[count];
			if(con!=null){
				pstmt = con.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(letterno));				
					rs = pstmt.executeQuery();
					if(rs.next()){
						
						bean=new QuotationRequestBean();
						bean.setProposalRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
						bean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
						bean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYID")));
						bean.setSecurityName(StringUtility.checknull(rs.getString("CATEGORYCD")));
						bean.setSurplusFund(StringUtility.checknull(rs.getString("SURPLUS_FUND")));
						bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
						bean.setMarketTypedef(StringUtility.checknull(rs.getString("MARKET_TYPE1")));
						bean.setApproved(StringUtility.checknull(rs.getString("APPROVED")));
						bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
						bean.setFormateRemarks(StringUtility.checknull(rs.getString("FORMATE_REMARKS")));
						bean.setModeOfPaymentRemarks(StringUtility.checknull(rs.getString("MODEOFPAYMENT_REMARKS")));
						bean.setPaymentThroughRemarks(StringUtility.checknull(rs.getString("PAYMENTTHROUGH_REMARKS")));
						bean.setAttachmenttoSelected(rs.getString("SEND_QUTOTATIONCALL_LETTER"));
						bean.setQuotationRequestCd(rs.getString("QUOTATIONREQUESTCD"));
						bean.setLetterNo(rs.getString("LETTER_NO"));
						bean.setMinimumQuantum(rs.getString("MINIMUM_QUANTUM"));
						bean.setQuotationAddress(rs.getString("QUOTATION_ADDRESS"));
						bean.setQuotationDate(rs.getString("QUOTATION_DATE"));
						bean.setQuotationTime(rs.getString("QUOTATION_TIME"));
						bean.setValidDate(rs.getString("VALID_DATE"));
						bean.setValidTime(rs.getString("VALID_TIME"));
						bean.setOpenDate(rs.getString("OPEN_DATE"));
						bean.setOpenTime(rs.getString("OPEN_TIME"));
						bean.setNameoftheTender(StringUtility.checknull(rs.getString("NAME_TENDERER")));
						bean.setAddressoftheTender(StringUtility.checknull(rs.getString("ADDRESS_TENDERER")));
						bean.setTelephoneNo(StringUtility.checknull(rs.getString("TELEPHONE_NO")));
						bean.setFaxNo(StringUtility.checknull(rs.getString("FAX_NO")));
						bean.setContactPerson(StringUtility.checknull(rs.getString("CONTACT_PERSON")));
						bean.setDeliveryRequestedin(StringUtility.checknull(rs.getString("DELIVERY_REQUESTED")));
						bean.setAccountNo(StringUtility.checknull(rs.getString("ACCOUNTNO")));
						bean.setStatus(StringUtility.checknull(rs.getString("STATUS")));
						bean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITYDATE")));
						bean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
						bean.setNumberOfUnits(StringUtility.checknull(rs.getString("NUMBER_OF_UNITS")));
						bean.setInvestmentFaceValue(StringUtility.checknull(rs.getString("INVESTMENT_FACEVALUE")));
						bean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
						bean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
						bean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHAEPRICE")));
						bean.setSecurityFullName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
						bean.setFromPeriod(StringUtility.checknull(rs.getString("FROMPERIOD")));
						bean.setToPeriod(StringUtility.checknull(rs.getString("TOPERIOD")));
						
						log.info("the remarks in dao is..."+bean.getPaymentThroughRemarks()+"-----"+bean.getModeOfPaymentRemarks());
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
				pstmtdt=con.prepareStatement(searchQrydt);
				if(pstmtdt!=null)
				{
					pstmtdt.setString(1,letterno);
					rsdt=pstmtdt.executeQuery();
					int i=0;
					while(rsdt.next())
					{
						arrangersarray[i]=rsdt.getString("ARRANGERCD");
						i++;
						
					}
					bean.setArrangers(arrangersarray);
					
				}
				else {
					throw new ServiceNotAvailableException();
				}
				
			}else{
				throw new ServiceNotAvailableException();
			}
		
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("QuotationRequestDAO:findQuotationRequest:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:findQuotationRequest:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return bean;
	}
	public List getSelectedArrangers(String letterNo)throws ServiceNotAvailableException,EPISException
	{
		List arrangersarray=new ArrayList();
		try{
			con = DBUtility.getConnection();
			ArrangersBean arrangers=null;
			 String searchQrydt="select arr.ARRANGERCD arrangercode,nvl(arr.ARRANGERNAME,' ') arrangername,nvl(arr.REG_OFFADDR,' ')address,nvl(arr.EMAIL,' ')email from invest_quotationrequest_dt dt,invest_arrangers arr where dt.ARRANGERCD=arr.ARRANGERCD and dt.LETTER_NO=?";
			 pstmtdt=con.prepareStatement(searchQrydt);
				if(pstmtdt!=null)
				{
					pstmtdt.setString(1,letterNo);
					rsdt=pstmtdt.executeQuery();
					
					while(rsdt.next())
					{
						arrangers=new ArrangersBean();
						arrangers.setArrangerCd(StringUtility.checknull(rsdt.getString("arrangercode")));
						arrangers.setArrangerName(StringUtility.checknull(rsdt.getString("arrangername")));
						arrangers.setRegOffAddr(StringUtility.checknull(rsdt.getString("address")));
						arrangers.setEmail(StringUtility.checknull(rsdt.getString("email")));
						
						arrangersarray.add(arrangers);
						
					}
					
					
				}
				else {
					throw new ServiceNotAvailableException();
				}
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:findQuotationRequest:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmtdt,con);			
		}
		return arrangersarray;
	}
	public List getQuotationRequests()throws ServiceNotAvailableException,EPISException
	{
		List list=new ArrayList();
		QuotationRequestBean bean=null;
		try{
			String quotationQuery="Select * from INVEST_QUOTATIONREQUEST WHERE LETTER_NO IS NOT NULL and LETTER_NO not in (select distinct LETTER_NO from invest_quotaion_data where YTMVERIFIED='Y') ";
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				rs=st.executeQuery(quotationQuery);
				while(rs.next())
				{
					bean=new QuotationRequestBean();
					bean.setLetterNo(rs.getString("LETTER_NO"));
					bean.setQuotationRequestCd(rs.getString("QUOTATIONREQUESTCD"));
					bean.setProposalRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
					bean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
					bean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYID")));
					bean.setSurplusFund(StringUtility.checknull(rs.getString("SURPLUS_FUND")));
					bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
					bean.setApproved(StringUtility.checknull(rs.getString("APPROVED")));
					bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
					bean.setAttachmenttoSelected(StringUtility.checknull(rs.getString("SEND_QUTOTATIONCALL_LETTER")));
					list.add(bean);
				}
				
			}
			else {
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:findQuotationRequest:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
		return list;
	}
	public String getLoginId(String letterNo)throws ServiceNotAvailableException,EPISException
	{
		String createdby="";
		try{
			String useridQuery="SELECT CREATED_BY FROM invest_quotationrequest WHERE LETTER_NO='"+letterNo+"'";
			con=DBUtility.getConnection();
			if(con!=null)
			{
					st=con.createStatement();
					rs=st.executeQuery(useridQuery);
					if(rs.next())
					{
						createdby=StringUtility.checknull(rs.getString("CREATED_BY"));
					}
			}
			
			else
			{
				throw new ServiceNotAvailableException();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:findQuotationRequest:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return createdby;
	}
	public List getArrangers()throws ServiceNotAvailableException,EPISException
	{
		List list= new ArrayList();
		try{
			ArrangersBean arrangers=null;
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				String query="select * from invest_arrangers where ARRANGERCD is not null";
				rs=st.executeQuery(query);
				while(rs.next())
				{
					arrangers=new ArrangersBean();
					arrangers.setArrangerCd(rs.getString("ARRANGERCD"));
					arrangers.setArrangerName(rs.getString("ARRANGERNAME"));
					list.add(arrangers);
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("QuotationRequestDAO:getArrangers:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("QuotationRequestDAO:getArrangers:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return list;
		
	}
	public String  saveQuotationRequest(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		String letter_No="";
try{
			log.info("this is calling.....");
			con = DBUtility.getConnection();
			if(con!=null){
			Date today = Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("MMM");
			String currentMonth = formatter.format(today);
			com.epis.utilities.DateValidation datevalidation=new com.epis.utilities.DateValidation();
			String year=datevalidation.getLetterYear(bean.getQuotationDate());
			
				
			String quotationcd =AutoGeneration.getNextCode("INVEST_QUOTATIONREQUEST","QUOTATIONREQUESTCD",5,con);
			letter_No=AutoGeneration.getNextCodeGBy("INVEST_QUOTATIONREQUEST","LETTER_NO",23,"FA/"+bean.getSecurityName()+"/INVEST/"+year,con);
			String arrangersarray[]=bean.getArrangers();
			
			
			
			
			
			String savequotationSql ="insert into invest_quotationrequest (QUOTATIONREQUESTCD, PROPOSAL_REF_NO,TRUSTTYPE, CATEGORYID,SURPLUS_FUND,MARKET_TYPE,REMARKS,LETTER_NO,CREATED_BY,MINIMUM_QUANTUM,QUOTATION_ADDRESS,QUOTATION_DATE,QUOTATION_TIME,VALID_DATE,VALID_TIME,OPEN_DATE,OPEN_TIME,NAME_TENDERER,ADDRESS_TENDERER,TELEPHONE_NO,FAX_NO,CONTACT_PERSON,DELIVERY_REQUESTED,ACCOUNTNO,STATUS,FACEVALUE,NUMBER_OF_UNITS,INVESTMENT_FACEVALUE,PURCHASE_OPTION,PREMIUM_PAID,PURCHAEPRICE,MATURITYDATE,SECURITY_NAME,INTEREST_RATE,INTEREST_DATE,INVESTMENT_PRICEOFFERED,FORMATE_REMARKS,MODEOFPAYMENT_REMARKS,PAYMENTTHROUGH_REMARKS,FROMPERIOD,TOPERIOD,CREATED_DT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
			String savequotationSqldt="insert into invest_quotationrequest_dt(LETTER_NO,ARRANGERCD) values(?,?)";
			String savequotationdata="insert into invest_quotaion_data(QUOTATIONCD,LETTER_NO,ARRANGERCD,TRUSTTYPE,CATEGORYID,SECURITY_NAME,FACEVALUE,NUMBER_OF_UNITS,INVESTMENT_FACEVALUE,PURCHASE_OPTION,PREMIUM_PAID,PURCHAEPRICE,MATURITYDATE,CREATED_BY,INTEREST_RATE,INTEREST_DATE,INVESTMENT_PRICEOFFERED,CREATED_DT)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			
			
			log.info("the query is"+"insert into invest_quotationrequest (QUOTATIONREQUESTCD, PROPOSAL_REF_NO,TRUSTTYPE, CATEGORYID,SURPLUS_FUND,MARKET_TYPE,REMARKS,LETTER_NO,CREATED_BY,MINIMUM_QUANTUM,QUOTATION_ADDRESS,QUOTATION_DATE,QUOTATION_TIME,VALID_DATE,VALID_TIME,OPEN_DATE,OPEN_TIME,NAME_TENDERER,ADDRESS_TENDERER,TELEPHONE_NO,FAX_NO,CONTACT_PERSON,DELIVERY_REQUESTED,ACCOUNTNO,STATUS,FACEVALUE,NUMBER_OF_UNITS,INVESTMENT_FACEVALUE,PURCHASE_OPTION,PREMIUM_PAID,PURCHAEPRICE,MATURITYDATE,SECURITY_NAME,INTEREST_RATE,INTEREST_DATE,INVESTMENT_PRICEOFFERED,CREATED_DT) values ('"+quotationcd+"','"+bean.getProposalRefNo()+"','"+bean.getTrustType()+"','"+bean.getSecurityCategory()+"','"+bean.getSurplusFund()+"','"+bean.getMarketType()+"','"+bean.getRemarks()+"','"+letter_No+"','"+bean.getLoginUserId()+"','"+bean.getMinimumQuantum()+"','"+bean.getQuotationAddress()+"','"+bean.getQuotationDate()+"','"+bean.getQuotationTime()+"','"+bean.getValidDate()+"','"+bean.getValidTime()+"','"+bean.getOpenDate()+"','"+bean.getOpenTime()+"','"+bean.getNameoftheTender()+"','"+bean.getAddressoftheTender()+"','"+bean.getTelephoneNo()+"','"+bean.getFaxNo()+"','"+bean.getContactPerson()+"','"+bean.getDeliveryRequestedin()+"','"+bean.getAccountNo()+"','"+bean.getStatus()+"','"+bean.getFaceValue()+"','"+bean.getNumberOfUnits()+"','"+bean.getInvestmentFaceValue()+"','"+bean.getPurchaseOption()+"','"+bean.getPremiumPaid()+"','"+bean.getPurchasePrice()+"','"+bean.getMaturityDate()+"','"+bean.getSecurityFullName()+"','"+bean.getInterestRate()+"','"+bean.getInterestDate()+"','"+bean.getModeOfPaymentRemarks()+"','"+bean.getPaymentThroughRemarks()+"',SYSDATE)");
			
				DBUtility.setAutoCommit(false,con);
				if(!StringUtility.checknull(bean.getAccountNo()).equals(""))
				{
					String bankquery="select BANKNAME,BRANCHNAME,decode(ACCOUNTTYPE,'S','Saving','C','Current') ACCOUNTTYPE,CSGLACCOUNTNO,PANNO from cb_bank_info where ACCOUNTNO='"+bean.getAccountNo()+"' ";
					st=con.createStatement();
					rs=st.executeQuery(bankquery);
					if(rs.next())
					{
						bean.setBankName(StringUtility.checknull(rs.getString("BANKNAME")));
						bean.setBranch(StringUtility.checknull(rs.getString("BRANCHNAME")));
						bean.setAccountType(StringUtility.checknull(rs.getString("ACCOUNTTYPE")));
						bean.setCsglAccountNo(StringUtility.checknull(rs.getString("CSGLACCOUNTNO")));
						bean.setPanNo(StringUtility.checknull(rs.getString("PANNO")));
						
					}
				}
				pstmt=con.prepareStatement(savequotationSql);		
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(quotationcd));
					pstmt.setString(2,StringUtility.checknull(bean.getProposalRefNo()));
					pstmt.setString(3,StringUtility.checknull(bean.getTrustType()));
					pstmt.setString(4,StringUtility.checknull(bean.getSecurityCategory()));
					pstmt.setString(5,StringUtility.checknull(bean.getSurplusFund()));
					pstmt.setString(6,StringUtility.checknull(bean.getMarketType()));
					pstmt.setString(7,StringUtility.checknull(bean.getRemarks()));
					pstmt.setString(8,StringUtility.checknull(letter_No));
					pstmt.setString(9,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.setString(10,StringUtility.checknull(bean.getMinimumQuantum()));
					pstmt.setString(11,StringUtility.checknull(bean.getQuotationAddress()));
					pstmt.setString(12,StringUtility.checknull(bean.getQuotationDate()));
					pstmt.setString(13,StringUtility.checknull(bean.getQuotationTime()));
					pstmt.setString(14,StringUtility.checknull(bean.getValidDate()));
					pstmt.setString(15,StringUtility.checknull(bean.getValidTime()));
					pstmt.setString(16,StringUtility.checknull(bean.getOpenDate()));
					pstmt.setString(17,StringUtility.checknull(bean.getOpenTime()));
					pstmt.setString(18,StringUtility.checknull(bean.getNameoftheTender()));
					pstmt.setString(19,StringUtility.checknull(bean.getAddressoftheTender()));
					pstmt.setString(20,StringUtility.checknull(bean.getTelephoneNo()));
					pstmt.setString(21,StringUtility.checknull(bean.getFaxNo()));
					pstmt.setString(22,StringUtility.checknull(bean.getContactPerson()));
					pstmt.setString(23,StringUtility.checknull(bean.getDeliveryRequestedin()));
					pstmt.setString(24,StringUtility.checknull(bean.getAccountNo()));
					pstmt.setString(25,StringUtility.checknull(bean.getStatus()));
					pstmt.setString(26,StringUtility.checknull(bean.getFaceValue()));
					pstmt.setString(27,StringUtility.checknull(bean.getNumberOfUnits()));
					pstmt.setString(28,StringUtility.checknull(bean.getInvestmentFaceValue()));
					pstmt.setString(29,StringUtility.checknull(bean.getPurchaseOption()));
					pstmt.setString(30,StringUtility.checknull(bean.getPremiumPaid()));
					pstmt.setString(31,StringUtility.checknull(bean.getPurchasePrice()));
					pstmt.setString(32,StringUtility.checknull(bean.getMaturityDate()));
					pstmt.setString(33,StringUtility.checknull(bean.getSecurityFullName()));
					pstmt.setString(34,StringUtility.checknull(bean.getInterestRate()));
					pstmt.setString(35,StringUtility.checknull(bean.getInterestDate()));
					pstmt.setString(36,StringUtility.checknull(bean.getPriceoffered()));
					pstmt.setString(37,StringUtility.checknull(bean.getFormateRemarks()));
					pstmt.setString(38,StringUtility.checknull(bean.getModeOfPaymentRemarks()));
					pstmt.setString(39,StringUtility.checknull(bean.getPaymentThroughRemarks()));
					pstmt.setString(40,StringUtility.checknull(bean.getFromPeriod()));
					pstmt.setString(41,StringUtility.checknull(bean.getToPeriod()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
				for(int i=0; i<arrangersarray.length ; i++){
					pstmtdt=con.prepareStatement(savequotationSqldt);
					if(pstmtdt!=null)
					{
						pstmtdt.setString(1,StringUtility.checknull(letter_No));
						pstmtdt.setString(2,StringUtility.checknull(arrangersarray[i]));
						pstmtdt.executeUpdate();
						
					}
					else{
						throw new ServiceNotAvailableException();
					}
					}
				if(bean.getMarketType().equals("R")||bean.getMarketType().equals("O"))
				{
					String quotationdatacd =AutoGeneration.getNextCode("invest_Quotaion_data","QUOTATIONCD",10,con);
					PreparedStatement pdata=con.prepareStatement(savequotationdata) ;
					if(pdata!=null)
					{
						pdata.setString(1,StringUtility.checknull(quotationdatacd));
						pdata.setString(2,StringUtility.checknull(letter_No));
						pdata.setString(3,StringUtility.checknull(arrangersarray[0]));
						pdata.setString(4,StringUtility.checknull(bean.getTrustType()));
						pdata.setString(5,StringUtility.checknull(bean.getSecurityCategory()));
						pdata.setString(6,StringUtility.checknull(bean.getSecurityFullName()));
						pdata.setString(7,StringUtility.checknull(bean.getFaceValue()));
						pdata.setString(8,StringUtility.checknull(bean.getNumberOfUnits()));
						pdata.setString(9,StringUtility.checknull(bean.getInvestmentFaceValue()));
						pdata.setString(10,StringUtility.checknull(bean.getPurchaseOption()));
						pdata.setString(11,StringUtility.checknull(bean.getPremiumPaid()));
						pdata.setString(12,StringUtility.checknull(bean.getPurchasePrice()));
						pdata.setString(13,StringUtility.checknull(bean.getMaturityDate()));
						pdata.setString(14,StringUtility.checknull(bean.getLoginUserId()));
						pdata.setString(15,StringUtility.checknull(bean.getInterestRate()));
						pdata.setString(16,StringUtility.checknull(bean.getInterestDate()));
						pdata.setString(17,StringUtility.checknull(bean.getPriceoffered()));
						pdata.executeUpdate();
						
					}
					else{
						throw new ServiceNotAvailableException();
					}
					String updatequery="update invest_quotationrequest set QUOTATIONCD=? where LETTER_NO=?";
					PreparedStatement updateps=con.prepareStatement(updatequery);
					if(updatequery!=null)
					{
						updateps.setString(1,StringUtility.checknull(quotationdatacd));
						updateps.setString(2,StringUtility.checknull(letter_No));
						updateps.executeUpdate();
					}
					else{
						throw new ServiceNotAvailableException();
					}
						
				}
			
				
			}else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
			}
			catch(Exception sql)
			{
				log.error("QuotationRequestDAO:saveQuotationRequest:SQLException:"+sql.getMessage());
				throw  new EPISException(sql);
			}
			
			log.error("QuotationRequestDAO:saveQuotationRequest:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			
			log.error("QuotationRequestDAO:saveQuotationRequest:Exception:"+e.getMessage());
			throw new EPISException(e);
			
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,con);	
		}	
		return letter_No;
	}
	public void editQuotationRequest(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		try{		
			con=DBUtility.getConnection();	
			String updatequotationSql ="update  INVEST_QUOTATIONREQUEST set SURPLUS_FUND=?,MARKET_TYPE=?,REMARKS=?,UPDATED_BY=?,MINIMUM_QUANTUM=?,QUOTATION_ADDRESS=?,QUOTATION_DATE=?,QUOTATION_TIME=?,VALID_DATE=?,VALID_TIME=?,OPEN_DATE=?,OPEN_TIME=?,NAME_TENDERER=?,ADDRESS_TENDERER=?,TELEPHONE_NO=?,FAX_NO=?,CONTACT_PERSON=?,DELIVERY_REQUESTED=?,ACCOUNTNO=?,STATUS=?,FACEVALUE=?,NUMBER_OF_UNITS=?,INVESTMENT_FACEVALUE=?,PURCHASE_OPTION=?,PREMIUM_PAID=?,PURCHAEPRICE=?,MATURITYDATE=?,SECURITY_NAME=?,MODEOFPAYMENT_REMARKS=?,PAYMENTTHROUGH_REMARKS=?,FROMPERIOD=?,TOPERIOD=?,UPDATED_DT=SYSDATE where LETTER_NO=?";
			String updatequotationSqldt="insert into invest_quotationrequest_dt(LETTER_NO,ARRANGERCD)values(?,?)";
			String deletequotationdt="delete from invest_quotationrequest_dt where LETTER_NO=?";
			if(con!=null){
				String arrangersarray[]=bean.getArrangers();
				
				
				
				DBUtility.setAutoCommit(false,con);
				pstmt=con.prepareStatement(updatequotationSql);	
				if(!StringUtility.checknull(bean.getAccountNo()).equals(""))
				{
					String bankquery="select BANKNAME,BRANCHNAME,decode(ACCOUNTTYPE,'S','Saving','C','Current') ACCOUNTTYPE,CSGLACCOUNTNO,PANNO from cb_bank_info where ACCOUNTNO='"+bean.getAccountNo()+"' ";
					st=con.createStatement();
					rs=st.executeQuery(bankquery);
					if(rs.next())
					{
						bean.setBankName(StringUtility.checknull(rs.getString("BANKNAME")));
						bean.setBranch(StringUtility.checknull(rs.getString("BRANCHNAME")));
						bean.setAccountType(StringUtility.checknull(rs.getString("ACCOUNTTYPE")));
						bean.setCsglAccountNo(StringUtility.checknull(rs.getString("CSGLACCOUNTNO")));
						bean.setPanNo(StringUtility.checknull(rs.getString("PANNO")));
						
					}
				}
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(bean.getSurplusFund()));
					pstmt.setString(2,StringUtility.checknull(bean.getMarketType()));
					pstmt.setString(3,StringUtility.checknull(bean.getRemarks()));
					pstmt.setString(4,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(bean.getMinimumQuantum()));
					pstmt.setString(6,StringUtility.checknull(bean.getQuotationAddress()));
					
					pstmt.setString(7,StringUtility.checknull(bean.getQuotationDate()));
					pstmt.setString(8,StringUtility.checknull(bean.getQuotationTime()));
					
					pstmt.setString(9,StringUtility.checknull(bean.getValidDate()));
					pstmt.setString(10,StringUtility.checknull(bean.getValidTime()));
					
					pstmt.setString(11,StringUtility.checknull(bean.getOpenDate()));
					pstmt.setString(12,StringUtility.checknull(bean.getOpenTime()));
					pstmt.setString(13,StringUtility.checknull(bean.getNameoftheTender()));
					pstmt.setString(14,StringUtility.checknull(bean.getAddressoftheTender()));
					pstmt.setString(15,StringUtility.checknull(bean.getTelephoneNo()));
					pstmt.setString(16,StringUtility.checknull(bean.getFaxNo()));
					pstmt.setString(17,StringUtility.checknull(bean.getContactPerson()));
					pstmt.setString(18,StringUtility.checknull(bean.getDeliveryRequestedin()));
					
					pstmt.setString(19,StringUtility.checknull(bean.getAccountNo()));
					pstmt.setString(20,StringUtility.checknull(bean.getStatus()));
					
					pstmt.setString(21,StringUtility.checknull(bean.getFaceValue()));
					pstmt.setString(22,StringUtility.checknull(bean.getNumberOfUnits()));
					pstmt.setString(23,StringUtility.checknull(bean.getInvestmentFaceValue()));
					
					pstmt.setString(24,StringUtility.checknull(bean.getPurchaseOption()));
					pstmt.setString(25,StringUtility.checknull(bean.getPremiumPaid()));
					pstmt.setString(26,StringUtility.checknull(bean.getPurchasePrice()));
					
					pstmt.setString(27,StringUtility.checknull(bean.getMaturityDate()));
					pstmt.setString(28,StringUtility.checknull(bean.getSecurityFullName()));
					pstmt.setString(29,StringUtility.checknull(bean.getModeOfPaymentRemarks()));
					pstmt.setString(30,StringUtility.checknull(bean.getPaymentThroughRemarks()));
					pstmt.setString(31,StringUtility.checknull(bean.getFromPeriod()));
					pstmt.setString(32,StringUtility.checknull(bean.getToPeriod()));
					
					
				
					pstmt.setString(33,StringUtility.checknull(bean.getLetterNo()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
				pstmt=con.prepareStatement(deletequotationdt);
				if(pstmt!=null)
				{
					pstmt.setString(1,bean.getLetterNo());
					pstmt.executeUpdate();
				}
				for(int i=0; i<arrangersarray.length ; i++){
					pstmtdt=con.prepareStatement(updatequotationSqldt);
					if(pstmtdt!=null)
					{
						pstmtdt.setString(1,StringUtility.checknull(bean.getLetterNo()));
						pstmtdt.setString(2,StringUtility.checknull(arrangersarray[i]));
						pstmtdt.executeUpdate();
						
					}
					else{
						throw new ServiceNotAvailableException();
					}
					}
				if(bean.getMarketType().equals("R")||bean.getMarketType().equals("O"))
				{
				String selectquery="Select QUOTATIONCD from invest_quotationrequest where LETTER_NO='"+bean.getLetterNo()+"'";
				String updateQuery="update invest_quotaion_data set ARRANGERCD=?,SECURITY_NAME=?,FACEVALUE=?,NUMBER_OF_UNITS=?,INVESTMENT_FACEVALUE=?,PURCHASE_OPTION=?,PREMIUM_PAID=?,PURCHAEPRICE=?,MATURITYDATE=?,UPDATED_BY=?,UPDATED_DT=sysdate where QUOTATIONCD=?";
				String quotationCd="";
				st=con.createStatement();
				
				ResultSet quotationrs=st.executeQuery(selectquery);
				if(quotationrs.next())
				{
					quotationCd=quotationrs.getString("QUOTATIONCD");
				}
				PreparedStatement dataps=con.prepareStatement(updateQuery);
				if(dataps!=null)
				{
					dataps.setString(1,StringUtility.checknull(arrangersarray[0]));
					dataps.setString(2,StringUtility.checknull(bean.getSecurityFullName()));
					dataps.setString(3,StringUtility.checknull(bean.getFaceValue()));
					dataps.setString(4,StringUtility.checknull(bean.getNumberOfUnits()));
					dataps.setString(5,StringUtility.checknull(bean.getInvestmentFaceValue()));
					dataps.setString(6,StringUtility.checknull(bean.getPurchaseOption()));
					dataps.setString(7,StringUtility.checknull(bean.getPremiumPaid()));
					dataps.setString(8,StringUtility.checknull(bean.getPurchasePrice()));
					dataps.setString(9,StringUtility.checknull(bean.getMaturityDate()));
					dataps.setString(10,StringUtility.checknull(bean.getLoginUserId()));
					dataps.setString(11,StringUtility.checknull(quotationCd));
					dataps.executeUpdate();
					
				}
				else
				{
					throw new ServiceNotAvailableException();	
				}
				}
				
			}else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
			}
			catch(Exception e)
			{
				log.error("QuotationRequestDAO:editQuotationRequest:SQLException"+e.getMessage());
			}
			sqlex.printStackTrace();
			log.error("QuotationRequestDAO:editQuotationRequest:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:editQuotationRequest:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,con);	
		}
	}
	
	public List searchQuotationRequests(QuotationRequestBean bean)throws ServiceNotAvailableException, EPISException
	{
		List list =new ArrayList();;
		try{
			QuotationRequestBean quotation =null;
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			String query = "select QUOTATIONREQUESTCD,PROPOSAL_REF_NO,LETTER_NO,TRUSTTYPE,QT.CATEGORYID, " +
					" CATEGORYCD,SURPLUS_FUND,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R', " +
					" 'RBI','O','OpenBid') MARKET_TYPE,NVL(REMARKS,'--')REMARKS,SEND_QUTOTATIONCALL_LETTER,(case " +
					" when (select count(*) from invest_quotaion_data data where data.LETTER_NO = QT.LETTER_NO ) " +
					" >0 then 'Y' else 'N' end) qdata from invest_quotationrequest QT,INVEST_SEC_CATEGORY SC  " +
					" where QUOTATIONREQUESTCD is not null AND QT.CATEGORYID=SC.CATEGORYID ";
			
			if(!bean.getLetterNo().equals(" ")){
				query +=" and upper(LETTER_NO) like upper('"+bean.getLetterNo()+"%')";
			}
			if(!bean.getProposalRefNo().equals(" ")){
				query +=" and upper(PROPOSAL_REF_NO) like upper('"+bean.getProposalRefNo()+"%')";
			}
			if(!bean.getTrustType().equals(" ")){
				query +=" and upper(TRUSTTYPE) like upper('"+bean.getTrustType()+"%')";
			}
			if(!bean.getSecurityCategory().equals(" ")){
				query +=" and upper(qt.CATEGORYID) like upper('"+bean.getSecurityCategory()+"%')";
			}
			query +=" order by QT.CREATED_DT DESC";
			log.info("QUOTATIONREQUESTDAO:searchTrustType(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				quotation = new QuotationRequestBean();
				quotation.setLetterNo(rs.getString("LETTER_NO"));
				quotation.setProposalRefNo(rs.getString("PROPOSAL_REF_NO"));
				quotation.setQuotationRequestCd(rs.getString("QUOTATIONREQUESTCD"));
				quotation.setTrustType(rs.getString("TRUSTTYPE"));
				quotation.setSecurityCategory(rs.getString("CATEGORYID"));
				quotation.setSecurityName(rs.getString("CATEGORYCD"));
				quotation.setSurplusFund(rs.getString("SURPLUS_FUND"));
				quotation.setMarketType(rs.getString("MARKET_TYPE"));
				quotation.setRemarks(rs.getString("REMARKS"));
				quotation.setAttachmenttoSelected(rs.getString("SEND_QUTOTATIONCALL_LETTER"));
				quotation.setHasQuotations(rs.getString("qdata"));
				list.add(quotation);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("QuotationRequestDAO:searchUnit:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("UnitDAO:searchUnit:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	public void deleteQuotationRequest(String letternos)throws ServiceNotAvailableException, EPISException
	{
try{
			
			con = DBUtility.getConnection();
			String deletequotationSqldt="delete from invest_quotationrequest_dt where LETTER_NO in('"+letternos+"')";
			String deletequotationSql ="delete  from INVEST_QUOTATIONREQUEST where LETTER_NO in ('"+letternos+"')";
			log.info("quotation requestdao delete query:"+deletequotationSqldt);
			log.info("QuotationRequestDAO: Delete delete quotationrequest Qry:"+deletequotationSql );
			if(con!=null){
				st=con.createStatement();
				if(st!=null){
					DBUtility.setAutoCommit(false,con);
					st.executeUpdate(deletequotationSqldt);
					st.executeUpdate(deletequotationSql);
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
				
			}
			catch(Exception e)
			{
				log.error(e.getMessage());
			}
			sqlex.printStackTrace();
			log.error("QuotationRequestDAO:deleteUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:deleteUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,st,con);	
		}
		
	}
	public QuotationRequestBean verifyArrangersMailing(QuotationRequestBean bean)throws ServiceNotAvailableException, EPISException
	{
		QuotationRequestBean quotation=null;
		SQLHelper sq=null;
		double surplusamt=0.0;
		double result=0.0;
		String concrores="";
		double minQuantumamt=0.0;
		double quantumresult=0.0;
		String conQuantumCrores="";
		String conInWords="";
		StringBuffer totconWords=new StringBuffer();
		totconWords.append("(");
		try{
			
			con = DBUtility.getConnection();
			String updatequery="update  INVEST_QUOTATIONREQUEST set SEND_QUTOTATIONCALL_LETTER=? where LETTER_NO=? ";
			String selectquery="select LETTER_NO,PROPOSAL_REF_NO,QUOTATIONREQUESTCD,to_char(quotation.created_dt,'dd-mm-yy-mm-ss') created_dt,Trusttype,nvl(surplus_fund,0)surplus_fund,nvl(MINIMUM_QUANTUM,0) MINIMUM_QUANTUM,decode(market_type,'P','Primary','S','Secondary','B','Both') market_type,decode(approved,'A','Approved','R','Reject') Approved,nvl(REMARKS,' ')REMARKS,nvl(FORMATE_REMARKS,' ')FORMATE_REMARKS,NVL(FAX_NO,'24610540')FAX_NO,SEND_QUTOTATIONCALL_LETTER,CATEGORYCD,QUOTATION_ADDRESS,to_char(QUOTATION_DATE,'dd/Mon/yyyy') QUOTATION_DATE,QUOTATION_TIME,to_char(VALID_DATE,'dd/Mon/yyyy') VALID_DATE,VALID_TIME,to_char(OPEN_DATE,'dd/Mon/yyyy') OPEN_DATE,OPEN_TIME,nvl(MODEOFPAYMENT_REMARKS,' ')MODEOFPAYMENT_REMARKS,nvl(PAYMENTTHROUGH_REMARKS,' ')PAYMENTTHROUGH_REMARKS from invest_quotationrequest quotation,invest_sec_category sec where LETTER_NO=? and quotation.CATEGORYID=sec.CATEGORYID";
				String attachment= StringUtility.checknull(bean.getAttachmenttoSelected());
			if(attachment.equals(""))
				attachment="N";
			if(con!=null){
				DBUtility.setAutoCommit(false,con);
				pstmt=con.prepareStatement(updatequery);		
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(attachment));
					pstmt.setString(2,StringUtility.checknull(bean.getLetterNo()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
			DBUtility.commitTrans(con);
			pstmtdt=con.prepareStatement(selectquery);
			if(pstmtdt!=null)
			{
				pstmtdt.setString(1,StringUtility.checknull(bean.getLetterNo()));
				rs=pstmtdt.executeQuery();
				if(rs.next())
				{
					sq=new SQLHelper();
					quotation=new QuotationRequestBean();
					quotation.setLetterNo(rs.getString("LETTER_NO"));
					quotation.setProposalRefNo(rs.getString("PROPOSAL_REF_NO"));
					quotation.setTrustType(rs.getString("TRUSTTYPE"));
					quotation.setSurplusFund(rs.getString("SURPLUS_FUND"));
					surplusamt=Double.parseDouble(rs.getString("SURPLUS_FUND"));
					
					
					result=surplusamt/10000000.00;
					concrores=result+"";
					
					quotation.setConcrores(concrores);
					quotation.setMinimumQuantum(rs.getString("MINIMUM_QUANTUM"));
					minQuantumamt=Double.parseDouble(rs.getString("MINIMUM_QUANTUM"));
					quantumresult=surplusamt/10000000.00;
					conQuantumCrores=quantumresult+"";
					quotation.setConQuantumCrores(conQuantumCrores);
					conInWords=sq.ConvertInWords(quantumresult);
					totconWords.append("Rupees "+conInWords+" "+"Crores Only  )" );
					quotation.setConInWords(totconWords.toString());
					quotation.setMarketType(rs.getString("MARKET_TYPE"));
					quotation.setApproved(rs.getString("APPROVED"));
					quotation.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
					quotation.setFormateRemarks(StringUtility.checknull(rs.getString("FORMATE_REMARKS")));
					quotation.setAttachmenttoSelected(rs.getString("SEND_QUTOTATIONCALL_LETTER"));
					quotation.setSecurityName(rs.getString("CATEGORYCD"));
					quotation.setQuotationAddress(StringUtility.checknull(bean.getQuotationAddress()));
					quotation.setQuotationDate(rs.getString("QUOTATION_DATE"));
					quotation.setQuotationTime(rs.getString("QUOTATION_TIME"));
					quotation.setValidDate(rs.getString("VALID_DATE"));
					quotation.setValidTime(rs.getString("VALID_TIME"));
					quotation.setOpenDate(rs.getString("OPEN_DATE"));
					quotation.setOpenTime(rs.getString("OPEN_TIME"));
					quotation.setQuotationCd(rs.getString("QUOTATIONREQUESTCD"));
					quotation.setCreatedDate(rs.getString("created_dt"));
					quotation.setFaxNo(rs.getString("FAX_NO"));
					quotation.setModeOfPaymentRemarks(StringUtility.checknull(rs.getString("MODEOFPAYMENT_REMARKS")));
					quotation.setPaymentThroughRemarks(StringUtility.checknull(rs.getString("PAYMENTTHROUGH_REMARKS")));
					HashMap documentdetails=new HashMap();
					
					documentdetails.put("sub","Sealed Quotations for Investment of Funds in "+quotation.getSecurityName()+" Bonds Through "+quotation.getMarketType()+" market");
					documentdetails.put("body", "        "+quotation.getTrustType()+" Trusts Proposes to invest its funds approx Rs."+quotation.getConQuantumCrores()+" Crores "+quotation.getConInWords()+" in Central "+quotation.getSecurityName()+" Bonds having minimum latest rating \"AA+\"and above at least from two credit agencies. Please quote your Competitive rates of various issues available in market on a given below format in a Sealed Cover Addressed to The Secretary. "+quotation.getTrustType()+" Trust, "+bean.getQuotationAddress()+" on "+quotation.getValidDate()+" by "+quotation.getValidTime());
					documentdetails.put("note1","The quotation must clearly indicate \"QUOTATION for "+quotation.getTrustType()+" Trust "+" on the face of envelope.");
					documentdetails.put("note2","The quotation shall be dropped in a quotation box kept in the CPF Section in, "+quotation.getQuotationAddress()+" up to "+quotation.getValidTime()+" hours on "+quotation.getValidDate());
					documentdetails.put("note3","Quotation received after "+quotation.getValidTime() +" hours will not be considered. ");
					documentdetails.put("note5",quotation.getTrustType()+" Trust requires Transaction to be executed on Delivery-versus Payment basis.");
					documentdetails.put("note8","The deal will be settld at Delhi on "+quotation.getModeOfPaymentRemarks()+" basis through "+quotation.getPaymentThroughRemarks());
					documentdetails.put("note10",quotation.getTrustType()+" Trust reserve the right to reject any quotations without assigning any reason.");
					
					
					quotation.setDocumentDetails(documentdetails);
					 
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
			}
			catch(Exception e)
			{
				log.error("QuotationRequestDAO:verifyArrangersMailing:SQLException"+e.getMessage());
			}
			sqlex.printStackTrace();
			log.error("QuotationRequestDAO:verifyArrangersMailing:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:verifyArrangersMailing:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,con);	
		}
		return quotation;
		
	}
	public QuotationRequestBean generateQuotationRequestReport(QuotationRequestBean quotation)throws ServiceNotAvailableException, EPISException
	{
		QuotationRequestBean bean=new QuotationRequestBean();
		SQLHelper sq=null;
		double surplusamt=0.0;
		double investamt=0.0;
		String investmentAmount="";
		double investresult=0.0;
		double result=0.0;
		String concrores="";
		double minQuantumamt=0.0;
		double quantumresult=0.0;
		String conQuantumCrores="";
		String conInWords="";
		StringBuffer totconWords=new StringBuffer();
		StringBuffer investmentinwords=new StringBuffer();
		totconWords.append("(");
		
		try{
			con = DBUtility.getConnection();
			String selectquery="select LETTER_NO,PROPOSAL_REF_NO,QUOTATIONREQUESTCD,to_char(quotation.created_dt,'dd-mm-yy-mm-ss') created_dt,Trusttype,nvl(surplus_fund,0)surplus_fund,nvl(MINIMUM_QUANTUM,0) MINIMUM_QUANTUM, market_type,decode(market_type,'P','Primary','S','Secondary','B','Both') markettypedesc,decode(approved,'A','Approved','R','Reject') Approved,nvl(REMARKS,' ')REMARKS,nvl(FORMATE_REMARKS,' ')FORMATE_REMARKS,SEND_QUTOTATIONCALL_LETTER,CATEGORYCD,QUOTATION_ADDRESS,to_char(QUOTATION_DATE,'dd/Mon/yyyy') QUOTATION_DATE,QUOTATION_TIME,to_char(VALID_DATE,'dd/Mon/yyyy') VALID_DATE,VALID_TIME,to_char(OPEN_DATE,'dd/Mon/yyyy') OPEN_DATE,OPEN_TIME,nvl(NAME_TENDERER,'')NAME_TENDERER,nvl(ADDRESS_TENDERER,'')ADDRESS_TENDERER,nvl(TELEPHONE_NO,0)TELEPHONE_NO,FAX_NO,nvl(CONTACT_PERSON,'')CONTACT_PERSON,nvl(STATUS,'')STATUS,nvl(DELIVERY_REQUESTED,'')DELIVERY_REQUESTED,nvl(ACCOUNTNO,'')ACCOUNTNO,nvl(INVESTMENT_FACEVALUE,0)INVESTMENT_FACEVALUE,nvl(to_char(MATURITYDATE,'dd/Mon/yyyy'),'')MATURITYDATE,nvl(FACEVALUE,0)FACEVALUE,nvl(PURCHAEPRICE,0)PURCHAEPRICE,nvl(NUMBER_OF_UNITS,0)NUMBER_OF_UNITS,nvl(PREMIUM_PAID,0)PREMIUM_PAID,nvl(PURCHASE_OPTION,'')PURCHASE_OPTION,nvl(SECURITY_NAME,'')SECURITY_NAME,nvl(INTEREST_RATE,0)INTEREST_RATE,nvl(to_char(INTEREST_DATE,'dd/Mon/yyyy'),'')INTEREST_DATE,nvl(INVESTMENT_PRICEOFFERED,0)INVESTMENT_PRICEOFFERED,nvl(MODEOFPAYMENT_REMARKS,' ')MODEOFPAYMENT_REMARKS,nvl(PAYMENTTHROUGH_REMARKS,' ')PAYMENTTHROUGH_REMARKS from invest_quotationrequest quotation,invest_sec_category sec where LETTER_NO=? and quotation.CATEGORYID=sec.CATEGORYID";
			String query="select BANKNAME,BRANCHNAME,decode(ACCOUNTTYPE,'S','Saving','C','Current') ACCOUNTTYPE, CSGLACCOUNTNO,PANNO from cb_bank_info where ACCOUNTNO=(Select ACCOUNTNO from invest_quotationrequest where LETTER_NO=?) ";
			String arrangerquery="select ARRANGERCD from invest_quotationrequest_dt where LETTER_NO='"+StringUtility.checknull(quotation.getLetterNo())+"'";
			pstmt=con.prepareStatement(selectquery);
			int count=DBUtility.getRecordCount("Select count(1) from invest_quotationrequest_dt where LETTER_NO='"+StringUtility.checknull(quotation.getLetterNo())+"'");
			log.info("the count is...."+count);
			String arrangerArray[]=new String[count];
			st=con.createStatement();
			ResultSet rs1=DBUtility.getRecordSet(arrangerquery,st);
			int k=0;
			while(rs1.next())
			{
				arrangerArray[k]=StringUtility.checknull(rs1.getString("ARRANGERCD"));
				k++;
			}
			
			bean.setArrangers(arrangerArray);
			pstmtdt=con.prepareStatement(query);
			
			if(pstmtdt!=null)
			{
				pstmtdt.setString(1,StringUtility.checknull(quotation.getLetterNo()));
				rsdt=pstmtdt.executeQuery();
				if(rsdt.next())
				{
					bean.setBankName(StringUtility.checknull(rsdt.getString("BANKNAME")));
					bean.setBranch(StringUtility.checknull(rsdt.getString("BRANCHNAME")));
					bean.setAccountType(StringUtility.checknull(rsdt.getString("ACCOUNTTYPE")));
					bean.setCsglAccountNo(StringUtility.checknull(rsdt.getString("CSGLACCOUNTNO")));
					bean.setPanNo(StringUtility.checknull(rsdt.getString("PANNO")));
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
			if(pstmt!=null)
			{
				pstmt.setString(1,StringUtility.checknull(quotation.getLetterNo()));
				rs=pstmt.executeQuery();
				if(rs.next())
				{
					sq=new SQLHelper();
					
					bean.setLetterNo(rs.getString("LETTER_NO"));
					bean.setProposalRefNo(rs.getString("PROPOSAL_REF_NO"));
					bean.setTrustType(rs.getString("TRUSTTYPE"));
					bean.setSurplusFund(rs.getString("SURPLUS_FUND"));
					surplusamt=Double.parseDouble(rs.getString("SURPLUS_FUND"));
					log.info("the surplus fund is"+bean.getSurplusFund());
					conInWords=sq.ConvertInWords(surplusamt);
					totconWords.append(conInWords+" "+"Only  )" );
					result=surplusamt/10000000.00;
					concrores=result+"";
					bean.setConInWords(totconWords.toString());
					bean.setConcrores(concrores);
					bean.setMinimumQuantum(rs.getString("MINIMUM_QUANTUM"));
					minQuantumamt=Double.parseDouble(rs.getString("MINIMUM_QUANTUM"));
					quantumresult=minQuantumamt/10000000.00;
					conQuantumCrores=quantumresult+"";
					bean.setConQuantumCrores(conQuantumCrores);
					bean.setMarketType(rs.getString("MARKET_TYPE"));
					bean.setMarketTypedef(rs.getString("markettypedesc"));
					bean.setApproved(rs.getString("APPROVED"));
					bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
					bean.setFormateRemarks(StringUtility.checknull(rs.getString("FORMATE_REMARKS")));
					bean.setAttachmenttoSelected(rs.getString("SEND_QUTOTATIONCALL_LETTER"));
					bean.setSecurityName(rs.getString("CATEGORYCD"));
					bean.setQuotationAddress(rs.getString("QUOTATION_ADDRESS"));
					bean.setQuotationDate(rs.getString("QUOTATION_DATE"));
					bean.setQuotationTime(rs.getString("QUOTATION_TIME"));
					bean.setValidDate(rs.getString("VALID_DATE"));
					bean.setValidTime(rs.getString("VALID_TIME"));
					bean.setOpenDate(rs.getString("OPEN_DATE"));
					bean.setOpenTime(rs.getString("OPEN_TIME"));
					bean.setQuotationCd(rs.getString("QUOTATIONREQUESTCD"));
					bean.setCreatedDate(rs.getString("created_dt"));
					bean.setNameoftheTender(StringUtility.checknull(rs.getString("NAME_TENDERER")));
					bean.setAddressoftheTender(StringUtility.checknull(rs.getString("ADDRESS_TENDERER")));
					bean.setTelephoneNo(StringUtility.checknull(rs.getString("TELEPHONE_NO")));
					bean.setFaxNo(StringUtility.checknull(rs.getString("FAX_NO")));
					bean.setContactPerson(StringUtility.checknull(rs.getString("CONTACT_PERSON")));
					bean.setStatus(StringUtility.checknull(rs.getString("STATUS")));
					bean.setDeliveryRequestedin(StringUtility.checknull("DELIVERY_REQUESTED"));
					bean.setAccountNo(StringUtility.checknull(rs.getString("ACCOUNTNO")));
					bean.setModeOfPaymentRemarks(StringUtility.checknull(rs.getString("MODEOFPAYMENT_REMARKS")));
					bean.setPaymentThroughRemarks(StringUtility.checknull(rs.getString("PAYMENTTHROUGH_REMARKS")));
					if(!StringUtility.checknull(rs.getString("INVESTMENT_FACEVALUE")).equals(""))
					{
						investamt=rs.getDouble("INVESTMENT_FACEVALUE");
						investresult=surplusamt/10000000.00;
						investmentAmount=investresult+"";
						investmentinwords.append("RS ");
						investmentinwords.append(investmentAmount );
						investmentinwords.append(" Crores Only");
						
						bean.setFacevalueincrores(investmentinwords.toString());
						
						
					}
					bean.setInvestmentFaceValue(StringUtility.checknull(rs.getString("INVESTMENT_FACEVALUE")));
					bean.setMaturityDate(StringUtility.checkFlag(rs.getString("MATURITYDATE")));
					bean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
					bean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHAEPRICE")));
					bean.setNumberOfUnits(StringUtility.checknull(rs.getString("NUMBER_OF_UNITS")));
					bean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
					bean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
					bean.setSecurityFullName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					bean.setInterestRate(StringUtility.checknull(rs.getString("INTEREST_RATE")));
					bean.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
					bean.setPriceoffered(StringUtility.checknull(rs.getString("INVESTMENT_PRICEOFFERED")));
					
					
					
					
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
			}
			catch(Exception e)
			{
				log.error("QuotationRequestDAO:verifyArrangersMailing:SQLException"+e.getMessage());
			}
			sqlex.printStackTrace();
			log.error("QuotationRequestDAO:verifyArrangersMailing:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:verifyArrangersMailing:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,con);	
		}
		return bean;
		
	}
	public void getBankDetails(String accountno)throws ServiceNotAvailableException, EPISException
	{
		QuotationRequestBean bean=null;
		List list=new ArrayList();
		try{
			String query="select BANKNAME,BRANCHNAME,decode(ACCOUNTTYPE,'S','Saving','C','Current') ACCOUNTTYPE, CSGLACCOUNTNO,PANNO from cb_bank_info where ACCOUNTNO='"+accountno+"'";
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				rs=st.executeQuery(query);
				if(rs.next())
				{
					bean=new QuotationRequestBean();
					bean.setBankName(StringUtility.checknull(rs.getString("BANKNAME")));
					bean.setBranch(StringUtility.checknull(rs.getString("BRANCHNAME")));
					bean.setAccountType(StringUtility.checknull(rs.getString("ACCOUNTTYPE")));
					bean.setCsglAccountNo(StringUtility.checknull(rs.getString("CSGLACCOUNTNO")));
					bean.setPanNo(StringUtility.checknull(rs.getString("PANNO")));
				}
				
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:verifyArrangersMailing:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,con);	
		}
		
	}
	public String getDesignation(String pensionNo)throws ServiceNotAvailableException, EPISException
	{
		String designation=null;
		try{
			con=DBUtility.getConnection();
			String designationQuery="Select designation from epis_user where USERID="+StringUtility.checknull(pensionNo);
			
			if(con!=null)
			{
				st=con.createStatement();
				
					
					rs=st.executeQuery(designationQuery);
					if(rs.next())
					{
						designation=StringUtility.checknull(rs.getString("designation"));
					}
					
				
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("QuotationRequestDAO:getDesignation:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		
		finally{
			DBUtility.closeConnection(null,st,con);	
		}
		
		return designation;
	}
	

}
