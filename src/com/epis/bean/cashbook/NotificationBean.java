package com.epis.bean.cashbook;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class NotificationBean  extends RequestBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotificationBean(){
		
	}
	
	public NotificationBean(HttpServletRequest request){
		super(request);
	}

	private String pensionNo ;
	private String employeeName ;
	private String keyNo ;
	private String approvedDate ;
	private String transactionId ;
	private String transactionDate;
	private String notificationtype;
	private String airportCode;
	private String approvedAmt;
	private String paidAmt;
	private String remaingAmt;
	
	public String getApprovedAmt() {
		return approvedAmt;
	}

	public void setApprovedAmt(String approvedAmt) {
		this.approvedAmt = approvedAmt;
	}

	public String getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getRemaingAmt() {
		return remaingAmt;
	}

	public void setRemaingAmt(String remaingAmt) {
		this.remaingAmt = remaingAmt;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getPensionNo() {
		return pensionNo;
	}

	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getNotificationtype() {
		return notificationtype;
	}

	public void setNotificationtype(String notificationtype) {
		this.notificationtype = notificationtype;
	}

}
