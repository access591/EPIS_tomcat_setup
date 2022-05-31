package com.epis.action.investment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.investment.FormFillingAppBean;
import com.epis.bean.investment.FormFillingBean;
import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.admin.UserDAO;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.investment.FormFillingService;
import com.epis.services.investment.InvestmentProposalService;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.DBUtility;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class FormFillingAction extends DispatchAction {
	
	public ActionForward searchFormFilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		DynaValidatorForm formfill=(DynaValidatorForm)af;
		FormFillingBean bean=new FormFillingBean();
		List formfillingList=null;
		List trustList=null;
		ActionForward forward=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		String displaymode=StringUtility.checknull(req.getParameter("mode"));
		if(displaymode.equals("PSUPrimary"))
			displaymode="psuprimary";
		if(mode.equals("rbiauction"))
			forward=am.findForward("showformfillingrbisearch");
		else if(mode.equals("PSUPrimary"))
		 forward=am.findForward("showformfillingsearch");
		
		try{
			bean.setSecurityName(StringUtility.checknull(formfill.getString("securityName")));
			bean.setProposalRefNo(StringUtility.checknull(formfill.getString("proposalRefNo")));
			bean.setTrustType(StringUtility.checknull(formfill.getString("trustType")));
			bean.setNameofApplicant(StringUtility.checknull(formfill.getString("nameofApplicant")));
			bean.setSecurityCategory(StringUtility.checknull(formfill.getString("securityCategory")));
			bean.setMode(StringUtility.checknull(mode));
			formfillingList=FormFillingService.getInstance().searchFormFill(bean);
			
			trustList=TrustTypeService.getInstance().getTrustTypes();
			req.setAttribute("formfillingList",formfillingList);
			req.setAttribute("trustList",trustList);
			req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories(displaymode));
			
		}
		catch(Exception e){
			ActionMessages errors = new ActionMessages();
			errors.add("quotation",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("formfillingList",formfillingList);
			req.setAttribute("trustList",trustList);
			forward = am.getInputForward();
	  }
		
		
		return forward;
	}
	public ActionForward showFormFillingAdd(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=null;
		List proposalRefList=null;
		List taxoption=null;
		List banks=null;
		String mode="";
		mode=StringUtility.checknull(req.getParameter("mode"));
		if(mode.equals("rbiauction"))
		{
			forward=am.findForward("showformfillingrbinew");
		}
		else if(mode.equals("PSUPrimary"))
		{
			
			forward=am.findForward("showformfillingnew");
		}
	
		
			
		
		
		try{
			
			proposalRefList=InvestmentProposalService.getInstance().getProposal(mode);
			taxoption=FormFillingService.getInstance().getStatueOfTaxOption();
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			req.setAttribute("banks",banks);
			req.setAttribute("proposalRefList",proposalRefList);
			req.setAttribute("taxoption",taxoption);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("quotation",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	
	public ActionForward addformfilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("formfillingsave");
		DynaValidatorForm formfill=(DynaValidatorForm)af;
		FormFillingBean bean=new FormFillingBean(req);
		List proposalRefList=null;
		List taxoption=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try{
			
			proposalRefList=InvestmentProposalService.getInstance().getProposal(mode);
			taxoption=FormFillingService.getInstance().getStatueOfTaxOption();
				
			bean.setProposalRefNo(StringUtility.checknull(formfill.getString("proposalRefNo")));
			bean.setTrustType(StringUtility.checknull(formfill.getString("trustType")));
			bean.setSecurityCategory(StringUtility.checknull(formfill.getString("securityCategory")));
			bean.setMarketType(StringUtility.checknull(formfill.getString("marketType")));
			bean.setSecurityName(StringUtility.checknull(formfill.getString("securityName")));
			bean.setNoofBonds(StringUtility.checknull(formfill.getString("noofBonds")));
			bean.setInvestAmt(StringUtility.checknull(formfill.getString("investAmt")));
			bean.setFaxNumber(StringUtility.checknull(formfill.getString("faxNumber")));
			bean.setArrangerDate(StringUtility.checknull(formfill.getString("arrangerDate")));
			bean.setAuctionDate(StringUtility.checknull(formfill.getString("auctionDate")));
			bean.setStatueoftaxOption(StringUtility.checknull(formfill.getString("statueoftaxOption")));
			bean.setNameofApplicant(StringUtility.checknull(formfill.getString("nameofApplicant")));
			bean.setPanno(StringUtility.checknull(formfill.getString("panno")));
			bean.setMailingAddress(StringUtility.checknull(formfill.getString("mailingAddress")));
			bean.setContactPerson(StringUtility.checknull(formfill.getString("contactPerson")));
			bean.setContactNumber(StringUtility.checknull(formfill.getString("contactNumber")));
			bean.setExtName(StringUtility.checknull(formfill.getString("extName")));
			bean.setAccountNo(StringUtility.checknull(formfill.getString("accountNo")));
			bean.setPartyName(StringUtility.checknull(formfill.getString("partyName")));
			bean.setPartyAddress(StringUtility.checknull(formfill.getString("partyAddress")));
			bean.setPartyContactNo(StringUtility.checknull(formfill.getString("partyContactNo")));
			bean.setPname(StringUtility.checknull(formfill.getString("pname")));
			bean.setPid(StringUtility.checknull(formfill.getString("pid")));
			bean.setClientId(StringUtility.checknull(formfill.getString("clientId")));
			bean.setMode(StringUtility.checknull(mode));
			
			bean.setFilePath(getServlet().getServletContext().getRealPath("/")+"/uploads/documents");
			log.info("this is calling file upload");
			bean.setUploadDocument((FormFile)formfill.getMultipartRequestHandler().getFileElements().get("uploadDocument"));
			FormFillingService.getInstance().saveFormFilling(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("formfillingsave").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("quotation",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("proposalRefList",proposalRefList);
			req.setAttribute("taxoption",taxoption);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward showEditFormFilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("showformfillingedit");
		DynaValidatorForm formfill=(DynaValidatorForm)af;
		FormFillingBean bean=new FormFillingBean(req);
		List taxoption=null;
		List banks=null;
		String mode="";
		mode=StringUtility.checknull(req.getParameter("mode"));
		if(mode.equals("rbiauction"))
		{
			forward=am.findForward("showformfillingrbiedit");
		}
		else if(mode.equals("PSUPrimary"))
		{
			
			forward=am.findForward("showformfillingedit");
		}
		try{
			bean.setFormCd(StringUtility.checknull(formfill.getString("formCd")));
			bean=FormFillingService.getInstance().findFormFill(bean);
			taxoption=FormFillingService.getInstance().getStatueOfTaxOption();
			banks=BankMasterService.getInstance().search(new BankMasterInfo());
			req.setAttribute("banks",banks);
			req.setAttribute("taxoption",taxoption);
			req.setAttribute("formfillBean",bean);
			
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("quotation",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward updateformfilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		log.info("this is....calling....");
		ActionForward forward=am.findForward("formfillingedit");
		
		
		DynaValidatorForm formfill=(DynaValidatorForm)af;
		FormFillingBean bean=new FormFillingBean(req);
		List taxoption=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try
		{
			bean.setFormCd(StringUtility.checknull(formfill.getString("formCd")));
			taxoption=FormFillingService.getInstance().getStatueOfTaxOption();
			bean.setNoofBonds(StringUtility.checknull(formfill.getString("noofBonds")));
			bean.setSecurityName(StringUtility.checknull(formfill.getString("securityName")));
			bean.setStatueoftaxOption(StringUtility.checknull(formfill.getString("statueoftaxOption")));
			bean.setNameofApplicant(StringUtility.checknull(formfill.getString("nameofApplicant")));
			bean.setPanno(StringUtility.checknull(formfill.getString("panno")));
			bean.setMailingAddress(StringUtility.checknull(formfill.getString("mailingAddress")));
			bean.setContactPerson(StringUtility.checknull(formfill.getString("contactPerson")));
			bean.setContactNumber(StringUtility.checknull(formfill.getString("contactNumber")));
			bean.setExtName(StringUtility.checknull(formfill.getString("extName")));
			bean.setAccountNo(StringUtility.checknull(formfill.getString("accountNo")));
			bean.setPartyName(StringUtility.checknull(formfill.getString("partyName")));
			bean.setPartyAddress(StringUtility.checknull(formfill.getString("partyAddress")));
			bean.setPartyContactNo(StringUtility.checknull(formfill.getString("partyContactNo")));
			bean.setPname(StringUtility.checknull(formfill.getString("pname")));
			bean.setPid(StringUtility.checknull(formfill.getString("pid")));
			bean.setClientId(StringUtility.checknull(formfill.getString("clientId")));
			bean.setFilePath(getServlet().getServletContext().getRealPath("/")+"/uploads/documents");
			log.info("this is calling file upload"+bean.getExtName());
			bean.setUploadDocument((FormFile)formfill.getMultipartRequestHandler().getFileElements().get("uploadDocument"));
			
			FormFillingService.getInstance().updateFormFill(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("formfillingedit").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("quotation",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("taxoption",taxoption);
			forward = am.getInputForward();
		}
		return forward;
	}
	public ActionForward deleteFormFilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward=am.findForward("formfillingsearch");
		List trustList=null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try{
			trustList=TrustTypeService.getInstance().getTrustTypes();
			
			
				String formCd=req.getParameter("deleteall");
				FormFillingService.getInstance().deleteFormFilling(formCd);
				StringBuffer newPath =
			        new StringBuffer(am.findForward("formfillingsearch").getPath());
			    newPath.append("&mode="+mode);
			     forward= (new ActionForward(newPath.toString(), true));
		
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("formfilling",new ActionMessage("formfilling.Errors", e.getMessage()));
			saveErrors(req,errors);
			req.setAttribute("trustList",trustList);
			forward = am.getInputForward();
		}
		
		
		return forward;
	}
	public ActionForward approvalFormFilling(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward =null;
		String formCd = req.getParameter("formCd");
		FormFillingBean bean = new FormFillingBean(req);
		FormFillingAppBean appBean = null;
		String refNo="";
		String mode=StringUtility.checknull(req.getParameter("mode"));
		String updatedFlag=StringUtility.checknull(req.getParameter("updatedFlag"));
		
		if(mode.equals("PSUPrimary"))
			forward=am.findForward("formfillingProposal");
		else if(mode.equals("rbiauction"))
			forward=am.findForward("formfillingrbiProposal");
		
		try {
			req.setAttribute("taxoption", FormFillingService.getInstance().getStatueOfTaxOption());
			
			req.setAttribute("edit", StringUtility.checknull(req.getParameter("edit")));
			
			String loginUserID = bean.getLoginUserId();
			refNo=FormFillingService.getInstance().getproposalRefno(formCd);
				
			bean = FormFillingService.getInstance().findapprovalformFilling(formCd);
			
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			
						
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (FormFillingAppBean)approvals.get(level);
				approvals.put(level,getUserDetails(appBean,req));
			}				
			
			appBean = new FormFillingAppBean();
			appBean.setApprovedBy(loginUserID);			
			approvals.put("3",getUserDetails(appBean,req));
			
			bean.setApprovals(approvals);
			bean.setUpdatedFlag(updatedFlag);
			req.setAttribute("proposal", bean);
			//InvestmentProposalService service = InvestmentProposalService.getInstance();
			//req.setAttribute("InvestDetails",service.getInvestmentDetails(refNo,mode));
			req.setAttribute("finYear",FormFillingService.getInstance().getFinYear(refNo));
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("formfilling", new ActionMessage("formfilling.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;

	}
	
	private FormFillingAppBean getUserDetails(FormFillingAppBean appBean,HttpServletRequest req){
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
	public ActionForward approvalUpdate(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		
		DynaValidatorForm formfill = (DynaValidatorForm) af;
		ActionForward forward = am.findForward("formfillingSearch");
		FormFillingAppBean bean = new FormFillingAppBean(req);
		
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try {
			String updatedFlag = StringUtility.checknull(formfill.getString("updatedFlag"));
			
			
			 if("Y".equals(StringUtility.checknull(formfill.getString("edit")))){
				 updatedFlag = String.valueOf(Integer.parseInt(StringUtility.checknull(formfill.getString("updatedFlag")))-1);
			 }
			 
			 bean.setFormCd(StringUtility.checknull(formfill.getString("formCd")));
			 
			 
			 bean.setDate(StringUtility.checknull(formfill.getString("approveDate")));
			 bean.setRemarks(StringUtility.checknull(formfill.getString("approvalRemarks")));
			 bean.setApprovalLevel(updatedFlag);
			 
			FormFillingService.getInstance().approvalUpdate(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("formfillingSearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("formfilling", new ActionMessage("formfilling.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;

	}
	public ActionForward generateFormFillingReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		log.info("the action is.... callling....");
		ActionForward forward = null;
		String formCd = req.getParameter("formCd");
		FormFillingBean bean = new FormFillingBean(req);
		FormFillingAppBean appBean = null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		if(mode.equals("PSUPrimary"))
			forward=am.findForward("formfillingreport");
		else if(mode.equals("rbiauction"))
			forward=am.findForward("formfillingrbireport");
		
		try{
			bean = FormFillingService.getInstance().findapprovalformFilling(formCd);
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (FormFillingAppBean)approvals.get(level);
				approvals.put(level,getUserDetails(appBean,req));
			}				
			bean.setApprovals(approvals);						
			req.setAttribute("proposal", bean);
			req.setAttribute("finYear",InvestmentProposalService.getInstance().getFinYear());
		}
		catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("formfilling", new ActionMessage("formfilling.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;
	}
}
