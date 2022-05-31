package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.PartyInfo;
import com.epis.dao.cashbook.PartyDAO;

public class PartyService {

	private PartyService() {
	}

	private static final PartyService service = new PartyService();

	public static PartyService getInstance() {
		return service;
	}
	
	public List search(PartyInfo info) throws Exception {
		return PartyDAO.getInstance().search(info);
	}
	
	public void delete(String[] recs) throws Exception {
		PartyDAO.getInstance().delete(recs);		
	}
	
	public void add(PartyInfo info) throws Exception {
		PartyDAO.getInstance().add(info);		
	}
	
	public boolean exists(PartyInfo info) throws Exception {
		return PartyDAO.getInstance().exists(info);
	}

	public PartyInfo edit(PartyInfo info) throws Exception {
		return PartyDAO.getInstance().edit(info);
	}

	public void update(PartyInfo info) throws Exception {
		PartyDAO.getInstance().update(info);		
		
	}
	public List partyReport(String party) throws Exception {
		return PartyDAO.getInstance().partyReport(party);		
	}
	public List getPartyList(String type)throws Exception{
		return PartyDAO.getInstance().getPartyList(type);
	}
	public List getGroupPartyList(String type)throws Exception{
		return PartyDAO.getInstance().getGroupPartyList(type);
	}
	public List getGroupList()throws Exception{
		return PartyDAO.getInstance().getGroupList();
	}
	
	/*	Log log = new Log(BankMasterService.class);
	PartyDAO dao = PartyDAO.getInstance();
	
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
*/	

}
