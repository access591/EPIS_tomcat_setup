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

import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.investment.GenerateBankLetterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.investment.FormFillingService;
import com.epis.services.investment.GenerateBankLetterService;
import com.epis.services.investment.QuotationService;
import com.epis.utilities.StringUtility;

public class GenerateBankLetterAction extends DispatchAction {
	public ActionForward showGenerateBankLetter(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		log.info("the bankletter start the step here");
		ActionForward forward=am.findForward("bankletter");
		List letterList=null;
		try{
			letterList=GenerateBankLetterService.getInstance().getApprovalLetters();
			req.setAttribute("letterList",letterList);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages(); 
			errors.add("generateBankLetter",new ActionMessage("generatebankletter.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward searchBankLetter(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		log.info("the action... is callinggg...");
		DynaValidatorForm bankLetter = (DynaValidatorForm)af;
		GenerateBankLetterBean bean=new GenerateBankLetterBean();
		ActionForward forward =am.findForward("showbanklettersearch");
		
		List bankLetterList=null;
		try{
			bean.setBankLetterNo(bankLetter.getString("bankLetterNo"));
			bean.setAccountNo(bankLetter.getString("accountNo"));
			bankLetterList=GenerateBankLetterService.getInstance().searchGenerateBankLetter(bean);
			log.info("the size is"+bankLetterList.size());
			req.setAttribute("bankLetterList",bankLetterList);
			req.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
		}
		catch(Exception e){
			ActionMessages errors = new ActionMessages(); 
			
			errors.add("generateBankLetter",new ActionMessage("generatebankletter.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
	  }
		
		return forward;
	}
	public ActionForward showGenerateBankLetterAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		log.info("the action is showgeneratebankletteradd");
		ActionForward forward = am.findForward("showbankletternew");
		DynaValidatorForm bankLetter=(DynaValidatorForm)af;
		GenerateBankLetterBean bean=new GenerateBankLetterBean();
		QuotationBean qbean=new QuotationBean();
		List purchasePriseList=null;
		List investmentModeList=null;
		List interestDatesList=null;
		List interestMonthsList=null;
		List accountypelist=null;
		try{
			bean.setQuotationCd(StringUtility.checknull(bankLetter.getString("letterNo")));
			qbean=GenerateBankLetterService.getInstance().bankLetterDetails(bean);
			purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
			interestDatesList=QuotationService.getInstance().getinterestDates();
    		interestMonthsList=QuotationService.getInstance().getinterestMonths();
    		accountypelist=FormFillingService.getInstance().getAccountTypes();
			investmentModeList=QuotationService.getInstance().getInvestmentModes();
			req.setAttribute("bankBean",qbean);
			req.setAttribute("purchasePriseList",purchasePriseList);
			req.setAttribute("investmentModeList",investmentModeList);
			req.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			req.setAttribute("interestDatesList",interestDatesList);
			req.setAttribute("accountypelist",accountypelist);
			req.setAttribute("interestMonthsList",interestMonthsList);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages(); 
			errors.add("generateBankLetter",new ActionMessage("generatebankletter.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward addGenerateBankLetter(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("generatebanklettersearch");
		DynaValidatorForm bank=(DynaValidatorForm)af;
		GenerateBankLetterBean bean=new GenerateBankLetterBean();
		QuotationBean qbean=new QuotationBean();
		
		try{
			bean.setQuotationCd(StringUtility.checknull(bank.getString("letterNo")));
			bean.setBankLetterNo(StringUtility.checknull(bank.getString("bankLetterNo")));
			bean.setDealDate(StringUtility.checknull(bank.getString("dealDate")));
			bean.setSettlementDate(StringUtility.checknull(bank.getString("settlementDate")));
			bean.setAccountNo(StringUtility.checknull(bank.getString("accountNo")));
			bean.setRate(StringUtility.checknull(bank.getString("faceValue")));
			bean.setAccuredAmount(StringUtility.checknull(bank.getString("accuredAmount")));
			bean.setNoofDays(StringUtility.checknull(bank.getString("noofDays")));
			bean.setSettlementAmount(StringUtility.checknull(bank.getString("totalConsideration")));
			bean.setPurchasePrice(StringUtility.checknull(bank.getString("purchasePrice")));
			bean.setPrincipalAmount(StringUtility.checknull(bank.getString("principleAmt")));
			bean.setBeneficiaryName(StringUtility.checknull(bank.getString("beneficiaryName")));
			bean.setBenificiaryBranch(StringUtility.checknull(bank.getString("benificiaryBranch")));
			bean.setAccountType(StringUtility.checknull(bank.getString("accountType")));
			bean.setIfscCode(StringUtility.checknull(bank.getString("ifscCode")));
			bean.setCreditAccountNo(StringUtility.checknull(bank.getString("creditAccountNo")));
			bean.setRemarks(StringUtility.checknull(bank.getString("remarks")));
			bean.setLeftSign(StringUtility.checknull(bank.getString("leftSign")));
			bean.setRightSign(StringUtility.checknull(bank.getString("rightSign")));
			bean.setBenificiaryBankName(StringUtility.checknull(bank.getString("benificiaryBankName")));
			qbean.setFaceValue(StringUtility.checknull(bank.getString("faceValue")));
			qbean.setPurchaseOption(StringUtility.checknull(bank.getString("purchaseOption")));
			qbean.setPremiumPaid(StringUtility.checknull(bank.getString("premiumPaid")));
			qbean.setInvestmentFaceValue(StringUtility.checknull(bank.getString("investmentFaceValue")));
			qbean.setNumberOfUnits(StringUtility.checknull(bank.getString("numberOfUnits")));
			qbean.setPurchasePrice(StringUtility.checknull(bank.getString("purchasePrice")));
			qbean.setMaturityDate(StringUtility.checknull(bank.getString("maturityDate")));
			qbean.setPriceoffered(StringUtility.checknull(bank.getString("priceoffered")));
			qbean.setInterestRate(StringUtility.checknull(bank.getString("interestRate")));
			qbean.setInvestmentMode(StringUtility.checknull(bank.getString("investmentMode")));
			qbean.setInterestDate(StringUtility.checknull(bank.getString("interestDate")));
			qbean.setInterestDate1(StringUtility.checknull(bank.getString("interestDate1")));
			qbean.setInterestMonth(StringUtility.checknull(bank.getString("interestMonth")));
			qbean.setInterestMonth1(StringUtility.checknull(bank.getString("interestMonth1")));
			qbean.setDealDate(StringUtility.checknull(bank.getString("dealDate")));
			qbean.setSettlementDate(StringUtility.checknull(bank.getString("settlementDate")));
			qbean.setYtm(StringUtility.checknull(bank.getString("ytm")));
			
			
			qbean.setQuotationCd(StringUtility.checknull(bank.getString("letterNo")));
			
			GenerateBankLetterService.getInstance().saveGenerateBankLetter(bean);
			GenerateBankLetterService.getInstance().updatequotationData(qbean);
		}
		catch(Exception e)
		{
			ActionMessages errors=new ActionMessages();
			errors.add("generateBankLetter",new ActionMessage("generatebankletter.Errors",e.getMessage()));
			try {
				qbean=GenerateBankLetterService.getInstance().bankLetterDetails(bean);
				req.setAttribute("bankBean",qbean);
				try {
					req.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ServiceNotAvailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EPISException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			saveErrors(req,errors);
			forward=am.getInputForward();
			
		}
		return forward;
	}
	public ActionForward generateBankLetterReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("generatebanklettersearch");
		DynaValidatorForm bankLetter=(DynaValidatorForm)af;
		GenerateBankLetterBean bean=new GenerateBankLetterBean();
		try{
			
			bean.setQuotationCd(StringUtility.checknull(bankLetter.getString("quotationCd")));
			bean=GenerateBankLetterService.getInstance().generateBankLetterReport(bean);
			req.setAttribute("bankLetter",bean);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages(); 
			errors.add("generateBankLetter",new ActionMessage("generatebankletter.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward updateQuotationData(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		DynaValidatorForm bank=(DynaValidatorForm)af;
		QuotationBean qbean=new QuotationBean();
		try{
			qbean.setFaceValue(StringUtility.checknull(bank.getString("faceValue")));
			qbean.setMaturityDate(StringUtility.checknull(bank.getString("maturityDate")));
			qbean.setInterestRate(StringUtility.checknull(bank.getString("interestRate")));
			qbean.setInterestDate(StringUtility.checknull(bank.getString("interestDate")));
			qbean.setInterestDate1(StringUtility.checknull(bank.getString("interestDate1")));
			qbean.setLetterNo(StringUtility.checknull(bank.getString("letterNo")));
			qbean.setPriceoffered(StringUtility.checknull(bank.getString("priceoffered")));
			qbean.setInterestMonth(StringUtility.checknull(bank.getString("interestMonth")));
			qbean.setInterestMonth1(StringUtility.checknull("interestMonth1"));
			GenerateBankLetterService.getInstance().updateQuotationData(qbean);
			res.setContentType("text/xml");
			res.setHeader("Pragma","no-cache");//for HTTP1.0
			res.setHeader("cache-control","no-cache");// for HTTP/1.1.
			PrintWriter out = res.getWriter();  
			
			
		}
		catch(Exception e)
		{
			log.error("the Exception in update quotationdata"+e.getMessage());
		}
		return null;
	}

}
