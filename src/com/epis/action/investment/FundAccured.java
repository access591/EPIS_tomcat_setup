package com.epis.action.investment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.admin.Bean;
import com.epis.bean.investment.FundAccuredBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;

import com.epis.services.investment.FundAccuredService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class FundAccured extends DispatchAction {

	Log log = new Log(FundAccured.class);

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceNotAvailableException {
		ActionMessages errors = (ActionMessages) request.getSession().getAttribute(
				"org.apache.struts.action.ERROR");
		try {
			if (errors == null)
				errors = new ActionMessages();
			DynaValidatorActionForm dynaForm = (DynaValidatorActionForm) form;
			FundAccuredBean bean = new FundAccuredBean();
			BeanUtils.copyProperties(bean, dynaForm);
			request.setAttribute("list", FundAccuredService.getInstance()
					.search(bean));
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for (int i = finYears.length - 1; i >= 0; i--) {
				finYear.add(new Bean(finYears[i], finYears[i]));
			}
			request.setAttribute("finYear", finYear);
		} catch (IllegalAccessException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} catch (EPISException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		return mapping.getInputForward();
	}

	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceNotAvailableException {
		ActionMessages errors = new ActionMessages();
		try {
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for (int i = finYears.length - 1; i >= 0; i--) {
				finYear.add(new Bean(finYears[i], finYears[i]));
			}
			request.setAttribute("finYear", finYear);
			request.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());
		} catch (Exception e) {
			errors.add("deleteRecord", new ActionMessage("errors", e
					.getMessage()));
		} finally {
			saveErrors(request, errors);
		}
		return mapping.findForward("new");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceNotAvailableException {
		ActionForward forward = mapping.findForward("search");
		ActionMessages errors = new ActionMessages();
		try {
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for (int i = finYears.length - 1; i >= 0; i--) {
				finYear.add(new Bean(finYears[i], finYears[i]));
			}
			request.setAttribute("finYear", finYear);
			request.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());
			DynaValidatorActionForm dynaForm = (DynaValidatorActionForm) form;
			FundAccuredBean bean = new FundAccuredBean();
			BeanUtils.copyProperties(bean, dynaForm);
			FundAccuredService.getInstance().add(bean);
		} catch (IllegalAccessException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} catch (InvocationTargetException e) {
			errors.add("", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} catch (EPISException e) {
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

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceNotAvailableException {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward("search");
		try {
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for (int i = finYears.length - 1; i >= 0; i--) {
				finYear.add(new Bean(finYears[i], finYears[i]));
			}
			request.setAttribute("finYear", finYear);
			FundAccuredBean bean = new FundAccuredBean();
			bean.setFinYear(request.getParameter("deleteRecord").substring(0,request.getParameter("deleteRecord").indexOf("#")));
			bean.setTrustType(request.getParameter("deleteRecord").substring(request.getParameter("deleteRecord").indexOf("#")+1));
			FundAccuredService.getInstance().delete(bean);
		} catch (EPISException e) {
			errors.add("deleteRecord", new ActionMessage("errors", e.getMessage()));
			forward = mapping.getInputForward();
		} finally {
			saveErrors(request.getSession(), errors);
		}
		return forward;
	}
}
