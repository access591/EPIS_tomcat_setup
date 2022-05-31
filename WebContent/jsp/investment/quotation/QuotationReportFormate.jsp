
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.investment.QuotationRequestBean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cdate" class="com.epis.utilities.DateValidation" scope="request" />
<jsp:useBean id="sq" class="com.epis.utilities.SQLHelper" scope="request" />
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			QuotationRequestBean bean = (QuotationRequestBean) request
					.getAttribute("letterformate");

			%>


<html >
	<head>
		<base href="<%=basePath%>">

		<title>Quotation Request Letter Formate</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
	</head>
	<body>
		<form>
			<logic:iterate id="arranger" name="arrangerArray">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">

					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
			<%
			if ((bean.getSecurityName().equals("SDL") && bean.getMarketType()
					.equals("R"))
					|| (bean.getSecurityName().equals("SDL") && bean
							.getMarketType().equals("O"))) {

				%>
					<tr>
						<td align="center">

							<table width="85%" border="0" align="center" cellpadding="4" cellspacing="0">


								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>
												<td rowspan=3>
													<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
												</td>
												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
												<td>
													&nbsp;
												</td>

											</tr>

										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

										</table>
									</td>
								</TR>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="center" class="reportsublabel">
										Security Bid for:
									</td>

								</tr>
								<tr>
									<td align="center" class="reportsublabel">
										Sub: Application for Tender for NonCompetitive Bids for State Development Loans Auction by
										<bean:write property="marketTypedef" name="letterformate" />
									</td>

								</tr>
								<tr>
									<td align="left">
										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td width="20%" align="left" class="reportsublabel">
													1
												</td>
												<td width="40%" align="left" class="reportsublabel" nowrap>
													<bean:message key="quotation.dateofauction" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel" nowrap>
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td width="20%" align="left" class="reportsublabel">
													2
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.nameofthetenderer" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="nameoftheTender" name="letterformate" />
												</td>
											</tr>
											<tr>
												<td width="20%" align="left" class="reportsublabel">
													3
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.addressofthetenderer" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="addressoftheTender" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													4
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.telephoneno" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="telephoneNo" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													5
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.contactperson" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="contactPerson" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													6
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.status" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="status" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													7
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.deliveryrequestedin" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="deliveryRequestedin" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													8
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.savingbankaccountno" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="bankName" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													9
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.branch" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="branch" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													10
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.accountno" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="accountNo" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td width="20%" align="left" class="reportsublabel">
													11
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:message key="quotation.facevalueofsecurityapplying" bundle="common" />
												</td>
												<td width="40%" align="left" class="reportsublabel">
													<bean:write property="facevalueincrores" name="letterformate" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="left">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td colspan=1>
										<table cellpadding="4" cellspacing="0" width="85%" align="left" border="0">
											<tr>
												<td align="left" class="label">
													<img src="<bean:write name="letterformate" property="singPathRight"/>" />
												</td>
												<td align="right" class="label">
													<img src="<bean:write name="letterformate" property="signPathLeft"/>" />
												</td>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													<bean:write name="letterformate" property="singPathRightName" />
												</TD>
												<td ALIGN="right" class="reportsublabel">
													<bean:write name="letterformate" property="signPathLeftName" />
												</TD>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													Place :NEW DELHI
												</TD>
												<TD>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</TD>
												<TD>
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>


							</table>
						</td>
					</tr>

					<%} else if ((bean.getSecurityName().equals("GOI") && bean
					.getMarketType().equals("R"))
					|| (bean.getSecurityName().equals("GOI") && bean
							.getMarketType().equals("O"))) {

				%>
					<tr>
						<td align="center">

							<table width="85%" border="0" align="center" cellpadding="4" cellspacing="0">

								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>
												<td rowspan=3>
													<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
												</td>
												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
												<td>
													&nbsp;
												</td>

											</tr>

										</table>
									</td>
								</tr>
								<TR>
									<TD align="center">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td align="center">
										<u>Application for Non-Competitive bidding in the auction of Government Securities</u>
									</td>
								</tr>
								<tr>
									<td>

										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

										</table>
									</td>
								</TR>

								<tr>
									<td>
										&nbsp;
									</td>
								</tr>

								<tr>
									<td align="center" class="reportsublabel">
										We,the Undersigned,hereby wish to submit under the non-competitive bidding facility of
										<bean:write property="marketTypedef" name="letterformate" />
										for auction of Government Securities and furnish the necessary information as detail below
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Investors(s) Details
									</td>
								</tr>
								<tr>
									<td align="left">
										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" class="reportsublabel" width="30%">
													Name Of the Investor(s)(In Capital Letters)
												</td>
												<td align="left" class="reportsublabel" width="70%">
													<bean:write property="nameoftheTender" name="letterformate" />
												</td>
											</tr>
											<td align="left" class="reportsublabel" width="30%">
												Postal Address
											</td>
											<td align="left" class="reportsublabel" width="70%">
												<bean:write property="addressoftheTender" name="letterformate" />
											</td>
											</tr>

											</tr>
											<td align="left" class="reportsublabel" width="30%">
												<bean:message key="quotation.telephoneno" bundle="common" />
											</td>
											<td align="left" class="reportsublabel" width="70%">
												<bean:write property="telephoneNo" name="letterformate" />
											</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="30%">
													<bean:message key="quotation.faxno" bundle="common" />
												</td>
												<td align="left" class="reportsublabel" width="70%">
													<bean:write property="faxNo" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="30%">
													Status Of Applicant(s)
												</td>
												<td align="left" class="reportsublabel" width="70%">
													<bean:write property="status" name="letterformate" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										# Please tick appropriate Category. In case of "Others" please furnish relevant details.
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										Bidding Details
									</td>
								</tr>
								<tr>
									<td align="left">
										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Name of the Security
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="securityFullName" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Bid Amount (Face Value) in Rs.
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="facevalueincrores" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td align="left" width="30%" class="reportsublabel">
													<bean:message key="quotation.dateofauction" bundle="common" />
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>

											<tr>
												<td align="left" width="30%" class="reportsublabel">
													<bean:message key="sq.maturitydate" bundle="common" />
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write name="letterformate" property="maturityDate" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										I/We would like to take delivery of the alloted security as follows(Tick the option of your choice)
									</td>
								</tr>
								<tr>
									<td align="left">
										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" class="reportsublabel" width="30%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="30%">
													By Credit to SGL account
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										SGL/CSGL/Demat account Details
									</td>
								</tr>
								<tr>
									<td align="left">
										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td width="50%" colspan=2 class="reportsublabel">
													SGL/CSGL Account Details
												</td>
												<td width="50%" colspan=2 class="reportsublabel">
													De-mat Account Details
												</td>
											</tr>
											<tr>
												<td width="50%" align="center" colspan=2 class="reportsublabel">
													(i) SGL Account
												</td>
												<td width="25%" align="left" class="reportsublabel">
													Depository Name
												</td>
												<td width="25%" align="left" class="reportsublabel">
													NSDL/CSDL
												</td>
											</tr>
											<tr>
												<td width="25%" align="left" class="reportsublabel">
													SGL Account No.
												</td>
												<td width="25%" align="left" class="reportsublabel">
													<bean:write property="accountNo" name="letterformate" />
												</td>
												<td width="25%" align="left" class="reportsublabel" rowspan=2>
													Depository Participant Name
												</td>
												<td width="25%" align="left" class="reportsublabel" rowspan=2>
													&nbsp;
												</td>

											</tr>
											<tr>
												<td colspan=2 align="left" width="50%" class="reportsublabel">
													(ii) CSGL Account
												</td>
											</tr>
											<tr>
												<td align="left" width="25%" class="reportsublabel">
													Name Of Bank
												</td>
												<td align="left" width="25%" class="reportsublabel">
													<bean:write property="bankName" name="letterformate" />
												</td>
												<td align="left" width="25%" class="reportsublabel">
													DD-ID No
												</td>
												<td align="left" width="25%" class="reportsublabel">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" width="25%" class="reportsublabel">
													CSGL Account No
												</td>
												<td align="left" width="25%" class="reportsublabel">
													<bean:write name="letterformate" property="csglAccountNo" />
												</td>
												<td align="left" width="25%" class="reportsublabel">
													Client-Id
												</td>
												<td align="left" width="25%" class="reportsublabel">
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										Particulars of the Bank Account and Pan No
									</td>
								</tr>
								<tr>
									<td align="left">

										<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Name of the Bank
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="bankName" name="letterformate" />
												</td>
											</tr>

											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Nature of the Account
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="accountType" name="letterformate" />
												</td>
											</tr>
											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Account No
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="accountNo" name="letterformate" />
												</td>
											</tr>
											<tr>
												<td align="left" width="30%" class="reportsublabel">
													Pan NO.
												</td>
												<td align="left" width="70%" class="reportsublabel">
													<bean:write property="panNo" name="letterformate" />
												</td>
											</tr>
										</table>
								<tr>
									<td align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										I/We have carefully read and understood the terms and conditions mentioned overleaf.
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										I/We confirm that the information given above is true and correct.
									</td>
								</tr>

								<tr>
									<td align="left" class="reportsublabel">
										I/We undertake and confirm that we have made only a single bid with you for the above auction and that
									</td>
								</tr>

								<tr>
									<td align="left" class="reportsublabel">
										I/We have not submitted any such bid with any other PD/Bank.
									</td>
								</tr>


								<tr>
									<td align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan=1>
										<table cellpadding="4" cellspacing="0" width="85%" align="left" border="0">
											<tr>
												<td align="left" class="label">
													<img src="<bean:write name="letterformate" property="singPathRight"/>" />
												</td>
												<td align="right" class="label">
													<img src="<bean:write name="letterformate" property="signPathLeft"/>" />
												</td>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													<bean:write name="letterformate" property="singPathRightName" />
												</TD>
												<td ALIGN="right" class="reportsublabel">
													<bean:write name="letterformate" property="signPathLeftName" />
												</TD>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													Place :NEW DELHI
												</TD>
												<TD>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td ALIGN="LEFT" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</TD>
												<TD>
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>











					<%} else if ((bean.getSecurityName().equals("PSU") && bean
					.getMarketType().equals("P"))
					|| (bean.getSecurityName().equals("Private") && bean
							.getMarketType().equals("P"))) {

				%>
					<tr>
						<td align="center">
							<table width="85%" border="0" align="center" cellpadding="4" cellspacing="0">

								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>

												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center">
													<bean:message key="quotation.address" bundle="common" />
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<TR>
									<td>
										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel">
													________________
												</td>
												<td align="right" class="reportsublabel">
													Fax No:
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

										</table>
									</td>
								</TR>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="1" cellspacing="4" width="100%" align="center">
											<tr>

												<td align="left" class="reportsublabel">
													SUB:- Sealed Quotations for Investment of
													<bean:write property="trustType" name="letterformate" />
													Fund in
													<bean:write property="securityName" name="letterformate" />
													through
													<bean:write property="marketTypedef" name="letterformate" />
													market
												</td>
											</TR>
											<tr>
												<td>
													&nbsp;
											</tr>

											<TR>
												<td align="left" class="reportsublabel">
													IAAI-Employees Contributory Provident Fund proposes to invests its Surplus Funds in
													<bean:write property="securityName" name="letterformate" />
													Bonds having minimum rating 'AA' and above from two rating agencies/State Guaranteed Bonds minimum rating A(SO) and above. It is requested to send details of various issues available in the
													<bean:write property="marketTypedef" name="letterformate" />
													Market on a given format in a Sealed Cover Address to the Secretary.
													<bean:write property="quotationAddress" name="letterformate" />
													on
													<bean:write property="quotationDate" name="letterformate" />
													by
													<bean:write property="quotationTime" name="letterformate" />
													hours.
												</td>
											</TR>




										</table>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp; &nbsp; &nbsp; &nbsp;
									</td>
								</tr>

								<tr>
									<td align="left" class="reportsublabel">
										1.
										<bean:write property="securityName" name="letterformate" />
										Bonds
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="4" cellspacing="0" width="90%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													1
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Name of
													<br>
													instrument
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													10
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Put/Call
													<br>
													option
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>

											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													2
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Category(
													<bean:write property="securityName" name="letterformate" />
													)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													11
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Credit Rating
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													3
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Issue Size
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													12
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Guarantee
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													4
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Nature of Bonds
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													13
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Redemption
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													5
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Cupon Rate
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													14
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Direct
													<br>
													incentive
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													6
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Face Value
													<br>
													(per bond)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													15
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Arranger's
													<br>
													incentive
													<br>
													offered
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													7
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Minimum
													<br>
													Application
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													16
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Closing date
													<br>
													of the issue
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													8
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Tenure
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													17
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Yield(p.a)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													9
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Interest Mode
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													18
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Eligibility of
													<br>
													PF trust
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

										</table>
									</td>
								</tr>
								<td>
									&nbsp;
								</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel" />
										*The above inforation shall be enclosed separately for each issue along with the propectus of the issue.
									</td>
								</tr>



								<tr>
									<td align="center">
										<table cellpadding="0" cellspacing="4" width="100%" align="center" border="0">
											<tr>
												<td colspan=2 align="left" class="reportsublabel">
													CONDITIONS:
												</td>
											</tr>



											<tr>
												<td colspan=2>
													&nbsp;
												</td>
											</tr>
											<TR>
												<TD align="left" class="reportsublabel" valign="top">
													i)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation must clearly indicate "QUOTATION for
													<bean:write property="trustType" name="letterformate" />
													Funds" on the face of envelope.
												</td>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													ii)
												</td>
												<TD align="left" class="reportsublabel">
													The Quotation shall be dropped in a quotation box kept in the office of Manager Accounts(CPF),
													<bean:write property="trustType" name="letterformate" />
													Trust(s),
													<bean:write property="quotationAddress" name="letterformate" />
													up to
													<bean:write property="quotationTime" name="letterformate" />
													hours on
													<bean:write property="quotationDate" name="letterformate" />
													.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iii)
												</td>
												<td align="left" class="reportsublabel">
													Quotation received after
													<bean:write property="quotationTime" name="letterformate" />
													hours on
													<bean:write property="quotationDate" name="letterformate" />
													Will not be considered.
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" valign="top">
													iv)
												</td>
												<TD align="left" class="reportsublabel">
													Your quotation should be accompanied by the following certificates:-
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													&nbsp
												</td>
												<TD align="left" class="reportsublabel">
													a) We are the lead arrangers/arranger to the issue appointed by the company.(for primary issue)
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													&nbsp
												</td>
												<TD align="left" class="reportsublabel">
													b) That no middleman will be involved and no brokerage will be payable by
													<bean:write property="trustType" name="letterformate" />
													on the above investment.
												</td>
											</tr>



											<TR>
												<td align="left" class="reportsublabel" valign="top">
													v)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations received through fax will not be considered.
												</td>
											</tr>
											<tr>
												<td align="right" class="reportsublabel" valign="top">
													vi)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations may be submitted strictly as per the above conditions Quotations not received as per above conditions is liable for disqualification.
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" valign="top">
													vii)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													reserve the right to reject any quotations without assigning any reason.
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>


					<%}

			else if ((bean.getSecurityName().equals("PSU") && bean
					.getMarketType().equals("S"))
					|| (bean.getSecurityName().equals("Any") && bean
							.getMarketType().equals("S"))
					|| (bean.getSecurityName().equals("SDL") && bean
							.getMarketType().equals("S"))
					|| (bean.getSecurityName().equals("GOI") && bean
							.getMarketType().equals("S"))) {
							

				%>
					<tr>
						<td align="center">
							<table width="85%" border="0" class="GridBorder" align="center" cellpadding="4" cellspacing="0">

								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>

												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center">
													<bean:message key="quotation.address" bundle="common" />
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<TR>
									<td>
										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													<bean:write name="arranger" property="arrangerName" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel">
													<bean:write name="arranger" property="regOffAddr" />
												</td>
												<td align="right" class="reportsublabel">
													Fax No:
													<bean:write name="letterformate" property="faxNo" />
												</td>
											</tr>


										</table>
									</td>
								</TR>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="1" cellspacing="4" width="100%" align="center">
											<TR>

												<td align="left" class="reportsublabel">
													SUB:- Sealed Quotations for Investment of Funds in
													<bean:write property="securityName" name="letterformate" />
													Bonds Through
													<bean:write property="marketTypedef" name="letterformate" />
													market
												</td>
											</TR>
											<tr>
												<td>
													&nbsp;
											</tr>

											<TR>
												<td align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trusts proposess to invest its funds approx Rs.
													<bean:write property="conQuantumCrores" name="letterformate" />
													Crores
													<bean:define id="minimumwords" property="conQuantumCrores" name="letterformate" type="java.lang.String" />
													(Rupees 
													<%=sq.ConvertInWords(Double.parseDouble(minimumwords))%> Crores
													Only) in Central
													<bean:write property="securityName" name="letterformate" />
													Bonds having minimum latest rating "AA+" and above at least from two credit agencies.Please quote your Competitive rates of various issues available in
													<bean:write property="marketTypedef" name="letterformate" />
													market on a given below format in a Sealed Cover Addressed to The Secretary.
													<bean:write property="trustType" name="letterformate" />
													Trust,
													<bean:message key="quotation.jasperaddress" bundle="common" />
													on
													<bean:write property="validDate" name="letterformate" />
													by
													<bean:write property="validTime" name="letterformate" />
													hours.
												</td>
											</TR>


										</table>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp; &nbsp; &nbsp; &nbsp;
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										<bean:write property="remarks" name="letterformate" />
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="4" cellspacing="0" width="90%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													1
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Name of instrument
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													2
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Nature Bonds
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													3
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Cupon Rate
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>

											</tr>



											<tr>
												<td align="left" class="reportsublabel" width="5%">
													4
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Face Value
													<br>
													(per bond)
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													5
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Price Offered
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>

											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													6
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Interest Payment Date
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													7
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Redemption/Maturity Date
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													8
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Credit rating (latest) of two agencies
												</td>
												<td align="left" class="reportsublabel" width="50%">
													i)
													<br>
													ii)
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													9
												</td>
												<td align="left" class="reportsublabel" width="45%">
													YTM on put Call Option(Annualized)
													<br>
													YTM on Maturity(Annualized)
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													10
												</td>
												<td align="left" class="reportsublabel" width="45%">
													Put/Call Option date (if any)
												</td>
												<td align="left" class="reportsublabel" width="50%">
													&nbsp;
												</td>
											</tr>


										</table>
									</td>
								</tr>
								<td>
									&nbsp;
								</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel" />
										Note:(a)The Deep discount bond / Perpetual bonds may not be quoted
									</td>
								</tr>



								<tr>
									<td align="center">
										<table cellpadding="0" cellspacing="4" width="100%" align="center" border="0">
											<tr>
												<td colspan=2 align="left" class="reportsublabel">
													2.CONDITIONS:
												</td>
											</TR>



											<tr>
												<td colspan=2>
													&nbsp;
												</td>
											</tr>
											<TR>
												<TD align="left" class="reportsublabel" valign="top">
													i)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation must clearly indicate "QUOTATION for
													<bean:write property="trustType" name="letterformate" />
													Trust " on the face of envelope.
												</td>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													ii)
												</td>
												<TD align="left" class="reportsublabel">
													The quotaion shall be dropped in a quotation box kept in the CPF Section in,
													<bean:message key="quotation.jasperaddress" bundle="common" />
													up to
													<bean:write property="validTime" name="letterformate" />
													hours on
													<bean:write property="validDate" name="letterformate" />
													.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iii)
												</td>
												<TD align="left" class="reportsublabel">
													Quotation received after
													<bean:write property="validTime" name="letterformate" />
													hours Will not be considered.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iv)
												</td>
												<TD align="left" class="reportsublabel">
													Your quotation Should accompained by the following certificates:-
													<br>
													The transaction will be direct sale between the seller and Trust for Secondary Market and no brokerage will be payable by Trust on above Investment.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													v)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trust requires Transaction to be executed on Delivery-vesus Payment basis.
												</td>
												<TD align="left" class="reportsublabel"></td>
											</tr>


											<tr>
												<td align="left" class="reportsublabel" valign="top">
													vi)
												</td>
												<TD align="left" class="reportsublabel">
													 Quotation received through Fax will not be Considered.
												</td>
											</tr>
											<logic:notEqual value="PSU" name="letterformate" property="securityName">

											<tr>
												<td align="right" class="reportsublabel" valign="top">
													vii)
												</td>
												<TD align="left" class="reportsublabel">
													Securities offered in SGL form will  be considered. 
												</td>
											</tr>
											</logic:notEqual>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													viii)
												</td>
												<TD align="left" class="reportsublabel">
													The deal will be settld at Delhi on <bean:write property="modeOfPaymentRemarks" name="letterformate"/> basis through <bean:write property="paymentThroughRemarks" name="letterformate"/>.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													ix)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations may be submitted strictly as per above condition. Quotation not received as per above conditions is liable for disqualification.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													x)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trust reserve the right to reject any quotations without assigning any reason.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													xi)
												</td>
												<TD align="left" class="reportsublabel">
													The information required of at least two credit rating agency must be clearly mentioned in the quotations failing which the quotation would be liable to be rejected.
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" valign="top">
													xii)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="formateRemarks" name="letterformate" />
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
									<td align="right" class="label">
									
										<img src="<bean:write name="letterformate" property="signPath"/>" />
									</td>
								</tr>
								<tr>
									<td ALIGN="right" class="reportsublabel">
										<bean:write name="letterformate" property="signPathName" />
									</td>
									</tr>
									
								<tr>
									<td ALIGN="right" class="reportsublabel">
										<bean:write name="letterformate" property="designation" />
									</td>
									</tr>
							</table>
						</td>
					</tr>
					<%} else if ((bean.getSecurityName().equals("PSU") && bean
					.getMarketType().equals("PS"))
					|| (bean.getSecurityName().equals("Private") && bean
							.getMarketType().equals("PS"))) {

				%>
					<tr>
						<td align="center">
							<table width="85%" border="0" align="center" cellpadding="4" cellspacing="0">

								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>

												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center">
													<bean:message key="quotation.address" bundle="common" />
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<TR>
									<td>
										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel">
													________________
												</td>
												<td align="right" class="reportsublabel">
													Fax No:
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

										</table>
									</td>
								</TR>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="1" cellspacing="4" width="100%" align="center">
											<TR>

												<td align="left" class="reportsublabel">
													SUB:- Sealed Quotations for Investment of Funds in
													<bean:write property="securityName" name="letterformate" />
													Bonds Through
													<bean:write property="marketTypedef" name="letterformate" />
													market
												</td>
											</TR>
											<tr>
												<td>
													&nbsp;
											</tr>

											<TR>
												<td align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trusts of AAI proposess to invest its surplus funds approx Rs.
													<bean:write property="concrores" name="letterformate" />
													Crores
													<bean:write property="conInWords" name="letterformate" />
													in
													<bean:write property="securityName" name="letterformate" />
													having minimum rating "AA+" and above at least from credit rating agencies. Please quote your Competitive rates available in the
													<bean:write property="marketTypedef" name="letterformate" />
													market in the format given below in a Sealed Cover Addressed to The Secretary.
													<bean:write property="quotationAddress" name="letterformate" />
													on
													<bean:write property="quotationDate" name="letterformate" />
													by
													<bean:write property="quotationTime" name="letterformate" />
													hours.
												</td>
											</TR>


										</table>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp; &nbsp; &nbsp; &nbsp;
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										1. Central
										<bean:write property="securityName" name="letterformate" />
										Bonds with 10 Years MATURITY
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="4" cellspacing="0" width="90%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													1
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Name of
													<br>
													instrument
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													9
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Redemption / Maturity date
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>

											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													2
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Issue Size/ Quantum offered
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													10
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Credit rating (latest )
													<br>
													of two rating agencies
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" width="5%">
													3
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Nature of Bonds
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													11
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Guarantee ( if any)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													4
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Coupon Rate
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													12
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Incentive offered
													<bean:write property="securityName" name="letterformate" />
													arranger
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													5
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Face Value (per bond)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													13
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Closing Date of the Issue
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													6
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Price offered
													<br>
													( if secondary issue)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													14
												</td>
												<td align="left" class="reportsublabel" width="25%">
													YTM on put call option (Annualized )
													<br>
													YTM on Maturity (Annunalised)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													7
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Tenure
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													15
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Eligibility of PF trust
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													8
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Interest payment date
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													16
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Minimum Application
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

											<tr>
												<td align="left" class="reportsublabel" width="5%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="25%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
												<td align="left" class="reportsublabel" width="5%">
													17
												</td>
												<td align="left" class="reportsublabel" width="25%">
													Put/Call option date ( if any)
												</td>
												<td align="left" class="reportsublabel" width="20%">
													&nbsp;
												</td>
											</tr>

										</table>
									</td>
								</tr>
								<td>
									&nbsp;
								</td>
								</tr>


								<tr>
									<td align="center">
										<table cellpadding="0" cellspacing="4" width="100%" align="center" border="0">
											<tr>
												<td colspan=2 align="left" class="reportsublabel">
													CONDITIONS:
												</td>
											</tr>



											<tr>
												<td colspan=2>
													&nbsp;
												</td>
											</tr>
											<TR>
												<TD align="left" class="reportsublabel" valign="top">
													i)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation must clearly indicate "QUOTATION for
													<bean:write property="trustType" name="letterformate" />
													Trust of AAI" on the face of envelope.
												</td>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													ii)
												</td>
												<TD align="left" class="reportsublabel">
													The quotaion shall be dropped in a quotation box kept in the
													<bean:write property="trustType" name="letterformate" />
													Section in,
													<bean:write property="quotationAddress" name="letterformate" />
													up to
													<bean:write property="quotationTime" name="letterformate" />
													hours on
													<bean:write property="quotationDate" name="letterformate" />
													.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iii)
												</td>
												<TD align="left" class="reportsublabel">
													Quotation received after
													<bean:write property="quotationTime" name="letterformate" />
													hours Will not be considered.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iv)
												</td>
												<TD align="left" class="reportsublabel">
													The issue quoted above should be valid for not less than 2 days from the date of opening the quotation.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													v)
												</td>
												<TD align="left" class="reportsublabel">
													Your quotation should be accompanied by the following certificates:-
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													&nbsp
												</td>
												<TD align="left" class="reportsublabel">
													a) We are the lead arrangers/arranger to the issue appointed by the company.(for primary issue)
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													&nbsp
												</td>
												<TD align="left" class="reportsublabel">
													b) The transaction will be a direct sale between the seller abd brokerage will be payable by
													<bean:write property="trustType" name="letterformate" />
													Trust(s)
												</td>
											</tr>


											<tr>
												<td align="left" class="reportsublabel" valign="top">
													vi)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trust(s) requires transactions to be executed on Delivery-versus-Payment basis.
												</td>
											</tr>
											<TR>
												<td align="left" class="reportsublabel" valign="top">
													vii)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations received through fax will not be considered.
												</td>
											</tr>
											<tr>
												<td align="right" class="reportsublabel" valign="top">
													viii)
												</td>
												<TD align="left" class="reportsublabel">
													Securities offered in Demat form will not be considered.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													ix)
												</td>
												<TD align="left" class="reportsublabel">
													The deal will be settld at Delhi.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													x)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations may be submitted strictly as per above condition. Quotation not received as per above conditions is liable for disqualification.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													xi)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trusts of AAI reserve the right to reject any quotations without assigning any reason.
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>


					<%} else {

				%>
					<tr>
						<td align="center">
							<table width="85%" border="0" align="center" cellpadding="4" cellspacing="0">

								<tr>
									<td align="center">
										<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
											<tr>

												<td class="reportsublabel" align=center nowrap="nowrap">
													<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA</font>
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center" nowrap="nowrap">
													<bean:write property="trustType" name="letterformate" />
													TRUST,
												</td>
											</tr>
											<tr>
												<td class="reportsublabel" align="center">
													<bean:message key="quotation.address" bundle="common" />
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<TR>
									<td>
										<table width="100%" cellpadding="0" cellspacing="0" align="center">


											<tr>
												<td align="left" class="reportsublabel">
													No :
													<bean:write property="letterNo" name="letterformate" />
												</td>
												<td align="right" class="reportsublabel">
													Date :
													<bean:write name="letterformate" property="quotationDate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													TO
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel">
													________________
												</td>
												<td align="right" class="reportsublabel">
													Fax No:
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" colspan="2">
													________________
												</td>
											</tr>

										</table>
									</td>
								</TR>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="1" cellspacing="4" width="100%" align="center">
											<TR>

												<td align="left" class="reportsublabel">
													SUB:- Sealed Quotations for Investment of Funds in
													<bean:write property="securityName" name="letterformate" />
													Securities from
													<bean:write property="marketTypedef" name="letterformate" />
													market
												</td>
											</TR>
											<tr>
												<td>
													&nbsp;
											</tr>

											<TR>
												<td align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trusts of AAI proposess to invest its surplus funds approx Rs.
													<bean:write property="concrores" name="letterformate" />
													Crores
													<bean:write property="conInWords" name="letterformate" />
													in
													<bean:write property="securityName" name="letterformate" />
													Securities. Please quote your Competitive rates available in the
													<bean:write property="marketTypedef" name="letterformate" />
													market in the format given below in a Sealed Cover Addressed to The Secretary.
													<bean:write property="quotationAddress" name="letterformate" />
													on
													<bean:write property="quotationDate" name="letterformate" />
													by
													<bean:write property="quotationTime" name="letterformate" />
													hours.
												</td>
											</TR>


										</table>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp; &nbsp; &nbsp; &nbsp;
									</td>
								</tr>
								<tr>
									<td align="left" class="reportsublabel">
										1. Rates for
										<bean:write property="securityName" name="letterformate" />
										Securities
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="4" cellspacing="0" width="90%" align="center" border="1" bordercolor="black">
											<tr>
												<td align="center" class="reportsublabel" nowrap>
													S.No
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Name of
													<br>
													Security
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Cupon Rate
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Interest
													<br>
													Payment Date
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Maturity Date
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Price
												</td>
												<td align="center" class="reportsublabel" nowrap>
													YTM(%)(P.A.)
												</td>
												<td align="center" class="reportsublabel" nowrap>
													Quantum Offered
													<br>
													(Rs. crores)
												</td>
											</tr>
											<tr>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
												<td align="center" class="reportsublabel" nowrap>
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr>
									<td align="center">
										<table cellpadding="0" cellspacing="4" width="100%" align="center" border="0">
											<tr>
												<td colspan=2 align="left" class="reportsublabel">
													CONDITIONS:
												</td>
											</TR>
											<tr>
												<td colspan=2>
													&nbsp;
												</td>
											</tr>
											<TR>
												<TD align="left" class="reportsublabel" valign="top">
													i)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation must clearly indicate "QUOTATION for
													<bean:write property="trustType" name="letterformate" />
													Trust of AAI" on the face of envelope.
												</td>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													ii)
												</td>
												<TD align="left" class="reportsublabel">
													The quotations shall be dropped in a quotation box kept in the
													<bean:write property="trustType" name="letterformate" />
													section in
													<bean:write property="quotationAddress" name="letterformate" />
													on
													<bean:write property="quotationDate" name="letterformate" />
													by
													<bean:write property="quotationTime" name="letterformate" />
													hours. Quotations received after
													<bean:write property="quotationAddress" name="letterformate" />
													will not be considered.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iii)
												</td>
												<TD align="left" class="reportsublabel">
													The rates quoted above shall be valid upto
													<bean:write property="validTime" name="letterformate" />
													hrs dt.
													<bean:write property="validDate" name="letterformate" />
													. Back out from offer may attract penalty up to depanelment.
												</TD>
											</tr>
											<tr>
												<TD align="left" class="reportsublabel" valign="top">
													iv)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation will be opened in room No.246 at
													<bean:write property="openTime" name="letterformate" />
													hrs on
													<bean:write property="openDate" name="letterformate" />
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													v)
												</td>
												<TD align="left" class="reportsublabel">
													The quotation should be accompanied by a certificate stating that the transaction will be a direct sale between the sellere and the Trust and that no middle man is involved. Also no brokerage will be payable by the Trust on the above
													investment.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													vi)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations received through fax will not be considered.
												</td>
											</tr>
											<TR>
												<td align="left" class="reportsublabel" valign="top">
													vii)
												</td>
												<TD align="left" class="reportsublabel">
													Securities offered in SGL from will only be considered.
												</td>
											</tr>
											<tr>
												<td align="right" class="reportsublabel" valign="top">
													viii)
												</td>
												<TD align="left" class="reportsublabel">
													The deal will be settled at Delhi.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													ix)
												</td>
												<TD align="left" class="reportsublabel">
													Quotations may be submitted strictly as per above condition. Quotation not received as per above conditions is liable for disqualification.
												</td>
											</tr>
											<tr>
												<td align="left" class="reportsublabel" valign="top">
													x)
												</td>
												<TD align="left" class="reportsublabel">
													<bean:write property="trustType" name="letterformate" />
													Trusts of AAI reserves the right to reject any quotations without assigning any reason.
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<%}

		%>

				</table>
				<p style="page-break-before: always">
				<Table Width='280' height="40" Cellspacing=0 Cellpadding=0 Border=0 align=center>
				<tr height=10><td></td></tr>
				</Table>
			</logic:iterate>
	</body>
</html>
