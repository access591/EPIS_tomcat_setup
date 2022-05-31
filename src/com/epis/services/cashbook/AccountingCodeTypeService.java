/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.AccountingCodeTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.AccountingCodeTypeDAO;

/**
 * Class AccountingCodeTypeService is a Business Delegate to reduce coupling
 * between presentation-tier clients and business services. This hides the
 * underlying implementation details of the business service.
 * 
 * @version 1.00 16 Dec 2009
 * @author Jaya Sree
 */

public class AccountingCodeTypeService {

	/*
	 * This private constructor is used for restricting the instantiation of a
	 * class to one object. By using Singleton Design pattern.
	 */
	private AccountingCodeTypeService() {
	}

	private static final AccountingCodeTypeService service = new AccountingCodeTypeService();

	public static AccountingCodeTypeService getInstance() {
		return service;
	}

	/*
	 * This method Checks whether the record already Exists or not and returns
	 * true if already exists else false.
	 */
	public boolean exists(AccountingCodeTypeBean info) throws EPISException {
		return AccountingCodeTypeDAO.getInstance().exists(info);
	}

	// This method saves the record in the database.
	public void add(AccountingCodeTypeBean info) throws EPISException {
		AccountingCodeTypeDAO.getInstance().add(info);
	}

	// This method is used to retrieve List of Records according to the given
	// search criteria .
	public List search(AccountingCodeTypeBean info) throws EPISException {
		return AccountingCodeTypeDAO.getInstance().search(info);
	}

	// This method is used to retrieve a Record to edit.
	public AccountingCodeTypeBean edit(AccountingCodeTypeBean info)
			throws EPISException {
		return AccountingCodeTypeDAO.getInstance().edit(info);
	}

	// This method updates the record in the database.
	public void update(AccountingCodeTypeBean info) throws EPISException {
		AccountingCodeTypeDAO.getInstance().update(info);
	}

	// This method deletes the records in the database.
	public void delete(String[] types) throws EPISException {
		AccountingCodeTypeDAO.getInstance().delete(types);
	}

	// This method is used to retrieve List of all the Records in the table.
	public List getAccountingCodeTypes() throws EPISException {
		return search(new AccountingCodeTypeBean());
	}
}
