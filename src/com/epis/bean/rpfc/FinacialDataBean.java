/**
 * File       : FinacialDataBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import java.util.ArrayList;

public class FinacialDataBean {
	String cpfAccNo="",employeeOldNo="",employeeNewNo="",employeeName="",designation="",pensionOption="";
	String emoluments="",empPfStatuary="",empVpf="",principal="", interest="",empTotal="",aaiPf="",aaiPension="",aaiTotal="";
	String airportCode="",monthYear="",dateofBirth="",pensionNumber="";
	String basic="",specialBasic="",dailyAllowance="",toDate="",fromDate="",gpf="",remarks="";
	String missingFlag="",pfAdvance="",advanceDrawn="",partFinal="",cpfInterest="",missingPF="",region="",icon="",contextPath="";
    String cpfArriers="",dateofJoining="",cpfDifference="",previousMonth="",penContri="";
    
	ArrayList transferInList,transferOutList;
	public String getCpfDifference() {
		return cpfDifference;
	}
	public void setCpfDifference(String cpfDifference) {
		this.cpfDifference = cpfDifference;
	}
	public String getPreviousMonth() {
		return previousMonth;
	}
	public void setPreviousMonth(String previousMonth) {
		this.previousMonth = previousMonth;
	}
	public String getDateofJoining() {
		return dateofJoining;
	}
	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
	}
	public String getCpfArriers() {
		return cpfArriers;
	}
	public String getPenContri() {
		return penContri;
	}
	public void setPenContri(String penContri) {
		this.penContri = penContri;
	}
	public void setCpfArriers(String cpfArriers) {
		this.cpfArriers = cpfArriers;
	}
	public String getCpfInterest() {
		return cpfInterest;
	}
	public void setCpfInterest(String cpfInterest) {
		this.cpfInterest = cpfInterest;
	}
	public String getAdvanceDrawn() {
		return advanceDrawn;
	}
	public void setAdvanceDrawn(String advanceDrawn) {
		this.advanceDrawn = advanceDrawn;
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
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getDailyAllowance() {
		return dailyAllowance;
	}
	public void setDailyAllowance(String dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}
	public String getSpecialBasic() {
		return specialBasic;
	}
	public void setSpecialBasic(String specialBasic) {
		this.specialBasic = specialBasic;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getAaiPension() {
		return aaiPension;
	}
	public void setAaiPension(String aaiPension) {
		this.aaiPension = aaiPension;
	}
	public String getAaiPf() {
		return aaiPf;
	}
	public void setAaiPf(String aaiPf) {
		this.aaiPf = aaiPf;
	}
	public String getAaiTotal() {
		return aaiTotal;
	}
	public void setAaiTotal(String aaiTotal) {
		this.aaiTotal = aaiTotal;
	}
	public String getCpfAccNo() {
		return cpfAccNo;
	}
	public void setCpfAccNo(String cpfAccNo) {
		this.cpfAccNo = cpfAccNo;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getEmoluments() {
		return emoluments;
	}
	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNewNo() {
		return employeeNewNo;
	}
	public void setEmployeeNewNo(String employeeNewNo) {
		this.employeeNewNo = employeeNewNo;
	}
	public String getEmployeeOldNo() {
		return employeeOldNo;
	}
	public void setEmployeeOldNo(String employeeOldNo) {
		this.employeeOldNo = employeeOldNo;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPensionOption() {
		return pensionOption;
	}
	public void setPensionOption(String pensionOption) {
		this.pensionOption = pensionOption;
	}
	
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getEmpPfStatuary() {
		return empPfStatuary;
	}
	public void setEmpPfStatuary(String empPfStatuary) {
		this.empPfStatuary = empPfStatuary;
	}
	public String getEmpTotal() {
		return empTotal;
	}
	public void setEmpTotal(String empTotal) {
		this.empTotal = empTotal;
	}
	public String getEmpVpf() {
		return empVpf;
	}
	public void setEmpVpf(String empVpf) {
		this.empVpf = empVpf;
	}
	public String getMissingPF() {
		return missingPF;
	}
	public void setMissingPF(String missingPF) {
		this.missingPF = missingPF;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getPensionNumber() {
		return pensionNumber;
	}
	public void setPensionNumber(String pensionNumber) {
		this.pensionNumber = pensionNumber;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getGpf() {
		return gpf;
	}
	public void setGpf(String gpf) {
		this.gpf = gpf;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public ArrayList getTransferInList() {
		return transferInList;
	}
	public void setTransferInList(ArrayList transferInList) {
		this.transferInList = transferInList;
	}
	public ArrayList getTransferOutList() {
		return transferOutList;
	}
	public void setTransferOutList(ArrayList transferOutList) {
		this.transferOutList = transferOutList;
	}
	


}
