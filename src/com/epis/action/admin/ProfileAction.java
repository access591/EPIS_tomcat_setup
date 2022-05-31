package com.epis.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import com.epis.bean.admin.ProfileOptionBean;
import com.epis.info.login.LoginInfo;
import com.epis.services.admin.ProfileService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ProfileAction extends DispatchAction {
	Log log = new Log(ProfileAction.class);

	public ActionForward showProfileOptions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {		
		
		try {
			List poList = null;						
			poList = ProfileService.getInstance().getProfileOptionList();
			request.setAttribute("profileOptionList", poList);
			
		} catch (Exception e) {			
			log.error("ProfileAction:showProfileOptions:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("profile", new ActionMessage("profile.Errors", e.getMessage()));
			saveErrors(request, errors);	
		}
		return mapping.findForward("showprofileoptions");
	}
	public ActionForward createProfileOptions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {		
		
		try {
			List poList = null;	
			poList = ProfileService.getInstance().getProfileOptionList();
			request.setAttribute("profileOptionList", poList);
			
		} catch (Exception e) {			
			log.error("ProfileAction:showProfileOptions:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("profile", new ActionMessage("profile.Errors", e.getMessage()));
			saveErrors(request, errors);	
		}
		return mapping.findForward("modifyprofileoptions");
	}
	public ActionForward updateProfileOptions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {		
		
		try {
			HttpSession session=request.getSession();
			LoginInfo loginInfo=(LoginInfo)session.getAttribute("user");
			List poList = new ArrayList();	
			ProfileOptionBean pobean=null;
			String[] optioncds=request.getParameterValues("option");
			
			if(optioncds!=null){
				for(int i=0;i<optioncds.length;i++){
					pobean=new ProfileOptionBean(optioncds[i],"","",StringUtility.checkFlag(request.getParameter(optioncds[i]+"M")),StringUtility.checkFlag(request.getParameter(optioncds[i]+"U")),StringUtility.checkFlag(request.getParameter(optioncds[i]+"R")),StringUtility.checkFlag(request.getParameter(optioncds[i]+"C")));
					pobean.setLoginUserId(loginInfo.getUserId());
					poList.add(pobean);
				}
			}
			
			ProfileService.getInstance().updateProfiles(poList);
		} catch (Exception e) {			
			log.error("ProfileAction:showProfileOptions:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("profile", new ActionMessage("profile.Errors", e.getMessage()));
			saveErrors(request, errors);	
			
		}
		return mapping.findForward("updateprofileoptions");
	}
	public ActionForward getProfile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {				
		try {
			String profileType = request.getParameter("profile");
			HttpSession session = request.getSession();
			if(session.getAttribute("profileList") == null  || session.getAttribute("reportList") == null){
				Map profile = ProfileService.getInstance().getProfile(profileType);				
				session.setAttribute("profileList",profile.get("S"));
				session.setAttribute("reportList",profile.get("R"));
				session.setAttribute("payrollList",profile.get("P"));
			}
		} catch (Exception e) {			
			log.error("ProfileAction:showProfileOptions:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("profile", new ActionMessage("profile.Errors", e.getMessage()));
			saveErrors(request, errors);	
			
		}
		return mapping.findForward("profile");
	}
	
}
