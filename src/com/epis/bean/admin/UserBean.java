package com.epis.bean.admin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.epis.bean.RequestBean;
import com.epis.utilities.StringUtility;

public class UserBean extends RequestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5747183602344177058L;
	private String userId;
	private String employeeId;
	private String userType;
	private String profileType;
	private String unit;
	private String status;
	private String userName;
	private String email;
	private String[] modules;
	private String expireDate;
	private String gridLength;
	private String createdOn;
	private String deleteFlag;
	private String passwordChangeFlag; 
	private String unitName; 
	private String roleCd; 
	private FormFile esignatory;
	private String esignatoryName;
	private String moduleNames;
	private String displayName;
	private String designation;
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public FormFile getEsignatory() {
		return esignatory;
	}
	public String getModuleNames() {
		return moduleNames;
	}
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}
	public void setEsignatory(FormFile esignatory) {
		this.esignatory = esignatory;
	}
	public UserBean(HttpServletRequest request){
		super(request);
		}
	public UserBean(String userId,String employeeId,String userType,String profileType,
			String unit,String status,String userName,String email,String[] modules,
			String expiryDate,String gridLength,String createdOn,String deleteFlag,
			String passwordChangeFlag,String unitName){
		this( userId, employeeId, userType, profileType,unit, status, userName, email,modules,
				 expiryDate, gridLength, createdOn, deleteFlag, passwordChangeFlag, unitName, "");		
	}
	
	public UserBean(String userId,String employeeId,String userType,String profileType,
			String unit,String status,String userName,String email,String[] modules,
			String expiryDate,String gridLength,String createdOn,String deleteFlag,
			String passwordChangeFlag,String unitName,String roleCd){
		this( userId, employeeId, userType, profileType,unit, status, userName, email,modules,
				 expiryDate, gridLength, createdOn, deleteFlag, passwordChangeFlag, unitName, roleCd,"");
	}
	public UserBean(String userId,String employeeId,String userType,String profileType,
			String unit,String status,String userName,String email,String[] modules,
			String expiryDate,String gridLength,String createdOn,String deleteFlag,
			String passwordChangeFlag,String unitName,String roleCd,String displayName){
		this.userId = userId;
		this.employeeId = employeeId;
		this.userType = userType;
		this.profileType = profileType;
		this.unit = unit;
		this.status = status;
		this.userName = userName;
		this.modules = modules;
		this.expireDate = expiryDate;
		this.gridLength = gridLength;
		this.userId = userId;
		this.createdOn = createdOn;
		this.deleteFlag = deleteFlag;
		this.passwordChangeFlag = passwordChangeFlag;
		this.unitName = unitName;
		this.email=email;		
		this.roleCd =roleCd;
		this.displayName=displayName;
	}
	
	public UserBean(){
		
	}

	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getGridLength() {
		return gridLength;
	}
	public void setGridLength(String gridLength) {
		this.gridLength = gridLength;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getModules() {
		return modules;
	}
	public void setModules(String[] modules) {
		this.modules = modules;
	}
	public String getPasswordChangeFlag() {
		return passwordChangeFlag;
	}
	public void setPasswordChangeFlag(String passwordChangeFlag) {
		this.passwordChangeFlag = passwordChangeFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer("UserId : ");
		buf.append(this.userId).append(" # EmployeeId : ").append(this.employeeId).append(" # UserType : ");
		buf.append(this.userType).append(" # ProfileType : ").append(this.profileType).append(" # Unit : ");
		buf.append(this.unit).append(" # Status : ").append(this.status).append(" # UserName : ");
		buf.append(this.userName).append(" # Modules : ").append(StringUtility.arrayToString(this.modules,"|")).append(" # ExpiryDate : ");
		buf.append(this.expireDate).append(" # GridLength : ").append(this.gridLength).append(" # CreatedOn : ");
		buf.append(this.createdOn).append(" # DeleteFlag : ").append(this.deleteFlag).append(" # PasswordChangeFlag : ");
		buf.append(this.passwordChangeFlag).append(" # UnitName : ").append(this.unitName);
		return buf.toString();
	}
	
	public boolean equals(Object obj){
		boolean bool = false;
		if (obj instanceof Bean && ((UserBean)obj).getUserId().equals(this.userId) ) {
			return true;
		}
		return bool;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getEsignatoryName() {
		return esignatoryName;
	}
	public void setEsignatoryName(String esignatoryName) {
		this.esignatoryName = esignatoryName;
	}
}
