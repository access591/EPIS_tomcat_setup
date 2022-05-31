<!--
/*
  * File       : NoteSheetSearch.jsp
  * Date       : 21/10/2009
  * Author     : Suneetha V 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.CommonUtil"%>
<%@page import="com.epis.bean.advances.*"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
		
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>

<html>
  <head>
   
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
	<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
	<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
	
    
		<script type="text/javascript"><!--		
		var appArr1='',appArr2='';
		function noteSheetReport(nssanctionNo,pensionNo,status,frmName)
		{
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
						
			
			if(status=='N'){			
				alert(pensionNo+' Should be approved.');
			}else{
		   var url="<%=basePath%>loadNoteSheet.do?method=finalSettlementArrearReport&frmPensionNo="+pensionNo+"&frmSanctionNo="+nssanctionNo+"&frm_name="+frmName;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
						
		    
		}	
		function sanctionOrder(nssanctionNo,pensionNo,status,verifiedby,flag,sanctionDate,frmName)
		{				    
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
						
			if(status=='N'){			
				alert(pensionNo+' Should be approved.');
			}else{		    
			
				if(verifiedby!='FINANCE,SRMGRREC,DGMREC,APPROVED'){			
					alert(pensionNo+' is not finally approved.');
				}else{			
				
				if((flag=='sanctionOrderReport1') && (sanctionDate=='---')){				
				 alert("PF ID "+pensionNo +" Final Settlement is approved.But Sanction Date is not available");
				 return false;				
				}else{								
			    var url="<%=basePath%>loadNoteSheet.do?method=sanctionOrder&frmPensionNo="+pensionNo+"&frmSanctionNo="+nssanctionNo+"&frmFlag="+flag+"&frmSanctionDate="+sanctionDate+"&frm_name="+frmName;
				wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		   	 	winOpened = true;
				wind1.window.focus();				
				}
				
				}
			}
		}		
		function search(frmName)
		{		   
		    if (!convert_date(document.forms[0].sanctiondt))
			{
				document.forms[0].sanctiondt.focus();
				return false;
			}
			
			if (!convert_date(document.forms[0].paymentdt))
			{
				document.forms[0].paymentdt.focus();
				return false;
			}
			
			var url="<%=basePath%>noteSheetSearch.do?method=searchFinalSettlementArrearProcess&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
		}
		
		function resetMaster(frmName){			
		
		    document.forms[0].pfid.value="";
		    document.forms[0].employeeName.value="";
		    document.forms[0].trust.value="";
		    document.forms[0].nssanctionno.value="";
		    document.forms[0].sanctiondt.value="";
		    document.forms[0].paymentdt.value="";
		    document.forms[0].seperationreason.value="";
		     document.forms[0].arreartype.value="";
			url="<%=basePath%>noteSheetSearch.do?method=loadFinalSettlementArrearProcessSearch&frm_name="+frmName;
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		
		function editFinalSettlement(pensionNo,nssanctionno,verifiedBy,frmName)
		{			 
		 // alert(frmName); 
		 // alert(verifiedBy); 
			  		if((frmName=='FSArrearProcess') && (verifiedBy=='FINANCE,SRMGRREC')){
			          	alert('Application No '+nssanctionno+' already approved by Senior Department. You can not edit the details.');
		   	        }else  if(((frmName=='FSArrearProcess') || (frmName=='FSArrearRecommendation')) && (verifiedBy=='FINANCE,SRMGRREC,DGMREC')){
			       		alert('Application No '+nssanctionno+' already approved by DGM. You can not edit the details.');
			        }else  if((frmName=='FSArrearProcess'  || frmName=='FSArrearRecommendation' || frmName=='FSArrearVerification') && (verifiedBy=='FINANCE,SRMGRREC,DGMREC,APPROVED')){
			          	alert('Application No '+nssanctionno+' already approved . You can not edit the details.');
		       		}else{ 
			var url;	
			url="<%=basePath%>loadNoteSheet.do?method=editNoteSheetArrear&pensionno="+pensionNo+"&sanctionno="+nssanctionno+"&frm_name="+frmName;
		 	
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();				
			
			}
		}		
			
		function editCheckedRecords(frmName){		
		     
		     //alert(frmName);
		      var count=0;
		      var str=new Array();
		      var pensionno,pfid,nssanctionno,sanctionDt,verifiedBy;
		        
		if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	   
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          pensionno=str[0];
			          nssanctionno=str[1];
			          verifiedBy=str[3];
			          sanctionDt=str[4];
			          
			        }
			       			        
			        if(sanctionDt!='---'){
			        alert('Final Settlement process is completed.');
			        
			        }else
			         editFinalSettlement(pensionno,nssanctionno,verifiedBy,frmName);
		     	
		}		
				
		function viewReport(frmName){			
		      
		      var count=0;
		      var str=new Array();
		      var pensionno,nssanctionno,status;
		    
		  if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	  
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		     
		       for(var j=0;j<str.length;j++){			      
			          pensionno=str[0];
			          nssanctionno=str[1];	
			          status=str[2];		          
			        }
			        noteSheetReport(nssanctionno,pensionno,status,frmName);
		}
		
		
			function viewSanctionOrder(frmName){		
		      
		      var count=0;
		      var str=new Array();
		      var pensionno,nssanctionno,status,verifiedby,sanctionDate;
		      var flag='sanctionOrderReport1';
		    
		    if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		     
		     		        
			        for(var j=0;j<str.length;j++){			      
			          pensionno=str[0];
			          nssanctionno=str[1];
			          status=str[2];			
			          verifiedby=str[3];	     
			          sanctionDate=str[4];     
			        }
			        
			     
			        sanctionOrder(nssanctionno,pensionno,status,verifiedby,flag,sanctionDate,frmName);
		}
		
		function sanctionReport(){
		
		  	  var count=0;
		      var str=new Array();
		      var pensionno,nssanctionno,status,verifiedby,sanctionDate;		   
		      var flag='sanctionOrderReport2';
		     
		
		 
		  if(document.forms[0].chkbox.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chkbox.length;i++){
		      	    
			      if (document.forms[0].chkbox[i].checked){
			        count++;
			        str=document.forms[0].chkbox[i].value.split(':');
			        
			      }
		      }		      
		     }else{
		     
		     	 	if(document.forms[0].chkbox.checked){
		     	 	
				 		str=document.forms[0].chkbox.value.split(':');
				 		
				 	}
			}
		   
		     for(var j=0;j<str.length;j++){			      
		          pensionno=str[0];
		          nssanctionno=str[1];
		          status=str[2];			
		          verifiedby=str[3];	       
		          sanctionDate=str[4];
		          
		          //alert(sanctionDate);
		          
		          if(j==0)      
		          {
			          if(sanctionDate=='---'){			          		     
				          				         
				           if(appArr1!='')
				           appArr1=appArr1+','+nssanctionno;
				           else
				          appArr1=nssanctionno;
				           
			          }else{			          
			         
			            if(appArr2!='')
				           appArr2=appArr2+','+nssanctionno;
				           else
				           appArr2=nssanctionno;
				           
				       
			          }
		          }		          	               
		          
			 }					 	     
		 
		}
		
		
		
		function editBankDetails(frmName){		
		     
		     //alert(frmName);
		      var count=0;
		      var str=new Array();
		      var pensionno,pfid,nssanctionno,empName,seperationreason,sanctionDt;
		        
		if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){		      	 
			      if (document.forms[0].chk[i].checked==true){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			        
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          pensionno=str[0];
			          nssanctionno=str[1];
			           empName=str[5];
			           seperationreason=str[6];
			           sanctionDt=str[4];
			        }			       
			        //alert(verifiedBy);			        
			         
			          
			    /*if(seperationreason=='Death'){
			  BankDeatils(pensionno,nssanctionno,empName,seperationreason,frmName);
			   }else{
			   alert('This Screen is for Death Cases Only');
			   return false;
			   } 	 */
			    
			 if(sanctionDt!='---'){
			        alert('Final Settlement Arrear process is completed.');
			        return false;
			   }else{
			           BankDeatils(pensionno,nssanctionno,empName,seperationreason,frmName);
			           }
		     	
		}
		function  BankDeatils(pensionNo,nssanctionno,empName,seperationreason,frmName)
		{						  
					var url;	
					var arrearType="ARREAR";
					url="<%=basePath%>editNoteSheet.do?method=loadNomineeBankDetails&pensionno="+pensionNo+"&transid="+nssanctionno+"&empName="+empName+"&srcFrmName="+frmName+"&frm_ArrearType="+arrearType+"&seperationreason="+seperationreason;
				  // alert(url);
				 	document.forms[0].action=url;
					document.forms[0].method="post";
					document.forms[0].submit();				
			         
		}		
		function editPostingDetails(frmName){		
		     
		     //alert(frmName);
		      var count=0;
		      var str=new Array();
		      var pensionno,pfid,nssanctionno,empName,seperationreason,sanctionDt;
		        
		if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){		      	 
			      if (document.forms[0].chk[i].checked==true){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			        
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          pensionno=str[0];
			          nssanctionno=str[1];
			           empName=str[5];
			           seperationreason=str[6];
			            sanctionDt=str[4];
			        }			       
			        //alert(verifiedBy);			        
			         
			          
			 if(sanctionDt!='---'){
			        alert('Final Settlement Arrear process is completed.');
			        return false;
			   }else{
			 PostingDeatils(pensionno,nssanctionno,empName,frmName);
			 }   	 
			          
		     	
		}
		function  PostingDeatils(pensionNo,nssanctionno,empName,frmName)
		{						  
					var url;	
					var arrearType="ARREAR";
					url="<%=basePath%>editNoteSheet.do?method=loadEmployeePostingDetails&pensionno="+pensionNo+"&nssanctionno="+nssanctionno+"&srcFrmName="+frmName+"&frm_ArrearType="+arrearType;
				 // alert(url);
				 	document.forms[0].action=url;
					document.forms[0].method="post";
					document.forms[0].submit();				
			         
		}		
		
		 
		
		
		function update(frmName){	  
		 
		  //alert('without sanction date'+appArr1);	 
		  //alert('with sanction date'+appArr2);
		  
		   var count=0;  
		   var verifiedby,pensionNo;
		   var nssanctionNo,pensionNo,advanceType,status,verifiedBy;
		  var nsstrip='',nsstripwithsanctiondt='',notVerifiedStrip='';
		   
		    if(document.forms[0].chkbox.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chkbox.length;i++){
		      	    
			      if (document.forms[0].chkbox[i].checked){
			        count++;
			        str=document.forms[0].chkbox[i].value.split(':');
			         
			       if(str[4]=='---'){
			      if(str[3]=='FINANCE,SRMGRREC,DGMREC,APPROVED'){
			      
			      if(nsstrip!=''){
			       nsstrip=nsstrip+','+str[1];
			      }else{
			      nsstrip=str[1];
			      }
			      
			      }else{			      
			       if(notVerifiedStrip!=''){
			       notVerifiedStrip=notVerifiedStrip+','+str[1];
			      }else{
			      notVerifiedStrip=str[1];
			      }
			      }
			      }else{
			      
			      if(nsstripwithsanctiondt!=''){
			       nsstripwithsanctiondt=nsstripwithsanctiondt+','+str[1];
			      }else{
			      nsstripwithsanctiondt=str[1];
			      }
			      
			      }
			        //  alert('--nsstrip---'+nsstrip);
			        //  alert('---nsstripwithsanctiondt--'+nsstripwithsanctiondt);
			         //alert('---notVerifiedStrip--'+notVerifiedStrip);
			      }
			      
			      
		      }
		      		      
		      if(count==0){
		      	alert('User Should select Final Settlement Arrear request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chkbox.checked){
				 		str=document.forms[0].chkbox.value.split(':');
				 		
				 		if(str[4]=='---'){
				   if(str[3]=='FINANCE,SRMGRREC,DGMREC,APPROVED'){
			      if(nsstrip!=''){
			       nsstrip=nsstrip+','+str[1];
			      }else{
			      nsstrip=str[1];
			      }
			      }else{
			      			      
			       if(notVerifiedStrip!=''){
			       notVerifiedStrip=notVerifiedStrip+','+str[1];
			      }else{
			      notVerifiedStrip=str[1];
			      }			      
			      }
			      }else{
			      
			      if(nsstripwithsanctiondt!=''){
			       nsstripwithsanctiondt=nsstripwithsanctiondt+','+str[1];
			      }else{
			      nsstripwithsanctiondt=str[1];
			      }
			      
			      }
				 		
				 	}else{
				 		   alert('User Should select Final Settlement Arrear request');		     
		       				return false;
				 	}
		     }
		     	     
		        for(var j=0;j<str.length;j++){			         
			          pensionNo=str[0];
			          nssanctionNo=str[1];
			          verifiedBy=str[2];			         
			          status=str[3];
			          
			        }	     
		  
		   
		  
		  if(nsstripwithsanctiondt!=''){	
		  
		    if(nsstrip!=''){ 
		   if(notVerifiedStrip!=''){
		   var chkstatus= confirm("Records with Application Id "+nsstripwithsanctiondt+ " contains Sanction Date And Records with Application Id "+notVerifiedStrip+ " not finally approved  These records are not processed. Do you want to continue?");
			}else{
			var chkstatus= confirm("Records with Application Id "+nsstripwithsanctiondt+ " contains Sanction Date. These records are not processed. Do you want to continue?");
			}
			 if (chkstatus== true)
			 {			  
				var url="<%=basePath%>loadNoteSheet.do?method=updateNoteSheetSanctionDate&frmSanctionNo="+nsstrip+"&frm_name="+frmName;
			   	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }
			 
			 }else{
			 if(notVerifiedStrip!=''){
			 alert('Sanction Date is already assigned for records with Application Id '+nsstripwithsanctiondt+' And '+notVerifiedStrip+' not finally approved ');
			 }else{
			   alert('Sanction Date is already assigned for records with Application Id '+nsstripwithsanctiondt);
			 }
			} 
		  }else{
		  		 
		  		 if(nsstrip!=''){  
		    if(notVerifiedStrip!=''){
		   var chkstatus= confirm("Records with Application Id "+notVerifiedStrip+ " not finally approved . These records are not processed. Do you want to continue?");
			
			 if (chkstatus== true)
			 {			  
				var url="<%=basePath%>loadNoteSheet.do?method=updateNoteSheetSanctionDate&frmSanctionNo="+nsstrip+"&frm_name="+frmName;
			   	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }
			 
			}else{
			var url="<%=basePath%>loadNoteSheet.do?method=updateNoteSheetSanctionDate&frmSanctionNo="+nsstrip+"&frm_name="+frmName;
			   	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();	
			
			}    
		  }else{
		   if(notVerifiedStrip!=''){
		  alert("Records with Application Id "+notVerifiedStrip+ " not finally approved ");
			}
		  
		  } 
		  
		  }
		  
		   
			
		}		
		--></script>
		
  </head>
  <html:form method="post" action="/noteSheetSearch.do?method=loadNoteSheetSearchForm">
  
 
    
     <logic:equal name="advanceBean" property="frmName" value="FSArrearProcess">     
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearprocesssearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>
     
     <logic:equal name="advanceBean" property="frmName" value="FSArrearRecommendation">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearrecommendationsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>     
     
     <logic:equal name="advanceBean" property="frmName" value="FSArrearVerification">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearverificationsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>   
     
     <logic:equal name="advanceBean" property="frmName" value="FSArrearApproval">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearapprovalsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>        
     
     <logic:equal name="advanceBean" property="frmName" value="FSArrearApproved">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearapprovedsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>     
    

		<tr>
			<td height="15%">
				<table align="center">
					<tr>
						<td class="tableTextBold">Application No:</td>
						<td><html:text property="nssanctionno" maxlength="25" styleClass="TextField"/></td>						
					
					
						<td class="tableTextBold">PF ID:   </td>
						<td><html:text property="pfid" styleClass="TextField"/></td>	
										
					</tr>
					<tr>			
					<td class="tableTextBold">Employee Name:</td>
						<td>
						<html:text property="employeeName" styleClass="TextField"/>
						</td>	
									
										
						<td class="tableTextBold">Trust:</td>
						<td><html:text property="trust" styleClass="TextField"/></td>	
					</tr>
					<tr>
						<td class="tableTextBold">Payment Date:</td>
						<td>
						<html:text property="paymentdt" maxlength="12" styleClass="TextField"/>&nbsp;&nbsp;&nbsp;			
						<a href="javascript:show_calendar('forms[0].paymentdt');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>			
						</td>	
						<td class="tableTextBold">Sanction Date:</td>
						<td>
						<html:text property="sanctiondt" maxlength="12" styleClass="TextField"/>&nbsp;&nbsp;&nbsp;			
						<a href="javascript:show_calendar('forms[0].sanctiondt');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>			
						</td>						
					</tr>
					<tr>
						<td class="tableTextBold">Seperation Reason:</td>
						<td>
						<html:select property="seperationreason"  styleClass="TextField">
						<html:option value="">Select One</html:option>
					    <html:option value="Retirement">Retirement</html:option>
					    <html:option value="VRS">VRS</html:option>
						<html:option value="Death">Death</html:option>
						<html:option value="Resignation">Resignation</html:option>
						<html:option value="Termination">Termination</html:option>
						<html:option value="EarlyPensions">Option for Early Pensions</html:option>
						<html:option value="Others">Others</html:option>
						</html:select>
						</td>						
					
						<td class="tableTextBold">Arrear Type:</td>
						<td>
						<html:select property="arreartype"  styleClass="TextField">
						<html:option value="">Select One</html:option>
					    <html:option value="payscalerevision">Revision of Pay Scale</html:option>
					    <html:option value="revisedinterestrate">Revised Interest Rate</html:option>	
					    <html:option value="dearnesspay">Dearness Pay Arrear(DP)</html:option>			
						</html:select>
						</td>					
					</tr>					
					
					<tr>						
						<td colspan="4" align="center">
								<input type="button" class="butt" value="Search" class="btn" onclick="javascript:search('<bean:write name="advanceBean" property="frmName"/>')" >
								<input type="button" class="butt" value="Reset" onclick="javascript:resetMaster('<bean:write name="advanceBean" property="frmName"/>')" class="btn">
								<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
						</td>						
					</tr>
			</table>
		</td>
	</tr>
</table>
			
		
			
				
<%=ScreenUtilities.searchSeperator()%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">	
  <tr>
                   <td  height="29" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>  
							
							<logic:notEqual name="advanceBean" property="frmName" value="FSArrearApproved">
							<td>
								<input type="button" value="Approve" class="btn" onclick="javascript:editCheckedRecords('<bean:write name="advanceBean" property="frmName"/>')">
							</td>						
							</logic:notEqual>
							
							<logic:equal name="advanceBean" property="frmName" value="FSArrearRecommendation">
							<td>
								<input type="button" value="Edit Posting Details" class="btn" onclick="javascript:editPostingDetails('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							<td>
								<input type="button" value="Edit Bank Details" class="btn" onclick="javascript:editBankDetails('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							<td>
								<input type="button" value="Update Sanction Date" class="btn" onclick="javascript:update('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							 
							</logic:equal>
							
							<!--<logic:equal name="advanceBean" property="frmName" value="FSArrearVerification">							
							<td>
								<input type="button" value="Edit Bank Details" class="btn" onclick="javascript:editBankDetails('<bean:write name="advanceBean" property="frmName"/>')">
							</td>							 
							</logic:equal>
								
							--><logic:notEqual name="advanceBean" property="frmName" value="FSArrearApproval">	
							<td>
								<input type="button" value="Print" class="btn" onclick="javascript:viewReport('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							</logic:notEqual>
						
						    <logic:equal name="advanceBean" property="frmName" value="FSArrearRecommendation">
							<td>
								<input type="button" value="Print-Sanction Order" class="btn" onclick="javascript:viewSanctionOrder('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							</logic:equal>
							
							<td>&nbsp;</td>
							
							
                        </tr>
                        </table></td>
                         
                        </tr>	
						<logic:present name="searchlist">
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="noteSheetList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.searchlist" requestURI="./noteSheetSearch.do?method=searchNoteSheet" >											
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="NoteSheetSearch.xls" />
    								<display:setProperty name="export.pdf.filename" value="NoteSheetSearch.pdf" />
    								<display:setProperty name="export.rtf.filename" value="NoteSheetSearch.rtf" />
    								
    								<display:column  sortable="true" headerClass="GridHBg" class="GridLCells"><input type="radio" name="chk" value="<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getNssanctionno()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctiondt()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getEmployeeName()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSeperationreason()%>"/></display:column>
									<display:column  sortable="true"  property="nssanctionno" sortable="true" title="Application No" headerClass="GridLTHCells" class="GridLTCells"/>
									<display:column  sortable="true"  property="pfid" sortable="true" title="PF ID" headerClass="GridLTHCells" class="GridLTCells"/>
    								<display:column  sortable="true"  property="employeeName"  headerClass="GridLTHCells" class="GridLTCells" title="Employee Name" />
    								<display:column  sortable="true"  property="designation" headerClass="GridLTHCells" class="GridLTCells" title="Designation"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column  sortable="true"  property="station"  headerClass="GridLTHCells" class="GridLTCells" title="Station"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								<display:column  sortable="true"  property="seperationreason" headerClass="GridLTHCells" class="GridLTCells" title="Seperation Reason"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column  sortable="true"  property="arreartype" headerClass="GridLTHCells" class="GridLTCells"  title="Arrear Type"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="netcontribution" headerClass="GridLTHCells" class="GridLTCells" title="Net contribution"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column  sortable="true"  property="paymentdt" headerClass="GridLTHCells" class="GridLTCells"  title="Payment Date"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="sanctiondt"  title="Sanction Date" headerClass="GridLTHCells" class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="verifiedBy"  title="Status" headerClass="GridLTHCells" class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator"/>  
									
									<logic:equal name="advanceBean" property="frmName" value="FSArrearRecommendation">
									<display:column sortable="true" headerClass="GridHBg" class="GridLCells"><input type="checkbox" name="chkbox" onclick="sanctionReport()" value="<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getNssanctionno()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctiondt()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>"/></display:column>													
									</logic:equal>
									</display:table>
								</td>
							</tr>
						</logic:present>
					 </table><%=ScreenUtilities.searchFooter()%>
  </html:form>
</html>
