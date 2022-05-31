package com.epis.action.cashbook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.cashbook.BankBook;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.cashbook.VoucherDetails;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.cashbook.VoucherService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

public class Voucher extends DispatchAction {
	
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
		ActionMessages errors = (ActionMessages)request.getAttribute("org.apache.struts.action.ERROR");
		try {
			if(errors == null)
				errors = new ActionMessages();
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
			VoucherInfo info = new VoucherInfo();
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("VoucherList", VoucherService.getInstance().search(info));
			HttpSession session = request.getSession();
			session.setAttribute("finYearList", VoucherService.getInstance().getFinYears());
		} catch (IllegalAccessException e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		} catch (Exception e) {
			errors.add("search",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return mapping.getInputForward();
	}
	
	/**
	 * Method fwdtoNew
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
		String voucherType = (String) dyna.get("voucherType");
		ActionForward forward = null;
		if("P".equals(voucherType)){
			System.out.println("---1---------");
			forward = mapping.findForward("paymentNew");
		}else if("R".equals(voucherType)){
			System.out.println("----2--------");
			forward = mapping.findForward("receiptNew");
		}
		return forward;
	}
	
	Log log = new Log(Voucher.class);

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		VoucherInfo info = new VoucherInfo();
		VoucherService service = VoucherService.getInstance();
		log.info("Voucher : service : Entering Method");
		String dispatch = null;
		String redirect = null;
		if ("addVoucher".equals(request.getParameter("method"))) {
			HttpSession session = request.getSession();
			info.setAccountNo(request.getParameter("accountNo"));
			info.setBankName(request.getParameter("bankName"));
			info.setFinYear(request.getParameter("year"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setVoucherType(request.getParameter("vouchertype"));
			info.setEnteredBy((String) session.getAttribute("userid"));
			info.setPreparedBy((String) session.getAttribute("userid"));
			info.setDetails(request.getParameter("voucherDetails"));
			info.setPreparedDt(request.getParameter("prepDate"));
			info.setDesignation(request.getParameter("edesignation"));
			info.setTransactionType(request.getParameter("transType"));
			String detailRecords[] = request
					.getParameterValues("detailRecords");
			int length = detailRecords.length;
			List voucherDts = new ArrayList();
			VoucherDetails voucherDt = null;
			for (int i = 0; i < length; i++) {
				StringTokenizer st = new StringTokenizer(detailRecords[i], "|");
				if (st.hasMoreTokens()) {
					voucherDt = new VoucherDetails();
					voucherDt.setAccountHead(st.nextToken());
					voucherDt.setMonthYear(st.nextToken());
					voucherDt.setDetails(st.nextToken());
					voucherDt.setChequeNo(st.nextToken());
					voucherDt.setDebit(Double.parseDouble(st.nextToken()));
					voucherDt.setCredit(Double.parseDouble(st.nextToken()));
				}
				voucherDts.add(voucherDt);
			}
			info.setVoucherDetails(voucherDts);
			if (!"C".equals(info.getVoucherType())) {
				info.setPartyType(request.getParameter("party"));
				if ("E".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("epfid"));
					// added three new parameters cpfaccno, region, pfidFlag
					info.setEmpCpfacno(request.getParameter("ecpfacno"));
					info.setEmpRegion(request.getParameter("eregion"));
					info.setPfidFlag(request.getParameter("pfidFlag"));
					info.setEmployeeName(request.getParameter("eName"));
					log.info("pfid flag" + info.getPfidFlag());

				} else if ("P".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("pName"));
				}
			} else {
				info.setPartyType("B");
				info.setEmpPartyCode(request.getParameter("contraAccountNo"));
			}
			try {
				String keyNo = service.addVoucherRecord(info);
				redirect = "./jsp/cashbook/voucher/Voucher.jsp?accountNo="
						+ info.getAccountNo() + "&&type="
						+ info.getVoucherType() + "&&keyNo=" + keyNo+"&&bankName="+info.getBankName();
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("searchRecords".equals(request.getParameter("method"))) {
			info.setBankName(request.getParameter("bankName") == null ? ""
					: request.getParameter("bankName"));
			info.setFinYear(request.getParameter("year") == null ? "" : request
					.getParameter("year"));
			info
					.setVoucherType(request.getParameter("voucherType") == null ? ""
							: request.getParameter("voucherType"));
			info.setPartyType(request.getParameter("partyType") == null ? ""
					: request.getParameter("partyType"));
			info.setAccountNo(request.getParameter("accountNo") == null ? ""
					: request.getParameter("accountNo"));
			info.setKeyNo(request.getParameter("keyNo") == null ? "" : request
					.getParameter("keyNo"));
			info.setVoucherNo(request.getParameter("vNo") == null ? ""
					: request.getParameter("vNo"));
			info.setVoucherDt(request.getParameter("vDate") == null ? ""
					: request.getParameter("vDate"));
			info.setPreparedDt(request.getParameter("prepDate") == null ? ""
					: request.getParameter("prepDate"));
			try {
				List dataList = service.searchRecords(info, "");
				request.setAttribute("dataList", dataList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			dispatch = "./jsp/cashbook/voucher/VoucherSearch.jsp?keyNo="
					+ info.getKeyNo() + "&&type=" + info.getVoucherType();
		} else if ("getReport".equals(request.getParameter("method"))) {
			info.setKeyNo(request.getParameter("keyNo"));
			try {
				info = service.getReport(info);
				request.setAttribute("info", info);
				if ("approve".equals(request.getParameter("type"))) {
					BankBook book = service.getOpenBalance(info.getAccountNo(),
							"01/Jan/9999");
					request.setAttribute("book", book);
					dispatch = "./jsp/cashbook/voucher/ApprovedVoucher.jsp";
				} else if ("Contra".equals(info.getVoucherType())) {
					dispatch = "./jsp/cashbook/voucher/ContraVoucherReport.jsp";
				}	else {
					dispatch = "./jsp/cashbook/voucher/VoucherReport.jsp";
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getVoucherAppRecords"
				.equals(request.getParameter("method"))) {
			info.setBankName(request.getParameter("bankName") == null ? ""
					: request.getParameter("bankName"));
			info.setFinYear(request.getParameter("year") == null ? "" : request
					.getParameter("year"));
			info
					.setVoucherType(request.getParameter("voucherType") == null ? ""
							: request.getParameter("voucherType"));
			info.setPartyType(request.getParameter("partyType") == null ? ""
					: request.getParameter("partyType"));
			info.setPreparedDt(request.getParameter("prepDate") == null ? ""
					: request.getParameter("prepDate"));
			info.setKeyNo(request.getParameter("prepNo") == null ? "" : request
					.getParameter("prepNo"));
			try {

				List dataList = service.searchRecords(info, "A");
				request.setAttribute("dataList", dataList);
				dispatch = "./jsp/cashbook/voucher/VoucherApproval.jsp";
				if (request.getParameter("type") != null
						&& "app".equals(request.getParameter("type"))) {
					dispatch += "?&&keyNo="
							+ (request.getParameter("keyNo") == null ? ""
									: request.getParameter("keyNo"));
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getVoucherApproval".equals(request.getParameter("method"))) {

			try {
				info.setVoucherDt(request.getParameter("voucherDate"));
				info.setKeyNo(request.getParameter("keyNo"));
				info.setCheckedBy(request.getParameter("checkedby"));
				info.setApprovedBy(request.getParameter("approved"));
				info.setStatus(request.getParameter("approvalstatus"));
				service.updateApprovalVoucher(info);
				redirect = "./Voucher?method=getVoucherAppRecords&&keyNo="
						+ info.getKeyNo() + "&&type=app";
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getBankBook".equals(request.getParameter("method"))) {
			// info.setAccountNo(request.getParameter("accountNo"));
			info.setAccountHead(request.getParameter("accountCode"));
			info.setFromDate(request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			try {
				List book = service.getBankBook(info);
				request.setAttribute("book", book);
				if(request.getParameter("reportType").equals("html")){
					dispatch = "./jsp/cashbook/bankbook/BankBook.jsp?fromDate="
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&accountHead="+
						info.getAccountHead()+"&&reportType="+request.getParameter("reportType")
						+"&&reportbal="+request.getParameter("reportbal");
				}else{
					dispatch = "./jsp/cashbook/bankbook/BankBookExcel.jsp?fromDate="
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&accountHead="+
						info.getAccountHead()+"&&reportType="+request.getParameter("reportType")
						+"&&reportbal="+request.getParameter("reportbal");
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("editVoucher".equals(request.getParameter("method"))) {

			info.setKeyNo(request.getParameter("keyNo"));
			try {
				VoucherInfo editInfo = service.getReport(info);
				request.setAttribute("einfo", editInfo);
				if ("C".equals(request.getParameter("type"))) {
					dispatch = "./jsp/cashbook/voucher/ContraVoucherEdit.jsp";
				} else {
					dispatch = "./jsp/cashbook/voucher/VoucherEdit.jsp";
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}

		} else if ("updateVoucher".equals(request.getParameter("method"))) {
			info.setKeyNo(request.getParameter("keyNo"));
			info.setAccountNo(request.getParameter("accountNo"));
			info.setFinYear(request.getParameter("year"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setDetails(request.getParameter("voucherDetails"));
			info.setVoucherType(request.getParameter("vouchertype"));
			info.setTransactionType(request.getParameter("transType"));
			String detailRecords[] = request
					.getParameterValues("detailRecords");
			int length = detailRecords.length;
			List voucherDts = new ArrayList();
			VoucherDetails voucherDt = null;
			for (int i = 0; i < length; i++) {
				StringTokenizer st = new StringTokenizer(detailRecords[i], "|");
				if (st.hasMoreTokens()) {
					voucherDt = new VoucherDetails();
					voucherDt.setAccountHead(st.nextToken());
					voucherDt.setMonthYear(st.nextToken());
					voucherDt.setDetails(st.nextToken());
					voucherDt.setChequeNo(st.nextToken());
					voucherDt.setDebit(Double.parseDouble(st.nextToken()));
					voucherDt.setCredit(Double.parseDouble(st.nextToken()));
				}
				voucherDts.add(voucherDt);
			}
			info.setVoucherDetails(voucherDts);
			if (!"C".equals(info.getVoucherType())) {
				info.setPartyType(request.getParameter("party"));
				if ("E".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("epfid"));
				} else if ("P".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("pName"));
				}
			} else {
				info.setPartyType("B");
				info.setEmpPartyCode(request.getParameter("contraAccountNo"));
			}
			try {
				service.updateVoucherRecord(info);
				redirect = "./Voucher?method=searchRecords&&accountNo="
						+ info.getAccountNo() + "&&voucherType="
						+ request.getParameter("vouchertype");
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("deleteVoucher".equals(request.getParameter("method"))) {
			String error = null;
			try {
				String keynos = request.getParameter("keynos");
				String codes = keynos.substring(0, keynos.length() - 1);
				service.deleteVoucher(codes);
				HttpSession session = request.getSession();
				UserTracking.write((String) session.getAttribute("userid"),
						codes, "D", "CB", "", "Voucher");
			} catch (Exception e) {
				e.printStackTrace();
				error = "Record(s) can not be deleted";
			}
			info.setKeyNo("");
			List dataList;
			try {
				dataList = service.searchRecords(info, "A");
				request.setAttribute("dataList", dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispatch = "./jsp/cashbook/voucher/VoucherApproval.jsp?error="
					+ error;
		} else if ("addContraRecord".equals(request.getParameter("method"))) {
			HttpSession session = request.getSession();
			info.setAccountNo(request.getParameter("accountNo"));
			info.setFinYear(request.getParameter("year"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setVoucherType("C");
			info.setEnteredBy((String) session.getAttribute("userid"));
			info.setPreparedBy((String) session.getAttribute("userid"));
			info.setDetails(request.getParameter("voucherDetails"));
			info.setPreparedDt(request.getParameter("prepDate"));
			VoucherDetails voucherDt = new VoucherDetails();
			voucherDt.setAccountHead(request.getParameter("accHead"));
			voucherDt.setMonthYear(request.getParameter("monthYear"));
			voucherDt.setDetails(request.getParameter("details"));
			voucherDt.setChequeNo(request.getParameter("chequeNo"));
			voucherDt.setCredit(Double.parseDouble(request.getParameter("amount")));
			List voucherDts = new ArrayList();
			voucherDts.add(voucherDt);
			info.setVoucherDetails(voucherDts);
			info.setPartyType("B");
			info.setEmpPartyCode(request.getParameter("contraAccountNo"));
			try {
				String keyNo = service.addVoucherRecord(info);
				redirect = "./Voucher?method=searchRecords&&accountNo="
						+ info.getAccountNo() + "&&voucherType=C"+ "&&keyNo=" + keyNo;
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getLedgerAccount".equals(request.getParameter("method"))) {
			info.setFromDate(request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			info.setBankCode(request.getParameter("bankCode"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setAccountHead(request.getParameter("accountCode"));
			info.setVoucherType(request.getParameter("voucherType"));
			try {
				List dataList = service.getLedgerAccount(info);
				request.setAttribute("book", dataList);
				dispatch = "./jsp/cashbook/voucher/LedgerAccount.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate();
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("updateContraRecord".equals(request.getParameter("method"))) {
			HttpSession session = request.getSession();
			info.setKeyNo(request.getParameter("keyNo"));
			info.setAccountNo(request.getParameter("accountNo"));
			info.setFinYear(request.getParameter("year"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setVoucherType("C");
			info.setEnteredBy((String) session.getAttribute("userid"));
			info.setPreparedBy((String) session.getAttribute("userid"));
			info.setDetails(request.getParameter("voucherDetails"));
			info.setPreparedDt(request.getParameter("prepDate"));
			VoucherDetails voucherDt = null;
			voucherDt = new VoucherDetails();
			voucherDt.setAccountHead(request.getParameter("accHead"));
			voucherDt.setMonthYear(request.getParameter("monthYear"));
			voucherDt.setDetails(request.getParameter("details"));
			voucherDt.setChequeNo(request.getParameter("chequeNo"));
			voucherDt.setDebit(Double.parseDouble(request
					.getParameter("amount")));
			List voucherDts = new ArrayList();
			voucherDts.add(voucherDt);
			info.setVoucherDetails(voucherDts);
			info.setPartyType("B");
			info.setEmpPartyCode(request.getParameter("contraAccountNo"));
			try {
				service.updateVoucherRecord(info);
				redirect = "./Voucher?method=searchRecords&&accountNo="
						+ info.getAccountNo() + "&&voucherType="
						+ request.getParameter("vouchertype");
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}else if ("getGenLedger".equals(request.getParameter("method"))) {
			info.setFromDate(request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setAccountHead(request.getParameter("accHead"));
			info.setVoucherType(request.getParameter("voucherType"));			
			try {
				Map ledger = service.getGenLedger(info);
				Map accounts = service.getAccountHeadsOpeningBalance(info);
				request.setAttribute("ledger", ledger);
				request.setAttribute("accounts",accounts );
				if("html".equals(request.getParameter("reportType"))){
					dispatch = "./jsp/cashbook/generalledger/GenLedgerAccount.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()
						+"&&reportContent="+request.getParameter("reportContent")
						+"&&reportbal="+request.getParameter("reportbal");
				}else{
					dispatch = "./jsp/cashbook/generalledger/GenLedgerAccountExcel.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()
						+"&&reportContent="+request.getParameter("reportContent")
						+"&&reportbal="+request.getParameter("reportbal");
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}else if ("getTrialBalance".equals(request.getParameter("method"))) {
			info.setFromDate(request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			info.setTrustType(request.getParameter("trusttype"));
			String reportType = request.getParameter("reportType");
			try {
				Map trialbalance = service.getTrialBalance(info);
				request.setAttribute("trailBalance", trialbalance);
				if("html".equals(request.getParameter("report"))){
					dispatch = "./jsp/cashbook/trialbalance/TrialBalance.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&reportType="+reportType;
				}else {
					dispatch = "./jsp/cashbook/trialbalance/TrialBalanceExcel.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&reportType="+reportType;
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}else if ("ePaymentSearch".equals(request.getParameter("method"))) {
			info.setFromDate(request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			try {
				List voucherList = service.ePaymentSearch(info);
				request.setAttribute("voucherList", voucherList);
				dispatch = "./jsp/cashbook/voucher/EPaymentTransfersParam.jsp";
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}else if ("ePaymentTransfers".equals(request.getParameter("method"))) {
			String keynos = request.getParameter("keynos");
			try {
				service.ePaymentTransfers(keynos);
				dispatch = "./Voucher?method=ePaymentSearch";
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}
		log.info("Voucher : service : Leaving Method");
		if (redirect != null) {
			response.sendRedirect(redirect);
		} else if (dispatch != null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatch);
			rd.forward(request, response);
		}
	}
	
	public ActionForward unreconcileParam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		ActionForward forward = null;	
		
		forward = mapping.findForward("unreconcileParam");	
		try {
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return forward;
	}
	
	public ActionForward bankReconciliation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ActionForward forward=null;
		forward=mapping.findForward("bankreconcileStatement");
		try{
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return forward;
	}
	
	public ActionForward bankreconcileReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ActionForward forward=null;
		
		VoucherInfo info=new VoucherInfo();
		BankBook book=new BankBook();
		String voucherDt=StringUtility.checknull(request.getParameter("voucherDt"));	
		String accountNo =StringUtility.checknull(request.getParameter("accountNumber"));
		
		ActionMessages errors = new ActionMessages();
		forward = mapping.findForward("bankreconcileReport");
		try {
			info=VoucherService.getInstance().bankreconcileReport(voucherDt,accountNo);
			book=VoucherService.getInstance().getBankReconcileOpenBalance(accountNo,voucherDt);
			request.setAttribute("bankBook",book);
			request.setAttribute("voucherbean",info);
			
		} catch (Exception e) {
			errors.add("Report",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		
		
		return forward;
	}
	
	public ActionForward unreconcileVouchers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		List voucherlist = new ArrayList();	
		String voucherDt=dyna.getString("voucherDt");	
		String accountNo = dyna.getString("accountNo");
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward("unreconcileVouchers");
		try {
			voucherlist=VoucherService.getInstance().unreconcileVouchers(voucherDt,accountNo);
			request.setAttribute("voucherlist",voucherlist);
			
		} catch (Exception e) {
			errors.add("Report",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		
		return forward;
	}

}
