 /**
  * File       : EditStaffTypeInfo.java
  * Date       : 08/08/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;


import java.util.List;
import java.io.*;



public class EditStaffTypeInfo implements Serializable {
	private Integer staffTypeCd;
	private String staffTypeName;
	private String staffTypeDesc;
	private List editStaffList;
	private String status;

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

	public Integer getStaffTypeCd() {
		return staffTypeCd;
	}

	public void setStaffTypeCd(Integer staffTypeCd) {
		this.staffTypeCd = staffTypeCd;
	}

	public List getEditStaffList() {
		return editStaffList;
	}

	public void setEditStaffList(List editStaffList) {
		this.editStaffList = editStaffList;
	}

	public String getStaffTypeDesc() {
		return staffTypeDesc;
	}

	public void setStaffTypeDesc(String staffTypeDesc) {
		this.staffTypeDesc = staffTypeDesc;
	}

	public String getStaffTypeName() {
		return staffTypeName;
	}

	public void setStaffTypeName(String staffTypeName) {
		this.staffTypeName = staffTypeName;
	}

}

