/**
  * File       : MenuMasterInfo.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.bean.rpfc;

public class MenuMasterInfo {
	private Integer childMenuCode;
	private String menuName;
	private Integer parentMenuCode;
	private String menuDisplayName;
	private Integer menuOrder;
	private String menuUrl;
	private String isOperational;
	private String isPopup;

	
	
	public String getIsPopup() {
		return isPopup;
	}
	public void setIsPopup(String isPopup) {
		this.isPopup = isPopup;
	}
	public String getIsOperational() {
		return isOperational;
	}
	public void setIsOperational(String isOperational) {
		this.isOperational = isOperational;
	}
	public String getMenuDisplayName() {
		return menuDisplayName;
	}
	public void setMenuDisplayName(String menuDisplayName) {
		this.menuDisplayName = menuDisplayName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	

	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Integer getChildMenuCode() {
		return childMenuCode;
	}
	public void setChildMenuCode(Integer childMenuCode) {
		this.childMenuCode = childMenuCode;
	}
	public Integer getParentMenuCode() {
		return parentMenuCode;
	}
	public void setParentMenuCode(Integer parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}
	
	
	
}
