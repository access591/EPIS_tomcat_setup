



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
					
                    <tr>
                    <td colspan="2">
                    <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

						<tr>
							<td colspan="10" align="center">
								<strong> Directorate of Finance & Accounts</strong>
							</td>
						</tr>
						<tr>
							<td colspan="10" align="center">
								<strong> CPF Section</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>                    
                    </td>
                    </tr>
					

					<tr>
						<td colspan="2">

							<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								
								

								<tr>
								
								
								    <td colspan="9">
								    <table width="84%" border="0" align="center" cellspacing="0" cellpadding="0">

										<tr>
										   
											<td colspan="12" class="reportdata" align="center">
												 Period From &nbsp;
										<bean:write name="reportbean1" property="fromYear" /> - <bean:write name="reportbean1" property="toYear" />
											</td>
										</tr>
										
										
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
								  </table>
										
									</td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								</tr>
							</table>


							<table width="90%" border="1" class="border" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" class="reportsublabel" width="5%">
										S.No
									</td>
									<td align="center" class="reportsublabel" width="15%">
										Date
									</td>
									<td align="center" class="reportsublabel" width="5%">
										SAP EMP No
									</td>
									<td align="center" class="reportsublabel" width="5%">
										PF ID
									</td>
									<td align="center" class="reportsublabel" width="5%">
										CPF No
									</td>
									<td align="center" class="reportsublabel" width="30%">
										Name & Designation
									</td>
									<td align="center" class="reportsublabel" width="5%">
										Place of Posting
									</td>
									<td align="center" class="reportsublabel" width="5%">
										Purpose(Sub Purpose)
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Required
									</td>
									<td align="center" class="reportsublabel" width="10%">
										No. Of Installment
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Per Installment
									</td>
									<td align="center" class="reportsublabel" width="10%">
										No. Of Interest Installment
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Interest Installment Amount
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Sanctioned
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
										Payment Status
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Payment Voucher No.
									</td>
								</tr>

								
								<logic:iterate id="sorder" name="advanceReportList" indexId="slno">
									<tr>
										<td class="reportdata">
											<%=slno.intValue()+1%>
										</td>
										<td class="reportdata" nowrap="nowrap">
											<bean:write name="sorder" property="advtransdt" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="employeeNo" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="pfid" />
										</td>
										<td class="reportdata" nowrap="nowrap">
											<bean:write name="sorder" property="cpfaccno" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="employeeName" />
											,
											<label style="white-space: nowrap"><bean:write name="sorder" property="designation" /></label>
										</td>
										<td class="reportdata" nowrap="nowrap">
											<bean:write name="sorder" property="placeofposting" />
										</td>
										<td class="reportdata">
											<bean:write name="sorder" property="purposeType" />
											(<label style="white-space: nowrap"><bean:write name="sorder" property="purposeOptionType" /></label>)
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="requiredAmt" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="cpfIntallments" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="mthinstallmentamt" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="interestinstallments" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="intinstallmentamt" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="approvedAmt" />
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
											<bean:write name="sorder" property="cbstatus" />
										</td>
										<td align="right" class="reportdata">
												<bean:write name="sorder" property="cbvocherno" />
										</td>

									</tr>
								</logic:iterate>
								
								<tr>
										<td class="reportdata" align="right" colspan="8">
											Grand Total
										</td>
										
										
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="totalrequiredamt" />
										</td>
										<td align="right" class="reportdata">
											&nbsp;
										</td>
										<td align="right" class="reportdata">											
											<bean:write name="sorder" property="totalMthInstallAmt" />											
										</td>
										<td align="right" class="reportdata">
											&nbsp;
										</td>
										<td align="right" class="reportdata">											
											<bean:write name="sorder" property="totalIntInstallAmt" />
										</td>
										<td align="right" class="reportdata">
											<bean:write name="sorder" property="totalsanctionedamt" />
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

					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="9" class="reportdata">
									It is requestes to recover the advanced from the salary as per details given above commencing from  &nbsp;
									<bean:write name="reportbean1" property="fromYear" /> - <bean:write name="reportbean1" property="toYear" />.
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
					<!--East REgion -->
				<logic:present  name="userbean">
				<logic:iterate id="userbean" name="userbean">
				<logic:notEqual name="userbean"  property="profileType" value="C"> 	
					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>								
									<td width="30%" align="right"  class="reportContentlabel" >
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="userbean" property="esignatoryName" />" width="150" height="48" border="no" alt="" />
            						</td>
								</tr>
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="displayName"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="designation"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									&nbsp;
									</td>
									<td class="reportdata" align="right">
									&nbsp;
									</td>
								</tr>
								</table>
								</td>
					</tr>
					</logic:notEqual>
					
					<logic:equal name="userbean"  property="profileType" value="C"> 		
					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>								
									<td width="30%" align="right"  class="reportContentlabel" >
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="userbean" property="esignatoryName" />" width="150" height="48" border="no" alt="" />
            						</td>
								</tr>
								<tr>
									<td colspan="8" class="reportdata">
									To, The Asstt.General 
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="displayName"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									Manager(F&A) Cash Section, 
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="designation"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									Rajiv Gandhi Bhavan 
									</td>
									<td class="reportdata" align="right">
									&nbsp;
									</td>
								</tr>
								</table>
								</td>
					</tr>
					</logic:equal>
					</logic:iterate>
				</logic:present>
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
