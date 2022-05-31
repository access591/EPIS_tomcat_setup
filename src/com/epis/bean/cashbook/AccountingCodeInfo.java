package com.epis.bean.cashbook;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class AccountingCodeInfo extends RequestBean implements Serializable{
	
	public AccountingCodeInfo(){
		
	}
	
	public AccountingCodeInfo(HttpServletRequest request){
		super(request);
	}
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountHead;
	private String particular;
	private String type;
	private String date;
	private String trustType;
	private String amount;
	private String amountType;
	private String displayName;
	
	private Map openBalances;
	
	public String getAccountHead() {
		return accountHead;
	}
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Map getOpenBalances() {
		return openBalances;
	}
	public void setOpenBalances(Map openBalances) {
		this.openBalances = openBalances;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getDisplayName() {
		displayName = accountHead+" - "+particular;
		return displayName;
	}
}
