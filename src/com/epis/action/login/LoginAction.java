package com.epis.action.login;

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
import com.epis.services.LoginService;
import com.epis.utilities.StringUtility;

public class LoginAction extends DispatchAction{
	
	public ActionForward loginValidation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		DynaValidatorForm dyna = (DynaValidatorForm) form;
		LoginInfo user=null;
		ActionForward forword=null;		
		
		try{	
			 String errorCode="";	
			 String logintype=request.getParameter("ltype");
			 System.out.println("-user--="+dyna.getString("userId")+"=-password--="+dyna.getString("password"));
			 if(!"".equals(StringUtility.checknull(dyna.getString("userId")))&& !"".equals(StringUtility.checknull(dyna.getString("password")))){
			 user=LoginService.getInstance().loginValidation(dyna.getString("userId"),dyna.getString("password"),logintype);
			 
			 if(user!=null){
				 if("M".equals(user.getProfile())){					 
					 if(user.isPasswordChanged()){
						 forword=mapping.findForward("profile");						 
					 }else{						 
					     forword=mapping.findForward("changePassword");
					 }
				 }else{						 
				     forword=mapping.findForward("profile");
				 }
				 HttpSession httpsession = request.getSession(false);
				 if(httpsession!=null){
		            httpsession.setAttribute("user", user);
				 }
			 }else{
				 errorCode="errors.invalid";
			 }
			 }else{
				 errorCode="login.required";
			 }
			if(!"".equals(errorCode)){		   		    	
				ActionMessages errors = new ActionMessages();  
				errors.add("",new ActionMessage(errorCode));
				saveErrors(request,errors);
				request.setAttribute("LoginType",logintype);
				forword=mapping.findForward("reLogin");
		    }
		}
		catch(Exception e){			
			log.error("LoginAction:loginValidation:Excpetion:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("login", new ActionMessage("login.Errors", e.getMessage()));
			saveErrors(request, errors);
			forword=mapping.getInputForward();
		}		
		
	   return forword;		
	}
	
	public ActionForward changePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		 DynaValidatorForm dyna = (DynaValidatorForm) form;
		LoginInfo loginInfo = new LoginInfo();
		ActionForward forword=null;
		try{
			loginInfo.setUserId(dyna.getString("userId"));
			loginInfo.setOldPassword(dyna.getString("oldPassword"));
			loginInfo.setNewPassword(dyna.getString("newPassword"));
			loginInfo.setConfirmPassword(dyna.getString("confirmPassword"));
			
			if(loginInfo.getNewPassword().equals(loginInfo.getConfirmPassword())){
				LoginService.getInstance().changePassword(loginInfo);			
				forword=mapping.findForward("profile");
			}
			else{
				ActionMessages errors = new ActionMessages();  
				errors.add("",new ActionMessage("errors.confirmPassword"));
				saveErrors(request,errors);
				forword=mapping.findForward("changePassword");
			}
		}
		catch(Exception e){			
			log.error("LoginAction:changePassword:Excpetion:"+e.getMessage());
			ActionMessages errors = new ActionMessages();  			
			saveErrors(request,errors);
			forword=mapping.getInputForward();
		}
		return forword;
	}
	
	public ActionForward changeAccountPassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		 DynaValidatorForm dyna = (DynaValidatorForm) form;
		LoginInfo loginInfo = new LoginInfo();
		ActionForward forword=null;
		try{
			loginInfo.setUserId(dyna.getString("userId"));
			loginInfo.setOldPassword(dyna.getString("oldPassword"));
			loginInfo.setNewPassword(dyna.getString("newPassword"));
			loginInfo.setConfirmPassword(dyna.getString("confirmPassword"));
			
			ActionMessages errors = new ActionMessages();  
			if(loginInfo.getNewPassword().equals(loginInfo.getConfirmPassword())){
				LoginService.getInstance().changePassword(loginInfo);
				errors.add("resetPassword", new ActionMessage("resetpassword.Errors", "Password Changed Successfully"));
				saveErrors(request, errors);
				forword=mapping.findForward("showAccount");
			}
			else{
				errors.add("",new ActionMessage("errors.confirmPassword"));
				saveErrors(request,errors);
				forword=mapping.findForward("incorrectPassword");
			}
		}
		catch(Exception e){			
			log.error("LoginAction:changePassword:Excpetion:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("resetPassword", new ActionMessage("resetpassword.Errors", e.getMessage()));
			saveErrors(request,errors);
			forword=mapping.findForward("incorrectPassword");
		}
		return forword;
	}
	public ActionForward logout(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		LoginInfo loginInfo = new LoginInfo();		 
		ActionForward forword=null; 
		
		HttpSession httpsession = request.getSession();
		loginInfo = (LoginInfo)httpsession.getAttribute("user");
		 httpsession = request.getSession(false);
			if (httpsession != null) {
				httpsession.invalidate();
			}  
			
			log.info("LoginAction::logout()--sessionId--"+httpsession.getId());
			log.info("User "+loginInfo.getUserName()+" Log Out Successfully");
			forword=mapping.findForward("sucess");
		 
		return forword;
	}
	
	
	
}
	
