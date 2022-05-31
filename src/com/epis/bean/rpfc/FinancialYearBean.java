/**
 * File       : FinancialYearBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;

public class FinancialYearBean {
	
	String month="",monthnumber="",fromDate="",toDate="",description="",remarks="";
	int financialId=0;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFinancialId() {
		return financialId;
	}
	public void setFinancialId(int financialId) {
		this.financialId = financialId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getMonthnumber() {
		return monthnumber;
	}
	public void setMonthnumber(String monthnumber) {
		this.monthnumber = monthnumber;
	}

}
