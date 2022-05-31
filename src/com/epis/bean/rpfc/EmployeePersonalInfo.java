/**
 * File       : EmployeePersonalInfo.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeePersonalInfo implements Serializable{
	private String employeeNumber="",employeeName="",airportCode="",region="",cpfAccno="",designation="",empDesLevel="",refPensionNumber="";
	private String recordVerified="",chkEmpNameFlag="false",pensionNo="",fhName="",seperationReason="",seperationDate="",emailID="",empNomineeSharable="";
	private String wetherOption="",dateOfBirth="",dateOfJoining="",remarks="",gender="",lastActive="",division="",maritalStatus="",otherReason="",department="";
	private String perAddress="",tempAddress="",form2Nominee="",airportSerialNumber="",dateOfEntitle="",nationality="",heightWithInches="";
	private String dateOfAnnuation="",pfID="",fhFlag="",pensionInfo="",finalSettlementDate="",seperationFromDate="";
	private String transferFlag="",oldPensionNo="",pfIDString="",whetherOptionDescr="",mappingFlag="",ageAtEntry="",basicWages="";
	private String resttlmentDate="",phoneNumber="",chkArrearFlag="",chkarrearAdj="";
	private String adjAmount="",adjInt="",adjDate="",adjRemarks="",monthyear="",fromarrearreviseddate="",toarrearreviseddate="";

	ArrayList pensionList=new ArrayList();
	ArrayList nomineeList=new ArrayList();
	public String getAdjAmount() {
		return adjAmount;
	}

	public void setAdjAmount(String adjAmount) {
		this.adjAmount = adjAmount;
	}

	public String getAdjDate() {
		return adjDate;
	}

	public void setAdjDate(String adjDate) {
		this.adjDate = adjDate;
	}

	public String getAdjInt() {
		return adjInt;
	}

	public void setAdjInt(String adjInt) {
		this.adjInt = adjInt;
	}

	public String getAdjRemarks() {
		return adjRemarks;
	}

	public void setAdjRemarks(String adjRemarks) {
		this.adjRemarks = adjRemarks;
	}

	public String getChkarrearAdj() {
		return chkarrearAdj;
	}

	public void setChkarrearAdj(String chkarrearAdj) {
		this.chkarrearAdj = chkarrearAdj;
	}

	public String getFromarrearreviseddate() {
		return fromarrearreviseddate;
	}

	public void setFromarrearreviseddate(String fromarrearreviseddate) {
		this.fromarrearreviseddate = fromarrearreviseddate;
	}

	public String getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}

	public String getToarrearreviseddate() {
		return toarrearreviseddate;
	}

	public void setToarrearreviseddate(String toarrearreviseddate) {
		this.toarrearreviseddate = toarrearreviseddate;
	}

	public ArrayList getPensionList() {
		return pensionList;
	}

	public String getAgeAtEntry() {
		return ageAtEntry;
	}

	public void setAgeAtEntry(String ageAtEntry) {
		this.ageAtEntry = ageAtEntry;
	}

	public String getBasicWages() {
		return basicWages;
	}

	public void setBasicWages(String basicWages) {
		this.basicWages = basicWages;
	}

	public String getMappingFlag() {
		return mappingFlag;
	}

	public void setMappingFlag(String mappingFlag) {
		this.mappingFlag = mappingFlag;
	}

	public void setPensionList(ArrayList pensionList) {
		this.pensionList = pensionList;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public String getFhFlag() {
		return fhFlag;
	}

	public void setFhFlag(String fhFlag) {
		this.fhFlag = fhFlag;
	}

	public String getPfID() {
        return pfID;
    }

    public void setPfID(String pfID) {
        this.pfID = pfID;
    }

    public String getDateOfAnnuation() {
		return dateOfAnnuation;
	}

	public void setDateOfAnnuation(String dateOfAnnuation) {
		this.dateOfAnnuation = dateOfAnnuation;
	}

	public String getAirportSerialNumber() {
		return airportSerialNumber;
	}

	public void setAirportSerialNumber(String airportSerialNumber) {
		this.airportSerialNumber = airportSerialNumber;
	}

	public String getForm2Nominee() {
		return form2Nominee;
	}

	public void setForm2Nominee(String form2Nominee) {
		this.form2Nominee = form2Nominee;
	}

	public String getPerAddress() {
		return perAddress;
	}

	public void setPerAddress(String perAddress) {
		this.perAddress = perAddress;
	}

	public String getTempAddress() {
		return tempAddress;
	}

	public void setTempAddress(String tempAddress) {
		this.tempAddress = tempAddress;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getEmpDesLevel() {
		return empDesLevel;
	}

	public void setEmpDesLevel(String empDesLevel) {
		this.empDesLevel = empDesLevel;
	}

	public String getEmpNomineeSharable() {
		return empNomineeSharable;
	}

	public void setEmpNomineeSharable(String empNomineeSharable) {
		this.empNomineeSharable = empNomineeSharable;
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getRefPensionNumber() {
		return refPensionNumber;
	}

	public void setRefPensionNumber(String refPensionNumber) {
		this.refPensionNumber = refPensionNumber;
	}

	public String getSeperationDate() {
		return seperationDate;
	}

	public void setSeperationDate(String seperationDate) {
		this.seperationDate = seperationDate;
	}

	public String getSeperationReason() {
		return seperationReason;
	}

	public void setSeperationReason(String seperationReason) {
		this.seperationReason = seperationReason;
	}

	public String getPensionNo() {
		return pensionNo;
	}

	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getWetherOption() {
		return wetherOption;
	}

	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}

	public String getChkEmpNameFlag() {
		return chkEmpNameFlag;
	}

	public void setChkEmpNameFlag(String chkEmpNameFlag) {
		this.chkEmpNameFlag = chkEmpNameFlag;
	}

	public String getRecordVerified() {
		return recordVerified;
	}

	public void setRecordVerified(String recordVerified) {
		this.recordVerified = recordVerified;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCpfAccno() {
		return cpfAccno;
	}

	public void setCpfAccno(String cpfAccno) {
		this.cpfAccno = cpfAccno;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

    public String getOldPensionNo() {
        return oldPensionNo;
    }

    public void setOldPensionNo(String oldPensionNo) {
        this.oldPensionNo = oldPensionNo;
    }

    public String getPfIDString() {
        return pfIDString;
    }

    public void setPfIDString(String pfIDString) {
        this.pfIDString = pfIDString;
    }

    public String getWhetherOptionDescr() {
        return whetherOptionDescr;
    }

    public void setWhetherOptionDescr(String whetherOptionDescr) {
        this.whetherOptionDescr = whetherOptionDescr;
    }

	public String getPensionInfo() {
		return pensionInfo;
	}

	public void setPensionInfo(String pensionInfo) {
		this.pensionInfo = pensionInfo;
	}

	public String getDateOfEntitle() {
		return dateOfEntitle;
	}

	public void setDateOfEntitle(String dateOfEntitle) {
		this.dateOfEntitle = dateOfEntitle;
	}

	public String getFinalSettlementDate() {
		return finalSettlementDate;
	}

	public void setFinalSettlementDate(String finalSettlementDate) {
		this.finalSettlementDate = finalSettlementDate;
	}

	public String getSeperationFromDate() {
		return seperationFromDate;
	}

	public void setSeperationFromDate(String seperationFromDate) {
		this.seperationFromDate = seperationFromDate;
	}

	public String getHeightWithInches() {
		return heightWithInches;
	}

	public void setHeightWithInches(String heightWithInches) {
		this.heightWithInches = heightWithInches;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getResttlmentDate() {
		return resttlmentDate;
	}

	public void setResttlmentDate(String resttlmentDate) {
		this.resttlmentDate = resttlmentDate;
	}

	public String getChkArrearFlag() {
		return chkArrearFlag;
	}

	public void setChkArrearFlag(String chkArrearFlag) {
		this.chkArrearFlag = chkArrearFlag;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ArrayList getNomineeList() {
		return nomineeList;
	}

	public void setNomineeList(ArrayList nomineeList) {
		this.nomineeList = nomineeList;
	}



}
