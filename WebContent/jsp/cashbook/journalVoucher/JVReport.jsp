<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%@ page import="com.epis.utilities.SQLHelper,com.epis.dao.admin.UserDAO,com.epis.bean.admin.UserBean,com.epis.utilities.DBUtility,com.epis.utilities.StringUtility,com.epis.bean.cashbook.JournalVoucherInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Forms - Journal Voucher Report</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		
		<link rel="stylesheet" href="css/aai.css" type="text/css" />		
		<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body background="body">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" colspan="2">
					<table border="0" cellpadding="3" cellspacing="0" align="center" >
						<tr>
							<td rowspan=3 >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<td class="reportlabel" align="center" valign="middle">
								<bean:message key="com.aai" bundle="common" />
							</td>
						</tr>
						<tr>							
							<td class="reportlabel" align="center" valign="middle">
								<bean:write name="info" property="trustType"/> <bean:message key="jv.trust" bundle="common" />
							</td>
						</tr>
						<tr>							
							<td class="reportlabel" align="center" valign="middle">
								<bean:write name="info" property="region"/>
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
				<td valign="top" >
					<table >
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.voucherType" bundle="common" />
							</td>
							<td class="label" align="left">
								: <bean:message key="jv.journal" bundle="common" />
							</td>
						</tr>
						<logic:equal value="P" name="info" property="partyType">
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.partyname" bundle="common" />
							</td>
							<td class="label" align="left">							
								: <bean:write name="info" property="partyName"/>
							</td>
						</tr>
						</logic:equal>
						<logic:equal value="E" name="info" property="partyType">
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.employeename" bundle="common" />
							</td>
							<td class="label" align="left">					
								: <bean:write name="info" property="employeeName"/>					
							</td>
						</tr>
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.pfid" bundle="common" />
							</td>
							<td class="label" align="left">	
								: <bean:write name="info" property="pfidFull"/>														
							</td>
						</tr>
						</logic:equal>
						<logic:equal value="O" name="info" property="partyType">
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.othercap" bundle="common" />
							</td>
							<td class="label" align="left">									
									: <bean:write name="info" property="other"/>
							</td>
						</tr>
						</logic:equal>
					</table>
				</td>
				<td valign="top" align="center">
					<table valign="top">
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.financialYear" bundle="common" />
							</td>
							<td class="label" align="left">
								: <bean:write name="info" property="finYear"/>
							</td>
						</tr>
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.prepdate" bundle="common" />
							</td>
							<td class="label" align="left">
								: <bean:write name="info" property="preparationDate"/>
							</td>
						</tr>
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.voucherno" bundle="common" />
							</td>
							<td class="label " align="left">
								: <bean:write name="info" property="voucherNo"/>
							</td>
						</tr>
						<tr>
							<td class="label" align="left">
								<bean:message key="jv.voucherdate" bundle="common" />
							</td>
							<td class="label" align="left">
								: <bean:write name="info" property="voucherDate"/>
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
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table class="border" width="80%">
						<tr>
							<td>
								
							</td>
							<td class="label" align="center">
								<bean:message key="bankInfo.accountCode" bundle="common" />								
							</td>
							<td class="label" align="center">
								<bean:message key="bankInfo.accountName" bundle="common" />								
							</td>
							<td class="label" align="center">
								<bean:message key="jv.details" bundle="common" />								
							</td>
							<td class="label" align="center">
								<bean:message key="voucher.debit" bundle="common" />								
							</td>
							<td class="label" align="center">
								<bean:message key="voucher.credit" bundle="common" />							
							</td>
						</tr>
					<c:set var="debitTotal" scope="page" value="0"/> 
					<logic:iterate id="debitDt" name="info" property="debitList">
						<c:set var='debitTotal' value='${debitDt.debit+debitTotal}'/>
						<tr>
							<td class="label">
								<bean:message key="jv.by" bundle="common" />
							</td>
							<td class="Data" align="center">
								<bean:write name="debitDt" property="accountCode"/>						
							</td>
							<td class="Data">
								<bean:write name="debitDt" property="particular"/>						
							</td>
							<td class="Data">
								<bean:write name="debitDt" property="description"/>								
							</td>
							<td class="Data" align="right">
								<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${debitDt.debit}'/>							
							</td>
							<td class="Data" align="right">
								<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${debitDt.credit}'/>						
							</td>
						</tr>					
					</logic:iterate>
						<tr>
							<td   colspan="6" style="height: 10px;background-color:#C8C8C8 ">								
							</td>
						</tr>
					<c:set var="creditTotal" scope="page" value="0"/> 
					<logic:iterate id="creditDt" name="info" property="creditList">
						<c:set var='creditTotal' value='${creditDt.credit+creditTotal}'/>
						<tr>
							<td class="label">
								<bean:message key="jv.to" bundle="common" />
							</td>
							<td class="Data" align="center">
								<bean:write name="creditDt" property="accountCode"/>						
							</td>
							<td class="Data">
								<bean:write name="creditDt" property="particular"/>						
							</td>
							<td class="Data">
								<bean:write name="creditDt" property="description"/>								
							</td>
							<td class="Data" align="right">
								<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${creditDt.debit}'/>								
							</td>
							<td class="Data" align="right">
								<fmt:formatNumber type="NUMBER"  minFractionDigits="2" value='${creditDt.credit}'/>					
							</td>
						</tr>					
					</logic:iterate>
						<tr>
							<td colspan="4" class="label" align="right">
								<bean:message key="report.total" bundle="common" />
							</td>							
							<td class="label" align="right">
								<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${debitTotal}'/>								
							</td>
							<td class="label" align="right">
								<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${creditTotal}'/>									
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td   colspan="2" style="height: 40px;">
					&nbsp;								
				</td>
			</tr>
			
			<tr>
				<td   colspan="2" style="height: 40px;">
					&nbsp;								
				</td>
			</tr>
			<tr>
				<td  colspan="2" class="label">
					<bean:message key="jv.rupeesinwords" bundle="common" />	:<bean:write name="info" property="amount"/>  <bean:message key="jv.only" bundle="common" />.
				</td>
			</tr>
			<tr>
				<td   colspan="2" style="height: 40px;">
					&nbsp;								
				</td>
			</tr>
			<tr>
				<td   colspan="2" align="left">
					<table align="left" width="100%"  border=0>
						<tr>
							<td  class="label" align="right"  valign="top">
								<bean:message key="jv.narrationcap" bundle="common" /> : &nbsp;&nbsp;&nbsp;
							</td>
							<td   class="label" align="left"  valign="top">
								<bean:write name="info" property="details"/>
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
JournalVoucherInfo info=(JournalVoucherInfo)request.getAttribute("info");
SQLHelper sql =new SQLHelper();
if(!"".equals(StringUtility.checknull(info.getPreparedBy()).trim())){
UserBean user = UserDAO.getInstance().getUser(info.getPreparedBy());
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
											&nbsp;
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
		</table>
	</body>
</html>