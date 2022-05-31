package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.investment.FundAccuredBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;

public class FundAccuredDAO {

	private FundAccuredDAO() {

	}

	private static final FundAccuredDAO dao = new FundAccuredDAO();

	public static FundAccuredDAO getInstance() {
		return dao;
	}

	public List search(FundAccuredBean bean) throws EPISException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List fundAcc = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pstmt = con.prepareStatement(searchQuery);
			pstmt.setString(1,bean.getFinYear()+"%");
			rs = pstmt.executeQuery(); 
			while(rs.next()){
				fundAcc.add(new FundAccuredBean(rs.getString("FINANCIALYEAR"),rs.getString("AMOUNT"),rs.getString("trusttype")));
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (EPISException e) {
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(con,pstmt,rs);
		}
		return fundAcc;
	}
	
	public void add(FundAccuredBean bean) throws EPISException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setString(1,bean.getFinYear());
			pstmt.setString(2,bean.getAmount());
			pstmt.setString(3,bean.getLoginUserId());
			pstmt.setString(4,bean.getLoginUnitCode());
			pstmt.setString(5,bean.getTrustType());
			pstmt.setString(6,bean.getAsOnDate());
			pstmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (EPISException e) {
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(con,pstmt,rs);
		}
	}

	public void delete(FundAccuredBean bean) throws EPISException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pstmt = con.prepareStatement(deleteQuery);
			pstmt.setString(1,bean.getFinYear());
			pstmt.setString(2,bean.getTrustType());
			pstmt.executeUpdate(); 		
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (EPISException e) {
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(con,pstmt,rs);
		}
	}
	
	String searchQuery = "select FINANCIALYEAR,AMOUNT,trusttype from invest_fundaccured where FINANCIALYEAR like ? ";
	String insertQuery = " insert into invest_fundaccured (FINANCIALYEAR,AMOUNT,CREATED_BY,CREATED_DT,UNITCODE,trusttype,ASONDATE)" +
			" values (?,?,?,sysdate,?,?,?) ";
	String deleteQuery = "delete from invest_fundaccured where FINANCIALYEAR= ? and TRUSTTYPE=?";
}
