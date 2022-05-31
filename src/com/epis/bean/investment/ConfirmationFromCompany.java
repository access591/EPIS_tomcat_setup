package com.epis.bean.investment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class ConfirmationFromCompany extends RequestBean {
	
	
	private String confirm;
	private String remarks;
	private String dealDate;
	private String settlementDate;
	private String maturityDate;
	private String cuponRate;
	private String premiumPaid;
	private String faceValue;
	private String purchaseOption;
	private String offeredPrice;
	private String ytmValue;
	private String callOption;
	private String modeOfInterest;
	private String interestDate;
	private String interestDate1;
	private String purchasePrice;
	private String refNo;
	private String securityCategory;
	private String marketType;
	private String marketTypedef;
	private String amountInv;
	private String trustType;
	private String noofBonds;
	private String letterNo;
	private String companyCd;
	private String hasQuotations;
	private String callDate;
	private String confirmationDef;
	private String purchaseoptionDef;
	private String calloptionDef;
	private String modeofinterestDef;
	private String flags;
	private String saveflag;
	
	private Map approvals;
	private String userId;
	private String appDate;
	private String proposaldate;
	private String subject;
	private String date;
	private String status;
	private String updatedFlag;
	private String mode;
	private String interestMonth;
	private String interestMonth1;
	private String  dispinterestdate;
	private String dispinterestdate1;
	private String securityName;
	private String appCount;
	private String securityFullName;
	private String confirmationRemarks;
	private String confirmationDate;
	public String getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(String confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	public String getConfirmationRemarks() {
		return confirmationRemarks;
	}
	public void setConfirmationRemarks(String confirmationRemarks) {
		this.confirmationRemarks = confirmationRemarks;
	}
	public String getSecurityFullName() {
		return securityFullName;
	}
	public void setSecurityFullName(String securityFullName) {
		this.securityFullName = securityFullName;
	}
	public String getAppCount() {
		return appCount;
	}
	public void setAppCount(String appCount) {
		this.appCount = appCount;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getDispinterestdate() {
		return dispinterestdate;
	}
	public void setDispinterestdate(String dispinterestdate) {
		this.dispinterestdate = dispinterestdate;
	}
	public String getDispinterestdate1() {
		return dispinterestdate1;
	}
	public void setDispinterestdate1(String dispinterestdate1) {
		this.dispinterestdate1 = dispinterestdate1;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
	public String getCallDate() {
		return callDate;
	}
	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}
	public String getMarketTypedef() {
		return marketTypedef;
	}
	public void setMarketTypedef(String marketTypedef) {
		this.marketTypedef = marketTypedef;
	}
	public String getHasQuotations() {
		return hasQuotations;
	}
	public void setHasQuotations(String hasQuotations) {
		this.hasQuotations = hasQuotations;
	}
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getAmountInv() {
		return amountInv;
	}
	public void setAmountInv(String amountInv) {
		this.amountInv = amountInv;
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
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
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
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
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
	public ConfirmationFromCompany()
	{
		
	}
	public ConfirmationFromCompany(HttpServletRequest req)
	{
		super(req);
	}
	public String getCalloptionDef() {
		return calloptionDef;
	}
	public void setCalloptionDef(String calloptionDef) {
		this.calloptionDef = calloptionDef;
	}
	public String getConfirmationDef() {
		return confirmationDef;
	}
	public void setConfirmationDef(String confirmationDef) {
		this.confirmationDef = confirmationDef;
	}
	public String getModeofinterestDef() {
		return modeofinterestDef;
	}
	public void setModeofinterestDef(String modeofinterestDef) {
		this.modeofinterestDef = modeofinterestDef;
	}
	public String getPurchaseoptionDef() {
		return purchaseoptionDef;
	}
	public void setPurchaseoptionDef(String purchaseoptionDef) {
		this.purchaseoptionDef = purchaseoptionDef;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public Map getApprovals() {
		return approvals;
	}
	public void setApprovals(Map approvals) {
		this.approvals = approvals;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProposaldate() {
		return proposaldate;
	}
	public void setProposaldate(String proposaldate) {
		this.proposaldate = proposaldate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSaveflag() {
		return saveflag;
	}
	public void setSaveflag(String saveflag) {
		this.saveflag = saveflag;
	}
}
