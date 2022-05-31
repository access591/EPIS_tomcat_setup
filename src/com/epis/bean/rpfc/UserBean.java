/**
  * File       : UserBean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.rpfc;

import com.epis.utilities.Log;

public class UserBean {

	Log log=new Log(UserBean.class);

	String username="",employee="",primaryairport="",department="";
	String designation="",dob="",pensionno="",email="",primarymodule="",secondarymodule="";
	String usertype="",expirydate="",division="",gridlength="",oldpwd="",newpwd="",airport="",groupname="",remarks="",groupselect="";
	int userid=0,groupid=0,regionId=0;
	String aaiCategory="",region="",loginTime="",computerName="",loginFromDate="",loginToDate="",phoneNo="",passwordFlag="";
 String passwordChangeFlag="";
    public void setUserId(int userid) {
		this.userid = userid;	
	}
	public int getUserId() {
		return userid;
	}
	
	public void setUserName(String username) {
		this.username = username;	
	}
	public String getUserName() {
		return username;
	}

	public void setEmployee(String employee) {
		this.employee = employee;		
	}
	public String getEmployee() {
		return employee;
	}

	public void setPrimaryAirport(String primaryairport) {
		this.primaryairport = primaryairport;		
	}
	public String getPrimaryAirport() {
		return primaryairport;
	}
	public void setDepartment(String department) {
		this.department = department;		
	}
	public String getDepartment() {
		return department;
	}

	public void setDesignation(String designation) {
		this.designation = designation;		
	}
	public String getDesignation() {
		return designation;
	}
	public void setDOB(String dob) {
		this.dob = dob;	
	}
	public String getAaiCategory() {
		return aaiCategory;
	}
	public void setAaiCategory(String aaiCategory) {
		this.aaiCategory = aaiCategory;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDOB() {
		return dob;
	}
	public void setPensionNo(String pensionno) {
		this.pensionno = pensionno;		
	}
	public String getPensionNo() {
		return pensionno;
	}

	public void setEmail(String email) {
		this.email = email;		
	}
	public String getEmail() {
		return email;
	}

	public void setPrimaryModule(String primarymodule) {
		this.primarymodule = primarymodule;		
	}
	public String getPrimaryModule() {
		return primarymodule;
	}
	
	public void setSecondaryModule(String secondarymodule) {
		this.secondarymodule = secondarymodule;	
	}
	public String getSecondaryModule() {
		return secondarymodule;
	}
	
	public void setUserType(String usertype) {
		this.usertype = usertype;	
	}
	public String getUserType() {
		return usertype;
	}

	public void setExpiryDate(String expirydate) {
		this.expirydate = expirydate;		
	}
	public String getExpiryDate() {
		return expirydate;
	}
	public void setDivision(String division) {
		this.division = division;		
	}
	public String getDivision() {
		return division;
	}
	public void setGridLength(String gridlength) {
		this.gridlength = gridlength;	
	}
	public String getGridLength() {
		return gridlength;
	}

	public void setAirport(String airport) {
		this.airport = airport;		
	}
	public String getAirport() {
		return airport;
	}

	public void setOldPwd(String oldpwd) {
		this.oldpwd = oldpwd;		
	}
	public String getOldPwd() {
		return oldpwd;
	}

	public void setNewPwd(String newpwd) {
		this.newpwd = newpwd;		
	}
	public String getNewPwd() {
		return newpwd;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;		
	}
	public String getRemarks() {
		return remarks;
	}

	 public void setGroupId(int groupid) {
		this.groupid = groupid;	
	}
	public int getGroupId() {
		return groupid;
	}

	public void setGroupName(String groupname) {
		this.groupname = groupname;		
	}
	public String getGroupName() {
		return groupname;
	}

	public void setSelectGroup(String groupselect) {
		this.groupselect = groupselect;		
	}
	public String getSelectGroup() {
		return groupselect;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getLoginFromDate() {
		return loginFromDate;
	}
	public void setLoginFromDate(String loginFromDate) {
		this.loginFromDate = loginFromDate;
	}
	public String getLoginToDate() {
		return loginToDate;
	}
	public void setLoginToDate(String loginToDate) {
		this.loginToDate = loginToDate;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPasswordFlag() {
		return passwordFlag;
	}
	public void setPasswordFlag(String passwordFlag) {
		this.passwordFlag = passwordFlag;
	}
	public String getPasswordChangeFlag() {
		return passwordChangeFlag;
	}
	public void setPasswordChangeFlag(String passwordChangeFlag) {
		this.passwordChangeFlag = passwordChangeFlag;
	}
	
	

	
}
