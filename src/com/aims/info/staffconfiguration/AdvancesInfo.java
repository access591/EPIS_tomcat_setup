/**
 * File       : AdvancesInfo.java
 * Date       : 28/07/2009
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */

package com.aims.info.staffconfiguration;

import java.io.Serializable;

/**
 * @author Srilakshmi E
 *
 */
public class AdvancesInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1021156526302784181L;
	private String advName;
	private String advDesc;
	private String advType;
	private String advCd;
	private String status;
	private String usercd;
	private String empno;
	private String empname;
	private String advamt;
	private String employeeadvcd;
	private String rateofinterest;
	private String noofinstallments;
	private String advancedate;
	private String paidinstallments;
	private String amountpaid;
	private String recoveredinstallments;
	private String advfinisheddt;
	private String amountrecovered;	
	private String emi;
	private String EARNDEDUCD;
	private String finyearCd;
	private String interestamt;
	private String interestinstallments;
	private String paidintrstinstallments;
	private String paidintrstamt;
	private String recoveredintrstAmt;
	private String recoveredintrstinstlmnts;
	private String interestemi;
	private String startFromMonth;
	private String firstEMI;
	private String firstInterestEMI;
	private String disciplineCd;
	private String staffCategoryCd;
	private String designationCd;
	private String fyearcd;
	private String principalstatus;
	private String intereststatus;
			
	private String empadvcd;
	
	private String advname;
	private String recoveredamt;
	
	private String balamt;
	
	private String balinst;
	
	private String openingBal;
	private String closingBal;
	private String transferedinAmt;
	
	private String salheadname;
	private String name;
	
	private String balinstramt; 
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the salheadname.
	 */
	public String getSalheadname() {
		return salheadname;
	}
	/**
	 * @param salheadname The salheadname to set.
	 */
	public void setSalheadname(String salheadname) {
		this.salheadname = salheadname;
	}
	/**
	 * @return Returns the closingBal.
	 */
	public String getClosingBal() {
		return closingBal;
	}
	/**
	 * @param closingBal The closingBal to set.
	 */
	public void setClosingBal(String closingBal) {
		this.closingBal = closingBal;
	}
	/**
	 * @return Returns the openingBal.
	 */
	public String getOpeningBal() {
		return openingBal;
	}
	/**
	 * @param openingBal The openingBal to set.
	 */
	public void setOpeningBal(String openingBal) {
		this.openingBal = openingBal;
	}
	/**
	 * @return Returns the transferedinAmt.
	 */
	public String getTransferedinAmt() {
		return transferedinAmt;
	}
	/**
	 * @param transferedinAmt The transferedinAmt to set.
	 */
	public void setTransferedinAmt(String transferedinAmt) {
		this.transferedinAmt = transferedinAmt;
	}
	/**
	 * @return Returns the balinst.
	 */
	public String getBalinst() {
		return balinst;
	}
	/**
	 * @param balinst The balinst to set.
	 */
	public void setBalinst(String balinst) {
		this.balinst = balinst;
	}
	/**
	 * @return Returns the balamt.
	 */
	public String getBalamt() {
		return balamt;
	}
	/**
	 * @param balamt The balamt to set.
	 */
	public void setBalamt(String balamt) {
		this.balamt = balamt;
	}
	/**
	 * @return Returns the recoveredamt.
	 */
	public String getRecoveredamt() {
		return recoveredamt;
	}
	/**
	 * @param recoveredamt The recoveredamt to set.
	 */
	public void setRecoveredamt(String recoveredamt) {
		this.recoveredamt = recoveredamt;
	}
	/**
	 * @return Returns the advname.
	 */
	public String getAdvname() {
		return advname;
	}
	/**
	 * @param advname The advname to set.
	 */
	public void setAdvname(String advname) {
		this.advname = advname;
	}
	/**
	 * @return Returns the empadvcd.
	 */
	public String getEmpadvcd() {
		return empadvcd;
	}
	/**
	 * @param empadvcd The empadvcd to set.
	 */
	public void setEmpadvcd(String empadvcd) {
		this.empadvcd = empadvcd;
	}
	/**
	 * @return Returns the intereststatus.
	 */
	public String getIntereststatus() {
		return intereststatus;
	}
	/**
	 * @param intereststatus The intereststatus to set.
	 */
	public void setIntereststatus(String intereststatus) {
		this.intereststatus = intereststatus;
	}
	/**
	 * @return Returns the principalstatus.
	 */
	public String getPrincipalstatus() {
		return principalstatus;
	}
	/**
	 * @param principalstatus The principalstatus to set.
	 */
	public void setPrincipalstatus(String principalstatus) {
		this.principalstatus = principalstatus;
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
	 * @return Returns the designationCd.
	 */
	public String getDesignationCd() {
		return designationCd;
	}
	/**
	 * @param designationCd The designationCd to set.
	 */
	public void setDesignationCd(String designationCd) {
		this.designationCd = designationCd;
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
	 * @return Returns the firstEMI.
	 */
	public String getFirstEMI() {
		return firstEMI;
	}
	/**
	 * @param firstEMI The firstEMI to set.
	 */
	public void setFirstEMI(String firstEMI) {
		this.firstEMI = firstEMI;
	}
	/**
	 * @return Returns the firstInterestEMI.
	 */
	public String getFirstInterestEMI() {
		return firstInterestEMI;
	}
	/**
	 * @param firstInterestEMI The firstInterestEMI to set.
	 */
	public void setFirstInterestEMI(String firstInterestEMI) {
		this.firstInterestEMI = firstInterestEMI;
	}
	/**
	 * @return Returns the startFromMonth.
	 */
	public String getStartFromMonth() {
		return startFromMonth;
	}
	/**
	 * @param startFromMonth The startFromMonth to set.
	 */
	public void setStartFromMonth(String startFromMonth) {
		this.startFromMonth = startFromMonth;
	}
	
	
	/**
	 * @return Returns the interestamt.
	 */
	public String getInterestamt() {
		return interestamt;
	}
	/**
	 * @param interestamt The interestamt to set.
	 */
	public void setInterestamt(String interestamt) {
		this.interestamt = interestamt;
	}
	/**
	 * @return Returns the interestemi.
	 */
	public String getInterestemi() {
		return interestemi;
	}
	/**
	 * @param interestemi The interestemi to set.
	 */
	public void setInterestemi(String interestemi) {
		this.interestemi = interestemi;
	}
	
	/**
	 * @return Returns the interestinstallments.
	 */
	public String getInterestinstallments() {
		return interestinstallments;
	}
	/**
	 * @param interestinstallments The interestinstallments to set.
	 */
	public void setInterestinstallments(String interestinstallments) {
		this.interestinstallments = interestinstallments;
	}
	/**
	 * @return Returns the paidintrstamt.
	 */
	public String getPaidintrstamt() {
		return paidintrstamt;
	}
	/**
	 * @param paidintrstamt The paidintrstamt to set.
	 */
	public void setPaidintrstamt(String paidintrstamt) {
		this.paidintrstamt = paidintrstamt;
	}
	/**
	 * @return Returns the paidintrstinstallments.
	 */
	public String getPaidintrstinstallments() {
		return paidintrstinstallments;
	}
	/**
	 * @param paidintrstinstallments The paidintrstinstallments to set.
	 */
	public void setPaidintrstinstallments(String paidintrstinstallments) {
		this.paidintrstinstallments = paidintrstinstallments;
	}
	/**
	 * @return Returns the recoveredintrstAmt.
	 */
	public String getRecoveredintrstAmt() {
		return recoveredintrstAmt;
	}
	/**
	 * @param recoveredintrstAmt The recoveredintrstAmt to set.
	 */
	public void setRecoveredintrstAmt(String recoveredintrstAmt) {
		this.recoveredintrstAmt = recoveredintrstAmt;
	}
	/**
	 * @return Returns the recoveredintrstinstlmnts.
	 */
	public String getRecoveredintrstinstlmnts() {
		return recoveredintrstinstlmnts;
	}
	/**
	 * @param recoveredintrstinstlmnts The recoveredintrstinstlmnts to set.
	 */
	public void setRecoveredintrstinstlmnts(String recoveredintrstinstlmnts) {
		this.recoveredintrstinstlmnts = recoveredintrstinstlmnts;
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
	 * @return Returns the advCd.
	 */
	public String getAdvCd() {
		return advCd;
	}
	/**
	 * @param advCd The advCd to set.
	 */
	public void setAdvCd(String advCd) {
		this.advCd = advCd;
	}
	/**
	 * @return Returns the advDesc.
	 */
	public String getAdvDesc() {
		return advDesc;
	}
	/**
	 * @param advDesc The advDesc to set.
	 */
	public void setAdvDesc(String advDesc) {
		this.advDesc = advDesc;
	}
	/**
	 * @return Returns the advName.
	 */
	public String getAdvName() {
		return advName;
	}
	/**
	 * @param advName The advName to set.
	 */
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	/**
	 * @return Returns the advType.
	 */
	public String getAdvType() {
		return advType;
	}
	/**
	 * @param advType The advType to set.
	 */
	public void setAdvType(String advType) {
		this.advType = advType;
	}
	/**
	 * @return Returns the status.
	 */
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
	 * @return Returns the advamt.
	 */
	public String getAdvamt() {
		return advamt;
	}
	/**
	 * @param advamt The advamt to set.
	 */
	public void setAdvamt(String advamt) {
		this.advamt = advamt;
	}
	/**
	 * @return Returns the advancedate.
	 */
	public String getAdvancedate() {
		return advancedate;
	}
	/**
	 * @param advancedate The advancedate to set.
	 */
	public void setAdvancedate(String advancedate) {
		this.advancedate = advancedate;
	}
	/**
	 * @return Returns the advfinisheddt.
	 */
	public String getAdvfinisheddt() {
		return advfinisheddt;
	}
	/**
	 * @param advfinisheddt The advfinisheddt to set.
	 */
	public void setAdvfinisheddt(String advfinisheddt) {
		this.advfinisheddt = advfinisheddt;
	}
	/**
	 * @return Returns the amountpaid.
	 */
	public String getAmountpaid() {
		return amountpaid;
	}
	/**
	 * @param amountpaid The amountpaid to set.
	 */
	public void setAmountpaid(String amountpaid) {
		this.amountpaid = amountpaid;
	}
	/**
	 * @return Returns the employeeadvcd.
	 */
	public String getEmployeeadvcd() {
		return employeeadvcd;
	}
	/**
	 * @param employeeadvcd The employeeadvcd to set.
	 */
	public void setEmployeeadvcd(String employeeadvcd) {
		this.employeeadvcd = employeeadvcd;
	}
	/**
	 * @return Returns the noofinstallments.
	 */
	public String getNoofinstallments() {
		return noofinstallments;
	}
	/**
	 * @param noofinstallments The noofinstallments to set.
	 */
	public void setNoofinstallments(String noofinstallments) {
		this.noofinstallments = noofinstallments;
	}
	/**
	 * @return Returns the paidinstallments.
	 */
	public String getPaidinstallments() {
		return paidinstallments;
	}
	/**
	 * @param paidinstallments The paidinstallments to set.
	 */
	public void setPaidinstallments(String paidinstallments) {
		this.paidinstallments = paidinstallments;
	}
	/**
	 * @return Returns the rateofinterest.
	 */
	public String getRateofinterest() {
		return rateofinterest;
	}
	/**
	 * @param rateofinterest The rateofinterest to set.
	 */
	public void setRateofinterest(String rateofinterest) {
		this.rateofinterest = rateofinterest;
	}
	/**
	 * @return Returns the recoveredinstallments.
	 */
	public String getRecoveredinstallments() {
		return recoveredinstallments;
	}
	/**
	 * @param recoveredinstallments The recoveredinstallments to set.
	 */
	public void setRecoveredinstallments(String recoveredinstallments) {
		this.recoveredinstallments = recoveredinstallments;
	}
	/**
	 * @return Returns the amountrecovered.
	 */
	public String getAmountrecovered() {
		return amountrecovered;
	}
	/**
	 * @param amountrecovered The amountrecovered to set.
	 */
	public void setAmountrecovered(String amountrecovered) {
		this.amountrecovered = amountrecovered;
	}
	
	
	
	/**
	 * @return Returns the emi.
	 */
	public String getEmi() {
		return emi;
	}
	/**
	 * @param emi The emi to set.
	 */
	public void setEmi(String emi) {
		this.emi = emi;
	}
	
	
	/**
	 * @return Returns the eARNDEDUCD.
	 */
	public String getEARNDEDUCD() {
		return EARNDEDUCD;
	}
	/**
	 * @param earndeducd The eARNDEDUCD to set.
	 */
	public void setEARNDEDUCD(String earndeducd) {
		EARNDEDUCD = earndeducd;
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
	public String getBalinstramt() {
		return balinstramt;
	}
	public void setBalinstramt(String balinstramt) {
		this.balinstramt = balinstramt;
	}
}
