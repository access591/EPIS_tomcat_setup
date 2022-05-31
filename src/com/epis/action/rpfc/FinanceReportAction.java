package com.epis.action.rpfc;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epis.action.advances.ForwardParameters;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeePensionCardInfo;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.EmployeeValidateInfo;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.PensionBean;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.services.rpfc.FinancialService;
import com.epis.services.rpfc.PensionService;
import com.epis.services.rpfc.PersonalService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class FinanceReportAction extends CommonRPFCAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	PensionService ps = new PensionService();
	FinancialService fs = new FinancialService();
	
	PersonalService personalService = new PersonalService();
	Log log = new Log(FinanceReportAction.class);
	public ActionForward loadmonthlycpfdatasearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		loadPrimarilyInfo(request,"loadtransferdevform");
		return mapping.findForward("loadtransferdevform");
	}

	public ActionForward loadtransfersearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		loadPrimarilyInfo(request,"loadcpfdeviationInput");
		return mapping.findForward("loadcpfdevform");
	}
	public ActionForward deviationsearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("FinanceReportAction : deviationsearch() ");
		String airportCode = "", employeeName = "", fromDt = "", cpfaccno = "",pensionno="", missingPF = "", employeeNumber = "";
		FinacialDataBean bean = new FinacialDataBean();
		ArrayList searchList=new ArrayList();
		
		if (request.getParameter("airPortCD") != null) {
		bean.setAirportCode(request.getParameter("airPortCD").toString());
		} else {
			bean.setAirportCode("");
		}
		if (request.getParameter("pfid") != null) {
			bean.setPensionNumber(commonUtil.getSearchPFID1(request.getParameter("pfid").toString()));
		} else {
			bean.setPensionNumber("");
		}
		
		String contextPath = request.getContextPath().toString();
		bean.setContextPath(contextPath);

	
		if (!request.getParameter("fromDt").equals("")) {
			try {
				bean.setFromDate(commonUtil.converDBToAppFormat(request
						.getParameter("fromDt").toString(), "dd/MM/yyyy",
						"dd/MMM/yyyy"));
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else {
			bean.setFromDate("01/Apr/1995");
		}
		if (!request.getParameter("toDt").equals("")) {
			try {
				bean.setToDate(commonUtil.converDBToAppFormat(request
						.getParameter("toDt").toString(), "dd/MM/yyyy",
						"dd/MMM/yyyy"));
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else {
			bean.setToDate(this.getCurrentDate("dd/MMM/yyyy"));
		}

		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		
		try {
			log.info("From Date" + bean.getFromDate() + "To Date"
					+ bean.getToDate());
			//searchBean = financeService.searchDeviationData(bean, getSearchInfo,gridLength);
			searchList=	fs.searchDeviationData(bean, getSearchInfo,10);
			
			log.info("Size of bean=========="
					+ searchBean.getSearchList().size());
						
			//session.setAttribute("reportfinancebean", searchBean);
		//	session.setAttribute("reportsearchBean", bean);
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		//request.setAttribute("searchBean", searchBean);
		request.setAttribute("region", bean.getRegion().trim());
		request.setAttribute("searchlist",searchList);
		
		log.info("///////////////////////");

		return mapping.findForward("loadcpfdevform");
	}
	private String getCurrentDate(String format) {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.format(new Date());
		return date;
	}
	public ActionForward transfersearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		log.info("transfersearch : dopost() ");
		String airportCode = "", employeeName = "", fromDt = "", cpfaccno = "",pensionno="", missingPF = "", employeeNumber = "";
		FinacialDataBean bean = new FinacialDataBean();
		FinacialDataBean finacialDataBean=new FinacialDataBean();
		ArrayList searchList=new ArrayList();
		ArrayList transferInList = new ArrayList();
		ArrayList transferOutList = new ArrayList();
		HttpSession session=request.getSession(true);
		HashMap regionHashmap = new HashMap();
		regionHashmap = commonUtil.getRegion();
		
		request.setAttribute("regionHashmap", regionHashmap);
		
		if (request.getParameter("select_region") != null) {
		bean.setRegion(request.getParameter("select_region").toString());
		} else {
			bean.setRegion("");
		}
		
		if (request.getParameter("select_airport") != null) {
			bean.setAirportCode(request.getParameter("select_airport").toString());
			} else {
				bean.setAirportCode("");
			}
		
		log.info("--Region---"+bean.getRegion()+"---Airport code----"+bean.getAirportCode());
		
		
		if (request.getParameter("pfid") != null) {
			bean.setPensionNumber(commonUtil.getSearchPFID1(request.getParameter("pfid").toString()));
		} else {
			bean.setPensionNumber("");
		}
		
		String contextPath = request.getContextPath().toString();
		bean.setContextPath(contextPath);

	
	if (!request.getParameter("fromDt").equals("")) {
			try {
				bean.setFromDate(commonUtil.converDBToAppFormat(request
						.getParameter("fromDt").toString(), "dd/MM/yyyy",
						"dd/MMM/yyyy"));
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else {
			bean.setFromDate("01/Apr/1995");
		}
		

		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		
		try {
			log.info("From Date" + bean.getFromDate() + "To Date"
					+ bean.getToDate());
			//searchBean = financeService.searchDeviationData(bean, getSearchInfo,gridLength);
			finacialDataBean=	fs.searcTransferData(bean, getSearchInfo,10);
			
			log.info("Size of bean=========="
					+ searchBean.getSearchList().size());
						
			//session.setAttribute("reportfinancebean", searchBean);
			session.setAttribute("reportsearchBean", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//request.setAttribute("searchBean", searchBean);
		request.setAttribute("region", bean.getRegion().trim());
		
		transferInList=finacialDataBean.getTransferInList();
		transferOutList=finacialDataBean.getTransferOutList();
		
		if(transferInList.size()!=0){
		request.setAttribute("transferInList",transferInList);
		}
		if(transferOutList.size()!=0){
			request.setAttribute("transferOutList",transferOutList);
		}
		
		
		log.info("///////////////////////");

		return mapping.findForward("loadtransferdevform");
	}
	public ActionForward loadtransferdetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "", sortingOrder = "",pfidString="",nextyear="",toYear="",frmSelectedDts="";
		
		ArrayList list = new ArrayList();

		
		String  pensionno = "",status="";
		
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		
		if (request.getParameter("frm_status") != null) {
			status = request.getParameter("frm_status");
		}
		
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
											
		log.info("===AAIEPF1Report=====" + region + "year" + year+"pensionno"+pensionno);					
		
		request.setAttribute("pensionno", pensionno);
		request.setAttribute("status", status);
		request.setAttribute("newairportcode", airportcode);
		request.setAttribute("newregion", region);
		
		loadPrimarilyInfo(request,"loadtransferdevupdateform");
		return mapping.findForward("loadtransferdevupdateform");
	}
	public ActionForward updatetransferdata(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "",status="", fromAirportCode = "",fromRegion="",pfidString="",nextyear="",toYear="",frmSelectedDts="";
		
		ArrayList list = new ArrayList();

		
		String  pensionno = "",seperationreason="",sepetaionDt="";
		
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		
		if (request.getParameter("seperationreason") != null) {
			seperationreason = request.getParameter("seperationreason");
		}
													
		if (request.getParameter("sepetaionDt") != null) {
			sepetaionDt = request.getParameter("sepetaionDt");
		}
		
		if (request.getParameter("airportcode") != null) {
			airportcode = request.getParameter("airportcode");
		}
		
		if (request.getParameter("region") != null) {
			region = request.getParameter("region");
		}
		
		if (request.getParameter("select_region") != null) {
			fromRegion = request.getParameter("select_region");
		}
		
		if (request.getParameter("select_airport") != null) {
			fromAirportCode = request.getParameter("select_airport");
		}
		
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}
		
		
		fs.updateTransferData(pensionno,seperationreason,sepetaionDt,airportcode,region,fromAirportCode,fromRegion,status);
		log.info("===AAIEPF1Report=====" + region + "year" + year+"pensionno"+pensionno);
		request.setAttribute("successinfo","Success");
		request.setAttribute("successMessage","Updated successfully.");
		request.setAttribute("type","success");
		request.setAttribute("sucessPath","pfinance.do?method=loadmonthlycpfdatasearch");
		return mapping.findForward("successtransferdata");
	
	}
	//This method having a PensionValidationServlet
	public ActionForward getFinanceDetailEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeeValidateInfo validateInfo=new EmployeeValidateInfo();
		String convertEffectivedate="",unitCD="",dateVal="",region="";
		String cpfacno="",effectiveDate="",employeeName="",employeeNO="",designation="",pensionno="";
		if(request.getParameter("cpfaccno")!=null){
			cpfacno=request.getParameter("cpfaccno");
		}else{
			cpfacno="";
		}
		if(request.getParameter("pensionno")!=null){
			pensionno=request.getParameter("pensionno");
		}else{
			pensionno="";
		}
		log.info("pensionno"+pensionno);
		if(request.getParameter("airportCD")!=null){
			unitCD=request.getParameter("airportCD");
		}else{
			unitCD="";
		}
		if(request.getParameter("effectiveDate")!=null){
			effectiveDate=request.getParameter("effectiveDate");
		}else{
			effectiveDate="";
		}
		if(request.getParameter("employeeNM")!=null){
			employeeName=request.getParameter("employeeNM");
		}else{
			employeeName="";
		}
		if(request.getParameter("employeeno")!=null){
			employeeNO=request.getParameter("employeeno");
		}else{
			employeeNO="";
		}
	
		if(request.getParameter("designation")!=null){
			designation=request.getParameter("designation");
		}else{
			designation="";
		}
		if(request.getParameter("region")!=null){
			region=request.getParameter("region");
		}else{
			region="";
		}
		CommonUtil commonUtil=new CommonUtil();
		try {
			if(!effectiveDate.equals("")){
                 
                 convertEffectivedate = commonUtil.converDBToAppFormat(effectiveDate, "yyyy-MM-dd", "MMM-yy");
                   
			}
			log.info("cpfacno"+cpfacno+"pensionno"+pensionno+"unitCD"+unitCD+"effectiveDate"+effectiveDate+"designation"+designation+"employeeNO"+employeeNO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		validateInfo=fs.getLoadFinancialEditDetails(cpfacno,pensionno,unitCD,convertEffectivedate,employeeNO,designation,employeeName,region);
		log.info("================financalList=====validationReport=======");
		request.setAttribute("loadFinValidationList",validateInfo);
		request.setAttribute("effectiveDate",effectiveDate);
		request.setAttribute("unitCode",unitCD);

	
		return mapping.findForward("loadfinanceedit");
	}
//	This method having a PensionValidationServlet
	public ActionForward updateFinanceDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeeValidateInfo validateInfo=new EmployeeValidateInfo();
		String convertEffectivedate="",unitCD="",frmBasic="",frmDailyAllowance="",frmSpcBasic="",region="";
		String frmPFStatury="",frmPFAdvance="",frmVPF="",frmPartFinal="",frmAdvanceDrawn="",frmCPFinterest="";
		String pensionno="",cpfacno="",effectiveDate="",employeeName="",frmName="",employeeNO="",designation="",frmEmoluments;
		EmployeeValidateInfo updateValidateInfo=new EmployeeValidateInfo();
		if(request.getParameter("cpfaccno")!=null){
			cpfacno=request.getParameter("cpfaccno");
			updateValidateInfo.setCpfaccno(cpfacno);
		}else{
			updateValidateInfo.setCpfaccno("");
		}
		if(request.getParameter("frmName")!=null){
			frmName=request.getParameter("frmName");			
		}else{
			frmName="";
		}		
		if(request.getParameter("pfid")!=null){
			pensionno=request.getParameter("pfid");
			updateValidateInfo.setPensionNumber(pensionno);
		}else{
			updateValidateInfo.setPensionNumber("");
		}
		if(request.getParameter("airportCD")!=null){
			unitCD=request.getParameter("airportCD");
			
		}else{
			unitCD="";
		}
		if(request.getParameter("region")!=null){
			region=request.getParameter("region");
			
		}else{
			region="";
		}
		if(request.getParameter("effective_date")!=null){
			effectiveDate=request.getParameter("effective_date");
		}else{
			effectiveDate="";
		}
		if(request.getParameter("employee_name")!=null){
			employeeName=request.getParameter("employee_name");
			updateValidateInfo.setEmployeeName(employeeName);
		}else{
			updateValidateInfo.setEmployeeName("");
		}
		if(request.getParameter("txtbasic")!=null){
			frmBasic=request.getParameter("txtbasic");
			updateValidateInfo.setBasic(frmBasic);
		}else{
			updateValidateInfo.setBasic("0.0");
		}
		
		if(request.getParameter("txtEmoluments")!=null){
			frmEmoluments=request.getParameter("txtEmoluments");
			updateValidateInfo.setEmoluments(frmEmoluments);
		}else{
			updateValidateInfo.setEmoluments("0.0");
		}
		
		if(request.getParameter("txtspecialbasic")!=null){
			frmSpcBasic=request.getParameter("txtspecialbasic");
			updateValidateInfo.setSpecialBasic(frmSpcBasic);
		}else{
			updateValidateInfo.setSpecialBasic("0.0");
		}
		
		if(request.getParameter("txtpfStatury")!=null){
			frmPFStatury=request.getParameter("txtpfStatury");
			updateValidateInfo.setEmpPFStatuary(frmPFStatury);
		}else{
			updateValidateInfo.setEmpPFStatuary("0.0");
		}
		
		if(request.getParameter("txtVPF")!=null){
			frmVPF=request.getParameter("txtVPF");
			updateValidateInfo.setEmpVPF(frmVPF);
		}else{
			updateValidateInfo.setEmpVPF("0.0");
		}
		
		if(request.getParameter("txtPFAdv")!=null){
			frmPFAdvance=request.getParameter("txtPFAdv");
			updateValidateInfo.setPfAdvance(frmPFAdvance);
		}else{
			updateValidateInfo.setPfAdvance("0.0");
		}
		if(request.getParameter("txtCPFInterest")!=null){
			frmCPFinterest=request.getParameter("txtCPFInterest");
			updateValidateInfo.setCpfInterest(frmCPFinterest);
		}else{
			updateValidateInfo.setCpfInterest("0.00");
		}
		if(request.getParameter("txtAdvanceDrwn")!=null){
			frmAdvanceDrawn=request.getParameter("txtAdvanceDrwn");
			updateValidateInfo.setAdvanceDrawn(frmAdvanceDrawn);
		}else{
			updateValidateInfo.setAdvanceDrawn("0.0");
		}
		String remarks="";
		if(request.getParameter("txtnewremarks")!=null){
			remarks=request.getParameter("txtnewremarks");
			updateValidateInfo.setRemarks(remarks);
		}
		
		if(request.getParameter("txtPartFinal")!=null){
			frmPartFinal=request.getParameter("txtPartFinal");
			updateValidateInfo.setPartFinal(frmPartFinal);
		}else{
			updateValidateInfo.setPartFinal("0.0");
		}
		CommonUtil commonUtil=new CommonUtil();
		try {
			if(!effectiveDate.equals("")){
				convertEffectivedate=commonUtil.converDBToAppFormat(effectiveDate,"yyyy-MM-dd","MMM-yy");
			}
			log.info("pensionno"+pensionno+"cpfacno"+cpfacno+"unitCD"+unitCD+"convertEffectivedate"+convertEffectivedate);
			log.info("frmBasic"+frmBasic+"frmDailyAllowance"+frmDailyAllowance+"frmSpcBasic"+frmSpcBasic);
			
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		String userName,computerName;
		HttpSession httpsession = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) httpsession.getAttribute("user");
		userName = logInfo.getUserName();
		computerName = getIPAddress(request);
		log.info("==========UpdateFinanceDetail===region===="+region+"frmName===="+frmName);
		int count=fs.financialUpdateDetails(updateValidateInfo,unitCD,convertEffectivedate,pensionno,cpfacno,employeeName,userName,computerName,region);
		log.info("================financalList=====UpdateFinanceDetail======="+count+"frmName===="+frmName);
		request.setAttribute("loadFinValidationList",validateInfo);
		request.setAttribute("effectiveDate",effectiveDate);
		String message="";
		if(frmName.equals("CPFDeviation")){
			
			request.setAttribute("type","success");
			message="This PFID "+pensionno;
			if(count!=0){
				request.setAttribute("successinfo","Success");
				request.setAttribute("successMessage",message+" Updated successfully.");
			}else{
				request.setAttribute("successinfo","Fail");
				request.setAttribute("successMessage",message+" Didn't Updated Successfully.");
			}
		
			request.setAttribute("sucessPath","jsp/rpfc/deviationstatement/CPFDeviationSearch.jsp");
		
			return mapping.findForward("successfinanceEdit");
		}else{
			return mapping.findForward("loadfinancdatasearch");
			
		}
	}
	//This method having a FinanceServlet
	public ActionForward loadcpfdeviationInput(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		loadPrimarilyInfo(request,"loadcpfinputparamsform");
		return mapping.findForward("loadcpfinputparamsform");
	}
//	This method having a FinanceServlet
	public ActionForward deviationreport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "", sortingOrder = "",pfidString="",nextyear="",toYear="",frmSelectedDts="";
		
		ArrayList list = new ArrayList();

				
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
					
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
								
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}			
		
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "pensionno";
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "";
		String empflag = "false", pensionno = "";
		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		
		try {
			frmSelectedDts=getFromToDates(year,toYear,month,month);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("===AAIEPF1Report=====" + region + "year" + year+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		String regionString="",airportcodeString="";
		if(region.equals("NO-SELECT")){
			regionString="";
		}else{
			regionString=region;
		}
		if(airportcode.equals("NO-SELECT")){
			airportcodeString="";
		}else{
			airportcodeString=airportcode;
		}
		list = fs.deviationReport(pfidString,regionString, airportcode,frmSelectedDts, empflag,
				empName, sortingOrder, pensionno);
		

		if(list.size()>0)
		request.setAttribute("AAIEPF1List", list);
		
		log.info("---------list  SIZE in Action-----------"+list.size());
		
		if(!year.equals("NO-SELECT")){
			 nextyear=year.substring(2,4);
						
			nextyear=Integer.toString(Integer.parseInt(nextyear)+1);
			
			if(Integer.parseInt(nextyear)<=9)
				nextyear="0"+nextyear;				
		}else{
			year="1995";
			nextyear=commonUtil.getCurrentDate("yy");
		}
			
		year=year+"-"+nextyear;
		request.setAttribute("finYear", year);
		
		if(!region.equals("NO-SELECT")){
			request.setAttribute("region", region);			
		}else{
			request.setAttribute("region", "-----");		
		}
		request.setAttribute("reportType", reportType);
		
	
		return mapping.findForward("loadcpfformreport");
	}
	public ActionForward loadAdj(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadAdj");	
		return mapping.findForward("loadAdj");
		
	}
	public void loadMonthlyRecoveries(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String year = "", month = "", region = "", aiportcode = "", pfid = "", toYear = "",cpfacno="", frmSelectedDts = "",pccontrib="";

		if (request.getParameter("select_month") != "NO-SELECT") {
			month = request.getParameter("select_month").toString();
		}
		/*if (request.getParameter("select_region") != "NO-SELECT") {
			region = request.getParameter("select_region").toString();
		}*/
		if (request.getParameter("select_airport") != "NO-SELECT") {
			aiportcode = request.getParameter("select_airport").toString();
		}
		if (request.getParameter("empserialNO") != "") {
			pfid = request.getParameter("empserialNO").toString();
		}
		if (request.getParameter("cpfacno")!= "") {
			cpfacno = request.getParameter("cpfacno").toString();
		}
		if (request.getParameter("region")!= "") {
			region = request.getParameter("region").toString();
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("to_year") != null) {
			toYear = request.getParameter("to_year");
		}


		ArrayList a = new ArrayList();
		
		try {
			frmSelectedDts = getFromToDates(year, toYear,
					month, month);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("frmSelectedDts " + frmSelectedDts);
		a = fs.getCpfData(pfid, frmSelectedDts, month,region,cpfacno);
		log.info("list " + a.size());
		request.setAttribute("cpfRecoveryList", a);

		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		for (int i = 0; a.size() != 0 && i < a.size(); i++) {
			String name = "", code = "";
			EmployeePensionCardInfo cardInfo = (EmployeePensionCardInfo) a
			.get(i);
			sb.append("<ServiceType>");
			sb.append("<pensionno>");
			sb.append(cardInfo.getPensionNo());
			sb.append("</pensionno>");
			sb.append("<monthyear>");
			sb.append(cardInfo.getMonthyear());
			sb.append("</monthyear>");
			sb.append("<emoluments>");
			sb.append(cardInfo.getEmoluments().trim());
			sb.append("</emoluments>");
			sb.append("<cpf>");
			sb.append(cardInfo.getEmppfstatury().trim());
			sb.append("</cpf>");
			sb.append("<vpf>");
			sb.append(cardInfo.getEmpvpf().trim());
			sb.append("</vpf>");
			sb.append("<principle>");
			sb.append(cardInfo.getPrincipal().trim());
			sb.append("</principle>");
			sb.append("<interest>");
			sb.append(cardInfo.getInterest().trim());
			sb.append("</interest>");
			sb.append("<PCContrib>");
			sb.append(cardInfo.getPensionContribution().trim());
			sb.append("</PCContrib>");
			sb.append("<region>");
			sb.append(cardInfo.getRegion());
			sb.append("</region>");
			sb.append("<advAmount>");
			sb.append(cardInfo.getAdvancesAmount());
			sb.append("</advAmount>");
			sb.append("<loan_sub_amt>");
			sb.append(cardInfo.getAdvancePFWPaid());
			sb.append("</loan_sub_amt>");
			sb.append("<loan_cont_amt>");
			sb.append(cardInfo.getAaiNet());
			sb.append("</loan_cont_amt>");
			sb.append("<cpfacno>");
			sb.append(cpfacno);
			sb.append("</cpfacno>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());

		
		
	}
	public void saveEmoluments(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		String emoluments = "0.00", cpf = "0.00", vpf = "0.00", principle = "0.00", interest = "0.00", pensionno = "", month = "", fromyear = "", toyear = "",transmonthyear="";
		String advAmount="0.00",loan_sub_amt="0.00",loan_cont_amt="0.00",pccontrib="0.00",frmSelectedDts="",region="";
		String cpfacno="";
		HttpSession httpsession = request.getSession(true);
		if (request.getParameter("emoluments") != "") {
			emoluments = request.getParameter("emoluments").toString();
		}
		if (request.getParameter("cpf") != "") {
			cpf = request.getParameter("cpf").toString();
		}
		if (request.getParameter("vpf") != "") {
			vpf = request.getParameter("vpf").toString();
		}
		if (request.getParameter("principle") != "") {
			principle = request.getParameter("principle").toString();
		}
		if (request.getParameter("interest") != "") {
			interest = request.getParameter("interest").toString();
		}
		if (request.getParameter("empserialNO") != "") {
			pensionno = request.getParameter("empserialNO").toString();
		}
		if (request.getParameter("cpfacno") != "") {
			cpfacno = request.getParameter("cpfacno").toString();
		}
		if (request.getParameter("select_month") != "") {
			month = request.getParameter("select_month").toString();
		}
		if (request.getParameter("frm_year") != null) {
			fromyear = request.getParameter("frm_year");
		}
		if (request.getParameter("to_year") != null) {
			toyear = request.getParameter("to_year");
		}
		if (request.getParameter("transmonthyear") != null) {
			transmonthyear = request.getParameter("transmonthyear").toString();
		}
		if (request.getParameter("pccontrib") != null) {
			pccontrib = request.getParameter("pccontrib");
		}

		if (request.getParameter("region") != null) {
			region = request.getParameter("region");
		}
		if (request.getParameter("advAmount") != null) {
			advAmount = request.getParameter("advAmount");
		}
		if (request.getParameter("loan_sub_amt") != null) {
			loan_sub_amt = request.getParameter("loan_sub_amt");
		}
		if (request.getParameter("loan_cont_amt") != null) {
			loan_cont_amt = request.getParameter("loan_cont_amt");
		}

		
		try {
			frmSelectedDts = getFromToDates(fromyear, toyear,
					month, month);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		LoginInfo logInfo = new LoginInfo();
		String userName="",computerName="";
		logInfo = (LoginInfo) httpsession.getAttribute("user");
		userName = logInfo.getUserName();
		computerName = getIPAddress(request);
		ArrayList a = fs.saveCpfAdjustMents(emoluments, cpf,
				vpf, principle, interest, pensionno, month, frmSelectedDts,pccontrib,transmonthyear,computerName,userName,region,advAmount,loan_sub_amt,loan_cont_amt,cpfacno);
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		for (int i = 0; a.size() != 0 && i < a.size(); i++) {
			String name = "", code = "";
			EmployeePensionCardInfo cardInfo = (EmployeePensionCardInfo) a
			.get(i);
			sb.append("<ServiceType>");
			sb.append("<pensionno>");
			sb.append(cardInfo.getPensionNo());
			sb.append("</pensionno>");
			sb.append("<monthyear>");
			sb.append(cardInfo.getMonthyear());
			sb.append("</monthyear>");
			sb.append("<emoluments>");
			sb.append(cardInfo.getEmoluments().trim());
			sb.append("</emoluments>");
			sb.append("<cpf>");
			sb.append(cardInfo.getEmppfstatury().trim());
			sb.append("</cpf>");
			sb.append("<vpf>");
			sb.append(cardInfo.getEmpvpf().trim());
			sb.append("</vpf>");
			sb.append("<principle>");
			sb.append(cardInfo.getPrincipal().trim());
			sb.append("</principle>");
			sb.append("<interest>");
			sb.append(cardInfo.getInterest().trim());
			sb.append("</interest>");
			sb.append("<PCContrib>");
			sb.append(cardInfo.getPensionContribution().trim());
			sb.append("</PCContrib>");
			sb.append("<region>");
			sb.append(region);
			sb.append("</region>");
			sb.append("<advAmount>");
			sb.append(cardInfo.getAdvancesAmount());
			sb.append("</advAmount>");
			sb.append("<loan_sub_amt>");
			sb.append(cardInfo.getAdvancePFWPaid());
			sb.append("</loan_sub_amt>");
			sb.append("<loan_cont_amt>");
			sb.append(cardInfo.getAaiNet());
			sb.append("</loan_cont_amt>");

			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());
	}
	public ActionForward loadarrearFinanceSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("loadarrearfindatamapping");
	}
	public ActionForward searchRecordsbyEmpSerailNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("FinanceReportAction::searchRecordsbyEmpSerailNo()");
		String employeeName = "";
        String cpfAccnos="";
		EmpMasterBean empBean = new EmpMasterBean();
		HttpSession session=request.getSession(true);
         if (request.getParameter("cpfnumbers") != null){
             cpfAccnos  = request.getParameter("cpfnumbers");
            request.setAttribute("cpfAccnos", cpfAccnos);
        
         }
        if (request.getParameter("empName") != null)
		
		empBean.setEmpName(request.getParameter("empName").toString()
				.trim());
		log.info(employeeName);
		if (request.getParameter("dob") != null) {
			empBean.setDateofBirth(request.getParameter("dob").toString()
					.trim());
		} else
			empBean.setDateofBirth("");
		if (request.getParameter("empsrlNo") != null) {
			empBean.setEmpSerialNo(request.getParameter("empsrlNo")
					.toString().trim().toUpperCase());
		} else {
			empBean.setEmpSerialNo("");
		}
		if (request.getParameter("doj") != null) {
			empBean.setDateofJoining((String) request.getParameter("doj")
					.toString());
		} else
			empBean.setDateofJoining("");
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) session.getAttribute("user");
		
		String userName = logInfo.getUserName();
		String userType=logInfo.getUserType();
		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		boolean flag = true;
		try {
			searchBean = ps.searchRecordsbyEmpSerailNo(empBean,
					getSearchInfo, 10,userType);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		ArrayList dataList=new ArrayList();
		request.setAttribute("searchBean", searchBean);
		searchBean = (SearchInfo) request.getAttribute("searchBean");
		dataList = searchBean.getSearchList();
		log.info("FinanceReportAction::searchRecordsbyEmpSerailNo"+dataList.size());
		request.setAttribute("searchInfoList", dataList);
		request.setAttribute("searchInfo", empBean);
		request.setAttribute("searchInfo", empBean);
		return mapping.findForward("loadarrearfindatamapping");
	}
	public ActionForward getProcessUnprocessList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("FinanceReportAction::getProcessUnprocessList ");
		String employeeName = "";
		EmpMasterBean empBean = new EmpMasterBean();
		ArrayList dataList=new ArrayList();
		if (request.getParameter("cpfacno") != null) {
			empBean.setCpfAcNo(request.getParameter("cpfacno").toString()
					.trim());
		} else
			empBean.setCpfAcNo("");
		if (request.getParameter("name") != null)
			employeeName = request.getParameter("name").toString()
					.trim();
		empBean.setEmpName(employeeName);
		log.info(employeeName);
		if (request.getParameter("dateofBirth") != null) {
			empBean.setDateofBirth(request.getParameter("dateofBirth").toString()
					.trim());
		} else
			empBean.setDateofBirth("");
		if (request.getParameter("empSerailNumber") != null) {
			empBean.setEmpSerialNo(request.getParameter("empSerailNumber")
					.toString().trim().toUpperCase());
		} else {
			empBean.setEmpSerialNo("");
		}
		empBean.setEmpSerialNo(commonUtil.getSearchPFID(empBean.getEmpSerialNo().toString().trim()));
	    if (request.getParameter("empCode") != null) {
	        empBean.setEmpNumber(request.getParameter("empCode").toString()
	                .trim());
	    } else
	        empBean.setEmpNumber("");
		log.info("dateofbirth "+empBean.getDateofBirth()+" empSrlno "+empBean.getEmpSerialNo());
		
		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		boolean flag = true;
		try {
		searchBean = ps.getProcessUnprocessList(empBean,
					getSearchInfo, 10);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		request.setAttribute("searchBean", searchBean);
		dataList = searchBean.getSearchList();
		log.info("FinanceReportAction::getProcessUnprocessList"+dataList.size());
		request.setAttribute("searchInfoList", dataList);
		request.setAttribute("searchInfo", empBean);


		return mapping.findForward("arrearprocesslist");
	}
	public void searchRecordsbyDobandNameUnprocessList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("searchRecordsbyDobandNameUnprocessList () ");
		String employeeName = "";
		EmpMasterBean empBean = new EmpMasterBean();
		if (request.getParameter("empName") != null){
		empBean.setEmpName(request.getParameter("empName").toString().trim());
		}else{
			empBean.setEmpName("");	
		}
		
		log.info(employeeName);
		if (request.getParameter("dateofbirth") != null) {
			empBean.setDateofBirth(request.getParameter("dateofbirth").toString()
					.trim());
		} else
			empBean.setDateofBirth("");
		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		boolean flag = true;
		
		try {
			searchBean = ps.searchRecordsbyDobandName(empBean,
					getSearchInfo, 10);
			ArrayList dataList = new ArrayList();
			dataList = searchBean.getSearchList();
			log.info("Search list " + searchBean);
			StringBuffer sb = new StringBuffer();
			sb.append("<ServiceTypes>");
		for (int i = 0; dataList != null && i < dataList.size(); i++) {
				String name = "", code = "";
				PensionBean bean = (PensionBean) dataList.get(i);
			sb.append("<ServiceType>");
			sb.append("<cpfacno>");
			sb.append(bean.getCpfAcNo());
			sb.append("</cpfacno>");
			sb.append("<employeename>");
			sb.append(bean.getEmployeeName().trim());
			sb.append("</employeename>");
			sb.append("<dateofbirth>");
            if(bean.getDateofBirth().equals("")){
                sb.append(" "); 
            }else{
                sb.append(bean.getDateofBirth()); 
            }
			
			sb.append("</dateofbirth>");
			sb.append("<dateofjoin>");
             if(bean.getDateofJoining().equals("")){
                    sb.append(" "); 
                }else{
                    sb.append(bean.getDateofJoining());
                }
			
			sb.append("</dateofjoin>");
			sb.append("<region>");
			sb.append(bean.getRegion());
			sb.append("</region>");
            sb.append("<airport>");
            sb.append(bean.getAirportCode());
            sb.append("</airport>");
            sb.append("<employeeNo>");
            sb.append(bean.getEmployeeCode());
            sb.append("</employeeNo>");
            
            sb.append("<pensionOption>");
            sb.append(bean.getPensionOption());
            sb.append("</pensionOption>");
			sb.append("</ServiceType>");
		}
			sb.append("</ServiceTypes>");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			log.info(sb.toString());
		//	ps.updatePensionCheck(empBean,getSearchInfo,gridLength);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		
	}
	public ActionForward unprocessList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
		String  cpfAccno = "", region = "";
		
        String empCode="";
        String empSerailNumber="",pensionNumber="";
		
		if(!request.getParameter("cpfacno").trim().equals("")){
			cpfAccno=request.getParameter("cpfacno").trim();
		}
        if(!request.getParameter("empCode").trim().equals("")){
            empCode=request.getParameter("empCode").trim();
        }
        
		if(!request.getParameter("region").equals("")){
		region=(String)request.getParameter("region");
		}
		String uncheckCpfacno=cpfAccno+"$"+region+"$"+empCode;
		empSerailNumber="";
		
		try{
			ps.updatePensionCheck(uncheckCpfacno,empSerailNumber,pensionNumber);	
		}catch(Exception e){
			log.printStackTrace(e);
		
		}
		
			
		return mapping.findForward("loadarrearfindatamapping");
	}
	public ActionForward addtoProcess(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String uncheckList="",empSerailNumber="",pensionNumber="";
		if(!request.getParameter("empSerailNumber").trim().equals("")){
		 empSerailNumber=request.getParameter("empSerailNumber").trim();
		}
		if(!request.getParameter("pensionNumber").trim().equals("")){
			pensionNumber=request.getParameter("pensionNumber").trim();
		}
		if(!request.getParameter("checklist").equals("")){
		uncheckList=(String)request.getParameter("checklist");
        log.info("unchecklist "+uncheckList);
		uncheckList.substring(2,uncheckList.length());
		}
		String[] temp1 = null;
		temp1 = uncheckList.split(",");
		log.info("checklist length " +temp1.length);
		for(int i=0;i<temp1.length;i++){
			String uncheckCpfacno=temp1[i];
           
		try{
            if(!uncheckCpfacno.equals("")){
			ps.updatePensionCheck(uncheckCpfacno,empSerailNumber,pensionNumber);	
            }
		}catch(Exception e){
			log.printStackTrace(e);
		}
		}
		return mapping.findForward("loadarrearfindatamapping");
	}	
	public ActionForward loadarrearsdatasearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
		return mapping.findForward("loadarrearinfo");
	}
	public ActionForward getArrearsData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList empList=new ArrayList();
		String pensionno="";
		String empName="";
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		if (request.getParameter("empName") != null) {
			empName = request.getParameter("empName");
		}
		empList=fs.getArrearsData(pensionno);
		request.setAttribute("empList", empList);
		log.info("getArrearsData Arrears Size"+empList.size());
		return mapping.findForward("loadarrearinfo");
	}
	public ActionForward getArrearsTransactionData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList empList=new ArrayList();
		String pensionno="",path="",dateOfAnnuation="";
		CommonDAO commonDAO=new CommonDAO();
		EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		if (request.getParameter("dateOfAnnuation") != null) {
			dateOfAnnuation = request.getParameter("dateOfAnnuation");
		}
		log.info("pensionno"+pensionno+"dateOfAnnuation"+dateOfAnnuation);
		empList=fs.getArrearsTransactionData(pensionno,dateOfAnnuation,"");
		personalInfo=commonDAO.getEmployeePersonalInfo("NO-SELECT","","", "",pensionno);
		if(empList.size()!=0){
			request.setAttribute("empList", empList);
			request.setAttribute("pensionno", pensionno);
			request.setAttribute("dateOfAnnuation", dateOfAnnuation);
			request.setAttribute("personalInfo", personalInfo);
			path="loadarrearseditscreen";
		}else{
			request.setAttribute("successinfo","Fail");
			request.setAttribute("successMessage","No Data Found");
			request.setAttribute("type","success");
			request.setAttribute("sucessPath","arrearfinance.do?method=getArrearsData&pensionno="+pensionno);
			path="nodataeditSearch";
		}
	return mapping.findForward(path);
	}
	public ActionForward addArrearsData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		ArrayList empList=new ArrayList();
		EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
		String pensionno="",dateOfAnnuation="",wetheroption="",path="";
		int listsize=0;
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		if (request.getParameter("dateOfAnnuation") != null) {
			dateOfAnnuation = request.getParameter("dateOfAnnuation");
		}
		if (request.getParameter("wetheroption") != null) {
			wetheroption = request.getParameter("wetheroption");
		}
		if (request.getParameter("listsize") != null) {
			listsize = Integer.parseInt(request.getParameter("listsize").toString());
		}
		for(int i=1;i<=listsize;i++){
			String monthyear=request.getParameter("monthyear"+i);
			String dueArrearamt=request.getParameter("arrearamt"+i);
			String dueEmoluments=request.getParameter("emoluments"+i);
			log.info("monthyear"+monthyear+"dueArrearamt"+dueArrearamt+"dueEmoluments"+dueEmoluments);
			fs.addArrearsData(pensionno,monthyear,dueArrearamt,dueEmoluments);	
		}
		ActionForward forward = mapping.findForward("processingarrears");
		ForwardParameters fwdParams = new ForwardParameters();
		Map params = new HashMap();
		
		fwdParams.add("pensionno", pensionno);
		fwdParams.add("dateOfAnnuation", dateOfAnnuation);
		
	
		
		
		return fwdParams.forward(forward);
		
	}
	public void updatearears(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String pensionno="",arreartotal="",arreardate="";
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		if (request.getParameter("arreartotal") != null) {
			arreartotal = request.getParameter("arreartotal");
		}
		if (request.getParameter("arreardate") != null) {
			arreardate = request.getParameter("arreardate");
		}
			
		fs.updateArearData(pensionno,arreartotal,arreardate);
		StringBuffer sb=new StringBuffer();
        sb.append("Update Successfully For the PFID"+pensionno);
		response.setContentType("text/xml");
		PrintWriter out;
		try {
			out = response.getWriter();
			log.info(sb.toString());
			out.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	public ActionForward getArrearsTransactionDataReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("FinanceReportAction::getArrearsTransactionDataReport Entering method");
		ArrayList empList=new ArrayList();
		ArrayList tranactiondata=new ArrayList();
		ArrayList arrearsInfo=new ArrayList();
		String pensionno="",dateOfAnnuation="",path="";

		EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
		if (request.getParameter("pensionno") != null) {
			pensionno = request.getParameter("pensionno");
		}
		if (request.getParameter("dateOfAnnuation") != null) {
			dateOfAnnuation = request.getParameter("dateOfAnnuation");
		}
		empList=fs.getArrearsData(pensionno);
		request.setAttribute("empList", empList);
		arrearsInfo=fs.getArrearsInfo(pensionno);
		tranactiondata=fs.getArrearsTransactionData(pensionno,dateOfAnnuation,"");
		personalInfo=commonDAO.getEmployeePersonalInfo("NO-SELECT","","", "",pensionno);
		
		if(tranactiondata.size()!=0){
			request.setAttribute("transacitondata", tranactiondata);
			request.setAttribute("pensionno", pensionno);
			request.setAttribute("arrearsInfo", arrearsInfo);
			request.setAttribute("dateOfAnnuation", dateOfAnnuation);
			request.setAttribute("personalInfo", personalInfo);
			path="arrearstransreport";
		}else{
			request.setAttribute("successinfo","Fail");
			request.setAttribute("successMessage","No Data Found");
			request.setAttribute("type","success");
			request.setAttribute("windowflag","Y");
			
			request.setAttribute("sucessPath","arrearfinance.do?method=getArrearsData&pensionno="+pensionno);
			path="nodataeditSearch";
		}
		return mapping.findForward(path);
	}
}
