<!--
/*
  * File       : PFWForm3ApprovalSearch.jsp
  * Date       : 07/11/2009
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
		//	 CommonUtil util = new CommonUtil();
         //int gridLength = util.gridLength();
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
		function resetMaster(){
			 document.forms[0].purposeType.value="";
			var url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form3";
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}		
		function selectSubMenuOptions(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var transID,pensionNo,advanceType,status,verifiedBy;
		   	  if(document.forms[0].chk==undefined){
		  	alert('User Should select advance request');		     
		       				return false;
		  }  
		  
		 
		  
		    if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select advance request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select advance request');		     
		       				return false;
				 	}
		     }
		     
		       for(var j=0;j<str.length;j++){			         
			          transID=str[0];
			          pensionNo=str[1];
			          advanceType=str[2];
			          status=str[3];
			          verifiedBy=str[4];
			        }
			       
			        if(frmType=='Approval'){
			         
			            if(verifiedBy=='PERSONNEL' || verifiedBy=='PERSONNEL,FINANCE'){
			        	advancesForm3Approve(transID,pensionNo,advanceType,status)
			        	}else{
			        	   	if(status=='R'){
				           		alert('Advance request with transaction id '+transID+' is rejected by RHQ.');
				        		advancesForm3Approve(transID,pensionNo,advanceType,status);
			        		}else{
			        			alert('Advance request with transaction id '+transID+' is already approved by RHQ.');
			        		}
			        	
			        	}
			        }else if(frmType=='Print'){
			        	advancesForm3Report(transID,pensionNo,advanceType,status)
			        }
		   	
		}
		function advancesForm3Approve(transID,pensionNo,advanceType,status){
		var flag;		  
		    if(status=='A'){
		     flag='ApprovalEdit';
		     	
		     var chkstatus= confirm("Advance Request is already approved by Finance department.Do you really want to edit?");
			 if (chkstatus==true){	
			 
			var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmName=PFWForm3&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
			 
			 }
		    }else{	
		    flag='ApprovalForm';	
		    			
		    var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmName=PFWForm3&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();			
			}
		}
	   function advancesForm3Report(transID,pensionNo,advanceType,status){	
	   var flag='ApprovalReport';		       
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
						
			if(status=='N'){
			
				alert(transID+' Should be approved.');
			}else{			
		    var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmName=PFWForm3Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
		}
		
		function search()
		{			
			if(document.forms[0].advanceTransyear.value!=''){
				if (!convert_date(document.forms[0].advanceTransyear))
				{
					document.forms[0].advanceTransyear.focus();
					return false;
				}
			}
			var url="<%=basePath%>loadApproval.do?method=search23Advances&frmName=PFWForm3";		
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
		
		function sanctionReport(){
		
		  	  var count=0;
		      var str=new Array();
		      var transID,pensionNo,advanceType,status,sanctionDate;   
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
		          transID=str[0];
			          pensionNo=str[1];
			          advanceType=str[2];
			          status=str[3];
			          sanctionDate=str[4];
		          
		          //alert(sanctionDate);
		          
		          if(j==0)      
		          {
			          if(sanctionDate==''){			          		     
				          				         
				           if(appArr1!='')
				           appArr1=appArr1+','+transID;
				           else
				          appArr1=transID;
				           
			          }else{			          
			         
			            if(appArr2!='')
				           appArr2=appArr2+','+transID;
				           else
				           appArr2=transID;
				           
				       
			          }
		          }	                   	               
		          
			 }			 
		}
		
		function update(){	
		 var count=0;  		
	     var transID,pensionNo,advanceType,status,verifiedBy,transactionStatus;
		  var transidstrip='',transidstripwithsanctiondt='',notVerifiedStrip='';
		  
		    if(document.forms[0].chkbox.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chkbox.length;i++){
		      	    
			      if (document.forms[0].chkbox[i].checked==true){
			        count++;
			        str=document.forms[0].chkbox[i].value.split(':');
			       //alert(str);
			     
			      if(str[4]==''){
			       if(str[6]=='Approved'){
			      if(transidstrip!=''){
			       transidstrip=transidstrip+','+str[0];
			      }else{
			      transidstrip=str[0];
			      }
			      }else{
			      if(notVerifiedStrip!=''){
			       notVerifiedStrip=notVerifiedStrip+','+str[0];
			      }else{
			      notVerifiedStrip=str[0];
			      }
			      
			      }
			      }else{
			      
			      if(transidstripwithsanctiondt!=''){
			       transidstripwithsanctiondt=transidstripwithsanctiondt+','+str[0];
			      }else{
			      transidstripwithsanctiondt=str[0];
			      }
			      
			      } 
			        //  alert('transidstrip----'+transidstrip);
			          //  alert('transidstripwithsanctiondt'+transidstripwithsanctiondt);
			             // alert('notVerifiedStrip-----'+notVerifiedStrip);
			      }
		      }
		      if(count==0){
		      	alert('User Should select advance request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chkbox.checked){
				 		str=document.forms[0].chkbox.value.split(':');
				 		
				  if(str[4]==''){
			       if(str[6]=='Approved'){
			      if(transidstrip!=''){
			       transidstrip=transidstrip+','+str[0];
			      }else{
			      transidstrip=str[0];
			      }
			      }else{
			      if(notVerifiedStrip!=''){
			       notVerifiedStrip=notVerifiedStrip+','+str[0];
			      }else{
			      notVerifiedStrip=str[0];
			      }
			      
			      }
			      }else{
			      
			      if(transidstripwithsanctiondt!=''){
			       transidstripwithsanctiondt=transidstripwithsanctiondt+','+str[0];
			      }else{
			      transidstripwithsanctiondt=str[0];
			      }
			      
			      }
			          
				 	}else{
				 		   alert('User Should select advance request');		     
		       				return false;
				 	}
		     }
		     
		     
		      for(var j=0;j<str.length;j++){			         
			          transID=str[0];
			          pensionNo=str[1];
			          advanceType=str[2];
			          status=str[3];
			          verifiedBy=str[5];
			          transactionStatus=str[6];
			        }
			        
						        	
		  if(transidstripwithsanctiondt!=''){		
		     
		    if(transidstrip!=''){
		    
		     if(notVerifiedStrip!=''){
		   var chkstatus= confirm("Records with Transaction Id "+transidstripwithsanctiondt+ " contains Sanction Date And Records with Application Id "+notVerifiedStrip+ " not finally approved  These records are not processed. Do you want to continue?");
			}else{
			var chkstatus= confirm("Records with Transaction Id "+transidstripwithsanctiondt+ " contains Sanction Date. These records are not processed. Do you want to continue?");
			}		  
		      
			 if (chkstatus== true)
			 {				 
				var url="<%=basePath%>loadApproval.do?method=updatePFWSanctionDate&frmTransNo="+transidstrip+"&frm_name=PFWForm3";
			 	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }
			 
			 }else{
			  
			   if(notVerifiedStrip!=''){
			 alert('Sanction Date is already assigned for records with Transaction Id '+transidstripwithsanctiondt+' And '+notVerifiedStrip+' not finally approved ');
			 }else{
			    alert('Sanction Date is already assigned for records with Transaction Id '+transidstripwithsanctiondt);
			    }
			 }
			 
			  
			
		  }else{  
		  
		  if(transidstrip!=''){  
		    if(notVerifiedStrip!=''){
		   var chkstatus= confirm("Records with Transaction Id "+notVerifiedStrip+ " not finally approved . These records are not processed. Do you want to continue?");
			
			 if (chkstatus== true)
			 {			 
				var url="<%=basePath%>loadApproval.do?method=updatePFWSanctionDate&frmTransNo="+transidstrip+"&frm_name=PFWForm3";
			   	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }
			 
			}else{
			  
			var url="<%=basePath%>loadApproval.do?method=updatePFWSanctionDate&frmTransNo="+transidstrip+"&frm_name=PFWForm3";
			   	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();	
			
			}    
		  }else{
		   if(notVerifiedStrip!=''){
		  alert("Records with Transaction Id "+notVerifiedStrip+ " not finally approved ");
			}
		  
		  } 
		  
		  }	 
		  
		  				
		}		
		
		</script>

	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
			<%=ScreenUtilities.saearchHeader("loansadvances.pfwverificationIII.screentitle")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">

				<tr>
					<td height="15%">
						<table align="center">
							<tr>
								<td class="tableTextBold">
									Advance ID:
								</td>
								<td>
									<html:text property="advanceTransID" styleClass="TextField" />
								</td>



							</tr>
							<tr>
								<td class="tableTextBold">
									Employee Name:
								</td>
								<td>
									<html:text property="employeeName" maxlength="50" styleClass="TextField" />
								</td>
								<td class="tableTextBold">
									PF ID:
								</td>
								<td>
									<html:text property="pfid" styleClass="TextField" />
								</td>

							</tr>

							<tr>
								<td class="tableTextBold">
									Purpose Details:&nbsp;
								</td>
								<td>
									<html:select property="purposeType" styleClass="TextField" tabindex="5">
										<html:option value=''>
									Select one
								</html:option>
										<html:option value='HBA'>
									HBA (Housing Built Area)
								</html:option>
										<html:option value='Marriage'>
									Marriage
								</html:option>
										<html:option value='HE'>
									Higher Education
								</html:option>
										<html:option value='LIC'>
									LIC (Life Insurance Policy)
								</html:option>
								<html:option value='SUPERANNUATION'>
									 Superannuation
								</html:option>
								<html:option value='Pandemic'>
									 Pandemic
								</html:option>
									</html:select>
									<!-- 						<select name='advanceType' onchange="advance_info();" style='width:130px'>
								<option value='pfw'>PFW</option>
								<option value='advance'>CPF Advance</option>
							</select>-->
								</td>
								<td class="tableTextBold">
									Advance Date:
								</td>
								<td>
									<html:text property="advanceTransyear" maxlength="12" styleClass="TextField" />
									<a href="javascript:show_calendar('forms[0].advanceTransyear');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
								</td>
							</tr>
							<tr>
								<td align="left">
									&nbsp;
								</td>
								<td>
									<input type="button" class="butt" value="Search" class="btn" onclick="javascript:search()">
									<input type="button" class="butt" value="Reset" onclick="javascript:resetMaster()" class="btn">
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
					<td width="37%" height="29" align="left" valign="top">
						<table width="155" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="40">
									<input type="button" value="Approval" class="btn" onclick="javascript:selectSubMenuOptions('Approval')">
								</td>
								<td width="40">
									<input type="button" value="Print" class="btn" onclick="javascript:selectSubMenuOptions('Print')">
								</td>
								<td>
								<input type="button" value="Update" class="btn" alt="Update Sanction Date" onclick="javascript:update()">
								</td>

							</tr>
						</table>
					</td>
					<td width="63%" align="left" valign="top">
						&nbsp;
					</td>
				</tr>
				<logic:present name="searchlist">
					<tr>
						<td align="center" width="100%">
							<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" style="width:100%;height:50px" id="advanceList" sort="list" pagesize="10" name="requestScope.searchlist"
								requestURI="./loadApproval.do?method=search23Advances&frmName=PFWForm3">
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />
								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>" />
								</display:column>
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceTransIDDec" sortable="true" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="pfid" sortable="true" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="employeeName" sortable="true" headerClass="sortable" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="purposeType" sortable="true" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="requiredamt" sortable="true" title="Required Amount" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceStatus" sortable="true" title="Status" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" headerClass="GridHBg" class="GridLCells"><input type="checkbox" name="chkbox"  value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getSanctiondt()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getTransactionStatus()%>"/></display:column>													
							</display:table>
						</td>
					</tr>
				</logic:present>

			</table>
			<%=ScreenUtilities.searchFooter()%>

		</html:form>
	</body>
</html>
