package com.aims.info.staffconfiguration;

public class StaffCategoryInfo {
	private Integer staffCategoryCd;
	private String staffCategoryName;
	private String staffCategoryDesc;
	private String status;
	private String usercd;
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	/**
	 * @return Returns the staffCategoryCd.
	 */
	public Integer getStaffCategoryCd() {
		return staffCategoryCd;
	}
	/**
	 * @param staffCategoryCd The staffCategoryCd to set.
	 */
	public void setStaffCategoryCd(Integer staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	/**
	 * @return Returns the staffCategoryDesc.
	 */
	public String getStaffCategoryDesc() {
		return staffCategoryDesc;
	}
	/**
	 * @param staffCategoryDesc The staffCategoryDesc to set.
	 */
	public void setStaffCategoryDesc(String staffCategoryDesc) {
		this.staffCategoryDesc = staffCategoryDesc;
	}
	/**
	 * @return Returns the staffCategoryName.
	 */
	public String getStaffCategoryName() {
		return staffCategoryName;
	}
	/**
	 * @param staffCategoryName The staffCategoryName to set.
	 */
	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
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
	
	

}
