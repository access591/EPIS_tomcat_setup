/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.action.cashbook;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.epis.bean.cashbook.AccountingCodeInfo;
import com.epis.bean.investment.TrustTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.services.cashbook.AccountingCodeService;
import com.epis.services.cashbook.AccountingCodeTypeService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.UserTracking;

/**
 * Class AccountingCode Class performs a role of an adapter between the contents
 * of an incoming HTTP request and the corresponding business logic that should
 * be executed to process this request corresponding to Accounting Code .
 * 
 * @version 1.00 03 Mar 2010
 * @author Jaya Sree
 */
public class AccountingCode extends DispatchAction {

	/**
	 * Method search To display the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCodeSearch.jsp
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		// To Display the errors that occurs during deletion/Saving/Updated
		ActionErrors errors = (ActionErrors) request.getSession().getAttribute(
				"org.apache.struts.action.ERROR");
		try {
			if (errors == null)
				errors = new ActionErrors();

			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeInfo info = new AccountingCodeInfo();

			/*
			 * Copy property values from the Form bean to the AccountingCodeInfo
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			/*
			 * The Results are placed in the request to display the records in
			 * the Search Result Page.
			 */
			request.setAttribute("records", AccountingCodeService.getInstance()
					.search(info));

			// The Accounting Code Types that are to be displayed in the Select
			// Box.
			request.setAttribute("typesList", AccountingCodeTypeService
					.getInstance().getAccountingCodeTypes());
			
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
	 * Method delete To Delete the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCodeSearch.jsp
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;

			// To Delete the Records
			AccountingCodeService.getInstance().delete(
					dyna.getStrings("deleteRecord"));
			
			AccountingCodeInfo info = new AccountingCodeInfo(request);
			int len = dyna.getStrings("deleteRecord").length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append(dyna.getStrings("deleteRecord")[i]).append("|");
			}
			UserTracking.write(info.getLoginUserId(),sb.toString(), "D", "CB", sb.toString(),"Accounting Code");
			errors.add("deleteRecord", new ActionMessage("record.deleted.sucess"));
		} catch (EPISException e) {			
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
		} finally {	
			saveErrors(request.getSession(), errors);
		}

		return mapping.findForward("search");
	}

	/**
	 * Method fwdtoNew To Forward to the new Page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to AccountingCode.jsp
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = new ActionErrors();
		try {

			// The Trust Types that are to be displayed in the Select.
			request.setAttribute("trustTypes", TrustTypeService.getInstance()
					.getTrustTypes());

			// The Accounting Code Types that are to be displayed in the Select.
			request.setAttribute("typesList", AccountingCodeTypeService
					.getInstance().getAccountingCodeTypes());

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
			AccountingCodeInfo info = new AccountingCodeInfo(request);

			/*
			 * Copy property values from the AccountingCodeInfo bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			// Retreive the List of all Trust Types.
			List trustTypes = TrustTypeService.getInstance().getTrustTypes();

			/*
			 * Trust Types list size is saved in the len int variable to
			 * increase the performance.
			 */
			int len = trustTypes.size();

			/*
			 * The TrustTypes,Opening Balance Amount and Amount Types are
			 * retreived that are entered in the form.
			 */
			Map openBal = new HashMap();
			for (int i = 0; i < len; i++) {
				TrustTypeBean trust = (TrustTypeBean) trustTypes.get(i);
				AccountingCodeInfo accInfo = new AccountingCodeInfo(request);
				accInfo.setTrustType(trust.getTrustType());
				accInfo.setAmount(request.getParameter("Amount"
						+ trust.getTrustCd()));
				accInfo.setAmountType(request.getParameter("AmountType"
						+ trust.getTrustCd()));
				openBal.put(trust.getTrustType(), accInfo);
			}
			info.setOpenBalances(openBal);

			/*
			 * If the screenType is NEW then the Record is to be Saved else
			 * Record is to be Updated.
			 */
			if ("NEW".equals(dyna.get("screenType"))) {
				// Checking whether the Record already exists or not.
				if (!AccountingCodeService.getInstance().exists(info)) {

					// To Add the New Record.
					AccountingCodeService.getInstance().add(info);

					// To Display the Record has been saved Successfully.
					errors.add("accountHead", new ActionMessage(
							"record.saved.sucess"));
				} else {
					errors.add("", new ActionMessage("record.exists"));
					
					// The Trust Types that are to be displayed in the Select.
					request.setAttribute("trustTypes", TrustTypeService.getInstance()
							.getTrustTypes());

					// The Accounting Code Types that are to be displayed in the Select.
					request.setAttribute("typesList", AccountingCodeTypeService
							.getInstance().getAccountingCodeTypes());
					
					/*
					 * As the record already exists it is forwarded to same
					 * Input Screen Displaying the error.
					 */
					forward = mapping.getInputForward();
				}
			} else {
				// To Update the Record.
				AccountingCodeService.getInstance().update(info);

				// To Display the Record has been saved Successfully.
				errors.add("accountHead", new ActionMessage(
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
	 * Method fwdtoEdit To Forward to the Edit Page.
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
			AccountingCodeInfo info = new AccountingCodeInfo();
			info.setAccountHead(request.getParameter("code"));
			info = AccountingCodeService.getInstance().edit(info);
			/*
			 * Copy property values from the AccountingCodeInfo bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(dyna, info);

			// The Opening Balances that are to be Displayed in the Form.
			request.setAttribute("openBals", info.getOpenBalances());

			// The Trust Types that are to be displayed in the Select.
			request.setAttribute("trustTypes", TrustTypeService.getInstance()
					.getTrustTypes());

			// The Accounting Code Types that are to be displayed in the Select.
			request.setAttribute("typesList", AccountingCodeTypeService
					.getInstance().getAccountingCodeTypes());

		} catch (Exception e) {
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
			forward = mapping.getInputForward();
		} finally {
			saveErrors(request.getSession(), errors);
		}
		return forward;
	}

	/**
	 * Method reportParam
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Forwarded to Report paramater Screen.
	 */
	public ActionForward reportParam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeInfo info = new AccountingCodeInfo(request);

			/*
			 * Copy property values from the AccountingCodeInfo bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			// The Accounting Code Types that are to be displayed in the Select.
			request.setAttribute("typesList", AccountingCodeTypeService
					.getInstance().getAccountingCodeTypes());

		} catch (Exception e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		
		return mapping.findForward("param");
	}

	/**
	 * Method getReport
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		
		ActionErrors errors = new ActionErrors();
		/*
		 * If there is no problem in reteiving the data of that particular
		 * Record then forwarded to Report Screen.
		 */
		ActionForward forward = mapping.findForward("report");
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			AccountingCodeInfo info = new AccountingCodeInfo(request);
			
			/*
			 * Copy property values from the AccountingCodeInfo bean to the Form
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);
			
			// The Trust Types that are to be displayed in the Select.
			request.setAttribute("trustTypes", TrustTypeService.getInstance()
					.getTrustTypes());
			
			// To Retrive the List of records to display in the Report.
			request.setAttribute("accList", AccountingCodeService.getInstance()
					.getReport(info));
			
			// If the report Type is of Excel the page is forwarded to reportExcel. 
			if ("excel".equals(dyna.getString("screenType"))) {
				forward = mapping.findForward("reportExcel");
			}
		} catch (Exception e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		saveErrors(request, errors);
		return forward;
	}
}
