package com.epis.action.investment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

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
import com.epis.bean.investment.RatioBean;
import com.epis.services.investment.RatioService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class RatioAction extends DispatchAction{
	Log log=new Log(RatioAction.class);
	 public ActionForward showRatioAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActionForward forward=mapping.findForward("showrationew"); 
		 HttpSession session = request.getSession();
		 try{
		    List secList = RatioService.getInstance().searchCategory();
		    session.setAttribute("secList",secList);
		 } catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
	       return forward;
	  }
	 
	 public ActionForward showCurrentRatio(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActionForward forward= null; 
		 HttpSession session = request.getSession();
		 RatioBean bean = new RatioBean();
		
		 try{
		    bean= RatioService.getInstance().getCurrentRatio();
		    session.setAttribute("ratiobean",bean);
		 } catch(Exception e){
			 System.out.println(e.getMessage());
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		 if(request.getParameter("mode")!=null && request.getParameter("mode").equals("E"))
			 forward = mapping.findForward("showratioedit");
			 else 
				 forward =mapping.findForward("showcurrentratio");
	       return forward;
	  }
	 public ActionForward addRatio(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 DynaValidatorForm ratio = (DynaValidatorForm)form;
		  RatioBean bean = new RatioBean(request);
		  UtilityBean util = null;
		  ActionForward forward=mapping.findForward("ratiosearch"); 
		  String element="";
		  try{
			Enumeration details = request.getParameterNames();
			ArrayList array = new ArrayList();
			while(details.hasMoreElements()){
				element=(String)details.nextElement();
				if(element.indexOf("SEC")!=-1){
					util = new UtilityBean();
					util.setKey(element.substring(3,element.length()));
					if(request.getParameter(element).equals(""))
						util.setValue("0");
					else		
						util.setValue(request.getParameter(element));
						array.add(util);
				    }
			}
			  bean.setValidFrom(StringUtility.checknull(ratio.getString("validFrom")));
			  bean.setRatioList(array);
			  RatioService.getInstance().saveRatio(bean);	
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
				System.out.println(e.getMessage());
		  }
		 return forward; 
		 }
	 public ActionForward searchRatio(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("showratiosearch"); 
		 try{
			   List ratioList =null;
			   HashMap map = null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   ratioList = RatioService.getInstance().searchRatio(StringUtility.checknull(dyna.getString("validFrom")));
			   map = RatioService.getInstance().getCategories();
			   request.setAttribute("ratioList",ratioList);
			   request.setAttribute("categories",map);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		   }
	  	   return forward;
		 }
	
	 public ActionForward updateRatio(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 DynaValidatorForm ratio = (DynaValidatorForm)form;
		 RatioBean bean = new RatioBean(request);
		 ActionForward forward=mapping.findForward("ratiosearch"); 
			  String element="";
			  try{
				UtilityBean util = null;
				Enumeration details = request.getParameterNames();
				ArrayList array = new ArrayList();
				while(details.hasMoreElements()){
					element=(String)details.nextElement();
					if(element.indexOf("SEC")!=-1){
						util = new UtilityBean();
						util.setKey(element.substring(3,element.length()));
						
						if(request.getParameter(element).equals(""))
							util.setValue("0");
						else		
							util.setValue(request.getParameter(element));
							array.add(util);
					    }
				}
				 
				  bean.setRatioCd(StringUtility.checknull(ratio.getString("ratioCd")));
				  bean.setRatioList(array);
				  RatioService.getInstance().editRatio(bean);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		   return forward; 
		 
		 }
	 public ActionForward deleteRatio(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActionForward forward=mapping.findForward("ratiosearch"); 
		 try{
			  String ratiocd= request.getParameter("ratiocd");
			  RatioService.getInstance().deleteRatio(ratiocd);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		 return forward; 
		 }
	   

	
}
