package com.epis.services;

import com.epis.info.login.LoginInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.login.LoginDao;


public class LoginService {
	
	private LoginService(){
		
	}
	private static LoginService loginServiceInstance=new LoginService();
	public static LoginService getInstance(){
		return loginServiceInstance;
	}
	public LoginInfo loginValidation(String userId,String password,String loginType) throws Exception {
		return LoginDao.getInstance().loginValidation(userId,password,loginType);
	}
	public void changePassword(LoginInfo loginInfo)throws EPISException{
		LoginDao.getInstance().changePassword(loginInfo);
	}

}
