package com.aims.info.accountsetup;

import java.util.ArrayList;


public class SearchAccountTypeInfo {
	
	
	  String searchacctypecd = "";
	  String searchacctypenm = "";
	  String searchacctypedesc = "";
	  String searchgroupaccnm = "";
	  String searchgroupacccd ="";
	  String searchacctypedelflag = "";
	  

	   String sortfield="";
	   String sorttype="";
	   String totolRecords = "0";
	   int pageNo = 1;
	   String navigation = "";
	   boolean hasNext = false;
	   boolean hasPrevious = false;
	   ArrayList searchList = new ArrayList();
	   String heading = "";
	   boolean last;
	   boolean first;
	   String searchtype = "";	 
	   Integer id;
	   
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public String getNavigation() {
		return navigation;
	}
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getSearchacctypecd() {
		return searchacctypecd;
	}
	public void setSearchacctypecd(String searchacctypecd) {
		this.searchacctypecd = searchacctypecd;
	}
	public String getSearchacctypedelflag() {
		return searchacctypedelflag;
	}
	public void setSearchacctypedelflag(String searchacctypedelflag) {
		this.searchacctypedelflag = searchacctypedelflag;
	}
	public String getSearchacctypedesc() {
		return searchacctypedesc;
	}
	public void setSearchacctypedesc(String searchacctypedesc) {
		this.searchacctypedesc = searchacctypedesc;
	}
	public String getSearchacctypenm() {
		return searchacctypenm;
	}
	public void setSearchacctypenm(String searchacctypenm) {
		this.searchacctypenm = searchacctypenm;
	}
	
	public String getSearchgroupaccnm() {
		return searchgroupaccnm;
	}
	public void setSearchgroupaccnm(String searchgroupaccnm) {
		this.searchgroupaccnm = searchgroupaccnm;
	}

	public String getSearchgroupacccd() {
		return searchgroupacccd;
	}
	public void setSearchgroupacccd(String searchgroupacccd) {
		this.searchgroupacccd = searchgroupacccd;
	}
	public ArrayList getSearchList() {
		return searchList;
	}
	public void setSearchList(ArrayList searchList) {
		this.searchList = searchList;
	}
	public String getSearchtype() {
		return searchtype;
	}
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}
	public String getSortfield() {
		return sortfield;
	}
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}
	public String getSorttype() {
		return sorttype;
	}
	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}
	public String getTotolRecords() {
		return totolRecords;
	}
	public void setTotolRecords(String totolRecords) {
		this.totolRecords = totolRecords;
	}
}
