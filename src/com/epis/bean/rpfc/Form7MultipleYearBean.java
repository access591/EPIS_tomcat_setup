package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class Form7MultipleYearBean implements Serializable{
ArrayList eachYearList=new ArrayList();
String message="";
public ArrayList getEachYearList() {
	return eachYearList;
}
public void setEachYearList(ArrayList eachYearList) {
	this.eachYearList = eachYearList;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
