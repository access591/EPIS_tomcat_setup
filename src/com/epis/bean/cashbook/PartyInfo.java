package com.epis.bean.cashbook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;


public class  PartyInfo extends RequestBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String partyName;
	private String partyDetail;
	private String faxNo;
	private String emailId;
	private String mobileNo;
	private String contactNo;
	private String partyCode;
	private String[] bankInfo;
	private List bankDetails;
	private String moduleType;
	private String invPartyname;
	private String groupName;
	private String groupCode;
	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getInvPartyname() {
		return invPartyname;
	}

	public void setInvPartyname(String invPartyname) {
		this.invPartyname = invPartyname;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public PartyInfo(HttpServletRequest request){
		super(request);
		}
	
	public PartyInfo(){
			
	}
	public String[] getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String[] bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
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
	public List getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(List bankDetails) {
		this.bankDetails = bankDetails;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
		
}
