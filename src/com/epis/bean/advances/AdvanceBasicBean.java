/**
  * File       : AdvanceBasicBean.java
  * Date       : 09/25/2009
  * Author     : Suresh Kumar Repaka 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvanceBasicBean extends RequestBean implements Serializable {
String employeeName="",remarksFlag="",pensionNo="",pfid="",designation="",department="",dateOfMembership="",dateOfJoining="",dateOfBirth="",advInsMonths="";
String employeeNo="",cpfaccno="",region="",transMnthYear="",fhName="",wetherOption="",mailID="";
String emoluments="",pfStatury="",advanceType="",pfwPurpose="",advPurpose="",purposeType="",advReasonText="",advReqAmnt="",pfwReqAmnt="",firstInsConrtiAmnt="0.00",firstInsSubAmnt="0.00",recEmpSubAmnt="0.00",recEmpConrtiAmnt="0.00";
String hbapurposetype  ="",hbarepaymenttype	="",hbaloanname="",hbaloanaddress="",osamountwithinterest	="0.00";
String hbaownername="",hbaowneraddress="",hbaownerarea="",hbaownerplotno="",hbaownerlocality="",hbaownermuncipal="";
String hbaownercity	="",hbadrwnfrmaai="",hbapermissionaai="",paymentdt="",remarks="";
String resignationreason="",organizationname="",organizationaddress="",appointmentdate="",postedas="",workingplace="",deathplace="";
String chkWthdrwlInfo="",wthDrwlTrnsdt="",wthdrwlAmount="0.00",wthdrwlpurpose="",partyName="",partyAddress="",lodInfo="";
String pfwHEType="",nmCourse="",nmInstitue="",addrInstitue="",curseDuration="",heLastExaminfo="",advanceTransID="",purposeOptionType="";
String fmlyEmpName="",fmlyDOB="",fmlyAge="",brthCertProve="",heRecog="",oblCermoney="",cpfIntallments="",userName="";
String advncerqddepend="",utlisiedamntdrwn="",seperationfavour="",seperationremarks="",nssanctionno="",chkBankFlag="",screenTitle="",frmName="";
//-----------NoteSheet Properties---------
String hbawthdrwlpurpose="",hbawthdrwlamount="",bankdetail="",hbawthdrwladdress="",marriagedate="",wthdrwlist="",verifiedby="";
String permenentaddress="",presentaddress="",phoneno="",authrizedStatus="",postingdetails="";;
String trust="",actualcost="",propertyaddress="",takenloan="",paymentinfo="",placeofposting="",adhocamt="";
String seperationreason="",seperationdate="",nomineename="",amtadmtdate="",emplshare="",emplrshare="",pensioncontribution="",netcontribution="",station="";
String sanctionno="",sanctiondt="",serialNo="",nomineeDOB="",nomineeRow="",nomineeaddress="",nomineerelation="",nomineeflag="Y",gardianname="",gardianaddress="",totalshare="";
String empage="",maritalstatus="",emprelation="",empaddress="",colonyname="",empmailid="",quarterno="",quarterallotment="",empstation="";
//-----------End NoteSheet Properties---------
String transremarks="",prevTransRemarks="",bankempname  ="",bankname  ="",banksavingaccno="",bankemprtgsneftcode="";
String arreartype="",arreardate="",fromfinyear="",tofinyear="",interestratefrom="",interestrateto="",transdt="",updateBankFlag="";
String equalshare="",postingRegion="",postingStation="",postingFlag="",soremarks="",dpfromfinyear="",dptofinyear="",profileName="",accountType="",reSettlementDate="",finalSettlementDate="";
public String getSoremarks() {
	return soremarks;
}

public String getDpfromfinyear() {
	return dpfromfinyear;
}

public void setDpfromfinyear(String dpfromfinyear) {
	this.dpfromfinyear = dpfromfinyear;
}

public String getDptofinyear() {
	return dptofinyear;
}

public void setDptofinyear(String dptofinyear) {
	this.dptofinyear = dptofinyear;
}

public void setSoremarks(String soremarks) {
	this.soremarks = soremarks;
}

public String getEqualshare() {
	return equalshare;
}

public void setEqualshare(String equalshare) {
	this.equalshare = equalshare;
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

public String getEmpage() {
	return empage;
}

public void setEmpage(String empage) {
	this.empage = empage;
}

public String getEmpmailid() {
	return empmailid;
}

public void setEmpmailid(String empmailid) {
	this.empmailid = empmailid;
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

public AdvanceBasicBean(HttpServletRequest request){
	super(request);
	}

public AdvanceBasicBean(){
}

public String getCpfIntallments() {
	return cpfIntallments;
}
public void setCpfIntallments(String cpfIntallments) {
	this.cpfIntallments = cpfIntallments;
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
public String getPurposeOptionType() {
	return purposeOptionType;
}
public void setPurposeOptionType(String purposeOptionType) {
	this.purposeOptionType = purposeOptionType;
}
public String getOblCermoney() {
	return oblCermoney;
}
public void setOblCermoney(String oblCermoney) {
	this.oblCermoney = oblCermoney;
}
public String getWthdrwlpurpose() {
	return wthdrwlpurpose;
}
public void setWthdrwlpurpose(String wthdrwlpurpose) {
	this.wthdrwlpurpose = wthdrwlpurpose;
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
public String getGardianaddress() {
	return gardianaddress;
}
public void setGardianaddress(String gardianaddress) {
	this.gardianaddress = gardianaddress;
}
public String getGardianname() {
	return gardianname;
}
public void setGardianname(String gardianname) {
	this.gardianname = gardianname;
}
public String getNetcontribution() {
	return netcontribution;
}
public void setNetcontribution(String netcontribution) {
	this.netcontribution = netcontribution;
}
public String getNomineeaddress() {
	return nomineeaddress;
}
public void setNomineeaddress(String nomineeaddress) {
	this.nomineeaddress = nomineeaddress;
}
public String getNomineeDOB() {
	return nomineeDOB;
}
public void setNomineeDOB(String nomineeDOB) {
	this.nomineeDOB = nomineeDOB;
}
public String getNomineeflag() {
	return nomineeflag;
}
public void setNomineeflag(String nomineeflag) {
	this.nomineeflag = nomineeflag;
}
public String getNomineename() {
	return nomineename;
}
public void setNomineename(String nomineename) {
	this.nomineename = nomineename;
}
public String getNomineerelation() {
	return nomineerelation;
}
public void setNomineerelation(String nomineerelation) {
	this.nomineerelation = nomineerelation;
}
public String getNomineeRow() {
	return nomineeRow;
}
public void setNomineeRow(String nomineeRow) {
	this.nomineeRow = nomineeRow;
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
public String getSerialNo() {
	return serialNo;
}
public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
}
public String getTotalshare() {
	return totalshare;
}
public void setTotalshare(String totalshare) {
	this.totalshare = totalshare;
}
public String getStation() {
	return station;
}
public void setStation(String station) {
	this.station = station;
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
public String getPaymentdt() {
	return paymentdt;
}
public void setPaymentdt(String paymentdt) {
	this.paymentdt = paymentdt;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
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
public String getNssanctionno() {
	return nssanctionno;
}
public void setNssanctionno(String nssanctionno) {
	this.nssanctionno = nssanctionno;
}
public String getChkBankFlag() {
	return chkBankFlag;
}
public void setChkBankFlag(String chkBankFlag) {
	this.chkBankFlag = chkBankFlag;
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
public String getMarriagedate() {
	return marriagedate;
}
public void setMarriagedate(String marriagedate) {
	this.marriagedate = marriagedate;
}
public String getWthdrwlist() {
	return wthdrwlist;
}
public void setWthdrwlist(String wthdrwlist) {
	this.wthdrwlist = wthdrwlist;
}
public String getPlaceofposting() {
	return placeofposting;
}
public void setPlaceofposting(String placeofposting) {
	this.placeofposting = placeofposting;
}
public String getAdhocamt() {
	return adhocamt;
}
public void setAdhocamt(String adhocamt) {
	this.adhocamt = adhocamt;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getScreenTitle() {
	return screenTitle;
}
public void setScreenTitle(String screenTitle) {
	this.screenTitle = screenTitle;
}
public String getFrmName() {
	return frmName;
}
public void setFrmName(String frmName) {
	this.frmName = frmName;
}

public String getAuthrizedStatus() {
	return authrizedStatus;
}

public void setAuthrizedStatus(String authrizedStatus) {
	this.authrizedStatus = authrizedStatus;
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

public String getPostingdetails() {
	return postingdetails;
}

public void setPostingdetails(String postingdetails) {
	this.postingdetails = postingdetails;
}

public String getVerifiedby() {
	return verifiedby;
}

public void setVerifiedby(String verifiedby) {
	this.verifiedby = verifiedby;
}

public String getBankdetail() {
	return bankdetail;
}

public void setBankdetail(String bankdetail) {
	this.bankdetail = bankdetail;
}

public String getAppointmentdate() {
	return appointmentdate;
}

public void setAppointmentdate(String appointmentdate) {
	this.appointmentdate = appointmentdate;
}

public String getDeathplace() {
	return deathplace;
}

public void setDeathplace(String deathplace) {
	this.deathplace = deathplace;
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

public String getPostingFlag() {
	return postingFlag;
}

public void setPostingFlag(String postingFlag) {
	this.postingFlag = postingFlag;
}

public String getPostingRegion() {
	return postingRegion;
}

public void setPostingRegion(String postingRegion) {
	this.postingRegion = postingRegion;
}

public String getPostingStation() {
	return postingStation;
}

public void setPostingStation(String postingStation) {
	this.postingStation = postingStation;
}

public String getRemarksFlag() {
	return remarksFlag;
}

public void setRemarksFlag(String remarksFlag) {
	this.remarksFlag = remarksFlag;
}

public String getPrevTransRemarks() {
	return prevTransRemarks;
}

public void setPrevTransRemarks(String prevTransRemarks) {
	this.prevTransRemarks = prevTransRemarks;
}

public String getTransremarks() {
	return transremarks;
}

public void setTransremarks(String transremarks) {
	this.transremarks = transremarks;
}

public String getTransdt() {
	return transdt;
}

public void setTransdt(String transdt) {
	this.transdt = transdt;
}

public String getUpdateBankFlag() {
	return updateBankFlag;
}

public void setUpdateBankFlag(String updateBankFlag) {
	this.updateBankFlag = updateBankFlag;
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

public String getProfileName() {
	return profileName;
}

public void setProfileName(String profileName) {
	this.profileName = profileName;
}

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public String getReSettlementDate() {
	return reSettlementDate;
}

public void setReSettlementDate(String reSettlementDate) {
	this.reSettlementDate = reSettlementDate;
}

public String getFinalSettlementDate() {
	return finalSettlementDate;
}

public void setFinalSettlementDate(String finalSettlementDate) {
	this.finalSettlementDate = finalSettlementDate;
}

public String getPfwReqAmnt() {
	return pfwReqAmnt;
}

public void setPfwReqAmnt(String pfwReqAmnt) {
	this.pfwReqAmnt = pfwReqAmnt;
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

public String getRecEmpConrtiAmnt() {
	return recEmpConrtiAmnt;
}

public void setRecEmpConrtiAmnt(String recEmpConrtiAmnt) {
	this.recEmpConrtiAmnt = recEmpConrtiAmnt;
}

public String getRecEmpSubAmnt() {
	return recEmpSubAmnt;
}

public void setRecEmpSubAmnt(String recEmpSubAmnt) {
	this.recEmpSubAmnt = recEmpSubAmnt;
}
public String getAdvInsMonths() {
	return advInsMonths;
}

public void setAdvInsMonths(String advInsMonths) {
	this.advInsMonths = advInsMonths;
}




}
