package com.epis.bean.cashbook;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentReceiptBean {
	private ArrayList receiptList;
	private ArrayList paymentList;
	private ArrayList BankOpeningList;
	private ArrayList BankClosingList;
	private HashMap bankDetails;
	public HashMap getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(HashMap bankDetails) {
		this.bankDetails = bankDetails;
	}
	public ArrayList getBankClosingList() {
		return BankClosingList;
	}
	public void setBankClosingList(ArrayList bankClosingList) {
		BankClosingList = bankClosingList;
	}
	public ArrayList getBankOpeningList() {
		return BankOpeningList;
	}
	public void setBankOpeningList(ArrayList bankOpeningList) {
		BankOpeningList = bankOpeningList;
	}
	public ArrayList getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(ArrayList paymentList) {
		this.paymentList = paymentList;
	}
	public ArrayList getReceiptList() {
		return receiptList;
	}
	public void setReceiptList(ArrayList receiptList) {
		this.receiptList = receiptList;
	}
	
	
	

}
