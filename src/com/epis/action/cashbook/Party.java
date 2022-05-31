package com.epis.action.cashbook;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.admin.Bean;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.cashbook.PartyInfo;
import com.epis.services.cashbook.PartyService;
import com.epis.utilities.StringUtility;


public class Party extends DispatchAction {
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
			PartyInfo info = new PartyInfo(); 
			info.setPartyName(dyna.getString("partyName"));
			request.setAttribute("PartyList", PartyService.getInstance().search(info));
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
	 * Method delete
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		String[] recs = dyna.getStrings("deleteRecord");		
		ActionMessages errors = new ActionMessages();
		try {
			PartyService.getInstance().delete(recs);
		} catch (Exception e) {
			errors.add("deleteRecord",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return mapping.findForward("search");
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
		request.setAttribute("ScreenType","NEW");
		return mapping.findForward("new");
	}
	
	/**
	 * Method add
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		PartyInfo info = new PartyInfo(request);
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward("search");
		BankMasterInfo bInfo = null;
		List bankDetails = new ArrayList();
		try {
			BeanUtils.copyProperties(info,dyna);
			String[] bankInfo = info.getBankInfo();
			int length = bankInfo.length;
			for (int i = 0; i < length; i++) {
				StringTokenizer st = new StringTokenizer(bankInfo[i], "|");
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
				}
				bankDetails.add(bInfo);
			}
			info.setBankDetails(bankDetails);
			if("NEW".equals(request.getParameter("ScreenType"))){
				request.setAttribute("ScreenType","NEW");
				if(PartyService.getInstance().exists(info)){
					errors.add("",new ActionMessage("partyInfo.errors.exists",info.getPartyName()));
					forward = mapping.getInputForward();
				}else{
					PartyService.getInstance().add(info);
				}
			}else if("EDIT".equals(request.getParameter("ScreenType"))){
				request.setAttribute("ScreenType","EDIT");
				PartyService.getInstance().update(info);
			}
		} catch (IllegalAccessException e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		} catch (InvocationTargetException e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return forward;
	}
	
	/**
	 * Method fwdtoEdit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages errors = new ActionMessages();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			PartyInfo info = new PartyInfo();
			info.setPartyCode(request.getParameter("code"));
			info = PartyService.getInstance().edit(info);	
			List bankDetails = info.getBankDetails();
			int detLen = bankDetails.size();
			BankMasterInfo bInfo = null; 
			StringBuffer bankInfoBuf = null;
			Bean comBean = null;
			List bankInfo = new ArrayList();
			for(int i=0;i<detLen;i++){
				bInfo = (BankMasterInfo)bankDetails.get(i);
				bankInfoBuf = new StringBuffer(StringUtility.checknull(bInfo.getBankCode()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getAccountNo())).append("|").append(StringUtility.checknull(bInfo.getIFSCCode()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getNEFTRTGSCode())).append("|").append(StringUtility.checknull(bInfo.getMICRNo()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getBankName())).append("|").append(StringUtility.checknull(bInfo.getBranchName()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getAddress())).append("|").append(StringUtility.checknull(bInfo.getPhoneNo()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getFaxNo())).append("|").append(StringUtility.checknull(bInfo.getAccountType()));
				bankInfoBuf.append("|").append(StringUtility.checknull(bInfo.getContactPerson())).append("|").append(StringUtility.checknull(bInfo.getMobileNo()));
				comBean = new Bean(bankInfoBuf.toString(),bankInfoBuf.toString());
				bankInfo.add(comBean);
			}
			BeanUtils.copyProperties(dyna,info);
			request.setAttribute("bankInfo",bankInfo);
			request.setAttribute("ScreenType","EDIT");
		} catch (Exception e) {
			e.printStackTrace();
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		return mapping.findForward("new");			
	}
	
	public ActionForward showPartyParam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("ScreenType","REPORT");
		return mapping.findForward("showPartyParam");
	}
	

	public ActionForward partyReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;
		List partylist = new ArrayList();	
		String party=dyna.getString("partyName");
		String reportType=dyna.getString("reportType");		
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward("showReport");
		try {
			partylist=PartyService.getInstance().partyReport(party);
			request.setAttribute("PartyList",partylist);
			if("excel".equals(reportType)){
				forward =mapping.findForward("reportExcel");
			}
		} catch (Exception e) {
			errors.add("Report",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		
		return forward;
	}
	public ActionForward cashBooktoInvest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward("mapping");
		List investList=new ArrayList();
		List cashList=new ArrayList();
		List cashinvestList=new ArrayList();
		try{
			investList=PartyService.getInstance().getPartyList("I");
			cashList=PartyService.getInstance().getPartyList("C");
			cashinvestList=PartyService.getInstance().getPartyList("B");
			request.setAttribute("investList",investList);
			request.setAttribute("cashList",cashList);
			request.setAttribute("cashinvestList",cashinvestList);
		}
		catch (Exception e) {
			errors.add("Report",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(request,errors);
		return forward;
		
	}
	public ActionForward cashtoInvestGroup(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionMessages errors=new ActionMessages();
		ActionForward forward=am.findForward("groupmapping");
		List investgroupList=new ArrayList();
		List cashinvestindividualList=new ArrayList();
		List cashgroupList=new ArrayList();
		List groupList=new ArrayList();
		List cashinvestList=new ArrayList();
		try{
			investgroupList=PartyService.getInstance().getGroupPartyList("I");
			cashgroupList=PartyService.getInstance().getGroupPartyList("C");
			cashinvestList=PartyService.getInstance().getGroupPartyList("B");
			groupList=PartyService.getInstance().getGroupList();
			cashinvestindividualList=PartyService.getInstance().getPartyList("B");
			
			
				
			
			req.setAttribute("investgroupList",investgroupList);
			req.setAttribute("cashgroupList",cashgroupList);
			//req.setAttribute("cashinvestList",cashinvestList);
			req.setAttribute("groupList",groupList);
			req.setAttribute("cashinvestindividualList",cashinvestList);
			log.info("the size"+investgroupList.size());
		}
		catch(Exception e){
			errors.add("Report",new ActionMessage("errors",e.getMessage()));
		}
		
		return forward;
	}
}
