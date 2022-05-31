package com.aims.info.quarter;

import java.io.Serializable;
import java.util.ArrayList;

public class QuarterMasterInfo implements Serializable{

	
	String quartertype;
	String description;
	String paymentType;
	String water;
	String cons;
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	String hrr;
	String quarterid;
	String status;
	String usercd;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCons() {
		return cons;
	}
	public void setCons(String cons) {
		this.cons = cons;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHrr() {
		return hrr;
	}
	public void setHrr(String  hrr) {
		this.hrr = hrr;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getQuarterid() {
		return quarterid;
	}
	public void setQuarterid(String quarterid) {
		this.quarterid = quarterid;
	}
	public String getQuartertype() {
		return quartertype;
	}
	public void setQuartertype(String quartertype) {
		this.quartertype = quartertype;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	
	
}
