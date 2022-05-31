/**
  * File       : SearchInfo.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchInfo implements Serializable{
	private int totalRecords=0,totalUnmappedRecords=0;
	public int getTotalUnmappedRecords() {
		return totalUnmappedRecords;
	}
	public void setTotalUnmappedRecords(int totalUnmappedRecords) {
		this.totalUnmappedRecords = totalUnmappedRecords;
	}
	private String navigation="";
	private boolean hasNext = false;
	private boolean hasPrevious = false;
	private boolean last;
	private boolean first;
	private int startIndex=1,gridlength=10,gridlength1=100;
	private int pageNo = 1;
	private ArrayList searchList = new ArrayList();
	private BottomGridNavigationInfo bottomGrid=new BottomGridNavigationInfo();
	private String navButton="";
	public String getNavButton() {
		return navButton;
	}
	public void setNavButton(String navButton) {
		this.navButton = navButton;
	}
	public BottomGridNavigationInfo getBottomGrid() {
		return bottomGrid;
	}
	public void setBottomGrid(BottomGridNavigationInfo bottomGrid) {
		this.bottomGrid = bottomGrid;
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
	public ArrayList getSearchList() {
		return searchList;
	}
	public void setSearchList(ArrayList searchList) {
		this.searchList = searchList;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getGridlength() {
		return gridlength;
	}
	public void setGridlength(int gridlength) {
		this.gridlength = gridlength;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getGridlength1() {
		return gridlength1;
	}
	public void setGridlength1(int gridlength1) {
		this.gridlength1 = gridlength1;
	}

}
