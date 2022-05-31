package com.epis.action.investment;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.investment.QuotationBean;
import com.epis.dao.investment.YTMVerificationDao;
import com.epis.services.investment.YTMVerificationService;
import com.epis.services.investment.QuotationService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class YTMVerificationAction extends DispatchAction {
	Log log = new Log(YTMVerificationAction.class);

	public ActionForward showYTMAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ArrayList letterlist = null;
		try {
			letterlist = (ArrayList) YTMVerificationService.getInstance()
					.getLetterNo();
			request.setAttribute("letters", letterlist);
		} catch (Exception e) {
			log.error("YTMAction:showYTMAdd:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("ytm", new ActionMessage("ytm.Errors", e.getMessage()));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		return mapping.findForward("ytmnew");
	}

	public ActionForward showYTMAddDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		QuotationBean qbean = new QuotationBean(request);
		String letterNo = StringUtility.checknull(request
				.getParameter("letterNo"));
		qbean.setLetterNo(letterNo);
		String ytmFlag = StringUtility.checknull(request
				.getParameter("ytmflag"));
		String ytmshow=StringUtility.checknull(request.getParameter("ytmshow"));
		ActionForward forward = mapping.findForward("ytmnewdetails");

		// log.info("letterNo:::"+letterNo);
		ArrayList letterdetails = null;

		try {
			if ("Y".equals(ytmFlag)) {
				qbean.setStatus(ytmFlag);
				YTMVerificationService.getInstance().updateYTMFlag(qbean);
				forward = mapping.findForward("ytmsearch");
			}
			letterdetails = (ArrayList) YTMVerificationService.getInstance()
					.getLetterDetails(letterNo);
			request.setAttribute("letterdetails", letterdetails);
			
			request.setAttribute("ytmshow",ytmshow);
			request.setAttribute("letterno", qbean);
		} catch (Exception e) {
			log
					.error("YTMAction:showYTMAddDetails:Exception:"
							+ e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("ytm", new ActionMessage("ytm.Errors", e.getMessage()));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws JspException {
		String quotationCD = request.getParameter("quotationCd");
		String letterNo = request.getParameter("letterNo");
		QuotationBean bean = new QuotationBean(request);
		bean.setQuotationCd(quotationCD);
		bean.setLetterNo(letterNo);
		String checking=StringUtility.checknull(request.getParameter("ytmshow"));
		 log.info("anil ytmshow in action:::"+checking);
		ActionForward forward=null;
		if(checking.equals("ytmshow"))
		{
		 forward = mapping.findForward("ytmsearchsuccess");
		}
		else
		{
			forward = mapping.findForward("ytmsearch");
		}
		try {
			YTMVerificationService.getInstance().editQuotation(bean);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("ytm", new ActionMessage("ytm.Errors", e.getMessage()));
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchYTM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("showytmsearch");

		ArrayList ytmList = null;
		ArrayList letterlist = null;
		try {
			DynaValidatorForm ytm = (DynaValidatorForm) form;
			String letterNo = StringUtility
					.checknull(ytm.getString("letterNo"));
			String status = StringUtility.checknull(ytm
					.getString("ytmVerified"));
			ytmList = (ArrayList) YTMVerificationDao.getInstance().searchYTM(
					letterNo, status);
			request.setAttribute("ytmList", ytmList);
			letterlist = (ArrayList) QuotationService.getInstance()
					.getLetterNumbers();
			request.setAttribute("letters", letterlist);
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("ytm", new ActionMessage("ytm.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showYTMDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String letterNo = StringUtility.checknull(request
				.getParameter("letterNo"));
		// log.info("letterNo:::"+letterNo);
		ArrayList letterdetails = null;
		QuotationBean qbean = new QuotationBean();
		try {
			letterdetails = (ArrayList) YTMVerificationService.getInstance()
					.getLetterDetails(letterNo);
			request.setAttribute("letterdetails", letterdetails);
			qbean.setLetterNo(letterNo);
			request.setAttribute("letterno", qbean);
		} catch (Exception e) {
			log.error("YTMAction:showYTMDetails:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("ytm", new ActionMessage("ytm.Errors", e.getMessage()));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		return mapping.findForward("ytmdetails");
	}
}
