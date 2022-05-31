package com.epis.action.investment;

import java.io.PrintWriter;
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
import com.epis.bean.investment.AmountTypeBean;
import com.epis.services.cashbook.BRStatementService;
import com.epis.services.investment.AmountTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;



public class AmountTypeAction extends DispatchAction {
	Log log=new Log(AmountTypeAction.class);
public ActionForward partyDetails(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	
	ActionForward forward=am.findForward("amountType");
	log.info("this action method is calling...");
	DynaValidatorForm amounttypeform=(DynaValidatorForm)af;
	try{
	List amounttypelist=AmountTypeService.getInstance().getAmountTypeList();
	List partydetails=AmountTypeService.getInstance().PartyDetails();
	req.setAttribute("amounttypelist",amounttypelist);
	req.setAttribute("partydetails",partydetails);
	}
	catch(Exception e)
	{
		ActionMessages errors=new ActionMessages();
		errors.add("amounttype",new ActionMessage("amounttype.errors",e.getMessage()));
		saveErrors(req,errors);
		log.error("Amount type action in Party Details:exception"+e.getMessage());
		
	}
	return forward;
	
	
}
public ActionForward updatePartyDetails(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	AmountTypeBean bean=new AmountTypeBean(req);
	
	 bean.setEmppartycode(StringUtility.checknull(req.getParameter("securityName")));
	 bean.setAmountdate(StringUtility.checknull(req.getParameter("amountdate")));
	 bean.setCredit(StringUtility.checknull(req.getParameter("amount")));
	 bean.setAmounttype(StringUtility.checknull(req.getParameter("amounttype")));
	 bean.setRowid(StringUtility.checknull(req.getParameter("rowvalue")));
	 log.info("the row value is"+bean.getAmountdate());
	 log.info("the amount type is"+bean.getEmppartycode());
	 
	 try{
		 String message=AmountTypeService.getInstance().updatePartyDetails(bean);
		 res.setContentType("text/xml");
		 res.setHeader("Pragma","no-cache");//for HTTP1.0
		 res.setHeader("cache-control","no-cache");// for HTTP/1.1.
	     PrintWriter out = res.getWriter();  
	     out.write(message);
		 
	 }
	 catch(Exception e)
	 {
		 log.error("the Exception in savePartyDetails"+e.getMessage());
	 }
	return null;
}
public ActionForward showPartyAmountTypeAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("addNew");
	DynaValidatorForm amounttypeform=(DynaValidatorForm)af;
	try{
		List SecurityNamesList=AmountTypeService.getInstance().getSecurityList();
		List amounttypelist=AmountTypeService.getInstance().getAmountTypeList();
		req.setAttribute("amounttypelist",amounttypelist);
		req.setAttribute("securityList",SecurityNamesList);
		
	}
	catch(Exception e)
	{
		ActionMessages errors=new ActionMessages();
		errors.add("amounttype",new ActionMessage("amounttype.errors",e.getMessage()));
		saveErrors(req,errors);
		log.error("Amount type action in Party Details:exception"+e.getMessage());
	}
	
	
	return forward;
}
public ActionForward savePartyAmount(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	log.info("this is savepartyamount method");
	ActionForward forward=am.findForward("showpartyamount");
	DynaValidatorForm amounttypeform=(DynaValidatorForm)af;
	AmountTypeBean bean=new AmountTypeBean(req);
	List SecurityNamesList=null;
	List amounttypelist=null;
	try{
		SecurityNamesList=AmountTypeService.getInstance().getSecurityList();
		amounttypelist=AmountTypeService.getInstance().getAmountTypeList();
		bean.setAchieveDetails(amounttypeform.getStrings("achieveDetails"));
		AmountTypeService.getInstance().savePartyDetails(bean);
	
	}
	catch(Exception e)
	{
		ActionMessages errors=new ActionMessages();
		errors.add("amounttype",new ActionMessage("amounttype.errors",e.getMessage()));
		saveErrors(req,errors);
		log.error("Amount type action in Party Details:exception"+e.getMessage());
		req.setAttribute("amounttypelist",amounttypelist);
		req.setAttribute("securityList",SecurityNamesList);
		forward = am.getInputForward();
	}
	return forward;
}

}
