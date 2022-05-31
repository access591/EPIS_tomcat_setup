/**
  * File       : PensionContBean.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class PensionContBean implements Serializable{
	String cpfacno="",employeeNM="",empDOB="",empDOJ="",empSerialNo="",empRegion="",gender="",employeeNO="",fhName="";
	String designation="",whetherOption="",dateOfEntitle="";
	String pensionNo="",maritalStatus="",pfID="",department="",empCpfaccno="",pfSettled="",finalSettlementDate="";
	public String getPfSettled() {
		return pfSettled;
	}

	public void setPfSettled(String pfSettled) {
		this.pfSettled = pfSettled;
	}

	public String getEmpCpfaccno() {
		return empCpfaccno;
	}

	public void setEmpCpfaccno(String empCpfaccno) {
		this.empCpfaccno = empCpfaccno;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	String countFlag="";
	String prepareString="";
	ArrayList empPensionList=new ArrayList();
	ArrayList blockList=new ArrayList();
	public ArrayList getBlockList() {
		return blockList;
	}

	public void setBlockList(ArrayList blockList) {
		this.blockList = blockList;
	}

	public String getCpfacno() {
		return cpfacno;
	}

	public void setCpfacno(String cpfacno) {
		this.cpfacno = cpfacno;
	}

	public String getEmpDOB() {
		return empDOB;
	}

	public void setEmpDOB(String empDOB) {
		this.empDOB = empDOB;
	}

	public String getEmpDOJ() {
		return empDOJ;
	}

	public void setEmpDOJ(String empDOJ) {
		this.empDOJ = empDOJ;
	}

	public String getEmployeeNM() {
		return employeeNM;
	}

	public void setEmployeeNM(String employeeNM) {
		this.employeeNM = employeeNM;
	}

	public String getEmpSerialNo() {
		return empSerialNo;
	}

	public void setEmpSerialNo(String empSerialNo) {
		this.empSerialNo = empSerialNo;
	}



	public String getEmpRegion() {
		return empRegion;
	}

	public void setEmpRegion(String empRegion) {
		this.empRegion = empRegion;
	}

	public ArrayList getEmpPensionList() {
		return empPensionList;
	}

	public void setEmpPensionList(ArrayList empPensionList) {
		this.empPensionList = empPensionList;
	}

	public String getEmployeeNO() {
		return employeeNO;
	}

	public void setEmployeeNO(String employeeNO) {
		this.employeeNO = employeeNO;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFhName() {
		return fhName;
	}

	public void setFhName(String fhName) {
		this.fhName = fhName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getWhetherOption() {
		return whetherOption;
	}

	public void setWhetherOption(String whetherOption) {
		this.whetherOption = whetherOption;
	}

	public String getDateOfEntitle() {
		return dateOfEntitle;
	}

	public void setDateOfEntitle(String dateOfEntitle) {
		this.dateOfEntitle = dateOfEntitle;
	}

	public String getPensionNo() {
		return pensionNo;
	}

	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

	public String getPfID() {
		return pfID;
	}

	public void setPfID(String pfID) {
		this.pfID = pfID;
	}

	public String getCountFlag() {
		return countFlag;
	}

	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}

	public String getPrepareString() {
		return prepareString;
	}

	public void setPrepareString(String prepareString) {
		this.prepareString = prepareString;
	}

	public String getFinalSettlementDate() {
		return finalSettlementDate;
	}

	public void setFinalSettlementDate(String finalSettlementDate) {
		this.finalSettlementDate = finalSettlementDate;
	}
}
