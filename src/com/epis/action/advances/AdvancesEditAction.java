package com.epis.action.advances;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.advances.AdvanceBasicReportBean;
import com.epis.bean.advances.AdvanceEditBean;
import com.epis.bean.advances.EmpBankMaster;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;
import com.epis.services.advances.CPFPTWAdvanceService;
import com.epis.services.CommonService;

public class AdvancesEditAction extends DispatchAction{
	
	CommonUtil commonUtil=new CommonUtil();
	CPFPTWAdvanceService ptwAdvanceService=null;
	CommonService commonService=new CommonService();
	HttpSession session=null;
	Log log = new Log(AdvancesAction.class);
	
	public ActionForward editAdvances(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AdvanceEditBean advanceEditBean=new AdvanceEditBean();
		AdvanceBasicBean advanceBasicBean=new AdvanceBasicBean();
		
		String currentDate="",userName="",computerName="",messages="",selectedEmpName="",selectedRegion="",frmTransID="",frmPensionNo="",cpfaccno="",transDt="",station="",region="";
		boolean flagval=false;
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		ArrayList personalList=new ArrayList();
		ArrayList nomineeList=new ArrayList();
		ArrayList airportsList=new ArrayList();
		session=request.getSession(true);
		if(session.getAttribute("userid")!=null){
			userName=(String)session.getAttribute("userid");
			computerName=(String)session.getAttribute("computername");
		}	
		EmpBankMaster bankMaster=new EmpBankMaster();
		ptwAdvanceService=new CPFPTWAdvanceService(userName,computerName);
		String pfwAdvanceType="",pfwPurposeType="",advPurpose="";
		StringBuffer nomineeRow = new StringBuffer();
		
		if(request.getParameter("frmTransID")!=null){
			frmTransID=request.getParameter("frmTransID");
		}		
		if(request.getParameter("frmPensionNo")!=null){
			frmPensionNo=request.getParameter("frmPensionNo");
		}
		currentDate="-Feb-2009";
		personalList=commonService.loadPersonalTransInfo(selectedEmpName,selectedRegion,frmPensionNo,cpfaccno,"-Sep-2007");
		advanceBasicBean=(AdvanceBasicBean)personalList.get(0);
		if(personalList.size()!=0){
			this.fillPersonalForm(personalList,form);
			
		}
		advanceEditBean=(AdvanceEditBean)ptwAdvanceService.loadAdvancesEditInfo(frmTransID,frmPensionNo);
		
	
				try {					
					// Getting Station,Region stored in employee_advances_form from 08-May-2012
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy"); 					 
					transDt = CommonUtil.converDBToAppFormat(new Date(advanceEditBean.getCreatedDate()));						 
					Date  transdate = df.parse(transDt);				
				if(transdate.after(new Date("08-May-2012"))){
					advanceEditBean.setStation(advanceEditBean.getStation());  // AIRPORTCODE FRM ADVANCE TABLE 
					advanceEditBean.setRegion(advanceEditBean.getRegion());   //  REGION FRM ADVANCE TABLE   
				}else{
					advanceEditBean.setStation(advanceBasicBean.getStation());   //  AIRPORTCODE FRM PERSNL;
					advanceEditBean.setRegion(advanceBasicBean.getRegion()); //  REGION FRM PERSNL  
				}
			 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		
		
		
		
		  if(!advanceEditBean.getRegion().equals("")){
				log.info("----******************---Region in Action--------"+advanceEditBean.getRegion());
				log.info("-------st in Action--------"+advanceEditBean.getStation());
				
				airportsList=commonUtil.getAirportList(advanceEditBean.getRegion());
				
				log.info("---------airportsList Size--------"+airportsList.size());
				if(airportsList.size()>0){				
					request.setAttribute("airportsList",airportsList);
				}		
			}	
		
		this.getScreenTitle(request,response);
		request.setAttribute("advanceEditInfo",advanceEditBean);
		return mapping.findForward("editAdvances");
	}
	
	public void getScreenTitle(HttpServletRequest request,HttpServletResponse response){
		log.info("AdvancesAction::getScreenTitle");
		AdvanceBasicBean advanceBean=new AdvanceBasicBean();	
		String frmName="",frmNm="";
						
		if(request.getParameter("frm_name")!=null){
			frmNm=request.getParameter("frm_name");
		}
		
		log.info("==========frmNm======="+frmNm);
		
		if(frmNm.equals("advances")){
			frmName="CPF Advance Form[Search]";
		}else if(frmNm.equals("pfw")){
			frmName="PFW (Part Final Withdrawn) Form[Search]";
		}else if(frmNm.equals("advancesedit")){
			frmName="CPF Advance [Edit] Form ";
		}else if(frmNm.equals("pfwedit")){
			frmName="PFW (Part Final Withdraw)[Edit] Form ";
		}
		   
		advanceBean.setScreenTitle(frmName);
		log.info("----frmName----"+frmName);
		advanceBean.setFrmName(frmNm);
		log.info("----frmNm----"+frmName);
		request.setAttribute("screenTitle",frmName);
		request.setAttribute("advanceBean",advanceBean);	
		
	}
	
	public ActionForward updatePFWAdvacneInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AdvanceEditBean advanceBean=new AdvanceEditBean();
		String userName="",computerName="",messages="",totalInstall="",pensionNo="",wthDrwFlag="NO";
		
		session=request.getSession(true);
		if(session.getAttribute("userid")!=null){
			userName=(String)session.getAttribute("userid");
			computerName=(String)session.getAttribute("computername");
		}
		
		EmpBankMaster bankMaster=new EmpBankMaster();
		ptwAdvanceService=new CPFPTWAdvanceService(userName,computerName);
		String pfwAdvanceType="",pfwPurposeType="",advPurpose="";
		if(request.getParameter("frm_pfwAdv")!=null){
			pfwAdvanceType=request.getParameter("frm_pfwAdv");
		}
		if(request.getParameter("frm_pfwPurpose")!=null){
			pfwPurposeType=request.getParameter("frm_pfwPurpose");
		}
		if(request.getParameter("frm_advPurpose")!=null){
			advPurpose=request.getParameter("frm_advPurpose");
		}
		if(request.getParameter("frm_totalinstall")!=null){
			totalInstall=request.getParameter("frm_totalinstall");
		}else{
			totalInstall="0";
		}
		if(request.getParameter("frm_pensionno")!=null){
			pensionNo=request.getParameter("frm_pensionno");
		}
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		advanceBean=this.fillDynaFormToBean(form);
		if (request.getParameter("frm_region") != null) {
			advanceBean.setRegion(request.getParameter("frm_region"));
		}	
		advanceBean.setPensionNo(pensionNo);		
		log.info("Pensionno"+dynaValide.getString("pensionNo"));
		advanceBean.setAdvanceType(pfwAdvanceType);
		advanceBean.setPfwPurpose(pfwPurposeType);
		advanceBean.setAdvPurpose(advPurpose);
		advanceBean.setCreatedDate(dynaValide.getString("createdDate"));
		if(!pfwPurposeType.equals("NO-SELECT")){
			dynaValide.set("purposeType",pfwPurposeType);
			advanceBean.setPurposeType(pfwPurposeType);
		}else if(!advPurpose.equals("NO-SELECT")){
			dynaValide.set("purposeType",advPurpose);	
			advanceBean.setPurposeType(advPurpose);
		}
		messages=ptwAdvanceService.updateAdvanceNextInfo(advanceBean,bankMaster,wthDrwFlag);
		dynaValide.set("message",messages);	
		ActionMessages actionMessage=new ActionMessages();
		actionMessage.add("message",new ActionMessage(messages));
		saveMessages(request,actionMessage);
		
		ActionForward forward = mapping.findForward("updatesuccess");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
				
		params.put("method", "searchAdvances");
		params.put("frm_name", "advances");
		//fwdParams.add(params);
		this.getScreenTitle(request,response);
		if(pfwAdvanceType.equals("CPF"))
		fwdParams.add("frm_name", "advances");
		else  if(pfwAdvanceType.equals("PFW"))
		fwdParams.add("frm_name", "pfw");
			
		return fwdParams.forward(forward);
		//return mapping.findForward("/advanceSearch.do?method=searchAdvances&frm_name=advances");
	}
	
	public ActionForward updateAdvacneInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AdvanceEditBean advanceBean=new AdvanceEditBean();
		AdvanceEditBean advanceEditBean=new AdvanceEditBean();
		AdvanceEditBean LODBean=null;
		EmpBankMaster bankMaster=new EmpBankMaster();
		EmpBankMaster bankMasterBean=new EmpBankMaster();
		
		AdvanceBasicReportBean withDrawalBean=null;
		
		String userName="",computerName="",messages="",totalInstall="",pensionNo="",path="";
		
		ArrayList advanceList=new ArrayList();
		ArrayList LODList=new ArrayList();
		ArrayList wthdrwList=new ArrayList();
		AdvanceBasicReportBean advanceReportBean=new AdvanceBasicReportBean();
		AdvanceBasicReportBean advanceReportBean1=new AdvanceBasicReportBean();
		
		session=request.getSession(true);
		if(session.getAttribute("userid")!=null){
			userName=(String)session.getAttribute("userid");
			computerName=(String)session.getAttribute("computername");
		}
		
		
		ptwAdvanceService=new CPFPTWAdvanceService(userName,computerName);
		String pfwAdvanceType="",pfwPurposeType="",advPurpose="",transId="";
		
		
		if(request.getParameter("frm_transId")!=null){
			transId=request.getParameter("frm_transId");
		}
		if(request.getParameter("frm_pfwAdv")!=null){
			pfwAdvanceType=request.getParameter("frm_pfwAdv");
		}
		if(request.getParameter("frm_pfwPurpose")!=null){
			pfwPurposeType=request.getParameter("frm_pfwPurpose");
		}
		if(request.getParameter("frm_advPurpose")!=null){
			advPurpose=request.getParameter("frm_advPurpose");
		}
		if(request.getParameter("frm_totalinstall")!=null){
			totalInstall=request.getParameter("frm_totalinstall");
		}else{
			totalInstall="0";
		}
		if(request.getParameter("frm_pensionno")!=null){
			pensionNo=request.getParameter("frm_pensionno");
		}
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		advanceBean=this.fillDynaFormToBean(form);
		if (request.getParameter("frm_region") != null) {
			advanceBean.setRegion(request.getParameter("frm_region"));
		}
 
		advanceBean.setPensionNo(pensionNo);
		advanceBean.setCpftotalinstall(totalInstall);		
		advanceBean.setAdvanceType(pfwAdvanceType);		
		advanceBean.setPfwPurpose(pfwPurposeType);
		advanceBean.setAdvPurpose(advPurpose);
		advanceBean.setCreatedDate(dynaValide.getString("createdDate"));
		log.info("updateAdvacneInfo==============================="+advanceBean.getCreatedDate());
		if(advanceBean.getPfwPurpose().equals("HBA")){
			advanceBean.setHbapurposetype(advanceBean.getPurposeOptionType());			
		}else if(advanceBean.getPfwPurpose().equals("MARRIAGE")){
			advanceBean.setEmpfmlydtls(advanceBean.getPurposeOptionType());
		}else if(advanceBean.getPfwPurpose().equals("HE") || advanceBean.getAdvPurpose().equals("EDUCATION")){
			advanceBean.setPfwHEType(advanceBean.getPurposeOptionType());
		}else if(advanceBean.getAdvPurpose().equals("COST") || advanceBean.getAdvPurpose().equals("ILLNESS")){
			advanceBean.setAdvancecostexp(advanceBean.getPurposeOptionType());
		}else if(advanceBean.getAdvPurpose().equals("OBLIGATORY")){
			advanceBean.setOblCermoney(advanceBean.getPurposeOptionType());
		}else if(advanceBean.getAdvPurpose().equals("OBMARRIAGE")){
			advanceBean.setCpfmarriageexp(advanceBean.getPurposeOptionType());
		}
		
		if(!pfwPurposeType.equals("NO-SELECT")){
			dynaValide.set("purposeType",pfwPurposeType);
			advanceBean.setPurposeType(pfwPurposeType);
		}else if(!advPurpose.equals("NO-SELECT")){
			dynaValide.set("purposeType",advPurpose);	
			advanceBean.setPurposeType(advPurpose);
		}
		if (dynaValide.getString("hbarepaymenttype") != null) {
			advanceBean.setHbarepaymenttype(dynaValide
					.getString("hbarepaymenttype"));
		}
		if (dynaValide.getString("hbaloanname") != null) {
			advanceBean.setHbaloanname(dynaValide.getString("hbaloanname"));
		}
		if (dynaValide.getString("hbaloanaddress") != null) {
			advanceBean.setHbaloanaddress(dynaValide.getString("hbaloanaddress"));
		}
		if (!dynaValide.getString("osamountwithinterest").equals("")) {
			advanceBean.setOsamountwithinterest(dynaValide
					.getString("osamountwithinterest"));
		} else {
			advanceBean.setOsamountwithinterest("0.00");
		}
		advanceList=ptwAdvanceService.updateAdvacneInfo(advanceBean,bankMaster);
		
		if(advanceList.size()>0){
		advanceReportBean=(AdvanceBasicReportBean)advanceList.get(0);
		request.setAttribute("advanceReportBean",advanceReportBean);
		this.getScreenTitle(request,response);		
		log.info("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS======================"+advanceBean.getCreatedDate());
		request.setAttribute("advanceBean",advanceBean);
		
		if(!advanceBean.getLodInfo().equals("")){
			String lod=advanceBean.getLodInfo();
			String[] lods=lod.split(",");			
			for(int i=0;i<lods.length;i++){			
				LODBean= new AdvanceEditBean();
				LODBean.setLodInfo(lods[i]);	
				LODList.add(LODBean);
			}								
			request.setAttribute("LODList",LODList);
		}
		
		if(advanceBean.getPaymentinfo().equals("Y")){
			bankMasterBean=(EmpBankMaster)advanceList.get(1);
			//request.setAttribute("bankMasterBean",bankMasterBean);
			
			this.fillAdvanceEditForm(bankMasterBean,form);			
		}
		
		if(advanceBean.getChkwthdrwlinfo().trim().equals("Y")){					
			wthdrwList=(ArrayList)advanceList.get(2);
					
		/*	for(int i=0;i<wthdrwList.size();i++){								
				withDrawalBean=(AdvanceBasicReportBean)wthdrwList.get(i);				
			}*/
			
			if(wthdrwList.size()>0){
			request.setAttribute("wthdrwList",wthdrwList);
			request.setAttribute("wthdrwStr",wthdrwList);
			}
			
			
		}
		
		if(advanceBean.getPfwPurpose().equals("HE") || advanceBean.getAdvPurpose().equals("EDUCATION")){
			advanceReportBean1=(AdvanceBasicReportBean)advanceList.get(3);
			request.setAttribute("advanceReportBean1",advanceReportBean1);			
		}
		
		
		
		path="updatecontinue";
		}else{
			path="updatesuccess";
		}
		advanceEditBean=(AdvanceEditBean)ptwAdvanceService.loadAdvancesEditInfo(transId,pensionNo);
		dynaValide.set("advReasonText", advanceEditBean.getAdvReasonText());
		request.setAttribute("advanceEditInfo",advanceEditBean);
		
		session.setAttribute("backAdvaceBean",advanceBean);
		return mapping.findForward(path);
	
	}
	
	private void fillAdvanceEditForm(EmpBankMaster bankBean,ActionForm form){
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;				
	  	dynaValide.set("bankempname",bankBean.getBankempname());	
		dynaValide.set("bankempaddrs",bankBean.getBankempaddrs());	
		dynaValide.set("bankname",bankBean.getBankname());	
		dynaValide.set("branchaddress",bankBean.getBranchaddress());	
		dynaValide.set("banksavingaccno",bankBean.getBanksavingaccno());	
		dynaValide.set("bankemprtgsneftcode",bankBean.getBankemprtgsneftcode());
		dynaValide.set("empmailid",bankBean.getEmpmailid());
		dynaValide.set("bankempmicrono",bankBean.getBankempmicrono());	
							
	}
	
	private AdvanceEditBean fillDynaFormToBean(ActionForm form){
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		AdvanceEditBean basicBean=new AdvanceEditBean();
		if(dynaValide.getString("employeeName")!=null){
			basicBean.setEmployeeName(dynaValide.getString("employeeName"));
		}
	
		if(dynaValide.getString("trust")!=null){
			basicBean.setTrust(dynaValide.getString("trust"));		
		}
		if(dynaValide.getString("cpftotalinstall")!=null){
			basicBean.setCpftotalinstall(dynaValide.getString("cpftotalinstall"));		
		}
		if(dynaValide.getString("advanceTransID")!=null){
			basicBean.setAdvanceTransID(dynaValide.getString("advanceTransID"));		
		}
		if(dynaValide.getString("emoluments")!=null){
			basicBean.setEmoluments(dynaValide.getString("emoluments"));		
		}
		
		if(dynaValide.getString("paymentdt")!=null){
			basicBean.setPaymentdt(dynaValide.getString("paymentdt"));		
		}	
		if(dynaValide.getString("remarks")!=null){
			basicBean.setRemarks(dynaValide.getString("remarks"));		
		}	
		if(dynaValide.getString("pensionNo")!=null){
			basicBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		log.info("fillDynaFormToBean Pension No"+basicBean.getPensionNo());
		if(dynaValide.getString("pfid")!=null){
			basicBean.setPfid(dynaValide.getString("pfid"));
		}
		if(dynaValide.getString("designation")!=null){
			basicBean.setDesignation(dynaValide.getString("designation"));
		}
		if(dynaValide.getString("department")!=null){
			basicBean.setDepartment(dynaValide.getString("department"));
		}
		
		if(dynaValide.getString("dateOfMembership")!=null){
			basicBean.setDateOfMembership(dynaValide.getString("dateOfMembership"));
		}
		
		if(dynaValide.getString("dateOfJoining")!=null){
			basicBean.setDateOfJoining(dynaValide.getString("dateOfJoining"));
		}
		if(dynaValide.getString("dateOfBirth")!=null){
			basicBean.setDateOfBirth(dynaValide.getString("dateOfBirth"));
		}
		if(dynaValide.getString("employeeNo")!=null){
			basicBean.setEmployeeNo(dynaValide.getString("employeeNo"));
		}
		if(dynaValide.getString("cpfaccno")!=null){
			basicBean.setCpfaccno(dynaValide.getString("cpfaccno"));
		}	
		if(dynaValide.getString("region")!=null){
			basicBean.setRegion(dynaValide.getString("region"));
		}	
		if(dynaValide.getString("transMnthYear")!=null){
			basicBean.setTransMnthYear(dynaValide.getString("transMnthYear"));
		}
		if(dynaValide.getString("fhName")!=null){
			basicBean.setFhName(dynaValide.getString("fhName"));
		}	
		if(dynaValide.getString("wetherOption")!=null){
			basicBean.setWetherOption(dynaValide.getString("wetherOption"));
		}
		if(dynaValide.getString("emoluments")!=null){
			basicBean.setEmoluments(dynaValide.getString("emoluments"));
		}
		if(dynaValide.getString("advReqAmnt")!=null){
			basicBean.setAdvReqAmnt(dynaValide.getString("advReqAmnt"));
		}
		if(dynaValide.getString("advReasonText")!=null){
			basicBean.setAdvReasonText(dynaValide.getString("advReasonText"));
		}
		if(dynaValide.getString("subscriptionAmt")!=null){
			basicBean.setPfStatury(dynaValide.getString("subscriptionAmt"));		
		}
		if(dynaValide.getString("station")!=null){
			basicBean.setStation(dynaValide.getString("station"));		
		}
		
		if(dynaValide.getString("paymentinfo")!=null){
			basicBean.setPaymentinfo(dynaValide.getString("paymentinfo"));		
		}else{
			basicBean.setPaymentinfo("");
		}
		if(dynaValide.getString("lodInfo")!=null){
			basicBean.setLodInfo(dynaValide.getString("lodInfo"));		
		}else{
			basicBean.setLodInfo("");
		}
		
		if(dynaValide.getString("purposeOptionType")!=null){
			basicBean.setPurposeOptionType(dynaValide.getString("purposeOptionType"));		
		}else{
			basicBean.setPurposeOptionType("");
		}
			
		if(dynaValide.getString("paymentinfo").equals("Y")){
			if(!dynaValide.getString("modeofpartyname").equals("")){
				basicBean.setBankdetail("party");
			}else
			basicBean.setBankdetail("bank");
		}else
			basicBean.setBankdetail("NO-SELECT");
		
		
		if(dynaValide.getString("chkwthdrwlinfo")!=null){
			basicBean.setChkwthdrwlinfo(dynaValide.getString("chkwthdrwlinfo"));		
		}else{
			basicBean.setChkwthdrwlinfo("");
		}
		
		if(dynaValide.getString("placeofposting")!=null){
			basicBean.setPlaceofposting(dynaValide.getString("placeofposting"));		
		}else{
			basicBean.setPlaceofposting("");
		}
		
		if(dynaValide.getString("advncerqddepend")!=null){
			basicBean.setAdvncerqddepend(dynaValide.getString("advncerqddepend"));		
		}else{
			basicBean.setAdvncerqddepend("");
		}
		
		if(dynaValide.getString("utlisiedamntdrwn")!=null){
			basicBean.setUtlisiedamntdrwn(dynaValide.getString("utlisiedamntdrwn"));		
		}else{
			basicBean.setUtlisiedamntdrwn("");
		}
			
		return basicBean;
	}
	
	public ActionForward loadAdvanceFormBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("AdvancesAction::loadAdvanceFormBack"+request.getAttribute("advanceBasicBean"));
		AdvanceEditBean advanceBasicBean=new AdvanceEditBean();
		AdvanceEditBean advanceEditBean=new AdvanceEditBean();
		ArrayList personalList=new ArrayList();
		String transId="",pensionNo="",selectedEmpName="",selectedRegion="",cpfaccno="";
		ArrayList airportsList=new ArrayList();
		ArrayList list=new ArrayList();
		session=request.getSession(true);
		request.setAttribute("focusFlag","true");
		
		if(request.getParameter("frm_transId")!=null){
			transId=request.getParameter("frm_transId");
		}		
		if(request.getParameter("frm_pensionno")!=null){
			pensionNo=request.getParameter("frm_pensionno");
		}
				
		if(session.getAttribute("backAdvaceBean")!=null){
			advanceBasicBean=(AdvanceEditBean)session.getAttribute("backAdvaceBean");
			list.add(advanceBasicBean);
			session.removeAttribute("backAdvaceBean");
		}		
		
		  if(!advanceBasicBean.getRegion().equals("")){				
				airportsList=commonUtil.getAirportList(advanceBasicBean.getRegion());				
				if(airportsList.size()>0){				
					request.setAttribute("airportsList",airportsList);
				}		
			}	
				
		personalList=commonService.loadPersonalTransInfo(selectedEmpName,selectedRegion,pensionNo,cpfaccno,"-Sep-2007");
		
		if(personalList.size()!=0){
			this.fillPersonalForm(personalList,form);			
		}
		
		advanceEditBean=(AdvanceEditBean)ptwAdvanceService.loadAdvancesEditInfo(transId,pensionNo);
		request.setAttribute("advanceEditInfo",advanceEditBean);
		
		this.getScreenTitle(request,response);
		
		return mapping.findForward("loadAdvanceEditForm");
	}
	
	private void fillPersonalForm(ArrayList list,ActionForm form){
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		AdvanceBasicBean basicBean=null;
		for(int i=0;i<list.size();i++){
			basicBean=(AdvanceBasicBean)list.get(i);
		}
		basicBean.setDateOfMembership(basicBean.getDateOfJoining());
	  	dynaValide.set("employeeName",basicBean.getEmployeeName());	
	  		
		dynaValide.set("pensionNo",basicBean.getPensionNo());	
		dynaValide.set("pfid",basicBean.getPfid());	
		dynaValide.set("designation",basicBean.getDesignation());	
		dynaValide.set("department",basicBean.getDepartment());	
		dynaValide.set("dateOfMembership",basicBean.getDateOfMembership());
		dynaValide.set("dateOfJoining",basicBean.getDateOfJoining());
		dynaValide.set("dateOfBirth",basicBean.getDateOfBirth());	
		dynaValide.set("employeeNo",basicBean.getEmployeeNo());	
		dynaValide.set("cpfaccno",basicBean.getCpfaccno());		
		dynaValide.set("region",basicBean.getRegion());		
		dynaValide.set("station",basicBean.getStation());		
		dynaValide.set("transMnthYear",basicBean.getTransMnthYear());
		dynaValide.set("fhName",basicBean.getFhName());		
		dynaValide.set("wetherOption",basicBean.getWetherOption());	
		dynaValide.set("emoluments",basicBean.getEmoluments());	
		dynaValide.set("pfStatury",basicBean.getPfStatury());	
		dynaValide.set("empmailid",basicBean.getMailID());	
		dynaValide.set("subscriptionAmt",basicBean.getPfStatury());	
		
	}
	
public ActionForward continueUpdateAdvances(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		AdvanceEditBean advanceBean=new AdvanceEditBean();
		EmpBankMaster bankMaster=new EmpBankMaster();
		String userName="",computerName="",messages="";
		session=request.getSession(true);
		if(session.getAttribute("userid")!=null){
			userName=(String)session.getAttribute("userid");
			computerName=(String)session.getAttribute("computername");
		}
		log.info("continueUpdateAdvances::userName"+userName+"computerName"+computerName);
		ptwAdvanceService=new CPFPTWAdvanceService(userName,computerName);
		String lodDocumentsInfo="",wthDrwFlag="";
		
		if(request.getParameter("lodinfo")!=null){
			lodDocumentsInfo=request.getParameter("lodinfo");			
		}
		
		log.info("Enclose Documents"+lodDocumentsInfo);
		
		if(request.getParameter("frm_wthDrwFlag")!=null){
			wthDrwFlag=request.getParameter("frm_wthDrwFlag");
			
		}
		 
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		
		advanceBean=this.fillDynaFormNextToBean(form,dynaValide.getString("advanceType"),dynaValide.getString("purposeType"),dynaValide.getString("wthdrwlist"));
		advanceBean.setCreatedDate(dynaValide.getString("createdDate"));
		advanceBean.setLodInfo(lodDocumentsInfo);
		String purposeType="";
		purposeType=dynaValide.getString("purposeType");
		advanceBean.setPurposeType(dynaValide.getString("purposeType"));
		advanceBean.setAdvanceType(dynaValide.getString("advanceType"));
		advanceBean.setCpftotalinstall(dynaValide.getString("cpftotalinstall"));
		log.info("saveAdvacneNextInfo::cpftotalinstall"+dynaValide.getString("cpftotalinstall"));
		if(advanceBean.getPurposeType().equals("defence")){
			advanceBean.setPurposeOptionType("SELF");
		}
		log.info("Purpose Option Type"+advanceBean.getPurposeOptionType());
		if(dynaValide.getString("advReqAmnt")!=null){
			advanceBean.setAdvReqAmnt(dynaValide.getString("advReqAmnt"));
		}
		if(dynaValide.getString("trust")!=null){
			advanceBean.setTrust(dynaValide.getString("trust"));
		}
		 if(dynaValide.getString("dateOfJoining")!=null){
				advanceBean.setDateOfJoining(dynaValide.getString("dateOfJoining"));
			}
			if(dynaValide.getString("station")!=null){
				advanceBean.setStation(dynaValide.getString("station"));
			}
		if(dynaValide.getString("region")!=null){
			advanceBean.setRegion(dynaValide.getString("region"));
		}
		if(dynaValide.getString("emoluments")!=null){
			advanceBean.setEmoluments(dynaValide.getString("emoluments"));
		}
		if(dynaValide.getString("pensionNo")!=null){
			advanceBean.setPensionNo(dynaValide.getString("pensionNo"));
		}
		if(dynaValide.getString("advanceTransID")!=null){
			advanceBean.setAdvanceTransID(dynaValide.getString("advanceTransID"));
		}
		if(dynaValide.getString("advReasonText")!=null){
			advanceBean.setAdvReasonText(dynaValide.getString("advReasonText"));
		}
		if(dynaValide.getString("bankdetail")!=null){
			if(dynaValide.getString("bankdetail").equals("NO-SELECT")){
				advanceBean.setPaymentinfo("N");
			}else{
				advanceBean.setPaymentinfo("Y");
			}
		}
		log.info("--in continueUpdateAdvances Paymentinfo--"+advanceBean.getPaymentinfo());
	  	if(dynaValide.getString("designation")!=null){
			advanceBean.setDesignation(dynaValide.getString("designation"));
		}
		if(dynaValide.getString("department")!=null){
			advanceBean.setDepartment(dynaValide.getString("department"));
		}
		if(dynaValide.getString("placeofposting")!=null){
			advanceBean.setPlaceofposting(dynaValide.getString("placeofposting"));
		}	
		
		if(request.getParameter("bankflag")!=null){
			advanceBean.setUpdateBankFlag(request.getParameter("bankflag"));
		} 
		log.info("---continueUpdateAdvances-----bankflag----"+advanceBean.getUpdateBankFlag());
		 
		bankMaster=this.fillDynaFormBankToBean(form);
		log.info("Amount "+dynaValide.getString("osamountwithinterest"));
		log.info("Pension no"+advanceBean.getPensionNo()+"Amount"+advanceBean.getAdvReqAmnt());
		log.info("Purspose TYpe"+advanceBean.getPurposeType()+"Advance Type"+advanceBean.getAdvanceType());
		
		messages=ptwAdvanceService.updateAdvanceNextInfo(advanceBean,bankMaster,wthDrwFlag);
		String bankInfo= ptwAdvanceService.updateBankInfo(advanceBean.getPensionNo(),advanceBean.getAdvanceTransID(),advanceBean.getUpdateBankFlag(), advanceBean.getPaymentinfo(),bankMaster);
			
		
		AdvanceBasicBean advanceBasic=new AdvanceBasicBean();
		request.setAttribute("advanceBean",advanceBasic);
		ActionMessages actionMessage=new ActionMessages();
		actionMessage.add("message",new ActionMessage("advance.success.message",messages));
		dynaValide.set("purposeType",purposeType);
		saveMessages(request,actionMessage);
		
		
		
		ActionForward forward = mapping.findForward("updatesuccess");
		ForwardParameters fwdParams = new ForwardParameters();
		
		Map params = new HashMap();
				
		params.put("method", "searchAdvances");
		params.put("frm_name", "advances");
		
		
		if(dynaValide.getString("advanceType").equals("CPF"))
			fwdParams.add("frm_name", "advances");
			else  if(dynaValide.getString("advanceType").equals("PFW"))
			fwdParams.add("frm_name", "pfw");
		
		dynaValide.getMap().clear();
		return fwdParams.forward(forward);
						
	}	
	private AdvanceEditBean fillDynaFormNextToBean(ActionForm form,String advanceType,String purposeType,String withDrawalList){
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		AdvanceEditBean basicBean=new AdvanceEditBean();
		String purposeOptionType="";
				
		if(advanceType.equals("PFW") && purposeType.equals("HBA")){
			purposeOptionType=dynaValide.getString("hbapurposetype");
		}else if(advanceType.equals("PFW") && purposeType.equals("HE")){
			purposeOptionType=dynaValide.getString("pfwHEType");
		}else if(advanceType.equals("PFW") && purposeType.equals("MARRIAGE")){
			purposeOptionType=dynaValide.getString("empfmlydtls");
		}else if(advanceType.equals("CPF") && purposeType.equals("COST")){
			purposeOptionType=dynaValide.getString("advancecostexp");
		}else if(advanceType.equals("CPF") && purposeType.equals("ILLNESS")){
			purposeOptionType=dynaValide.getString("advancecostexp");
		}else if(advanceType.equals("CPF") && purposeType.equals("OBMARRIAGE")){
			purposeOptionType=dynaValide.getString("cpfmarriageexp");
		}else if(advanceType.equals("CPF") && purposeType.equals("OBLIGATORY") && dynaValide.getString("advanceobligexp").equals("Other Ceremonies")){
			purposeOptionType=dynaValide.getString("advanceobligexp")+"-"+dynaValide.getString("oblCermoney");
		}else if(advanceType.equals("CPF") && purposeType.equals("EDUCATION")){
			purposeOptionType=dynaValide.getString("pfwHEType");
		}
		if(advanceType.equals("CPF") && !purposeType.equals("obMarriage")){
			basicBean.setAdvncerqddepend(dynaValide.getString("advncerqddepend"));
			basicBean.setUtlisiedamntdrwn(dynaValide.getString("utlisiedamntdrwn"));
		}
			
		basicBean.setPurposeOptionType(purposeOptionType);
		if(dynaValide.getString("fmlyEmpName")!=null){
			basicBean.setFmlyEmpName(dynaValide.getString("fmlyEmpName"));
		}
		if(dynaValide.getString("heRecog")!=null){
			basicBean.setHeRecog(dynaValide.getString("heRecog"));
		}
		if(dynaValide.getString("fmlyDOB")!=null){
			basicBean.setFmlyDOB(dynaValide.getString("fmlyDOB"));
		}
		if(dynaValide.getString("fmlyAge")!=null){
			basicBean.setFmlyAge(dynaValide.getString("fmlyAge"));
		}
		
		if(dynaValide.getString("brthCertProve")!=null){
			basicBean.setBrthCertProve(dynaValide.getString("brthCertProve"));
		}
		
		if(dynaValide.getString("hbarepaymenttype")!=null){
			basicBean.setHbarepaymenttype(dynaValide.getString("hbarepaymenttype"));
		}
		if(dynaValide.getString("hbaloanname")!=null){
			basicBean.setHbaloanname(dynaValide.getString("hbaloanname"));
		}
		if(dynaValide.getString("hbaloanaddress")!=null){
			basicBean.setHbaloanaddress(dynaValide.getString("hbaloanaddress"));
		}
		if(!dynaValide.getString("osamountwithinterest").equals("")){
			basicBean.setOsamountwithinterest(dynaValide.getString("osamountwithinterest"));
		}else{
			basicBean.setOsamountwithinterest("0.00");
		}
		if(dynaValide.getString("hbaownername")!=null){
			basicBean.setHbaownername(dynaValide.getString("hbaownername"));
		}
		  if(!withDrawalList.equals("")){			
				basicBean.setWthdrwlist(withDrawalList);
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
				basicBean.setWthdrwlist(withDrawals);
			
			}
		if(dynaValide.getString("hbaowneraddress")!=null){
			basicBean.setHbaowneraddress(dynaValide.getString("hbaowneraddress"));
		}
		
		if(dynaValide.getString("hbaownerarea")!=null){
			basicBean.setHbaownerarea(dynaValide.getString("hbaownerarea"));
		}
		if(dynaValide.getString("hbaownerplotno")!=null){
			basicBean.setHbaownerplotno(dynaValide.getString("hbaownerplotno"));
		}
		if(dynaValide.getString("hbaownerlocality")!=null){
			basicBean.setHbaownerlocality(dynaValide.getString("hbaownerlocality"));
		}
		if(dynaValide.getString("hbaownermuncipal")!=null){
			basicBean.setHbaownermuncipal(dynaValide.getString("hbaownermuncipal"));
		}	
		if(dynaValide.getString("hbaownercity")!=null){
			basicBean.setHbaownercity(dynaValide.getString("hbaownercity"));
		}	
		if(dynaValide.getString("hbadrwnfrmaai")!=null){
			basicBean.setHbadrwnfrmaai(dynaValide.getString("hbadrwnfrmaai"));
		}
		if(dynaValide.getString("hbapermissionaai")!=null){
			basicBean.setHbapermissionaai(dynaValide.getString("hbapermissionaai"));
		}	
		if(dynaValide.getString("propertyaddress")!=null){
			basicBean.setPropertyaddress(dynaValide.getString("propertyaddress"));
		}
		if(!dynaValide.getString("actualcost").equals("")){
			basicBean.setActualcost(dynaValide.getString("actualcost"));
		}else{
			basicBean.setActualcost("0.00");
		}
		if(dynaValide.getString("wthDrwlTrnsdt")!=null){
			basicBean.setWthDrwlTrnsdt(dynaValide.getString("wthDrwlTrnsdt"));
		}
		if(!dynaValide.getString("wthdrwlAmount").equals("")){
			basicBean.setWthdrwlAmount(dynaValide.getString("wthdrwlAmount"));
		}else{
			basicBean.setWthdrwlAmount("0.00");
		}
		if(!dynaValide.getString("wthdrwlpurpose").equals("")){
			basicBean.setWthdrwlpurpose(dynaValide.getString("wthdrwlpurpose"));
		}else{
			basicBean.setWthdrwlpurpose("");
		}
		if(dynaValide.getString("chkwthdrwlinfo")!=null){
			basicBean.setChkwthdrwlinfo(dynaValide.getString("chkwthdrwlinfo"));
		}
		if(dynaValide.getString("modeofpartyname")!=null){
			basicBean.setPartyName(dynaValide.getString("modeofpartyname"));
		}
		if(dynaValide.getString("modeofpartyaddrs")!=null){
			basicBean.setPartyAddress(dynaValide.getString("modeofpartyaddrs"));
		}
		if(!dynaValide.getString("hbawthdrwlpurpose").equals("")){
			basicBean.setHbawthdrwlpurpose(dynaValide.getString("hbawthdrwlpurpose"));
		}else{
			basicBean.setHbawthdrwlpurpose("");
		}
		
		if(!dynaValide.getString("hbawthdrwlamount").equals("")){
			basicBean.setHbawthdrwlamount(dynaValide.getString("hbawthdrwlamount"));
		}else{
			basicBean.setHbawthdrwlamount("0.00");
		}
		
		if(!dynaValide.getString("hbawthdrwladdress").equals("")){
			basicBean.setHbawthdrwladdress(dynaValide.getString("hbawthdrwladdress"));
		}else{
			basicBean.setHbawthdrwladdress("");
		}
		
		if(dynaValide.getString("marriagedate")!=null){
			basicBean.setMarriagedate(dynaValide.getString("marriagedate"));
		}else{
			basicBean.setMarriagedate("");
		}
		if(dynaValide.getString("nmCourse")!=null){
			basicBean.setNmCourse(dynaValide.getString("nmCourse"));
		}
		if(dynaValide.getString("nmInstitue")!=null){
			basicBean.setNmInstitue(dynaValide.getString("nmInstitue"));
		}
		if(dynaValide.getString("addrInstitue")!=null){
			basicBean.setAddrInstitue(dynaValide.getString("addrInstitue"));
		}
		
		if(dynaValide.getString("curseDuration")!=null){
			basicBean.setCurseDuration(dynaValide.getString("curseDuration"));
		}
		if(dynaValide.getString("heLastExaminfo")!=null){
			basicBean.setHeLastExaminfo(dynaValide.getString("heLastExaminfo"));
		}
		if(dynaValide.getString("subscriptionAmt")!=null){
			basicBean.setPfStatury(dynaValide.getString("subscriptionAmt"));
		}
		return basicBean;
	}
	
	private EmpBankMaster fillDynaFormBankToBean(ActionForm form){
		EmpBankMaster bankMaster=new EmpBankMaster();
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
		
				
		if(dynaValide.getString("chkbankinfo")!=null){
			bankMaster.setChkBankInfo(dynaValide.getString("chkbankinfo"));
		}else{
			bankMaster.setChkBankInfo("");
		}
		if(dynaValide.getString("bankempname")!=null){
			bankMaster.setBankempname(dynaValide.getString("bankempname"));
		}
		if(dynaValide.getString("bankempaddrs")!=null){
			bankMaster.setBankempaddrs(dynaValide.getString("bankempaddrs"));
		}	
		if(dynaValide.getString("bankname")!=null){
			bankMaster.setBankname(dynaValide.getString("bankname"));
		}
		if(dynaValide.getString("branchaddress")!=null){
			bankMaster.setBranchaddress(dynaValide.getString("branchaddress"));
		}
		if(dynaValide.getString("banksavingaccno")!=null){
			bankMaster.setBanksavingaccno(dynaValide.getString("banksavingaccno"));
		}
		if(dynaValide.getString("empmailid")!=null){
			bankMaster.setEmpmailid(dynaValide.getString("empmailid"));
		}
		if(dynaValide.getString("bankemprtgsneftcode")!=null){
			bankMaster.setBankemprtgsneftcode(dynaValide.getString("bankemprtgsneftcode"));
		}
		if(dynaValide.getString("bankempmicrono")!=null){
			bankMaster.setBankempmicrono(dynaValide.getString("bankempmicrono"));
		}
		if(dynaValide.getString("modeofpartyname")!=null){
			bankMaster.setPartyName(dynaValide.getString("modeofpartyname"));
		}
		if(dynaValide.getString("modeofpartyaddrs")!=null){
			bankMaster.setPartyAddress(dynaValide.getString("modeofpartyaddrs"));
		}
		return bankMaster;
	}

	public ActionForward loadPreviousWithDrawalDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("AdvancesEditAction::loadPreviousWithDrawalDetails");
				
		ArrayList wthDrwlList=new ArrayList();
		DynaValidatorForm dynaValide=(DynaValidatorForm)form;
	    String transID="",chkWthDrawInfo="";
			    
		if(request.getParameter("frm_transid")!=null){
			transID=request.getParameter("frm_transid");
		}
		
		if(request.getParameter("frm_wthdrwinfo")!=null){
			chkWthDrawInfo=request.getParameter("frm_wthdrwinfo");
		}		
			
		String wthdrwStr=ptwAdvanceService.loadWithDrawalDetails(transID,chkWthDrawInfo);
	  
		log.info("-------wthdrwStr in Edit Action------"+wthdrwStr);
	
		if(!wthdrwStr.equals("")){
		request.setAttribute("wthdrwStr",wthdrwStr);
		}
		
		return mapping.findForward("loadWthdrwlDetails");
	}
	 
}
