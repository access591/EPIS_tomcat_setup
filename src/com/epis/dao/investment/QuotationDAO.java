package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.admin.Bean;
import com.epis.bean.cashbook.PartyInfo;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.services.cashbook.PartyService;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class QuotationDAO {
	
	Log log  = new Log(QuotationDAO.class);
	private QuotationDAO(){
		
	}
	private static final QuotationDAO dao = new QuotationDAO();
	public static QuotationDAO getInstance(){
		return dao;
	}
	public List searchQuotation(String letterNo,String SecurityName,String trust,String SecurityCategory) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="SELECT QUOTATIONCD,ARRANGERCD,BROKERADDRESS,BROKER_INCENTIVE,BROKERNAME,CALLOPTION," +
					" DIRECT_INCENTIVE,DISNICNO,FACEVALUE,GUARENTEETYPE,TO_CHAR(to_date(INTEREST_DAY_MONTH,'dd/MM'),'dd/Mon') " +
					" INTEREST_DATE,nvl(INTEREST_RATE,0)INTEREST_RATE,INVESTMENT_FACEVALUE,INVESTMENTMODE, " +
					" INVESTMENTTYPE,LETTER_NO,to_char(MATURITYDATE,'dd/Mon/YYYY')MATURITYDATE,NUMBER_OF_UNITS, " +
					" PREMIUM_PAID,PURCHAEPRICE,RATING,to_char(REDEMPTIONDATE,'dd/Mon/YYYY')REDEMPTIONDATE, " +
					" REMARKS,mt.CATEGORYID,CATEGORYCD,SECURITY_NAME,TRUSTTYPE,nvl(YTMPERCENTAGE,0)YTMPERCENTAGE, " +
					" YTMVERIFIED from invest_Quotaion_data mt,invest_sec_category sc where mt.CATEGORYID=sc.CATEGORYID and " +
					" letter_no is not null and upper(letter_no) like upper(nvl(?,''))||'%' and upper(nvl( " +
					" SECURITY_NAME,' ')) like upper(nvl(?,''))||'%' and upper(TRUSTTYPE) like upper(nvl(?,''))||'%' and upper(mt.CATEGORYID) like upper(nvl(?,''))||'%' order by mt.CREATED_DT desc ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(letterNo));
					pstmt.setString(2,StringUtility.checknull(SecurityName));
					pstmt.setString(3,StringUtility.checknull(trust));
					pstmt.setString(4,StringUtility.checknull(SecurityCategory));
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						QuotationBean qbean = new QuotationBean();
						qbean.setArranger(StringUtility.checknull(reusltSet.getString("ARRANGERCD")));
						qbean.setBrokerAddress(StringUtility.checknull(reusltSet.getString("brokeraddress")));
						qbean.setBrokerIncentive(StringUtility.checknull(reusltSet.getString("Broker_incentive")));
						qbean.setBrokerName(StringUtility.checknull(reusltSet.getString("brokername")));
						qbean.setCallOption(StringUtility.checknull(reusltSet.getString("calloption")));
						qbean.setDirectIncentive(StringUtility.checknull(reusltSet.getString("direct_incentive")));
						qbean.setDisIncNo(StringUtility.checknull(reusltSet.getString("DISNICNO")));
						qbean.setFaceValue(StringUtility.checknull(reusltSet.getString("FACEVALUE")));
						qbean.setGuarenteeType(StringUtility.checknull(reusltSet.getString("guarenteetype")));
						qbean.setInterestDate(StringUtility.checknull(reusltSet.getString("Interest_Date")));
						qbean.setInterestRate(StringUtility.checknull(reusltSet.getString("Interest_Rate")));
						qbean.setInvestmentFaceValue(StringUtility.checknull(reusltSet.getString("investment_facevalue")));
						qbean.setInvestmentMode(StringUtility.checknull(reusltSet.getString("investmentmode")));
						qbean.setInvestmentType(StringUtility.checknull(reusltSet.getString("investmenttype")));
						qbean.setLetterNo(StringUtility.checknull(reusltSet.getString("LETTER_NO")));
						qbean.setMaturityDate(StringUtility.checknull(reusltSet.getString("MaturityDate")));
						qbean.setNumberOfUnits(StringUtility.checknull(reusltSet.getString("number_of_units")));
						qbean.setPremiumPaid(StringUtility.checknull(reusltSet.getString("premium_paid")));
						qbean.setPurchasePrice(StringUtility.checknull(reusltSet.getString("purchaeprice")));
						qbean.setQuotationCd(reusltSet.getString("quotationcd"));
						qbean.setRating(StringUtility.checknull(reusltSet.getString("RATING")));
						qbean.setRedemptionDate(StringUtility.checknull(reusltSet.getString("redemptiondate")));
						qbean.setRemarks(StringUtility.checknull(reusltSet.getString("remarks")));
						qbean.setSecurityCategory(StringUtility.checknull(reusltSet.getString("CATEGORYID")));
						qbean.setSecurityCd(StringUtility.checknull(reusltSet.getString("CATEGORYCD")));
						qbean.setSecurityName(StringUtility.checknull(reusltSet.getString("SECURITY_NAME")));
						qbean.setTrustName(StringUtility.checknull(reusltSet.getString("TRUSTTYPE")));
						qbean.setYtm(StringUtility.checknull(reusltSet.getString("YTMPercentage")));
						qbean.setYtmVerified(StringUtility.checknull(reusltSet.getString("YTMVERIFIED")));
						list.add(qbean);
						log.info("..."+qbean);
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
			log.error("QuotationDAO:searchQuotation:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("QuotationDAO:searchQuotation:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		
		return list;
		
	}

	public void saveQuotation(QuotationBean qbean) throws ServiceNotAvailableException, EPISException, SQLException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		
		try{
			
			connection = DBUtility.getConnection();
			
			if(connection!=null){
				String saveunitSql ="insert into invest_Quotaion_data (QUOTATIONCD,ARRANGERCD,BROKERADDRESS,BROKER_INCENTIVE," +
						"BROKERNAME,CALLOPTION,DIRECT_INCENTIVE,DISNICNO,FACEVALUE,GUARENTEETYPE,INTEREST_DAY_MONTH,INTEREST_DAY_MONTH1,INTEREST_RATE," +
						"INVESTMENT_FACEVALUE,INVESTMENTMODE,INVESTMENTTYPE,LETTER_NO,MATURITYDATE,NUMBER_OF_UNITS,PREMIUM_PAID," +
						"PURCHAEPRICE,RATING,REDEMPTIONDATE,REMARKS,CATEGORYID,SECURITY_NAME,TRUSTTYPE,YTMPERCENTAGE,CREATED_BY,CREATED_DT,DEMAT_NO,purchase_option,YTM_Call,ytmmaturity,closingdate,tenure,minApp,eligibility_PF,INVESTMENT_PRICEOFFERED,DEALDATE,SETTLEMENTDATE,PROPOSAL_REF_NO) " +
						"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?)";
				String quotationcd =AutoGeneration.getNextCode("invest_Quotaion_data","QUOTATIONCD",10,connection);
				pstmt=connection.prepareStatement(saveunitSql);		
				String interestDate="";
				String interestDate1="";
				if(pstmt!=null){
					if(!StringUtility.checknull(qbean.getInterestDate()).equals(""))
					interestDate=qbean.getInterestDate()+"/"+qbean.getInterestMonth();
					if(!StringUtility.checknull(qbean.getInterestDate1()).equals(""))
						interestDate1=qbean.getInterestDate1()+"/"+qbean.getInterestMonth1();
						
					pstmt.setString(1,StringUtility.checknull(quotationcd));
					pstmt.setString(2,StringUtility.checknull(qbean.getArranger()));
					pstmt.setString(3,StringUtility.checknull(qbean.getBrokerAddress()));
					pstmt.setString(4,StringUtility.checknull(qbean.getBrokerIncentive()));
					pstmt.setString(5,StringUtility.checknull(qbean.getBrokerName()));
					pstmt.setString(6,StringUtility.checknull(qbean.getCallOption()));
					pstmt.setString(7,StringUtility.checknull(qbean.getDirectIncentive()));
					pstmt.setString(8,StringUtility.checknull(qbean.getDisIncNo()));
					pstmt.setString(9,StringUtility.checknull(qbean.getFaceValue()));
					pstmt.setString(10,StringUtility.checknull(qbean.getGuarenteeType()));
					pstmt.setString(11,StringUtility.checknull(interestDate));
					pstmt.setString(12,StringUtility.checknull(interestDate1));
					pstmt.setString(13,StringUtility.checknull(qbean.getInterestRate()));
					pstmt.setString(14,StringUtility.checknull(qbean.getInvestmentFaceValue()));
					pstmt.setString(15,StringUtility.checknull(qbean.getInvestmentMode()));
					pstmt.setString(16,StringUtility.checknull(qbean.getInvestmentType()));
					pstmt.setString(17,StringUtility.checknull(qbean.getLetterNo()));
					pstmt.setString(18,StringUtility.checknull(qbean.getMaturityDate()));
					pstmt.setString(19,StringUtility.checknull(qbean.getNumberOfUnits()));
					pstmt.setString(20,StringUtility.checknull(qbean.getPremiumPaid()));
					pstmt.setString(21,StringUtility.checknull(qbean.getPurchasePrice()));
					pstmt.setString(22,StringUtility.checknull(qbean.getRating()));
					pstmt.setString(23,StringUtility.checknull(qbean.getRedemptionDate()));
					pstmt.setString(24,StringUtility.checknull(qbean.getRemarks()));
					pstmt.setString(25,StringUtility.checknull(qbean.getSecurityCategory()));
					pstmt.setString(26,StringUtility.checknull(qbean.getSecurityName()));
					pstmt.setString(27,StringUtility.checknull(qbean.getTrustName()));
					pstmt.setString(28,StringUtility.checknull(qbean.getYtm()));
					pstmt.setString(29,StringUtility.checknull(qbean.getLoginUserId()));
					pstmt.setString(30,StringUtility.checknull(qbean.getDematno()));
					pstmt.setString(31,StringUtility.checknull(qbean.getPurchaseOption()));
					pstmt.setString(32,StringUtility.checknull(qbean.getYtmcall()));
					pstmt.setString(33,StringUtility.checknull(qbean.getYtmmaturity()));
					pstmt.setString(34,StringUtility.checknull(qbean.getClosingDate()));
					pstmt.setString(35,StringUtility.checknull(qbean.getTenure()));
					pstmt.setString(36,StringUtility.checknull(qbean.getMinApp()));
					pstmt.setString(37,StringUtility.checknull(qbean.getEligpftrust()));
					pstmt.setString(38,StringUtility.checknull(qbean.getPriceoffered()));
					pstmt.setString(39,StringUtility.checknull(qbean.getDealDate()));
					pstmt.setString(40,StringUtility.checknull(qbean.getSettlementDate()));
					pstmt.setString(41,StringUtility.checknull(qbean.getProposalRefNo()));
									
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}

		}catch(ServiceNotAvailableException snex){
	
			throw snex;
		}catch(SQLException sqlex){
	
			log.error("QuotationDAO:saveQuotation:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
	
			
			log.error("QuotationDAO:saveQuotation:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editQuotation(QuotationBean qbean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  invest_Quotaion_data set ARRANGERCD=?,BROKERADDRESS=?,BROKER_INCENTIVE=?," +
						"BROKERNAME=?,CALLOPTION=?,DIRECT_INCENTIVE=?,DISNICNO=?,FACEVALUE=?,GUARENTEETYPE=?,INTEREST_DAY_MONTH=?,INTEREST_DAY_MONTH1=?,INTEREST_RATE=?," +
						"INVESTMENT_FACEVALUE=?,INVESTMENTMODE=?,INVESTMENTTYPE=?,LETTER_NO=?,MATURITYDATE=?,NUMBER_OF_UNITS=?,PREMIUM_PAID=?," +
						"PURCHAEPRICE=?,RATING=?,REDEMPTIONDATE=?,REMARKS=?,CATEGORYID=?,SECURITY_NAME=?,TRUSTTYPE=?,YTMPERCENTAGE=?,UPDATED_BY=?,UPDATED_DT=SYSDATE,DEMAT_NO=?,purchase_option=?,YTM_Call=?,ytmmaturity=?,closingdate=?,tenure=?,minApp=?,eligibility_PF=?,INVESTMENT_PRICEOFFERED=? where QUOTATIONCD=?";
			String interestDate="";
			String interestDate1="";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					if(!StringUtility.checknull(qbean.getInterestDate()).equals(""))
						interestDate=qbean.getInterestDate()+"/"+qbean.getInterestMonth();
						if(!StringUtility.checknull(qbean.getInterestDate1()).equals(""))
							interestDate1=qbean.getInterestDate1()+"/"+qbean.getInterestMonth1();
					pstmt.setString(1,StringUtility.checknull(qbean.getArranger()));
					pstmt.setString(2,StringUtility.checknull(qbean.getBrokerAddress()));
					pstmt.setString(3,StringUtility.checknull(qbean.getBrokerIncentive()));
					pstmt.setString(4,StringUtility.checknull(qbean.getBrokerName()));
					pstmt.setString(5,StringUtility.checknull(qbean.getCallOption()));
					pstmt.setString(6,StringUtility.checknull(qbean.getDirectIncentive()));
					pstmt.setString(7,StringUtility.checknull(qbean.getDisIncNo()));
					pstmt.setString(8,StringUtility.checknull(qbean.getFaceValue()));
					pstmt.setString(9,StringUtility.checknull(qbean.getGuarenteeType()));
					pstmt.setString(10,StringUtility.checknull(interestDate));
					pstmt.setString(11,StringUtility.checknull(interestDate1));
					pstmt.setString(12,StringUtility.checknull(qbean.getInterestRate()));
					pstmt.setString(13,StringUtility.checknull(qbean.getInvestmentFaceValue()));
					pstmt.setString(14,StringUtility.checknull(qbean.getInvestmentMode()));
					pstmt.setString(15,StringUtility.checknull(qbean.getInvestmentType()));
					pstmt.setString(16,StringUtility.checknull(qbean.getLetterNo()));
					pstmt.setString(17,StringUtility.checknull(qbean.getMaturityDate()));
					pstmt.setString(18,StringUtility.checknull(qbean.getNumberOfUnits()));
					pstmt.setString(19,StringUtility.checknull(qbean.getPremiumPaid()));
					pstmt.setString(20,StringUtility.checknull(qbean.getPurchasePrice()));
					pstmt.setString(21,StringUtility.checknull(qbean.getRating()));
					pstmt.setString(22,StringUtility.checknull(qbean.getRedemptionDate()));
					pstmt.setString(23,StringUtility.checknull(qbean.getRemarks()));
					pstmt.setString(24,StringUtility.checknull(qbean.getSecurityCategory()));
					pstmt.setString(25,StringUtility.checknull(qbean.getSecurityName()));
					pstmt.setString(26,StringUtility.checknull(qbean.getTrustName()));
					pstmt.setString(27,StringUtility.checknull(qbean.getYtm()));
					pstmt.setString(28,StringUtility.checknull(qbean.getLoginUserId()));	
					pstmt.setString(29,StringUtility.checknull(qbean.getDematno()));
					pstmt.setString(30,StringUtility.checknull(qbean.getPurchaseOption()));
					pstmt.setString(31,StringUtility.checknull(qbean.getYtmcall()));
					pstmt.setString(32,StringUtility.checknull(qbean.getYtmmaturity()));
					pstmt.setString(33,StringUtility.checknull(qbean.getClosingDate()));
					pstmt.setString(34,StringUtility.checknull(qbean.getTenure()));
					pstmt.setString(35,StringUtility.checknull(qbean.getMinApp()));
					pstmt.setString(36,StringUtility.checknull(qbean.getEligpftrust()));
					
					pstmt.setString(37,StringUtility.checknull(qbean.getPriceoffered()));
					pstmt.setString(38,StringUtility.checknull(qbean.getQuotationCd()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("QuotationDAO :editQuotation  with info :"+qbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("QuotationDAO:editQuotation:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationDAO:editQuotation:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public QuotationBean findQuotation(String quotationcode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		QuotationBean quotation=null;
		
		try{	
			String searchQry="SELECT PROPOSAL_REF_NO,QUOTATIONCD,mt.ARRANGERCD,ARRANGERNAME,BROKERADDRESS,BROKER_INCENTIVE,BROKERNAME,CALLOPTION,DIRECT_INCENTIVE,DISNICNO,FACEVALUE,GUARENTEETYPE,INTEREST_DAY_MONTH,INTEREST_DAY_MONTH1,nvl(to_char(to_date(INTEREST_DAY_MONTH,'dd/MM'),'dd/Mon'),'--')interestmonth,nvl(to_char(to_date(INTEREST_DAY_MONTH1,'dd/MM'),'dd/Mon'),'--')interestmonth1,INTEREST_RATE,INVESTMENT_FACEVALUE,INVESTMENTMODE,INVESTMENTTYPE,LETTER_NO,to_char(MATURITYDATE,'dd/Mon/YYYY')MATURITYDATE,to_char(DEALDATE,'dd/Mon/YYYY')DEALDATE,to_char(SETTLEMENTDATE,'dd/Mon/YYYY')SETTLEMENTDATE,NUMBER_OF_UNITS,PREMIUM_PAID,PURCHAEPRICE,RATING,to_char(REDEMPTIONDATE,'dd/Mon/YYYY')REDEMPTIONDATE,mt.REMARKS,mt.CATEGORYID,CATEGORYCD,SECURITY_NAME,TRUSTTYPE,YTMPERCENTAGE,DEMAT_NO,nvl(purchase_option,'')purchase_option,nvl(YTM_Call,'')YTM_Call,nvl(ytmmaturity,'')ytmmaturity,nvl(to_char(closingdate,'dd/Mon/yyyy'),'')closingdate,nvl(tenure,'')tenure,nvl(minApp,'')minApp,nvl(eligibility_PF,'')eligibility_PF from invest_quotaion_data mt,invest_sec_category sc,invest_arrangers arranger where sc.CATEGORYID=mt.CATEGORYID and mt.arrangercd=arranger.arrangercd and QUOTATIONCD=? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(quotationcode));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						quotation = new QuotationBean();
						quotation.setProposalRefNo(StringUtility.checknull(reusltSet.getString("PROPOSAL_REF_NO")));
						quotation.setArranger(StringUtility.checknull(reusltSet.getString("ARRANGERCD")));
						quotation.setArrangerCd(StringUtility.checknull(reusltSet.getString("ARRANGERNAME")));
						quotation.setBrokerAddress(StringUtility.checknull(reusltSet.getString("brokeraddress")));
						quotation.setBrokerIncentive(StringUtility.checknull(reusltSet.getString("Broker_incentive")));
						quotation.setBrokerName(StringUtility.checknull(reusltSet.getString("brokername")));
						quotation.setCallOption(StringUtility.checknull(reusltSet.getString("calloption")));
						quotation.setDirectIncentive(StringUtility.checknull(reusltSet.getString("direct_incentive")));
						quotation.setDisIncNo(StringUtility.checknull(reusltSet.getString("DISNICNO")));
						quotation.setFaceValue(StringUtility.checknull(reusltSet.getString("FACEVALUE")));
						quotation.setGuarenteeType(StringUtility.checknull(reusltSet.getString("guarenteetype")));
						quotation.setInterestdispmonth(StringUtility.checknull(reusltSet.getString("interestmonth")));
						quotation.setInterestdispmonth1(StringUtility.checknull(reusltSet.getString("interestmonth1")));
						String interestDate=StringUtility.checknull(reusltSet.getString("INTEREST_DAY_MONTH"));
						String interestDate1=StringUtility.checknull(reusltSet.getString("INTEREST_DAY_MONTH1"));
						
						if(!interestDate.equals(""))
						{
							quotation.setInterestDate(interestDate.substring(0,interestDate.indexOf("/")));
							quotation.setInterestMonth(interestDate.substring(interestDate.indexOf("/")+1,interestDate.length()));
						}
						if(!interestDate1.equals(""))
						{
							quotation.setInterestDate1(interestDate.substring(0,interestDate1.indexOf("/")));
							quotation.setInterestMonth1(interestDate.substring(interestDate1.indexOf("/")+1,interestDate1.length()));
						}
						quotation.setInterestRate(StringUtility.checknull(reusltSet.getString("Interest_Rate")));
						quotation.setInvestmentFaceValue(StringUtility.checknull(reusltSet.getString("investment_facevalue")));
						quotation.setInvestmentMode(StringUtility.checknull(reusltSet.getString("investmentmode")));
						quotation.setInvestmentType(StringUtility.checknull(reusltSet.getString("investmenttype")));
						quotation.setLetterNo(StringUtility.checknull(reusltSet.getString("LETTER_NO")));
						quotation.setMaturityDate(StringUtility.checknull(reusltSet.getString("MaturityDate")));
						quotation.setNumberOfUnits(StringUtility.checknull(reusltSet.getString("number_of_units")));
						quotation.setPremiumPaid(StringUtility.checknull(reusltSet.getString("premium_paid")));
						quotation.setPurchasePrice(StringUtility.checknull(reusltSet.getString("purchaeprice")));
						quotation.setQuotationCd(reusltSet.getString("quotationcd"));
						quotation.setRating(StringUtility.checknull(reusltSet.getString("RATING")));
						quotation.setRedemptionDate(StringUtility.checknull(reusltSet.getString("redemptiondate")));
						quotation.setRemarks(StringUtility.checknull(reusltSet.getString("remarks")));
						quotation.setSecurityCd(StringUtility.checknull(reusltSet.getString("CATEGORYCD")));
						quotation.setSecurityCategory(StringUtility.checknull(reusltSet.getString("CATEGORYID")));
						
						quotation.setSecurityName(StringUtility.checknull(reusltSet.getString("SECURITY_NAME")));
						quotation.setTrustName(StringUtility.checknull(reusltSet.getString("TRUSTTYPE")));
						quotation.setYtm(StringUtility.checknull(reusltSet.getString("YTMPercentage")));
						quotation.setDematno(StringUtility.checknull(reusltSet.getString("DEMAT_NO")));
						quotation.setPurchaseOption(StringUtility.checknull(reusltSet.getString("purchase_option")));
						quotation.setYtmcall(StringUtility.checknull(reusltSet.getString("YTM_Call")));
						quotation.setYtmmaturity(StringUtility.checknull(reusltSet.getString("ytmmaturity")));
						quotation.setClosingDate(StringUtility.checknull(reusltSet.getString("closingdate")));
						quotation.setTenure(StringUtility.checknull(reusltSet.getString("tenure")));
						quotation.setMinApp(StringUtility.checknull(reusltSet.getString("minApp")));
						quotation.setEligpftrust(StringUtility.checknull(reusltSet.getString("eligibility_PF")));
						quotation.setDealDate(StringUtility.checknull(reusltSet.getString("DEALDATE")));
						quotation.setSettlementDate(StringUtility.checknull(reusltSet.getString("SETTLEMENTDATE")));
						
						
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("QuotationDAO:findQuotation for Cd: "+quotationcode+" :result:"+quotation);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("QuotationDAO:findQuotation:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationDAO:findQuotation:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return quotation;
	}

	public void deleteQuotation(String quotationcodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			log.info("the cd is...."+quotationcodes);
			connection = DBUtility.getConnection();			
			String deletequery ="delete  from INVEST_QUOTAION_DATA where QUOTATIONCD in ('"+quotationcodes+"')";
			String updatequery="update INVEST_REGISTER set DELSTATUS='Y' where LETTER_NO in(select LETTER_NO from INVEST_QUOTAION_DATA where QUOTATIONCD in ('"+quotationcodes+"')) ";
			log.info("QuotationDAO: deleteQuotation Qry:"+deletequery );
			log.info("QuotationDAO: UPDATEQUERY Qry:"+updatequery );
			
			if(connection!=null){
				stmt=connection.createStatement();
				if(stmt!=null){	
					stmt.executeUpdate(updatequery);
					stmt.executeUpdate(deletequery);
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("QuotationDAO:deleteQuotation:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationDAO:deleteQuotation:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List gettypeOfCalls()
	{
		Bean bean1=new Bean();
		bean1.setName("Call Option");
		bean1.setCode("C");
		Bean bean2=new Bean();
		bean2.setName("Put Call Option");
		bean2.setCode("P");
		Bean bean3=new Bean();
		bean3.setName("Both");
		bean3.setCode("B");
		bean3.setName("Any");
		bean3.setCode("A");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		
		
		return list;
	}
	public List getinvestmentTypes()
	{
		Bean bean1=new Bean();
		bean1.setName("Primary");
		bean1.setCode("P");
		Bean bean2=new Bean();
		bean2.setName("Secondary");
		bean2.setCode("S");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		return list;
	}
	public List getGuarenteTypes()
	{
		Bean bean1=new Bean();
		bean1.setName("Central");
		bean1.setCode("C");
		Bean bean2=new Bean();
		bean2.setName("State");
		bean2.setCode("S");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		return list;
		
	}
	public List getinterestDates()
	{
		List list=new ArrayList();
		for(int i=1; i<=31; i++)
		{
		Bean bean1=new Bean();
		if(i<=9)
		{
			bean1.setName("0"+i);
			bean1.setCode("0"+i);
		}
		else
		{
			bean1.setName(i+"");
			bean1.setCode(i+"");
		}
		list.add(bean1);
		}
		
	return list;
	
	}
	public List getInvestmentModes()
	{
		
		
		Bean bean1=new Bean();
		bean1.setName("Half Yearly");
		bean1.setCode("H");
		Bean bean2=new Bean();
		bean2.setName("Yearly");
		bean2.setCode("Y");
		List list=new ArrayList();
		
		list.add(bean2);
		list.add(bean1);
		
		return list;
	}
	public List getinterestMonths()
	{
		String monthName[]={"Jan","Feb","Mar","APr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String monthValue[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
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
	public List getPutCallAnnualize()
	{
		Bean bean1=new Bean();
		bean1.setName("Put");
		bean1.setCode("P");
		Bean bean2=new Bean();
		bean2.setName("Call");
		bean2.setCode("C");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		return list;
	}
	public List getPurchasePriceOption()
	{
		Bean bean1=new Bean();
		bean1.setName("Premium Paid Per Unit");
		bean1.setCode("P");
		Bean bean2=new Bean();
		bean2.setName("Discount Per Unit");
		bean2.setCode("D");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		return list;
	}
	public List getLetterNumbers()throws ServiceNotAvailableException, EPISException
	{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		Statement stmt=null;
		QuotationBean qbean=null;
		try{
			String strSql="Select distinct LETTER_NO from invest_quotaion_data where QUOTATIONCD is NOT NULL";
			connection=DBUtility.getConnection();
			if(connection !=null)
			{
				stmt=connection.createStatement();
				reusltSet=stmt.executeQuery(strSql);
				while(reusltSet.next())
				{
					qbean=new QuotationBean();
					qbean.setLetterNo(reusltSet.getString("LETTER_NO"));
					list.add(qbean);
					
				}
				
			}
			else{
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("QuotationDAO:deleteQuotation:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationDAO:deleteQuotation:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
		
		return list;
	}
	/*public static void main(String args[])
	{
		List list=QuotationDAO.getInstance().getinterestMonths();
		for(int i=0; i<list.size(); i++)
			System.out.println("the names are"+((Bean)list.get(i)).getCode());
	}*/
	
                   
}
