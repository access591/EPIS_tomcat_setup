package com.epis.action.investment;
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

import com.epis.bean.cashbook.PartyInfo;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.services.cashbook.PartyService;
import com.epis.services.investment.ConfirmationFromCompanyService;
import com.epis.services.investment.InvestmentRegisterService;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.QuotationService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;

import java.util.List;

public class InvestmentRegisterAction extends DispatchAction {
	Log log=new Log(InvestmentRegisterAction.class);
	
public ActionForward searchInvestmentRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=null;
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	List investmentRegisterList=null;
	String mode=StringUtility.checknull(req.getParameter("mode"));
	if(mode.equals("rbiauctionManual"))
		forward=am.findForward("showinvestmentRegisterManualsearch");
	else
		forward=am.findForward("showinvestmentRegistersearch");
		
	
	log.info("searchinvestregister bean mode...."+mode);
	try{
		bean.setLetterNo(StringUtility.checknull(register.getString("letterNo")));
		bean.setRefNo(StringUtility.checknull(register.getString("refNo")));
		bean.setSecurityCategory(StringUtility.checknull(register.getString("securityCategory")));
		bean.setSecurityName(StringUtility.checknull(register.getString("securityName")));
		bean.setMode(mode);
		investmentRegisterList=InvestmentRegisterService.getInstance().searchInvestmentRegister(bean);
		req.setAttribute("investmentRegisterList",investmentRegisterList);
		req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
				.getSecurityCategories(mode));
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}
public ActionForward showInvestmentRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=null;
	List regLettersList=null;
	String mode=StringUtility.checknull(req.getParameter("mode"));
	forward=am.findForward("showInvestmentRegister");
	try{
		regLettersList=InvestmentRegisterService.getInstance().getLetterNumbers(mode);
		req.setAttribute("regLettersList",regLettersList);
		req.setAttribute("modetype",mode);
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	
	return forward;
}
public ActionForward showInvestmentRegisterManualAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	InvestmentRegisterBean bean=new InvestmentRegisterBean(req);
	ActionForward forward=am.findForward("showInvestmentRegisterAdd");
	String mode=StringUtility.checknull(req.getParameter("mode"));
	List purchaseoptionList=null;
	List modeinterestList=null;
	List interestDatesList=null;
	List interestMonthsList=null;
	List confirmationList=null;
	bean.setMode(mode);
	try{
		modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
		purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
		interestDatesList=QuotationService.getInstance().getinterestDates();
		interestMonthsList=QuotationService.getInstance().getinterestMonths();
		confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
		req.setAttribute("trustRecords", TrustTypeService.getInstance()
				.getTrustTypes(mode));
		req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
				.getSecurityCategories(mode));
		req.setAttribute("marketTypeList", QuotationRequestService.getInstance()
				.getMarketTypeList(mode));
		req.setAttribute("agreeList",ConfirmationFromCompanyService.getInstance().getconfirmationList());
		
		req.setAttribute("modeinterestList",modeinterestList);
		req.setAttribute("purchaseoptionList",purchaseoptionList);
		req.setAttribute("interestDatesList",interestDatesList);
		req.setAttribute("interestMonthsList",interestMonthsList);
		req.setAttribute("confirmationList",confirmationList);
		req.setAttribute("registerBean",bean);
		
	}
	catch(Exception e)
	{
		
	}
	return forward;
}
public ActionForward InvestmentRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("showInvestmentRegisternew");
	List agreeList=null;
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	String mode=StringUtility.checknull(req.getParameter("mode"));
	try{
		bean.setLetterNo(StringUtility.checknull(register.getString("letterNo")));
		bean.setMode(mode);
		bean=InvestmentRegisterService.getInstance().getRegisterDetails(bean);
		
		agreeList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
		req.setAttribute("agreeList",agreeList);
		req.setAttribute("registerBean",bean);
	}
	catch(Exception  e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}
public ActionForward addInvestmentRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("saveInvestmentRegister");
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean(req);
	String mode=StringUtility.checknull(req.getParameter("mode"));
	log.info("the mode is coming from addinvestmentregister"+mode);
	List agreeList=null;
	try{
		BeanUtils.copyProperties(bean,register);
		bean.setMode(mode);
		bean.setSettlementAmt(StringUtility.checknull(req.getParameter("settlementAmount")));
		InvestmentRegisterService.getInstance().addInvestmentRegister(bean);
		agreeList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
		
		StringBuffer newPath =
	        new StringBuffer(am.findForward("saveInvestmentRegister").getPath());
	    newPath.append("&mode="+mode);
	     forward= (new ActionForward(newPath.toString(), true));
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		req.setAttribute("agreeList",agreeList);
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}
public ActionForward addInvestmentRegisterManual(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	List purchaseoptionList=null;
	List modeinterestList=null;
	List interestDatesList=null;
	List interestMonthsList=null;
	List confirmationList=null;
	List trusttypelist=null;
	List securitycategorylist=null;
	log.info("addInvestmentRegisterManual:entering");
	ActionForward forward=am.findForward("saveInvestmentRegisterManual");
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean(req);
	String mode=StringUtility.checknull(req.getParameter("mode"));
	try{
		securitycategorylist=SecurityCategoryService.getInstance().getSecurityCategories(mode);
		trusttypelist=TrustTypeService.getInstance().getTrustTypes(mode);
		modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
		purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
		interestDatesList=QuotationService.getInstance().getinterestDates();
		interestMonthsList=QuotationService.getInstance().getinterestMonths();
		confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
		bean.setMode(mode);
		bean.setRefNo(StringUtility.checknull(register.getString("refNo")));
		bean.setTrustType(StringUtility.checknull(register.getString("trustType")));
		bean.setSecurityCategory(StringUtility.checknull(register.getString("securityCategory")));
		bean.setMarketType(StringUtility.checknull(register.getString("marketType")));
		bean.setAmountInv(StringUtility.checknull(register.getString("amountInv")));
		bean.setISIN(StringUtility.checknull(register.getString("ISIN")));
		bean.setConfirm(StringUtility.checknull(register.getString("confirm")));
		bean.setSecurityName(StringUtility.checknull(req.getParameter("securityName")));
		bean.setDealDate(StringUtility.checknull(register.getString("dealDate")));
		bean.setSettlementDate(StringUtility.checknull(register.getString("settlementDate")));
		bean.setMaturityDate(StringUtility.checknull(register.getString("maturityDate")));
		bean.setCuponRate(StringUtility.checknull(register.getString("cuponRate")));
		bean.setFaceValue(StringUtility.checknull(register.getString("faceValue")));
		bean.setNoofBonds(StringUtility.checknull(register.getString("noofBonds")));
		bean.setOfferedPrice(StringUtility.checknull(register.getString("offeredPrice")));
		bean.setPremiumPaid(StringUtility.checknull(register.getString("premiumPaid")));
		bean.setPurchaseOption(StringUtility.checknull(register.getString("purchaseOption")));
		bean.setPurchasePrice(StringUtility.checknull(register.getString("purchasePrice")));
		bean.setModeOfInterest(StringUtility.checknull(register.getString("modeOfInterest")));
		bean.setAccuredInterestAmount(StringUtility.checknull(register.getString("accuredInterestAmount")));
		
		bean.setCallOption(StringUtility.checknull(register.getString("callOption")));
		bean.setYtmValue(StringUtility.checknull(register.getString("ytmValue")));
		
		bean.setInterestMonth(StringUtility.checknull(register.getString("interestMonth")));
		log.info("the interest month is..."+bean.getInterestMonth());
		bean.setInterestDate(StringUtility.checknull(register.getString("interestDate")));
		bean.setInterestMonth1(StringUtility.checknull(register.getString("interestMonth1")));
		bean.setInterestDate1(StringUtility.checknull(register.getString("interestDate1")));
		bean.setCallDate(StringUtility.checknull(register.getString("callDate")));
		bean.setArrangerName(StringUtility.checknull(register.getString("arrangerName")));
		bean.setIncentivePayable(StringUtility.checknull(register.getString("incentivePayable")));
		bean.setIncentivetext(StringUtility.checknull(register.getString("incentivetext")));
		InvestmentRegisterService.getInstance().addInvestmentRegisterManual(bean);
		log.info("this calling ending add investement register manual");
		
		
		if("N".equals(req.getParameter("exist"))){
			PartyInfo party = new PartyInfo(req);
			party.setPartyName(bean.getSecurityName()+"-"+bean.getRefNo());
			party.setModuleType("I");
			
			try{
			PartyService.getInstance().add(party);
			}
			catch(Exception e)
			{
				log.info("InvestmentApprovalAction:addInvestRegister:Error:"+e.getMessage() );
			}
			
		}
		
		
		StringBuffer newPath =
	        new StringBuffer(am.findForward("saveInvestmentRegisterManual").getPath());
	    newPath.append("&mode="+mode);
	     forward= (new ActionForward(newPath.toString(), true));
		
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		
		saveErrors(req,errors);
		req.setAttribute("trustRecords", trusttypelist);
		req.setAttribute("categoryRecords",securitycategorylist );
		req.setAttribute("marketTypeList", QuotationRequestService.getInstance()
				.getMarketTypeList(mode));
		req.setAttribute("agreeList",ConfirmationFromCompanyService.getInstance().getconfirmationList());
		
		req.setAttribute("modeinterestList",modeinterestList);
		req.setAttribute("purchaseoptionList",purchaseoptionList);
		req.setAttribute("interestDatesList",interestDatesList);
		req.setAttribute("interestMonthsList",interestMonthsList);
		req.setAttribute("confirmationList",confirmationList);
		req.setAttribute("registerBean",bean);
		forward = am.getInputForward();
	}
	log.info("addInvestmentRegisterManual:leaving");
	return forward;
	
}
public ActionForward generateInvestmentRegisterReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("registerReport");
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	try{
		bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
		bean=InvestmentRegisterService.getInstance().generateInvestmentRegisterReport(bean);
		req.setAttribute("registerBean",bean);
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}
public ActionForward editInvestmentRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("EditInvestmentRegister");
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
	try{
		//bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
		bean=InvestmentRegisterService.getInstance().generateInvestmentRegisterReport(bean);
		//System.out.println(bean.getLetterNo());
		req.setAttribute("registerBean",bean);
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}
public ActionForward updateInvestmentRegisterManual(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
{
	ActionForward forward=am.findForward("updateInvestmentRegisterManual");
	DynaValidatorForm register=(DynaValidatorForm)af;
	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
	bean.setISIN(StringUtility.checknull(register.getString("ISIN")));
	String mode=StringUtility.checknull(req.getParameter("mode"));
	try{
		//bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
		//bean=InvestmentRegisterService.getInstance().generateInvestmentRegisterReport(bean);
		System.out.println(bean.getRegisterCd()+bean.getISIN());
		//req.setAttribute("registerBean",bean);
		int n=InvestmentRegisterService.getInstance().updateInvestmentISIN(bean);
		
		StringBuffer newPath =
	        new StringBuffer(am.findForward("saveInvestmentRegisterManual").getPath());
	    newPath.append("&mode="+mode);
	     forward= (new ActionForward(newPath.toString(), true));
		
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages();
		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
		saveErrors(req,errors);
		forward = am.getInputForward();
	}
	return forward;
}

}
