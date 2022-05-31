package com.epis.action.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.epis.info.login.LoginInfo;
import com.epis.services.admin.AccessRightsService;
import com.epis.services.admin.RoleService;
import com.epis.services.admin.UserService;
import com.epis.utilities.Configurations;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.admin.AccessRightsBean;

public class AccessRightsAction extends DispatchAction {
	Log log = new Log(AccessRightsAction.class);

	public ActionForward fwdtoAccessRights(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){		
		try {
			List users=null;
			
			if("USER".equals(Configurations.getAccessRightsType())){
				users=UserService.getInstance().getUserList(((LoginInfo)request.getSession().getAttribute("user")).getUserId());
			}else if("ROLE".equals(Configurations.getAccessRightsType())){
				users=RoleService.getInstance().getRoles(((LoginInfo)request.getSession().getAttribute("user")).getUserId());
			}
			
			request.setAttribute("users",users);	
							
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("AccessRightsAction:search:Exception"+e.getMessage());
		}
		return mapping.findForward("accessRights");
	}
	
	public ActionForward edit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){
		
		String [] screeninfo=null;
		List screens=new ArrayList();
		AccessRightsBean bean=new AccessRightsBean(request);
		try {
			Map screencodes=UserService.getInstance().getAllScreenCodes();
			Enumeration penum=request.getParameterNames();
			while(penum.hasMoreElements()){
				String element=(String)penum.nextElement();
				
				if(screencodes.containsKey(element)){
					
					screeninfo=new String[2];
					screeninfo[0]=element;
					//screeninfo[1]=StringUtility.checkFlag(request.getParameter(element+"N"));
					//screeninfo[2]=StringUtility.checkFlag(request.getParameter(element+"E"));
				    //screeninfo[3]=StringUtility.checkFlag(request.getParameter(element+"D"));
                    //screeninfo[4]=StringUtility.checkFlag(request.getParameter(element+"V"));
                    ///screeninfo[5]=StringUtility.checkFlag(request.getParameter(element+"R"));
                    screeninfo[1]=StringUtility.checknull(bean.getLoginUserId());
                    screens.add(screeninfo);
				}
				
			}
			AccessRightsService.getInstance().saveAccessRights(screens,request.getParameter("userName"),request.getParameter("moduleCodes"));
			//List users=UserService.getInstance().getUserList();
			//request.setAttribute("users",users);	
			
			/*
			 * DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			HttpSession session = request.getSession();
			String userType = (String)session.getAttribute("Type");
			String user = dyna.getString("user");
			String modules = dyna.getString("modules");
			Map accessCodes = AccessRightsService.getInstance().getAccessCodes(modules);
			Map accessrights = AccessRightsService.getInstance().getAccessRights(userType,user,modules);
			Iterator iter = accessrights.keySet().iterator();
			while(iter.hasNext()){
				String screencode = (String)iter.next();
				bean = (AccessRightsBean)accessCodes.get(screencode);
			}
			*/
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("AccessRightsAction:search:Exception"+e.getMessage());
		}
		return mapping.findForward("screenCodes");
	}	
}
