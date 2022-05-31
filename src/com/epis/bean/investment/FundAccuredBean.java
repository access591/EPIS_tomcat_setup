package com.epis.bean.investment;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class FundAccuredBean extends RequestBean{

	private String finYear;
	private String amount;
	private String trustType;
	private String asOnDate;
	
	public String getAsOnDate() {
		return asOnDate;
	}

	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public FundAccuredBean(HttpServletRequest request){
		super(request);
	}
	
	public FundAccuredBean(){
		
	}

	public FundAccuredBean(String finYear, String amount) {
		this.finYear = finYear;
		this.amount = amount;
	}
	
	public FundAccuredBean(String finYear, String amount,String trustType) {
		this(finYear, amount);
		this.trustType = trustType;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	
}
