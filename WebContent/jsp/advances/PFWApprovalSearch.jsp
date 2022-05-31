<!--
/*
  * File       : PFWApprovalSearch.jsp
  * Date       : 07/11/2009
  * Author     : Suneetha V 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean" pageEncoding="UTF-8"%>
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
			var url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form2";
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}		
		
		function advancesForm2Approve(transID,pensionNo,advanceType,status){		
		    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();			
		}
	   function advancesForm2Report(transID,pensionNo,advanceType,status){	     
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			
		    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
		}
		function search()
		{
			
			var url="<%=basePath%>loadApproval.do?method=searchAdvances&frmName=PFWForm2";
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
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td height="5%" colspan="2" align="center" class="ScreenHeading">
						PFW Approval Form[Search]
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td height="15%">
						<table align="center">
							<tr>
								<td class="screenlabel">
									Advance ID:
								</td>
								<td>
									<html:text property="advanceTransID" />
								</td>
								<td class="screenlabel">
									Advance Status:
								</td>
								<td>
									<html:select property="advanceTransStatus" style="width:150px">
										<html:option value=''>Select One</html:option>
										<html:option value='N'>New</html:option>
										<html:option value='A'>Approved</html:option>
										<html:option value='R'>Reject</html:option>

									</html:select>
								</td>


							</tr>
							<tr>
								<td class="screenlabel">
									Employee Name:
								</td>
								<td>
									<html:text property="employeeName" maxlength="50" />
								</td>
								<td class="screenlabel">
									PF ID:
								</td>
								<td>
									<html:text property="pfid" />
								</td>

							</tr>

							<tr>
								<td class="screenlabel">
									Purpose Details:&nbsp;
								</td>
								<td>
									<html:select property="purposeType" style='width:130px' tabindex="5">
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
									</html:select>
									<!-- 						<select name='advanceType' onchange="advance_info();" style='width:130px'>
								<option value='pfw'>PFW</option>
								<option value='advance'>CPF Advance</option>
							</select>-->
								</td>
								<td class="screenlabel">
									Advance Date:
								</td>
								<td>
									<html:text property="advanceTransyear" maxlength="12" />
								</td>
							</tr>
							<tr>
								<td align="left">
									&nbsp;
								</td>
								<td>
									<input type="button" class="btn" value="Search" class="btn" onclick="javascript:search()">
									<input type="button" class="btn" value="Reset" onclick="javascript:resetMaster()" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>

							</tr>
						</table>
					</td>
				</tr>






				<tr>
					<td>
					</td>
				</tr>
				<tr>
					<logic:present name="searchlist">
						<tr>
							<td align="center" width="100%">
								<display:table export="true" sort="list" id="advanceList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.searchlist" requestURI="./loadApproval.do?method=searchAdvances&frmName=PFWForm2">
									<display:setProperty name="export.amount" value="list" />
									<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
									<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
									<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />


									<display:column title="&nbsp;&nbsp;&nbsp;&nbsp;" media="html">
										<a
											href="javascript:advancesForm2Approve('<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>')">
											<img src="<%=basePath%>images/viewDetails.gif" border='0' alt='Form2'> </a>
									</display:column>

									<display:column property="advanceTransIDDec" title="Advance TransID" />
									<display:column property="pfid" sortable="true" title="PF ID" />
									<display:column property="employeeName" sortable="true" headerClass="sortable" title="Employee Name" />
									<display:column property="purposeType" sortable="true" title="Purpose Type" decorator="com.epis.utilities.StringFormatDecorator" />
									<display:column property="requiredamt" sortable="true" title="Required Amount" decorator="com.epis.utilities.StringFormatDecorator" />
									<display:column property="advanceStatus" title="Status" decorator="com.epis.utilities.StringFormatDecorator" />

									<display:column title="&nbsp;&nbsp;&nbsp;&nbsp;" media="html">
										<a
											href="javascript:advancesForm2Report('<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceTransID()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getPensionNo()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceType()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("advanceList")).getAdvanceStatus()%>')">
											<img src="<%=basePath%>images/viewDetails.gif" border='0' alt='Form2'> </a>
									</display:column>

								</display:table>
							</td>
						</tr>
					</logic:present>
			</table>
			</td>

			</tr>

			</table>
		</html:form>
	</body>
</html>
