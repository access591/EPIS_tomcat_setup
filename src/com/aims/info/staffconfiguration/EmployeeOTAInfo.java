package com.aims.info.staffconfiguration;

public class EmployeeOTAInfo {
	
	private String finyearCd="";
	private String month="";
	private String paybillcd="";
	private String[] payDetails;
	private String recordID="";
	private String usercd="";
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	/**
	 * @return Returns the finyearCd.
	 */
	public String getFinyearCd() {
		return finyearCd;
	}
	/**
	 * @param finyearCd The finyearCd to set.
	 */
	public void setFinyearCd(String finyearCd) {
		this.finyearCd = finyearCd;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return Returns the optionDetails.
	 */
	
	/**
	 * @return Returns the paybillcd.
	 */
	public String getPaybillcd() {
		return paybillcd;
	}
	/**
	 * @param paybillcd The paybillcd to set.
	 */
	public void setPaybillcd(String paybillcd) {
		this.paybillcd = paybillcd;
	}
	/**
	 * @return Returns the recordID.
	 */
	public String getRecordID() {
		return recordID;
	}
	/**
	 * @param recordID The recordID to set.
	 */
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	/**
	 * @return Returns the payDetails.
	 */
	public String[] getPayDetails() {
		return payDetails;
	}
	/**
	 * @param payDetails The payDetails to set.
	 */
	public void setPayDetails(String[] payDetails) {
		this.payDetails = payDetails;
	}

}
