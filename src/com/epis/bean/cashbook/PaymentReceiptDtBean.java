package com.epis.bean.cashbook;

public class PaymentReceiptDtBean {
	private String finYear;
	private String receiptAmt;
	private String paymentAmt;
	private String bankOpeningAmt;
	private String bankName;
	private String accheadDesc;
	public String getAccheadDesc() {
		return accheadDesc;
	}
	public void setAccheadDesc(String accheadDesc) {
		this.accheadDesc = accheadDesc;
	}
	public String getBankOpeningAmt() {
		return bankOpeningAmt;
	}
	public void setBankOpeningAmt(String bankOpeningAmt) {
		this.bankOpeningAmt = bankOpeningAmt;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(String receiptAmt) {
		this.receiptAmt = receiptAmt;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
