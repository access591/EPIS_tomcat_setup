package com.epis.action.admin;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.admin.RoleBean;
import com.epis.services.admin.RoleService;
import com.epis.services.admin.SubModuleService;

import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
public class RoleAction extends DispatchAction {
	Log log = new Log(RoleAction.class);

	public ActionForward showRoleAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("showRoleAdd");
		try {
			
			ArrayList modulelist = (ArrayList)SubModuleService.getInstance().getModuleList();
			 request.setAttribute("modulelist", modulelist);
			
			
		}catch (Exception e) {
			log.error("RoleAction:showRoleAd:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);			
			
		}
		return mapping.findForward("showrolenew");
	}

	public ActionForward addRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)			 {
		
		DynaValidatorForm sm = (DynaValidatorForm) form;
		RoleBean bean = new RoleBean(request);
		ActionForward forward = mapping.findForward("rolesearch");
		System.out.println("addRole");
		try {
			
			bean.setRoleName(StringUtility.checknull(sm.getString("roleName")));
			bean.setRoleDescription(StringUtility.checknull(sm.getString("roleDescription")));
			bean.setModules(StringUtility.checknull(sm.getString("selectedModules")));
			
			RoleService.getInstance().saveRole(bean);
		} catch (Exception e) {
			log.error("RoleAction:addRole:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("showrolesearch");
		System.out.println("showRoleAdd");
		try {
			List roleList = null;			
			DynaValidatorForm dyna = (DynaValidatorForm) form;			
			roleList = RoleService.getInstance().searchRole(
			StringUtility.checknull(dyna.getString("roleName")));
			System.out.println("roleList"+roleList.size());
			request.setAttribute("roleList", roleList);
			
		} catch (Exception e) {			
			log.error("RoleAction:searchRole:Exception :"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors ", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String roleCd = request.getParameter("roleCd");
		ActionForward forward =mapping.findForward("showroleedit");
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;			
			RoleBean bean = new RoleBean();			
		    bean = RoleService.getInstance().findRole(roleCd);	
		    ArrayList modulelist = (ArrayList)SubModuleService.getInstance().getModuleList();
			 request.setAttribute("modulelist", modulelist);
			 BeanUtils.copyProperty(dyna,"modules",bean.getModules().split("\\,")); 
		    request.setAttribute("role", bean);
		} catch (Exception e) {
			log.error("RoleAction:showEditRole:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("submodule", new ActionMessage("submodule.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm sm = (DynaValidatorForm) form;
		RoleBean bean = new RoleBean(request);
		ActionForward forward =mapping.findForward("rolesearch");
		
		try {
			bean.setRoleCd(StringUtility.checknull(sm.getString("roleCd")));
			bean.setRoleName(StringUtility.checknull(sm.getString("roleName")));
			bean.setRoleDescription(StringUtility.checknull(sm.getString("roleDescription")));
			bean.setModules(StringUtility.checknull(sm.getString("selectedModules")));
			RoleService.getInstance().editRole(bean);
		} catch (Exception e) {
			log.error("RoleAction:updateRole:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("role", new ActionMessage("role.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("role", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("roleemptysearch");
		
		try {
			String roleCd = request.getParameter("deleteall");
			RoleService.getInstance().deleteRole(roleCd);
		} catch (Exception e) {
			log.error("RoleAction:deleteRole:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("role", new ActionMessage("role.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward("rolesearch");
		}
		
		return forward;
	}

}
