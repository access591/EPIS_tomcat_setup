package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.BankOpenBalInfo;
import com.epis.dao.cashbook.BankOpenBalDAO;
import com.epis.utilities.Log;


public class BankOpenBalService {
	
	private BankOpenBalService() {
	}
	
	Log log = new Log(BankOpenBalDAO.class);

	private static final BankOpenBalService service = new BankOpenBalService();

	
	public static BankOpenBalService getInstance() {
		return service;
	}
	
	public boolean exists(BankOpenBalInfo info) throws Exception {	
		log.info("BankOpenBalService : exists : Entering Method");
		boolean exist = BankOpenBalDAO.getInstance().exists(info);	
		log.info("BankOpenBalService : exists : Entering Method");
		return exist;
	}
	
	public void add(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalService : add : Entering Method");
		BankOpenBalDAO.getInstance().add(info);		
		log.info("BankOpenBalService : add : Entering Method");
	}
	
	public List search(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalService : search : Entering Method");
		List dataList = BankOpenBalDAO.getInstance().search(info);	
		log.info("BankOpenBalService : search : Entering Method");
		return dataList;
	}
	

	public void delete(String[] types) throws Exception {
		log.info("BankOpenBalService : deleteBankOpenBalRecord : Entering Method");
		BankOpenBalDAO.getInstance().delete(types);		
		log.info("BankOpenBalService : deleteBankOpenBalRecord : Leaving Method");		
	}

	public BankOpenBalInfo edit(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalService : getRecord : Entering Method");
		info = BankOpenBalDAO.getInstance().edit(info);		
		log.info("BankOpenBalService : getRecord : Leaving Method");
		return info;
	}

	public void update(BankOpenBalInfo info) throws Exception {
		log.info("BankOpenBalService : updateOpenBalRecord : Entering Method");
		BankOpenBalDAO.getInstance().update(info);		
		log.info("BankOpenBalService : updateOpenBalRecord : Leaving Method");		
	}
	
	public List getBankList() throws Exception {		
		return BankOpenBalDAO.getInstance().getBankList();
	}
	
	
	
	public String getAccno(String bankname) throws Exception {		
		List accInfo = BankOpenBalDAO.getInstance().getAccno(bankname);	
		StringBuffer sb = new StringBuffer("<ServiceTypes>");
		int len = accInfo.size();
		for(int i=0;i<len;i++) {			
			sb.append("<ServiceType>");
			sb.append("<accNo>").append(accInfo.get(i))
			.append("</accNo></ServiceType>");
		}
		sb.append("</ServiceTypes>");
		return sb.toString();
	}


}
