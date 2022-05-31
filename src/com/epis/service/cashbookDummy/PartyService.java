package com.epis.service.cashbookDummy;

import java.util.List;

import com.epis.bean.cashbookDummy.PartyInfo;
import com.epis.dao.cashbookDummy.PartyDAO;
import com.epis.utilities.Log;


public class PartyService {

	Log log = new Log(BankMasterService.class);
	PartyDAO dao = new PartyDAO();
	
	public List getPartyList(PartyInfo info,String type) throws Exception {
		log.info("PartyService : getPartyList : Entering Method");
		List partyInfo = dao.getPartyList(info,type);		
		log.info("PartyService : getPartyList : Leaving Method");
		return partyInfo;
	}

	public void addPartyRecord(PartyInfo info) throws Exception {
		log.info("PartyService : addPartyRecord : Entering Method");
		dao.addPartyRecord(info);		
		log.info("PartyService : addPartyRecord : Leaving Method");		
	}

	public boolean exists(PartyInfo info,String accNos) throws Exception {
		log.info("PartyService : exists : Entering Method");
		boolean exists = dao.exists(info,accNos);		
		log.info("PartyService : exists : Leaving Method");
		return exists;
	}

	public void updatePartyRecord(PartyInfo info) throws Exception {
		log.info("PartyService : updatePartyRecord : Entering Method");
		dao.updatePartyRecord(info);		
		log.info("PartyService : updatePartyRecord : Leaving Method");		
		
	}

	public void deletePartyRecord(String codes) throws Exception {
		log.info("PartyService : deletePartyRecord : Entering Method");
		try{
		dao.deletePartyRecord(codes);	
		}catch(Exception e)
		{throw e;}
		log.info("PartyService : deletePartyRecord : Leaving Method");		
	}

}
