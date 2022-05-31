package com.epis.bean.investment;
import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;


public class QuotationRequestBean  extends RequestBean{
	private String proposalRefNo;
	private String trustType;
	private String securityCategory;
	private String commonAddress;
	private String surplusFund;
    private String marketType;
    private String[] arrangers;
    private String approved;
    private String remarks;
    private String attachmenttoSelected;
    private String quotationRequestCd;
    private String letterNo;
    private String securityName;
    private String minimumQuantum;
    private String quotationAddress;
    private String quotationDate;
    private String quotationTime;
    private String validDate;
    private String validTime;
    private String openDate;
    private String openTime;
    private String currentDate;
    private String quotationCd;
    private String createdDate;
    private String host;
    private String authId;
    private String authPwd;
    private String  concrores;
    private String conInWords;
    private String conQuantumCrores;
    private String nameoftheTender;
    private String addressoftheTender;
    private String telephoneNo;
    private String faxNo;
    private String contactPerson;
    private String status;
    private String deliveryRequestedin;
    private String accountNo;
    private String marketTypedef;
    private String bankName;
    private String branch;
    private String csglAccountNo;
    private String panNo;
    private String accountType;
    private String hasQuotations;
    private String faceValue;
    private String numberOfUnits;
    
    private String investmentFaceValue;
    private String purchaseOption;
    
    private String premiumPaid;
    private String purchasePrice;
    
    private String maturityDate;
    private String securityFullName;
    private String facevalueincrores;
    private String CSGLACCOUNTNO;
    private String signPathLeft;
    private String singPathRight;
    private String signPathLeftName;
    private String singPathRightName;
    private String interestRate;
    private String interestDate;
    private String priceoffered;
    private String formateRemarks;
    private String signPath;
    private String signPathName;
    private String designation;
    private String arrangerName;
    private String arrangerAddress;
    private String modeOfPaymentRemarks;
    private String paymentThroughRemarks;
    private String fromPeriod;
    private String toPeriod;
    private HashMap documentDetails=new HashMap();
	public String getArrangerAddress() {
		return arrangerAddress;
	}
	public void setArrangerAddress(String arrangerAddress) {
		this.arrangerAddress = arrangerAddress;
	}
	public String getArrangerName() {
		return arrangerName;
	}
	public void setArrangerName(String arrangerName) {
		this.arrangerName = arrangerName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSignPathName() {
		return signPathName;
	}
	public void setSignPathName(String signPathName) {
		this.signPathName = signPathName;
	}
	public String getSignPath() {
		return signPath;
	}
	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}
	public String getFormateRemarks() {
		return formateRemarks;
	}
	public void setFormateRemarks(String formateRemarks) {
		this.formateRemarks = formateRemarks;
	}
	public String getPriceoffered() {
		return priceoffered;
	}
	public void setPriceoffered(String priceoffered) {
		this.priceoffered = priceoffered;
	}
	public String getInterestDate() {
		return interestDate;
	}
	public void setInterestDate(String interestDate) {
		this.interestDate = interestDate;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getSignPathLeft() {
		return signPathLeft;
	}
	public void setSignPathLeft(String signPathLeft) {
		this.signPathLeft = signPathLeft;
	}
	public String getSingPathRight() {
		return singPathRight;
	}
	public void setSingPathRight(String singPathRight) {
		this.singPathRight = singPathRight;
	}
	public String getCSGLACCOUNTNO() {
		return CSGLACCOUNTNO;
	}
	public void setCSGLACCOUNTNO(String csglaccountno) {
		CSGLACCOUNTNO = csglaccountno;
	}
	public String getHasQuotations() {
		return hasQuotations;
	}
	public void setHasQuotations(String hasQuotations) {
		this.hasQuotations = hasQuotations;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCsglAccountNo() {
		return csglAccountNo;
	}
	public void setCsglAccountNo(String csglAccountNo) {
		this.csglAccountNo = csglAccountNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getConQuantumCrores() {
		return conQuantumCrores;
	}
	public void setConQuantumCrores(String conQuantumCrores) {
		this.conQuantumCrores = conQuantumCrores;
	}
	public String getConInWords() {
		return conInWords;
	}
	public void setConInWords(String conInWords) {
		this.conInWords = conInWords;
	}
	public String getConcrores() {
		return concrores;
	}
	public void setConcrores(String concrores) {
		this.concrores = concrores;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getAuthPwd() {
		return authPwd;
	}
	public void setAuthPwd(String authPwd) {
		this.authPwd = authPwd;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getQuotationCd() {
		return quotationCd;
	}
	public void setQuotationCd(String quotationCd) {
		this.quotationCd = quotationCd;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getMinimumQuantum() {
		return minimumQuantum;
	}
	public void setMinimumQuantum(String minimumQuantum) {
		this.minimumQuantum = minimumQuantum;
	}
	
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getQuotationAddress() {
		return quotationAddress;
	}
	public void setQuotationAddress(String quotationAddress) {
		this.quotationAddress = quotationAddress;
	}
	public String getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
	}
	public String getQuotationTime() {
		return quotationTime;
	}
	public void setQuotationTime(String quotationTime) {
		this.quotationTime = quotationTime;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public QuotationRequestBean(HttpServletRequest request){
    	super(request);
    	}
    public QuotationRequestBean(){
    	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String[] getArrangers() {
		return arrangers;
	}
	public void setArrangers(String[] arrangers) {
		this.arrangers = arrangers;
	}
	public String getAttachmenttoSelected() {
		return attachmenttoSelected;
	}
	public void setAttachmenttoSelected(String attachmenttoSelected) {
		this.attachmenttoSelected = attachmenttoSelected;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getProposalRefNo() {
		return proposalRefNo;
	}
	public void setProposalRefNo(String proposalRefNo) {
		this.proposalRefNo = proposalRefNo;
	}
	public String getQuotationRequestCd() {
		return quotationRequestCd;
	}
	public void setQuotationRequestCd(String quotationRequestCd) {
		this.quotationRequestCd = quotationRequestCd;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSecurityCategory() {
		return securityCategory;
	}
	public void setSecurityCategory(String securityCategory) {
		this.securityCategory = securityCategory;
	}
	public String getSurplusFund() {
		return surplusFund;
	}
	public void setSurplusFund(String surplusFund) {
		this.surplusFund = surplusFund;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getCommonAddress() {
		return commonAddress;
	}
	public void setCommonAddress(String commonAddress) {
		this.commonAddress = commonAddress;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAddressoftheTender() {
		return addressoftheTender;
	}
	public void setAddressoftheTender(String addressoftheTender) {
		this.addressoftheTender = addressoftheTender;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getDeliveryRequestedin() {
		return deliveryRequestedin;
	}
	public void setDeliveryRequestedin(String deliveryRequestedin) {
		this.deliveryRequestedin = deliveryRequestedin;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getNameoftheTender() {
		return nameoftheTender;
	}
	public void setNameoftheTender(String nameoftheTender) {
		this.nameoftheTender = nameoftheTender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getMarketTypedef() {
		return marketTypedef;
	}
	public void setMarketTypedef(String marketTypedef) {
		this.marketTypedef = marketTypedef;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	public String getInvestmentFaceValue() {
		return investmentFaceValue;
	}
	public void setInvestmentFaceValue(String investmentFaceValue) {
		this.investmentFaceValue = investmentFaceValue;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getNumberOfUnits() {
		return numberOfUnits;
	}
	public void setNumberOfUnits(String numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
	public String getPremiumPaid() {
		return premiumPaid;
	}
	public void setPremiumPaid(String premiumPaid) {
		this.premiumPaid = premiumPaid;
	}
	public String getPurchaseOption() {
		return purchaseOption;
	}
	public void setPurchaseOption(String purchaseOption) {
		this.purchaseOption = purchaseOption;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getSecurityFullName() {
		return securityFullName;
	}
	public void setSecurityFullName(String securityFullName) {
		this.securityFullName = securityFullName;
	}
	public String getFacevalueincrores() {
		return facevalueincrores;
	}
	public void setFacevalueincrores(String facevalueincrores) {
		this.facevalueincrores = facevalueincrores;
	}
	public String getSignPathLeftName() {
		return signPathLeftName;
	}
	public void setSignPathLeftName(String signPathLeftName) {
		this.signPathLeftName = signPathLeftName;
	}
	public String getSingPathRightName() {
		return singPathRightName;
	}
	public void setSingPathRightName(String singPathRightName) {
		this.singPathRightName = singPathRightName;
	}
	public HashMap getDocumentDetails() {
		return documentDetails;
	}
	public void setDocumentDetails(HashMap documentDetails) {
		this.documentDetails = documentDetails;
	}
	public String getModeOfPaymentRemarks() {
		return modeOfPaymentRemarks;
	}
	public void setModeOfPaymentRemarks(String modeOfPaymentRemarks) {
		this.modeOfPaymentRemarks = modeOfPaymentRemarks;
	}
	public String getPaymentThroughRemarks() {
		return paymentThroughRemarks;
	}
	public void setPaymentThroughRemarks(String paymentThroughRemarks) {
		this.paymentThroughRemarks = paymentThroughRemarks;
	}
	public String getFromPeriod() {
		return fromPeriod;
	}
	public void setFromPeriod(String fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	public String getToPeriod() {
		return toPeriod;
	}
	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}
	
}
