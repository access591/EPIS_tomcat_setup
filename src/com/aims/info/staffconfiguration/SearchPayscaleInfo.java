
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



public class SearchPayscaleInfo implements Serializable
{

	  private String searchcadreCd;
	  private String searchgroupCd;
	  private String searchcadreName;
	  private String searchgroupName;
	  private String searchpayScale;
	  private String deletePayScaleCd;
	  private String searchpayCd;
	
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
	public String getSearchcadreCd() {
		return searchcadreCd;
	}
	public void setSearchcadreCd(String searchcadreCd) {
		this.searchcadreCd = searchcadreCd;
	}
	public String getSearchgroupCd() {
		return searchgroupCd;
	}
	public void setSearchgroupCd(String searchgroupCd) {
		this.searchgroupCd = searchgroupCd;
	}
	public String getSearchpayScale() {
		return searchpayScale;
	}
	public void setSearchpayScale(String searchpayScale) {
		this.searchpayScale = searchpayScale;
	}
	
	public String getDeletePayScaleCd() {
		return deletePayScaleCd;
	}
	public void setDeletePayScaleCd(String deletePayScaleCd) {
		this.deletePayScaleCd = deletePayScaleCd;
	}
	public String getSearchpayCd() {
		return searchpayCd;
	}
	public void setSearchpayCd(String searchpayCd) {
		this.searchpayCd = searchpayCd;
	}
	public String getSearchcadreName() {
		return searchcadreName;
	}
	public void setSearchcadreName(String searchcadreName) {
		this.searchcadreName = searchcadreName;
	}
	public String getSearchgroupName() {
		return searchgroupName;
	}
	public void setSearchgroupName(String searchgroupName) {
		this.searchgroupName = searchgroupName;
	}
	
}

