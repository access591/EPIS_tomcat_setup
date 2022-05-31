package com.epis.dao.investment;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.bean.investment.QuotationAppBean;
import com.epis.bean.investment.QuotationRequestBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CurrencyFormat;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class InvestApprovalDAO {
	Log log=new Log(InvestApprovalDAO.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	PreparedStatement pstmt=null;
	private InvestApprovalDAO(){
			
		}
	   private static final InvestApprovalDAO investDAO= new InvestApprovalDAO();
	   public static InvestApprovalDAO getInstance(){
		return investDAO;   
	   }
	public List searchInvestApproval(QuotationBean qbean) throws Exception {
		List list =new ArrayList();;
		try{
			log.info("this is calling for search results.....");
			QuotationBean appbean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select distinct(LETTER_NO)LETTER_NO,QUOTATIONCD,to_char(OPENDATE,'dd/Mon/yyyy')OPENDATE,decode(appstatus,'','All','S','Stmt generated','N','Stmt Not Generated')STATUS,Nvl(APPROVED_1,'N')||'|'||  Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N')  flags,Decode('A',APPROVED_7,'Approved_7',APPROVED_6,'Approved_6',APPROVED_5,'Approved_5',APPROVED_4,'Approved_4',APPROVED_3,  'Approved_3',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') approvedstatus,(case when nvl(BANKLETTERSTATUS,'N')='Y' then 'Y' else 'N' end)  qdata   from invest_quotaion_data where QUOTATIONCD is not null  and SHORTLISTED = 'Y' and approvalstatus='A' and  LETTER_NO like ?";
			boolean bool=false;
			if(!"".equals( qbean.getStatus())){
				query +=" and  appstatus = ? ";
				bool = true;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%"+qbean.getLetterNo()+"%");
			if(bool)
				ps.setString(2, qbean.getStatus());
			rs=ps.executeQuery();
			while(rs.next()){
				appbean = new QuotationBean();
				appbean.setLetterNo(rs.getString("LETTER_NO"));
				appbean.setOpendate(rs.getString("OPENDATE"));
				appbean.setQuotationCd(rs.getString("QUOTATIONCD"));
				appbean.setStatus(rs.getString("STATUS"));
				appbean.setApprovalStatus(rs.getString("approvedstatus"));
				appbean.setFlags(rs.getString("flags"));
				appbean.setQdata(rs.getString("qdata"));
				
				list.add(appbean);
			}
			log.info("this is ending of the statement...."+list.size());
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("ComparativeDAO:searchComparative():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		
		return list;
	}
	public HashMap showInvestApproval(QuotationBean qbean) throws Exception {
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		QuotationBean bean=null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);
			String updateDate ="Update invest_quotaion_data set APP_DATE=?,APPROVAL=?,appstatus='S',APP_REMARKS=? where LETTER_NO=? ";
			 PreparedStatement updateps =con.prepareStatement(updateDate);
			 updateps.setString(1,StringUtility.checknull(qbean.getOpendate()));
			 updateps.setString(2,StringUtility.checknull(qbean.getStatus()));	
			 updateps.setString(3,StringUtility.checknull(qbean.getRemarks()));	
			 updateps.setString(4,qbean.getLetterNo());
			 updateps.executeUpdate();
			 updateps.close();
			
			
			
			String getcompstmtSql ="select qu.Arrangercd Arrangercd,arrangername,Security_Name,Nvl(Purchaeprice,0)Purchaeprice,Nvl(YTMPERCENTAGE,0)YTMPERCENTAGE,qu.app_remarks remarks from invest_quotaion_data qu, invest_arrangers arr where qu.Arrangercd = arr.arrangercd and (qu.shortlisted ='Y' or qu.SLANOTNEEDED='Y') and qu.letter_no=?";
			log.info("ComparativeDAO:saveComparativeStatus(): "+getcompstmtSql);	
			  PreparedStatement pst = con.prepareStatement( getcompstmtSql);
			  pst.setString(1,qbean.getLetterNo());
			  rs=pst.executeQuery();
				while(rs.next()){
					bean = new QuotationBean();
					bean.setArrangerCd(rs.getString("Arrangercd"));
					bean.setArranger(rs.getString("arrangername"));
					bean.setSecurityName(rs.getString("Security_Name"));
					bean.setPurchasePrice(rs.getString("Purchaeprice"));
					bean.setYtm(rs.getString("YTMPERCENTAGE"));
					bean.setRemarks(rs.getString("remarks"));
					if(map.containsKey(rs.getString("arrangername")))
					{
						map1=(HashMap)map.get(rs.getString("arrangername"));
					}else{
						map1=new HashMap();
					}
					map1.put(rs.getString("Security_Name"),bean);
					map.put(rs.getString("arrangername"),map1);
					
				}
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("ComparativeDAO:saveComparativeStatus():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
		return map;
	}
	public HashMap getApprovalReport(QuotationBean qbean)throws Exception {
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		QuotationBean bean=null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String getcompstmtSql ="select qu.Arrangercd Arrangercd,arrangername,Security_Name,Nvl(Purchaeprice,0)Purchaeprice,Nvl(YTMPERCENTAGE,0)YTMPERCENTAGE,qu.app_remarks remarks,decode(approvalstatus,'A','Approved','R','Reject','Not Approve')APPROVAL from invest_quotaion_data qu, invest_arrangers arr where qu.Arrangercd = arr.arrangercd and (qu.shortlisted ='Y' or qu.SLANOTNEEDED='Y') and qu.QUOTATIONCD=?";
			log.info("ComparativeDAO:saveComparativeStatus(): "+getcompstmtSql);	
			  PreparedStatement pst = con.prepareStatement( getcompstmtSql);
			  pst.setString(1,qbean.getQuotationCd());
			  rs=pst.executeQuery();
				while(rs.next()){
					bean = new QuotationBean();
					bean.setArrangerCd(rs.getString("Arrangercd"));
					bean.setArranger(rs.getString("arrangername"));
					bean.setSecurityName(rs.getString("Security_Name"));
					bean.setPurchasePrice(rs.getString("Purchaeprice"));
					bean.setYtm(rs.getString("YTMPERCENTAGE"));
					bean.setMode(rs.getString("APPROVAL"));
					bean.setRemarks(rs.getString("remarks"));
					if(map.containsKey(rs.getString("arrangername")))
					{
						map1=(HashMap)map.get(rs.getString("arrangername"));
					}else{
						map1=new HashMap();
					}
					map1.put(rs.getString("Security_Name"),bean);
					map.put(rs.getString("arrangername"),map1);
					
				}
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("ComparativeDAO:getApprovalReport():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
		return map;
	}
	public void saveInvestApproval(QuotationBean bean) throws Exception {
		UtilityBean util = null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);	
			  
			  List list = bean.getShortListArrangres();
				for(int i=0;i<list.size();i++){
					/*String saveShortListSql ="update invest_quotaion_data qu set SHORTLISTED = 'Y',appstatus='S',approvalstatus='A',APP_UPDATED_BY=?,APP_UPDATED_DT=SYSDATE where  qu.letter_no=? and upper(qu.SECURITY_NAME) = upper(?) and qu.arrangercd =(select arrangercd from invest_arrangers arr  where upper(arr.arrangername) = upper(?)) ";
					log.info("ShortListDAO:saveShortList(): "+saveShortListSql+"    "+bean.getUserId());
					  PreparedStatement ps =con.prepareStatement(saveShortListSql);
					  ps.setString(1,StringUtility.checknull(bean.getUserId()));
					  ps.setString(2,bean.getLetterNo());
					  util = (UtilityBean)list.get(i);
					  ps.setString(3,util.getValue());
					  ps.setString(4,bean.getArranger());
					  ps.executeUpdate();
					  ps.close();
					  log.info("ShortListDAO:util.getValue(): "+util.getValue()+"  getArranger()  "+bean.getArranger());*/
					String saveShortListSql ="update invest_quotaion_data qu set SHORTLISTED = 'Y',approvalstatus='A',APP_UPDATED_BY=?,APP_UPDATED_DT=SYSDATE where  qu.letter_no=? and upper(qu.SECURITY_NAME) = upper(?) and qu.arrangercd =(select arrangercd from invest_arrangers arr  where upper(arr.arrangername) = upper(?))  ";
					log.info("ShortListDAO:saveShortList(): "+saveShortListSql+"    "+bean.getLoginUserId());
					  PreparedStatement ps =con.prepareStatement(saveShortListSql);
					 ps.setString(1,StringUtility.checknull(bean.getLoginUserId()));
					  ps.setString(2,bean.getLetterNo());
					 /* util = (UtilityBean)list.get(i);
					  ps.setString(3,util.getValue());
					  ps.setString(4,bean.getArranger());*/
					  util = (UtilityBean)list.get(i);
					  ps.setString(3,util.getValue());
					  ps.setString(4,bean.getArranger());
					  
					  ps.executeUpdate();
					  ps.close();
					 // log.info("ShortListDAO:util.getValue(): "+util.getValue()+"  getArranger()  "+bean.getArranger());
				}
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("ShortListDAO:saveShortList():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	public QuotationBean getInvestRegister(String letterno) throws Exception {
		QuotationBean appbean =null;
		String[] qData = letterno.split("\\*");
		try{
			
			
			double investamt=0.0;
			double purchaseprice=0.0;
			double facevalue=0.0;
			double totalfacevalue=0.0;
			double settlementamount=0.0;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select to_char(proposal.proposaldate,'dd/Mon/yyyy') invest_date,proposal.amt_inv investamt,qu.PROPOSAL_REF_NO refNo, qu.security_name,qu.letter_no,qu.trusttype,qu.disnicno,qu.facevalue cuponrate,qu.INVESTMENT_FACEVALUE facevalue, " +
					" qu.number_of_units,qu.premium_paid,qu.PURCHASE_OPTION PURCHASE_OPTION,qu.categoryid,sec.categorycd,qu.interest_rate, " +
					" qu.direct_incentive,qu.broker_incentive,qu.brokername,qu.brokeraddress,qu.ytmpercentage, " +
					" qu.rating,qu.remarks,decode(qu.investmenttype,'P','Primary','S','Secondary')investtype, " +
					" investmenttype,qu.INTEREST_DAY_MONTH INTEREST_DAY_MONTH,qu.INTEREST_DAY_MONTH1 INTEREST_DAY_MONTH1,to_char(to_date(qu.INTEREST_DAY_MONTH,'dd/MM'),'dd/Mon')interestdispmonth,to_char(to_date(qu.INTEREST_DAY_MONTH1,'dd/MM'),'dd/Mon')interestdispmonth1, to_char( " +
					" qu.REDEMPTIONDATE,'dd/Mon/yyyy')REDEMPTIONDATE,to_char(qu.MATURITYDATE,'dd/Mon/yyyy') " +
					" MATURITYDATE,decode(CALLOPTION,'C','Call Option','P','Put Call Option','B','Both')CALLOPT, " +
					" CALLOPTION,decode(INVESTMENTMODE,'M','Monthly','H','Half Yearly','Y','Yearly')INVESTMODE, " +
					" INVESTMENTMODE,decode(GUARENTEETYPE,'C','Central','S','State')GUARENTEE,GUARENTEETYPE, " +
					" ge.PRINCIPAL_AMOUNT PURCHAEPRICE,PURCHAEPRICE offerprice,ge.SETTLEMENT_AMOUNT SETTLEMENT_AMOUNT,qu.ARRANGERCD, to_char(SETTLEMENT_DATE,'dd/Mon/yyyy') settlementDate,to_char(DEALDATE,'dd/Mon/yyyy') DEALDATE,(nvl(qu.INVESTMENT_FACEVALUE,0)*nvl(NUMBER_OF_UNITS,0))facevalueinrs from invest_quotaion_data qu,invest_generate_bankletter ge,invest_sec_category sec,invest_proposal proposal where APPROVALSTATUS='A' " +
					" and  sec.categoryid=qu.categoryid and qu.proposal_ref_no=proposal.ref_no and qu.LETTER_NO=ge.LETTER_NO and qu.letter_no=? and qu.security_name=? and qu.ARRANGERCD=?";
			
			
			//log.info("the query is...."+query);
			
			PreparedStatement ps = con.prepareStatement(query);
			
			 ps.setString(1,qData[0]);
			 ps.setString(2,qData[1]);
			 ps.setString(3,qData[2]);
			rs=ps.executeQuery();
			
			if(rs.next()){
				appbean = new QuotationBean();
				appbean.setLetterNo(rs.getString("LETTER_NO"));
				appbean.setSecurityName(rs.getString("SECURITY_NAME"));
				appbean.setTrustName(rs.getString("TRUSTTYPE"));
				appbean.setDisIncNo(rs.getString("DISNICNO"));
				appbean.setFaceValue(rs.getString("cuponrate"));
				appbean.setNumberOfUnits(rs.getString("NUMBER_OF_UNITS"));
				appbean.setInvestdate(StringUtility.checknull(rs.getString("invest_date")));
				investamt=rs.getDouble("investamt");
				appbean.setAmountInv(String.valueOf(new CurrencyFormat().getDecimalCurrency(investamt)));
				facevalue=rs.getDouble("facevalue");
				purchaseprice=rs.getDouble("PURCHAEPRICE");
				totalfacevalue=rs.getDouble("facevalueinrs");
				settlementamount=rs.getDouble("SETTLEMENT_AMOUNT");
				appbean.setInvestmentFaceValue(String.valueOf(new CurrencyFormat().getDecimalCurrency(facevalue)));
				appbean.setPremiumPaid(rs.getString("PREMIUM_PAID"));
				appbean.setPurchaseOption(rs.getString("PURCHASE_OPTION"));
				appbean.setProposalRefNo(StringUtility.checknull(rs.getString("refNo")));
				appbean.setSecurityCategory(rs.getString("categorycd"));
				appbean.setSecurityCd(rs.getString("categoryid"));
				appbean.setInvesttype(rs.getString("investtype"));
				appbean.setInvestmentType(rs.getString("investmenttype"));
				appbean.setInvestmode(rs.getString("INVESTMODE"));
				appbean.setInvestmentMode(rs.getString("INVESTMENTMODE"));
				appbean.setInterestDate(rs.getString("INTEREST_DAY_MONTH"));
				appbean.setInterestDate1(rs.getString("INTEREST_DAY_MONTH1"));
				appbean.setInterestdispmonth(StringUtility.checknull(rs.getString("interestdispmonth")));
				appbean.setInterestdispmonth1(StringUtility.checknull(rs.getString("interestdispmonth1")));				
				appbean.setInterestRate(rs.getString("INTEREST_RATE"));
				appbean.setDirectIncentive(rs.getString("DIRECT_INCENTIVE"));
				appbean.setBrokerIncentive(rs.getString("BROKER_INCENTIVE"));
				appbean.setRedemptionDate(rs.getString("REDEMPTIONDATE"));
				appbean.setBrokerName(rs.getString("BROKERNAME"));
				appbean.setBrokerAddress(rs.getString("BROKERADDRESS"));
				appbean.setMaturityDate(rs.getString("MATURITYDATE"));
				appbean.setYtm(rs.getString("YTMPERCENTAGE"));
				appbean.setCallopt(rs.getString("CALLOPT"));
				appbean.setCallOption(rs.getString("CALLOPTION"));
				appbean.setRating(rs.getString("RATING"));
				appbean.setGuarantee(rs.getString("GUARENTEE"));
				appbean.setGuarenteeType(rs.getString("GUARENTEETYPE"));
				appbean.setRemarks(rs.getString("REMARKS"));
				appbean.setArrangerCd(rs.getString("ARRANGERCD"));
				appbean.setSettlementDate(rs.getString("settlementDate"));
				appbean.setDealDate(rs.getString("DEALDATE"));
				appbean.setPriceoffered(rs.getString("offerprice"));
				appbean.setPurchasePrice(String.valueOf(new CurrencyFormat().getDecimalCurrency(purchaseprice)));
				appbean.setSettlementAmount(String.valueOf(new CurrencyFormat().getDecimalCurrency(settlementamount)));
				appbean.setTotalFaceValue(String.valueOf(new CurrencyFormat().getDecimalCurrency(totalfacevalue)));
				
				
			}
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("ComparativeDAO:searchComparative():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		
		return appbean;
	}
	 
	public void addInvestRegister(QuotationBean qbean) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;		
		PreparedStatement pstmt=null;
		PreparedStatement ps=null;
		String registerCd="";
		try{
			
			connection = DBUtility.getConnection();			
			if(connection!=null){
				String saveunitSql ="insert into investment_register(REGISTER_CD,LETTER_NO,SECURITY_NAME,PROPOSAL_REF_NO," +
				"TRUSTTYPE,CATEGORYCD,CATEGORYID,AMT_INV,NO_OF_BONDS,DEALDATE,SETTLEMENT_DATE,MATURITY_DATE,CUPON_RATE," +
				"PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,MODEOF_INTEREST,INTEREST_DATE," +
				"INTEREST_DATE1,PURCHASE_PRICE,REGISTER_ACCEPTED,INVEST_DATE,SETTLEMENT_AMOUNT,DISNICNO,RATING,DIRECT_INCENTIVE," +
				"BROKER_INCENTIVE,REDEMPTIONDATE,INVESTMENTTYPE,GUARENTEETYPE,BROKERNAME,BROKERADDRESS,REMARKS,REGISTRAR_DETAILS," +
				"ARRANGERCD,CREATED_BY,CREATED_DT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		 registerCd =AutoGeneration.getNextCode("investment_register","REGISTER_CD",5,connection);
				 
				 
				 
				pstmt=connection.prepareStatement(saveunitSql);	
				
				
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(registerCd));
					pstmt.setString(2,StringUtility.checknull(qbean.getLetterNo()));
					pstmt.setString(3,StringUtility.checknull(qbean.getSecurityName()));
					pstmt.setString(4,StringUtility.checknull(qbean.getProposalRefNo()));
					pstmt.setString(5,StringUtility.checknull(qbean.getTrustName()));
					pstmt.setString(6,StringUtility.checknull(qbean.getSecurityCategory()));
					pstmt.setString(7,StringUtility.checknull(qbean.getSecurityCd()));
					pstmt.setString(8,StringUtility.checknull(qbean.getAmountInv()).replaceAll(",",""));
					pstmt.setString(9,StringUtility.checknull(qbean.getNumberOfUnits()));
					pstmt.setString(10,StringUtility.checknull(qbean.getDealDate()));
					pstmt.setString(11,StringUtility.checknull(qbean.getSettlementDate()));
					pstmt.setString(12,StringUtility.checknull(qbean.getMaturityDate()));
					pstmt.setString(13,StringUtility.checknull(qbean.getFaceValue()).replaceAll(",",""));
					pstmt.setString(14,StringUtility.checknull(qbean.getPremiumPaid()));
					pstmt.setString(15,StringUtility.checknull(qbean.getInvestmentFaceValue()).replaceAll(",",""));
					pstmt.setString(16,StringUtility.checknull(qbean.getPurchaseOption()));
					pstmt.setString(17,StringUtility.checknull(qbean.getPriceoffered()));
					pstmt.setString(18,StringUtility.checknull(qbean.getYtm()));
					pstmt.setString(19,StringUtility.checknull(qbean.getCallOption()));  
					pstmt.setString(20,StringUtility.checknull(qbean.getInvestmentMode()));
					pstmt.setString(21,StringUtility.checknull(qbean.getInterestDate()));
					pstmt.setString(22,StringUtility.checknull(qbean.getInterestDate1()));
					pstmt.setString(23,StringUtility.checknull(qbean.getPurchasePrice()).replaceAll(",",""));
					pstmt.setString(24,StringUtility.checknull("Y"));
					pstmt.setString(25,StringUtility.checknull(qbean.getInvestdate()));
					pstmt.setString(26,StringUtility.checknull(qbean.getSettlementAmount()).replaceAll(",",""));
					pstmt.setString(27,StringUtility.checknull(qbean.getDisIncNo()));
					pstmt.setString(28,StringUtility.checknull(qbean.getRating()));
					pstmt.setString(29,StringUtility.checknull(qbean.getDirectIncentive()));
					pstmt.setString(30,StringUtility.checknull(qbean.getBrokerIncentive()));
					pstmt.setString(31,StringUtility.checknull(qbean.getRedemptionDate()));
					pstmt.setString(32,StringUtility.checknull(qbean.getInvestmentType()));				
					pstmt.setString(33,StringUtility.checknull(qbean.getGuarenteeType()));	
					pstmt.setString(34,StringUtility.checknull(qbean.getBrokerName()));
					pstmt.setString(35,StringUtility.checknull(qbean.getBrokerAddress()));
					pstmt.setString(36,StringUtility.checknull(qbean.getRemarks()));
					pstmt.setString(37,StringUtility.checknull(qbean.getRegistrardetails()));
					pstmt.setString(38,StringUtility.checknull(qbean.getArrangerCd()));
					pstmt.setString(39,StringUtility.checknull(qbean.getLoginUserId()));
					
					
					
					pstmt.executeUpdate();
					
					String upquery ="update invest_quotaion_data set regflag='Y' where BANKLETTERSTATUS='Y' and LETTER_NO=? and SECURITY_NAME=? and ARRANGERCD=?";
					ps=connection.prepareStatement(upquery);	
					ps.setString(1,qbean.getLetterNo());
					ps.setString(2,qbean.getSecurityName());
					ps.setString(3,qbean.getArrangerCd());
					ps.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestApprovalDAO:addInvestRegister:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestApprovalDAO:addInvestRegister:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}
	public List getApprovalLetters() throws EPISException {

		List list=new ArrayList();
		QuotationRequestBean bean=null;
		try{
			String quotationQuery="select distinct letter_no||'*'||SECURITY_NAME||'*'||ARRANGERCD letter_no,letter_no||' - '||SECURITY_NAME secName from invest_quotaion_data where APPROVALSTATUS='A' and regflag is null and BANKLETTERSTATUS ='Y' ";
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				rs=st.executeQuery(quotationQuery);
				while(rs.next())
				{
					bean=new QuotationRequestBean();
					bean.setLetterNo(rs.getString("LETTER_NO"));
					bean.setSecurityName(rs.getString("secName"));
					list.add(bean);
				}

			}
			else {
				throw new ServiceNotAvailableException();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("InvestApprovalDAO:getApprovalLetters:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
		return list;
	
	}
	public List getAppLetterNos() throws ServiceNotAvailableException, EPISException {
		List list =new ArrayList();
		PreparedStatement pstmt=null;
		try{
			QuotationBean qbean =null;
			con = DBUtility.getConnection();
			String query = "Select distinct(LETTER_NO) from invest_quotaion_data WHERE LETTER_NO IS NOT NULL and opendate is not null  and (SHORTLISTED ='Y' or SLANOTNEEDED='Y')  and APPSTATUS ='N'";
			query +=" order by LETTER_NO";
			if(con!=null){
				pstmt = con.prepareStatement(query);	
				rs = pstmt.executeQuery();
			while(rs.next()){
				qbean = new QuotationBean();
				qbean.setLetterNo(rs.getString("LETTER_NO"));
				list.add(qbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
			}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestApprovalDAO:getAppLetterNos:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestApprovalDAO:getAppLetterNos:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
		return list;
	}
	public List getCashBookSecurities() throws ServiceNotAvailableException, EPISException {
		List list =new ArrayList();
		PreparedStatement pstmt=null;
		try{
			QuotationBean qbean =null;
			con = DBUtility.getConnection();
			String query = "select replace(PARTYNAME,'&',' ^ ')PARTYNAME from party_info order by PARTYNAME";
			
			if(con!=null){
				pstmt = con.prepareStatement(query);	
				rs = pstmt.executeQuery();
			while(rs.next()){
				qbean = new QuotationBean();
				qbean.setSecurityName(rs.getString("PARTYNAME"));
				list.add(qbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
			}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestApprovalDAO:getCashBookSecurities():SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestApprovalDAO:getCashBookSecurities():Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
		return list;
	}
	
	
	public InvestmentRegisterBean generateInvestmentRegisterReport(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentRegisterBean registerBean=new InvestmentRegisterBean();
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				String selectQuery="SELECT LETTER_NO,decode(REGISTER_ACCEPTED,'Y','Accept','N','Reject')REGISTER_ACCEPTED,company.SECURITY_NAME,LETTER_NO,company.PROPOSAL_REF_NO,company.TRUSTTYPE,company.CATEGORYCD,company.AMT_INV,company.NO_OF_BONDS,CONFIRMATION,decode(CONFIRMATION,'Y','YES','N','NO')CONFIRMATIONDEF,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/YYYY')SETTLEMENT_DATE,to_char(MATURITY_DATE,'dd/Mon/yyyy')MATURITY_DATE,CUPON_RATE,PREMIUM_PAID,FACEVALUE,PURCHASE_OPTION,decode(PURCHASE_OPTION,'P','Premium Paid Per Unit','D','Discount Per Unit')PURCHASE_OPTIONdef,OFFERED_PRICE,YTM_VALUE,CALL_OPTION,decode(CALL_OPTION,'Y','YES','N','NO')CALLOPTIONDEF,nvl(to_char(CALL_DATE,'dd/Mon/yyyy'),'')CALL_DATE,MODEOF_INTEREST,DECODE(MODEOF_INTEREST,'Y','YEARLY','H','HALF-YEARLY')MODEOFINTERESTDEF,nvl(to_char(to_date(INTEREST_DATE,'dd/MM'),'dd/Mon'),'')INTEREST_DATE,nvl(to_char(to_date(INTEREST_DATE1,'dd/MM'),'dd/Mon'),'')INTEREST_DATE1,PURCHASE_PRICE FROM investment_register company where REGISTER_CD is not null and REGISTER_CD='"+bean.getRegisterCd()+"'";
				rs=DBUtility.getRecordSet(selectQuery,st);
				if(rs.next())
				{
					registerBean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
					registerBean.setAcceptence(StringUtility.checknull(rs.getString("REGISTER_ACCEPTED")));
					registerBean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
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
	
	
	public QuotationBean findapprovalconfirmation(String quotationCd)throws EPISException,ServiceNotAvailableException
	{
		QuotationBean bean=null;
		String approvalQuery="";
		try{
				approvalQuery="Select LETTER_NO,QUOTATIONCD,Nvl(form.APPROVED_1,'N') APPROVED_1, to_char(form.APPROVE_1_DT,'dd/Mon/yyyy')  APPROVE_1_DT ,Nvl(form.APPROVAL_1_REMARKS,' ') APPROVAL_1_REMARKS,Nvl(form.APPROVED_2,'N') APPROVED_2, to_char(form.APPROVE_2_DT, 'dd/Mon/yyyy') APPROVE_2_DT ,Nvl(form.APPROVAL_2_REMARKS,'  ') APPROVAL_2_REMARKS,Nvl(form.APPROVED_3,'N') APPROVED_3, to_char(form.APPROVE_3_DT,'dd/Mon/yyyy') APPROVE_3_DT ,Nvl(form.APPROVAL_3_REMARKS,'  ') APPROVAL_3_REMARKS,Nvl(form.APPROVED_4,'N') APPROVED_4, to_char(form.APPROVE_4_DT,'dd/Mon/yyyy') APPROVE_4_DT ,Nvl(form.APPROVAL_4_REMARKS,'  ') APPROVAL_4_REMARKS,Nvl(form.APPROVED_5,'N') APPROVED_5, to_char(form.APPROVE_5_DT,'dd/Mon/yyyy') APPROVE_5_DT , Nvl(form.APPROVAL_5_REMARKS,'  ') APPROVAL_5_REMARKS,nvl(form.APPROVED_6,'N')APPROVED_6,to_char(form.APPROVE_6_DT,'dd/Mon/yyyy')APPROVE_6_DT,nvl(form.APPROVAL_6_REMARKS,' ')APPROVAL_6_REMARKS,nvl(form.APPROVED_7,'N')APPROVED_7,to_char(form.APPROVE_7_DT,'dd/Mon/yyyy')APPROVE_7_DT,nvl(form.APPROVAL_7_REMARKS,' ')APPROVAL_7_REMARKS,form.CREATED_BY,form.APPROVAL_1_BY,form.APPROVAL_2_BY,form.APPROVAL_3_BY, form.APPROVAL_4_BY, form.APPROVAL_5_BY,form.APPROVAL_6_BY,form.APPROVAL_7_BY,Decode('A',form.APPROVED_7,'7',form.APPROVED_6,'6',form.APPROVED_5,'5',form.APPROVED_4,'4',form.APPROVED_3,'3',form.APPROVED_2,'2',form.APPROVED_1,'1','0') flag,to_char(Decode('A',form.APPROVED_7,form.APPROVE_7_DT,form.APPROVED_6,form.APPROVE_6_DT,form.APPROVED_5,form.APPROVE_5_DT,form.APPROVED_4,form.APPROVE_4_DT,form.APPROVED_3,form.APPROVE_3_DT,form.APPROVED_2,form.APPROVE_2_DT,form.APPROVED_1,form.APPROVE_1_DT,proposal.PROPOSALDATE),'dd/Mon/YYYY') appDate  from invest_quotaion_data form,invest_proposal proposal where proposal.REF_NO=form.PROPOSAL_REF_NO and QUOTATIONCD is not null  and QUOTATIONCD=?";
				
				 
				con=DBUtility.getConnection();
				if(con!=null)
				{
					pstmt=con.prepareStatement(approvalQuery);
					if(pstmt!=null)
					{
						pstmt.setString(1,StringUtility.checknull(quotationCd));
						rs=pstmt.executeQuery();
						if(rs.next())
						{
							log.info("the dao is starting here...");
							bean=new QuotationBean();
							bean.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
							bean.setQuotationCd(StringUtility.checknull(rs.getString("QUOTATIONCD")));
							
							Map approvals = new LinkedHashMap();
							QuotationAppBean appbean=new QuotationAppBean();
							appbean.setApprovedBy(bean.getUserId());
							approvals.put("0",appbean);
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_1_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_1_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_1"));
							appbean.setApprovedBy(rs.getString("APPROVAL_1_BY"));
							approvals.put("1",appbean);
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_2_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_2_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_2"));
							appbean.setApprovedBy(rs.getString("APPROVAL_2_BY"));
							approvals.put("2",appbean);
							
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_3_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_3_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_3"));
							appbean.setApprovedBy(rs.getString("APPROVAL_3_BY"));
							approvals.put("3",appbean);
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_4_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_4_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_4"));
							appbean.setApprovedBy(rs.getString("APPROVAL_4_BY"));
							approvals.put("4",appbean);
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_5_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_5_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_5"));
							appbean.setApprovedBy(rs.getString("APPROVAL_5_BY"));
							approvals.put("5",appbean);
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_6_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_6_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_6"));
							appbean.setApprovedBy(rs.getString("APPROVAL_6_BY"));
							approvals.put("6",appbean);
							
							appbean = new QuotationAppBean();
							appbean.setDate(rs.getString("APPROVE_7_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_7_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_7"));
							appbean.setApprovedBy(rs.getString("APPROVAL_7_BY"));
							approvals.put("7",appbean);
							
							bean.setApprovals(approvals);							
							bean.setFlags(rs.getString("flag"));
							bean.setAppDate(rs.getString("appDate"));
							log.info("the dao is ending... here...");
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
	
	
	public void approvalUpdate(QuotationAppBean bean)throws
	  ServiceNotAvailableException,EPISException { 
	  
	  
	  
	  try{ 
		  con=DBUtility.getConnection(); 
		  int i = Integer.parseInt(StringUtility.checknull(bean.getApprovalLevel()));	
		  
		  String updateSql ="update invest_quotaion_data set APPROVED_"+i+"='A',APPROVE_"+i+"_DT=?,APPROVAL_"+i+"_REMARKS=?," +
		  		" APPROVAL_"+i+"_BY=?,APPROVAL_"+i+"_DT=SYSDATE where QUOTATIONCD=?";
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
