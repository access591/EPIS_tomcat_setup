/**
 * File       : CompartiveReportAaiDataBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;

import java.io.Serializable;

public class CompartiveReportAaiDataBean implements Serializable{
	String aaiCPFaccno="",aaiEmpName,aai3Designation="", aaiAirportCode="",aaiEmpNo = "";

	public String getAai3Designation() {
		return aai3Designation;
	}

	public void setAai3Designation(String aai3Designation) {
		this.aai3Designation = aai3Designation;
	}

	public String getAaiCPFaccno() {
		return aaiCPFaccno;
	}

	public void setAaiCPFaccno(String aaiCPFaccno) {
		this.aaiCPFaccno = aaiCPFaccno;
	}

	public String getAaiEmpName() {
		return aaiEmpName;
	}

	public void setAaiEmpName(String aaiEmpName) {
		this.aaiEmpName = aaiEmpName;
	}

	public String getAaiAirportCode() {
		return aaiAirportCode;
	}

	public void setAaiAirportCode(String aaiAirportCode) {
		this.aaiAirportCode = aaiAirportCode;
	}

	public String getAaiEmpNo() {
		return aaiEmpNo;
	}

	public void setAaiEmpNo(String aaiEmpNo) {
		this.aaiEmpNo = aaiEmpNo;
	}
}
