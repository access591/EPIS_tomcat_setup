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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.cashbook.BankOpenBalInfo;
import com.epis.common.exception.EPISException;
import com.epis.services.cashbook.BankOpenBalService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
/**
 * MyEclipse Struts Creation date: 03-05-2010
 * 
 * XDoclet definition:
 * 
 */

public class BankOpenBal extends DispatchAction {
	Log log = new Log(BankOpenBal.class);
	
	/**
	 * Method search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages errors = (ActionMessages)request.getAttribute("org.apache.struts.action.ERROR");
		
		log.info("BankOpenBal : Add : Entering Method");
		try {
			if(errors == null)
				errors = new ActionMessages();
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			BankOpenBalInfo info = new BankOpenBalInfo(); 
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("dataList", BankOpenBalService.getInstance().search(info));
			request.setAttribute("bankDetails",BankOpenBalService.getInstance().getBankList());
			request.setAttribute("ScreenType","SEARCH");
		} catch (IllegalAccessException e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		} catch (Exception e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return mapping.getInputForward();
	}
	
	public ActionForward getAccno(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			out.print(BankOpenBalService.getInstance().getAccno(StringUtility.checknull(dyna.getString("bankName"))));
			} catch (Exception e) {	
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
		}
		return null;
	}
	
	/**
	 * Method delete
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		String[] types = dyna.getStrings("deleteRecord");		
		ActionMessages errors = new ActionMessages();
		try {
			BankOpenBalService.getInstance().delete(types);
		} catch (EPISException e) {
			errors.add("deleteRecord",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return mapping.findForward("search");
	}

	//Log log = new Log(BankMaster.class);	
	
	
	
	/**
	 * Method fwdtoNew
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
		request.setAttribute("bankDetails",BankOpenBalService.getInstance().getBankList());
		request.setAttribute("ScreenType","NEW");
		
		} catch (Exception e) {
			e.printStackTrace();
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
	public ActionForward add(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("BankOpenBal : Add : Entering Method");
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;		
		BankOpenBalInfo info = new BankOpenBalInfo(request);
		ActionForward forward = mapping.findForward("search");
		BankOpenBalService service = BankOpenBalService.getInstance();		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try {
			BeanUtils.copyProperties(info,dyna);
			if("NEW".equals(StringUtility.checknull(request.getParameter("ScreenType")))){
				if(service.exists(info)){
					errors.add("bankInfo.errors.exists",new ActionMessage("bankInfo.errors.exists",info.getAccountNo()));
					forward = mapping.getInputForward();
					throw new Exception();
				}	
				service.add(info);
			}
				else if("EDIT".equals(StringUtility.checknull(request.getParameter("ScreenType")))){
					service.update(info);
				}
			
			forward = mapping.findForward("search");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			saveErrors(session,errors);
		}
		return forward;
	}
	
	
	
	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			BankOpenBalInfo info = new BankOpenBalInfo();
			info.setAccountNo(request.getParameter("accountNo"));
			
			info = BankOpenBalService.getInstance().edit(info);				
			BeanUtils.copyProperties(dyna,info);
			request.setAttribute("ScreenType","EDIT");
			
			request.setAttribute("bankName",StringUtility.checknull(info.getBankName()));
			request.setAttribute("accountNo",StringUtility.checknull(info.getAccountNo()));
			HttpSession session = request.getSession();			
			session.setAttribute("bankDetails",BankOpenBalService.getInstance().getBankList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("new");		
	}

}

