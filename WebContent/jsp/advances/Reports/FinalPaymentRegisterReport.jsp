


<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
String headQuaters="",fileName="",reportType="";					
%>
<%
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Consolidated_Report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<logic:present name="detailList">

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

					<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

						<tr>
							<td colspan="12" align="center">
								<strong>A.A.I. Employees Contributory Provident Fund</strong>
							</td>
						</tr>
						<tr>
							<td colspan="12" align="center">
								<strong>Final Payment <%=request.getAttribute("arrearType")%></strong>
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

							<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>

								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td colspan="6" align="right" class="reportsublabel">
										From <%=request.getAttribute("fromDate")%>  To <%=request.getAttribute("toDate")%>
									</td>
								</tr>
								 
							</table>


							<table width="100%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" class="reportsublabel">
										S.No
									</td>
									<td align="center" class="reportsublabel">
										Name of Employee
									</td>
									<td align="center" class="reportsublabel">
										Designation
									</td>
									<td align="center" class="reportsublabel">
										PFID
									</td>
									<td align="center" class="reportsublabel">
										Station
									</td>
									<td align="center" class="reportsublabel">
										Region
									</td>
									<td align="center" class="reportsublabel">
										Retirement Resigned Expired other
									</td>
									<td align="center" class="reportsublabel" nowrap="nowrap">
										Date of Seperation
									</td>
									<logic:equal name="sanctionOrderBean" property="seperationreason" value="Death">
									<td align="center" class="reportsublabel">
										Name of Nominee
									</td>
									</logic:equal>
									<td align="center" class="reportsublabel" nowrap="nowrap">
										Date of Sanction
									</td>
									<td align="center" class="reportsublabel">
										EMP Subscription
									</td>
									<td align="center" class="reportsublabel">
										AAI Contribution
									</td>
									<td align="center" class="reportsublabel">
										Pension
									</td>
									<logic:equal name="sanctionOrderBean" property="seperationreason" value="VRS">
										<td align="center" class="reportsublabel">
											Adhoc Amount already paid
										</td>
									</logic:equal>
									<td align="center" class="reportsublabel">
										Total
									</td>
									<td align="center" class="reportsublabel">
										Signature
									</td>
								</tr>


								<logic:iterate id="sorder" name="detailList" indexId="slno">
									<tr>
										<td class="reportdata" align="center">
											<%=slno.intValue()+1%>
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="employeeName" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="designation" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="pfid" />
										</td>
										<td class="reportdata" align="center">
											<bean:write name="sorder" property="airportcd" />
										<td class="reportdata" align="center">
											<bean:write name="sorder" property="region" />
											<bean:define id="station" name="sorder" property="airportcd" />
										</td>
										<td align="center" class="reportdata">
											<bean:write name="sorder" property="seperationreason" />
										</td>
										<td align="center" class="reportdata">
											<bean:write name="sorder" property="seperationdate" />
										</td>
										<logic:equal name="sanctionOrderBean" property="seperationreason" value="Death">
										<td align="center" class="reportdata">
											<bean:write name="sorder" property="nomineename" />
										</td>
										</logic:equal> 
										<td align="center" class="reportdata">
											<bean:write name="sorder" property="sanctiondt" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="emplshare" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="emplrshare" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="pensioncontribution" />
										</td>
										<logic:equal name="sanctionOrderBean" property="seperationreason" value="VRS">
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="adhocamt" />
											</td>
										</logic:equal>

										<td align="right" class="reportdata">
											<bean:write name="sorder" property="netcontribution" />
										</td>
										<td align="center" class="reportdata">
											&nbsp;
										</td>
									</tr>
								</logic:iterate>
								<logic:equal name="sanctionOrderBean" property="seperationreason" value="VRS">
								<tr> 
											<td class="reportdata" colspan="9" align="right">
												Grand Total
											</td>
											
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalsubamt" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalConAmt" />
											</td>

											<td align="right" class="reportdata">												
												<bean:write name="sorder" property="totalPenConAmt" />
											</td>
											 <td align="right" class="reportdata">
												<bean:write name="sorder" property="totalAdhocAmt" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalNetConAmt" />
											</td>
											<td class="reportdata" nowrap="nowrap">
												&nbsp;
											</td>
											 
										</tr>
									</logic:equal>
								
								 
								<logic:notEqual name="sanctionOrderBean" property="seperationreason" value="VRS">
								<logic:notEqual name="sanctionOrderBean" property="seperationreason" value="Death">
								<tr> 
											<td class="reportdata" colspan="9" align="right">
												Grand Total
											</td>
											
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalsubamt" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalConAmt" />
											</td>

											<td align="right" class="reportdata">												
												<bean:write name="sorder" property="totalPenConAmt" />
											</td>											 
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalNetConAmt" />
											</td>
											<td class="reportdata" nowrap="nowrap">
												&nbsp;
											</td>
											 

										</tr>
										
								</logic:notEqual> 
								 </logic:notEqual>
								 
								 <logic:equal name="sanctionOrderBean" property="seperationreason" value="Death">
								<tr> 
											<td class="reportdata" colspan="10" align="right">
												Grand Total
											</td>
											
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalsubamt" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalConAmt" />
											</td>

											<td align="right" class="reportdata">												
												<bean:write name="sorder" property="totalPenConAmt" />
											</td>											 
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalNetConAmt" />
											</td>
											<td class="reportdata" nowrap="nowrap">
												&nbsp;
											</td>
											 

										</tr>
										
									 
								 </logic:equal>
								 
								 
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
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="12" class="reportdata">
										Date&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="12" class="reportdata">
										Authorised Signature
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
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
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
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
			<logic:notPresent name="detailList">
				<table width="100%" height="60%" border="0" cellspacing="0" cellpadding="0" valign="middle">
					<tr>
						<td align="center">
							<strong>No Records Found</strong>
						</td>
					</tr>
				</table>
			</logic:notPresent>
		</html:form>
	</body>
</html>
