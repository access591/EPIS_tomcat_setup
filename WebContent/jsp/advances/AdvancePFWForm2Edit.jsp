<!--
/*
  * File       : AdvancePFWForm2Edit.jsp
  * Date       : 21/01/2010
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->

<%@ page language="java"  pageEncoding="UTF-8"%>
<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();

%>
<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="this is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/commonfunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/datetime.js"></script>
		<SCRIPT type="text/javascript">
		function saveForm2(){
				
				url="<%=basePath%>loadApproval.do?method=savePFWForm2";
				
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		</SCRIPT>

	</head>

	<body class="bodybackground");">

		<html:form method="post" action="loadApproval.do?method=savePFWForm2">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<jsp:include page="PensionMenu.jsp" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbborder">
							<tr>
								<td height="5%" colspan="2" align="center" class="ScreenMasterHeading">

									PFW (Part Final Withdraw) Verfication Form-II [Edit] &nbsp;&nbsp;<font color="red"> </font>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>

							<tr>
								<td height="15%">
									<table align="center" border="0">

										<tr>
											<td width="12%">
												&nbsp;
											</td>
											<td width="34%" class="screenlabel">
												KEY NO
											</td>
											<td width="1%">
												:
											</td>
											<td width="53%" class="reportdata">
												<html:text name="Form2ReportBean" property="advanceTransID" readonly="true"></html:text>
												<html:hidden property="advntrnsid" name="Form2ReportBean" />
												<html:hidden property="advanceType" name="Form2ReportBean" />
											</td>
										</tr>

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Employee code
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="employeeNo" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Employee Name
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="employeeName" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Designation
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="designation" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Father&rsquo;s Name
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="fhName" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Department
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="department" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Date of joining in AAI
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="dateOfJoining" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Date of Birth
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:text name="Form2ReportBean" property="dateOfBirth" readonly="true"></html:text>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Taken Loan
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<html:select property="takenloan" name="Form2ReportBean" style="width:130px">
													<html:option value='Y'>Yes</html:option>
													<html:option value='N'>No</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Verified By &nbsp;
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												Personnel
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Authorized Status &nbsp;
											</td>
											<td>
												:
											</td>
											<td>
												<html:select property="authorizedsts" style="width:130px">
													<html:option value='A'>Accept</html:option>
													<html:option value='R'>Reject</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="screenlabel">
												Remarks &nbsp;
											</td>
											<td>
												:
											</td>
											<td>
												<html:text property="authorizedrmrks" maxlength="150" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<tr>
											<td align="center" colspan="4">
												<table align="center">
													<tr>
														<td>
															&nbsp;
														</td>
														<td align="left" id="submit">
															<input type="button" class="btn" value="Update" onclick="saveForm2();" tabindex="9" />
														</td>
														<td align="right">
															<input type="reset" class="btn" value="Reset" class="btn" tabindex="10" />
															<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="11" />
														</td>
													</tr>


												</table>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						</table>
						</html:form>
	</body>
</html>

