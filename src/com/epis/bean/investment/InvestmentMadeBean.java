package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class InvestmentMadeBean extends RequestBean {
	private String investmentMadeCd;
	private String trustType;
	private String securityCategory;
	private String invAmount;
	private String asOnDate;
	
	public String getAsOnDate() {
		return asOnDate;
	}
	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}
	public String getInvAmount() {
		return invAmount;
	}
	public void setInvAmount(String invAmount) {
		this.invAmount = invAmount;
	}
	public String getInvestmentMadeCd() {
		return investmentMadeCd;
	}
	public void setInvestmentMadeCd(String investmentMadeCd) {
		this.investmentMadeCd = investmentMadeCd;
	}
	public String getSecurityCategory() {
		return securityCategory;
	}
	public void setSecurityCategory(String securityCategory) {
		this.securityCategory = securityCategory;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public InvestmentMadeBean()
	{
		
	}
	public InvestmentMadeBean(HttpServletRequest req)
	{
		super(req);
	}

}
