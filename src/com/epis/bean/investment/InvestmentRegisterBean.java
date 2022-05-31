package com.epis.bean.investment;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class InvestmentRegisterBean extends RequestBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String registerCd;
	private String letterNo;
	private String securityName;
	private String refNo;
	private String trustType;
	private String securityCategory;
	private String amountInv;
	private String noofBonds;
	private String confirm;
	private String confirmationDef;
	private String dealDate;
	private String settlementDate;
	private String maturityDate;
	private String cuponRate;
	private String premiumPaid;
	private String purchaseOption;
	private String purchaseOptionDef;
	private String offeredPrice;
	private String ytmValue;
	private String callOption;
	private String callOptinDef;
	private String callDate;
	private String modeOfInterest;
	private String modeOfInterestDef;
	private String interestDate;
	private String interestDate1;
	private String purchasePrice;
	private String acceptence;
	private String faceValue;
	private String investdate;
	private String settlementAmt;
	private String mode;
	private String securityFullName;
	private String marketType;
	private String exist;
	private String interestMonth;
	private String interestMonth1;
	private String accuredInterestAmount;
	private String arrangerName;
	private String incentivePayable;
	private String incentivetext;
	private String ISIN;
	
	public String getISIN() {
		return ISIN;
	}
	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}
	public String getArrangerName() {
		return arrangerName;
	}
	public void setArrangerName(String arrangerName) {
		this.arrangerName = arrangerName;
	}
	public String getIncentivePayable() {
		return incentivePayable;
	}
	public void setIncentivePayable(String incentivePayable) {
		this.incentivePayable = incentivePayable;
	}
	public String getIncentivetext() {
		return incentivetext;
	}
	public void setIncentivetext(String incentivetext) {
		this.incentivetext = incentivetext;
	}
	public String getAccuredInterestAmount() {
		return accuredInterestAmount;
	}
	public void setAccuredInterestAmount(String accuredInterestAmount) {
		this.accuredInterestAmount = accuredInterestAmount;
	}
	public String getInterestMonth() {
		return interestMonth;
	}
	public void setInterestMonth(String interestMonth) {
		this.interestMonth = interestMonth;
	}
	public String getInterestMonth1() {
		return interestMonth1;
	}
	public void setInterestMonth1(String interestMonth1) {
		this.interestMonth1 = interestMonth1;
	}
	public String getExist() {
		return exist;
	}
	public void setExist(String exist) {
		this.exist = exist;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getSecurityFullName() {
		return securityFullName;
	}
	public void setSecurityFullName(String securityFullName) {
		this.securityFullName = securityFullName;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getInvestdate() {
		return investdate;
	}
	public void setInvestdate(String investdate) {
		this.investdate = investdate;
	}
	public InvestmentRegisterBean()
	{
		
	}
	public InvestmentRegisterBean(HttpServletRequest req)
	{
		super(req);
	}
	public String getAcceptence() {
		return acceptence;
	}
	public void setAcceptence(String acceptence) {
		this.acceptence = acceptence;
	}
	public String getAmountInv() {
		return amountInv;
	}
	public void setAmountInv(String amountInv) {
		this.amountInv = amountInv;
	}
	public String getCallDate() {
		return callDate;
	}
	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}
	public String getCallOption() {
		return callOption;
	}
	public void setCallOption(String callOption) {
		this.callOption = callOption;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getCuponRate() {
		return cuponRate;
	}
	public void setCuponRate(String cuponRate) {
		this.cuponRate = cuponRate;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getInterestDate() {
		return interestDate;
	}
	public void setInterestDate(String interestDate) {
		this.interestDate = interestDate;
	}
	public String getInterestDate1() {
		return interestDate1;
	}
	public void setInterestDate1(String interestDate1) {
		this.interestDate1 = interestDate1;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getModeOfInterest() {
		return modeOfInterest;
	}
	public void setModeOfInterest(String modeOfInterest) {
		this.modeOfInterest = modeOfInterest;
	}
	public String getNoofBonds() {
		return noofBonds;
	}
	public void setNoofBonds(String noofBonds) {
		this.noofBonds = noofBonds;
	}
	public String getOfferedPrice() {
		return offeredPrice;
	}
	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
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
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRegisterCd() {
		return registerCd;
	}
	public void setRegisterCd(String registerCd) {
		this.registerCd = registerCd;
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
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getYtmValue() {
		return ytmValue;
	}
	public void setYtmValue(String ytmValue) {
		this.ytmValue = ytmValue;
	}
	public String getCallOptinDef() {
		return callOptinDef;
	}
	public void setCallOptinDef(String callOptinDef) {
		this.callOptinDef = callOptinDef;
	}
	public String getConfirmationDef() {
		return confirmationDef;
	}
	public void setConfirmationDef(String confirmationDef) {
		this.confirmationDef = confirmationDef;
	}
	public String getModeOfInterestDef() {
		return modeOfInterestDef;
	}
	public void setModeOfInterestDef(String modeOfInterestDef) {
		this.modeOfInterestDef = modeOfInterestDef;
	}
	public String getPurchaseOptionDef() {
		return purchaseOptionDef;
	}
	public void setPurchaseOptionDef(String purchaseOptionDef) {
		this.purchaseOptionDef = purchaseOptionDef;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	public String getSettlementAmt() {
		return settlementAmt;
	}
	public void setSettlementAmt(String settlementAmt) {
		this.settlementAmt = settlementAmt;
	}

}
