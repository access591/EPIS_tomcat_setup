<!--
/*
  * File       : ApprovalForm.jsp
  * Date       : 06/10/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->

<%@ page language="java"  contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
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
		
		</SCRIPT>

	</head>

	<body class="bodybackground");">

		<html:form method="post" action="loadAdvance.do?method=saveApprovalForm">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

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

									Cpf Advance / Pfw (Part Final Withdraw) Approval Form &nbsp;&nbsp;<font color="red"> </font>
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
												PF ID:&nbsp;
											</td>
											<td>


												<html:hidden property="pensionNo" />
												<html:hidden property="region" />

												<html:text property="pfid" name="empinfoBean" tabindex="1" />
												<!-- <input type="text" name="pfid" />-->


											</td>
											<td class="screenlabel">
												Employee Code:&nbsp;
											</td>
											<td>
												<html:text property="employeeNo" name="empinfoBean" readonly="true" />
												<!-- 	<input type="text" name="empcode" />-->
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Name:&nbsp;
											</td>
											<td>
												<html:text property="employeeName" name="empinfoBean" readonly="true" />
												<!--  <input type="text" name="empname" /> -->
											</td>

											<td class="screenlabel">
												Designation:&nbsp;
											</td>
											<td>

												<html:text property="designation" name="empinfoBean" readonly="true" />
												<!-- <input type="text" name="designation" />-->
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Father's Name:&nbsp;
											</td>
											<td>
												<html:text property="fhName" name="empinfoBean" readonly="true" />
												<!-- <input type="text" name="fname" /> -->
											</td>


											<td class="screenlabel">
												Department:&nbsp;
											</td>
											<td>
												<html:text property="department" name="empinfoBean" readonly="true" />
												<!-- <input type="text" name="department" />-->
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Date of Joining in AAI:&nbsp;
											</td>
											<td>
												<html:text property="dateOfJoining" name="empinfoBean" readonly="true" />
												<!-- 	<input type="text" name="djoin" /> -->
											</td>

											<td class="screenlabel">
												Date of Joining CPF:&nbsp;
											</td>
											<td>
												<html:text property="dateOfMembership" name="empinfoBean" readonly="true" />
												<!-- 	<input type="text" name="dcpf" /> -->
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Date of Birth:&nbsp;
											</td>
											<td>
												<html:text property="dateOfBirth" name="empinfoBean" readonly="true" />
												<!-- <input type="text" name="dbirth" />-->
											</td>

											<td class="screenlabel">
												Emoluments (Pay + DA):&nbsp;
											</td>
											<td>

												<html:text property="emoluments" name="empinfoBean" readonly="true" />
												<!-- <input type="text" name="emoluments" />-->
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Amount of&nbsp;
												<br />
												PFW/Advance Required:&nbsp;
											</td>
											<td>
												<html:text property="advReqAmnt" name="empinfoBean" tabindex="3" />
											</td>

											<td class="screenlabel">
												&nbsp;
											</td>

											<td>
												<html:text property="advanceTransID" name="empinfoBean" />
											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Approved:&nbsp;
											</td>
											<td>
												<html:radio property="approvestatus" value="A"></html:radio>
											</td>
											<td class="screenlabel">
												Reject:&nbsp;
											</td>
											<td>
												<html:radio property="approvestatus" value="R"></html:radio>
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
															<input type="submit" class="btn" value="Submit" tabindex="9" />
														</td>
														<td align="right">

															<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="10" />
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

