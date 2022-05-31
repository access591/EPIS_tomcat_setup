package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class form3CommonBean implements Serializable{
String cpflist="";
ArrayList form3MemberList=new ArrayList();
public String getCpflist() {
	return cpflist;
}
public void setCpflist(String cpflist) {
	this.cpflist = cpflist;
}
public ArrayList getForm3MemberList() {
	return form3MemberList;
}
public void setForm3MemberList(ArrayList form3MemberList) {
	this.form3MemberList = form3MemberList;
}
}
