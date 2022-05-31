/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.epis.action.cashbook;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.cashbook.AccountingCodeTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.services.cashbook.AccountingCodeTypeService;

/**
 * Class AccountingCodeTypeAction enables a user to collect related functions
 * into a single Action.An Action Class performs a role of an adapter between
 * the contents of an incoming HTTP request and the corresponding business logic
 * that should be executed to process this request corresponding to Accounting
 * Code Type.
 * 
 * @version 1.00 16 Dec 2009
 * @author Jaya Sree
 */
public class AccountingCodeTypeAction extends DispatchAction {

	/**
	 * Method search To display the Records in the Search Result
	 * Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCodeTypeSearch.jsp
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// To Display the errors that occurs during deletion/Saving/Updated
		ActionErrors errors = (ActionErrors) request.getSession().getAttribute(
				"org.apache.struts.action.ERROR");
		try {

			if (errors == null) {
				errors = new ActionErrors();
			}
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeTypeBean info = new AccountingCodeTypeBean();

			/*
			 * Copy property values from the Form bean to the AccountingCodeType
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			/*
			 * The Results are placed in the request to display the records in
			 * the Search Result Page.
			 */
			request.setAttribute("records", AccountingCodeTypeService
					.getInstance().search(info));

		} catch (IllegalAccessException e) {
			errors.add("accountCodeType", new ActionMessage("errors", e
					.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("accountCodeType", new ActionMessage("errors", e
					.getMessage()));
		} catch (EPISException e) {
			errors.add("accountCodeType", new ActionMessage("errors", e
					.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		return mapping.getInputForward();
	}

	/**
	 * Method delete  To Delete the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCodeTypeSearch.jsp
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			
			// To Delete the Records
			AccountingCodeTypeService.getInstance().delete(
					dyna.getStrings("deleteRecord"));
			
		} catch (EPISException e) {
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
		} finally {
			saveErrors(request.getSession(), errors);
		}
		
		return mapping.findForward("search");
	}

	/**
	 * Method fwdtoNew  To Forward to the new Page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCodeType.jsp
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("new");
	}

	/**
	 * Method addAccountTypeRecord  To Edit/Update a Record.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		/*
		 * If the Record does not exists then the record is Saved/Updated and
		 * forwarded to method Seach
		 */
		ActionForward forward = mapping.findForward("search");
		
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeTypeBean info = new AccountingCodeTypeBean(request);

			/*
			 * Copy property values from the AccountingCodeType bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			/*
			 * If the Code exists then the Record is to be Updated else New
			 * Record is to be Saved.
			 */
			if ("".equals(info.getCode())) {
				// Checking whether the Record already exists or not.
				if (AccountingCodeTypeService.getInstance().exists(info)) {
					errors.add("", new ActionMessage(
							"accCodeType.errors.exists", info
									.getAccountCodeType()));
					/*
					 * As the record already exists it is forwarded to same
					 * Input Screen Displaying the error.
					 */
					forward = mapping.getInputForward();
				} else {
					// To Add the New Record.
					AccountingCodeTypeService.getInstance().add(info);
					// To Display the Record has been saved Successfully.
					errors.add("accountCodeType", new ActionMessage(
							"record.saved.sucess"));
				}
			} else {
				// To Update the Record.
				AccountingCodeTypeService.getInstance().update(info);
				// To Display the Record has been saved Successfully.
				errors.add("accountCodeType", new ActionMessage(
						"record.saved.sucess"));
			}
		} catch (IllegalAccessException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} catch (InvocationTargetException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} catch (Exception e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} finally {
			saveErrors(request.getSession(), errors);
		}
		return forward;
	}

	/**
	 * Method fwdtoEdit  To Forward to the Edit Page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		
		/*
		 * If there is no problem in reteiving the data of that particular
		 * Record then forwarded to method Edit Screen.
		 */
		ActionForward forward = mapping.findForward("new");
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeTypeBean info = new AccountingCodeTypeBean();
			info.setCode(request.getParameter("code"));

			/*
			 * Copy property values from the AccountingCodeType bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(dyna, AccountingCodeTypeService
					.getInstance().edit(info));
		} catch (Exception e) {
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
			forward = mapping.getInputForward();
		} finally {
			saveErrors(request.getSession(), errors);
		}
		return forward;
	}
}
