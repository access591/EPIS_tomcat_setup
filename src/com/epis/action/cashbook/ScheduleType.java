/* Copyright © 2009 Navayuga Infotech, All Rights Reserved.
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

import com.epis.bean.cashbook.ScheduleTypeInfo;
import com.epis.common.exception.EPISException;
import com.epis.services.cashbook.AccountingCodeService;
import com.epis.services.cashbook.AccountingCodeTypeService;
import com.epis.services.cashbook.ScheduleTypeService;


/**
* Class ScheduleType Class performs a role of an adapter between the contents
* of an incoming HTTP request and the corresponding business logic that should
* be executed to process this request corresponding to Schedule Type .
* 
* @version 1.00 29 Sep 2010
* @author Jaya Sree
*/
public class ScheduleType extends DispatchAction {

	/**
	 * Method search To display the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to ScheduleTypeSearch.jsp
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//  To Display the errors that occurs during deletion/Saving/Updated
		ActionErrors errors = (ActionErrors) request.getSession().getAttribute(
				"org.apache.struts.action.ERROR");
		try {
			if (errors == null)
				errors = new ActionErrors();

			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			ScheduleTypeInfo info = new ScheduleTypeInfo();

			/*
			 * Copy property values from the Form bean to the AccountingCodeInfo
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			/*
			 * The Results are placed in the request to display the records in
			 * the Search Result Page.
			 */
			request.setAttribute("records", ScheduleTypeService.getInstance()
					.search(info));
			
			
		} catch (IllegalAccessException e) {
			errors.add("schType", new ActionMessage("errors", e
					.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("schType", new ActionMessage("errors", e
					.getMessage()));
		} catch (EPISException e) {
			errors.add("schType", new ActionMessage("errors", e
					.getMessage()));
		} finally {
			saveErrors(request, errors);
		}

		return mapping.getInputForward();
	}
	
	/**
	 * Method fwdtoNew To Forward to new Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to ScheduleType.jsp
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionErrors errors = new ActionErrors();
		try {

			// The Accounting Codes that are to be displayed in the Select.
			request.setAttribute("accHeads", AccountingCodeService.getInstance().getAccountingHeads());
			
			// The Accounting Code Types that are to be displayed in the Select
			// Box.
			request.setAttribute("typesList", ScheduleTypeService
					.getInstance().getAccountingCodeTypes());
			
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			BeanUtils.copyProperty(dyna,"screenType","NEW");
			
		} catch (Exception e) {
			errors.add("new", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}

		return mapping.findForward("new");
	}
	
	/**
	 * Method add To Edit/Update a Record.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws EPISException 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {

		/*
		 * If the Record does not exists then the record is Saved/Updated and
		 * forwarded to method Seach
		 */
		ActionForward forward = mapping.findForward("search");

		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			ScheduleTypeInfo info = new ScheduleTypeInfo(request);

			/*
			 * Copy property values from the AccountingCodeInfo bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			// The Accounting Codes that are to be displayed in the Select.
			request.setAttribute("accHeads", AccountingCodeService.getInstance().getAccountingHeads());
			
			// The Accounting Code Types that are to be displayed in the Select
			// Box.
			request.setAttribute("typesList", ScheduleTypeService
					.getInstance().getAccountingCodeTypes());
			/*
			 * If the screenType is NEW then the Record is to be Saved else
			 * Record is to be Updated.
			 */
			if ("NEW".equals(dyna.get("screenType"))) {
				// Checking whether the Record already exists or not.
				if (!ScheduleTypeService.getInstance().exists(info)) {

					// To Add the New Record.
					ScheduleTypeService.getInstance().add(info);

					// To Display the Record has been saved Successfully.
					errors.add("accountHead", new ActionMessage(
							"record.saved.sucess"));
				} else {
					errors.add("", new ActionMessage("record.exists"));
					/*
					 * As the record already exists it is forwarded to same
					 * Input Screen Displaying the error.
					 */
					forward = mapping.getInputForward();
				}
			} else {
				// To Update the Record.
				ScheduleTypeService.getInstance().update(info);

				// To Display the Record has been saved Successfully.
				errors.add("schType", new ActionMessage(
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
	 * Method fwdtoEdit To Forward to Edit Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to ScheduleType.jsp
	 */
	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ScheduleTypeInfo info = new ScheduleTypeInfo();
			BeanUtils.copyProperties(info,dyna);

			// The Accounting Codes that are to be displayed in the Select.
			request.setAttribute("accHeads", AccountingCodeService.getInstance().getAccountingHeads());
			
			// The Accounting Code Types that are to be displayed in the Select
			// Box.
			request.setAttribute("typesList", ScheduleTypeService
					.getInstance().getAccountingCodeTypes());
			
			info = ScheduleTypeService.getInstance().getSchedule(info);
			
			BeanUtils.copyProperties(dyna,info);
			BeanUtils.copyProperty(dyna,"screenType","EDIT");
			
		} catch (Exception e) {
			errors.add("new", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		return mapping.findForward("new");
	}
	
	/**
	 * Method fwdtoEdit To Forward to Edit Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to ScheduleType.jsp
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionErrors errors = new ActionErrors();
		try {

			// The Accounting Codes that are to be displayed in the Select.
			request.setAttribute("accHeads", AccountingCodeService.getInstance().getAccountingHeads());
			
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ScheduleTypeInfo info = new ScheduleTypeInfo();
			BeanUtils.copyProperties(info,dyna);
			
			ScheduleTypeService.getInstance().delete(info);			
			
		} catch (Exception e) {
			errors.add("schType", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}

		return mapping.findForward("search");
	}
}


