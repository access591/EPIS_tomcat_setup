/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.cashbook.AccountingCodeTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

/**
 * Class AccountingCodeTypeDAO depends on specific features of data resources
 * that ties together business logic with data access logic.
 * 
 * @version 1.00 16 Dec 2009
 * @author Jaya Sree
 */

public class AccountingCodeTypeDAO {

	// This provides a logging object that you can create and destroy as you.
	Log log = new Log(AccountingCodeTypeDAO.class);

	/*
	 * This private constructor is used for restricting the instantiation of a
	 * class to one object. By using Singleton Design pattern.
	 */
	private AccountingCodeTypeDAO() {
	}

	private static final AccountingCodeTypeDAO dao = new AccountingCodeTypeDAO();

	public static AccountingCodeTypeDAO getInstance() {
		return dao;
	}

	// This method is used to retrieve List of Records according to the given
	// search criteria .
	public List search(AccountingCodeTypeBean info) throws EPISException {

		log.info("AccountingCodeTypeDAO : search : Entering method");

		Connection con = null;
		ResultSet rs = null;
		List records = new ArrayList();

		try {
			// Getting the Database Connection.
			con = DBUtility.getConnection();

			String[] params = new String[2];
			params[0] = StringUtility.checknull(info.getAccountCodeType(), "%");
			params[1] = StringUtility.checknull(info.getDescription(), "%");

			// Executes the SQL query and returns the ResultSet
			rs = DBUtility.prepExecuteQuery("accType.search", params, con);

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

	public boolean exists(AccountingCodeTypeBean info) throws EPISException {

		log.info("AccountingCodeTypeDAO : exists : Entering method");

		Connection con = null;
		ResultSet rs = null;
		boolean exists = false;

		try {
			// Getting the Database Connection.
			con = DBUtility.getConnection();

			String[] params = new String[1];
			params[0] = StringUtility.checknull(info.getAccountCodeType());

			// Executes the SQL query and returns the ResultSet
			rs = DBUtility.prepExecuteQuery("accType.exists", params, con);
			
			if (rs.next() && rs.getInt(1) >= 1) {
				// The record with the data given already exists.
				exists = true;
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

		log.info("AccountingCodeTypeDAO : exists : leaving method");

		return exists;
	}

	public void add(AccountingCodeTypeBean info) throws EPISException {

		log.info("AccountingCodeTypeDAO : add : Entering method");

		try {
			// Setting the designated parameter to the given Java String value.
			String[] params = new String[4];
			params[0] = StringUtility.checknull(info.getAccountCodeType());
			params[1] = StringUtility.checknull(info.getDescription());
			params[2] = StringUtility.checknull(info.getLoginUserId());
			params[3] = StringUtility.checknull(info.getLoginUnitCode());

			// Executes the SQL query and returns the int
			DBUtility.prepExecuteUpdate("accType.insert", params);
			UserTracking.write(info.getLoginUserId(), info.toString(), "S", "CB", info.getAccountCodeType(),
					"Accounting Code Type");
		} catch (EPISException e) {
			log.error(e.toString());
			throw new EPISException(e);
		}
		log.info("AccountingCodeTypeDAO : add : leaving method");
	}

	public AccountingCodeTypeBean edit(AccountingCodeTypeBean info)
			throws EPISException {
		log.info("AccountingCodeTypeDAO : edit : Entering method");
		Connection con = null;
		ResultSet rs = null;
		try {
			// Getting the Database Connection.
			con = DBUtility.getConnection();

			String[] params = new String[1];
			params[0] = StringUtility.checknull(info.getCode());

			// Executes the SQL query and returns the ResultSet
			rs = DBUtility.prepExecuteQuery("accType.edit", params, con);

			if (rs.next()) {
				info.setAccountCodeType(rs.getString("accountcodetype"));
				info.setDescription(rs.getString("description"));
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

		log.info("AccountingCodeTypeDAO : edit : leaving method");

		return info;
	}

	public void update(AccountingCodeTypeBean info) throws EPISException {

		log.info("AccountingCodeTypeDAO : update : Entering method");

		try {
			// Setting the designated parameter to the given Java String value.
			String[] params = new String[4];
			params[0] = StringUtility.checknull(info.getAccountCodeType());
			params[1] = StringUtility.checknull(info.getDescription());
			params[2] = StringUtility.checknull(info.getLoginUserId());
			params[3] = StringUtility.checknull(info.getCode());

			// Executes the SQL query and returns the int
			DBUtility.prepExecuteUpdate("accType.update", params);

		} catch (EPISException e) {
			log.error(e.toString());
			throw new EPISException(e);
		}
		log.info("AccountingCodeTypeDAO : update : leaving method");
	}

	public void delete(String[] types) throws EPISException {
		log.info("AccountingCodeTypeDAO : delete : Entering method");
		try {
			int len = types.length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append(types[i]).append("|");
			}

			// Setting the designated parameter to the given Java String value.
			String[] params = new String[1];
			params[0] = StringUtility.checknull(sb.toString());
			// Executes the SQL query and returns the int
			DBUtility.prepExecuteUpdate("accType.delete", params);
		} catch (EPISException e) {
			log.error(e.toString());
			throw new EPISException(e);
		}
		log.info("AccountingCodeTypeDAO : delete : leaving method");
	}
}
