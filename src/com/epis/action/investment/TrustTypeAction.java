package com.epis.action.investment;

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
import com.epis.bean.investment.TrustTypeBean;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class TrustTypeAction extends DispatchAction {
	 
	 Log log=new Log(TrustTypeAction.class);
	
	 public ActionForward showTrustTypeAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){ 
	       return mapping.findForward("showtrustnew");
	 }
	
	 public ActionForward addTrustType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		  
		  DynaValidatorForm trust = (DynaValidatorForm)form;
		  TrustTypeBean bean = new TrustTypeBean(request);
		  ActionForward forward=mapping.findForward("trustsearch"); 
		  
		  try{
			  bean.setTrustType(StringUtility.checknull(trust.getString("trustType")));
			  bean.setDescription(StringUtility.checknull(trust.getString("description")));
			  TrustTypeService.getInstance().saveTrustType(bean);
			  StringBuffer newPath =
			        new StringBuffer(mapping.findForward("trustsearch").getPath());
			    newPath.append("&Success=S");
			    log.info("the path is"+newPath.toString());
			    forward= (new ActionForward(newPath.toString(), true));
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  forward = mapping.getInputForward();
			  log.error("TrustTypeAction:addTrustType:Exception"+e.getMessage());
		  }
		  
		 return forward; 
	 }
	 
	 public ActionForward searchTrustType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("showtrustsearch"); 
		 ActionMessages errors = new ActionMessages(); 
		 
		 try{
			   List trustList =null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   trustList = TrustTypeService.getInstance().searchTrustType(StringUtility.checknull(dyna.getString("trustType")));
			   request.setAttribute("trusttypeList",trustList);
			   if(StringUtility.checknull(request.getParameter("Success")).equals("S"))
			   {
				   
				   errors.add("success",new ActionMessage("quotation.Success","Record Saved Successfully"));
	    			saveErrors(request, errors);
			   }
		   }catch(Exception e){
			   
			   errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("TrustTypeAction:searchTrustType:Exception"+e.getMessage());
		   }
		   if(request.getParameter("error")!=null)
			  {
				  errors.add("quotation",new ActionMessage("trust.Errors",request.getParameter("error")));
	  			  saveErrors(request, errors);
			  }
	  	   return forward;
	 }
	 
	 public ActionForward showEditTrust(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		  
		   String trustcd= request.getParameter("trustcd");
		   TrustTypeBean bean = new TrustTypeBean();
		   ActionForward forward=mapping.findForward("showtrustedit"); 
		   
		   try{
			   bean=TrustTypeService.getInstance().findTrustType(trustcd);
			   request.setAttribute("trust",bean);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("TrustTypeAction:showEditTrust:Exception"+e.getMessage());
		   }
	  	   return forward;
	 }
	 
	 public ActionForward updateTrustType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		  DynaValidatorForm trust = (DynaValidatorForm)form;
		  TrustTypeBean bean = new TrustTypeBean(request);
		  ActionForward forward=mapping.findForward("trustsearch"); 
		 
		  try{
			  bean.setTrustCd(StringUtility.checknull(trust.getString("trustCd")));
			  bean.setTrustType(StringUtility.checknull(trust.getString("trustType")));
			  bean.setDescription(StringUtility.checknull(trust.getString("description")));
			  TrustTypeService.getInstance().editTrustType(bean);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  forward = mapping.getInputForward();
			  log.error("TrustTypeAction:updateTrustType:Exception"+e.getMessage());
		  }
		 return forward; 
	 }
	 
	 public ActionForward deleteTrustType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 
		 ActionForward forward=mapping.findForward("trustsearch"); 
		 
		 try{
			  String trustcds= request.getParameter("deleteall");
			  TrustTypeService.getInstance().deleteTrustType(trustcds);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  StringBuffer newPath =
			        new StringBuffer(mapping.findForward("trustsearch").getPath());
			    newPath.append("&error="+e.getMessage());
			    log.info("the path is"+newPath.toString());
			    forward= (new ActionForward(newPath.toString(), true));
		  }
		 return forward; 
	 }
}
