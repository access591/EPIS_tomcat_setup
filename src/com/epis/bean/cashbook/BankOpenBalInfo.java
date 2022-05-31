package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class BankOpenBalInfo extends RequestBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountNo;
	private String opendate;
	private double amount;
	private String details;
	private String bankName;
	private String amountType;
	
	public BankOpenBalInfo(HttpServletRequest request){
		super(request);
		}
	
	public BankOpenBalInfo(){
			
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {		
		this.accountNo = accountNo;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
