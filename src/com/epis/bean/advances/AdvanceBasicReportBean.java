/**
 * File       : AdvanceBasicBean.java
 * Date       : 09/25/2009
 * Author     : Suresh Kumar Repaka 
 * Description: 
 * Copyright (2009) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.bean.advances;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvanceBasicReportBean extends RequestBean implements Serializable {

	String employeeName = "", pensionNo = "", pfid = "", designation = "",
			department = "", dateOfMembership = "", dateOfJoining = "",
			dateOfBirth = "";

	String employeeNo = "", cpfaccno = "", region = "", transMnthYear = "",
			fhName = "", wetherOption = "", mailID = "";

	String emoluments = "", pfStatury = "", advanceType = "", pfwPurpose = "",
			advPurpose = "", purposeType = "", advReasonText = "",
			advReqAmnt = "";

	String empage = "", PFWrule = "", CPFFund = "", hbapurposetype = "",
			hbarepaymenttype = "", hbaloanname = "";

	String hbaloanaddress = "", osamountwithinterest = "0.00",
			hbaownername = "", purposeTypeDescr = "", sanctionOrderNo = "";

	String hbaowneraddress = "", hbaownerarea = "", hbaownerplotno = "",
			hbaownerlocality = "", hbaownermuncipal = "", hbaownercity = "",
			hbadrwnfrmaai = "", placeofposting = "";

	String hbapermissionaai = "", gender = "", nomineecount = "",
			wthdrwid = "";

	String trust = "", actualcost = "", propertyaddress = "", takenloan = "",
			paymentinfo = "", mthinstallmentamt = "";

	String hbawthdrwlpurpose = "", hbawthdrwlamount = "",
			hbawthdrwladdress = "", airportlabel = "", nomineeAppointed = "";

	String chkWthdrwlInfo = "", wthDrwlTrnsdt = "", wthdrwlAmount = "0.00",
			partyName = "", partyAddress = "", lodInfo = "", remarks = "";

	String pfwHEType = "", nmCourse = "", nmInstitue = "", addrInstitue = "",
			curseDuration = "", heLastExaminfo = "", advanceTransID = "",
			purposeOptionType = "";;

	String fmlyEmpName = "", fmlyDOB = "", fmlyAge = "", brthCertProve = "",
			heRecog = "", oblCermoney = "", cpfIntallments = "",
			wthdrwlpurpose = "";

	String advncerqddepend = "", utlisiedamntdrwn = "", seperationfavour = "",
			seperationremarks = "", amtinwords = "", chkBankFlag = "";

	// --------Note Sheet Properties-------
	String permenentaddress = "", presentaddress = "", phoneno = "",
			nssanctionno = "", verifiedby = "",sanctionOrderFlag="";

	String airportcd = "", sanctionno = "", sanctiondt = "",
			seperationreason = "", seperationdate = "", nomineename = "",
			regionLbl = "";

	String amtadmtdate = "", emplshare = "", emplrshare = "",
			pensioncontribution = "", netcontribution = "", nextdaydate = "",
			paymentdate = "";

	String advtransdt = "", advntrnsid = "", approvedremarks = "",
			advtransstatus = "", adhocamt = "", interestinstallments = "",
			intinstallmentamt = "";

	String requiredAmt = "", approvedAmt = "", month = "", screenTitle = "",
			dispPurposeTypeText = "", dispPurposeOptionTxt = "",
			marriagedate = "";

	String fromYear = "", toYear = "", postheld = "";

	String maritalstatus = "", emprelation = "", empaddress = "",
			quarterallotment = "", quarterno = "", colonyname = "",
			empstation = "";

	String resignationreason = "", organizationname = "",
			organizationaddress = "", appointmentdate = "", postedas = "",
			workingplace = "";

	List sanctionList;

	List multipleNomineeList;

	List nomineeList;

	String arreartype = "", arreardate = "", fromfinyear = "", tofinyear = "",
			interestratefrom = "", interestrateto = "",cbstatus="",cbvocherno="";

	String purchasesitecount = "", purchasehousecount = "",
			constructionhousecount = "", acquireflatcount = "",
			renovationhousecount = "", repaymenthbacount = "",
			hbaotherscount = "";

	String purchasesiteamount = "", purchasehouseamount = "",
			constructionhouseamount = "", acquireflatamount = "",
			renovationhouseamount = "", repaymenthbaamount = "",
			hbaothersamount = "";

	String selfmarriagecount = "", selfmarriageamount = "",
			dependentmarriagecount = "", dependentmarriageamount = "";

	String dependenteducationcount = "", dependenteducationamount = "",
			reccount = "", tblairportcd = "", soremarks = "", totalConAmt = "",
			totalPenConAmt = "", totalAdhocAmt = "", totalNetConAmt = "";;

	String totalrequiredamt = "", totalactualcost = "", totalsubamt = "",
			totalsanctionedamt = "", totalMthInstallAmt = "",
			totalIntInstallAmt = "",userApprovedBy="";
	
	String bankempname = "",bankname = "",banksavingaccno = "",bankemprtgsneftcode ="",rateOfInterest="",CBRemarks="";
	String SOPara1="",SORefType="",SO_As_Per_CHQ="",SO_No_Payment="",SO_Interest_for="",SO_Note_Apprd_Remarks="",SO_Death_Notes="";
	String transDigitalSign="",transDispSignName="",FS_SO_Trust="",CC_Lines="",SO_airportCd="",finyear="",payMntInfo="";

	public String getUserApprovedBy() {
		return userApprovedBy;
	}

	public void setUserApprovedBy(String userApprovedBy) {
		this.userApprovedBy = userApprovedBy;
	}

	public String getBankempname() {
		return bankempname;
	}

	public void setBankempname(String bankempname) {
		this.bankempname = bankempname;
	}

	public String getBankemprtgsneftcode() {
		return bankemprtgsneftcode;
	}

	public void setBankemprtgsneftcode(String bankemprtgsneftcode) {
		this.bankemprtgsneftcode = bankemprtgsneftcode;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBanksavingaccno() {
		return banksavingaccno;
	}

	public void setBanksavingaccno(String banksavingaccno) {
		this.banksavingaccno = banksavingaccno;
	}

	public String getTotalactualcost() {
		return totalactualcost;
	}

	public String getSoremarks() {
		return soremarks;
	}

	public void setSoremarks(String soremarks) {
		this.soremarks = soremarks;
	}

	public void setTotalactualcost(String totalactualcost) {
		this.totalactualcost = totalactualcost;
	}

	public String getTblairportcd() {
		return tblairportcd;
	}

	public void setTblairportcd(String tblairportcd) {
		this.tblairportcd = tblairportcd;
	}

	public String getTotalIntInstallAmt() {
		return totalIntInstallAmt;
	}

	public void setTotalIntInstallAmt(String totalIntInstallAmt) {
		this.totalIntInstallAmt = totalIntInstallAmt;
	}

	public String getTotalMthInstallAmt() {
		return totalMthInstallAmt;
	}

	public void setTotalMthInstallAmt(String totalMthInstallAmt) {
		this.totalMthInstallAmt = totalMthInstallAmt;
	}

	public String getTotalrequiredamt() {
		return totalrequiredamt;
	}

	public void setTotalrequiredamt(String totalrequiredamt) {
		this.totalrequiredamt = totalrequiredamt;
	}

	public String getTotalsanctionedamt() {
		return totalsanctionedamt;
	}

	public void setTotalsanctionedamt(String totalsanctionedamt) {
		this.totalsanctionedamt = totalsanctionedamt;
	}

	public String getTotalsubamt() {
		return totalsubamt;
	}

	public void setTotalsubamt(String totalsubamt) {
		this.totalsubamt = totalsubamt;
	}

	public String getAcquireflatamount() {
		return acquireflatamount;
	}

	public void setAcquireflatamount(String acquireflatamount) {
		this.acquireflatamount = acquireflatamount;
	}

	public String getAcquireflatcount() {
		return acquireflatcount;
	}

	public void setAcquireflatcount(String acquireflatcount) {
		this.acquireflatcount = acquireflatcount;
	}

	public String getConstructionhouseamount() {
		return constructionhouseamount;
	}

	public void setConstructionhouseamount(String constructionhouseamount) {
		this.constructionhouseamount = constructionhouseamount;
	}

	public String getConstructionhousecount() {
		return constructionhousecount;
	}

	public void setConstructionhousecount(String constructionhousecount) {
		this.constructionhousecount = constructionhousecount;
	}

	public String getDependenteducationamount() {
		return dependenteducationamount;
	}

	public void setDependenteducationamount(String dependenteducationamount) {
		this.dependenteducationamount = dependenteducationamount;
	}

	public String getDependenteducationcount() {
		return dependenteducationcount;
	}

	public void setDependenteducationcount(String dependenteducationcount) {
		this.dependenteducationcount = dependenteducationcount;
	}

	public String getDependentmarriageamount() {
		return dependentmarriageamount;
	}

	public void setDependentmarriageamount(String dependentmarriageamount) {
		this.dependentmarriageamount = dependentmarriageamount;
	}

	public String getDependentmarriagecount() {
		return dependentmarriagecount;
	}

	public void setDependentmarriagecount(String dependentmarriagecount) {
		this.dependentmarriagecount = dependentmarriagecount;
	}

	public String getHbaothersamount() {
		return hbaothersamount;
	}

	public void setHbaothersamount(String hbaothersamount) {
		this.hbaothersamount = hbaothersamount;
	}

	public String getHbaotherscount() {
		return hbaotherscount;
	}

	public void setHbaotherscount(String hbaotherscount) {
		this.hbaotherscount = hbaotherscount;
	}

	public String getPurchasehouseamount() {
		return purchasehouseamount;
	}

	public void setPurchasehouseamount(String purchasehouseamount) {
		this.purchasehouseamount = purchasehouseamount;
	}

	public String getPurchasehousecount() {
		return purchasehousecount;
	}

	public void setPurchasehousecount(String purchasehousecount) {
		this.purchasehousecount = purchasehousecount;
	}

	public String getPurchasesiteamount() {
		return purchasesiteamount;
	}

	public void setPurchasesiteamount(String purchasesiteamount) {
		this.purchasesiteamount = purchasesiteamount;
	}

	public String getPurchasesitecount() {
		return purchasesitecount;
	}

	public void setPurchasesitecount(String purchasesitecount) {
		this.purchasesitecount = purchasesitecount;
	}

	public String getReccount() {
		return reccount;
	}

	public void setReccount(String reccount) {
		this.reccount = reccount;
	}

	public String getRenovationhouseamount() {
		return renovationhouseamount;
	}

	public void setRenovationhouseamount(String renovationhouseamount) {
		this.renovationhouseamount = renovationhouseamount;
	}

	public String getRenovationhousecount() {
		return renovationhousecount;
	}

	public void setRenovationhousecount(String renovationhousecount) {
		this.renovationhousecount = renovationhousecount;
	}

	public String getRepaymenthbaamount() {
		return repaymenthbaamount;
	}

	public void setRepaymenthbaamount(String repaymenthbaamount) {
		this.repaymenthbaamount = repaymenthbaamount;
	}

	public String getRepaymenthbacount() {
		return repaymenthbacount;
	}

	public void setRepaymenthbacount(String repaymenthbacount) {
		this.repaymenthbacount = repaymenthbacount;
	}

	public String getSelfmarriageamount() {
		return selfmarriageamount;
	}

	public void setSelfmarriageamount(String selfmarriageamount) {
		this.selfmarriageamount = selfmarriageamount;
	}

	public String getSelfmarriagecount() {
		return selfmarriagecount;
	}

	public void setSelfmarriagecount(String selfmarriagecount) {
		this.selfmarriagecount = selfmarriagecount;
	}

	public String getArreardate() {
		return arreardate;
	}

	public void setArreardate(String arreardate) {
		this.arreardate = arreardate;
	}

	public String getArreartype() {
		return arreartype;
	}

	public void setArreartype(String arreartype) {
		this.arreartype = arreartype;
	}

	public String getFromfinyear() {
		return fromfinyear;
	}

	public void setFromfinyear(String fromfinyear) {
		this.fromfinyear = fromfinyear;
	}

	public String getInterestratefrom() {
		return interestratefrom;
	}

	public void setInterestratefrom(String interestratefrom) {
		this.interestratefrom = interestratefrom;
	}

	public String getInterestrateto() {
		return interestrateto;
	}

	public void setInterestrateto(String interestrateto) {
		this.interestrateto = interestrateto;
	}

	public String getTofinyear() {
		return tofinyear;
	}

	public void setTofinyear(String tofinyear) {
		this.tofinyear = tofinyear;
	}

	public AdvanceBasicReportBean(HttpServletRequest request) {
		super(request);
	}

	public AdvanceBasicReportBean() {
	}

	public String getAdhocamt() {
		return adhocamt;
	}

	public void setAdhocamt(String adhocamt) {
		this.adhocamt = adhocamt;
	}

	public String getAirportcd() {
		return airportcd;
	}

	public void setAirportcd(String airportcd) {
		this.airportcd = airportcd;
	}

	public String getAmtadmtdate() {
		return amtadmtdate;
	}

	public void setAmtadmtdate(String amtadmtdate) {
		this.amtadmtdate = amtadmtdate;
	}

	public String getEmplrshare() {
		return emplrshare;
	}

	public void setEmplrshare(String emplrshare) {
		this.emplrshare = emplrshare;
	}

	public String getEmplshare() {
		return emplshare;
	}

	public void setEmplshare(String emplshare) {
		this.emplshare = emplshare;
	}

	public String getNetcontribution() {
		return netcontribution;
	}

	public void setNetcontribution(String netcontribution) {
		this.netcontribution = netcontribution;
	}

	public String getNextdaydate() {
		return nextdaydate;
	}

	public void setNextdaydate(String nextdaydate) {
		this.nextdaydate = nextdaydate;
	}

	public String getNomineename() {
		return nomineename;
	}

	public void setNomineename(String nomineename) {
		this.nomineename = nomineename;
	}

	public String getPensioncontribution() {
		return pensioncontribution;
	}

	public void setPensioncontribution(String pensioncontribution) {
		this.pensioncontribution = pensioncontribution;
	}

	public String getSanctiondt() {
		return sanctiondt;
	}

	public void setSanctiondt(String sanctiondt) {
		this.sanctiondt = sanctiondt;
	}

	public List getSanctionList() {
		return sanctionList;
	}

	public void setSanctionList(List sanctionList) {
		this.sanctionList = sanctionList;
	}

	public String getSanctionno() {
		return sanctionno;
	}

	public void setSanctionno(String sanctionno) {
		this.sanctionno = sanctionno;
	}

	public String getSeperationdate() {
		return seperationdate;
	}

	public void setSeperationdate(String seperationdate) {
		this.seperationdate = seperationdate;
	}

	public String getSeperationreason() {
		return seperationreason;
	}

	public void setSeperationreason(String seperationreason) {
		this.seperationreason = seperationreason;
	}

	// --------End Note Sheet Properties-------
	public String getCpfIntallments() {
		return cpfIntallments;
	}

	public void setCpfIntallments(String cpfIntallments) {
		this.cpfIntallments = cpfIntallments;
	}

	public String getOblCermoney() {
		return oblCermoney;
	}

	public void setOblCermoney(String oblCermoney) {
		this.oblCermoney = oblCermoney;
	}

	public String getHeRecog() {
		return heRecog;
	}

	public void setHeRecog(String heRecog) {
		this.heRecog = heRecog;
	}

	public String getBrthCertProve() {
		return brthCertProve;
	}

	public void setBrthCertProve(String brthCertProve) {
		this.brthCertProve = brthCertProve;
	}

	public String getFmlyAge() {
		return fmlyAge;
	}

	public void setFmlyAge(String fmlyAge) {
		this.fmlyAge = fmlyAge;
	}

	public String getFmlyDOB() {
		return fmlyDOB;
	}

	public void setFmlyDOB(String fmlyDOB) {
		this.fmlyDOB = fmlyDOB;
	}

	public String getFmlyEmpName() {
		return fmlyEmpName;
	}

	public void setFmlyEmpName(String fmlyEmpName) {
		this.fmlyEmpName = fmlyEmpName;
	}

	public String getPurposeOptionType() {
		return purposeOptionType;
	}

	public void setPurposeOptionType(String purposeOptionType) {
		this.purposeOptionType = purposeOptionType;
	}

	public String getAdvanceTransID() {
		return advanceTransID;
	}

	public void setAdvanceTransID(String advanceTransID) {
		this.advanceTransID = advanceTransID;
	}

	public String getAddrInstitue() {
		return addrInstitue;
	}

	public void setAddrInstitue(String addrInstitue) {
		this.addrInstitue = addrInstitue;
	}

	public String getCurseDuration() {
		return curseDuration;
	}

	public void setCurseDuration(String curseDuration) {
		this.curseDuration = curseDuration;
	}

	public String getHeLastExaminfo() {
		return heLastExaminfo;
	}

	public void setHeLastExaminfo(String heLastExaminfo) {
		this.heLastExaminfo = heLastExaminfo;
	}

	public String getNmCourse() {
		return nmCourse;
	}

	public void setNmCourse(String nmCourse) {
		this.nmCourse = nmCourse;
	}

	public String getNmInstitue() {
		return nmInstitue;
	}

	public void setNmInstitue(String nmInstitue) {
		this.nmInstitue = nmInstitue;
	}

	public String getPfwHEType() {
		return pfwHEType;
	}

	public void setPfwHEType(String pfwHEType) {
		this.pfwHEType = pfwHEType;
	}

	public String getLodInfo() {
		return lodInfo;
	}

	public void setLodInfo(String lodInfo) {
		this.lodInfo = lodInfo;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getChkWthdrwlInfo() {
		return chkWthdrwlInfo;
	}

	public void setChkWthdrwlInfo(String chkWthdrwlInfo) {
		this.chkWthdrwlInfo = chkWthdrwlInfo;
	}

	public String getWthdrwlAmount() {
		return wthdrwlAmount;
	}

	public void setWthdrwlAmount(String wthdrwlAmount) {
		this.wthdrwlAmount = wthdrwlAmount;
	}

	public String getWthDrwlTrnsdt() {
		return wthDrwlTrnsdt;
	}

	public void setWthDrwlTrnsdt(String wthDrwlTrnsdt) {
		this.wthDrwlTrnsdt = wthDrwlTrnsdt;
	}

	public String getHbadrwnfrmaai() {
		return hbadrwnfrmaai;
	}

	public void setHbadrwnfrmaai(String hbadrwnfrmaai) {
		this.hbadrwnfrmaai = hbadrwnfrmaai;
	}

	public String getHbaloanaddress() {
		return hbaloanaddress;
	}

	public void setHbaloanaddress(String hbaloanaddress) {
		this.hbaloanaddress = hbaloanaddress;
	}

	public String getHbaloanname() {
		return hbaloanname;
	}

	public void setHbaloanname(String hbaloanname) {
		this.hbaloanname = hbaloanname;
	}

	public String getHbaowneraddress() {
		return hbaowneraddress;
	}

	public void setHbaowneraddress(String hbaowneraddress) {
		this.hbaowneraddress = hbaowneraddress;
	}

	public String getHbaownerarea() {
		return hbaownerarea;
	}

	public void setHbaownerarea(String hbaownerarea) {
		this.hbaownerarea = hbaownerarea;
	}

	public String getHbaownercity() {
		return hbaownercity;
	}

	public void setHbaownercity(String hbaownercity) {
		this.hbaownercity = hbaownercity;
	}

	public String getHbaownerlocality() {
		return hbaownerlocality;
	}

	public void setHbaownerlocality(String hbaownerlocality) {
		this.hbaownerlocality = hbaownerlocality;
	}

	public String getHbaownermuncipal() {
		return hbaownermuncipal;
	}

	public void setHbaownermuncipal(String hbaownermuncipal) {
		this.hbaownermuncipal = hbaownermuncipal;
	}

	public String getHbaownername() {
		return hbaownername;
	}

	public void setHbaownername(String hbaownername) {
		this.hbaownername = hbaownername;
	}

	public String getHbaownerplotno() {
		return hbaownerplotno;
	}

	public void setHbaownerplotno(String hbaownerplotno) {
		this.hbaownerplotno = hbaownerplotno;
	}

	public String getHbapermissionaai() {
		return hbapermissionaai;
	}

	public void setHbapermissionaai(String hbapermissionaai) {
		this.hbapermissionaai = hbapermissionaai;
	}

	public String getHbapurposetype() {
		return hbapurposetype;
	}

	public void setHbapurposetype(String hbapurposetype) {
		this.hbapurposetype = hbapurposetype;
	}

	public String getHbarepaymenttype() {
		return hbarepaymenttype;
	}

	public void setHbarepaymenttype(String hbarepaymenttype) {
		this.hbarepaymenttype = hbarepaymenttype;
	}

	public String getOsamountwithinterest() {
		return osamountwithinterest;
	}

	public void setOsamountwithinterest(String osamountwithinterest) {
		this.osamountwithinterest = osamountwithinterest;
	}

	public String getAdvReasonText() {
		return advReasonText;
	}

	public void setAdvReasonText(String advReasonText) {
		this.advReasonText = advReasonText;
	}

	public String getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(String purposeType) {
		this.purposeType = purposeType;
	}

	public String getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}

	public String getAdvPurpose() {
		return advPurpose;
	}

	public void setAdvPurpose(String advPurpose) {
		this.advPurpose = advPurpose;
	}

	public String getPfwPurpose() {
		return pfwPurpose;
	}

	public void setPfwPurpose(String pfwPurpose) {
		this.pfwPurpose = pfwPurpose;
	}

	public String getEmoluments() {
		return emoluments;
	}

	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}

	public String getPfStatury() {
		return pfStatury;
	}

	public void setPfStatury(String pfStatury) {
		this.pfStatury = pfStatury;
	}

	public String getWetherOption() {
		return wetherOption;
	}

	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}

	public String getCpfaccno() {
		return cpfaccno;
	}

	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
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

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTransMnthYear() {
		return transMnthYear;
	}

	public void setTransMnthYear(String transMnthYear) {
		this.transMnthYear = transMnthYear;
	}

	public String getAdvReqAmnt() {
		return advReqAmnt;
	}

	public void setAdvReqAmnt(String advReqAmnt) {
		this.advReqAmnt = advReqAmnt;
	}

	public String getMailID() {
		return mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	public String getCPFFund() {
		return CPFFund;
	}

	public void setCPFFund(String fund) {
		CPFFund = fund;
	}

	public String getEmpage() {
		return empage;
	}

	public void setEmpage(String empage) {
		this.empage = empage;
	}

	public String getPFWrule() {
		return PFWrule;
	}

	public void setPFWrule(String wrule) {
		PFWrule = wrule;
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

	public String getWthdrwlpurpose() {
		return wthdrwlpurpose;
	}

	public void setWthdrwlpurpose(String wthdrwlpurpose) {
		this.wthdrwlpurpose = wthdrwlpurpose;
	}

	public String getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}

	public String getRegionLbl() {
		return regionLbl;
	}

	public void setRegionLbl(String regionLbl) {
		this.regionLbl = regionLbl;
	}

	public String getAmtinwords() {
		return amtinwords;
	}

	public void setAmtinwords(String amtinwords) {
		this.amtinwords = amtinwords;
	}

	public String getSeperationfavour() {
		return seperationfavour;
	}

	public void setSeperationfavour(String seperationfavour) {
		this.seperationfavour = seperationfavour;
	}

	public String getSeperationremarks() {
		return seperationremarks;
	}

	public void setSeperationremarks(String seperationremarks) {
		this.seperationremarks = seperationremarks;
	}

	public String getChkBankFlag() {
		return chkBankFlag;
	}

	public void setChkBankFlag(String chkBankFlag) {
		this.chkBankFlag = chkBankFlag;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdvtransdt() {
		return advtransdt;
	}

	public void setAdvtransdt(String advtransdt) {
		this.advtransdt = advtransdt;
	}

	public String getAdvntrnsid() {
		return advntrnsid;
	}

	public void setAdvntrnsid(String advntrnsid) {
		this.advntrnsid = advntrnsid;
	}

	public String getAdvtransstatus() {
		return advtransstatus;
	}

	public void setAdvtransstatus(String advtransstatus) {
		this.advtransstatus = advtransstatus;
	}

	public String getApprovedremarks() {
		return approvedremarks;
	}

	public void setApprovedremarks(String approvedremarks) {
		this.approvedremarks = approvedremarks;
	}

	public String getApprovedAmt() {
		return approvedAmt;
	}

	public void setApprovedAmt(String approvedAmt) {
		this.approvedAmt = approvedAmt;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRequiredAmt() {
		return requiredAmt;
	}

	public void setRequiredAmt(String requiredAmt) {
		this.requiredAmt = requiredAmt;
	}

	public String getScreenTitle() {
		return screenTitle;
	}

	public void setScreenTitle(String screenTitle) {
		this.screenTitle = screenTitle;
	}

	public String getTrust() {
		return trust;
	}

	public void setTrust(String trust) {
		this.trust = trust;
	}

	public String getActualcost() {
		return actualcost;
	}

	public void setActualcost(String actualcost) {
		this.actualcost = actualcost;
	}

	public String getPropertyaddress() {
		return propertyaddress;
	}

	public void setPropertyaddress(String propertyaddress) {
		this.propertyaddress = propertyaddress;
	}

	public String getTakenloan() {
		return takenloan;
	}

	public void setTakenloan(String takenloan) {
		this.takenloan = takenloan;
	}

	public String getDispPurposeTypeText() {
		return dispPurposeTypeText;
	}

	public void setDispPurposeTypeText(String dispPurposeTypeText) {
		this.dispPurposeTypeText = dispPurposeTypeText;
	}

	public String getDispPurposeOptionTxt() {
		return dispPurposeOptionTxt;
	}

	public void setDispPurposeOptionTxt(String dispPurposeOptionTxt) {
		this.dispPurposeOptionTxt = dispPurposeOptionTxt;
	}

	public String getPaymentinfo() {
		return paymentinfo;
	}

	public void setPaymentinfo(String paymentinfo) {
		this.paymentinfo = paymentinfo;
	}

	public String getHbawthdrwladdress() {
		return hbawthdrwladdress;
	}

	public void setHbawthdrwladdress(String hbawthdrwladdress) {
		this.hbawthdrwladdress = hbawthdrwladdress;
	}

	public String getHbawthdrwlamount() {
		return hbawthdrwlamount;
	}

	public void setHbawthdrwlamount(String hbawthdrwlamount) {
		this.hbawthdrwlamount = hbawthdrwlamount;
	}

	public String getHbawthdrwlpurpose() {
		return hbawthdrwlpurpose;
	}

	public void setHbawthdrwlpurpose(String hbawthdrwlpurpose) {
		this.hbawthdrwlpurpose = hbawthdrwlpurpose;
	}

	public String getNomineecount() {
		return nomineecount;
	}

	public void setNomineecount(String nomineecount) {
		this.nomineecount = nomineecount;
	}

	public String getMarriagedate() {
		return marriagedate;
	}

	public void setMarriagedate(String marriagedate) {
		this.marriagedate = marriagedate;
	}

	public String getWthdrwid() {
		return wthdrwid;
	}

	public void setWthdrwid(String wthdrwid) {
		this.wthdrwid = wthdrwid;
	}

	public String getPlaceofposting() {
		return placeofposting;
	}

	public void setPlaceofposting(String placeofposting) {
		this.placeofposting = placeofposting;
	}

	public List getNomineeList() {
		return nomineeList;
	}

	public void setNomineeList(List nomineeList) {
		this.nomineeList = nomineeList;
	}

	public String getPurposeTypeDescr() {
		return purposeTypeDescr;
	}

	public void setPurposeTypeDescr(String purposeTypeDescr) {
		this.purposeTypeDescr = purposeTypeDescr;
	}

	public String getAirportlabel() {
		return airportlabel;
	}

	public void setAirportlabel(String airportlabel) {
		this.airportlabel = airportlabel;
	}

	public String getInterestinstallments() {
		return interestinstallments;
	}

	public void setInterestinstallments(String interestinstallments) {
		this.interestinstallments = interestinstallments;
	}

	public String getIntinstallmentamt() {
		return intinstallmentamt;
	}

	public void setIntinstallmentamt(String intinstallmentamt) {
		this.intinstallmentamt = intinstallmentamt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNssanctionno() {
		return nssanctionno;
	}

	public void setNssanctionno(String nssanctionno) {
		this.nssanctionno = nssanctionno;
	}

	public String getPermenentaddress() {
		return permenentaddress;
	}

	public void setPermenentaddress(String permenentaddress) {
		this.permenentaddress = permenentaddress;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getPresentaddress() {
		return presentaddress;
	}

	public void setPresentaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}

	public String getVerifiedby() {
		return verifiedby;
	}

	public void setVerifiedby(String verifiedby) {
		this.verifiedby = verifiedby;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getPostheld() {
		return postheld;
	}

	public void setPostheld(String postheld) {
		this.postheld = postheld;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getColonyname() {
		return colonyname;
	}

	public void setColonyname(String colonyname) {
		this.colonyname = colonyname;
	}

	public String getEmpaddress() {
		return empaddress;
	}

	public void setEmpaddress(String empaddress) {
		this.empaddress = empaddress;
	}

	public String getEmprelation() {
		return emprelation;
	}

	public void setEmprelation(String emprelation) {
		this.emprelation = emprelation;
	}

	public String getEmpstation() {
		return empstation;
	}

	public void setEmpstation(String empstation) {
		this.empstation = empstation;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getQuarterallotment() {
		return quarterallotment;
	}

	public void setQuarterallotment(String quarterallotment) {
		this.quarterallotment = quarterallotment;
	}

	public String getQuarterno() {
		return quarterno;
	}

	public void setQuarterno(String quarterno) {
		this.quarterno = quarterno;
	}

	public String getMthinstallmentamt() {
		return mthinstallmentamt;
	}

	public void setMthinstallmentamt(String mthinstallmentamt) {
		this.mthinstallmentamt = mthinstallmentamt;
	}

	public String getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(String appointmentdate) {
		this.appointmentdate = appointmentdate;
	}

	public String getOrganizationaddress() {
		return organizationaddress;
	}

	public void setOrganizationaddress(String organizationaddress) {
		this.organizationaddress = organizationaddress;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getPostedas() {
		return postedas;
	}

	public void setPostedas(String postedas) {
		this.postedas = postedas;
	}

	public String getResignationreason() {
		return resignationreason;
	}

	public void setResignationreason(String resignationreason) {
		this.resignationreason = resignationreason;
	}

	public String getWorkingplace() {
		return workingplace;
	}

	public void setWorkingplace(String workingplace) {
		this.workingplace = workingplace;
	}

	public String getTotalAdhocAmt() {
		return totalAdhocAmt;
	}

	public void setTotalAdhocAmt(String totalAdhocAmt) {
		this.totalAdhocAmt = totalAdhocAmt;
	}

	public String getTotalConAmt() {
		return totalConAmt;
	}

	public void setTotalConAmt(String totalConAmt) {
		this.totalConAmt = totalConAmt;
	}

	public String getTotalNetConAmt() {
		return totalNetConAmt;
	}

	public void setTotalNetConAmt(String totalNetConAmt) {
		this.totalNetConAmt = totalNetConAmt;
	}

	public String getTotalPenConAmt() {
		return totalPenConAmt;
	}

	public void setTotalPenConAmt(String totalPenConAmt) {
		this.totalPenConAmt = totalPenConAmt;
	}

	public String getNomineeAppointed() {
		return nomineeAppointed;
	}

	public void setNomineeAppointed(String nomineeAppointed) {
		this.nomineeAppointed = nomineeAppointed;
	}

	public String getSanctionOrderNo() {
		return sanctionOrderNo;
	}

	public void setSanctionOrderNo(String sanctionOrderNo) {
		this.sanctionOrderNo = sanctionOrderNo;
	}

	public List getMultipleNomineeList() {
		return multipleNomineeList;
	}

	public void setMultipleNomineeList(List multipleNomineeList) {
		this.multipleNomineeList = multipleNomineeList;
	}

	public String getSanctionOrderFlag() {
		return sanctionOrderFlag;
	}

	public void setSanctionOrderFlag(String sanctionOrderFlag) {
		this.sanctionOrderFlag = sanctionOrderFlag;
	}

	public String getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(String rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public String getCbstatus() {
		return cbstatus;
	}

	public void setCbstatus(String cbstatus) {
		this.cbstatus = cbstatus;
	}

	public String getCbvocherno() {
		return cbvocherno;
	}

	public void setCbvocherno(String cbvocherno) {
		this.cbvocherno = cbvocherno;
	}

	public String getCBRemarks() {
		return CBRemarks;
	}

	public void setCBRemarks(String remarks) {
		CBRemarks = remarks;
	}

	public String getCC_Lines() {
		return CC_Lines;
	}

	public void setCC_Lines(String lines) {
		CC_Lines = lines;
	}

	public String getFinyear() {
		return finyear;
	}

	public void setFinyear(String finyear) {
		this.finyear = finyear;
	}

	public String getFS_SO_Trust() {
		return FS_SO_Trust;
	}

	public void setFS_SO_Trust(String trust) {
		FS_SO_Trust = trust;
	}

	public String getPayMntInfo() {
		return payMntInfo;
	}

	public void setPayMntInfo(String payMntInfo) {
		this.payMntInfo = payMntInfo;
	}

	public String getSO_airportCd() {
		return SO_airportCd;
	}

	public void setSO_airportCd(String cd) {
		SO_airportCd = cd;
	}

	public String getSO_As_Per_CHQ() {
		return SO_As_Per_CHQ;
	}

	public void setSO_As_Per_CHQ(String as_Per_CHQ) {
		SO_As_Per_CHQ = as_Per_CHQ;
	}

	public String getSO_Death_Notes() {
		return SO_Death_Notes;
	}

	public void setSO_Death_Notes(String death_Notes) {
		SO_Death_Notes = death_Notes;
	}

	public String getSO_Interest_for() {
		return SO_Interest_for;
	}

	public void setSO_Interest_for(String interest_for) {
		SO_Interest_for = interest_for;
	}

	public String getSO_No_Payment() {
		return SO_No_Payment;
	}

	public void setSO_No_Payment(String no_Payment) {
		SO_No_Payment = no_Payment;
	}

	public String getSO_Note_Apprd_Remarks() {
		return SO_Note_Apprd_Remarks;
	}

	public void setSO_Note_Apprd_Remarks(String note_Apprd_Remarks) {
		SO_Note_Apprd_Remarks = note_Apprd_Remarks;
	}

	public String getSOPara1() {
		return SOPara1;
	}

	public void setSOPara1(String para1) {
		SOPara1 = para1;
	}

	public String getSORefType() {
		return SORefType;
	}

	public void setSORefType(String refType) {
		SORefType = refType;
	}

	public String getTransDigitalSign() {
		return transDigitalSign;
	}

	public void setTransDigitalSign(String transDigitalSign) {
		this.transDigitalSign = transDigitalSign;
	}

	public String getTransDispSignName() {
		return transDispSignName;
	}

	public void setTransDispSignName(String transDispSignName) {
		this.transDispSignName = transDispSignName;
	}

}
