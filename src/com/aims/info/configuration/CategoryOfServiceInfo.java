
package com.aims.info.configuration;

import java.sql.Date;

/**
 * @author naveenk
 *
 */

public class CategoryOfServiceInfo {
	
	private String categoryCd;
	
	private String categoryName;
	
	private String tds;
	
	private String vat;
	
	private String fromDate;
	
	private String toDate;
	
	private String status;
	
	private String userCd;
	
	private String mode;

	/**
	 * @return Returns the categoryCd.
	 */
	public String getCategoryCd() {
		return categoryCd;
	}

	/**
	 * @param categoryCd The categoryCd to set.
	 */
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	/**
	 * @return Returns the categoryName.
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName The categoryName to set.
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * @return Returns the userCd.
	 */
	public String getUserCd() {
		return userCd;
	}

	/**
	 * @param userCd The userCd to set.
	 */
	public void setUserCd(String userCd) {
		this.userCd = userCd;
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

	/**
	 * @return Returns the tds.
	 */
	public String getTds() {
		return tds;
	}

	/**
	 * @param tds The tds to set.
	 */
	public void setTds(String tds) {
		this.tds = tds;
	}

	/**
	 * @return Returns the vat.
	 */
	public String getVat() {
		return vat;
	}

	/**
	 * @param vat The vat to set.
	 */
	public void setVat(String vat) {
		this.vat = vat;
	}

}
