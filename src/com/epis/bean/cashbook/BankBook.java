package com.epis.bean.cashbook;

import java.io.Serializable;
import java.util.List;

public class BankBook extends Info implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String voucherno;
	private String bankName;
	private String accountNo;
	private String accountHead;
	private String particular;
	private String partyName;
	private String description;
	private String voucherType;
	private double payments;
	private double receipts;
	private double openingBalAmt;
	private double openingBalAmtDebit;
	private List bankBookList;
	private String chequeNo;
	private String voucherDate;
	private String amountType;
	private String trustType;
	private String accountHeadType;
	
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getAccountHead() {
		return accountHead;
	}
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public double getPayments() {
		return payments;
	}
	public void setPayments(double payments) {
		this.payments = payments;
	}
	public double getReceipts() {
		return receipts;
	}
	public void setReceipts(double receipts) {
		this.receipts = receipts;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public List getBankBookList() {
		return bankBookList;
	}
	public void setBankBookList(List bankBookList) {
		this.bankBookList = bankBookList;
	}
	public double getOpeningBalAmt() {
		return openingBalAmt;
	}
	public void setOpeningBalAmt(double openingBalAmt) {
		this.openingBalAmt = openingBalAmt;
	}
	public String getVoucherno() {
		return voucherno;
	}
	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getAccountHeadType() {
		return accountHeadType;
	}
	public void setAccountHeadType(String accountHeadType) {
		this.accountHeadType = accountHeadType;
	}
	public double getOpeningBalAmtDebit() {
		return openingBalAmtDebit;
	}
	public void setOpeningBalAmtDebit(double openingBalAmtDebit) {
		this.openingBalAmtDebit = openingBalAmtDebit;
	}
	
}
