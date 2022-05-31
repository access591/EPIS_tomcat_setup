package com.epis.bean.cashbook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.epis.bean.RequestBean;

public class BRStatementInfo extends RequestBean {

	public BRStatementInfo(HttpServletRequest request){
		super(request);
		}
	
	public BRStatementInfo() {

	}
	
	private String accountNo;
	private String bankName;
	private String columnValue;
	private String description;
	private String columnNo;
	private List colMapping;
	private FormFile file;
	private String  fileName;
	private String  keyno;
	private String transactionDt;
	private String valueDt;
	private String creditAmt;
	private String debitAmt;
	private  String chequeNo;
	private  String debitOrCredit;
	private String amount;
	private String dataLine;
	private String voucherDate;
	private String voucherNo;
	


	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getDataLine() {
		return dataLine;
	}

	public void setDataLine(String dataLine) {
		this.dataLine = dataLine;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public List getColMapping() {
		return colMapping;
	}

	public void setColMapping(List colMapping) {
		this.colMapping = colMapping;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	
	public String getTransactionDt() {
		return transactionDt;
	}

	public void setTransactionDt(String transactionDt) {
		this.transactionDt = transactionDt;
	}
	

	public String getValueDt() {
		return valueDt;
	}

	public void setValueDt(String valueDt) {
		this.valueDt = valueDt;
	}
	
	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public String getDebitOrCredit() {
		return debitOrCredit;
	}

	public void setDebitOrCredit(String debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}	
	
	
	public String getDebitAmt() {
		return debitAmt;
	}

	public void setDebitAmt(String debitAmt) {
		this.debitAmt = debitAmt;
	}
	
	public String getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(String creditAmt) {
		this.creditAmt = creditAmt;
	}
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
