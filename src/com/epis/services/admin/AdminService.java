package com.epis.services.admin;

import java.util.List;

import com.epis.dao.admin.AdminDAO;
import com.epis.info.login.LoginInfo;


public class AdminService {

	private AdminService() {
	}

	private static final AdminService service = new AdminService();

	public static AdminService getInstance() {
		return service;
	}
	
	public List getUserList() throws Exception {
		return AdminDAO.getInstance().getUserList();
	}
	public void resetPassword(LoginInfo loginInfo) throws Exception{
		AdminDAO.getInstance().resetPassword(loginInfo);
	}
	
}
