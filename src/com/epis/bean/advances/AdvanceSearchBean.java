package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AdvanceSearchBean extends RequestBean implements Serializable{
	String	advanceTransID="",employeeName="",pensionNo="",pfid="",advanceTransyear="",advanceType="",purposeType="",advanceStatus="",trust="",station="";;
	String advanceTransIDDec="",advanceTrnStatus="",paymentdt="",seperationreason="",transMnthYear="",formName="",verifiedBy="", transactionStatus="";;
//	-----------NoteSheet Properties---------
	String sanctionno="",sanctiondt="",cpfIntallments="",requiredamt="",emplshare="",emplrshare="",pensioncontribution="",netcontribution="",region="",designation="";
	 String transdt="",finalTrnStatus="",fsType="",requestType="",paymentinfo="";
	 String arreartype="",arreardate="",fromfinyear="",tofinyear="",interestratefrom="",interestrateto="",nssanctionno="",checkListStatus="";
	 String verifiedByStatus="",keyNo="",userName="",profileName="",userId="",imgagePath="",dirPath="",voucherNo="",revAdvanceTransID="",finalSettlementDate="",reSettlementDate="";
//	-----------End NoteSheet Properties---------

	public String getNssanctionno() {
		return nssanctionno;
	}

	public String getVerifiedByStatus() {
		return verifiedByStatus;
	}

	public void setVerifiedByStatus(String verifiedByStatus) {
		this.verifiedByStatus = verifiedByStatus;
	}

	public void setNssanctionno(String nssanctionno) {
		this.nssanctionno = nssanctionno;
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

	public String getFinalTrnStatus() {
		return finalTrnStatus;
	}

	public void setFinalTrnStatus(String finalTrnStatus) {
		this.finalTrnStatus = finalTrnStatus;
	}

	public String getTransdt() {
		return transdt;
	}

	public void setTransdt(String transdt) {
		this.transdt = transdt;
	}

	public AdvanceSearchBean(HttpServletRequest request){
		super(request);
		}
	
	public AdvanceSearchBean(){
	}

public String getAdvanceTrnStatus() {
	return advanceTrnStatus;
}

public String getVerifiedBy() {
	return verifiedBy;
}

public void setVerifiedBy(String verifiedBy) {
	this.verifiedBy = verifiedBy;
}

public void setAdvanceTrnStatus(String advanceTrnStatus) {
	this.advanceTrnStatus = advanceTrnStatus;
}

public String getAdvanceTransIDDec() {
	return advanceTransIDDec;
}

public void setAdvanceTransIDDec(String advanceTransIDDec) {
	this.advanceTransIDDec = advanceTransIDDec;
}

public String getAdvanceStatus() {
	return advanceStatus;
}

public void setAdvanceStatus(String advanceStatus) {
	this.advanceStatus = advanceStatus;
}

public String getAdvanceTransID() {
	return advanceTransID;
}

public void setAdvanceTransID(String advanceTransID) {
	this.advanceTransID = advanceTransID;
}

public String getAdvanceTransyear() {
	return advanceTransyear;
}

public void setAdvanceTransyear(String advanceTransyear) {
	this.advanceTransyear = advanceTransyear;
}

public String getAdvanceType() {
	return advanceType;
}

public void setAdvanceType(String advanceType) {
	this.advanceType = advanceType;
}

public String getEmployeeName() {
	return employeeName;
}

public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
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

public String getPurposeType() {
	return purposeType;
}

public void setPurposeType(String purposeType) {
	this.purposeType = purposeType;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
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

public String getPensioncontribution() {
	return pensioncontribution;
}

public void setPensioncontribution(String pensioncontribution) {
	this.pensioncontribution = pensioncontribution;
}

public String getRegion() {
	return region;
}

public void setRegion(String region) {
	this.region = region;
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

public String getPaymentdt() {
	return paymentdt;
}

public void setPaymentdt(String paymentdt) {
	this.paymentdt = paymentdt;
}

public String getSeperationreason() {
	return seperationreason;
}

public void setSeperationreason(String seperationreason) {
	this.seperationreason = seperationreason;
}

public String getTransMnthYear() {
	return transMnthYear;
}

public void setTransMnthYear(String transMnthYear) {
	this.transMnthYear = transMnthYear;
}

public String getFormName() {
	return formName;
}

public void setFormName(String formName) {
	this.formName = formName;
}

public String getRequiredamt() {
	return requiredamt;
}

public void setRequiredamt(String requiredamt) {
	this.requiredamt = requiredamt;
}

public String getCpfIntallments() {
	return cpfIntallments;
}

public void setCpfIntallments(String cpfIntallments) {
	this.cpfIntallments = cpfIntallments;
}

public String getTransactionStatus() {
	return transactionStatus;
}

public void setTransactionStatus(String transactionStatus) {
	this.transactionStatus = transactionStatus;
}

public String getStation() {
	return station;
}

public void setStation(String station) {
	this.station = station;
}

public String getTrust() {
	return trust;
}

public void setTrust(String trust) {
	this.trust = trust;
}

public String getFsType() {
	return fsType;
}

public void setFsType(String fsType) {
	this.fsType = fsType;
}

public String getRequestType() {
	return requestType;
}

public void setRequestType(String requestType) {
	this.requestType = requestType;
}

public String getPaymentinfo() {
	return paymentinfo;
}

public void setPaymentinfo(String paymentinfo) {
	this.paymentinfo = paymentinfo;
}

public String getKeyNo() {
	return keyNo;
}

public void setKeyNo(String keyNo) {
	this.keyNo = keyNo;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getProfileName() {
	return profileName;
}

public void setProfileName(String profileName) {
	this.profileName = profileName;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
} 

public String getCheckListStatus() {
	return checkListStatus;
}

public void setCheckListStatus(String checkListStatus) {
	this.checkListStatus = checkListStatus;
}

public String getDirPath() {
	return dirPath;
}

public void setDirPath(String dirPath) {
	this.dirPath = dirPath;
}

public String getImgagePath() {
	return imgagePath;
}

public void setImgagePath(String imgagePath) {
	this.imgagePath = imgagePath;
}

public String getVoucherNo() {
	return voucherNo;
}

public void setVoucherNo(String voucherNo) {
	this.voucherNo = voucherNo;
}

public String getRevAdvanceTransID() {
	return revAdvanceTransID;
}

public void setRevAdvanceTransID(String revAdvanceTransID) {
	this.revAdvanceTransID = revAdvanceTransID;
}

public String getFinalSettlementDate() {
	return finalSettlementDate;
}

public void setFinalSettlementDate(String finalSettlementDate) {
	this.finalSettlementDate = finalSettlementDate;
}

public String getReSettlementDate() {
	return reSettlementDate;
}

public void setReSettlementDate(String reSettlementDate) {
	this.reSettlementDate = reSettlementDate;
}


}
