package com.epis.bean.rpfc;

import java.io.Serializable;

public class RPFCForm9Bean implements Serializable{
	String empPFID="",empPensionNo="",cpfaccno="",employeeName="",employeeNo="",empDesig="",empDOJ="";
	String empDOS="",empAirportCode="",region="",fhName="";
	String fromEmpYear="",toEmpYear="",dateOfBirth="";
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
	}
	public String getFromEmpYear() {
		return fromEmpYear;
	}
	public void setFromEmpYear(String fromEmpYear) {
		this.fromEmpYear = fromEmpYear;
	}
	public String getToEmpYear() {
		return toEmpYear;
	}
	public void setToEmpYear(String toEmpYear) {
		this.toEmpYear = toEmpYear;
	}
	public String getCpfaccno() {
		return cpfaccno;
	}
	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
	}
	public String getEmpAirportCode() {
		return empAirportCode;
	}
	public void setEmpAirportCode(String empAirportCode) {
		this.empAirportCode = empAirportCode;
	}
	public String getEmpDesig() {
		return empDesig;
	}
	public void setEmpDesig(String empDesig) {
		this.empDesig = empDesig;
	}

	public String getEmpDOJ() {
		return empDOJ;
	}
	public void setEmpDOJ(String empDOJ) {
		this.empDOJ = empDOJ;
	}
	public String getEmpDOS() {
		return empDOS;
	}
	public void setEmpDOS(String empDOS) {
		this.empDOS = empDOS;
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
	public String getEmpPensionNo() {
		return empPensionNo;
	}
	public void setEmpPensionNo(String empPensionNo) {
		this.empPensionNo = empPensionNo;
	}
	public String getEmpPFID() {
		return empPFID;
	}
	public void setEmpPFID(String empPFID) {
		this.empPFID = empPFID;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}
