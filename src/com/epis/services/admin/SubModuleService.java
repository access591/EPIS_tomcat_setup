package com.epis.services.admin;

import java.util.List;
import com.epis.bean.admin.SMBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.SubModuleDAO;


public class SubModuleService {

	private SubModuleService() {
	}

	private static final SubModuleService service = new SubModuleService();

	public static SubModuleService getInstance() {
		return service;
	}
	
	public List getSMList() throws Exception {
		return SubModuleDAO.getInstance().getSMList();
	}
	public List getModuleList()throws Exception {
		return SubModuleDAO.getInstance().getModuleList();
	}
	public List searchSM(String moduleCode,String subModuleName) throws ServiceNotAvailableException, EPISException{
		return SubModuleDAO.getInstance().searchSM(moduleCode,subModuleName);
	}
	public void saveSM(SMBean apbean) throws ServiceNotAvailableException, EPISException{
		SubModuleDAO.getInstance().saveSM(apbean);
	}
	public void editSM(SMBean apbean) throws ServiceNotAvailableException, EPISException{
		SubModuleDAO.getInstance().editSM(apbean);
	}
	public SMBean findSM(String smCode) throws ServiceNotAvailableException, EPISException {
		SMBean smbean =new SMBean();
		smbean = SubModuleDAO.getInstance().findSM(smCode);
	return smbean;
	}
	public void deleteSM(String smcds) throws ServiceNotAvailableException, EPISException {
		SubModuleDAO.getInstance().deleteSM(smcds);
	}
}
