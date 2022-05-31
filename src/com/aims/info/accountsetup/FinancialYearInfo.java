 /**
  * File       : FinancialYearInfo.java
  * Date       : 08/28/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.accountsetup;

import java.io.Serializable;
import java.sql.Date;

public class FinancialYearInfo implements Serializable {

	private String finyearCd;
	private String finyearName;
	private String finyearDesc;
	private String fromDate;
	private String toDate;
	public String status;
	public String usercd;
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
	 * @return Returns the finyearDesc.
	 */
	public String getFinyearDesc() {
		return finyearDesc;
	}
	/**
	 * @param finyearDesc The finyearDesc to set.
	 */
	public void setFinyearDesc(String finyearDesc) {
		this.finyearDesc = finyearDesc;
	}
	/**
	 * @return Returns the finyearName.
	 */
	public String getFinyearName() {
		return finyearName;
	}
	/**
	 * @param finyearName The finyearName to set.
	 */
	public void setFinyearName(String finyearName) {
		this.finyearName = finyearName;
	}
	
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
}
