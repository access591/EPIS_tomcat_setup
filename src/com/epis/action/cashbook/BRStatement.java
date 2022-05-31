package com.epis.action.cashbook;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.DynaValidatorForm;
import com.epis.bean.cashbook.BRStatementInfo;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.services.cashbook.BRStatementService;
import com.epis.services.cashbook.BankMasterService;


public class BRStatement extends DispatchAction {

	/**
	 * Method search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.getInputForward();
	}
	
	public ActionForward mappingValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			BRStatementInfo info = new BRStatementInfo(request);
			info.setAccountNo(dyna.getString("accountNo"));
			request.setAttribute("columns",BRStatementService.getInstance().getColumns());
			request.setAttribute("mappedColumns",BRStatementService.getInstance().getMappedColumns(info));	
			request.setAttribute("accountNo",info.getAccountNo());
			request.setAttribute("data",info.getAccountNo());
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.findForward("configure");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		BRStatementInfo cols = null;
		BRStatementInfo info = new BRStatementInfo(request);
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		try {
			List columns = BRStatementService.getInstance().getColumns();
			List colMapping = new ArrayList();			
			for(int i=0;i<columns.size();i++){
				cols = (BRStatementInfo)columns.get(i);
				cols.setColumnNo(request.getParameter(cols.getColumnValue()));	
				colMapping.add(cols);
			}	
			info.setColMapping(colMapping);
			info.setAccountNo(dyna.getString("accountNo"));
			info.setDataLine(dyna.getString("dataLine"));
			BRStatementService.getInstance().save(info);
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.findForward("search");
	}
	public ActionForward importStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.getInputForward();
	}
	
	public ActionForward saveFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		ActionMessages errors = new ActionMessages();
		try {
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));					
			DynaValidatorForm dynaForm = (DynaValidatorForm)form;
			BRStatementInfo info = new BRStatementInfo(request);			
			BeanUtils.copyProperties(info,dynaForm);
			BRStatementService.getInstance().saveFile(info,getServlet().getServletContext().getRealPath("/"));	
			errors.add("errors",new ActionMessage("errors","Saved Sucessfully"));
		} catch (Exception e) {			
			errors.add("errors",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return mapping.getInputForward();
	}
	
	 public ActionForward showDetails(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response)
			 {	
			 log.info("inside ShowVouchers");
			 List Blist=null;
				//HttpSession session = request.getSession(true);
				try{
					DynaValidatorForm dyna = (DynaValidatorForm) form;
					//info.setLedgername(led.getString("LEDGERNM"));
					Blist = BRStatementService.getInstance().showDetails(dyna.getString("description"),dyna.getString("accountNo"));
				     request.setAttribute("Blist",Blist);
				     request.setAttribute("accountNo",dyna.getString("accountNo"));
				     //request.setAttribute("sno",dyna.getString("i"));

				}
				catch(Exception e){
					e.printStackTrace();
				}
				return mapping.findForward("showDetails");
			 }
	 public ActionForward saveDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
			String keyno = request.getParameter("keyno");
			String voucherNo = request.getParameter("voucherNo");
			String accountno=request.getParameter("accountno");
			log.info("keyno "+keyno);	
			String s = BRStatementService.getInstance().saveDetails(keyno,voucherNo,accountno);
			
			try{
			 	response.setContentType("text/xml");
			 	response.setHeader("Pragma","no-cache");//for HTTP1.0
				response.setHeader("cache-control","no-cache");// for HTTP/1.1.
		        PrintWriter out = response.getWriter();  
		        out.write(s);
		 	}catch(Exception e){		 		
		 	}			
			return null;
		}
	 public ActionForward savePartyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 {
		 String rowvalue=request.getParameter("rowvalue");
		 String amounttype=request.getParameter("amounttype");
		 log.info("the row value is"+rowvalue);
		 log.info("the amount type is"+amounttype);
		 String message=BRStatementService.getInstance().savePartyDetails(rowvalue,amounttype);
		 try{
			 response.setContentType("text/xml");
			 	response.setHeader("Pragma","no-cache");//for HTTP1.0
				response.setHeader("cache-control","no-cache");// for HTTP/1.1.
		        PrintWriter out = response.getWriter();  
		        out.write(message);
			 
		 }
		 catch(Exception e)
		 {
			 log.error("the Exception in savePartyDetails"+e.getMessage());
		 }
		 
		 return null;
	 }
	 
	 public ActionForward showParam(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			try {
				log.info("inside brStatement.java");
				request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			} catch (Exception e) {
				errors.add("",new ActionMessage("errors",e.getMessage()));
			}finally{
				saveErrors(session,errors);
			}		
			log.info("leaving brStatement.java");
			return mapping.findForward("showParam");
		}
	 
	 
	 
	 
	 
	 
	 public ActionForward showVoucherReportParam(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			try {
				log.info("inside brStatement.java");
				request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			} catch (Exception e) {
				errors.add("",new ActionMessage("errors",e.getMessage()));
			}finally{
				saveErrors(session,errors);
			}		
			log.info("leaving brStatement.java");
			return mapping.findForward("showVoucherParam");
		}
	 
	 
	 
		
	 
		public ActionForward showReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			List datalist = new ArrayList();	
			List bankList = new ArrayList();
			String fromDate=dyna.getString("fromDate");
			String toDate=dyna.getString("toDate");
			String reportType=dyna.getString("reportType");
			String accountNo=dyna.getString("accountNo");
			BankMasterInfo info= new BankMasterInfo();
			ActionMessages errors = new ActionMessages();
			ActionForward forward = mapping.findForward("showReport");
			if("excel".equals(reportType))
				forward = mapping.findForward("showExcel");
			
			try {
				if(!accountNo.equals(""))
					info.setAccountNo(accountNo);	
				bankList = BankMasterService.getInstance().search(info);	
				request.setAttribute("bankList",bankList);
			
				datalist=BRStatementService.getInstance().showReport(fromDate,toDate,accountNo);
				request.setAttribute("datalist",datalist);			
			} catch (Exception e) {
				errors.add("Report",new ActionMessage("errors",e.getMessage()));
			}
			saveErrors(request,errors);
			
			return forward;
		}
		public ActionForward unreconcileReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			List datalist = new ArrayList();
			List companyDatalist=new ArrayList();
			String fromDate=dyna.getString("fromDate");
			String toDate=dyna.getString("toDate");
			String reportType=dyna.getString("reportType");
			String accountNo=dyna.getString("accountNo");
			BankMasterInfo info= new BankMasterInfo();
			List bankList = new ArrayList();
			ActionMessages errors = new ActionMessages();
			ActionForward forward = mapping.findForward("showReport");
			if("excel".equals(reportType))
				forward = mapping.findForward("showExcel");
			
			try {
				if(!accountNo.equals(""))
					info.setAccountNo(accountNo);				
				bankList = BankMasterService.getInstance().search(info);	
				request.setAttribute("bankList",bankList);
				
				datalist=BRStatementService.getInstance().unreconcileReport(fromDate,toDate,accountNo);
				companyDatalist=BRStatementService.getInstance().unreconcileVoucherReport(fromDate,toDate,accountNo);
				request.setAttribute("companyDatalist",companyDatalist);
				request.setAttribute("datalist",datalist);			
			} catch (Exception e) {
				errors.add("Report",new ActionMessage("errors",e.getMessage()));
			}
			saveErrors(request,errors);
			
			return forward;
		}
		
		
		public ActionForward unreconcileVoucherReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			List datalist = new ArrayList();	
			String fromDate=dyna.getString("fromDate");
			String toDate=dyna.getString("toDate");
			String reportType=dyna.getString("reportType");
			String accountNo=dyna.getString("accountNo");
			BankMasterInfo info= new BankMasterInfo();
			List bankList = new ArrayList();
			ActionMessages errors = new ActionMessages();
			ActionForward forward = mapping.findForward("showReport");
			if("excel".equals(reportType))
				forward = mapping.findForward("showExcel");
			
			try {
				if(!accountNo.equals(""))
					info.setAccountNo(accountNo);				
				bankList = BankMasterService.getInstance().search(info);	
				request.setAttribute("bankList",bankList);
				
				datalist=BRStatementService.getInstance().unreconcileVoucherReport(fromDate,toDate,accountNo);
				request.setAttribute("datalist",datalist);			
			} catch (Exception e) {
				errors.add("Report",new ActionMessage("errors",e.getMessage()));
			}
			saveErrors(request,errors);
			
			return forward;
		}
		
}
