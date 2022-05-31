package com.epis.bean;



public class MailContent  {
private final String salutation="Dear Sir/Madam,";
private String bodyContent1="",bodyContent2="",bodyContent3="";
private String signInfo="";
public String getBodyContent1() {
	return bodyContent1;
}
public void setBodyContent1(String bodyContent1) {
	this.bodyContent1 = bodyContent1;
}
public String getBodyContent2() {
	return bodyContent2;
}
public void setBodyContent2(String bodyContent2) {
	this.bodyContent2 = bodyContent2;
}
public String getBodyContent3() {
	return bodyContent3;
}
public void setBodyContent3(String bodyContent3) {
	this.bodyContent3 = bodyContent3;
}
public String getSalutation() {
	return salutation;
}

public String getSignInfo() {
	return signInfo;
}
public void setSignInfo(String signInfo) {
	this.signInfo = signInfo;
}

}
