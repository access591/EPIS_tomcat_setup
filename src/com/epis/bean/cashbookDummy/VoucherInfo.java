package com.epis.bean.cashbookDummy;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class VoucherInfo extends RequestBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String keyNo;
	private String otherTransactionType;//PFW/CPF/FS	
	private String bankName;
	private String transactionId;
	private String accountHead;
	private String toAccountHead;
	private String fromVoucherNo;
	private String toVoucherNo;
	private String path;

	private String accountNo;

	private String finYear;

	private String trustType;

	private String voucherType;

	private String partyType;

	private String empPartyCode;

	private String partyDetails;

	private String voucherNo;

	private String preparedBy;

	private String checkedBy;

	private String approvedBy;

	private String enteredBy;

	private String enteredDate;

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
	private String rowid;
	private String designation;
	private String pfid;
	private String transactionType;
	private String empUnitName;
	private String purpose;
	private String appStatus;
	private String vtype;
	private String ISIN;

	public String getISIN() {
		return ISIN;
	}

	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getEmpUnitName() {
		return empUnitName;
	}

	public void setEmpUnitName(String empUnitName) {
		this.empUnitName = empUnitName;
	}

	public VoucherInfo() {
	}
	
	public VoucherInfo(HttpServletRequest request) {
		super(request);
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

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getOtherTransactionType() {
		return otherTransactionType;
	}

	public void setOtherTransactionType(String otherTransactionType) {
		this.otherTransactionType = otherTransactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getToAccountHead() {
		return toAccountHead;
	}

	public void setToAccountHead(String toAccountHead) {
		this.toAccountHead = toAccountHead;
	}

	public String getFromVoucherNo() {
		return fromVoucherNo;
	}

	public void setFromVoucherNo(String fromVoucherNo) {
		this.fromVoucherNo = fromVoucherNo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getToVoucherNo() {
		return toVoucherNo;
	}

	public void setToVoucherNo(String toVoucherNo) {
		this.toVoucherNo = toVoucherNo;
	}
}
