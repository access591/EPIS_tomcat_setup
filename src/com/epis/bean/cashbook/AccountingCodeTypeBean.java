package com.epis.bean.cashbook;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
public class AccountingCodeTypeBean  extends RequestBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountCodeType;
	private String description;
	private String code;
	
	public AccountingCodeTypeBean(HttpServletRequest request){
		super(request);
		}
	
	public AccountingCodeTypeBean() {

	}

	public AccountingCodeTypeBean(String code, String accountCodeType,
			String description) {
		this.code = code;
		this.accountCodeType = accountCodeType;
		this.description = description;
	}

	public String getAccountCodeType() {
		return accountCodeType;
	}

	public void setAccountCodeType(String accountCodeType) {
		this.accountCodeType = accountCodeType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return accountCodeType+" -- "+description;
	}

}
