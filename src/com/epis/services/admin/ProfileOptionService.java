package com.epis.services.admin;

import java.util.List;
import com.epis.bean.admin.ProfileOptionBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.ProfileOptionDAO;


public class ProfileOptionService {

	private ProfileOptionService() {
	}

	private static final ProfileOptionService service = new ProfileOptionService();

	public static ProfileOptionService getInstance() {
		return service;
	}
	
	public List getOptionList() throws Exception {
		return ProfileOptionDAO.getInstance().getOptionList();
	}
	public List searchOption(String optionCode,String optionName) throws ServiceNotAvailableException, EPISException{
		return ProfileOptionDAO.getInstance().searchOption(optionCode,optionName);
	}
	public void saveOption(ProfileOptionBean obean) throws ServiceNotAvailableException, EPISException{
		ProfileOptionDAO.getInstance().saveOption(obean);
	}
	public void editOption(ProfileOptionBean obean) throws ServiceNotAvailableException, EPISException{
		ProfileOptionDAO.getInstance().editOption(obean);
	}
	public ProfileOptionBean findOption(String optionCode) throws ServiceNotAvailableException, EPISException {
		ProfileOptionBean obean =new ProfileOptionBean();
		obean = ProfileOptionDAO.getInstance().findOption(optionCode);
	return obean;
	}
	public void deleteOption(String optioncds) throws ServiceNotAvailableException, EPISException {
		ProfileOptionDAO.getInstance().deleteOption(optioncds);
	}
}
