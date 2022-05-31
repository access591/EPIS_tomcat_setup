/*
 * Created on Oct 20, 2009
 */
package com.aims.info.staff;

import java.util.List;

/**
 * @author srilakshmie
 */
public class PMNRFundInfo {
	private String empno;
	private String emplnumber;
	private String empname;
	private String emppmnrfundcd;
	private String payrollmonthid;
	private String noofdays;
	private String pmnrfund;
	private String usercd;
	private String staffCategoryCd;
	private String basicSal;
	private List employeeList;
	private String singledayamount;
	private String emppmnrfunddetcd;
	
	private String staffctgrynm = "";
	private String payrollmonthnm = "";
	
	private String isEdit = "";
	
	
	
	private int length;
	
	/**
	 * @return Returns the employeeList.
	 */
	public List getEmployeeList() {
		return employeeList;
	}
	/**
	 * @param employeeList The employeeList to set.
	 */
	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}
	/**
	 * @return Returns the basicSal.
	 */
	public String getBasicSal() {
		return basicSal;
	}
	/**
	 * @param basicSal The basicSal to set.
	 */
	public void setBasicSal(String basicSal) {
		this.basicSal = basicSal;
	}
	/**
	 * @return Returns the empname.
	 */
	public String getEmpname() {
		return empname;
	}
	/**
	 * @param empname The empname to set.
	 */
	public void setEmpname(String empname) {
		this.empname = empname;
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
	/**
	 * @return Returns the emppmnrfundcd.
	 */
	public String getEmppmnrfundcd() {
		return emppmnrfundcd;
	}
	/**
	 * @param emppmnrfundcd The emppmnrfundcd to set.
	 */
	public void setEmppmnrfundcd(String emppmnrfundcd) {
		this.emppmnrfundcd = emppmnrfundcd;
	}
	/**
	 * @return Returns the noofdays.
	 */
	public String getNoofdays() {
		return noofdays;
	}
	/**
	 * @param noofdays The noofdays to set.
	 */
	public void setNoofdays(String noofdays) {
		this.noofdays = noofdays;
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
	 * @return Returns the pmnrfund.
	 */
	public String getPmnrfund() {
		return pmnrfund;
	}
	/**
	 * @param pmnrfund The pmnrfund to set.
	 */
	public void setPmnrfund(String pmnrfund) {
		this.pmnrfund = pmnrfund;
	}
	/**
	 * @return Returns the staffCategoryCd.
	 */
	public String getStaffCategoryCd() {
		return staffCategoryCd;
	}
	/**
	 * @param staffCategoryCd The staffCategoryCd to set.
	 */
	public void setStaffCategoryCd(String staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
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
	
	/**
	 * @return Returns the staffctgrynm.
	 */
	public String getStaffctgrynm() {
		return staffctgrynm;
	}
	/**
	 * @param staffctgrynm The staffctgrynm to set.
	 */
	public void setStaffctgrynm(String staffctgrynm) {
		this.staffctgrynm = staffctgrynm;
	}
	
	/**
	 * @return Returns the payrollmonthnm.
	 */
	public String getPayrollmonthnm() {
		return payrollmonthnm;
	}
	/**
	 * @param payrollmonthnm The payrollmonthnm to set.
	 */
	public void setPayrollmonthnm(String payrollmonthnm) {
		this.payrollmonthnm = payrollmonthnm;
	}
	
	/**
	 * @return Returns the emppmnrfunddetcd.
	 */
	public String getEmppmnrfunddetcd() {
		return emppmnrfunddetcd;
	}
	/**
	 * @param emppmnrfunddetcd The emppmnrfunddetcd to set.
	 */
	public void setEmppmnrfunddetcd(String emppmnrfunddetcd) {
		this.emppmnrfunddetcd = emppmnrfunddetcd;
	}
	
	/**
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * @return Returns the singledayamount.
	 */
	public String getSingledayamount() {
		return singledayamount;
	}
	/**
	 * @param singledayamount The singledayamount to set.
	 */
	public void setSingledayamount(String singledayamount) {
		this.singledayamount = singledayamount;
	}
	
	/**
	 * @return Returns the isEdit.
	 */
	public String getIsEdit() {
		return isEdit;
	}
	/**
	 * @param isEdit The isEdit to set.
	 */
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
}
