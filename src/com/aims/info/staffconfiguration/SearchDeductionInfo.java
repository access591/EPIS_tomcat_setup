
/**
  * File       : SearchDeductionInfo.java
  * Date       : 09/06/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.util.ArrayList;



public class SearchDeductionInfo implements Serializable
 {

	  private String searchdeductionCd;
	  private String searchdeductionName;
	  private String searchdeductionDesc;
	  private int[] deleteDeductionCd;
	
	  private String sortfield="";
	  private String sorttype="";
	  private String totolRecords="0";
	  private int pageNo = 1;
	  private String navigation;
	  private boolean hasNext = false;
	  private boolean hasPrevious = false;
	  private ArrayList searchList = new ArrayList();
	  private String heading;
	  private boolean last;
	  private boolean first;
	  private String searchtype;
	  private String updateStatus;
	  private Integer id;
	  
	  
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
	public String getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	public int[] getDeleteDeductionCd() {
		return deleteDeductionCd;
	}
	public void setDeleteDeductionCd(int[] deleteDeductionCd) {
		this.deleteDeductionCd = deleteDeductionCd;
	}
	public String getSearchdeductionCd() {
		return searchdeductionCd;
	}
	public void setSearchdeductionCd(String searchdeductionCd) {
		this.searchdeductionCd = searchdeductionCd;
	}
	public String getSearchdeductionDesc() {
		return searchdeductionDesc;
	}
	public void setSearchdeductionDesc(String searchdeductionDesc) {
		this.searchdeductionDesc = searchdeductionDesc;
	}
	public String getSearchdeductionName() {
		return searchdeductionName;
	}
	public void setSearchdeductionName(String searchdeductionName) {
		this.searchdeductionName = searchdeductionName;
	}
	  

	
}

