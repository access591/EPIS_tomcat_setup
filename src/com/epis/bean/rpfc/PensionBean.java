/**
  * File       : PensionBean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.epis.bean.rpfc;

public class PensionBean {
	String airportCode = "";
	String employeeCode = "";
	String employeeName = "";
	String desegnation = "";
	String cpfAcNo = "";
	String acno="";
	String pensionnumber="";
	String remarks="";
	String dateofBirth="";
	String pensionOption="";
	String lastActive="";
	String dateofJoining ="";
	String region="";
	String sex="";
	String fHName="";
	String pensionCheck="";
	int regionId=0;
	String division="";
	String unitCode="";
	String empSerialNumber="";
	String department="";
	String totalRecrods="";
	String SeperationReason="",formsdisable="",pcreportverified="",seperationDate="",claimsprocess="";
	
		public String getSeperationReason() {
		return SeperationReason;
	}
	public void setSeperationReason(String seperationReason) {
		SeperationReason = seperationReason;
	}
		public String getTotalRecrods() {
		return totalRecrods;
	}
	public void setTotalRecrods(String totalRecrods) {
		this.totalRecrods = totalRecrods;
	}
		public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
		public String getEmpSerialNumber() {
		return empSerialNumber;
	}
	public void setEmpSerialNumber(String empSerialNumber) {
		this.empSerialNumber = empSerialNumber;
	}
		public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPensionCheck() {
		return pensionCheck;
	}
	public void setPensionCheck(String pensionCheck) {
		this.pensionCheck = pensionCheck;
	}
	public String getFHName() {
		return fHName;
	}
	public void setFHName(String name) {
		fHName = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDateofJoining() {
		return dateofJoining;
	}
	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
	}
	public String getLastActive() {
		return lastActive;
	}
	public void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getPensionOption() {
		return pensionOption;
	}
	public void setPensionOption(String pensionOption) {
		this.pensionOption = pensionOption;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPensionnumber() {
		return pensionnumber;
	}
	public void setPensionnumber(String pensionnumber) {
		this.pensionnumber = pensionnumber;
	}
	public String getAcno() {
		return acno;
	}
	public void setAcno(String acno) {
		this.acno = acno;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getCpfAcNo() {
		return cpfAcNo;
	}
	public void setCpfAcNo(String cpfAcNo) {
		this.cpfAcNo = cpfAcNo;
	}
	public String getDesegnation() {
		return desegnation;
	}
	public void setDesegnation(String desegnation) {
		this.desegnation = desegnation;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getFormsdisable() {
		return formsdisable;
	}
	public void setFormsdisable(String formsdisable) {
		this.formsdisable = formsdisable;
	}
	public String getPcreportverified() {
		return pcreportverified;
	}
	public void setPcreportverified(String pcreportverified) {
		this.pcreportverified = pcreportverified;
	}
	public String getSeperationDate() {
		return seperationDate;
	}
	public void setSeperationDate(String seperationDate) {
		this.seperationDate = seperationDate;
	}
	public String getClaimsprocess() {
		return claimsprocess;
	}
	public void setClaimsprocess(String claimsprocess) {
		this.claimsprocess = claimsprocess;
	}

}
