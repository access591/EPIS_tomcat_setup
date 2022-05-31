<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Daily Statement Of Bank position</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content=" Daily Statement Of Bank position" />
		
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script language="javascript" type="text/javascript">		  
			function  getReport(){
				if(document.forms[0].fromDate.value == ''){
					alert("Please Enter Date (Mandatory)");
					document.forms[0].fromDate.focus();
					return false;
				}
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/reports.do?method=getDailyStatement&&fromDate="+document.forms[0].fromDate.value+"&&reportType="+document.forms[0].reportType.value;
				window.open(url,"DailyStatementOfBankPosition","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
		</script>
	</head>

	<body>
		<html:form action="/reports.do?method=getDailyStatement" method="post"  onsubmit="return getReport()">
			<%=ScreenUtilities.screenHeader("dailystatement.title")%>
			<table width="100%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						&nbsp;
					</td>					
				</tr>	
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="jv.date" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text maxlength="11" property="fromDate" styleId="fromDate" styleClass="TextField" style="width:80 px" />
						<html:link href="javascript:showCalendar('fromDate');">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
						
					</td>
				</tr>
				<tr>
					<td class="tableTextBold"  align="right" nowrap>
						<bean:message key="accCode.reportType" bundle="common" /> : 
					</td>
					<td align="left" nowrap >
						<html:select property='reportType' style="WIDTH:70px" styleClass="TextField">
						<html:option value="html">HTML</html:option>
						<html:option value="excel">MS-Excel</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td   align="center" nowrap colspan="4">
						&nbsp;
					</td>					
				</tr>	
				<tr>
					<td colspan="4" align="center">
						<input type="submit" class="butt" value="Submit" />
						<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" />
					</td>
				</tr>
						
			 </table>
			<%=ScreenUtilities.searchFooter()%>			
	   </html:form>
	</body>
</html>
				