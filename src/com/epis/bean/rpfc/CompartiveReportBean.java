/**
 * File       : CompartiveReportBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class CompartiveReportBean implements Serializable{
ArrayList commonList=new ArrayList();
ArrayList form3List=new ArrayList();
ArrayList aaiList=new ArrayList();
ArrayList commonAaiList=new ArrayList();
ArrayList commonPensionList=new ArrayList();
String flag="";

public ArrayList getCommonAaiList() {
	return commonAaiList;
}

public void setCommonAaiList(ArrayList commonAaiList) {
	this.commonAaiList = commonAaiList;
}
public ArrayList getcommonPensionList() {
	return commonPensionList;
}
public void setcommonPensionList(ArrayList commonPensionList) {
	this.commonPensionList = commonPensionList;
}
public ArrayList getAaiList() {
	return aaiList;
}
public void setAaiList(ArrayList aaiList) {
	this.aaiList = aaiList;
}
public ArrayList getCommonList() {
	return commonList;
}
public void setCommonList(ArrayList commonList) {
	this.commonList = commonList;
}
public ArrayList getForm3List() {
	return form3List;
}
public void setForm3List(ArrayList form3List) {
	this.form3List = form3List;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}

}
