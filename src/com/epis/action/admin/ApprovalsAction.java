package com.epis.action.admin;

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
import com.epis.bean.admin.ApprovalsBean;
import com.epis.services.admin.ApprovalsService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ApprovalsAction extends DispatchAction {
	Log log = new Log(ApprovalsAction.class);

	public ActionForward showApprovalAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		return mapping.findForward("showapprovalsnew");
	}

	public ActionForward addApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)			 {
		
		DynaValidatorForm approval = (DynaValidatorForm) form;
		ApprovalsBean bean = new ApprovalsBean(request);
		ActionForward forward = mapping.findForward("approvalssearch");
		
		try {
			bean.setApprovalName(StringUtility.checknull(approval.getString("approvalName")));
			bean.setDescription(StringUtility.checknull(approval.getString("description")));
			ApprovalsService.getInstance().saveApproval(bean);
		} catch (Exception e) {
			log.error("ApprovalsAction:addApproval:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("approvals", new ActionMessage("approvals.Errors", e.getMessage()));
			saveErrors(request, errors);
			
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("showapprovalssearch");
		try {
			List optionList = null;			
			DynaValidatorForm dyna = (DynaValidatorForm) form;			
			optionList = ApprovalsService.getInstance().searchApproval(
					StringUtility.checknull(dyna.getString("approvalCode")),StringUtility.checknull(dyna.getString("approvalName")));
			request.setAttribute("approvalsList", optionList);
			
		} catch (Exception e) {			
			log.error("ApprovalsAction:searchOption:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("approvals", new ActionMessage("approvals.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String approvalcode = request.getParameter("approvalcode");
		ActionForward forward =mapping.findForward("showapprovalsedit");
		try {
			ApprovalsBean bean = new ApprovalsBean();			
		    bean = ApprovalsService.getInstance().findApproval(approvalcode);		
		    request.setAttribute("approvals", bean);
		} catch (Exception e) {
			log.error("ApprovalsAction:showEditApproval:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("approvals", new ActionMessage("approvals.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm approval = (DynaValidatorForm) form;
		ApprovalsBean bean = new ApprovalsBean(request);
		ActionForward forward =mapping.findForward("approvalssearch");
		
		try {
			bean.setApprovalCode(StringUtility.checknull(approval.getString("approvalCode")));
			bean.setApprovalName(StringUtility.checknull(approval.getString("approvalName")));
			bean.setDescription(StringUtility.checknull(approval.getString("description")));
			ApprovalsService.getInstance().editApproval(bean);
		} catch (Exception e) {
			log.error("ApprovalsAction:updateApproval:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("approvals", new ActionMessage("approvals.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("approvals", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("approvalsemptysearch");
		
		try {
			String approvalcds = request.getParameter("deleteall");
			ApprovalsService.getInstance().deleteApproval(approvalcds);
		} catch (Exception e) {
			log.error("ApprovalsAction:deleteApproval:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("approvals", new ActionMessage("approvals.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		
		return forward;
	}

}
