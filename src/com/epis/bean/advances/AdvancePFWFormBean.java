package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvancePFWFormBean extends RequestBean implements Serializable{
	String fhName="", employeeName	="", pensionNo="", pfid="", designation="", department="", dateOfMembership=""; 
	String dateOfJoining="", dateOfBirth="", employeeNo="", emoluments ="", advanceTransID="",advntrnsdt="", totalInst="";
	String prpsecvrdclse="", amntAproved="", amntRecommended="", advnceRequest="", empshare="", mnthsemoluments="", outstndamount="";
	String purposeType="",advanceType="",purposeOptionType="",authrizedRemarks="",authrizedStatus="";
	String transStatus="",amntRecommendedDscr="",sanctiondate="";
	String advncerqddepend="",utlisiedamntdrwn="",CPFFund="",paymentinfo="";
	 String  approvedsubamt="",approvedconamt="";

	 public AdvancePFWFormBean(HttpServletRequest request){
			super(request);
			}
	 
	public AdvancePFWFormBean(){
	}
		

	public String getApprovedconamt() {
		return approvedconamt;
	}

	public void setApprovedconamt(String approvedconamt) {
		this.approvedconamt = approvedconamt;
	}

	public String getApprovedsubamt() {
		return approvedsubamt;
	}

	public void setApprovedsubamt(String approvedsubamt) {
		this.approvedsubamt = approvedsubamt;
	}

	public String getSanctiondate() {
		return sanctiondate;
	}

	public void setSanctiondate(String sanctiondate) {
		this.sanctiondate = sanctiondate;
	}

	public String getPaymentinfo() {
		return paymentinfo;
	}

	public void setPaymentinfo(String paymentinfo) {
		this.paymentinfo = paymentinfo;
	}

	public String getCPFFund() {
		return CPFFund;
	}

	public void setCPFFund(String fund) {
		CPFFund = fund;
	}

	public String getAdvncerqddepend() {
		return advncerqddepend;
	}

	public void setAdvncerqddepend(String advncerqddepend) {
		this.advncerqddepend = advncerqddepend;
	}

	public String getUtlisiedamntdrwn() {
		return utlisiedamntdrwn;
	}

	public void setUtlisiedamntdrwn(String utlisiedamntdrwn) {
		this.utlisiedamntdrwn = utlisiedamntdrwn;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getAuthrizedRemarks() {
		return authrizedRemarks;
	}

	public void setAuthrizedRemarks(String authrizedRemarks) {
		this.authrizedRemarks = authrizedRemarks;
	}

	public String getAuthrizedStatus() {
		return authrizedStatus;
	}

	public void setAuthrizedStatus(String authrizedStatus) {
		this.authrizedStatus = authrizedStatus;
	}

	public String getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}



	public String getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(String purposeType) {
		this.purposeType = purposeType;
	}

	public String getPurposeOptionType() {
		return purposeOptionType;
	}

	public void setPurposeOptionType(String purposeOptionType) {
		this.purposeOptionType = purposeOptionType;
	}

	public String getAmntRecommended() {
		return amntRecommended;
	}

	public void setAmntRecommended(String amntRecommended) {
		this.amntRecommended = amntRecommended;
	}

	public String getAdvnceRequest() {
		return advnceRequest;
	}

	public void setAdvnceRequest(String advnceRequest) {
		this.advnceRequest = advnceRequest;
	}

	public String getAmntAproved() {
		return amntAproved;
	}

	public void setAmntAproved(String amntAproved) {
		this.amntAproved = amntAproved;
	}

	public String getAdvanceTransID() {
		return advanceTransID;
	}

	public void setAdvanceTransID(String advanceTransID) {
		this.advanceTransID = advanceTransID;
	}




	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDateOfMembership() {
		return dateOfMembership;
	}

	public void setDateOfMembership(String dateOfMembership) {
		this.dateOfMembership = dateOfMembership;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmoluments() {
		return emoluments;
	}

	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmpshare() {
		return empshare;
	}

	public void setEmpshare(String empshare) {
		this.empshare = empshare;
	}

	public String getFhName() {
		return fhName;
	}

	public void setFhName(String fhName) {
		this.fhName = fhName;
	}

	public String getMnthsemoluments() {
		return mnthsemoluments;
	}

	public void setMnthsemoluments(String mnthsemoluments) {
		this.mnthsemoluments = mnthsemoluments;
	}

	public String getOutstndamount() {
		return outstndamount;
	}

	public void setOutstndamount(String outstndamount) {
		this.outstndamount = outstndamount;
	}

	public String getPensionNo() {
		return pensionNo;
	}

	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getPrpsecvrdclse() {
		return prpsecvrdclse;
	}

	public void setPrpsecvrdclse(String prpsecvrdclse) {
		this.prpsecvrdclse = prpsecvrdclse;
	}

	public String getTotalInst() {
		return totalInst;
	}

	public void setTotalInst(String totalInst) {
		this.totalInst = totalInst;
	}

	public String getAdvntrnsdt() {
		return advntrnsdt;
	}

	public void setAdvntrnsdt(String advntrnsdt) {
		this.advntrnsdt = advntrnsdt;
	}

	public String getAmntRecommendedDscr() {
		return amntRecommendedDscr;
	}

	public void setAmntRecommendedDscr(String amntRecommendedDscr) {
		this.amntRecommendedDscr = amntRecommendedDscr;
	}

	

	


}
