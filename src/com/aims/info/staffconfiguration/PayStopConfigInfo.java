package com.aims.info.staffconfiguration;

import java.util.List;

public class PayStopConfigInfo  implements java.io.Serializable {
    private int configid;
    private String staffCategoryName;
    private int staffCategoryCd;
    private String earnings;
    private String deductions;
    private String perks;
    private String recoveries;
    private String earningCd;
    private String status;
    private String userCd;
    private List earnList;
    private List dedList;
    private List perkList;
    private List recList;
    
	public int getConfigid() {
		return configid;
	}
	public void setConfigid(int configid) {
		this.configid = configid;
	}
	public String getDeductions() {
		return deductions;
	}
	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}
	public String getEarningCd() {
		return earningCd;
	}
	public void setEarningCd(String earningCd) {
		this.earningCd = earningCd;
	}
	public String getEarnings() {
		return earnings;
	}
	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}
	public String getPerks() {
		return perks;
	}
	public void setPerks(String perks) {
		this.perks = perks;
	}
	public String getRecoveries() {
		return recoveries;
	}
	public void setRecoveries(String recoveries) {
		this.recoveries = recoveries;
	}
	public int getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(int staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	public String getStaffCategoryName() {
		return staffCategoryName;
	}
	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	
	/**
	 * @return Returns the dedList.
	 */
	public List getDedList() {
		return dedList;
	}
	/**
	 * @param dedList The dedList to set.
	 */
	public void setDedList(List dedList) {
		this.dedList = dedList;
	}
	/**
	 * @return Returns the earnList.
	 */
	public List getEarnList() {
		return earnList;
	}
	/**
	 * @param earnList The earnList to set.
	 */
	public void setEarnList(List earnList) {
		this.earnList = earnList;
	}
	/**
	 * @return Returns the perkList.
	 */
	public List getPerkList() {
		return perkList;
	}
	/**
	 * @param perkList The perkList to set.
	 */
	public void setPerkList(List perkList) {
		this.perkList = perkList;
	}
	/**
	 * @return Returns the recList.
	 */
	public List getRecList() {
		return recList;
	}
	/**
	 * @param recList The recList to set.
	 */
	public void setRecList(List recList) {
		this.recList = recList;
	}
}
