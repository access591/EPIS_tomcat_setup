package com.epis.action.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.SmartListHelper;

import com.epis.bean.admin.UserBean;
import com.epis.info.login.LoginInfo;
import com.epis.services.admin.RoleService;
import com.epis.services.admin.UnitService;
import com.epis.services.admin.UserService;
import com.epis.utilities.Configurations;
import com.epis.utilities.Log;
import com.jspsmart.upload.SmartUpload;

public class UserAction extends DispatchAction {
	Log log = new Log(UserAction.class);

	public ActionForward search(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){		
		try {
			HttpSession session = request.getSession();
			session.setAttribute("unitList",UnitService.getInstance().getUnitList());	
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:search:Exception"+e.getMessage());
		}
		return mapping.findForward("search");
	}
	
	public ActionForward searchResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){
		
		try {
			DynaValidatorForm dynaForm = (DynaValidatorForm)form;
			UserBean user = new UserBean();
			user.setEmployeeId(dynaForm.getString("employeeId"));
			user.setUserName(dynaForm.getString("userName"));
			user.setUserType(dynaForm.getString("userType"));
			user.setProfileType(dynaForm.getString("profileType"));
			user.setUnit(dynaForm.getString("unit"));
			user.setStatus(dynaForm.getString("status"));			
			HttpSession session = request.getSession();
			LoginInfo info = (LoginInfo) session.getAttribute("user");
			request.setAttribute("userList",UserService.getInstance().getUserList(user,info.getUserId()));	
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:searchResult:Exception"+e.getMessage());
			e.printStackTrace();
		}
		return mapping.findForward("search");
	}
	
	public ActionForward delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){
		
		try {
			UserService.getInstance().delete(request.getParameter("userIds"));
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:delete:Exception"+e.getMessage());
		}
		return mapping.findForward("searchResult");
	}
	public ActionForward fwdtoNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){		
		try {
			HttpSession session = request.getSession();
			session.setAttribute("unitList",UnitService.getInstance().getUnitList());
			session.setAttribute("moduleList",UserService.getInstance().getModuleList());
			LoginInfo user = (LoginInfo)session.getAttribute("user");
			session.setAttribute("roleList",RoleService.getInstance().getRoles(user.getUserId()));
			request.setAttribute("Role",Configurations.getAccessRightsType());
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:fwdtoNew:Exception"+e.getMessage());
		}
		return mapping.findForward("new");
	}
	public ActionForward add(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){
		
			ActionForward forward = mapping.findForward("search");
			
		try {
			HttpSession session=request.getSession();
			LoginInfo loginInfo=(LoginInfo)session.getAttribute("user");
			String UserID=(String) loginInfo.getUserId();
			DynaValidatorForm dynaForm = (DynaValidatorForm)form;
			UserBean user = new UserBean();
			BeanUtils.copyProperties(user,dynaForm);	
			if("".equals(user.getUserId())){
				UserService.getInstance().add(user,UserID);
			}else {
				UserService.getInstance().edit(user,UserID);				
			}
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:add:Exception"+e.getMessage());
			forward = mapping.getInputForward();
		}
		return forward;
	}
	
	public ActionForward fwdtoEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){		
			ActionForward forward = null;		
		try {
			DynaValidatorForm dyna = (DynaValidatorForm)form;
			HttpSession session = request.getSession();
			session.setAttribute("unitList",UnitService.getInstance().getUnitList());
			session.setAttribute("moduleList",UserService.getInstance().getModuleList());
			LoginInfo userInfo = (LoginInfo)session.getAttribute("user");
			session.setAttribute("roleList",RoleService.getInstance().getRoles(userInfo.getUserId()));
			request.setAttribute("Role",Configurations.getAccessRightsType());
			UserBean user = UserService.getInstance().getUser(request.getParameter("userId"));			
			BeanUtils.copyProperties(dyna,user);	
			String path="";
			if(user.getEsignatoryName()!=null){
					path = getServlet().getServletContext().getRealPath("/")+"/uploads/dbf/"+user.getEsignatoryName();
					UserService.getInstance().getImage(path,user.getUserName());
			}
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:fwdtoEdit:Exception"+e.getMessage());
			forward = mapping.getInputForward();
		}
		forward = mapping.findForward("new");		
		return forward;
	}
	public ActionForward myAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response ){		
			ActionForward forward = null;		
		try {
			DynaValidatorForm dyna = (DynaValidatorForm)form;
			HttpSession session = request.getSession();
			session.setAttribute("unitList",UnitService.getInstance().getUnitList());
			session.setAttribute("moduleList",UserService.getInstance().getModuleList());
			LoginInfo userInfo = (LoginInfo)session.getAttribute("user");
			session.setAttribute("roleList",RoleService.getInstance().getRoles(userInfo.getUserId()));
			request.setAttribute("Role",Configurations.getAccessRightsType());
			UserBean user = UserService.getInstance().getuserAccount(userInfo.getUserId());			
			request.setAttribute("myaccount",user);
			} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("errors",new ActionMessage(e.getMessage()));
			saveErrors(request,errors);
			log.error("UserAction:fwdtoEdit:Exception"+e.getMessage());
			forward = mapping.getInputForward();
		}
		forward = mapping.findForward("showmyaccount");		
		return forward;
	}
	

}
