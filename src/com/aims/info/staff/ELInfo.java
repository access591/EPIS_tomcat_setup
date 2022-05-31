package com.aims.info.staff;

import java.util.List;

public class ELInfo {
	
	  private String eltransid;
	  private String elmasterid;
	  private String empname;
	  private String empno;
	  private String emplnumber;
	  private String department;
	  private String designation;
	  private String basic="";
	  private String sp="";
	  private String dp="";
	  private String vda="";
	  private String fyearcd;
	  private String eldays;
	  private String totamt;
	  private String taxpercent;
	  private String incometax;
	  private String netamt;
	  private String paymentstatus;
	  private String days;
	  private String amt;
	  private String day;
	  private String totel;
	  private String balel;
	  private String availeldays;
	  private String appeldays;
	  private String submittype;
	  private String popupval;
	  private String passedby;
	  private String passeddt;
	  private String remarks;
	  private String elPymtCd;
	  private String descipline;
	  private String paymentVoucherNo;
	  private String paymentDt;
	  private List elAccList;
	  private List elDetList;
	  private String ledgerCd;
	  private String ledgerNm;
	  private double debitAmt;
	  private double credtAmt;
	  private double debitTotal;
	  private double credtTotal;
	  private String amtInWords;

	  
	/**
	 * @return Returns the amtInWords.
	 */
	public String getAmtInWords() {
		return amtInWords;
	}
	/**
	 * @param amtInWords The amtInWords to set.
	 */
	public void setAmtInWords(String amtInWords) {
		this.amtInWords = amtInWords;
	}
	/**
	 * @return Returns the credtAmt.
	 */
	public double getCredtAmt() {
		return credtAmt;
	}
	/**
	 * @param credtAmt The credtAmt to set.
	 */
	public void setCredtAmt(double credtAmt) {
		this.credtAmt = credtAmt;
	}
	/**
	 * @return Returns the credtTotal.
	 */
	public double getCredtTotal() {
		return credtTotal;
	}
	/**
	 * @param credtTotal The credtTotal to set.
	 */
	public void setCredtTotal(double credtTotal) {
		this.credtTotal = credtTotal;
	}
	/**
	 * @return Returns the debitAmt.
	 */
	public double getDebitAmt() {
		return debitAmt;
	}
	/**
	 * @param debitAmt The debitAmt to set.
	 */
	public void setDebitAmt(double debitAmt) {
		this.debitAmt = debitAmt;
	}
	/**
	 * @return Returns the debitTotal.
	 */
	public double getDebitTotal() {
		return debitTotal;
	}
	/**
	 * @param debitTotal The debitTotal to set.
	 */
	public void setDebitTotal(double debitTotal) {
		this.debitTotal = debitTotal;
	}
	/**
	 * @return Returns the descipline.
	 */
	public String getDescipline() {
		return descipline;
	}
	/**
	 * @param descipline The descipline to set.
	 */
	public void setDescipline(String descipline) {
		this.descipline = descipline;
	}
	/**
	 * @return Returns the elAccList.
	 */
	public List getElAccList() {
		return elAccList;
	}
	/**
	 * @param elAccList The elAccList to set.
	 */
	public void setElAccList(List elAccList) {
		this.elAccList = elAccList;
	}
	/**
	 * @return Returns the elDetList.
	 */
	public List getElDetList() {
		return elDetList;
	}
	/**
	 * @param elDetList The elDetList to set.
	 */
	public void setElDetList(List elDetList) {
		this.elDetList = elDetList;
	}
	/**
	 * @return Returns the elPymtCd.
	 */
	public String getElPymtCd() {
		return elPymtCd;
	}
	/**
	 * @param elPymtCd The elPymtCd to set.
	 */
	public void setElPymtCd(String elPymtCd) {
		this.elPymtCd = elPymtCd;
	}
	/**
	 * @return Returns the ledgerCd.
	 */
	public String getLedgerCd() {
		return ledgerCd;
	}
	/**
	 * @param ledgerCd The ledgerCd to set.
	 */
	public void setLedgerCd(String ledgerCd) {
		this.ledgerCd = ledgerCd;
	}
	/**
	 * @return Returns the ledgerNm.
	 */
	public String getLedgerNm() {
		return ledgerNm;
	}
	/**
	 * @param ledgerNm The ledgerNm to set.
	 */
	public void setLedgerNm(String ledgerNm) {
		this.ledgerNm = ledgerNm;
	}
	/**
	 * @return Returns the passedby.
	 */
	public String getPassedby() {
		return passedby;
	}
	/**
	 * @param passedby The passedby to set.
	 */
	public void setPassedby(String passedby) {
		this.passedby = passedby;
	}
	/**
	 * @return Returns the passeddt.
	 */
	public String getPasseddt() {
		return passeddt;
	}
	/**
	 * @param passeddt The passeddt to set.
	 */
	public void setPasseddt(String passeddt) {
		this.passeddt = passeddt;
	}
	/**
	 * @return Returns the paymentDt.
	 */
	public String getPaymentDt() {
		return paymentDt;
	}
	/**
	 * @param paymentDt The paymentDt to set.
	 */
	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}
	/**
	 * @return Returns the paymentVoucherNo.
	 */
	public String getPaymentVoucherNo() {
		return paymentVoucherNo;
	}
	/**
	 * @param paymentVoucherNo The paymentVoucherNo to set.
	 */
	public void setPaymentVoucherNo(String paymentVoucherNo) {
		this.paymentVoucherNo = paymentVoucherNo;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the elmasterid.
	 */
	public String getElmasterid() {
		return elmasterid;
	}
	/**
	 * @param elmasterid The elmasterid to set.
	 */
	public void setElmasterid(String elmasterid) {
		this.elmasterid = elmasterid;
	}
	/**
	 * @return Returns the amt.
	 */
	public String getAmt() {
		return amt;
	}
	/**
	 * @param amt The amt to set.
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}
	/**
	 * @return Returns the basic.
	 */
	public String getBasic() {
		return basic;
	}
	/**
	 * @param basic The basic to set.
	 */
	public void setBasic(String basic) {
		this.basic = basic;
	}
	/**
	 * @return Returns the day.
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return Returns the days.
	 */
	public String getDays() {
		return days;
	}
	/**
	 * @param days The days to set.
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * @return Returns the department.
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department The department to set.
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return Returns the designation.
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation The designation to set.
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * @return Returns the dp.
	 */
	public String getDp() {
		return dp;
	}
	/**
	 * @param dp The dp to set.
	 */
	public void setDp(String dp) {
		this.dp = dp;
	}
	/**
	 * @return Returns the eldays.
	 */
	public String getEldays() {
		return eldays;
	}
	/**
	 * @param eldays The eldays to set.
	 */
	public void setEldays(String eldays) {
		this.eldays = eldays;
	}
	/**
	 * @return Returns the eltransid.
	 */
	public String getEltransid() {
		return eltransid;
	}
	/**
	 * @param eltransid The eltransid to set.
	 */
	public void setEltransid(String eltransid) {
		this.eltransid = eltransid;
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
	 * @return Returns the fyearcd.
	 */
	public String getFyearcd() {
		return fyearcd;
	}
	/**
	 * @param fyearcd The fyearcd to set.
	 */
	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}
	/**
	 * @return Returns the incometax.
	 */
	public String getIncometax() {
		return incometax;
	}
	/**
	 * @param incometax The incometax to set.
	 */
	public void setIncometax(String incometax) {
		this.incometax = incometax;
	}
	/**
	 * @return Returns the netamt.
	 */
	public String getNetamt() {
		return netamt;
	}
	/**
	 * @param netamt The netamt to set.
	 */
	public void setNetamt(String netamt) {
		this.netamt = netamt;
	}
	/**
	 * @return Returns the paymentstatus.
	 */
	public String getPaymentstatus() {
		return paymentstatus;
	}
	/**
	 * @param paymentstatus The paymentstatus to set.
	 */
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	/**
	 * @return Returns the sp.
	 */
	public String getSp() {
		return sp;
	}
	/**
	 * @param sp The sp to set.
	 */
	public void setSp(String sp) {
		this.sp = sp;
	}
	/**
	 * @return Returns the taxpercent.
	 */
	public String getTaxpercent() {
		return taxpercent;
	}
	/**
	 * @param taxpercent The taxpercent to set.
	 */
	public void setTaxpercent(String taxpercent) {
		this.taxpercent = taxpercent;
	}
	/**
	 * @return Returns the totamt.
	 */
	public String getTotamt() {
		return totamt;
	}
	/**
	 * @param totamt The totamt to set.
	 */
	public void setTotamt(String totamt) {
		this.totamt = totamt;
	}
	/**
	 * @return Returns the vda.
	 */
	public String getVda() {
		return vda;
	}
	/**
	 * @param vda The vda to set.
	 */
	public void setVda(String vda) {
		this.vda = vda;
	}
	/**
	 * @return Returns the totel.
	 */
	public String getTotel() {
		return totel;
	}
	/**
	 * @param totel The totel to set.
	 */
	public void setTotel(String totel) {
		this.totel = totel;
	}
	/**
	 * @return Returns the appeldays.
	 */
	public String getAppeldays() {
		return appeldays;
	}
	/**
	 * @param appeldays The appeldays to set.
	 */
	public void setAppeldays(String appeldays) {
		this.appeldays = appeldays;
	}
	/**
	 * @return Returns the availeldays.
	 */
	public String getAvaileldays() {
		return availeldays;
	}
	/**
	 * @param availeldays The availeldays to set.
	 */
	public void setAvaileldays(String availeldays) {
		this.availeldays = availeldays;
	}
	/**
	 * @return Returns the submittype.
	 */
	public String getSubmittype() {
		return submittype;
	}
	/**
	 * @param submittype The submittype to set.
	 */
	public void setSubmittype(String submittype) {
		this.submittype = submittype;
	}
	/**
	 * @return Returns the balel.
	 */
	public String getBalel() {
		return balel;
	}
	/**
	 * @param balel The balel to set.
	 */
	public void setBalel(String balel) {
		this.balel = balel;
	}
	/**
	 * @return Returns the popupval.
	 */
	public String getPopupval() {
		return popupval;
	}
	/**
	 * @param popupval The popupval to set.
	 */
	public void setPopupval(String popupval) {
		this.popupval = popupval;
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
}
