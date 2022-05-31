

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
	</head>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="6">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="32%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" alt="" />
								</td>
								<td width="1%">
									&nbsp;
								</td>
								<td width="68%" class="reportlabel">
									AIRPORTS AUTHORITY OF INDIA
								</td>
							</tr>
							<tr>
								<td rowspan="2">
									&nbsp;
								</td>
								<td class="reportsublabel">
									&nbsp;&nbsp;&nbsp;EMPLOYEE PROVIDENT FUND
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="6" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="28%">
									&nbsp;
								</td>
								<td width="72%" class="reportsublabel">
									HO: R.G.B. SAFDARJUNG AIRPORT, New Delhi
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="6" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">

							<tr>
								<td width="27%">
									&nbsp;
								</td>
								<td width="73%" class="reportlabel">
									(APPLICATION FOR GRANT OF C.P.F. ADVANCE)
								</td>
							</tr>
							<tr>
								<td colspan="2" class="reportsublabel" align="center">

									<logic:equal name="advanceBean" property="frmName" value="CPFRecommendation">
            PART – III
            </logic:equal>
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


				<logic:present name="reportBean">


					<tr>
						<td colspan="6">
							<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center">


								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										Trust:&nbsp;
										<bean:write name="reportBean" property="trust" />
									</td>
									<td width="6%"></td>
									<td width="43%" class="reportsublabel">
										Region:
										<bean:write name="reportBean" property="region" /> - <bean:write name="reportBean" property="station" />
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td colspan="3" class="reportsublabel">
										Particulars as Verified:
									</td>
								</tr>

								<tr>
									<td>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Key ID
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="advanceTransID" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
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
									<td class="reportdata">
										Name (IN BLOCK LETTERS)
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="empnamewthblk" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
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
									<td class="reportdata">
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
									<td class="reportdata">
										Previous Advance Outstanding
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="outstndamountCurr" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Purpose of Advance
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
									<td class="reportdata">
										Purpose covered clause
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="prpsecvrdclse" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Total No of Instalments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="totalInst" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										No of Interest Installments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="interestinstallments" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Amount of Interest Installments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="intinstallmentamt" />
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td colspan="3" class="reportsublabel">
										Entitlement:
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Advance Applied
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="advnceRequestCurr" />
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Amount of Per Month Istallments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="mthinstallmentamt" />
									</td>
								</tr>



								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Six/three months of Emoluments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="mnthsemolumentsCurr" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										50% of Employee share
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="empshareCurr" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Amount to be Recommended Rs.
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="amntRecommendedCurr" />
									</td>
								</tr>

						
								<logic:present name="reportbankMaster">
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
										<bean:write name="reportbankMaster" property="bankempname" />
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
										<bean:write name="reportbankMaster" property="bankempaddrs" />
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
										<bean:write name="reportbankMaster" property="bankname" />
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
										<bean:write name="reportbankMaster" property="branchaddress" />
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
										<bean:write name="reportbankMaster" property="banksavingaccno" />
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
										<bean:write name="reportbankMaster" property="bankemprtgsneftcode" />
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
				</logic:present>
			


				<logic:equal name="advanceBean" property="frmName" value="CPFApproved">

					<tr>
						<td colspan="6" class="reportContent">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="80%" class="reportContent" colspan="2">
										Verified above particulars for grant of CPF advance of Rs.
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.
									</td>
								</tr>

							</table>
						</td>
					</tr>
					<logic:present name="transList">
					<logic:iterate id="trans" name="transList" indexId="slno">

					<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								
								
								<logic:equal name="trans" property="transCode"  value="2">
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="3%">
										&nbsp;
									</td>
									<td width="46%" class="reportsublabel" align="right">
										
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
										
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
									<td class="reportsublabel" align="right">
										(Authorized Signatory)
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
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="transDispSignName" />
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
								<logic:notEqual name="trans" property="designation"  value="---">
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="designation" />
									</td>
								</logic:notEqual>
								</tr>
								</logic:equal>

							</table>
						</td>
					</tr>
					

					<logic:equal name="trans" property="transCode"  value="3">
					
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
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="80%" class="reportContent" colspan="2">
										Amount recommended Rs.
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.
									</td>
								</tr>

							</table>
						</td>
					</tr>
					
					
					
					<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="3%">
										&nbsp;
									</td>
									<td width="46%" class="reportsublabel" align="right">
																				
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
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
									<td class="reportsublabel" align="right">
										(Authorized Signatory)
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
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="transDispSignName" />
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
								<logic:notEqual name="trans" property="designation"  value="---">
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="designation" />
									</td>
								</logic:notEqual>
								</tr>

							</table>
						</td>
					</tr>
					
					</logic:equal>
					
					<logic:equal name="trans" property="transCode"  value="4">

					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="70%" class="reportsublabel" align="center">
										Approval
									</td>
								</tr>

							</table>
						</td>
					</tr>


					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="80%" class="reportContent" colspan="2">
										Amount Approved Rs
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.

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
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="3%">
										&nbsp;
									</td>
									<td width="46%" class="reportsublabel" align="right">
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
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
									<td class="reportsublabel" align="right">
										(Authorized Signatory)
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
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="transDispSignName" />
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
								<logic:notEqual name="trans" property="designation"  value="---">
									<td class="reportsublabel" align="right">
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
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									 
								</tr>

							</table>
						</td>
					</tr>
					
					
					
					<tr>

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
		</td>
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
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="80%" class="reportContent" colspan="2">
										Amount recommended Rs.
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.
									</td>
								</tr>

							</table>
						</td>
					</tr>
					
					
					
					<tr>

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
		</td>
		</tr>
					
					 
					
						 

					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="70%" class="reportsublabel" align="center">
										Approval
									</td>
								</tr>

							</table>
						</td>
					</tr>


					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="80%" class="reportContent" colspan="2">
										Amount Approved Rs
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.

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

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
		</td>
		</tr>


				 
			 
			
			</logic:notPresent>	
				</logic:equal>

				<logic:equal name="advanceBean" property="frmName" value="CPFRecommendation">
				<logic:present name="transList">
					<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 
							<logic:iterate id="trans" name="transList" indexId="slno">
								
								<logic:equal name="trans" property="transCode"  value="2">
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="3%">
										&nbsp;
									</td>
									<td width="46%" class="reportsublabel" align="right">
										
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
										
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
									<td class="reportsublabel" align="right">
										(Authorized Signatory)
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
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="transDispSignName" />
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
								<logic:notEqual name="trans" property="designation"  value="---">
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="designation" />
									</td>
								</logic:notEqual>
								</tr>
								</logic:equal>
								 
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
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<logic:equal name="trans" property="transCode"  value="3">
								<tr>
									<td width="70%" class="reportsublabel" align="center">
										PART - IV
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
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="23%">
										&nbsp;
									</td>
									<td width="49%" class="reportContent">
										Amount Recommended Rs
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.
									</td>
									<td width="2%">
										&nbsp;
									</td>
									<td width="44%">
										&nbsp;
									</td>
									<td></td>
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
						<td class="screenlabel">
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="20%">
										&nbsp;
									</td>
									<td width="31%" class="reportsublabel">
										&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
									</td>
									<td width="3%">
										&nbsp;
									</td>
									<td width="46%" class="reportsublabel" align="right">
																				
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
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
									<td class="reportsublabel" align="right">
										(Authorized Signatory)
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
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="transDispSignName" />
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
								<logic:notEqual name="trans" property="designation"  value="---">
									<td class="reportsublabel" align="right">
										<bean:write name="trans" property="designation" />
									</td>
								</logic:notEqual>
								</tr>

							</table>
						</td>
					</tr>
								 
								</logic:equal>
								</logic:iterate>
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
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:present>
				<logic:notPresent name="transList"> 
				<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr>

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
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
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
		<logic:equal name="advanceBean" property="frmName" value="CPFRecommendation">
					<tr>
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
								<tr>
									<td width="70%" class="reportsublabel" align="center">
										PART - IV
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
						<td colspan="6" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td width="23%">
										&nbsp;
									</td>
									<td width="49%" class="reportContent">
										Amount Recommended Rs
										<bean:write name="reportBean" property="amntRecommendedCurr" />
										(Rs.
										<bean:write name="reportBean" property="amntRecommendedWords" />
										Only) in favor of
										<bean:write name="reportBean" property="employeeName" />
										as refundable advance for the purpose of
										<bean:write name="reportBean" property="purposeType" />
										Expanses.
									</td>
									<td width="2%">
										&nbsp;
									</td>
									<td width="44%">
										&nbsp;
									</td>
									<td></td>
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
						<td class="screenlabel">
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 
							 
							 
								
			<tr>

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
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
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
								 
								 
							</table>
						</td>
					</tr>
				 </logic:equal>
				</logic:notPresent>
				</logic:equal>

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
