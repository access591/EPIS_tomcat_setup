package com.epis.bean.investment;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class QuotationDetailsBean extends RequestBean {
	
	private String  securityName;
	private String  securityAddress;
	private String quotationSubmitDate;
	private String quotationSubmitTime;
	private String quotationValidDate;
	private String quotationValidTime;
	private String quotationOpenDate;
	private String quotaionOpenTime;
	
	
	public String getQuotaionOpenTime() {
		return quotaionOpenTime;
	}


	public void setQuotaionOpenTime(String quotaionOpenTime) {
		this.quotaionOpenTime = quotaionOpenTime;
	}


	public String getQuotationOpenDate() {
		return quotationOpenDate;
	}


	public void setQuotationOpenDate(String quotationOpenDate) {
		this.quotationOpenDate = quotationOpenDate;
	}


	public String getQuotationSubmitDate() {
		return quotationSubmitDate;
	}


	public void setQuotationSubmitDate(String quotationSubmitDate) {
		this.quotationSubmitDate = quotationSubmitDate;
	}


	public String getQuotationSubmitTime() {
		return quotationSubmitTime;
	}


	public void setQuotationSubmitTime(String quotationSubmitTime) {
		this.quotationSubmitTime = quotationSubmitTime;
	}


	public String getQuotationValidDate() {
		return quotationValidDate;
	}


	public void setQuotationValidDate(String quotationValidDate) {
		this.quotationValidDate = quotationValidDate;
	}


	public String getQuotationValidTime() {
		return quotationValidTime;
	}


	public void setQuotationValidTime(String quotationValidTime) {
		this.quotationValidTime = quotationValidTime;
	}


	public String getSecurityAddress() {
		return securityAddress;
	}


	public void setSecurityAddress(String securityAddress) {
		this.securityAddress = securityAddress;
	}


	public String getSecurityName() {
		return securityName;
	}


	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}


	public QuotationDetailsBean(HttpServletRequest request)
	{
		super(request);
	}

}
