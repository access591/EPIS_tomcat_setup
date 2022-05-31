



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
								<td width="26%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td width="40%">
									<strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; AIRPORTS AUTHORITY OF INDIA</strong>
								</td>
								<td width="30%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td width="62%">
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
								<td width="24%" rowspan="2">
									&nbsp;
								</td>
								<td width="70%" class="reportsublabel">
									<strong>Application for PFW from CPF accumulation Unit</strong>
									<bean:write name="reportBean" property="department" />
								</td>
							</tr>
							<tr>

								<td width="70%" class="reportsublabel">
									<strong>Application for the PFW for the Purpose of </strong>
									<bean:write name="reportBean" property="dispPurposeTypeText" />
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<logic:present name="reportBean">
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td width="15%">
										&nbsp;
									</td>
									<td width="30%" class="reportsublabel">
										KEY ID
									</td>
									<td width="3%">
										:
									</td>
									<td width="37%" class="reportdata">
										<bean:write name="reportBean" property="advanceTransID" />
									</td>
									</td width="15%">
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										PF ID
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="pfid" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Employee code
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="employeeNo" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Name (IN BLOCK LETTERS)
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="employeeName" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Designation
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="designation" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Father&rsquo;s Name
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="fhName" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Department
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="department" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Place of Posting
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="placeofposting" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Date of joining in AAI
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="dateOfJoining" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Date of joining Fund
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="dateOfMembership" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Date of Birth
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="dateOfBirth" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Emoluments (Pay +DA)
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="emoluments" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Form Fill Date
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="transMnthYear" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Amount of PFW Required
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="advReqAmnt" />
									</td>
								</tr>

								<logic:equal property="purposeType" value="HBA" name="reportBean">
									<logic:notEqual property="actualcost" value="0.00" name="reportBean">
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												Actual Cost of Purpose
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="actualcost" />
											</td>
										</tr>
									</logic:notEqual>
								</logic:equal>
								<logic:equal property="purposeType" value="SUPERANNUATION" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Purposes for PFW
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeType" />
										</td>
									</tr>
								</logic:equal>

								<bean:define id="chkPurposeTypeHBA" name="reportBean" property="purposeType" type="String" />
								<logic:equal property="pfwPurHBA" value="<%=chkPurposeTypeHBA%>" name="advanceForm">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Purposes for PFW
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeType" />
											(
											<bean:write name="reportBean" property="dispPurposeOptionTxt" />
											)
										</td>
									</tr>
								</logic:equal>
								<logic:notEqual property="purposeType" value="SUPERANNUATION" name="reportBean">
								<logic:notEqual property="pfwPurHBA" value="<%=chkPurposeTypeHBA%>" name="advanceForm">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Purposes for PFW
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="dispPurposeTypeText" />
										</td>
									</tr>
								</logic:notEqual>
							</logic:notEqual>

								<bean:define id="selPurposeTypeHE" name="reportBean" property="purposeType" type="String" />
								<logic:equal property="pfwPurHE" value="<%=selPurposeTypeHE%>" name="advanceForm">

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyEmpName" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Date Of Birth
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyDOB" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Age
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyAge" />
											Years
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name of course
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="nmCourse" />
										</td>
									</tr>

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name of institution
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="nmInstitue" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Address of institution
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="addrInstitue" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Duration of course
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="curseDuration" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Last examination passed
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="heLastExaminfo" />
										</td>
									</tr>
								</logic:equal>
								<bean:define id="selPurposeTypeHBA" name="reportBean" property="purposeType" type="String" />
								<logic:equal property="pfwPurHBA" value="<%=selPurposeTypeHBA%>" name="advanceForm">

									<logic:equal property="purposeOptionType" value="<%=Constants.APPLICATION_HBA_PURPOSE_REPAYMENTHBA%>" name="reportBean">
									
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												a)Name
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="hbaloanname" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												b)Address
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="hbaloanaddress" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												c)Outstanding amount of loan
												<br />
												With interest as on
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="osamountwithinterest" />
											</td>
										</tr>
									</logic:equal>

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name of Present owner of house
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaownername" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Address of present owner
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaowneraddress" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Area of proposed of Flat/Plot (Sq.m)
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											&nbsp;
											<bean:write name="reportBean" property="hbaownerarea" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Plot/House/Flat No.
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaownerplotno" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Locality
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaownerlocality" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name of Municipality or other local authority
											<br />
											Under whose jurisdiction it is located
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaownermuncipal" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											City
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbaownercity" />
										</td>
									</tr>

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Property Address
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="propertyaddress" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Whether HBA is drawn from AAI
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbadrwnfrmaai" />
										</td>
									</tr>


									<logic:match name="reportBean" property="hbadrwnfrmaai" value="Y">

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												Purpose
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="hbawthdrwlpurpose" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												Amount
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="hbawthdrwlamount" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												Address
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="hbawthdrwladdress" />
											</td>
										</tr>
									</logic:match>

									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Intimated to AAI for permission
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="hbapermissionaai" />
										</td>
									</tr>
								</logic:equal>



								<logic:equal property="purposeType" value="MARRIAGE" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Name
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyEmpName" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Date Of Birth
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyDOB" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Age
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="fmlyAge" />
											Years
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Marriage Date
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="marriagedate" />
										</td>
									</tr>

								</logic:equal>
							<logic:notEqual property="purposeType" value="SUPERANNUATION" name="reportBean">
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Please state if similar withdrawal was made in the past, if so gives details
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="chkWthdrwlInfo" />
									</td>
								</tr>
							</logic:notEqual>
								<logic:equal value="Y" property="chkWthdrwlInfo" name="reportBean">
									<logic:present name="wthdrwlist">
										<logic:iterate name="wthdrwlist" id="wthdrwdetails" indexId="slno">
											<logic:equal value="1" property="wthdrwid" name="wthdrwdetails">
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label">
														a.Give Previous withdrawal Details
													</td>
													<td>
														:
													</td>
													<td class="reportdata">
														1.
														<bean:write name="wthdrwdetails" property="wthdrwlpurpose" />
													</td>

												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label">
														b.Amount of withdrawal
													</td>
													<td>
														:
													</td>
													<td class="reportdata">
														<bean:write name="wthdrwdetails" property="wthdrwlAmount" />
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label">
														c.Date of withdrawal
													</td>
													<td>
														:
													</td>
													<td class="reportdata">
														<bean:write name="wthdrwdetails" property="wthDrwlTrnsdt" />
													</td>
												</tr>
											</logic:equal>
											<logic:notEqual value="1" property="wthdrwid" name="wthdrwdetails">
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label" align="center">
														&nbsp;
													</td>
													<td>
														:
													</td>
													<td class="reportdata">
														<%=slno.intValue()+1%>
														.
														<bean:write name="wthdrwdetails" property="wthdrwlpurpose" />
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label">
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td class="reportdata">
														<bean:write name="wthdrwdetails" property="wthdrwlAmount" />
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
													<td class="label">
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td class="reportdata">
														<bean:write name="wthdrwdetails" property="wthDrwlTrnsdt" />
													</td>
												</tr>
											</logic:notEqual>
										</logic:iterate>
									</logic:present>
								</logic:equal>
								<logic:equal value="Y" property="paymentinfo" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											<u>Bank Details for payment</u>
										</td>
										<td>
											&nbsp;
										</td>
										<td class="reportdata">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Name (Appearing in the saving bank A/c )
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="bankempname" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Address of Employee (as per bank Record)
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="bankempaddrs" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Bank Name
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="bankname" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Branch Address
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="branchaddress" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Saving bank A/c No.
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="banksavingaccno" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											RTGS Code&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="bankemprtgsneftcode" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Employee mail ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="empmailid" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											MICR No.(Printed on cheque book)
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBankBean" property="bankempmicrono" />
										</td>
									</tr>

									<logic:equal value="Y" property="chkBankFlag" name="reportBean">
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="label">
												a) Party Name
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="partyName" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="label">
												b) Party Address
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="partyAddress" />
											</td>
										</tr>
									</logic:equal>
								</logic:equal>
								<bean:define id="loddocument" property="lodInfo" name="reportBean" />
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										<u>List of Documents to be enclosed</u>&nbsp;&nbsp;
									</td>
									<td>
										&nbsp;&nbsp;
									</td>
									<td class="reportdata">
										&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>

									<td class="reportdata" colspan="3" nowrap="nowrap">
										<%=loddocument%>
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
									<td class="reportdata">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="label" colspan="3">
										CONDITIONS
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="label" colspan="3">
										I undertake to comply with the following conditions:
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContent" colspan="3">
										<%
            String str="";
             String[] strarr=new String[9];
             
            
             str=Constants.APPLICATION_PFW_CONDITIONS;
             
              strarr=str.split(":");
            for(int i=0;i<strarr.length;i++){
         %>
										<%=strarr[i]%>
										<BR>

										<%
         }
         %>

									</td>
									<td>
										&nbsp;
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
									<td class="label" colspan="3">
										DECLARATION
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td class="label" colspan="3">
										I declare that---
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
									<td class="reportContent" colspan="3">
										<%
           // String str="";
            // String[] strarr=new String[9];
             
            
             str=Constants.APPLICATION_DECLARATION_OFFICE;
             
              strarr=str.split(":");
            for(int i=0;i<strarr.length;i++){
         %>
										<%=strarr[i]%>
										<BR>

										<%
         }
         %>

									</td>
									<td>
										&nbsp;
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


								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>

				</logic:present>
				<tr>

					<td height="22" colspan="2" class="reportsublabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="15%">
									&nbsp;
								</td>
								<td width="30%" class="reportsublabel">
									&nbsp;
								</td>
								<td width="10%">
									&nbsp;
								</td>
								<td width="37%" class="reportsublabel">
									&nbsp;__________________
								</td>
								<td width="5%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportsublabel">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td class="reportsublabel">
									Signature of applicant
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
