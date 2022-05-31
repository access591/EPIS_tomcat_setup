package com.epis.action.investment;

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

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.investment.LetterToBankBean;
import com.epis.dao.admin.UserDAO;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.investment.FormFillingService;
import com.epis.services.investment.LettertoBankService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.utilities.DateValidation;
import com.epis.utilities.StringUtility;

public class LettertoBankAction extends DispatchAction {
	public ActionForward searchLettertoBank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
			DynaValidatorForm letterform=(DynaValidatorForm)af;
			ActionForward forward=am.findForward("showlettertobanksearch");
			List banks=null;
			List lettertobankList=null;
			LetterToBankBean bean=new LetterToBankBean();
			String mode=StringUtility.checknull(req.getParameter("mode"));
			try{
				bean.setLetterNo(StringUtility.checknull(letterform.getString("letterNo")));
				bean.setRefNo(StringUtility.checknull(letterform.getString("refNo")));
				bean.setAccountNo(StringUtility.checknull(letterform.getString("accountNo")));
				bean.setBeneficiaryName(StringUtility.checknull(letterform.getString("beneficiaryName")));
				bean.setSellerRefNo(StringUtility.checknull(letterform.getString("sellerRefNo")));
				bean.setSecurityName(StringUtility.checknull(letterform.getString("securityName")));
				bean.setSecurityCategory(StringUtility.checknull(letterform.getString("securityCategory")));
				bean.setMode(mode);
				banks=BankMasterService.getInstance().search(new BankMasterInfo());
				lettertobankList=LettertoBankService.getInstance().searchLettertoBank(bean);
				
				req.setAttribute("banks",banks);
				req.setAttribute("lettertobankList",lettertobankList);
				req.setAttribute("modeType",mode);
				req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
						.getSecurityCategories(mode));
				
			}
			catch(Exception e)
			{
				ActionMessages errors = new ActionMessages();
				errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
				saveErrors(req,errors);
				req.setAttribute("banks",banks);
				forward = am.getInputForward();
			}
		return forward;
	}

	public ActionForward showLettertoBankAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=null;
		List banks=null;
		List accountypelist=null;
		
		List proposalList=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		if(mode.equals("rbiauction"))
		{
			forward=am.findForward("showlettertobankrbinew");
		}
		else if(mode.equals("psuprimary"))
		{
			forward=am.findForward("showlettertobankpsunew");
		}
		
		try
		{
			
			log.info("the showing add letter mode"+mode);
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			accountypelist=FormFillingService.getInstance().getAccountTypes();
			proposalList=FormFillingService.getInstance().getProposal(mode);
			req.setAttribute("accountypelist",accountypelist);
			req.setAttribute("banks",banks);
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("modeType",mode);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("banks",banks);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward addletterbank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("lettertoBankSave");
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		LetterToBankBean bean=new LetterToBankBean(req);
		HttpSession session=req.getSession(true);
		String mode=StringUtility.checknull(req.getParameter("mode"));
		
		try
		{
			bean.setRefNo(StringUtility.checknull(letterform.getString("refNo")));
			bean.setTrustType(StringUtility.checknull(letterform.getString("trustType")));
			bean.setSecurityCategory(StringUtility.checknull(letterform.getString("securityCategory")));
			bean.setMarketType(StringUtility.checknull(letterform.getString("marketType")));
			bean.setAccountNo(StringUtility.checknull(letterform.getString("accountNo")));
			bean.setBankName(StringUtility.checknull(letterform.getString("bankName")));
			bean.setBranchName(StringUtility.checknull(letterform.getString("branchName")));
			bean.setAccountType(StringUtility.checknull(letterform.getString("accountType")));
			bean.setBankDate(StringUtility.checknull(letterform.getString("bankDate")));
			bean.setBeneficiaryName(StringUtility.checknull(letterform.getString("beneficiaryName")));
			bean.setCreditAccountNo(StringUtility.checknull(letterform.getString("creditAccountNo")));
			bean.setCenterLocation(StringUtility.checknull(letterform.getString("centerLocation")));
			bean.setRemitanceAmt(StringUtility.checknull(letterform.getString("remitanceAmt")));
			bean.setRemarks(StringUtility.checknull(letterform.getString("remarks")));
			if(bean.getAccountType().equals("S"))
				bean.setAccountTypedef("Saving");
			else if(bean.getAccountType().equals("C"))
				bean.setAccountTypedef("Current");
			bean.setAmountInv(StringUtility.checknull(letterform.getString("amountInv")));
			bean.setIfscCode(StringUtility.checknull(letterform.getString("ifscCode")));
			bean.setLeftSign(StringUtility.checknull(letterform.getString("leftSign")));
			bean.setRightSign(StringUtility.checknull(letterform.getString("rightSign")));
			bean.setSglAccountNo(StringUtility.checknull(letterform.getString("sglAccountNo")));
			bean.setSellerRefNo(StringUtility.checknull(letterform.getString("sellerRefNo")));
			bean.setDealDate(StringUtility.checknull(letterform.getString("dealDate")));
			bean.setSettlementDate(StringUtility.checknull(letterform.getString("settlementDate")));
			bean.setFaceValue(StringUtility.checknull(letterform.getString("faceValue")));
			bean.setOfferedPrice(StringUtility.checknull(letterform.getString("offeredPrice")));
			bean.setPrincipalAmount(StringUtility.checknull(letterform.getString("principalAmount")));
			bean.setNoofdays(StringUtility.checknull(letterform.getString("noofdays")));
			bean.setPaymentValue(StringUtility.checknull(letterform.getString("paymentValue")));
			bean.setAccuredInterestAmount(StringUtility.checknull(letterform.getString("accuredInterestAmount")));
			bean.setSettlementAmount(StringUtility.checknull(letterform.getString("settlementAmount")));
			bean.setSecurityName(StringUtility.checknull(letterform.getString("securityName")));
			bean.setSecurityFullName(StringUtility.checknull(letterform.getString("securityFullName")));
			bean.setMode(mode);
			if(bean.getMode().equals("psuprimary"))
				bean.setSettlementAmount(StringUtility.checknull(letterform.getString("remitanceAmt")));
			
			
			String securityName=LettertoBankService.getInstance().getSecurityName(StringUtility.checknull(letterform.getString("refNo")));
			String aaiaccountNo=LettertoBankService.getInstance().getaaiaccountNo(StringUtility.checknull(letterform.getString("refNo")));
			String bankAddress=LettertoBankService.getInstance().getBankAddress(StringUtility.checknull(letterform.getString("accountNo")));
			if(StringUtility.checknull(bankAddress).equals(""))
				bankAddress=LettertoBankService.getInstance().getBankAddress(StringUtility.checknull(aaiaccountNo));
				
			String accounttypeaai=LettertoBankService.getInstance().getBankAccountType(StringUtility.checknull(letterform.getString("accountNo")));
			log.info("the settlement amount is.."+bean.getSettlementAmount());
			String letterNo=LettertoBankService.getInstance().saveLettertoBank(bean);
			bean.setSecurityName(securityName);
			bean.setBankAddress(bankAddress);
			bean.setAaiaccountNo(StringUtility.checknull(aaiaccountNo));
			bean.setAccountTypaai(accounttypeaai);
			bean.setLetterNo(letterNo);
			session.setAttribute("letterToBankBean",bean);
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		
		return forward;
	}
	public ActionForward letttertoBank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	
	{
		ActionForward forward=am.findForward("lettertoBankFormat");
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the letter mode is..."+mode);
		StringBuffer newPath =
	        new StringBuffer(am.findForward("lettertoBankFormat").getPath());
	    newPath.append("&mode="+mode);
	     forward= (new ActionForward(newPath.toString(), true));
		
		return forward;
	}
	public ActionForward showEditLettertoBank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showlettertobankedit");
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		List proposalList=null;
		List banks=null;
		
		String letterNo=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the showedit mode is..."+mode);
	
		LetterToBankBean bean=new LetterToBankBean();
		try
		{
			
			 
			letterNo=letterform.getString("letterNo");
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			proposalList=FormFillingService.getInstance().getProposal(mode);
			bean=LettertoBankService.getInstance().findLetterToBank(letterNo);
			req.setAttribute("banks",banks);
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("letterbean",bean);
			req.setAttribute("modeType",mode);
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("banks",banks);
			forward = am.getInputForward();
		}
		
		return forward;
	}
	public ActionForward editletterbank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("lettertoBankupdate");
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		LetterToBankBean bean=new LetterToBankBean(req);
		LetterToBankBean lettertobank=new LetterToBankBean();
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the editing letter mode"+mode);
		HttpSession session=req.getSession(true);
		try
		{
			bean.setLetterNo(StringUtility.checknull(letterform.getString("letterNo")));
			log.info("the letter no is...."+bean.getLetterNo());
			bean.setBankDate(StringUtility.checknull(letterform.getString("bankDate")));
			bean.setBeneficiaryName(StringUtility.checknull(letterform.getString("beneficiaryName")));
			bean.setCreditAccountNo(StringUtility.checknull(letterform.getString("creditAccountNo")));
			bean.setCenterLocation(StringUtility.checknull(letterform.getString("centerLocation")));
			bean.setRemitanceAmt(StringUtility.checknull(letterform.getString("remitanceAmt")));
			bean.setRemarks(StringUtility.checknull(letterform.getString("remarks")));
			bean.setLeftSign(StringUtility.checknull(letterform.getString("leftSign")));
			bean.setRightSign(StringUtility.checknull(letterform.getString("rightSign")));
			bean.setSellerRefNo(StringUtility.checknull(letterform.getString("sellerRefNo")));
			bean.setNoofdays(StringUtility.checknull(letterform.getString("noofdays")));
			bean.setPaymentValue(StringUtility.checknull(letterform.getString("paymentValue")));
			bean.setAccuredInterestAmount(StringUtility.checknull(letterform.getString("accuredInterestAmount")));
			bean.setSettlementAmount(StringUtility.checknull(letterform.getString("settlementAmount")));
			String letterNo1=LettertoBankService.getInstance().editletterbank(bean);
			bean.setMode(mode);
			lettertobank=LettertoBankService.getInstance().findLetterToBank(letterNo1);
			lettertobank.setMode(mode);
			session.setAttribute("letterToBankBean",lettertobank);
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
		
	}
	public ActionForward deleteLettertoBank(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)throws Exception
	{
		ActionForward forward=am.findForward("lettertobanksearch");
		List banks=null;
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		LetterToBankBean bean=new LetterToBankBean();
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try
		{
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			String letterNo = req.getParameter("deleteall");
			LettertoBankService.getInstance().deleteLettertoBank(letterNo);
			log.info("++++++refnos-----------"+letterNo);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("lettertobanksearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));		
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("banks",banks);
			forward = am.getInputForward();
		}
		return forward;
	}
	
	public ActionForward generateLettertoBankReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)throws Exception
	{
		ActionForward forward=am.findForward("lettertoBankview");
		List banks=null;
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		LetterToBankBean bean=new LetterToBankBean();
		try{
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			String letterNo=StringUtility.checknull(letterform.getString("letterNo"));
			bean=LettertoBankService.getInstance().findLetterToBank(letterNo);
			req.setAttribute("letterToBankBean",bean);
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("lettertoBank",new ActionMessage("lettertobank.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("banks",banks);
			forward = am.getInputForward();
		}
		return forward;
	}
	
}
