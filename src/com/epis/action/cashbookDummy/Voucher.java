package com.epis.action.cashbookDummy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbookDummy.BankBook;
import com.epis.bean.cashbookDummy.VoucherDetails;
import com.epis.bean.cashbookDummy.VoucherInfo;
import com.epis.dao.admin.UserDAO;
import com.epis.service.cashbookDummy.VoucherService;
import com.epis.services.cashbook.NotificationService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

public class Voucher extends HttpServlet {

	Log log = new Log(Voucher.class);
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		VoucherInfo info = new VoucherInfo(request);
		VoucherService service = new VoucherService();
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
			info.setDesignation(request.getParameter("edesignation"));
			info.setTransactionId(request.getParameter("transID"));
			info.setOtherTransactionType(request.getParameter("otherTransType"));
			info.setPurpose(StringUtility.checknull(request.getParameter("purpose")));
			info.setAppStatus(StringUtility.checknull(request.getParameter("appStatus")));
			if(!StringUtility.checknull(info.getOtherTransactionType()).equals(""))
			{
				info.setDetails(info.getDetails()+" "+StringUtility.checknull(request.getParameter("narration")));
			}
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
					voucherDt.setRegard("");
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
					info.setEmpUnitName(request.getParameter("unitName"));					
					log.info("pfid flag" + info.getPfidFlag());

				}
				if("I".equals(info.getPartyType()))
				{
					info.setEmpPartyCode(request.getParameter("pName"));
					info.setISIN(request.getParameter("isin"));
				}
				if("C".equals(info.getPartyType()))
				{
					info.setEmpPartyCode(request.getParameter("pName"));
				}
				else if ("P".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("pName"));
				}
			} else {
				info.setPartyType("B");
				info.setEmpPartyCode(request.getParameter("contraAccountNo"));
			}
			try {
				String keyNo = service.addVoucherRecord(info);
				if(info.getOtherTransactionType() !=null && !"".equals(info.getOtherTransactionType())){
					redirect = "/Notifications.do?method=getNotifications";
				}else {
					redirect = "./jsp/cashbook/voucher/Voucher.jsp?accountNo="
						+ info.getAccountNo() + "&&type="
						+ info.getVoucherType() + "&&keyNo=" + keyNo+"&&bankName="+info.getBankName();
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("searchRecords".equals(request.getParameter("method"))) {
			info.setBankName(request.getParameter("bankName") == null ? ""
					: request.getParameter("bankName"));
			info.setFinYear(request.getParameter("year") == null ? "" : request.getParameter("year"));
			info.setVoucherType(request.getParameter("voucherType") == null ? ""
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
			info.setEmpPartyCode(request.getParameter("partyName") == null ? ""
					: request.getParameter("partyName"));
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
					String voucherdate=service.getappVoucherDate();
					request.setAttribute("voucherappdate",voucherdate);
					request.setAttribute("book", book);
					dispatch = "./jsp/cashbook/voucher/ApprovedVoucher.jsp";
				} else if ("Contra".equals(info.getVoucherType())) {
					dispatch = "./jsp/cashbook/voucher/ContraVoucherReport.jsp";
				}	else {
					dispatch = "./jsp/cashbook/voucher/VoucherReport.jsp";
				}
				UserBean user = UserDAO.getInstance().getUser(info.getApprovedBy());
				String path = request.getSession().getServletContext().getRealPath("/");
				path +="uploads/SIGNATORY/"+user.getEsignatoryName();
				UserDAO.getInstance().getImage(path,user.getUserName());
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}
		else if("getMultipleVouchers".equals(request.getParameter("method")))
		{
					
					try{
					info.setFromVoucherNo(request.getParameter("fromvoucherNo"));
					info.setToVoucherNo(request.getParameter("tovoucherNo"));
					info.setFinYear(request.getParameter("finyear"));
					info.setVoucherType(request.getParameter("voucherType"));
					info.setAccountNo(request.getParameter("accountNo"));
					String path=request.getSession().getServletContext().getRealPath("/");
					info.setPath(path);
					List multiplevouchers=service.getMultipleVoucherData(info);
					request.setAttribute("multiplevouchers",multiplevouchers);
					if("C".equals(info.getVoucherType())) {
							dispatch = "./jsp/cashbook/voucher/ContraMultipleVoucherReport.jsp";
						}	else {
							dispatch = "./jsp/cashbook/voucher/MultipleVoucherReport.jsp";
						}
					}
					catch(Exception e)
					{
						log.printStackTrace(e);
					}
					
				}
		else if ("getVoucherAppRecords"
				.equals(request.getParameter("method"))) {
			HttpSession session=request.getSession();
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
				if (session.getAttribute("typeapp") != null
						&& "first".equals(session.getAttribute("typeapp"))) {
					dispatch += "?&&keyNo="
							+ (request.getParameter("keyNo") == null ? ""
									: request.getParameter("keyNo"));
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getVoucherApproval".equals(request.getParameter("method"))) {
			HttpSession session=request.getSession();
			try {
				info.setVoucherDt(request.getParameter("voucherDate"));
				info.setKeyNo(request.getParameter("keyNo"));
				info.setCheckedBy(request.getParameter("checkedby"));
				info.setApprovedBy(request.getParameter("approved"));
				info.setStatus(request.getParameter("approvalstatus"));
				info.setVtype(request.getParameter("vtype"));
				service.updateApprovalVoucher(info);
				session.setAttribute("typeapp","first");
				redirect = "./Voucher?method=getVoucherAppRecords&&keyNo="
						+ info.getKeyNo() + "&voucherType="+info.getVtype()+"&&type=app";
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

		}else if ("editNotification".equals(request.getParameter("method"))) {

			info.setKeyNo(request.getParameter("keyNo"));
			try {
				VoucherInfo editInfo = service.getReport(info);
				request.setAttribute("einfo", editInfo);
				request.setAttribute("notifications", NotificationService.getInstance().getNotifications(editInfo));
				dispatch = "./jsp/cashbook/voucher/VoucherMapNotification.jsp";
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
			info.setPreparedDt(request.getParameter("prepDate"));
			String detailRecords[] = request
					.getParameterValues("detailRecords");
			VoucherDetails voucherDt = null;
			List voucherDts = new ArrayList();
			if(detailRecords != null){
				int length = detailRecords.length;
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
						voucherDt.setRegard("");
					}
					voucherDts.add(voucherDt);
				}
			}
			info.setVoucherDetails(voucherDts);
			if (!"C".equals(info.getVoucherType())) {
				info.setPartyType(request.getParameter("party"));
				if ("E".equals(info.getPartyType())) {
					info.setEmpPartyCode(request.getParameter("epfid"));
				}
				if("I".equals(info.getPartyType()))
				{
					
					info.setEmpPartyCode(request.getParameter("pName"));
					info.setISIN(request.getParameter("isin"));
					log.info("<<<<<<<<<<<<<<<<:"+info.getEmpPartyCode()+info.getISIN());
				}
				if("C".equals(info.getPartyType()))
				{
					info.setEmpPartyCode(request.getParameter("pName"));
				}
				else if ("P".equals(info.getPartyType())) {
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
			info.setOtherTransactionType(request.getParameter("otherTransType"));
			info.setAppStatus(StringUtility.checknull(request.getParameter("appStatus")));
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
			voucherDt.setCredit(Double.parseDouble(request
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
			info.setFromDate("".equals(StringUtility.checknull(request.getParameter("fromDate")))?"01/Apr/2010":request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setAccountHead(request.getParameter("accHead"));
			info.setVoucherType(request.getParameter("voucherType"));
			info.setToAccountHead(request.getParameter("toaccHead"));
			try {
				Map ledger = service.getGenLedger(info);
				Map accounts = service.getAccountHeadsOpeningBalance(info);
				request.setAttribute("ledger", ledger);
				request.setAttribute("accounts",accounts );
				if("html".equals(request.getParameter("reportType"))){
					dispatch = "./jsp/cashbook/generalledger/GenLedgerAccount.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()
						+"&&reportContent="+request.getParameter("reportContent")
						+"&&reportbal="+request.getParameter("reportbal")+"&zerotransaction="+StringUtility.checknull(request.getParameter("zerotransaction"));
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
			info.setFromDate("".equals(StringUtility.checknull(request.getParameter("fromDate")))?"01/Apr/2010":request.getParameter("fromDate"));
			info.setToDate(request.getParameter("toDate"));
			info.setTrustType(request.getParameter("trusttype"));
			String reportType = request.getParameter("reportType");
			String grouping = request.getParameter("grouping");
			try {
				Map trialbalance = service.getTrialBalance(info);
				request.setAttribute("trailBalance", trialbalance);
				if("html".equals(request.getParameter("report"))){
					dispatch = "./jsp/cashbook/trialbalance/TrialBalance.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&reportType="+reportType
						+"&&grouping="+grouping;
				}else {
					dispatch = "./jsp/cashbook/trialbalance/TrialBalanceExcel.jsp?fromDate"
						+ info.getFromDate() + "&&toDate=" + info.getToDate()+"&&reportType="+reportType
						+"&&grouping="+grouping;
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
		}else if ("updateNotiVoucher".equals(request.getParameter("method"))) {
			String transid = request.getParameter("transid");
			String keyno = request.getParameter("keyno");
			try {
				service.updateNotiVoucher(transid,keyno);
				redirect = "./Voucher?method=searchRecords&&voucherType="
					+ request.getParameter("vouchertype");
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
}
