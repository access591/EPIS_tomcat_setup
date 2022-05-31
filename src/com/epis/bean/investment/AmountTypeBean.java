package com.epis.bean.investment;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class AmountTypeBean extends RequestBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String emppartycode;
	private String credit;
	private String accounthead;
	private String voucherno;
	private String rowid;
	private String amounttype;
	private String amountdate;
	private String[] achieveDetails; 
	
	
	public String[] getAchieveDetails() {
		return achieveDetails;
	}

	public void setAchieveDetails(String[] achieveDetails) {
		this.achieveDetails = achieveDetails;
	}

	public String getAmountdate() {
		return amountdate;
	}

	public void setAmountdate(String amountdate) {
		this.amountdate = amountdate;
	}

	public AmountTypeBean(HttpServletRequest request)
	{
		super(request);
	}
	
	public AmountTypeBean()
	{
		
	}

	public String getAccounthead() {
		return accounthead;
	}

	public void setAccounthead(String accounthead) {
		this.accounthead = accounthead;
	}

	public String getAmounttype() {
		return amounttype;
	}

	public void setAmounttype(String amounttype) {
		this.amounttype = amounttype;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getEmppartycode() {
		return emppartycode;
	}

	public void setEmppartycode(String emppartycode) {
		this.emppartycode = emppartycode;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

}
