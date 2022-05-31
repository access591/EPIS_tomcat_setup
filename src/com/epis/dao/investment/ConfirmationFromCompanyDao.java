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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.bean.investment.FormFillingAppBean;
import com.epis.bean.investment.FormFillingBean;
import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ConfirmationFromCompanyDao {
	Connection con=null;
	ResultSet rs=null;
	Statement st=null;
	PreparedStatement pstmt=null;
	PreparedStatement prepare=null;
	Log log=new Log(ConfirmationFromCompanyDao.class);
	
	private ConfirmationFromCompanyDao()
	{
		
	}
	private static final ConfirmationFromCompanyDao confirmdao=new ConfirmationFromCompanyDao();
	public static ConfirmationFromCompanyDao getInstance()
	{
		return confirmdao;
	}
	
	public List searchConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		List list=new ArrayList();
		try{
			ConfirmationFromCompany confirmationbean=null;
			String selectQuery=null;
			con=DBUtility.getConnection();
			if(con!=null)
			{
			st=con.createStatement();
			if(bean.getMode().equals("rbiauction"))
			selectQuery="select COMPANY_CD,nvl(company.CONFIRMATION,'N')CONFIRMATION,nvl(company.CONFIRMATION,'C')saveflag,company.LETTER_NO,company.PROPOSAL_REF_NO,company.TRUSTTYPE,company.CATEGORYCD,nvl(company.SURITY_FULLNAME,company.SECURITY_NAME)SECURITY_NAME,to_char(company.SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,to_char(company.MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,Nvl(APPROVED_1,'N')||'|'||  Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N')  flags,Decode('A',APPROVED_7,'Approved_7',APPROVED_6,'Approved_6',APPROVED_5,'Approved_5',APPROVED_4,'Approved_4',APPROVED_3,  'Approved_3',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status,(case when (select count(*) from invest_bankletter data where data.PROPOSAL_REF_NO||data.SECURITY_NAME||data.SURITY_FULLNAME = company.PROPOSAL_REF_NO||company.SECURITY_NAME||company.SURITY_FULLNAME )>0 then 'Y' else 'N' end)  qdata from invest_confirmation_company company where company.MODULE_FLAG is null and company.CATEGORYCD<>'PSU' AND MARKET_TYPE<>'P'  AND COMPANY_CD is not null ";
			else if(bean.getMode().equals("psuprimary"))
				selectQuery="select COMPANY_CD,nvl(company.CONFIRMATION,'N')CONFIRMATION,nvl(company.CONFIRMATION,'C')saveflag,company.LETTER_NO,company.PROPOSAL_REF_NO,company.TRUSTTYPE,company.CATEGORYCD,nvl(register.SECURITY_NAME,'--')SECURITY_NAME,to_char(company.SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,to_char(company.MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,Nvl(APPROVED_1,'N')||'|'||  Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N')  flags,Decode('A',APPROVED_7,'Approved_7',APPROVED_6,'Approved_6',APPROVED_5,'Approved_5',APPROVED_4,'Approved_4',APPROVED_3,  'Approved_3',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status,(case when (select count(*) from investment_register data where data.LETTER_NO = company.LETTER_NO )>0 then 'Y' else 'N' end)  qdata from invest_confirmation_company company,investment_register register where company.CATEGORYCD='PSU' AND MARKET_TYPE='P' and company.PROPOSAL_REF_NO=register.PROPOSAL_REF_NO(+) And COMPANY_CD is not null ";
				
			if(!bean.getLetterNo().equals(""))
			{
				selectQuery +=" and upper(company.LETTER_NO) like upper('"+bean.getLetterNo()+"%')";
			}
			if(!bean.getRefNo().equals(""))
			{
				selectQuery +=" and upper(company.PROPOSAL_REF_NO) like upper('"+bean.getRefNo()+"%')";
			}
			if(!bean.getSecurityCategory().equals(""))
			{
				selectQuery +=" and upper(company.CATEGORYCD) like upper('"+bean.getSecurityCategory()+"%')";
			}
			if(!bean.getSettlementDate().equals(""))
			{
				selectQuery +=" and company.SETTLEMENT_DATE>='"+bean.getSettlementDate()+"'";
			}
			selectQuery +=" order by company.CREATED_DT desc ";
			rs=DBUtility.getRecordSet(selectQuery,st);
			while(rs.next())
			{
				confirmationbean=new ConfirmationFromCompany();
				confirmationbean.setCompanyCd(StringUtility.checknull(rs.getString("COMPANY_CD")));
				confirmationbean.setConfirm(StringUtility.checknull(rs.getString("CONFIRMATION")));
				confirmationbean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
				confirmationbean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
				confirmationbean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
				confirmationbean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
				confirmationbean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
				confirmationbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
				confirmationbean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
				confirmationbean.setStatus(StringUtility.checknull(rs.getString("STATUS")));
				confirmationbean.setFlags(StringUtility.checknull(rs.getString("flags")));
				confirmationbean.setHasQuotations(StringUtility.checknull(rs.getString("QDATA")));
				confirmationbean.setSaveflag(StringUtility.checknull(rs.getString("saveflag")));
				list.add(confirmationbean);
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
			log.error("ConfirmationCompanyDAO:searchLettertoBank:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
		
		
	}
	
	public void saveConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		String companyCd="";
		String letterNo="";
		String interestDate="";
		String interestDate1="";
		String updatequery="";
		try{
			con=DBUtility.getConnection();
			
			if(con!=null)
			{
				
				Date today = Calendar.getInstance().getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("MMM");
				String currentMonth = formatter.format(today);
				com.epis.utilities.DateValidation datevalidation=new com.epis.utilities.DateValidation();
				String year=datevalidation.getLetterYear(bean.getDealDate());
				companyCd =AutoGeneration.getNextCode("invest_confirmation_company","COMPANY_CD",5,con);
				letterNo=AutoGeneration.getNextCodeGBy("invest_confirmation_company","LETTER_NO",23,"FA/"+bean.getSecurityCategory()+"/INVEST/"+year,con);
				DBUtility.setAutoCommit(false,con);
				String Savequery="INSERT INTO invest_confirmation_company(COMPANY_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,MARKET_TYPE,AMT_INV,NO_OF_BONDS,CONFIRMATION,DEALDATE,SETTLEMENT_DATE,MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,CALL_DATE,MODEOF_INTEREST,INTEREST_DATE,INTEREST_DATE1,PURCHASE_PRICE,REMARKS,CREATED_BY,SECURITY_NAME,SURITY_FULLNAME,CREATED_DT,preperation_confirmation) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,'Y')";
				if(bean.getMode().equals("psuprimary"))
				updatequery="UPDATE invest_formfilling SET NO_OF_BONDS=? WHERE PROPOSAL_REF_NO=?";
				/*else if(bean.getMode().equals("preperationrbiauction"))
					updatequery="UPDATE invest_formfilling SET NO_OF_BONDS=? WHERE PROPOSAL_REF_NO=? and SECURITY_NAME=?";*/
				
				pstmt=con.prepareStatement(Savequery);
				if(pstmt!=null)
				{
					if(!StringUtility.checknull(bean.getInterestDate()).equals(""))
						interestDate=bean.getInterestDate()+"/"+bean.getInterestMonth();
						if(!StringUtility.checknull(bean.getInterestDate1()).equals(""))
							interestDate1=bean.getInterestDate1()+"/"+bean.getInterestMonth1();
					pstmt.setString(1,companyCd);
					pstmt.setString(2,letterNo);
					pstmt.setString(3,bean.getRefNo());
					pstmt.setString(4,bean.getTrustType());
					pstmt.setString(5,bean.getSecurityCategory());
					pstmt.setString(6,bean.getMarketType());
					pstmt.setString(7,bean.getAmountInv());
					pstmt.setString(8,bean.getNoofBonds());
					pstmt.setString(9,bean.getConfirm());
					pstmt.setString(10,bean.getDealDate());
					pstmt.setString(11,bean.getSettlementDate());
					pstmt.setString(12,bean.getMaturityDate());
					pstmt.setString(13,bean.getCuponRate());
					pstmt.setString(14,bean.getPremiumPaid());
					pstmt.setString(15,bean.getFaceValue());
					pstmt.setString(16,bean.getPurchaseOption());
					pstmt.setString(17,bean.getOfferedPrice());
					pstmt.setString(18,bean.getYtmValue());
					pstmt.setString(19,bean.getCallOption());
					pstmt.setString(20,bean.getCallDate());
					pstmt.setString(21,bean.getModeOfInterest());
					pstmt.setString(22,interestDate);
					pstmt.setString(23,interestDate1);
					pstmt.setString(24,bean.getPurchasePrice());
					pstmt.setString(25,bean.getRemarks());
					pstmt.setString(26,bean.getLoginUserId());
					pstmt.setString(27,bean.getSecurityName());
					pstmt.setString(28,bean.getSecurityFullName());
					pstmt.executeUpdate();
					
				}
				
				else
				{
					throw new ServiceNotAvailableException();
				}
				if(bean.getMode().equals("psuprimary"))
				{
				prepare=con.prepareStatement(updatequery);
				if(prepare!=null)
				{
						prepare.setString(1,bean.getNoofBonds());
						prepare.setString(2,bean.getRefNo());
						prepare.executeUpdate();
					
				}
				else
				{
					throw new ServiceNotAvailableException();
				}
				}
				
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
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
			log.error("ConfirmationCompanyDAO:searchLettertoBank:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	
	public void updateConfirmation(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		log.info("the letterno in update confirmation is...."+bean.getLetterNo());
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				String updateConfirmation="update invest_confirmation_company set CONFIRMATION='Y',CONFIRMATIONREMARKS='"+bean.getConfirmationRemarks()+"',CONFIRMATIONDATE='"+bean.getConfirmationDate()+"' where LETTER_NO=?";
				pstmt=con.prepareStatement(updateConfirmation);
				if(pstmt!=null)
				{
					pstmt.setString(1,StringUtility.checknull(bean.getLetterNo()));
					pstmt.executeUpdate();
				}
				else
				{
					throw new ServiceNotAvailableException();
				}
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
			log.error("ConfirmationCompanyDAO:searchLettertoBank:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	
	
	public ConfirmationFromCompany findConfirmationCompany(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		
		ConfirmationFromCompany bean=null;
		
		try{
			String selectQuery="SELECT COMPANY_CD,LETTER_NO,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,MARKET_TYPE,SECURITY_NAME,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','O','OpenBid') MARKET_TYPEdef,AMT_INV,NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid Per Unit','D','Discount Per Unit')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,INTEREST_DATE INTEREST_DATE,INTEREST_DATE1 INTEREST_DATE1,to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon') dispinterestdate,to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon') dispinterestdate1,PURCHASE_PRICE,nvl(REMARKS,'')REMARKS FROM invest_confirmation_company where LETTER_NO='"+letterNo+"'";
			
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				rs=DBUtility.getRecordSet(selectQuery,st);
				if(rs.next())
				{
					bean=new ConfirmationFromCompany();
					bean.setCompanyCd(StringUtility.checknull(rs.getString("COMPANY_CD")));
					bean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
					bean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
					bean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
					
					
					bean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
					bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
					bean.setMarketTypedef(StringUtility.checknull(rs.getString("MARKET_TYPEDEF")));
					bean.setAmountInv(StringUtility.checknull(rs.getString("AMT_INV")));
					
					bean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					
					bean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
					bean.setConfirm(StringUtility.checknull(rs.getString("CONFIRMATION")));
					bean.setConfirmationDef(StringUtility.checknull(rs.getString("CONFIRMATIONDEF")));
					bean.setDealDate(StringUtility.checknull(rs.getString("DEALDATE")));
					
					
					
					bean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
					bean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
					bean.setCuponRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
					bean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
					
					
					bean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
					
					
					bean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
					bean.setPurchaseoptionDef(StringUtility.checknull(rs.getString("PURCHASE_OPTIONDEF")));
					bean.setOfferedPrice(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
					bean.setYtmValue(StringUtility.checknull(rs.getString("YTM_VALUE")));
					
					
					
					bean.setCallOption(StringUtility.checknull(rs.getString("CALL_OPTION")));
					bean.setCalloptionDef(StringUtility.checknull(rs.getString("CALLOPTIONDEF")));
					bean.setCallDate(StringUtility.checknull(rs.getString("CALL_DATE")));
					bean.setModeOfInterest(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
					
					bean.setModeofinterestDef(StringUtility.checknull(rs.getString("MODEOFINTERESTDEF")));
					String interestDate=StringUtility.checknull(rs.getString("INTEREST_DATE"));
					String interestDate1=StringUtility.checknull(rs.getString("INTEREST_DATE1"));
					
					if(!interestDate.equals(""))
					{
						bean.setInterestDate(interestDate.substring(0,interestDate.indexOf("/")));
						bean.setInterestMonth(interestDate.substring(interestDate.indexOf("/")+1,interestDate.length()));
					}
					if(!interestDate1.equals(""))
					{
						bean.setInterestDate1(interestDate1.substring(0,interestDate1.indexOf("/")));
						bean.setInterestMonth1(interestDate1.substring(interestDate1.indexOf("/")+1,interestDate1.length()));
					}
					bean.setDispinterestdate(StringUtility.checknull(rs.getString("dispinterestdate")));
					bean.setDispinterestdate1(StringUtility.checknull(rs.getString("dispinterestdate1")));
					bean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
					
					bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
					
					
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
			log.error("ConfirmationCompanyDAO:searchLettertoBank:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return bean;
	}
	
	public void editConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		
		try
		{
			 String interestDate="";
			 String interestDate1="";
			con=DBUtility.getConnection();
			if(con!=null)
			{
				if(!StringUtility.checknull(bean.getInterestDate()).equals(""))
					interestDate=bean.getInterestDate()+"/"+bean.getInterestMonth();
					if(!StringUtility.checknull(bean.getInterestDate1()).equals(""))
						interestDate1=bean.getInterestDate1()+"/"+bean.getInterestMonth1();
				DBUtility.setAutoCommit(false,con);
				st=con.createStatement();
				String upadeQuery="update invest_confirmation_company set CONFIRMATION='"+bean.getConfirm()+"',DEALDATE='"+bean.getDealDate()+"',SETTLEMENT_DATE='"+bean.getSettlementDate()+"',CUPON_RATE='"+bean.getCuponRate()+"',FACEVALUE='"+bean.getFaceValue()+"',NO_OF_BONDS='"+bean.getNoofBonds()+"',PURCHASE_OPTION='"+bean.getPurchaseOption()+"',PREMIUM_PAID='"+bean.getPremiumPaid()+"',OFFERED_PRICE='"+bean.getOfferedPrice()+"',PURCHASE_PRICE='"+bean.getPurchasePrice()+"',YTM_VALUE='"+bean.getYtmValue()+"',CALL_OPTION='"+bean.getCallOption()+"',MODEOF_INTEREST='"+bean.getModeOfInterest()+"',MATURITY_DATE='"+bean.getMaturityDate()+"',INTEREST_DATE='"+interestDate+"',INTEREST_DATE1='"+interestDate1+"',CALL_DATE='"+bean.getCallDate()+"',REMARKS='"+bean.getRemarks()+"',UPDATED_BY='"+bean.getLoginUserId()+"',UPDATED_DT=sysdate where LETTER_NO='"+bean.getLetterNo()+"'";
				String updateQuery1="UPDATE invest_formfilling SET NO_OF_BONDS='"+bean.getNoofBonds()+"' WHERE PROPOSAL_REF_NO='"+bean.getRefNo()+"'";
				st.executeUpdate(upadeQuery);
				st.executeUpdate(updateQuery1);
				DBUtility.setAutoCommit(false,con);
			}
			else
			{
					throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(Exception e){
			try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
	}
	public void deleteConfirmationCompany(String letterNo)throws  EPISException,ServiceNotAvailableException 
	{
		try
		{
			con=DBUtility.getConnection();
			DBUtility.setAutoCommit(false,con);
			if(con!=null)
			{
				st=con.createStatement();
				String deleteQuery="delete from invest_confirmation_company where LETTER_NO='"+letterNo+"'";
				log.info("the login query is..."+deleteQuery);
				st.executeUpdate(deleteQuery);
				DBUtility.setAutoCommit(true,con);
			}
			else
			{
					throw new ServiceNotAvailableException();
			}
			
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(Exception e){
			try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
		
	}
	public String getproposalRefno(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		String refNO="";
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				String query="select PROPOSAL_REF_NO from invest_confirmation_company where letter_no='"+letterNo+"'";
				st=con.createStatement();
				rs=DBUtility.getRecordSet(query,st);
				if(rs.next())
				{
					refNO=StringUtility.checknull(rs.getString("PROPOSAL_REF_NO"));
				}
			}
			
			else
			{
					throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(Exception e){
			try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("ConfirmationCompanyDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return refNO;
	}
	public ConfirmationFromCompany findapprovalconfirmation(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		ConfirmationFromCompany bean=null;
		String approvalQuery="";
		try{
				approvalQuery="Select COMPANY_CD,LETTER_NO,PROPOSAL_REF_NO,form.TRUSTTYPE,form.MARKET_TYPE,prop.SUBJECT,form.CATEGORYCD,decode(form.MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','O','Open Bid','PS','Primary And Secondary') MARKET_TYPEDEF,form.AMT_INV,NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid Per Unit','D','Discount Per Unit')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')INTEREST_DATE,nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'')INTEREST_DATE1,PURCHASE_PRICE,nvl(form.REMARKS,'')REMARKS,Nvl(form.APPROVED_1,'N') APPROVED_1, to_char(form.APPROVE_1_DT,'dd/Mon/yyyy')  APPROVE_1_DT ,Nvl(form.APPROVAL_1_REMARKS,' ') APPROVAL_1_REMARKS,Nvl(form.APPROVED_2,'N') APPROVED_2, to_char(form.APPROVE_2_DT, 'dd/Mon/yyyy') APPROVE_2_DT ,Nvl(form.APPROVAL_2_REMARKS,'  ') APPROVAL_2_REMARKS,Nvl(form.APPROVED_3,'N') APPROVED_3, to_char(form.APPROVE_3_DT,'dd/Mon/yyyy') APPROVE_3_DT ,Nvl(form.APPROVAL_3_REMARKS,'  ') APPROVAL_3_REMARKS,Nvl(form.APPROVED_4,'N') APPROVED_4, to_char(form.APPROVE_4_DT,'dd/Mon/yyyy') APPROVE_4_DT ,Nvl(form.APPROVAL_4_REMARKS,'  ') APPROVAL_4_REMARKS,Nvl(form.APPROVED_5,'N') APPROVED_5, to_char(form.APPROVE_5_DT,'dd/Mon/yyyy') APPROVE_5_DT , Nvl(form.APPROVAL_5_REMARKS,'  ') APPROVAL_5_REMARKS,nvl(form.APPROVED_6,'N')APPROVED_6,to_char(form.APPROVE_6_DT,'dd/Mon/yyyy')APPROVE_6_DT,nvl(form.APPROVAL_6_REMARKS,' ')APPROVAL_6_REMARKS,nvl(form.APPROVED_7,'N')APPROVED_7,to_char(form.APPROVE_7_DT,'dd/Mon/yyyy')APPROVE_7_DT,nvl(form.APPROVAL_7_REMARKS,' ')APPROVAL_7_REMARKS,form.CREATED_BY,form.APPROVAL_1_BY,form.APPROVAL_2_BY,form.APPROVAL_3_BY, form.APPROVAL_4_BY, form.APPROVAL_5_BY,form.APPROVAL_6_BY,form.APPROVAL_7_BY,Decode('A',form.APPROVED_7,'7',form.APPROVED_6,'6',form.APPROVED_5,'5',form.APPROVED_4,'4',form.APPROVED_3,'3',form.APPROVED_2,'2',form.APPROVED_1,'1','0') flag,to_char(Decode('A',form.APPROVED_7,form.APPROVE_7_DT,form.APPROVED_6,form.APPROVE_6_DT,form.APPROVED_5,form.APPROVE_5_DT,form.APPROVED_4,form.APPROVE_4_DT,form.APPROVED_3,form.APPROVE_3_DT,form.APPROVED_2,form.APPROVE_2_DT,form.APPROVED_1,form.APPROVE_1_DT,PROPOSALDATE),'dd/Mon/YYYY') appDate ,to_char(prop.PROPOSALDATE,'dd/Mon/YYYY')PROPOSALDATE,nvl(SUBJECT,'')SUBJECT,nvl(prop.REMARKS,'--')REMARKS,form.CONFIRMATIONREMARKS CONFIRMATIONREMARKS,to_char(form.CONFIRMATIONDATE,'dd/Mon/yyyy') CONFIRMATIONDATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,nvl(SURITY_FULLNAME,'--')SURITY_FULLNAME from invest_confirmation_company form,invest_proposal prop  where form.PROPOSAL_REF_NO=prop.REF_NO and LETTER_NO=?";
				
				 
				con=DBUtility.getConnection();
				if(con!=null)
				{
					pstmt=con.prepareStatement(approvalQuery);
					if(pstmt!=null)
					{
						pstmt.setString(1,StringUtility.checknull(letterNo));
						rs=pstmt.executeQuery();
						if(rs.next())
						{
							bean=new ConfirmationFromCompany();
							bean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
							bean.setRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
							bean.setProposaldate(StringUtility.checknull(rs.getString("PROPOSALDATE")));
							bean.setSubject(StringUtility.checknull(rs.getString("SUBJECT")));
							bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
							bean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
							
							bean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
							bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
							bean.setMarketTypedef(StringUtility.checknull(rs.getString("MARKET_TYPEDEF")));
							bean.setAmountInv(StringUtility.checknull(rs.getString("AMT_INV")));
							
							
							bean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
							bean.setConfirm(StringUtility.checknull(rs.getString("CONFIRMATION")));
							bean.setConfirmationDef(StringUtility.checknull(rs.getString("CONFIRMATIONDEF")));
							bean.setDealDate(StringUtility.checknull(rs.getString("DEALDATE")));
							
							
							
							bean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
							bean.setMaturityDate(StringUtility.checknull(rs.getString("MATURITY_DATE")));
							bean.setCuponRate(StringUtility.checknull(rs.getString("CUPON_RATE")));
							bean.setPremiumPaid(StringUtility.checknull(rs.getString("PREMIUM_PAID")));
							
							bean.setFaceValue(StringUtility.checknull(rs.getString("FACEVALUE")));
							
							
							bean.setPurchaseOption(StringUtility.checknull(rs.getString("PURCHASE_OPTION")));
							bean.setPurchaseoptionDef(StringUtility.checknull(rs.getString("PURCHASE_OPTIONDEF")));
							bean.setOfferedPrice(StringUtility.checknull(rs.getString("OFFERED_PRICE")));
							bean.setYtmValue(StringUtility.checknull(rs.getString("YTM_VALUE")));
							
							
							
							bean.setCallOption(StringUtility.checknull(rs.getString("CALL_OPTION")));
							bean.setCalloptionDef(StringUtility.checknull(rs.getString("CALLOPTIONDEF")));
							bean.setCallDate(StringUtility.checknull(rs.getString("CALL_DATE")));
							bean.setModeOfInterest(StringUtility.checknull(rs.getString("MODEOF_INTEREST")));
							
							bean.setModeofinterestDef(StringUtility.checknull(rs.getString("MODEOFINTERESTDEF")));
							bean.setInterestDate(StringUtility.checknull(rs.getString("INTEREST_DATE")));
							bean.setInterestDate1(StringUtility.checknull(rs.getString("INTEREST_DATE1")));
							bean.setPurchasePrice(StringUtility.checknull(rs.getString("PURCHASE_PRICE")));
							
							bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
							bean.setConfirmationRemarks(StringUtility.checknull(rs.getString("CONFIRMATIONREMARKS")));
							bean.setConfirmationDate(StringUtility.checknull(rs.getString("CONFIRMATIONDATE")));
							bean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
							bean.setSecurityFullName(StringUtility.checknull(rs.getString("SURITY_FULLNAME")));
							
							
							bean.setUserId(StringUtility.checknull(rs.getString("CREATED_BY")));
							Map approvals = new LinkedHashMap();
							ConfirmationFromCompanyAppBean appbean=new ConfirmationFromCompanyAppBean();
							appbean.setApprovedBy(bean.getUserId());
							
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_1_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_1_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_1"));
							appbean.setApprovedBy(rs.getString("APPROVAL_1_BY"));
							approvals.put("1",appbean);
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_2_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_2_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_2"));
							appbean.setApprovedBy(rs.getString("APPROVAL_2_BY"));
							approvals.put("2",appbean);
							
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_3_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_3_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_3"));
							appbean.setApprovedBy(rs.getString("APPROVAL_3_BY"));
							approvals.put("3",appbean);
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_4_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_4_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_4"));
							appbean.setApprovedBy(rs.getString("APPROVAL_4_BY"));
							approvals.put("4",appbean);
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_5_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_5_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_5"));
							appbean.setApprovedBy(rs.getString("APPROVAL_5_BY"));
							approvals.put("5",appbean);
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_6_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_6_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_6"));
							appbean.setApprovedBy(rs.getString("APPROVAL_6_BY"));
							approvals.put("6",appbean);
							
							appbean = new ConfirmationFromCompanyAppBean();
							appbean.setDate(rs.getString("APPROVE_7_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_7_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_7"));
							appbean.setApprovedBy(rs.getString("APPROVAL_7_BY"));
							approvals.put("7",appbean);
							
							bean.setApprovals(approvals);							
							bean.setFlags(rs.getString("flag"));
							bean.setAppDate(rs.getString("appDate"));
						}
					}
					else
					{
						throw new ServiceNotAvailableException();
					}
					
				}
				else{
					throw new ServiceNotAvailableException();
				}
				
				 
			
		}
		catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, st, con);
		}	
		return bean;
	}
	
	public void approvalUpdate(ConfirmationFromCompanyAppBean bean)throws
	  ServiceNotAvailableException,EPISException { 
	  
	  
	  
	  try{ 
		  con=DBUtility.getConnection(); 
		  int i = Integer.parseInt(StringUtility.checknull(bean.getApprovalLevel()));	
		  
		  String updateSql ="update invest_confirmation_company set APPROVED_"+i+"='A',APPROVE_"+i+"_DT=?,APPROVAL_"+i+"_REMARKS=?," +
		  		" APPROVAL_"+i+"_BY=?,APPROVAL_"+i+"_DT=SYSDATE where LETTER_NO=?";
		  if(con!=null){
			  pstmt=con.prepareStatement(updateSql); 
			  if(pstmt!=null){
				  pstmt.setString(1,StringUtility.checknull(bean.getDate()));
				  pstmt.setString(2,StringUtility.checknull(bean.getRemarks()));
				  pstmt.setString(3,StringUtility.checknull(bean.getLoginUserId()));
				  pstmt.setString(4,StringUtility.checknull(bean.getLetterNo()));
				  pstmt.executeUpdate(); 
			  }else{ 
				  throw new ServiceNotAvailableException(); 
			  }
		  }else{ 
			  throw new ServiceNotAvailableException(); 
		  }
	  }catch(ServiceNotAvailableException  snex){ 
		  throw snex; 
	  }catch(SQLException sqlex){ 
		  sqlex.printStackTrace();
		  throw new EPISException(sqlex); 
	  }catch(Exception e){ 
		  e.printStackTrace();
		  throw new EPISException(e); 
	  } finally {
		  DBUtility.closeConnection(null,pstmt,con); 
	  } 
}

}
