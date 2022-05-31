package com.epis.action.cashbookDummy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epis.bean.cashbookDummy.AccountingCodeInfo;
import com.epis.service.cashbookDummy.AccountingCodeService;
import com.epis.utilities.Log;
import com.epis.utilities.UserTracking;

public class AccountingCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log log = new Log(AccountingCode.class);	

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AccountingCodeService service = new AccountingCodeService();
		AccountingCodeInfo info = new AccountingCodeInfo();
		String error = null;
		String dispatch = null;
		String redirect = null;
		log.info("AccountingCode : service : Entering Method");
		if ("addAccountRecord".equals(request.getParameter("method"))) {
			HttpSession session = request.getSession();
			info.setAccountHead(request.getParameter("accountCode"));
			info.setParticular(request.getParameter("accountName"));
			info.setType(request.getParameter("accountType"));
			info.setDate(request.getParameter("openedDate"));
			info.setAmount(request.getParameter("amount"));
			info.setAmountType(request.getParameter("amountType"));
			info.setAmountI(request.getParameter("amountI"));
			info.setAmountTypeI(request.getParameter("amountTypeI"));
			info.setAmountN(request.getParameter("amountN"));
			info.setAmountTypeN(request.getParameter("amountTypeN"));
			info.setEnteredBy((String) session.getAttribute("userid"));
			String popUp = request.getParameter("popUp");
			try {
				if (!service.exists(info)) {
					service.addAccountRecord(info);
					if ("Y".equals(popUp)) {
						redirect = "./jsp/cashbook/accountingCode/AccountingCodePopup.jsp?redirect=Y&&accHead="
								+ request.getParameter("accountCode")
								+ "&particular="
								+ request.getParameter("accountName");
					} else {
						redirect = "./AccountingCode?method=searchRecords&&accountCode="
								+ info.getAccountHead();
					}
				} else {
					error = " Record Already Exists. ";
					if ("Y".equals(popUp)) {
						dispatch = "./jsp/cashbook/accountingCode/AccountingCodePopup.jsp?error="
								+ error;
					} else {
						dispatch = "./jsp/cashbook/accountingCode/AccountingCode.jsp?error="
								+ error;
					}
				}
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("getAccountList".equals(request.getParameter("method"))) {
			info.setParticular(request.getParameter("particular"));
			info.setAccountHead(request.getParameter("AccHead"));
			String type = request.getParameter("type");
			List dataList = null;
			try {
				dataList = service.getAccountList(info, type);
				request.setAttribute("AccList", dataList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			if("ajax".equals(type)){
				StringBuffer sb = new StringBuffer("<ServiceTypes>");
				int listSize = dataList.size();
				for (int cnt = 0; cnt < listSize; cnt++) {
					info = (AccountingCodeInfo) dataList.get(cnt);
					sb.append("<ServiceType><CODE>").append(
							info.getAccountHead()).append("</CODE>");
					sb.append("<NAME>").append(info.getParticular())
							.append("</NAME>");
					sb.append("<TYPE>").append(info.getType())
							.append("</TYPE></ServiceType>");
				}
				sb.append("</ServiceTypes>");
				String ajaxString = sb.toString().replaceAll("&","\\$");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.print(ajaxString);
			}else {
				dispatch = "./jsp/cashbook/accountingCode/AccountInfo.jsp";
			}
		} else if ("searchRecords".equals(request.getParameter("method"))) {
			info.setAccountHead(request.getParameter("accountCode"));
			info.setParticular(request.getParameter("accountName"));
			info.setType(request.getParameter("accountType"));
			try {
				List dataList = service.getAccountList(info, "");
				request.setAttribute("dataList", dataList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			dispatch = "./jsp/cashbook/accountingCode/AccountingCodeSearch.jsp";
		}

		else if ("editAccountRecord".equals(request.getParameter("method"))) {
			info.setAccountHead(request.getParameter("accHead"));
			try {
				ArrayList editList = (ArrayList) service.getAccountList(info,
						"edit");
				Iterator it = editList.iterator();
				while (it.hasNext()) {
					info = (AccountingCodeInfo) it.next();
				}
				request.setAttribute("einfo", info);
				dispatch = "./jsp/cashbook/accountingCode/AccountingEdit.jsp";

			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("updateAccountRecord".equals(request.getParameter("method"))) {

			try {
				info.setAccountHead(request.getParameter("accountCode"));
				info.setParticular(request.getParameter("accountName"));
				info.setType(request.getParameter("accountType"));
				info.setDate(request.getParameter("openedDate"));
				info.setAmount(request.getParameter("amount"));
				info.setAmountType(request.getParameter("amountType"));
				info.setAmountI(request.getParameter("amountI"));
				info.setAmountTypeI(request.getParameter("amountTypeI"));
				info.setAmountN(request.getParameter("amountN"));
				info.setAmountTypeN(request.getParameter("amountTypeN"));
				service.updateAccountRecord(info);
				info.setAccountHead("");
				info.setParticular("");
				info.setType("");
				List dataList = service.getAccountList(info, "");
				request.setAttribute("dataList", dataList);
				redirect = "./AccountingCode?method=searchRecords&&accountCode="
						+ request.getParameter("accountCode");

			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("deleteAccountRecord".equals(request.getParameter("method"))) {

			try {
				String accHeads = request.getParameter("accHeads");
				String codes = accHeads.substring(0, accHeads.length() - 1);
				service.deleteAccountRecord(codes);
				HttpSession session = request.getSession();	
				UserTracking.write((String)session.getAttribute("userid"),codes,"D","CB","","Accounting Code");
			} catch (Exception e) {
				error = "Record(s) can not be deleted";
			}
			info.setAccountHead("");
			info.setParticular("");
			info.setType("");
			List dataList;
			try {
				dataList = service.getAccountList(info, "");
				request.setAttribute("dataList", dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}

			dispatch = "./jsp/cashbook/accountingCode/AccountingCodeSearch.jsp?error="
					+ error;
		}
		log.info("AccountingCode : service : Leaving Method");
		if (redirect != null) {
			response.sendRedirect(redirect);
		} else if (dispatch != null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatch);
			rd.forward(request, response);
		}
	}
}
