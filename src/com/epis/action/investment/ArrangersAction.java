package com.epis.action.investment;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.investment.ArrangersBean;
import com.epis.services.investment.ArrangersService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ArrangersAction extends DispatchAction{
	 Log log=new Log(ArrangersAction.class);
	 
	 public ActionForward showArrangersAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		    
		 HttpSession session = request.getSession();
		  ActionForward forward=mapping.findForward("showarrangersnew"); 
		 try{
			 List searchlist  = ArrangersService.getInstance().searchCategory();
			 session.setAttribute("categorylist",searchlist);
		 }catch(Exception e){
			 ActionMessages errors = new ActionMessages(); 
			 errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			 saveErrors(request,errors);
			 forward = mapping.getInputForward();
			 log.error("ArrangersAction:showArrangersAdd:Exception"+e.getMessage());
		  }
	     return forward;
	 }
	 public ActionForward addArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		  DynaValidatorForm dyna = (DynaValidatorForm)form;
		  ArrangersBean bean = null;
		  ActionForward forward=mapping.findForward("arrangerssearch"); 
		  
		  try{
			   bean = new ArrangersBean(request);
			  /* bean.setArrangerName(StringUtility.checknull(dyna.getString("arrangerName")));	
			   bean.setCategoryIds(StringUtility.checknull(dyna.getString("categoryIds")));	
			   bean.setRegOffAddr(StringUtility.checknull(dyna.getString("regOffAddr")));	
			   bean.setRegPhoneNo(StringUtility.checknull(dyna.getString("regPhoneNo")));	
			   bean.setRegFaxNo(StringUtility.checknull(dyna.getString("regFaxNo")));	
			   bean.setDelhiOffAddr(StringUtility.checknull(dyna.getString("delhiOffAddr")));	
			   bean.setDelhiOffPhNo(StringUtility.checknull(dyna.getString("delhiOffPhNo")));	
			   bean.setDelhiOffFaxNo(StringUtility.checknull(dyna.getString("delhiOffFaxNo")));	
			   bean.setNameOfHeadOfDelhiOff(StringUtility.checknull(dyna.getString("nameOfHeadOfDelhiOff")));
			   bean.setDelhiHeadOffMobileNo(StringUtility.checknull(dyna.getString("delhiHeadOffMobileNo")));
			   bean.setDelhiHeadOffPhNo(StringUtility.checknull(dyna.getString("delhiHeadOffPhNo")));
			   bean.setPramotorname(StringUtility.checknull(dyna.getString("pramotorname")));
			   bean.setPramotorContactNo(StringUtility.checknull(dyna.getString("pramotorContactNo")));
			   bean.setPramotorEmail(StringUtility.checknull(dyna.getString("pramotorEmail")));
			   bean.setNetworthAmount(StringUtility.checknull(dyna.getString("networthAmount")));
			   bean.setNetworthAsOn(StringUtility.checknull(dyna.getString("networthAsOn")));
			   bean.setEmail(StringUtility.checknull(dyna.getString("email")));
			   bean.setRegwithsebi(StringUtility.checknull(dyna.getString("regwithsebi")));
			   bean.setSebivaliddate(StringUtility.checknull(dyna.getString("sebivaliddate")));
			   bean.setRegwithbse(StringUtility.checknull(dyna.getString("regwithbse")));
			   bean.setBsevaliddate(StringUtility.checknull(dyna.getString("bsevaliddate")));
			   bean.setRegwithnse(StringUtility.checknull(dyna.getString("regwithnse")));
			   bean.setNsevaliddate(StringUtility.checknull(dyna.getString("nsevaliddate")));
			   bean.setRegwithrbi(StringUtility.checknull(dyna.getString("regwithrbi")));
			   bean.setRbivaliddate(StringUtility.checknull(dyna.getString("rbivaliddate")));
			   bean.setRemarks(StringUtility.checknull(dyna.getString("remarks")));
			   bean.setStatus(StringUtility.checknull(dyna.getString("status")));*/
			   BeanUtils.copyProperties(bean,dyna);
			   ArrangersService.getInstance().saveArrangers(bean);
			   StringBuffer newPath =
			        new StringBuffer(mapping.findForward("arrangerssearch").getPath());
			    newPath.append("&Success=S");
			     forward= (new ActionForward(newPath.toString(), true));
			   
		 
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
				log.error("ArrangersAction:addArrangers:Exception"+e.getMessage());
		  }
		  
		 return forward; 
	 }
	 public ActionForward searchArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("showarrangerssearch"); 
		 ActionMessages errors = new ActionMessages(); 
		 ArrangersBean bean = new ArrangersBean();
		 
		 try{  
			   List arrangerList =null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   bean.setArrangerName(StringUtility.checknull(dyna.getString("arrangerName")));
			   bean.setCategoryIds(StringUtility.checknull(dyna.getString("categoryIds")));
			   List searchlist  = ArrangersService.getInstance().searchCategory();
			   HttpSession session = request.getSession();		
			   arrangerList = ArrangersService.getInstance().searchArrangers(bean);
			   session.setAttribute("categorylist",searchlist);
			   request.setAttribute("arrangerList",arrangerList);
			   if(StringUtility.checknull(request.getParameter("Success")).equals("S"))
			   {
				   
				   errors.add("success",new ActionMessage("quotation.Success","Record Saved Successfully"));
	    			saveErrors(request, errors);
			   }
		   }catch(Exception e){
			   
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
		   }
		   if(request.getParameter("error")!=null)
			  {
				  errors.add("quotation",new ActionMessage("arranger.Errors",request.getParameter("error")));
	  			  saveErrors(request, errors);
			  }
	  	   return forward;
	 }
	 public ActionForward showEditArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		   
		   String arrangerCd= request.getParameter("arrangerCd");
		   DynaValidatorForm dyna = (DynaValidatorForm)form;
		   ArrangersBean bean = new ArrangersBean();
		   ActionForward forward=mapping.findForward("showarrangersedit");
		   
		   try{
			   bean=ArrangersService.getInstance().findArrangers(arrangerCd);
			   request.setAttribute("arranger",bean);
			   BeanUtils.copyProperties(dyna,bean);
		   }catch(Exception e){
		       ActionMessages errors = new ActionMessages(); 
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("ArrangersAction:showEditArrangers:Exception"+e.getMessage());
		   }
		  
	  	   return forward;
	 }
	 public ActionForward updateArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		  DynaValidatorForm dyna = (DynaValidatorForm)form;
		  ArrangersBean bean = null;
		  ActionForward forward=mapping.findForward("arrangerssearch"); 
		  
		  try{
			   bean = new ArrangersBean(request);
			/*   bean.setArrangerCd(StringUtility.checknull(dyna.getString("arrangerCd")));
			   bean.setArrangerName(StringUtility.checknull(dyna.getString("arrangerName")));		   
			   bean.setCategoryIds(StringUtility.checknull(dyna.getString("categoryIds")));	
			   bean.setRegOffAddr(StringUtility.checknull(dyna.getString("regOffAddr")));	
			   bean.setRegPhoneNo(StringUtility.checknull(dyna.getString("regPhoneNo")));	
			   bean.setRegFaxNo(StringUtility.checknull(dyna.getString("regFaxNo")));	
			   bean.setDelhiOffAddr(StringUtility.checknull(dyna.getString("delhiOffAddr")));	
			   bean.setDelhiOffPhNo(StringUtility.checknull(dyna.getString("delhiOffPhNo")));	
			   bean.setDelhiOffFaxNo(StringUtility.checknull(dyna.getString("delhiOffFaxNo")));	
			   bean.setNameOfHeadOfDelhiOff(StringUtility.checknull(dyna.getString("nameOfHeadOfDelhiOff")));	
			   bean.setDelhiHeadOffPhNo(StringUtility.checknull(dyna.getString("delhiHeadOffPhNo")));
			   bean.setDelhiHeadOffMobileNo(StringUtility.checknull(dyna.getString("delhiHeadOffMobileNo")));
			   bean.setPramotorname(StringUtility.checknull(dyna.getString("pramotorname")));
			   bean.setPramotorContactNo(StringUtility.checknull(dyna.getString("pramotorContactNo")));
			   bean.setPramotorEmail(StringUtility.checknull(dyna.getString("pramotorEmail")));
			   bean.setNetworthAmount(StringUtility.checknull(dyna.getString("networthAmount")));
			   bean.setNetworthAsOn(StringUtility.checknull(dyna.getString("networthAsOn")));
			   bean.setEmail(StringUtility.checknull(dyna.getString("email")));
			   bean.setRegwithsebi(StringUtility.checknull(dyna.getString("regwithsebi")));
			   bean.setSebivaliddate(StringUtility.checknull(dyna.getString("sebivaliddate")));
			   bean.setRegwithbse(StringUtility.checknull(dyna.getString("regwithbse")));
			   bean.setBsevaliddate(StringUtility.checknull(dyna.getString("bsevaliddate")));
			   bean.setRegwithnse(StringUtility.checknull(dyna.getString("regwithnse")));
			   bean.setNsevaliddate(StringUtility.checknull(dyna.getString("nsevaliddate")));
			   bean.setRegwithrbi(StringUtility.checknull(dyna.getString("regwithrbi")));
			   bean.setRbivaliddate(StringUtility.checknull(dyna.getString("rbivaliddate")));
			   bean.setRemarks(StringUtility.checknull(dyna.getString("remarks")));
			   bean.setStatus(StringUtility.checknull(dyna.getString("status")));*/ 
			   BeanUtils.copyProperties(bean,dyna);
		       ArrangersService.getInstance().editArrangers(bean);
		  }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("ArrangersAction:updateArrangers:Exception"+e.getMessage());
		  }
		 return forward; 
	 }
	 public ActionForward deleteArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("arrangerssearch"); 
		 
		 try{
			  String arrangercds= request.getParameter("deleteall");
			  ArrangersService.getInstance().deleteArrangers(arrangercds);
		 }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			  saveErrors(request,errors);
			 log.error("ArrangersAction:deleteArrangers:Exception"+e.getMessage());
			 StringBuffer newPath =
			        new StringBuffer(mapping.findForward("arrangerssearch").getPath());
			    newPath.append("&error="+e.getMessage());
			    log.info("the path is"+newPath.toString());
			    forward= (new ActionForward(newPath.toString(), true));
		 }
		 return forward; 
     }
public ActionForward paramArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("arrangersparam"); 
		 ArrangersBean bean = new ArrangersBean();
		 
		 try{  
			   
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   bean.setArrangerName(StringUtility.checknull(dyna.getString("arrangerName")));
			   bean.setCategoryIds(StringUtility.checknull(dyna.getString("categoryIds")));
			   List searchlist  = ArrangersService.getInstance().searchCategory();
			   HttpSession session = request.getSession();		
			   session.setAttribute("categorylist",searchlist);
			  
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
		   }
	  	   return forward;
	 }
public ActionForward reportArrangers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("arrangersreport"); 
	 ArrangersBean bean = new ArrangersBean();
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	 try{  
		 if("excel".equals(reportType)){
			 forward=mapping.findForward("arrangersreportExcel");
		 }
		   List arrangerList =null;
		   bean.setArrangerName(StringUtility.checknull(request.getParameter("arrangerName")));
		   bean.setCategoryIds(StringUtility.checknull(request.getParameter("categoryIds")));
		  arrangerList = ArrangersService.getInstance().searchArrangers(bean);
		  request.setAttribute("arrangerList",arrangerList);
	   }catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
	   }
 	   return forward;
}
}
