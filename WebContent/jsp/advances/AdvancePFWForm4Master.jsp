<!--
/*
  * File       : AdvancePFWForm4Master.jsp
  * Date       : 09/30/2009
  * Author     : Suresh Kumar Repaka 
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
		function resetMaster(frmName){
			var url="<%=basePath%>loadAdvanceForm4.do?method=loadForm4Approval&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}

		function selectSubMenuOptions(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var transID,pensionNo,advanceType,status,purposeType,frmName,sanctionDt;
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
			          purposeType=str[4];
			          frmName=str[5];
			          sanctionDt=str[6];
			        }
			        if(frmType=='Approval'){
			        	advancesForm4Screen(transID,pensionNo,advanceType,status,frmName);
			        }else if(frmType=='Print'){
			        	advancesForm4Report(transID,pensionNo,advanceType,status);
			        }else if(frmType=='Print-Sanc'){
			        	advancesSanctionOrder(transID,pensionNo,advanceType,status,purposeType,sanctionDt);
			        }
		   	
		}
		function advancesForm4Screen(transID,pensionNo,advanceType,status,frmName){
				var formType="Y";
				
				
			if(status=='A'){
		     alert('PFW Advance Request is already approved');
		    }else{
				var url="<%=basePath%>form4Advance.do?method=advanceForm4Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frm_type="+formType+"&frmName="+frmName;
		 		document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
				}
			
	    
		}
		function advancesForm4Report(transID,pensionNo,advanceType,status){
				var formType="Form-4Report";
				
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				if(status=='N'){
	  				 alert(transID+' Should be Final Approved.');
			    }else{	
				var url="<%=basePath%>form4Advance.do?method=advanceForm4Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frm_type="+formType;
		 		wind1 = window.open(url,"FinalApprovalReport","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 			winOpened = true;
				wind1.window.focus();
				}
		}
		
		function advancesSanctionOrder(transID,pensionNo,advanceType,status,purposeType,sanctionDt){
				var formType="PFWSanctionOrder";
				
				
				
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				if(status=='N'){
	  				 alert(transID+' Should be Final Approved.');
	  		 	}else if(!(purposeType=='MARRIAGE' || purposeType=='HBA' || purposeType=='HE' || purposeType=='SUPERANNUATION' ||purposeType=='PANDEMIC')){
	  		 	 alert('LIC under process');
	  		 	
			    }else{
			    
				    if(sanctionDt==''){
					
						 alert("PF ID "+pensionNo +" PFW Request is approved.But Sanction Date is not available");
						 return false;
					
					}else{
						
						var url="<%=basePath%>form4Advance.do?method=advanceSanctionOrder&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frm_type="+formType;
				 		wind1 = window.open(url,"FinalApprovalReport","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		   	 			winOpened = true;
						wind1.window.focus();
					}
				}
		}
		
		function search(frmName)
		{
		
			if(document.forms[0].advanceTransyear!='')
			{
				if (!convert_date(document.forms[0].advanceTransyear))
				{
					document.forms[0].advanceTransyear.value='';
					document.forms[0].advanceTransyear.focus();
					return false;
				}
			}
			
			var url="<%=basePath%>loadAdvanceForm4.do?method=searchForm4Approval&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
	
		}
		</script>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

	</head>
	<body onload="document.forms[0].advanceTransID.focus();">
		<html:form method="post" action="/advanceSearch.do?method=loadAdvanceSearchForm">
		
			<logic:equal name="advanceBean" property="frmName" value="PFWApproval">
				<%=ScreenUtilities.saearchHeader("loansadvances.pfwapproval.screentitle")%>
			</logic:equal>
			<logic:equal name="advanceBean" property="frmName" value="PFWApproved">
				<%=ScreenUtilities.saearchHeader("loansadvances.pfwapproved.screentitle")%>
			</logic:equal>
			 
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
								<td class="tableTextBold">
									Purpose Details:&nbsp;
								</td>
								<td>
									<html:select property="purposeType" styleClass="TextField">
										<html:option value=''>Select One</html:option>
										<html:option value='HBA'>HBA (Housing Built Area)</html:option>
										<html:option value='MARRIAGE'>Marriage</html:option>
										<html:option value='HE'>Higher Education</html:option>
										<html:option value='LIC'>LIC (Life Insurance Policy)</html:option>
										<html:option value='Superannuation'>Superannuation</html:option>
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
									<html:text property="advanceTransyear" styleClass="TextField" maxlength="12" />
									&nbsp;<a href="javascript:show_calendar('forms[0].advanceTransyear');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
								</td>
							</tr>
							<tr>
								<td align="left">
									&nbsp;
								</td>
								<td>
									<input type="button" class="butt" value="Search" class="btn" onclick="javascript:search('<bean:write name="advanceBean" property="frmName"/>')">
									<input type="button" class="butt" value="Reset" onclick="javascript:resetMaster('<bean:write name="advanceBean" property="frmName"/>')" class="btn">
									<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>

							</tr>
						</table>
					</td>
			</table>




			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="37%" height="29" align="left" valign="top">
						<table width="155" border="0" cellspacing="1" cellpadding="1">
							<tr>
								<logic:notEqual name="advanceBean" property="frmName" value="PFWApproved">
								<td width="40">
									<input type="button" value="Approval" class="btn" onclick="javascript:selectSubMenuOptions('Approval')">
								</td>
							</logic:notEqual> 	
							<logic:notEqual name="advanceBean" property="frmName" value="PFWApproval">
				   				<td width="40">
									<input type="button" value="Print" class="btn" onclick="javascript:selectSubMenuOptions('Print')">
								</td>
								<td width="60">
									<input type="button" value="Print-Sanction Order" class="btn" onclick="javascript:selectSubMenuOptions('Print-Sanc')">
								</td>
							</logic:notEqual>
							</tr>
						</table>
					</td>
					<td width="60%" align="left" valign="top">
						&nbsp;
					</td>
				</tr>

				<logic:present name="searchlist">
					<tr>
						<td align="center" width="100%">
							<display:table cellspacing="0" cellpadding="0" class="GridBorder" style="width:100%;height:50px" export="true" sort="list" id="advanceList" sort="list" pagesize="10" name="requestScope.searchlist"
								requestURI="./loadAdvanceForm4.do?method=searchForm4Approval">
								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />
								<display:column headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPurposeType()%>:<bean:write name="advanceBean" property="frmName"/>:<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getSanctiondt()%>" />
								</display:column>

								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceTransIDDec" sortable="true" title="Advance TransID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="pfid" sortable="true" title="PF ID" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="employeeName" sortable="true" title="Employee Name" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="designation" sortable="true" title="Designation" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceType" sortable="true" title="Advance Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="purposeType" sortable="true" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="advanceStatus" sortable="true" title="Status" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column headerClass="GridLTHCells" class="GridLTCells" property="transMnthYear" sortable="true" title="Form Date" />

							</display:table>
						</td>
					</tr>
				</logic:present>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
