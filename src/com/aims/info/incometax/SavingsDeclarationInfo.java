/*
 * Created on Sep 15, 2009
 */
package com.aims.info.incometax;

import java.util.List;

/**
 * @author Srilakshmi E
 */
public class SavingsDeclarationInfo {
	private String savingsName;
	private String savDeclCd;
	private String finyearcd;
	private String empno;
	private String totalSaving;
	private String savDecDetCd;
	private String savingsCd;
	private String savingAmt;
	private String[] detArray;
	private String empName;
	private String name;
	private String finYearName;
	private List savingDt;
	private List nscDt;
	private String percent;
	private String nscAmount;
	private String interestamt;
	private String status;
	private String usercd;
	private String paybillcd;
	
	public String getPaybillcd() {
		return paybillcd;
	}
	public void setPaybillcd(String paybillcd) {
		this.paybillcd = paybillcd;
	}
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInterestamt() {
		return interestamt;
	}
	public void setInterestamt(String interestamt) {
		this.interestamt = interestamt;
	}
	public String getNscAmount() {
		return nscAmount;
	}
	public void setNscAmount(String nscAmount) {
		this.nscAmount = nscAmount;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return Returns the detArray.
	 */
	public String[] getDetArray() {
		return detArray;
	}
	/**
	 * @param detArray The detArray to set.
	 */
	public void setDetArray(String[] detArray) {
		this.detArray = detArray;
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
	 * @return Returns the finyearcd.
	 */
	public String getFinyearcd() {
		return finyearcd;
	}
	/**
	 * @param finyearcd The finyearcd to set.
	 */
	public void setFinyearcd(String finyearcd) {
		this.finyearcd = finyearcd;
	}
	/**
	 * @return Returns the savDecDetCd.
	 */
	public String getSavDecDetCd() {
		return savDecDetCd;
	}
	/**
	 * @param savDecDetCd The savDecDetCd to set.
	 */
	public void setSavDecDetCd(String savDecDetCd) {
		this.savDecDetCd = savDecDetCd;
	}
	/**
	 * @return Returns the savDeclCd.
	 */
	public String getSavDeclCd() {
		return savDeclCd;
	}
	/**
	 * @param savDeclCd The savDeclCd to set.
	 */
	public void setSavDeclCd(String savDeclCd) {
		this.savDeclCd = savDeclCd;
	}
	/**
	 * @return Returns the savingAmt.
	 */
	public String getSavingAmt() {
		return savingAmt;
	}
	/**
	 * @param savingAmt The savingAmt to set.
	 */
	public void setSavingAmt(String savingAmt) {
		this.savingAmt = savingAmt;
	}
	/**
	 * @return Returns the savingsCd.
	 */
	public String getSavingsCd() {
		return savingsCd;
	}
	/**
	 * @param savingsCd The savingsCd to set.
	 */
	public void setSavingsCd(String savingsCd) {
		this.savingsCd = savingsCd;
	}
	/**
	 * @return Returns the savingsName.
	 */
	public String getSavingsName() {
		return savingsName;
	}
	/**
	 * @param savingsName The savingsName to set.
	 */
	public void setSavingsName(String savingsName) {
		this.savingsName = savingsName;
	}
	/**
	 * @return Returns the totalSaving.
	 */
	public String getTotalSaving() {
		return totalSaving;
	}
	/**
	 * @param totalSaving The totalSaving to set.
	 */
	public void setTotalSaving(String totalSaving) {
		this.totalSaving = totalSaving;
	}
	/**
	 * @return Returns the finYearName.
	 */
	public String getFinYearName() {
		return finYearName;
	}
	/**
	 * @param finYearName The finYearName to set.
	 */
	public void setFinYearName(String finYearName) {
		this.finYearName = finYearName;
	}
	public List getSavingDt() {
		return savingDt;
	}
	public void setSavingDt(List savingDt) {
		this.savingDt = savingDt;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public List getNscDt() {
		return nscDt;
	}
	public void setNscDt(List nscDt) {
		this.nscDt = nscDt;
	}
}
