<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page import="com.epis.info.login.LoginInfo" %>
<%@ page import="com.epis.bean.cashbookDummy.BankBook" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherDetails" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.RequestBean" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="com.epis.dao.CommonDAO" %>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.utilities.DBUtility" %>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
VoucherInfo info = (VoucherInfo) request.getAttribute("info");
LoginInfo user = (LoginInfo) session.getAttribute("user");
String appvoucherDt=(String)request.getAttribute("voucherappdate");
BankBook book = (BankBook) request.getAttribute("book");
DecimalFormat df = new DecimalFormat("####.##");

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI - CashBook - Forms - Voucher Report</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" type=""></script>
		<script type="text/javascript">
			function empDetails(pfid,name,desig){
				if(document.forms[0].empHid.value=='Y'){
					document.forms[0].checkedby.value=name;
				}else{
			   		document.forms[0].approved.value=name;
				}
			}
	
		     function popupWindow(mylink, windowname){
				if (! window.focus){
					return true;
				}
				var href;
				if (typeof(mylink) == 'string'){
				   href=mylink;
				} else {
					href=mylink.href;
				}
				progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
				return true;
			}
		 	
		 	function checkApproval(){
		 		if(document.forms[0].voucherDate.value==''){
		 			alert("Please Enter Date (Mandatory)");
		 			document.forms[0].voucherDate.focus();
		 			return false;
		 		}
		 		if(!convert_date(document.forms[0].voucherDate)){
					return false;
				}
				convert_date(document.forms[0].voucherDate);
				if(compareDates(document.forms[0].prepDate.value,document.forms[0].voucherDate.value) == "larger"){
					alert("Approval Date should be Greatee than or equal to  Preparation Date i.e "+document.forms[0].prepDate.value);
		 			document.forms[0].voucherDate.focus();
		 			return false;
				}
			

				if('<%=info.getVoucherType().toUpperCase()%>' == "PAYMENT" && (document.forms[0].openBal.value < document.forms[0].netpayment.value)){
					if(!confirm("The Balance amount is : "+document.forms[0].openBal.value+"\n The Net Payment is : "+Math.abs(document.forms[0].netpayment.value)+"\nWould you like to Continue?"))
						return false;
				}
				document.getElementById('appSubmit').disabled=true;
		 	}
		</script>
	</head>

	<body class="BodyBackground1" onload='document.forms[0].voucherDate.focus();'>
		<form action="<%=basePathBuf%>Voucher?method=getVoucherApproval" method="post" onsubmit="javascript :return checkApproval()">		<input type=hidden name=openBal value='<%=df.format(book.getOpeningBalAmt())%>' />
			<%=ScreenUtilities.screenHeader("voucher.title")%>
			<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									<table >
										<tr>
											<td class="label" align="left" nowrap>
												VOUCHER TYPE
											</td>
											<td class="label" align="left" >
												:
												<%=info.getVoucherType()%>
											</td>
										</tr>
										<tr>
											<td class="label" align="left" >
												<%=info.getPartyType().equals("E") ? "EMPLOYEE NO"
					: info.getPartyType().equals("P") ? "PARTY NAME":info.getPartyType().equals("I") ?"InvestParty" :info.getPartyType().equals("C")?"Cash"
							: "BANK NAME"%>
											</td>
											<td class="label" align="left" >
												:
												<%=info.getEmpPartyCode()%>
											</td>
										</tr>
										<tr>
											<td class="label" align="left" >
												<%=info.getPartyType().equals("E") ? "EMPLOYEE NAME"
					: info.getPartyType().equals("B") ? "ACCOUNT NO" : ""%>
											</td>
											<td class="label" align="left" >
												<%=info.getPartyType().equals("P") ? "" : info.getPartyType().equals("I")?"" :info.getPartyType().equals("C")?"":": "
					+ info.getPartyDetails()%>
											</td>
										</tr>
<% 
	if("E".equals(info.getPartyType())){
%>						
									<tr>
										<td class="label" align="left" nowrap>
											EMPLOYEE PFID
										</td>
										<td class="label" align="left" nowrap>
											:&nbsp;<%=(new CommonDAO()).getPFID(info.getPartyDetails().toUpperCase(),(new SQLHelper()).getDescription("employee_personal_info","to_char(DATEOFBIRTH,'DD/MON/YYYY')","pensionno",info.getPfid(),DBUtility.getConnection()),info.getPfid())%>
										</td>
									</tr>
<%}%>										
									</table>
								</td>
								<td>
									<table align='right' >
										<tr>
											<td class="label" align="left" nowrap>
												BANK NAME
											</td>
											<td class="label" align="left" nowrap>
												:
												<%=info.getBankName()%>
											</td>
										</tr>
										<tr>
											<td class="label" align="left" nowrap>
												ACCOUNT NO
											</td>
											<td class="label" align="left" nowrap>
												:
												<%=info.getAccountNo()%>
											</td>
										</tr>
										<tr>
											<td class="label" align="left" nowrap>
												FINANCIAL YEAR
											</td>
											<td class="label" align="left" nowrap>
												:
												<%=info.getFinYear()%>
											</td>
										</tr>
										<tr>
											<td class="label" align="left" nowrap>
												Preperation Date
											</td>
											<td class="label" align="left" nowrap>
												:
												<%=info.getPreparedDt()%>
											</td>
										</tr>

										<tr>
											<td class="label" align="left" nowrap>
												VOUCHER DATE
											</td>
											<td class="label" align="left" nowrap>
												:
												<input name=prepDate type="hidden" value='<%=info.getPreparedDt()%>'/>
												<input name=vtype type="hidden" value='<%=info.getVtype()%>'/>
												<input name='voucherDate' Class="TextField" maxlength="11" value='<%=info.getVoucherType().equals("Payment")?appvoucherDt:info.getPreparedDt()%>' id="voucherDate" size="18" />
												&nbsp; <a href="javascript:showCalendar('voucherDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
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
									<table align="center" cellpadding="0" cellspacing="0" border="1" width="100%" class='border'>
										<tr>
											<td class="tblabel" align="center">
												Account Code
											</td>
											<td class="tblabel" align="center">
												Particular
											</td>
											<td class="tblabel" align="center">
												Month/Year:
											</td>
											<td class="tblabel" align="center">
												Cheque No.
											</td>
											<td class="tblabel" align="center">
												Details (if any)
											</td>
											<td class="tblabel" align="center">
												Debit(RS.)
											</td>
											<td class="tblabel" align="center">
												Credit(RS.)
											</td>
										</tr>
										<%List voucherDts = info.getVoucherDetails();
			int listSize = voucherDts.size();
			double amount = 0.0;
			double credit = 0.0;
			double debit  = 0.0;
			for (int i = 0; i < listSize; i++) {
				VoucherDetails details = (VoucherDetails) voucherDts.get(i);
				credit += details.getCredit();
				debit += details.getDebit();
%>
										<tr>
											<td class="Data" align="center">
												<%=details.getAccountHead()%>
											</td>
											<td class="Data" align="left">
												<%=details.getParticular()%>
											</td>
											<td class="Data" align="center">
												<%=details.getMonthYear()%>
											</td>
											<td class="Data" align="left">
												<%=details.getChequeNo()%>
											</td>
											<td class="Data" align="left">
												<%=details.getDetails()%>
											</td>
											<td class="Data" align="right">
												<%=df.format(details.getDebit())%>
											</td>
											<td class="Data" align="right">
												<%=df.format(details.getCredit())%>
											</td>
										</tr>

										<%amount += details.getCredit()-details.getDebit();}%>

										<tr>

											<td class="label" align="right" colspan="5">
												TOTAL&nbsp;&nbsp;
											</td>
											<td class="Data" align="right">
												<%=df.format(debit)%>
											</td>
											<td class="Data" align="right">
												<%=df.format(credit)%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<br />
									<br />
								</td>
							</tr>
							<tr>
								<td class="label" align="right" colspan="2">
									NET PAYMENT :
									<%=df.format(Math.abs(amount))+" "+("PAYMENT".equals(info.getVoucherType().toUpperCase())?"Dr.":"Cr.")%>
									<input type=hidden name=netpayment value='<%=df.format(amount)%>'>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>

							<tr>
								<td width="45%" class="label" colspan="2" align="left">
									Rupees (in words)
									<%=CommonUtil.ConvertInWords(Double.parseDouble(df.format(Math.abs(debit-credit))))%> Only.
								</td>
							</tr>
							<tr>
								<td align="center" class="Data" colspan="2">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>

							<tr>

								<td width="45%" class="label" colspan="2" align="left">
									DETAILS :
									<%=info.getDetails()%>
								</td>
							</tr>
							<tr>
								<td>
									<br />
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<br />
								</td>
							</tr>
							<tr>
								<td width="100%" colspan="2" align="center">
									<table width="100%" align="center" border="0">
										<tr>
											<td class="label" nowrap="nowrap" align="right">
												Prepared By :
											</td>											
											<td class="Data" align="left">
												<%=info.getPreparedBy()==null?"--":info.getPreparedBy()%>
											</td>
										
											<td class="label" nowrap="nowrap" align="right">
												Checked By :
											</td>											
											<td class="Data" align="left" nowrap="nowrap">
												<input type="text" name="checkedby" />
												<input type="hidden" name="empHid" />
												<img src="<%=basePath%>images/search1.gif" onclick="document.forms[0].empHid.value='Y';popupWindow('<%=basePath%>jsp/cashbook/voucher/EmployeeInfo.jsp','AAI');" alt="Click The Icon to Select EmployeeName" src="" alt="" />
											<input type="hidden" name="approvalstatus" value="Y" />
											</td>
											<td class="label" nowrap="nowrap" align="right">
												Approved By :
											</td>
											<td class="Data" align="left" nowrap="nowrap">
												<%=StringUtility.checknull((new RequestBean(request)).getLoginUserDispName())%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<br />
								</td>
							</tr>
							<tr>
								<td width="100%" colspan="2" align="center">
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="15%" class="label" nowrap="nowrap">
												Voucher Prep No :
											</td>
											<td class="data">
												<%=info.getKeyNo()%>
												<input type="hidden" name="keyNo" value="<%=info.getKeyNo()%>" />
											</td>
											<td width="30%">
												&nbsp;
											</td>
											<td width="15%" class="label" nowrap="nowrap">
												Received By:
											</td>
											<td class="data" align="left">
												________________________
											</td>
										</tr>
										<tr height="20">
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit" id="appSubmit"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>
