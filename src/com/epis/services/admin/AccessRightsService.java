package com.epis.services.admin;

import java.util.List;
import java.util.Map;

import com.epis.common.exception.EPISException;
import com.epis.dao.admin.AccessRightsDAO;

public class  AccessRightsService {

	private AccessRightsService() {
	}

	private static final AccessRightsService service = new AccessRightsService();

	public static AccessRightsService getInstance() {
		return service;
	}

	public Map getAccessCodes(String modules) throws EPISException {
		Map accesscodes = AccessRightsDAO.getInstance().getAccessCodes(modules); 
		return accesscodes;
	}

	public List getAccessRights(String userType, String user) throws EPISException {
		List accessRights = AccessRightsDAO.getInstance().getAccessRights(userType,user); 
		return accessRights;
	}
	public void saveAccessRights(List screens,String userorroleId,String moduleCodes) throws EPISException{
		AccessRightsDAO.getInstance().saveAccessRights(screens,userorroleId,moduleCodes);
	}
}
