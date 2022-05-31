package com.aims.action.payrollprocess;

import java.io.PrintWriter;

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



import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.Log; 

import com.aims.dao.PayRollCommonDAO;
import com.aims.dao.RegionDAO;



import com.aims.info.payrollprocess.PayInputParamInfo;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.payrollprocess.PayrollProcessInfo;


import com.aims.service.PayrollProcessService;


public class PayrollProcessAction extends DispatchAction{

	Log log=new Log(PayrollProcessAction.class);
	RegionDAO  region = new RegionDAO(); 
	
	public ActionForward loadPaySlipParamsPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
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
		 
		return mapping.findForward("showPaySlipParam");
	}
	public ActionForward viewPaySlip(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
		PaySlipInfo psinfo = new PaySlipInfo();
	
		String fwd = "viewPaySlip";
		try{
			String type = "",id="",reportType = "",station="",mnthDecr="",year="";
			
		
			if(request.getParameter("frm_empno")!=null){
				psinfo.setEmpNo(request.getParameter("frm_empno"));
			}
			if(request.getParameter("frm_mnthid")!=null){
				id=request.getParameter("frm_mnthid");
			}
			if(request.getParameter("frm_reportType")!=null){
				reportType=request.getParameter("frm_reportType");
			}
			if(request.getParameter("frmAirportCode")!=null){
				station=request.getParameter("frmAirportCode");
			}
			if(request.getParameter("frm_mnthdescr")!=null){
				mnthDecr=request.getParameter("frm_mnthdescr");
			}
		
			if(request.getParameter("frm_year")!=null){
				year=request.getParameter("frm_year");
			}
			
			psinfo.setMonth(mnthDecr);
			//psinfo.setStation(dynaForm.getString("station"));
			
			
			psinfo.setPaybillcd("");
			psinfo.setDiscipline("");
			log.info("station:: "+station+"payrollid"+id);
			//`dynaForm.get
		
			psinfo.setPayrollmonthid(id);
			psinfo.setStation(station);
			//finance year is hard code.need to retrive from financialyear table
			psinfo.setFyearcd("37");
			
			psinfo.setYear(year);
		
			psinfo.setType(type);
			List list = PayrollProcessService.getInstance().viewPaySlip(psinfo);
			request.setAttribute("stationName",PayRollCommonDAO.getNameFromCd("stationmaster","STATIONNAME","STATIONCD",station).toUpperCase());
			request.setAttribute("payrollmonth",PayRollCommonDAO.getNameFromCd("monthlypayroll","PAYROLLMONTHNM||'-'||PAYROLLYEAR","PAYROLLMONTHID",id));
			request.setAttribute("empPayList",list);
		
		
			fwd = "viewPaySlip";
			
		}catch(Exception e){
			log.printStackTrace(e);
			String str = e.getMessage();
			ActionMessages errors = new ActionMessages();  
			errors.add("schdApplCd",new ActionMessage("errors.cdexists",str.substring(returnIndexVal(str),str.length())));
			saveErrors(request,errors);
			return mapping.getInputForward();
		}
		log.info("--------fwd is ------ "+fwd);
		return mapping.findForward(fwd);
	}

	public int returnIndexVal(String str){
		int indx = 0;
		if(str.lastIndexOf("Exception:")!=-1)
			indx = str.lastIndexOf("Exception:")+10;
		return indx;
	}

	
	
	
	
	public ActionForward getPayMonths(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String discd = request.getParameter("discd");
		String staffctgrycd = request.getParameter("stctgrycd");
		PayrollProcessInfo ppinfo  = new PayrollProcessInfo();
		ppinfo.setDisciplinecd(new Integer(discd).intValue());
		ppinfo.setStaffctgrycd(new Integer(staffctgrycd).intValue());
		String paidMonths = PayrollProcessService.getInstance().getPayMonths(ppinfo);
		try{
		 	response.setContentType("text/html");
		 	response.setHeader("Pragma","no-cache");//for HTTP1.0
			response.setHeader("cache-control","no-cache");// for HTTP/1.1.
	        PrintWriter out = response.getWriter();  
	        out.write(paidMonths);
	 	}catch(Exception e){
	 		
	 	}
		return null;
	}

}
