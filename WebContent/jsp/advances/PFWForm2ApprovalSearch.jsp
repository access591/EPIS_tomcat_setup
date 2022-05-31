<!--
/*
  * File       : PFWForm2ApprovalSearch.jsp
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
			 document.forms[0].purposeType.value="";
			var url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form2";
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
			         
			           if(verifiedBy=='' || verifiedBy=='PERSONNEL'){
			        	advancesForm2Approve(transID,pensionNo,advanceType,status)
			           }else{
			           	if(status=='R'){
			           		alert('Advance request with transaction id '+transID+' is rejected by Finance Department.');
			        		advancesForm2Approve(transID,pensionNo,advanceType,status);
			        	}else{
			        		alert('Advance request with transaction id '+transID+' is already approved by Finance Department.');
			        	}
			        	
			           }
			        	
			        	
			        }else if(frmType=='Print'){
			        	advancesForm2Report(transID,pensionNo,advanceType,status)
			        }
		   	
		}
		function advancesForm2Approve(transID,pensionNo,advanceType,status){
		
		var flag;		 
		    if(status=='A' || status=='R'){
		    
		     var chkstatus= confirm("Advance Request is already approved by Personnel department.Do you really want to edit?");
			
			 if (chkstatus==true){			 
			 flag='ApprovalEdit';		   
			 
			    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
			 }
			 		     
		    }else{		    
			status='N';	
			flag='ApprovalForm';	
			
			    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();			
			}
		}
	   function advancesForm2Report(transID,pensionNo,advanceType,apprdstatus){	     
	   var flag='ApprovalReport';	
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			status='A';	
			if(apprdstatus=='N'){
			
				alert(transID+' Should be approved.');
			}else{
		    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;;
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
			var url="<%=basePath%>loadApproval.do?method=search23Advances&frmName=PFWForm2";
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
	
		</script>


	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
			<%=ScreenUtilities
									.saearchHeader("loansadvances.pfwverificationII.screentitle")%>
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
								<html:option value='Superannuation'>
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
				<tr></tr>
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
							<display:table cellspacing="0" cellpadding="0" class="GridBorder" style="width:100%;height:50px" export="true" sort="list" id="advanceList" sort="list" pagesize="10" name="requestScope.searchlist"
								requestURI="./loadApproval.do?method=search23Advances&amp;frmName=PFWForm2">
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />

								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>" />
								</display:column>
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="advanceTransIDDec" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="pfid" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="employeeName" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="purposeType" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="requiredamt" title="Required Amount" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="advanceStatus" title="Status" decorator="com.epis.utilities.StringFormatDecorator" />


							</display:table>
						</td>
					</tr>
				</logic:present>

			</table>
			<%=ScreenUtilities.searchFooter()%>

		</html:form>
	</body>
</html>
