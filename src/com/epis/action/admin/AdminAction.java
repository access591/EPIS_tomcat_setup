package com.epis.action.admin;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.info.login.LoginInfo;
import com.epis.services.admin.AdminService;
import com.epis.utilities.Log;

public class AdminAction extends DispatchAction{
	
	Log log = new Log(AdminAction.class);
	public ActionForward showResetPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			List userList = null;	
					
			userList = AdminService.getInstance().getUserList();
			request.setAttribute("userList", userList);
			
		} catch (Exception e) {			
			log.error("AdminAction:showResetPassword:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("resetPassword", new ActionMessage("resetpassword.Errors", e
					.getMessage()));
			saveErrors(request, errors);	
			
		}
		return mapping.findForward("showresetpassword");
	}
	public ActionForward resetPassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		 DynaValidatorForm dyna = (DynaValidatorForm) form;
		LoginInfo loginInfo = new LoginInfo();		
		try{
			ActionMessages errors = new ActionMessages();
			loginInfo.setUserId(dyna.getString("userId"));			
			loginInfo.setNewPassword(dyna.getString("newPassword"));
			loginInfo.setConfirmPassword(dyna.getString("confirmPassword"));
			if(loginInfo.getNewPassword().equals(loginInfo.getConfirmPassword())){
				AdminService.getInstance().resetPassword(loginInfo);	
				
				errors.add("resetPassword", new ActionMessage("resetpassword.Errors", "Password Changed Successfully"));
				saveErrors(request, errors);
			}
			else{				
				errors.add("",new ActionMessage("errors.confirmPassword"));
				saveErrors(request,errors);				
			}
		}
		catch(Exception e){			
			ActionMessages errors = new ActionMessages();
			errors.add("resetPassword", new ActionMessage("resetpassword.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			log.error("AdminAction:resetPassword:Exception:"+e.getMessage());
		}
		return mapping.findForward("resetpassword");
	}
}
	
