package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.cashbook.FundsTransferInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class FundsTransferDAO {
	Log log = new Log(FundsTransferDAO.class);

	private FundsTransferDAO() {

	}

	private static final FundsTransferDAO dao = new FundsTransferDAO();

	public static FundsTransferDAO getInstance() {
		return dao;
	}

	public List search(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : search : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List funds = new ArrayList();
		try {
			con = DBUtility.getConnection();
			if(info.getPreparationDate() != null && !"".equals(info.getPreparationDate())){
				pst = con.prepareStatement(getQuery("selectQuery")+
						" and  PREPERATION_DT = to_date('"+info.getPreparationDate()+"','dd/mm/yy')");				
			}
			
			else if(info.getApprovedDt() != null && !"".equals(info.getApprovedDt())){
				pst = con.prepareStatement(getQuery("selectQuery")+
						" and  APPROVED_DT = to_date('"+info.getApprovedDt()+"','dd/mm/yy')");				
			}
			else {
				pst = con.prepareStatement(getQuery("selectQuery"));				
			}
			pst.setString(1,StringUtility.checknull(info.getFromAccountNo())+"%");
			pst.setString(2,StringUtility.checknull(info.getToAccountNo())+"%");
			pst.setString(3,StringUtility.checknull(info.getApproval())+"%");
			pst.setString(4,StringUtility.checknull(info.getFinalApproval())+"%");
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new FundsTransferInfo();
				info.setFromAccountNo(rs.getString("from_account"));
				info.setToAccountNo(rs.getString("to_account"));
				info.setKeyno(rs.getString("keyno"));
				info.setPreparationDate(rs.getString("preperation_dt")); 
				info.setApprovedDt(StringUtility.checknull(rs.getString("APPROVED_DT")));
				info.setApprovedBy(StringUtility.checknull(rs.getString("APPROVED_BY")));
				info.setAmount(rs.getString("amount"));
				info.setApproval(rs.getString("APPROVAL"));                       
				info.setFinalApproval(rs.getString("FINAPPROVAL"));
				info.setFinalApprovedBy(StringUtility.checknull(rs.getString("FIN_APPPROVED_BY")));
				
				funds.add(info);
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : search : leaving method");
		return funds;
	}
	
	public void save(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : save : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String keyno = null;
		try {
			con = DBUtility.getConnection();
			com.epis.utilities.DateValidation datevalidation=new com.epis.utilities.DateValidation();
			String year=datevalidation.getLetterYear(info.getPreparationDate());
			pst = con.prepareStatement(getQuery("generateKeynoQuery"));
			pst.setString(1,StringUtility.checknull(info.getPreparationDate()));
			pst.setString(2,StringUtility.checknull(info.getPreparationDate()));
			rs = pst.executeQuery();
			if(rs.next()){
				keyno = rs.getString(1);
			}
			pst.close();
			if(keyno != null){
				pst = con.prepareStatement(getQuery("insertQuery"));			
				pst.setString(1,StringUtility.checknull(keyno));
				pst.setString(2,StringUtility.checknull(info.getFromAccountNo()));
				pst.setString(3,StringUtility.checknull(info.getToAccountNo()));
				pst.setString(4,StringUtility.checknull(info.getPreparationDate()));
				pst.setString(5,StringUtility.checknull(info.getAmount()));
				pst.setString(6,StringUtility.checknull(info.getLoginUserId()));
				pst.setString(7,StringUtility.checknull(info.getLoginUnitCode()));
				pst.setString(8,StringUtility.checknull(year));
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : save : leaving method");
	}
	
	public FundsTransferInfo getRecord(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : search : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQuery("selectRecQuery"));				
			pst.setString(1,StringUtility.checknull(info.getKeyno()));
			rs = pst.executeQuery();
			while (rs.next()) {
				info.setFromAccountNo(rs.getString("from_account"));
				info.setToAccountNo(rs.getString("to_account"));
				info.setKeyno(rs.getString("keyno"));
				info.setPreparationDate(rs.getString("preperation_dt"));                       
				info.setAmount(rs.getString("amount"));
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : search : leaving method");
		return info;
	}
	
	public void update(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : update : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();			
			pst = con.prepareStatement(getQuery("updateQuery"));
			pst.setString(1,StringUtility.checknull(info.getFromAccountNo()));
			pst.setString(2,StringUtility.checknull(info.getToAccountNo()));
			pst.setString(3,StringUtility.checknull(info.getPreparationDate()));
			pst.setString(4,StringUtility.checknull(info.getAmount()));
			pst.setString(5,StringUtility.checknull(info.getLoginUserId()));
			pst.setString(6,StringUtility.checknull(info.getKeyno()));
			pst.executeUpdate();			
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null,pst,con);
		}
		log.info("FundsTransferDAO : update : leaving method");
	}
	
	public FundsTransferInfo getApprovalRecord(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : getApprovalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = DBUtility.getConnection();
			
			
			pst = con.prepareStatement(getQuery("approvalQuery"));				
			pst.setString(1,StringUtility.checknull(info.getKeyno()));
			rs = pst.executeQuery();
			while (rs.next()) {
				info.setLetterNo(StringUtility.checknull(rs.getString("LETTER_NO")));
				info.setAddress(rs.getString("address").replaceAll(",",",\n").trim());
				info.setAccountType(rs.getString("accType"));
				info.setFromAccountNo(rs.getString("fromAccountNo"));
				info.setAmount(rs.getString("amount"));                       
				info.setToBank(rs.getString("tobank").toUpperCase());
				info.setFromBank(rs.getString("frombank").toUpperCase());
				info.setToAccountNo(rs.getString("toAccountNo"));
				info.setAccountName(rs.getString("accName"));
				info.setIfscCode(rs.getString("ifsccode")); 
				info.setPreparationDate(rs.getString("preperation_dt"));
				info.setApprovedBy(StringUtility.checknull(rs.getString("APPROVED_BY")));
				info.setApproval(StringUtility.checknull(rs.getString("APPROVAL")));
				info.setApprovedDt(StringUtility.checknull(rs.getString("APPROVED_DT")));
				info.setFinalApprovedBy(StringUtility.checknull(rs.getString("FIN_APPPROVED_BY")));
				info.setFinalApproval(StringUtility.checknull(rs.getString("FINAPPROVAL")));
				info.setFinalApprovedDt(StringUtility.checknull(rs.getString("FIN_APPROVED_DT")));
			
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : getApprovalRecord : leaving method");
		return info;		
	}
	
	public FundsTransferInfo SaveApproval(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : SaveApprovalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet letterrs=null;
		String letterno=null;
		int i=0;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			
			pst = con.prepareStatement(getQuery("generateletternoQuery"));
			pst.setString(1,StringUtility.checknull(info.getKeyno()));
			
			letterrs = pst.executeQuery();
			if(letterrs.next()){
				letterno = letterrs.getString(1);
			}
			
			pst = con.prepareStatement(getQuery("updateapprovalQuery"));	
			pst.setString(1,StringUtility.checknull(info.getLoginUserId()));			
			pst.setString(2,StringUtility.checknull(letterno));
			pst.setString(3,StringUtility.checknull(info.getKeyno()));
			i=pst.executeUpdate();
			con.commit();
			
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : SaveApprovalRecord : leaving method");
		if(i>0)
			info=getApprovalRecord(info);		
		return info;	
	}
	
	public FundsTransferInfo SaveFinalApproval(FundsTransferInfo info) throws EPISException {
		log.info("FundsTransferDAO : SaveFinalApproval : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int i=0;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQuery("updatefinapprovalQuery"));	
			pst.setString(1,StringUtility.checknull(info.getLoginUserId()));			
			pst.setString(2,StringUtility.checknull(info.getKeyno()));
			i=pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("FundsTransferDAO : SaveFinalApproval : leaving method");
		if(i>0)
			info=getApprovalRecord(info);		
		return info;	
	}
	
	public void deleteApprovals(String keynos,String loginid) throws Exception {
		log.info("FundsTransferDAO : update : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(getQuery("deleteQuery"));
			pst.setString(1, loginid);
			pst.setString(2, keynos);			
			pst.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
	}
	
	private String getQuery(String query){
		Map queries = new HashMap(); 
		
		queries.put("selectQuery","select keyno,from_account,to_account,to_char(preperation_dt,'dd/Mon/YYYY') " +
				"preperation_dt,to_char(APPROVED_DT,'dd/Mon/YYYY') APPROVED_DT,APPROVED_BY,amount,Nvl(APPROVAL,'N') APPROVAL,Nvl(FINAPPROVAL,'N') FINAPPROVAL,FIN_APPPROVED_BY from " +
				"cb_fundstransfer where FROM_ACCOUNT like ? and TO_ACCOUNT like ? and  Nvl(APPROVAL,'N') like ?" +
				" and  Nvl(FINAPPROVAL,'N') like ? and DELETED_FLAG='N'");
		
		queries.put("selectRecQuery","select keyno,from_account,to_account,to_char(preperation_dt,'dd/Mon/YYYY') " +
				"preperation_dt,amount from cb_fundstransfer where keyno = ?  ");
		
		queries.put("insertQuery","insert into cb_fundstransfer(keyno,from_account,to_account,preperation_dt," +
				"amount,created_by,unitcode,FYEAR) values (?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
		
		queries.put("updateQuery","update cb_fundstransfer set from_account=?,to_account=?,preperation_dt = " +
				"to_date(?,'dd/mm/yyyy'), amount = ?,EDITED_BY=?,EDITED_DT=sysdate where keyno=?");
		
		queries.put("generateKeynoQuery","select to_char(to_date(?,'dd/mm/yyyy'),'ddmmyy')||Lpad(Nvl(max(" +
				"substr(KEYNO, 7)), 0) + 1, 6, '0') keyno from cb_fundstransfer where to_char(" +
				"to_date(?,'dd/mm/yyyy'),'ddmmyy') = substr(KEYNO, 0, 6)");
		
		queries.put("approvalQuery","select LETTER_NO,frombank.address address,decode(frombank.accounttype, 'S', " +
				" 'Savings', 'C', 'Current') accType,frombank.accountno fromAccountNo, amount,initcap(tobank.bankname) " +
				" tobank ,Initcap(frombank.bankname) frombank,tobank.accountno toAccountNo,tobank.accountName accName,"+
				" APPROVED_BY,to_char(APPROVED_DT,'dd/Mon/YYYY')APPROVED_DT ,APPROVAL, "+
				" tobank.ifsccode ifsccode,to_char(preperation_dt,'dd/Mon/YYYY') preperation_dt,to_char(FIN_APPROVED_DT,'dd/Mon/YYYY') FIN_APPROVED_DT,FINAPPROVAL,FIN_APPPROVED_BY from cb_bank_info " +
				" frombank, cb_fundstransfer ft, cb_bank_info tobank where frombank.accountno = ft.from_account " +
				" and tobank.accountno = ft.to_account and ft.keyno = ?");
		
		queries.put("updateapprovalQuery","update cb_fundstransfer set APPROVED_BY=?,APPROVED_DT=sysdate,APPROVAL='Y',LETTER_NO=? where KEYNO=?");
		
		queries.put("updatefinapprovalQuery","update cb_fundstransfer set FIN_APPPROVED_BY=?,FIN_APPROVED_DT=sysdate,FINAPPROVAL='Y' where KEYNO=?");
		
		queries.put("deleteQuery","update cb_fundstransfer set DELETED_BY=?,DELETED_DT=sysdate,DELETED_FLAG='Y' where INSTR(?,KEYNO)>0");
		queries.put("generateletternoQuery","select  'AAI/EPFTrust/CHQ/'||fund.fyear||'/'|| LPAD((SELECT MAX(TO_NUMBER(NVL(SUBSTR(LETTER_NO,INSTR(LETTER_NO, '/', -1) + 1),0))) + 1 from  CB_FUNDSTRANSFER vfund where vfund.fyear=fund.fyear),3,0 ) from CB_FUNDSTRANSFER fund where fund.keyno=?");
				
		return queries.get(query).toString();
	}
	
}
