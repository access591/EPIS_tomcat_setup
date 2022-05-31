//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;



/** 
 * MyEclipse Struts
 * Creation date: 28-08-07
 * 
 * XDoclet definition:
 * @struts.form name=""
 */
public class SearchEmpInfo implements Serializable {

	  //private String fromDate ;
	  //private String toDate ;
	  private Integer searchempNo;
	  private String searchemplNo;
	  private String searchempName;
	 private String searchFirstName;
	 private String searchLastName;
	 private Integer searchStaffCategory;
	 private Integer searchDescipline;
	 private String status;
	 
	
	  private String sortfield="";
	  private String sorttype="";
	  private String totolRecords;
	  private int pageNo = 1;
	  private String navigation;
	  private boolean hasNext = false;
	  private boolean hasPrevious = false;
	  private ArrayList searchList = new ArrayList();
	  private String heading;
	  private boolean last;
	  private boolean first;
	  private String searchtype;
	  private Integer id;
	  
	
	  
	
	/**
	 * @return Returns the searchemplNo.
	 */
	public String getSearchemplNo() {
		return searchemplNo;
	}
	/**
	 * @param searchemplNo The searchemplNo to set.
	 */
	public void setSearchemplNo(String searchemplNo) {
		this.searchemplNo = searchemplNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSearchtype() {
		return searchtype;
	}
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}
	
	
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
	public String getSearchempName() {
		return searchempName;
	}
	public void setSearchempName(String searchempName) {
		this.searchempName = searchempName;
	}
	public Integer getSearchempNo() {
		return searchempNo;
	}
	public void setSearchempNo(Integer searchempNo) {
		this.searchempNo = searchempNo;
	}
	
	public Integer getSearchDescipline() {
		return searchDescipline;
	}
	public void setSearchDescipline(Integer searchDescipline) {
		this.searchDescipline = searchDescipline;
	}
	public String getSearchFirstName() {
		return searchFirstName;
	}
	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}
	public String getSearchLastName() {
		return searchLastName;
	}
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}
	public Integer getSearchStaffCategory() {
		return searchStaffCategory;
	}
	public void setSearchStaffCategory(Integer searchStaffCategory) {
		this.searchStaffCategory = searchStaffCategory;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
	

}

