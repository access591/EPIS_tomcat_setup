package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvanceEditBean extends RequestBean implements Serializable{
	String employeeName="",pensionNo="",pfid="",designation="",department="",dateOfMembership="",dateOfJoining="",dateOfBirth="";
	String employeeNo="",cpfaccno="",region="",transMnthYear="",fhName="",wetherOption="",mailID="";
	String emoluments="",pfStatury="",advanceType="",pfwPurpose="",advPurpose="",purposeType="",advReasonText="",advReqAmnt="";
	String hbapurposetype  ="",hbarepaymenttype	="",hbaloanname="",hbaloanaddress="",osamountwithinterest	="0.00";
	String hbaownername="",hbaowneraddress="",hbaownerarea="",hbaownerplotno="",hbaownerlocality="",hbaownermuncipal="";
	String hbaownercity	="",hbadrwnfrmaai="",hbapermissionaai="",paymentdt="",remarks="";
	String chkWthdrwlInfo="",wthDrwlTrnsdt="",wthdrwlAmount="0.00",wthdrwlpurpose="",partyName="",partyAddress="",lodInfo="";
	String pfwHEType="",nmCourse="",nmInstitue="",addrInstitue="",curseDuration="",heLastExaminfo="",advanceTransID="",purposeOptionType="";
	String fmlyEmpName="",fmlyDOB="",fmlyAge="",brthCertProve="",heRecog="",oblCermoney="";
	String advncerqddepend="",utlisiedamntdrwn="",seperationfavour="",seperationremarks="",nssanctionno="",chkBankFlag="",trust="";
	String station="",paymentinfo="",cpftotalinstall="",chkwthdrwlinfo="",empfmlydtls="",placeofposting="";
	
	String hbawthdrwlpurpose="",hbawthdrwlamount="",hbawthdrwladdress="",marriagedate="",wthdrwlist="",actualcost="",propertyaddress="";
	String modeofpartyname="",modeofpartyaddrs="",bankdetail="",createdDate="";
	String bankempname="",bankempaddrs="",bankname="",branchaddress="",banksavingaccno="",bankemprtgsneftcode="",empmailid="",bankempmicrono="";
	String advancecostexp="",cpfmarriageexp="",updateBankFlag="";
	
	public String getUpdateBankFlag() {
		return updateBankFlag;
	}

	public void setUpdateBankFlag(String updateBankFlag) {
		this.updateBankFlag = updateBankFlag;
	}

	public AdvanceEditBean(HttpServletRequest request){
		super(request);
		}
	
	public AdvanceEditBean(){
	}
	
	
	public String getAddrInstitue() {
		return addrInstitue;
	}
	public void setAddrInstitue(String addrInstitue) {
		this.addrInstitue = addrInstitue;
	}
	public String getAdvanceTransID() {
		return advanceTransID;
	}
	public void setAdvanceTransID(String advanceTransID) {
		this.advanceTransID = advanceTransID;
	}
	public String getAdvanceType() {
		return advanceType;
	}
	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}
	public String getAdvncerqddepend() {
		return advncerqddepend;
	}
	public void setAdvncerqddepend(String advncerqddepend) {
		this.advncerqddepend = advncerqddepend;
	}
	public String getAdvPurpose() {
		return advPurpose;
	}
	public void setAdvPurpose(String advPurpose) {
		this.advPurpose = advPurpose;
	}
	public String getAdvReasonText() {
		return advReasonText;
	}
	public void setAdvReasonText(String advReasonText) {
		this.advReasonText = advReasonText;
	}
	public String getAdvReqAmnt() {
		return advReqAmnt;
	}
	public void setAdvReqAmnt(String advReqAmnt) {
		this.advReqAmnt = advReqAmnt;
	}
	public String getBrthCertProve() {
		return brthCertProve;
	}
	public void setBrthCertProve(String brthCertProve) {
		this.brthCertProve = brthCertProve;
	}
	public String getChkBankFlag() {
		return chkBankFlag;
	}
	public void setChkBankFlag(String chkBankFlag) {
		this.chkBankFlag = chkBankFlag;
	}
	
	public String getCpfaccno() {
		return cpfaccno;
	}
	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
	}
	
	public String getCurseDuration() {
		return curseDuration;
	}
	public void setCurseDuration(String curseDuration) {
		this.curseDuration = curseDuration;
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
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
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
	public String getHeLastExaminfo() {
		return heLastExaminfo;
	}
	public void setHeLastExaminfo(String heLastExaminfo) {
		this.heLastExaminfo = heLastExaminfo;
	}
	public String getHeRecog() {
		return heRecog;
	}
	public void setHeRecog(String heRecog) {
		this.heRecog = heRecog;
	}
	public String getLodInfo() {
		return lodInfo;
	}
	public void setLodInfo(String lodInfo) {
		this.lodInfo = lodInfo;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
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
	public String getNssanctionno() {
		return nssanctionno;
	}
	public void setNssanctionno(String nssanctionno) {
		this.nssanctionno = nssanctionno;
	}
	public String getOblCermoney() {
		return oblCermoney;
	}
	public void setOblCermoney(String oblCermoney) {
		this.oblCermoney = oblCermoney;
	}
	public String getOsamountwithinterest() {
		return osamountwithinterest;
	}
	public void setOsamountwithinterest(String osamountwithinterest) {
		this.osamountwithinterest = osamountwithinterest;
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
	public String getPaymentdt() {
		return paymentdt;
	}
	public void setPaymentdt(String paymentdt) {
		this.paymentdt = paymentdt;
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
	public String getPfStatury() {
		return pfStatury;
	}
	public void setPfStatury(String pfStatury) {
		this.pfStatury = pfStatury;
	}
	public String getPfwHEType() {
		return pfwHEType;
	}
	public void setPfwHEType(String pfwHEType) {
		this.pfwHEType = pfwHEType;
	}
	public String getPfwPurpose() {
		return pfwPurpose;
	}
	public void setPfwPurpose(String pfwPurpose) {
		this.pfwPurpose = pfwPurpose;
	}
	public String getPurposeOptionType() {
		return purposeOptionType;
	}
	public void setPurposeOptionType(String purposeOptionType) {
		this.purposeOptionType = purposeOptionType;
	}
	public String getPurposeType() {
		return purposeType;
	}
	public void setPurposeType(String purposeType) {
		this.purposeType = purposeType;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public String getTransMnthYear() {
		return transMnthYear;
	}
	public void setTransMnthYear(String transMnthYear) {
		this.transMnthYear = transMnthYear;
	}
	public String getUtlisiedamntdrwn() {
		return utlisiedamntdrwn;
	}
	public void setUtlisiedamntdrwn(String utlisiedamntdrwn) {
		this.utlisiedamntdrwn = utlisiedamntdrwn;
	}
	public String getWetherOption() {
		return wetherOption;
	}
	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}
	
	public String getWthdrwlpurpose() {
		return wthdrwlpurpose;
	}
	public void setWthdrwlpurpose(String wthdrwlpurpose) {
		this.wthdrwlpurpose = wthdrwlpurpose;
	}
	
	public String getTrust() {
		return trust;
	}
	public void setTrust(String trust) {
		this.trust = trust;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
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
	
	public String getModeofpartyname() {
		return modeofpartyname;
	}
	public void setModeofpartyname(String modeofpartyname) {
		this.modeofpartyname = modeofpartyname;
	}
	public String getModeofpartyaddrs() {
		return modeofpartyaddrs;
	}
	public void setModeofpartyaddrs(String modeofpartyaddrs) {
		this.modeofpartyaddrs = modeofpartyaddrs;
	}
	public String getBankdetail() {
		return bankdetail;
	}
	public void setBankdetail(String bankdetail) {
		this.bankdetail = bankdetail;
	}
	public String getBankempaddrs() {
		return bankempaddrs;
	}
	public void setBankempaddrs(String bankempaddrs) {
		this.bankempaddrs = bankempaddrs;
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
	public String getBankempmicrono() {
		return bankempmicrono;
	}
	public void setBankempmicrono(String bankempmicrono) {
		this.bankempmicrono = bankempmicrono;
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
	public String getBranchaddress() {
		return branchaddress;
	}
	public void setBranchaddress(String branchaddress) {
		this.branchaddress = branchaddress;
	}
	public String getEmpmailid() {
		return empmailid;
	}
	public void setEmpmailid(String empmailid) {
		this.empmailid = empmailid;
	}
	public String getCpftotalinstall() {
		return cpftotalinstall;
	}
	public void setCpftotalinstall(String cpftotalinstall) {
		this.cpftotalinstall = cpftotalinstall;
	}
	public String getChkwthdrwlinfo() {
		return chkwthdrwlinfo;
	}
	public void setChkwthdrwlinfo(String chkwthdrwlinfo) {
		this.chkwthdrwlinfo = chkwthdrwlinfo;
	}
	public String getEmpfmlydtls() {
		return empfmlydtls;
	}
	public void setEmpfmlydtls(String empfmlydtls) {
		this.empfmlydtls = empfmlydtls;
	}
	public String getPlaceofposting() {
		return placeofposting;
	}
	public void setPlaceofposting(String placeofposting) {
		this.placeofposting = placeofposting;
	}
	public String getAdvancecostexp() {
		return advancecostexp;
	}
	public void setAdvancecostexp(String advancecostexp) {
		this.advancecostexp = advancecostexp;
	}
	public String getCpfmarriageexp() {
		return cpfmarriageexp;
	}
	public void setCpfmarriageexp(String cpfmarriageexp) {
		this.cpfmarriageexp = cpfmarriageexp;
	}

	public String getChkWthdrwlInfo() {
		return chkWthdrwlInfo;
	}

	public void setChkWthdrwlInfo(String chkWthdrwlInfo) {
		this.chkWthdrwlInfo = chkWthdrwlInfo;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	

}
