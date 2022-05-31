package com.aims.info.accountsetup;

import java.io.Serializable;
import java.util.ArrayList;

public class BankMasterInfo implements Serializable{

	
	String bankid;
	String searchbankId;
	String bankname;
	String branchname;
	String micrno;
	String ifscno;
	String ecrno;
	String rtgsno;
	String userCd;
	String address;
	String city;
	String state;
	String country;
	String zipcode;
	String status;
	ArrayList bankList;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getSearchbankId() {
		return searchbankId;
	}
	public void setSearchbankId(String searchbankId) {
		this.searchbankId = searchbankId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList getBankList() {
		return bankList;
	}
	public void setBankList(ArrayList bankList) {
		this.bankList = bankList;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getEcrno() {
		return ecrno;
	}
	public void setEcrno(String ecrno) {
		this.ecrno = ecrno;
	}
	public String getIfscno() {
		return ifscno;
	}
	public void setIfscno(String ifscno) {
		this.ifscno = ifscno;
	}
	public String getMicrno() {
		return micrno;
	}
	public void setMicrno(String micrno) {
		this.micrno = micrno;
	}
	public String getRtgsno() {
		return rtgsno;
	}
	public void setRtgsno(String rtgsno) {
		this.rtgsno = rtgsno;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
