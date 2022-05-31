package com.epis.action.investment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.investment.ArrangersBean;
import com.epis.bean.investment.QuotationRequestBean;
import com.epis.dao.admin.UserDAO;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.investment.ArrangersService;
import com.epis.services.investment.InvestmentProposalService;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.QuotationService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.services.investment.TrustTypeService;
import com.epis.utilities.LetterFormates;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;


public class QuotationRequestAction extends DispatchAction {
	Log log=new Log(QuotationRequestAction.class);
	public ActionForward showQuotationRequestsAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ActionForward forward = mapping.findForward("showquotationrequestnew");
		List approvedList=null;
		List marketTypeList=null;
		List proposalRefList=null;
		List arrangersList=null;
		List purchasePriseList=null;
		List periodList=null;
		try{
			 approvedList=QuotationRequestService.getInstance().getApprovedList();
			 marketTypeList=QuotationRequestService.getInstance().getMarketTypeList();
			 proposalRefList=InvestmentProposalService.getInstance().getProposal();
			 purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
			 arrangersList=QuotationRequestService.getInstance().getArrangers();
			 periodList=QuotationRequestService.getInstance().getPeriods();
			 request.setAttribute("approvedList",approvedList);
			 request.setAttribute("marketTypeList",marketTypeList);
			 request.setAttribute("arrangersList",arrangersList);
			 request.setAttribute("proposalRefList",proposalRefList);
			 request.setAttribute("purchasePriseList",purchasePriseList);
			 request.setAttribute("periodList",periodList);
			 request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			 
			
			
		}catch (Exception e) {
			log.error("QuotationRequestAction:showquotationRequestsAdd:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			
			forward = mapping.getInputForward();
		}
	return forward;
	}
	public ActionForward addQuotationRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		ActionForward forward = mapping.findForward("quotationletterformate");
		SQLHelper sq=null;
		List ReportList=new ArrayList();
		Map reportMap=new HashMap();
		DynaValidatorForm quotation=(DynaValidatorForm)form;
		QuotationRequestBean bean=new QuotationRequestBean(request);
		HttpSession session = request.getSession();
		String letterNo="";
		List approvedList=null;
		List marketTypeList=null;
		List proposalRefList=null;
		List arrangersList=null;
		List purchasePriseList=null;
		List BankList=null;
		List periodList=null;
		double surplusamt=0.0;
		double investamt=0.0;
		StringBuffer investmentinwords=new StringBuffer();
		String investmentAmount="";
		double investresult=0.0;
		double result=0.0;
		String concrores="";
		double minQuantumamt=0.0;
		double quantumresult=0.0;
		String conQuantumCrores="";
		String conInWords="";
		StringBuffer totconWords=new StringBuffer();
		totconWords.append("(");
		
		
		try{
			approvedList=QuotationRequestService.getInstance().getApprovedList();
			 marketTypeList=QuotationRequestService.getInstance().getMarketTypeList();
			 proposalRefList=InvestmentProposalService.getInstance().getProposal();
			 purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
			 arrangersList=QuotationRequestService.getInstance().getArrangers();
			 BankList=BankMasterService.getInstance().search(new BankMasterInfo());
			 periodList=QuotationRequestService.getInstance().getPeriods();
			sq=new SQLHelper();
			bean.setProposalRefNo(StringUtility.checknull(quotation.getString("proposalRefNo")));
			bean.setTrustType(StringUtility.checknull(quotation.getString("trustType")));
			bean.setSecurityCategory(StringUtility.checknull(quotation.getString("securityCategory")));
			bean.setSecurityName(StringUtility.checknull(quotation.getString("securityName")));
			bean.setSurplusFund(StringUtility.checknull(quotation.getString("surplusFund")));
			surplusamt=Double.parseDouble(quotation.getString("surplusFund"));
			conInWords=sq.ConvertInWords(surplusamt);
			totconWords.append(conInWords+" "+"Only  )");
			
			
			result=surplusamt/10000000.00;
			concrores=result+"";
			bean.setConInWords(totconWords.toString());
			
			log.info("the converting crores amount...."+result);
			bean.setConcrores(concrores);
			bean.setMarketType(StringUtility.checknull(quotation.getString("marketType")));
			bean.setMarketTypedef(StringUtility.checknull(quotation.getString("marketTypedef")));
			bean.setArrangers(quotation.getStrings("arrangers"));
			//bean.setApproved(StringUtility.checknull(quotation.getString("approved")));
			bean.setRemarks(StringUtility.checknull(quotation.getString("remarks")));
			bean.setFormateRemarks(StringUtility.checknull(quotation.getString("formateRemarks")));
			bean.setModeOfPaymentRemarks(StringUtility.checknull(quotation.getString("modeOfPaymentRemarks")));
			bean.setPaymentThroughRemarks(StringUtility.checknull(quotation.getString("paymentThroughRemarks")));
			bean.setMinimumQuantum(StringUtility.checknull(quotation.getString("minimumQuantum")));
			minQuantumamt=Double.parseDouble(quotation.getString("minimumQuantum"));
			quantumresult=minQuantumamt/10000000.00;
			conQuantumCrores=quantumresult+"";
			bean.setConQuantumCrores(conQuantumCrores);
			bean.setQuotationAddress(StringUtility.checknull(quotation.getString("quotationAddress")));
			bean.setQuotationDate(StringUtility.checknull(quotation.getString("quotationDate")));
			bean.setQuotationTime(StringUtility.checknull(quotation.getString("quotationTime")));
			bean.setValidDate(StringUtility.checknull(quotation.getString("validDate")));
			bean.setValidTime(StringUtility.checknull(quotation.getString("validTime")));
			bean.setOpenDate(StringUtility.checknull(quotation.getString("openDate")));
			bean.setOpenTime(StringUtility.checknull(quotation.getString("openTime")));
			
			bean.setNameoftheTender(StringUtility.checknull(quotation.getString("nameoftheTender")));
			bean.setAddressoftheTender(StringUtility.checknull(quotation.getString("addressoftheTender")));
			bean.setTelephoneNo(StringUtility.checknull(quotation.getString("telephoneNo")));
			bean.setFaxNo(StringUtility.checknull(quotation.getString("faxNo")));
			bean.setContactPerson(StringUtility.checknull(quotation.getString("contactPerson")));
			bean.setStatus(StringUtility.checknull(quotation.getString("status")));
			bean.setDeliveryRequestedin(StringUtility.checknull(quotation.getString("deliveryRequestedin")));
			bean.setAccountNo(StringUtility.checknull(quotation.getString("accountNo")));
			bean.setFaceValue(StringUtility.checknull(quotation.getString("faceValue")));
			bean.setNumberOfUnits(StringUtility.checknull(quotation.getString("numberOfUnits")));
			bean.setInvestmentFaceValue(StringUtility.checknull(quotation.getString("investmentFaceValue")));
			bean.setPurchaseOption(StringUtility.checknull(quotation.getString("purchaseOption")));
			bean.setPremiumPaid(StringUtility.checknull(quotation.getString("premiumPaid")));
			bean.setPurchasePrice(StringUtility.checknull(quotation.getString("purchasePrice")));
			bean.setMaturityDate(StringUtility.checknull(quotation.getString("maturityDate")));
			bean.setSecurityFullName(StringUtility.checknull(quotation.getString("securityFullName")));
			bean.setInterestRate(StringUtility.checknull(quotation.getString("interestRate")));
			bean.setInterestDate(StringUtility.checknull(quotation.getString("interestDate")));
			bean.setPriceoffered(StringUtility.checknull(quotation.getString("priceoffered")));
			bean.setFromPeriod(StringUtility.checknull(quotation.getString("fromPeriod")));
			bean.setToPeriod(StringUtility.checknull(quotation.getString("toPeriod")));
			UserBean signPath=UserDAO.getInstance().getUser(bean.getLoginUserId());
			
			UserBean user = UserDAO.getInstance().getUser("23207");
			UserBean user1=UserDAO.getInstance().getUser("23208");
			String path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			
			String path1 = request.getSession().getServletContext().getRealPath("/");
			path1 +="uploads/SIGNATORY/"+user1.getEsignatoryName();
			
			String path2=request.getSession().getServletContext().getRealPath("/");
			path2 +="uploads/SIGNATORY/"+signPath.getEsignatoryName();
			
			UserDAO.getInstance().getImage(path,user.getUserName());
			UserDAO.getInstance().getImage(path1,user1.getUserName());
			UserDAO.getInstance().getImage(path2,signPath.getUserName());
			
			bean.setSignPathLeft("uploads/SIGNATORY/"+user.getEsignatoryName());
			bean.setSingPathRight("uploads/SIGNATORY/"+user1.getEsignatoryName());
			bean.setSignPath("uploads/SIGNATORY/"+signPath.getEsignatoryName());
			String designation=QuotationRequestService.getInstance().getDesignation(bean.getLoginUserId());
			bean.setSignPathLeftName(user.getDisplayName());
			bean.setSingPathRightName(user1.getDisplayName());
			bean.setSignPathName(signPath.getDisplayName());
			bean.setDesignation(bean.getDesignation());
			
			
			ArrangersBean arrangerbean=null;
			String arrangersarray[]=bean.getArrangers();
			ArrayList arrangerArray=new ArrayList();
			for(int i=0; i<arrangersarray.length; i++)
			{
				 arrangerbean=new ArrangersBean();
				 arrangerbean=ArrangersService.getInstance().findArrangers(arrangersarray[i]);
				 arrangerArray.add(arrangerbean);
			}
			if(!StringUtility.checknull(quotation.getString("investmentFaceValue")).equals(""))
			{
				investamt=Double.parseDouble(quotation.getString("investmentFaceValue"));
				investresult=surplusamt/10000000.00;
				investmentAmount=investresult+"";
				investmentinwords.append("RS ");
				investmentinwords.append(investmentAmount );
				investmentinwords.append(" Crores Only");
				
				bean.setFacevalueincrores(investmentinwords.toString());
				
				
			}
			QuotationRequestService.getInstance().getBankDetails(bean.getAccountNo());
			letterNo=QuotationRequestService.getInstance().saveQuotationRequest(bean);
			bean.setLetterNo(letterNo);
			
			
			
			/*LetterFormates formates=new LetterFormates();
			
			ReportList.add(bean);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(ReportList);
			reportMap.put("imagepath",getServlet().getServletContext().getRealPath("/images/aimslogo.gif"));
			System.out.println("the path is......++++"+bean.getLetterNo());
			String file=getServlet().getServletContext().getRealPath("/letters/quotationLetterFormate.jrxml");
			System.out.println("the path is ssfdfd"+file);
		//	String file1=getServlet().getServletContext().getRealPath("/letters/quotationLetterFormate.jrxml");
			InputStream input = new FileInputStream(new File(file));
			JasperDesign design = JRXmlLoader.load(input);
			System.out.println("after loading the xml file...");
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint print = JasperFillManager.fillReport(report,reportMap,ds);
			log.info("this is ending...of jasper...");
			formates.generateHtmlOutput(print,request,response);*/
			session.setAttribute("arrangerArray",arrangerArray);
			session.setAttribute("letterformate",bean);
			
			
			 //Getting Dibursment Details......
		  //  map= reportservice.DisbursmentsReport(bean);
		//	ReportList=(List)map.get("DisbeanList");
			//String file =getServlet().getServletContext().getRealPath("/reports/BeneficiaryDisbursmentReport.jasper");
		//	JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(ReportList);			 
		  //  JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
		   
			 //Setting Report Image path

			 	// reportMap.put("imagepath",getServlet().getServletContext().getRealPath("/")+"images/Logo_Reports.png");

			// ReportsUtil reportsUtil =  new ReportsUtil();
			 
			 //Generarte PDF Report
			// reportsUtil.generatePDFOutput(response, reportMap,file,ds,"Beneficiary-Disbursementreport");
		
			 
			
		}
		catch(Exception e){
			System.out.println("..................."+e.getMessage());
		 	ActionMessages errors = new ActionMessages(); 
			errors.add("quotation",new ActionMessage("quotationrequest.Errors", e.getMessage()));
			saveErrors(session,errors);
			 request.setAttribute("approvedList",approvedList);
			 request.setAttribute("marketTypeList",marketTypeList);
			 request.setAttribute("arrangersList",arrangersList);
			 request.setAttribute("proposalRefList",proposalRefList);
			 request.setAttribute("purchasePriseList",purchasePriseList);
			 request.setAttribute("banks",BankList);
			 request.setAttribute("periodList",periodList);
			forward = mapping.getInputForward();
	  }
		return forward;
	}
	public ActionForward searchQuotationRequests(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		DynaValidatorForm quotation = (DynaValidatorForm)af;
		  QuotationRequestBean bean = new QuotationRequestBean();
		  List quotationRequestList=null;
		  List trustList=null;
		  List securityList=null;
		ActionForward forward =am.findForward("showquotationrequestsearch");
	 	ActionMessages errors = new ActionMessages(); 
		try{
			
			  bean.setProposalRefNo(StringUtility.checknull(quotation.getString("proposalRefNo")));
			  bean.setLetterNo(StringUtility.checknull(quotation.getString("letterNo")));
			  bean.setTrustType(StringUtility.checknull(quotation.getString("trustType")));
			  bean.setSecurityCategory(StringUtility.checknull(quotation.getString("securityCategory")));
			  quotationRequestList=QuotationRequestService.getInstance().searchQuotationRequests(bean);
			  securityList=SecurityCategoryService.getInstance().getSecurityCategories();
			  trustList=TrustTypeService.getInstance().getTrustTypes();
			  req.setAttribute("quotationrequestList",quotationRequestList);
			  req.setAttribute("trustList",trustList);
			  req.setAttribute("securityList",securityList);
			  if(StringUtility.checknull(req.getParameter("Success")).equals("S"))
			   {
				   
				   errors.add("success",new ActionMessage("quotation.Success","Record Saved Successfully"));
	    			saveErrors(req, errors);
			   }
			 
			  }catch(Exception e){
				
					errors.add("quotation",new ActionMessage("quotationrequest.Errors", e.getMessage()));
					saveErrors(req,errors);
					forward = am.getInputForward();
			  }
			  if(req.getParameter("error")!=null)
			  {
				  errors.add("quotation",new ActionMessage("quotationrequest.Errors",req.getParameter("error")));
	  			  saveErrors(req, errors);
			  }
		return forward;
	}
	public ActionForward showEditQuotationRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		DynaValidatorForm quotation = (DynaValidatorForm)form;
		List approvedList=null;
		List marketTypeList=null;
		List arrangersList=null;
		List purchasePriseList=null;
		List periodList=null;
		  QuotationRequestBean bean = new QuotationRequestBean();
		  ActionForward forward =mapping.findForward("showquotationrequestedit");
		  String letterno=request.getParameter("letterno");
		  ActionMessages errors = new ActionMessages();
		  try{
			  bean=QuotationRequestService.getInstance().findQuotationRequest(letterno);
			  approvedList=QuotationRequestService.getInstance().getApprovedList();
			 marketTypeList=QuotationRequestService.getInstance().getMarketTypeList();
			 arrangersList=QuotationRequestService.getInstance().getArrangers();
			 purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
			 periodList=QuotationRequestService.getInstance().getPeriods();
			 request.setAttribute("approvedList",approvedList);
			 request.setAttribute("marketTypeList",marketTypeList);
			 request.setAttribute("arrangersList",arrangersList);
			 request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			 request.setAttribute("quotationRequest",bean);
			 request.setAttribute("purchasePriseList",purchasePriseList);
			 request.setAttribute("periodList",periodList);
			 
			  
		  }
		  catch (Exception e) {
				log.error("QuotationRequestAction:showEditQuotationRequest:Exception:"+e.getMessage());
				
				errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
						.getMessage()));
				saveErrors(request, errors);
				
				forward = mapping.getInputForward();
			}
		 
			return forward;
	}
	public ActionForward updateQuotationRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		DynaValidatorForm quotation = (DynaValidatorForm) form;
		QuotationRequestBean bean = new QuotationRequestBean(request);
		SQLHelper sq=null;
		List approvedList=null;
		List marketTypeList=null;
		List proposalRefList=null;
		List arrangersList=null;
		List purchasePriseList=null;
		List BankList=null;
		List periodList=null;
		ActionForward forward =mapping.findForward("quotationletterformate");
		HttpSession session = request.getSession();
		double surplusamt=0.0;
		double result=0.0;
		double investamt=0.0;
		StringBuffer investmentinwords=new StringBuffer();
		String investmentAmount="";
		double investresult=0.0;
		String concrores="";
		double minQuantumamt=0.0;
		double quantumresult=0.0;
		String conQuantumCrores="";
		String conInWords="";
		StringBuffer totconWords=new StringBuffer();
		totconWords.append("(");
		
		try {
			approvedList=QuotationRequestService.getInstance().getApprovedList();
			 marketTypeList=QuotationRequestService.getInstance().getMarketTypeList();
			 proposalRefList=InvestmentProposalService.getInstance().getProposal();
			 purchasePriseList=QuotationService.getInstance().getPurchasePriceOption();
			 arrangersList=QuotationRequestService.getInstance().getArrangers();
			 BankList=BankMasterService.getInstance().search(new BankMasterInfo());
			 periodList=QuotationRequestService.getInstance().getPeriods();
			sq=new SQLHelper();
			bean.setLetterNo(StringUtility.checknull(quotation.getString("letterNo")));
			bean.setQuotationRequestCd(StringUtility.checknull(quotation.getString("quotationRequestCd")));
			bean.setProposalRefNo(StringUtility.checknull(quotation.getString("proposalRefNo")));
			bean.setTrustType(StringUtility.checknull(quotation.getString("trustType")));
			bean.setSecurityCategory(StringUtility.checknull(quotation.getString("securityCategory")));
			bean.setSecurityName(StringUtility.checknull(quotation.getString("securityName")));
			bean.setSurplusFund(StringUtility.checknull(quotation.getString("surplusFund")));
			surplusamt=Double.parseDouble(quotation.getString("surplusFund"));
			conInWords=sq.ConvertInWords(surplusamt);
			totconWords.append(conInWords+" "+"Only  )" );
			result=surplusamt/10000000.00;
			concrores=result+"";
			bean.setConInWords(totconWords.toString());
			
			log.info("the converting crores amount...."+result);
			bean.setConcrores(concrores);
			bean.setMarketType(StringUtility.checknull(quotation.getString("marketType")));
			bean.setMarketTypedef(StringUtility.checknull(quotation.getString("marketTypedef")));
			bean.setArrangers(quotation.getStrings("arrangers"));
			bean.setApproved(StringUtility.checknull(quotation.getString("approved")));
			bean.setRemarks(StringUtility.checknull(quotation.getString("remarks")));
			bean.setFormateRemarks(StringUtility.checknull(quotation.getString("formateRemarks")));
			bean.setModeOfPaymentRemarks(StringUtility.checknull(quotation.getString("modeOfPaymentRemarks")));
			bean.setPaymentThroughRemarks(StringUtility.checknull(quotation.getString("paymentThroughRemarks")));
			
			
			bean.setMinimumQuantum(StringUtility.checknull(quotation.getString("minimumQuantum")));
			minQuantumamt=Double.parseDouble(quotation.getString("minimumQuantum"));
			quantumresult=minQuantumamt/10000000.00;
			conQuantumCrores=quantumresult+"";
			bean.setConQuantumCrores(conQuantumCrores);
			bean.setQuotationAddress(StringUtility.checknull(quotation.getString("quotationAddress")));
			bean.setQuotationDate(StringUtility.checknull(quotation.getString("quotationDate")));
			bean.setQuotationTime(StringUtility.checknull(quotation.getString("quotationTime")));
			bean.setValidDate(StringUtility.checknull(quotation.getString("validDate")));
			bean.setValidTime(StringUtility.checknull(quotation.getString("validTime")));
			bean.setOpenDate(StringUtility.checknull(quotation.getString("openDate")));
			bean.setOpenTime(StringUtility.checknull(quotation.getString("openTime")));
			bean.setNameoftheTender(StringUtility.checknull(quotation.getString("nameoftheTender")));
			bean.setAddressoftheTender(StringUtility.checknull(quotation.getString("addressoftheTender")));
			bean.setTelephoneNo(StringUtility.checknull(quotation.getString("telephoneNo")));
			bean.setFaxNo(StringUtility.checknull(quotation.getString("faxNo")));
			bean.setContactPerson(StringUtility.checknull(quotation.getString("contactPerson")));
			bean.setStatus(StringUtility.checknull(quotation.getString("status")));
			bean.setDeliveryRequestedin(StringUtility.checknull(quotation.getString("deliveryRequestedin")));
			bean.setAccountNo(StringUtility.checknull(quotation.getString("accountNo")));
			bean.setFaceValue(StringUtility.checknull(quotation.getString("faceValue")));
			bean.setNumberOfUnits(StringUtility.checknull(quotation.getString("numberOfUnits")));
			bean.setInvestmentFaceValue(StringUtility.checknull(quotation.getString("investmentFaceValue")));
			bean.setPurchaseOption(StringUtility.checknull(quotation.getString("purchaseOption")));
			bean.setPremiumPaid(StringUtility.checknull(quotation.getString("premiumPaid")));
			bean.setPurchasePrice(StringUtility.checknull(quotation.getString("purchasePrice")));
			bean.setMaturityDate(StringUtility.checknull(quotation.getString("maturityDate")));
			bean.setSecurityFullName(StringUtility.checknull(quotation.getString("securityFullName")));
			bean.setFromPeriod(StringUtility.checknull(quotation.getString("fromPeriod")));
			bean.setToPeriod(StringUtility.checknull(quotation.getString("toPeriod")));
			String userid=QuotationRequestService.getInstance().getLoginId(StringUtility.checknull(quotation.getString("letterNo")));
			UserBean signPath=UserDAO.getInstance().getUser(userid);
			log.info("the login userid is"+bean.getLoginUserId());
			UserBean user = UserDAO.getInstance().getUser("23207");
			UserBean user1=UserDAO.getInstance().getUser("23208");
			String path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			
			String path1 = request.getSession().getServletContext().getRealPath("/");
			path1 +="uploads/SIGNATORY/"+user1.getEsignatoryName();
			
			String path2=request.getSession().getServletContext().getRealPath("/");
			path2 +="uploads/SIGNATORY/"+signPath.getEsignatoryName();
			String designation=QuotationRequestService.getInstance().getDesignation(userid);
			UserDAO.getInstance().getImage(path,user.getUserName());
			UserDAO.getInstance().getImage(path1,user1.getUserName());
			UserDAO.getInstance().getImage(path2,signPath.getUserName());
			
			bean.setSignPathLeft("uploads/SIGNATORY/"+user.getEsignatoryName());
			bean.setSingPathRight("uploads/SIGNATORY/"+user1.getEsignatoryName());
			bean.setSignPath("uploads/SIGNATORY/"+signPath.getEsignatoryName());
			bean.setSignPathLeftName(user.getDisplayName());
			bean.setSingPathRightName(user1.getDisplayName());
			bean.setSignPathName(signPath.getDisplayName());
			
			
			ArrangersBean arrangerbean=null;
			String arrangersarray[]=bean.getArrangers();
			ArrayList arrangerArray=new ArrayList();
			for(int i=0; i<arrangersarray.length; i++)
			{
				 arrangerbean=new ArrangersBean();
				 arrangerbean=ArrangersService.getInstance().findArrangers(arrangersarray[i]);
				 arrangerArray.add(arrangerbean);
			}
			if(!StringUtility.checknull(quotation.getString("investmentFaceValue")).equals(""))
			{
				investamt=Double.parseDouble(quotation.getString("investmentFaceValue"));
				investresult=investamt/10000000.00;
				investmentAmount=investresult+"";
				investmentinwords.append("RS. ");
				investmentinwords.append(investmentAmount );
				investmentinwords.append(" Crores Only");
				
				bean.setFacevalueincrores(investmentinwords.toString());
				
				
			}
		QuotationRequestService.getInstance().getBankDetails(bean.getAccountNo());
		
			QuotationRequestService.getInstance().editQuotationRequest(bean);
			log.info("the market type is...."+bean.getMarketType());
			log.info("the csglaccountno..."+bean.getCsglAccountNo());
			log.info("the action designation is...."+designation);
			bean.setDesignation(designation);
			session.setAttribute("arrangerArray",arrangerArray);	
			session.setAttribute("letterformate",bean);
			
			
		} catch (Exception e) {
			log.error("quotationAction:quotationRequest:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			 request.setAttribute("approvedList",approvedList);
			 request.setAttribute("marketTypeList",marketTypeList);
			 request.setAttribute("arrangersList",arrangersList);
			 request.setAttribute("proposalRefList",proposalRefList);
			 request.setAttribute("purchasePriseList",purchasePriseList);
			 request.setAttribute("banks",BankList);
			request.setAttribute("quotationRequest", bean);
			request.setAttribute("periodList",periodList);
			forward = mapping.getInputForward();
		}
		return forward;
	}
	public ActionForward deleteQuotationRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ActionForward forward =mapping.findForward("quotationrequestsearch");
		
		
		try {
			String letternos = request.getParameter("deleteall");
			QuotationRequestService.getInstance().deleteQuotationRequest(letternos);
		} catch (Exception e) {
			log.error("QuotationRequestAction:deleteQuotationRequest:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			StringBuffer newPath =
		        new StringBuffer(mapping.findForward("quotationrequestsearch").getPath());
		    newPath.append("&error="+e.getMessage());
		    log.info("the path is"+newPath.toString());
		    forward= (new ActionForward(newPath.toString(), true));
			
			
			
		}
		
		return forward;
		
	}
	public ActionForward quotationletterFormate(ActionMapping mapping,ActionForm form, HttpServletRequest req,HttpServletResponse res)
	{
		List ReportList=null;
		Map reportMap=new HashMap();
		ResourceBundle bundle=null;
		ResourceBundle bundle1=null;
		String commonAddress=null;
		String authId=null;
		String host=null;
		String authPwd=null;
		LetterFormates formates=null;
		DynaValidatorForm quotation = (DynaValidatorForm) form;
		QuotationRequestBean bean = new QuotationRequestBean(req);
		ActionForward forward =mapping.findForward("quotationrequestsearch");
		try{
			String userid=QuotationRequestService.getInstance().getLoginId(StringUtility.checknull(quotation.getString("letterNo")));
			UserBean signPath=UserDAO.getInstance().getUser(userid);
			String designation=QuotationRequestService.getInstance().getDesignation(userid);
			log.info("the designation is...."+designation);
			String path2=req.getSession().getServletContext().getRealPath("/");
			path2 +="uploads/SIGNATORY/"+signPath.getEsignatoryName();
			UserDAO.getInstance().getImage(path2,signPath.getUserName());
			
			bean.setLetterNo(StringUtility.checknull(quotation.getString("letterNo")));
			bean.setAttachmenttoSelected(StringUtility.checknull(quotation.getString("attachmenttoSelected")));
			bundle=ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
			commonAddress=bundle.getString("quotation.jasperaddress");
			bean.setQuotationAddress(commonAddress);
			bean= QuotationRequestService.getInstance().verifyArrangersMailing(bean);
			
			bundle1=ResourceBundle.getBundle("com.epis.resource.MAIL");
			bean.setCommonAddress(commonAddress);
			host=bundle1.getString("mail.host");
			authId=bundle1.getString("mail.authid");
			authPwd=bundle1.getString("mail.authpwd");
			log.info("the common address is "+commonAddress);
			
			bean.setHost(host);
			bean.setAuthId(authId);
			bean.setAuthPwd(authPwd);
			ServletContext sc=getServlet().getServletContext();
			log.info("the path is..."+sc.getRealPath("/uploads/SIGNATORY/"+signPath.getEsignatoryName()));
			bean.setSignPath(sc.getRealPath("/uploads/SIGNATORY/"+signPath.getEsignatoryName()));
			bean.setSignPathName(signPath.getDisplayName());
			bean.setDesignation(designation);
			log.info("the designation is"+bean.getDesignation());
			ArrangersBean arranger=null;
			if(bean.getAttachmenttoSelected().equals("Y"))
			{
			List arrangers=QuotationRequestService.getInstance().getSelectedArrangers(bean.getLetterNo());
			for(int k=0; k<arrangers.size(); k++)
			{
				ReportList=new ArrayList();
				arranger=(ArrangersBean)arrangers.get(k);
				if(arranger!=null)
				{
					bean.setArrangerName(arranger.getArrangerName());
					bean.setArrangerAddress(arranger.getRegOffAddr());
			 formates=new LetterFormates();
			
			ReportList.add(bean);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(ReportList);
			reportMap.put("imagepath",getServlet().getServletContext().getRealPath("/images/aimslogo.gif"));
			String file=getServlet().getServletContext().getRealPath("/letters/quotationLetterFormate.jasper");
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
		    JasperPrint jp1 = JasperFillManager.fillReport(file, reportMap, ds);
		    
		    List pages = new ArrayList(jp1.getPages());
			
			int i=1;
			for(int count=1;count<pages.size()-1;count++){
			
			jp1.addPage(i, (JRPrintPage)pages.get(count-1));
			i++;
			}
			String path=getServlet().getServletContext().getRealPath("/quotationletters/"+bean.getCreatedDate()+bean.getQuotationCd());
			log.info("the path is...."+path);
			//formates.generateWordFile(jp1, path);
			formates.genPdfFile(jp1, path);
			formates.sendArrangersMail(bean,path,arranger.getEmail());
				}
			}
			}

		//	String file1=getServlet().getServletContext().getRealPath("/letters/quotationLetterFormate.jrxml");
			/*InputStream input = new FileInputStream(new File(file));
			JasperDesign design = JRXmlLoader.load(input);
			System.out.println("after loading the xml file...");
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint print = JasperFillManager.fillReport(report,reportMap,ds);
			log.info("this is ending...of jasper...");
			formates.generateHtmlOutput(print,req,res);*/
			log.info("the letterno is...."+bean.getLetterNo());
		}
		catch(Exception e){
			log.error("quotationAction:quotationRequest:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = mapping.getInputForward();
			
		}
		
		return forward;
	}
	public ActionForward generateQuotationRequestReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	{
		ActionForward forward =am.findForward("quotationreportformate");
		QuotationRequestBean bean = new QuotationRequestBean(req);
		
		try{
			
			bean.setLetterNo(req.getParameter("letterNo"));
			
			UserBean user = UserDAO.getInstance().getUser("23207");
			UserBean user1=UserDAO.getInstance().getUser("23208");
			String userid=QuotationRequestService.getInstance().getLoginId(StringUtility.checknull(req.getParameter("letterNo")));
			log.info("the user id is..."+userid);
			UserBean signPath=UserDAO.getInstance().getUser(userid);
			log.info("the login id is...."+bean.getLoginUserId());
			String path = req.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			
			
			String path1 = req.getSession().getServletContext().getRealPath("/");
			path1 +="uploads/SIGNATORY/"+user1.getEsignatoryName();
			
			String path2=req.getSession().getServletContext().getRealPath("/");
			path2 +="uploads/SIGNATORY/"+signPath.getEsignatoryName();
			log.info("the path is..."+path2);
			
			UserDAO.getInstance().getImage(path,user.getUserName());
			UserDAO.getInstance().getImage(path1,user1.getUserName());
			
			bean.setSignPathLeft("uploads/SIGNATORY/"+user.getEsignatoryName());
			bean.setSingPathRight("uploads/SIGNATORY/"+user1.getEsignatoryName());
			
			log.info("the signpath is..."+bean.getSignPath());
			String designation=QuotationRequestService.getInstance().getDesignation(userid);
			log.info("the designation is"+designation);
			bean.setSignPathLeftName(user.getDisplayName());
			bean.setSingPathRightName(user1.getDisplayName());
			
			
			bean=QuotationRequestService.getInstance().generateQuotationRequestReport(bean);
			UserDAO.getInstance().getImage(path2,signPath.getUserName());
			bean.setSignPath("uploads/SIGNATORY/"+signPath.getEsignatoryName());
			bean.setSignPathName(signPath.getDisplayName());
						
			ArrangersBean arrangerbean=null;
			String arrangersarray[]=bean.getArrangers();
			ArrayList arrangerArray=new ArrayList();
			for(int i=0; i<arrangersarray.length; i++)
			{
				 arrangerbean=new ArrangersBean();
				 arrangerbean=ArrangersService.getInstance().findArrangers(arrangersarray[i]);
				 arrangerArray.add(arrangerbean);
			}
			bean.setDesignation(designation);
			req.setAttribute("arrangerArray",arrangerArray);
			req.setAttribute("letterformate",bean);
			
		}
		catch(Exception e)
		{
			log.error("quotationAction:quotationRequest:Exception:"+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add("quotation", new ActionMessage("quotationrequest.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = am.getInputForward();
		}
		return forward;
	}

}
