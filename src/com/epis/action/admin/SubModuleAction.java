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
import com.epis.bean.admin.SMBean;
import com.epis.services.admin.SubModuleService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class SubModuleAction extends DispatchAction {
	Log log = new Log(SubModuleAction.class);

	public ActionForward showSMAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List modules=SubModuleService.getInstance().getModuleList();
			
		request.setAttribute("modules",modules);
		}catch (Exception e) {
			log.error("SubModuleAction:showSMAd:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);			
			
		}
		return mapping.findForward("showsmnew");
	}

	public ActionForward addSM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)			 {
		
		DynaValidatorForm sm = (DynaValidatorForm) form;
		SMBean bean = new SMBean(request);
		ActionForward forward = mapping.findForward("smsearch");
		
		try {
			bean.setModuleCode(StringUtility.checknull(sm.getString("moduleCode")));
			bean.setSubModuleName(StringUtility.checknull(sm.getString("subModuleName")));
			bean.setSortingOrder(StringUtility.checknull(sm.getString("sortingOrder")));
			SubModuleService.getInstance().saveSM(bean);
		} catch (Exception e) {
			log.error("SubModuleAction:addSM:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchSM(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("showsmsearch");
		try {
			List smList = null;			
			DynaValidatorForm dyna = (DynaValidatorForm) form;			
			smList = SubModuleService.getInstance().searchSM(
					StringUtility.checknull(dyna.getString("moduleCode")),StringUtility.checknull(dyna.getString("subModuleName")));
			  List modules=SubModuleService.getInstance().getModuleList();
				
				request.setAttribute("modules",modules);
			request.setAttribute("smList", smList);
			
		} catch (Exception e) {			
			log.error("SubModuleAction:searchSM:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditSM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String smcode = request.getParameter("smcode");
		ActionForward forward =mapping.findForward("showsmedit");
		try {
			SMBean bean = new SMBean();			
		    bean = SubModuleService.getInstance().findSM(smcode);		
		    List modules=SubModuleService.getInstance().getModuleList();
			
			request.setAttribute("modules",modules);
		    request.setAttribute("submodule", bean);
		} catch (Exception e) {
			log.error("SubModuleAction:showEditSM:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateSM(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm sm = (DynaValidatorForm) form;
		SMBean bean = new SMBean(request);
		ActionForward forward =mapping.findForward("smsearch");
		
		try {
			bean.setModuleCode(StringUtility.checknull(sm.getString("moduleCode")));
			bean.setSubModuleCode(StringUtility.checknull(sm.getString("subModuleCode")));
			bean.setSubModuleName(StringUtility.checknull(sm.getString("subModuleName")));
			bean.setSortingOrder(StringUtility.checknull(sm.getString("sortingOrder")));
			SubModuleService.getInstance().editSM(bean);
		} catch (Exception e) {
			log.error("SubModuleAction:updateSM:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("submodule", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteSM(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("smemptysearch");
		
		try {
			String smcds = request.getParameter("deleteall");
			SubModuleService.getInstance().deleteSM(smcds);
		} catch (Exception e) {
			log.error("SubModuleAction:deleteSM:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward("smsearch");
		}
		
		return forward;
	}

}
