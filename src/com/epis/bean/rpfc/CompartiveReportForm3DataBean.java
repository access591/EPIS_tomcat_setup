/**
 * File       : CompartiveReportForm3DataBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import java.io.Serializable;

public class CompartiveReportForm3DataBean implements Serializable{
String form3CPFaccno="",form3EmpName,form3Designation="",form3EmpNo="",form3AirportCode="";



public String getForm3CPFaccno() {
	return form3CPFaccno;
}

public void setForm3CPFaccno(String form3CPFaccno) {
	this.form3CPFaccno = form3CPFaccno;
}

public String getForm3Designation() {
	return form3Designation;
}

public void setForm3Designation(String form3Designation) {
	this.form3Designation = form3Designation;
}

public String getForm3EmpName() {
	return form3EmpName;
}

public void setForm3EmpName(String form3EmpName) {
	this.form3EmpName = form3EmpName;
}

public String getForm3AirportCode() {
	return form3AirportCode;
}

public void setForm3AirportCode(String form3AirportCode) {
	this.form3AirportCode = form3AirportCode;
}

public String getForm3EmpNo() {
	return form3EmpNo;
}

public void setForm3EmpNo(String form3EmpNo) {
	this.form3EmpNo = form3EmpNo;
}

}
