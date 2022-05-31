package com.epis.bean.cashbookDummy;

import java.io.Serializable;

public class AccountingCodeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String accountHead;
	private String particular;
	private String type;
	private String date;
	private String amount;
	private String amountType;
	private String amountI;
	private String amountTypeI;
	private String amountN;
	private String amountTypeN;
	private String trustType;
	private String enteredBy; 
	private String enteredDate;
	
	public String getAccountHead() {
		return accountHead;
	}
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}
	public String getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	public String getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(String enteredDate) {
		this.enteredDate = enteredDate;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getAmountI() {
		return amountI;
	}
	public void setAmountI(String amountI) {
		this.amountI = amountI;
	}
	public String getAmountN() {
		return amountN;
	}
	public void setAmountN(String amountN) {
		this.amountN = amountN;
	}
	public String getAmountTypeI() {
		return amountTypeI;
	}
	public void setAmountTypeI(String amountTypeI) {
		this.amountTypeI = amountTypeI;
	}
	public String getAmountTypeN() {
		return amountTypeN;
	}
	public void setAmountTypeN(String amountTypeN) {
		this.amountTypeN = amountTypeN;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
}
