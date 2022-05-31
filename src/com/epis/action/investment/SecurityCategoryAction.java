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
import com.epis.bean.investment.SecurityCategoryBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class SecurityCategoryAction extends DispatchAction{
	 Log log=new Log(SecurityCategoryAction.class);
	 int size =0;
	 
	 public ActionForward showSecurityCategoryAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 try {
			List accHeadList = SecurityCategoryService.getInstance().getAccountHeads();
			request.setAttribute("accHeadList",accHeadList);
		} catch (ServiceNotAvailableException e) {
			e.printStackTrace();
		} catch (EPISException e) {
			
			e.printStackTrace();
		}
	       return mapping.findForward("showcategorynew");
	 }
	 
	 public ActionForward addSecurityCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		  DynaValidatorForm category = (DynaValidatorForm)form;
		  SecurityCategoryBean bean = new SecurityCategoryBean(request);
		  ActionForward forward=mapping.findForward("categorysearch"); 
		  
		  try{
			  bean.setCategoryCd(StringUtility.checknull(category.getString("categoryCd")));
			  bean.setDescription(StringUtility.checknull(category.getString("description")));
			  bean.setInvestbaseamt(StringUtility.checknull(category.getString("investbaseamt")));
			  bean.setOpeningbal(StringUtility.checknull(category.getString("openingbal")));
			  bean.setInvestinterest(StringUtility.checknull(category.getString("investinterest")));
			  SecurityCategoryService.getInstance().saveCategory(bean);
			  StringBuffer newPath =
			        new StringBuffer(mapping.findForward("categorysearch").getPath());
			    newPath.append("&Success=S");
			     forward= (new ActionForward(newPath.toString(), true));
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("category",new ActionMessage("category.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  forward = mapping.getInputForward();
			  log.error("SecurityCategoryAction:addSecurityCategory:Exception"+e.getMessage());
		  }
		  	List accHeadList;
			try {
				accHeadList = SecurityCategoryService.getInstance().getAccountHeads();
				request.setAttribute("accHeadList",accHeadList);
			} catch (ServiceNotAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EPISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 return forward;
	 }
	 public ActionForward searchSCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("showcategorysearch"); 
		 ActionMessages errors = new ActionMessages(); 
		 
		 try{
			   List categoryList =null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   categoryList = SecurityCategoryService.getInstance().searchCategory(StringUtility.checknull(dyna.getString("categoryCd")));
			   size = categoryList.size();
			   request.setAttribute("categoryList",categoryList);
			   if(StringUtility.checknull(request.getParameter("Success")).equals("S"))
			   {
				   
				   errors.add("success",new ActionMessage("quotation.Success","Record Saved Successfully"));
	    			saveErrors(request, errors);
			   }
		   }catch(Exception e){
			   
			   errors.add("category",new ActionMessage("category.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("SecurityCategoryAction:searchSCategory:Exception"+e.getMessage());
		   }
		   if(request.getParameter("error")!=null)
			  {
				  errors.add("quotation",new ActionMessage("category.Errors",request.getParameter("error")));
	  			  saveErrors(request, errors);
			  }
	  	   return forward;
	 }
	 public ActionForward showEditSecurityCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 
		   String categoryid= request.getParameter("categoryid");
		   SecurityCategoryBean bean = new SecurityCategoryBean();
		   ActionForward forward=mapping.findForward("showcategoryedit"); 
		   
		   try{
			   bean=SecurityCategoryService.getInstance().findSecurityCategory(categoryid);
			   request.setAttribute("category",bean);
			   List accHeadList = SecurityCategoryService.getInstance().getAccountHeads();
				request.setAttribute("accHeadList",accHeadList);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("category",new ActionMessage("category.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("SecurityCategoryAction:showEditSecurityCategory:Exception"+e.getMessage());
		   }
	  	   return forward;
	 }
	 public ActionForward updateSecurityCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 DynaValidatorForm category = (DynaValidatorForm)form;
		 SecurityCategoryBean bean = new SecurityCategoryBean(request);
		 ActionForward forward=mapping.findForward("categorysearch"); 
		 
		  try{
			  bean.setCategoryId(StringUtility.checknull(category.getString("categoryId")));
			  bean.setCategoryCd(StringUtility.checknull(category.getString("categoryCd")));
			  bean.setInvestbaseamt(StringUtility.checknull(category.getString("investbaseamt")));
			  bean.setOpeningbal(StringUtility.checknull(category.getString("openingbal")));
			  bean.setInvestinterest(StringUtility.checknull(category.getString("investinterest")));
			  bean.setDescription(StringUtility.checknull(category.getString("description")));
			  SecurityCategoryService.getInstance().editCategory(bean);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("category",new ActionMessage("category.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  forward = mapping.getInputForward();
			  log.error("SecurityCategoryAction:updateSecurityCategory:Exception"+e.getMessage());
		  }
		 return forward; 
	 }
	 public ActionForward deleteSecurityCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("categorysearch"); 
		 
		 try{
			  String categorycds= request.getParameter("deleteall");
			  SecurityCategoryService.getInstance().deleteCategory(categorycds);
		  }catch(Exception e){
			  ActionMessages errors = new ActionMessages(); 
			  errors.add("category",new ActionMessage("category.Errors", e.getMessage()));
			  saveErrors(request,errors);
			  log.error("SecurityCategoryAction:deleteSecurityCategory:Exception"+e.getMessage());
			  StringBuffer newPath =
			        new StringBuffer(mapping.findForward("categorysearch").getPath());
			    newPath.append("&error="+e.getMessage());
			    log.info("the path is"+newPath.toString());
			    forward= (new ActionForward(newPath.toString(), true));
		  }
		 return forward; 
	 }
}
