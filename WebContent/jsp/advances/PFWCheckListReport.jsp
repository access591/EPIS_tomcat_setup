<!--
/*
  * File       : PFWCheckListReport.jsp
  * Date       : 12/04/2009
  * Author     : R Suresh Kumar
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();

			%>

<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>


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

	<body>
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
				<td colspan="4">
					<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="35%" class="reportContentsublabel">
								PF ID&nbsp;
							</td>
							<td width="3%" align="center">
								:
							</td>
							<td width="37%" class="reportContentdata">
								<bean:write name="Form3ReportBean" property="pfid" />
							</td>
						</tr>
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="35%" class="reportContentsublabel">
								Key No&nbsp;
							</td>
							<td width="3%" align="center">
								:
							</td>
							<td width="37%" class="reportContentdata">
								<bean:write name="Form3ReportBean" property="advanceTransID" />
							</td>
						</tr>

						<tr>
							<td>
								&nbsp;
							</td>
							<td class="reportContentsublabel">
								Name &nbsp;
							</td>
							<td align="center">
								:
							</td>
							<td class="reportContentdata">
								<bean:write name="Form3ReportBean" property="employeeName" />
							</td>
						</tr>
						<logic:notEqual property="purposeType" name="Form3ReportBean" value="HBA">
						<logic:notEqual property="purposeType" name="Form3ReportBean" value="SUPERANNUATION">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Emoluments&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="Form3ReportBean" property="emolumentsCurr" />
								</td>
							</tr>
						</logic:notEqual>
						</logic:notEqual>
						<logic:equal property="purposeType" name="Form3ReportBean" value="HBA">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									<bean:write property="emolumentsLabel" name="advancePFWForm4" />
									&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="Form3ReportBean" property="mnthsemoluments" />
								</td>
							</tr>
						</logic:equal>
						<tr>
							<td width="25%">
								&nbsp;
							</td>
							<td width="35%" class="reportContentsublabel">
								Date of Joining Fund&nbsp;
							</td>
							<td width="3%" align="center">
								:
							</td>
							<td width="37%" class="reportContentdata">
								<bean:write name="Form3ReportBean" property="dateOfMembership" />
							</td>
						</tr>
						<logic:equal property="advanceType" name="Form3ReportBean" value="PFW">
							<logic:notEqual property="purposeType" name="Form3ReportBean" value="HBA">
								<logic:notEqual property="purposeType" name="Form3ReportBean" value="LIC">
									<logic:notEqual property="purposeType" name="Form3ReportBean" value="SUPERANNUATION">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											Dependent &nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="purposeOptionType" />
										</td>
									</tr>

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											Date of Birth&nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="dateOfBirth" />
										</td>
									</tr>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEqual>
						</logic:equal>
						<logic:equal property="purposeType" name="Form3ReportBean" value="HBA">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Whether HBA loan has been taken from AAI&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="basicReportBean" property="hbadrwnfrmaai" />
								</td>
							</tr>
						</logic:equal>
						<logic:notEqual property="purposeType" name="Form3ReportBean" value="SUPERANNUATION">
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="reportContentsublabel">
								Whether any part final has been taken past&nbsp;
							</td>
							<td align="center">
								:
							</td>
							<td class="reportContentdata">
								<bean:write name="Form3ReportBean" property="chkwthdrwlinfo" />
							</td>
						</tr>
					</logic:notEqual>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="reportContentsublabel">
								Employer Contribution&nbsp;
							</td>
							<td align="center">
								:
							</td>
							<td class="reportContentdata">
								<bean:write name="Form3ReportBean" property="contributionAmt" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="reportContentsublabel">
								Employee Suscription&nbsp;
							</td>
							<td align="center">
								:
							</td>
							<td class="reportContentdata">
								<bean:write name="Form3ReportBean" property="subscriptionAmt" />
							</td>
						</tr>

						<bean:define id="loddocument" property="lodInfo" name="Form3ReportBean" />
						<tr>

							<td class="reportsublabel" colspan="3" align="center">
								<u>List of Documents to be enclosed</u>&nbsp;&nbsp;
							</td>

						</tr>
						<logic:present name="LODList">
							<logic:iterate name="LODList" id="lod">
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>

									<td class="reportdata" colspan="3" nowrap="nowrap">
										<%=loddocument%>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>

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

			<logic:equal property="paymentinfo" name="Form3ReportBean" value="Y">
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="reportContentlabel">
						&nbsp;&nbsp;&nbsp;&nbsp;Bank Details for payment&nbsp;
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
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="35%" class="reportContentsublabel">
									Name (Appearing in the saving bank A/c ) &nbsp;
								</td>
								<td width="3%" align="center">
									:
								</td>
								<td width="37%" class="reportContentdata">
									<bean:write name="bankMasterBean" property="bankempname" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Address of Employee (as per bank Record)&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="bankempaddrs" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Bank Name&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="bankname" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Branch Address&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="branchaddress" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Saving bank A/c No.&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="banksavingaccno" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									RTGS Code&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="bankemprtgsneftcode" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									Employee mail ID&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="empmailid" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportContentsublabel">
									MICR No.(Printed on cheque book)&nbsp;
								</td>
								<td align="center">
									:
								</td>
								<td class="reportContentdata">
									<bean:write name="bankMasterBean" property="bankempmicrono" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</logic:equal>






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
		<logic:present name="transList">
			<tr>

				<td height="22" colspan="2" class="reportContentlabel">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<logic:iterate id="trans" name="transList" indexId="slno">
						<logic:equal name="trans" property="transCode"  value=" 31">
						<tr>
							<td width="10%">
								&nbsp;
							</td>
							<td width="25%">
								&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
							</td>
							<td width="21%">
								&nbsp;
							</td>
							<td width="29%" class="reportContentlabel" align="center">
								
								<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
									
							</td>
						</tr>
						
						<tr>
							<td width="10%">
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
							<td width="10%">
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
							 
						</tr>
						
						<tr>
							<td width="10%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="45%" class="reportContentlabel" align="center">
								__________________
							</td>
						</tr>
						<tr>
							<td width="10%">
								&nbsp;
							</td>
							<td width="30%" class="reportContentlabel">
								&nbsp;
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="45%" class="reportContentlabel" align="center">
								(Asst. Manager)
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

	</body>
</html>

