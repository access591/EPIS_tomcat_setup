/*
 * Created on Jul 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.payrollprocess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author subbaraod
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JournalVoucherInfo implements Serializable{
	
	private String discipline = "";
	private String disciplineCd = "";
	private String staffcategory= "";
	private String staffcategoryCd= "";
	private String month = "";
	private List earningList = new ArrayList();
	private double earnTotal = 0.0;
	private List deductionList = new ArrayList();
	private double dedTotal = 0.0;
	/**
	 * @return Returns the dedTotal.
	 */
	public double getDedTotal() {
		return dedTotal;
	}
	/**
	 * @param dedTotal The dedTotal to set.
	 */
	public void setDedTotal(double dedTotal) {
		this.dedTotal = dedTotal;
	}
	/**
	 * @return Returns the deductionList.
	 */
	public List getDeductionList() {
		return deductionList;
	}
	/**
	 * @param deductionList The deductionList to set.
	 */
	public void setDeductionList(List deductionList) {
		this.deductionList = deductionList;
	}
	/**
	 * @return Returns the discipline.
	 */
	public String getDiscipline() {
		return discipline;
	}
	/**
	 * @param discipline The discipline to set.
	 */
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	/**
	 * @return Returns the disciplineCd.
	 */
	public String getDisciplineCd() {
		return disciplineCd;
	}
	/**
	 * @param disciplineCd The disciplineCd to set.
	 */
	public void setDisciplineCd(String disciplineCd) {
		this.disciplineCd = disciplineCd;
	}
	/**
	 * @return Returns the earningList.
	 */
	public List getEarningList() {
		return earningList;
	}
	/**
	 * @param earningList The earningList to set.
	 */
	public void setEarningList(List earningList) {
		this.earningList = earningList;
	}
	/**
	 * @return Returns the earnTotal.
	 */
	public double getEarnTotal() {
		return earnTotal;
	}
	/**
	 * @param earnTotal The earnTotal to set.
	 */
	public void setEarnTotal(double earnTotal) {
		this.earnTotal = earnTotal;
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
	 * @return Returns the staffcategory.
	 */
	public String getStaffcategory() {
		return staffcategory;
	}
	/**
	 * @param staffcategory The staffcategory to set.
	 */
	public void setStaffcategory(String staffcategory) {
		this.staffcategory = staffcategory;
	}
	/**
	 * @return Returns the staffcategoryCd.
	 */
	public String getStaffcategoryCd() {
		return staffcategoryCd;
	}
	/**
	 * @param staffcategoryCd The staffcategoryCd to set.
	 */
	public void setStaffcategoryCd(String staffcategoryCd) {
		this.staffcategoryCd = staffcategoryCd;
	}
	

}
