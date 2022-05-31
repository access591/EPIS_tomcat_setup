package com.epis.validate.cashbook;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.epis.bean.cashbookDummy.AccountingCodeTypeInfo;
import com.epis.service.cashbookDummy.AccountingCodeTypeService;

public class AccountingCodeTypeValidate {
	
	public ActionErrors validateAdd(AccountingCodeTypeInfo info) {
		ActionErrors errors = new ActionErrors();
		if(info.getAccountCodeType() == null || "".equals(info.getAccountCodeType())) {
			errors.add("type",new ActionMessage("accCodeType.errors.required"));
		}
		AccountingCodeTypeService service = AccountingCodeTypeService.getInstance();
		try {
			if(service.exists(info)){
				errors.add("type",new ActionMessage("accCodeType.errors.exists",info.getAccountCodeType()));
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		return errors;
	}
}
