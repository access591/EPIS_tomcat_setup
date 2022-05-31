package com.epis.service.cashbookDummy;

import java.util.List;

import com.epis.bean.cashbookDummy.AccountingCodeTypeInfo;
import com.epis.dao.cashbookDummy.AccountingCodeTypeDAO;
import com.epis.utilities.Log;


public class AccountingCodeTypeService {

	static AccountingCodeTypeService service = new AccountingCodeTypeService();
	Log log = new Log(AccountingCodeTypeService.class);
	AccountingCodeTypeDAO dao = AccountingCodeTypeDAO.getInstance();
	
	private AccountingCodeTypeService(){
		
	}	
	
	public static AccountingCodeTypeService getInstance(){
		return service;
	}

	public boolean exists(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeService : exists : Entering Method");
		boolean exist = dao.exists(info);		
		log.info("AccountingCodeTypeService : exists : Leaving Method");
		return exist;
	}

	public void addAccountTypeRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeInfo : addAccountTypeRecord : Entering Method");
		dao.addAccountTypeRecord(info);		
		log.info("AccountingCodeTypeInfo : addAccountTypeRecord : Leaving Method");
	}

	public List searchRecords(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeService : searchRecords : Entering Method");
		List records = dao.searchRecords(info);		
		log.info("AccountingCodeTypeService : searchRecords : Leaving Method");
		return records;
	}

	public AccountingCodeTypeInfo editRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeService : editRecord : Entering Method");
		info = dao.editRecord(info);		
		log.info("AccountingCodeTypeService : editRecord : Leaving Method");
		return info;
	}

	public void updateRecord(AccountingCodeTypeInfo info) throws Exception {
		log.info("AccountingCodeTypeService : updateRecord : Entering Method");
		dao.updateRecord(info);		
		log.info("AccountingCodeTypeService : updateRecord : Leaving Method");
	}

	public void deleteRecord(String[] types) throws Exception {
		log.info("AccountingCodeTypeService : deleteRecord : Entering Method");
		dao.deleteRecord(types);		
		log.info("AccountingCodeTypeService : deleteRecord : Leaving Method");
	}
}
