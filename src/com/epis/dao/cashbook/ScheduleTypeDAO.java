/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.cashbook.AccountingCodeTypeBean;
import com.epis.bean.cashbook.ScheduleTypeInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

/**
 * Class ScheduleTypeDAO is a Access to data varies depending on the source of
 * the data. Access to persistent storage, such as to a database
 * 
 * @version 1.00 29 Oct 2010
 * @author Jaya Sree
 */
public class ScheduleTypeDAO {

	Log log = new Log(ScheduleTypeDAO.class);

	/**
	 * This private constructor is used for restricting the instantiation of a
	 * class to one object. By using Singleton Design pattern.
	 */
	private ScheduleTypeDAO() {
	}

	private static final ScheduleTypeDAO dao = new ScheduleTypeDAO();

	public static ScheduleTypeDAO getInstance() {
		return dao;
	}
	
	/**
	 * This method is used to retrieve List of Records according to the given
	 * search criteria .
	 * 
	 * @param info
	 * @return schTypes
	 * @throws EPISException
	 */
	public List search(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : search : Entering method");

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List schTypes = new ArrayList();
		try {
			// Establishing Connection to Database
			con = DBUtility.getConnection();

			// Select query to retrieve the data from the table by giving the
			// dynamic parameters
			pst = con.prepareStatement(selectQuery);
			pst.setString(1, StringUtility.checknull(info.getSchType()) + "%");
			pst.setString(2, StringUtility.checknull(info.getDescription())
					+ "%");

			// Execute the Query
			rs = pst.executeQuery();

			// If the Current row exists then the loop executes.
			while (rs.next()) {
				info = new ScheduleTypeInfo();
				info.setSchType(rs.getString("name"));
				info.setDescription(rs.getString("description"));
				if(rs.getString("accheadsadd") != null )
					info.setAccHeadAdd(rs.getString("accheadsadd").split("\\,"));
				if(rs.getString("accheadsless") != null )
					info.setAccHeadLess(rs.getString("accheadsless").split("\\,"));
				info.setType(rs.getString("type"));
				// Adding all the objects to a list.
				schTypes.add(info);
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(con, pst, rs);
		}
		log.info("ScheduleTypeDAO : search : leaving method");

		return schTypes;
	}	

	/**
	 * This method is used to retrieve the status whether the data already exists or not.
	 * 
	 * @param info
	 * @return exist
	 * @throws EPISException
	 */
	public boolean exists(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : exists : Entering method");

		boolean exist = false;
		int count;

		try {
			// Count query to known whether the data already exists or not
			String existQuery = " select count(*) from cb_scheduletype where upper(name) = upper('"
					+ info.getSchType() + "') ";
			count = DBUtility.getRecordCount(existQuery);
			exist = count > 0 ? true : false;
		} catch (Exception e) {
			throw new EPISException(e);
		}

		log.info("ScheduleTypeDAO : exists : leaving method");

		return exist;
	}
	
	/**
	 * This method is used to add a new record.
	 * 
	 * @param info
	 * @throws EPISException 
	 */
	public void add(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : add : Entering method");
		
		Connection con = null;
		PreparedStatement pst = null;

		try {
			// Establishing Connection to Database
			con = DBUtility.getConnection();

			// Insert query to insert the data into the table by giving the
			// dynamic parameters
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, StringUtility.checknull(info.getSchType()));
			pst.setString(2, StringUtility.checknull(info.getDescription()));
			pst.setString(3, StringUtility.checknull(info.getAccHeadsAdd()));
			pst.setString(4, StringUtility.checknull(info.getLoginUserId()));
			pst.setString(5, StringUtility.checknull(info.getLoginUnitCode()));
			pst.setString(6, StringUtility.checknull(info.getType()));
			pst.setString(7, StringUtility.checknull(info.getAccHeadsLess()));
			
			// Execute the Query
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new EPISException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(con, pst,null);
		}
		
		log.info("ScheduleTypeDAO : add : leaving method");
	}
	
	public ScheduleTypeInfo getSchedule(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : getSchedule : Entering method");

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			// Establishing Connection to Database
			con = DBUtility.getConnection();

			// Select query to retrieve the data from the table by giving the
			// dynamic parameters
			pst = con.prepareStatement(schQuery);
			pst.setString(1, StringUtility.checknull(info.getSchType()));

			// Execute the Query
			rs = pst.executeQuery();

			// If the Current row exists then the loop executes.
			if (rs.next()) {
				info.setSchType(rs.getString("name"));
				info.setDescription(rs.getString("description"));
				if(rs.getString("accheadsadd") != null )
					info.setAccHeadAdd(rs.getString("accheadsadd").split("\\,"));
				if(rs.getString("accheadsless") != null )
					info.setAccHeadLess(rs.getString("accheadsless").split("\\,"));
				info.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(con, pst, rs);
		}
		log.info("ScheduleTypeDAO : getSchedule : leaving method");

		return info;
	}
	
	public void update(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : update : Entering method");
		
		Connection con = null;
		PreparedStatement pst = null;

		try {
			// Establishing Connection to Database
			con = DBUtility.getConnection();

			// Update query to update the data into the table by giving the
			// dynamic parameters
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, StringUtility.checknull(info.getDescription()));
			pst.setString(2, StringUtility.checknull(info.getAccHeadsAdd()));
			pst.setString(3, StringUtility.checknull(info.getLoginUserId()));
			pst.setString(4, StringUtility.checknull(info.getAccHeadsLess()));
			pst.setString(5, StringUtility.checknull(info.getType()));
			pst.setString(6, StringUtility.checknull(info.getSchType()));
			
			// Execute the Query
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(con, pst,null);
		}		
		log.info("ScheduleTypeDAO : update : leaving method");
	}
	
	public void delete(ScheduleTypeInfo info) throws EPISException {
		log.info("ScheduleTypeDAO : delete : Entering method");
		
		Connection con = null;
		PreparedStatement pst = null;

		try {
			// Establishing Connection to Database
			con = DBUtility.getConnection();

			// Delete query to delete the data into the table by giving the
			// dynamic parameters
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, StringUtility.checknull(info.getSchType()));			
			
			// Execute the Query
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(con, pst,null);
		}
		
		log.info("ScheduleTypeDAO : delete : leaving method");
	}
	
	public List getAccountingCodeTypes() throws EPISException {
		log.info("AccountingCodeTypeDAO : search : Entering method");

		Connection con = null;
		ResultSet rs = null;
		List records = new ArrayList();

		try {
			// Getting the Database Connection.
			con = DBUtility.getConnection();

			
			// Executes the SQL query and returns the ResultSet
			rs = DBUtility.getRecordSet(accQuery,con.createStatement());

			while (rs.next()) {
				// Adding of the AccountingCodeTypeBean objects to the List
				records.add(new AccountingCodeTypeBean(rs.getString("code"), rs
						.getString("accountcodetype"), rs
						.getString("description")));
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new EPISException(e);
		} catch (Exception e) {
			log.error(e.toString());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, null, con);
		}

		log.info("AccountingCodeTypeDAO : search : leaving method");

		return records;
	}
	
	String accQuery = "select code,accountcodetype,description from Cb_AccountCodeType_INFO ";

	String selectQuery = "select name,type.description,accheadsadd,accheadsless,ACCOUNTCODETYPE type from cb_scheduletype type,cb_accountcodetype_info where upper(name) like upper(?) and upper(type.description) like upper(?) and type= code order by name ";
	
	String schQuery = "select name,description,accheadsadd,accheadsless,type from cb_scheduletype where upper(name) = upper(?) ";

	String insertQuery = "insert into cb_scheduletype(name,description,accheadsadd,createdon,created_by,unitcode,type,accheadsless) values(upper(?),?,?,sysdate,?,?,?,?)";
	
	String updateQuery = " update cb_scheduletype set description=? ,accheadsadd=? , UPDATED_BY=? , UPDATED_DT=sysdate,accheadsless=?,type=?  where upper(name) = upper(?)";
	
	String deleteQuery = " delete from cb_scheduletype where upper(name) = upper(?)";

	

}
