package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;


public class BankMasterInfo extends RequestBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bankName;
	private String branchName;
	private String bankCode;
	private String address;
	private String phoneNo;
	private String faxNo;
	private String accountCode;
	private String particular;
	private String accountNo;
	private String accountType;
	private String IFSCCode;
	private String NEFTRTGSCode;
	private String MICRNo;
	private String contactPerson;
	private String mobileNo;
	private String trustType;
	private String unitName;
	private String region;
	private String bankDetails;
	private String accountName;
	
	public BankMasterInfo(HttpServletRequest request){
		super(request);
		}
	
	public BankMasterInfo(){
			
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getIFSCCode() {
		return IFSCCode;
	}
	public void setIFSCCode(String code) {
		IFSCCode = code;
	}
	public String getMICRNo() {
		return MICRNo;
	}
	public void setMICRNo(String no) {
		MICRNo = no;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getNEFTRTGSCode() {
		return NEFTRTGSCode;
	}
	public void setNEFTRTGSCode(String code) {
		NEFTRTGSCode = code;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getBankDetails() {
		bankDetails = bankName+" -- "+accountNo;
		return bankDetails;
	}
	public String toString() {
		return bankName+" -- "+branchName+" -- "+bankCode+" -- "+address+
		" -- "+phoneNo+" -- "+faxNo+" -- "+accountCode+" -- "+accountType+" -- "+
		IFSCCode+" -- "+NEFTRTGSCode+" -- "+MICRNo+" -- "+contactPerson+" -- "+mobileNo+
		" -- "+trustType+" -- "+unitName+" -- "+region+" -- "+accountName;
	}
}
