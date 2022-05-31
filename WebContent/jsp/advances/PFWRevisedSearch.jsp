<!--
/*
  * File       : PFWRevisedSearch.jsp
  * Date       : 24-May-2012
  * Author     : Radha P 
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
		function resetMaster(frmName){
			 document.forms[0].purposeType.value="";
			var url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}		
		function selectSubMenuOptions(frmType,frmName){
				    
		      var count=0;
		      var str=new Array();
		      var transID,pensionNo,advanceType,status,verifiedBy,purposeType,revTransID;
		      
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
			          purposeType=str[5];
			          revTransID=str[6];
			        }
			        
			        if(purposeType!="HBA"){
			         alert("We Provide Revision of  Sanction Order Only For HBA cases");		   
			        return false;
			        }
			        if(frmType=='Approval'){ 
			        		PFWRevisedEntry(transID,revTransID,pensionNo,advanceType,purposeType,verifiedBy,frmName) ;
			         
			        }else if( (frmType=='Print') ||(frmType=='Print-Sanc')) {
			        	advancesForm2Report(transID,revTransID,pensionNo,advanceType,verifiedBy,status,frmName,frmType);
			        }
		   	
		}
		function PFWRevisedEntry(transID,revTransID,pensionNo,advanceType,purposeType,verifiedBy,frmName){
		
		var frmFlag; 
		
		if(!verifiedBy==""){
		transID = revTransID;
		}
		if(revTransID!="---"){
		frmFlag = "Edit";
		}else{
		frmFlag = "New";
		}
		//alert("==revTransID=="+revTransID+"=verifiedBy=="+verifiedBy);
		if((frmName=='PFWRevisedVerification') && (verifiedBy=='FINANCE,RHQ')){
			         alert("Revision of PFW Sanction Order is Approved by DGM");
			        }else  if((frmName=='PFWRevisedVerification'  || frmName=='PFWRevisedRecommendation' || frmName=='PFWRevisedApproval') && (verifiedBy=='FINANCE,RHQ,APPROVED')){
			         alert("Revision of PFW Sanction Order is Approved");
			        }else{
			    var url="<%=basePath%>loadApproval.do?method=loadPFWRevisedDetails&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmPurposeType="+purposeType+"&frmName="+frmName+"&frmFlag="+frmFlag;
			// alert(url);
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();			
			 }
		}
	   function advancesForm2Report(transID,revTransID,pensionNo,advanceType,verifiedBy,apprdstatus,frmName,frmType){	     
	   var flag='ApprovalReport';	
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			 
		 if(revTransID!="---"){
		transID = revTransID;
		}
		
		// alert("==frmType=="+frmType+"==frmName=="+frmName+"=verifiedBy=="+verifiedBy);
		  if(frmType=='Print') {
		 if(((frmName=='PFWRevisedVerification') && (verifiedBy==''))  ||
		  ((frmName=='PFWRevisedRecommendation') && (verifiedBy=='FINANCE'))){
			        
			       alert(pensionNo+' Should be approved.'); 
			         return false;
			        
		 }
		 }
		    var url="<%=basePath%>form4Advance.do?method=advanceSanctionOrderRev&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&reportType="+frmType+"&frmName="+frmName;
			//alert(url);
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			 
		
		}
	 
		function search(frmName)
		{
			    if(document.forms[0].advanceTransyear.value!=''){
				if (!convert_date(document.forms[0].advanceTransyear))
				{
					document.forms[0].advanceTransyear.focus();
					return false;
				}
			}
			var url="<%=basePath%>loadApproval.do?method=searchAdvancesRev&frm_name="+frmName;
		 	//alert(url);
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
	
		</script>


	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
		<logic:equal name="advanceBean" property="frmName" value="PFWRevisedVerification">
		<%=ScreenUtilities.saearchHeader("loansadvances.pfwrevisedverificationsearch.screentitle")%>
		</logic:equal>
		<logic:equal name="advanceBean" property="frmName" value="PFWRevisedRecommendation">
		<%=ScreenUtilities.saearchHeader("loansadvances.pfwrevisedrecommendationsearch.screentitle")%>
		</logic:equal>
		<logic:equal name="advanceBean" property="frmName" value="PFWRevisedApproval">
		<%=ScreenUtilities.saearchHeader("loansadvances.pfwrevisedapporovalsearch.screentitle")%>
		</logic:equal>
		<logic:equal name="advanceBean" property="frmName" value="PFWRevisedApproved">
		<%=ScreenUtilities.saearchHeader("loansadvances.pfwrevisedapporovedsearch.screentitle")%>
		</logic:equal>
		
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td height="15%">
						<table align="center">
							<tr>
								<td class="screenlabelerrors" colspan="3" align="center" style="color:red">


									<logic:messagesPresent message="true">
										<html:messages id="message" message="true"  bundle="common">
											<bean:write name="message" />
											<br />
										</html:messages>

									</logic:messagesPresent>

								</td>
							</tr>
							
							<tr>
								<td class="tableTextBold">
									Advance ID:
								</td>
								<td>
									<html:text property="advanceTransID" styleClass="TextField" />
								</td>
								<td class="tableTextBold">
									Sanctioned Date:
								</td>
								<td>
									<html:text property="advanceTransyear" maxlength="12" styleClass="TextField" />
									<a href="javascript:show_calendar('forms[0].advanceTransyear');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
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
									</html:select>
									<!-- 						<select name='advanceType' onchange="advance_info();" style='width:130px'>
								<option value='pfw'>PFW</option>
								<option value='advance'>CPF Advance</option>
							</select>-->
								</td>
								
							</tr>
							<tr>
								<td align="left">
									&nbsp;
								</td>
								<td>
									<input type="button" class="butt" value="Search" class="btn" onclick="javascript:search('<bean:write name="advanceBean" property="frmName" />')">
									<input type="button" class="butt" value="Reset" onclick="javascript:resetMaster('<bean:write name="advanceBean" property="frmName" />')" class="btn">
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
					 <td  height="29" align="left" valign="top">
						<table width="100" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<logic:notEqual name="advanceBean" property="frmName" value="PFWRevisedApproved">
								<td>
									<input type="button" value="Approval" class="btn" onclick="javascript:selectSubMenuOptions('Approval','<bean:write name="advanceBean" property="frmName" />')">
								</td>
								</logic:notEqual>
								 
								<logic:notEqual name="advanceBean" property="frmName" value="PFWRevisedApproval">
								<td>
									<input type="button" value="Print" class="btn" onclick="javascript:selectSubMenuOptions('Print','<bean:write name="advanceBean" property="frmName" />')">
								</td>	
								</logic:notEqual>	
									
								<logic:equal name="advanceBean" property="frmName" value="PFWRevisedApproved">
								<td>
									<input type="button" value="Print-Sanction Order" class="btn" onclick="javascript:selectSubMenuOptions('Print-Sanc','<bean:write name="advanceBean" property="frmName" />')">
								</td>
								</logic:equal>
								 
							</tr>
							
							
						</table>
					</td>
					 
				</tr>

				<logic:present name="searchlist">
					<tr>
						<td align="center" width="100%">
							<display:table cellspacing="0" cellpadding="0" class="GridBorder" style="width:100%;height:50px" export="true" sort="list" id="advanceList" sort="list" pagesize="10" name="requestScope.searchlist"
								requestURI="./loadApproval.do?method=search23Advances&amp;frmName=PFWRevised">
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />

								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPurposeType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getRevAdvanceTransID()%>" />
								</display:column>
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="advanceTransIDDec" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="pfid" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="employeeName" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" sortable="true" property="purposeType" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
							 
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
