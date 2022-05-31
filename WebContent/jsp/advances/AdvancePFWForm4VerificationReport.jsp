


<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
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
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="29%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td width="71%">
									<strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; AIRPORTS AUTHORITY OF INDIA</strong>
								</td>
							</tr>
							<tr>
								<td width="70%">
									<p align="left">
										<strong>(DEPARTMENT OF FINANCE &amp; ACCOUNTS)</strong>
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>

					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="40%" rowspan="2">
									&nbsp;
								</td>
								<td width="60%" class="reportsublabel">
									<strong> Verified by RHQ/CHQ</strong>
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">



							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td width="21%">
									&nbsp;
								</td>
								<td width="30%" class="reportContentlabel">
									&nbsp;
								</td>
								<td width="4%">
									&nbsp;
								</td>
								<td width="39%" class="reportContentdata">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												&nbsp;
											</td>
											<td width="34%" class="reportContentsublabel">
												PF ID &nbsp;
											</td>
											<td width="3%" align="center">
												:
											</td>
											<td width="42%" class="reportContentdata">
												<bean:write name="Form3ReportBean" property="pfid" />
											</td>
										</tr>

										<tr>
											<td width="25%">
												&nbsp;
											</td>
											<td width="34%" class="reportContentsublabel">
												Key No &nbsp;
											</td>
											<td width="3%" align="center">
												:
											</td>
											<td width="42%" class="reportContentdata">
												<bean:write name="Form3ReportBean" property="advanceTransID" />
											</td>
										</tr>

										<tr>
											<td width="25%">
												&nbsp;
											</td>
											<td width="34%" class="reportContentsublabel">
												Name&nbsp;
											</td>
											<td width="3%" align="center">
												:
											</td>
											<td width="42%" class="reportContentdata">
												<bean:write name="Form3ReportBean" property="employeeName" />
											</td>
										</tr>

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportContentsublabel">
												Designation &nbsp;
											</td>
											<td align="center">
												:
											</td>
											<td class="reportContentdata">
												<bean:write name="Form3ReportBean" property="designation" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportContentsublabel">
												Place of Posting &nbsp;
											</td>
											<td align="center">
												:
											</td>
											<td class="reportContentdata">
												<bean:write name="Form3ReportBean" property="placeofposting" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportContentsublabel">
												Purpose &nbsp;
											</td>
											<td align="center">
												:
											</td>
											<td class="reportContentdata">
												<logic:equal name="Form3ReportBean" property="purposeType" value="HBA">
													<bean:write name="Form3ReportBean" property="purposeType" /> ,
            									</logic:equal>
            								<logic:equal name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
													<bean:write name="Form3ReportBean" property="purposeType" />  
            									</logic:equal>
												<bean:write name="Form3ReportBean" property="purposeOptionTypeDesr" />
											</td>
										</tr>

										<logic:equal name="Form3ReportBean" property="purposeFlag" value="Y">
											<tr>
												<td>
													&nbsp;
												</td>
												<td class="reportContentsublabel">
													Recommended Employee subscription Amount &nbsp;
												</td>
												<td align="center">
													:
												</td>
												<td class="reportContentdata">
													<bean:write name="Form3ReportBean" property="approvedsubamtcurr" />
												</td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
												<td class="reportContentsublabel">
													Recommended Employer contribution Amount &nbsp;
												</td>
												<td align="center">
													:
												</td>
												<td class="reportContentdata">
													<bean:write name="Form3ReportBean" property="approvedcontamtcurr" />
												</td>
											</tr>

										</logic:equal>
										 
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportContentsublabel">
												Amount recommended for sanction of PFW of Rs. &nbsp;
											</td>
											<td align="center">
												:
											</td>
											<td class="reportContentdata">
												<bean:write name="Form3ReportBean" property="amntRecommendedCurr" />
											</td>
										</tr>


									</table>
								</td>
							</tr>
							<!-- <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel" >2. Amount recommended for sanction of PFW of Rs.</td>
              <td align="center">:</td>
            <td class="reportContentdata"><bean:write name="Form3ReportBean" property="amntRecommendedCurr"/></td>
            <td >&nbsp;</td>
          </tr>-->
							<tr>
							</tr>


						</table>
					</td>
				</tr>


				<tr>
					<td height="24">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td class="reportContentdata">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td height="24">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td class="reportContentdata">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
			</table>
			</td>
			</tr>
	<logic:present name="transList">
			<tr>

				<td height="22" colspan="2" class="reportContentlabel">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
						
						<logic:iterate id="trans" name="transList" indexId="slno">
						
						<logic:equal name="trans" property="transCode"  value="34">
						
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="25%">
								&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
							</td>
							<td width="5%">
								&nbsp;
							</td>
							<td width="29%" class="reportContentlabel" align="center">
								<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
									
							</td>
						</tr>

						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="45%" class="reportContentlabel" align="center">
								(<bean:write name="trans" property="transDispSignName" />)
							</td>
						</tr>
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
						<logic:notEqual name="trans" property="designation"  value="---">
							<td width="45%" class="reportContentlabel" align="center">
								<bean:write name="trans" property="designation" />
							</td>
						</logic:notEqual>
						</tr>
						
						</logic:equal>
						</logic:iterate>

					</table>
				</td>
			</tr>
			
			</logic:present>
			
			<logic:notPresent name="transList">
			<tr>

				<td height="22" colspan="2" class="reportContentlabel">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
						 
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="45%" class="reportContentlabel" align="center">________________
								 
							</td>
						</tr>
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="45%" class="reportContentlabel" align="center">
								SECRETARY –ECPF TRUST
							</td>
						</tr>
						 
					</table>
				</td>
			</tr>
			
			</logic:notPresent>
			
			
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
		</html:form>
	</body>
</html>
