package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class EmpBankMaster extends RequestBean implements Serializable{
	String bankempname  ="",bankempaddrs ="",bankname  ="",branchaddress="",empmailid="",banksavingaccno="",bankemprtgsneftcode="",bankempmicrono="";
	String chkBankInfo="N", transId="",bankpaymentmode="",city="",partyName="",partyAddress="",nomineeSerialNo="";
	
	public String getNomineeSerialNo() {
		return nomineeSerialNo;
	}

	public void setNomineeSerialNo(String nomineeSerialNo) {
		this.nomineeSerialNo = nomineeSerialNo;
	}

	public String getBankpaymentmode() {
		return bankpaymentmode;
	}

	public void setBankpaymentmode(String bankpaymentmode) {
		this.bankpaymentmode = bankpaymentmode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public EmpBankMaster(HttpServletRequest request){
		super(request);
		}
	
	public EmpBankMaster(){
	}

	public String getChkBankInfo() {
		return chkBankInfo;
	}

	public void setChkBankInfo(String chkBankInfo) {
		this.chkBankInfo = chkBankInfo;
	}

	public String getBankempaddrs() {
		return bankempaddrs;
	}

	public void setBankempaddrs(String bankempaddrs) {
		this.bankempaddrs = bankempaddrs;
	}

	public String getBankempmicrono() {
		return bankempmicrono;
	}

	public void setBankempmicrono(String bankempmicrono) {
		this.bankempmicrono = bankempmicrono;
	}

	public String getBankempname() {
		return bankempname;
	}

	public void setBankempname(String bankempname) {
		this.bankempname = bankempname;
	}

	public String getBankemprtgsneftcode() {
		return bankemprtgsneftcode;
	}

	public void setBankemprtgsneftcode(String bankemprtgsneftcode) {
		this.bankemprtgsneftcode = bankemprtgsneftcode;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBanksavingaccno() {
		return banksavingaccno;
	}

	public void setBanksavingaccno(String banksavingaccno) {
		this.banksavingaccno = banksavingaccno;
	}

	public String getBranchaddress() {
		return branchaddress;
	}

	public void setBranchaddress(String branchaddress) {
		this.branchaddress = branchaddress;
	}

	public String getEmpmailid() {
		return empmailid;
	}

	public void setEmpmailid(String empmailid) {
		this.empmailid = empmailid;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
}
