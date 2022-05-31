/*
 * Created on Sep 17, 2009
 */
package com.aims.info.incometax;

import java.util.List;

/**
 * @author Srilakshmi E
 */
public class ProjectedIncomeTaxInfo {
	
	private List empPayList;
	private String grossAmt;
	private double netAmt  = 0.0;
	private String sectionCd;
	private String savingNm;
	private String savingAmt;
	private List sectionList;
	private String hra;
	private double prfTax;
	private String yrlyPrfTax;
	private String nscIntrAmt;
	private double deduAmt;
	private double totalSec80Savings;
	private String totalSec80ApplAmt;
	private String taxableIncome;
	private String totITLessEc;
	private String ec;
	private String totIT;
	private String taxPerMonth;
	private String totalSavings;
	private String netSalBeforePFTax;
	private String taxableSal;
	private String grossTotalIncome;
	private String otherIncome;
	private String recIT;
	private double itFrmCurMnth;
	private String currMonth;
	private String exempCEA;
	private double exempWashAll;
	private String exemAllUS10;
	private String tot80eud;
	private double incomehouseprop = 0;
	private String sec24tot;
	private String helpage;
	private String qrtrsrent ;
	private String grosssaladj ;
	private String pmReliefFund;
	private String exempHostal;
	private String exempTransport;
	private String exempMedicalRemb;
	private String AAIBearedTax;
	


	/**
	 * @return Returns the incomehouseprop.
	 */
	public double getIncomehouseprop() {
		return incomehouseprop;
	}
	/**
	 * @param incomehouseprop The incomehouseprop to set.
	 */
	public void setIncomehouseprop(double incomehouseprop) {
		this.incomehouseprop = incomehouseprop;
	}



	/**
	 * @return Returns the exempWashAll.
	 */
	public double getExempWashAll() {
		return exempWashAll;
	}
	/**
	 * @param exempWashAll The exempWashAll to set.
	 */
	public void setExempWashAll(double exempWashAll) {
		this.exempWashAll = exempWashAll;
	}
	/**
	 * @return Returns the currMonth.
	 */
	public String getCurrMonth() {
		return currMonth;
	}
	/**
	 * @param currMonth The currMonth to set.
	 */
	public void setCurrMonth(String currMonth) {
		this.currMonth = currMonth;
	}
	/**
	 * @return Returns the itFrmCurMnth.
	 */
	public double getItFrmCurMnth() {
		return itFrmCurMnth;
	}
	/**
	 * @param itFrmCurMnth The itFrmCurMnth to set.
	 */
	public void setItFrmCurMnth(double itFrmCurMnth) {
		this.itFrmCurMnth = itFrmCurMnth;
	}







	public String getGrossTotalIncome() {
		return grossTotalIncome;
	}
	public void setGrossTotalIncome(String grossTotalIncome) {
		this.grossTotalIncome = grossTotalIncome;
	}
	public String getOtherIncome() {
		return otherIncome;
	}
	public void setOtherIncome(String otherIncome) {
		this.otherIncome = otherIncome;
	}
	public String getExempCEA() {
		return exempCEA;
	}
	public void setExempCEA(String exempCEA) {
		this.exempCEA = exempCEA;
	}
	public String getExempHostal() {
		return exempHostal;
	}
	public void setExempHostal(String exempHostal) {
		this.exempHostal = exempHostal;
	}
	public String getExempMedicalRemb() {
		return exempMedicalRemb;
	}
	public void setExempMedicalRemb(String exempMedicalRemb) {
		this.exempMedicalRemb = exempMedicalRemb;
	}
	public String getExempTransport() {
		return exempTransport;
	}
	public void setExempTransport(String exempTransport) {
		this.exempTransport = exempTransport;
	}
	public String getGrosssaladj() {
		return grosssaladj;
	}
	public void setGrosssaladj(String grosssaladj) {
		this.grosssaladj = grosssaladj;
	}
	public String getNetSalBeforePFTax() {
		return netSalBeforePFTax;
	}
	public void setNetSalBeforePFTax(String netSalBeforePFTax) {
		this.netSalBeforePFTax = netSalBeforePFTax;
	}
	public String getNscIntrAmt() {
		return nscIntrAmt;
	}
	public void setNscIntrAmt(String nscIntrAmt) {
		this.nscIntrAmt = nscIntrAmt;
	}
	public String getQrtrsrent() {
		return qrtrsrent;
	}
	public void setQrtrsrent(String qrtrsrent) {
		this.qrtrsrent = qrtrsrent;
	}
	public String getTaxableSal() {
		return taxableSal;
	}
	public void setTaxableSal(String taxableSal) {
		this.taxableSal = taxableSal;
	}
	public String getYrlyPrfTax() {
		return yrlyPrfTax;
	}
	public void setYrlyPrfTax(String yrlyPrfTax) {
		this.yrlyPrfTax = yrlyPrfTax;
	}
	/**
	 * @return Returns the deduAmt.
	 */
	public double getDeduAmt() {
		return deduAmt;
	}
	/**
	 * @param deduAmt The deduAmt to set.
	 */
	public void setDeduAmt(double deduAmt) {
		this.deduAmt = deduAmt;
	}

	public String getExemAllUS10() {
		return exemAllUS10;
	}
	public void setExemAllUS10(String exemAllUS10) {
		this.exemAllUS10 = exemAllUS10;
	}
	public String getHra() {
		return hra;
	}
	public void setHra(String hra) {
		this.hra = hra;
	}
	/**
	 * @return Returns the prfTax.
	 */
	public double getPrfTax() {
		return prfTax;
	}
	/**
	 * @param prfTax The prfTax to set.
	 */
	public void setPrfTax(double prfTax) {
		this.prfTax = prfTax;
	}
	/**
	 * @return Returns the totalSec80Savings.
	 */
	public double getTotalSec80Savings() {
		return totalSec80Savings;
	}
	/**
	 * @param totalSec80Savings The totalSec80Savings to set.
	 */
	public void setTotalSec80Savings(double totalSec80Savings) {
		this.totalSec80Savings = totalSec80Savings;
	}
	/**
	 * @return Returns the sectionCd.
	 */
	public String getSectionCd() {
		return sectionCd;
	}
	/**
	 * @param sectionCd The sectionCd to set.
	 */
	public void setSectionCd(String sectionCd) {
		this.sectionCd = sectionCd;
	}
	
	/**
	 * @return Returns the savingAmt.
	 */

	/**
	 * @return Returns the savingNm.
	 */
	public String getSavingNm() {
		return savingNm;
	}
	/**
	 * @param savingNm The savingNm to set.
	 */
	public void setSavingNm(String savingNm) {
		this.savingNm = savingNm;
	}
	
	/**
	 * @return Returns the empPayList.
	 */
	public List getEmpPayList() {
		return empPayList;
	}
	/**
	 * @param empPayList The empPayList to set.
	 */
	public void setEmpPayList(List empPayList) {
		this.empPayList = empPayList;
	}

	public String getGrossAmt() {
		return grossAmt;
	}
	public void setGrossAmt(String grossAmt) {
		this.grossAmt = grossAmt;
	}
	/**
	 * @return Returns the netAmt.
	 */
	public double getNetAmt() {
		return netAmt;
	}
	/**
	 * @param netAmt The netAmt to set.
	 */
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	/**
	 * @return Returns the sectionList.
	 */
	public List getSectionList() {
		return sectionList;
	}
	/**
	 * @param sectionList The sectionList to set.
	 */
	public void setSectionList(List sectionList) {
		this.sectionList = sectionList;
	}


	public String getHelpage() {
		return helpage;
	}
	public void setHelpage(String helpage) {
		this.helpage = helpage;
	}
	public String getPmReliefFund() {
		return pmReliefFund;
	}
	public void setPmReliefFund(String pmReliefFund) {
		this.pmReliefFund = pmReliefFund;
	}
	public String getRecIT() {
		return recIT;
	}
	public void setRecIT(String recIT) {
		this.recIT = recIT;
	}
	public String getSec24tot() {
		return sec24tot;
	}
	public void setSec24tot(String sec24tot) {
		this.sec24tot = sec24tot;
	}
	public String getTaxableIncome() {
		return taxableIncome;
	}
	public void setTaxableIncome(String taxableIncome) {
		this.taxableIncome = taxableIncome;
	}
	public String getTaxPerMonth() {
		return taxPerMonth;
	}
	public void setTaxPerMonth(String taxPerMonth) {
		this.taxPerMonth = taxPerMonth;
	}
	public String getTot80eud() {
		return tot80eud;
	}
	public void setTot80eud(String tot80eud) {
		this.tot80eud = tot80eud;
	}
	public String getTotalSec80ApplAmt() {
		return totalSec80ApplAmt;
	}
	public void setTotalSec80ApplAmt(String totalSec80ApplAmt) {
		this.totalSec80ApplAmt = totalSec80ApplAmt;
	}
	public String getTotIT() {
		return totIT;
	}
	public void setTotIT(String totIT) {
		this.totIT = totIT;
	}

	public String getAAIBearedTax() {
		return AAIBearedTax;
	}
	public void setAAIBearedTax(String bearedTax) {
		AAIBearedTax = bearedTax;
	}
	public String getEc() {
		return ec;
	}
	public void setEc(String ec) {
		this.ec = ec;
	}
	public String getTotITLessEc() {
		return totITLessEc;
	}
	public void setTotITLessEc(String totITLessEc) {
		this.totITLessEc = totITLessEc;
	}
	public String getSavingAmt() {
		return savingAmt;
	}
	public void setSavingAmt(String savingAmt) {
		this.savingAmt = savingAmt;
	}
	public String getTotalSavings() {
		return totalSavings;
	}
	public void setTotalSavings(String totalSavings) {
		this.totalSavings = totalSavings;
	}
	
}
