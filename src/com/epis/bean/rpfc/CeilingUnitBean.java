/**
 * File       : CeilingUnitBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import com.epis.utilities.Log;
public class CeilingUnitBean {

	Log log = new Log(CeilingUnitBean.class);

	String fromwedate = "", towedate = "", unitname = "", unitoption = "",
			region = "", unitcode = "", hiddenunitcode = "", hiddenregion = "",
			weDate = "";

	int clientcode = 0, interestcode = 0;

	double rate = 0.0, interestrate = 0.0;

	public void setCeilingCode(int clientcode) {
		this.clientcode = clientcode;

	}

	public int getCeilingCode() {
		return clientcode;
	}

	public void setInterestCode(int interestcode) {
		this.interestcode = interestcode;

	}

	public int getInterestCode() {
		return interestcode;
	}

	public void setRate(double rate) {
		this.rate = rate;

	}

	public double getRate() {
		return rate;
	}

	public void setInterestRate(double interestrate) {
		this.interestrate = interestrate;

	}

	public double getInterestRate() {
		return interestrate;
	}

	public void setFromWeDate(String fromwedate) {
		this.fromwedate = fromwedate;
	}

	public String getFromWeDate() {
		return fromwedate;
	}

	public void setToWeDate(String towedate) {
		this.towedate = towedate;
	}

	public String getToWeDate() {
		return towedate;
	}

	public void setUnitName(String unitname) {
		this.unitname = unitname;
	}

	public String getUnitName() {
		return unitname;
	}

	public void setUnitCode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getUnitCode() {
		return unitcode;
	}

	public void setHiddenUnitCode(String hiddenunitcode) {
		this.hiddenunitcode = hiddenunitcode;
	}

	public String getHiddenUnitCode() {
		return hiddenunitcode;
	}

	public void setUnitOption(String unitoption) {
		this.unitoption = unitoption;
	}

	public String getUnitOption() {
		return unitoption;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setHiddenRegion(String hiddenregion) {
		this.hiddenregion = hiddenregion;
	}

	public String getHiddenRegion() {
		return hiddenregion;
	}

	public String getWeDate() {
		return weDate;
	}

	public void setWeDate(String weDate) {
		this.weDate = weDate;
	}

}
