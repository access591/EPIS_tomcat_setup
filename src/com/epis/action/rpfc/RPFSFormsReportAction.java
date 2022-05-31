package com.epis.action.rpfc;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epis.bean.rpfc.Form8Bean;
import com.epis.bean.rpfc.RegionBean;
import com.epis.dao.CommonDAO;
import com.epis.services.rpfc.EPFFormsReportService;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.services.rpfc.FinancialService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class RPFSFormsReportAction extends CommonRPFCAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	Log log=new Log(RPFSFormsReportAction.class);
	FinancialService financeService = new FinancialService();
	FinancialReportService finReportService = new FinancialReportService();
	//This method copied from reportservlet
	public ActionForward loadform3params(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadform3params");
		return mapping.findForward("loadform3inputparams");
	}
//	This method copied from reportservlet
	public ActionForward allRegionForm3(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		String airportcode = "", reportType = "", sortingOrder = "";
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfaccno";
		}

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "";
		String empflag = "false";
		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		log.info("region==========getform3=====" + region + "year" + year
				+ "month" + month);
		String fullMonthName = "";
		if (!month.equals("00")) {
			frmMonthYear = "%" + "-" + month + "-" + year;
			disMonthYear = month + "-" + year;
			try {
				displayDate = commonUtil.converDBToAppFormat(disMonthYear,
						"MM-yyyy", "MMM-yyyy");
				int months = 0;
				months = Integer.parseInt(month) - 1;
				log.info("months=================" + months
						+ "month=========" + month);
				if (months < 10) {
					month = "0" + months;
				} else {
					month = Integer.toString(months);
				}
				disMonthYear = month + "-" + year;
				log.info("disMonthYear=================" + disMonthYear
						+ "month=========" + month);
				fullMonthName = commonUtil.converDBToAppFormat(
						disMonthYear, "MM-yyyy", "MMMM-yyyy");
				selectedDate = "%-" + displayDate;
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}

		} else {
			selectedDate = year;
			displayDate = year;
		}
		ArrayList form3List = new ArrayList();

		form3List = financeService.financeForm3ReportAll(selectedDate,
				region, fullMonthName, sortingOrder, empName, empflag);

		log.info("selectedDate==========getform3=====" + selectedDate
				+ "form3==Size==" + form3List.size());

		request.setAttribute("displayDate", fullMonthName);
		request.setAttribute("form3List", form3List);
		request.setAttribute("reportType", reportType);
		RequestDispatcher rd = null;

		/*rd = request
		.getRequestDispatcher("./PensionView/PensionForm3ReportAll.jsp");*/


		return mapping.findForward("form3all");
	}
//	This method copied from reportservlet
	public ActionForward getform3(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		ArrayList airportList = new ArrayList();
		String airportcode = "", reportType = "", sortingOrder = "", formType = "";
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfaccno";
		}

		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "";
		String empflag = "false";
		if (request.getParameter("frm_emp_flag") != null) {
			empflag = request.getParameter("frm_emp_flag");
		}
		if (request.getParameter("frm_empnm") != null) {
			empName = request.getParameter("frm_empnm");
		}
		log.info("region==========getform3=====" + region + "year" + year
				+ "month" + month);
		String fullMonthName = "";
		if (!month.equals("00")) {
			frmMonthYear = "%" + "-" + month + "-" + year;
			disMonthYear = month + "-" + year;
			try {
				displayDate = commonUtil.converDBToAppFormat(disMonthYear,
						"MM-yyyy", "MMM-yyyy");
				int months = 0;
				months = Integer.parseInt(month) - 1;
				log.info("months=================" + months
						+ "month=========" + month);
				if (months < 10) {
					month = "0" + months;
				} else {
					month = Integer.toString(months);
				}
				disMonthYear = month + "-" + year;
				log.info("disMonthYear=================" + disMonthYear
						+ "month=========" + month);
				fullMonthName = commonUtil.converDBToAppFormat(
						disMonthYear, "MM-yyyy", "MMMM-yyyy");
				selectedDate = "%-" + displayDate;
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}

		} else {
			selectedDate = year;
			displayDate = year;
		}
		ArrayList regionList = new ArrayList();
		if (region.equals("NO-SELECT")) {
			regionList = commonUtil.loadRegions();
		} else {
			RegionBean bean = new RegionBean();
			bean.setRegion(region);
			bean.setAirportcode("-NA-");
			regionList.add(bean);
		}

		log.info("selectedDate==========getform3=====" + selectedDate
				+ "airportList==Size==" + region);
		request.setAttribute("empName", empName);
		request.setAttribute("empNameFlag", empflag);
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("selectedDate", selectedDate);
		request.setAttribute("displayDate", fullMonthName);
		request.setAttribute("region", regionList);
		request.setAttribute("reportType", reportType);
		request.setAttribute("sortingOrder", sortingOrder);
		String returnPath="";
		if (formType.equals("FORM-3")) {
			returnPath="loadform3form";
		} else if (formType.equals("FORM-3-PS")) {
			returnPath="loadform3psform";
		}
		return mapping.findForward(returnPath);
	}
//	This method copied from reportservlet
	public ActionForward dupform3(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		String reportType = "", sortingOrder = "";

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}

		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfaccno";
		}

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}

		log.info("region==========getform3=====" + region + "year" + year
				+ "month" + month);
		String fullMonthName = "";
		if (!month.equals("00")) {
			frmMonthYear = "%" + "-" + month + "-" + year;
			disMonthYear = month + "-" + year;
			try {
				displayDate = commonUtil.converDBToAppFormat(disMonthYear,
						"MM-yyyy", "MMM-yyyy");
				int months = 0;
				months = Integer.parseInt(month) - 1;
				log.info("months=================" + months
						+ "month=========" + month);
				if (months < 10) {
					month = "0" + months;
				} else {
					month = Integer.toString(months);
				}
				disMonthYear = month + "-" + year;
				log.info("disMonthYear=================" + disMonthYear
						+ "month=========" + month);
				fullMonthName = commonUtil.converDBToAppFormat(
						disMonthYear, "MM-yyyy", "MMMM-yyyy");
				selectedDate = "%-" + displayDate;
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}

		} else {
			selectedDate = year;
			displayDate = year;
		}

		log.info("selectedDate==========getform3=====" + selectedDate
				+ "airportList==Size==" + region);

		request.setAttribute("selectedDate", selectedDate);
		request.setAttribute("displayDate", fullMonthName);
		request.setAttribute("reportType", reportType);
		request.setAttribute("sortingOrder", sortingOrder);
	/*	RequestDispatcher rd = request
		.getRequestDispatcher("./PensionView/PensionForm3DuplicateDataReport.jsp");*/
		return mapping.findForward("loadform3dupform");
	}	
//	This method copied from reportservlet
	public ActionForward form3pfid(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";

		String formType = "", reportType = "", airportcode = "", sortingOrder = "";

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}

		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}
		log.info("region==========getform3=====" + region + "year" + year
				+ "month" + month + "airportcode" + airportcode
				+ "sortingOrder====" + sortingOrder);
		String fullMonthName = "";
		if (!month.equals("00")) {
			frmMonthYear = "%" + "-" + month + "-" + year;
			disMonthYear = month + "-" + year;
			try {
				displayDate = commonUtil.converDBToAppFormat(disMonthYear,
						"MM-yyyy", "MMM-yyyy");
				int months = 0;
				months = Integer.parseInt(month) - 1;
				log.info("months=================" + months
						+ "month=========" + month);
				if (months < 10) {
					month = "0" + months;
				} else {
					month = Integer.toString(months);
				}
				disMonthYear = month + "-" + year;
				log.info("disMonthYear=================" + disMonthYear
						+ "month=========" + month);
				fullMonthName = commonUtil.converDBToAppFormat(
						disMonthYear, "MM-yyyy", "MMMM-yyyy");
				selectedDate = "01-" + displayDate;
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}

		} else {
			selectedDate = year;
			displayDate = year;
		}

		ArrayList form3PSList = new ArrayList();
		form3PSList = finReportService.financeForm3ByPFID(selectedDate,
				region, airportcode, reportType, sortingOrder);
		request.setAttribute("form3PSList", form3PSList);
		request.setAttribute("displayDate", displayDate);
		request.setAttribute("selectedDate", selectedDate);
		request.setAttribute("reportType", reportType);
		log.info("displayDate" + displayDate + "formType============="
				+ formType);
		String returnPath="";
		if (formType.equals("FORM-3-PFID")) {
			returnPath="loadform3pfidform";
		} else if (formType.equals("FORM-3-PS-PFID")) {
			returnPath="loadform3pfidpsform";
		}


		return mapping.findForward(returnPath);
	}
//	This method copied from reportservlet
	public ActionForward loadrpfcform4(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadrpfcform4inputparams");
		return mapping.findForward("loadrpfcform4inputparams");
	}	
	
//	This method copied from reportservlet
	public ActionForward loadrpfcform5(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadrpfcform5inputparams");
		return mapping.findForward("loadrpfcform5inputparams");
	}	
//	This method copied from reportservlet
	public ActionForward getrpfcform4(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		String airportcode = "", reportType = "", sortingOrder = "", formType = "", pensionno = "", toYear = "";
		ArrayList form4List = new ArrayList();
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "pensionno";
		}

		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}

		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}

		String currentDate = "", prevDate = "";
		if (Integer.parseInt(month) >= 4 && Integer.parseInt(month) <= 12) {
			currentDate = "01/" + month + "/" + year;
			prevDate = "01/" + (Integer.parseInt(month) - 1) + "/" + year;
		} else {
			currentDate = "01/" + month + "/" + toYear;
			if (Integer.parseInt(month) - 1 == 0) {
				prevDate = "01/12/" + year;
			} else {
				prevDate = "01/" + (Integer.parseInt(month) - 1) + "/"
				+ toYear;
			}

		}
		try {
			currentDate = commonUtil.converDBToAppFormat(currentDate,
					"dd/MM/yyyy", "dd/MMM/yyyy");
			prevDate = commonUtil.converDBToAppFormat(prevDate,
					"dd/MM/yyyy", "dd/MMM/yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		form4List = financeService.rpfcForm4Report(airportcode, prevDate,
				currentDate, region, pensionno, sortingOrder);

		log.info("region=====form4List=====" + region + "From year" + year
				+ "month" + month + "pensionno===" + pensionno
				+ "toYear=====" + toYear);
		log.info("Current Date=====getform5=====" + currentDate
				+ "prevDate" + prevDate + "airportcode" + airportcode);
		ArrayList regionList = new ArrayList();
		if (region.equals("NO-SELECT")) {
			regionList = commonUtil.loadRegions();
		} else {
			RegionBean bean = new RegionBean();
			bean.setRegion(region);
			bean.setAirportcode("-NA-");
			regionList.add(bean);
		}

		try {
			selectedDate = commonUtil.converDBToAppFormat(prevDate,
					"dd/MMM/yyyy", "MMM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("selectedDate", selectedDate);
		request.setAttribute("dataList", form4List);
		request.setAttribute("region", regionList);
		request.setAttribute("reportType", reportType);
		request.setAttribute("sortingOrder", sortingOrder);
		return mapping.findForward("rpfcform4");
	}
//	This method copied from reportservlet
	public ActionForward getrpfcform5(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		String airportcode = "", reportType = "", sortingOrder = "", formType = "", pensionno = "", toYear = "";
		ArrayList form5List = new ArrayList();
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "pensionno";
		}

		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}

		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}

		String currentDate = "", prevDate = "";
		if (Integer.parseInt(month) >= 4 && Integer.parseInt(month) <= 12) {
			currentDate = "01/" + month + "/" + year;
			prevDate = "01/" + (Integer.parseInt(month) - 1) + "/" + year;
		} else {
			currentDate = "01/" + month + "/" + toYear;
			if (Integer.parseInt(month) - 1 == 0) {
				prevDate = "01/12/" + year;
			} else {
				prevDate = "01/" + (Integer.parseInt(month) - 1) + "/"
				+ toYear;
			}

		}
		try {
			currentDate = commonUtil.converDBToAppFormat(currentDate,
					"dd/MM/yyyy", "dd/MMM/yyyy");
			prevDate = commonUtil.converDBToAppFormat(prevDate,
					"dd/MM/yyyy", "dd/MMM/yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		form5List = financeService.rpfcForm5Report(prevDate, currentDate,
				region, pensionno, airportcode, sortingOrder);

		log.info("region=====getform5=====" + region + "From year" + year
				+ "month" + month + "pensionno===" + pensionno
				+ "toYear=====" + toYear);
		log.info("Current Date=====getform5=====" + currentDate
				+ "prevDate" + prevDate + "airportcode" + airportcode);
		ArrayList regionList = new ArrayList();
		if (region.equals("NO-SELECT")) {
			regionList = commonUtil.loadRegions();
		} else {
			RegionBean bean = new RegionBean();
			bean.setRegion(region);
			bean.setAirportcode("-NA-");
			regionList.add(bean);
		}

		try {
			selectedDate = commonUtil.converDBToAppFormat(prevDate,
					"dd/MMM/yyyy", "MMM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("selectedDate", selectedDate);
		request.setAttribute("dataList", form5List);
		request.setAttribute("region", regionList);
		request.setAttribute("reportType", reportType);
		request.setAttribute("sortingOrder", sortingOrder);
	
		return mapping.findForward("rpfcform5");
	}
	public ActionForward loadpersonalreport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadpersonalreport");
		return mapping.findForward("loadpersonalreport");
	}
	public ActionForward personalreport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){

		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";

		String formType = "", reportType = "", airportcode = "", sortingOrder = "", seperationResaon = "";

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}

		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		}
		if (request.getParameter("frm_reason") != null) {
			seperationResaon = request.getParameter("frm_reason");
		}
		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (airportcode.equals("[Select One]")) {
			airportcode = "";
		} else if (airportcode.equals("NO-SELECT")) {
			airportcode = "";
		}
		if (region.equals("[Select One]")) {
			region = "";
		} else if (region.equals("NO-SELECT")) {
			region = "";
		}
		if (seperationResaon.equals("[Select One]")) {
			seperationResaon = "NO-SELECT";
		} else if (seperationResaon.equals("NO-SELECT")) {
			seperationResaon = "NO-SELECT";
		}
		log.info("region==========getform3=====" + region + "year" + year
				+ "month" + month + "airportcode" + airportcode);
		String fullMonthName = "";
		if (!year.equals("Select One") || !year.equals("0-[Select One]")) {
			if (!month.equals("00")) {
				frmMonthYear = "%" + "-" + month + "-" + year;
				disMonthYear = month + "-" + year;
				try {
					displayDate = commonUtil.converDBToAppFormat(
							disMonthYear, "MM-yyyy", "MMM-yyyy");
					int months = 0;
					months = Integer.parseInt(month) - 1;
					log.info("months=================" + months
							+ "month=========" + month);
					if (months < 10) {
						month = "0" + months;
					} else {
						month = Integer.toString(months);
					}
					disMonthYear = month + "-" + year;
					log.info("disMonthYear=================" + disMonthYear
							+ "month=========" + month);
					fullMonthName = commonUtil.converDBToAppFormat(
							disMonthYear, "MM-yyyy", "MMMM-yyyy");
					selectedDate = "01-" + displayDate;
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					log.printStackTrace(e);
				}

			} else {
				selectedDate = year;
				displayDate = year;
			}
		}

		ArrayList form3PSList = new ArrayList();
		ArrayList form3RetrList = new ArrayList();
		form3PSList = finReportService.personalReportByPFID(selectedDate,
				region, airportcode, sortingOrder, seperationResaon, false);
		if (seperationResaon.equals("Retirement")) {
			form3RetrList = finReportService.personalReportByPFID(
					selectedDate, region, airportcode, sortingOrder,
					seperationResaon, true);
			request.setAttribute("form3RetrList", form3RetrList);
		}
		displayDate = "01-Mar-2009";
		selectedDate = "01-Mar-2009";
		request.setAttribute("form3PSList", form3PSList);
		request.setAttribute("displayDate", displayDate);
		request.setAttribute("selectedDate", selectedDate);
		if (seperationResaon.equals("NO-SELECT")) {
			seperationResaon = "";
		}
		request.setAttribute("seperationResaon", seperationResaon);
		request.setAttribute("reportType", reportType);
		log.info("displayDate" + displayDate + "formType============="
				+ formType);
	
		
		return mapping.findForward("personalreportform");
	}
	public ActionForward loadrpfcform6a(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadrpfcform6inputparams");
		return mapping.findForward("loadrpfcform6inputparams");
	
	}
	public ActionForward rpfcform6areport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", regionDesc = "", year = "", month = "", monthDesc = "", toYear = "", selectedDate = "", frmMonthYear = "", shnYear = "", displayDate = "", disMonthYear = "";

		String airportcode = "", reportType = "", totalSub = "", sortingOrder = "", pfidString = "", empName = "", empflag = "false", pensionno = "", frmSelectedDts = "";
		ArrayList epfForm3List = new ArrayList();
		ArrayList missingPFIDsList = new ArrayList();
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
		} else {
			pfidString = "";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "PENSIONNO";
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
			frmSelectedDts = getFromToDates(year, toYear, month,
					month);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("===Form-6A=====" + region + "year" + frmSelectedDts
				+ "empflag=========" + empflag + "pfidString" + pfidString
				+ "pensionno" + pensionno);
		EPFFormsReportService epfformsService = new EPFFormsReportService();
		epfForm3List = epfformsService.loadEPFForm6AReport(pfidString,
				region, airportcode, empName, empflag, frmSelectedDts,
				sortingOrder, pensionno);
		missingPFIDsList = epfformsService.loadEPFMissingTransPFIDs(
				pfidString, region, airportcode, empName, empflag,
				frmSelectedDts, sortingOrder, pensionno);
		log.info("===AAI EPF-3==missingPFIDsList==="
				+ missingPFIDsList.size());
		request.setAttribute("cardList", epfForm3List);
		request.setAttribute("missingPFIDsList", missingPFIDsList);

		if (!year.equals("NO-SELECT")) {
			shnYear = year + "-" + toYear;
		} else {
			shnYear = "1995-" + commonUtil.getCurrentDate("yyyy");
		}

		if (epfForm3List.size() != 0) {
			totalSub = Integer.toString(epfForm3List.size());
		} else {
			totalSub = "0";
		}
		if (!region.equals("NO-SELECT")) {
			regionDesc = region;
		} else {
			regionDesc = "";
		}
		try {
			monthDesc = commonUtil.converDBToAppFormat("01-" + month + "-"
					+ commonUtil.getCurrentDate("yyyy"), "dd-MM-yyyy",
			"MMM");
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
		
		return mapping.findForward("loadrpfcform6form");
	}
	public ActionForward loadform7Input(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		Map monthMap = new LinkedHashMap();
	
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadform7Input");
		return mapping.findForward("loadform7Inputparams");
	}
	public ActionForward form7report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		String airportcode = "", reportType = "",returnPath="", sortingOrder = "pensionno", pfidString = "",formType="";
		ArrayList list = new ArrayList();

		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}
		log.info("Search Servlet" + pfidString);

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "", airportCode = "";
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
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		String formArrRvsFormFlag="";
		
		boolean chkMultipleYearFlag = false;
		log.info("pfidString" + pfidString + "year" + year + "sortingOrder"
				+ sortingOrder + "region" + region + "airportCode"
				+ airportCode + "pensionno" + pensionno);
		String formVal = "";
		if (formType.equals("FORM-7PS-SUMMARY")) {
			formVal = "Summary";
			formArrRvsFormFlag="N";
		}else if(formType.equals("FORM-7PS-REVISED")){
			formVal = "Revised";
			formArrRvsFormFlag="Y";
		} else {
			formVal = "NonSummary";
			formArrRvsFormFlag="N";
		}
		if (year.equals("NO-SELECT") && !pensionno.equals("")) {
			
				list = finReportService.allYearsForm7PrintOutForPFID(
						pfidString, year, sortingOrder, region, airportCode,
						pensionno, empflag, empName, formVal,formArrRvsFormFlag);
				chkMultipleYearFlag = true;
				year = "1995";
			
			
		} else {
			list = finReportService.form7PrintingReport(pfidString, year,
					sortingOrder, region, airportCode, pensionno, empflag,
					empName, formVal,formArrRvsFormFlag);
		}

		request.setAttribute("form8List", list);
		log.info("==========Form-7=====" + region + "year" + year);
		String nextYear = "", shnYear = "",xlsPurspose="";
		if (chkMultipleYearFlag == false) {
			int nxtYear = Integer.parseInt(year) + 1;
			nextYear = Integer.toString(nxtYear);
			shnYear = "01-Apr-" + year + " To Mar-" + nextYear;
			xlsPurspose=pfidString+"_"+year+"_"+nextYear;
		} else {
			shnYear = "";
			xlsPurspose="";
		}

		request.setAttribute("chkYear", year);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", region);

		if(chkMultipleYearFlag==true){
			if (formType.equals("FORM-7PS-SUMMARY")) {
				returnPath="form7MultiYearsSummaryform";
				
			} else {
				returnPath="form7MultiYearsform";
			}
		}else if(formType.equals("FORM-7PS-REVISED")){
			request.setAttribute("xlsPurspose", xlsPurspose);
			returnPath="form7revised";
		}else{
			returnPath="form7form";
		}
		return mapping.findForward(returnPath);
	}
	public ActionForward form7reportzero(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";

		String airportcode = "", reportType = "", sortingOrder = "", pfidString = "";
		ArrayList list = new ArrayList();
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}
		log.info("Search Servlet" + pfidString);

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "", airportCode = "";
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

		log.info("pfidString" + pfidString + "year" + year + "sortingOrder"
				+ sortingOrder + "region" + region + "airportCode"
				+ airportCode + "pensionno" + pensionno);
		list = finReportService.form7ZeroPrintingReport(pfidString, year,
				sortingOrder, region, airportCode, pensionno, empflag,
				empName);
		request.setAttribute("form8List", list);
		log.info("==========Form-7=====" + region + "year" + year);
		String nextYear = "";
		int nxtYear = Integer.parseInt(year.substring(2, 4)) + 1;
		log.info("==========Form-7=====" + nxtYear);
		if (nxtYear >= 1 && nxtYear <= 9) {
			nextYear = year.substring(0, 2) + "0" + nxtYear;
		} else {
			log.info("==========nextYear===Length==" + nextYear.length());
			nextYear = Integer.toString(nxtYear);

			if (nextYear.length() == 3) {
				nextYear = Integer.toString(Integer.parseInt(year
						.substring(0, 2)) + 1)
						+ "00";
			} else {
				nextYear = year.substring(0, 2) + nxtYear;
			}
		}
		String shnYear = "01-Apr-" + year + " To Mar-" + nextYear;
		request.setAttribute("chkYear", year);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", region);
		
		return mapping.findForward("form7reportzero");
	}
	public ActionForward form7Indexreport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";

		String airportcode = "", reportType = "", sortingOrder = "pensionno", pfidString = "";
		ArrayList list = new ArrayList();

		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("frm_airportcd") != null) {
			airportcode = request.getParameter("frm_airportcd");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}
		log.info("Search Servlet" + pfidString);

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "", airportCode = "";
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

		log.info("pfidString" + pfidString + "year" + year + "sortingOrder"
				+ sortingOrder + "region" + region + "airportCode"
				+ airportCode + "pensionno" + pensionno);
		FinancialReportService finReportService=new FinancialReportService();
		list = finReportService.form7PrintingIndexReport(pfidString, year,
				sortingOrder, region, airportCode, pensionno, empflag,
				empName);

		request.setAttribute("form7IndexList", list);
		log.info("==========Form-7 Index Page=====" + region + "year" + year);
		String nextYear = "";
		int nxtYear = Integer.parseInt(year.substring(2, 4)) + 1;
		log.info("==========Form-7 Index Page=====" + nxtYear);
		if (nxtYear >= 1 && nxtYear <= 9) {
			nextYear = year.substring(0, 2) + "0" + nxtYear;
		} else {
			log.info("==========nextYear===Length==" + nextYear.length());
			nextYear = Integer.toString(nxtYear);

			if (nextYear.length() == 3) {
				nextYear = Integer.toString(Integer.parseInt(year
						.substring(0, 2)) + 1)
						+ "00";
			} else {
				nextYear = year.substring(0, 2) + nxtYear;
			}
		}
		String shnYear = "01-Apr-" + year + " To Mar-" + nextYear;
		request.setAttribute("chkYear", year);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", region);
	
		return mapping.findForward("form7IndexReport");
	}
	
	public ActionForward loadform8params(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadform8Inputparams");
		return mapping.findForward("loadform8Inputparams");
	}
	public ActionForward loadForm8(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", year = "", month = "", selectedDate = "", frmMonthYear = "", displayDate = "", disMonthYear = "";
		Form8Bean form8Bean = new Form8Bean();
		String airportcode = "", reportType = "", sortingOrder = "", formType = "";
		ArrayList list = new ArrayList();

		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}

		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}

		if (!request.getParameter("sortingOrder").equals("")) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfacno";
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		String empName = "", airportCode = "";
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
		if (request.getParameter("frm_airportcd") != null) {
			airportCode = request.getParameter("frm_airportcd");
		}
		ArrayList form8List = new ArrayList();
		boolean chkMultipleYearFlag=false;
		if(!pensionno.equals("") && (year.equals("NO-SELECT")|| year.equals("Select One"))){
			form8List = finReportService.getAllYearsForm8PrintOut(year, month, sortingOrder,
					region, airportCode, pensionno,formType);
			chkMultipleYearFlag=true;
		}else{
			form8List = finReportService.form8Report(year, month, sortingOrder,
					region, airportCode, pensionno,formType);
		}
	
		// request.setAttribute("form8List", form8Bean);
		request.setAttribute("form8List", form8List);

		log.info("==========Form-8=====" + region + "year" + year);

		String nextYear = "", finalNextYear = "", shnYear = "", remtYears = "", pensHdingFlag = "";
		int years = 0;
		if(chkMultipleYearFlag==false){
			if (!year.equals("Select One")) {
				int nxtYear = Integer.parseInt(year) + 1;
				nextYear=Integer.toString(nxtYear);
				shnYear = "Mar-" + year + " To Feb-" + nextYear;
			}else{
				shnYear = "Mar-1995 To Feb-2008";
				remtYears = "1995-2008";
			}
		}else{
			shnYear="";
		}

		request.setAttribute("dspYear", shnYear);
		request.setAttribute("pensionHding", pensHdingFlag);
		request.setAttribute("dspRemtYears", remtYears);
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", region);
		String returnPath="";
		if(chkMultipleYearFlag==true){
			returnPath="form8PSPFIDReport";
		}else{
			if (formType.equals("FORM-8")) {
				returnPath="form8Report";
			} else if (formType.equals("FORM-8-PS")||formType.equals("FORM-8-PS-Arrear")) {
				returnPath="form8PSReport";
				
			}
		}
		
		return mapping.findForward(returnPath);
	}
public ActionForward loadtrustpcInput(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		Map monthMap = new LinkedHashMap();
	
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request,"loadtrustpcInput");
		return mapping.findForward("loadtrustpcInput");
	}
	public ActionForward trustreport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String region = "", regionDesc = "", year = "", month = "", toYear = "", selectedDate = "", frmMonthYear = "", shnYear = "", displayDate = "", disMonthYear = "";

		String airportcode = "", division = "", reportType = "", totalSub = "", sortingOrder = "", pfidString = "", empName = "", empflag = "false", pensionno = "", frmSelectedDts = "";
		ArrayList truswistList = new ArrayList();
	
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
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
		if (request.getParameter("frm_division") != null) {
			division = request.getParameter("frm_division");
		}
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		} else {
			pfidString = "";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "PENSIONNO";
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
		if (year.equals("NO-SELECT") && month.equals("NO-SELECT")) {
			selectedDate = "";
		} else {
			if (!month.equals("NO-SELECT")) {
				selectedDate = "01-" + month + "-" + year;
			} else {
				selectedDate = "01-03-" + year;
			}

		}

		log.info("===Trust PC Report=====" + region + "year" + year
				+ "empflag=========" + empflag + "pfidString" + pfidString
				+ "pensionno" + pensionno + "selectedDate=" + selectedDate);

		try {
			truswistList = finReportService.loadgetTrustPCReport(
					pfidString, division, region, airportcode, empName,
					empflag, commonUtil.converDBToAppFormat(selectedDate,
							"dd-MM-yyyy", "dd-MMM-yyyy"), sortingOrder,
							pensionno);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("cardList", truswistList);

		if (!year.equals("NO-SELECT")) {
			shnYear = year + "-" + toYear;
		} else {
			shnYear = "1995-" + commonUtil.getCurrentDate("yyyy");
		}

		if (!region.equals("NO-SELECT")) {
			regionDesc = region;
		} else {
			regionDesc = "";
		}

		request.setAttribute("regionDesc", regionDesc);
		request.setAttribute("airportcode", airportcode);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("dspMonth", month);
		request.setAttribute("reportType", reportType);
	
		return mapping.findForward("trustpcform");
	}
}
