/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.AccountingCodeInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.AccountingCodeDAO;

/**
 * Class AccountingCodeService is a Business Delegate to reduce coupling
 * between presentation-tier clients and business services. This hides the
 * underlying implementation details of the business service.
 * 
 * @version 1.00 03 Mar 2010
 * @author Jaya Sree
 */
public class AccountingCodeService {
	
	/*
	 * This private constructor is used for restricting the instantiation of a
	 * class to one object. By using Singleton Design pattern.
	 */
	private AccountingCodeService() {
	}

	private static final AccountingCodeService service = new AccountingCodeService();

	public static AccountingCodeService getInstance() {
		return service;
	}

	// This method saves the record in the database.
	public void add(AccountingCodeInfo info) throws Exception {
		AccountingCodeDAO.getInstance().add(info);		
	}

	// This method is used to retrieve List of Records according to the given
	// search criteria .
	public List search(AccountingCodeInfo info) throws EPISException {
		return AccountingCodeDAO.getInstance().search(info);
	}
	
	public List getBankAccountingCodeList() throws EPISException {
		return AccountingCodeDAO.getInstance().getBankAccountingCodeList();
	}
	
	/*
	 * This method Checks whether the record already Exists or not and returns
	 * true if already exists else false.
	 */
	public boolean exists(AccountingCodeInfo info) throws EPISException {
		return AccountingCodeDAO.getInstance().exists(info);		
	}

	// This method updates the record in the database.
	public void update(AccountingCodeInfo info) throws Exception {		
		AccountingCodeDAO.getInstance().update(info);		
	}

	public void delete(String[] types) throws EPISException {
		AccountingCodeDAO.getInstance().delete(types);		
	}

	// This method is used to retrieve a Record to edit.
	public AccountingCodeInfo edit(AccountingCodeInfo info) throws EPISException {
		return AccountingCodeDAO.getInstance().edit(info);
	}

	// This method is used to retrieve all of Records.
	public List getAccountingHeads() throws Exception {
		return AccountingCodeDAO.getInstance().search(new AccountingCodeInfo());
	}

	// To retrieve the Records based on the Search criteria and display in the Report.
	public List getReport(AccountingCodeInfo info) throws EPISException {
		return AccountingCodeDAO.getInstance().getReport(info);
	}
}
