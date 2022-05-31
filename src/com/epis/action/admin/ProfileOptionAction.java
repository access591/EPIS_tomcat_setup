package com.epis.action.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.admin.ProfileOptionBean;
import com.epis.services.admin.ProfileOptionService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ProfileOptionAction extends DispatchAction {
	Log log = new Log(ProfileOptionAction.class);

	public ActionForward showOptionAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		return mapping.findForward("showoptionnew");
	}

	public ActionForward addOption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		DynaValidatorForm option = (DynaValidatorForm) form;
		ProfileOptionBean bean = new ProfileOptionBean(request);
		ActionForward forward = mapping.findForward("optionsearch");
		
		try {
			bean.setOptionName(StringUtility.checknull(option.getString("optionName")));
			bean.setDescription(StringUtility.checknull(option.getString("description")));
			bean.setPath(StringUtility.checknull(option.getString("path")));
			bean.setOptionType(StringUtility.checknull(option.getString("optionType")));
			ProfileOptionService.getInstance().saveOption(bean);

		} catch (Exception e) {
			log.error("ProfileOptionAction:addOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("option", new ActionMessage("option.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchOption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward =mapping.findForward("showoptionsearch");
		
		try {
			List optionList = null;			
			DynaValidatorForm dyna = (DynaValidatorForm) form;			
			optionList = ProfileOptionService.getInstance().searchOption(
					StringUtility.checknull(dyna.getString("optionCode")),StringUtility.checknull(dyna.getString("optionName")));
			request.setAttribute("optionList", optionList);
			
		} catch (Exception e) {			
			log.error("ProfileOptionAction:searchOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("option", new ActionMessage("option.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditOption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String optioncode = request.getParameter("optioncode");
		ActionForward forward =mapping.findForward("showoptionedit");
		
		try {
			ProfileOptionBean bean = new ProfileOptionBean();
		    bean = ProfileOptionService.getInstance().findOption(optioncode);		
		    request.setAttribute("option", bean);
		} catch (Exception e) {
			log.error("ProfileOptionAction:showEditOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("option", new ActionMessage("option.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateOption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm option = (DynaValidatorForm) form;
		ProfileOptionBean bean = new ProfileOptionBean(request);
		ActionForward forward =mapping.findForward("optionsearch");
		
		try {
			bean.setOptionCode(StringUtility.checknull(option.getString("optionCode")));
			bean.setOptionName(StringUtility.checknull(option.getString("optionName")));
			bean.setDescription(StringUtility.checknull(option.getString("description")));	
			bean.setPath(StringUtility.checknull(option.getString("path")));
			bean.setOptionType(StringUtility.checknull(option.getString("optionType")));
			ProfileOptionService.getInstance().editOption(bean);
		} catch (Exception e) {
			log.error("ProfileOptionAction:updateOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("option", new ActionMessage("option.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("option", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteOption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("optionemptysearch");
		
		try {
			String optioncds = request.getParameter("deleteall");
			ProfileOptionService.getInstance().deleteOption(optioncds);
		} catch (Exception e) {
			log.error("ProfileOptionAction:deleteOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("option", new ActionMessage("option.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward("optionsearch");
		}
		
		return forward;
	}

}
