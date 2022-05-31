package com.epis.dao.cashbookDummy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.cashbookDummy.AccountingCodeTypeInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;


public class AccountingCodeTypeDAO {

	static AccountingCodeTypeDAO dao = new AccountingCodeTypeDAO();
	Log log = new Log(AccountingCodeTypeDAO.class);
	
	private AccountingCodeTypeDAO(){
		
	}	
	
	public static AccountingCodeTypeDAO getInstance(){
		return dao;
	}

	public boolean exists(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeDAO : exists : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(existQuery);
			pst.setString(1, info.getAccountCodeType());
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) >= 1){
					exists = true;
				}
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
		log.info("AccountingCodeTypeDAO : exists : leaving method");
		return exists;
	}
	
	public void addAccountTypeRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeDAO : addAccountTypeRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, info.getAccountCodeType());
			pst.setString(2, info.getDescription());
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
		log.info("AccountingCodeTypeDAO : addAccountTypeRecord : leaving method");
	}
	
	public List searchRecords(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeDAO : searchRecords : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		List records = new ArrayList();
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(searchQuery);
			pst.setString(1, info.getAccountCodeType()+"%");
			pst.setString(2, info.getDescription()+"%");
			rs = pst.executeQuery();
			while(rs.next()){
				info = new AccountingCodeTypeInfo();
				info.setCode(rs.getString("code"));
				info.setAccountCodeType(rs.getString("accountcodetype"));
				info.setDescription(rs.getString("description"));
				records.add(info);
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
		log.info("AccountingCodeTypeDAO : searchRecords : leaving method");
		return records;
	}
	
	public AccountingCodeTypeInfo editRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeDAO : editRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		List records = new ArrayList();
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(editQuery);
			pst.setString(1, info.getCode());
			rs = pst.executeQuery();
			if(rs.next()){
				info.setAccountCodeType(rs.getString("accountcodetype"));
				info.setDescription(rs.getString("description"));
				records.add(info);
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
		log.info("AccountingCodeTypeDAO : editRecord : leaving method");
		return info;
	}
	
	public void updateRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeDAO : updateRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, info.getDescription());
			pst.setString(2, info.getCode());
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
		log.info("AccountingCodeTypeDAO : updateRecord : leaving method");
	}
	
	public void deleteRecord(String[] types) throws Exception {
		log.info("AccountingCodeTypeDAO : deleteRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<types.length;i++){
				sb.append(types[i]).append("|");
			}
			con = DBUtility.getConnection();
			pst = con.prepareStatement(deleteQuery);
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
		log.info("AccountingCodeTypeDAO : deleteRecord : leaving method");
	}
	
	String existQuery = " select count(*) from CB_AccountCodeType_INFO where upper(trim(accountcodetype))= upper(trim(?))";
	
	String insertQuery = " insert into CB_AccountCodeType_INFO (code,accountcodetype,description) values ((select lpad(nvl(max(nvl(code,0)),0)+1,5,0) from AccountCodeType_INFO),?,?)";
	
	String searchQuery = "select code,accountcodetype,description from CB_AccountCodeType_INFO where upper(trim(accountcodetype))  like upper(trim(?)) and upper(trim(description)) like upper(trim(?))";
	
	String editQuery = " select accountcodetype,description from CB_AccountCodeType_INFO where upper(trim(code))= upper(trim(?))";
	
	String updateQuery = " update CB_AccountCodeType_INFO set description=? where upper(trim(code))= upper(trim(?))";

	String deleteQuery = " delete CB_AccountCodeType_INFO Where INSTR(upper(?),upper(code)) > 0 ";
}
