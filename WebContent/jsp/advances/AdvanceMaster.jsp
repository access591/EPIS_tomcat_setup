<!--
/*
  * File       : AdvanceMaster.jsp
  * Date       : 09/30/2009
  * Author     : Suresh Kumar Repaka 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.info.login.LoginInfo,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%@page import="com.epis.bean.advances.*"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			HttpSession httpsession = request.getSession();
			LoginInfo logInfo=new LoginInfo();
		//	 CommonUtil util = new CommonUtil();
         //int gridLength = util.gridLength();
            logInfo=(LoginInfo)httpsession.getAttribute("user");
         String userName=logInfo.getUserName();
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
		function resetMaster(){
		
		   var frmName;
		   if(document.forms[0].advanceType.value=="CPF")
		    frmName='advances';
		   else if(document.forms[0].advanceType.value=="pfw")   
			   frmName='pfw';
		
			var url="<%=basePath%>advanceSearch.do?method=loadAdvanceSearchForm&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		function reportAdvances(transID,pensionNo)
		{
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
	    var url="<%=basePath%>loadAdvance.do?method=advanceReport&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 	winOpened = true;
		wind1.window.focus();
		}
		function reportAdvances2(transID,pensionNo,advanceType){
			if(advanceType=='PFW'){
				advancesForm2Report(transID,pensionNo);
			}else{
				var url="<%=basePath%>advanceForm2.do?method=loadAdvanceForm2&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		 		document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
			}
	    
		}
		function reportAdvances3(transID,pensionNo,advanceType){
			if(advanceType=='PFW'){
				var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		 		document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
			}else{
				alert('Form-3 is not available for CPF Advances');
			}
	    
		}
		function advancesForm2Report(transID,pensionNo){
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
		    var url="<%=basePath%>loadAdvance.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
		}
		
		function search()
		{
		   //alert(document.forms[0].advanceType.value);
		   var frmName;
		   if(document.forms[0].advanceType.value=="CPF")
		    frmName='advances';
		   else if(document.forms[0].advanceType.value=="pfw")   
			   frmName='pfw';
			
			if(document.forms[0].advanceTransyear!='')
			{
				if (!convert_date(document.forms[0].advanceTransyear))
				{
					document.forms[0].advanceTransyear.value='';
					document.forms[0].advanceTransyear.focus();
					return false;
				}
			}
			  
			var url="<%=basePath%>advanceSearch.do?method=searchAdvances&frmName=AdvanceSearch&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
		
		function editAdvances(transID,pensionNo,verifiedBy,status)
		{						
			if(status=="Rejected"){
			alert("CPF Advance Request Rejected by CHQ");
			return false;
			}						
			if(verifiedBy=='PERSONNEL'){
			  alert('Advance Trans Id '+transID+' already approved by Personnel Department. You can not edit the details.');
			}else if(verifiedBy=='PERSONNEL,FINANCE'){
			  alert('Advance Trans Id '+transID+' already approved by Personnel , Finance Department. You can not edit the details.');
			}else if(verifiedBy=='PERSONNEL,FINANCE,RHQ'){
			  alert('Advance Trans Id '+transID+' already approved by CHQ/RHQ. You can not edit the details.');
			}else if(verifiedBy=='PERSONNEL,REC'){
			  alert('Advance Trans Id '+transID+' already approved by Recommended. You can not edit the details.');
			}else if(verifiedBy=='PERSONNEL,REC,FINANCE'){
			  alert('Advance Trans Id '+transID+' is already approved . You can not edit the details.');
			}else{
			 var url,frmName;
			
			if(document.forms[0].advanceType.value=='CPF'){
			frmName="advancesedit";
			
			 url="<%=basePath%>loadAdvanceEdit.do?method=editAdvances&frm_name=advancesedit&frmTransID="+transID+"&frmPensionNo="+pensionNo+"&frm_name="+frmName;
			}else{
			frmName="pfwedit";
		     url="<%=basePath%>loadAdvanceEdit.do?method=editAdvances&frm_name=pfwedit&frmTransID="+transID+"&frmPensionNo="+pensionNo+"&frm_name="+frmName;
		     }
		     
		    	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			}
		}
		
		function deleteAdvances(transID,pensionNo,verifiedBy)
		{					
			var advanceType=document.forms[0].advanceType.value;
		  
			if((verifiedBy=='PERSONNEL,FINANCE,RHQ')||(verifiedBy=='PERSONNEL,REC')||(verifiedBy=='PERSONNEL,REC,FINANCE') ) {
			  alert('You can not Delete the details.');
			} else{
			 var chkstatus= confirm("Do you really want to delete Advances Request?");
			 if (chkstatus== true)
			 {
			  		 
			    if(document.forms[0].advanceType.value=='CPF'){			    
			    var url="<%=basePath%>loadAdvance.do?method=deleteAdvances&frm_name=advances&frmTransID="+transID+"&frmPensionNo="+pensionNo+"&advanceType="+advanceType+"&verifiedBy="+verifiedBy;
			    }else{
			    var url="<%=basePath%>loadAdvance.do?method=deleteAdvances&frm_name=pfw&frmTransID="+transID+"&frmPensionNo="+pensionNo+"&advanceType="+advanceType+"&verifiedBy="+verifiedBy;
			    }
			    
		    	document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }	 		    
		}
		}
		
		function editCheckedRecords(){		
		     
		      var count=0;
		      var str=new Array();
		      var transid,pfid,verifiedby,approvedStatus;
		        
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
			          transid=str[0];
			          pfid=str[1];
			          verifiedby=str[2];
			          approvedStatus=str[3];
			        }
			        
			        editAdvances(transid,pfid,verifiedby,approvedStatus);
		     	
		}
		
		function deleteCheckedRecords(){
				    
		      var count=0;
		      var str=new Array();
		      var transid,pfid,verifiedby;
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
			          transid=str[0];
			          pfid=str[1];
			          verifiedby=str[2];
			        }
			        deleteAdvances(transid,pfid,verifiedby);
		   	
		}
		
		function viewReport(){		
		      
		      var count=0;
		      var str=new Array();
		      var transid,pfid,verifiedby;
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
			          transid=str[0];
			          pfid=str[1];
			          verifiedby=str[2];
			        }
			    
			        reportAdvances(transid,pfid);
		     
		}
				
		</script>

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
			<%=ScreenUtilities.saearchHeader(request.getAttribute("screenTitle").toString())%>
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
								<td class="tableTextBold">
									Advance Status:
								</td>
								<td>
									<html:select property="advanceTransStatus" styleClass="TextField">
										<html:option value=''>Select One</html:option>
										<html:option value='N'>New</html:option>
										<html:option value='A'>Approved</html:option>
										<html:option value='R'>Reject</html:option>

									</html:select>
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									Employee Name:
								</td>
								<td>
									<html:text property="employeeName" styleClass="TextField" maxlength="50" />
								</td>
								<td class="tableTextBold">
									PF ID:
								</td>
								<td>
									<html:text property="pfid" styleClass="TextField" />
								</td>
							</tr>

							<tr>
								<!-- <td class="tableTextBold">Advance Details:&nbsp;</td>
						<td>-->

								<!-- <html:select  property="advanceType" style="width:150px">  
							    <html:option value=''>Select One</html:option>
								<html:option value='pfw'>PFW</html:option>
								<html:option value='CPF'>CPF Advance</html:option>
						</html:select>-->
								<!-- 						<select name='advanceType' onchange="advance_info();" style='width:130px'>
								<option value='pfw'>PFW</option>
								<option value='advance'>CPF Advance</option>
							</select>-->

								<logic:equal name="advanceBean" property="frmName" value="advances">
									<html:hidden property="advanceType" value="CPF" styleClass="TextField" />
								</logic:equal>

								<logic:equal name="advanceBean" property="frmName" value="pfw">
									<html:hidden property="advanceType" value="pfw" styleClass="TextField" />
								</logic:equal>

								</td>
								<td class="tableTextBold">
									Advance Date:
								</td>
								<td>
									<html:text property="advanceTransyear" maxlength="12" styleClass="TextField" />
									&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].advanceTransyear');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
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
					<td height="29" align="left" valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<logic:equal name="advanceBean" property="frmName" value="advances">
										<input type="button" value="New" class="btn" onclick="javascript:window.location.href='<%=basePath%>loadAdvance.do?method=loadAdvanceForm&frm_name=advancesnew'">
									</logic:equal>

									<logic:equal name="advanceBean" property="frmName" value="pfw">
										<input type="button" value="New" class="btn" onclick="javascript:window.location.href='<%=basePath%>loadAdvance.do?method=loadAdvanceForm&frm_name=pfwnew'">
									</logic:equal>
								</td>
								<td>
									<input type="button" value="Edit" class="btn" onclick="javascript:editCheckedRecords()">
								</td>
								<td>
									<input type="button" value="Delete" class="btn" onclick="javascript:deleteCheckedRecords()">
								</td>

								<td>
									<input type="button" value="Print" class="btn" onclick="javascript:viewReport()">
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<logic:present name="searchlist">
					<tr>
						<td align="left" valign="top">
							<display:table cellpadding="0" cellspacing="0" class="GridBorder" style="width:100%;height:50px" export="true" sort="list" id="advanceList" sort="list" pagesize="10" name="requestScope.searchlist"
								requestURI="./advanceSearch.do?method=searchAdvances">
								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>" />
								</display:column>
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceTransIDDec" sortable="true" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="pfid" sortable="true" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="employeeName" sortable="true" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="designation" title="Designation" sortable="true" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceType" sortable="true" title="Advance Type" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="purposeType" sortable="true" title="Purpose Type" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="transactionStatus" title="Status" sortable="true" />
								<display:column headerClass="GridHBg" class="GridLTCells" property="transMnthYear" sortable="true" title="Form Date" />
							</display:table>

						</td>
					</tr>
				</logic:present>

			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
