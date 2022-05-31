package com.epis.bean.cashbook;

import java.io.Serializable;

public class HdfcBean implements Serializable{

	private String transactionType;
	private String benecode;
	private String beneAcno;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String instrumentAmt;
	private String insrtuctionRefNo;
	private String custRefNo;
	private String paymentDetails;
	private String chqNo;
	private String chqTrDate;
	private String micrNO;
	private String ifscCode;
	private String bankName;
	private String branchName;
	private String bmailID;
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getBenecode() {
		return benecode;
	}
	public void setBenecode(String benecode) {
		this.benecode = benecode;
	}
	public String getBeneAcno() {
		return beneAcno;
	}
	public void setBeneAcno(String beneAcno) {
		this.beneAcno = beneAcno;
	}
	public String getInstrumentAmt() {
		return instrumentAmt;
	}
	public void setInstrumentAmt(String instrumentAmt) {
		this.instrumentAmt = instrumentAmt;
	}
	public String getInsrtuctionRefNo() {
		return insrtuctionRefNo;
	}
	public void setInsrtuctionRefNo(String insrtuctionRefNo) {
		this.insrtuctionRefNo = insrtuctionRefNo;
	}
	public String getCustRefNo() {
		return custRefNo;
	}
	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}
	public String getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getChqNo() {
		return chqNo;
	}
	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}
	public String getChqTrDate() {
		return chqTrDate;
	}
	public void setChqTrDate(String chqTrDate) {
		this.chqTrDate = chqTrDate;
	}
	public String getMicrNO() {
		return micrNO;
	}
	public void setMicrNO(String micrNO) {
		this.micrNO = micrNO;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBmailID() {
		return bmailID;
	}
	public void setBmailID(String bmailID) {
		this.bmailID = bmailID;
	}
}
