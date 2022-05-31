
/*
  * File       : MonthlyPayrollInfo
  * Date       : 09/20/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.payrollprocess;

public class MonthlyPayrollInfo {
	
	public int payrollmonthid;
	public String payrollmonthnm;
	public String payrollyear;
	public java.sql.Date payrollfromdt;
	public java.sql.Date payrolltodt;
	public String payrollstatus;
	public String deleteflag;
	public String fyearcd;
	
	public String definedGprof; // used in GprofDAO tells whether gprof is defined for payrollmonthid
	public String definedMonthlyIT; // used in StaffConfigurationDAO tells whether IT is defined for payrollmonthid
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	
	public java.sql.Date getPayrollfromdt() {
		return payrollfromdt;
	}
	public void setPayrollfromdt(java.sql.Date payrollfromdt) {
		this.payrollfromdt = payrollfromdt;
	}
	public void setPayrolltodt(java.sql.Date payrolltodt) {
		this.payrolltodt = payrolltodt;
	}
	public int getPayrollmonthid() {
		return payrollmonthid;
	}
	public void setPayrollmonthid(int payrollmonthid) {
		this.payrollmonthid = payrollmonthid;
	}
	public String getPayrollmonthnm() {
		return payrollmonthnm;
	}
	public void setPayrollmonthnm(String payrollmonthnm) {
		this.payrollmonthnm = payrollmonthnm;
	}
	public String getPayrollstatus() {
		return payrollstatus;
	}
	public void setPayrollstatus(String payrollstatus) {
		this.payrollstatus = payrollstatus;
	}
	
	public java.sql.Date getPayrolltodt() {
		return payrolltodt;
	}
	public String getPayrollyear() {
		return payrollyear;
	}
	public void setPayrollyear(String payrollyear) {
		this.payrollyear = payrollyear;
	}
	

	
	/**
	 * @return Returns the definedGprof.
	 */
	public String getDefinedGprof() {
		return definedGprof;
	}
	/**
	 * @param definedGprof The definedGprof to set.
	 */
	public void setDefinedGprof(String definedGprof) {
		this.definedGprof = definedGprof;
	}
	public String getFyearcd() {
		return fyearcd;
	}
	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}
	public String getDefinedMonthlyIT() {
		return definedMonthlyIT;
	}
	public void setDefinedMonthlyIT(String definedMonthlyIT) {
		this.definedMonthlyIT = definedMonthlyIT;
	}
}
