package com.epis.bean.rpfc;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class SignatureMappingBean {
String type="",moduleName="",moduleCode="",screenName="",screenCode="",userID="",userName="",fromDate="",toDate="",dispMgrName="",station="",desgNRemarks="",activity="",signatureName="";
String[] screenPermissionInfo;
private List screenPermissionDetails;
FormFile signature;
public FormFile getSignature() {
	return signature;
}

public void setSignature(FormFile signature) {
	this.signature = signature;
}

public String getScreenCode() {
	return screenCode;
}

public void setScreenCode(String screenCode) {
	this.screenCode = screenCode;
}

public String getScreenName() {
	return screenName;
}

public void setScreenName(String screenName) {
	this.screenName = screenName;
}

public String getModuleCode() {
	return moduleCode;
}

public void setModuleCode(String moduleCode) {
	this.moduleCode = moduleCode;
}

public String getModuleName() {
	return moduleName;
}

public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}

  
public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

 
public String getFromDate() {
	return fromDate;
}

public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}

public String getToDate() {
	return toDate;
}

public void setToDate(String toDate) {
	this.toDate = toDate;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getDispMgrName() {
	return dispMgrName;
}

public void setDispMgrName(String dispMgrName) {
	this.dispMgrName = dispMgrName;
}

public String getActivity() {
	return activity;
}

public void setActivity(String activity) {
	this.activity = activity;
}

public String getStation() {
	return station;
}

public void setStation(String station) {
	this.station = station;
}

public String getDesgNRemarks() {
	return desgNRemarks;
}

public void setDesgNRemarks(String desgNRemarks) {
	this.desgNRemarks = desgNRemarks;
}

public String getSignatureName() {
	return signatureName;
}

public void setSignatureName(String signatureName) {
	this.signatureName = signatureName;
}

public String getUserID() {
	return userID;
}

public void setUserID(String userID) {
	this.userID = userID;
}

public String[] getScreenPermissionInfo() {
	return screenPermissionInfo;
}

public void setScreenPermissionInfo(String[] screenPermissionInfo) {
	this.screenPermissionInfo = screenPermissionInfo;
}

public List getScreenPermissionDetails() {
	return screenPermissionDetails;
}

public void setScreenPermissionDetails(List screenPermissionDetails) {
	this.screenPermissionDetails = screenPermissionDetails;
}
}
