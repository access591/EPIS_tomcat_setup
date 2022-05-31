package com.aims.info.staffconfiguration;

import java.util.List;

public class EmployeeIncomeTaxInfo {
	
	private String empincometaxcd;
	private String empincometaxdetcd;
	private String empno;
	private String emplnumber;
	private String empname;
	private String fyearcd;
	private String monthId;
	private Double taxAmt;
	private Double ptax;
	private String userCd;
	private String type;
	private String staffCategoryCd;
	private String staffCategoryName;
	private List employeeList;
	public List getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}
	
	/**
	 * @return Returns the ptax.
	 */
	public Double getPtax() {
		return ptax;
	}
	/**
	 * @param ptax The ptax to set.
	 */
	public void setPtax(Double ptax) {
		this.ptax = ptax;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getEmpincometaxcd() {
		return empincometaxcd;
	}
	public void setEmpincometaxcd(String empincometaxcd) {
		this.empincometaxcd = empincometaxcd;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	
	/**
	 * @return Returns the emplnumber.
	 */
	public String getEmplnumber() {
		return emplnumber;
	}
	/**
	 * @param emplnumber The emplnumber to set.
	 */
	public void setEmplnumber(String emplnumber) {
		this.emplnumber = emplnumber;
	}
	public String getFyearcd() {
		return fyearcd;
	}
	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public Double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}
	public String getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(String staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	public String getStaffCategoryName() {
		return staffCategoryName;
	}
	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
	}
	public String getEmpincometaxdetcd() {
		return empincometaxdetcd;
	}
	public void setEmpincometaxdetcd(String empincometaxdetcd) {
		this.empincometaxdetcd = empincometaxdetcd;
	}
	

}
