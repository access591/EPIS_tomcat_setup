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
	
    
		<script type="text/javascript">		
		 var appArr1='',appArr2='';
		function noteSheetReport(nssanctionNo,pensionNo,status,frmName)
		{
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
						
			
			if(status=='N'){			
				alert(pensionNo+' Should be approved.');
			}else{
		   var url="<%=basePath%>loadNoteSheet.do?method=noteSheetReport&frmPensionNo="+pensionNo+"&frmSanctionNo="+nssanctionNo+"&frm_name="+frmName;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
						
		    
		}	
		function sanctionOrder(nssanctionno,pensionNo,status,verifiedby,flag,sanctionDate,frmName)
		{				    
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
						
			if(status=='N'){			
				alert(pensionNo+' Should be approved.');
			}else{
			
				if(verifiedby!='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'){			
					alert(pensionNo+' is not finally approved.');
				}else{			
				
				if((flag=='sanctionOrderReport1') && (sanctionDate=='---')){
				
				 alert("PF ID "+pensionNo +" Final Settlement is approved.But Sanction Date is not available");
				 return false;
				
				}else{
				
			    var url="<%=basePath%>loadNoteSheet.do?method=sanctionOrder&frmPensionNo="+pensionNo+"&frmSanctionNo="+nssanctionno+"&frmFlag="+flag+"&frmSanctionDate="+sanctionDate+"&frm_namae="+frmName;
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
			
			var url="<%=basePath%>noteSheetSearch.do?method=searchNoteSheet&frm_name="+frmName;
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
			url="<%=basePath%>noteSheetSearch.do?method=loadNoteSheetSearchForm&frm_name="+frmName;
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		
		function editFinalSettlement(pensionNo,nssanctionno,frmName,verifiedBy)
		{						
					  
			  		if((frmName=='FSFormII') && (verifiedBy=='PERSONNEL,FINANCE,SRMGRREC')){
			         alert("Final Settlement Request is approved by Senior Manager");
			        }else  if(((frmName=='FSFormII') || (frmName=='FSFormIII')) && (verifiedBy=='PERSONNEL,FINANCE,SRMGRREC,DGMREC')){
			         alert("Final Settlement Request is approved by DGM");
			        }else  if((frmName=='FSFormII'  || frmName=='FSFormIII' || frmName=='FSFormIV') && (verifiedBy=='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED')){
			         alert("Final Settlement Request is approved");
			        }else{
					var url;	
					url="<%=basePath%>loadNoteSheet.do?method=editNoteSheet&pensionno="+pensionNo+"&nssanctionno="+nssanctionno+"&frm_name="+frmName;
				 	
				 	document.forms[0].action=url;
					document.forms[0].method="post";
					document.forms[0].submit();				
			        }		    
			  
		}		
			
		function editCheckedRecords(frmName){		
		     
		     //alert(frmName);
		      var count=0;
		      var str=new Array();
		      var pensionno,pfid,nssanctionno,sanctionDt,verifiedBy,FSDate;
		        
		if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){		      	 
			      if (document.forms[0].chk[i].checked==true){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			        
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          pensionno=str[0];
			          nssanctionno=str[1];
			          sanctionDt=str[4];
			          verifiedBy=str[5];
			          FSDate=str[8];
			        }
			       
			        // alert(FSDate);
			        
			        if(sanctionDt!='---'){
			        alert('Final Settlement process is completed.');
			        
			        }else{
			        if(frmName=="FSFormIII"){
			         if(FSDate!='---'){
			         editFinalSettlement(pensionno,nssanctionno,frmName,verifiedBy);
			         }else{
			         alert("Please Add/Update FinalSettlement Date Thru Edit Settlement Information Screen due to PFCard Interest Process");
			         return false;
			         }
			         }else{
			          editFinalSettlement(pensionno,nssanctionno,frmName,verifiedBy);
			         }
		     	}
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
		      	alert('User Should select Final Settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement request');		     
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
		      	alert('User Should select Final Settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement request');		     
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
		      var pensionno,sanctionno,status,verifiedby,sanctionDate;		   
		      var flag='sanctionOrderReport2';
		     
		 //alert(document.forms[0].chkbox.length);
		 
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
		          sanctionno=str[1];
		          status=str[2];			
		          verifiedby=str[3];	       
		          sanctionDate=str[4];
		          
		          //alert(sanctionDate);
		          
		          if(j==0)      
		          {
			          if(sanctionDate=='---'){			          		     
				          				         
				           if(appArr1!='')
				           appArr1=appArr1+','+sanctionno;
				           else
				          appArr1=sanctionno;
				           
			          }else{			          
			         
			            if(appArr2!='')
				           appArr2=appArr2+','+sanctionno;
				           else
				           appArr2=sanctionno;
				           
				       
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
		      	alert('User Should select Final Settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select Final Settlement request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          pensionno=str[0];
			          nssanctionno=str[1];
			          sanctionDt =str[4];
			           empName=str[6];
			           seperationreason=str[7];
			        }			       
			        //alert(verifiedBy);
			        			        
			        /* if(seperationreason=='Death'){
			   BankDeatils(pensionno,nssanctionno,empName,frmName);
			   }else{
			   alert('This Screen is for Death Cases Only');
			   return false;
			   } 	 */
			   
			 if(sanctionDt!='---'){
			        alert('Final Settlement process is completed.');
			        return false;
			   }else{
			    BankDeatils(pensionno,nssanctionno,empName,seperationreason,frmName);
			    }
		}
		function  BankDeatils(pensionNo,nssanctionno,empName,seperationreason,frmName)
		{						  
					var url;	
					var arrearType="NON-ARREAR";
					url="<%=basePath%>editNoteSheet.do?method=loadNomineeBankDetails&pensionno="+pensionNo+"&transid="+nssanctionno+"&srcFrmName="+frmName+"&frm_ArrearType="+arrearType+"&seperationreason="+seperationreason+"&empName="+empName;
				 	// alert(url);
				 	document.forms[0].action=url;
					document.forms[0].method="post";
					document.forms[0].submit();				
			         
		}		
		
	 
		function update(frmName){ 
		   var count=0;  
		   var nssanctionNo,pensionNo,advanceType,status,verifiedBy;
		   var nsstrip='',nsstripwithsanctiondt='',notVerifiedStrip='';
		 
		 
		    if(document.forms[0].chkbox.length!=undefined){		 
		      for(var i=0;i<document.forms[0].chkbox.length;i++){
		      	    
			      if (document.forms[0].chkbox[i].checked==true){
			        count++;
			        str=document.forms[0].chkbox[i].value.split(':');
			        
			      if(str[4]=='---'){
			      if(str[3]=='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'){
			      
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
			        // alert('---notVerifiedStrip--'+notVerifiedStrip);
			       
			      }
		      }
		      if(count==0){
		      	alert('User Should select Final Settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chkbox.checked){
				 		str=document.forms[0].chkbox.value.split(':');
				  if(str[4]=='---'){
				   if(str[3]=='PERSONNEL,FINANCE,SRMGRREC,DGMREC,APPROVED'){
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
			         // alert(nsstrip);
			         // alert(nsstripwithsanctiondt);
				 	}else{
				 		   alert('User Should select Final Settlement request');		     
		       				return false;
				 	}
		     }
		      
		      for(var j=0;j<str.length;j++){			         
			          pensionNo=str[0];
			          nssanctionNo=str[1];
			          verifiedBy=str[2];			         
			          status=str[3];
			          
			        }
			        //alert('pensionNo -----'+pensionNo);
			       // alert('status -----'+status);
			        //alert('verifiedBy -----'+verifiedBy);
			 		  
			 		  
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
		</script>
		
  </head>
  <html:form method="post" action="/noteSheetSearch.do?method=loadNoteSheetSearchForm">
    
     <logic:equal name="advanceBean" property="frmName" value="FSFormII">     
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementverificationIIsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>
     
     <logic:equal name="advanceBean" property="frmName" value="FSFormIII">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementverificationIIIsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>     
     
     <logic:equal name="advanceBean" property="frmName" value="FSFormIV">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementverificationIVsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>   
     
     <logic:equal name="advanceBean" property="frmName" value="FSApproval">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementapprovalsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>   
          
     <logic:equal name="advanceBean" property="frmName" value="FSApproved">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementapprovedsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
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
						<td class="tableTextBold">Seperation Reason</td>
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
					
						<td>&nbsp;</td>
						<td>&nbsp;</td>					
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
							<logic:notEqual name="advanceBean" property="frmName" value="FSApproved">
							<td>
								<input type="button" value="Approve" class="btn" onclick="javascript:editCheckedRecords('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
													
							</logic:notEqual>
							 
							<logic:equal name="advanceBean" property="frmName" value="FSFormIII">
							
							<td>
								<input type="button" value="Edit Bank Details" class="btn" onclick="javascript:editBankDetails('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							
							<td>
								<input type="button" value="Update Sanction Date" class="btn" onclick="javascript:update('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							</logic:equal>
							
							<!--<logic:equal name="advanceBean" property="frmName" value="FSFormIV">							
							<td>
								<input type="button" value="Edit Bank Details" class="btn" onclick="javascript:editBankDetails('<bean:write name="advanceBean" property="frmName"/>')">
							</td>							 
							</logic:equal>
								
							-->
							<logic:notEqual name="advanceBean" property="frmName" value="FSApproval">	
							<td>
								<input type="button" value="Print" class="btn" onclick="javascript:viewReport('<bean:write name="advanceBean" property="frmName"/>')">
							</td>
							</logic:notEqual>
						
						    <logic:equal name="advanceBean" property="frmName" value="FSFormIII">
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
    								<display:column  sortable="true" headerClass="GridHBg" class="GridLCells"><input type="radio" name="chk" value="<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getNssanctionno()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctiondt()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getEmployeeName()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSeperationreason()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getFinalSettlementDate()%>"/></display:column>
									<display:column  sortable="true"  property="nssanctionno" sortable="true" title="Application No" headerClass="GridLTHCells" class="GridLTCells"/>
									<display:column  sortable="true"  property="pfid" sortable="true" title="PF ID" headerClass="GridLTHCells" class="GridLTCells"/>
    								<display:column  sortable="true"  property="employeeName"  headerClass="GridLTHCells" class="GridLTCells" title="Employee Name" />
    								<display:column  sortable="true"  property="designation" headerClass="GridLTHCells" class="GridLTCells" title="Designation"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column  sortable="true"  property="station"  headerClass="GridLTHCells" class="GridLTCells" title="Station"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								<display:column  sortable="true"  property="seperationreason" headerClass="GridLTHCells" class="GridLTCells" title="Seperation Reason"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column  sortable="true"  property="netcontribution" headerClass="GridLTHCells" class="GridLTCells" title="Net contribution"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column  sortable="true"  property="paymentdt" headerClass="GridLTHCells" class="GridLTCells"  title="Payment Date"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="sanctiondt"  title="Sanction Date" headerClass="GridLTHCells" class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="verifiedBy"  title="Status" headerClass="GridLTHCells" class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									
									<logic:equal name="advanceBean" property="frmName" value="FSFormIII">
									<display:column sortable="true" headerClass="GridHBg" class="GridLCells"><input type="checkbox" name="chkbox"  value="<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getNssanctionno()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctiondt()%>::<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getEmployeeName()%>"/></display:column>													
									</logic:equal>
									
									</display:table>
								</td>
							</tr>
						</logic:present>
					 </table><%=ScreenUtilities.searchFooter()%>
  </html:form>

