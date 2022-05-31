package com.epis.action.investment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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

import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.services.investment.ComparativeService;
import com.epis.services.investment.QuotationRequestService;
import com.epis.services.investment.YTMVerificationService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ComparativeAction extends DispatchAction{
	Log log=new Log(ComparativeAction.class);
	 public ActionForward showComparativeAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServiceNotAvailableException, EPISException{
		 List complettersList=ComparativeService.getInstance().getComparativeLetterNos();
		 request.setAttribute("complettersList",complettersList);
	       return mapping.findForward("showcomparisionnew");
	  }
	 public ActionForward showCompatativeStmt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 DynaValidatorForm compstmt = (DynaValidatorForm)form;
		 QuotationBean bean = new QuotationBean(request);
		 HttpSession session = request.getSession();
		  ActionForward forward= null ; 
		  String letterno=StringUtility.checknull(compstmt.getString("letterNo"));
		  String opendate=StringUtility.checknull(compstmt.getString("opendate"));
		  String mode=StringUtility.checknull(request.getParameter("mode"));
		  
		  String remarks=StringUtility.checknull(compstmt.getString("remarks"));
		  try{
		  bean.setMode(mode);	
		  bean.setLetterNo(letterno);
		  bean.setOpendate(opendate);
		  bean.setStatus("S");
		  bean.setRemarks(remarks);
		  HashMap arranger= ComparativeService.getInstance().saveComparativeStatus(bean);
		  bean=ComparativeService.getInstance().getMarketType(letterno);
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
           comp[0][1] = "Particular";
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
           				comp[j][i]= qbean.getPurchasePrice()+"/"+qbean.getYtm(); 
           				if(qbean.getRemarks() != null)
           				comp[j][arrSize-1]= qbean.getRemarks();    
           			}	
           		}
           	}
           }
           session.setAttribute("arrSize",new Integer(arrSize));
           session.setAttribute("secSize",new Integer(secSize));
           session.setAttribute("compArray",comp);
           session.setAttribute("quotationbean",bean);
		  }catch(Exception e){
			 	ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		  }
		  if(!mode.equals("")&&mode.equals("report")){
		  StringBuffer buffer = new StringBuffer(mapping.findForward("showreport").getPath());
		  buffer.append("?letterNo="+letterno);
		  buffer.append("&&opendate="+opendate);
		  forward= (new ActionForward(buffer.toString(), true));
		  }else
		  {
			  StringBuffer buffer = new StringBuffer(mapping.findForward("showcomstmt").getPath());
			  buffer.append("?letterNo="+letterno);
			  buffer.append("&&opendate="+opendate);
			  forward= (new ActionForward(buffer.toString(), true)); 
		  }
		  String compFlag = StringUtility.checknull(request
					.getParameter("cmpFlag"));
		  if ("Y".equals(compFlag)) {
			  ComparativeService.getInstance().updateCompFlag(bean);
				forward = mapping.findForward("comparisionsearch");
			}
		 return forward; 
		 }
	 public ActionForward searchComparative(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 ActionForward forward=mapping.findForward("showcomparisionsearch"); 
		 try{
			   List comparativeList =null;
			   DynaValidatorForm dyna = (DynaValidatorForm)form;
			   QuotationBean bean = new QuotationBean();
			   bean.setLetterNo(StringUtility.checknull(dyna.getString("letterNo")));
			   bean.setStatus(StringUtility.checknull(dyna.getString("status")));
			   comparativeList = ComparativeService.getInstance().searchComparative(bean);
			   request.setAttribute("comparativeList",comparativeList);
		   }catch(Exception e){
			   ActionMessages errors = new ActionMessages(); 
				errors.add("trust",new ActionMessage("trust.Errors", e.getMessage()));
				saveErrors(request,errors);
				forward = mapping.getInputForward();
		   }
	  	   return forward;
		 }
	
}
