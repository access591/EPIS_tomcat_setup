

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
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
								<td width="62%">
									<p align="left">
										<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; EMPLOYEE PROVIDENT FUND</strong>
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
								<td width="60%" class="reportlabel">
									<strong> HO: R.G.B. SAFDARJUNG AIRPORT, New Delhi</strong>
								</td>
							</tr>
							<tr>
								<td class="reportlabel">
									(APPLICATION FOR GRANT OF C.P.F. ADVANCE)
								</td>
							</tr>
						</table>
					</td>


				</tr>
				<logic:present name="reportBean">
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="label" align="center" colspan="3">
										<strong> PART – I</strong>
									</td>
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
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="37%" class="reportsublabel">
										Trust:&nbsp;
										<bean:write name="reportBean" property="trust" />
									</td>
									<td width="2%"></td>
									<td width="34%" class="reportsublabel">
										Place of Posting:
										<bean:write name="reportBean" property="placeofposting" />
									</td>
									<td width="10">
										&nbsp;
									</td>
								</tr>

								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="37%" class="reportsublabel">
										KEY ID
									</td>
									<td width="2%">
										:
									</td>
									<td width="34%" class="reportdata">
										<bean:write name="reportBean" property="advanceTransID" />
									</td>
									<td width="10">
										&nbsp;
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
									<td>
										&nbsp;
									</td>
								</tr>
								<!-- <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Employee code </td>
            <td>:</td>
            <td class="reportdata"><bean:write name="reportBean" property="employeeNo"/></td>
             <td>&nbsp;</td>
          </tr>-->
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
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
									<td>
										&nbsp;
									</td>
								</tr>
								<!-- <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Form Fill Date</td>
            <td>:</td>
            <td class="reportdata"><bean:write name="reportBean" property="transMnthYear"/></td>
             <td>&nbsp;</td>
          </tr>-->
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Emoluments (Pay +DA) as on
										<bean:write name="reportBean" property="transMnthYear" />
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="emoluments" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Amount of Advance Required
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="advReqAmnt" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportsublabel">
										Total No of Instalments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="cpfIntallments" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>

								<logic:equal property="advanceType" value="CPF" name="reportBean">
									<logic:equal property="purposeType" value="OTHERS" name="reportBean">
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="reportsublabel">
												Purposes
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="purposeType" />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="label">
												Reason Written
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="advReasonText" />
											</td>
										</tr>
									</logic:equal>
								</logic:equal>
								<logic:equal property="purposeType" value="DEFENCE" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Defence of court case For
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeOptionType" />
										</td>
									</tr>
								</logic:equal>
								<logic:present name="prpsOptionsCPF">
									<bean:define id="selPurpType" name="prpsOptionsCPF" type="String" />
									<logic:equal property="purposeType" value="<%=selPurpType%>" name="reportBean">
										<logic:equal property="purposeType" value="COST" name="reportBean">
											<tr>
												<td>
													&nbsp;
												</td>
												<td class="reportsublabel">
													Cost of Passage For
												</td>
												<td>
													:
												</td>
												<td class="reportdata">
													<bean:write name="reportBean" property="purposeOptionType" />
												</td>
											</tr>
										</logic:equal>
										<logic:equal property="purposeType" value="ILLNESS" name="reportBean">
											<tr>
												<td>
													&nbsp;
												</td>
												<td class="reportsublabel">
													Illness Expanses For
												</td>
												<td>
													:
												</td>
												<td class="reportdata">
													<bean:write name="reportBean" property="purposeOptionType" />
												</td>
											</tr>
										</logic:equal>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="label">
												a)Name
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
											<td class="label">
												b)Date of Birth
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
											<td class="label">
												c)Age
											</td>
											<td>
												:
											</td>
											<td class="reportdata">
												<bean:write name="reportBean" property="fmlyAge" />
												Years
											</td>
										</tr>

									</logic:equal>
								</logic:present>



								<logic:equal property="purposeType" value="OBMARRIAGE" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Marriage Expanses For
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeOptionType" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
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
										<td class="label">
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
										<td class="label">
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

								</logic:equal>

								<logic:equal property="purposeType" value="OBLIGATORY" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Purpose for Advance
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											Obligatory Expanses
										</td>
									</tr>

									<logic:equal property="purposeOptionType" value="OTHER CEREMONIES" name="reportBean">

										<!-- <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Ceremony Name</td>
            <td>:</td>
            <td class="reportdata"><bean:write name="reportBean" property="oblCermoney"/></td>
          </tr>-->


									</logic:equal>
								</logic:equal>
								<logic:equal property="purposeType" value="MARRIAGE" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
											Purpose
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeType" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
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
										<td class="label">
											Date Of Birth
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
										<td class="label">
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

								</logic:equal>
								<logic:equal property="purposeType" value="EDUCATION" name="reportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportsublabel">
											Higher Education For
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="purposeOptionType" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="label">
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
										<td class="label">
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
										<td class="label">
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
											Name of Course
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
											Duration of Course
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
										<td class="label">
											Name & Address of Institution
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
										<td class="label">
											&nbsp;
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
											Recognized
										</td>
										<td>
											:
										</td>
										<td class="reportdata">
											<bean:write name="reportBean" property="heRecog" />
										</td>
									</tr>
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
											RTGS Code&nbsp;/&nbsp;IFSC Code&nbsp;&nbsp;&nbsp;&nbsp;
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
									<!-- <tr>
            <td>&nbsp;</td>
            <td class="label">MICR No.(Printed on cheque book)</td>
            <td>:</td>
            <td class="reportdata"><bean:write name="reportBankBean" property="bankempmicrono"/></td>
          </tr>-->
								</logic:equal>
								<logic:equal property="advanceType" value="CPF" name="reportBean">
									<logic:notEqual property="purposeType" value="OBMARRIAGE" name="reportBean">
										<logic:equal property="advncerqddepend" value="Y" name="reportBean">
											<tr>
												<td colspan="4">
													<table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr>
															<td width="21%">
																&nbsp;
															</td>
															<td class="label" colspan="3">
																&nbsp;Certified that the person for whom the advance is required is actually dependent upon me
															</td>

															<!-- <td class="reportdata"><bean:write name="reportBean" property="advncerqddepend"/></td>-->
														</tr>
													</table>
												</td>

											</tr>
										</logic:equal>
										<logic:equal property="utlisiedamntdrwn" value="Y" name="reportBean">
											<tr>

												<td colspan="4">
													<table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr>
															<td width="21%">
																&nbsp;
															</td>
															<td class="label" colspan="3">
																&nbsp;Certified that the amount of advance will be utilised for the purpose which is drawn
															</td>

															<!--  <td class="reportdata"><bean:write name="reportBean" property="utlisiedamntdrwn"/></td>-->
														</tr>
													</table>
												</td>

											</tr>
										</logic:equal>


									</logic:notEqual>
								</logic:equal>
								<logic:notEqual property="lodInfo" name="reportBean" value="---">


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
								</logic:notEqual>
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

							</table>
						</td>
					</tr>

				</logic:present>

				<tr>

					<td height="22" colspan="2" class="reportsublabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td width="37%" class="reportsublabel">
									&nbsp;
								</td>
								<td width="2%">
									&nbsp;
								</td>
								<td width="34%" class="reportsublabel">
									&nbsp;___________________
								</td>
								<td width="10">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="reportsublabel">
									Date:
								</td>
								<td>
									&nbsp;
								</td>
								<td class="reportsublabel">
									Signature of applicant
								</td>
								<td>
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
									<bean:write name="reportBean" property="employeeName" />
								</td>
								<td>
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
