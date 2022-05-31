package com.epis.bean.rpfc;

import java.io.Serializable;

public class NomineeBean implements Serializable{
	String familyDetail="",NomineeDetail="";
	String cpfaccno ="",srno="",nomineeName  ="",nomineeDob="",nomineeAddress ="";
	String nomineeRelation="",nameOfGuardian="",totalShare="",gaurdianAddress="",region="",empflag ="";
	String familyMemberName="",familyDateOfBirth="",familyRelation="",nomineeAccNo="",nomineeReturnFlag="";
	public String getFamilyDateOfBirth() {
		return familyDateOfBirth;
	}

	public void setFamilyDateOfBirth(String familyDateOfBirth) {
		this.familyDateOfBirth = familyDateOfBirth;
	}

	public String getFamilyMemberName() {
		return familyMemberName;
	}

	public void setFamilyMemberName(String familyMemberName) {
		this.familyMemberName = familyMemberName;
	}

	public String getFamilyRelation() {
		return familyRelation;
	}

	public void setFamilyRelation(String familyRelation) {
		this.familyRelation = familyRelation;
	}

	public String getCpfaccno() {
		return cpfaccno;
	}

	public void setCpfaccno(String cpfaccno) {
		this.cpfaccno = cpfaccno;
	}

	public String getEmpflag() {
		return empflag;
	}

	public void setEmpflag(String empflag) {
		this.empflag = empflag;
	}

	public String getGaurdianAddress() {
		return gaurdianAddress;
	}

	public void setGaurdianAddress(String gaurdianAddress) {
		this.gaurdianAddress = gaurdianAddress;
	}

	public String getNameOfGuardian() {
		return nameOfGuardian;
	}

	public void setNameOfGuardian(String nameOfGuardian) {
		this.nameOfGuardian = nameOfGuardian;
	}

	public String getNomineeAddress() {
		return nomineeAddress;
	}

	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}

	public String getNomineeDob() {
		return nomineeDob;
	}

	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeRelation() {
		return nomineeRelation;
	}

	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSrno() {
		return srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getTotalShare() {
		return totalShare;
	}

	public void setTotalShare(String totalShare) {
		this.totalShare = totalShare;
	}

	public String getFamilyDetail() {
		return familyDetail;
	}

	public void setFamilyDetail(String familyDetail) {
		this.familyDetail = familyDetail;
	}

	public String getNomineeDetail() {
		return NomineeDetail;
	}

	public void setNomineeDetail(String nomineeDetail) {
		NomineeDetail = nomineeDetail;
	}

	public String getNomineeAccNo() {
		return nomineeAccNo;
	}

	public void setNomineeAccNo(String nomineeAccNo) {
		this.nomineeAccNo = nomineeAccNo;
	}

	public String getNomineeReturnFlag() {
		return nomineeReturnFlag;
	}

	public void setNomineeReturnFlag(String nomineeReturnFlag) {
		this.nomineeReturnFlag = nomineeReturnFlag;
	}

}
