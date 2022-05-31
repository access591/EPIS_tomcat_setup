package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class FundsTransferInfo extends RequestBean {
	
	public FundsTransferInfo(HttpServletRequest request){
		super(request);
		}
	
	public FundsTransferInfo() {

	}
	
	private String fromAccountNo;
	private String toAccountNo;
	private String preparationDate;
	private String amount;
	private String keyno;
	private String approval;
	private String finalApproval;
	private String address;
	private String accountType;
	private String toBank;
	private String fromBank;
	private String accountName;
	private String ifscCode;
	private String appDesignation;
	private String appSignPath;
	private String appDispalyName;
	private String finAppDesignation;
	private String finAppSignPath;
	private String finAppDispalyName;
	private String approvedBy;
	private String approvedDt;
	private String finalApprovedBy;
	private String finalApprovedDt;
	private String letterNo;
	
	public String getLetterNo() {
		return letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFromAccountNo() {
		return fromAccountNo;
	}

	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}

	public String getPreparationDate() {
		return preparationDate;
	}

	public void setPreparationDate(String preparationDate) {
		this.preparationDate = preparationDate;
	}

	public String getToAccountNo() {
		return toAccountNo;
	}

	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFromBank() {
		return fromBank;
	}

	public void setFromBank(String fromBank) {
		this.fromBank = fromBank;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getToBank() {
		return toBank;
	}

	public void setToBank(String toBank) {
		this.toBank = toBank;
	}

	public String getAppDesignation() {
		return appDesignation;
	}

	public void setAppDesignation(String appDesignation) {
		this.appDesignation = appDesignation;
	}

	public String getAppDispalyName() {
		return appDispalyName;
	}

	public void setAppDispalyName(String appDispalyName) {
		this.appDispalyName = appDispalyName;
	}

	public String getAppSignPath() {
		return appSignPath;
	}

	public void setAppSignPath(String appSignPath) {
		this.appSignPath = appSignPath;
	}

	public String getFinAppDesignation() {
		return finAppDesignation;
	}

	public void setFinAppDesignation(String finAppDesignation) {
		this.finAppDesignation = finAppDesignation;
	}

	public String getFinAppDispalyName() {
		return finAppDispalyName;
	}

	public void setFinAppDispalyName(String finAppDispalyName) {
		this.finAppDispalyName = finAppDispalyName;
	}

	public String getFinAppSignPath() {
		return finAppSignPath;
	}

	public void setFinAppSignPath(String finAppSignPath) {
		this.finAppSignPath = finAppSignPath;
	}
	
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public String getApprovedDt() {
		
		return approvedDt;
	} 
	
	public void setApprovedDt(String approvedDt) {
		this.approvedDt = approvedDt;
	}

	public void setFinalApprovedBy(String finalApprovedBy) {
		this.finalApprovedBy = finalApprovedBy;
		
	}
	
	public String getFinalApprovedBy() {
		return finalApprovedBy;
		
	}
	public String getfFinalApprovedDt() {
			
			return  finalApprovedDt;
		} 

	public void setFinalApprovedDt(String finalApprovedDt) {
		this.finalApprovedDt = finalApprovedDt;
		
	}
	

}
