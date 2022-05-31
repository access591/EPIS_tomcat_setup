
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
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.cashbook.NotificationBean" %>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
VoucherInfo info = (VoucherInfo)request.getAttribute("einfo");
List notiInfo = (List)request.getAttribute("notifications");
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
		<script language="javascript">
		function checkNoti(){
			if(document.forms[0].transid.value==''){
				alert("Please Seclect Notification(Mandatory)");
				return false;
			}
		}
		</script>
	</head>

	<body background="body">
		<form action="/Voucher?method=updateNotiVoucher" onsubmit="return checkNoti()" method="post">
		<%=ScreenUtilities.screenHeader("Voucher Information")%>
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
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
										<td class="label" align="left">
											VOUCHER TYPE
										</td>
										<td class="label" align="left">
											:
											<%=info.getVoucherType().toUpperCase()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											<%=info.getPartyType().equals("E")?"EMPLOYEE NO":info.getPartyType().equals("P")?"PARTY NAME":"BANK NAME"%>
										</td>
										<td class="label" align="left"> 
											:
											<%=info.getEmpPartyCode()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											<%=info.getPartyType().equals("E")?"EMPLOYEE NAME":info.getPartyType().toUpperCase().equals("B")?"ACCOUNT NO":""%>
										</td>
										<td class="label" align="left">
											<%=info.getPartyType().equals("P")?"":": "+info.getPartyDetails().toUpperCase()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											EMPLOYEE PFID
										</td>
										<td class="label" align="left">
											:&nbsp;<%=(new CommonDAO()).getPFID(info.getPartyDetails().toUpperCase(),(new SQLHelper()).getDescription("employee_personal_info","to_char(DATEOFBIRTH,'DD/MON/YYYY')","pensionno",info.getPfid(),DBUtility.getConnection()),info.getPfid())%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											Notifications
										</td>
										<td class="label" align="left" >
											:<select name='transid' style="width:250px " class="Textfield">
											<option value="">Select One</option>
<%
	if(notiInfo !=null)
	for(int i=0;i<notiInfo.size();i++){
	NotificationBean bean = (NotificationBean)notiInfo.get(i);
%>											
											<option value="<%=bean.getTransactionId()+"|"+bean.getNotificationtype()%>"><%=bean.getPensionNo()+" - "+bean.getKeyNo()+" - "+bean.getApprovedDate()%> </option> 
<%	
	}
%>											
											</select>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table align='right'>
									<tr>
										<td class="label" align="left">
											BANK NAME
										</td>
										<td class="label" align="left">
											:
											<%=info.getBankName().toUpperCase()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											ACCOUNT NO
										</td>
										<td class="label" align="left">
											:
											<%=info.getAccountNo()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											FINANCIAL YEAR
										</td>
										<td class="label" align="left">
											:
											<%=info.getFinYear()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											VOUCHER NO
										</td>
										<td class="label" align="left">
											:
											<%=info.getVoucherNo()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											PREPARED DATE
										</td>
										<td class="label" align="left">
											: <%=info.getPreparedDt()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											VOUCHER DATE
										</td>
										<td class="label" align="left">
											: <%=info.getVoucherDt()%>
										</td>
									</tr>
									<tr>
										<td class="label" align="left">
											TRUST TYPE
										</td>
										<td class="label" align="left">
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
							<td width="100%" colspan="2" align="center">
								<table width="100%" align="center">
									<tr>
										<td class="Data" class="label" nowrap>
											Prepared By :
											<%=info.getPreparedBy()==null || "null".equals(info.getPreparedBy()) ?"":info.getPreparedBy()%>
										</td>
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
											<input type="hidden" name="keyno" value="<%=info.getKeyNo()%>"/>
											<input type="hidden" name="vouchertype" value="<%= request.getParameter("type")%>"/>
											<input type="submit" class="butt" value="Save"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%=ScreenUtilities.screenFooter()%></form>
	</body>
</html>
