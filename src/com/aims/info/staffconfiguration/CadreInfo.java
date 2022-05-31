package com.aims.info.staffconfiguration;

public class CadreInfo {

	public String cadreCd;
	public String cadreName;
	public String cadreDesc;
	public String status;
	public Integer staffCategoryCd;
	public Integer groupCd;
	public String groupName;
	public String staffCategoryName;
	public String usercd;
	
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	public String getCadreCd() {
		return cadreCd;
	}
	public void setCadreCd(String cadreCd) {
		this.cadreCd = cadreCd;
	}
	public String getCadreDesc() {
		return cadreDesc;
	}
	public void setCadreDesc(String cadreDesc) {
		this.cadreDesc = cadreDesc;
	}
	public String getCadreName() {
		return cadreName;
	}
	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}
	
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getGroupCd() {
		return groupCd;
	}
	public void setGroupCd(Integer groupCd) {
		this.groupCd = groupCd;
	}
	public Integer getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(Integer staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStaffCategoryName() {
		return staffCategoryName;
	}
	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
	}
	
}
