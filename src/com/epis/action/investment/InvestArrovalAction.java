package com.epis.action.investment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbook.PartyInfo;

import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.bean.investment.QuotationAppBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.UserDAO;
import com.epis.services.cashbook.PartyService;
import com.epis.services.investment.ConfirmationFromCompanyService;
import com.epis.services.investment.InvestApprovalService;
import com.epis.services.investment.InvestmentProposalService;
import com.epis.services.investment.InvestmentRegisterService;
import com.epis.services.investment.SecurityCategoryService;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class InvestArrovalAction extends DispatchAction{
	Log log=new Log(InvestArrovalAction.class);
	 public ActionForward showInvestApprovalAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServiceNotAvailableException, EPISException{
		 List approvalLettersList=InvestApprovalService.getInstance().getAppLetterNos();
		 request.setAttribute("approvalLettersList",approvalLettersList);
	       return mapping.findForward("showinvestapprovalnew");
	  }
	 
	 public ActionForward searchInvestRegister(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
	 {
	 	ActionForward forward=am.findForward("showinvestmentRegistersearch");
	 	DynaValidatorForm register=(DynaValidatorForm)af;
	 	InvestmentRegisterBean bean=new InvestmentRegisterBean();
	 	List investmentRegisterList=null;
	 	List regLettersList=null;
	 	String mode=StringUtility.checknull(req.getParameter("mode"));
	 	log.info("searchinvestregister bean mode...."+mode);
	 	try{
	 		log.info("this is starting of search resultss....");
	 		bean.setLetterNo(StringUtility.checknull(register.getString("letterNo")));
	 		bean.setRefNo(StringUtility.checknull(register.getString("refNo")));
	 		bean.setSecurityCategory(StringUtility.checknull(register.getString("securityCategory")));
	 		bean.setMode(mode);
	 		investmentRegisterList=InvestmentRegisterService.getInstance().searchInvestmentRegister(bean);
	 		req.setAttribute("investmentRegisterList",investmentRegisterList);
	 		req.setAttribute("categoryRecords", SecurityCategoryService.getInstance()
					.getSecurityCategories());
	 		log.info("this is ending of search resultss....");
	 	}
	 	catch(Exception e)
	 	{
	 		ActionMessages errors = new ActionMessages();
	 		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
	 		req.setAttribute("regLettersList",regLettersList);
	 		saveErrors(req,errors);
	 		forward = am.getInputForward();
	 	}
	 	return forward;
	 }
	 public ActionForward showInvestApproval(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 DynaValidatorForm compstmt = (DynaValidatorForm)form;
		 HttpSession session = request.getSession();
		  ActionForward forward= null ; 
		  String letterno=StringUtility.checknull(compstmt.getString("letterNo"));
		  
		  String approvedate=StringUtility.checknull(compstmt.getString("opendate"));
		  String approvestatus=StringUtility.checknull(compstmt.getString("status"));
		  String remarks=StringUtility.checknull(compstmt.getString("remarks"));
		  QuotationBean bean = new QuotationBean(request);
		  try{
			  bean.setLetterNo(letterno);
			  bean.setOpendate(approvedate);
			  bean.setStatus(approvestatus);
			  bean.setRemarks(remarks);
		  HashMap arranger= InvestApprovalService.getInstance().showInvestApproval(bean);
		  String hashkey = "";
        HashMap security = null;
        
        List arrangers = new ArrayList();
        List securities = new ArrayList();
        SortedSet arrsort= new TreeSet(arranger.keySet());
        Iterator iterator = (arrsort).iterator();
			while(iterator.hasNext()) {
           hashkey = iterator.next().toString();
           arrangers.add(hashkey);
           security =(HashMap)arranger.get(hashkey);
            SortedSet secsort= new TreeSet(security.keySet());
           Iterator iter = secsort.iterator();
           while(iter.hasNext()){
           		hashkey = (String)iter.next();
			   		if(!securities.contains(hashkey)){
			   			securities.add(hashkey);
			   		}
           }
         }
         int arrSize = arrangers.size()+3;
         int secSize = securities.size()+1;
         String[][] comp = new String[secSize][arrSize];
         comp[0][0] = "Sr. No.";
         comp[0][1] = "Security Name";
         comp[0][arrSize-1] = "Remarks";
         for(int i=2;i<arrSize-1;i++){
         	comp[0][i]=(String)arrangers.get(i-2);
         }  
         for(int i=1;i<secSize;i++){
         	comp[i][0]= String.valueOf(i);
         	comp[i][1]=(String)securities.get(i-1);
         }      
         Map sec = null;  
         QuotationBean qbean = null;             
         for(int i=2;i<arrSize-1;i++){
         	for(int j=1;j<secSize;j++){
         		if(arranger.containsKey(comp[0][i])){
         			sec = (HashMap)arranger.get(comp[0][i]);
         			
         			if(sec.containsKey(comp[j][1])){
         				qbean = (QuotationBean)sec.get(comp[j][1]);
         				comp[j][i]= qbean.getPurchasePrice()+"/"+qbean.getYtm()+"<input type=checkbox  name='shortlist' id='"+comp[0][i]+"'   value='"+comp[j][1]+"' >";
         				if(qbean.getRemarks() != null)
         				comp[j][arrSize-1]= qbean.getRemarks();    
         			}	
         		}
         		
         	}
         	comp[0][i]=comp[0][i]+"<input type=radio name='apparrangers' value='"+comp[0][i]+"' onClick='disablingShortlisted();'>";
         }
         session.setAttribute("arrSize",new Integer(arrSize));
         session.setAttribute("secSize",new Integer(secSize));
         session.setAttribute("compArray",comp);
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		
			  StringBuffer buffer = new StringBuffer(mapping.findForward("showinvestapproval").getPath());
			  buffer.append("?letterNo="+letterno);
			  forward= (new ActionForward(buffer.toString(), true)); 
		  
	 return forward; 
		 }
	 public ActionForward getApprovalReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	 {
		 DynaValidatorForm compstmt = (DynaValidatorForm)form;
		 HttpSession session = request.getSession();
		  ActionForward forward= mapping.findForward("approvalReport");; 
		  String quotationCd=StringUtility.checknull(compstmt.getString("quotationCd"));
		  QuotationBean bean = new QuotationBean(request);
		  QuotationAppBean appBean=null;
		  try{
			  bean.setQuotationCd(quotationCd);
			  
		  HashMap arranger= InvestApprovalService.getInstance().getApprovalReport(bean);
		  String hashkey = "";
        HashMap security = null;
        
        List arrangers = new ArrayList();
        List securities = new ArrayList();
        SortedSet arrsort= new TreeSet(arranger.keySet());
        Iterator iterator = (arrsort).iterator();
			while(iterator.hasNext()) {
           hashkey = iterator.next().toString();
           arrangers.add(hashkey);
           security =(HashMap)arranger.get(hashkey);
            SortedSet secsort= new TreeSet(security.keySet());
           Iterator iter = secsort.iterator();
           while(iter.hasNext()){
           		hashkey = (String)iter.next();
			   		if(!securities.contains(hashkey)){
			   			securities.add(hashkey);
			   		}
           }
         }
         int arrSize = arrangers.size()+3;
         int secSize = securities.size()+1;
         String[][] comp = new String[secSize][arrSize];
         comp[0][0] = "Sr. No.";
         comp[0][1] = "Security Name";
         comp[0][arrSize-1] = "Remarks";
         for(int i=2;i<arrSize-1;i++){
         	comp[0][i]=(String)arrangers.get(i-2);
         }  
         for(int i=1;i<secSize;i++){
         	comp[i][0]= String.valueOf(i);
         	comp[i][1]=(String)securities.get(i-1);
         }      
         Map sec = null;  
         QuotationBean qbean = null;             
         for(int i=2;i<arrSize-1;i++){
         	for(int j=1;j<secSize;j++){
         		if(arranger.containsKey(comp[0][i])){
         			sec = (HashMap)arranger.get(comp[0][i]);
         			
         			if(sec.containsKey(comp[j][1])){
         				qbean = (QuotationBean)sec.get(comp[j][1]);
         				comp[j][i]= qbean.getPurchasePrice()+"/"+qbean.getYtm()+"("+qbean.getMode()+")";
         				if(qbean.getRemarks() != null)
         				comp[j][arrSize-1]= qbean.getRemarks();    
         			}	
         		}
         		
         	}
         	comp[0][i]=comp[0][i];
         }
         
         bean = InvestApprovalService.getInstance().findapprovalconfirmation(quotationCd);
			LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
			Set set = approvals.keySet();
			Iterator iter = set.iterator();
			int count=0;
			while(iter.hasNext()){
				String level = (String)iter.next();
				appBean = (QuotationAppBean)approvals.get(level);
				if(StringUtility.checknull(appBean.getApproved()).equals("A"))
					count++;
				approvals.put(level,getUserDetails(appBean,request));
			}				
			bean.setApprovals(approvals);
			bean.setAppCount(count+"");
			
         
         session.setAttribute("arrSize",new Integer(arrSize));
         session.setAttribute("secSize",new Integer(secSize));
         session.setAttribute("compArray",comp);
         request.setAttribute("quotationbean",bean);
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		  
		  return forward;
	 }
	 
	 
	 public ActionForward approvalinvestApproval(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
		{
			ActionForward forward=am.findForward("approvalinvest");
			log.info("this is investment appproval coming.....page...");
			String quotationCd=StringUtility.checknull(req.getParameter("quotationCd"));
			String updatedFlag=StringUtility.checknull(req.getParameter("updatedFlag"));
			
			
			 QuotationBean bean = new QuotationBean(req);
			 QuotationAppBean appbean=null;
			 HttpSession session = req.getSession();
			
			String refNo="";
			try {
				
				
				req.setAttribute("edit", StringUtility.checknull(req.getParameter("edit")));
				
				String loginUserID = bean.getLoginUserId();
				
				bean.setQuotationCd(quotationCd);
				  
				  HashMap arranger= InvestApprovalService.getInstance().getApprovalReport(bean);
				  String hashkey = "";
		        HashMap security = null;
		        
		        List arrangers = new ArrayList();
		        List securities = new ArrayList();
		        SortedSet arrsort= new TreeSet(arranger.keySet());
		        Iterator iterator = (arrsort).iterator();
					while(iterator.hasNext()) {
		           hashkey = iterator.next().toString();
		           arrangers.add(hashkey);
		           security =(HashMap)arranger.get(hashkey);
		            SortedSet secsort= new TreeSet(security.keySet());
		           Iterator iter = secsort.iterator();
		           while(iter.hasNext()){
		           		hashkey = (String)iter.next();
					   		if(!securities.contains(hashkey)){
					   			securities.add(hashkey);
					   		}
		           }
		         }
		         int arrSize = arrangers.size()+3;
		         int secSize = securities.size()+1;
		         String[][] comp = new String[secSize][arrSize];
		         comp[0][0] = "Sr. No.";
		         comp[0][1] = "Security Name";
		         comp[0][arrSize-1] = "Remarks";
		         for(int i=2;i<arrSize-1;i++){
		         	comp[0][i]=(String)arrangers.get(i-2);
		         }  
		         for(int i=1;i<secSize;i++){
		         	comp[i][0]= String.valueOf(i);
		         	comp[i][1]=(String)securities.get(i-1);
		         }      
		         Map sec = null;  
		         QuotationBean qbean = null;             
		         for(int i=2;i<arrSize-1;i++){
		         	for(int j=1;j<secSize;j++){
		         		if(arranger.containsKey(comp[0][i])){
		         			sec = (HashMap)arranger.get(comp[0][i]);
		         			
		         			if(sec.containsKey(comp[j][1])){
		         				qbean = (QuotationBean)sec.get(comp[j][1]);
		         				comp[j][i]= qbean.getPurchasePrice()+"/"+qbean.getYtm()+"("+qbean.getMode()+")";
		         				if(qbean.getRemarks() != null)
		         				comp[j][arrSize-1]= qbean.getRemarks();    
		         			}	
		         		}
		         		
		         	}
		         	comp[0][i]=comp[0][i];
		         }
				
				
				bean = InvestApprovalService.getInstance().findapprovalconfirmation(quotationCd);
				log.info("the first method is.....ending...");	
				LinkedHashMap approvals = (LinkedHashMap)bean.getApprovals();			
				
							
				Set set = approvals.keySet();
				Iterator iter = set.iterator();
				while(iter.hasNext()){
					String level = (String)iter.next();
					appbean = (QuotationAppBean)approvals.get(level);
					approvals.put(level,getUserDetails(appbean,req));
				}				
				
				appbean = new QuotationAppBean();
				appbean.setApprovedBy(loginUserID);			
				approvals.put("8",getUserDetails(appbean,req));
				bean.setUpdatedFlag(updatedFlag);
				
				bean.setApprovals(approvals);
				session.setAttribute("arrSize",new Integer(arrSize));
		         session.setAttribute("secSize",new Integer(secSize));
		         session.setAttribute("compArray",comp);
				req.setAttribute("quotationbean", bean);
				log.info("the updated flag is..."+bean.getUpdatedFlag());
			} catch (Exception e) {
				log.info("the errors...is"+e.toString());
				ActionMessages errors = new ActionMessages();
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				
				saveErrors(req, errors);
				forward = am.getInputForward();
			}
			
			
			return forward;
		}
	 
	 private QuotationAppBean getUserDetails(QuotationAppBean appBean,HttpServletRequest req){
			try {
				if(!"".equals(StringUtility.checknull(appBean.getApprovedBy()))){
					SQLHelper sql = new SQLHelper();
					UserBean user = UserDAO.getInstance().getUser(appBean.getApprovedBy());
					String path = req.getSession().getServletContext().getRealPath("/");
					path +="uploads/SIGNATORY/"+user.getEsignatoryName();
					
					UserDAO.getInstance().getImage(path,user.getUserName());
					
					appBean.setSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
					appBean.setDisplayName(user.getDisplayName());
					appBean.setDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (EPISException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return appBean;
		}
	 
	 public ActionForward approvalUpdate(ActionMapping am, ActionForm af,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
			log.info("the method is calling....");
			DynaValidatorForm proposal = (DynaValidatorForm) af;
			ActionForward forward = am.findForward("investapprovalsearch");
			QuotationAppBean bean = new QuotationAppBean(req);
			
			log.info("the edit....."+proposal.getString("edit"));
			try {
				String updatedFlag = StringUtility.checknull(proposal.getString("updatedFlag"));
				log.info("the updated flag..."+updatedFlag);
				 if("Y".equals(StringUtility.checknull(proposal.getString("edit")))){
					 updatedFlag = String.valueOf(Integer.parseInt(StringUtility.checknull(proposal.getString("updatedFlag"))));
				 }
				 bean.setLetterNo(StringUtility.checknull(proposal.getString("quotationCd")));
				 bean.setDate(StringUtility.checknull(proposal.getString("approveDate")));
				 bean.setRemarks(StringUtility.checknull(proposal.getString("approvalRemarks")));
				 bean.setApprovalLevel(updatedFlag);
				 log.info("the appproval....."+proposal.getString("approvalRemarks")+"----"+proposal.getString("quotationCd"));
				 InvestApprovalService.getInstance().approvalUpdate(bean);
				StringBuffer newPath =
			        new StringBuffer(am.findForward("investapprovalsearch").getPath());
			    
			     forward= (new ActionForward(newPath.toString(), true));

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(req, errors);
				forward = am.getInputForward();
			}
			return forward;
		}

	 
	 
	 public ActionForward saveInvestApproval(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 QuotationBean bean = new QuotationBean(request);
		 UtilityBean util = null;
		  ActionForward forward=mapping.findForward("investapprovalsearch"); 
		  try{
		  String arrshort = request.getParameter("shortarrangers");
		  log.info("the securitynames are"+arrshort);
		  log.info("the arrangers are"+StringUtility.checknull(request.getParameter("approvedarranger")));
		  StringTokenizer tokens = new StringTokenizer(arrshort,",");
		  ArrayList array = new ArrayList();
			while(tokens.hasMoreTokens()){
				util = new UtilityBean();
				String securities = (String)tokens.nextElement();
				util.setValue(securities);
				array.add(util);
				}
			bean.setShortListArrangres(array);
			bean.setArranger(StringUtility.checknull(request.getParameter("approvedarranger")));
			bean.setLetterNo(StringUtility.checknull(request.getParameter("letterNo")));
			InvestApprovalService.getInstance().saveInvestApproval(bean);
		 
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		 return forward; 
		 }
	 public ActionForward searchInvestApproval(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("showinvestapprovalsearch"); 
		 try{
			   List shortList =null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   QuotationBean bean = new QuotationBean();
			   bean.setLetterNo(StringUtility.checknull(dyna.getString("letterNo")));
			   bean.setStatus(StringUtility.checknull(dyna.getString("status")));
			   shortList = InvestApprovalService.getInstance().searchInvestApproval(bean);
			   request.setAttribute("appList",shortList);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		   }
	  	   return forward;
		 }
	 
	 public ActionForward showRegisterLetters(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("showinvestregister"); 
		 try{
			 List regLettersList=InvestApprovalService.getInstance().getApprovalLetters();
			 request.setAttribute("regLettersList",regLettersList);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		   }
	  	   return forward;
		 }
	
	 public ActionForward InvestRegister(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("addinvestregister"); 
		 try{
			 DynaValidatorForm dyna = (DynaValidatorForm)form;
			 QuotationBean regbean =new QuotationBean() ;
			   regbean = InvestApprovalService.getInstance().getInvestRegister(dyna.getString("letterNo"));
			   request.setAttribute("regBean",regbean);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
				 
		   }  
	  	   return forward;
		 }
	 
		 public ActionForward addInvestRegister(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
				
				DynaValidatorForm register = (DynaValidatorForm) form;
				QuotationBean bean = new QuotationBean(request);
				ActionForward forward = mapping.findForward("showinvestregister");
				List regLettersList=null;
				
				try {
							
					BeanUtils.copyProperties(bean,register) ;
					
					InvestApprovalService.getInstance().addInvestRegister(bean);
					if("N".equals(request.getParameter("exist"))){
						PartyInfo party = new PartyInfo(request);
						party.setPartyName(bean.getSecurityName());
						
						try{
						PartyService.getInstance().add(party);
						}
						catch(Exception e)
						{
							log.info("InvestmentApprovalAction:addInvestRegister:Error:"+e.getMessage() );
						}
						
					}
					
					 regLettersList=InvestApprovalService.getInstance().getApprovalLetters();
				
					 
				} catch (Exception e) {
					log.error("QuotationAction:addQuotation:Exception:"+e.getMessage());
					ActionMessages errors = new ActionMessages();
					errors.add("quotation", new ActionMessage("quotation.Errors", e.getMessage()));
					saveErrors(request, errors);
					request.setAttribute("regLettersList",regLettersList);
					forward = mapping.getInputForward();
				}
				return forward;
			 } 

			 public ActionForward getCashBookSecurities(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
				  
				 StringBuffer sb1 = new StringBuffer();
				 PrintWriter out = null;
				 try{
					 String sname = "";
					   List cashbookSecurityList= InvestApprovalService.getInstance().getCashBookSecurities();
					   if(cashbookSecurityList != null){
							 sb1.append("<SecurityDetails>");
						     int size = cashbookSecurityList.size();
							for (int i = 0; cashbookSecurityList != null && i < size; i++) {			
								
								QuotationBean sinfo = (QuotationBean) cashbookSecurityList.get(i);
								sb1.append("<SecurityDetail>");
								sb1.append("<getSecurityName>");
								if (sinfo.getSecurityName() != null)
									sname = sinfo.getSecurityName();
								sb1.append(sname);
								sb1.append("</getSecurityName>");
								sb1.append("</SecurityDetail>");
							}
							sb1.append("</SecurityDetails>");
						}
						response.setContentType("text/xml");
						response.setHeader("Pragma","no-cache");//for HTTP1.0
						response.setHeader("cache-control","no-cache"); //for HTTP/1.1.	
						
						out = response.getWriter();
						out.print(sb1.toString());
				   }catch(Exception e){
						   	e.printStackTrace();
					   }
			  	   return null;
				 }
			 
			 
			 public ActionForward generateInvestRegisterReport(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res)
			 {
			 	ActionForward forward=am.findForward("registerReport");
			 	DynaValidatorForm register=(DynaValidatorForm)af;
			 	InvestmentRegisterBean bean=new InvestmentRegisterBean();
			 	try{
			 		bean.setRegisterCd(StringUtility.checknull(register.getString("registerCd")));
			 		bean=InvestApprovalService.getInstance().generateInvestmentRegisterReport(bean);
			 		req.setAttribute("registerBean",bean);
			 	}
			 	catch(Exception e)
			 	{
			 		ActionMessages errors = new ActionMessages();
			 		errors.add("investmentregister",new ActionMessage("investmentregister.Errors", e.getMessage()));
			 		saveErrors(req,errors);
			 		forward = am.getInputForward();
			 	}
			 	return forward;
			 }
}
