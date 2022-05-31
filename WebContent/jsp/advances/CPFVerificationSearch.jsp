<!--
/*
  * File       : CPFVerificationSearch.jsp
  * Date       : 17/12/2009
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
		function resetMaster(){
			 document.forms[0].purposeType.value="";
			var url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=CPFform2";
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}		
		function selectSubMenuOptions(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var transID,pensionNo,advanceType,status,transDate,verifiedBy;
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
			          transDate=str[3];
			          status=str[4];
			          verifiedBy=str[5];
			        }
			       
			        
			        if(frmType=='Approval'){
			        	advancesForm2Approve(transID,pensionNo,advanceType,transDate,status,verifiedBy)
			        }else if(frmType=='Print'){
			        	advancesForm2Report(transID,pensionNo,advanceType,transDate,status)
			        }
		   	
		}
		function advancesForm2Approve(transID,pensionNo,advanceType,transDate,status,verifiedBy){		 
			if(status=="Rejected"){
		alert("CPF Advance Request Rejected by CHQ");
		return false;
		}
		    	if(verifiedBy=='PERSONNEL,REC'){
		         alert("CPF Advance Request already Recommended");
		        }else  if(verifiedBy=='PERSONNEL,REC,FINANCE'){
		         alert("CPF Advance Request already Approved");
		        }else{
				var formType="Y";
				var url="<%=basePath%>advanceForm2.do?method=loadCPFVerificationForm&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmTransDate="+transDate+"&frm_formType="+formType;
		 		document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
				}				
		}
	   function advancesForm2Report(transID,pensionNo,advanceType,transDate,apprdstatus){	     
			var formType="N";
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			
			if(apprdstatus=='N'){
				alert(transID+' Should be approved.');
			}else{
			var url="<%=basePath%>advanceForm2.do?method=loadCPFVerificationForm&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmTransDate="+transDate+"&frm_formType="+formType;
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
			var url="<%=basePath%>loadApproval.do?method=searchCPFVerificationAdvances&frmName=CPFVerification";
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
		
		</script>


	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
			<%=ScreenUtilities.saearchHeader("loansadvances.cpfverification.screentitle")%>
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
										<option value="">
											Select one
										</option>
										<option value='cost'>
											Cost Of Passage
										</option>
										<option value='obligatory'>
											Obligatory Expanses
										</option>
										<option value='obMarriage'>
											Marriage Expanses
										</option>
										<option value='illness'>
											Illness Expanses
										</option>
										<option value='education'>
											Higher Education
										</option>
										<option value='defence'>
											Defense Of Court Case
										</option>
										<option value='others'>
											Others
										</option>
									</html:select>

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
							<display:table export="true" sort="list" id="advanceList" style="width:100%;height:50px" class="GridBorder" sort="list" pagesize="10" name="requestScope.searchlist" requestURI="./loadApproval.do?method=search23Advances&frmName=PFWForm2">
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />

								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getTransMnthYear()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>" />
								</display:column>


								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceTransIDDec" sortable="true" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="pfid" sortable="true" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="employeeName" sortable="true" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceType" sortable="true" title="Advance Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="purposeType" sortable="true" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="requiredamt" sortable="true" title="Required Amount" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="cpfIntallments" sortable="true" title="No.of Installments" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceStatus" sortable="true" title="Status" />




							</display:table>
						</td>
					</tr>
				</logic:present>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
