package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class JournalVoucherDetails extends RequestBean {
	
	public JournalVoucherDetails(HttpServletRequest request){
		super(request);
	}
	
	public JournalVoucherDetails() {

	}
	
	private String accountCode;
	private String description;
	private String debit;
	private String credit;
	private String append;	
	private String particular;
	
	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public String getAppend() {
		append = accountCode+"|"+description+"|"+("0".equals(debit)?credit:debit);
		return append ;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String toString(){
		return accountCode+"|"+description+"|"+("0".equals(debit)?credit:debit) ;
	}
	
}
