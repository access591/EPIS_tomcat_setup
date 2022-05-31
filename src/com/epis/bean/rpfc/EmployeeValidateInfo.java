/**
 * File       : EmployeeValidateInfo.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;

import java.io.Serializable;

public class EmployeeValidateInfo implements Serializable{
	
	String dateOfBirth="",desegnation="",employeeName="",employeeNo="",wetherOption="",cpfaccno="",emoluments="";
	String empPFStatuary="",empVPF="",empAdvRecPrincipal="",empAdvRecInterest="",emptotal="",aaiconPF="",aaiconPension="",aaiTotal="";
	String valAAIPension="",valAAIPF="",valAAITotal="",basic="",specialBasic="",dailyAllowance="",region="";
	String missingFlag="",pfAdvance="",advanceDrawn="",partFinal="",cpfInterest="",airportCD="",effectiveDate="";
	String missingInfo="",remarks="",pensionNumber="",monthYear="",updatedDate="",ipAddress="",userName="";
	public String getPensionNumber() {
		return pensionNumber;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPensionNumber(String pensionNumber) {
		this.pensionNumber = pensionNumber;
	}
	boolean validateFlag=false;
	public String getMissingInfo() {
		return missingInfo;
	}
	public void setMissingInfo(String missingInfo) {
		this.missingInfo = missingInfo;
	}
	public String getValAAIPension() {
		return valAAIPension;
	}
	public void setValAAIPension(String valAAIPension) {
		this.valAAIPension = valAAIPension;
	}
	public String getValAAIPF() {
		return valAAIPF;
	}
	public void setValAAIPF(String valAAIPF) {
		this.valAAIPF = valAAIPF;
	}
	public String getValAAITotal() {
		return valAAITotal;
	}
	public void setValAAITotal(String valAAITotal) {
		this.valAAITotal = valAAITotal;
	}
	public String getAaiconPension() {
		return aaiconPension;
	}
	public void setAaiconPension(String aaiconPension) {
		this.aaiconPension = aaiconPension;
	}
	public String getAaiconPF() {
		return aaiconPF;
	}
	public void setAaiconPF(String aaiconPF) {
		this.aaiconPF = aaiconPF;
	}
	public String getAaiTotal() {
		return aaiTotal;
	}
	public void setAaiTotal(String aaiTotal) {
		this.aaiTotal = aaiTotal;
	}
	public String getCpfaccno() {
		return cpfaccno;
	}
	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDesegnation() {
		return desegnation;
	}
	public void setDesegnation(String desegnation) {
		this.desegnation = desegnation;
	}
	public String getEmoluments() {
		return emoluments;
	}
	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}
	public String getEmpAdvRecInterest() {
		return empAdvRecInterest;
	}
	public void setEmpAdvRecInterest(String empAdvRecInterest) {
		this.empAdvRecInterest = empAdvRecInterest;
	}
	public String getEmpAdvRecPrincipal() {
		return empAdvRecPrincipal;
	}
	public void setEmpAdvRecPrincipal(String empAdvRecPrincipal) {
		this.empAdvRecPrincipal = empAdvRecPrincipal;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getEmpPFStatuary() {
		return empPFStatuary;
	}
	public void setEmpPFStatuary(String empPFStatuary) {
		this.empPFStatuary = empPFStatuary;
	}
	public String getEmptotal() {
		return emptotal;
	}
	public void setEmptotal(String emptotal) {
		this.emptotal = emptotal;
	}
	public String getEmpVPF() {
		return empVPF;
	}
	public void setEmpVPF(String empVPF) {
		this.empVPF = empVPF;
	}
	public String getWetherOption() {
		return wetherOption;
	}
	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}
	public boolean isValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(boolean validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getAdvanceDrawn() {
		return advanceDrawn;
	}
	public void setAdvanceDrawn(String advanceDrawn) {
		this.advanceDrawn = advanceDrawn;
	}
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getCpfInterest() {
		return cpfInterest;
	}
	public void setCpfInterest(String cpfInterest) {
		this.cpfInterest = cpfInterest;
	}
	public String getDailyAllowance() {
		return dailyAllowance;
	}
	public void setDailyAllowance(String dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}
	public String getMissingFlag() {
		return missingFlag;
	}
	public void setMissingFlag(String missingFlag) {
		this.missingFlag = missingFlag;
	}
	public String getPartFinal() {
		return partFinal;
	}
	public void setPartFinal(String partFinal) {
		this.partFinal = partFinal;
	}
	public String getPfAdvance() {
		return pfAdvance;
	}
	public void setPfAdvance(String pfAdvance) {
		this.pfAdvance = pfAdvance;
	}
	public String getSpecialBasic() {
		return specialBasic;
	}
	public void setSpecialBasic(String specialBasic) {
		this.specialBasic = specialBasic;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAirportCD() {
		return airportCD;
	}
	public void setAirportCD(String airportCD) {
		this.airportCD = airportCD;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

}
