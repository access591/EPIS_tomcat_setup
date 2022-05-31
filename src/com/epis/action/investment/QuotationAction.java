package com.epis.action.investment;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.investment.QuotationBean;
import com.epis.info.login.LoginInfo;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.QuotationService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class QuotationAction extends DispatchAction{
	
	Log log=new Log(QuotationAction.class);
    public ActionForward showQuotationAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
    	ActionForward forward = mapping.findForward("showquotationnew");
    	List quotationRequestList=null;
    	List typeOfCallList=null;
    	List investmenttypeList=null;
    	List guarenteetypeList=null;
    	List investmentModeList=null;
    	List interestDatesList=null;
    	List interestMonthsList=null;
    	List putCallList=null;
    	List purchasePriseList=null;
    	ActionMessages errors = new ActionMessages();
    	
    	try{
    		quotationRequestList=QuotationRequestService.getInstance().getQuotationRequests();
    		typeOfCallList=QuotationService.getInstance().gettypeOfCalls();
    		investmenttypeList=QuotationService.getInstance().getinvestmentTypes();
    		interestDatesList=QuotationService.getInstance().getinterestDates();
    		interestMonthsList=QuotationService.getInstance().getinterestMonths();
    		guarenteetypeList=QuotationService.getInstance().getGuarenteTypes();
    		investmentModeList=QuotationService.getInstance().getInvestmentModes();
    		putCallList=QuotationService.getInstance().getPutCallAnnualize();
    		purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
    		request.setAttribute("quotationRequestList",quotationRequestList);
    		request.setAttribute("typeOfCallList",typeOfCallList);
    		request.setAttribute("investmenttypeList",investmenttypeList);
    		request.setAttribute("guarenteetypeList",guarenteetypeList);
    		request.setAttribute("investmentModeList",investmentModeList);
    		request.setAttribute("putCallList",putCallList);
    		request.setAttribute("purchasePriseList",purchasePriseList);
    		request.setAttribute("interestDatesList",interestDatesList);
    		request.setAttribute("interestMonthsList",interestMonthsList);
    		
    		
    		
    		if(StringUtility.checknull(request.getParameter("Success")).equals("S"))
    		{
    			
    			errors.add("success",new ActionMessage("quotation.Success","Record Saved Successfully"));
    			saveErrors(request, errors);
    		}
    		
    		
    	}
    	catch (Exception e) {
			log.error("QuotationAction:addQuotation:Exception:"+e.getMessage());
			
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			
			forward = mapping.getInputForward();
		}
		
		return forward;
	}

	public ActionForward addQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)			 {
		
		DynaValidatorForm quotation = (DynaValidatorForm) form;
		QuotationBean bean = new QuotationBean(request);
		ActionForward forward = mapping.findForward("quotationsearch");
		
		try {
			LoginInfo loginInfo=(LoginInfo)request.getSession().getAttribute("user");
			BeanUtils.copyProperties(bean,quotation) ;	
			QuotationService.getInstance().saveQuotation(bean);
			request.setAttribute("Success","S");
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("quotationsearch").getPath());
		    newPath.append("&Success=S");
		    forward= (new ActionForward(newPath.toString(), true));
			
		} catch (Exception e) {
			log.error("QuotationAction:addQuotation:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("quotationsearch").getPath());
		    
		    forward= (new ActionForward(newPath.toString(), false));
			
		}
		 
		return forward;
	}

	public ActionForward searchQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List trustList=null;
		  List securityList=null;
		
		ActionForward forward =mapping.findForward("showquotationsearch");
		ActionMessages errors = new ActionMessages();
		try {
			List quotationList = null;			
			DynaValidatorForm dyna = (DynaValidatorForm) form;	
			QuotationBean bean = new QuotationBean();
			BeanUtils.copyProperties(bean,dyna);
			quotationList = QuotationService.getInstance().searchQuotation(bean.getLetterNo(),bean.getSecurityName(),bean.getTrustName(),bean.getSecurityCategory());
			securityList=SecurityCategoryService.getInstance().getSecurityCategories();
			trustList=TrustTypeService.getInstance().getTrustTypes();
			request.setAttribute("quotationList", quotationList);
			request.setAttribute("trustList",trustList);
			request.setAttribute("securityList",securityList);
			
		} catch (Exception e) {			
			log.error("ApprovalsAction:searchOption:Exception:"+e.getMessage());
			errors.add("approvals", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		if(request.getParameter("error")!=null)
		  {
			  errors.add("quotation",new ActionMessage("quotation.Errors",request.getParameter("error")));
			  saveErrors(request, errors);
		  }
		return forward;
	}

	public ActionForward showEditQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String quotationcode = request.getParameter("quotationcode");
		ActionForward forward =mapping.findForward("showquotationedit");
		
    	List typeOfCallList=null;
    	List investmenttypeList=null;
    	List guarenteetypeList=null;
    	List investmentModeList=null;
    	List putCallList=null;
    	List purchasePriseList=null;
    	List interestDatesList=null;
    	List interestMonthsList=null;
		try {
			
    		typeOfCallList=QuotationService.getInstance().gettypeOfCalls();
    		investmenttypeList=QuotationService.getInstance().getinvestmentTypes();
    		guarenteetypeList=QuotationService.getInstance().getGuarenteTypes();
    		investmentModeList=QuotationService.getInstance().getInvestmentModes();
    		putCallList=QuotationService.getInstance().getPutCallAnnualize();
    		purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
    		interestDatesList=QuotationService.getInstance().getinterestDates();
    		interestMonthsList=QuotationService.getInstance().getinterestMonths();
    		request.setAttribute("typeOfCallList",typeOfCallList);
    		request.setAttribute("investmenttypeList",investmenttypeList);
    		request.setAttribute("guarenteetypeList",guarenteetypeList);
    		request.setAttribute("investmentModeList",investmentModeList);
    		request.setAttribute("putCallList",putCallList);
    		request.setAttribute("purchasePriseList",purchasePriseList);
    		request.setAttribute("interestDatesList",interestDatesList);
    		request.setAttribute("interestMonthsList",interestMonthsList);
			QuotationBean bean = new QuotationBean();			
		   bean = QuotationService.getInstance().findQuotation(quotationcode);		
		    request.setAttribute("quotation", bean);
		} catch (Exception e) {
			log.error("QuotationAction:showEditQuotation:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm quotation = (DynaValidatorForm) form;
		QuotationBean bean = new QuotationBean(request);
		ActionForward forward =mapping.findForward("quotationsearch");
		
		try {
			BeanUtils.copyProperties(bean,quotation);	
			QuotationService.getInstance().editQuotation(bean);
		} catch (Exception e) {
			log.error("QuotationAction:updateQuotation:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("quotation", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("the action is....deletequotation...");
		ActionForward forward =mapping.findForward("quotationemptysearch");
		
		try {
			String quotationcds = request.getParameter("deleteall");
			QuotationService.getInstance().deleteQuotation(quotationcds);
		} catch (Exception e) {
			log.error("QuotationAction:deleteQuotation:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(request, errors);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("quotationemptysearch").getPath());
		    newPath.append("&error="+e.getMessage());
		    log.info("the path is"+newPath.toString());
		    forward= (new ActionForward(newPath.toString(), true));
		}
		
		return forward;
	}
	public ActionForward generateQuotationReport(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=mapping.findForward("quotationreport");
		try{
			String quotationCd=req.getParameter("quotationCd");
			QuotationBean bean = new QuotationBean();	
			bean=QuotationService.getInstance().findQuotation(quotationCd);
			req.setAttribute("quotation",bean);
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
			saveErrors(req, errors);
			
			forward = mapping.getInputForward();
		}
		return forward;
	}
	 
	 
	 
}
