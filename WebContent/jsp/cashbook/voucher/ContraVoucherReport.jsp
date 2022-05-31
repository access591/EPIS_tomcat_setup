
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="com.epis.utilities.CurrencyFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherDetails" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.bean.admin.UserBean" %>
<%@ page import="com.epis.dao.admin.UserDAO" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
VoucherInfo info = (VoucherInfo)request.getAttribute("info");
CurrencyFormat cf = new CurrencyFormat();
List voucherDts = info.getVoucherDetails();
VoucherDetails details = (VoucherDetails)voucherDts.get(0);
CommonUtil util = new CommonUtil();
SQLHelper sql = new SQLHelper();
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
	</head>

	<body background="body">
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan=3>
								<img src="<%=basePath%>images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<td class="reportlabel" align="center" valign="middle">
								AIRPORTS AUTHORITY OF INDIA
							</td>
						</tr>
						<tr>						
							<td class="reportlabel" align="center" valign="middle">
								<%=info.getTrustType()+" TRUST"%> 
							</td>
						</tr>						
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
										<td class="label">
											VOUCHER TYPE
										</td>
										<td class="label">
											:	CONTRA
										</td>
									</tr>									
								</table>
							</td>
							<td align="right"> 
								<table>
									<tr>
										<td class="label">
											FINANCIAL YEAR
										</td>
										<td class="label" align="left">
											: <%=info.getFinYear()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											VOUCHER NO
										</td>
										<td class="label" align="left">
											: <%=info.getVoucherNo()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											PREPARED DATE
										</td>
										<td class="label" align="left">
											: <%=info.getPreparedDt()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											VOUCHER DATE
										</td>
										<td class="label" align="left">
											: <%=info.getVoucherDt()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											TRUST TYPE
										</td>
										<td class="label" align="left">
											: <%=info.getTrustType()+" TRUST"%> 
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
						<TR>
							<td align="right" colspan="2" valign="top" > 
								<table width=100% cellpadding="0" cellspacing="0" class=border>
									<tr>
										<td align="left" class="label"> 
											Account Code
										</td>	
										<td align="left" class="label"> 
											Particulars
										</td>	
										<td align="left" class="label"> 
											Month/Year
										</td>	
										<td align="left" class="label"> 
											Details (ifany)
										</td>
										<td align="left" class="label"> 
											Cheque No.
										</td>									
										<td class="label" >
											Debit
										</td>
										<td class="label" >
											Credit
										</td>
									</tr>									
									 <tr>
										 <td align="left" class="Data" > 
											<B><%=details.getAccountHead()%>
										</td>
										<td align="left" class="Data" > 
											<B><%=info.getBankName()%>
										</td>									
										<td align="left" class="Data" rowspan=2 > 
											<B><%=details.getMonthYear()%>
										</td>
										<td align="left" class="Data" rowspan=2 > 
											<B><%=details.getDetails()%>
										</td>
										<td align="left" class="Data" rowspan=2 > 
											<B><%=details.getChequeNo()%>
										</td>
										<td class="label">
											0
										</td>
										<td class="label">
											<%=cf.getDecimalCurrency(details.getCredit())%>
										</td>
									</tr>
									 <tr>
										 <td align="left" class="Data" > 
											<B><%=sql.getDescription("cb_bank_info","accountcode","accountno",info.getPartyDetails(),com.epis.utilities.DBUtility.getConnection())%>
										</td>
										<td align="left" class="Data" > 
											<B><%=info.getEmpPartyCode()%>
										</td>									
										<td class="label">
											<%=cf.getDecimalCurrency(details.getCredit())%>
										</td>
										<td class="label">
											0
										</td>
									</tr>									
									<tr>	
										<td class="label" colspan=5>
											TOTAL
										</td>							
										<td class="label" >
											<%=cf.getDecimalCurrency(details.getCredit())%>
										</td>
										<td class="label" >
											<%=cf.getDecimalCurrency(details.getCredit())%>
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
							<td colspan="3">
								<table>
									<tr>
										<td class="label">NET AMOUNT
										</td>
										<td  class="Data">
											: <%=cf.getDecimalCurrency(details.getCredit())%>
										</td>
									</tr>
									<tr>
										<td class="label">Rupees (in words) 
										</td>
										<td  class="Data"> : <%=util.ConvertInWords(details.getCredit())%>  Only.
										</td>
									</tr>
								</table>	
							</td>
						</tr>	
						<tr>
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td align="left" class="Data" colspan="3"> 
								<table>
									<tr>
										<td class="label" valign="top">
											Narration :
										</td>
										<td class="Data">
											<%=info.getDetails()%>
										</td>
									</tr>
								</table>											
							</td>									
						</tr>
						<tr>
							<td colspan="3">
								<table width="100%">
									<tr>
										<td class="label" align="right">Prepared By : 
										</td>
										<td  class="Data" align="left">
											<%=info.getPreparedBy()%>
										</td>									
										<td class="label" align="right" >Checked By :
										</td>
										<td  class="Data" align="left"><%=info.getCheckedBy()%>
										</td>
										<td class="label" align="right">Approved By : 
										</td>
<%	
if(!"".equals(StringUtility.checknull(info.getApprovedBy()).trim())){
UserBean user = UserDAO.getInstance().getUser(info.getApprovedBy());
String path = "uploads/SIGNATORY/"+user.getEsignatoryName();
%>		
										<td class="Data" class="label" nowrap align="left">
											<img  alt="" src="<%=path%>"/>
											<BR>
											(<%=user.getDisplayName()%>)
										</td>
<%}%>																			
									</tr>
								</table>	
							</td>
						</tr>	
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>