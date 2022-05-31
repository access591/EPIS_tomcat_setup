package com.epis.action.rpfc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.SignatureMappingBean;
import com.epis.dao.CommonDAO;
import com.epis.dao.rpfc.PensionDAO;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.services.rpfc.FinancialService;
import com.epis.services.rpfc.PensionService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;

public class RPFCLookupAction extends CommonRPFCAction {
	CommonUtil commonUtil = new CommonUtil();
	
	CommonDAO commonDAO = new CommonDAO();
	
	Log log = new Log(RPFCLookupAction.class);
	
	FinancialService financeService = new FinancialService();
	
	FinancialReportService finReportService = new FinancialReportService();
	
	public void getPFCardAirports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "", stationName = "";
		region = request.getParameter("region");
		ArrayList ServiceType = null;
		
		if (!stationName.equals("")) {
			ServiceType = new ArrayList();
			EmpMasterBean masterBean = new EmpMasterBean();
			masterBean.setStation(stationName);
			ServiceType.add(masterBean);
		} else {
			ServiceType = commonDAO.getAirportsByBulkPFIDSTbl(region);
		}
		log.info("airport list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "";
			EmpMasterBean bean = (EmpMasterBean) ServiceType.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			if (bean.getStation() != null)
				name = bean.getStation().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());
		
	}
	
	public void getPFIDListWthoutTrnFlag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String region = "", monthYear = "", airportcode = "", transferFlag = "", lastmonthFlag = "", month = "", fromYear = "", toYear = "", year = "";
		int pereachpage = 0;
		ArrayList rangeList = new ArrayList();
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_ltstmonthflag") != null) {
			lastmonthFlag = request.getParameter("frm_ltstmonthflag");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}
		String[] sltedYear = year.split("-");
		if (sltedYear.length == 2) {
			fromYear = sltedYear[0];
			toYear = sltedYear[1];
			String prefixFrmYear = fromYear.substring(0, 2);
			if (prefixFrmYear.equals("19")
					&& Integer.parseInt(fromYear.substring(0, 2))
					- Integer.parseInt(toYear) == 1) {
				toYear = prefixFrmYear + toYear;
			} else {
				toYear = "20" + toYear;
			}
			log.info("==getPFIDListWthoutTrnFlag=region=" + region
					+ "airportcode===" + airportcode + "lastmonthFlag"
					+ lastmonthFlag + "month" + month + "year" + year);
			log.info("==getPFIDListWthoutTrnFlag=fromYear=" + fromYear
					+ "toYear===" + toYear);
			
		} else {
			fromYear = sltedYear[0];
			toYear = Integer.toString(Integer.parseInt(fromYear) + 1);
		}
		if (!month.equals("NO-SELECT")) {
			if (Integer.parseInt(month) >= 4 && Integer.parseInt(month) <= 12) {
				monthYear = "-" + month + "-" + fromYear;
			} else if (Integer.parseInt(month) >= 1
					&& Integer.parseInt(month) <= 3) {
				monthYear = "-" + month + "-" + toYear;
			}
		} else {
			monthYear = commonDAO.getLatestTrnsDate(region);
		}
		log.info("==getPFIDListWthoutTrnFlag=fromYear=" + fromYear
				+ "toYear===" + toYear);
		ArrayList ServiceType = null;
		ResourceBundle bundle = ResourceBundle
		.getBundle("com.epis.resource.ApplicationResources");
		int totalSize = 0;
		
		if (bundle.getString("common.pension.pagesize") != null) {
			totalSize = Integer.parseInt(bundle
					.getString("common.pension.pagesize"));
		} else {
			totalSize = 100;
		}
		
		rangeList = commonDAO.navgtionPFIDsList(region, airportcode, Integer
				.toString(totalSize), monthYear, lastmonthFlag);
		
		log.info("getPFIDListWthoutTrnFlag::Total List Size "
				+ rangeList.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		String rangeString = "";
		for (int i = 0; i < rangeList.size(); i++) {
			String name = "";
			rangeString = (String) rangeList.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			sb.append(rangeString);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("<PARAMSTR>");
		sb.append("<PARAM1>");
		sb.append(monthYear);
		sb.append("</PARAM1>");
		sb.append("</PARAMSTR>");
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out;
		try {
			out = response.getWriter();
			log.info(sb.toString());
			out.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		
	}
	
	public void getPersonalTblAirports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "", stationName = "";
		region = request.getParameter("region");
		ArrayList ServiceType = null;
		
		if (!stationName.equals("")) {
			ServiceType = new ArrayList();
			EmpMasterBean masterBean = new EmpMasterBean();
			masterBean.setStation(stationName);
			ServiceType.add(masterBean);
		} else {
			
			ServiceType = commonDAO.getAirportsByPersonalTbl(region);
		}
		log.info("airport list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "";
			EmpMasterBean bean = (EmpMasterBean) ServiceType.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			if (bean.getStation() != null)
				name = bean.getStation().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
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
	
	public void getPFIDBulkPrintingList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "", monthYear = "", airportcode = "", transferFlag = "", lastmonthFlag = "", month = "", fromYear = "", toYear = "", year = "";
		int pereachpage = 0;
		int totalSize = 0;
		ArrayList rangeList = new ArrayList();
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		
		if (request.getParameter("frm_ltstmonthflag") != null) {
			lastmonthFlag = request.getParameter("frm_ltstmonthflag");
		}
		if (request.getParameter("frm_month") != null) {
			month = request.getParameter("frm_month");
		}
		if (request.getParameter("frm_year") != null) {
			year = request.getParameter("frm_year");
		}else{
			year="2008";
		}
		if (request.getParameter("frm_pagesize") != null) {
			totalSize = Integer.parseInt(request.getParameter("frm_pagesize"));
		} else {
			totalSize = 100;
		}
		if(year.indexOf("-")!=-1){
			String[] sltedYear = year.split("-");
			if (sltedYear.length == 2) {
				fromYear = sltedYear[0];
				toYear=Integer.toString(Integer.parseInt(fromYear)+1);
			}
		}else{
			fromYear=year;
			toYear=Integer.toString(Integer.parseInt(fromYear)+1);
		}
		log.info("==getPFIDListWthoutTrnFlag=fromYear=" + fromYear
				+ "toYear===" + toYear);
		String finyear=fromYear+"-"+toYear;
		ArrayList ServiceType = null;
		ResourceBundle bundle = ResourceBundle
		.getBundle("com.epis.resource.ApplicationResources");
		rangeList = commonDAO.bulkNavgtionPFIDsList(region, airportcode,
				Integer.toString(totalSize), monthYear, lastmonthFlag,finyear);
		
		log.info("getPFIDListWthoutTrnFlag::Total List Size "
				+ rangeList.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		String rangeString = "";
		for (int i = 0; i < rangeList.size(); i++) {
			String name = "";
			rangeString = (String) rangeList.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			sb.append(rangeString);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("<PARAMSTR>");
		sb.append("<PARAM1>");
		sb.append(monthYear);
		sb.append("</PARAM1>");
		sb.append("</PARAMSTR>");
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());
	}
	public void getAirports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PensionService ps1 = new PensionService();
		ArrayList ServiceType = null;
		String region=(String)request.getParameter("region");
		ServiceType = ps1.getAirports(region);
		log.info("airport list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "", code = "";
			EmpMasterBean bean = (EmpMasterBean) ServiceType.get(i);

			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			if (bean.getStation() != null)
				name = bean.getStation().replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("<airPortCode>");
			if (bean.getUnitCode() != null)
				code = bean.getUnitCode().replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;");
			sb.append(code);
			sb.append("</airPortCode>");
			sb.append("<selected>");
			sb.append("true");
			sb.append("</selected>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());

	}
	public void getPFIDList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "", airportcode = "", transferFlag = "";
		int pereachpage = 0;
		if (request.getParameter("frm_region") != null) {
			region = request.getParameter("frm_region");
		}
		if (request.getParameter("frm_airportcode") != null) {
			airportcode = request.getParameter("frm_airportcode");
		}
		if (request.getParameter("frm_transflag") != null) {
			transferFlag = request.getParameter("frm_transflag");
		}
		log.info("==getPFIDList==" + region + "airportcode===" + airportcode
				+ "transferFlag===" + transferFlag);
		ArrayList ServiceType = null;
		ArrayList rangeList = new ArrayList();
		ResourceBundle bundle = ResourceBundle
		.getBundle("com.epis.resource.ApplicationResources");
		int totalSize = 0;
		
		if (bundle.getString("common.pension.pagesize") != null) {
			totalSize = Integer.parseInt(bundle
					.getString("common.pension.pagesize"));
		} else {
			totalSize = 100;
		}
		rangeList = commonDAO.getPFIDsNaviagtionList(region, airportcode,
				transferFlag, Integer.toString(totalSize));
		log.info("pereachpage " + rangeList.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		String rangeString = "";
		for (int i = 0; i < rangeList.size(); i++) {
			
			rangeString = (String) rangeList.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			sb.append(rangeString);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());
	}
	
	public void getFinanceTblAirports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String region = "";
		region = request.getParameter("region");
		CommonUtil commonUtil = new CommonUtil();
		log.info("=============getAirports================"
				+ request.getParameter("region"));
		ArrayList ServiceType = null;
		ServiceType = commonUtil.getAirportsByFinanceTbl(region);
		log.info("airport list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "";
			EmpMasterBean bean = (EmpMasterBean) ServiceType.get(i);
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			if (bean.getStation() != null)
				name = bean.getStation().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info(sb.toString());
		out.write(sb.toString());
	}
	
	public void getUnitTblAirports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PensionDAO ps1 = new PensionDAO();
		ArrayList ServiceType = null;
		String region = (String) request.getParameter("region");
		ServiceType = ps1.getAirports(region);
		log.info("airport list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "", code = "";
			EmpMasterBean bean = (EmpMasterBean) ServiceType.get(i);
			
			sb.append("<ServiceType>");
			sb.append("<airPortName>");
			if (bean.getStation() != null)
				name = bean.getStation().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("<airPortCode>");
			if (bean.getUnitCode() != null)
				code = bean.getUnitCode().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(code);
			sb.append("</airPortCode>");
			sb.append("<selected>");
			sb.append("true");
			sb.append("</selected>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		out.write(sb.toString());
	}
	public void  loadModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		 
		 
		ArrayList ServiceType = null; 
			ServiceType = commonDAO.loadModules();
	 
		log.info("Module list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "",code="";
			SignatureMappingBean bean = (SignatureMappingBean) ServiceType.get(i);
			sb.append("<ServiceType>");
			sb.append("<moduleName>");
			if (bean.getModuleName() != null)
				name = bean.getModuleName().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</moduleName>");
			sb.append("<moduleCode>");
			if (bean.getModuleCode() != null)
				code = bean.getModuleCode().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(code);
			sb.append("</moduleCode>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
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
	
	//New method
	public void  loadScreenNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		 
		 String moduleCode="";
		ArrayList ServiceType = null; 
		if(request.getParameter("modulecode")!=null){
			moduleCode=request.getParameter("modulecode").trim();
		}
			ServiceType = commonDAO.loadScreenNames(moduleCode);
			
		log.info("ScreenNames list " + ServiceType.size());
		StringBuffer sb = new StringBuffer();
		sb.append("<ServiceTypes>");
		
		for (int i = 0; ServiceType != null && i < ServiceType.size(); i++) {
			String name = "",code="";
			SignatureMappingBean bean = (SignatureMappingBean) ServiceType.get(i);
			sb.append("<ServiceType>");
			sb.append("<screenName>");
			if (bean.getScreenName() != null)
				name = bean.getScreenName().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(name);
			sb.append("</screenName>");		
			sb.append("<screenCode>");
			if (bean.getScreenCode() != null)
				code = bean.getScreenCode().replaceAll("<", "&lt;").replaceAll(
						">", "&gt;");
			sb.append(code);
			sb.append("</screenCode>");	
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
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
}
