package com.epis.action.rpfc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epis.action.advances.ForwardParameters;
import com.epis.bean.rpfc.Form8Bean;

import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.services.rpfc.PensionService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class ReportAction extends CommonRPFCAction {
	CommonUtil commonUtil = new CommonUtil();

	CommonDAO commonDAO = new CommonDAO();

	FinancialReportService finReportService = new FinancialReportService();

	PensionService ps = new PensionService();

	Log log = new Log(ReportAction.class);

	public ActionForward loadpfcardInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		loadPrimarilyInfo(request, "loadpfcardInput");
		return mapping.findForward("loadpfcardInput");
	}

	public ActionForward loadpcsummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		loadPrimarilyInfo(request, "loadpcsummary");
		return mapping.findForward("loadpcsummaryreport");
	}

	public ActionForward loadTransferInOutparams(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		LoginInfo logInfo = new LoginInfo();
		HttpSession session = request.getSession(true);
		logInfo = (LoginInfo) session.getAttribute("user");
		String userName = logInfo.getUserName();
		String userType = logInfo.getUserType();

		request.setAttribute("frmUserName", userName);
		session.setAttribute("usertype", userType);

		loadPrimarilyInfo(request, "loadtransferinoutparams");
		return mapping.findForward("loadtransferinoutparams");

	}

	public ActionForward loadFinContri(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		LoginInfo logInfo = new LoginInfo();
		HttpSession session = request.getSession(true);
		logInfo = (LoginInfo) session.getAttribute("user");
		String userName = logInfo.getUserName();
		request.setAttribute("frmUserName", userName);
		loadPrimarilyInfo(request, "loadpcreportInput");
		return mapping.findForward("loadpcreportInput");
	}

	public ActionForward cardReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "", year = "";
		String reportType = "", sortingOrder = "", pfidString = "", lastmonthFlag = "", lastmonthYear = "", frmName = "";
		ArrayList list = new ArrayList();
		String airportCode = "", bulkPrintFlag = "", signature = "";

		if (request.getParameter("frm_signature") != null) {
			signature = request.getParameter("frm_signature");
		}
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}

		if (request.getParameter("frm_blkprintflag") != null) {
			bulkPrintFlag = request.getParameter("frm_blkprintflag");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("frmAirportCode") != null) {
			airportCode = request.getParameter("frmAirportCode");
		}

		if (frmName.equals("CPFDeviation")) {
			if (request.getParameter("frm_year") != null) {
				year = request.getParameter("frm_year");
			}

			String[] arr = year.split("-");
			String fromYear = arr[0];
			String toYear = arr[1].substring(2, 4);
			year = fromYear;

		} else {
			if (request.getParameter("frm_year") != null) {
				year = request.getParameter("frm_year");
			}
		}
		if (request.getParameter("frm_region") != null
				&& request.getParameter("frm_region") != "") {
			region = request.getParameter("frm_region");
		}
		log.info("region " + region);
		if (request.getParameter("frm_pfids") != null
				&& request.getParameter("frm_pfids") != "") {
			pfidString = request.getParameter("frm_pfids");
		} else {
			pfidString = "";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfacno";
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
		if (request.getParameter("transMonthYear") != null) {
			lastmonthYear = request.getParameter("transMonthYear");
		}
		if (request.getParameter("frm_ltstmonthflag") != null) {
			lastmonthFlag = request.getParameter("frm_ltstmonthflag");
		}
		log.info("===cardReport=====" + region + "year" + year
				+ "empflag=========" + empflag + "pfidString" + pfidString
				+ "pensionno" + pensionno + "lastmonthYear==" + lastmonthYear);
		if (pfidString.equals("")) {
			list = finReportService.pfCardReport(region, year, empflag,
					empName, sortingOrder, pensionno);
		} else {
			list = finReportService.pfCardReportPrinting(pfidString, region,
					year, empflag, empName, sortingOrder, pensionno,
					lastmonthFlag, lastmonthYear, airportCode);
		}

		request.setAttribute("cardList", list);

		String nextYear = "", shnYear = "";
		if (!year.equals("Select One")) {
			int nxtYear = Integer.parseInt(year.substring(2, 4)) + 1;
			if (nxtYear >= 1 && nxtYear <= 9) {
				nextYear = "0" + nxtYear;
			} else {
				log.info("==========nextYear=====" + nextYear.length());
				nextYear = Integer.toString(nxtYear);

				if (nextYear.length() == 3) {
					nextYear = "00";
				}
			}
			shnYear = year + "-" + nextYear;
		} else {
			shnYear = "1995-2010";
		}
		request.setAttribute("reportType", reportType);
		String regionString = "", stationString = "";
		if (region.equals("NO-SELECT")) {
			regionString = "";
		} else {
			regionString = region;
		}
		if (airportCode.equals("NO-SELECT")) {
			stationString = "";
		} else {
			if (region.equals("CHQIAD")) {
				stationString = airportCode;
			} else {
				stationString = "";
			}

		}
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", regionString);
		request.setAttribute("airportCode", stationString);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("blkprintflag", bulkPrintFlag);
		request.setAttribute("signature", signature);
		return mapping.findForward("pfcardInput");
	}

	public ActionForward getReportPenContr(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String region = "", finYear = "", reportType = "", airportCode = "", finMonth = "", formType = "", selectedMonth = "", empserialNO = "";
		String cpfAccno = "", page = "", toYear = "";
		String transferFlag = "", pfidString = "", path = "", chkBulkPrint = "";

		ArrayList pensionContributionList = new ArrayList();
		if (request.getParameter("frm_pfids") != null) {
			pfidString = request.getParameter("frm_pfids");
		}
		log.info("Search Servlet" + pfidString);
		if (request.getParameter("cpfAccno") != null) {
			cpfAccno = request.getParameter("cpfAccno");
		}
		if (request.getParameter("transferStatus") != null) {
			transferFlag = request.getParameter("transferStatus");
		}
		String mappingFlag = "";
		if (request.getParameter("mappingFlag") != null) {
			mappingFlag = request.getParameter("mappingFlag");

		}

		if (request.getParameter("page") != null) {
			page = request.getParameter("page");
		}

		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
			// region="NO-SELECT";
		} else {
			region = "NO-SELECT";
		}
		log.info("region in servelt ***** " + region);
		if (request.getParameter("frm_year") != null) {
			finYear = request.getParameter("frm_year");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frm_month") != null) {
			selectedMonth = request.getParameter("frm_month");
		}
		if (request.getParameter("empserialNO") != null) {
			empserialNO = commonUtil.getSearchPFID1(request.getParameter(
					"empserialNO").toString().trim());
		}
		// /
		// empserialNO=commonUtil.trailingZeros(empserialNO.toCharArray());
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportCode = request.getParameter("frm_airportcode");

		} else {
			airportCode = "NO-SELECT";
		}
		if (request.getParameter("frm_toyear") != null) {
			toYear = request.getParameter("frm_toyear");

		} else {
			airportCode = "NO-SELECT";
		}
		if (request.getParameter("frm_bulkprint") != null) {
			chkBulkPrint = request.getParameter("frm_bulkprint");
		}
		log.info("region====" + region + "finYear" + finYear + "reportType"
				+ reportType + "airportCode==" + airportCode + "Month==="
				+ selectedMonth);
		pensionContributionList = finReportService
				.getPensionContributionReport(toYear, finYear, region,
						airportCode, selectedMonth, empserialNO, cpfAccno,
						transferFlag, mappingFlag, pfidString, chkBulkPrint);
		log.info("pensionContributionList " + pensionContributionList.size());

		request.setAttribute("penContrList", pensionContributionList);
		request.setAttribute("reportType", reportType);
		request.setAttribute("reportType", reportType);
		if (page.equals("")) {
			path = "getpcreport";
		} else if (page.equals("PensionContribution")) {
			path = "getpcreport";
		} else {
			path = "getpcscreen";
		}
		return mapping.findForward(path);
	}

	public ActionForward loadForm6Cmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		Iterator monthIterator1 = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		monthIterator1 = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("monthToIterator", monthIterator1);
		loadPrimarilyInfo(request, "loadForm6Cmp");
		return mapping.findForward("loadform6Comp");
	}

	public ActionForward loadForm6Comp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String fromYear = "", toYear = "", fromMonth = "", toMonth = "", region = "", reporType = "", formType = "";
		ArrayList regionList = new ArrayList();
		if (request.getParameter("frm_year") != null) {
			fromYear = request.getParameter("frm_year");
		}
		if (request.getParameter("to_year") != null) {
			toYear = request.getParameter("to_year");
		}
		if (request.getParameter("frm_month") != null) {
			fromMonth = request.getParameter("frm_month");
		}
		if (request.getParameter("to_month") != null) {
			toMonth = request.getParameter("to_month");
		}
		if (request.getParameter("frm_reportType") != null) {
			reporType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}

		if (region.equals("NO-SELECT")) {

			HashMap regionHashmap = new HashMap();
			HashMap sortedRegionMap = new LinkedHashMap();

			Iterator regionIterator = null;
			regionHashmap = commonUtil.getRegion();

			Set keys = regionHashmap.keySet();
			regionIterator = keys.iterator();
			String rgn = "";
			while (regionIterator.hasNext()) {
				rgn = regionHashmap.get(regionIterator.next()).toString();

				regionList.add(rgn);
			}

		} else {
			regionList.add(region);
		}
		String fromDate = "", toDate = "";

		if (!fromYear.equals("NO-SELECT") && !toYear.equals("NO-SELECT")) {
			if (!fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;

			} else if (fromMonth.equals("NO-SELECT")
					&& !toMonth.equals("NO-SELECT")) {
				fromDate = "01-04-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;
			} else if (!fromMonth.equals("NO-SELECT")
					&& toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-03-" + toYear;
			} else {
				fromDate = "01-04-" + fromYear;
				toDate = "01-03-" + toYear;
			}
		} else if (fromYear.equals("NO-SELECT") && !toYear.equals("NO-SELECT")) {
			fromYear = "1995";
			if (!fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;
			} else if (fromMonth.equals("NO-SELECT")
					&& !toMonth.equals("NO-SELECT")) {
				fromDate = "01-04-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;
			} else if (!fromMonth.equals("NO-SELECT")
					&& toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-03-" + toYear;
			} else {
				fromDate = "01-04-" + fromYear;
				toDate = "01-03-" + toYear;
			}
		} else if (!fromYear.equals("NO-SELECT") && toYear.equals("NO-SELECT")) {
			toYear = "2008";
			if (!fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;
			} else if (fromMonth.equals("NO-SELECT")
					&& !toMonth.equals("NO-SELECT")) {
				fromDate = "01-04-" + fromYear;
				toDate = "01-" + toMonth + "-" + toYear;
			} else if (!fromMonth.equals("NO-SELECT")
					&& toMonth.equals("NO-SELECT")) {
				fromDate = "01-" + fromMonth + "-" + fromYear;
				toDate = "01-03-" + toYear;
			} else {
				fromDate = "01-04-" + fromYear;
				toDate = "01-03-" + toYear;
			}
		} else if (fromYear.equals("NO-SELECT") && toYear.equals("NO-SELECT")) {
			fromDate = "01-04-1995";
			toDate = "01-03-2008";
		}
		int totalYears = 0;
		ArrayList dateList = new ArrayList();
		int fromYears = 0, toYears = 0;
		String dispFromYear = "", dispToYear = "", frmSeltYear = "", toSeltYear = "", finDspMnthYear = "";
		try {
			String fromSelYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MM-yyyy", "yyyy");
			dispFromYear = commonUtil.converDBToAppFormat(fromDate,
					"dd-MM-yyyy", "dd-MMM-yyyy");
			dispToYear = commonUtil.converDBToAppFormat(toDate, "dd-MM-yyyy",
					"dd-MMM-yyyy");
			String fromSelWithOutYear = commonUtil.converDBToAppFormat(
					fromDate, "dd-MM-yyyy", "dd-MMM-");
			String toSelYear = commonUtil.converDBToAppFormat(toDate,
					"dd-MM-yyyy", "yyyy");
			String toSelWithOutYear = commonUtil.converDBToAppFormat(toDate,
					"dd-MM-yyyy", "dd-MMM-");
			totalYears = Integer.parseInt(toSelYear)
					- Integer.parseInt(fromSelYear);

			StringBuffer buffer = new StringBuffer();
			if (totalYears != 0) {
				boolean yearFlag = false;
				for (int i = 0; i < totalYears; i++) {
					fromYears = Integer.parseInt(fromSelYear) + i;
					toYears = fromYears + 1;
					frmSeltYear = fromSelWithOutYear + fromYears;

					/*
					 * if(!formType.equals("FORM-6-PS-1995") &&
					 * yearFlag==false){ String[]
					 * mnthSel=frmSeltYear.split("-"); frmSeltYear=""; String
					 * days="",mnths="Dec",yers=""; days=mnthSel[0];
					 * yers=mnthSel[2]; frmSeltYear=days+"-"+mnths+"-"+yers;
					 * yearFlag=true; }
					 */
					toSeltYear = toSelWithOutYear + toYears;
					log.info("frmSeltYear" + frmSeltYear + "Select Year"
							+ toSeltYear);
					buffer.append(frmSeltYear);
					buffer.append(",");
					buffer.append(toSeltYear);
					dateList.add(buffer.toString());
					fromYears = 0;
					toYears = 0;
					frmSeltYear = "";
					toSeltYear = "";
					buffer = null;
					buffer = new StringBuffer();
				}
			} else if (totalYears == 0) {
				fromYears = Integer.parseInt(fromSelYear);
				toYears = fromYears;
				frmSeltYear = fromSelWithOutYear + fromYears;
				toSeltYear = toSelWithOutYear + toYears;
				buffer.append(frmSeltYear);
				buffer.append(",");
				buffer.append(toSeltYear);
				dateList.add(buffer.toString());
			}

		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			finDspMnthYear = commonUtil.converDBToAppFormat(dispFromYear,
					"dd-MMM-yyyy", "dd-MM-yyyy");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] fromYearArry = finDspMnthYear.split("-");
		int finFromMnh = 0, finFromYear = 0, finToYear = 0, selYear = 0;
		finFromMnh = Integer.parseInt(fromYearArry[1]);
		selYear = Integer.parseInt(fromYearArry[2]);
		if (finFromMnh >= 3 && finFromMnh <= 12) {
			finFromYear = selYear;
			selYear = selYear + 1;
			finToYear = selYear;
		} else if (finFromMnh >= 1 && finFromMnh <= 3) {
			finToYear = selYear;
			selYear = selYear - 1;
			finFromYear = selYear;
		}

		String diplayMessage = "Mar-" + finFromYear + " To " + "Feb-"
				+ finToYear;
		request.setAttribute("diplayMessage", diplayMessage);
		request.setAttribute("fromDate", dispFromYear);
		request.setAttribute("toDate", dispToYear);
		request.setAttribute("dateList", dateList);
		request.setAttribute("region", regionList);
		String returnPath = "";
		if (formType.equals("FORM-6-PS-1995")) {
			returnPath = "form61995Comp";

		} else {
			returnPath = "form6Comp";

		}

		return mapping.findForward(returnPath);
	}

	public ActionForward loadPFIDInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession httpsession = request.getSession(true);
		String path = "";
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) httpsession.getAttribute("user");
		/*
		 * if(request.getParameter("region")!=null){
		 * region=request.getParameter("region").toString(); //
		 * out.println("region "+region); }else{
		 * if(request.getAttribute("region")!=null){
		 * region=(String)request.getAttribute("region"); }else{ region=""; } }
		 */
		log.info("loadPFIDInformation" + logInfo.getProfile());
		if (!logInfo.getProfile().equals("M")) {
			loadPrimarilyInfo(request, "loadPFIDInformation");
			path = "commonPersSearch";
		} else {
			request.setAttribute("successinfo", "Fail");
			request.setAttribute("successMessage", "Permission Denied.");
			request.setAttribute("type", "success");
			request.setAttribute("sucessPath", "");
			request.setAttribute("windowflag", "popclose");
			path = "succcommonPersSearch";
		}

		return mapping.findForward(path);
	}

	// Copied From SearchPensionMasterServlet==getEmployeeMappedList
	public ActionForward getPFIDInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("getEmployeeMappedList () ");
		String region = request.getParameter("region").toString();
		if (region.equals("[Select One]")) {
			region = "";
		}
		String empName = request.getParameter("empName").toString().trim();
		String pfId = request.getParameter("pfId").toString().trim();
		pfId = commonUtil.getSearchPFID1(pfId.toString().trim());
		String transferType = "";
		if (request.getParameter("transferType") != null) {
			transferType = request.getParameter("transferType").toString();
		}
		ArrayList mappedList = new ArrayList();
		try {
			mappedList = ps.getEmployeeLookUpList(region, empName.trim(), pfId);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		request.setAttribute("MappedList", mappedList);
		request.setAttribute("ArrearInfo", "");
		request.setAttribute("transferType", transferType);

		log.info("Mapped LIst " + mappedList.size());
		loadPrimarilyInfo(request, "loadPFIDInformation");
		return mapping.findForward("commonPersSearch");
	}

	public ActionForward deleteTransactionData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cpfAccno = "", monthYear = "", region = "", pfid = "", airportcode = "";
		log.info("delete items  " + request.getParameterValues("cpfno"));
		String[] deletedmonths = {};
		String[] deleteTransactions = {};
		if (request.getParameterValues("cpfno") != null) {
			deletedmonths = (String[]) request.getParameterValues("cpfno");
		}
		for (int i = 0; i < deletedmonths.length; i++) {
			log.info(deletedmonths[i]);
			deleteTransactions = deletedmonths[i].split(",");
			for (int j = 0; j < deleteTransactions.length; j++) {
				monthYear = deleteTransactions[0].substring(1,
						deleteTransactions[0].length() - 1);
				cpfAccno = deleteTransactions[1].substring(1,
						deleteTransactions[1].length() - 1);
				region = deleteTransactions[2].substring(1,
						deleteTransactions[2].length() - 1);
				airportcode = deleteTransactions[3].substring(1,
						deleteTransactions[3].length() - 1);
			}

			if (request.getParameter("pfid") != null) {
				pfid = request.getParameter("pfid");
			}
			LoginInfo logInfo = new LoginInfo();
			HttpSession session = request.getSession(true);
			logInfo = (LoginInfo) session.getAttribute("user");
			String userName = logInfo.getUserName();
			String ipAddress = getIPAddress(request);

			pfid = commonUtil.trailingZeros(pfid.toCharArray());
			finReportService.deleteTransactionData(cpfAccno, monthYear, region,
					airportcode, ipAddress, userName, pfid);
		}
		int insertedRec = finReportService.preProcessAdjOB(pfid);
		// log.info("deleteTransactionData=============Current Date========="
		// +
		// commonUtil.getCurrentDate("dd-MMM-yyyy")+"insertedRec"+insertedRec
		// );
		String reportType = "Html";
		String yearID = "NO-SELECT";
		// region = "NO-SELECT";
		String pfidStrip = "1 - 1";
		/*
		 * String params = "&frm_region=" + region + "&frm_airportcode=" +
		 * airportcode + "&frm_year=" + yearID + "&frm_reportType=" + reportType +
		 * "&empserialNO=" + pfid + "&frm_pfids=" + pfidStrip;
		 */
		ForwardParameters fwdParams = new ForwardParameters();
		ActionForward forward = mapping
				.findForward("/reportservlet?method=getReportPenContr");
		Map params = new HashMap();
		params.put("frm_region", region);
		params.put("frm_airportcode", airportcode);
		params.put("frm_year", yearID);
		params.put("frm_reportType", reportType);
		params.put("empserialNO", pfid);
		params.put("frm_pfids", pfidStrip);
		fwdParams.add(params);
		return fwdParams.forward(forward);
	}

	public void editTransactionData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String cpfAccno = "", monthYear = "", region = "", pfid = "", airportcode = "", from7narration = "";
		log.info("Edit items  " + request.getParameterValues("cpfno"));
		String emoluments = "0.00", editid = "", vpf = "0.00", principle = "0.00", interest = "0.00", contri = "0.00", loan = "0.00", aailoan = "0.00", advance = "0.00", pcheldamt = "0.00";
		String noofmonths = "", arrearflag = "", duputationflag = "";
		LoginInfo logInfo = new LoginInfo();
		String pensionoption = "";
		if (request.getParameter("pensionNo") != null) {
			pfid = commonUtil.getSearchPFID1(request.getParameter("pensionNo")
					.toString());
		}
		if (request.getParameter("cpfaccno") != null) {
			cpfAccno = request.getParameter("cpfaccno");
		}
		if (request.getParameter("monthyear") != null) {
			monthYear = request.getParameter("monthyear");
		}
		if (request.getParameter("emoluments") != null) {
			emoluments = request.getParameter("emoluments");
		}
		if (request.getParameter("vpf") != null) {
			vpf = request.getParameter("vpf");
		}
		if (request.getParameter("principle") != null) {
			principle = request.getParameter("principle");
		}
		if (request.getParameter("interest") != null) {
			interest = request.getParameter("interest");
		}
		if (request.getParameter("region") != null) {
			region = request.getParameter("region");
		}
		if (request.getParameter("airportcode") != null) {
			airportcode = request.getParameter("airportcode");
		}
		if (request.getParameter("contri") != null) {
			contri = request.getParameter("contri");
		}
		if (request.getParameter("advance") != null) {
			advance = request.getParameter("advance");
		}
		if (request.getParameter("loan") != null) {
			loan = request.getParameter("loan");
		}
		if (request.getParameter("aailoan") != null) {
			aailoan = request.getParameter("aailoan");
		}
		if (request.getParameter("from7narration") != null) {
			from7narration = request.getParameter("from7narration");
		}
		if (request.getParameter("pcheldamt") != null) {
			pcheldamt = request.getParameter("pcheldamt");
		}
		if (request.getParameter("noofmonths") != null) {
			noofmonths = request.getParameter("noofmonths");
		}
		if (request.getParameter("arrearfalg") != "") {
			arrearflag = request.getParameter("arrearflag");
		}

		if (request.getParameter("editid") != null) {
			editid = request.getParameter("editid");
		}
		if (request.getParameter("duputationflag") != null) {
			duputationflag = request.getParameter("duputationflag");
		}
		HttpSession session = request.getSession(true);
		logInfo = (LoginInfo) session.getAttribute("user");
		String userName = logInfo.getUserName();
		String ipAddress = getIPAddress(request);
		pfid = commonUtil.trailingZeros(pfid.toCharArray());
		if (request.getParameter("pensionoption") != null) {
			pensionoption = request.getParameter("pensionoption").toString();
		}
		finReportService.editTransactionData(cpfAccno, monthYear, emoluments,
				vpf, principle, interest, contri, advance, loan, aailoan, pfid,
				region, airportcode, userName, ipAddress, from7narration,
				pcheldamt, noofmonths, arrearflag, duputationflag,
				pensionoption);

		int insertedRec = finReportService.preProcessAdjOB(pfid);

		log.info("deleteTransactionData=============Current Date========="
				+ commonUtil.getCurrentDate("dd-MMM-yyyy") + "insertedRec"
				+ insertedRec);
		String reportType = "Html";
		String yearID = "NO-SELECT";
		// region="NO-SELECT";
		String pfidStrip = "1 - 1";
		String page = "PensionContributionScreen";
		String mappingFlag = "true";
		String params = "&frm_region=" + region + "&frm_airportcode="
				+ airportcode + "&frm_year=" + yearID + "&frm_reportType="
				+ reportType + "&empserialNO=" + pfid + "&frm_pfids="
				+ pfidStrip + "&page=" + page + "&mappingFlag=" + mappingFlag;

		String url = "./reportservlet?method=getReportPenContr" + params;
		log.info("url is " + url);

		response.setContentType("text/html");
		PrintWriter out;
		out = response.getWriter();
		out.write(editid);
	}

	public void getContribution(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String monthYear = "", pfid = "", emoluments = "", dob = "", penionOption = "", contributionbox = "";
		if (request.getParameter("pfid") != null) {
			pfid = commonUtil.getSearchPFID1(request.getParameter("pfid")
					.toString());
		}
		if (request.getParameter("monthyear") != null) {
			monthYear = request.getParameter("monthyear");
		}
		if (request.getParameter("emoluments") != null) {
			emoluments = request.getParameter("emoluments");
		}
		if (request.getParameter("dob") != null) {
			dob = request.getParameter("dob");
		}
		if (request.getParameter("wetheroption") != null) {
			penionOption = request.getParameter("wetheroption");
		}
		if (request.getParameter("pfid") != null) {
			pfid = request.getParameter("pfid");
		}
		if (request.getParameter("contributionbox") != null) {
			contributionbox = request.getParameter("contributionbox");
		}
		double pensionContribution = finReportService.pensionCalculation(
				monthYear, emoluments, penionOption, "");
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		String designation = "";
		sb.append("<ServiceType>");
		sb.append("<pensionContribution>");
		sb.append(Math.round(pensionContribution));
		sb.append("</pensionContribution>");
		sb.append("<contributionbox>");
		sb.append(contributionbox);
		sb.append("</contributionbox>");
		sb.append("</ServiceType>");

		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}

	public ActionForward PCSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
			sortingOrder = "pensionno";
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
		log.info("airportCode " + airportCode);
		ArrayList form8List = new ArrayList();
		String frmMonth = "", fromYear = "", toYear = "";
		if (!month.equals("00")) {
			try {
				frmMonth = commonUtil.converDBToAppFormat(month, "MM", "MMM");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
		}
		if (month.equals("00")) {
			fromYear = "01-Apr-" + year;
			toYear = "01-Mar-" + (Integer.parseInt(year) + 1);
		} else if (!month.equals("NO-SELECT")) {
			fromYear = "01-" + frmMonth + "-" + year;
			toYear = fromYear;
		}
		String regionString = "", airportcodeString = "";
		if (!region.equals("NO-SELECT")) {
			regionString = region;
		} else {
			regionString = "";
		}
		if (!airportCode.equals("NO-SELECT")) {
			airportcodeString = airportCode;
		} else {
			airportcodeString = "";
		}
		form8List = finReportService.summaryPCReport(fromYear, toYear,
				sortingOrder, regionString, airportcodeString, empflag,
				empName, pensionno);

		log.info("==========Form-8=====" + form8List.size() + "Region" + region
				+ "year" + year);

		String shnYear = "";
		shnYear = fromYear + " To " + toYear;

		if (!region.equals("NO-SELECT")) {
			regionString = region;
		} else {
			regionString = "All Regions";
		}
		request.setAttribute("region", region);
		request.setAttribute("form8List", form8List);
		request.setAttribute("dspYear", shnYear);
		request.setAttribute("reportType", reportType);
		return mapping.findForward("ndpcsummaryreport");
	}

	public ActionForward TransferInOutReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String region = "", year = "", month = "";
		String reportType = "", sortingOrder = "", pfidString = "", lastmonthFlag = "", lastmonthYear = "", frmName = "";
		ArrayList list = new ArrayList();
		String airportCode = "", bulkPrintFlag = "";
		String frmSelectedDts = "", toYear = "";
		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("frmAirportCode") != null) {
			airportCode = request.getParameter("frmAirportCode");
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

		if (request.getParameter("frm_region") != null
				&& request.getParameter("frm_region") != "") {
			region = request.getParameter("frm_region");
		}

		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfacno";
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

		if (request.getParameter("transMonthYear") != null) {
			lastmonthYear = request.getParameter("transMonthYear");
		}

		if (request.getParameter("frm_ltstmonthflag") != null) {
			lastmonthFlag = request.getParameter("frm_ltstmonthflag");
		}
		if (request.getParameter("frm_ToYear") != null) {
			toYear = request.getParameter("frm_ToYear");
		}

		try {
			frmSelectedDts = getFromToDates(year, toYear, month, month);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = finReportService.TransferInOutPrinting(pfidString, region,
				frmSelectedDts, empflag, empName, sortingOrder, pensionno,
				lastmonthFlag, frmSelectedDts, airportCode);

		if (toYear.equals("")) {
			toYear = String.valueOf(Integer.parseInt(year) + 1);
		}
		if (!year.equals("NO-SELECT")) {
			if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 3) {
				year = "01-" + month + "-" + toYear;

			} else {
				year = "01-" + month + "-" + year;
			}

		}

		request.setAttribute("empinfo", list);
		request.setAttribute("reportType", reportType);
		request.setAttribute("date", year);
		String regionString = "", stationString = "";
		if (region.equals("NO-SELECT")) {
			regionString = "";
		} else {
			regionString = region;
		}
		if (airportCode.equals("NO-SELECT")) {
			stationString = "";
		} else {
			if (region.equals("CHQIAD")) {
				stationString = airportCode;
			} else {
				stationString = "";
			}

		}
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", regionString);
		request.setAttribute("airportCode", stationString);

		return mapping.findForward("transferinoutreport");
	}

	public ActionForward loadStatmentPCWagesInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Map monthMap = new LinkedHashMap();
		Iterator monthIterator = null;
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		request.setAttribute("monthIterator", monthIterator);
		LoginInfo logInfo = new LoginInfo();
		HttpSession session = request.getSession(true);
		logInfo = (LoginInfo) session.getAttribute("user");
		String userName = logInfo.getUserName();
		String userType = logInfo.getUserType();

		request.setAttribute("frmUserName", userName);
		session.setAttribute("usertype", userType);

		loadPrimarilyInfo(request, "loadstatmentpcwagesInput");
		return mapping.findForward("loadstatmentpcwagesInput");

	}

	// New Method
	public ActionForward statmentPCWagesReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String reportType = "", sortingOrder = "", pensionno = "", dateOfAnnunation = "", pfidString = "", airportCode = "", month = "", region = "", year = "";
		ArrayList list = new ArrayList();

		if (request.getParameter("frm_reportType") != null) {
			reportType = request.getParameter("frm_reportType");
		}
		if (request.getParameter("frm_monthID") != null) {
			month = request.getParameter("frm_monthID");
			if (!month.equals("NO-SELECT")) {
				try {
					month = commonUtil.converDBToAppFormat(request
							.getParameter("frm_monthID"), "MM", "MMM");
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}

		if (request.getParameter("frmAirportCode") != null) {
			airportCode = request.getParameter("frmAirportCode");
		}

		if (request.getParameter("frm_region") != null
				&& request.getParameter("frm_region") != "") {
			region = request.getParameter("frm_region");
		} else {
			region = "NO-SELECT";
		}
		if (request.getParameter("dateOfAnnuation") != null
				&& request.getParameter("dateOfAnnuation") != "") {
			dateOfAnnunation = request.getParameter("dateOfAnnuation");
		}

		if (request.getParameter("frm_pfids") != null
				&& request.getParameter("frm_pfids") != "") {
			pfidString = request.getParameter("frm_pfids");
		} else {
			pfidString = "";
		}
		if (request.getParameter("sortingOrder") != null) {
			sortingOrder = request.getParameter("sortingOrder");
		} else {
			sortingOrder = "cpfacno";
		}

		if (request.getParameter("frm_pensionno") != null) {
			pensionno = request.getParameter("frm_pensionno");
		}
		if (!dateOfAnnunation.equals("")) {
			String dates[] = dateOfAnnunation.split("-");
			month = "NO-SELECT";
			year = "NO-SELECT";
		}
		log.info("===statmentPCWagesReport=====" + region + "year" + year
				+ "Month" + month + "pfidString" + pfidString + "pensionno"
				+ pensionno);

		list = finReportService.getStatementWagesPCReport(pfidString, region,
				month, year, sortingOrder, pensionno, airportCode);

		request.setAttribute("cardList", list);

		request.setAttribute("reportType", reportType);
		String regionString = "", stationString = "";
		if (region.equals("NO-SELECT")) {
			regionString = "";
		} else {
			regionString = region;
		}
		if (airportCode.equals("NO-SELECT")) {
			stationString = "";
		} else {
			if (region.equals("CHQIAD")) {
				stationString = airportCode;
			} else {
				stationString = "";
			}

		}
		request.setAttribute("reportType", reportType);
		request.setAttribute("region", regionString);
		request.setAttribute("airportCode", stationString);

		return mapping.findForward("statementpcwagesreport");

	}

	public void editFinalDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String finalsettlementDate = "", pfid = "";
		String editid = "";
		if (request.getParameter("pensionNo") != null) {
			pfid = commonUtil.getSearchPFID1(request.getParameter("pensionNo")
					.toString());
		}

		if (request.getParameter("finalsettlementDate") != null) {
			finalsettlementDate = request.getParameter("finalsettlementDate");
		}

		pfid = commonUtil.trailingZeros(pfid.toCharArray());
		finReportService.editFinalDate(finalsettlementDate, pfid);

		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		sb.append("<ServiceType>");
		sb.append("<pfid>");
		sb.append(pfid.trim());
		sb.append("</pfid>");
		sb.append("</ServiceType>");

		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());

	}
	public ActionForward getEmolumentslog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		   log.info("ReportAction::getEmolumentslog()");
		String pfId = request.getParameter("pfId").toString().trim();
		String airportcode = request.getParameter("airportcode").toString()
				.trim();
		ArrayList adjLoginfo = new ArrayList();
		pfId = commonUtil.getSearchPFID1(pfId.toString().trim());
		ArrayList emolumentsloginfo = new ArrayList();
		ArrayList deleteloginfo = new ArrayList();

		try {
			emolumentsloginfo = ps.getEmolumentslog(pfId, airportcode);
			deleteloginfo = ps.getDeletelog(pfId, airportcode);
			adjLoginfo = ps.getAdjlog(pfId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("adjLoginfo", adjLoginfo);
		request.setAttribute("deleteloginfo", deleteloginfo);
		request.setAttribute("emolumentsloginfo", emolumentsloginfo);
		log.info("Mapped LIst " + emolumentsloginfo.size());
	               return mapping.findForward("pfcardlog");

	}
	
}
