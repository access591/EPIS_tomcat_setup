
/**
 * @author anilkumark
 *
 */
package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.bean.investment.InvestmentProposalBean;
import com.epis.bean.investment.InvestmentProposalDt;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.DateValidation;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class InvestmentProposalDAO {
	
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;

	Log log = new Log(InvestmentProposalDAO.class);

	private InvestmentProposalDAO() {
	}

	private static final InvestmentProposalDAO investmentProposalDAO = new InvestmentProposalDAO();

	public static InvestmentProposalDAO getInstance() {
		return investmentProposalDAO;
	}

	public List searchInvestmentProposal(InvestmentProposalBean propBean)
			throws ServiceNotAvailableException, EPISException {
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		InvestmentProposalBean pbean = null;
		String query="";
		try {
			con = DBUtility.getConnection();
			if(propBean.getMode().equals("psuprimary"))
			{
				query = "select REF_NO,trust.TRUSTTYPE,proposal.CATEGORYCD,proposal.AMT_INV,proposal.REMARKS,nvl(to_char(SETTLEMENT_DATE,'dd/Mon/yyyy'),'--')SETTLEMENT_DATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,Nvl(APPROVED_1,'N')||'|'|| " +
				" Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N') " +
				" flags,(case when (select count(*) from invest_formfilling data where data.PROPOSAL_REF_NO " +
				" = proposal.REF_NO )>0 then 'Y' else 'N' end) qdata,Decode('A',APPROVED_7,'Approved_7',APPROVED_6,'Approved_6',APPROVED_5,'Approved_5',APPROVED_4,'Approved_4',APPROVED_3,  'Approved_3',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status from invest_proposal proposal ,INVEST_TRUSTTYPE " +
				" trust,INVESTMENT_REGISTER register where proposal.TRUSTCD=trust.TRUSTCD and proposal.CATEGORYCD='PSU' and MARKET_TYPE='P' and proposal.ref_no=register.PROPOSAL_REF_NO(+) and Upper(ref_no) like upper(nvl(?,''))||'%' " +
				" and proposal.TRUSTCD like upper(nvl(?,''))||'%' and Upper(proposal.CATEGORYCD) like " +
				" upper(nvl(?,''))||'%' order by proposal.CREATED_DT desc ";
				
			}
			else if(propBean.getMode().equals("rbiauction"))
			{
				query = "select REF_NO,trust.TRUSTTYPE,proposal.CATEGORYCD,proposal.AMT_INV,proposal.REMARKS,nvl(to_char(SETTLEMENT_DATE,'dd/Mon/yyyy'),'--')SETTLEMENT_DATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,Nvl(APPROVED_1,'N')||'|'|| " +
				" Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N') " +
				" flags,(case when (select count(*) from invest_formfilling data where data.PROPOSAL_REF_NO " +
				" = proposal.REF_NO )>0 then 'Y' else 'N' end) qdata,Decode('A',APPROVED_7,'Approved_7',APPROVED_6,'Approved_6',APPROVED_5,'Approved_5',APPROVED_4,'Approved_4',APPROVED_3,  'Approved_3',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status from invest_proposal proposal ,INVEST_TRUSTTYPE " +
				" trust,INVESTMENT_REGISTER register where proposal.MODULE_FLAG is null and proposal.TRUSTCD=trust.TRUSTCD and proposal.CATEGORYCD IN('SDL','GOI') and MARKET_TYPE IN('R','RB') and proposal.ref_no=register.PROPOSAL_REF_NO(+)  and Upper(ref_no) like upper(nvl(?,''))||'%' " +
				" and proposal.TRUSTCD like upper(nvl(?,''))||'%' and Upper(proposal.CATEGORYCD) like " +
				" upper(nvl(?,''))||'%' order by proposal.CREATED_DT desc ";
			}
			else
			{
				query = "select REF_NO,trust.TRUSTTYPE,proposal.CATEGORYCD,proposal.AMT_INV,proposal.REMARKS,nvl(to_char(SETTLEMENT_DATE,'dd/Mon/yyyy'),'--')SETTLEMENT_DATE,nvl(SECURITY_NAME,'--')SECURITY_NAME,Nvl(APPROVED_1,'N')||'|'|| " +
				" Nvl(APPROVED_2,'N')||'|'||Nvl(APPROVED_3,'N')||'|'||Nvl(APPROVED_4,'N')||'|'||Nvl(APPROVED_5,'N')||'|'||nvl(APPROVED_6,'N')||'|'||nvl(APPROVED_7,'N') " +
				" flags,(case when (select count(*) from invest_quotationrequest data where data.PROPOSAL_REF_NO " +
				" = proposal.REF_NO )>0 then 'Y' else 'N' end) qdata,(case  when APPROVED_7 = 'A' then  'APPROVED_7' else (case  when APPROVED_6 = 'A' then  'APPROVED_6'  else  (case when APPROVED_5 = 'A' then  'APPROVED_5'  else (case  when APPROVED_4 = 'A' then 'APPROVED_4'  else (case when APPROVED_3 = 'A' then  'APPROVED_3'  else (case when APPROVED_2 = 'A' then 'APPROVED_2' else (case when APPROVED_1 = 'A' then   'APPROVED_1' else 'Not Apporved' end) end) end) end) end)end) end) status from invest_proposal proposal ,INVEST_TRUSTTYPE " +
				" trust,INVESTMENT_REGISTER register where proposal.TRUSTCD=trust.TRUSTCD and MARKET_TYPE not in('P','R','RB') and proposal.ref_no=register.PROPOSAL_REF_NO(+) and Upper(ref_no) like upper(nvl(?,''))||'%' " +
				" and proposal.TRUSTCD like upper(nvl(?,''))||'%' and Upper(proposal.CATEGORYCD) like " +
				" upper(nvl(?,''))||'%' order by proposal.CREATED_DT desc ";
				
			}

			 
			if (con != null) {
				pstmt = con.prepareStatement(query);
				if (pstmt != null) {
					pstmt.setString(1, propBean.getRefNo());
					pstmt.setString(2, propBean.getTrustType());
					pstmt.setString(3, propBean.getSecurityCat());
					
					rs = pstmt.executeQuery();
					while (rs.next()) {
						pbean = new InvestmentProposalBean();
						pbean.setRefNo(rs.getString("REF_NO"));
						pbean.setTrustType(rs.getString("TRUSTTYPE"));
						pbean.setSecurityCat(rs.getString("CATEGORYCD"));
						pbean.setAmountInv(rs.getString("AMT_INV"));
						pbean.setRemarks(rs.getString("REMARKS"));
						pbean.setFlags(rs.getString("flags"));
						pbean.setHasQrs(rs.getString("qdata"));
						pbean.setStatus(rs.getString("STATUS"));
						pbean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
						pbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
						list.add(pbean);
					}
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw new EPISException(snex);
		} catch (SQLException sqlex) {
			throw new EPISException(sqlex);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, con);
		}
		return list;
	}

	public List getProposal() throws ServiceNotAvailableException,EPISException {
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		try {
			InvestmentProposalBean pbean = null;
			con = DBUtility.getConnection();

			String query = "select * from invest_proposal where APPROVED_4='A' and((approved_5='A' and APPROVED_6='A')or(approved_6='A' and APPROVED_7='A') or(approved_5='A' and APPROVED_7='A')) and MARKET_TYPE not in('P','R','RB') and REF_NO NOT IN(select PROPOSAL_REF_NO from invest_quotationrequest)";
			
			if (con != null) {
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					pbean = new InvestmentProposalBean();
					pbean.setRefNo(rs.getString("REF_NO"));
					pbean.setTrustType(rs.getString("TRUSTCD"));
					pbean.setSecurityCat(rs.getString("CATEGORYCD"));
					pbean.setAmountInv(rs.getString("AMT_INV"));
					pbean.setRemarks(rs.getString("REMARKS"));
					list.add(pbean);
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("InvestmentProposalDAO:getProposal:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("InvestmentProposalDAO:getProposal:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, con);
		}

		return list;

	}
	
	
	public List getProposal(String mode) throws ServiceNotAvailableException,EPISException {
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		try {
			InvestmentProposalBean pbean = null;
			con = DBUtility.getConnection();

			String query ="";
			if(mode.equals("PSUPrimary"))
				query="select * from invest_proposal where APPROVED_4='A' and((approved_5='A' and APPROVED_6='A')or(approved_6='A' and APPROVED_7='A') or(approved_5='A' and APPROVED_7='A')) and CATEGORYCD='PSU' AND MARKET_TYPE='P' and REF_NO NOT IN(select PROPOSAL_REF_NO from invest_formfilling)";
			else if(mode.equals("rbiauction"))
				query="select * from invest_proposal where APPROVED_4='A' and((approved_5='A' and APPROVED_6='A')or(approved_6='A' and APPROVED_7='A') or(approved_5='A' and APPROVED_7='A')) and CATEGORYCD IN ('SDL','GOI') AND MARKET_TYPE IN('R' ,'RB') and AMT_INV <>nvl((SELECT SUM(AMT_INV) FROM INVEST_FORMFILLING form WHERE PROPOSAL_REF_NO = REF_NO),0) ";
			
			if (con != null) {
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					pbean = new InvestmentProposalBean();
					pbean.setRefNo(rs.getString("REF_NO"));
					pbean.setTrustType(rs.getString("TRUSTCD"));
					pbean.setSecurityCat(rs.getString("CATEGORYCD"));
					pbean.setAmountInv(rs.getString("AMT_INV"));
					pbean.setRemarks(rs.getString("REMARKS"));
					list.add(pbean);
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("InvestmentProposalDAO:getProposal:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("InvestmentProposalDAO:getProposal:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, con);
		}

		return list;

	}

	
	
	

	public void saveInvestmentProposal(InvestmentProposalBean pbean)
			throws ServiceNotAvailableException, EPISException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1=null;
		try {
			connection = DBUtility.getConnection();
			if (connection != null) {
				String saveQry = "insert into invest_proposal(ref_no, TRUSTCD,SUBJECT,PROPOSALDATE," +
						"MARKET_TYPE,CATEGORYCD,amt_inv,remarks,created_by,created_dt) values " +
						"(?,?,?,?,?,?,?,?,?,SYSDATE)";
				String SaveDtQry="INSERT INTO INVEST_PROPOSAL_DT(PROPOASL_CD,REF_NO,SECURITY_NAME,CREATED_BY,CREATED_DT)" +
						"VALUES(?,?,?,?,SYSDATE)";
				
				pstmt1=connection.prepareStatement(SaveDtQry);
				pstmt = connection.prepareStatement(saveQry);
				if (pstmt != null) {
					pstmt.setString(1, StringUtility
							.checknull(pbean.getRefNo()));
					pstmt.setString(2, StringUtility.checknull(pbean
							.getTrustType()));
					pstmt.setString(3, StringUtility.checknull(pbean
							.getSubject()));
					pstmt.setString(4, StringUtility.checknull(pbean
							.getProposaldate()));
					pstmt.setString(5, StringUtility.checknull(pbean
							.getMarketType()));
					pstmt.setString(6, StringUtility.checknull(pbean
							.getSecurityCat()));
					pstmt.setString(7, StringUtility.checknull(pbean
							.getAmountInv()));
					pstmt.setString(8, StringUtility.checknull(pbean
							.getRemarks()));
					pstmt.setString(9, StringUtility.checknull(pbean
							.getLoginUserId()));
					pstmt.executeUpdate();
				} else {
					throw new ServiceNotAvailableException();
				}
				if(pstmt1!=null)
				{
					if(pbean.getSecurityDetails().size()!=0)
					{
						List investDts=pbean.getSecurityDetails();
						int length=investDts.size();
						for(int i=0; i<length; i++)
						{
							InvestmentProposalDt investdt=(InvestmentProposalDt)investDts.get(i);
							pstmt1.setString(1,AutoGeneration.getNextCode("invest_proposal_dt","PROPOASL_CD",5,connection));
							pstmt1.setString(2,StringUtility.checknull(pbean.getRefNo()));
							pstmt1.setString(3,StringUtility.checknull(investdt.getSecurityName()));
							pstmt1.setString(4,StringUtility.checknull(pbean.getLoginUserId()));
							pstmt1.executeUpdate();
						}
					}
				}
				else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			throw new EPISException(sqlex);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pstmt, connection);
		}
	}

	public void editInvestmentProposal(InvestmentProposalBean pbean)
			throws ServiceNotAvailableException, EPISException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1=null;
		try {

			connection = DBUtility.getConnection();
			st=connection.createStatement();
			String updateSql = "update invest_proposal set TRUSTCD=?,SUBJECT=?,PROPOSALDATE=?,MARKET_TYPE=?,CATEGORYCD=?,amt_inv=?,remarks=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where ref_no=?";
			String deleteqry="delete from invest_proposal_dt where REF_NO='"+pbean.getRefNo()+"'";
			if (connection != null) {
				String SaveDtQry="INSERT INTO INVEST_PROPOSAL_DT(PROPOASL_CD,REF_NO,SECURITY_NAME,CREATED_BY,CREATED_DT)" +
				"VALUES(?,?,?,?,SYSDATE)";
		
				pstmt1=connection.prepareStatement(SaveDtQry);
				pstmt = connection.prepareStatement(updateSql);
				if (pstmt != null) {

					pstmt.setString(1, StringUtility.checknull(pbean
							.getTrustType()));
					pstmt.setString(2, StringUtility.checknull(pbean
							.getSubject()));
					pstmt.setString(3, StringUtility.checknull(pbean
							.getProposaldate()));
					pstmt.setString(4, StringUtility.checknull(pbean
							.getMarketType()));
					pstmt.setString(5, StringUtility.checknull(pbean
							.getSecurityCat()));
					pstmt.setString(6, StringUtility.checknull(pbean
							.getAmountInv()));
					pstmt.setString(7, StringUtility.checknull(pbean
							.getRemarks()));
					pstmt.setString(8, StringUtility.checknull(pbean
							.getLoginUserId()));
					pstmt.setString(9, StringUtility
							.checknull(pbean.getRefNo()));
					pstmt.executeUpdate();
				} else {
					throw new ServiceNotAvailableException();
				}
				st.executeUpdate(deleteqry);
				if(pstmt1!=null)
				{
					if(pbean.getSecurityDetails().size()!=0)
					{
						List investDts=pbean.getSecurityDetails();
						int length=investDts.size();
						log.info("the length is..."+length);
						for(int i=0; i<length; i++)
						{
							InvestmentProposalDt investdt=(InvestmentProposalDt)investDts.get(i);
							pstmt1.setString(1,AutoGeneration.getNextCode("invest_proposal_dt","PROPOASL_CD",5,connection));
							pstmt1.setString(2,StringUtility.checknull(pbean.getRefNo()));
							pstmt1.setString(3,StringUtility.checknull(investdt.getSecurityName()));
							pstmt1.setString(4,StringUtility.checknull(pbean.getLoginUserId()));
							pstmt1.executeUpdate();
						}
					}
				}
				else {
					throw new ServiceNotAvailableException();
				}
				
			} else {
				throw new ServiceNotAvailableException();
			}
			// log.info("Region DAO :editProposal with info :"+pbean);
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			log.error("Region DAO:editProposal:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Region DAO:editProposal:Exception" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pstmt, connection);
		}
	}

	
	  public void approvalUpdate(InvestProposalAppBean bean)throws
		  ServiceNotAvailableException,EPISException { 
		  
		  Connection connection=null;
		  PreparedStatement pstmt=null; 
		  
		  try{ 
			  connection=DBUtility.getConnection(); 
			  int i = Integer.parseInt(StringUtility.checknull(bean.getApprovalLevel()));	
			  
			  String updateSql ="update invest_proposal set APPROVED_"+i+"='A',APPROVE_"+i+"_DT=?,APPROVAL_"+i+"_REMARKS=?," +
			  		" APPROVAL_"+i+"_BY=?,APPROVAL_"+i+"_DT=SYSDATE where REF_NO=?";
			  if(connection!=null){
				  pstmt=connection.prepareStatement(updateSql); 
				  if(pstmt!=null){
					  pstmt.setString(1,StringUtility.checknull(bean.getDate()));
					  pstmt.setString(2,StringUtility.checknull(bean.getRemarks()));
					  pstmt.setString(3,StringUtility.checknull(bean.getLoginUserId()));
					  pstmt.setString(4,StringUtility.checknull(bean.getRefNo()));
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
			  DBUtility.closeConnection(null,pstmt,connection); 
		  } 
	}
	 
	public InvestmentProposalBean findProposal(String refNo, String compare)
			throws ServiceNotAvailableException, EPISException {
		
		Connection connection = null;
		ResultSet rs = null;
		ResultSet rsdt=null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtdt=null;
		InvestmentProposalBean pbean = null;
		InvestmentProposalDt investdt=null;
		List investdts=new ArrayList();
		String searchQry = "";
		String searchDtQry="";

		try {
			searchDtQry="select nvl(SECURITY_NAME,'--')SECURITY_NAME from invest_proposal_dt where ref_no=? ";
			if (compare.equals("edit")) {
				searchQry = "select ref_no,TRUSTCD,AMT_INV,SUBJECT,to_char(PROPOSALDATE,'dd/Mon/yyyy') " +
						" PROPOSALDATE,MARKET_TYPE,CATEGORYCD,remarks from invest_proposal where ref_no =? ";
				
			} else  {
				searchQry = "select ref_no,prop.TRUSTCD,TRUSTTYPE,AMT_INV,SUBJECT,to_char(PROPOSALDATE, " +
						" 'dd/Mon/yyyy') PROPOSALDATE,decode(MARKET_TYPE,'P','Primary','S','Secondary','B' " +
						" ,'Both','R','RBI','O','Open Bid','PS','Primary And Secondary') MARKET_TYPE, " +
						" CATEGORYCD,remarks,Nvl(APPROVED_1,'N') APPROVED_1, to_char(APPROVE_1_DT,'dd/Mon/yyyy') " +
						" APPROVE_1_DT ,Nvl(APPROVAL_1_REMARKS,' ') APPROVAL_1_REMARKS,Nvl(APPROVED_2,'N') APPROVED_2, to_char(APPROVE_2_DT, " +
						" 'dd/Mon/yyyy') APPROVE_2_DT ,Nvl(APPROVAL_2_REMARKS,' ') APPROVAL_2_REMARKS,Nvl(APPROVED_3,'N') APPROVED_3, to_char( " +
						" APPROVE_3_DT,'dd/Mon/yyyy') APPROVE_3_DT ,Nvl(APPROVAL_3_REMARKS,' ') APPROVAL_3_REMARKS,Nvl(APPROVED_4,'N')  " +
						" APPROVED_4, to_char(APPROVE_4_DT,'dd/Mon/yyyy') APPROVE_4_DT ,Nvl(APPROVAL_4_REMARKS,' ') APPROVAL_4_REMARKS,Nvl( " +
						" APPROVED_5,'N') APPROVED_5, to_char(APPROVE_5_DT,'dd/Mon/yyyy') APPROVE_5_DT , " +
						" Nvl(APPROVAL_5_REMARKS,' ') APPROVAL_5_REMARKS,nvl(APPROVED_6,'N')APPROVED_6,to_char(APPROVE_6_DT,'dd/Mon/yyyy')APPROVE_6_DT,nvl(APPROVAL_6_REMARKS,' ')APPROVAL_6_REMARKS,nvl(APPROVED_7,'N')APPROVED_7,to_char(APPROVE_7_DT,'dd/Mon/yyyy')APPROVE_7_DT,nvl(APPROVAL_7_REMARKS,' ')APPROVAL_7_REMARKS,prop.CREATED_BY,APPROVAL_1_BY,APPROVAL_2_BY,APPROVAL_3_BY, " +
						" APPROVAL_4_BY, APPROVAL_5_BY,APPROVAL_6_BY,APPROVAL_7_BY,Decode('A',APPROVED_7,'7',APPROVED_6,'6',APPROVED_5,'5',APPROVED_4,'4',APPROVED_3, " +
						" '3',APPROVED_2,'2',APPROVED_1,'1','0') flag,to_char(Decode('A',APPROVED_7,APPROVE_7_DT,APPROVED_6,APPROVE_6_DT,APPROVED_5,APPROVE_5_DT, " +
						" APPROVED_4,APPROVE_4_DT,APPROVED_3,APPROVE_3_DT,APPROVED_2,APPROVE_2_DT,APPROVED_1, " +
						" APPROVE_1_DT,PROPOSALDATE),'dd/Mon/YYYY') appDate from invest_proposal prop, " +
						" invest_trusttype trust where prop.TRUSTCD=trust.TRUSTCD and ref_no =?  ";
				
			}
			log.info("the search query is..."+searchQry);
			
			
			connection = DBUtility.getConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(searchQry);
				
				if (pstmt != null) {
					pstmt.setString(1, StringUtility.checknull(refNo));
					rs = pstmt.executeQuery();
					if (rs.next()) {
						pbean = new InvestmentProposalBean();
						// pbean.setProposalCD(rs.getString("proposal_cd"));
						pbean.setRefNo(rs.getString("REF_NO"));
						if (compare.equals("edit")) {
							pbean.setTrustType(rs.getString("TRUSTCD"));
						} else  {
							pbean.setTrustType(rs.getString("TRUSTTYPE"));
							Map approvals = new LinkedHashMap();
							pbean.setUserId(rs.getString("CREATED_BY"));
							InvestProposalAppBean appBean = new InvestProposalAppBean();
							appBean.setApprovedBy(pbean.getUserId());
							approvals.put("0",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_1_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_1_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_1"));
							appBean.setApprovedBy(rs.getString("APPROVAL_1_BY"));
							approvals.put("1",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_2_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_2_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_2"));
							appBean.setApprovedBy(rs.getString("APPROVAL_2_BY"));
							approvals.put("2",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_3_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_3_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_3"));
							appBean.setApprovedBy(rs.getString("APPROVAL_3_BY"));
							approvals.put("3",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_4_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_4_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_4"));
							appBean.setApprovedBy(rs.getString("APPROVAL_4_BY"));
							approvals.put("4",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_5_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_5_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_5"));
							appBean.setApprovedBy(rs.getString("APPROVAL_5_BY"));
							approvals.put("5",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_6_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_6_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_6"));
							appBean.setApprovedBy(rs.getString("APPROVAL_6_BY"));
							approvals.put("6",appBean);
							appBean = new InvestProposalAppBean();
							appBean.setDate(rs.getString("APPROVE_7_DT"));
							appBean.setRemarks(rs.getString("APPROVAL_7_REMARKS"));
							appBean.setApproved(rs.getString("APPROVED_7"));
							appBean.setApprovedBy(rs.getString("APPROVAL_7_BY"));
							approvals.put("7",appBean);
							pbean.setApprovals(approvals);							
							pbean.setFlags(rs.getString("flag"));
							pbean.setAppDate(rs.getString("appDate"));
						}
						pbean.setSubject(StringUtility.checknull(rs
								.getString("SUBJECT")));
						pbean.setSecurityCat(rs.getString("CATEGORYCD"));
						pbean.setAmountInv(rs.getString("AMT_INV"));
						pbean.setRemarks(StringUtility.checknull(rs
								.getString("REMARKS")));
						pbean.setProposaldate(rs.getString("PROPOSALDATE"));
						pbean.setMarketType(rs.getString("MARKET_TYPE"));
					}
					
				} else {
					throw new ServiceNotAvailableException();
				}
				pstmtdt=connection.prepareStatement(searchDtQry);
				
			if(pstmtdt!=null)
			{
				
				pstmtdt.setString(1, StringUtility.checknull(refNo));
				rsdt = pstmtdt.executeQuery();
				while(rsdt.next())
				{
					investdt=new InvestmentProposalDt();
					investdt.setSecurityName(StringUtility.checknull(rsdt.getString("SECURITY_NAME")));
					investdts.add(investdt);
				}
				pbean.setSecurityDetails(investdts);
			}
			else
			{
				throw new ServiceNotAvailableException();
			}
				
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			log.error("InvestmentProposalDAO:findProposal:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("InvestmentProposalDAO:findProposal:Exception"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, connection);
		}
		return pbean;
	}

	public void deleteInvestmentProposal(String refnos)
			throws ServiceNotAvailableException, EPISException {

		String deleteSql = "delete  from invest_proposal where ref_no in ('"+ refnos + "')";
		String deletedetail="delete from invest_proposal_dt where ref_no in('"+ refnos + "')";
		try {
			con = DBUtility.getConnection();

			if (con != null) {
				st = con.createStatement();
				if (st != null) {
					DBUtility.setAutoCommit(false, con);
					st.executeUpdate(deletedetail);
					st.executeUpdate(deleteSql);

				} else {
					throw new ServiceNotAvailableException();
				}

			} else {
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			try {
				DBUtility.rollbackTrans(con);

			} catch (Exception e) {
				log.error(e.getMessage());
			}
			sqlex.printStackTrace();
			log
					.error("InitialProposalDAO:deleteInvestmentProposal:SQLException"
							+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("InitialProposalDAO:deleteInvestmentProposal:Exception"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, st, con);
		}
	}

	public List getInvestmentDetails(String refNo,String mode) throws EPISException {
		List details = new ArrayList();		
		ResultSet rs = null;
		Map dates = new LinkedHashMap(); 
		List securities = new ArrayList();
		String[] invDetails = null;
		String fundAccured = null;
		
		try {
			
			String query = "select count(*) from invest_sec_category ";
			int cnt = DBUtility.getRecordCount(query);
			
			con = DBUtility.getConnection();
			if(con != null){
				st = con.createStatement();
				if(st != null){
					query =" select CATEGORYCD from invest_sec_category ";
					rs = DBUtility.getRecordSet(query,st);
					
					int i=0;
					invDetails = new String[cnt+2];
					invDetails[i++] = "Particulars";
					invDetails[i++] = "Purchase Price";
					while(rs.next()){		
						invDetails[i++] = rs.getString(1);
					}
					details.add(invDetails);
					
					rs.close();
					
					query =" select cat.CATEGORYCD, PERCENTAGE, to_char(validfrom, 'dd/Mon/YYYY') validfrom, " +
							" to_char(nvl(validto, sysdate), 'dd/Mon/YYYY') validto from invest_ratio_category " +
							" catratio, invest_ratio ratio, invest_sec_category cat, invest_proposal where " +
							" ratio.RATIOCD = catratio.RATIOCD and ref_no = '"+refNo+"' and cat.categoryid = " +
							" catratio.categoryid and ((case when PROPOSALDATE >= '01/Apr/' || to_char( " +
							" PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') else " +
							" '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) < validto " +
							" or (case when PROPOSALDATE >= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then " +
							" '31/Mar/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) + 1) else '31/Mar/' " +
							" || to_char(PROPOSALDATE, 'YYYY') end) > validfrom) order by validfrom asc ";
					rs = DBUtility.getRecordSet(query,st);
					List percentages = null;
					String[] per = null;					
					while(rs.next()){	
						percentages = (ArrayList)dates.get(rs.getString("validfrom")+"|"+rs.getString("validto"));
						if(percentages== null)
							percentages = new ArrayList();
						per = new String[4];
						per[0] = rs.getString("CATEGORYCD");
						per[1] = rs.getString("PERCENTAGE");
						per[2] = rs.getString("validfrom");
						per[3] = rs.getString("validto");
						percentages.add(per);
						dates.put(rs.getString("validfrom")+"|"+rs.getString("validto"),percentages);
					}
					
					rs.close();
					log.info("the mode is..."+mode);
					if(mode.equals(""))
					{
					query="select replace(reg.security_name,'&','and') securityname, nvl(reg.SETTLEMENT_AMOUNT,0) purchaseprice, to_char( reg.invest_date, 'dd/Mon/YYYY')  investDate,cat.categorycd categorycode " +
							"from invest_sec_category  cat,investment_register reg, invest_proposal prop,invest_trusttype trt where cat.categorycd = reg.categorycd " +
							"and prop.ref_no =  '"+refNo+"' and reg.invest_date between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.trusttype = reg.trusttype and reg.register_cd<nvl((select register_cd  from investment_register  where proposal_ref_no = '"+refNo+"'),'99999') ";
					query+=" union ";
					query+="select cat.categorycd securityname, inv.INV_AMOUNT purchaseprice,to_char(inv.AS_ONDATE,'dd/Mon/yyyy')investDate,cat.categorycd categorycode from investment_made inv,invest_sec_category  cat,invest_proposal prop,invest_trusttype trt where cat.CATEGORYID = inv.CATEGORYID and prop.ref_no ='"+refNo+"' and inv.AS_ONDATE between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.TRUSTCD = inv.TRUSTCD and inv.TRUSTCD=prop.trustcd";
					}
					else if(mode.equals("rbiauction"))
					{
						query="select replace(reg.security_name,'&','and') securityname, nvl(reg.SETTLEMENT_AMOUNT,0) purchaseprice, to_char( reg.invest_date, 'dd/Mon/YYYY')  investDate,cat.categorycd categorycode " +
						"from invest_sec_category  cat,investment_register reg, invest_proposal prop,invest_trusttype trt where cat.categorycd = reg.categorycd " +
						"and prop.ref_no =  '"+refNo+"' and reg.invest_date between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.trusttype = reg.trusttype  ";
						query+=" union ";
						query+="select cat.categorycd securityname, inv.INV_AMOUNT purchaseprice,to_char(inv.AS_ONDATE,'dd/Mon/yyyy')investDate,cat.categorycd categorycode from investment_made inv,invest_sec_category  cat,invest_proposal prop,invest_trusttype trt where cat.CATEGORYID = inv.CATEGORYID and prop.ref_no ='"+refNo+"' and inv.AS_ONDATE between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.TRUSTCD = inv.TRUSTCD and inv.TRUSTCD=prop.trustcd";
						
					}
					else if(mode.equals("psuprimary"))
					{
						query="select replace(reg.security_name,'&','and') securityname, nvl(reg.SETTLEMENT_AMOUNT,0) purchaseprice, to_char( reg.invest_date, 'dd/Mon/YYYY')  investDate,cat.categorycd categorycode " +
						"from invest_sec_category  cat,investment_register reg, invest_proposal prop,invest_trusttype trt where cat.categorycd = reg.categorycd " +
						"and prop.ref_no =  '"+refNo+"' and reg.invest_date between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.trusttype = reg.trusttype  ";
						query+=" union ";
						query+="select cat.categorycd securityname, inv.INV_AMOUNT purchaseprice,to_char(inv.AS_ONDATE,'dd/Mon/yyyy')investDate,cat.categorycd categorycode from investment_made inv,invest_sec_category  cat,invest_proposal prop,invest_trusttype trt where cat.CATEGORYID = inv.CATEGORYID and prop.ref_no ='"+refNo+"' and inv.AS_ONDATE between (case when PROPOSALDATE >= '01/Apr/'  || to_char(PROPOSALDATE, 'YYYY') then '01/Apr/' || to_char(PROPOSALDATE, 'YYYY')  else '01/Apr/' || to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) - 1) end) and  (case when PROPOSALDATE <= '01/Apr/' || to_char(PROPOSALDATE, 'YYYY') then '31/Mar/'  || to_char(PROPOSALDATE, 'YYYY') else '31/Mar/' || to_char(to_number(to_char(  PROPOSALDATE, 'YYYY')) + 1) end) and trt.TRUSTCD = inv.TRUSTCD and inv.TRUSTCD=prop.trustcd";
					}

					log.info("the quey is..."+query);
							
					
					rs = DBUtility.getRecordSet(query,st);
					String[] secDetails = null;
					
					while(rs.next()){	
						secDetails = new String[4];
						secDetails[0] = rs.getString("securityname");
						secDetails[1] = rs.getString("purchaseprice");
						secDetails[2] = rs.getString("investDate");
						secDetails[3] = rs.getString("categorycode");
						
						/*secDetails[0] = rs.getString("CATEGORYCD");
						secDetails[1] = rs.getString("INV_AMOUNT");
						secDetails[2] = rs.getString("AS_ONDATE");*/
						securities.add(secDetails);
					}
					
					rs.close();
					
					query = " select amount,Financialyear from Invest_Fundaccured acc,INVEST_PROPOSAL prop,invest_trusttype trt WHERE prop.ref_no " +
							" ='"+refNo+"' and Financialyear = (case when PROPOSALDATE >= '01/Apr/' || to_char " +
							" (PROPOSALDATE, 'YYYY') then to_char(PROPOSALDATE, 'YYYY') ||'-'|| to_char(to_number( " +
							" to_char(PROPOSALDATE, 'YY')) + 1) else to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) " +
							" - 1)||'-'|| to_char(PROPOSALDATE, 'YY') end) and trt.trusttype = acc.trusttype and prop.trustcd = trt.trustcd"; 
					rs = DBUtility.getRecordSet(query,st);
					if(rs.next()){	
						fundAccured = rs.getString("amount");
						finYear = rs.getString("Financialyear");
					}else {
						fundAccured = "0";
						SQLHelper sql = new SQLHelper();
						finYear = sql.getDescription("INVEST_PROPOSAL","(case when PROPOSALDATE >= '01/Apr/' || to_char " +
							" (PROPOSALDATE, 'YYYY') then to_char(PROPOSALDATE, 'YYYY') ||'-'|| to_char(to_number( " +
							" to_char(PROPOSALDATE, 'YY')) + 1) else to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) " +
							" - 1)||'-'|| to_char(PROPOSALDATE, 'YY') end) Financialyear","ref_no",refNo,DBUtility.getConnection());
					}
					
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
				
		} catch (ServiceNotAvailableException snex) {
			throw new EPISException(snex);
		} catch (SQLException sqlex) {
			throw new EPISException(sqlex);
		} catch (Exception e) {			
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}	
		try{
			DecimalFormat df = new DecimalFormat("####.##");
			Set s = dates.keySet();
			Iterator iter = s.iterator();
			String[] investDet = null;
			while(iter.hasNext()){
				String key = (String)iter.next();
				String from = key.substring(0,key.indexOf("|"));
				String to = key.substring(key.indexOf("|")+1);
				List percent = (ArrayList)dates.get(key);
				int detLength = invDetails.length;
				investDet = new String[detLength];	
				for(int i=0;i<detLength;i++){
					investDet[i] = "";					
				}
				details.add(investDet);
				investDet = new String[detLength];
				investDet[0] = "";
				investDet[1] = "Amount invested";
				for(int i=2;i<detLength;i++){
					for(int j=0;j<percent.size();j++){
						String[] catDet = (String[])percent.get(j);
						if(catDet[0].equals(invDetails[i])){
							investDet[i] = catDet[1]+" %";
						}
					}
					if(investDet[i]==null)
						investDet[i]="0";
				}
				details.add(investDet);
				String[] fundDet = new String[detLength];
				fundDet[0] = "Fund Accured";
				fundDet[1] = df.format(Double.parseDouble(fundAccured));
				for(int i=2;i<detLength;i++){
					fundDet[i] = df.format(Double.parseDouble(fundAccured)*Double.parseDouble(investDet[i].replaceAll("%",""))/100);
				}
				details.add(fundDet);
				String[] security = null;
				double[] total = new double[detLength];
				double totalInvested = 0.00;
				
				for(int i=0;i<securities.size();i++){
					security = new String[detLength];
					String[] sec = (String[])securities.get(i);
					if(DateValidation.isDateBetween(from,to,sec[2])){
						security[0] = sec[0];
						security[1] = "";
						for(int j=2;j<detLength;j++){
							if(sec[3].equals(invDetails[j])){
								security[j] = sec[1];
								securities.remove(i);
								i--;
								/*Below code commented on 28-01-2011. Security details should not be displayed in ivestment praposal report*/
								//details.add(security);
								total[j] += Double.parseDouble(sec[1]);
								totalInvested += Double.parseDouble(sec[1]);
							}else{
								security[j] = "";
							}
						}
					}					
				}
				String[] investMade = new String[detLength];
				investMade[0] = "Investment made";
				investMade[1] = df.format(totalInvested);
				for(int j=2;j<detLength;j++){
					investMade[j] = df.format(total[j]);
				}
				details.add(investMade);
				investMade = new String[detLength];
				investMade[0] = "ratio in terms of % age of invest. Made";
				investMade[1] = "";
				for(int j=2;j<detLength;j++){
					investMade[j] = totalInvested!=0?(df.format((total[j]/totalInvested)*100)+" %"):"0 %";					
				}
				details.add(investMade);
				investMade = new String[detLength];
				investMade[0] = "Excess / Short fall";
				
				double exceeOrShortFall  = 0.00;
				for(int j=2;j<detLength;j++){
					investMade[j] = df.format(Double.parseDouble(fundDet[j])-total[j]);	
					exceeOrShortFall += Double.parseDouble(fundDet[j])-total[j];
				}
				investMade[1] = df.format(exceeOrShortFall);
				details.add(investMade);
				
				fundAccured =investMade[1];
			}
		}catch (Exception e) {
			throw new EPISException();
		}
		return details;
	}
	
	private String  finYear ;
	public String getFinYear(){
		return finYear;
	}
	
}
