package com.epis.dao.cashbookDummy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.epis.bean.cashbookDummy.AccountingCodeInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.UserTracking;
 

public class AccountingCodeDAO {

	Log log = new Log(AccountingCodeDAO.class);

	public void addAccountRecord(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeDAO : addAccountRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, info.getAccountHead());
			pst.setString(2, info.getParticular());
			pst.setString(3, info.getType());
			pst.setString(4, info.getEnteredBy());
			pst.setString(5, info.getDate());
			pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		try {
			pst = con.prepareStatement(insertDtQuery);
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "AAI EPF");
			pst.setString(3, info.getAmount());
			pst.setString(4, info.getAmountType());
			pst.executeUpdate();
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "IAAI ECPF");
			pst.setString(3, info.getAmountI());
			pst.setString(4, info.getAmountTypeI());
			pst.executeUpdate();
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "NAA ECPF");
			pst.setString(3, info.getAmountN());
			pst.setString(4, info.getAmountTypeN());
			pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		UserTracking.write(info.getEnteredBy(), info.getParticular() + "-"
				+ info.getType() , "S", "CB", info.getAccountHead(),"Accounting Code");
		log.info("AccountingCodeDAO : addAccountRecord : leaving method");
	}

	public List getAccountList(AccountingCodeInfo info, String type)
			throws Exception {
		log.info("AccountingCodeDAO : getAccountList : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		HashMap accInfo = new HashMap();
		List acc = new ArrayList();
		try {
			con = DBUtility.getConnection();
			if ("rem".equals(type)) {
				pst = con.prepareStatement(remQuery);
				pst.setString(1, (info.getAccountHead() != null ? info
						.getAccountHead() : ""));
			} else if ("edit".equals(type)) {
				pst = con.prepareStatement(editQuery);
				pst.setString(1, (info.getAccountHead() != null ? info
						.getAccountHead() : ""));
			} else {
				pst = con.prepareStatement(selectQuery);
				pst.setString(1, (info.getAccountHead() != null ? info
						.getAccountHead() : "")
						+ "%");
			}
			pst.setString(2, (info.getParticular() != null ? info
					.getParticular() : "")
					+ "%");
			pst.setString(3, (info.getType() != null ? info.getType() : "")
					+ "%");
			rs = pst.executeQuery();
			StringBuffer codes = new StringBuffer("|");
			while (rs.next()) {
				info = new AccountingCodeInfo();
				info.setAccountHead(rs.getString("ACCOUNTHEAD"));
				info.setParticular(rs.getString("PARTICULAR"));
				info.setType(rs.getString("TYPE"));
				info.setDate(rs.getString("OpenDATE"));
				accInfo.put(rs.getString("ACCOUNTHEAD"),info);
				codes.append(rs.getString("ACCOUNTHEAD")).append("|");
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		try {
			pst = con.prepareStatement(selectDtQuery);
			pst.setString(1, info.getAccountHead());			
			rs = pst.executeQuery();
			while (rs.next()) {
				info = (AccountingCodeInfo) accInfo.get(rs.getString("ACCOUNTHEAD"));
				if(info==null)
				if("A".equals(rs.getString("TRUSTTYPE"))){
					info.setAmount(rs.getString("AMOUNT"));
					info.setAmountType(rs.getString("AMOUNTTYPE"));
				}else if("I".equals(rs.getString("TRUSTTYPE"))){
					info.setAmountI(rs.getString("AMOUNT"));
					info.setAmountTypeI(rs.getString("AMOUNTTYPE"));
				}else if("N".equals(rs.getString("TRUSTTYPE"))){
					info.setAmountN(rs.getString("AMOUNT"));
					info.setAmountTypeN(rs.getString("AMOUNTTYPE"));
				}
				accInfo.put(rs.getString("ACCOUNTHEAD"),info);
			}
			Set set = accInfo.keySet();
			String[] strkeys = (String[])set.toArray(new String[set.size()]);
			Arrays.sort(strkeys);
			int len = strkeys.length;
			for(int i=0;i<len;i++){
				acc.add(accInfo.get(strkeys[i]));
				//i++;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}		 
		log.info("AccountingCodeDAO : getAccountList : leaving method");
		return acc;
	}

	public boolean exists(AccountingCodeInfo info) throws Exception {
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
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
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
	
	public List getBankAccountingCodeList() throws Exception {
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
			while(rs.next()){
				info = new AccountingCodeInfo();
				info.setAccountHead(rs.getString("ACCOUNTHEAD"));
				info.setParticular(rs.getString("PARTICULAR"));
				acc.add(info);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("AccountingCodeDAO : getBankAccountingCodeList : leaving method");
		return acc;
	}

	public void updateAccountRecord(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeDAO : updateAccountRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, info.getParticular());
			pst.setString(2, info.getType());
			pst.setString(3, info.getDate());
			pst.setString(4, info.getAccountHead());
			pst.executeUpdate();
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, info.getAccountHead());
			pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		try {
			pst = con.prepareStatement(insertDtQuery);
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "A");
			pst.setString(3, info.getAmount());
			pst.setString(4, info.getAmountType());
			pst.executeUpdate();
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "I");
			pst.setString(3, info.getAmountI());
			pst.setString(4, info.getAmountTypeI());
			pst.executeUpdate();
			pst.setString(1, info.getAccountHead());
			pst.setString(2, "N");
			pst.setString(3, info.getAmountN());
			pst.setString(4, info.getAmountTypeN());
			pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		UserTracking.write(info.getEnteredBy(), info.getParticular() + "-"
				+ info.getType() , "U", "CB", info.getAccountHead(),"Accounting Code");
		log.info("AccountingCodeDAO : updateAccountRecord : leaving method");

	}

	public void deleteAccountRecord(String codes) throws Exception {
		log.info("AccountingCodeDAO : deleteAccountRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, codes);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("AccountingCodeDAO : deleteAccountRecord : leaving method");

	}

	String insertQuery = "insert into CB_ACCOUNTCODE_INFO(ACCOUNTHEAD,PARTICULAR,TYPE,ENTEREDBY,OpenDATE) values(?,?,?,?,?)";
	
	String insertDtQuery = "insert into CB_ACCOUNTCODE_DETAILS(ACCOUNTHEAD,TRUSTTYPE,AMOUNT,AMOUNTTYPE) values(?,?,?,?)";

	String selectQuery = "select ACCOUNTHEAD,PARTICULAR,TYPE,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),' ') OpenDATE from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD like ? and upper(PARTICULAR) like upper(?) and Nvl(TYPE,' ') like ?  order by ACCOUNTHEAD";

	String editQuery = "select ACCOUNTHEAD,PARTICULAR,nvl(TYPE,' ')TYPE,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),' ') OpenDATE from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD = ? and upper(PARTICULAR) like upper(?) and Nvl(TYPE,' ') like ?  ";

	String updateQuery = "Update CB_ACCOUNTCODE_INFO set PARTICULAR=?,TYPE=?,OpenDATE=? Where ACCOUNTHEAD=?";

	String deleteQuery = "Delete from CB_ACCOUNTCODE_INFO  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";
	
	String deleteDtQuery = "Delete from CB_ACCOUNTCODE_DETAILS  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";
	
	String selectDtQuery = "select ACCOUNTHEAD,trusttype,Nvl(amount,0) amount,NVL(AMOUNTTYPE,'DR') AMOUNTTYPE from CB_ACCOUNTCODE_DETAILS  Where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0";

	String remQuery = "select ACCOUNTHEAD,PARTICULAR,DECODE(TYPE,'A','Asset','L','Liability','I','Income','E','Expenditure','U','Inter Unit','') TYPE,Nvl(to_char(OpenDATE,'dd/Mon/YYYY'),' ') OpenDATE from CB_ACCOUNTCODE_INFO where INSTR(upper(?),upper(ACCOUNTHEAD)) > 0 and upper(PARTICULAR) like upper(?) and Nvl(TYPE,' ') like ?   order by ACCOUNTHEAD";
	
	String bankAccountCodeQuery = "select ACCOUNTHEAD,PARTICULAR from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD in (select distinct ACCOUNTCODE from CB_bank_info )";

	String countQuery = "select count(*) from CB_ACCOUNTCODE_INFO where ACCOUNTHEAD = ?";

}
