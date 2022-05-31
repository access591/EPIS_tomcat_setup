package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epis.bean.cashbook.BankOpenBalInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;


public class BankOpenBalDAO {
	
	Log log = new Log(BankOpenBalDAO.class);

	private BankOpenBalDAO() {

	}

	private static final BankOpenBalDAO dao = new BankOpenBalDAO();

	public static BankOpenBalDAO getInstance() {
		return dao;
	}
	
	public boolean exists(BankOpenBalInfo info) throws Exception {
		log.info("BankMasterDAO : exists : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("existQuery"));
			pst.setString(1, info.getAccountNo());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) >= 1) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		log.info("BankMasterDAO : exists : leaving method");
		return exists;
	}

	
	public void add(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalDAO : addOpenBalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("insertQuery"));
			pst.setString(1, info.getAccountNo());
			pst.setString(2, info.getOpendate());
			pst.setDouble(3, info.getAmount());
			pst.setString(4, info.getDetails());
			pst.setString(5, info.getLoginUserId());
			pst.setString(6, info.getAmountType());
			pst.setString(7, info.getLoginUnitCode());
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
		UserTracking.write(info.getLoginUserId(), info.getAmount() + "-"
				+ info.getDetails() + info.getOpendate(), "S", "CB", info
				.getAccountNo(),"Bank Opening Balance");
		log.info("BankOpenBalDAO : addOpenBalRecord : Leaving method");
	}
	
	public List getBankList() throws Exception {
		log.info("BankOpenBalDAO : getBankDetails : Entering method");

		ArrayList bankInfo = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("bankQuery"));
			//pst.setString(1, info.getBankName());
			rs = pst.executeQuery();
			while (rs.next()) {
				BankOpenBalInfo info = new BankOpenBalInfo();
				info.setBankName(rs.getString("bankName")+" -- "+rs.getString("accountNo"));	
				info.setAccountNo(rs.getString("accountNo"));		
				bankInfo.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankOpenBalDAO : getBankDetails : leaving method");
		return bankInfo;
	}
	
	public List getAccno(String bankName) throws Exception{
		ArrayList accInfo = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//BankOpenBalInfo info = new BankOpenBalInfo();
		
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("accQuery"));			
			pst.setString(1, StringUtility.checknull(bankName));
			rs = pst.executeQuery();
			while (rs.next()) {				
				accInfo.add(rs.getString("ACCOUNTNO"));			
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankOpenBalDAO : getAccno : leaving method");
		return accInfo;
	}

	public List search(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalDAO : searchRecords : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List dataList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("selectQuery"));
			pst.setString(1, info.getBankName() + "%");
			pst.setString(2, info.getAccountNo() + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new BankOpenBalInfo();
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setBankName(rs.getString("bankName"));
				info.setDetails(rs.getString("DETAILS"));
				info.setOpendate(rs.getString("OPENEDDATE"));
				info.setAmount(rs.getDouble("AMOUNT"));
				info.setAmountType(rs.getString("AMOUNTTYPE"));
				dataList.add(info);
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
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("BankOpenBalDAO : searchRecords : Leaving method");
		return dataList;
	}

	public void delete(String[] types) throws Exception {
		log.info("BankOpenBalDAO : deleteBankOpenBalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("deleteQuery"));
			int len = types.length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append(types[i]).append("|");
			}
			pst.setString(1, sb.toString());
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
		log.info("BankOpenBalDAO : deleteBankOpenBalRecord : Leaving method");
	}

	public BankOpenBalInfo edit(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalDAO : getRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("editQuery"));
			pst.setString(1, info.getAccountNo());			
			rs = pst.executeQuery();
			if (rs.next()) {				
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setBankName(rs.getString("bankName"));
				info.setDetails(rs.getString("DETAILS"));
				info.setOpendate(rs.getString("OPENEDDATE"));
				info.setAmount(rs.getDouble("AMOUNT"));
				info.setAmountType(rs.getString("AMOUNTTYPE"));
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
		log.info("BankOpenBalDAO : getRecord : Leaving method");
		return info;
	}

	public void update(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalDAO : updateOpenBalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(queries("updateQuery"));
			pst.setString(1, info.getOpendate());
			pst.setDouble(2, info.getAmount());
			pst.setString(3, info.getDetails());
			pst.setString(4, info.getAmountType());
			pst.setString(5, info.getLoginUserId());
			pst.setString(6, info.getAccountNo());
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
		UserTracking.write(info.getLoginUserId(), info.getAmount() + "-"
				+ info.getDetails() + info.getOpendate(), "U", "CB", info
				.getAccountNo(),"Bank Opening Balance");
		log.info("BankOpenBalDAO : updateOpenBalRecord : Leaving method");
	}
	
	public static String queries(String query) {
		HashMap queries = new HashMap();

		StringBuffer insertQuery = new StringBuffer(" insert into Cb_BANKOPENINGBAL_INFO(ACCOUNTNO,OPENEDDATE,AMOUNT,DETAILS,ENTEREDBY,AMOUNTTYPE,UNITCODE) values(?,?,?,?,?,?,?)");
		queries.put("insertQuery", insertQuery);
		
		StringBuffer deleteQuery = new StringBuffer(" delete from Cb_BANKOPENINGBAL_INFO where  instr(upper(?),upper(ACCOUNTNO))>0 ");
		queries.put("deleteQuery", deleteQuery);
		
		StringBuffer selectQuery = new StringBuffer(" select info.ACCOUNTNO,bankName,Nvl(to_char(OPENEDDATE,'dd/Mon/yyyy'),' ')OPENEDDATE,AMOUNT,Nvl(DETAILS,' ')DETAILS,decode(AMOUNTTYPE,'DR','Dr.','Cr.') AMOUNTTYPE ");
		selectQuery.append(" from Cb_BANKOPENINGBAL_INFO info,Cb_bank_info bank where bank.ACCOUNTNO=info.ACCOUNTNO and upper(bankname) like upper(?) and upper(info.ACCOUNTNO) like upper(?)");
		queries.put("selectQuery", selectQuery);
		
		StringBuffer editQuery = new StringBuffer(" select info.ACCOUNTNO,bankName,Nvl(to_char(OPENEDDATE,'dd/Mon/yyyy'),' ')OPENEDDATE,AMOUNT,Nvl(DETAILS,' ')DETAILS,");
		editQuery.append("AMOUNTTYPE from Cb_BANKOPENINGBAL_INFO info,Cb_bank_info bank where bank.ACCOUNTNO=info.ACCOUNTNO and info.ACCOUNTNO = ?");
		queries.put("editQuery", editQuery);
				
		StringBuffer updateQuery = new StringBuffer(" update Cb_BANKOPENINGBAL_INFO set OPENEDDATE = ? , AMOUNT = ? , DETAILS = ?,AMOUNTTYPE=?,EDITEDBY=?,EDITEDDT=SYSDATE  where ACCOUNTNO = ?");
		queries.put("updateQuery", updateQuery);
		
		StringBuffer accQuery = new StringBuffer("select * from Cb_BANK_INFO bank ,Cb_ACCOUNTCODE_INFO acc  where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and Upper(BANKNAME) like Upper(?) and ACCOUNTNO Not in (select ACCOUNTNO from  BANKOPENINGBAL_INFO)");
		queries.put("accQuery", accQuery);
		
		StringBuffer bankQuery = new StringBuffer(" select * from cb_bank_info where ACCOUNTNO not in (select ACCOUNTNO from cb_bankopeningbal_info)");
		queries.put("bankQuery", bankQuery);
		
		StringBuffer existQuery = new StringBuffer(" select count(*) from Cb_BANKOPENINGBAL_INFO  bank ,Cb_bank_info acc where acc.ACCOUNTNO=bank.ACCOUNTNO and bank.ACCOUNTNO = ?");
		queries.put("existQuery", existQuery);
		
		StringBuffer otherOpenBalQuery = new StringBuffer(" select * from cb_bank_info where ACCOUNTNO notin (select ACCOUNTNO from cb_bankopeningbal_info)");
		queries.put("otherOpenBalQuery", otherOpenBalQuery);		
		
		return queries.get(query).toString();
	}
	
}
