
package com.epis.services.admin;

import java.util.List;
import com.epis.bean.admin.RoleBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.RoleDAO;


public class RoleService {

	private RoleService() {
	}

	private static final RoleService service = new RoleService();

	public static RoleService getInstance() {
		return service;
	}
	
	/*public List getSMList() throws Exception {
		return SubModuleDAO.getInstance().getSMList();
	}
	public List getModuleList()throws Exception {
		return SubModuleDAO.getInstance().getModuleList();
	}*/
	public List searchRole(String roleName) throws ServiceNotAvailableException, EPISException{
		return RoleDAO.getInstance().searchRole(roleName);
	}
	public void saveRole(RoleBean bean) throws ServiceNotAvailableException, EPISException{
		RoleDAO.getInstance().saveRole(bean);
		
	}
	public void editRole(RoleBean bean) throws ServiceNotAvailableException, EPISException{
		RoleDAO.getInstance().editRole(bean);
	}
	public RoleBean findRole(String roleCd) throws ServiceNotAvailableException, EPISException {
		RoleBean bean =new RoleBean();
		bean = RoleDAO.getInstance().findRole(roleCd);
	return bean;
	}
	public void deleteRole(String roleCd) throws ServiceNotAvailableException, EPISException {
		RoleDAO.getInstance().deleteRole(roleCd);
	}

	public List getRoles(String userId) throws ServiceNotAvailableException, EPISException {
		
		return RoleDAO.getInstance().getRoles(userId);
	}
}
