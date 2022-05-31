package com.epis.bean.investment;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class GenerateBankLetterBean extends RequestBean {
	private String letterNo;
	private String bankLetterNo;
	private String dealDate;
	private String settlementDate;
	private String bankName;
	private String accountNo;
	private String quotationCd;
	private String purchasePrice;
	private String security;
	private String trustName;
	private String securityName;
	private String arrangerName;
	private String address;
	private String rate;
	private String noofDays;
	private String accuredAmount;
	private String investmentFaceValue;
	private String principalAmount;
	private String settlementAmount;
	private String settlementAmountWords;
	private String referenceNo;
	private String marketType;
	private String benificiaryBankName;
	private String benificiaryBranch;
	private String accountType;
	private String ifscCode;
	private String leftSign;
	private String rightSign;
	private String creditAccountNo;
	private String beneficiaryName;
	private String remarks;
	
	
	
	
	
	
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBenificiaryBankName() {
		return benificiaryBankName;
	}
	public void setBenificiaryBankName(String benificiaryBankName) {
		this.benificiaryBankName = benificiaryBankName;
	}
	public String getBenificiaryBranch() {
		return benificiaryBranch;
	}
	public void setBenificiaryBranch(String benificiaryBranch) {
		this.benificiaryBranch = benificiaryBranch;
	}
	public String getCreditAccountNo() {
		return creditAccountNo;
	}
	public void setCreditAccountNo(String creditAccountNo) {
		this.creditAccountNo = creditAccountNo;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getLeftSign() {
		return leftSign;
	}
	public void setLeftSign(String leftSign) {
		this.leftSign = leftSign;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRightSign() {
		return rightSign;
	}
	public void setRightSign(String rightSign) {
		this.rightSign = rightSign;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getSettlementAmountWords() {
		return settlementAmountWords;
	}
	public void setSettlementAmountWords(String settlementAmountWords) {
		this.settlementAmountWords = settlementAmountWords;
	}
	public String getInvestmentFaceValue() {
		return investmentFaceValue;
	}
	public void setInvestmentFaceValue(String investmentFaceValue) {
		this.investmentFaceValue = investmentFaceValue;
	}
	public String getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getAccuredAmount() {
		return accuredAmount;
	}
	public void setAccuredAmount(String accuredAmount) {
		this.accuredAmount = accuredAmount;
	}
	public String getNoofDays() {
		return noofDays;
	}
	public void setNoofDays(String noofDays) {
		this.noofDays = noofDays;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArrangerName() {
		return arrangerName;
	}
	public void setArrangerName(String arrangerName) {
		this.arrangerName = arrangerName;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getTrustName() {
		return trustName;
	}
	public void setTrustName(String trustName) {
		this.trustName = trustName;
	}
	public String getQuotationCd() {
		return quotationCd;
	}
	public void setQuotationCd(String quotationCd) {
		this.quotationCd = quotationCd;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankLetterNo() {
		return bankLetterNo;
	}
	public void setBankLetterNo(String bankLetterNo) {
		this.bankLetterNo = bankLetterNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public GenerateBankLetterBean(HttpServletRequest request)
	{
		super(request);
	}
	public GenerateBankLetterBean()
	{
		
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

}
