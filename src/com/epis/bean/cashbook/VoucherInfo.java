package com.epis.bean.cashbook;

import java.io.Serializable;
import java.util.List;

public class VoucherInfo extends Info implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333224242299939607L;
	private String keyNo;
	private String bankName;
	private String accountHead;
	private String accountNo;
	private String finYear;
	private String trustType;
	private String voucherType;
	private String partyType;
	private String empPartyCode;
	private String partyDetails;
	private String partyName;
	private String voucherNo;
	private String preparedBy;
	private String checkedBy;
	private String approvedBy;
	private List voucherDetails;
	private String voucherDt;
	private String details;
	private String status;
	private String fromDate;
	private String toDate;
	private String preparedDt;
	private String bankCode;
	private String empCpfacno;
	private String empRegion;
	private String pfidFlag;
	private String employeeName;
	private String amount;
	private String designation;
	private String transactionType;// cheque/RTGS
	private String transactionId;
	private String module;
	private String unitName;
	private String keyno;
	private String otherTransactionType;
	private String debitAmount;	
	private String creditAmount;//PFW/CPF/FS
	private String bankbookCredit;
	private String bankbookDebit;
	private String bankCredit;
	private String bankDebit;
	private String bankClosingBal;
	private String checkingRecordCount;
	private String purpose;
	private String narration;
	
	
	
	
	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCheckingRecordCount() {
		return checkingRecordCount;
	}

	public void setCheckingRecordCount(String checkingRecordCount) {
		this.checkingRecordCount = checkingRecordCount;
	}

	public String getBankbookCredit() {
		return bankbookCredit;
	}

	public void setBankbookCredit(String bankbookCredit) {
		this.bankbookCredit = bankbookCredit;
	}

	public String getBankbookDebit() {
		return bankbookDebit;
	}

	public void setBankbookDebit(String bankbookDebit) {
		this.bankbookDebit = bankbookDebit;
	}

	public String getBankClosingBal() {
		return bankClosingBal;
	}

	public void setBankClosingBal(String bankClosingBal) {
		this.bankClosingBal = bankClosingBal;
	}

	public String getBankCredit() {
		return bankCredit;
	}

	public void setBankCredit(String bankCredit) {
		this.bankCredit = bankCredit;
	}

	public String getBankDebit() {
		return bankDebit;
	}

	public void setBankDebit(String bankDebit) {
		this.bankDebit = bankDebit;
	}

	public String getOtherTransactionType() {
		return otherTransactionType;
	}

	public void setOtherTransactionType(String otherTransactionType) {
		this.otherTransactionType = otherTransactionType;
	}
	
	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	
	
	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPfidFlag() {
		return pfidFlag;
	}

	public void setPfidFlag(String pfidFlag) {
		this.pfidFlag = pfidFlag;
	}

	public String getEmpCpfacno() {
		return empCpfacno;
	}

	public void setEmpCpfacno(String empCpfacno) {
		this.empCpfacno = empCpfacno;
	}

	public String getEmpRegion() {
		return empRegion;
	}

	public void setEmpRegion(String empRegion) {
		this.empRegion = empRegion;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getEmpPartyCode() {
		return empPartyCode;
	}

	public void setEmpPartyCode(String empPartyCode) {
		this.empPartyCode = empPartyCode;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public List getVoucherDetails() {
		return voucherDetails;
	}

	public void setVoucherDetails(List voucherDetails) {
		this.voucherDetails = voucherDetails;
	}

	public String getPartyDetails() {
		return partyDetails;
	}

	public void setPartyDetails(String partyDetails) {
		this.partyDetails = partyDetails;
	}

	public String getVoucherDt() {
		return voucherDt;
	}

	public void setVoucherDt(String voucherDt) {
		this.voucherDt = voucherDt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPreparedDt() {
		return preparedDt;
	}

	public void setPreparedDt(String preparedDt) {
		this.preparedDt = preparedDt;
	}

	public String getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String unitName) {
		this.unitName = unitName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
}
