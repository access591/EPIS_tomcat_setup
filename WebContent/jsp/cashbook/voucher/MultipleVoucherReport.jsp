
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherDetails" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.RequestBean" %>
<%@ page import="com.epis.dao.admin.UserDAO" %>
<%@ page import="com.epis.bean.admin.UserBean" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="com.epis.dao.CommonDAO" %>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.utilities.DBUtility" %>
<%@ page import="java.util.*"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;

CommonUtil util = new CommonUtil();

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
	</head>

	<body background="body">
	
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
				<%
		ArrayList multipleVouchers = (ArrayList) request.getAttribute("multiplevouchers");
		if(multipleVouchers.size()==0)
		{
		%>
		<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan=2>
								<img src="<%=basePath%>images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<td class="reportlabel" align="center" valign="middle">
								AIRPORTS AUTHORITY OF INDIA
							</td>
						</tr>
		</table>
		<br>
		<tr>
          <td class="tableText"  align=center nowrap colspan=5><font color=red size=4>No Records Found</font></td>
        </tr>
		<%
		}
		else
		{
		for(int vouchers=0; vouchers<multipleVouchers.size(); vouchers++)
		{
		VoucherInfo info = (VoucherInfo)multipleVouchers.get(vouchers);
		
	%>
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan=2>
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
					<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
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
											:
											<%=info.getVoucherType().toUpperCase()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											<%=info.getPartyType().equals("E")?"EMPLOYEE CPF NO":info.getPartyType().equals("P")?"PARTY NAME":"BANK NAME"%>
										</td>
										<td class="label">
											:
											<%=info.getEmpPartyCode()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											<%=info.getPartyType().equals("E")?"EMPLOYEE NAME":info.getPartyType().toUpperCase().equals("B")?"ACCOUNT NO":""%>
										</td>
										<td class="label">
											<%=info.getPartyType().equals("P")?"":": "+info.getPartyDetails().toUpperCase()%>
										</td>
									</tr>
<% 
	if("E".equals(info.getPartyType())){
%>						
									<tr>
										<td class="label">
											EMPLOYEE PFID
										</td>
										<td class="label">
											:&nbsp;<%=(new CommonDAO()).getPFID(info.getPartyDetails().toUpperCase(),(new SQLHelper()).getDescription("employee_personal_info","to_char(DATEOFBIRTH,'DD/MON/YYYY')","pensionno",info.getPfid(),DBUtility.getConnection()),info.getPfid())%>
										</td>
									</tr>
<%}%>								
								</table>
							</td>
							<td>
								<table align='right'>
									<tr>
										<td class="label">
											BANK NAME
										</td>
										<td class="label">
											:
											<%=info.getBankName().toUpperCase()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											ACCOUNT NO
										</td>
										<td class="label">
											:
											<%=info.getAccountNo()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											FINANCIAL YEAR
										</td>
										<td class="label">
											:
											<%=info.getFinYear()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											VOUCHER NO
										</td>
										<td class="label">
											:
											<%=info.getVoucherNo()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											PREPARED DATE
										</td>
										<td class="label">
											: <%=info.getPreparedDt()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											VOUCHER DATE
										</td>
										<td class="label">
											: <%=info.getVoucherDt()%>
										</td>
									</tr>
									<tr>
										<td class="label">
											TRUST TYPE
										</td>
										<td class="label">
											: <%=info.getTrustType()%>
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
											Details (ifany)
										</td>
										<td class="tblabel" align="center">
											Cheque No.
										</td>
										<td class="tblabel" align="center">
											Debit(RS.)
										</td>
										<td class="tblabel" align="center">
											Credit(RS.)
										</td>
									</tr>
									<% 
List voucherDts = info.getVoucherDetails();
int listSize = voucherDts.size();
double debit = 0.0;
double credit = 0.0;
DecimalFormat df = new DecimalFormat("####.##");
for(int i=0;i<listSize;i++ ){
	VoucherDetails details = (VoucherDetails)voucherDts.get(i);
%>
									<tr>
										<td class="Data" align="center">
											<%=details.getAccountHead()%>
										</td>
										<td class="Data" align="left">
											<%=details.getParticular()%>
										</td>
										<td class="Data" align="center">
											<%=details.getMonthYear()==null?"":details.getMonthYear()%>
										</td>
										<td class="Data" align="left">
											<%=details.getDetails()%>
										</td>
										<td class="Data" align="left">
											<%=details.getChequeNo()%>
										</td>
										<td class="Data" align="right">
											<%=df.format(details.getDebit())%>
										</td>
										<td class="Data" align="right">
											<%=df.format(details.getCredit())%>
										</td>
									</tr>

									<%
	debit +=  details.getDebit();
	credit += details.getCredit();

}
double amount = debit-credit;

%>
									<tr>

										<td class="label" align="right" colspan="5">
											TOTAL&nbsp;&nbsp;
										</td>
										<td class="Data" align="right">
											<%=df.format(debit)%>
										</td>
										<td class="Data" align="right" >
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
							<td class="label" align="right" colspan="2" nowrap>
								NET AMOUNT :
								<%=df.format(Math.abs(amount))+" "+("PAYMENT".equals(info.getVoucherType().toUpperCase())?"Dr.":"Cr.")%>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<br />
								<br />
							</td>
						</tr>
						<tr>
							<td width="45%" class="label" colspan="2" nowrap>
								Rupees (in words) <%=util.ConvertInWords(Double.parseDouble(df.format(Math.abs(amount))))%> Only.
							</td>
						</tr>
						<tr>
							<td align="center" class="Data" colspan="2">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<br />
								<br />
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<table>
									<tr>
					
										<td  class="label" nowrap valign="top">
											NARRATION :
										</td>
										<td  class="label"  nowrap >
											<PRE><%=util.wrapTextArea(info.getDetails().trim(),100)%></PRE>
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
							<td>
								<br />
							</td>
						</tr>
						<tr>
							<td width="100%" colspan="2" align="center">
								<table width="100%" align="center">
									<tr>
										<td  class="label" nowrap align="right">
											Prepared By :
										</td>
										<td class="Data" class="label" nowrap align="left">
<%	
SQLHelper sql =new SQLHelper();
if(!"".equals(StringUtility.checknull(info.getPreparedBy()).trim())){
UserBean user = UserDAO.getInstance().getUser(sql.getDescription("epis_user","userid","username",info.getPreparedBy(),DBUtility.getConnection()));
String path = "uploads/SIGNATORY/"+user.getEsignatoryName();
if(user.getEsignatoryName() !=null){
%>		
										
											<img  alt="" src="<%=path%>"/>
<%}%>											
											<BR>
											(<%=user.getDisplayName()%>)
										</td>
<%}%>											
										
										<td class="Data" class="label" nowrap>
											Checked By :
											<%=info.getCheckedBy()%>
										</td>
										<td class="Data" class="label" nowrap align="right">Approved By :
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
						<tr>
							<td>
								<br />
							</td>
						</tr>
						<tr>
							<td colspan=4>
								<table width="90%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50%" class="label" nowrap>
											&nbsp;&nbsp;&nbsp;Voucher Prep No : <%=info.getKeyNo()%>
										</td>

										<td width="50%" class="label" nowrap align="right" >
											Received By :  ______________________
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<p style="page-break-before: always">
					<%
					}
					}
					%>
				</td>
			</tr>
		</table>
	</body>
</html>
