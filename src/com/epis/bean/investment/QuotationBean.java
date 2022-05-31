package com.epis.bean.investment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
public class QuotationBean extends RequestBean implements Serializable{
	private String quotationCd="";
	private String letterNo="";
	private String trustName="";
	private String securityCategory="";
	private String securityCd="";
	private String arranger="";
	private String arrangerCd="";
	private String securityName="";
	private String disIncNo="";
	private String rating="";
	private String faceValue="";
	private String numberOfUnits="";
	private String investmentFaceValue="";
	private String premiumPaid="";
	private String interestRate="";
	private String interestDate="";
	private String interestDate1="";
	private String interestMonth="";
	private String interestMonth1="";
	private String ytm="";
	private String directIncentive="";
	private String brokerIncentive="";
	private String maturityDate="";
	private String callOption="";
	private String redemptionDate="";
	private String investmentType="";
	private String investmentMode="";
	private String guarenteeType="";
	private String purchasePrice="";
	private String brokerName="";
	private String brokerAddress="";
	private String remarks="";
	private String ytmVerified="";
	private String status;
	private String opendate;
	private String ytp;
	private String yield;
	private String  bgcolor;
	private List shortListArrangres;
	private String shortlisted;
	private String sNo;
	private double amt; 
	private String dematno;
	private String investmentdate;
	private String registrardetails;
	private String investtype;
	private String callopt;
	private String investmode;
	private String guarantee;
	private String redumptionDueDate;
	private String amountRceived;
	private String openingbal;
	private String baseamount;
	private String investinterest;
	private String bankLetterNo;
	private String purchaseOption;
	private String ytmmaturity;
	private String ytmcall;
	private String closingDate;
	private String tenure;
	private String minApp;
	private String eligpftrust;
	private String priceoffered;
	private String dealDate;
	private String settlementDate;
	private String marketType;
	private String settlementAmount;
	private String accuredInterest;
	private String annualInterest;
	private String proposalRefNo;
	private String interestdispmonth;
	private String interestdispmonth1;
	private String totalPurchasePrice;
	private String totalFaceValue;
	private String principleAmt;
	private String investdate;
	private String amountInv;
	private String mode;
	private String approvalStatus;
	private String flags;
	private String qdata;
	private Map approvals;
	private String userId;
	private String appDate;
	private String updatedFlag;
	private String appCount;
	private String securityproposalNo;
	private String interestrvincb;
	private String maturityincb;
	private String interestrvincbdate;
	private String maturityincbdate;
	private String groupName;
	private String securityProposal;
	private String totalInterest;
	private String totalMaturity;
	private String closingBal;
	private String incentivepayable;
	
	private static final long serialVersionUID = 1L;
	
	public void setInterestDate1(String interestDate1)
	{
		this.interestDate1=interestDate1;
	}
	public String getInterestDate1()
	{
		return interestDate1;
	}

	public QuotationBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuotationBean(HttpServletRequest request){
		super(request);
		}
		

	public String getArranger() {
		return arranger;
	}

	public void setArranger(String arranger) {
		this.arranger = arranger;
	}

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public void setBrokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
	}

	public String getBrokerIncentive() {
		return brokerIncentive;
	}

	public void setBrokerIncentive(String brokerIncentive) {
		this.brokerIncentive = brokerIncentive;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getCallOption() {
		return callOption;
	}

	public void setCallOption(String callOption) {
		this.callOption = callOption;
	}

	public String getDirectIncentive() {
		return directIncentive;
	}

	public void setDirectIncentive(String directIncentive) {
		this.directIncentive = directIncentive;
	}

	public String getDisIncNo() {
		return disIncNo;
	}

	public void setDisIncNo(String disIncNo) {
		this.disIncNo = disIncNo;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getGuarenteeType() {
		return guarenteeType;
	}

	public void setGuarenteeType(String guarenteeType) {
		this.guarenteeType = guarenteeType;
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

	public String getInvestmentFaceValue() {
		return investmentFaceValue;
	}

	public void setInvestmentFaceValue(String investmentFaceValue) {
		this.investmentFaceValue = investmentFaceValue;
	}

	public String getInvestmentMode() {
		return investmentMode;
	}

	public void setInvestmentMode(String investmentMode) {
		this.investmentMode = investmentMode;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
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

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(String redemptionDate) {
		this.redemptionDate = redemptionDate;
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

	public String getYtm() {
		return ytm;
	}

	public void setYtm(String ytm) {
		this.ytm = ytm;
	}

	public String getQuotationCd() {
		return quotationCd;
	}

	public void setQuotationCd(String quotationCd) {
		this.quotationCd = quotationCd;
	}
	public String toString(){
		return (new StringBuffer("").append("QuotationCd: ").append(this.quotationCd).append("\n")
				.append("Arranger: ").append(this.arranger).append("\n")
				.append("BrokerAddress: ").append(this.brokerAddress).append("\n")
				.append("BrokerIncentive: ").append(this.brokerIncentive).append("\n")
				.append("BrokerName: ").append(this.brokerName).append("\n")
				.append("CallOption: ").append(this.callOption).append("\n")
				.append("DirectIncentive: ").append(this.directIncentive).append("\n")
				.append("DisIncNo: ").append(this.disIncNo).append("\n")
				.append("FaceValue: ").append(this.faceValue).append("\n")
				.append("GuarenteeType: ").append(this.guarenteeType).append("\n")
				.append("InterestDate: ").append(this.interestDate).append("\n")
				.append("InterestRate: ").append(this.interestRate).append("\n")
				.append("InvestmentFaceValue: ").append(this.investmentFaceValue).append("\n")
				.append("InvestmentMode: ").append(this.investmentMode).append("\n")
				.append("InvestmentType: ").append(this.investmentType).append("\n")
				.append("LetterNo: ").append(this.letterNo).append("\n")
				.append("MaturityDate: ").append(this.maturityDate).append("\n")
				.append("NumberOfUnits: ").append(this.numberOfUnits).append("\n")
				.append("PremiumPaid: ").append(this.premiumPaid).append("\n")
				.append("PurchasePrice: ").append(this.purchasePrice).append("\n")
				.append("Rating: ").append(this.rating).append("\n")
				.append("RedemptionDate: ").append(this.redemptionDate).append("\n")
				.append("Remarks: ").append(this.remarks).append("\n")
				.append("SecurityCategory: ").append(this.securityCategory).append("\n")
				.append("SecurityName: ").append(this.securityName).append("\n")
				.append("TrustName: ").append(this.trustName).append("\n")
				.append("Ytm: ").append(this.ytm).append("\n")).toString();
	}

	public String getYtmVerified() {
		return ytmVerified;
	}

	public void setYtmVerified(String ytmVerified) {
		this.ytmVerified = ytmVerified;
	}

	
	
	public String getSNo() {
		return sNo;
	}
	public void setSNo(String no) {
		sNo = no;
	}
	public String getOpendate() {
		return opendate;
	}

	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArrangerCd() {
		return arrangerCd;
	}

	public void setArrangerCd(String arrangerCd) {
		this.arrangerCd = arrangerCd;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getYtp() {
		return ytp;
	}

	public void setYtp(String ytp) {
		this.ytp = ytp;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getShortlisted() {
		return shortlisted;
	}

	public void setShortlisted(String shortlisted) {
		this.shortlisted = shortlisted;
	}

	public List getShortListArrangres() {
		return shortListArrangres;
	}

	public void setShortListArrangres(List shortListArrangres) {
		this.shortListArrangres = shortListArrangres;
	}
	public String getDematno() {
		return dematno;
	}
	public void setDematno(String dematno) {
		this.dematno = dematno;
	}
	public String getCallopt() {
		return callopt;
	}
	public void setCallopt(String callopt) {
		this.callopt = callopt;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getInvestmentdate() {
		return investmentdate;
	}
	public void setInvestmentdate(String investmentdate) {
		this.investmentdate = investmentdate;
	}
	public String getInvestmode() {
		return investmode;
	}
	public void setInvestmode(String investmode) {
		this.investmode = investmode;
	}
	public String getInvesttype() {
		return investtype;
	}
	public void setInvesttype(String investtype) {
		this.investtype = investtype;
	}
	public String getRegistrardetails() {
		return registrardetails;
	}
	public void setRegistrardetails(String registrardetails) {
		this.registrardetails = registrardetails;
	}
	public String getRedumptionDueDate() {
		return redumptionDueDate;
	}
	public void setRedumptionDueDate(String redumptionDueDate) {
		this.redumptionDueDate = redumptionDueDate;
	}
	public String getAmountRceived() {
		return amountRceived;
	}
	public void setAmountRceived(String amountRceived) {
		this.amountRceived = amountRceived;
	}
	public String getBaseamount() {
		return baseamount;
	}
	public void setBaseamount(String baseamount) {
		this.baseamount = baseamount;
	}
	public String getInvestinterest() {
		return investinterest;
	}
	public void setInvestinterest(String investinterest) {
		this.investinterest = investinterest;
	}
	public String getOpeningbal() {
		return openingbal;
	}
	public void setOpeningbal(String openingbal) {
		this.openingbal = openingbal;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getSecurityCd() {
		return securityCd;
	}
	public void setSecurityCd(String securityCd) {
		this.securityCd = securityCd;
	}
	public String getBankLetterNo() {
		return bankLetterNo;
	}
	public void setBankLetterNo(String bankLetterNo) {
		this.bankLetterNo = bankLetterNo;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	public String getEligpftrust() {
		return eligpftrust;
	}
	public void setEligpftrust(String eligpftrust) {
		this.eligpftrust = eligpftrust;
	}
	public String getMinApp() {
		return minApp;
	}
	public void setMinApp(String minApp) {
		this.minApp = minApp;
	}
	public String getPurchaseOption() {
		return purchaseOption;
	}
	public void setPurchaseOption(String purchaseOption) {
		this.purchaseOption = purchaseOption;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getYtmcall() {
		return ytmcall;
	}
	public void setYtmcall(String ytmcall) {
		this.ytmcall = ytmcall;
	}
	public String getYtmmaturity() {
		return ytmmaturity;
	}
	public void setYtmmaturity(String ytmmaturity) {
		this.ytmmaturity = ytmmaturity;
	}
	public String getPriceoffered() {
		return priceoffered;
	}
	public void setPriceoffered(String priceoffered) {
		this.priceoffered = priceoffered;
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
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getAccuredInterest() {
		return accuredInterest;
	}
	public void setAccuredInterest(String accuredInterest) {
		this.accuredInterest = accuredInterest;
	}
	public String getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getAnnualInterest() {
		return annualInterest;
	}
	public void setAnnualInterest(String annualInterest) {
		this.annualInterest = annualInterest;
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
	public String getProposalRefNo() {
		return proposalRefNo;
	}
	public void setProposalRefNo(String proposalRefNo) {
		this.proposalRefNo = proposalRefNo;
	}
	public String getInterestdispmonth() {
		return interestdispmonth;
	}
	public void setInterestdispmonth(String interestdispmonth) {
		this.interestdispmonth = interestdispmonth;
	}
	public String getInterestdispmonth1() {
		return interestdispmonth1;
	}
	public void setInterestdispmonth1(String interestdispmonth1) {
		this.interestdispmonth1 = interestdispmonth1;
	}
	public String getTotalFaceValue() {
		return totalFaceValue;
	}
	public void setTotalFaceValue(String totalFaceValue) {
		this.totalFaceValue = totalFaceValue;
	}
	public String getTotalPurchasePrice() {
		return totalPurchasePrice;
	}
	public void setTotalPurchasePrice(String totalPurchasePrice) {
		this.totalPurchasePrice = totalPurchasePrice;
	}
	public String getPrincipleAmt() {
		return principleAmt;
	}
	public void setPrincipleAmt(String principleAmt) {
		this.principleAmt = principleAmt;
	}
	
	
	public String getInvestdate() {
		return investdate;
	}
	public void setInvestdate(String investdate) {
		this.investdate = investdate;
	}
	public String getAmountInv() {
		return amountInv;
	}
	public void setAmountInv(String amountInv) {
		this.amountInv = amountInv;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getQdata() {
		return qdata;
	}
	public void setQdata(String qdata) {
		this.qdata = qdata;
	}
	public Map getApprovals() {
		return approvals;
	}
	public void setApprovals(Map approvals) {
		this.approvals = approvals;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getUpdatedFlag() {
		return updatedFlag;
	}
	public void setUpdatedFlag(String updatedFlag) {
		this.updatedFlag = updatedFlag;
	}
	public String getAppCount() {
		return appCount;
	}
	public void setAppCount(String appCount) {
		this.appCount = appCount;
	}
	public String getSecurityproposalNo() {
		return securityproposalNo;
	}
	public void setSecurityproposalNo(String securityproposalNo) {
		this.securityproposalNo = securityproposalNo;
	}
	public String getInterestrvincb() {
		return interestrvincb;
	}
	public void setInterestrvincb(String interestrvincb) {
		this.interestrvincb = interestrvincb;
	}
	public String getMaturityincb() {
		return maturityincb;
	}
	public void setMaturityincb(String maturityincb) {
		this.maturityincb = maturityincb;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSecurityProposal() {
		return securityProposal;
	}
	public void setSecurityProposal(String securityProposal) {
		this.securityProposal = securityProposal;
	}
	public String getInterestrvincbdate() {
		return interestrvincbdate;
	}
	public void setInterestrvincbdate(String interestrvincbdate) {
		this.interestrvincbdate = interestrvincbdate;
	}
	public String getMaturityincbdate() {
		return maturityincbdate;
	}
	public void setMaturityincbdate(String maturityincbdate) {
		this.maturityincbdate = maturityincbdate;
	}
	public String getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(String totalInterest) {
		this.totalInterest = totalInterest;
	}
	public String getTotalMaturity() {
		return totalMaturity;
	}
	public void setTotalMaturity(String totalMaturity) {
		this.totalMaturity = totalMaturity;
	}
	public String getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(String closingBal) {
		this.closingBal = closingBal;
	}
	public String getIncentivepayable() {
		return incentivepayable;
	}
	public void setIncentivepayable(String incentivepayable) {
		this.incentivepayable = incentivepayable;
	}
	
	

}
