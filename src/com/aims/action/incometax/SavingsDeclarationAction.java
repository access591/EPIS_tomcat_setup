/*
 * Created on Sep 15, 2009
 */
package com.aims.action.incometax;


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

import com.aims.dao.PayRollCommonDAO;

import com.aims.info.incometax.ProjectedIncomeTaxInfo;
import com.aims.info.payrollprocess.PayInputParamInfo;

import com.aims.service.IncomeTaxService;

import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.CommonUtil;


/**
 * @author Srilakshmi E
 */

public class SavingsDeclarationAction extends DispatchAction {

	
	
	public ActionForward showProjectedIncomeTaxParam(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpsession=request.getSession(true);
		CommonDAO commonDAO=new CommonDAO();
		LoginInfo logInfo = new LoginInfo();
		String employeeName="",pensionNo="",employeeno="";
		EmployeePersonalInfo personalData=new EmployeePersonalInfo();
		
		 logInfo = (LoginInfo) httpsession.getAttribute("user");
		 log.info("CommonRPFCAction::Pension No"+logInfo.getPensionNo()+"User Type"+logInfo.getUserType()+"Region"+logInfo.getRegion());
		 personalData=commonDAO.getEmployeePersonalInfo("NO-SELECT","","","",logInfo.getPensionNo());
		 employeeno=PayRollCommonDAO.getEmpno(logInfo.getPensionNo());
		 employeeName=personalData.getEmployeeName();
		 pensionNo=logInfo.getPensionNo();
		 PayInputParamInfo pinput=new PayInputParamInfo();
		 log.info("employeeno"+employeeno);
		 pinput.setEmployeeno(employeeno);
		 pinput.setEmployeeName(employeeName);
		 pinput.setPensionNo(pensionNo);
		 pinput.setStncode("CHQD");
		 httpsession.setAttribute("pinput",pinput);
		return mapping.findForward("ProjectedIncomeTaxParam");
	}
	public ActionForward getProjectedIncomeTaxInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dyna = (DynaValidatorForm) form;
		HttpSession session = request.getSession();
		String empno="",station="";
		ProjectedIncomeTaxInfo info = new ProjectedIncomeTaxInfo(); 
		if(request.getParameter("frm_empno")!=null){
			empno=request.getParameter("frm_empno");
		}
		if(request.getParameter("frmAirportCode")!=null){
			station=request.getParameter("frmAirportCode");
		}
		try{
			List projITList = IncomeTaxService.getInstance().getProjectedIncomeTaxInfo(empno,"37");
			request.setAttribute("projITList",projITList);
			request.setAttribute("fyearnm",PayRollCommonDAO.getNameFromCd("financialyear","FYEARNM","FYEARCD","37"));
			request.setAttribute("stationName",PayRollCommonDAO.getNameFromCd("stationmaster","STATIONNAME","STATIONCD",station).toUpperCase());
			
		}catch(Exception e){
			String str = e.getMessage();
			ActionMessages errors = new ActionMessages();  
			errors.add("cd",new ActionMessage("errors.cdexists",str.substring(CommonUtil.returnIndexVal(str),str.length())));
			saveErrors(request,errors);
			return mapping.getInputForward();
		}
		return mapping.findForward("ProjectedIncomeTax");
	}

}
