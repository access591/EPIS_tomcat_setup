package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.investment.GenerateBankLetterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.DateValidation;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class GenerateBankLetterDAO {
	Connection con = null;
	ResultSet rs = null;
	ResultSet rsdt=null;
	Statement st = null;
	PreparedStatement pstmt=null;
	PreparedStatement pstmtdt=null;
	PreparedStatement pstmtnotes=null;
	Log log  = new Log(GenerateBankLetterDAO.class);
	private final static GenerateBankLetterDAO bankletterdao=new GenerateBankLetterDAO();
	private GenerateBankLetterDAO(){
		
	}
	public static GenerateBankLetterDAO getInstance()
	{
		return bankletterdao;
	}
	
	public List searchGenerateBankLetter(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		List list =new ArrayList();;
		try{
			GenerateBankLetterBean bankletter =null;
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			String query = "select bankcd,QUOTATIONCD,bankname,accountno,letter_no,bank_letter_no,to_char(deal_date,'dd/Mon/yyyy') deal_date,to_char(settlement_date,'dd/Mon/yyyy') settlement_date  from invest_generate_bankletter where BANKCD is not null";
			if(!bean.getBankLetterNo().equals(" ")){
				query +=" and upper(BANK_LETTER_NO) like upper('"+bean.getBankLetterNo()+"%')";
			}
			if(!bean.getAccountNo().equals("")){
				query +=" and ACCOUNTNO='"+bean.getAccountNo()+"'";
			}
			
			query +=" order by CREATED_DT desc";
			log.info("GenerateBankLetterDAO:searchGenerateBankLetter(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				bankletter = new GenerateBankLetterBean();
				bankletter.setQuotationCd(StringUtility.checknull(rs.getString("QUOTATIONCD")));
				bankletter.setBankName(StringUtility.checknull(rs.getString("bankname")));
				bankletter.setBankLetterNo(StringUtility.checknull(rs.getString("bank_letter_no")));
				bankletter.setLetterNo(StringUtility.checknull(rs.getString("letter_no")));
				bankletter.setDealDate(StringUtility.checknull(rs.getString("deal_date")));
				bankletter.setSettlementDate(StringUtility.checknull(rs.getString("settlement_date")));
				bankletter.setAccountNo(StringUtility.checknull(rs.getString("accountno")));
				
				list.add(bankletter);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("the list size is"+list.size());
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("GenerateBankLetterDAO:searchBankLetter:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:searchBankLetter:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	public List getApprovalLetters()throws ServiceNotAvailableException, EPISException
	{
		List list=new ArrayList();
		try{
			GenerateBankLetterBean bankletter =null;
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			String query = "select letter_no||'-'||SECURITY_NAME letterno,QUOTATIONCD from invest_quotaion_data where APPROVALSTATUS='A' and APPROVED_4 = 'A' and((approved_5='A' and APPROVED_6='A')or(approved_6='A' and APPROVED_7='A') or(approved_5='A' and APPROVED_7='A')) AND BANKLETTERSTATUS IS NULL";
			
			log.info("GenerateBankLetterDAO:getApprovalLetters(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				bankletter = new GenerateBankLetterBean();
				bankletter.setQuotationCd(StringUtility.checknull(rs.getString("QUOTATIONCD")));
				bankletter.setLetterNo(StringUtility.checknull(rs.getString("letterno")));
				
				list.add(bankletter);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("GenerateBankLetterDAO:getApprovalLetters:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:getApprovalLetters:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	public void saveGenerateBankLetter(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		String bankLetterNo="";
		int recordCount=0;
		String bankName="";
		String letterNo="";
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				Statement stmt=con.createStatement();
				String bankCd =AutoGeneration.getNextCode("INVEST_GENERATE_BANKLETTER","BANKCD",5,con);
				log.info("the coming bankcd is..."+bankCd);
				bankLetterNo=AutoGeneration.getNextCodeGBy("INVEST_GENERATE_BANKLETTER","BANK_LETTER_NO",30,bean.getBankLetterNo()+"/",con);
				log.info("the bankletterno is"+bankLetterNo);
				recordCount=DBUtility.getRecordCount("Select count(*) from INVEST_GENERATE_BANKLETTER where BANK_LETTER_NO='"+bankLetterNo+"'");
				if(recordCount!=0)
				{
					throw new Exception("BankLetter Number Already Exist");
				}
				else
				{
					DBUtility.setAutoCommit(false,con);
					rs=stmt.executeQuery("select BANKNAME from cb_bank_info where ACCOUNTNO='"+bean.getAccountNo()+"' ");
					if(rs.next())
					{
						bankName=rs.getString("BANKNAME");
						
					}
					rs.close();
					rsdt=stmt.executeQuery("select LETTER_NO from invest_quotaion_data where QUOTATIONCD='"+bean.getQuotationCd()+"'");
					if(rsdt.next())
					{
						letterNo=rsdt.getString("LETTER_NO");
					}
					rsdt.close();
					
					String savebankSql="Insert into INVEST_GENERATE_BANKLETTER (BANKCD,BANKNAME,ACCOUNTNO,LETTER_NO,BANK_LETTER_NO,deal_date,settlement_date,QUOTATIONCD,CREATED_BY,RATE,NOOF_DAYS,ACCURED_AMOUNT,PRINCIPAL_AMOUNT,SETTLEMENT_AMOUNT,BENEFICIARY_NAME,BENEFICIARY_ACCOUNT_NO,BANK_NAME,BRANCH_NAME,ACCOUNT_TYPE,IFSCCODE,LEFT_SIGN,RIGHT_SIGN,REMARKS,CREATED_DT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
					pstmt=con.prepareStatement(savebankSql);
					if(pstmt!=null)
					{
						log.info("the rate is"+bean.getRate());
						log.info("the noof days"+bean.getNoofDays());
						log.info("the accuredinterest"+bean.getAccuredAmount());
						pstmt.setString(1,StringUtility.checknull(bankCd));
						pstmt.setString(2,StringUtility.checknull(bankName));
						pstmt.setString(3,StringUtility.checknull(bean.getAccountNo()));
						pstmt.setString(4,StringUtility.checknull(letterNo));
						pstmt.setString(5,StringUtility.checknull(bankLetterNo));
						pstmt.setString(6,StringUtility.checknull(bean.getDealDate()));
						pstmt.setString(7,StringUtility.checknull(bean.getSettlementDate()));
						pstmt.setString(8,StringUtility.checknull(bean.getQuotationCd()));
						pstmt.setString(9,StringUtility.checknull(bean.getLoginUserId()));
						pstmt.setString(10,StringUtility.checknull(bean.getRate()));
						pstmt.setString(11,StringUtility.checknull(bean.getNoofDays()));
						pstmt.setString(12,StringUtility.checknull(bean.getAccuredAmount()));
						pstmt.setString(13,StringUtility.checknull(bean.getPrincipalAmount()));
						pstmt.setString(14,StringUtility.checknull(bean.getSettlementAmount()));
						pstmt.setString(15,StringUtility.checknull(bean.getBeneficiaryName()));
						pstmt.setString(16,StringUtility.checknull(bean.getCreditAccountNo()));
						pstmt.setString(17,StringUtility.checknull(bean.getBenificiaryBankName()));
						pstmt.setString(18,StringUtility.checknull(bean.getBenificiaryBranch()));
						pstmt.setString(19,StringUtility.checknull(bean.getAccountType()));
						pstmt.setString(20,StringUtility.checknull(bean.getIfscCode()));
						pstmt.setString(21,StringUtility.checknull(bean.getLeftSign()));
						pstmt.setString(22,StringUtility.checknull(bean.getRightSign()));
						pstmt.setString(23,StringUtility.checknull(bean.getRemarks()));
						
						pstmt.executeUpdate();						
					}				
					else{
						throw new ServiceNotAvailableException();
					}
					
					pstmt.close();
					
					String savebankStatusSql=" update invest_quotaion_data set BANKLETTERSTATUS='Y' where QUOTATIONCD='"+bean.getQuotationCd()+"'" ;
					pstmt=con.prepareStatement(savebankStatusSql);
					if(pstmt!=null)
					{														
						pstmt.executeUpdate();						
					}				
					else{
						throw new ServiceNotAvailableException();
					}
					
				}
				DBUtility.commitTrans(con);
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("GenerateBankLetterDAO:saveGenerateBankLetter:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:saveGenerateBankLetter:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	public void updatequotationData(QuotationBean qbean)throws ServiceNotAvailableException, EPISException
	{
		try{
			con=DBUtility.getConnection();
			
			log.info("the quotation cd is"+qbean.getQuotationCd());
			String updateSql="UPDATE  INVEST_QUOTAION_DATA SET FACEVALUE=?,PURCHASE_OPTION=?,PREMIUM_PAID=?,INVESTMENT_FACEVALUE=?,NUMBER_OF_UNITS=?,PURCHAEPRICE=?,MATURITYDATE=?,INVESTMENT_PRICEOFFERED=?,INVESTMENTMODE=?,INTEREST_RATE=?,INTEREST_DAY_MONTH=?,INTEREST_DAY_MONTH1=?,DEALDATE=?,SETTLEMENTDATE=?,YTMPERCENTAGE=? WHERE  QUOTATIONCD=? ";
			String interestDate="";
			String interestDate1="";
			if(con!=null)
			{
				pstmt=con.prepareStatement(updateSql);
				if(pstmt!=null){
					if(!StringUtility.checknull(qbean.getInterestDate()).equals(""))
						interestDate=qbean.getInterestDate()+"/"+qbean.getInterestMonth();
						if(!StringUtility.checknull(qbean.getInterestDate1()).equals(""))
							interestDate1=qbean.getInterestDate1()+"/"+qbean.getInterestMonth1();
						
					pstmt.setString(1,qbean.getFaceValue());
					pstmt.setString(2,qbean.getPurchaseOption());
					pstmt.setString(3,qbean.getPremiumPaid());
					pstmt.setString(4,qbean.getInvestmentFaceValue());
					pstmt.setString(5,qbean.getNumberOfUnits());
					pstmt.setString(6,qbean.getPurchasePrice());
					pstmt.setString(7,qbean.getMaturityDate());
					pstmt.setString(8,qbean.getPriceoffered());
					pstmt.setString(9,qbean.getInvestmentMode());
					pstmt.setString(10,qbean.getInterestRate());
					pstmt.setString(11,interestDate);
					pstmt.setString(12,interestDate1);
					pstmt.setString(13,qbean.getDealDate());
					pstmt.setString(14,qbean.getSettlementDate());
					pstmt.setString(15,qbean.getYtm());
					pstmt.setString(16,qbean.getQuotationCd());
					pstmt.executeUpdate();
				}
				else{
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
			log.error("GenerateBankLetterDAO:updateQuotationData:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:updateQuotationData:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
	}
	
	public void updateQuotationLetterData(QuotationBean qbean)throws ServiceNotAvailableException, EPISException
	{
		try{
			con=DBUtility.getConnection();
			
			
			String updateSql="UPDATE  INVEST_QUOTAION_DATA SET FACEVALUE=?,MATURITYDATE=?,INTEREST_RATE=?,INTEREST_DAY_MONTH=?,INTEREST_DAY_MONTH1=?,INVESTMENT_PRICEOFFERED=?,DEALDATE=?,SETTLEMENTDATE=? WHERE  QUOTATIONCD=? ";
			String interestDate="";
			String interestDate1="";
			if(con!=null)
			{
				pstmt=con.prepareStatement(updateSql);
				if(pstmt!=null){
					if(!StringUtility.checknull(qbean.getInterestDate()).equals(""))
						interestDate=qbean.getInterestDate()+"/"+qbean.getInterestMonth();
						if(!StringUtility.checknull(qbean.getInterestDate1()).equals(""))
							interestDate1=qbean.getInterestDate1()+"/"+qbean.getInterestMonth1();
						
					pstmt.setString(1,qbean.getFaceValue());
					pstmt.setString(2,qbean.getMaturityDate());
					pstmt.setString(3,qbean.getInterestRate());
					pstmt.setString(4,interestDate);
					pstmt.setString(5,interestDate1);
					pstmt.setString(6,qbean.getPriceoffered());
					pstmt.setString(7,qbean.getDealDate());
					pstmt.setString(8,qbean.getSettlementDate());
					pstmt.setString(9,qbean.getLetterNo());
					pstmt.executeUpdate();
				}
				else{
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
			log.error("GenerateBankLetterDAO:updateQuotationLetterData:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:updateQuotationLetterData:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	public GenerateBankLetterBean generateBankLetterReport(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		GenerateBankLetterBean bankLetter=null;
		String security="";
		String marketType="";
		String arrangerName="";
		SQLHelper sq=new SQLHelper();
		double totalAmount=0.0;
		String settlementwords="";
		StringBuffer settlementAmountWords=new StringBuffer();
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				String securityQuery="select category.CATEGORYCD,quotation.MARKET_TYPE from invest_quotaion_data data,invest_sec_category category,invest_quotationrequest quotation where data.QUOTATIONCD='"+bean.getQuotationCd()+"' and data.categoryid=category.categoryid and data.letter_no=quotation.letter_no ";
				rs=st.executeQuery(securityQuery);
				if(rs.next())
				{
					security=StringUtility.checknull(rs.getString("CATEGORYCD"));
					marketType=StringUtility.checknull(rs.getString("MARKET_TYPE"));
				}
				rs.close();
				
				String arrangerquery="select ARRANGERNAME from invest_quotaion_data data,invest_arrangers arranger where QUOTATIONCD='"+bean.getQuotationCd()+"' and data.ARRANGERCD=arranger.ARRANGERCD";
				rs=st.executeQuery(arrangerquery);
				if(rs.next())
				{
					arrangerName=rs.getString("ARRANGERNAME");
					
				}
				rs.close();
				
				String query="select ge.LETTER_NO,quotation.PROPOSAL_REF_NO, ge.ACCOUNTNO,ADDRESS,quotation.SECURITY_NAME,quotation.CATEGORYID,quotation.TRUSTTYPE,quotation.PURCHAEPRICE,(nvl(quotation.INVESTMENT_FACEVALUE,0)*nvl(quotation.NUMBER_OF_UNITS,0))FACEVALUE,nvl(quotation.INVESTMENT_PRICEOFFERED,0)RATE,nvl(NOOF_DAYS,0)NOOF_DAYS,nvl(ACCURED_AMOUNT,0)ACCURED_AMOUNT,nvl(PRINCIPAL_AMOUNT,0)PrincipalAmount,nvl(SETTLEMENT_AMOUNT,0) settlementamount,to_char(DEAL_DATE,'dd/Mon/yyyy')deal_date,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy') SETTLEMENT_DATE,nvl(ge.BENEFICIARY_NAME,'')BENEFICIARY_NAME,nvl(ge.BENEFICIARY_ACCOUNT_NO,'')BENEFICIARY_ACCOUNT_NO,nvl(ge.BANK_NAME,'')BANK_NAME,nvl(ge.BRANCH_NAME,'')BRANCH_NAME,nvl(ge.ACCOUNT_TYPE,'')ACCOUNT_TYPE,nvl(ge.IFSCCODE,'')IFSCCODE,nvl(LEFT_SIGN,'')LEFT_SIGN,nvl(RIGHT_SIGN,'')RIGHT_SIGN,nvl(ge.REMARKS,'')REMARKS from invest_quotaion_data quotation,invest_generate_bankletter ge,cb_bank_info info,invest_quotationrequest req where ge.quotationcd='"+bean.getQuotationCd()+"' and quotation.quotationcd=ge.quotationcd and info.accountno = ge.accountno and req.LETTER_NO=ge.LETTER_NO ";
				
				rsdt=st.executeQuery(query);
				
				if(rsdt.next())
				{
					bankLetter=new GenerateBankLetterBean();
					bankLetter.setBankLetterNo(StringUtility.checknull(rsdt.getString("LETTER_NO")));
					bankLetter.setReferenceNo(StringUtility.checknull(rsdt.getString("PROPOSAL_REF_NO")));
					
					bankLetter.setAddress(StringUtility.checknull(rsdt.getString("ADDRESS").replaceAll(",",",\n").trim()));
					bankLetter.setSecurityName(StringUtility.checknull(rsdt.getString("SECURITY_NAME")));
					bankLetter.setTrustName(StringUtility.checknull(rsdt.getString("TRUSTTYPE")));
					bankLetter.setPurchasePrice(StringUtility.checknull(rsdt.getString("PURCHAEPRICE")));
					bankLetter.setAccountNo(StringUtility.checknull(rsdt.getString("ACCOUNTNO")));
					bankLetter.setArrangerName(StringUtility.checknull(arrangerName));
					bankLetter.setSecurity(StringUtility.checknull(security));
					bankLetter.setDealDate(StringUtility.checknull(rsdt.getString("deal_date")));
					bankLetter.setSettlementDate(StringUtility.checknull(rsdt.getString("SETTLEMENT_DATE")));
					bankLetter.setRate(StringUtility.checknull(rsdt.getString("RATE")));
					bankLetter.setNoofDays(StringUtility.checknull(rsdt.getString("NOOF_DAYS")));
					bankLetter.setAccuredAmount(StringUtility.checknull(rsdt.getString("ACCURED_AMOUNT")));
					bankLetter.setInvestmentFaceValue(StringUtility.checknull(rsdt.getString("FACEVALUE")));
					bankLetter.setPrincipalAmount(StringUtility.checknull(rsdt.getString("PRINCIPALAMOUNT")));
					bankLetter.setSettlementAmount(StringUtility.checknull(rsdt.getString("SETTLEMENTAMOUNT")));
					bankLetter.setBeneficiaryName(StringUtility.checknull(rsdt.getString("BENEFICIARY_NAME")));
					bankLetter.setCreditAccountNo(StringUtility.checknull(rsdt.getString("BENEFICIARY_ACCOUNT_NO")));
					bankLetter.setBankName(StringUtility.checknull(rsdt.getString("BANK_NAME")));
					bankLetter.setBenificiaryBranch(StringUtility.checknull(rsdt.getString("BRANCH_NAME")));
					bankLetter.setAccountType(StringUtility.checknull(rsdt.getString("ACCOUNT_TYPE")));
					bankLetter.setIfscCode(StringUtility.checknull(rsdt.getString("IFSCCODE")));
					bankLetter.setLeftSign(StringUtility.checknull(rsdt.getString("LEFT_SIGN")));
					bankLetter.setRightSign(StringUtility.checknull(rsdt.getString("RIGHT_SIGN")));
					bankLetter.setRemarks(StringUtility.checknull(rsdt.getString("REMARKS")));
					bankLetter.setMarketType(StringUtility.checknull(marketType));
					
					totalAmount=Double.parseDouble(bankLetter.getSettlementAmount());
					
					settlementwords=sq.ConvertInWords(totalAmount);
					
					settlementAmountWords.append(settlementwords+" "+"Only");
					bankLetter.setSettlementAmountWords(settlementAmountWords.toString());
					
					
					
				}
				
				rsdt.close();
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("GenerateBankLetterDAO:searchBankLetter:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:searchBankLetter:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return bankLetter;
	}
	public QuotationBean bankLetterDetails(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		QuotationBean bankletter=null;
		String security="";
		String arrangerName="";
		DateValidation dv=new DateValidation();
		try{
			if(con!=null)
			{
				con=DBUtility.getConnection();
				st=con.createStatement();
				String securityQuery="select CATEGORYCD from invest_quotaion_data data,invest_sec_category category where QUOTATIONCD='"+bean.getQuotationCd()+"' and data.categoryid=category.categoryid ";
				rs=st.executeQuery(securityQuery);
				if(rs.next())
				{
					security=StringUtility.checknull(rs.getString("CATEGORYCD"));
				}
				rs.close();
				String arrangerquery="select ARRANGERNAME from invest_quotaion_data data,invest_arrangers arranger where QUOTATIONCD='"+bean.getQuotationCd()+"' and data.ARRANGERCD=arranger.ARRANGERCD";
				rs=st.executeQuery(arrangerquery);
				if(rs.next())
				{
					arrangerName=rs.getString("ARRANGERNAME");
					
				}
				rs.close();
				String query="select LETTER_NO,TRUSTTYPE,SECURITY_NAME,FACEVALUE,nvl(to_char(MATURITYDATE,'dd/Mon/yyyy'),'')MATURITYDATE,INTEREST_DAY_MONTH INTEREST_DATE,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENTDATE,'dd/Mon/yyyy')SETTLEMENTDATE,INTEREST_DAY_MONTH1 INTEREST_DATE1,nvl(BROKERNAME,'')BROKERNAME,nvl(BROKERADDRESS,'')BROKERADDRESS,quotationcd,nvl(PURCHAEPRICE,0)price,nvl(NUMBER_OF_UNITS,0)NUMBER_OF_UNITS,nvl(INVESTMENT_FACEVALUE,0)INVESTMENT_FACEVALUE,nvl(INTEREST_RATE,0)INTEREST_RATE,nvl(INVESTMENT_PRICEOFFERED,0)INVESTMENT_PRICEOFFERED,(nvl(NUMBER_OF_UNITS,0)*nvl(INVESTMENT_PRICEOFFERED,0)) principalamount,nvl(PREMIUM_PAID,0)PREMIUM_PAID,PURCHASE_OPTION,decode(INVESTMENTTYPE,'P','Primary','S','Secondary')INVESTMENTTYPE,nvl(INVESTMENTMODE,'')INVESTMENTMODE,nvl(YTMPERCENTAGE,0)YTMPERCENTAGE from invest_quotaion_data where quotationcd='"+bean.getQuotationCd()+"'";
				log.info("the query is...."+query);
				rsdt=st.executeQuery(query);
				if(rsdt.next())
				{
					bankletter=new QuotationBean();
					String letterno=StringUtility.checknull(rsdt.getString("LETTER_NO"));
					log.info("the letter from actual"+letterno);
					String letter=(letterno.substring((letterno).indexOf("/",1)+1,letterno.length()));
					log.info("the letter is:"+letter);
					String result=letter.substring(0,(letter).indexOf("/",-1));
					log.info("the result is:"+result);
					String totalResult="FA/"+result+"/"+dv.getLetterYear(StringUtility.checknull(rsdt.getString("DEALDATE")));
					log.info("the bankletter nuber is"+totalResult);
					
					bankletter.setLetterNo(StringUtility.checknull(rsdt.getString("LETTER_NO")));
					bankletter.setTrustName(StringUtility.checknull(rsdt.getString("TRUSTTYPE")));
					bankletter.setSecurityCategory(StringUtility.checknull(security));
					bankletter.setArranger(StringUtility.checknull(arrangerName));
					bankletter.setSecurityName(StringUtility.checknull(rsdt.getString("SECURITY_NAME")));
					bankletter.setBrokerName(StringUtility.checknull(rsdt.getString("BROKERNAME")));
					bankletter.setBrokerAddress(StringUtility.checknull(rsdt.getString("BROKERADDRESS")));
					bankletter.setQuotationCd(StringUtility.checknull(rsdt.getString("quotationcd")));
					bankletter.setPurchasePrice(StringUtility.checknull(rsdt.getString("price")));
					log.info("the price...."+bankletter.getPurchasePrice());
					bankletter.setPrincipleAmt(StringUtility.checknull(rsdt.getString("principalamount")));
					bankletter.setNumberOfUnits(StringUtility.checknull(rsdt.getString("NUMBER_OF_UNITS")));
					bankletter.setInvestmentFaceValue(StringUtility.checknull(rsdt.getString("INVESTMENT_FACEVALUE")));
					bankletter.setFaceValue(StringUtility.checknull(rsdt.getString("FACEVALUE")));
					bankletter.setMaturityDate(StringUtility.checknull(rsdt.getString("MATURITYDATE")));
					String interestDate=StringUtility.checknull(rsdt.getString("INTEREST_DATE"));
					log.info("the interest date is..."+interestDate);
					String interestDate1=StringUtility.checknull(rsdt.getString("INTEREST_DATE1"));
					
					if(!interestDate.equals(""))
					{
						bankletter.setInterestDate(interestDate.substring(0,interestDate.indexOf("/")));
						bankletter.setInterestMonth(interestDate.substring(interestDate.indexOf("/")+1,interestDate.length()));
					}
					if(!interestDate1.equals(""))
					{
						bankletter.setInterestDate1(interestDate.substring(0,interestDate1.indexOf("/")));
						bankletter.setInterestMonth1(interestDate.substring(interestDate1.indexOf("/")+1,interestDate1.length()));
					}
					bankletter.setInterestRate(StringUtility.checknull(rsdt.getString("INTEREST_RATE")));
					bankletter.setPremiumPaid(StringUtility.checknull(rsdt.getString("PREMIUM_PAID")));
					bankletter.setInvestmentType(StringUtility.checknull(rsdt.getString("INVESTMENTTYPE")));
					bankletter.setInvestmentMode(StringUtility.checknull(rsdt.getString("INVESTMENTMODE")));
					bankletter.setPurchaseOption(StringUtility.checknull(rsdt.getString("PURCHASE_OPTION")));
					bankletter.setPriceoffered(StringUtility.checknull(rsdt.getString("INVESTMENT_PRICEOFFERED")));
					bankletter.setDealDate(StringUtility.checknull(rsdt.getString("DEALDATE")));
					bankletter.setSettlementDate(StringUtility.checknull(rsdt.getString("SETTLEMENTDATE")));
					bankletter.setYtm(StringUtility.checknull(rsdt.getString("YTMPERCENTAGE")));
					bankletter.setBankLetterNo(StringUtility.checknull(totalResult));
					
					log.info("the price....endi.....g"+bankletter.getPurchasePrice());
					
					
					
					
				}
				rsdt.close();
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("GenerateBankLetterDAO:searchBankLetter:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("enerateBankLetterDAO:searchBankLetter:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return bankletter;
	}

}
