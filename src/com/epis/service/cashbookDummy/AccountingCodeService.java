package com.epis.service.cashbookDummy;

import java.util.List;

import com.epis.bean.cashbookDummy.AccountingCodeInfo;
import com.epis.dao.cashbookDummy.AccountingCodeDAO;
import com.epis.utilities.Log;
 
public class AccountingCodeService {
	
	Log log = new Log(AccountingCodeService.class);
	AccountingCodeDAO dao = new AccountingCodeDAO();
	
	public void addAccountRecord(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeService : addAccountRecord : Entering Method");
		dao.addAccountRecord(info);		
		log.info("AccountingCodeService : addAccountRecord : Leaving Method");		
	}

	public List getAccountList(AccountingCodeInfo info,String type) throws Exception {
		log.info("AccountingCodeService : getAccountList : Entering Method");
		List dataList = dao.getAccountList(info,type);		
		log.info("AccountingCodeService : getAccountList : Leaving Method");
		return dataList;
	}
	
	public List getBankAccountingCodeList() throws Exception {
		log.info("AccountingCodeService : getBankAccountingCodeList : Entering Method");
		List dataList = dao.getBankAccountingCodeList();		
		log.info("AccountingCodeService : getBankAccountingCodeList : Leaving Method");
		return dataList;
	}
	
	public boolean exists(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeService : exists : Entering Method");
		boolean exists = dao.exists(info);		
		log.info("AccountingCodeService : exists : Leaving Method");
		return exists;
	}

	public void updateAccountRecord(AccountingCodeInfo info) throws Exception {
		log.info("AccountingCodeService : updateAccountRecord : Entering Method");
		dao.updateAccountRecord(info);		
		log.info("AccountingCodeService : updateAccountRecord : Leaving Method");
		
	}

	public void deleteAccountRecord(String codes) throws Exception {
		log.info("AccountingCodeService : deleteAccountRecord : Entering Method");
		try{
		dao.deleteAccountRecord(codes);
		}catch(Exception e)
		{throw e;}
		log.info("AccountingCodeService : deleteAccountRecord : Leaving Method");
		
	}
}
