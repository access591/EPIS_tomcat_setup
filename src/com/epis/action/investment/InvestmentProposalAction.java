package com.epis.action.investment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.bean.investment.InvestmentProposalBean;
import com.epis.bean.investment.InvestmentProposalDt;
import com.epis.common.exception.EPISException;
import com.epis.dao.admin.UserDAO;
import com.epis.services.investment.InvestmentProposalService;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class InvestmentProposalAction extends DispatchAction {
	Log log = new Log(InvestmentProposalAction.class);

	public ActionForward showProposalAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String mode=StringUtility.checknull(request.getParameter("mode"));
		ActionForward forward=null;
		if(mode.equals("psuprimary"))
		{
			log.info("this is psuprimary....");
			forward=mapping.findForward("showpsuproposalnew");
		}
		else if(mode.equals("rbiauction"))
		{
			forward=mapping.findForward("showrbiproposalnew");
		}
		else
		{
			forward=mapping.findForward("showproposalnew");
		}
		
		try {
			request.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());
			request.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories(mode));
			request.setAttribute("marketTypeList", QuotationRequestService.getInstance()
					.getMarketTypeList(mode));
		} catch (Exception e) {
			log.error("UnitAction:showUnitAdd:Exception:" + e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward addProposal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JspException {
		
		DynaValidatorForm proposal = (DynaValidatorForm) form;
		InvestmentProposalBean bean = new InvestmentProposalBean(request);
		ActionForward forward = mapping.findForward("proposalsearch");
		String mode=StringUtility.checknull(request.getParameter("mode"));
		List trusttypelist=null;
		List categorylist=null;
		List marketlist=null;
		try {
			
			trusttypelist=TrustTypeService.getInstance().getTrustTypes();
			categorylist=SecurityCategoryService.getInstance().getSecurityCategories(mode);
			marketlist=QuotationRequestService.getInstance().getMarketTypeList(mode);
			
			bean.setAmountInv(StringUtility.checknull(proposal
					.getString("amountInv")));
			bean.setRefNo(StringUtility.checknull(proposal.getString("refNo")));
			bean.setSubject(StringUtility.checknull(proposal
					.getString("subject")));
			bean.setProposaldate(StringUtility.checknull(proposal
					.getString("proposaldate")));
			bean.setRemarks(StringUtility.checknull(proposal
					.getString("remarks")));
			bean.setSecurityCat(StringUtility.checknull(proposal
					.getString("securityCat")));
			bean.setTrustType(StringUtility.checknull(proposal
					.getString("trustType")));
			log.info("the securitycategory is..."+proposal
					.getString("trustType"));
			bean.setMarketType(StringUtility.checknull(proposal
					.getString("marketType")));
			
			
			
			List investdts=new ArrayList();
			InvestmentProposalDt investdt=null;
			if(request.getParameterValues("achieveDetails")!=null)
			{
				
				String detailRecords[] = request
				.getParameterValues("achieveDetails");
				
				for(int i=0; i<detailRecords.length; i++)
				{
					
					
					 investdt=new InvestmentProposalDt();
					 investdt.setSecurityName(detailRecords[i]);
					 
					 investdts.add(investdt);
					
				}
			}
			bean.setSecurityDetails(investdts);
			InvestmentProposalService.getInstance()
			.saveInvestmentProposal(bean);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("proposalsearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
			
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("trust", new ActionMessage("proposal.Errors", e
					.getMessage()));
			request.setAttribute("trustRecords",trusttypelist);
			request.setAttribute("categoryRecords",categorylist);
			request.setAttribute("marketTypeList",marketlist);
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchInvestProposal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionForward forward = null;
		ActionMessages errors = new ActionMessages();
		String mode=StringUtility.checknull(request.getParameter("mode"));
		
		if(mode.equals("psuprimary"))
			forward=mapping.findForward("showpsuproposalsearch");
		else if(mode.equals("rbiauction"))
			forward=mapping.findForward("showrbiproposalsearch");
		else
			forward=mapping.findForward("showproposalsearch");
		

		try {
			DynaValidatorForm dyna = (DynaValidatorForm) form;

			InvestmentProposalBean propBean = new InvestmentProposalBean();
			propBean.setRefNo(StringUtility.checknull(dyna.getString("refNo")));
			propBean.setTrustType(StringUtility.checknull(dyna
					.getString("trustType")));
			propBean.setSecurityCat(StringUtility.checknull(dyna
					.getString("securityCat")));
			propBean.setMode(mode);

			request.setAttribute("proposalList", InvestmentProposalService
					.getInstance().searchInvestmentProposal(propBean));

			request.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());

			request.setAttribute("categoryRecords", SecurityCategoryService
					.getInstance().getSecurityCategories());
			
		} catch (Exception e) {
			errors.add("trust", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		if (request.getParameter("error") != null) {
			errors.add("quotation", new ActionMessage("proposal.Errors",
					request.getParameter("error")));
			saveErrors(request, errors);
		}
		return forward;
	}

	public ActionForward showEditProposal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward=null;
		String refNo = request.getParameter("refNo");
		
		InvestmentProposalBean bean = new InvestmentProposalBean();
		String mode=StringUtility.checknull(request.getParameter("mode"));
		if(mode.equals("psuprimary"))
			forward = mapping.findForward("showpsuproposaledit");
		else if(mode.equals("rbiauction"))
			forward=mapping.findForward("showrbiproposaledit");
		else
			forward = mapping.findForward("showproposaledit");
		 
		try {
			bean = InvestmentProposalService.getInstance().findProposal(
					refNo, "edit");
			request.setAttribute("proposal", bean);

			request.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());

			request.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories(mode));
			
			request.setAttribute("marketTypeList", QuotationRequestService.getInstance()
					.getMarketTypeList(mode));
			
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward editProposal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm proposal = (DynaValidatorForm) form;
		InvestmentProposalBean bean = new InvestmentProposalBean(request);
		ActionForward forward = mapping.findForward("proposalsearch");
		String mode=StringUtility.checknull(request.getParameter("mode"));
		System.out.println("in action  aniiiiii edit");
		try {
			// bean.setProposalCD(StringUtility.checknull(proposal.getString("proposalCD")));
			bean.setAmountInv(StringUtility.checknull(proposal
					.getString("amountInv")));
			bean.setRefNo(StringUtility.checknull(proposal.getString("refNo")));
			bean.setRemarks(StringUtility.checknull(proposal
					.getString("remarks")));
			bean.setSecurityCat(StringUtility.checknull(proposal
					.getString("securityCat")));
			bean.setTrustType(StringUtility.checknull(proposal
					.getString("trustType")));
			bean.setSubject(StringUtility.checknull(proposal
					.getString("subject")));
			bean.setProposaldate(StringUtility.checknull(proposal
					.getString("proposaldate")));
			bean.setMarketType(StringUtility.checknull(proposal
					.getString("marketType")));
			
			
			List investdts=new ArrayList();
			InvestmentProposalDt investdt=null;
			if(request.getParameterValues("achieveDetails")!=null)
			{
				
				String detailRecords[] = request
				.getParameterValues("achieveDetails");
				
				for(int i=0; i<detailRecords.length; i++)
				{
					
					
					 investdt=new InvestmentProposalDt();
					 investdt.setSecurityName(detailRecords[i]);
					 
					 investdts.add(investdt);
					
				}
			}
			bean.setSecurityDetails(investdts);

			InvestmentProposalService.getInstance()
					.editInvestmentProposal(bean);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("proposalsearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteProposal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = mapping.findForward("proposalsearch");
		String mode=StringUtility.checknull(request.getParameter("mode"));
		try {
			String refnos = request.getParameter("deleteall");
			InvestmentProposalService.getInstance().deleteInvestmentProposal(
					refnos);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("proposalsearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			StringBuffer newPath = new StringBuffer(mapping.findForward(
					"proposalsearch").getPath());
			newPath.append("&error=" + e.getMessage());
			forward = (new ActionForward(newPath.toString(), true));
		}
		return forward;
	}

	public ActionForward approvalProposal(ActionMapping am, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ActionForward forward = null;
		String refNo = req.getParameter("refNo");
		InvestmentProposalBean bean = new InvestmentProposalBean(req);
		InvestProposalAppBean appBean = null;
		String mode=StringUtility.checknull(req.getParameter("mode"));
		String updatedFlag=StringUtility.checknull(req.getParameter("updatedFlag"));
		if(mode.equals("psuprimary"))
			forward=am.findForward("approvalpsuProposal");
		else if(mode.equals("rbiauction"))
			forward=am.findForward("approvalrbiProposal");
		else
			forward=am.findForward("approvalProposal");
		try {
			req.setAttribute("trustRecords", TrustTypeService.getInstance()
					.getTrustTypes());

			req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories(mode));
			
			req.setAttribute("marketTypeList", QuotationRequestService.getInstance()
					.getMarketTypeList(mode));
			req.setAttribute("edit", StringUtility.checknull(req.getParameter("edit")));
			
			String loginUserID = bean.getLoginUserId();
				
			bean = InvestmentProposalService.getInstance().findProposal(refNo, "approve");
			
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			
						
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (InvestProposalAppBean)approvals.get(level);
				approvals.put(level,getUserDetails(appBean,req));
			}				
			
			appBean = new InvestProposalAppBean();
			appBean.setApprovedBy(loginUserID);			
			approvals.put("8",getUserDetails(appBean,req));
			
			bean.setApprovals(approvals);
			bean.setUpdatedFlag(updatedFlag);
			req.setAttribute("proposal", bean);
			InvestmentProposalService service = InvestmentProposalService.getInstance();
			req.setAttribute("InvestDetails",service.getInvestmentDetails(refNo,mode));
			req.setAttribute("finYear",service.getFinYear());
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;
	}
	
	private InvestProposalAppBean getUserDetails(InvestProposalAppBean appBean,HttpServletRequest req){
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
		ActionForward forward = am.findForward("proposalsearch");
		InvestProposalAppBean bean = new InvestProposalAppBean(req);
		String mode=StringUtility.checknull(req.getParameter("mode"));
		try {
			String updatedFlag = StringUtility.checknull(proposal.getString("updatedFlag"));
			 if("Y".equals(StringUtility.checknull(proposal.getString("edit")))){
				 updatedFlag = String.valueOf(Integer.parseInt(StringUtility.checknull(proposal.getString("updatedFlag"))));
			 }
			 bean.setRefNo(StringUtility.checknull(proposal.getString("refNo")));
			 bean.setDate(StringUtility.checknull(proposal.getString("approveDate")));
			 bean.setRemarks(StringUtility.checknull(proposal.getString("approvalRemarks")));
			 bean.setApprovalLevel(updatedFlag);
			InvestmentProposalService.getInstance().approvalUpdate(bean);
			StringBuffer newPath =
		        new StringBuffer(am.findForward("proposalsearch").getPath());
		    newPath.append("&mode="+mode);
		     forward= (new ActionForward(newPath.toString(), true));

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;
	}

	public ActionForward getReport(ActionMapping am, ActionForm af,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward = am.findForward("proposalReport");
		InvestmentProposalBean bean = new InvestmentProposalBean();
		String refNo = req.getParameter("refNo");
		String mode=StringUtility.checknull(req.getParameter("mode"));
		InvestProposalAppBean appBean = null;
		try {
			bean = InvestmentProposalService.getInstance().findProposal(refNo, "approve");			
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			
						
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			int count=0;
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (InvestProposalAppBean)approvals.get(level);
				if(StringUtility.checknull(appBean.getApproved()).equals("A"))
					count++;
				approvals.put(level,getUserDetails(appBean,req));
			}				
			
			bean.setApprovals(approvals);
			bean.setAppCount(count+"");
			req.setAttribute("proposal", bean);
			InvestmentProposalService service = InvestmentProposalService.getInstance();
			req.setAttribute("InvestDetails",service.getInvestmentDetails(refNo,mode));
			req.setAttribute("finYear",service.getFinYear());

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("proposal", new ActionMessage("proposal.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}

		return forward;
	}

}
