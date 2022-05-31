package com.epis.action.rpfc;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epis.bean.rpfc.epfforms.AAIEPFReportBean;

import com.epis.dao.CommonDAO;
import com.epis.services.rpfc.EPFFormsReportService;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.services.rpfc.FinancialService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class AAIEPFFormsReportAction extends CommonRPFCAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	Log log=new Log(AAIEPFFormsReportAction.class);
	EPFFormsReportService epfformsService=new EPFFormsReportService();
	FinancialService financeService = new FinancialService();
	FinancialReportService finReportService = new FinancialReportService();
	public ActionForward loadob(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadob");	
		return mapping.findForward("loadobreport");
	}
	public ActionForward loadnodalepfforms(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
		return mapping.findForward("loadnodalepfforms");
	}
		
	public ActionForward loadepf2(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadadjobreport");	
		return mapping.findForward("loadadjobreport");
	}
	
	public ActionForward AAIEPF1Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "", sortingOrder = "",pfidString="",nextyear="",toYear="",frmSelectedDts="";
		
		AAIEPFReportBean AAIEPFBean=new AAIEPFReportBean();
		ArrayList list1 = new ArrayList();
		ArrayList list2 = new ArrayList();
				
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
		
		AAIEPFBean = epfformsService.AAIEPFForm1Report(pfidString,region, airportcode,frmSelectedDts, empflag,
				empName, sortingOrder, pensionno);
		
		if(AAIEPFBean.getReportList1()!=null){
			list1=AAIEPFBean.getReportList1();
		}
		if(AAIEPFBean.getReportList2()!=null){
			list2=AAIEPFBean.getReportList2();
		}
		

		if(list1.size()>0)
		request.setAttribute("AAIEPF1List", list1);			
		log.info("---------list1  SIZE in Action-----------"+list1.size());
		
		if(list2.size()>0)
		request.setAttribute("AAIEPF1List2", list2);				
		log.info("---------list2  SIZE in Action-----------"+list2.size());
		
		if(!year.equals("NO-SELECT")){
			 nextyear=year.substring(2,4);
						
			nextyear=Integer.toString(Integer.parseInt(nextyear)+1);
			
			if(Integer.parseInt(nextyear)<=9)
				nextyear="0"+nextyear;				
		}else{
			year="1995";
			nextyear=commonUtil.getCurrentDate("yy");
		}
		String dispOBYear="01.04."+year;		
		year=year+"-"+nextyear;
		request.setAttribute("finYear", year);
		
		request.setAttribute("dispOBYear", dispOBYear);
		if(!region.equals("NO-SELECT")){
			request.setAttribute("region", region);			
		}else{
			request.setAttribute("region", "-----");		
		}
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showobreport");
	}
	public ActionForward AAIEPF2Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
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
		
		list = epfformsService.AAIEPFForm2Report(pfidString,region, airportcode,frmSelectedDts, empflag,
				empName, sortingOrder, pensionno);
		

		if(list.size()>0)
		request.setAttribute("AAIEPF2List", list);
						
		if(year.equals("NO-SELECT")){				
			year="---";							
		}				
		
		request.setAttribute("finYear", year);
		
		if(!region.equals("NO-SELECT")){
			request.setAttribute("region", region);			
		}else{
			request.setAttribute("region", "-----");		
		}
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showadjobreport");
	}
	
	public ActionForward loadepf3(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf3report");	
		return mapping.findForward("loadepf3report");
	}
	public ActionForward loadepf5(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf5report");	
		return mapping.findForward("loadepf5report");
	}
	public ActionForward loadepf6(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf6report");	
		return mapping.findForward("loadepf6report");
	}
	public ActionForward loadepf7(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf7report");	
		return mapping.findForward("loadepf7report");
	}
	public ActionForward loadepf8(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf8report");	
		return mapping.findForward("loadepf8report");
	}
	public ActionForward loadepf11(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf11report");	
		return mapping.findForward("loadepf11report");
	}
	public ActionForward loadepf12(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadepf12report");	
		return mapping.findForward("loadepf12report");
	}
	
	public ActionForward epf3report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "",toMonth="",monthDesc="", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", reportType = "",totalSub="", sortingOrder = "",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		ArrayList epfForm3List = new ArrayList();
		ArrayList missingPFIDsList=new ArrayList();
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_to_month") != null) {
			toMonth = request.getParameter("frm_to_month");
		}
		
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}

		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		try {
			frmSelectedDts=getFromToDatesForForm3(year,toYear,month,toMonth);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("===AAI EPF-3=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		epfForm3List=epfformsService.loadEPFForm3Report(pfidString,region,airportcode,empName,empflag,frmSelectedDts,sortingOrder,pensionno);
		missingPFIDsList=epfformsService.loadEPFMissingTransPFIDs(pfidString,region,airportcode,empName,empflag,frmSelectedDts,sortingOrder,pensionno);
		log.info("===AAI EPF-3==missingPFIDsList===" + missingPFIDsList.size() );
		request.setAttribute("cardList", epfForm3List);
		request.setAttribute("missingPFIDsList", missingPFIDsList);
		
		if (!year.equals("NO-SELECT")) {
			shnYear=year+"-"+toYear;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		
		if(epfForm3List.size()!=0){
			totalSub=Integer.toString(epfForm3List.size());
		}else{
			totalSub="0";
		}
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
		try {
			monthDesc=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("totalSub", totalSub);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("dspMonth", monthDesc);
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showepf3report");
	}
	public ActionForward AAIEPF8Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "", sortingOrder = "",pfidString="",nextyear="",toYear="",frmSelectedDts="";
		
		ArrayList list = new ArrayList();
		AAIEPFReportBean aaiEPFBean =new AAIEPFReportBean();

				
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
		String empName = "",monthDesc="";
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
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		try {
			frmSelectedDts=getFromToDates(year,toYear,month,month);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("===AAIEPF1Report=====" + region + "year" + year+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		
		aaiEPFBean = epfformsService.AAIEPFForm8Report(pfidString,region, airportcode,frmSelectedDts, empflag,
				empName, sortingOrder, pensionno);
		

		if(!aaiEPFBean.equals(""))
		request.setAttribute("aaiEPFBean", aaiEPFBean);
		
		log.info("---------aaiEPFBean in Action-----------"+aaiEPFBean);
		
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
		
		try {
			monthDesc=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!month.equals("NO-SELECT")){
			request.setAttribute("month", monthDesc);			
		}else{
			request.setAttribute("month", "-----");		
		}
		
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showepf8report");
	}
	public ActionForward AAIEPF5Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", reportType = "",totalSub="", sortingOrder = "",monthDesc="",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		ArrayList epfForm3List = new ArrayList();

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
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
		log.info("===AAI EPF-3=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		epfForm3List=epfformsService.loadEPFForm5Report(pfidString,region,airportcode,empName,empflag,frmSelectedDts,sortingOrder,pensionno);
		request.setAttribute("cardList", epfForm3List);
		if (!year.equals("NO-SELECT")) {
			shnYear=year+"-"+toYear;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		if(!month.equals("NO-SELECT")){
			try {
				monthDesc=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("dspMonth", monthDesc);
		}

	
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
	
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
	
		request.setAttribute("dspYear", shnYear);
	
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showepf5report");
	}
	public ActionForward AAIEPF11Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", reportType = "",totalSub="", sortingOrder = "",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		ArrayList epfForm3List = new ArrayList();

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}

		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		log.info("===AAI EPF-11=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		log.info("sortingOrder==================="+sortingOrder);
		String[] yearList=year.split("-");
		String frmSelectedYear=yearList[0]+"-"+(Integer.parseInt(yearList[0])+1);
		log.info("===AAI EPF-11=====Year" +frmSelectedYear );
		epfForm3List=epfformsService.loadEPFForm11Report(pfidString,region,airportcode,empName,empflag,frmSelectedYear,sortingOrder,pensionno);
		request.setAttribute("cardList", epfForm3List);
		if(epfForm3List.size()!=0){
			totalSub=Integer.toString(epfForm3List.size());
		}else{
			totalSub="0";
		}
		if (!year.equals("NO-SELECT")) {
			shnYear=frmSelectedYear;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		
		if(!month.equals("NO-SELECT")){
			try {
				month=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("dspMonth", month);
		}
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
		
		request.setAttribute("totalSub", totalSub);
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("reportType", reportType);
		request.setAttribute("dspYear", shnYear);
		return mapping.findForward("showepf11report");
	}
	public ActionForward AAIEPF12Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", reportType = "",totalSub="", sortingOrder = "",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		ArrayList epfForm3List = new ArrayList();

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}

		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}

		log.info("===AAI EPF-12=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		log.info("sortingOrder==================="+sortingOrder);
		String[] yearList=year.split("-");
		String frmSelectedYear=yearList[0]+"-"+(Integer.parseInt(yearList[0])+1);
		log.info("===AAI EPF-12=====Year" +frmSelectedYear );
		epfForm3List=epfformsService.loadEPFForm11Report(pfidString,region,airportcode,empName,empflag,frmSelectedYear,sortingOrder,pensionno);
		request.setAttribute("cardList", epfForm3List);
		if(epfForm3List.size()!=0){
			totalSub=Integer.toString(epfForm3List.size());
		}else{
			totalSub="0";
		}
		if (!year.equals("NO-SELECT")) {
			shnYear=frmSelectedYear;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		
	
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
		if(!month.equals("NO-SELECT")){
			try {
				month=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("dspMonth", month);
		}
	
		request.setAttribute("totalSub", totalSub);
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("reportType", reportType);
		request.setAttribute("dspYear", shnYear);
		return mapping.findForward("showepf12report");
	}
	public ActionForward AAIEPF6Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", frmSelectedDts1="",reportType = "",totalSub="", sortingOrder = "",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		ArrayList epfForm3List = new ArrayList();
		ArrayList missingPFIDsList=new ArrayList();
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		frmSelectedDts=year+"-"+Integer.toString(Integer.parseInt(year)+1);
		toYear=Integer.toString(Integer.parseInt(year)+1);
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}

		log.info("===AAI EPF-6=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		epfForm3List=epfformsService.loadEPFForm6Report(pfidString,region,airportcode,empName,empflag,frmSelectedDts,sortingOrder,pensionno);
		try {
			frmSelectedDts1=getFromToDates(year,toYear,month,month);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
		}
		missingPFIDsList=epfformsService.loadEPFMissingTransPFIDs(pfidString,region,airportcode,empName,empflag,frmSelectedDts1,sortingOrder,pensionno);
		request.setAttribute("cardList", epfForm3List);
		request.setAttribute("missingPFIDsList", missingPFIDsList);
		if (!year.equals("NO-SELECT")) {
			shnYear=frmSelectedDts;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		if(!month.equals("NO-SELECT")){
			try {
				month=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("dspMonth", month);
		}

	
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
	
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
	
		request.setAttribute("dspYear", shnYear);

		request.setAttribute("reportType", reportType);
		return mapping.findForward("showepf6report");
	}
	public ActionForward AAIEPF7Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "",regionDesc="", year = "", month = "", toYear="",selectedDate = "", frmMonthYear = "",  shnYear = "",displayDate = "", disMonthYear = "";
		
		String airportcode = "", reportType = "",totalSub="", sortingOrder = "",pfidString="",empName = "",empflag = "false", pensionno = "",frmSelectedDts="";
		String frmSelectedDts1="";
		ArrayList epfForm3List = new ArrayList();
		ArrayList missingPFIDsList = new ArrayList();
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		frmSelectedDts=year+"-"+Integer.toString(Integer.parseInt(year)+1);
		toYear=Integer.toString(Integer.parseInt(year)+1);
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}else{
			pfidString="";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}else{
			sortingOrder="PENSIONNO";
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		
		log.info("===AAI EPF-7=====" + region + "year" + frmSelectedDts+"empflag========="+empflag+"pfidString"+pfidString+"pensionno"+pensionno);
		epfForm3List=epfformsService.loadEPFForm7Report(region,airportcode,frmSelectedDts,sortingOrder);
		try {
			frmSelectedDts1=getFromToDates(year,toYear,month,month);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
		}
		missingPFIDsList=epfformsService.loadEPFMissingTransPFIDs(pfidString,region,airportcode,empName,empflag,frmSelectedDts1,sortingOrder,pensionno);
		request.setAttribute("missingPFIDsList", missingPFIDsList);
		request.setAttribute("cardList", epfForm3List);
		if (!year.equals("NO-SELECT")) {
			shnYear=frmSelectedDts;
		} else {
			shnYear = "1995-"+commonUtil.getCurrentDate("yyyy");
		}
		
		
		if(!region.equals("NO-SELECT")){
			regionDesc=region;
		}else{
			regionDesc="";
		}
		try {
			month=commonUtil.converDBToAppFormat("01-"+month+"-"+commonUtil.getCurrentDate("yyyy"),"dd-MM-yyyy","MMM");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
		
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("dspMonth", month);
		request.setAttribute("reportType", reportType);
		return mapping.findForward("showepf7report");
	}
}
