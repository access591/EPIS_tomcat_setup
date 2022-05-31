//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.epis.action.cashbookDummy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.cashbookDummy.AccountingCodeTypeInfo;
import com.epis.service.cashbookDummy.AccountingCodeTypeService;
import com.epis.validate.cashbook.AccountingCodeTypeValidate;

/**
 * MyEclipse Struts Creation date: 12-16-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class AccountingCodeType extends DispatchAction {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method addAccountTypeRecord
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addAccountTypeRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		AccountingCodeTypeInfo info = new AccountingCodeTypeInfo();
		info.setAccountCodeType(dyna.getString("accountCodeType"));
		info.setDescription(dyna.getString("description"));
		AccountingCodeTypeValidate validate = new AccountingCodeTypeValidate();
		ActionErrors errors = validate.validateAdd(info);
		saveErrors(request, errors);
		ActionForward forward = null;
		if (!errors.isEmpty()) {
			forward = mapping.getInputForward();
		} else {
			AccountingCodeTypeService service = AccountingCodeTypeService
					.getInstance();
			try {
				service.addAccountTypeRecord(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
			StringBuffer newPath = new StringBuffer(mapping.findForward(
					"search").getPath());
			newPath.append("&from=IS");
			forward = (new ActionForward(newPath.toString(), true));
		}
		return forward;
	}

	/**
	 * Method searchRecords
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		AccountingCodeTypeInfo info = new AccountingCodeTypeInfo();
		info.setAccountCodeType(dyna.getString("accountCodeType"));
		info.setDescription(dyna.getString("description"));
		AccountingCodeTypeService service = AccountingCodeTypeService
				.getInstance();
		ActionErrors errors = new ActionErrors();
		if ("IS".equals(request.getParameter("from"))) {
			errors.add("type", new ActionMessage("common.saved"));
		}
		saveErrors(request, errors);
		try {
			List records = service.searchRecords(info);
			request.setAttribute("records", records);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("search");
	}

	/**
	 * Method editRecord
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		AccountingCodeTypeInfo info = new AccountingCodeTypeInfo();
		info.setCode(dyna.getString("code"));
		AccountingCodeTypeService service = AccountingCodeTypeService
				.getInstance();
		try {
			info = service.editRecord(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dyna.set("code", info.getCode());
		dyna.set("accountCodeType", info.getAccountCodeType());
		dyna.set("description", info.getDescription());
		return mapping.findForward("edit");
	}

	/**
	 * Method updateRecord
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward updateRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		AccountingCodeTypeInfo info = new AccountingCodeTypeInfo();
		info.setCode(dyna.getString("code"));
		info.setAccountCodeType(dyna.getString("accountCodeType"));
		info.setDescription(dyna.getString("description"));
		AccountingCodeTypeService service = AccountingCodeTypeService
				.getInstance();
		try {
			service.updateRecord(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("search");
	}
	
	/**
	 * Method deleteRecord
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		String[] types = dyna.getStrings("deleteRecord");		
		AccountingCodeTypeService service = AccountingCodeTypeService
				.getInstance();
		try {
			service.deleteRecord(types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("search");
	}
}
