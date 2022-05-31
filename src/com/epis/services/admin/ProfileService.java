package com.epis.services.admin;

import java.util.List;
import java.util.Map;

import com.epis.common.exception.EPISException;
import com.epis.dao.admin.ProfileDAO;



public class ProfileService {

	private ProfileService() {
	}

	private static final ProfileService service = new ProfileService();

	public static ProfileService getInstance() {
		return service;
	}
	
	public List getProfileOptionList() throws Exception {
		return ProfileDAO.getInstance().getProfileOptionList();
	}

	public void updateProfiles(List poList) throws Exception{
		
		 ProfileDAO.getInstance().updateProfiles(poList);
	}

	public Map getProfile(String profileType) throws EPISException {
		Map profile = ProfileDAO.getInstance().getProfile(profileType);
		return profile;
	}
	
}
