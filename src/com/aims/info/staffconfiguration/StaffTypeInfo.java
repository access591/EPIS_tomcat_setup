package com.aims.info.staffconfiguration;

public class StaffTypeInfo {
	private Integer staffTypeCd;
	private String staffTypeName;
	private String staffTypeDesc;
	private String deletedFlag;
	private String status;

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
	public String getStaffTypeName(){
		return this.staffTypeName;
	}
	public void setStaffTypeName(String stafftypenm){
		this.staffTypeName = stafftypenm;
	}
	public String getStaffTypeDesc(){
		return this.staffTypeDesc;
	}
	public void setStaffTypeDesc(String stafftypedesc){
		this.staffTypeDesc = stafftypedesc;
	}
	public String getDeletedFlag(){
		return this.deletedFlag;
	}
	public void setDeletedFlag(String deletedflag){
		this.deletedFlag = deletedflag;
	}
	public Integer getStaffTypeCd() {
		return staffTypeCd;
	}
	public void setStaffTypeCd(Integer staffTypeCd) {
		this.staffTypeCd = staffTypeCd;
	}
}
