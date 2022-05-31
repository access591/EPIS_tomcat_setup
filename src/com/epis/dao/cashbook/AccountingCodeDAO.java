package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.epis.bean.cashbook.AccountingCodeInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;
import com.epis.utilities.DBUtility;

public class AccountingCodeDAO {

	Log log = new Log(AccountingCodeDAO.class);

	private AccountingCodeDAO() {

	}

	private static final AccountingCodeDAO dao = new AccountingCodeDAO();

	public static AccountingCodeDAO getInstance() {
		return dao;
	}

	public void add(AccountingCodeInfo info) throws  Exception {
		log.info("AccountingCodeDAO : addAccountRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, info.getAccountHead());
			pst.setString(2, info.getParticular());
			pst.setString(3, info.getType());
			pst.setString(4, info.getLoginUserId());
			pst.setString(5, info.getDate());
			pst.setString(6, info.getLoginUnitCode());
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(insertDtQuery);
			Map  openBal = info.getOpenBalances();
			AccountingCodeInfo accInfo = null;
			Iterator iter = openBal.keySet().iterator();
			while(iter.hasNext()){				
				accInfo = (AccountingCodeInfo)openBal.get(iter.next());
				pst.setString(1, info.getAccountHead());
				pst.setString(2, accInfo.getTrustType());
				pst.setString(3, accInfo.getAmount());
				pst.setString(4, accInfo.getAmountType());
				pst.setString(5, accInfo.getLoginUserId());
				pst.setString(6, accInfo.getLoginUnitCode());
				pst.executeUpdate();
			}
		con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}		
		UserTracking.write(info.getLoginUserId(), info.getParticular() + "-"
				+ info.getType(), "S", "CB", info.getAccountHead(),
				"Accounting Code");
		log.info("AccountingCodeDAO : addAccountRecord : leaving method");
	}

	
	public List search(AccountingCodeInfo info)
			throws EPISException {
		log.info("AccountingCodeDAO : search : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List acc = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(selectQuery);
			pst.setString(1,StringUtility.checknull(info.getAccountHead())+ "%");
			pst.setString(2,StringUtility.checknull(info.getParticular())+ "%");
			pst.setString(3,StringUtility.checknull(info.getType())+"%");
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new AccountingCodeInfo();
				info.setAccountHead(rs.getString("ACCOUNTHEAD"));
				info.setParticular(rs.getString("PARTICULAR"));
				info.setType(rs.getString("TYPE"));
				info.setDate(rs.getString("OpenDATE"));
				acc.add(info);
			}			
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}		
		log.info("AccountingCodeDAO : search : leaving method");
		return acc;
	}

	public boolean exists(AccountingCodeInfo info) throws EPISException {
		log.info("AccountingCodeDAO : exists : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(countQuery);
			pst.setString(1, info.getAccountHead());
			rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("AccountingCodeDAO : exists : leaving method");
		return exists;
	}

	public List getBankAccountingCodeList() throws EPISException {
		log.info("AccountingCodeDAO : getBankAccountingCodeList : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List acc = new ArrayList();
		AccountingCodeInfo info = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(bankAccountCodeQuery);
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new AccountingCodeInfo();
				info.setAccountHead(rs.getString("ACCOUNTHEAD"));
				info.setParticular(rs.getString("PARTICULAR"));
				acc.add(info);
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log
				.info("AccountingCodeDAO : getBankAccountingCodeList : leaving method");
		return acc;
	}

	public void update(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeDAO : updateAccountRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, info.getParticular());
			pst.setString(2, info.getType());
			pst.setString(3, info.getDate());
			pst.setString(4, info.getLoginUserId());
			pst.setString(5, info.getAccountHead());
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, info.getAccountHead());
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(insertDtQuery);
			Map  openBal = info.getOpenBalances();
			AccountingCodeInfo accInfo = null;
			Iterator iter = openBal.keySet().iterator();
			while(iter.hasNext()){				
				accInfo = (AccountingCodeInfo)openBal.get(iter.next());
				pst.setString(1, info.getAccountHead());
				pst.setString(2, accInfo.getTrustType());
				pst.setString(3, accInfo.getAmount());
				pst.setString(4, accInfo.getAmountType());
				pst.setString(5, accInfo.getLoginUserId());
				pst.setString(6, accInfo.getLoginUnitCode());
				pst.executeUpdate();
				
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}		
		UserTracking.write(info.getLoginUserId(), info.getParticular() + "-"
				+ info.getType(), "U", "CB", info.getAccountHead(),
				"Accounting Code");
		log.info("AccountingCodeDAO : updateAccountRecord : leaving method");

	}

	public void delete(String[] types) throws EPISException {
		log.info("AccountingCodeTypeDAO : delete : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			int len = types.length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append(types[i]).append("|");
			}
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, sb.toString());
			pst.executeUpdate();
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, sb.toString());
			pst.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new EPISException(e1);
			}
			throw new EPISException(e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new EPISException(e1);
			}
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}		
		log.info("AccountingCodeTypeDAO : deleteRecord : leaving method");
	}
	
	public AccountingCodeInfo edit(AccountingCodeInfo info) throws EPISException {
		log.info("AccountingCodeTypeDAO : edit : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(editQuery);
			pst.setString(1,info.getAccountHead());
			rs = pst.executeQuery();			
			HashMap openBalances = new HashMap();
			boolean bool = true;
			while(rs.next()){
				if(bool){
					info.setDate(rs.getString("OpenDATE"));
					info.setParticular(rs.getString("PARTICULAR"));
					info.setType(rs.getString("code"));					
					bool=true;
				}
				AccountingCodeInfo openBals = new AccountingCodeInfo();
				openBals.setTrustType(rs.getString("trusttype"));
				openBals.setAmount(rs.getString("amount"));
				openBals.setAmountType(rs.getString("amounttype"));
				openBalances.put(openBals.getTrustType(),openBals);
			}
			info.setOpenBalances(openBalances);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new EPISException(e1);
			}
			throw new EPISException(e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new EPISException(e1);
			}
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		log.info("AccountingCodeTypeDAO : edit : leaving method");
		return info;
	}
	
	public List getReport(AccountingCodeInfo info) throws EPISException {
		log.info("AccountingCodeDAO : search : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List acc = new ArrayList();
		StringBuffer query = new StringBuffer("select info.accounthead ACCOUNTHEAD,info.particular PARTICULAR ");
		query.append(" ,typeinfo.accountcodetype TYPE,to_char(info.opendate,'dd/Mon/YYYY') OpenDATE");
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(createTrustQuery);
			rs = pst.executeQuery();
			if(rs.next()) {
				query.append(",").append(rs.getString(1));
			}			
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		query.append(" from cb_accountcode_info info, cb_accountcodetype_info typeinfo where typeinfo.code ");
		query.append(" = info.type and  upper(trim(ACCOUNTHEAD)) like upper(trim(?)) and upper(trim(PARTICULAR))");
		query.append(" like upper(trim(?)) and Nvl(upper(trim(TYPE)),' ') like upper(trim(?))  order by ACCOUNTHEAD");
		try{
			pst = con.prepareStatement(query.toString());
			pst.setString(1,StringUtility.checknull(info.getAccountHead())+ "%");
			pst.setString(2,StringUtility.checknull(info.getParticular())+ "%");
			pst.setString(3,StringUtility.checknull(info.getType())+"%");
			rs = pst.executeQuery();
			Map openBalances = null;
			while (rs.next()) {
				info = new AccountingCodeInfo();
				info.setAccountHead(rs.getString("ACCOUNTHEAD"));
				info.setParticular(rs.getString("PARTICULAR"));
				info.setType(rs.getString("TYPE"));
				info.setDate(rs.getString("OpenDATE"));
				int i=4;
				openBalances = new HashMap();
				while(i<rs.getMetaData().getColumnCount()){
					AccountingCodeInfo openBals = new AccountingCodeInfo();
					openBals.setTrustType(rs.getString(++i));
					openBals.setAmount(rs.getString(++i));
					openBalances.put(openBals.getTrustType(),openBals);
				}
				info.setOpenBalances(openBalances);
				acc.add(info);
			}			
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}		
		log.info("AccountingCodeDAO : search : leaving method");
		return acc;
	}

	String insertQuery = "insert into CB_ACCOUNTCODE_INFO(ACCOUNTHEAD,PARTICULAR,TYPE,ENTEREDBY,OpenDATE,UNITCODE) values(?,?,?,?,?,?)";

	String insertDtQuery = "insert into CB_ACCOUNTCODE_DETAILS(ACCOUNTHEAD,TRUSTTYPE,AMOUNT,AMOUNTTYPE,ENTEREDBY,UNITCODE) values(?,?,?,?,?,?)";

	String selectQuery = "select ACCOUNTHEAD,PARTICULAR,typeinfo.accountcodetype TYPE,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),' ') OpenDATE from CB_ACCOUNTCODE_INFO info,cb_accountcodetype_info typeinfo where typeinfo.code(+) = info.type and upper(trim(ACCOUNTHEAD)) like upper(trim(?)) and upper(trim(PARTICULAR)) like upper(trim(?)) and Nvl(upper(trim(TYPE)),' ') like upper(trim(?))  order by ACCOUNTHEAD";

	String editQuery = "select info.ACCOUNTHEAD,PARTICULAR,type.code ,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),'') OpenDATE,det.trusttype,to_number(det.amount,'999999999999999.99') amount,det.amounttype from CB_ACCOUNTCODE_INFO info,CB_ACCOUNTCODE_DETAILS det,CB_accountcodetype_info type  where upper(trim(info.ACCOUNTHEAD)) = upper(trim(?))  and det.accounthead(+) = info.accounthead and info.type = type.code(+)";

	String updateQuery = "Update CB_ACCOUNTCODE_INFO set PARTICULAR=?,TYPE=?,OpenDATE=?,EDITEDBY=?,EDITEDDT=sysdate Where upper(trim(ACCOUNTHEAD)) = upper(trim(?))";

	String deleteQuery = "Delete from CB_ACCOUNTCODE_INFO  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";

	String deleteDtQuery = "Delete from CB_ACCOUNTCODE_DETAILS  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";

	String selectDtQuery = "select ACCOUNTHEAD,trusttype,Nvl(amount,0) amount,NVL(AMOUNTTYPE,'DR') AMOUNTTYPE from CB_ACCOUNTCODE_DETAILS  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";

	String remQuery = "select ACCOUNTHEAD,PARTICULAR,TYPE,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),' ') OpenDATE from CB_ACCOUNTCODE_INFO where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0 and upper(trim(PARTICULAR)) like upper(trim(?)) and Nvl(upper(trim(TYPE)),' ') like upper(trim(?))   order by ACCOUNTHEAD";

	String createTrustQuery = "select join(cursor(select ''''||trusttype||''',(select amount||'' ''||amounTtype from  cb_accountcode_details where trusttype='''||trusttype||''' '||' and accounthead=info.accounthead)' from invest_trusttype)) from dual";
	
	String bankAccountCodeQuery = "select ACCOUNTHEAD,PARTICULAR from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD in (select distinct ACCOUNTCODE from CB_bank_info )";

	String countQuery = "select count(*) from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD = ?";

	

	

}
