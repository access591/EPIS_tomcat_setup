/**
  * File       : PensionBean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.epis.bean.rpfc;

public class ArrearsTransactionBean {
	private String empName ="",empNumber="",fhName="";
	private String pensionNumber="",cpfAcNo="",empSerialNo="";	
	private String dateofJoining="",dateOfAnnuation="",dateofBirth="";
	private String wetherOption="";
	private String station="";
	private String emoluments = "";
	private String emppfstatury = "";
	private String monthYear = "";
	private String pensioncontri="";
	private String arrearAmount="";
	private String dueEmoluments="";
	private String region="",employeePF="0.00",emolumentsArrear="0.00",arrearMonth="";
	
	public String getArrearMonth() {
		return arrearMonth;
	}
	public void setArrearMonth(String arrearMonth) {
		this.arrearMonth = arrearMonth;
	}
	public String getEmolumentsArrear() {
		return emolumentsArrear;
	}
	public void setEmolumentsArrear(String emolumentsArrear) {
		this.emolumentsArrear = emolumentsArrear;
	}
	public String getEmployeePF() {
		return employeePF;
	}
	public void setEmployeePF(String employeePF) {
		this.employeePF = employeePF;
	}
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
	}
	
	
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public String getEmpSerialNo() {
		return empSerialNo;
	}
	public void setEmpSerialNo(String empSerialNo) {
		this.empSerialNo = empSerialNo;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getDateofJoining() {
		return dateofJoining;
	}
	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
	}
	
	public String getDateOfAnnuation() {
		return dateOfAnnuation;
	}
	public void setDateOfAnnuation(String dateOfAnnuation) {
		this.dateOfAnnuation = dateOfAnnuation;
	}
	public String getWetherOption() {
		return wetherOption;
	}
	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPensionNumber() {
		return pensionNumber;
	}
	public void setPensionNumber(String pensionNumber) {
		this.pensionNumber = pensionNumber;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}	
	
	public String getCpfAcNo() {
		return cpfAcNo;
	}
	public void setCpfAcNo(String cpfAcNo) {
		this.cpfAcNo = cpfAcNo;
	}
	public String getEmoluments() {
		return emoluments;
	}
	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}	
	public String getEmppfstatury() {
		return emppfstatury;
	}
	public void setEmppfstatury(String emppfstatury) {
		this.emppfstatury = emppfstatury;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}		
	public String getPensioncontri() {
		return pensioncontri;
	}
	public void setPensioncontri(String pensioncontri) {
		this.pensioncontri = pensioncontri;
	}
	public String getArrearAmount() {
		return arrearAmount;
	}
	public void setArrearAmount(String arrearAmount) {
		this.arrearAmount = arrearAmount;
	}
	public String getDueEmoluments() {
		return dueEmoluments;
	}
	public void setDueEmoluments(String dueEmoluments) {
		this.dueEmoluments = dueEmoluments;
	}
}
