package com.epis.bean.cashbook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class JournalVoucherInfo extends RequestBean {
	
	public JournalVoucherInfo(HttpServletRequest request){
		super(request);
		}
	
	public JournalVoucherInfo() {

	}
	
	private String keyNo;
	private String partyType;
	private String pfid;
	private String partyCode;
	private String pfidFull;
	private String employeeName;
	private String other;
	private String trustType;
	private String voucherNo;
	private String voucherDate;
	private String unitCode;
	private String region;
	private String details;
	private String preparedBy;
	private String preparationDate;
	private String approval;
	private String approvedBy;
	private String approvalDate;
	private List debitList;
	private List creditList;
	private String stationName;
	private String[] byRecords;
	private String[] toRecords;
	private String partyName;
	private String finYear;		
	private String amount;	

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getPreparationDate() {
		return preparationDate;
	}

	public void setPreparationDate(String preparationDate) {
		this.preparationDate = preparationDate;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

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

	public List getCreditList() {
		return creditList;
	}

	public void setCreditList(List creditList) {
		this.creditList = creditList;
	}

	public List getDebitList() {
		return debitList;
	}

	public void setDebitList(List debitList) {
		this.debitList = debitList;
	}

	public String[] getByRecords() {
		return byRecords;
	}

	public void setByRecords(String[] byRecords) {
		this.byRecords = byRecords;
	}

	public String[] getToRecords() {
		return toRecords;
	}

	public void setToRecords(String[] toRecords) {
		this.toRecords = toRecords;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPfidFull() {
		return pfidFull;
	}

	public void setPfidFull(String pfidFull) {
		this.pfidFull = pfidFull;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
}
