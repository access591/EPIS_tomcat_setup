package com.epis.action.cashbookDummy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epis.bean.cashbookDummy.BankMasterInfo;
import com.epis.bean.cashbookDummy.PartyInfo;
import com.epis.service.cashbookDummy.PartyService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

public class Party extends HttpServlet{

	Log log = new Log(Party.class);
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {		
		PartyService service = new PartyService();
		PartyInfo info = new PartyInfo();
		log.info("Party : service : Entering Method");
		String error = null;
		String dispatch = null;
		String redirect = null;
		String accounts =null;
		if ("getPartyList".equals(request.getParameter("method"))) {
			String pName = request.getParameter("pName");
			String mType=StringUtility.checknull(request.getParameter("mType"));
			info.setModuleType(mType);
			info.setPartyName(pName==null?"":pName.trim());
			try {
				List dataList = service.getPartyList(info,"");				
				request.setAttribute("PartyList",dataList);
				dispatch = "./jsp/cashbook/party/PartyInfo.jsp";				
			} catch (Exception e) {
				log.printStackTrace(e);
			}
		}else if ("addPartyRecord".equals(request.getParameter("method"))) {
			info.setPartyName(request.getParameter("partyName"));
			info.setPartyDetail(request.getParameter("partyDetail"));
			info.setMobileNo(request.getParameter("mobileNo"));
			info.setFaxNo(request.getParameter("faxNo"));
			info.setContactNo(request.getParameter("contactNo"));
			info.setEmailId(request.getParameter("email"));
			HttpSession session = request.getSession();
			info.setEnteredBy((String)session.getAttribute("userid"));
			if(request.getParameterValues("detailRecords")!=null)
			{
			String detailRecords[] = request
					.getParameterValues("detailRecords");
			int length = detailRecords.length;
			List bankInfo = new ArrayList();
			BankMasterInfo bInfo = null;
			
			for (int i = 0; i < length; i++) {
				StringTokenizer st = new StringTokenizer(detailRecords[i], "|");
				if (st.hasMoreTokens()) {
					bInfo = new BankMasterInfo();
					bInfo.setBankCode(st.nextToken());
					bInfo.setAccountNo(st.nextToken());
					bInfo.setIFSCCode(st.nextToken());
					bInfo.setNEFTRTGSCode(st.nextToken());
					bInfo.setMICRNo(st.nextToken());
					bInfo.setBankName(st.nextToken());
					bInfo.setBranchName(st.nextToken());
					bInfo.setAddress(st.nextToken());
					bInfo.setPhoneNo(st.nextToken());
					bInfo.setFaxNo(st.nextToken());
					bInfo.setAccountType(st.nextToken());					
					bInfo.setContactPerson(st.nextToken());
					bInfo.setMobileNo(st.nextToken());
					accounts +="|"+ bInfo.getAccountNo().toUpperCase()+"|";
				}
				bankInfo.add(bInfo);
			}
			info.setBankInfo(bankInfo);
			
			try {
				if(!service.exists(info,accounts.substring(0,accounts.length()-1))){
					
					service.addPartyRecord(info);
					redirect = "./Party?method=searchRecords&&partyName="+info.getPartyName().replaceAll("%","~");
				}else {
					error = " Record Already Exists With Party Name or Account No. ";
					dispatch = "./jsp/cashbook/party/PartyMaster.jsp?error="+error;
				}				
			} catch (Exception e) {
				log.printStackTrace(e);
				if(e.getMessage().indexOf("CHECK_ACCNO")>0){
					dispatch = "./jsp/cashbook/party/PartyMaster.jsp?error=Account Details Exists in Bank Master";
				}
				
	    }
	 }else
		 try{
		 service.addPartyRecord(info);
		 redirect = "./Party?method=searchRecords&&partyName="+info.getPartyName().replaceAll("%","~");
		 }catch(Exception e){
			 log.printStackTrace(e); 
		 }
			
		}else if("searchRecords".equals(request.getParameter("method"))) {
			info.setPartyName(request.getParameter("partyName")==null?"":request.getParameter("partyName").replaceAll("~","%"));
			
			try{
				List dataList = service.getPartyList(info,"");
				request.setAttribute("dataList",dataList);
			}catch (Exception e) {
				log.printStackTrace(e);
			}			
			dispatch = "./jsp/cashbook/party/PartyMasterSearch.jsp";
		}else if ("editPartyRecord".equals(request.getParameter("method"))) {
			 info.setPartyCode( request.getParameter("pCode"));			 
				try {
					ArrayList editList = (ArrayList)service.getPartyList(info,"edit");
					Iterator it = editList.iterator();
					while (it.hasNext()) {
						info= (PartyInfo)it.next();
					}
					request.setAttribute("einfo",info);
					dispatch = "./jsp/cashbook/party/PartyEdit.jsp";
					
				} catch (Exception e) {
					log.printStackTrace(e);
				}						
			}else if ("updatePartyRecord".equals(request.getParameter("method"))) {
			    
				try {

					info.setPartyCode(request.getParameter("partyCode"));
					info.setPartyName(request.getParameter("partyName").replaceAll("~","%"));
					info.setPartyDetail(request.getParameter("partyDetail"));
					info.setMobileNo(request.getParameter("mobileNo"));
					info.setFaxNo(request.getParameter("faxNo"));
					info.setContactNo(request.getParameter("contactNo"));
					info.setEmailId(request.getParameter("email"));
					HttpSession session = request.getSession();
					info.setEnteredBy((String)session.getAttribute("userid"));
					if(request.getParameterValues("detailRecords")!=null)
					{
					String detailRecords[] = request
							.getParameterValues("detailRecords");
					int length = detailRecords.length;
					List bankInfo = new ArrayList();
					BankMasterInfo bInfo = null;
					for (int i = 0; i < length; i++) {
						StringTokenizer st = new StringTokenizer(detailRecords[i], "|");
						if (st.hasMoreTokens()) {
							bInfo = new BankMasterInfo();
							bInfo.setBankCode(st.nextToken());
							bInfo.setAccountNo(st.nextToken());
							bInfo.setIFSCCode(st.nextToken());
							bInfo.setNEFTRTGSCode(st.nextToken());
							bInfo.setMICRNo(st.nextToken());
							bInfo.setBankName(st.nextToken());
							bInfo.setBranchName(st.nextToken());
							bInfo.setAddress(st.nextToken());
							bInfo.setPhoneNo(st.nextToken());
							bInfo.setFaxNo(st.nextToken());
							bInfo.setAccountType(st.nextToken());					
							bInfo.setContactPerson(st.nextToken());
							bInfo.setMobileNo(st.nextToken());
							accounts +="|"+ bInfo.getAccountNo().toUpperCase()+"|";
						}
						bankInfo.add(bInfo);
					}
					
					info.setBankInfo(bankInfo);
					service.updatePartyRecord(info);
					//List dataList = service.getPartyList(info,"");
					//request.setAttribute("dataList",dataList);
					redirect = "./Party?method=searchRecords&&partyName="+request.getParameter("partyName");
					}else
						 try{
							 service.updatePartyRecord(info);
							 redirect = "./Party?method=searchRecords&&partyName="+request.getParameter("partyName");
							 }catch(Exception e){
								 log.printStackTrace(e); 
				 }
				} catch (Exception e) {
					log.printStackTrace(e);
					if(e.getMessage().indexOf("CHECK_ACCNO")>0){
						dispatch = "./jsp/cashbook/party/PartyEdit.jsp?error=Account Details Exists in Bank Master";
						request.setAttribute("einfo",info);
					}
				}
				
			}else if ("deletePartyRecord".equals(request.getParameter("method"))) {
			    
				try {
					 String partyCode = request.getParameter("partyCode");
					 String codes = partyCode.substring(0,partyCode.length()-1);
					 service.deletePartyRecord(codes);
					 HttpSession session = request.getSession();	
					 UserTracking.write((String)session.getAttribute("userid"),codes,"D","CB","","Party Master");
				} catch (Exception e) {
					error = "Record(s) can not be deleted";
				}	
				info.setPartyName("");
				List dataList;
				try {
					dataList = service.getPartyList(info,"");
					request.setAttribute("dataList",dataList);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				dispatch = "./jsp/cashbook/party/PartyMasterSearch.jsp?error="+error;
			}
		log.info("Party : service : Leaving Method");
		if(redirect !=null){
			response.sendRedirect(redirect);
		}else if(dispatch !=null){
			RequestDispatcher rd = request.getRequestDispatcher(dispatch);
			rd.forward(request,response);
		}
	}
}
