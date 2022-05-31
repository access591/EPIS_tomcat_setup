package com.epis.action.cashbookDummy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epis.bean.cashbookDummy.BankMasterInfo;
import com.epis.service.cashbookDummy.BankMasterService;
import com.epis.utilities.Log;
import com.epis.utilities.UserTracking;

public class BankMaster extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log log = new Log(BankMaster.class);	

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("BankMaster : service : Entering Method");
		BankMasterService service = new BankMasterService();		
		String error = null;
		String dispatch = null;
		String redirect = null;
		if ("addBankRecord".equals(request.getParameter("method"))) {
			HttpSession session = request.getSession();
			BankMasterInfo info = new BankMasterInfo();
			info.setBankName(request.getParameter("bankName"));
			info.setBranchName(request.getParameter("branchName"));
			info.setBankCode(request.getParameter("bankCode"));
			info.setPhoneNo(request.getParameter("phoneNo"));
			info.setFaxNo(request.getParameter("faxNo"));
			info.setAccountCode(request.getParameter("accountCode"));
			info.setAccountNo(request.getParameter("accountNo"));
			info.setIFSCCode(request.getParameter("ifscCode"));
			info.setNEFTRTGSCode(request.getParameter("neftCode"));
			info.setMICRNo(request.getParameter("micrNo"));
			info.setContactPerson(request.getParameter("contactPerson"));
			info.setMobileNo(request.getParameter("mobileNo"));
			info.setAddress(request.getParameter("address"));
			info.setEnteredBy((String) session.getAttribute("userid"));
			info.setAccountType(request.getParameter("accountType"));
			info.setTrustType(request.getParameter("trusttype"));
			info.setRegion(request.getParameter("region"));
			info.setUnitName(request.getParameter("unitName"));
			try {
				if (!service.exists(info)) {
					service.addBankRecord(info);
					redirect = "./BankMaster?method=searchRecords&&bankacno="
							+ request.getParameter("accountNo");
				} else {	
					List dataList = null;
					try {
						dataList = service.getRegions();
						request.setAttribute("regions",dataList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					error = " Record Already Exists. ";
					dispatch = "./jsp/cashbook/bank/BankMaster.jsp?error="
							+ error;
				}
			} catch (Exception e) {
				log.printStackTrace(e);
				List dataList = null;
				try {
					dataList = service.getRegions();
					request.setAttribute("regions",dataList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (e.getMessage().indexOf("CHECK_PACCNO") > 0) {
					dispatch = "./jsp/cashbook/bank/BankMaster.jsp?error=Account Details Exists in Party Master";
				} else {
					dispatch = "./jsp/cashbook/bank/BankMaster.jsp?error="
							+ e.getMessage();
				}
			}
		} else if ("getBankList".equals(request.getParameter("method"))) {
			String rem = request.getParameter("type");
			BankMasterInfo info = new BankMasterInfo();
			info.setAccountNo(request.getParameter("accountNo"));
			info.setBankName(request.getParameter("bankName"));
			info.setTrustType(request.getParameter("trusttype"));
			List dataList = null;
			try {
				dataList = service.getBankList(info, rem);
				request.setAttribute("BankList", dataList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			if ("ajax".equals(rem)) {
				StringBuffer sb = new StringBuffer("<ServiceTypes>");
				int listSize = dataList.size();
				for (int cnt = 0; cnt < listSize; cnt++) {
					info = (BankMasterInfo) dataList.get(cnt);
					sb.append("<ServiceType><bankName>").append(
							info.getBankName()).append("</bankName>");
					sb.append("<accountNo>").append(info.getAccountNo())
							.append("</accountNo>");
					sb.append("<accHead>").append(info.getAccountCode())
							.append("</accHead>");
					sb.append("<particular>").append(info.getParticular())
							.append("</particular>");
					sb.append("<trustType>").append(info.getTrustType())
							.append("</trustType></ServiceType>");
				}
				sb.append("</ServiceTypes>");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.print(sb);

			} else {
				dispatch = "./jsp/cashbook/bank/BankInfo.jsp";
			}
		} else if ("searchRecords".equals(request.getParameter("method"))) {
			BankMasterInfo info = new BankMasterInfo();
			info.setBankName(request.getParameter("bankname") == null ? ""
					: request.getParameter("bankname"));
			info.setBranchName(request.getParameter("branchname") == null ? ""
					: request.getParameter("branchname"));
			info.setBankCode(request.getParameter("bankcode") == null ? ""
					: request.getParameter("bankcode"));
			info.setAccountNo(request.getParameter("bankacno") == null ? ""
					: request.getParameter("bankacno"));
			try {
				List dataList = service.getBankList(info, "");
				request.setAttribute("BankList", dataList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			dispatch = "./jsp/cashbook/bank/BankMasterSearch.jsp";
		} else if ("editBankRecord".equals(request.getParameter("method"))) {
			BankMasterInfo info = new BankMasterInfo();
			info.setAccountNo(request.getParameter("accNo"));
			try {
				ArrayList editList = (ArrayList) service.getBankList(info,
						"edit");
				Iterator it = editList.iterator();
				while (it.hasNext()) {
					info = (BankMasterInfo) it.next();
				}
				request.setAttribute("binfo", info);
				List dataList = null;
				try {
					dataList = service.getRegions();
					request.setAttribute("regions",dataList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dispatch = "./jsp/cashbook/bank/BankMasterEdit.jsp";

			} catch (Exception e) {
				log.printStackTrace(e);
			}
		} else if ("updateBankRecord".equals(request.getParameter("method"))) {
			BankMasterInfo info = new BankMasterInfo();
			try {				
				info.setBankName(request.getParameter("bankName"));
				info.setBranchName(request.getParameter("branchName"));
				info.setBankCode(request.getParameter("bankCode"));
				info.setPhoneNo(request.getParameter("phoneNo"));
				info.setFaxNo(request.getParameter("faxNo"));
				info.setAccountCode(request.getParameter("accountCode"));
				info.setAccountNo(request.getParameter("accountNo"));
				info.setIFSCCode(request.getParameter("ifscCode"));
				info.setNEFTRTGSCode(request.getParameter("neftCode"));
				info.setMICRNo(request.getParameter("micrNo"));
				info.setContactPerson(request.getParameter("contactPerson"));
				info.setMobileNo(request.getParameter("mobileNo"));
				info.setAddress(request.getParameter("address"));
				info.setAccountType(request.getParameter("accountType"));
				info.setUnitName(request.getParameter("unitName"));
				info.setTrustType(request.getParameter("trusttype"));
				info.setRegion(request.getParameter("region"));
				service.updateBankRecord(info);
				info.setBankName("");
				info.setBranchName("");
				info.setBankCode("");
				info.setAccountNo("");

				List dataList = service.getBankList(info, "");
				request.setAttribute("BankList", dataList);
				redirect = "./BankMaster?method=searchRecords&&bankacno="
						+ request.getParameter("accountNo");

			} catch (Exception e) {
				log.printStackTrace(e);
				List dataList = null;
				try {
					dataList = service.getRegions();
					request.setAttribute("regions",dataList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (e.getMessage().indexOf("CHECK_PACCNO") > 0) {
					dispatch = "./jsp/cashbook/bank/BankMasterEdit.jsp?error=Account Details Exists in Party Master";
				} else {
					dispatch = "./jsp/cashbook/bank/BankMasterEdit.jsp?error="
							+ e.getMessage();
				}
				request.setAttribute("binfo", info);
			}
		} else if ("deleteBankRecord".equals(request.getParameter("method"))) {
			BankMasterInfo info = new BankMasterInfo();
			String codes = "";
			try {
				String accNo = request.getParameter("accNo");
				codes = accNo.substring(0, accNo.length() - 1);
				service.deleteBankRecord(codes);
				HttpSession session = request.getSession();	
				UserTracking.write((String)session.getAttribute("userid"),codes,"D","CB","","Bank Master");
			} catch (Exception e) {
				error = "Record(s) can not be deleted";
			}
			List dataList;
			try {
				dataList = service.getBankList(info, "");
				request.setAttribute("BankList", dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			dispatch = "./jsp/cashbook/bank/BankMasterSearch.jsp?error="
					+ error;
		}else if ("getBankDetails".equals(request.getParameter("method"))) {
			BankMasterInfo info = new BankMasterInfo();
			List dataList = null;
			try {
				info.setTrustType(request.getParameter("trusttype"));				
				dataList = service.getBankDetails(info);
				StringBuffer sb = new StringBuffer("<ServiceTypes>");
				int listSize = dataList.size();
				for (int cnt = 0; cnt < listSize; cnt++) {
					info = (BankMasterInfo) dataList.get(cnt);
					sb.append("<ServiceType><bankCode>").append(info.getBankCode())
							.append("</bankCode>");
					sb.append("<accHead>").append(info.getAccountCode())
							.append("</accHead></ServiceType>");
				}
				sb.append("</ServiceTypes>");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.print(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if ("getRegions".equals(request.getParameter("method"))) {
			List dataList = null;
			try {
				dataList = service.getRegions();
				request.setAttribute("regions",dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispatch = "./jsp/cashbook/bank/BankMaster.jsp";
		}else if ("getUnits".equals(request.getParameter("method"))) {
			Map data = null;
			try {
				data = service.getUnits(request.getParameter("region"));
				StringBuffer sb = new StringBuffer("<ServiceTypes>");
				Set s = data.keySet();
				Iterator iter = s.iterator();
				while (iter.hasNext()) {
					String unitcode = (String)iter.next();
					sb.append("<ServiceType><unitCode>").append(unitcode)
							.append("</unitCode>");
					sb.append("<unitName>").append((String)data.get(unitcode))
							.append("</unitName></ServiceType>");
				}
				sb.append("</ServiceTypes>");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.print(sb.toString());
				System.out.print("-------------------"+sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.print("-------------------"+request.getParameter("method"));
		log.info("BankMaster : service : Leaving Method");
		if (redirect != null) {
			response.sendRedirect(redirect);
		} else if (dispatch != null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatch);
			rd.forward(request, response);
		}
	}
}
