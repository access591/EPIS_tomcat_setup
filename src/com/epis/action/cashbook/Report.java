package com.epis.action.cashbook;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.admin.Bean;
import com.epis.bean.cashbook.IncomeAndExpenditureBean;
import com.epis.bean.cashbook.PaymentReceiptBean;
import com.epis.bean.cashbook.PaymentReceiptDtBean;
import com.epis.bean.cashbook.ReportBean;
import com.epis.bean.cashbook.ScheduleTypeInfo;
import com.epis.bean.cashbookDummy.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.services.cashbook.ReportService;
import com.epis.services.cashbook.ScheduleTypeService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.StringUtility;


public class Report extends DispatchAction {

	/**
	 * Method dailyStatement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward dailyStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		return mapping.findForward("dailyStatementSearch");
	}
	
	/**
	 * Method getDailyStatement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws EPISException 
	 */
	public ActionForward getDailyStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {		
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ReportBean bean = new ReportBean();			
			BeanUtils.copyProperties(bean,dyna);	
			bean.setDay(StringUtility.getDay(bean.getFromDate()));
			Map dsList = ReportService.getInstance().getDailyStatement(bean);
			bean.setNoOfRecords(dsList.size());
			request.setAttribute("dailyStmt",dsList);
			request.setAttribute("reportBean",bean);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (EPISException e) {			
			e.printStackTrace();
		}
		return mapping.findForward("dailyStatement");
	}	
	
	/**
	 * Method incomeAndExpenditure
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward incomeAndExpenditure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		try {
			String years[] = StringUtility.getFinYearsTill("2009");
			List currYear = new ArrayList(); 
			int yearlen = years.length;
			for(int i=0;i < yearlen;i++){
				currYear.add(new Bean(years[i],years[i].substring(0,2)+years[i].substring(5)));
			}
			request.setAttribute("currYear",currYear);
			request.setAttribute("Trusts",TrustTypeService.getInstance().getTrustTypes());
			request.setAttribute("finYears",StringUtility.getFYearMonths());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("ieSearch");
	}
	
	/**
	 * Method getIncomeAndExpenditure
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws EPISException 
	 */
	public ActionForward getIncomeAndExpenditure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {		
		try {
			DecimalFormat decFormat = new DecimalFormat("##########0.00");
			IncomeAndExpenditureBean iebean = null;
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ReportBean bean = new ReportBean();
			bean.setFinYear(dyna.getString("finYear").split("\\,"));
			bean.setTrustType(dyna.getString("trustType"));
			String toMonth = "".equals(StringUtility.checknull(dyna.getString("toMonth")))?CommonUtil.getCurrentMonth():dyna.getString("toMonth");
			bean.setToMonth(toMonth);
			Map ieReport = ReportService.getInstance().getIncomeAndExpenditure(bean);
			int length = bean.getFinYear().length;
			List incExp = new ArrayList();
			String[] report = new String[2*length+2];
			int i=0;
			String[] finYears = bean.getFinYear();
			int len = finYears.length;
			String[] years = new String[len];
			for(int j=0;j < len ;j++){
				years[j] = (Integer.parseInt(finYears[j])-1)+"-"+(finYears[j].substring(2));
			}
			bean.setFinYear(years);			
			
			List exp = (List) ieReport.get("E");
			List inc = (List) ieReport.get("I");
			
			int expLen = exp.size();
			int incLen = inc.size();
			
			double expTot[] = new double[len];
			double incTot[] = new double[len];
			
			for(int j=0; j < expLen ;j++){
				report = new String[2*length+4];
				i=0;
				iebean = (IncomeAndExpenditureBean) exp.get(j);
				report[i++] = iebean.getParticular();
				report[i++] = iebean.getSchType();
				for(int k=0;k < len ;k++){
					if(Double.parseDouble(iebean.getAmount()[k]) == 0.00)
						report[i++] = "0.00";
					else
						report[i++] = decFormat.format((-1.00)* Double.parseDouble(iebean.getAmount()[k]));
					expTot[k] +=  (-1.00)*Double.parseDouble(iebean.getAmount()[k]);
				}
				if(j<=incLen){
					iebean = (IncomeAndExpenditureBean) inc.get(j);
					report[i++] = iebean.getParticular();
					report[i++] = iebean.getSchType();
					for(int k=0;k < len ;k++){
						report[i++] = decFormat.format(Double.parseDouble(iebean.getAmount()[k]));
						incTot[k] +=  Double.parseDouble(iebean.getAmount()[k]);
					}
				}else{
					report[i++] = "";
					report[i++] = "";
					for(int k=0;k < len ;k++){
						report[i++] = "";					
					}
				}
				incExp.add(report);
			}
			
			for(int j=expLen; j < incLen ;j++){
				report = new String[2*length+4];
				i=0;
				report[i++] = "";
				report[i++] = "";
				for(int k=0;k < len ;k++){
					report[i++] = "";					
				}
				iebean = (IncomeAndExpenditureBean) inc.get(j);
				report[i++] = iebean.getParticular();
				report[i++] = iebean.getSchType();
				for(int k=0;k < len ;k++){
					report[i++] = decFormat.format(Double.parseDouble(iebean.getAmount()[k]));
					incTot[k] +=  Double.parseDouble(iebean.getAmount()[k]);
				}
				incExp.add(report);
			}
			
			
			report = new String[2*length+4];
			i=0;
			report[i++] = "Net Profit";
			report[i++] = "";
			boolean bool = false ;
			for(int k=0;k < len ;k++){
				if(expTot[k] < incTot[k]){
					report[i++] = decFormat.format(incTot[k]-expTot[k]);
					bool = true;
				}else{
					report[i++] = "-";
				}
			}
			if(!bool){
				i=0;
				report = new String[2*length+4];
				report[i++] = "";
				report[i++] = "";
				for(int k=0;k < len ;k++){
					report[i++] = "";					
				}
			}
			bool = false ;
			int geti = i;
			report[i++] = "Net Loss";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] > incTot[k]){
					report[i++] = decFormat.format(expTot[k]-incTot[k]);
					bool = true;
				}
				else
					report[i++] = "-";
			}
			if(!bool){
				i=geti;				
				report[i++] = "";
				report[i++] = "";
				for(int k=0;k < len ;k++){
					report[i++] = "";					
				}
			}
			incExp.add(report);
			
			report = new String[2*length+4];
			i=0;
			report[i++] = "Total";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] < incTot[k])
					report[i++] = decFormat.format(incTot[k]);
				else
					report[i++] = decFormat.format(expTot[k]);
			}
			report[i++] = "Total";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] > incTot[k])
					report[i++] = decFormat.format(expTot[k]);
				else
					report[i++] = decFormat.format(incTot[k]);
			}
			bean.setReportType(dyna.getString("reportType"));
			request.setAttribute("report",incExp);
			request.setAttribute("bean",bean);
			request.setAttribute("Totals",report);			
		} catch (EPISException e) {			
			e.printStackTrace();
		}
		return mapping.findForward("ie");
	}	
	
	/**
	 * Method balanceSheet
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward balanceSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		try {
			String years[] = StringUtility.getFinYearsTill("2009");
			List currYear = new ArrayList(); 
			int yearlen = years.length;
			for(int i=0;i < yearlen;i++){
				currYear.add(new Bean(years[i],years[i].substring(0,2)+years[i].substring(5)));
			}
			request.setAttribute("currYear",currYear);
			request.setAttribute("Trusts",TrustTypeService.getInstance().getTrustTypes());
			request.setAttribute("finYears",StringUtility.getFYearMonths());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("bsSearch");
	}
	
	/**
	 * Method getIncomeAndExpenditure
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws EPISException 
	 */
	public ActionForward getBalanceSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {		
		try {
			DecimalFormat decFormat = new DecimalFormat("##########0.00");
			IncomeAndExpenditureBean iebean = null;
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ReportBean bean = new ReportBean();
			bean.setFinYear(dyna.getString("finYear").split("\\,"));
			bean.setTrustType(dyna.getString("trustType"));
			String toMonth = "".equals(StringUtility.checknull(dyna.getString("toMonth")))?CommonUtil.getCurrentMonth():dyna.getString("toMonth");
			bean.setToMonth(toMonth);
			Map ieReport = ReportService.getInstance().getBalanceSheet(bean);
			int length = bean.getFinYear().length;
			List incExp = new ArrayList();
			String[] report = new String[2*length+2];
			int i=0;
			String[] finYears = bean.getFinYear();
			int len = finYears.length;
			String[] years = new String[len];
			for(int j=0;j < len ;j++){
				years[j] = (Integer.parseInt(finYears[j])-1)+"-"+(finYears[j].substring(2));
			}
			bean.setFinYear(years);			
			
			List exp = (List) ieReport.get("L");
			List inc = (List) ieReport.get("A");
			
			int expLen = exp.size();
			int incLen = inc.size();
			
			double expTot[] = new double[len];
			double incTot[] = new double[len];
			
			for(int j=0; j < expLen ;j++){
				report = new String[2*length+4];
				i=0;
				iebean = (IncomeAndExpenditureBean) exp.get(j);
				report[i++] = iebean.getParticular();
				report[i++] = iebean.getSchType();
				for(int k=0;k < len ;k++){
					report[i++] = decFormat.format(Double.parseDouble(iebean.getAmount()[k]));
					expTot[k] +=  Double.parseDouble(iebean.getAmount()[k]);
				}
				if(j<incLen){
					iebean = (IncomeAndExpenditureBean) inc.get(j);
					report[i++] = iebean.getParticular();
					report[i++] = iebean.getSchType();
					for(int k=0;k < len ;k++){
						report[i++] = decFormat.format(Double.parseDouble(iebean.getAmount()[k]));
						incTot[k] +=  Double.parseDouble(iebean.getAmount()[k]);
					}
				}else{
					report[i++] = "";
					report[i++] = "";
					for(int k=0;k < len ;k++){
						report[i++] = "";					
					}
				}
				incExp.add(report);
			}
			
			for(int j=expLen; j < incLen ;j++){
				report = new String[2*length+4];
				i=0;
				iebean = (IncomeAndExpenditureBean) inc.get(j);
				report[i++] = "";
				report[i++] = "";
				for(int k=0;k < len ;k++){
					report[i++] = "";					
				}
				report[i++] = iebean.getParticular();
				report[i++] = iebean.getSchType();				
				for(int k=0;k < len ;k++){
					report[i++] = decFormat.format(Double.parseDouble(iebean.getAmount()[k]));
					incTot[k] +=  Double.parseDouble(iebean.getAmount()[k]);
				}				
				incExp.add(report);
			}
			report = new String[2*length+4];
			i=0;
			report[i++] = "Reserve Account";
			report[i++] = "";
			report[2+len] = "Reserve Account";
			report[3+len] = "";
			for(int k=0;k < len ;k++){
				String str = ReportService.getInstance().getReserveAmount(finYears[k],bean.getTrustType(),bean.getToMonth());
				
				if(str.startsWith("-")){
					
					report[i++] = decFormat.format(Double.parseDouble(str.substring(1)));
					report[i+len+1] = "0.00";
					expTot[k] += Double.parseDouble(str.substring(1));
				} else {
					
					report[i++] = decFormat.format(Double.parseDouble(str));
					report[i+len+1] = "0.00";
					expTot[k] += Double.parseDouble(str);
					
				}
			}
			incExp.add(report);
			report = new String[2*length+4];
			i=0;
			report[i++] = "Difference in Opening balance";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] < incTot[k])
					report[i++] = decFormat.format(incTot[k]-expTot[k]);
				else
					report[i++] = "-";
			}
			report[i++] = "Difference in Opening balance";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] > incTot[k])
					report[i++] = decFormat.format(expTot[k]-incTot[k]);
				else
					report[i++] = "-";
			}
			incExp.add(report);			
			
			report = new String[2*length+4];
			i=0;
			report[i++] = "Total";		
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] < incTot[k])
					report[i++] = decFormat.format(incTot[k]);
				else
					report[i++] = decFormat.format(expTot[k]);
			}
			report[i++] = "Total";
			report[i++] = "";
			for(int k=0;k < len ;k++){
				if(expTot[k] > incTot[k])
					report[i++] = decFormat.format(expTot[k]);
				else
					report[i++] = decFormat.format(incTot[k]);
			}
			bean.setReportType(dyna.getString("reportType"));
			request.setAttribute("report",incExp);
			request.setAttribute("bean",bean);
			request.setAttribute("Totals",report);
		} catch (EPISException e) {			
			e.printStackTrace();
		}
		return mapping.findForward("bs");
	}	
	
	/**
	 * Method getScheduleReport
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getScheduleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("schReport"));
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			ReportBean bean = new ReportBean();
			int finYear = Integer.parseInt(dyna.getString("finYear"));
			bean.setFromDate("01/Apr/"+(finYear-1));
			String formMonth = "".equals(StringUtility.checknull(dyna.getString("toMonth")))?CommonUtil.getCurrentMonth():dyna.getString("toMonth");
			String month = CommonUtil.getNextMonth(formMonth);
			if(!("Jan".equalsIgnoreCase(month) || "Feb".equalsIgnoreCase(month) || "Mar".equalsIgnoreCase(month)|| "Apr".equalsIgnoreCase(month))){
				finYear--;
			}
			bean.setToDate("01/"+month+"/"+finYear);
			bean.setTrustType(dyna.getString("trustType"));
			bean.setScheduleType(dyna.getString("scheduleType"));
			
			request.getSession().setAttribute("trailBalance", ReportService.getInstance().getScheduleReport(bean));
			redirect.addParameter("fromDate",bean.getFromDate());
			redirect.addParameter("toDate",bean.getToDate());
			redirect.addParameter("trustType",bean.getTrustType());
			redirect.addParameter("scheduleType",bean.getScheduleType());
			redirect.addParameter("tomonth",formMonth);
			redirect.addParameter("grouping","Y");			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirect;
	}
	
	
	/**
	 * Method scheduleReport
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward scheduleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		try {
			String years[] = StringUtility.getFinYearsTill("2010");
			List currYear = new ArrayList(); 
			int yearlen = years.length;
			for(int i=0;i < yearlen;i++){
				currYear.add(new Bean(years[i],years[i].substring(0,2)+years[i].substring(5)));
			}
			request.setAttribute("schedule",ScheduleTypeService.getInstance().search(new ScheduleTypeInfo()));
			request.setAttribute("currYear",currYear);
			request.setAttribute("Trusts",TrustTypeService.getInstance().getTrustTypes());
			request.setAttribute("finYears",StringUtility.getFYearMonths());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("schReportSearch");
	}
	public ActionForward getPaymentandReceipt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		try{
			String years[] = StringUtility.getFinYearsTill("2009");
			List currYear = new ArrayList(); 
			int yearlen = years.length;
			for(int i=0;i < yearlen;i++){
				currYear.add(new Bean(years[i],years[i].substring(0,2)+years[i].substring(5)));
			}
			request.setAttribute("currYear",currYear);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("paymentandreceiptparam");
	}
	public ActionForward rejectedcasesprmLoans(ActionMapping am,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		try{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return am.findForward("rejectedcasesparam");
	}
	public ActionForward getRejectedLoans(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		VoucherInfo info=new VoucherInfo(req);
		List rejectedCases=null;
		try{
			info.setEmpPartyCode(StringUtility.checknull(req.getParameter("pfId")));
			info.setTransactionId(StringUtility.checknull(req.getParameter("transactionNo")));
			info.setPreparedDt(StringUtility.checknull(req.getParameter("approvedDate")));
			rejectedCases=ReportService.getInstance().getRejectedLoans(info);
			req.setAttribute("rejectedCases",rejectedCases);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return am.findForward("rejectedcasesreport");
	}
	
	public ActionForward getPaymentandReceiptReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		PaymentReceiptBean bean=new PaymentReceiptBean();
		PaymentReceiptDtBean dtbean=new PaymentReceiptDtBean();
		try{
			String finYear=request.getParameter("finyear");
			dtbean.setFinYear(finYear);
			
			bean=ReportService.getInstance().getPaymentandReceiptReport(dtbean);
			request.setAttribute("beanlist",bean);
			
		}
		catch(Exception e)
		{
			
		}
		return mapping.findForward("paymentandreceiptreport");
	}
}
