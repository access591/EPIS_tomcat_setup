package com.aims.info.accountsetup;

import java.io.Serializable;

public class AccountTypeInfo implements Serializable{

	String acctypecd= "";
	String acctypenm = "";
	String acctypedesc="";
	String groupacccd = "";
	String deleteflag = "";
	String status="";
	
		
	public String getAcctypecd() {
		return acctypecd;
	}
	public void setAcctypecd(String acctypecd) {
		this.acctypecd = acctypecd;
	}
	public String getAcctypedesc() {
		return acctypedesc;
	}
	public void setAcctypedesc(String acctypedescs) {
		this.acctypedesc = acctypedescs;
	}
	public String getAcctypenm() {
		return acctypenm;
	}
	public void setAcctypenm(String acctypenm) {
		this.acctypenm = acctypenm;
	}
	
	public String getGroupacccd() {
		return groupacccd;
	}
	public void setGroupacccd(String groupacccd) {
		this.groupacccd = groupacccd;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public String getStatus() {
		return status;
	}	
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
