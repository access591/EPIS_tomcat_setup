/**
 * File       : AdvancesAction.java
 * Date       : 09/25/2009
 * Author     : Suresh Kumar Repaka 
 * Description: 
 * Copyright (2009) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.action.advances;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.advances.AdvanceBasicReportBean;
import com.epis.bean.advances.AdvanceCPFForm2Bean;
import com.epis.bean.advances.AdvanceEditBean;
import com.epis.bean.advances.AdvancePFWFormBean;
import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.bean.advances.CPFPFWTransBean;
import com.epis.bean.advances.EmpBankMaster;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.advances.CPFPFWTransInfo;
import com.epis.info.login.LoginInfo;
import com.epis.services.CommonService;
import com.epis.services.advances.CPFPTWAdvanceService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class AdvancesAction extends CommonAction {
	CommonUtil commonUtil = new CommonUtil();
	
	CPFPTWAdvanceService ptwAdvanceService = null;
	
	CommonService commonService = new CommonService();
	
	LoginInfo logInfo = new LoginInfo();
	
	HttpSession session = null;
	
	Log log = new Log(AdvancesAction.class);
	
	String loginUserId = "", loginUserName = "", loginUnitCode = "",
	loginRegion = "", loginProfile = "", loginSignName = "",loginUserDesignation="",loginUnitName="",loginAccountType="";
	
	public ActionForward loadStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ArrayList stationList = null;
		StringBuffer sb1 = new StringBuffer();
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		String chkFrmName="",profileName="";
		if(request.getParameter("frm_loadfrmname")!=null){
			chkFrmName=request.getParameter("frm_loadfrmname");
		}else{
			chkFrmName="N";
		}
		this.getLoginDetails(request);
		profileName = this.loginProfile;
		log.info("AdvancesAction::loadStations()------"+profileName);
		advanceBean.setProfileName(profileName);
		try {
			if(chkFrmName.equals("finsttlment")){
				advanceBean.setRegion(request.getParameter("frm_region"));
			}else{
				advanceBean.setRegion(request.getParameter("region").toString());
			} 
			
			stationList = (ArrayList) commonService.loadStations(advanceBean);
			
			sb1.append("<Stations>");
			for (int i = 0; stationList != null && i < stationList.size(); i++) {
				String station = "";
				AdvanceBasicBean bean = (AdvanceBasicBean) stationList.get(i);
				
				sb1.append("<Detail>");
				
				sb1.append("<Station>");
				if (bean.getStation() != null)
					station = bean.getStation();
				sb1.append(StringEscapeUtils.escapeXml(station));
				sb1.append("</Station>");
				
				sb1.append("</Detail>");
				
			}
			sb1.append("</Stations>");
			
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			log.info("........" + sb1.toString());
			out.write(sb1.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward loadAdvanceForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadAdvanceForm");
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList personalList = new ArrayList();
		ArrayList airportsList = new ArrayList();
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		HttpSession httpsession = request.getSession(true);
		String frmPensionNo = "", selectedRegion = "", selectedEmpName = "", cpfaccno = "";
		request.setAttribute("focusFlag", "false");
		
		if (request.getParameter("frmPensionNo") != null) {
			frmPensionNo = request.getParameter("frmPensionNo");
		} else {
			logInfo = (LoginInfo) httpsession.getAttribute("user");
			if (logInfo.getProfile().equals("M")) {
				frmPensionNo = logInfo.getPensionNo();
			} else {
				frmPensionNo = "";
			}
			
		}
		log.info("AdvancesAction::loadAdvanceForm======================"
				+ frmPensionNo+	"frmname"+request.getParameter("frm_name"));
		this.getScreenTitle(request, response, "");
		
		if (!frmPensionNo.equals("")) {
			personalList = commonService.loadPersonalTransInfo(selectedEmpName,
					selectedRegion, frmPensionNo, cpfaccno, "-Jun-2010");
			advanceBasicBean = (AdvanceBasicBean) personalList.get(0);
		}
		
		if (personalList.size() != 0) {
			this.fillPersonalForm(personalList, form);
			
		} else {
			this.fillResetPersonalForm(form);
		}
		
		if (!advanceBasicBean.getRegion().equals("")) {
			airportsList = commonUtil.getAirportList(advanceBasicBean
					.getRegion());
			
			if (airportsList.size() > 0) {
				
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		return mapping.findForward("loadAdvanceForm");
	}
//	13-Feb-2012 Radha  getting  Last Month Installment Amount
//	19-Jan-2012 Radha  getting displaying Employee Bank Details
//	08-Jun-2011 Radha Resovling report opening issue in screen level
	public ActionForward loadAdvanceForm2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadAdvanceForm");
		String pensionNo = "", transactionNo = "", returnPage = "", formType = "", transactionDate = "", frmName = "", srcFrmName="";
		AdvanceCPFForm2Bean avneFrm2Bean = new AdvanceCPFForm2Bean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster(); 
		CPFPFWTransBean transBean = new CPFPFWTransBean();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList form2List = new ArrayList();
		ArrayList transList = new ArrayList();
		ptwAdvanceService = new CPFPTWAdvanceService();
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frmTransDate") != null) {
			transactionDate = request.getParameter("frmTransDate");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		//		Notification Screen Purpose
		if (request.getParameter("srcFrmName") != null) {
			srcFrmName = request.getParameter("srcFrmName");
		} 
		request.setAttribute("srcFrmName", srcFrmName);
		try {
			form2List = ptwAdvanceService.loadCPFAdvanceForm2(pensionNo,
					transactionNo, formType, transactionDate);
			avneFrm2Bean = (AdvanceCPFForm2Bean) form2List.get(0);
			log
			.info("--------getAdvntrnsdt() in action--------"
					+ avneFrm2Bean.getAdvntrnsdt() + "formType====="
					+ formType);
			/*bankMaster = (EmpBankMaster) form2List.get(1);*/
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("reportbankMaster", bankMasterBean);
			if (formType.equals("Y")) {
				this.fillForm2Bean(avneFrm2Bean, form);
				
				dynaValide.set("interestRate", avneFrm2Bean.getInterestRate());
				dynaValide.set("advanceTransIDDec", avneFrm2Bean
						.getAdvanceTransIDDec());
				dynaValide.set("interestinstallments", avneFrm2Bean
						.getInterestinstallments());
				dynaValide.set("intinstallmentamt", avneFrm2Bean
						.getIntinstallmentamt());
				dynaValide.set("mthinstallmentamt", avneFrm2Bean
						.getMthinstallmentamt());
				dynaValide.set("lastmthinstallmentamt",avneFrm2Bean.getLastmthinstallmentamt());
				dynaValide.set("verifiedby", avneFrm2Bean
						.getVerifiedby());
				
				dynaValide.set("authorizedrmrks", avneFrm2Bean
						.getAuthrizedRemarks());
				
				dynaValide.set("authorizedsts",avneFrm2Bean.getTransStatus());
				dynaValide.set("paymentinfo",avneFrm2Bean.getPaymentinfo());
				
			 
				request.setAttribute("bankMasterBean", bankMasterBean);
				 
				returnPage = "loadform2";
			} else {
				request.setAttribute("reportBean", avneFrm2Bean);
				dynaValide.set("interestinstallments", avneFrm2Bean
						.getInterestinstallments());
				dynaValide.set("intinstallmentamt", avneFrm2Bean
						.getIntinstallmentamt());
				
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String path = getServlet().getServletContext().getRealPath("/")
				+ "/uploads/dbf/";
				
				if (frmName.equals("CPFRecommendation")) {
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_CPF_RECOMMENDATION,
							path);
				} else {
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_CPF_APPROVAL,
							path);
				}
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					
					for (int j = 0; j < transList.size(); j++) {
						transBean = (CPFPFWTransBean) transList.get(j);
						log.info("-----Code in Action-----"
								+ transBean.getTransCode());
						log.info("-----sign in Action-----"
								+ transBean.getTransDispSignName());
					}
					
					request.setAttribute("transList", transList);
				}
				
				returnPage = "cpfform2report";
				
			}
			
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			
			log.printStackTrace(e);
			returnPage = "loadform2";
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("cpfform2report");
		}
		
		if(returnPage.equals("loadform2")){
			saveToken(request);
			}
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward(returnPage);
	}
	
	public ActionForward advanceForm2Report(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean(
				request);
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		String prpsOptionsCPF = "", path = "", flag = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", transactionNo = "", rtrnPage = "", AdvStatus = "";
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		log.info("----------request.getParameter(frmAdvStatus)------------"
				+ request.getParameter("frmAdvStatus"));
		if (request.getParameter("frmAdvStatus") != null) {
			AdvStatus = request.getParameter("frmAdvStatus");
		}
		
		try {
			
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		 
			log.info("advanceReport::pensionNo" + pensionNo + "transactionNo"
					+ transactionNo + "AdvStatus" + AdvStatus);
			list = ptwAdvanceService.advanceForm2Report(pensionNo,
					transactionNo);
			log.info("Size" + list.size());
			if (list.size() != 0) {
				advanceReportBean = (AdvanceBasicReportBean) list.get(0);
				dynaValide.set("authorizedrmrks", advanceReportBean
						.getApprovedremarks());
				dynaValide.set("authorizedsts", advanceReportBean
						.getAdvtransstatus());
				dynaValide.set("takenloan", advanceReportBean.getTakenloan());
				request.setAttribute("Form2ReportBean", advanceReportBean);
			}
			
			if (AdvStatus.equals("N")) {
				path = "loadPFWApprovalForm";
			} else {
				if (flag.equals("ApprovalEdit")) {
					path = "loadPFWApprovalForm";
				} else {
					this.getLoginDetails(request);
					
					log.info("loginUserName------" + this.loginUserName);
					
					CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(
							this.loginUserId, this.loginUserName,
							this.loginUnitCode, this.loginRegion,
							this.loginSignName);
					String imgagePath = getServlet().getServletContext()
					.getRealPath("/")
					+ "/uploads/dbf/";
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_PFW_PART_II,
							imgagePath);
					log.info("transList size" + transList.size());
					
					if (transList.size() > 0) {
						request.setAttribute("transList", transList);
						
					} 
					path = "viewForm2Report";
				}
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("viewForm2Report");
		}
		log.info("=======Path=======" + path);
		return mapping.findForward(path);
	}
	
	public ActionForward loadAdvanceSearchForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadAdvanceForm");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String frmName = "";
		
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("advanceTransyear", "");
		
		log.info("------&&&&&&&&&&&&  before function---------"
				+ request.getParameter("frm_name"));
		
		this.getScreenTitle(request, response, "");
		log.info("-------frm Name in caller-----"
				+ request.getAttribute("advanceBean"));
		return mapping.findForward("searchAdvances");
	}
	
	public void getScreenTitle(HttpServletRequest request,
			HttpServletResponse response, String advanceType) {
		log.info("AdvancesAction::getScreenTitle");
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		
		String frmName = "", frmNm = "";
		
		if (request.getParameter("frm_name") != null) {
			frmNm = request.getParameter("frm_name");
		} else {
			if (!"".equals(advanceType)) {
				frmNm = advanceType;
			}
			
		}
		
		if (frmNm.equals("advancesnew")) {
			frmName = "loansadvances.cpf.screentitle";
		} else if (frmNm.equals("pfwnew")) {
			frmName = "loansadvances.pfw.screentitle";
		} else if (frmNm.equals("advances")) {
			frmName = "loansadvances.cpfsearch.screentitle";
		} else if (frmNm.equals("CPFApproval")) {
			frmName = "loansadvances.cpfapproval.screentitle";
		} else if (frmNm.equals("CPFApproved")) {
			frmName = "loansadvances.cpfapproved.screentitle";
		} else if (frmNm.equals("PFWApproval")) {
			frmName = "loansadvances.pfwapproval.screentitle";
		} else if (frmNm.equals("PFWApproved")) {
			frmName = "loansadvances.pfwapproved.screentitle";
		} else if (frmNm.equals("pfw")) {
			frmName = "loansadvances.pfwsearch.screentitle";
		} else if (frmNm.toUpperCase().equals("CPF")) {
			frmName = "loansadvances.cpf.screentitle";
		} else {
			frmName = "";
		}
		
		advanceBean.setScreenTitle(frmName);
		log.info("----frmName----" + frmName);
		advanceBean.setFrmName(frmNm);
		log.info("----frmNm----" + frmNm);
		request.setAttribute("screenTitle", frmName);
		request.setAttribute("advanceBean", advanceBean);
		
	}
//	On 23-Jan-2012 for loading date according to user 
	public ActionForward searchAdvances(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		this.getLoginDetails(request);
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (request.getParameter("frmName") != null) {
			searchBean.setFormName(request.getParameter("frmName"));
		}
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
			}
		}
		if (dynaValide.getString("advanceType") != null) {
			searchBean.setAdvanceType(dynaValide.getString("advanceType"));
		}
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		if (dynaValide.getString("advanceTransStatus") != null) {
			searchBean.setAdvanceTrnStatus(dynaValide
					.getString("advanceTransStatus"));
		}
		
		if (request.getParameter("frm_name") != null) {
			log.info("----*********------" + request.getParameter("frm_name"));
		}
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		log.info("----userName-----" + searchBean.getUserName()+"==ProfileName="+ searchBean.getProfileName());
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchAdvance(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		log.info("-------frm Name in caller-----"
				+ request.getAttribute("advanceBean"));
		
		return mapping.findForward("success");
	}
 
//	On 23-Jan-2012 for loading date according to user  
	public ActionForward search23Advances(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String path = "",frm_name="";
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		this.getLoginDetails(request);
		
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
			
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		if (dynaValide.getString("advanceTransStatus") != null) {
			searchBean.setAdvanceTrnStatus(dynaValide
					.getString("advanceTransStatus"));
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		log.info("-------Frm Name--------" + request.getParameter("frmName")
				+ "Pension NO" + searchBean.getPensionNo());
		if (request.getParameter("frmName") != null) {
			frm_name=request.getParameter("frmName");
			searchBean.setFormName(request.getParameter("frmName"));
			if (request.getParameter("frmName").equals("PFWForm2")) {
				searchBean.setAdvanceType(dynaValide.getString("advanceType"));
			}
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		if (request.getParameter("frmName").equals("CPFCheckList")) {
			searchBean.setAdvanceType("CPF");
		} else {
			searchBean.setAdvanceType("PFW");
		}
		if (dynaValide.getString("checkListStatus") != null) {
			searchBean.setCheckListStatus(dynaValide
					.getString("checkListStatus"));
		}

		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
				
		ArrayList searchlist = ptwAdvanceService.searchAdvance(searchBean);
		request.setAttribute("searchlist", searchlist);
		
		 
		if (request.getParameter("frmName").equals("PFWForm2")) {
			path = "Form2success";
		} else if (request.getParameter("frmName").equals("PFWForm3")) {
			path = "Form3success";
		} else if (request.getParameter("frmName").equals("PFWCheckList")) {
			path = "loadPFWCheckListSearch";
		} else if (request.getParameter("frmName").equals("CPFCheckList")) {
			path = "loadCPFCheckListSearch";
		} else {
			path = "Form4success";
		}
		this.getScreenTitle(request, response, frm_name);
		log.info("--------path in action------" + path);
		return mapping.findForward(path);
	}
 
	
	//replace the method
	//	04-Oct-2010 Radha  For adding saveToken
	//07-Jun-2011 Radha Resovling report opening issue in screen level
	public ActionForward advanceForm3Report(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String prpsOptionsCPF = "", frmName = "", path = "", flag = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String pensionNo = "", transactionNo = "", rtrnPage = "";
		
		//saveToken(request);
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		log.info("----------request.getParameter(frmAdvStatus)------------"
				+ request.getParameter("frmAdvStatus"));
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
					
				}
				
			}
			
		}
		
		try { 
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		
			log.info("advanceReport::pensionNo" + pensionNo + "transactionNo"
					+ transactionNo);
			list = ptwAdvanceService.advanceForm3Report(pensionNo,
					transactionNo, frmName);
			log.info("Size" + list.size());
			
			advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
			 
			this.fillForm4BeanToDyna(advanceReportBean, form);
			dynaValide.set("subscriptionAmt", advanceReportBean
					.getSubscriptionAmt());
			dynaValide.set("contributionAmt", advanceReportBean
					.getContributionAmt());
			dynaValide.set("prpsecvrdclse", advanceReportBean
					.getPrpsecvrdclse());
			dynaValide.set("purposeType", advanceReportBean.getPurposeType());
			dynaValide.set("amntrcdedrs", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("paymentinfo", advanceReportBean.getPaymentinfo());
			dynaValide.set("emolumentsLabel", advanceReportBean
					.getEmolumentsLabel());
			dynaValide.set("approvedsubamt", advanceReportBean
					.getApprovedsubamt());
			dynaValide.set("approvedconamt", advanceReportBean
					.getApprovedconamt());
			dynaValide.set("firstInsSubAmnt", advanceReportBean
					.getFirstInsSubAmnt());
			dynaValide.set("firstInsConrtiAmnt", advanceReportBean
					.getFirstInsConrtiAmnt());
			dynaValide.set("verifiedby", advanceReportBean.getVerifiedby());
			dynaValide.set("authorizedrmrks", advanceReportBean
					.getAuthrizedRemarks());
			dynaValide.set("advanceTransIDDec", advanceReportBean
					.getAdvanceTransIDDec());
			request.setAttribute("Form3ReportBean", advanceReportBean);
			 
			if (frmName.equals("PFWForm3")) {
				path = "loadPFWForm3ApprovalForm";
			} else {
				if (flag.equals("ApprovalEdit")) {
					path = "loadPFWForm3ApprovalForm";
				} else {
					
					this.getLoginDetails(request);
					
					log.info("loginUserName------" + this.loginUserName);
					
					CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(
							this.loginUserId, this.loginUserName,
							this.loginUnitCode, this.loginRegion,
							this.loginSignName);
					String imgagePath = getServlet().getServletContext()
					.getRealPath("/")
					+ "/uploads/dbf/";
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_PFW_PART_III_SRMGR,
							imgagePath);
					log.info("transList size" + transList.size());
					
					if (transList.size() > 0) {
						request.setAttribute("transList", transList);
						
					} 
					path = "viewForm3Report";
				}
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("viewForm3Report");
		}
		
		if(path.equals("loadPFWForm3ApprovalForm")){
			saveToken(request);
			}
		return mapping.findForward(path);
	}
	public ActionForward advanceForm4VerificationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean(request);
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String prpsOptionsCPF = "", frmName = "", path = "", flag = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String pensionNo = "", transactionNo = "", rtrnPage = "";
		
		saveToken(request);
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		log.info("----------request.getParameter(frmAdvStatus)------------"
				+ request.getParameter("frmAdvStatus"));
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
					
				}
				
			}
			
		}
		try {
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		  
			log.info("advanceReport::pensionNo" + pensionNo + "transactionNo"
					+ transactionNo);
			list = ptwAdvanceService.advanceForm3Report(pensionNo,
					transactionNo, frmName);
			log.info("Size" + list.size());
			
			advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);			 
			this.fillForm4BeanToDyna(advanceReportBean, form);
			dynaValide.set("subscriptionAmt", advanceReportBean
					.getSubscriptionAmt());
			dynaValide.set("contributionAmt", advanceReportBean
					.getContributionAmt());
			dynaValide.set("prpsecvrdclse", advanceReportBean
					.getPrpsecvrdclse());
			dynaValide.set("purposeType", advanceReportBean.getPurposeType());
			dynaValide.set("amntrcdedrs", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("paymentinfo", advanceReportBean.getPaymentinfo());
			dynaValide.set("emolumentsLabel", advanceReportBean
					.getEmolumentsLabel());
			dynaValide.set("approvedsubamt", advanceReportBean
					.getApprovedsubamt());
			dynaValide.set("approvedconamt", advanceReportBean
					.getApprovedconamt());
			dynaValide.set("verifiedby", advanceReportBean.getVerifiedby());
			dynaValide.set("amntRecommended", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("advanceTransIDDec", advanceReportBean
					.getAdvanceTransIDDec());
			dynaValide.set("purposeFlag", advanceReportBean
					.getPurposeFlag());
			dynaValide.set("firstInsSubAmnt", advanceReportBean
					.getFirstInsSubAmnt());
			dynaValide.set("firstInsConrtiAmnt", advanceReportBean
					.getFirstInsConrtiAmnt());
			request.setAttribute("Form3ReportBean", advanceReportBean);
			 
			if (frmName.equals("PFWForm4Verification")) {
				path = "loadPFWForm4ApprovalForm";
			} else {
				if (flag.equals("ApprovalEdit")) {
					path = "loadPFWForm4ApprovalForm";
				} else {
					this.getLoginDetails(request);
					
					log.info("loginUserName------" + this.loginUserName);
					
					CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(
							this.loginUserId, this.loginUserName,
							this.loginUnitCode, this.loginRegion,
							this.loginSignName);
					String imgagePath = getServlet().getServletContext()
					.getRealPath("/")
					+ "/uploads/dbf/";
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_PFW_PART_IV_SECRETARY,
							imgagePath);
					log.info("transList size" + transList.size());
					
					if (transList.size() > 0) {
						request.setAttribute("transList", transList);
						
					} 
					path = "viewForm4Report";
					
				}
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			this.getScreenTitle(request,response,frmName);
			return mapping.findForward("viewForm4Report");
		}
		log.info("=======frmName=========="+frmName);
		this.getScreenTitle(request,response,frmName);
		return mapping.findForward(path);
	}
	
	// ----------------------------------End :
	// advanceForm3Report()--------------------------------------------------
	//	On 30-May-2012 for implementing  only if FinalSettlment date is updated in personal info then only Arrear will process
	//On 25-Jan-2012 for implementing  retriving  of data for region ,Unit wise
	//On 21-Jan-2012 for  restricting entry for death,Resignation & Retiremen cases for Loans & Advances
	public ActionForward lookupPFID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String selectedPFID = "", selectedEmpName = "", currentDay="",trust = "", currentYear = "", currentMonth = "", selectedRegion = "", selectedCpfaccno = "", returnPageFlag = "", page = "", currentDate = "",frm_name="";
		String profileName="",userStation="",userRegion="",accountype="",errorFlag="false",action_flag="";
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList airportsList = new ArrayList();
		ArrayList yearList=new ArrayList();
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		this.getLoginDetails(request);
		profileName=this.loginProfile;
		userRegion=this.loginRegion;
		userStation=this.loginUnitName;
		if (request.getParameter("frm_pensionno") != null) {
			selectedPFID = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_cpfaccno") != null) {
			selectedCpfaccno = request.getParameter("frm_cpfaccno");
		}
		if (request.getParameter("frm_trust") != null) {
			trust = request.getParameter("frm_trust");
		}
		if (request.getParameter("frm_region") != null) {
			selectedRegion = request.getParameter("frm_region");
		}
		if (request.getParameter("empName") != null) {
			selectedEmpName = request.getParameter("empName");
		}
		if (request.getParameter("goFlag") != null) {
			returnPageFlag = request.getParameter("goFlag");
		}
		if (request.getParameter("frm_name") != null) {
			frm_name = request.getParameter("frm_name");
		}
		if (request.getParameter("action_flag") != null) {
			action_flag = request.getParameter("action_flag");
		}
		log.info("======lookupPFID==========="+frm_name+"===returnPageFlag==="+returnPageFlag);
		selectedPFID = commonUtil.getSearchPFID(selectedPFID);
		currentDate = commonUtil.getCurrentDate("dd-MM-yyyy");
		String currentDtInfo[] = currentDate.split("-");
		currentDay = currentDtInfo[0]; 
		currentMonth = Integer.toString(Integer.parseInt(currentDtInfo[1]));
		currentYear = currentDtInfo[2];
		try {
			currentDate = commonUtil.converDBToAppFormat(currentMonth + "-"
					+ currentYear, "MM-yyyy", "MMM-yyyy");
			currentDate = "-" + currentDate;
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		String maxMonthYear="",resultfromepv="";
		
		try {
			maxMonthYear = commonService.lookforMonth(selectedPFID,currentDate,true);
			resultfromepv="-"+maxMonthYear ;
			personalList = commonService.loadPersonalTransInfo(selectedEmpName,
					selectedRegion, selectedPFID, selectedCpfaccno, resultfromepv);
			log.info("===personalList=="+personalList.size());
		} catch (EPISException e1) {
			log.info("===Exception==");
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("norecords", new ActionMessage(
			"common.norecords"));
			saveMessages(request, actionMessage);
			request.setAttribute("focusFlag", "false");
			errorFlag="true";
		}
		
		// --------begin----------
		
		if (personalList.size() == 0) {
			log.info("===Enter==");
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("norecords",
					new ActionMessage("common.norecords"));
			saveMessages(request, actionMessage);
			request.setAttribute("focusFlag", "false");
			request.setAttribute("personalList", personalList);			 
			errorFlag="true";
		}
		if (personalList.size() != 0) {
			advanceBean = (AdvanceBasicBean) personalList.get(0);
			
			log.info("=Employee Region=="+advanceBean.getRegion()+"==Station==="+advanceBean.getStation());
			log.info("=User Region=="+userRegion+"==Station==="+userStation);
		if(action_flag.equals("loadpfid"))	{
		if(profileName.equals("U")){
			ActionMessages actionMessage = new ActionMessages();
			if((!advanceBean.getRegion().equals(userRegion))  || (!advanceBean.getStation().equals(userStation))) {
				actionMessage.add("norecords", new ActionMessage(
				"common.norights.unit",advanceBean.getRegion(),advanceBean.getStation()));
				errorFlag="true";
				saveMessages(request, actionMessage);
				log.info("==errors ===="+actionMessage.get("norecords"));
			}  
		}else if(profileName.equals("R")){
			
			ActionMessages actionMessage = new ActionMessages();
			if(!(advanceBean.getRegion().equals(userRegion))) {
				actionMessage.add("norecords", new ActionMessage(
				"common.norights.rhq",advanceBean.getRegion()));
				saveMessages(request, actionMessage);
				errorFlag="true";
			}  else if(!(advanceBean.getStation().equals(userStation))) {
				accountype= commonUtil.getAccountType(advanceBean.getRegion(),advanceBean.getStation());
				if(advanceBean.getRegion().equals("CHQIAD") && accountype.equals("RAU")){
					actionMessage.add("norecords", new ActionMessage(
							"common.norights.rhq.iad.rau",advanceBean.getRegion(),advanceBean.getStation()));
					errorFlag="true";
					saveMessages(request, actionMessage);
				}
				if(accountype.equals("SAU")){
					actionMessage.add("norecords", new ActionMessage(
							"common.norights.rhq.sau",accountype));
					errorFlag="true";
					saveMessages(request, actionMessage);
				}
				
			} 
			log.info("==errors ===="+actionMessage.get("norecords"));
		}
		}
		if(errorFlag.equals("false")){
		if (returnPageFlag.equals("notesheet")) {
			nomineeList = commonService.loadNomineeInfo(selectedEmpName,
					selectedRegion, selectedPFID, "-Sep-2007");
		}
		
		
		if (returnPageFlag.equals("notesheet")) {
			
			if (personalList.size() != 0) {
				this.fillPersonalForm(personalList, form);
				DynaValidatorForm dynaValide = (DynaValidatorForm) form;
				if (trust.equals("IAAI")) {
					dynaValide.set("station", "");
					dynaValide.set("trust", "IAAI");
				}
			}
			
			advanceBean = (AdvanceBasicBean) personalList.get(0);
			if (!advanceBean.getRegion().equals("")) {
				airportsList = commonUtil.getAirportList(advanceBean
						.getRegion());
				if (airportsList.size() > 0) {
					request.setAttribute("airportsList", airportsList);
				}
			}
			request.setAttribute("nomineeList", nomineeList);
			page = "loadNoteSheetForm";
			
		} else if (returnPageFlag.equals("finalsettlement")){
			if (personalList.size() != 0) {
				
				noteSheetBean = commonService.checkSanctionDate(selectedPFID);
				ActionMessages actionMessage = new ActionMessages();
				
				yearList=commonUtil.getYearList();
				
				log.info("yearList=========="+yearList.size());
				request.setAttribute("yearList", yearList);
				
				if ((noteSheetBean.getPensionNo() != null)
						&& (!noteSheetBean.getPensionNo().equals("")) && returnPageFlag.equals("finalsettlement")) {
					log.info("-------Pension No in ACTION------"
							+ noteSheetBean.getPensionNo());
					
					if (!noteSheetBean.getSanctiondt().equals("")) {
						actionMessage.add("norecords", new ActionMessage(
						"common.finalsettlement.approved"));
					} else {
						actionMessage.add("norecords", new ActionMessage(
						"common.finalsettlement.process"));
					}   
					
					saveMessages(request, actionMessage);
				}
				
				else {
					
					this.fillPersonalForm(personalList, form);
					DynaValidatorForm dynaValide = (DynaValidatorForm) form;
					if (trust.equals("IAAI")) {
						dynaValide.set("station", "");
						dynaValide.set("trust", "IAAI");
					}
					
					advanceBean = (AdvanceBasicBean) personalList.get(0);
					if (!advanceBean.getRegion().equals("")) {
						airportsList = commonUtil.getAirportList(advanceBean
								.getRegion());
						if (airportsList.size() > 0) {
							request.setAttribute("airportsList", airportsList);
						}
					}
				}
			}
			request.setAttribute("nomineeList", nomineeList);
			
			
			page = "loadFinalSettlement";
			
		}else if(returnPageFlag.equals("finalsettlementarrear")) {
			
			if (personalList.size() != 0) {
				
				advanceBean = (AdvanceBasicBean) personalList.get(0);
				
				ActionMessages actionMessage = new ActionMessages();
				
				yearList=commonUtil.getYearList();
				
				
				request.setAttribute("yearList", yearList);
				
				if (advanceBean.getReSettlementDate().equals("---")) {
					actionMessage.add("message", new ActionMessage(
					"common.resettlement.notexist"));						
					 
				}else 	if (advanceBean.getSeperationreason().equals("")) {
					actionMessage.add("norecords", new ActionMessage(
					"common.finalsettlement.notexist")); 					
				}
				else {
					
					this.fillPersonalForm(personalList, form);
					DynaValidatorForm dynaValide = (DynaValidatorForm) form;
					if (trust.equals("IAAI")) {
						dynaValide.set("station", "");
						dynaValide.set("trust", "IAAI");
					}
					
					
					if (!advanceBean.getRegion().equals("")) {
						airportsList = commonUtil.getAirportList(advanceBean
								.getRegion());
						if (airportsList.size() > 0) {
							request.setAttribute("airportsList", airportsList);
						}
					}
				}
				saveMessages(request, actionMessage);
			}
			request.setAttribute("nomineeList", nomineeList);
			
			page = "loadFinalSettlementArrear";
			
		}else {
			
			log.info("====This is for CPF/PFW cases======");
			if (!returnPageFlag.equals("true")) {
				request.setAttribute("personalList", personalList);
				request.setAttribute("selectedRegion", selectedRegion);
				page = "loadPersonalForm";
			} else {
				if (personalList.size() != 0) {
					
					noteSheetBean = commonService
					.checkSanctionDate(selectedPFID);
					ActionMessages actionMessage = new ActionMessages();
					
					if ((noteSheetBean.getPensionNo() != null)
							&& (!noteSheetBean.getPensionNo().equals(""))) {
						log.info("-------Pension No in ACTION------"
								+ noteSheetBean.getPensionNo()+"======seperation reason===="+noteSheetBean.getSeperationreason());
						
						if (!noteSheetBean.getSanctiondt().equals("")) {
							actionMessage.add("norecords", new ActionMessage(
							"common.finalsettlement.approved"));
						}  else {
							
							this.fillPersonalForm(personalList, form);
							advanceBean = (AdvanceBasicBean) personalList
							.get(0);
							
							if (!advanceBean.getRegion().equals("")) {
								airportsList = commonUtil
								.getAirportList(advanceBean.getRegion());
								
								if (airportsList.size() > 0) {
									request.setAttribute("airportsList",
											airportsList);
								}
							}
							
						}
						saveMessages(request, actionMessage);
					} else {
						log.info("====sanctiondate not present cases======");
						this.fillPersonalForm(personalList, form);
						advanceBean = (AdvanceBasicBean) personalList.get(0);
						log.info("====sanctiondate not present cases======"+advanceBean.getSeperationreason()+"==");
						
						
					  if (!advanceBean.getSeperationreason().equals("")) {
						 	actionMessage.add("norecords", new ActionMessage(
							"common.loansadvances.entry.failed",advanceBean.getSeperationreason())); 
						}else{
						
						if (!advanceBean.getRegion().equals("")) {
							airportsList = commonUtil
							.getAirportList(advanceBean.getRegion());
							
							if (airportsList.size() > 0) {
								request.setAttribute("airportsList",
										airportsList);
							}
						}
					}
					  saveMessages(request, actionMessage);
					}
					
				} else {
					ActionMessages actionMessage = new ActionMessages();
					actionMessage.add("norecords", new ActionMessage(
					"common.norecords"));
					saveMessages(request, actionMessage);
					request.setAttribute("focusFlag", "false");
				}
				page = "loadAdvanceForm";
			}
			request.setAttribute("focusFlag", "true");
		}
		
		} 
		}
		//common code
		if(errorFlag.equals("true")){
			if(returnPageFlag.equals("finalsettlement")){
				page = "loadFinalSettlement";
			}else if(returnPageFlag.equals("finalsettlementarrear")) {
				page = "loadFinalSettlementArrear";	
			}else if (frm_name.equals("advancesnew")||frm_name.equals("pfwnew")) {
				page = "loadAdvanceForm";
			}else{
				page = "loadPersonalForm";
				
			} 
		}
		
		log.info("=======ReturnPage======"+page);
		this.getScreenTitle(request, response, "");
		return mapping.findForward(page);
	}
	
	public ActionForward loadAdvanceFormBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadAdvanceFormBack"
				+ request.getAttribute("advanceBasicBean"));
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		ArrayList list = new ArrayList();
		ArrayList airportsList = new ArrayList();
		session = request.getSession(true);
		request.setAttribute("focusFlag", "true");
		if (session.getAttribute("backAdvaceBean") != null) {
			advanceBasicBean = (AdvanceBasicBean) session
			.getAttribute("backAdvaceBean");
			list.add(advanceBasicBean);
			session.removeAttribute("backAdvaceBean");
		}
		
		if (!advanceBasicBean.getRegion().equals("")) {
			airportsList = commonUtil.getAirportList(advanceBasicBean
					.getRegion());
			if (airportsList.size() > 0) {
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		fillPersonalForm(list, form);
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("loadAdvanceForm");
	}
	
	public ActionForward saveAdvacneInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		String userName = "", computerName = "", messages = "", totalInstall = "", pensionNo = "";
		
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
	
		log.info("saveAdvacneInfo============="+userName+"computerName=========="+computerName);
		EmpBankMaster bankMaster = new EmpBankMaster();
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String pfwAdvanceType = "", pfwPurposeType = "", advPurpose = "";
		if (request.getParameter("frm_pfwAdv") != null) {
			pfwAdvanceType = request.getParameter("frm_pfwAdv");
		}
		if (request.getParameter("frm_pfwPurpose") != null) {
			pfwPurposeType = request.getParameter("frm_pfwPurpose");
		}
		if (request.getParameter("frm_advPurpose") != null) {
			advPurpose = request.getParameter("frm_advPurpose");
		}
		if (request.getParameter("frm_totalinstall") != null) {
			totalInstall = request.getParameter("frm_totalinstall");
		} else {
			totalInstall = "0";
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionNo = request.getParameter("frm_pensionno");
		}
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advanceBean = this.fillDynaFormToBean(form, request);
		if (request.getParameter("frm_region") != null) {
			advanceBean.setRegion(request.getParameter("frm_region"));
		} 
		if (dynaValide.getString("placeofposting") != null) {
			advanceBean.setPlaceofposting(dynaValide
					.getString("placeofposting"));
		}
		advanceBean.setPensionNo(pensionNo);
		advanceBean.setCpfIntallments(totalInstall);
		log.info("Pensionno" + dynaValide.getString("pensionNo"));
		advanceBean.setAdvanceType(pfwAdvanceType);
		advanceBean.setPfwPurpose(pfwPurposeType);
		advanceBean.setAdvPurpose(advPurpose);
		if (!pfwPurposeType.equals("NO-SELECT")) {
			dynaValide.set("purposeType", pfwPurposeType);
			advanceBean.setPurposeType(pfwPurposeType);
		} else if (!advPurpose.equals("NO-SELECT")) {
			dynaValide.set("purposeType", advPurpose);
			advanceBean.setPurposeType(advPurpose);
		}
		boolean errorFlag = false;
		if (!userName.equals(""))
			advanceBean.setUserName(userName);
		
		try {
			messages = ptwAdvanceService
			.addAdvanceInfo(advanceBean, bankMaster);
		} catch (EPISException e) {
			dynaValide.set("message", e.getMessage());
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(messages));
			saveMessages(request, actionMessage);
			errorFlag = true;
		}
		if (errorFlag == false) {
			dynaValide.set("message", messages);
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(messages));
			saveMessages(request, actionMessage);
		}
		
		this.getScreenTitle(request, response, "");
		
		ActionForward forward = mapping.findForward("success");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		params.put("method", "searchAdvances");
		params.put("frm_name", "advances");
		
		if (pfwAdvanceType.equals("CPF"))
			fwdParams.add("frm_name", "advances");
		else if (pfwAdvanceType.equals("PFW"))
			fwdParams.add("frm_name", "pfw");
		
		return fwdParams.forward(forward);
	}
//	 By Radha On 26-Apr-2011 for adding party details 
	public ActionForward saveAdvacneNextInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		String userName = "", computerName = "", messages = "",accountType="";
		this.loadPrimarilyInfo(request,"");
		this.getLoginDetails(request);
		userName = userid;
		computerName = userIPaddress;
		accountType=this.loginAccountType;
		
		//log.info("saveAdvacneNextInfo::userName" + userName + "computerName"
				//+ computerName+ "accountType"+ accountType);
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String lodDocumentsInfo = "";
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
			
		}
		//log.info("Enclose Documents" + lodDocumentsInfo);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		advanceBean = this.fillDynaFormNextToBean(form, dynaValide
				.getString("advanceType"), dynaValide.getString("purposeType"),
				dynaValide.getString("wthdrwlist"));
		advanceBean.setLodInfo(lodDocumentsInfo);
		String purposeType = "";
		purposeType = dynaValide.getString("purposeType");
		advanceBean.setPurposeType(dynaValide.getString("purposeType"));
		advanceBean.setAdvanceType(dynaValide.getString("advanceType"));
		advanceBean.setCpfIntallments(dynaValide.getString("cpftotalinstall"));
		log.info("saveAdvacneNextInfo::cpftotalinstall"
				+ dynaValide.getString("cpftotalinstall"));
		if (advanceBean.getPurposeType().equals("defence")) {
			advanceBean.setPurposeOptionType("SELF");
		}
		//log.info("Purpose Option Type" + advanceBean.getPurposeOptionType());
		//log.info("advReqAmnt"+dynaValide.getString("advReqAmnt"));
		//log.info("pfwReqAmnt"+dynaValide.getString("pfwReqAmnt"));
		//log.info("Purpose Option Type" + advanceBean.getAdvanceType());
		if(advanceBean.getAdvanceType().equals("CPF")){
		if (dynaValide.getString("advReqAmnt") != null) {
			advanceBean.setAdvReqAmnt(dynaValide.getString("advReqAmnt"));
		}
		}else{
		if (dynaValide.getString("pfwReqAmnt") != null) {
			advanceBean.setAdvReqAmnt(dynaValide.getString("pfwReqAmnt"));
		}
		}
		//add fields
		if (dynaValide.getString("recEmpSubAmnt") != null) {
			advanceBean.setRecEmpSubAmnt(dynaValide.getString("recEmpSubAmnt"));
		}else{
			advanceBean.setRecEmpSubAmnt("0.00");
		}
		if (dynaValide.getString("recEmpConrtiAmnt") != null) {
			advanceBean.setRecEmpConrtiAmnt(dynaValide.getString("recEmpConrtiAmnt"));
		}else{
			advanceBean.setRecEmpConrtiAmnt("0.00");
		}
		if (dynaValide.getString("firstInsSubAmnt") != null) {
			advanceBean.setFirstInsSubAmnt(dynaValide.getString("firstInsSubAmnt"));
		}else{
			advanceBean.setFirstInsSubAmnt("0.00");
		}
		if (dynaValide.getString("firstInsConrtiAmnt") != null) {
			advanceBean.setFirstInsConrtiAmnt(dynaValide.getString("firstInsConrtiAmnt"));
		}else{
			advanceBean.setFirstInsConrtiAmnt("0.00");
		}
		
		if (dynaValide.getString("placeofposting") != null) {
			advanceBean.setPlaceofposting(dynaValide
					.getString("placeofposting"));
		}
		if (dynaValide.getString("trust") != null) {
			advanceBean.setTrust(dynaValide.getString("trust"));
		}
		if (dynaValide.getString("dateOfJoining") != null) {
			advanceBean.setDateOfJoining(dynaValide.getString("dateOfJoining"));
		}
		if (dynaValide.getString("station") != null) {
			advanceBean.setStation(dynaValide.getString("station"));
		}
		if (dynaValide.getString("region") != null) {
			advanceBean.setRegion(dynaValide.getString("region"));
		}
		if (dynaValide.getString("emoluments") != null) {
			advanceBean.setEmoluments(dynaValide.getString("emoluments"));
		}
		if (dynaValide.getString("pensionNo") != null) {
			advanceBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		if (dynaValide.getString("advReasonText") != null) {
			advanceBean.setAdvReasonText(dynaValide.getString("advReasonText"));
		}
		if (dynaValide.getString("bankdetail") != null) {
			if (dynaValide.getString("bankdetail").equals("NO-SELECT")) {
				advanceBean.setPaymentinfo("N");
			} else {
				advanceBean.setPaymentinfo("Y");
			}
		}
		if (dynaValide.getString("designation") != null) {
			advanceBean.setDesignation(dynaValide.getString("designation"));
		}
		if (dynaValide.getString("department") != null) {
			advanceBean.setDepartment(dynaValide.getString("department"));
		}
		bankMaster = this.fillDynaFormBankToBean(form);
		
		if (dynaValide.getString("modeofpartyname") != null) {
			bankMaster.setPartyName(dynaValide.getString("modeofpartyname"));
		}
		if (dynaValide.getString("modeofpartyaddrs") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("modeofpartyaddrs"));
		}
		log.info("Amount " + dynaValide.getString("osamountwithinterest"));
		log.info("Pension no" + advanceBean.getPensionNo() + "Amount"
				+ advanceBean.getAdvReqAmnt());
		log.info("Purspose TYpe" + advanceBean.getPurposeType()
				+ "Advance Type" + advanceBean.getAdvanceType());
		
		if (!userName.equals("")) {
			advanceBean.setUserName(userName);
		}
		
		log.info("--------advanceBean.getUserName()--------"
				+ advanceBean.getUserName());
		
		advanceBean.setAccountType(accountType);
		log.info("--------accountType--------"
				+ advanceBean.getAccountType());
		try {
			messages = ptwAdvanceService
			.addAdvanceInfo(advanceBean, bankMaster);
			AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
			request.setAttribute("advanceBean", advanceBasic);
			dynaValide.set("purposeType", purposeType);
			dynaValide.set("employeeName", "");
			dynaValide.set("pensionNo", "");
			dynaValide.set("emoluments", "");
			dynaValide.set("advReqAmnt", "");
			dynaValide.set("pfwReqAmnt", "");
			dynaValide.set("dateOfBirth", "");
			dynaValide.set("dateOfJoining", "");
			dynaValide.set("placeofposting", "NO-SELECT");
			dynaValide.set("firstInsSubAmnt", "");
			dynaValide.set("firstInsConrtiAmnt", "");
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("loadAdvanceForm");
		}
		
		log.info("-----message in ACTION-----" + messages);
		
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		saveMessages(request, actionMessage);
		
		this.getScreenTitle(request, response, advanceBean.getAdvanceType());		
		return mapping.findForward("loadAdvanceForm");
	}
	
	public ActionForward continueWithPTWAdvanceOptions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		String pfwAdvanceType = "", pfwPurposeType = "", advPurpose = "", totalInstall = "", pensionNo = "", trust = "";
		boolean pandemicFlag=false;
		String page="contWthAdvOpt";
		int count=0;
		session = request.getSession(true);
		
		if (request.getParameter("frm_pfwAdv") != null) {
			pfwAdvanceType = request.getParameter("frm_pfwAdv");
		}
		if (request.getParameter("frm_pfwPurpose") != null) {
			pfwPurposeType = request.getParameter("frm_pfwPurpose");
		}
		if (request.getParameter("frm_advPurpose") != null) {
			advPurpose = request.getParameter("frm_advPurpose");
		}
		if (request.getParameter("frm_totalinstall") != null) {
			totalInstall = request.getParameter("frm_totalinstall");
		} else {
			totalInstall = "0";
		}
		if (request.getParameter("frm_pensionno") != null) {
			pensionNo = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_trust") != null) {
			trust = request.getParameter("frm_trust");
		}
		log.info("continueWithPTWAdvanceOptions::totalInstall======"
				+ totalInstall);
		advanceBasicBean = this.fillDynaFormToBean(form, request);
		if (request.getParameter("frm_region") != null) {
			advanceBasicBean.setRegion(request.getParameter("frm_region"));
		}
		if (dynaValide.getString("placeofposting") != null) {
			advanceBasicBean.setPlaceofposting(dynaValide
					.getString("placeofposting"));
		}
		advanceBasicBean.setPensionNo(pensionNo);
		advanceBasicBean.setAdvanceType(pfwAdvanceType);
		advanceBasicBean.setPfwPurpose(pfwPurposeType);
		advanceBasicBean.setAdvPurpose(advPurpose);
		advanceBasicBean.setCpfIntallments(totalInstall);
		advanceBasicBean.setTrust(trust);
		
		if (advanceBasicBean.getAdvanceType().equals("pfw")) {
			dynaValide.set("advanceType", advanceBasicBean.getAdvanceType());
		} else {
			dynaValide.set("advanceType", "CPF");
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		//employeeBankBean = ptwAdvanceService.employeeBankInfo(advanceBasicBean.getPensionNo());
		dynaValide.set("dateOfJoining", advanceBasicBean.getDateOfJoining());
		
		/*dynaValide.set("bankempname", employeeBankBean.getBankempname());
		 dynaValide.set("bankempaddrs", employeeBankBean.getBankempaddrs());
		 dynaValide.set("bankname", employeeBankBean.getBankname());
		 dynaValide.set("branchaddress", employeeBankBean.getBranchaddress());
		 dynaValide
		 .set("banksavingaccno", employeeBankBean.getBanksavingaccno());
		 dynaValide.set("bankemprtgsneftcode", employeeBankBean
		 .getBankemprtgsneftcode());
		 dynaValide.set("bankempmicrono", employeeBankBean.getBankempmicrono());
		 dynaValide.set("empmailid", employeeBankBean.getEmpmailid());
		 dynaValide.set("chkbankinfo", employeeBankBean.getChkBankInfo());
		 */
		
		//log.info(advanceBasicBean.getFirstInsSubAmnt()+":::"+advanceBasicBean.getFirstInsConrtiAmnt());
		dynaValide.set("cpftotalinstall", totalInstall);
		dynaValide.set("subscriptionAmt", advanceBasicBean.getPfStatury());
		dynaValide.set("employeeName", advanceBasicBean.getEmployeeName());
		dynaValide.set("dateOfBirth", advanceBasicBean.getDateOfBirth());
		dynaValide.set("region", advanceBasicBean.getRegion());
		dynaValide.set("trust", advanceBasicBean.getTrust());
		dynaValide.set("advReasonText", advanceBasicBean.getAdvReasonText());
		dynaValide.set("recEmpSubAmnt",advanceBasicBean.getRecEmpSubAmnt());
		dynaValide.set("recEmpConrtiAmnt",advanceBasicBean.getRecEmpConrtiAmnt());
		dynaValide.set("firstInsSubAmnt",advanceBasicBean.getFirstInsSubAmnt());
		dynaValide.set("firstInsConrtiAmnt",advanceBasicBean.getFirstInsConrtiAmnt());
		
		if (!pfwPurposeType.equals("NO-SELECT")) {
			dynaValide.set("purposeType", pfwPurposeType);
			advanceBasicBean.setPurposeType(pfwPurposeType);
		} else if (!advPurpose.equals("NO-SELECT")) {
			dynaValide.set("purposeType", advPurpose);
			advanceBasicBean.setPurposeType(advPurpose);
		}
		log.info("pfwPurposeType" + advanceBasicBean.getPurposeType());
		this.getScreenTitle(request, response, advanceBasicBean
				.getAdvanceType());
		request.setAttribute("advanceBean", advanceBasicBean);
		session.setAttribute("backAdvaceBean", advanceBasicBean);
		
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message",
				new ActionMessage("common.pandemic"));
		saveMessages(request, actionMessage);
		if(pfwPurposeType.equals("Pandemic")){
			count=ptwAdvanceService.isPFWPandemic(pensionNo);
			if(count>0){
			page = "loadAdvanceForm";
			}
		}
		
		// this.getScreenTitle(request,response);
		return mapping.findForward(page);
	}
	
	private void fillPersonalForm(ArrayList list, ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean basicBean = null;
		for (int i = 0; i < list.size(); i++) {
			basicBean = (AdvanceBasicBean) list.get(i);
		}
		basicBean.setDateOfMembership(basicBean.getDateOfJoining());
		dynaValide.set("employeeName", basicBean.getEmployeeName());
		dynaValide.set("pensionNo", basicBean.getPensionNo());
		dynaValide.set("pfid", basicBean.getPfid());
		dynaValide.set("designation", basicBean.getDesignation());
		dynaValide.set("department", basicBean.getDepartment());
		dynaValide.set("dateOfMembership", basicBean.getDateOfMembership());
		dynaValide.set("dateOfJoining", basicBean.getDateOfJoining());
		dynaValide.set("dateOfBirth", basicBean.getDateOfBirth());
		dynaValide.set("employeeNo", basicBean.getEmployeeNo());
		dynaValide.set("cpfaccno", basicBean.getCpfaccno());
		dynaValide.set("region", basicBean.getRegion());
		dynaValide.set("station", basicBean.getStation());
		dynaValide.set("transMnthYear", basicBean.getTransMnthYear());
		dynaValide.set("fhName", basicBean.getFhName());
		dynaValide.set("wetherOption", basicBean.getWetherOption());
		dynaValide.set("emoluments", basicBean.getEmoluments());
		dynaValide.set("pfStatury", basicBean.getPfStatury());
		dynaValide.set("empmailid", basicBean.getMailID());
		dynaValide.set("subscriptionAmt", basicBean.getPfStatury());
		
		dynaValide.set("permenentaddress", basicBean.getPermenentaddress());
		dynaValide.set("presentaddress", basicBean.getPresentaddress());
		dynaValide.set("phoneno", basicBean.getPhoneno());
		dynaValide.set("seperationreason", basicBean.getSeperationreason());
		dynaValide.set("seperationdate", basicBean.getSeperationdate());
		
		
	}
	
	private void fillResetPersonalForm(ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		dynaValide.getMap().clear();
	}
	
	private AdvanceBasicBean fillDynaFormToBean(ActionForm form,
			HttpServletRequest request) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean basicBean = new AdvanceBasicBean(request);
		if (dynaValide.getString("employeeName") != null) {
			basicBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (dynaValide.getString("emplshare") != null) {
			basicBean.setEmplshare(dynaValide.getString("emplshare"));
		} else {
			basicBean.setEmplshare("0");
		}
		
		if (dynaValide.getString("emplrshare") != null) {
			basicBean.setEmplrshare(dynaValide.getString("emplrshare"));
		} else {
			basicBean.setEmplrshare("0");
		}
		
		if (dynaValide.getString("nssanctionno") != null) {
			basicBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		if (dynaValide.getString("pensioncontribution") != null) {
			basicBean.setPensioncontribution(dynaValide
					.getString("pensioncontribution"));
		} else {
			basicBean.setPensioncontribution("0");
		}
		if (dynaValide.getString("soremarks") != null) {
			basicBean.setSoremarks(dynaValide.getString("soremarks"));
		}else{
			basicBean.setSoremarks("");
		}
		
		if (dynaValide.getString("netcontribution") != null) {
			basicBean.setNetcontribution(dynaValide
					.getString("netcontribution"));
		} else {
			basicBean.setNetcontribution("0");
		}
		if (dynaValide.getString("sanctionno") != null) {
			basicBean.setSanctionno(dynaValide.getString("sanctionno"));
		}
		if (dynaValide.getString("paymentdt") != null) {
			basicBean.setPaymentdt(dynaValide.getString("paymentdt"));
		}
		if (dynaValide.getString("remarks") != null) {
			basicBean.setRemarks(dynaValide.getString("remarks"));
		}
		if (dynaValide.getString("pensionNo") != null) {
			basicBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		//log.info("fillDynaFormToBean Pension No" + basicBean.getPensionNo()+dynaValide.getString("frmName"));
		if (dynaValide.getString("pfid") != null) {
			basicBean.setPfid(dynaValide.getString("pfid"));
		}
		if (dynaValide.getString("designation") != null) {
			basicBean.setDesignation(dynaValide.getString("designation"));
		}
		if (dynaValide.getString("department") != null) {
			basicBean.setDepartment(dynaValide.getString("department"));
		}
		
		if (dynaValide.getString("dateOfMembership") != null) {
			basicBean.setDateOfMembership(dynaValide
					.getString("dateOfMembership"));
		}
		
		if (dynaValide.getString("dateOfJoining") != null) {
			basicBean.setDateOfJoining(dynaValide.getString("dateOfJoining"));
		}
		if (dynaValide.getString("dateOfBirth") != null) {
			basicBean.setDateOfBirth(dynaValide.getString("dateOfBirth"));
		}
		if (dynaValide.getString("employeeNo") != null) {
			basicBean.setEmployeeNo(dynaValide.getString("employeeNo"));
		}
		if (dynaValide.getString("cpfaccno") != null) {
			basicBean.setCpfaccno(dynaValide.getString("cpfaccno"));
		}
		if (dynaValide.getString("region") != null) {
			basicBean.setRegion(dynaValide.getString("region"));
		}
		if (dynaValide.getString("transMnthYear") != null) {
			basicBean.setTransMnthYear(dynaValide.getString("transMnthYear"));
		}
		if (dynaValide.getString("fhName") != null) {
			basicBean.setFhName(dynaValide.getString("fhName"));
		}
		if (dynaValide.getString("wetherOption") != null) {
			basicBean.setWetherOption(dynaValide.getString("wetherOption"));
		}
		if (dynaValide.getString("emoluments") != null) {
			basicBean.setEmoluments(dynaValide.getString("emoluments"));
		}
		if(basicBean.getAdvanceType().equals("CPF")){
		if (dynaValide.getString("advReqAmnt") != null) {
			basicBean.setAdvReqAmnt(dynaValide.getString("advReqAmnt"));
		}
		}else{
		if (dynaValide.getString("pfwReqAmnt") != null) {
			basicBean.setAdvReqAmnt(dynaValide.getString("pfwReqAmnt"));
		}
		}
		/**new fields--*/
		if (dynaValide.getString("recEmpSubAmnt") != null && !dynaValide.getString("recEmpSubAmnt").equals("")) {
			basicBean.setRecEmpSubAmnt(dynaValide.getString("recEmpSubAmnt"));
		}else{
			basicBean.setRecEmpSubAmnt("0.00");
		}
		if (dynaValide.getString("recEmpConrtiAmnt") != null && !dynaValide.getString("recEmpConrtiAmnt").equals("")) {
			basicBean.setRecEmpConrtiAmnt(dynaValide.getString("recEmpConrtiAmnt"));
		}else{
			basicBean.setRecEmpConrtiAmnt("0.00");
		}
		
		if (dynaValide.getString("firstInsSubAmnt") != null && !dynaValide.getString("firstInsSubAmnt").equals("")) {
			basicBean.setFirstInsSubAmnt(dynaValide.getString("firstInsSubAmnt"));
		}else{
			basicBean.setFirstInsSubAmnt("0.00");
		}
		if (dynaValide.getString("firstInsConrtiAmnt") != null && !dynaValide.getString("firstInsConrtiAmnt").equals("")) {
			basicBean.setFirstInsConrtiAmnt(dynaValide.getString("firstInsConrtiAmnt"));
		}else{
			basicBean.setFirstInsConrtiAmnt("0.00");
		}
		if (dynaValide.getString("advReasonText") != null) {
			basicBean.setAdvReasonText(dynaValide.getString("advReasonText"));
		}
		if (dynaValide.getString("subscriptionAmt") != null) {
			basicBean.setPfStatury(dynaValide.getString("subscriptionAmt"));
		}
		
		// -----------------------Note Sheet--------------------------------
		if (dynaValide.getString("station") != null) {
			basicBean.setStation(dynaValide.getString("station"));
		}
		log.info("-----seperationreason------"
				+ dynaValide.getString("seperationreason"));
		if (dynaValide.getString("seperationreason") != null) {
			basicBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("seperationdate") != null) {
			basicBean.setSeperationdate(dynaValide.getString("seperationdate"));
		}
		if (dynaValide.getString("nomineename") != null) {
			basicBean.setNomineename(dynaValide.getString("nomineename"));
		}
		if (dynaValide.getString("seperationfavour") != null) {
			basicBean.setSeperationfavour(dynaValide
					.getString("seperationfavour"));
		}
		if (dynaValide.getString("seperationremarks") != null) {
			basicBean.setSeperationremarks(dynaValide
					.getString("seperationremarks"));
		}
		if (dynaValide.getString("amtadmtdate") != null) {
			basicBean.setAmtadmtdate(dynaValide.getString("amtadmtdate"));
		}
		
		if (dynaValide.getString("trust") != null) {
			basicBean.setTrust(dynaValide.getString("trust"));
		}
		
		if (dynaValide.getString("sanctiondt") != null) {
			basicBean.setSanctiondt(dynaValide.getString("sanctiondt"));
		}
		

		
		
		return basicBean;
	}
	
	private AdvanceBasicBean fillDynaFormNextToBean(ActionForm form,
			String advanceType, String purposeType, String withDrawalList) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean basicBean = new AdvanceBasicBean();
		String purposeOptionType = "";
		log.info("advanceType" + advanceType + "purposeType" + purposeType
				+ "advanceobligexp" + dynaValide.getString("advanceobligexp"));
		if (advanceType.equals("pfw") && purposeType.equals("HBA")) {
			purposeOptionType = dynaValide.getString("hbapurposetype");
		} else if (advanceType.equals("pfw") && purposeType.equals("HE")) {
			purposeOptionType = dynaValide.getString("pfwhetype");
		} else if (advanceType.equals("pfw") && purposeType.equals("Marriage")) {
			purposeOptionType = dynaValide.getString("empfmlydtls");
		} else if (advanceType.equals("CPF") && purposeType.equals("cost")) {
			purposeOptionType = dynaValide.getString("advancecostexp");
		} else if (advanceType.equals("CPF") && purposeType.equals("illness")) {
			purposeOptionType = dynaValide.getString("advancecostexp");
		} else if (advanceType.equals("CPF")
				&& purposeType.equals("obMarriage")) {
			purposeOptionType = dynaValide.getString("cpfmarriageexp");
		} else if (advanceType.equals("CPF")
				&& purposeType.equals("obligatory")
				&& dynaValide.getString("advanceobligexp").equals(
				"Other Ceremonies")) {
			purposeOptionType = dynaValide.getString("advanceobligexp") + "-"
			+ dynaValide.getString("oblcermoney");
		} else if (advanceType.equals("CPF") && purposeType.equals("education")) {
			purposeOptionType = dynaValide.getString("pfwhetype");
		}
		if (advanceType.equals("CPF") && !purposeType.equals("obMarriage")) {
			basicBean.setAdvncerqddepend(dynaValide
					.getString("advncerqddepend"));
			basicBean.setUtlisiedamntdrwn(dynaValide
					.getString("utlisiedamntdrwn"));
		}
		log.info(dynaValide.getString("advncerqddepend"));
		log.info("AdvanceAction::fillDynaFormNextToBean"
				+ dynaValide.getString("cpfmarriageexp"));
		log.info("AdvanceAction::fillDynaFormNextToBean======="
				+ basicBean.getAdvncerqddepend() + "Utilized"
				+ basicBean.getUtlisiedamntdrwn());
		basicBean.setPurposeOptionType(purposeOptionType);
		if (dynaValide.getString("fmlyempname") != null) {
			basicBean.setFmlyEmpName(dynaValide.getString("fmlyempname"));
		}
		if (dynaValide.getString("herecog") != null) {
			basicBean.setHeRecog(dynaValide.getString("herecog"));
		}
		if (dynaValide.getString("fmlydob") != null) {
			basicBean.setFmlyDOB(dynaValide.getString("fmlydob"));
		}
		if (dynaValide.getString("fmlyage") != null) {
			basicBean.setFmlyAge(dynaValide.getString("fmlyage"));
		}
		
		if (dynaValide.getString("brthcertprove") != null) {
			basicBean.setBrthCertProve(dynaValide.getString("brthcertprove"));
		}
		
		if (dynaValide.getString("hbarepaymenttype") != null) {
			basicBean.setHbarepaymenttype(dynaValide
					.getString("hbarepaymenttype"));
		}
		if (dynaValide.getString("hbaloanname") != null) {
			basicBean.setHbaloanname(dynaValide.getString("hbaloanname"));
		}
		if (dynaValide.getString("hbaloanaddress") != null) {
			basicBean.setHbaloanaddress(dynaValide.getString("hbaloanaddress"));
		}
		if (!dynaValide.getString("osamountwithinterest").equals("")) {
			basicBean.setOsamountwithinterest(dynaValide
					.getString("osamountwithinterest"));
		} else {
			basicBean.setOsamountwithinterest("0.00");
		}
		if (dynaValide.getString("hbaownername") != null) {
			basicBean.setHbaownername(dynaValide.getString("hbaownername"));
		}
		if (!withDrawalList.equals("")) {
			basicBean.setWthdrwlist(withDrawalList);
		} else {
			String withDrawals = "";
			
			if (!dynaValide.getString("wthdrwlpurpose").equals("")) {
				withDrawals = dynaValide.getString("wthdrwlpurpose");
			} else {
				withDrawals = "-";
			}
			
			if (!dynaValide.getString("wthdrwlAmount").equals("")) {
				withDrawals += "+" + dynaValide.getString("wthdrwlAmount");
			}
			
			if (dynaValide.getString("wthDrwlTrnsdt") != null) {
				withDrawals += "+" + dynaValide.getString("wthDrwlTrnsdt");
			}
			basicBean.setWthdrwlist(withDrawals);
			
		}
		if (dynaValide.getString("wthDrwlTrnsdt") != null) {
			basicBean.setWthDrwlTrnsdt(dynaValide.getString("wthDrwlTrnsdt"));
		}
		if (!dynaValide.getString("wthdrwlAmount").equals("")) {
			basicBean.setWthdrwlAmount(dynaValide.getString("wthdrwlAmount"));
		} else {
			basicBean.setWthdrwlAmount("0.00");
		}
		if (dynaValide.getString("hbaowneraddress") != null) {
			basicBean.setHbaowneraddress(dynaValide
					.getString("hbaowneraddress"));
		}
		if (dynaValide.getString("advReasonText") != null) {
			basicBean.setAdvReasonText(dynaValide
					.getString("advReasonText"));
		}
		if (dynaValide.getString("hbaownerarea") != null) {
			basicBean.setHbaownerarea(dynaValide.getString("hbaownerarea"));
		}
		if (dynaValide.getString("hbaownerplotno") != null) {
			basicBean.setHbaownerplotno(dynaValide.getString("hbaownerplotno"));
		}
		if (dynaValide.getString("hbaownerlocality") != null) {
			basicBean.setHbaownerlocality(dynaValide
					.getString("hbaownerlocality"));
		}
		if (dynaValide.getString("hbaownermuncipality") != null) {
			basicBean.setHbaownermuncipal(dynaValide
					.getString("hbaownermuncipality"));
		}
		if (dynaValide.getString("hbaownercity") != null) {
			basicBean.setHbaownercity(dynaValide.getString("hbaownercity"));
		}
		if (dynaValide.getString("hbadrwnfrmaai") != null) {
			basicBean.setHbadrwnfrmaai(dynaValide.getString("hbadrwnfrmaai"));
		}
		if (dynaValide.getString("hbapermissionaai") != null) {
			basicBean.setHbapermissionaai(dynaValide
					.getString("hbapermissionaai"));
		}
		if (dynaValide.getString("propertyaddress") != null) {
			basicBean.setPropertyaddress(dynaValide
					.getString("propertyaddress"));
		}
		if (!dynaValide.getString("actualcost").equals("")) {
			basicBean.setActualcost(dynaValide.getString("actualcost"));
		} else {
			basicBean.setActualcost("0.00");
		}
		if (dynaValide.getString("wthDrwlTrnsdt") != null) {
			basicBean.setWthDrwlTrnsdt(dynaValide.getString("wthDrwlTrnsdt"));
		}
		if (!dynaValide.getString("wthdrwlAmount").equals("")) {
			basicBean.setWthdrwlAmount(dynaValide.getString("wthdrwlAmount"));
		} else {
			basicBean.setWthdrwlAmount("0.00");
		}
		if (!dynaValide.getString("wthdrwlpurpose").equals("")) {
			basicBean.setWthdrwlpurpose(dynaValide.getString("wthdrwlpurpose"));
		} else {
			basicBean.setWthdrwlpurpose("");
		}
		if (dynaValide.getString("chkwthdrwlinfo") != null) {
			basicBean.setChkWthdrwlInfo(dynaValide.getString("chkwthdrwlinfo"));
		}
		if (dynaValide.getString("modeofpartyname") != null) {
			basicBean.setPartyName(dynaValide.getString("modeofpartyname"));
		}
		if (dynaValide.getString("modeofpartyaddrs") != null) {
			basicBean.setPartyAddress(dynaValide.getString("modeofpartyaddrs"));
		}
		if (!dynaValide.getString("hbawthdrwlpurpose").equals("")) {
			basicBean.setHbawthdrwlpurpose(dynaValide
					.getString("hbawthdrwlpurpose"));
		} else {
			basicBean.setHbawthdrwlpurpose("");
		}
		
		if (!dynaValide.getString("hbawthdrwlamount").equals("")) {
			basicBean.setHbawthdrwlamount(dynaValide
					.getString("hbawthdrwlamount"));
		} else {
			basicBean.setHbawthdrwlamount("0.00");
		}
		
		if (!dynaValide.getString("hbawthdrwladdress").equals("")) {
			basicBean.setHbawthdrwladdress(dynaValide
					.getString("hbawthdrwladdress"));
		} else {
			basicBean.setHbawthdrwladdress("");
		}
		
		if (dynaValide.getString("marriagedate") != null) {
			basicBean.setMarriagedate(dynaValide.getString("marriagedate"));
		} else {
			basicBean.setMarriagedate("");
		}
		if (dynaValide.getString("nmcourse") != null) {
			basicBean.setNmCourse(dynaValide.getString("nmcourse"));
		}
		if (dynaValide.getString("nminstitue") != null) {
			basicBean.setNmInstitue(dynaValide.getString("nminstitue"));
		}
		if (dynaValide.getString("addrsinstitue") != null) {
			basicBean.setAddrInstitue(dynaValide.getString("addrsinstitue"));
		}
		log.info("-----------course duration----------"
				+ dynaValide.getString("curseduration"));
		if (dynaValide.getString("curseduration") != null) {
			basicBean.setCurseDuration(dynaValide.getString("curseduration"));
		}
		if (dynaValide.getString("helastexaminfo") != null) {
			basicBean.setHeLastExaminfo(dynaValide.getString("helastexaminfo"));
		}
		if (dynaValide.getString("subscriptionAmt") != null) {
			basicBean.setPfStatury(dynaValide.getString("subscriptionAmt"));
		}
		return basicBean;
	}
	
	private EmpBankMaster fillDynaFormBankToBean(ActionForm form) {
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		log.info("-----Check bank info value in action----"
				+ dynaValide.getString("chkbankinfo"));
		
		if (dynaValide.getString("chkbankinfo") != null) {
			bankMaster.setChkBankInfo(dynaValide.getString("chkbankinfo"));
		} else {
			bankMaster.setChkBankInfo("");
		}
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		if (dynaValide.getString("bankempaddrs") != null) {
			bankMaster.setBankempaddrs(dynaValide.getString("bankempaddrs"));
		}
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}
		if (dynaValide.getString("branchaddress") != null) {
			bankMaster.setBranchaddress(dynaValide.getString("branchaddress"));
		}
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}
		if (dynaValide.getString("empmailid") != null) {
			bankMaster.setEmpmailid(dynaValide.getString("empmailid"));
		}
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("bankempmicrono") != null) {
			bankMaster
			.setBankempmicrono(dynaValide.getString("bankempmicrono"));
		}
		
		if (dynaValide.getString("bankpaymentmode") != null) {
			bankMaster.setBankpaymentmode(dynaValide.getString("bankpaymentmode"));
		}
		
		if (dynaValide.getString("city") != null) {
			bankMaster.setCity(dynaValide.getString("city"));
		}
		
		return bankMaster;
	}
	
	
	public ActionForward advanceReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();
		EmpBankMaster reportBankBean = new EmpBankMaster();
		ArrayList wthdrwList = new ArrayList();
		ArrayList list = new ArrayList();
		String prpsOptionsCPF = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", transactionNo = "", rtrnPage = "";
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		
		log.info("advanceReport::pensionNo" + pensionNo + "transactionNo"
				+ transactionNo);
		list = ptwAdvanceService.advanceReport(pensionNo, transactionNo);
		log.info("AdvanceAction::advanceReport::Size" + list.size());
		advanceReportBean = (AdvanceBasicReportBean) list.get(0);
		reportBankBean = (EmpBankMaster) list.get(1);
		request.setAttribute("reportBean", advanceReportBean);
		request.setAttribute("reportBankBean", reportBankBean);
		if (advanceReportBean.getPurposeType().equals("COST")) {
			prpsOptionsCPF = advanceReportBean.getPurposeType();
		} else if (advanceReportBean.getPurposeType().equals("ILLNESS")) {
			prpsOptionsCPF = advanceReportBean.getPurposeType();
		}
		log.info("AdvanceAction::advanceReport:::Purpose Type"
				+ advanceReportBean.getPurposeType() + "prpsOptionsCPF"
				+ prpsOptionsCPF);
		if (advanceReportBean.getChkWthdrwlInfo().equals("Y")) {
			wthdrwList = (ArrayList) list.get(2);
			request.setAttribute("wthdrwlist", wthdrwList);
		}
		request.setAttribute("prpsOptionsCPF", prpsOptionsCPF);
		if (advanceReportBean.getAdvanceType().toLowerCase().equals("pfw")) {
			rtrnPage = "viewPFWReport";
		} else {
			rtrnPage = "viewCPFReport";
		}
		return mapping.findForward(rtrnPage);
	}
	
	/*public ActionForward saveCPFForm2(ActionMapping mapping, ActionForm form,
	 HttpServletRequest request, HttpServletResponse response) {
	 AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
	 DynaValidatorForm dynaValide = (DynaValidatorForm) form;
	 advneFrm2Bean = this.fillFrmForm2Bean(form, request);
	 int totalRecUpdated = 0;
	 String advanceType = "", frmName = "", path = "", recAmt = "",frmFlag="";
	 
	 if (dynaValide.getString("interestinstallments") != null) {
	 advneFrm2Bean.setInterestinstallments(dynaValide
	 .getString("interestinstallments"));
	 }
	 if (dynaValide.getString("intinstallmentamt") != null) {
	 advneFrm2Bean.setIntinstallmentamt(dynaValide
	 .getString("intinstallmentamt"));
	 }
	 if (dynaValide.getString("advanceType") != null) {
	 advanceType = dynaValide.getString("advanceType");
	 }
	 
	 if (request.getParameter("rec_amt") != null) {
	 recAmt = request.getParameter("rec_amt");
	 advneFrm2Bean.setAmntRecommended(recAmt);
	 }
	 
	 log.info("======recAmt=======" + recAmt);
	 if (request.getParameter("frm_name") != null) {
	 frmName = request.getParameter("frm_name");
	 }
	 
	 if (request.getParameter("frmFlag") != null) {
	 frmFlag = request.getParameter("frmFlag");
	 }
	 
	 
	 totalRecUpdated = ptwAdvanceService.updateCPFAdvanceForm2(
	 advneFrm2Bean, frmName,frmFlag);
	 this.getScreenTitle(request, response, advanceType);
	 
	 return mapping.findForward("successcpfform2");
	 
	 }*/
//	 on 13-Feb-2012 for last Intrest Amnt
//	 on 21-Jan-2012 for saving bank details
//	Replace the complete method on 28-Jul-2010.above commented method before replacing
	public ActionForward saveCPFForm2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advneFrm2Bean = this.fillFrmForm2Bean(form, request);
		int totalRecUpdated = 0;
		String advanceType = "", frmName = "", path = "", recAmt = "",frmFlag="";
		String paymentInfoFlag="N",updateBankFlag="N",srcFrmName="";
		boolean tokenCheck;
		
		if ( isTokenValid(request) ) {
			tokenCheck=true;
			resetToken(request);
		}else{
			tokenCheck=false;
		}
		
		log.info("=======****tokenCheck****======="+tokenCheck);
		
		if (dynaValide.getString("totalInst") != null) {
			advneFrm2Bean.setTotalInst(dynaValide
					.getString("totalInst"));
		}
		
		if (dynaValide.getString("interestinstallments") != null) {
			advneFrm2Bean.setInterestinstallments(dynaValide
					.getString("interestinstallments"));
		}
		if (dynaValide.getString("intinstallmentamt") != null) {
			advneFrm2Bean.setIntinstallmentamt(dynaValide
					.getString("intinstallmentamt"));
		}
		if (dynaValide.getString("lastmthinstallmentamt") != null) {
			advneFrm2Bean.setLastmthinstallmentamt(dynaValide
					.getString("lastmthinstallmentamt"));
		}
		if (dynaValide.getString("advanceType") != null) {
			advanceType = dynaValide.getString("advanceType");
		}
		
		if (request.getParameter("rec_amt") != null) {
			recAmt = request.getParameter("rec_amt");
			advneFrm2Bean.setAmntRecommended(recAmt);
		}
		
		log.info("======recAmt=======" + recAmt);
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		}
		
 	
		if (dynaValide.getString("paymentinfo")!=null){		 
			paymentInfoFlag=dynaValide.getString("paymentinfo");
		} 		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 		 
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("partyName") != null) {
			bankMaster.setPartyName(dynaValide.getString("partyName"));
		}
		if (dynaValide.getString("partyAddress") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("partyAddress"));
		}
		//		Notification Screen Purpose
		if (request.getParameter("srcFrmName") != null) {
			srcFrmName = request.getParameter("srcFrmName");
		}
		if(srcFrmName.equals("Notifications")){
			path="loadNotifications";
		}else{
			path = "successcpfform2";
		}
	 	
		if(tokenCheck==true){			
			totalRecUpdated = ptwAdvanceService.updateCPFAdvanceForm2(
					advneFrm2Bean, frmName,frmFlag);
			 String bankInfo= ptwAdvanceService.updateBankInfo(advneFrm2Bean.getPensionNo(),advneFrm2Bean.getAdvanceTransID(),updateBankFlag,paymentInfoFlag,bankMaster);
	  	}
		
		resetToken(request);
		
		this.getScreenTitle(request, response, advanceType);
		log.info("========Path ==========="+path);
		return mapping.findForward(path);
		
	}
	private void fillForm2Bean(AdvanceCPFForm2Bean form2Bean, ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		dynaValide.set("employeeName", form2Bean.getEmployeeName());
		dynaValide.set("pensionNo", form2Bean.getPensionNo());
		dynaValide.set("pfid", form2Bean.getPfid());
		dynaValide.set("designation", form2Bean.getDesignation());
		dynaValide.set("department", form2Bean.getDepartment());
		dynaValide.set("dateOfMembership", form2Bean.getDateOfMembership());
		dynaValide.set("dateOfJoining", form2Bean.getDateOfJoining());
		dynaValide.set("dateOfBirth", form2Bean.getDateOfBirth());
		dynaValide.set("employeeNo", form2Bean.getEmployeeNo());
		dynaValide.set("emoluments", form2Bean.getEmoluments());
		dynaValide.set("advntrnsid", form2Bean.getAdvanceTransID());
		dynaValide.set("totalInst", form2Bean.getTotalInst());
		dynaValide.set("fhName", form2Bean.getFhName());
		dynaValide.set("region", form2Bean.getRegion());
		dynaValide.set("empnamewthblk", form2Bean.getEmpnamewthblk());
		dynaValide.set("amntrcdedrs", form2Bean.getAmntRecommended());
		dynaValide.set("amntapprvdrs", form2Bean.getAmntRecommended());
		dynaValide.set("advnceapplid", form2Bean.getAdvnceRequest());
		dynaValide.set("empshare", form2Bean.getEmpshare());
		dynaValide.set("mnthsemoluments", form2Bean.getMnthsemoluments());
		dynaValide.set("outstndamount", form2Bean.getOutstndamount());
		dynaValide.set("mnthsemoluments", form2Bean.getMnthsemoluments());
		dynaValide.set("outstndamount", form2Bean.getOutstndamount());
		dynaValide.set("purposeType", form2Bean.getPurposeType());
		dynaValide.set("advanceType", form2Bean.getAdvanceType());
		dynaValide.set("advntrnsdt", form2Bean.getAdvntrnsdt());
		dynaValide.set("prpsecvrdclse", form2Bean.getPrpsecvrdclse());
		dynaValide.set("userTransMnthEmolunts", form2Bean.getUserTransMnthEmolunts());
		
	}
//	07-Jun-2011 Radha Resovling report opening issue in screen level
	public ActionForward loadCPFVerificationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadCPFVerificationForm");
		String pensionNo = "", transactionNo = "", returnPage = "", formType = "", transactionDate = "";
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceCPFForm2Bean avneFrm2Bean = new AdvanceCPFForm2Bean(request);
		
		EmpBankMaster bankMaster = new EmpBankMaster();
		CPFPFWTransBean transBean = new CPFPFWTransBean();
		ArrayList form2List = new ArrayList();
		ArrayList transList = new ArrayList();
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frmTransDate") != null) {
			transactionDate = request.getParameter("frmTransDate");
		}
		try {
			form2List = ptwAdvanceService.loadCPFAdvanceForm2(pensionNo,
					transactionNo, formType, transactionDate);
			avneFrm2Bean = (AdvanceCPFForm2Bean) form2List.get(0);
			log
			.info("--------getAdvntrnsdt() in action--------"
					+ avneFrm2Bean.getAdvntrnsdt() + "formType====="
					+ formType);
			
			
			
			bankMaster = (EmpBankMaster) form2List.get(1);
			if (formType.equals("Y")) {
				this.fillForm2Bean(avneFrm2Bean, form);
				dynaValide.set("advanceTransIDDec", avneFrm2Bean
						.getAdvanceTransIDDec());
				dynaValide.set("authorizedsts", avneFrm2Bean.getTransStatus());
				
				returnPage = "loadCPFverificationform";
			} else {
				
				request.setAttribute("reportBean", avneFrm2Bean);
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String path = getServlet().getServletContext().getRealPath("/")
				+ "/uploads/dbf/";
				transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
						transactionNo,
						Constants.APPLICATION_PROCESSING_CPF_VERFICATION, path);
				log.info("transList size" + transList.size());
				returnPage = "CPFverificationreport";
				
				if (transList.size() > 0) {
					transBean = (CPFPFWTransBean) transList.get(0);
					request.setAttribute("transBean", transBean);	
					request.setAttribute("transList", transList);
				} 
				returnPage = "CPFverificationreport";
				
			}
			
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			
			log.printStackTrace(e);
			returnPage = "loadCPFverificationform";
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("CPFverificationreport");
		}
		if(returnPage.equals("loadCPFverificationform")){
			saveToken(request);
			}
		dynaValide.set("interestRate", avneFrm2Bean.getInterestRate());
		return mapping.findForward(returnPage);
	}
	
	/*public ActionForward saveCPFVerification(ActionMapping mapping,
	 ActionForm form, HttpServletRequest request,
	 HttpServletResponse response) {
	 AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
	 DynaValidatorForm dynaValide = (DynaValidatorForm) form;
	 int totalRecUpdated = 0;
	 advneFrm2Bean = this.fillFrmForm2Bean(form, request);
	 log.info("EmpShare" + dynaValide.getString("empshare"));
	 if (dynaValide.getString("interestinstallments") != null) {
	 advneFrm2Bean.setInterestinstallments(dynaValide
	 .getString("interestinstallments"));
	 }
	 
	 if (dynaValide.getString("intinstallmentamt") != null) {
	 advneFrm2Bean.setIntinstallmentamt(dynaValide
	 .getString("intinstallmentamt"));
	 }
	 
	 log.info("-----User name----" + advneFrm2Bean.getLoginUserName());
	 log.info("-----Unit----" + advneFrm2Bean.getLoginUnitCode());
	 log.info("-----Region----" + advneFrm2Bean.getLoginRegion());
	 
	 totalRecUpdated = ptwAdvanceService
	 .updateCPFVerification(advneFrm2Bean);
	 return mapping.findForward("CPFForm2Success");
	 }*/
//	Replace the complete method on 28-Jul-2010.above commented method before replacing
	
	
	public ActionForward saveCPFVerification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		int totalRecUpdated = 0;
		boolean tokenCheck;
		
		if ( isTokenValid(request) ) {
			tokenCheck=true;
			resetToken(request);
		}else{
			tokenCheck=false;
		}
		log.info("=======****tokenCheck****======="+tokenCheck);
		
		advneFrm2Bean = this.fillFrmForm2Bean(form, request);
		log.info("EmpShare" + dynaValide.getString("empshare"));
		
		if (dynaValide.getString("totalInst") != null) {
			advneFrm2Bean.setTotalInst(dynaValide
					.getString("totalInst"));
		}
		
		if (dynaValide.getString("interestinstallments") != null) {
			advneFrm2Bean.setInterestinstallments(dynaValide
					.getString("interestinstallments"));
		}
		if (dynaValide.getString("intinstallmentamt") != null) {
			advneFrm2Bean.setIntinstallmentamt(dynaValide
					.getString("intinstallmentamt"));
		}
		if (dynaValide.getString("lastmthinstallmentamt") != null) {
			advneFrm2Bean.setLastmthinstallmentamt(dynaValide
					.getString("lastmthinstallmentamt"));
		}
		if(tokenCheck==true){			
			totalRecUpdated = ptwAdvanceService
			.updateCPFVerification(advneFrm2Bean);
			
		}
		
		resetToken(request);			
		
		return mapping.findForward("CPFForm2Success");
	}
	private AdvanceCPFForm2Bean fillFrmForm2Bean(ActionForm form,
			HttpServletRequest request) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceCPFForm2Bean form2Bean = new AdvanceCPFForm2Bean(request);
		if (dynaValide.getString("employeeName") != null) {
			form2Bean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (dynaValide.getString("pensionNo") != null) {
			form2Bean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		if (dynaValide.getString("mthinstallmentamt") != null) {
			form2Bean.setMthinstallmentamt(dynaValide
					.getString("mthinstallmentamt"));
		}
		if (dynaValide.getString("advntrnsid") != null) {
			form2Bean.setAdvanceTransID(dynaValide.getString("advntrnsid"));
			if (!form2Bean.getAdvanceTransID().equals("")) {
				String[] fndTransID = form2Bean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					form2Bean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		if (dynaValide.getString("outstndamount") != null) {
			form2Bean.setOutstndamount(dynaValide.getString("outstndamount"));
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			form2Bean.setAmntRecommended(dynaValide.getString("amntrcdedrs"));
		}
		if (dynaValide.getString("amntapprvdrs") != null) {
			form2Bean.setAmntAproved(dynaValide.getString("amntapprvdrs"));
		}
		if (dynaValide.getString("amntapprvdrs") != null) {
			form2Bean.setAmntAproved(dynaValide.getString("amntapprvdrs"));
		}
		
		if (dynaValide.getString("authorizedrmrks") != null) {
			form2Bean.setAuthrizedRemarks(dynaValide
					.getString("authorizedrmrks"));
		}
		if (dynaValide.getString("authorizedsts") != null) {
			form2Bean.setAuthrizedStatus(dynaValide.getString("authorizedsts"));
		}
		if (dynaValide.getString("prpsecvrdclse") != null) {
			form2Bean.setPrpsecvrdclse(dynaValide.getString("prpsecvrdclse"));
		}
		if (dynaValide.getString("purposeType") != null) {
			form2Bean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		if (dynaValide.getString("advanceType") != null) {
			form2Bean.setAdvanceType(dynaValide.getString("advanceType"));
		}
		log.info("fillFrmForm2Bean::Advance Type" + form2Bean.getAdvanceType());
		if (form2Bean.getAdvanceType().equals("PFW")) {
			if (dynaValide.getString("takenloan") != null) {
				form2Bean.setTakenloan(dynaValide.getString("takenloan"));
			}
		}
		
		if (dynaValide.getString("mnthsemoluments") != null) {
			form2Bean.setEmoluments(dynaValide.getString("mnthsemoluments"));
		}
		if (dynaValide.getString("empshare") != null) {
			form2Bean.setEmpshare(dynaValide.getString("empshare"));
		}
		return form2Bean;
		
	}
	
	private AdvanceCPFForm2Bean fillFrmForm2Bean(ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceCPFForm2Bean form2Bean = new AdvanceCPFForm2Bean();
		if (dynaValide.getString("employeeName") != null) {
			form2Bean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (dynaValide.getString("pensionNo") != null) {
			form2Bean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		if (dynaValide.getString("mthinstallmentamt") != null) {
			form2Bean.setMthinstallmentamt(dynaValide
					.getString("mthinstallmentamt"));
		}
		if (dynaValide.getString("advntrnsid") != null) {
			form2Bean.setAdvanceTransID(dynaValide.getString("advntrnsid"));
			if (!form2Bean.getAdvanceTransID().equals("")) {
				String[] fndTransID = form2Bean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					form2Bean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		if (dynaValide.getString("outstndamount") != null) {
			form2Bean.setOutstndamount(dynaValide.getString("outstndamount"));
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			form2Bean.setAmntRecommended(dynaValide.getString("amntrcdedrs"));
		}
		if (dynaValide.getString("amntapprvdrs") != null) {
			form2Bean.setAmntAproved(dynaValide.getString("amntapprvdrs"));
		}
		if (dynaValide.getString("amntapprvdrs") != null) {
			form2Bean.setAmntAproved(dynaValide.getString("amntapprvdrs"));
		}
		
		if (dynaValide.getString("authorizedrmrks") != null) {
			form2Bean.setAuthrizedRemarks(dynaValide
					.getString("authorizedrmrks"));
		}
		if (dynaValide.getString("authorizedsts") != null) {
			form2Bean.setAuthrizedStatus(dynaValide.getString("authorizedsts"));
		}
		if (dynaValide.getString("prpsecvrdclse") != null) {
			form2Bean.setPrpsecvrdclse(dynaValide.getString("prpsecvrdclse"));
		}
		if (dynaValide.getString("purposeType") != null) {
			form2Bean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		if (dynaValide.getString("advanceType") != null) {
			form2Bean.setAdvanceType(dynaValide.getString("advanceType"));
		}
		log.info("fillFrmForm2Bean::Advance Type" + form2Bean.getAdvanceType());
		if (form2Bean.getAdvanceType().equals("PFW")) {
			if (dynaValide.getString("takenloan") != null) {
				form2Bean.setTakenloan(dynaValide.getString("takenloan"));
			}
		}
		
		if (dynaValide.getString("mnthsemoluments") != null) {
			form2Bean.setEmoluments(dynaValide.getString("mnthsemoluments"));
		}
		if (dynaValide.getString("empshare") != null) {
			form2Bean.setEmpshare(dynaValide.getString("empshare"));
		}
		return form2Bean;
		
	}
	
	public ActionForward loadLookupPFID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("AdvancesAction::loadLookupPFID");
		String selectedPFID = "", selectedEmpName = "", selectedRegion = "", returnPageFlag = "", page = "";
		ArrayList personalList = new ArrayList();
		request.setAttribute("focusFlag", "true");
		page = "loadPersonalForm";
		
		return mapping.findForward(page);
	}
	
	// -------------------------Note Sheet
	// Form-----------------------------------------
	public ActionForward loadNoteSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("AdvancesAction::loadNoteSheet");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		request.setAttribute("focusFlag", "false");
		dynaValide.set("employeeName", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("pfid", "");
		dynaValide.set("designation", "");
		dynaValide.set("department", "");
		dynaValide.set("dateOfMembership", "");
		dynaValide.set("dateOfJoining", "");
		dynaValide.set("dateOfBirth", "");
		dynaValide.set("employeeNo", "");
		dynaValide.set("cpfaccno", "");
		dynaValide.set("region", "");
		dynaValide.set("transMnthYear", "");
		dynaValide.set("fhName", "");
		dynaValide.set("wetherOption", "");
		
		return mapping.findForward("loadNoteSheet");
	}
	
	/*	public ActionForward saveNoteSheet(ActionMapping mapping, ActionForm form,
	 HttpServletRequest request, HttpServletResponse response) {
	 AdvanceBasicBean advanceBean = new AdvanceBasicBean();
	 String userName = "", computerName = "", messages = "", totalInstall = "", pensionNo = "", station = "";
	 boolean flagval = false;
	 
	 session = request.getSession(true);
	 if (session.getAttribute("userid") != null) {
	 userName = (String) session.getAttribute("userid");
	 computerName = (String) session.getAttribute("computername");
	 }
	 EmpBankMaster bankMaster = new EmpBankMaster();
	 ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
	 String pfwAdvanceType = "", pfwPurposeType = "", advPurpose = "", deleteStatus = "";
	 StringBuffer nomineeRow = new StringBuffer();
	 
	 DynaValidatorForm dynaValide = (DynaValidatorForm) form;
	 
	 if (request.getParameterValues("nomineename") != null) {
	 
	 String slno[] = request.getParameterValues("serialNo");
	 String Nname[] = request.getParameterValues("nomineename");
	 String Naddress[] = request.getParameterValues("nomineeaddress");
	 String Ndob[] = request.getParameterValues("nomineeDOB");
	 String Nrelation[] = request.getParameterValues("nomineerelation");
	 String guardianname[] = request.getParameterValues("gardianname");
	 String gaddress[] = request.getParameterValues("gardianaddress");
	 String totalshare[] = request.getParameterValues("totalshare");
	 String flag[] = request.getParameterValues("nomineeflag");
	 String deletionStatus[] = request
	 .getParameterValues("deletionStatus");
	 System.out.println(Nname.length);
	 
	 String srno = "", nAddress = "", nDob = "", nRelation = "", nGuardianname = "", nTotalshare = "", gardaddressofNaminee = "", nFlag = "";
	 int incsrno = 0;
	 String empOldNomineeName = "";
	 for (int i = 0; i < Nname.length; i++) {
	 if (!Nname[i].equals("")) {
	 nomineeRow.append(Nname[i].toString() + "@");
	 if (Naddress[i].equals("")) {
	 nAddress = "XXX";
	 } else {
	 nAddress = Naddress[i].toString();
	 }
	 if (Nrelation[i].equals("")) {
	 nRelation = "XXX";
	 } else {
	 nRelation = Nrelation[i].toString();
	 }
	 
	 if (Ndob[i].equals("")) {
	 nDob = "XXX";
	 } else {
	 nDob = Ndob[i].toString();
	 }
	 if (guardianname[i].equals("")) {
	 nGuardianname = "XXX";
	 } else {
	 nGuardianname = guardianname[i].toString();
	 }
	 if (gaddress[i].equals("")) {
	 gardaddressofNaminee = "XXX";
	 } else {
	 gardaddressofNaminee = gaddress[i].toString().trim();
	 }
	 
	 if (totalshare[i].equals("")) {
	 nTotalshare = "XXX";
	 } else {
	 nTotalshare = totalshare[i].toString();
	 }
	 
	 if (slno[i].equals("")) {
	 srno = incsrno + 1 + "";
	 flagval = true;
	 } else {
	 srno = slno[i].toString();
	 incsrno = Integer.parseInt(srno);
	 }
	 
	 if (flag[i].equals("")) {
	 nFlag = "N";
	 } else {
	 nFlag = flag[i].toString();
	 }
	 if (deletionStatus[i].equals("")) {
	 deleteStatus = "N";
	 } else {
	 deleteStatus = "D";
	 }
	 nomineeRow.append(nAddress + "@");
	 nomineeRow.append(nDob + "@");
	 nomineeRow.append(nRelation + "@");
	 nomineeRow.append(nGuardianname + "@");
	 nomineeRow.append(gardaddressofNaminee + "@");
	 nomineeRow.append(nTotalshare + "@");
	 nomineeRow.append(srno.trim() + "@");
	 nomineeRow.append(nFlag + "@");
	 nomineeRow.append(deleteStatus);
	 nomineeRow.append("***");
	 
	 if (flagval == true)
	 incsrno++;
	 }
	 
	 }
	 log.info("Nominee data ----------- " + nomineeRow.toString());
	 System.out.println("Nominee data " + nomineeRow.toString());
	 }
	 if (request.getParameter("frm_station") != null) {
	 station = request.getParameter("frm_station");
	 }
	 advanceBean = this.fillDynaFormToBean(form, request);
	 
	 if (nomineeRow.toString() != null) {
	 advanceBean.setNomineeRow(nomineeRow.toString());
	 } else {
	 advanceBean.setNomineeRow("");
	 }
	 if (!station.equals("")) {
	 advanceBean.setStation(station);
	 } else {
	 advanceBean.setStation("");
	 }
	 
	 if (!dynaValide.getString("adhocamt").equals("")) {
	 advanceBean.setAdhocamt(dynaValide.getString("adhocamt"));
	 } else {
	 advanceBean.setAdhocamt("0.0");
	 }
	 ptwAdvanceService.addNoteSheet(advanceBean, bankMaster);
	 return mapping.findForward("success");
	 }*/
	
	public ActionForward loadNoteSheetSearchForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadNoteSheetSearchForm");
		
		this.getScreenTitle(request, response, "");
		return mapping.findForward("searchNoteSheet");
	}
	
	public ActionForward searchNoteSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String frmName = "";
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		
		if (dynaValide.getString("nssanctionno") != null) {
			searchBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		
		if (dynaValide.getString("sanctiondt") != null) {
			searchBean.setSanctiondt(dynaValide.getString("sanctiondt"));
		}
		
		if (dynaValide.getString("paymentdt") != null) {
			searchBean.setPaymentdt(dynaValide.getString("paymentdt"));
		}
		
		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("trust") != null) {
			searchBean.setTrust(dynaValide.getString("trust"));
		}
		
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchNoteSheet(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("success");
		
	}
//	On 08-Mar-2011 by Radha for Termination Report case
//	On 08-Apr-2011 by Radha for Death Report case
//	On 03-May-2011 by Radha  to show differences to old n new format of NoteSheet order for VRS case
//	On 04-May-2011 by Radha  to show differences to old n new format of NoteSheet order for all  cases
	public ActionForward noteSheetReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();	 
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();		
		ptwAdvanceService = new CPFPTWAdvanceService();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String pensionNo = "", nsSanctionNo = "", seperationyr = "", frmName = "", seperationnextyr = "", path = "", currentYear = "", currentYear1 = "", previousYear2 = "", previousYear1 = "";
		int currentMonth = 0, nextYear = 0, previousYear = 0;
		try {
			if (request.getParameter("frmPensionNo") != null) {
				pensionNo = request.getParameter("frmPensionNo");
			}
			if (request.getParameter("frmSanctionNo") != null) {
				nsSanctionNo = request.getParameter("frmSanctionNo");
			}
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			
			
			
			list = ptwAdvanceService.noteSheetReport(pensionNo, nsSanctionNo);
			log.info("Size" + list.size());
			advanceReportBean = (AdvanceBasicReportBean) list.get(0);			 
			
			if (advanceReportBean.getSeperationreason().equals("Death")) {
				 
				log.info("----------Multiple Nominee list size in action---------"
						+ advanceReportBean.getMultipleNomineeList().size());
				 if(advanceReportBean.getMultipleNomineeList() != null) {
						if (advanceReportBean.getMultipleNomineeList().size() > 0) {
							multipleNomineeList = (ArrayList) advanceReportBean
							.getMultipleNomineeList();
							request.setAttribute("multipleNomineeList", multipleNomineeList);
						}
					}
				
			}
			
			
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,nsSanctionNo);
	
			
			if(advanceReportBean.getPaymentinfo().equals("Y")){
			request.setAttribute("bankMasterBean", bankMasterBean);
			}
			
			currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
			currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
			currentYear1 = commonUtil.getCurrentDate("yyyy").substring(0, 4);
			
			nextYear = Integer.parseInt(currentYear) + 1;
			previousYear = Integer.parseInt(currentYear1) - 1;
			previousYear2 = Integer.toString(previousYear);
			
			if (previousYear <= 9)
				previousYear1 = Integer.toString(previousYear);
			
			if (currentMonth >= 04)
				advanceReportBean.setTransMnthYear(currentYear1 + "-"
						+ nextYear);
			else if (previousYear <= 9)
				advanceReportBean.setTransMnthYear(previousYear1 + "-"
						+ currentYear);
			else
				advanceReportBean.setTransMnthYear(previousYear2 + "-"
						+ currentYear);
			 
			if (advanceReportBean.getSeperationreason().equals("Resignation")
					|| advanceReportBean.getSeperationreason().equals("Death")
					|| advanceReportBean.getSeperationreason().equals("Termination")) {
				if(advanceReportBean.getSanctionOrderFlag().equals("After")){
				if (advanceReportBean.getSeperationreason().equals(
				"Resignation")) {
					path = "viewNoteSheetResignationReport";
				} else if (advanceReportBean.getSeperationreason().equals(
				"Death")) {
					path = "nsdeathreport";
				}else if (advanceReportBean.getSeperationreason().equals(
				"Termination")) {
					path = "nsterminationreport";
				}
				}else{
					if (advanceReportBean.getSeperationreason().equals(
					"Resignation")) {
						path = "viewNoteSheetResignationReportOldFormat";
					} else if (advanceReportBean.getSeperationreason().equals(
					"Death")) {
						path = "nsdeathreportOldFormat";
					}else if (advanceReportBean.getSeperationreason().equals(
					"Termination")) {
						path = "nsterminationreport";
					} 
				}
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				
				if (frmName.equals("FSFormII"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_PROCESS_FORM,
							imgagePath);
				else if (frmName.equals("FSFormIII"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR,
							imgagePath);
				else if (frmName.equals("FSFormIV"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_DGM,
							imgagePath);
				else if (frmName.equals("FSApproval"))
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_APPROVAL,
							imgagePath);
				else
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_APPROVAL,
							imgagePath);
				
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
				}
				
			} else {
				
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				
				if (frmName.equals("FSFormII"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_PROCESS_FORM,
							imgagePath);
				else if (frmName.equals("FSFormIII"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR,
							imgagePath);
				else if (frmName.equals("FSFormIV"))
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_DGM,
							imgagePath);
				else if (frmName.equals("FSApproval"))
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_APPROVAL,
							imgagePath);
				else
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_APPROVAL,
							imgagePath);
				
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
				}
				// For Retirement & VRS cases
				 
					if(advanceReportBean.getSanctionOrderFlag().equals("After")){
						path = "viewNoteSheetReport";
					}else{
						path = "viewNoteSheetReportOldFormat";
					}
				 
			}
			
			log.info("----reason--"+advanceReportBean.getSeperationreason()+"--flag--"+advanceReportBean.getSanctionOrderFlag());
			log.info("------path-----"+path);
			this.getScreenTitle(request, response, "");
			
			request.setAttribute("reportBean", advanceReportBean);
			
			/*	if (transList.size()==0) {
			 path = "failureMessage";				
			 }*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return mapping.findForward(path);
	}
	
	
	public ActionForward editNoteSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean adBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetTransBean = new AdvanceBasicBean();
		String currentDate = "", userName = "", computerName = "", messages = "", path = "", selectedEmpName = "", selectedRegion = "", pensionNo = "", nsSanctionNo = "", cpfaccno = "", frmName = "";
		boolean flagval = false;
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList bankList = new ArrayList();
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		log.info("editNoteSheet==============="+userName+"computerName==========="+computerName);
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String pfwAdvanceType = "", pfwPurposeType = "", advPurpose = "";
		StringBuffer nomineeRow = new StringBuffer();
		
		saveToken(request);
		
		if (request.getParameter("pensionno") != null) {
			pensionNo = request.getParameter("pensionno");
		}
		if (request.getParameter("nssanctionno") != null) {
			nsSanctionNo = request.getParameter("nssanctionno");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		currentDate = "-Feb-2009";
		personalList = commonService.loadPersonalTransInfo(selectedEmpName,
				selectedRegion, pensionNo, cpfaccno, "-Sep-2007");
		
		
		advanceBean = (AdvanceBasicBean) personalList.get(0);
		advanceBean.setDateOfMembership(advanceBean.getDateOfJoining());
		request.setAttribute("personalInfo", advanceBean);
		ArrayList airportsList = new ArrayList();
		if (!advanceBean.getRegion().equals("")) {
			log.info("-------Region in Action--------"
					+ advanceBean.getRegion());
			log.info("-------st in Action--------" + advanceBean.getStation());
			
			airportsList = commonUtil.getAirportList(advanceBean.getRegion());
			
			log
			.info("---------airportsList Size--------"
					+ airportsList.size());
			if (airportsList.size() > 0) {
				for (int j = 0; j < airportsList.size(); j++) {
					
					adBean = (AdvanceBasicBean) airportsList.get(j);
					log.info("-----Station in Action------"
							+ adBean.getStation());
				}
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		noteSheetBean = commonService.loadNoteSheetInfo(pensionNo, nsSanctionNo);
		request.setAttribute("noteSheetInfo", noteSheetBean);
		
		// added  to get Bank Details
		bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,nsSanctionNo);
		request.setAttribute("bankMasterBean", bankMasterBean);
		//added to get multible bank details for FS APPROVALs
		bankList=ptwAdvanceService.employeeBankInfoList(pensionNo,nsSanctionNo);
		request.setAttribute("bankMasterBeanList", bankList);
		// request.setAttribute("personalInfo",advanceBean);
		
		nomineeList = commonService.loadNomineeInfo(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		if (nomineeList.size() != 0) {
			adBean = (AdvanceBasicBean) nomineeList.get(0);
			request.setAttribute("nomineeList", nomineeList);
		}
		
		String nomineeInfo = commonService.loadNomineeDetails(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		
		
		
		if(nomineeInfo.length()>0){
			log.info("nominee informatio in advances action ---- "+nomineeInfo);
			
			request.setAttribute("nomineeInfo", nomineeInfo.substring(0,nomineeInfo.length()-1));
		}
		ArrayList newAirportsList = new ArrayList();
		log.info("------- Posting  st in Action--------"+frmName+"=====" + noteSheetBean.getPostingStation()+ noteSheetBean.getPostingRegion());
		if (noteSheetBean.getPostingFlag().equals("Y")) {
			log.info("------- Posting  st in Action--------" + noteSheetBean.getPostingStation()+ noteSheetBean.getPostingRegion());
			newAirportsList = commonUtil.getAirportList(noteSheetBean.getPostingRegion());
			request.setAttribute("newairportsList", newAirportsList);
		}
		
		dynaValide.set("remarks", "");
		dynaValide.set("nomineename", "");
		dynaValide.set("nomineeaddress", "");
		dynaValide.set("nomineeDOB", "");
		dynaValide.set("nomineerelation", "");
		dynaValide.set("gardianname", "");
		dynaValide.set("gardianaddress", "");
		dynaValide.set("totalshare", "");
		
		log.info("----pensionno in editNoteSheet()-----" + pensionNo+"noteSheetBean.getPostingFlag()========"+noteSheetBean.getPostingFlag());
		
		
		
		ArrayList transList = new ArrayList();
		CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
				this.loginUserName, this.loginUnitCode,
				this.loginRegion, this.loginSignName);
		String imgagePath = getServlet().getServletContext()
		.getRealPath("/")
		+ "/uploads/dbf/";
		
		CPFPFWTransBean transBean=new CPFPFWTransBean();
		try {
			if (frmName.equals("FSFormII")){
				
				 transBean = (CPFPFWTransBean) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_PROCESS_FORM);
					
		 }else if (frmName.equals("FSFormIII")){
			
			 transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR);
				
		 }else if (frmName.equals("FSFormIV")) {
			 transBean = (CPFPFWTransBean) cpfInfo
			.readTransInfo(
					pensionNo,
					nsSanctionNo,
					Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_DGM);
		 
		 }else if(frmName.equals("FSApproval")){
			 transBean = (CPFPFWTransBean) cpfInfo.readTransInfo(pensionNo,
					 nsSanctionNo,
					Constants.APPLICATION_PROCESSING_FINAL_APPROVAL);
			  
		 }else{
			 transBean = (CPFPFWTransBean) cpfInfo.readTransInfo(pensionNo,
					 nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_APPROVAL);
		 }
			} catch (EPISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
			
		 if((transBean.getRemarks().equals(""))||(transBean.getRemarks().equals("---"))){
			 noteSheetBean.setRemarksFlag("N");
		 }else{
			 noteSheetBean.setRemarksFlag("Y");
		 }
		 
		 
		 request.setAttribute("noteSheetInfo", noteSheetBean);
		this.getScreenTitle(request, response, "");
		HashMap regionHashmap=new HashMap();
		regionHashmap = commonUtil.getRegion();
		request.setAttribute("regionHashmap", regionHashmap);
		if (frmName.equals("FSFormIII")) {
			path = "editFSVerificationIII";
		} else if (frmName.equals("FSFormIV")) {
			path = "editFSVerificationIV";
		} else if (frmName.equals("FSApproval")) {
			path = "editFSApproval";
		} else {
			path = "editNoteSheet";
		}
		return mapping.findForward(path);
	}
	//Changes done by radha p at 15-Apr-2011
	
	/*public ActionForward updateNoteSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean(request);
		EmpBankMaster  bankMaster= new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String userName = "", computerName = "", messages = "", pensionNo = "", deleteStatus = "", frmName = "", frmFlag = "",frmNomineeDets="";
		String remarksFlag="",paymentinfo="";
		boolean flagval = false;
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		log.info("updateNoteSheet==============="+userName+"computerName==========="+computerName);
		log.info("AdvancesAction::updateNoteSheet::isTokenValid -------"+isTokenValid(request)+"frm_name"+request.getParameter("frm_name"));
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		}
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		log.info("NOMINEE NAME in ACTION is -------"+request.getParameterValues("nomineename"));
		
		
		advanceBean = this.fillDynaFormToBean(form, request);
		
		
		if (!dynaValide.getString("adhocamt").equals("")) {
			advanceBean.setAdhocamt(dynaValide.getString("adhocamt"));
		} else {
			advanceBean.setAdhocamt("0.0");
		}
		
		
		
		log.info("frmName in updateNoteSheet() is ----- "+frmName); 
		
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		}
		
		if (request.getParameter("frm_nominees") != null) {
			frmNomineeDets = request.getParameter("frm_nominees");
		}
		
		log.info("frmNomineeDets ----------"+frmNomineeDets);
		
		if (!frmNomineeDets.equals("")) {
			advanceBean.setNomineeRow(frmNomineeDets);
		} else {
			advanceBean.setNomineeRow("");
		}
		
		if (dynaValide.getString("authorizedsts") != null) {
			advanceBean.setAuthrizedStatus(dynaValide.getString("authorizedsts"));
		}
		
		
		if (dynaValide.getString("equalshare").equals("on")) {
			advanceBean.setEqualshare("Y");
		}else{
			advanceBean.setEqualshare("N");
		}
		
		
		if (request.getParameter("posting_flag") != null) {
			advanceBean.setPostingFlag(request.getParameter("posting_flag"));
			
			if(advanceBean.getPostingFlag().equals("Y")){
				if (request.getParameter("posting_region") != null) {
					advanceBean.setPostingRegion(request.getParameter("posting_region"));
				}else{
					advanceBean.setPostingRegion("");
				}
				if (request.getParameter("posting_station") != null) {
					advanceBean.setPostingStation(request.getParameter("posting_station"));
				}else{
					advanceBean.setPostingStation("");
				}
			}else{
				advanceBean.setPostingStation("");
				advanceBean.setPostingRegion("");
			}
		}else{
			advanceBean.setPostingFlag("N");
			advanceBean.setPostingStation("");
			advanceBean.setPostingRegion("");
		}
		
		if (dynaValide.getString("remarksFlag")!=null){		 
			remarksFlag = dynaValide.getString("remarksFlag");
		}
		
		advanceBean.setRemarksFlag(remarksFlag);
		log.info("-----------------IN ACTION  updateNoteSheet REMARKS FLAG----------"+remarksFlag);
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			advanceBean.setPaymentinfo(dynaValide.getString("paymentinfo"));
		}else{
		advanceBean.setPaymentinfo("N");
		}
		
		if (request.getParameter("bankflag") != null) {
			advanceBean.setUpdateBankFlag(request.getParameter("bankflag"));
		}else{
			advanceBean.setUpdateBankFlag("N");
		}
		log.info("-----------------IN ACTION  updateNoteSheet paymentinfo FLAG----------"+	advanceBean.getPaymentinfo());
		
		log.info("--New Region---"+advanceBean.getPostingRegion()+"--------New Station--------"+advanceBean.getPostingStation()+"--------postingflag--------"+advanceBean.getPostingFlag());
		
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}
		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}
		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		

		String saveNcontinue="N";
		if(request.getParameter("continueFlag")!=null){
			saveNcontinue =request.getParameter("continueFlag");
		}
		
		log.info("----saveNcontinue----"+saveNcontinue);
		
		if(!frmName.equals("")){
			String message = ptwAdvanceService.updateNoteSheet(advanceBean,
					frmName, frmFlag);
		if(saveNcontinue.equals("N")){
		 String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
		 }
		}
		ActionForward forward = mapping.findForward("success");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		params.put("method", "searchNoteSheet");
		// params.put("frm_name", "advances");
		// fwdParams.add(params);
		

		
		if((advanceBean.getSeperationreason().equals("Death"))&&(saveNcontinue.equals("Y"))){
			log.info("--in updateNoteShett-------");
			ArrayList nomineeList=null; 
			ArrayList nomineeSearchList = null;
			String nomineeName = "",nomineeSerialNo=""; 
			Map map = new LinkedHashMap();
			log.info("--in loadNominees:: pensionNo--"+advanceBean.getPensionNo()+"-nssanctionno--"+advanceBean.getNssanctionno());
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			 
			 
			for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				//log.info("- in updateNoteSheet()--nomineeSerialNo---"+nomineeSerialNo+"--nomineeName----"+nomineeName);
				map.put(nomineeSerialNo,nomineeName);
			}
			
			nomineeSearchList = ptwAdvanceService.nomineeBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				//log.info("----"+bankMasterBean.getNomineeSerialNo()+"--"+bankMasterBean.getBankempname()+"--"+bankMasterBean.getBanksavingaccno());
			}
			
			request.setAttribute("nomineeSearchList", nomineeSearchList);
			
			 dynaValide.set("bankempname","");
			 dynaValide.set("bankname","");
			 dynaValide.set("banksavingaccno","");
			 dynaValide.set("bankemprtgsneftcode","");
			 
			  request.setAttribute("nomineeHashmap", map);
			  request.setAttribute("seperateScreen","N");
			this.getScreenTitle(request, response, "BankDet");
			saveToken(request);
			String path="addnomineebankdetails";
			return mapping.findForward(path);
		}else{ 
		this.getScreenTitle(request, response, "");
		if (frmName.equals("FSFormII"))
			fwdParams.add("frm_name", "FSFormII");
		else if (frmName.equals("FSFormIII"))
			fwdParams.add("frm_name", "FSFormIII");
		else if (frmName.equals("FSFormIV"))
			fwdParams.add("frm_name", "FSFormIV");
		
		return fwdParams.forward(forward);
		}
		
		
		
	}*/
	//By Radha on 09-Feb-2012 for passing data to Nominee Bank Details Screen
	public ActionForward updateNoteSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean(request);
		EmpBankMaster  bankMaster= new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String userName = "", computerName = "", messages = "", pensionNo = "", deleteStatus = "", frmName = "", frmFlag = "",frmNomineeDets="";
		String remarksFlag="",paymentinfo="";
		AdvanceSearchBean  employeeInfo = new AdvanceSearchBean();
		boolean flagval = false;
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		log.info("updateNoteSheet==============="+userName+"computerName==========="+computerName);
		log.info("AdvancesAction::updateNoteSheet::isTokenValid -------"+isTokenValid(request)+"frm_name"+request.getParameter("frm_name"));
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		}
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		log.info("NOMINEE NAME in ACTION is -------"+request.getParameterValues("nomineename"));
		
		
		advanceBean = this.fillDynaFormToBean(form, request);
		
		
		if (!dynaValide.getString("adhocamt").equals("")) {
			advanceBean.setAdhocamt(dynaValide.getString("adhocamt"));
		} else {
			advanceBean.setAdhocamt("0.0");
		}
		
		
		
		log.info("frmName in updateNoteSheet() is ----- "+frmName); 
		
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		}
		
		if (request.getParameter("frm_nominees") != null) {
			frmNomineeDets = request.getParameter("frm_nominees");
		}
		
		log.info("frmNomineeDets ----------"+frmNomineeDets);
		
		if (!frmNomineeDets.equals("")) {
			advanceBean.setNomineeRow(frmNomineeDets);
		} else {
			advanceBean.setNomineeRow("");
		}
		
		if (dynaValide.getString("authorizedsts") != null) {
			advanceBean.setAuthrizedStatus(dynaValide.getString("authorizedsts"));
		}
		
		
		if (dynaValide.getString("equalshare").equals("on")) {
			advanceBean.setEqualshare("Y");
		}else{
			advanceBean.setEqualshare("N");
		}
		
		
		if (request.getParameter("posting_flag") != null) {
			advanceBean.setPostingFlag(request.getParameter("posting_flag"));
			
			if(advanceBean.getPostingFlag().equals("Y")){
				if (request.getParameter("posting_region") != null) {
					advanceBean.setPostingRegion(request.getParameter("posting_region"));
				}else{
					advanceBean.setPostingRegion("");
				}
				if (request.getParameter("posting_station") != null) {
					advanceBean.setPostingStation(request.getParameter("posting_station"));
				}else{
					advanceBean.setPostingStation("");
				}
			}else{
				advanceBean.setPostingStation("");
				advanceBean.setPostingRegion("");
			}
		}else{
			advanceBean.setPostingFlag("N");
			advanceBean.setPostingStation("");
			advanceBean.setPostingRegion("");
		}
		
		if (dynaValide.getString("remarksFlag")!=null){		 
			remarksFlag = dynaValide.getString("remarksFlag");
		}
		
		advanceBean.setRemarksFlag(remarksFlag);
		log.info("-----------------IN ACTION  updateNoteSheet REMARKS FLAG----------"+remarksFlag);
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			advanceBean.setPaymentinfo(dynaValide.getString("paymentinfo"));
		}else{
		advanceBean.setPaymentinfo("N");
		}
		
		if (request.getParameter("bankflag") != null) {
			advanceBean.setUpdateBankFlag(request.getParameter("bankflag"));
		}else{
			advanceBean.setUpdateBankFlag("N");
		}
		log.info("-----------------IN ACTION  updateNoteSheet paymentinfo FLAG----------"+	advanceBean.getPaymentinfo());
		
		log.info("--New Region---"+advanceBean.getPostingRegion()+"--------New Station--------"+advanceBean.getPostingStation()+"--------postingflag--------"+advanceBean.getPostingFlag());
		
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}
		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}
		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		

		String saveNcontinue="N";
		if(request.getParameter("continueFlag")!=null){
			saveNcontinue =request.getParameter("continueFlag");
		}
		
		log.info("----saveNcontinue----"+saveNcontinue);
		
		if(!frmName.equals("")){
			String message = ptwAdvanceService.updateNoteSheet(advanceBean,
					frmName, frmFlag);
		if(saveNcontinue.equals("N")){
		 String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
		 }
		}
		ActionForward forward = mapping.findForward("success");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		params.put("method", "searchNoteSheet");
		// params.put("frm_name", "advances");
		// fwdParams.add(params);
		

		
		if((advanceBean.getSeperationreason().equals("Death"))&&(saveNcontinue.equals("Y"))){
			log.info("--in updateNoteShett-------");
			ArrayList nomineeList=null; 
			ArrayList nomineeSearchList = null;
			String nomineeName = "",nomineeSerialNo="",srcFrmName=""; 
			Map map = new LinkedHashMap();
			 
		if(frmName.equals("")){
			if (request.getParameter("srcFrmName") != null) {
				srcFrmName = request.getParameter("srcFrmName");
			}
		}else{
			srcFrmName=frmName;
		}
			
		log.info("----frmName--"+frmName+"--srcFrmName---"+srcFrmName);
			log.info("--in loadNominees:: pensionNo--"+advanceBean.getPensionNo()+"-nssanctionno--"+advanceBean.getNssanctionno());
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			 
			 
			for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				//log.info("- in updateNoteSheet()--nomineeSerialNo---"+nomineeSerialNo+"--nomineeName----"+nomineeName);
				map.put(nomineeSerialNo,nomineeName);
			}
			
			nomineeSearchList = ptwAdvanceService.nomineeBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				//log.info("----"+bankMasterBean.getNomineeSerialNo()+"--"+bankMasterBean.getBankempname()+"--"+bankMasterBean.getBanksavingaccno());
			}
			
			employeeInfo.setEmployeeName(advanceBean.getEmployeeName());
			employeeInfo.setSeperationreason(advanceBean.getSeperationreason());
			request.setAttribute("nomineeSearchList", nomineeSearchList);
			
			 dynaValide.set("bankempname","");
			 dynaValide.set("bankname","");
			 dynaValide.set("banksavingaccno","");
			 dynaValide.set("bankemprtgsneftcode","");
			 
			  request.setAttribute("nomineeHashmap", map);
			  request.setAttribute("srcFrmName",srcFrmName);
			  request.setAttribute("seperateScreen","N");
			  request.setAttribute("arrearType","NON-ARREAR");
			  request.setAttribute("employeeInfo", employeeInfo);
			this.getScreenTitle(request, response, "BankDet");
			saveToken(request);
			String path="addnomineebankdetails";
			log.info("==Path==="+path);
			return mapping.findForward(path);
		}else{ 
		this.getScreenTitle(request, response, "");
		if (frmName.equals("FSFormII"))
			fwdParams.add("frm_name", "FSFormII");
		else if (frmName.equals("FSFormIII"))
			fwdParams.add("frm_name", "FSFormIII");
		else if (frmName.equals("FSFormIV"))
			fwdParams.add("frm_name", "FSFormIV");
		
		return fwdParams.forward(forward);
		}
		 
	}

//New Method 
//Changes done by radhap on 15-Apr-2011
/*	public ActionForward DeleteNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpBankMaster bankMaster = new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String pensionNo="",nsSanctionNo="",nomineeSerialNo="", nomineeName = "",frmNewScreen="";
		int result=0;
		ArrayList nomineeSearchList=new ArrayList();
		ArrayList nomineeList=null; 		   
		Map nomineesMap = new LinkedHashMap();			 
		Map map = new LinkedHashMap();
		 
		log.info("--DeleteNomineeBankDetails--");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		if(request.getParameter("frmNewScreen")!=null){
			frmNewScreen=request.getParameter("frmNewScreen");
			}else{
				frmNewScreen="N";
			}
			 
		
		log.info("AdvancesAction::DeleteNomineeBankDetails::  frmNewScreen -------"+frmNewScreen);
		
		  
			if (request.getParameter("pfid") != null) {
				pensionNo=commonUtil.getSearchPFID(request.getParameter("pfid").toString().trim());
			}			 
			if(request.getParameter("nssanctionno")!=null){
			nsSanctionNo=request.getParameter("nssanctionno");
			}			  
			if(request.getParameter("nomineeserialno")!=null){
			nomineeSerialNo=request.getParameter("nomineeserialno");
			}
			result= ptwAdvanceService.deleteNomineeBankDetails(pensionNo,nsSanctionNo,nomineeSerialNo);
 	 
			nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,nsSanctionNo);
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				}			
			request.setAttribute("nomineeSearchList", nomineeSearchList);		  
			
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,nsSanctionNo);
			 
			 	for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				map.put(nomineeSerialNo,nomineeName);
			}
			 	request.setAttribute("seperateScreen",frmNewScreen);
			  request.setAttribute("nomineeHashmap", map);	
			
			this.getScreenTitle(request, response, "BankDet"); 
			return mapping.findForward("addnomineebankdetails");
		 }*/

	 //Changes done by radha p at 15-Apr-2011
	/*public ActionForward addNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpBankMaster bankMaster = new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		ArrayList nomineeSearchList=new ArrayList();
		ArrayList nomineeList=null; 		   
		Map nomineesMap = new LinkedHashMap();			 
		Map map = new LinkedHashMap();
		String pensionNo="",nsSanctionNo="",flag="N",frmName="",frmNewScreen="";
		String nomineeName = "",nomineeSerialNo="";	
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		if(request.getParameter("frmNewScreen")!=null){
			frmNewScreen=request.getParameter("frmNewScreen");
			}
		
		log.info("AdvancesAction::addNomineeBankDetails::  frmNewScreen -------"+frmNewScreen);
		 
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		}
		 
		log.info("AdvancesAction::addNomineeBankDetails::isTokenValid -------"+isTokenValid(request)+"frm_name"+request.getParameter("frm_name"));
		
		 
		log.info("--addNomineeBankDetails--");
			if (request.getParameter("pfid") != null) {
			pensionNo=commonUtil.getSearchPFID(request.getParameter("pfid").toString().trim());
			}		 
			if(request.getParameter("nssanctionno")!=null){
			nsSanctionNo=request.getParameter("nssanctionno");
			}		 
			if(request.getParameter("nomineeserialno")!=null){
			bankMaster.setNomineeSerialNo(request.getParameter("nomineeserialno"));
			}			
			if (dynaValide.getString("bankempname") != null) {
				bankMaster.setBankempname(dynaValide.getString("bankempname"));
			}			 
			if (dynaValide.getString("bankname") != null) {
				bankMaster.setBankname(dynaValide.getString("bankname"));
			}			 
			if (dynaValide.getString("banksavingaccno") != null) {
				bankMaster.setBanksavingaccno(dynaValide
						.getString("banksavingaccno"));
			}			 
			if (dynaValide.getString("bankemprtgsneftcode") != null) {
				bankMaster.setBankemprtgsneftcode(dynaValide
						.getString("bankemprtgsneftcode"));
			}
			
			if(!frmName.equals("")){
		 String bankInfo= ptwAdvanceService.addNomineeBankDet(pensionNo,nsSanctionNo,bankMaster);
			}
		 nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,nsSanctionNo);
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				}			
			request.setAttribute("nomineeSearchList", nomineeSearchList);		  
			
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,nsSanctionNo);
			 
			 	for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				map.put(nomineeSerialNo,nomineeName);
			}
			 
			  request.setAttribute("nomineeHashmap", map);	
			
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankname","");
		 dynaValide.set("banksavingaccno","");
		 dynaValide.set("bankemprtgsneftcode","");	 
			 
		 
		 ForwardParameters fwdParams = new ForwardParameters();
		 ActionForward forward=null;
		  if(frmNewScreen.equals("Y")){			 
			 forward=mapping.findForward("addnomineebankdetailsfrmnewscreen");
				Map params = new HashMap();		 		 
				params.put("method", "loadNomineeBankDetails");
				this.getScreenTitle(request, response, "");		
				fwdParams.add("pensionno", pensionNo);	
				fwdParams.add("nssanctionno", nsSanctionNo);	
					 
		  }else{			 
			  forward=mapping.findForward("nomineesucess");
			Map params = new HashMap();		 		 
			params.put("method", "updateNoteSheet");
			this.getScreenTitle(request, response, "");		
				fwdParams.add("continueFlag", "Y");			  	
			 
		  }
		  return fwdParams.forward(forward);
		 }

	
	*/
	public ActionForward DeleteNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpBankMaster bankMaster = new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		AdvanceSearchBean  employeeInfo = new AdvanceSearchBean();
		String pensionNo="",nsSanctionNo="",nomineeSerialNo="", nomineeName = "",frmNewScreen="",srcFrmName="",arrearType="",seperationreason="";
		int result=0;
		ArrayList nomineeSearchList=new ArrayList();
		ArrayList nomineeList=null; 		   
		Map nomineesMap = new LinkedHashMap();			 
		Map map = new LinkedHashMap();
		 
		log.info("--DeleteNomineeBankDetails--");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		if(request.getParameter("frm_ArrearType")!=null){
			arrearType=request.getParameter("frm_ArrearType");
			}			
		if(request.getParameter("srcFrmName")!=null){
			srcFrmName=request.getParameter("srcFrmName");
			}
		
		if(request.getParameter("frmNewScreen")!=null){
			frmNewScreen=request.getParameter("frmNewScreen");
			}else{
				frmNewScreen="N";
			}
					
		log.info("AdvancesAction::DeleteNomineeBankDetails::  frmNewScreen -------"+frmNewScreen);
		
		  
			if (request.getParameter("pfid") != null) {
				pensionNo=commonUtil.getSearchPFID(request.getParameter("pfid").toString().trim());
			}			 
			if(request.getParameter("nssanctionno")!=null){
			nsSanctionNo=request.getParameter("nssanctionno");
			}			  
			if(request.getParameter("seperationreason")!=null){
				seperationreason=request.getParameter("seperationreason");
				employeeInfo.setSeperationreason(seperationreason);
				}
			if(request.getParameter("nomineeserialno")!=null){
			nomineeSerialNo=request.getParameter("nomineeserialno");
			}
			result= ptwAdvanceService.deleteNomineeBankDetails(pensionNo,nsSanctionNo,nomineeSerialNo);
 	 
			nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,nsSanctionNo);
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				}			
			request.setAttribute("nomineeSearchList", nomineeSearchList);		  
			
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,nsSanctionNo);
			 
			 	for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				map.put(nomineeSerialNo,nomineeName);
			}
			 	request.setAttribute("srcFrmName",srcFrmName);
			 	request.setAttribute("arrearType",arrearType);
			 	request.setAttribute("seperateScreen",frmNewScreen);
			  request.setAttribute("nomineeHashmap", map);	
			  employeeBankBean = ptwAdvanceService.employeeBankInfo(pensionNo, nsSanctionNo);		 
				request.setAttribute("empBankInfo", employeeBankBean);
				request.setAttribute("employeeInfo", employeeInfo);
				log.info("---Name-----"+employeeInfo.getEmployeeName()+"---Reason---"+employeeInfo.getSeperationreason());
			this.getScreenTitle(request, response, "BankDet"); 
			return mapping.findForward("addnomineebankdetails");
		 }
	
//replace the method
// By Radha On 18-Apr-2011 for Bank Details for Arrears Case

//	 By Radha On 18-Apr-2011 for Bank Details for Arrears Case
	public ActionForward addNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpBankMaster bankMaster = new EmpBankMaster();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		EmpBankMaster employeeBankBean = new EmpBankMaster(); 
		AdvanceSearchBean  employeeInfo = new AdvanceSearchBean();
		ArrayList nomineeSearchList=new ArrayList();
		ArrayList nomineeList=null; 		   
		Map nomineesMap = new LinkedHashMap();			 
		Map map = new LinkedHashMap();
		String pensionNo="", transid="",flag="N",frmName="",frmNewScreen="",srcFrmName="",seperationreason="",bankFlag="Y", paymentFlag="Y",empName="",advanceType="";
		String nomineeName = "",nomineeSerialNo="",arrearType="";	
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		if(request.getParameter("frmNewScreen")!=null){
			frmNewScreen=request.getParameter("frmNewScreen");
			}
		if(request.getParameter("srcFrmName")!=null){
			srcFrmName=request.getParameter("srcFrmName");
			}
		
		log.info("AdvancesAction::addNomineeBankDetails::  srcFrmName -------"+srcFrmName); 
		log.info("AdvancesAction::addNomineeBankDetails::  frmNewScreen -------"+frmNewScreen);
		
	 	
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		}
		 
		log.info("AdvancesAction::addNomineeBankDetails::isTokenValid -------"+isTokenValid(request)+"frm_name"+request.getParameter("frm_name"));
		
		 
		log.info("--addNomineeBankDetails--");
			if (request.getParameter("pfid") != null) {
			pensionNo=commonUtil.getSearchPFID(request.getParameter("pfid").toString().trim());
			employeeInfo.setPensionNo(pensionNo);
			}		 
			 
			if(request.getParameter("transid")!=null){
				transid=request.getParameter("transid");
			}
			if(srcFrmName.equals("FSArrearRecommendation") || srcFrmName.equals("FSFormIII")){ 
				employeeInfo.setNssanctionno(transid);
				 
			if(request.getParameter("frm_ArrearType")!=null){
				arrearType=request.getParameter("frm_ArrearType"); 
				}
			}else{ 
					employeeInfo.setAdvanceTransID(transid);
				 
				if(request.getParameter("frm_AdvanceType")!=null){
					advanceType=request.getParameter("frm_AdvanceType");
				
					}
			}
			if(dynaValide.getString("employeeName")!=null){
				empName=dynaValide.getString("employeeName");
				employeeInfo.setEmployeeName(empName);
				}
			 
			if(dynaValide.getString("seperationreason")!=null){
				seperationreason=dynaValide.getString("seperationreason");
				employeeInfo.setSeperationreason(seperationreason);
			 }
			if(request.getParameter("paymentinfoflag")!=null){
				paymentFlag=request.getParameter("paymentinfoflag");
			 
			 } 
			if(request.getParameter("nomineeserialno")!=null){
			bankMaster.setNomineeSerialNo(request.getParameter("nomineeserialno"));
			}			
			if (dynaValide.getString("bankempname") != null) {
				bankMaster.setBankempname(dynaValide.getString("bankempname"));
			}			 
			if (dynaValide.getString("bankname") != null) {
				bankMaster.setBankname(dynaValide.getString("bankname"));
			}			 
			if (dynaValide.getString("banksavingaccno") != null) {
				bankMaster.setBanksavingaccno(dynaValide
						.getString("banksavingaccno"));
			}			 
			if (dynaValide.getString("bankemprtgsneftcode") != null) {
				bankMaster.setBankemprtgsneftcode(dynaValide
						.getString("bankemprtgsneftcode"));
			}
			
			if(!frmName.equals("")){
				 if(seperationreason.equals("Death")){
		 String nomineeBankInfo= ptwAdvanceService.addNomineeBankDet(pensionNo,transid,bankMaster);
				 }else{
			if(!bankMaster.getBankempname().equals("")){
				 String empBankInfo= ptwAdvanceService.updateBankInfo(pensionNo,transid,bankFlag, paymentFlag,bankMaster);
			}
				 }
			}
			 if(seperationreason.equals("Death")){
		 nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,transid);
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				}			
			request.setAttribute("nomineeSearchList", nomineeSearchList);			
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,transid);
			 
			 	for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				map.put(nomineeSerialNo,nomineeName);
			}
			 
			  request.setAttribute("nomineeHashmap", map);	
			  
	        }
			  employeeBankBean = ptwAdvanceService.employeeBankInfo(pensionNo, transid);		 
				request.setAttribute("empBankInfo", employeeBankBean);
				request.setAttribute("employeeInfo", employeeInfo);
			  
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankname","");
		 dynaValide.set("banksavingaccno","");
		 dynaValide.set("bankemprtgsneftcode","");	
		 
		 if(request.getParameter("frm_ArrearType")!=null){
			 arrearType=request.getParameter("frm_ArrearType");
				} 
		 
		 log.info("---arrearType- ---"+arrearType+"==srcFrmName==="+srcFrmName+"=====empName=="+empName);
		 
		 ForwardParameters fwdParams = new ForwardParameters();
		 ActionForward forward=null;
		 
		 if(srcFrmName.equals("FSArrearProcess") || srcFrmName.equals("FSArrearRecommendation") || srcFrmName.equals("FSFormII")  || srcFrmName.equals("FSFormIII")){
		 
		 if(arrearType.equals("ARREAR")){
			 
			 if(frmNewScreen.equals("Y")){			 
				 forward=mapping.findForward("addnomineebankdetailsfrmnewscreen");
					Map params = new HashMap();		 		 
					params.put("method", "loadNomineeBankDetails");
					this.getScreenTitle(request, response, "");		
					fwdParams.add("pensionno", pensionNo);	
					fwdParams.add("nssanctionno", transid);	
					fwdParams.add("srcFrmName", srcFrmName);
					fwdParams.add("empName", empName);
					fwdParams.add("seperationreason", seperationreason);
					
						 
			  }else{			 
				  forward=mapping.findForward("arrearnomineesucess");
				Map params = new HashMap();		 		 
				params.put("method", "updateNoteSheetArrear");
				this.getScreenTitle(request, response, "");		
					fwdParams.add("continueFlag", "Y");		
					fwdParams.add("frm_name", srcFrmName);
					 
			  }
				 
			 
		 }else{		 
		  if(frmNewScreen.equals("Y")){			 
			 forward=mapping.findForward("addnomineebankdetailsfrmnewscreen");
				Map params = new HashMap();		 		 
				params.put("method", "loadNomineeBankDetails");
				this.getScreenTitle(request, response, "");		
				fwdParams.add("pensionno", pensionNo);	
				fwdParams.add("nssanctionno", transid);	
				fwdParams.add("srcFrmName", srcFrmName);	
				fwdParams.add("empName", empName);
				fwdParams.add("seperationreason", seperationreason);
				
					 
		  }else{			 
			  forward=mapping.findForward("nomineesucess");
			Map params = new HashMap();		 		 
			params.put("method", "updateNoteSheet");
			this.getScreenTitle(request, response, "");		
				fwdParams.add("continueFlag", "Y");		
				fwdParams.add("frm_name", srcFrmName);
				 
		  }
		 }
		 
	}else{ 
		
		if(frmNewScreen.equals("Y")){			 
			 forward=mapping.findForward("addnomineebankdetailsfrmnewscreen");
				Map params = new HashMap();		 		 
				params.put("method", "loadNomineeBankDetails");
				this.getScreenTitle(request, response, "");		
				fwdParams.add("pensionno", pensionNo);	
				fwdParams.add("advanceTransID", transid);	
				fwdParams.add("srcFrmName", srcFrmName);
				fwdParams.add("empName", empName);
				fwdParams.add("seperationreason", seperationreason);
				fwdParams.add("advanceType", advanceType);
				
		
		
	}
	}
		  return fwdParams.forward(forward);
		 }


	
	public ActionForward loadForm4Approval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadForm4Approval");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("purposeType", "");
		
		dynaValide.set("advanceTransyear", "");
		this.getScreenTitle(request, response, "");
		return mapping.findForward("loadform4app");
	}
	
	public ActionForward loadCPFFormApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadCPFFormApproval");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("purposeType", "");
		dynaValide.set("advanceTransyear", "");
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("loadcpfsearch");
	}
	//	On 24-Jan-2012 for loading date according to user		
	public ActionForward searchCPFApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		this.getLoginDetails(request);
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
			
		}
		if (request.getParameter("frm_name") != null) {
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		if (dynaValide.getString("advanceType") != null) {
			searchBean.setAdvanceType("CPF");
		}
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		if (dynaValide.getString("advanceTransStatus") != null) {
			searchBean.setAdvanceTrnStatus(dynaValide
					.getString("advanceTransStatus"));
		}
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		searchBean.setUserId(this.loginUserId);
		 
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchAdvance(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("loadcpfsearch");
	}
	//	On 30-Jan-2012 for loading date according to user  
	public ActionForward searchForm4Approval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		this.getLoginDetails(request);
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
			
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		
		if (dynaValide.getString("advanceType") != null) {
			searchBean.setAdvanceType("PFW");
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		/*
		 * if(dynaValide.getString("advanceTransStatus")!=null){
		 * searchBean.setAdvanceTrnStatus("A"); }
		 */
		
		if (request.getParameter("frm_name") != null) {
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		// searchBean.setVerifiedBy("FINANCE");
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchAdvance(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("loadform4app");
	}
	
	public ActionForward advanceForm4Report(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String prpsOptionsCPF = "", frmType = "", path = "",frmName="";
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", transactionNo = "", rtrnPage = "";
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
					
				}
				
			}
			
		}
		if (request.getParameter("frm_type") != null) {
			frmType = request.getParameter("frm_type");
		}
		
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		
		try {
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		 
			log.info("advanceForm4Report::pensionNo" + pensionNo
					+ "transactionNo" + transactionNo);
			dynaValide.set("authorizedrmrks", "");
			list = ptwAdvanceService.advanceForm3Report(pensionNo,
					transactionNo, frmType);
			log.info("advanceForm4Report::::Size" + list.size());
			
			advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
		 
			if (advanceReportBean.getAdvanceType().equals("PFW")
					&& (advanceReportBean.getPurposeType().equals("HBA") ||advanceReportBean.getPurposeType().equals("PANDEMIC"))) {
				dynaValide.set("contributionAmt", advanceReportBean
						.getContributionAmt());
				dynaValide.set("contributionAmtCurr", advanceReportBean
						.getContributionAmtCurr());
			} else {
				dynaValide.set("contributionAmt", "0");
				dynaValide.set("contributionAmtCurr", "0.0");
				
			}
			request.setAttribute("Form3ReportBean", advanceReportBean);
			this.fillForm4BeanToDyna(advanceReportBean, form);
			dynaValide.set("sanctiondate", commonUtil
					.getCurrentDate("dd-MMM-yyyy"));
			dynaValide.set("amntrcdedrs", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("emolumentsLabel", advanceReportBean
					.getEmolumentsLabel());
			dynaValide.set("advanceTransIDDec", advanceReportBean
					.getAdvanceTransIDDec());
			dynaValide.set("paymentinfo", advanceReportBean.getPaymentinfo());
			log.info("Form-4 Transaction Emoluments"
					+ advanceReportBean.getEmoluments());
			 
			
			if (frmType.equals("Y")) {
				
				path = "viewForm4";
			} else {
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
						transactionNo,
						Constants.APPLICATION_PROCESSING_PFW_APPROVAL_ED,
						imgagePath);
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
					
				} 
				path = "viewForm4Report";
				
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			this.getScreenTitle(request, response, "");
			return mapping.findForward("viewForm4Report");
		}
	
		this.getScreenTitle(request, response, frmName);
		
		
		return mapping.findForward(path);
	}
	
	public ActionForward savePFWForm4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvancePFWFormBean advneFrm4Bean = new AdvancePFWFormBean(request);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advneFrm4Bean = this.fillFrmForm4Bean(form, request);
		int totalRecUpdated = 0;
		String advanceType = "",frmName="";
		if (dynaValide.getString("mnthsemoluments") != null) {
			advneFrm4Bean.setMnthsemoluments(dynaValide
					.getString("mnthsemoluments"));
		}
		if (dynaValide.getString("advanceType") != null) {
			advanceType = dynaValide.getString("advanceType");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		 
		log.info("---frmName---"+frmName);
		totalRecUpdated = ptwAdvanceService
		.updateCPFAdvanceForm4(advneFrm4Bean);
		this.getScreenTitle(request, response, frmName);
		return mapping.findForward("success");
	}
	public ActionForward savePFWForm3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvancePFWFormBean advneFrm4Bean = new AdvancePFWFormBean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String emoluments = "", subscriptionAmt = "", contributionAmt = "", cpfFund = "", narration = "", amountRecmded = "", mnthsEmoluments = "", flag = "";
		String firstInsSubAmnt="",firstInsConrtiAmnt="";
		String paymentInfoFlag="N",updateBankFlag="N";
		advneFrm4Bean = this.fillFrmForm4Bean(form, request);
		int totalRecUpdated = 0;
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (dynaValide.getString("prpsecvrdclse") != null) {
			advneFrm4Bean.setPrpsecvrdclse(dynaValide
					.getString("prpsecvrdclse"));
		}
		if (dynaValide.getString("emoluments") != null) {
			emoluments = dynaValide.getString("emoluments");
		}
		if (dynaValide.getString("subscriptionAmt") != null) {
			subscriptionAmt = dynaValide.getString("subscriptionAmt");
		}
		if (dynaValide.getString("contributionAmt") != null) {
			contributionAmt = dynaValide.getString("contributionAmt");
		}
		if (dynaValide.getString("CPFFund") != null) {
			cpfFund = dynaValide.getString("CPFFund");
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			amountRecmded = dynaValide.getString("amntrcdedrs");
		}
		if (dynaValide.getString("mnthsemoluments") != null) {
			mnthsEmoluments = dynaValide.getString("mnthsemoluments");
		}
		if (dynaValide.getString("narration") != null) {
			narration = dynaValide.getString("narration");
		}
		if (dynaValide.getString("firstInsSubAmnt") != null) {
			firstInsSubAmnt = dynaValide.getString("firstInsSubAmnt");
		}
		if (dynaValide.getString("firstInsConrtiAmnt") != null) {
			firstInsConrtiAmnt = dynaValide.getString("firstInsConrtiAmnt");
		}
		
		/*if (dynaValide.getString("subscriptionAmt50percent") != null) {
		 if (dynaValide.getString("purposeType").equals("MARRIAGE")) {
		 subscriptionAmt = dynaValide
		 .getString("subscriptionAmt50percent");
		 }
		 }*/
		  
		if (dynaValide.getString("paymentinfo")!=null){		 
			paymentInfoFlag=dynaValide.getString("paymentinfo");
		} 		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 		 
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("partyName") != null) {
			bankMaster.setPartyName(dynaValide.getString("partyName"));
		}
		if (dynaValide.getString("partyAddress") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("partyAddress"));
		}
		
		 
		
		boolean tokenCheck;
		
		if ( isTokenValid(request) ) {
			tokenCheck=true;
			resetToken(request);
		}else{
			tokenCheck=false;
		}
		log.info("=======****tokenCheck****======="+tokenCheck);
		
		if(tokenCheck==true){
			totalRecUpdated = ptwAdvanceService.updateCPFAdvanceForm3(		 
					advneFrm4Bean, emoluments, subscriptionAmt, contributionAmt,
					cpfFund, amountRecmded, mnthsEmoluments, flag, narration,firstInsSubAmnt,firstInsConrtiAmnt);
			String bankInfo= ptwAdvanceService.updateBankInfo(advneFrm4Bean.getPensionNo(),advneFrm4Bean.getAdvanceTransID(),updateBankFlag,paymentInfoFlag,bankMaster);
			  
		}
		return mapping.findForward("success");
	}
	
//	04-Oct-2010 Radha  For adding saveToken
	public ActionForward savePFWForm4Verification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvancePFWFormBean advneFrm4Bean = new AdvancePFWFormBean(request);
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String emoluments = "", subscriptionAmt = "", contributionAmt = "", cpfFund = "", amountRecmded = "", mnthsEmoluments = "", flag = "", approvedSubAmt = "", empshare = "", advnceapplid = "";
		String paymentInfoFlag="N",updateBankFlag="N",frm_name="";
		String firstInsSubAmnt="",firstInsConrtiAmnt="";
		int totalRecUpdated = 0;
		
		advneFrm4Bean = this.fillFrmForm4Bean(form, request);
		
		if (dynaValide.getString("purposeType") != null) {
			advneFrm4Bean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (dynaValide.getString("firstInsSubAmnt") != null) {
			firstInsSubAmnt = dynaValide.getString("firstInsSubAmnt");
		}
		if (dynaValide.getString("firstInsConrtiAmnt") != null) {
			firstInsConrtiAmnt = dynaValide.getString("firstInsConrtiAmnt");
		}
		
		if (request.getParameter("frm_name") != null) {
			frm_name = request.getParameter("frm_name");
		}
		if (dynaValide.getString("emoluments") != null) {
			emoluments = dynaValide.getString("emoluments");
		}
		if (dynaValide.getString("subscriptionAmt") != null) {
			subscriptionAmt = dynaValide.getString("subscriptionAmt");
		}
		if (dynaValide.getString("contributionAmt") != null) {
			contributionAmt = dynaValide.getString("contributionAmt");
		}
		if (dynaValide.getString("CPFFund") != null) {
			cpfFund = dynaValide.getString("CPFFund");
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			amountRecmded = dynaValide.getString("amntrcdedrs");
		}
		if (dynaValide.getString("approvedsubamt") != null) {
			approvedSubAmt = dynaValide.getString("approvedsubamt");
		}
		
		if (dynaValide.getString("empshare") != null) {
			empshare = dynaValide.getString("empshare");
		}
		
		if (dynaValide.getString("advnceapplid") != null) {
			advnceapplid = dynaValide.getString("advnceapplid");
		}
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			paymentInfoFlag=dynaValide.getString("paymentinfo");
		} 		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 		 
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("partyName") != null) {
			bankMaster.setPartyName(dynaValide.getString("partyName"));
		}
		if (dynaValide.getString("partyAddress") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("partyAddress"));
		}
		
		
		boolean tokenCheck;
		
		if ( isTokenValid(request) ) {
			tokenCheck=true;
			resetToken(request);
		}else{
			tokenCheck=false;
		}
		log.info("=======****tokenCheck****======="+tokenCheck);
		
		if(tokenCheck==true){
			totalRecUpdated = ptwAdvanceService.updatePFWForm4Verification(
					advneFrm4Bean, emoluments, subscriptionAmt, contributionAmt,
					cpfFund, amountRecmded, mnthsEmoluments, flag, approvedSubAmt,
					empshare, advnceapplid,firstInsSubAmnt,firstInsConrtiAmnt);
			 String bankInfo= ptwAdvanceService.updateBankInfo(advneFrm4Bean.getPensionNo(),advneFrm4Bean.getAdvanceTransID(),updateBankFlag,paymentInfoFlag,bankMaster);
			  	
		}
		this.getScreenTitle(request,response,"");
		return mapping.findForward("success");
	}
	// By Radha Modified on 09-Aug-2011 to  set    approvedSub,aprovedContr values to zero if data not provide
	private AdvancePFWFormBean fillFrmForm4Bean(ActionForm form,
			HttpServletRequest request) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvancePFWFormBean pfwBean = new AdvancePFWFormBean(request);
		
		if (dynaValide.getString("pensionNo") != null) {
			pfwBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		if (dynaValide.getString("advntrnsid") != null) {
			pfwBean.setAdvanceTransID(dynaValide.getString("advntrnsid"));
			if (!pfwBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = pfwBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					pfwBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		if ((dynaValide.getString("approvedsubamt") != null)&&(!dynaValide.getString("approvedsubamt").equals(""))) {
			pfwBean.setApprovedsubamt(dynaValide.getString("approvedsubamt"));
		}else{
			pfwBean.setApprovedsubamt("0.0");
		}
		if ((dynaValide.getString("approvedconamt") != null) && (!dynaValide.getString("approvedconamt").equals(""))) {
			pfwBean.setApprovedconamt(dynaValide.getString("approvedconamt"));
		}else{
			pfwBean.setApprovedconamt("0.0");
		}		 
		if (dynaValide.getString("sanctiondate") != null) {
			pfwBean.setSanctiondate(dynaValide.getString("sanctiondate"));
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			pfwBean.setAmntRecommended(dynaValide.getString("amntrcdedrs"));
		}
		if (dynaValide.getString("amntrcdedrs") != null) {
			pfwBean.setAmntAproved(dynaValide.getString("amntrcdedrs"));
		}
		
		if (dynaValide.getString("authorizedrmrks") != null) {
			pfwBean
			.setAuthrizedRemarks(dynaValide
					.getString("authorizedrmrks"));
		}
		if (dynaValide.getString("authorizedsts") != null) {
			pfwBean.setAuthrizedStatus(dynaValide.getString("authorizedsts"));
		}
		
		return pfwBean;
		
	}
	
	private void fillForm4BeanToDyna(AdvanceCPFForm2Bean form2Bean,
			ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		dynaValide.set("employeeName", form2Bean.getEmployeeName());
		dynaValide.set("pensionNo", form2Bean.getPensionNo());
		dynaValide.set("pfid", form2Bean.getPfid());
		dynaValide.set("designation", form2Bean.getDesignation());
		dynaValide.set("department", form2Bean.getDepartment());
		dynaValide.set("dateOfMembership", form2Bean.getDateOfMembership());
		dynaValide.set("dateOfJoining", form2Bean.getDateOfJoining());
		dynaValide.set("dateOfBirth", form2Bean.getDateOfBirth());
		dynaValide.set("employeeNo", form2Bean.getEmployeeNo());
		dynaValide.set("emoluments", form2Bean.getEmoluments());
		dynaValide.set("advntrnsid", form2Bean.getAdvanceTransID());
		dynaValide.set("totalInst", form2Bean.getTotalInst());
		dynaValide.set("fhName", form2Bean.getFhName());
		dynaValide.set("amntrcdedrs", form2Bean.getAmntRecommended());
		dynaValide.set("amntapprvdrs", form2Bean.getAmntRecommended());
		dynaValide.set("advnceapplid", form2Bean.getAdvnceRequest());
		dynaValide.set("empshare", form2Bean.getEmpshare());
		dynaValide.set("mnthsemoluments", form2Bean.getMnthsemoluments());
		dynaValide.set("outstndamount", form2Bean.getOutstndamount());
		
		dynaValide.set("CPFFund", form2Bean.getCPFFund());
		dynaValide.set("purposeType", form2Bean.getPurposeType());
		dynaValide.set("advanceType", form2Bean.getAdvanceType());
		dynaValide.set("advntrnsdt", form2Bean.getAdvntrnsdt());
		dynaValide.set("placeofposting", form2Bean.getPlaceofposting());
		dynaValide.set("purposeOptionType", form2Bean.getPurposeOptionType());
		dynaValide.set("purposeOptionTypeDesr", form2Bean
				.getPurposeOptionTypeDesr());
		
	}
	
	public ActionForward loadPFWRevisedDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList list = new ArrayList();
		AdvanceSearchBean advanceSearchBean = new AdvanceSearchBean(request);
		String  path = "", flag = "",purposeType="", frm_name="";
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", transactionNo = "", rtrnPage = "", AdvStatus = "",frmFlag="";
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		if (request.getParameter("frmPurposeType") != null) {
			purposeType = request.getParameter("frmPurposeType");
		}  
		if (request.getParameter("frmName") != null) {
			frm_name = request.getParameter("frmName");
		} 
		
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		} 
		
		advanceSearchBean.setPensionNo(pensionNo);
		advanceSearchBean.setAdvanceTransID(transactionNo);
		advanceSearchBean.setPurposeType(purposeType);
		advanceSearchBean.setFormName(frm_name);
		try {
			AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		 
			log.info("advanceReport::pensionNo" + pensionNo + "transactionNo"
					+ transactionNo + "AdvStatus" + AdvStatus+"frmFlag"+frmFlag);
			if(frmFlag.equals("New")){
			list = ptwAdvanceService.loadPFWRevisedDetails(pensionNo,
					transactionNo,purposeType);
			}else{
			list = ptwAdvanceService.editAdvancesRevised(pensionNo,
					transactionNo,purposeType,frm_name);
			}
			
			
			log.info("Size" + list.size());
			if (list.size() != 0) {
				advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
				basicReportBean = (AdvanceBasicReportBean) list.get(1);				 
				dynaValide.set("takenloan", advanceReportBean.getTakenloan());
				request.setAttribute("Form2ReportBean", advanceReportBean);
				request.setAttribute("basicReportBean", basicReportBean);
			}
			if(frm_name.equals("PFWRevisedVerification")){
				path = "loadPFWRevisedVerificationForm";
			}else if (frm_name.equals("PFWRevisedRecommendation")){
				path = "loadPFWRevisedRecommendationForm";
			}else if (frm_name.equals("PFWRevisedApproval")){
				path = "loadPFWRevisedApprovalForm";
			}
		saveToken(request);			
		 this.getScreenTitle(request,response,frm_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("loadPFWRevisedForm");
		}
		log.info("=======Path=======" + path);
		return mapping.findForward(path);
	}
	public ActionForward updatePFWRevisedDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceCPFForm2Bean advneBean = new AdvanceCPFForm2Bean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String emoluments = "", subscriptionAmt = "", contributionAmt = "", cpfFund = "", narration = "", amountRecmded = "", mnthsEmoluments = "", flag = "";
		String paymentInfoFlag="N",updateBankFlag="N",frm_name="",message="";
		int totalRecUpdated =0;
		if (dynaValide.getString("pensionNo") != null) {
			advneBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		log.info("=========="+dynaValide.getString("pensionNo"));
		if (dynaValide.getString("advanceTransID") != null) {
			advneBean.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!advneBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = advneBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					advneBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		if (dynaValide.getString("purposeType") != null) {
			advneBean.setPurposeType(dynaValide.getString("purposeType"));
		} 
		if (dynaValide.getString("propertyaddress") != null) {
			advneBean.setPropertyAddress(dynaValide.getString("propertyaddress"));
		}
		if (dynaValide.getString("narration") != null) {
			advneBean.setNarration(dynaValide.getString("narration"));
		}
		 
		if (dynaValide.getString("paymentinfo")!=null){		 
			paymentInfoFlag=dynaValide.getString("paymentinfo");
		} 		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 	
		advneBean.setPaymentinfo(paymentInfoFlag);
		advneBean.setUpdateBankFlag(updateBankFlag);
		
		if (request.getParameter("frm_name") != null) {
			advneBean.setFormName(request.getParameter("frm_name"));
		}
		if (request.getParameter("frmFlag") != null) {
			advneBean.setFrmFlag(request.getParameter("frmFlag"));
		}
		if (request.getParameter("revAdvanceTransID") != null) {
			advneBean.setRevAdvanceTransID(request.getParameter("revAdvanceTransID"));
		}
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		 
		 this.getLoginDetails(request);
		 advneBean.setLoginUserId(this.loginUserId);
		 advneBean.setLoginUserName(this.loginUserName);
		 advneBean.setLoginUnitCode(this.loginUnitCode);
		advneBean.setLoginRegion(this.loginRegion);
		advneBean.setLoginUserDispName(this.loginSignName) ; 
		advneBean.setLoginUserDesignation(this.loginUserDesignation) ; 
		boolean tokenCheck;
		
		if ( isTokenValid(request) ) {
			tokenCheck=true;
			resetToken(request);
		}else{
			tokenCheck=false;
		}
		log.info("=======****tokenCheck****======="+tokenCheck);
		
		if(tokenCheck==true){
			message = ptwAdvanceService.updatePFWRevisedDetails(		 
					advneBean,bankMaster );
			}
		if(frm_name.equals("PFWRevisedVerification")){ 
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", message));
		saveMessages(request, actionMessage);
		}
		this.getScreenTitle(request,response,frm_name);
		return mapping.findForward("loadPFWRevisedSearch");
	}
	public ActionForward loadPFWApprovalSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadPFWApprovalSearch");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String path = "",frm_name="";
		log.info("=======FLAG========" + request.getParameter("flag"));
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("advanceTransyear", "");
		
		if (request.getParameter("flag").equals("form2")) {
			path = "loadPFWForm2Approvals";
		} else if (request.getParameter("flag").equals("form3")) {
			path = "loadPFWForm3Approvals";
		} else if (request.getParameter("flag").equals("form4")) {
			path = "loadPFWForm4Approvals";
			frm_name="PFWForm4";
		}else if (request.getParameter("flag").equals("PFWRevisedVerification")||
				request.getParameter("flag").equals("PFWRevisedRecommendation") ||
				request.getParameter("flag").equals("PFWRevisedApproval")||
				request.getParameter("flag").equals("PFWRevisedApproved")) {
			path = "loadPFWRevisedSearch";
			frm_name= request.getParameter("flag");
		}  else {
			path = "loadCPFVerification";
		}
		log.info("========path========"+path+"==frm_name==="+frm_name);
		this.getScreenTitle(request,response,frm_name);
		return mapping.findForward(path);
	}
	
	public ActionForward savePFWForm2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
		EmpBankMaster bankMaster = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advneFrm2Bean = this.fillFrmForm2Bean(form, request);
		int totalRecUpdated = 0;
		String paymentInfoFlag="N",updateBankFlag="N";
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			paymentInfoFlag=dynaValide.getString("paymentinfo");
		} 		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 		 
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("partyName") != null) {
			bankMaster.setPartyName(dynaValide.getString("partyName"));
		}
		if (dynaValide.getString("partyAddress") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("partyAddress"));
		}
		
		 String bankInfo= ptwAdvanceService.updateBankInfo(advneFrm2Bean.getPensionNo(),advneFrm2Bean.getAdvanceTransID(),updateBankFlag,paymentInfoFlag,bankMaster);
		 
		totalRecUpdated = ptwAdvanceService
		.updatePFWAdvanceForm2(advneFrm2Bean);
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("advanceTransyear", "");
		return mapping.findForward("saveForm2");
	}
	
	public ActionForward advanceSanctionOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String frmType = "", path = "", pensionNo = "", transactionNo = "", currentYear = "";
		int nextYear = 0;
		int previousYear = 0, currentMonth = 0;
		String previousYear1 = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
				}
			}
		}
		if (request.getParameter("frm_type") != null) {
			frmType = request.getParameter("frm_type");
		}
		
		try { 
			log.info("advanceSanctionOrder::pensionNo" + pensionNo
					+ "transactionNo" + transactionNo);
			dynaValide.set("authorizedrmrks", "");
			list = ptwAdvanceService.advanceSanctionOrder(pensionNo,
					transactionNo, frmType);
			log.info("advanceSanctionOrder::::Size" + list.size());
			
			advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
			basicReportBean = (AdvanceBasicReportBean) list.get(1);
			
			if (advanceReportBean.getAdvanceType().equals("PFW")
					&& advanceReportBean.getPurposeType().equals("HBA") ||
					advanceReportBean.getAdvanceType().equals("PFW")
					&& advanceReportBean.getPurposeType().equals("SUPERANNAUTION") ) {
				dynaValide.set("contributionAmt", advanceReportBean
						.getContributionAmt());
			} else {
				dynaValide.set("contributionAmt", "0");
			}
			//added new fields by prasad on 03-Jun-2013
			dynaValide.set("firstInsSubAmnt",advanceReportBean.getFirstInsSubAmnt());
			dynaValide.set("firstInsConrtiAmnt",advanceReportBean.getFirstInsConrtiAmnt());
			dynaValide.set("firstInsTotAmnt",advanceReportBean.getFirstInsTotAmnt());
			
			log.info("advanceReportBean.getFirstInsTotAmnt()"+advanceReportBean.getFirstInsTotAmnt());
			/*
			 * currentYear=commonUtil.getCurrentDate("yyyy").substring(2,4);
			 * nextYear=Integer.parseInt(currentYear)+1;
			 * advanceReportBean.setFinyear(currentYear+"-"+nextYear);
			 */
			currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
			currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
			nextYear = Integer.parseInt(currentYear) + 1;
			previousYear = Integer.parseInt(currentYear) - 1;
			
			if (previousYear <= 9)
				previousYear1 = "0" + previousYear;
			
			if (currentMonth >= 04)
				advanceReportBean.setFinyear(currentYear + "-" + nextYear);
			else if (previousYear <= 9)
				advanceReportBean.setFinyear(previousYear1 + "-" + currentYear);
			else
				advanceReportBean.setFinyear(previousYear + "-" + currentYear);
			/*log.info("finyear:"+advanceReportBean.getFinyear());
			if(advanceReportBean.getFinyear().equals("13-14")){
				dynaValide.set("transMnthYear","2013-14");
				dynaValide.set("rateOfInterest","8.25");
				log.info("finyearin:"+advanceReportBean.getFinyear());
			}*/
			
			request.setAttribute("PFWSanctionOrderBean", advanceReportBean);
			this.fillForm4BeanToDyna(advanceReportBean, form);
			dynaValide.set("amntrcdedrs", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("partyname", advanceReportBean.getPartyname());
			request.setAttribute("BasicReportBean", basicReportBean);
			 
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		 
			if (frmType.equals("Y")) {
				path = "viewForm4";
			} else if (frmType.equals("Form-4Report")) {
				path = "viewForm4Report";
				
				
				
			} else {
				
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
						transactionNo,
						Constants.APPLICATION_PROCESSING_PFW_APPROVAL_ED,
						imgagePath);
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
					
				}
				 
				path = "viewPFWSanctionOrderReport";
				 
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("viewPFWSanctionOrderReport");
		}
		return mapping.findForward(path);
	}
	public ActionForward previousWithDrawalDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::previousWithDrawalDetails");
		String wthdrwlPurpose = "", wthdrwlAmount = "", wthdrwlDate = "",wthdrwStr="",path="";
		
		ArrayList wthDrwlList = new ArrayList();
		AdvanceBasicBean basicBean = new AdvanceBasicBean();
		
		String formName="";
		
		if (request.getParameter("frm_wthdrwlPurpose") != null) {
			wthdrwlPurpose = request.getParameter("frm_wthdrwlPurpose");
			basicBean.setWthdrwlpurpose(wthdrwlPurpose);
		}
		if (request.getParameter("frm_wthdrwlAmount") != null) {
			wthdrwlAmount = request.getParameter("frm_wthdrwlAmount");
			basicBean.setWthdrwlAmount(wthdrwlAmount);
		}
		if (request.getParameter("frm_wthdrwlDate") != null) {
			wthdrwlDate = request.getParameter("frm_wthdrwlDate");
			basicBean.setWthDrwlTrnsdt(wthdrwlDate);
		}
		
		if (request.getParameter("frm_withDrawStr") != null) {
			wthdrwStr = request.getParameter("frm_withDrawStr");			
		}
		
		
		request.setAttribute("wthdrwlBean", basicBean);
		wthDrwlList.add(basicBean);
		request.setAttribute("wthDrwlList", wthDrwlList);
		
		log.info("wthdrwStr in Action is----------"+wthdrwStr);
		
		if(wthdrwStr.length()>0){
			log.info("wthdrwStr length in Action is----------"+wthdrwStr.length());		
			
			request.setAttribute("wthdrwStr", wthdrwStr.substring(0,wthdrwStr.length()-1));
		}
		
		this.getScreenTitle(request,response,"");
		
		basicBean=(AdvanceBasicBean)request.getAttribute("advanceBean");
		
		
		
		if(basicBean.getFrmName().equals("pfwnew")){
			formName="new";
		}else{
			formName="edit";
		}
		
		request.setAttribute("formName",formName); 
		
		if(!wthdrwStr.equals("")){
			path="loadWthdrwlDetails";						
		}else{
			path="loadWthdrwlForm";			
			
		}
		
		return mapping.findForward(path);
	}
	//	On 23-Jan-2012 for loading date according to user 
	public ActionForward searchCPFVerificationAdvances(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String path = "";
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		this.getLoginDetails(request);
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
			
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		if (dynaValide.getString("advanceTransStatus") != null) {
			searchBean.setAdvanceTrnStatus(dynaValide
					.getString("advanceTransStatus"));
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		log.info("-------Frm Name--------" + request.getParameter("frmName")
				+ "Pension NO" + searchBean.getPensionNo());
		if (request.getParameter("frmName") != null) {
			searchBean.setFormName(request.getParameter("frmName"));
			
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		searchBean.setAdvanceType("CPF");
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		ArrayList searchlist = ptwAdvanceService.searchAdvance(searchBean);
		request.setAttribute("searchlist", searchlist);
		
		path = "CPFForm2Success";
		return mapping.findForward(path);
	}
	
	
	
	public ActionForward loadCheckListSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String path = "";
		
		dynaValide.set("advanceType", "");
		dynaValide.set("advanceTransID", "");
		dynaValide.set("advanceTransStatus", "");
		dynaValide.set("pfid", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("advanceTransyear", "");
		
		if (request.getParameter("flag").equals("PFWCheckList")) {
			path = "loadPFWCheckListSearch";
		} else if (request.getParameter("flag").equals("CPFCheckList")) {
			path = "loadCPFCheckListSearch";
		}
		return mapping.findForward(path);
	}
	
	// New Method------
	
	public ActionForward checkListApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean(request);
		AdvanceCPFForm2Bean LODBean = new AdvanceCPFForm2Bean();
		ArrayList list = new ArrayList();
		ArrayList LODList = new ArrayList();
		ArrayList transList = new ArrayList();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		String prpsOptionsCPF = "", frmName = "", path = "", flag = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String pensionNo = "", transactionNo = "", rtrnPage = "";
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
				}
			}
		}
		
		list = ptwAdvanceService.checkListApprove(pensionNo, transactionNo,
				frmName);
		
		advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
		bankMasterBean = (EmpBankMaster) list.get(1);
		basicReportBean = (AdvanceBasicReportBean) list.get(2);
		this.fillForm4BeanToDyna(advanceReportBean, form);
		dynaValide.set("subscriptionAmt", advanceReportBean
				.getSubscriptionAmt());
		dynaValide.set("contributionAmt", advanceReportBean
				.getContributionAmt());
		dynaValide.set("prpsecvrdclse", advanceReportBean.getPrpsecvrdclse());
		dynaValide.set("purposeType", advanceReportBean.getPurposeType());
		dynaValide.set("amntrcdedrs", advanceReportBean.getAmntRecommended());
		dynaValide.set("paymentinfo", advanceReportBean.getPaymentinfo());
		dynaValide.set("emolumentsLabel", advanceReportBean
				.getEmolumentsLabel());
		dynaValide.set("approvedsubamt", advanceReportBean.getApprovedsubamt());
		dynaValide.set("approvedconamt", advanceReportBean.getApprovedconamt());
		dynaValide.set("verifiedby", advanceReportBean.getVerifiedby());
		dynaValide.set("authorizedrmrks", advanceReportBean
				.getAuthrizedRemarks());
		dynaValide.set("advanceTransIDDec", advanceReportBean
				.getAdvanceTransIDDec());
		dynaValide.set("chkwthdrwlinfo", advanceReportBean.getChkwthdrwlinfo());
		
		if (!advanceReportBean.getLodInfo().trim().equals("")) {
			String lod = advanceReportBean.getLodInfo();
			
			String[] lods = lod.split(",");
			
			for (int i = 0; i < lods.length; i++) {
				LODBean = new AdvanceCPFForm2Bean();
				LODBean.setLodInfo(lods[i]);
				LODList.add(LODBean);
			}
			
			request.setAttribute("LODList", LODList);
			
		}
		
		String checkFlag = "";
		
		if (!advanceReportBean.getLodInfo().trim().equals("")) {
			checkFlag = "Y";
			dynaValide.set("chkwthdrwlflag", checkFlag);
		} else {
			checkFlag = "N";
			dynaValide.set("chkwthdrwlflag", checkFlag);
		}
		
		request.setAttribute("Form3ReportBean", advanceReportBean);
		request.setAttribute("bankMasterBean", bankMasterBean);
		request.setAttribute("basicReportBean", basicReportBean);
		
		try {
			if (frmName.equals("PFWCheckList")) {
				path = "loadPFWCheckListForm";
			} else if (frmName.equals("PFWCheckListReport")) {
				
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
						transactionNo,
						Constants.APPLICATION_PROCESSING_PFW_CHECK_LIST,
						imgagePath);
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
					
				} 
				path = "loadPFWCheckListReport";
				
			} else {
				if (flag.equals("ApprovalEdit")) {
					path = "loadPFWForm3ApprovalForm";
				} else {
					path = "viewForm3Report";
					
					
					
				}
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("loadPFWCheckListReport");
		}
		
		return mapping.findForward(path);
	}
	
	// New Method------
	
	public ActionForward savePFWCheckList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvancePFWFormBean advneFrm4Bean = new AdvancePFWFormBean(request);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String emoluments = "", subscriptionAmt = "", contributionAmt = "", cpfFund = "", amountRecmded = "", mnthsEmoluments = "", flag = "";
		String hbaDrwnFromAAI = "", wthdrwlInfo = "", lodDocumentsInfo = "", purposeOptionType = "";
		String paymentInfoFlag="N",updateBankFlag="N";
		advneFrm4Bean = this.fillFrmForm4Bean(form, request);
		EmpBankMaster bankMaster = new EmpBankMaster();
		
		if (dynaValide.getString("purposeType") != null) {
			advneFrm4Bean.setPurposeType(dynaValide.getString("purposeType"));
		}
		int totalRecUpdated = 0;
		if (request.getParameter("frmFlag") != null) {
			flag = request.getParameter("frmFlag");
		}
		if (dynaValide.getString("emoluments") != null) {
			emoluments = dynaValide.getString("emoluments");
		}
		
		if (dynaValide.getString("hbadrwnfrmaai") != null) {
			hbaDrwnFromAAI = dynaValide.getString("hbadrwnfrmaai");
		}
		
		if (dynaValide.getString("chkwthdrwlinfo") != null) {
			wthdrwlInfo = dynaValide.getString("chkwthdrwlinfo");
		}
		
		if (dynaValide.getString("subscriptionAmt") != null) {
			subscriptionAmt = dynaValide.getString("subscriptionAmt");
		}
		if (dynaValide.getString("contributionAmt") != null) {
			contributionAmt = dynaValide.getString("contributionAmt");
		}
		
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
		}
		
		if (request.getParameter("purposeOptionType") != null) {
			purposeOptionType = request.getParameter("purposeOptionType");
		}
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			 paymentInfoFlag = dynaValide.getString("paymentinfo") ;
		} 
		
		if (request.getParameter("bankflag") != null) {
			updateBankFlag=request.getParameter("bankflag");
		} 
		 


		if (dynaValide.getString("chkbankinfo") != null) {
			bankMaster.setChkBankInfo(dynaValide.getString("chkbankinfo"));
		} else {
			bankMaster.setChkBankInfo("");
		}
		
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		} 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		} 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		if (dynaValide.getString("partyName") != null) {
			bankMaster
			.setPartyName(dynaValide.getString("partyName"));
		}
		
		if (dynaValide.getString("partyAddress") != null) {
			bankMaster.setPartyAddress(dynaValide.getString("partyAddress"));
		}
		 
		totalRecUpdated = ptwAdvanceService.savePFWCheckList(advneFrm4Bean,
				emoluments, hbaDrwnFromAAI, wthdrwlInfo, subscriptionAmt,
				contributionAmt, lodDocumentsInfo, purposeOptionType, flag);
		String bankInfo= ptwAdvanceService.updateBankInfo(advneFrm4Bean.getPensionNo(),advneFrm4Bean.getAdvanceTransID(),updateBankFlag,paymentInfoFlag,bankMaster);
		
		
		return mapping.findForward("success");
	}
	// On 24-Apr-2012 for Notification navigation
	// New Method------
	
	public ActionForward advanceCheckListApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String pensionNo = "", transactionNo = "", returnPage = "", formType = "", transactionDate = "", frmName = "",srcFrmName="";
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceCPFForm2Bean avneFrm2Bean = new AdvanceCPFForm2Bean();
		AdvanceCPFForm2Bean LODBean = new AdvanceCPFForm2Bean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		ArrayList form2List = new ArrayList();
		ArrayList LODList = new ArrayList();
		ptwAdvanceService = new CPFPTWAdvanceService();
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
		}
		if (request.getParameter("frm_formType") != null) {
			formType = request.getParameter("frm_formType");
		}
		if (request.getParameter("frmTransDate") != null) {
			transactionDate = request.getParameter("frmTransDate");
		}
		//Notification Screen Purpose
		if (request.getParameter("srcFrmName") != null) {
			srcFrmName = request.getParameter("srcFrmName");
		}
		
		try {
			form2List = ptwAdvanceService.advanceCheckListApprove(pensionNo,
					transactionNo, formType, transactionDate);
			avneFrm2Bean = (AdvanceCPFForm2Bean) form2List.get(0);
			
			bankMaster = (EmpBankMaster) form2List.get(1);
			if (frmName.equals("CPFCheckList")) {
				this.fillForm2Bean(avneFrm2Bean, form);
				dynaValide.set("advanceTransIDDec", avneFrm2Bean
						.getAdvanceTransIDDec());
				
				request.setAttribute("advanceChkListBean", avneFrm2Bean);
				
				returnPage = "loadCPFCheckListForm";
			} else {
				request.setAttribute("reportBean", avneFrm2Bean);
				returnPage = "CPFverificationreport";
			}
			
			if (!avneFrm2Bean.getLodInfo().trim().equals("")) {
				String lod = avneFrm2Bean.getLodInfo();
				
				String[] lods = lod.split(",");
				
				for (int i = 0; i < lods.length; i++) {
					LODBean = new AdvanceCPFForm2Bean();
					LODBean.setLodInfo(lods[i]);
					LODList.add(LODBean);
				}
				
				request.setAttribute("LODList", LODList);
				
			}
			request.setAttribute("srcFrmName", srcFrmName);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			
			log.printStackTrace(e);
			returnPage = "loadCPFverificationform";
		}
		
		return mapping.findForward(returnPage);
		
	}
	
	// New Method------
	
	public ActionForward saveCPFCheckList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceCPFForm2Bean advneFrm2Bean = new AdvanceCPFForm2Bean(request);
		String lodDocumentsInfo = "",srcFrmName = "",path="";
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		if (dynaValide.getString("totalInst") != null) {
			advneFrm2Bean.setTotalInst(dynaValide.getString("totalInst"));
		}
		
		if (dynaValide.getString("outstndamount") != null) {
			advneFrm2Bean.setOutstndamount(dynaValide
					.getString("outstndamount"));
		}
		
		if (dynaValide.getString("purposeType") != null) {
			advneFrm2Bean.setPurposeType(dynaValide.getString("purposeType"));
		}
		
		if (dynaValide.getString("advnceapplid") != null) {
			advneFrm2Bean.setAmntRecommended(dynaValide
					.getString("advnceapplid"));
		}
		
		if (dynaValide.getString("pensionNo") != null) {
			advneFrm2Bean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		
		if (dynaValide.getString("advntrnsid") != null) {
			advneFrm2Bean.setAdvanceTransID(dynaValide.getString("advntrnsid"));
			if (!advneFrm2Bean.getAdvanceTransID().equals("")) {
				String[] fndTransID = advneFrm2Bean.getAdvanceTransID().split(
				"/");
				if (fndTransID.length == 3) {
					advneFrm2Bean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
		}
		if (request.getParameter("srcFrmName") != null) {
			srcFrmName = request.getParameter("srcFrmName");
		}
		if(srcFrmName.equals("Notifications")){
			path="loadNotifications";
		}else{
			path = "CPFCheckListSuccess";
		}
		advneFrm2Bean.setLodInfo(lodDocumentsInfo);
		advneFrm2Bean.setLodInfo(lodDocumentsInfo);
		int totalRecUpdated = 0;
		totalRecUpdated = ptwAdvanceService.saveCPFCheckList(advneFrm2Bean);
		log.info("====Path===="+path);
		return mapping.findForward(path);
	}
	
	// New Method---------
	
	public ActionForward loadFinalSettlementAppSearchForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlementAppSearchForm");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		dynaValide.set("employeeName", "");
		dynaValide.set("pfid", "");
		dynaValide.set("seperationreason", "");
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("searchFinalSettlement");
	}
	
	// New Method---------
	
	public ActionForward searchFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String flag = "FSSearch";
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		if (dynaValide.getString("nssanctionno") != null) {
			searchBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchFinalSettlement(
				searchBean, flag);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("finalsettlementsuccess");
	}
	
	// New Method---------
	
	public ActionForward loadFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlement");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		request.setAttribute("focusFlag", "false");
		dynaValide.set("pfid", "");
		dynaValide.set("cpfaccno", "");
		dynaValide.set("fhName", "");
		dynaValide.set("employeeNo", "");
		dynaValide.set("dateOfJoining", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("seperationreason", "");
		dynaValide.set("designation", "");
		dynaValide.set("region", "");
		dynaValide.set("dateOfBirth", "");
		dynaValide.set("seperationdate", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("station", "");
		
		return mapping.findForward("loadFinalSettlement");
	}
	
	// New Method---------
	// By Radha on 26-Dec-2011 for Restricting multiple submissions
	public ActionForward saveFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		session = request.getSession(true);
		if(request.getParameter("frm_region")!=null){
			advanceBasicBean.setRegion(request.getParameter("frm_region"));
		}
		advanceBasicBean = this.fillDynaFormToNoteSheetBean(form);
		request.setAttribute("advanceBean", advanceBasicBean);
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		employeeBankBean = ptwAdvanceService.employeeBankInfo(advanceBasicBean
				.getPensionNo());
		
		dynaValide.set("bankempname", employeeBankBean.getBankempname());
		dynaValide.set("bankempaddrs", employeeBankBean.getBankempaddrs());
		dynaValide.set("bankname", employeeBankBean.getBankname());
		dynaValide.set("branchaddress", employeeBankBean.getBranchaddress());
		dynaValide
		.set("banksavingaccno", employeeBankBean.getBanksavingaccno());
		dynaValide.set("bankemprtgsneftcode", employeeBankBean
				.getBankemprtgsneftcode());
		dynaValide.set("bankempmicrono", employeeBankBean.getBankempmicrono());
		dynaValide.set("empmailid", employeeBankBean.getEmpmailid());
		dynaValide.set("chkbankinfo", employeeBankBean.getChkBankInfo());
		this.getScreenTitle(request, response, "");
		saveToken(request);		 
		return mapping.findForward("contNoteSheet");
	}
	
	// New Method---------
	
	private AdvanceBasicBean fillDynaFormToNoteSheetBean(ActionForm form) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean basicBean = new AdvanceBasicBean();
		
		if (dynaValide.getString("employeeName") != null) {
			basicBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (dynaValide.getString("nssanctionno") != null) {
			basicBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		} else {
			basicBean.setNssanctionno("0");
		}
		if (dynaValide.getString("pensionNo") != null) {
			basicBean.setPensionNo(dynaValide.getString("pensionNo"));
		} else {
			basicBean.setPensionNo("0");
		}
		if (dynaValide.getString("cpfaccno") != null) {
			basicBean.setCpfaccno(dynaValide.getString("cpfaccno"));
		}
		if (dynaValide.getString("pfid") != null) {
			basicBean.setPfid(dynaValide.getString("pfid"));
		}
		
		if (dynaValide.getString("seperationdate") != null) {
			basicBean.setSeperationdate(dynaValide.getString("seperationdate"));
		}
		
		if (dynaValide.getString("designation") != null) {
			basicBean.setDesignation(dynaValide.getString("designation"));
		}
		
		if (dynaValide.getString("station") != null) {
			basicBean.setStation(dynaValide.getString("station"));
		}
		
		if (dynaValide.getString("region") != null) {
			basicBean.setRegion(dynaValide.getString("region"));
		}
		
		if (dynaValide.getString("seperationreason") != null) {
			basicBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		
		if (dynaValide.getString("permenentaddress") != null) {
			basicBean.setPermenentaddress(dynaValide
					.getString("permenentaddress"));
		}
		
		if (dynaValide.getString("presentaddress") != null) {
			basicBean.setPresentaddress(dynaValide.getString("presentaddress"));
		}
		
		if (dynaValide.getString("phoneno") != null) {
			basicBean.setPhoneno(dynaValide.getString("phoneno"));
		}
		
		if (dynaValide.getString("empmailid") != null) {
			basicBean.setMailID(dynaValide.getString("empmailid"));
		}
		
		if (dynaValide.getString("resignationreason") != null) {
			basicBean.setResignationreason(dynaValide.getString("resignationreason"));
		}
		
		if (dynaValide.getString("organizationname") != null) {
			basicBean.setOrganizationname(dynaValide.getString("organizationname"));
		}
		
		if (dynaValide.getString("organizationaddress") != null) {
			basicBean.setOrganizationaddress(dynaValide.getString("organizationaddress"));
		}
		
		if (dynaValide.getString("appointmentdate") != null) {
			basicBean.setAppointmentdate(dynaValide.getString("appointmentdate"));
		}		
		
		if (dynaValide.getString("postedas") != null) {
			basicBean.setPostedas(dynaValide.getString("postedas"));
		}		
		
		if (dynaValide.getString("workingplace") != null) {
			basicBean.setWorkingplace(dynaValide.getString("workingplace"));
		}
		
		return basicBean;
	}
	
	// New Method---------
	
	// New Method---------
	
	private AdvanceBasicBean fillFinalSettlementFormNextToBean(ActionForm form,
			HttpServletRequest request) {
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean basicBean = new AdvanceBasicBean(request);
		
		if (dynaValide.getString("pensionNo") != null) {
			basicBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		
		if (dynaValide.getString("employeeName") != null) {
			basicBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("fhName") != null) {
			basicBean.setFhName(dynaValide.getString("fhName"));
		}
		
		if (dynaValide.getString("region") != null) {
			basicBean.setRegion(dynaValide.getString("region"));
		}
		if (dynaValide.getString("designation") != null) {
			basicBean.setDesignation(dynaValide.getString("designation"));
		}
		if (dynaValide.getString("dateOfJoining") != null) {
			basicBean.setDateOfJoining(dynaValide.getString("dateOfJoining"));
		}
		if (dynaValide.getString("station") != null) {
			basicBean.setStation(dynaValide.getString("station"));
		}
		if (dynaValide.getString("seperationreason") != null) {
			basicBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("seperationdate") != null) {
			basicBean.setSeperationdate(dynaValide.getString("seperationdate"));
		}
		if (dynaValide.getString("permenentaddress") != null) {
			basicBean.setPermenentaddress(dynaValide
					.getString("permenentaddress"));
		}
		if (dynaValide.getString("presentaddress") != null) {
			basicBean.setPresentaddress(dynaValide.getString("presentaddress"));
		}
		if (dynaValide.getString("phoneno") != null) {
			basicBean.setPhoneno(dynaValide.getString("phoneno"));
		}
		if (dynaValide.getString("empmailid") != null) {
			basicBean.setMailID(dynaValide.getString("empmailid"));
		}
		
		if (dynaValide.getString("empage") != null) {
			basicBean.setEmpage(dynaValide.getString("empage"));
		}
		
		if (dynaValide.getString("maritalstatus") != null) {
			basicBean.setMaritalstatus(dynaValide.getString("maritalstatus"));
		}
		
		if (dynaValide.getString("emprelation") != null) {
			basicBean.setEmprelation(dynaValide.getString("emprelation"));
		}
		
		if (dynaValide.getString("empaddress") != null) {
			basicBean.setEmpaddress(dynaValide.getString("empaddress"));
		}
		
		if (dynaValide.getString("quarterallotment") != null) {
			basicBean.setQuarterallotment(dynaValide
					.getString("quarterallotment"));
		}
		
		if (dynaValide.getString("quarterno") != null) {
			basicBean.setQuarterno(dynaValide.getString("quarterno"));
		}
		
		if (dynaValide.getString("colonyname") != null) {
			basicBean.setColonyname(dynaValide.getString("colonyname"));
		}
		
		if (dynaValide.getString("empstation") != null) {
			basicBean.setEmpstation(dynaValide.getString("empstation"));
		}
		
		if (dynaValide.getString("deathplace") != null) {
			basicBean.setDeathplace(dynaValide.getString("deathplace"));
		}
		
		
		return basicBean;
	}
	
	// New Method---------
	
	public ActionForward loadFinalSettlementVerificationSearchForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlementVerificationSearchForm");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		dynaValide.set("employeeName", "");
		dynaValide.set("pfid", "");
		dynaValide.set("seperationreason", "");
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("searchFinalSettlementVerification");
	}
	 //	On 15-Feb-2012 for PFW   Sanction Order reports 
	//On 08-Feb-2012 for FinalSettlement Arrear Sanction Order reports 
	public ActionForward loadPrintSanctionOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadPrintSanctionOrder");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String frmName="",path="";
		 
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		if(frmName.equals("FSSO") || frmName.equals("FSARREARSO")){
			path="loadPrintSanctionOrder";
		}else{
			path="loadPFWPrintSanctionOrder";
		}
		dynaValide.set("employeeName", "");
		dynaValide.set("pfid", "");
		dynaValide.set("seperationreason", "");
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward(path);
	}
	
	// New Method---------
	
	public ActionForward searchFinalSettlementVerification(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String flag = "FSVerification";
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		if (dynaValide.getString("nssanctionno") != null) {
			searchBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}

		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchFinalSettlement(
				searchBean, flag);
		
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("fsverificationsearchsuccess");
	}
//	On 08-Feb-2012 for FinalSettlement Arrear Sanction Order reports 
	public ActionForward searchPrintSanctionOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String flag = "PrintSanctionOrder",frmName="";
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		if (dynaValide.getString("sanctionno") != null) {
			searchBean.setSanctionno(dynaValide.getString("sanctionno"));
		}
		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		if(frmName.equals("FSSO")){
			searchBean.setFsType("NON-ARREAR");
		}else{
			searchBean.setFsType("ARREAR");
		}
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchPrintSanctionOrder(
				searchBean, flag);
		
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("printSanctionOrder");
	}
	
	
//	On 15-Feb-2012 for PFW Sanction Order reports 
	public ActionForward searchPrintPFWSanctionOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);	 
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
			
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
				
			}
			
		}
		
		if (dynaValide.getString("advanceType") != null) {
			searchBean.setAdvanceType("PFW");
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		if ((dynaValide.getString("advanceTransyear") != null && !(dynaValide.getString("advanceTransyear").equals("")))) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		/*
		 * if(dynaValide.getString("advanceTransStatus")!=null){
		 * searchBean.setAdvanceTrnStatus("A"); }
		 */
		
		if (request.getParameter("frm_name") != null) {
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		// searchBean.setVerifiedBy("FINANCE");
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchPrintPFWSanctionOrder(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		  
		return mapping.findForward("printPFWSanctionOrder");
	}
	
	// New Method---------
	
	public ActionForward finalSettlementVerificationApproval(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean advanceBasicReportBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean advReportBasicBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean otherDetBean = new AdvanceBasicReportBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		AdvanceBasicReportBean LODBean = null;
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList list = new ArrayList();
		ArrayList LODList = new ArrayList();
		ArrayList postingDetList = new ArrayList();
		ArrayList transList = new ArrayList();
		String path = "", frmName = "";
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", sanctionNo = "";
		
		saveToken(request);
		
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmSanctionNo") != null) {
			sanctionNo = request.getParameter("frmSanctionNo");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		
		try {
			log.info("advanceReport::pensionNo" + pensionNo + "sanctionNo"
					+ sanctionNo);
			list = ptwAdvanceService.finalSettlementVerificationApproval(
					pensionNo, sanctionNo, frmName);
			log.info("Size" + list.size());
			
			if (list.size() != 0) {
				advanceReportBean = (AdvanceBasicReportBean) list.get(0);
				bankMasterBean = (EmpBankMaster) list.get(1);
				postingDetList = (ArrayList) list.get(2);
				advReportBasicBean = (AdvanceBasicReportBean) list.get(3);
				otherDetBean = (AdvanceBasicReportBean) list.get(4);
				
				request.setAttribute("Form2ReportBean", advanceReportBean);
				request.setAttribute("bankMasterBean", bankMasterBean);
				request.setAttribute("otherDetBean", otherDetBean);
				
				if (postingDetList.size() != 0) {
					request.setAttribute("postingDetList", postingDetList);
				}
				
				request.setAttribute("empNoteSheetPersonalInfoBean",
						advReportBasicBean);
			}
			
			if (!advanceReportBean.getLodInfo().equals("")) {
				String lod = advanceReportBean.getLodInfo();
				String[] lods = lod.split(",");
				for (int i = 0; i < lods.length; i++) {
					LODBean = new AdvanceBasicReportBean();
					LODBean.setLodInfo(lods[i]);
					LODList.add(LODBean);
				}
				request.setAttribute("LODList", LODList);
			}
			this.getScreenTitle(request, response, "");
			
			log.info("------Seperation Reason------"+advanceReportBean.getSeperationreason());
			
			
			if (frmName.equals("FSFormI")) {
				path = "loadFSVerificationForm";
			} else {
				
				this.getLoginDetails(request);
				CPFPFWTransBean transBean=new CPFPFWTransBean();
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				transList = (ArrayList) cpfInfo
				.readTransInfo(
						pensionNo,
						sanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_PERSONAL_VERFICATION,
						imgagePath);
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);					
					transBean=(CPFPFWTransBean)transList.get(0);
					log.info("Approved Date in Action==========="+transBean.getTransApprovedDate());
					request.setAttribute("transBean", transBean);
				}
				
				if(advanceReportBean.getSeperationreason().equals("Resignation") || advanceReportBean.getSeperationreason().equals("VRS")){		
					
					if(frmName.equals("FSFormIReport") && advanceReportBean.getResignationreason().equals("personal")){
						path = "loadFSResigPersonalVerificaitonReport";
					}else{					
						path = "loadFSAppResignationReport";
					}
				}else if(advanceReportBean.getSeperationreason().equals("Retirement")){		
					path = "loadFSAppRetirementReport";
				}else if(advanceReportBean.getSeperationreason().equals("Death")){		
					path = "loadFSAppDeathReport";
				}else{
					path = "loadFSAppReport";
				}
				
				/*if (!frmName.equals("FSAppForm")) {
				 if (transList.size()==0) {
				 path = "failureMessage";				
				 }
				 } 		*/	
				
			}
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("loadFSAppReport");
		}
		
		log.info("=======Path=======" + path);
		
		return mapping.findForward(path);
		
	}
	public ActionForward saveFinalSettlementVerification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean(request);
		EmpBankMaster bankMaster = new EmpBankMaster();
		String userName = "", computerName = "", messages = "",frmName="";
		session = request.getSession(true);
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		log.info("editNoteSheet==============="+userName+"computerName==========="+computerName);
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String lodDocumentsInfo = "";
		
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
		}
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advanceBean = this.fillFinalSettlementFormNextToBean(form, request);
		advanceBean.setLodInfo(lodDocumentsInfo);
		
		if (dynaValide.getString("authorizedsts") != null) {
			advanceBean.setAuthrizedStatus(dynaValide
					.getString("authorizedsts"));
		}
		
		if (dynaValide.getString("nssanctionno") != null) {
			advanceBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
		}
		
		
		bankMaster = this.fillDynaFormBankToBean(form);
		
		if (!userName.equals("")) {
			advanceBean.setUserName(userName);
		}
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			advanceBean.setPaymentinfo(dynaValide.getString("paymentinfo"));
		}else{
		advanceBean.setPaymentinfo("N");
		}
		
		if (request.getParameter("bankflag") != null) {
			advanceBean.setUpdateBankFlag(request.getParameter("bankflag"));
		}else{
			advanceBean.setUpdateBankFlag("N");
		}
		
		if(!frmName.equals("")){
			messages = ptwAdvanceService.addFinalSettlementVerificationInfo(advanceBean);
			 String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
				
		}
		
		resetToken(request);
		
		AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
		request.setAttribute("advanceBean", advanceBasic);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		 
		saveMessages(request, actionMessage);
		
		
		
		ActionForward forward=null;
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		if (frmName
				.equals(Constants.APPLICATION_FINAL_SETTLEMENT_PERSONNAL_VERIFICATION_FORM)) {
			
			forward = mapping.findForward("finalsettlementverificationsuccess");
			params.put("method", "searchFinalSettlementVerification");
			this.getScreenTitle(request, response, "");
			if (frmName
					.equals(Constants.APPLICATION_FINAL_SETTLEMENT_PERSONNAL_VERIFICATION_FORM))
				fwdParams.add("frm_name", Constants.APPLICATION_FINAL_SETTLEMENT_PERSONNAL_VERIFICATION_FORM);
			
		} 
		return fwdParams.forward(forward); 
		
		 
	}
	
	public ActionForward detailsOfPosting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::detailsOfPosting");
		String pensionNo = "", wthdrwlAmount = "", wthdrwlDate = "",frmName="",path="";
		
		ArrayList wthDrwlList = new ArrayList();
		AdvanceBasicBean basicBean = new AdvanceBasicBean();
		
		if (request.getParameter("frm_pensionno") != null) {
			pensionNo = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		
		String postingDetStr = ptwAdvanceService.detailsOfPosting(pensionNo);
		
		
		if (!postingDetStr.equals("")) {
			request.setAttribute("postingDetStr", postingDetStr);
		}
		
		if(frmName.equals("FSAppNew")){
			path="addPostingDetails";
		}else{
			path="editPostingDetails";
		}
		return mapping.findForward(path);
	}
	
	// Replace the complete method------------
	// modified on 30-Jul-11 for passing formName 
	public ActionForward saveFinalSettlementNextInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		String userName = "", computerName = "", messages = "" , frmName="";
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		
		log.info("saveFinalSettlementNextInfo::userName" + userName
				+ "computerName" + computerName);
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String lodDocumentsInfo = "";
		log.info("= frmName ====="+ request.getParameter("frm_name"));
		log.info("= isTokenValid(request) ====="+ isTokenValid(request));
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName = "";
		}
		
		
		
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
		}
		log.info("Enclose Documents" + lodDocumentsInfo);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advanceBean = this.fillFinalSettlementFormNextToBean(form, request);
		advanceBean.setLodInfo(lodDocumentsInfo);
		
		if (dynaValide.getString("cpfaccno") != null) {
			advanceBean.setCpfaccno(dynaValide.getString("cpfaccno"));
		}
		
		if (dynaValide.getString("postingdetails") != null) {
			advanceBean.setPostingdetails(dynaValide
					.getString("postingdetails"));
		}
		
		if (dynaValide.getString("bankdetail") != null) {
			if (dynaValide.getString("bankdetail").equals("NO-SELECT")) {
				advanceBean.setPaymentinfo("N");
			} else {
				advanceBean.setPaymentinfo("Y");
			}
		}
		
		if (dynaValide.getString("resignationreason") != null) {
			advanceBean.setResignationreason(dynaValide.getString("resignationreason"));
		}
		
		if (dynaValide.getString("organizationname") != null) {
			advanceBean.setOrganizationname(dynaValide.getString("organizationname"));
		}
		
		if (dynaValide.getString("organizationaddress") != null) {
			advanceBean.setOrganizationaddress(dynaValide.getString("organizationaddress"));
		}
		
		if (dynaValide.getString("appointmentdate") != null) {
			advanceBean.setAppointmentdate(dynaValide.getString("appointmentdate"));
		}
		
		if (dynaValide.getString("postedas") != null) {
			advanceBean.setPostedas(dynaValide.getString("postedas"));
		}
		
		if (dynaValide.getString("workingplace") != null) {
			advanceBean.setWorkingplace(dynaValide.getString("workingplace"));
		}		
		
		bankMaster = this.fillDynaFormBankToBean(form);
		
		
		
		if (!userName.equals("")) {
			advanceBean.setUserName(userName);
		}
		
		log.info("--------advanceBean.getUserName()--------"
				+ advanceBean.getUserName());
		if(!frmName.equals("")){
		messages = ptwAdvanceService.addFinalSettlementInfo(advanceBean,
				bankMaster);
		}
		log.info("-----message in ACTION-----" + messages);
		AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
		request.setAttribute("advanceBean", advanceBasic);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		
		saveMessages(request, actionMessage);
		// this.getScreenTitle(request,response,advanceBean.getAdvanceType());
		
		//return mapping.findForward("finalsettlementsuccess");
		
		ActionForward forward = mapping.findForward("finalsettlementsuccess");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		params.put("method", "searchFinalSettlement");		 
		this.getScreenTitle(request, response, "");		 
			fwdParams.add("frm_name", "FSAppForm");		  
		return fwdParams.forward(forward);
		
	}
	
	public ActionForward deleteFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		String userName = "", computerName = "", frmNm = "";
		session = request.getSession(true);
		ActionForward forward =null;
		userName = userid;
		computerName = userIPaddress;
		
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		advanceBean = this.fillDynaFormToBean(form, request);
		
		if (request.getParameter("frmPensionNo") != null) {			 
			advanceBean.setPensionNo(request.getParameter("frmPensionNo"));
		}
		
		if (request.getParameter("frmSanctionNo") != null) {		 
			advanceBean.setNssanctionno(request.getParameter("frmSanctionNo"));
		}
		
		if (request.getParameter("frm_name") != null) {
			frmNm = request.getParameter("frm_name");
		}
		
		if (request.getParameter("frm_verifiedBy") != null) {
			advanceBean.setVerifiedby(request.getParameter("frm_verifiedBy"));;
		}
		
		if (request.getParameter("frm_arrearType") != null) {
			advanceBean.setArreartype(request.getParameter("frm_arrearType"));
		}
		  
		log.info("deleteFinalSettlement:=====================================frmNm"+frmNm);
		int n = ptwAdvanceService.deleteFinalSettlement(advanceBean);
		this.getScreenTitle(request, response, "");
		// return mapping.findForward("updatesuccess");
		
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		if (frmNm
				.equals(Constants.APPLICATION_FINAL_SETTLEMENT_APPLICATION_FORM)) {
			forward = mapping.findForward("finalsettlementsuccess");
			params.put("method", "searchFinalSettlement");
			this.getScreenTitle(request, response, "");
			if (frmNm
					.equals(Constants.APPLICATION_FINAL_SETTLEMENT_APPLICATION_FORM))
				fwdParams
				.add(
						"frm_name",
						Constants.APPLICATION_FINAL_SETTLEMENT_APPLICATION_FORM);
			
		} else if (frmNm
				.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_FORM)) {
			
			forward = mapping.findForward("finalsettlementarrearsuccess");
			params.put("method", "searchFinalSettlementArrear");
			this.getScreenTitle(request, response, "");
			if (frmNm
					.equals(Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_FORM))
				fwdParams.add("frm_name",
						Constants.APPLICATION_FINAL_SETTLEMENT_ARREAR_FORM);
			
		}
		
		return fwdParams.forward(forward);
	}
	//By Radha On 25-Apr-2012 for Jasper Report Generation Purpose
	public ActionForward updateNoteSheetSanctionDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String frmNm = "";
		
		if (request.getParameter("frmSanctionNo") != null) {
			searchBean.setNssanctionno(request.getParameter("frmSanctionNo"));
		}
		
		if (request.getParameter("frm_name") != null) {
			frmNm = request.getParameter("frm_name");
		}
		if (request.getParameter("frmSeperationReason") != null) {
			searchBean.setSeperationreason(request.getParameter("frmSeperationReason"));
		}
		if(frmNm.equals("FSFormIII"))
		{
			searchBean.setArreartype("NON-ARREAR") ;
		}else if(frmNm.equals("FSArrearRecommendation")){
			searchBean.setArreartype("ARREAR") ;
		}
		this.getLoginDetails(request);
		searchBean.setLoginUserId(this.loginUserId);
		searchBean.setLoginUserName(this.loginUserName);
		searchBean.setLoginUnitCode(this.loginUnitCode);
		searchBean.setLoginRegion(this.loginRegion);
		searchBean.setLoginUserDispName(this.loginSignName);
		
		String imgagePath = getServlet().getServletContext()
		.getRealPath("/")
		+ "/uploads/dbf/";
		String dirPath = getServlet().getServletContext()
		.getRealPath("/");
		searchBean.setImgagePath(imgagePath);
		searchBean.setDirPath(dirPath);
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		ptwAdvanceService.updateNoteSheetSanctionDate(searchBean);
		this.getScreenTitle(request, response, "");
		 
		ActionForward forward =null;
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		  
		if(frmNm.equals("FSFormIII"))
		{
			params.put("method", "searchNoteSheet");
			forward = mapping.findForward("success");
			this.getScreenTitle(request, response, "");
		 
			if (frmNm.equals("FSFormIII"))
				fwdParams.add("frm_name", "FSFormIII");
		}else if(frmNm.equals("FSArrearRecommendation")){
			
			params.put("method", "searchFinalSettlementArrearProcess");
			forward = mapping.findForward("processsearchsuccess");
			this.getScreenTitle(request, response, "");
			 
			if (frmNm.equals("FSArrearRecommendation"))
				fwdParams.add("frm_name", "FSArrearRecommendation");
			
		}
	 
		return fwdParams.forward(forward);
	}

	
	// Replace complete method
	
	
	
	public void getLoginDetails(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("user");
		this.loginUserId = loginInfo.getUserId();
		this.loginUserName = loginInfo.getUserName();
		this.loginUnitCode = loginInfo.getUnitCd();
		this.loginUnitName = loginInfo.getUnitName();
		this.loginRegion = loginInfo.getRegion();
		this.loginProfile = loginInfo.getProfile();
		this.loginAccountType = loginInfo.getAccountType();
		this.loginSignName = loginInfo.getDisplayName();
		this.loginUserDesignation= loginInfo.getDesignation();
	}
	
	public ActionForward updatePFWSanctionDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String frmNm = "",userName="",computerName="";
		
		if (request.getParameter("frmTransNo") != null) {
			searchBean.setAdvanceTransID(request.getParameter("frmTransNo"));
		}
		
		if (request.getParameter("frm_name") != null) {
			frmNm = request.getParameter("frm_name");
		}
		this.getLoginDetails(request);
		userName = this.loginUserName;
		
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		ptwAdvanceService.updatePFWSanctionDate(searchBean);
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("Form3success");
	}
	public ActionForward editFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean adBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		AdvanceBasicReportBean otherDetBean = new AdvanceBasicReportBean();
		
		String currentDate = "", userName = "", computerName = "", messages = "", path = "", selectedEmpName = "", selectedRegion = "", pensionNo = "", sanctionNo = "", cpfaccno = "", frmName = "";
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		
		userName = userid;
		computerName = userIPaddress;
		
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmSanctionNo") != null) {
			sanctionNo = request.getParameter("frmSanctionNo");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		currentDate = "-Feb-2009";
		personalList = commonService.loadPersonalTransInfo(selectedEmpName,
				selectedRegion, pensionNo, cpfaccno, "-Sep-2007");
		advanceBean = (AdvanceBasicBean) personalList.get(0);
		advanceBean.setDateOfMembership(advanceBean.getDateOfJoining());
		request.setAttribute("personalInfo", advanceBean);
		ArrayList airportsList = new ArrayList();
		if (!advanceBean.getRegion().equals("")) {
			airportsList = commonUtil.getAirportList(advanceBean.getRegion());
			
			
			if (airportsList.size() > 0) {
				for (int j = 0; j < airportsList.size(); j++) {
					adBean = (AdvanceBasicBean) airportsList.get(j);					
				}
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		noteSheetBean = commonService.loadNoteSheetInfo(pensionNo, sanctionNo);
		request.setAttribute("noteSheetInfo", noteSheetBean);
		
		
		nomineeList = commonService.loadNomineeInfo(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		if (nomineeList.size() != 0) {
			adBean = (AdvanceBasicBean) nomineeList.get(0);
			request.setAttribute("nomineeList", nomineeList);
		}
		
		
		otherDetBean=(AdvanceBasicReportBean)ptwAdvanceService.loadNoteSheetOtherDetails(advanceBean.getPensionNo());
		request.setAttribute("otherDetBean", otherDetBean);
		
		
		dynaValide.set("remarks", "");
		dynaValide.set("nomineename", "");
		dynaValide.set("nomineeaddress", "");
		dynaValide.set("nomineeDOB", "");
		dynaValide.set("nomineerelation", "");
		dynaValide.set("gardianname", "");
		dynaValide.set("gardianaddress", "");
		dynaValide.set("totalshare", "");
		
		path = "editFinalSettlement";
		
		return mapping.findForward(path);
		
	}
	public ActionForward continueFinalSettlement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList LODList=new ArrayList();
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		AdvanceBasicReportBean  noteSheetPersonalBean=new AdvanceBasicReportBean();
		AdvanceBasicBean LODBean = null;
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		String frm_station = "", frm_sepReason = "", frm_pensionno = "",frm_sanctionno="", trust = "";
		session = request.getSession(true);
		
		if (request.getParameter("frm_station") != null) {
			frm_station = request.getParameter("frm_station");
		}
		if (request.getParameter("frm_sepReason") != null) {
			frm_sepReason = request.getParameter("frm_sepReason");
		}
		if (request.getParameter("frm_pensionno") != null) {
			frm_pensionno = request.getParameter("frm_pensionno");
		}
		if (request.getParameter("frm_sanctionno") != null) {
			frm_sanctionno = request.getParameter("frm_sanctionno");
		}
		
		log.info("=====Reason in Action======="+dynaValide.getString("resignationreason"));
		
		
		advanceBasicBean = this.fillDynaFormToBean(form, request);		
		advanceBasicBean.setPensionNo(frm_pensionno);		
		
		if (dynaValide.getString("paymentinfo") != null) {
			advanceBasicBean.setPaymentinfo(dynaValide.getString("paymentinfo"));
		}
		
		if (dynaValide.getString("resignationreason") != null) {
			advanceBasicBean.setResignationreason(dynaValide.getString("resignationreason"));
		}
		
		if(advanceBasicBean.getResignationreason().equals("otherdepartment")){
			
			if (dynaValide.getString("organizationname") != null) {
				advanceBasicBean.setOrganizationname(dynaValide.getString("organizationname"));
			}
			if (dynaValide.getString("organizationaddress") != null) {
				advanceBasicBean.setOrganizationaddress(dynaValide.getString("organizationaddress"));
			}
			if (dynaValide.getString("appointmentdate") != null) {
				advanceBasicBean.setAppointmentdate(dynaValide.getString("appointmentdate"));
			}
			if (dynaValide.getString("postedas") != null) {
				advanceBasicBean.setPostedas(dynaValide.getString("postedas"));
			}
			if (dynaValide.getString("workingplace") != null) {
				advanceBasicBean.setWorkingplace(dynaValide.getString("workingplace"));
			}
		}
		
		
		if(dynaValide.getString("paymentinfo").equals("Y")){			
			advanceBasicBean.setBankdetail("bank");
		}else{
			advanceBasicBean.setBankdetail("NO-SELECT");
		}
		
		if (dynaValide.getString("lodInfo") != null) {
			advanceBasicBean.setLodInfo(dynaValide.getString("lodInfo"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		employeeBankBean = ptwAdvanceService.employeeBankInfo(advanceBasicBean.getPensionNo(),frm_sanctionno);
		dynaValide.set("dateOfJoining", advanceBasicBean.getDateOfJoining());
		
		
		dynaValide.set("bankempname", employeeBankBean.getBankempname());
		dynaValide.set("banksavingaccno", employeeBankBean.getBanksavingaccno());
		dynaValide.set("bankname", employeeBankBean.getBankname());
		dynaValide.set("branchaddress", employeeBankBean.getBranchaddress());
		dynaValide.set("bankemprtgsneftcode", employeeBankBean.getBankemprtgsneftcode());
		dynaValide.set("bankpaymentmode", employeeBankBean.getBankpaymentmode());
		dynaValide.set("city", employeeBankBean.getCity());
		
		
		if(!advanceBasicBean.getLodInfo().equals("")){
			String lod=advanceBasicBean.getLodInfo();
			String[] lods=lod.split(",");			
			for(int i=0;i<lods.length;i++){			
				LODBean= new AdvanceBasicBean();
				LODBean.setLodInfo(lods[i]);	
				LODList.add(LODBean);
			}								
			request.setAttribute("LODList",LODList);
		}
		noteSheetPersonalBean=ptwAdvanceService.loadNoteSheetPersonalDetails(frm_pensionno);
		request.setAttribute("noteSheetPersonalBean", noteSheetPersonalBean);
		
		
		dynaValide.set("empage", noteSheetPersonalBean.getEmpage());
		dynaValide.set("maritalstatus", noteSheetPersonalBean.getMaritalstatus());
		dynaValide.set("emprelation", noteSheetPersonalBean.getEmprelation());
		dynaValide.set("empaddress", noteSheetPersonalBean.getEmpaddress());
		dynaValide.set("quarterallotment", noteSheetPersonalBean.getQuarterallotment());
		dynaValide.set("quarterno", noteSheetPersonalBean.getQuarterno());
		dynaValide.set("colonyname", noteSheetPersonalBean.getColonyname());
		dynaValide.set("empstation", noteSheetPersonalBean.getEmpstation());
		
		
		request.setAttribute("advanceBean", advanceBasicBean);
		session.setAttribute("backAdvaceBean", advanceBasicBean);
		
		return mapping.findForward("contFinalSettlement");
	}
	
	public ActionForward updateFinalSettlementInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		EmpBankMaster bankMaster = new EmpBankMaster();
		String userName = "", computerName = "", messages = "",withDrawalList="";
		session = request.getSession(true);
		userName = userid;
		computerName = userIPaddress;
		log.info("saveFinalSettlementNextInfo::userName" + userName
				+ "computerName" + computerName);
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String lodDocumentsInfo = "";
		
		if (request.getParameter("lodinfo") != null) {
			lodDocumentsInfo = request.getParameter("lodinfo");
		}
		log.info("Enclose Documents" + lodDocumentsInfo);
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		advanceBean = this.fillFinalSettlementFormNextToBean(form, request);
		advanceBean.setLodInfo(lodDocumentsInfo);
		
		if (dynaValide.getString("cpfaccno") != null) {
			advanceBean.setCpfaccno(dynaValide.getString("cpfaccno"));
		}
		
		if (dynaValide.getString("postingdetails") != null) {
			advanceBean.setPostingdetails(dynaValide
					.getString("postingdetails"));
		}
		
		if (dynaValide.getString("bankdetail") != null) {
			if (dynaValide.getString("bankdetail").equals("NO-SELECT")) {
				advanceBean.setPaymentinfo("N");
			} else {
				advanceBean.setPaymentinfo("Y");
			}
		}
		
		if (dynaValide.getString("nssanctionno") != null) {
			advanceBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		
		
		bankMaster = this.fillDynaFormBankToBean(form);
		
		if (!userName.equals("")) {
			advanceBean.setUserName(userName);
		}		
		
		if (dynaValide.getString("empage") != null) {
			advanceBean.setEmpage(dynaValide.getString("empage"));
		}
		
		if (dynaValide.getString("maritalstatus") != null) {
			advanceBean.setMaritalstatus(dynaValide.getString("maritalstatus"));
		}
		
		if (dynaValide.getString("emprelation") != null) {
			advanceBean.setEmprelation(dynaValide.getString("emprelation"));
		}
		
		if (dynaValide.getString("empaddress") != null) {
			advanceBean.setEmpaddress(dynaValide.getString("empaddress"));
		}
		
		if (dynaValide.getString("quarterallotment") != null) {
			advanceBean.setQuarterallotment(dynaValide.getString("quarterallotment"));
		}
		
		if(advanceBean.getQuarterallotment().equals("Y")){
			if (dynaValide.getString("quarterno") != null) {
				advanceBean.setQuarterno(dynaValide.getString("quarterno"));
			}
			
			if (dynaValide.getString("colonyname") != null) {
				advanceBean.setColonyname(dynaValide.getString("colonyname"));
			}
			
			if (dynaValide.getString("empstation") != null) {
				advanceBean.setEmpstation(dynaValide.getString("empstation"));
			}
		}else{
			advanceBean.setQuarterno("");
			advanceBean.setColonyname("");
			advanceBean.setEmpstation("");
		}
		
		if (dynaValide.getString("postingdetails") != null) {
			withDrawalList=dynaValide.getString("postingdetails");
			
			log.info("withDrawalList in Action--------"+withDrawalList);
			
			if(!withDrawalList.equals("")){			
				advanceBean.setPostingdetails(withDrawalList);
			}else{						
				String withDrawals="";
				
				if(!dynaValide.getString("wthdrwlpurpose").equals("")){				
					withDrawals=dynaValide.getString("wthdrwlpurpose");
				}else{
					withDrawals="-";
				}
				
				if(!dynaValide.getString("wthdrwlAmount").equals("")){				
					withDrawals+="+"+dynaValide.getString("wthdrwlAmount");
				}
				
				if(dynaValide.getString("wthDrwlTrnsdt")!=null){				
					withDrawals+="+"+dynaValide.getString("wthDrwlTrnsdt");				
				}		
				advanceBean.setPostingdetails(withDrawals);
				
			}
		}
		
		if (dynaValide.getString("resignationreason") != null) {
			advanceBean.setResignationreason(dynaValide.getString("resignationreason"));
		}
		
		if (dynaValide.getString("organizationname") != null) {
			advanceBean.setOrganizationname(dynaValide.getString("organizationname"));
		}
		
		if (dynaValide.getString("organizationaddress") != null) {
			advanceBean.setOrganizationaddress(dynaValide.getString("organizationaddress"));
		}
		
		if (dynaValide.getString("appointmentdate") != null) {
			advanceBean.setAppointmentdate(dynaValide.getString("appointmentdate"));
		}
		
		if (dynaValide.getString("postedas") != null) {
			advanceBean.setPostedas(dynaValide.getString("postedas"));
		}
		
		if (dynaValide.getString("workingplace") != null) {
			advanceBean.setWorkingplace(dynaValide.getString("workingplace"));
		}
		
		messages = ptwAdvanceService.updateFinalSettlementInfo(advanceBean);
		
		  String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
			
		log.info("-----message in ACTION-----" + messages);
		AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
		request.setAttribute("advanceBean", advanceBasic);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		
		saveMessages(request, actionMessage);				
		session.setAttribute("backFinalSettlementBean",advanceBean);
		
		
		ActionForward forward = mapping.findForward("finalsettlementsuccess");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
		params.put("method", "searchFinalSettlement");		 
		this.getScreenTitle(request, response, "");		 
			fwdParams.add("frm_name", "FSAppForm");		  
		return fwdParams.forward(forward);
		 
	 
		
	}
	public ActionForward loadFinalSettlementAppBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("AdvancesAction::loadFinalSettlementAppBack"+request.getAttribute("advanceBasicBean"));
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		AdvanceBasicReportBean otherDetBean=new AdvanceBasicReportBean();
		ArrayList personalList=new ArrayList();
		String sanctionNo="",pensionNo="",selectedEmpName="",selectedRegion="",cpfaccno="";
		ArrayList airportsList=new ArrayList();
		ArrayList list=new ArrayList();
		session=request.getSession(true);
		request.setAttribute("focusFlag","true");
		
		if(request.getParameter("frm_sanctionno")!=null){
			sanctionNo=request.getParameter("frm_sanctionno");
		}		
		if(request.getParameter("frm_pensionno")!=null){
			pensionNo=request.getParameter("frm_pensionno");
		}
		
		if(session.getAttribute("backFinalSettlementBean")!=null){
			advanceBean=(AdvanceBasicBean)session.getAttribute("backFinalSettlementBean");
			list.add(advanceBean);
			session.removeAttribute("backFinalSettlementBean");
		}		
		
		if(!advanceBean.getRegion().equals("")){				
			airportsList=commonUtil.getAirportList(advanceBean.getRegion());				
			if(airportsList.size()>0){				
				request.setAttribute("airportsList",airportsList);
			}		
		}	
		
		personalList=commonService.loadPersonalTransInfo(selectedEmpName,selectedRegion,pensionNo,cpfaccno,"-Sep-2007");
		
		if(personalList.size()!=0){				
			advanceBean = (AdvanceBasicBean) personalList.get(0);			
			request.setAttribute("personalInfo", advanceBean);
		}
		
		noteSheetBean = commonService.loadNoteSheetInfo(pensionNo, sanctionNo);
		request.setAttribute("noteSheetInfo", noteSheetBean);
		
		otherDetBean=(AdvanceBasicReportBean)ptwAdvanceService.loadNoteSheetOtherDetails(advanceBean.getPensionNo());
		request.setAttribute("otherDetBean", otherDetBean);	
		
		
		return mapping.findForward("loadFinalSettlementAppEditForm");
	}
	// New Method
	
	public ActionForward loadFinalSettlementArrearSearchForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlementArrearSearchForm");
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		dynaValide.set("employeeName", "");
		dynaValide.set("pfid", "");
		dynaValide.set("seperationreason", "");
		this.getScreenTitle(request, response, "");
		
		return mapping.findForward("searchFinalSettlementArrear");
	}
	
	// New Method
	
	public ActionForward searchFinalSettlementArrear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String flag = "FSArrearSearch";
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
					 
		}
		if (dynaValide.getString("nssanctionno") != null) {
			searchBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("arreartype") != null) {
			searchBean.setArreartype(dynaValide.getString("arreartype"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchFinalSettlement(
				searchBean, flag);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "FSArrForm");
		return mapping.findForward("finalsettlementarrearsuccess");
	}
	
	// New Method
	//By Radha on 23-Dec-2011 for Restricting multiple submissions
	public ActionForward loadFinalSettlementArrear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlementArrear");
		ArrayList yearList=new ArrayList();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		request.setAttribute("focusFlag", "false");
		dynaValide.set("pfid", "");
		dynaValide.set("cpfaccno", "");
		dynaValide.set("fhName", "");
		dynaValide.set("employeeNo", "");
		dynaValide.set("dateOfJoining", "");
		dynaValide.set("employeeName", "");
		dynaValide.set("seperationreason", "");
		dynaValide.set("designation", "");
		dynaValide.set("region", "");
		dynaValide.set("dateOfBirth", "");
		dynaValide.set("seperationdate", "");
		dynaValide.set("pensionNo", "");
		dynaValide.set("station", "");
		
		
		yearList=commonUtil.getYearList();
		
		log.info("yearList=========="+yearList.size());
		request.setAttribute("yearList", yearList);
		this.getScreenTitle(request,response,"");
		saveToken(request);
		return mapping.findForward("loadFinalSettlementArrear");
	}
	
	 
	//By Radha on 26-Dec-2011 for Restricting multiple submissions 
	//Changes done by Radha P on 14-Apr-2011
	public ActionForward saveFinalSettlementArrear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		String messages="",frmName="";
		session = request.getSession(true);
		
		advanceBasicBean = this.fillDynaFormToNoteSheetBean(form);
		
		log.info("====isTokenValid(request)=="+isTokenValid(request));
		if (isTokenValid(request)) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName = "";
		}
		 
		
		if (dynaValide.getString("arreartype") != null) {
			advanceBasicBean.setArreartype(dynaValide.getString("arreartype"));
		}else{
			advanceBasicBean.setArreartype("");
		}
		
		if (dynaValide.getString("arreardate") != null) {
			advanceBasicBean.setArreardate(dynaValide.getString("arreardate"));
		}else{
			advanceBasicBean.setArreardate("");
		}
		
		if((advanceBasicBean.getArreartype().equals("revisedinterestrate"))||
				(advanceBasicBean.getArreartype().equals("dearnesspay")))
		{
			
			if(request.getParameter("frm_fromyear")!=null){
				advanceBasicBean.setFromfinyear("01-Apr-"+request.getParameter("frm_fromyear"));
			}else{
				advanceBasicBean.setFromfinyear("");
			}
			
			
			if(request.getParameter("frm_toyear")!=null){
				advanceBasicBean.setTofinyear("31-Mar-"+request.getParameter("frm_toyear"));
			}else{
				advanceBasicBean.setTofinyear("");
			}
			
		}
		
		if (dynaValide.getString("interestratefrom") != null) {
			advanceBasicBean.setInterestratefrom(dynaValide.getString("interestratefrom"));
		}else{
			advanceBasicBean.setInterestratefrom("");
		}
		
		if (dynaValide.getString("interestrateto") != null) {
			advanceBasicBean.setInterestrateto(dynaValide.getString("interestrateto"));
		}else{
			advanceBasicBean.setInterestrateto("");
		}
		
		if (dynaValide.getString("paymentinfo") != null) {
			if (dynaValide.getString("paymentinfo").equals("")||dynaValide.getString("paymentinfo").equals("N")) {
				advanceBasicBean.setPaymentinfo("N");
			} else {
				advanceBasicBean.setPaymentinfo("Y");
			}
		}
		
		if (request.getParameter("frm_region") != null) {
			advanceBasicBean.setRegion(request.getParameter("frm_region"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();		
		employeeBankBean = this.fillDynaFormBankToBean(form);		
		if(!frmName.equals("")){
		messages = ptwAdvanceService.addFinalSettlementArrearInfo(advanceBasicBean,employeeBankBean);
		}
		log.info("-----message in ACTION-----" + messages);
		AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
		request.setAttribute("advanceBean", advanceBasic);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		
		saveMessages(request, actionMessage);
		this.getScreenTitle(request,response,"");
		return mapping.findForward("finalsettlementarrearsuccess");
	}
//	By Radha On 28-Jul-2011 for show differences in Old & New Formats for Death Reports
	public ActionForward finalSettlementArrearReport(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceBasicReportBean advanceReportBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean advanceBasicReportBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean advReportBasicBean = new AdvanceBasicReportBean();
		AdvanceBasicReportBean otherDetBean = new AdvanceBasicReportBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		AdvanceBasicReportBean LODBean = null;
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList list = new ArrayList();
		ArrayList LODList = new ArrayList();
		ArrayList postingDetList = new ArrayList();
		ArrayList transList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();
		String path = "", frmName = "",currentYear = "", currentYear1 = "", previousYear2 = "", previousYear1 = "";;
		int currentMonth = 0, nextYear = 0, previousYear = 0;
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", nsSanctionNo = "";
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmSanctionNo") != null) {
			nsSanctionNo = request.getParameter("frmSanctionNo");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		
		try {
			log.info("advanceReport::pensionNo" + pensionNo + "sanctionNo"
					+ nsSanctionNo);
			list = ptwAdvanceService.finalSettlementVerificationApproval(
					pensionNo, nsSanctionNo, frmName);
			log.info("Size" + list.size());
			
			if (list.size() != 0) {
				advanceReportBean = (AdvanceBasicReportBean) list.get(0);				 
				postingDetList = (ArrayList) list.get(2);
				advReportBasicBean = (AdvanceBasicReportBean) list.get(3);
				otherDetBean = (AdvanceBasicReportBean) list.get(4);
				
				request.setAttribute("Form2ReportBean", advanceReportBean);			 
				request.setAttribute("otherDetBean", otherDetBean);
				
				if (postingDetList.size() != 0) {
					request.setAttribute("postingDetList", postingDetList);
				}
				
				request.setAttribute("empNoteSheetPersonalInfoBean",
						advReportBasicBean);
			}
			if (advanceReportBean.getSeperationreason().equals("Death")) {
				 
				log.info("----------Multiple Nominee list size in action---------"
						+ advanceReportBean.getMultipleNomineeList().size());
				 if(advanceReportBean.getMultipleNomineeList() != null) {
						if (advanceReportBean.getMultipleNomineeList().size() > 0) {
							multipleNomineeList = (ArrayList) advanceReportBean
							.getMultipleNomineeList();
							request.setAttribute("multipleNomineeList", multipleNomineeList);
						}
					}
				
			}
			
			if (!advanceReportBean.getLodInfo().equals("")) {
				String lod = advanceReportBean.getLodInfo();
				String[] lods = lod.split(",");
				for (int i = 0; i < lods.length; i++) {
					LODBean = new AdvanceBasicReportBean();
					LODBean.setLodInfo(lods[i]);
					LODList.add(LODBean);
				}
				request.setAttribute("LODList", LODList);
			}
			this.getScreenTitle(request, response, "");
			
			log.info("------Seperation Reason------"+advanceReportBean.getSeperationreason());
			
			
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,nsSanctionNo);
			
			if(advanceReportBean.getPaymentinfo().equals("Y")){
			request.setAttribute("bankMasterBean", bankMasterBean);
			}
			
			currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
			currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
			currentYear1 = commonUtil.getCurrentDate("yyyy").substring(0, 4);
			
			nextYear = Integer.parseInt(currentYear) + 1;
			previousYear = Integer.parseInt(currentYear1) - 1;
			previousYear2 = Integer.toString(previousYear);
			
			if (previousYear <= 9)
				previousYear1 = Integer.toString(previousYear);
			
			if (currentMonth >= 04)
				advanceReportBean.setTransMnthYear(currentYear1 + "-"
						+ nextYear);
			else if (previousYear <= 9)
				advanceReportBean.setTransMnthYear(previousYear1 + "-"
						+ currentYear);
			else
				advanceReportBean.setTransMnthYear(previousYear2 + "-"
						+ currentYear);
			
			this.getScreenTitle(request, response, "");
			
			log.info("frmName============================="+frmName);
			if(!frmName.equals("")){
				this.getLoginDetails(request);
				CPFPFWTransBean transBean=new CPFPFWTransBean();
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				if(frmName.equals("FSArrearProcess")){
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_PROCESS_ARREAR_FORM,
							imgagePath);
					
				}else if(frmName.equals("FSArrearRecommendation")){
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR,
							imgagePath);
					
					
				}else if(frmName.equals("FSArrearVerification")){
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_DGM,
							imgagePath);
					
				}else if(frmName.equals("FSArrearApproval")){
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL,
							imgagePath);
					
				}else{  
					transList = (ArrayList) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL,
							imgagePath);
					
				}
				log.info("transList size" + transList.size());
				
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);					
					transBean=(CPFPFWTransBean)transList.get(0);
					log.info("Approved Date in Action==========="+transBean.getTransApprovedDate());
					request.setAttribute("transBean", transBean);
				}
			}
			
			if (advanceReportBean.getSeperationreason().equals("Death")) {					 
					if(advanceReportBean.getSanctionOrderFlag().equals("After")){
						path = "nsarreardeathreport";
						}else{
						path = "nsarreardeathreportOldFormat";
						}
				}else{
					path = "loadFSArrearReport";
				}
			 
			
			
			
			
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("loadFSAppReport");
		}
		
		log.info("=======Path=======" + path);
		
		return mapping.findForward(path);
		
	}
	
	// New Method
	
	public ActionForward loadFinalSettlementArrearProcessSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadFinalSettlementArrearProcessSearch");
		
		this.getScreenTitle(request, response, "");
		return mapping.findForward("searchFinalSettltementArrearProcess");
	}
	
	// New Method
	
	public ActionForward searchFinalSettlementArrearProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String frmName = "";
		 
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		 
		if (dynaValide.getString("nssanctionno") != null) {
			searchBean.setNssanctionno(dynaValide.getString("nssanctionno"));
		}
		
		if (dynaValide.getString("sanctiondt") != null) {
			searchBean.setSanctiondt(dynaValide.getString("sanctiondt"));
		}
		
		if (dynaValide.getString("paymentdt") != null) {
			searchBean.setPaymentdt(dynaValide.getString("paymentdt"));
		}
		
		if (dynaValide.getString("seperationreason") != null) {
			searchBean.setSeperationreason(dynaValide
					.getString("seperationreason"));
		}
		
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		
		if (dynaValide.getString("trust") != null) {
			searchBean.setTrust(dynaValide.getString("trust"));
		}
		
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		 
		if (request.getParameter("arreartype") != null) {			
			searchBean.setArreartype(request.getParameter("arreartype"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchNoteSheet(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		return mapping.findForward("processsearchsuccess");
		
	}
//	On 06-May-2011 By Radha for Getting  Bank Details
	public ActionForward editNoteSheetArrear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean adBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		AdvanceBasicBean transInfoBean = new AdvanceBasicBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		String currentDate = "", userName = "", computerName = "", messages = "", path = "", selectedEmpName = "", selectedRegion = "", pensionNo = "", nsSanctionNo = "", cpfaccno = "", frmName = "";
		String transRemarks="",prevTransRemarks="";
		
		boolean flagval = false;
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
	 
		
		userName = userid;
		computerName = userIPaddress;
		EmpBankMaster bankMaster = new EmpBankMaster();
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		String pfwAdvanceType = "", pfwPurposeType = "", advPurpose = "";
		StringBuffer nomineeRow = new StringBuffer();
		saveToken(request);
		
		log.info("=======editNoteSheetArrear=======");
		
		if (request.getParameter("pensionno") != null) {
			pensionNo = request.getParameter("pensionno");
		}
		if (request.getParameter("sanctionno") != null) {
			nsSanctionNo = request.getParameter("sanctionno");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		currentDate = "-Feb-2009";
		personalList = commonService.loadPersonalTransInfo(selectedEmpName,
				selectedRegion, pensionNo, cpfaccno, "-Sep-2007");
		advanceBean = (AdvanceBasicBean) personalList.get(0);
		advanceBean.setDateOfMembership(advanceBean.getDateOfJoining());
		request.setAttribute("personalInfo", advanceBean);
		ArrayList airportsList = new ArrayList();
		ArrayList yearList = new ArrayList();
		
		yearList=commonUtil.getYearList();
		
		log.info("yearList=========="+yearList.size());
		request.setAttribute("yearList", yearList);
		
		if (!advanceBean.getRegion().equals("")) {
			log.info("-------Region in Action--------"
					+ advanceBean.getRegion());
			log.info("-------st in Action--------" + advanceBean.getStation());
			
			airportsList = commonUtil.getAirportList(advanceBean.getRegion());
			
			log
			.info("---------airportsList Size--------"
					+ airportsList.size());
			if (airportsList.size() > 0) {
				for (int j = 0; j < airportsList.size(); j++) {
					
					adBean = (AdvanceBasicBean) airportsList.get(j);
					log.info("-----Station in Action------"
							+ adBean.getStation());
				}
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		noteSheetBean = commonService.loadNoteSheetInfo(pensionNo, nsSanctionNo);
		//request.setAttribute("noteSheetInfo", noteSheetBean);
		
		//		 added  to get Bank Details
		bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,nsSanctionNo);
		request.setAttribute("bankMasterBean", bankMasterBean);
		
		// request.setAttribute("personalInfo",advanceBean);
		
		nomineeList = commonService.loadNomineeInfo(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		if (nomineeList.size() != 0) {
			adBean = (AdvanceBasicBean) nomineeList.get(0);
			request.setAttribute("nomineeList", nomineeList);
		}
		
		
		String nomineeInfo = commonService.loadNomineeDetails(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		 
		if(nomineeInfo.length()>0){
			log.info("nominee informatio in advances action ---- "+nomineeInfo);			
			request.setAttribute("nomineeInfo", nomineeInfo.substring(0,nomineeInfo.length()-1));
		}
		
		
		dynaValide.set("remarks", "");
		dynaValide.set("soremarks", "");
		dynaValide.set("nomineename", "");
		dynaValide.set("nomineeaddress", "");
		dynaValide.set("nomineeDOB", "");
		dynaValide.set("nomineerelation", "");
		dynaValide.set("gardianname", "");
		dynaValide.set("gardianaddress", "");
		dynaValide.set("totalshare", "");
		
		log.info("----pensionno in editNoteSheetArrear()-----" + pensionNo);
		
		
		
		this.getScreenTitle(request, response, "");
		 
		log.info("frmName============================="+frmName);
		CPFPFWTransBean transBean=new CPFPFWTransBean();
		try{
		if(!frmName.equals("")){
			this.getLoginDetails(request);
			
			
			log.info("loginUserName------" + this.loginUserName);
			
			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
					this.loginUserName, this.loginUnitCode,
					this.loginRegion, this.loginSignName);
			
			if(frmName.equals("FSArrearProcess")){
				
					transBean = (CPFPFWTransBean) cpfInfo
					.readTransInfo(
							pensionNo,
							nsSanctionNo,
							Constants.APPLICATION_PROCESSING_FINAL_PROCESS_ARREAR_FORM);
				
			}else if(frmName.equals("FSArrearRecommendation")){
				transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR);
				
				
			}else if(frmName.equals("FSArrearVerification")){
				
				transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR);
				
				prevTransRemarks=transBean.getRemarks();
				  
				transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_DGM);
				
			}else if(frmName.equals("FSArrearApproval")){
				transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL);
				
			}else{  
				transBean = (CPFPFWTransBean) cpfInfo
				.readTransInfo(
						pensionNo,
						nsSanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL);
				
			}
					
			 
				log.info("Transactional Remarks in Action==========="+transBean.getRemarks());
				    transRemarks=transBean.getRemarks();
			 noteSheetBean.setTransremarks(transRemarks);
			 noteSheetBean.setPrevTransRemarks(prevTransRemarks);
		} 
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			 
			 if((transBean.getRemarks().equals(""))||(transBean.getRemarks().equals("---"))){
				 noteSheetBean.setRemarksFlag("N");
			 }else{
				 noteSheetBean.setRemarksFlag("Y");
			 }
			
			 
		request.setAttribute("noteSheetInfo", noteSheetBean);
		
		
		if (frmName.equals("FSArrearRecommendation")) {
			path = "editNoteSheetArrearRecommendation";
		} else if (frmName.equals("FSArrearVerification")) {
			path = "editNoteSheetArrearVerification";
		} else if (frmName.equals("FSArrearApproval")) {
			path = "editNoteSheetArrearApproval";
		} else {
			path = "editNoteSheetArrear";
		}
		return mapping.findForward(path);
	}
	// New Method
	// 26-Aug-2010 Radha  For adding isTokenValid
	//	 By Radha On 18-Apr-2011 for Bank Details for Death cases
	//	 By Radha On 06-May-2011 for Bank Details in screen level
	public ActionForward updateNoteSheetArrear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBean = new AdvanceBasicBean(request);
		String userName = "", computerName = "", messages = "", pensionNo = "", deleteStatus = "", frmName = "", frmFlag = "";
		String remarksFlag="",frmNomineeDets="";
		boolean flagval = false;
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		EmpBankMaster bankMaster = new EmpBankMaster();	
		AdvanceSearchBean  employeeInfo = new AdvanceSearchBean();
		session = request.getSession(true);
		this.loadPrimarilyInfo(request,"");
		userName = userid;
		computerName = userIPaddress;
		 
		
		log.info("updateNoteSheet==============="+userName+"computerName==========="+computerName);
		log.info("AdvancesAction::updateNoteSheetArrear::isTokenValid -------"+isTokenValid(request)+"frm_name"+request.getParameter("frm_name"));
		
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
				log.info("frmName--------"+frmName);
			}
			resetToken(request);
		}else{
			frmName="";
		}
		 
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		StringBuffer nomineeRow = new StringBuffer();
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		advanceBean = this.fillDynaFormToBean(form, request);
		
		if (request.getParameter("frm_nominees") != null) {
			frmNomineeDets = request.getParameter("frm_nominees");
		}
		
		log.info("frmNomineeDets ----------"+frmNomineeDets);
		
		if (!frmNomineeDets.equals("")) {
			advanceBean.setNomineeRow(frmNomineeDets);
		} else {
			advanceBean.setNomineeRow("");
		}
		 
		if (dynaValide.getString("equalshare").equals("on")) {
			advanceBean.setEqualshare("Y");
		}else{
			advanceBean.setEqualshare("N");
		} 
		log.info("--Equalshare------"+advanceBean.getEqualshare()+"--"+dynaValide.getString("equalshare").equals("on"));
		
		
		if (dynaValide.getString("transremarks") != null) {
			advanceBean.setTransremarks(dynaValide.getString("transremarks"));
		}else{
			advanceBean.setTransremarks("");
		}
		if (!dynaValide.getString("arreartype").equals("")) {
			advanceBean.setArreartype(dynaValide.getString("arreartype"));
		} else {
			advanceBean.setArreartype("");
		}
		
		
		if (!dynaValide.getString("arreardate").equals("")) {
			advanceBean.setArreardate(dynaValide.getString("arreardate"));
		} else {
			advanceBean.setArreardate("");
		}
		
		
		if (!dynaValide.getString("fromfinyear").equals("")) {
			advanceBean.setFromfinyear("01-Apr-"+dynaValide.getString("fromfinyear"));
		} else {
			advanceBean.setFromfinyear("");
		}
		
		if (!dynaValide.getString("tofinyear").equals("")) {
			advanceBean.setTofinyear("31-Mar-"+dynaValide.getString("tofinyear"));
		} else {
			advanceBean.setTofinyear("");
		}
		
		
		if (!dynaValide.getString("interestratefrom").equals("")) {
			advanceBean.setInterestratefrom(dynaValide.getString("interestratefrom"));
		} else {
			advanceBean.setInterestratefrom("");
		}
		
		if (!dynaValide.getString("interestrateto").equals("")) {
			advanceBean.setInterestrateto(dynaValide.getString("interestrateto"));
		} else {
			advanceBean.setInterestrateto("");
		}
		
		 
		if (!dynaValide.getString("adhocamt").equals("")) {
			advanceBean.setAdhocamt(dynaValide.getString("adhocamt"));
		} else {
			advanceBean.setAdhocamt("0.0");
		}
		
		 
		
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		}
		
		
		if (dynaValide.getString("remarksFlag")!=null){		 
			remarksFlag = dynaValide.getString("remarksFlag");
		}
		
		advanceBean.setRemarksFlag(remarksFlag);
		
		log.info("-----------------IN ACTION  updateNoteSheetArrear REMARKS FLAG----------"+remarksFlag);
		
		if (dynaValide.getString("paymentinfo")!=null){		 
			advanceBean.setPaymentinfo(dynaValide.getString("paymentinfo"));
		}else{
		advanceBean.setPaymentinfo("N");
		}
		
		if (request.getParameter("bankflag") != null) {
			advanceBean.setUpdateBankFlag(request.getParameter("bankflag"));
		}else{
			advanceBean.setUpdateBankFlag("N");
		}
		log.info("-----------------IN ACTION  updateNoteSheetArrear paymentinfo FLAG----------"+	advanceBean.getPaymentinfo());
		
		 
		if (dynaValide.getString("bankempname") != null) {
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		 
		if (dynaValide.getString("bankname") != null) {
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}
		 
		if (dynaValide.getString("banksavingaccno") != null) {
			bankMaster.setBanksavingaccno(dynaValide
					.getString("banksavingaccno"));
		}
		 
		if (dynaValide.getString("bankemprtgsneftcode") != null) {
			bankMaster.setBankemprtgsneftcode(dynaValide
					.getString("bankemprtgsneftcode"));
		}
		
		String saveNcontinue="N";
		if(request.getParameter("continueFlag")!=null){
			saveNcontinue =request.getParameter("continueFlag");
		}
		
		log.info("----saveNcontinue----"+saveNcontinue);
		
		
		if(!frmName.equals("")){
		String message = ptwAdvanceService.updateNoteSheetArrear(advanceBean,
				frmName, frmFlag);
		if(saveNcontinue.equals("N")){
			 String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
			 }
		}
		// return mapping.findForward("success");
		 
		 
		if((advanceBean.getSeperationreason().equals("Death"))&&(saveNcontinue.equals("Y"))){
			log.info("--in updateNoteShett-------");
			ArrayList nomineeList=null; 
			ArrayList nomineeSearchList = null;
			String nomineeName = "",nomineeSerialNo="",srcFrmName=""; 
			Map map = new LinkedHashMap();
			 
		if(frmName.equals("")){
			if (request.getParameter("srcFrmName") != null) {
				srcFrmName = request.getParameter("srcFrmName");
			}
		}else{
			srcFrmName=frmName;
		}
			
		log.info("----frmName--"+frmName+"--srcFrmName---"+srcFrmName);
			log.info("--in loadNominees:: pensionNo--"+advanceBean.getPensionNo()+"-nssanctionno--"+advanceBean.getNssanctionno());
			nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			 
			 
			for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
				AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
				  
				nomineeName=bean.getNomineename();
				nomineeSerialNo=bean.getSerialNo();
				//log.info("- in updateNoteSheet()--nomineeSerialNo---"+nomineeSerialNo+"--nomineeName----"+nomineeName);
				map.put(nomineeSerialNo,nomineeName);
			}
			
			nomineeSearchList = ptwAdvanceService.nomineeBankInfo(advanceBean.getPensionNo(),advanceBean.getNssanctionno());
			for(int i=0;i<nomineeSearchList.size();i++){
				bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
				//log.info("----"+bankMasterBean.getNomineeSerialNo()+"--"+bankMasterBean.getBankempname()+"--"+bankMasterBean.getBanksavingaccno());
			}

			employeeInfo.setEmployeeName(advanceBean.getEmployeeName());
			employeeInfo.setSeperationreason(advanceBean.getSeperationreason());

			
			request.setAttribute("nomineeSearchList", nomineeSearchList);
			
			 dynaValide.set("bankempname","");
			 dynaValide.set("bankname","");
			 dynaValide.set("banksavingaccno","");
			 dynaValide.set("bankemprtgsneftcode","");
			 
			  request.setAttribute("nomineeHashmap", map);
			  request.setAttribute("srcFrmName",srcFrmName);
			  request.setAttribute("seperateScreen","N");
			  request.setAttribute("arrearType","ARREAR");
			  request.setAttribute("employeeInfo", employeeInfo);
			this.getScreenTitle(request, response, "BankDet");
			saveToken(request);
			String path="addnomineebankdetails";
			return mapping.findForward(path);
		}else{
		 
		

			ActionForward forward = mapping.findForward("processsearchsuccess");
			ForwardParameters fwdParams = new ForwardParameters();
			
			Map params = new HashMap();
		params.put("method", "searchFinalSettlementArrearProcess");
		// params.put("frm_name", "advances");
		// fwdParams.add(params);
		this.getScreenTitle(request, response, "");
		if (frmName.equals("FSArrearProcess"))
			fwdParams.add("frm_name", "FSArrearProcess");
		else if (frmName.equals("FSArrearRecommendation"))
			fwdParams.add("frm_name", "FSArrearRecommendation");
		else if (frmName.equals("FSArrearVerification"))
			fwdParams.add("frm_name", "FSArrearVerification");
		
		return fwdParams.forward(forward);
		}
		
	}
//	changes done by radhap on 03-May-2011 to show differences to old n new format of sanction order VRS Case
//	changes done by radhap on 04-May-2011 to show differences to old n new format of sanction orderfor all  Cases
//	changes done by radhap on 13-Jun-2011 to show differences to old n new format of sanction for arrears
	
	public ActionForward sanctionOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ArrayList sanctionOrderList = new ArrayList();
		ptwAdvanceService = new CPFPTWAdvanceService();
		String pensionNo = "", nssanctionNo = "", path = "", currentYear = "", currentYear1 = "", previousYear2 = "", previousYear1 = "", frmFlag = "", frmSanctionDate = "",frmName="";
		int previousYear = 0, currentMonth = 0, nextYear = 0;
		ArrayList nomineeList = new ArrayList();
		ArrayList multipleNomineeList = new ArrayList();		
		ArrayList transList = new ArrayList();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceBasicReportBean reportBean = new AdvanceBasicReportBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmSanctionNo") != null) {
			nssanctionNo = request.getParameter("frmSanctionNo");
		}
		if (request.getParameter("frmFlag") != null) {
			frmFlag = request.getParameter("frmFlag");
		}
		if (request.getParameter("frmSanctionDate") != null) {
			frmSanctionDate = request.getParameter("frmSanctionDate");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		try {
			
			log.info("advanceReport::pensionNo" + pensionNo + "nssanctionNo"
					+ nssanctionNo);
			 
			sanctionOrderList = ptwAdvanceService.sanctionOrder(pensionNo,
					nssanctionNo, frmFlag, frmSanctionDate);
			log.info("Size" + sanctionOrderList.size());
			if (sanctionOrderList.size() == 1) {
				reportBean = (AdvanceBasicReportBean) sanctionOrderList.get(0);
				log
				.info("Seperation Reason"
						+ reportBean.getSeperationreason());
				dynaValide.set("seperationreason", reportBean
						.getSeperationreason());
				dynaValide.set("remarks", reportBean
						.getRemarks());
				if (reportBean.getSeperationreason().equals("Death")) {
					log.info("----------Nominee list size in action---------"
							+ reportBean.getNomineeList().size());
					if (reportBean.getNomineeList() != null) {
						if (reportBean.getNomineeList().size() > 0) {
							nomineeList = (ArrayList) reportBean
							.getNomineeList();
							request.setAttribute("nomineeList", nomineeList);
						}
					}
					log.info("----------Multiple Nominee list size in action---------"
							+ reportBean.getMultipleNomineeList().size());
					 if(reportBean.getMultipleNomineeList() != null) {
							if (reportBean.getMultipleNomineeList().size() > 0) {
								multipleNomineeList = (ArrayList) reportBean
								.getMultipleNomineeList();
								request.setAttribute("multipleNomineeList", multipleNomineeList);
							}
						}
					
				}
			}
			
			
			 
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,nssanctionNo);
			
			
			 log.info("--Paymentinfo------"+reportBean.getPaymentinfo()+"::::::::::::::::"+reportBean.getFinyear());
			
			if(reportBean.getPaymentinfo().equals("Y")){
			request.setAttribute("bankMasterBean", bankMasterBean);
			}
			
			currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
			currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
			currentYear1 = commonUtil.getCurrentDate("yyyy").substring(0, 4);
			AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
			nextYear = Integer.parseInt(currentYear) + 1;
			previousYear = Integer.parseInt(currentYear1) - 1;
			previousYear2 = Integer.toString(previousYear);
			
			if (previousYear <= 9)
				previousYear1 = Integer.toString(previousYear);
			
			if (currentMonth >= 04)
				advanceReportBean.setFinyear(currentYear1 + "-" + nextYear);
			else if (previousYear <= 9)
				advanceReportBean.setFinyear(previousYear1 + "-" + currentYear);
			else
				advanceReportBean.setFinyear(previousYear2 + "-" + currentYear);
			
			advanceReportBean.setFinyear(reportBean.getFinyear());
			
			request.setAttribute("reportBean", reportBean);
			request.setAttribute("advanceReportBean", advanceReportBean);
			request.setAttribute("sanctionOrderList", sanctionOrderList);
			
			if(reportBean.getSanctionOrderFlag().equals("After")){
				if (reportBean.getRegion().equals("CHQNAD")
						&& reportBean.getSeperationreason().equals("Retirement")) {
					path = "viewRetirementSanctionOrder";
				} else if (reportBean.getRegion().equals("CHQNAD")
						&& reportBean.getSeperationreason().equals("Death")) {
					path = "viewDeathSanctionOrder";
				}else{
					path = "viewSanctionOrder";
				}
				 
				}else{
					if (reportBean.getRegion().equals("CHQNAD")
							&& reportBean.getSeperationreason().equals("Retirement")) {
						path = "viewRetirementSanctionOrderOldFormat";
					} else if (reportBean.getRegion().equals("CHQNAD")
							&& reportBean.getSeperationreason().equals("Death")) {
						path = "viewDeathSanctionOrderOldFormat";
					}else{
						path = "viewSanctionOrderOldFormat";
					} 
				}
				 
				this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				
				transList = (ArrayList) cpfInfo
				.readTransInfo(
						pensionNo,
						nssanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR,
						imgagePath);
				
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
				}
				
			   
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			
			if(reportBean.getSanctionOrderFlag().equals("After")){
				if (reportBean.getRegion().equals("CHQNAD")
						&& reportBean.getSeperationreason().equals("Retirement")) {
					path = "viewRetirementSanctionOrder";
				} else if (reportBean.getRegion().equals("CHQNAD")
						&& reportBean.getSeperationreason().equals("Death")) {
					path = "viewDeathSanctionOrder";
				}else{
					path = "viewSanctionOrder";
				}
				 
				}else{
					if (reportBean.getRegion().equals("CHQNAD")
							&& reportBean.getSeperationreason().equals("Retirement")) {
						path = "viewRetirementSanctionOrderOldFormat";
					} else if (reportBean.getRegion().equals("CHQNAD")
							&& reportBean.getSeperationreason().equals("Death")) {
						path = "viewDeathSanctionOrderOldFormat";
					}else{
						path = "viewSanctionOrderOldFormat";
					} 
				}
				 
		}
		
		if(frmName.equals("FSArrearRecommendation")){
			this.getLoginDetails(request);
			CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
					this.loginUserName, this.loginUnitCode,
					this.loginRegion, this.loginSignName);
			String imgagePath = getServlet().getServletContext()
			.getRealPath("/")
			+ "/uploads/dbf/";
			try {
				transList = (ArrayList) cpfInfo
				.readTransInfo(
						pensionNo,
						nssanctionNo,
						Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR,
						imgagePath);
			} catch (EPISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			log.info("transList size" + transList.size());
			
			if (transList.size() > 0) {
				request.setAttribute("transList", transList);
			}
			
			if(reportBean.getSanctionOrderFlag().equals("After")){
				path="viewArrearSanctionOrder";
				}else{
					path= "viewArrearSanctionOrderOldFormat";
				}
			 
		}
		log.info("----path--------"+path);
		return mapping.findForward(path);
	}

	public ActionForward editFinalSettlementArrear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {   	
		AdvanceBasicBean advanceBean = new AdvanceBasicBean();
		AdvanceBasicBean adBean = new AdvanceBasicBean();
		AdvanceBasicBean noteSheetBean = new AdvanceBasicBean();
		AdvanceBasicReportBean otherDetBean = new AdvanceBasicReportBean();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		
		String currentDate = "", userName = "", computerName = "", messages = "", path = "", selectedEmpName = "", selectedRegion = "", pensionNo = "", sanctionNo = "", cpfaccno = "", frmName = "";
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ActionForward forward=null;
		
		session = request.getSession(true);
		userName = userid;
		computerName = userIPaddress;
		
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmSanctionNo") != null) {
			sanctionNo = request.getParameter("frmSanctionNo");
		}
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		currentDate = "-Feb-2009";
		personalList = commonService.loadPersonalTransInfo(selectedEmpName,
				selectedRegion, pensionNo, cpfaccno, "-Sep-2007");
		advanceBean = (AdvanceBasicBean) personalList.get(0);
		advanceBean.setDateOfMembership(advanceBean.getDateOfJoining());
		request.setAttribute("personalInfo", advanceBean);
		ArrayList airportsList = new ArrayList();
		
		
		ArrayList yearList = new ArrayList();
		
		yearList=commonUtil.getYearList();
		
		log.info("yearList=========="+yearList.size());
		request.setAttribute("yearList", yearList);
		
		
		noteSheetBean = commonService.loadNoteSheetInfo(pensionNo, sanctionNo);
		
		if (!noteSheetBean.getRegion().equals("")) {
			airportsList = commonUtil.getAirportList(noteSheetBean.getRegion());			
			
			if (airportsList.size() > 0) {
				for (int j = 0; j < airportsList.size(); j++) {
					adBean = (AdvanceBasicBean) airportsList.get(j);					
				}
				request.setAttribute("airportsList", airportsList);
			}
		}
		
		if(noteSheetBean.getArreartype().equals("dearnesspay"))
		{
			noteSheetBean.setDpfromfinyear(noteSheetBean.getFromfinyear());
			
			noteSheetBean.setDptofinyear((noteSheetBean.getTofinyear()));
			
		}
		
		
		request.setAttribute("noteSheetInfo", noteSheetBean);
		
		
		nomineeList = commonService.loadNomineeInfo(selectedEmpName,
				selectedRegion, pensionNo, "-Sep-2007");
		if (nomineeList.size() != 0) {
			adBean = (AdvanceBasicBean) nomineeList.get(0);
			request.setAttribute("nomineeList", nomineeList);
		}
		
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		employeeBankBean = ptwAdvanceService.employeeBankInfo(pensionNo, sanctionNo);
		request.setAttribute("empBankInfo", employeeBankBean);
		
		
		dynaValide.set("dateOfJoining", advanceBean.getDateOfJoining());
		
		
		dynaValide.set("bankempname", employeeBankBean.getBankempname());
		dynaValide.set("banksavingaccno", employeeBankBean.getBanksavingaccno());
		dynaValide.set("bankname", employeeBankBean.getBankname());
		dynaValide.set("branchaddress", employeeBankBean.getBranchaddress());
		dynaValide.set("bankemprtgsneftcode", employeeBankBean.getBankemprtgsneftcode());
		dynaValide.set("bankpaymentmode", employeeBankBean.getBankpaymentmode());
		dynaValide.set("city", employeeBankBean.getCity());
		
		
		path = "editFinalSettlementArrear";
		
		
		
		log.info("---------Forward------------"+forward);
		
		return mapping.findForward(path);
		
		
	}
	
	//12-Aug-2010 Radha for arreardate in FS Arrears
	public ActionForward updateFinalSettlementArrearInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		AdvanceBasicBean advanceBasicBean = new AdvanceBasicBean();
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		String userName = "", computerName = "", messages = "",withDrawalList="",frmName="";
		session = request.getSession(true);
		userName = userid;
		computerName = userIPaddress;
		log.info("updateFinalSettlementArrearInfo::userName" + userName
				+ "computerName" + computerName);
		ptwAdvanceService = new CPFPTWAdvanceService(userName, computerName);
		
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		
		advanceBasicBean = this.fillDynaFormToNoteSheetBean(form);
		
		if (request.getParameter("frm_name") != null) {
			frmName = request.getParameter("frm_name");
		}
		
		if (dynaValide.getString("arreartype") != null) {
			advanceBasicBean.setArreartype(dynaValide.getString("arreartype"));
		}else{
			advanceBasicBean.setArreartype("");
		}
		if(advanceBasicBean.getArreartype().equals("payscalerevision"))
		{
			if (dynaValide.getString("arreardate") != null) {
				advanceBasicBean.setArreardate(dynaValide.getString("arreardate"));
			}else{
				advanceBasicBean.setArreardate("");
			}
		}
		
		
		if((advanceBasicBean.getArreartype().equals("revisedinterestrate"))||
				(advanceBasicBean.getArreartype().equals("dearnesspay")))
		{
			
			if(request.getParameter("frm_fromyear")!=null){
				advanceBasicBean.setFromfinyear("01-Apr-"+request.getParameter("frm_fromyear"));
			}else{
				advanceBasicBean.setFromfinyear("");
			}
			
			
			if(request.getParameter("frm_toyear")!=null){
				advanceBasicBean.setTofinyear("31-Mar-"+request.getParameter("frm_toyear"));
			}else{
				advanceBasicBean.setTofinyear("");
			}
			
		}
		
		if (dynaValide.getString("interestratefrom") != null) {
			advanceBasicBean.setInterestratefrom(dynaValide.getString("interestratefrom"));
		}else{
			advanceBasicBean.setInterestratefrom("");
		}
		
		if (dynaValide.getString("interestrateto") != null) {
			advanceBasicBean.setInterestrateto(dynaValide.getString("interestrateto"));
		}else{
			advanceBasicBean.setInterestrateto("");
		}
		
		if (dynaValide.getString("paymentinfo") != null) {
			if (dynaValide.getString("paymentinfo").equals("")||dynaValide.getString("paymentinfo").equals("N")) {
				advanceBasicBean.setPaymentinfo("N");
			} else {
				advanceBasicBean.setPaymentinfo("Y");
			}
		}
		
		if(request.getParameter("frm_region")!=null){
			advanceBasicBean.setRegion(request.getParameter("frm_region"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();		
		employeeBankBean = this.fillDynaFormBankToBean(form);		
		
		messages = ptwAdvanceService.updateFinalSettlementArrearInfo(advanceBasicBean);
		
		String bankInfo= ptwAdvanceService.updateBankInfo(advanceBasicBean.getPensionNo(),advanceBasicBean.getNssanctionno(),advanceBasicBean.getUpdateBankFlag(), advanceBasicBean.getPaymentinfo(),employeeBankBean);
		
		log.info("-----message in ACTION-----" + messages);
		AdvanceBasicBean advanceBasic = new AdvanceBasicBean();
		request.setAttribute("advanceBean", advanceBasic);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage(
				"advance.success.message", messages));
		
		saveMessages(request, actionMessage);
		this.getScreenTitle(request,response,"");
		
		
		return mapping.findForward("finalsettlementarrearsuccess");
	}
	public ActionForward loadSearchtoDeleteRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("AdvancesAction::loadSearchtoDeleteRecords");
		
		this.getScreenTitle(request, response, "");
		return mapping.findForward("loadsearchtodeleterecords");
	}
	
	//New Method
	public ActionForward searchRecordsToDelete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		AdvanceSearchBean bean = new AdvanceSearchBean();
		 
		
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
					 
		}
		if (dynaValide.getString("sanctionno") != null) {
			searchBean.setSanctionno(dynaValide.getString("sanctionno"));
		}
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean.setAdvanceTransID(dynaValide.getString("advanceTransID"));
		}		 
		
		if (dynaValide.getString("requestType") != null) {
			searchBean.setRequestType(dynaValide.getString("requestType"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		log.info("Type"+searchBean.getRequestType());
		ArrayList searchlist = ptwAdvanceService.searchRecordsToDelete(searchBean);
		request.setAttribute("searchlist", searchlist);
		if (searchlist.size() != 0) {
		bean=(AdvanceSearchBean)searchlist.get(0);
		}
		request.setAttribute("AdvanceSearchBean", bean);
		this.getScreenTitle(request, response, "FSDelete");
		return mapping.findForward("searchrecordssuccess");
	}
	
	//New Method
	public ActionForward deleteRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String frmName="";
		if (request.getParameter("frmPensionNo") != null) {
			searchBean.setPensionNo(request.getParameter("frmPensionNo"));
		}
		if (request.getParameter("frmNSSanctionNo") != null) {
			searchBean.setNssanctionno(request.getParameter("frmNSSanctionNo"));
		}
		 
		if (request.getParameter("frmAdvanceTransID") != null) {
			searchBean.setAdvanceTransID(request.getParameter("frmAdvanceTransID"));
		}	
		if (request.getParameter("frmAdvanceType") != null) {
			searchBean.setAdvanceType(request.getParameter("frmAdvanceType"));
		}
		if (request.getParameter("frmPurposeType") != null) {
			searchBean.setPurposeType(request.getParameter("frmPurposeType"));
		}
		if (request.getParameter("frmFSType") != null) {
			searchBean.setFsType(request.getParameter("frmFSType"));			
			
		if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			 	
			}
		}
		if (request.getParameter("verifiedBy") != null) {
			searchBean.setVerifiedBy(request.getParameter("verifiedBy"));
		}
		
		ptwAdvanceService = new CPFPTWAdvanceService();
		int count = ptwAdvanceService.deleteRecords(searchBean);	
		
		 
		ForwardParameters fwdParams = new ForwardParameters();
		ActionForward forward=mapping.findForward("searchrecordssuccess");
		Map params = new HashMap();		 		 
		params.put("method", "searchRecordsToDelete");
		this.getScreenTitle(request, response, "");				 
		
			fwdParams.add("frm_name", "FSDelete");
		  	
		return fwdParams.forward(forward);
			
	}
	/*public ActionForward loadNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("--in updateNoteShett-------");
		ArrayList nomineeList=null; 
		ArrayList nomineeSearchList = null;
		String nomineeName = "",nomineeSerialNo="",pensionNo="",nsSanctionNo=""; 
		Map map = new LinkedHashMap();
		 
		EmpBankMaster bankMasterBean = new EmpBankMaster();			 
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		if(request.getParameter("pensionno")!=null){
			pensionNo=request.getParameter("pensionno");
			}
		
		if(request.getParameter("nssanctionno")!=null){
			nsSanctionNo=request.getParameter("nssanctionno");
			}
		
		log.info("--in loadNominees:: pensionNo--"+pensionNo +"-nssanctionno--"+nsSanctionNo);
		nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,nsSanctionNo);
		 
		 
		for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
			AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
			  
			nomineeName=bean.getNomineename();
			nomineeSerialNo=bean.getSerialNo();
			//log.info("- in updateNoteSheet()--nomineeSerialNo---"+nomineeSerialNo+"--nomineeName----"+nomineeName);
			map.put(nomineeSerialNo,nomineeName);
		}
		
		nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,nsSanctionNo);
		for(int i=0;i<nomineeSearchList.size();i++){
			bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
			//log.info("----"+bankMasterBean.getNomineeSerialNo()+"--"+bankMasterBean.getBankempname()+"--"+bankMasterBean.getBanksavingaccno());
		}
		
		request.setAttribute("nomineeSearchList", nomineeSearchList);
		
		request.setAttribute("seperateScreen","Y");
		dynaValide.set("pensionNo",pensionNo);
		dynaValide.set("nssanctionno",nsSanctionNo);
		
		log.info("-----"+dynaValide.getString("pensionNo")+"--"+dynaValide.getString("nssanctionno"));
		
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankname","");
		 dynaValide.set("banksavingaccno","");
		 dynaValide.set("bankemprtgsneftcode","");
		 
		  request.setAttribute("nomineeHashmap", map);
		this.getScreenTitle(request, response, "BankDet");
		saveToken(request);
		String path="loadnomineebankdetails";
		return mapping.findForward(path);
	
	}*/
	public ActionForward loadNomineeBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
  
		ArrayList nomineeList=null; 
		ArrayList nomineeSearchList = null;
		String nomineeName = "",nomineeSerialNo="",pensionNo="",transid="", srcFrmName="",arrearType="",advanceType="",seperationreason="",empName=""; 
		Map map = new LinkedHashMap();
		
		EmpBankMaster bankMasterBean = new EmpBankMaster();		
		EmpBankMaster employeeBankBean = new EmpBankMaster();
		AdvanceBasicBean  employeeInfo = new AdvanceBasicBean();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		
		
		if(request.getParameter("srcFrmName")!=null){
			srcFrmName=request.getParameter("srcFrmName");
			}
		if(request.getParameter("pensionno")!=null){
			pensionNo=request.getParameter("pensionno");
			employeeInfo.setPensionNo(pensionNo);
			}
		
		if(request.getParameter("transid")!=null){
			transid=request.getParameter("transid");
		}
		if(srcFrmName.equals("FSArrearRecommendation") || srcFrmName.equals("FSFormIII")){ 
			employeeInfo.setNssanctionno(transid); 
		if(request.getParameter("frm_ArrearType")!=null){
			arrearType=request.getParameter("frm_ArrearType"); 
			}
		}else{ 
				employeeInfo.setAdvanceTransID(transid); 
			if(request.getParameter("frm_AdvanceType")!=null){
				advanceType=request.getParameter("frm_AdvanceType");
			
				}
		}
		if(request.getParameter("empName")!=null){
			empName=request.getParameter("empName");
			employeeInfo.setEmployeeName(empName);
			}
		
		if(request.getParameter("seperationreason")!=null){
			seperationreason=request.getParameter("seperationreason");
			employeeInfo.setSeperationreason(seperationreason);
			}
		
		log.info("--in loadNominees:: pensionNo--"+pensionNo +"-nssanctionno--"+transid);
		
		if(seperationreason.equals("Death")){
		nomineeList =(ArrayList) commonService.loadDeathCaseNomineeInfo(pensionNo,transid);
		 
		 
		for (int i = 0; nomineeList != null && i < nomineeList.size(); i++) {				
			AdvanceBasicBean bean = (AdvanceBasicBean) nomineeList.get(i);
			  
			nomineeName=bean.getNomineename();
			nomineeSerialNo=bean.getSerialNo();
			//log.info("- in updateNoteSheet()--nomineeSerialNo---"+nomineeSerialNo+"--nomineeName----"+nomineeName);
			map.put(nomineeSerialNo,nomineeName);
		}
		
		nomineeSearchList = ptwAdvanceService.nomineeBankInfo(pensionNo,transid);
		for(int i=0;i<nomineeSearchList.size();i++){
			bankMasterBean =(EmpBankMaster)nomineeSearchList.get(i);
			//log.info("----"+bankMasterBean.getNomineeSerialNo()+"--"+bankMasterBean.getBankempname()+"--"+bankMasterBean.getBanksavingaccno());
		}
		
		request.setAttribute("nomineeSearchList", nomineeSearchList);
		}
		
		employeeBankBean = ptwAdvanceService.employeeBankInfo(pensionNo, transid);		 
		request.setAttribute("empBankInfo", employeeBankBean);
		request.setAttribute("employeeInfo", employeeInfo);		
		request.setAttribute("srcFrmName",srcFrmName);
		request.setAttribute("seperateScreen","Y");
		
		
		dynaValide.set("pensionNo",pensionNo);
		dynaValide.set("employeeName",empName);
		dynaValide.set("seperationreason",seperationreason);
		
		if(srcFrmName.equals("FSArrearRecommendation") || srcFrmName.equals("FSFormIII")){
		dynaValide.set("nssanctionno",transid);
		request.setAttribute("arrearType",arrearType);
		request.setAttribute("advanceType","");
		}else{
			dynaValide.set("advanceTransID",transid);
			request.setAttribute("advanceType",advanceType);
			request.setAttribute("arrearType","");
		}
		
		log.info("---Name-----"+employeeInfo.getEmployeeName()+"---Reason---"+employeeInfo.getSeperationreason()+"==srcFrmName="+srcFrmName);
		
		log.info("-----"+dynaValide.getString("pensionNo")+"--"+dynaValide.getString("nssanctionno")+"-----arrearType------"+arrearType);
		
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankempname","");
		 dynaValide.set("bankname","");
		 dynaValide.set("banksavingaccno","");
		 dynaValide.set("bankemprtgsneftcode","");
		 
		  request.setAttribute("nomineeHashmap", map);
		this.getScreenTitle(request, response, "BankDet");
		saveToken(request);
		String path="loadnomineebankdetails";
		return mapping.findForward(path);
	
	}
	public ActionForward loadEmployeePostingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
 
		ArrayList nomineeList=null; 
		ArrayList nomineeSearchList = null;
		String nomineeName = "",nomineeSerialNo="",pensionNo="",nsSanctionNo="",srcFrmName="",arrearType=""; 
		Map map = new LinkedHashMap();
		 
		EmpBankMaster bankMasterBean = new EmpBankMaster();			 
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		if(request.getParameter("frm_ArrearType")!=null){
			arrearType=request.getParameter("frm_ArrearType");
			}
		
		if(request.getParameter("srcFrmName")!=null){
			srcFrmName=request.getParameter("srcFrmName");
			}
		if(request.getParameter("pensionno")!=null){
			pensionNo=request.getParameter("pensionno");
			}
		
		if(request.getParameter("nssanctionno")!=null){
			nsSanctionNo=request.getParameter("nssanctionno");
			}
		AdvanceBasicBean  basicBean = new AdvanceBasicBean();
		log.info("--in loadNominees:: pensionNo--"+pensionNo +"-nssanctionno--"+nsSanctionNo);
		basicBean =(AdvanceBasicBean) commonService.loadNoteSheetInfo(pensionNo,nsSanctionNo);
		 
		dynaValide.set("pensionNo",pensionNo);
		dynaValide.set("nssanctionno",nsSanctionNo);
		dynaValide.set("employeeName",basicBean.getEmployeeName());
		dynaValide.set("region",basicBean.getRegion());
		dynaValide.set("station",basicBean.getStation());
		log.info("---------"+basicBean.getPostingFlag());
		dynaValide.set("postingFlag",basicBean.getPostingFlag());
		
		HashMap regionHashmap=new HashMap();
		regionHashmap = commonUtil.getRegion();
		request.setAttribute("regionHashmap", regionHashmap);
		 
		request.setAttribute("employeeInfo", basicBean);
		request.setAttribute("postingFlag", basicBean.getPostingFlag());
		this.getScreenTitle(request, response, "FSAPostingDet");
		saveToken(request);
		String path="loademployeepostingdetails";
		return mapping.findForward(path);
	
	}
	public ActionForward addEmployeePostingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
 
		String pensionNo="",nsSanctionNo="",flag="N",frmName="", postingRegion="",postingStation="";
		String nomineeName = "",nomineeSerialNo="",arrearType="";	
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		 
		
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		} 
		 
		log.info("--addEmployeePostingDetails--"+frmName);
			 	 
			
			if (dynaValide.getString("pensionNo") != null) {
				pensionNo = dynaValide.getString("pensionNo").trim() ;
			}
			if (dynaValide.getString("nssanctionno") != null) {
				nsSanctionNo = dynaValide.getString("nssanctionno") ;
			}
			if(request.getParameter("postingregion")!=null){
				postingRegion=request.getParameter("postingregion");
				}
			if(request.getParameter("postingstation")!=null){
				postingStation=request.getParameter("postingstation");
				} 	 
			
			if(!frmName.equals("")){
		  ptwAdvanceService.addEmployeePostingDet(pensionNo,nsSanctionNo,postingRegion,postingStation);
			}
		 
		 
		 ForwardParameters fwdParams = new ForwardParameters();
		 ActionForward forward=null; 
		 
			  forward=mapping.findForward("addpostingdetailssuccess");
			Map params = new HashMap();		 		 
			params.put("method", "loadEmployeePostingDetails");
			this.getScreenTitle(request, response, "");		
			fwdParams.add("pensionno", pensionNo);		 
			fwdParams.add("nssanctionno", nsSanctionNo);	
			fwdParams.add("frm_name", frmName);	 
		  return fwdParams.forward(forward);
		 }
	
	public ActionForward deleteEmployeePostingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
 
		String pensionNo="",nsSanctionNo="",flag="N",frmName="", postingRegion="",postingStation="";
		String nomineeName = "",nomineeSerialNo="",arrearType="";	
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		 
		 
		
		if ( isTokenValid(request) ) {
			if (request.getParameter("frm_name") != null) {
				frmName = request.getParameter("frm_name");
			}
			resetToken(request);
		}else{
			frmName="";
		} 
		 
		log.info("--addEmployeePostingDetails--"+frmName);
			 	 
			
			if (dynaValide.getString("pensionNo") != null) {
				pensionNo = dynaValide.getString("pensionNo").trim() ;
			}
			if (dynaValide.getString("nssanctionno") != null) {
				nsSanctionNo = dynaValide.getString("nssanctionno") ;
			}
			 	 
			
			if(!frmName.equals("")){
		  ptwAdvanceService.deleteEmployeePostingDetails(pensionNo,nsSanctionNo);
			}
		 
		 
		 ForwardParameters fwdParams = new ForwardParameters();
		 ActionForward forward=null; 
		 
			  forward=mapping.findForward("addpostingdetailssuccess");
			Map params = new HashMap();		 		 
			params.put("method", "loadEmployeePostingDetails");
			this.getScreenTitle(request, response, "");		
			fwdParams.add("pensionno", pensionNo);		 
			fwdParams.add("nssanctionno", nsSanctionNo);	
			fwdParams.add("frm_name", frmName);	 
		  return fwdParams.forward(forward);
		 }
	public ActionForward deleteAdvances(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AdvanceEditBean advanceEditBean=new AdvanceEditBean();		
		
		String userName="",computerName="",frmTransID="",frmPensionNo="",frmNm="",advanceType="", verifiedBy="";
		 AdvanceSearchBean advanceSearchBean = new AdvanceSearchBean();		
		session=request.getSession(true);
		if(session.getAttribute("userid")!=null){
			userName=(String)session.getAttribute("userid");
			computerName=(String)session.getAttribute("computername");
		}	
	
		ptwAdvanceService=new CPFPTWAdvanceService(userName,computerName);
			
		if(request.getParameter("frmTransID")!=null){
			frmTransID=request.getParameter("frmTransID");
		}		
		if(request.getParameter("frmPensionNo")!=null){
			frmPensionNo=request.getParameter("frmPensionNo");
		}
		if(request.getParameter("advanceType")!=null){
			advanceType=request.getParameter("advanceType");
		}
		if(request.getParameter("verifiedBy")!=null){
			verifiedBy=request.getParameter("verifiedBy");
		}
		if(request.getParameter("frm_name")!=null){
			frmNm=request.getParameter("frm_name");
		}
		this.getLoginDetails(request);
		
		advanceSearchBean.setPensionNo(frmPensionNo);
		advanceSearchBean.setAdvanceTransID(frmTransID);
		advanceSearchBean.setAdvanceType(advanceType);
		advanceSearchBean.setVerifiedBy(verifiedBy);
		advanceSearchBean.setLoginUserId(this.loginUserId);		
		advanceSearchBean.setLoginUserName(this.loginUserName);
		advanceSearchBean.setLoginUnitCode(this.loginUnitCode);
		advanceSearchBean.setLoginRegion(this.loginRegion);
		advanceSearchBean.setLoginUserDispName(this.loginSignName);
		int n=ptwAdvanceService.deleteAdvances(advanceSearchBean);	
		this.getScreenTitle(request,response,frmNm);
		 
		ActionForward forward = mapping.findForward("success");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
		
						
		params.put("method", "searchAdvances");
		params.put("frm_name", "advances");
		
		if(frmNm.equals("advances"))
			fwdParams.add("frm_name", "advances");
		else
			fwdParams.add("frm_name", "pfw");
		

		return fwdParams.forward(forward);
	}
	
	public ActionForward advanceSanctionOrderRev(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AdvanceCPFForm2Bean advanceReportBean = new AdvanceCPFForm2Bean();
		ArrayList list = new ArrayList();
		ArrayList transList = new ArrayList();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		EmpBankMaster bankMasterBean = new EmpBankMaster();
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		String frmName = "", path = "", pensionNo = "", transactionNo = "", currentYear = "",reportType="";
		int nextYear = 0;
		int previousYear = 0, currentMonth = 0;
		String previousYear1 = "";
		ptwAdvanceService = new CPFPTWAdvanceService();
		
		if (request.getParameter("frmPensionNo") != null) {
			pensionNo = request.getParameter("frmPensionNo");
		}
		if (request.getParameter("frmTransID") != null) {
			transactionNo = request.getParameter("frmTransID");
			if (transactionNo.lastIndexOf("/") != -1) {
				if (!transactionNo.equals("")) {
					String[] fndTransID = transactionNo.split("/");
					if (fndTransID.length == 3) {
						transactionNo = fndTransID[2];
					}
				}
			}
		}
		if (request.getParameter("frmName") != null) {
			frmName = request.getParameter("frmName");
		}
		if (request.getParameter("reportType") != null) {
			reportType = request.getParameter("reportType");
		}
		 
		try { 
			log.info("advanceSanctionOrderRev::pensionNo" + pensionNo
					+ "transactionNo" + transactionNo);
			dynaValide.set("authorizedrmrks", "");
			list = ptwAdvanceService.advanceSanctionOrderRev(pensionNo,
					transactionNo, frmName);
			log.info("advanceSanctionOrderRev::::Size" + list.size());
			
			advanceReportBean = (AdvanceCPFForm2Bean) list.get(0);
			basicReportBean = (AdvanceBasicReportBean) list.get(1);
			
			 
			
			/*
			 * currentYear=commonUtil.getCurrentDate("yyyy").substring(2,4);
			 * nextYear=Integer.parseInt(currentYear)+1;
			 * advanceReportBean.setFinyear(currentYear+"-"+nextYear);
			 */
			currentMonth = Integer.parseInt(commonUtil.getCurrentDate("MM"));
			currentYear = commonUtil.getCurrentDate("yyyy").substring(2, 4);
			nextYear = Integer.parseInt(currentYear) + 1;
			previousYear = Integer.parseInt(currentYear) - 1;
			
			if (previousYear <= 9)
				previousYear1 = "0" + previousYear;
			
			if (currentMonth >= 04)
				advanceReportBean.setFinyear(currentYear + "-" + nextYear);
			else if (previousYear <= 9)
				advanceReportBean.setFinyear(previousYear1 + "-" + currentYear);
			else
				advanceReportBean.setFinyear(previousYear + "-" + currentYear);
			request.setAttribute("PFWSanctionOrderBean", advanceReportBean);
			this.fillForm4BeanToDyna(advanceReportBean, form);
			dynaValide.set("amntrcdedrs", advanceReportBean
					.getAmntRecommended());
			dynaValide.set("partyname", advanceReportBean.getPartyname());
			request.setAttribute("BasicReportBean", basicReportBean);
			 
			bankMasterBean = ptwAdvanceService.employeeBankInfo(pensionNo,transactionNo);
			request.setAttribute("bankMasterBean", bankMasterBean);
		  
			this.getLoginDetails(request);
				
				log.info("loginUserName------" + this.loginUserName);
				
				CPFPFWTransInfo cpfInfo = new CPFPFWTransInfo(this.loginUserId,
						this.loginUserName, this.loginUnitCode,
						this.loginRegion, this.loginSignName);
				String imgagePath = getServlet().getServletContext()
				.getRealPath("/")
				+ "/uploads/dbf/";
				
				if(frmName.equals("PFWRevisedVerification")){
				transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
						transactionNo,
						Constants.APPLICATION_PROCESSING_PFW_REVISED_VERIFICATION,
						imgagePath);
		       }else if(frmName.equals("PFWRevisedRecommendation")){
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_PFW_REVISED_RECOMMENDATION,
							imgagePath);
			    }else if(frmName.equals("PFWRevisedApproved")){
					transList = (ArrayList) cpfInfo.readTransInfo(pensionNo,
							transactionNo,
							Constants.APPLICATION_PROCESSING_PFW_REVISED_APPROVAL_ED,
							imgagePath);
			       }
				log.info("transList size" + transList.size());
				
				if (transList.size() > 0) {
					request.setAttribute("transList", transList);
					
				}
				  
				 
				if(reportType.equals("Print-Sanc")){
					path = "viewPFWSanctionOrderRevReport";
				}else if(reportType.equals("Print")){
					path = "viewPFWSORevNoteSheetReport";
				}
				
				
				
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			ActionMessages actionMessage = new ActionMessages();
			actionMessage.add("message", new ActionMessage(e.getMessage()));
			return mapping.findForward("viewPFWSanctionOrderRevReport");
		}
		return mapping.findForward(path);
	}
	public ActionForward searchAdvancesRev(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaValidatorForm dynaValide = (DynaValidatorForm) form;
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		String path="";
		this.getLoginDetails(request);
		if (dynaValide.getString("employeeName") != null) {
			searchBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
		if (request.getParameter("frm_name") != null) {
			searchBean.setFormName(request.getParameter("frm_name"));
		}
		if (dynaValide.getString("pfid") != null) {
			searchBean.setPensionNo(commonUtil.getSearchPFID(dynaValide
					.getString("pfid").toString().trim()));
		}
		
		if (dynaValide.getString("advanceTransID") != null) {
			searchBean
			.setAdvanceTransID(dynaValide.getString("advanceTransID"));
			if (!searchBean.getAdvanceTransID().equals("")) {
				String[] fndTransID = searchBean.getAdvanceTransID().split("/");
				if (fndTransID.length == 3) {
					searchBean.setAdvanceTransID(fndTransID[2]);
				}
			}
		}
		if (dynaValide.getString("advanceType") != null) {
			searchBean.setAdvanceType(dynaValide.getString("advanceType"));
		}
		if (dynaValide.getString("advanceTransyear") != null) {
			searchBean.setAdvanceTransyear(dynaValide
					.getString("advanceTransyear"));
		}
		if (dynaValide.getString("advanceTransStatus") != null) {
			searchBean.setAdvanceTrnStatus(dynaValide
					.getString("advanceTransStatus"));
		}
		if (dynaValide.getString("purposeType") != null) {
			searchBean.setPurposeType(dynaValide.getString("purposeType"));
		}
		if (request.getParameter("frm_name") != null) {
			log.info("----*********------" + request.getParameter("frm_name"));
		}
		searchBean.setUserName(this.loginUserName);
		searchBean.setProfileName(this.loginProfile);
		log.info("----userName-----" + searchBean.getUserName()+"==ProfileName="+ searchBean.getProfileName());
		ptwAdvanceService = new CPFPTWAdvanceService();
		ArrayList searchlist = ptwAdvanceService.searchAdvancesRev(searchBean);
		request.setAttribute("searchlist", searchlist);
		this.getScreenTitle(request, response, "");
		 
		  if (request.getParameter("frm_name").equals("PFWRevisedVerification")||
				request.getParameter("frm_name").equals("PFWRevisedRecommendation")||
				request.getParameter("frm_name").equals("PFWRevisedApproval") || 
				request.getParameter("frm_name").equals("PFWRevisedApproved")) {
			  
			path = "loadPFWRevisedSearch";
		} 
		  
		return mapping.findForward(path);
	}
}
