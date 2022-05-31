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
import com.epis.bean.admin.UnitBean;
import com.epis.services.admin.RegionService;
import com.epis.services.admin.UnitService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class UnitAction extends DispatchAction {
	Log log = new Log(UnitAction.class);

	public ActionForward showUnitAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
			ActionForward forward = mapping.findForward("showunitnew");
			try{
				List regionList=null;
				regionList=RegionService.getInstance().getRegionList();
				request.setAttribute("regions", regionList);
			}catch (Exception e) {
				log.error("UnitAction:showUnitAdd:Exception:"+e.getMessage());
				ActionMessages errors = new ActionMessages();
				errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
				saveErrors(request, errors);
				forward = mapping.getInputForward();
			}
		return forward;
	}

	public ActionForward addUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaValidatorForm unit = (DynaValidatorForm) form;
		UnitBean bean = new UnitBean(request);
		ActionForward forward = mapping.findForward("unitsearch");
		
		try {
			bean.setUnitCode(StringUtility.checknull(unit.getString("unitCode")));
			bean.setUnitName(StringUtility.checknull(unit.getString("unitName")));
			bean.setOption(StringUtility.checknull(unit.getString("option")));
			bean.setRegion(StringUtility.checknull(unit.getString("region")));
			UnitService.getInstance().saveUnit(bean);
		} catch (Exception e) {
			log.error("UnitAction:addUnit:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchUnit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("showunitsearch");
		
		try {
			List unitList = null;
			List regionList=null;
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			regionList=RegionService.getInstance().getRegionList();
			unitList = UnitService.getInstance().searchUnit(
					StringUtility.checknull(dyna.getString("unitCode")),StringUtility.checknull(dyna.getString("unitName")),StringUtility.checknull(dyna.getString("option")),StringUtility.checknull(dyna.getString("region")));
			request.setAttribute("unitList", unitList);
			request.setAttribute("regions", regionList);
		} catch (Exception e) {			
			log.error("UnitAction:searchUnit:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward showEditUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String unitcode = request.getParameter("unitcode");
		ActionForward forward =mapping.findForward("showunitedit");
		
		try {
			UnitBean bean = new UnitBean();
			List regionList=null;
			bean = UnitService.getInstance().findUnit(unitcode);
			regionList=RegionService.getInstance().getRegionList();
			request.setAttribute("regions", regionList);
			request.setAttribute("unit", bean);
		} catch (Exception e) {
			log.error("UnitAction:showEditUnit:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward updateUnit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm unit = (DynaValidatorForm) form;
		UnitBean bean = new UnitBean(request);
		ActionForward forward =mapping.findForward("unitsearch");
		
		try {
			bean.setUnitCode(StringUtility.checknull(unit.getString("unitCode")));
			bean.setUnitName(StringUtility.checknull(unit.getString("unitName")));
			bean.setOption(StringUtility.checknull(unit.getString("option")));
			bean.setRegion(StringUtility.checknull(unit.getString("region")));
			UnitService.getInstance().editUnit(bean);
		} catch (Exception e) {
			log.error("UnitAction:updateUnit:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
			request.setAttribute("unit", bean);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward deleteUnit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward forward =mapping.findForward("unitemptysearch");
		
		try {
			String unitcds = request.getParameter("deleteall");
			UnitService.getInstance().deleteUnit(unitcds);
		} catch (Exception e) {
			log.error("UnitAction:deleteUnit:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("unit", new ActionMessage("unit.Errors", e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		
		return forward;
	}

}
