package com.epis.action.advances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.common.exception.EPISException;
import com.epis.info.login.LoginInfo;
import com.epis.services.advances.CPFPTWAdvanceService;
import com.epis.services.advances.NotificationService;

public class Notifications extends DispatchAction {
NotificationService notificationService  = new  NotificationService();
 
/**
	 * Method getNotifications
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	String loginUserId = "", loginUserName = "", loginUnitCode = "",
	loginRegion = "", loginProfile = "", loginSignName = "",loginUserDesignation="",loginUnitName="",loginAccountType="",loginUserPrivilageStage="";
	
	public ActionForward getNotifications(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors =  new ActionErrors();
		AdvanceSearchBean searchBean = new AdvanceSearchBean(request);
		ArrayList searchlist = new ArrayList();
		ArrayList cashbookList = new ArrayList();
		String frmName="";
		try {
			this.getLoginDetails(request);
			searchBean.setUserId(this.loginUserId);
			searchBean.setUserName(this.loginUserName);
			searchBean.setProfileName(this.loginProfile);
			searchBean.setLoginRegion(this.loginRegion);
			searchBean.setLoginUnitName(this.loginUnitName);
			searchBean.setLoginUserPrivilageStage(this.loginUserPrivilageStage);
			searchBean.setAdvanceType("CPF");
			request.setAttribute("cpf", NotificationService.getInstance().getNotifications("CPF",searchBean));			 
			
			log.info("----userName-----" + searchBean.getUserName()+"==ProfileName="+ searchBean.getProfileName());
			
			log.info("----loginUserPrivilageStage-----" + searchBean.getLoginUserPrivilageStage() +"==loginUserPrivilageStage==="+ this.loginUserPrivilageStage);
			if(this.loginUserPrivilageStage.equals("Approval")){
				frmName="CPFApproval";
			}else if(this.loginUserPrivilageStage.equals("Recommendation")){
				frmName="CPFRecommendation";
			}else{
				frmName="";
			}
			searchBean.setFormName(frmName); 
			
			  searchlist = notificationService.searchAdvanceForNotifications(searchBean); 
			  cashbookList = notificationService.searchAdvanceInCashBookEntries(searchBean); 
			
			request.setAttribute("cpfsearchlist", searchlist);
			request.setAttribute("cashBookList", cashbookList);
			request.setAttribute("privilageStage", this.loginUserPrivilageStage);
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.findForward("notifications");
	}
	public ActionForward fwdToNewRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Notifications::fwdToNewRequest() Enters");		 
		AdvanceSearchBean advanceBean = new AdvanceSearchBean();  
			String messages =""; 
			if(request.getParameter("frmPensionNo")!=null){
				advanceBean.setPensionNo(request.getParameter("frmPensionNo"));
			}else{
				advanceBean.setPensionNo("");
			}
			if(request.getParameter("frmTransID")!=null){
				advanceBean.setAdvanceTransID(request.getParameter("frmTransID"));
			}else{
				advanceBean.setAdvanceTransID("");
			}
			if(request.getParameter("frmAdvanceType")!=null){
				advanceBean.setAdvanceType(request.getParameter("frmAdvanceType"));
			}else{
				advanceBean.setAdvanceType("");
			}
			if(request.getParameter("frmPurposeType")!=null){
				advanceBean.setPurposeType(request.getParameter("frmPurposeType"));
			}else{
				advanceBean.setPurposeType("");
			}
			log.info("pensionno ="+advanceBean.getPensionNo()+ "==AdvanceTransID=="+advanceBean.getAdvanceTransID());
			this.getLoginDetails(request);
			advanceBean.setLoginUserId(this.loginUserId);
			advanceBean.setLoginUserName(this.loginUserName);
			advanceBean.setLoginUnitCode(this.loginUnitCode);
			advanceBean.setLoginRegion(this.loginRegion);
			advanceBean.setLoginUserDispName(this.loginSignName);
		
			 
			try {
				messages = notificationService.getNewAdvanceRequest(advanceBean);
				log.info("==messages==="+messages);
			} catch (EPISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 
			 ActionForward forward = null;
				ForwardParameters fwdParams = new ForwardParameters(); 
				Map params = new HashMap(); 
				params.put("method", "getNotifications");			 
				forward = mapping.findForward("loadNotifications");
				
				return fwdParams.forward(forward); 
		 
	}
	 
	 
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
		this.loginUserPrivilageStage= loginInfo.getPrivilageStage();
	}
	 
}
