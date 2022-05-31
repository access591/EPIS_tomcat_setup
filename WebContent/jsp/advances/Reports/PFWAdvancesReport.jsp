



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
			<logic:present name="advanceReportList">
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

					<logic:present name="reportbean1">
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
										<td colspan="8" class="reportsublabel">
											From  &nbsp;:&nbsp;&nbsp;
											<%=request.getAttribute("FromDate")%> To <%=request.getAttribute("ToDate")%>
										</td>
										<td class="reportsublabel" align="right">
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


								<table width="90%" border="1" class="border" align="center" cellspacing="0" cellpadding="0">
									<thead>
										<tr>
											<td align="center" class="reportsublabel" width="5%">
												S.No
											</td>
											<td align="center" class="reportsublabel" width="15%">
												Date
											</td>											
											<td align="center" class="reportsublabel" width="5%">
												PF ID
											</td>
											<td align="center" class="reportsublabel" width="5%">
												Station Name
											</td>											
											<td align="center" class="reportsublabel" width="15%">
												Name & Designation
											</td>
											<td align="center" class="reportsublabel" width="5%">
												Purpose
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Amount Required
											</td>
											<td align="center" class="reportsublabel" width="5%">
												Actual cost
											</td>
											<td align="center" class="reportsublabel" width="5%">
												Employer (Subs .+ Cont.)
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Amount Sanctioned
											</td>
											<td align="center" class="reportsublabel" width="5%">
												Date of Sanctioned
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Name In Bank
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Bank Name
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Bank Acc No
											</td>
											<td align="center" class="reportsublabel" width="10%">
												RTGS Code
											</td>
											<td align="center" class="reportsublabel" width="10%">
												Remarks & Signature
											</td>
										</tr>

										<tr>
											<td align="center" class="reportsublabel">
												1
											</td>
											<td align="center" class="reportsublabel">
												2
											</td>
											<td align="center" class="reportsublabel">
												3
											</td>
											<td align="center" class="reportsublabel">
												4
											</td>
											<td align="center" class="reportsublabel">
												5
											</td>
											<td align="center" class="reportsublabel">
												6
											</td>
											<td align="center" class="reportsublabel">
												7
											</td>
											<td align="center" class="reportsublabel">
												8
											</td>
											<td align="center" class="reportsublabel">
												9
											</td>
											<td align="center" class="reportsublabel">
												10
											</td>
											<td align="center" class="reportsublabel">
												11
											</td>
											<td align="center" class="reportsublabel">
												12
											</td>
											<td align="center" class="reportsublabel">
												13
											</td>
											<td align="center" class="reportsublabel">
												14
											</td>
											<td align="center" class="reportsublabel">
												15
											</td>
											<td align="center" class="reportsublabel">
												16
											</td>
											 
										</tr>
									</thead>
									<logic:iterate id="sorder" name="advanceReportList" indexId="slno">
										<tr>
											<td class="reportdata">
												<%=slno.intValue()+1%>
											</td>
											<td class="reportdata" nowrap="nowrap">
												<bean:write name="sorder" property="advtransdt" />
											</td>											 
											<td class="reportdata">
												<bean:write name="sorder" property="pfid" />
											</td>
											<td class="reportdata">
												<bean:write name="sorder" property="airportcd" />
											</td>
											<td class="reportdata">
												<bean:write name="sorder" property="employeeName" />
												,
												<bean:write name="sorder" property="designation" />
											</td>
											<td class="reportdata">
												<logic:equal name="sorder" property="purposeType" value="HBA">
													<bean:write name="sorder" property="purposeType" />( <bean:write name="sorder" property="purposeOptionType" />)
            									</logic:equal>

												<logic:notEqual name="sorder" property="purposeType" value="HBA">
													<bean:write name="sorder" property="purposeType" />
												</logic:notEqual>

											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="requiredAmt" />
											</td>

											<td align="right" class="reportdata">
												<bean:write name="sorder" property="actualcost" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="emplrshare" />
											</td>

											<td align="right" class="reportdata">
												<bean:write name="sorder" property="approvedAmt" />
											</td>
											<td class="reportdata" nowrap="nowrap">
												<bean:write name="sorder" property="sanctiondt" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="bankempname" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="bankname" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="banksavingaccno" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="bankemprtgsneftcode" />
											</td>
											<td align="right" class="reportdata">
												&nbsp;
											</td>

										</tr>
									</logic:iterate>
									
									<tr>
											<td class="reportdata" colspan="6" align="right">
												Grand Total
											</td>
											
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalrequiredamt" />
											</td>

											<td align="right" class="reportdata">
												
												<bean:write name="sorder" property="totalactualcost" />
											</td>
											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalsubamt" />
											</td>

											<td align="right" class="reportdata">
												<bean:write name="sorder" property="totalsanctionedamt" />
											</td>
											<td class="reportdata" colspan="6" align="right">
												&nbsp;
											</td>
											 

										</tr>

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
			</logic:present>

			<logic:notPresent name="advanceReportList">
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
