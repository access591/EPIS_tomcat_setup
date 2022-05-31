package com.epis.action.investment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
//import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.admin.Bean;
import com.epis.bean.investment.ArrangersBean;
import com.epis.bean.investment.InvestmentReportsBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.dao.investment.InvestmentReportDAO;
import com.epis.services.investment.ArrangersService;
import com.epis.services.investment.InvestmentReportService;
import com.epis.services.investment.QuotationService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
//import com.epis.utilities.StringUtility;
//import com.epis.utilities.UtilityBean;

public class InvestmentReportAction extends DispatchAction{
	Log log=new Log(InvestmentReportAction.class);
	 public ActionForward InterestReceivedStatement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActionForward forward=mapping.findForward("interestReceivedPrm"); 
		 try{
			List investmentModeList=QuotationService.getInstance().getInvestmentModes();
			 request.setAttribute("investmentModeList",investmentModeList);
		    List secList = InvestmentReportService.getInstance().SecurityNames("");
		    request.setAttribute("secList",secList);
		    log.info("actionnnnnnnnnnnnnnnnnnn  "+secList.size());
		 } catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
	       return forward;
	  }
public ActionForward InvestmentRegisterPrm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
{
	ActionForward forward=mapping.findForward("investmentregisterprm");
	List secList=null;
	try{
		secList = InvestmentReportService.getInstance().SecurityNames("");
		String[] finYears = StringUtility.getFinYearsTill("2009");
		List finYear = new ArrayList();
		for(int i=finYears.length-1;i>=0;i--){
			finYear.add(new Bean(finYears[i],finYears[i]));				
		}
		request.setAttribute("finYear",finYear);
		request.setAttribute("interestMonthList",InvestmentReportService.getInstance().getinterestMonths());
		request.setAttribute("maturityMonthList",InvestmentReportService.getInstance().getinterestMonths());
		request.setAttribute("maturityYearList",InvestmentReportService.getInstance().getMaturityYears());
	    request.setAttribute("secList",secList);
	    request.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
				.getSecurityCategories());
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages(); 
		errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
		saveErrors(request,errors);
		forward = mapping.getInputForward();
	}
	return forward;
}


public ActionForward InvestmentGroupRegisterPrm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
{
	ActionForward forward=mapping.findForward("investmentgroupregisterprm");
	List groupList=null;
	try{
		groupList = InvestmentReportService.getInstance().groupNames();
		request.setAttribute("interestMonthList",InvestmentReportService.getInstance().getinterestMonths());
		request.setAttribute("maturityMonthList",InvestmentReportService.getInstance().getinterestMonths());
		request.setAttribute("maturityYearList",InvestmentReportService.getInstance().getMaturityYears());
	    request.setAttribute("secList",groupList);
	    request.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
				.getSecurityCategories());
	}
	catch(Exception e)
	{
		ActionMessages errors = new ActionMessages(); 
		errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
		saveErrors(request,errors);
		forward = mapping.getInputForward();
	}
	return forward;
}
	 
public ActionForward InterstReceiveStatementReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("interstReceiveStatementReport"); 
		 InvestmentReportsBean bean = new InvestmentReportsBean();
		 String reportType=StringUtility.checknull(request.getParameter("reportType"));
		 try{ 
			   List arrangerList =null;
				
			   bean.setModeOfInvestment(StringUtility.checknull(request.getParameter("modeOfInvestment")));
			   bean.setSecurityNames(StringUtility.checknull(request.getParameter("securityNames")).replaceAll("\\^","%"));
			   bean.setFromDate(StringUtility.checknull(request.getParameter("fromDate")));
			   bean.setToDate(StringUtility.checknull(request.getParameter("toDate")));
			   
			   
			   arrangerList = InvestmentReportService.getInstance().InterestReceivedStatement(bean);
			  request.setAttribute("arrangerList",arrangerList);
			  if("excel".equals(reportType)){
					forward =mapping.findForward("interstReceiveStatementReportExcel");
				}
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
		   }
	 	   return forward;
	}
public ActionForward InvestmentRegisterReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
{
	ActionForward forward=mapping.findForward("investmentregisterreport");
	InvestmentReportsBean bean=new InvestmentReportsBean();
	String SecurityNames=StringUtility.checknull(request.getParameter("securityNames"));
	SecurityNames=StringUtility.checknull(SecurityNames).replaceAll("\\^","%");
	SecurityNames=StringUtility.checknull(SecurityNames).replaceAll("ampersand","&");
	
	try{
		List arrangerList=null;
		bean.setFromDate(StringUtility.checknull(request.getParameter("fromDate")));
		bean.setToDate(StringUtility.checknull(request.getParameter("toDate")));
		bean.setSecurityNames(StringUtility.checknull(SecurityNames));
		bean.setInvestDate(StringUtility.checknull(request.getParameter("investDate")));
		bean.setSecurityCategory(StringUtility.checknull(request.getParameter("securityCd")));
		bean.setInterestMonth(StringUtility.checknull(request.getParameter("interestMonth")));
		bean.setMaturityMonth(StringUtility.checknull(request.getParameter("maturityMonth")));
		bean.setMaturityYear(StringUtility.checknull(request.getParameter("maturityYear")));
		bean.setFinYear(StringUtility.checknull(request.getParameter("finYear")));
		arrangerList=InvestmentReportService.getInstance().InvestmentRegisterReport(bean);
		request.setAttribute("arrangerList",arrangerList);
		request.setAttribute("reportType",StringUtility.checknull(request.getParameter("reportType")));
		
		
	}
	catch(Exception e)
	{
			ActionMessages errors = new ActionMessages(); 
		   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
	}
	return forward;
	
}

public ActionForward InvestmentGroupRegisterReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
{
	ActionForward forward=mapping.findForward("investmentgroupregisterreport");
	InvestmentReportsBean bean=new InvestmentReportsBean();
	String SecurityNames=StringUtility.checknull(request.getParameter("securityNames"));
	SecurityNames=StringUtility.checknull(SecurityNames).replaceAll("\\^","%");
	SecurityNames=StringUtility.checknull(SecurityNames).replaceAll("ampersand","&");
	String reportType=StringUtility.checknull(request.getParameter("securityNames"));
	try{
		if("excel".equals(reportType)){
			forward=mapping.findForward("investmentgroupregisterreportExcel");
		}
		List arrangerList=null;
		bean.setFromDate(StringUtility.checknull(request.getParameter("fromDate")));
		bean.setToDate(StringUtility.checknull(request.getParameter("toDate")));
		bean.setSecurityNames(StringUtility.checknull(SecurityNames));
		bean.setInvestDate(StringUtility.checknull(request.getParameter("investDate")));
		bean.setSecurityCategory(StringUtility.checknull(request.getParameter("securityCd")));
		bean.setInterestMonth(StringUtility.checknull(request.getParameter("interestMonth")));
		bean.setMaturityMonth(StringUtility.checknull(request.getParameter("maturityMonth")));
		bean.setMaturityYear(StringUtility.checknull(request.getParameter("maturityYear")));
		arrangerList=InvestmentReportService.getInstance().InvestmentgroupRegisterReport(bean);
		request.setAttribute("arrangerList",arrangerList);
		
		
	}
	catch(Exception e)
	{
			ActionMessages errors = new ActionMessages(); 
		   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
	}
	return forward;
	
}


public ActionForward InterestReceivedRegister(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception
{
	ActionForward forward=mapping.findForward("interestReceivedInCashbook");
	
	InvestmentReportsBean bean=new InvestmentReportsBean();
	String SecurityProposal=StringUtility.checknull(req.getParameter("securityProposal"));
	
	SecurityProposal=StringUtility.checknull(SecurityProposal.replaceAll("~","%"));
	SecurityProposal=StringUtility.checknull(SecurityProposal.replaceAll("ß","#"));
	SecurityProposal=StringUtility.checknull(SecurityProposal).replaceAll("\\^","%");
	SecurityProposal=StringUtility.checknull(SecurityProposal).replaceAll("ampersand","&");
	
	
	
	try{
		List voucherDetails=null;
		bean.setSecurityproposalNo(SecurityProposal);
		
		voucherDetails=InvestmentReportService.getInstance().InterestReceivedRegister(bean);
		req.setAttribute("voucherDetails",voucherDetails);
		
	}
	catch(Exception e)
	{
			ActionMessages errors = new ActionMessages(); 
		   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
		   saveErrors(req,errors);
		   forward = mapping.getInputForward();
		   log.error("ArrangersAction:searchArrangers:Exception"+e.getMessage());
	}
	
	
	return forward;
	
}

	 public ActionForward TrustInvestmentMade(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActionForward forward=mapping.findForward("trustinvestmentmadePrm"); 
		 try{
			 List trustlist = (ArrayList)TrustTypeService.getInstance().getTrustTypes();
			 request.setAttribute("trustList", trustlist);
		 } catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
	       return forward;
	  }
public ActionForward TrustInvestmentMadeReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 
		 ActionForward forward=mapping.findForward("trustinvestmentmadeReport"); 
		 InvestmentReportsBean bean = new InvestmentReportsBean();
		 HttpSession session = request.getSession();
		 String reportType=StringUtility.checknull(request.getParameter("reportType"));
		 try{ 
			  
			   bean.setTrustType(StringUtility.checknull(request.getParameter("trusttype")));
			   bean.setDate(StringUtility.checknull(request.getParameter("date")));
			  List investmadeList = InvestmentReportService.getInstance().getTrustInvestmentMadeDetails(bean);
			  session.setAttribute("investmadeList",investmadeList);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
			   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
			   saveErrors(request,errors);
			   forward = mapping.getInputForward();
			   log.error("InvestmentReportAction:TrustInvestmentMadeReport():Exception"+e.getMessage());
		   }
		   StringBuffer newPath =
		        new StringBuffer(mapping.findForward("trustinvestmentmadeReport").getPath());
		 
		    newPath.append("?fyear="+request.getParameter("date"));
		    newPath.append("&trusttype="+StringUtility.checknull(request.getParameter("trusttype")));
		    newPath.append("&reportType="+reportType);
		    forward= (new ActionForward(newPath.toString(), true));
	 	   return forward;
	}	
public ActionForward paramInvestStatament(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 return mapping.findForward("investstmtparam"); 
		  
}
public ActionForward paramInvestMade(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
return mapping.findForward("investmadeparam"); 
}
public ActionForward reportInvestStmt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){

ActionForward forward=mapping.findForward("investstmtreport"); 
String year=StringUtility.checknull(request.getParameter("year"));
String security=StringUtility.checknull(request.getParameter("security"));
String trust=StringUtility.checknull(request.getParameter("trust"));
String frmDT=StringUtility.checknull(request.getParameter("frmDT"));
String toDT=StringUtility.checknull(request.getParameter("toDT"));
String report=StringUtility.checknull(request.getParameter("report"));
String reportType=StringUtility.checknull(request.getParameter("reportType"));
if(report.equals("IM"))
	 forward=mapping.findForward("investmadereport"); 

if(!report.equals("IM")){
frmDT="01-Apr-"+year;
toDT="31-Mar-"+(Integer.parseInt(year)+1);
}
log.info("aaaaaaannnnnniiiiiiiiiiilllllllll"+frmDT+toDT);
List list =null;
try{  
	   list =InvestmentReportService.getInstance().getInvStReportData(year,security,trust,frmDT,toDT);
	   request.setAttribute("recordList",list);
	   if("excel".equals(reportType)){
		   if(report.equals("IM")){
			forward =mapping.findForward("investmadereportExcel");
		   }else
			forward =mapping.findForward("investstmtreportExcel");
		}
	   log.info("record list "+list.size());
  }catch(Exception e){
	   ActionMessages errors = new ActionMessages(); 
	   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
	   saveErrors(request,errors);
	   forward = mapping.getInputForward();
	   log.error("InvestmentReportAction:reportInvestStmt:Exception"+e.getMessage());
  }
   return forward;
}


public ActionForward InvestmentStatistics(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	HttpSession session = request.getSession();
	List seccatList = InvestmentReportService.getInstance().SecurityCategories();
    session.setAttribute("seccatList",seccatList);
    List secnamesList = InvestmentReportService.getInstance().SecurityNames("");
    session.setAttribute("secnamesList",secnamesList);
	return mapping.findForward("investstatisticsparam"); 
	}
public ActionForward InvestmentStatisticsReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("investstatisticsReport"); 
	 InvestmentReportsBean bean = new InvestmentReportsBean();
	 HttpSession session = request.getSession();
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	 try{ 
		   bean.setSecurityCategory(StringUtility.checknull(request.getParameter("securityCategory")));
		   bean.setSecurityName(StringUtility.checknull(request.getParameter("securityName")).replaceAll("\\^","%"));
		   bean.setFromDate(StringUtility.checknull(request.getParameter("fromDate")));
		   bean.setToDate(StringUtility.checknull(request.getParameter("toDate")));
		  List investstatistics = InvestmentReportService.getInstance().InvestmentStatisticsDetails(bean);  
		  session.setAttribute("investstatistics",investstatistics);
	   }catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("arranger",new ActionMessage("arranger.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:TrustInvestmentMadeReport():Exception"+e.getMessage());
	   }
	   String fdate = "",tdate="";
	    String fromDate = StringUtility.checknull(request.getParameter("fromDate"));
	    if(!"".equals(fromDate))
	       fdate = fromDate.substring(9,fromDate.length());
	    if(!"".equals(fromDate))
	       tdate = String.valueOf(Integer.parseInt(fdate)-1);
	      if(tdate.length()!=2)
	    	  tdate="0"+tdate;
	      String fyear = tdate+"-"+fdate;
	   StringBuffer newPath =
	        new StringBuffer(mapping.findForward("investstatisticsReport").getPath());
	    newPath.append("?fyear="+fyear);
	    newPath.append("&category="+StringUtility.checknull(request.getParameter("categoryCd")));
	    newPath.append("&sname="+StringUtility.checknull(request.getParameter("securityName")));
	    newPath.append("&reportType="+reportType);
	    forward= (new ActionForward(newPath.toString(), true));
	   return forward;
}	
public ActionForward paramAmtInvested(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	//log.info("anillllll");
	return mapping.findForward("amtinvestparam"); 
}



public ActionForward reportAmtInvest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("amtinvestreport"); 
	 String year=StringUtility.checknull(request.getParameter("year"));
	 String security=StringUtility.checknull(request.getParameter("security"));
	 String trust=StringUtility.checknull(request.getParameter("trust"));
	 String frmDT=StringUtility.checknull(request.getParameter("frmDT"));
	 String toDT=StringUtility.checknull(request.getParameter("toDT"));
	 String report=StringUtility.checknull(request.getParameter("report"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	 
	//System.out.println("anillllllllllllllllllllllllllllll");
	try{
		if("excel".equals(reportType))
		forward=mapping.findForward("amtinvestreportExcel");
		QuotationBean qbean=new QuotationBean();
		List ratioList =InvestmentReportService.getInstance().getCurrentRatio();
		int listSize=ratioList.size();
		qbean.setSNo(String.valueOf(listSize));
		request.setAttribute("ratioList",ratioList);
		request.setAttribute("ratioSize",qbean);
		Map map=InvestmentReportService.getInstance().getAmountInvested(security,trust,frmDT,toDT);
		//System.out.println("sunillllllllllllllllllllllllllllll "+map.size());
		request.setAttribute("records",map);
	}catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:reportInvestStmt:Exception"+e.getMessage());
	   }
	
	 //log.info("aaaaaaannnnnniiiiiiiiiiilllllllll");
	 
	   return forward;
}
public ActionForward TrustYTMInvestMade(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	List trustlist = (ArrayList)TrustTypeService.getInstance().getTrustTypes();
	 request.setAttribute("trustList", trustlist);
	return mapping.findForward("ytminvestmentmadeparam"); 
	}
public ActionForward TrustYTMInvestMadeReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("ytminvestmentmadereport"); 
	 String trust=StringUtility.checknull(request.getParameter("trusttype"));
	 String frmDT=StringUtility.checknull(request.getParameter("fromDate"));
	 String toDT=StringUtility.checknull(request.getParameter("toDate"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	try{
		HttpSession session = request.getSession();
		InvestmentReportsBean ibean = new InvestmentReportsBean();
		ibean.setTrustType(trust);
		ibean.setFromDate(frmDT);
		ibean.setToDate(toDT);
		List ytminvestList =InvestmentReportService.getInstance().getYTMInvest(ibean);
		session.setAttribute("ytminvestList",ytminvestList);
	}catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:TrustYTMInvestMadeReport:Exception"+e.getMessage());
	   }
	
	StringBuffer newPath =
        new StringBuffer(mapping.findForward("ytminvestmentmadereport").getPath());
    newPath.append("?frmDT="+frmDT);
    newPath.append("&toDT="+toDT);
    newPath.append("&trust="+trust);
    newPath.append("&reportType="+reportType);
    forward= (new ActionForward(newPath.toString(), true));
	 
	   return forward;
}
public ActionForward RedumptionBondSecuritires(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	List trustlist = (ArrayList)TrustTypeService.getInstance().getTrustTypes();
	 request.setAttribute("trustList", trustlist);
	return mapping.findForward("redumptionbondsecuritiresparam"); 
	}
public ActionForward RedumptionBondSecuritiresReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("redumptionbondsecuritiresreport"); 
	 String trust=StringUtility.checknull(request.getParameter("trusttype"));
	 String year=StringUtility.checknull(request.getParameter("year"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	try{
		HttpSession session = request.getSession();
		InvestmentReportsBean ibean = new InvestmentReportsBean();
		ibean.setTrustType(trust);
		ibean.setDate(year);
		List redumptionBondList =InvestmentReportService.getInstance().getRedumptionBonds(ibean);
		session.setAttribute("redumptionBondList",redumptionBondList);
	}catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:RedumptionBondSecuritiresReport():Exception"+e.getMessage());
	   }
	
	StringBuffer newPath =
       new StringBuffer(mapping.findForward("redumptionbondsecuritiresreport").getPath());
   newPath.append("?year="+year);
   newPath.append("&trust="+trust);
   newPath.append("&reportType="+reportType);
   forward= (new ActionForward(newPath.toString(), true));
	 
	   return forward;
}

public ActionForward TrustTotalInvestment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	List trustlist = (ArrayList)TrustTypeService.getInstance().getTrustTypes();
	 request.setAttribute("trustList", trustlist);
	return mapping.findForward("trusttotalinvestmentparam"); 
	}
public ActionForward TrustTotalInvestmentReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("trusttotalinvestmentreport"); 
	 String trust=StringUtility.checknull(request.getParameter("trusttype"));
	 String date=StringUtility.checknull(request.getParameter("date"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	try{
		HttpSession session = request.getSession();
		InvestmentReportsBean ibean = new InvestmentReportsBean();
		ibean.setTrustType(trust);
		ibean.setDate(date);
		List trustTotalInvestList =InvestmentReportService.getInstance().trustTotalInvestment(ibean);
		session.setAttribute("trustTotalInvestList",trustTotalInvestList);
	}catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:TrustTotalInvestment():Exception"+e.getMessage());
	   }
	
	StringBuffer newPath =
      new StringBuffer(mapping.findForward("trusttotalinvestmentreport").getPath());
  newPath.append("?date="+date);
  newPath.append("&trust="+trust);
  newPath.append("&reportType="+reportType);
  forward= (new ActionForward(newPath.toString(), true));
	 
	   return forward;
}
public ActionForward paramInterestDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
	return mapping.findForward("interestdetailparam"); 
}
public ActionForward paramFileDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
	return mapping.findForward("filedetailparam"); 
}


public ActionForward reportIntersetDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("investstmtreport"); 
	 String year=StringUtility.checknull(request.getParameter("year"));
	 String security=StringUtility.checknull(request.getParameter("security"));
	 String trust=StringUtility.checknull(request.getParameter("trust"));
	 String frmDT=StringUtility.checknull(request.getParameter("frmDT"));
	 String toDT=StringUtility.checknull(request.getParameter("toDT"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	 forward=mapping.findForward("interestdetailreport"); 
	
	 //log.info("aaaaaaannnnnniiiiiiiiiiilllllllll");
	 List list =null;
	 try{  
		   list =InvestmentReportService.getInstance().getInterestDetails(year,security,trust,frmDT,toDT);
		   request.setAttribute("recordList",list);
		   log.info("record list "+list.size());
		   if("excel".equals(reportType)){
			   forward=mapping.findForward("interestdetailreportExcel"); 
		   }
	   }catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:reportInvestStmt:Exception"+e.getMessage());
	   }
	   return forward;
}
public ActionForward reportFileDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	 
	 ActionForward forward=mapping.findForward("filedetailreport"); 
	 String year=StringUtility.checknull(request.getParameter("year"));
	 String security=StringUtility.checknull(request.getParameter("security"));
	 String trust=StringUtility.checknull(request.getParameter("trust"));
	 String frmDT=StringUtility.checknull(request.getParameter("frmDT"));
	 String toDT=StringUtility.checknull(request.getParameter("toDT"));
	 String report=StringUtility.checknull(request.getParameter("report"));
	 String reportType=StringUtility.checknull(request.getParameter("reportType"));
	//System.out.println("anillllllllllllllllllllllllllllll");
	try{
		if("excel".equals(reportType)){
			forward=mapping.findForward("filedetailreportExcel");
		}
		QuotationBean qbean=new QuotationBean();
		List ratioList =InvestmentReportService.getInstance().getCurrentRatio();
		int listSize=ratioList.size()+1;
		qbean.setSNo(String.valueOf(listSize));
		request.setAttribute("ratioList",ratioList);
		request.setAttribute("ratioSize",qbean);
		Map map=InvestmentReportService.getInstance().getFileDetails(security,trust,frmDT,toDT);
		//System.out.println("sunillllllllllllllllllllllllllllll "+map.size());
		request.setAttribute("records",map);
	}catch(Exception e){
		   ActionMessages errors = new ActionMessages(); 
		   errors.add("report",new ActionMessage("report.Errors", e.getMessage()));
		   saveErrors(request,errors);
		   forward = mapping.getInputForward();
		   log.error("InvestmentReportAction:reportFileDetails:Exception"+e.getMessage());
	   }
	
	 //log.info("aaaaaaannnnnniiiiiiiiiiilllllllll");
	 
	   return forward;
}

}

