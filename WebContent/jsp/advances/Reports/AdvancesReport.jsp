



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
	String headQuaters="",fileName="",reportType="";	
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Advances_Report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="38%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td>
									<p align="justify">
										<strong> AIRPORTS AUTHORITY OF INDIA</strong>
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<logic:present name="advanceReportList">
					<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

						<tr>
							<td colspan="10" align="center">
								<strong><bean:write name="reportbean" property="screenTitle" /></strong>
							</td>
						</tr>
						<tr>
							<td colspan="10" align="center">
								<strong>Year&nbsp;:&nbsp;&nbsp;<bean:write name="reportbean1" property="transMnthYear" /></strong>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>

					<tr>
						<td colspan="2">

							<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="8" class="reportContentlabel">
										Month &nbsp;:&nbsp;&nbsp;
										<bean:write name="reportbean1" property="month" />
									</td>
									<td class="reportContentlabel" align="right">
										Region&nbsp;:&nbsp;&nbsp;
										<bean:write name="reportbean1" property="region" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>


							<table width="90%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" class="reportContentlabel" width="5%">
										S.No
									</td>
									<td align="center" class="reportContentlabel" width="15%">
										Date
									</td>
									<td align="center" class="reportContentlabel" width="5%">
										Emp. No
									</td>
									<td align="center" class="reportContentlabel" width="5%">
										PF ID
									</td>
									<td align="center" class="reportContentlabel" width="5%">
										CPF No
									</td>
									<td align="center" class="reportContentlabel" width="30%">
										Name & Designation
									</td>
									<td align="center" class="reportContentlabel" width="5%">
										Purpose
									</td>
									<td align="center" class="reportContentlabel" width="10%">
										Amount Required
									</td>
									<td align="center" class="reportContentlabel" width="10%">
										Amount Sanctioned
									</td>
									<td align="center" class="reportContentlabel" width="10%">
										Remarks & Signature
									</td>
								</tr>

								<tr>
									<td align="center" class="reportContentlabel">
										1
									</td>
									<td align="center" class="reportContentlabel">
										2
									</td>
									<td align="center" class="reportContentlabel">
										3
									</td>
									<td align="center" class="reportContentlabel">
										4
									</td>
									<td align="center" class="reportContentlabel">
										5
									</td>
									<td align="center" class="reportContentlabel">
										6
									</td>
									<td align="center" class="reportContentlabel">
										7
									</td>
									<td align="center" class="reportContentlabel">
										8
									</td>
									<td align="center" class="reportContentlabel">
										9
									</td>
									<td align="center" class="reportContentlabel">
										10
									</td>
								</tr>
								<logic:iterate id="sorder" name="advanceReportList" indexId="slno">
									<tr>
										<td class="reportContentdata">
											<%=slno.intValue()+1%>
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="advtransdt" />
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="employeeNo" />
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="pfid" />
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="cpfaccno" />
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="employeeName" />
											,
											<bean:write name="sorder" property="designation" />
										</td>
										<td class="reportContentdata">
											<bean:write name="sorder" property="advanceType" />
										</td>
										<td align="right" class="reportContentdata">
											<bean:write name="sorder" property="requiredAmt" />
										</td>
										<td align="right" class="reportContentdata">
											<bean:write name="sorder" property="approvedAmt" />
										</td>
										<td align="right" class="reportContentdata">
											&nbsp;
										</td>

									</tr>
								</logic:iterate>

							</table>
						</td>
					</tr>

					<tr>
						<td class="screenlabel">
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="50%" class="screenlabel">
							&nbsp;
						</td>
						<td width="50%">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
			</table>
			</logic:present>
		</html:form>
	</body>
</html>
