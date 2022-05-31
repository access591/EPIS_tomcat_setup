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
				var years = "";
				var count = 0;
				for(var i=0;i< document.forms[0].finYear.length ;i++){
					if(document.forms[0].finYear[i].selected){
						years += document.forms[0].finYear[i].value+",";
						count++;
					}					
				}
				if(count == 0){
					alert("Please Select Financial Years");
					return false;
				}
				years = years.substring(0,years.length-1);				
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/reports.do?method=getIncomeAndExpenditure&&trustType="+document.forms[0].trustType.value+"&&finYear="+years+"&&toMonth="+document.forms[0].toMonth.value+"&&reportType="+document.forms[0].reportType.value;
				window.open(url,"DailyStatementOfBankPosition","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
		</script>
	</head>

	<body>
		<html:form action="/reports.do?method=getDailyStatement" method="post"  onsubmit="return getReport()">
			<%=ScreenUtilities.screenHeader("ie.title")%>
			<table width="100%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						&nbsp;
					</td>					
				</tr>	
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="ie.finyear1" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:select property="finYear" multiple="multiple" size="2" styleClass="TextField">
						<html:options collection="currYear" property="name" labelProperty="code" name="Bean" />
						</html:select>						
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.trustType" bundle="common" />
						:
					</td>
					<td align="left">
						<html:select property="trustType" styleClass="TextField" style="width:120 px ;height:50 px">
							<html:options name="TrustTypeBean" property="trustType" labelProperty="trustType" labelName="trustType" collection="Trusts" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="bs.tomonth" bundle="common" />
						:
					</td>
					<td align="left">
						<html:select property="toMonth" styleClass="TextField" style="width:70 px ;height:50 px">
							<html:option value="">Select</html:option>
							<html:options name="Bean" property="code" labelProperty="name"  collection="finYears" />
						</html:select>
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
				