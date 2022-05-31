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

import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.investment.InvestmentMadeBean;
import com.epis.dao.investment.InvestmentMadeDao;
import com.epis.services.investment.InvestmentMadeService;

public class InvestmentMadeAction extends DispatchAction {
	Log log=new Log(InvestmentMadeAction.class);
	public ActionForward searchinvestMade(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showinvestmentMadeSearch");
		InvestmentMadeBean bean=new InvestmentMadeBean();
		List investmentmadeList=null;
		DynaValidatorForm investmade=(DynaValidatorForm)af;
		try{
			bean.setSecurityCategory(StringUtility.checknull(investmade.getString("securityCategory")));
			bean.setTrustType(StringUtility.checknull(investmade.getString("trustType")));
			investmentmadeList=InvestmentMadeService.getInstance().searchinvestMade(bean);
			req.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());
			req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories());
			req.setAttribute("investmentmadeList",investmentmadeList);
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:searchinvestMade:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			return am.getInputForward();
		}
		
		return forward;
	}
	public ActionForward showInvestmentMadeNew(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showinvestmentMadeNew");
		try{
			req.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());
			req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories());
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:showInvestmentMadeNew:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			
			saveErrors(req, errors);
			return am.getInputForward();
		}
		
		return forward;
	}
	public ActionForward addinvestmentMade(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("investmentMadeSearch");
		List trustRecords=null;
		List categoryRecords=null;
		DynaValidatorForm investmade=(DynaValidatorForm)af;
		InvestmentMadeBean bean=new InvestmentMadeBean(req);
		try{
			trustRecords=TrustTypeService.getInstance().getTrustTypes();
			categoryRecords=SecurityCategoryService.getInstance().getSecurityCategories();
			bean.setTrustType(StringUtility.checknull(investmade.getString("trustType")));
			bean.setSecurityCategory(StringUtility.checknull(investmade.getString("securityCategory")));
			bean.setInvAmount(StringUtility.checknull(investmade.getString("invAmount")));
			bean.setAsOnDate(StringUtility.checknull(investmade.getString("asOnDate")));
			InvestmentMadeService.getInstance().addinvestmentMade(bean);
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:showInvestmentMadeNew:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			req.setAttribute("trustRecords",trustRecords);
			req.setAttribute("categoryRecords",categoryRecords);
			saveErrors(req, errors);
			return am.getInputForward();
		}
		return forward;
	}
	public ActionForward showEditInvestmentMade(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showinvestmentMadeEdit");
		DynaValidatorForm investmade=(DynaValidatorForm)af;
		InvestmentMadeBean bean=new InvestmentMadeBean();
		try{
			bean.setInvestmentMadeCd(StringUtility.checknull(investmade.getString("investmentMadeCd")));
			bean=InvestmentMadeService.getInstance().findInvestmentMade(bean);
			req.setAttribute("investmadebean",bean);
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:showEditInvestmentMade:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			return am.getInputForward();
		}
		return forward;
	}
	public ActionForward updateinvestmentMade(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("investmentMadeSearch");
		DynaValidatorForm investmade=(DynaValidatorForm)af;
		InvestmentMadeBean bean=new InvestmentMadeBean(req);
		try{
				bean.setInvestmentMadeCd(StringUtility.checknull(investmade.getString("investmentMadeCd")));
				bean.setInvAmount(StringUtility.checknull(investmade.getString("invAmount")));
				bean.setAsOnDate(StringUtility.checknull(investmade.getString("asOnDate")));
				InvestmentMadeService.getInstance().updateinvestmentMade(bean);
			
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:updateinvestmentMade:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			return am.getInputForward();
		}
		return forward;
	}
	public ActionForward deleteInvestmentMade(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("investmentMadeSearch");
		InvestmentMadeBean bean=new InvestmentMadeBean();
		try{
			bean.setInvestmentMadeCd(StringUtility.checknull(req.getParameter("deleteall")));
			InvestmentMadeService.getInstance().deleteInvestmentMade(bean);
		}
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:deleteInvestmentMade:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			return am.getInputForward();
		}
		return forward;
	}
	public ActionForward generateInvestmentMadeReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showinvestmentMadeReport");
		DynaValidatorForm investmade=(DynaValidatorForm)af;
		InvestmentMadeBean bean=new InvestmentMadeBean();
		try{
			bean.setInvestmentMadeCd(StringUtility.checknull(investmade.getString("investmentMadeCd")));
			bean=InvestmentMadeService.getInstance().findInvestmentMade(bean);
			req.setAttribute("investmadebean",bean);
			
		}
		
		catch(Exception e)
		{
			log.error("InvestmentMadeAction:generateInvestmentMadeReport:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("investmentmade", new ActionMessage("investmentmade.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			return am.getInputForward();
		}
		return forward;
		
	}

}
