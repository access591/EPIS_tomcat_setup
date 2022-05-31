package com.epis.services.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import com.epis.bean.admin.UserBean;
import com.epis.dao.admin.UserDAO;

public class UserService {

	private UserService() {
	}

	private static final UserService service = new UserService();

	public static UserService getInstance() {
		return service;
	}
	
	public List getUserList(UserBean user,String userId) throws Exception {
		return UserDAO.getInstance().getUserList(user,userId);
	}
	
	public List getUserList(String userid) throws Exception {
		return UserDAO.getInstance().getUserList(userid);
	}

	public void delete(String userIds) throws Exception {
		UserDAO.getInstance().delete(userIds);		
	}
	
	public void add(UserBean user,String UserID) throws Exception {
		UserDAO.getInstance().add(user,UserID);
	}

	public List getModuleList() throws Exception {
		return UserDAO.getInstance().getModuleList();
	}
	public List getModuleList(String modules) throws Exception {
		return UserDAO.getInstance().getModuleList(modules);
	}
	public List getSubModuleList(String module) throws Exception {
		return UserDAO.getInstance().getSubModuleList(module);
	}
	public List getScreensList(String submodule,String userId) throws Exception {
		return UserDAO.getInstance().getScreensList(submodule,userId);
	}
	public Map getAllScreenCodes() throws Exception {
		return UserDAO.getInstance().getAllScreenCodes();
	}
	public UserBean getUser(String userId) throws Exception {
		return UserDAO.getInstance().getUser(userId);
	}

	public void edit(UserBean user,String UserID) throws Exception {
		UserDAO.getInstance().edit(user,UserID);
	}

	public void getImage( String path, String userName) throws Exception {
		UserDAO.getInstance().getImage(path,userName);
	}
	
	public UserBean getuserAccount(String userId) throws Exception {
		return UserDAO.getInstance().getuserAccount(userId);
	}
}
