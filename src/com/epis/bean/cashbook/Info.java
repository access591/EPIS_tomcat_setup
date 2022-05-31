package com.epis.bean.cashbook;

import java.io.Serializable;

public class Info implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 970572621124537707L;
	private String enteredBy; 
	private String enteredDate;
	private String editedBy;
	private String editedDate;
	private String unitCode;
	
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	public String getEditedDate() {
		return editedDate;
	}
	public void setEditedDate(String editedDate) {
		this.editedDate = editedDate;
	}
	public String getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	public String getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(String enteredDate) {
		this.enteredDate = enteredDate;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
