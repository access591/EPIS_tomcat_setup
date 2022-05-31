package com.epis.bean.investment;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
import org.apache.struts.upload.FormFile;

public class FormFillingBean extends RequestBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String proposalRefNo;
	private String trustType;
	private String securityCategory;
	private String marketType;
	private String securityName;
	private String noofBonds;
	private String investAmt;
	private String statueoftaxOption;
	private String nameofApplicant;
	private String panno;
	private String mailingAddress;
	private String contactPerson;
	private String contactNumber;
	private String hasQuotations;
	private String formCd;
	private String flags;
	private Map approvals;
	private String userId;
	private String appDate;
	private String proposaldate;
	private String subject;
	private String remarks;
	private String date;
	private FormFile uploadDocument;
	private String extName;
	private String filePath;
	private String auctionDate;
	private String arrangerDate;
	private String faxNumber;
	private String mode;
	private String status;
	private String updatedFlag;
	private String accountNo;
	private String bankName;
	private String bankAddress;
	private String accountType;
	private String partyName;
	private String partyContactNo;
	private String partyAddress;
	private String pname;
	private String pid;
	private String clientId;
	private String settlementDate;
	private String proposalSecurity;
	private String proposalSecuritydef;
	
	
	public String getProposalSecuritydef() {
		return proposalSecuritydef;
	}
	public void setProposalSecuritydef(String proposalSecuritydef) {
		this.proposalSecuritydef = proposalSecuritydef;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPartyAddress() {
		return partyAddress;
	}
	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}
	public String getPartyContactNo() {
		return partyContactNo;
	}
	public void setPartyContactNo(String partyContactNo) {
		this.partyContactNo = partyContactNo;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String id) {
		pid = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String name) {
		pname = name;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getUpdatedFlag() {
		return updatedFlag;
	}
	public void setUpdatedFlag(String updatedFlag) {
		this.updatedFlag = updatedFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getArrangerDate() {
		return arrangerDate;
	}
	public void setArrangerDate(String arrangerDate) {
		this.arrangerDate = arrangerDate;
	}
	public String getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProposaldate() {
		return proposaldate;
	}
	public void setProposaldate(String proposaldate) {
		this.proposaldate = proposaldate;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map getApprovals() {
		return approvals;
	}
	public void setApprovals(Map approvals) {
		this.approvals = approvals;
	}
	public FormFillingBean()
	{
		
	}
	public FormFillingBean(HttpServletRequest req)
	{
		super(req);
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getFormCd() {
		return formCd;
	}
	public void setFormCd(String formCd) {
		this.formCd = formCd;
	}
	public String getHasQuotations() {
		return hasQuotations;
	}
	public void setHasQuotations(String hasQuotations) {
		this.hasQuotations = hasQuotations;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getInvestAmt() {
		return investAmt;
	}
	public void setInvestAmt(String investAmt) {
		this.investAmt = investAmt;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getNameofApplicant() {
		return nameofApplicant;
	}
	public void setNameofApplicant(String nameofApplicant) {
		this.nameofApplicant = nameofApplicant;
	}
	public String getNoofBonds() {
		return noofBonds;
	}
	public void setNoofBonds(String noofBonds) {
		this.noofBonds = noofBonds;
	}
	public String getPanno() {
		return panno;
	}
	public void setPanno(String panno) {
		this.panno = panno;
	}
	public String getProposalRefNo() {
		return proposalRefNo;
	}
	public void setProposalRefNo(String proposalRefNo) {
		this.proposalRefNo = proposalRefNo;
	}
	public String getSecurityCategory() {
		return securityCategory;
	}
	public void setSecurityCategory(String securityCategory) {
		this.securityCategory = securityCategory;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getStatueoftaxOption() {
		return statueoftaxOption;
	}
	public void setStatueoftaxOption(String statueoftaxOption) {
		this.statueoftaxOption = statueoftaxOption;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public FormFile getUploadDocument() {
		return uploadDocument;
	}
	public void setUploadDocument(FormFile uploadDocument) {
		this.uploadDocument = uploadDocument;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getProposalSecurity() {
		return proposalSecurity;
	}
	public void setProposalSecurity(String proposalSecurity) {
		this.proposalSecurity = proposalSecurity;
	}
	
	

}
