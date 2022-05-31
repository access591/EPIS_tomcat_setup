package com.aims.info.configuration;

import java.io.Serializable;

public class BudgetYearInfo implements Serializable{
	/**
	 * @return Returns the budgetYearCode.
	 */
	public String getBudgetYearCode() {
		return budgetYearCode;
	}
	/**
	 * @param budgetYearCode The budgetYearCode to set.
	 */
	public void setBudgetYearCode(String budgetYearCode) {
		this.budgetYearCode = budgetYearCode;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	private String budgetYearCode = "";
	private String fromDate = "";
	private String toDate = "";
	private String status;
	private String description = "";
	private String userid;
	/**
	 * @return Returns the userid.
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid The userid to set.
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
}