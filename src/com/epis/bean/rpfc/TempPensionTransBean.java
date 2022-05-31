
/**
 * File       : TempPensionTransBean.java
 * Date       : 08/07/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.bean.rpfc;

import java.io.Serializable;

public class TempPensionTransBean implements Serializable {
	String emoluments="",monthyear="",cpf="",station="",pensionContr="",remarks="",genMonthYear="";
    String empVPF="",empAdvRec="",empInrstRec="",aaiPFCont="";
    String transCpfaccno="",region="",recordCount="",pfID="",interestRate="";
    String dbPensionCtr="",employeeLoan="",advAmount="",aaiLoan="", dataFreezFlag="";
    String form7Narration="",pcHeldAmt="",noofMonths="", pccalApplied="",arrearFlag="",deputationFlag="";

	public String getArrearFlag() {
		return arrearFlag;
	}

	public void setArrearFlag(String arrearFlag) {
		this.arrearFlag = arrearFlag;
	}

	public String getDeputationFlag() {
		return deputationFlag;
	}

	public void setDeputationFlag(String deputationFlag) {
		this.deputationFlag = deputationFlag;
	}

	public String getAaiLoan() {
		return aaiLoan;
	}

	public String getDataFreezFlag() {
		return dataFreezFlag;
	}

	public void setDataFreezFlag(String dataFreezFlag) {
		this.dataFreezFlag = dataFreezFlag;
	}

	public String getForm7Narration() {
		return form7Narration;
	}

	public void setForm7Narration(String form7Narration) {
		this.form7Narration = form7Narration;
	}

	public String getNoofMonths() {
		return noofMonths;
	}

	public void setNoofMonths(String noofMonths) {
		this.noofMonths = noofMonths;
	}

	public String getPcHeldAmt() {
		return pcHeldAmt;
	}

	public void setPcHeldAmt(String pcHeldAmt) {
		this.pcHeldAmt = pcHeldAmt;
	}

	public void setAaiLoan(String aaiLoan) {
		this.aaiLoan = aaiLoan;
	}

	public String getEmployeeLoan() {
		return employeeLoan;
	}

	public void setEmployeeLoan(String employeeLoan) {
		this.employeeLoan = employeeLoan;
	}

	public String getAdvAmount() {
		return advAmount;
	}

	public void setAdvAmount(String advAmount) {
		this.advAmount = advAmount;
	}

	public String getDbPensionCtr() {
		return dbPensionCtr;
	}

	public void setDbPensionCtr(String dbPensionCtr) {
		this.dbPensionCtr = dbPensionCtr;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTransCpfaccno() {
		return transCpfaccno;
	}

	public void setTransCpfaccno(String transCpfaccno) {
		this.transCpfaccno = transCpfaccno;
	}

	public String getEmpAdvRec() {
		return empAdvRec;
	}

	public void setEmpAdvRec(String empAdvRec) {
		this.empAdvRec = empAdvRec;
	}

	public String getEmpInrstRec() {
		return empInrstRec;
	}

	public void setEmpInrstRec(String empInrstRec) {
		this.empInrstRec = empInrstRec;
	}

	public String getEmpVPF() {
		return empVPF;
	}

	public void setEmpVPF(String empVPF) {
		this.empVPF = empVPF;
	}

	public String getGenMonthYear() {
		return genMonthYear;
	}

	public void setGenMonthYear(String genMonthYear) {
		this.genMonthYear = genMonthYear;
	}

	public String getEmoluments() {
		return emoluments;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}

	public String getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}

	public String getPensionContr() {
		return pensionContr;
	}

	public void setPensionContr(String pensionContr) {
		this.pensionContr = pensionContr;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAaiPFCont() {
		return aaiPFCont;
	}

	public void setAaiPFCont(String aaiPFCont) {
		this.aaiPFCont = aaiPFCont;
	}

	public String getPfID() {
		return pfID;
	}

	public void setPfID(String pfID) {
		this.pfID = pfID;
	}

	public String getPccalApplied() {
		return pccalApplied;
	}

	public void setPccalApplied(String pccalApplied) {
		this.pccalApplied = pccalApplied;
	}

}
