package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class LetterToBankBean extends RequestBean {
	
	public LetterToBankBean()
	{
		
	}
	public LetterToBankBean(HttpServletRequest req)
	{
			super(req);
	}
	private String bankCd;
	private String letterNo;
	private String refNo;
	private String bankDate;
	private String beneficiaryName;
	private String creditAccountNo;
	private String accountNo;
	private String centerLocation;
	private String bankName;
	private String branchName;
	private String accountType;
	private String remitanceAmt;
	private String remarks;
	private String hasQuotations;
	private String trustType;
	private String securityCategory;
	private String marketType;
	private String securityName;
	private String bankAddress;
	private String amountInv;
	private String accountTypedef;
	private String accountTypaai;
	private String marketTypedef;
	private String signPathLeft;
	private String singPathRight;
	private String ifscCode;
	private String leftSign;
	private String rightSign;
	private String sglAccountNo;
	private String sellerRefNo;
	private String dealDate;
	private String settlementDate;
	private String faceValue;
	private String offeredPrice;
	private String principalAmount;
	private String noofdays;
	private String paymentValue;
	private String accuredInterestAmount;
	private String SettlementAmount;
	private String mode;
	private String aaiaccountNo;
	private String refnoSecurityName;
	private String remainingAmt;
	private String securityFullName;
	private String refnoSecurityDef;
	
	public String getRefnoSecurityDef() {
		return refnoSecurityDef;
	}
	public void setRefnoSecurityDef(String refnoSecurityDef) {
		this.refnoSecurityDef = refnoSecurityDef;
	}
	public String getSecurityFullName() {
		return securityFullName;
	}
	public void setSecurityFullName(String securityFullName) {
		this.securityFullName = securityFullName;
	}
	public String getRemainingAmt() {
		return remainingAmt;
	}
	public void setRemainingAmt(String remainingAmt) {
		this.remainingAmt = remainingAmt;
	}
	public String getAaiaccountNo() {
		return aaiaccountNo;
	}
	public void setAaiaccountNo(String aaiaccountNo) {
		this.aaiaccountNo = aaiaccountNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getAccuredInterestAmount() {
		return accuredInterestAmount;
	}
	public void setAccuredInterestAmount(String accuredInterestAmount) {
		this.accuredInterestAmount = accuredInterestAmount;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	public String getNoofdays() {
		return noofdays;
	}
	public void setNoofdays(String noofdays) {
		this.noofdays = noofdays;
	}
	public String getOfferedPrice() {
		return offeredPrice;
	}
	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	public String getPaymentValue() {
		return paymentValue;
	}
	public void setPaymentValue(String paymentValue) {
		this.paymentValue = paymentValue;
	}
	public String getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String getSellerRefNo() {
		return sellerRefNo;
	}
	public void setSellerRefNo(String sellerRefNo) {
		this.sellerRefNo = sellerRefNo;
	}
	public String getSettlementAmount() {
		return SettlementAmount;
	}
	public void setSettlementAmount(String settlementAmount) {
		SettlementAmount = settlementAmount;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getSglAccountNo() {
		return sglAccountNo;
	}
	public void setSglAccountNo(String sglAccountNo) {
		this.sglAccountNo = sglAccountNo;
	}
	public String getLeftSign() {
		return leftSign;
	}
	public void setLeftSign(String leftSign) {
		this.leftSign = leftSign;
	}
	public String getRightSign() {
		return rightSign;
	}
	public void setRightSign(String rightSign) {
		this.rightSign = rightSign;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
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
	public String getMarketTypedef() {
		return marketTypedef;
	}
	public void setMarketTypedef(String marketTypedef) {
		this.marketTypedef = marketTypedef;
	}
	public String getAccountTypedef() {
		return accountTypedef;
	}
	public void setAccountTypedef(String accountTypedef) {
		this.accountTypedef = accountTypedef;
	}
	public String getAmountInv() {
		return amountInv;
	}
	public void setAmountInv(String amountInv) {
		this.amountInv = amountInv;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getSecurityCategory() {
		return securityCategory;
	}
	public void setSecurityCategory(String securityCategory) {
		this.securityCategory = securityCategory;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getHasQuotations() {
		return hasQuotations;
	}
	public void setHasQuotations(String hasQuotations) {
		this.hasQuotations = hasQuotations;
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
	public String getBankCd() {
		return bankCd;
	}
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	public String getBankDate() {
		return bankDate;
	}
	public void setBankDate(String bankDate) {
		this.bankDate = bankDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCenterLocation() {
		return centerLocation;
	}
	public void setCenterLocation(String centerLocation) {
		this.centerLocation = centerLocation;
	}
	public String getCreditAccountNo() {
		return creditAccountNo;
	}
	public void setCreditAccountNo(String creditAccountNo) {
		this.creditAccountNo = creditAccountNo;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemitanceAmt() {
		return remitanceAmt;
	}
	public void setRemitanceAmt(String remitanceAmt) {
		this.remitanceAmt = remitanceAmt;
	}
	public String getAccountTypaai() {
		return accountTypaai;
	}
	public void setAccountTypaai(String accountTypaai) {
		this.accountTypaai = accountTypaai;
	}
	public String getRefnoSecurityName() {
		return refnoSecurityName;
	}
	public void setRefnoSecurityName(String refnoSecurityName) {
		this.refnoSecurityName = refnoSecurityName;
	}
	
}
