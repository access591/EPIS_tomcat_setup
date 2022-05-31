package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.investment.FormFillingBean;
import com.epis.bean.investment.LetterToBankBean;
import com.epis.utilities.DateValidation;

public class LetterToBankDao {
	Connection con=null;
	ResultSet rs=null;
	Statement st=null;
	PreparedStatement pstmt=null;
	PreparedStatement prepare=null;
	Log log=new Log(FormFillingDAO.class);
	
	private LetterToBankDao()
	{
		
	}
	private static final LetterToBankDao letterdao=new LetterToBankDao();
	public static LetterToBankDao getInstance()
	{
		return letterdao;
	}
	
	
	public List searchLettertoBank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	{
		List list =new ArrayList();;
		try{
			LetterToBankBean letterbean =null;
			String query=null;
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			if(bean.getMode().equals("psuprimary"))
			query = "select BANK_LETTERNO,BANK_NAME,BENEFICIARY_NAME,to_char(BANKDATE,'dd/Mon/yyyy') BANKDATE,(case when (select count(*) from invest_confirmation_company data where data.PROPOSAL_REF_NO = bankletter.PROPOSAL_REF_NO )>0 then 'Y' else 'N' end)  qdata from invest_bankletter bankletter where CATEGORYCD='PSU' AND MARKET_TYPE='P' AND BANKCD is not null  ";
			else if(bean.getMode().equals("rbiauction"))
			query="select BANK_LETTERNO,nvl(SURITY_FULLNAME,SECURITY_NAME)SECURITY_NAME,SELLER_REF_NO,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,(case when (select count(*) from investment_register data where data.PROPOSAL_REF_NO||data.SECURITY_NAME||data.SURITY_FULLNAME = bankletter.PROPOSAL_REF_NO||bankletter.SECURITY_NAME||bankletter.SURITY_FULLNAME )>0 then 'Y' else 'N' end)  qdata from invest_bankletter bankletter where CATEGORYCD<>'PSU' AND MARKET_TYPE<>'P' AND BANKCD is not null";	
			
			
					
			
			if(!bean.getLetterNo().equals(" ")){
				query +=" and upper(BANK_LETTERNO) like upper('"+bean.getLetterNo()+"%')";
			}
			if(!bean.getRefNo().equals(" ")){
				query +=" and upper(PROPOSAL_REF_NO) like upper('"+bean.getRefNo()+"%')";
			}
			if(!bean.getSecurityCategory().equals(" ")){
				query +=" and upper(CATEGORYCD) like upper('"+bean.getSecurityCategory()+"%')";
			}
			if(!bean.getAccountNo().equals(" ")&& bean.getMode().equals("psuprimary")){
				query +=" and upper(ACCOUNTNO) like upper('"+bean.getAccountNo()+"%')";
			}
			if(!bean.getBeneficiaryName().equals(" ")&& bean.getMode().equals("psuprimary")){
				query +=" and upper(BENEFICIARY_NAME) like upper('"+bean.getBeneficiaryName()+"%')";
			}
			
			if(!bean.getSellerRefNo().equals(" ")&& bean.getMode().equals("rbiauction")){
				query +=" and upper(SELLER_REF_NO) like upper('"+bean.getSellerRefNo()+"%')";
			}
			if(!bean.getSecurityName().equals(" ")&& bean.getMode().equals("rbiauction")){
				query +=" and upper(SECURITY_NAME) like upper('"+bean.getSecurityName()+"%')";
			}
			query +=" order by CREATED_DT DESC";
			log.info("FormFillingSearchDAO:searchFormFilling(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				letterbean=new LetterToBankBean();
				letterbean.setLetterNo(StringUtility.checknull(rs.getString("BANK_LETTERNO")));
				letterbean.setHasQuotations(StringUtility.checknull(rs.getString("QDATA")));
				if(bean.getMode().equals("psuprimary"))
				{
				letterbean.setBankName(StringUtility.checknull(rs.getString("BANK_NAME")));
				letterbean.setBeneficiaryName(StringUtility.checknull(rs.getString("BENEFICIARY_NAME")));
				letterbean.setBankDate(StringUtility.checknull(rs.getString("BANKDATE")));
				}
				else if(bean.getMode().equals("rbiauction"))
				{
					letterbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					letterbean.setSellerRefNo(StringUtility.checknull(rs.getString("SELLER_REF_NO")));
					letterbean.setDealDate(StringUtility.checknull(rs.getString("DEALDATE")));
					letterbean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
					
				}
					
							
				list.add(letterbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("LetterToBankDAO:searchLettertoBank:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("LetterToBankDAO:searchLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	public String saveLettertoBank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	{
		String bankCd=null;
		String bankLetterNo=null;
		try
		{
			con=DBUtility.getConnection();
			DateValidation d=new DateValidation();
			String year="";
			if(con!=null)
			{
				
				if(bean.getMode().equals("rbiauction"))
				{
					year=d.getLetterYear(bean.getDealDate());
				}
				else if(bean.getMode().equals("psuprimary"))
				{
					year=d.getLetterYear(bean.getBankDate());
				}
					
				
				 bankCd =AutoGeneration.getNextCode("invest_bankLetter","BANKCD",5,con);
				 bankLetterNo=AutoGeneration.getNextCodeGBy("invest_bankLetter","BANK_LETTERNO",20,"AAI/Invest/"+year,con);
				log.info("-----"+bankCd+"++++++++++++++"+bankLetterNo);
				DBUtility.setAutoCommit(false,con);
				
				String insertQuery="insert into invest_bankLetter(BANKCD,BANK_LETTERNO,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,MARKET_TYPE,BANKDATE,BENEFICIARY_NAME,CREDIT_ACCOUNT_NO,ACCOUNTNO,CENTER_LOCATION,BANK_NAME,BRANCH_NAME,ACCOUNT_TYPE,REMITANCE_AMOUNT,AMT_INV,REMARKS,CREATED_BY,IFSCCODE,LEFT_SIGN,RIGHT_SIGN," +
						"SGL_ACCOUNT_NO ,SELLER_REF_NO ,DEALDATE, SETTLEMENT_DATE, SECURITY_NAME,FACEVALUE,OFFERED_PRICE," +
						"PRINCIPAL_AMOUNT,NOOFDAYS_LASTDATE,PAYMENT_VALUE,ACCURED_INTEREST_AMOUNT,SETTLEMENT_AMOUNT,SURITY_FULLNAME," +
						"CREATED_DT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
				pstmt=con.prepareStatement(insertQuery);
				if(pstmt!=null)
				{
					pstmt.setString(1,StringUtility.checknull(bankCd));
					pstmt.setString(2,StringUtility.checknull(bankLetterNo));
					pstmt.setString(3,StringUtility.checknull(bean.getRefNo()));
					pstmt.setString(4,StringUtility.checknull(bean.getTrustType()));
					pstmt.setString(5,StringUtility.checknull(bean.getSecurityCategory()));
					pstmt.setString(6,StringUtility.checknull(bean.getMarketType()));
					pstmt.setString(7,StringUtility.checknull(bean.getBankDate()));
					pstmt.setString(8,StringUtility.checknull(bean.getBeneficiaryName()));
					pstmt.setString(9,StringUtility.checknull(bean.getCreditAccountNo()));
					pstmt.setString(10,StringUtility.checknull(bean.getAccountNo()));
					pstmt.setString(11,StringUtility.checknull(bean.getCenterLocation()));
					pstmt.setString(12,StringUtility.checknull(bean.getBankName()));
					pstmt.setString(13,StringUtility.checknull(bean.getBranchName()));
					pstmt.setString(14,StringUtility.checknull(bean.getAccountType()));
					pstmt.setString(15,StringUtility.checknull(bean.getRemitanceAmt()));
					pstmt.setString(16,StringUtility.checknull(bean.getAmountInv()));
					pstmt.setString(17,StringUtility.checknull(bean.getRemarks()));
					pstmt.setString(18,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.setString(19,StringUtility.checknull(bean.getIfscCode()));
					pstmt.setString(20,StringUtility.checknull(bean.getLeftSign()));
					pstmt.setString(21,StringUtility.checknull(bean.getRightSign()));
					pstmt.setString(22,StringUtility.checknull(bean.getSglAccountNo()));
					pstmt.setString(23,StringUtility.checknull(bean.getSellerRefNo()));
					pstmt.setString(24,StringUtility.checknull(bean.getDealDate()));
					pstmt.setString(25,StringUtility.checknull(bean.getSettlementDate()));
					pstmt.setString(26,StringUtility.checknull(bean.getSecurityName()));
					pstmt.setString(27,StringUtility.checknull(bean.getFaceValue()));
					pstmt.setString(28,StringUtility.checknull(bean.getOfferedPrice()));
					pstmt.setString(29,StringUtility.checknull(bean.getPrincipalAmount()));
					pstmt.setString(30,StringUtility.checknull(bean.getNoofdays()));
					pstmt.setString(31,StringUtility.checknull(bean.getPaymentValue()));
					pstmt.setString(32,StringUtility.checknull(bean.getAccuredInterestAmount()));
					pstmt.setString(33,StringUtility.checknull(bean.getSettlementAmount()));
					pstmt.setString(34,StringUtility.checknull(bean.getSecurityFullName()));
					
					
					
					pstmt.executeUpdate();
				}
				else{
					throw new ServiceNotAvailableException();
				}
				
				pstmt.close();
			}
			else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
			
		}
		catch(Exception e){
			
			log.error("LetterToBankDAO:saveLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
		return bankLetterNo;
	}
	public String getSecurityName(String refNo)throws EPISException,ServiceNotAvailableException
	{
		String securityName=null;
		try{
		String strquery="select SECURITY_NAME from invest_formfilling where PROPOSAL_REF_NO='"+refNo+"'";
		con=DBUtility.getConnection();
		if(con!=null)
		{
			st=con.createStatement();
			rs=DBUtility.getRecordSet(strquery,st);
			if(rs.next())
			{
				securityName=StringUtility.checknull(rs.getString("SECURITY_NAME"));
			}
			
		}
		else{
			throw new ServiceNotAvailableException();
		}
		}
		catch(Exception e){
			
			log.error("LetterToBankDAO:saveLettertoBank:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
		return securityName;
	}
	
	public String getaaiaccountNo(String refNo)throws EPISException,ServiceNotAvailableException
	{
		String aaiaccountNo=null;
		try{
		String strquery="select nvl(ACCOUNTNO,' ')ACCOUNTNO from invest_formfilling where PROPOSAL_REF_NO='"+refNo+"'";
		con=DBUtility.getConnection();
		if(con!=null)
		{
			st=con.createStatement();
			rs=DBUtility.getRecordSet(strquery,st);
			if(rs.next())
			{
				aaiaccountNo=StringUtility.checknull(rs.getString("ACCOUNTNO"));
			}
			
		}
		else{
			throw new ServiceNotAvailableException();
		}
		}
		catch(Exception e){
			
			log.error("LetterToBankDAO:getaaiaccountNo:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
		return aaiaccountNo;

	}
	 public String getBankAddress(String accountNo)throws EPISException,ServiceNotAvailableException
		{
			String acc=null;
			try
			{
				con=DBUtility.getConnection();
				String str="select ADDRESS from cb_bank_info where ACCOUNTNO='"+accountNo+"'";
				 st=con.createStatement();
				ResultSet rs=st.executeQuery(str);
				while(rs.next())
				{
					acc=StringUtility.checknull(rs.getString("ADDRESS"));
					
				}
				
			}
			catch(Exception e){
				
				log.error("LetterToBankDAO:saveLettertoBank:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs,st,con);			
			}
			log.info("+++++++++++++lettertobankAddress"+acc);
			return acc;
		}
	 public String getBankAccountType(String accountNo)throws EPISException,ServiceNotAvailableException
		{
			String acctype=null;
			try
			{
				con=DBUtility.getConnection();
				String str="select decode(ACCOUNTTYPE,'S','Saving','C','Current')ACCOUNTTYPE from cb_bank_info where ACCOUNTNO='"+accountNo+"'";
				 st=con.createStatement();
				ResultSet rs=st.executeQuery(str);
				if(rs.next())
				{
					acctype=StringUtility.checknull(rs.getString("ACCOUNTTYPE"));
				}
			}
			catch(Exception e){
				
				log.error("LetterToBankDAO:saveLettertoBank:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs,st,con);			
			}
			log.info("+++++++++++++Address"+acctype);
			return acctype;
		}
	 public LetterToBankBean findLetterToBank(String letterNo)throws EPISException,ServiceNotAvailableException
	 {
		 LetterToBankBean bean=new LetterToBankBean();
		 
		 Statement stmt1=null;
		 ResultSet rs1=null;
		 try
		 {
			 String selctQyery="select BANKCD,BANK_LETTERNO,PROPOSAL_REF_NO,letter.TRUSTTYPE,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','RB','RBI OIL BONDS','O','OpenBid') MARKET_TYPEdef," +
			 		"MARKET_TYPE,to_char(BANKDATE,'dd/Mon/yyyy')BANKDATE,nvl(letter.IFSCCODE,'')IFSCCODE,nvl(LEFT_SIGN,'')LEFT_SIGN,nvl(RIGHT_SIGN,'')RIGHT_SIGN,BENEFICIARY_NAME,CREDIT_ACCOUNT_NO," +
			 		"letter.ACCOUNTNO,CENTER_LOCATION,BANK_NAME,BRANCH_NAME,nvl(decode(letter.ACCOUNT_TYPE,'S','SavingsAccount','C','Current Account'),'--')ACCOUNTTYPEdef,decode(info.ACCOUNTTYPE,'S','SavingsAccount','C','Current Account')accountypedefaai," +
			 		"ACCOUNT_TYPE,CATEGORYCD,REMITANCE_AMOUNT,AMT_INV,NVL(REMARKS,'')REMARKS,nvl(SGL_ACCOUNT_NO,'')SGL_ACCOUNT_NO,nvl(SELLER_REF_NO,'')SELLER_REF_NO,nvl(to_char(DEALDATE,'dd/Mon/yyyy'),'')DEALDATE,nvl(to_char(SETTLEMENT_DATE,'dd/Mon/yyyy'),'')SETTLEMENT_DATE,nvl(SECURITY_NAME,'')SECURITY_NAME,nvl(SURITY_FULLNAME,' ')SURITY_FULLNAME,nvl(FACEVALUE,0)FACEVALUE,nvl(OFFERED_PRICE,0)OFFERED_PRICE,nvl(PRINCIPAL_AMOUNT,0)PRINCIPAL_AMOUNT,nvl(NOOFDAYS_LASTDATE,0)NOOFDAYS_LASTDATE,nvl(PAYMENT_VALUE,0)PAYMENT_VALUE,nvl(ACCURED_INTEREST_AMOUNT,0)ACCURED_INTEREST_AMOUNT,nvl(SETTLEMENT_AMOUNT,0)SETTLEMENT_AMOUNT,(select nvl(ACCOUNTNO,'')ACCOUNTNO from invest_formfilling  where proposal_ref_no||security_name=letter.proposal_ref_no||letter.security_name) AAIAccountNo FROM invest_bankletter letter,cb_bank_info info WHERE letter.ACCOUNTNO=info.ACCOUNTNO(+) and BANK_LETTERNO='"+letterNo+"'";
			
			 log.info(selctQyery);
			 con=DBUtility.getConnection();
			 if(con!=null)
			 {
				 stmt1=con.createStatement();
				 rs1=DBUtility.getRecordSet(selctQyery,stmt1);
				
			 	if(rs1.next())
			 	{
			 		bean.setBankCd(StringUtility.checknull(rs1.getString("BANKCD")));
			 		bean.setLetterNo(StringUtility.checknull(rs1.getString("BANK_LETTERNO")));
			 		String PROPOSAL_REF_NO=StringUtility.checknull(rs1.getString("PROPOSAL_REF_NO"));
			 		bean.setRefNo(PROPOSAL_REF_NO);
			 		bean.setTrustType(StringUtility.checknull(rs1.getString("TRUSTTYPE")));
			 		
			 		String secNmae=getSecurityName(PROPOSAL_REF_NO);
			 		bean.setSecurityName(secNmae);
			 		bean.setMarketTypedef(StringUtility.checknull(rs1.getString("MARKET_TYPEDEF")));
			 		bean.setMarketType(StringUtility.checknull(rs1.getString("MARKET_TYPE")));
			 		bean.setBankDate(StringUtility.checknull(rs1.getString("BANKDATE")));
			 		bean.setBeneficiaryName(StringUtility.checknull(rs1.getString("BENEFICIARY_NAME")));
			 		bean.setCreditAccountNo(StringUtility.checknull(rs1.getString("CREDIT_ACCOUNT_NO")));
			 		String ACCOUNTNO=StringUtility.checknull(rs1.getString("ACCOUNTNO"));
			 		if(ACCOUNTNO.equals(""))
			 			ACCOUNTNO=StringUtility.checknull(rs1.getString("AAIAccountNo"));
			 		bean.setBankAddress(StringUtility.checknull(getBankAddress(ACCOUNTNO)));
			 		bean.setAccountNo(ACCOUNTNO);
			 		bean.setCenterLocation(StringUtility.checknull(rs1.getString("CENTER_LOCATION")));
			 		bean.setBankName(StringUtility.checknull(rs1.getString("BANK_NAME")));
			 		bean.setBranchName(StringUtility.checknull(rs1.getString("BRANCH_NAME")));
			 		bean.setAccountTypedef(StringUtility.checknull(rs1.getString("ACCOUNTTYPEdef")));
			 		bean.setAccountTypaai(StringUtility.checknull(rs1.getString("accountypedefaai")));
			 		bean.setAccountType(StringUtility.checknull(rs1.getString("ACCOUNT_TYPE")));
			 		bean.setIfscCode(StringUtility.checknull(rs1.getString("IFSCCODE")));
			 		bean.setLeftSign(StringUtility.checknull(rs1.getString("LEFT_SIGN")));
			 		bean.setRightSign(StringUtility.checknull(rs1.getString("RIGHT_SIGN")));
			 		bean.setSecurityCategory(StringUtility.checknull(rs1.getString("CATEGORYCD")));
			 		bean.setRemitanceAmt(StringUtility.checknull(rs1.getString("REMITANCE_AMOUNT")));
			 		bean.setAmountInv(StringUtility.checknull(rs1.getString("AMT_INV")));
			 		bean.setRemarks(StringUtility.checknull(rs1.getString("REMARKS")));
			 		bean.setSglAccountNo(StringUtility.checknull(rs1.getString("SGL_ACCOUNT_NO")));
			 		bean.setSellerRefNo(StringUtility.checknull(rs1.getString("SELLER_REF_NO")));
			 		bean.setDealDate(StringUtility.checknull(rs1.getString("DEALDATE")));
			 		bean.setSettlementDate(StringUtility.checknull(rs1.getString("SETTLEMENT_DATE")));
			 		bean.setSecurityName(StringUtility.checknull(rs1.getString("SECURITY_NAME")));
			 		bean.setSecurityFullName(StringUtility.checknull(rs1.getString("SURITY_FULLNAME")));
			 		bean.setFaceValue(StringUtility.checknull(rs1.getString("FACEVALUE")));
			 		bean.setOfferedPrice(StringUtility.checknull(rs1.getString("OFFERED_PRICE")));
			 		bean.setPrincipalAmount(StringUtility.checknull(rs1.getString("PRINCIPAL_AMOUNT")));
			 		bean.setNoofdays(StringUtility.checknull(rs1.getString("NOOFDAYS_LASTDATE")));
			 		bean.setPaymentValue(StringUtility.checknull(rs1.getString("PAYMENT_VALUE")));
			 		bean.setAccuredInterestAmount(StringUtility.checknull(rs1.getString("ACCURED_INTEREST_AMOUNT")));
			 		bean.setSettlementAmount(StringUtility.checknull(rs1.getString("SETTLEMENT_AMOUNT")));
			 		bean.setAaiaccountNo(StringUtility.checknull(rs1.getString("AAIAccountNo")));
			 		
			 		
				}
			 
			 }
			 else{
					throw new ServiceNotAvailableException();
				}
			 
		 }
		 catch(Exception e){
				
				log.error("LetterToBankDAO:findLetterToBank:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs1,stmt1,con);			
			}
		 return bean;
	 }
	 public String editletterbank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	 {
		 String bankLetterNo=bean.getLetterNo();
		 try
		 {
			 String updatequery="update invest_bankletter set BENEFICIARY_NAME='"+bean.getBeneficiaryName()+"',CREDIT_ACCOUNT_NO='"+bean.getCreditAccountNo()+"',BANKDATE='"+bean.getBankDate()+"',CENTER_LOCATION='"+bean.getCenterLocation()+"',REMITANCE_AMOUNT='"+bean.getRemitanceAmt()+"',REMARKS='"+bean.getRemarks()+"',LEFT_SIGN='"+StringUtility.checknull(bean.getLeftSign())+"',RIGHT_SIGN='"+StringUtility.checknull(bean.getRightSign())+"',SELLER_REF_NO='"+StringUtility.checknull(bean.getSellerRefNo())+"',NOOFDAYS_LASTDATE='"+StringUtility.checknull(bean.getNoofdays())+"',PAYMENT_VALUE='"+StringUtility.checknull(bean.getPaymentValue())+"',ACCURED_INTEREST_AMOUNT='"+StringUtility.checknull(bean.getAccuredInterestAmount())+"',SETTLEMENT_AMOUNT='"+StringUtility.checknull(bean.getSettlementAmount())+"',UPDATED_BY='"+bean.getLoginUserId()+"',UPDATED_DT=sysdate where BANK_LETTERNO=?";
			 log.info("the update query is..."+updatequery);
			 con=DBUtility.getConnection();
			 DBUtility.setAutoCommit(false,con);
			 if(con!=null)
			 {
				 pstmt=con.prepareStatement(updatequery);
				 pstmt.setString(1,bankLetterNo);
			 
				 int i=pstmt.executeUpdate();
				 DBUtility.setAutoCommit(true,con);
				 log.info("++++++++i++++++++++++++++++"+i);
			 }
			 else{
					throw new ServiceNotAvailableException();
				}
			 
		 }
		 catch(Exception e){
			 try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				log.error("LetterToBankDAO:findLetterToBank:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs,pstmt,con);			
			}
			
		 return bankLetterNo;
	 }
	 public void deleteLettertoBank(String letterNo)throws EPISException,ServiceNotAvailableException
	 {
		 try
		 {
			 
			 String deleteQuery="delete from invest_bankletter where BANK_LETTERNO='"+letterNo+"'";
			 con=DBUtility.getConnection();
			 DBUtility.setAutoCommit(false,con);
			 if(con!=null)
			 {
			 st=con.createStatement();
			 st.executeUpdate(deleteQuery);
			 
			 }
			 else{
					throw new ServiceNotAvailableException();
				}
			 DBUtility.commitTrans(con);
		 }
		 catch(Exception e){
			 try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				log.error("LetterToBankDAO:findLetterToBank:Exception:"+e.getMessage());
				throw new EPISException(e);
			}finally {			
					DBUtility.closeConnection(rs,st,con);			
			}
			
	 }
	 public  List getProposal(String mode)throws ServiceNotAvailableException,EPISException
		{
			List list = new ArrayList();
			PreparedStatement pstmt = null;
			try {
				LetterToBankBean pbean = null;
				con = DBUtility.getConnection();

				String query ="";
					if(mode.equals("psuprimary"))
					query="select * from invest_bankletter where CATEGORYCD='PSU' AND MARKET_TYPE='P' AND  PROPOSAL_REF_NO NOT IN(select PROPOSAL_REF_NO from invest_confirmation_company)";
					else if(mode.equals("rbiauction"))
						query="select * from invest_confirmation_company where PREPERATION_CONFIRMATION='Y'";
					else if(mode.equals("preperationrbiauction"))
						query="select PROPOSAL_REF_NO,PROPOSAL_REF_NO||security_name proposalsecurity,PROPOSAL_REF_NO,PROPOSAL_REF_NO||'--'||security_name proposalsecuritydef,TRUSTTYPE,CATEGORYCD,AMT_INV,(amt_inv-nvl((select sum(NO_OF_BONDS*FACEVALUE) from invest_confirmation_company confirm  where confirm.PROPOSAL_REF_NO || confirm.SECURITY_NAME = form.PROPOSAL_REF_NO || form.SECURITY_NAME), 0))remaining from Invest_Formfilling form where APPROVED_2='A'  and CATEGORYCD<>'PSU' AND MARKET_TYPE<>'P' AND  AMT_INV<>nvl((select sum(NO_OF_BONDS*FACEVALUE) from invest_confirmation_company confirm where confirm.PROPOSAL_REF_NO||confirm.SECURITY_NAME=form.PROPOSAL_REF_NO||form.SECURITY_NAME),0)";
				
				if (con != null) {
					pstmt = con.prepareStatement(query);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						pbean = new LetterToBankBean();
						pbean.setRefNo(rs.getString("PROPOSAL_REF_NO"));
						pbean.setTrustType(rs.getString("TRUSTTYPE"));
						pbean.setSecurityCategory(rs.getString("CATEGORYCD"));
						pbean.setAmountInv(rs.getString("AMT_INV"));
						if(mode.equals("preperationrbiauction"))
						{
						pbean.setRefnoSecurityName(rs.getString("proposalsecurity"));
						pbean.setRemainingAmt(rs.getString("remaining"));
						pbean.setRefnoSecurityDef(rs.getString("proposalsecuritydef"));
						}
						list.add(pbean);
					}
				} else {
					throw new ServiceNotAvailableException();
				}
			} catch (ServiceNotAvailableException snex) {
				throw snex;
			} catch (SQLException sqlex) {
				log.error("LetterToBankDAO:getProposal:SQLException:"
						+ sqlex.getMessage());
				throw new EPISException(sqlex);
			} catch (Exception e) {

				log.error("LetterToBankDAO:getProposal:Exception:"
						+ e.getMessage());
				throw new EPISException(e);
			} finally {
				DBUtility.closeConnection(rs, pstmt, con);
			}

			return list;
		}


}
