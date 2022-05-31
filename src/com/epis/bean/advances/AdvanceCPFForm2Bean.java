package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvanceCPFForm2Bean extends RequestBean implements Serializable{
	String fhName="", employeeName	="", pensionNo="", pfid="", designation="", department="", dateOfMembership=""; 
	String dateOfJoining="", dateOfBirth="", employeeNo="", emoluments ="", advanceTransID="",advntrnsdt="", totalInst="";
	String prpsecvrdclse="", amntAproved="", amntRecommended="", advnceRequest="", empshare="", mnthsemoluments="", outstndamount="";
	String purposeType="",purposeFlag="",advanceType="",purposeOptionType="",authrizedRemarks="",authrizedStatus="",advtransdt="";
	String transStatus="",amntRecommendedDscr="",paymentinfo="",sanctiondate="",purposeOptionTypeDesr="", verifiedby="";
	String advncerqddepend="",utlisiedamntdrwn="",CPFFund="",takenloan="",mthinstallmentamt="",empnamewthblk="";;
	String subscriptionAmt="",contributionAmt="",emolumentsCurr ="",advnceRequestCurr="",subscriptionAmtCurr="",empshareCurr="",contributionAmtCurr="",firstInsSubAmntCurr="",firstInsConrtiAmntCurr="",firstInsTotAmnt="";
	String mnthsemolumentsCurr="",CPFFundCurr="",amntRecommendedCurr="",outstndamountCurr="",trust="",partyname="",approvedsubamt="",approvedconamt=""; 
	String cpfaccno="",station="",region="",amntRecommendedWords="",gender="",placeofposting="",advanceApprovedCurr="",regionlabel="",finyear="",emolumentsLabel="";
	String approvedsubamtcurr="",approvedcontamtcurr="",ntimesemoluments="",ntimesemolumentscurr="",narration="";
	String interestRate="",interestinstallments="",intinstallmentamt="",amntAprovedCurr="",contentFlag="",advanceTransIDDec="",firstInsSubAmnt="",firstInsConrtiAmnt="";
	String chkwthdrwlinfo="",lodInfo="",pfwSanctionOrderNo="",lastmthinstallmentamt="",userTransMnthEmolunts="",propertyAddress="",formName="",updateBankFlag="",revAdvanceTransID="",frmFlag="";
	  
	public String getFirstInsConrtiAmntCurr() {
		return firstInsConrtiAmntCurr;
	}

	public void setFirstInsConrtiAmntCurr(String firstInsConrtiAmntCurr) {
		this.firstInsConrtiAmntCurr = firstInsConrtiAmntCurr;
	}

	public String getFirstInsSubAmntCurr() {
		return firstInsSubAmntCurr;
	}

	public void setFirstInsSubAmntCurr(String firstInsSubAmntCurr) {
		this.firstInsSubAmntCurr = firstInsSubAmntCurr;
	}

	public String getPfwSanctionOrderNo() {
		return pfwSanctionOrderNo;
	}

	public void setPfwSanctionOrderNo(String pfwSanctionOrderNo) {
		this.pfwSanctionOrderNo = pfwSanctionOrderNo;
	}

	public String getChkwthdrwlinfo() {
		return chkwthdrwlinfo;
	}

	public void setChkwthdrwlinfo(String chkwthdrwlinfo) {
		this.chkwthdrwlinfo = chkwthdrwlinfo;
	}

	public String getLodInfo() {
		return lodInfo;
	}

	public void setLodInfo(String lodInfo) {
		this.lodInfo = lodInfo;
	}

	public AdvanceCPFForm2Bean(HttpServletRequest request){
		super(request);
		}
	
	public AdvanceCPFForm2Bean(){
	}
	
	public String getAmntAprovedCurr() {
		return amntAprovedCurr;
	}

	public void setAmntAprovedCurr(String amntAprovedCurr) {
		this.amntAprovedCurr = amntAprovedCurr;
	}

	public String getContentFlag() {
		return contentFlag;
	}

	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
	}

	public String getInterestinstallments() {
		return interestinstallments;
	}

	public void setInterestinstallments(String interestinstallments) {
		this.interestinstallments = interestinstallments;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getIntinstallmentamt() {
		return intinstallmentamt;
	}

	public void setIntinstallmentamt(String intinstallmentamt) {
		this.intinstallmentamt = intinstallmentamt;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getApprovedconamt() {
		return approvedconamt;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getAdvanceApprovedCurr() {
		return advanceApprovedCurr;
	}

	public String getEmpnamewthblk() {
		return empnamewthblk;
	}

	public void setEmpnamewthblk(String empnamewthblk) {
		this.empnamewthblk = empnamewthblk;
	}

	public String getMthinstallmentamt() {
		return mthinstallmentamt;
	}

	public void setMthinstallmentamt(String mthinstallmentamt) {
		this.mthinstallmentamt = mthinstallmentamt;
	}

	public void setAdvanceApprovedCurr(String advanceApprovedCurr) {
		this.advanceApprovedCurr = advanceApprovedCurr;
	}

	public String getCpfaccno() {
		return cpfaccno;
	}

	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
	}

	public String getFinyear() {
		return finyear;
	}

	public void setFinyear(String finyear) {
		this.finyear = finyear;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionlabel() {
		return regionlabel;
	}

	public void setRegionlabel(String regionlabel) {
		this.regionlabel = regionlabel;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getMnthsemolumentsCurr() {
		return mnthsemolumentsCurr;
	}

	public String getTrust() {
		return trust;
	}

	public void setTrust(String trust) {
		this.trust = trust;
	}

	public void setMnthsemolumentsCurr(String mnthsemolumentsCurr) {
		this.mnthsemolumentsCurr = mnthsemolumentsCurr;
	}

	public String getEmpshareCurr() {
		return empshareCurr;
	}

	public void setEmpshareCurr(String empshareCurr) {
		this.empshareCurr = empshareCurr;
	}

	public String getSubscriptionAmtCurr() {
		return subscriptionAmtCurr;
	}

	public void setSubscriptionAmtCurr(String subscriptionAmtCurr) {
		this.subscriptionAmtCurr = subscriptionAmtCurr;
	}

	public String getAdvnceRequestCurr() {
		return advnceRequestCurr;
	}

	public void setAdvnceRequestCurr(String advnceRequestCurr) {
		this.advnceRequestCurr = advnceRequestCurr;
	}

	public String getEmolumentsCurr() {
		return emolumentsCurr;
	}

	public void setEmolumentsCurr(String emolumentsCurr) {
		this.emolumentsCurr = emolumentsCurr;
	}

	public String getTakenloan() {
		return takenloan;
	}

	public void setTakenloan(String takenloan) {
		this.takenloan = takenloan;
	}

	public String getAdvtransdt() {
		return advtransdt;
	}

	public void setAdvtransdt(String advtransdt) {
		this.advtransdt = advtransdt;
	}

	public String getContributionAmt() {
		return contributionAmt;
	}

	public void setContributionAmt(String contributionAmt) {
		this.contributionAmt = contributionAmt;
	}

	public String getSubscriptionAmt() {
		return subscriptionAmt;
	}

	public void setSubscriptionAmt(String subscriptionAmt) {
		this.subscriptionAmt = subscriptionAmt;
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

	public String getContributionAmtCurr() {
		return contributionAmtCurr;
	}

	public void setContributionAmtCurr(String contributionAmtCurr) {
		this.contributionAmtCurr = contributionAmtCurr;
	}

	public String getCPFFundCurr() {
		return CPFFundCurr;
	}

	public void setCPFFundCurr(String fundCurr) {
		CPFFundCurr = fundCurr;
	}

	public String getAmntRecommendedCurr() {
		return amntRecommendedCurr;
	}

	public void setAmntRecommendedCurr(String amntRecommendedCurr) {
		this.amntRecommendedCurr = amntRecommendedCurr;
	}

	public String getOutstndamountCurr() {
		return outstndamountCurr;
	}

	public void setOutstndamountCurr(String outstndamountCurr) {
		this.outstndamountCurr = outstndamountCurr;
	}

	public String getPaymentinfo() {
		return paymentinfo;
	}

	public void setPaymentinfo(String paymentinfo) {
		this.paymentinfo = paymentinfo;
	}

	public String getSanctiondate() {
		return sanctiondate;
	}

	public void setSanctiondate(String sanctiondate) {
		this.sanctiondate = sanctiondate;
	}

	public String getEmolumentsLabel() {
		return emolumentsLabel;
	}

	public void setEmolumentsLabel(String emolumentsLabel) {
		this.emolumentsLabel = emolumentsLabel;
	}

	public String getAmntRecommendedWords() {
		return amntRecommendedWords;
	}

	public void setAmntRecommendedWords(String amntRecommendedWords) {
		this.amntRecommendedWords = amntRecommendedWords;
	}

	public String getPlaceofposting() {
		return placeofposting;
	}

	public void setPlaceofposting(String placeofposting) {
		this.placeofposting = placeofposting;
	}

	public String getPurposeOptionTypeDesr() {
		return purposeOptionTypeDesr;
	}

	public void setPurposeOptionTypeDesr(String purposeOptionTypeDesr) {
		this.purposeOptionTypeDesr = purposeOptionTypeDesr;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getVerifiedby() {
		return verifiedby;
	}

	public void setVerifiedby(String verifiedby) {
		this.verifiedby = verifiedby;
	}

	public String getApprovedcontamtcurr() {
		return approvedcontamtcurr;
	}

	public void setApprovedcontamtcurr(String approvedcontamtcurr) {
		this.approvedcontamtcurr = approvedcontamtcurr;
	}

	public String getApprovedsubamtcurr() {
		return approvedsubamtcurr;
	}

	public void setApprovedsubamtcurr(String approvedsubamtcurr) {
		this.approvedsubamtcurr = approvedsubamtcurr;
	}

	public String getNtimesemoluments() {
		return ntimesemoluments;
	}

	public void setNtimesemoluments(String ntimesemoluments) {
		this.ntimesemoluments = ntimesemoluments;
	}

	public String getNtimesemolumentscurr() {
		return ntimesemolumentscurr;
	}

	public void setNtimesemolumentscurr(String ntimesemolumentscurr) {
		this.ntimesemolumentscurr = ntimesemolumentscurr;
	}

	public String getAdvanceTransIDDec() {
		return advanceTransIDDec;
	}

	public void setAdvanceTransIDDec(String advanceTransIDDec) {
		this.advanceTransIDDec = advanceTransIDDec;
	}

	public String getPurposeFlag() {
		return purposeFlag;
	}

	public void setPurposeFlag(String purposeFlag) {
		this.purposeFlag = purposeFlag;
	}

	public String getLastmthinstallmentamt() {
		return lastmthinstallmentamt;
	}

	public void setLastmthinstallmentamt(String lastmthinstallmentamt) {
		this.lastmthinstallmentamt = lastmthinstallmentamt;
	}

	public String getUserTransMnthEmolunts() {
		return userTransMnthEmolunts;
	}

	public void setUserTransMnthEmolunts(String userTransMnthEmolunts) {
		this.userTransMnthEmolunts = userTransMnthEmolunts;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getUpdateBankFlag() {
		return updateBankFlag;
	}

	public void setUpdateBankFlag(String updateBankFlag) {
		this.updateBankFlag = updateBankFlag;
	}

	public String getRevAdvanceTransID() {
		return revAdvanceTransID;
	}

	public void setRevAdvanceTransID(String revAdvanceTransID) {
		this.revAdvanceTransID = revAdvanceTransID;
	}

	public String getFrmFlag() {
		return frmFlag;
	}

	public void setFrmFlag(String frmFlag) {
		this.frmFlag = frmFlag;
	}

	public String getFirstInsConrtiAmnt() {
		return firstInsConrtiAmnt;
	}

	public void setFirstInsConrtiAmnt(String firstInsConrtiAmnt) {
		this.firstInsConrtiAmnt = firstInsConrtiAmnt;
	}

	public String getFirstInsSubAmnt() {
		return firstInsSubAmnt;
	}

	public void setFirstInsSubAmnt(String firstInsSubAmnt) {
		this.firstInsSubAmnt = firstInsSubAmnt;
	}

	public String getFirstInsTotAmnt() {
		return firstInsTotAmnt;
	}

	public void setFirstInsTotAmnt(String firstInsTotAmnt) {
		this.firstInsTotAmnt = firstInsTotAmnt;
	}



	

	


}
