<!--
/*
  * File       : AdvancePFWForm2.jsp
  * Date       : 07/10/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->



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
	</head>
	<body>
		<center>
			<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
				<table width="70%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="21%" rowspan="2">
										<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" alt="" />
									</td>
									<td width="58%" align="center">
										<strong> AIRPORTS AUTHORITY OF INDIA</strong>
									</td>
									<td width="15%">
										&nbsp;
									</td>
								</tr>
								<tr>

									<td align="center" nowrap="nowrap">
										<strong>(DEPARTMENT OF FINANCE &amp; ACCOUNTS)</strong>
									</td>
									<td width="15%">
										&nbsp;
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
										<strong> Verified by Finance & Accounts</strong>
									</td>
								</tr>
							</table>
						</td>

					</tr>
					<tr>
						<td colspan="2" align="center">
							&nbsp;
						</td>
					</tr>


					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">




								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Particulars as verified 
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										&nbsp;
									</td>
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
									<td class="reportContentdata">
										Key No
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="advanceTransID" />
									</td>
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
									<td class="reportContentdata">
										PF ID
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="pfid" />
									</td>
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
									<td class="reportContentdata">
										Date of Joining Fund 
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="dateOfMembership" />
									</td>
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
									<td class="reportContentdata">
										Name
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="employeeName" />
									</td>
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
									<td class="reportContentdata">
										Designation
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="designation" />
									</td>
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
									<td class="reportContentdata">
										Place Of Posting
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="placeofposting" />
									</td>
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
									<td class="reportContentdata">
										Purpose
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										
										<logic:notEqual name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
													<bean:write name="Form3ReportBean" property="purposeOptionTypeDesr" />  
            							</logic:notEqual>
            							<logic:equal name="Form3ReportBean" property="purposeType" value="HBA Others">
													<bean:write name="Form3ReportBean" property="purposeOptionTypeDesr" />  
            							</logic:equal>
            							<logic:equal name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
												<bean:write name="Form3ReportBean" property="purposeType" />  
            							</logic:equal>
            							<logic:equal name="Form3ReportBean" property="purposeType" value="PANDEMIC">
												<bean:write name="Form3ReportBean" property="purposeType" />  
            							</logic:equal>
									</td>
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
									<td class="reportContentsublabel">
										Emoluments(Pay + DA) &nbsp;
									</td>
									<td align="center">
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="emolumentsCurr" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Rule under which PFW is covered&nbsp;
									</td>
									<td align="center">
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="prpsecvrdclse" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Employee Subscription&nbsp;
									</td>
									<td align="center">
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="subscriptionAmtCurr" />
									</td>
								</tr>
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
										<bean:write name="Form3ReportBean" property="contributionAmtCurr" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Fund Available in CPF A/c&nbsp;
									</td>
									<td align="center">
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="CPFFundCurr" />
									</td>
								</tr>

								<logic:notEqual name="Form3ReportBean" property="purposeType" value="LIC">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											To be recommended Employee subscription Amount&nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="approvedsubamtcurr" />
										</td>
									</tr>
									<logic:equal name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											To be recommended Employer contribution Amount&nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="approvedcontamtcurr" />
										</td>
									</tr>
								</logic:equal>
								</logic:notEqual>


								<logic:equal name="Form3ReportBean" property="purposeType" value="HBA">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											To be recommended Employer contribution Amount&nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="approvedcontamtcurr" />
										</td>
									</tr>
								</logic:equal>
							
							
									<logic:equal name="Form3ReportBean" property="purposeType" value="PANDEMIC">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											To be recommended Employer contribution Amount&nbsp;
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
											First Installment to be released Employee subscription Amount   &nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="firstInsSubAmntCurr" />
										</td>
									</tr>
									
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentsublabel">
											First Installment to be released Employer contribution Amount&nbsp;
										</td>
										<td align="center">
											:
										</td>
										<td class="reportContentdata">
											<bean:write name="Form3ReportBean" property="firstInsConrtiAmntCurr" />
										</td>
									</tr>
								
							
							
							
							
							
								
								<logic:present name="bankMasterBean">
								<tr>
									<td>
										&nbsp;
									</td>
									<td colspan="3" class="reportsublabel">
										Bank Details for payment:
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Name (Appearing in the saving bank A/c)
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="bankempname" />
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Address of Employee(as per bank Record)
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="bankempaddrs" />
									</td>
								</tr>



								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Bank Name
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="bankname" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Branch Address
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="branchaddress" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Saving bank A/c No.
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="banksavingaccno" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										RTGS Code/IFSC Code
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="bankMasterBean" property="bankemprtgsneftcode" />
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
								</logic:present>

							</table>
						</td>
					</tr>
                    <logic:present name="transList"> 
                    <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="33">

					<tr>

						<td height="22" colspan="2" class="reportContentlabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="25%">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="21%">
										&nbsp;
									</td>
									<td width="29%" class="reportContentlabel" align="center" nowrap="nowrap">
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
									
									</td>
								</tr>

								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<td width="45%" class="reportContentlabel" align="center" nowrap="nowrap">
										(<bean:write name="trans" property="transDispSignName" />)
									</td>
								</tr>
								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<logic:notEqual name="trans" property="designation"  value="---">
									<td width="45%" class="reportContentlabel" align="center" nowrap="nowrap">
										<bean:write name="trans" property="designation" />
									</td>
									</logic:notEqual>
								</tr>
							</table>
						</td>
					</tr>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					
					<logic:notPresent name="transList">
					 
					<tr>

						<td height="22" colspan="2" class="reportContentlabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								 <tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<td width="45%" class="reportContentlabel" align="center" nowrap="nowrap">________________
										 
									</td>
								</tr>
								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<td width="45%" class="reportContentlabel" align="center" nowrap="nowrap">
										MANAGER –CPF SECTION
									</td>
								</tr>
							</table>
						</td>
					</tr>
					 
					</logic:notPresent>
					
				 
					
					<tr>
					<td>&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="70%" class="reportsublabel" align="center">
										Recommendation
									</td>
								</tr>

							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
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
					<logic:present name="transList">
					  <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="34">
					<tr>

						<td height="22" colspan="2" class="reportContentlabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5%">
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
									<td width="5%">
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
									<td width="5%">
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

							</table>
						</td>
					</tr>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					
					<logic:notPresent name="transList">
					
					 
					<tr>

						<td height="22" colspan="2" class="reportContentlabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								 
								<tr>
									<td width="5%">
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
									<td width="5%">
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
					<td>&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="70%" class="reportsublabel" align="center">
										Approved
									</td>
								</tr>

							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
								<tr>
									<td>
										&nbsp;
									</td>

									<logic:equal name="Form3ReportBean" property="purposeType" value="MARRIAGE">
										<td class="reportContentdata">
											100 % of own Subscription + Interest
										</td>
									</logic:equal>
									<logic:equal name="Form3ReportBean" property="purposeType" value="HE">
										<td class="reportContentdata">
											100 % of own Subscription + Interest
										</td>
									</logic:equal>
									<logic:equal name="Form3ReportBean" property="purposeType" value="HBA">
										<td class="reportContentdata">
											Employee subscription
										</td>
									</logic:equal>
									<logic:equal name="Form3ReportBean" property="purposeType" value="LIC">
										<td class="reportContentdata">
											Employee subscription
										</td>
									</logic:equal>
									<logic:equal name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
										<td class="reportContentdata">
											Employee subscription
										</td>
									</logic:equal>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="empshareCurr" />
									</td>
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
									<td class="reportContentdata">
										AAI Contribution
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="advancePFWForm4" property="contributionAmtCurr" />
									</td>
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
									<td class="reportContentdata">
										Amount of PFW applied
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="advnceRequestCurr" />
									</td>
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
									<td class="reportContentdata">
										Amount sanctioned
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form3ReportBean" property="amntRecommendedCurr" />
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
								</tr>



								<tr>
									<td colspan="2" align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td colspan="5" class="reportContentdata">
										The amount of Rs.
										<bean:write name="Form3ReportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write property="amntRecommendedDscr" name="Form3ReportBean" />
										only) is sanctioned provisionally under rule
										<bean:write property="prpsecvrdclse" name="Form3ReportBean" />
										subject to ratification at the next board of trustee's meeting.
									</td>
								</tr>

							</table>
						</td>
					</tr>
					<logic:present name="transList"> 
					 <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="35">
                    	
					<tr>
						<td height="22" colspan="2" class="reportContentdata">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5%">
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
									<td width="5%">
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
									<td width="5%">
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

		
		

							</table>
						</td>
					</tr>
					</logic:equal>
					</logic:iterate>
					</logic:present>
				<logic:notPresent name="transList">
				 
					<tr>
						<td height="22" colspan="2" class="reportContentdata">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								 
								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<td width="45%" class="reportContentlabel" align="center">_______________
										 
									</td>
								</tr>
								<tr>
									<td width="5%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										&nbsp;
									</td>
									<td width="1%">
										&nbsp;
									</td>
									<td width="45%" class="reportContentlabel" align="center">
										PRESIDENT –ECPF TRUST
									</td>
								</tr>

		
		

							</table>
						</td>
					</tr>
					 
				</logic:notPresent>	
					
				</table>
			</html:form>
		</center>
	</body>
</html>
