package com.epis.action.advances;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.epis.bean.admin.UserBean;
import com.epis.bean.advances.AdvanceBasicReportBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.dao.CommonDAO;
import com.epis.dao.advances.CPFPFWTransInfo;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;
import com.epis.services.advances.CPFPTWAdvanceService;

public class AdvanceReportAction extends CommonAction{
	Log log = new Log(AdvanceReportAction.class);
	CommonUtil commonUtil=new CommonUtil();
	CPFPTWAdvanceService ptwAdvanceService=null;
	CommonDAO commonDAO=new CommonDAO();
	CPFPFWTransInfo cpfpfwtransInfo = new CPFPFWTransInfo();
	public ActionForward loadAdvanceInputForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("AdvanceReportAction::loadAdvanceInputForm");
		String path="",accessFrom="";
		ArrayList yearList=new ArrayList();
		Map monthMap=new  LinkedHashMap();
		HashMap regionHashmap=new HashMap();
		Iterator monthIterator = null;
		yearList=commonUtil.getYearList();
		monthMap = commonUtil.getMonthsList();
		 regionHashmap = commonUtil.getRegion();
         
        Set monthset = monthMap.entrySet();
        monthIterator = monthset.iterator();
		if(request.getParameter("access_from")!=null){
			accessFrom="P";
		}else{
			accessFrom="N";
		}
		 request.setAttribute("accessFrom", accessFrom);
        request.setAttribute("monthIterator", monthIterator);
        request.setAttribute("yearList", yearList);
        //request.setAttribute("regionHashmap", regionHashmap);
        this.getRegionByProfile(request);
        if(request.getParameter("flag").equals("SanctionOrder")){
        	path="advreportparams";
        }else if(request.getParameter("flag").equals("FinalPaymentRegister")){
        	path="finalPaymentRegister";
        }else if(request.getParameter("flag").equals("MISReport")){
        	path="MISReportParam";
        }else if(request.getParameter("flag").equals("pfwsmaryreport")){
        	path="pfwsmaryreportParam";
        }else if(request.getParameter("flag").equals("ArrearSanctionOrder")){
        	path="AreearReportParam";
        }else if(request.getParameter("flag").equals("AdvancesReport")){
        	path="advanceReportParam";
        }else if(request.getParameter("flag").equals("CPFRejected")){
        	path="rejectedCPFReportParam";
        }else{
        	path="summarizedadvanceReportParam";
        }
        
		return mapping.findForward(path);
	}
	public ActionForward consSanctionOrder(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList sanctionOrderList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		int nextYear=0;
		int previousYear=0,currentMonth=0;
		List detailList=null;
		String station="",region="",month="",year="",currentYear1="",currentYear="",previousYear1="",previousYear2="",seperationreason="",trust="",reportType="",path="",fromDate="",toDate="",frmName="";
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();
		
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}
		/*if(request.getParameter("frm_month")!=null){
			month=request.getParameter("frm_month");
		}
		if(request.getParameter("frm_year")!=null){
			year=request.getParameter("frm_year");
		}*/
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}
		if(request.getParameter("frm_sepreason")!=null){
			seperationreason=request.getParameter("frm_sepreason");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}
		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
		
		if(request.getParameter("frm_name")!=null){
			frmName=request.getParameter("frm_name");
		}else{
			frmName="NON-ARREAR";			
		}
		
		log.info("frmName in Action-------------"+frmName);
		
		sanctionOrderList=ptwAdvanceService.consSanctionOrder(region,fromDate,toDate,seperationreason,station,trust,frmName);
		log.info("Size"+sanctionOrderList.size());
		
		reportbean=(AdvanceBasicReportBean)sanctionOrderList.get(0);
		detailList=(List)reportbean.getSanctionList();
		
		log.info("-------detailList size-------"+detailList.size());
		currentMonth=Integer.parseInt(commonUtil.getCurrentDate("MM"));
		currentYear=commonUtil.getCurrentDate("yyyy").substring(2,4);	
		currentYear1=commonUtil.getCurrentDate("yyyy").substring(0,4);	
			
		nextYear=Integer.parseInt(currentYear)+1;	
		previousYear=Integer.parseInt(currentYear1)-1;
		previousYear2=Integer.toString(previousYear);
		if(previousYear<=9)
			previousYear1=Integer.toString(previousYear);
			
		if(currentMonth>=04)
			reportbean.setTransMnthYear(currentYear1+"-"+nextYear);
		else if(previousYear<=9)
			reportbean.setTransMnthYear(previousYear1+"-"+currentYear);
		else
			reportbean.setTransMnthYear(previousYear2+"-"+currentYear);	
		
		request.setAttribute("reportType",reportType);
		request.setAttribute("sanctionOrderBean",reportbean);
		AdvanceBasicReportBean basicBean=new AdvanceBasicReportBean();
		ArrayList nomineeList=new ArrayList();
		 if(reportbean.getSeperationreason().equals("Death"))
			{
			for(int j=0;j<detailList.size();j++){
				basicBean = new AdvanceBasicReportBean();
				basicBean=(AdvanceBasicReportBean)detailList.get(j);
				
				nomineeList=(ArrayList)basicBean.getNomineeList();
								
				if(nomineeList.size()>0){
					request.setAttribute("nomineeList",nomineeList);				
				}	
								
			}
			}
		if(detailList.size()!=0){
			request.setAttribute("detailList",detailList);
		}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		//request.setAttribute("sanctionOrderList",sanctionOrderList);	
		 if(region.equals("CHQNAD")){
				if(seperationreason.equals("Death"))
					path="viewCHQNADDeathSanctionOrder";
				else if(seperationreason.equals("Retirement"))
					path="viewCHQNADSanctionOrder";
				else if(seperationreason.equals("Resignation"))
					path="viewCHQNADResigSanctionOrder";
				else
					path="viewSanctionOrder";
					
			}else{
				path="viewSanctionOrder";
			}
		 return mapping.findForward(path);
	}
	//	By Radha On 27-Jul-2011  for Displaying Designation and Signatures dynamically
	public ActionForward advancesReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList advanceReportList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		List detailList=null;
		String region="",toDate="",fromDate="",advanceType="",purposeType="",path="",reportType="",advType="",avdvType1="",avdvType2="",trust="",station="";
		String repFromDate="",repToDate="",imgagePath="",accessFrom="S" ;
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean(request);
		AdvanceBasicReportBean reportbean1=new AdvanceBasicReportBean();
		UserBean userbean = new UserBean();
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}		
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}		
		if(request.getParameter("frm_advancetype")!=null){
			advanceType=request.getParameter("frm_advancetype");
		}
		if(request.getParameter("frm_purposeType")!=null){
			purposeType=request.getParameter("frm_purposeType");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		if(request.getParameter("access_from")!=null){
			accessFrom=request.getParameter("access_from");
		}else{
			accessFrom="N";
		}
		
		log.info("-----fromDate------"+fromDate);
		log.info("-----toDate------"+toDate+"accessFrom"+accessFrom+"Access frm reques"+request.getParameter("access_from")+"reportbean.getLoginUserId()"+reportbean.getLoginUserId());		
		
			imgagePath = getServlet().getServletContext().getRealPath("/")+ "/uploads/dbf/";
			advanceReportList=ptwAdvanceService.advancesReport(region,fromDate,toDate,advanceType,purposeType,trust,station,reportbean.getLoginProfile(),reportbean.getLoginUnitCode(),reportbean.getLoginUserId(),reportbean.getLoginUserAccType(),accessFrom);
			String approvalids="";
			String[] approvalidsList=null;
			if(accessFrom.equals("N")){
				approvalids=reportbean.getLoginUserId()+",";
			}else{
				approvalids=ptwAdvanceService.getAdvanceApprovedDetails(fromDate,toDate,reportbean.getLoginUnitCode(),region,reportbean.getLoginProfile(),reportbean.getLoginUserAccType());
			}
			approvalidsList=approvalids.split(",");
			List approvedListSign=new ArrayList();
			if (!approvalidsList.equals("")){
				for (int j=0;j<approvalidsList.length ;j++){
					userbean = cpfpfwtransInfo.readUserSignaturesforreports(imgagePath,approvalidsList[j]);
					approvedListSign.add(userbean);
				}
			}
	
		

		
		
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
			}
		log.info("Size"+advanceReportList.size());
		
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
		}
									
		request.setAttribute("reportType",reportType);
		
		if(advanceType.equals("PFW")){
			reportbean.setScreenTitle("Part Final Withdrawal (PFW) Reports");
			path="viewPFWAdvanceReport";
		}else if(advanceType.equals("CPF")){
			reportbean.setScreenTitle("CPF Refundable Advances");
			path="viewCPFAdvanceReport";
		}else
			reportbean.setScreenTitle("Advances Or PFW Report");
		
		request.setAttribute("reportbean",reportbean);
		request.setAttribute("userbean",approvedListSign);
		
		
		if(advanceReportList.size()>0){
		reportbean1 =(AdvanceBasicReportBean)advanceReportList.get(0);		
		
		/*if(fromDate.equals("Select One")){
			reportbean1.setTransMnthYear("2009-10");
		}
		
		if(!toDate.equals("0"))
			reportbean1.setMonth(toDate);	
		else
			reportbean1.setMonth("");*/
		
		if(!fromDate.equals("")){
			  repFromDate=commonUtil.converDBToAppFormat(fromDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			  reportbean1.setFromYear(repFromDate);
			
		}
		
		if(!toDate.equals("")){
			repToDate=commonUtil.converDBToAppFormat(toDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			 reportbean1.setToYear(repToDate);
		}
		
		
		if(region.equals("NO-SELECT"))
			reportbean1.setRegion("");
	
		request.setAttribute("reportbean1",reportbean1);
		 
		request.setAttribute("FromDate",repFromDate);
		request.setAttribute("ToDate",repToDate);
		

		if((!fromDate.equals("")) && (!toDate.equals(""))){
			
			if(fromDate.substring(6,fromDate.length()).equals(toDate.substring(6,toDate.length()))){
				 reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length()));
			}else{ 
				reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length())+"-"+toDate.substring(8,toDate.length()));
			}
		}
		
		 
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return mapping.findForward(path);
	}
	//By Radha on 27-Dec-2011 for Summarized Advances Report
	public ActionForward summarizedAdvancesReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList advanceReportList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		List detailList=null;
		String region="",toDate="",fromDate="",advanceType="",purposeType="",path="",reportType="",advType="",avdvType1="",avdvType2="",trust="",station="";
		String repFromDate="",repToDate="",imgagePath="",advanceReportType="";
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean(request);
		AdvanceBasicReportBean reportbean1=new AdvanceBasicReportBean();
		UserBean userbean = new UserBean();
		log.info("-----In Action--summarizedAdvancesReport----");
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}		
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}		
		if(request.getParameter("frm_advancetype")!=null){
			advanceType=request.getParameter("frm_advancetype");
		}
		if(request.getParameter("frm_purposeType")!=null){
			purposeType=request.getParameter("frm_purposeType");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		log.info("-----region------"+region);
		log.info("-----fromDate------"+fromDate);
		log.info("-----toDate------"+toDate);		
		
		if(region.equals("NO-SELECT")){
			request.setAttribute("displayType","regionwise");	
		}else{
			request.setAttribute("displayType","notregionwise");	
		}
		imgagePath = getServlet().getServletContext().getRealPath("/")+ "/uploads/dbf/";
		advanceReportList=ptwAdvanceService.summarizedAdvancesReport(region,fromDate,toDate,advanceType,purposeType,trust,station,reportbean.getLoginProfile(),reportbean.getLoginUnitCode(),reportbean.getLoginUserId(),advanceReportType);
		userbean = cpfpfwtransInfo.readUserSignaturesforreports(imgagePath,reportbean.getLoginUserId());
		
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
			 
			}
		log.info("Size"+advanceReportList.size());
		 						
		request.setAttribute("reportType",reportType);
		
		if(advanceType.equals("PFW")){
			reportbean.setScreenTitle("Part Final Withdrawal (PFW) Reports");
			path="viewPFWAdvanceReport";
		}else if(advanceType.equals("CPF")){
			reportbean.setScreenTitle("CPF Refundable Advances");
			path="viewSummarizedCPFAdvanceReport";
		}else
			reportbean.setScreenTitle("Advances Or PFW Report");
		
		request.setAttribute("reportbean",reportbean);
		request.setAttribute("userbean",userbean);
		

		if(advanceReportList.size()>0){
		reportbean1 =(AdvanceBasicReportBean)advanceReportList.get(0);		
		
		/*if(fromDate.equals("Select One")){
			reportbean1.setTransMnthYear("2009-10");
		}
		
		if(!toDate.equals("0"))
			reportbean1.setMonth(toDate);	
		else
			reportbean1.setMonth("");*/
		
		if(!fromDate.equals("")){
			  repFromDate=commonUtil.converDBToAppFormat(fromDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			  reportbean1.setFromYear(repFromDate);
			
		}
		
		if(!toDate.equals("")){
			repToDate=commonUtil.converDBToAppFormat(toDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			 reportbean1.setToYear(repToDate);
		}
		
		
		if(region.equals("NO-SELECT"))
			reportbean1.setRegion("");
	
		request.setAttribute("reportbean1",reportbean1);
		 
		request.setAttribute("FromDate",repFromDate);
		request.setAttribute("ToDate",repToDate);
		

		if((!fromDate.equals("")) && (!toDate.equals(""))){
			
			if(fromDate.substring(6,fromDate.length()).equals(toDate.substring(6,toDate.length()))){
				 reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length()));
			}else{ 
				reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length())+"-"+toDate.substring(8,toDate.length()));
			}
		}
		
		 
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		return mapping.findForward(path);
	}
	
	public ActionForward finalPaymentRegister(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList sanctionOrderList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		List detailList=null;
		String region="",toDate="",fromDate="",seperationreason="",reportType="",trust="",station="",arrearType="";
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();
		
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}
		if(request.getParameter("frm_sepreason")!=null){
			seperationreason=request.getParameter("frm_sepreason");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}
		
		if(request.getParameter("arrear_type")!=null){
			arrearType=request.getParameter("arrear_type");
		}
		
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
        log.info("=========trust======="+trust);
        
        if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
        
		sanctionOrderList=ptwAdvanceService.finalPaymentRegister(region,fromDate,toDate,seperationreason,trust,station,arrearType);
		if(sanctionOrderList.size()!=0){
			reportbean=(AdvanceBasicReportBean)sanctionOrderList.get(0);
			detailList=(List)reportbean.getSanctionList();
			
			request.setAttribute("reportType",reportType);
			request.setAttribute("sanctionOrderBean",reportbean);
			request.setAttribute("detailList",detailList);			
		}
		log.info("Size"+sanctionOrderList.size());
		
		reportbean=(AdvanceBasicReportBean)sanctionOrderList.get(0);
		detailList=(List)reportbean.getSanctionList();
		
		request.setAttribute("reportType",reportType);
		request.setAttribute("sanctionOrderBean",reportbean);
		request.setAttribute("detailList",detailList);
		
		String frmDt="",toDt="";
		  
		
	 
		frmDt = commonUtil.converDBToAppFormat(fromDate, "dd/MM/yy", "MMM-yyyy");
		toDt = commonUtil.converDBToAppFormat(toDate, "dd/MM/yy", "MMM-yyyy");
			 
		
		request.setAttribute("fromDate",frmDt);
		request.setAttribute("toDate",toDt);
		String arreartype="";
		if(arrearType.equals("NON-ARREAR")){
			arreartype="";
		}else{
			arreartype="Arrears";
		}
		request.setAttribute("arrearType",arreartype);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		//request.setAttribute("sanctionOrderList",sanctionOrderList);	
		return mapping.findForward("viewFinalPaymentRegister");
	}
	public ActionForward getAirports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		String region = "",stationName="";
		region = request.getParameter("region");
		ArrayList ServiceType = null;
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("station")!=null){
			stationName=(String)session.getAttribute("station");
		}
		if(!stationName.equals("")){
			ServiceType = new ArrayList();
			EmpMasterBean masterBean=new EmpMasterBean();
			masterBean.setStation(stationName);
			ServiceType.add(masterBean);
		}else{
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
				name = bean.getStation().replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
			sb.append(name);
			sb.append("</airPortName>");
			sb.append("</ServiceType>");
		}
		sb.append("</ServiceTypes>");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		log.info(sb.toString());
		out.write(sb.toString());
		
		return mapping.findForward("");

	
	}

	// New Method
	
	public ActionForward MISReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList advanceReportList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		List detailList=null;
		String region="",toDate="",fromDate="",fromYear="",fromMonth="",subPurposeType="",purposeType="",path="",reportType="",advType="",avdvType1="",avdvType2="",trust="",station="";
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean reportbean1=new AdvanceBasicReportBean();;		
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}		
		if(request.getParameter("frm_year")!=null){
			fromYear=request.getParameter("frm_year");
		}
		if(request.getParameter("frm_month")!=null){
			fromMonth=request.getParameter("frm_month");
		}		
		if(request.getParameter("frm_subpurposetype")!=null){
			subPurposeType=request.getParameter("frm_subpurposetype");
		}
		if(request.getParameter("frm_purposeType")!=null){
			purposeType=request.getParameter("frm_purposeType");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		
		
		if((!fromYear.equals("")) && (!fromMonth.equals("0") ) ){
			fromDate="01/"+fromMonth+"/"+fromYear;
			toDate="01/"+fromMonth+"/"+fromYear;
		}else{
			fromDate="01/4/"+fromYear;
			toDate="01/3/"+(Integer.parseInt(fromYear)+1);
		}
	
		
		advanceReportList=ptwAdvanceService.MISReport(region,fromDate,toDate,subPurposeType,purposeType,trust,station);
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
			}
		log.info("Size======="+advanceReportList.size());
		
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
		}
									
		request.setAttribute("reportType",reportType);
		
			
		request.setAttribute("reportbean",reportbean);
		

		if(advanceReportList.size()>0){
		reportbean1 =(AdvanceBasicReportBean)advanceReportList.get(0);		
		
		
		
		if((!fromYear.equals(""))){
			reportbean1.setFromYear(fromYear);
			reportbean1.setToYear(Integer.toString(Integer.parseInt(fromYear)+1).substring(2,4));
			
		}
		
				
		if(region.equals("NO-SELECT"))
			reportbean1.setRegion("");
	
		request.setAttribute("reportbean1",reportbean1);				
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		path="viewPFWMISReport";
		return mapping.findForward(path);
	}
	public ActionForward pfwSummaryReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList advanceReportList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		
		String region="",toDate="",fromDate="",path="",reportType="",station="";
		String repFromDate="",repToDate="";
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean reportbean1=new AdvanceBasicReportBean();;		
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}		
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}		


		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}

		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		
		
		log.info("-----fromDate------"+fromDate+"toDate"+toDate+"station"+station+"region"+region);
		if(!fromDate.equals("")){
			  repFromDate=commonUtil.converDBToAppFormat(fromDate,"dd/MM/yyyy", "dd-MMM-yyyy");
		}
		
		if(!toDate.equals("")){
			repToDate=commonUtil.converDBToAppFormat(toDate,"dd/MM/yyyy", "dd-MMM-yyyy");
		}
		
		
		advanceReportList=ptwAdvanceService.pfwSummaryReport(region,repFromDate,repToDate,station);
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
		}
		log.info("Size"+advanceReportList.size());
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
		}
		request.setAttribute("reportType",reportType); 
		request.setAttribute("reportbean",reportbean); 
		
		
		if(region.equals("NO-SELECT"))
			reportbean1.setRegion("");
	
		request.setAttribute("reportbean1",reportbean1);
		 
		request.setAttribute("FromDate",repFromDate);
		request.setAttribute("ToDate",repToDate);
		

		if((!fromDate.equals("")) && (!toDate.equals(""))){
			
			if(fromDate.substring(6,fromDate.length()).equals(toDate.substring(6,toDate.length()))){
				 reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length()));
			}else{ 
				reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length())+"-"+toDate.substring(8,toDate.length()));
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return mapping.findForward("viewPFWSummaryReport");
	}
 
	public ActionForward rejectedAdvancesReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList advanceReportList=new ArrayList();	
		ptwAdvanceService=new CPFPTWAdvanceService();
		
		List detailList=null;
		String region="",toDate="",fromDate="",advanceType="",purposeType="",path="",reportType="",advType="",avdvType1="",avdvType2="",trust="",station="";
		String repFromDate="",repToDate="",imgagePath="",accessFrom="S" ;
		AdvanceBasicReportBean reportbean = new AdvanceBasicReportBean(request);
		AdvanceBasicReportBean reportbean1=new AdvanceBasicReportBean();
		UserBean userbean = new UserBean();
		try{		
		if(request.getParameter("frm_region")!=null){
			region=request.getParameter("frm_region");
		}		
		if(request.getParameter("frm_fromDate")!=null){
			fromDate=request.getParameter("frm_fromDate");
		}
		if(request.getParameter("frm_toDate")!=null){
			toDate=request.getParameter("frm_toDate");
		}		
		if(request.getParameter("frm_advancetype")!=null){
			advanceType=request.getParameter("frm_advancetype");
		}
		if(request.getParameter("frm_purposeType")!=null){
			purposeType=request.getParameter("frm_purposeType");
		}
		if(request.getParameter("frm_reportType")!=null){
			reportType=request.getParameter("frm_reportType");
		}		 
		if(request.getParameter("frm_station")!=null){
			station=request.getParameter("frm_station");
		}
		if(request.getParameter("frm_trust")!=null){
			trust=request.getParameter("frm_trust");
		}
		if(request.getParameter("access_from")!=null){
			accessFrom=request.getParameter("access_from");
		}else{
			accessFrom="N";
		}
		
		log.info("-----fromDate------"+fromDate);
		log.info("-----toDate------"+toDate+"accessFrom"+accessFrom+"Access frm reques"+request.getParameter("access_from")+"reportbean.getLoginUserId()"+reportbean.getLoginUserId());		
		
		  advanceReportList=ptwAdvanceService.rejectedCPFReport(region,fromDate,toDate,advanceType,purposeType,trust,station,reportbean.getLoginProfile(),reportbean.getLoginUnitCode(),reportbean.getLoginUserId(),reportbean.getLoginUserAccType(),accessFrom);
		  							
		 
		log.info("Size"+advanceReportList.size());
		if(advanceReportList.size()!=0){
			request.setAttribute("advanceReportList",advanceReportList);
		}
		if(advanceReportList.size()>0){
		//reportbean1 =(AdvanceBasicReportBean)advanceReportList.get(0);		
		
	 
		
		if(!fromDate.equals("")){
			  repFromDate=commonUtil.converDBToAppFormat(fromDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			  reportbean1.setFromYear(repFromDate); 
		}
		
		if(!toDate.equals("")){
			repToDate=commonUtil.converDBToAppFormat(toDate,"dd/MM/yyyy", "dd-MMM-yyyy");
			 reportbean1.setToYear(repToDate);
		}
		
		
		if(region.equals("NO-SELECT")) {
			region="All-Regions";
		}
		reportbean1.setRegion(region);
	
		 
			reportbean.setScreenTitle("Rejected Advances Report");
			path="viewRejectedCPFReport"; 
		
		request.setAttribute("reportbean",reportbean);
		request.setAttribute("reportbean1",reportbean1);
		request.setAttribute("reportType",reportType); 		 
		request.setAttribute("FromDate",repFromDate);
		request.setAttribute("ToDate",repToDate);
		

		if((!fromDate.equals("")) && (!toDate.equals(""))){
			
			if(fromDate.substring(6,fromDate.length()).equals(toDate.substring(6,toDate.length()))){
				 reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length()));
			}else{ 
				reportbean1.setTransMnthYear(fromDate.substring(6,fromDate.length())+"-"+toDate.substring(8,toDate.length()));
			}
		} 
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		path="viewRejectedCPFReport";
		return mapping.findForward(path);
	}
}

