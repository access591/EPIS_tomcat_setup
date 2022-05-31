package com.epis.action.cashbook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.cashbook.VoucherInfo;
import com.epis.services.cashbook.NotificationService;
import com.epis.utilities.StringUtility;

public class Notifications extends DispatchAction {

	/**
	 * Method getNotifications
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getNotifications(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors =  new ActionErrors();
		try {
			request.setAttribute("cpf", NotificationService.getInstance().getNotifications("CPF"));
			request.setAttribute("pfw", NotificationService.getInstance().getNotifications("PFW"));
			request.setAttribute("fs", NotificationService.getInstance().getNotifications("FS"));
			request.setAttribute("fsa", NotificationService.getInstance().getNotifications("FSA"));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.findForward("notifications");
	}
	
	public ActionForward pendingAmt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		ActionErrors errors =  new ActionErrors();
		List pendingAmtlist=null;
		try{
			String queryType=StringUtility.checknull(request.getParameter("queryType"));
			pendingAmtlist=NotificationService.getInstance().getPendingAmt(queryType);
			request.setAttribute("pendingAmtlist",pendingAmtlist);
		}
		catch(Exception e)
		{
			saveErrors(session,errors);
		}
		return mapping.findForward("pendingamtemp");
	}
	
	/**
	 * Method fwdToPayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdToPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors =  new ActionErrors();
		try {
			DynaValidatorActionForm dyna =(DynaValidatorActionForm) form;
			VoucherInfo info = new VoucherInfo();
			info.setTransactionId(dyna.getString("keyno"));
			request.setAttribute("payment", NotificationService.getInstance().getVoucherDetails(info));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.findForward("paymentVocucher");
	}
	
}
