package com.epis.bean.cashbook;

public class IncomeAndExpenditureBean {

	private String particular;
	private String type;
	private String year;
	private String[] amount;
	private String schType;
	
	public String[] getAmount() {
		return amount;
	}
	public void setAmount(String[] amount) {
		this.amount = amount;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSchType() {
		return schType;
	}
	public void setSchType(String schType) {
		this.schType = schType;
	}	
}
