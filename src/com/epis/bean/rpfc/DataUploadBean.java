/**
 * File       : EmpMasterBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;

import java.io.Serializable;


public class DataUploadBean implements Serializable{
	String srno = "", empNumber = "", cpfAcNo = "",empName = "", designation = "", empLevel = "",cpf = "", dateofBirth = "", dateofJoining = "";
	String remarks = "", station = "", fhName = "",gender = "", department = "", division = "";
	String emoluments = "", aaiPf = "", aaiPension = "", aaiTotal = "";
	String empPFStatury = "", empVPF = "", empTotal = "",empPrincipal = "", empInterest = "",region = "", lastActive = "";
	//PF rec.from previous employer 
	String empSubscription="",empContribution="",empPensionCert="";
	String computerName = "",pfid = "";
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
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCpfAcNo() {
		return cpfAcNo;
	}
	public void setCpfAcNo(String cpfAcNo) {
		this.cpfAcNo = cpfAcNo;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getEmoluments() {
		return emoluments;
	}
	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}
	public String getEmpContribution() {
		return empContribution;
	}
	public void setEmpContribution(String empContribution) {
		this.empContribution = empContribution;
	}
	public String getEmpInterest() {
		return empInterest;
	}
	public void setEmpInterest(String empInterest) {
		this.empInterest = empInterest;
	}
	public String getEmpLevel() {
		return empLevel;
	}
	public void setEmpLevel(String empLevel) {
		this.empLevel = empLevel;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public String getEmpPensionCert() {
		return empPensionCert;
	}
	public void setEmpPensionCert(String empPensionCert) {
		this.empPensionCert = empPensionCert;
	}
	public String getEmpPFStatury() {
		return empPFStatury;
	}
	public void setEmpPFStatury(String empPFStatury) {
		this.empPFStatury = empPFStatury;
	}
	public String getEmpPrincipal() {
		return empPrincipal;
	}
	public void setEmpPrincipal(String empPrincipal) {
		this.empPrincipal = empPrincipal;
	}
	public String getEmpSubscription() {
		return empSubscription;
	}
	public void setEmpSubscription(String empSubscription) {
		this.empSubscription = empSubscription;
	}
	public String getEmpTotal() {
		return empTotal;
	}
	public void setEmpTotal(String empTotal) {
		this.empTotal = empTotal;
	}
	public String getEmpVPF() {
		return empVPF;
	}
	public void setEmpVPF(String empVPF) {
		this.empVPF = empVPF;
	}
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLastActive() {
		return lastActive;
	}
	public void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}
	public String getPfid() {
		return pfid;
	}
	public void setPfid(String pfid) {
		this.pfid = pfid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSrno() {
		return srno;
	}
	public void setSrno(String srno) {
		this.srno = srno;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}




	

	

}
