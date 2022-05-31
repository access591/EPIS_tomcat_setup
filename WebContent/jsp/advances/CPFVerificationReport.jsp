



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
					<td colspan="6">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="31%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td width="1%">
									&nbsp;
								</td>
								<td width="68%">
									<strong> AIRPORTS AUTHORITY OF INDIA</strong>
								</td>
							</tr>
							<tr>
								<td rowspan="2">
									&nbsp;
								</td>
								<td>
									<strong> EMPLOYEE PROVIDENT FUND</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="6" align="center">
						</strong>
						</p>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="28%">
									&nbsp;
								</td>
								<td width="72%" class="reportlabel">
									HO: R.G.B. SAFDARJUNG AIRPORT, New Delhi
								</td>
							</tr>
							<tr>
								<td width="27%">
									&nbsp;
								</td>
								<td width="73%" class="reportsublabel">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(APPLICATION FOR GRANT OF C.P.F. ADVANCE)
								</td>
							</tr>
							<tr>
								<td colspan="2" class="reportsublabel" align="center">
									PART – II
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
				<logic:present name="reportBean">


					<tr>
						<td colspan="6">
							<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center">


								<tr>
									<td width="21%">
										&nbsp;
									</td>
									<td width="27%" class="reportsublabel">
										Trust:&nbsp;
										<bean:write name="reportBean" property="trust" />
									</td>
									<td width="2%"></td>
									<td width="44%" class="reportsublabel">
										Region:
										<bean:write name="reportBean" property="region" />
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
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
										Date of joining in Fund
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
										Six/Three months Emoluments
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="mnthsemoluments" />
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
										<bean:write name="reportBean" property="empshare" />
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										Total No of Installments
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
										Amount of Installment
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
									<td class="reportdata">
										Amount to be Recommended
									</td>
									<td>
										:
									</td>
									<td class="reportdata">
										<bean:write name="reportBean" property="amntRecommended" />
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
				
				<logic:present name="transBean">
				<tr>

					<td colspan="6" height="22" class="reportsublabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<logic:iterate id="trans" name="transList" indexId="slno">						 
							<logic:equal name="trans" property="transCode"  value="2">
							<tr>
								<td width="21%">
									&nbsp;
								</td>
								<td width="27%" class="reportsublabel">&nbsp;
									Date : <bean:write name="trans" property="transApprovedDate" />
								</td>
								<td width="2%">
									&nbsp;
								</td>
								<td width="44%" class="reportsublabel">
									<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" />
										
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
								<td class="reportsublabel">
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
									<td class="reportsublabel">
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
			<logic:notPresent name="transBean">
			<tr>

					<td colspan="6" height="22" class="reportsublabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
