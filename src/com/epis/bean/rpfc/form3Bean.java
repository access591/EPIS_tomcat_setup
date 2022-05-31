 /**
  * File       : form3Bean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.rpfc;

import java.io.Serializable;

public class form3Bean implements Serializable{
	String cpfaccno="",employeeNo="",pensionNumber="",employeeName="";
	String designation="",familyMemberName="",emoluments="",dateOfBirth="";
	String dateOfEntitle="",remarks="",sex="",airportCode="",ageAtEntry="";
	double empSubStatutory = 0.0,empSubOptional = 0.0,principle = 0.0,interest = 0.0,pfAdvRecTotal=0.0,empContrPF = 0.0,empContrPension = 0.0,empContrTotal = 0.0,totalPension=0.0,totalEmoluments=0.0;
	String dateOfLeaving="",leavingReason="",wetherOption="",dateOfJoining="",pfID="";
	int subScribersCount = 0;
	String accntNo="";
	double remittedAmt = 0.0;
	String remittedDate = "", bankName="", bankAddr="", region="", monthYear="";
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getEmpContrPension() {
		return empContrPension;
	}
	public void setEmpContrPension(double empContrPension) {
		this.empContrPension = empContrPension;
	}
	public double getEmpContrPF() {
		return empContrPF;
	}
	public void setEmpContrPF(double empContrPF) {
		this.empContrPF = empContrPF;
	}
	public double getEmpContrTotal() {
		return empContrTotal;
	}
	public void setEmpContrTotal(double empContrTotal) {
		this.empContrTotal = empContrTotal;
	}
	public double getEmpSubOptional() {
		return empSubOptional;
	}
	public void setEmpSubOptional(double empSubOptional) {
		this.empSubOptional = empSubOptional;
	}
	public double getEmpSubStatutory() {
		return empSubStatutory;
	}
	public void setEmpSubStatutory(double empSubStatutory) {
		this.empSubStatutory = empSubStatutory;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public double getPfAdvRecTotal() {
		return pfAdvRecTotal;
	}
	public void setPfAdvRecTotal(double pfAdvRecTotal) {
		this.pfAdvRecTotal = pfAdvRecTotal;
	}
	public double getPrinciple() {
		return principle;
	}
	public void setPrinciple(double principle) {
		this.principle = principle;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getRemittedAmt() {
		return remittedAmt;
	}
	public void setRemittedAmt(double remittedAmt) {
		this.remittedAmt = remittedAmt;
	}
	public String getRemittedDate() {
		return remittedDate;
	}
	public void setRemittedDate(String remittedDate) {
		this.remittedDate = remittedDate;
	}
	public int getSubScribersCount() {
		return subScribersCount;
	}
	public void setSubScribersCount(int subScribersCount) {
		this.subScribersCount = subScribersCount;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDateOfEntitle() {
		return dateOfEntitle;
	}
	public void setDateOfEntitle(String dateOfEntitle) {
		this.dateOfEntitle = dateOfEntitle;
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
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getFamilyMemberName() {
		return familyMemberName;
	}
	public void setFamilyMemberName(String familyMemberName) {
		this.familyMemberName = familyMemberName;
	}
	public String getPensionNumber() {
		return pensionNumber;
	}
	public void setPensionNumber(String pensionNumber) {
		this.pensionNumber = pensionNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAgeAtEntry() {
		return ageAtEntry;
	}
	public void setAgeAtEntry(String ageAtEntry) {
		this.ageAtEntry = ageAtEntry;
	}
	public String getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getLeavingReason() {
		return leavingReason;
	}
	public void setLeavingReason(String leavingReason) {
		this.leavingReason = leavingReason;
	}
	public String getWetherOption() {
		return wetherOption;
	}
	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}
    public String getDateOfJoining() {
        return dateOfJoining;
    }
    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
	public String getPfID() {
		return pfID;
	}
	public void setPfID(String pfID) {
		this.pfID = pfID;
	}
	public String getAccntNo() {
		return accntNo;
	}
	public void setAccntNo(String accntNo) {
		this.accntNo = accntNo;
	}
	
	public double getTotalPension() {
		return totalPension;
	}
	public void setTotalPension(double totalPension) {
		this.totalPension = totalPension;
	}
	public double getTotalEmoluments() {
		return totalEmoluments;
	}
	public void setTotalEmoluments(double totalEmoluments) {
		this.totalEmoluments = totalEmoluments;
	}
}
