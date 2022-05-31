package com.aims.info.payrollprocess;

import java.util.ArrayList;

public class SearchMonthlyPayroll {

	String searchpayrollmonthid = "";
	String searchpayrollmonthnm = "";
	String searchpayrollyear = "";
	String searchpayrollfromdt = "";
	String searchpayrolltodt = "";
	String searchpayrollstatus = "";
	String deletepayrollmonth = "";
	String searchprocessstatus = "";
	
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
	public String getDeletepayrollmonth() {
		return deletepayrollmonth;
	}
	public void setDeletepayrollmonth(String deletepayrollmonth) {
		this.deletepayrollmonth = deletepayrollmonth;
	}
	public String getSearchpayrollfromdt() {
		return searchpayrollfromdt;
	}
	public void setSearchpayrollfromdt(String searchpayrollfromdt) {
		this.searchpayrollfromdt = searchpayrollfromdt;
	}
	public String getSearchpayrollmonthid() {
		return searchpayrollmonthid;
	}
	public void setSearchpayrollmonthid(String searchpayrollmonthid) {
		this.searchpayrollmonthid = searchpayrollmonthid;
	}
	public String getSearchpayrollmonthnm() {
		return searchpayrollmonthnm;
	}
	public void setSearchpayrollmonthnm(String searchpayrollmonthnm) {
		this.searchpayrollmonthnm = searchpayrollmonthnm;
	}
	public String getSearchpayrollstatus() {
		return searchpayrollstatus;
	}
	public void setSearchpayrollstatus(String searchpayrollstatus) {
		this.searchpayrollstatus = searchpayrollstatus;
	}
	public String getSearchpayrolltodt() {
		return searchpayrolltodt;
	}
	public void setSearchpayrolltodt(String searchpayrolltodt) {
		this.searchpayrolltodt = searchpayrolltodt;
	}
	public String getSearchpayrollyear() {
		return searchpayrollyear;
	}
	public void setSearchpayrollyear(String searchpayrollyear) {
		this.searchpayrollyear = searchpayrollyear;
	}
	public String getSearchprocessstatus() {
		return searchprocessstatus;
	}
	public void setSearchprocessstatus(String searchprocessstatus) {
		this.searchprocessstatus = searchprocessstatus;
	}
	
	
}
