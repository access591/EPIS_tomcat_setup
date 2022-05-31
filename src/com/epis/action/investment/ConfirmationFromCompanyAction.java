package com.epis.action.investment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.FormFillingAppBean;
import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.services.investment.ConfirmationFromCompanyService;
import com.epis.services.investment.FormFillingService;
import com.epis.services.investment.InvestmentProposalService;
import com.epis.services.investment.LettertoBankService;
import com.epis.services.investment.QuotationService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.utilities.DBUtility;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;
import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.admin.UserDAO;

public class ConfirmationFromCompanyAction extends DispatchAction {
	
	public ActionForward searchConfirmationCompany(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=null;
		DynaValidatorForm confirmation=(DynaValidatorForm)af;
		ConfirmationFromCompany bean=new ConfirmationFromCompany();
		List confirmationcompanyList=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the mode is..."+mode);
		if(mode.equals("rbiauction"))
		{
			forward=am.findForward("showconfirmationcompanyrbisearch");
		}
		else if(mode.equals("psuprimary"))
		{
			forward=am.findForward("showconfirmationcompanypsusearch");
		}
		
		try
		{
			bean.setLetterNo(StringUtility.checknull(confirmation.getString("letterNo")));
			bean.setRefNo(StringUtility.checknull(confirmation.getString("refNo")));
			bean.setSettlementDate(StringUtility.checknull(confirmation.getString("settlementDate")));
			bean.setSecurityCategory(StringUtility.checknull(confirmation.getString("securityCategory")));
			bean.setMode(StringUtility.checknull(mode));
			confirmationcompanyList=ConfirmationFromCompanyService.getInstance().searchConfirmationCompany(bean);
			req.setAttribute("confirmationcompanyList",confirmationcompanyList);
			req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories(mode));
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward showConfirmationCompanyAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=null;
		List proposalList=null;
		List confirmationList=null;
		List modeinterestList=null;
		List purchaseoptionList=null;
		List interestDatesList=null;
    	List interestMonthsList=null;
    	
    	
		ConfirmationFromCompany bean=new ConfirmationFromCompany();
		String mode=StringUtility.checknull(req.getParameter("mode"));
		if(mode.equals("rbiauction"))
		{
			forward=am.findForward("showconfirmationcompanyrbinew");
		}
		else if(mode.equals("psuprimary"))
		{
			forward=am.findForward("showconfirmationpsunew");
		}
		else if(mode.equals("preperationrbiauction"))
		{
			forward=am.findForward("preperationrbinew");
		}
		bean.setMode(mode);
		try{
			proposalList=LettertoBankService.getInstance().getProposal(mode);
			confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
			modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
			purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
			interestDatesList=QuotationService.getInstance().getinterestDates();
    		interestMonthsList=QuotationService.getInstance().getinterestMonths();
    		bean.setLetterNo(StringUtility.checknull(req.getParameter("letterNo")));
    		if(bean.getMode().equals("rbiauction"))
    		{
    		bean=ConfirmationFromCompanyService.getInstance().findConfirmationCompany(StringUtility.checknull(req.getParameter("letterNo")));
    		bean.setMode(mode);
    		}
    		
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("confirmationList",confirmationList);
			req.setAttribute("modeinterestList",modeinterestList);
			req.setAttribute("purchaseoptionList",purchaseoptionList);
			req.setAttribute("confirmationbean",bean);
			req.setAttribute("interestDatesList",interestDatesList);
    		req.setAttribute("interestMonthsList",interestMonthsList);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		
		return forward;
	}
	public ActionForward addConfirmationCompany(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=null;
		List proposalList=null;
		List confirmationList=null;
		List modeinterestList=null;
		List purchaseoptionList=null;
		StringBuffer newPath =new StringBuffer();
		DynaValidatorForm confirm=(DynaValidatorForm)af;
		ConfirmationFromCompany bean=new ConfirmationFromCompany(req);
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the insert confirmation"+mode);
		
		try{
			proposalList=LettertoBankService.getInstance().getProposal(mode);
			confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
			modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
			purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
			bean.setConfirm(StringUtility.checknull(confirm.getString("confirm")));
			bean.setRemarks(StringUtility.checknull(confirm.getString("remarks")));
			bean.setDealDate(StringUtility.checknull(confirm.getString("dealDate")));
			bean.setSettlementDate(StringUtility.checknull(confirm.getString("settlementDate")));
			bean.setMaturityDate(StringUtility.checknull(confirm.getString("maturityDate")));
			bean.setCuponRate(StringUtility.checknull(confirm.getString("cuponRate")));
			bean.setPurchaseOption(StringUtility.checknull(confirm.getString("purchaseOption")));
			bean.setPremiumPaid(StringUtility.checknull(confirm.getString("premiumPaid")));
			bean.setFaceValue(StringUtility.checknull(confirm.getString("faceValue")));
			bean.setOfferedPrice(StringUtility.checknull(confirm.getString("offeredPrice")));
			bean.setYtmValue(StringUtility.checknull(confirm.getString("ytmValue")));
			bean.setCallOption(StringUtility.checknull(confirm.getString("callOption")));
			bean.setCallDate(StringUtility.checknull(confirm.getString("callDate")));
			bean.setModeOfInterest(StringUtility.checknull(confirm.getString("modeOfInterest")));
			bean.setInterestDate(StringUtility.checknull(confirm.getString("interestDate")));
			bean.setInterestDate1(StringUtility.checknull(confirm.getString("interestDate1")));
			bean.setInterestMonth(StringUtility.checknull(confirm.getString("interestMonth")));
			bean.setInterestMonth1(StringUtility.checknull(confirm.getString("interestMonth1")));
			bean.setPurchasePrice(StringUtility.checknull(confirm.getString("purchasePrice")));
			bean.setRefNo(StringUtility.checknull(confirm.getString("refNo")));
			bean.setSecurityCategory(StringUtility.checknull(confirm.getString("securityCategory")));
			bean.setMarketType(StringUtility.checknull(confirm.getString("marketType")));
			bean.setAmountInv(StringUtility.checknull(confirm.getString("amountInv")));
			bean.setTrustType(StringUtility.checknull(confirm.getString("trustType")));
			bean.setNoofBonds(StringUtility.checknull(confirm.getString("noofBonds")));
			bean.setLetterNo(StringUtility.checknull(confirm.getString("letterNo")));
			bean.setMode(StringUtility.checknull(mode));
			bean.setSecurityFullName(StringUtility.checknull(req.getParameter("securityfullname")));
			bean.setSecurityName(StringUtility.checknull(req.getParameter("security_name")));
			bean.setConfirmationRemarks(StringUtility.checknull(confirm.getString("confirmationRemarks")));
			bean.setConfirmationDate(StringUtility.checknull(confirm.getString("confirmationDate")));
			if(mode.equals("psuprimary")|| mode.equals("preperationrbiauction"))
			ConfirmationFromCompanyService.getInstance().saveConfirmationCompany(bean);
			else if(mode.equals("rbiauction"))
				ConfirmationFromCompanyService.getInstance().updateConfirmation(bean);
			
			if(mode.equals("rbiauction")||mode.equals("psuprimary"))
			{
				forward=am.findForward("confirmationcompanysave");
				newPath.append(am.findForward("confirmationcompanysave").getPath());
			}
			if(mode.equals("preperationrbiauction"))
			{
				forward=am.findForward("confirmationpreperationsave");
				newPath.append(am.findForward("confirmationpreperationsave").getPath());
			}
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		}
		catch(Exception e)
		{
			
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("confirmationList",confirmationList);
			req.setAttribute("modeinterestList",modeinterestList);
			req.setAttribute("purchaseoptionList",purchaseoptionList);
			forward = am.getInputForward();
			
		}
		
		
		return forward;
		
	}
	
	public ActionForward editConfirmationCompany(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("confirmationcompanyupdate");
		DynaValidatorForm confirm=(DynaValidatorForm)af;
		List proposalList=null;
		List confirmationList=null;
		List modeinterestList=null;
		List purchaseoptionList=null;
		ConfirmationFromCompany bean=new ConfirmationFromCompany(req);
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try
		{
			proposalList=LettertoBankService.getInstance().getProposal(mode);
			confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
			modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
			purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
			bean.setLetterNo(StringUtility.checknull(confirm.getString("letterNo")));
			bean.setConfirm(StringUtility.checknull(confirm.getString("confirm")));
			bean.setRemarks(StringUtility.checknull(confirm.getString("remarks")));
			bean.setDealDate(StringUtility.checknull(confirm.getString("dealDate")));
			bean.setSettlementDate(StringUtility.checknull(confirm.getString("settlementDate")));
			bean.setMaturityDate(StringUtility.checknull(confirm.getString("maturityDate")));
			bean.setCuponRate(StringUtility.checknull(confirm.getString("cuponRate")));
			bean.setPurchaseOption(StringUtility.checknull(confirm.getString("purchaseOption")));
			bean.setPremiumPaid(StringUtility.checknull(confirm.getString("premiumPaid")));
			bean.setFaceValue(StringUtility.checknull(confirm.getString("faceValue")));
			bean.setOfferedPrice(StringUtility.checknull(confirm.getString("offeredPrice")));
			bean.setYtmValue(StringUtility.checknull(confirm.getString("ytmValue")));
			bean.setCallOption(StringUtility.checknull(confirm.getString("callOption")));
			bean.setCallDate(StringUtility.checknull(confirm.getString("callDate")));
			bean.setModeOfInterest(StringUtility.checknull(confirm.getString("modeOfInterest")));
			bean.setInterestDate(StringUtility.checknull(confirm.getString("interestDate")));
			bean.setInterestDate1(StringUtility.checknull(confirm.getString("interestDate1")));
			bean.setInterestMonth(StringUtility.checknull(confirm.getString("interestMonth")));
			bean.setInterestMonth1(StringUtility.checknull(confirm.getString("interestMonth1")));
			bean.setPurchasePrice(StringUtility.checknull(confirm.getString("purchasePrice")));
			bean.setRefNo(StringUtility.checknull(confirm.getString("refNo")));
			bean.setSecurityCategory(StringUtility.checknull(confirm.getString("securityCategory")));
			bean.setMarketType(StringUtility.checknull(confirm.getString("marketType")));
			bean.setAmountInv(StringUtility.checknull(confirm.getString("amountInv")));
			bean.setTrustType(StringUtility.checknull(confirm.getString("trustType")));
			bean.setNoofBonds(StringUtility.checknull(confirm.getString("noofBonds")));
			ConfirmationFromCompanyService.getInstance().editConfirmationCompany(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("confirmationcompanyupdate").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		}
		catch(Exception e)
		{
			
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("confirmationList",confirmationList);
			req.setAttribute("modeinterestList",modeinterestList);
			req.setAttribute("purchaseoptionList",purchaseoptionList);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward showEditConfirmationCompany(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showconfirmationcompanyedit");
		DynaValidatorForm letterform=(DynaValidatorForm)af;
		List proposalList=null;
		List confirmationList=null;
		List modeinterestList=null;
		List purchaseoptionList=null;
		List interestDatesList=null;
    	List interestMonthsList=null;
		ConfirmationFromCompany bean=new ConfirmationFromCompany();
		String letterNo=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		
		try
		{
			letterNo=letterform.getString("letterNo");
			
			proposalList=LettertoBankService.getInstance().getProposal(mode);
			
			confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
			modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
			purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
			interestDatesList=QuotationService.getInstance().getinterestDates();
    		interestMonthsList=QuotationService.getInstance().getinterestMonths();
			
			bean=ConfirmationFromCompanyService.getInstance().findConfirmationCompany(letterNo);
			bean.setMode(mode);
			
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("confirmationList",confirmationList);
			req.setAttribute("modeinterestList",modeinterestList);
			req.setAttribute("purchaseoptionList",purchaseoptionList);
			req.setAttribute("interestDatesList",interestDatesList);
    		req.setAttribute("interestMonthsList",interestMonthsList);
			req.setAttribute("conformationBean",bean);
		}
		catch(Exception e)
		{
			
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
			
		}
		return forward;
	}
	public ActionForward deleteConfirmationCompany(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("confirmationcompanysearch");
		List proposalList=null;
		List confirmationList=null;
		List modeinterestList=null;
		List purchaseoptionList=null;
		List categoryList=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the mode is..."+mode);
		try
		{
			proposalList=LettertoBankService.getInstance().getProposal(mode);
			confirmationList=ConfirmationFromCompanyService.getInstance().getconfirmationList();
			modeinterestList=ConfirmationFromCompanyService.getInstance().getModeofInterestList();
			purchaseoptionList=ConfirmationFromCompanyService.getInstance().getpurchasePriceOption();
			categoryList=SecurityCategoryService.getInstance().getSecurityCategories(mode);
			String letterNo = req.getParameter("deleteall");
			
			ConfirmationFromCompanyService.getInstance().deleteConfirmationCompany(letterNo);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("confirmationcompanysearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		}
		catch(Exception e)
		{
			
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany",new ActionMessage("confirmationcompany.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("proposalList",proposalList);
			req.setAttribute("confirmationList",confirmationList);
			req.setAttribute("modeinterestList",modeinterestList);
			req.setAttribute("purchaseoptionList",purchaseoptionList);
			req.setAttribute("categoryRecords",categoryList );
			forward = am.getInputForward();
		}
		
		return forward;
	}
	
	public ActionForward generateConfirmationCompanyReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("confirmationcompanyview");
		DynaValidatorForm confirm=(DynaValidatorForm)af;
		ConfirmationFromCompany bean=new ConfirmationFromCompany();
		ConfirmationFromCompanyAppBean appBean=null;
		String letterNo=StringUtility.checknull(confirm.getString("letterNo"));
		
		
		try{
			bean = ConfirmationFromCompanyService.getInstance().findapprovalconfirmation(letterNo);
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			int count=0;
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (ConfirmationFromCompanyAppBean)approvals.get(level);
				if(StringUtility.checknull(appBean.getApproved()).equals("A"))
					count++;
				approvals.put(level,getUserDetails(appBean,req));
			}				
			bean.setApprovals(approvals);
			bean.setAppCount(count+"");
			req.setAttribute("confirmationbean", bean);
			req.setAttribute("finYear",InvestmentProposalService.getInstance().getFinYear());
		}
		catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany", new ActionMessage("confirmationcompany.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		
		return forward;
	}
	
	public ActionForward approvalConfirmation(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("confirmationapproval");
		DynaValidatorForm confirm=(DynaValidatorForm)af;
		String letterno=StringUtility.checknull(req.getParameter("letterNo"));
		String updatedFlag=StringUtility.checknull(req.getParameter("updatedFlag"));
		ConfirmationFromCompany bean=new ConfirmationFromCompany(req);
		ConfirmationFromCompanyAppBean appbean=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		String refNo="";
		try {
			
			
			req.setAttribute("edit", StringUtility.checknull(req.getParameter("edit")));
			
			String loginUserID = bean.getLoginUserId();
			refNo=ConfirmationFromCompanyService.getInstance().getproposalRefno(letterno);
				
			bean = ConfirmationFromCompanyService.getInstance().findapprovalconfirmation(letterno);
			
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			
						
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String level = (String)iter.next();
				appbean = (ConfirmationFromCompanyAppBean)approvals.get(level);
				approvals.put(level,getUserDetails(appbean,req));
			}				
			
			appbean = new ConfirmationFromCompanyAppBean();
			appbean.setApprovedBy(loginUserID);			
			approvals.put("8",getUserDetails(appbean,req));
			bean.setUpdatedFlag(updatedFlag);
			bean.setMode(mode);
			bean.setApprovals(approvals);						
			req.setAttribute("proposal", bean);
			//InvestmentProposalService service = InvestmentProposalService.getInstance();
			//req.setAttribute("InvestDetails",service.getInvestmentDetails(refNo,mode));
			req.setAttribute("finYear",FormFillingService.getInstance().getFinYear(refNo));
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany", new ActionMessage("confirmationcompany.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		
		
		return forward;
	}
	
	
	private ConfirmationFromCompanyAppBean getUserDetails(ConfirmationFromCompanyAppBean appBean,HttpServletRequest req){
		try {
			if(!"".equals(StringUtility.checknull(appBean.getApprovedBy()))){
				SQLHelper sql = new SQLHelper();
				UserBean user = UserDAO.getInstance().getUser(appBean.getApprovedBy());
				String path = req.getSession().getServletContext().getRealPath("/");
				path +="uploads/SIGNATORY/"+user.getEsignatoryName();
				
				UserDAO.getInstance().getImage(path,user.getUserName());
				
				appBean.setSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
				appBean.setDisplayName(user.getDisplayName());
				appBean.setDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EPISException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return appBean;
	}
	
	public ActionForward approvalUpdate(ActionMapping am, ActionForm af,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("the method is calling....");
		DynaValidatorForm proposal = (DynaValidatorForm) af;
		ActionForward forward = am.findForward("confirmationSearch");
		ConfirmationFromCompanyAppBean bean = new ConfirmationFromCompanyAppBean(req);
		String mode=StringUtility.checknull(req.getParameter("mode"));
		log.info("the confirmation approval"+mode);
		try {
			String updatedFlag = StringUtility.checknull(proposal.getString("updatedFlag"));
			 if("Y".equals(StringUtility.checknull(proposal.getString("edit")))){
				 updatedFlag = String.valueOf(Integer.parseInt(StringUtility.checknull(proposal.getString("updatedFlag"))));
			 }
			 bean.setLetterNo(StringUtility.checknull(proposal.getString("letterNo")));
			 bean.setDate(StringUtility.checknull(proposal.getString("approveDate")));
			 bean.setRemarks(StringUtility.checknull(proposal.getString("approvalRemarks")));
			 bean.setApprovalLevel(updatedFlag);
			 ConfirmationFromCompanyService.getInstance().approvalUpdate(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("confirmationSearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("confirmationcompany", new ActionMessage("confirmationcompany.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;
	}

}
