package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class DailyStatement extends RequestBean {

	public DailyStatement(HttpServletRequest request) {
		super(request);
	}

	public DailyStatement() {

	}

	private String maturityOfBonds="0";
	private String interstOnBonds="0";
	private String redemptionOfBonds="0";
	private String accretion="0";
	private String otherReceipts="0";
	private String finalPayment="0";
	private String partFinal="0";
	private String loansAndAdvance="0";
	private String investment="0";
	private String otherPayment="0";
	private String bankName;
	private String accountNo;
	private String openingBalance="0";
	private String closingBalance="0";
	private String totalPayment="0";
	private String totalReceipt="0";

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccretion() {
		return accretion;
	}

	public void setAccretion(String accretion) {
		this.accretion = accretion;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getFinalPayment() {
		return finalPayment;
	}

	public void setFinalPayment(String finalPayment) {
		this.finalPayment = finalPayment;
	}

	public String getInterstOnBonds() {
		return interstOnBonds;
	}

	public void setInterstOnBonds(String interstOnBonds) {
		this.interstOnBonds = interstOnBonds;
	}

	public String getInvestment() {
		return investment;
	}

	public void setInvestment(String investment) {
		this.investment = investment;
	}

	public String getLoansAndAdvance() {
		return loansAndAdvance;
	}

	public void setLoansAndAdvance(String loansAndAdvance) {
		this.loansAndAdvance = loansAndAdvance;
	}

	public String getMaturityOfBonds() {
		return maturityOfBonds;
	}

	public void setMaturityOfBonds(String maturityOfBonds) {
		this.maturityOfBonds = maturityOfBonds;
	}

	public String getOtherPayment() {
		return otherPayment;
	}

	public void setOtherPayment(String otherPayment) {
		this.otherPayment = otherPayment;
	}

	public String getOtherReceipts() {
		return otherReceipts;
	}

	public void setOtherReceipts(String otherReceipts) {
		this.otherReceipts = otherReceipts;
	}

	public String getPartFinal() {
		return partFinal;
	}

	public void setPartFinal(String partFinal) {
		this.partFinal = partFinal;
	}

	public String getRedemptionOfBonds() {
		return redemptionOfBonds;
	}

	public void setRedemptionOfBonds(String redemptionOfBonds) {
		this.redemptionOfBonds = redemptionOfBonds;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getTotalReceipt() {
		return totalReceipt;
	}

	public void setTotalReceipt(String totalReceipt) {
		this.totalReceipt = totalReceipt;
	}

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

}
