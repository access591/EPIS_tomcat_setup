package com.aims.info.accountsetup;

import java.io.Serializable;

public class BaseAccountInfo implements Serializable{
	
	int baseacccd = 0;
	String baseaccnm = "";
	String baseaccdesc="";
	String baseacctype = "";
	String status = "";
	
	public int getBaseacccd() {
		return baseacccd;
	}
	public void setBaseacccd(int baseacccd) {
		this.baseacccd = baseacccd;
	}
	public String getBaseaccdesc() {
		return baseaccdesc;
	}
	public void setBaseaccdesc(String baseaccdesc) {
		this.baseaccdesc = baseaccdesc;
	}
	public String getBaseaccnm() {
		return baseaccnm;
	}
	public void setBaseaccnm(String baseaccnm) {
		this.baseaccnm = baseaccnm;
	}
	public String getBaseacctype() {
		return baseacctype;
	}
	public void setBaseacctype(String baseacctype) {
		this.baseacctype = baseacctype;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

	
}
