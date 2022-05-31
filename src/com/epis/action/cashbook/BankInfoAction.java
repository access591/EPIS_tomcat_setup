/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.action.cashbook;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.common.exception.EPISException;

import com.epis.services.admin.RegionService;
import com.epis.services.admin.UnitService;
import com.epis.services.cashbook.AccountingCodeService;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

/**
 * Class BankInfoAction enables a user to collect related functions into a
 * single Action.An Action Class performs a role of an adapter between the
 * contents of an incoming HTTP request and the corresponding business logic
 * that should be executed to process this request corresponding to Bank
 * Information.
 * 
 * @version 1.00 03 May 2010
 * @author Jaya Sree
 */
public class BankInfoAction extends DispatchAction {

	/**
	 * Method search To display the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to BankMasterSearch.jsp
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
			BankMasterInfo info = new BankMasterInfo();

			/*
			 * Copy property values from the Form bean to the BankMasterInfo
			 * bean for all cases where the property names are the same.
			 */
			BeanUtils.copyProperties(info, dyna);

			/*
			 * The Results are placed in the request to display the records in
			 * the Search Result Page.
			 */
			request.setAttribute("BankList", BankMasterService.getInstance()
					.search(info));

		} catch (IllegalAccessException e) {
			errors.add("bankName", new ActionMessage("errors", e.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("bankName", new ActionMessage("errors", e.getMessage()));
		} catch (Exception e) {
			errors.add("bankName", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		return mapping.getInputForward();
	}

	/**
	 * Method delete To delete the Records in the Search Result Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The page is forwarded to BankMasterSearch.jsp
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		String[] types = dyna.getStrings("deleteRecord");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			BankMasterService.getInstance().delete(types);
			BankMasterInfo info = new BankMasterInfo(request);
			int len = types.length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append(types[i]).append("|");
			}
			UserTracking.write(info.getLoginUserId(),sb.toString(), "D", "CB", sb.toString(),"Bank Master");
			errors.add("deleteRecord", new ActionMessage("record.deleted.sucess"));
		} catch (EPISException e) {
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
		}
		saveErrors(session, errors);
		return mapping.findForward("search");
	}

	/**
	 * Method fwdtoNew
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			session.setAttribute("trustTypes", TrustTypeService.getInstance()
					.getTrustTypes());
			session.setAttribute("regions", RegionService.getInstance()
					.getRegionList());
			session.setAttribute("accountHeads", AccountingCodeService
					.getInstance().getAccountingHeads());
			request.setAttribute("ScreenType", "NEW");
		} catch (Exception e) {
			errors.add("bankName", new ActionMessage("errors", e.getMessage()));
		}finally{
			saveErrors(session, errors);
		}
		return mapping.findForward("new");
	}

	/**
	 * Method addAccountTypeRecord
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		BankMasterInfo info = new BankMasterInfo(request);
		ActionForward forward = mapping.findForward("search");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try {
			BeanUtils.copyProperties(info, dyna);
			if ("NEW".equals(StringUtility.checknull(request
					.getParameter("ScreenType")))) {
				if (BankMasterService.getInstance().exists(info)) {
					errors.add("bankInfo.errors.exists", new ActionMessage(
							"bankInfo.errors.exists", info.getAccountNo()));
					request.setAttribute("ScreenType", "NEW");
					forward = mapping.getInputForward();
				} else {
					BankMasterService.getInstance().add(info);
					errors.add("bankName", new ActionMessage(
							"record.saved.sucess"));
				}
			} else if ("EDIT".equals(StringUtility.checknull(request
					.getParameter("ScreenType")))) {
				BankMasterService.getInstance().update(info);
				errors
						.add("bankName", new ActionMessage(
								"record.saved.sucess"));
			}
		} catch (Exception e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(session, errors);
		}
		return forward;
	}

	/**
	 * Method getUnits
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	public ActionForward getUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			out.print(UnitService.getInstance().getUnits("", "", "",
					StringUtility.checknull(dyna.getString("region"))));
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors
					.add("unit", new ActionMessage("unit.Errors", e
							.getMessage()));
			saveErrors(session, errors);
		}
		return null;
	}

	/**
	 * Method fwdtoEdit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			BankMasterInfo info = new BankMasterInfo();
			info.setAccountNo(request.getParameter("code"));
			info = BankMasterService.getInstance().edit(info);
			BeanUtils.copyProperties(dyna, info);
			request.setAttribute("ScreenType", "EDIT");
			request.setAttribute("unitName", StringUtility.checknull(info
					.getUnitName()));
			session.setAttribute("trustTypes", TrustTypeService.getInstance()
					.getTrustTypes());
			session.setAttribute("regions", RegionService.getInstance()
					.getRegionList());
			session.setAttribute("accountHeads", AccountingCodeService
					.getInstance().getAccountingHeads());
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("bankName", new ActionMessage("unit.Errors", e
					.getMessage()));
			saveErrors(session, errors);
		}
		return mapping.findForward("new");
	}
}
