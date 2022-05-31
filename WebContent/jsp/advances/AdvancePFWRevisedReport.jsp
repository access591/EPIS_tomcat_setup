<!--
/*
  * File       : AdvancePFWSanctionOrderReport.jsp
  * Date       : 02/12/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					System.out.println("------------"+basePath);
String headQuaters="",fileName="",reportType="";					
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
										<strong> Verified by Finance & Accounts(Revised)</strong>
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
						<td colspan="2" align="center">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2" align="center" class="reportlabel"></td>
					</tr>

					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
								 
								 
								 <logic:notEqual value="Superannuation" name="PFWSanctionOrderBean" property="purposeType">
								 
									 
								<tr>   
									<td colspan="6" align="center">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>

												<td width="10%" class="reportContent" colspan="5" align="justify">
													<p STYLE="text-indent: 1cm">
														Sanction is hereby accorded under
														<bean:write name="PFWSanctionOrderBean" property="trust" />
														ECPF Regulation



														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Hba">
															<bean:write name="PFWSanctionOrderBean" property="prpsecvrdclse" />
														</logic:equal>

														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Higher Education">
															<bean:write name="PFWSanctionOrderBean" property="prpsecvrdclse" />
														</logic:equal>

														grant of part final withdrawal of Rs.
														<bean:write name="PFWSanctionOrderBean" property="amntAproved" />
														(Rs.
														<bean:write name="PFWSanctionOrderBean" property="advanceApprovedCurr" />
														Only) from employee subscription

														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Hba">

															<logic:notEqual name="PFWSanctionOrderBean" property="contributionAmt" value="">
																<logic:notEqual name="PFWSanctionOrderBean" property="purposeOptionType" value="Renovation House">
														 
														and employer contribution(Rs. <bean:write name="PFWSanctionOrderBean" property="subscriptionAmt" />
														 + Rs <bean:write name="PFWSanctionOrderBean" property="contributionAmt" />)
														
														</logic:notEqual>
															</logic:notEqual>
														</logic:equal>


														to Sh.
														<bean:write name="PFWSanctionOrderBean" property="employeeName" />
														<bean:write name="PFWSanctionOrderBean" property="designation" />
														<bean:write name="PFWSanctionOrderBean" property="station" />

														from his CPF A/c No
														<bean:write name="PFWSanctionOrderBean" property="cpfaccno" />


														and PF ID
														<bean:write name="PFWSanctionOrderBean" property="pfid" />

														for

														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Hba">
															<bean:write name="PFWSanctionOrderBean" property="purposeOptionType" />
														at
														<bean:write name="BasicReportBean" property="propertyaddress" />.											
														
														</logic:equal>

														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Higher Education">

															<bean:write name="PFWSanctionOrderBean" property="purposeType" />
														of
														
														<logic:equal name="PFWSanctionOrderBean" property="gender" value="M">
														his
														</logic:equal>
															<logic:equal name="PFWSanctionOrderBean" property="gender" value="F">
														her
														</logic:equal>

															<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="SON">
													       son
													      </logic:equal>
															<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="DAUGHTER">
													       daughter
													      </logic:equal>
																																									

														
														from
														<bean:write name="BasicReportBean" property="nmInstitue" />	
														persuing  											
														<bean:write name="BasicReportBean" property="nmCourse" />.			
														</logic:equal>

														<logic:equal name="PFWSanctionOrderBean" property="purposeType" value="Marriage">
															<bean:write name="PFWSanctionOrderBean" property="purposeType" />
														of &nbsp;his

														<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="SON">
													       son
													      </logic:equal>
															<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="DAUGHTER">
													       daughter
													      </logic:equal>

															<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="SON">
													       Mr.
													      </logic:equal>

															<logic:equal name="PFWSanctionOrderBean" property="purposeOptionType" value="DAUGHTER">
													       Km.
													      </logic:equal>



															<bean:write name="BasicReportBean" property="fmlyEmpName" />
														on <bean:write name="BasicReportBean" property="marriagedate" />
														</logic:equal>

													</P>
												</td>

											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td width="10%" class="reportContent" colspan="5" align="justify">
													<p STYLE="text-indent: 1cm">
														CHQ is requested to make the payment to the
												 
														<logic:equal name="PFWSanctionOrderBean" property="partyname" value="---">
															<bean:write name="PFWSanctionOrderBean" property="employeeName" />.
													</logic:equal>

														<logic:notEqual name="PFWSanctionOrderBean" property="partyname" value="---">
															<bean:write name="PFWSanctionOrderBean" property="partyname" />.
													</logic:notEqual>
												</td>
											</tr>
											</logic:notEqual>
											
											
											 <logic:equal value="Superannuation" name="PFWSanctionOrderBean" property="purposeType">
											 <tr>
							<td colspan="6">

								<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9" class="reportContentlabel">
											Sub:  CPF Part Final Withdrawal
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td colspan="9" class="reportContent">
											<p STYLE="text-indent: 1cm">
												Sanction of the president AAI EPF Trust is hereby conveyed for the withdrawal within one year before the retirement as per the AAI EPF Rules 30. The detail of CPF dues is as Follows:- 

											</p>
										</td>
									</tr>
									<tr>
									<td>
									<table width="100%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td class="reportContentlabel" align="center">
											S.No
										</td>
										<td class="reportContentlabel" align="center">
											PF ID
										</td>
										<td class="reportContentlabel" align="center">
											Name & Designation S/Ms./Sh .
										</td>
										<td class="reportContentlabel" align="center">
											Station
										</td>
										<td class="reportContentlabel" align="center">
											Employee Subscription
											<br>
											Rs
										</td>
										 
										<td class="reportContentlabel" align="center">
											AAI Contribution
											<br>
											Rs
										</td>
										

										
											<td class="reportContentlabel" align="center">
												Net Amount Payable
												<br>
												Rs
												<br>
												(5+6)
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
									</tr>
													<tr>
										<td align="center" class="reportContentdata">
											1
										</td>
										<td align="center" class="reportContentdata">
											
												<bean:write name="PFWSanctionOrderBean" property="pfid" />
										</td>
										<td align="center" class="reportContentdata">
											<bean:write name="PFWSanctionOrderBean" property="employeeName" />
											<br>
														<bean:write name="PFWSanctionOrderBean" property="designation" />
													
										</td>
										<td align="center" class="reportContentdata">
												<bean:write name="PFWSanctionOrderBean" property="station" />
										</td>
										<td align="center" class="reportContentdata">
											 <bean:write name="PFWSanctionOrderBean" property="subscriptionAmt" />
										</td>
										<td align="center" class="reportContentdata">
											<bean:write name="PFWSanctionOrderBean" property="contributionAmt" />
										</td>
										<td align="center" class="reportContentdata">
												<bean:write name="PFWSanctionOrderBean" property="amntAproved" />
										</td>
										
									
									</tr>
									</table>
									</td>
									</tr>
									
											 </logic:equal>
											<tr>
											<td>
			<logic:equal name="PFWSanctionOrderBean" property="paymentinfo" value="Y">         	
         	<logic:present name="bankMasterBean">
         		
         		<table width="85%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="8">
						&nbsp;
				</td>
				</tr>
         	
        		<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">Bank details is given below :-</td>
            	<td>&nbsp;</td>
 				</tr>  
 				</table>
 			
			<table width="70%" border="0" align="center" cellspacing="0" cellpadding="0">        		 
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel" >1. Name of Employee as per Saving Bank Account</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankempname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">2. Name Of Bank</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">3. Bank Account Number</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="banksavingaccno"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">4. IFSC Code</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankemprtgsneftcode"/></td>
            	<td>&nbsp;</td>
 				</tr>   
 				<logic:notEqual name="bankMasterBean" property="partyName" value="---">
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">5. Party Name</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="partyName"/></td>
            	<td>&nbsp;</td>
 				</tr>   
 				</logic:notEqual> 
 				<logic:notEqual  name="bankMasterBean" property="partyAddress" value="---">
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">6. Party Address</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="partyAddress"/></td>
            	<td>&nbsp;</td>
 				</tr>   
 				</logic:notEqual>   
 			 </table>
 			   
 			
        	</logic:present>
         </logic:equal>        
        
								</td>			
											</tr>
											
											<tr>
												<td>
													&nbsp;
												</td>
											</tr>
											
											
											 <logic:notEqual value="Superannuation" name="PFWSanctionOrderBean" property="purposeType">
											<tr>
												<td width="10%" class="reportContent" colspan="5">
													<p STYLE="text-indent: 1cm">

														<logic:equal name="PFWSanctionOrderBean" property="gender" value="M">
															Sh.
														</logic:equal>
														<logic:equal name="PFWSanctionOrderBean" property="gender" value="F">
														Smt.
														</logic:equal>


														<bean:write name="PFWSanctionOrderBean" property="employeeName" />
														<bean:write name="PFWSanctionOrderBean" property="designation" />
														<bean:write name="PFWSanctionOrderBean" property="station" />
														is required to produce a certificate that the amount of withdrawal has been utilized for the purpose for which it was sanctioned. The official concerned is advised to ensure compliance with all the formalities relating to the said purpose.
												</td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
											 <td width="10%" class="reportContentlabel" colspan="5" >
							 
												<p STYLE="text-indent: 1cm">
 				  					 			 No payment to be released by region/station against this sanction order.</p>
 												 
											</td>
											</tr>
											</logic:notEqual>
											 <logic:equal value="Superannuation" name="PFWSanctionOrderBean" property="purposeType">
											 			<tr>
				 <td>
				 	<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="9">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContent">
					<p STYLE="text-indent: 1cm">
 				     As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the employee
 				     
 					</p>
 				</td>
 				</tr>
 				</table>
 			</td>
 			</tr>
 			<tr>
			<td>
 				<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="9">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContentlabel">
					 <p STYLE="text-indent: 1cm;font-size: 20px;font-weight: bold;">
 				       No payment to be released by region/station against this sanction order. </p> 
 				</td>
 				</tr>
 				</table>
				 </td>
				</tr>
				 			<tr>
			<td>
 				<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="9">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContentlabel">
					 <p STYLE="text-indent: 1cm;font-size: 20px;font-weight: bold;">
 				     Interest for the financial year 2011-12 has been calculated @ 9.5% provisionally. </p> 
 				</td>
 				</tr>
 				</table>
				 </td>
				</tr>
											 </logic:equal>
										</table>
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



							<!--	<tr>
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
								</tr>-->
							</table>
						</td>
					</tr>

					 
					<tr>
						<td class="reportContentdata" colspan="2">
							<logic:notEqual name="PFWSanctionOrderBean" property="narration" value="">
							Note:&nbsp;<bean:write name="PFWSanctionOrderBean" property="narration" />
							</logic:notEqual>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td height="22" colspan="2" class="reportContentdata">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 <logic:present name="transList"> 
                    <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="37">

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
					
					<tr>
					<td>&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
					</tr>
					
					
					<logic:present name="transList">
					  <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="38">
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
					
					<tr>
					<td>&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
					</tr>
					
					
					<logic:present name="transList"> 
					 <logic:iterate id="trans" name="transList" indexId="slno">
                    	<logic:equal name="trans" property="transCode"  value="39">
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
					
					 
								 
					 
					 
				</table> 
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
				</table>
			</html:form>
			 
		</center>
		
	</body>
</html>

 