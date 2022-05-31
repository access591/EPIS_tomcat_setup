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
import com.epis.bean.admin.RegionBean;
import com.epis.services.admin.RegionService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class RegionAction extends DispatchAction {
	Log log = new Log(RegionAction.class);

	public ActionForward showRegionAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("showregionnew");
	}

	public ActionForward addRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	{
		
		DynaValidatorForm region = (DynaValidatorForm) form;
		RegionBean bean = new RegionBean(request);
		ActionForward forward = mapping.findForward("regionsearch");
		
		try {
			bean.setRegionName(StringUtility.checknull(region.getString("regionName")));
			bean.setAaiCategory(StringUtility.checknull(region.getString("aaiCategory")));
			bean.setRemarks(StringUtility.checknull(region.getString("remarks")));
			RegionService.getInstance().saveRegion(bean);
		} catch (Exception e) {
			log.error("RegionAction:addRegion:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("region", new ActionMessage("region.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("showregionsearch");
		
		try {
			List regionList = null;
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			regionList = RegionService.getInstance().searchRegion(
					StringUtility.checknull(dyna.getString("regionName")),StringUtility.checknull(dyna.getString("aaiCategory")));
			request.setAttribute("regionList", regionList);
		} catch (Exception e) {			
			log.error("RegionAction:searchRegion:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("region", new ActionMessage("region.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String regioncd = request.getParameter("regioncd");
		ActionForward forward =mapping.findForward("showregionedit");
		
		try {
			RegionBean bean = new RegionBean();
			bean = RegionService.getInstance().findRegion(regioncd);
			request.setAttribute("region", bean);
		} catch (Exception e) {
			log.error("RegionAction:showEditRegion:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("region", new ActionMessage("region.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		
		DynaValidatorForm region = (DynaValidatorForm) form;
		RegionBean bean = new RegionBean(request);
		ActionForward forward =mapping.findForward("regionsearch");
		
		try {
			bean.setRegionCd(StringUtility.checknull(region.getString("regionCd")));
			bean.setRegionName(StringUtility.checknull(region.getString("regionName")));
			bean.setAaiCategory(StringUtility.checknull(region.getString("aaiCategory")));
			bean.setRemarks(StringUtility.checknull(region.getString("remarks")));
			RegionService.getInstance().editRegion(bean);
		} catch (Exception e) {
			log.error("RegionAction:updateRegion:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("region", new ActionMessage("region.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("region", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		
		ActionForward forward =mapping.findForward("regionemptysearch");
		
		try {
			String regioncds = request.getParameter("deleteall");
			RegionService.getInstance().deleteRegion(regioncds);
		} catch (Exception e) {
			log.error("RegionAction:deleteRegion:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("region", new ActionMessage("region.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		
		return forward;
	}

}
