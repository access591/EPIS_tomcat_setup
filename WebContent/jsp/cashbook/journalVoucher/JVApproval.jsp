<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Forms - Journal Voucher Report</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/aai.css" type="text/css" />		
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script language="javascript">
		  function checkDate(){
			   if(document.forms[0].voucherDate.value == ''){
					alert("Please Enter the Voucher Date(Mandatory)");
					document.forms[0].voucherDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].voucherDate)){
						document.forms[0].voucherDate.select();
						return(false);
					   }
			}
		</script>
	</head>
	<body background="body">
		<html:form action="/journalVoucherApproval.do?method=SaveApproval" onsubmit='return checkDate()'>
		<logic:notEqual name="info" property="approval" value="Y">
			<%=ScreenUtilities.searchSeperator()%>
			</logic:notEqual>
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
			<html:hidden property="keyNo" />
			<tr>
					<td align="center" colspan=2>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
							<tr>
								<td >
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="7">
									<bean:message key="com.aai" bundle="common" />
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
					<table>
						<tr>
							<td class="label">
								<bean:message key="jv.stationcap" bundle="common" />
							</td>
							<td class="label">
								: <bean:write name="info" property="stationName"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								<bean:message key="jv.voucherType" bundle="common" />
							</td>
							<td class="label">
								: <bean:message key="jv.journal" bundle="common" />
							</td>
						</tr>
						<logic:equal value="P" name="info" property="partyType">
						<tr>
							<td class="label">
								<bean:message key="jv.partyname" bundle="common" />
							</td>
							<td class="label">							
								: <bean:write name="info" property="partyName"/>
							</td>
						</tr>
						</logic:equal>
						<logic:equal value="E" name="info" property="partyType">
						<tr>
							<td class="label">
								<bean:message key="jv.employeename" bundle="common" />
							</td>
							<td class="label">					
								: <bean:write name="info" property="employeeName"/>					
							</td>
						</tr>
						<tr>
							<td class="label">
								<bean:message key="jv.pfid" bundle="common" />
							</td>
							<td class="label">	
								: <bean:write name="info" property="pfidFull"/>														
							</td>
						</tr>
						</logic:equal>
						<logic:equal value="O" name="info" property="partyType">
						<tr>
							<td class="label">
								<bean:message key="jv.othercap" bundle="common" />
							</td>
							<td class="label">									
									: <bean:write name="info" property="other"/>
							</td>
						</tr>
						</logic:equal>
					</table>
				</td>
				<td>
					<table>
						<tr>
							<td class="label">
								<bean:message key="jv.financialYear" bundle="common" />
							</td>
							<td class="label">
								: <bean:write name="info" property="finYear"/>
								<input type="hidden" name="fyear" value="<bean:write name="info" property="finYear"/>"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								<bean:message key="jv.date" bundle="common" />
							</td>
							<td class="label">
								: <bean:write name="info" property="preparationDate"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								<bean:message key="jv.voucherdate" bundle="common" />
							</td>
							<td class="label">:
							<logic:notEqual name="info" property="approval" value="Y">
									<html:text maxlength="11" property="voucherDate" styleId="voucherDate" styleClass='TextField' style="width:80 px" />
									<html:link href="javascript:showCalendar('voucherDate');">
										<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
									</html:link>
								</logic:notEqual>
								<logic:equal name="info" property="approval" value="Y">
									<bean:write name="info" property="voucherDate"/>
								</logic:equal>
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
					<table class="border" width="100%">
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
				<td  colspan="2" class="label">
					<bean:message key="jv.netamount" bundle="common" />	: <fmt:formatNumber minFractionDigits="2" type="NUMBER" value='${creditTotal}'/>						
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
					<table align="left">
						<tr>
							<td  class="label" align="right"  valign="top">
								<bean:message key="jv.narrationcap" bundle="common" /> : &nbsp;&nbsp;&nbsp;
							</td>
							<td   class="label" align="left"  valign="top">
								<PRE><bean:write name="info" property="details"/></PRE>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<logic:notEqual name="info" property="approval" value="Y">			
			<tr>
				<td colspan="2" align="center">
					<html:submit styleClass="butt"><bean:message key="button.save" bundle="common" /></html:submit>
				</td>
			</tr>
			</logic:notEqual>
		</table>
		<logic:notEqual name="info" property="approval" value="Y">
			<%=ScreenUtilities.searchFooter()%>
			</logic:notEqual>
			
		</html:form>
	</body>
</html>