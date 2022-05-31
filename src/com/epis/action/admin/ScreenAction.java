package com.epis.action.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.services.admin.SubModuleService;
import com.epis.services.admin.ScreenService;
import com.epis.bean.admin.ScreenBean;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ScreenAction extends DispatchAction {
	Log log = new Log(ScreenAction.class);

	public ActionForward showNewScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ArrayList modulelist = null;
		try {
			modulelist = (ArrayList) SubModuleService.getInstance()
					.getModuleList();
			request.setAttribute("modules", modulelist);
		} catch (Exception e) {
			log.error("ScreenAction:showNewScreen:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		return mapping.findForward("shownewscreen");
	}

	public ActionForward searchScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("showscreensearch");
		ArrayList modulelist = null;
		ArrayList submodulelist = null;
		try {
			List screenList = null;
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			screenList = ScreenService.getInstance().searchScreen(
					StringUtility.checknull(dyna.getString("screenName")),
					StringUtility.checknull(dyna.getString("subModuleName")),
					StringUtility.checknull(dyna.getString("moduleName")));
			request.setAttribute("screens", screenList);
			modulelist = (ArrayList) SubModuleService.getInstance()
					.getModuleList();
			request.setAttribute("modules", modulelist);
			submodulelist = (ArrayList) SubModuleService.getInstance()
					.getSMList();
			request.setAttribute("submodules", submodulelist);
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward addScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JspException {
		DynaValidatorForm screen = (DynaValidatorForm) form;
		ScreenBean bean = new ScreenBean(request);
		ActionForward forward = mapping.findForward("searchscreen");
		try {
			BeanUtils.copyProperties(bean,screen);	
			ScreenService.getInstance().saveScreen(bean);

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String screenCD = request.getParameter("screenCD");
		ScreenBean bean = new ScreenBean();
		ActionForward forward = mapping.findForward("showeditscreen");
		try {
			bean = ScreenService.getInstance().findScreen(screenCD);
			request.setAttribute("screen", bean);
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward editScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm screen = (DynaValidatorForm) form;
		ScreenBean bean = new ScreenBean(request);
		ActionForward forward = mapping.findForward("searchscreen");
		try {
			BeanUtils.copyProperties(bean,screen);	
			ScreenService.getInstance().editScreen(bean);

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = mapping.findForward("searchscreen");
		try {
			String screencds = request.getParameter("deleteall");
			ScreenService.getInstance().deleteScreen(screencds);
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("screen", new ActionMessage("screen.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward("searchscreenerror");
		}
		return forward;
	}

}
