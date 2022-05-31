package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class TrustPCRegionBean implements Serializable{
String regionName="";
ArrayList regionList=new ArrayList();
public ArrayList getRegionList() {
	return regionList;
}
public void setRegionList(ArrayList regionList) {
	this.regionList = regionList;
}
public String getRegionName() {
	return regionName;
}
public void setRegionName(String regionName) {
	this.regionName = regionName;
}
}
