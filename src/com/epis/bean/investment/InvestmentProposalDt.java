package com.epis.bean.investment;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class InvestmentProposalDt extends RequestBean {
	private String securityName;
	
	public InvestmentProposalDt(HttpServletRequest request)
	{
		super(request);
	}
	public InvestmentProposalDt()
	{
		
	}
	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	

}
