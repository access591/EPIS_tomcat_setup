package com.epis.bean.rpfc.epfforms;

import java.io.Serializable;
import java.util.ArrayList;


public class AAIEPFReportBean implements Serializable{
	String pensionNo="",cpfAccno="",employeeNumber="",employeeName="",designation="",fhName="",dateOfBirth="";
	String airportCode="",region="",subscriptionAmt="",contributionamt="",obYear="",outstandingAmt="";
	String advPurpose="",advAmt="",loanPurpose="",empShare="",AAIShare="",subscriptionInterest="",pensionInterest="";
	String settlementReason="",pensionContribution="",netAmt="",transDate="";
	
	ArrayList advancesList,lonesList,finalSettlementList,reportList1,reportList2;
	ArrayList advancesList2,lonesList2,finalSettlementList2;

	public ArrayList getAdvancesList2() {
		return advancesList2;
	}
	public void setAdvancesList2(ArrayList advancesList2) {
		this.advancesList2 = advancesList2;
	}
	public ArrayList getFinalSettlementList2() {
		return finalSettlementList2;
	}
	public void setFinalSettlementList2(ArrayList finalSettlementList2) {
		this.finalSettlementList2 = finalSettlementList2;
	}
	public ArrayList getLonesList2() {
		return lonesList2;
	}
	public void setLonesList2(ArrayList lonesList2) {
		this.lonesList2 = lonesList2;
	}
	public ArrayList getReportList1() {
		return reportList1;
	}
	public void setReportList1(ArrayList reportList1) {
		this.reportList1 = reportList1;
	}
	public ArrayList getReportList2() {
		return reportList2;
	}
	public void setReportList2(ArrayList reportList2) {
		this.reportList2 = reportList2;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getContributionamt() {
		return contributionamt;
	}
	public void setContributionamt(String contributionamt) {
		this.contributionamt = contributionamt;
	}
	public String getCpfAccno() {
		return cpfAccno;
	}
	public void setCpfAccno(String cpfAccno) {
		this.cpfAccno = cpfAccno;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
	}
	public String getPensionNo() {
		return pensionNo;
	}
	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSubscriptionAmt() {
		return subscriptionAmt;
	}
	public void setSubscriptionAmt(String subscriptionAmt) {
		this.subscriptionAmt = subscriptionAmt;
	}
	public String getObYear() {
		return obYear;
	}
	public void setObYear(String obYear) {
		this.obYear = obYear;
	}
	public String getOutstandingAmt() {
		return outstandingAmt;
	}
	public void setOutstandingAmt(String outstandingAmt) {
		this.outstandingAmt = outstandingAmt;
	}
	public String getAdvPurpose() {
		return advPurpose;
	}
	public void setAdvPurpose(String advPurpose) {
		this.advPurpose = advPurpose;
	}
	public String getAdvAmt() {
		return advAmt;
	}
	public void setAdvAmt(String advAmt) {
		this.advAmt = advAmt;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public String getEmpShare() {
		return empShare;
	}
	public void setEmpShare(String empShare) {
		this.empShare = empShare;
	}
	public String getAAIShare() {
		return AAIShare;
	}
	public void setAAIShare(String share) {
		AAIShare = share;
	}
	public String getSettlementReason() {
		return settlementReason;
	}
	public void setSettlementReason(String settlementReason) {
		this.settlementReason = settlementReason;
	}
	public String getPensionContribution() {
		return pensionContribution;
	}
	public void setPensionContribution(String pensionContribution) {
		this.pensionContribution = pensionContribution;
	}
	public ArrayList getAdvancesList() {
		return advancesList;
	}
	public void setAdvancesList(ArrayList advancesList) {
		this.advancesList = advancesList;
	}
	public ArrayList getFinalSettlementList() {
		return finalSettlementList;
	}
	public void setFinalSettlementList(ArrayList finalSettlementList) {
		this.finalSettlementList = finalSettlementList;
	}
	public ArrayList getLonesList() {
		return lonesList;
	}
	public void setLonesList(ArrayList lonesList) {
		this.lonesList = lonesList;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(String netAmt) {
		this.netAmt = netAmt;
	}
	public String getPensionInterest() {
		return pensionInterest;
	}
	public void setPensionInterest(String pensionInterest) {
		this.pensionInterest = pensionInterest;
	}
	public String getSubscriptionInterest() {
		return subscriptionInterest;
	}
	public void setSubscriptionInterest(String subscriptionInterest) {
		this.subscriptionInterest = subscriptionInterest;
	}

	
	
	

}
