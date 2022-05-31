package com.epis.action.advances;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;
import com.epis.services.advances.CPFPTWAdvanceService;
import com.epis.services.CommonService;

public class ReportAction  extends DispatchAction{
	CommonUtil commonUtil=new CommonUtil();	
	CommonService commonService=new CommonService();
	HttpSession session=null;
	Log log = new Log(ReportAction.class);
	public ActionForward loadReportParam(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;		
	  	dynaValide.set("year","");	
		dynaValide.set("month","");	
		dynaValide.set("region","");	
				
		return mapping.findForward("loadReportParamForm");
	}

}
