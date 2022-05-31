/**
 * @author anilkumark
 *
 */
package com.epis.action.admin;

	

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import com.epis.services.admin.CommonMenueService;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import java.util.*;



public class CommonMenueAction extends DispatchAction {
	Log log=new Log(CommonMenueAction.class);
	 
	
	 public ActionForward showMenue(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("menueitems"); 
		 String moduleCD=request.getParameter("mod");
		 log.info("hi anil action::"+moduleCD);
		 HttpSession session=request.getSession();
		 LoginInfo lbean=(LoginInfo) session.getAttribute("user");
		 String userName=(String) lbean.getUserName();
		 String rolecd=lbean.getRoleCd();
		 
		 try{
			   Map menueList =null;
			   menueList = CommonMenueService.getInstance().getMenueItems(StringUtility.checknull(userName),StringUtility.checknull(moduleCD),StringUtility.checknull(rolecd));
			   request.setAttribute("menuelist", menueList);
			  //System.out.println("hi anil:;"+menueList.size());
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("screen",new ActionMessage("screen.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		   }
	  	   return forward;
		 }
}
