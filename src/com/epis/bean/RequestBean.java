package com.epis.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epis.info.login.LoginInfo;

public class RequestBean {
	
	private String loginUserId="";
	private String loginUnitCode="";
	private String loginRegion="";
	private String loginProfile="";
	private String loginUserName="";
	private String loginUserDispName="";
	private String loginUserType="";
	private String loginUserDesignation="";
	private String loginUserAccType="";
	private String loginUserPrivilageStage="";
	private String loginUnitName="";
	public String getLoginUnitName() {
		return loginUnitName;
	}
	public void setLoginUnitName(String loginUnitName) {
		this.loginUnitName = loginUnitName;
	}
	public String getLoginUserPrivilageStage() {
		return loginUserPrivilageStage;
	}
	public void setLoginUserPrivilageStage(String loginUserPrivilageStage) {
		this.loginUserPrivilageStage = loginUserPrivilageStage;
	}
	public RequestBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLoginUserType() {
		return loginUserType;
	}
	public void setLoginUserType(String loginUserType) {
		this.loginUserType = loginUserType;
	}
	public RequestBean(HttpServletRequest request) {
		HttpSession session=request.getSession();
		LoginInfo loginInfo=(LoginInfo)session.getAttribute("user");
		this.loginUserId=loginInfo.getUserId();
		this.loginUserName=loginInfo.getUserName();
		this.loginUserType=loginInfo.getUserType();
		this.loginUnitCode=loginInfo.getUnitCd();		
		this.loginRegion=loginInfo.getRegion();
		this.loginProfile=loginInfo.getProfile();	
		this.loginUserDispName=loginInfo.getDisplayName();
		this.loginUserDesignation=loginInfo.getDesignation();
		this.loginUserAccType=loginInfo.getAccountType();
		this.loginUserPrivilageStage=loginInfo.getPrivilageStage();
		this.loginUnitName=loginInfo.getUnitName(); 
	}
	public String getLoginUserDispName() {
		return loginUserDispName;
	}
	public void setLoginUserDispName(String loginUserDispName) {
		this.loginUserDispName = loginUserDispName;
	}
	public String getLoginProfile() {
		return loginProfile;
	}
	public void setLoginProfile(String loginProfile) {
		this.loginProfile = loginProfile;
	}
	public String getLoginRegion() {
		return loginRegion;
	}
	public void setLoginRegion(String loginRegion) {
		this.loginRegion = loginRegion;
	}
	public String getLoginUnitCode() {
		return loginUnitCode;
	}
	public void setLoginUnitCode(String loginUnitCode) {
		this.loginUnitCode = loginUnitCode;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getLoginUserDesignation() {
		return loginUserDesignation;
	}
	public void setLoginUserDesignation(String loginUserDesignation) {
		this.loginUserDesignation = loginUserDesignation;
	}
	public String getLoginUserAccType() {
		return loginUserAccType;
	}
	public void setLoginUserAccType(String loginUserAccType) {
		this.loginUserAccType = loginUserAccType;
	}


}
