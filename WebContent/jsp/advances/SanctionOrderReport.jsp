

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
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="38%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" alt="" />
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
				<logic:present name="sanctionOrderList">
					<logic:iterate id="st" name="sanctionOrderList">
						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="9">
									<strong>Ref. No. AAI/CPF/FP/<bean:write name="st" property="regionLbl" /></strong>
								</td>
								<td align="right">
									<strong>Date &nbsp;<bean:write name="st" property="sanctiondt" /> </strong>
								</td>
							</tr>
							<tr>
								<td colspan="10" align="center">
									<strong><u>SANCTION ORDER</u></strong>
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

								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9" class="reportContentlabel">
											Sub: <u>Final payment of CPF dues.</u>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>

										<td colspan="9" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												Sanction of the president AAI EPF Trust (IAAI & NAA ECPF Trust) is hereby conveyed for the final payment of CPF dues in r/o of the following employee retiring/retired/resigned/terminated/expired on : &nbsp;<STRONG><bean:write name="st"
														property="seperationdate" />.</STRONG> The detail of CPF dues is as Follows:-
											</p>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
								</table>


								<table width="80%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td align="center" class="reportContentlabel">
											S.No
										</td>
										<td align="center" class="reportContentlabel">
											CPF A/C No
										</td>
										<td align="center" class="reportContentlabel">
											Name & Designation S/Ms./Sh .
										</td>
										<td align="center" class="reportContentlabel">
											Station
										</td>
										<td align="center" class="reportContentlabel">
											Employee Subscription
											<br>
											Rs
										</td>
										<td align="center" class="reportContentlabel">
											AAI Contribution
											<br>
											Rs
										</td>
										<td align="center" class="reportContentlabel">
											Pension Amount Deducted
											<br>
											Rs
										</td>
										<td align="center" class="reportContentlabel">
											Net Amount Payable
											<br>
											Rs
											<br>
											(5+6-7)
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
										<td align="center" class="reportContentlabel">
											8
										</td>
									</tr>
									<bean:define id="list" name="st" property="sanctionList" />

									<logic:iterate id="sorder" name="list" indexId="slno">
										<tr>
											<td class="reportContentdata">
												<%=slno.intValue()+1%>
											</td>
											<td class="reportContentdata">
												<bean:write name="sorder" property="cpfaccno" />
											</td>
											<td class="reportContentdata">
												<bean:write name="sorder" property="employeeName" />
												<bean:write name="sorder" property="designation" />
											</td>
											<td class="reportContentdata">
												<bean:write name="sorder" property="airportcd" />
												<bean:define id="station" name="sorder" property="airportcd" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplshare" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplrshare" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="pensioncontribution" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="netcontribution" />
											</td>
										</tr>
									</logic:iterate>
								</table>
								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												Ad-hoc payment if any, already made may be deducted from this authorization
											</p>
										</td>
									</tr>

									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												Outstanding dues of AAI/Govt., if any, are to be recovered/ adjusted at your end, with due approval of the competent authority subject to receipt of written consent of the ex-official/ Nominee in this regards.
											</p>
										</td>
									</tr>

									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												Interest for the financial year 2009-10 has been calculated @ 8% provisionally.
											</p>
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
										<td colspan="9" class="reportContentdata">
											<strong style="text-decoration: underline">Note: Payable on or after </strong>
											<bean:write name="st" property="paymentdate" />
											.
										</td>
									</tr>

								</table>
							</td>
						</tr>

						<tr>

							<td height="22" colspan="2" class="reportsublabel">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<!-- <td width="29%" class="reportContentlabel" align="center">(Authorized Signatory)</td>-->
										<td width="29%" class="reportContentlabel" align="center">
											(A.H WADHVA )
										</td>
									</tr>
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="center">
											Sr. Manager (Finance)
										</td>
									</tr>


								</table>
								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="8">
											&nbsp;
										</td>
									</tr>


									<tr>
										<td colspan="8" class="reportContentdata">
											The General Manager (F&A), AAI,
											<bean:write name="station" />
											,
											<bean:write name="st" property="region" />
										</td>
									</tr>
									<tr>
										<!-- <td colspan="8" class="reportContentdata">The OIC, AAI<bean:write name="station" />  </td>-->
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
					</logic:iterate>
				</logic:present>
			</table>
			
		</html:form>
	</body>
</html>
