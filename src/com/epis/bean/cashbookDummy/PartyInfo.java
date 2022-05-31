package com.epis.bean.cashbookDummy;

import java.io.Serializable;
import java.util.List;

public class PartyInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String partyName;
	private String partyDetail;
	private String faxNo;
	private String emailId;
	private String mobileNo;
	private String enteredBy; 
	private String enteredDate;
	private String contactNo;
	private String partyCode;
	private List bankInfo;
	private String moduleType;
	private String refNo;
	private String securityName;
	private String facevalueinRs;
	private String dealDate;
	private String settlementDate;
	private String ISIN;
	
	public String getISIN() {
		return ISIN;
	}
	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getFacevalueinRs() {
		return facevalueinRs;
	}
	public void setFacevalueinRs(String facevalueinRs) {
		this.facevalueinRs = facevalueinRs;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	public List getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(List bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	public String getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(String enteredDate) {
		this.enteredDate = enteredDate;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPartyDetail() {
		return partyDetail;
	}
	public void setPartyDetail(String partyDetail) {
		this.partyDetail = partyDetail;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
}
