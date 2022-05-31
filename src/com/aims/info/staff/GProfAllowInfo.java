/*
 * Created on Oct 21, 2009
 */
package com.aims.info.staff;

import java.util.List;

/**
 * @author srilakshmie
 */
public class GProfAllowInfo {
	private String gprofamtcd;
	private String payrollmonthid;
	private String cadrecd;
	private String cadrenm;
	private String gprofamount;
	private String gprofdatacd;
	private String empno;
	private String eoldays;
	private String gprofeolamount;
	private String usercd;
	private List cadreList;
	private List ecadreList;
	private List ncadreList;
	private String fyear;
	private String type;
	private String empname;
	private String monthyear;
	private String eolamt;
	private String gprofamtmonth;
	private String monthlydetail;
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	private String mondetcd;
	
	private String adjustments = "";
	private String adjop = "";
	
	
	
	
	
	
	
	
	/**
	 * @return Returns the adjop.
	 */
	public String getAdjop() {
		return adjop;
	}
	/**
	 * @param adjop The adjop to set.
	 */
	public void setAdjop(String adjop) {
		this.adjop = adjop;
	}
	/**
	 * @return Returns the adjustments.
	 */
	public String getAdjustments() {
		return adjustments;
	}
	/**
	 * @param adjustments The adjustments to set.
	 */
	public void setAdjustments(String adjustments) {
		this.adjustments = adjustments;
	}
	/**
	 * @return Returns the mondetcd.
	 */
	public String getMondetcd() {
		return mondetcd;
	}
	/**
	 * @param mondetcd The mondetcd to set.
	 */
	public void setMondetcd(String mondetcd) {
		this.mondetcd = mondetcd;
	}
	/**
	 * @return Returns the monthlydetail.
	 */
	public String getMonthlydetail() {
		return monthlydetail;
	}
	/**
	 * @param monthlydetail The monthlydetail to set.
	 */
	public void setMonthlydetail(String monthlydetail) {
		this.monthlydetail = monthlydetail;
	}
	/**
	 * @return Returns the gprofamtmonth.
	 */
	public String getGprofamtmonth() {
		return gprofamtmonth;
	}
	/**
	 * @param gprofamtmonth The gprofamtmonth to set.
	 */
	public void setGprofamtmonth(String gprofamtmonth) {
		this.gprofamtmonth = gprofamtmonth;
	}
	/**
	 * @return Returns the eolamt.
	 */
	public String getEolamt() {
		return eolamt;
	}
	/**
	 * @param eolamt The eolamt to set.
	 */
	public void setEolamt(String eolamt) {
		this.eolamt = eolamt;
	}
	public String getMonthyear() {
		return monthyear;
	}
	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the cadrenm.
	 */
	public String getCadrenm() {
		return cadrenm;
	}
	/**
	 * @param cadrenm The cadrenm to set.
	 */
	public void setCadrenm(String cadrenm) {
		this.cadrenm = cadrenm;
	}
	/**
	 * @return Returns the ecadreList.
	 */
	public List getEcadreList() {
		return ecadreList;
	}
	/**
	 * @param ecadreList The ecadreList to set.
	 */
	public void setEcadreList(List ecadreList) {
		this.ecadreList = ecadreList;
	}
	/**
	 * @return Returns the ncadreList.
	 */
	public List getNcadreList() {
		return ncadreList;
	}
	/**
	 * @param ncadreList The ncadreList to set.
	 */
	public void setNcadreList(List ncadreList) {
		this.ncadreList = ncadreList;
	}
	/**
	 * @return Returns the fyear.
	 */
	public String getFyear() {
		return fyear;
	}
	/**
	 * @param fyear The fyear to set.
	 */
	public void setFyear(String fyear) {
		this.fyear = fyear;
	}
	/**
	 * @return Returns the cadreList.
	 */
	public List getCadreList() {
		return cadreList;
	}
	/**
	 * @param cadreList The cadreList to set.
	 */
	public void setCadreList(List cadreList) {
		this.cadreList = cadreList;
	}
	/**
	 * @return Returns the cadrecd.
	 */
	public String getCadrecd() {
		return cadrecd;
	}
	/**
	 * @param cadrecd The cadrecd to set.
	 */
	public void setCadrecd(String cadrecd) {
		this.cadrecd = cadrecd;
	}
	/**
	 * @return Returns the empno.
	 */
	public String getEmpno() {
		return empno;
	}
	/**
	 * @param empno The empno to set.
	 */
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	/**
	 * @return Returns the eoldays.
	 */
	public String getEoldays() {
		return eoldays;
	}
	/**
	 * @param eoldays The eoldays to set.
	 */
	public void setEoldays(String eoldays) {
		this.eoldays = eoldays;
	}
	/**
	 * @return Returns the gprofamount.
	 */
	public String getGprofamount() {
		return gprofamount;
	}
	/**
	 * @param gprofamount The gprofamount to set.
	 */
	public void setGprofamount(String gprofamount) {
		this.gprofamount = gprofamount;
	}
	/**
	 * @return Returns the gprofamtcd.
	 */
	public String getGprofamtcd() {
		return gprofamtcd;
	}
	/**
	 * @param gprofamtcd The gprofamtcd to set.
	 */
	public void setGprofamtcd(String gprofamtcd) {
		this.gprofamtcd = gprofamtcd;
	}
	/**
	 * @return Returns the gprofdatacd.
	 */
	public String getGprofdatacd() {
		return gprofdatacd;
	}
	/**
	 * @param gprofdatacd The gprofdatacd to set.
	 */
	public void setGprofdatacd(String gprofdatacd) {
		this.gprofdatacd = gprofdatacd;
	}
	/**
	 * @return Returns the gprofeolamount.
	 */
	public String getGprofeolamount() {
		return gprofeolamount;
	}
	/**
	 * @param gprofeolamount The gprofeolamount to set.
	 */
	public void setGprofeolamount(String gprofeolamount) {
		this.gprofeolamount = gprofeolamount;
	}
	/**
	 * @return Returns the payrollmonthid.
	 */
	public String getPayrollmonthid() {
		return payrollmonthid;
	}
	/**
	 * @param payrollmonthid The payrollmonthid to set.
	 */
	public void setPayrollmonthid(String payrollmonthid) {
		this.payrollmonthid = payrollmonthid;
	}
	/**
	 * @return Returns the usercd.
	 */
	public String getUsercd() {
		return usercd;
	}
	/**
	 * @param usercd The usercd to set.
	 */
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
}
