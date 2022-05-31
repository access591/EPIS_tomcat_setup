package com.epis.service.cashbookDummy;

import java.util.List;
import java.util.Map;

import com.epis.bean.cashbookDummy.BankMasterInfo;
import com.epis.dao.cashbookDummy.BankMasterDAO;
import com.epis.utilities.Log;

public class BankMasterService {
	
	Log log = new Log(BankMasterService.class);
	BankMasterDAO dao = new BankMasterDAO();
	
	public List getBankList(BankMasterInfo info,String type) throws Exception {
		log.info("BankMasterService : getBankList : Entering Method");
		List bankInfo = dao.getBankList(info,type);		
		log.info("BankMasterService : getBankList : Leaving Method");
		return bankInfo;
	}

	public void addBankRecord(BankMasterInfo info) throws Exception {
		log.info("BankMasterService : addBankRecord : Entering Method");
		dao.addBankRecord(info);		
		log.info("BankMasterService : addBankRecord : Leaving Method");		
	}

	public boolean exists(BankMasterInfo info) throws Exception {
		log.info("BankMasterService : exists : Entering Method");
		boolean exists = dao.exists(info);		
		log.info("BankMasterService : exists : Leaving Method");
		return exists;
	}

	public void updateBankRecord(BankMasterInfo info) throws Exception {
		log.info("BankMasterService : updateBankRecord : Entering Method");
		dao.updateBankRecord(info);		
		log.info("BankMasterService : updateBankRecord : Leaving Method");	
		
	}

	public void deleteBankRecord(String codes) throws Exception {
		log.info("BankMasterService : deleteBankRecord : Entering Method");
		try{
		dao.deleteBankRecord(codes);	
		}catch(Exception e)
		{throw e;}
		log.info("BankMasterService : deleteBankRecord : Leaving Method");	
		
	}

	public List getBankDetails(BankMasterInfo info) throws Exception {
		log.info("BankMasterService : getBankDetails : Entering Method");
		List bankInfo = dao.getBankDetails(info);		
		log.info("BankMasterService : getBankDetails : Leaving Method");
		return bankInfo;
	}

	public List getRegions() throws Exception {
		log.info("BankMasterService : getRegions : Entering Method");
		List regions = dao.getRegions();		
		log.info("BankMasterService : getRegions : Leaving Method");
		return regions;
	}

	public Map getUnits(String region) throws Exception {
		log.info("BankMasterService : getUnits : Entering Method");
		Map units = dao.getUnits(region);		
		log.info("BankMasterService : getUnits : Leaving Method");
		return units;
	}

	
}
