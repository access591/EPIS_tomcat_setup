package com.aims.info.payrollprocess;

import java.io.Serializable;

public class PayInputParamInfo implements Serializable{
String employeeno="",employeeName="",pensionNo="",stncode="";

public String getEmployeeName() {
	return employeeName;
}

public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}

public String getEmployeeno() {
	return employeeno;
}

public void setEmployeeno(String employeeno) {
	this.employeeno = employeeno;
}

public String getPensionNo() {
	return pensionNo;
}

public void setPensionNo(String pensionNo) {
	this.pensionNo = pensionNo;
}

public String getStncode() {
	return stncode;
}

public void setStncode(String stncode) {
	this.stncode = stncode;
}

}
